/*
 * An XML document type.
 * Localname: authPassengerResponse
 * Namespace: http://onlineclaims.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.onlineclaims.AuthPassengerResponseDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.onlineclaims.impl;

/**
 * A document containing one authPassengerResponse(@http://onlineclaims.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public class AuthPassengerResponseDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
    implements com.bagnet.nettracer.ws.onlineclaims.AuthPassengerResponseDocument {
    private static final javax.xml.namespace.QName AUTHPASSENGERRESPONSE$0 = new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com",
            "authPassengerResponse");

    public AuthPassengerResponseDocumentImpl(
        org.apache.xmlbeans.SchemaType sType) {
        super(sType);
    }

    /**
     * Gets the "authPassengerResponse" element
     */
    public com.bagnet.nettracer.ws.onlineclaims.AuthPassengerResponseDocument.AuthPassengerResponse getAuthPassengerResponse() {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.onlineclaims.AuthPassengerResponseDocument.AuthPassengerResponse target =
                null;
            target = (com.bagnet.nettracer.ws.onlineclaims.AuthPassengerResponseDocument.AuthPassengerResponse) get_store()
                                                                                                                    .find_element_user(AUTHPASSENGERRESPONSE$0,
                    0);

            if (target == null) {
                return null;
            }

            return target;
        }
    }

    /**
     * Sets the "authPassengerResponse" element
     */
    public void setAuthPassengerResponse(
        com.bagnet.nettracer.ws.onlineclaims.AuthPassengerResponseDocument.AuthPassengerResponse authPassengerResponse) {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.onlineclaims.AuthPassengerResponseDocument.AuthPassengerResponse target =
                null;
            target = (com.bagnet.nettracer.ws.onlineclaims.AuthPassengerResponseDocument.AuthPassengerResponse) get_store()
                                                                                                                    .find_element_user(AUTHPASSENGERRESPONSE$0,
                    0);

            if (target == null) {
                target = (com.bagnet.nettracer.ws.onlineclaims.AuthPassengerResponseDocument.AuthPassengerResponse) get_store()
                                                                                                                        .add_element_user(AUTHPASSENGERRESPONSE$0);
            }

            target.set(authPassengerResponse);
        }
    }

    /**
     * Appends and returns a new empty "authPassengerResponse" element
     */
    public com.bagnet.nettracer.ws.onlineclaims.AuthPassengerResponseDocument.AuthPassengerResponse addNewAuthPassengerResponse() {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.onlineclaims.AuthPassengerResponseDocument.AuthPassengerResponse target =
                null;
            target = (com.bagnet.nettracer.ws.onlineclaims.AuthPassengerResponseDocument.AuthPassengerResponse) get_store()
                                                                                                                    .add_element_user(AUTHPASSENGERRESPONSE$0);

            return target;
        }
    }

    /**
     * An XML authPassengerResponse(@http://onlineclaims.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public static class AuthPassengerResponseImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
        implements com.bagnet.nettracer.ws.onlineclaims.AuthPassengerResponseDocument.AuthPassengerResponse {
        private static final javax.xml.namespace.QName RETURN$0 = new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com",
                "return");

        public AuthPassengerResponseImpl(org.apache.xmlbeans.SchemaType sType) {
            super(sType);
        }

        /**
         * Gets the "return" element
         */
        public com.bagnet.nettracer.ws.onlineclaims.xsd.PassengerView getReturn() {
            synchronized (monitor()) {
                check_orphaned();

                com.bagnet.nettracer.ws.onlineclaims.xsd.PassengerView target = null;
                target = (com.bagnet.nettracer.ws.onlineclaims.xsd.PassengerView) get_store()
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

                com.bagnet.nettracer.ws.onlineclaims.xsd.PassengerView target = null;
                target = (com.bagnet.nettracer.ws.onlineclaims.xsd.PassengerView) get_store()
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
            com.bagnet.nettracer.ws.onlineclaims.xsd.PassengerView xreturn) {
            synchronized (monitor()) {
                check_orphaned();

                com.bagnet.nettracer.ws.onlineclaims.xsd.PassengerView target = null;
                target = (com.bagnet.nettracer.ws.onlineclaims.xsd.PassengerView) get_store()
                                                                                      .find_element_user(RETURN$0,
                        0);

                if (target == null) {
                    target = (com.bagnet.nettracer.ws.onlineclaims.xsd.PassengerView) get_store()
                                                                                          .add_element_user(RETURN$0);
                }

                target.set(xreturn);
            }
        }

        /**
         * Appends and returns a new empty "return" element
         */
        public com.bagnet.nettracer.ws.onlineclaims.xsd.PassengerView addNewReturn() {
            synchronized (monitor()) {
                check_orphaned();

                com.bagnet.nettracer.ws.onlineclaims.xsd.PassengerView target = null;
                target = (com.bagnet.nettracer.ws.onlineclaims.xsd.PassengerView) get_store()
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

                com.bagnet.nettracer.ws.onlineclaims.xsd.PassengerView target = null;
                target = (com.bagnet.nettracer.ws.onlineclaims.xsd.PassengerView) get_store()
                                                                                      .find_element_user(RETURN$0,
                        0);

                if (target == null) {
                    target = (com.bagnet.nettracer.ws.onlineclaims.xsd.PassengerView) get_store()
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
