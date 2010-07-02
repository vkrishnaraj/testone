package com.bagnet.nettracer.cronjob.utilities;


import org.hibernate.Session;
import org.junit.Test;

import com.bagnet.nettracer.hibernate.*;


public class CronUtilsTest {
	@Test
	public void hourlySqlServerStatusCheckTest(){
		CronUtils CU = new CronUtils("B6");
		CU.hourlySqlServerStatusCheck();
	}
}
