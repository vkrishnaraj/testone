/*
 * Created on Jul 6, 2005
 *
 * Administrator
 */
package com.bagnet.nettracer.tracing.utils.audit;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TimeZone;

import org.hibernate.Query;
import org.hibernate.Session;

import org.apache.log4j.Logger;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.ControlLog;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.OHD_Address;
import com.bagnet.nettracer.tracing.db.OHD_Inventory;
import com.bagnet.nettracer.tracing.db.OHD_Itinerary;
import com.bagnet.nettracer.tracing.db.OHD_Passenger;
import com.bagnet.nettracer.tracing.db.OHD_Photo;
import com.bagnet.nettracer.tracing.db.Remark;
import com.bagnet.nettracer.tracing.db.audit.Audit_AirlineMembership;
import com.bagnet.nettracer.tracing.db.audit.Audit_ControlLog;
import com.bagnet.nettracer.tracing.db.audit.Audit_OHD;
import com.bagnet.nettracer.tracing.db.audit.Audit_OHD_Address;
import com.bagnet.nettracer.tracing.db.audit.Audit_OHD_Inventory;
import com.bagnet.nettracer.tracing.db.audit.Audit_OHD_Itinerary;
import com.bagnet.nettracer.tracing.db.audit.Audit_OHD_Passenger;
import com.bagnet.nettracer.tracing.db.audit.Audit_OHD_Photo;
import com.bagnet.nettracer.tracing.db.audit.Audit_OHD_Remark;
import com.bagnet.nettracer.tracing.db.audit.Audit_Remark;
import com.bagnet.nettracer.tracing.forms.audit.AuditOnHandForm;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.IncidentUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;

/**
 * @author Administrator
 * 
 * create date - Jul 6, 2005
 */
public class AuditOHDUtils {

	private static Logger logger = Logger.getLogger(AuditOHDUtils.class);

	public static List getAuditsForComparison(String audit_ohd_id) {

		Session sess = null;

		try {
			sess = HibernateWrapper.getSession().openSession();
			StringBuffer sql = new StringBuffer(512);

			sql.append("select aohd from com.bagnet.nettracer.tracing.db.audit.Audit_OHD aohd ");
			sql.append(" where 1=1 ");

			List audit_ohd_list = new ArrayList();

			StringTokenizer st = new StringTokenizer(audit_ohd_id, ",");

			int i = 0;
			while (st.hasMoreTokens()) {
				audit_ohd_list.add(st.nextToken());
				if (i < 1) {
					sql.append(" and audit_ohd_id = :audit_ohd" + i);
				} else {
					sql.append(" or audit_ohd_id = :audit_ohd" + i);
				}
				i++;
			}
			sql.append(" order by aohd.audit_ohd_id ");

			Query q = sess.createQuery(sql.toString());

			for (int j = 0; j < i; j++) {
				q.setString("audit_ohd" + j, (String) audit_ohd_list.get(j));
			}
			return q.list();
		} catch (Exception e) {
			logger.error("unable to retrieve ohd: " + e);
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
	 * 
	 * @param form
	 * @param user
	 * @param rowsperpage
	 * @param currpage
	 * @param iscount
	 * @return
	 */
	public static List findOnHandBagsByAuditSearchCriteria(AuditOnHandForm form, Agent user,
			int rowsperpage, int currpage, boolean iscount) {

		Session sess = null;

		try {
			sess = HibernateWrapper.getSession().openSession();
			StringBuffer sql = new StringBuffer(512);

			if (iscount) sql
					.append("select count(distinct ohd.OHD_ID) from com.bagnet.nettracer.tracing.db.OHD ohd, com.bagnet.nettracer.tracing.db.audit.Audit_OHD aohd ");
			else sql
					.append("select distinct ohd from com.bagnet.nettracer.tracing.db.OHD ohd, com.bagnet.nettracer.tracing.db.audit.Audit_OHD aohd ");

			sql.append(" where 1=1 and ohd.OHD_ID=aohd.OHD_ID ");

			if (form.getOhd_ID() != null && !form.getOhd_ID().equals("")) {
				sql.append(" and aohd.OHD_ID like :OHD_ID ");
			}

			if (form.getAgent().length() > 0) sql
					.append(" and aohd.modifying_agent.username like :agent ");

			Date sdate = null, edate = null;

			TimeZone tz = TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone());
			ArrayList dateal = null;
			if ((dateal = IncidentUtils.calculateDateTimeDiff(form.getS_createtime(),form.getE_createtime(),tz,user)) == null) {
				return null;
			} 
			sdate = (Date)dateal.get(0);edate = (Date)dateal.get(1);

			if (sdate != null && !sdate.equals("")) {
				sql.append(" and aohd.time_modified between :sdate and :edate ");
			}

			if (!iscount) sql.append(" order by ohd.OHD_ID");

			Query q = sess.createQuery(sql.toString());

			if (rowsperpage > 0) {
				int startnum = currpage * rowsperpage;
				q.setFirstResult(startnum);
				q.setMaxResults(rowsperpage);
			}

			if (form.getOhd_ID() != null && !form.getOhd_ID().equals("")) {
				q.setString("OHD_ID", form.getOhd_ID());
			}

			if (sdate != null && !sdate.equals("")) {
				q.setDate("sdate", sdate);
				q.setDate("edate", edate);
			}

			if (form.getAgent().trim().length() > 0) {
				q.setString("agent", form.getAgent().trim());
			}

			return q.list();
		} catch (Exception e) {
			logger.error("unable to retrieve ohd: " + e);
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
	 * 
	 * @param ohd_id
	 * @param rowsperpage
	 * @param currpage
	 * @param iscount
	 * @return
	 */
	public static List getAudits(String ohd_id, int rowsperpage, int currpage, boolean iscount) {

		Session sess = null;

		try {
			sess = HibernateWrapper.getSession().openSession();
			StringBuffer sql = new StringBuffer(512);
			if (iscount) sql
					.append("select count(aohd.OHD_ID) from com.bagnet.nettracer.tracing.db.audit.Audit_OHD aohd ");
			else sql
					.append("select distinct aohd from com.bagnet.nettracer.tracing.db.audit.Audit_OHD aohd ");
			sql.append(" where 1=1 and aohd.OHD_ID like :OHD_ID ");

			if (!iscount) sql.append(" order by aohd.time_modified");
			Query q = sess.createQuery(sql.toString());
			if (rowsperpage > 0) {
				int startnum = currpage * rowsperpage;
				q.setFirstResult(startnum);
				q.setMaxResults(rowsperpage);
			}
			q.setString("OHD_ID", ohd_id);
			return q.list();
		} catch (Exception e) {
			logger.error("unable to retrieve audit ohd: " + e);
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
	 * 
	 * @param ohd
	 * @param mod_agent
	 * @return @throws
	 *         Exception
	 */
	public static Audit_OHD getAuditOHD(OHD ohd, Agent mod_agent) throws Exception {
		Audit_OHD audit_ohd = new Audit_OHD();
		audit_ohd.set_DATEFORMAT(ohd.get_DATEFORMAT());
		audit_ohd.set_TIMEFORMAT(ohd.get_TIMEFORMAT());
		audit_ohd.set_TIMEZONE(ohd.get_TIMEZONE());
		audit_ohd.setAgent(ohd.getAgent());
		audit_ohd.setBagarrivedate(ohd.getBagarrivedate());
		audit_ohd.setClaimnum(ohd.getClaimnum());
		audit_ohd.setClose_date(ohd.getClose_date());
		audit_ohd.setColor(ohd.getColor());
		audit_ohd.setFirstname(ohd.getFirstname());
		audit_ohd.setFoundAtStation(ohd.getFoundAtStation());
		audit_ohd.setFounddate(ohd.getFounddate());
		audit_ohd.setFoundtime(ohd.getFoundtime());
		audit_ohd.setTime_modified(TracerDateTime.getGMTDate());
		audit_ohd.setFaultstation_ID(ohd.getFaultstation_ID());
		audit_ohd.setLoss_code(ohd.getLoss_code());
		audit_ohd.setMatched_incident(ohd.getMatched_incident());

		if (ohd.getControlLog() != null) {
			Set aControlLog = new HashSet();
			for (Iterator i = ohd.getControlLog().iterator(); i.hasNext();) {
				ControlLog cl = (ControlLog) i.next();

				Audit_ControlLog acl = new Audit_ControlLog();
				acl.setControlling_station(cl.getControlling_station());
				acl.setEnd_date(cl.getEnd_date());
				acl.setAudit_ohd(audit_ohd);
				acl.setStart_date(cl.getStart_date());
				acl.setControl_id(cl.getControl_id());
				acl.setOhd(ohd);
				aControlLog.add(acl);
			}
			audit_ohd.setControlLog(aControlLog);
		}

		if (ohd.getMembership() != null) {
			//copy the airline membership as a new entry
			Audit_AirlineMembership audit_mem = new Audit_AirlineMembership();
			audit_mem.setCompanycode_ID(ohd.getMembership().getCompanycode_ID());
			audit_mem.setMembership_ID(ohd.getMembership().getMembership_ID());
			audit_mem.setMembershipnum(ohd.getMembership().getMembershipnum());
			audit_mem.setMembershipstatus(ohd.getMembership().getMembershipstatus());

			audit_ohd.setMembership(audit_mem);
		}

		audit_ohd.setHoldingStation(ohd.getHoldingStation());
		audit_ohd.setLastname(ohd.getLastname());
		audit_ohd.setManufacturer_ID(ohd.getManufacturer_ID());
		audit_ohd.setManufacturer_other(ohd.getManufacturer_other());
		audit_ohd.setMiddlename(ohd.getMiddlename());
		audit_ohd.setOHD_ID(ohd.getOHD_ID());
		audit_ohd.setXdescelement_ID_1(ohd.getXdescelement_ID_1());
		audit_ohd.setXdescelement_ID_2(ohd.getXdescelement_ID_2());
		audit_ohd.setXdescelement_ID_3(ohd.getXdescelement_ID_3());
		audit_ohd.setType(ohd.getType());
		audit_ohd.setStorage_location(ohd.getStorage_location());
		audit_ohd.setStatus(ohd.getStatus());
		audit_ohd.setRecord_locator(ohd.getRecord_locator());
		audit_ohd.setOhd_type(ohd.getOhd_type());

		audit_ohd.setModifying_agent(mod_agent);

		//create a set of items

		if (ohd.getItems() != null) {
			Set audit_items = new HashSet();
			for (Iterator i = ohd.getItems().iterator(); i.hasNext();) {
				OHD_Inventory inv = (OHD_Inventory) i.next();
				Audit_OHD_Inventory aud_inv = new Audit_OHD_Inventory();

				aud_inv.setDescription(inv.getDescription());
				aud_inv.setOHD_categorytype_ID(inv.getOHD_categorytype_ID());
				aud_inv.setOHD_Inventory_ID(inv.getOHD_Inventory_ID());
				aud_inv.setOhd(audit_ohd);
				audit_items.add(aud_inv);
			}
			audit_ohd.setItems(audit_items);
		} else {
			audit_ohd.setItems(null);
		}

		if (ohd.getRemarks() != null) {
			Set audit_remarks = new HashSet();
			for (Iterator i = ohd.getRemarks().iterator(); i.hasNext();) {
				Remark remark = (Remark) i.next();
				Audit_OHD_Remark aud_remark = new Audit_OHD_Remark();

				aud_remark.set_DATEFORMAT(remark.get_DATEFORMAT());
				aud_remark.set_TIMEFORMAT(remark.get_TIMEFORMAT());
				aud_remark.set_TIMEZONE(remark.get_TIMEZONE());
				aud_remark.setAgent(remark.getAgent());
				aud_remark.setCreatetime(remark.getCreatetime());
				aud_remark.setRemark_ID(remark.getRemark_ID());
				aud_remark.setRemarktext(remark.getRemarktext());
				aud_remark.setRemarktype(remark.getRemarktype());
				aud_remark.setAudit_ohd(audit_ohd);
				audit_remarks.add(aud_remark);
			}
			audit_ohd.setRemarks(audit_remarks);
		} else {
			audit_ohd.setRemarks(null);
		}

		if (ohd.getPhotos() != null) {
			Set audit_photos = new HashSet();
			for (Iterator i = ohd.getPhotos().iterator(); i.hasNext();) {
				OHD_Photo photo = (OHD_Photo) i.next();
				Audit_OHD_Photo aud_photo = new Audit_OHD_Photo();

				aud_photo.setOHD_ID(photo.getOhd().getOHD_ID());
				aud_photo.setPhoto_ID(photo.getPhoto_ID());
				aud_photo.setPicpath(photo.getPicpath());
				aud_photo.setThumbpath(photo.getThumbpath());
				aud_photo.setOhd(audit_ohd);
				audit_photos.add(aud_photo);
			}
			audit_ohd.setPhotos(audit_photos);
		} else {
			audit_ohd.setPhotos(null);
		}

		if (ohd.getItinerary() != null) {
			Set audit_itinerary = new HashSet();
			for (Iterator i = ohd.getItinerary().iterator(); i.hasNext();) {
				OHD_Itinerary itin = (OHD_Itinerary) i.next();
				Audit_OHD_Itinerary aud_itin = new Audit_OHD_Itinerary();

				aud_itin.set_DATEFORMAT(itin.get_DATEFORMAT());
				aud_itin.set_TIMEFORMAT(itin.get_TIMEFORMAT());

				aud_itin.setScharrivetime(itin.getScharrivetime());
				aud_itin.setArrivedate(itin.getArrivedate());
				aud_itin.setActarrivetime(itin.getActarrivetime());

				aud_itin.setSchdeparttime(itin.getSchdeparttime());
				aud_itin.setDepartdate(itin.getDepartdate());
				aud_itin.setActdeparttime(itin.getActdeparttime());

				aud_itin.setAirline(itin.getAirline());
				aud_itin.setItinerary_ID(itin.getItinerary_ID());
				aud_itin.setFlightnum(itin.getFlightnum());
				aud_itin.setItinerarytype(itin.getItinerarytype());
				aud_itin.setLegfrom(itin.getLegfrom());
				aud_itin.setLegfrom_type(itin.getLegfrom_type());

				aud_itin.setLegto(itin.getLegto());
				aud_itin.setLegto_type(itin.getLegto_type());
				aud_itin.setOhd(audit_ohd);
				audit_itinerary.add(aud_itin);
			}
			audit_ohd.setItinerary(audit_itinerary);
		} else {
			audit_ohd.setItinerary(null);
		}

		if (ohd.getPassengers() != null) {
			Set audit_passenger = new HashSet();
			for (Iterator i = ohd.getPassengers().iterator(); i.hasNext();) {
				OHD_Passenger pass = (OHD_Passenger) i.next();
				Audit_OHD_Passenger aud_pass = new Audit_OHD_Passenger();

				aud_pass.setPassenger_id(pass.getPassenger_id());
				aud_pass.setFirstname(pass.getFirstname());
				aud_pass.setMiddlename(pass.getMiddlename());
				aud_pass.setLastname(pass.getLastname());
				aud_pass.setIsprimary(pass.getIsprimary());
				aud_pass.setOhd(audit_ohd);
				Set audit_addresses = new HashSet();

				OHD_Address address = (OHD_Address) pass.getAddress(0);

				if (address != null) {
					Audit_OHD_Address audit_address = new Audit_OHD_Address();

					audit_address.setAddress_ID(address.getAddress_ID());
					audit_address.setAddress1(address.getAddress1());
					audit_address.setAddress2(address.getAddress2());
					audit_address.setAddresstype(address.getAddresstype());
					audit_address.setAltphone(address.getAltphone());
					audit_address.setZip(address.getZip());
					audit_address.setHomephone(address.getHomephone());
					audit_address.setWorkphone(address.getWorkphone());
					audit_address.setMobile(address.getMobile());
					audit_address.setPager(address.getPager());
					audit_address.setCity(address.getCity());
					audit_address.setEmail(address.getEmail());
					audit_address.setState_ID(address.getState_ID());
					audit_address.setCountrycode_ID(address.getCountrycode_ID());
					audit_address.setProvince(address.getProvince());
					audit_address.setAudit_ohd_passenger(aud_pass);
					audit_addresses.add(audit_address);
					aud_pass.setAddresses(audit_addresses);
				}
				audit_passenger.add(aud_pass);
			}
			audit_ohd.setPassengers(audit_passenger);
		} else {
			audit_ohd.setPassengers(null);
		}

		return audit_ohd;
	}

	public static boolean holdingStationHasChanged(List audit_ohd_list) {
		boolean ret = false;

		int old_value = -1;
		for (int i = 0; i < audit_ohd_list.size(); i++) {
			Audit_OHD aohd = (Audit_OHD) audit_ohd_list.get(i);
			int new_value = aohd.getHoldingStation().getStation_ID();
			if (i == 0) {
				old_value = new_value;
			} else {
				if (old_value != new_value) {
					return true;
				}
			}
		}
		return ret;
	}

	public static boolean closeDateHasChanged(List audit_ohd_list) {
		boolean ret = false;

		Date old_value = null;
		for (int i = 0; i < audit_ohd_list.size(); i++) {
			Audit_OHD aohd = (Audit_OHD) audit_ohd_list.get(i);
			Date new_value = aohd.getClose_date();
			if (i == 0) {
				old_value = new_value;
			} else {
				if (notEqualObjects(old_value, new_value)) {
					return true;
				}
			}
		}
		return ret;
	}

	public static boolean statusHasChanged(List audit_ohd_list) {
		boolean ret = false;

		int old_value = -1;
		for (int i = 0; i < audit_ohd_list.size(); i++) {
			Audit_OHD aohd = (Audit_OHD) audit_ohd_list.get(i);
			int new_value = aohd.getStatus().getStatus_ID();
			if (i == 0) {
				old_value = new_value;
			} else {
				if (old_value != new_value) {
					return true;
				}
			}
		}
		return ret;
	}

	public static boolean nameOnBagHasChanged(List audit_ohd_list) {
		boolean ret = false;

		String old_firstname = null;
		String old_lastname = null;
		String old_miname = null;
		for (int i = 0; i < audit_ohd_list.size(); i++) {
			Audit_OHD aohd = (Audit_OHD) audit_ohd_list.get(i);
			String new_firstname = aohd.getFirstname();
			String new_lastname = aohd.getLastname();
			String new_miname = aohd.getMiddlename();

			Date new_value = aohd.getClose_date();
			if (i == 0) {
				old_firstname = new_firstname;
				old_lastname = new_lastname;
				old_miname = new_miname;

			} else {
				if ((notEqualObjects(old_firstname, new_firstname))
						|| (notEqualObjects(old_lastname, new_lastname))
						|| (notEqualObjects(old_miname, new_miname))) {
					return true;
				}
			}
		}
		return ret;
	}

	public static boolean claimNumHasChanged(List audit_ohd_list) {
		boolean ret = false;

		String old_value = null;
		for (int i = 0; i < audit_ohd_list.size(); i++) {
			Audit_OHD aohd = (Audit_OHD) audit_ohd_list.get(i);
			String new_value = aohd.getClaimnum();
			if (i == 0) {
				old_value = new_value;
			} else {
				if (notEqualObjects(old_value, new_value)) {
					return true;
				}
			}
		}
		return ret;
	}

	public static boolean recordLocatorHasChanged(List audit_ohd_list) {
		boolean ret = false;

		String old_value = null;
		for (int i = 0; i < audit_ohd_list.size(); i++) {
			Audit_OHD aohd = (Audit_OHD) audit_ohd_list.get(i);
			String new_value = aohd.getRecord_locator();
			if (i == 0) {
				old_value = new_value;
			} else {
				if (notEqualObjects(old_value, new_value)) {
					return true;
				}
			}
		}
		return ret;
	}

	public static boolean storageLocationHasChanged(List audit_ohd_list) {
		boolean ret = false;

		String old_value = null;
		for (int i = 0; i < audit_ohd_list.size(); i++) {
			Audit_OHD aohd = (Audit_OHD) audit_ohd_list.get(i);
			String new_value = aohd.getStorage_location();
			if (i == 0) {
				old_value = new_value;
			} else {
				if (notEqualObjects(old_value, new_value)) {
					return true;
				}
			}
		}
		return ret;
	}

	public static boolean colorHasChanged(List audit_ohd_list) {
		boolean ret = false;

		String old_value = null;
		for (int i = 0; i < audit_ohd_list.size(); i++) {
			Audit_OHD aohd = (Audit_OHD) audit_ohd_list.get(i);
			String new_value = aohd.getColor();
			if (i == 0) {
				old_value = new_value;
			} else {
				if (notEqualObjects(old_value, new_value)) {
					return true;
				}
			}
		}
		return ret;
	}

	public static boolean typeHasChanged(List audit_ohd_list) {
		boolean ret = false;

		String old_value = null;
		for (int i = 0; i < audit_ohd_list.size(); i++) {
			Audit_OHD aohd = (Audit_OHD) audit_ohd_list.get(i);
			String new_value = aohd.getType();
			if (i == 0) {
				old_value = new_value;
			} else {
				if (notEqualObjects(old_value, new_value)) {
					return true;
				}
			}
		}
		return ret;
	}

	public static boolean manufacturerHasChanged(List audit_ohd_list) {
		boolean ret = false;

		String old_value = null;
		for (int i = 0; i < audit_ohd_list.size(); i++) {
			Audit_OHD aohd = (Audit_OHD) audit_ohd_list.get(i);
			String new_value = aohd.getManufacturer();
			if (i == 0) {
				old_value = new_value;
			} else {
				if (notEqualObjects(old_value, new_value)) {
					return true;
				}
			}
		}
		return ret;
	}

	public static boolean passengerInfoHasChanged(List audit_ohd_list) {
		boolean ret = false;

		List old_value = null;
		for (int i = 0; i < audit_ohd_list.size(); i++) {
			Audit_OHD aohd = (Audit_OHD) audit_ohd_list.get(i);
			List new_value = aohd.getPassengerList();
			if (i == 0) {
				old_value = new_value;
			} else {
				if (old_value != new_value) {
					if (old_value == null || new_value == null) return true;
					else if (old_value.size() != new_value.size()) return true;
					else {
						//Size of the sets are equal; compare each item.
						for (int j = 0; j < old_value.size(); j++) {
							Audit_OHD_Passenger pass1 = (Audit_OHD_Passenger) old_value.get(j);
							Audit_OHD_Passenger pass2 = (Audit_OHD_Passenger) new_value.get(j);

							if (notEqualObjects(pass1, pass2)) {
								return true;
							}
						}
					}
				}
			}
		}
		return ret;
	}

	public static boolean bagItineraryHasChanged(List audit_ohd_list) {
		boolean ret = false;

		List old_value = null;
		for (int i = 0; i < audit_ohd_list.size(); i++) {
			Audit_OHD aohd = (Audit_OHD) audit_ohd_list.get(i);
			List new_value = aohd.getItineraryList();
			if (i == 0) {
				old_value = new_value;
			} else {
				if (old_value != new_value) {
					if (old_value == null || new_value == null) return true;
					else if (old_value.size() != new_value.size()) return true;
					else {
						//Size of the sets are equal; compare each item.
						for (int j = 0; j < old_value.size(); j++) {
							Audit_OHD_Itinerary itin1 = (Audit_OHD_Itinerary) old_value.get(j);
							Audit_OHD_Itinerary itin2 = (Audit_OHD_Itinerary) new_value.get(j);

							if (notEqualObjects(itin1, itin2)) {
								return true;
							}
						}
					}
				}
			}
		}
		return ret;
	}

	public static boolean bagInventoryHasChanged(List audit_ohd_list) {
		boolean ret = false;

		List old_value = null;
		for (int i = 0; i < audit_ohd_list.size(); i++) {
			Audit_OHD aohd = (Audit_OHD) audit_ohd_list.get(i);
			List new_value = aohd.getItemList();
			if (i == 0) {
				old_value = new_value;
			} else {
				if (old_value != new_value) {
					if (old_value == null || new_value == null) return true;
					else if (old_value.size() != new_value.size()) return true;
					else {
						//Size of the sets are equal; compare each item.
						for (int j = 0; j < old_value.size(); j++) {
							Audit_OHD_Inventory itin1 = (Audit_OHD_Inventory) old_value.get(j);
							Audit_OHD_Inventory itin2 = (Audit_OHD_Inventory) new_value.get(j);

							if (notEqualObjects(itin1, itin2)) {
								return true;
							}
						}
					}
				}
			}
		}
		return ret;
	}

	public static boolean photosHasChanged(List audit_ohd_list) {
		boolean ret = false;

		List old_value = null;
		for (int i = 0; i < audit_ohd_list.size(); i++) {
			Audit_OHD aohd = (Audit_OHD) audit_ohd_list.get(i);
			List new_value = aohd.getPhotoList();
			if (i == 0) {
				old_value = new_value;
			} else {
				if (old_value != new_value) {
					if (old_value == null || new_value == null) return true;
					else if (old_value.size() != new_value.size()) return true;
					else {
						//Size of the sets are equal; compare each item.
						for (int j = 0; j < old_value.size(); j++) {
							Audit_OHD_Photo photo1 = (Audit_OHD_Photo) old_value.get(j);
							Audit_OHD_Photo photo2 = (Audit_OHD_Photo) new_value.get(j);

							if (notEqualObjects(photo1, photo2)) {
								return true;
							}
						}
					}
				}
			}
		}
		return ret;
	}

	public static boolean remarksHasChanged(List audit_ohd_list) {
		boolean ret = false;

		List old_value = null;
		for (int i = 0; i < audit_ohd_list.size(); i++) {
			Audit_OHD aohd = (Audit_OHD) audit_ohd_list.get(i);
			List new_value = aohd.getRemarkList();
			if (i == 0) {
				old_value = new_value;
			} else {
				if (old_value != new_value) {
					if (old_value == null || new_value == null) return true;
					else if (old_value.size() != new_value.size()) return true;
					else {
						//Size of the sets are equal; compare each item.
						for (int j = 0; j < old_value.size(); j++) {
							Audit_Remark rem1 = (Audit_Remark) old_value.get(j);
							Audit_Remark rem2 = (Audit_Remark) new_value.get(j);

							if (notEqualObjects(rem1, rem2)) {
								return true;
							}
						}
					}
				}
			}
		}
		return ret;
	}

	public static boolean externalDescHasChanged(List audit_ohd_list) {
		boolean ret = false;

		String x1_old_value = null;
		String x2_old_value = null;
		String x3_old_value = null;
		for (int i = 0; i < audit_ohd_list.size(); i++) {
			Audit_OHD aohd = (Audit_OHD) audit_ohd_list.get(i);
			String new_value_1 = aohd.getDesc1();
			String new_value_2 = aohd.getDesc2();
			String new_value_3 = aohd.getDesc3();
			if (i == 0) {
				x1_old_value = new_value_1;
				x2_old_value = new_value_2;
				x3_old_value = new_value_3;
			} else {
				if (notEqualObjects(x1_old_value, new_value_1)
						|| notEqualObjects(x2_old_value, new_value_2)
						|| notEqualObjects(x3_old_value, new_value_3)) {
					return true;
				}
			}
		}
		return ret;
	}

	public static boolean airlineMembershipHasChanged(List audit_ohd_list) {
		boolean ret = false;

		Audit_AirlineMembership old_value = null;
		for (int i = 0; i < audit_ohd_list.size(); i++) {
			Audit_OHD aohd = (Audit_OHD) audit_ohd_list.get(i);
			Audit_AirlineMembership new_value = aohd.getMembership();
			if (i == 0) {
				old_value = new_value;
			} else {
				if (notEqualObjects(old_value, new_value)) {
					return true;
				}
			}
		}
		return ret;
	}

	public static boolean notEqualObjects(Object val1, Object val2) {
		boolean ret = false;

		if (val1 != val2) {
			if (val1 == null || val2 == null || !val1.equals(val2)) {
				ret = true;
			}
		}
		return ret;
	}

}