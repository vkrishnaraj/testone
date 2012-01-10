package com.bagnet.nettracer.tracing.history;

import javax.servlet.http.HttpSession;

public class HistoryContainerFactory {
	
	public static HistoryContainer getHistoryContainer(HttpSession session) {
		HistoryContainer hc = new HistoryContainer();
		session.setAttribute("historyContainer", hc);
		return hc;
	}

}
