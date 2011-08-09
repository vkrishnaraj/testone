package aero.nettracer.fs.utilities;

import aero.nettracer.selfservice.fraud.ClaimBean;

public class DataRetentionThread implements Runnable{

	private static final long INTERVAL = 86400000; //24 hours 
	
	@Override
	public void run() {
		while(true){
			try{	
				ClaimBean bean = new ClaimBean();
				bean.deleteOldFiles();
				Thread.sleep(INTERVAL);
			} catch (Exception e){
				e.printStackTrace();
			}
		}
	}

}
