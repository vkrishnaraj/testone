/**
 * ReservationService_1_0CallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.3  Built on : Aug 10, 2007 (04:45:47 LKT)
 */
package aero.nettracer.serviceprovider.ws_1_0;


/**
 *  ReservationService_1_0CallbackHandler Callback class, Users can extend this class and implement
 *  their own receiveResult and receiveError methods.
 */
public abstract class ReservationService_1_0CallbackHandler {
    protected Object clientData;

    /**
     * User can pass in any object that needs to be accessed once the NonBlocking
     * Web service call is finished and appropriate method of this CallBack is called.
     * @param clientData Object mechanism by which the user can pass in user data
     * that will be avilable at the time this callback is called.
     */
    public ReservationService_1_0CallbackHandler(Object clientData) {
        this.clientData = clientData;
    }

    /**
     * Please use this constructor if you don't want to set any clientData
     */
    public ReservationService_1_0CallbackHandler() {
        this.clientData = null;
    }

    /**
     * Get the client data
     */
    public Object getClientData() {
        return clientData;
    }

    /**
     * auto generated Axis2 call back method for getEnplanements method
     * override this method for handling normal response from getEnplanements operation
     */
    public void receiveResultgetEnplanements(
        aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsResponseDocument result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from getEnplanements operation
     */
    public void receiveErrorgetEnplanements(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for writeRemark method
     * override this method for handling normal response from writeRemark operation
     */
    public void receiveResultwriteRemark(
        aero.nettracer.serviceprovider.ws_1_0.WriteRemarkResponseDocument result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from writeRemark operation
     */
    public void receiveErrorwriteRemark(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for getReservationData method
     * override this method for handling normal response from getReservationData operation
     */
    public void receiveResultgetReservationData(
        aero.nettracer.serviceprovider.ws_1_0.GetReservationDataResponseDocument result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from getReservationData operation
     */
    public void receiveErrorgetReservationData(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for getOsiContents method
     * override this method for handling normal response from getOsiContents operation
     */
    public void receiveResultgetOsiContents(
        aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsResponseDocument result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from getOsiContents operation
     */
    public void receiveErrorgetOsiContents(java.lang.Exception e) {
    }
}
