/*
 * An XML document type.
 * Localname: placeActionFile
 * Namespace: http://wt_1_0.serviceprovider.nettracer.aero
 * Java type: aero.nettracer.serviceprovider.wt_1_0.PlaceActionFileDocument
 *
 * Automatically generated - do not modify.
 */
package aero.nettracer.serviceprovider.wt_1_0.impl;

/**
 * A document containing one placeActionFile(@http://wt_1_0.serviceprovider.nettracer.aero) element.
 *
 * This is a complex type.
 */
public class PlaceActionFileDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
    implements aero.nettracer.serviceprovider.wt_1_0.PlaceActionFileDocument {
    private static final javax.xml.namespace.QName PLACEACTIONFILE$0 = new javax.xml.namespace.QName("http://wt_1_0.serviceprovider.nettracer.aero",
            "placeActionFile");

    public PlaceActionFileDocumentImpl(org.apache.xmlbeans.SchemaType sType) {
        super(sType);
    }

    /**
     * Gets the "placeActionFile" element
     */
    public aero.nettracer.serviceprovider.wt_1_0.PlaceActionFileDocument.PlaceActionFile getPlaceActionFile() {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.wt_1_0.PlaceActionFileDocument.PlaceActionFile target =
                null;
            target = (aero.nettracer.serviceprovider.wt_1_0.PlaceActionFileDocument.PlaceActionFile) get_store()
                                                                                                         .find_element_user(PLACEACTIONFILE$0,
                    0);

            if (target == null) {
                return null;
            }

            return target;
        }
    }

    /**
     * Sets the "placeActionFile" element
     */
    public void setPlaceActionFile(
        aero.nettracer.serviceprovider.wt_1_0.PlaceActionFileDocument.PlaceActionFile placeActionFile) {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.wt_1_0.PlaceActionFileDocument.PlaceActionFile target =
                null;
            target = (aero.nettracer.serviceprovider.wt_1_0.PlaceActionFileDocument.PlaceActionFile) get_store()
                                                                                                         .find_element_user(PLACEACTIONFILE$0,
                    0);

            if (target == null) {
                target = (aero.nettracer.serviceprovider.wt_1_0.PlaceActionFileDocument.PlaceActionFile) get_store()
                                                                                                             .add_element_user(PLACEACTIONFILE$0);
            }

            target.set(placeActionFile);
        }
    }

    /**
     * Appends and returns a new empty "placeActionFile" element
     */
    public aero.nettracer.serviceprovider.wt_1_0.PlaceActionFileDocument.PlaceActionFile addNewPlaceActionFile() {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.wt_1_0.PlaceActionFileDocument.PlaceActionFile target =
                null;
            target = (aero.nettracer.serviceprovider.wt_1_0.PlaceActionFileDocument.PlaceActionFile) get_store()
                                                                                                         .add_element_user(PLACEACTIONFILE$0);

            return target;
        }
    }

    /**
     * An XML placeActionFile(@http://wt_1_0.serviceprovider.nettracer.aero).
     *
     * This is a complex type.
     */
    public static class PlaceActionFileImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
        implements aero.nettracer.serviceprovider.wt_1_0.PlaceActionFileDocument.PlaceActionFile {
        private static final javax.xml.namespace.QName HEADER$0 = new javax.xml.namespace.QName("http://wt_1_0.serviceprovider.nettracer.aero",
                "header");
        private static final javax.xml.namespace.QName PXF$2 = new javax.xml.namespace.QName("http://wt_1_0.serviceprovider.nettracer.aero",
                "pxf");

        public PlaceActionFileImpl(org.apache.xmlbeans.SchemaType sType) {
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
         * Gets the "pxf" element
         */
        public aero.nettracer.serviceprovider.wt_1_0.common.xsd.Pxf getPxf() {
            synchronized (monitor()) {
                check_orphaned();

                aero.nettracer.serviceprovider.wt_1_0.common.xsd.Pxf target = null;
                target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Pxf) get_store()
                                                                                    .find_element_user(PXF$2,
                        0);

                if (target == null) {
                    return null;
                }

                return target;
            }
        }

        /**
         * Tests for nil "pxf" element
         */
        public boolean isNilPxf() {
            synchronized (monitor()) {
                check_orphaned();

                aero.nettracer.serviceprovider.wt_1_0.common.xsd.Pxf target = null;
                target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Pxf) get_store()
                                                                                    .find_element_user(PXF$2,
                        0);

                if (target == null) {
                    return false;
                }

                return target.isNil();
            }
        }

        /**
         * True if has "pxf" element
         */
        public boolean isSetPxf() {
            synchronized (monitor()) {
                check_orphaned();

                return get_store().count_elements(PXF$2) != 0;
            }
        }

        /**
         * Sets the "pxf" element
         */
        public void setPxf(
            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Pxf pxf) {
            synchronized (monitor()) {
                check_orphaned();

                aero.nettracer.serviceprovider.wt_1_0.common.xsd.Pxf target = null;
                target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Pxf) get_store()
                                                                                    .find_element_user(PXF$2,
                        0);

                if (target == null) {
                    target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Pxf) get_store()
                                                                                        .add_element_user(PXF$2);
                }

                target.set(pxf);
            }
        }

        /**
         * Appends and returns a new empty "pxf" element
         */
        public aero.nettracer.serviceprovider.wt_1_0.common.xsd.Pxf addNewPxf() {
            synchronized (monitor()) {
                check_orphaned();

                aero.nettracer.serviceprovider.wt_1_0.common.xsd.Pxf target = null;
                target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Pxf) get_store()
                                                                                    .add_element_user(PXF$2);

                return target;
            }
        }

        /**
         * Nils the "pxf" element
         */
        public void setNilPxf() {
            synchronized (monitor()) {
                check_orphaned();

                aero.nettracer.serviceprovider.wt_1_0.common.xsd.Pxf target = null;
                target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Pxf) get_store()
                                                                                    .find_element_user(PXF$2,
                        0);

                if (target == null) {
                    target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Pxf) get_store()
                                                                                        .add_element_user(PXF$2);
                }

                target.setNil();
            }
        }

        /**
         * Unsets the "pxf" element
         */
        public void unsetPxf() {
            synchronized (monitor()) {
                check_orphaned();
                get_store().remove_element(PXF$2, 0);
            }
        }
    }
}
