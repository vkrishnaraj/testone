/**
 * NTPassengerViewService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.bagnet.nettracer.ws.passengerview;

public interface NTPassengerViewService extends javax.xml.rpc.Service {
    public java.lang.String getNTPassengerViewServiceSOAP12port_httpAddress();

    public com.bagnet.nettracer.ws.passengerview.NTPassengerViewServicePortType getNTPassengerViewServiceSOAP12port_http() throws javax.xml.rpc.ServiceException;

    public com.bagnet.nettracer.ws.passengerview.NTPassengerViewServicePortType getNTPassengerViewServiceSOAP12port_http(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
    public java.lang.String getNTPassengerViewServiceSOAP11port_httpAddress();

    public com.bagnet.nettracer.ws.passengerview.NTPassengerViewServicePortType getNTPassengerViewServiceSOAP11port_http() throws javax.xml.rpc.ServiceException;

    public com.bagnet.nettracer.ws.passengerview.NTPassengerViewServicePortType getNTPassengerViewServiceSOAP11port_http(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
