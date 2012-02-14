/*
 * XML Type:  UpdateIncidentResponse
 * Namespace: http://xsd.pojo.core.ws.nettracer.bagnet.com/xsd
 * Java type: com.bagnet.nettracer.ws.core.pojo.xsd.xsd.UpdateIncidentResponse
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.core.pojo.xsd.xsd;


/**
 * An XML UpdateIncidentResponse(@http://xsd.pojo.core.ws.nettracer.bagnet.com/xsd).
 *
 * This is a complex type.
 */
public interface UpdateIncidentResponse extends org.apache.xmlbeans.XmlObject {
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType) org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(UpdateIncidentResponse.class.getClassLoader(),
            "schemaorg_apache_xmlbeans.system.sB1D181FCD070B8C28352F412287BB191")
                                                                                                                           .resolveHandle("updateincidentresponse6b97type");

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
     * Gets the "success" element
     */
    boolean getSuccess();

    /**
     * Gets (as xml) the "success" element
     */
    org.apache.xmlbeans.XmlBoolean xgetSuccess();

    /**
     * True if has "success" element
     */
    boolean isSetSuccess();

    /**
     * Sets the "success" element
     */
    void setSuccess(boolean success);

    /**
     * Sets (as xml) the "success" element
     */
    void xsetSuccess(org.apache.xmlbeans.XmlBoolean success);

    /**
     * Unsets the "success" element
     */
    void unsetSuccess();

    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    public static final class Factory {
        private Factory() {
        } // No instance of this class allowed

        public static com.bagnet.nettracer.ws.core.pojo.xsd.xsd.UpdateIncidentResponse newInstance() {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.xsd.UpdateIncidentResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                  .newInstance(type,
                null);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.xsd.UpdateIncidentResponse newInstance(
            org.apache.xmlbeans.XmlOptions options) {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.xsd.UpdateIncidentResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                  .newInstance(type,
                options);
        }

        /** @param xmlAsString the string value to parse */
        public static com.bagnet.nettracer.ws.core.pojo.xsd.xsd.UpdateIncidentResponse parse(
            java.lang.String xmlAsString)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.xsd.UpdateIncidentResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                  .parse(xmlAsString,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.xsd.UpdateIncidentResponse parse(
            java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.xsd.UpdateIncidentResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                  .parse(xmlAsString,
                type, options);
        }

        /** @param file the file from which to load an xml document */
        public static com.bagnet.nettracer.ws.core.pojo.xsd.xsd.UpdateIncidentResponse parse(
            java.io.File file)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.xsd.UpdateIncidentResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                  .parse(file,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.xsd.UpdateIncidentResponse parse(
            java.io.File file, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.xsd.UpdateIncidentResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                  .parse(file,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.xsd.UpdateIncidentResponse parse(
            java.net.URL u)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.xsd.UpdateIncidentResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                  .parse(u,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.xsd.UpdateIncidentResponse parse(
            java.net.URL u, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.xsd.UpdateIncidentResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                  .parse(u,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.xsd.UpdateIncidentResponse parse(
            java.io.InputStream is)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.xsd.UpdateIncidentResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                  .parse(is,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.xsd.UpdateIncidentResponse parse(
            java.io.InputStream is, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.xsd.UpdateIncidentResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                  .parse(is,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.xsd.UpdateIncidentResponse parse(
            java.io.Reader r)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.xsd.UpdateIncidentResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                  .parse(r,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.xsd.UpdateIncidentResponse parse(
            java.io.Reader r, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.xsd.UpdateIncidentResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                  .parse(r,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.xsd.UpdateIncidentResponse parse(
            javax.xml.stream.XMLStreamReader sr)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.xsd.UpdateIncidentResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                  .parse(sr,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.xsd.UpdateIncidentResponse parse(
            javax.xml.stream.XMLStreamReader sr,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.xsd.UpdateIncidentResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                  .parse(sr,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.xsd.UpdateIncidentResponse parse(
            org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.xsd.UpdateIncidentResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                  .parse(node,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.xsd.UpdateIncidentResponse parse(
            org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.xsd.UpdateIncidentResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                  .parse(node,
                type, options);
        }

        /** @deprecated {@link XMLInputStream} */
        public static com.bagnet.nettracer.ws.core.pojo.xsd.xsd.UpdateIncidentResponse parse(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.xsd.UpdateIncidentResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                  .parse(xis,
                type, null);
        }

        /** @deprecated {@link XMLInputStream} */
        public static com.bagnet.nettracer.ws.core.pojo.xsd.xsd.UpdateIncidentResponse parse(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.xsd.UpdateIncidentResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
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
