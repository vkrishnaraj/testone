package com.bagnet.nettracer.tracing.utils.general;

import static org.junit.Assert.*;

import org.junit.Test;

public class LoggerTest {
	@Test
	public void logGeneralTest(){
		assertTrue(Logger.logGeneral("test1234", "test log", 0));
	}
	
	@Test
	public void logLFTest(){
		assertTrue(Logger.logLF("test1234", "test log", 1589));
	}
}
