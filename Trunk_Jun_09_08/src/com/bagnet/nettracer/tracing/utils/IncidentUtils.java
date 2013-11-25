/*
 * Created on Oct 28, 2004
 *
 * Administrator
 */
package com.bagnet.nettracer.tracing.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.constant.TracingConstants.SortParam;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Company;
import com.bagnet.nettracer.tracing.db.IATA_irregularity_code;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Item;
import com.bagnet.nettracer.tracing.db.ItemType;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.forms.ClaimsToBeProcessedForm;
import com.bagnet.nettracer.tracing.forms.ViewTemporaryReportsForm;

/**
 * @author Administrator
 * 
 * create date - Oct 28, 2004
 */
public class IncidentUtils {

	public static List retrieveClaimsList(ClaimsToBeProcessedForm form, String sort, Agent a,
			int rowsperpage, int currpage) {
		Session sess = null;

		try {
			sess = HibernateWrapper.getSession().openSession();
			
			TimeZone tz = TimeZone.getTimeZone(AdminUtils.getTimeZoneById(a.getDefaulttimezone()).getTimezone());

			Date sdate = null, edate = null;
			Date sdate1 = null, edate1 = null; // add one for timezone
			Date stime = null; // time to compare (04:00 if eastern, for example)


			StringBuffer sql = new StringBuffer();
			sql.append("from " + "com.bagnet.nettracer.tracing.db.Incident incident where 1=1 ");
			sql.append(" and incident.stationassigned.company.companyCode_ID = :company");
			sql.append(" and incident.stationassigned.stationcode = :stationcode");
			sql.append(" and incident.status.status_ID <> :status_ID");

			if (form != null) {
				if (form.getInc_num() != null && form.getInc_num().length() > 0) {
					sql.append(" and incident.incident_ID like :inc_num ");
				}

				ArrayList dateal = null;
				if ((dateal = IncidentUtils.calculateDateDiff(form.getS_time(),form.getE_time(),tz,a)) == null) {
					return null;
				} 
				sdate = (Date)dateal.get(0);sdate1 = (Date)dateal.get(1);
				edate = (Date)dateal.get(2);edate1 = (Date)dateal.get(3);
				stime = (Date)dateal.get(4);
				
				
				if (sdate != null) {
					if (edate != null && sdate != edate) {
						sql.append(" and ((incident.createdate= :startdate and incident.createtime >= :starttime) "
							+ " or (incident.createdate= :enddate1 and incident.createtime <= :starttime)"
							+ " or (incident.createdate > :startdate and incident.createdate <= :enddate))");

					} else {
						sql.append(" and ((incident.createdate= :startdate and incident.createtime >= :starttime) "
								+ " or (incident.createdate= :startdate1 and incident.createtime <= :starttime))");
					}
				}
			}

			if (sort != null && sort.length() > 0) {
				if (sort.equalsIgnoreCase(SortParam.INCIDENT.getParamString())) {
					sql.append(" order by incident.incident_ID  asc ");
				} else {
					if (sort.equalsIgnoreCase(SortParam.CREATEDATE.getParamString())) {
						sql.append("  order by incident.createdate desc ");
					}
				}
			} else {
				sql.append(" order by incident.incident_ID desc ");
			}

			Query q = sess.createQuery(sql.toString());

			if (rowsperpage > 0) {
				int startnum = currpage * rowsperpage;
				q.setFirstResult(startnum);
				q.setMaxResults(rowsperpage);
			}

			q.setString("company", a.getStation().getCompany().getCompanyCode_ID());
			q.setString("stationcode", "CLAIM");
			q.setInteger("status_ID", TracingConstants.MBR_STATUS_CLOSED);

			if (form != null) {
				if (form.getInc_num() != null && form.getInc_num().length() > 0) {
					q.setString("inc_num", form.getInc_num());
				}
				if (sdate != null) {
					if (edate != null && sdate != edate) {
						q.setDate("startdate", sdate);
						q.setTime("starttime", stime);
						q.setDate("enddate", edate);
						q.setDate("enddate1", edate1);
						
					} else {
						q.setDate("startdate", sdate);
						q.setDate("startdate1", sdate1);
						q.setTime("starttime", stime);
					}
				}
			}
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

	@SuppressWarnings("unchecked")
	public static List<ItemType> retrieveItemTypes() {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(ItemType.class);
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

	public static ItemType retrieveItemTypeWithId(int type_id, String locale) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(ItemType.class);
			cri.add(Restrictions.eq("itemType_ID", new Integer(type_id)));
			return (ItemType) cri.list().get(0);
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
	
	public static ItemType retrieveItemTypeWithDesc(String desc) {
		ItemType retVal = new ItemType();
		
		if (desc.equalsIgnoreCase("Delayed")) {
			retVal.setItemType_ID(1);
		} else if (desc.equalsIgnoreCase("Pilfered")) {
			retVal.setItemType_ID(2);
		} else if (desc.equalsIgnoreCase("Damaged")) {
			retVal.setItemType_ID(3);
		}
		return retVal;
	}

	/**
	 * get report method id
	 */
	public static int getReportMethod(String val) {
		return ((ClientUtils) SpringUtils.getBean("clientUtils")).getReportMethodType(val);
	}
	
	public static int getSalutationid(String val) {
		if (val == null || val.length() == 0) return 0;
		if (val.equals("Dr")) return 1;
		else if (val.equals("Mr")) return 2;
		else if (val.equals("Ms")) return 3;
		else if (val.equals("Miss")) return 4;
		else if (val.equals("Mrs")) return 5;
		else if (val.equals("Other")) return 6;
		
		return 0;
	}
	
	public static List getIncidents(Agent user, String sort, ViewTemporaryReportsForm form,
			String status_ID, String station_id, int rowsperpage, int currpage, boolean isCount) {
		return getIncidents(user, sort, form, status_ID, station_id, rowsperpage, currpage, isCount, false);
	}
	public static List getIncidents(Agent user, String sort, ViewTemporaryReportsForm form,
			String status_ID, String station_id, int rowsperpage, int currpage, boolean isCount, boolean dirtyRead) {
		OHD ret = null;
		Session sess = null;
		try {
			TimeZone tz = TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone());

			
			if(dirtyRead) {
				sess = HibernateWrapper.getDirtySession().openSession();
			}
			else {
				sess = HibernateWrapper.getSession().openSession();
			}
			StringBuffer sql = new StringBuffer(512);

			if (isCount) sql
					.append("select count(incident.incident_ID) from com.bagnet.nettracer.tracing.db.Incident incident where 1=1 ");
			else sql
					.append("select distinct incident from com.bagnet.nettracer.tracing.db.Incident incident where 1=1 ");

			sql.append(" and incident.stationassigned.station_ID = :stationID ");
			sql.append(" and incident.status.status_ID = :status_ID ");

			if (form.getIncident_num() != null && form.getIncident_num().length() > 0) {
				sql.append(" and incident.incident_ID like :incident_ID ");
			}

			Date sdate = null, edate = null;
			Date sdate1 = null, edate1 = null; // add one for timezone
			Date stime = null; // time to compare (04:00 if eastern, for example)
			String dateq = "";
			
			ArrayList dateal = null;
			if ((dateal = IncidentUtils.calculateDateDiff(form.getS_time(),form.getE_time(),tz,user)) == null) {
				return null;
			} 
			sdate = (Date)dateal.get(0);sdate1 = (Date)dateal.get(1);
			edate = (Date)dateal.get(2);edate1 = (Date)dateal.get(3);
			stime = (Date)dateal.get(4);
			
			
			if (sdate != null) {
				if (edate != null && sdate != edate) {
					sql.append(" and ((incident.createdate= :startdate and incident.createtime >= :starttime) "
						+ " or (incident.createdate= :enddate1 and incident.createtime <= :starttime)"
						+ " or (incident.createdate > :startdate and incident.createdate <= :enddate))");

				} else {
					sql.append(" and ((incident.createdate= :startdate and incident.createtime >= :starttime) "
							+ " or (incident.createdate= :startdate1 and incident.createtime <= :starttime))");
				}
			}

			
			if (!isCount) {
				if (sort != null && sort.length() > 0) {
					if (sort.equalsIgnoreCase("incident")) {
						sql.append(" order by incident.incident_ID  asc ");
					} else {
						if (sort.equalsIgnoreCase("createdate")) {
							sql.append("  order by incident.createdate desc ");
						}
					}
				} else {
					sql.append("  order by incident.createdate desc ");
				}
			}

			Query q = sess.createQuery(sql.toString());

			if (rowsperpage > 0) {
				int startnum = currpage * rowsperpage;
				q.setFirstResult(startnum);
				q.setMaxResults(rowsperpage);
			}

			q.setInteger("stationID", Integer.parseInt(station_id));
			q.setInteger("status_ID", Integer.parseInt(status_ID));

			if (sdate != null) {
				if (edate != null && sdate != edate) {
					q.setDate("startdate", sdate);
					q.setTime("starttime", stime);
					q.setDate("enddate", edate);
					q.setDate("enddate1", edate1);
					
				} else {
					q.setDate("startdate", sdate);
					q.setDate("startdate1", sdate1);
					q.setTime("starttime", stime);
				}
			}

			
			if (form.getIncident_num() != null && form.getIncident_num().length() > 0) {
				q.setString("incident_ID", form.getIncident_num());
			}
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
	public static int retrieveClaimsListCount(ClaimsToBeProcessedForm form, Agent a) {
		return retrieveClaimsListCount(form, a, false);
	}

	public static int retrieveClaimsListCount(ClaimsToBeProcessedForm form, Agent a, boolean dirtyRead) {
		Session sess = null;

		try {
			TimeZone tz = TimeZone.getTimeZone(AdminUtils.getTimeZoneById(a.getDefaulttimezone()).getTimezone());

			Date sdate = null, edate = null;
			Date sdate1 = null, edate1 = null; // add one for timezone
			Date stime = null; // time to compare (04:00 if eastern, for example)
			String dateq = "";
			
			if(dirtyRead) {
				sess = HibernateWrapper.getDirtySession().openSession();
			}
			else {
				sess = HibernateWrapper.getSession().openSession();
			}

			StringBuffer sql = new StringBuffer();
			sql.append("select count(incident.incident_ID) from " + "com.bagnet.nettracer.tracing.db.Incident incident where 1=1 ");
			sql.append(" and incident.stationassigned.company.companyCode_ID = :company");
			sql.append(" and incident.stationassigned.stationcode = :stationcode");
			sql.append(" and incident.status.status_ID <> :status_ID");


			if (form != null) {
				if (form.getInc_num() != null && form.getInc_num().length() > 0) {
					sql.append(" and incident.incident_ID like :inc_num ");
				}

				
				
				ArrayList dateal = null;
				if ((dateal = IncidentUtils.calculateDateDiff(form.getS_time(),form.getE_time(),tz,a)) == null) {
					return 0;
				} 
				sdate = (Date)dateal.get(0);sdate1 = (Date)dateal.get(1);
				edate = (Date)dateal.get(2);edate1 = (Date)dateal.get(3);
				stime = (Date)dateal.get(4);
				
				
				if (sdate != null) {
					if (edate != null && sdate != edate) {
						sql.append(" and ((incident.createdate= :startdate and incident.createtime >= :starttime) "
							+ " or (incident.createdate= :enddate1 and incident.createtime <= :starttime)"
							+ " or (incident.createdate > :startdate and incident.createdate <= :enddate))");

					} else {
						sql.append(" and ((incident.createdate= :startdate and incident.createtime >= :starttime) "
								+ " or (incident.createdate= :startdate1 and incident.createtime <= :starttime))");
					}
				}
			}

			Query q = sess.createQuery(sql.toString());
			q.setString("company", a.getStation().getCompany().getCompanyCode_ID());
			q.setString("stationcode", "CLAIM");
			q.setInteger("status_ID", TracingConstants.MBR_STATUS_CLOSED);

			if (form != null) {
				if (form.getInc_num() != null && form.getInc_num().length() > 0) {
					q.setString("inc_num", form.getInc_num());
				}
				if (sdate != null) {
					if (edate != null && sdate != edate) {
						q.setDate("startdate", sdate);
						q.setTime("starttime", stime);
						q.setDate("enddate", edate);
						q.setDate("enddate1", edate1);
						
					} else {
						q.setDate("startdate", sdate);
						q.setDate("startdate1", sdate1);
						q.setTime("starttime", stime);
					}
				}
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

	public static List retrieveIncidentCodes(String locale, int incident_type) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(IATA_irregularity_code.class).add(
					Restrictions.eq("locale", locale));
			if (incident_type == TracingConstants.LOST_DELAY
					|| incident_type == TracingConstants.MISSING_ARTICLES) {
				Disjunction desc = Restrictions.disjunction();
				desc.add(Restrictions.lt("loss_code", new Integer(80)));
				desc.add(Restrictions.gt("loss_code", new Integer(89)));
				cri.add(desc);
			}
			if (incident_type == TracingConstants.DAMAGED_BAG) {
				cri.add(Restrictions.lt("loss_code", new Integer(90)));
			}
			cri.addOrder(Order.asc("loss_code"));
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
	
	public static List<Incident> getStationAssignedList(int station_id, boolean disabling) {
		Session sess = null;
		try {

			// Search for all incidents for station
			String query = "select incident.incident_ID from com.bagnet.nettracer.tracing.db.Incident incident where";
			
			// Search for only open incidents for a station
			if (disabling) {
				query += " incident.stationassigned.station_ID = :station_id";
				query += " and incident.status.status_ID != :mbr_closed_status";
			} else {
				query += " (incident.stationassigned.station_ID = :station_id or incident.faultstation.station_ID = :station_id)";
			}
			
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess.createQuery(query);
			q.setInteger("station_id", station_id);
			if (disabling) {
				q.setInteger("mbr_closed_status", TracingConstants.MBR_STATUS_CLOSED);
			}

			return q.list();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (sess != null)
					sess.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static boolean incidentWithLossCode(String loss_code, int itemtype_id) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(Incident.class).add(
					Restrictions.eq("loss_code", new Integer(loss_code)));
			cri.add(Restrictions.eq("itemtype.itemType_ID", new Integer(loss_code)));
			List list = cri.list();
			if (list != null && list.size() > 0) return true;
			else return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
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

	public static boolean checkCreatedByAgent(String agent_id) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(Incident.class);
			cri.createCriteria("agent").add(Restrictions.eq("agent_ID", new Integer(agent_id)));
			List results = cri.list();
			if (results != null && results.size() > 0) return true;
			else return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
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

	public static Incident findIncidentByID(String incident_ID) throws HibernateException {
		Session sess = HibernateWrapper.getSession().openSession();

		try {
			Query q = sess
					.createQuery("from com.bagnet.nettracer.tracing.db.Incident incident where incident.incident_ID= :incident_ID");
			q.setParameter("incident_ID", incident_ID);
			List list = q.list();

			if (list.size() == 0) {
				return null;
			}
			Incident iDTO = (Incident) list.get(0);

			return iDTO;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			sess.close();
		}
	}
	
	/**
	 * method to calculate the agent timezone based date/time for comparing dates in db
	 * @param sdate
	 * @param sdate1
	 * @param edate
	 * @param edate1
	 * @param stime
	 * @param srDTO
	 * @param tz
	 * @return
	 */
	public static ArrayList calculateDateDiff(String starttime, String endtime, TimeZone tz, Agent u) {
		ArrayList al = new ArrayList();
		Date sdate=null,sdate1=null,edate=null, edate1=null, stime=null;
		if (starttime != null && starttime.length() > 0) {
			sdate = DateUtils.convertToDate(starttime, u.getDateformat().getFormat(), null);
			if (sdate == null) // invalid date
				return null;
			
			if (TracerDateTime.getHourDiff(tz) == 0) {
				sdate1 = sdate;
			} else {

				Calendar c = new GregorianCalendar();
				c.setTime(sdate);
				c.add(Calendar.DAY_OF_MONTH, 1);
				sdate1 = c.getTime();
			}
			stime = DateUtils.convertToDate(Integer.toString(TracerDateTime.getHourDiff(tz)), "H", null);

		}
		if (endtime != null && endtime.length() > 0) {
			edate = DateUtils.convertToDate(endtime, u.getDateformat().getFormat(), null);
			if (edate == null) // invalid date
				return null;
			
			if (TracerDateTime.getHourDiff(tz) == 0) {
				edate1 = edate;
			} else {
				Calendar c = new GregorianCalendar();
				c.setTime(edate);
				c.add(Calendar.DAY_OF_MONTH, 1);
				edate1 = c.getTime();
			}
		}
		al.add(sdate);
		al.add(sdate1);
		al.add(edate);
		al.add(edate1);
		al.add(stime);

		return al;
	}
	
	/** 
	 * if the table has one column that keeps date and time, then use this method
	 * @param starttime
	 * @param endtime
	 * @param tz
	 * @param u
	 * @return
	 */
	public static ArrayList calculateDateTimeDiff(String starttime, String endtime, TimeZone tz, Agent u) {
		ArrayList al = new ArrayList();
		Date sdate=null,edate=null;
		if (starttime != null && starttime.length() > 0) {
			sdate = DateUtils.convertToDate(starttime, u.getDateformat().getFormat(), null);
			if (sdate == null) // invalid date
				return null;
			
			int hoursdif = TracerDateTime.getHourDiff(tz);
			Calendar c = new GregorianCalendar();
			c.setTime(sdate);
			c.add(Calendar.HOUR_OF_DAY, hoursdif);
			sdate = c.getTime();
			
		}
		if (endtime != null && endtime.length() > 0) {
			edate = DateUtils.convertToDate(endtime, u.getDateformat().getFormat(), null);
			if (edate == null) // invalid date
				return null;
			
			int hoursdif = TracerDateTime.getHourDiff(tz);
			Calendar c = new GregorianCalendar();
			c.setTime(edate);
			c.add(Calendar.HOUR_OF_DAY, hoursdif);
			c.add(Calendar.DAY_OF_MONTH, 1);	// add another day as well
			edate = c.getTime();

		} else {
			if (sdate != null) {
				Calendar c = new GregorianCalendar();
				c.setTime(sdate);
				c.add(Calendar.DAY_OF_MONTH, 1);
				edate = c.getTime();
			}
		}
		al.add(sdate);
		al.add(edate);

		return al;
	}

	public static void manageIncidentEmailing(Company company, HttpServletRequest request) {
		if (!company.getVariable().isEmail_customer()) {
			request.setAttribute("companyDoesntEmail", 1);
		}
			
	}
	
	public static boolean promptToCloseFile(String incident_id, Incident incidentObj,
			HttpServletRequest request) {
		if (incidentObj != null) {
			return promptToCloseFile(incidentObj, request);
		} else if (incident_id != null) {
			Incident inc = IncidentBMO.getIncidentByID(incident_id, null);
			return promptToCloseFile(inc, request);
		}
		return false;
	}

	public static boolean promptToCloseFile(Incident incident, HttpServletRequest request) {
		boolean promptToClose = true;
		
		if (incident != null && incident.getStatus().getStatus_ID() != TracingConstants.MBR_STATUS_CLOSED && incident.getItemtype().getItemType_ID() == TracingConstants.LOST_DELAY) {
			for (Item item: (List<Item>)incident.getItemlist()) {
				if (item != null && (item.getStatus() == null || item.getStatus().getStatus_ID() != TracingConstants.ITEM_STATUS_PROCESSFORDELIVERY)) {
					promptToClose = false;
				}
			}
			
			if (promptToClose) {
				if (request != null) {
					request.setAttribute("promptToCloseFile", "1");
				}
				return true;
			}
		}
		return false;
	}
}