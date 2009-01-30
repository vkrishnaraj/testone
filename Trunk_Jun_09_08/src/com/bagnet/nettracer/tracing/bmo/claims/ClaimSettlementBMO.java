package com.bagnet.nettracer.tracing.bmo.claims;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.claims.AuditClaimSettlement;
import com.bagnet.nettracer.tracing.db.claims.AuditClaimSettlementBag;
import com.bagnet.nettracer.tracing.db.claims.AuditSettlementBagInventory;
import com.bagnet.nettracer.tracing.db.claims.ClaimSettlement;
import com.bagnet.nettracer.tracing.db.claims.ClaimSettlementBag;
import com.bagnet.nettracer.tracing.db.claims.SettlementBagInventory;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;


public class ClaimSettlementBMO {
	
	private static Logger logger = Logger.getLogger(ClaimSettlementBMO.class);
	
	public static ClaimSettlement getClaimSettlement(String incident_ID, Session sess) {
		boolean sessionNull = (sess == null);

		try {
			if (sessionNull) {
				sess = HibernateWrapper.getSession().openSession();
			}
			Query q = sess.createQuery("from com.bagnet.nettracer.tracing.db.claims.ClaimSettlement claim where claim.incident.incident_ID= :incident_ID");
			q.setParameter("incident_ID", incident_ID);
			List list = q.list();

			if (list.size() == 0) {
				logger.debug("Unable to find claim settlement object: " + incident_ID);
				return null;
			}
			ClaimSettlement obj = (ClaimSettlement) list.get(0);

			return obj;
		} catch (Exception e) {
			logger.error("Unable to retrieve claim settlement object: " + e);
			e.printStackTrace();
			return null;
		} finally {
			if (sess != null && sessionNull) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.error("Unable to close connection: " + e);
				}
			}
		}
	}
	
	public static ClaimSettlement saveClaimSettlement(ClaimSettlement claim, Session sess, Agent agent) {
		boolean sessionNull = (sess == null);
		Transaction t = null;
		
		try {
			if (sessionNull) {
				sess = HibernateWrapper.getSession().openSession();
			}
			
			t = sess.beginTransaction();
			sess.saveOrUpdate(claim);
			t.commit();
			auditClaimSettlement(claim, sess, agent);

		} catch (Exception e) {
			if (t != null) {
				t.rollback();
			}
			e.printStackTrace();
			throw new RuntimeException("Unable to save claim settlement object.");
		} finally {
			if (sessionNull) {
				sess.close();
			}
		}
		return claim;
	}
	
	public static boolean doesClaimSettlementClaimExist(String incidentId) {
		
		if (getClaimSettlement(incidentId, null) != null) {
			return true;
		}
		return false;
	}
	

	public static void auditClaimSettlement(ClaimSettlement cs, Session sess, Agent a) {
		Transaction t = null;
		try {
			t = sess.beginTransaction();
			
			AuditClaimSettlement acs = new AuditClaimSettlement();
			acs.setModifyingAgent(a);
			acs.setTime_modified(TracerDateTime.getGMTDate());
			ArrayList<AuditClaimSettlementBag> bagList = new ArrayList<AuditClaimSettlementBag>();
			acs.setAuditBagList(bagList);
			// Claim Settlement
			BeanUtils.copyProperties(acs, cs);

			// Bags
			for (ClaimSettlementBag bag: cs.getBagList()) {
				AuditClaimSettlementBag aBag = new AuditClaimSettlementBag();
				aBag.setAuditClaimSettlement(acs);
				ArrayList<AuditSettlementBagInventory> invList = new ArrayList<AuditSettlementBagInventory>();
				aBag.setAuditInventory(invList);
				BeanUtils.copyProperties(aBag, bag);
				bagList.add(aBag);
				
				for (SettlementBagInventory inv: bag.getInventory()) {
					AuditSettlementBagInventory aInv = new AuditSettlementBagInventory();
					aInv.setAuditClaimSettlementBag(aBag);
					BeanUtils.copyProperties(aInv, inv);
					invList.add(aInv);
				}
			}
			
			sess.save(acs);
			
			t.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (t != null) {
				t.rollback();
			}
		}
	}

}


