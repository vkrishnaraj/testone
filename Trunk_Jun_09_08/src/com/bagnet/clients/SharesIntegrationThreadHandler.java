package com.bagnet.clients;

import com.bagnet.nettracer.tracing.utils.TracerProperties;
import com.bagnet.nettracer.tracing.utils.general.ThreadHandler;



public class SharesIntegrationThreadHandler implements ThreadHandler{
private ThreadHandler  target=new com.bagnet.clients.defaul.SharesIntegrationThreadHandler();
	
    public SharesIntegrationThreadHandler() {
        String companyCode = TracerProperties.get("wt.company.code");
        String path = TracerProperties.get(companyCode, "shares.class.path"); // Use new TP get method
        try {
        Class cls = Class.forName("com.bagnet.clients." + path+".SharesIntegrationThreadHandler");
        ThreadHandler res = (ThreadHandler) cls.newInstance();
        target=res;
        }catch (Exception x) {
                
        }
    	
    }

	@Override
	public int getCurrentQueueSize() {
		// TODO Auto-generated method stub
		return target.getCurrentQueueSize();
	}

	@Override
	public int getMaxQueueSize() {
		// TODO Auto-generated method stub
		return target.getMaxQueueSize();
	}

	
	
}
