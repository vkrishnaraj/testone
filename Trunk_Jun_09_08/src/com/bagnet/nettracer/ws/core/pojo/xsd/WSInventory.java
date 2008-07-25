/*
 * XML Type:  WS_Inventory
 * Namespace: http://pojo.core.ws.nettracer.bagnet.com/xsd
 * Java type: com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.core.pojo.xsd;


/**
 * An XML WS_Inventory(@http://pojo.core.ws.nettracer.bagnet.com/xsd).
 *
 * This is a complex type.
 */
public interface WSInventory extends org.apache.xmlbeans.XmlObject {
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType) org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(WSInventory.class.getClassLoader(),
            "schemaorg_apache_xmlbeans.system.s2182E89F12DCED7BC68D5ADCA1063463")
                                                                                                                           .resolveHandle("wsinventoryc3fftype");

    /**
     * Gets the "category" element
     */
    java.lang.String getCategory();

    /**
     * Gets (as xml) the "category" element
     */
    org.apache.xmlbeans.XmlString xgetCategory();

    /**
     * Tests for nil "category" element
     */
    boolean isNilCategory();

    /**
     * True if has "category" element
     */
    boolean isSetCategory();

    /**
     * Sets the "category" element
     */
    void setCategory(java.lang.String category);

    /**
     * Sets (as xml) the "category" element
     */
    void xsetCategory(org.apache.xmlbeans.XmlString category);

    /**
     * Nils the "category" element
     */
    void setNilCategory();

    /**
     * Unsets the "category" element
     */
    void unsetCategory();

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

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory newInstance() {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                   .newInstance(type,
                null);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory newInstance(
            org.apache.xmlbeans.XmlOptions options) {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                   .newInstance(type,
                options);
        }

        /** @param xmlAsString the string value to parse */
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory parse(
            java.lang.String xmlAsString)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                   .parse(xmlAsString,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory parse(
            java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                   .parse(xmlAsString,
                type, options);
        }

        /** @param file the file from which to load an xml document */
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory parse(
            java.io.File file)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                   .parse(file,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory parse(
            java.io.File file, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                   .parse(file,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory parse(
            java.net.URL u)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                   .parse(u,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory parse(
            java.net.URL u, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                   .parse(u,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory parse(
            java.io.InputStream is)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                   .parse(is,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory parse(
            java.io.InputStream is, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                   .parse(is,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory parse(
            java.io.Reader r)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                   .parse(r,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory parse(
            java.io.Reader r, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                   .parse(r,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory parse(
            javax.xml.stream.XMLStreamReader sr)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                   .parse(sr,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory parse(
            javax.xml.stream.XMLStreamReader sr,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                   .parse(sr,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory parse(
            org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                   .parse(node,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory parse(
            org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                   .parse(node,
                type, options);
        }

        /** @deprecated {@link XMLInputStream} */
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory parse(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                   .parse(xis,
                type, null);
        }

        /** @deprecated {@link XMLInputStream} */
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory parse(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
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
