/*
 * An XML document type.
 * Localname: UpdateBdoDelivery
 * Namespace: http://core.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.core.UpdateBdoDeliveryDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.core;


/**
 * A document containing one UpdateBdoDelivery(@http://core.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public interface UpdateBdoDeliveryDocument extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(UpdateBdoDeliveryDocument.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s46C2824FBA7D2A16453E6357E9EA151B").resolveHandle("updatebdodeliverybec0doctype");
    
    /**
     * Gets the "UpdateBdoDelivery" element
     */
    com.bagnet.nettracer.ws.core.UpdateBdoDeliveryDocument.UpdateBdoDelivery getUpdateBdoDelivery();
    
    /**
     * Sets the "UpdateBdoDelivery" element
     */
    void setUpdateBdoDelivery(com.bagnet.nettracer.ws.core.UpdateBdoDeliveryDocument.UpdateBdoDelivery updateBdoDelivery);
    
    /**
     * Appends and returns a new empty "UpdateBdoDelivery" element
     */
    com.bagnet.nettracer.ws.core.UpdateBdoDeliveryDocument.UpdateBdoDelivery addNewUpdateBdoDelivery();
    
    /**
     * An XML UpdateBdoDelivery(@http://core.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public interface UpdateBdoDelivery extends org.apache.xmlbeans.XmlObject
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(UpdateBdoDelivery.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s46C2824FBA7D2A16453E6357E9EA151B").resolveHandle("updatebdodelivery2404elemtype");
        
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
         * Gets the "companycode" element
         */
        java.lang.String getCompanycode();
        
        /**
         * Gets (as xml) the "companycode" element
         */
        org.apache.xmlbeans.XmlString xgetCompanycode();
        
        /**
         * Tests for nil "companycode" element
         */
        boolean isNilCompanycode();
        
        /**
         * True if has "companycode" element
         */
        boolean isSetCompanycode();
        
        /**
         * Sets the "companycode" element
         */
        void setCompanycode(java.lang.String companycode);
        
        /**
         * Sets (as xml) the "companycode" element
         */
        void xsetCompanycode(org.apache.xmlbeans.XmlString companycode);
        
        /**
         * Nils the "companycode" element
         */
        void setNilCompanycode();
        
        /**
         * Unsets the "companycode" element
         */
        void unsetCompanycode();
        
        /**
         * Gets the "updates" element
         */
        com.bagnet.nettracer.ws.core.pojo.xsd.BdoStatusUpdate getUpdates();
        
        /**
         * Tests for nil "updates" element
         */
        boolean isNilUpdates();
        
        /**
         * True if has "updates" element
         */
        boolean isSetUpdates();
        
        /**
         * Sets the "updates" element
         */
        void setUpdates(com.bagnet.nettracer.ws.core.pojo.xsd.BdoStatusUpdate updates);
        
        /**
         * Appends and returns a new empty "updates" element
         */
        com.bagnet.nettracer.ws.core.pojo.xsd.BdoStatusUpdate addNewUpdates();
        
        /**
         * Nils the "updates" element
         */
        void setNilUpdates();
        
        /**
         * Unsets the "updates" element
         */
        void unsetUpdates();
        
        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        
        public static final class Factory
        {
            public static com.bagnet.nettracer.ws.core.UpdateBdoDeliveryDocument.UpdateBdoDelivery newInstance() {
              return (com.bagnet.nettracer.ws.core.UpdateBdoDeliveryDocument.UpdateBdoDelivery) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static com.bagnet.nettracer.ws.core.UpdateBdoDeliveryDocument.UpdateBdoDelivery newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (com.bagnet.nettracer.ws.core.UpdateBdoDeliveryDocument.UpdateBdoDelivery) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static com.bagnet.nettracer.ws.core.UpdateBdoDeliveryDocument newInstance() {
          return (com.bagnet.nettracer.ws.core.UpdateBdoDeliveryDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static com.bagnet.nettracer.ws.core.UpdateBdoDeliveryDocument newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (com.bagnet.nettracer.ws.core.UpdateBdoDeliveryDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static com.bagnet.nettracer.ws.core.UpdateBdoDeliveryDocument parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.core.UpdateBdoDeliveryDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static com.bagnet.nettracer.ws.core.UpdateBdoDeliveryDocument parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.core.UpdateBdoDeliveryDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static com.bagnet.nettracer.ws.core.UpdateBdoDeliveryDocument parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.core.UpdateBdoDeliveryDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static com.bagnet.nettracer.ws.core.UpdateBdoDeliveryDocument parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.core.UpdateBdoDeliveryDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static com.bagnet.nettracer.ws.core.UpdateBdoDeliveryDocument parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.core.UpdateBdoDeliveryDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static com.bagnet.nettracer.ws.core.UpdateBdoDeliveryDocument parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.core.UpdateBdoDeliveryDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static com.bagnet.nettracer.ws.core.UpdateBdoDeliveryDocument parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.core.UpdateBdoDeliveryDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static com.bagnet.nettracer.ws.core.UpdateBdoDeliveryDocument parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.core.UpdateBdoDeliveryDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static com.bagnet.nettracer.ws.core.UpdateBdoDeliveryDocument parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.core.UpdateBdoDeliveryDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static com.bagnet.nettracer.ws.core.UpdateBdoDeliveryDocument parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.core.UpdateBdoDeliveryDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static com.bagnet.nettracer.ws.core.UpdateBdoDeliveryDocument parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.core.UpdateBdoDeliveryDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static com.bagnet.nettracer.ws.core.UpdateBdoDeliveryDocument parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.core.UpdateBdoDeliveryDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static com.bagnet.nettracer.ws.core.UpdateBdoDeliveryDocument parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.core.UpdateBdoDeliveryDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static com.bagnet.nettracer.ws.core.UpdateBdoDeliveryDocument parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.core.UpdateBdoDeliveryDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.bagnet.nettracer.ws.core.UpdateBdoDeliveryDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.bagnet.nettracer.ws.core.UpdateBdoDeliveryDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.bagnet.nettracer.ws.core.UpdateBdoDeliveryDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.bagnet.nettracer.ws.core.UpdateBdoDeliveryDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
