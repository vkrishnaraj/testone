/*
 * An XML document type.
 * Localname: saveClaimResponse
 * Namespace: http://onlineclaims.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.onlineclaims.SaveClaimResponseDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.onlineclaims.impl;

/**
 * A document containing one saveClaimResponse(@http://onlineclaims.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public class SaveClaimResponseDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
    implements com.bagnet.nettracer.ws.onlineclaims.SaveClaimResponseDocument {
    private static final javax.xml.namespace.QName SAVECLAIMRESPONSE$0 = new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com",
            "saveClaimResponse");

    public SaveClaimResponseDocumentImpl(org.apache.xmlbeans.SchemaType sType) {
        super(sType);
    }

    /**
     * Gets the "saveClaimResponse" element
     */
    public com.bagnet.nettracer.ws.onlineclaims.SaveClaimResponseDocument.SaveClaimResponse getSaveClaimResponse() {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.onlineclaims.SaveClaimResponseDocument.SaveClaimResponse target =
                null;
            target = (com.bagnet.nettracer.ws.onlineclaims.SaveClaimResponseDocument.SaveClaimResponse) get_store()
                                                                                                            .find_element_user(SAVECLAIMRESPONSE$0,
                    0);

            if (target == null) {
                return null;
            }

            return target;
        }
    }

    /**
     * Sets the "saveClaimResponse" element
     */
    public void setSaveClaimResponse(
        com.bagnet.nettracer.ws.onlineclaims.SaveClaimResponseDocument.SaveClaimResponse saveClaimResponse) {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.onlineclaims.SaveClaimResponseDocument.SaveClaimResponse target =
                null;
            target = (com.bagnet.nettracer.ws.onlineclaims.SaveClaimResponseDocument.SaveClaimResponse) get_store()
                                                                                                            .find_element_user(SAVECLAIMRESPONSE$0,
                    0);

            if (target == null) {
                target = (com.bagnet.nettracer.ws.onlineclaims.SaveClaimResponseDocument.SaveClaimResponse) get_store()
                                                                                                                .add_element_user(SAVECLAIMRESPONSE$0);
            }

            target.set(saveClaimResponse);
        }
    }

    /**
     * Appends and returns a new empty "saveClaimResponse" element
     */
    public com.bagnet.nettracer.ws.onlineclaims.SaveClaimResponseDocument.SaveClaimResponse addNewSaveClaimResponse() {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.onlineclaims.SaveClaimResponseDocument.SaveClaimResponse target =
                null;
            target = (com.bagnet.nettracer.ws.onlineclaims.SaveClaimResponseDocument.SaveClaimResponse) get_store()
                                                                                                            .add_element_user(SAVECLAIMRESPONSE$0);

            return target;
        }
    }

    /**
     * An XML saveClaimResponse(@http://onlineclaims.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public static class SaveClaimResponseImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
        implements com.bagnet.nettracer.ws.onlineclaims.SaveClaimResponseDocument.SaveClaimResponse {
        private static final javax.xml.namespace.QName RETURN$0 = new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com",
                "return");

        public SaveClaimResponseImpl(org.apache.xmlbeans.SchemaType sType) {
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
