package com.nettracer.claims.core.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.nettracer.claims.core.exception.SimplePersistenceException;
import com.nettracer.claims.core.model.Company;
import com.nettracer.claims.core.model.DropDown;
import com.nettracer.claims.core.model.Label;
import com.nettracer.claims.core.model.Languages;
import com.nettracer.claims.core.model.Localetext;
import com.nettracer.claims.hibernate.HibernateDaoSupport;

/**
 * @author utpal.patra
 * 
 */

@Repository
public class AdminDao extends HibernateDaoSupport {
	private static Logger logger = Logger.getLogger(AdminDao.class);
	
	private static final String PASSENGER_LOGIN = "Login";
	private static final String DIRECTION = "Direction";
	private static final String PASSENGER_INFO = "Passenger";
	private static final String FLIGHT_INFO = "Flight";
	private static final String BAGGAGE_INFO = "Baggage";
	private static final String GENERAL_INFO = "General";
	private static final String UPLOAD_INFO = "Upload";
	private static final String FRAUD_QUESTIONS = "Fraud";
	private static final String SUBMIT_CLAIM = "Submit";
	private static final String SAVED_SREEN = "Saved";

	@SuppressWarnings("unchecked")
	public List<List<Label>> getAll() throws SimplePersistenceException {
		logger.debug("getAll Method Call to fetch all the Label Data");

		List<List<Label>> labelList = new ArrayList<List<Label>>();
		labelList.add((List<Label>) getSession().createCriteria(Label.class)
				.add(Restrictions.like("page", PASSENGER_INFO + "%")).list());

		labelList.add((List<Label>) getSession().createCriteria(Label.class)
				.add(Restrictions.like("page", FLIGHT_INFO + "%")).list());

		labelList.add((List<Label>) getSession().createCriteria(Label.class)
				.add(Restrictions.like("page", BAGGAGE_INFO + "%")).list());
		labelList.add((List<Label>) getSession().createCriteria(Label.class)
				.add(Restrictions.like("page", GENERAL_INFO + "%")).list());
		labelList
				.add((List<Label>) getSession()
						.createCriteria(Label.class)
						.add(Restrictions.like("page", "%" + UPLOAD_INFO + "%"))
						.list());
		labelList.add((List<Label>) getSession().createCriteria(Label.class)
				.add(Restrictions.like("page", FRAUD_QUESTIONS + "%")).list());
		labelList.add((List<Label>) getSession().createCriteria(Label.class)
				.add(Restrictions.like("page", SUBMIT_CLAIM + "%")).list());
		labelList.add((List<Label>) getSession().createCriteria(Label.class)
				.add(Restrictions.like("page", SAVED_SREEN + "%")).list());

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
		if(languageDescription.equalsIgnoreCase("Please Select a Language")){
			return null;
		}
		Long languageId = ((Languages) getHibernateTemplate().find(
				"from Languages where description= ?", languageDescription)
				.get(0)).getId();
		return (List<Localetext>) getHibernateTemplate().find(
				"FROM Localetext where languageId= ?", languageId);
		/*
		 * return(List<Localetext>)getHibernateTemplate().find(
		 * "SELECT lt FROM Localetext lt INNER JOIN FETCH lt.languages.description=?"
		 * ,languageDescription);
		 */
	}
	
	@SuppressWarnings("unchecked")
	public List<Localetext> getPassengerContents(String languageDescription)
			throws SimplePersistenceException {
		logger.debug("Calling getPassengerContents to fetch all the Passenger texts");
		
		Long languageId = getLanguageId(languageDescription);
		
		return (languageId == null ? null : (List<Localetext>) getHibernateTemplate().find(
				"SELECT lt FROM Localetext lt " +
				"INNER JOIN FETCH lt.languages lg " +
				"INNER JOIN FETCH lt.label lb " +
				"WHERE lg.id = ? AND lb.page LIKE ? "
				,new Object[]{languageId,PASSENGER_INFO+ "%"}));
	}
	
	@SuppressWarnings("unchecked")
	public List<Localetext> getFlightContents(String languageDescription)
			throws SimplePersistenceException {
		logger.debug("Calling getFlightContents to fetch all the Flight texts");
		
		Long languageId = getLanguageId(languageDescription);
		
		return (languageId == null ? null : (List<Localetext>) getHibernateTemplate().find(
				"SELECT lt FROM Localetext lt " +
				"INNER JOIN FETCH lt.languages lg " +
				"INNER JOIN FETCH lt.label lb " +
				"WHERE lg.id = ? AND lb.page LIKE ? "
				,new Object[]{languageId,FLIGHT_INFO+ "%"}));
	}
	
	@SuppressWarnings("unchecked")
	public List<Localetext> getBaggageContents(String languageSelected) throws SimplePersistenceException {
		logger.debug("Calling getBaggageContents to fetch all the Baggage texts");
		
		Long languageId = getLanguageId(languageSelected);
		
		return (languageId == null ? null : (List<Localetext>) getHibernateTemplate().find(
				"SELECT lt FROM Localetext lt " +
				"INNER JOIN FETCH lt.languages lg " +
				"INNER JOIN FETCH lt.label lb " +
				"WHERE lg.id = ? AND lb.page LIKE ? "
				,new Object[]{languageId,BAGGAGE_INFO+ "%"}));
	}

	@SuppressWarnings("unchecked")
	public List<Localetext> getFraudQuestionContents(String languageSelected) throws SimplePersistenceException{
		logger.debug("Calling getFraudQuestionContents to fetch all the Fraud Question texts");
		
		Long languageId = getLanguageId(languageSelected);
		
		return (languageId == null ? null : (List<Localetext>) getHibernateTemplate().find(
				"SELECT lt FROM Localetext lt " +
				"INNER JOIN FETCH lt.languages lg " +
				"INNER JOIN FETCH lt.label lb " +
				"WHERE lg.id = ? AND lb.page LIKE ? "
				,new Object[]{languageId,FRAUD_QUESTIONS+ "%"}));
	}

	@SuppressWarnings("unchecked")
	public List<Localetext> getGeneralContents(String languageSelected) throws SimplePersistenceException{
		logger.debug("Calling getGeneralContents to fetch all the General or common  texts");
		
		Long languageId = getLanguageId(languageSelected);
		
		return (languageId == null ? null : (List<Localetext>) getHibernateTemplate().find(
				"SELECT lt FROM Localetext lt " +
				"INNER JOIN FETCH lt.languages lg " +
				"INNER JOIN FETCH lt.label lb " +
				"WHERE lg.id = ? AND lb.page LIKE ? "
				,new Object[]{languageId,GENERAL_INFO+ "%"}));
	}

	@SuppressWarnings("unchecked")
	public List<Localetext> getSavedContents(String languageSelected) throws SimplePersistenceException{
		logger.debug("Calling getSavedContents to fetch all the labels for saving the data");
		
		Long languageId = getLanguageId(languageSelected);
		
		return (languageId == null ? null : (List<Localetext>) getHibernateTemplate().find(
				"SELECT lt FROM Localetext lt " +
				"INNER JOIN FETCH lt.languages lg " +
				"INNER JOIN FETCH lt.label lb " +
				"WHERE lg.id = ? AND lb.page LIKE ? "
				,new Object[]{languageId,SAVED_SREEN+ "%"}));
	}

	@SuppressWarnings("unchecked")
	public List<Localetext> getSubmitContents(String languageSelected) throws SimplePersistenceException{
		logger.debug("Calling getSubmitContents to fetch all the submission texts");
		
		Long languageId = getLanguageId(languageSelected);
		
		return (languageId == null ? null : (List<Localetext>) getHibernateTemplate().find(
				"SELECT lt FROM Localetext lt " +
				"INNER JOIN FETCH lt.languages lg " +
				"INNER JOIN FETCH lt.label lb " +
				"WHERE lg.id = ? AND lb.page LIKE ? "
				,new Object[]{languageId,SUBMIT_CLAIM+ "%"}));
	}

	@SuppressWarnings("unchecked")
	public List<Localetext> getUploadContents(String languageSelected) throws SimplePersistenceException{
		logger.debug("Calling getUploadContents to fetch all the Upload labels");
		
		Long languageId = getLanguageId(languageSelected);
		
		return (languageId == null ? null : (List<Localetext>) getHibernateTemplate().find(
				"SELECT lt FROM Localetext lt " +
				"INNER JOIN FETCH lt.languages lg " +
				"INNER JOIN FETCH lt.label lb " +
				"WHERE lg.id = ? AND lb.page LIKE ? "
				,new Object[]{languageId,UPLOAD_INFO+ "%"}));
	}
	
	@SuppressWarnings("unchecked")
	public List<Localetext> getDirectionContents(String languageSelected) {
		logger.debug("Calling getDirectionContents to fetch all the Direction labels");
		
		Long languageId = getLanguageId(languageSelected);
		
		return (languageId == null ? null : (List<Localetext>) getHibernateTemplate().find(
				"SELECT lt FROM Localetext lt " +
				"INNER JOIN FETCH lt.languages lg " +
				"INNER JOIN FETCH lt.label lb " +
				"WHERE lg.id = ? AND lb.page LIKE ? "
				,new Object[]{languageId, "%"+DIRECTION+ "%"}));
	}

	@SuppressWarnings("unchecked")
	public List<Localetext> getPassengerLoginContents(String languageSelected) {
		logger.debug("Calling getPassengerLoginContents to fetch all the Passenger Login labels");
		
		Long languageId = getLanguageId(languageSelected);
		
		return (languageId == null ? null : (List<Localetext>) getHibernateTemplate().find(
				"SELECT lt FROM Localetext lt " +
				"INNER JOIN FETCH lt.languages lg " +
				"INNER JOIN FETCH lt.label lb " +
				"WHERE lg.id = ? AND lb.page LIKE ? "
				,new Object[]{languageId, "%"+PASSENGER_LOGIN+ "%"}));
	}
	
	private Long getLanguageId(String languageDescription){
		if(null == languageDescription || languageDescription.equalsIgnoreCase("Please Select a Language")){
			return null;
		}
		return ((Languages) getHibernateTemplate().find(
				"from Languages where description= ?", languageDescription)
				.get(0)).getId();
	}

	/*
	 * @SuppressWarnings("unchecked") public Map<String,List<Localetext>>
	 * getPageContents(String languageDescription) throws
	 * SimplePersistenceException{
	 * logger.debug("Calling getPageContents to fetch pagewise labels"); Long
	 * languageId=((Languages)getHibernateTemplate()
	 * .find("from Languages where description= ?",
	 * languageDescription).get(0)).getId(); Map<String,List<Localetext>>
	 * pagewiseMap=new HashMap<String,List<Localetext>>(); List<Localetext>
	 * labelList = new ArrayList<Localetext>();
	 * 
	 * return (List<Localetext>)getHibernateTemplate()
	 * .find("FROM Localetext where languageId= ?", languageId);
	 * return(List<Localetext>)getHibernateTemplate().find(
	 * "SELECT lt FROM Localetext lt INNER JOIN FETCH lt.languages.description=?"
	 * ,languageDescription); }
	 */

	private Company getCompany() {
		return (Company) getSession().createSQLQuery("select * from company")
				.addScalar("code", Hibernate.STRING)
				.addScalar("name", Hibernate.STRING)
				.addScalar("address", Hibernate.STRING)
				.addScalar("city", Hibernate.STRING)
				.addScalar("state", Hibernate.STRING)
				.addScalar("country", Hibernate.STRING)
				.addScalar(	"replyAddress", Hibernate.STRING)
				.setResultTransformer(Transformers.aliasToBean(Company.class))
				.uniqueResult();
	}

	

	/**
	 * Save the Content and Language Data
	 * 
	 */
	public void saveContentLanguage(List<List<Localetext>> list)
			throws SimplePersistenceException {

		for (List<Localetext> lists:list) {
			for(Localetext text:lists){
				getHibernateTemplate().update(text);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void saveContentLanguageMaps(Map<String, List<Map<String, List<Localetext>>>> languageMap) 
				throws SimplePersistenceException {
		List<Languages> allLanguages=(List<Languages>)getHibernateTemplate().loadAll(Languages.class);
		for(Languages lang:allLanguages){
			lang.setActiveStatus(false);
			getHibernateTemplate().update(lang);
		}
		
		for(Map.Entry<String, List<Map<String, List<Localetext>>>> outerMapEntry : languageMap.entrySet()){
			if(!outerMapEntry.getKey().equalsIgnoreCase("Please Select a Language")){
				Languages languages=(Languages)getHibernateTemplate()
						.find("FROM Languages where description = ? ",outerMapEntry.getKey()).get(0);
				languages.setActiveStatus(true);
				getHibernateTemplate().update(languages);
			}
			for(Map<String, List<Localetext>> pageMap : outerMapEntry.getValue()){ //returns a  List<Map<String, List<Localetext>>>
				for(Map.Entry<String, List<Localetext>> pageMapEntry : pageMap.entrySet()){
					for(Localetext localetext : pageMapEntry.getValue()){
						getHibernateTemplate().update(localetext);
					}
				}
			}
		}

	}

	

	

}
