package com.bagnet.nettracer.tracing.dao.impl;


import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.dao.DocumentDAO;
import com.bagnet.nettracer.tracing.db.documents.Document;

public class DocumentDAOImpl implements DocumentDAO {

	private static Logger logger = Logger.getLogger(DocumentDAOImpl.class);
	
	@Override
	public Document load(long documentId) {
		Document document = null;
		Session session = null;
		try {
			session = HibernateWrapper.getSession().openSession();
			Criteria criteria = session.createCriteria(Document.class, "d");
			criteria.add(Restrictions.eq("t.id", documentId));
			document = (Document) criteria.uniqueResult();
		} catch (Exception e) {
			logger.error("Failed to load document with id: " + documentId, e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return document;
	}

	@Override
	public long save(Document document) {
		long id = 0;
		if (document == null) {
			return id;
		}
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = HibernateWrapper.getSession().openSession();
			transaction = session.beginTransaction();			
			session.save(document);
			transaction.commit();
			id = document.getId();
		} catch (Exception e) {
			logger.error("Failed to save document", e);
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return id;
	}

	@Override
	public boolean update(Document document) {
		boolean success = false;
		if (document == null) {
			return success;
		}
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = HibernateWrapper.getSession().openSession();
			transaction = session.beginTransaction();			
			session.merge(document);
			transaction.commit();
			success = true;
		} catch (Exception e) {
			logger.error("Failed to update document with id: " + document.getId(), e);
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

	@Override
	public boolean delete(long documentId) {
		boolean success = false;
		if (documentId <= 0) return success;
		
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateWrapper.getSession().openSession();
			transaction = session.beginTransaction();
			Document document = (Document) session.createCriteria(Document.class, "d").add(Restrictions.eq("d.id", documentId)).uniqueResult();
			session.delete(document);
			transaction.commit();
			success = true;
		} catch (Exception e) {
			logger.error("Failed to delete document with id: " + documentId, e);
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
