import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

	
	public ArrayList<Client> clients;
	
	
	private int port;
	public ServerSocket socket;
	
	
	public Server(int port) {
	
		this.port = port; 
		this.clients = new ArrayList<Client>();
		
		
		startListening();
		
	}


	private void startListening() {
		
		
		try {
			socket = new ServerSocket(this.port);
			System.out.println("[OK] Server is now listening on port: " + port);
		} catch (IOException e) {
			System.out.println("[FAIL] Failed to bind to port: " + port);
			return;
		}
		
		
		while(true) {
		
			
			try {
				
				Socket clientSocket = socket.accept();
				
				System.out.println("[OK] Connection established with " + socket.getInetAddress().getHostName());
				
				Client client = new Client(clientSocket, clients.size() + 1);
				client.start();
				clients.add(client);				
				
			} catch (IOException e) {
				System.out.println("[FAIL] Failed to establish connection with client.");
				return;
			}
			
			
			
			
			
			
		}
		
		
	}
	
	
}
