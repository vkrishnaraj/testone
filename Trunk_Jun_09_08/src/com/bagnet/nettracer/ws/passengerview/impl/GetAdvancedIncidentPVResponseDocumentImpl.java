/*
 * An XML document type.
 * Localname: getAdvancedIncidentPVResponse
 * Namespace: http://passengerview.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentPVResponseDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.passengerview.impl;

/**
 * A document containing one getAdvancedIncidentPVResponse(@http://passengerview.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public class GetAdvancedIncidentPVResponseDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
    implements com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentPVResponseDocument {
    private static final javax.xml.namespace.QName GETADVANCEDINCIDENTPVRESPONSE$0 =
        new javax.xml.namespace.QName("http://passengerview.ws.nettracer.bagnet.com",
            "getAdvancedIncidentPVResponse");

    public GetAdvancedIncidentPVResponseDocumentImpl(
        org.apache.xmlbeans.SchemaType sType) {
        super(sType);
    }

    /**
     * Gets the "getAdvancedIncidentPVResponse" element
     */
    public com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentPVResponseDocument.GetAdvancedIncidentPVResponse getGetAdvancedIncidentPVResponse() {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentPVResponseDocument.GetAdvancedIncidentPVResponse target =
                null;
            target = (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentPVResponseDocument.GetAdvancedIncidentPVResponse) get_store()
                                                                                                                                     .find_element_user(GETADVANCEDINCIDENTPVRESPONSE$0,
                    0);

            if (target == null) {
                return null;
            }

            return target;
        }
    }

    /**
     * Sets the "getAdvancedIncidentPVResponse" element
     */
    public void setGetAdvancedIncidentPVResponse(
        com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentPVResponseDocument.GetAdvancedIncidentPVResponse getAdvancedIncidentPVResponse) {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentPVResponseDocument.GetAdvancedIncidentPVResponse target =
                null;
            target = (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentPVResponseDocument.GetAdvancedIncidentPVResponse) get_store()
                                                                                                                                     .find_element_user(GETADVANCEDINCIDENTPVRESPONSE$0,
                    0);

            if (target == null) {
                target = (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentPVResponseDocument.GetAdvancedIncidentPVResponse) get_store()
                                                                                                                                         .add_element_user(GETADVANCEDINCIDENTPVRESPONSE$0);
            }

            target.set(getAdvancedIncidentPVResponse);
        }
    }

    /**
     * Appends and returns a new empty "getAdvancedIncidentPVResponse" element
     */
    public com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentPVResponseDocument.GetAdvancedIncidentPVResponse addNewGetAdvancedIncidentPVResponse() {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentPVResponseDocument.GetAdvancedIncidentPVResponse target =
                null;
            target = (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentPVResponseDocument.GetAdvancedIncidentPVResponse) get_store()
                                                                                                                                     .add_element_user(GETADVANCEDINCIDENTPVRESPONSE$0);

            return target;
        }
    }

    /**
     * An XML getAdvancedIncidentPVResponse(@http://passengerview.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public static class GetAdvancedIncidentPVResponseImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
        implements com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentPVResponseDocument.GetAdvancedIncidentPVResponse {
        private static final javax.xml.namespace.QName RETURN$0 = new javax.xml.namespace.QName("http://passengerview.ws.nettracer.bagnet.com",
                "return");

        public GetAdvancedIncidentPVResponseImpl(
            org.apache.xmlbeans.SchemaType sType) {
            super(sType);
        }

        /**
         * Gets the "return" element
         */
        public com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident getReturn() {
            synchronized (monitor()) {
                check_orphaned();

                com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident target =
                    null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident) get_store()
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

                com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident target =
                    null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident) get_store()
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
            com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident xreturn) {
            synchronized (monitor()) {
                check_orphaned();

                com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident target =
                    null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident) get_store()
                                                                                          .find_element_user(RETURN$0,
                        0);

                if (target == null) {
                    target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident) get_store()
                                                                                              .add_element_user(RETURN$0);
                }

                target.set(xreturn);
            }
        }

        /**
         * Appends and returns a new empty "return" element
         */
        public com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident addNewReturn() {
            synchronized (monitor()) {
                check_orphaned();

                com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident target =
                    null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident) get_store()
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

                com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident target =
                    null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident) get_store()
                                                                                          .find_element_user(RETURN$0,
                        0);

                if (target == null) {
                    target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident) get_store()
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
