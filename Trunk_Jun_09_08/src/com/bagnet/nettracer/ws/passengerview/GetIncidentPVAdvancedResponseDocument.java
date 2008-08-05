/*
 * An XML document type.
 * Localname: getIncidentPVAdvancedResponse
 * Namespace: http://passengerview.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.passengerview.GetIncidentPVAdvancedResponseDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.passengerview;


/**
 * A document containing one getIncidentPVAdvancedResponse(@http://passengerview.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public interface GetIncidentPVAdvancedResponseDocument extends org.apache.xmlbeans.XmlObject {
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType) org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(GetIncidentPVAdvancedResponseDocument.class.getClassLoader(),
            "schemaorg_apache_xmlbeans.system.sFC8F9E68937D2AB189E65DF85DA86C7B")
                                                                                                                           .resolveHandle("getincidentpvadvancedresponse2be5doctype");

    /**
     * Gets the "getIncidentPVAdvancedResponse" element
     */
    com.bagnet.nettracer.ws.passengerview.GetIncidentPVAdvancedResponseDocument.GetIncidentPVAdvancedResponse getGetIncidentPVAdvancedResponse();

    /**
     * Sets the "getIncidentPVAdvancedResponse" element
     */
    void setGetIncidentPVAdvancedResponse(
        com.bagnet.nettracer.ws.passengerview.GetIncidentPVAdvancedResponseDocument.GetIncidentPVAdvancedResponse getIncidentPVAdvancedResponse);

    /**
     * Appends and returns a new empty "getIncidentPVAdvancedResponse" element
     */
    com.bagnet.nettracer.ws.passengerview.GetIncidentPVAdvancedResponseDocument.GetIncidentPVAdvancedResponse addNewGetIncidentPVAdvancedResponse();

    /**
     * An XML getIncidentPVAdvancedResponse(@http://passengerview.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public interface GetIncidentPVAdvancedResponse extends org.apache.xmlbeans.XmlObject {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType) org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(GetIncidentPVAdvancedResponse.class.getClassLoader(),
                "schemaorg_apache_xmlbeans.system.sFC8F9E68937D2AB189E65DF85DA86C7B")
                                                                                                                               .resolveHandle("getincidentpvadvancedresponseb3a8elemtype");

        /**
         * Gets the "return" element
         */
        com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident getReturn();

        /**
         * Tests for nil "return" element
         */
        boolean isNilReturn();

        /**
         * True if has "return" element
         */
        boolean isSetReturn();

        /**
         * Sets the "return" element
         */
        void setReturn(
            com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident xreturn);

        /**
         * Appends and returns a new empty "return" element
         */
        com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident addNewReturn();

        /**
         * Nils the "return" element
         */
        void setNilReturn();

        /**
         * Unsets the "return" element
         */
        void unsetReturn();

        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        public static final class Factory {
            private Factory() {
            } // No instance of this class allowed

            public static com.bagnet.nettracer.ws.passengerview.GetIncidentPVAdvancedResponseDocument.GetIncidentPVAdvancedResponse newInstance() {
                return (com.bagnet.nettracer.ws.passengerview.GetIncidentPVAdvancedResponseDocument.GetIncidentPVAdvancedResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                                                               .newInstance(type,
                    null);
            }

            public static com.bagnet.nettracer.ws.passengerview.GetIncidentPVAdvancedResponseDocument.GetIncidentPVAdvancedResponse newInstance(
                org.apache.xmlbeans.XmlOptions options) {
                return (com.bagnet.nettracer.ws.passengerview.GetIncidentPVAdvancedResponseDocument.GetIncidentPVAdvancedResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
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

        public static com.bagnet.nettracer.ws.passengerview.GetIncidentPVAdvancedResponseDocument newInstance() {
            return (com.bagnet.nettracer.ws.passengerview.GetIncidentPVAdvancedResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                             .newInstance(type,
                null);
        }

        public static com.bagnet.nettracer.ws.passengerview.GetIncidentPVAdvancedResponseDocument newInstance(
            org.apache.xmlbeans.XmlOptions options) {
            return (com.bagnet.nettracer.ws.passengerview.GetIncidentPVAdvancedResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                             .newInstance(type,
                options);
        }

        /** @param xmlAsString the string value to parse */
        public static com.bagnet.nettracer.ws.passengerview.GetIncidentPVAdvancedResponseDocument parse(
            java.lang.String xmlAsString)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.passengerview.GetIncidentPVAdvancedResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                             .parse(xmlAsString,
                type, null);
        }

        public static com.bagnet.nettracer.ws.passengerview.GetIncidentPVAdvancedResponseDocument parse(
            java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.passengerview.GetIncidentPVAdvancedResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                             .parse(xmlAsString,
                type, options);
        }

        /** @param file the file from which to load an xml document */
        public static com.bagnet.nettracer.ws.passengerview.GetIncidentPVAdvancedResponseDocument parse(
            java.io.File file)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.passengerview.GetIncidentPVAdvancedResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                             .parse(file,
                type, null);
        }

        public static com.bagnet.nettracer.ws.passengerview.GetIncidentPVAdvancedResponseDocument parse(
            java.io.File file, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.passengerview.GetIncidentPVAdvancedResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                             .parse(file,
                type, options);
        }

        public static com.bagnet.nettracer.ws.passengerview.GetIncidentPVAdvancedResponseDocument parse(
            java.net.URL u)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.passengerview.GetIncidentPVAdvancedResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                             .parse(u,
                type, null);
        }

        public static com.bagnet.nettracer.ws.passengerview.GetIncidentPVAdvancedResponseDocument parse(
            java.net.URL u, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.passengerview.GetIncidentPVAdvancedResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                             .parse(u,
                type, options);
        }

        public static com.bagnet.nettracer.ws.passengerview.GetIncidentPVAdvancedResponseDocument parse(
            java.io.InputStream is)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.passengerview.GetIncidentPVAdvancedResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                             .parse(is,
                type, null);
        }

        public static com.bagnet.nettracer.ws.passengerview.GetIncidentPVAdvancedResponseDocument parse(
            java.io.InputStream is, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.passengerview.GetIncidentPVAdvancedResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                             .parse(is,
                type, options);
        }

        public static com.bagnet.nettracer.ws.passengerview.GetIncidentPVAdvancedResponseDocument parse(
            java.io.Reader r)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.passengerview.GetIncidentPVAdvancedResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                             .parse(r,
                type, null);
        }

        public static com.bagnet.nettracer.ws.passengerview.GetIncidentPVAdvancedResponseDocument parse(
            java.io.Reader r, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.passengerview.GetIncidentPVAdvancedResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                             .parse(r,
                type, options);
        }

        public static com.bagnet.nettracer.ws.passengerview.GetIncidentPVAdvancedResponseDocument parse(
            javax.xml.stream.XMLStreamReader sr)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.passengerview.GetIncidentPVAdvancedResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                             .parse(sr,
                type, null);
        }

        public static com.bagnet.nettracer.ws.passengerview.GetIncidentPVAdvancedResponseDocument parse(
            javax.xml.stream.XMLStreamReader sr,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.passengerview.GetIncidentPVAdvancedResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                             .parse(sr,
                type, options);
        }

        public static com.bagnet.nettracer.ws.passengerview.GetIncidentPVAdvancedResponseDocument parse(
            org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.passengerview.GetIncidentPVAdvancedResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                             .parse(node,
                type, null);
        }

        public static com.bagnet.nettracer.ws.passengerview.GetIncidentPVAdvancedResponseDocument parse(
            org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.passengerview.GetIncidentPVAdvancedResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                             .parse(node,
                type, options);
        }

        /** @deprecated {@link XMLInputStream} */
        public static com.bagnet.nettracer.ws.passengerview.GetIncidentPVAdvancedResponseDocument parse(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (com.bagnet.nettracer.ws.passengerview.GetIncidentPVAdvancedResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                             .parse(xis,
                type, null);
        }

        /** @deprecated {@link XMLInputStream} */
        public static com.bagnet.nettracer.ws.passengerview.GetIncidentPVAdvancedResponseDocument parse(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (com.bagnet.nettracer.ws.passengerview.GetIncidentPVAdvancedResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
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
