package com.bagnet.nettracer.cronjob.bmo;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import static org.hibernate.criterion.Restrictions.*;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.bagnet.nettracer.tracing.db.WorldTracerFile.WTStatus;
import com.bagnet.nettracer.tracing.db.wtq.WorldTracerQueue;
import com.bagnet.nettracer.tracing.db.wtq.WtqEraseActionFile;
import com.bagnet.nettracer.tracing.db.wtq.WorldTracerQueue.WtqStatus;

public class WTQueueBmo extends HibernateDaoSupport {
	
	private static final String FIND_TASKS_BY_COMPANY_HQL =
		"from WorldTracerQueue wq where wq.agent.companycode_ID = :company and wq.status = :status";

	@Transactional
	public void updateQueue(WorldTracerQueue queue) {
		Session sess = getSession(false);
		sess.update(queue);
	}

	@Transactional(readOnly=true)
	public List<WorldTracerQueue> findAllPendingTasks(String company) {
		return findPendingTasksByType(company, WorldTracerQueue.class);
	}
	
	@Transactional(readOnly=true)
	public List<WtqEraseActionFile> findPendingEraseActionFiles(String company) {
		return findPendingTasksByType(company, WtqEraseActionFile.class);
	}
	
	private <T extends WorldTracerQueue> List<T> findPendingTasksByType(String company, Class<T> queueClass) {
		Session sess = getSession(false);
		Criteria cri = sess.createCriteria(queueClass);
		cri.createCriteria("agent").add(Restrictions.eq("companycode_ID", company));
		cri.add(Restrictions.eq("status", WtqStatus.PENDING));
		return cri.list();
	}


}
