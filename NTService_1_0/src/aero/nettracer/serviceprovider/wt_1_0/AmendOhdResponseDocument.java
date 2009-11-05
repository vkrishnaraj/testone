/*
 * An XML document type.
 * Localname: amendOhdResponse
 * Namespace: http://wt_1_0.serviceprovider.nettracer.aero
 * Java type: aero.nettracer.serviceprovider.wt_1_0.AmendOhdResponseDocument
 *
 * Automatically generated - do not modify.
 */
package aero.nettracer.serviceprovider.wt_1_0;


/**
 * A document containing one amendOhdResponse(@http://wt_1_0.serviceprovider.nettracer.aero) element.
 *
 * This is a complex type.
 */
public interface AmendOhdResponseDocument extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(AmendOhdResponseDocument.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s062C397454C935FCA9C3F988DC4FD439").resolveHandle("amendohdresponse2933doctype");
    
    /**
     * Gets the "amendOhdResponse" element
     */
    aero.nettracer.serviceprovider.wt_1_0.AmendOhdResponseDocument.AmendOhdResponse getAmendOhdResponse();
    
    /**
     * Sets the "amendOhdResponse" element
     */
    void setAmendOhdResponse(aero.nettracer.serviceprovider.wt_1_0.AmendOhdResponseDocument.AmendOhdResponse amendOhdResponse);
    
    /**
     * Appends and returns a new empty "amendOhdResponse" element
     */
    aero.nettracer.serviceprovider.wt_1_0.AmendOhdResponseDocument.AmendOhdResponse addNewAmendOhdResponse();
    
    /**
     * An XML amendOhdResponse(@http://wt_1_0.serviceprovider.nettracer.aero).
     *
     * This is a complex type.
     */
    public interface AmendOhdResponse extends org.apache.xmlbeans.XmlObject
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(AmendOhdResponse.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s062C397454C935FCA9C3F988DC4FD439").resolveHandle("amendohdresponseec2celemtype");
        
        /**
         * Gets the "return" element
         */
        aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse getReturn();
        
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
        void setReturn(aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse xreturn);
        
        /**
         * Appends and returns a new empty "return" element
         */
        aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse addNewReturn();
        
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
            public static aero.nettracer.serviceprovider.wt_1_0.AmendOhdResponseDocument.AmendOhdResponse newInstance() {
              return (aero.nettracer.serviceprovider.wt_1_0.AmendOhdResponseDocument.AmendOhdResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static aero.nettracer.serviceprovider.wt_1_0.AmendOhdResponseDocument.AmendOhdResponse newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (aero.nettracer.serviceprovider.wt_1_0.AmendOhdResponseDocument.AmendOhdResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static aero.nettracer.serviceprovider.wt_1_0.AmendOhdResponseDocument newInstance() {
          return (aero.nettracer.serviceprovider.wt_1_0.AmendOhdResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static aero.nettracer.serviceprovider.wt_1_0.AmendOhdResponseDocument newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (aero.nettracer.serviceprovider.wt_1_0.AmendOhdResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static aero.nettracer.serviceprovider.wt_1_0.AmendOhdResponseDocument parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (aero.nettracer.serviceprovider.wt_1_0.AmendOhdResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static aero.nettracer.serviceprovider.wt_1_0.AmendOhdResponseDocument parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (aero.nettracer.serviceprovider.wt_1_0.AmendOhdResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static aero.nettracer.serviceprovider.wt_1_0.AmendOhdResponseDocument parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aero.nettracer.serviceprovider.wt_1_0.AmendOhdResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static aero.nettracer.serviceprovider.wt_1_0.AmendOhdResponseDocument parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aero.nettracer.serviceprovider.wt_1_0.AmendOhdResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static aero.nettracer.serviceprovider.wt_1_0.AmendOhdResponseDocument parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aero.nettracer.serviceprovider.wt_1_0.AmendOhdResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static aero.nettracer.serviceprovider.wt_1_0.AmendOhdResponseDocument parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aero.nettracer.serviceprovider.wt_1_0.AmendOhdResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static aero.nettracer.serviceprovider.wt_1_0.AmendOhdResponseDocument parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aero.nettracer.serviceprovider.wt_1_0.AmendOhdResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static aero.nettracer.serviceprovider.wt_1_0.AmendOhdResponseDocument parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aero.nettracer.serviceprovider.wt_1_0.AmendOhdResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static aero.nettracer.serviceprovider.wt_1_0.AmendOhdResponseDocument parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aero.nettracer.serviceprovider.wt_1_0.AmendOhdResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static aero.nettracer.serviceprovider.wt_1_0.AmendOhdResponseDocument parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aero.nettracer.serviceprovider.wt_1_0.AmendOhdResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static aero.nettracer.serviceprovider.wt_1_0.AmendOhdResponseDocument parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (aero.nettracer.serviceprovider.wt_1_0.AmendOhdResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static aero.nettracer.serviceprovider.wt_1_0.AmendOhdResponseDocument parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (aero.nettracer.serviceprovider.wt_1_0.AmendOhdResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static aero.nettracer.serviceprovider.wt_1_0.AmendOhdResponseDocument parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (aero.nettracer.serviceprovider.wt_1_0.AmendOhdResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static aero.nettracer.serviceprovider.wt_1_0.AmendOhdResponseDocument parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (aero.nettracer.serviceprovider.wt_1_0.AmendOhdResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static aero.nettracer.serviceprovider.wt_1_0.AmendOhdResponseDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (aero.nettracer.serviceprovider.wt_1_0.AmendOhdResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static aero.nettracer.serviceprovider.wt_1_0.AmendOhdResponseDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (aero.nettracer.serviceprovider.wt_1_0.AmendOhdResponseDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
