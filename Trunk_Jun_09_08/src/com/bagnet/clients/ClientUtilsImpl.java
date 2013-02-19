package com.bagnet.clients;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.bagnet.nettracer.tracing.utils.ClientUtils;
import com.bagnet.nettracer.tracing.utils.TracerProperties;

public class ClientUtilsImpl implements ClientUtils {

	private ClientUtils target=new com.bagnet.clients.defaul.ClientUtilsImpl();
	

    public ClientUtilsImpl() {
        String companyCode = TracerProperties.get("wt.company.code");
        String path = TracerProperties.get(companyCode, "client.utils.class.path"); // Use new TP get method
        try {
        Class cls = Class.forName("com.bagnet.clients." + path+".ClientUtilsImpl");
        ClientUtils res = (ClientUtils) cls.newInstance();
        target=res;
        }catch (Exception x) {
                
        }
    	
    }

	@Override
	public String getReportMethodName(int methodType) {
		return target.getReportMethodName(methodType);
	}


	@Override
	public int getReportMethodType(String methodName) {
		// TODO Auto-generated method stub
		return target.getReportMethodType(methodName);
	}


	@Override
	public List<LabelValueBean> getReportMethodsList(String locale) {
		// TODO Auto-generated method stub
		return target.getReportMethodsList(locale);
	}
}
