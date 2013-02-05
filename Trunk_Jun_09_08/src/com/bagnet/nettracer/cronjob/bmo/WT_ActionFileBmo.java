package com.bagnet.nettracer.cronjob.bmo;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles;
import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles.ActionFileType;

public class WT_ActionFileBmo {

	public static final String DELETE_BY_DAY_TYPE = "delete from Worldtracer_Actionfiles where airline = :airline and station = :station and seq = :seq and day = :day and action_file_type = :actionFileType";
	
	public static final String DELETE_BY_STATION = "delete from Worldtracer_Actionfiles where airline = :airline and station = :station";

	public static final String DELETE_BY_TYPE = "delete from Worldtracer_Actionfiles where airline = :airline and station = :station and action_file_type = :actionFileType and seq = :seq";

	public static final String DELETE_SINGLE = "delete from Worldtracer_Actionfiles where airline = :airline and station = :station and day = :day and action_file_type = :actionFileType and seq = :seq"
			+ " and item_number = :item_number";

	public static final String FIND_TEXT_FOR_WAF = "select action_file_text from Worldtracer_Actionfiles where airline = :airline and station = :station and day = :day and action_file_type = :actionFileType and seq = :seq and item_number = :item_number";
	
	public static final String FIND_WAF_SUMMARY = "from Worldtracer_Actionfiles where airline = :airline and station = :wtStation and action_file_type = :afType and seq = :seq and day = :day and deleted = false";
	
	public static final String FIND_WAF = "from Worldtracer_Actionfiles where airline = :airline and station = :wtStation and action_file_type = :afType and seq = :seq and day = :day and item_number = :itemNum";

	public void saveActionFile(Worldtracer_Actionfiles waf) {
		Session sess = HibernateWrapper.getSession().openSession();
		sess.save(waf);
		sess.close();
	}

	public void deleteActionFiles(String airline, String station, ActionFileType actionFileType, String seq, int day) {
		Session sess = HibernateWrapper.getSession().openSession();
		Query q = sess.createQuery(DELETE_BY_DAY_TYPE);
		q.setParameter("station", station);
		q.setParameter("airline", airline);
		q.setInteger("day", day);
		q.setParameter("actionFileType", actionFileType);
		q.setParameter("seq", seq);
		
		q.executeUpdate();
		sess.close();
		
	}

	public String findTextForAf(Worldtracer_Actionfiles waf) {
		Session sess = HibernateWrapper.getSession().openSession();
		Query q = sess.createQuery(FIND_TEXT_FOR_WAF);
		q.setParameter("station", waf.getStation());
		q.setParameter("airline", waf.getAirline());
		q.setInteger("day", waf.getDay());
		q.setParameter("actionFileType", waf.getAction_file_type());
		q.setInteger("item_number", waf.getItem_number());
		q.setParameter("seq", waf.getSeq());
		
		List<String> result = q.list();
		sess.close();
		if(result.size() > 0) {
			return result.get(0);
		}
		return null;
	}

	public void deleteActionFile(Worldtracer_Actionfiles waf) {
		Session sess = HibernateWrapper.getSession().openSession();
		Query q = sess.createQuery(DELETE_SINGLE);
		q.setParameter("station", waf.getStation());
		q.setParameter("airline", waf.getAirline());
		q.setInteger("day", waf.getDay());
		q.setParameter("actionFileType", waf.getAction_file_type());
		q.setParameter("item_number", waf.getItem_number());
		q.setParameter("seq", waf.getSeq());

		q.executeUpdate();
		sess.close();
	}

	public void replaceActionFiles(List<Worldtracer_Actionfiles> result,
			String companyCode, String stationCode, ActionFileType type, String seq, int day) {
		deleteActionFiles(companyCode, stationCode, type, seq, day);
		if (result != null) {
			for (Worldtracer_Actionfiles waf : result) {
				saveActionFile(waf);
			}
		}
	}

	public void deleteActionFiles(String companyCode, String stationCode,
			ActionFileType afType, String seq) {
		Session sess = HibernateWrapper.getSession().openSession();
		Query q = sess.createQuery(DELETE_BY_TYPE);
		q.setParameter("station", stationCode);
		q.setParameter("airline", companyCode);
		q.setParameter("actionFileType", afType);
		q.setParameter("seq", seq);

		q.executeUpdate();
		sess.close();
	}

	public List<Worldtracer_Actionfiles> findActionFileSummary(
			String companyCode, String wtStation, ActionFileType afType, String seq,
			int day) {
		Session sess = HibernateWrapper.getSession().openSession();
		Query q = sess.createQuery(FIND_WAF_SUMMARY);
		q.setParameter("airline", companyCode);
		q.setParameter("afType", afType);
		q.setParameter("wtStation", wtStation);
		q.setParameter("seq", seq);
		q.setInteger("day", day);
		List<Worldtracer_Actionfiles> result = q.list();
		sess.close();
		return result;
	}

	public void updateDetails(String companyCode, String wtStation,
			ActionFileType category, String seq, int day, int fileNum, String result, String ahl_id, String ohd_id, double percent) {
		Session sess = HibernateWrapper.getSession().openSession();
		Query q = sess.createQuery(WT_ActionFileBmo.FIND_WAF);
		q.setParameter("airline", companyCode);
		q.setParameter("afType", category);
		q.setParameter("wtStation", wtStation);
		q.setParameter("seq", seq);
		q.setInteger("day", day);
		q.setInteger("itemNum", fileNum);
		Worldtracer_Actionfiles waf = (Worldtracer_Actionfiles) q.uniqueResult();
		if(waf == null) return;
		waf.setAction_file_text(result);
		waf.setPercent_match(percent);
		waf.setWt_incident_id(ahl_id);
		waf.setWt_ohd_id(ohd_id);
		sess.update(waf);
		sess.close();
		return;
	}

}
