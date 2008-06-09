/*
 * An XML document type.
 * Localname: insertOHDResponse
 * Namespace: http://core.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.core.InsertOHDResponseDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.core.impl;

/**
 * A document containing one insertOHDResponse(@http://core.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public class InsertOHDResponseDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
    implements com.bagnet.nettracer.ws.core.InsertOHDResponseDocument {
    private static final javax.xml.namespace.QName INSERTOHDRESPONSE$0 = new javax.xml.namespace.QName("http://core.ws.nettracer.bagnet.com",
            "insertOHDResponse");

    public InsertOHDResponseDocumentImpl(org.apache.xmlbeans.SchemaType sType) {
        super(sType);
    }

    /**
     * Gets the "insertOHDResponse" element
     */
    public com.bagnet.nettracer.ws.core.InsertOHDResponseDocument.InsertOHDResponse getInsertOHDResponse() {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.InsertOHDResponseDocument.InsertOHDResponse target =
                null;
            target = (com.bagnet.nettracer.ws.core.InsertOHDResponseDocument.InsertOHDResponse) get_store()
                                                                                                    .find_element_user(INSERTOHDRESPONSE$0,
                    0);

            if (target == null) {
                return null;
            }

            return target;
        }
    }

    /**
     * Sets the "insertOHDResponse" element
     */
    public void setInsertOHDResponse(
        com.bagnet.nettracer.ws.core.InsertOHDResponseDocument.InsertOHDResponse insertOHDResponse) {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.InsertOHDResponseDocument.InsertOHDResponse target =
                null;
            target = (com.bagnet.nettracer.ws.core.InsertOHDResponseDocument.InsertOHDResponse) get_store()
                                                                                                    .find_element_user(INSERTOHDRESPONSE$0,
                    0);

            if (target == null) {
                target = (com.bagnet.nettracer.ws.core.InsertOHDResponseDocument.InsertOHDResponse) get_store()
                                                                                                        .add_element_user(INSERTOHDRESPONSE$0);
            }

            target.set(insertOHDResponse);
        }
    }

    /**
     * Appends and returns a new empty "insertOHDResponse" element
     */
    public com.bagnet.nettracer.ws.core.InsertOHDResponseDocument.InsertOHDResponse addNewInsertOHDResponse() {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.InsertOHDResponseDocument.InsertOHDResponse target =
                null;
            target = (com.bagnet.nettracer.ws.core.InsertOHDResponseDocument.InsertOHDResponse) get_store()
                                                                                                    .add_element_user(INSERTOHDRESPONSE$0);

            return target;
        }
    }

    /**
     * An XML insertOHDResponse(@http://core.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public static class InsertOHDResponseImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
        implements com.bagnet.nettracer.ws.core.InsertOHDResponseDocument.InsertOHDResponse {
        private static final javax.xml.namespace.QName RETURN$0 = new javax.xml.namespace.QName("http://core.ws.nettracer.bagnet.com",
                "return");

        public InsertOHDResponseImpl(org.apache.xmlbeans.SchemaType sType) {
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
