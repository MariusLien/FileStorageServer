import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client extends Thread {

	private int ID;
	private Socket socket;

	public Client(Socket clientSocket, int ID) {

		this.socket = clientSocket;
		this.ID = ID;

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

			try {

				while ((line = brinp.readLine()) != null) {

					System.out.println("[CLIENT] " + line);

					// GET 1

					String[] args = line.split(" ");

					if (args.length == 0)
						out.write("ERROR Invalid request".getBytes());

					String action = args[0];
					String location = args[1];

					if (action == null || location == null)
						out.write("ERROR Invalid request".getBytes());

					if (action == "GET") {

						if (FileUtils.isInteger(location)) {

							// ID is passed.

							ServerFile serverfile = Main.database.getFile(Integer.parseInt(location));

							if (!serverfile.secured) {

								out.write(FileUtils.readBytesFromFile(serverfile.file));

							} else {
								System.out.println("[WARNING] File is secure.");
							}

						} else {

							ServerFile serverfile = Main.database.getFile(location);

							if (!serverfile.secured) {

								out.write(FileUtils.readBytesFromFile(serverfile.file));

							} else {
								System.out.println("[WARNING] File is secure.");
							}

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
