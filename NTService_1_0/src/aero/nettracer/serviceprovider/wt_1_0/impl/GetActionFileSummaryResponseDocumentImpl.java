/*
 * An XML document type.
 * Localname: getActionFileSummaryResponse
 * Namespace: http://wt_1_0.serviceprovider.nettracer.aero
 * Java type: aero.nettracer.serviceprovider.wt_1_0.GetActionFileSummaryResponseDocument
 *
 * Automatically generated - do not modify.
 */
package aero.nettracer.serviceprovider.wt_1_0.impl;

/**
 * A document containing one getActionFileSummaryResponse(@http://wt_1_0.serviceprovider.nettracer.aero) element.
 *
 * This is a complex type.
 */
public class GetActionFileSummaryResponseDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
    implements aero.nettracer.serviceprovider.wt_1_0.GetActionFileSummaryResponseDocument {
    private static final javax.xml.namespace.QName GETACTIONFILESUMMARYRESPONSE$0 =
        new javax.xml.namespace.QName("http://wt_1_0.serviceprovider.nettracer.aero",
            "getActionFileSummaryResponse");

    public GetActionFileSummaryResponseDocumentImpl(
        org.apache.xmlbeans.SchemaType sType) {
        super(sType);
    }

    /**
     * Gets the "getActionFileSummaryResponse" element
     */
    public aero.nettracer.serviceprovider.wt_1_0.GetActionFileSummaryResponseDocument.GetActionFileSummaryResponse getGetActionFileSummaryResponse() {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.wt_1_0.GetActionFileSummaryResponseDocument.GetActionFileSummaryResponse target =
                null;
            target = (aero.nettracer.serviceprovider.wt_1_0.GetActionFileSummaryResponseDocument.GetActionFileSummaryResponse) get_store()
                                                                                                                                   .find_element_user(GETACTIONFILESUMMARYRESPONSE$0,
                    0);

            if (target == null) {
                return null;
            }

            return target;
        }
    }

    /**
     * Sets the "getActionFileSummaryResponse" element
     */
    public void setGetActionFileSummaryResponse(
        aero.nettracer.serviceprovider.wt_1_0.GetActionFileSummaryResponseDocument.GetActionFileSummaryResponse getActionFileSummaryResponse) {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.wt_1_0.GetActionFileSummaryResponseDocument.GetActionFileSummaryResponse target =
                null;
            target = (aero.nettracer.serviceprovider.wt_1_0.GetActionFileSummaryResponseDocument.GetActionFileSummaryResponse) get_store()
                                                                                                                                   .find_element_user(GETACTIONFILESUMMARYRESPONSE$0,
                    0);

            if (target == null) {
                target = (aero.nettracer.serviceprovider.wt_1_0.GetActionFileSummaryResponseDocument.GetActionFileSummaryResponse) get_store()
                                                                                                                                       .add_element_user(GETACTIONFILESUMMARYRESPONSE$0);
            }

            target.set(getActionFileSummaryResponse);
        }
    }

    /**
     * Appends and returns a new empty "getActionFileSummaryResponse" element
     */
    public aero.nettracer.serviceprovider.wt_1_0.GetActionFileSummaryResponseDocument.GetActionFileSummaryResponse addNewGetActionFileSummaryResponse() {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.wt_1_0.GetActionFileSummaryResponseDocument.GetActionFileSummaryResponse target =
                null;
            target = (aero.nettracer.serviceprovider.wt_1_0.GetActionFileSummaryResponseDocument.GetActionFileSummaryResponse) get_store()
                                                                                                                                   .add_element_user(GETACTIONFILESUMMARYRESPONSE$0);

            return target;
        }
    }

    /**
     * An XML getActionFileSummaryResponse(@http://wt_1_0.serviceprovider.nettracer.aero).
     *
     * This is a complex type.
     */
    public static class GetActionFileSummaryResponseImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
        implements aero.nettracer.serviceprovider.wt_1_0.GetActionFileSummaryResponseDocument.GetActionFileSummaryResponse {
        private static final javax.xml.namespace.QName RETURN$0 = new javax.xml.namespace.QName("http://wt_1_0.serviceprovider.nettracer.aero",
                "return");

        public GetActionFileSummaryResponseImpl(
            org.apache.xmlbeans.SchemaType sType) {
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
