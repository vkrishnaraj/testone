/*
 * Created on Oct 28, 2004
 *
 * Administrator
 */
package com.bagnet.nettracer.tracing.utils;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;

import aero.nettracer.fs.model.Attachment;
import aero.nettracer.fs.model.FsAttachment;
import aero.nettracer.fs.utilities.TransportMapper;
import aero.nettracer.selfservice.fraud.client.ClaimClientRemote;

import com.healthmarketscience.rmiio.RemoteInputStream;
import com.healthmarketscience.rmiio.GZIPRemoteInputStream;
import com.healthmarketscience.rmiio.RemoteStreamServer;

import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.bmo.OhdBMO;
import com.bagnet.nettracer.tracing.bmo.OtherSystemInformationBMO;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.bmo.StatusBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Address;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.AirlineMembership;
import com.bagnet.nettracer.tracing.db.Articles;
import com.bagnet.nettracer.tracing.db.BDO;
import com.bagnet.nettracer.tracing.db.Company_Specific_Variable;
import com.bagnet.nettracer.tracing.db.DeliveryInstructions;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Incident_Claimcheck;
import com.bagnet.nettracer.tracing.db.Item;
import com.bagnet.nettracer.tracing.db.Item_Inventory;
import com.bagnet.nettracer.tracing.db.Item_Photo;
import com.bagnet.nettracer.tracing.db.Itinerary;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.OtherSystemInformation;
import com.bagnet.nettracer.tracing.db.Passenger;
import com.bagnet.nettracer.tracing.db.Remark;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.Claim;
import com.bagnet.nettracer.tracing.forms.ClaimForm;


/**
 * @author Administrator
 * 
 * create date - Oct 28, 2004
 */
public class FileShareUtils {
	
	static Logger logger = Logger.getLogger(FileShareUtils.class);
	
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
		
				Hashtable files = aform.getMultipartRequestHandler().getFileElements();
				FormFile theFile = (FormFile) files.get("attachfile");
				java.io.File thisfile=new java.io.File(theFile.getFileName());
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