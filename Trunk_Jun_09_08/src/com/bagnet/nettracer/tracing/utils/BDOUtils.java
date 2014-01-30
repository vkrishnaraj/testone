/*
 * Created on Oct 28, 2004
 *
 * Administrator
 */
package com.bagnet.nettracer.tracing.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.struts.util.MessageResources;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.bmo.LossCodeBMO;
import com.bagnet.nettracer.tracing.bmo.OhdBMO;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.bmo.StatusBMO;
import com.bagnet.nettracer.tracing.bmo.exception.StaleStateException;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Address;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.BDO;
import com.bagnet.nettracer.tracing.db.BDO_Passenger;
import com.bagnet.nettracer.tracing.db.Category;
import com.bagnet.nettracer.tracing.db.Company_specific_irregularity_code;
import com.bagnet.nettracer.tracing.db.DeliverCo_Station;
import com.bagnet.nettracer.tracing.db.DeliverCompany;
import com.bagnet.nettracer.tracing.db.Deliver_ServiceLevel;
import com.bagnet.nettracer.tracing.db.DeliveryIntegrationType;
import com.bagnet.nettracer.tracing.db.ExpensePayout;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Item;
import com.bagnet.nettracer.tracing.db.Item_BDO;
import com.bagnet.nettracer.tracing.db.Itinerary;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.OHD_Address;
import com.bagnet.nettracer.tracing.db.OHD_Passenger;
import com.bagnet.nettracer.tracing.db.Passenger;
import com.bagnet.nettracer.tracing.db.Remark;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.wtq.WtqCreateBdo;
import com.bagnet.nettracer.tracing.forms.BDOForm;
import com.bagnet.nettracer.tracing.forms.SearchBDOForm;
import com.bagnet.nettracer.wt.WorldTracerQueueUtils;

/**
 * @author Administrator
 * 
 *         bdo utils handling all BDO actions.
 * 
 *         create date - Oct 28, 2004
 */
public class BDOUtils {

	private static Logger logger = Logger.getLogger(BDOUtils.class);

	@SuppressWarnings("rawtypes")
	public static boolean createNewBDO(String ohd_ID, String incident_ID, BDOForm theform, HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();

		Agent user = (Agent) session.getAttribute("user");

		IncidentBMO iBMO = new IncidentBMO();
		OhdBMO oBMO = new OhdBMO();
		@SuppressWarnings("unused")
		BDO bdo = null;

		theform = new BDOForm();
		session.setAttribute("BDOForm", theform);

		request.setAttribute("servicelevels", new ArrayList());

		/** *** create bdo for the first time ***** */

		theform.setCreatedate(TracerDateTime.getGMTDate());
		theform.setCreatetime(TracerDateTime.getGMTDate());
		theform.set_DATEFORMAT(user.getDateformat().getFormat());
		theform.set_TIMEFORMAT(user.getTimeformat().getFormat());
		theform.setCurrency(user.getDefaultcurrency());
		if (user.getDefaulttimezone() != null)
			theform.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone()));

		BDO_Passenger bp = null;
		if (ohd_ID != null) {
			// create bdo from ohd
			OHD oDTO = oBMO.findOHDByID(ohd_ID);
			if (oDTO == null)
				return false;

			if (user.getStation().getStation_ID() != oDTO.getHoldingStation().getStation_ID()) {
				return false;
			}

			theform.setOhd(oDTO);
			theform.setAgent(user);
			theform.setCompanycode_ID(oDTO.getHoldingStation().getCompany().getCompanyCode_ID());
			theform.setStation(oDTO.getHoldingStation());

			// loop through ohd passenger and put all info into passengerlist
			OHD_Passenger op = null;
			OHD_Address oa = null;

			for (int i = 0; i < oDTO.getPassengers().size(); i++) {
				op = (OHD_Passenger) oDTO.getPassengers().iterator().next();
				bp = new BDO_Passenger();
				bp.set_DATEFORMAT(user.getDateformat().getFormat());
				BeanUtils.copyProperties(bp, op);
				oa = (OHD_Address) op.getAddress(0);
				BeanUtils.copyProperties(bp, oa);
				theform.getPassengerlist().add(bp);
			}
			// if no passenger then create
			if (oDTO.getPassengers() == null || oDTO.getPassengers().size() == 0) {
				bp = new BDO_Passenger();
				bp.set_DATEFORMAT(user.getDateformat().getFormat());
				theform.getPassengerlist().add(bp);
			}

			// if no passenger then create
			if (oDTO.getPassengers() == null || oDTO.getPassengers().size() == 0) {
				bp = new BDO_Passenger();
				bp.set_DATEFORMAT(user.getDateformat().getFormat());
				theform.getPassengerlist().add(bp);
			}

		} else if (incident_ID != null) {
			// create bdo from incident
			Incident iDTO = iBMO.findIncidentByID(incident_ID);
			if (iDTO == null)
				return false;
			if((iDTO.getItemtype_ID() == TracingConstants.MISSING_ARTICLES || iDTO.getItemtype_ID() == TracingConstants.DAMAGED_BAG)){
				OHD o=OhdBMO.getOHDByMatchIncidentID(iDTO.getIncident_ID(), null);
				if(o!=null){
					return false;
				}
			}
			theform.setIncident(iDTO);
			theform.setAgent(user);
			theform.setCompanycode_ID(iDTO.getStationassigned().getCompany().getCompanyCode_ID());

			if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_BDOS_FOR_STATIONS, user)) {
				theform.setStation(user.getStation());
			} else {
				theform.setStation(iDTO.getStationassigned());
			}

			// loop through passenger and put all info into passengerlist
			Passenger p = null;
			Address a = null;

			for (int i = 0; i < iDTO.getPassenger_list().size(); i++) {
				p = (Passenger) iDTO.getPassenger_list().get(i);
				bp = new BDO_Passenger();
				bp.set_DATEFORMAT(user.getDateformat().getFormat());
				BeanUtils.copyProperties(bp, p);
				a = (Address) p.getAddress(0);
				a.set_DATEFORMAT(user.getDateformat().getFormat());
				BeanUtils.copyProperties(bp, a);
				theform.getPassengerlist().add(bp);
			}
			// if no passenger then create
			if (iDTO.getPassenger_list() == null || iDTO.getPassenger_list().size() == 0) {
				bp = new BDO_Passenger();
				bp.set_DATEFORMAT(user.getDateformat().getFormat());
				theform.getPassengerlist().add(bp);
			}

			// choose only bags that are not delivered
			Item item = null;
			OHD ohd_obj = null;
			for (int i = 0; i < iDTO.getItemlist().size(); i++) {
				item = (Item) iDTO.getItemlist().get(i);
				if (item != null && item.getStatus().getStatus_ID() != TracingConstants.ITEM_STATUS_PROCESSFORDELIVERY) {
					theform.getItemlist().add(item);
					// find out if this l/d bag is matched to ohd
					ohd_obj = oBMO.findOHDByID(item.getOHD_ID());
					if (ohd_obj != null && ohd_obj.getHoldingStation().getStation_ID() == iDTO.getStationassigned().getStation_ID()) {
						// bag matched and in station
						item.setIs_in_station(1);
					} else {
						// bag not in station
						item.setIs_in_station(0);
					}
				}
			}

			theform.setChoosebags(1);

			// don't need to choose bag if there is only one bag
			if (theform.getItemlist().size() <= 1) {
				theform.setChoosebags(0);
			}
			
			// get special directions from incident
			if(iDTO.getDeliveryInstructions()!=null){
				theform.setDelivery_comments(iDTO.getDeliveryInstructions().getInstructions());
			}

		}
		List<DeliverCo_Station> list = new ArrayList<DeliverCo_Station>(BDOUtils.getDeliveryCompanies(theform.getStation().getStation_ID()));
		if (list != null)
			request.setAttribute("delivercompanies", list);

		return true;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static boolean findBDO(int bdo_ID, BDOForm theform, HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		Agent user = (Agent) session.getAttribute("user");

		BDO bdo = getBDOFromDB(bdo_ID);

		if (bdo != null) {
			// bdo found, populate form

			bdo.set_DATEFORMAT(user.getDateformat().getFormat());
			bdo.set_TIMEFORMAT(user.getTimeformat().getFormat());

			if (user.getDefaulttimezone() != null)
				bdo.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone()));

			theform = new BDOForm();
			session.setAttribute("BDOForm", theform);

			theform.setChoosebags(0);
			BeanUtils.copyProperties(theform, bdo);

			theform.setPassengerlist(new ArrayList(bdo.getPassengers()));
			theform.setItemlist(new ArrayList(bdo.getItems()));
			
			populateFormWithExistingData(bdo.getIncident(),theform);

			if (bdo.getExpensePayout() != null) {

				theform.setCost(TracingConstants.DECIMALFORMAT.format(bdo.getExpensePayout().getCheckamt()));
				theform.setCurrency(bdo.getExpensePayout().getCurrency_ID());
			}

			// loop through and check to see if bag is in station or not
			Item item = null;
			OHD ohd_obj = null;
			OhdBMO oBMO = new OhdBMO();
			for (int i = 0; i < theform.getItemlist().size(); i++) {
				item = (Item) theform.getItemlist().get(i);
				// find out if this l/d bag is matched to ohd
				ohd_obj = oBMO.findOHDByID(item.getOHD_ID());
				if (ohd_obj != null && ohd_obj.getHoldingStation().getStation_ID() == theform.getIncident().getStationassigned().getStation_ID()) {
					// bag matched and in station
					item.setIs_in_station(1);
				} else {
					// bag not in station
					item.setIs_in_station(0);
				}
			}

			BDO_Passenger bp = null;
			for (int i = 0; i < theform.getPassengerlist().size(); i++) {
				bp = (BDO_Passenger) theform.getPassenger(i);
				bp.set_DATEFORMAT(user.getDateformat().getFormat());
			}

			if (bdo.getDelivercompany() != null) {
				theform.setDelivercompany_ID(bdo.getDelivercompany().getDelivercompany_ID());
			}
			if (bdo.getServicelevel() != null) {
				theform.setServicelevel_ID(bdo.getServicelevel().getServicelevel_ID());
			}

			if (theform.getOhd() == null)
				theform.setOhd(new OHD());
			if (theform.getIncident() == null)
				theform.setIncident(new Incident());

			List<DeliverCo_Station> list = new ArrayList<DeliverCo_Station>(getDeliveryCompanies(theform.getStation().getStation_ID(), bdo.getDelivercompany()));

			if (list != null)
				request.setAttribute("delivercompanies", list);

			if (theform.getDelivercompany_ID() > 0) {
				List<Deliver_ServiceLevel> servicelevels = BDOUtils.getServiceLevels(theform.getDelivercompany_ID(), bdo.getServicelevel());
				request.setAttribute("servicelevels", servicelevels);
			}

			return true;
		} else {
			return false;
		}

	}

	/**
	 * Method to fill the form with existing data from the database for the purposes of determining if remarks are required
	 * 
	 * @param incident_id - id to look up incident to get the item list from
	 * @param theform - form to populate
	 */
	public static void populateFormWithExistingData(String incident_ID,
			BDOForm theform) {
		Incident inc=IncidentBMO.getIncidentByID(incident_ID, null);
		populateFormWithExistingData(inc,theform);
		
	}
	
	/**
	 * Method to fill the form with existing data from the database for the purposes of determining if remarks are required
	 * 
	 * @param incident_id - id to look up incident to get the item list from
	 * @param theform - form to populate
	 */
	public static void populateFormWithExistingData(Incident inc,
			BDOForm theform) {
		if(inc!=null){
			if(inc.getItemlist()!=null){
				theform.setExistItemList(new ArrayList<Item>(inc.getItemlist()));
			} else {
				theform.setExistItemList(new ArrayList<Item>());
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static boolean findBDOList(String ohd_ID, String incident_ID, BDOForm theform, HttpServletRequest request) throws Exception {

		HttpSession session = request.getSession();
		Agent user = (Agent) session.getAttribute("user");

		IncidentBMO iBMO = new IncidentBMO();
		OhdBMO oBMO = new OhdBMO();
		OHD ohd = null;
		Incident incident = null;
		List<BDO> list = null;
		if (ohd_ID != null) {
			// verifty ohd
			ohd = oBMO.findOHDByID(ohd_ID);
			if (ohd == null)
				return false;

			// first try to see if bdo has been created already
			list = oBMO.findBDOList(ohd_ID);
		} else {
			// verify incident
			incident = iBMO.findIncidentByID(incident_ID);
			if (incident == null)
				return false;

			// first try to see if bdo has been created already
			list = iBMO.findBDOList(incident_ID);
		}

		theform = new BDOForm();
		request.setAttribute("BDOForm", theform);
		theform.setOhd(ohd != null ? ohd : new OHD());
		theform.setIncident(incident != null ? incident : new Incident());

		if (list != null) {
			BDO bdo = null;
			for (int i = 0; i < list.size(); i++) {
				bdo = (BDO) list.get(i);
				bdo.set_DATEFORMAT(user.getDateformat().getFormat());
				bdo.set_TIMEFORMAT(user.getTimeformat().getFormat());
				bdo.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone()));
			}
			request.setAttribute("bdo_list", list);

		}
		return true;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static BDO createBdo(BDOForm theform, String[] bagchosen) throws Exception {
		BDO bdo = new BDO();

		try {
			OhdBMO oBMO = new OhdBMO();

			BeanUtils.copyProperties(bdo, theform);

			if (theform.getPassengerlist() != null) {
				for (int i = 0; i < theform.getPassengerlist().size(); i++) {
					BDO_Passenger bp = (BDO_Passenger) theform.getPassenger(i);
					bp.setBdo(bdo);
				}
				bdo.setPassengers(new LinkedHashSet(theform.getPassengerlist()));
			}
			if(theform.getPickupdate()!=null && theform.getPickuptime()!=null){
				// do the  GMT conversion for pickupdate and pickuptime
				String strDate = DateUtils.formatDate(theform.getPickupdate(), TracingConstants.DB_DATEFORMAT, null, null);
				String strTime = DateUtils.formatDate(theform.getPickuptime(), TracingConstants.DB_TIMEFORMAT, null, null);
				Date tempdate = DateUtils.convertToGMTDate(strDate + " " + strTime,TracingConstants.DB_DATETIMEFORMAT2, null);
				
				bdo.setPickuptime(DateUtils.convertToDate(DateUtils.formatDate(tempdate, TracingConstants.DB_TIMEFORMAT, null, null), TracingConstants.DB_TIMEFORMAT, null));
				bdo.setPickupdate(DateUtils.convertToDate(DateUtils.formatDate(tempdate, TracingConstants.DISPLAY_DATETIMEFORMAT, null, null), TracingConstants.DISPLAY_DATETIMEFORMAT, null));
			} else {
				bdo.setPickuptime(null);
				bdo.setPickupdate(null);
				
			}
			if (theform.getDelivercompany_ID() > 0) {
				DeliverCompany dc = new DeliverCompany();
				dc.setDelivercompany_ID(theform.getDelivercompany_ID());
				bdo.setDelivercompany(dc);
			}

			if (theform.getServicelevel_ID() > 0) {
				Deliver_ServiceLevel dsl = new Deliver_ServiceLevel();
				dsl.setServicelevel_ID(theform.getServicelevel_ID());
				bdo.setServicelevel(dsl);
			}
			
			if (theform.getIncident().getIncident_ID() != null) {
				// prevent hibernate from erroring out
				bdo.setOhd(null);

				// if it is lost delayed, change status to to be delivered
				Item item = null;
				@SuppressWarnings("unused")
				int bagnumber;
				OHD ohd_obj = null;

				for (int i = theform.getItemlist().size() - 1; i >= 0; i--) {

					item = (Item) theform.getItemlist().get(i);
					ArrayList<String> bagsChosen = new ArrayList<String>();

					if (bagchosen != null)
						for (String bag : bagchosen) {
							bagsChosen.add(bag);
						}

					if (bagchosen == null || (bagchosen != null && bagsChosen.contains(Integer.toString(item.getBagnumber())))) {
						item.setStatus(StatusBMO.getStatus(TracingConstants.ITEM_STATUS_PROCESSFORDELIVERY));
						// item.setBdo(bdo);

						manageItemBdoList(bdo, item);

						// find out if this l/d bag is matched to ohd
						ohd_obj = oBMO.findOHDByID(item.getOHD_ID());
						if (ohd_obj != null && ohd_obj.getHoldingStation().getStation_ID() == theform.getIncident().getStationassigned().getStation_ID()) {
							bdo.setOhd(ohd_obj);
						}

					} else {
						theform.getItemlist().remove(i);
					}
				}

				//Remarks
				Remark re = null;
				if(bdo.getIncident()!=null && theform.getRemark() != null) {
					re = new Remark();
					re.setAgent(theform.getAgent());
					re.setRemarktype(TracingConstants.REMARK_REGULAR);
					re.setIncident(bdo.getIncident());
					re.setRemarktext(theform.getRemark());
					re.setCreatetime(new SimpleDateFormat(TracingConstants.DB_DATETIMEFORMAT).format(TracerDateTime.getGMTDate()));
					re.setSecure(theform.isSecure());
					// If agent_ID = 0, agent is transient. To prevent exception
					// from being thrown, we get the non-transient version.
					// Was unable to determine what causes object to become
					// transient.
					if(re.getAgent() != null && re.getAgent().getAgent_ID() == 0) {
						try {
							Agent tmpAgent = TracerUtils.getAgent(re.getAgent().getUsername(), re.getAgent()
									.getCompanycode_ID());
							re.setAgent(tmpAgent);
						}
						catch (Exception e) {
							logger.error("Could not correct transient agent: " + e.getMessage());
							e.printStackTrace();
						}
					}
					bdo.getIncident().getRemarks().add(re);
				}
			} else if (theform.getOhd().getOHD_ID() != null) {
				// first check to see if this ohd is matched to a l/d item
				Item item = findOHDfromMatchedLD(theform.getOhd().getOHD_ID());
				if (item != null) {
					// matched so insert incident id into bdo
					bdo.setIncident(item.getIncident());
					item.setStatus(StatusBMO.getStatus(TracingConstants.ITEM_STATUS_PROCESSFORDELIVERY));

					manageItemBdoList(bdo, item);

					theform.getItemlist().add(item);
					// bdo.setItems(new HashSet(theform.getItemlist()));
				} else {
					bdo.setIncident(null);
				}

			}
		} catch (Exception e) {
			logger.error("Error creating BDO object...");
			e.printStackTrace();
			throw e;
		}
		return bdo;
	}

	private static void manageItemBdoList(BDO bdo, Item item) {
		Set<Item_BDO> ibdo_list = item.getItem_bdo();
		Item_BDO itemBdo = null;
		if (ibdo_list == null) {
			ibdo_list = new LinkedHashSet<Item_BDO>();
			item.setItem_bdo(ibdo_list);
		}

		Set<Item_BDO> bdo_item_list = bdo.getItem_bdo();
		if (bdo_item_list == null) {
			bdo_item_list = new LinkedHashSet<Item_BDO>();
			bdo.setItem_bdo(bdo_item_list);
		}

		itemBdo = findAndMapItemToBdo(bdo, item, ibdo_list, null);
		findAndMapItemToBdo(bdo, item, bdo_item_list, itemBdo);
	}

	private static Item_BDO findAndMapItemToBdo(BDO bdo, Item item, Set<Item_BDO> ibdo_list, Item_BDO ibdo) {
		Item_BDO tmp = null;
	
		Iterator<Item_BDO> i = ibdo_list.iterator();
		boolean isFound = false;
		while (i.hasNext()) {
			tmp = i.next();
			if (tmp.getBdo().getBDO_ID() == bdo.getBDO_ID() && tmp.getItem().getItem_ID() == item.getItem_ID()) {
				ibdo = tmp;
				isFound = true;
				break;
			}
		}
	
		if (ibdo == null) {
			ibdo = new Item_BDO();
			ibdo.setItem(item);
			ibdo.setBdo(bdo);
			ibdo_list.add(ibdo);
		} else if (isFound == false && ibdo != null) {
			ibdo_list.add(ibdo);
		}
		return ibdo;
	}

	public static boolean insertBDO(BDO bdo, Agent agent) throws Exception {
		try {

			OhdBMO oBMO = new OhdBMO();
			OHD ohd = bdo.getOhd();

			// If the BDO is matched
			if (bdo.getItems() != null && bdo.getItems().size() > 0) {
				Set<Item> items = (Set<Item>) bdo.getItems();
				for (Item item : items) {
					if (item != null) {
						if (item.getOHD_ID() != null && !item.getOHD_ID().equals("")) {
							ohd = OhdBMO.getOHDByID(item.getOHD_ID(), null);
							ohd.setStatus(StatusBMO.getStatus(TracingConstants.OHD_STATUS_PROCESSFORDELIVERY));
							oBMO.insertOHD(ohd, agent);
						}
					}
				}
			} else if (ohd != null) {
				ohd.setStatus(StatusBMO.getStatus(TracingConstants.OHD_STATUS_PROCESSFORDELIVERY));
				oBMO.insertOHD(ohd, agent);
				bdo.setOhd(ohd);
			}

			if (!insertBDOtoDB(bdo, agent))
				return false;

		} catch (Exception e) {
			logger.error("Unable to insert BDO...");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static List<Station> getDeliverCompanies(int station_ID) {
		// populate delivery service
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(Station.class);

			cri.add(Restrictions.eq("station_ID", new Integer(station_ID)));
			cri.addOrder(Order.asc("description"));

			@SuppressWarnings("unchecked")
			List<Station> list = cri.list();

			return list;

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	/** 
	 * Method to get all SERV Delivery Companies to determine the if the Request Delivery Cost button should appear
	 */
	public static List<DeliverCompany> getSERVDeliverCompanies() {
		// populate delivery service
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(DeliverCompany.class);

			cri.add(Restrictions.eq("delivery_integration_type", DeliveryIntegrationType.SERV));
			cri.addOrder(Order.asc("id"));

			@SuppressWarnings("unchecked")
			List<DeliverCompany> list = cri.list();

			return list;

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public static BDO getBDOFromDB(int bdo_ID) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(BDO.class).add(Restrictions.eq("BDO_ID", new Integer(bdo_ID)));
			@SuppressWarnings("unchecked")
			List<BDO> list = cri.list();

			if (list.size() == 0) {
				return null;
			}
			BDO bdo = (BDO) list.get(0);
			return bdo;
		} catch (Exception e) {
			logger.error("unable to retrieve bdo: " + e);
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

	public static List<Deliver_ServiceLevel> getServiceLevels(int delivercompany_ID) {
		return getServiceLevels(delivercompany_ID, null);
	}

	public static List<Deliver_ServiceLevel> getServiceLevels(int delivercompany_ID, Deliver_ServiceLevel dsl) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(DeliverCompany.class).add(Restrictions.eq("delivercompany_ID", new Integer(delivercompany_ID)));
			@SuppressWarnings("unchecked")
			List<DeliverCompany> list = cri.list();

			if (list.size() == 0) {
				logger.debug("unable to find bdo from delivercompany: " + delivercompany_ID);
				return null;
			}
			DeliverCompany dc = (DeliverCompany) list.get(0);
			@SuppressWarnings({ "unchecked" })
			ArrayList<Deliver_ServiceLevel> al = new ArrayList<Deliver_ServiceLevel>(dc.getServicelevels());
			List<Deliver_ServiceLevel> nl = new ArrayList<Deliver_ServiceLevel>();

			if (al.size() == 0 && dsl != null) {
				nl.add(dsl);
			}

			for (int x = 0; x < al.size(); ++x) {
				Deliver_ServiceLevel sl = al.get(x);
				if (sl.isActive() == true || (dsl != null && dsl.getServicelevel_ID() == sl.getServicelevel_ID())) {
					nl.add(sl);
				}
			}

			return nl;

		} catch (Exception e) {
			logger.error("unable to retrieve delivercompany: " + e);
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

	public static Deliver_ServiceLevel getServiceLevel(String serviceLevel_id) {
		// populate delivery service
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(Deliver_ServiceLevel.class);

			cri.add(Restrictions.eq("servicelevel_ID", new Integer(serviceLevel_id)));

			@SuppressWarnings("unchecked")
			List<Deliver_ServiceLevel> list = cri.list();
			if (list != null)
				return (Deliver_ServiceLevel) list.get(0);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public static DeliverCo_Station getDeliverCo_Station(String stationId, DeliverCompany deliverCo) {
		// populate delivery service
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(DeliverCo_Station.class);

			cri.add(Restrictions.eq("station_ID", new Integer(stationId)));
			cri.add(Restrictions.eq("delivercompany", deliverCo));

			@SuppressWarnings("unchecked")
			List<DeliverCo_Station> list = cri.list();
			if (list != null)
				return (DeliverCo_Station) list.get(0);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static List<Station> getStationsByDeliveryCompany(DeliverCompany delivercompany) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(DeliverCo_Station.class);
			cri.add(Restrictions.eq("delivercompany", delivercompany));
			List<DeliverCo_Station> delivercoStations = cri.list();

			List<Integer> stationIdList = new ArrayList<Integer>();

			for (int x = 0; x < delivercoStations.size(); x++) {
				stationIdList.add(new Integer(delivercoStations.get(x).getStation_ID()));
			}

			if (stationIdList.size() > 0) {
				cri = sess.createCriteria(Station.class);
				cri.add(Restrictions.in("station_ID", stationIdList));
				cri.add(Restrictions.eq("active", true));
				cri.addOrder(Order.asc("stationcode"));
			} else {
				return null;
			}

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

	public static List<DeliverCo_Station> getDeliveryCompanies(int station_ID) {
		return getDeliveryCompanies(station_ID, false);
	}

	public static List<DeliverCo_Station> getDeliveryCompanies(int station_ID, boolean dirtyRead) {
		return getDeliveryCompanies(station_ID, null, false);
	}

	public static List<DeliverCo_Station> getDeliveryCompanies(int station_ID, DeliverCompany dc) {
		return getDeliveryCompanies(station_ID, dc, false);
	}

	public static List<DeliverCo_Station> getDeliveryCompanies(int station_ID, DeliverCompany dc, boolean dirtyRead) {
		Session sess = null;
		try {

			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(DeliverCo_Station.class);
			cri.add(Restrictions.eq("station_ID", new Integer(station_ID)));
			@SuppressWarnings("unchecked")
			List<DeliverCo_Station> ol = cri.list();
			List<DeliverCo_Station> nl = new ArrayList<DeliverCo_Station>();

			for (int x = 0; x < ol.size(); ++x) {
				DeliverCo_Station tmp = ol.get(x);
				if ((tmp.getDelivercompany().isActive()) || (dc != null && dc.equals(tmp.getDelivercompany()))) {
					nl.add(tmp);
				}
			}
			return nl;
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

	public static boolean insertBDOtoDB(BDO bdo, Agent user) {
		Transaction t = null;
		Session sess = null;
		try {
			boolean isnew = false;
			if (bdo.getBDO_ID() == 0)
				isnew = true;

			if (isnew == false) {
				BDO oldBdo = BDOUtils.getBDOFromDB(bdo.getBDO_ID());
				bdo.setDelivery_integration_id(oldBdo.getDelivery_integration_id());
				bdo.setDelivery_integration_type(oldBdo.getDelivery_integration_type());
				bdo.setIntegrationDelivercompany_ID(oldBdo.getIntegrationDelivercompany_ID());

				ExpensePayout newEp = bdo.getExpensePayout();
				ExpensePayout oldEp = oldBdo.getExpensePayout();

				if (oldEp == null) {
					bdo.setExpensePayout(newEp);
				} else {
					oldEp.setCurrency(newEp.getCurrency());
					oldEp.setCheckamt(newEp.getCheckamt());
					bdo.setExpensePayout(oldEp);
				}

			}

			sess = HibernateWrapper.getSession().openSession();
			t = sess.beginTransaction();
			if (isnew) {
				Incident inc = bdo.getIncident();
				Remark rem = null;
				if (inc != null) {
					rem = new Remark();
					rem.setCreatetime(new SimpleDateFormat(TracingConstants.DB_DATETIMEFORMAT).format(TracerDateTime.getGMTDate()));
					rem.setAgent(user);
					rem.setIncident(inc);
					String temp = "";
					for (Item item : (Iterable<Item>) bdo.getItems()) {
						temp += " " + Integer.toString(item.getBagnumber() + 1);
					}
					rem.setRemarktext(TracerUtils.getText("bdo.created.bagnum", user) + temp);
					rem.setRemarktype(TracingConstants.REMARK_REGULAR);
					inc.getRemarks().add(rem);
					sess.save(rem);
				}
				OHD ohd = bdo.getOhd();
				if (ohd != null) {
					rem = new Remark();
					rem.setCreatetime(new SimpleDateFormat(TracingConstants.DB_DATETIMEFORMAT).format(TracerDateTime.getGMTDate()));
					rem.setAgent(user);
					rem.setOhd(ohd);
					rem.setRemarktext(TracerUtils.getText("bdo.created", user));
					ohd.getRemarks().add(rem);
					sess.save(rem);
				}
				try {
					SmsEmailService smsEmailService = new SmsEmailService();
					Incident myIncident = bdo.getIncident();
					if (myIncident != null) {
						smsEmailService.sendBdoMessage(myIncident);
					}				
				} catch (Exception e) {
					e.printStackTrace();
				}
			} 
			if(bdo.getIncident()!=null && bdo.getIncident().getRemarks()!=null && bdo.getIncident().getRemarks().size()>0){
				for(Remark r:bdo.getIncident().getRemarks()){
					if(r.getRemark_ID()==0)
						sess.saveOrUpdate(r);
				}
			}

			sess.saveOrUpdate(bdo);

			t.commit();

			String formateddatetime = DateUtils.formatDate(TracerDateTime.getGMTDate(), TracingConstants.DB_DATETIMEFORMAT, null, bdo.get_TIMEZONE());

			if (isnew && SpringUtils.getReservationIntegration().isWriteCommentToPnrOn() && bdo.getIncident() != null) {
				SpringUtils.getReservationIntegration().writeCommentToPNR(
						"Baggage Claim (" + bdo.getIncident().getIncident_ID() + ") has been scheduled for delivery on " + formateddatetime, bdo.getIncident().getRecordlocator());

			}

			if (isnew) {
				if ((bdo.getIncident() != null && bdo.getIncident().getWtFile() != null)
						&& UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_WORLD_TRACER_BDO, user)) {
					WtqCreateBdo wtq = new WtqCreateBdo();
					wtq.setAgent(bdo.getAgent());
					wtq.setBdo(bdo);
					try {
						WorldTracerQueueUtils.createOrReplaceQueue(wtq);
					} catch (Exception e) {
						logger.error("unable to queue Worldtracer Bdo", e);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("unable to insert bdo into database: " + e);
			try {
				t.rollback();
			} catch (Exception e1) {
			}
			return false;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.error("unable to close connection: " + e);
				}
			}
		}
		return true;
	}

	/**
	 * this method is used by search bdo action
	 * 
	 * @param theform
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static ArrayList searchBDOs(SearchBDOForm siDTO, Agent user, int rowsperpage, int currpage, boolean iscount) throws HibernateException {
		Session sess = HibernateWrapper.getSession().openSession();
		Query q = null;
		try {
			StringBuffer s = new StringBuffer(512);

			TimeZone tz = TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone());

			if (iscount)
				s.append("select count(bdo.BDO_ID) from com.bagnet.nettracer.tracing.db.BDO bdo ");
			else
				s.append("select bdo from com.bagnet.nettracer.tracing.db.BDO bdo ");

			if (siDTO.getFirstname().length() > 0 || siDTO.getMiddlename().length() > 0 || siDTO.getLastname().length() > 0)
				s.append(" join bdo.passengers passenger ");

			s.append(" where 1=1 ");

			if (siDTO.getIncident_ID().length() > 0)
				s.append(" and bdo.incident.incident_ID like :incident_ID ");
			if (siDTO.getOhd_ID().length() > 0)
				s.append(" and bdo.ohd.OHD_ID like :ohd_ID ");
			if (siDTO.getBdo_ID().length() >0)
				s.append(" and bdo.BDO_ID like :bdo_ID "); 
			if (siDTO.getAgent().length() > 0)
				s.append(" and bdo.agent.username like :agent ");

			Date sdate = null, edate = null;
			Date sdate1 = null, edate1 = null; // add one for timezone
			Date stime = null; // time to compare (04:00 if eastern, for
			// example)
			@SuppressWarnings("unused")
			String dateq = "";

			ArrayList dateal = null;
			if ((dateal = IncidentUtils.calculateDateDiff(siDTO.getS_createtime(), siDTO.getE_createtime(), tz, user)) == null) {
				return null;
			}
			sdate = (Date) dateal.get(0);
			sdate1 = (Date) dateal.get(1);
			edate = (Date) dateal.get(2);
			edate1 = (Date) dateal.get(3);
			stime = (Date) dateal.get(4);

			if (sdate != null) {
				if (edate != null && sdate != edate) {
					s.append(" and ((bdo.createdate= :startdate and bdo.createtime >= :starttime) " + " or (bdo.createdate= :enddate1 and bdo.createtime <= :starttime)"
							+ " or (bdo.createdate > :startdate and bdo.createdate <= :enddate))");

				} else {
					s.append(" and ((bdo.createdate= :startdate and bdo.createtime >= :starttime) " + " or (bdo.createdate= :startdate1 and bdo.createtime <= :starttime))");
				}
			}

			if (siDTO.getCompanycreated_ID().length() > 0)
				s.append(" and bdo.companycode_ID = :companycreated_ID");
			if (siDTO.getStationcreated_ID() > 0)
				s.append(" and bdo.station.station_ID = :stationcreated_ID");

			if (siDTO.getDelivercompany_ID() > 0)
				s.append(" and bdo.delivercompany.delivercompany_ID = :delivercompany");
			if (siDTO.getServicelevel_ID() > 0)
				s.append(" and bdo.servicelevel.servicelevel_ID = :service");

			Date deliverydate = null;
			if (siDTO.getDeliverydate().length() > 0) {
				deliverydate = DateUtils.convertToDate(siDTO.getDeliverydate(), user.getDateformat().getFormat(), null);
				if (deliverydate != null)
					s.append(" and bdo.deliverydate = :deliverydate");
			}

			if (siDTO.getFirstname().length() > 0 || siDTO.getMiddlename().length() > 0 || siDTO.getLastname().length() > 0) {
				s.append(" and passenger.firstname like :firstname");
				s.append(" and passenger.middlename like :middlename");
				s.append(" and passenger.lastname like :lastname");
			}

			if (!iscount)
				s.append(" order by bdo.BDO_ID");

			q = sess.createQuery(s.toString());

			if (rowsperpage > 0) {
				int startnum = currpage * rowsperpage;
				q.setFirstResult(startnum);
				q.setMaxResults(rowsperpage);
			}

			if (siDTO.getIncident_ID().length() > 0)
				q.setString("incident_ID", siDTO.getIncident_ID());
			if (siDTO.getOhd_ID().length() > 0)
				q.setString("ohd_ID", siDTO.getOhd_ID());
				
			if (siDTO.getBdo_ID() != null && siDTO.getBdo_ID().length() > 0) {
				String getBDO_ID = siDTO.getBdo_ID().toUpperCase();
				if (getBDO_ID.contains("%")) {
					if (getBDO_ID.indexOf("BDO") == 0) {
						getBDO_ID = getBDO_ID.replace("BDO", "");

					}
					if (getBDO_ID.indexOf("BD") == 0) {
						getBDO_ID = getBDO_ID.replace("BD", "");

					}
					if (getBDO_ID.indexOf("B") == 0) {
						getBDO_ID = getBDO_ID.replace("B", "");

					}

					q.setString("bdo_ID", getBDO_ID);
				}

				else {

					Long val = null;

					String bdoid = null;

					if (getBDO_ID != null && getBDO_ID.contains("BDO")) {
						String temp = getBDO_ID.replace("BDO", "");
						try {
							val = Long.parseLong(temp);
							bdoid = String.valueOf(val);
						} catch (NumberFormatException e) {
							bdoid = siDTO.getBdo_ID();
						}
					}
					q.setString("bdo_ID", bdoid.toUpperCase());
				}
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

			if (siDTO.getCompanycreated_ID().length() > 0)
				q.setString("companycreated_ID", siDTO.getCompanycreated_ID());
			if (siDTO.getStationcreated_ID() > 0)
				q.setInteger("stationcreated_ID", siDTO.getStationcreated_ID());

			if (siDTO.getDelivercompany_ID() > 0)
				q.setInteger("delivercompany", siDTO.getDelivercompany_ID());
			if (siDTO.getServicelevel_ID() > 0)
				q.setInteger("service", siDTO.getServicelevel_ID());

			if (deliverydate != null)
				q.setDate("deliverydate", deliverydate);

			if (siDTO.getFirstname().length() > 0 || siDTO.getMiddlename().length() > 0 || siDTO.getLastname().length() > 0) {

				String a = siDTO.getFirstname().length() == 0 ? "%" : siDTO.getFirstname();
				String b = siDTO.getMiddlename().length() == 0 ? "%" : siDTO.getMiddlename();
				String c = siDTO.getLastname().length() == 0 ? "%" : siDTO.getLastname();

				q.setString("firstname", a);
				q.setString("middlename", b);
				q.setString("lastname", c);
			}

			if (siDTO.getAgent().length() > 0)
				q.setString("agent", siDTO.getAgent());

			@SuppressWarnings({ "unchecked" })
			LinkedHashSet qlhs = new LinkedHashSet(q.list());
			@SuppressWarnings({ "unchecked" })
			ArrayList al = new ArrayList(qlhs);
			return al;
		} catch (Exception e) {
			logger.error("unable to retrieve bdo in searchBDOs: " + e);
			e.printStackTrace();
			return null;
		} finally {
			sess.close();
		}
	}

	public static DeliverCompany getDeliverCompany(int delivercompany_id) {
		// populate delivery service
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(DeliverCompany.class);

			cri.add(Restrictions.eq("delivercompany_ID", new Integer(delivercompany_id)));

			@SuppressWarnings("rawtypes")
			List list = cri.list();
			if (list != null)
				return (DeliverCompany) list.get(0);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	/**
	 * this method will return an incident item that has this ohd_ID matched to
	 * combine bdos
	 * 
	 * @param ohd_id
	 */
	@Deprecated
	public static Item findOHDfromMatchedLD(String ohd_id) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(Item.class);
			cri.add(Restrictions.eq("OHD_ID", ohd_id));
			@SuppressWarnings("rawtypes")
			List result = cri.list();
			if (result != null && result.size() > 0)
				return (Item) result.get(0);

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

	/**
	 * this method will return an Iterator that has BDO object
	 * 
	 * @param bdo_id
	 */
	@SuppressWarnings("rawtypes")
	public static Iterator findWt_id(int bdo_id) {

		SessionFactory sessionFactory = HibernateWrapper.getSession();

		Session hibernateSession = sessionFactory.openSession();
		
		try {

			Query query = hibernateSession.createQuery("from BDO as b where b.BDO_ID=?");
	
			query.setInteger(0, bdo_id);
	
			List list2 = query.list();
	
			Iterator it = list2.iterator();
	
			return it;
		
		} finally {
			hibernateSession.close();
		}
	}

	public static void cancelBdo(int bdo_id, int item_id, Agent a) {
		MessageResources messages = MessageResources.getMessageResources("com.bagnet.nettracer.tracing.resources.ApplicationResources");

		Session sess = null;
		BDO bdo = null;
		Incident inc = null;
		OHD ohd = null;
		boolean fullCancel = true;
		OhdBMO oBMO = new OhdBMO();
		Item item = null;

		try {
			sess = HibernateWrapper.getSession().openSession();
			bdo = (BDO) sess.load(BDO.class, bdo_id);

			if (item_id > 0) {

				for (Item i : (Set<Item>) bdo.getItems()) {
					if (i != null) {
						if (i.getItem_ID() == item_id) {
							item = i;
						} else if (!i.getBdo().isCanceled()) {
							fullCancel = false;
						}
					}
				}
			}

			if (fullCancel) {
				//bdo.setDelivery_comments(bdo.getDelivery_comments() + "\n\n***BDO Canceled by user: " + a.getUsername() + " at " + TracerDateTime.getGMTDate());
				bdo.setDelivery_comments(bdo.getDelivery_comments() 
						+ "\n\n***BDO Canceled by user: " 
						+ a.getUsername() 
						+ " on " 
						+ TracerDateTime.getGMTDateDayString()
						+ " at "
						+ TracerDateTime.getGMTDateTimeString());
				bdo.setCanceled(true);

				sess.save(bdo);
				inc = bdo.getIncident();

				if (inc != null) {
					for (int i = 0; i < inc.getItemlist().size(); i++) {
						item = (Item) inc.getItemlist().get(i);
						if (item != null && item.getStatus().getStatus_ID() == TracingConstants.ITEM_STATUS_PROCESSFORDELIVERY) {
							if (item.getOHD_ID() != null && item.getOHD_ID().length() > 0) {
								ohd = oBMO.findOHDByID(item.getOHD_ID(), sess);

								if (inc.getStationassigned().getStation_ID() == ohd.getHoldingStation().getStation_ID()) {
									// REVERT ITEM TO EITHER TO BE DELIVERED (IF
									// MATCHED & IN SAME STATION)
									Status iStatus = new Status();
									iStatus.setStatus_ID(TracingConstants.ITEM_STATUS_TOBEDELIVERED);
									item.setStatus(iStatus);
								} else {
									// OR MATCHED IF MATCHED AND NOT IN SAME
									// STATION
									Status iStatus = new Status();
									iStatus.setStatus_ID(TracingConstants.ITEM_STATUS_MATCHED);
									item.setStatus(iStatus);
								}

								if (ohd != null && ohd.getStatus().getStatus_ID() == TracingConstants.OHD_STATUS_PROCESSFORDELIVERY) {
									Status bStatus = new Status();
									bStatus.setStatus_ID(TracingConstants.OHD_STATUS_TO_BE_DELIVERED);
									ohd.setStatus(bStatus);
								}
								Set<Remark> ohdRemarks = ohd.getRemarks();
								Remark r = new Remark();
								r.setAgent(a);
								r.setCreatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(TracerDateTime.getGMTDate()));
								r.setRemarktext(messages.getMessage(new Locale(a.getCurrentlocale()), "remark.cancel.ohd.bdo") + " " + bdo.getBDO_ID_ref());
								r.setOhd(ohd);
								// Add a remark to the OHD saying the bag is forwarded.
								ohdRemarks.add(r);
								
								oBMO.insertOHD(ohd, a, sess);
							} else {
								// OR OPEN IF NOT MATCHED
								Status iStatus = new Status();
								iStatus.setStatus_ID(TracingConstants.ITEM_STATUS_OPEN);
								item.setStatus(iStatus);

							}
						}
					}

					Set<Remark> remarks = inc.getRemarks();
					Remark r = new Remark();
					r.setAgent(a);
					r.setCreatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(TracerDateTime.getGMTDate()));
					r.setRemarktext(messages.getMessage(new Locale(a.getCurrentlocale()), "remark.cancel.full.bdo") + " " + bdo.getBDO_ID_ref());
					r.setIncident(inc);
					// Add a remark to the OHD saying the bag is forwarded.
					remarks.add(r);
					
					// Save incident
					IncidentBMO ibmo = new IncidentBMO();
					try{
						ibmo.saveAndAuditIncident(false, inc, a, sess);
					} catch (StaleStateException sse){
						//loupas - should never reach here
					}
					
				}

				// Save on-hand
				if (bdo.getOhd() != null) {
					ohd = bdo.getOhd();
					if (ohd != null && ohd.getStatus().getStatus_ID() == TracingConstants.OHD_STATUS_PROCESSFORDELIVERY) {
						Status bStatus = new Status();
						bStatus.setStatus_ID(TracingConstants.OHD_STATUS_TO_BE_DELIVERED);
						ohd.setStatus(bStatus);
					}
					
					Set<Remark> ohdRemarks = ohd.getRemarks();
					Remark r = new Remark();
					r.setAgent(a);
					r.setCreatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(TracerDateTime.getGMTDate()));
					r.setRemarktext(messages.getMessage(new Locale(a.getCurrentlocale()), "remark.cancel.ohd.bdo") + " " + bdo.getBDO_ID_ref());
					r.setOhd(ohd);
					// Add a remark to the OHD saying the bag is forwarded.
					ohdRemarks.add(r);
					
					oBMO.insertOHD(ohd, a, sess);
				}
			} else {
				//bdo.setDelivery_comments(bdo.getDelivery_comments() + "\n\n***Item Canceled by user: " + a.getUsername() + " at " + TracerDateTime.getGMTDate());

				bdo.setDelivery_comments(bdo.getDelivery_comments() 
						+ "\n\n***BDO Canceled by user: " 
						+ a.getUsername() 
						+ " on " 
						+ TracerDateTime.getGMTDateDayString()
						+ " at "
						+ TracerDateTime.getGMTDateTimeString());
				
				sess.save(bdo);
				inc = bdo.getIncident();

				if (inc != null) {

					if (item != null && item.getStatus().getStatus_ID() == TracingConstants.ITEM_STATUS_PROCESSFORDELIVERY) {
						if (item.getOHD_ID() != null && item.getOHD_ID().length() > 0) {
							ohd = oBMO.findOHDByID(item.getOHD_ID(), sess);

							if (inc.getStationassigned().getStation_ID() == ohd.getHoldingStation().getStation_ID()) {
								// REVERT ITEM TO EITHER TO BE DELIVERED (IF
								// MATCHED & IN SAME STATION)
								Status iStatus = new Status();
								iStatus.setStatus_ID(TracingConstants.ITEM_STATUS_TOBEDELIVERED);
								item.setStatus(iStatus);
							} else {
								// OR MATCHED IF MATCHED AND NOT IN SAME STATION
								Status iStatus = new Status();
								iStatus.setStatus_ID(TracingConstants.ITEM_STATUS_MATCHED);
								item.setStatus(iStatus);
							}

							if (ohd != null && ohd.getStatus().getStatus_ID() == TracingConstants.OHD_STATUS_PROCESSFORDELIVERY) {
								Status bStatus = new Status();
								bStatus.setStatus_ID(TracingConstants.OHD_STATUS_TO_BE_DELIVERED);
								ohd.setStatus(bStatus);
							}
							
							Set<Remark> ohdRemarks = ohd.getRemarks();
							Remark r = new Remark();
							r.setAgent(a);
							r.setCreatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(TracerDateTime.getGMTDate()));
							r.setRemarktext(messages.getMessage(new Locale(a.getCurrentlocale()), "remark.cancel.ohd.bdo") + " " + bdo.getBDO_ID_ref());
							r.setOhd(ohd);
							// Add a remark to the OHD saying the bag is forwarded.
							ohdRemarks.add(r);
							
							oBMO.insertOHD(ohd, a, sess);
						} else {
							// OR OPEN IF NOT MATCHED
							Status iStatus = new Status();
							iStatus.setStatus_ID(TracingConstants.ITEM_STATUS_OPEN);
							item.setStatus(iStatus);

						}
					}
					
					Set<Remark> remarks = inc.getRemarks();
					Remark r = new Remark();
					r.setAgent(a);
					r.setCreatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(TracerDateTime.getGMTDate()));
					r.setRemarktext(messages.getMessage(new Locale(a.getCurrentlocale()), "remark.cancel.partial.bdo") + " " + bdo.getBDO_ID_ref());
					r.setIncident(inc);
					// Add a remark to the OHD saying the bag is forwarded.
					remarks.add(r);
					
					IncidentBMO ibmo = new IncidentBMO();
					try{
						ibmo.saveAndAuditIncident(false, inc, a, sess);
					} catch (StaleStateException sse){
						//loupas - should never reach here
					}
				}

				// Save on-hand
				if (bdo.getOhd() != null) {
					ohd = bdo.getOhd();
					if (ohd != null && ohd.getStatus().getStatus_ID() == TracingConstants.OHD_STATUS_PROCESSFORDELIVERY) {
						Status bStatus = new Status();
						bStatus.setStatus_ID(TracingConstants.OHD_STATUS_TO_BE_DELIVERED);
						ohd.setStatus(bStatus);
					}
					
					Set<Remark> ohdRemarks = ohd.getRemarks();
					Remark r = new Remark();
					r.setAgent(a);
					r.setCreatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(TracerDateTime.getGMTDate()));
					r.setRemarktext(messages.getMessage(new Locale(a.getCurrentlocale()), "remark.cancel.ohd.bdo") + " " + bdo.getBDO_ID_ref());
					r.setOhd(ohd);
					// Add a remark to the OHD saying the bag is forwarded.
					ohdRemarks.add(r);
					
					oBMO.insertOHD(ohd, a, sess);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		DeliveryIntegrationTypeUtils.integrate(bdo, a);
	}
	
	/**
	 * Method to get Categories that pertain to BDOs - Type "1"
	 */
	public static List<Category> getBDOCategories() {
		Session sess = null;
		try {

			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(Category.class);
			cri.add(Restrictions.eq("type", new Integer(TracingConstants.BDO_TYPE)));
			@SuppressWarnings("unchecked")
			List<Category> ol = cri.list();
			return ol;
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

	/**
	 * Find BDO By Item Count - Searching for a number of non-cancelled BDOs associated to a item. 
	 * @param item_ID - the id of the item to look for BDOs
	 * @return Number of non-cancelled BDOs associated with an Item
	 */
	public static long findBDOByItemCount(int item_ID) {
		String sql = "select count(distinct ibdo.bdo.BDO_ID) from com.bagnet.nettracer.tracing.db.Item_BDO ibdo where ibdo.item.item_ID=:itemid and ibdo.bdo.canceled=0";
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess.createQuery(sql);
			q.setParameter("itemid", item_ID);
			@SuppressWarnings("rawtypes")
			List ibdocount=  q.list();
			if(ibdocount!=null)
				return (Long)ibdocount.get(0);
		} catch (Exception e) {
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
		return -1;
	}

	/**
	 * Method to check the selected fault station against the incident passenger
	 * itinerary to confirm the selected fault station is in the passenger
	 * itinerary
	 * 
	 * @param theform - theform the collect the necessary information  to determine the fault station is in the passenger Itinerary
	 */
	public static boolean checkFaultStationForPaxItin(BDOForm theform) {
		HashMap<String,String> paxItinMap=new HashMap<String,String>();
		/**
		 * Check the properties table to see if the company wants to
		 * check against Pax Itinerary (0) or Baggage Itinerary (1) or
		 * neither (-1)
		 */
		int itinType=PropertyBMO.getValueAsInt(PropertyBMO.PROPERTY_BAG_LEVEL_LOSS_CODES_ITIN_CHECK);
		boolean inPaxItin=true;
		if(itinType!=-1){
			Item item=null;
			Map<Integer,Company_specific_irregularity_code> codeMap=new HashMap<Integer,Company_specific_irregularity_code>();
			if(theform.getIncident()!=null && theform.getIncident().getIncident_ID()!=null){

				List<Integer> lossCodeList=new ArrayList<Integer>();

				for(Object obj:theform.getItemlist()){
					item=(Item) obj;
					lossCodeList.add(item.getLossCode());
				}
				List<Company_specific_irregularity_code> codeList=LossCodeBMO.getLossCodes(lossCodeList, theform.getIncident().getItemtype_ID(), theform.getCompanycode_ID());
				
				for(Itinerary itin:theform.getIncident().getItinerary_list()){
					if(itin.getItinerarytype()==itinType){
						for(Company_specific_irregularity_code code:codeList){
							if(paxItinMap.get(itin.getLegfrom())==null
									&& ((itin.getLegfrom_type()==TracingConstants.LEG_B_STATION && code.isDepartStation())
											|| (itin.getLegfrom_type()==TracingConstants.LEG_T_STATION && code.isTransferStation())
											|| (itin.getLegfrom_type()==TracingConstants.LEG_E_STATION && code.isDestinationStation()))){
								paxItinMap.put(itin.getLegfrom()+code.getLoss_code(), "1");
							}
							if(paxItinMap.get(itin.getLegto())==null
									&& ((itin.getLegto_type()==TracingConstants.LEG_B_STATION && code.isDepartStation())
											|| (itin.getLegto_type()==TracingConstants.LEG_T_STATION && code.isTransferStation())
											|| (itin.getLegto_type()==TracingConstants.LEG_E_STATION && code.isDestinationStation()))){
								paxItinMap.put(itin.getLegto()+code.getLoss_code(), "1");
							}
							codeMap.put(code.getLoss_code(),code);
						}
					}
				}
			}
			item=null;
			for(Object obj:theform.getItemlist()){
				item=(Item) obj;
				if(item.getFaultStation()!=null && item.getFaultStation().getStationcode()!=null
						&& item.getFaultStation().getStationcode().length()>0
						&& item.getLossCode()>0){
					Company_specific_irregularity_code code=codeMap.get(item.getLossCode());
					if(paxItinMap.get(item.getFaultStation().getStationcode()+item.getLossCode())==null
							&& !(code.isAnyStation())){
						inPaxItin=false;
						break;
					}
				}
			}
		}
		return inPaxItin;
		
	}
}
