package utils;
import java.io.File;
import java.text.DecimalFormat;

public class ServerFile {

	
	public int id;
	public String filename;
	public String path;
	
	public File file;
	
	public ServerFile(int id, String filename, String path, File file) {
		
		this.id = id;
		this.filename = filename;
		this.path = path;
		this.file = file;
		
		
		
	}
	
	public long getSize() {
		return file.length();
	}
	
	public byte[] getBytes() {
		return FileUtils.readBytesFromFile(file);
	}
	
}
