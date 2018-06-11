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
					
					
					
				}
				
			} catch (IOException e) {
				System.out.println("[FAIL] Failed reading line from client.");
				return;
			}
        	
        	
        }
		
	}
	
}
