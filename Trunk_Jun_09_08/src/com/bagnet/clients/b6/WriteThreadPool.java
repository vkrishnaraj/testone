package com.bagnet.clients.b6;

import java.util.ArrayList;

import com.bagnet.nettracer.tracing.utils.StringUtils;

public class WriteThreadPool implements Runnable {

	private String pnr = "";
	private String comment = "";
	
	public WriteThreadPool(String pnr, String comment) {
		this.pnr = pnr;
		this.comment = comment;
	}
	
	public void run() {
		JetBlueIntegrationWrapper wrapper = new JetBlueIntegrationWrapper();
		ArrayList<String> list = StringUtils.splitOnWordBreak(comment, 45);
		for (String str: list) {
			wrapper.writeCommentToPNR(pnr, str);
		}
	}

}
