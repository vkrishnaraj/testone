/*
 * Created on Sep 2, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.TimeZone;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Message;
import com.bagnet.nettracer.tracing.db.MessageCopy;
import com.bagnet.nettracer.tracing.db.Recipient;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Status;

/**
 * @author Ankur Gupta
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class MessageUtils {

	public static Message getMessage(String messageId) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(Message.class).add(
					Expression.eq("message_id", new Integer(messageId)));
			return (Message) cri.list().get(0);
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

	public static List getReportMessages(String fileref, int type) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(Message.class).add(
					Expression.eq("file_ref_number", fileref)).add(
					Expression.eq("file_type", new Integer(type)));
			return cri.list();
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


	public static MessageCopy getMessageCopy(String messagecopyId) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(MessageCopy.class).add(
					Expression.eq("message_copy_id", new Integer(messagecopyId)));
			return (MessageCopy) cri.list().get(0);
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

	public static List getMessages(String stationId, int rowsperpage, int currpage, int status_id,
			String s_date, String e_date, String search_sub, String search_file, Agent user, String sort) {
		Session sess = null;

		try {
			sess = HibernateWrapper.getSession().openSession();

			String sql = "select message from "
					+ "com.bagnet.nettracer.tracing.db.MessageCopy message where 1=1 ";
			sql += " and message.receiving_station.station_ID = :station_ID";

			if (search_file != null && search_file.trim().length() > 0) {
				sql += " and message.parent_message.file_ref_number = :file_ref_number";
			}
			if (search_sub != null && search_sub.trim().length() > 0) {
				sql += " and message.subject like :subject";
			}
			
			Date sdate=null,edate=null;
			TimeZone tz = TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone());
			ArrayList dateal = null;
			if ((dateal = IncidentUtils.calculateDateTimeDiff(s_date,e_date,tz,user)) == null) {
				return null;
			} 
			sdate = (Date)dateal.get(0);edate = (Date)dateal.get(1);
			
			if (sdate != null && !sdate.equals("")) {
				sql += " and message.parent_message.send_date between :sdate and :edate ";
			}
			

			if (status_id != -1) {
				sql += " and message.status.status_ID = :status_ID";
			} else {
				sql += " and message.status.status_ID <> :status_ID and message.status.status_ID <> "
					+ TracingConstants.MESSAGE_STATUS_SENT + " and message.status.status_ID <> " 
					+ TracingConstants.MESSAGE_STATUS_DELETED;
			}

			if (sort != null && sort.length() > 0) {
				if (sort.equalsIgnoreCase("order_file_number")) {
					sql += " order by message.parent_message.file_ref_number asc";
				} else {
					if (sort.equalsIgnoreCase("order_date")) {
						sql += " order by message.parent_message.send_date desc ";
					}
				}
			} else {
				sql += " order by message.parent_message.send_date desc ";
			}

			Query q = sess.createQuery(sql);

			if (rowsperpage > 0) {
				int startnum = currpage * rowsperpage;
				q.setFirstResult(startnum);
				q.setMaxResults(rowsperpage);
			}

			q.setInteger("station_ID", Integer.parseInt(stationId));
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
			q.setInteger("status_ID", status_id);
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
	
	public static void sendmessage(Station station, String subject, Agent user,
			String message, String incident_id, String ohd_id) {
		Message msg = new Message();
		msg.setSend_date(TracerDateTime.getGMTDate());

		msg.setAgent(user);
		msg.setSend_station(station);

		String body = message;
		msg.setMessage(body);
		msg.setSubject(subject + incident_id);
		if (incident_id == "" && ohd_id == "") {
			msg.setFile_type(-1);
			msg.setFile_ref_number("");
		}

		if (incident_id != "") {
			msg.setFile_ref_number(incident_id);
			msg.setFile_type(1);
		}
		if (ohd_id != "") {
			msg.setFile_type(0);
			msg.setFile_ref_number(ohd_id);
		}
		Recipient rec = new Recipient();
		rec.setCompany_code(station.getCompany().getCompanyCode_ID());
		rec.setStation(station);
		rec.setMessage(msg);
		List newRecpList = new ArrayList();
		newRecpList.add(rec);

		msg.setRecipients(new HashSet(newRecpList));
		HibernateUtils.save(msg);
	}
	
	public static int getMessagesCount(String stationId, int status_id, String s_date, String e_date,
			String search_sub, String search_file, Agent user, String sort) {
		return getMessagesCount(stationId, status_id, s_date, e_date, search_sub, search_file, user, sort, false);
	}

	public static int getMessagesCount(String stationId, int status_id, String s_date, String e_date,
			String search_sub, String search_file, Agent user, String sort, boolean dirtyRead) {
		Session sess = null;

		try {
			if(dirtyRead) {
				sess = HibernateWrapper.getDirtySession().openSession();
			}
			else {
				sess = HibernateWrapper.getSession().openSession();
			}

			String sql = "select count(message.message_copy_id) from "
					+ "com.bagnet.nettracer.tracing.db.MessageCopy message where 1=1 ";
			sql += " and message.receiving_station.station_ID = :station_ID";

			if (search_file != null && search_file.trim().length() > 0) {
				sql += " and message.parent_message.file_ref_number = :file_ref_number";
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
				sql += " and message.parent_message.send_date between :sdate and :edate ";
			}

			if (search_sub != null && search_sub.trim().length() > 0) {
				sql += " and message.subject like :subject";
			}
			if (status_id != -1) {
				sql += " and message.status.status_ID = :status_ID";
			} else {
				sql += " and message.status.status_ID <> :status_ID and message.status.status_ID <> "
						+ TracingConstants.MESSAGE_STATUS_SENT + " and message.status.status_ID <> " 
						+ TracingConstants.MESSAGE_STATUS_DELETED;
			}

			Query q = sess.createQuery(sql);
			q.setInteger("station_ID", Integer.parseInt(stationId));
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
			q.setInteger("status_ID", status_id);
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

	public static int getNewMessages(int stationId) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(MessageCopy.class).add(
					Expression.eq("status.status_ID", new Integer(TracingConstants.MESSAGE_STATUS_NEW))).add(
					Expression.eq("receiving_station.station_ID", new Integer(stationId)));
			return cri.list().size();
		} catch (Exception e) {
			System.out.println("message error: " + e.toString());
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