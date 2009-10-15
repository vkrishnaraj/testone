/*
 * XML Type:  WorldTracerResponse
 * Namespace: http://common.wt_1_0.serviceprovider.nettracer.aero/xsd
 * Java type: aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse
 *
 * Automatically generated - do not modify.
 */
package aero.nettracer.serviceprovider.wt_1_0.common.xsd;


/**
 * An XML WorldTracerResponse(@http://common.wt_1_0.serviceprovider.nettracer.aero/xsd).
 *
 * This is a complex type.
 */
public interface WorldTracerResponse extends aero.nettracer.serviceprovider.ws_1_0.response.xsd.GenericResponse {
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType) org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(WorldTracerResponse.class.getClassLoader(),
            "schemaorg_apache_xmlbeans.system.sBCED675595897F865A233256B59F19BA")
                                                                                                                           .resolveHandle("worldtracerresponse805btype");

    /**
     * Gets the "actionFiles" element
     */
    org.apache.xmlbeans.XmlObject getActionFiles();

    /**
     * Tests for nil "actionFiles" element
     */
    boolean isNilActionFiles();

    /**
     * True if has "actionFiles" element
     */
    boolean isSetActionFiles();

    /**
     * Sets the "actionFiles" element
     */
    void setActionFiles(org.apache.xmlbeans.XmlObject actionFiles);

    /**
     * Appends and returns a new empty "actionFiles" element
     */
    org.apache.xmlbeans.XmlObject addNewActionFiles();

    /**
     * Nils the "actionFiles" element
     */
    void setNilActionFiles();

    /**
     * Unsets the "actionFiles" element
     */
    void unsetActionFiles();

    /**
     * Gets the "ahl" element
     */
    aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl getAhl();

    /**
     * Tests for nil "ahl" element
     */
    boolean isNilAhl();

    /**
     * True if has "ahl" element
     */
    boolean isSetAhl();

    /**
     * Sets the "ahl" element
     */
    void setAhl(aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl ahl);

    /**
     * Appends and returns a new empty "ahl" element
     */
    aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl addNewAhl();

    /**
     * Nils the "ahl" element
     */
    void setNilAhl();

    /**
     * Unsets the "ahl" element
     */
    void unsetAhl();

    /**
     * Gets the "counts" element
     */
    org.apache.xmlbeans.XmlObject getCounts();

    /**
     * Tests for nil "counts" element
     */
    boolean isNilCounts();

    /**
     * True if has "counts" element
     */
    boolean isSetCounts();

    /**
     * Sets the "counts" element
     */
    void setCounts(org.apache.xmlbeans.XmlObject counts);

    /**
     * Appends and returns a new empty "counts" element
     */
    org.apache.xmlbeans.XmlObject addNewCounts();

    /**
     * Nils the "counts" element
     */
    void setNilCounts();

    /**
     * Unsets the "counts" element
     */
    void unsetCounts();

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
     * Gets the "responseData" element
     */
    java.lang.String getResponseData();

    /**
     * Gets (as xml) the "responseData" element
     */
    org.apache.xmlbeans.XmlString xgetResponseData();

    /**
     * Tests for nil "responseData" element
     */
    boolean isNilResponseData();

    /**
     * True if has "responseData" element
     */
    boolean isSetResponseData();

    /**
     * Sets the "responseData" element
     */
    void setResponseData(java.lang.String responseData);

    /**
     * Sets (as xml) the "responseData" element
     */
    void xsetResponseData(org.apache.xmlbeans.XmlString responseData);

    /**
     * Nils the "responseData" element
     */
    void setNilResponseData();

    /**
     * Unsets the "responseData" element
     */
    void unsetResponseData();

    /**
     * Gets the "responseId" element
     */
    java.lang.String getResponseId();

    /**
     * Gets (as xml) the "responseId" element
     */
    org.apache.xmlbeans.XmlString xgetResponseId();

    /**
     * Tests for nil "responseId" element
     */
    boolean isNilResponseId();

    /**
     * True if has "responseId" element
     */
    boolean isSetResponseId();

    /**
     * Sets the "responseId" element
     */
    void setResponseId(java.lang.String responseId);

    /**
     * Sets (as xml) the "responseId" element
     */
    void xsetResponseId(org.apache.xmlbeans.XmlString responseId);

    /**
     * Nils the "responseId" element
     */
    void setNilResponseId();

    /**
     * Unsets the "responseId" element
     */
    void unsetResponseId();

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

        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse newInstance() {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                      .newInstance(type,
                null);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse newInstance(
            org.apache.xmlbeans.XmlOptions options) {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                      .newInstance(type,
                options);
        }

        /** @param xmlAsString the string value to parse */
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse parse(
            java.lang.String xmlAsString)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                      .parse(xmlAsString,
                type, null);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse parse(
            java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                      .parse(xmlAsString,
                type, options);
        }

        /** @param file the file from which to load an xml document */
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse parse(
            java.io.File file)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                      .parse(file,
                type, null);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse parse(
            java.io.File file, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                      .parse(file,
                type, options);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse parse(
            java.net.URL u)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                      .parse(u,
                type, null);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse parse(
            java.net.URL u, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                      .parse(u,
                type, options);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse parse(
            java.io.InputStream is)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                      .parse(is,
                type, null);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse parse(
            java.io.InputStream is, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                      .parse(is,
                type, options);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse parse(
            java.io.Reader r)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                      .parse(r,
                type, null);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse parse(
            java.io.Reader r, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                      .parse(r,
                type, options);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse parse(
            javax.xml.stream.XMLStreamReader sr)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                      .parse(sr,
                type, null);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse parse(
            javax.xml.stream.XMLStreamReader sr,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                      .parse(sr,
                type, options);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse parse(
            org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                      .parse(node,
                type, null);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse parse(
            org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                      .parse(node,
                type, options);
        }

        /** @deprecated {@link XMLInputStream} */
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse parse(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                      .parse(xis,
                type, null);
        }

        /** @deprecated {@link XMLInputStream} */
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse parse(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
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
