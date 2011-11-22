package com.bagnet.nettracer.tracing.bmo;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Region;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.audit.Audit_Station;
import com.bagnet.nettracer.tracing.utils.audit.AuditStationUtils;

public class RegionBMO {
	
	public static List<Region> getRegions(String companycode_id){
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess.createQuery("from com.bagnet.nettracer.tracing.db.Region r where r.company.companyCode_ID = :company and r.active = :active order by r.name asc");
			q.setParameter("company", companycode_id);
			q.setParameter("active", true);
			return (List<Region>) q.list();
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static Region getRegion(long region_id){
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(Region.class).add(
					Expression.eq("id", new Long(region_id)));
			if (cri.list().size() > 0)
				return (Region) cri.list().get(0);
			else
				return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static List<Station> getStationsByRegion(long region_id){
		String query = "from com.bagnet.nettracer.tracing.db.Station station where region.id = :region_id";
		Session session = null;
		List<Station> list = null;
		try {
			session = HibernateWrapper.getSession().openSession();
			Query q = session.createQuery(query);
			q.setParameter("region_id", region_id);
			list = q.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return list;
	}
	
	public static long saveRegion(Region region, Agent user){
		long id = -1;
		if (region == null) {
			return id;
		}
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = HibernateWrapper.getSession().openSession();
			transaction = session.beginTransaction();
			if(region.getId() > 0) {
				session.merge(region);
			} else {
				region.setActive(true);
				session.save(region);
			}
			transaction.commit();
			id = region.getId();
			if (user.getStation().getCompany().getVariable().getAudit_station() == 1) {
				saveRegionAudit(id, user);
			}
		} catch (Exception e) {
			e.printStackTrace();
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
	
	private static void saveRegionAudit(long region_id, Agent user){
		if(region_id < 0){
			return;
		}
		List<Station> list = RegionBMO.getStationsByRegion(region_id);
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateWrapper.getSession().openSession();
			transaction = session.beginTransaction();
			for(Station station:list){
				Audit_Station audit_station = AuditStationUtils.getAuditStation(station, user);
				if (audit_station != null) {
					session.save(audit_station);
				}
			}
			transaction.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	public static boolean updateStationRegion(List<Station> stations, Agent user){
		boolean success = false;
		if(stations == null){
			return false;
		}
		Session sess = null;
		Transaction transaction = null;
		
		List<Region>regionForAudit = RegionBMO.getRegions(user.getCompanycode_ID());
		try {
			sess = HibernateWrapper.getSession().openSession();
			transaction = sess.beginTransaction();
			Query q = sess.createSQLQuery("update station set region_id = :region, goal = :goal where station_id = :station");
			for(Station station:stations){
				if(station.getCurrentRegionId() != station.getRegion().getId() || station.getCurrentGoal() != station.getGoal()){
					q.setParameter("station", station.getStation_ID());
					q.setParameter("region", station.getRegion()!=null&&station.getRegion().getId()!=0?station.getRegion().getId():null);
					q.setParameter("goal", station.getGoal());
					q.executeUpdate();
					
					if(regionForAudit != null){
						for(Region region:regionForAudit){
							if(station.getRegion() != null && station.getRegion().getId() == region.getId()){
								station.setRegion(region);
							}
						}
					}
					if (user.getStation().getCompany().getVariable().getAudit_station() == 1) {
						Audit_Station audit_station = AuditStationUtils.getAuditStation(station, user);
						if (audit_station != null) {
							sess.save(audit_station);
						}
					}
				}
			}
			transaction.commit();
			success = true;
		} catch (Exception e){
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return success;
	}
	
	
	public static boolean resetStationRegion(long regionId, Agent user){
		boolean success = false;
		if(regionId <= 0){
			return false;
		}
		Session sess = null;
		Transaction transaction = null;
		try {
			List<Station> list = RegionBMO.getStationsByRegion(regionId);
			sess = HibernateWrapper.getSession().openSession();
			transaction = sess.beginTransaction();
			for(Station station:list){
				station.setRegion(null);
				sess.update(station);
				if (user.getStation().getCompany().getVariable().getAudit_station() == 1) {
					Audit_Station audit_station = AuditStationUtils.getAuditStation(station, user);
					if (audit_station != null) {
						sess.save(audit_station);
					}
				}
			}
			transaction.commit();
			success = true;
		} catch (Exception e){
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return success;
	}
	
	public static boolean deleteRegion(long id, Agent user){
		boolean success = false;
		Region r = RegionBMO.getRegion(id);
		if(r != null && RegionBMO.resetStationRegion(id, user)){
			Session sess = null;
			Transaction transaction = null;
			try {
				sess = HibernateWrapper.getSession().openSession();
				transaction = sess.beginTransaction();
				r.setActive(false);
				sess.merge(r);
				transaction.commit();
				success = true;
			} catch (Exception e){
				e.printStackTrace();
				if (transaction != null) {
					transaction.rollback();
				}
			} finally {
				if (sess != null) {
					try {
						sess.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return success;
	}
	
}
