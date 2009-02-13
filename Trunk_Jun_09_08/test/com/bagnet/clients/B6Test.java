package com.bagnet.clients;


import java.util.ArrayList;

import org.junit.Test;

import com.bagnet.clients.b6.JetBlueIntegrationWrapper;
import com.bagnet.clients.b6.ReservationIntegrationImpl;
import com.bagnet.clients.b6.WriteThreadPool;
import com.jetblue.schemas._2008._03.framework.baggage.ReservationDetail;

public class B6Test {
	
	@Test
	public void getBooking() {
		
		System.setProperty("javax.net.ssl.trustStore", "c:\\secure\\ntcerts");
    System.setProperty("javax.net.ssl.trustStorePassword", "nettracer1");

    System.setProperty("javax.net.ssl.keyStore", "c:\\secure\\ntkeys.ks");
    System.setProperty("javax.net.ssl.keyStorePassword", "nettracer1");

		
		String recordLocator = "S364AA";
    //String tagNumber = "0279955858";
    String tagNumber = "0279660953";
    
		ArrayList<String> errors = new ArrayList<String>();
		ReservationDetail booking = null;
		try {
			JetBlueIntegrationWrapper wrapper = new JetBlueIntegrationWrapper();
			
			boolean result = true;
			//result = wrapper.getBookingByKey(recordLocator, null);
			result = wrapper.getBookingByKey(null, tagNumber);
			
			if (!result) {
				System.out.println("error");
			} else {
				// Get the booking
				booking = wrapper.getReservationDetail();
			}
			
			System.out.println(booking);
			System.out.println(booking);

			return;

		} catch (Exception e) {
			System.out.println(e.getStackTrace());
			e.printStackTrace();
			return;
		}
	}
	
	//@Test
	public void writeComment() {
		
		System.setProperty("javax.net.ssl.trustStore", "c:\\secure\\ntcerts");
    System.setProperty("javax.net.ssl.trustStorePassword", "nettracer1");

    System.setProperty("javax.net.ssl.keyStore", "c:\\secure\\ntkeys.ks");
    System.setProperty("javax.net.ssl.keyStorePassword", "nettracer1");

		
		String recordLocator = "S364AA";
		ArrayList<String> errors = new ArrayList<String>();
		ReservationDetail booking = null;
		try {
			/*
			JetBlueIntegrationWrapper wrapper = new JetBlueIntegrationWrapper();
			
			wrapper.writeCommentToPNR(recordLocator, "This is the final test ATLB6000002");
			*/
			ReservationIntegrationImpl resInt = new ReservationIntegrationImpl();
			resInt.writeCommentToPNR("This is my test comment.", recordLocator);
			System.out.println("End of calling method");
			
			for (int i=0; i<60; ++i) {
				Thread.sleep(1000);
				System.out.println("Waiting...");
			}
			return;

		} catch (Exception e) {
			System.out.println(e.getStackTrace());
			e.printStackTrace();
			return;
		}
	}


	
}
