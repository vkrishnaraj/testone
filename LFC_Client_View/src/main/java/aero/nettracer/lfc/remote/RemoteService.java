package aero.nettracer.lfc.remote;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

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
	
	private static List<CategoryBean> categories;
	private static List<KeyValueBean> colors;
	private static List<KeyValueBean> states;
	private static List<KeyValueBean> countries;
	private static List<KeyValueBean> locations;
	private static HashMap<String, ArrayList<KeyValueBean>> locationsByState;

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
	
	public static boolean getLists(String company) {
		try {
			LFCClientServiceRemote o = getRemoteService();
			if (o != null) {
				categories = o.getCategories(company);
				colors = o.getColors();
				states = o.getState();
				countries = o.getCountries();
				locations = o.getStations("AB", company);
				locationsByState = o.getStationsByState("AB", company);
			}
			ctx.close();
		} catch (NamingException ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static List<CategoryBean> getCategories(String company) {
		if (categories == null) {
			if (!getLists(company)) {
				return null;
			}
		}
		return categories;
	}
	
	public static List<KeyValueBean> getColors(String company) {
		if (colors == null) {
			if (!getLists(company)) {
				return null;
			}
		}
		return colors;
	}
	
	public static List<KeyValueBean> getCountries(String company) {
		if (countries == null) {
			if (!getLists(company)) {
				return null;
			}
		}
		return countries;
	}
	
	public static List<KeyValueBean> getStates(String company) {
		if (states == null) {
			if (!getLists(company)) {
				return null;
			}
		}
		return states;
	}
	
	public static List<KeyValueBean> getLocations(String company) {
		if (locations == null) {
			if (!getLists(company)) {
				return null;
			}
		}
		return locations;
	}
	
	public static HashMap<String, ArrayList<KeyValueBean>> getLocationsByState(String company) {
		if (locationsByState == null) {
			if (!getLists(company)) {
				return null;
			}
		}
		return locationsByState;
	}
	
	public static LostReportBean getReport(long id, String name, String company) {
		LostReportBean toReturn = new LostReportBean();
		try {
			LFCClientServiceRemote o = getRemoteService();
			if (o != null) {
				toReturn = o.getLostReport(id, name);
				categories = o.getCategories(company);
				colors = o.getColors();
				states = o.getState();
				countries = o.getCountries();
				locations = o.getStations("AB", company);
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
