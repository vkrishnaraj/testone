package com.bagnet.nettracer.tracing.bmo;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.CustomerViewableComment;
import com.bagnet.nettracer.tracing.db.Item;
import com.bagnet.nettracer.tracing.db.MessageCopy;
import com.bagnet.nettracer.tracing.db.PaxCommunication;
import com.bagnet.nettracer.tracing.db.PaxCommunication.PaxCommunicationStatus;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.IncidentUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.tracing.utils.TracerProperties;

public class PaxCommunicationBMO {

	private static Logger logger = Logger.getLogger(PaxCommunicationBMO.class);
	
	@SuppressWarnings("unchecked")
	public static List<PaxCommunication> getPaxCommunication(String incident_ID, Session sess) {
		boolean sessionNull = (sess == null);

		try {
			if (sessionNull) {
				sess = HibernateWrapper.getSession().openSession();
			}
			
			Criteria cri = sess.createCriteria(PaxCommunication.class)
					.add(Restrictions.eq("incident.incident_ID", incident_ID))
					.addOrder(Order.asc("createdate"));
			if (cri.list().size() > 0) {
				return cri.list();
			} else {
				logger.debug("Unable to find any pax communication for: " + incident_ID);
				return null;
			}
		} catch (Exception e) {
			logger.error("Unable to retrieve pax communication due to error: " + e);
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
	public static boolean updateNEWPaxCommunicationStatus(String incident_ID, Session sess, String newStatus, Agent agent) {
		//TODO: THIS LIST NEEDS TO COME FROM SESSION INSTEAD OF FROM DB
		List<PaxCommunication> myPaxCommunications = getNEWPaxCommunicationStatus(incident_ID, sess);
		return updateStatus(myPaxCommunications, newStatus, agent);
	}
	
	public static boolean updateNEWPaxCommunicationStatus(List<PaxCommunication> myPaxCommunications, String newStatus, Agent agent) {
		//TODO: THIS LIST NEEDS TO COME FROM SESSION INSTEAD OF FROM DB
		//step one - find out all the ones that 
		return updateStatus(myPaxCommunications, newStatus, agent);
	}
	
	public static boolean isThereNewPaxCommunications(String incident_ID, Session sess) {
		boolean result = false;
		List<PaxCommunication> myPaxCommunications = getNEWPaxCommunicationStatus(incident_ID, sess);
		if(myPaxCommunications != null) {
			result = true;
		}
		return result;
	}
	
	public static boolean isThereNewPaxCommunications(Session sess) {
		boolean result = false;
		List<PaxCommunication> myPaxCommunications = getAllNEWPaxCommunication(sess);
		if(myPaxCommunications != null) {
			result = true;
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	private static List<PaxCommunication> getAllNEWPaxCommunication(Session sess) {
		boolean sessionNull = (sess == null);

		try {
			if (sessionNull) {
				sess = HibernateWrapper.getSession().openSession();
			}
			
			Criteria cri = sess.createCriteria(PaxCommunication.class)
					.add(Restrictions.isNull("agent")) //flagging this is by pax
					.add(Restrictions.eq("status", PaxCommunicationStatus.NEW));
			if (cri.list().size() > 0) {
				return cri.list();
			} else {
				logger.debug("Unable to find any new pax communication at all");
				return null;
			}
		} catch (Exception e) {
			logger.error("Unable to retrieve pax communication due to error: " + e);
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
	
	@SuppressWarnings("unchecked")
	private static List<PaxCommunication> getNEWPaxCommunicationStatus(String incident_ID, Session sess) {
		boolean sessionNull = (sess == null);

		try {
			if (sessionNull) {
				sess = HibernateWrapper.getSession().openSession();
			}
			
			Criteria cri = sess.createCriteria(PaxCommunication.class)
					.add(Restrictions.eq("incident.incident_ID", incident_ID))
					.add(Restrictions.isNull("agent")) //flagging this is by pax
					.add(Restrictions.eq("status", PaxCommunicationStatus.NEW));
			if (cri.list().size() > 0) {
				return cri.list();
			} else {
				logger.debug("Unable to find any new pax communication for: " + incident_ID);
				return null;
			}
		} catch (Exception e) {
			logger.error("Unable to retrieve pax communication due to error: " + e);
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
	private static boolean updateStatus(List list, String myNewStatus, Agent agent){
		boolean flag=true;
		
		if(list == null) {
			flag=false;
		} else {
			try {
				Session session=HibernateWrapper.getSession().openSession();
				Transaction tx=session.beginTransaction();
				PaxCommunicationStatus myPaxCommunicationStatus;
				if (myNewStatus.equals("Acknowledge")) {
					myPaxCommunicationStatus = PaxCommunicationStatus.ACKNOWLEDGED;
				} else {
					myPaxCommunicationStatus = PaxCommunicationStatus.RESPONDED;
				}
				Iterator it=list.iterator();
				while(it.hasNext()){
					PaxCommunication myPaxCommunication = (PaxCommunication)it.next();
					myPaxCommunication.setStatus(myPaxCommunicationStatus);
					//set the acknowledge_date and acknowledge agent
					java.util.Date myDate = TracerDateTime.getGMTDate();
					myPaxCommunication.setAcknowledge_timestamp(myDate);
					myPaxCommunication.setAcknowledge_agent(agent);
					session.update(myPaxCommunication);
					if(tx.isActive()){
						tx.commit();
					}
					else{
						tx=session.beginTransaction();
						tx.commit();
					}
				}
				session.close();
			} catch (HibernateException e) {
				flag=false;
				e.printStackTrace();
			}	
			
		}
		return flag;
	}
	
	public static int getPaxMessagesCount(String stationId, String status_id, String s_date, String e_date,
			String search_sub, String search_file, Agent user, String sort) {
		return getPaxMessagesCount(stationId, status_id, s_date, e_date, search_sub, search_file, user, sort, false);
	}
	
	public static int getPaxMessagesCount(String stationId, String status_id, String s_date, String e_date,
			String search_sub, String search_file, Agent user, String sort, boolean dirtyRead) {
		Session sess = null;

		try {
			if(dirtyRead) {
				sess = HibernateWrapper.getDirtySession().openSession();
			}
			else {
				sess = HibernateWrapper.getSession().openSession();
			}

			String sql = "select count(*) from "
					+ "com.bagnet.nettracer.tracing.db.PaxCommunication paxcommunication where 1=1 ";
			//sql += " and paxcommunication.agent is null";
			sql += " and paxcommunication.incident.stationassigned.station_ID = :station_id";

			if (search_file != null && search_file.trim().length() > 0) {
				sql += " and paxcommunication.incident.incident_ID = :file_ref_number";
			}
			
			Date sdate=null,edate=null;
			if (user != null) {
				TimeZone tz = TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone());
				ArrayList dateal = null;
				if ((dateal = IncidentUtils.calculateDateTimeDiff(s_date,e_date,tz,user)) == null) {
					return 0;
				} 
				sdate = (Date)dateal.get(0);edate = (Date)dateal.get(1);
			}
			
			if (sdate != null && !sdate.equals("")) {
				sql += " and paxcommunication.createdate between :sdate and :edate ";
			}

			if (search_sub != null && search_sub.trim().length() > 0) {
				sql += " and paxcommunication.comment like :comment";
			}
			if (!status_id.equalsIgnoreCase("-1")) {
				sql += " and paxcommunication.status = :status_ID";
			} 

			sql += " and paxcommunication.agent is null";
			
			Query q = sess.createQuery(sql);
			int asdf = 1;
			q.setInteger("station_id", Integer.parseInt(stationId));

			if (search_file != null && search_file.trim().length() > 0) {
				q.setString("file_ref_number", search_file.trim());
			}
			if (sdate != null && !sdate.equals("")) {
				q.setDate("sdate", sdate);
				q.setDate("edate", edate);
			}

			if (search_sub != null && search_sub.trim().length() > 0) {
				q.setString("comment", "%" + search_sub.trim() + "%");
			}
			
			if (!status_id.equalsIgnoreCase("-1")) {
				q.setString("status_ID", status_id);
			}
			//q.setString("status_ID", status_id);
			List list = q.list();
			if (list.size() > 0) {
				return ((Long) list.get(0)).intValue();
			} else {
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
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
	
	public static int getHighPriorityPaxMessagesCount(String stationId, String status_id) {
		Session sess = null;
		
		int hoursBack = 0;
		Date ageDate = TracerDateTime.getGMTDate();
		
		try {
			hoursBack = PropertyBMO.getValueAsInt(PropertyBMO.HIGH_PRIORITY_PAX_COMMUNICATION_HOURS);
		} catch (Exception e) {
		
		}
		
		if (hoursBack == 0) {
			return 0;
		} else {
			ageDate.setTime(ageDate.getTime() - (hoursBack * 60*60*1000));
		}
		
		try {
			
			
			
			sess = HibernateWrapper.getDirtySession().openSession();
			String sql = "select count(*) from "
					+ "com.bagnet.nettracer.tracing.db.PaxCommunication paxcommunication where 1=1 ";
			sql += " and paxcommunication.incident.stationassigned.station_ID = :station_id";
			sql += " and paxcommunication.agent is null";			
			sql += " and paxcommunication.createdate < :sdate";
			
			if (!status_id.equalsIgnoreCase("-1")) {
				sql += " and paxcommunication.status = :status_ID";
			} 
			
			Query q = sess.createQuery(sql);
			int asdf = 1;
			q.setInteger("station_id", Integer.parseInt(stationId));
			q.setDate("sdate", ageDate);
			
			if (!status_id.equalsIgnoreCase("-1")) {
				q.setString("status_ID", status_id);
			}

			List list = q.list();
			if (list.size() > 0) {
				return ((Long) list.get(0)).intValue();
			} else {
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
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
	
	
	
	public static List<PaxCommunication> getPaxMessages(String stationId, int rowsperpage, int currpage, String status_id,
			String s_date, String e_date, String search_sub, String search_file, Agent user, String sort) {
		Session sess = null;

		try {
			sess = HibernateWrapper.getSession().openSession();

			String sql = "select message from"
					   + " com.bagnet.nettracer.tracing.db.PaxCommunication message";
			//sql += " join message.incident incident ";
			sql += " where 1=1";
			sql += " and message.agent is null";
			sql += " and message.incident.stationassigned.station_ID = :station_id";

			if (search_file != null && search_file.trim().length() > 0) {
				sql += " and message.incident.incident_ID = :file_ref_number";
			}
			if (search_sub != null && search_sub.trim().length() > 0) {
				sql += " and message.comment like :subject";
			}
			
			Date sdate=null,edate=null;
			TimeZone tz = TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone());
			ArrayList dateal = null;
			if ((dateal = IncidentUtils.calculateDateTimeDiff(s_date,e_date,tz,user)) == null) {
				return null;
			} 
			sdate = (Date)dateal.get(0);edate = (Date)dateal.get(1);
			
			if (sdate != null && !sdate.equals("")) {
				sql += " and message.createdate between :sdate and :edate ";
			}
			

			if (! status_id.equalsIgnoreCase("-1")) {
				sql += " and message.status = :status_ID";
			} 

			sql += " order by message.incident.incident_ID asc";
			
			if (sort != null && sort.length() > 0) {
				if (sort.equalsIgnoreCase("order_by_file_number")) {
					//sql += " message.incident.incident_ID asc";
				} else {
					if (sort.equalsIgnoreCase("order_by_date")) {
						sql += ", message.createdate desc ";
					}
				}
			} else {
				sql += ", message.createdate asc ";
			}
			
			Query q = sess.createQuery(sql);

			if (rowsperpage > 0) {
				int startnum = currpage * rowsperpage;
				q.setFirstResult(startnum);
				q.setMaxResults(rowsperpage);
			}

			q.setInteger("station_id", Integer.parseInt(stationId));
			if (search_file != null && search_file.trim().length() > 0) {
				q.setString("file_ref_number", search_file.trim());
			}
			if (sdate != null && !sdate.equals("")) {
				q.setDate("sdate", sdate);
				q.setDate("edate", edate);
			}
			
			if (search_sub != null && search_sub.trim().length() > 0) {
				q.setString("subject", "%" + search_sub.trim() + "%");
			}
			if (! status_id.equalsIgnoreCase("-1")) {
				q.setString("status_ID", status_id);
			}
			//q.setString("status_ID", status_id);
			return q.list();
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
	
	public static PaxCommunication getPaxCommunication(String paxCommunicationId) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(PaxCommunication.class).add(
					Expression.eq("id", new Integer(paxCommunicationId)));
			return (PaxCommunication) cri.list().get(0);
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
	
}
