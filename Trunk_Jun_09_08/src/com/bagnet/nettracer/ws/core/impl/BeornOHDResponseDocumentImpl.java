/*
 * An XML document type.
 * Localname: beornOHDResponse
 * Namespace: http://core.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.core.BeornOHDResponseDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.core.impl;

/**
 * A document containing one beornOHDResponse(@http://core.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public class BeornOHDResponseDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
    implements com.bagnet.nettracer.ws.core.BeornOHDResponseDocument {
    private static final javax.xml.namespace.QName BEORNOHDRESPONSE$0 = new javax.xml.namespace.QName("http://core.ws.nettracer.bagnet.com",
            "beornOHDResponse");

    public BeornOHDResponseDocumentImpl(org.apache.xmlbeans.SchemaType sType) {
        super(sType);
    }

    /**
     * Gets the "beornOHDResponse" element
     */
    public com.bagnet.nettracer.ws.core.BeornOHDResponseDocument.BeornOHDResponse getBeornOHDResponse() {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.BeornOHDResponseDocument.BeornOHDResponse target =
                null;
            target = (com.bagnet.nettracer.ws.core.BeornOHDResponseDocument.BeornOHDResponse) get_store()
                                                                                                  .find_element_user(BEORNOHDRESPONSE$0,
                    0);

            if (target == null) {
                return null;
            }

            return target;
        }
    }

    /**
     * Sets the "beornOHDResponse" element
     */
    public void setBeornOHDResponse(
        com.bagnet.nettracer.ws.core.BeornOHDResponseDocument.BeornOHDResponse beornOHDResponse) {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.BeornOHDResponseDocument.BeornOHDResponse target =
                null;
            target = (com.bagnet.nettracer.ws.core.BeornOHDResponseDocument.BeornOHDResponse) get_store()
                                                                                                  .find_element_user(BEORNOHDRESPONSE$0,
                    0);

            if (target == null) {
                target = (com.bagnet.nettracer.ws.core.BeornOHDResponseDocument.BeornOHDResponse) get_store()
                                                                                                      .add_element_user(BEORNOHDRESPONSE$0);
            }

            target.set(beornOHDResponse);
        }
    }

    /**
     * Appends and returns a new empty "beornOHDResponse" element
     */
    public com.bagnet.nettracer.ws.core.BeornOHDResponseDocument.BeornOHDResponse addNewBeornOHDResponse() {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.BeornOHDResponseDocument.BeornOHDResponse target =
                null;
            target = (com.bagnet.nettracer.ws.core.BeornOHDResponseDocument.BeornOHDResponse) get_store()
                                                                                                  .add_element_user(BEORNOHDRESPONSE$0);

            return target;
        }
    }

    /**
     * An XML beornOHDResponse(@http://core.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public static class BeornOHDResponseImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
        implements com.bagnet.nettracer.ws.core.BeornOHDResponseDocument.BeornOHDResponse {
        private static final javax.xml.namespace.QName RETURN$0 = new javax.xml.namespace.QName("http://core.ws.nettracer.bagnet.com",
                "return");

        public BeornOHDResponseImpl(org.apache.xmlbeans.SchemaType sType) {
            super(sType);
        }

        /**
         * Gets the "return" element
         */
        public com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse getReturn() {
            synchronized (monitor()) {
                check_orphaned();

                com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse) get_store()
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

                com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse) get_store()
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
            com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse xreturn) {
            synchronized (monitor()) {
                check_orphaned();

                com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse) get_store()
                                                                                   .find_element_user(RETURN$0,
                        0);

                if (target == null) {
                    target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse) get_store()
                                                                                       .add_element_user(RETURN$0);
                }

                target.set(xreturn);
            }
        }

        /**
         * Appends and returns a new empty "return" element
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
         * Nils the "return" element
         */
        public void setNilReturn() {
            synchronized (monitor()) {
                check_orphaned();

                com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse) get_store()
                                                                                   .find_element_user(RETURN$0,
                        0);

                if (target == null) {
                    target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse) get_store()
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
