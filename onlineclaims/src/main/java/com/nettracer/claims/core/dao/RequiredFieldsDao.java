package com.nettracer.claims.core.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.nettracer.claims.core.model.DropDown;
import com.nettracer.claims.core.model.Label;
import com.nettracer.claims.hibernate.HibernateDaoSupport;

/**
 * @author utpal.patra
 * 
 */

@Repository
public class RequiredFieldsDao extends HibernateDaoSupport {
	private static Logger logger = Logger.getLogger(HibernateDaoSupport.class);

	@SuppressWarnings("unchecked")
	public List<Label> getAll() {
		logger.info("getAll Method Call to fetch all the Label Data");
		List<Label> labelList = (List<Label>) getSession().createSQLQuery("select * from label")
			.addScalar("id", Hibernate.LONG)
			.addScalar("label", Hibernate.STRING)
			.addScalar("delayedState", Hibernate.LONG)
			.addScalar("damagedState", Hibernate.LONG)
			.addScalar("pilferedState", Hibernate.LONG)
			.setResultTransformer(Transformers.aliasToBean(Label.class))
			.list();
		return labelList;
	}

	@SuppressWarnings("unchecked")
	public List<DropDown> getDropDowns() {
		List<DropDown> dropdownList = (List<DropDown>) getHibernateTemplate()
				.loadAll(DropDown.class);
		return dropdownList;
	}

	public void save(List<Label> requiredFieldsList) {
		getHibernateTemplate().saveOrUpdateAll(requiredFieldsList);
	}

}
