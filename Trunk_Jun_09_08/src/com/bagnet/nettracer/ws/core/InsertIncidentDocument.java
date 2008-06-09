/*
 * An XML document type.
 * Localname: insertIncident
 * Namespace: http://core.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.core.InsertIncidentDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.core;

import org.apache.xmlbeans.xml.stream.XMLInputStream;


/**
 * A document containing one insertIncident(@http://core.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public interface InsertIncidentDocument extends org.apache.xmlbeans.XmlObject {
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType) org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(InsertIncidentDocument.class.getClassLoader(),
            "schemaorg_apache_xmlbeans.system.s51D0044A072EEB5AF0F7D86C8EBC33B8")
                                                                                                                           .resolveHandle("insertincidentb425doctype");

    /**
     * Gets the "insertIncident" element
     */
    com.bagnet.nettracer.ws.core.InsertIncidentDocument.InsertIncident getInsertIncident();

    /**
     * Sets the "insertIncident" element
     */
    void setInsertIncident(
        com.bagnet.nettracer.ws.core.InsertIncidentDocument.InsertIncident insertIncident);

    /**
     * Appends and returns a new empty "insertIncident" element
     */
    com.bagnet.nettracer.ws.core.InsertIncidentDocument.InsertIncident addNewInsertIncident();

    /**
     * An XML insertIncident(@http://core.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public interface InsertIncident extends org.apache.xmlbeans.XmlObject {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType) org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(InsertIncident.class.getClassLoader(),
                "schemaorg_apache_xmlbeans.system.s51D0044A072EEB5AF0F7D86C8EBC33B8")
                                                                                                                               .resolveHandle("insertincidentb13eelemtype");

        /**
         * Gets the "si" element
         */
        com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident getSi();

        /**
         * Tests for nil "si" element
         */
        boolean isNilSi();

        /**
         * True if has "si" element
         */
        boolean isSetSi();

        /**
         * Sets the "si" element
         */
        void setSi(com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident si);

        /**
         * Appends and returns a new empty "si" element
         */
        com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident addNewSi();

        /**
         * Nils the "si" element
         */
        void setNilSi();

        /**
         * Unsets the "si" element
         */
        void unsetSi();

        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        public static final class Factory {
            private Factory() {
            } // No instance of this class allowed

            public static com.bagnet.nettracer.ws.core.InsertIncidentDocument.InsertIncident newInstance() {
                return (com.bagnet.nettracer.ws.core.InsertIncidentDocument.InsertIncident) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                        .newInstance(type,
                    null);
            }

            public static com.bagnet.nettracer.ws.core.InsertIncidentDocument.InsertIncident newInstance(
                org.apache.xmlbeans.XmlOptions options) {
                return (com.bagnet.nettracer.ws.core.InsertIncidentDocument.InsertIncident) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
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

        public static com.bagnet.nettracer.ws.core.InsertIncidentDocument newInstance() {
            return (com.bagnet.nettracer.ws.core.InsertIncidentDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                     .newInstance(type,
                null);
        }

        public static com.bagnet.nettracer.ws.core.InsertIncidentDocument newInstance(
            org.apache.xmlbeans.XmlOptions options) {
            return (com.bagnet.nettracer.ws.core.InsertIncidentDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                     .newInstance(type,
                options);
        }

        /** @param xmlAsString the string value to parse */
        public static com.bagnet.nettracer.ws.core.InsertIncidentDocument parse(
            java.lang.String xmlAsString)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.InsertIncidentDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                     .parse(xmlAsString,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.InsertIncidentDocument parse(
            java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.InsertIncidentDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                     .parse(xmlAsString,
                type, options);
        }

        /** @param file the file from which to load an xml document */
        public static com.bagnet.nettracer.ws.core.InsertIncidentDocument parse(
            java.io.File file)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.InsertIncidentDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                     .parse(file,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.InsertIncidentDocument parse(
            java.io.File file, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.InsertIncidentDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                     .parse(file,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.InsertIncidentDocument parse(
            java.net.URL u)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.InsertIncidentDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                     .parse(u,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.InsertIncidentDocument parse(
            java.net.URL u, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.InsertIncidentDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                     .parse(u,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.InsertIncidentDocument parse(
            java.io.InputStream is)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.InsertIncidentDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                     .parse(is,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.InsertIncidentDocument parse(
            java.io.InputStream is, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.InsertIncidentDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                     .parse(is,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.InsertIncidentDocument parse(
            java.io.Reader r)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.InsertIncidentDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                     .parse(r,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.InsertIncidentDocument parse(
            java.io.Reader r, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.InsertIncidentDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                     .parse(r,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.InsertIncidentDocument parse(
            javax.xml.stream.XMLStreamReader sr)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.InsertIncidentDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                     .parse(sr,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.InsertIncidentDocument parse(
            javax.xml.stream.XMLStreamReader sr,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.InsertIncidentDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                     .parse(sr,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.InsertIncidentDocument parse(
            org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.InsertIncidentDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                     .parse(node,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.InsertIncidentDocument parse(
            org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.InsertIncidentDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                     .parse(node,
                type, options);
        }

        /** @deprecated {@link XMLInputStream} */
        public static com.bagnet.nettracer.ws.core.InsertIncidentDocument parse(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (com.bagnet.nettracer.ws.core.InsertIncidentDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                     .parse(xis,
                type, null);
        }

        /** @deprecated {@link XMLInputStream} */
        public static com.bagnet.nettracer.ws.core.InsertIncidentDocument parse(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (com.bagnet.nettracer.ws.core.InsertIncidentDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
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
