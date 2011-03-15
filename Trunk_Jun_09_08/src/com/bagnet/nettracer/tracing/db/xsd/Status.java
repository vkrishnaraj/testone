/*
 * XML Type:  Status
 * Namespace: http://db.tracing.nettracer.bagnet.com/xsd
 * Java type: com.bagnet.nettracer.tracing.db.xsd.Status
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.tracing.db.xsd;


/**
 * An XML Status(@http://db.tracing.nettracer.bagnet.com/xsd).
 *
 * This is a complex type.
 */
public interface Status extends com.bagnet.nettracer.tracing.db.i8n.xsd.LocaleBasedObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(Status.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s3120780053B2C9C6ED89C37135AF9582").resolveHandle("status2315type");
    
    /**
     * Gets the "key" element
     */
    java.lang.String getKey2();
    
    /**
     * Gets (as xml) the "key" element
     */
    org.apache.xmlbeans.XmlString xgetKey2();
    
    /**
     * Tests for nil "key" element
     */
    boolean isNilKey2();
    
    /**
     * True if has "key" element
     */
    boolean isSetKey2();
    
    /**
     * Sets the "key" element
     */
    void setKey2(java.lang.String key2);
    
    /**
     * Sets (as xml) the "key" element
     */
    void xsetKey2(org.apache.xmlbeans.XmlString key2);
    
    /**
     * Nils the "key" element
     */
    void setNilKey2();
    
    /**
     * Unsets the "key" element
     */
    void unsetKey2();
    
    /**
     * Gets the "status_ID" element
     */
    int getStatusID();
    
    /**
     * Gets (as xml) the "status_ID" element
     */
    org.apache.xmlbeans.XmlInt xgetStatusID();
    
    /**
     * True if has "status_ID" element
     */
    boolean isSetStatusID();
    
    /**
     * Sets the "status_ID" element
     */
    void setStatusID(int statusID);
    
    /**
     * Sets (as xml) the "status_ID" element
     */
    void xsetStatusID(org.apache.xmlbeans.XmlInt statusID);
    
    /**
     * Unsets the "status_ID" element
     */
    void unsetStatusID();
    
    /**
     * Gets the "table_ID" element
     */
    int getTableID();
    
    /**
     * Gets (as xml) the "table_ID" element
     */
    org.apache.xmlbeans.XmlInt xgetTableID();
    
    /**
     * True if has "table_ID" element
     */
    boolean isSetTableID();
    
    /**
     * Sets the "table_ID" element
     */
    void setTableID(int tableID);
    
    /**
     * Sets (as xml) the "table_ID" element
     */
    void xsetTableID(org.apache.xmlbeans.XmlInt tableID);
    
    /**
     * Unsets the "table_ID" element
     */
    void unsetTableID();
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static com.bagnet.nettracer.tracing.db.xsd.Status newInstance() {
          return (com.bagnet.nettracer.tracing.db.xsd.Status) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static com.bagnet.nettracer.tracing.db.xsd.Status newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (com.bagnet.nettracer.tracing.db.xsd.Status) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static com.bagnet.nettracer.tracing.db.xsd.Status parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.tracing.db.xsd.Status) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static com.bagnet.nettracer.tracing.db.xsd.Status parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.tracing.db.xsd.Status) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static com.bagnet.nettracer.tracing.db.xsd.Status parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.tracing.db.xsd.Status) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static com.bagnet.nettracer.tracing.db.xsd.Status parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.tracing.db.xsd.Status) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static com.bagnet.nettracer.tracing.db.xsd.Status parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.tracing.db.xsd.Status) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static com.bagnet.nettracer.tracing.db.xsd.Status parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.tracing.db.xsd.Status) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static com.bagnet.nettracer.tracing.db.xsd.Status parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.tracing.db.xsd.Status) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static com.bagnet.nettracer.tracing.db.xsd.Status parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.tracing.db.xsd.Status) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static com.bagnet.nettracer.tracing.db.xsd.Status parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.tracing.db.xsd.Status) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static com.bagnet.nettracer.tracing.db.xsd.Status parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.tracing.db.xsd.Status) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static com.bagnet.nettracer.tracing.db.xsd.Status parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.tracing.db.xsd.Status) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static com.bagnet.nettracer.tracing.db.xsd.Status parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.tracing.db.xsd.Status) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static com.bagnet.nettracer.tracing.db.xsd.Status parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.tracing.db.xsd.Status) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static com.bagnet.nettracer.tracing.db.xsd.Status parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.tracing.db.xsd.Status) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.bagnet.nettracer.tracing.db.xsd.Status parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.bagnet.nettracer.tracing.db.xsd.Status) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.bagnet.nettracer.tracing.db.xsd.Status parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.bagnet.nettracer.tracing.db.xsd.Status) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
