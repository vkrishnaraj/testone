package com.nettracer.claims.core.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.nettracer.claims.core.model.Localetext;
import com.nettracer.claims.hibernate.HibernateDaoSupport;

/**
 * @author utpal.patra
 * 
 */

@Repository
public class PassengerDao extends HibernateDaoSupport {
	private static Logger logger = Logger.getLogger(PassengerDao.class);
	
	private static final String PASSENGER_LOGIN = "Login";
	private static final String DIRECTION = "Direction";
	private static final String FLIGHT_INFO = "Flight";
	private static final String BAGGAGE_INFO = "Baggage";
	private static final String GENERAL_INFO = "General";
	private static final String UPLOAD_INFO = "Upload";
	private static final String FRAUD_QUESTIONS = "Fraud";
	private static final String SUBMIT_CLAIM = "Submit";
	private static final String SAVED_SREEN = "Saved";
	
	
	public List<Localetext> getPassengerContents(String languageSelected) {
		// TODO Auto-generated method stub
		return null;
	}

	
	

}
