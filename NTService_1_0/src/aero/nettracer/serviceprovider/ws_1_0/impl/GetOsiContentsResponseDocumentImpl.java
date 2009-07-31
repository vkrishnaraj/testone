/*
 * An XML document type.
 * Localname: getOsiContentsResponse
 * Namespace: http://ws_1_0.serviceprovider.nettracer.aero
 * Java type: aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsResponseDocument
 *
 * Automatically generated - do not modify.
 */
package aero.nettracer.serviceprovider.ws_1_0.impl;

/**
 * A document containing one getOsiContentsResponse(@http://ws_1_0.serviceprovider.nettracer.aero) element.
 *
 * This is a complex type.
 */
public class GetOsiContentsResponseDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
    implements aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsResponseDocument {
    private static final javax.xml.namespace.QName GETOSICONTENTSRESPONSE$0 = new javax.xml.namespace.QName("http://ws_1_0.serviceprovider.nettracer.aero",
            "getOsiContentsResponse");

    public GetOsiContentsResponseDocumentImpl(
        org.apache.xmlbeans.SchemaType sType) {
        super(sType);
    }

    /**
     * Gets the "getOsiContentsResponse" element
     */
    public aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsResponseDocument.GetOsiContentsResponse getGetOsiContentsResponse() {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsResponseDocument.GetOsiContentsResponse target =
                null;
            target = (aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsResponseDocument.GetOsiContentsResponse) get_store()
                                                                                                                       .find_element_user(GETOSICONTENTSRESPONSE$0,
                    0);

            if (target == null) {
                return null;
            }

            return target;
        }
    }

    /**
     * Sets the "getOsiContentsResponse" element
     */
    public void setGetOsiContentsResponse(
        aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsResponseDocument.GetOsiContentsResponse getOsiContentsResponse) {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsResponseDocument.GetOsiContentsResponse target =
                null;
            target = (aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsResponseDocument.GetOsiContentsResponse) get_store()
                                                                                                                       .find_element_user(GETOSICONTENTSRESPONSE$0,
                    0);

            if (target == null) {
                target = (aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsResponseDocument.GetOsiContentsResponse) get_store()
                                                                                                                           .add_element_user(GETOSICONTENTSRESPONSE$0);
            }

            target.set(getOsiContentsResponse);
        }
    }

    /**
     * Appends and returns a new empty "getOsiContentsResponse" element
     */
    public aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsResponseDocument.GetOsiContentsResponse addNewGetOsiContentsResponse() {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsResponseDocument.GetOsiContentsResponse target =
                null;
            target = (aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsResponseDocument.GetOsiContentsResponse) get_store()
                                                                                                                       .add_element_user(GETOSICONTENTSRESPONSE$0);

            return target;
        }
    }

    /**
     * An XML getOsiContentsResponse(@http://ws_1_0.serviceprovider.nettracer.aero).
     *
     * This is a complex type.
     */
    public static class GetOsiContentsResponseImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
        implements aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsResponseDocument.GetOsiContentsResponse {
        private static final javax.xml.namespace.QName RETURN$0 = new javax.xml.namespace.QName("http://ws_1_0.serviceprovider.nettracer.aero",
                "return");

        public GetOsiContentsResponseImpl(org.apache.xmlbeans.SchemaType sType) {
            super(sType);
        }

        /**
         * Gets the "return" element
         */
        public aero.nettracer.serviceprovider.ws_1_0.response.xsd.OsiResponse getReturn() {
            synchronized (monitor()) {
                check_orphaned();

                aero.nettracer.serviceprovider.ws_1_0.response.xsd.OsiResponse target =
                    null;
                target = (aero.nettracer.serviceprovider.ws_1_0.response.xsd.OsiResponse) get_store()
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

                aero.nettracer.serviceprovider.ws_1_0.response.xsd.OsiResponse target =
                    null;
                target = (aero.nettracer.serviceprovider.ws_1_0.response.xsd.OsiResponse) get_store()
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
            aero.nettracer.serviceprovider.ws_1_0.response.xsd.OsiResponse xreturn) {
            synchronized (monitor()) {
                check_orphaned();

                aero.nettracer.serviceprovider.ws_1_0.response.xsd.OsiResponse target =
                    null;
                target = (aero.nettracer.serviceprovider.ws_1_0.response.xsd.OsiResponse) get_store()
                                                                                              .find_element_user(RETURN$0,
                        0);

                if (target == null) {
                    target = (aero.nettracer.serviceprovider.ws_1_0.response.xsd.OsiResponse) get_store()
                                                                                                  .add_element_user(RETURN$0);
                }

                target.set(xreturn);
            }
        }

        /**
         * Appends and returns a new empty "return" element
         */
        public aero.nettracer.serviceprovider.ws_1_0.response.xsd.OsiResponse addNewReturn() {
            synchronized (monitor()) {
                check_orphaned();

                aero.nettracer.serviceprovider.ws_1_0.response.xsd.OsiResponse target =
                    null;
                target = (aero.nettracer.serviceprovider.ws_1_0.response.xsd.OsiResponse) get_store()
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

                aero.nettracer.serviceprovider.ws_1_0.response.xsd.OsiResponse target =
                    null;
                target = (aero.nettracer.serviceprovider.ws_1_0.response.xsd.OsiResponse) get_store()
                                                                                              .find_element_user(RETURN$0,
                        0);

                if (target == null) {
                    target = (aero.nettracer.serviceprovider.ws_1_0.response.xsd.OsiResponse) get_store()
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
