package com.bagnet.nettracer.wt;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.BDO;
import com.bagnet.nettracer.tracing.db.Company_Specific_Variable;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.WT_FWD_Log;
import com.bagnet.nettracer.tracing.db.WT_Info;
import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles;
import com.bagnet.nettracer.tracing.db.WorldTracerFile.WTStatus;
import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles.ActionFileType;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.HibernateUtils;

public class WorldTracerUtils {
	// private static String wt_user = "Air@18maR";
	// private static String wt_pass = "Tran$615J";

	// public static String wt_suffix_airline;

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
	private static String error;
	private static Logger logger = Logger.getLogger(WorldTracerUtils.class);

	public static String SendTty(HttpClient client, String companycode,
			ArrayList ttylist) {
		String responseBody = null;
		wt_http = WorldTracerUtils.getWt_url(companycode);
		wt_url = "http://" + wt_http + "/";
		String cgiexe = "cgi-bin/bagTTY.exe";
		String getstring = wt_url + cgiexe;
		getstring = getstring.replace(" ", "+");

		PostMethod method = new PostMethod(getstring);
		method.setDoAuthentication(true);
		method.addParameter("A1", companycode.toLowerCase());
		method.addParameter("A2", "WM");
		method.addParameter("TX1", ttylist.get(0).toString());
		method.addParameter("TX2", ttylist.get(1).toString());
		method.addParameter("TX3", ttylist.get(2).toString());
		method.addParameter("TX4", ttylist.get(3).toString());
		method.addParameter("OA", ttylist.get(4).toString());
		method.addParameter("CA", ttylist.get(5).toString());
		method.addParameter("FTYP1", ttylist.get(6).toString());
		method.addParameter("FTYP2", ttylist.get(7).toString());
		method.addParameter("FTYP3", ttylist.get(8).toString());
		method.addParameter("FTYP4", ttylist.get(9).toString());
		method.addParameter("FREF1", ttylist.get(10).toString());
		method.addParameter("FREF2", ttylist.get(11).toString());
		method.addParameter("FREF3", ttylist.get(12).toString());
		method.addParameter("FREF4", ttylist.get(13).toString());
		method.addParameter("TTYTXT", ttylist.get(14).toString());

		// Provide custom retry handler is necessary
		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler(3, false));
		try {
			
			String requestInfo = WorldTracerUtils.getWtRequest(wt_url, cgiexe);
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

	/**
	 * get the NT agent to use for wt inserts
	 */
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

			List list = q.list();
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
		// method.setQueryString("OPT=A");
		// method.setQueryString("ARL=" + companycode.toUpperCase());
		// method.setQueryString("STN=" + stationcode.toUpperCase());

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
			// Execute the method.
			/*
			int statusCode = client.executeMethod(method);

			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + method.getStatusLine());
			}
*/
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

	/**
	 * find incident by using the worldtracer id column to determine if the
	 * incident is new or not
	 * 
	 * @param wt_id
	 * @return
	 * @throws HibernateException
	 */
	public static Incident findIncidentByWTID(String wt_id)
			throws HibernateException {
		Session sess = HibernateWrapper.getSession().openSession();

		try {
			Query q = sess
					.createQuery("from com.bagnet.nettracer.tracing.db.Incident incident where incident.wtFile.wt_id = :wt_id");
			q.setParameter("wt_id", wt_id);
			List list = q.list();

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
					.createQuery("from com.bagnet.nettracer.tracing.db.OHD ohd where ohd.wt_id = :wt_id");
			q.setParameter("wt_id", wt_id);
			List list = q.list();

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
			cri.add(Expression.eq("action_file_text", actionfile_text));
			List list = cri.list();
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
			cri.add(Expression.eq("id", ID));
			List list = cri.list();
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
			cri.add(Expression.eq("wt_ohd_id", wt_id));
			List list = cri.list();
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
			cri.add(Expression.eq("wt_incident_id", wt_incident_id));
			List list = cri.list();
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
			cri.add(Expression.eq("action_file_type", type));
			cri.add(Expression.eq("day", new Integer(day)));
			cri.add(Expression.eq("airline", airline));
			cri.add(Expression.eq("station", station));
			cri.add(Expression.eq("deleted", false));


			if (rowsperpage > 0) {
				int startnum = currpage * rowsperpage;
				cri.setFirstResult(startnum);
				cri.setMaxResults(rowsperpage);
			}
			ArrayList<Worldtracer_Actionfiles> list = (ArrayList) cri.list();
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
					.createQuery("from com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles wa where wa.id = :af_id");
			q.setInteger("af_id", af_id);
			List list = q.list();

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
			cri.add(Expression.eq("action_file_type", ActionFileType.valueOf(wt_type.toUpperCase())));
			// cri.add(Expression.eq("action_file_type","WM"));
			if (rowsperpage > 0) {
				int startnum = currpage * rowsperpage;
				cri.setFirstResult(startnum);
				cri.setMaxResults(rowsperpage);
			}

			if (ahl_id != null && ahl_id.length() > 0)
				cri.add(Expression.eq("wt_incident_id", ahl_id));
			if (ohd_id != null && ohd_id.length() > 0)
				cri.add(Expression.eq("wt_ohd_id", ohd_id));
			ArrayList<Worldtracer_Actionfiles> list = (ArrayList) cri.list();
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
			List list = q.list();
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

	public void setError(String error) {
		this.error = error;
	}

	public static BDO findBDOByID(int BDO_ID) {
		Session sess = null;

		try {
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess
					.createQuery("from com.bagnet.nettracer.tracing.db.BDO bdo where bdo.BDO_ID= :BDO_ID");
			q.setParameter("BDO_ID", BDO_ID);
			List list = q.list();

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
		try {
			Session session = HibernateWrapper.getSession().openSession();
			Transaction tx = session.beginTransaction();
			WT_Info wt_info = new WT_Info();
			wt_info.setRequestContext(requestContext);
			wt_info.setResponseContext(responseContext);
			session.save(wt_info);
			tx.commit();
			session.close();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}
//count the number of the WorldTracer-ActionFile ,and make it as a property of the class CountActionFile 
	//and put the CountActionFile object into the list. 
	public static List<CountActionFile> countActionFile(String airline, String station) {
		List<CountActionFile> list = new ArrayList<CountActionFile>();
		int count;
		Session session;
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
	/*
	public static String getPostWtRequest(GetMethod method,String cgiexe)
	{
		StringBuffer sb=new StringBuffer();
		sb.append("URL: ");
		try {
			sb.append(method.getURI().toString());
		} catch (URIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	public static void insertAmendWtString(IncidentForm incform,Incident inc){
		//incform.
	}
	*/
}