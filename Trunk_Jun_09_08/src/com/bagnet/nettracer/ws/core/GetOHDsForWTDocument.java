/*
 * An XML document type.
 * Localname: getOHDsForWT
 * Namespace: http://core.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.core.GetOHDsForWTDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.core;

import org.apache.xmlbeans.xml.stream.XMLInputStream;


/**
 * A document containing one getOHDsForWT(@http://core.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public interface GetOHDsForWTDocument extends org.apache.xmlbeans.XmlObject {
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType) org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(GetOHDsForWTDocument.class.getClassLoader(),
            "schemaorg_apache_xmlbeans.system.s51D0044A072EEB5AF0F7D86C8EBC33B8")
                                                                                                                           .resolveHandle("getohdsforwtc8a2doctype");

    /**
     * Gets the "getOHDsForWT" element
     */
    com.bagnet.nettracer.ws.core.GetOHDsForWTDocument.GetOHDsForWT getGetOHDsForWT();

    /**
     * Sets the "getOHDsForWT" element
     */
    void setGetOHDsForWT(
        com.bagnet.nettracer.ws.core.GetOHDsForWTDocument.GetOHDsForWT getOHDsForWT);

    /**
     * Appends and returns a new empty "getOHDsForWT" element
     */
    com.bagnet.nettracer.ws.core.GetOHDsForWTDocument.GetOHDsForWT addNewGetOHDsForWT();

    /**
     * An XML getOHDsForWT(@http://core.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public interface GetOHDsForWT extends org.apache.xmlbeans.XmlObject {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType) org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(GetOHDsForWT.class.getClassLoader(),
                "schemaorg_apache_xmlbeans.system.s51D0044A072EEB5AF0F7D86C8EBC33B8")
                                                                                                                               .resolveHandle("getohdsforwtf61eelemtype");

        /**
         * Gets the "companycode" element
         */
        java.lang.String getCompanycode();

        /**
         * Gets (as xml) the "companycode" element
         */
        org.apache.xmlbeans.XmlString xgetCompanycode();

        /**
         * Tests for nil "companycode" element
         */
        boolean isNilCompanycode();

        /**
         * True if has "companycode" element
         */
        boolean isSetCompanycode();

        /**
         * Sets the "companycode" element
         */
        void setCompanycode(java.lang.String companycode);

        /**
         * Sets (as xml) the "companycode" element
         */
        void xsetCompanycode(org.apache.xmlbeans.XmlString companycode);

        /**
         * Nils the "companycode" element
         */
        void setNilCompanycode();

        /**
         * Unsets the "companycode" element
         */
        void unsetCompanycode();

        /**
         * Gets the "session_id" element
         */
        java.lang.String getSessionId();

        /**
         * Gets (as xml) the "session_id" element
         */
        org.apache.xmlbeans.XmlString xgetSessionId();

        /**
         * Tests for nil "session_id" element
         */
        boolean isNilSessionId();

        /**
         * True if has "session_id" element
         */
        boolean isSetSessionId();

        /**
         * Sets the "session_id" element
         */
        void setSessionId(java.lang.String sessionId);

        /**
         * Sets (as xml) the "session_id" element
         */
        void xsetSessionId(org.apache.xmlbeans.XmlString sessionId);

        /**
         * Nils the "session_id" element
         */
        void setNilSessionId();

        /**
         * Unsets the "session_id" element
         */
        void unsetSessionId();

        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        public static final class Factory {
            private Factory() {
            } // No instance of this class allowed

            public static com.bagnet.nettracer.ws.core.GetOHDsForWTDocument.GetOHDsForWT newInstance() {
                return (com.bagnet.nettracer.ws.core.GetOHDsForWTDocument.GetOHDsForWT) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                    .newInstance(type,
                    null);
            }

            public static com.bagnet.nettracer.ws.core.GetOHDsForWTDocument.GetOHDsForWT newInstance(
                org.apache.xmlbeans.XmlOptions options) {
                return (com.bagnet.nettracer.ws.core.GetOHDsForWTDocument.GetOHDsForWT) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
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

        public static com.bagnet.nettracer.ws.core.GetOHDsForWTDocument newInstance() {
            return (com.bagnet.nettracer.ws.core.GetOHDsForWTDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                   .newInstance(type,
                null);
        }

        public static com.bagnet.nettracer.ws.core.GetOHDsForWTDocument newInstance(
            org.apache.xmlbeans.XmlOptions options) {
            return (com.bagnet.nettracer.ws.core.GetOHDsForWTDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                   .newInstance(type,
                options);
        }

        /** @param xmlAsString the string value to parse */
        public static com.bagnet.nettracer.ws.core.GetOHDsForWTDocument parse(
            java.lang.String xmlAsString)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.GetOHDsForWTDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                   .parse(xmlAsString,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.GetOHDsForWTDocument parse(
            java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.GetOHDsForWTDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                   .parse(xmlAsString,
                type, options);
        }

        /** @param file the file from which to load an xml document */
        public static com.bagnet.nettracer.ws.core.GetOHDsForWTDocument parse(
            java.io.File file)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.GetOHDsForWTDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                   .parse(file,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.GetOHDsForWTDocument parse(
            java.io.File file, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.GetOHDsForWTDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                   .parse(file,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.GetOHDsForWTDocument parse(
            java.net.URL u)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.GetOHDsForWTDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                   .parse(u,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.GetOHDsForWTDocument parse(
            java.net.URL u, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.GetOHDsForWTDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                   .parse(u,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.GetOHDsForWTDocument parse(
            java.io.InputStream is)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.GetOHDsForWTDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                   .parse(is,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.GetOHDsForWTDocument parse(
            java.io.InputStream is, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.GetOHDsForWTDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                   .parse(is,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.GetOHDsForWTDocument parse(
            java.io.Reader r)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.GetOHDsForWTDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                   .parse(r,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.GetOHDsForWTDocument parse(
            java.io.Reader r, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.GetOHDsForWTDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                   .parse(r,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.GetOHDsForWTDocument parse(
            javax.xml.stream.XMLStreamReader sr)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.GetOHDsForWTDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                   .parse(sr,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.GetOHDsForWTDocument parse(
            javax.xml.stream.XMLStreamReader sr,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.GetOHDsForWTDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                   .parse(sr,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.GetOHDsForWTDocument parse(
            org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.GetOHDsForWTDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                   .parse(node,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.GetOHDsForWTDocument parse(
            org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.GetOHDsForWTDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                   .parse(node,
                type, options);
        }

        /** @deprecated {@link XMLInputStream} */
        public static com.bagnet.nettracer.ws.core.GetOHDsForWTDocument parse(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (com.bagnet.nettracer.ws.core.GetOHDsForWTDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                   .parse(xis,
                type, null);
        }

        /** @deprecated {@link XMLInputStream} */
        public static com.bagnet.nettracer.ws.core.GetOHDsForWTDocument parse(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (com.bagnet.nettracer.ws.core.GetOHDsForWTDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
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
