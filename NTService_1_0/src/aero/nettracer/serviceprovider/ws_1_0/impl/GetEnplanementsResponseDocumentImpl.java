/*
 * An XML document type.
 * Localname: getEnplanementsResponse
 * Namespace: http://ws_1_0.serviceprovider.nettracer.aero
 * Java type: aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsResponseDocument
 *
 * Automatically generated - do not modify.
 */
package aero.nettracer.serviceprovider.ws_1_0.impl;

/**
 * A document containing one getEnplanementsResponse(@http://ws_1_0.serviceprovider.nettracer.aero) element.
 *
 * This is a complex type.
 */
public class GetEnplanementsResponseDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
    implements aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsResponseDocument {
    private static final javax.xml.namespace.QName GETENPLANEMENTSRESPONSE$0 = new javax.xml.namespace.QName("http://ws_1_0.serviceprovider.nettracer.aero",
            "getEnplanementsResponse");

    public GetEnplanementsResponseDocumentImpl(
        org.apache.xmlbeans.SchemaType sType) {
        super(sType);
    }

    /**
     * Gets the "getEnplanementsResponse" element
     */
    public aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsResponseDocument.GetEnplanementsResponse getGetEnplanementsResponse() {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsResponseDocument.GetEnplanementsResponse target =
                null;
            target = (aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsResponseDocument.GetEnplanementsResponse) get_store()
                                                                                                                         .find_element_user(GETENPLANEMENTSRESPONSE$0,
                    0);

            if (target == null) {
                return null;
            }

            return target;
        }
    }

    /**
     * Sets the "getEnplanementsResponse" element
     */
    public void setGetEnplanementsResponse(
        aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsResponseDocument.GetEnplanementsResponse getEnplanementsResponse) {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsResponseDocument.GetEnplanementsResponse target =
                null;
            target = (aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsResponseDocument.GetEnplanementsResponse) get_store()
                                                                                                                         .find_element_user(GETENPLANEMENTSRESPONSE$0,
                    0);

            if (target == null) {
                target = (aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsResponseDocument.GetEnplanementsResponse) get_store()
                                                                                                                             .add_element_user(GETENPLANEMENTSRESPONSE$0);
            }

            target.set(getEnplanementsResponse);
        }
    }

    /**
     * Appends and returns a new empty "getEnplanementsResponse" element
     */
    public aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsResponseDocument.GetEnplanementsResponse addNewGetEnplanementsResponse() {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsResponseDocument.GetEnplanementsResponse target =
                null;
            target = (aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsResponseDocument.GetEnplanementsResponse) get_store()
                                                                                                                         .add_element_user(GETENPLANEMENTSRESPONSE$0);

            return target;
        }
    }

    /**
     * An XML getEnplanementsResponse(@http://ws_1_0.serviceprovider.nettracer.aero).
     *
     * This is a complex type.
     */
    public static class GetEnplanementsResponseImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
        implements aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsResponseDocument.GetEnplanementsResponse {
        private static final javax.xml.namespace.QName RETURN$0 = new javax.xml.namespace.QName("http://ws_1_0.serviceprovider.nettracer.aero",
                "return");

        public GetEnplanementsResponseImpl(org.apache.xmlbeans.SchemaType sType) {
            super(sType);
        }

        /**
         * Gets the "return" element
         */
        public aero.nettracer.serviceprovider.ws_1_0.response.xsd.EnplanementResponse getReturn() {
            synchronized (monitor()) {
                check_orphaned();

                aero.nettracer.serviceprovider.ws_1_0.response.xsd.EnplanementResponse target =
                    null;
                target = (aero.nettracer.serviceprovider.ws_1_0.response.xsd.EnplanementResponse) get_store()
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

                aero.nettracer.serviceprovider.ws_1_0.response.xsd.EnplanementResponse target =
                    null;
                target = (aero.nettracer.serviceprovider.ws_1_0.response.xsd.EnplanementResponse) get_store()
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
            aero.nettracer.serviceprovider.ws_1_0.response.xsd.EnplanementResponse xreturn) {
            synchronized (monitor()) {
                check_orphaned();

                aero.nettracer.serviceprovider.ws_1_0.response.xsd.EnplanementResponse target =
                    null;
                target = (aero.nettracer.serviceprovider.ws_1_0.response.xsd.EnplanementResponse) get_store()
                                                                                                      .find_element_user(RETURN$0,
                        0);

                if (target == null) {
                    target = (aero.nettracer.serviceprovider.ws_1_0.response.xsd.EnplanementResponse) get_store()
                                                                                                          .add_element_user(RETURN$0);
                }

                target.set(xreturn);
            }
        }

        /**
         * Appends and returns a new empty "return" element
         */
        public aero.nettracer.serviceprovider.ws_1_0.response.xsd.EnplanementResponse addNewReturn() {
            synchronized (monitor()) {
                check_orphaned();

                aero.nettracer.serviceprovider.ws_1_0.response.xsd.EnplanementResponse target =
                    null;
                target = (aero.nettracer.serviceprovider.ws_1_0.response.xsd.EnplanementResponse) get_store()
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

                aero.nettracer.serviceprovider.ws_1_0.response.xsd.EnplanementResponse target =
                    null;
                target = (aero.nettracer.serviceprovider.ws_1_0.response.xsd.EnplanementResponse) get_store()
                                                                                                      .find_element_user(RETURN$0,
                        0);

                if (target == null) {
                    target = (aero.nettracer.serviceprovider.ws_1_0.response.xsd.EnplanementResponse) get_store()
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
