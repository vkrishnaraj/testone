/*
 * An XML document type.
 * Localname: writePassengerCommentResponse
 * Namespace: http://v1_1.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.v1_1.WritePassengerCommentResponseDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.v1_1;


/**
 * A document containing one writePassengerCommentResponse(@http://v1_1.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public interface WritePassengerCommentResponseDocument extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(WritePassengerCommentResponseDocument.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s0CD22E1FFB756BC4D961DA8746CE10BF").resolveHandle("writepassengercommentresponsef37bdoctype");
    
    /**
     * Gets the "writePassengerCommentResponse" element
     */
    com.bagnet.nettracer.ws.v1_1.WritePassengerCommentResponseDocument.WritePassengerCommentResponse getWritePassengerCommentResponse();
    
    /**
     * Sets the "writePassengerCommentResponse" element
     */
    void setWritePassengerCommentResponse(com.bagnet.nettracer.ws.v1_1.WritePassengerCommentResponseDocument.WritePassengerCommentResponse writePassengerCommentResponse);
    
    /**
     * Appends and returns a new empty "writePassengerCommentResponse" element
     */
    com.bagnet.nettracer.ws.v1_1.WritePassengerCommentResponseDocument.WritePassengerCommentResponse addNewWritePassengerCommentResponse();
    
    /**
     * An XML writePassengerCommentResponse(@http://v1_1.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public interface WritePassengerCommentResponse extends org.apache.xmlbeans.XmlObject
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(WritePassengerCommentResponse.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s0CD22E1FFB756BC4D961DA8746CE10BF").resolveHandle("writepassengercommentresponse4c2celemtype");
        
        /**
         * Gets the "return" element
         */
        com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVIncident getReturn();
        
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
        void setReturn(com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVIncident xreturn);
        
        /**
         * Appends and returns a new empty "return" element
         */
        com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVIncident addNewReturn();
        
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
        
        public static final class Factory
        {
            public static com.bagnet.nettracer.ws.v1_1.WritePassengerCommentResponseDocument.WritePassengerCommentResponse newInstance() {
              return (com.bagnet.nettracer.ws.v1_1.WritePassengerCommentResponseDocument.WritePassengerCommentResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static com.bagnet.nettracer.ws.v1_1.WritePassengerCommentResponseDocument.WritePassengerCommentResponse newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (com.bagnet.nettracer.ws.v1_1.WritePassengerCommentResponseDocument.WritePassengerCommentResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static com.bagnet.nettracer.ws.v1_1.WritePassengerCommentResponseDocument newInstance() {
          return (com.bagnet.nettracer.ws.v1_1.WritePassengerCommentResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static com.bagnet.nettracer.ws.v1_1.WritePassengerCommentResponseDocument newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (com.bagnet.nettracer.ws.v1_1.WritePassengerCommentResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static com.bagnet.nettracer.ws.v1_1.WritePassengerCommentResponseDocument parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.v1_1.WritePassengerCommentResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static com.bagnet.nettracer.ws.v1_1.WritePassengerCommentResponseDocument parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.v1_1.WritePassengerCommentResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static com.bagnet.nettracer.ws.v1_1.WritePassengerCommentResponseDocument parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.v1_1.WritePassengerCommentResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static com.bagnet.nettracer.ws.v1_1.WritePassengerCommentResponseDocument parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.v1_1.WritePassengerCommentResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static com.bagnet.nettracer.ws.v1_1.WritePassengerCommentResponseDocument parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.v1_1.WritePassengerCommentResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static com.bagnet.nettracer.ws.v1_1.WritePassengerCommentResponseDocument parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.v1_1.WritePassengerCommentResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static com.bagnet.nettracer.ws.v1_1.WritePassengerCommentResponseDocument parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.v1_1.WritePassengerCommentResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static com.bagnet.nettracer.ws.v1_1.WritePassengerCommentResponseDocument parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.v1_1.WritePassengerCommentResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static com.bagnet.nettracer.ws.v1_1.WritePassengerCommentResponseDocument parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.v1_1.WritePassengerCommentResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static com.bagnet.nettracer.ws.v1_1.WritePassengerCommentResponseDocument parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.v1_1.WritePassengerCommentResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static com.bagnet.nettracer.ws.v1_1.WritePassengerCommentResponseDocument parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.v1_1.WritePassengerCommentResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static com.bagnet.nettracer.ws.v1_1.WritePassengerCommentResponseDocument parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.v1_1.WritePassengerCommentResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static com.bagnet.nettracer.ws.v1_1.WritePassengerCommentResponseDocument parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.v1_1.WritePassengerCommentResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static com.bagnet.nettracer.ws.v1_1.WritePassengerCommentResponseDocument parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.v1_1.WritePassengerCommentResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.bagnet.nettracer.ws.v1_1.WritePassengerCommentResponseDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.bagnet.nettracer.ws.v1_1.WritePassengerCommentResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.bagnet.nettracer.ws.v1_1.WritePassengerCommentResponseDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.bagnet.nettracer.ws.v1_1.WritePassengerCommentResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
