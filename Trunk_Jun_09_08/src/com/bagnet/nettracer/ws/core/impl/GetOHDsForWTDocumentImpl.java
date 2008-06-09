/*
 * An XML document type.
 * Localname: getOHDsForWT
 * Namespace: http://core.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.core.GetOHDsForWTDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.core.impl;

/**
 * A document containing one getOHDsForWT(@http://core.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public class GetOHDsForWTDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
    implements com.bagnet.nettracer.ws.core.GetOHDsForWTDocument {
    private static final javax.xml.namespace.QName GETOHDSFORWT$0 = new javax.xml.namespace.QName("http://core.ws.nettracer.bagnet.com",
            "getOHDsForWT");

    public GetOHDsForWTDocumentImpl(org.apache.xmlbeans.SchemaType sType) {
        super(sType);
    }

    /**
     * Gets the "getOHDsForWT" element
     */
    public com.bagnet.nettracer.ws.core.GetOHDsForWTDocument.GetOHDsForWT getGetOHDsForWT() {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.GetOHDsForWTDocument.GetOHDsForWT target =
                null;
            target = (com.bagnet.nettracer.ws.core.GetOHDsForWTDocument.GetOHDsForWT) get_store()
                                                                                          .find_element_user(GETOHDSFORWT$0,
                    0);

            if (target == null) {
                return null;
            }

            return target;
        }
    }

    /**
     * Sets the "getOHDsForWT" element
     */
    public void setGetOHDsForWT(
        com.bagnet.nettracer.ws.core.GetOHDsForWTDocument.GetOHDsForWT getOHDsForWT) {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.GetOHDsForWTDocument.GetOHDsForWT target =
                null;
            target = (com.bagnet.nettracer.ws.core.GetOHDsForWTDocument.GetOHDsForWT) get_store()
                                                                                          .find_element_user(GETOHDSFORWT$0,
                    0);

            if (target == null) {
                target = (com.bagnet.nettracer.ws.core.GetOHDsForWTDocument.GetOHDsForWT) get_store()
                                                                                              .add_element_user(GETOHDSFORWT$0);
            }

            target.set(getOHDsForWT);
        }
    }

    /**
     * Appends and returns a new empty "getOHDsForWT" element
     */
    public com.bagnet.nettracer.ws.core.GetOHDsForWTDocument.GetOHDsForWT addNewGetOHDsForWT() {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.GetOHDsForWTDocument.GetOHDsForWT target =
                null;
            target = (com.bagnet.nettracer.ws.core.GetOHDsForWTDocument.GetOHDsForWT) get_store()
                                                                                          .add_element_user(GETOHDSFORWT$0);

            return target;
        }
    }

    /**
     * An XML getOHDsForWT(@http://core.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public static class GetOHDsForWTImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
        implements com.bagnet.nettracer.ws.core.GetOHDsForWTDocument.GetOHDsForWT {
        private static final javax.xml.namespace.QName COMPANYCODE$0 = new javax.xml.namespace.QName("http://core.ws.nettracer.bagnet.com",
                "companycode");
        private static final javax.xml.namespace.QName SESSIONID$2 = new javax.xml.namespace.QName("http://core.ws.nettracer.bagnet.com",
                "session_id");

        public GetOHDsForWTImpl(org.apache.xmlbeans.SchemaType sType) {
            super(sType);
        }

        /**
         * Gets the "companycode" element
         */
        public java.lang.String getCompanycode() {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .find_element_user(COMPANYCODE$0,
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
                                                             .find_element_user(COMPANYCODE$0,
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
                                                             .find_element_user(COMPANYCODE$0,
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

                return get_store().count_elements(COMPANYCODE$0) != 0;
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
                                                               .find_element_user(COMPANYCODE$0,
                        0);

                if (target == null) {
                    target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                                   .add_element_user(COMPANYCODE$0);
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
                                                             .find_element_user(COMPANYCODE$0,
                        0);

                if (target == null) {
                    target = (org.apache.xmlbeans.XmlString) get_store()
                                                                 .add_element_user(COMPANYCODE$0);
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
                                                             .find_element_user(COMPANYCODE$0,
                        0);

                if (target == null) {
                    target = (org.apache.xmlbeans.XmlString) get_store()
                                                                 .add_element_user(COMPANYCODE$0);
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
                get_store().remove_element(COMPANYCODE$0, 0);
            }
        }

        /**
         * Gets the "session_id" element
         */
        public java.lang.String getSessionId() {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .find_element_user(SESSIONID$2,
                        0);

                if (target == null) {
                    return null;
                }

                return target.getStringValue();
            }
        }

        /**
         * Gets (as xml) the "session_id" element
         */
        public org.apache.xmlbeans.XmlString xgetSessionId() {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .find_element_user(SESSIONID$2,
                        0);

                return target;
            }
        }

        /**
         * Tests for nil "session_id" element
         */
        public boolean isNilSessionId() {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .find_element_user(SESSIONID$2,
                        0);

                if (target == null) {
                    return false;
                }

                return target.isNil();
            }
        }

        /**
         * True if has "session_id" element
         */
        public boolean isSetSessionId() {
            synchronized (monitor()) {
                check_orphaned();

                return get_store().count_elements(SESSIONID$2) != 0;
            }
        }

        /**
         * Sets the "session_id" element
         */
        public void setSessionId(java.lang.String sessionId) {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .find_element_user(SESSIONID$2,
                        0);

                if (target == null) {
                    target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                                   .add_element_user(SESSIONID$2);
                }

                target.setStringValue(sessionId);
            }
        }

        /**
         * Sets (as xml) the "session_id" element
         */
        public void xsetSessionId(org.apache.xmlbeans.XmlString sessionId) {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .find_element_user(SESSIONID$2,
                        0);

                if (target == null) {
                    target = (org.apache.xmlbeans.XmlString) get_store()
                                                                 .add_element_user(SESSIONID$2);
                }

                target.set(sessionId);
            }
        }

        /**
         * Nils the "session_id" element
         */
        public void setNilSessionId() {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .find_element_user(SESSIONID$2,
                        0);

                if (target == null) {
                    target = (org.apache.xmlbeans.XmlString) get_store()
                                                                 .add_element_user(SESSIONID$2);
                }

                target.setNil();
            }
        }

        /**
         * Unsets the "session_id" element
         */
        public void unsetSessionId() {
            synchronized (monitor()) {
                check_orphaned();
                get_store().remove_element(SESSIONID$2, 0);
            }
        }
    }
}
