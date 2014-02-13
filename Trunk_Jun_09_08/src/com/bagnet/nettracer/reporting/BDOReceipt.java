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
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.TracerProperties;

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
			MessageResources messages = MessageResources.getMessageResources("com.bagnet.nettracer.tracing.resources.ApplicationResources");
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
				brd.setDate1(DateUtils.formatDate(theform.getCreatedate(), theform.getAgent().getDateformat().getFormat(), null, null));				
				String RefInfo=messages.getMessage(TracerProperties.BDO_LABEL_REFERENCE_NUMBER)+": ";
				if(theform.getIncident_ID()!=null)
					RefInfo+=theform.getIncident_ID();
				else if (theform.getOHD_ID()!=null)
					RefInfo+=theform.getOHD_ID();
				brd.setRefNum(RefInfo);
				brd.setAddress(messages.getMessage(TracerProperties.BDO_LABEL_DELIVERY_ADDRESS)+":");
				String CustInfo=(pa.getFirstname() != null ? (pa.getFirstname() + " ") : "") + (pa.getLastname() != null ? pa.getLastname() : "")+"\r  "+
						pa.getAddress1()+" "+pa.getAddress2()+"\r  "+pa.getCity()+", ";
				
				if(pa.getCountrycode_ID()!=null && pa.getCountrycode_ID().equals(TracingConstants.US_COUNTRY_CODE)){
					if(pa.getState_ID()!=null && pa.getState_ID().length()>0) {
						CustInfo+=pa.getState_ID()+", ";
					}
				} else if(pa.getProvince()!=null && pa.getProvince().length()>0) {
					CustInfo+=pa.getProvince()+", ";
				}
				
				if(pa.getZip()!=null && pa.getZip().length()>0){
					CustInfo+=pa.getZip();
				}
				
				if(pa.getCountrycode_ID()!=null && pa.getCountrycode_ID().length()>0 && pa.getCountrycode_ID()!=TracingConstants.US_COUNTRY_CODE ){
					CustInfo+=", "+pa.getCountrycode_ID();
				}
				CustInfo+="\r\r";
				
				String phno = pa.getHomephone();
				String wphno = pa.getWorkphone();
				String mphno = pa.getMobile();
				if (phno != null && phno.length() != 0)
					CustInfo+=messages.getMessage(TracerProperties.BDO_LABEL_PHONE_NUMBER)+": "+phno+"\r";
				if (wphno != null && wphno.length() != 0)
					CustInfo+=messages.getMessage(TracerProperties.BDO_LABEL_WORK_NUMBER)+": "+wphno+"\r";
				if(mphno!=null && mphno.length()!=0)
					CustInfo+=messages.getMessage(TracerProperties.BDO_LABEL_MOBILE_NUMBER)+": "+mphno+"\r";
				if(pa.getHotel()!=null && pa.getHotel().length()>0)
					CustInfo+=messages.getMessage(TracerProperties.BDO_LABEL_HOTEL)+": "+pa.getHotel(); 
				if(pa.getHotelphone()!=null && pa.getHotelphone().length()>0){
					CustInfo+="\r"+messages.getMessage(TracerProperties.BDO_LABEL_HOTEL_NUMBER)+": "+pa.getHotelphone();
				}
				
				brd.setCustomerinfo(CustInfo);
				
				brd.setToname((pa.getFirstname() != null ? (pa.getFirstname() + " ") : "") + (pa.getLastname() != null ? pa.getLastname() : ""));
				brd.setApt(pa.getAddress2());
				brd.setCity(pa.getCity());
				brd.setState(pa.getState_ID());
				brd.setZip(pa.getZip());
				
				brd.setBagInfo(messages.getMessage(TracerProperties.BDO_LABEL_BAG_INFORMATION));
				brd.setCustInfo(messages.getMessage(TracerProperties.BDO_LABEL_CUSTOMER_INFORMATION));
				brd.setDelInfo(messages.getMessage(TracerProperties.BDO_LABEL_DELIVERY_INFORMATION));
				brd.setDelIns(messages.getMessage(TracerProperties.BDO_LABEL_DELIVERY_INSTRUCTIONS));				

				brd.setPhone(phno);
				if(theform.getBagcount()<=10){
					brd.setNumbags(messages.getMessage(TracerProperties.BDO_LABEL_NUMBER_BAGS)+": "+theform.getBagcount() + "");
				} else {
					brd.setNumbags(messages.getMessage(TracerProperties.BDO_LABEL_NUMBER_BAGS)+": 10");
				}

				String deliInfo="";

				String bagInfo="";
				String bagInfo2="";

				if(theform.getOhd()!=null && theform.getBagcount()==0) {
					bagInfo+=messages.getMessage(TracerProperties.BDO_LABEL_NUMBER_BAGS)+": 1\r\r";
				} else {
					bagInfo+=messages.getMessage(TracerProperties.BDO_LABEL_NUMBER_BAGS)+": "+theform.getBagcount() + "\r";
				}
				bagInfo2+="\r";
				StringBuffer sb = new StringBuffer();
				StringBuffer sb2 = new StringBuffer();
				if(theform.getItemlist().size()>0){
					for (int i = 0; i < theform.getItemlist().size(); i++) {
						if(i<=4){
							bagInfo+=(i+1)+":\t";
							if (((Item) theform.getItemlist().get(i)).getClaimchecknum() != null
									&& ((Item) theform.getItemlist().get(i)).getClaimchecknum().length() > 0) {
								bagInfo+=messages.getMessage(TracerProperties.BDO_LABEL_BAG_NUMBER)+": "+((Item) theform.getItemlist().get(i)).getClaimchecknum().trim()+"\t";
								sb.append(((Item) theform.getItemlist().get(i)).getClaimchecknum().trim());
								sb.append("\r");
							}
							if (((Item) theform.getItemlist().get(i)).getColor() != null) {
								bagInfo+=messages.getMessage(TracerProperties.BDO_LABEL_COLOR)+": "+((Item) theform.getItemlist().get(i)).getColor()+"\t";
								sb2.append(((Item) theform.getItemlist().get(i)).getColor());
								sb2.append(" ");
							}
							if (((Item) theform.getItemlist().get(i)).getBagtype() != null) {
								bagInfo+=messages.getMessage(TracerProperties.BDO_LABEL_TYPE)+": "+((Item) theform.getItemlist().get(i)).getBagtype();
								sb2.append(((Item) theform.getItemlist().get(i)).getBagtype());
								sb2.append(", ");
							}
							bagInfo+="\r";
						} else if (i<=9) {
							bagInfo2+=(i+1)+":\t";
							if (((Item) theform.getItemlist().get(i)).getClaimchecknum() != null
									&& ((Item) theform.getItemlist().get(i)).getClaimchecknum().length() > 0) {
								bagInfo2+=messages.getMessage(TracerProperties.BDO_LABEL_BAG_NUMBER)+": "+((Item) theform.getItemlist().get(i)).getClaimchecknum().trim()+"\t";
								sb.append(((Item) theform.getItemlist().get(i)).getClaimchecknum().trim());
								sb.append("\r");
							}
							if (((Item) theform.getItemlist().get(i)).getColor() != null) {
								bagInfo2+=messages.getMessage(TracerProperties.BDO_LABEL_COLOR)+": "+((Item) theform.getItemlist().get(i)).getColor()+"\t";
								sb2.append(((Item) theform.getItemlist().get(i)).getColor());
								sb2.append(" ");
							}
							if (((Item) theform.getItemlist().get(i)).getBagtype() != null) {
								bagInfo2+=messages.getMessage(TracerProperties.BDO_LABEL_TYPE)+": "+((Item) theform.getItemlist().get(i)).getBagtype();
								sb2.append(((Item) theform.getItemlist().get(i)).getBagtype());
								sb2.append(", ");
							}
							bagInfo2+="\r";
						}
					}
				} else if(theform.getOhd()!=null) {
					bagInfo+="1:\t";
					if (theform.getOhd().getClaimnum() != null
							&& (theform.getOhd().getClaimnum().length() > 0)) {
						bagInfo+=messages.getMessage(TracerProperties.BDO_LABEL_BAG_NUMBER)+": "+theform.getOhd().getClaimnum().trim()+"\t";
						sb.append(theform.getOhd().getClaimnum().trim());
						sb.append("\r");
					}
					if (theform.getOhd().getColor() != null) {
						bagInfo+=messages.getMessage(TracerProperties.BDO_LABEL_COLOR)+": "+theform.getOhd().getColor()+"\t";
						sb2.append(theform.getOhd().getColor());
						sb2.append(" ");
					}
					if (theform.getOhd().getType() != null) {
						bagInfo+=messages.getMessage(TracerProperties.BDO_LABEL_TYPE)+": "+theform.getOhd().getType();
						sb2.append(theform.getOhd().getType());
						sb2.append(", ");
					}
					bagInfo+="\r";
				}
				
//				if (sb.length() > 0)
//					brd.setBagtag(sb.toString().substring(0, sb.toString().length() - 1));
//				else
//					brd.setBagtag("");
				if (sb2.length() > 0)
					brd.setDescription(sb2.toString().substring(0, sb2.toString().length() - 1));
				brd.setBagtag(bagInfo);
				brd.setBagtag2(bagInfo2);
				brd.setRemarks("");
				brd.setAgent(theform.getAgent().getUsername());
				brd.setReceivedby(messages.getMessage(TracerProperties.BDO_LABEL_RECEIVED_BY)); //Why blank?
				brd.setInstructions(theform.getDelivery_comments());

				DeliverCompany dc = BDOUtils.getDeliverCompany(theform.getDelivercompany_ID());
				if (dc != null){
					brd.setVendor(dc.getName());
					deliInfo+=messages.getMessage(TracerProperties.BDO_LABEL_DELIVERY_COMPANY)+": "+dc.getName()+"\r";
				}

				StringBuilder charges = new StringBuilder("");
				if (theform.getCost() != null) {
					charges.append(theform.getCost());
				}
				
				if (theform.getCurrency() != null) {
					charges.append(" "+theform.getCurrency());
					
				}
				
				try {
					brd.setCharges(charges.toString());
					if(charges.length()>0){
						deliInfo+=messages.getMessage(TracerProperties.BDO_LABEL_CHARGES)+": "+charges.toString()+"\r";
					}
				} catch (Exception e) {
					brd.setCharges("");
					deliInfo+="\r";
				}
				
				Deliver_ServiceLevel sl = DelivercompanyBMO.getServiceLevel(theform.getServicelevel_ID());
				if (sl != null && sl.getDescription() != null) 
				{	brd.setServiceLevel(sl.getDescription());	
					deliInfo+=messages.getMessage(TracerProperties.BDO_LABEL_SERVICE_LEVEL)+": "+sl.getDescription();
				}
				//add pickup date and time to deliver info if applicable
				String pickupDateAndTime = theform.getDisppickupdate() + " " + theform.getDisppickuptime();
				if (pickupDateAndTime != null && pickupDateAndTime.trim().length() > 0) 
				{	
					brd.setDate2(pickupDateAndTime);
					deliInfo+="\r";
					deliInfo+=messages.getMessage(TracerProperties.BDO_LABEL_PICKUPDATE_AND_TIME)+": "+ brd.getDate2();
				}	
				
				brd.setDeliveryinfo(deliInfo); //Add this too				
				al.add(brd);						
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