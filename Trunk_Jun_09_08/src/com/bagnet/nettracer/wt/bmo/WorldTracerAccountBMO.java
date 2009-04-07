package com.bagnet.nettracer.wt.bmo;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.bagnet.nettracer.tracing.db.wt.WorldTracerAccount;
import com.bagnet.nettracer.tracing.utils.TracerProperties;

public class WorldTracerAccountBMO extends HibernateDaoSupport {
	private static String INSTANCE_NAME = TracerProperties.getInstanceLabel();
	
	@Transactional(readOnly=true)
	public List<WorldTracerAccount> getAccountNames(String companyCode) {
		Session sess = getSession(false);
		Query q = sess.createQuery("from WorldTracerAccount a where a.companyCode = :companyCode and a.instanceName = :instanceName");
		q.setString("companyCode", companyCode);
		q.setString("instanceName", INSTANCE_NAME);
		return (List<WorldTracerAccount>)q.list();
	}
}
