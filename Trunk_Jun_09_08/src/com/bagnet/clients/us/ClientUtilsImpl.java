package com.bagnet.clients.us;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.struts.util.LabelValueBean;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.utils.ClientUtils;

public class ClientUtilsImpl implements ClientUtils {
	
	@Override
	public String getReportMethodName(int methodType) {
		
		switch (methodType) {
			case TracingConstants.REPORT_METHOD_IN_PERSON:
				return TracingConstants.REPORT_METHOD_NAME_IN_PERSON;
			case TracingConstants.REPORT_METHOD_BSO_PHONE:
				return TracingConstants.REPORT_METHOD_NAME_BSO_PHONE;
			case TracingConstants.REPORT_METHOD_CALL_CENTER:
				return TracingConstants.REPORT_METHOD_NAME_CALL_CENTER;
			case TracingConstants.REPORT_METHOD_CBRO_ONLINE:
				return TracingConstants.REPORT_METHOD_NAME_CBRO_ONLINE;
			case TracingConstants.REPORT_METHOD_KIOSK:
				return TracingConstants.REPORT_METHOD_NAME_KIOSK;
			case TracingConstants.REPORT_METHOD_CBRO_CLAIM:
				return TracingConstants.REPORT_METHOD_NAME_CBRO_CLAIM;
			case TracingConstants.REPORT_METHOD_CBRO_PHONE:
				return TracingConstants.REPORT_METHOD_NAME_CBRO_PHONE;
			default:
				return "";
		}

	}
	
	@Override
	public int getReportMethodType(String methodName) {
		int type = 0;
		if (methodName != null && !methodName.trim().isEmpty()) {
			if (methodName.equals(TracingConstants.REPORT_METHOD_NAME_IN_PERSON)) {
				type = TracingConstants.REPORT_METHOD_IN_PERSON;
			} else if (methodName.equals(TracingConstants.REPORT_METHOD_NAME_BSO_PHONE)) {
				type = TracingConstants.REPORT_METHOD_BSO_PHONE;
			} else if (methodName.equals(TracingConstants.REPORT_METHOD_NAME_CALL_CENTER)) {
				type = TracingConstants.REPORT_METHOD_CALL_CENTER;
			} else if (methodName.equals(TracingConstants.REPORT_METHOD_NAME_CBRO_ONLINE)) {
				type = TracingConstants.REPORT_METHOD_CBRO_ONLINE;
			} else if (methodName.equals(TracingConstants.REPORT_METHOD_NAME_KIOSK)) {
				type = TracingConstants.REPORT_METHOD_KIOSK;
			} else if (methodName.equals(TracingConstants.REPORT_METHOD_NAME_CBRO_CLAIM)) {
				type = TracingConstants.REPORT_METHOD_CBRO_CLAIM;
			} else if (methodName.equals(TracingConstants.REPORT_METHOD_NAME_CBRO_PHONE)) {
				type = TracingConstants.REPORT_METHOD_CBRO_PHONE;
			}
		}
		return type;
	}
	
	@Override
	public List<LabelValueBean> getReportMethodsList(String locale) {
		ArrayList<LabelValueBean> result = new ArrayList<LabelValueBean>();
		ResourceBundle resources = ResourceBundle.getBundle("com.bagnet.nettracer.tracing.resources.ApplicationResources", new Locale(locale));
		
		result.add(getLabelValueBean(resources.getString("select.in_person"), String.valueOf(TracingConstants.REPORT_METHOD_IN_PERSON)));
		result.add(getLabelValueBean(resources.getString("select.bsophone"), String.valueOf(TracingConstants.REPORT_METHOD_BSO_PHONE)));
		result.add(getLabelValueBean(resources.getString("select.callcenter"), String.valueOf(TracingConstants.REPORT_METHOD_CALL_CENTER)));
		result.add(getLabelValueBean(resources.getString("select.kiosk"), String.valueOf(TracingConstants.REPORT_METHOD_KIOSK)));
		result.add(getLabelValueBean(resources.getString("select.cbro.claim"), String.valueOf(TracingConstants.REPORT_METHOD_CBRO_CLAIM)));
		result.add(getLabelValueBean(resources.getString("select.online"), String.valueOf(TracingConstants.REPORT_METHOD_CBRO_ONLINE)));
		result.add(getLabelValueBean(resources.getString("select.cbro.phone"), String.valueOf(TracingConstants.REPORT_METHOD_CBRO_PHONE)));
		
		return result;
	}
	
	private LabelValueBean getLabelValueBean(String label, String value) {
		LabelValueBean bean = new LabelValueBean();
		bean.setLabel(label);
		bean.setValue(value);
		return bean;
	}

}
