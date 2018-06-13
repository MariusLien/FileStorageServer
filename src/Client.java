import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.file.Path;
import java.util.Date;

public class Client extends Thread {

	private int ID;
	private Socket socket;
	
	private CurrentFile currentfile;
	private boolean expectingBytes;
	

	public Client(Socket clientSocket, int ID) {

		this.socket = clientSocket;
		this.ID = ID;
		this.currentfile = null;
		this.expectingBytes = false;

	}
	
	private void sendFile(ServerFile file, DataOutputStream out) {
		
		long startTime = System.currentTimeMillis();
		try {
			out.write(FileUtils.readBytesFromFile(file.file));
			Date endTime = new Date();
			float difference = endTime.getTime() - startTime;
			System.out.println("[OK] File done sending (" + difference + " ms)");
		} catch (IOException e) {
			try {
				out.write("ERROR Failed to send file.".getBytes());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			System.out.println("[FAIL] Failed to send file.");
			return;
		}
	
		
	}

	public void run() {

		InputStream in = null;
		BufferedReader brinp = null;
		DataOutputStream out = null;

		try {
			in = socket.getInputStream();
			brinp = new BufferedReader(new InputStreamReader(in));
			out = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			System.out.println("[FAIL] Failed initializing Inputstream, bufferedreader or dataoutputstream.");
			return;
		}
		
		

		
		
		String line;
		while (true) {

			if(this.currentfile != null) {
				System.out.println("[STATUS] Waiting for bytes");
				
				byte[] bytes = new byte[256];
				try {
					int size = in.read(bytes);
				} catch (IOException e) {
					System.out.println("[FAIL] Failed recieving bytes.");
					e.printStackTrace();
					this.currentfile = null;
					continue;
				}
				
				Path path = FileUtils.saveToStorage(bytes, this.currentfile.path.getPath());
				
				if(path == null) {
					try {
						out.write("ERROR Failed to store file to storage.".getBytes());
					} catch (IOException e) {
						e.printStackTrace();
					}
					this.currentfile = null;
					continue;
				}
				
				if(this.currentfile.usingPassword) {
					Main.database.uploadFile(path.toString(), this.currentfile.password);
				} else {
				
					Main.database.uploadFile(path.toString());
					this.currentfile = null;
					continue;
				}
				
				
				continue;
			} 
			
			
			try {

				if ((line = brinp.readLine()) != null) {
					
					
					
					System.out.println("[CLIENT] " + line);

					// GET 1

					String[] args = line.split(" ");
					
					if(args.length < 1) {
						out.write("ERROR Invalid request".getBytes());
						continue;
					}
					
					String action = args[0];
					
				
					
					
					if (action.equals("GET")) {
											
						if (args.length < 2) {
							out.write("ERROR Invalid request".getBytes());
							continue;
						}
						
						String location = args[1];
						
						if (FileUtils.isInteger(location)) {
							

							ServerFile serverfile = Main.database.getFile(Integer.parseInt(location));

							if(serverfile == null) {
								out.write("ERROR Could not find file.".getBytes());
								continue;
							}
							
							if (!serverfile.secured) {
								sendFile(serverfile, out);
							} else {
								
								if(args.length == 3) {
									
									String password = FileUtils.md5(args[2]);

									if(password.equalsIgnoreCase(serverfile.accesskey)) {
										sendFile(serverfile, out);
									} else {
										out.write("ERROR Password incorrect.".getBytes());
									}
									
								} else {
									out.write("ERROR Invalid request use, GET <path or id> <password>".getBytes());
								}
								
								System.out.println("[WARNING] File is secure.");
							}

						} else {

							ServerFile serverfile = Main.database.getFile(location);

							if (!serverfile.secured) {

								out.write(FileUtils.readBytesFromFile(serverfile.file));

							} else {
								
								if(args.length == 3) {
									
									String password = FileUtils.md5(args[2]);

									if(password.equalsIgnoreCase(serverfile.accesskey)) {
										sendFile(serverfile, out);
									} else {
										out.write("ERROR Password incorrect.".getBytes());
									}
									
								} else {
									out.write("ERROR Invalid request use, GET <path or id> <password>".getBytes());
								}
								
								System.out.println("[WARNING] File is secure.");
							}

						}

					} else if(action.equals("UPLOAD")) {
						
						// UPLOAD <path> <password>
						
						if(args.length == 2) {
							
							// Not secured file
							
							String path = args[1];
							
							
							File directory = new File("storage\\" + path);
						
							
							
							this.currentfile = new CurrentFile(directory, false, null);
							System.out.println("Setted the current file");
							
							
							continue;
						} 
						
						if(args.length == 3) {
							
							// Secured file
							

							String password = FileUtils.md5(args[2]);
							String path = args[1];
							
							
							File directory = new File("storage\\" + path);
							
							
							
							this.currentfile = new CurrentFile(directory, true, password);
							System.out.println("Setted the current file");
							
							continue;
						}
						
						
						
						
						
					}

				}

			} catch (IOException e) {
				System.out.println("[FAIL] Failed reading line from client.");
				return;
			}

		}

	}

}



class CurrentFile {
	
	public File path;
	public String password;
	public boolean usingPassword;
	
	public CurrentFile(File path, boolean usingpassword, String password) {
		
		this.path = path;
		this.usingPassword = usingpassword;
		this.password = password;
		
	}
	
	
}
