/*
 * An XML document type.
 * Localname: authAdminUser
 * Namespace: http://onlineclaims.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.onlineclaims;


/**
 * A document containing one authAdminUser(@http://onlineclaims.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public interface AuthAdminUserDocument extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(AuthAdminUserDocument.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sECB0ECF159DBAD03265284E0F73AEDC5").resolveHandle("authadminuserd185doctype");
    
    /**
     * Gets the "authAdminUser" element
     */
    com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserDocument.AuthAdminUser getAuthAdminUser();
    
    /**
     * Sets the "authAdminUser" element
     */
    void setAuthAdminUser(com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserDocument.AuthAdminUser authAdminUser);
    
    /**
     * Appends and returns a new empty "authAdminUser" element
     */
    com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserDocument.AuthAdminUser addNewAuthAdminUser();
    
    /**
     * An XML authAdminUser(@http://onlineclaims.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public interface AuthAdminUser extends org.apache.xmlbeans.XmlObject
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(AuthAdminUser.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sECB0ECF159DBAD03265284E0F73AEDC5").resolveHandle("authadminuser31e3elemtype");
        
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
            public static com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserDocument.AuthAdminUser newInstance() {
              return (com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserDocument.AuthAdminUser) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserDocument.AuthAdminUser newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserDocument.AuthAdminUser) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserDocument newInstance() {
          return (com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserDocument newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserDocument parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserDocument parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserDocument parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserDocument parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserDocument parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserDocument parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserDocument parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserDocument parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserDocument parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserDocument parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserDocument parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserDocument parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserDocument parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserDocument parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
