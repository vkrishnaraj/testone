package com.bagnet.nettracer.tracing.utils.lf;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

import aero.nettracer.lf.services.LFServiceRemote;

import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.db.lf.detection.LFMatchHistory;
import com.bagnet.nettracer.tracing.history.FoundHistoryObject;
import com.bagnet.nettracer.tracing.utils.general.ConnectionUtil;

import javax.naming.Context;
import javax.naming.NamingException;

public class TraceThread implements Runnable{
	
	private ArrayBlockingQueue<FoundHistoryObject> queue;
	
	public TraceThread(ArrayBlockingQueue queue){
		this.queue = queue;
	}
	
	@Override
	public void run() {
		try {
			Context ctx = ConnectionUtil.getInitialContext(PropertyBMO.LF_EJB_SERVER_LOCATION);
//			Context ctx = ConnectionUtil.getInitialContext("jnp://192.168.2.145:1399");
			LFServiceRemote bean = (LFServiceRemote) ConnectionUtil.getRemoteEjb(ctx, "tracer/LFServiceBean/remote");
			if(bean == null){
				throw new Exception();//TODO create connection exception
			}
			while(true){
				FoundHistoryObject f = queue.take();
				List<LFMatchHistory> r = bean.traceFoundItem(f.getFound().getId());
				f.setHasTraceResults(r != null && r.size() > 0);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
