package aero.nettracer.portal.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import aero.nettracer.portal.faces.util.FileHelper;

/**
 * This servlet is used to access the image from the file system.
 * 
 * @author Ankur Gupta
 * 
 * create date - Feb 28, 2005
 */
public class showImage extends HttpServlet {
	//~ Constructors
	// *******************************************************************************

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException,
			IOException {
		doPost(req, res);
	}
	
	private static String fileMimeTypeSelector(String imageID) {
		String result = "image/jpeg";
		if ( !(imageID == null || imageID.equals(""))) {
			String myFileExt = imageID.substring(imageID.lastIndexOf('.')+1, imageID.length());
			if (myFileExt.equalsIgnoreCase("doc")) {
				result = "application/msword";
			} else if (myFileExt.equalsIgnoreCase("xls")) {
				result = "application/vnd.ms-excel";
			} else if (myFileExt.equalsIgnoreCase("pdf")) {
				result = "application/pdf";
			} else if (myFileExt.equalsIgnoreCase("tif")) {
				result = "image/tiff";
			} else if (myFileExt.equalsIgnoreCase("tiff")) {
				result = "image/x-tiff";
			} else if (myFileExt.equalsIgnoreCase("png")) {
				result = "image/png";
			} else if (myFileExt.equalsIgnoreCase("jpg")) {
				result = "image/jpeg";
			} else if (myFileExt.equalsIgnoreCase("jpeg")) {
				result = "image/jpeg";
			} else {
				result = "application/octet-stream";
			}
		}
		
		return result;
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException,
			IOException {

		String imageID = req.getParameter("ID");
		String imageStore = FileHelper.BASE_PATH;
		
		File file = new File(imageStore + imageID);
		
		if (! file.exists()) {
			file = new File(imageStore + "image-file-icon.png");
		}

		InputStream is = new FileInputStream(file);

		// Get the size of the file
		long length = file.length();

		// You cannot create an array using a long type.
		// It needs to be an int type.
		// Before converting to an int type, check
		// to ensure that file is not larger than Integer.MAX_VALUE.
		if (length > Integer.MAX_VALUE) {
			// File is too large
		}

		// Create the byte array to hold the data
		byte[] img = new byte[(int) length];

		// Read in the bytes
		int offset = 0;
		int numRead = 0;
		while (offset < img.length && (numRead = is.read(img, offset, img.length - offset)) >= 0) {
			offset += numRead;
		}

		// Ensure all the bytes have been read in
		if (offset < img.length) {
			throw new IOException("File not completely read: " + file.getName());
		}

		// Close the input stream and return bytes
		is.close();
		//res.setContentType("image/jpeg");
		String fileMimeType = fileMimeTypeSelector(imageID);
		res.setContentType(fileMimeType);
		OutputStream out = res.getOutputStream();
		res.setContentLength(img.length);
		out.write(img);
		out.flush();
		out.close();
	}
}