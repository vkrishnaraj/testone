/*
 * An XML document type.
 * Localname: insertOHD
 * Namespace: http://core.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.core.InsertOHDDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.core;


/**
 * A document containing one insertOHD(@http://core.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public interface InsertOHDDocument extends org.apache.xmlbeans.XmlObject {
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType) org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(InsertOHDDocument.class.getClassLoader(),
            "schemaorg_apache_xmlbeans.system.s2182E89F12DCED7BC68D5ADCA1063463")
                                                                                                                           .resolveHandle("insertohd321adoctype");

    /**
     * Gets the "insertOHD" element
     */
    com.bagnet.nettracer.ws.core.InsertOHDDocument.InsertOHD getInsertOHD();

    /**
     * Sets the "insertOHD" element
     */
    void setInsertOHD(
        com.bagnet.nettracer.ws.core.InsertOHDDocument.InsertOHD insertOHD);

    /**
     * Appends and returns a new empty "insertOHD" element
     */
    com.bagnet.nettracer.ws.core.InsertOHDDocument.InsertOHD addNewInsertOHD();

    /**
     * An XML insertOHD(@http://core.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public interface InsertOHD extends org.apache.xmlbeans.XmlObject {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType) org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(InsertOHD.class.getClassLoader(),
                "schemaorg_apache_xmlbeans.system.s2182E89F12DCED7BC68D5ADCA1063463")
                                                                                                                               .resolveHandle("insertohda8b8elemtype");

        /**
         * Gets the "session_id" element
         */
        java.lang.String getSessionId();

        /**
         * Gets (as xml) the "session_id" element
         */
        org.apache.xmlbeans.XmlString xgetSessionId();

        /**
         * Tests for nil "session_id" element
         */
        boolean isNilSessionId();

        /**
         * True if has "session_id" element
         */
        boolean isSetSessionId();

        /**
         * Sets the "session_id" element
         */
        void setSessionId(java.lang.String sessionId);

        /**
         * Sets (as xml) the "session_id" element
         */
        void xsetSessionId(org.apache.xmlbeans.XmlString sessionId);

        /**
         * Nils the "session_id" element
         */
        void setNilSessionId();

        /**
         * Unsets the "session_id" element
         */
        void unsetSessionId();

        /**
         * Gets the "si" element
         */
        com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD getSi();

        /**
         * Tests for nil "si" element
         */
        boolean isNilSi();

        /**
         * True if has "si" element
         */
        boolean isSetSi();

        /**
         * Sets the "si" element
         */
        void setSi(com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD si);

        /**
         * Appends and returns a new empty "si" element
         */
        com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD addNewSi();

        /**
         * Nils the "si" element
         */
        void setNilSi();

        /**
         * Unsets the "si" element
         */
        void unsetSi();

        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        public static final class Factory {
            private Factory() {
            } // No instance of this class allowed

            public static com.bagnet.nettracer.ws.core.InsertOHDDocument.InsertOHD newInstance() {
                return (com.bagnet.nettracer.ws.core.InsertOHDDocument.InsertOHD) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                              .newInstance(type,
                    null);
            }

            public static com.bagnet.nettracer.ws.core.InsertOHDDocument.InsertOHD newInstance(
                org.apache.xmlbeans.XmlOptions options) {
                return (com.bagnet.nettracer.ws.core.InsertOHDDocument.InsertOHD) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
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

        public static com.bagnet.nettracer.ws.core.InsertOHDDocument newInstance() {
            return (com.bagnet.nettracer.ws.core.InsertOHDDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                .newInstance(type,
                null);
        }

        public static com.bagnet.nettracer.ws.core.InsertOHDDocument newInstance(
            org.apache.xmlbeans.XmlOptions options) {
            return (com.bagnet.nettracer.ws.core.InsertOHDDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                .newInstance(type,
                options);
        }

        /** @param xmlAsString the string value to parse */
        public static com.bagnet.nettracer.ws.core.InsertOHDDocument parse(
            java.lang.String xmlAsString)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.InsertOHDDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                .parse(xmlAsString,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.InsertOHDDocument parse(
            java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.InsertOHDDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                .parse(xmlAsString,
                type, options);
        }

        /** @param file the file from which to load an xml document */
        public static com.bagnet.nettracer.ws.core.InsertOHDDocument parse(
            java.io.File file)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.InsertOHDDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                .parse(file,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.InsertOHDDocument parse(
            java.io.File file, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.InsertOHDDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                .parse(file,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.InsertOHDDocument parse(
            java.net.URL u)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.InsertOHDDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                .parse(u,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.InsertOHDDocument parse(
            java.net.URL u, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.InsertOHDDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                .parse(u,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.InsertOHDDocument parse(
            java.io.InputStream is)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.InsertOHDDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                .parse(is,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.InsertOHDDocument parse(
            java.io.InputStream is, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.InsertOHDDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                .parse(is,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.InsertOHDDocument parse(
            java.io.Reader r)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.InsertOHDDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                .parse(r,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.InsertOHDDocument parse(
            java.io.Reader r, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.InsertOHDDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                .parse(r,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.InsertOHDDocument parse(
            javax.xml.stream.XMLStreamReader sr)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.InsertOHDDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                .parse(sr,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.InsertOHDDocument parse(
            javax.xml.stream.XMLStreamReader sr,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.InsertOHDDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                .parse(sr,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.InsertOHDDocument parse(
            org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.InsertOHDDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                .parse(node,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.InsertOHDDocument parse(
            org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.InsertOHDDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                .parse(node,
                type, options);
        }

        /** @deprecated {@link XMLInputStream} */
        public static com.bagnet.nettracer.ws.core.InsertOHDDocument parse(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (com.bagnet.nettracer.ws.core.InsertOHDDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                .parse(xis,
                type, null);
        }

        /** @deprecated {@link XMLInputStream} */
        public static com.bagnet.nettracer.ws.core.InsertOHDDocument parse(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (com.bagnet.nettracer.ws.core.InsertOHDDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
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
