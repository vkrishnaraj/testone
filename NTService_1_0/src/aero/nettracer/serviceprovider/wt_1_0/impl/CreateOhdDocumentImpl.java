/*
 * An XML document type.
 * Localname: createOhd
 * Namespace: http://wt_1_0.serviceprovider.nettracer.aero
 * Java type: aero.nettracer.serviceprovider.wt_1_0.CreateOhdDocument
 *
 * Automatically generated - do not modify.
 */
package aero.nettracer.serviceprovider.wt_1_0.impl;

/**
 * A document containing one createOhd(@http://wt_1_0.serviceprovider.nettracer.aero) element.
 *
 * This is a complex type.
 */
public class CreateOhdDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
    implements aero.nettracer.serviceprovider.wt_1_0.CreateOhdDocument {
    private static final javax.xml.namespace.QName CREATEOHD$0 = new javax.xml.namespace.QName("http://wt_1_0.serviceprovider.nettracer.aero",
            "createOhd");

    public CreateOhdDocumentImpl(org.apache.xmlbeans.SchemaType sType) {
        super(sType);
    }

    /**
     * Gets the "createOhd" element
     */
    public aero.nettracer.serviceprovider.wt_1_0.CreateOhdDocument.CreateOhd getCreateOhd() {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.wt_1_0.CreateOhdDocument.CreateOhd target =
                null;
            target = (aero.nettracer.serviceprovider.wt_1_0.CreateOhdDocument.CreateOhd) get_store()
                                                                                             .find_element_user(CREATEOHD$0,
                    0);

            if (target == null) {
                return null;
            }

            return target;
        }
    }

    /**
     * Sets the "createOhd" element
     */
    public void setCreateOhd(
        aero.nettracer.serviceprovider.wt_1_0.CreateOhdDocument.CreateOhd createOhd) {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.wt_1_0.CreateOhdDocument.CreateOhd target =
                null;
            target = (aero.nettracer.serviceprovider.wt_1_0.CreateOhdDocument.CreateOhd) get_store()
                                                                                             .find_element_user(CREATEOHD$0,
                    0);

            if (target == null) {
                target = (aero.nettracer.serviceprovider.wt_1_0.CreateOhdDocument.CreateOhd) get_store()
                                                                                                 .add_element_user(CREATEOHD$0);
            }

            target.set(createOhd);
        }
    }

    /**
     * Appends and returns a new empty "createOhd" element
     */
    public aero.nettracer.serviceprovider.wt_1_0.CreateOhdDocument.CreateOhd addNewCreateOhd() {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.wt_1_0.CreateOhdDocument.CreateOhd target =
                null;
            target = (aero.nettracer.serviceprovider.wt_1_0.CreateOhdDocument.CreateOhd) get_store()
                                                                                             .add_element_user(CREATEOHD$0);

            return target;
        }
    }

    /**
     * An XML createOhd(@http://wt_1_0.serviceprovider.nettracer.aero).
     *
     * This is a complex type.
     */
    public static class CreateOhdImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
        implements aero.nettracer.serviceprovider.wt_1_0.CreateOhdDocument.CreateOhd {
        private static final javax.xml.namespace.QName HEADER$0 = new javax.xml.namespace.QName("http://wt_1_0.serviceprovider.nettracer.aero",
                "header");
        private static final javax.xml.namespace.QName OHD$2 = new javax.xml.namespace.QName("http://wt_1_0.serviceprovider.nettracer.aero",
                "ohd");

        public CreateOhdImpl(org.apache.xmlbeans.SchemaType sType) {
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
         * Gets the "ohd" element
         */
        public aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd getOhd() {
            synchronized (monitor()) {
                check_orphaned();

                aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd target = null;
                target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd) get_store()
                                                                                    .find_element_user(OHD$2,
                        0);

                if (target == null) {
                    return null;
                }

                return target;
            }
        }

        /**
         * Tests for nil "ohd" element
         */
        public boolean isNilOhd() {
            synchronized (monitor()) {
                check_orphaned();

                aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd target = null;
                target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd) get_store()
                                                                                    .find_element_user(OHD$2,
                        0);

                if (target == null) {
                    return false;
                }

                return target.isNil();
            }
        }

        /**
         * True if has "ohd" element
         */
        public boolean isSetOhd() {
            synchronized (monitor()) {
                check_orphaned();

                return get_store().count_elements(OHD$2) != 0;
            }
        }

        /**
         * Sets the "ohd" element
         */
        public void setOhd(
            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd ohd) {
            synchronized (monitor()) {
                check_orphaned();

                aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd target = null;
                target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd) get_store()
                                                                                    .find_element_user(OHD$2,
                        0);

                if (target == null) {
                    target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd) get_store()
                                                                                        .add_element_user(OHD$2);
                }

                target.set(ohd);
            }
        }

        /**
         * Appends and returns a new empty "ohd" element
         */
        public aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd addNewOhd() {
            synchronized (monitor()) {
                check_orphaned();

                aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd target = null;
                target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd) get_store()
                                                                                    .add_element_user(OHD$2);

                return target;
            }
        }

        /**
         * Nils the "ohd" element
         */
        public void setNilOhd() {
            synchronized (monitor()) {
                check_orphaned();

                aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd target = null;
                target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd) get_store()
                                                                                    .find_element_user(OHD$2,
                        0);

                if (target == null) {
                    target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd) get_store()
                                                                                        .add_element_user(OHD$2);
                }

                target.setNil();
            }
        }

        /**
         * Unsets the "ohd" element
         */
        public void unsetOhd() {
            synchronized (monitor()) {
                check_orphaned();
                get_store().remove_element(OHD$2, 0);
            }
        }
    }
}
