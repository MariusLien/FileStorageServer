import java.io.File;
import java.nio.file.Paths;

public class Main {

	
	public static Database database;
	private static Server tcpServer;
	
	public static void main(String[] args) {
		
		FileUtils.createStorageFolder();
		
		database = new Database("fileserver", "root", "root");		
		
		
		ServerFile sfile = database.getFile(4);
		
		
		tcpServer = new Server(5943);
	}
	
	
	
	
}
