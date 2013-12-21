/*
 * An XML document type.
 * Localname: saveNewIncident
 * Namespace: http://onlineclaims.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.onlineclaims.SaveNewIncidentDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.onlineclaims;


/**
 * A document containing one saveNewIncident(@http://onlineclaims.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public interface SaveNewIncidentDocument extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(SaveNewIncidentDocument.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s47918F556B545B4B159263E4D62D20A1").resolveHandle("savenewincident7128doctype");
    
    /**
     * Gets the "saveNewIncident" element
     */
    com.bagnet.nettracer.ws.onlineclaims.SaveNewIncidentDocument.SaveNewIncident getSaveNewIncident();
    
    /**
     * Sets the "saveNewIncident" element
     */
    void setSaveNewIncident(com.bagnet.nettracer.ws.onlineclaims.SaveNewIncidentDocument.SaveNewIncident saveNewIncident);
    
    /**
     * Appends and returns a new empty "saveNewIncident" element
     */
    com.bagnet.nettracer.ws.onlineclaims.SaveNewIncidentDocument.SaveNewIncident addNewSaveNewIncident();
    
    /**
     * An XML saveNewIncident(@http://onlineclaims.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public interface SaveNewIncident extends org.apache.xmlbeans.XmlObject
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(SaveNewIncident.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s47918F556B545B4B159263E4D62D20A1").resolveHandle("savenewincidentb2a9elemtype");
        
        /**
         * Gets the "firstName" element
         */
        java.lang.String getFirstName();
        
        /**
         * Gets (as xml) the "firstName" element
         */
        org.apache.xmlbeans.XmlString xgetFirstName();
        
        /**
         * Tests for nil "firstName" element
         */
        boolean isNilFirstName();
        
        /**
         * True if has "firstName" element
         */
        boolean isSetFirstName();
        
        /**
         * Sets the "firstName" element
         */
        void setFirstName(java.lang.String firstName);
        
        /**
         * Sets (as xml) the "firstName" element
         */
        void xsetFirstName(org.apache.xmlbeans.XmlString firstName);
        
        /**
         * Nils the "firstName" element
         */
        void setNilFirstName();
        
        /**
         * Unsets the "firstName" element
         */
        void unsetFirstName();
        
        /**
         * Gets the "lastName" element
         */
        java.lang.String getLastName();
        
        /**
         * Gets (as xml) the "lastName" element
         */
        org.apache.xmlbeans.XmlString xgetLastName();
        
        /**
         * Tests for nil "lastName" element
         */
        boolean isNilLastName();
        
        /**
         * True if has "lastName" element
         */
        boolean isSetLastName();
        
        /**
         * Sets the "lastName" element
         */
        void setLastName(java.lang.String lastName);
        
        /**
         * Sets (as xml) the "lastName" element
         */
        void xsetLastName(org.apache.xmlbeans.XmlString lastName);
        
        /**
         * Nils the "lastName" element
         */
        void setNilLastName();
        
        /**
         * Unsets the "lastName" element
         */
        void unsetLastName();
        
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
         * Gets the "incident" element
         */
        com.bagnet.nettracer.ws.onlineclaims.xsd.Incident getIncident();
        
        /**
         * Tests for nil "incident" element
         */
        boolean isNilIncident();
        
        /**
         * True if has "incident" element
         */
        boolean isSetIncident();
        
        /**
         * Sets the "incident" element
         */
        void setIncident(com.bagnet.nettracer.ws.onlineclaims.xsd.Incident incident);
        
        /**
         * Appends and returns a new empty "incident" element
         */
        com.bagnet.nettracer.ws.onlineclaims.xsd.Incident addNewIncident();
        
        /**
         * Nils the "incident" element
         */
        void setNilIncident();
        
        /**
         * Unsets the "incident" element
         */
        void unsetIncident();
        
        /**
         * Gets the "auth" element
         */
        com.bagnet.nettracer.ws.onlineclaims.xsd.NtAuth getAuth();
        
        /**
         * Tests for nil "auth" element
         */
        boolean isNilAuth();
        
        /**
         * True if has "auth" element
         */
        boolean isSetAuth();
        
        /**
         * Sets the "auth" element
         */
        void setAuth(com.bagnet.nettracer.ws.onlineclaims.xsd.NtAuth auth);
        
        /**
         * Appends and returns a new empty "auth" element
         */
        com.bagnet.nettracer.ws.onlineclaims.xsd.NtAuth addNewAuth();
        
        /**
         * Nils the "auth" element
         */
        void setNilAuth();
        
        /**
         * Unsets the "auth" element
         */
        void unsetAuth();
        
        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        
        public static final class Factory
        {
            public static com.bagnet.nettracer.ws.onlineclaims.SaveNewIncidentDocument.SaveNewIncident newInstance() {
              return (com.bagnet.nettracer.ws.onlineclaims.SaveNewIncidentDocument.SaveNewIncident) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static com.bagnet.nettracer.ws.onlineclaims.SaveNewIncidentDocument.SaveNewIncident newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (com.bagnet.nettracer.ws.onlineclaims.SaveNewIncidentDocument.SaveNewIncident) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static com.bagnet.nettracer.ws.onlineclaims.SaveNewIncidentDocument newInstance() {
          return (com.bagnet.nettracer.ws.onlineclaims.SaveNewIncidentDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.SaveNewIncidentDocument newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (com.bagnet.nettracer.ws.onlineclaims.SaveNewIncidentDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static com.bagnet.nettracer.ws.onlineclaims.SaveNewIncidentDocument parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.onlineclaims.SaveNewIncidentDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.SaveNewIncidentDocument parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.onlineclaims.SaveNewIncidentDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static com.bagnet.nettracer.ws.onlineclaims.SaveNewIncidentDocument parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.onlineclaims.SaveNewIncidentDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.SaveNewIncidentDocument parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.onlineclaims.SaveNewIncidentDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.SaveNewIncidentDocument parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.onlineclaims.SaveNewIncidentDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.SaveNewIncidentDocument parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.onlineclaims.SaveNewIncidentDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.SaveNewIncidentDocument parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.onlineclaims.SaveNewIncidentDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.SaveNewIncidentDocument parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.onlineclaims.SaveNewIncidentDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.SaveNewIncidentDocument parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.onlineclaims.SaveNewIncidentDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.SaveNewIncidentDocument parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.onlineclaims.SaveNewIncidentDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.SaveNewIncidentDocument parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.onlineclaims.SaveNewIncidentDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.SaveNewIncidentDocument parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.onlineclaims.SaveNewIncidentDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.SaveNewIncidentDocument parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.onlineclaims.SaveNewIncidentDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.SaveNewIncidentDocument parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.onlineclaims.SaveNewIncidentDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.bagnet.nettracer.ws.onlineclaims.SaveNewIncidentDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.bagnet.nettracer.ws.onlineclaims.SaveNewIncidentDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.bagnet.nettracer.ws.onlineclaims.SaveNewIncidentDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.bagnet.nettracer.ws.onlineclaims.SaveNewIncidentDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
