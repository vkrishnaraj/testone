package com.bagnet.clients.b6;

public class WriteThreadPool implements Runnable {

	private String pnr = "";
	private String comment = "";
	
	public WriteThreadPool(String pnr, String comment) {
		this.pnr = pnr;
		this.comment = comment;
	}
	
	public void run() {
		JetBlueIntegrationWrapper wrapper = new JetBlueIntegrationWrapper();
		wrapper.writeCommentToPNR(pnr, comment);
	}

}
