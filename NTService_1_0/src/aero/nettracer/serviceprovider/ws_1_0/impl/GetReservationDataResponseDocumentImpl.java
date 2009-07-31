/*
 * An XML document type.
 * Localname: getReservationDataResponse
 * Namespace: http://ws_1_0.serviceprovider.nettracer.aero
 * Java type: aero.nettracer.serviceprovider.ws_1_0.GetReservationDataResponseDocument
 *
 * Automatically generated - do not modify.
 */
package aero.nettracer.serviceprovider.ws_1_0.impl;

/**
 * A document containing one getReservationDataResponse(@http://ws_1_0.serviceprovider.nettracer.aero) element.
 *
 * This is a complex type.
 */
public class GetReservationDataResponseDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
    implements aero.nettracer.serviceprovider.ws_1_0.GetReservationDataResponseDocument {
    private static final javax.xml.namespace.QName GETRESERVATIONDATARESPONSE$0 = new javax.xml.namespace.QName("http://ws_1_0.serviceprovider.nettracer.aero",
            "getReservationDataResponse");

    public GetReservationDataResponseDocumentImpl(
        org.apache.xmlbeans.SchemaType sType) {
        super(sType);
    }

    /**
     * Gets the "getReservationDataResponse" element
     */
    public aero.nettracer.serviceprovider.ws_1_0.GetReservationDataResponseDocument.GetReservationDataResponse getGetReservationDataResponse() {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.ws_1_0.GetReservationDataResponseDocument.GetReservationDataResponse target =
                null;
            target = (aero.nettracer.serviceprovider.ws_1_0.GetReservationDataResponseDocument.GetReservationDataResponse) get_store()
                                                                                                                               .find_element_user(GETRESERVATIONDATARESPONSE$0,
                    0);

            if (target == null) {
                return null;
            }

            return target;
        }
    }

    /**
     * Sets the "getReservationDataResponse" element
     */
    public void setGetReservationDataResponse(
        aero.nettracer.serviceprovider.ws_1_0.GetReservationDataResponseDocument.GetReservationDataResponse getReservationDataResponse) {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.ws_1_0.GetReservationDataResponseDocument.GetReservationDataResponse target =
                null;
            target = (aero.nettracer.serviceprovider.ws_1_0.GetReservationDataResponseDocument.GetReservationDataResponse) get_store()
                                                                                                                               .find_element_user(GETRESERVATIONDATARESPONSE$0,
                    0);

            if (target == null) {
                target = (aero.nettracer.serviceprovider.ws_1_0.GetReservationDataResponseDocument.GetReservationDataResponse) get_store()
                                                                                                                                   .add_element_user(GETRESERVATIONDATARESPONSE$0);
            }

            target.set(getReservationDataResponse);
        }
    }

    /**
     * Appends and returns a new empty "getReservationDataResponse" element
     */
    public aero.nettracer.serviceprovider.ws_1_0.GetReservationDataResponseDocument.GetReservationDataResponse addNewGetReservationDataResponse() {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.ws_1_0.GetReservationDataResponseDocument.GetReservationDataResponse target =
                null;
            target = (aero.nettracer.serviceprovider.ws_1_0.GetReservationDataResponseDocument.GetReservationDataResponse) get_store()
                                                                                                                               .add_element_user(GETRESERVATIONDATARESPONSE$0);

            return target;
        }
    }

    /**
     * An XML getReservationDataResponse(@http://ws_1_0.serviceprovider.nettracer.aero).
     *
     * This is a complex type.
     */
    public static class GetReservationDataResponseImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
        implements aero.nettracer.serviceprovider.ws_1_0.GetReservationDataResponseDocument.GetReservationDataResponse {
        private static final javax.xml.namespace.QName RETURN$0 = new javax.xml.namespace.QName("http://ws_1_0.serviceprovider.nettracer.aero",
                "return");

        public GetReservationDataResponseImpl(
            org.apache.xmlbeans.SchemaType sType) {
            super(sType);
        }

        /**
         * Gets the "return" element
         */
        public aero.nettracer.serviceprovider.ws_1_0.response.xsd.ReservationResponse getReturn() {
            synchronized (monitor()) {
                check_orphaned();

                aero.nettracer.serviceprovider.ws_1_0.response.xsd.ReservationResponse target =
                    null;
                target = (aero.nettracer.serviceprovider.ws_1_0.response.xsd.ReservationResponse) get_store()
                                                                                                      .find_element_user(RETURN$0,
                        0);

                if (target == null) {
                    return null;
                }

                return target;
            }
        }

        /**
         * Tests for nil "return" element
         */
        public boolean isNilReturn() {
            synchronized (monitor()) {
                check_orphaned();

                aero.nettracer.serviceprovider.ws_1_0.response.xsd.ReservationResponse target =
                    null;
                target = (aero.nettracer.serviceprovider.ws_1_0.response.xsd.ReservationResponse) get_store()
                                                                                                      .find_element_user(RETURN$0,
                        0);

                if (target == null) {
                    return false;
                }

                return target.isNil();
            }
        }

        /**
         * True if has "return" element
         */
        public boolean isSetReturn() {
            synchronized (monitor()) {
                check_orphaned();

                return get_store().count_elements(RETURN$0) != 0;
            }
        }

        /**
         * Sets the "return" element
         */
        public void setReturn(
            aero.nettracer.serviceprovider.ws_1_0.response.xsd.ReservationResponse xreturn) {
            synchronized (monitor()) {
                check_orphaned();

                aero.nettracer.serviceprovider.ws_1_0.response.xsd.ReservationResponse target =
                    null;
                target = (aero.nettracer.serviceprovider.ws_1_0.response.xsd.ReservationResponse) get_store()
                                                                                                      .find_element_user(RETURN$0,
                        0);

                if (target == null) {
                    target = (aero.nettracer.serviceprovider.ws_1_0.response.xsd.ReservationResponse) get_store()
                                                                                                          .add_element_user(RETURN$0);
                }

                target.set(xreturn);
            }
        }

        /**
         * Appends and returns a new empty "return" element
         */
        public aero.nettracer.serviceprovider.ws_1_0.response.xsd.ReservationResponse addNewReturn() {
            synchronized (monitor()) {
                check_orphaned();

                aero.nettracer.serviceprovider.ws_1_0.response.xsd.ReservationResponse target =
                    null;
                target = (aero.nettracer.serviceprovider.ws_1_0.response.xsd.ReservationResponse) get_store()
                                                                                                      .add_element_user(RETURN$0);

                return target;
            }
        }

        /**
         * Nils the "return" element
         */
        public void setNilReturn() {
            synchronized (monitor()) {
                check_orphaned();

                aero.nettracer.serviceprovider.ws_1_0.response.xsd.ReservationResponse target =
                    null;
                target = (aero.nettracer.serviceprovider.ws_1_0.response.xsd.ReservationResponse) get_store()
                                                                                                      .find_element_user(RETURN$0,
                        0);

                if (target == null) {
                    target = (aero.nettracer.serviceprovider.ws_1_0.response.xsd.ReservationResponse) get_store()
                                                                                                          .add_element_user(RETURN$0);
                }

                target.setNil();
            }
        }

        /**
         * Unsets the "return" element
         */
        public void unsetReturn() {
            synchronized (monitor()) {
                check_orphaned();
                get_store().remove_element(RETURN$0, 0);
            }
        }
    }
}
