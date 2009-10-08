/**
 * PaxViewPortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.bagnet.nettracer.ws.v1_1;

public interface PaxViewPortType extends java.rmi.Remote {
    public com.bagnet.nettracer.ws.v1_1.paxview.xsd.WS_PVIncident writePassengerComment(java.lang.String incident_id, java.lang.String comment, java.lang.String username, java.lang.String password) throws java.rmi.RemoteException;
    public com.bagnet.nettracer.ws.v1_1.paxview.xsd.WS_PVIncident getPaxView(java.lang.String incident_id, java.lang.String lastname, java.lang.String username, java.lang.String password) throws java.rmi.RemoteException;
    public com.bagnet.nettracer.ws.v1_1.paxview.xsd.WS_PVIncident getPaxViewIvr(java.lang.String incident_id, java.lang.String username, java.lang.String password) throws java.rmi.RemoteException;
}
