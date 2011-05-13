
/**
 * NTPassengerViewServiceCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5  Built on : Apr 30, 2009 (06:07:24 EDT)
 */

    package com.bagnet.nettracer.ws.passengerview;

    /**
     *  NTPassengerViewServiceCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class NTPassengerViewServiceCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public NTPassengerViewServiceCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public NTPassengerViewServiceCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for getAdvancedIncidentsPVPhone method
            * override this method for handling normal response from getAdvancedIncidentsPVPhone operation
            */
           public void receiveResultgetAdvancedIncidentsPVPhone(
                    com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPhoneResponseDocument result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getAdvancedIncidentsPVPhone operation
           */
            public void receiveErrorgetAdvancedIncidentsPVPhone(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getAdvancedIncidentsPVPnr method
            * override this method for handling normal response from getAdvancedIncidentsPVPnr operation
            */
           public void receiveResultgetAdvancedIncidentsPVPnr(
                    com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrResponseDocument result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getAdvancedIncidentsPVPnr operation
           */
            public void receiveErrorgetAdvancedIncidentsPVPnr(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getIncidentPV method
            * override this method for handling normal response from getIncidentPV operation
            */
           public void receiveResultgetIncidentPV(
                    com.bagnet.nettracer.ws.passengerview.GetIncidentPVResponseDocument result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getIncidentPV operation
           */
            public void receiveErrorgetIncidentPV(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getAdvancedIncidentsPVFrequentFlyer method
            * override this method for handling normal response from getAdvancedIncidentsPVFrequentFlyer operation
            */
           public void receiveResultgetAdvancedIncidentsPVFrequentFlyer(
                    com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVFrequentFlyerResponseDocument result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getAdvancedIncidentsPVFrequentFlyer operation
           */
            public void receiveErrorgetAdvancedIncidentsPVFrequentFlyer(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getAdvancedIncidentPVWithAgent method
            * override this method for handling normal response from getAdvancedIncidentPVWithAgent operation
            */
           public void receiveResultgetAdvancedIncidentPVWithAgent(
                    com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentPVWithAgentResponseDocument result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getAdvancedIncidentPVWithAgent operation
           */
            public void receiveErrorgetAdvancedIncidentPVWithAgent(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getActivePhoneList method
            * override this method for handling normal response from getActivePhoneList operation
            */
           public void receiveResultgetActivePhoneList(
                    com.bagnet.nettracer.ws.passengerview.GetActivePhoneListResponseDocument result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getActivePhoneList operation
           */
            public void receiveErrorgetActivePhoneList(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getAdvancedIncidentPV method
            * override this method for handling normal response from getAdvancedIncidentPV operation
            */
           public void receiveResultgetAdvancedIncidentPV(
                    com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentPVResponseDocument result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getAdvancedIncidentPV operation
           */
            public void receiveErrorgetAdvancedIncidentPV(java.lang.Exception e) {
            }
                


    }
    