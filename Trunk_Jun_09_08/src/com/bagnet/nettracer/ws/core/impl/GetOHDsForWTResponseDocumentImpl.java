/*
 * An XML document type.
 * Localname: getOHDsForWTResponse
 * Namespace: http://core.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.core.GetOHDsForWTResponseDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.core.impl;

/**
 * A document containing one getOHDsForWTResponse(@http://core.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public class GetOHDsForWTResponseDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
    implements com.bagnet.nettracer.ws.core.GetOHDsForWTResponseDocument {
    private static final javax.xml.namespace.QName GETOHDSFORWTRESPONSE$0 = new javax.xml.namespace.QName("http://core.ws.nettracer.bagnet.com",
            "getOHDsForWTResponse");

    public GetOHDsForWTResponseDocumentImpl(
        org.apache.xmlbeans.SchemaType sType) {
        super(sType);
    }

    /**
     * Gets the "getOHDsForWTResponse" element
     */
    public com.bagnet.nettracer.ws.core.GetOHDsForWTResponseDocument.GetOHDsForWTResponse getGetOHDsForWTResponse() {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.GetOHDsForWTResponseDocument.GetOHDsForWTResponse target =
                null;
            target = (com.bagnet.nettracer.ws.core.GetOHDsForWTResponseDocument.GetOHDsForWTResponse) get_store()
                                                                                                          .find_element_user(GETOHDSFORWTRESPONSE$0,
                    0);

            if (target == null) {
                return null;
            }

            return target;
        }
    }

    /**
     * Sets the "getOHDsForWTResponse" element
     */
    public void setGetOHDsForWTResponse(
        com.bagnet.nettracer.ws.core.GetOHDsForWTResponseDocument.GetOHDsForWTResponse getOHDsForWTResponse) {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.GetOHDsForWTResponseDocument.GetOHDsForWTResponse target =
                null;
            target = (com.bagnet.nettracer.ws.core.GetOHDsForWTResponseDocument.GetOHDsForWTResponse) get_store()
                                                                                                          .find_element_user(GETOHDSFORWTRESPONSE$0,
                    0);

            if (target == null) {
                target = (com.bagnet.nettracer.ws.core.GetOHDsForWTResponseDocument.GetOHDsForWTResponse) get_store()
                                                                                                              .add_element_user(GETOHDSFORWTRESPONSE$0);
            }

            target.set(getOHDsForWTResponse);
        }
    }

    /**
     * Appends and returns a new empty "getOHDsForWTResponse" element
     */
    public com.bagnet.nettracer.ws.core.GetOHDsForWTResponseDocument.GetOHDsForWTResponse addNewGetOHDsForWTResponse() {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.GetOHDsForWTResponseDocument.GetOHDsForWTResponse target =
                null;
            target = (com.bagnet.nettracer.ws.core.GetOHDsForWTResponseDocument.GetOHDsForWTResponse) get_store()
                                                                                                          .add_element_user(GETOHDSFORWTRESPONSE$0);

            return target;
        }
    }

    /**
     * An XML getOHDsForWTResponse(@http://core.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public static class GetOHDsForWTResponseImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
        implements com.bagnet.nettracer.ws.core.GetOHDsForWTResponseDocument.GetOHDsForWTResponse {
        private static final javax.xml.namespace.QName RETURN$0 = new javax.xml.namespace.QName("http://core.ws.nettracer.bagnet.com",
                "return");

        public GetOHDsForWTResponseImpl(org.apache.xmlbeans.SchemaType sType) {
            super(sType);
        }

        /**
         * Gets array of all "return" elements
         */
        public com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD[] getReturnArray() {
            synchronized (monitor()) {
                check_orphaned();

                java.util.List targetList = new java.util.ArrayList();
                get_store().find_all_element_users(RETURN$0, targetList);

                com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD[] result = new com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD[targetList.size()];
                targetList.toArray(result);

                return result;
            }
        }

        /**
         * Gets ith "return" element
         */
        public com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD getReturnArray(int i) {
            synchronized (monitor()) {
                check_orphaned();

                com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD) get_store()
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

                com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD) get_store()
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
            com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD[] xreturnArray) {
            synchronized (monitor()) {
                check_orphaned();
                arraySetterHelper(xreturnArray, RETURN$0);
            }
        }

        /**
         * Sets ith "return" element
         */
        public void setReturnArray(int i,
            com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD xreturn) {
            synchronized (monitor()) {
                check_orphaned();

                com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD) get_store()
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

                com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD) get_store()
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
        public com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD insertNewReturn(
            int i) {
            synchronized (monitor()) {
                check_orphaned();

                com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD) get_store()
                                                                           .insert_element_user(RETURN$0,
                        i);

                return target;
            }
        }

        /**
         * Appends and returns a new empty value (as xml) as the last "return" element
         */
        public com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD addNewReturn() {
            synchronized (monitor()) {
                check_orphaned();

                com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD) get_store()
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
