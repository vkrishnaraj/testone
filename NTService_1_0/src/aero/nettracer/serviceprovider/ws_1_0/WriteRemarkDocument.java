/*
 * An XML document type.
 * Localname: writeRemark
 * Namespace: http://ws_1_0.serviceprovider.nettracer.aero
 * Java type: aero.nettracer.serviceprovider.ws_1_0.WriteRemarkDocument
 *
 * Automatically generated - do not modify.
 */
package aero.nettracer.serviceprovider.ws_1_0;


/**
 * A document containing one writeRemark(@http://ws_1_0.serviceprovider.nettracer.aero) element.
 *
 * This is a complex type.
 */
public interface WriteRemarkDocument extends org.apache.xmlbeans.XmlObject {
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType) org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(WriteRemarkDocument.class.getClassLoader(),
            "schemaorg_apache_xmlbeans.system.sBE8218F01D2D304949F3BBA6C8907854")
                                                                                                                           .resolveHandle("writeremarkf584doctype");

    /**
     * Gets the "writeRemark" element
     */
    aero.nettracer.serviceprovider.ws_1_0.WriteRemarkDocument.WriteRemark getWriteRemark();

    /**
     * Sets the "writeRemark" element
     */
    void setWriteRemark(
        aero.nettracer.serviceprovider.ws_1_0.WriteRemarkDocument.WriteRemark writeRemark);

    /**
     * Appends and returns a new empty "writeRemark" element
     */
    aero.nettracer.serviceprovider.ws_1_0.WriteRemarkDocument.WriteRemark addNewWriteRemark();

    /**
     * An XML writeRemark(@http://ws_1_0.serviceprovider.nettracer.aero).
     *
     * This is a complex type.
     */
    public interface WriteRemark extends org.apache.xmlbeans.XmlObject {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType) org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(WriteRemark.class.getClassLoader(),
                "schemaorg_apache_xmlbeans.system.sBE8218F01D2D304949F3BBA6C8907854")
                                                                                                                               .resolveHandle("writeremarkc1f9elemtype");

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
         * A factory class with static methods for creating instances
         * of this type.
         */
        public static final class Factory {
            private Factory() {
            } // No instance of this class allowed

            public static aero.nettracer.serviceprovider.ws_1_0.WriteRemarkDocument.WriteRemark newInstance() {
                return (aero.nettracer.serviceprovider.ws_1_0.WriteRemarkDocument.WriteRemark) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                           .newInstance(type,
                    null);
            }

            public static aero.nettracer.serviceprovider.ws_1_0.WriteRemarkDocument.WriteRemark newInstance(
                org.apache.xmlbeans.XmlOptions options) {
                return (aero.nettracer.serviceprovider.ws_1_0.WriteRemarkDocument.WriteRemark) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
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

        public static aero.nettracer.serviceprovider.ws_1_0.WriteRemarkDocument newInstance() {
            return (aero.nettracer.serviceprovider.ws_1_0.WriteRemarkDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                           .newInstance(type,
                null);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.WriteRemarkDocument newInstance(
            org.apache.xmlbeans.XmlOptions options) {
            return (aero.nettracer.serviceprovider.ws_1_0.WriteRemarkDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                           .newInstance(type,
                options);
        }

        /** @param xmlAsString the string value to parse */
        public static aero.nettracer.serviceprovider.ws_1_0.WriteRemarkDocument parse(
            java.lang.String xmlAsString)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.ws_1_0.WriteRemarkDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                           .parse(xmlAsString,
                type, null);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.WriteRemarkDocument parse(
            java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.ws_1_0.WriteRemarkDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                           .parse(xmlAsString,
                type, options);
        }

        /** @param file the file from which to load an xml document */
        public static aero.nettracer.serviceprovider.ws_1_0.WriteRemarkDocument parse(
            java.io.File file)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.ws_1_0.WriteRemarkDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                           .parse(file,
                type, null);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.WriteRemarkDocument parse(
            java.io.File file, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.ws_1_0.WriteRemarkDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                           .parse(file,
                type, options);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.WriteRemarkDocument parse(
            java.net.URL u)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.ws_1_0.WriteRemarkDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                           .parse(u,
                type, null);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.WriteRemarkDocument parse(
            java.net.URL u, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.ws_1_0.WriteRemarkDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                           .parse(u,
                type, options);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.WriteRemarkDocument parse(
            java.io.InputStream is)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.ws_1_0.WriteRemarkDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                           .parse(is,
                type, null);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.WriteRemarkDocument parse(
            java.io.InputStream is, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.ws_1_0.WriteRemarkDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                           .parse(is,
                type, options);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.WriteRemarkDocument parse(
            java.io.Reader r)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.ws_1_0.WriteRemarkDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                           .parse(r,
                type, null);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.WriteRemarkDocument parse(
            java.io.Reader r, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.ws_1_0.WriteRemarkDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                           .parse(r,
                type, options);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.WriteRemarkDocument parse(
            javax.xml.stream.XMLStreamReader sr)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.ws_1_0.WriteRemarkDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                           .parse(sr,
                type, null);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.WriteRemarkDocument parse(
            javax.xml.stream.XMLStreamReader sr,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.ws_1_0.WriteRemarkDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                           .parse(sr,
                type, options);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.WriteRemarkDocument parse(
            org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.ws_1_0.WriteRemarkDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                           .parse(node,
                type, null);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.WriteRemarkDocument parse(
            org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.ws_1_0.WriteRemarkDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                           .parse(node,
                type, options);
        }

        /** @deprecated {@link XMLInputStream} */
        public static aero.nettracer.serviceprovider.ws_1_0.WriteRemarkDocument parse(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (aero.nettracer.serviceprovider.ws_1_0.WriteRemarkDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                           .parse(xis,
                type, null);
        }

        /** @deprecated {@link XMLInputStream} */
        public static aero.nettracer.serviceprovider.ws_1_0.WriteRemarkDocument parse(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (aero.nettracer.serviceprovider.ws_1_0.WriteRemarkDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
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
