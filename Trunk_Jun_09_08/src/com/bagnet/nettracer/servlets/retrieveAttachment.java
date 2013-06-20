package com.bagnet.nettracer.servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.naming.Context;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import aero.nettracer.fs.model.FsAttachment;
import aero.nettracer.fs.utilities.TransportMapper;
import aero.nettracer.selfservice.fraud.client.ClaimClientRemote;

import com.healthmarketscience.rmiio.RemoteInputStreamClient;
import com.healthmarketscience.rmiio.RemoteStreamServer;
import com.healthmarketscience.rmiio.RemoteOutputStream;
import com.healthmarketscience.rmiio.RemoteInputStream;
import com.healthmarketscience.rmiio.GZIPRemoteInputStream;
import com.healthmarketscience.rmiio.RemoteOutputStreamClient;
import com.healthmarketscience.rmiio.RemoteInputStreamServer;

import com.bagnet.nettracer.tracing.actions.ModifyClaimAction;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.utils.TracerProperties;
import com.bagnet.nettracer.tracing.utils.ntfs.ConnectionUtil;

/**
 * This servlet is used to access the image from the file system.
 * 
 * @author Ankur Gupta
 * 
 *         create date - Feb 28, 2005
 */
public class retrieveAttachment extends HttpServlet {
	// ~ Constructors
	// *******************************************************************************

	private static final Logger logger = Logger
			.getLogger(retrieveAttachment.class);

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	private static String fileMimeTypeSelector(String imageID) {
		String result = "image/jpeg";
		if (!(imageID == null || imageID.equals(""))) {
			String myFileExt = imageID.substring(imageID.lastIndexOf('.') + 1,
					imageID.length());
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
			} else if ((myFileExt.equalsIgnoreCase("jpg"))
					|| (myFileExt.equalsIgnoreCase("png"))) {
				result = "image/jpeg";
			} else {
				result = "application/octet-stream";
			}
		}

		return result;
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		boolean ntfsUser = PropertyBMO.isTrue("ntfs.user");
		Context ctx = null;
		ClaimClientRemote remote = null;
		ActionMessages errors = new ActionMessages();
		try {
			ctx = ConnectionUtil.getInitialContext();
			remote = (ClaimClientRemote) ConnectionUtil.getRemoteEjb(ctx,
					PropertyBMO
							.getValue(PropertyBMO.CENTRAL_FRAUD_SERVICE_NAME));
		} catch (Exception ex) {
			logger.error(ex);
		}

		if (remote == null) {
			ActionMessage error = new ActionMessage(
					"error.fs.could.not.communicate");
			errors.add(ActionMessages.GLOBAL_MESSAGE, error);
		} else {
			HttpSession session = req.getSession(true);
			if (session.getAttribute("user") == null) {

				res.sendRedirect("logoff.do");
				return;
			}
			Agent user = (Agent) session.getAttribute("user");
			String attachID = req.getParameter("ID");

				FsAttachment results = TransportMapper.map(remote.getAttachment(Integer.valueOf(attachID),user.getCompanycode_ID())); // Should check for Permissions too
				if (results == null) {
					res.sendRedirect("invalid_attachment.do");
					return;
				}
				String imageStore = TracerProperties.get("fs_image_store");
				if ("US".equals(user.getCompanycode_ID())) {
					imageStore = TracerProperties.get(user.getCompanycode_ID(),"image_store");
				}
				
				File file = new File(imageStore + results.getPath());
				
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
				while (offset < img.length
						&& (numRead = is.read(img, offset, img.length - offset)) >= 0) {
					offset += numRead;
				}

				// Ensure all the bytes have been read in
				if (offset < img.length) {
					throw new IOException("File not completely read: "
							+ file.getName());
				}

				// Close the input stream and return bytes
				is.close();
				String fileMimeType = fileMimeTypeSelector(file.getPath());
				res.setContentType(fileMimeType);
				res.setContentLength(img.length);
				OutputStream out = res.getOutputStream();
				out.write(img);
				out.flush();
				out.close();
		}
	}
}