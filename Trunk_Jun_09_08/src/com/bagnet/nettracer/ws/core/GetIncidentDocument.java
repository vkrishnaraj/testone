/*
 * An XML document type.
 * Localname: getIncident
 * Namespace: http://core.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.core.GetIncidentDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.core;


/**
 * A document containing one getIncident(@http://core.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public interface GetIncidentDocument extends org.apache.xmlbeans.XmlObject {
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType) org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(GetIncidentDocument.class.getClassLoader(),
            "schemaorg_apache_xmlbeans.system.s2182E89F12DCED7BC68D5ADCA1063463")
                                                                                                                           .resolveHandle("getincident2b70doctype");

    /**
     * Gets the "getIncident" element
     */
    com.bagnet.nettracer.ws.core.GetIncidentDocument.GetIncident getGetIncident();

    /**
     * Sets the "getIncident" element
     */
    void setGetIncident(
        com.bagnet.nettracer.ws.core.GetIncidentDocument.GetIncident getIncident);

    /**
     * Appends and returns a new empty "getIncident" element
     */
    com.bagnet.nettracer.ws.core.GetIncidentDocument.GetIncident addNewGetIncident();

    /**
     * An XML getIncident(@http://core.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public interface GetIncident extends org.apache.xmlbeans.XmlObject {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType) org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(GetIncident.class.getClassLoader(),
                "schemaorg_apache_xmlbeans.system.s2182E89F12DCED7BC68D5ADCA1063463")
                                                                                                                               .resolveHandle("getincident4124elemtype");

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
         * Gets the "inc_type" element
         */
        java.lang.String getIncType();

        /**
         * Gets (as xml) the "inc_type" element
         */
        org.apache.xmlbeans.XmlString xgetIncType();

        /**
         * Tests for nil "inc_type" element
         */
        boolean isNilIncType();

        /**
         * True if has "inc_type" element
         */
        boolean isSetIncType();

        /**
         * Sets the "inc_type" element
         */
        void setIncType(java.lang.String incType);

        /**
         * Sets (as xml) the "inc_type" element
         */
        void xsetIncType(org.apache.xmlbeans.XmlString incType);

        /**
         * Nils the "inc_type" element
         */
        void setNilIncType();

        /**
         * Unsets the "inc_type" element
         */
        void unsetIncType();

        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        public static final class Factory {
            private Factory() {
            } // No instance of this class allowed

            public static com.bagnet.nettracer.ws.core.GetIncidentDocument.GetIncident newInstance() {
                return (com.bagnet.nettracer.ws.core.GetIncidentDocument.GetIncident) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                  .newInstance(type,
                    null);
            }

            public static com.bagnet.nettracer.ws.core.GetIncidentDocument.GetIncident newInstance(
                org.apache.xmlbeans.XmlOptions options) {
                return (com.bagnet.nettracer.ws.core.GetIncidentDocument.GetIncident) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
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

        public static com.bagnet.nettracer.ws.core.GetIncidentDocument newInstance() {
            return (com.bagnet.nettracer.ws.core.GetIncidentDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                  .newInstance(type,
                null);
        }

        public static com.bagnet.nettracer.ws.core.GetIncidentDocument newInstance(
            org.apache.xmlbeans.XmlOptions options) {
            return (com.bagnet.nettracer.ws.core.GetIncidentDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                  .newInstance(type,
                options);
        }

        /** @param xmlAsString the string value to parse */
        public static com.bagnet.nettracer.ws.core.GetIncidentDocument parse(
            java.lang.String xmlAsString)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.GetIncidentDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                  .parse(xmlAsString,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.GetIncidentDocument parse(
            java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.GetIncidentDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                  .parse(xmlAsString,
                type, options);
        }

        /** @param file the file from which to load an xml document */
        public static com.bagnet.nettracer.ws.core.GetIncidentDocument parse(
            java.io.File file)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.GetIncidentDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                  .parse(file,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.GetIncidentDocument parse(
            java.io.File file, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.GetIncidentDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                  .parse(file,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.GetIncidentDocument parse(
            java.net.URL u)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.GetIncidentDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                  .parse(u,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.GetIncidentDocument parse(
            java.net.URL u, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.GetIncidentDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                  .parse(u,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.GetIncidentDocument parse(
            java.io.InputStream is)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.GetIncidentDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                  .parse(is,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.GetIncidentDocument parse(
            java.io.InputStream is, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.GetIncidentDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                  .parse(is,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.GetIncidentDocument parse(
            java.io.Reader r)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.GetIncidentDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                  .parse(r,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.GetIncidentDocument parse(
            java.io.Reader r, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.GetIncidentDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                  .parse(r,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.GetIncidentDocument parse(
            javax.xml.stream.XMLStreamReader sr)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.GetIncidentDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                  .parse(sr,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.GetIncidentDocument parse(
            javax.xml.stream.XMLStreamReader sr,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.GetIncidentDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                  .parse(sr,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.GetIncidentDocument parse(
            org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.GetIncidentDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                  .parse(node,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.GetIncidentDocument parse(
            org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.GetIncidentDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                  .parse(node,
                type, options);
        }

        /** @deprecated {@link XMLInputStream} */
        public static com.bagnet.nettracer.ws.core.GetIncidentDocument parse(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (com.bagnet.nettracer.ws.core.GetIncidentDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                  .parse(xis,
                type, null);
        }

        /** @deprecated {@link XMLInputStream} */
        public static com.bagnet.nettracer.ws.core.GetIncidentDocument parse(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (com.bagnet.nettracer.ws.core.GetIncidentDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
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
