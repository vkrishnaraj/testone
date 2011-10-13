/*
 * An XML document type.
 * Localname: queryForFaultCodeResponse
 * Namespace: http://core.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.core.QueryForFaultCodeResponseDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.core.impl;

/**
 * A document containing one queryForFaultCodeResponse(@http://core.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public class QueryForFaultCodeResponseDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
    implements com.bagnet.nettracer.ws.core.QueryForFaultCodeResponseDocument {
    private static final javax.xml.namespace.QName QUERYFORFAULTCODERESPONSE$0 = new javax.xml.namespace.QName("http://core.ws.nettracer.bagnet.com",
            "queryForFaultCodeResponse");

    public QueryForFaultCodeResponseDocumentImpl(
        org.apache.xmlbeans.SchemaType sType) {
        super(sType);
    }

    /**
     * Gets the "queryForFaultCodeResponse" element
     */
    public com.bagnet.nettracer.ws.core.QueryForFaultCodeResponseDocument.QueryForFaultCodeResponse getQueryForFaultCodeResponse() {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.QueryForFaultCodeResponseDocument.QueryForFaultCodeResponse target =
                null;
            target = (com.bagnet.nettracer.ws.core.QueryForFaultCodeResponseDocument.QueryForFaultCodeResponse) get_store()
                                                                                                                    .find_element_user(QUERYFORFAULTCODERESPONSE$0,
                    0);

            if (target == null) {
                return null;
            }

            return target;
        }
    }

    /**
     * Sets the "queryForFaultCodeResponse" element
     */
    public void setQueryForFaultCodeResponse(
        com.bagnet.nettracer.ws.core.QueryForFaultCodeResponseDocument.QueryForFaultCodeResponse queryForFaultCodeResponse) {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.QueryForFaultCodeResponseDocument.QueryForFaultCodeResponse target =
                null;
            target = (com.bagnet.nettracer.ws.core.QueryForFaultCodeResponseDocument.QueryForFaultCodeResponse) get_store()
                                                                                                                    .find_element_user(QUERYFORFAULTCODERESPONSE$0,
                    0);

            if (target == null) {
                target = (com.bagnet.nettracer.ws.core.QueryForFaultCodeResponseDocument.QueryForFaultCodeResponse) get_store()
                                                                                                                        .add_element_user(QUERYFORFAULTCODERESPONSE$0);
            }

            target.set(queryForFaultCodeResponse);
        }
    }

    /**
     * Appends and returns a new empty "queryForFaultCodeResponse" element
     */
    public com.bagnet.nettracer.ws.core.QueryForFaultCodeResponseDocument.QueryForFaultCodeResponse addNewQueryForFaultCodeResponse() {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.QueryForFaultCodeResponseDocument.QueryForFaultCodeResponse target =
                null;
            target = (com.bagnet.nettracer.ws.core.QueryForFaultCodeResponseDocument.QueryForFaultCodeResponse) get_store()
                                                                                                                    .add_element_user(QUERYFORFAULTCODERESPONSE$0);

            return target;
        }
    }

    /**
     * An XML queryForFaultCodeResponse(@http://core.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public static class QueryForFaultCodeResponseImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
        implements com.bagnet.nettracer.ws.core.QueryForFaultCodeResponseDocument.QueryForFaultCodeResponse {
        private static final javax.xml.namespace.QName RETURN$0 = new javax.xml.namespace.QName("http://core.ws.nettracer.bagnet.com",
                "return");

        public QueryForFaultCodeResponseImpl(
            org.apache.xmlbeans.SchemaType sType) {
            super(sType);
        }

        /**
         * Gets the "return" element
         */
        public com.bagnet.nettracer.ws.core.pojo.xsd.WSQueryResponse getReturn() {
            synchronized (monitor()) {
                check_orphaned();

                com.bagnet.nettracer.ws.core.pojo.xsd.WSQueryResponse target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSQueryResponse) get_store()
                                                                                     .find_element_user(RETURN$0,
                        0);

                if (target == null) {
                    return null;
                }

                return target;
            }
        }

        /**
         * Tests for nil "return" element
         */
        public boolean isNilReturn() {
            synchronized (monitor()) {
                check_orphaned();

                com.bagnet.nettracer.ws.core.pojo.xsd.WSQueryResponse target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSQueryResponse) get_store()
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
        public void setReturn(
            com.bagnet.nettracer.ws.core.pojo.xsd.WSQueryResponse xreturn) {
            synchronized (monitor()) {
                check_orphaned();

                com.bagnet.nettracer.ws.core.pojo.xsd.WSQueryResponse target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSQueryResponse) get_store()
                                                                                     .find_element_user(RETURN$0,
                        0);

                if (target == null) {
                    target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSQueryResponse) get_store()
                                                                                         .add_element_user(RETURN$0);
                }

                target.set(xreturn);
            }
        }

        /**
         * Appends and returns a new empty "return" element
         */
        public com.bagnet.nettracer.ws.core.pojo.xsd.WSQueryResponse addNewReturn() {
            synchronized (monitor()) {
                check_orphaned();

                com.bagnet.nettracer.ws.core.pojo.xsd.WSQueryResponse target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSQueryResponse) get_store()
                                                                                     .add_element_user(RETURN$0);

                return target;
            }
        }

        /**
         * Nils the "return" element
         */
        public void setNilReturn() {
            synchronized (monitor()) {
                check_orphaned();

                com.bagnet.nettracer.ws.core.pojo.xsd.WSQueryResponse target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSQueryResponse) get_store()
                                                                                     .find_element_user(RETURN$0,
                        0);

                if (target == null) {
                    target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSQueryResponse) get_store()
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
