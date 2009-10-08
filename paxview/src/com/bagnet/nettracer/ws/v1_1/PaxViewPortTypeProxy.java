package com.bagnet.nettracer.ws.v1_1;

public class PaxViewPortTypeProxy implements com.bagnet.nettracer.ws.v1_1.PaxViewPortType {
  private String _endpoint = null;
  private com.bagnet.nettracer.ws.v1_1.PaxViewPortType paxViewPortType = null;
  
  public PaxViewPortTypeProxy() {
    _initPaxViewPortTypeProxy();
  }
  
  public PaxViewPortTypeProxy(String endpoint) {
    _endpoint = endpoint;
    _initPaxViewPortTypeProxy();
  }
  
  private void _initPaxViewPortTypeProxy() {
    try {
      paxViewPortType = (new com.bagnet.nettracer.ws.v1_1.PaxViewLocator()).getPaxViewSOAP11port_http();
      if (paxViewPortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)paxViewPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)paxViewPortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (paxViewPortType != null)
      ((javax.xml.rpc.Stub)paxViewPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.bagnet.nettracer.ws.v1_1.PaxViewPortType getPaxViewPortType() {
    if (paxViewPortType == null)
      _initPaxViewPortTypeProxy();
    return paxViewPortType;
  }
  
  public com.bagnet.nettracer.ws.v1_1.paxview.xsd.WS_PVIncident writePassengerComment(java.lang.String incident_id, java.lang.String comment, java.lang.String username, java.lang.String password) throws java.rmi.RemoteException{
    if (paxViewPortType == null)
      _initPaxViewPortTypeProxy();
    return paxViewPortType.writePassengerComment(incident_id, comment, username, password);
  }
  
  public com.bagnet.nettracer.ws.v1_1.paxview.xsd.WS_PVIncident getPaxView(java.lang.String incident_id, java.lang.String lastname, java.lang.String username, java.lang.String password) throws java.rmi.RemoteException{
    if (paxViewPortType == null)
      _initPaxViewPortTypeProxy();
    return paxViewPortType.getPaxView(incident_id, lastname, username, password);
  }
  
  public com.bagnet.nettracer.ws.v1_1.paxview.xsd.WS_PVIncident getPaxViewIvr(java.lang.String incident_id, java.lang.String username, java.lang.String password) throws java.rmi.RemoteException{
    if (paxViewPortType == null)
      _initPaxViewPortTypeProxy();
    return paxViewPortType.getPaxViewIvr(incident_id, username, password);
  }
  
  
}