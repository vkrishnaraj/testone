package aero.nettracer.portal.faces.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class FileHelper {
	public static final String BASE_PATH = "C:/nettracer_claims_files/";
	private static String path;
	
	private static String getFilePath(String incidentId) {
		
		Calendar cal = new GregorianCalendar();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		
		String datePath = year + "/" + month + "/" + day + "/";
		
		String filePath = datePath + incidentId;
		return filePath;
		
	}

	public static boolean saveImage(String incidentId, String fileName, byte[] file) throws IOException {
		
		String filePath = getFilePath(incidentId);
		
		if (!makeFolder(BASE_PATH + filePath)) {
			//Error in creating a directory.
			return false;
		}
		
		path=filePath;
		
		//write the file to the file specified
		OutputStream outs = new FileOutputStream(BASE_PATH + filePath + "/" + fileName);
		outs.write(file);
		outs.flush();
		outs.close();
		
		return true;
		
	}

	

	public static boolean makeFolder(String folder) {
		java.io.File dir = new java.io.File(folder);
		if (!dir.exists()) {
			if (!dir.mkdirs()) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean deleteImage(String incidentId, String fileName, String filePath) throws IOException {
		
		// A File object to represent the filename
	    java.io.File f = new java.io.File(BASE_PATH + filePath + "/" + fileName);

	    // Make sure the file or directory exists and isn't write protected
	    if (!f.exists())
	      throw new IllegalArgumentException(
	          "Delete: no such file or directory: " + fileName);

	    if (!f.canWrite())
	      throw new IllegalArgumentException("Delete: write protected: " + fileName);

	   
	    // Attempt to delete it
	    boolean success = f.delete();

	    if (!success)
	      throw new IllegalArgumentException("Delete: deletion failed");
		
		return success;
		
	}

	public static String getPath() {
		return path ;
	}
}
