package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;

/**
 * Process for adding new delivery company integrations:
 * 
 * 1.  Add delivery company to the enum type below.
 * 2.  Modify getIntegrationTypeString from DeliveryIntegrationTypeUtils.java
 * 3.  Modify integrate from DeliveryIntegrationTypeUtils.java
 * 4.  Add resources to the applicationresources.properties file.  
 * 5.  Build out integration classes (com.bagnet.nettracer.integrations.delivery.*)
 * 
 * Please be aware that the ENUM type below will be inserted into the
 * database.  For example, if RYNNS is the enum type of a delivery company
 * then the database will contain "RYNNS" in the integration field.
 */

public enum DeliveryIntegrationType implements Serializable {
	NONE, RYNNS //,  DSI,  FEDEX
}
