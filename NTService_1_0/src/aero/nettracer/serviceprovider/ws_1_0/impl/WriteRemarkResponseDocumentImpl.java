/*
 * An XML document type.
 * Localname: writeRemarkResponse
 * Namespace: http://ws_1_0.serviceprovider.nettracer.aero
 * Java type: aero.nettracer.serviceprovider.ws_1_0.WriteRemarkResponseDocument
 *
 * Automatically generated - do not modify.
 */
package aero.nettracer.serviceprovider.ws_1_0.impl;

/**
 * A document containing one writeRemarkResponse(@http://ws_1_0.serviceprovider.nettracer.aero) element.
 *
 * This is a complex type.
 */
public class WriteRemarkResponseDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
    implements aero.nettracer.serviceprovider.ws_1_0.WriteRemarkResponseDocument {
    private static final javax.xml.namespace.QName WRITEREMARKRESPONSE$0 = new javax.xml.namespace.QName("http://ws_1_0.serviceprovider.nettracer.aero",
            "writeRemarkResponse");

    public WriteRemarkResponseDocumentImpl(org.apache.xmlbeans.SchemaType sType) {
        super(sType);
    }

    /**
     * Gets the "writeRemarkResponse" element
     */
    public aero.nettracer.serviceprovider.ws_1_0.WriteRemarkResponseDocument.WriteRemarkResponse getWriteRemarkResponse() {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.ws_1_0.WriteRemarkResponseDocument.WriteRemarkResponse target =
                null;
            target = (aero.nettracer.serviceprovider.ws_1_0.WriteRemarkResponseDocument.WriteRemarkResponse) get_store()
                                                                                                                 .find_element_user(WRITEREMARKRESPONSE$0,
                    0);

            if (target == null) {
                return null;
            }

            return target;
        }
    }

    /**
     * Sets the "writeRemarkResponse" element
     */
    public void setWriteRemarkResponse(
        aero.nettracer.serviceprovider.ws_1_0.WriteRemarkResponseDocument.WriteRemarkResponse writeRemarkResponse) {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.ws_1_0.WriteRemarkResponseDocument.WriteRemarkResponse target =
                null;
            target = (aero.nettracer.serviceprovider.ws_1_0.WriteRemarkResponseDocument.WriteRemarkResponse) get_store()
                                                                                                                 .find_element_user(WRITEREMARKRESPONSE$0,
                    0);

            if (target == null) {
                target = (aero.nettracer.serviceprovider.ws_1_0.WriteRemarkResponseDocument.WriteRemarkResponse) get_store()
                                                                                                                     .add_element_user(WRITEREMARKRESPONSE$0);
            }

            target.set(writeRemarkResponse);
        }
    }

    /**
     * Appends and returns a new empty "writeRemarkResponse" element
     */
    public aero.nettracer.serviceprovider.ws_1_0.WriteRemarkResponseDocument.WriteRemarkResponse addNewWriteRemarkResponse() {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.ws_1_0.WriteRemarkResponseDocument.WriteRemarkResponse target =
                null;
            target = (aero.nettracer.serviceprovider.ws_1_0.WriteRemarkResponseDocument.WriteRemarkResponse) get_store()
                                                                                                                 .add_element_user(WRITEREMARKRESPONSE$0);

            return target;
        }
    }

    /**
     * An XML writeRemarkResponse(@http://ws_1_0.serviceprovider.nettracer.aero).
     *
     * This is a complex type.
     */
    public static class WriteRemarkResponseImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
        implements aero.nettracer.serviceprovider.ws_1_0.WriteRemarkResponseDocument.WriteRemarkResponse {
        private static final javax.xml.namespace.QName RETURN$0 = new javax.xml.namespace.QName("http://ws_1_0.serviceprovider.nettracer.aero",
                "return");

        public WriteRemarkResponseImpl(org.apache.xmlbeans.SchemaType sType) {
            super(sType);
        }

        /**
         * Gets the "return" element
         */
        public aero.nettracer.serviceprovider.ws_1_0.response.xsd.RemarkResponse getReturn() {
            synchronized (monitor()) {
                check_orphaned();

                aero.nettracer.serviceprovider.ws_1_0.response.xsd.RemarkResponse target =
                    null;
                target = (aero.nettracer.serviceprovider.ws_1_0.response.xsd.RemarkResponse) get_store()
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

                aero.nettracer.serviceprovider.ws_1_0.response.xsd.RemarkResponse target =
                    null;
                target = (aero.nettracer.serviceprovider.ws_1_0.response.xsd.RemarkResponse) get_store()
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
            aero.nettracer.serviceprovider.ws_1_0.response.xsd.RemarkResponse xreturn) {
            synchronized (monitor()) {
                check_orphaned();

                aero.nettracer.serviceprovider.ws_1_0.response.xsd.RemarkResponse target =
                    null;
                target = (aero.nettracer.serviceprovider.ws_1_0.response.xsd.RemarkResponse) get_store()
                                                                                                 .find_element_user(RETURN$0,
                        0);

                if (target == null) {
                    target = (aero.nettracer.serviceprovider.ws_1_0.response.xsd.RemarkResponse) get_store()
                                                                                                     .add_element_user(RETURN$0);
                }

                target.set(xreturn);
            }
        }

        /**
         * Appends and returns a new empty "return" element
         */
        public aero.nettracer.serviceprovider.ws_1_0.response.xsd.RemarkResponse addNewReturn() {
            synchronized (monitor()) {
                check_orphaned();

                aero.nettracer.serviceprovider.ws_1_0.response.xsd.RemarkResponse target =
                    null;
                target = (aero.nettracer.serviceprovider.ws_1_0.response.xsd.RemarkResponse) get_store()
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

                aero.nettracer.serviceprovider.ws_1_0.response.xsd.RemarkResponse target =
                    null;
                target = (aero.nettracer.serviceprovider.ws_1_0.response.xsd.RemarkResponse) get_store()
                                                                                                 .find_element_user(RETURN$0,
                        0);

                if (target == null) {
                    target = (aero.nettracer.serviceprovider.ws_1_0.response.xsd.RemarkResponse) get_store()
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
