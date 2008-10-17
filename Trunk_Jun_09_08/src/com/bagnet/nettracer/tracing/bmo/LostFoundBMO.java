/*
 * Created on Jul 15, 2004
 *
 * Administrator
 */
package com.bagnet.nettracer.tracing.bmo;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;

import org.apache.log4j.Logger;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.LostAndFoundIncident;
import com.bagnet.nettracer.tracing.db.LostAndFound_Photo;
import com.bagnet.nettracer.tracing.db.LostAndFound_Range;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.audit.Audit_LostAndFoundIncident;
import com.bagnet.nettracer.tracing.forms.SearchLostFoundForm;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.audit.AuditLostFoundUtils;

/**
 * @author Administrator
 * 
 * create date - Jul 15, 2004
 */
public class LostFoundBMO {
	private static Logger logger = Logger.getLogger(LostFoundBMO.class);

	public boolean insertLostFound(LostAndFoundIncident iDTO, Agent mod_agent) throws HibernateException {
		Transaction t = null;
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			
			LostAndFoundIncident oldinc = null;
			String incident_id = iDTO.getFile_ref_number();
			boolean isnew = false;
			if (incident_id == null || incident_id.length() <= 0) {
				isnew = true;
				iDTO.setFile_ref_number(getLostAndFound_ID(iDTO.getCreate_station()));
			} else {
				oldinc = getLostAndFoundIncident(incident_id);
				if (oldinc == null)
					isnew = true;
			}
			
			t = sess.beginTransaction();

			// save incident
			if (iDTO.getFile_ref_number() != null) {
				if (isnew)
					sess.save(iDTO);
				else {
					// delete first then insert
					sess.delete(oldinc);
					
					if (iDTO.getPhotos() != null && iDTO.getPhotos().size() > 0) {
						for (Iterator i = iDTO.getPhotos().iterator(); i.hasNext();) {
							LostAndFound_Photo o = (LostAndFound_Photo) i.next();
							o.setPhoto_ID(0);
						}
					}
					
					sess.save(iDTO);
				}
				t.commit();
			} else {
				return false;
			}

			if (iDTO.getFiling_agent().getStation().getCompany().getVariable().getAudit_lost_found() == 1) {
				Audit_LostAndFoundIncident audit_lfi = AuditLostFoundUtils.getAuditLostAndFound(iDTO, mod_agent);
				if (audit_lfi != null) {
					t = sess.beginTransaction();
					sess.save(audit_lfi);
					t.commit();

				}
			}

		} catch (Exception e) {
			logger.error("unable to insert into database: " + e);

			if (t != null)
				t.rollback();
			return false;
		} finally {

			sess.close();
		}
		return true;
	}

	/**
	 * Obtain company record based on passed in company code
	 * 
	 * @param companyCode
	 *          the code related to the company
	 * @return company or null if exception or nothing found.
	 */
	public LostAndFoundIncident getLostAndFoundIncident(String file_ref_number) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(LostAndFoundIncident.class).add(
					Expression.eq("file_ref_number", file_ref_number));
			return (LostAndFoundIncident) cri.list().get(0);
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

	/**
	 * Automatically generate 10 character incident number 3 alpha/7 numeric
	 * 
	 * @param station
	 *          station for which identifier is sought
	 * @return the on hand identifier for the station
	 */
	public String getLostAndFound_ID(Station station) {
		// get highest num from Incident_Range table
		Session sess = null;
		try {
			station = findStationByID(station.getStation_ID());
			if (station == null) {
				logger.error("invalid station");
				return null;
			}
			String companycode = station.getCompany().getCompanyCode_ID();
			String stationcode = station.getStationcode();

			sess = HibernateWrapper.getSession().openSession();
			Transaction t = sess.beginTransaction();
			LostAndFound_Range ir = new LostAndFound_Range();
			ir.setCompanycode_ID(companycode);
			sess.save(ir);
			t.commit();
			long newnum = ir.getCurrent_num();
			if (newnum == 0) {
				logger.error("unable to create a new ohd number");
				return null;
			}

			// get the last record with this companycode_ID;
			Query q = sess
					.createQuery("select count(lostandfound_range.current_num) from com.bagnet.nettracer.tracing.db.LostAndFound_Range lostandfound_range "
							+ "where lostandfound_range.companycode_ID = :companycode_ID group by lostandfound_range.companycode_ID");
			q.setString("companycode_ID", companycode);
			List list = q.list();

			if (list == null || list.size() == 0) {
				// starts off at 1
				newnum = 1;
			} else {
				newnum = ((Long) list.get(0)).intValue();
			}

			StringBuffer s = new StringBuffer();
			s.append(stationcode);
			s.append(companycode);
			String num = Long.toString(newnum);
			// padd new number to # digits that total length will equal to
			// tracingconstants.incident_len
			for (int i = 0; i < TracingConstants.INCIDENT_LEN - companycode.length()
					- stationcode.length() - num.length(); i++) {
				s.append("0");
			}
			s.append(num);

			// making sure there isn't another incident id in the table already
			// this would have never happen in production
			String lostfound_num = s.toString().toUpperCase();
			q = sess.createQuery("from com.bagnet.nettracer.tracing.db.LostAndFoundIncident lfi "
					+ "where lfi.file_ref_number = :file_ref_number");
			q.setString("file_ref_number", lostfound_num);

			list = q.list();
			if (list != null && list.size() > 0) {
				return getLostAndFound_ID(station);
			} else {
				return lostfound_num;
			}

		} catch (Exception e) {
			logger.error("unable to create a new lost/found number: " + e);
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.error("unable to close connection: " + e);
				}
			}
		}
	}




	/**
	 * Find stations by ID
	 * 
	 * @param station_ID
	 *          station's ID
	 * @return station if found; null otherwise
	 */
	private Station findStationByID(int station_ID) {
		try {
			String query = "select station from com.bagnet.nettracer.tracing.db.Station station "
					+ "where station.station_ID=:station_ID";
			Session sess = HibernateWrapper.getSession().openSession();
			Query q = sess.createQuery(query);
			q.setInteger("station_ID", station_ID);
			List list = q.list();

			if (list.size() == 0) {
				logger.debug("unable to find station: " + station_ID);
				return null;
			}
			Station station = (Station) list.get(0);
			sess.close();
			return station;
		} catch (Exception e) {
			logger.error("unable to retrieve station: " + e);
			return null;
		}
	}

	public List findLostFound(SearchLostFoundForm daform, Agent user, int rowsperpage,
			int currpage, boolean isCount) {

		Session sess = null;

		try {
			sess = HibernateWrapper.getSession().openSession();
			StringBuffer sql = new StringBuffer(512);

			if (isCount) sql
					.append("select count(lfd.file_ref_number) from com.bagnet.nettracer.tracing.db.LostAndFoundIncident lfd ");
			else sql
					.append("select distinct lfd from com.bagnet.nettracer.tracing.db.LostAndFoundIncident lfd ");

			sql.append(" where 1=1 ");

			if (daform.getFile_ref_number() != null && daform.getFile_ref_number().length() > 0) {
				sql.append(" and lfd.file_ref_number like :file_ref_number ");
			}

			if (daform.getFinding_agent_name() != null && daform.getFinding_agent_name().length() > 0) {
				sql.append(" and lfd.finding_agent_name like :finding_agent_name ");
			}

			if (daform.getCustomer_name() != null && daform.getCustomer_name().length() > 0) {
				sql.append(" and lfd.customer_name like :customer_name ");
			}

			if (daform.getCustomer_address() != null && daform.getCustomer_address().length() > 0) {
				sql.append(" and lfd.customer_address like :customer_address ");
			}

			if (daform.getCustomer_tel() != null && daform.getCustomer_tel().length() > 0) {
				sql.append(" and lfd.customer_tel like :customer_tel ");
			}

			if (daform.getLocation() != null && daform.getLocation().length() > 0) {
				sql.append(" and lfd.location like :location ");
			}
			
			if (daform.getFiling_station()  > 0) {
				sql.append(" and lfd.create_station.station_ID = :station_id ");
			}

			if (daform.getItem_description() != null && daform.getItem_description().length() > 0) {
				sql.append(" and lfd.item_description like :item_description ");
			}

			Date sdate = null, edate = null;
			
			if (daform.getS_createtime() != null && daform.getS_createtime().length() > 0) {
				sdate = DateUtils.convertToDate(daform.getS_createtime(), user.getDateformat().getFormat(), null);
			}
			if (daform.getE_createtime() != null && daform.getE_createtime().length() > 0) {
				edate = DateUtils.convertToDate(daform.getE_createtime(), user.getDateformat().getFormat(), null);
			}
			
			if (sdate != null && !sdate.equals("")) {
				if (edate != null && !edate.equals("")) {
					if (sdate.equals(edate)) sql.append(" and lfd.dateFoundLost = :sdate ");
					else sql.append(" and lfd.dateFoundLost between :sdate and :edate ");
				} else {
					sql.append(" and lfd.dateFoundLost = :sdate ");
				}
			}

			if (daform.getReport_status_ID() != null && daform.getReport_status_ID().length() > 0) {
				sql.append(" and lfd.report_status.status_ID = :report_status_ID ");
			}

			if (daform.getDisposal_status_ID() != null && daform.getDisposal_status_ID().length() > 0) {
				sql.append(" and lfd.disposal_status.status_ID = :disposal_status_ID ");
			}

			if (daform.getReport_type() != null && daform.getReport_type().length() > 0) {
				sql.append(" and lfd.report_type = :report_type ");
			}

			
			if (!isCount) sql.append(" order by lfd.file_ref_number");

			Query q = sess.createQuery(sql.toString());
			if (rowsperpage > 0) {
				int startnum = currpage * rowsperpage;
				q.setFirstResult(startnum);
				q.setMaxResults(rowsperpage);
			}

			if (daform.getFile_ref_number() != null && daform.getFile_ref_number().length() > 0) {
				q.setString("file_ref_number", daform.getFile_ref_number());
			}

			if (daform.getFinding_agent_name() != null && daform.getFinding_agent_name().length() > 0) {
				q.setString("finding_agent_name", "%" + daform.getFinding_agent_name() + "%");
			}

			if (daform.getCustomer_name() != null && daform.getCustomer_name().length() > 0) {
				q.setString("customer_name", daform.getCustomer_name());
			}

			if (daform.getCustomer_address() != null && daform.getCustomer_address().length() > 0) {
				q.setString("customer_address", daform.getCustomer_address());
			}

			if (daform.getCustomer_tel() != null && daform.getCustomer_tel().length() > 0) {
				q.setString("customer_tel", daform.getCustomer_tel());
			}

			if (daform.getLocation() != null && daform.getLocation().length() > 0) {
				q.setString("location", "%" + daform.getLocation() + "%");
			}
			
			if (daform.getFiling_station()  > 0) {
				q.setInteger("station_id",daform.getFiling_station());
			}

			if (daform.getItem_description() != null && daform.getItem_description().length() > 0) {
				q.setString("item_description", "%" + daform.getItem_description() + "%");
			}

			if (sdate != null && !sdate.equals("")) {
				if (edate != null && !edate.equals("")) {
					if (sdate.equals(edate)) q.setDate("sdate", sdate);
					else {
						q.setDate("sdate", sdate);
						q.setDate("edate", edate);
					}
				} else {
					q.setDate("sdate", sdate);
				}
			}

			if (daform.getReport_status_ID() != null && daform.getReport_status_ID().length() > 0) {
				q.setInteger("report_status_ID", Integer.parseInt(daform.getReport_status_ID()));
			}

			if (daform.getDisposal_status_ID() != null && daform.getDisposal_status_ID().length() > 0) {
				q.setInteger("disposal_status_ID", Integer.parseInt(daform.getDisposal_status_ID()));
			}

			if (daform.getReport_type() != null && daform.getReport_type().length() > 0) {
				q.setInteger("report_type", Integer.parseInt(daform.getReport_type()));
			}

			return q.list();
		} catch (Exception e) {
			logger.error("unable to retrieve lost/found: " + e);
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.error("unable to close connection: " + e);
				}
			}
		}
	}
}