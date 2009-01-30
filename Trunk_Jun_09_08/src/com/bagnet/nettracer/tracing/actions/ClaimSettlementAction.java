/*
 * Created on Jul 9, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.actions;

import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.bmo.claims.ClaimSettlementBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Address;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Incident_Claimcheck;
import com.bagnet.nettracer.tracing.db.Item;
import com.bagnet.nettracer.tracing.db.Item_Inventory;
import com.bagnet.nettracer.tracing.db.Passenger;
import com.bagnet.nettracer.tracing.db.claims.ClaimSettlement;
import com.bagnet.nettracer.tracing.db.claims.ClaimSettlementBag;
import com.bagnet.nettracer.tracing.db.claims.SettlementBagInventory;
import com.bagnet.nettracer.tracing.forms.ClaimSettlementForm;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.tracing.utils.TracerUtils;

public class ClaimSettlementAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession();

		ClaimSettlementForm form = (ClaimSettlementForm) actionForm;
		
		ClaimSettlement claimObject = null;
		String number = (String) request.getParameter("screen");
		int num = 0;
		

		if (number != null && number.length() > 0) {
			try {
				num = Integer.parseInt(number);
			} catch (Exception e) {
				// Ignore
			}
		}


		// check session
		TracerUtils.checkSession(session);

		Agent user = (Agent) session.getAttribute("user");
		if (user == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}

		String incidentId = form.getIncident_ID();
		boolean contentsModified = false;
		
		// Create new claim settlement objects from incident
		if (request.getParameter("createclaimsettlement") != null) {
			claimObject = generateClaimObjectsFromIncidentId(incidentId, user);
			num = 1;
		} else if (request.getParameter("save1") != null) {
			saveClaimObject(form, 1, request);
			num = 1;
		} else if (request.getParameter("save2") != null) {
			saveClaimObject(form, 2, request);
			num = 2;
		} else if (request.getParameter("save3") != null) {
			saveClaimObject(form, 3, request);
			num = 3;
		} else if (request.getParameter("save4") != null) {
			saveClaimObject(form, 4, request);
			num = 4;
		} else {
			// Adding or deleting content
			
			contentsModified = addOrDeleteContents(request, form);
			if (contentsModified) {
				num = 3;
			}
		}
		
		if (num != 0) {
			if (!contentsModified) {
				claimObject = ClaimSettlementBMO.getClaimSettlement(incidentId, null);
				populateClaimSettlementForm(claimObject, form, user);
			}
			request.setAttribute("claimSettlementForm", form);
		}
		
		switch (num) {
		// Show Button
		case 1:
			return (mapping.findForward(TracingConstants.CLAIM_SETTLEMENT1));
		case 2:
			return (mapping.findForward(TracingConstants.CLAIM_SETTLEMENT2));
		case 3:
			return (mapping.findForward(TracingConstants.CLAIM_SETTLEMENT3));
		case 4:
			return (mapping.findForward(TracingConstants.CLAIM_SETTLEMENT4));

			// Show button to create claim settlement objects
		default:
			return (mapping.findForward(TracingConstants.CLAIM_SETTLEMENT));
		}

	}

	private boolean addOrDeleteContents(HttpServletRequest request,
			ClaimSettlementForm form) {
		boolean retValue = false;
		Enumeration e = request.getParameterNames();
		while (e.hasMoreElements()) {
			String parameter = (String) e.nextElement();
			
			// Add Contents
			if (parameter.indexOf("addContents") > -1) {
				try {
					StringTokenizer st = new StringTokenizer(parameter, "[]");
					if (st.hasMoreElements())
						st.nextToken();
					if (st.hasMoreElements()) {
						int bagIndex = Integer.parseInt(st.nextToken());
						int index = 0;
						for (ClaimSettlementBag bag: form.getBagList()) {
							if (index == bagIndex) {
								SettlementBagInventory o = new SettlementBagInventory();
								o.setPosition(bag.getInventory().size());
								o.setClaimSettlementBag(bag);
								bag.getInventory().add(o);
								
								retValue = true;
								break;
							} else {
								++index;
							}
						}
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			
			// Delete Contents
			if (parameter.indexOf("deleteBag") > -1) {
				StringTokenizer st = new StringTokenizer(parameter, "[]");
				SettlementBagInventory objToDelete = null;
				if (st.hasMoreElements())
					st.nextToken();
				if (st.hasMoreElements()) {
					int bagIndex = Integer.parseInt(st.nextToken());
					st.nextToken();
					int inventoryIndex = Integer.parseInt(st.nextToken());
					
					int index = 0;
					
					for (ClaimSettlementBag bag: form.getBagList()) {
						if (index == bagIndex) {
							int invIndex = 0;
							for (SettlementBagInventory inv: bag.getInventory()) {
								if (invIndex == inventoryIndex) {
									objToDelete = inv;
									retValue = true;
									break;
								} else {
									++invIndex;
								}
							}
							break;
						} else {
							++index;
						}
					}
				}
				if (objToDelete != null) {
					//objToDelete.getClaimSettlementBag().getInventory().remove(objToDelete);
					objToDelete.setFlaggedForRemoval(true);
				}
			}

		}
		return retValue;
	}
	
	private void populateClaimSettlementForm(ClaimSettlement cs, ClaimSettlementForm form, Agent a) {
		Incident inc = cs.getIncident();
		inc.set_DATEFORMAT(a.getDateformat().getFormat());
		inc.set_TIMEFORMAT(a.getTimeformat().getFormat());
		TimeZone tz = TimeZone.getTimeZone(AdminUtils.getTimeZoneById(a.getCurrenttimezone()).getTimezone());
		inc.set_TIMEZONE(tz);
		
		
		
		form.setIncident_ID(inc.getIncident_ID());
		form.setIncidentItemType(inc.getItemtype().getDescription());
		form.setIncidentStatus(inc.getStatusdesc());
		form.setIncidentCreateDate(inc.getDisplaydate());
		form.setComments(cs.getComments());
		form.setClaimAgent(cs.getClaimAgent());
		
		
		if (inc.getItemtype().getItemType_ID() == TracingConstants.LOST_DELAY) {
			for (Incident_Claimcheck ch: (Set<Incident_Claimcheck>)inc.getClaimchecks()) {
				if (ch.getOHD_ID() != null && ch.getOHD_ID().trim().length() > 0) {
					form.setMatchDetected("YES");
				}
			}
			
			for (Item item: (List<Item>)inc.getItemlist()) {
				if (item.getOHD_ID() != null && item.getOHD_ID().trim().length() > 0) {
					form.setMatchDetected("YES");
				}
			}
		}
		
		form.setRecordLocator(inc.getRecordlocator());
		form.setClaimType(cs.getClaimType());
		form.setPplcVia(cs.getPplcVia());
		
		form.setVerifyAddress(cs.isVerifyAddress());
		form.setVerifyBagColor(cs.isVerifyBagColor());
		form.setVerifyBrand(cs.isVerifyBrand());
		form.setVerifyContents(cs.isVerifyContents());
		form.setVerifyEmail(cs.isVerifyEmail());
		form.setVerifyFraudCC(cs.isVerifyFraudCC());
		form.setVerifyFraudName(cs.isVerifyFraudName());
		form.setVerifyFraudPhone(cs.isVerifyFraudPhone());
		form.setVerifyPhone(cs.isVerifyPhone());
		form.setVerifyTrace1(cs.isVerifyTrace1());
		form.setVerifyTrace2(cs.isVerifyTrace2());
		form.setVerifyTrace3(cs.isVerifyTrace3());
		
		form.setFirstContact(convertDate(cs.getFirstContact(), a));
		form.setSecondContact(convertDate(cs.getSecondContact(), a));
		form.setPplcSent(convertDate(cs.getPplcSent(), a));		
		form.setPplcDue(convertDate(cs.getPplcDue(), a));
		form.setPplcReceived(convertDate(cs.getPplcReceived(), a));
		form.setDepreciationDue(convertDate(cs.getDepreciationDue(), a));
		form.setDepreciationComplete(convertDate(cs.getDepreciationComplete(), a));
		form.setDateTakeover(convertDate(cs.getDateTakeover(), a));
		form.setOfferDue(convertDate(cs.getOfferDue(), a));
		form.setClaimType(cs.getClaimType());
		
		// Page 2
		form.setFirstName(cs.getFirstName());
		form.setLastName(cs.getLastName());
		form.setSalutation(cs.getSalutation());
		form.setAddress1(cs.getAddress1());
		form.setAddress2(cs.getAddress2());
		form.setLanguage(cs.getLanguage());
		form.setCity(cs.getCity());
		form.setZip(cs.getZip());
		form.setProvince(cs.getProvince());
		form.setCountrycode_ID(cs.getCountryCode_ID());
		form.setHomePhone(cs.getHomePhone());
		form.setBusinessPhone(cs.getBusinessPhone());
		form.setMobilePhone(cs.getMobilePhone());
		form.setPager(cs.getPager());
		form.setFax(cs.getFax());
		form.setEmail(cs.getEmail());
		form.setMembership(cs.getMembership());
		
		// Page 3
		form.setBagList(cs.getBagList());


		// Page 4
		form.setOfferSent(convertDate(cs.getOfferSent(), a));
		form.setReleaseDue(convertDate(cs.getReleaseDue(), a));
		form.setRevisitRequested(convertDate(cs.getRevisitRequested(), a));
		form.setDateStatusChange(convertDate(cs.getDateStatusChange(), a));
		form.setAmountClaimed(cs.getAmountClaimed());
		form.setAmountOffered(cs.getAmountOffered());
		form.setAuditVOOffered(cs.getAuditVOOffered());
		form.setTotalPaid(cs.getTotalPaid());
		form.setTotalPaidCertif(cs.getTotalPaidCertif());
		form.setTotalPaidVouchers(cs.getTotalPaidVouchers());
		form.setRevisitedBy(cs.getRevisitedBy());
		
		
	}

	private void saveClaimObject(ClaimSettlementForm form, int saveButton, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		Agent a = (Agent) session.getAttribute("user");
		
		Session oldSess = HibernateWrapper.getSession().openSession();
		ClaimSettlement oldCs = ClaimSettlementBMO.getClaimSettlement(form.getIncident_ID(), oldSess);
		oldSess.close();
		
		Session sess = HibernateWrapper.getSession().openSession();
		
		ClaimSettlement cs = ClaimSettlementBMO.getClaimSettlement(form.getIncident_ID(), sess);
		List bagList = null;
		
		switch (saveButton) {
			case 1:
				cs.setClaimAgent(form.getClaimAgent());
				cs.setDateTakeover(convertDate(form.getDateTakeover(), a));
				cs.setDepreciationComplete(convertDate(form.getDepreciationComplete(), a));
				cs.setDepreciationDue(convertDate(form.getDepreciationDue(), a));
				cs.setFirstContact(convertDate(form.getFirstContact(), a));

				if (form.getNewComment() != null && form.getNewComment().length() > 0)
				{
					String comments = cs.getComments();
					if (comments == null) {
						comments = new String();
					}
					if (comments.length() > 0)
						comments += "\n\n";
					String dateTime = 
					DateUtils.formatDate(TracerDateTime.getGMTDate(), TracingConstants.DISPLAY_DATETIMEFORMAT, null, null);
					comments += dateTime + " GMT  <" + a.getUsername() + ">\n";
					comments += form.getNewComment();
					cs.setComments(comments);
					form.setNewComment("");
				}
				
				cs.setPplcDue(convertDate(form.getPplcDue(), a));
				cs.setPplcReceived(convertDate(form.getPplcReceived(), a));
				cs.setPplcSent(convertDate(form.getPplcSent(), a));
				cs.setPplcVia(form.getPplcVia());
				cs.setOfferDue(convertDate(form.getOfferDue(), a));
				cs.setSecondContact(convertDate(form.getSecondContact(), a));
				cs.setVerifyAddress(form.isVerifyAddress());
				cs.setVerifyBagColor(form.isVerifyBagColor());
				cs.setVerifyBrand(form.isVerifyBrand());
				cs.setVerifyContents(form.isVerifyContents());
				cs.setVerifyEmail(form.isVerifyEmail());
				cs.setVerifyFraudCC(form.isVerifyFraudCC());
				cs.setVerifyFraudName(form.isVerifyFraudName());
				cs.setVerifyFraudPhone(form.isVerifyFraudPhone());
				cs.setVerifyPhone(form.isVerifyPhone());
				cs.setVerifyTrace1(form.isVerifyTrace1());
				cs.setVerifyTrace2(form.isVerifyTrace2());
				cs.setVerifyTrace3(form.isVerifyTrace3());

				break;
			case 2:
				cs.setAddress1(form.getAddress1());
				cs.setAddress2(form.getAddress2());
				cs.setBusinessPhone(form.getBusinessPhone());
				cs.setCity(form.getCity());
				cs.setCountryCode_ID(form.getCountrycode_ID());
				cs.setEmail(form.getEmail());
				cs.setFax(form.getFax());
				cs.setFirstName(form.getFirstName());
				cs.setHomePhone(form.getHomePhone());
				cs.setLanguage(form.getLanguage());
				cs.setLastName(form.getLastName());
				cs.setMembership(form.getMembership());
				cs.setMobilePhone(form.getMobilePhone());
				cs.setPager(form.getPager());
				cs.setProvince(form.getProvince());
				cs.setSalutation(form.getSalutation());
				cs.setState_ID(form.getState_ID());
				
				break;
			case 3:
								
				bagList = form.getBagList();
				for (int i=0; i<bagList.size(); ++i) {
					ClaimSettlementBag bag = (ClaimSettlementBag)bagList.get(i);
					bag.setPosition(i);
					for (int j=0; j<bag.getInventory().size(); ++ j) {
						SettlementBagInventory inv = bag.getInventory().get(j);
						inv.setPosition(j);
					}
				}
				cs.setBagList(bagList);
				break;
			case 4:
				cs.setAmountClaimed(form.getAmountClaimed());
				cs.setAmountOffered(form.getAmountClaimed());
				cs.setAuditVOOffered(form.getAuditVOOffered());
				cs.setDateStatusChange(convertDate(form.getDateStatusChange(), a));
				cs.setOfferSent(convertDate(form.getOfferSent(), a));
				cs.setOfferSentVia(form.getOfferSentVia());
				cs.setReleaseDue(convertDate(form.getReleaseDue(), a));
				cs.setTotalPaid(form.getTotalPaid());
				cs.setTotalPaidCertif(form.getTotalPaidCertif());
				cs.setTotalPaidVouchers(form.getTotalPaidVouchers());
				cs.setRevisitRequested(convertDate(form.getRevisitRequested(), a));
				cs.setRevisitedBy(form.getRevisitedBy());
				break;
		}
		
			
		Transaction t = null;
		try {
			t = sess.beginTransaction();
			
			ArrayList<SettlementBagInventory> removeList = new ArrayList<SettlementBagInventory>();
			for (ClaimSettlementBag bag: cs.getBagList()) {
				for (SettlementBagInventory inv: bag.getInventory()) {
					if (inv.isFlaggedForRemoval()) {
						removeList.add(inv);
					}
				}
			}

			for (SettlementBagInventory item: removeList) {
				item.getClaimSettlementBag().getInventory().remove(item);
				SettlementBagInventory sbi = (SettlementBagInventory) sess.load(SettlementBagInventory.class, item.getInventoryId());
				sbi.getClaimSettlementBag().getInventory().remove(sbi);
				sess.delete(sbi);
			}
			
			sess.merge(cs);
			
			t.commit();
			ClaimSettlementBMO.auditClaimSettlement(cs, sess, a);
		} catch (Exception e) {
			e.printStackTrace();
			if (t != null) {
				t.rollback();
			}
		}
		
		
		
		sess.close();
		
	}

	private void clearIds(ClaimSettlement cs) {
		cs.setClaimSettlementId(0);
		for (ClaimSettlementBag bag: cs.getBagList()) {
			bag.setBagId(0);
			for (SettlementBagInventory inv: bag.getInventory()) {
				inv.setInventoryId(0);
			}
		}

	}

	private ClaimSettlement generateClaimObjectsFromIncidentId(String incidentId, Agent agent) {
		
		Session sess = HibernateWrapper.getSession().openSession();
		Incident incident = IncidentBMO.getIncidentByID(incidentId, sess);
		
		ClaimSettlement cs = new ClaimSettlement();
		cs.setIncident(incident);
		
		if (incident.getPassenger_list().size() > 0) {
			Passenger pax = (Passenger) incident.getPassenger_list().get(0);
			
			if (pax != null) {
				cs.setSalutation(pax.getSalutation());
				cs.setFirstName(pax.getFirstname());
				cs.setLastName(pax.getLastname());
				cs.setMembership(pax.getAirlinememnumber());
				
				Address addr = (Address) pax.getAddress(0);
				
				if (addr != null) {
					cs.setAddress1(addr.getAddress1());
					cs.setAddress2(addr.getAddress2());
					cs.setBusinessPhone(addr.getWorkphone());
					cs.setCity(addr.getCity());
					cs.setCountryCode_ID(addr.getCountrycode_ID());
					cs.setEmail(addr.getEmail());
					cs.setFax(addr.getAltphone());
					cs.setHomePhone(addr.getHomephone());
					cs.setMobilePhone(addr.getMobile());
					cs.setPager(addr.getPager());
					cs.setProvince(addr.getProvince());
					cs.setState_ID(addr.getState_ID());
					cs.setZip(addr.getZip());
					cs.setEmail(addr.getEmail());
				}
			}
		}	
	
		String claimType = incident.getItemtype().getDescription();
		
		ArrayList bagList = new ArrayList<ClaimSettlementBag>();
		cs.setBagList(bagList);
		int bagIndex = 0;
		for (Item item: (List<Item>) incident.getItemlist()) {
			
			ClaimSettlementBag bag = new ClaimSettlementBag();
			
			bag.setClaimSettlement(cs);
			bag.setColor(item.getColor());
			bag.setType(item.getBagtype());
			bag.setManufacturer(item.getManufacturer());
			bag.setPosition(bagIndex++);
			
			List<SettlementBagInventory> bagInventory = new ArrayList<SettlementBagInventory>();
			bag.setInventory(bagInventory);
			
			int invIndex = 0;
			for (Item_Inventory inventory: (List<Item_Inventory>) item.getInventorylist()) {
				if (inventory.getDescription() != null && inventory.getDescription().trim().length() > 0) {
					SettlementBagInventory bagItem = new SettlementBagInventory();
					bagItem.setPosition(invIndex++);
					bagItem.setCategoryType_ID(inventory.getCategorytype_ID());
					bagItem.setDescription(inventory.getDescription());
					bagItem.setClaimSettlementBag(bag);
					bagInventory.add(bagItem);
				}
			}
			
			bagList.add(bag);
			
		}
		
		
		ClaimSettlementBMO.saveClaimSettlement(cs, sess, agent);
		
		sess.close();
		return cs;
	}
	
	private Date convertDate(String s, Agent a) {
		return DateUtils.convertToDate(s, a.getDateformat().getFormat(), null);
	}
	
	private String convertDate(Date d, Agent a) {
		return DateUtils.formatDate(d, a.getDateformat().getFormat(), null, null);
	}
	
	
}