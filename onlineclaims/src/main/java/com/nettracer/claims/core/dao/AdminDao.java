package com.nettracer.claims.core.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.nettracer.claims.core.model.Company;
import com.nettracer.claims.core.model.DropDown;
import com.nettracer.claims.core.model.Label;
import com.nettracer.claims.core.model.Languages;
import com.nettracer.claims.hibernate.HibernateDaoSupport;

/**
 * @author utpal.patra
 * 
 */

@Repository
public class AdminDao extends HibernateDaoSupport {
	private static Logger logger = Logger.getLogger(AdminDao.class);
	
	private static final String PASSENGER_INFO = "Passenger";
	private static final String FLIGHT_INFO = "Flight";
	private static final String BAGGAGE_INFO = "Baggage";
	private static final String GENERAL_INFO = "General";
	private static final String UPLOAD_INFO = "Upload";
	private static final String FRAUD_QUESTIONS = "Fraud";
	private static final String SUBMIT_CLAIM = "Submit";
	private static final String SAVED_SREEN = "Saved";

	@SuppressWarnings("unchecked")
	public List<List<Label>> getAll() {
		logger.info("getAll Method Call to fetch all the Label Data");
		
		List<List<Label>> labelList = new ArrayList<List<Label>>();
		labelList.add((List<Label>) getSession().createCriteria(Label.class)
		    	.add( Restrictions.like("page", PASSENGER_INFO+"%") )
		    	.list());
		
		labelList.add((List<Label>) getSession().createCriteria(Label.class)
		    	.add( Restrictions.like("page", FLIGHT_INFO+"%") )
		    	.list());
		
		labelList.add((List<Label>) getSession().createCriteria(Label.class)
		    	.add( Restrictions.like("page", BAGGAGE_INFO+"%") )
		    	.list());
		labelList.add((List<Label>) getSession().createCriteria(Label.class)
		    	.add( Restrictions.like("page", GENERAL_INFO+"%") )
		    	.list());
		labelList.add((List<Label>) getSession().createCriteria(Label.class)
		    	.add( Restrictions.like("page", "%"+UPLOAD_INFO+"%") )
		    	.list());
		labelList.add((List<Label>) getSession().createCriteria(Label.class)
		    	.add( Restrictions.like("page", FRAUD_QUESTIONS+"%") )
		    	.list());
		labelList.add((List<Label>) getSession().createCriteria(Label.class)
		    	.add( Restrictions.like("page", SUBMIT_CLAIM+"%") )
		    	.list());
		labelList.add((List<Label>) getSession().createCriteria(Label.class)
		    	.add( Restrictions.like("page", SAVED_SREEN+"%") )
		    	.list());
		
		return labelList;
	}
	
	@SuppressWarnings("unchecked")
	public List<DropDown> getDropDowns() {
		List<DropDown> dropdownList = (List<DropDown>) getHibernateTemplate()
				.loadAll(DropDown.class);
		return dropdownList;
	}

	public void save(List<Label> requiredFieldsList) {
		logger.info("Calling save method");
		getHibernateTemplate().saveOrUpdateAll(requiredFieldsList);
	}

	public Company getApplicationData() {
		logger.info("Calling getApplicationData");
		return getCompany();
	}

	public void saveCompany(Company company) {
		logger.info("Calling save method");
		Company existingCompany=getCompany();
		if(null != existingCompany){
			getHibernateTemplate().delete(existingCompany);
		}
		getHibernateTemplate().save(company);
	}
	
	/**
	 * Fetch all the Languages
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Languages> getLanguages(){
		logger.info("Calling getLanguages to fetch all the Languages");
		return (List<Languages>)getHibernateTemplate().loadAll(Languages.class);
	}
	
	private Company getCompany(){
		return (Company)getSession().createSQLQuery("select * from company")
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

}
