package com.bagnet.nettracer.tracing.utils.ntfs;

import java.util.LinkedHashSet;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import aero.nettracer.fs.model.File;
import aero.nettracer.fs.model.FsClaim;
import aero.nettracer.fs.model.FsIncident;
import aero.nettracer.fs.model.detection.TraceResponse;
import aero.nettracer.selfservice.fraud.ClaimRemote;

import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.dao.FileDAO;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.utils.ClaimUtils;

public class ConnectionUtil {
	
	  private static final Logger logger = Logger.getLogger(ConnectionUtil.class);
	
	private final static long DEFAULT_TIMEOUT = 1000;
	  
	  static String user     = null;
	  static String password = null;
	  public static String url      = PropertyBMO.getValue(PropertyBMO.CENTRAL_FRAUD_SERVER_LOCATION);
	  public static String service  = PropertyBMO.getValue(PropertyBMO.CENTRAL_FRAUD_SERVICE_NAME);
	  public static String permissions_service = PropertyBMO.getValue(PropertyBMO.CENTRAL_FRAUD_PERMISSION_SERVICE_NAME);

	
	  static public Context getInitialContext() throws NamingException {
		  
	    Properties p = new Properties();
	    p.put(Context.INITIAL_CONTEXT_FACTORY,
	          "org.jnp.interfaces.NamingContextFactory");
	    p.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces"); 
	    p.put(Context.PROVIDER_URL, url);
	    p.put("jnp.socketFactory", "org.jnp.interfaces.TimedSocketFactory");
	    
	    if (user != null) {
	      System.out.println ("user: " + user);
	      p.put(Context.SECURITY_PRINCIPAL, user);
	      if (password == null) 
	        password = "";
	      p.put(Context.SECURITY_CREDENTIALS, password);
	    } 
	    return new InitialContext(p);
	  }
	  
	  public static String echoServiceTestClaim(String echo){
		 Context ctx = null;
		String ret = null;
		 try {
			ctx = getInitialContext();
		  if (ctx != null) {
			  ClaimRemote remote = (ClaimRemote) getRemoteEjb(ctx, service);
			  if(remote != null){
				  ret = remote.echoTest(echo);
			  }
			  ctx.close();
		  }
		  
	  } catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  return ret;
	  }
	  
	  public static Object getRemoteEjb(Context ctx, String remote){
		  return getRemoteEjb(ctx, remote, DEFAULT_TIMEOUT);
	  }
	  
	  public static Object getRemoteEjb(Context ctx, String remote, long timeout){
		  ConnectionThread conn = new ConnectionThread(ctx, remote);
		  Thread t = new Thread(conn);
		  t.start();

		  long interval = 50;
		  long time = 0;
		  while(time < timeout){
			  if (conn.getConnection() != null){
				  System.out.println("connection time: " + time);
				  return conn.getConnection();
			  }
			  try {
				  Thread.sleep(interval);
			  } catch (InterruptedException e) {
				  // TODO Auto-generated catch block
				  e.printStackTrace();
			  }
			  time += interval;
		  }
		  return null;
	  }
	  
	  public static long insertFile(File file) throws NamingException {
		  Context ctx = getInitialContext();
		  long id = -1;
		  if (ctx != null) {
			  ClaimRemote remote = (ClaimRemote) getRemoteEjb(ctx, service);
			  if(remote != null){
			  id = remote.insertFile(file);
			  }
			  ctx.close();
		  }
		  return id;
	  }
	  
	  public static TraceResponse submitClaim(long fileId, boolean primary, boolean hasViewResultsPermission) {
			if (fileId <= 0) {
				return null;
			}
			TraceResponse results = null;
			try {
				Context ctx = ConnectionUtil.getInitialContext();
				ClaimRemote remote = (ClaimRemote) getRemoteEjb(ctx,service);
				
				if (remote != null) {
					int wait = 6;
					try {
						wait = PropertyBMO.getValueAsInt(PropertyBMO.CENTRAL_FRAUD_CHECK_TIMEOUT);
					} catch (Exception e) {
						//
					}
					results = remote.traceFile(fileId, wait, primary, hasViewResultsPermission);
				}
				ctx.close();
			} catch (Exception e) {
				logger.error(e);	
			}
			return results;
		}
	  
	public static File createAndSubmitForTracing(Incident iDTO, Agent user, HttpServletRequest request, boolean hasViewResultsPermission) {
		request.removeAttribute("fraudStatus");
		File file = null;
		try {
			file = new File();
			file.setValidatingCompanycode(user.getCompanycode_ID());
			FsIncident fsIncident = ClaimUtils.getFsIncident(iDTO, user);
			fsIncident.setFile(file);
			file.setIncident(fsIncident);
			FileDAO.saveFile(file, true);
			long remoteFileId = ConnectionUtil.insertFile(file);
			file = FileDAO.loadFile(file.getId());
			file.setSwapId(remoteFileId);
			FileDAO.saveFile(file, false);
			if (remoteFileId > 0) {
				TraceResponse results = ConnectionUtil.submitClaim(remoteFileId, true, hasViewResultsPermission);
				if (results != null) {
					request.getSession().setAttribute("results", results.getMatchHistory());
					request.getSession().setAttribute("traceResponse", results);
					String status = "no_fraud";
					if (results.getThreatLevel() == TraceResponse.THREAT_LEVEL_RED) {
						status = "known_fraud";
					} else if (results.getThreatLevel() == TraceResponse.THREAT_LEVEL_YELLOW) {
						status = "suspected_fraud";
					} else if (results.getThreatLevel() == TraceResponse.THREAT_LEVEL_ORANGE) {
						status = "suspected_fraud";
					}
					request.setAttribute("fraudStatus", status);
				}
			}

		} catch (Exception e) {
			logger.error(e);
		}
		return file;

	}
	  
	  // DO NOT USE - NEED TO CLOSE CONTEXT
//	  public static ClaimRemote getClaimRemote() {
//		  try {
//			  Context ctx = ConnectionUtil.getInitialContext();
//			  ClaimRemote o = (ClaimRemote) ctx.lookup("NTServices_1_0/ClaimBean/remote");
//			  return o;
//		  } catch (NamingException ne) {
//			  ne.printStackTrace();
//			  return null;
//		  }
//	  }
}
