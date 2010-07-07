/*
 * An XML document type.
 * Localname: uploadFile
 * Namespace: http://onlineclaims.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.onlineclaims.UploadFileDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.onlineclaims;


/**
 * A document containing one uploadFile(@http://onlineclaims.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public interface UploadFileDocument extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(UploadFileDocument.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sB761FFC9C65C105A1900CD40FE557D9F").resolveHandle("uploadfile3742doctype");
    
    /**
     * Gets the "uploadFile" element
     */
    com.bagnet.nettracer.ws.onlineclaims.UploadFileDocument.UploadFile getUploadFile();
    
    /**
     * Sets the "uploadFile" element
     */
    void setUploadFile(com.bagnet.nettracer.ws.onlineclaims.UploadFileDocument.UploadFile uploadFile);
    
    /**
     * Appends and returns a new empty "uploadFile" element
     */
    com.bagnet.nettracer.ws.onlineclaims.UploadFileDocument.UploadFile addNewUploadFile();
    
    /**
     * An XML uploadFile(@http://onlineclaims.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public interface UploadFile extends org.apache.xmlbeans.XmlObject
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(UploadFile.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sB761FFC9C65C105A1900CD40FE557D9F").resolveHandle("uploadfile53a9elemtype");
        
        /**
         * Gets the "claimId" element
         */
        long getClaimId();
        
        /**
         * Gets (as xml) the "claimId" element
         */
        org.apache.xmlbeans.XmlLong xgetClaimId();
        
        /**
         * True if has "claimId" element
         */
        boolean isSetClaimId();
        
        /**
         * Sets the "claimId" element
         */
        void setClaimId(long claimId);
        
        /**
         * Sets (as xml) the "claimId" element
         */
        void xsetClaimId(org.apache.xmlbeans.XmlLong claimId);
        
        /**
         * Unsets the "claimId" element
         */
        void unsetClaimId();
        
        /**
         * Gets the "filename" element
         */
        java.lang.String getFilename();
        
        /**
         * Gets (as xml) the "filename" element
         */
        org.apache.xmlbeans.XmlString xgetFilename();
        
        /**
         * Tests for nil "filename" element
         */
        boolean isNilFilename();
        
        /**
         * True if has "filename" element
         */
        boolean isSetFilename();
        
        /**
         * Sets the "filename" element
         */
        void setFilename(java.lang.String filename);
        
        /**
         * Sets (as xml) the "filename" element
         */
        void xsetFilename(org.apache.xmlbeans.XmlString filename);
        
        /**
         * Nils the "filename" element
         */
        void setNilFilename();
        
        /**
         * Unsets the "filename" element
         */
        void unsetFilename();
        
        /**
         * Gets the "file" element
         */
        byte[] getFile();
        
        /**
         * Gets (as xml) the "file" element
         */
        org.apache.xmlbeans.XmlBase64Binary xgetFile();
        
        /**
         * Tests for nil "file" element
         */
        boolean isNilFile();
        
        /**
         * True if has "file" element
         */
        boolean isSetFile();
        
        /**
         * Sets the "file" element
         */
        void setFile(byte[] file);
        
        /**
         * Sets (as xml) the "file" element
         */
        void xsetFile(org.apache.xmlbeans.XmlBase64Binary file);
        
        /**
         * Nils the "file" element
         */
        void setNilFile();
        
        /**
         * Unsets the "file" element
         */
        void unsetFile();
        
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
            public static com.bagnet.nettracer.ws.onlineclaims.UploadFileDocument.UploadFile newInstance() {
              return (com.bagnet.nettracer.ws.onlineclaims.UploadFileDocument.UploadFile) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static com.bagnet.nettracer.ws.onlineclaims.UploadFileDocument.UploadFile newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (com.bagnet.nettracer.ws.onlineclaims.UploadFileDocument.UploadFile) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static com.bagnet.nettracer.ws.onlineclaims.UploadFileDocument newInstance() {
          return (com.bagnet.nettracer.ws.onlineclaims.UploadFileDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.UploadFileDocument newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (com.bagnet.nettracer.ws.onlineclaims.UploadFileDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static com.bagnet.nettracer.ws.onlineclaims.UploadFileDocument parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.onlineclaims.UploadFileDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.UploadFileDocument parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.onlineclaims.UploadFileDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static com.bagnet.nettracer.ws.onlineclaims.UploadFileDocument parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.onlineclaims.UploadFileDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.UploadFileDocument parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.onlineclaims.UploadFileDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.UploadFileDocument parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.onlineclaims.UploadFileDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.UploadFileDocument parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.onlineclaims.UploadFileDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.UploadFileDocument parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.onlineclaims.UploadFileDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.UploadFileDocument parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.onlineclaims.UploadFileDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.UploadFileDocument parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.onlineclaims.UploadFileDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.UploadFileDocument parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.onlineclaims.UploadFileDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.UploadFileDocument parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.onlineclaims.UploadFileDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.UploadFileDocument parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.onlineclaims.UploadFileDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.UploadFileDocument parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.onlineclaims.UploadFileDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.UploadFileDocument parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.onlineclaims.UploadFileDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.bagnet.nettracer.ws.onlineclaims.UploadFileDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.bagnet.nettracer.ws.onlineclaims.UploadFileDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.bagnet.nettracer.ws.onlineclaims.UploadFileDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.bagnet.nettracer.ws.onlineclaims.UploadFileDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
