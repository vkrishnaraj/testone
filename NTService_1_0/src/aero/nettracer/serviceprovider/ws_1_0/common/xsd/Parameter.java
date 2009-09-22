/*
 * XML Type:  Parameter
 * Namespace: http://common.ws_1_0.serviceprovider.nettracer.aero/xsd
 * Java type: aero.nettracer.serviceprovider.ws_1_0.common.xsd.Parameter
 *
 * Automatically generated - do not modify.
 */
package aero.nettracer.serviceprovider.ws_1_0.common.xsd;


/**
 * An XML Parameter(@http://common.ws_1_0.serviceprovider.nettracer.aero/xsd).
 *
 * This is a complex type.
 */
public interface Parameter extends org.apache.xmlbeans.XmlObject {
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType) org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(Parameter.class.getClassLoader(),
            "schemaorg_apache_xmlbeans.system.sEFE6345392E523A6707B3AC577A384C3")
                                                                                                                           .resolveHandle("parameter1185type");

    /**
     * Gets the "name" element
     */
    java.lang.String getName();

    /**
     * Gets (as xml) the "name" element
     */
    org.apache.xmlbeans.XmlString xgetName();

    /**
     * Tests for nil "name" element
     */
    boolean isNilName();

    /**
     * True if has "name" element
     */
    boolean isSetName();

    /**
     * Sets the "name" element
     */
    void setName(java.lang.String name);

    /**
     * Sets (as xml) the "name" element
     */
    void xsetName(org.apache.xmlbeans.XmlString name);

    /**
     * Nils the "name" element
     */
    void setNilName();

    /**
     * Unsets the "name" element
     */
    void unsetName();

    /**
     * Gets the "value" element
     */
    java.lang.String getValue();

    /**
     * Gets (as xml) the "value" element
     */
    org.apache.xmlbeans.XmlString xgetValue();

    /**
     * Tests for nil "value" element
     */
    boolean isNilValue();

    /**
     * True if has "value" element
     */
    boolean isSetValue();

    /**
     * Sets the "value" element
     */
    void setValue(java.lang.String value);

    /**
     * Sets (as xml) the "value" element
     */
    void xsetValue(org.apache.xmlbeans.XmlString value);

    /**
     * Nils the "value" element
     */
    void setNilValue();

    /**
     * Unsets the "value" element
     */
    void unsetValue();

    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    public static final class Factory {
        private Factory() {
        } // No instance of this class allowed

        public static aero.nettracer.serviceprovider.ws_1_0.common.xsd.Parameter newInstance() {
            return (aero.nettracer.serviceprovider.ws_1_0.common.xsd.Parameter) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                            .newInstance(type,
                null);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.common.xsd.Parameter newInstance(
            org.apache.xmlbeans.XmlOptions options) {
            return (aero.nettracer.serviceprovider.ws_1_0.common.xsd.Parameter) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                            .newInstance(type,
                options);
        }

        /** @param xmlAsString the string value to parse */
        public static aero.nettracer.serviceprovider.ws_1_0.common.xsd.Parameter parse(
            java.lang.String xmlAsString)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.ws_1_0.common.xsd.Parameter) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                            .parse(xmlAsString,
                type, null);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.common.xsd.Parameter parse(
            java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.ws_1_0.common.xsd.Parameter) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                            .parse(xmlAsString,
                type, options);
        }

        /** @param file the file from which to load an xml document */
        public static aero.nettracer.serviceprovider.ws_1_0.common.xsd.Parameter parse(
            java.io.File file)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.ws_1_0.common.xsd.Parameter) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                            .parse(file,
                type, null);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.common.xsd.Parameter parse(
            java.io.File file, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.ws_1_0.common.xsd.Parameter) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                            .parse(file,
                type, options);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.common.xsd.Parameter parse(
            java.net.URL u)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.ws_1_0.common.xsd.Parameter) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                            .parse(u,
                type, null);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.common.xsd.Parameter parse(
            java.net.URL u, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.ws_1_0.common.xsd.Parameter) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                            .parse(u,
                type, options);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.common.xsd.Parameter parse(
            java.io.InputStream is)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.ws_1_0.common.xsd.Parameter) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                            .parse(is,
                type, null);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.common.xsd.Parameter parse(
            java.io.InputStream is, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.ws_1_0.common.xsd.Parameter) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                            .parse(is,
                type, options);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.common.xsd.Parameter parse(
            java.io.Reader r)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.ws_1_0.common.xsd.Parameter) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                            .parse(r,
                type, null);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.common.xsd.Parameter parse(
            java.io.Reader r, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.ws_1_0.common.xsd.Parameter) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                            .parse(r,
                type, options);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.common.xsd.Parameter parse(
            javax.xml.stream.XMLStreamReader sr)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.ws_1_0.common.xsd.Parameter) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                            .parse(sr,
                type, null);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.common.xsd.Parameter parse(
            javax.xml.stream.XMLStreamReader sr,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.ws_1_0.common.xsd.Parameter) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                            .parse(sr,
                type, options);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.common.xsd.Parameter parse(
            org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.ws_1_0.common.xsd.Parameter) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                            .parse(node,
                type, null);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.common.xsd.Parameter parse(
            org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.ws_1_0.common.xsd.Parameter) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                            .parse(node,
                type, options);
        }

        /** @deprecated {@link XMLInputStream} */
        public static aero.nettracer.serviceprovider.ws_1_0.common.xsd.Parameter parse(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (aero.nettracer.serviceprovider.ws_1_0.common.xsd.Parameter) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                            .parse(xis,
                type, null);
        }

        /** @deprecated {@link XMLInputStream} */
        public static aero.nettracer.serviceprovider.ws_1_0.common.xsd.Parameter parse(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (aero.nettracer.serviceprovider.ws_1_0.common.xsd.Parameter) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
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
