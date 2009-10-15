/*
 * An XML document type.
 * Localname: forwardOhd
 * Namespace: http://wt_1_0.serviceprovider.nettracer.aero
 * Java type: aero.nettracer.serviceprovider.wt_1_0.ForwardOhdDocument
 *
 * Automatically generated - do not modify.
 */
package aero.nettracer.serviceprovider.wt_1_0;


/**
 * A document containing one forwardOhd(@http://wt_1_0.serviceprovider.nettracer.aero) element.
 *
 * This is a complex type.
 */
public interface ForwardOhdDocument extends org.apache.xmlbeans.XmlObject {
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType) org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(ForwardOhdDocument.class.getClassLoader(),
            "schemaorg_apache_xmlbeans.system.sBCED675595897F865A233256B59F19BA")
                                                                                                                           .resolveHandle("forwardohdf6cadoctype");

    /**
     * Gets the "forwardOhd" element
     */
    aero.nettracer.serviceprovider.wt_1_0.ForwardOhdDocument.ForwardOhd getForwardOhd();

    /**
     * Sets the "forwardOhd" element
     */
    void setForwardOhd(
        aero.nettracer.serviceprovider.wt_1_0.ForwardOhdDocument.ForwardOhd forwardOhd);

    /**
     * Appends and returns a new empty "forwardOhd" element
     */
    aero.nettracer.serviceprovider.wt_1_0.ForwardOhdDocument.ForwardOhd addNewForwardOhd();

    /**
     * An XML forwardOhd(@http://wt_1_0.serviceprovider.nettracer.aero).
     *
     * This is a complex type.
     */
    public interface ForwardOhd extends org.apache.xmlbeans.XmlObject {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType) org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(ForwardOhd.class.getClassLoader(),
                "schemaorg_apache_xmlbeans.system.sBCED675595897F865A233256B59F19BA")
                                                                                                                               .resolveHandle("forwardohd1b8celemtype");

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
         * Gets the "data" element
         */
        aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardOhd getData();

        /**
         * Tests for nil "data" element
         */
        boolean isNilData();

        /**
         * True if has "data" element
         */
        boolean isSetData();

        /**
         * Sets the "data" element
         */
        void setData(
            aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardOhd data);

        /**
         * Appends and returns a new empty "data" element
         */
        aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardOhd addNewData();

        /**
         * Nils the "data" element
         */
        void setNilData();

        /**
         * Unsets the "data" element
         */
        void unsetData();

        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        public static final class Factory {
            private Factory() {
            } // No instance of this class allowed

            public static aero.nettracer.serviceprovider.wt_1_0.ForwardOhdDocument.ForwardOhd newInstance() {
                return (aero.nettracer.serviceprovider.wt_1_0.ForwardOhdDocument.ForwardOhd) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                         .newInstance(type,
                    null);
            }

            public static aero.nettracer.serviceprovider.wt_1_0.ForwardOhdDocument.ForwardOhd newInstance(
                org.apache.xmlbeans.XmlOptions options) {
                return (aero.nettracer.serviceprovider.wt_1_0.ForwardOhdDocument.ForwardOhd) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
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

        public static aero.nettracer.serviceprovider.wt_1_0.ForwardOhdDocument newInstance() {
            return (aero.nettracer.serviceprovider.wt_1_0.ForwardOhdDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                          .newInstance(type,
                null);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.ForwardOhdDocument newInstance(
            org.apache.xmlbeans.XmlOptions options) {
            return (aero.nettracer.serviceprovider.wt_1_0.ForwardOhdDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                          .newInstance(type,
                options);
        }

        /** @param xmlAsString the string value to parse */
        public static aero.nettracer.serviceprovider.wt_1_0.ForwardOhdDocument parse(
            java.lang.String xmlAsString)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.wt_1_0.ForwardOhdDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                          .parse(xmlAsString,
                type, null);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.ForwardOhdDocument parse(
            java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.wt_1_0.ForwardOhdDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                          .parse(xmlAsString,
                type, options);
        }

        /** @param file the file from which to load an xml document */
        public static aero.nettracer.serviceprovider.wt_1_0.ForwardOhdDocument parse(
            java.io.File file)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.wt_1_0.ForwardOhdDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                          .parse(file,
                type, null);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.ForwardOhdDocument parse(
            java.io.File file, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.wt_1_0.ForwardOhdDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                          .parse(file,
                type, options);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.ForwardOhdDocument parse(
            java.net.URL u)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.wt_1_0.ForwardOhdDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                          .parse(u,
                type, null);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.ForwardOhdDocument parse(
            java.net.URL u, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.wt_1_0.ForwardOhdDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                          .parse(u,
                type, options);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.ForwardOhdDocument parse(
            java.io.InputStream is)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.wt_1_0.ForwardOhdDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                          .parse(is,
                type, null);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.ForwardOhdDocument parse(
            java.io.InputStream is, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.wt_1_0.ForwardOhdDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                          .parse(is,
                type, options);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.ForwardOhdDocument parse(
            java.io.Reader r)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.wt_1_0.ForwardOhdDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                          .parse(r,
                type, null);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.ForwardOhdDocument parse(
            java.io.Reader r, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.wt_1_0.ForwardOhdDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                          .parse(r,
                type, options);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.ForwardOhdDocument parse(
            javax.xml.stream.XMLStreamReader sr)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.wt_1_0.ForwardOhdDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                          .parse(sr,
                type, null);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.ForwardOhdDocument parse(
            javax.xml.stream.XMLStreamReader sr,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.wt_1_0.ForwardOhdDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                          .parse(sr,
                type, options);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.ForwardOhdDocument parse(
            org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.wt_1_0.ForwardOhdDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                          .parse(node,
                type, null);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.ForwardOhdDocument parse(
            org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.wt_1_0.ForwardOhdDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                          .parse(node,
                type, options);
        }

        /** @deprecated {@link XMLInputStream} */
        public static aero.nettracer.serviceprovider.wt_1_0.ForwardOhdDocument parse(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (aero.nettracer.serviceprovider.wt_1_0.ForwardOhdDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                          .parse(xis,
                type, null);
        }

        /** @deprecated {@link XMLInputStream} */
        public static aero.nettracer.serviceprovider.wt_1_0.ForwardOhdDocument parse(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (aero.nettracer.serviceprovider.wt_1_0.ForwardOhdDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
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
