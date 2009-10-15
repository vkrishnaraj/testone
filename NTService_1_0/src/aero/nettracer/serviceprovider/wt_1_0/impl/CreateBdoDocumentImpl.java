/*
 * An XML document type.
 * Localname: createBdo
 * Namespace: http://wt_1_0.serviceprovider.nettracer.aero
 * Java type: aero.nettracer.serviceprovider.wt_1_0.CreateBdoDocument
 *
 * Automatically generated - do not modify.
 */
package aero.nettracer.serviceprovider.wt_1_0.impl;

/**
 * A document containing one createBdo(@http://wt_1_0.serviceprovider.nettracer.aero) element.
 *
 * This is a complex type.
 */
public class CreateBdoDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
    implements aero.nettracer.serviceprovider.wt_1_0.CreateBdoDocument {
    private static final javax.xml.namespace.QName CREATEBDO$0 = new javax.xml.namespace.QName("http://wt_1_0.serviceprovider.nettracer.aero",
            "createBdo");

    public CreateBdoDocumentImpl(org.apache.xmlbeans.SchemaType sType) {
        super(sType);
    }

    /**
     * Gets the "createBdo" element
     */
    public aero.nettracer.serviceprovider.wt_1_0.CreateBdoDocument.CreateBdo getCreateBdo() {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.wt_1_0.CreateBdoDocument.CreateBdo target =
                null;
            target = (aero.nettracer.serviceprovider.wt_1_0.CreateBdoDocument.CreateBdo) get_store()
                                                                                             .find_element_user(CREATEBDO$0,
                    0);

            if (target == null) {
                return null;
            }

            return target;
        }
    }

    /**
     * Sets the "createBdo" element
     */
    public void setCreateBdo(
        aero.nettracer.serviceprovider.wt_1_0.CreateBdoDocument.CreateBdo createBdo) {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.wt_1_0.CreateBdoDocument.CreateBdo target =
                null;
            target = (aero.nettracer.serviceprovider.wt_1_0.CreateBdoDocument.CreateBdo) get_store()
                                                                                             .find_element_user(CREATEBDO$0,
                    0);

            if (target == null) {
                target = (aero.nettracer.serviceprovider.wt_1_0.CreateBdoDocument.CreateBdo) get_store()
                                                                                                 .add_element_user(CREATEBDO$0);
            }

            target.set(createBdo);
        }
    }

    /**
     * Appends and returns a new empty "createBdo" element
     */
    public aero.nettracer.serviceprovider.wt_1_0.CreateBdoDocument.CreateBdo addNewCreateBdo() {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.wt_1_0.CreateBdoDocument.CreateBdo target =
                null;
            target = (aero.nettracer.serviceprovider.wt_1_0.CreateBdoDocument.CreateBdo) get_store()
                                                                                             .add_element_user(CREATEBDO$0);

            return target;
        }
    }

    /**
     * An XML createBdo(@http://wt_1_0.serviceprovider.nettracer.aero).
     *
     * This is a complex type.
     */
    public static class CreateBdoImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
        implements aero.nettracer.serviceprovider.wt_1_0.CreateBdoDocument.CreateBdo {
        private static final javax.xml.namespace.QName HEADER$0 = new javax.xml.namespace.QName("http://wt_1_0.serviceprovider.nettracer.aero",
                "header");
        private static final javax.xml.namespace.QName BDO$2 = new javax.xml.namespace.QName("http://wt_1_0.serviceprovider.nettracer.aero",
                "bdo");

        public CreateBdoImpl(org.apache.xmlbeans.SchemaType sType) {
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
         * Gets the "bdo" element
         */
        public aero.nettracer.serviceprovider.wt_1_0.common.xsd.Bdo getBdo() {
            synchronized (monitor()) {
                check_orphaned();

                aero.nettracer.serviceprovider.wt_1_0.common.xsd.Bdo target = null;
                target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Bdo) get_store()
                                                                                    .find_element_user(BDO$2,
                        0);

                if (target == null) {
                    return null;
                }

                return target;
            }
        }

        /**
         * Tests for nil "bdo" element
         */
        public boolean isNilBdo() {
            synchronized (monitor()) {
                check_orphaned();

                aero.nettracer.serviceprovider.wt_1_0.common.xsd.Bdo target = null;
                target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Bdo) get_store()
                                                                                    .find_element_user(BDO$2,
                        0);

                if (target == null) {
                    return false;
                }

                return target.isNil();
            }
        }

        /**
         * True if has "bdo" element
         */
        public boolean isSetBdo() {
            synchronized (monitor()) {
                check_orphaned();

                return get_store().count_elements(BDO$2) != 0;
            }
        }

        /**
         * Sets the "bdo" element
         */
        public void setBdo(
            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Bdo bdo) {
            synchronized (monitor()) {
                check_orphaned();

                aero.nettracer.serviceprovider.wt_1_0.common.xsd.Bdo target = null;
                target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Bdo) get_store()
                                                                                    .find_element_user(BDO$2,
                        0);

                if (target == null) {
                    target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Bdo) get_store()
                                                                                        .add_element_user(BDO$2);
                }

                target.set(bdo);
            }
        }

        /**
         * Appends and returns a new empty "bdo" element
         */
        public aero.nettracer.serviceprovider.wt_1_0.common.xsd.Bdo addNewBdo() {
            synchronized (monitor()) {
                check_orphaned();

                aero.nettracer.serviceprovider.wt_1_0.common.xsd.Bdo target = null;
                target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Bdo) get_store()
                                                                                    .add_element_user(BDO$2);

                return target;
            }
        }

        /**
         * Nils the "bdo" element
         */
        public void setNilBdo() {
            synchronized (monitor()) {
                check_orphaned();

                aero.nettracer.serviceprovider.wt_1_0.common.xsd.Bdo target = null;
                target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Bdo) get_store()
                                                                                    .find_element_user(BDO$2,
                        0);

                if (target == null) {
                    target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Bdo) get_store()
                                                                                        .add_element_user(BDO$2);
                }

                target.setNil();
            }
        }

        /**
         * Unsets the "bdo" element
         */
        public void unsetBdo() {
            synchronized (monitor()) {
                check_orphaned();
                get_store().remove_element(BDO$2, 0);
            }
        }
    }
}
