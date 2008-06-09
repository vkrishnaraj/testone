/*
 * An XML document type.
 * Localname: authenticateResponse
 * Namespace: http://core.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.core.AuthenticateResponseDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.core.impl;

/**
 * A document containing one authenticateResponse(@http://core.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public class AuthenticateResponseDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
    implements com.bagnet.nettracer.ws.core.AuthenticateResponseDocument {
    private static final javax.xml.namespace.QName AUTHENTICATERESPONSE$0 = new javax.xml.namespace.QName("http://core.ws.nettracer.bagnet.com",
            "authenticateResponse");

    public AuthenticateResponseDocumentImpl(
        org.apache.xmlbeans.SchemaType sType) {
        super(sType);
    }

    /**
     * Gets the "authenticateResponse" element
     */
    public com.bagnet.nettracer.ws.core.AuthenticateResponseDocument.AuthenticateResponse getAuthenticateResponse() {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.AuthenticateResponseDocument.AuthenticateResponse target =
                null;
            target = (com.bagnet.nettracer.ws.core.AuthenticateResponseDocument.AuthenticateResponse) get_store()
                                                                                                          .find_element_user(AUTHENTICATERESPONSE$0,
                    0);

            if (target == null) {
                return null;
            }

            return target;
        }
    }

    /**
     * Sets the "authenticateResponse" element
     */
    public void setAuthenticateResponse(
        com.bagnet.nettracer.ws.core.AuthenticateResponseDocument.AuthenticateResponse authenticateResponse) {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.AuthenticateResponseDocument.AuthenticateResponse target =
                null;
            target = (com.bagnet.nettracer.ws.core.AuthenticateResponseDocument.AuthenticateResponse) get_store()
                                                                                                          .find_element_user(AUTHENTICATERESPONSE$0,
                    0);

            if (target == null) {
                target = (com.bagnet.nettracer.ws.core.AuthenticateResponseDocument.AuthenticateResponse) get_store()
                                                                                                              .add_element_user(AUTHENTICATERESPONSE$0);
            }

            target.set(authenticateResponse);
        }
    }

    /**
     * Appends and returns a new empty "authenticateResponse" element
     */
    public com.bagnet.nettracer.ws.core.AuthenticateResponseDocument.AuthenticateResponse addNewAuthenticateResponse() {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.AuthenticateResponseDocument.AuthenticateResponse target =
                null;
            target = (com.bagnet.nettracer.ws.core.AuthenticateResponseDocument.AuthenticateResponse) get_store()
                                                                                                          .add_element_user(AUTHENTICATERESPONSE$0);

            return target;
        }
    }

    /**
     * An XML authenticateResponse(@http://core.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public static class AuthenticateResponseImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
        implements com.bagnet.nettracer.ws.core.AuthenticateResponseDocument.AuthenticateResponse {
        private static final javax.xml.namespace.QName RETURN$0 = new javax.xml.namespace.QName("http://core.ws.nettracer.bagnet.com",
                "return");

        public AuthenticateResponseImpl(org.apache.xmlbeans.SchemaType sType) {
            super(sType);
        }

        /**
         * Gets the "return" element
         */
        public java.lang.String getReturn() {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .find_element_user(RETURN$0,
                        0);

                if (target == null) {
                    return null;
                }

                return target.getStringValue();
            }
        }

        /**
         * Gets (as xml) the "return" element
         */
        public org.apache.xmlbeans.XmlString xgetReturn() {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .find_element_user(RETURN$0,
                        0);

                return target;
            }
        }

        /**
         * Tests for nil "return" element
         */
        public boolean isNilReturn() {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString) get_store()
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
        public void setReturn(java.lang.String xreturn) {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .find_element_user(RETURN$0,
                        0);

                if (target == null) {
                    target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                                   .add_element_user(RETURN$0);
                }

                target.setStringValue(xreturn);
            }
        }

        /**
         * Sets (as xml) the "return" element
         */
        public void xsetReturn(org.apache.xmlbeans.XmlString xreturn) {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .find_element_user(RETURN$0,
                        0);

                if (target == null) {
                    target = (org.apache.xmlbeans.XmlString) get_store()
                                                                 .add_element_user(RETURN$0);
                }

                target.set(xreturn);
            }
        }

        /**
         * Nils the "return" element
         */
        public void setNilReturn() {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .find_element_user(RETURN$0,
                        0);

                if (target == null) {
                    target = (org.apache.xmlbeans.XmlString) get_store()
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
