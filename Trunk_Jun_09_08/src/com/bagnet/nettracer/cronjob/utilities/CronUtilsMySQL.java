package com.bagnet.nettracer.cronjob.utilities;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.db.wtq.WorldTracerQueue;

public class CronUtilsMySQL extends CronUtils{
	public CronUtilsMySQL(String companyCode) {
		super(companyCode);
	}
	
	protected int checkSQLServerPercentageFailures(){
		String sql = "SELECT count(wtq_status) FROM " +
				"(SELECT wtq_status FROM wt_queue ORDER BY CREATEDATE DESC limit 50) count_table " +
				"WHERE wtq_status = 'FAIL'";
		
		Session sess = HibernateWrapper.getSession().openSession();
		SQLQuery query = sess.createSQLQuery(sql);

		List<BigInteger> list = (List<BigInteger>) query.list();
		
		sess.close();
		return ((BigInteger)list.get(0)).intValue();
	}
	
	protected int checkSQLServerSequentialFailures() {
		
		String sql = "SELECT wtq_status FROM wt_queue " +
		"ORDER BY CREATEDATE DESC limit 50";
		Session sess = HibernateWrapper.getSession().openSession();
		
		SQLQuery query = sess.createSQLQuery(sql);
		query.addScalar("wtq_status", Hibernate.STRING);
		
		List<String> list = (List<String>) query.list();
		
		int sequentialFails = 0;
		for (String str: list) {
			if (str.equals(WorldTracerQueue.WtqStatus.SUCCESS.name())) {
				sequentialFails = 0;
				break;
			}  else if (str.equals(WorldTracerQueue.WtqStatus.FAIL.name())) {
				sequentialFails++;
			}
		}
		
		sess.close();
		return sequentialFails;
	}

	protected int checkPendingSize() {
		String sql = "SELECT count(*) as icount FROM wt_queue " +
		"WHERE wtq_status = 'PENDING'";
		Session sess = HibernateWrapper.getSession().openSession();
		SQLQuery query = sess.createSQLQuery(sql);
		List<BigInteger> list = (List<BigInteger>) query.list();
		sess.close();
		return ((BigInteger)list.get(0)).intValue();
	}
}
