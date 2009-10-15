/*
 * An XML document type.
 * Localname: closeOhdResponse
 * Namespace: http://wt_1_0.serviceprovider.nettracer.aero
 * Java type: aero.nettracer.serviceprovider.wt_1_0.CloseOhdResponseDocument
 *
 * Automatically generated - do not modify.
 */
package aero.nettracer.serviceprovider.wt_1_0.impl;

/**
 * A document containing one closeOhdResponse(@http://wt_1_0.serviceprovider.nettracer.aero) element.
 *
 * This is a complex type.
 */
public class CloseOhdResponseDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
    implements aero.nettracer.serviceprovider.wt_1_0.CloseOhdResponseDocument {
    private static final javax.xml.namespace.QName CLOSEOHDRESPONSE$0 = new javax.xml.namespace.QName("http://wt_1_0.serviceprovider.nettracer.aero",
            "closeOhdResponse");

    public CloseOhdResponseDocumentImpl(org.apache.xmlbeans.SchemaType sType) {
        super(sType);
    }

    /**
     * Gets the "closeOhdResponse" element
     */
    public aero.nettracer.serviceprovider.wt_1_0.CloseOhdResponseDocument.CloseOhdResponse getCloseOhdResponse() {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.wt_1_0.CloseOhdResponseDocument.CloseOhdResponse target =
                null;
            target = (aero.nettracer.serviceprovider.wt_1_0.CloseOhdResponseDocument.CloseOhdResponse) get_store()
                                                                                                           .find_element_user(CLOSEOHDRESPONSE$0,
                    0);

            if (target == null) {
                return null;
            }

            return target;
        }
    }

    /**
     * Sets the "closeOhdResponse" element
     */
    public void setCloseOhdResponse(
        aero.nettracer.serviceprovider.wt_1_0.CloseOhdResponseDocument.CloseOhdResponse closeOhdResponse) {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.wt_1_0.CloseOhdResponseDocument.CloseOhdResponse target =
                null;
            target = (aero.nettracer.serviceprovider.wt_1_0.CloseOhdResponseDocument.CloseOhdResponse) get_store()
                                                                                                           .find_element_user(CLOSEOHDRESPONSE$0,
                    0);

            if (target == null) {
                target = (aero.nettracer.serviceprovider.wt_1_0.CloseOhdResponseDocument.CloseOhdResponse) get_store()
                                                                                                               .add_element_user(CLOSEOHDRESPONSE$0);
            }

            target.set(closeOhdResponse);
        }
    }

    /**
     * Appends and returns a new empty "closeOhdResponse" element
     */
    public aero.nettracer.serviceprovider.wt_1_0.CloseOhdResponseDocument.CloseOhdResponse addNewCloseOhdResponse() {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.wt_1_0.CloseOhdResponseDocument.CloseOhdResponse target =
                null;
            target = (aero.nettracer.serviceprovider.wt_1_0.CloseOhdResponseDocument.CloseOhdResponse) get_store()
                                                                                                           .add_element_user(CLOSEOHDRESPONSE$0);

            return target;
        }
    }

    /**
     * An XML closeOhdResponse(@http://wt_1_0.serviceprovider.nettracer.aero).
     *
     * This is a complex type.
     */
    public static class CloseOhdResponseImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
        implements aero.nettracer.serviceprovider.wt_1_0.CloseOhdResponseDocument.CloseOhdResponse {
        private static final javax.xml.namespace.QName RETURN$0 = new javax.xml.namespace.QName("http://wt_1_0.serviceprovider.nettracer.aero",
                "return");

        public CloseOhdResponseImpl(org.apache.xmlbeans.SchemaType sType) {
            super(sType);
        }

        /**
         * Gets the "return" element
         */
        public aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse getReturn() {
            synchronized (monitor()) {
                check_orphaned();

                aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse target =
                    null;
                target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse) get_store()
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

                aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse target =
                    null;
                target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse) get_store()
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
            aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse xreturn) {
            synchronized (monitor()) {
                check_orphaned();

                aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse target =
                    null;
                target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse) get_store()
                                                                                                    .find_element_user(RETURN$0,
                        0);

                if (target == null) {
                    target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse) get_store()
                                                                                                        .add_element_user(RETURN$0);
                }

                target.set(xreturn);
            }
        }

        /**
         * Appends and returns a new empty "return" element
         */
        public aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse addNewReturn() {
            synchronized (monitor()) {
                check_orphaned();

                aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse target =
                    null;
                target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse) get_store()
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

                aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse target =
                    null;
                target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse) get_store()
                                                                                                    .find_element_user(RETURN$0,
                        0);

                if (target == null) {
                    target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse) get_store()
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
