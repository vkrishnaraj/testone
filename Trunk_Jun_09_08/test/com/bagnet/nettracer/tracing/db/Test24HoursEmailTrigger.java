//package com.bagnet.nettracer.tracing.db;
//
//import java.sql.ResultSet;
//import java.text.DateFormat;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.Iterator;
//import java.util.List;
//
//import org.hibernate.HibernateException;
//import org.hibernate.SQLQuery;
//import org.hibernate.Session;
//import org.hibernate.Transaction;
//
//import com.bagnet.nettracer.hibernate.HibernateWrapper;
//import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
//import com.bagnet.nettracer.tracing.utils.SmsEmailService;
//
//public class Test24HoursEmailTrigger {
//
//	public void process24HoursEmails() {
//		try {
//			Session session = HibernateWrapper.getSession().openSession();
//
//			ResultSet rs = null;
//			Transaction t = null;
//			try {
//
//				// String query =
//				// "select Incident_ID, createdate, createtime from incident ";
//				String query = "select * from incident ";
//				query += "where status_ID = 12 ";
//				query += "and itemtype_ID = 1 ";
//				query += "and Incident_ID not in (select distinct incident_id from bdo where incident_ID IS NOT NULL) ";
//				query += "and Incident_ID not in (select distinct incident_id from z_us_24hrs_email where incident_ID IS NOT NULL) ";
//
//				String timeQuery_1 = "";
//				String timeQuery_2 = "";
//
//				timeQuery_1 += "and createdate >= ? ";
//				timeQuery_1 += "and createtime >= ? ";
//				timeQuery_1 += "and createdate <= ? ";
//				timeQuery_1 += "and createtime <= ? ";
//
//				timeQuery_2 += "and ";
//				timeQuery_2 += "((createdate = ? and createtime >= ?) "; // ie.
//				// 2010-05-24
//				timeQuery_2 += "or (createdate = ? and createtime <= ?)) "; // ie.
//				// 2010-05-25
//				// next
//				// day
//
//				SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
//				SimpleDateFormat timeformat = new SimpleDateFormat("HH:mm:ss");
//
//				DateFormat datetimeformatter = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
//				// test case one
//				// Date myChosenDate =
//				// (Date)datetimeformatter.parse("2010.02.03 00:30:00");
//
//				// test case two
//				// Date myChosenDate =
//				// (Date)datetimeformatter.parse("2010.02.03 15:23:47");
//
//				// test case three
//				// Date myChosenDate =
//				// (Date)datetimeformatter.parse("2010.01.11 00:40:10");
//
//				// test case four "2010.01.31 23:55:56"
//				Date myChosenDate = (Date) datetimeformatter.parse("2010.02.01 23:55:56");
//
//				Calendar endCal = Calendar.getInstance();
//				Calendar startCal = Calendar.getInstance();
//
//				endCal.setTime(myChosenDate);
//				startCal.setTime(myChosenDate);
//
//				// figure out whether the two times fall on the same day or two
//				// consecutive days
//				String myTime = timeformat.format(startCal.getTime());
//				if ((myTime.compareTo("00:00:00") >= 0) && (myTime.compareTo("01:00:00") <= 0)) {
//					System.out.println(" our time is in that critical spot...");
//					query += timeQuery_2;
//				} else {
//					System.out.println(" our time is NOT in that critical spot...");
//					query += timeQuery_1;
//				}
//
//				endCal.add(Calendar.HOUR, -24); // Subtracting 24 hour to
//												// current
//				// date time
//
//				String newEndDate = dateformat.format(endCal.getTime());
//				String newEndTime = timeformat.format(endCal.getTime());
//
//				startCal.add(Calendar.HOUR, -25); // Subtracting 25 hour to
//													// current
//				// date time
//
//				String newStartDate = dateformat.format(startCal.getTime());
//				String newStartTime = timeformat.format(startCal.getTime());
//
//				SQLQuery hibQuery = session.createSQLQuery(query).addEntity("incident", Incident.class);
//
//				hibQuery.setString(0, newStartDate);
//				hibQuery.setString(1, newStartTime);
//				hibQuery.setString(2, newEndDate);
//				hibQuery.setString(3, newEndTime);
//
//				System.out.println("the entire sql is:" + hibQuery.toString());
//
//				// execute query, and return
//				List myList = hibQuery.list();
//
//				Iterator itr = myList.iterator();
//				while (itr.hasNext()) {
//					Incident myItem = (Incident) itr.next();
//
//					String myIncidentId = myItem.getIncident_ID();
//					System.out.println("incidentId=" + myIncidentId);
//
//					Incident myIncident = IncidentBMO.getIncidentByID(myIncidentId, session);
//
//					if (myIncident == null) {
//						// System.out.println("incident is null, try another one.");
//					} else {
//						SmsEmailService smsEmailService = new SmsEmailService();
//						smsEmailService.send24HoursMessage(myIncident);
//					}
//
//					String insertQuery = "INSERT INTO z_us_24hrs_email (incident_ID) VALUES('" + myIncidentId + "')";
//
//					SQLQuery hibInsertQuery = session.createSQLQuery(insertQuery);
//					// hibInsertQuery.setString(0, myIncidentId);
//					t = session.beginTransaction();
//					int numOfRowInserted = hibInsertQuery.executeUpdate();
//					t.commit();
//					if (numOfRowInserted > 0) {
//						System.out.println("new item with incidentId=" + myIncidentId + " has been inserted.");
//					}
//
//				}
//
//			} catch (HibernateException e) {
//				e.printStackTrace();
//				if (t != null) {
//					t.rollback();
//				}
//			} finally {
//				if (session != null) {
//					session.close();
//				}
//			}
//
//		} catch (Exception e) {
//			logger.error("Error processing 24 hour emails: ", e);
//		}
//	}
//}
