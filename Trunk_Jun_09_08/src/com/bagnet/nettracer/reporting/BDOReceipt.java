/*
 * Created on Aug 10, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.reporting;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.util.MessageResources;

import com.bagnet.nettracer.tracing.bmo.DelivercompanyBMO;
import com.bagnet.nettracer.tracing.bmo.ReportBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.BDO_Passenger;
import com.bagnet.nettracer.tracing.db.DeliverCompany;
import com.bagnet.nettracer.tracing.db.Deliver_ServiceLevel;
import com.bagnet.nettracer.tracing.db.Item;
import com.bagnet.nettracer.tracing.dto.BDO_Receipt_DTO;
import com.bagnet.nettracer.tracing.forms.BDOForm;
import com.bagnet.nettracer.tracing.utils.BDOUtils;

/**
 * @author Matt
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class BDOReceipt {
	private static Logger logger = Logger.getLogger(BDOReceipt.class);

	/**
	 *  
	 */
	public BDOReceipt() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static String createReport(BDOForm theform, ServletContext sc, HttpServletRequest request, int outputtype, String language) {
		try {
			
			// If a BDO_ID is explicitly provided, populate the form with it.
			if (request.getParameter("bdo_id") != null) {
				BDOUtils.findBDO(Integer.parseInt(request.getParameter("bdo_id")), theform, request);
				theform = (BDOForm) request.getSession().getAttribute("BDOForm");
			}

			Map parameters = new HashMap();
			ResourceBundle myResources = ResourceBundle.getBundle("com.bagnet.nettracer.tracing.resources.ApplicationResources", new Locale(language));
			parameters.put("REPORT_RESOURCE_BUNDLE", myResources);
			parameters.put("bdo_id",theform.getBDO_ID_ref());
						
			File logo = new File(sc.getRealPath("/") + "reports/bdologo.gif");
			if (logo.exists()) {
				parameters.put("logo", logo.getAbsolutePath());
			}

			ArrayList al = new ArrayList();

			BDO_Passenger pa = (BDO_Passenger) theform.getPassenger(0);

			for (int j = 0; j < 3; j++) {
				BDO_Receipt_DTO brd = new BDO_Receipt_DTO();
				brd.setToname((pa.getFirstname() != null ? (pa.getFirstname() + " ") : "") + (pa.getLastname() != null ? pa.getLastname() : ""));
				brd.setAddress(pa.getAddress1());
				brd.setApt(pa.getAddress2());
				brd.setCity(pa.getCity());
				brd.setState(pa.getState_ID());
				brd.setZip(pa.getZip());
				brd.setRefNum(theform.getIncident_ID());

				String phno = pa.getHomephone();
				if (phno == null || phno.length() == 0)
					phno = pa.getMobile();

				brd.setPhone(phno);

				brd.setNumbags(theform.getBagcount() + "");

				StringBuffer sb = new StringBuffer();
				StringBuffer sb2 = new StringBuffer();
				for (int i = 0; i < theform.getItemlist().size(); i++) {
					if (((Item) theform.getItemlist().get(i)).getClaimchecknum() != null
							&& ((Item) theform.getItemlist().get(i)).getClaimchecknum().length() > 0) {
						sb.append(((Item) theform.getItemlist().get(i)).getClaimchecknum().trim());
						sb.append(",");
					}
					if (((Item) theform.getItemlist().get(i)).getColor() != null) {
						sb2.append(((Item) theform.getItemlist().get(i)).getColor());
						sb2.append(" ");
					}
					if (((Item) theform.getItemlist().get(i)).getBagtype() != null) {
						sb2.append(((Item) theform.getItemlist().get(i)).getBagtype());
						sb2.append(",");
					}
				}
				if (sb.length() > 0)
					brd.setBagtag(sb.toString().substring(0, sb.toString().length() - 1));

				if (sb2.length() > 0)
					brd.setDescription(sb2.toString().substring(0, sb2.toString().length() - 1));

				brd.setRemarks("");
				brd.setAgent(theform.getAgent().getUsername());
				brd.setDate1(theform.getDispcreatetime());
				brd.setDate2(theform.getDispdeliverydate());
				brd.setReceivedby("");
				brd.setInstructions(theform.getDelivery_comments());

				DeliverCompany dc = BDOUtils.getDeliverCompany(theform.getDelivercompany_ID());
				if (dc != null)
					brd.setVendor(dc.getName());

				StringBuilder charges = new StringBuilder("");
				if (theform.getCurrency() != null) {
					charges.append(theform.getCurrency());
				}
				
				if (theform.getCost() != null) {
					charges.append(theform.getCost());
				}
				
				try {
					brd.setCharges(charges.toString());
				} catch (Exception e) {
					brd.setCharges("");
				}
				al.add(brd);
				
				Deliver_ServiceLevel sl = DelivercompanyBMO.getServiceLevel(theform.getServicelevel_ID());
				if (sl != null && sl.getDescription() != null) 
					brd.setServiceLevel(sl.getDescription());	
				
			}

			ReportBMO rbmo = new ReportBMO(request);
			return rbmo.getReportFile(al, parameters, "BDO_Receipt", sc.getRealPath("/"), outputtype);

		} catch (Exception e) {
			logger.error("unable to create claim prorate report: " + e);
			e.printStackTrace();
			return null;
		}
	}
}