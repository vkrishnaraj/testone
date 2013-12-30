package com.bagnet.nettracer.tracing.bmo;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.type.StandardBasicTypes;

import com.bagnet.nettracer.exceptions.MissingRequiredFieldsException;
import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.BagDrop;
import com.bagnet.nettracer.tracing.db.audit.Audit_BagDrop;
import com.bagnet.nettracer.tracing.dto.BagDropDTO;
import com.bagnet.nettracer.tracing.utils.DateUtils;

/**
 * BagDropBMO - providing basic database methods
 * 
 * Should never utilize this BMO class directly, use BagDropUtils which contains all the control logic
 * 
 * @author Loupas
 *
 */
public class BagDropBMO {
	
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(BagDropBMO.class);

	/**
	 * Maps the sort parameters from DisplayTag to the appropriate database fields.
	 * Flight numbers are stored as a String, must cast to int in order to be sorted numerically
	 */
	private static Map<String, String> map = new LinkedHashMap<String, String>();
	
	static {
		map.put("origin", 		"b.originStationCode");
		map.put("dest",			"b.arrivalStationCode");
		map.put("flight",		"cast(b.flight as int)");
		map.put("scharr", 		"b.schArrivalDate");
		map.put("actarr", 		"b.actArrivalDate");
		map.put("bagdrop", 		"b.bagDropTime");
	}
	
	/**
	 * Updates the lastUpdated attribute to current GMT and saves
	 * 
	 * @param bagDrop
	 * @return
	 */
	public long insertBagDrop(BagDrop bagDrop, Agent agent){
		Session sess = null;
		Transaction t = null;
		bagDrop.setLastUpdated(DateUtils.convertToGMTDate(new Date()));
		
		try {
			sess = HibernateWrapper.getSession().openSession();
			t = sess.beginTransaction();
			sess.saveOrUpdate(bagDrop);
			sess.saveOrUpdate(getAuditBagDrop(bagDrop,agent));
			t.commit();
			return bagDrop.getId();
		} catch (Exception e){
			e.printStackTrace();
			if(t != null){
				t.rollback();
			}
			return -1;
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
	
	/**
	 * Maps current state of the BagDrop object to an audit element
	 * 
	 * @param bagDrop
	 * @param agent
	 * @return
	 */
	public Audit_BagDrop getAuditBagDrop(BagDrop bagDrop, Agent agent){
		Audit_BagDrop abd = new Audit_BagDrop();
		abd.setBagdrop(bagDrop);
		abd.setEntryDate(bagDrop.getLastUpdated());
		abd.setEntryMethod(bagDrop.getEntryMethod());
		abd.setModifyAgent(agent);
		abd.setSchArrivalDate(bagDrop.getSchArrivalDate());
		abd.setActArrivalDate(bagDrop.getActArrivalDate());
		abd.setBagDropTime(bagDrop.getBagDropTime());
		return abd;
	}
	
	/**
	 * Returns a List of BagDrop object based on the given criteria default sort order arrival date asc
	 * 
	 * @param dto
	 * @return
	 */
	public List<BagDrop> getBagDrop(BagDropDTO dto){
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			HashMap<String, Object>params = new HashMap<String, Object>();
			String sql = getBagDropQuery(dto, params);
			Query query = sess.createQuery(sql);
			query.setProperties(params);
			
			if(dto.getStartIndex() > -1 && dto.getMaxResults() > 0){
				query.setFirstResult(dto.getStartIndex());
				query.setMaxResults(dto.getMaxResults());
			}
			
			@SuppressWarnings("unchecked")
			List<BagDrop> results = query.list(); 
			return results;
		} catch (Exception e){
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
	
	/**
	 * Returns a count of BagDrop objects based on the given criteria
	 * 
	 * @param dto
	 * @return
	 */
	public long getBagDropCount(BagDropDTO dto){
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			HashMap<String, Object>params = new HashMap<String, Object>();
			String sql = "select count(b.id) " + getBagDropQuery(dto, params);
			Query query = sess.createQuery(sql);
			query.setProperties(params);
			return (Long)query.uniqueResult();
		} catch (Exception e){
			e.printStackTrace();
			return 0;
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
	
	/**
	 * Returns query used by getBagDrop and getBagDropCount
	 * Creates a new HashMap of Query parameters
	 * 
	 * @param dto
	 * @return
	 */
	private String getBagDropQuery(BagDropDTO dto, Map<String,Object>params){
		String sql = "from BagDrop b where 1=1 ";
		if(dto != null){
			if(dto.getId() > 0){
				sql += "and b.id = :id ";
				params.put("id", dto.getId());
				return sql;//unique id, no need to add any additional search criteria
			}
			if(dto.getAirlineCode() != null && dto.getAirlineCode().trim().length() > 0){
				sql += "and b.airline = :airline ";
				params.put("airline", dto.getAirlineCode().trim());
			}
			if(dto.getStartScheduleArrivalDate() != null){
				sql += "and b.schArrivalDate >= :startSchArrivalDate ";
				params.put("startSchArrivalDate", dto.getStartScheduleArrivalDate());
			}
			if(dto.getEndScheduleArrivalDate() != null){
				sql += "and b.schArrivalDate <= :endSchArrivalDate ";
				//adding one day to allow the search to be inclusive of the provided end date
				Calendar cal = GregorianCalendar.getInstance();
				cal.setTime(dto.getEndScheduleArrivalDate());
				cal.add(Calendar.DATE, 1);
				params.put("endSchArrivalDate", cal.getTime());
			}
			if(dto.getFlightNumber() != null && dto.getFlightNumber().trim().length() > 0){
				sql += "and b.flight like :flight ";
				params.put("flight", dto.getFlightNumber().trim());
			}
			if(dto.getArrivalStation() != null && dto.getArrivalStation().trim().length() > 0){
				sql += "and b.arrivalStationCode = :arrivalStationCode ";
				params.put("arrivalStationCode", dto.getArrivalStation().trim());
			}
			if(dto.getOriginStation() != null && dto.getOriginStation().trim().length() > 0){
				sql += "and b.originStationCode = :originStationCode ";
				params.put("originStationCode", dto.getOriginStation().trim());
			}
			if(dto.getSort() != null){
				sql += "order by " + (BagDropBMO.map.get(dto.getSort())!=null?BagDropBMO.map.get(dto.getSort()):"b.schArrivalDate");
				sql += dto.getDir()==null||TracingConstants.SORT_ASCENDING.equals(dto.getDir()) ? " asc ":" desc " ;
			}
		}
		return sql;
	}
	
	/**
	 * Returns the ID of the most recent scheduled arrival that falls between the start and end date
	 * for the given company, flight number and arrival station
	 * 
	 * @param companycode
	 * @param flightNum
	 * @param arrivalStation
	 * @param startdate
	 * @param enddate
	 * @return
	 */
	public long getBagDropID(String companycode, String flightNum, String arrivalStation, Date startdate, Date enddate){
		if(companycode == null ||
				flightNum == null ||
				arrivalStation == null ||
				startdate == null ||
				enddate == null){
			throw new MissingRequiredFieldsException();
		}
		BagDropDTO dto = new BagDropDTO();
		dto.setAirlineCode(companycode);
		dto.setFlightNumber(flightNum);
		dto.setArrivalStation(arrivalStation);
		dto.setStartScheduleArrivalDate(startdate);
		dto.setEndScheduleArrivalDate(enddate);
		
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			HashMap<String, Object>params = new HashMap<String, Object>();
			String sql = "select b.id " + getBagDropQuery(dto, params) + " order by b.schArrivalDate desc";
			Query query = sess.createQuery(sql);
			query.setProperties(params);
			query.setMaxResults(1);
			@SuppressWarnings("unchecked")
			List<Long> results = query.list();
			if(results != null && results.size() > 0){
				return (Long)results.get(0);
			} else {
				return 0;
			}
		} catch (Exception e){
			e.printStackTrace();
			return 0;
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
	
	/**
	 * Returns the BagDrop for the given ID
	 * 
	 * @param id
	 * @return
	 */
	public BagDrop getBagDropByID(long id){
		BagDropDTO dto = new BagDropDTO();
		dto.setId(id);
		List<BagDrop> bagDropList = getBagDrop(dto);
		if(bagDropList != null && bagDropList.size() > 0){
			return bagDropList.get(0);
		} else {
			return null;
		}
	}
	
	/**
	 * Return the audit List for a given bag drop ID in desc order
	 * 
	 * @param bagdropId
	 * @return
	 */
	public List<Audit_BagDrop> getAudit_BagDropList(long bagdropId){
		Session sess = null;
		try {
			String sql = "from com.bagnet.nettracer.tracing.db.audit.Audit_BagDrop where bagdrop.id = :id order by entryDate desc";
			sess = HibernateWrapper.getSession().openSession();
			Query query = sess.createQuery(sql);
			query.setParameter("id", bagdropId);
			@SuppressWarnings("unchecked")
			List<Audit_BagDrop> results = query.list(); 
			return results;
		} catch (Exception e){
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
	
	/**
	 * Returns the most recent Audit_BagDrop entry date based on station and entryMethod
	 * 
	 * @param station
	 * @param entryMethod
	 * @return
	 */
	public Date getLastUpdateDate(String station, String companycode, int entryMethod){
		Session sess = null;
		try {
			String sql = "select max(abd.entryDate) entrydate from bagdrop bd left outer join audit_bagdrop abd on bd.id = abd.bagdrop_id " +
					"where bd.arrivalStationCode = :station " +
					"and bd.airline = :companycode " +
					"and abd.entryMethod = :entryMethod";
			sess = HibernateWrapper.getSession().openSession();
			SQLQuery query = sess.createSQLQuery(sql);
			query.setParameter("station", station);
			query.setParameter("companycode", companycode);
			query.setParameter("entryMethod", entryMethod);
			query.addScalar("entrydate", StandardBasicTypes.TIMESTAMP);
			
			Date result = (Date)query.uniqueResult();
			return result;
		} catch (Exception e){
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
	
	/**
	 * Returns the average time to coursel in minutes based on the presents of an actual arrival time or schedule arrival time (if both are present, use actual) and
	 * bagdrop time for the given company, station and date range.
	 * 
	 * @param station
	 * @param companycode
	 * @param start
	 * @param end
	 * @return
	 */
	public int getAverageTimeToCarousel(String station, String companycode, Date start, Date end){
		Session sess = null;
		try {
			
			String sql = "select avg(timestampdiff(MINUTE, case when actArrivalDate is null then schArrivalDate else actArrivalDate end, bagdroptime)) avgtime " +
			             "from bagdrop where scharrivaldate between :start and :end " +
			             "and arrivalStationCode = :station " +
			             "and airline = :companycode " +
			             "and bagdroptime is not null and (schArrivalDate is not null or actArrivalDate is not null) ";   
					
			sess = HibernateWrapper.getSession().openSession();
			SQLQuery query = sess.createSQLQuery(sql);
			query.setParameter("station", station);
			query.setParameter("companycode", companycode);
			query.setParameter("start", start);
			query.setParameter("end", end);
			query.addScalar("avgtime", StandardBasicTypes.INTEGER);
			
			Integer result = (Integer)query.uniqueResult();
			return result;
		} catch (Exception e){
			return 0;
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
	
}
