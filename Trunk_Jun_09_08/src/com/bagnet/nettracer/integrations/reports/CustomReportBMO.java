package com.bagnet.nettracer.integrations.reports;

import javax.servlet.http.HttpServletRequest;

import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.dto.StatReportDTO;

public interface CustomReportBMO {
	public String createCustomReport(StatReportDTO srDTO, HttpServletRequest request, Agent user, String rootpath);
}
