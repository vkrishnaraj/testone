/*
 * An XML document type.
 * Localname: getActionFileDetails
 * Namespace: http://wt_1_0.serviceprovider.nettracer.aero
 * Java type: aero.nettracer.serviceprovider.wt_1_0.GetActionFileDetailsDocument
 *
 * Automatically generated - do not modify.
 */
package aero.nettracer.serviceprovider.wt_1_0.impl;

/**
 * A document containing one getActionFileDetails(@http://wt_1_0.serviceprovider.nettracer.aero) element.
 *
 * This is a complex type.
 */
public class GetActionFileDetailsDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
    implements aero.nettracer.serviceprovider.wt_1_0.GetActionFileDetailsDocument {
    private static final javax.xml.namespace.QName GETACTIONFILEDETAILS$0 = new javax.xml.namespace.QName("http://wt_1_0.serviceprovider.nettracer.aero",
            "getActionFileDetails");

    public GetActionFileDetailsDocumentImpl(
        org.apache.xmlbeans.SchemaType sType) {
        super(sType);
    }

    /**
     * Gets the "getActionFileDetails" element
     */
    public aero.nettracer.serviceprovider.wt_1_0.GetActionFileDetailsDocument.GetActionFileDetails getGetActionFileDetails() {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.wt_1_0.GetActionFileDetailsDocument.GetActionFileDetails target =
                null;
            target = (aero.nettracer.serviceprovider.wt_1_0.GetActionFileDetailsDocument.GetActionFileDetails) get_store()
                                                                                                                   .find_element_user(GETACTIONFILEDETAILS$0,
                    0);

            if (target == null) {
                return null;
            }

            return target;
        }
    }

    /**
     * Sets the "getActionFileDetails" element
     */
    public void setGetActionFileDetails(
        aero.nettracer.serviceprovider.wt_1_0.GetActionFileDetailsDocument.GetActionFileDetails getActionFileDetails) {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.wt_1_0.GetActionFileDetailsDocument.GetActionFileDetails target =
                null;
            target = (aero.nettracer.serviceprovider.wt_1_0.GetActionFileDetailsDocument.GetActionFileDetails) get_store()
                                                                                                                   .find_element_user(GETACTIONFILEDETAILS$0,
                    0);

            if (target == null) {
                target = (aero.nettracer.serviceprovider.wt_1_0.GetActionFileDetailsDocument.GetActionFileDetails) get_store()
                                                                                                                       .add_element_user(GETACTIONFILEDETAILS$0);
            }

            target.set(getActionFileDetails);
        }
    }

    /**
     * Appends and returns a new empty "getActionFileDetails" element
     */
    public aero.nettracer.serviceprovider.wt_1_0.GetActionFileDetailsDocument.GetActionFileDetails addNewGetActionFileDetails() {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.wt_1_0.GetActionFileDetailsDocument.GetActionFileDetails target =
                null;
            target = (aero.nettracer.serviceprovider.wt_1_0.GetActionFileDetailsDocument.GetActionFileDetails) get_store()
                                                                                                                   .add_element_user(GETACTIONFILEDETAILS$0);

            return target;
        }
    }

    /**
     * An XML getActionFileDetails(@http://wt_1_0.serviceprovider.nettracer.aero).
     *
     * This is a complex type.
     */
    public static class GetActionFileDetailsImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
        implements aero.nettracer.serviceprovider.wt_1_0.GetActionFileDetailsDocument.GetActionFileDetails {
        private static final javax.xml.namespace.QName HEADER$0 = new javax.xml.namespace.QName("http://wt_1_0.serviceprovider.nettracer.aero",
                "header");
        private static final javax.xml.namespace.QName DATA$2 = new javax.xml.namespace.QName("http://wt_1_0.serviceprovider.nettracer.aero",
                "data");

        public GetActionFileDetailsImpl(org.apache.xmlbeans.SchemaType sType) {
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
         * Gets the "data" element
         */
        public aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileRequestData getData() {
            synchronized (monitor()) {
                check_orphaned();

                aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileRequestData target =
                    null;
                target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileRequestData) get_store()
                                                                                                      .find_element_user(DATA$2,
                        0);

                if (target == null) {
                    return null;
                }

                return target;
            }
        }

        /**
         * Tests for nil "data" element
         */
        public boolean isNilData() {
            synchronized (monitor()) {
                check_orphaned();

                aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileRequestData target =
                    null;
                target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileRequestData) get_store()
                                                                                                      .find_element_user(DATA$2,
                        0);

                if (target == null) {
                    return false;
                }

                return target.isNil();
            }
        }

        /**
         * True if has "data" element
         */
        public boolean isSetData() {
            synchronized (monitor()) {
                check_orphaned();

                return get_store().count_elements(DATA$2) != 0;
            }
        }

        /**
         * Sets the "data" element
         */
        public void setData(
            aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileRequestData data) {
            synchronized (monitor()) {
                check_orphaned();

                aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileRequestData target =
                    null;
                target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileRequestData) get_store()
                                                                                                      .find_element_user(DATA$2,
                        0);

                if (target == null) {
                    target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileRequestData) get_store()
                                                                                                          .add_element_user(DATA$2);
                }

                target.set(data);
            }
        }

        /**
         * Appends and returns a new empty "data" element
         */
        public aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileRequestData addNewData() {
            synchronized (monitor()) {
                check_orphaned();

                aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileRequestData target =
                    null;
                target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileRequestData) get_store()
                                                                                                      .add_element_user(DATA$2);

                return target;
            }
        }

        /**
         * Nils the "data" element
         */
        public void setNilData() {
            synchronized (monitor()) {
                check_orphaned();

                aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileRequestData target =
                    null;
                target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileRequestData) get_store()
                                                                                                      .find_element_user(DATA$2,
                        0);

                if (target == null) {
                    target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileRequestData) get_store()
                                                                                                          .add_element_user(DATA$2);
                }

                target.setNil();
            }
        }

        /**
         * Unsets the "data" element
         */
        public void unsetData() {
            synchronized (monitor()) {
                check_orphaned();
                get_store().remove_element(DATA$2, 0);
            }
        }
    }
}
