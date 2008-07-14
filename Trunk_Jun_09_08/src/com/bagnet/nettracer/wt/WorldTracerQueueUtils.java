package com.bagnet.nettracer.wt;

import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Locale;

import javax.mail.internet.InternetAddress;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.MessageResources;
import org.apache.struts.validator.DynaValidatorForm;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.bagnet.nettracer.email.HtmlEmail;
import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Address;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.AirlineMembership;
import com.bagnet.nettracer.tracing.db.Articles;
import com.bagnet.nettracer.tracing.db.Billing;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Incident_Claimcheck;
import com.bagnet.nettracer.tracing.db.Item;
import com.bagnet.nettracer.tracing.db.ItemType;
import com.bagnet.nettracer.tracing.db.Item_Inventory;
import com.bagnet.nettracer.tracing.db.Itinerary;
import com.bagnet.nettracer.tracing.db.Passenger;
import com.bagnet.nettracer.tracing.db.Remark;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.WT_FWD_Log_Itinerary;
import com.bagnet.nettracer.tracing.db.WT_Queue;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.WT_FWD_Log;
import com.bagnet.nettracer.tracing.db.WT_ROH;
import com.bagnet.nettracer.tracing.db.WT_TTY;
import com.bagnet.nettracer.tracing.db.audit.Audit_Station;
import com.bagnet.nettracer.tracing.forms.IncidentForm;
import com.bagnet.nettracer.tracing.forms.OnHandForm;
import com.bagnet.nettracer.tracing.forms.WorldTracerFWDForm;
import com.bagnet.nettracer.tracing.forms.WorldTracerTTYForm;
import com.bagnet.nettracer.tracing.utils.BagService;
import com.bagnet.nettracer.tracing.utils.BillingUtils;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.EmailParser;
import com.bagnet.nettracer.tracing.utils.HibernateUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.audit.AuditStationUtils;

public class WorldTracerQueueUtils {
	private String error;
	private static Logger logger = Logger.getLogger(WorldTracerQueueUtils.class);
	
	/**
	 * 
	 * @param obj
	 * @throws Exception
	 */
	public static boolean saveWtobj(Incident iDTO, IncidentForm theform,WT_Queue obj, Agent user) throws Exception {
		BeanUtils.copyProperties(iDTO, theform);
		Session sess = null;
		Transaction t = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			t = sess.beginTransaction();
			//Figure out if new or old.
			boolean isNew = obj.getWt_queue_id() != 0 ? false: true;
			if (isNew) {
				sess.save(obj);
			} else {
				sess.saveOrUpdate(obj);
			}



			t.commit();
			return true;
		} catch (Exception e) {
			
			if (t != null) {
				t.rollback();
				return false;
			}
			throw e;
		} finally {
			if (sess != null) sess.close();
		}
	}
	public static void saveOhdobj(OHD oDTO, OnHandForm theform,WT_Queue obj, Agent user) throws Exception {
		BeanUtils.copyProperties(oDTO, theform);
		Session sess = null;
		Transaction t = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			t = sess.beginTransaction();
			//Figure out if new or old.
			boolean isNew = obj.getWt_queue_id() != 0 ? false: true;
			if (isNew) {
				sess.save(obj);
			} else {
				sess.saveOrUpdate(obj);
			}



			t.commit();
		
		} catch (Exception e) {
			if (t != null) {
				t.rollback();
				
			}
			throw e;
		} finally {
			if (sess != null) sess.close();
		}
	}
	public static boolean saveFwdobj(WorldTracerFWDForm theform,WT_Queue obj, Agent user) throws Exception {
		
		Session sess = null;
		Transaction t = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			t = sess.beginTransaction();
			//Figure out if new or old.
			boolean isNew = obj.getWt_queue_id() != 0 ? false: true;
			if (isNew) {
				sess.save(obj);
			} else {
				sess.saveOrUpdate(obj);
			}



			t.commit();
			return true;
		} catch (Exception e) {
			if (t != null) {
				t.rollback();
				return false;
			}
			throw e;
		} finally {
			if (sess != null) sess.close();
		}
	}
	
	public  boolean saveWtobj(WT_Queue obj, Agent user) throws Exception {
		
		Session sess = null;
		Transaction t = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			t = sess.beginTransaction();
			//Figure out if new or old.
			boolean isNew = obj.getWt_queue_id() != 0 ? false: true;
			if (isNew) {
				sess.save(obj);
			} else {
				sess.saveOrUpdate(obj);
			}



			t.commit();
			return true;
		} catch (Exception e) {
			
			if (t != null) {
				t.rollback();
				return false;
			}
			throw e;
		} finally {
			if (sess != null) sess.close();
		}
	}
	public  void saveOhdobj(WT_Queue obj, Agent user) throws Exception {

		Session sess = null;
		Transaction t = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			t = sess.beginTransaction();
			//Figure out if new or old.
			boolean isNew = obj.getWt_queue_id() != 0 ? false: true;
			if (isNew) {
				sess.save(obj);
			} else {
				sess.saveOrUpdate(obj);
			}



			t.commit();
		
		} catch (Exception e) {
			if (t != null) {
				t.rollback();
				
			}
			throw e;
		} finally {
			if (sess != null) sess.close();
		}
	}
	public String postWTROH(HttpClient client,WT_ROH inc) {
		// make the request
		String company = inc.getRoh_agent_id().getCompanycode_ID();
		String ahl_id = inc.getWt_ahl_id();
		StringBuffer roh_string = new StringBuffer();
		
		String ohd_id = inc.getWt_ohd_id().toUpperCase();
		String fi = inc.getFi().toUpperCase();
		String ag = inc.getAg().toUpperCase();
		String tx1 = inc.getTeletype_address1().toUpperCase();
		String tx2 = inc.getTeletype_address2().toUpperCase();
		String tx3 = inc.getTeletype_address3().toUpperCase();
		String tx4 = inc.getTeletype_address4().toUpperCase();
		String name = inc.getLname().toUpperCase();
		
		roh_string.append(".roh " + ohd_id);
		if (fi.length() > 0) roh_string.append(".FI " + fi);
		if (ag.length() > 0) roh_string.append(".AG " + ag);
		if (tx1.length() > 0) roh_string.append(".TX " + tx1);
		if (tx2.length() > 0) roh_string.append(".TX " + tx2);
		if (tx3.length() > 0) roh_string.append(".TX " + tx3);
		if (tx4.length() > 0) roh_string.append(".TX " + tx4);
		if (name.length() > 0) roh_string.append(".NM " + name);
		
		//HttpClient client = WorldTracerUtils.connectWT(WorldTracerUtils.wt_suffix_airline + "/",company);
		//String test = WorldTracerUtils.postROH(client, company, ahl_id,roh_string.toString());
		
		// for now return roh_string
		//test = roh_string.toString();
		String test = "ok";
		return test;
	}
	public String postWTTTY(HttpClient client,WT_TTY inc) {
		// make the request
		String company = inc.getTty_agent().getCompanycode_ID();
		StringBuffer tty_string = new StringBuffer();
		String tx1 = inc.getTeletype_address1().toUpperCase();
		String tx2 = inc.getTeletype_address2().toUpperCase();
		String tx3 = inc.getTeletype_address3().toUpperCase();
		String tx4 = inc.getTeletype_address4().toUpperCase();
		String oa = inc.getOrigin_address().toUpperCase();
		String ca = inc.getAirline_code().toUpperCase();
		String ftyp1 = inc.getFile_type1().toUpperCase();
		String ftyp2 = inc.getFile_type2().toUpperCase();
		String ftyp3 = inc.getFile_type3().toUpperCase();
		String ftyp4 = inc.getFile_type4().toUpperCase();
		String fref1 = inc.getFile_reference1().toUpperCase();
		String fref2 = inc.getFile_reference2().toUpperCase();
		String fref3 = inc.getFile_reference3().toUpperCase();
		String fref4 = inc.getFile_reference4().toUpperCase();
		String ttytxt = inc.getText().toUpperCase();
		

        ArrayList <String> ttylist = new ArrayList();
        ttylist.add(tx1);
        ttylist.add(tx2);
        ttylist.add(tx3);
        ttylist.add(tx4);
        ttylist.add(oa);
        ttylist.add(ca);
        ttylist.add(ftyp1);
        ttylist.add(ftyp2);
        ttylist.add(ftyp3);
        ttylist.add(ftyp4);
        ttylist.add(fref1);
        ttylist.add(fref2);
        ttylist.add(fref3);
        ttylist.add(fref4);
        ttylist.add(ttytxt);
        		
		
		//String test = WorldTracerUtils.SendTty(client,company,ttylist);
		
		// for now return ohd_string
		//test = tty_string.toString();
		String test= "ok";
		return test;
	}
	public String  insertfwd(HttpClient client, String companycode,WT_FWD_Log wtfwd, int filenum){
		String _n = ".";
		String _t = "";
		String _h = "/";
		String responseBody = null;
		

        //retrieve fwd
		WorldTracerUtils wtutil = new WorldTracerUtils();
		WT_FWD_Log fwd = wtutil.findFWDByID(wtfwd.getWt_fwd_log_id());
		if (fwd == null) {
			setError("invalid wt_fwd_log filenum");
			return null;
		}
		// generate post string _s
		StringBuffer sb = new StringBuffer();

		sb.append("STNARL" + _t);
		//sb.append(fwd.gegetHoldingStation().getStationcode());
		sb.append(TracerUtils.getStation(fwd.getFwd_station_id()));
		sb.append(TracerUtils.getStation(fwd.getFwd_station_id()).getStationcode());
		sb.append(_n);
		// passengers last name

		sb.append("NM" + _t + fwd.getPassenger1());
		sb.append(_h + fwd.getPassenger2());
		sb.append(_h + fwd.getPassenger3());
		sb.append(_n);
		// flight date
		boolean hasflight = false;
		if (fwd.getItinerary() != null && fwd.getItinerary().size() > 0) {
			String temp;
			int c = 0;
			
			for (Iterator i = fwd.getItinerary().iterator(); i.hasNext();) {
				WT_FWD_Log_Itinerary op = (WT_FWD_Log_Itinerary) i.next();
				temp = op.getAirline() + op.getFlightnum() + "/";
				if (DateUtils.formatDate(op.getDepartdate(), "ddMMM", null, null) != null) temp += DateUtils.formatDate(op.getDepartdate(), "ddMMM", null, null);
				if (c == 0) {
					if (temp.trim().length() > 1) sb.append("FD" + _t + temp.toUpperCase());
				} else {
					if (temp.trim().length() > 1) sb.append(_h + temp.toUpperCase());
				}
				if (temp.trim().length() > 1) {hasflight=true;c++;}
				if (c >= 4) break;
			}
		}
		sb.append(_n);
		if (!hasflight) {
			error = "Please enter a valid flight/date itinerary";
			return null;
		}
		//Tag Number
		if(fwd.getBagtag() != null){
			sb.append("TN"+_t+fwd.getBagtag()+_n);
		}
		//Expedite Number
		if(fwd.getEbagtag() != null){
			sb.append("XT"+_t+fwd.getEbagtag()+_n);
		}
		//Forward Bags
		sb.append("FB"+_t+"1"+_n);
		//Reason For Loss
		if(wtfwd.getReason_for_loss()!=null){
		  sb.append("RL"+_t+wtfwd.getReason_for_loss()+_n);
		}
		//Reason Comments
		if(wtfwd.getReason_for_loss()!=null){
		  sb.append("RC"+_t+wtfwd.getReason_for_loss()+_n);
		}
		//Fault Station
		if(wtfwd.getFault_station()!=null){
		  sb.append("FS"+_t+wtfwd.getFault_station()+_n);
		}
		//Fault Terminal
		if(wtfwd.getFault_terminal()!=null){
		  sb.append("FT"+_t+wtfwd.getFault_terminal()+_n);
		}
		//Handled Airline Copy
		  sb.append("HC"+_t+"Y"+_n);
		//Supplementary Information
		//Teletype Address
		if(wtfwd.getTeletype_address1()!=null){
		  sb.append("TX"+_t+wtfwd.getTeletype_address1()+_n);
		}
		if(wtfwd.getTeletype_address2()!=null){
		  sb.append(wtfwd.getTeletype_address2()+_n);
		}
		if(wtfwd.getTeletype_address3()!=null){
		  sb.append(wtfwd.getTeletype_address3()+_n);
		}
		if(wtfwd.getTeletype_address4()!=null){
		  sb.append(wtfwd.getTeletype_address4()+_n);
		}
		  
	    // agent
		  sb.append("AG" + _t + fwd.getForwarding_agent().getUsername() + "/" + fwd.getForwarding_agent().getCompanycode_ID() + _n);
		// replace string
			String wtstring = sb.toString();
			wtstring = wtstring.replace("\n", "");
			wtstring = wtstring.replace("\r", "");
			wtstring = wtstring.replace("STN", "");
			wtstring = wtstring.replace("ARL", "");
			wtstring = wtstring.toUpperCase();	
			if (wtstring.substring(wtstring.length()-2).equals("..")) wtstring = wtstring.substring(0,wtstring.length()-2);
			String encodedstring = wtstring.substring(5);
			String tempstring = encodedstring;
			try {
				tempstring = URLEncoder.encode(tempstring,"UTF-8");
			} catch (Exception e) {}
			//String getstring = WorldTracerUtils.wt_url + "cgi-bin/bagOHD.exe";
			String getstring = WorldTracerUtils.wt_url + "cgi-bin/bagFWD.exe?A1=" + companycode.toLowerCase() + "&A2=WM&STNARL="+wtstring.substring(0,5) + "&FWD=" + tempstring;
			getstring = getstring.replace(" ", "+");
			GetMethod method = null;
			try {
				
				method = new GetMethod(getstring);


				method.setDoAuthentication(true);
		
				// Provide custom retry handler is necessary
				method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));

			
				// Execute the method.

				

				int statusCode = client.executeMethod(method);


				if (statusCode != HttpStatus.SC_OK) {
					System.err.println("Method failed: " + method.getStatusLine());
				}

				// Read the response body.
				responseBody = method.getResponseBodyAsString();
				
		
			} catch (HttpException e) {
				System.err.println("Fatal protocol violation: " + e.getMessage());
				e.printStackTrace();
				error = "fatal protocal violation" + e.toString();
				return null;
			} catch (Exception e) {
				System.err.println("Fatal error: " + e.getMessage());
				e.printStackTrace();
				error = "fatal error" + e.toString();
				return null;
			} finally {
				// Release the connection.
				if (method != null) method.releaseConnection();
								
			}
		return responseBody;
	}
	public void setError(String error) {
		this.error = error;
	}
}
