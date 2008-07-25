/*
 * An XML document type.
 * Localname: logoffResponse
 * Namespace: http://core.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.core.LogoffResponseDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.core;


/**
 * A document containing one logoffResponse(@http://core.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public interface LogoffResponseDocument extends org.apache.xmlbeans.XmlObject {
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType) org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(LogoffResponseDocument.class.getClassLoader(),
            "schemaorg_apache_xmlbeans.system.s2182E89F12DCED7BC68D5ADCA1063463")
                                                                                                                           .resolveHandle("logoffresponsee606doctype");

    /**
     * Gets the "logoffResponse" element
     */
    com.bagnet.nettracer.ws.core.LogoffResponseDocument.LogoffResponse getLogoffResponse();

    /**
     * Sets the "logoffResponse" element
     */
    void setLogoffResponse(
        com.bagnet.nettracer.ws.core.LogoffResponseDocument.LogoffResponse logoffResponse);

    /**
     * Appends and returns a new empty "logoffResponse" element
     */
    com.bagnet.nettracer.ws.core.LogoffResponseDocument.LogoffResponse addNewLogoffResponse();

    /**
     * An XML logoffResponse(@http://core.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public interface LogoffResponse extends org.apache.xmlbeans.XmlObject {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType) org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(LogoffResponse.class.getClassLoader(),
                "schemaorg_apache_xmlbeans.system.s2182E89F12DCED7BC68D5ADCA1063463")
                                                                                                                               .resolveHandle("logoffresponse8f5eelemtype");

        /**
         * Gets the "return" element
         */
        boolean getReturn();

        /**
         * Gets (as xml) the "return" element
         */
        org.apache.xmlbeans.XmlBoolean xgetReturn();

        /**
         * True if has "return" element
         */
        boolean isSetReturn();

        /**
         * Sets the "return" element
         */
        void setReturn(boolean xreturn);

        /**
         * Sets (as xml) the "return" element
         */
        void xsetReturn(org.apache.xmlbeans.XmlBoolean xreturn);

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

            public static com.bagnet.nettracer.ws.core.LogoffResponseDocument.LogoffResponse newInstance() {
                return (com.bagnet.nettracer.ws.core.LogoffResponseDocument.LogoffResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                        .newInstance(type,
                    null);
            }

            public static com.bagnet.nettracer.ws.core.LogoffResponseDocument.LogoffResponse newInstance(
                org.apache.xmlbeans.XmlOptions options) {
                return (com.bagnet.nettracer.ws.core.LogoffResponseDocument.LogoffResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
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

        public static com.bagnet.nettracer.ws.core.LogoffResponseDocument newInstance() {
            return (com.bagnet.nettracer.ws.core.LogoffResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                     .newInstance(type,
                null);
        }

        public static com.bagnet.nettracer.ws.core.LogoffResponseDocument newInstance(
            org.apache.xmlbeans.XmlOptions options) {
            return (com.bagnet.nettracer.ws.core.LogoffResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                     .newInstance(type,
                options);
        }

        /** @param xmlAsString the string value to parse */
        public static com.bagnet.nettracer.ws.core.LogoffResponseDocument parse(
            java.lang.String xmlAsString)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.LogoffResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                     .parse(xmlAsString,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.LogoffResponseDocument parse(
            java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.LogoffResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                     .parse(xmlAsString,
                type, options);
        }

        /** @param file the file from which to load an xml document */
        public static com.bagnet.nettracer.ws.core.LogoffResponseDocument parse(
            java.io.File file)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.LogoffResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                     .parse(file,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.LogoffResponseDocument parse(
            java.io.File file, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.LogoffResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                     .parse(file,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.LogoffResponseDocument parse(
            java.net.URL u)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.LogoffResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                     .parse(u,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.LogoffResponseDocument parse(
            java.net.URL u, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.LogoffResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                     .parse(u,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.LogoffResponseDocument parse(
            java.io.InputStream is)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.LogoffResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                     .parse(is,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.LogoffResponseDocument parse(
            java.io.InputStream is, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.LogoffResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                     .parse(is,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.LogoffResponseDocument parse(
            java.io.Reader r)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.LogoffResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                     .parse(r,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.LogoffResponseDocument parse(
            java.io.Reader r, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.LogoffResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                     .parse(r,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.LogoffResponseDocument parse(
            javax.xml.stream.XMLStreamReader sr)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.LogoffResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                     .parse(sr,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.LogoffResponseDocument parse(
            javax.xml.stream.XMLStreamReader sr,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.LogoffResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                     .parse(sr,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.LogoffResponseDocument parse(
            org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.LogoffResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                     .parse(node,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.LogoffResponseDocument parse(
            org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.LogoffResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                     .parse(node,
                type, options);
        }

        /** @deprecated {@link XMLInputStream} */
        public static com.bagnet.nettracer.ws.core.LogoffResponseDocument parse(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (com.bagnet.nettracer.ws.core.LogoffResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                     .parse(xis,
                type, null);
        }

        /** @deprecated {@link XMLInputStream} */
        public static com.bagnet.nettracer.ws.core.LogoffResponseDocument parse(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (com.bagnet.nettracer.ws.core.LogoffResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
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
