package com.bagnet.nettracer.tracing.utils;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

public interface ClientUtils {
	
	public String getReportMethodName(int methodType);
	public int getReportMethodType(String methodName);
	public List<LabelValueBean> getReportMethodsList(String locale);

}
