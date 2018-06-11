import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client extends Thread {

	
	private Socket socket;
	
	public Client(Socket clientSocket) {
		this.socket = clientSocket;
	}
	
	
	public void run() {
		
		InputStream inp = null;
        BufferedReader brinp = null;
        DataOutputStream out = null;
        
        try {
            inp = socket.getInputStream();
            brinp = new BufferedReader(new InputStreamReader(inp));
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
        	System.out.println("[FAIL] Failed initializing Inputstream, bufferedreader or dataoutputstream.");
            return;
        }
        
        
        String line;
        while (true) {
        	
        	/* Code comes soon */
        	
        	
        	
        }
		
	}
	
}
