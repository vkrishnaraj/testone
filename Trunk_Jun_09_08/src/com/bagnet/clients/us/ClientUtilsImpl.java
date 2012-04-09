package com.bagnet.clients.us;

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

}
