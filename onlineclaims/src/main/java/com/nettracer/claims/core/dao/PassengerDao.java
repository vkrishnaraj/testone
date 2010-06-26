package com.nettracer.claims.core.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.nettracer.claims.core.exception.SimplePersistenceException;
import com.nettracer.claims.core.model.Airport;
import com.nettracer.claims.core.model.CountryCode;
import com.nettracer.claims.core.model.Languages;
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
	
	
	/**
	 * Passenger side label display.
	 * 
	 * @param languageSelected
	 * @return
	 */

	@SuppressWarnings("unchecked")
	public List<Localetext> getPassengerLoginContents(String languageSelected) throws SimplePersistenceException{
		logger.debug("Calling getPassengerLoginContents to fetch all the Passenger Login labels");
		
		Long languageId = getLanguageId(languageSelected);
		
		return (languageId == null ? null : (List<Localetext>) getHibernateTemplate().find(
				"SELECT lt FROM Localetext lt " +
				"INNER JOIN FETCH lt.languages lg " +
				"INNER JOIN FETCH lt.label lb " +
				"WHERE lg.id = ? AND lb.page LIKE ? "
				,new Object[]{languageId, "%"+PASSENGER_LOGIN}));
	}
	
	

	private Long getLanguageId(String languageDescription)throws SimplePersistenceException{
		logger.debug("Calling getLanguageId to get the language id");
		if(null == languageDescription || languageDescription.equalsIgnoreCase("Please Select a Language")){
			return null;
		}
		return ((Languages) getHibernateTemplate().find(
				"from Languages where description= ?", languageDescription)
				.get(0)).getId();
	}



	@SuppressWarnings("unchecked")
	public List<Localetext> getPassengerDirection(String selectedLanguage) throws SimplePersistenceException{
		Long languageId = getLanguageId(selectedLanguage);
		
		return (languageId == null ? null : (List<Localetext>) getHibernateTemplate().find(
				"SELECT lt FROM Localetext lt " +
				"INNER JOIN FETCH lt.languages lg " +
				"INNER JOIN FETCH lt.label lb " +
				"WHERE lg.id = ? AND lb.page LIKE ? "
				,new Object[]{languageId, "%"+DIRECTION}));
	}



	@SuppressWarnings("unchecked")
	public List<Localetext> getPassengerInfo(String selectedLanguage) throws SimplePersistenceException{
		Long languageId = getLanguageId(selectedLanguage);
		
		return (languageId == null ? null : (List<Localetext>) getHibernateTemplate().find(
				"SELECT lt FROM Localetext lt " +
				"INNER JOIN FETCH lt.languages lg " +
				"INNER JOIN FETCH lt.label lb " +
				"WHERE lg.id = ? AND lb.page LIKE ? "
				,new Object[]{languageId, "Passenger Information"}));
	}


	@SuppressWarnings("unchecked")
	public List<Localetext> getFlightLabels(String selectedLanguage)  throws SimplePersistenceException{
		Long languageId = getLanguageId(selectedLanguage);
		return (languageId == null ? null : (List<Localetext>) getHibernateTemplate().find(
				"SELECT lt FROM Localetext lt " +
				"INNER JOIN FETCH lt.languages lg " +
				"INNER JOIN FETCH lt.label lb " +
				"WHERE lg.id = ? AND lb.page = ? "
				,new Object[]{languageId, "Flight Information"}));
	}



	@SuppressWarnings("unchecked")
	public List<CountryCode> getCountries()  throws SimplePersistenceException {
		return (List<CountryCode>) getHibernateTemplate().loadAll(CountryCode.class);
	}



	@SuppressWarnings("unchecked")
	public List<Airport> getAirportList() {
		return (List<Airport>)getHibernateTemplate().loadAll(Airport.class) ;
	}
	

}
