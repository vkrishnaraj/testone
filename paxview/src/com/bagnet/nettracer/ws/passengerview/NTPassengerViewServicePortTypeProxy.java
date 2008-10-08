package com.bagnet.nettracer.ws.passengerview;

public class NTPassengerViewServicePortTypeProxy implements com.bagnet.nettracer.ws.passengerview.NTPassengerViewServicePortType {
  private String _endpoint = null;
  private com.bagnet.nettracer.ws.passengerview.NTPassengerViewServicePortType nTPassengerViewServicePortType = null;
  
  public NTPassengerViewServicePortTypeProxy() {
    _initNTPassengerViewServicePortTypeProxy();
  }
  
  public NTPassengerViewServicePortTypeProxy(String endpoint) {
    _endpoint = endpoint;
    _initNTPassengerViewServicePortTypeProxy();
  }
  
  private void _initNTPassengerViewServicePortTypeProxy() {
    try {
      nTPassengerViewServicePortType = (new com.bagnet.nettracer.ws.passengerview.NTPassengerViewServiceLocator()).getNTPassengerViewServiceSOAP11port_http();
      if (nTPassengerViewServicePortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)nTPassengerViewServicePortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)nTPassengerViewServicePortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (nTPassengerViewServicePortType != null)
      ((javax.xml.rpc.Stub)nTPassengerViewServicePortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.bagnet.nettracer.ws.passengerview.NTPassengerViewServicePortType getNTPassengerViewServicePortType() {
    if (nTPassengerViewServicePortType == null)
      _initNTPassengerViewServicePortTypeProxy();
    return nTPassengerViewServicePortType;
  }
  
  public com.bagnet.nettracer.ws.core.pojo.xsd.WS_PVIncident getIncidentPV(java.lang.String incident_id, java.lang.String lastname) throws java.rmi.RemoteException{
    if (nTPassengerViewServicePortType == null)
      _initNTPassengerViewServicePortTypeProxy();
    return nTPassengerViewServicePortType.getIncidentPV(incident_id, lastname);
  }
  
  public com.bagnet.nettracer.ws.core.pojo.xsd.WS_PVAdvancedIncident getAdvancedIncidentPV(java.lang.String incident_id, java.lang.String lastname, java.lang.Boolean doNotAuthorize) throws java.rmi.RemoteException{
    if (nTPassengerViewServicePortType == null)
      _initNTPassengerViewServicePortTypeProxy();
    return nTPassengerViewServicePortType.getAdvancedIncidentPV(incident_id, lastname, doNotAuthorize);
  }
  
  
}