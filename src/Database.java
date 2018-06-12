import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {

	public Connection conn;

	public Database(String dbname, String user, String pass) {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("[OK] Driver loaded.");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("[FAIL] Cannot find the driver in the classpath!", e);
		}

		System.out.println("[STATUS] Connecting to databse.");

		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + dbname
					+ "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					user, pass);

			System.out.println("[OK] Connection with MYSQL database established.");

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("[FAIL] Failed to connect to MYSQL database.");
			return;
		}

	}

	public Connection getConnection() {
		return conn;
	}

	public ServerFile getFile(String path) {

		try {
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM files WHERE path=?");
			statement.setString(0, path);
			ResultSet result = statement.executeQuery();
			result.next();

			File file = new File(path);
			String access = result.getString(5);
			boolean secured = (access.isEmpty()) ? false : true;
			
			
			
			if (file.exists()) {
				return new ServerFile(result.getInt(0), result.getString(1), path, secured, file);
			}
			System.out.println("[FAIL] File could not be found.");
			return null;
		} catch (SQLException e) {
			System.out.println("[FAIL] Failed creating prepared statement.");
			return null;
		}
	}

	public void uploadFile(String path) {

		File file = new File(path);
		String filename = file.getName();
		long size = file.length();

		try {
			PreparedStatement statement = conn
					.prepareStatement("INSERT INTO files (filename, path, size, access) VALUES (?,?,?,?)");
			statement.setString(1, filename);
			;
			statement.setString(2, path);
			statement.setLong(3, size);
			statement.setString(4, "");

			statement.executeUpdate();
			System.out.println("[OK] File record uploaded to database.");

		} catch (SQLException e) {
			System.out.println("[FAIL] Failed uploading file record to database.");
			return;
		}

	}

	public void uploadFile(String path, String password) {

		File file = new File(path);
		String filename = file.getName();
		long size = file.length();

		try {
			
			String encPassword = FileUtils.md5(password);
			System.out.println(encPassword);
			
			PreparedStatement statement = conn
					.prepareStatement("INSERT INTO files (filename, path, size, access) VALUES (?,?,?,?)");
			statement.setString(1, filename);
			;
			statement.setString(2, path);
			statement.setLong(3, size);
			statement.setString(4, encPassword);

			statement.executeUpdate();
			System.out.println("[OK] File record uploaded to database.");

		} catch (SQLException e) {
			System.out.println("[FAIL] Failed uploading file record to database.");
			return;
		}

	}

	public ServerFile getFile(int id) {

		try {
			PreparedStatement statement = conn.prepareStatement("SELECT * FROM files WHERE id=?");
			statement.setInt(1, id);
			ResultSet result = statement.executeQuery();

			if (result.next()) {
				String path = result.getString(3);
				File file = new File(path);

				String access = result.getString(5);
				boolean secured = (access.isEmpty()) ? false : true;
				
				if (file.exists()) {
					return new ServerFile(result.getInt(1), result.getString(2), path, secured, file);
				}
				System.out.println("[FAIL] File could not be found");
				return null;
			}

			System.out.println("[FAIL] Could not find any files on the ID " + id);
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("[FAIL] Failed creating prepared statement.");
			return null;
		}
	}

}
