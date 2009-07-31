/*
 * An XML document type.
 * Localname: getOsiContentsResponse
 * Namespace: http://ws_1_0.serviceprovider.nettracer.aero
 * Java type: aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsResponseDocument
 *
 * Automatically generated - do not modify.
 */
package aero.nettracer.serviceprovider.ws_1_0;


/**
 * A document containing one getOsiContentsResponse(@http://ws_1_0.serviceprovider.nettracer.aero) element.
 *
 * This is a complex type.
 */
public interface GetOsiContentsResponseDocument extends org.apache.xmlbeans.XmlObject {
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType) org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(GetOsiContentsResponseDocument.class.getClassLoader(),
            "schemaorg_apache_xmlbeans.system.s3ABAB084C02932FDB9E0FCEE6AB72CA7")
                                                                                                                           .resolveHandle("getosicontentsresponsebba7doctype");

    /**
     * Gets the "getOsiContentsResponse" element
     */
    aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsResponseDocument.GetOsiContentsResponse getGetOsiContentsResponse();

    /**
     * Sets the "getOsiContentsResponse" element
     */
    void setGetOsiContentsResponse(
        aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsResponseDocument.GetOsiContentsResponse getOsiContentsResponse);

    /**
     * Appends and returns a new empty "getOsiContentsResponse" element
     */
    aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsResponseDocument.GetOsiContentsResponse addNewGetOsiContentsResponse();

    /**
     * An XML getOsiContentsResponse(@http://ws_1_0.serviceprovider.nettracer.aero).
     *
     * This is a complex type.
     */
    public interface GetOsiContentsResponse extends org.apache.xmlbeans.XmlObject {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType) org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(GetOsiContentsResponse.class.getClassLoader(),
                "schemaorg_apache_xmlbeans.system.s3ABAB084C02932FDB9E0FCEE6AB72CA7")
                                                                                                                               .resolveHandle("getosicontentsresponseb52delemtype");

        /**
         * Gets the "return" element
         */
        aero.nettracer.serviceprovider.ws_1_0.response.xsd.OsiResponse getReturn();

        /**
         * Tests for nil "return" element
         */
        boolean isNilReturn();

        /**
         * True if has "return" element
         */
        boolean isSetReturn();

        /**
         * Sets the "return" element
         */
        void setReturn(
            aero.nettracer.serviceprovider.ws_1_0.response.xsd.OsiResponse xreturn);

        /**
         * Appends and returns a new empty "return" element
         */
        aero.nettracer.serviceprovider.ws_1_0.response.xsd.OsiResponse addNewReturn();

        /**
         * Nils the "return" element
         */
        void setNilReturn();

        /**
         * Unsets the "return" element
         */
        void unsetReturn();

        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        public static final class Factory {
            private Factory() {
            } // No instance of this class allowed

            public static aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsResponseDocument.GetOsiContentsResponse newInstance() {
                return (aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsResponseDocument.GetOsiContentsResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                                                 .newInstance(type,
                    null);
            }

            public static aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsResponseDocument.GetOsiContentsResponse newInstance(
                org.apache.xmlbeans.XmlOptions options) {
                return (aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsResponseDocument.GetOsiContentsResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
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

        public static aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsResponseDocument newInstance() {
            return (aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                      .newInstance(type,
                null);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsResponseDocument newInstance(
            org.apache.xmlbeans.XmlOptions options) {
            return (aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                      .newInstance(type,
                options);
        }

        /** @param xmlAsString the string value to parse */
        public static aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsResponseDocument parse(
            java.lang.String xmlAsString)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                      .parse(xmlAsString,
                type, null);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsResponseDocument parse(
            java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                      .parse(xmlAsString,
                type, options);
        }

        /** @param file the file from which to load an xml document */
        public static aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsResponseDocument parse(
            java.io.File file)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                      .parse(file,
                type, null);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsResponseDocument parse(
            java.io.File file, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                      .parse(file,
                type, options);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsResponseDocument parse(
            java.net.URL u)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                      .parse(u,
                type, null);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsResponseDocument parse(
            java.net.URL u, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                      .parse(u,
                type, options);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsResponseDocument parse(
            java.io.InputStream is)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                      .parse(is,
                type, null);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsResponseDocument parse(
            java.io.InputStream is, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                      .parse(is,
                type, options);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsResponseDocument parse(
            java.io.Reader r)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                      .parse(r,
                type, null);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsResponseDocument parse(
            java.io.Reader r, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                      .parse(r,
                type, options);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsResponseDocument parse(
            javax.xml.stream.XMLStreamReader sr)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                      .parse(sr,
                type, null);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsResponseDocument parse(
            javax.xml.stream.XMLStreamReader sr,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                      .parse(sr,
                type, options);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsResponseDocument parse(
            org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                      .parse(node,
                type, null);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsResponseDocument parse(
            org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                      .parse(node,
                type, options);
        }

        /** @deprecated {@link XMLInputStream} */
        public static aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsResponseDocument parse(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                      .parse(xis,
                type, null);
        }

        /** @deprecated {@link XMLInputStream} */
        public static aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsResponseDocument parse(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
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
