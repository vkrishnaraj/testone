/*
 * Created on Jul 27, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.utils;

import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.log4j.Logger;
import org.apache.struts.upload.FormFile;

import com.bagnet.nettracer.tracing.db.Agent;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * @author Ankur Gupta
 * 
 */
public class ImageUtils {
	private static final int THUMB_WIDTH = 120;
	private static final int THUMB_HEIGHT = 80;
	private static final int THUMB_QUALITY = 80;
	private static Logger logger = Logger.getLogger(ImageUtils.class);

	public static boolean generateThumbImageAndSave(String sourceFileName, String destFileName) {
		boolean success = false;
		try {
			//		 load image from INFILE
			Image image = Toolkit.getDefaultToolkit().getImage(sourceFileName);
			MediaTracker mediaTracker = new MediaTracker(new Container());
			mediaTracker.addImage(image, 0);
			mediaTracker.waitForID(0);
			// determine thumbnail size from WIDTH and HEIGHT
			int thumbWidth = THUMB_WIDTH;
			int thumbHeight = THUMB_HEIGHT;
			double thumbRatio = (double) thumbWidth / (double) thumbHeight;
			int imageWidth = image.getWidth(null);
			int imageHeight = image.getHeight(null);
			double imageRatio = (double) imageWidth / (double) imageHeight;
			if (thumbRatio < imageRatio) {
				thumbHeight = (int) (thumbWidth / imageRatio);
			} else {
				thumbWidth = (int) (thumbHeight * imageRatio);
			}
			// draw original image to thumbnail image object and
			// scale it to the new size on-the-fly
			BufferedImage thumbImage = new BufferedImage(thumbWidth, thumbHeight,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D graphics2D = thumbImage.createGraphics();
			graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			graphics2D.drawImage(image, 0, 0, thumbWidth, thumbHeight, null);
			// save thumbnail image to OUTFILE
			BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(destFileName));
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(thumbImage);
			int quality = THUMB_QUALITY;
			quality = Math.max(0, Math.min(quality, 100));
			param.setQuality((float) quality / 100.0f, false);
			encoder.setJPEGEncodeParam(param);
			encoder.encode(thumbImage);
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
			success = false;
		}
		return success;
	}
	// upload images or files for the front pages
	public static boolean doUpload(FormFile theFile,Agent user, String folder,String picpath,String thumbpath) {
		return doUpload(theFile, user, folder, picpath, thumbpath, false);
	}
	
	// upload images or files for the front pages
	public static boolean doUpload(FormFile theFile,Agent user, String folder,String picpath,String thumbpath, boolean isFs) {

		try {
			//to find out the file extension: if image, then create thumb
			boolean isThisImageFile = false;
			String myFileName = theFile.getFileName();
			String myFileExt = myFileName.substring(myFileName.lastIndexOf('.')+1, myFileName.length());
			if (myFileExt.equalsIgnoreCase("gif")
//				|| myFileExt.equalsIgnoreCase("tif")
//				|| myFileExt.equalsIgnoreCase("gif")
				|| myFileExt.equalsIgnoreCase("jpg")
				|| myFileExt.equalsIgnoreCase("jpeg")) {
				
				isThisImageFile = true;
			}
			
			// check file size
			int allowedsize = user.getStation().getCompany().getVariable().getMax_image_file_size();
			if (allowedsize > 0 && theFile.getFileSize() > allowedsize) {
				return false;
			}
			
			//retrieve the file data
			String image_store = TracerProperties.get(user.getCompanycode_ID(),"image_store");
			if (isFs && !"US".equals(user.getCompanycode_ID())) {
				image_store = TracerProperties.get("fs_image_store");
			}
			if (!ImageUtils.makeFolder(image_store + folder)) {
				//Error in creating a directory.
				logger.error("Unable to create directory");
				return false;
			}

			//file names corresponding to the local store.
			String sfileName = image_store + picpath;
			String dfileName = image_store + thumbpath;
			
			
			//write the file to the file specified
			byte databytes[] = new byte[theFile.getFileSize()];
			InputStream ins = theFile.getInputStream();
			int bytesRead = 0;
			int totalRead = 0;
			while (totalRead < theFile.getFileSize()) {
				bytesRead = ins.read(databytes, totalRead, theFile.getFileSize());
				totalRead += bytesRead;
			}
			ins.close();
			
			OutputStream outs = new FileOutputStream(sfileName);
			outs.write(databytes);
			outs.flush();
			outs.close();
			
			if (isThisImageFile) {
				// generate thumbnail
				//logger.info("Right before success");
				boolean success = ImageUtils.generateThumbImageAndSave(sfileName, dfileName);
				//logger.info("Success: " + success);
				return success;
			} else {
				return true;
			}

		} catch (FileNotFoundException fnfe) {
			logger.error("Exception A: " + fnfe.getMessage());
			//Error creating file
			return false;
		} catch (IOException ioe) {
			//Error opening files.
			logger.error("Exception B: " + ioe.getMessage());
			return false;
		}		
		
	}
	
	
	public static boolean makeFolder(String folder) {
		//logger.error("Folder name:" + folder);
		File dir = new File(folder);
		if (!dir.exists()) {
			if (!dir.mkdirs()) {
				return false;
			}
		}
		return true;
	}
}