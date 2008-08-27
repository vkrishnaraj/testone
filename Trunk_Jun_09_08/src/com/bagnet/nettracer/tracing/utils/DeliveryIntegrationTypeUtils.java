package com.bagnet.nettracer.tracing.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.struts.util.LabelValueBean;
import org.apache.struts.util.MessageResources;

import com.bagnet.nettracer.integrations.delivery.BDOIntegration;
import com.bagnet.nettracer.integrations.delivery.DeliveryIntegrationResponse;
import com.bagnet.nettracer.integrations.delivery.Rynns;
import com.bagnet.nettracer.tracing.bmo.DelivercompanyBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.BDO;
import com.bagnet.nettracer.tracing.db.DeliverCompany;
import com.bagnet.nettracer.tracing.db.DeliveryIntegrationType;

public class DeliveryIntegrationTypeUtils {

	/**
	 * @return list of LabelValueBeans.
	 */
	public static List<LabelValueBean> getIntegrationTypeList() {
		ArrayList<LabelValueBean> list = new ArrayList<LabelValueBean>();
		for (DeliveryIntegrationType type: DeliveryIntegrationType.values()) {
			LabelValueBean bean = new LabelValueBean(DeliveryIntegrationTypeUtils.getIntegrationTypeString(type), type.ordinal() + "");
			list.add(bean);
		}
		return list;
	}

	/**
	 * Returns the string representation of the provided DeliveryIntegrationType
	 * @param type
	 * @return string representing the name of the integrated delivery company.
	 */
	public static String getIntegrationTypeString(DeliveryIntegrationType type) {
		MessageResources messages = MessageResources.getMessageResources("com.bagnet.nettracer.tracing.resources.ApplicationResources");
		if (type.equals(DeliveryIntegrationType.RYNNS)) {
			return messages.getMessage(new Locale(TracingConstants.DEFAULT_LOCALE), "delivercompany.integration.rynns");
		} 
		/*
		else if (type.equals(DeliveryIntegrationType.DSI)) {
			return messages.getMessage(new Locale(TracingConstants.DEFAULT_LOCALE), "delivercompany.integration.dsi");
		}
		else if (type.equals(DeliveryIntegrationType.FEDEX)) {
			return messages.getMessage(new Locale(TracingConstants.DEFAULT_LOCALE), "delivercompany.integration.fedex");
		}
		*/
		return " ";
		
	}
	
	/**
	 * Method maps a provided integer back to the enum type
	 * @param intValue the ordinal value of the ENUM being searched for
	 * @return returns the ENUM value of the given ordinal value
	 */
	public static DeliveryIntegrationType getDeliveryIntegrationType(int intValue) {
		DeliveryIntegrationType[] array = DeliveryIntegrationType.values();
		for (DeliveryIntegrationType item: array) {
			if (item.ordinal() == intValue) {
				return item;
			}
		}
		return null; 
	}

	/**
	 * 
	 * @param bdo
	 * @param user
	 * @return Delivery integration response which contains success and response messages.
	 */
	public static DeliveryIntegrationResponse integrate(BDO bdo, Agent agent) {
		// If there is a BDO
		String currentCompanyId = bdo.getDelivercompany().getDelivercompany_ID() + "";
		DeliverCompany currentDeliveryCompany = DelivercompanyBMO.getDeliveryCompany(currentCompanyId);
		
		
				
		DeliveryIntegrationResponse response = null;
		
		if (bdo != null) {
			// If there has been a previous successful message, error out.
			if (bdo.getDelivery_integration_type() != null) {
				
				int originalCompanyId = bdo.getIntegrationDelivercompany_ID();
				DeliverCompany integrationDeliveryCompany = null;
				
				if (originalCompanyId != 0) {
					integrationDeliveryCompany = DelivercompanyBMO.getDeliveryCompany("" + originalCompanyId);
				}
				
				response = new DeliveryIntegrationResponse();
				response.setSuccess(false);

				String responseText = TracerUtils.getResourcePropertyText("delivercompany.integration.alreadysubmitted.html", agent);

				String phoneNumber1 = "";
				if (integrationDeliveryCompany.getPhone() != null ) {
					phoneNumber1 = integrationDeliveryCompany.getPhone();
				}
				
				String phoneNumber2 = "";
				if (currentDeliveryCompany.getPhone() != null ) {
					phoneNumber2 = currentDeliveryCompany.getPhone();
				}
				
				// Replace values in the response string
				responseText = responseText.replace("${deliveryCompany1}", integrationDeliveryCompany.getName());
				responseText = responseText.replace("${deliveryCompany2}", currentDeliveryCompany.getName());			
				responseText = responseText.replace("${phoneNumber1}", phoneNumber1);
				responseText = responseText.replace("${phoneNumber2}", phoneNumber2);
				
				response.setResponse(responseText); 
				
				return response;
			}
			
			// Integration with delivery company.
			if (currentDeliveryCompany.getDelivery_integration_type() != DeliveryIntegrationType.NONE) {
				
				DeliveryIntegrationType type = currentDeliveryCompany.getDelivery_integration_type();
				BDOIntegration integration = null;
				
				// Add new integration types here. 
				if (type.equals(DeliveryIntegrationType.RYNNS)) {
					integration = new Rynns();
				}
				
				response = integration.integrate(bdo, agent);
				response.setIntegrationType(currentDeliveryCompany.getDelivery_integration_type());
				
				if (response.isSuccess()) {
					// Update BDO with unique key and integration type;
					bdo.setDelivery_integration_type(response.getIntegrationType());
					bdo.setDelivery_integration_id(response.getUniqueIntegrationId());
					bdo.setIntegrationDelivercompany_ID(currentDeliveryCompany.getDelivercompany_ID());
					HibernateUtils.save(bdo);
				}
				
				return response;
			}
		}
		
		return null;
	}

}
