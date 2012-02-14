/*
 * An XML document type.
 * Localname: bulkBeornOHDResponse
 * Namespace: http://core.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.core.BulkBeornOHDResponseDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.core.impl;

/**
 * A document containing one bulkBeornOHDResponse(@http://core.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public class BulkBeornOHDResponseDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
    implements com.bagnet.nettracer.ws.core.BulkBeornOHDResponseDocument {
    private static final javax.xml.namespace.QName BULKBEORNOHDRESPONSE$0 = new javax.xml.namespace.QName("http://core.ws.nettracer.bagnet.com",
            "bulkBeornOHDResponse");

    public BulkBeornOHDResponseDocumentImpl(
        org.apache.xmlbeans.SchemaType sType) {
        super(sType);
    }

    /**
     * Gets the "bulkBeornOHDResponse" element
     */
    public com.bagnet.nettracer.ws.core.BulkBeornOHDResponseDocument.BulkBeornOHDResponse getBulkBeornOHDResponse() {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.BulkBeornOHDResponseDocument.BulkBeornOHDResponse target =
                null;
            target = (com.bagnet.nettracer.ws.core.BulkBeornOHDResponseDocument.BulkBeornOHDResponse) get_store()
                                                                                                          .find_element_user(BULKBEORNOHDRESPONSE$0,
                    0);

            if (target == null) {
                return null;
            }

            return target;
        }
    }

    /**
     * Sets the "bulkBeornOHDResponse" element
     */
    public void setBulkBeornOHDResponse(
        com.bagnet.nettracer.ws.core.BulkBeornOHDResponseDocument.BulkBeornOHDResponse bulkBeornOHDResponse) {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.BulkBeornOHDResponseDocument.BulkBeornOHDResponse target =
                null;
            target = (com.bagnet.nettracer.ws.core.BulkBeornOHDResponseDocument.BulkBeornOHDResponse) get_store()
                                                                                                          .find_element_user(BULKBEORNOHDRESPONSE$0,
                    0);

            if (target == null) {
                target = (com.bagnet.nettracer.ws.core.BulkBeornOHDResponseDocument.BulkBeornOHDResponse) get_store()
                                                                                                              .add_element_user(BULKBEORNOHDRESPONSE$0);
            }

            target.set(bulkBeornOHDResponse);
        }
    }

    /**
     * Appends and returns a new empty "bulkBeornOHDResponse" element
     */
    public com.bagnet.nettracer.ws.core.BulkBeornOHDResponseDocument.BulkBeornOHDResponse addNewBulkBeornOHDResponse() {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.BulkBeornOHDResponseDocument.BulkBeornOHDResponse target =
                null;
            target = (com.bagnet.nettracer.ws.core.BulkBeornOHDResponseDocument.BulkBeornOHDResponse) get_store()
                                                                                                          .add_element_user(BULKBEORNOHDRESPONSE$0);

            return target;
        }
    }

    /**
     * An XML bulkBeornOHDResponse(@http://core.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public static class BulkBeornOHDResponseImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
        implements com.bagnet.nettracer.ws.core.BulkBeornOHDResponseDocument.BulkBeornOHDResponse {
        private static final javax.xml.namespace.QName RETURN$0 = new javax.xml.namespace.QName("http://core.ws.nettracer.bagnet.com",
                "return");

        public BulkBeornOHDResponseImpl(org.apache.xmlbeans.SchemaType sType) {
            super(sType);
        }

        /**
         * Gets array of all "return" elements
         */
        public com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse[] getReturnArray() {
            synchronized (monitor()) {
                check_orphaned();

                java.util.List targetList = new java.util.ArrayList();
                get_store().find_all_element_users(RETURN$0, targetList);

                com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse[] result = new com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse[targetList.size()];
                targetList.toArray(result);

                return result;
            }
        }

        /**
         * Gets ith "return" element
         */
        public com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse getReturnArray(
            int i) {
            synchronized (monitor()) {
                check_orphaned();

                com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse) get_store()
                                                                                   .find_element_user(RETURN$0,
                        i);

                if (target == null) {
                    throw new IndexOutOfBoundsException();
                }

                return target;
            }
        }

        /**
         * Tests for nil ith "return" element
         */
        public boolean isNilReturnArray(int i) {
            synchronized (monitor()) {
                check_orphaned();

                com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse) get_store()
                                                                                   .find_element_user(RETURN$0,
                        i);

                if (target == null) {
                    throw new IndexOutOfBoundsException();
                }

                return target.isNil();
            }
        }

        /**
         * Returns number of "return" element
         */
        public int sizeOfReturnArray() {
            synchronized (monitor()) {
                check_orphaned();

                return get_store().count_elements(RETURN$0);
            }
        }

        /**
         * Sets array of all "return" element
         */
        public void setReturnArray(
            com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse[] xreturnArray) {
            synchronized (monitor()) {
                check_orphaned();
                arraySetterHelper(xreturnArray, RETURN$0);
            }
        }

        /**
         * Sets ith "return" element
         */
        public void setReturnArray(int i,
            com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse xreturn) {
            synchronized (monitor()) {
                check_orphaned();

                com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse) get_store()
                                                                                   .find_element_user(RETURN$0,
                        i);

                if (target == null) {
                    throw new IndexOutOfBoundsException();
                }

                target.set(xreturn);
            }
        }

        /**
         * Nils the ith "return" element
         */
        public void setNilReturnArray(int i) {
            synchronized (monitor()) {
                check_orphaned();

                com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse) get_store()
                                                                                   .find_element_user(RETURN$0,
                        i);

                if (target == null) {
                    throw new IndexOutOfBoundsException();
                }

                target.setNil();
            }
        }

        /**
         * Inserts and returns a new empty value (as xml) as the ith "return" element
         */
        public com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse insertNewReturn(
            int i) {
            synchronized (monitor()) {
                check_orphaned();

                com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse) get_store()
                                                                                   .insert_element_user(RETURN$0,
                        i);

                return target;
            }
        }

        /**
         * Appends and returns a new empty value (as xml) as the last "return" element
         */
        public com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse addNewReturn() {
            synchronized (monitor()) {
                check_orphaned();

                com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse) get_store()
                                                                                   .add_element_user(RETURN$0);

                return target;
            }
        }

        /**
         * Removes the ith "return" element
         */
        public void removeReturn(int i) {
            synchronized (monitor()) {
                check_orphaned();
                get_store().remove_element(RETURN$0, i);
            }
        }
    }
}
