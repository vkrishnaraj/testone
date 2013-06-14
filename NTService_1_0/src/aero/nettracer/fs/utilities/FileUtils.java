/*
 * Created on Jul 27, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package aero.nettracer.fs.utilities;

import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import aero.nettracer.fs.model.FsAttachment;
import aero.nettracer.fs.model.FsClaim;
import aero.nettracer.fs.model.detection.AccessRequest.RequestStatus;
import aero.nettracer.fs.utilities.tracing.Producer;
import aero.nettracer.selfservice.fraud.PrivacyPermissionsBean;
import aero.nettracer.serviceprovider.common.db.PrivacyPermissions;
import aero.nettracer.serviceprovider.common.db.PrivacyPermissions.AccessLevelType;
import aero.nettracer.serviceprovider.common.hibernate.HibernateWrapper;

import com.healthmarketscience.rmiio.GZIPRemoteInputStream;
import com.healthmarketscience.rmiio.RemoteInputStream;
import com.healthmarketscience.rmiio.RemoteInputStreamClient;
import com.healthmarketscience.rmiio.RemoteInputStreamServer;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * @author Sean Fine
 * 
 */
public class FileUtils {
	private static final int THUMB_WIDTH = 120;
	private static final int THUMB_HEIGHT = 80;
	private static final int THUMB_QUALITY = 80;
	private static Logger logger = Logger.getLogger(FileUtils.class);

	public static boolean generateThumbImageAndSave(String sourceFileName, String destFileName ) {
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
	public static FsAttachment doUpload(FsAttachment attach) {
		int attachid=saveAttachment(attach);
		return attach;
	}
	
	public static int saveAttachment(FsAttachment file) {
		Transaction t = null;
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			t = sess.beginTransaction();
				logger.debug("saving:" + file.getId());
				sess.saveOrUpdate(file);
				t.commit();
			
			return file.getId();
		} catch (Exception e) {
			e.printStackTrace();
			if (t != null) {
				try {
					t.rollback();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			return -1;
		} finally {

			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static boolean saveAttachments(Set<FsAttachment> attachments) {
		Transaction t = null;
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			t = sess.beginTransaction();
				for(FsAttachment attach:attachments){
					sess.saveOrUpdate(attach);
				}
				t.commit();
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			if (t != null) {
				try {
					t.rollback();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			return false;
		} finally {

			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static FsAttachment getPath(int attachID, Session sess, String airline) {
		if (attachID < 0) return null;
		boolean sessionNull = (sess == null);

		try {
			if (sessionNull) {
				sess = HibernateWrapper.getSession().openSession();
			}
			FsAttachment attach=(FsAttachment) sess.load(FsAttachment.class, attachID);

			boolean canAccess=true;
			if(attach.getClaim_id()!=0){
				FsClaim claim=getClaim(attach.getClaim_id(), sess);
				List<PrivacyPermissions> p = PrivacyPermissionsBean.getPrivacyPermissions();
				List<FsAttachment> attachList=new ArrayList<FsAttachment>();
				attachList.add(attach);
				
				RequestStatus rs;
				long requestedId;
					
				requestedId = claim.getFile().getId();
				rs = Producer.getRequestStatus(requestedId, airline);
				if(rs != null && rs.equals(RequestStatus.Approved)){
					canAccess=Producer.censorAttachments(attachList, claim.getFile().getValidatingCompanycode(), AccessLevelType.req, airline, p);
				} else {
					canAccess=Producer.censorAttachments(attachList, claim.getFile().getValidatingCompanycode(), AccessLevelType.def, airline, p);
				}
			}
			if(canAccess) 
				return attach;
			else 
				return null;
		} catch (Exception e) {
			logger.error("unable to retrieve FsAttachment: " + e);
			e.printStackTrace();
			return null;
		} finally {
			if (sess != null && sessionNull) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.error("unable to close connection: " + e);
				}
			}
		}
	}
	
	public static FsClaim getClaim(long claimId, Session sess){
		boolean sessionNull = (sess == null);

		try {
			if (sessionNull) {
				sess = HibernateWrapper.getSession().openSession();
			}
		return (FsClaim) sess.load(FsClaim.class, claimId);
		} catch (Exception e) {
			logger.error("unable to retrieve FsClaim: " + e);
			e.printStackTrace();
			return null;
		} finally {
			if (sess != null && sessionNull) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.error("unable to close connection: " + e);
				}
			}
		}
	}
	
	public static boolean makeFolder(String folder) {
		File dir = new File(folder);
		if (!dir.exists()) {
			if (!dir.mkdirs()) {
				return false;
			}
		}
		return true;
	}
	
	public static FsAttachment getAttachment(int attachID, String valComp){
		
	    FsAttachment attach=getPath(attachID, null, valComp);
	    return attach;
	}
	
	public static List<FsAttachment> getAttachmentsById(List<Integer> attachIDs){
		String query = "from aero.nettracer.fs.model.FsAttachment attachment where attachment.id in (:attachIDs)";
		Session session = null; 
		List<FsAttachment> list = null;
		try {
			session = HibernateWrapper.getSession().openSession();
			Query q = session.createQuery(query);
			q.setParameterList("attachIDs", attachIDs);
			list = q.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return list;
	}
	
	public static List<FsAttachment> getAttachmentsByClaimId(long claimId, String airline){
		String query = "from aero.nettracer.fs.model.FsAttachment attachment where attachment.claim_id = :claimId";
		Session session = null; 
		List<FsAttachment> list = null;
		try {
			session = HibernateWrapper.getSession().openSession();
			Query q = session.createQuery(query);
			q.setParameter("claimId", claimId);
			list = q.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		FsClaim claim=getClaim(claimId,null);
		String valCode=claim.getFile().getValidatingCompanycode();
		List<PrivacyPermissions> p = PrivacyPermissionsBean.getPrivacyPermissions();
		
		if (list!=null && list.size()>0) {
			RequestStatus rs;
			long requestedId;
			
				requestedId = claim.getFile().getId();
				rs = Producer.getRequestStatus(requestedId, airline);
		
			if(rs != null && rs.equals(RequestStatus.Approved)){
				Producer.censorAttachments(list, valCode, AccessLevelType.req, airline, p);
				
			} else {
				Producer.censorAttachments(list, valCode, AccessLevelType.def, airline, p);
			}
		}
		return list;
	}
	
	public static boolean saveAttachments(List<Integer> attachIDs, FsClaim claim){
		try{
			List<FsAttachment> attachments=getAttachmentsById(attachIDs);
			Set<FsAttachment> toSave=new HashSet();
			for(FsAttachment attach:attachments){
				attach.setClaim_id(claim.getId());
				toSave.add(attach);
			}
			saveAttachments(toSave);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean deleteAttachment(int attachID){
		try{
			List alist=new ArrayList();
			alist.add(attachID);
			List<FsAttachment> attachments=getAttachmentsById(alist);
			Set<FsAttachment> toDelete=new HashSet(attachments);
			deleteAttachments(toDelete);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean deleteAttachments(Set<FsAttachment> attachments){
		Transaction t = null;
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			t = sess.beginTransaction();
			for(FsAttachment attach:attachments){
				sess.delete(attach);
			}
				t.commit();
				
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			if (t != null) {
				try {
					t.rollback();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			return false;
		} finally {

			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}

