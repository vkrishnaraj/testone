
/**
 * PaxViewCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5  Built on : Apr 30, 2009 (06:07:24 EDT)
 */

    package com.bagnet.nettracer.ws.v1_1;

    /**
     *  PaxViewCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class PaxViewCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public PaxViewCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public PaxViewCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for getPaxView method
            * override this method for handling normal response from getPaxView operation
            */
           public void receiveResultgetPaxView(
                    com.bagnet.nettracer.ws.v1_1.GetPaxViewResponseDocument result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getPaxView operation
           */
            public void receiveErrorgetPaxView(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for writePassengerComment method
            * override this method for handling normal response from writePassengerComment operation
            */
           public void receiveResultwritePassengerComment(
                    com.bagnet.nettracer.ws.v1_1.WritePassengerCommentResponseDocument result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from writePassengerComment operation
           */
            public void receiveErrorwritePassengerComment(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getPaxViewIvr method
            * override this method for handling normal response from getPaxViewIvr operation
            */
           public void receiveResultgetPaxViewIvr(
                    com.bagnet.nettracer.ws.v1_1.GetPaxViewIvrResponseDocument result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getPaxViewIvr operation
           */
            public void receiveErrorgetPaxViewIvr(java.lang.Exception e) {
            }
                


    }
    