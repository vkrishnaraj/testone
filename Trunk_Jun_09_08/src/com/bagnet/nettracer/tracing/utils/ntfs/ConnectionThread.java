package com.bagnet.nettracer.tracing.utils.ntfs;

import javax.naming.Context;
import javax.naming.NamingException;

public class ConnectionThread implements Runnable{
	
	Context ctx;
	String service;
	Object connection;

	public ConnectionThread(Context ctx, String service){
		this.ctx = ctx;
		this.service = service;
		this.connection = null;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			this.connection = ctx.lookup(this.service);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Object getConnection() {
		return connection;
	}

}
