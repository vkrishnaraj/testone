package com.bagnet.nettracer.tracing.utils.ntfs;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import aero.nettracer.fs.model.File;
import aero.nettracer.fs.model.FsIncident;
import aero.nettracer.fs.model.detection.MatchHistory;
import aero.nettracer.fs.model.detection.TraceResponse;
import aero.nettracer.selfservice.fraud.ClaimRemote;

import com.bagnet.nettracer.tracing.actions.RequestInfoAction;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.dao.FileDAO;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.utils.ClaimUtils;

public class ConnectionUtil {
	
	  private static final Logger logger = Logger.getLogger(ConnectionUtil.class);
	
	  static String user     = null;
	  static String password = null;
	  static String url      = PropertyBMO.getValue(PropertyBMO.CENTRAL_FRAUD_SERVER_LOCATION);

	
	  static public Context getInitialContext() throws NamingException {
	    Properties p = new Properties();
	    p.put(Context.INITIAL_CONTEXT_FACTORY,
	          "org.jnp.interfaces.NamingContextFactory");
	    p.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces"); 
	    p.put(Context.PROVIDER_URL, url);
	    if (user != null) {
	      System.out.println ("user: " + user);
	      p.put(Context.SECURITY_PRINCIPAL, user);
	      if (password == null) 
	        password = "";
	      p.put(Context.SECURITY_CREDENTIALS, password);
	    } 
	    return new InitialContext(p);
	  }
	  
	  public static long insertFile(File file) throws NamingException {
		  Context ctx = getInitialContext();
		  long id = -1;
		  if (ctx != null) {
			  ClaimRemote remote = (ClaimRemote) ctx.lookup("NTServices_1_0/ClaimBean/remote");
			  id = remote.insertFile(file);
			  ctx.close();
		  }
		  return id;
	  }
	  
	  public static TraceResponse submitClaim(long fileId, boolean primary) {
			if (fileId <= 0) {
				return null;
			}
			TraceResponse results = null;
			try {
				Context ctx = ConnectionUtil.getInitialContext();
				ClaimRemote remote = (ClaimRemote) ctx.lookup("NTServices_1_0/ClaimBean/remote");
				
				if (remote != null) {
					int wait = 6;
					try {
						wait = PropertyBMO.getValueAsInt(PropertyBMO.CENTRAL_FRAUD_CHECK_TIMEOUT);
					} catch (Exception e) {
						//
					}
					results = remote.traceFile(fileId, wait, primary);
				}
				ctx.close();
			} catch (NamingException e) {
				logger.error(e);	
			}
			return results;
		}
	  
	public static File createAndSubmitForTracing(Incident iDTO, Agent user, HttpServletRequest request) {
		request.removeAttribute("fraudStatus");
		File file = null;
		try {
			file = new File();
			FsIncident fsIncident = ClaimUtils.getFsIncident(iDTO, user);
			fsIncident.setFile(file);
			file.setIncident(fsIncident);
			long remoteFileId = ConnectionUtil.insertFile(file);
			file.setSwapId(remoteFileId);
			FileDAO.saveFile(file);
			if (remoteFileId > 0) {
				TraceResponse results = ConnectionUtil.submitClaim(remoteFileId, true);
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
