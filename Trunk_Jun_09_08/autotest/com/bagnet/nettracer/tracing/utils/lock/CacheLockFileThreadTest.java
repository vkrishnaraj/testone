package com.bagnet.nettracer.tracing.utils.lock;

import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;


/**
 * Thread class used for uniting testing multiple clients
 * 
 * @author Loupas
 *
 */
public class CacheLockFileThreadTest implements Runnable{

	AsyncCacheLockFile aclf;
	Incident incident;
	Agent agent;
	QueueItem  ret;
	long wait;
	
	CacheLockFileThreadTest(AsyncCacheLockFile aclf, Incident incident, Agent agent, QueueItem  ret){
		this(aclf, incident, agent, ret, 0);
	}
	CacheLockFileThreadTest(AsyncCacheLockFile aclf, Incident incident, Agent agent, QueueItem  ret, long wait){
		this.aclf = aclf;
		this.incident = incident;
		this.agent = agent;
		this.ret = ret;
		this.wait = wait;
	}
	
	@Override
	public void run() {
		ret.setLockIncidentReturn(aclf.lockIncident(agent, incident));
		try {
			Thread.sleep(wait);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}

}
