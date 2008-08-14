/*
 * Created on Nov 8, 2004
 *
 * Administrator
 */
package com.bagnet.nettracer.cronjob;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * @author Administrator
 * 
 * create date - Nov 8, 2004
 */
public class NettracerCron {
	public static void main(String[] args) {
		AbstractApplicationContext ctx = new ClassPathXmlApplicationContext("com/bagnet/nettracer/cronjob/applicationContext.xml");
		
		ctx.registerShutdownHook();
	}
}