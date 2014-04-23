package com.bagnet.nettracer.tracing.dao.lf;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Transaction;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.db.lf.Subcompany;
import com.bagnet.nettracer.tracing.utils.TracerProperties;

public class SubCompanyDAO {

	private static final String EXCEPTION_MESSAGE = "Exception in SubCompanyDAO";
	
	private static Logger logger = Logger.getLogger(SubCompanyDAO.class);

	private static ConcurrentHashMap<String, Subcompany> subCompCache = new ConcurrentHashMap<String, Subcompany>();
	
	public static void resetCache() {
		subCompCache.clear();
	}
	
	public static Subcompany getSubcompany(String key) {
		return subCompCache.get(key);
	}
	
	public static void setSubcompany(String key, Subcompany o) {
		subCompCache.put(key, o);
	}
	
	public static Subcompany loadSubcompany(long id) {
		Session session = null;
		Subcompany subcomp = null;
		
		try {
			session = HibernateWrapper.getSession().openSession();
			subcomp = (Subcompany) session.get(Subcompany.class, id);
		} catch (Exception e) {
			logger.error(EXCEPTION_MESSAGE, e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return subcomp;
	}
	public static Subcompany loadSubcompany(String code) {
		return loadSubcompany(code, null);
	}
	public static Subcompany loadSubcompany(String code, String airline) {
		if(subCompCache.containsKey(code)) {
			return subCompCache.get(code);
		}
		Session session = null;
		Subcompany subcomp = null;
		
		try {
			session = HibernateWrapper.getSession().openSession();
			Criteria criteria = session.createCriteria(Subcompany.class);
			criteria.add(Restrictions.eq("subcompanyCode", code));
			criteria.add(Restrictions.eq("company.companyCode_ID", TracerProperties.get(airline,"wt.company.code")));
			subcomp=(Subcompany) criteria.uniqueResult();
			if(subcomp!=null){
				System.out.print("Got subcomp: "+subcomp.getSubcompanyCode());
				setSubcompany(code,subcomp);
			}
		} catch (Exception e) {
			logger.error(EXCEPTION_MESSAGE, e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return subcomp;
	}
	
	@SuppressWarnings("unchecked")
	public static Set<Subcompany> loadSubcompaniesByCompCode(String compCode) {
		if (compCode == null) {
			return null;
		}
		LinkedHashSet<Subcompany> results = null;
		Session session = null;
		
		try {
			session = HibernateWrapper.getSession().openSession();
			Criteria criteria = session.createCriteria(Subcompany.class);
			criteria.add(Restrictions.eq("company.companyCode_ID", compCode));
			results=new LinkedHashSet<Subcompany>(criteria.list());
			if (results.isEmpty()) {
				results = null;
			}
		} catch (Exception e) {
			logger.error(EXCEPTION_MESSAGE, e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return results;
	}
	
	public static boolean saveSubcompany(Subcompany subcomp) {
		boolean success = false;
		if (subcomp == null) {
			return success;
		}
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = HibernateWrapper.getSession().openSession();
			transaction = session.beginTransaction();
			if(subcomp.getId() > 0) {
				session.merge(subcomp);
			} else {
				session.save(subcomp);
			}
			transaction.commit();
			success = true;
		} catch (Exception e) {
			logger.error(EXCEPTION_MESSAGE, e);
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return success;
	}
	
}
