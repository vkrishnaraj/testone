/*
 * XML Type:  ForwardOhd
 * Namespace: http://common.wt_1_0.serviceprovider.nettracer.aero/xsd
 * Java type: aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardOhd
 *
 * Automatically generated - do not modify.
 */
package aero.nettracer.serviceprovider.wt_1_0.common.xsd;


/**
 * An XML ForwardOhd(@http://common.wt_1_0.serviceprovider.nettracer.aero/xsd).
 *
 * This is a complex type.
 */
public interface ForwardOhd extends org.apache.xmlbeans.XmlObject {
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType) org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(ForwardOhd.class.getClassLoader(),
            "schemaorg_apache_xmlbeans.system.sBCED675595897F865A233256B59F19BA")
                                                                                                                           .resolveHandle("forwardohd6213type");

    /**
     * Gets the "ohd" element
     */
    aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd getOhd();

    /**
     * Tests for nil "ohd" element
     */
    boolean isNilOhd();

    /**
     * True if has "ohd" element
     */
    boolean isSetOhd();

    /**
     * Sets the "ohd" element
     */
    void setOhd(aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd ohd);

    /**
     * Appends and returns a new empty "ohd" element
     */
    aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd addNewOhd();

    /**
     * Nils the "ohd" element
     */
    void setNilOhd();

    /**
     * Unsets the "ohd" element
     */
    void unsetOhd();

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

        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardOhd newInstance() {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardOhd) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                             .newInstance(type,
                null);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardOhd newInstance(
            org.apache.xmlbeans.XmlOptions options) {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardOhd) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                             .newInstance(type,
                options);
        }

        /** @param xmlAsString the string value to parse */
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardOhd parse(
            java.lang.String xmlAsString)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardOhd) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                             .parse(xmlAsString,
                type, null);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardOhd parse(
            java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardOhd) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                             .parse(xmlAsString,
                type, options);
        }

        /** @param file the file from which to load an xml document */
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardOhd parse(
            java.io.File file)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardOhd) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                             .parse(file,
                type, null);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardOhd parse(
            java.io.File file, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardOhd) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                             .parse(file,
                type, options);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardOhd parse(
            java.net.URL u)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardOhd) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                             .parse(u,
                type, null);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardOhd parse(
            java.net.URL u, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardOhd) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                             .parse(u,
                type, options);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardOhd parse(
            java.io.InputStream is)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardOhd) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                             .parse(is,
                type, null);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardOhd parse(
            java.io.InputStream is, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardOhd) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                             .parse(is,
                type, options);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardOhd parse(
            java.io.Reader r)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardOhd) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                             .parse(r,
                type, null);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardOhd parse(
            java.io.Reader r, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardOhd) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                             .parse(r,
                type, options);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardOhd parse(
            javax.xml.stream.XMLStreamReader sr)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardOhd) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                             .parse(sr,
                type, null);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardOhd parse(
            javax.xml.stream.XMLStreamReader sr,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardOhd) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                             .parse(sr,
                type, options);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardOhd parse(
            org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardOhd) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                             .parse(node,
                type, null);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardOhd parse(
            org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardOhd) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                             .parse(node,
                type, options);
        }

        /** @deprecated {@link XMLInputStream} */
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardOhd parse(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardOhd) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                             .parse(xis,
                type, null);
        }

        /** @deprecated {@link XMLInputStream} */
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardOhd parse(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardOhd) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
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
