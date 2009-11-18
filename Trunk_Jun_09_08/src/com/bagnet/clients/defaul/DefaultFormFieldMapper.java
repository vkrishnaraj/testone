package com.bagnet.clients.defaul;

import org.apache.commons.lang.StringUtils;

public class DefaultFormFieldMapper {
	
	public enum NetTracerField {
		PAX_FIRST_NAME(20, ""),
		PAX_MIDDLE_NAME(20, ""),
		PAX_LAST_NAME(20, ""),
		CLAIM_CHECK_NUMBER(10, ""),
		AIRLINE(2, ""),
		FLIGHT(4, ""),
		AIRPORT(3, ""),
		ADDRESS1(50, ""),
		ADDRESS2(50, ""),
		ADDR_CITY(50, ""),
		ADDR_PROVINCE(100, ""),
		ADDR_ZIP(10, ""),
		ADDR_PHONE(50, ""),
		ADDR_EMAIL(100, ""),
		ADDR_COUNTRY(3, ""),
		ADDR_STATE(2, ""),
		OHD_ADDR_EMAIL(50, ""),
		OHD_ADDR_PHONE(25, ""),
		MEMBERSHIP_STATUS(20, ""),
		MEMBERSHIP_NUM(20, ""),
		MEMBERSHIP_AIRLINE(3, "");
		
		private int maxLength;
		private String defaultValue;
		
		NetTracerField(int maxLength, String defaultValue) {
			this.maxLength = maxLength;
			this.defaultValue = defaultValue;
		}
		
		/* (non-Javadoc)
		 * @see com.bagnet.clients.defaul.FieldMapperEnum#maxLength()
		 */
		public int maxLength() {
			return maxLength;
		}
		
		/* (non-Javadoc)
		 * @see com.bagnet.clients.defaul.FieldMapperEnum#defaultValue()
		 */
		public String defaultValue() {
			return defaultValue;
		}
	}
	
	public String mapStringAndTrim(NetTracerField field, String value){
		if(value == null || value.trim().length() < 1) {
			return field.defaultValue();
		}
		else {
			return StringUtils.substring(value.trim(), 0, field.maxLength());
		}
	}
	
	public String mapString(NetTracerField field, String value){
		if(value == null || value.trim().length() < 1) {
			return field.defaultValue();
		}
		else {
			String interim = value;
			if (field.name().equals(NetTracerField.FLIGHT.name())) {
				while (interim.startsWith("0")) {
					if (interim.length() > 1) {
						interim = interim.substring(1);
					} else {
						return "";
					}
				}

				return interim;
			} else {
				return StringUtils.substring(value, 0, field.maxLength());
			}
		}
	}

}
