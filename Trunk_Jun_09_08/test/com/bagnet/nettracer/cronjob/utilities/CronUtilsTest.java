package com.bagnet.nettracer.cronjob.utilities;


import org.hibernate.Session;
import org.junit.Test;

import com.bagnet.nettracer.cronjob.incident.AutoClose;
import com.bagnet.nettracer.hibernate.*;


public class CronUtilsTest {
	@Test
	public void hourlySqlServerStatusCheckTest(){
		CronUtils CU = new CronUtils("B6");
		CU.hourlySqlServerStatusCheck();
	}
	
	@Test
	public void testCloseOHDS(){
		AutoClose ac=new AutoClose("WN");
		ac.autoCloseOHDs();
	}
}
