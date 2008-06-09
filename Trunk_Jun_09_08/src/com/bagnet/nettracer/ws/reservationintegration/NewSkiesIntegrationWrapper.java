/*
 * Created on Jan 17, 2007
 *
 * matt
 */
package com.bagnet.nettracer.ws.reservationintegration;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import com.airtran.AddBookingCommentsESDocument;
import com.airtran.AddBookingCommentsESResponseDocument;
import com.airtran.EnplanementBookingServiceStub;
import com.airtran.GetBookingInformationESDocument;
import com.airtran.GetBookingInformationESResponseDocument;
import com.airtran.GetEnplanementsESDocument;
import com.airtran.GetEnplanementsESResponseDocument;
import com.navitaire.schemas.messages.booking.Booking;

/**
 * @author matt
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class NewSkiesIntegrationWrapper {
	private String propertyfile;
	private String endpoint;
	private String calltype;
	
	private Booking thebook;
	
	private ArrayList callparams = new ArrayList();

	private String errormsg = "";
	private String resultstr = "";

	public NewSkiesIntegrationWrapper() {
		propertyfile = NewSkiesIntegrationWrapper.class.getResource("/integration.properties").getPath();
	}
	
	public boolean useIntegration(String prop) {
		Properties properties = new Properties();
		if (propertyfile == null) return false;
		try {
			properties.load(new FileInputStream(propertyfile));
			String useIntegration = properties.getProperty(prop);
			if (useIntegration != null && useIntegration.equals("1")) return true;
			else return false;
		} catch (IOException e) {
			// unable to get config
			setErrormsg("Unable to retrieve properties from integration.properties file");
			return false;
		}
	}

	public boolean readBookingProps(String urlprop) {
		Properties properties = new Properties();
		if (propertyfile == null) return false;
		try {
			properties.load(new FileInputStream(propertyfile));
			endpoint = properties.getProperty(urlprop);


		} catch (IOException e) {
			// unable to get config
			setErrormsg("Unable to retrieve properties from integration.properties file");
			return false;
		}
		return true;
	}
	
	public boolean readEnplaneProps(String urlprop, String calltype) {
		Properties properties = new Properties();
		if (propertyfile == null) return false;
		try {
			properties.load(new FileInputStream(propertyfile));
			endpoint = properties.getProperty(urlprop);
			this.calltype = properties.getProperty(calltype);

		} catch (IOException e) {
			// unable to get config
			setErrormsg("Unable to retrieve properties from integration.properties file");
			return false;
		}
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