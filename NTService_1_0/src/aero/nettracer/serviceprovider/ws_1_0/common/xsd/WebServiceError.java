/*
 * XML Type:  WebServiceError
 * Namespace: http://common.ws_1_0.serviceprovider.nettracer.aero/xsd
 * Java type: aero.nettracer.serviceprovider.ws_1_0.common.xsd.WebServiceError
 *
 * Automatically generated - do not modify.
 */
package aero.nettracer.serviceprovider.ws_1_0.common.xsd;


/**
 * An XML WebServiceError(@http://common.ws_1_0.serviceprovider.nettracer.aero/xsd).
 *
 * This is a complex type.
 */
public interface WebServiceError extends org.apache.xmlbeans.XmlObject {
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType) org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(WebServiceError.class.getClassLoader(),
            "schemaorg_apache_xmlbeans.system.sA7FCDC8BA50307927F3589E751AC55EF")
                                                                                                                           .resolveHandle("webserviceerrora463type");

    /**
     * Gets the "description" element
     */
    java.lang.String getDescription();

    /**
     * Gets (as xml) the "description" element
     */
    org.apache.xmlbeans.XmlString xgetDescription();

    /**
     * Tests for nil "description" element
     */
    boolean isNilDescription();

    /**
     * True if has "description" element
     */
    boolean isSetDescription();

    /**
     * Sets the "description" element
     */
    void setDescription(java.lang.String description);

    /**
     * Sets (as xml) the "description" element
     */
    void xsetDescription(org.apache.xmlbeans.XmlString description);

    /**
     * Nils the "description" element
     */
    void setNilDescription();

    /**
     * Unsets the "description" element
     */
    void unsetDescription();

    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    public static final class Factory {
        private Factory() {
        } // No instance of this class allowed

        public static aero.nettracer.serviceprovider.ws_1_0.common.xsd.WebServiceError newInstance() {
            return (aero.nettracer.serviceprovider.ws_1_0.common.xsd.WebServiceError) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                  .newInstance(type,
                null);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.common.xsd.WebServiceError newInstance(
            org.apache.xmlbeans.XmlOptions options) {
            return (aero.nettracer.serviceprovider.ws_1_0.common.xsd.WebServiceError) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                  .newInstance(type,
                options);
        }

        /** @param xmlAsString the string value to parse */
        public static aero.nettracer.serviceprovider.ws_1_0.common.xsd.WebServiceError parse(
            java.lang.String xmlAsString)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.ws_1_0.common.xsd.WebServiceError) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                  .parse(xmlAsString,
                type, null);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.common.xsd.WebServiceError parse(
            java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.ws_1_0.common.xsd.WebServiceError) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                  .parse(xmlAsString,
                type, options);
        }

        /** @param file the file from which to load an xml document */
        public static aero.nettracer.serviceprovider.ws_1_0.common.xsd.WebServiceError parse(
            java.io.File file)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.ws_1_0.common.xsd.WebServiceError) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                  .parse(file,
                type, null);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.common.xsd.WebServiceError parse(
            java.io.File file, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.ws_1_0.common.xsd.WebServiceError) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                  .parse(file,
                type, options);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.common.xsd.WebServiceError parse(
            java.net.URL u)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.ws_1_0.common.xsd.WebServiceError) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                  .parse(u,
                type, null);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.common.xsd.WebServiceError parse(
            java.net.URL u, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.ws_1_0.common.xsd.WebServiceError) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                  .parse(u,
                type, options);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.common.xsd.WebServiceError parse(
            java.io.InputStream is)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.ws_1_0.common.xsd.WebServiceError) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                  .parse(is,
                type, null);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.common.xsd.WebServiceError parse(
            java.io.InputStream is, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.ws_1_0.common.xsd.WebServiceError) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                  .parse(is,
                type, options);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.common.xsd.WebServiceError parse(
            java.io.Reader r)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.ws_1_0.common.xsd.WebServiceError) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                  .parse(r,
                type, null);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.common.xsd.WebServiceError parse(
            java.io.Reader r, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.ws_1_0.common.xsd.WebServiceError) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                  .parse(r,
                type, options);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.common.xsd.WebServiceError parse(
            javax.xml.stream.XMLStreamReader sr)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.ws_1_0.common.xsd.WebServiceError) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                  .parse(sr,
                type, null);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.common.xsd.WebServiceError parse(
            javax.xml.stream.XMLStreamReader sr,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.ws_1_0.common.xsd.WebServiceError) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                  .parse(sr,
                type, options);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.common.xsd.WebServiceError parse(
            org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.ws_1_0.common.xsd.WebServiceError) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                  .parse(node,
                type, null);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.common.xsd.WebServiceError parse(
            org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.ws_1_0.common.xsd.WebServiceError) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                  .parse(node,
                type, options);
        }

        /** @deprecated {@link XMLInputStream} */
        public static aero.nettracer.serviceprovider.ws_1_0.common.xsd.WebServiceError parse(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (aero.nettracer.serviceprovider.ws_1_0.common.xsd.WebServiceError) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                  .parse(xis,
                type, null);
        }

        /** @deprecated {@link XMLInputStream} */
        public static aero.nettracer.serviceprovider.ws_1_0.common.xsd.WebServiceError parse(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (aero.nettracer.serviceprovider.ws_1_0.common.xsd.WebServiceError) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
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
