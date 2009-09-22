/*
 * An XML document type.
 * Localname: getOsiContents
 * Namespace: http://ws_1_0.serviceprovider.nettracer.aero
 * Java type: aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsDocument
 *
 * Automatically generated - do not modify.
 */
package aero.nettracer.serviceprovider.ws_1_0;


/**
 * A document containing one getOsiContents(@http://ws_1_0.serviceprovider.nettracer.aero) element.
 *
 * This is a complex type.
 */
public interface GetOsiContentsDocument extends org.apache.xmlbeans.XmlObject {
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType) org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(GetOsiContentsDocument.class.getClassLoader(),
            "schemaorg_apache_xmlbeans.system.sEFE6345392E523A6707B3AC577A384C3")
                                                                                                                           .resolveHandle("getosicontentse868doctype");

    /**
     * Gets the "getOsiContents" element
     */
    aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsDocument.GetOsiContents getGetOsiContents();

    /**
     * Sets the "getOsiContents" element
     */
    void setGetOsiContents(
        aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsDocument.GetOsiContents getOsiContents);

    /**
     * Appends and returns a new empty "getOsiContents" element
     */
    aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsDocument.GetOsiContents addNewGetOsiContents();

    /**
     * An XML getOsiContents(@http://ws_1_0.serviceprovider.nettracer.aero).
     *
     * This is a complex type.
     */
    public interface GetOsiContents extends org.apache.xmlbeans.XmlObject {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType) org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(GetOsiContents.class.getClassLoader(),
                "schemaorg_apache_xmlbeans.system.sEFE6345392E523A6707B3AC577A384C3")
                                                                                                                               .resolveHandle("getosicontentsa44delemtype");

        /**
         * Gets the "header" element
         */
        aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader getHeader();

        /**
         * Tests for nil "header" element
         */
        boolean isNilHeader();

        /**
         * True if has "header" element
         */
        boolean isSetHeader();

        /**
         * Sets the "header" element
         */
        void setHeader(
            aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader header);

        /**
         * Appends and returns a new empty "header" element
         */
        aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader addNewHeader();

        /**
         * Nils the "header" element
         */
        void setNilHeader();

        /**
         * Unsets the "header" element
         */
        void unsetHeader();

        /**
         * Gets the "pnr" element
         */
        java.lang.String getPnr();

        /**
         * Gets (as xml) the "pnr" element
         */
        org.apache.xmlbeans.XmlString xgetPnr();

        /**
         * Tests for nil "pnr" element
         */
        boolean isNilPnr();

        /**
         * True if has "pnr" element
         */
        boolean isSetPnr();

        /**
         * Sets the "pnr" element
         */
        void setPnr(java.lang.String pnr);

        /**
         * Sets (as xml) the "pnr" element
         */
        void xsetPnr(org.apache.xmlbeans.XmlString pnr);

        /**
         * Nils the "pnr" element
         */
        void setNilPnr();

        /**
         * Unsets the "pnr" element
         */
        void unsetPnr();

        /**
         * Gets the "bagTag" element
         */
        java.lang.String getBagTag();

        /**
         * Gets (as xml) the "bagTag" element
         */
        org.apache.xmlbeans.XmlString xgetBagTag();

        /**
         * Tests for nil "bagTag" element
         */
        boolean isNilBagTag();

        /**
         * True if has "bagTag" element
         */
        boolean isSetBagTag();

        /**
         * Sets the "bagTag" element
         */
        void setBagTag(java.lang.String bagTag);

        /**
         * Sets (as xml) the "bagTag" element
         */
        void xsetBagTag(org.apache.xmlbeans.XmlString bagTag);

        /**
         * Nils the "bagTag" element
         */
        void setNilBagTag();

        /**
         * Unsets the "bagTag" element
         */
        void unsetBagTag();

        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        public static final class Factory {
            private Factory() {
            } // No instance of this class allowed

            public static aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsDocument.GetOsiContents newInstance() {
                return (aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsDocument.GetOsiContents) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                                 .newInstance(type,
                    null);
            }

            public static aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsDocument.GetOsiContents newInstance(
                org.apache.xmlbeans.XmlOptions options) {
                return (aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsDocument.GetOsiContents) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                                 .newInstance(type,
                    options);
            }
        }
    }

    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    public static final class Factory {
        private Factory() {
        } // No instance of this class allowed

        public static aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsDocument newInstance() {
            return (aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                              .newInstance(type,
                null);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsDocument newInstance(
            org.apache.xmlbeans.XmlOptions options) {
            return (aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                              .newInstance(type,
                options);
        }

        /** @param xmlAsString the string value to parse */
        public static aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsDocument parse(
            java.lang.String xmlAsString)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                              .parse(xmlAsString,
                type, null);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsDocument parse(
            java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                              .parse(xmlAsString,
                type, options);
        }

        /** @param file the file from which to load an xml document */
        public static aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsDocument parse(
            java.io.File file)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                              .parse(file,
                type, null);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsDocument parse(
            java.io.File file, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                              .parse(file,
                type, options);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsDocument parse(
            java.net.URL u)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                              .parse(u,
                type, null);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsDocument parse(
            java.net.URL u, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                              .parse(u,
                type, options);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsDocument parse(
            java.io.InputStream is)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                              .parse(is,
                type, null);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsDocument parse(
            java.io.InputStream is, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                              .parse(is,
                type, options);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsDocument parse(
            java.io.Reader r)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                              .parse(r,
                type, null);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsDocument parse(
            java.io.Reader r, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                              .parse(r,
                type, options);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsDocument parse(
            javax.xml.stream.XMLStreamReader sr)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                              .parse(sr,
                type, null);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsDocument parse(
            javax.xml.stream.XMLStreamReader sr,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                              .parse(sr,
                type, options);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsDocument parse(
            org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                              .parse(node,
                type, null);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsDocument parse(
            org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                              .parse(node,
                type, options);
        }

        /** @deprecated {@link XMLInputStream} */
        public static aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsDocument parse(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                              .parse(xis,
                type, null);
        }

        /** @deprecated {@link XMLInputStream} */
        public static aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsDocument parse(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
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
