

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class FileUtils {


	
	public static String md5(String password) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			System.out.println("[FAIL] Could not find alghoritm 'md5'");
			e.printStackTrace();
			return "";
		}
        md.update(password.getBytes());
        
        byte byteData[] = md.digest();
 
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
	}

	public static void createStorageFolder() {
		File dir = new File("storage");
		if(!dir.exists()) {
			dir.mkdirs();
		}
	}
	
	
	
	public static boolean isInteger(String t) {
	
			
			try {
				Integer.parseInt(t);
			} catch(Exception e) {
				return false;
			}	

	  
	    return true;
	}
	
	public static void saveToStorage(byte[] bytes, String path) {
		
		
		File directory = new File("storage\\" + path);
		String filename = getFilesInDir(directory) + ".data";
		
		
		Path fileLocation = Paths.get("storage\\" + path + "\\" + filename);
		

		try {
			Files.write(fileLocation, bytes, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.CREATE_NEW);
		
			
			
		} catch (IOException e) {
			System.out.println("[FAIL] Failed writing file to storage.");
		}
		
	}
	
	
	public static int getFilesInDir(File dir) {
		
		if(!dir.exists()) dir.mkdir();
		
		int size = dir.listFiles().length;
		System.out.println(size);
		return size;
	}
	
	
//	public static byte[] retrieveBytesFromStorage(String path) {
//		
//		Path fileLocation = Paths.get("path");
//		try {
//			byte[] data = Files.readAllBytes(fileLocation);
//			return data;
//		} catch (IOException e) {
//			System.out.println("[FAIL] Failed retrieving bytes from storage.");
//			return null;
//		}
//	}
	
		

	
	public static byte[] readBytesFromFile(File file) {

        FileInputStream fileInputStream = null;
        byte[] bytesArray = null;

        try {

            bytesArray = new byte[(int) file.length()];

            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bytesArray);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        return bytesArray;

    }
	
}
