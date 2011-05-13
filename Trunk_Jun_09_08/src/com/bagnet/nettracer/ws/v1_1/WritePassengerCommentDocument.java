/*
 * An XML document type.
 * Localname: writePassengerComment
 * Namespace: http://v1_1.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.v1_1.WritePassengerCommentDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.v1_1;


/**
 * A document containing one writePassengerComment(@http://v1_1.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public interface WritePassengerCommentDocument extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(WritePassengerCommentDocument.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s0CD22E1FFB756BC4D961DA8746CE10BF").resolveHandle("writepassengercomment929adoctype");
    
    /**
     * Gets the "writePassengerComment" element
     */
    com.bagnet.nettracer.ws.v1_1.WritePassengerCommentDocument.WritePassengerComment getWritePassengerComment();
    
    /**
     * Sets the "writePassengerComment" element
     */
    void setWritePassengerComment(com.bagnet.nettracer.ws.v1_1.WritePassengerCommentDocument.WritePassengerComment writePassengerComment);
    
    /**
     * Appends and returns a new empty "writePassengerComment" element
     */
    com.bagnet.nettracer.ws.v1_1.WritePassengerCommentDocument.WritePassengerComment addNewWritePassengerComment();
    
    /**
     * An XML writePassengerComment(@http://v1_1.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public interface WritePassengerComment extends org.apache.xmlbeans.XmlObject
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(WritePassengerComment.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s0CD22E1FFB756BC4D961DA8746CE10BF").resolveHandle("writepassengercomment6f6aelemtype");
        
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
         * A factory class with static methods for creating instances
         * of this type.
         */
        
        public static final class Factory
        {
            public static com.bagnet.nettracer.ws.v1_1.WritePassengerCommentDocument.WritePassengerComment newInstance() {
              return (com.bagnet.nettracer.ws.v1_1.WritePassengerCommentDocument.WritePassengerComment) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static com.bagnet.nettracer.ws.v1_1.WritePassengerCommentDocument.WritePassengerComment newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (com.bagnet.nettracer.ws.v1_1.WritePassengerCommentDocument.WritePassengerComment) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static com.bagnet.nettracer.ws.v1_1.WritePassengerCommentDocument newInstance() {
          return (com.bagnet.nettracer.ws.v1_1.WritePassengerCommentDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static com.bagnet.nettracer.ws.v1_1.WritePassengerCommentDocument newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (com.bagnet.nettracer.ws.v1_1.WritePassengerCommentDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static com.bagnet.nettracer.ws.v1_1.WritePassengerCommentDocument parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.v1_1.WritePassengerCommentDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static com.bagnet.nettracer.ws.v1_1.WritePassengerCommentDocument parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.v1_1.WritePassengerCommentDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static com.bagnet.nettracer.ws.v1_1.WritePassengerCommentDocument parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.v1_1.WritePassengerCommentDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static com.bagnet.nettracer.ws.v1_1.WritePassengerCommentDocument parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.v1_1.WritePassengerCommentDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static com.bagnet.nettracer.ws.v1_1.WritePassengerCommentDocument parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.v1_1.WritePassengerCommentDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static com.bagnet.nettracer.ws.v1_1.WritePassengerCommentDocument parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.v1_1.WritePassengerCommentDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static com.bagnet.nettracer.ws.v1_1.WritePassengerCommentDocument parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.v1_1.WritePassengerCommentDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static com.bagnet.nettracer.ws.v1_1.WritePassengerCommentDocument parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.v1_1.WritePassengerCommentDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static com.bagnet.nettracer.ws.v1_1.WritePassengerCommentDocument parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.v1_1.WritePassengerCommentDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static com.bagnet.nettracer.ws.v1_1.WritePassengerCommentDocument parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.v1_1.WritePassengerCommentDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static com.bagnet.nettracer.ws.v1_1.WritePassengerCommentDocument parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.v1_1.WritePassengerCommentDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static com.bagnet.nettracer.ws.v1_1.WritePassengerCommentDocument parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.v1_1.WritePassengerCommentDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static com.bagnet.nettracer.ws.v1_1.WritePassengerCommentDocument parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.v1_1.WritePassengerCommentDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static com.bagnet.nettracer.ws.v1_1.WritePassengerCommentDocument parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.v1_1.WritePassengerCommentDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.bagnet.nettracer.ws.v1_1.WritePassengerCommentDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.bagnet.nettracer.ws.v1_1.WritePassengerCommentDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.bagnet.nettracer.ws.v1_1.WritePassengerCommentDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.bagnet.nettracer.ws.v1_1.WritePassengerCommentDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
