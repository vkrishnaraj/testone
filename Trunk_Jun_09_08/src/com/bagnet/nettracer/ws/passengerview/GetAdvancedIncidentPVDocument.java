/*
 * An XML document type.
 * Localname: getAdvancedIncidentPV
 * Namespace: http://passengerview.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentPVDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.passengerview;


/**
 * A document containing one getAdvancedIncidentPV(@http://passengerview.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public interface GetAdvancedIncidentPVDocument extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(GetAdvancedIncidentPVDocument.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s565D75BFF2D11A660C05CC688A6DB709").resolveHandle("getadvancedincidentpv9626doctype");
    
    /**
     * Gets the "getAdvancedIncidentPV" element
     */
    com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentPVDocument.GetAdvancedIncidentPV getGetAdvancedIncidentPV();
    
    /**
     * Sets the "getAdvancedIncidentPV" element
     */
    void setGetAdvancedIncidentPV(com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentPVDocument.GetAdvancedIncidentPV getAdvancedIncidentPV);
    
    /**
     * Appends and returns a new empty "getAdvancedIncidentPV" element
     */
    com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentPVDocument.GetAdvancedIncidentPV addNewGetAdvancedIncidentPV();
    
    /**
     * An XML getAdvancedIncidentPV(@http://passengerview.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public interface GetAdvancedIncidentPV extends org.apache.xmlbeans.XmlObject
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(GetAdvancedIncidentPV.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s565D75BFF2D11A660C05CC688A6DB709").resolveHandle("getadvancedincidentpv572aelemtype");
        
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
        
        public static final class Factory
        {
            public static com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentPVDocument.GetAdvancedIncidentPV newInstance() {
              return (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentPVDocument.GetAdvancedIncidentPV) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentPVDocument.GetAdvancedIncidentPV newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentPVDocument.GetAdvancedIncidentPV) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentPVDocument newInstance() {
          return (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentPVDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentPVDocument newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentPVDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentPVDocument parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentPVDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentPVDocument parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentPVDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentPVDocument parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentPVDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentPVDocument parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentPVDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentPVDocument parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentPVDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentPVDocument parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentPVDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentPVDocument parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentPVDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentPVDocument parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentPVDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentPVDocument parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentPVDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentPVDocument parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentPVDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentPVDocument parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentPVDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentPVDocument parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentPVDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentPVDocument parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentPVDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentPVDocument parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentPVDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentPVDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentPVDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentPVDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentPVDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
