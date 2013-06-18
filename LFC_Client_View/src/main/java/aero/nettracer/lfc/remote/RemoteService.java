package aero.nettracer.lfc.remote;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.bagnet.nettracer.tracing.constant.TracingConstants;

import aero.nettracer.lf.services.LFCClientServiceRemote;
import aero.nettracer.lfc.model.AddressBean;
import aero.nettracer.lfc.model.CCBean;
import aero.nettracer.lfc.model.CategoryBean;
import aero.nettracer.lfc.model.KeyValueBean;
import aero.nettracer.lfc.model.LostReportBean;
import aero.nettracer.lfc.model.RateBean;
import aero.nettracer.lfc.model.ShippingBean;

public class RemoteService {
	static String user = null;
	static String password = null;
	static String urlLF = System.getProperty("lfc.remote.url");
	static String urlAB = System.getProperty("ab.remote.url");
	static String remoteLF = System.getProperty("lfc.remote.bean");
	static String remoteAB = System.getProperty("ab.remote.bean");
	
	static Context ctx;
	
	private static List<CategoryBean> categoriesAB;
	private static List<CategoryBean> categoriesLF;
	private static List<KeyValueBean> colors;
	private static List<KeyValueBean> states;
	private static List<KeyValueBean> countries;
	private static List<KeyValueBean> locationsAB_AVS;
	private static List<KeyValueBean> locationsAB_BGT;
	private static List<KeyValueBean> locationsLF;
	private static HashMap<String, ArrayList<KeyValueBean>> locationsByStateAB_AVS;
	private static HashMap<String, ArrayList<KeyValueBean>> locationsByStateAB_BGT;

	public static Context getInitialContextAB() throws NamingException {
		return getInitialContext(urlAB);
	}

	public static Context getInitialContextLF() throws NamingException {
		return getInitialContext(urlLF);
	}
	
	public static Context getInitialContext(String url) throws NamingException {
		Hashtable p = new Hashtable();
		p.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
		return new InitialContext(p);
	}
	
	public static LFCClientServiceRemote getRemoteServiceAB() throws NamingException {
		ctx = getInitialContextAB();
		LFCClientServiceRemote o = (LFCClientServiceRemote) ctx.lookup(remoteAB);
		return o;
	}
	
	public static LFCClientServiceRemote getRemoteServiceLF() throws NamingException {
		ctx = getInitialContextLF();
		LFCClientServiceRemote o = (LFCClientServiceRemote) ctx.lookup(remoteLF);
		return o;
	}

	public static boolean getLists() {
		return getLists(null);
	}
	public static boolean getLists(String subCompany) {
		return (getListsAB() && getListsLF(subCompany));
	}
	
	public static boolean getListsAB() {
		try {
			LFCClientServiceRemote o = getRemoteServiceAB();
			if (o != null) {
				categoriesAB = o.getCategories(TracingConstants.LF_AB_COMPANY_ID);
				locationsAB_AVS = o.getStations(TracingConstants.LF_AB_COMPANY_ID, TracingConstants.LF_AVIS_COMPANY_ID);
				locationsByStateAB_AVS = o.getStationsByState(TracingConstants.LF_AB_COMPANY_ID, TracingConstants.LF_AVIS_COMPANY_ID);
				locationsAB_BGT = o.getStations(TracingConstants.LF_AB_COMPANY_ID, TracingConstants.LF_BUDGET_COMPANY_ID);
				locationsByStateAB_BGT = o.getStationsByState(TracingConstants.LF_AB_COMPANY_ID, TracingConstants.LF_BUDGET_COMPANY_ID);
			}
			ctx.close();
		} catch (NamingException ex) {
			ex.printStackTrace();
			return false;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static boolean getListsLF(String subcompany) {
		try {
			LFCClientServiceRemote o = getRemoteServiceLF();
			if (o != null) {
				categoriesLF = o.getCategories(TracingConstants.LF_LF_COMPANY_ID);
				colors = o.getColors();
				states = o.getState();
				countries = o.getCountries();
				if(subcompany!=null)
					locationsLF = o.getStationsBySubCompany(TracingConstants.LF_LF_COMPANY_ID,subcompany);
				else
					locationsLF = o.getStations(TracingConstants.LF_LF_COMPANY_ID);
			}
			ctx.close();
		} catch (NamingException ex) {
			ex.printStackTrace();
			return false;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static List<CategoryBean> getCategories(String company) {
		if (categoriesAB == null || categoriesLF == null) {
			if (!getLists()) {
				return null;
			}
		}
		if (company.equals(TracingConstants.LF_AB_COMPANY_ID)) {
			return categoriesAB;
		}
		return categoriesLF;
	}
	
	public static List<KeyValueBean> getColors() {
		if (colors == null) {
			if (!getLists()) {
				return null;
			}
		}
		return colors;
	}
	
	public static List<KeyValueBean> getCountries() {
		if (countries == null) {
			if (!getLists()) {
				return null;
			}
		}
		return countries;
	}
	
	public static List<KeyValueBean> getStates() {
		if (states == null) {
			if (!getLists()) {
				return null;
			}
		}
		return states;
	}
	
	public static List<KeyValueBean> getLocations(String subCompany) {
		if (locationsAB_AVS == null || locationsAB_BGT == null || locationsLF == null) {
			if (!getLists(subCompany)) {
				return null;
			}
		}
		if (subCompany.equals(TracingConstants.LF_AVIS_COMPANY_ID)) {
			return locationsAB_AVS;
		} else if (subCompany.equals(TracingConstants.LF_BUDGET_COMPANY_ID)) {
			return locationsAB_BGT;
		}
		return locationsLF;
	}
	
	public static HashMap<String, ArrayList<KeyValueBean>> getLocationsByState(String subCompany) {
		if (locationsByStateAB_AVS == null || locationsByStateAB_BGT == null) {
			if (!getLists()) {
				return null;
			}
		}
		if (subCompany.equals(TracingConstants.LF_AVIS_COMPANY_ID)) {
			return locationsByStateAB_AVS;
		}
		return locationsByStateAB_BGT;
	}
	
	public static LostReportBean getReportAB(long id, String name) {
		LostReportBean toReturn = new LostReportBean();
		try {
			LFCClientServiceRemote o = getRemoteServiceAB();
			if (o != null) {
				toReturn = o.getLostReport(id, name);
			}
			ctx.close();
		} catch (NamingException ex) {
			ex.printStackTrace();
		}
		return toReturn;
	}
	
	public static LostReportBean getReportLF(long id, String name) {
		LostReportBean toReturn = new LostReportBean();
		try {
			LFCClientServiceRemote o = getRemoteServiceLF();
			if (o != null) {
				toReturn = o.getLostReport(id, name);
			}
			ctx.close();
		} catch (NamingException ex) {
			ex.printStackTrace();
		}
		return toReturn;
	}
	
	public static LostReportBean getReportShippingLF(long id, String name) {
		LostReportBean toReturn = new LostReportBean();
		try {
			LFCClientServiceRemote o = getRemoteServiceLF();
			if (o != null) {
				toReturn = o.getLostReportShipping(id, name);
			}
			ctx.close();
		} catch (NamingException ex) {
			ex.printStackTrace();
		}
		return toReturn;
	}
	
	public static long createReport(LostReportBean bean) {
		long toReturn = -1;
		try {
			LFCClientServiceRemote o;
			if (bean != null && bean.getCompany() != null && bean.getCompany().equals(TracingConstants.LF_AB_COMPANY_ID)) {
				o = getRemoteServiceAB();
			} else {
				o = getRemoteServiceLF();
			}
			if (o != null) {
				toReturn = o.saveOrUpdateLostReport(bean);
			}
			ctx.close();
		} catch (NamingException ex) {
			ex.printStackTrace();
		}
		return toReturn;
	}
	
	public static ShippingBean createReportAndShip(LostReportBean bean) {
		ShippingBean toReturn = null;
		try {
			LFCClientServiceRemote o;
			if (bean != null && bean.getCompany() != null && bean.getCompany().equals(TracingConstants.LF_AB_COMPANY_ID)) {
				o = getRemoteServiceAB();
			} else {
				o = getRemoteServiceLF();
			}
			if (o != null) {
				toReturn = o.saveOrUpdateShipping(bean);
			}
			ctx.close();
		} catch (NamingException ex) {
			ex.printStackTrace();
		}
		return toReturn;
	}
	
	public static AddressBean validateAddressFedex(LostReportBean bean) {
		AddressBean toReturn = null;
		try {
			LFCClientServiceRemote o;
			if (bean != null && bean.getCompany() != null && bean.getCompany().equals(TracingConstants.LF_AB_COMPANY_ID)) {
				o = getRemoteServiceAB();
			} else {
				o = getRemoteServiceLF();
			}
			if (o != null) {
				toReturn = o.validateAddressFedex(bean);
			}
			ctx.close();
		} catch (NamingException ex) {
			ex.printStackTrace();
		}
		return toReturn;
	}
	
	public static List<RateBean> getRatesForAddress(LostReportBean bean) {
		List<RateBean> toReturn = null;
		try {
			LFCClientServiceRemote o;
			if (bean != null && bean.getCompany() != null && bean.getCompany().equals(TracingConstants.LF_AB_COMPANY_ID)) {
				o = getRemoteServiceAB();
			} else {
				o = getRemoteServiceLF();
			}
			if (o != null) {
				toReturn = o.getRatesForAddress(bean);
			}
			ctx.close();
		} catch (NamingException ex) {
			ex.printStackTrace();
		}
		return toReturn;
	}

	public static boolean authorizeCc(LostReportBean bean) {
		try {
			LFCClientServiceRemote o;
			if (bean != null && bean.getCompany() != null && bean.getCompany().equals(TracingConstants.LF_AB_COMPANY_ID)) {
				o = getRemoteServiceAB();
			} else {
				o = getRemoteServiceLF();
			}
			if (o != null) {
				return o.authorizeCc(bean);
			}
			ctx.close();
		} catch (NamingException ex) {
			ex.printStackTrace();
		}
		return false;
	}

}
