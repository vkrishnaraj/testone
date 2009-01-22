/*
 * Created on Jul 9, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.actions;

import java.util.LinkedHashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.Session;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.bmo.claims.ClaimSettlementBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Address;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Item;
import com.bagnet.nettracer.tracing.db.Item_Inventory;
import com.bagnet.nettracer.tracing.db.Passenger;
import com.bagnet.nettracer.tracing.db.claims.ClaimSettlement;
import com.bagnet.nettracer.tracing.db.claims.ClaimSettlementBag;
import com.bagnet.nettracer.tracing.db.claims.SettlementBagInventory;
import com.bagnet.nettracer.tracing.forms.ClaimSettlementForm;
import com.bagnet.nettracer.tracing.utils.TracerUtils;

public class ClaimSettlementAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession();

		ClaimSettlementForm form = (ClaimSettlementForm) actionForm;
		
		String number = (String) request.getParameter("screen");
		int num = 0;
		String incident_id = null;

		if (request.getParameter("createclaimsettlement") != null) {
			session.setAttribute("claimsettlementon", "claimssettlement");
			num = 1;
		} else if (number != null && number.length() > 0) {
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

		// Create new claim settlement objects from incident
		if (request.getParameter("createclaimsettlement") != null) {
			String incidentId = form.getIncident_ID();
			ClaimSettlement claimObject = generateClaimObjectsFromIncidentId(incidentId);

			num = 1;
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
			System.out.println(form.getIncident_ID());
			System.out.println((String) request.getParameter("incident_ID"));
			return (mapping.findForward(TracingConstants.CLAIM_SETTLEMENT));
		}

	}

	private ClaimSettlement generateClaimObjectsFromIncidentId(String incidentId) {
		
		Session sess = HibernateWrapper.getSession().openSession();
		Incident incident = IncidentBMO.getIncidentByID(incidentId, sess);
		
		ClaimSettlement cs = new ClaimSettlement();
		
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
				}
			}
		}	
	
		String claimType = incident.getItemtype().getDescription();
		

		LinkedHashSet<ClaimSettlementBag> bagList = new LinkedHashSet<ClaimSettlementBag>();
		cs.setBagList(bagList);
		for (Item item: (List<Item>) incident.getItemlist()) {
			
			ClaimSettlementBag bag = new ClaimSettlementBag();
			
			bag.setClaimSettlement(cs);
			bag.setColor(item.getColor());
			bag.setType(item.getBagtype());
			bag.setManufacturer(item.getManufacturer());
			
			LinkedHashSet<SettlementBagInventory> bagInventory = new LinkedHashSet<SettlementBagInventory>();
			bag.setInventory(bagInventory);
			
			for (Item_Inventory inventory: (List<Item_Inventory>) item.getInventorylist()) {
				SettlementBagInventory bagItem = new SettlementBagInventory();
				bagItem.setCategoryType_ID(inventory.getCategorytype_ID());
				bagItem.setDescription(inventory.getDescription());
				bagInventory.add(bagItem);
			}
			
			bagList.add(bag);
			
		}
		
		ClaimSettlementBMO.saveClaimSettlement(cs, sess);
		
		sess.close();
		return cs;
	}
}