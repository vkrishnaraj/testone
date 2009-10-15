/*
 * An XML document type.
 * Localname: getAhlResponse
 * Namespace: http://wt_1_0.serviceprovider.nettracer.aero
 * Java type: aero.nettracer.serviceprovider.wt_1_0.GetAhlResponseDocument
 *
 * Automatically generated - do not modify.
 */
package aero.nettracer.serviceprovider.wt_1_0;


/**
 * A document containing one getAhlResponse(@http://wt_1_0.serviceprovider.nettracer.aero) element.
 *
 * This is a complex type.
 */
public interface GetAhlResponseDocument extends org.apache.xmlbeans.XmlObject {
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType) org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(GetAhlResponseDocument.class.getClassLoader(),
            "schemaorg_apache_xmlbeans.system.sBCED675595897F865A233256B59F19BA")
                                                                                                                           .resolveHandle("getahlresponsee4e0doctype");

    /**
     * Gets the "getAhlResponse" element
     */
    aero.nettracer.serviceprovider.wt_1_0.GetAhlResponseDocument.GetAhlResponse getGetAhlResponse();

    /**
     * Sets the "getAhlResponse" element
     */
    void setGetAhlResponse(
        aero.nettracer.serviceprovider.wt_1_0.GetAhlResponseDocument.GetAhlResponse getAhlResponse);

    /**
     * Appends and returns a new empty "getAhlResponse" element
     */
    aero.nettracer.serviceprovider.wt_1_0.GetAhlResponseDocument.GetAhlResponse addNewGetAhlResponse();

    /**
     * An XML getAhlResponse(@http://wt_1_0.serviceprovider.nettracer.aero).
     *
     * This is a complex type.
     */
    public interface GetAhlResponse extends org.apache.xmlbeans.XmlObject {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType) org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(GetAhlResponse.class.getClassLoader(),
                "schemaorg_apache_xmlbeans.system.sBCED675595897F865A233256B59F19BA")
                                                                                                                               .resolveHandle("getahlresponse71ccelemtype");

        /**
         * Gets the "return" element
         */
        aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse getReturn();

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
            aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse xreturn);

        /**
         * Appends and returns a new empty "return" element
         */
        aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse addNewReturn();

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

            public static aero.nettracer.serviceprovider.wt_1_0.GetAhlResponseDocument.GetAhlResponse newInstance() {
                return (aero.nettracer.serviceprovider.wt_1_0.GetAhlResponseDocument.GetAhlResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                                 .newInstance(type,
                    null);
            }

            public static aero.nettracer.serviceprovider.wt_1_0.GetAhlResponseDocument.GetAhlResponse newInstance(
                org.apache.xmlbeans.XmlOptions options) {
                return (aero.nettracer.serviceprovider.wt_1_0.GetAhlResponseDocument.GetAhlResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
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

        public static aero.nettracer.serviceprovider.wt_1_0.GetAhlResponseDocument newInstance() {
            return (aero.nettracer.serviceprovider.wt_1_0.GetAhlResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                              .newInstance(type,
                null);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.GetAhlResponseDocument newInstance(
            org.apache.xmlbeans.XmlOptions options) {
            return (aero.nettracer.serviceprovider.wt_1_0.GetAhlResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                              .newInstance(type,
                options);
        }

        /** @param xmlAsString the string value to parse */
        public static aero.nettracer.serviceprovider.wt_1_0.GetAhlResponseDocument parse(
            java.lang.String xmlAsString)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.wt_1_0.GetAhlResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                              .parse(xmlAsString,
                type, null);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.GetAhlResponseDocument parse(
            java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.wt_1_0.GetAhlResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                              .parse(xmlAsString,
                type, options);
        }

        /** @param file the file from which to load an xml document */
        public static aero.nettracer.serviceprovider.wt_1_0.GetAhlResponseDocument parse(
            java.io.File file)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.wt_1_0.GetAhlResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                              .parse(file,
                type, null);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.GetAhlResponseDocument parse(
            java.io.File file, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.wt_1_0.GetAhlResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                              .parse(file,
                type, options);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.GetAhlResponseDocument parse(
            java.net.URL u)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.wt_1_0.GetAhlResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                              .parse(u,
                type, null);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.GetAhlResponseDocument parse(
            java.net.URL u, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.wt_1_0.GetAhlResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                              .parse(u,
                type, options);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.GetAhlResponseDocument parse(
            java.io.InputStream is)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.wt_1_0.GetAhlResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                              .parse(is,
                type, null);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.GetAhlResponseDocument parse(
            java.io.InputStream is, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.wt_1_0.GetAhlResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                              .parse(is,
                type, options);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.GetAhlResponseDocument parse(
            java.io.Reader r)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.wt_1_0.GetAhlResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                              .parse(r,
                type, null);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.GetAhlResponseDocument parse(
            java.io.Reader r, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.wt_1_0.GetAhlResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                              .parse(r,
                type, options);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.GetAhlResponseDocument parse(
            javax.xml.stream.XMLStreamReader sr)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.wt_1_0.GetAhlResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                              .parse(sr,
                type, null);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.GetAhlResponseDocument parse(
            javax.xml.stream.XMLStreamReader sr,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.wt_1_0.GetAhlResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                              .parse(sr,
                type, options);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.GetAhlResponseDocument parse(
            org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.wt_1_0.GetAhlResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                              .parse(node,
                type, null);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.GetAhlResponseDocument parse(
            org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.wt_1_0.GetAhlResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                              .parse(node,
                type, options);
        }

        /** @deprecated {@link XMLInputStream} */
        public static aero.nettracer.serviceprovider.wt_1_0.GetAhlResponseDocument parse(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (aero.nettracer.serviceprovider.wt_1_0.GetAhlResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                              .parse(xis,
                type, null);
        }

        /** @deprecated {@link XMLInputStream} */
        public static aero.nettracer.serviceprovider.wt_1_0.GetAhlResponseDocument parse(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (aero.nettracer.serviceprovider.wt_1_0.GetAhlResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
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
