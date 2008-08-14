package com.bagnet.nettracer.cronjob.bmo;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles;
import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles.ActionFileType;

public class WT_ActionFileBmo extends HibernateDaoSupport {

	private static final String DELETE_BY_DAY_TYPE = 
		"delete from Worldtracer_Actionfiles where airline = :airline and station = :station and day = :day and action_file_type = :actionFileType";
	
	private static final String DELETE_SINGLE = 
		"delete from Worldtracer_Actionfiles where airline = :airline and station = :station and day = :day and action_file_type = :actionFileType" +
		" and item_number = :item_number";
	
	private static final String FIND_TEXT_FOR_WAF = 
		"select action_file_text from Worldtracer_Actionfiles where airline = :airline and station = :station and day = :day and action_file_type = :actionFileType and item_number = :item_number";

	@Transactional
	public void saveActionFile(Worldtracer_Actionfiles waf) {
		Session sess = getSession(false);
		sess.save(waf);
	}

	@Transactional
	public void deleteActionFiles(String airline, String station, ActionFileType actionFileType, int day) {
		Session sess = getSession(false);
		Query q = sess.createQuery(DELETE_BY_DAY_TYPE);
		q.setParameter("station", station);
		q.setParameter("airline", airline);
		q.setInteger("day", day);
		q.setParameter("actionFileType", actionFileType);
		
		q.executeUpdate();
		
	}

	@Transactional(readOnly = true)
	public String findTextForAf(Worldtracer_Actionfiles waf) {
		Session sess = getSession(false);
		Query q = sess.createQuery(FIND_TEXT_FOR_WAF);
		q.setParameter("station", waf.getStation());
		q.setParameter("airline", waf.getAirline());
		q.setInteger("day", waf.getDay());
		q.setParameter("actionFileType", waf.getAction_file_type());
		q.setInteger("item_number", waf.getItem_number());
		
		List<String> result = q.list();
		
		if(result.size() > 0) {
			return result.get(0);
		}
		return null;
	}

	@Transactional
	public void deleteActionFile(Worldtracer_Actionfiles waf) {
		Session sess = getSession(false);
		Query q = sess.createQuery(DELETE_SINGLE);
		q.setParameter("station", waf.getStation());
		q.setParameter("airline", waf.getAirline());
		q.setInteger("day", waf.getDay());
		q.setParameter("actionFileType", waf.getAction_file_type());
		q.setParameter("item_number", waf.getItem_number());
		
		q.executeUpdate();
		
	}
}
