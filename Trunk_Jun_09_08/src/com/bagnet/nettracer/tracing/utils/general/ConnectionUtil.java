package com.bagnet.nettracer.tracing.utils.general;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.bagnet.nettracer.tracing.utils.lf.TraceHandler;
import com.bagnet.nettracer.tracing.utils.ntfs.ConnectionThread;

public class ConnectionUtil {
	private static final Logger logger = Logger.getLogger(TraceHandler.class);

	private final static long DEFAULT_TIMEOUT = 10000;

	static String user     = null;
	static String password = null;

	
	static public Context getInitialContext(String url) throws NamingException {
		Hashtable p = new Hashtable();
		p.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces"); 
		return new InitialContext(p);
	}
	
	  public static Object getRemoteEjb(Context ctx, String remote){
		  return getRemoteEjb(ctx, remote,DEFAULT_TIMEOUT);
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
	
}
