package com.bagnet.nettracer.servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bagnet.nettracer.tracing.utils.TracerProperties;

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

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException,
			IOException {
		HttpSession session = req.getSession(true);

		String imageID = req.getParameter("ID");
	
		File file = new File(TracerProperties.get("image_store") + imageID);

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
		res.setContentType("image/jpeg");
		OutputStream out = res.getOutputStream();
		res.setContentLength(img.length);
		out.write(img);
		out.flush();
		out.close();
	}
}