/*
 * An XML document type.
 * Localname: bulkBeornOHDResponse
 * Namespace: http://core.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.core.BulkBeornOHDResponseDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.core;


/**
 * A document containing one bulkBeornOHDResponse(@http://core.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public interface BulkBeornOHDResponseDocument extends org.apache.xmlbeans.XmlObject {
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType) org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(BulkBeornOHDResponseDocument.class.getClassLoader(),
            "schemaorg_apache_xmlbeans.system.sB1D181FCD070B8C28352F412287BB191")
                                                                                                                           .resolveHandle("bulkbeornohdresponsee490doctype");

    /**
     * Gets the "bulkBeornOHDResponse" element
     */
    com.bagnet.nettracer.ws.core.BulkBeornOHDResponseDocument.BulkBeornOHDResponse getBulkBeornOHDResponse();

    /**
     * Sets the "bulkBeornOHDResponse" element
     */
    void setBulkBeornOHDResponse(
        com.bagnet.nettracer.ws.core.BulkBeornOHDResponseDocument.BulkBeornOHDResponse bulkBeornOHDResponse);

    /**
     * Appends and returns a new empty "bulkBeornOHDResponse" element
     */
    com.bagnet.nettracer.ws.core.BulkBeornOHDResponseDocument.BulkBeornOHDResponse addNewBulkBeornOHDResponse();

    /**
     * An XML bulkBeornOHDResponse(@http://core.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public interface BulkBeornOHDResponse extends org.apache.xmlbeans.XmlObject {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType) org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(BulkBeornOHDResponse.class.getClassLoader(),
                "schemaorg_apache_xmlbeans.system.sB1D181FCD070B8C28352F412287BB191")
                                                                                                                               .resolveHandle("bulkbeornohdresponse9d5eelemtype");

        /**
         * Gets array of all "return" elements
         */
        com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse[] getReturnArray();

        /**
         * Gets ith "return" element
         */
        com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse getReturnArray(
            int i);

        /**
         * Tests for nil ith "return" element
         */
        boolean isNilReturnArray(int i);

        /**
         * Returns number of "return" element
         */
        int sizeOfReturnArray();

        /**
         * Sets array of all "return" element
         */
        void setReturnArray(
            com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse[] xreturnArray);

        /**
         * Sets ith "return" element
         */
        void setReturnArray(int i,
            com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse xreturn);

        /**
         * Nils the ith "return" element
         */
        void setNilReturnArray(int i);

        /**
         * Inserts and returns a new empty value (as xml) as the ith "return" element
         */
        com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse insertNewReturn(
            int i);

        /**
         * Appends and returns a new empty value (as xml) as the last "return" element
         */
        com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse addNewReturn();

        /**
         * Removes the ith "return" element
         */
        void removeReturn(int i);

        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        public static final class Factory {
            private Factory() {
            } // No instance of this class allowed

            public static com.bagnet.nettracer.ws.core.BulkBeornOHDResponseDocument.BulkBeornOHDResponse newInstance() {
                return (com.bagnet.nettracer.ws.core.BulkBeornOHDResponseDocument.BulkBeornOHDResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                                    .newInstance(type,
                    null);
            }

            public static com.bagnet.nettracer.ws.core.BulkBeornOHDResponseDocument.BulkBeornOHDResponse newInstance(
                org.apache.xmlbeans.XmlOptions options) {
                return (com.bagnet.nettracer.ws.core.BulkBeornOHDResponseDocument.BulkBeornOHDResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
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

        public static com.bagnet.nettracer.ws.core.BulkBeornOHDResponseDocument newInstance() {
            return (com.bagnet.nettracer.ws.core.BulkBeornOHDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                           .newInstance(type,
                null);
        }

        public static com.bagnet.nettracer.ws.core.BulkBeornOHDResponseDocument newInstance(
            org.apache.xmlbeans.XmlOptions options) {
            return (com.bagnet.nettracer.ws.core.BulkBeornOHDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                           .newInstance(type,
                options);
        }

        /** @param xmlAsString the string value to parse */
        public static com.bagnet.nettracer.ws.core.BulkBeornOHDResponseDocument parse(
            java.lang.String xmlAsString)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.BulkBeornOHDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                           .parse(xmlAsString,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.BulkBeornOHDResponseDocument parse(
            java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.BulkBeornOHDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                           .parse(xmlAsString,
                type, options);
        }

        /** @param file the file from which to load an xml document */
        public static com.bagnet.nettracer.ws.core.BulkBeornOHDResponseDocument parse(
            java.io.File file)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.BulkBeornOHDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                           .parse(file,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.BulkBeornOHDResponseDocument parse(
            java.io.File file, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.BulkBeornOHDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                           .parse(file,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.BulkBeornOHDResponseDocument parse(
            java.net.URL u)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.BulkBeornOHDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                           .parse(u,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.BulkBeornOHDResponseDocument parse(
            java.net.URL u, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.BulkBeornOHDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                           .parse(u,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.BulkBeornOHDResponseDocument parse(
            java.io.InputStream is)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.BulkBeornOHDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                           .parse(is,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.BulkBeornOHDResponseDocument parse(
            java.io.InputStream is, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.BulkBeornOHDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                           .parse(is,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.BulkBeornOHDResponseDocument parse(
            java.io.Reader r)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.BulkBeornOHDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                           .parse(r,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.BulkBeornOHDResponseDocument parse(
            java.io.Reader r, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.BulkBeornOHDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                           .parse(r,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.BulkBeornOHDResponseDocument parse(
            javax.xml.stream.XMLStreamReader sr)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.BulkBeornOHDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                           .parse(sr,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.BulkBeornOHDResponseDocument parse(
            javax.xml.stream.XMLStreamReader sr,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.BulkBeornOHDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                           .parse(sr,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.BulkBeornOHDResponseDocument parse(
            org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.BulkBeornOHDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                           .parse(node,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.BulkBeornOHDResponseDocument parse(
            org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.BulkBeornOHDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                           .parse(node,
                type, options);
        }

        /** @deprecated {@link XMLInputStream} */
        public static com.bagnet.nettracer.ws.core.BulkBeornOHDResponseDocument parse(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (com.bagnet.nettracer.ws.core.BulkBeornOHDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                           .parse(xis,
                type, null);
        }

        /** @deprecated {@link XMLInputStream} */
        public static com.bagnet.nettracer.ws.core.BulkBeornOHDResponseDocument parse(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (com.bagnet.nettracer.ws.core.BulkBeornOHDResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
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
