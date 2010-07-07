package com.bagnet.nettracer.tracing.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class FileHelper {
	
	private static final String BASE_PATH = "c:/nettracer_claims_files/";

	public boolean saveImage(int claimId, String fileName, byte[] file) throws IOException {
		
		Calendar cal = new GregorianCalendar();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		
		String datePath = year + "/" + month + "/" + day + "/";
		
		String filePath = BASE_PATH + datePath + claimId;
		
		if (!makeFolder(filePath)) {
			//Error in creating a directory.
			return false;
		}
		
		//write the file to the file specified
		OutputStream outs = new FileOutputStream(filePath + "/" + fileName);
		outs.write(file);
		outs.flush();
		outs.close();
		
		return true;
		
	}

	public boolean makeFolder(String folder) {
		File dir = new File(folder);
		if (!dir.exists()) {
			if (!dir.mkdirs()) {
				return false;
			}
		}
		return true;
	}

}
