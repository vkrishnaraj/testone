/*
 * XML Type:  Pxf
 * Namespace: http://common.wt_1_0.serviceprovider.nettracer.aero/xsd
 * Java type: aero.nettracer.serviceprovider.wt_1_0.common.xsd.Pxf
 *
 * Automatically generated - do not modify.
 */
package aero.nettracer.serviceprovider.wt_1_0.common.xsd;


/**
 * An XML Pxf(@http://common.wt_1_0.serviceprovider.nettracer.aero/xsd).
 *
 * This is a complex type.
 */
public interface Pxf extends org.apache.xmlbeans.XmlObject {
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType) org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(Pxf.class.getClassLoader(),
            "schemaorg_apache_xmlbeans.system.sBCED675595897F865A233256B59F19BA")
                                                                                                                           .resolveHandle("pxfdc59type");

    /**
     * Gets the "content" element
     */
    java.lang.String getContent();

    /**
     * Gets (as xml) the "content" element
     */
    org.apache.xmlbeans.XmlString xgetContent();

    /**
     * Tests for nil "content" element
     */
    boolean isNilContent();

    /**
     * True if has "content" element
     */
    boolean isSetContent();

    /**
     * Sets the "content" element
     */
    void setContent(java.lang.String content);

    /**
     * Sets (as xml) the "content" element
     */
    void xsetContent(org.apache.xmlbeans.XmlString content);

    /**
     * Nils the "content" element
     */
    void setNilContent();

    /**
     * Unsets the "content" element
     */
    void unsetContent();

    /**
     * Gets the "destination" element
     */
    int getDestination();

    /**
     * Gets (as xml) the "destination" element
     */
    org.apache.xmlbeans.XmlInt xgetDestination();

    /**
     * True if has "destination" element
     */
    boolean isSetDestination();

    /**
     * Sets the "destination" element
     */
    void setDestination(int destination);

    /**
     * Sets (as xml) the "destination" element
     */
    void xsetDestination(org.apache.xmlbeans.XmlInt destination);

    /**
     * Unsets the "destination" element
     */
    void unsetDestination();

    /**
     * Gets array of all "pxfDetails" elements
     */
    aero.nettracer.serviceprovider.wt_1_0.common.xsd.PxfAas[] getPxfDetailsArray();

    /**
     * Gets ith "pxfDetails" element
     */
    aero.nettracer.serviceprovider.wt_1_0.common.xsd.PxfAas getPxfDetailsArray(
        int i);

    /**
     * Tests for nil ith "pxfDetails" element
     */
    boolean isNilPxfDetailsArray(int i);

    /**
     * Returns number of "pxfDetails" element
     */
    int sizeOfPxfDetailsArray();

    /**
     * Sets array of all "pxfDetails" element
     */
    void setPxfDetailsArray(
        aero.nettracer.serviceprovider.wt_1_0.common.xsd.PxfAas[] pxfDetailsArray);

    /**
     * Sets ith "pxfDetails" element
     */
    void setPxfDetailsArray(int i,
        aero.nettracer.serviceprovider.wt_1_0.common.xsd.PxfAas pxfDetails);

    /**
     * Nils the ith "pxfDetails" element
     */
    void setNilPxfDetailsArray(int i);

    /**
     * Inserts and returns a new empty value (as xml) as the ith "pxfDetails" element
     */
    aero.nettracer.serviceprovider.wt_1_0.common.xsd.PxfAas insertNewPxfDetails(
        int i);

    /**
     * Appends and returns a new empty value (as xml) as the last "pxfDetails" element
     */
    aero.nettracer.serviceprovider.wt_1_0.common.xsd.PxfAas addNewPxfDetails();

    /**
     * Removes the ith "pxfDetails" element
     */
    void removePxfDetails(int i);

    /**
     * Gets array of all "teletype" elements
     */
    java.lang.String[] getTeletypeArray();

    /**
     * Gets ith "teletype" element
     */
    java.lang.String getTeletypeArray(int i);

    /**
     * Gets (as xml) array of all "teletype" elements
     */
    org.apache.xmlbeans.XmlString[] xgetTeletypeArray();

    /**
     * Gets (as xml) ith "teletype" element
     */
    org.apache.xmlbeans.XmlString xgetTeletypeArray(int i);

    /**
     * Tests for nil ith "teletype" element
     */
    boolean isNilTeletypeArray(int i);

    /**
     * Returns number of "teletype" element
     */
    int sizeOfTeletypeArray();

    /**
     * Sets array of all "teletype" element
     */
    void setTeletypeArray(java.lang.String[] teletypeArray);

    /**
     * Sets ith "teletype" element
     */
    void setTeletypeArray(int i, java.lang.String teletype);

    /**
     * Sets (as xml) array of all "teletype" element
     */
    void xsetTeletypeArray(org.apache.xmlbeans.XmlString[] teletypeArray);

    /**
     * Sets (as xml) ith "teletype" element
     */
    void xsetTeletypeArray(int i, org.apache.xmlbeans.XmlString teletype);

    /**
     * Nils the ith "teletype" element
     */
    void setNilTeletypeArray(int i);

    /**
     * Inserts the value as the ith "teletype" element
     */
    void insertTeletype(int i, java.lang.String teletype);

    /**
     * Appends the value as the last "teletype" element
     */
    void addTeletype(java.lang.String teletype);

    /**
     * Inserts and returns a new empty value (as xml) as the ith "teletype" element
     */
    org.apache.xmlbeans.XmlString insertNewTeletype(int i);

    /**
     * Appends and returns a new empty value (as xml) as the last "teletype" element
     */
    org.apache.xmlbeans.XmlString addNewTeletype();

    /**
     * Removes the ith "teletype" element
     */
    void removeTeletype(int i);

    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    public static final class Factory {
        private Factory() {
        } // No instance of this class allowed

        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.Pxf newInstance() {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Pxf) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                      .newInstance(type,
                null);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.Pxf newInstance(
            org.apache.xmlbeans.XmlOptions options) {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Pxf) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                      .newInstance(type,
                options);
        }

        /** @param xmlAsString the string value to parse */
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.Pxf parse(
            java.lang.String xmlAsString)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Pxf) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                      .parse(xmlAsString,
                type, null);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.Pxf parse(
            java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Pxf) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                      .parse(xmlAsString,
                type, options);
        }

        /** @param file the file from which to load an xml document */
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.Pxf parse(
            java.io.File file)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Pxf) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                      .parse(file,
                type, null);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.Pxf parse(
            java.io.File file, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Pxf) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                      .parse(file,
                type, options);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.Pxf parse(
            java.net.URL u)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Pxf) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                      .parse(u,
                type, null);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.Pxf parse(
            java.net.URL u, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Pxf) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                      .parse(u,
                type, options);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.Pxf parse(
            java.io.InputStream is)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Pxf) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                      .parse(is,
                type, null);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.Pxf parse(
            java.io.InputStream is, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Pxf) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                      .parse(is,
                type, options);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.Pxf parse(
            java.io.Reader r)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Pxf) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                      .parse(r,
                type, null);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.Pxf parse(
            java.io.Reader r, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Pxf) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                      .parse(r,
                type, options);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.Pxf parse(
            javax.xml.stream.XMLStreamReader sr)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Pxf) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                      .parse(sr,
                type, null);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.Pxf parse(
            javax.xml.stream.XMLStreamReader sr,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Pxf) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                      .parse(sr,
                type, options);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.Pxf parse(
            org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Pxf) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                      .parse(node,
                type, null);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.Pxf parse(
            org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Pxf) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                      .parse(node,
                type, options);
        }

        /** @deprecated {@link XMLInputStream} */
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.Pxf parse(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Pxf) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                      .parse(xis,
                type, null);
        }

        /** @deprecated {@link XMLInputStream} */
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.Pxf parse(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Pxf) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
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
