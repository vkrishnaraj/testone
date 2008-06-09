/*
 * An XML document type.
 * Localname: getOHDsForWTResponse
 * Namespace: http://core.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.core.GetOHDsForWTResponseDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.core;

import org.apache.xmlbeans.xml.stream.XMLInputStream;


/**
 * A document containing one getOHDsForWTResponse(@http://core.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public interface GetOHDsForWTResponseDocument extends org.apache.xmlbeans.XmlObject {
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType) org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(GetOHDsForWTResponseDocument.class.getClassLoader(),
            "schemaorg_apache_xmlbeans.system.s51D0044A072EEB5AF0F7D86C8EBC33B8")
                                                                                                                           .resolveHandle("getohdsforwtresponsef383doctype");

    /**
     * Gets the "getOHDsForWTResponse" element
     */
    com.bagnet.nettracer.ws.core.GetOHDsForWTResponseDocument.GetOHDsForWTResponse getGetOHDsForWTResponse();

    /**
     * Sets the "getOHDsForWTResponse" element
     */
    void setGetOHDsForWTResponse(
        com.bagnet.nettracer.ws.core.GetOHDsForWTResponseDocument.GetOHDsForWTResponse getOHDsForWTResponse);

    /**
     * Appends and returns a new empty "getOHDsForWTResponse" element
     */
    com.bagnet.nettracer.ws.core.GetOHDsForWTResponseDocument.GetOHDsForWTResponse addNewGetOHDsForWTResponse();

    /**
     * An XML getOHDsForWTResponse(@http://core.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public interface GetOHDsForWTResponse extends org.apache.xmlbeans.XmlObject {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType) org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(GetOHDsForWTResponse.class.getClassLoader(),
                "schemaorg_apache_xmlbeans.system.s51D0044A072EEB5AF0F7D86C8EBC33B8")
                                                                                                                               .resolveHandle("getohdsforwtresponse7bfeelemtype");

        /**
         * Gets array of all "return" elements
         */
        com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD[] getReturnArray();

        /**
         * Gets ith "return" element
         */
        com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD getReturnArray(int i);

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
            com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD[] xreturnArray);

        /**
         * Sets ith "return" element
         */
        void setReturnArray(int i,
            com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD xreturn);

        /**
         * Nils the ith "return" element
         */
        void setNilReturnArray(int i);

        /**
         * Inserts and returns a new empty value (as xml) as the ith "return" element
         */
        com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD insertNewReturn(int i);

        /**
         * Appends and returns a new empty value (as xml) as the last "return" element
         */
        com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD addNewReturn();

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

            public static com.bagnet.nettracer.ws.core.GetOHDsForWTResponseDocument.GetOHDsForWTResponse newInstance() {
                return (com.bagnet.nettracer.ws.core.GetOHDsForWTResponseDocument.GetOHDsForWTResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                                    .newInstance(type,
                    null);
            }

            public static com.bagnet.nettracer.ws.core.GetOHDsForWTResponseDocument.GetOHDsForWTResponse newInstance(
                org.apache.xmlbeans.XmlOptions options) {
                return (com.bagnet.nettracer.ws.core.GetOHDsForWTResponseDocument.GetOHDsForWTResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
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

        public static com.bagnet.nettracer.ws.core.GetOHDsForWTResponseDocument newInstance() {
            return (com.bagnet.nettracer.ws.core.GetOHDsForWTResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                           .newInstance(type,
                null);
        }

        public static com.bagnet.nettracer.ws.core.GetOHDsForWTResponseDocument newInstance(
            org.apache.xmlbeans.XmlOptions options) {
            return (com.bagnet.nettracer.ws.core.GetOHDsForWTResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                           .newInstance(type,
                options);
        }

        /** @param xmlAsString the string value to parse */
        public static com.bagnet.nettracer.ws.core.GetOHDsForWTResponseDocument parse(
            java.lang.String xmlAsString)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.GetOHDsForWTResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                           .parse(xmlAsString,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.GetOHDsForWTResponseDocument parse(
            java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.GetOHDsForWTResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                           .parse(xmlAsString,
                type, options);
        }

        /** @param file the file from which to load an xml document */
        public static com.bagnet.nettracer.ws.core.GetOHDsForWTResponseDocument parse(
            java.io.File file)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.GetOHDsForWTResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                           .parse(file,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.GetOHDsForWTResponseDocument parse(
            java.io.File file, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.GetOHDsForWTResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                           .parse(file,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.GetOHDsForWTResponseDocument parse(
            java.net.URL u)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.GetOHDsForWTResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                           .parse(u,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.GetOHDsForWTResponseDocument parse(
            java.net.URL u, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.GetOHDsForWTResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                           .parse(u,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.GetOHDsForWTResponseDocument parse(
            java.io.InputStream is)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.GetOHDsForWTResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                           .parse(is,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.GetOHDsForWTResponseDocument parse(
            java.io.InputStream is, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.GetOHDsForWTResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                           .parse(is,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.GetOHDsForWTResponseDocument parse(
            java.io.Reader r)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.GetOHDsForWTResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                           .parse(r,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.GetOHDsForWTResponseDocument parse(
            java.io.Reader r, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.GetOHDsForWTResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                           .parse(r,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.GetOHDsForWTResponseDocument parse(
            javax.xml.stream.XMLStreamReader sr)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.GetOHDsForWTResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                           .parse(sr,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.GetOHDsForWTResponseDocument parse(
            javax.xml.stream.XMLStreamReader sr,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.GetOHDsForWTResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                           .parse(sr,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.GetOHDsForWTResponseDocument parse(
            org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.GetOHDsForWTResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                           .parse(node,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.GetOHDsForWTResponseDocument parse(
            org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.GetOHDsForWTResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                           .parse(node,
                type, options);
        }

        /** @deprecated {@link XMLInputStream} */
        public static com.bagnet.nettracer.ws.core.GetOHDsForWTResponseDocument parse(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (com.bagnet.nettracer.ws.core.GetOHDsForWTResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                           .parse(xis,
                type, null);
        }

        /** @deprecated {@link XMLInputStream} */
        public static com.bagnet.nettracer.ws.core.GetOHDsForWTResponseDocument parse(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (com.bagnet.nettracer.ws.core.GetOHDsForWTResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
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
