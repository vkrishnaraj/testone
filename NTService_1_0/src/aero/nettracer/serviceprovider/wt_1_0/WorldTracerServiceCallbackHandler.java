
/**
 * WorldTracerServiceCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5  Built on : Apr 30, 2009 (06:07:24 EDT)
 */

    package aero.nettracer.serviceprovider.wt_1_0;

    /**
     *  WorldTracerServiceCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class WorldTracerServiceCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public WorldTracerServiceCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public WorldTracerServiceCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for forwardOhd method
            * override this method for handling normal response from forwardOhd operation
            */
           public void receiveResultforwardOhd(
                    aero.nettracer.serviceprovider.wt_1_0.ForwardOhdResponseDocument result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from forwardOhd operation
           */
            public void receiveErrorforwardOhd(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for suspendAhl method
            * override this method for handling normal response from suspendAhl operation
            */
           public void receiveResultsuspendAhl(
                    aero.nettracer.serviceprovider.wt_1_0.SuspendAhlResponseDocument result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from suspendAhl operation
           */
            public void receiveErrorsuspendAhl(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getActionFileDetails method
            * override this method for handling normal response from getActionFileDetails operation
            */
           public void receiveResultgetActionFileDetails(
                    aero.nettracer.serviceprovider.wt_1_0.GetActionFileDetailsResponseDocument result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getActionFileDetails operation
           */
            public void receiveErrorgetActionFileDetails(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for amendAhl method
            * override this method for handling normal response from amendAhl operation
            */
           public void receiveResultamendAhl(
                    aero.nettracer.serviceprovider.wt_1_0.AmendAhlResponseDocument result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from amendAhl operation
           */
            public void receiveErroramendAhl(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for createBdo method
            * override this method for handling normal response from createBdo operation
            */
           public void receiveResultcreateBdo(
                    aero.nettracer.serviceprovider.wt_1_0.CreateBdoResponseDocument result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from createBdo operation
           */
            public void receiveErrorcreateBdo(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getOhd method
            * override this method for handling normal response from getOhd operation
            */
           public void receiveResultgetOhd(
                    aero.nettracer.serviceprovider.wt_1_0.GetOhdResponseDocument result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getOhd operation
           */
            public void receiveErrorgetOhd(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for requestQuickOhd method
            * override this method for handling normal response from requestQuickOhd operation
            */
           public void receiveResultrequestQuickOhd(
                    aero.nettracer.serviceprovider.wt_1_0.RequestQuickOhdResponseDocument result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from requestQuickOhd operation
           */
            public void receiveErrorrequestQuickOhd(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getAhl method
            * override this method for handling normal response from getAhl operation
            */
           public void receiveResultgetAhl(
                    aero.nettracer.serviceprovider.wt_1_0.GetAhlResponseDocument result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getAhl operation
           */
            public void receiveErrorgetAhl(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getActionFileCounts method
            * override this method for handling normal response from getActionFileCounts operation
            */
           public void receiveResultgetActionFileCounts(
                    aero.nettracer.serviceprovider.wt_1_0.GetActionFileCountsResponseDocument result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getActionFileCounts operation
           */
            public void receiveErrorgetActionFileCounts(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for requestOhd method
            * override this method for handling normal response from requestOhd operation
            */
           public void receiveResultrequestOhd(
                    aero.nettracer.serviceprovider.wt_1_0.RequestOhdResponseDocument result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from requestOhd operation
           */
            public void receiveErrorrequestOhd(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for closeOhd method
            * override this method for handling normal response from closeOhd operation
            */
           public void receiveResultcloseOhd(
                    aero.nettracer.serviceprovider.wt_1_0.CloseOhdResponseDocument result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from closeOhd operation
           */
            public void receiveErrorcloseOhd(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getActionFileSummary method
            * override this method for handling normal response from getActionFileSummary operation
            */
           public void receiveResultgetActionFileSummary(
                    aero.nettracer.serviceprovider.wt_1_0.GetActionFileSummaryResponseDocument result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getActionFileSummary operation
           */
            public void receiveErrorgetActionFileSummary(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for createOhd method
            * override this method for handling normal response from createOhd operation
            */
           public void receiveResultcreateOhd(
                    aero.nettracer.serviceprovider.wt_1_0.CreateOhdResponseDocument result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from createOhd operation
           */
            public void receiveErrorcreateOhd(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for sendForwardMessage method
            * override this method for handling normal response from sendForwardMessage operation
            */
           public void receiveResultsendForwardMessage(
                    aero.nettracer.serviceprovider.wt_1_0.SendForwardMessageResponseDocument result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from sendForwardMessage operation
           */
            public void receiveErrorsendForwardMessage(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for reinstanteAhl method
            * override this method for handling normal response from reinstanteAhl operation
            */
           public void receiveResultreinstanteAhl(
                    aero.nettracer.serviceprovider.wt_1_0.ReinstanteAhlResponseDocument result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from reinstanteAhl operation
           */
            public void receiveErrorreinstanteAhl(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for eraseActionFile method
            * override this method for handling normal response from eraseActionFile operation
            */
           public void receiveResulteraseActionFile(
                    aero.nettracer.serviceprovider.wt_1_0.EraseActionFileResponseDocument result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from eraseActionFile operation
           */
            public void receiveErroreraseActionFile(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for amendOhd method
            * override this method for handling normal response from amendOhd operation
            */
           public void receiveResultamendOhd(
                    aero.nettracer.serviceprovider.wt_1_0.AmendOhdResponseDocument result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from amendOhd operation
           */
            public void receiveErroramendOhd(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for createAhl method
            * override this method for handling normal response from createAhl operation
            */
           public void receiveResultcreateAhl(
                    aero.nettracer.serviceprovider.wt_1_0.CreateAhlResponseDocument result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from createAhl operation
           */
            public void receiveErrorcreateAhl(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for closeAhl method
            * override this method for handling normal response from closeAhl operation
            */
           public void receiveResultcloseAhl(
                    aero.nettracer.serviceprovider.wt_1_0.CloseAhlResponseDocument result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from closeAhl operation
           */
            public void receiveErrorcloseAhl(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for placeActionFile method
            * override this method for handling normal response from placeActionFile operation
            */
           public void receiveResultplaceActionFile(
                    aero.nettracer.serviceprovider.wt_1_0.PlaceActionFileResponseDocument result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from placeActionFile operation
           */
            public void receiveErrorplaceActionFile(java.lang.Exception e) {
            }
                


    }
    