import java.io.File;

public class Main {

	
	public static Database database;
	private static Server tcpServer;
	
	public static void main(String[] args) {
		
		
		tcpServer = new Server(5943);
		database = new Database("fileserver", "", "");
		
		
		
	}
	
	
}
