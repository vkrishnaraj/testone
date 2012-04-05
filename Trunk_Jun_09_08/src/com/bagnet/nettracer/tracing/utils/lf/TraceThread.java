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

import javax.naming.Context;

import org.apache.log4j.Logger;

public class TraceThread implements Runnable{
	private static final Logger logger = Logger.getLogger(TraceThread.class);
	
	private ArrayBlockingQueue<Object[]> queue;
	private ThreadContainer container;
	
	public TraceThread(ArrayBlockingQueue queue, ThreadContainer container){
		this.queue = queue;
		this.container = container;
	}
	
	@Override
	public void run() {
		try {
			Context ctx = ConnectionUtil.getInitialContext(PropertyBMO.getValue(PropertyBMO.LF_EJB_SERVER_LOCATION));
			while(true){
				try{
					container.setWaiting(true);
					Object[] o = queue.take();
					container.setStartTime(new Date());
					container.setWaiting(false);
					LFServiceRemote bean = null;
					try{
						bean = (LFServiceRemote)ctx.lookup("tracer/LFServiceBean/remote");
					} catch (Exception e){
						logger.error("unable to connect to service " + PropertyBMO.getValue(PropertyBMO.LF_EJB_SERVER_LOCATION));
					}
					if(bean != null){
						container.setConnectError(false);
						if(o[0] == TraceHandler.TYPE_FOUND){
							bean.traceFoundItem((Long)o[1]);
						} else if (o[0] == TraceHandler.TYPE_LOST){
							bean.traceLostItem((Long)o[1]);
						} else if (o[0] == TraceHandler.TYPE_FOUND_HISTORY_OBJECT){
							List<LFMatchHistory> r = bean.traceFoundItem(((FoundHistoryObject)o[1]).getFound().getId());
							((FoundHistoryObject)o[1]).setHasTraceResults(r != null && r.size() > 0);
						}
					} else {
						//unable to connect to service
						System.out.println("unable to connect");
						queue.put(o);
						container.setConnectError(true);
					}
				} catch (Exception e2){
					e2.printStackTrace();
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
