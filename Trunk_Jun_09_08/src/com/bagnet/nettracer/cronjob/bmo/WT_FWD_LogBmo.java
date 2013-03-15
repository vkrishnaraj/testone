package com.bagnet.nettracer.cronjob.bmo;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.bagnet.nettracer.tracing.db.WT_FWD_Log;

public class WT_FWD_LogBmo {
	private static final String FIND_FWDS_BY_COMPANY = "from WT_FWD_Log wf where wf.forwarding_agent.companycode_ID = :company and wq.queue_status between 0 and 4";
    private SessionFactory sessionFactory;
    
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    public void setSessionFactory(SessionFactory sessionFactory) {
         this.sessionFactory = sessionFactory;
    }

	@Transactional(readOnly = true)
	public List<WT_FWD_Log> getPendingFwdsByCompany(String companyCode) {
		Session sess = null;
		List<WT_FWD_Log> result = null;
		try {
			sess = getSessionFactory().openSession();
		
			Query q = sess.createQuery(FIND_FWDS_BY_COMPANY);
			q.setParameter("company", companyCode);
			result = q.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (sess != null) {
				sess.close();
			}
		}
		return result;
	}

	@Transactional
	public void updateFwd(WT_FWD_Log fwd) {
		Session sess = null;
		try {
			sess = getSessionFactory().openSession();
			sess.update(fwd);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (sess != null) {
				sess.close();
			}
		}
	}
}
