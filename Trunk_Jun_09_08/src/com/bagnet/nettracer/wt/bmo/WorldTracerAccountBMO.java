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
	
	private static final String FIND_BY_COMPANY_INSTANCE = "from WorldTracerAccount a where a.companyCode = :companyCode and a.instanceName = :instanceName";
	private static final String COUNT_BY_COMPANY_INSTANCE = "select count(a) from WorldTracerAccount a where a.companyCode = :companyCode and a.instanceName = :instanceName";

	@Transactional(readOnly = true)
	public List<WorldTracerAccount> getAccountNames(String companyCode) {
		Session sess = getSession(false);
		Query q = sess.createQuery(FIND_BY_COMPANY_INSTANCE);
		q.setString("companyCode", companyCode);
		q.setString("instanceName", INSTANCE_NAME);
		return q.list();
	}

	@Transactional(readOnly = true)
	public WorldTracerAccount getAccount(long id) {
		Session sess = getSession(false);
		return (WorldTracerAccount) sess.get(WorldTracerAccount.class, id);
	}

	@Transactional(readOnly = true)
	public int getNumAccounts(String companyCode) {
		Session sess = getSession(false);
		Query q = sess.createQuery(FIND_BY_COMPANY_INSTANCE);
		q.setString("companyCode", companyCode);
		q.setString("instanceName", INSTANCE_NAME);
		return (Integer)q.uniqueResult();
	}
}
