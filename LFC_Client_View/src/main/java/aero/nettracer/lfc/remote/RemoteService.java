package aero.nettracer.lfc.remote;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.bagnet.nettracer.tracing.constant.TracingConstants;

import aero.nettracer.lf.services.LFCClientServiceRemote;
import aero.nettracer.lfc.model.CategoryBean;
import aero.nettracer.lfc.model.KeyValueBean;
import aero.nettracer.lfc.model.LostReportBean;

public class RemoteService {
	static String user = null;
	static String password = null;
	// static String url = "jnp://127.0.0.1:1199";
	// static String url = "jnp://192.168.2.145:1399";
	// static String url = "jnp://184.172.41.2:1199";
	static String url = System.getProperty("lfc.remote.url");
	
	static Context ctx;
	
	private static List<CategoryBean> categoriesAB;
	private static List<CategoryBean> categoriesWN;
	private static List<KeyValueBean> colors;
	private static List<KeyValueBean> states;
	private static List<KeyValueBean> countries;
	private static List<KeyValueBean> locationsAB_AVS;
	private static List<KeyValueBean> locationsAB_BGT;
	private static List<KeyValueBean> locationsWN;
	private static HashMap<String, ArrayList<KeyValueBean>> locationsByStateAB_AVS;
	private static HashMap<String, ArrayList<KeyValueBean>> locationsByStateAB_BGT;

	public static Context getInitialContext() throws NamingException {
		Properties p = new Properties();
		p.put("jnp.socketFactory", "org.jnp.interfaces.TimedSocketFactory");
		// p.put("jnp.timeout", "5000");
		// p.put("jnp.sotimeout", "10000");
		p.put(Context.INITIAL_CONTEXT_FACTORY,
				"org.jnp.interfaces.NamingContextFactory");
		p.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
		p.put(Context.PROVIDER_URL, url);
		if (user != null) {
			System.out.println("user: " + user);
			p.put(Context.SECURITY_PRINCIPAL, user);
			if (password == null)
				password = "";
			p.put(Context.SECURITY_CREDENTIALS, password);
		}
		return new InitialContext(p);
	}
	
	public static LFCClientServiceRemote getRemoteService() throws NamingException {
		ctx = getInitialContext();
		LFCClientServiceRemote o = (LFCClientServiceRemote) ctx.lookup("tracer/LFCClientServiceBean/remote");
		return o;
	}
	
	public static boolean getLists() {
		try {
			LFCClientServiceRemote o = getRemoteService();
			if (o != null) {
				categoriesAB = o.getCategories(TracingConstants.LF_AB_COMPANY_ID);
				categoriesWN = o.getCategories(TracingConstants.LF_WN_COMPANY_ID);
				colors = o.getColors();
				states = o.getState();
				countries = o.getCountries();
				locationsWN = o.getStations(TracingConstants.LF_WN_COMPANY_ID);
				locationsAB_AVS = o.getStations(TracingConstants.LF_AB_COMPANY_ID, TracingConstants.LF_AVIS_COMPANY_ID);
				locationsByStateAB_AVS = o.getStationsByState(TracingConstants.LF_AB_COMPANY_ID, TracingConstants.LF_AVIS_COMPANY_ID);
				locationsAB_BGT = o.getStations(TracingConstants.LF_AB_COMPANY_ID, TracingConstants.LF_BUDGET_COMPANY_ID);
				locationsByStateAB_BGT = o.getStationsByState(TracingConstants.LF_AB_COMPANY_ID, TracingConstants.LF_BUDGET_COMPANY_ID);
			}
			ctx.close();
		} catch (NamingException ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static List<CategoryBean> getCategories(String company) {
		if (categoriesAB == null || categoriesWN == null) {
			if (!getLists()) {
				return null;
			}
		}
		if (company.equals(TracingConstants.LF_AB_COMPANY_ID)) {
			return categoriesAB;
		}
		return categoriesWN;
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
		if (locationsAB_AVS == null || locationsAB_BGT == null || locationsWN == null) {
			if (!getLists()) {
				return null;
			}
		}
		if (subCompany.equals(TracingConstants.LF_AVIS_COMPANY_ID)) {
			return locationsAB_AVS;
		} else if (subCompany.equals(TracingConstants.LF_BUDGET_COMPANY_ID)) {
			return locationsAB_BGT;
		}
		return locationsWN;
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
	
	public static LostReportBean getReport(long id, String name) {
		LostReportBean toReturn = new LostReportBean();
		try {
			LFCClientServiceRemote o = getRemoteService();
			if (o != null) {
				toReturn = o.getLostReport(id, name);
//				categoriesAB = o.getCategories(TracingConstants.LF_AB_COMPANY_ID);
//				categoriesWN = o.getCategories(TracingConstants.LF_WN_COMPANY_ID);
//				colors = o.getColors();
//				states = o.getState();
//				countries = o.getCountries();
//				locationsWN = o.getStations(TracingConstants.LF_WN_COMPANY_ID);
//				locationsAB_AVS = o.getStations(TracingConstants.LF_AB_COMPANY_ID, TracingConstants.LF_AVIS_COMPANY_ID);
//				locationsByStateAB_AVS = o.getStationsByState(TracingConstants.LF_AB_COMPANY_ID, TracingConstants.LF_AVIS_COMPANY_ID);
//				locationsAB_BGT = o.getStations(TracingConstants.LF_AB_COMPANY_ID, TracingConstants.LF_BUDGET_COMPANY_ID);
//				locationsByStateAB_BGT = o.getStationsByState(TracingConstants.LF_AB_COMPANY_ID, TracingConstants.LF_BUDGET_COMPANY_ID);
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
			LFCClientServiceRemote o = getRemoteService();
			if (o != null) {
				toReturn = o.saveOrUpdateLostReport(bean);
			}
			ctx.close();
		} catch (NamingException ex) {
			ex.printStackTrace();
		}
		return toReturn;
	}

}
