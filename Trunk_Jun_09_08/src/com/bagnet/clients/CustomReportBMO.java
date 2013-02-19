/*
 * Created on Jul 15, 2004
 *
 * Administrator
 */
package com.bagnet.clients;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

//import com.bagnet.clients.us.CustomReportBMO;
import com.bagnet.nettracer.reporting.ReportingConstants;
import com.bagnet.nettracer.tracing.bmo.ReportBMO;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.dto.StatReportDTO;
import com.bagnet.nettracer.tracing.utils.Precoder;
import com.bagnet.nettracer.tracing.utils.TracerProperties;

/**
 * @author Administrator
 * 
 * create date - Jul 15, 2004
 */
public class CustomReportBMO implements
		com.bagnet.nettracer.integrations.reports.CustomReportBMO {
	private com.bagnet.nettracer.integrations.reports.CustomReportBMO  target=new com.bagnet.clients.defaul.CustomReportBMO();
	
    public CustomReportBMO() {
        String companyCode = TracerProperties.get("wt.company.code");
        String path = TracerProperties.get(companyCode, "report.class.path"); // Use new TP get method
        try {
        Class cls = Class.forName("com.bagnet.clients." + path+".CustomReportBMO");
        com.bagnet.nettracer.integrations.reports.CustomReportBMO res = (com.bagnet.nettracer.integrations.reports.CustomReportBMO) cls.newInstance();
        target=res;
        }catch (Exception x) {
                
        }
    	
    }

	@Override
	public String createCustomReport(StatReportDTO srDTO,
			HttpServletRequest request, Agent user, String rootpath) {
		// TODO Auto-generated method stub
		return target.createCustomReport(srDTO, request, user, rootpath);
	}
}