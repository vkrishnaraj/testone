package com.bagnet.nettracer.tracing.utils.ntfs;

import java.util.Hashtable;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.jboss.ejb.client.ContextSelector;
import org.jboss.ejb.client.EJBClientConfiguration;
import org.jboss.ejb.client.EJBClientContext;
import org.jboss.ejb.client.PropertiesBasedEJBClientConfiguration;
import org.jboss.ejb.client.remoting.ConfigBasedEJBClientContextSelector;

import aero.nettracer.fs.model.File;
import aero.nettracer.fs.model.FsIncident;
import aero.nettracer.fs.model.detection.TraceResponse;
import aero.nettracer.fs.utilities.TransportMapper;
import aero.nettracer.selfservice.fraud.client.ClaimClientRemote;

import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.dao.FileDAO;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.utils.ClaimUtils;

public class ConnectionUtil {

	private static final Logger logger = Logger.getLogger(ConnectionUtil.class);

	private final static long DEFAULT_TIMEOUT = 1000;

	static public Context getInitialContext() throws NamingException {
		Properties props = new Properties();
	    props.put("endpoint.name","endpoint");
	    props.put("remote.connectionprovider.create.options.org.xnio.Options.SSL_ENABLED","false");
	    props.put("remote.connections","default");
	    props.put("remote.connection.default.host","10.41.103.66");
	    props.put("remote.connection.default.port","4747");
	    props.put("remote.connection.default.connect.options.org.xnio.Options.SASL_POLICY_NOANONYMOUS","false");
	    EJBClientConfiguration ejbClientConfiguration = new PropertiesBasedEJBClientConfiguration(props);
	    ContextSelector<EJBClientContext> ejbClientContextSelector = new ConfigBasedEJBClientContextSelector(ejbClientConfiguration);
	    EJBClientContext.setSelector(ejbClientContextSelector);
	 
	    Properties p = new Properties();
		p.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
		p.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
		p.put(Context.PROVIDER_URL, PropertyBMO.getValue(PropertyBMO.CENTRAL_FRAUD_SERVER_LOCATION));
		p.put("jboss.naming.client.ejb.context", "true");
		p.put(Context.SECURITY_PRINCIPAL, "hudson");
		p.put(Context.SECURITY_CREDENTIALS, "nettracer");
		return new InitialContext(p);

	}

	public static String echoServiceTestClaim(String echo){
		Context ctx = null;
		String ret = null;
		try {
			ctx = getInitialContext();
			if (ctx != null) {
				ClaimClientRemote remote = (ClaimClientRemote) getRemoteEjb(ctx, PropertyBMO.getValue(PropertyBMO.CENTRAL_FRAUD_SERVICE_NAME));
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
		long timeout = PropertyBMO.getValueAsInt(PropertyBMO.CENTRAL_FRAUD_TIMEOUT);
		return getRemoteEjb(ctx, remote, timeout!=0?timeout:DEFAULT_TIMEOUT);
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
			ClaimClientRemote remote = (ClaimClientRemote) getRemoteEjb(ctx, PropertyBMO.getValue(PropertyBMO.CENTRAL_FRAUD_SERVICE_NAME));
			if(remote != null){
				id = remote.insertFile(TransportMapper.map(file));
			}
			ctx.close();
		}
		return id;
	}

	public static TraceResponse submitClaim(long fileId, boolean primary, boolean hasViewResultsPermission) {
		return submitClaim(fileId, primary, hasViewResultsPermission, PropertyBMO.getValueAsInt(PropertyBMO.CENTRAL_FRAUD_CHECK_TIMEOUT));
	}
	  
	public static TraceResponse submitClaim(long fileId, boolean primary, boolean hasViewResultsPermission, int wait) {
		if (fileId <= 0) {
			return null;
		}
		TraceResponse results = null;
		try {
			Context ctx = ConnectionUtil.getInitialContext();
			ClaimClientRemote remote = (ClaimClientRemote) getRemoteEjb(ctx,PropertyBMO.getValue(PropertyBMO.CENTRAL_FRAUD_SERVICE_NAME));

			if (remote != null) {
				results = TransportMapper.map(remote.traceFile(fileId, wait, primary, hasViewResultsPermission));
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

	public static File getFsFile(long id, String companycode){
		File file = null;
		try{
			Context ctx = getInitialContext();

			if (ctx != null) {
				ClaimClientRemote remote = (ClaimClientRemote) getRemoteEjb(ctx, PropertyBMO.getValue(PropertyBMO.CENTRAL_FRAUD_SERVICE_NAME));
				if(remote != null){
					file = TransportMapper.map(remote.getFile(id, companycode));
				}
				try {
					ctx.close();
				} catch (NamingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (Exception e){
			e.printStackTrace();
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
