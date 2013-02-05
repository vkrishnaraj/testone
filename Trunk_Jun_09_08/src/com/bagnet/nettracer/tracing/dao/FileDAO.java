package com.bagnet.nettracer.tracing.dao;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Transaction;
//import org.hibernate.classic.Session;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;

import aero.nettracer.fs.model.File;
import aero.nettracer.fs.model.FsIncident;

import com.bagnet.nettracer.hibernate.HibernateWrapper;

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
	
	public static boolean saveFile(File file, boolean firstSave) {
		//set empty string airlineIncidentId to null for database constraint to work properly
		if(file != null && file.getIncident() != null 
				&& file.getIncident().getAirlineIncidentId() != null 
				&& file.getIncident().getAirlineIncidentId().trim().length() == 0){
			file.getIncident().setAirlineIncidentId(null);
		}
		
		boolean success = false;
		if (file == null) {
			return success;
		}
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = HibernateWrapper.getSession().openSession();
			transaction = session.beginTransaction();
			if (firstSave) {
				// this was added due to claims not saving with merge on the first save
				session.saveOrUpdate(file);
			} else if(file.getId() > 0) {
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
	
//	public static boolean saveOrUpdateFile(File file) {
//		boolean success = false;
//		if (file == null) {
//			return success;
//		}
//		Session session = null;
//		Transaction transaction = null;
//		
//		try {
//			session = HibernateWrapper.getSession().openSession();
//			transaction = session.beginTransaction();
//			if(file.getId() > 0) {
//				session.saveOrUpdate(file);
//			} else {
//				session.save(file);
//			}
//			transaction.commit();
//			success = true;
//		} catch (Exception e) {
//			logger.error(EXCEPTION_MESSAGE, e);
//			if (transaction != null) {
//				transaction.rollback();
//			}
//		} finally {
//			if (session != null) {
//				session.close();
//			}
//		}
//		return success;
//	}
	
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
