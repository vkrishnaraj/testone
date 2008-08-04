/*
 * An XML document type.
 * Localname: getIncidentPV2
 * Namespace: http://passengerview.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.passengerview.GetIncidentPV2Document
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.passengerview;


/**
 * A document containing one getIncidentPV2(@http://passengerview.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public interface GetIncidentPV2Document extends org.apache.xmlbeans.XmlObject {
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType) org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(GetIncidentPV2Document.class.getClassLoader(),
            "schemaorg_apache_xmlbeans.system.s1E8397785CFE6E375EB3DD72EBE6EB5F")
                                                                                                                           .resolveHandle("getincidentpv23ec0doctype");

    /**
     * Gets the "getIncidentPV2" element
     */
    com.bagnet.nettracer.ws.passengerview.GetIncidentPV2Document.GetIncidentPV2 getGetIncidentPV2();

    /**
     * Sets the "getIncidentPV2" element
     */
    void setGetIncidentPV2(
        com.bagnet.nettracer.ws.passengerview.GetIncidentPV2Document.GetIncidentPV2 getIncidentPV2);

    /**
     * Appends and returns a new empty "getIncidentPV2" element
     */
    com.bagnet.nettracer.ws.passengerview.GetIncidentPV2Document.GetIncidentPV2 addNewGetIncidentPV2();

    /**
     * An XML getIncidentPV2(@http://passengerview.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public interface GetIncidentPV2 extends org.apache.xmlbeans.XmlObject {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType) org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(GetIncidentPV2.class.getClassLoader(),
                "schemaorg_apache_xmlbeans.system.s1E8397785CFE6E375EB3DD72EBE6EB5F")
                                                                                                                               .resolveHandle("getincidentpv21d00elemtype");

        /**
         * Gets the "incident_id" element
         */
        java.lang.String getIncidentId();

        /**
         * Gets (as xml) the "incident_id" element
         */
        org.apache.xmlbeans.XmlString xgetIncidentId();

        /**
         * Tests for nil "incident_id" element
         */
        boolean isNilIncidentId();

        /**
         * True if has "incident_id" element
         */
        boolean isSetIncidentId();

        /**
         * Sets the "incident_id" element
         */
        void setIncidentId(java.lang.String incidentId);

        /**
         * Sets (as xml) the "incident_id" element
         */
        void xsetIncidentId(org.apache.xmlbeans.XmlString incidentId);

        /**
         * Nils the "incident_id" element
         */
        void setNilIncidentId();

        /**
         * Unsets the "incident_id" element
         */
        void unsetIncidentId();

        /**
         * Gets the "lastname" element
         */
        java.lang.String getLastname();

        /**
         * Gets (as xml) the "lastname" element
         */
        org.apache.xmlbeans.XmlString xgetLastname();

        /**
         * Tests for nil "lastname" element
         */
        boolean isNilLastname();

        /**
         * True if has "lastname" element
         */
        boolean isSetLastname();

        /**
         * Sets the "lastname" element
         */
        void setLastname(java.lang.String lastname);

        /**
         * Sets (as xml) the "lastname" element
         */
        void xsetLastname(org.apache.xmlbeans.XmlString lastname);

        /**
         * Nils the "lastname" element
         */
        void setNilLastname();

        /**
         * Unsets the "lastname" element
         */
        void unsetLastname();

        /**
         * Gets the "doNotAuthorize" element
         */
        boolean getDoNotAuthorize();

        /**
         * Gets (as xml) the "doNotAuthorize" element
         */
        org.apache.xmlbeans.XmlBoolean xgetDoNotAuthorize();

        /**
         * True if has "doNotAuthorize" element
         */
        boolean isSetDoNotAuthorize();

        /**
         * Sets the "doNotAuthorize" element
         */
        void setDoNotAuthorize(boolean doNotAuthorize);

        /**
         * Sets (as xml) the "doNotAuthorize" element
         */
        void xsetDoNotAuthorize(org.apache.xmlbeans.XmlBoolean doNotAuthorize);

        /**
         * Unsets the "doNotAuthorize" element
         */
        void unsetDoNotAuthorize();

        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        public static final class Factory {
            private Factory() {
            } // No instance of this class allowed

            public static com.bagnet.nettracer.ws.passengerview.GetIncidentPV2Document.GetIncidentPV2 newInstance() {
                return (com.bagnet.nettracer.ws.passengerview.GetIncidentPV2Document.GetIncidentPV2) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                                 .newInstance(type,
                    null);
            }

            public static com.bagnet.nettracer.ws.passengerview.GetIncidentPV2Document.GetIncidentPV2 newInstance(
                org.apache.xmlbeans.XmlOptions options) {
                return (com.bagnet.nettracer.ws.passengerview.GetIncidentPV2Document.GetIncidentPV2) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
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

        public static com.bagnet.nettracer.ws.passengerview.GetIncidentPV2Document newInstance() {
            return (com.bagnet.nettracer.ws.passengerview.GetIncidentPV2Document) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                              .newInstance(type,
                null);
        }

        public static com.bagnet.nettracer.ws.passengerview.GetIncidentPV2Document newInstance(
            org.apache.xmlbeans.XmlOptions options) {
            return (com.bagnet.nettracer.ws.passengerview.GetIncidentPV2Document) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                              .newInstance(type,
                options);
        }

        /** @param xmlAsString the string value to parse */
        public static com.bagnet.nettracer.ws.passengerview.GetIncidentPV2Document parse(
            java.lang.String xmlAsString)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.passengerview.GetIncidentPV2Document) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                              .parse(xmlAsString,
                type, null);
        }

        public static com.bagnet.nettracer.ws.passengerview.GetIncidentPV2Document parse(
            java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.passengerview.GetIncidentPV2Document) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                              .parse(xmlAsString,
                type, options);
        }

        /** @param file the file from which to load an xml document */
        public static com.bagnet.nettracer.ws.passengerview.GetIncidentPV2Document parse(
            java.io.File file)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.passengerview.GetIncidentPV2Document) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                              .parse(file,
                type, null);
        }

        public static com.bagnet.nettracer.ws.passengerview.GetIncidentPV2Document parse(
            java.io.File file, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.passengerview.GetIncidentPV2Document) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                              .parse(file,
                type, options);
        }

        public static com.bagnet.nettracer.ws.passengerview.GetIncidentPV2Document parse(
            java.net.URL u)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.passengerview.GetIncidentPV2Document) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                              .parse(u,
                type, null);
        }

        public static com.bagnet.nettracer.ws.passengerview.GetIncidentPV2Document parse(
            java.net.URL u, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.passengerview.GetIncidentPV2Document) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                              .parse(u,
                type, options);
        }

        public static com.bagnet.nettracer.ws.passengerview.GetIncidentPV2Document parse(
            java.io.InputStream is)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.passengerview.GetIncidentPV2Document) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                              .parse(is,
                type, null);
        }

        public static com.bagnet.nettracer.ws.passengerview.GetIncidentPV2Document parse(
            java.io.InputStream is, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.passengerview.GetIncidentPV2Document) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                              .parse(is,
                type, options);
        }

        public static com.bagnet.nettracer.ws.passengerview.GetIncidentPV2Document parse(
            java.io.Reader r)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.passengerview.GetIncidentPV2Document) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                              .parse(r,
                type, null);
        }

        public static com.bagnet.nettracer.ws.passengerview.GetIncidentPV2Document parse(
            java.io.Reader r, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.passengerview.GetIncidentPV2Document) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                              .parse(r,
                type, options);
        }

        public static com.bagnet.nettracer.ws.passengerview.GetIncidentPV2Document parse(
            javax.xml.stream.XMLStreamReader sr)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.passengerview.GetIncidentPV2Document) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                              .parse(sr,
                type, null);
        }

        public static com.bagnet.nettracer.ws.passengerview.GetIncidentPV2Document parse(
            javax.xml.stream.XMLStreamReader sr,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.passengerview.GetIncidentPV2Document) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                              .parse(sr,
                type, options);
        }

        public static com.bagnet.nettracer.ws.passengerview.GetIncidentPV2Document parse(
            org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.passengerview.GetIncidentPV2Document) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                              .parse(node,
                type, null);
        }

        public static com.bagnet.nettracer.ws.passengerview.GetIncidentPV2Document parse(
            org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.passengerview.GetIncidentPV2Document) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                              .parse(node,
                type, options);
        }

        /** @deprecated {@link XMLInputStream} */
        public static com.bagnet.nettracer.ws.passengerview.GetIncidentPV2Document parse(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (com.bagnet.nettracer.ws.passengerview.GetIncidentPV2Document) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                              .parse(xis,
                type, null);
        }

        /** @deprecated {@link XMLInputStream} */
        public static com.bagnet.nettracer.ws.passengerview.GetIncidentPV2Document parse(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (com.bagnet.nettracer.ws.passengerview.GetIncidentPV2Document) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
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
