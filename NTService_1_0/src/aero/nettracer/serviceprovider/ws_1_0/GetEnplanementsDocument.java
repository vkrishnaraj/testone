/*
 * An XML document type.
 * Localname: getEnplanements
 * Namespace: http://ws_1_0.serviceprovider.nettracer.aero
 * Java type: aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsDocument
 *
 * Automatically generated - do not modify.
 */
package aero.nettracer.serviceprovider.ws_1_0;


/**
 * A document containing one getEnplanements(@http://ws_1_0.serviceprovider.nettracer.aero) element.
 *
 * This is a complex type.
 */
public interface GetEnplanementsDocument extends org.apache.xmlbeans.XmlObject {
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType) org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(GetEnplanementsDocument.class.getClassLoader(),
            "schemaorg_apache_xmlbeans.system.s3ABAB084C02932FDB9E0FCEE6AB72CA7")
                                                                                                                           .resolveHandle("getenplanementsc40bdoctype");

    /**
     * Gets the "getEnplanements" element
     */
    aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsDocument.GetEnplanements getGetEnplanements();

    /**
     * Sets the "getEnplanements" element
     */
    void setGetEnplanements(
        aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsDocument.GetEnplanements getEnplanements);

    /**
     * Appends and returns a new empty "getEnplanements" element
     */
    aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsDocument.GetEnplanements addNewGetEnplanements();

    /**
     * An XML getEnplanements(@http://ws_1_0.serviceprovider.nettracer.aero).
     *
     * This is a complex type.
     */
    public interface GetEnplanements extends org.apache.xmlbeans.XmlObject {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType) org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(GetEnplanements.class.getClassLoader(),
                "schemaorg_apache_xmlbeans.system.s3ABAB084C02932FDB9E0FCEE6AB72CA7")
                                                                                                                               .resolveHandle("getenplanements6147elemtype");

        /**
         * Gets the "header" element
         */
        aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader getHeader();

        /**
         * Tests for nil "header" element
         */
        boolean isNilHeader();

        /**
         * True if has "header" element
         */
        boolean isSetHeader();

        /**
         * Sets the "header" element
         */
        void setHeader(
            aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader header);

        /**
         * Appends and returns a new empty "header" element
         */
        aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader addNewHeader();

        /**
         * Nils the "header" element
         */
        void setNilHeader();

        /**
         * Unsets the "header" element
         */
        void unsetHeader();

        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        public static final class Factory {
            private Factory() {
            } // No instance of this class allowed

            public static aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsDocument.GetEnplanements newInstance() {
                return (aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsDocument.GetEnplanements) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                                   .newInstance(type,
                    null);
            }

            public static aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsDocument.GetEnplanements newInstance(
                org.apache.xmlbeans.XmlOptions options) {
                return (aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsDocument.GetEnplanements) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                                   .newInstance(type,
                    options);
            }
        }
    }

    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    public static final class Factory {
        private Factory() {
        } // No instance of this class allowed

        public static aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsDocument newInstance() {
            return (aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                               .newInstance(type,
                null);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsDocument newInstance(
            org.apache.xmlbeans.XmlOptions options) {
            return (aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                               .newInstance(type,
                options);
        }

        /** @param xmlAsString the string value to parse */
        public static aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsDocument parse(
            java.lang.String xmlAsString)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                               .parse(xmlAsString,
                type, null);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsDocument parse(
            java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                               .parse(xmlAsString,
                type, options);
        }

        /** @param file the file from which to load an xml document */
        public static aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsDocument parse(
            java.io.File file)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                               .parse(file,
                type, null);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsDocument parse(
            java.io.File file, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                               .parse(file,
                type, options);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsDocument parse(
            java.net.URL u)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                               .parse(u,
                type, null);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsDocument parse(
            java.net.URL u, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                               .parse(u,
                type, options);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsDocument parse(
            java.io.InputStream is)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                               .parse(is,
                type, null);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsDocument parse(
            java.io.InputStream is, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                               .parse(is,
                type, options);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsDocument parse(
            java.io.Reader r)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                               .parse(r,
                type, null);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsDocument parse(
            java.io.Reader r, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                               .parse(r,
                type, options);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsDocument parse(
            javax.xml.stream.XMLStreamReader sr)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                               .parse(sr,
                type, null);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsDocument parse(
            javax.xml.stream.XMLStreamReader sr,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                               .parse(sr,
                type, options);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsDocument parse(
            org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                               .parse(node,
                type, null);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsDocument parse(
            org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                               .parse(node,
                type, options);
        }

        /** @deprecated {@link XMLInputStream} */
        public static aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsDocument parse(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                               .parse(xis,
                type, null);
        }

        /** @deprecated {@link XMLInputStream} */
        public static aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsDocument parse(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                               .parse(xis,
                type, options);
        }

        /** @deprecated {@link XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                               .newValidatingXMLInputStream(xis,
                type, null);
        }

        /** @deprecated {@link XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                               .newValidatingXMLInputStream(xis,
                type, options);
        }
    }
}
