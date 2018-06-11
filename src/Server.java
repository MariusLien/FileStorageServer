import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

	
	private int maxClients;
	public ArrayList<Client> clients;
	
	
	private int port;
	public ServerSocket socket;
	
	
	public Server(int port, int maxClients) {
	
		this.port = port; 
		this.maxClients = maxClients;
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
			
			if(clients.size() >= maxClients) {
				return;
			}
			
			try {
				
				Socket clientSocket = socket.accept();
				
				Client client = new Client(clientSocket);
				client.start();
				clients.add(client);
				
			} catch (IOException e) {
				System.out.println("[FAIL] Failed accepting the socket with the client.");
				return;
			}
			
			
			
			
		}
		
		
	}
	
	
}
