/*
 * An XML document type.
 * Localname: getAdvancedIncidentsPVPnrResponse
 * Namespace: http://passengerview.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrResponseDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.passengerview;


/**
 * A document containing one getAdvancedIncidentsPVPnrResponse(@http://passengerview.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public interface GetAdvancedIncidentsPVPnrResponseDocument extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(GetAdvancedIncidentsPVPnrResponseDocument.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s565D75BFF2D11A660C05CC688A6DB709").resolveHandle("getadvancedincidentspvpnrresponse3650doctype");
    
    /**
     * Gets the "getAdvancedIncidentsPVPnrResponse" element
     */
    com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrResponseDocument.GetAdvancedIncidentsPVPnrResponse getGetAdvancedIncidentsPVPnrResponse();
    
    /**
     * Sets the "getAdvancedIncidentsPVPnrResponse" element
     */
    void setGetAdvancedIncidentsPVPnrResponse(com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrResponseDocument.GetAdvancedIncidentsPVPnrResponse getAdvancedIncidentsPVPnrResponse);
    
    /**
     * Appends and returns a new empty "getAdvancedIncidentsPVPnrResponse" element
     */
    com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrResponseDocument.GetAdvancedIncidentsPVPnrResponse addNewGetAdvancedIncidentsPVPnrResponse();
    
    /**
     * An XML getAdvancedIncidentsPVPnrResponse(@http://passengerview.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public interface GetAdvancedIncidentsPVPnrResponse extends org.apache.xmlbeans.XmlObject
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(GetAdvancedIncidentsPVPnrResponse.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s565D75BFF2D11A660C05CC688A6DB709").resolveHandle("getadvancedincidentspvpnrresponse8dfeelemtype");
        
        /**
         * Gets array of all "return" elements
         */
        com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident[] getReturnArray();
        
        /**
         * Gets ith "return" element
         */
        com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident getReturnArray(int i);
        
        /**
         * Tests for nil ith "return" element
         */
        boolean isNilReturnArray(int i);
        
        /**
         * Returns number of "return" element
         */
        int sizeOfReturnArray();
        
        /**
         * Sets array of all "return" element
         */
        void setReturnArray(com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident[] xreturnArray);
        
        /**
         * Sets ith "return" element
         */
        void setReturnArray(int i, com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident xreturn);
        
        /**
         * Nils the ith "return" element
         */
        void setNilReturnArray(int i);
        
        /**
         * Inserts and returns a new empty value (as xml) as the ith "return" element
         */
        com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident insertNewReturn(int i);
        
        /**
         * Appends and returns a new empty value (as xml) as the last "return" element
         */
        com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident addNewReturn();
        
        /**
         * Removes the ith "return" element
         */
        void removeReturn(int i);
        
        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        
        public static final class Factory
        {
            public static com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrResponseDocument.GetAdvancedIncidentsPVPnrResponse newInstance() {
              return (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrResponseDocument.GetAdvancedIncidentsPVPnrResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrResponseDocument.GetAdvancedIncidentsPVPnrResponse newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrResponseDocument.GetAdvancedIncidentsPVPnrResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrResponseDocument newInstance() {
          return (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrResponseDocument newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrResponseDocument parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrResponseDocument parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrResponseDocument parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrResponseDocument parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrResponseDocument parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrResponseDocument parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrResponseDocument parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrResponseDocument parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrResponseDocument parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrResponseDocument parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrResponseDocument parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrResponseDocument parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrResponseDocument parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrResponseDocument parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrResponseDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrResponseDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
