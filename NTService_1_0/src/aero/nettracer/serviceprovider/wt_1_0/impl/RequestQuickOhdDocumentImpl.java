/*
 * An XML document type.
 * Localname: requestQuickOhd
 * Namespace: http://wt_1_0.serviceprovider.nettracer.aero
 * Java type: aero.nettracer.serviceprovider.wt_1_0.RequestQuickOhdDocument
 *
 * Automatically generated - do not modify.
 */
package aero.nettracer.serviceprovider.wt_1_0.impl;

/**
 * A document containing one requestQuickOhd(@http://wt_1_0.serviceprovider.nettracer.aero) element.
 *
 * This is a complex type.
 */
public class RequestQuickOhdDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
    implements aero.nettracer.serviceprovider.wt_1_0.RequestQuickOhdDocument {
    private static final javax.xml.namespace.QName REQUESTQUICKOHD$0 = new javax.xml.namespace.QName("http://wt_1_0.serviceprovider.nettracer.aero",
            "requestQuickOhd");

    public RequestQuickOhdDocumentImpl(org.apache.xmlbeans.SchemaType sType) {
        super(sType);
    }

    /**
     * Gets the "requestQuickOhd" element
     */
    public aero.nettracer.serviceprovider.wt_1_0.RequestQuickOhdDocument.RequestQuickOhd getRequestQuickOhd() {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.wt_1_0.RequestQuickOhdDocument.RequestQuickOhd target =
                null;
            target = (aero.nettracer.serviceprovider.wt_1_0.RequestQuickOhdDocument.RequestQuickOhd) get_store()
                                                                                                         .find_element_user(REQUESTQUICKOHD$0,
                    0);

            if (target == null) {
                return null;
            }

            return target;
        }
    }

    /**
     * Sets the "requestQuickOhd" element
     */
    public void setRequestQuickOhd(
        aero.nettracer.serviceprovider.wt_1_0.RequestQuickOhdDocument.RequestQuickOhd requestQuickOhd) {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.wt_1_0.RequestQuickOhdDocument.RequestQuickOhd target =
                null;
            target = (aero.nettracer.serviceprovider.wt_1_0.RequestQuickOhdDocument.RequestQuickOhd) get_store()
                                                                                                         .find_element_user(REQUESTQUICKOHD$0,
                    0);

            if (target == null) {
                target = (aero.nettracer.serviceprovider.wt_1_0.RequestQuickOhdDocument.RequestQuickOhd) get_store()
                                                                                                             .add_element_user(REQUESTQUICKOHD$0);
            }

            target.set(requestQuickOhd);
        }
    }

    /**
     * Appends and returns a new empty "requestQuickOhd" element
     */
    public aero.nettracer.serviceprovider.wt_1_0.RequestQuickOhdDocument.RequestQuickOhd addNewRequestQuickOhd() {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.wt_1_0.RequestQuickOhdDocument.RequestQuickOhd target =
                null;
            target = (aero.nettracer.serviceprovider.wt_1_0.RequestQuickOhdDocument.RequestQuickOhd) get_store()
                                                                                                         .add_element_user(REQUESTQUICKOHD$0);

            return target;
        }
    }

    /**
     * An XML requestQuickOhd(@http://wt_1_0.serviceprovider.nettracer.aero).
     *
     * This is a complex type.
     */
    public static class RequestQuickOhdImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
        implements aero.nettracer.serviceprovider.wt_1_0.RequestQuickOhdDocument.RequestQuickOhd {
        private static final javax.xml.namespace.QName HEADER$0 = new javax.xml.namespace.QName("http://wt_1_0.serviceprovider.nettracer.aero",
                "header");
        private static final javax.xml.namespace.QName DATA$2 = new javax.xml.namespace.QName("http://wt_1_0.serviceprovider.nettracer.aero",
                "data");

        public RequestQuickOhdImpl(org.apache.xmlbeans.SchemaType sType) {
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
        public aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardOhd getData() {
            synchronized (monitor()) {
                check_orphaned();

                aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardOhd target =
                    null;
                target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardOhd) get_store()
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

                aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardOhd target =
                    null;
                target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardOhd) get_store()
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
            aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardOhd data) {
            synchronized (monitor()) {
                check_orphaned();

                aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardOhd target =
                    null;
                target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardOhd) get_store()
                                                                                           .find_element_user(DATA$2,
                        0);

                if (target == null) {
                    target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardOhd) get_store()
                                                                                               .add_element_user(DATA$2);
                }

                target.set(data);
            }
        }

        /**
         * Appends and returns a new empty "data" element
         */
        public aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardOhd addNewData() {
            synchronized (monitor()) {
                check_orphaned();

                aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardOhd target =
                    null;
                target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardOhd) get_store()
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

                aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardOhd target =
                    null;
                target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardOhd) get_store()
                                                                                           .find_element_user(DATA$2,
                        0);

                if (target == null) {
                    target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardOhd) get_store()
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
