package com.bagnet.nettracer.cronjob.bmo;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.bagnet.nettracer.tracing.db.WT_FWD_Log;

public class WT_FWD_LogBmo extends HibernateDaoSupport {
	private static final String FIND_FWDS_BY_COMPANY = "from WT_FWD_Log wf where wf.forwarding_agent.companycode_ID = :company and wq.queue_status between 0 and 4";

	@Transactional(readOnly = true)
	public List<WT_FWD_Log> getPendingFwdsByCompany(String companyCode) {
		Session sess = getSession(false);
		Query q = sess.createQuery(FIND_FWDS_BY_COMPANY);
		q.setParameter("company", companyCode);
		List<WT_FWD_Log> result = q.list();
		return result;
	}

	@Transactional
	public void updateFwd(WT_FWD_Log fwd) {
		Session sess = getSession(false);
		sess.update(fwd);
	}
}
