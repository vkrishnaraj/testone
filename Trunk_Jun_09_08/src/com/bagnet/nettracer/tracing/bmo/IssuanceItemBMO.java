/**
 * 
 */
package com.bagnet.nettracer.tracing.bmo;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.dozer.DozerBeanMapper;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.BeanUtils;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.DeliverCompany;
import com.bagnet.nettracer.tracing.db.Deliver_ServiceLevel;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.issuance.AuditIssuanceItemQuantity;
import com.bagnet.nettracer.tracing.db.issuance.IssuanceItemQuantity;

/**
 * @author byron
 *
 */
public class IssuanceItemBMO {

	private static Logger logger = Logger.getLogger(IssuanceItemBMO.class);
	
	public static List<IssuanceItemQuantity> getQuantifiedItems(Station station) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Query query = sess.createQuery("from IssuanceItemQuantity q where q.station.stationcode = :code");
			query.setParameter("code", station.getStationcode());
			return  query.list();
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.fatal(e.getMessage());
				}
			}
		}
	}
	
	public static List<AuditIssuanceItemQuantity> getAuditQuantifiedItems(Station station) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Query query = sess.createQuery("from AuditIssuanceItemQuantity q where q.station.stationcode = :code");
			query.setParameter("code", station.getStationcode());
			return  query.list();
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.fatal(e.getMessage());
				}
			}
		}
	}
	
	public static void editQuantifiedItem(long id, int quantity, int minQuantity, Agent user, String incID) {
			Session sess = null;
			Transaction t = null;
			try {
				sess = HibernateWrapper.getSession().openSession();
				IssuanceItemQuantity qItem = (IssuanceItemQuantity) sess.load(IssuanceItemQuantity.class, id);
				int oldQuant = qItem.getQuantity();
				if (quantity > 0) {
					qItem.setQuantity(quantity);
					qItem.setMinimuActiveQuantity(minQuantity);
				} else {
					qItem.setQuantity(qItem.getQuantity() - 1);
				}
				Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
				AuditIssuanceItemQuantity auditQItem = mapper.map(qItem, AuditIssuanceItemQuantity.class);
				auditQItem.setEditAgent(user);
				auditQItem.setEditDate(new Date());
				auditQItem.setIncidentID(incID);
				auditQItem.setQuantityChange(qItem.getQuantity() - oldQuant);
				t = sess.beginTransaction();
				sess.update(qItem);
				sess.save(auditQItem);
				t.commit();
			} catch (Exception e) {
				logger.fatal(e.getMessage());
				t.rollback();
			} finally {
				if (sess != null) {
					try {
						sess.close();
					} catch (Exception e) {
						logger.fatal(e.getMessage());
					}
				}
			}
	}

}
