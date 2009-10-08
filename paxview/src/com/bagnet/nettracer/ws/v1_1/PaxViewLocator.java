/**
 * PaxViewLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.bagnet.nettracer.ws.v1_1;

public class PaxViewLocator extends org.apache.axis.client.Service implements com.bagnet.nettracer.ws.v1_1.PaxView {

    public PaxViewLocator() {
    }

    private String myUrl = null;
    
    
    public String getMyUrl() {
		return myUrl;
	}

	public void setMyUrl(String myUrl) {
		this.myUrl = myUrl;
		this.PaxViewSOAP12port_http_address = myUrl;
	}

	public PaxViewLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public PaxViewLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for PaxViewSOAP11port_http
    private java.lang.String PaxViewSOAP11port_http_address = "http://localhost:8080/axis2/services/PaxView";

    public java.lang.String getPaxViewSOAP11port_httpAddress() {
        return PaxViewSOAP11port_http_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String PaxViewSOAP11port_httpWSDDServiceName = "PaxViewSOAP11port_http";

    public java.lang.String getPaxViewSOAP11port_httpWSDDServiceName() {
        return PaxViewSOAP11port_httpWSDDServiceName;
    }

    public void setPaxViewSOAP11port_httpWSDDServiceName(java.lang.String name) {
        PaxViewSOAP11port_httpWSDDServiceName = name;
    }

    public com.bagnet.nettracer.ws.v1_1.PaxViewPortType getPaxViewSOAP11port_http() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(PaxViewSOAP11port_http_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getPaxViewSOAP11port_http(endpoint);
    }

    public com.bagnet.nettracer.ws.v1_1.PaxViewPortType getPaxViewSOAP11port_http(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.bagnet.nettracer.ws.v1_1.PaxViewSOAP11BindingStub _stub = new com.bagnet.nettracer.ws.v1_1.PaxViewSOAP11BindingStub(portAddress, this);
            _stub.setPortName(getPaxViewSOAP11port_httpWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setPaxViewSOAP11port_httpEndpointAddress(java.lang.String address) {
        PaxViewSOAP11port_http_address = address;
    }


    // Use to get a proxy class for PaxViewSOAP12port_http
    private java.lang.String PaxViewSOAP12port_http_address = "http://localhost:8080/axis2/services/PaxView";

    public java.lang.String getPaxViewSOAP12port_httpAddress() {
        return PaxViewSOAP12port_http_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String PaxViewSOAP12port_httpWSDDServiceName = "PaxViewSOAP12port_http";

    public java.lang.String getPaxViewSOAP12port_httpWSDDServiceName() {
        return PaxViewSOAP12port_httpWSDDServiceName;
    }

    public void setPaxViewSOAP12port_httpWSDDServiceName(java.lang.String name) {
        PaxViewSOAP12port_httpWSDDServiceName = name;
    }

    public com.bagnet.nettracer.ws.v1_1.PaxViewPortType getPaxViewSOAP12port_http() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(PaxViewSOAP12port_http_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getPaxViewSOAP12port_http(endpoint);
    }

    public com.bagnet.nettracer.ws.v1_1.PaxViewPortType getPaxViewSOAP12port_http(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.bagnet.nettracer.ws.v1_1.PaxViewSOAP12BindingStub _stub = new com.bagnet.nettracer.ws.v1_1.PaxViewSOAP12BindingStub(portAddress, this);
            _stub.setPortName(getPaxViewSOAP12port_httpWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setPaxViewSOAP12port_httpEndpointAddress(java.lang.String address) {
        PaxViewSOAP12port_http_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     * This service has multiple ports for a given interface;
     * the proxy implementation returned may be indeterminate.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.bagnet.nettracer.ws.v1_1.PaxViewPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.bagnet.nettracer.ws.v1_1.PaxViewSOAP11BindingStub _stub = new com.bagnet.nettracer.ws.v1_1.PaxViewSOAP11BindingStub(new java.net.URL(PaxViewSOAP11port_http_address), this);
                _stub.setPortName(getPaxViewSOAP11port_httpWSDDServiceName());
                return _stub;
            }
            if (com.bagnet.nettracer.ws.v1_1.PaxViewPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.bagnet.nettracer.ws.v1_1.PaxViewSOAP12BindingStub _stub = new com.bagnet.nettracer.ws.v1_1.PaxViewSOAP12BindingStub(new java.net.URL(PaxViewSOAP12port_http_address), this);
                _stub.setPortName(getPaxViewSOAP12port_httpWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("PaxViewSOAP11port_http".equals(inputPortName)) {
            return getPaxViewSOAP11port_http();
        }
        else if ("PaxViewSOAP12port_http".equals(inputPortName)) {
            return getPaxViewSOAP12port_http();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://v1_1.ws.nettracer.bagnet.com", "PaxView");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://v1_1.ws.nettracer.bagnet.com", "PaxViewSOAP11port_http"));
            ports.add(new javax.xml.namespace.QName("http://v1_1.ws.nettracer.bagnet.com", "PaxViewSOAP12port_http"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("PaxViewSOAP11port_http".equals(portName)) {
            setPaxViewSOAP11port_httpEndpointAddress(address);
        }
        else 
if ("PaxViewSOAP12port_http".equals(portName)) {
            setPaxViewSOAP12port_httpEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
