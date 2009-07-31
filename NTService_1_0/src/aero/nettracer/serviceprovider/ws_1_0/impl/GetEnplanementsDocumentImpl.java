/*
 * An XML document type.
 * Localname: getEnplanements
 * Namespace: http://ws_1_0.serviceprovider.nettracer.aero
 * Java type: aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsDocument
 *
 * Automatically generated - do not modify.
 */
package aero.nettracer.serviceprovider.ws_1_0.impl;

/**
 * A document containing one getEnplanements(@http://ws_1_0.serviceprovider.nettracer.aero) element.
 *
 * This is a complex type.
 */
public class GetEnplanementsDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
    implements aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsDocument {
    private static final javax.xml.namespace.QName GETENPLANEMENTS$0 = new javax.xml.namespace.QName("http://ws_1_0.serviceprovider.nettracer.aero",
            "getEnplanements");

    public GetEnplanementsDocumentImpl(org.apache.xmlbeans.SchemaType sType) {
        super(sType);
    }

    /**
     * Gets the "getEnplanements" element
     */
    public aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsDocument.GetEnplanements getGetEnplanements() {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsDocument.GetEnplanements target =
                null;
            target = (aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsDocument.GetEnplanements) get_store()
                                                                                                         .find_element_user(GETENPLANEMENTS$0,
                    0);

            if (target == null) {
                return null;
            }

            return target;
        }
    }

    /**
     * Sets the "getEnplanements" element
     */
    public void setGetEnplanements(
        aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsDocument.GetEnplanements getEnplanements) {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsDocument.GetEnplanements target =
                null;
            target = (aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsDocument.GetEnplanements) get_store()
                                                                                                         .find_element_user(GETENPLANEMENTS$0,
                    0);

            if (target == null) {
                target = (aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsDocument.GetEnplanements) get_store()
                                                                                                             .add_element_user(GETENPLANEMENTS$0);
            }

            target.set(getEnplanements);
        }
    }

    /**
     * Appends and returns a new empty "getEnplanements" element
     */
    public aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsDocument.GetEnplanements addNewGetEnplanements() {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsDocument.GetEnplanements target =
                null;
            target = (aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsDocument.GetEnplanements) get_store()
                                                                                                         .add_element_user(GETENPLANEMENTS$0);

            return target;
        }
    }

    /**
     * An XML getEnplanements(@http://ws_1_0.serviceprovider.nettracer.aero).
     *
     * This is a complex type.
     */
    public static class GetEnplanementsImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
        implements aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsDocument.GetEnplanements {
        private static final javax.xml.namespace.QName HEADER$0 = new javax.xml.namespace.QName("http://ws_1_0.serviceprovider.nettracer.aero",
                "header");

        public GetEnplanementsImpl(org.apache.xmlbeans.SchemaType sType) {
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
    }
}
