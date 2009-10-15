/*
 * An XML document type.
 * Localname: amendAhl
 * Namespace: http://wt_1_0.serviceprovider.nettracer.aero
 * Java type: aero.nettracer.serviceprovider.wt_1_0.AmendAhlDocument
 *
 * Automatically generated - do not modify.
 */
package aero.nettracer.serviceprovider.wt_1_0;


/**
 * A document containing one amendAhl(@http://wt_1_0.serviceprovider.nettracer.aero) element.
 *
 * This is a complex type.
 */
public interface AmendAhlDocument extends org.apache.xmlbeans.XmlObject {
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType) org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(AmendAhlDocument.class.getClassLoader(),
            "schemaorg_apache_xmlbeans.system.sBCED675595897F865A233256B59F19BA")
                                                                                                                           .resolveHandle("amendahl65badoctype");

    /**
     * Gets the "amendAhl" element
     */
    aero.nettracer.serviceprovider.wt_1_0.AmendAhlDocument.AmendAhl getAmendAhl();

    /**
     * Sets the "amendAhl" element
     */
    void setAmendAhl(
        aero.nettracer.serviceprovider.wt_1_0.AmendAhlDocument.AmendAhl amendAhl);

    /**
     * Appends and returns a new empty "amendAhl" element
     */
    aero.nettracer.serviceprovider.wt_1_0.AmendAhlDocument.AmendAhl addNewAmendAhl();

    /**
     * An XML amendAhl(@http://wt_1_0.serviceprovider.nettracer.aero).
     *
     * This is a complex type.
     */
    public interface AmendAhl extends org.apache.xmlbeans.XmlObject {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType) org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(AmendAhl.class.getClassLoader(),
                "schemaorg_apache_xmlbeans.system.sBCED675595897F865A233256B59F19BA")
                                                                                                                               .resolveHandle("amendahl39ccelemtype");

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
         * A factory class with static methods for creating instances
         * of this type.
         */
        public static final class Factory {
            private Factory() {
            } // No instance of this class allowed

            public static aero.nettracer.serviceprovider.wt_1_0.AmendAhlDocument.AmendAhl newInstance() {
                return (aero.nettracer.serviceprovider.wt_1_0.AmendAhlDocument.AmendAhl) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                     .newInstance(type,
                    null);
            }

            public static aero.nettracer.serviceprovider.wt_1_0.AmendAhlDocument.AmendAhl newInstance(
                org.apache.xmlbeans.XmlOptions options) {
                return (aero.nettracer.serviceprovider.wt_1_0.AmendAhlDocument.AmendAhl) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
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

        public static aero.nettracer.serviceprovider.wt_1_0.AmendAhlDocument newInstance() {
            return (aero.nettracer.serviceprovider.wt_1_0.AmendAhlDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                        .newInstance(type,
                null);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.AmendAhlDocument newInstance(
            org.apache.xmlbeans.XmlOptions options) {
            return (aero.nettracer.serviceprovider.wt_1_0.AmendAhlDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                        .newInstance(type,
                options);
        }

        /** @param xmlAsString the string value to parse */
        public static aero.nettracer.serviceprovider.wt_1_0.AmendAhlDocument parse(
            java.lang.String xmlAsString)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.wt_1_0.AmendAhlDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                        .parse(xmlAsString,
                type, null);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.AmendAhlDocument parse(
            java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.wt_1_0.AmendAhlDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                        .parse(xmlAsString,
                type, options);
        }

        /** @param file the file from which to load an xml document */
        public static aero.nettracer.serviceprovider.wt_1_0.AmendAhlDocument parse(
            java.io.File file)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.wt_1_0.AmendAhlDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                        .parse(file,
                type, null);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.AmendAhlDocument parse(
            java.io.File file, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.wt_1_0.AmendAhlDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                        .parse(file,
                type, options);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.AmendAhlDocument parse(
            java.net.URL u)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.wt_1_0.AmendAhlDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                        .parse(u,
                type, null);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.AmendAhlDocument parse(
            java.net.URL u, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.wt_1_0.AmendAhlDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                        .parse(u,
                type, options);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.AmendAhlDocument parse(
            java.io.InputStream is)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.wt_1_0.AmendAhlDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                        .parse(is,
                type, null);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.AmendAhlDocument parse(
            java.io.InputStream is, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.wt_1_0.AmendAhlDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                        .parse(is,
                type, options);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.AmendAhlDocument parse(
            java.io.Reader r)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.wt_1_0.AmendAhlDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                        .parse(r,
                type, null);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.AmendAhlDocument parse(
            java.io.Reader r, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.wt_1_0.AmendAhlDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                        .parse(r,
                type, options);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.AmendAhlDocument parse(
            javax.xml.stream.XMLStreamReader sr)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.wt_1_0.AmendAhlDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                        .parse(sr,
                type, null);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.AmendAhlDocument parse(
            javax.xml.stream.XMLStreamReader sr,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.wt_1_0.AmendAhlDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                        .parse(sr,
                type, options);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.AmendAhlDocument parse(
            org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.wt_1_0.AmendAhlDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                        .parse(node,
                type, null);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.AmendAhlDocument parse(
            org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.wt_1_0.AmendAhlDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                        .parse(node,
                type, options);
        }

        /** @deprecated {@link XMLInputStream} */
        public static aero.nettracer.serviceprovider.wt_1_0.AmendAhlDocument parse(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (aero.nettracer.serviceprovider.wt_1_0.AmendAhlDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                        .parse(xis,
                type, null);
        }

        /** @deprecated {@link XMLInputStream} */
        public static aero.nettracer.serviceprovider.wt_1_0.AmendAhlDocument parse(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (aero.nettracer.serviceprovider.wt_1_0.AmendAhlDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
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
