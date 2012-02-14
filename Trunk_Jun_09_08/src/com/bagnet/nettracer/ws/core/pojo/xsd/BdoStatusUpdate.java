/*
 * XML Type:  BdoStatusUpdate
 * Namespace: http://pojo.core.ws.nettracer.bagnet.com/xsd
 * Java type: com.bagnet.nettracer.ws.core.pojo.xsd.BdoStatusUpdate
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.core.pojo.xsd;


/**
 * An XML BdoStatusUpdate(@http://pojo.core.ws.nettracer.bagnet.com/xsd).
 *
 * This is a complex type.
 */
public interface BdoStatusUpdate extends org.apache.xmlbeans.XmlObject {
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType) org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(BdoStatusUpdate.class.getClassLoader(),
            "schemaorg_apache_xmlbeans.system.sB1D181FCD070B8C28352F412287BB191")
                                                                                                                           .resolveHandle("bdostatusupdate18e2type");

    /**
     * Gets the "bdo" element
     */
    int getBdo();

    /**
     * Gets (as xml) the "bdo" element
     */
    org.apache.xmlbeans.XmlInt xgetBdo();

    /**
     * True if has "bdo" element
     */
    boolean isSetBdo();

    /**
     * Sets the "bdo" element
     */
    void setBdo(int bdo);

    /**
     * Sets (as xml) the "bdo" element
     */
    void xsetBdo(org.apache.xmlbeans.XmlInt bdo);

    /**
     * Unsets the "bdo" element
     */
    void unsetBdo();

    /**
     * Gets the "remarks" element
     */
    java.lang.String getRemarks();

    /**
     * Gets (as xml) the "remarks" element
     */
    org.apache.xmlbeans.XmlString xgetRemarks();

    /**
     * Tests for nil "remarks" element
     */
    boolean isNilRemarks();

    /**
     * True if has "remarks" element
     */
    boolean isSetRemarks();

    /**
     * Sets the "remarks" element
     */
    void setRemarks(java.lang.String remarks);

    /**
     * Sets (as xml) the "remarks" element
     */
    void xsetRemarks(org.apache.xmlbeans.XmlString remarks);

    /**
     * Nils the "remarks" element
     */
    void setNilRemarks();

    /**
     * Unsets the "remarks" element
     */
    void unsetRemarks();

    /**
     * Gets the "status" element
     */
    java.lang.String getStatus();

    /**
     * Gets (as xml) the "status" element
     */
    org.apache.xmlbeans.XmlString xgetStatus();

    /**
     * Tests for nil "status" element
     */
    boolean isNilStatus();

    /**
     * True if has "status" element
     */
    boolean isSetStatus();

    /**
     * Sets the "status" element
     */
    void setStatus(java.lang.String status);

    /**
     * Sets (as xml) the "status" element
     */
    void xsetStatus(org.apache.xmlbeans.XmlString status);

    /**
     * Nils the "status" element
     */
    void setNilStatus();

    /**
     * Unsets the "status" element
     */
    void unsetStatus();

    /**
     * Gets the "statusDateTime" element
     */
    java.util.Calendar getStatusDateTime();

    /**
     * Gets (as xml) the "statusDateTime" element
     */
    org.apache.xmlbeans.XmlDateTime xgetStatusDateTime();

    /**
     * Tests for nil "statusDateTime" element
     */
    boolean isNilStatusDateTime();

    /**
     * True if has "statusDateTime" element
     */
    boolean isSetStatusDateTime();

    /**
     * Sets the "statusDateTime" element
     */
    void setStatusDateTime(java.util.Calendar statusDateTime);

    /**
     * Sets (as xml) the "statusDateTime" element
     */
    void xsetStatusDateTime(org.apache.xmlbeans.XmlDateTime statusDateTime);

    /**
     * Nils the "statusDateTime" element
     */
    void setNilStatusDateTime();

    /**
     * Unsets the "statusDateTime" element
     */
    void unsetStatusDateTime();

    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    public static final class Factory {
        private Factory() {
        } // No instance of this class allowed

        public static com.bagnet.nettracer.ws.core.pojo.xsd.BdoStatusUpdate newInstance() {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.BdoStatusUpdate) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                       .newInstance(type,
                null);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.BdoStatusUpdate newInstance(
            org.apache.xmlbeans.XmlOptions options) {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.BdoStatusUpdate) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                       .newInstance(type,
                options);
        }

        /** @param xmlAsString the string value to parse */
        public static com.bagnet.nettracer.ws.core.pojo.xsd.BdoStatusUpdate parse(
            java.lang.String xmlAsString)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.BdoStatusUpdate) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                       .parse(xmlAsString,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.BdoStatusUpdate parse(
            java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.BdoStatusUpdate) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                       .parse(xmlAsString,
                type, options);
        }

        /** @param file the file from which to load an xml document */
        public static com.bagnet.nettracer.ws.core.pojo.xsd.BdoStatusUpdate parse(
            java.io.File file)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.BdoStatusUpdate) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                       .parse(file,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.BdoStatusUpdate parse(
            java.io.File file, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.BdoStatusUpdate) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                       .parse(file,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.BdoStatusUpdate parse(
            java.net.URL u)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.BdoStatusUpdate) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                       .parse(u,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.BdoStatusUpdate parse(
            java.net.URL u, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.BdoStatusUpdate) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                       .parse(u,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.BdoStatusUpdate parse(
            java.io.InputStream is)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.BdoStatusUpdate) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                       .parse(is,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.BdoStatusUpdate parse(
            java.io.InputStream is, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.BdoStatusUpdate) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                       .parse(is,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.BdoStatusUpdate parse(
            java.io.Reader r)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.BdoStatusUpdate) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                       .parse(r,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.BdoStatusUpdate parse(
            java.io.Reader r, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.BdoStatusUpdate) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                       .parse(r,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.BdoStatusUpdate parse(
            javax.xml.stream.XMLStreamReader sr)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.BdoStatusUpdate) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                       .parse(sr,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.BdoStatusUpdate parse(
            javax.xml.stream.XMLStreamReader sr,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.BdoStatusUpdate) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                       .parse(sr,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.BdoStatusUpdate parse(
            org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.BdoStatusUpdate) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                       .parse(node,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.BdoStatusUpdate parse(
            org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.BdoStatusUpdate) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                       .parse(node,
                type, options);
        }

        /** @deprecated {@link XMLInputStream} */
        public static com.bagnet.nettracer.ws.core.pojo.xsd.BdoStatusUpdate parse(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.BdoStatusUpdate) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                       .parse(xis,
                type, null);
        }

        /** @deprecated {@link XMLInputStream} */
        public static com.bagnet.nettracer.ws.core.pojo.xsd.BdoStatusUpdate parse(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.BdoStatusUpdate) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
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
