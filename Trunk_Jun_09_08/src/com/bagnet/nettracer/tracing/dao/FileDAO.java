package com.bagnet.nettracer.tracing.dao;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Expression;

import aero.nettracer.fs.model.File;
import aero.nettracer.fs.model.FsIncident;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.db.Incident;

public class FileDAO {

private static final String EXCEPTION_MESSAGE = "Exception in FileDAO";
	
	private static Logger logger = Logger.getLogger(FileDAO.class);
	
	public static File loadFile(long id) {
		Session session = null;
		File file = null;
		
		try {
			session = HibernateWrapper.getSession().openSession();
			file = (File) session.get(File.class, id);
		} catch (Exception e) {
			logger.error(EXCEPTION_MESSAGE, e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return file;
	}
	
	public static boolean saveFile(File file) {
		boolean success = false;
		if (file == null) {
			return success;
		}
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = HibernateWrapper.getSession().openSession();
			transaction = session.beginTransaction();
			if(file.getId() > 0) {
				session.merge(file);
			} else {
				session.save(file);
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
	
	public static File loadFile(String incidentId) {
		if (incidentId == null) {
			return null;
		}
		Session session = null;
		File file = null;
		
		try {
			session = HibernateWrapper.getSession().openSession();
			Criteria criteria = session.createCriteria(FsIncident.class);
			criteria.add(Expression.eq("airlineIncidentId", incidentId));
			FsIncident fsIncident = (FsIncident) criteria.uniqueResult();
			if (fsIncident != null) {
				file = fsIncident.getFile();
			}
		} catch (Exception e) {
			logger.error(EXCEPTION_MESSAGE, e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return file;
	}
	
}
