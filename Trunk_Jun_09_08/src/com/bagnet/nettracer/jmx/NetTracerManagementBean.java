package com.bagnet.nettracer.jmx;

import com.bagnet.nettracer.cronjob.utilities.CronUtils;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.utils.SpringUtils;
import com.bagnet.nettracer.tracing.utils.TracerProperties;

public class NetTracerManagementBean {

	public void reloadDatabaseProperties() {
		PropertyBMO.resetCache();
	}
	
	public void reloadTracerFileProperties() {
		TracerProperties.reloadProperties();
	}
	
	public void sendBillingReport(String companyCode) {
		CronUtils u = new CronUtils(companyCode);
		u.emailPreviousMonthsBillingReport();
	}

	public int getSharesQueueCount(){
		try{
			return SpringUtils.getSharesIntegrationHandler().getCurrentQueueSize();
		} catch (Exception e){
			//This is a usair implementation only
			return -1;
		}
	}
	
	public int getSharesQueueMaxCount(){
		try{
			return SpringUtils.getSharesIntegrationHandler().getMaxQueueSize();
		} catch (Exception e){
			//This is a usair implementation only
			return -1;
		}
	}
	
}
