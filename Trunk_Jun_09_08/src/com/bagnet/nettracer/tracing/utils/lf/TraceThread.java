package com.bagnet.nettracer.tracing.utils.lf;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

import aero.nettracer.lf.services.LFServiceRemote;

import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.db.lf.detection.LFMatchHistory;
import com.bagnet.nettracer.tracing.history.FoundHistoryObject;
import com.bagnet.nettracer.tracing.utils.general.ConnectionUtil;
import com.bagnet.nettracer.tracing.utils.general.ThreadContainer;
import com.bagnet.nettracer.tracing.utils.general.ThreadMonitor;

import javax.naming.Context;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

public class TraceThread implements Runnable{
	private static final Logger logger = Logger.getLogger(TraceThread.class);
	
	private ArrayBlockingQueue<FoundHistoryObject> queue;
	private ThreadContainer container;
	
	public TraceThread(ArrayBlockingQueue queue, ThreadContainer container){
		this.queue = queue;
		this.container = container;
	}
	
	@Override
	public void run() {
		try {
			Context ctx = ConnectionUtil.getInitialContext(PropertyBMO.LF_EJB_SERVER_LOCATION);
//			Context ctx = ConnectionUtil.getInitialContext("jnp://192.168.2.145:1399");
//			LFServiceRemote bean = (LFServiceRemote) ConnectionUtil.getRemoteEjb(ctx, "tracer/LFServiceBean/remote");
//			if(bean == null){
//				throw new RemoteConnectionException("unable to connect: " + PropertyBMO.LF_EJB_SERVER_LOCATION);
//			}
			while(true){
				container.setWaiting(true);
				FoundHistoryObject f = queue.take();
				container.setStartTime(new Date());
				container.setWaiting(false);
				LFServiceRemote bean = null;
				try{
					bean = (LFServiceRemote)ctx.lookup("tracer/LFServiceBean/remote");
				} catch (Exception e){
					logger.error("unable to connect to service " + PropertyBMO.LF_EJB_SERVER_LOCATION);
				}
				if(bean != null){
					List<LFMatchHistory> r = bean.traceFoundItem(f.getFound().getId());
					f.setHasTraceResults(r != null && r.size() > 0);
				} else {
					//unable to connect to service
					System.out.println("unable to connect");
					queue.put(f);
					//TODO email
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
