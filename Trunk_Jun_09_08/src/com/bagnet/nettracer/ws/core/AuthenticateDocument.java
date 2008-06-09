/*
 * An XML document type.
 * Localname: authenticate
 * Namespace: http://core.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.core.AuthenticateDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.core;

import org.apache.xmlbeans.xml.stream.XMLInputStream;


/**
 * A document containing one authenticate(@http://core.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public interface AuthenticateDocument extends org.apache.xmlbeans.XmlObject {
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType) org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(AuthenticateDocument.class.getClassLoader(),
            "schemaorg_apache_xmlbeans.system.s51D0044A072EEB5AF0F7D86C8EBC33B8")
                                                                                                                           .resolveHandle("authenticatea58fdoctype");

    /**
     * Gets the "authenticate" element
     */
    com.bagnet.nettracer.ws.core.AuthenticateDocument.Authenticate getAuthenticate();

    /**
     * Sets the "authenticate" element
     */
    void setAuthenticate(
        com.bagnet.nettracer.ws.core.AuthenticateDocument.Authenticate authenticate);

    /**
     * Appends and returns a new empty "authenticate" element
     */
    com.bagnet.nettracer.ws.core.AuthenticateDocument.Authenticate addNewAuthenticate();

    /**
     * An XML authenticate(@http://core.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public interface Authenticate extends org.apache.xmlbeans.XmlObject {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType) org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(Authenticate.class.getClassLoader(),
                "schemaorg_apache_xmlbeans.system.s51D0044A072EEB5AF0F7D86C8EBC33B8")
                                                                                                                               .resolveHandle("authenticatede7eelemtype");

        /**
         * Gets the "username" element
         */
        java.lang.String getUsername();

        /**
         * Gets (as xml) the "username" element
         */
        org.apache.xmlbeans.XmlString xgetUsername();

        /**
         * Tests for nil "username" element
         */
        boolean isNilUsername();

        /**
         * True if has "username" element
         */
        boolean isSetUsername();

        /**
         * Sets the "username" element
         */
        void setUsername(java.lang.String username);

        /**
         * Sets (as xml) the "username" element
         */
        void xsetUsername(org.apache.xmlbeans.XmlString username);

        /**
         * Nils the "username" element
         */
        void setNilUsername();

        /**
         * Unsets the "username" element
         */
        void unsetUsername();

        /**
         * Gets the "password" element
         */
        java.lang.String getPassword();

        /**
         * Gets (as xml) the "password" element
         */
        org.apache.xmlbeans.XmlString xgetPassword();

        /**
         * Tests for nil "password" element
         */
        boolean isNilPassword();

        /**
         * True if has "password" element
         */
        boolean isSetPassword();

        /**
         * Sets the "password" element
         */
        void setPassword(java.lang.String password);

        /**
         * Sets (as xml) the "password" element
         */
        void xsetPassword(org.apache.xmlbeans.XmlString password);

        /**
         * Nils the "password" element
         */
        void setNilPassword();

        /**
         * Unsets the "password" element
         */
        void unsetPassword();

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
         * A factory class with static methods for creating instances
         * of this type.
         */
        public static final class Factory {
            private Factory() {
            } // No instance of this class allowed

            public static com.bagnet.nettracer.ws.core.AuthenticateDocument.Authenticate newInstance() {
                return (com.bagnet.nettracer.ws.core.AuthenticateDocument.Authenticate) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                    .newInstance(type,
                    null);
            }

            public static com.bagnet.nettracer.ws.core.AuthenticateDocument.Authenticate newInstance(
                org.apache.xmlbeans.XmlOptions options) {
                return (com.bagnet.nettracer.ws.core.AuthenticateDocument.Authenticate) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
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

        public static com.bagnet.nettracer.ws.core.AuthenticateDocument newInstance() {
            return (com.bagnet.nettracer.ws.core.AuthenticateDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                   .newInstance(type,
                null);
        }

        public static com.bagnet.nettracer.ws.core.AuthenticateDocument newInstance(
            org.apache.xmlbeans.XmlOptions options) {
            return (com.bagnet.nettracer.ws.core.AuthenticateDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                   .newInstance(type,
                options);
        }

        /** @param xmlAsString the string value to parse */
        public static com.bagnet.nettracer.ws.core.AuthenticateDocument parse(
            java.lang.String xmlAsString)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.AuthenticateDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                   .parse(xmlAsString,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.AuthenticateDocument parse(
            java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.AuthenticateDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                   .parse(xmlAsString,
                type, options);
        }

        /** @param file the file from which to load an xml document */
        public static com.bagnet.nettracer.ws.core.AuthenticateDocument parse(
            java.io.File file)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.AuthenticateDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                   .parse(file,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.AuthenticateDocument parse(
            java.io.File file, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.AuthenticateDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                   .parse(file,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.AuthenticateDocument parse(
            java.net.URL u)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.AuthenticateDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                   .parse(u,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.AuthenticateDocument parse(
            java.net.URL u, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.AuthenticateDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                   .parse(u,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.AuthenticateDocument parse(
            java.io.InputStream is)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.AuthenticateDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                   .parse(is,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.AuthenticateDocument parse(
            java.io.InputStream is, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.AuthenticateDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                   .parse(is,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.AuthenticateDocument parse(
            java.io.Reader r)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.AuthenticateDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                   .parse(r,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.AuthenticateDocument parse(
            java.io.Reader r, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.AuthenticateDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                   .parse(r,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.AuthenticateDocument parse(
            javax.xml.stream.XMLStreamReader sr)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.AuthenticateDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                   .parse(sr,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.AuthenticateDocument parse(
            javax.xml.stream.XMLStreamReader sr,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.AuthenticateDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                   .parse(sr,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.AuthenticateDocument parse(
            org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.AuthenticateDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                   .parse(node,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.AuthenticateDocument parse(
            org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.AuthenticateDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                   .parse(node,
                type, options);
        }

        /** @deprecated {@link XMLInputStream} */
        public static com.bagnet.nettracer.ws.core.AuthenticateDocument parse(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (com.bagnet.nettracer.ws.core.AuthenticateDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                   .parse(xis,
                type, null);
        }

        /** @deprecated {@link XMLInputStream} */
        public static com.bagnet.nettracer.ws.core.AuthenticateDocument parse(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (com.bagnet.nettracer.ws.core.AuthenticateDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
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
