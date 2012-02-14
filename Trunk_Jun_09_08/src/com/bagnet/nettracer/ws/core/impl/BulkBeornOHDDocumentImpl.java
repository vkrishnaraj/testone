/*
 * An XML document type.
 * Localname: bulkBeornOHD
 * Namespace: http://core.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.core.BulkBeornOHDDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.core.impl;

/**
 * A document containing one bulkBeornOHD(@http://core.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public class BulkBeornOHDDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
    implements com.bagnet.nettracer.ws.core.BulkBeornOHDDocument {
    private static final javax.xml.namespace.QName BULKBEORNOHD$0 = new javax.xml.namespace.QName("http://core.ws.nettracer.bagnet.com",
            "bulkBeornOHD");

    public BulkBeornOHDDocumentImpl(org.apache.xmlbeans.SchemaType sType) {
        super(sType);
    }

    /**
     * Gets the "bulkBeornOHD" element
     */
    public com.bagnet.nettracer.ws.core.BulkBeornOHDDocument.BulkBeornOHD getBulkBeornOHD() {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.BulkBeornOHDDocument.BulkBeornOHD target =
                null;
            target = (com.bagnet.nettracer.ws.core.BulkBeornOHDDocument.BulkBeornOHD) get_store()
                                                                                          .find_element_user(BULKBEORNOHD$0,
                    0);

            if (target == null) {
                return null;
            }

            return target;
        }
    }

    /**
     * Sets the "bulkBeornOHD" element
     */
    public void setBulkBeornOHD(
        com.bagnet.nettracer.ws.core.BulkBeornOHDDocument.BulkBeornOHD bulkBeornOHD) {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.BulkBeornOHDDocument.BulkBeornOHD target =
                null;
            target = (com.bagnet.nettracer.ws.core.BulkBeornOHDDocument.BulkBeornOHD) get_store()
                                                                                          .find_element_user(BULKBEORNOHD$0,
                    0);

            if (target == null) {
                target = (com.bagnet.nettracer.ws.core.BulkBeornOHDDocument.BulkBeornOHD) get_store()
                                                                                              .add_element_user(BULKBEORNOHD$0);
            }

            target.set(bulkBeornOHD);
        }
    }

    /**
     * Appends and returns a new empty "bulkBeornOHD" element
     */
    public com.bagnet.nettracer.ws.core.BulkBeornOHDDocument.BulkBeornOHD addNewBulkBeornOHD() {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.BulkBeornOHDDocument.BulkBeornOHD target =
                null;
            target = (com.bagnet.nettracer.ws.core.BulkBeornOHDDocument.BulkBeornOHD) get_store()
                                                                                          .add_element_user(BULKBEORNOHD$0);

            return target;
        }
    }

    /**
     * An XML bulkBeornOHD(@http://core.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public static class BulkBeornOHDImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
        implements com.bagnet.nettracer.ws.core.BulkBeornOHDDocument.BulkBeornOHD {
        private static final javax.xml.namespace.QName SESSIONID$0 = new javax.xml.namespace.QName("http://core.ws.nettracer.bagnet.com",
                "session_id");
        private static final javax.xml.namespace.QName SI$2 = new javax.xml.namespace.QName("http://core.ws.nettracer.bagnet.com",
                "si");

        public BulkBeornOHDImpl(org.apache.xmlbeans.SchemaType sType) {
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
         * Gets array of all "si" elements
         */
        public com.bagnet.nettracer.ws.core.pojo.xsd.WSBEORN[] getSiArray() {
            synchronized (monitor()) {
                check_orphaned();

                java.util.List targetList = new java.util.ArrayList();
                get_store().find_all_element_users(SI$2, targetList);

                com.bagnet.nettracer.ws.core.pojo.xsd.WSBEORN[] result = new com.bagnet.nettracer.ws.core.pojo.xsd.WSBEORN[targetList.size()];
                targetList.toArray(result);

                return result;
            }
        }

        /**
         * Gets ith "si" element
         */
        public com.bagnet.nettracer.ws.core.pojo.xsd.WSBEORN getSiArray(int i) {
            synchronized (monitor()) {
                check_orphaned();

                com.bagnet.nettracer.ws.core.pojo.xsd.WSBEORN target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSBEORN) get_store()
                                                                             .find_element_user(SI$2,
                        i);

                if (target == null) {
                    throw new IndexOutOfBoundsException();
                }

                return target;
            }
        }

        /**
         * Tests for nil ith "si" element
         */
        public boolean isNilSiArray(int i) {
            synchronized (monitor()) {
                check_orphaned();

                com.bagnet.nettracer.ws.core.pojo.xsd.WSBEORN target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSBEORN) get_store()
                                                                             .find_element_user(SI$2,
                        i);

                if (target == null) {
                    throw new IndexOutOfBoundsException();
                }

                return target.isNil();
            }
        }

        /**
         * Returns number of "si" element
         */
        public int sizeOfSiArray() {
            synchronized (monitor()) {
                check_orphaned();

                return get_store().count_elements(SI$2);
            }
        }

        /**
         * Sets array of all "si" element
         */
        public void setSiArray(
            com.bagnet.nettracer.ws.core.pojo.xsd.WSBEORN[] siArray) {
            synchronized (monitor()) {
                check_orphaned();
                arraySetterHelper(siArray, SI$2);
            }
        }

        /**
         * Sets ith "si" element
         */
        public void setSiArray(int i,
            com.bagnet.nettracer.ws.core.pojo.xsd.WSBEORN si) {
            synchronized (monitor()) {
                check_orphaned();

                com.bagnet.nettracer.ws.core.pojo.xsd.WSBEORN target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSBEORN) get_store()
                                                                             .find_element_user(SI$2,
                        i);

                if (target == null) {
                    throw new IndexOutOfBoundsException();
                }

                target.set(si);
            }
        }

        /**
         * Nils the ith "si" element
         */
        public void setNilSiArray(int i) {
            synchronized (monitor()) {
                check_orphaned();

                com.bagnet.nettracer.ws.core.pojo.xsd.WSBEORN target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSBEORN) get_store()
                                                                             .find_element_user(SI$2,
                        i);

                if (target == null) {
                    throw new IndexOutOfBoundsException();
                }

                target.setNil();
            }
        }

        /**
         * Inserts and returns a new empty value (as xml) as the ith "si" element
         */
        public com.bagnet.nettracer.ws.core.pojo.xsd.WSBEORN insertNewSi(int i) {
            synchronized (monitor()) {
                check_orphaned();

                com.bagnet.nettracer.ws.core.pojo.xsd.WSBEORN target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSBEORN) get_store()
                                                                             .insert_element_user(SI$2,
                        i);

                return target;
            }
        }

        /**
         * Appends and returns a new empty value (as xml) as the last "si" element
         */
        public com.bagnet.nettracer.ws.core.pojo.xsd.WSBEORN addNewSi() {
            synchronized (monitor()) {
                check_orphaned();

                com.bagnet.nettracer.ws.core.pojo.xsd.WSBEORN target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSBEORN) get_store()
                                                                             .add_element_user(SI$2);

                return target;
            }
        }

        /**
         * Removes the ith "si" element
         */
        public void removeSi(int i) {
            synchronized (monitor()) {
                check_orphaned();
                get_store().remove_element(SI$2, i);
            }
        }
    }
}
