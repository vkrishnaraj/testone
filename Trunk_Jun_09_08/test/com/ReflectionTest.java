package com;

import org.junit.Test;

import com.bagnet.nettracer.tracing.db.Address;
import com.bagnet.nettracer.tracing.db.Passenger;
import com.bagnet.nettracer.tracing.utils.StringTemplateProcessor;

public class ReflectionTest {
	
	@Test
	public void runTest() throws Exception {
		Passenger pax = new Passenger();
		pax.setFirstname("B");
		pax.setMiddlename("E");
		pax.setLastname("S");
		
		Address add = new Address();
		add.setAddress1("TEST");

		String template = "'{firstname} {middlename} {lastname} {address1} {address2}'";

		StringTemplateProcessor a = new StringTemplateProcessor();
		try {
			a.addClass(pax);
			a.addClass(add);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(a.fillValues(template));
		

	}
	
}
