/*
 * An XML document type.
 * Localname: getIncidentPVAdvancedResponse
 * Namespace: http://passengerview.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.passengerview.GetIncidentPVAdvancedResponseDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.passengerview.impl;

/**
 * A document containing one getIncidentPVAdvancedResponse(@http://passengerview.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public class GetIncidentPVAdvancedResponseDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
    implements com.bagnet.nettracer.ws.passengerview.GetIncidentPVAdvancedResponseDocument {
    private static final javax.xml.namespace.QName GETINCIDENTPVADVANCEDRESPONSE$0 =
        new javax.xml.namespace.QName("http://passengerview.ws.nettracer.bagnet.com",
            "getIncidentPVAdvancedResponse");

    public GetIncidentPVAdvancedResponseDocumentImpl(
        org.apache.xmlbeans.SchemaType sType) {
        super(sType);
    }

    /**
     * Gets the "getIncidentPVAdvancedResponse" element
     */
    public com.bagnet.nettracer.ws.passengerview.GetIncidentPVAdvancedResponseDocument.GetIncidentPVAdvancedResponse getGetIncidentPVAdvancedResponse() {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.passengerview.GetIncidentPVAdvancedResponseDocument.GetIncidentPVAdvancedResponse target =
                null;
            target = (com.bagnet.nettracer.ws.passengerview.GetIncidentPVAdvancedResponseDocument.GetIncidentPVAdvancedResponse) get_store()
                                                                                                                                     .find_element_user(GETINCIDENTPVADVANCEDRESPONSE$0,
                    0);

            if (target == null) {
                return null;
            }

            return target;
        }
    }

    /**
     * Sets the "getIncidentPVAdvancedResponse" element
     */
    public void setGetIncidentPVAdvancedResponse(
        com.bagnet.nettracer.ws.passengerview.GetIncidentPVAdvancedResponseDocument.GetIncidentPVAdvancedResponse getIncidentPVAdvancedResponse) {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.passengerview.GetIncidentPVAdvancedResponseDocument.GetIncidentPVAdvancedResponse target =
                null;
            target = (com.bagnet.nettracer.ws.passengerview.GetIncidentPVAdvancedResponseDocument.GetIncidentPVAdvancedResponse) get_store()
                                                                                                                                     .find_element_user(GETINCIDENTPVADVANCEDRESPONSE$0,
                    0);

            if (target == null) {
                target = (com.bagnet.nettracer.ws.passengerview.GetIncidentPVAdvancedResponseDocument.GetIncidentPVAdvancedResponse) get_store()
                                                                                                                                         .add_element_user(GETINCIDENTPVADVANCEDRESPONSE$0);
            }

            target.set(getIncidentPVAdvancedResponse);
        }
    }

    /**
     * Appends and returns a new empty "getIncidentPVAdvancedResponse" element
     */
    public com.bagnet.nettracer.ws.passengerview.GetIncidentPVAdvancedResponseDocument.GetIncidentPVAdvancedResponse addNewGetIncidentPVAdvancedResponse() {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.passengerview.GetIncidentPVAdvancedResponseDocument.GetIncidentPVAdvancedResponse target =
                null;
            target = (com.bagnet.nettracer.ws.passengerview.GetIncidentPVAdvancedResponseDocument.GetIncidentPVAdvancedResponse) get_store()
                                                                                                                                     .add_element_user(GETINCIDENTPVADVANCEDRESPONSE$0);

            return target;
        }
    }

    /**
     * An XML getIncidentPVAdvancedResponse(@http://passengerview.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public static class GetIncidentPVAdvancedResponseImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
        implements com.bagnet.nettracer.ws.passengerview.GetIncidentPVAdvancedResponseDocument.GetIncidentPVAdvancedResponse {
        private static final javax.xml.namespace.QName RETURN$0 = new javax.xml.namespace.QName("http://passengerview.ws.nettracer.bagnet.com",
                "return");

        public GetIncidentPVAdvancedResponseImpl(
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
