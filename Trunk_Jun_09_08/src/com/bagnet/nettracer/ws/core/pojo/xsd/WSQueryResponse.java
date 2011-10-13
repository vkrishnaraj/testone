/*
 * XML Type:  WS_QueryResponse
 * Namespace: http://pojo.core.ws.nettracer.bagnet.com/xsd
 * Java type: com.bagnet.nettracer.ws.core.pojo.xsd.WSQueryResponse
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.core.pojo.xsd;


/**
 * An XML WS_QueryResponse(@http://pojo.core.ws.nettracer.bagnet.com/xsd).
 *
 * This is a complex type.
 */
public interface WSQueryResponse extends org.apache.xmlbeans.XmlObject {
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType) org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(WSQueryResponse.class.getClassLoader(),
            "schemaorg_apache_xmlbeans.system.sA332F380CC86A9BA665F4B1F0488351C")
                                                                                                                           .resolveHandle("wsqueryresponse8052type");

    /**
     * Gets the "errorResponse" element
     */
    java.lang.String getErrorResponse();

    /**
     * Gets (as xml) the "errorResponse" element
     */
    org.apache.xmlbeans.XmlString xgetErrorResponse();

    /**
     * Tests for nil "errorResponse" element
     */
    boolean isNilErrorResponse();

    /**
     * True if has "errorResponse" element
     */
    boolean isSetErrorResponse();

    /**
     * Sets the "errorResponse" element
     */
    void setErrorResponse(java.lang.String errorResponse);

    /**
     * Sets (as xml) the "errorResponse" element
     */
    void xsetErrorResponse(org.apache.xmlbeans.XmlString errorResponse);

    /**
     * Nils the "errorResponse" element
     */
    void setNilErrorResponse();

    /**
     * Unsets the "errorResponse" element
     */
    void unsetErrorResponse();

    /**
     * Gets array of all "result" elements
     */
    java.lang.String[] getResultArray();

    /**
     * Gets ith "result" element
     */
    java.lang.String getResultArray(int i);

    /**
     * Gets (as xml) array of all "result" elements
     */
    org.apache.xmlbeans.XmlString[] xgetResultArray();

    /**
     * Gets (as xml) ith "result" element
     */
    org.apache.xmlbeans.XmlString xgetResultArray(int i);

    /**
     * Tests for nil ith "result" element
     */
    boolean isNilResultArray(int i);

    /**
     * Returns number of "result" element
     */
    int sizeOfResultArray();

    /**
     * Sets array of all "result" element
     */
    void setResultArray(java.lang.String[] resultArray);

    /**
     * Sets ith "result" element
     */
    void setResultArray(int i, java.lang.String result);

    /**
     * Sets (as xml) array of all "result" element
     */
    void xsetResultArray(org.apache.xmlbeans.XmlString[] resultArray);

    /**
     * Sets (as xml) ith "result" element
     */
    void xsetResultArray(int i, org.apache.xmlbeans.XmlString result);

    /**
     * Nils the ith "result" element
     */
    void setNilResultArray(int i);

    /**
     * Inserts the value as the ith "result" element
     */
    void insertResult(int i, java.lang.String result);

    /**
     * Appends the value as the last "result" element
     */
    void addResult(java.lang.String result);

    /**
     * Inserts and returns a new empty value (as xml) as the ith "result" element
     */
    org.apache.xmlbeans.XmlString insertNewResult(int i);

    /**
     * Appends and returns a new empty value (as xml) as the last "result" element
     */
    org.apache.xmlbeans.XmlString addNewResult();

    /**
     * Removes the ith "result" element
     */
    void removeResult(int i);

    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    public static final class Factory {
        private Factory() {
        } // No instance of this class allowed

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSQueryResponse newInstance() {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSQueryResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                       .newInstance(type,
                null);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSQueryResponse newInstance(
            org.apache.xmlbeans.XmlOptions options) {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSQueryResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                       .newInstance(type,
                options);
        }

        /** @param xmlAsString the string value to parse */
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSQueryResponse parse(
            java.lang.String xmlAsString)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSQueryResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                       .parse(xmlAsString,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSQueryResponse parse(
            java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSQueryResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                       .parse(xmlAsString,
                type, options);
        }

        /** @param file the file from which to load an xml document */
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSQueryResponse parse(
            java.io.File file)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSQueryResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                       .parse(file,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSQueryResponse parse(
            java.io.File file, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSQueryResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                       .parse(file,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSQueryResponse parse(
            java.net.URL u)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSQueryResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                       .parse(u,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSQueryResponse parse(
            java.net.URL u, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSQueryResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                       .parse(u,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSQueryResponse parse(
            java.io.InputStream is)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSQueryResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                       .parse(is,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSQueryResponse parse(
            java.io.InputStream is, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSQueryResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                       .parse(is,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSQueryResponse parse(
            java.io.Reader r)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSQueryResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                       .parse(r,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSQueryResponse parse(
            java.io.Reader r, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSQueryResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                       .parse(r,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSQueryResponse parse(
            javax.xml.stream.XMLStreamReader sr)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSQueryResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                       .parse(sr,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSQueryResponse parse(
            javax.xml.stream.XMLStreamReader sr,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSQueryResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                       .parse(sr,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSQueryResponse parse(
            org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSQueryResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                       .parse(node,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSQueryResponse parse(
            org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSQueryResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                       .parse(node,
                type, options);
        }

        /** @deprecated {@link XMLInputStream} */
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSQueryResponse parse(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSQueryResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                       .parse(xis,
                type, null);
        }

        /** @deprecated {@link XMLInputStream} */
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSQueryResponse parse(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSQueryResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
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
