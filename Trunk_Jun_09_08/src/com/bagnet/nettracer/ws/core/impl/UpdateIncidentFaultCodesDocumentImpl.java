/*
 * An XML document type.
 * Localname: updateIncidentFaultCodes
 * Namespace: http://core.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.core.impl;

/**
 * A document containing one updateIncidentFaultCodes(@http://core.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public class UpdateIncidentFaultCodesDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
    implements com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesDocument {
    private static final javax.xml.namespace.QName UPDATEINCIDENTFAULTCODES$0 = new javax.xml.namespace.QName("http://core.ws.nettracer.bagnet.com",
            "updateIncidentFaultCodes");

    public UpdateIncidentFaultCodesDocumentImpl(
        org.apache.xmlbeans.SchemaType sType) {
        super(sType);
    }

    /**
     * Gets the "updateIncidentFaultCodes" element
     */
    public com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesDocument.UpdateIncidentFaultCodes getUpdateIncidentFaultCodes() {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesDocument.UpdateIncidentFaultCodes target =
                null;
            target = (com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesDocument.UpdateIncidentFaultCodes) get_store()
                                                                                                                  .find_element_user(UPDATEINCIDENTFAULTCODES$0,
                    0);

            if (target == null) {
                return null;
            }

            return target;
        }
    }

    /**
     * Sets the "updateIncidentFaultCodes" element
     */
    public void setUpdateIncidentFaultCodes(
        com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesDocument.UpdateIncidentFaultCodes updateIncidentFaultCodes) {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesDocument.UpdateIncidentFaultCodes target =
                null;
            target = (com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesDocument.UpdateIncidentFaultCodes) get_store()
                                                                                                                  .find_element_user(UPDATEINCIDENTFAULTCODES$0,
                    0);

            if (target == null) {
                target = (com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesDocument.UpdateIncidentFaultCodes) get_store()
                                                                                                                      .add_element_user(UPDATEINCIDENTFAULTCODES$0);
            }

            target.set(updateIncidentFaultCodes);
        }
    }

    /**
     * Appends and returns a new empty "updateIncidentFaultCodes" element
     */
    public com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesDocument.UpdateIncidentFaultCodes addNewUpdateIncidentFaultCodes() {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesDocument.UpdateIncidentFaultCodes target =
                null;
            target = (com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesDocument.UpdateIncidentFaultCodes) get_store()
                                                                                                                  .add_element_user(UPDATEINCIDENTFAULTCODES$0);

            return target;
        }
    }

    /**
     * An XML updateIncidentFaultCodes(@http://core.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public static class UpdateIncidentFaultCodesImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
        implements com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesDocument.UpdateIncidentFaultCodes {
        private static final javax.xml.namespace.QName SESSIONID$0 = new javax.xml.namespace.QName("http://core.ws.nettracer.bagnet.com",
                "session_id");
        private static final javax.xml.namespace.QName INCIDENTID$2 = new javax.xml.namespace.QName("http://core.ws.nettracer.bagnet.com",
                "incidentId");
        private static final javax.xml.namespace.QName FAULTSTATION$4 = new javax.xml.namespace.QName("http://core.ws.nettracer.bagnet.com",
                "faultStation");
        private static final javax.xml.namespace.QName COMPANYCODE$6 = new javax.xml.namespace.QName("http://core.ws.nettracer.bagnet.com",
                "companyCode");
        private static final javax.xml.namespace.QName FAULTCODE$8 = new javax.xml.namespace.QName("http://core.ws.nettracer.bagnet.com",
                "faultCode");
        private static final javax.xml.namespace.QName COMMENT$10 = new javax.xml.namespace.QName("http://core.ws.nettracer.bagnet.com",
                "comment");

        public UpdateIncidentFaultCodesImpl(
            org.apache.xmlbeans.SchemaType sType) {
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
         * Gets the "incidentId" element
         */
        public java.lang.String getIncidentId() {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .find_element_user(INCIDENTID$2,
                        0);

                if (target == null) {
                    return null;
                }

                return target.getStringValue();
            }
        }

        /**
         * Gets (as xml) the "incidentId" element
         */
        public org.apache.xmlbeans.XmlString xgetIncidentId() {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .find_element_user(INCIDENTID$2,
                        0);

                return target;
            }
        }

        /**
         * Tests for nil "incidentId" element
         */
        public boolean isNilIncidentId() {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .find_element_user(INCIDENTID$2,
                        0);

                if (target == null) {
                    return false;
                }

                return target.isNil();
            }
        }

        /**
         * True if has "incidentId" element
         */
        public boolean isSetIncidentId() {
            synchronized (monitor()) {
                check_orphaned();

                return get_store().count_elements(INCIDENTID$2) != 0;
            }
        }

        /**
         * Sets the "incidentId" element
         */
        public void setIncidentId(java.lang.String incidentId) {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .find_element_user(INCIDENTID$2,
                        0);

                if (target == null) {
                    target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                                   .add_element_user(INCIDENTID$2);
                }

                target.setStringValue(incidentId);
            }
        }

        /**
         * Sets (as xml) the "incidentId" element
         */
        public void xsetIncidentId(org.apache.xmlbeans.XmlString incidentId) {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .find_element_user(INCIDENTID$2,
                        0);

                if (target == null) {
                    target = (org.apache.xmlbeans.XmlString) get_store()
                                                                 .add_element_user(INCIDENTID$2);
                }

                target.set(incidentId);
            }
        }

        /**
         * Nils the "incidentId" element
         */
        public void setNilIncidentId() {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .find_element_user(INCIDENTID$2,
                        0);

                if (target == null) {
                    target = (org.apache.xmlbeans.XmlString) get_store()
                                                                 .add_element_user(INCIDENTID$2);
                }

                target.setNil();
            }
        }

        /**
         * Unsets the "incidentId" element
         */
        public void unsetIncidentId() {
            synchronized (monitor()) {
                check_orphaned();
                get_store().remove_element(INCIDENTID$2, 0);
            }
        }

        /**
         * Gets the "faultStation" element
         */
        public java.lang.String getFaultStation() {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .find_element_user(FAULTSTATION$4,
                        0);

                if (target == null) {
                    return null;
                }

                return target.getStringValue();
            }
        }

        /**
         * Gets (as xml) the "faultStation" element
         */
        public org.apache.xmlbeans.XmlString xgetFaultStation() {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .find_element_user(FAULTSTATION$4,
                        0);

                return target;
            }
        }

        /**
         * Tests for nil "faultStation" element
         */
        public boolean isNilFaultStation() {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .find_element_user(FAULTSTATION$4,
                        0);

                if (target == null) {
                    return false;
                }

                return target.isNil();
            }
        }

        /**
         * True if has "faultStation" element
         */
        public boolean isSetFaultStation() {
            synchronized (monitor()) {
                check_orphaned();

                return get_store().count_elements(FAULTSTATION$4) != 0;
            }
        }

        /**
         * Sets the "faultStation" element
         */
        public void setFaultStation(java.lang.String faultStation) {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .find_element_user(FAULTSTATION$4,
                        0);

                if (target == null) {
                    target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                                   .add_element_user(FAULTSTATION$4);
                }

                target.setStringValue(faultStation);
            }
        }

        /**
         * Sets (as xml) the "faultStation" element
         */
        public void xsetFaultStation(org.apache.xmlbeans.XmlString faultStation) {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .find_element_user(FAULTSTATION$4,
                        0);

                if (target == null) {
                    target = (org.apache.xmlbeans.XmlString) get_store()
                                                                 .add_element_user(FAULTSTATION$4);
                }

                target.set(faultStation);
            }
        }

        /**
         * Nils the "faultStation" element
         */
        public void setNilFaultStation() {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .find_element_user(FAULTSTATION$4,
                        0);

                if (target == null) {
                    target = (org.apache.xmlbeans.XmlString) get_store()
                                                                 .add_element_user(FAULTSTATION$4);
                }

                target.setNil();
            }
        }

        /**
         * Unsets the "faultStation" element
         */
        public void unsetFaultStation() {
            synchronized (monitor()) {
                check_orphaned();
                get_store().remove_element(FAULTSTATION$4, 0);
            }
        }

        /**
         * Gets the "companyCode" element
         */
        public java.lang.String getCompanyCode() {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .find_element_user(COMPANYCODE$6,
                        0);

                if (target == null) {
                    return null;
                }

                return target.getStringValue();
            }
        }

        /**
         * Gets (as xml) the "companyCode" element
         */
        public org.apache.xmlbeans.XmlString xgetCompanyCode() {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .find_element_user(COMPANYCODE$6,
                        0);

                return target;
            }
        }

        /**
         * Tests for nil "companyCode" element
         */
        public boolean isNilCompanyCode() {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .find_element_user(COMPANYCODE$6,
                        0);

                if (target == null) {
                    return false;
                }

                return target.isNil();
            }
        }

        /**
         * True if has "companyCode" element
         */
        public boolean isSetCompanyCode() {
            synchronized (monitor()) {
                check_orphaned();

                return get_store().count_elements(COMPANYCODE$6) != 0;
            }
        }

        /**
         * Sets the "companyCode" element
         */
        public void setCompanyCode(java.lang.String companyCode) {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .find_element_user(COMPANYCODE$6,
                        0);

                if (target == null) {
                    target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                                   .add_element_user(COMPANYCODE$6);
                }

                target.setStringValue(companyCode);
            }
        }

        /**
         * Sets (as xml) the "companyCode" element
         */
        public void xsetCompanyCode(org.apache.xmlbeans.XmlString companyCode) {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .find_element_user(COMPANYCODE$6,
                        0);

                if (target == null) {
                    target = (org.apache.xmlbeans.XmlString) get_store()
                                                                 .add_element_user(COMPANYCODE$6);
                }

                target.set(companyCode);
            }
        }

        /**
         * Nils the "companyCode" element
         */
        public void setNilCompanyCode() {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .find_element_user(COMPANYCODE$6,
                        0);

                if (target == null) {
                    target = (org.apache.xmlbeans.XmlString) get_store()
                                                                 .add_element_user(COMPANYCODE$6);
                }

                target.setNil();
            }
        }

        /**
         * Unsets the "companyCode" element
         */
        public void unsetCompanyCode() {
            synchronized (monitor()) {
                check_orphaned();
                get_store().remove_element(COMPANYCODE$6, 0);
            }
        }

        /**
         * Gets the "faultCode" element
         */
        public int getFaultCode() {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .find_element_user(FAULTCODE$8,
                        0);

                if (target == null) {
                    return 0;
                }

                return target.getIntValue();
            }
        }

        /**
         * Gets (as xml) the "faultCode" element
         */
        public org.apache.xmlbeans.XmlInt xgetFaultCode() {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.XmlInt target = null;
                target = (org.apache.xmlbeans.XmlInt) get_store()
                                                          .find_element_user(FAULTCODE$8,
                        0);

                return target;
            }
        }

        /**
         * True if has "faultCode" element
         */
        public boolean isSetFaultCode() {
            synchronized (monitor()) {
                check_orphaned();

                return get_store().count_elements(FAULTCODE$8) != 0;
            }
        }

        /**
         * Sets the "faultCode" element
         */
        public void setFaultCode(int faultCode) {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .find_element_user(FAULTCODE$8,
                        0);

                if (target == null) {
                    target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                                   .add_element_user(FAULTCODE$8);
                }

                target.setIntValue(faultCode);
            }
        }

        /**
         * Sets (as xml) the "faultCode" element
         */
        public void xsetFaultCode(org.apache.xmlbeans.XmlInt faultCode) {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.XmlInt target = null;
                target = (org.apache.xmlbeans.XmlInt) get_store()
                                                          .find_element_user(FAULTCODE$8,
                        0);

                if (target == null) {
                    target = (org.apache.xmlbeans.XmlInt) get_store()
                                                              .add_element_user(FAULTCODE$8);
                }

                target.set(faultCode);
            }
        }

        /**
         * Unsets the "faultCode" element
         */
        public void unsetFaultCode() {
            synchronized (monitor()) {
                check_orphaned();
                get_store().remove_element(FAULTCODE$8, 0);
            }
        }

        /**
         * Gets the "comment" element
         */
        public java.lang.String getComment() {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .find_element_user(COMMENT$10,
                        0);

                if (target == null) {
                    return null;
                }

                return target.getStringValue();
            }
        }

        /**
         * Gets (as xml) the "comment" element
         */
        public org.apache.xmlbeans.XmlString xgetComment() {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .find_element_user(COMMENT$10,
                        0);

                return target;
            }
        }

        /**
         * Tests for nil "comment" element
         */
        public boolean isNilComment() {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .find_element_user(COMMENT$10,
                        0);

                if (target == null) {
                    return false;
                }

                return target.isNil();
            }
        }

        /**
         * True if has "comment" element
         */
        public boolean isSetComment() {
            synchronized (monitor()) {
                check_orphaned();

                return get_store().count_elements(COMMENT$10) != 0;
            }
        }

        /**
         * Sets the "comment" element
         */
        public void setComment(java.lang.String comment) {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .find_element_user(COMMENT$10,
                        0);

                if (target == null) {
                    target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                                   .add_element_user(COMMENT$10);
                }

                target.setStringValue(comment);
            }
        }

        /**
         * Sets (as xml) the "comment" element
         */
        public void xsetComment(org.apache.xmlbeans.XmlString comment) {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .find_element_user(COMMENT$10,
                        0);

                if (target == null) {
                    target = (org.apache.xmlbeans.XmlString) get_store()
                                                                 .add_element_user(COMMENT$10);
                }

                target.set(comment);
            }
        }

        /**
         * Nils the "comment" element
         */
        public void setNilComment() {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .find_element_user(COMMENT$10,
                        0);

                if (target == null) {
                    target = (org.apache.xmlbeans.XmlString) get_store()
                                                                 .add_element_user(COMMENT$10);
                }

                target.setNil();
            }
        }

        /**
         * Unsets the "comment" element
         */
        public void unsetComment() {
            synchronized (monitor()) {
                check_orphaned();
                get_store().remove_element(COMMENT$10, 0);
            }
        }
    }
}
