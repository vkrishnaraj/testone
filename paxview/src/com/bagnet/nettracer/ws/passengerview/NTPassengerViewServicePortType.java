/**
 * NTPassengerViewServicePortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.bagnet.nettracer.ws.passengerview;

public interface NTPassengerViewServicePortType extends java.rmi.Remote {
    public com.bagnet.nettracer.ws.core.pojo.xsd.WS_PVIncident getIncidentPV(java.lang.String incident_id, java.lang.String lastname) throws java.rmi.RemoteException;
    public com.bagnet.nettracer.ws.core.pojo.xsd.WS_PVAdvancedIncident getAdvancedIncidentPV(java.lang.String incident_id, java.lang.String lastname, java.lang.Boolean doNotAuthorize) throws java.rmi.RemoteException;
}
