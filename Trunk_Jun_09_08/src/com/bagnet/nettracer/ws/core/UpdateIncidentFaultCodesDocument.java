/*
 * An XML document type.
 * Localname: updateIncidentFaultCodes
 * Namespace: http://core.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.core;


/**
 * A document containing one updateIncidentFaultCodes(@http://core.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public interface UpdateIncidentFaultCodesDocument extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(UpdateIncidentFaultCodesDocument.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s45E1E5C329E9FFAAC1A63D087AEED0C2").resolveHandle("updateincidentfaultcodes0679doctype");
    
    /**
     * Gets the "updateIncidentFaultCodes" element
     */
    com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesDocument.UpdateIncidentFaultCodes getUpdateIncidentFaultCodes();
    
    /**
     * Sets the "updateIncidentFaultCodes" element
     */
    void setUpdateIncidentFaultCodes(com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesDocument.UpdateIncidentFaultCodes updateIncidentFaultCodes);
    
    /**
     * Appends and returns a new empty "updateIncidentFaultCodes" element
     */
    com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesDocument.UpdateIncidentFaultCodes addNewUpdateIncidentFaultCodes();
    
    /**
     * An XML updateIncidentFaultCodes(@http://core.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public interface UpdateIncidentFaultCodes extends org.apache.xmlbeans.XmlObject
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(UpdateIncidentFaultCodes.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s45E1E5C329E9FFAAC1A63D087AEED0C2").resolveHandle("updateincidentfaultcodesf43eelemtype");
        
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
         * Gets the "incidentId" element
         */
        java.lang.String getIncidentId();
        
        /**
         * Gets (as xml) the "incidentId" element
         */
        org.apache.xmlbeans.XmlString xgetIncidentId();
        
        /**
         * Tests for nil "incidentId" element
         */
        boolean isNilIncidentId();
        
        /**
         * True if has "incidentId" element
         */
        boolean isSetIncidentId();
        
        /**
         * Sets the "incidentId" element
         */
        void setIncidentId(java.lang.String incidentId);
        
        /**
         * Sets (as xml) the "incidentId" element
         */
        void xsetIncidentId(org.apache.xmlbeans.XmlString incidentId);
        
        /**
         * Nils the "incidentId" element
         */
        void setNilIncidentId();
        
        /**
         * Unsets the "incidentId" element
         */
        void unsetIncidentId();
        
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
         * Gets the "comment" element
         */
        java.lang.String getComment();
        
        /**
         * Gets (as xml) the "comment" element
         */
        org.apache.xmlbeans.XmlString xgetComment();
        
        /**
         * Tests for nil "comment" element
         */
        boolean isNilComment();
        
        /**
         * True if has "comment" element
         */
        boolean isSetComment();
        
        /**
         * Sets the "comment" element
         */
        void setComment(java.lang.String comment);
        
        /**
         * Sets (as xml) the "comment" element
         */
        void xsetComment(org.apache.xmlbeans.XmlString comment);
        
        /**
         * Nils the "comment" element
         */
        void setNilComment();
        
        /**
         * Unsets the "comment" element
         */
        void unsetComment();
        
        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        
        public static final class Factory
        {
            public static com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesDocument.UpdateIncidentFaultCodes newInstance() {
              return (com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesDocument.UpdateIncidentFaultCodes) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesDocument.UpdateIncidentFaultCodes newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesDocument.UpdateIncidentFaultCodes) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesDocument newInstance() {
          return (com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesDocument newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesDocument parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesDocument parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesDocument parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesDocument parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesDocument parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesDocument parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesDocument parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesDocument parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesDocument parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesDocument parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesDocument parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesDocument parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesDocument parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesDocument parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
