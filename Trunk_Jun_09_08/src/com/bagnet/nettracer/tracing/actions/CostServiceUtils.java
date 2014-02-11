package com.bagnet.nettracer.tracing.actions;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Set;
import java.util.TimeZone;

import javax.ejb.Stateless;

import org.apache.axis2.AxisFault;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.tempuri.Address;
import org.tempuri.ArrayOfItemData;
import org.tempuri.BDOAddDocument;
import org.tempuri.BDOAddDocument.BDOAdd;
import org.tempuri.BDOAddResponseDocument;
import org.tempuri.BDOAddResponseDocument.BDOAddResponse;
import org.tempuri.BagDeliveryType;
import org.tempuri.CalculateDeliveryCostDocument;
import org.tempuri.CalculateDeliveryCostDocument.CalculateDeliveryCost;
import org.tempuri.CalculateDeliveryCostResponseDocument;
import org.tempuri.CalculateDeliveryCostResponseDocument.CalculateDeliveryCostResponse;
import org.tempuri.ErrorCodeEnum;
import org.tempuri.ErrorCodeEnum.Enum;
import org.tempuri.ItemData;
import org.tempuri.RetVal;
import org.tempuri.SouthwestAirlinesCalculateDeliveryCostRequest;
import org.tempuri.SouthwestAirlinesCalculateDeliveryCostResponse;
import org.tempuri.SouthwestAirlinesStub;

import com.bagnet.nettracer.tracing.bmo.CategoryBMO;
import com.bagnet.nettracer.tracing.bmo.DelivercompanyBMO;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.BDO;
import com.bagnet.nettracer.tracing.db.BDO_Passenger;
import com.bagnet.nettracer.tracing.db.Category;
import com.bagnet.nettracer.tracing.db.DeliverCompany;
import com.bagnet.nettracer.tracing.db.DeliveryIntegrationType;
import com.bagnet.nettracer.tracing.db.Item;
import com.bagnet.nettracer.tracing.forms.BDOForm;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.HibernateUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;

@Stateless
public class CostServiceUtils {

	static private int DEFAULT_RES_WS_TIMEOUT = 30000;
	
	/**
	 * Method to Call BDOAdd Web Service method - Unused.
	 */
	public static RetVal bdoAdd(BDO bdo, Agent user){
		DeliverCompany dc=null;
		if(bdo!=null)
			dc= DelivercompanyBMO.getDeliveryCompany(bdo.getDelivercompany().getDelivercompany_ID() + "");
		if(dc!=null && dc.getDelivery_integration_type().equals(DeliveryIntegrationType.SERV)){
			// If there has been a previous successful message, error out.

			String noticeText = null;
			 
			if (bdo.getDelivery_integration_type() != null) {
				
				int originalCompanyId = bdo.getIntegrationDelivercompany_ID();
				DeliverCompany integrationDeliveryCompany = null;
				
				if (originalCompanyId != 0) {
					integrationDeliveryCompany = DelivercompanyBMO.getDeliveryCompany("" + originalCompanyId);
				}

				// If the delivery companies have changed, send response.
				if (dc.getDelivercompany_ID() != integrationDeliveryCompany.getDelivercompany_ID()) {
					noticeText = TracerUtils.getText("delivercompany.integration.alreadysubmitted.html", user);
				}
			}
			if(noticeText==null){
				SouthwestAirlinesStub stub=null;
				Logger logger = Logger.getLogger(CostServiceUtils.class);
				try {
					stub = new SouthwestAirlinesStub(PropertyBMO.getValue(PropertyBMO.SWA_SERVICE_ADDRESS_ENDPOINT));
		
					int timeout = PropertyBMO.getValueAsInt(PropertyBMO.RES_WS_TIMEOUT);
					stub._getServiceClient().getOptions().setProperty(HTTPConstants.SO_TIMEOUT, timeout==0?DEFAULT_RES_WS_TIMEOUT:timeout);
					stub._getServiceClient().getOptions().setProperty(HTTPConstants.CONNECTION_TIMEOUT, timeout==0?DEFAULT_RES_WS_TIMEOUT:timeout);
				} catch (AxisFault e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				BDOAddDocument validate=BDOAddDocument.Factory.newInstance();
				BDOAdd request=validate.addNewBDOAdd();
				request.setVendorImportCode(dc.getIntegration_key());
				request.setAirportCode(user.getStation().getStationcode());
				
				String airlineCode = user.getCompanycode_ID();
				
				request.setAirlineCode(airlineCode);
				request.setResponsibleAirlineCode(airlineCode);
				String bdoNum = bdo.getBDO_ID_ref().substring(3);
				request.setBDONumber(bdoNum);
				BDO_Passenger pax = bdo.getPassenger(0);
				request.setLastName(pax.getLastname());
				request.setMiddleName(pax.getMiddlename());
				request.setFirstName(pax.getFirstname());
				request.setEmailAddress(pax.getEmail());
		
				request.setLocalPhone(pax.getHotel());
				request.setPermanentPhone(pax.getHomephone());
				request.setMobilePhone(pax.getMobile());
				
				request.setAddressLine1(pax.getAddress1());
				request.setAddressLine2(pax.getAddress2());
				request.setZipCode(pax.getZip());
				request.setCity(pax.getCity());
				request.setState(pax.getState_ID());
				
				request.setFlightNumber("");
				request.setFlightDateSpecified(false);
				request.setRoute("");
				request.setServiceLevel(DelivercompanyBMO.getServiceLevel(bdo.getServicelevel().getServicelevel_ID()).getService_code());
		
				Calendar claimCal = Calendar.getInstance();
				if (bdo.getIncident() != null) {
					request.setClaimReferenceNumber(bdo.getIncident().getIncident_ID());
					bdo.getIncident().set_DATEFORMAT(TracingConstants.DISPLAY_DATEFORMAT);
					bdo.getIncident().set_TIMEFORMAT(TracingConstants.DISPLAY_TIMEFORMAT_B);
					bdo.getIncident().set_TIMEZONE(TimeZone.getTimeZone("GMT")); //agent.getCurrenttimezone()
					claimCal.setTime(DateUtils.convertToDate(bdo.getIncident().getDisplaydate(), bdo.getIncident().get_DATEFORMAT() + " " + bdo.getIncident().get_TIMEFORMAT(), null,null));
					request.setClaimDate(fixTimeZone(claimCal,"GMT"));
				} else if (bdo.getOhd() != null){
					request.setClaimReferenceNumber(bdo.getOhd().getOHD_ID());
					bdo.getOhd().set_DATEFORMAT(TracingConstants.DISPLAY_DATEFORMAT);
					bdo.getOhd().set_TIMEFORMAT(TracingConstants.DISPLAY_TIMEFORMAT_B);
					bdo.getOhd().set_TIMEZONE(TimeZone.getTimeZone("GMT")); //agent.getCurrenttimezone()
					claimCal.setTime(DateUtils.convertToDate(bdo.getOhd().getDisplaydate(), bdo.getOhd().get_DATEFORMAT() + " " + bdo.getOhd().get_TIMEFORMAT(), null,null));
					request.setClaimDate(fixTimeZone(claimCal,"GMT"));
				}
				request.setClaimDateSpecified(true);
		
				Calendar deliveryCal = Calendar.getInstance();
				if (bdo.getDeliverydate() != null) {
					deliveryCal.setTime(bdo.getDeliverydate());
					request.setDeliveryDateSpecified(true);
				} else {
					request.setDeliveryDateSpecified(false);
				}
				
				StringBuffer remarks = new StringBuffer();
				remarks.append("Field Office: " + dc.getName() + "\n");
				remarks.append(bdo.getDelivery_comments());
				
				request.setDeliveryRemarks(remarks.toString());
		
				bdo.set_DATEFORMAT(TracingConstants.DISPLAY_DATEFORMAT);
				bdo.set_TIMEFORMAT(TracingConstants.DISPLAY_TIMEFORMAT_B);
				bdo.set_TIMEZONE(TimeZone.getTimeZone("GMT")); //agent.getCurrenttimezone()
				Calendar createCal = Calendar.getInstance();
				createCal.setTime(DateUtils.convertToDate(bdo.getDispcreatetime(), bdo.get_DATEFORMAT() + " " + bdo.get_TIMEFORMAT(), null,null));
				request.setCreatedDate(fixTimeZone(createCal,"GMT"));
				
				createCal.setTime(DateUtils.convertToDate(bdo.getDisppickupdate(), bdo.get_DATEFORMAT() + " " + bdo.get_TIMEFORMAT(), null,null));
				request.setPickUpDate(fixTimeZone(createCal,"GMT"));
				
				ArrayOfItemData array = request.addNewItemsData();
		
				if (bdo.getItems() != null && bdo.getIncident() != null) {
					for (Item item: (Set<Item>)bdo.getItems()) {
						ItemData id = array.addNewItemData();
						id.setColor(item.getColor());
						if (item.getClaimchecknum() != null && item.getClaimchecknum().trim().length() > 0) {
							id.setTag(item.getClaimchecknum());	
						} else {
							id.setTag("notAvailable");
						}
						
						id.setItemType(item.getBagtype());
						id.setBrand(item.getManuname());
					}
				}
				request.setBDONumber(bdo.getBDO_ID_ref());
			    try {

					logger.info(validate);
			    	BDOAddResponseDocument replyDoc=stub.bDO_Add(validate);
					logger.info(replyDoc);
			    	
					// Initialize the service
			    	// This is the call to the web service
			    	BDOAddResponse reply = replyDoc.getBDOAddResponse();
					RetVal response= reply.getBDOAddResult();
					
					if(response.getErrorCodes()==null || (response.getErrorCodes()!=null && response.getErrorCodes().sizeOfErrorCodeArray()==0)){
						
						//TODO: Determine what to put in place of the OrderID as you don't really get any further information beyond bdoNumber and errorCodes
						bdo.setDelivery_integration_id(String.valueOf(reply.getBDOAddResult().getOrderID()));
						bdo.setDelivery_integration_type(DeliveryIntegrationType.SERV);
						bdo.setIntegrationDelivercompany_ID(dc.getDelivercompany_ID());
						HibernateUtils.save(bdo);
					}
					
					
					return response;
				} catch (Exception e) {
		            e.printStackTrace();
				}
			}
		}
		return null;
	}	


	/**
	 * Method to Call calculateDeliveryCost Web Service method.
	 * Return BDO Form to display to user, but doesn't save BDO
	 */
	public static BDOForm calculateDeliveryCost(BDOForm form, Agent user,  ActionMessages messages){
		SouthwestAirlinesStub stub=null;
		Logger logger = Logger.getLogger(CostServiceUtils.class);
		try {
			stub = new SouthwestAirlinesStub(PropertyBMO.getValue(PropertyBMO.SWA_SERVICE_ADDRESS_ENDPOINT));

			int timeout = PropertyBMO.getValueAsInt(PropertyBMO.RES_WS_TIMEOUT);
			stub._getServiceClient().getOptions().setProperty(HTTPConstants.SO_TIMEOUT, timeout==0?DEFAULT_RES_WS_TIMEOUT:timeout);
			stub._getServiceClient().getOptions().setProperty(HTTPConstants.CONNECTION_TIMEOUT, timeout==0?DEFAULT_RES_WS_TIMEOUT:timeout);
		} catch (AxisFault e2) {
			e2.printStackTrace();
		}
		CalculateDeliveryCostDocument validate=CalculateDeliveryCostDocument.Factory.newInstance();
		CalculateDeliveryCost req2=validate.addNewCalculateDeliveryCost();
		SouthwestAirlinesCalculateDeliveryCostRequest req=req2.addNewRequest();

		DeliverCompany dc=null;
		if(form!=null && form.getDelivercompany_ID()!=0){
			dc= DelivercompanyBMO.getDeliveryCompany(form.getDelivercompany_ID() + "");
			if(dc!=null)
				req.setVendorImportCode(dc.getIntegration_key());
		}
		
		BDO_Passenger pax = form.getPassenger(0);

		Address add=req.addNewAddress();
		add.setAddress1(pax.getAddress1());
		add.setAddress2(pax.getAddress2());
		add.setZip(pax.getZip());
		add.setCity(pax.getCity());
		add.setState(pax.getState_ID());
		
		req.setServiceType(DelivercompanyBMO.getServiceLevel(form.getServicelevel_ID()).getService_code());

		if (form.getIncident() != null && form.getItemlist() != null && form.getItemlist().size()>0) {
			for (Item item: (ArrayList<Item>)form.getItemlist()) {
				BagDeliveryType id = req.addNewBagDeliveryTypes();
				if (item.getClaimchecknum() != null && item.getClaimchecknum().trim().length() > 0) {
					id.setBagTag(item.getClaimchecknum());	
				} else {
					id.setBagTag("notAvailable");
				}
				Category o=CategoryBMO.getCategory(item.getOther());
				if(o!=null)
					id.setBagOtherCharge(o.getDescription());
				if(item.getSpecialCondition()==TracingConstants.SPECIAL_CONDITION_OVERSIZED){
					id.setOversize(true);
					id.setOverweight(false);
				} else if(item.getSpecialCondition()==TracingConstants.SPECIAL_CONDITION_OVERWEIGHT){
					id.setOversize(false);
					id.setOverweight(true);
				} else if(item.getSpecialCondition()==TracingConstants.SPECIAL_CONDITION_BOTH){
					id.setOversize(true);
					id.setOverweight(true);
				}
				id.setStandard(item.isNoAddFees());
			}
			
		} else if (form.getOhd() != null){
			BagDeliveryType id = req.addNewBagDeliveryTypes();
			if (form.getOhd().getClaimchecknum_bagnumber() != null && form.getOhd().getClaimchecknum_bagnumber().trim().length() > 0) {
				id.setBagTag(form.getOhd().getClaimchecknum_bagnumber());	
			} else {
				id.setBagTag("notAvailable");
			}
			Category o=CategoryBMO.getCategory(form.getOhd().getOther());
			if(o!=null)
				id.setBagOtherCharge(o.getDescription());
			if(form.getOhd().getSpecialCondition()==TracingConstants.SPECIAL_CONDITION_OVERSIZED){
				id.setOversize(true);
				id.setOverweight(false);
			} else if(form.getOhd().getSpecialCondition()==TracingConstants.SPECIAL_CONDITION_OVERWEIGHT){
				id.setOversize(false);
				id.setOverweight(true);
			} else if(form.getOhd().getSpecialCondition()==TracingConstants.SPECIAL_CONDITION_BOTH){
				id.setOversize(true);
				id.setOverweight(true);
			}
			id.setStandard(form.getOhd().isNoAddFees());
		}
		
		form.set_DATEFORMAT(user.getDateformat().getFormat());
		form.set_TIMEFORMAT(user.getTimeformat().getFormat());
		form.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone()));
		
	    try {
	    	logger.info(validate);
	    	CalculateDeliveryCostResponseDocument replyDoc=stub.calculateDeliveryCost(validate);
	    	logger.info(replyDoc);
			// Initialize the service
			
			// This is the call to the web service
	    	CalculateDeliveryCostResponse reply = replyDoc.getCalculateDeliveryCostResponse();
	    	SouthwestAirlinesCalculateDeliveryCostResponse a= reply.getCalculateDeliveryCostResult();
			if(a.getErrorCodes()==null || (a.getErrorCodes()!=null && a.getErrorCodes().sizeOfErrorCodeEnumArray()==0)){
				try{
					BigDecimal cost=a.getDeliveryCharges().add(a.getFuelSurchargeCost()).add(a.getAddtlFeesCost());
					form.setCost(cost.toString());
					form.setCurrency("USD");
					form.setOrigDelivCost(cost.doubleValue());
				} catch (Exception e){
					ActionMessage error = new ActionMessage("error.delivery.request.calculation");
					messages.add(ActionMessages.GLOBAL_MESSAGE, error);
					e.printStackTrace();
				}
				
			} else {
				boolean specificError=false;
				for(Enum err:a.getErrorCodes().getErrorCodeEnumArray()){
					if(err.equals(ErrorCodeEnum.ZIP_CODE_OUTSIDE_DELIVERY_AREA)){
						ActionMessage error = new ActionMessage("error.zip.out.delivery");
						messages.add(ActionMessages.GLOBAL_MESSAGE, error);
						specificError=true;
					} 
					if(err.equals(ErrorCodeEnum.UNKNOWN_ZIP_CODE)){
						ActionMessage error = new ActionMessage("error.unknown.zip");
						messages.add(ActionMessages.GLOBAL_MESSAGE, error);
						specificError=true;
					} 
					if(err.equals(ErrorCodeEnum.INVALID_VENDOR_CODE)){

						ActionMessage error = new ActionMessage("error.invalid.vendor");
						messages.add(ActionMessages.GLOBAL_MESSAGE, error);
						specificError=true;
					}
				}
				if(!specificError){
					ActionMessage error = new ActionMessage("error.general.delivery.request");
					messages.add(ActionMessages.GLOBAL_MESSAGE, error);
				}
			}
			
			return form;
		} catch (Exception e) {

			ActionMessage error = new ActionMessage("error.cannot.connect.delivery.request");
			messages.add(ActionMessages.GLOBAL_MESSAGE, error);
            e.printStackTrace();
		}

		return form;
	}
	
	private static Calendar fixTimeZone(Calendar cal, String timezone){
		Calendar ret = Calendar.getInstance(TimeZone.getTimeZone(timezone));
		for (int i = 0; i < Calendar.FIELD_COUNT; i++){
			if(i != Calendar.ZONE_OFFSET && i != Calendar.DST_OFFSET){
				ret.set(i, cal.get(i));
			}
		}
		return ret;
	}
}