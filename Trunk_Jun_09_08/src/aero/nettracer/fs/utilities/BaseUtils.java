package aero.nettracer.fs.utilities;

import aero.nettracer.fs.model.Phone;

public class BaseUtils {

	
	public static Phone simplifyPhone(String phone) {
		String numPhone = removeNonNumeric(phone);
		
		int length = numPhone.length();
		
		if (length == 0) {
			return null;
		} 

		Phone p = new Phone();
		
		
		if (length == 10) {
			p.setPhoneNumber(phone);
		} else if (length == 11) {
			p.setPhoneNumber(phone.substring(1));
		} else {
			p.setPhoneNumber(phone);
		}
		
		return p;
	}
	
	public static String removeNonNumeric(String s) {
		if (s == null) return s;
		StringBuffer sb = new StringBuffer(100);
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (Character.isDigit(c)) {
				sb.append(c);
			}
		}
		return sb.toString();
	}

}
