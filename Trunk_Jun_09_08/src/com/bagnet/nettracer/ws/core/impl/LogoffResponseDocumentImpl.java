/*
 * An XML document type.
 * Localname: logoffResponse
 * Namespace: http://core.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.core.LogoffResponseDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.core.impl;

/**
 * A document containing one logoffResponse(@http://core.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public class LogoffResponseDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
    implements com.bagnet.nettracer.ws.core.LogoffResponseDocument {
    private static final javax.xml.namespace.QName LOGOFFRESPONSE$0 = new javax.xml.namespace.QName("http://core.ws.nettracer.bagnet.com",
            "logoffResponse");

    public LogoffResponseDocumentImpl(org.apache.xmlbeans.SchemaType sType) {
        super(sType);
    }

    /**
     * Gets the "logoffResponse" element
     */
    public com.bagnet.nettracer.ws.core.LogoffResponseDocument.LogoffResponse getLogoffResponse() {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.LogoffResponseDocument.LogoffResponse target =
                null;
            target = (com.bagnet.nettracer.ws.core.LogoffResponseDocument.LogoffResponse) get_store()
                                                                                              .find_element_user(LOGOFFRESPONSE$0,
                    0);

            if (target == null) {
                return null;
            }

            return target;
        }
    }

    /**
     * Sets the "logoffResponse" element
     */
    public void setLogoffResponse(
        com.bagnet.nettracer.ws.core.LogoffResponseDocument.LogoffResponse logoffResponse) {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.LogoffResponseDocument.LogoffResponse target =
                null;
            target = (com.bagnet.nettracer.ws.core.LogoffResponseDocument.LogoffResponse) get_store()
                                                                                              .find_element_user(LOGOFFRESPONSE$0,
                    0);

            if (target == null) {
                target = (com.bagnet.nettracer.ws.core.LogoffResponseDocument.LogoffResponse) get_store()
                                                                                                  .add_element_user(LOGOFFRESPONSE$0);
            }

            target.set(logoffResponse);
        }
    }

    /**
     * Appends and returns a new empty "logoffResponse" element
     */
    public com.bagnet.nettracer.ws.core.LogoffResponseDocument.LogoffResponse addNewLogoffResponse() {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.LogoffResponseDocument.LogoffResponse target =
                null;
            target = (com.bagnet.nettracer.ws.core.LogoffResponseDocument.LogoffResponse) get_store()
                                                                                              .add_element_user(LOGOFFRESPONSE$0);

            return target;
        }
    }

    /**
     * An XML logoffResponse(@http://core.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public static class LogoffResponseImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
        implements com.bagnet.nettracer.ws.core.LogoffResponseDocument.LogoffResponse {
        private static final javax.xml.namespace.QName RETURN$0 = new javax.xml.namespace.QName("http://core.ws.nettracer.bagnet.com",
                "return");

        public LogoffResponseImpl(org.apache.xmlbeans.SchemaType sType) {
            super(sType);
        }

        /**
         * Gets the "return" element
         */
        public boolean getReturn() {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .find_element_user(RETURN$0,
                        0);

                if (target == null) {
                    return false;
                }

                return target.getBooleanValue();
            }
        }

        /**
         * Gets (as xml) the "return" element
         */
        public org.apache.xmlbeans.XmlBoolean xgetReturn() {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.XmlBoolean target = null;
                target = (org.apache.xmlbeans.XmlBoolean) get_store()
                                                              .find_element_user(RETURN$0,
                        0);

                return target;
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
        public void setReturn(boolean xreturn) {
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

                target.setBooleanValue(xreturn);
            }
        }

        /**
         * Sets (as xml) the "return" element
         */
        public void xsetReturn(org.apache.xmlbeans.XmlBoolean xreturn) {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.XmlBoolean target = null;
                target = (org.apache.xmlbeans.XmlBoolean) get_store()
                                                              .find_element_user(RETURN$0,
                        0);

                if (target == null) {
                    target = (org.apache.xmlbeans.XmlBoolean) get_store()
                                                                  .add_element_user(RETURN$0);
                }

                target.set(xreturn);
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
