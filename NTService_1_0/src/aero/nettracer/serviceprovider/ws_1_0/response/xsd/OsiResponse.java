/*
 * XML Type:  OsiResponse
 * Namespace: http://response.ws_1_0.serviceprovider.nettracer.aero/xsd
 * Java type: aero.nettracer.serviceprovider.ws_1_0.response.xsd.OsiResponse
 *
 * Automatically generated - do not modify.
 */
package aero.nettracer.serviceprovider.ws_1_0.response.xsd;


/**
 * An XML OsiResponse(@http://response.ws_1_0.serviceprovider.nettracer.aero/xsd).
 *
 * This is a complex type.
 */
public interface OsiResponse extends aero.nettracer.serviceprovider.ws_1_0.response.xsd.GenericResponse {
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType) org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(OsiResponse.class.getClassLoader(),
            "schemaorg_apache_xmlbeans.system.s3ABAB084C02932FDB9E0FCEE6AB72CA7")
                                                                                                                           .resolveHandle("osiresponse2298type");

    /**
     * Gets the "osi" element
     */
    java.lang.String getOsi();

    /**
     * Gets (as xml) the "osi" element
     */
    org.apache.xmlbeans.XmlString xgetOsi();

    /**
     * Tests for nil "osi" element
     */
    boolean isNilOsi();

    /**
     * True if has "osi" element
     */
    boolean isSetOsi();

    /**
     * Sets the "osi" element
     */
    void setOsi(java.lang.String osi);

    /**
     * Sets (as xml) the "osi" element
     */
    void xsetOsi(org.apache.xmlbeans.XmlString osi);

    /**
     * Nils the "osi" element
     */
    void setNilOsi();

    /**
     * Unsets the "osi" element
     */
    void unsetOsi();

    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    public static final class Factory {
        private Factory() {
        } // No instance of this class allowed

        public static aero.nettracer.serviceprovider.ws_1_0.response.xsd.OsiResponse newInstance() {
            return (aero.nettracer.serviceprovider.ws_1_0.response.xsd.OsiResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                .newInstance(type,
                null);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.response.xsd.OsiResponse newInstance(
            org.apache.xmlbeans.XmlOptions options) {
            return (aero.nettracer.serviceprovider.ws_1_0.response.xsd.OsiResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                .newInstance(type,
                options);
        }

        /** @param xmlAsString the string value to parse */
        public static aero.nettracer.serviceprovider.ws_1_0.response.xsd.OsiResponse parse(
            java.lang.String xmlAsString)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.ws_1_0.response.xsd.OsiResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                .parse(xmlAsString,
                type, null);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.response.xsd.OsiResponse parse(
            java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.ws_1_0.response.xsd.OsiResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                .parse(xmlAsString,
                type, options);
        }

        /** @param file the file from which to load an xml document */
        public static aero.nettracer.serviceprovider.ws_1_0.response.xsd.OsiResponse parse(
            java.io.File file)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.ws_1_0.response.xsd.OsiResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                .parse(file,
                type, null);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.response.xsd.OsiResponse parse(
            java.io.File file, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.ws_1_0.response.xsd.OsiResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                .parse(file,
                type, options);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.response.xsd.OsiResponse parse(
            java.net.URL u)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.ws_1_0.response.xsd.OsiResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                .parse(u,
                type, null);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.response.xsd.OsiResponse parse(
            java.net.URL u, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.ws_1_0.response.xsd.OsiResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                .parse(u,
                type, options);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.response.xsd.OsiResponse parse(
            java.io.InputStream is)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.ws_1_0.response.xsd.OsiResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                .parse(is,
                type, null);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.response.xsd.OsiResponse parse(
            java.io.InputStream is, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.ws_1_0.response.xsd.OsiResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                .parse(is,
                type, options);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.response.xsd.OsiResponse parse(
            java.io.Reader r)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.ws_1_0.response.xsd.OsiResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                .parse(r,
                type, null);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.response.xsd.OsiResponse parse(
            java.io.Reader r, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.ws_1_0.response.xsd.OsiResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                .parse(r,
                type, options);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.response.xsd.OsiResponse parse(
            javax.xml.stream.XMLStreamReader sr)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.ws_1_0.response.xsd.OsiResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                .parse(sr,
                type, null);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.response.xsd.OsiResponse parse(
            javax.xml.stream.XMLStreamReader sr,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.ws_1_0.response.xsd.OsiResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                .parse(sr,
                type, options);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.response.xsd.OsiResponse parse(
            org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.ws_1_0.response.xsd.OsiResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                .parse(node,
                type, null);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.response.xsd.OsiResponse parse(
            org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.ws_1_0.response.xsd.OsiResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                .parse(node,
                type, options);
        }

        /** @deprecated {@link XMLInputStream} */
        public static aero.nettracer.serviceprovider.ws_1_0.response.xsd.OsiResponse parse(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (aero.nettracer.serviceprovider.ws_1_0.response.xsd.OsiResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                .parse(xis,
                type, null);
        }

        /** @deprecated {@link XMLInputStream} */
        public static aero.nettracer.serviceprovider.ws_1_0.response.xsd.OsiResponse parse(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (aero.nettracer.serviceprovider.ws_1_0.response.xsd.OsiResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
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
