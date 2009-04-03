package com.bagnet.nettracer.wt.bmo;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.bagnet.nettracer.tracing.db.wt.ActionFileStation;

public class ActionFileStationBMO extends HibernateDaoSupport {

	@Transactional(readOnly=true)
	public ActionFileStation getAfStation(String companyCode, String wtStation) {
		Session sess = getSession(false);
		Query q = sess.createQuery("from ActionFileStation afs where afs.companyCode = :companyCode and afs.stationCode = :wtStation");
		q.setString("companyCode", companyCode);
		q.setString("wtStation", wtStation);
		return (ActionFileStation) q.uniqueResult();
	}

}
