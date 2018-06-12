import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

	public Connection conn;
	
	
	public Database(String dbname, String username, String pass) {
		
		try {
			conn = DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/" + dbname,username,pass);
			
			System.out.println("[OK] Connection with MYSQL database established.");
			
		} catch (SQLException e) {
			System.out.println("[FAIL] Failed to connect to MYSQL database.");
			return;
		}  
		
		
	}
	
	public Connection getConnection() {
		return conn;
	}
	
	
	
}
