/*
 * Created on Jan 17, 2007
 *
 * matt
 */
package com.bagnet.clients.airtran;

import java.util.ArrayList;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.airtran.AddBookingCommentsESDocument;
import com.airtran.AddBookingCommentsESResponseDocument;
import com.airtran.Booking;
import com.airtran.EnplanementBookingServiceStub;
import com.airtran.GetBookingInformationESDocument;
import com.airtran.GetBookingInformationESResponseDocument;
import com.airtran.GetEnplanementsESDocument;
import com.airtran.GetEnplanementsESResponseDocument;
import com.bagnet.nettracer.tracing.utils.TracerProperties;

/**
 * @author matt
 * 
 * Preferences - Java - Code Style - Code Templates
 */
public class NewSkiesIntegrationWrapper {
	
	protected static Logger logger = Logger.getLogger(NewSkiesIntegrationWrapper.class);
	
	private String endpoint;
	private String calltype;
	
	private Booking thebook;
	
	private ArrayList callparams = new ArrayList();

	private String errormsg = "";
	private String resultstr = "";

	public NewSkiesIntegrationWrapper() {

	}
	
	public boolean useIntegration(String prop) {
		
		String useIntegration = TracerProperties.get(prop);
		if (useIntegration != null && useIntegration.equals("1")) return true;
		else return false;
	}

	public boolean readBookingProps(String urlprop) {
		Properties properties = new Properties();
		endpoint = TracerProperties.get(urlprop);
		return true;
	}
	
	public boolean readEnplaneProps(String urlprop, String calltype) {
		endpoint = TracerProperties.get(urlprop);
		this.calltype = TracerProperties.get(calltype);
		return true;
	}

	/**
	 * get booking reservation information
	 * @param recordlocator
	 * @return
	 */
	public boolean getBooking(String recordlocator) {
		try {
			
			boolean b1 = readBookingProps("booking.endpoint");
			if (!b1) return false;
			
			EnplanementBookingServiceStub stub = new EnplanementBookingServiceStub(null,endpoint);
			GetBookingInformationESDocument es = GetBookingInformationESDocument.Factory.newInstance();
			GetBookingInformationESDocument.GetBookingInformationES es2 = es.addNewGetBookingInformationES();
			es2.setSRecordLocator(recordlocator);
			
			GetBookingInformationESResponseDocument resEs = stub.GetBookingInformationES(es);
			thebook = resEs.getGetBookingInformationESResponse().getGetBookingInformationESResult();
			

			if (thebook != null) {
				return true;
			} else {
				logger.info("Location 5");
				setErrormsg("error.norecord");
				return false;
			}
	
		} catch (Exception e) {
			e.printStackTrace();
			setErrormsg("error calling webservice: " + e.toString());
			return false;
		}

	}

	/**
	 * update booking comments
	 * @param recordlocator
	 * @param comments
	 * @return
	 */
	public boolean updateBookingComment(String recordlocator, String comments) {
		try {
			
			boolean b1 = readBookingProps("booking.endpoint");
			if (!b1) return false;
			
			EnplanementBookingServiceStub stub = new EnplanementBookingServiceStub(null,endpoint);
			AddBookingCommentsESDocument es = AddBookingCommentsESDocument.Factory.newInstance();
			AddBookingCommentsESDocument.AddBookingCommentsES es2 = es.addNewAddBookingCommentsES();
			es2.setSComments(comments);
			es2.setSRecordLocator(recordlocator);
			AddBookingCommentsESResponseDocument resEs = stub.AddBookingCommentsES(es);
			

			if (resEs != null) return true;
			else return false;
	
		} catch (Exception e) {
			e.printStackTrace();
			setErrormsg("error calling webservice: " + e.toString());
			return false;
		}

	}

	/**
	 * get enplanement data
	 * @param startdate
	 * @param enddate
	 * @param citycode
	 * @return
	 */
	public int getEnplanement(String startdate,String enddate,String citycode) {
		try {
			
			EnplanementBookingServiceStub stub = new EnplanementBookingServiceStub(null,endpoint);
			GetEnplanementsESDocument es = GetEnplanementsESDocument.Factory.newInstance();
			GetEnplanementsESDocument.GetEnplanementsES es2 = es.addNewGetEnplanementsES();
			es2.setSFromDate(startdate);
			es2.setSToDate(enddate);
			es2.setSCityCode(citycode);
			es2.setSWcWoc(calltype);
			
			GetEnplanementsESResponseDocument resEs = stub.GetEnplanementsES(es);
			return resEs.getGetEnplanementsESResponse().getGetEnplanementsESResult();
	
		} catch (Exception e) {
			e.printStackTrace();
			setErrormsg("error calling webservice: " + e.toString());
			return -1;
		}

	}
	/**
	 * @return Returns the errormsg.
	 */
	public String getErrormsg() {
		return errormsg;
	}

	/**
	 * @param errormsg
	 *          The errormsg to set.
	 */
	public void setErrormsg(String errormsg) {
		this.errormsg = errormsg;
	}
	
	/**
	 * @return Returns the resultstr.
	 */
	public String getResultstr() {
		return resultstr;
	}
	/**
	 * @param resultstr The resultstr to set.
	 */
	public void setResultstr(String resultstr) {
		this.resultstr = resultstr;
	}
	
	
	/**
	 * @return Returns the callparams.
	 */
	public ArrayList getCallparams() {
		return callparams;
	}
	/**
	 * @param callparams The callparams to set.
	 */
	public void setCallparams(ArrayList callparams) {
		this.callparams = callparams;
	}
	/**
	 * @return Returns the endpoint.
	 */
	public String getEndpoint() {
		return endpoint;
	}
	/**
	 * @param endpoint The endpoint to set.
	 */
	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	
	/**
	 * @return Returns the calltype.
	 */
	public String getCalltype() {
		return calltype;
	}
	/**
	 * @param calltype The calltype to set.
	 */
	public void setCalltype(String calltype) {
		this.calltype = calltype;
	}
	/**
	 * @return Returns the thebook.
	 */
	public Booking getThebook() {
		return thebook;
	}
	/**
	 * @param thebook The thebook to set.
	 */
	public void setThebook(Booking thebook) {
		this.thebook = thebook;
	}

}