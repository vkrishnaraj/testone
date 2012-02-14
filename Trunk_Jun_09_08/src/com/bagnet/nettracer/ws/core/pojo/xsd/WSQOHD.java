/*
 * XML Type:  WS_QOHD
 * Namespace: http://pojo.core.ws.nettracer.bagnet.com/xsd
 * Java type: com.bagnet.nettracer.ws.core.pojo.xsd.WSQOHD
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.core.pojo.xsd;


/**
 * An XML WS_QOHD(@http://pojo.core.ws.nettracer.bagnet.com/xsd).
 *
 * This is a complex type.
 */
public interface WSQOHD extends org.apache.xmlbeans.XmlObject {
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType) org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(WSQOHD.class.getClassLoader(),
            "schemaorg_apache_xmlbeans.system.sB1D181FCD070B8C28352F412287BB191")
                                                                                                                           .resolveHandle("wsqohd912dtype");

    /**
     * Gets the "bagTagNumber" element
     */
    java.lang.String getBagTagNumber();

    /**
     * Gets (as xml) the "bagTagNumber" element
     */
    org.apache.xmlbeans.XmlString xgetBagTagNumber();

    /**
     * Tests for nil "bagTagNumber" element
     */
    boolean isNilBagTagNumber();

    /**
     * True if has "bagTagNumber" element
     */
    boolean isSetBagTagNumber();

    /**
     * Sets the "bagTagNumber" element
     */
    void setBagTagNumber(java.lang.String bagTagNumber);

    /**
     * Sets (as xml) the "bagTagNumber" element
     */
    void xsetBagTagNumber(org.apache.xmlbeans.XmlString bagTagNumber);

    /**
     * Nils the "bagTagNumber" element
     */
    void setNilBagTagNumber();

    /**
     * Unsets the "bagTagNumber" element
     */
    void unsetBagTagNumber();

    /**
     * Gets the "comment" element
     */
    java.lang.String getComment();

    /**
     * Gets (as xml) the "comment" element
     */
    org.apache.xmlbeans.XmlString xgetComment();

    /**
     * Tests for nil "comment" element
     */
    boolean isNilComment();

    /**
     * True if has "comment" element
     */
    boolean isSetComment();

    /**
     * Sets the "comment" element
     */
    void setComment(java.lang.String comment);

    /**
     * Sets (as xml) the "comment" element
     */
    void xsetComment(org.apache.xmlbeans.XmlString comment);

    /**
     * Nils the "comment" element
     */
    void setNilComment();

    /**
     * Unsets the "comment" element
     */
    void unsetComment();

    /**
     * Gets the "foundAtStation" element
     */
    java.lang.String getFoundAtStation();

    /**
     * Gets (as xml) the "foundAtStation" element
     */
    org.apache.xmlbeans.XmlString xgetFoundAtStation();

    /**
     * Tests for nil "foundAtStation" element
     */
    boolean isNilFoundAtStation();

    /**
     * True if has "foundAtStation" element
     */
    boolean isSetFoundAtStation();

    /**
     * Sets the "foundAtStation" element
     */
    void setFoundAtStation(java.lang.String foundAtStation);

    /**
     * Sets (as xml) the "foundAtStation" element
     */
    void xsetFoundAtStation(org.apache.xmlbeans.XmlString foundAtStation);

    /**
     * Nils the "foundAtStation" element
     */
    void setNilFoundAtStation();

    /**
     * Unsets the "foundAtStation" element
     */
    void unsetFoundAtStation();

    /**
     * Gets the "founddatetime" element
     */
    java.util.Calendar getFounddatetime();

    /**
     * Gets (as xml) the "founddatetime" element
     */
    org.apache.xmlbeans.XmlDateTime xgetFounddatetime();

    /**
     * Tests for nil "founddatetime" element
     */
    boolean isNilFounddatetime();

    /**
     * True if has "founddatetime" element
     */
    boolean isSetFounddatetime();

    /**
     * Sets the "founddatetime" element
     */
    void setFounddatetime(java.util.Calendar founddatetime);

    /**
     * Sets (as xml) the "founddatetime" element
     */
    void xsetFounddatetime(org.apache.xmlbeans.XmlDateTime founddatetime);

    /**
     * Nils the "founddatetime" element
     */
    void setNilFounddatetime();

    /**
     * Unsets the "founddatetime" element
     */
    void unsetFounddatetime();

    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    public static final class Factory {
        private Factory() {
        } // No instance of this class allowed

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSQOHD newInstance() {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSQOHD) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                              .newInstance(type,
                null);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSQOHD newInstance(
            org.apache.xmlbeans.XmlOptions options) {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSQOHD) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                              .newInstance(type,
                options);
        }

        /** @param xmlAsString the string value to parse */
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSQOHD parse(
            java.lang.String xmlAsString)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSQOHD) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                              .parse(xmlAsString,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSQOHD parse(
            java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSQOHD) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                              .parse(xmlAsString,
                type, options);
        }

        /** @param file the file from which to load an xml document */
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSQOHD parse(
            java.io.File file)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSQOHD) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                              .parse(file,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSQOHD parse(
            java.io.File file, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSQOHD) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                              .parse(file,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSQOHD parse(
            java.net.URL u)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSQOHD) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                              .parse(u,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSQOHD parse(
            java.net.URL u, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSQOHD) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                              .parse(u,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSQOHD parse(
            java.io.InputStream is)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSQOHD) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                              .parse(is,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSQOHD parse(
            java.io.InputStream is, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSQOHD) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                              .parse(is,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSQOHD parse(
            java.io.Reader r)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSQOHD) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                              .parse(r,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSQOHD parse(
            java.io.Reader r, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSQOHD) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                              .parse(r,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSQOHD parse(
            javax.xml.stream.XMLStreamReader sr)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSQOHD) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                              .parse(sr,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSQOHD parse(
            javax.xml.stream.XMLStreamReader sr,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSQOHD) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                              .parse(sr,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSQOHD parse(
            org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSQOHD) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                              .parse(node,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSQOHD parse(
            org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSQOHD) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                              .parse(node,
                type, options);
        }

        /** @deprecated {@link XMLInputStream} */
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSQOHD parse(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSQOHD) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                              .parse(xis,
                type, null);
        }

        /** @deprecated {@link XMLInputStream} */
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSQOHD parse(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSQOHD) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
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
