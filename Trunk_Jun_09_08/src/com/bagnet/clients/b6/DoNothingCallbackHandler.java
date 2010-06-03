//package com.bagnet.clients.b6;
//
//import org.apache.log4j.Logger;
//
//import servicemanager.uddi_6605bbea_dcf6_11dd_9aaf_9cc8f268cf20.PNRService_vs3_0_BP_NT_DEVCallbackHandler;
//
//public class DoNothingCallbackHandler extends
//		PNRService_vs3_0_BP_NT_DEVCallbackHandler {
//	
//	private Logger logger = Logger.getLogger(DoNothingCallbackHandler.class);
//	private boolean complete;
//	
//  public void receiveResultAddComment(
//      com.jetblue.schemas._2008._09.telnet.services.pnr.AddCommentResponseDocument result) {
//  	
//  	logger.info("Result in callback: " + result.getAddCommentResponse());
//  	System.out.println("Result in callback: " + result.getAddCommentResponse());
//  	complete = true;
//  	
//  }
//
//  public void receiveErrorAddComment(java.lang.Exception e) {
//  	logger.error("Web Service Exception", e);
//  }
//  
//  public boolean isComplete() {
//  	return complete;
//  }
//
//}
