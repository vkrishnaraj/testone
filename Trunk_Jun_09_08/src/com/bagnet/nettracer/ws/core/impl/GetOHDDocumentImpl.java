/*
 * An XML document type.
 * Localname: getOHD
 * Namespace: http://core.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.core.GetOHDDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.core.impl;

/**
 * A document containing one getOHD(@http://core.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public class GetOHDDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
    implements com.bagnet.nettracer.ws.core.GetOHDDocument {
    private static final javax.xml.namespace.QName GETOHD$0 = new javax.xml.namespace.QName("http://core.ws.nettracer.bagnet.com",
            "getOHD");

    public GetOHDDocumentImpl(org.apache.xmlbeans.SchemaType sType) {
        super(sType);
    }

    /**
     * Gets the "getOHD" element
     */
    public com.bagnet.nettracer.ws.core.GetOHDDocument.GetOHD getGetOHD() {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.GetOHDDocument.GetOHD target = null;
            target = (com.bagnet.nettracer.ws.core.GetOHDDocument.GetOHD) get_store()
                                                                              .find_element_user(GETOHD$0,
                    0);

            if (target == null) {
                return null;
            }

            return target;
        }
    }

    /**
     * Sets the "getOHD" element
     */
    public void setGetOHD(
        com.bagnet.nettracer.ws.core.GetOHDDocument.GetOHD getOHD) {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.GetOHDDocument.GetOHD target = null;
            target = (com.bagnet.nettracer.ws.core.GetOHDDocument.GetOHD) get_store()
                                                                              .find_element_user(GETOHD$0,
                    0);

            if (target == null) {
                target = (com.bagnet.nettracer.ws.core.GetOHDDocument.GetOHD) get_store()
                                                                                  .add_element_user(GETOHD$0);
            }

            target.set(getOHD);
        }
    }

    /**
     * Appends and returns a new empty "getOHD" element
     */
    public com.bagnet.nettracer.ws.core.GetOHDDocument.GetOHD addNewGetOHD() {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.GetOHDDocument.GetOHD target = null;
            target = (com.bagnet.nettracer.ws.core.GetOHDDocument.GetOHD) get_store()
                                                                              .add_element_user(GETOHD$0);

            return target;
        }
    }

    /**
     * An XML getOHD(@http://core.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public static class GetOHDImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
        implements com.bagnet.nettracer.ws.core.GetOHDDocument.GetOHD {
        private static final javax.xml.namespace.QName SESSIONID$0 = new javax.xml.namespace.QName("http://core.ws.nettracer.bagnet.com",
                "session_id");
        private static final javax.xml.namespace.QName OHDID$2 = new javax.xml.namespace.QName("http://core.ws.nettracer.bagnet.com",
                "ohd_id");

        public GetOHDImpl(org.apache.xmlbeans.SchemaType sType) {
            super(sType);
        }

        /**
         * Gets the "session_id" element
         */
        public java.lang.String getSessionId() {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .find_element_user(SESSIONID$0,
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
                                                             .find_element_user(SESSIONID$0,
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
                                                             .find_element_user(SESSIONID$0,
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

                return get_store().count_elements(SESSIONID$0) != 0;
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
                                                               .find_element_user(SESSIONID$0,
                        0);

                if (target == null) {
                    target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                                   .add_element_user(SESSIONID$0);
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
                                                             .find_element_user(SESSIONID$0,
                        0);

                if (target == null) {
                    target = (org.apache.xmlbeans.XmlString) get_store()
                                                                 .add_element_user(SESSIONID$0);
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
                                                             .find_element_user(SESSIONID$0,
                        0);

                if (target == null) {
                    target = (org.apache.xmlbeans.XmlString) get_store()
                                                                 .add_element_user(SESSIONID$0);
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
                get_store().remove_element(SESSIONID$0, 0);
            }
        }

        /**
         * Gets the "ohd_id" element
         */
        public java.lang.String getOhdId() {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .find_element_user(OHDID$2,
                        0);

                if (target == null) {
                    return null;
                }

                return target.getStringValue();
            }
        }

        /**
         * Gets (as xml) the "ohd_id" element
         */
        public org.apache.xmlbeans.XmlString xgetOhdId() {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .find_element_user(OHDID$2,
                        0);

                return target;
            }
        }

        /**
         * Tests for nil "ohd_id" element
         */
        public boolean isNilOhdId() {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .find_element_user(OHDID$2,
                        0);

                if (target == null) {
                    return false;
                }

                return target.isNil();
            }
        }

        /**
         * True if has "ohd_id" element
         */
        public boolean isSetOhdId() {
            synchronized (monitor()) {
                check_orphaned();

                return get_store().count_elements(OHDID$2) != 0;
            }
        }

        /**
         * Sets the "ohd_id" element
         */
        public void setOhdId(java.lang.String ohdId) {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .find_element_user(OHDID$2,
                        0);

                if (target == null) {
                    target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                                   .add_element_user(OHDID$2);
                }

                target.setStringValue(ohdId);
            }
        }

        /**
         * Sets (as xml) the "ohd_id" element
         */
        public void xsetOhdId(org.apache.xmlbeans.XmlString ohdId) {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .find_element_user(OHDID$2,
                        0);

                if (target == null) {
                    target = (org.apache.xmlbeans.XmlString) get_store()
                                                                 .add_element_user(OHDID$2);
                }

                target.set(ohdId);
            }
        }

        /**
         * Nils the "ohd_id" element
         */
        public void setNilOhdId() {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .find_element_user(OHDID$2,
                        0);

                if (target == null) {
                    target = (org.apache.xmlbeans.XmlString) get_store()
                                                                 .add_element_user(OHDID$2);
                }

                target.setNil();
            }
        }

        /**
         * Unsets the "ohd_id" element
         */
        public void unsetOhdId() {
            synchronized (monitor()) {
                check_orphaned();
                get_store().remove_element(OHDID$2, 0);
            }
        }
    }
}
