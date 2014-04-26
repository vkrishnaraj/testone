package com.bagnet.nettracer.wt;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Order;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.bmo.CategoryBMO;
import com.bagnet.nettracer.tracing.bmo.ManufacturerBMO;
import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.bmo.XDescElementsBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.BDO;
import com.bagnet.nettracer.tracing.db.Company_Specific_Variable;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Incident_Claimcheck;
import com.bagnet.nettracer.tracing.db.Item_Inventory;
import com.bagnet.nettracer.tracing.db.Manufacturer;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.OHD_CategoryType;
import com.bagnet.nettracer.tracing.db.Remark;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.WT_FWD_Log;
import com.bagnet.nettracer.tracing.db.WT_Info;
import com.bagnet.nettracer.tracing.db.WorldTracerFile;
import com.bagnet.nettracer.tracing.db.WorldTracerFile.WTStatus;
import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles;
import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles.ActionFileType;
import com.bagnet.nettracer.tracing.forms.IncidentForm;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.HibernateUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;

public class WorldTracerUtils {

	private static String wt_http;
	public static String wt_url;
	public static String wt_suffix_airline = "us";

	private static String nt_user = "ogadmin";
	private static String nt_comp = "OW";

	public static String status_active = "A";
	public static String status_closed = "C";
	public static String status_suspended = "S";
	public static String status_extended = "D";
	public static String status_handled = "H";
	public static String status_qoh = "Q";
	private static Logger logger = Logger.getLogger(WorldTracerUtils.class);


	/**
	 * get the NT agent to use for wt inserts
	 */
	@SuppressWarnings("unchecked")
	public static Agent getWTAgent(int station_id, String companycode_id) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();

			String sql = "select agent from com.bagnet.nettracer.tracing.db.Agent agent where "
					+ "agent.is_wt_user = :is_wt and agent.station.station_ID = :station_id and agent.companycode_ID = :companycode_id";

			Query q = sess.createQuery(sql);
			q.setParameter("is_wt", new Integer(1));
			q.setParameter("station_id", new Integer(station_id));
			q.setParameter("companycode_id", companycode_id);

			List<Agent> list = q.list();
			if (list != null && list.size() > 0)
				return (Agent) list.get(0);

			// if get here then get the agent for the company
			sql = "select agent from com.bagnet.nettracer.tracing.db.Agent agent where "
					+ "agent.is_wt_user = :is_wt and agent.companycode_ID = :companycode_id";

			q = sess.createQuery(sql);
			q.setParameter("is_wt", new Integer(1));
			q.setParameter("companycode_id", companycode_id);

			list = q.list();
			if (list != null && list.size() > 0)
				return (Agent) list.get(0);

			// if get here then use ogadmin
			sql = "select agent from com.bagnet.nettracer.tracing.db.Agent agent where "
					+ "agent.username = :nt_user and agent.companycode_ID = :companycode_id";

			q = sess.createQuery(sql);
			q.setParameter("nt_user", nt_user);
			q.setParameter("companycode_id", nt_comp);

			list = q.list();
			if (list != null && list.size() > 0)
				return (Agent) list.get(0);
			return null;

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
	
	public static String getActiveStations(HttpClient client, String companycode) {
		String responseBody = null;
		wt_http = WorldTracerUtils.getWt_url(companycode);
		wt_url = "http://" + wt_http + "/";
		PostMethod method = new PostMethod(wt_url + "cgi-bin/bagDSL.exe");
		method.setDoAuthentication(true);
		method.addParameter("OPT", "ACTIVE");
		method.addParameter("ARL", companycode.toUpperCase());
		method.addParameter("B1", "Submit DSL");
		method.addParameter("A1", companycode.toLowerCase());
		method.addParameter("A2", "WM");

		// Provide custom retry handler is necessary
		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler(3, false));

		try {
			// Execute the method.
			int statusCode = client.executeMethod(method);

			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + method.getStatusLine());
			}

			// Read the response body.
			responseBody = method.getResponseBodyAsString();
			int start = responseBody.indexOf("---- TYPE A ACCESS - CRT ----");
			int end = responseBody.indexOf("---- TYPE B ACCESS - TTY ----");
			if (start > 0 && end > 0) {
				responseBody = responseBody.substring(start
						+ "---- TYPE A ACCESS - CRT ----".length(), end);
			}

		} catch (HttpException e) {
			System.err.println("Fatal protocol violation: " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Fatal transport error: " + e.getMessage());
			e.printStackTrace();
		} finally {
			// Release the connection.
			method.releaseConnection();
		}
		return responseBody;
	}

	public static String getAllRAF(HttpClient client, String companycode,
			String stationcode, WTStatus status, String dt) {
		String responseBody = null;

		if (dt != null && dt.length() > 0)
			dt = ".DT+" + dt;
		else
			dt = "";
		wt_http = WorldTracerUtils.getWt_url(companycode);
		wt_url = "http://" + wt_http + "/";
		GetMethod method = new GetMethod(wt_url + "cgi-bin/bagRAF.exe?STN="
				+ stationcode.toUpperCase() + "&ARL="
				+ companycode.toUpperCase() + "&OPT=" + status + "&SEARCH="
				+ dt);
		method.setDoAuthentication(true);
		
		// Provide custom retry handler is necessary
		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler(3, false));

		try {
			// Execute the method.
			int statusCode = client.executeMethod(method);

			if (statusCode != HttpStatus.SC_OK) {
				return "Method failed: " + method.getStatusLine();
			}

			// Read the response body.
			responseBody = method.getResponseBodyAsString();
			return responseBody;

		} catch (HttpException e) {
			System.err.println("Fatal protocol violation: " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Fatal transport error: " + e.getMessage());
			e.printStackTrace();
		} finally {
			// Release the connection.
			method.releaseConnection();
		}
		return responseBody;
	}

	public static String getRAF(HttpClient client, String filenum, String wt_url) {
		String responseBody = null;

		GetMethod method = new GetMethod(wt_url + "cgi-bin/bagDAH.exe?T1="
				+ filenum.toUpperCase());
		method.setDoAuthentication(true);

		// Provide custom retry handler is necessary
		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler(3, false));

		try {
			// Execute the method.
			int statusCode = client.executeMethod(method);

			if (statusCode != HttpStatus.SC_OK) {
				return "Method failed: " + method.getStatusLine();
			}

			// Read the response body.
			responseBody = method.getResponseBodyAsString();
			return responseBody;

		} catch (HttpException e) {
			System.err.println("Fatal protocol violation: " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Fatal transport error: " + e.getMessage());
			e.printStackTrace();
		} finally {
			// Release the connection.
			method.releaseConnection();
		}
		return responseBody;
	}

	/**
	 * get all onhands from WT
	 * 
	 * @param client
	 * @param companycode
	 * @param stationcode
	 * @return
	 */
	public static String getAllROF(HttpClient client, String companycode,
			String stationcode, String status, String dt) {
		String responseBody = null;
		if (dt != null && dt.length() > 0)
			dt = ".DT+" + dt;
		else
			dt = "";
		wt_http = WorldTracerUtils.getWt_url(companycode);
		wt_url = "http://" + wt_http + "/";
		GetMethod method = new GetMethod(wt_url + "cgi-bin/bagROF.exe?STN="
				+ stationcode.toUpperCase() + "&ARL="
				+ companycode.toUpperCase() + "&OPT=" + status + "&SEARCH="
				+ dt);
		method.setDoAuthentication(true);

		// Provide custom retry handler is necessary
		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler(3, false));

		try {
			// Execute the method.
			int statusCode = client.executeMethod(method);

			if (statusCode != HttpStatus.SC_OK) {
				return "Method failed: " + method.getStatusLine();
			}

			// Read the response body.
			responseBody = method.getResponseBodyAsString();
			return responseBody;

		} catch (HttpException e) {
			System.err.println("Fatal protocol violation: " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Fatal transport error: " + e.getMessage());
			e.printStackTrace();
		} finally {
			// Release the connection.
			method.releaseConnection();
		}
		return responseBody;
	}

	public static String getROF(HttpClient client, String filenum, String wt_url) {
		String responseBody = null;

		GetMethod method = new GetMethod(wt_url + "cgi-bin/bagDOH.exe?T1="
				+ filenum.toUpperCase());
		method.setDoAuthentication(true);

		// Provide custom retry handler is necessary
		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler(3, false));

		try {
			// Execute the method.
			int statusCode = client.executeMethod(method);

			if (statusCode != HttpStatus.SC_OK) {
				return "Method failed: " + method.getStatusLine();
			}

			// Read the response body.
			responseBody = method.getResponseBodyAsString();
			return responseBody;

		} catch (HttpException e) {
			System.err.println("Fatal protocol violation: " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Fatal transport error: " + e.getMessage());
			e.printStackTrace();
		} finally {
			// Release the connection.
			method.releaseConnection();
		}
		return responseBody;
	}

	/**
	 * post roh to worldtracer
	 * 
	 * @param client
	 * @param companycode
	 * @param ahl_id
	 * @param ohd_string
	 * @return
	 */
	public static String postROH(HttpClient client, String companycode,
			String ahl_id, String ohd_string) {
		String responseBody = null;

		try {
			ohd_string = URLEncoder.encode(ohd_string, "UTF-8");
		} catch (Exception e) {
		}
		wt_http = WorldTracerUtils.getWt_url(companycode);
		wt_url = "http://" + wt_http + "/";
		String cgiexe = "cgi-bin/bagResponseROH.exe?A1="
				+ companycode.toUpperCase() + "&T1=" + ahl_id + "&ROH="
				+ ohd_string;
		String getstring = wt_url + cgiexe;
		getstring = getstring.replace(" ", "+");

		GetMethod method = new GetMethod(getstring);
		method.setDoAuthentication(true);

		// Provide custom retry handler is necessary
		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler(3, false));

		try {
			
			String requestInfo = WorldTracerUtils.getWtRequest(wt_url, cgiexe);
			
			// Read the response body.
			responseBody = method.getResponseBodyAsString();
			WorldTracerUtils.insertWTInfo(requestInfo,responseBody);
		} catch (HttpException e) {
			System.err.println("Fatal protocol violation: " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Fatal transport error: " + e.getMessage());
			e.printStackTrace();
		} finally {
			// Release the connection.
			method.releaseConnection();
		}
		return responseBody;
	}

	public static int getXdescID(String code) {
		if (code == null)
			return 7;
		else if (code.equals("W"))
			return 6;
		else if (code.equals("S"))
			return 5;
		else if (code.equals("P"))
			return 4;
		else if (code.equals("H"))
			return 3;
		else if (code.equals("C"))
			return 2;
		else if (code.equals("B"))
			return 1;

		return 7;
	}

	public static Incident findIncidentByWTID(String wt_id)
		throws HibernateException {
		return findIncidentByWTID(wt_id, false);
	}
	
	/**
	 * find incident by using the worldtracer id column to determine if the
	 * incident is new or not
	 * 
	 * @param wt_id
	 * @return
	 * @throws HibernateException
	 */
	public static Incident findIncidentByWTID(String wt_id, boolean dirtyRead)
			throws HibernateException {
		Session sess = null;
		
		if(dirtyRead) {
			sess = HibernateWrapper.getDirtySession().openSession();
		}
		else {
			sess = HibernateWrapper.getSession().openSession();
		}

		try {
			Query q = sess
					.createQuery("from com.bagnet.nettracer.tracing.db.Incident incident where incident.wtFile.wt_id = :wt_id");
			q.setParameter("wt_id", wt_id);
			@SuppressWarnings("unchecked")
			List<Incident> list = q.list();

			if (list.size() == 0) {
				return null;
			}
			Incident iDTO = (Incident) list.get(0);

			return iDTO;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			sess.close();
		}
	}

	/**
	 * find ohd by using the worldtracer id column to determine if the ohd is
	 * new or not
	 * 
	 * @param wt_id
	 * @return
	 * @throws HibernateException
	 */
	public static OHD findOHDByWTID(String wt_id) throws HibernateException {
		Session sess = HibernateWrapper.getSession().openSession();

		try {
			Query q = sess
					.createQuery("from com.bagnet.nettracer.tracing.db.OHD ohd where ohd.wtFile.wt_id = :wt_id");
			q.setParameter("wt_id", wt_id);
			@SuppressWarnings("unchecked")
			List<OHD> list = q.list();

			if (list.size() == 0) {
				return null;
			}
			OHD iDTO = (OHD) list.get(0);

			return iDTO;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			sess.close();
		}
	}

	public static Worldtracer_Actionfiles findActionFile(String actionfile_text) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(Worldtracer_Actionfiles.class);
			cri.add(Restrictions.eq("action_file_text", actionfile_text));
			@SuppressWarnings("unchecked")
			List<Worldtracer_Actionfiles> list = cri.list();
			if (list != null && list.size() > 0) {
				return (Worldtracer_Actionfiles) list.get(0);
			}
			return null;
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

	public static Worldtracer_Actionfiles findActionFileByID(int ID) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(Worldtracer_Actionfiles.class);
			cri.add(Restrictions.eq("id", ID));
			@SuppressWarnings("unchecked")
			List<Worldtracer_Actionfiles> list = cri.list();
			if (list != null && list.size() > 0) {
				return (Worldtracer_Actionfiles) list.get(0);
			}
			return null;
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

	public static Worldtracer_Actionfiles findActionFileByOhdID(String wt_id) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(Worldtracer_Actionfiles.class);
			cri.add(Restrictions.eq("wt_ohd_id", wt_id));
			@SuppressWarnings("unchecked")
			List<Worldtracer_Actionfiles> list = cri.list();
			if (list != null && list.size() > 0) {
				return (Worldtracer_Actionfiles) list.get(0);
			}
			return null;
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

	public static Worldtracer_Actionfiles findActionFileByIncidentID(
			String wt_incident_id) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(Worldtracer_Actionfiles.class);
			cri.add(Restrictions.eq("wt_incident_id", wt_incident_id));
			@SuppressWarnings("unchecked")
			List<Worldtracer_Actionfiles> list = cri.list();
			if (list != null && list.size() > 0) {
				return (Worldtracer_Actionfiles) list.get(0);
			}
			return null;
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

	/**
	 * find action files from db based on type, day, airline, and station
	 * 
	 * @param wt_type
	 * @param day
	 * @param airline
	 * @param station
	 * @return
	 */
	public static ArrayList<Worldtracer_Actionfiles> findActionFiles(
			String wt_type, String day, String airline, String station,
			int rowsperpage, int currpage) {
		Session sess = null;
		try {
			ActionFileType type = ActionFileType.valueOf(wt_type.toUpperCase());
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(Worldtracer_Actionfiles.class);
			cri.addOrder(Order.desc("percent_match"));
			cri.add(Restrictions.eq("action_file_type", type));
			cri.add(Restrictions.eq("day", new Integer(day)));
			cri.add(Restrictions.eq("airline", airline));
			cri.add(Restrictions.eq("station", station));
			cri.add(Restrictions.eq("deleted", false));


			if (rowsperpage > 0) {
				int startnum = currpage * rowsperpage;
				cri.setFirstResult(startnum);
				cri.setMaxResults(rowsperpage);
			}
			@SuppressWarnings("unchecked")
			ArrayList<Worldtracer_Actionfiles> list = (ArrayList<Worldtracer_Actionfiles>) cri.list();
			return list;
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

	/**
	 * 
	 * @param af_id
	 * @return
	 */
	public static boolean deleteActionFiles(String af_id_str) {
		// delete from db first
		Session sess = null;

		try {
			int af_id = Integer.parseInt(af_id_str);
			if (af_id == 0)
				return false;
			sess = HibernateWrapper.getSession().openSession();

			Query q = sess
					.createQuery("from com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles wa where wa.id = :af_id and deleted = false");
			q.setInteger("af_id", af_id);
			@SuppressWarnings("unchecked")
			List<Worldtracer_Actionfiles> list = q.list();

			if (list.size() == 0) {
				return false;
			}
			Worldtracer_Actionfiles af = (Worldtracer_Actionfiles) list.get(0);
			if (af != null) {
				af.setDeleted(true);
				HibernateUtils.save(af);
			}

			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
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

	/**
	 * find action files from db by ahl_id or ohd_id
	 * 
	 * @param wt_type
	 * @param day
	 * @param airline
	 * @param station
	 * @return
	 */
	public static ArrayList<Worldtracer_Actionfiles> findActionFilesbyWTId(
			String ahl_id, String ohd_id, String wt_type, int rowsperpage,
			int currpage) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(Worldtracer_Actionfiles.class);
			cri.addOrder(Order.desc("percent_match"));
			cri.add(Restrictions.eq("action_file_type", ActionFileType.valueOf(wt_type.toUpperCase())));
			if (rowsperpage > 0) {
				int startnum = currpage * rowsperpage;
				cri.setFirstResult(startnum);
				cri.setMaxResults(rowsperpage);
			}

			if (ahl_id != null && ahl_id.length() > 0)
				cri.add(Restrictions.eq("wt_incident_id", ahl_id));
			if (ohd_id != null && ohd_id.length() > 0)
				cri.add(Restrictions.eq("wt_ohd_id", ohd_id));
			@SuppressWarnings({ "unchecked" })
			ArrayList<Worldtracer_Actionfiles> list = (ArrayList<Worldtracer_Actionfiles>) cri.list();
			return list;
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

	public static WT_FWD_Log findFWDByID(int wt_fwd_log_id) {
		Session sess = null;
		try {
			String query = "select wtfwd from com.bagnet.nettracer.tracing.db.WT_FWD_Log wtfwd "
					+ "where wtfwd.wt_fwd_log_id=:wt_fwd_log_id";
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess.createQuery(query);
			q.setInteger("wt_fwd_log_id", wt_fwd_log_id);
			@SuppressWarnings("unchecked")
			List<WT_FWD_Log> list = q.list();
			if (list.size() == 0) {
				logger.debug("unable to find wt_fwd_log: " + wt_fwd_log_id);
				return null;
			}
			WT_FWD_Log wDTO = (WT_FWD_Log) list.get(0);
			return wDTO;
		} catch (Exception e) {
			logger.error("unable to retrieve FWD: " + e);
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.error("unable to close connection: " + e);
				}
			}
		}
	}

	public static String getWt_suffix_airline(String companycode) {
		Company_Specific_Variable comsv = AdminUtils
				.getCompVariable(companycode);
		String wt_suffix_airline = comsv.getWt_airlinecode();
		if (wt_suffix_airline == null) {
			return null;
		}
		return wt_suffix_airline.toLowerCase();
	}

	public static String getWt_url(String companycode) {
		String wt_url = null;
		Company_Specific_Variable comsv = AdminUtils
				.getCompVariable(companycode);

		if (comsv.getWt_url() != null && !comsv.getWt_url().equals("")) {
			wt_url = comsv.getWt_url();
		} else
			wt_url = "www.worldtracer.aero";

		return wt_url;
	}

	public static BDO findBDOByID(int BDO_ID) {
		Session sess = null;

		try {
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess
					.createQuery("from com.bagnet.nettracer.tracing.db.BDO bdo where bdo.BDO_ID= :BDO_ID");
			q.setParameter("BDO_ID", BDO_ID);
			@SuppressWarnings("unchecked")
			List<BDO> list = q.list();

			if (list.size() == 0) {
				logger.debug("unable to find bdo: " + BDO_ID);
				return null;
			}
			BDO iDTO = (BDO) list.get(0);

			return iDTO;
		} catch (Exception e) {
			logger.error("unable to retrieve bdo: " + e);
			e.printStackTrace();
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.error("unable to close connection: " + e);
				}
			}
		}
	}

	public static boolean insertWTInfo(String requestContext,
			String responseContext) {
		boolean flag = true;
		Session session = null;
		try {
			session = HibernateWrapper.getSession().openSession();
			Transaction tx = session.beginTransaction();
			WT_Info wt_info = new WT_Info();
			wt_info.setRequestContext(requestContext);
			wt_info.setResponseContext(responseContext);
			session.save(wt_info);
			tx.commit();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			flag = false;
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return flag;
	}
//count the number of the WorldTracer-ActionFile ,and make it as a property of the class CountActionFile 
	//and put the CountActionFile object into the list. 
	public static List<CountActionFile> countActionFile(String airline, String station) {
		List<CountActionFile> list = new ArrayList<CountActionFile>();
		int count;
		Session session = null;
		try {
			session = HibernateWrapper.getSession().openSession();
			for (ActionFileType type : ActionFileType.values()) {
				CountActionFile countActionFile = new CountActionFile();
				for (int j = 1; j <= 7; j++) {
					Query query = session
							.createQuery("select count(*) from Worldtracer_Actionfiles worldtracer where worldtracer.action_file_type=? and worldtracer.day=? and worldtracer.airline=? and worldtracer.station=? and worldtracer.deleted = false");
					query.setParameter(0, type);
					query.setParameter(1, j);
					query.setParameter(2, airline);
					query.setParameter(3, station);
					String count1 = query.uniqueResult().toString();
					count = Integer.parseInt(count1);
					countActionFile.setType(type.name());
					countActionFile.setDayCount(j, count);
				}
				list.add(countActionFile);
			}
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return list;
	}


	public static int countOneTypeActionFile(String type,String day,String airline,String station){

		int int_day=Integer.parseInt(day);
		Session sess=null;
		int count=0;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Query query = sess
					.createQuery("select count(*) from Worldtracer_Actionfiles where action_file_type=? and day=? and airline=? and station=? and deleted = false");
			query.setParameter(0, ActionFileType.valueOf(type.toUpperCase()));
			query.setParameter(1, int_day);
			query.setParameter(2, airline);
			query.setParameter(3, station);
			String string_count=query.uniqueResult().toString();
			count=Integer.parseInt(string_count);
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (sess != null) {
				sess.close();
			}
		}
		return count;
	}
	public static String getWtRequest(PostMethod method,String cgiexe){

		NameValuePair nvps[] = method.getParameters();
		StringBuffer sb = new StringBuffer();
		sb.append("URL: ");
		sb.append(wt_url + cgiexe);
		sb.append("|||POST INFO: ");
		for (int i=0;i<nvps.length;i++) {
			NameValuePair nvp = nvps[i];
			sb.append(nvp.getName() + "=" + nvp.getValue() + "|||");
		}
		String requestinfo = sb.toString();
		return requestinfo;
	}
	public static String getWtRequest(String wt_url,String cgiexe){

		StringBuffer sb = new StringBuffer();
		sb.append("URL: ");
		sb.append(wt_url + cgiexe);

		String requestinfo = sb.toString();
		return requestinfo;
	}
	

	public static int getContentCategory(String code) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			code = code.replace("/", "");
			Criteria cri = sess.createCriteria(OHD_CategoryType.class).add(Restrictions.like("wtCategory", code)).add(Restrictions.eq("locale", "en"));
			OHD_CategoryType oc = (OHD_CategoryType) cri.list().get(0);
			return oc.getOHD_CategoryType_ID();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
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
	
	public static IncidentForm mapAhlToIncident(IncidentForm i, aero.nettracer.serviceprovider.wt_1_0.common.Ahl a, Agent user) {
		WorldTracerFile wtf=new WorldTracerFile(a.getAhlId(), WorldTracerFile.WTStatus.ACTIVE);
		i.setWtFile(wtf);
		
		i.setAgent(user);
		
		i.setStatus(new Status(TracingConstants.MBR_STATUS_OPEN));
		
		
		if(a.getStationCode()!=null && a.getStationCode().length()==3 && a.getAirlineCode()!=null 
				&& a.getAirlineCode().length()>0){
			Station s=StationBMO.getStationByCode(a.getStationCode(), a.getAirlineCode());
			if(s!=null){
				i.setStationcreated(s);
				i.setStationassigned(s);
			} else {
				i.setStationcreated(user.getStation());
				i.setStationassigned(user.getStation());
			}
		}

		i.setCreatedate(TracerDateTime.getGMTDate());
		i.setCreatetime(TracerDateTime.getGMTDate());

		i.setNumbagchecked(a.getNumberBagsChecked());
		i.setNumpassengers(a.getNumberPaxAffected());
		
		if (a.getFaultReason() != 0) {
			i.setLoss_code(a.getFaultReason());
			Station s=StationBMO.getStationByCode(a.getFaultStation());
			if(s!=null){
				i.setFaultstation(s);
			}
		} else {
			a.setFaultReason(79);
			a.setFaultReasonDescription("Created in error");
		}
		
		i.setRecordlocator(a.getPnrLocator());
		
		ArrayList<com.bagnet.nettracer.tracing.db.Passenger> paxList = new ArrayList<com.bagnet.nettracer.tracing.db.Passenger>();
		if(a.getPax() != null){
			for (aero.nettracer.serviceprovider.wt_1_0.common.Passenger p: a.getPax()){
				if (p != null) {
					com.bagnet.nettracer.tracing.db.Passenger pax = mapAhlPassenger(p);
					paxList.add(pax);
				}
			}
		}
		i.setPassengerlist(paxList);
		
		ArrayList<com.bagnet.nettracer.tracing.db.Itinerary> itinList = new ArrayList<com.bagnet.nettracer.tracing.db.Itinerary>();
		if(a.getPaxItinerary() != null){
			for (aero.nettracer.serviceprovider.wt_1_0.common.Itinerary itin: a.getPaxItinerary()) {
				if (itin != null && itin.getDepartureCity()!=null && !itin.getDepartureCity().isEmpty()
						&& itin.getArrivalCity()!=null && !itin.getArrivalCity().isEmpty()) {
					com.bagnet.nettracer.tracing.db.Itinerary it = mapItinerary(itin,user);
					it.setItinerarytype(TracingConstants.PASSENGER_ROUTING);
					itinList.add(it);
				}
			}
		}
		if(a.getBagItinerary() != null){
			for (aero.nettracer.serviceprovider.wt_1_0.common.Itinerary itin: a.getBagItinerary()) {
				/** NT-1312: Bag Itinerary in WT tend to not have Departure Cities  or Arrival Cities **/
				if (itin != null) {
					com.bagnet.nettracer.tracing.db.Itinerary it = mapItinerary(itin,user);
					it.setItinerarytype(TracingConstants.BAGGAGE_ROUTING);
					itinList.add(it);
				}
			}
		}

		i.setItinerarylist(itinList);
		
		ArrayList<com.bagnet.nettracer.tracing.db.Item> items = new ArrayList<com.bagnet.nettracer.tracing.db.Item>();

		if(a.getItem() != null){
			for (aero.nettracer.serviceprovider.wt_1_0.common.Item item: a.getItem()) {
				if (item != null) {
					mapAhlItem(items, item);
				}
			}
		}
		i.setItemlist(items);
		
		ArrayList<com.bagnet.nettracer.tracing.db.Incident_Claimcheck> claimChecks = new ArrayList<com.bagnet.nettracer.tracing.db.Incident_Claimcheck>();
		if(a.getClaimCheck() != null){
			for (aero.nettracer.serviceprovider.wt_1_0.common.ClaimCheck cc: a.getClaimCheck()) {
				StringBuilder claimCheckNum=new StringBuilder();

				if(cc.getAirlineCode()!=null && cc.getAirlineCode().length()>0){
					claimCheckNum.append(cc.getAirlineCode());
				}
				if(cc.getTagNumber()!=null && cc.getTagNumber().length()>0){
					claimCheckNum.append(cc.getTagNumber());
				}
				if(claimCheckNum.length()>0){
					Incident_Claimcheck cl=new Incident_Claimcheck();
					cl.setClaimchecknum(claimCheckNum.toString());
					claimChecks.add(cl);
				}
			}
		}
		i.setClaimchecklist(claimChecks);
		
		//Remark for FurtherInfo
		List<Remark> remarks=new ArrayList<Remark>();
		i.setRemarklist(remarks);
		Remark rem =i.getRemark(i.getRemarklist().size());
		rem.setCreatetime(new SimpleDateFormat(TracingConstants.DB_DATETIMEFORMAT).format(TracerDateTime.getGMTDate()));
		rem.setAgent(user);
		rem.set_DATEFORMAT(user.getDateformat().getFormat());
		rem.set_TIMEFORMAT(user.getTimeformat().getFormat());
		rem.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone()));
		if(a.getFurtherInfo()!=null && a.getFurtherInfo().length()>0){
			rem.setRemarktext(a.getFurtherInfo());
		}
		
		return i;
	}
	
	private static com.bagnet.nettracer.tracing.db.Passenger mapAhlPassenger(aero.nettracer.serviceprovider.wt_1_0.common.Passenger p) {
		aero.nettracer.serviceprovider.wt_1_0.common.Address ad = p.getAddress();
		com.bagnet.nettracer.tracing.db.Passenger pax = new com.bagnet.nettracer.tracing.db.Passenger();
		com.bagnet.nettracer.tracing.db.Address add = new com.bagnet.nettracer.tracing.db.Address();
		
		pax.addAddress(add);
		
		com.bagnet.nettracer.tracing.db.AirlineMembership membership=new com.bagnet.nettracer.tracing.db.AirlineMembership();
		membership.setMembershipnum(p.getFfNumber());
		membership.setCompanycode_ID(p.getFfAirline());
		membership.setMembershipstatus(p.getFfStatus());
		
		pax.setMembership(membership);
		pax.setFirstname(p.getFirstname());
		pax.setLastname(p.getLastname());
		pax.setMiddlename(p.getMiddlename());
		pax.setSalutation(p.getSalutation());
		pax.setLanguageFreeFlow(p.getLanguageFreeFlow());
		if(ad!=null){
			add.setPassenger(pax);
			add.setAddress1(ad.getAddress1());
			add.setAddress2(ad.getAddress2());
			add.setAltphone(ad.getAltPhone());
			add.setCity(ad.getCity());
			
			add.setCountrycode_ID(ad.getCountryCode());
			
			add.setEmail(ad.getEmailAddress());
			add.setHomephone(ad.getHomePhone());
			add.setMobile(ad.getMobilePhone());
			add.setPager(ad.getPagerNumber());
			add.setProvince(ad.getProvince());
			
			add.setState_ID(ad.getState());
			add.setPermanent(!ad.isTemporaryAddress());
			add.setWorkphone(ad.getWorkPhone());
			add.setZip(ad.getZip());
		}
		return pax;
	}

	private static com.bagnet.nettracer.tracing.db.Itinerary mapItinerary(aero.nettracer.serviceprovider.wt_1_0.common.Itinerary itin, Agent user) {
		com.bagnet.nettracer.tracing.db.Itinerary it = new com.bagnet.nettracer.tracing.db.Itinerary();
		if(itin!=null){
			it.set_DATEFORMAT(user.getDateformat().getFormat());
			it.set_TIMEFORMAT(user.getTimeformat().getFormat());
			it.setAirline(itin.getAirline());
			it.setLegto(itin.getArrivalCity());
			it.setLegto_type(itin.getLegto_type());
	
			it.setLegfrom(itin.getDepartureCity());
			it.setLegfrom_type(itin.getLegfrom_type());
			if(itin.getFlightDate()!=null){
				it.setDepartdate(itin.getFlightDate().getTime());
				it.setDisdepartdate(DateUtils.formatDate(itin.getFlightDate().getTime(), user.getDateformat().getFormat(), null, null));
			}
			
			if(itin.getFlightNumber()!=null){
				String flightNum=itin.getFlightNumber();
				if(flightNum.length()>4){
					flightNum=flightNum.substring(0,4);
				}
				it.setFlightnum(flightNum);
			}
		}
		
		return it;
	}
	
	private static void mapAhlItem(ArrayList<com.bagnet.nettracer.tracing.db.Item> items, aero.nettracer.serviceprovider.wt_1_0.common.Item item) {
		com.bagnet.nettracer.tracing.db.Item it = new com.bagnet.nettracer.tracing.db.Item();
		
		it.setBagnumber(item.getBagNumber());
		it.setColor(item.getColor());
		
		ArrayList<Item_Inventory> contents = new ArrayList<Item_Inventory>();
		if(item.getContent() != null){
			for (aero.nettracer.serviceprovider.wt_1_0.common.Content inv:item.getContent()) {
				Item_Inventory content = new Item_Inventory();
				if(inv.getCategory()!=null){
					OHD_CategoryType ohdct=CategoryBMO.getCategoryByWT(inv.getCategory(), TracingConstants.DEFAULT_LOCALE);
					if(ohdct!=null){
						content.setCategorytype_ID(ohdct.getOHD_CategoryType_ID());
					}	else{
						content.setCategorytype_ID(0);
					}
				}
				
				content.setItem(it);
				if(inv.getDescription()!=null){
					content.setDescription(inv.getDescription().trim().toUpperCase());
				}
				contents.add(content);
			}
		}
		it.setInventorylist(contents);
		int descId=XDescElementsBMO.getXdescelementid(item.getDesc1());
		it.setXdescelement_ID_1(descId);
		descId=XDescElementsBMO.getXdescelementid(item.getDesc2());
		it.setXdescelement_ID_2(descId);
		descId=XDescElementsBMO.getXdescelementid(item.getDesc3());
		it.setXdescelement_ID_3(descId);

		it.setFnameonbag(item.getFirstNameOnBag());
		it.setLnameonbag(item.getLastNameOnBag());
		
		if(item.getManufacturer()!=null && !item.getManufacturer().isEmpty()){
			String manuDesc="";
			if(item.getManufacturer().contains("/")){
				manuDesc=item.getManufacturer().substring(0,item.getManufacturer().indexOf("/"));
			} else {
				manuDesc=item.getManufacturer();
			}
			Manufacturer manu=ManufacturerBMO.getManufacturerByDesc(manuDesc);
			if(manu!=null){			
				it.setManufacturer_ID(manu.getManufacturer_ID());
			} else {
				it.setManufacturer_ID(TracingConstants.MANUFACTURER_OTHER_ID);
				it.setManufacturer_other(manuDesc);
			}
		}
		if(item.getType()!=null){
			try{
				DecimalFormat myFormatter=new DecimalFormat("00");
				int type=Integer.valueOf(item.getType());
				String bdoNum=myFormatter.format(type);
				it.setBagtype(bdoNum);
			} catch (NumberFormatException nfe){
				nfe.printStackTrace();
			}
		}
		
		it.setExternaldesc(item.getExternaldesc());
		
		it.setStatus(new Status(TracingConstants.ITEM_STATUS_OPEN));
		items.add(it);
	}

}