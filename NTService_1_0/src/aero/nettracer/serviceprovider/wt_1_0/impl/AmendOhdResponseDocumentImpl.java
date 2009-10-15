/*
 * An XML document type.
 * Localname: amendOhdResponse
 * Namespace: http://wt_1_0.serviceprovider.nettracer.aero
 * Java type: aero.nettracer.serviceprovider.wt_1_0.AmendOhdResponseDocument
 *
 * Automatically generated - do not modify.
 */
package aero.nettracer.serviceprovider.wt_1_0.impl;

/**
 * A document containing one amendOhdResponse(@http://wt_1_0.serviceprovider.nettracer.aero) element.
 *
 * This is a complex type.
 */
public class AmendOhdResponseDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
    implements aero.nettracer.serviceprovider.wt_1_0.AmendOhdResponseDocument {
    private static final javax.xml.namespace.QName AMENDOHDRESPONSE$0 = new javax.xml.namespace.QName("http://wt_1_0.serviceprovider.nettracer.aero",
            "amendOhdResponse");

    public AmendOhdResponseDocumentImpl(org.apache.xmlbeans.SchemaType sType) {
        super(sType);
    }

    /**
     * Gets the "amendOhdResponse" element
     */
    public aero.nettracer.serviceprovider.wt_1_0.AmendOhdResponseDocument.AmendOhdResponse getAmendOhdResponse() {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.wt_1_0.AmendOhdResponseDocument.AmendOhdResponse target =
                null;
            target = (aero.nettracer.serviceprovider.wt_1_0.AmendOhdResponseDocument.AmendOhdResponse) get_store()
                                                                                                           .find_element_user(AMENDOHDRESPONSE$0,
                    0);

            if (target == null) {
                return null;
            }

            return target;
        }
    }

    /**
     * Sets the "amendOhdResponse" element
     */
    public void setAmendOhdResponse(
        aero.nettracer.serviceprovider.wt_1_0.AmendOhdResponseDocument.AmendOhdResponse amendOhdResponse) {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.wt_1_0.AmendOhdResponseDocument.AmendOhdResponse target =
                null;
            target = (aero.nettracer.serviceprovider.wt_1_0.AmendOhdResponseDocument.AmendOhdResponse) get_store()
                                                                                                           .find_element_user(AMENDOHDRESPONSE$0,
                    0);

            if (target == null) {
                target = (aero.nettracer.serviceprovider.wt_1_0.AmendOhdResponseDocument.AmendOhdResponse) get_store()
                                                                                                               .add_element_user(AMENDOHDRESPONSE$0);
            }

            target.set(amendOhdResponse);
        }
    }

    /**
     * Appends and returns a new empty "amendOhdResponse" element
     */
    public aero.nettracer.serviceprovider.wt_1_0.AmendOhdResponseDocument.AmendOhdResponse addNewAmendOhdResponse() {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.wt_1_0.AmendOhdResponseDocument.AmendOhdResponse target =
                null;
            target = (aero.nettracer.serviceprovider.wt_1_0.AmendOhdResponseDocument.AmendOhdResponse) get_store()
                                                                                                           .add_element_user(AMENDOHDRESPONSE$0);

            return target;
        }
    }

    /**
     * An XML amendOhdResponse(@http://wt_1_0.serviceprovider.nettracer.aero).
     *
     * This is a complex type.
     */
    public static class AmendOhdResponseImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
        implements aero.nettracer.serviceprovider.wt_1_0.AmendOhdResponseDocument.AmendOhdResponse {
        private static final javax.xml.namespace.QName RETURN$0 = new javax.xml.namespace.QName("http://wt_1_0.serviceprovider.nettracer.aero",
                "return");

        public AmendOhdResponseImpl(org.apache.xmlbeans.SchemaType sType) {
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
