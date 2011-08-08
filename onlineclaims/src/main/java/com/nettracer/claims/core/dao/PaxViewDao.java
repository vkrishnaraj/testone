package com.nettracer.claims.core.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.nettracer.claims.core.exception.SimplePersistenceException;
import com.nettracer.claims.core.model.Airline;
import com.nettracer.claims.core.model.Airport;
import com.nettracer.claims.core.model.Company;
import com.nettracer.claims.core.model.CountryCode;
import com.nettracer.claims.core.model.DropDown;
import com.nettracer.claims.core.model.Label;
import com.nettracer.claims.core.model.StateCode;
import com.nettracer.claims.core.model.Languages;
import com.nettracer.claims.core.model.Localetext;
import com.nettracer.claims.hibernate.HibernateDaoSupport;

/**
 * @author utpal.patra
 * 
 */

@Repository
public class PaxViewDao extends HibernateDaoSupport {
	private static Logger logger = Logger.getLogger(PaxViewDao.class);

	private static final String PASSENGER_LOGIN = "Passenger Login";
	private static final String DIRECTION = "Direction";
	private static final String PASSENGER_INFO = "Passenger Information";
	private static final String FLIGHT_INFO = "Flight Information";
	private static final String BAGGAGE_INFO = "Baggage Information";
	private static final String UPLOAD_INFO = "File Upload";
	private static final String FRAUD_QUESTIONS = "Fraud Question";
	private static final String SUBMIT_CLAIM = "Submit Claim";
	private static final String SAVED_SREEN = "Saved Screen";
	private static final String GENERAL_INFO = "General";

	public List<Localetext> getGeneralContents(String selectedLanguage)
			throws SimplePersistenceException {
		return getPageLabels(selectedLanguage, GENERAL_INFO);
	}

	public List<Localetext> getPassengerLoginContents(String selectedLanguage)
			throws SimplePersistenceException {
		logger.debug("Calling getPassengerLoginContents to fetch all the Passenger Login labels");
		return getPageLabels(selectedLanguage, PASSENGER_LOGIN);
	}

	public List<Localetext> getPassengerDirection(String selectedLanguage)
			throws SimplePersistenceException {
		return getPageLabels(selectedLanguage, DIRECTION);
	}

	public List<Localetext> getPassengerInfo(String selectedLanguage)
			throws SimplePersistenceException {
		return getPageLabels(selectedLanguage, PASSENGER_INFO);
	}

	public List<Localetext> getFlightLabels(String selectedLanguage)
			throws SimplePersistenceException {
		return getPageLabels(selectedLanguage, FLIGHT_INFO);
	}

	public List<Localetext> getBagDetailsLabel(String selectedLanguage)
			throws SimplePersistenceException {
		return getPageLabels(selectedLanguage, BAGGAGE_INFO);
	}

	public List<Localetext> getFileUploadLabel(String selectedLanguage)
			throws SimplePersistenceException {
		return getPageLabels(selectedLanguage, UPLOAD_INFO);
	}

	public List<Localetext> getFraudQuestionLabel(String selectedLanguage)
			throws SimplePersistenceException {
		return getPageLabels(selectedLanguage, FRAUD_QUESTIONS);
	}

	public List<Localetext> getSubmitClaimLabel(String selectedLanguage)
			throws SimplePersistenceException {
		return getPageLabels(selectedLanguage, SUBMIT_CLAIM);
	}

	public List<Localetext> getSavedScreenLabel(String selectedLanguage)
			throws SimplePersistenceException {
		return getPageLabels(selectedLanguage, SAVED_SREEN);
	}

	@SuppressWarnings("unchecked")
	public List<Localetext> getPageLabels(String selectedLanguage,
			String pageName) throws SimplePersistenceException {
		Long languageId = getLanguageId(selectedLanguage);
		return (languageId == null ? null
				: (List<Localetext>) getHibernateTemplate().find(
						"SELECT lt FROM Localetext lt "
								+ "INNER JOIN FETCH lt.languages lg "
								+ "INNER JOIN FETCH lt.label lb "
								+ "WHERE lg.id = ? AND lb.page = ? ",
						new Object[] { languageId, pageName }));
	}

	private Long getLanguageId(String languageDescription)
			throws SimplePersistenceException {
		logger.debug("Calling getLanguageId to get the language id");
		if (null == languageDescription
				|| languageDescription
						.equalsIgnoreCase("Please Select a Language")) {
			return null;
		}
		return ((Languages) getHibernateTemplate().find(
				"from Languages where description= ?", languageDescription)
				.get(0)).getId();
	}

	@SuppressWarnings("unchecked")
	public List<CountryCode> getCountries() throws SimplePersistenceException {
		// return (List<CountryCode>)
		// getHibernateTemplate().loadAll(CountryCode.class);
		logger.error(">>>>> getting countries with order by country name ...");
		return (List<CountryCode>) getAllOfItWithOrderBy(CountryCode.class,
				"country");
	}

	@SuppressWarnings("unchecked")
	public List<StateCode> getStates() throws SimplePersistenceException {
		// return (List<CountryCode>)
		// getHibernateTemplate().loadAll(CountryCode.class);
		logger.error(">>>>> getting states with order by state name ...");
		return (List<StateCode>) getAllOfItWithOrderBy(StateCode.class, "state");
	}

	protected List getAllOfItWithOrderBy(Class myclass, String orderBy) {
		return getHibernateTemplate()
				.findByCriteria(
						DetachedCriteria.forClass(myclass).addOrder(
								Order.asc(orderBy)));
	}

	@SuppressWarnings("unchecked")
	public List<Airport> getAirportList() {
		return (List<Airport>) getHibernateTemplate().loadAll(Airport.class);
	}

	@SuppressWarnings("unchecked")
	public List<Airport> getAirportList(String compare) {
		List<Airport> toReturn = new ArrayList<Airport>();
		
		toReturn.addAll((List<Airport>) getSession().createCriteria(Airport.class)
				.add(Restrictions.like("airportCode", compare + "%")).list());

		List<Airport> toAdd = new ArrayList<Airport>();
		for (Airport port : ((List<Airport>) getSession().createCriteria(Airport.class)
				.add(Restrictions.like("airportDesc", compare + "%")).list())) {
			if (port != null && !toReturn.contains(port)) {
				toAdd.add(port);
			}
		}
		toReturn.addAll(toAdd);
		
		return toReturn;
	}

	@SuppressWarnings("unchecked")
	public List<List<Label>> getAll() throws SimplePersistenceException {
		logger.debug("getAll Method Call to fetch all the Label Data");

		List<List<Label>> labelList = new ArrayList<List<Label>>();

		// labelList.add((List<Label>) getSession().createCriteria(Label.class)
		// .add(Restrictions.like("page", PASSENGER_LOGIN)).list());
		// labelList.add((List<Label>) getSession().createCriteria(Label.class)
		// .add(Restrictions.like("page", DIRECTION)).list());
		labelList.add((List<Label>) getSession().createCriteria(Label.class)
				.add(Restrictions.like("page", PASSENGER_INFO)).list());
		labelList.add((List<Label>) getSession().createCriteria(Label.class)
				.add(Restrictions.like("page", FLIGHT_INFO)).list());
		labelList.add((List<Label>) getSession().createCriteria(Label.class)
				.add(Restrictions.like("page", BAGGAGE_INFO)).list());
		labelList.add((List<Label>) getSession().createCriteria(Label.class)
				.add(Restrictions.like("page", GENERAL_INFO)).list());
		labelList.add((List<Label>) getSession().createCriteria(Label.class)
				.add(Restrictions.like("page", UPLOAD_INFO)).list());
		labelList.add((List<Label>) getSession().createCriteria(Label.class)
				.add(Restrictions.like("page", FRAUD_QUESTIONS)).list());
		labelList.add((List<Label>) getSession().createCriteria(Label.class)
				.add(Restrictions.like("page", SUBMIT_CLAIM)).list());
		labelList.add((List<Label>) getSession().createCriteria(Label.class)
				.add(Restrictions.like("page", SAVED_SREEN)).list());

		return labelList;
	}

	@SuppressWarnings("unchecked")
	public List<DropDown> getDropDowns() throws SimplePersistenceException {
		List<DropDown> dropdownList = (List<DropDown>) getHibernateTemplate()
				.loadAll(DropDown.class);
		return dropdownList;
	}

	public void save(List<Label> requiredFieldsList)
			throws SimplePersistenceException {
		logger.debug("Calling save method");
		getHibernateTemplate().saveOrUpdateAll(requiredFieldsList);
	}

	public Company getApplicationData() throws SimplePersistenceException {
		logger.debug("Calling getApplicationData");
		return getCompany();
	}

	public void saveCompany(Company company) throws SimplePersistenceException {
		logger.debug("Calling save method");
		Company existingCompany = getCompany();
		if (null != existingCompany) {
			getHibernateTemplate().delete(existingCompany);
		}
		getHibernateTemplate().save(company);
	}

	/**
	 * Fetch all the Languages
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Languages> getLanguages() throws SimplePersistenceException {
		logger.debug("Calling getLanguages to fetch all the Languages");
		return (List<Languages>) getHibernateTemplate()
				.loadAll(Languages.class);
	}

	/**
	 * Fetch all the Local text. Needed for Maintain Content and Language Page.
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Localetext> getContents(String languageDescription)
			throws SimplePersistenceException {
		logger.debug("Calling getContents to fetch all the localized texts");
		Long languageId = getLanguageId(languageDescription);
		return (languageId == null ? null
				: (List<Localetext>) getHibernateTemplate().find(
						"FROM Localetext where languageId= ?", languageId));
	}

	private Company getCompany() {
		return (Company) getSession().createSQLQuery("select * from company")
				.addScalar("code", Hibernate.STRING)
				.addScalar("name", Hibernate.STRING)
				.addScalar("address", Hibernate.STRING)
				.addScalar("city", Hibernate.STRING)
				.addScalar("state", Hibernate.STRING)
				.addScalar("country", Hibernate.STRING)
				.addScalar("replyAddress", Hibernate.STRING)
				.setResultTransformer(Transformers.aliasToBean(Company.class))
				.uniqueResult();
	}

	/**
	 * Save the Content and Language Data
	 * 
	 */

	@SuppressWarnings("unchecked")
	public void saveContentLanguageMaps(
			Map<String, List<Map<String, List<Localetext>>>> languageMap)
			throws SimplePersistenceException {
		List<Languages> allLanguages = (List<Languages>) getHibernateTemplate()
				.loadAll(Languages.class);
		for (Languages lang : allLanguages) {
			lang.setActiveStatus(false);
			getHibernateTemplate().update(lang);
		}

		for (Map.Entry<String, List<Map<String, List<Localetext>>>> outerMapEntry : languageMap
				.entrySet()) {
			if (!outerMapEntry.getKey().equalsIgnoreCase(
					"Please Select a Language")) {
				Languages languages = (Languages) getHibernateTemplate().find(
						"FROM Languages where description = ? ",
						outerMapEntry.getKey()).get(0);
				languages.setActiveStatus(true);
				getHibernateTemplate().update(languages);
			}
			for (Map<String, List<Localetext>> pageMap : outerMapEntry
					.getValue()) { // returns a List<Map<String,
									// List<Localetext>>>
				for (Map.Entry<String, List<Localetext>> pageMapEntry : pageMap
						.entrySet()) {
					for (Localetext localetext : pageMapEntry.getValue()) {
						getHibernateTemplate().update(localetext);
					}
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public List<Airline> getAirlines() {
		logger.error(">>>>> getting airlines with order by airline name ...");
		List<Airline> toReturn = new ArrayList<Airline>();
		List<Airline> important = getHibernateTemplate().findByCriteria(DetachedCriteria.forClass(Airline.class)
				.add(Restrictions.lt("id", "16")));
		List<Airline> ordered = getHibernateTemplate().findByCriteria(DetachedCriteria.forClass(Airline.class)
				.add(Restrictions.gt("id", "15")).addOrder(Order.asc("airlineDesc")));
		toReturn.addAll(important);
		toReturn.addAll(ordered);
		
		return toReturn;//(List<Airline>) getAllOfItWithOrderBy(Airline.class, "id");
	}
}
