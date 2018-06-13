
import java.io.File;
import java.text.DecimalFormat;

public class ServerFile {

	
	public int id;
	public String filename;
	public String path;
	public Boolean secured;
	public String accesskey;
	public File file;
	
	public ServerFile(int id, String filename, String path, boolean secured, String accesskey, File file) {
		
		this.accesskey = accesskey;
		this.id = id;
		this.filename = filename;
		this.path = path;
		this.file = file;
		this.secured = secured;
		
		
	}
	
	public long getSize() {
		return file.length();
	}
	
	public byte[] getBytes() {
		return FileUtils.readBytesFromFile(file);
	}
	
}
