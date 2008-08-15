/*
 * An XML document type.
 * Localname: beornOHDResponse
 * Namespace: http://core.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.core.BeornOHDResponseDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.core;


/**
 * A document containing one beornOHDResponse(@http://core.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public interface BeornOHDResponseDocument extends org.apache.xmlbeans.XmlObject {
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType) org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(BeornOHDResponseDocument.class.getClassLoader(),
            "schemaorg_apache_xmlbeans.system.s16B510B5563ED0857509A4DB5C2996DF")
                                                                                                                           .resolveHandle("beornohdresponse88dedoctype");

    /**
     * Gets the "beornOHDResponse" element
     */
    com.bagnet.nettracer.ws.core.BeornOHDResponseDocument.BeornOHDResponse getBeornOHDResponse();

    /**
     * Sets the "beornOHDResponse" element
     */
    void setBeornOHDResponse(
        com.bagnet.nettracer.ws.core.BeornOHDResponseDocument.BeornOHDResponse beornOHDResponse);

    /**
     * Appends and returns a new empty "beornOHDResponse" element
     */
    com.bagnet.nettracer.ws.core.BeornOHDResponseDocument.BeornOHDResponse addNewBeornOHDResponse();

    /**
     * An XML beornOHDResponse(@http://core.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public interface BeornOHDResponse extends org.apache.xmlbeans.XmlObject {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType) org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(BeornOHDResponse.class.getClassLoader(),
                "schemaorg_apache_xmlbeans.system.s16B510B5563ED0857509A4DB5C2996DF")
                                                                                                                               .resolveHandle("beornohdresponse6e1eelemtype");

        /**
         * Gets the "return" element
         */
        com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse getReturn();

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
            com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse xreturn);

        /**
         * Appends and returns a new empty "return" element
         */
        com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse addNewReturn();

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

            public static com.bagnet.nettracer.ws.core.BeornOHDResponseDocument.BeornOHDResponse newInstance() {
                return (com.bagnet.nettracer.ws.core.BeornOHDResponseDocument.BeornOHDResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                            .newInstance(type,
                    null);
            }

            public static com.bagnet.nettracer.ws.core.BeornOHDResponseDocument.BeornOHDResponse newInstance(
                org.apache.xmlbeans.XmlOptions options) {
                return (com.bagnet.nettracer.ws.core.BeornOHDResponseDocument.BeornOHDResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
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

        public static com.bagnet.nettracer.ws.core.BeornOHDResponseDocument newInstance() {
            return (com.bagnet.nettracer.ws.core.BeornOHDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                       .newInstance(type,
                null);
        }

        public static com.bagnet.nettracer.ws.core.BeornOHDResponseDocument newInstance(
            org.apache.xmlbeans.XmlOptions options) {
            return (com.bagnet.nettracer.ws.core.BeornOHDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                       .newInstance(type,
                options);
        }

        /** @param xmlAsString the string value to parse */
        public static com.bagnet.nettracer.ws.core.BeornOHDResponseDocument parse(
            java.lang.String xmlAsString)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.BeornOHDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                       .parse(xmlAsString,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.BeornOHDResponseDocument parse(
            java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.BeornOHDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                       .parse(xmlAsString,
                type, options);
        }

        /** @param file the file from which to load an xml document */
        public static com.bagnet.nettracer.ws.core.BeornOHDResponseDocument parse(
            java.io.File file)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.BeornOHDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                       .parse(file,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.BeornOHDResponseDocument parse(
            java.io.File file, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.BeornOHDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                       .parse(file,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.BeornOHDResponseDocument parse(
            java.net.URL u)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.BeornOHDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                       .parse(u,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.BeornOHDResponseDocument parse(
            java.net.URL u, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.BeornOHDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                       .parse(u,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.BeornOHDResponseDocument parse(
            java.io.InputStream is)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.BeornOHDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                       .parse(is,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.BeornOHDResponseDocument parse(
            java.io.InputStream is, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.BeornOHDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                       .parse(is,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.BeornOHDResponseDocument parse(
            java.io.Reader r)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.BeornOHDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                       .parse(r,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.BeornOHDResponseDocument parse(
            java.io.Reader r, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.BeornOHDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                       .parse(r,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.BeornOHDResponseDocument parse(
            javax.xml.stream.XMLStreamReader sr)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.BeornOHDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                       .parse(sr,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.BeornOHDResponseDocument parse(
            javax.xml.stream.XMLStreamReader sr,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.BeornOHDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                       .parse(sr,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.BeornOHDResponseDocument parse(
            org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.BeornOHDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                       .parse(node,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.BeornOHDResponseDocument parse(
            org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.BeornOHDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                       .parse(node,
                type, options);
        }

        /** @deprecated {@link XMLInputStream} */
        public static com.bagnet.nettracer.ws.core.BeornOHDResponseDocument parse(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (com.bagnet.nettracer.ws.core.BeornOHDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                       .parse(xis,
                type, null);
        }

        /** @deprecated {@link XMLInputStream} */
        public static com.bagnet.nettracer.ws.core.BeornOHDResponseDocument parse(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (com.bagnet.nettracer.ws.core.BeornOHDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
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
