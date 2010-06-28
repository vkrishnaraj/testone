package com.nettracer.claims.core.service;

import java.util.List;

import com.nettracer.claims.core.exception.SimplePersistenceException;
import com.nettracer.claims.core.model.Airport;
import com.nettracer.claims.core.model.CountryCode;
import com.nettracer.claims.core.model.Localetext;
import com.nettracer.claims.core.model.MultilingualLabel;

/**
 * @author Utpal Patra
 * 
 */
public interface PassengerService {
	public List<Localetext> getPassengerLoginContents(String languageSelected) throws SimplePersistenceException;

	public List<Localetext> getPassengerDirection(String selectedLanguage)throws SimplePersistenceException;

	public MultilingualLabel getPassengerInfo(String selectedLanguage, Long baggageState) throws SimplePersistenceException;

	public MultilingualLabel getFlightLabels(String selectedLanguage, Long baggageState) throws SimplePersistenceException;

	public List<CountryCode> getCountries() throws SimplePersistenceException;

	public List<Airport> getAirportList() throws SimplePersistenceException;

	public MultilingualLabel getSubmitClaimLabel(String selectedLanguage,
			Long baggageState) throws SimplePersistenceException;

	public MultilingualLabel getSavedScreenLabel(String selectedLanguage,
			Long baggageState) throws SimplePersistenceException;

}
