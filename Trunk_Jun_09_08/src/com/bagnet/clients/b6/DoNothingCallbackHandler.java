package com.bagnet.clients.b6;

import org.apache.log4j.Logger;

import servicemanager.uddi_6605bbea_dcf6_11dd_9aaf_9cc8f268cf20.PNRService_vs3_0_BP_NT_DEVCallbackHandler;

public class DoNothingCallbackHandler extends
		PNRService_vs3_0_BP_NT_DEVCallbackHandler {
	
	private Logger logger = Logger.getLogger(DoNothingCallbackHandler.class);
	private com.jetblue.schemas._2008._09.telnet.services.pnr.AddCommentResponseDocument result;

	public DoNothingCallbackHandler() {
	}

	public DoNothingCallbackHandler(Object clientData) {

		super(clientData);
	}
	
  public void receiveResultAddComment(
      com.jetblue.schemas._2008._09.telnet.services.pnr.AddCommentResponseDocument result) {
  }

  public void receiveErrorAddComment(java.lang.Exception e) {
  	logger.error("Web Service Exception", e);
  }

}
