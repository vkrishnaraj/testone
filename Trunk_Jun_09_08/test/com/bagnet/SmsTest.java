package com.bagnet;

import org.junit.Test;

import com.bagnet.nettracer.cronjob.utilities.CronUtils;

public class SmsTest {
	@Test
	public void test() {
		CronUtils cu = new CronUtils("US");
		cu.hourlySqlServerStatusCheck();
	}
}
