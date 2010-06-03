//package com.bagnet.clients;
//
//
//import java.util.ArrayList;
//
//import org.junit.Test;
//
//import com.bagnet.clients.b6.JetBlueIntegrationWrapper;
//import com.jetblue.schemas._2008._03.framework.baggage.ReservationDetail;
//
//public class B6Test {
//	
//	@Test
//	public void getOldBooking() {
//		
//		System.setProperty("javax.net.ssl.trustStore", "c:\\secure\\ntcerts");
//	    System.setProperty("javax.net.ssl.trustStorePassword", "nettracer1");
//	    System.setProperty("javax.net.ssl.keyStore", "c:\\secure\\ntkeys.ks");
//	    System.setProperty("javax.net.ssl.keyStorePassword", "nettracer1");
//
//		
//		String recordLocator = "NF1HN0";
//    
//		ArrayList<String> errors = new ArrayList<String>();
//		ReservationDetail booking = null;
//		try {
//			JetBlueIntegrationWrapper wrapper = new JetBlueIntegrationWrapper();
//			
//			boolean result = true;
//			result = wrapper.getBookingByKey(recordLocator, null);
//			//result = wrapper.getBookingByKey(null, tagNumber);
//			
//			if (!result) {
//				System.out.println("error");
//			} else {
//				// Get the booking
//				booking = wrapper.getReservationDetail();
//			}
//			
//			if (booking == null) {
//				throw new Exception();
//			} else {
//				System.out.println(booking);
//			}
//			return;
//
//		} catch (Exception e) {
//			System.out.println(e.getStackTrace());
//			e.printStackTrace();
//			return;
//		}
//	}
//	
//	@Test
//	public void getNewBooking() throws Exception {
//		
//		System.setProperty("javax.net.ssl.trustStore", "c:\\secure\\newfiles\\ntcerts");
//	    System.setProperty("javax.net.ssl.trustStorePassword", "nettracer1");
//	
//	    System.setProperty("javax.net.ssl.keyStore", "c:\\secure\\newfiles\\ntkeys.ks");
//	    System.setProperty("javax.net.ssl.keyStorePassword", "nettracer1");
//
//		
//		String recordLocator = "NF1HN0";
//    
//		ArrayList<String> errors = new ArrayList<String>();
//		ReservationDetail booking = null;
//		try {
//			JetBlueIntegrationWrapper wrapper = new JetBlueIntegrationWrapper();
//			
//			boolean result = true;
//			result = wrapper.getBookingByKey(recordLocator, null);
//			//result = wrapper.getBookingByKey(null, tagNumber);
//			
//			if (!result) {
//				System.out.println("error");
//			} else {
//				// Get the booking
//				booking = wrapper.getReservationDetail();
//			}
//			
//			if (booking == null) {
//				throw new Exception();
//			} else {
//				System.out.println(booking);
//			}
//
//			return;
//
//		} catch (Exception e) {
//			System.out.println(e.getStackTrace());
//			e.printStackTrace();
//			throw e;
//		}
//	}
////	
//	@Test
//	public void writeOldComment() {
//		
//		System.setProperty("javax.net.ssl.trustStore", "c:\\secure\\newfiles\\ntcerts");
//	    System.setProperty("javax.net.ssl.trustStorePassword", "nettracer1");
//	
//	    System.setProperty("javax.net.ssl.keyStore", "c:\\secure\\newfiles\\ntkeys.ks");
//	    System.setProperty("javax.net.ssl.keyStorePassword", "nettracer1");
//		
//		String recordLocator = "NF1HN0";
//		ArrayList<String> errors = new ArrayList<String>();
//		ReservationDetail booking = null;
//		try {
//			
//			JetBlueIntegrationWrapper wrapper = new JetBlueIntegrationWrapper();
//			wrapper.writeCommentToPNR(recordLocator, "This is a test comment...");
//
//			for (int i=0; i<60; ++i) {
//				Thread.sleep(1000);
//				System.out.println("Waiting...");
//			}
//			return;
//
//		} catch (Exception e) {
//			System.out.println(e.getStackTrace());
//			e.printStackTrace();
//			return;
//		}
//	}
//
//
//	
//}
