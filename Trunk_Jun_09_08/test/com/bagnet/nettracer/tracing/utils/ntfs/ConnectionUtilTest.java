package com.bagnet.nettracer.tracing.utils.ntfs;

import java.util.Date;

import org.junit.Test;

import com.bagnet.nettracer.tracing.bmo.PropertyBMO;

public class ConnectionUtilTest {

	@Test
	public void test(){
		PropertyBMO.getValue(PropertyBMO.CENTRAL_FRAUD_SERVER_LOCATION);
		Date start = new Date();
		String s = ConnectionUtil.echoServiceTestClaim("hello world");
		System.out.println(s!=null?s:"null");
		  Date end = new Date();
		  System.out.println(end.getTime() - start.getTime());
	}
}
