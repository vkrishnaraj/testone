/*
 * An XML document type.
 * Localname: logoff
 * Namespace: http://core.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.core.LogoffDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.core.impl;

/**
 * A document containing one logoff(@http://core.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public class LogoffDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
    implements com.bagnet.nettracer.ws.core.LogoffDocument {
    private static final javax.xml.namespace.QName LOGOFF$0 = new javax.xml.namespace.QName("http://core.ws.nettracer.bagnet.com",
            "logoff");

    public LogoffDocumentImpl(org.apache.xmlbeans.SchemaType sType) {
        super(sType);
    }

    /**
     * Gets the "logoff" element
     */
    public com.bagnet.nettracer.ws.core.LogoffDocument.Logoff getLogoff() {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.LogoffDocument.Logoff target = null;
            target = (com.bagnet.nettracer.ws.core.LogoffDocument.Logoff) get_store()
                                                                              .find_element_user(LOGOFF$0,
                    0);

            if (target == null) {
                return null;
            }

            return target;
        }
    }

    /**
     * Sets the "logoff" element
     */
    public void setLogoff(
        com.bagnet.nettracer.ws.core.LogoffDocument.Logoff logoff) {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.LogoffDocument.Logoff target = null;
            target = (com.bagnet.nettracer.ws.core.LogoffDocument.Logoff) get_store()
                                                                              .find_element_user(LOGOFF$0,
                    0);

            if (target == null) {
                target = (com.bagnet.nettracer.ws.core.LogoffDocument.Logoff) get_store()
                                                                                  .add_element_user(LOGOFF$0);
            }

            target.set(logoff);
        }
    }

    /**
     * Appends and returns a new empty "logoff" element
     */
    public com.bagnet.nettracer.ws.core.LogoffDocument.Logoff addNewLogoff() {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.LogoffDocument.Logoff target = null;
            target = (com.bagnet.nettracer.ws.core.LogoffDocument.Logoff) get_store()
                                                                              .add_element_user(LOGOFF$0);

            return target;
        }
    }

    /**
     * An XML logoff(@http://core.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public static class LogoffImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
        implements com.bagnet.nettracer.ws.core.LogoffDocument.Logoff {
        private static final javax.xml.namespace.QName SESSIONID$0 = new javax.xml.namespace.QName("http://core.ws.nettracer.bagnet.com",
                "session_id");

        public LogoffImpl(org.apache.xmlbeans.SchemaType sType) {
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
    }
}
