import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {

	
	public static Database database;
	private static Server tcpServer;
	
	public static void main(String[] args) {
		
		database = new Database("fileserver", "root", "root");		
			
		tcpServer = new Server(5943);
	}
	
	
	
	
}
