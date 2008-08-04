/*
 * An XML document type.
 * Localname: getIncidentPV2
 * Namespace: http://passengerview.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.passengerview.GetIncidentPV2Document
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.passengerview.impl;

/**
 * A document containing one getIncidentPV2(@http://passengerview.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public class GetIncidentPV2DocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
    implements com.bagnet.nettracer.ws.passengerview.GetIncidentPV2Document {
    private static final javax.xml.namespace.QName GETINCIDENTPV2$0 = new javax.xml.namespace.QName("http://passengerview.ws.nettracer.bagnet.com",
            "getIncidentPV2");

    public GetIncidentPV2DocumentImpl(org.apache.xmlbeans.SchemaType sType) {
        super(sType);
    }

    /**
     * Gets the "getIncidentPV2" element
     */
    public com.bagnet.nettracer.ws.passengerview.GetIncidentPV2Document.GetIncidentPV2 getGetIncidentPV2() {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.passengerview.GetIncidentPV2Document.GetIncidentPV2 target =
                null;
            target = (com.bagnet.nettracer.ws.passengerview.GetIncidentPV2Document.GetIncidentPV2) get_store()
                                                                                                       .find_element_user(GETINCIDENTPV2$0,
                    0);

            if (target == null) {
                return null;
            }

            return target;
        }
    }

    /**
     * Sets the "getIncidentPV2" element
     */
    public void setGetIncidentPV2(
        com.bagnet.nettracer.ws.passengerview.GetIncidentPV2Document.GetIncidentPV2 getIncidentPV2) {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.passengerview.GetIncidentPV2Document.GetIncidentPV2 target =
                null;
            target = (com.bagnet.nettracer.ws.passengerview.GetIncidentPV2Document.GetIncidentPV2) get_store()
                                                                                                       .find_element_user(GETINCIDENTPV2$0,
                    0);

            if (target == null) {
                target = (com.bagnet.nettracer.ws.passengerview.GetIncidentPV2Document.GetIncidentPV2) get_store()
                                                                                                           .add_element_user(GETINCIDENTPV2$0);
            }

            target.set(getIncidentPV2);
        }
    }

    /**
     * Appends and returns a new empty "getIncidentPV2" element
     */
    public com.bagnet.nettracer.ws.passengerview.GetIncidentPV2Document.GetIncidentPV2 addNewGetIncidentPV2() {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.passengerview.GetIncidentPV2Document.GetIncidentPV2 target =
                null;
            target = (com.bagnet.nettracer.ws.passengerview.GetIncidentPV2Document.GetIncidentPV2) get_store()
                                                                                                       .add_element_user(GETINCIDENTPV2$0);

            return target;
        }
    }

    /**
     * An XML getIncidentPV2(@http://passengerview.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public static class GetIncidentPV2Impl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
        implements com.bagnet.nettracer.ws.passengerview.GetIncidentPV2Document.GetIncidentPV2 {
        private static final javax.xml.namespace.QName INCIDENTID$0 = new javax.xml.namespace.QName("http://passengerview.ws.nettracer.bagnet.com",
                "incident_id");
        private static final javax.xml.namespace.QName LASTNAME$2 = new javax.xml.namespace.QName("http://passengerview.ws.nettracer.bagnet.com",
                "lastname");
        private static final javax.xml.namespace.QName DONOTAUTHORIZE$4 = new javax.xml.namespace.QName("http://passengerview.ws.nettracer.bagnet.com",
                "doNotAuthorize");

        public GetIncidentPV2Impl(org.apache.xmlbeans.SchemaType sType) {
            super(sType);
        }

        /**
         * Gets the "incident_id" element
         */
        public java.lang.String getIncidentId() {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .find_element_user(INCIDENTID$0,
                        0);

                if (target == null) {
                    return null;
                }

                return target.getStringValue();
            }
        }

        /**
         * Gets (as xml) the "incident_id" element
         */
        public org.apache.xmlbeans.XmlString xgetIncidentId() {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .find_element_user(INCIDENTID$0,
                        0);

                return target;
            }
        }

        /**
         * Tests for nil "incident_id" element
         */
        public boolean isNilIncidentId() {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .find_element_user(INCIDENTID$0,
                        0);

                if (target == null) {
                    return false;
                }

                return target.isNil();
            }
        }

        /**
         * True if has "incident_id" element
         */
        public boolean isSetIncidentId() {
            synchronized (monitor()) {
                check_orphaned();

                return get_store().count_elements(INCIDENTID$0) != 0;
            }
        }

        /**
         * Sets the "incident_id" element
         */
        public void setIncidentId(java.lang.String incidentId) {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .find_element_user(INCIDENTID$0,
                        0);

                if (target == null) {
                    target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                                   .add_element_user(INCIDENTID$0);
                }

                target.setStringValue(incidentId);
            }
        }

        /**
         * Sets (as xml) the "incident_id" element
         */
        public void xsetIncidentId(org.apache.xmlbeans.XmlString incidentId) {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .find_element_user(INCIDENTID$0,
                        0);

                if (target == null) {
                    target = (org.apache.xmlbeans.XmlString) get_store()
                                                                 .add_element_user(INCIDENTID$0);
                }

                target.set(incidentId);
            }
        }

        /**
         * Nils the "incident_id" element
         */
        public void setNilIncidentId() {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .find_element_user(INCIDENTID$0,
                        0);

                if (target == null) {
                    target = (org.apache.xmlbeans.XmlString) get_store()
                                                                 .add_element_user(INCIDENTID$0);
                }

                target.setNil();
            }
        }

        /**
         * Unsets the "incident_id" element
         */
        public void unsetIncidentId() {
            synchronized (monitor()) {
                check_orphaned();
                get_store().remove_element(INCIDENTID$0, 0);
            }
        }

        /**
         * Gets the "lastname" element
         */
        public java.lang.String getLastname() {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .find_element_user(LASTNAME$2,
                        0);

                if (target == null) {
                    return null;
                }

                return target.getStringValue();
            }
        }

        /**
         * Gets (as xml) the "lastname" element
         */
        public org.apache.xmlbeans.XmlString xgetLastname() {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .find_element_user(LASTNAME$2,
                        0);

                return target;
            }
        }

        /**
         * Tests for nil "lastname" element
         */
        public boolean isNilLastname() {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .find_element_user(LASTNAME$2,
                        0);

                if (target == null) {
                    return false;
                }

                return target.isNil();
            }
        }

        /**
         * True if has "lastname" element
         */
        public boolean isSetLastname() {
            synchronized (monitor()) {
                check_orphaned();

                return get_store().count_elements(LASTNAME$2) != 0;
            }
        }

        /**
         * Sets the "lastname" element
         */
        public void setLastname(java.lang.String lastname) {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .find_element_user(LASTNAME$2,
                        0);

                if (target == null) {
                    target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                                   .add_element_user(LASTNAME$2);
                }

                target.setStringValue(lastname);
            }
        }

        /**
         * Sets (as xml) the "lastname" element
         */
        public void xsetLastname(org.apache.xmlbeans.XmlString lastname) {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .find_element_user(LASTNAME$2,
                        0);

                if (target == null) {
                    target = (org.apache.xmlbeans.XmlString) get_store()
                                                                 .add_element_user(LASTNAME$2);
                }

                target.set(lastname);
            }
        }

        /**
         * Nils the "lastname" element
         */
        public void setNilLastname() {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .find_element_user(LASTNAME$2,
                        0);

                if (target == null) {
                    target = (org.apache.xmlbeans.XmlString) get_store()
                                                                 .add_element_user(LASTNAME$2);
                }

                target.setNil();
            }
        }

        /**
         * Unsets the "lastname" element
         */
        public void unsetLastname() {
            synchronized (monitor()) {
                check_orphaned();
                get_store().remove_element(LASTNAME$2, 0);
            }
        }

        /**
         * Gets the "doNotAuthorize" element
         */
        public boolean getDoNotAuthorize() {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .find_element_user(DONOTAUTHORIZE$4,
                        0);

                if (target == null) {
                    return false;
                }

                return target.getBooleanValue();
            }
        }

        /**
         * Gets (as xml) the "doNotAuthorize" element
         */
        public org.apache.xmlbeans.XmlBoolean xgetDoNotAuthorize() {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.XmlBoolean target = null;
                target = (org.apache.xmlbeans.XmlBoolean) get_store()
                                                              .find_element_user(DONOTAUTHORIZE$4,
                        0);

                return target;
            }
        }

        /**
         * True if has "doNotAuthorize" element
         */
        public boolean isSetDoNotAuthorize() {
            synchronized (monitor()) {
                check_orphaned();

                return get_store().count_elements(DONOTAUTHORIZE$4) != 0;
            }
        }

        /**
         * Sets the "doNotAuthorize" element
         */
        public void setDoNotAuthorize(boolean doNotAuthorize) {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .find_element_user(DONOTAUTHORIZE$4,
                        0);

                if (target == null) {
                    target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                                   .add_element_user(DONOTAUTHORIZE$4);
                }

                target.setBooleanValue(doNotAuthorize);
            }
        }

        /**
         * Sets (as xml) the "doNotAuthorize" element
         */
        public void xsetDoNotAuthorize(
            org.apache.xmlbeans.XmlBoolean doNotAuthorize) {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.XmlBoolean target = null;
                target = (org.apache.xmlbeans.XmlBoolean) get_store()
                                                              .find_element_user(DONOTAUTHORIZE$4,
                        0);

                if (target == null) {
                    target = (org.apache.xmlbeans.XmlBoolean) get_store()
                                                                  .add_element_user(DONOTAUTHORIZE$4);
                }

                target.set(doNotAuthorize);
            }
        }

        /**
         * Unsets the "doNotAuthorize" element
         */
        public void unsetDoNotAuthorize() {
            synchronized (monitor()) {
                check_orphaned();
                get_store().remove_element(DONOTAUTHORIZE$4, 0);
            }
        }
    }
}
