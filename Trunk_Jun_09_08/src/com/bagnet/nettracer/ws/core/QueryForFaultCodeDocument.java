/*
 * An XML document type.
 * Localname: queryForFaultCode
 * Namespace: http://core.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.core.QueryForFaultCodeDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.core;


/**
 * A document containing one queryForFaultCode(@http://core.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public interface QueryForFaultCodeDocument extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(QueryForFaultCodeDocument.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s46C2824FBA7D2A16453E6357E9EA151B").resolveHandle("queryforfaultcode5636doctype");
    
    /**
     * Gets the "queryForFaultCode" element
     */
    com.bagnet.nettracer.ws.core.QueryForFaultCodeDocument.QueryForFaultCode getQueryForFaultCode();
    
    /**
     * Sets the "queryForFaultCode" element
     */
    void setQueryForFaultCode(com.bagnet.nettracer.ws.core.QueryForFaultCodeDocument.QueryForFaultCode queryForFaultCode);
    
    /**
     * Appends and returns a new empty "queryForFaultCode" element
     */
    com.bagnet.nettracer.ws.core.QueryForFaultCodeDocument.QueryForFaultCode addNewQueryForFaultCode();
    
    /**
     * An XML queryForFaultCode(@http://core.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public interface QueryForFaultCode extends org.apache.xmlbeans.XmlObject
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(QueryForFaultCode.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s46C2824FBA7D2A16453E6357E9EA151B").resolveHandle("queryforfaultcodefbf0elemtype");
        
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
         * Gets the "faultStation" element
         */
        java.lang.String getFaultStation();
        
        /**
         * Gets (as xml) the "faultStation" element
         */
        org.apache.xmlbeans.XmlString xgetFaultStation();
        
        /**
         * Tests for nil "faultStation" element
         */
        boolean isNilFaultStation();
        
        /**
         * True if has "faultStation" element
         */
        boolean isSetFaultStation();
        
        /**
         * Sets the "faultStation" element
         */
        void setFaultStation(java.lang.String faultStation);
        
        /**
         * Sets (as xml) the "faultStation" element
         */
        void xsetFaultStation(org.apache.xmlbeans.XmlString faultStation);
        
        /**
         * Nils the "faultStation" element
         */
        void setNilFaultStation();
        
        /**
         * Unsets the "faultStation" element
         */
        void unsetFaultStation();
        
        /**
         * Gets the "companyCode" element
         */
        java.lang.String getCompanyCode();
        
        /**
         * Gets (as xml) the "companyCode" element
         */
        org.apache.xmlbeans.XmlString xgetCompanyCode();
        
        /**
         * Tests for nil "companyCode" element
         */
        boolean isNilCompanyCode();
        
        /**
         * True if has "companyCode" element
         */
        boolean isSetCompanyCode();
        
        /**
         * Sets the "companyCode" element
         */
        void setCompanyCode(java.lang.String companyCode);
        
        /**
         * Sets (as xml) the "companyCode" element
         */
        void xsetCompanyCode(org.apache.xmlbeans.XmlString companyCode);
        
        /**
         * Nils the "companyCode" element
         */
        void setNilCompanyCode();
        
        /**
         * Unsets the "companyCode" element
         */
        void unsetCompanyCode();
        
        /**
         * Gets the "faultCode" element
         */
        int getFaultCode();
        
        /**
         * Gets (as xml) the "faultCode" element
         */
        org.apache.xmlbeans.XmlInt xgetFaultCode();
        
        /**
         * True if has "faultCode" element
         */
        boolean isSetFaultCode();
        
        /**
         * Sets the "faultCode" element
         */
        void setFaultCode(int faultCode);
        
        /**
         * Sets (as xml) the "faultCode" element
         */
        void xsetFaultCode(org.apache.xmlbeans.XmlInt faultCode);
        
        /**
         * Unsets the "faultCode" element
         */
        void unsetFaultCode();
        
        /**
         * Gets the "IncidentType" element
         */
        java.lang.String getIncidentType();
        
        /**
         * Gets (as xml) the "IncidentType" element
         */
        org.apache.xmlbeans.XmlString xgetIncidentType();
        
        /**
         * Tests for nil "IncidentType" element
         */
        boolean isNilIncidentType();
        
        /**
         * True if has "IncidentType" element
         */
        boolean isSetIncidentType();
        
        /**
         * Sets the "IncidentType" element
         */
        void setIncidentType(java.lang.String incidentType);
        
        /**
         * Sets (as xml) the "IncidentType" element
         */
        void xsetIncidentType(org.apache.xmlbeans.XmlString incidentType);
        
        /**
         * Nils the "IncidentType" element
         */
        void setNilIncidentType();
        
        /**
         * Unsets the "IncidentType" element
         */
        void unsetIncidentType();
        
        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        
        public static final class Factory
        {
            public static com.bagnet.nettracer.ws.core.QueryForFaultCodeDocument.QueryForFaultCode newInstance() {
              return (com.bagnet.nettracer.ws.core.QueryForFaultCodeDocument.QueryForFaultCode) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static com.bagnet.nettracer.ws.core.QueryForFaultCodeDocument.QueryForFaultCode newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (com.bagnet.nettracer.ws.core.QueryForFaultCodeDocument.QueryForFaultCode) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static com.bagnet.nettracer.ws.core.QueryForFaultCodeDocument newInstance() {
          return (com.bagnet.nettracer.ws.core.QueryForFaultCodeDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static com.bagnet.nettracer.ws.core.QueryForFaultCodeDocument newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (com.bagnet.nettracer.ws.core.QueryForFaultCodeDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static com.bagnet.nettracer.ws.core.QueryForFaultCodeDocument parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.core.QueryForFaultCodeDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static com.bagnet.nettracer.ws.core.QueryForFaultCodeDocument parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.core.QueryForFaultCodeDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static com.bagnet.nettracer.ws.core.QueryForFaultCodeDocument parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.core.QueryForFaultCodeDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static com.bagnet.nettracer.ws.core.QueryForFaultCodeDocument parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.core.QueryForFaultCodeDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static com.bagnet.nettracer.ws.core.QueryForFaultCodeDocument parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.core.QueryForFaultCodeDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static com.bagnet.nettracer.ws.core.QueryForFaultCodeDocument parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.core.QueryForFaultCodeDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static com.bagnet.nettracer.ws.core.QueryForFaultCodeDocument parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.core.QueryForFaultCodeDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static com.bagnet.nettracer.ws.core.QueryForFaultCodeDocument parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.core.QueryForFaultCodeDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static com.bagnet.nettracer.ws.core.QueryForFaultCodeDocument parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.core.QueryForFaultCodeDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static com.bagnet.nettracer.ws.core.QueryForFaultCodeDocument parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.core.QueryForFaultCodeDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static com.bagnet.nettracer.ws.core.QueryForFaultCodeDocument parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.core.QueryForFaultCodeDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static com.bagnet.nettracer.ws.core.QueryForFaultCodeDocument parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.core.QueryForFaultCodeDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static com.bagnet.nettracer.ws.core.QueryForFaultCodeDocument parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.core.QueryForFaultCodeDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static com.bagnet.nettracer.ws.core.QueryForFaultCodeDocument parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.core.QueryForFaultCodeDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.bagnet.nettracer.ws.core.QueryForFaultCodeDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.bagnet.nettracer.ws.core.QueryForFaultCodeDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.bagnet.nettracer.ws.core.QueryForFaultCodeDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.bagnet.nettracer.ws.core.QueryForFaultCodeDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
