/*
 * An XML document type.
 * Localname: writeRemark
 * Namespace: http://ws_1_0.serviceprovider.nettracer.aero
 * Java type: aero.nettracer.serviceprovider.ws_1_0.WriteRemarkDocument
 *
 * Automatically generated - do not modify.
 */
package aero.nettracer.serviceprovider.ws_1_0.impl;

/**
 * A document containing one writeRemark(@http://ws_1_0.serviceprovider.nettracer.aero) element.
 *
 * This is a complex type.
 */
public class WriteRemarkDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
    implements aero.nettracer.serviceprovider.ws_1_0.WriteRemarkDocument {
    private static final javax.xml.namespace.QName WRITEREMARK$0 = new javax.xml.namespace.QName("http://ws_1_0.serviceprovider.nettracer.aero",
            "writeRemark");

    public WriteRemarkDocumentImpl(org.apache.xmlbeans.SchemaType sType) {
        super(sType);
    }

    /**
     * Gets the "writeRemark" element
     */
    public aero.nettracer.serviceprovider.ws_1_0.WriteRemarkDocument.WriteRemark getWriteRemark() {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.ws_1_0.WriteRemarkDocument.WriteRemark target =
                null;
            target = (aero.nettracer.serviceprovider.ws_1_0.WriteRemarkDocument.WriteRemark) get_store()
                                                                                                 .find_element_user(WRITEREMARK$0,
                    0);

            if (target == null) {
                return null;
            }

            return target;
        }
    }

    /**
     * Sets the "writeRemark" element
     */
    public void setWriteRemark(
        aero.nettracer.serviceprovider.ws_1_0.WriteRemarkDocument.WriteRemark writeRemark) {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.ws_1_0.WriteRemarkDocument.WriteRemark target =
                null;
            target = (aero.nettracer.serviceprovider.ws_1_0.WriteRemarkDocument.WriteRemark) get_store()
                                                                                                 .find_element_user(WRITEREMARK$0,
                    0);

            if (target == null) {
                target = (aero.nettracer.serviceprovider.ws_1_0.WriteRemarkDocument.WriteRemark) get_store()
                                                                                                     .add_element_user(WRITEREMARK$0);
            }

            target.set(writeRemark);
        }
    }

    /**
     * Appends and returns a new empty "writeRemark" element
     */
    public aero.nettracer.serviceprovider.ws_1_0.WriteRemarkDocument.WriteRemark addNewWriteRemark() {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.ws_1_0.WriteRemarkDocument.WriteRemark target =
                null;
            target = (aero.nettracer.serviceprovider.ws_1_0.WriteRemarkDocument.WriteRemark) get_store()
                                                                                                 .add_element_user(WRITEREMARK$0);

            return target;
        }
    }

    /**
     * An XML writeRemark(@http://ws_1_0.serviceprovider.nettracer.aero).
     *
     * This is a complex type.
     */
    public static class WriteRemarkImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
        implements aero.nettracer.serviceprovider.ws_1_0.WriteRemarkDocument.WriteRemark {
        private static final javax.xml.namespace.QName HEADER$0 = new javax.xml.namespace.QName("http://ws_1_0.serviceprovider.nettracer.aero",
                "header");
        private static final javax.xml.namespace.QName PNR$2 = new javax.xml.namespace.QName("http://ws_1_0.serviceprovider.nettracer.aero",
                "pnr");
        private static final javax.xml.namespace.QName REMARK$4 = new javax.xml.namespace.QName("http://ws_1_0.serviceprovider.nettracer.aero",
                "remark");

        public WriteRemarkImpl(org.apache.xmlbeans.SchemaType sType) {
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
         * Gets the "remark" element
         */
        public java.lang.String getRemark() {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .find_element_user(REMARK$4,
                        0);

                if (target == null) {
                    return null;
                }

                return target.getStringValue();
            }
        }

        /**
         * Gets (as xml) the "remark" element
         */
        public org.apache.xmlbeans.XmlString xgetRemark() {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .find_element_user(REMARK$4,
                        0);

                return target;
            }
        }

        /**
         * Tests for nil "remark" element
         */
        public boolean isNilRemark() {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .find_element_user(REMARK$4,
                        0);

                if (target == null) {
                    return false;
                }

                return target.isNil();
            }
        }

        /**
         * True if has "remark" element
         */
        public boolean isSetRemark() {
            synchronized (monitor()) {
                check_orphaned();

                return get_store().count_elements(REMARK$4) != 0;
            }
        }

        /**
         * Sets the "remark" element
         */
        public void setRemark(java.lang.String remark) {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .find_element_user(REMARK$4,
                        0);

                if (target == null) {
                    target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                                   .add_element_user(REMARK$4);
                }

                target.setStringValue(remark);
            }
        }

        /**
         * Sets (as xml) the "remark" element
         */
        public void xsetRemark(org.apache.xmlbeans.XmlString remark) {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .find_element_user(REMARK$4,
                        0);

                if (target == null) {
                    target = (org.apache.xmlbeans.XmlString) get_store()
                                                                 .add_element_user(REMARK$4);
                }

                target.set(remark);
            }
        }

        /**
         * Nils the "remark" element
         */
        public void setNilRemark() {
            synchronized (monitor()) {
                check_orphaned();

                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .find_element_user(REMARK$4,
                        0);

                if (target == null) {
                    target = (org.apache.xmlbeans.XmlString) get_store()
                                                                 .add_element_user(REMARK$4);
                }

                target.setNil();
            }
        }

        /**
         * Unsets the "remark" element
         */
        public void unsetRemark() {
            synchronized (monitor()) {
                check_orphaned();
                get_store().remove_element(REMARK$4, 0);
            }
        }
    }
}
