/*
 * Created on Oct 28, 2004
 *
 * Administrator
 */
package com.bagnet.nettracer.tracing.utils;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;

import javax.servlet.ServletContext;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;

import aero.nettracer.fs.model.Attachment;
import aero.nettracer.fs.model.FsAttachment;
import aero.nettracer.fs.utilities.TransportMapper;
import aero.nettracer.selfservice.fraud.client.ClaimClientRemote;

import com.bagnet.nettracer.reporting.ReportingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Claim;


/**
 * @author Administrator
 * 
 * create date - Oct 28, 2004
 */
public class FileShareUtils {
	
	static Logger logger = Logger.getLogger(FileShareUtils.class);

	public static File getFile(String filepath, ServletContext sc) {
		if (StringUtils.isBlank(filepath)) {
			logger.error("Empty file path.");
			return null;
		}
		
		filepath = sc.getRealPath("/") + ReportingConstants.REPORT_TMP_PATH + filepath;
		File f = new File(filepath);
		if (!f.exists()) {
			logger.error("no report file exists");
			return null;
		} else {
			return f;
		}
	}
	
	public static Attachment uploadFile(ActionForm aform, String lead, Claim claim, Agent user, ActionMessages errors, ClaimClientRemote remote) {
		// Save the file in the local directory.
		FsAttachment fsAttach = uploadFile(aform, lead, user, errors, remote);
		
		if (fsAttach == null) {
			return null;
		}
		
		Attachment attachment = new Attachment();
		attachment.setAgent(user);
		attachment.setClaim(claim);
		attachment.setCreateDate(new Date());
		attachment.setDescription(fsAttach.getDescription());
		attachment.setAttachment_id(fsAttach.getId());
		return attachment;
	}
	
	public static FsAttachment uploadFile(ActionForm aform, String lead, Agent user, ActionMessages errors, ClaimClientRemote remote) {
		// Save the file in the local directory.
		
		logger.debug("UPLOADING FILE TO FS. REMOTE: " + remote);
		
				Hashtable<?, ?> files = aform.getMultipartRequestHandler().getFileElements();
				FormFile theFile = (FormFile) files.get("attachfile");
				if (theFile != null && theFile.getFileSize() > 0) {
					String st = Long.toString((new Date()).getTime());
					
					// now make subfolder with year and month
					Calendar cal = new GregorianCalendar();
					int year = cal.get(Calendar.YEAR);
					int month = cal.get(Calendar.MONTH) + 1;
					int day = cal.get(Calendar.DAY_OF_MONTH);
					
					//compute the folder name
					String folder = "NTFS/" + user.getStation().getCompany().getCompanyCode_ID() + "/" + year + "/" + month + "/" + day + "/";;

					//paths to be stored in the db
					String fileName = theFile.getFileName();
					String filepath = folder + "FS_" + lead + "_" + st + "_" + fileName;
					String thumbpath = folder + "FS_" + lead + "_" + st + "_thumb_" + fileName;
//					RemoteStreamServer server;
//					server.
					
					logger.debug("FILE PREPARED. TRYING TO GENERATE REMOTE INPUT STREAM.");
					
					try{
						FsAttachment fsAttach = null;
						boolean uploadresult = ImageUtils.doUpload(theFile, user, folder, filepath, thumbpath, true);
						if (!uploadresult) {
							ActionMessage error = new ActionMessage("error.uploadfile");
							errors.add(ActionMessages.GLOBAL_MESSAGE, error);
							return null;
						} else {
							// add the image to the DB.
							FsAttachment attach = new FsAttachment();
							attach.setPath(filepath);
							attach.setDescription(fileName);
							attach.setCompCode(user.getStation().getCompany().getCompanyCode_ID());
							fsAttach = TransportMapper.map(remote.uploadAttachment(TransportMapper.map(attach)));
						}	
						
						// add the image to the DB.
						//Send to 
						logger.debug("FILE ATTACHMENT SUCCESSFUL!!!");

						return fsAttach;
					}
					catch(Exception ee) {
						ee.printStackTrace();
							ActionMessage error = new ActionMessage("error.uploadfile");
							errors.add(ActionMessages.GLOBAL_MESSAGE, error);
							
						} 
				} else {
					// upload file errors.
					ActionMessage error = new ActionMessage("error.uploadfile");
					errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				}	
				return null;
	}
		
}