/*
 * XML Type:  Message
 * Namespace: http://onlineclaims.ws.nettracer.bagnet.com/xsd
 * Java type: com.bagnet.nettracer.ws.onlineclaims.xsd.Message
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.onlineclaims.xsd;


/**
 * An XML Message(@http://onlineclaims.ws.nettracer.bagnet.com/xsd).
 *
 * This is a complex type.
 */
public interface Message extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(Message.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s47918F556B545B4B159263E4D62D20A1").resolveHandle("message6584type");
    
    /**
     * Gets the "dateCreated" element
     */
    java.util.Calendar getDateCreated();
    
    /**
     * Gets (as xml) the "dateCreated" element
     */
    org.apache.xmlbeans.XmlDateTime xgetDateCreated();
    
    /**
     * Tests for nil "dateCreated" element
     */
    boolean isNilDateCreated();
    
    /**
     * True if has "dateCreated" element
     */
    boolean isSetDateCreated();
    
    /**
     * Sets the "dateCreated" element
     */
    void setDateCreated(java.util.Calendar dateCreated);
    
    /**
     * Sets (as xml) the "dateCreated" element
     */
    void xsetDateCreated(org.apache.xmlbeans.XmlDateTime dateCreated);
    
    /**
     * Nils the "dateCreated" element
     */
    void setNilDateCreated();
    
    /**
     * Unsets the "dateCreated" element
     */
    void unsetDateCreated();
    
    /**
     * Gets the "dateReviewed" element
     */
    java.util.Calendar getDateReviewed();
    
    /**
     * Gets (as xml) the "dateReviewed" element
     */
    org.apache.xmlbeans.XmlDateTime xgetDateReviewed();
    
    /**
     * Tests for nil "dateReviewed" element
     */
    boolean isNilDateReviewed();
    
    /**
     * True if has "dateReviewed" element
     */
    boolean isSetDateReviewed();
    
    /**
     * Sets the "dateReviewed" element
     */
    void setDateReviewed(java.util.Calendar dateReviewed);
    
    /**
     * Sets (as xml) the "dateReviewed" element
     */
    void xsetDateReviewed(org.apache.xmlbeans.XmlDateTime dateReviewed);
    
    /**
     * Nils the "dateReviewed" element
     */
    void setNilDateReviewed();
    
    /**
     * Unsets the "dateReviewed" element
     */
    void unsetDateReviewed();
    
    /**
     * Gets the "id" element
     */
    long getId();
    
    /**
     * Gets (as xml) the "id" element
     */
    org.apache.xmlbeans.XmlLong xgetId();
    
    /**
     * True if has "id" element
     */
    boolean isSetId();
    
    /**
     * Sets the "id" element
     */
    void setId(long id);
    
    /**
     * Sets (as xml) the "id" element
     */
    void xsetId(org.apache.xmlbeans.XmlLong id);
    
    /**
     * Unsets the "id" element
     */
    void unsetId();
    
    /**
     * Gets the "message" element
     */
    java.lang.String getMessage();
    
    /**
     * Gets (as xml) the "message" element
     */
    org.apache.xmlbeans.XmlString xgetMessage();
    
    /**
     * Tests for nil "message" element
     */
    boolean isNilMessage();
    
    /**
     * True if has "message" element
     */
    boolean isSetMessage();
    
    /**
     * Sets the "message" element
     */
    void setMessage(java.lang.String message);
    
    /**
     * Sets (as xml) the "message" element
     */
    void xsetMessage(org.apache.xmlbeans.XmlString message);
    
    /**
     * Nils the "message" element
     */
    void setNilMessage();
    
    /**
     * Unsets the "message" element
     */
    void unsetMessage();
    
    /**
     * Gets the "publish" element
     */
    boolean getPublish();
    
    /**
     * Gets (as xml) the "publish" element
     */
    org.apache.xmlbeans.XmlBoolean xgetPublish();
    
    /**
     * True if has "publish" element
     */
    boolean isSetPublish();
    
    /**
     * Sets the "publish" element
     */
    void setPublish(boolean publish);
    
    /**
     * Sets (as xml) the "publish" element
     */
    void xsetPublish(org.apache.xmlbeans.XmlBoolean publish);
    
    /**
     * Unsets the "publish" element
     */
    void unsetPublish();
    
    /**
     * Gets the "statusId" element
     */
    int getStatusId();
    
    /**
     * Gets (as xml) the "statusId" element
     */
    org.apache.xmlbeans.XmlInt xgetStatusId();
    
    /**
     * True if has "statusId" element
     */
    boolean isSetStatusId();
    
    /**
     * Sets the "statusId" element
     */
    void setStatusId(int statusId);
    
    /**
     * Sets (as xml) the "statusId" element
     */
    void xsetStatusId(org.apache.xmlbeans.XmlInt statusId);
    
    /**
     * Unsets the "statusId" element
     */
    void unsetStatusId();
    
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
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Message newInstance() {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Message) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Message newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Message) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Message parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Message) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Message parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Message) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Message parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Message) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Message parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Message) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Message parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Message) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Message parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Message) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Message parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Message) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Message parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Message) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Message parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Message) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Message parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Message) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Message parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Message) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Message parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Message) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Message parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Message) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Message parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Message) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Message parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Message) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Message parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Message) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
