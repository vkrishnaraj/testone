/*
 * An XML document type.
 * Localname: getPaxView
 * Namespace: http://v1_1.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.v1_1.GetPaxViewDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.v1_1;


/**
 * A document containing one getPaxView(@http://v1_1.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public interface GetPaxViewDocument extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(GetPaxViewDocument.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s0CD22E1FFB756BC4D961DA8746CE10BF").resolveHandle("getpaxview49fedoctype");
    
    /**
     * Gets the "getPaxView" element
     */
    com.bagnet.nettracer.ws.v1_1.GetPaxViewDocument.GetPaxView getGetPaxView();
    
    /**
     * Sets the "getPaxView" element
     */
    void setGetPaxView(com.bagnet.nettracer.ws.v1_1.GetPaxViewDocument.GetPaxView getPaxView);
    
    /**
     * Appends and returns a new empty "getPaxView" element
     */
    com.bagnet.nettracer.ws.v1_1.GetPaxViewDocument.GetPaxView addNewGetPaxView();
    
    /**
     * An XML getPaxView(@http://v1_1.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public interface GetPaxView extends org.apache.xmlbeans.XmlObject
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(GetPaxView.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s0CD22E1FFB756BC4D961DA8746CE10BF").resolveHandle("getpaxviewa5ecelemtype");
        
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
         * Gets the "username" element
         */
        java.lang.String getUsername();
        
        /**
         * Gets (as xml) the "username" element
         */
        org.apache.xmlbeans.XmlString xgetUsername();
        
        /**
         * Tests for nil "username" element
         */
        boolean isNilUsername();
        
        /**
         * True if has "username" element
         */
        boolean isSetUsername();
        
        /**
         * Sets the "username" element
         */
        void setUsername(java.lang.String username);
        
        /**
         * Sets (as xml) the "username" element
         */
        void xsetUsername(org.apache.xmlbeans.XmlString username);
        
        /**
         * Nils the "username" element
         */
        void setNilUsername();
        
        /**
         * Unsets the "username" element
         */
        void unsetUsername();
        
        /**
         * Gets the "password" element
         */
        java.lang.String getPassword();
        
        /**
         * Gets (as xml) the "password" element
         */
        org.apache.xmlbeans.XmlString xgetPassword();
        
        /**
         * Tests for nil "password" element
         */
        boolean isNilPassword();
        
        /**
         * True if has "password" element
         */
        boolean isSetPassword();
        
        /**
         * Sets the "password" element
         */
        void setPassword(java.lang.String password);
        
        /**
         * Sets (as xml) the "password" element
         */
        void xsetPassword(org.apache.xmlbeans.XmlString password);
        
        /**
         * Nils the "password" element
         */
        void setNilPassword();
        
        /**
         * Unsets the "password" element
         */
        void unsetPassword();
        
        /**
         * Gets the "msgsReadByUser" element
         */
        boolean getMsgsReadByUser();
        
        /**
         * Gets (as xml) the "msgsReadByUser" element
         */
        org.apache.xmlbeans.XmlBoolean xgetMsgsReadByUser();
        
        /**
         * True if has "msgsReadByUser" element
         */
        boolean isSetMsgsReadByUser();
        
        /**
         * Sets the "msgsReadByUser" element
         */
        void setMsgsReadByUser(boolean msgsReadByUser);
        
        /**
         * Sets (as xml) the "msgsReadByUser" element
         */
        void xsetMsgsReadByUser(org.apache.xmlbeans.XmlBoolean msgsReadByUser);
        
        /**
         * Unsets the "msgsReadByUser" element
         */
        void unsetMsgsReadByUser();
        
        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        
        public static final class Factory
        {
            public static com.bagnet.nettracer.ws.v1_1.GetPaxViewDocument.GetPaxView newInstance() {
              return (com.bagnet.nettracer.ws.v1_1.GetPaxViewDocument.GetPaxView) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static com.bagnet.nettracer.ws.v1_1.GetPaxViewDocument.GetPaxView newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (com.bagnet.nettracer.ws.v1_1.GetPaxViewDocument.GetPaxView) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static com.bagnet.nettracer.ws.v1_1.GetPaxViewDocument newInstance() {
          return (com.bagnet.nettracer.ws.v1_1.GetPaxViewDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static com.bagnet.nettracer.ws.v1_1.GetPaxViewDocument newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (com.bagnet.nettracer.ws.v1_1.GetPaxViewDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static com.bagnet.nettracer.ws.v1_1.GetPaxViewDocument parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.v1_1.GetPaxViewDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static com.bagnet.nettracer.ws.v1_1.GetPaxViewDocument parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.v1_1.GetPaxViewDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static com.bagnet.nettracer.ws.v1_1.GetPaxViewDocument parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.v1_1.GetPaxViewDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static com.bagnet.nettracer.ws.v1_1.GetPaxViewDocument parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.v1_1.GetPaxViewDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static com.bagnet.nettracer.ws.v1_1.GetPaxViewDocument parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.v1_1.GetPaxViewDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static com.bagnet.nettracer.ws.v1_1.GetPaxViewDocument parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.v1_1.GetPaxViewDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static com.bagnet.nettracer.ws.v1_1.GetPaxViewDocument parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.v1_1.GetPaxViewDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static com.bagnet.nettracer.ws.v1_1.GetPaxViewDocument parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.v1_1.GetPaxViewDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static com.bagnet.nettracer.ws.v1_1.GetPaxViewDocument parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.v1_1.GetPaxViewDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static com.bagnet.nettracer.ws.v1_1.GetPaxViewDocument parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.v1_1.GetPaxViewDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static com.bagnet.nettracer.ws.v1_1.GetPaxViewDocument parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.v1_1.GetPaxViewDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static com.bagnet.nettracer.ws.v1_1.GetPaxViewDocument parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.v1_1.GetPaxViewDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static com.bagnet.nettracer.ws.v1_1.GetPaxViewDocument parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.v1_1.GetPaxViewDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static com.bagnet.nettracer.ws.v1_1.GetPaxViewDocument parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.v1_1.GetPaxViewDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.bagnet.nettracer.ws.v1_1.GetPaxViewDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.bagnet.nettracer.ws.v1_1.GetPaxViewDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.bagnet.nettracer.ws.v1_1.GetPaxViewDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.bagnet.nettracer.ws.v1_1.GetPaxViewDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
