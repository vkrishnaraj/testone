/*
 * XML Type:  RequestHeader
 * Namespace: http://common.ws_1_0.serviceprovider.nettracer.aero/xsd
 * Java type: aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader
 *
 * Automatically generated - do not modify.
 */
package aero.nettracer.serviceprovider.ws_1_0.common.xsd;


/**
 * An XML RequestHeader(@http://common.ws_1_0.serviceprovider.nettracer.aero/xsd).
 *
 * This is a complex type.
 */
public interface RequestHeader extends org.apache.xmlbeans.XmlObject {
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType) org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(RequestHeader.class.getClassLoader(),
            "schemaorg_apache_xmlbeans.system.sEFE6345392E523A6707B3AC577A384C3")
                                                                                                                           .resolveHandle("requestheader7f58type");

    /**
     * Gets the "parameters" element
     */
    aero.nettracer.serviceprovider.ws_1_0.common.xsd.Parameter getParameters();

    /**
     * Tests for nil "parameters" element
     */
    boolean isNilParameters();

    /**
     * True if has "parameters" element
     */
    boolean isSetParameters();

    /**
     * Sets the "parameters" element
     */
    void setParameters(
        aero.nettracer.serviceprovider.ws_1_0.common.xsd.Parameter parameters);

    /**
     * Appends and returns a new empty "parameters" element
     */
    aero.nettracer.serviceprovider.ws_1_0.common.xsd.Parameter addNewParameters();

    /**
     * Nils the "parameters" element
     */
    void setNilParameters();

    /**
     * Unsets the "parameters" element
     */
    void unsetParameters();

    /**
     * Gets the "password" element
     */
    java.lang.String getPassword();

    /**
     * Gets (as xml) the "password" element
     */
    org.apache.xmlbeans.XmlString xgetPassword();

    /**
     * Tests for nil "password" element
     */
    boolean isNilPassword();

    /**
     * True if has "password" element
     */
    boolean isSetPassword();

    /**
     * Sets the "password" element
     */
    void setPassword(java.lang.String password);

    /**
     * Sets (as xml) the "password" element
     */
    void xsetPassword(org.apache.xmlbeans.XmlString password);

    /**
     * Nils the "password" element
     */
    void setNilPassword();

    /**
     * Unsets the "password" element
     */
    void unsetPassword();

    /**
     * Gets the "username" element
     */
    java.lang.String getUsername();

    /**
     * Gets (as xml) the "username" element
     */
    org.apache.xmlbeans.XmlString xgetUsername();

    /**
     * Tests for nil "username" element
     */
    boolean isNilUsername();

    /**
     * True if has "username" element
     */
    boolean isSetUsername();

    /**
     * Sets the "username" element
     */
    void setUsername(java.lang.String username);

    /**
     * Sets (as xml) the "username" element
     */
    void xsetUsername(org.apache.xmlbeans.XmlString username);

    /**
     * Nils the "username" element
     */
    void setNilUsername();

    /**
     * Unsets the "username" element
     */
    void unsetUsername();

    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    public static final class Factory {
        private Factory() {
        } // No instance of this class allowed

        public static aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader newInstance() {
            return (aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                .newInstance(type,
                null);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader newInstance(
            org.apache.xmlbeans.XmlOptions options) {
            return (aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                .newInstance(type,
                options);
        }

        /** @param xmlAsString the string value to parse */
        public static aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader parse(
            java.lang.String xmlAsString)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                .parse(xmlAsString,
                type, null);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader parse(
            java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                .parse(xmlAsString,
                type, options);
        }

        /** @param file the file from which to load an xml document */
        public static aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader parse(
            java.io.File file)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                .parse(file,
                type, null);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader parse(
            java.io.File file, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                .parse(file,
                type, options);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader parse(
            java.net.URL u)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                .parse(u,
                type, null);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader parse(
            java.net.URL u, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                .parse(u,
                type, options);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader parse(
            java.io.InputStream is)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                .parse(is,
                type, null);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader parse(
            java.io.InputStream is, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                .parse(is,
                type, options);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader parse(
            java.io.Reader r)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                .parse(r,
                type, null);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader parse(
            java.io.Reader r, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                .parse(r,
                type, options);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader parse(
            javax.xml.stream.XMLStreamReader sr)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                .parse(sr,
                type, null);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader parse(
            javax.xml.stream.XMLStreamReader sr,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                .parse(sr,
                type, options);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader parse(
            org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                .parse(node,
                type, null);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader parse(
            org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                .parse(node,
                type, options);
        }

        /** @deprecated {@link XMLInputStream} */
        public static aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader parse(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                .parse(xis,
                type, null);
        }

        /** @deprecated {@link XMLInputStream} */
        public static aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader parse(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
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
