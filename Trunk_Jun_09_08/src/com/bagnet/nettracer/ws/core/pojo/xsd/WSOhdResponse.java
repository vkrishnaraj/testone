/*
 * XML Type:  WS_OhdResponse
 * Namespace: http://pojo.core.ws.nettracer.bagnet.com/xsd
 * Java type: com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.core.pojo.xsd;


/**
 * An XML WS_OhdResponse(@http://pojo.core.ws.nettracer.bagnet.com/xsd).
 *
 * This is a complex type.
 */
public interface WSOhdResponse extends org.apache.xmlbeans.XmlObject {
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType) org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(WSOhdResponse.class.getClassLoader(),
            "schemaorg_apache_xmlbeans.system.sB1D181FCD070B8C28352F412287BB191")
                                                                                                                           .resolveHandle("wsohdresponse51cftype");

    /**
     * Gets the "claimCheckNumber" element
     */
    java.lang.String getClaimCheckNumber();

    /**
     * Gets (as xml) the "claimCheckNumber" element
     */
    org.apache.xmlbeans.XmlString xgetClaimCheckNumber();

    /**
     * Tests for nil "claimCheckNumber" element
     */
    boolean isNilClaimCheckNumber();

    /**
     * True if has "claimCheckNumber" element
     */
    boolean isSetClaimCheckNumber();

    /**
     * Sets the "claimCheckNumber" element
     */
    void setClaimCheckNumber(java.lang.String claimCheckNumber);

    /**
     * Sets (as xml) the "claimCheckNumber" element
     */
    void xsetClaimCheckNumber(org.apache.xmlbeans.XmlString claimCheckNumber);

    /**
     * Nils the "claimCheckNumber" element
     */
    void setNilClaimCheckNumber();

    /**
     * Unsets the "claimCheckNumber" element
     */
    void unsetClaimCheckNumber();

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
     * Gets the "ohdId" element
     */
    java.lang.String getOhdId();

    /**
     * Gets (as xml) the "ohdId" element
     */
    org.apache.xmlbeans.XmlString xgetOhdId();

    /**
     * Tests for nil "ohdId" element
     */
    boolean isNilOhdId();

    /**
     * True if has "ohdId" element
     */
    boolean isSetOhdId();

    /**
     * Sets the "ohdId" element
     */
    void setOhdId(java.lang.String ohdId);

    /**
     * Sets (as xml) the "ohdId" element
     */
    void xsetOhdId(org.apache.xmlbeans.XmlString ohdId);

    /**
     * Nils the "ohdId" element
     */
    void setNilOhdId();

    /**
     * Unsets the "ohdId" element
     */
    void unsetOhdId();

    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    public static final class Factory {
        private Factory() {
        } // No instance of this class allowed

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse newInstance() {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                     .newInstance(type,
                null);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse newInstance(
            org.apache.xmlbeans.XmlOptions options) {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                     .newInstance(type,
                options);
        }

        /** @param xmlAsString the string value to parse */
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse parse(
            java.lang.String xmlAsString)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                     .parse(xmlAsString,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse parse(
            java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                     .parse(xmlAsString,
                type, options);
        }

        /** @param file the file from which to load an xml document */
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse parse(
            java.io.File file)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                     .parse(file,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse parse(
            java.io.File file, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                     .parse(file,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse parse(
            java.net.URL u)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                     .parse(u,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse parse(
            java.net.URL u, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                     .parse(u,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse parse(
            java.io.InputStream is)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                     .parse(is,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse parse(
            java.io.InputStream is, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                     .parse(is,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse parse(
            java.io.Reader r)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                     .parse(r,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse parse(
            java.io.Reader r, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                     .parse(r,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse parse(
            javax.xml.stream.XMLStreamReader sr)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                     .parse(sr,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse parse(
            javax.xml.stream.XMLStreamReader sr,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                     .parse(sr,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse parse(
            org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                     .parse(node,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse parse(
            org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                     .parse(node,
                type, options);
        }

        /** @deprecated {@link XMLInputStream} */
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse parse(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                     .parse(xis,
                type, null);
        }

        /** @deprecated {@link XMLInputStream} */
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse parse(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
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
