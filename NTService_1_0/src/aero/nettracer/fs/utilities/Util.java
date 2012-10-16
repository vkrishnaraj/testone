package aero.nettracer.fs.utilities;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;

import aero.nettracer.fs.model.GreyListAddress;
import aero.nettracer.serviceprovider.common.hibernate.HibernateWrapper;

public class Util {
	private static HashMap<String, GreyListAddress> greyListMap = null;
	
	private static DecimalFormat geocodeDecimalFormat = new DecimalFormat("0.00000");//5 decimal points should give us sub 7m accuracy
	
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
	
	public static HashMap<String, GreyListAddress> getGreyListAddressMap() {
		if (greyListMap != null) {
			return greyListMap;
		} else {
			greyListMap = new HashMap<String, GreyListAddress>();
			List<GreyListAddress> addresses = getGreyListedAddreses();
			for (GreyListAddress a: addresses) {
				String key = normalizeGeoNumber(a.getLatitude()) + "/" + normalizeGeoNumber(a.getLongitude());
				greyListMap.put(key, a);
			}
			return greyListMap;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	private static List<GreyListAddress> getGreyListedAddreses() {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(GreyListAddress.class);
			return cri.list();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}		
	}
	
	public static String normalizeGeoNumber(double d){
		return geocodeDecimalFormat.format(d);
	}
	
}
