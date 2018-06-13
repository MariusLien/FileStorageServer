import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.net.ssl.SSLServerSocketFactory;

public class Server {

	
	public ArrayList<Client> clients;
	
	public boolean isSSL;
	private int port;
	public ServerSocket socket;
	
	
	public Server(int port, String sslCertPath, String pass) {
		
		System.setProperty("javax.net.ssl.trustStore", sslCertPath);
		System.setProperty("javax.net.ssl.keyStorePassword", pass);
		
		this.isSSL = true;
		this.port = port; 
		this.clients = new ArrayList<Client>();
		
		
		startListening();
		
	}
	
	public Server(int port) {
	
		this.isSSL = false;
		this.port = port; 
		this.clients = new ArrayList<Client>();
		
		
		startListening();
		
	
	}


	private void startListening() {
		
		
		try {
			if(!isSSL) {
				socket = new ServerSocket(this.port);
				System.out.println("[OK] Server is now listening on port: " + port);
			} else {
				socket = ((SSLServerSocketFactory)SSLServerSocketFactory.getDefault()).createServerSocket(port);
				System.out.println("[OK] Server is now listening on a secure connection.");
			}
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
