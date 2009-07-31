/*
 * An XML document type.
 * Localname: getReservationData
 * Namespace: http://ws_1_0.serviceprovider.nettracer.aero
 * Java type: aero.nettracer.serviceprovider.ws_1_0.GetReservationDataDocument
 *
 * Automatically generated - do not modify.
 */
package aero.nettracer.serviceprovider.ws_1_0.impl;

/**
 * A document containing one getReservationData(@http://ws_1_0.serviceprovider.nettracer.aero) element.
 *
 * This is a complex type.
 */
public class GetReservationDataDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
    implements aero.nettracer.serviceprovider.ws_1_0.GetReservationDataDocument {
    private static final javax.xml.namespace.QName GETRESERVATIONDATA$0 = new javax.xml.namespace.QName("http://ws_1_0.serviceprovider.nettracer.aero",
            "getReservationData");

    public GetReservationDataDocumentImpl(org.apache.xmlbeans.SchemaType sType) {
        super(sType);
    }

    /**
     * Gets the "getReservationData" element
     */
    public aero.nettracer.serviceprovider.ws_1_0.GetReservationDataDocument.GetReservationData getGetReservationData() {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.ws_1_0.GetReservationDataDocument.GetReservationData target =
                null;
            target = (aero.nettracer.serviceprovider.ws_1_0.GetReservationDataDocument.GetReservationData) get_store()
                                                                                                               .find_element_user(GETRESERVATIONDATA$0,
                    0);

            if (target == null) {
                return null;
            }

            return target;
        }
    }

    /**
     * Sets the "getReservationData" element
     */
    public void setGetReservationData(
        aero.nettracer.serviceprovider.ws_1_0.GetReservationDataDocument.GetReservationData getReservationData) {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.ws_1_0.GetReservationDataDocument.GetReservationData target =
                null;
            target = (aero.nettracer.serviceprovider.ws_1_0.GetReservationDataDocument.GetReservationData) get_store()
                                                                                                               .find_element_user(GETRESERVATIONDATA$0,
                    0);

            if (target == null) {
                target = (aero.nettracer.serviceprovider.ws_1_0.GetReservationDataDocument.GetReservationData) get_store()
                                                                                                                   .add_element_user(GETRESERVATIONDATA$0);
            }

            target.set(getReservationData);
        }
    }

    /**
     * Appends and returns a new empty "getReservationData" element
     */
    public aero.nettracer.serviceprovider.ws_1_0.GetReservationDataDocument.GetReservationData addNewGetReservationData() {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.ws_1_0.GetReservationDataDocument.GetReservationData target =
                null;
            target = (aero.nettracer.serviceprovider.ws_1_0.GetReservationDataDocument.GetReservationData) get_store()
                                                                                                               .add_element_user(GETRESERVATIONDATA$0);

            return target;
        }
    }

    /**
     * An XML getReservationData(@http://ws_1_0.serviceprovider.nettracer.aero).
     *
     * This is a complex type.
     */
    public static class GetReservationDataImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
        implements aero.nettracer.serviceprovider.ws_1_0.GetReservationDataDocument.GetReservationData {
        private static final javax.xml.namespace.QName HEADER$0 = new javax.xml.namespace.QName("http://ws_1_0.serviceprovider.nettracer.aero",
                "header");
        private static final javax.xml.namespace.QName PNR$2 = new javax.xml.namespace.QName("http://ws_1_0.serviceprovider.nettracer.aero",
                "pnr");
        private static final javax.xml.namespace.QName BAGTAG$4 = new javax.xml.namespace.QName("http://ws_1_0.serviceprovider.nettracer.aero",
                "bagTag");

        public GetReservationDataImpl(org.apache.xmlbeans.SchemaType sType) {
            super(sType);
        }

        /**
         * Gets the "header" element
         */
        public aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader getHeader() {
            synchronized (monitor()) {
                check_orphaned();

                aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader target =
                    null;
                target = (aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader) get_store()
                                                                                              .find_element_user(HEADER$0,
                        0);

                if (target == null) {
                    return null;
                }

                return target;
            }
        }

        /**
         * Tests for nil "header" element
         */
        public boolean isNilHeader() {
            synchronized (monitor()) {
                check_orphaned();

                aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader target =
                    null;
                target = (aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader) get_store()
                                                                                              .find_element_user(HEADER$0,
                        0);

                if (target == null) {
                    return false;
                }

                return target.isNil();
            }
        }

        /**
         * True if has "header" element
         */
        public boolean isSetHeader() {
            synchronized (monitor()) {
                check_orphaned();

                return get_store().count_elements(HEADER$0) != 0;
            }
        }

        /**
         * Sets the "header" element
         */
        public void setHeader(
            aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader header) {
            synchronized (monitor()) {
                check_orphaned();

                aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader target =
                    null;
                target = (aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader) get_store()
                                                                                              .find_element_user(HEADER$0,
                        0);

                if (target == null) {
                    target = (aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader) get_store()
                                                                                                  .add_element_user(HEADER$0);
                }

                target.set(header);
            }
        }

        /**
         * Appends and returns a new empty "header" element
         */
        public aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader addNewHeader() {
            synchronized (monitor()) {
                check_orphaned();

                aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader target =
                    null;
                target = (aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader) get_store()
                                                                                              .add_element_user(HEADER$0);

                return target;
            }
        }

        /**
         * Nils the "header" element
         */
        public void setNilHeader() {
            synchronized (monitor()) {
                check_orphaned();

                aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader target =
                    null;
                target = (aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader) get_store()
                                                                                              .find_element_user(HEADER$0,
                        0);

                if (target == null) {
                    target = (aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader) get_store()
                                                                                                  .add_element_user(HEADER$0);
                }

                target.setNil();
            }
        }

        /**
         * Unsets the "header" element
         */
        public void unsetHeader() {
            synchronized (monitor()) {
                check_orphaned();
                get_store().remove_element(HEADER$0, 0);
            }
        }

        /**
         * Gets the "pnr" element
         */
        public java.lang.String getPnr() {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .find_element_user(PNR$2,
                        0);

                if (target == null) {
                    return null;
                }

                return target.getStringValue();
            }
        }

        /**
         * Gets (as xml) the "pnr" element
         */
        public org.apache.xmlbeans.XmlString xgetPnr() {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .find_element_user(PNR$2,
                        0);

                return target;
            }
        }

        /**
         * Tests for nil "pnr" element
         */
        public boolean isNilPnr() {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .find_element_user(PNR$2,
                        0);

                if (target == null) {
                    return false;
                }

                return target.isNil();
            }
        }

        /**
         * True if has "pnr" element
         */
        public boolean isSetPnr() {
            synchronized (monitor()) {
                check_orphaned();

                return get_store().count_elements(PNR$2) != 0;
            }
        }

        /**
         * Sets the "pnr" element
         */
        public void setPnr(java.lang.String pnr) {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .find_element_user(PNR$2,
                        0);

                if (target == null) {
                    target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                                   .add_element_user(PNR$2);
                }

                target.setStringValue(pnr);
            }
        }

        /**
         * Sets (as xml) the "pnr" element
         */
        public void xsetPnr(org.apache.xmlbeans.XmlString pnr) {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .find_element_user(PNR$2,
                        0);

                if (target == null) {
                    target = (org.apache.xmlbeans.XmlString) get_store()
                                                                 .add_element_user(PNR$2);
                }

                target.set(pnr);
            }
        }

        /**
         * Nils the "pnr" element
         */
        public void setNilPnr() {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .find_element_user(PNR$2,
                        0);

                if (target == null) {
                    target = (org.apache.xmlbeans.XmlString) get_store()
                                                                 .add_element_user(PNR$2);
                }

                target.setNil();
            }
        }

        /**
         * Unsets the "pnr" element
         */
        public void unsetPnr() {
            synchronized (monitor()) {
                check_orphaned();
                get_store().remove_element(PNR$2, 0);
            }
        }

        /**
         * Gets the "bagTag" element
         */
        public java.lang.String getBagTag() {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .find_element_user(BAGTAG$4,
                        0);

                if (target == null) {
                    return null;
                }

                return target.getStringValue();
            }
        }

        /**
         * Gets (as xml) the "bagTag" element
         */
        public org.apache.xmlbeans.XmlString xgetBagTag() {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .find_element_user(BAGTAG$4,
                        0);

                return target;
            }
        }

        /**
         * Tests for nil "bagTag" element
         */
        public boolean isNilBagTag() {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .find_element_user(BAGTAG$4,
                        0);

                if (target == null) {
                    return false;
                }

                return target.isNil();
            }
        }

        /**
         * True if has "bagTag" element
         */
        public boolean isSetBagTag() {
            synchronized (monitor()) {
                check_orphaned();

                return get_store().count_elements(BAGTAG$4) != 0;
            }
        }

        /**
         * Sets the "bagTag" element
         */
        public void setBagTag(java.lang.String bagTag) {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .find_element_user(BAGTAG$4,
                        0);

                if (target == null) {
                    target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                                   .add_element_user(BAGTAG$4);
                }

                target.setStringValue(bagTag);
            }
        }

        /**
         * Sets (as xml) the "bagTag" element
         */
        public void xsetBagTag(org.apache.xmlbeans.XmlString bagTag) {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .find_element_user(BAGTAG$4,
                        0);

                if (target == null) {
                    target = (org.apache.xmlbeans.XmlString) get_store()
                                                                 .add_element_user(BAGTAG$4);
                }

                target.set(bagTag);
            }
        }

        /**
         * Nils the "bagTag" element
         */
        public void setNilBagTag() {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .find_element_user(BAGTAG$4,
                        0);

                if (target == null) {
                    target = (org.apache.xmlbeans.XmlString) get_store()
                                                                 .add_element_user(BAGTAG$4);
                }

                target.setNil();
            }
        }

        /**
         * Unsets the "bagTag" element
         */
        public void unsetBagTag() {
            synchronized (monitor()) {
                check_orphaned();
                get_store().remove_element(BAGTAG$4, 0);
            }
        }
    }
}
