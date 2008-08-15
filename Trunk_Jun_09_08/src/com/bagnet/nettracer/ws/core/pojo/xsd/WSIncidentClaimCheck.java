/*
 * XML Type:  WS_IncidentClaimCheck
 * Namespace: http://pojo.core.ws.nettracer.bagnet.com/xsd
 * Java type: com.bagnet.nettracer.ws.core.pojo.xsd.WSIncidentClaimCheck
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.core.pojo.xsd;


/**
 * An XML WS_IncidentClaimCheck(@http://pojo.core.ws.nettracer.bagnet.com/xsd).
 *
 * This is a complex type.
 */
public interface WSIncidentClaimCheck extends org.apache.xmlbeans.XmlObject {
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType) org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(WSIncidentClaimCheck.class.getClassLoader(),
            "schemaorg_apache_xmlbeans.system.s16B510B5563ED0857509A4DB5C2996DF")
                                                                                                                           .resolveHandle("wsincidentclaimcheck5c09type");

    /**
     * Gets the "claimchecknum" element
     */
    java.lang.String getClaimchecknum();

    /**
     * Gets (as xml) the "claimchecknum" element
     */
    org.apache.xmlbeans.XmlString xgetClaimchecknum();

    /**
     * Tests for nil "claimchecknum" element
     */
    boolean isNilClaimchecknum();

    /**
     * True if has "claimchecknum" element
     */
    boolean isSetClaimchecknum();

    /**
     * Sets the "claimchecknum" element
     */
    void setClaimchecknum(java.lang.String claimchecknum);

    /**
     * Sets (as xml) the "claimchecknum" element
     */
    void xsetClaimchecknum(org.apache.xmlbeans.XmlString claimchecknum);

    /**
     * Nils the "claimchecknum" element
     */
    void setNilClaimchecknum();

    /**
     * Unsets the "claimchecknum" element
     */
    void unsetClaimchecknum();

    /**
     * Gets the "matched_ohd" element
     */
    java.lang.String getMatchedOhd();

    /**
     * Gets (as xml) the "matched_ohd" element
     */
    org.apache.xmlbeans.XmlString xgetMatchedOhd();

    /**
     * Tests for nil "matched_ohd" element
     */
    boolean isNilMatchedOhd();

    /**
     * True if has "matched_ohd" element
     */
    boolean isSetMatchedOhd();

    /**
     * Sets the "matched_ohd" element
     */
    void setMatchedOhd(java.lang.String matchedOhd);

    /**
     * Sets (as xml) the "matched_ohd" element
     */
    void xsetMatchedOhd(org.apache.xmlbeans.XmlString matchedOhd);

    /**
     * Nils the "matched_ohd" element
     */
    void setNilMatchedOhd();

    /**
     * Unsets the "matched_ohd" element
     */
    void unsetMatchedOhd();

    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    public static final class Factory {
        private Factory() {
        } // No instance of this class allowed

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSIncidentClaimCheck newInstance() {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSIncidentClaimCheck) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                            .newInstance(type,
                null);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSIncidentClaimCheck newInstance(
            org.apache.xmlbeans.XmlOptions options) {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSIncidentClaimCheck) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                            .newInstance(type,
                options);
        }

        /** @param xmlAsString the string value to parse */
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSIncidentClaimCheck parse(
            java.lang.String xmlAsString)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSIncidentClaimCheck) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                            .parse(xmlAsString,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSIncidentClaimCheck parse(
            java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSIncidentClaimCheck) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                            .parse(xmlAsString,
                type, options);
        }

        /** @param file the file from which to load an xml document */
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSIncidentClaimCheck parse(
            java.io.File file)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSIncidentClaimCheck) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                            .parse(file,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSIncidentClaimCheck parse(
            java.io.File file, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSIncidentClaimCheck) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                            .parse(file,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSIncidentClaimCheck parse(
            java.net.URL u)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSIncidentClaimCheck) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                            .parse(u,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSIncidentClaimCheck parse(
            java.net.URL u, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSIncidentClaimCheck) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                            .parse(u,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSIncidentClaimCheck parse(
            java.io.InputStream is)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSIncidentClaimCheck) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                            .parse(is,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSIncidentClaimCheck parse(
            java.io.InputStream is, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSIncidentClaimCheck) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                            .parse(is,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSIncidentClaimCheck parse(
            java.io.Reader r)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSIncidentClaimCheck) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                            .parse(r,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSIncidentClaimCheck parse(
            java.io.Reader r, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSIncidentClaimCheck) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                            .parse(r,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSIncidentClaimCheck parse(
            javax.xml.stream.XMLStreamReader sr)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSIncidentClaimCheck) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                            .parse(sr,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSIncidentClaimCheck parse(
            javax.xml.stream.XMLStreamReader sr,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSIncidentClaimCheck) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                            .parse(sr,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSIncidentClaimCheck parse(
            org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSIncidentClaimCheck) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                            .parse(node,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSIncidentClaimCheck parse(
            org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSIncidentClaimCheck) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                            .parse(node,
                type, options);
        }

        /** @deprecated {@link XMLInputStream} */
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSIncidentClaimCheck parse(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSIncidentClaimCheck) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                            .parse(xis,
                type, null);
        }

        /** @deprecated {@link XMLInputStream} */
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSIncidentClaimCheck parse(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSIncidentClaimCheck) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
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
