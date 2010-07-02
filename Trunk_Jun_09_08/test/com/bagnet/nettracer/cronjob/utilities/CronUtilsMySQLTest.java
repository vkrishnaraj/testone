package com.bagnet.nettracer.cronjob.utilities;

import org.hibernate.Session;
import org.junit.Test;

import com.bagnet.nettracer.hibernate.HibernateWrapper;

public class CronUtilsMySQLTest {
	@Test
	public void hourlySqlServerStatusCheckTest(){
		CronUtilsMySQL CU = new CronUtilsMySQL("B6");
		CU.hourlySqlServerStatusCheck();
	}
	
}
