/*
 * An XML document type.
 * Localname: getIncidentPV2Response
 * Namespace: http://passengerview.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.passengerview.GetIncidentPV2ResponseDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.passengerview.impl;

/**
 * A document containing one getIncidentPV2Response(@http://passengerview.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public class GetIncidentPV2ResponseDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
    implements com.bagnet.nettracer.ws.passengerview.GetIncidentPV2ResponseDocument {
    private static final javax.xml.namespace.QName GETINCIDENTPV2RESPONSE$0 = new javax.xml.namespace.QName("http://passengerview.ws.nettracer.bagnet.com",
            "getIncidentPV2Response");

    public GetIncidentPV2ResponseDocumentImpl(
        org.apache.xmlbeans.SchemaType sType) {
        super(sType);
    }

    /**
     * Gets the "getIncidentPV2Response" element
     */
    public com.bagnet.nettracer.ws.passengerview.GetIncidentPV2ResponseDocument.GetIncidentPV2Response getGetIncidentPV2Response() {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.passengerview.GetIncidentPV2ResponseDocument.GetIncidentPV2Response target =
                null;
            target = (com.bagnet.nettracer.ws.passengerview.GetIncidentPV2ResponseDocument.GetIncidentPV2Response) get_store()
                                                                                                                       .find_element_user(GETINCIDENTPV2RESPONSE$0,
                    0);

            if (target == null) {
                return null;
            }

            return target;
        }
    }

    /**
     * Sets the "getIncidentPV2Response" element
     */
    public void setGetIncidentPV2Response(
        com.bagnet.nettracer.ws.passengerview.GetIncidentPV2ResponseDocument.GetIncidentPV2Response getIncidentPV2Response) {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.passengerview.GetIncidentPV2ResponseDocument.GetIncidentPV2Response target =
                null;
            target = (com.bagnet.nettracer.ws.passengerview.GetIncidentPV2ResponseDocument.GetIncidentPV2Response) get_store()
                                                                                                                       .find_element_user(GETINCIDENTPV2RESPONSE$0,
                    0);

            if (target == null) {
                target = (com.bagnet.nettracer.ws.passengerview.GetIncidentPV2ResponseDocument.GetIncidentPV2Response) get_store()
                                                                                                                           .add_element_user(GETINCIDENTPV2RESPONSE$0);
            }

            target.set(getIncidentPV2Response);
        }
    }

    /**
     * Appends and returns a new empty "getIncidentPV2Response" element
     */
    public com.bagnet.nettracer.ws.passengerview.GetIncidentPV2ResponseDocument.GetIncidentPV2Response addNewGetIncidentPV2Response() {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.passengerview.GetIncidentPV2ResponseDocument.GetIncidentPV2Response target =
                null;
            target = (com.bagnet.nettracer.ws.passengerview.GetIncidentPV2ResponseDocument.GetIncidentPV2Response) get_store()
                                                                                                                       .add_element_user(GETINCIDENTPV2RESPONSE$0);

            return target;
        }
    }

    /**
     * An XML getIncidentPV2Response(@http://passengerview.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public static class GetIncidentPV2ResponseImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
        implements com.bagnet.nettracer.ws.passengerview.GetIncidentPV2ResponseDocument.GetIncidentPV2Response {
        private static final javax.xml.namespace.QName RETURN$0 = new javax.xml.namespace.QName("http://passengerview.ws.nettracer.bagnet.com",
                "return");

        public GetIncidentPV2ResponseImpl(org.apache.xmlbeans.SchemaType sType) {
            super(sType);
        }

        /**
         * Gets the "return" element
         */
        public com.bagnet.nettracer.ws.core.pojo.xsd.WSPVIncident2 getReturn() {
            synchronized (monitor()) {
                check_orphaned();

                com.bagnet.nettracer.ws.core.pojo.xsd.WSPVIncident2 target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVIncident2) get_store()
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

                com.bagnet.nettracer.ws.core.pojo.xsd.WSPVIncident2 target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVIncident2) get_store()
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
            com.bagnet.nettracer.ws.core.pojo.xsd.WSPVIncident2 xreturn) {
            synchronized (monitor()) {
                check_orphaned();

                com.bagnet.nettracer.ws.core.pojo.xsd.WSPVIncident2 target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVIncident2) get_store()
                                                                                   .find_element_user(RETURN$0,
                        0);

                if (target == null) {
                    target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVIncident2) get_store()
                                                                                       .add_element_user(RETURN$0);
                }

                target.set(xreturn);
            }
        }

        /**
         * Appends and returns a new empty "return" element
         */
        public com.bagnet.nettracer.ws.core.pojo.xsd.WSPVIncident2 addNewReturn() {
            synchronized (monitor()) {
                check_orphaned();

                com.bagnet.nettracer.ws.core.pojo.xsd.WSPVIncident2 target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVIncident2) get_store()
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

                com.bagnet.nettracer.ws.core.pojo.xsd.WSPVIncident2 target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVIncident2) get_store()
                                                                                   .find_element_user(RETURN$0,
                        0);

                if (target == null) {
                    target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVIncident2) get_store()
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
