/*
 * An XML document type.
 * Localname: getAdvancedIncidentsPVPnr
 * Namespace: http://passengerview.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.passengerview;


/**
 * A document containing one getAdvancedIncidentsPVPnr(@http://passengerview.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public interface GetAdvancedIncidentsPVPnrDocument extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(GetAdvancedIncidentsPVPnrDocument.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s565D75BFF2D11A660C05CC688A6DB709").resolveHandle("getadvancedincidentspvpnr5911doctype");
    
    /**
     * Gets the "getAdvancedIncidentsPVPnr" element
     */
    com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrDocument.GetAdvancedIncidentsPVPnr getGetAdvancedIncidentsPVPnr();
    
    /**
     * Sets the "getAdvancedIncidentsPVPnr" element
     */
    void setGetAdvancedIncidentsPVPnr(com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrDocument.GetAdvancedIncidentsPVPnr getAdvancedIncidentsPVPnr);
    
    /**
     * Appends and returns a new empty "getAdvancedIncidentsPVPnr" element
     */
    com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrDocument.GetAdvancedIncidentsPVPnr addNewGetAdvancedIncidentsPVPnr();
    
    /**
     * An XML getAdvancedIncidentsPVPnr(@http://passengerview.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public interface GetAdvancedIncidentsPVPnr extends org.apache.xmlbeans.XmlObject
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(GetAdvancedIncidentsPVPnr.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s565D75BFF2D11A660C05CC688A6DB709").resolveHandle("getadvancedincidentspvpnr7500elemtype");
        
        /**
         * Gets the "recordLocator" element
         */
        java.lang.String getRecordLocator();
        
        /**
         * Gets (as xml) the "recordLocator" element
         */
        org.apache.xmlbeans.XmlString xgetRecordLocator();
        
        /**
         * Tests for nil "recordLocator" element
         */
        boolean isNilRecordLocator();
        
        /**
         * True if has "recordLocator" element
         */
        boolean isSetRecordLocator();
        
        /**
         * Sets the "recordLocator" element
         */
        void setRecordLocator(java.lang.String recordLocator);
        
        /**
         * Sets (as xml) the "recordLocator" element
         */
        void xsetRecordLocator(org.apache.xmlbeans.XmlString recordLocator);
        
        /**
         * Nils the "recordLocator" element
         */
        void setNilRecordLocator();
        
        /**
         * Unsets the "recordLocator" element
         */
        void unsetRecordLocator();
        
        /**
         * Gets the "callingAgent" element
         */
        java.lang.String getCallingAgent();
        
        /**
         * Gets (as xml) the "callingAgent" element
         */
        org.apache.xmlbeans.XmlString xgetCallingAgent();
        
        /**
         * Tests for nil "callingAgent" element
         */
        boolean isNilCallingAgent();
        
        /**
         * True if has "callingAgent" element
         */
        boolean isSetCallingAgent();
        
        /**
         * Sets the "callingAgent" element
         */
        void setCallingAgent(java.lang.String callingAgent);
        
        /**
         * Sets (as xml) the "callingAgent" element
         */
        void xsetCallingAgent(org.apache.xmlbeans.XmlString callingAgent);
        
        /**
         * Nils the "callingAgent" element
         */
        void setNilCallingAgent();
        
        /**
         * Unsets the "callingAgent" element
         */
        void unsetCallingAgent();
        
        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        
        public static final class Factory
        {
            public static com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrDocument.GetAdvancedIncidentsPVPnr newInstance() {
              return (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrDocument.GetAdvancedIncidentsPVPnr) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrDocument.GetAdvancedIncidentsPVPnr newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrDocument.GetAdvancedIncidentsPVPnr) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrDocument newInstance() {
          return (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrDocument newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrDocument parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrDocument parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrDocument parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrDocument parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrDocument parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrDocument parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrDocument parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrDocument parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrDocument parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrDocument parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrDocument parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrDocument parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrDocument parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrDocument parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
