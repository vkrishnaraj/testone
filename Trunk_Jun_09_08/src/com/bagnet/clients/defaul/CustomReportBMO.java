/*
 * Created on Jul 15, 2004
 *
 * Administrator
 */
package com.bagnet.clients.defaul;

import javax.servlet.http.HttpServletRequest;

import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.dto.StatReportDTO;

/**
 * @author Administrator
 * 
 * create date - Jul 15, 2004
 */
public class CustomReportBMO implements
		com.bagnet.nettracer.integrations.reports.CustomReportBMO {

	public String createCustomReport(StatReportDTO srDTO,
			HttpServletRequest request, Agent user, String rootpath) {

		return null;
	}

}