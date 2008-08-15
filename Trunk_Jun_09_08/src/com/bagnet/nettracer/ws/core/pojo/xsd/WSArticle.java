/*
 * XML Type:  WS_Article
 * Namespace: http://pojo.core.ws.nettracer.bagnet.com/xsd
 * Java type: com.bagnet.nettracer.ws.core.pojo.xsd.WSArticle
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.core.pojo.xsd;


/**
 * An XML WS_Article(@http://pojo.core.ws.nettracer.bagnet.com/xsd).
 *
 * This is a complex type.
 */
public interface WSArticle extends org.apache.xmlbeans.XmlObject {
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType) org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(WSArticle.class.getClassLoader(),
            "schemaorg_apache_xmlbeans.system.s16B510B5563ED0857509A4DB5C2996DF")
                                                                                                                           .resolveHandle("wsarticle0125type");

    /**
     * Gets the "article" element
     */
    java.lang.String getArticle();

    /**
     * Gets (as xml) the "article" element
     */
    org.apache.xmlbeans.XmlString xgetArticle();

    /**
     * Tests for nil "article" element
     */
    boolean isNilArticle();

    /**
     * True if has "article" element
     */
    boolean isSetArticle();

    /**
     * Sets the "article" element
     */
    void setArticle(java.lang.String article);

    /**
     * Sets (as xml) the "article" element
     */
    void xsetArticle(org.apache.xmlbeans.XmlString article);

    /**
     * Nils the "article" element
     */
    void setNilArticle();

    /**
     * Unsets the "article" element
     */
    void unsetArticle();

    /**
     * Gets the "cost" element
     */
    double getCost();

    /**
     * Gets (as xml) the "cost" element
     */
    org.apache.xmlbeans.XmlDouble xgetCost();

    /**
     * True if has "cost" element
     */
    boolean isSetCost();

    /**
     * Sets the "cost" element
     */
    void setCost(double cost);

    /**
     * Sets (as xml) the "cost" element
     */
    void xsetCost(org.apache.xmlbeans.XmlDouble cost);

    /**
     * Unsets the "cost" element
     */
    void unsetCost();

    /**
     * Gets the "currency_ID" element
     */
    java.lang.String getCurrencyID();

    /**
     * Gets (as xml) the "currency_ID" element
     */
    org.apache.xmlbeans.XmlString xgetCurrencyID();

    /**
     * Tests for nil "currency_ID" element
     */
    boolean isNilCurrencyID();

    /**
     * True if has "currency_ID" element
     */
    boolean isSetCurrencyID();

    /**
     * Sets the "currency_ID" element
     */
    void setCurrencyID(java.lang.String currencyID);

    /**
     * Sets (as xml) the "currency_ID" element
     */
    void xsetCurrencyID(org.apache.xmlbeans.XmlString currencyID);

    /**
     * Nils the "currency_ID" element
     */
    void setNilCurrencyID();

    /**
     * Unsets the "currency_ID" element
     */
    void unsetCurrencyID();

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
     * Gets the "purchasedate" element
     */
    java.lang.String getPurchasedate();

    /**
     * Gets (as xml) the "purchasedate" element
     */
    org.apache.xmlbeans.XmlString xgetPurchasedate();

    /**
     * Tests for nil "purchasedate" element
     */
    boolean isNilPurchasedate();

    /**
     * True if has "purchasedate" element
     */
    boolean isSetPurchasedate();

    /**
     * Sets the "purchasedate" element
     */
    void setPurchasedate(java.lang.String purchasedate);

    /**
     * Sets (as xml) the "purchasedate" element
     */
    void xsetPurchasedate(org.apache.xmlbeans.XmlString purchasedate);

    /**
     * Nils the "purchasedate" element
     */
    void setNilPurchasedate();

    /**
     * Unsets the "purchasedate" element
     */
    void unsetPurchasedate();

    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    public static final class Factory {
        private Factory() {
        } // No instance of this class allowed

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSArticle newInstance() {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSArticle) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                 .newInstance(type,
                null);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSArticle newInstance(
            org.apache.xmlbeans.XmlOptions options) {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSArticle) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                 .newInstance(type,
                options);
        }

        /** @param xmlAsString the string value to parse */
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSArticle parse(
            java.lang.String xmlAsString)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSArticle) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                 .parse(xmlAsString,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSArticle parse(
            java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSArticle) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                 .parse(xmlAsString,
                type, options);
        }

        /** @param file the file from which to load an xml document */
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSArticle parse(
            java.io.File file)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSArticle) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                 .parse(file,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSArticle parse(
            java.io.File file, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSArticle) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                 .parse(file,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSArticle parse(
            java.net.URL u)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSArticle) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                 .parse(u,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSArticle parse(
            java.net.URL u, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSArticle) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                 .parse(u,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSArticle parse(
            java.io.InputStream is)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSArticle) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                 .parse(is,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSArticle parse(
            java.io.InputStream is, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSArticle) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                 .parse(is,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSArticle parse(
            java.io.Reader r)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSArticle) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                 .parse(r,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSArticle parse(
            java.io.Reader r, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSArticle) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                 .parse(r,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSArticle parse(
            javax.xml.stream.XMLStreamReader sr)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSArticle) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                 .parse(sr,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSArticle parse(
            javax.xml.stream.XMLStreamReader sr,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSArticle) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                 .parse(sr,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSArticle parse(
            org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSArticle) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                 .parse(node,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSArticle parse(
            org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSArticle) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                 .parse(node,
                type, options);
        }

        /** @deprecated {@link XMLInputStream} */
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSArticle parse(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSArticle) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                 .parse(xis,
                type, null);
        }

        /** @deprecated {@link XMLInputStream} */
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSArticle parse(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSArticle) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
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
