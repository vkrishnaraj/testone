/*
 * Created on Oct 28, 2004
 *
 * Administrator
 */
package com.bagnet.nettracer.tracing.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TimeZone;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.actions.templates.DocumentTemplateResult;
import com.bagnet.nettracer.tracing.adapter.TemplateAdapter;
import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.bmo.IssuanceItemBMO;
import com.bagnet.nettracer.tracing.bmo.LossCodeBMO;
import com.bagnet.nettracer.tracing.bmo.OhdBMO;
import com.bagnet.nettracer.tracing.bmo.OtherSystemInformationBMO;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.bmo.StatusBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Address;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.AirlineMembership;
import com.bagnet.nettracer.tracing.db.Articles;
import com.bagnet.nettracer.tracing.db.BDO;
import com.bagnet.nettracer.tracing.db.Category;
import com.bagnet.nettracer.tracing.db.Company;
import com.bagnet.nettracer.tracing.db.Company_Specific_Variable;
import com.bagnet.nettracer.tracing.db.Company_specific_irregularity_code;
import com.bagnet.nettracer.tracing.db.DeliveryInstructions;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Incident_Claimcheck;
import com.bagnet.nettracer.tracing.db.Item;
import com.bagnet.nettracer.tracing.db.Item_Inventory;
import com.bagnet.nettracer.tracing.db.Itinerary;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.OtherSystemInformation;
import com.bagnet.nettracer.tracing.db.Passenger;
import com.bagnet.nettracer.tracing.db.Remark;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.documents.Document;
import com.bagnet.nettracer.tracing.db.documents.templates.Template;
import com.bagnet.nettracer.tracing.db.issuance.IssuanceCategory;
import com.bagnet.nettracer.tracing.db.issuance.IssuanceItem;
import com.bagnet.nettracer.tracing.db.issuance.IssuanceItemIncident;
import com.bagnet.nettracer.tracing.db.issuance.IssuanceItemInventory;
import com.bagnet.nettracer.tracing.db.issuance.IssuanceItemQuantity;
import com.bagnet.nettracer.tracing.dto.IncidentActivityDTO;
import com.bagnet.nettracer.tracing.dto.TemplateAdapterDTO;
import com.bagnet.nettracer.tracing.factory.TemplateAdapterFactory;
import com.bagnet.nettracer.tracing.forms.IncidentForm;
import com.bagnet.nettracer.tracing.service.DocumentService;
import com.bagnet.nettracer.tracing.service.TemplateService;

/**
 * @author Administrator
 * 
 * create date - Oct 28, 2004
 */
public class MBRActionUtils {
	
	static Logger logger = Logger.getLogger(MBRActionUtils.class);
	static DocumentService documentService = (DocumentService) SpringUtils.getBean(TracingConstants.DOCUMENT_SERVICE_BEAN);
	static TemplateService templateService = (TemplateService) SpringUtils.getBean(TracingConstants.TEMPLATE_SERVICE_BEAN);	
	static ActionMessages errors = new ActionMessages();
	/**
	 * This method is responsible for processing the Add buttons on the Incident pages.
	 * 
	 * @param theform
	 * @param request
	 * @param user
	 * @param itemtype
	 * @return
	 */
	public static boolean actionAdd(IncidentForm theform, HttpServletRequest request, Agent user, int itemtype) {
		
		// Permission for editing remarks on any incident. Split by incident type.
		boolean editRemarks=false;
		if(itemtype==TracingConstants.LOST_DELAY){
			editRemarks=UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_REMARK_UPDATE_LD, user);
		} else if (itemtype==TracingConstants.MISSING_ARTICLES){
			editRemarks=UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_REMARK_UPDATE_MS, user);
		} else if (itemtype==TracingConstants.DAMAGED_BAG){
			editRemarks=UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_REMARK_UPDATE_DA, user);
		}
		
		// Permission for editing item contents. Requires the remarks permission per client request.
		boolean editContents = (editRemarks && UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_ADD_NEW_CONTENTS, user));
		
		// Permission for editing the entire incident.
		boolean editIncident = UserPermissions.hasIncidentSavePermission(user, theform.getIncident_ID());
		

		// when adding or deleting from the page,
		// email_customer checkbox needs to be set to 0 if user unchecked it
		if (request.getParameter("email_customer") == null)
			theform.setEmail_customer(0);
			
		int numberToAdd = 1;
		int fileindex = -1;
		@SuppressWarnings("unused")
		StringTokenizer stt = null;
		@SuppressWarnings("rawtypes")
		Enumeration e = request.getParameterNames();
		if (editIncident || editContents) {
			// Adding contents to items.
			while (e.hasMoreElements()) {
				String parameter = (String) e.nextElement();
				if (parameter.indexOf("addinventory") > -1) {
					String index = parameter.substring(parameter.indexOf("[")+1, java.lang.Math.min(parameter.indexOf("]"), parameter.length()));				
					numberToAdd = TracerUtils.getNumberToAdd(request, "addNumInventory[" + index + "]" );
					fileindex = Integer.parseInt(parameter.substring(parameter.indexOf("[") + 1, parameter.indexOf("]")));
					
					for (int i=0; i<numberToAdd; ++i) {
						Item_Inventory ii = new Item_Inventory();
						ii.setItem(theform.getItem(fileindex, -1));
						ii.set_DATEFORMAT(user.getDateformat().getFormat());
						ii.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone()));
						ii.setEnteredDate(DateUtils.convertToGMTDate(new Date()));
						ii.setInvItemCurrency(user.getDefaultcurrency());
						theform.getItem(fileindex, -1).getInventorylist().add(ii);
	
					}
					request.setAttribute("inventory", Integer.toString(fileindex));
					request.setAttribute("markDirty", 1);
					return true;
				}
			}
		}


		if (editIncident || editRemarks) {
			// add new remark box
			if (request.getParameter("addremark") != null) {
				//set new remark with current time
				Remark r = theform.getRemark(theform.getRemarklist().size());
				r.setCreatetime(new SimpleDateFormat(TracingConstants.DB_DATETIMEFORMAT).format(TracerDateTime.getGMTDate()));
				r.setAgent(user);
				r.set_DATEFORMAT(user.getDateformat().getFormat());
				r.set_TIMEFORMAT(user.getTimeformat().getFormat());
				r.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone()));
				request.setAttribute("remark", Integer.toString(theform.getRemarklist().size() - 1));
				request.setAttribute("markDirty", 1);
				return true;
			}
		}
		if (editIncident) {
			// add passenger
			if (request.getParameter("addPassenger") != null) {
				numberToAdd = TracerUtils.getNumberToAdd(request, "addPassengerNum");
				for (int i=0; i<numberToAdd; ++i) {
					theform.getPassenger(theform.getPassengerlist().size());
				}
				request.setAttribute("passenger", Integer.toString(theform.getPassengerlist().size() - 1));
				request.setAttribute("markDirty", 1);
				return true;
			}
	
			// add claimcheck
			if (request.getParameter("addclaimcheck") != null) {
				numberToAdd = TracerUtils.getNumberToAdd(request, "addclaimcheckNum");
				for (int i=0; i<numberToAdd; ++i) {
					theform.getClaimcheck(theform.getClaimchecklist().size());
				}
				request.setAttribute("claimcheck", "1");
				request.setAttribute("markDirty", 1);
				return true;
			}
			// add new item box
			if (request.getParameter("additem") != null) {
				numberToAdd = TracerUtils.getNumberToAdd(request, "additemNum");
				for (int i=0; i<numberToAdd; ++i) {
		
					Item item = theform.getItem(theform.getItemlist().size(), TracingConstants.LOST_DELAY);
					item.setXdescelement_ID_1(TracingConstants.XDESC_TYPE_X);
					item.setXdescelement_ID_2(TracingConstants.XDESC_TYPE_X);
					item.setXdescelement_ID_3(TracingConstants.XDESC_TYPE_X);
					item.set_DATEFORMAT(user.getDateformat().getFormat());
					item.setCurrency_ID(user.getDefaultcurrency());
					// set item status if it is not being set to open
					item.setStatus(StatusBMO.getStatus(TracingConstants.ITEM_STATUS_OPEN));
					//set default weight unit
					String myDefaultWeightUnit = PropertyBMO.getValue(PropertyBMO.PROPERTY_NT_COMPANY_WEIGHT_UNIT_DEFAULT);
					item.setBag_weight_unit(myDefaultWeightUnit);
				}
				request.setAttribute("item", Integer.toString(theform.getItemlist().size() - 1));
				request.setAttribute("markDirty", 1);
				return true;
			}
	
			if (request.getParameter("addarticles") != null) {
				numberToAdd = TracerUtils.getNumberToAdd(request, "addarticlesNum");
				for (int i=0; i<numberToAdd; ++i) {	
					Articles a = theform.getArticle(theform.getArticlelist().size());
					a.setCurrency_ID(user.getDefaultcurrency());
				}
				request.setAttribute("articles", Integer.toString(theform.getArticlelist().size() - 1));
				request.setAttribute("markDirty", 1);
				return true;
			}
			// add new passenger itinerary box
			if (request.getParameter("addpassit") != null) {		
				numberToAdd = TracerUtils.getNumberToAdd(request, "addpassitNum");
				for (int i=0; i<numberToAdd; ++i) {
					theform.getItinerary(theform.getItinerarylist().size(), TracingConstants.PASSENGER_ROUTING);
				}
				request.setAttribute("passit", "1");
				request.setAttribute("markDirty", 1);
				return true;
			}
			// add new bag itinerary box
			if (request.getParameter("addbagit") != null) {
				numberToAdd = TracerUtils.getNumberToAdd(request, "addbagitNum");
				for (int i=0; i<numberToAdd; ++i) {
					theform.getItinerary(theform.getItinerarylist().size(), TracingConstants.BAGGAGE_ROUTING);
				}
				request.setAttribute("bagit", "1");
				request.setAttribute("markDirty", 1);
				return true;
			}
			// clones passenger itinerary into bag itinerary box
			if (request.getParameter("clonepassit") != null) {
				List<Itinerary> passList = theform.getPassItineraryList();
				List<Itinerary> newList = new ArrayList<Itinerary>();
				if (passList != null) {
					for (Itinerary pItin : passList) {
						if (pItin != null) {
							newList.add(pItin);
							Itinerary bItin = createBagItinFromPassItin(pItin);
							if (bItin != null) {
								newList.add(bItin);
							}
						}
					}
				}
				if (newList.size() > 0) {
					theform.setItinerarylist(newList);
				}
				request.setAttribute("passit", "1");
				request.setAttribute("markDirty", 1);
				return true;
			}
		} // end if (editIncident)
		return false;
		
	}
	
	private static Itinerary createBagItinFromPassItin(Itinerary pItin) {
		Itinerary bItin = new Itinerary();
		try {
			BeanUtils.copyProperties(bItin, pItin);
			bItin.setItinerarytype(TracingConstants.BAGGAGE_ROUTING);
		} catch (Exception e) {
			logger.error("Passenger Itinerary Clone Failure: " + e); 
			return null;
		}
		return bItin;
	}
	
	public static boolean actionReturnItem(IncidentForm theform, HttpServletRequest request, Agent user) {
		if (request.getParameter("returnissuanceitem") != null) {
			String index = request.getParameter("returnissuanceitem");
			if (index != null && index.matches("^\\d+$")) {
				int i = Integer.parseInt(index);
				IssuanceItemIncident iItem = theform.getIssuanceItemIncidents().get(i);
				if (iItem.getId() > 0) {
					iItem.setReturned(true);
					iItem.setReturnDate(TracerDateTime.getGMTDate());
					iItem.setUpdated(true);
				} else {
					theform.getIssuanceItemIncidents().remove(i);
				}
				adjustIssuanceLists(request, null, null, iItem);
				request.setAttribute("issuanceitem", "1");
				request.setAttribute("markDirty", 1);
				return true;
			}
		}
		return false;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static boolean actionIssueItem(IncidentForm theform, HttpServletRequest request, Agent user) {
		// add new remark box
		boolean issueQ = (request.getParameter("issueItem") != null);
		boolean issueL = (request.getParameter("loanItem") != null);
		boolean issueT = (request.getParameter("tradeItem") != null);
		Incident incident = new Incident();//The incident will be coming from the IncidentForm not the database
		try {
			// copy into incident bean
			BeanUtils.copyProperties(incident, theform);
			incident.setPassengers(new LinkedHashSet(theform.getPassengerlist()));			
		} catch (Exception e) {
			logger.error("unable to insert incident due to bean copyproperties error: " + e);
			e.printStackTrace();
		}
		if (issueQ || issueL || issueT) {
			String type = request.getParameter("issuance_type");
			String quantity = request.getParameter("issuance_quantity");
			if (type != null && type.matches("^\\d+$")) {
				IssuanceItemIncident iss_inc = new IssuanceItemIncident();
				iss_inc.setIssueAgent(user);
				iss_inc.setIssueDate(TracerDateTime.getGMTDate());
				iss_inc.setUpdated(true);
				if (issueQ && quantity != null && quantity.matches("^\\d+$")) {
					int quantityInt = Integer.parseInt(quantity);
					if (quantityInt > 0) {
						iss_inc.setQuantity(quantityInt);
						IssuanceItemQuantity qItem = getQuantifiedItem(request, type);
						qItem.setQuantity(qItem.getQuantity() - iss_inc.getQuantity());
						iss_inc.setIssuanceItemQuantity(qItem);
						adjustIssuanceLists(request, qItem, null, null);
						//generate tempalte receipt
						DocumentTemplateResult result = generateTemplateReceipt(user, qItem.getIssuanceItem(), incident);
						if (result.isSuccess()) {
							Document d = (Document) result.getPayload();
							iss_inc.setDocument(d);	 					
							request.setAttribute("receiptName", d.getFileName());
						} else {
							errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(result.getMessageKey()));
						}
					} else { // quantity is a number but less than 1
						return false;
					}
				} else if (issueL || issueT) {
					IssuanceItemInventory iItem = getInventoriedItem(request, type);
					if (issueL) {
						iItem.setInventoryStatus(StatusBMO.getStatus(TracingConstants.ISSUANCE_ITEM_INVENTORY_STATUS_ONLOAN));
					} else {
						iItem.setInventoryStatus(StatusBMO.getStatus(TracingConstants.ISSUANCE_ITEM_INVENTORY_STATUS_ISSUED));
					}
					iss_inc.setIssuanceItemInventory(iItem);
					adjustIssuanceLists(request, null, iItem, null);
					//generate tempalte receipt					
					DocumentTemplateResult result = generateTemplateReceipt(user, iItem.getIssuanceItem(), incident);
					if (result.isSuccess()) {
						Document d = (Document) result.getPayload();
						iss_inc.setDocument(d);						
						request.setAttribute("receiptName", d.getFileName());
					} else {
						errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(result.getMessageKey()));
					}							
				} else { // issueQ but quantity is null or not a number
					return false;
				}
				theform.getIssuanceItemIncidents().add(iss_inc);
				request.setAttribute("issuanceitem", "1");
				request.setAttribute("markDirty", 1);
				return true;
			}
		}
		return false;
	}
	
	private static IssuanceItemQuantity getQuantifiedItem(HttpServletRequest request, String id) {
		@SuppressWarnings("unchecked")
		List<IssuanceItemQuantity> returnQList = (ArrayList<IssuanceItemQuantity>) request.getAttribute("item_quantity_resultList");
		long qID = Long.parseLong(id);
		for (IssuanceItemQuantity lQItem : returnQList) { // Find item and either adjust quantity or remove from list
			if (qID == lQItem.getId()) {
				return lQItem;
			}
		}
		return IssuanceItemBMO.getQuantifiedItem(id);
	}
	
	private static IssuanceItemInventory getInventoriedItem(HttpServletRequest request, String id) {
		@SuppressWarnings("unchecked")
		List<IssuanceItemInventory> returnIList = (ArrayList<IssuanceItemInventory>) request.getAttribute("item_inventory_resultList");
		long iID = Long.parseLong(id);
		for (IssuanceItemInventory lIItem : returnIList) { // Find item and either adjust quantity or remove from list
			if (iID == lIItem.getId()) {
				return lIItem;
			}
		}
		return IssuanceItemBMO.getInventoriedItem(id);
	}
	
	private static void adjustIssuanceLists(HttpServletRequest request, IssuanceItemQuantity qItem, IssuanceItemInventory iItem, IssuanceItemIncident item) {
		@SuppressWarnings("unchecked")
		List<IssuanceItemQuantity> returnQList = (ArrayList<IssuanceItemQuantity>) request.getAttribute("item_quantity_resultList");
		@SuppressWarnings("unchecked")
		List<IssuanceItemInventory> returnIList = (ArrayList<IssuanceItemInventory>) request.getAttribute("item_inventory_resultList");
		List<IssuanceCategory> returnCList = new ArrayList<IssuanceCategory>();
		boolean remove = true;
		if (qItem != null) { // Issued Quantified Item
			if (qItem.getQuantity() > 0) { // Only remove if quantity reduced to 0
				remove = false;
			}
			int index = 0;
			List<IssuanceItemQuantity> qListForLoop = new ArrayList<IssuanceItemQuantity>(returnQList);
			for (IssuanceItemQuantity lQItem : qListForLoop) { // Find item and either adjust quantity or remove from list
				if (qItem.getId() == lQItem.getId()) {
					if (remove) {
						returnQList.remove(lQItem);
					} else {
						returnQList.set(index, qItem);
					}
					break;
				}
				index++;
			}
		}
		if (iItem != null) { // Issued Inventoried Item
			List<IssuanceItemInventory> iListForLoop = new ArrayList<IssuanceItemInventory>(returnIList);
			for (IssuanceItemInventory lIItem : iListForLoop) {
				if (iItem.getId() == lIItem.getId()) {
					returnIList.remove(lIItem);
					break;
				}
			}
		}
		if (item != null) { // Returned an Issued Item
			if (item.getIssuanceItemInventory() != null) { // For Inventoried just Add Item to List
				returnIList.add(item.getIssuanceItemInventory());
			} else { // For Quantified First try to adjust quantity if item available, if item not available then add it to list.
				boolean addQItem = true;
				for (IssuanceItemQuantity lQItem : returnQList) { // Adjusting quantity if possible
					if (item.getIssuanceItemQuantity().getId() == lQItem.getId()) {
						lQItem.setQuantity(lQItem.getQuantity() + item.getQuantity());
						addQItem = false;
					}
				}
				if (addQItem) { // Adding to list if quantity not adjusted
					item.getIssuanceItemQuantity().setQuantity(item.getQuantity());
					returnQList.add(item.getIssuanceItemQuantity());
				}
			}
		}
		returnCList = getIssuanceCategories(returnQList, returnIList);
		request.setAttribute("item_quantity_resultList", returnQList);
		request.setAttribute("item_inventory_resultList", returnIList);
		request.setAttribute("item_category_resultList", returnCList);
	}
	
	public static void createIssuanceLists(HttpServletRequest request, Station station, int itemtype, List<IssuanceItemIncident> iItems) {
		List<IssuanceItemQuantity> returnQList = new ArrayList<IssuanceItemQuantity>();
		List<IssuanceItemInventory> returnIList = new ArrayList<IssuanceItemInventory>();
		List<IssuanceCategory> returnCList = new ArrayList<IssuanceCategory>();
		if (station != null && station.getStation_ID() > 0) {
			Station statObj = StationBMO.getStation(station.getStation_ID());
			List<IssuanceItemQuantity> fullQList = IssuanceItemBMO.getQuantifiedItems(statObj);
			List<IssuanceItemInventory> fullIList = IssuanceItemBMO.getInventoriedItems(statObj, TracingConstants.ISSUANCE_ITEM_INVENTORY_STATUS_AVAILABLE);
			for (IssuanceItemQuantity qItem : fullQList) {
				if (isIssuanceItemActive(qItem.getIssuanceItem(), itemtype) && qItem.getQuantity() > 0) {
					returnQList.add(qItem);
				}
			}
			for (IssuanceItemInventory iItem : fullIList) {
				if (isIssuanceItemActive(iItem.getIssuanceItem(), itemtype)) {
					returnIList.add(iItem);
				}
			}
		}
		adjustItems(iItems, returnQList, returnIList);
		returnCList = getIssuanceCategories(returnQList, returnIList);
		request.setAttribute("item_quantity_resultList", returnQList);
		request.setAttribute("item_inventory_resultList", returnIList);
		request.setAttribute("item_category_resultList", returnCList);
	}
	
	/**
	 * This method is insane and I hate that its needed. It adjusts the lists of quantified items and inventoried items based on issuance item actions
	 * that have occurred on the incident but have not yet been saved. It has been extra commented to help guide you through the jungle of logic and I
	 * apologize for the eight brackets at the end but this is what happens after around 12 iterations of trying to handle this tricky edge case.
	 * @param incItems
	 * @param qItems
	 * @param iItems
	 */
	private static void adjustItems(List<IssuanceItemIncident> incItems, List<IssuanceItemQuantity> qItems, List<IssuanceItemInventory> iItems) {
		if (incItems != null) {
			for (IssuanceItemIncident item : incItems) {
				if (item != null && item.isUpdated()) { // ADJUST LIST FOR UPDATED BUT NOT SAVED ITEMS ONLY!!!
					if (item.getIssuanceItemQuantity() != null && qItems != null) { // THIS IS A QUANTIFIED ITEM
						boolean notAdjusted = true;
						List<IssuanceItemQuantity> qListForLoop = new ArrayList<IssuanceItemQuantity>(qItems);
						for (IssuanceItemQuantity qItem : qListForLoop) { // CHECK IF ITEM IS IN LIST THEN INCREMENT IF ITEM IS BEING RETURNED, DECREMENT OTHERWISE
							if (qItem != null && qItem.getId() == item.getIssuanceItemQuantity().getId()) {
								if (item.isReturned()) {
									qItem.setQuantity(qItem.getQuantity() + item.getQuantity());
								} else {
									qItem.setQuantity(qItem.getQuantity() - item.getQuantity());
									if (qItem.getQuantity() <= 0) { // IF DECREMENTING REDUCES ITEM TO 0 THEN REMOVE IT
										qItems.remove(qItem);
									}
								}
								notAdjusted = false;
								break;
							}
						}
						if (notAdjusted && item.isReturned()) { // IF ITEM IS NOT CURRENTLY IN LIST AND IS BEING RETURNED, THEN INCREMENT AND ADD TO LIST
							IssuanceItemQuantity qItem = item.getIssuanceItemQuantity();
							qItem.setQuantity(item.getQuantity());
							qItems.add(qItem);
						}
					} // END QUANTIFIED ITEM
					if (item.getIssuanceItemInventory() != null && iItems != null) { // THIS IS AN INVENTORIED ITEM
						if (item.isReturned()) { // BEING RETURNED, THEREFORE ADD IT TO THE LIST
							IssuanceItemInventory iItem = item.getIssuanceItemInventory();
							iItems.add(iItem);
						} else { // BEING ISSUED, THEREFORE REMOVE IT FROM THE LIST
							List<IssuanceItemInventory> iListForLoop = new ArrayList<IssuanceItemInventory>(iItems);
							for (IssuanceItemInventory iItem : iListForLoop) {
								if (iItem != null && iItem.getId() == item.getIssuanceItemInventory().getId()) {
									iItems.remove(iItem);
									break;
								}
							}
						}
					} // END INVENTORIED ITEM
				} // END if(item != null && item.isUpdated())
			} // END for (IssuanceItemIncident item : incItems)
		} // END if (incItmes != null)
	}
	
	private static List<IssuanceCategory> getIssuanceCategories(List<IssuanceItemQuantity> qItems, List<IssuanceItemInventory> iItems) {
		List<IssuanceCategory> returnCList = new ArrayList<IssuanceCategory>();
		if (qItems != null) {
			for (IssuanceItemQuantity lQItem : qItems) { // Reset categories for all Quantified Items
				if (!isCategoryExists(lQItem.getIssuanceItem().getCategory().getId(), returnCList)) {
					returnCList.add(lQItem.getIssuanceItem().getCategory());
				}
			}
		}
		if (iItems != null) {
			for (IssuanceItemInventory lIItem : iItems) { // Reset categories for all Inventoried Items
				if (!isCategoryExists(lIItem.getIssuanceItem().getCategory().getId(), returnCList)) {
					returnCList.add(lIItem.getIssuanceItem().getCategory());
				}
			}
		}
		return returnCList;
	}
	
	private static boolean isCategoryExists(long catID, List<IssuanceCategory> cats) {
		if (cats != null) {
			for (IssuanceCategory cat : cats) {
				if (cat != null && catID == cat.getId()) {
					return true;
				}
			}
		}
		return false;
	}
	
	private static boolean isIssuanceItemActive(IssuanceItem item, int itemtype) {
		IssuanceCategory cat = item.getCategory();
		if (!item.isActive() || !cat.isActive()) {
			return false;
		} else if(itemtype==TracingConstants.LOST_DELAY && !cat.isLostdelay()){
			return false;
		} else if (itemtype==TracingConstants.MISSING_ARTICLES && !cat.isMissing()){
			return false;
		} else if (itemtype==TracingConstants.DAMAGED_BAG && !cat.isDamage()){
			return false;
		}
		return true;
	}

	public static boolean actionDelete(IncidentForm theform, HttpServletRequest request) {
		if (request.getParameter("delete_these_elements") != null && request.getParameter("delete_these_elements").length() > 0) {
			String deleteTheseElements =  request.getParameter("delete_these_elements");
			String[] elements = deleteTheseElements.split(",");
			boolean itemsDeleted = false;
			
			HashMap<String, ArrayList<Integer>> elementBreakdown = new HashMap<String,ArrayList<Integer>>();
			HashMap<Integer, ArrayList<Integer>> inventoryBreakdown = new HashMap<Integer,ArrayList<Integer>>();
			
			// Iterate through elements and sort into types
			for (String element: elements) {
				String[] str = element.split("_");

				// Treat inventory different because
				// 1.  They are nested objects
				// 2.  They must be dealt with prior to items.
				if (str[0].equals(TracingConstants.JSP_DELETE_INVENTORY)) {
					addToIntegerMap(inventoryBreakdown, Integer.parseInt(str[1]), Integer.parseInt(str[2]));
				}else {
					addToStringMap(elementBreakdown, str[0], str[1]);
				}
			}
			
			// Deal with Inventory
			for (Integer itemIndex: inventoryBreakdown.keySet()) {
				ArrayList<Integer> list = inventoryBreakdown.get(itemIndex);				
				Collections.sort(list, Collections.reverseOrder());
				
				for (int inventoryIndex: list) {
					@SuppressWarnings("unused")
					Item_Inventory ii = (Item_Inventory) theform.getItem(itemIndex, -1).getInventorylist().get(inventoryIndex);
					theform.getItem(itemIndex, -1).getInventorylist().remove(inventoryIndex);
				}
			}
			
			// Sort elements in the list in descending integer order,
			// then iterate through them to perform action.
			for (String key: elementBreakdown.keySet()) {
				ArrayList<Integer> list = elementBreakdown.get(key);				
				Collections.sort(list, Collections.reverseOrder());
				
				for (int index: list) {
					
					if(key.equals(TracingConstants.JSP_DELETE_ITINERARY)) {
						List<Itinerary> itinerarylist = theform.getItinerarylist();
						if (itinerarylist != null)
							itinerarylist.remove(index);
					} 

					if(key.equals(TracingConstants.JSP_DELETE_CLAIMCHECK)) {
						List<Incident_Claimcheck> claimchecklist = theform.getClaimchecklist();
						if (claimchecklist != null)
							claimchecklist.remove(index);
					} 

					if(key.equals(TracingConstants.JSP_DELETE_ITEM)) {
						List<Item> itemList = theform.getItemlist();
						if (itemList != null) {
							itemList.remove(index);
							itemsDeleted = true;
						}
					}
					
					if(key.equals(TracingConstants.JSP_DELETE_PAX)) {
						List<Passenger> passList = theform.getPassengerlist();
						if (passList != null)
							passList.remove(index);
					}
					
					if(key.equals(TracingConstants.JSP_DELETE_ARTICLE)) {
						List<Articles> articleList = theform.getArticlelist();
						if (articleList != null)
							articleList.remove(index);
					} 		
				}
			}
			
			if (itemsDeleted) {
				List<Item> itemList = theform.getItemlist();
				for (int i = 0; i < itemList.size(); i++) {
					Item item = (Item) itemList.get(i);
					item.setBagnumber(i);					
				}
				request.setAttribute("item", Integer.toString(theform.getItemlist().size() - 1));
			}
		}
		
		boolean deleteRemark = false;

		// when adding or deleting from the page,
		// email_customer checkbox needs to be set to 0 if user unchecked it
		if (request.getParameter("email_customer") == null)
			theform.setEmail_customer(0);

		String index = "0";
		@SuppressWarnings("rawtypes")
		Enumeration e = request.getParameterNames();
		while (e.hasMoreElements()) {
			String parameter = (String) e.nextElement();
			if (parameter.indexOf("[") != -1) {
				index = parameter.substring(parameter.indexOf("[") + 1, parameter.indexOf("]"));
					if (parameter.indexOf("deleteRemark") != -1) {
					deleteRemark = true;
					break;
				}
			}
		}
		if (deleteRemark) {
			List<Remark> remarkList = theform.getRemarklist();
			if (remarkList != null)
				remarkList.remove(Integer.parseInt(index));
			request.setAttribute("remark", Integer.toString(theform.getRemarklist().size() - 1));
			request.setAttribute("markDirty", 1);
			return true;
		}
		
		return org.apache.commons.lang.StringUtils.equalsIgnoreCase("y", request.getParameter("actionDelete"));
	}
	
	public static void addToIntegerMap(HashMap<Integer, ArrayList<Integer>> typeBreakdown, Integer key, Integer value) {
		ArrayList<Integer> internalList = null;
		
		if (typeBreakdown.containsKey(key)) {
			internalList = typeBreakdown.get(key);
			internalList.add(value);
		} else {
			internalList = new ArrayList<Integer>();
			internalList.add(value);
			typeBreakdown.put(key, internalList);
		}
	}
	
	public static void addToStringMap(HashMap<String, ArrayList<Integer>> typeBreakdown, String key, String integer) {
		ArrayList<Integer> internalList = null;
		
		Integer value = Integer.parseInt(integer);
		
		if (typeBreakdown.containsKey(key)) {
			internalList = typeBreakdown.get(key);
			internalList.add(value);
		} else {
			internalList = new ArrayList<Integer>();
			internalList.add(value);
			typeBreakdown.put(key, internalList);
		}
	}

	@SuppressWarnings("unchecked")
	public static boolean actionClose(IncidentForm theform, HttpServletRequest request, Agent user, ActionMessages errors) throws Exception {
		String incident = request.getParameter("incident_ID");
		
		Incident inc = null;
		if(incident != null && incident.length() > 0 && request.getParameter("close") != null) {
			BagService bs = new BagService();
			inc = bs.findIncidentByID(incident, theform, user, TracingConstants.LOST_DELAY);
		} else {
			inc = IncidentBMO.getIncidentByID(theform.getIncident_ID(), null); 
		}
		
		List<Station> faultstationlist = null;
		List<Company> faultCompanyList = null;
		if (theform.getFaultcompany_id() != null && !theform.getFaultcompany_id().equals("")) {
			if (TracerProperties.isTrue(user.getCompanycode_ID(),TracerProperties.SET_DEFAULT_AIRLINE) && (theform.getFaultcompany_id() == null || theform.getFaultcompany_id().equals(""))) {
				theform.setFaultcompany_id(user.getCompanycode_ID());
			}
			// If the user has limited permission, 

			if (UserPermissions.hasLimitedSavePermission(user, inc)) {
				faultstationlist = UserPermissions.getLimitedSaveStations(user, theform.getIncident_ID());
				faultCompanyList = new ArrayList<Company>();
				faultCompanyList.add(user.getStation().getCompany());
			} else if (inc != null && UserPermissions.hasLimitedFaultAirlinesByType(user, inc.getItemtype().getItemType_ID())) {
				faultstationlist = TracerUtils.getStationList(theform.getFaultcompany_id());
				faultCompanyList = new ArrayList<Company>();
				faultCompanyList.add(user.getStation().getCompany());
			} else {
				faultstationlist = TracerUtils.getStationList(theform.getFaultcompany_id());
				faultCompanyList = (List<Company>) request.getSession().getAttribute("companylistByName");
			}


			request.setAttribute("faultstationlist", faultstationlist);
			request.setAttribute("faultCompanyList", faultCompanyList);
		} 
		
		// change faultstationlist (ajax call)
		if (request.getParameter("getstation") != null && request.getParameter("getstation").equals("1")) {

			if (theform.getFaultcompany_id() == null || theform.getFaultcompany_id().equals("")) {
				theform.setFaultstation_id(0);
				return true;
			}

			return true;
		}

		if (request.getParameter("close") != null && (request.getParameter("doclose") != null || request.getParameter("doclosewt") != null)) {
			ActionMessage error = null;
			/**
			 * Checking for the losscode per bag permission. If the permission is not set, then the incident will close on a per incident basis. 
			 * If it is set, then the fault station is checked against the passenger itinerary to confirm it's valid.
			 */
			if(!UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_LOSS_CODES_BAG_LEVEL, user)){
				if (theform.getFaultstation_id() == 0 || Integer.toString(theform.getFaultstation_id()) == "") {
					error = new ActionMessage("error.choose_faultcompany");
					if (error != null) {
						if (error.getKey().length() > 0) {
							errors.add(ActionMessages.GLOBAL_MESSAGE, error);
						}
					}
		
					return true;
				}
				if (theform.getLoss_code() == 0) {
					error = new ActionMessage("error.choose_lossreason");
					if (error != null) {
						if (error.getKey().length() > 0) {
							errors.add(ActionMessages.GLOBAL_MESSAGE, error);
						}
					}
					return true;
				}
			} else {
				if(UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_LOSS_CODES_BAG_LEVEL, user)){
					HashMap<String,String> paxItinMap=new HashMap<String,String>();
					/**
					 * Check the properties table to see if the company wants to
					 * check against Pax Itinerary (0) or Baggage Itinerary (1) or
					 * neither (-1)
					 */
					int itinType=PropertyBMO.getValueAsInt(PropertyBMO.PROPERTY_BAG_LEVEL_LOSS_CODES_ITIN_CHECK);
					if(itinType!=-1){
						Map<Integer,Company_specific_irregularity_code> codeMap=new HashMap<Integer,Company_specific_irregularity_code>();
						List<Integer> lossCodeList=new ArrayList<Integer>();
						Item item=null;
						for(Object obj:theform.getItemlist()){
							item=(Item) obj;
							lossCodeList.add(item.getLossCode());
						}
						List<Company_specific_irregularity_code> codeList=LossCodeBMO.getLossCodes(lossCodeList, inc.getItemtype_ID(), inc.getStationcreated().getCompany().getCompanyCode_ID());
						
						for(Itinerary itin:theform.getItinerarylist()){
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
									codeMap.put(code.getLoss_code(), code);
								}
							}
						}
						boolean inPaxItin=true;
						for(Item it:theform.getItemlist()){
							if(it.getFaultStation()!=null && it.getFaultStation().getStationcode()!=null 
									&& it.getFaultStation().getStationcode().length()>0 && it.getLossCode()>0){
								Company_specific_irregularity_code code=codeMap.get(it.getLossCode());
								if(paxItinMap.get(it.getFaultStation().getStationcode()+it.getLossCode())==null
										&& !(code.isAnyStation())){
									inPaxItin=false;
									break;
								}
							}
						}
						if(!inPaxItin){
							error = new ActionMessage("error.fault.pax.itin");
							if (error != null) {
								if (error.getKey().length() > 0) {
									errors.add(ActionMessages.GLOBAL_MESSAGE, error);
								}
							}
							return true;
						}
					}
				}
			}
			theform.getStatus().setStatus_ID(TracingConstants.MBR_STATUS_CLOSED);

			return false;
		}

		// regular save, don't change status
		if (request.getParameter("close") != null && request.getParameter("save") != null) {
			return false;
		}

		if (theform.getStatus() != null) {
			request.setAttribute("currentstatus", Integer.toString(theform.getStatus().getStatus_ID()));
		}

		String comp = theform.getFaultcompany_id();
		if ((comp == null || comp.length() == 0) && theform.getStationassigned().getCompany() != null) {
			comp = theform.getStationassigned().getCompany().getCompanyCode_ID();
		}
		
		if (faultstationlist == null) {
			faultstationlist = TracerUtils.getStationList(comp);
			request.setAttribute("faultstationlist", faultstationlist);
		}

		// add closing remark

		if (request.getParameter("addcloseremark") != null) {
			//set new remark with current time
			Remark r = theform.getClosingRemark(theform.getRemarklist().size());
			r.setCreatetime(new SimpleDateFormat(TracingConstants.DB_DATETIMEFORMAT).format(TracerDateTime.getGMTDate()));
			r.setAgent(user);
			r.set_DATEFORMAT(user.getDateformat().getFormat());
			r.set_TIMEFORMAT(user.getTimeformat().getFormat());
			r.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone()));
			request.setAttribute("markDirty", 1);
			return true;
		}
		// delete closing remark
		boolean deleteremark = false;
		String index = "0";
		@SuppressWarnings("rawtypes")
		Enumeration e = request.getParameterNames();
		while (e.hasMoreElements()) {
			String parameter = (String) e.nextElement();
			if (parameter.indexOf("[") != -1) {
				index = parameter.substring(parameter.indexOf("[") + 1, parameter.indexOf("]"));
				if (parameter.indexOf("deleteCloseRemark") != -1) {
					deleteremark = true;
					break;
				}
			}
		}
		if (deleteremark) {
			List<Remark> remarkList = theform.getRemarklist();
			if (remarkList != null)
				remarkList.remove(Integer.parseInt(index));
			request.setAttribute("markDirty", 1);
			return true;
		}

		// close report
		if (request.getParameter("close") != null) {
			if (theform.getFaultcompany_id() == null || theform.getFaultcompany_id().equals("")) {
				// use current company as fault company and get default_station_code
				// from company_specific_variables
				theform.setFaultcompany_id(theform.getStationassigned().getCompany().getCompanyCode_ID());
			}

			if (theform.getFaultstation_id() <= 0) {
				Company_Specific_Variable csv = AdminUtils.getCompVariable(theform.getFaultcompany_id());
				if (csv.getDefault_station_code() == 0)
					theform.setFaultstation(theform.getStationassigned());
				else {
					Station st = StationBMO.getStation(Integer.toString(csv.getDefault_station_code()));
					theform.setFaultstation(st);
				}
			}

			boolean hasclose = false;
			for (int i = 0; i < theform.getRemarklist().size(); i++) {
				Remark r = theform.getClosingRemark(i);
				if (r.getRemarktype() == TracingConstants.REMARK_CLOSING) {
					hasclose = true;
					break;
				}
			}
			if (!hasclose) {
				// add closing remark if there isn't one
				Remark r = theform.getClosingRemark(theform.getRemarklist().size());
				r.setCreatetime(new SimpleDateFormat(TracingConstants.DB_DATETIMEFORMAT).format(TracerDateTime.getGMTDate()));
				r.setAgent(user);
				r.set_DATEFORMAT(user.getDateformat().getFormat());
				r.set_TIMEFORMAT(user.getTimeformat().getFormat());
				r.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone()));
			}

			return true;
		}
		return false;
	}

	public static boolean actionGetStation(IncidentForm theform, HttpServletRequest request, Agent user) throws Exception {
		if (request.getParameter("getstation") != null && request.getParameter("getstation").equals("1")) {

			if (theform.getFaultcompany_id() == null)
				return true;

			List<Station> faultstationlist = TracerUtils.getStationList(theform.getFaultcompany_id());
			request.setAttribute("faultstationlist", faultstationlist);
			return true;
		}
		return false;
	}

	/**
	 * add associate report
	 * 
	 * @param theform
	 * @param request
	 * @param user
	 * @return @throws
	 *         Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String actionAddAssoc(IncidentForm theform, HttpServletRequest request, Agent user) throws Exception {
		String loc = null;
		int type = 0; 
		
		if (request.getParameter("add_assoc_report") != null) {
			if (Integer.parseInt(request.getParameter("add_assoc_report")) == TracingConstants.LOST_DELAY) {
				loc = TracingConstants.LD_MAIN;
				type = TracingConstants.LOST_DELAY;
			}
			if (Integer.parseInt(request.getParameter("add_assoc_report")) == TracingConstants.MISSING_ARTICLES) {
				loc = TracingConstants.MISSING_MAIN;
				type = TracingConstants.MISSING_ARTICLES;
			}
			if (Integer.parseInt(request.getParameter("add_assoc_report")) == TracingConstants.DAMAGED_BAG) {
				loc = TracingConstants.DAMAGED_MAIN;
				type = TracingConstants.DAMAGED_BAG;
			}
			if (type == 0)
				return null;

			// MJS: populate the OSI if we have it
			OtherSystemInformation osi = OtherSystemInformationBMO.getOsi(theform.getIncident_ID(), null);
			if (osi != null) {
				theform.setOtherSystemInformation(osi.getInfo());
			}
			
			request.setAttribute("markDirty", 1);
			
			// initialize vars
			request.setAttribute("newform", "1");
			theform.setIncident_ID("");
			theform.setWtFile(null);
			theform.set_DATEFORMAT(user.getDateformat().getFormat());
			theform.set_TIMEFORMAT(user.getTimeformat().getFormat());
			theform.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone()));
			theform.setCreatedate(TracerDateTime.getGMTDate());
			theform.setCreatetime(TracerDateTime.getGMTDate());
			// set station
			theform.setStationcreated_ID(user.getStation().getStation_ID());
			theform.setStationcreated(user.getStation());
			theform.setStationassigned_ID(user.getStation().getStation_ID());
			//theform.setFaultstation(new Station());
			
			// MLL: making blanket change, fault station is always going to be agent station
			theform.setFaultstation(new Station());
			theform.getFaultstation().setStation_ID(user.getStation().getStation_ID());
			
			// MJS: set the loss code
			int defaultLossCode = theform.getAgent().getStation().getCompany().getVariable().getDefault_loss_code();
			if(type == TracingConstants.DAMAGED_BAG && PropertyBMO.getValueAsInt(PropertyBMO.PROPERTY_DEFAULT_DAM_CODE) > 0){
				theform.setLoss_code(PropertyBMO.getValueAsInt(PropertyBMO.PROPERTY_DEFAULT_DAM_CODE));
			} else if (type == TracingConstants.MISSING_ARTICLES && PropertyBMO.getValueAsInt(PropertyBMO.PROPERTY_DEFAULT_PIL_CODE) > 0){
				theform.setLoss_code(PropertyBMO.getValueAsInt(PropertyBMO.PROPERTY_DEFAULT_PIL_CODE));
			} else {
				if (defaultLossCode > 0) {
					theform.setLoss_code(defaultLossCode);
				} else {
					theform.setLoss_code(0);
				}
			}
			
			if(theform.getDeliveryInstructions()==null && type == TracingConstants.LOST_DELAY)
			{
				DeliveryInstructions DI=new DeliveryInstructions();
				DI.setInstructions("");
				theform.setDeliveryInstructions(DI);
			}
			
			// set agent
			theform.setAgent(user);
			// set status as temp to start off
			Status status = new Status();
			status.setStatus_ID(TracingConstants.MBR_STATUS_TEMP);
			theform.setStatus(status);
			// set report method
			theform.setReportmethod(user.getStation().getCompany().getVariable().getReport_method());

			// clear pass ids for new insert
			ArrayList al = new ArrayList();
			ArrayList al2 = null;
			if (theform.getPassengerlist() != null) {
				Passenger pa = null;
				Passenger pa2 = null;
				Address ad = null;
				Address ad2 = null;
				for (int i = 0; i < theform.getPassengerlist().size(); i++) {
					pa = (Passenger) theform.getPassengerlist().get(i);
					pa.setPassenger_ID(0);
					
					pa2 = new Passenger();
					BeanUtils.copyProperties(pa2, pa);
					
					if (pa2.getMembership() != null) pa2.getMembership().setMembership_ID(0);
					else {
						pa2.setMembership(new AirlineMembership());
					}
					
					// address
					al2 = new ArrayList();
					for (int j = 0; j < pa.getAddresses().size(); j++) {
						ad = (Address) pa.getAddress(j);
						ad2 = new Address();
						BeanUtils.copyProperties(ad2, ad);
						ad2.setAddress_ID(0);
						ad2.setPassenger(pa2);
						al2.add(ad2);
					}
					pa2.setAddresses(new HashSet(al2));

					al.add(pa2);
				}
			}
			theform.setPassengerlist(al);
			
			theform.setActivityDtos(new ArrayList<IncidentActivityDTO>());

			// clear itinerary ids for new insert
			al = new ArrayList();
			if (theform.getItinerarylist() != null) {
				Itinerary iti = null;
				Itinerary iti2 = null;
				for (int i = 0; i < theform.getItinerarylist().size(); i++) {
					iti = (Itinerary) theform.getItinerarylist().get(i);
					iti.setItinerary_ID(0);
					iti2 = new Itinerary();
					BeanUtils.copyProperties(iti2, iti);
					al.add(iti2);
				}
			}
			theform.setItinerarylist(al);

			// clear bag ids
			al = new ArrayList();
			if (theform.getItemlist() != null) {
				Item item = null;
				Item item2 = null;
				Item_Inventory ii = null;
				Item_Inventory ii2 = null;
				for (int i = 0; i < theform.getItemlist().size(); i++) {
					item = (Item) theform.getItemlist().get(i);
					item.setItemtype_ID(type);
					item.setItem_ID(0);
					item.setIncident(null);
					item.set_DATEFORMAT(user.getDateformat().getFormat());
					item.setCurrency_ID(user.getDefaultcurrency());
					item.setOHD_ID("");
					item.setPhotolist(new ArrayList());
					item2 = new Item();
					
					BeanUtils.copyProperties(item2, item);
					
					//to fix a bug reported by USAIR - hibernate exception 
					//when saving associated incident, when original LD incident has a BDO
					item2.setItem_bdo(null);
					Status itemStatus = new Status();
					itemStatus.setStatus_ID(TracingConstants.ITEM_STATUS_OPEN);
					item2.setStatus(itemStatus);
					
					// clear inventory
					al2 = new ArrayList();
					for (int j = 0; j < item.getInventorylist().size(); j++) {
						ii = (Item_Inventory) item.getInventorylist().get(j);
						ii2 = new Item_Inventory();
						BeanUtils.copyProperties(ii2, ii);
						ii2.setInventory_ID(0);
						ii2.setItem(item2);
						al2.add(ii2);
					}
					item2.setInventory(new HashSet(al2));

					al.add(item2);

				}
			}

			theform.setItemlist(al);

			// clear remarks id
			al = new ArrayList();
			if (theform.getRemarklist() != null) {
				Remark remark = null;
				Remark remark2 = null;
				for (int i = 0; i < theform.getRemarklist().size(); i++) {
					remark = (Remark) theform.getRemarklist().get(i);
					remark.setRemark_ID(0);
					remark.set_DATEFORMAT(user.getDateformat().getFormat());
					remark.set_TIMEFORMAT(user.getTimeformat().getFormat());
					remark.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone()));
					remark2 = new Remark();
					BeanUtils.copyProperties(remark2, remark);
					al.add(remark2);
				}
			}
			theform.setRemarklist(al);

			// clear claim information
			theform.setClaims(null);

			// create new article
			theform.setArticlelist(new ArrayList());
			if (type == TracingConstants.MISSING_ARTICLES) {
				Articles a = theform.getArticle(0);
				a.set_DATEFORMAT(user.getDateformat().getFormat());
				a.setEnteredDate(new Date());
				a.setCurrency_ID(user.getDefaultcurrency());
			}

			// set new claimcheck
			al = new ArrayList();
			al.add(new Incident_Claimcheck());
			theform.setClaimchecklist(al);
			
			
			
		}
		return loc;
	}

	/**
	 * MATCH
	 * 
	 * @param theform
	 * @param request
	 * @param user
	 * @param realpath
	 * @return @throws
	 *         Exception
	 */
	public static ActionMessage actionMatching(IncidentForm theform, HttpServletRequest request, Agent user, ServletContext sc) throws Exception {

		BagService bs = new BagService();
		Incident_Claimcheck ic = null;
		Item item = null;
		OhdBMO oBMO = new OhdBMO();

		OHD ohd_obj = null;

		ActionMessage error = null;

		// match claim check is clicked
		for (int i = 0; i < theform.getClaimchecklist().size(); i++) {
			if (request.getParameter("matchclaim" + i) != null) {
				// unmatch one claim
				ic = (Incident_Claimcheck) theform.getClaimcheck(i);
				String ohd = ic.getTempOHD_ID();
				if (ohd != null)
					ohd = StringUtils.fillzero(TracerUtils.removeSpaces(ohd.toUpperCase()));
				ohd_obj = oBMO.findOHDByID(ohd);
				if (ohd_obj == null) {
					// not a valid on-hand id
					ic.setOHD_ID("");
					error = new ActionMessage("error.match_noonhand");
					//request.setAttribute("claimcheck", "1");
					return error;
				}
				ic.setOHD_ID(ohd); // new uppercased and spaced removed ohd_id

				if ((error = bs.insertIncident(new Incident(), theform, TracingConstants.LOST_DELAY, sc, request)) == null) {
					if (ohd_obj.getHoldingStation().getStation_ID() == theform.getStationassigned().getStation_ID()) {
						ohd_obj.setStatus(StatusBMO.getStatus(TracingConstants.OHD_STATUS_TO_BE_DELIVERED));
					} else if (ohd_obj.getStatus().getStatus_ID() == TracingConstants.OHD_STATUS_IN_TRANSIT) {
						ohd_obj.setStatus(StatusBMO.getStatus(TracingConstants.OHD_STATUS_MATCH_IN_TRANSIT));
					}
					ohd_obj.setMatched_incident(theform.getIncident_ID());
					oBMO.insertOHD(ohd_obj, theform.getAgent());
				} else {
					return error;
				}
				request.setAttribute("claimcheck", "1");
				return new ActionMessage("");
			}
		}

		// match bag is clicked
		for (int i = 0; i < theform.getItemlist().size(); i++) {
			if (request.getParameter("matchbag" + i) != null) {
				// unmatch one bag
				item = (Item) theform.getItem(i, 0);
				String ohd = item.getTempOHD_ID();
				if (ohd != null)
					ohd = TracerUtils.removeSpaces(ohd.toUpperCase());
				ohd_obj = oBMO.findOHDByID(ohd);
				if (ohd_obj == null) {
					// not a valid on-hand id
					item.setOHD_ID("");
					error = new ActionMessage("error.match_noonhand");
					//request.setAttribute("item", Integer.toString(i));
					return error;
				} else {
					if (!(ohd_obj.getStatus().getStatus_ID() == TracingConstants.OHD_STATUS_OPEN || ohd_obj.getStatus().getStatus_ID() == TracingConstants.OHD_STATUS_IN_TRANSIT || ohd_obj.getStatus().getStatus_ID() == TracingConstants.OHD_STATUS_MATCH_IN_TRANSIT)) {
						
						// ohd is not open
						item.setOHD_ID("");
						error = new ActionMessage("error.match_noonhand");
						//request.setAttribute("item", Integer.toString(i));
						return error;
					}
				}
				item.setOHD_ID(ohd); // new uppercased and spaced removed ohd_id

				if (item.getClaimchecknum() == null || item.getClaimchecknum().length() <= 0) {
					// no claimcheck, most likely manually matched
					// move ohd information over
					if (ohd_obj.getClaimnum() != null) {
						item.setClaimchecknum(ohd_obj.getClaimnum());
						// find claimnum in claimcheck list and match that one as well
						for (int j = 0; j < theform.getClaimchecklist().size(); j++) {
							ic = (Incident_Claimcheck) theform.getClaimchecklist().get(j);
							if (ic.getClaimchecknum() != null && ic.getClaimchecknum().equals(ohd_obj.getClaimnum())) {
								ic.setOHD_ID(item.getOHD_ID());
							}
						}
					}

				}

				// change both item and ohd to tobedelivered because they are in the
				// same station
				if (ohd_obj.getHoldingStation().getStation_ID() == theform.getStationassigned().getStation_ID()) {
					item.setStatus(StatusBMO.getStatus(TracingConstants.ITEM_STATUS_TOBEDELIVERED));
				} else {
					// change item to matched only
					item.setStatus(StatusBMO.getStatus(TracingConstants.ITEM_STATUS_MATCHED));
				}

				if ((error = bs.insertIncident(new Incident(), theform, TracingConstants.LOST_DELAY, sc, request)) == null) {
					// update ohd status to be delivered if it is in this station
					if (ohd_obj.getHoldingStation().getStation_ID() == theform.getStationassigned().getStation_ID()) {
						ohd_obj.setStatus(StatusBMO.getStatus(TracingConstants.OHD_STATUS_TO_BE_DELIVERED));
					} else if (ohd_obj.getStatus().getStatus_ID() == TracingConstants.OHD_STATUS_IN_TRANSIT) {
						ohd_obj.setStatus(StatusBMO.getStatus(TracingConstants.OHD_STATUS_MATCH_IN_TRANSIT));
					}
					ohd_obj.setMatched_incident(theform.getIncident_ID());
					oBMO.insertOHD(ohd_obj, theform.getAgent());
				} else {
					return error;
				}

				request.setAttribute("item", Integer.toString(i));
				return new ActionMessage("");
			}
		}
		return null;
	}

	public static boolean actionUnMatching(IncidentForm theform, HttpServletRequest request, Agent user, ServletContext sc) throws Exception {
		// unmatch claim is clicked
		BagService bs = new BagService();
		Incident_Claimcheck ic = null;
		Item item = null;
		OHD ohd_obj = null;
		OhdBMO oBMO = new OhdBMO();
		for (int i = 0; i < theform.getClaimchecklist().size(); i++) {
			if (request.getParameter("unmatchclaim" + i) != null) {
				// unmatch one claim
				ic = (Incident_Claimcheck) theform.getClaimcheck(i);
				String ohd = ic.getOHD_ID();
				ohd_obj = oBMO.findOHDByID(ohd);
				// find the bag associated with this
				for (int j = 0; j < theform.getItemlist().size(); j++) {
					item = (Item) theform.getItem(j, 0);
					if (item.getOHD_ID() != null && item.getOHD_ID().equals(ohd)) {
						// see if bdo is created first
						BDO bdo = item.getBdo();
						if (bdo != null) {
							// if bdo created, then set bdo to ohd instead
//							bdo.setIncident(null);
//							item.setBdo(null);
							BDOUtils.cancelBdo(bdo.getBDO_ID(), item.getItem_ID(), (Agent) request.getSession().getAttribute("user"));
							BDOUtils.insertBDOtoDB(bdo, user);
						}
						
						// clear bag ohd and claimcheck
						item.setOHD_ID("");
						item.setClaimchecknum("");

						// if bag is at the report station, change both status to open
						// again
						
						if (ohd_obj != null ) {
							if (ohd_obj.getHoldingStation().getStation_ID() == theform.getStationassigned().getStation_ID()) {
								item.setStatus(StatusBMO.getStatus(TracingConstants.ITEM_STATUS_OPEN));	
								if (bdo == null) {
									// if no bdo, then change ohd status, otherwise leave it as is
									ohd_obj.setStatus(StatusBMO.getStatus(TracingConstants.OHD_STATUS_OPEN));
								}
							} else {
								ohd_obj.setStatus(StatusBMO.getStatus(TracingConstants.OHD_STATUS_OPEN));
							}
							
						} else {
							// change item to open only
							item.setStatus(StatusBMO.getStatus(TracingConstants.ITEM_STATUS_OPEN));
						}

						break;
					}
				}
				if (ohd_obj != null) {
					ohd_obj.setMatched_incident(null);
					oBMO.insertOHD(ohd_obj, user);
				}
				//empty out claim ohd
				ic.setOHD_ID("");
				// call unmatch to clear out match history
				MatchUtils.unmatchTheOHD(ohd, user);

				bs.insertIncident(new Incident(), theform, TracingConstants.LOST_DELAY, sc, request);
				request.setAttribute("claimcheck", "1");
				return true;
			}
		}

		// unmatch bag is clicked
		for (int i = 0; i < theform.getItemlist().size(); i++) {
			if (request.getParameter("unmatchbag" + i) != null) {
				// unmatch one bag
				item = (Item) theform.getItem(i, 0);
				String ohd = item.getOHD_ID();
				ohd_obj = oBMO.findOHDByID(ohd);
				// find the claimcheck associated with this
				for (int j = 0; j < theform.getClaimchecklist().size(); j++) {
					ic = (Incident_Claimcheck) theform.getClaimcheck(j);
					if (ic.getOHD_ID() != null && ic.getOHD_ID().equals(ohd)) {
						// clear claim ohd
						ic.setOHD_ID("");
						break;
					}
				}
				
				// see if bdo is created first
				BDO bdo = item.getBdo();
				if (bdo != null) {
					// if bdo created, then set bdo to ohd instead
//					bdo.setIncident(null);
//					item.setBdo(null);
					BDOUtils.cancelBdo(bdo.getBDO_ID(), item.getItem_ID(), (Agent) request.getSession().getAttribute("user"));
					BDOUtils.insertBDOtoDB(bdo, user);
				}
				
				//empty out bag ohd and claimcheck
				item.setOHD_ID("");
				item.setClaimchecknum("");

				// if bag is at the report station, change both status to open
				// again
				if (ohd_obj != null & ohd_obj.getHoldingStation().getStation_ID() == theform.getStationassigned().getStation_ID()) {
					item.setStatus(StatusBMO.getStatus(TracingConstants.ITEM_STATUS_OPEN));
					if (bdo == null) {
						ohd_obj.setStatus(StatusBMO.getStatus(TracingConstants.OHD_STATUS_OPEN));
					}
					oBMO.insertOHD(ohd_obj, theform.getAgent());
				} else {
					// change item to open only
					item.setStatus(StatusBMO.getStatus(TracingConstants.ITEM_STATUS_OPEN));
				}

				// call unmatch to clear out match history
				MatchUtils.unmatchTheOHD(ohd, user);
				
				if (ohd_obj != null) {
					ohd_obj.setMatched_incident(null);
					oBMO.insertOHD(ohd_obj, user);
				}
				bs.insertIncident(new Incident(), theform, TracingConstants.LOST_DELAY, sc, request);

				request.setAttribute("item", Integer.toString(i));
				return true;
			}
		}

		return false;
	}

	/**
	 * Passenger Picked Up - Marks the selected Item as Passenger Picked Up
	 * @param theform - the incident form to be updated
	 * @param request - the Request used toget parameters and update messages
	 * @param errors - The ActionMessage list to update special information such as if the status couldn't be updated, or if the incident needs to be saved 
	 * @return boolean if the status change happened
	 * @throws Exception
	 */
	public static boolean passengerPickedUp(IncidentForm theform, HttpServletRequest request, ActionMessages errors) throws Exception {

		Item item = null;
		ActionMessage error = null;
		
		// Passenger Picked Up is clicked
		try{
			//Potential TODO: Add logic to check for Station here
			for (int i = 0; i < theform.getItemlist().size(); i++) {
				if (request.getParameter("passengerpickedup" + i) != null) {
					item = (Item) theform.getItem(i, 0);
					Status s=null;
					if(item.getStatus().getStatus_ID()!=TracingConstants.ITEM_STATUS_PASSENGER_PICKED_UP){
						s=StatusBMO.getStatus(TracingConstants.ITEM_STATUS_PASSENGER_PICKED_UP);
					} else {
						if(item.getOHD_ID()!=null && !item.getOHD_ID().isEmpty()){
							OHD o=OhdBMO.getOHDByID(item.getOHD_ID(),null);
							if(o!=null){
								if(o.getHoldingStation()!=null && item.getIncident()!=null && item.getIncident().getStationassigned()!=null &&
										o.getHoldingStation().getStation_ID()==item.getIncident().getStationassigned().getStation_ID()){
									s=StatusBMO.getStatus(TracingConstants.ITEM_STATUS_TOBEDELIVERED);
								} else {
									s=StatusBMO.getStatus(TracingConstants.ITEM_STATUS_MATCHED);
								}
							}
						} else {
							s=StatusBMO.getStatus(TracingConstants.ITEM_STATUS_OPEN);
						}
					}
					item.setStatus(s);
					error=new ActionMessage("save.reminder");
					errors.add(ActionMessages.GLOBAL_MESSAGE, error);
					return true;
				}
			}
		} catch (Exception e) {
			error = new ActionMessage("error.cannot.passenger.pick.up");
			errors.add(ActionMessages.GLOBAL_MESSAGE, error);
		}
		return false;
	}
	
	public static ActionMessage checkOHDEntered(IncidentForm theform, HttpServletRequest request, Agent user, ServletContext sc) throws Exception {

		Incident_Claimcheck ic = null;
		Item item = null;

		ActionMessage error = null;

	
		for (int i = 0; i < theform.getClaimchecklist().size(); i++) {
			ic = (Incident_Claimcheck) theform.getClaimcheck(i);
			String ohd = ic.getTempOHD_ID();
			if (ohd != null && ohd.length() > 0 && (ic.getOHD_ID() == null || ic.getOHD_ID().length() == 0)) {
				error = new ActionMessage("error.match_needtoconfirm");
				return error;
			}
		}

		// match bag is clicked
		for (int i = 0; i < theform.getItemlist().size(); i++) {
			item = (Item) theform.getItem(i, 0);
			String ohd = item.getTempOHD_ID();
			if (ohd != null && ohd.length() > 0 && ic != null && (ic.getOHD_ID() == null || ic.getOHD_ID().length() == 0)) {
				error = new ActionMessage("error.match_needtoconfirm");
				return error;
			}
		}
		return null;
	}
	
	
	public static boolean actionChangeAssignedStation(IncidentForm theform, HttpServletRequest request) {

		// change up service level
		if (request.getParameter("changeassignedstation") != null && request.getParameter("changeassignedstation").equals("1")) {
			if (theform.getStationassigned_ID() > 0) {
				List<Agent> agentassignedlist = TracerUtils.getAgentlist(theform.getStationassigned_ID());
				// AJAX CALL
				request.setAttribute("agentassignedlist", agentassignedlist);
				return true;
			}
		}
		return false;
	}

	public static boolean prePopulate(HttpServletRequest request,IncidentForm form, ArrayList<String> alerrors, int incidentType) {
		if (request.getParameter("doprepopulate") != null && (request.getParameter("doprepopulate").length() > 0)) {
			if (request.getParameter("wt_af_id") == null) {
				alerrors.addAll(SpringUtils.getReservationIntegration().populateIncidentForm(request, form, incidentType));
				return true;
			}
		}
		return false;
	}

	/**
	 * PNR Prepopulation Check Tool
	 *
	 * Method uses to get and return a list of incidents that match pnr 
	 * and were made within the last X amount of days (determined by Company_Specific_Variable pnrLastXDays)
	 *
	 * @param  pnr 	String of pnr to compare against Incidents
	 * @param  lastXDays	Integer of day count that the incident has to be made within the last days of to be included.
	 * @return List of incidents with similar pnr and made in the last X Days.
	 * -Sean Fine
	 */

	public static List<Incident> prePopulateCheck(String pnr, int lastXDays) {
		
		return IncidentBMO.getIncidentsByPNR(pnr, lastXDays);
		// TODO Auto-generated method stub
		
	}
	

	/**
	 * Method to get Assist Device Types - Category Type "2"
	 */
	public static List<Category> getAssistDeviceTypes() {
		Session sess = null;
		try {

			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(Category.class);
			cri.add(Restrictions.eq("type", new Integer(TracingConstants.ASSIST_DEVICE_TYPE)));
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

	private static DocumentTemplateResult generateTemplateReceipt(Agent user, IssuanceItem qitem, Incident incident) {
		DocumentTemplateResult result = new DocumentTemplateResult();
		// 1. load the template
		long templateId = 0;
		if (qitem.getCategory().getTemplate() != null) {
			templateId = qitem.getCategory().getTemplate().getId();
		} else {
			logger.error("No template defined " );
			result.setMessageKey("no.template.receipt.defined");
			return result;			
		}

		Template template = templateService.load(templateId);
		if (template == null) {
			result.setMessageKey("no.template.receipt.defined");
			return result;
		}
		// 2. get the template adapter
		TemplateAdapterDTO dto = DomainUtils.getTemplateAdapterDTO(user, template);
		dto.setIncident(incident);
		
		try {
			TemplateAdapter adapter = TemplateAdapterFactory.getTemplateAdapter(dto);
			Document document = new Document(template);
			
			// 3. merge the template and adapter
			result = documentService.mergeDocumentToPrint(document, adapter);
			if (!result.isSuccess()) return result;

			// 4. save the document
			if (documentService.save(document) > 0) {
				result.setPayload(document);
			} else {
				result.setSuccess(false);
			}
		} catch (Exception e) {
			logger.error("Failed to generate the template receipt for Incident with id: " + qitem.getCategory().getTemplate().getId(), e);
		}
		
		return result;
	}
}