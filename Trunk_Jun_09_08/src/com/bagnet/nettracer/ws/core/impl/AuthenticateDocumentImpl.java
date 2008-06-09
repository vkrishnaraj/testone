/*
 * An XML document type.
 * Localname: authenticate
 * Namespace: http://core.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.core.AuthenticateDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.core.impl;

/**
 * A document containing one authenticate(@http://core.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public class AuthenticateDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
    implements com.bagnet.nettracer.ws.core.AuthenticateDocument {
    private static final javax.xml.namespace.QName AUTHENTICATE$0 = new javax.xml.namespace.QName("http://core.ws.nettracer.bagnet.com",
            "authenticate");

    public AuthenticateDocumentImpl(org.apache.xmlbeans.SchemaType sType) {
        super(sType);
    }

    /**
     * Gets the "authenticate" element
     */
    public com.bagnet.nettracer.ws.core.AuthenticateDocument.Authenticate getAuthenticate() {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.AuthenticateDocument.Authenticate target =
                null;
            target = (com.bagnet.nettracer.ws.core.AuthenticateDocument.Authenticate) get_store()
                                                                                          .find_element_user(AUTHENTICATE$0,
                    0);

            if (target == null) {
                return null;
            }

            return target;
        }
    }

    /**
     * Sets the "authenticate" element
     */
    public void setAuthenticate(
        com.bagnet.nettracer.ws.core.AuthenticateDocument.Authenticate authenticate) {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.AuthenticateDocument.Authenticate target =
                null;
            target = (com.bagnet.nettracer.ws.core.AuthenticateDocument.Authenticate) get_store()
                                                                                          .find_element_user(AUTHENTICATE$0,
                    0);

            if (target == null) {
                target = (com.bagnet.nettracer.ws.core.AuthenticateDocument.Authenticate) get_store()
                                                                                              .add_element_user(AUTHENTICATE$0);
            }

            target.set(authenticate);
        }
    }

    /**
     * Appends and returns a new empty "authenticate" element
     */
    public com.bagnet.nettracer.ws.core.AuthenticateDocument.Authenticate addNewAuthenticate() {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.AuthenticateDocument.Authenticate target =
                null;
            target = (com.bagnet.nettracer.ws.core.AuthenticateDocument.Authenticate) get_store()
                                                                                          .add_element_user(AUTHENTICATE$0);

            return target;
        }
    }

    /**
     * An XML authenticate(@http://core.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public static class AuthenticateImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
        implements com.bagnet.nettracer.ws.core.AuthenticateDocument.Authenticate {
        private static final javax.xml.namespace.QName USERNAME$0 = new javax.xml.namespace.QName("http://core.ws.nettracer.bagnet.com",
                "username");
        private static final javax.xml.namespace.QName PASSWORD$2 = new javax.xml.namespace.QName("http://core.ws.nettracer.bagnet.com",
                "password");
        private static final javax.xml.namespace.QName COMPANYCODE$4 = new javax.xml.namespace.QName("http://core.ws.nettracer.bagnet.com",
                "companycode");

        public AuthenticateImpl(org.apache.xmlbeans.SchemaType sType) {
            super(sType);
        }

        /**
         * Gets the "username" element
         */
        public java.lang.String getUsername() {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .find_element_user(USERNAME$0,
                        0);

                if (target == null) {
                    return null;
                }

                return target.getStringValue();
            }
        }

        /**
         * Gets (as xml) the "username" element
         */
        public org.apache.xmlbeans.XmlString xgetUsername() {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .find_element_user(USERNAME$0,
                        0);

                return target;
            }
        }

        /**
         * Tests for nil "username" element
         */
        public boolean isNilUsername() {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .find_element_user(USERNAME$0,
                        0);

                if (target == null) {
                    return false;
                }

                return target.isNil();
            }
        }

        /**
         * True if has "username" element
         */
        public boolean isSetUsername() {
            synchronized (monitor()) {
                check_orphaned();

                return get_store().count_elements(USERNAME$0) != 0;
            }
        }

        /**
         * Sets the "username" element
         */
        public void setUsername(java.lang.String username) {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .find_element_user(USERNAME$0,
                        0);

                if (target == null) {
                    target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                                   .add_element_user(USERNAME$0);
                }

                target.setStringValue(username);
            }
        }

        /**
         * Sets (as xml) the "username" element
         */
        public void xsetUsername(org.apache.xmlbeans.XmlString username) {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .find_element_user(USERNAME$0,
                        0);

                if (target == null) {
                    target = (org.apache.xmlbeans.XmlString) get_store()
                                                                 .add_element_user(USERNAME$0);
                }

                target.set(username);
            }
        }

        /**
         * Nils the "username" element
         */
        public void setNilUsername() {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .find_element_user(USERNAME$0,
                        0);

                if (target == null) {
                    target = (org.apache.xmlbeans.XmlString) get_store()
                                                                 .add_element_user(USERNAME$0);
                }

                target.setNil();
            }
        }

        /**
         * Unsets the "username" element
         */
        public void unsetUsername() {
            synchronized (monitor()) {
                check_orphaned();
                get_store().remove_element(USERNAME$0, 0);
            }
        }

        /**
         * Gets the "password" element
         */
        public java.lang.String getPassword() {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .find_element_user(PASSWORD$2,
                        0);

                if (target == null) {
                    return null;
                }

                return target.getStringValue();
            }
        }

        /**
         * Gets (as xml) the "password" element
         */
        public org.apache.xmlbeans.XmlString xgetPassword() {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .find_element_user(PASSWORD$2,
                        0);

                return target;
            }
        }

        /**
         * Tests for nil "password" element
         */
        public boolean isNilPassword() {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .find_element_user(PASSWORD$2,
                        0);

                if (target == null) {
                    return false;
                }

                return target.isNil();
            }
        }

        /**
         * True if has "password" element
         */
        public boolean isSetPassword() {
            synchronized (monitor()) {
                check_orphaned();

                return get_store().count_elements(PASSWORD$2) != 0;
            }
        }

        /**
         * Sets the "password" element
         */
        public void setPassword(java.lang.String password) {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .find_element_user(PASSWORD$2,
                        0);

                if (target == null) {
                    target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                                   .add_element_user(PASSWORD$2);
                }

                target.setStringValue(password);
            }
        }

        /**
         * Sets (as xml) the "password" element
         */
        public void xsetPassword(org.apache.xmlbeans.XmlString password) {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .find_element_user(PASSWORD$2,
                        0);

                if (target == null) {
                    target = (org.apache.xmlbeans.XmlString) get_store()
                                                                 .add_element_user(PASSWORD$2);
                }

                target.set(password);
            }
        }

        /**
         * Nils the "password" element
         */
        public void setNilPassword() {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .find_element_user(PASSWORD$2,
                        0);

                if (target == null) {
                    target = (org.apache.xmlbeans.XmlString) get_store()
                                                                 .add_element_user(PASSWORD$2);
                }

                target.setNil();
            }
        }

        /**
         * Unsets the "password" element
         */
        public void unsetPassword() {
            synchronized (monitor()) {
                check_orphaned();
                get_store().remove_element(PASSWORD$2, 0);
            }
        }

        /**
         * Gets the "companycode" element
         */
        public java.lang.String getCompanycode() {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .find_element_user(COMPANYCODE$4,
                        0);

                if (target == null) {
                    return null;
                }

                return target.getStringValue();
            }
        }

        /**
         * Gets (as xml) the "companycode" element
         */
        public org.apache.xmlbeans.XmlString xgetCompanycode() {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .find_element_user(COMPANYCODE$4,
                        0);

                return target;
            }
        }

        /**
         * Tests for nil "companycode" element
         */
        public boolean isNilCompanycode() {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .find_element_user(COMPANYCODE$4,
                        0);

                if (target == null) {
                    return false;
                }

                return target.isNil();
            }
        }

        /**
         * True if has "companycode" element
         */
        public boolean isSetCompanycode() {
            synchronized (monitor()) {
                check_orphaned();

                return get_store().count_elements(COMPANYCODE$4) != 0;
            }
        }

        /**
         * Sets the "companycode" element
         */
        public void setCompanycode(java.lang.String companycode) {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .find_element_user(COMPANYCODE$4,
                        0);

                if (target == null) {
                    target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                                   .add_element_user(COMPANYCODE$4);
                }

                target.setStringValue(companycode);
            }
        }

        /**
         * Sets (as xml) the "companycode" element
         */
        public void xsetCompanycode(org.apache.xmlbeans.XmlString companycode) {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .find_element_user(COMPANYCODE$4,
                        0);

                if (target == null) {
                    target = (org.apache.xmlbeans.XmlString) get_store()
                                                                 .add_element_user(COMPANYCODE$4);
                }

                target.set(companycode);
            }
        }

        /**
         * Nils the "companycode" element
         */
        public void setNilCompanycode() {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .find_element_user(COMPANYCODE$4,
                        0);

                if (target == null) {
                    target = (org.apache.xmlbeans.XmlString) get_store()
                                                                 .add_element_user(COMPANYCODE$4);
                }

                target.setNil();
            }
        }

        /**
         * Unsets the "companycode" element
         */
        public void unsetCompanycode() {
            synchronized (monitor()) {
                check_orphaned();
                get_store().remove_element(COMPANYCODE$4, 0);
            }
        }
    }
}
