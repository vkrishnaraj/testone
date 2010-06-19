package com.nettracer.claims.core.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.nettracer.claims.core.exception.SimplePersistenceException;
import com.nettracer.claims.core.model.Company;
import com.nettracer.claims.core.model.CountryCode;
import com.nettracer.claims.core.model.DropDown;
import com.nettracer.claims.core.model.Label;
import com.nettracer.claims.core.model.Languages;
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

}
