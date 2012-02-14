/*
 * An XML document type.
 * Localname: bulkBeornOHD
 * Namespace: http://core.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.core.BulkBeornOHDDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.core;


/**
 * A document containing one bulkBeornOHD(@http://core.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public interface BulkBeornOHDDocument extends org.apache.xmlbeans.XmlObject {
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType) org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(BulkBeornOHDDocument.class.getClassLoader(),
            "schemaorg_apache_xmlbeans.system.sB1D181FCD070B8C28352F412287BB191")
                                                                                                                           .resolveHandle("bulkbeornohd16afdoctype");

    /**
     * Gets the "bulkBeornOHD" element
     */
    com.bagnet.nettracer.ws.core.BulkBeornOHDDocument.BulkBeornOHD getBulkBeornOHD();

    /**
     * Sets the "bulkBeornOHD" element
     */
    void setBulkBeornOHD(
        com.bagnet.nettracer.ws.core.BulkBeornOHDDocument.BulkBeornOHD bulkBeornOHD);

    /**
     * Appends and returns a new empty "bulkBeornOHD" element
     */
    com.bagnet.nettracer.ws.core.BulkBeornOHDDocument.BulkBeornOHD addNewBulkBeornOHD();

    /**
     * An XML bulkBeornOHD(@http://core.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public interface BulkBeornOHD extends org.apache.xmlbeans.XmlObject {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType) org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(BulkBeornOHD.class.getClassLoader(),
                "schemaorg_apache_xmlbeans.system.sB1D181FCD070B8C28352F412287BB191")
                                                                                                                               .resolveHandle("bulkbeornohd7a7eelemtype");

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
         * Gets array of all "si" elements
         */
        com.bagnet.nettracer.ws.core.pojo.xsd.WSBEORN[] getSiArray();

        /**
         * Gets ith "si" element
         */
        com.bagnet.nettracer.ws.core.pojo.xsd.WSBEORN getSiArray(int i);

        /**
         * Tests for nil ith "si" element
         */
        boolean isNilSiArray(int i);

        /**
         * Returns number of "si" element
         */
        int sizeOfSiArray();

        /**
         * Sets array of all "si" element
         */
        void setSiArray(com.bagnet.nettracer.ws.core.pojo.xsd.WSBEORN[] siArray);

        /**
         * Sets ith "si" element
         */
        void setSiArray(int i, com.bagnet.nettracer.ws.core.pojo.xsd.WSBEORN si);

        /**
         * Nils the ith "si" element
         */
        void setNilSiArray(int i);

        /**
         * Inserts and returns a new empty value (as xml) as the ith "si" element
         */
        com.bagnet.nettracer.ws.core.pojo.xsd.WSBEORN insertNewSi(int i);

        /**
         * Appends and returns a new empty value (as xml) as the last "si" element
         */
        com.bagnet.nettracer.ws.core.pojo.xsd.WSBEORN addNewSi();

        /**
         * Removes the ith "si" element
         */
        void removeSi(int i);

        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        public static final class Factory {
            private Factory() {
            } // No instance of this class allowed

            public static com.bagnet.nettracer.ws.core.BulkBeornOHDDocument.BulkBeornOHD newInstance() {
                return (com.bagnet.nettracer.ws.core.BulkBeornOHDDocument.BulkBeornOHD) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                    .newInstance(type,
                    null);
            }

            public static com.bagnet.nettracer.ws.core.BulkBeornOHDDocument.BulkBeornOHD newInstance(
                org.apache.xmlbeans.XmlOptions options) {
                return (com.bagnet.nettracer.ws.core.BulkBeornOHDDocument.BulkBeornOHD) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
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

        public static com.bagnet.nettracer.ws.core.BulkBeornOHDDocument newInstance() {
            return (com.bagnet.nettracer.ws.core.BulkBeornOHDDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                   .newInstance(type,
                null);
        }

        public static com.bagnet.nettracer.ws.core.BulkBeornOHDDocument newInstance(
            org.apache.xmlbeans.XmlOptions options) {
            return (com.bagnet.nettracer.ws.core.BulkBeornOHDDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                   .newInstance(type,
                options);
        }

        /** @param xmlAsString the string value to parse */
        public static com.bagnet.nettracer.ws.core.BulkBeornOHDDocument parse(
            java.lang.String xmlAsString)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.BulkBeornOHDDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                   .parse(xmlAsString,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.BulkBeornOHDDocument parse(
            java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.BulkBeornOHDDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                   .parse(xmlAsString,
                type, options);
        }

        /** @param file the file from which to load an xml document */
        public static com.bagnet.nettracer.ws.core.BulkBeornOHDDocument parse(
            java.io.File file)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.BulkBeornOHDDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                   .parse(file,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.BulkBeornOHDDocument parse(
            java.io.File file, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.BulkBeornOHDDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                   .parse(file,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.BulkBeornOHDDocument parse(
            java.net.URL u)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.BulkBeornOHDDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                   .parse(u,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.BulkBeornOHDDocument parse(
            java.net.URL u, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.BulkBeornOHDDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                   .parse(u,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.BulkBeornOHDDocument parse(
            java.io.InputStream is)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.BulkBeornOHDDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                   .parse(is,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.BulkBeornOHDDocument parse(
            java.io.InputStream is, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.BulkBeornOHDDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                   .parse(is,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.BulkBeornOHDDocument parse(
            java.io.Reader r)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.BulkBeornOHDDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                   .parse(r,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.BulkBeornOHDDocument parse(
            java.io.Reader r, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.BulkBeornOHDDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                   .parse(r,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.BulkBeornOHDDocument parse(
            javax.xml.stream.XMLStreamReader sr)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.BulkBeornOHDDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                   .parse(sr,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.BulkBeornOHDDocument parse(
            javax.xml.stream.XMLStreamReader sr,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.BulkBeornOHDDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                   .parse(sr,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.BulkBeornOHDDocument parse(
            org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.BulkBeornOHDDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                   .parse(node,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.BulkBeornOHDDocument parse(
            org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.BulkBeornOHDDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                   .parse(node,
                type, options);
        }

        /** @deprecated {@link XMLInputStream} */
        public static com.bagnet.nettracer.ws.core.BulkBeornOHDDocument parse(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (com.bagnet.nettracer.ws.core.BulkBeornOHDDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                   .parse(xis,
                type, null);
        }

        /** @deprecated {@link XMLInputStream} */
        public static com.bagnet.nettracer.ws.core.BulkBeornOHDDocument parse(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (com.bagnet.nettracer.ws.core.BulkBeornOHDDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
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
