package com.bagnet.nettracer.tracing.dao;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Expression;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.actions.salvage.SalvageSearchAction;
import com.bagnet.nettracer.tracing.bmo.LostFoundBMO;
import com.bagnet.nettracer.tracing.bmo.OhdBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.LostAndFoundIncident;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.salvage.Salvage;
import com.bagnet.nettracer.tracing.db.salvage.SalvageBox;
import com.bagnet.nettracer.tracing.db.salvage.SalvageItem;
import com.bagnet.nettracer.tracing.db.salvage.SalvageOHDReference;
import com.bagnet.nettracer.tracing.forms.salvage.SalvageSearchForm;
import com.bagnet.nettracer.tracing.utils.DateUtils;


public class SalvageDAO {
	
	private static final String EXCEPTION_MESSAGE = "Exception in SalvageDAO";
	
	private static Logger logger = Logger.getLogger(SalvageSearchAction.class);
	
	public static Salvage loadSalvage(int id) {
		Session session = null;
		Salvage salvage = null;
		
		try {
			session = HibernateWrapper.getSession().openSession();
			salvage = (Salvage) session.get(Salvage.class, id);
		} catch (Exception e) {
			logger.error(EXCEPTION_MESSAGE, e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return salvage;
	}
	
	public static boolean saveSalvage(Salvage salvage) {
		boolean success = false;
		if (salvage == null) {
			return success;
		}
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = HibernateWrapper.getSession().openSession();
			transaction = session.beginTransaction();
			if(salvage.getSalvageId() > 0) {
				session.merge(salvage);
			} else {
				session.save(salvage);
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
	
	@SuppressWarnings("unchecked")
	public static Set getSalvagesFromSearchForm(SalvageSearchForm form, Agent agent) {
		LinkedHashSet results = null;
		Session session = null;

		try {
			session = HibernateWrapper.getSession().openSession();
			Criteria criteria = session.createCriteria(Salvage.class);
			criteria = getSalvageIdCriteria(form, criteria);
			criteria = getSalvageStatusCriteria(form, criteria);
			criteria = getSalvageDateCriteria(form, agent, criteria);
			results = new LinkedHashSet(criteria.list());
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
	
	private static Criteria getSalvageIdCriteria(SalvageSearchForm form, Criteria criteria) {
		if (form.getSalvageId() > 0) {
			criteria.add(Expression.like("salvageId", form.getSalvageId()));
		}
		return criteria;
	}
	
	private static Criteria getSalvageStatusCriteria(SalvageSearchForm form, Criteria criteria) {
		if (form.getSalvageStatus() == TracingConstants.SALVAGE_ALL) {
			criteria.add(Expression.in("status", new Integer[] {TracingConstants.SALVAGE_OPEN, TracingConstants.SALVAGE_CLOSED}));
		} else {
			criteria.add(Expression.eq("status", form.getSalvageStatus()));
		}
		return criteria;
	}

	private static Criteria getSalvageDateCriteria(SalvageSearchForm form, Agent agent, Criteria criteria) {
		Date startDate = DateUtils.convertToDate(form.getS_createtime(), "", agent.getCurrentlocale());
		Date endDate = DateUtils.convertToDate(form.getE_createtime(), "", agent.getCurrentlocale());
		
		if (startDate != null && endDate != null) {
			criteria.add(Expression.between("salvageDate", startDate, endDate));
		} else if (startDate != null && endDate == null) {
			criteria.add(Expression.ge("salvageDate", startDate));
		} else if (startDate == null && endDate != null) {
			criteria.add(Expression.le("salvageDate", endDate));
		}
		
		return criteria;
	}
	
	public static LostAndFoundIncident loadLostAndFound(String id) {
		Session session = null;
		LostAndFoundIncident lf = null;
		
		try {
			session = HibernateWrapper.getSession().openSession();
			lf = (LostAndFoundIncident) session.get(LostAndFoundIncident.class, id);
		} catch (Exception e) {
			logger.error(EXCEPTION_MESSAGE, e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return lf;
	}
	
	public static OHD loadOhd(String id) {
		Session session = null;
		OHD ohd = null;
		
		try {
			session = HibernateWrapper.getSession().openSession();
			ohd = (OHD) session.get(OHD.class, id);
		} catch (Exception e) {
			logger.error(EXCEPTION_MESSAGE, e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return ohd;
	}

	
	public static boolean saveCompleteSalvage(Salvage salvage, Agent a) {
		boolean success = false;
		if (salvage == null) {
			return success;
		}
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = HibernateWrapper.getSession().openSession();

			closeFoundObjects(salvage, a, session);
			closeOhds(salvage, a, session);
			
			transaction = session.beginTransaction();
			session.merge(salvage);
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

	private static void closeFoundObjects(Salvage salvage, Agent a, Session session) {
		for (SalvageBox box : salvage.getSalvageBoxes()) {
			for (SalvageItem item : box.getSalvageItems()) {
				
					
				if (item.getLostAndFoundId() != null) {
					LostAndFoundIncident lf = SalvageDAO.loadLostAndFound(item.getLostAndFoundId());
					if (lf != null) {
						Status reportStatus = new Status();
						reportStatus.setStatus_ID(41);
						Status disposal_status = new Status();
						disposal_status.setStatus_ID(TracingConstants.LF_STATUS_SALVAGED);
						
						lf.setReport_status(reportStatus);
						lf.setDisposal_status(disposal_status);
						LostFoundBMO.insertLostFound(lf, a, session);
					
						logger.info("Closed: " + lf.getFile_ref_number());
					}
				}
			}
		}
	}
	


	private static void closeOhds(Salvage salvage, Agent a, Session session) {
		Status closedStatus = new Status();
		closedStatus.setStatus_ID(TracingConstants.OHD_STATUS_CLOSED);
		
		
		for (SalvageOHDReference ref: salvage.getOhdReferences()) {
			try {
				OHD ohd = OhdBMO.getOHDByID(ref.getOhdId(), session);
				if (ohd.getStatus().getStatus_ID() != TracingConstants.OHD_STATUS_CLOSED) {
					
					Status disposal_status = new Status();
					disposal_status.setStatus_ID(TracingConstants.LF_STATUS_SALVAGED);
					ohd.setDisposal_status(disposal_status);
					
					ohd.setStatus(closedStatus);
					OhdBMO.updateOHD(ohd, a, session);
				} else {
					session.evict(ohd);
				}
				logger.info("Closed: " + ohd.getOHD_ID());
			} catch (Exception e) {
				logger.error("closeUSAirOldMassOhdsInSQLServer: Exception encountered", e);
			}
		}
	}
	
}
