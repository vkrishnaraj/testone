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

public class SecondTraceThread implements Runnable{
	private static final Logger logger = Logger.getLogger(TraceThread.class);
	
	private ArrayBlockingQueue<Object[]> queue;
	private ThreadContainer container;
	
	public SecondTraceThread(ArrayBlockingQueue queue, ThreadContainer container){
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
					container.setDead(false);
					Object[] o = queue.take();
					container.setStartTime(new Date());
					container.setWaiting(false);
					LFServiceRemote bean = null;
					try{
						bean = (LFServiceRemote)ctx.lookup(PropertyBMO.getValue(PropertyBMO.LF_EJB_SERVER_NAME));
					} catch (Exception e){
						logger.error("unable to connect to LFC Secondary Trace service " + PropertyBMO.getValue(PropertyBMO.LF_EJB_SERVER_NAME));
					}
					if(bean != null){
						container.setConnectError(false);
						if(o[0] == TraceHandler.TYPE_FOUND){
							bean.traceFoundItemSecondary((Long)o[1]);
						} else if (o[0] == TraceHandler.TYPE_LOST){
							bean.traceLostItemSecondary((Long)o[1]);
						} else if (o[0] == TraceHandler.TYPE_FOUND_HISTORY_OBJECT){
							List<LFMatchHistory> r = bean.traceFoundItemSecondary(((FoundHistoryObject)o[1]).getFound().getId());
							((FoundHistoryObject)o[1]).setHasTraceResults(r != null && r.size() > 0);
						}
					} else {
						//unable to connect to service
						System.out.println("unable to connect");
						queue.put(o);
						container.setConnectError(true);
						Thread.sleep(30000);
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
