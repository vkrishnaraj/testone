package com.bagnet.clients;

import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.utils.Precoder;
import com.bagnet.nettracer.tracing.utils.PrecoderResult;
import com.bagnet.nettracer.tracing.utils.TracerProperties;

public class PrecoderImpl implements Precoder{

	private Precoder target=new com.bagnet.clients.defaul.PrecoderImpl();
	

    public PrecoderImpl() {
        String companyCode = TracerProperties.get("wt.company.code");
        String path = TracerProperties.get(companyCode, "precoder.class.path"); // Use new TP get method
        try {
        Class cls = Class.forName("com.bagnet.clients." + path+".PrecoderImpl");
        Precoder res = (Precoder) cls.newInstance();
        target=res;
        }catch (Exception x) {
                
        }
    	
    }


	@Override
	public PrecoderResult getFaultStationAndLossCode(Incident inc) {
		// TODO Auto-generated method stub
		return target.getFaultStationAndLossCode(inc);
	}

}
