/*
 * XML Type:  NTDateFormat
 * Namespace: http://db.tracing.nettracer.bagnet.com/xsd
 * Java type: com.bagnet.nettracer.tracing.db.xsd.NTDateFormat
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.tracing.db.xsd;


/**
 * An XML NTDateFormat(@http://db.tracing.nettracer.bagnet.com/xsd).
 *
 * This is a complex type.
 */
public interface NTDateFormat extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(NTDateFormat.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s3120780053B2C9C6ED89C37135AF9582").resolveHandle("ntdateformat949ctype");
    
    /**
     * Gets the "dateformat_ID" element
     */
    int getDateformatID();
    
    /**
     * Gets (as xml) the "dateformat_ID" element
     */
    org.apache.xmlbeans.XmlInt xgetDateformatID();
    
    /**
     * True if has "dateformat_ID" element
     */
    boolean isSetDateformatID();
    
    /**
     * Sets the "dateformat_ID" element
     */
    void setDateformatID(int dateformatID);
    
    /**
     * Sets (as xml) the "dateformat_ID" element
     */
    void xsetDateformatID(org.apache.xmlbeans.XmlInt dateformatID);
    
    /**
     * Unsets the "dateformat_ID" element
     */
    void unsetDateformatID();
    
    /**
     * Gets the "format" element
     */
    java.lang.String getFormat();
    
    /**
     * Gets (as xml) the "format" element
     */
    org.apache.xmlbeans.XmlString xgetFormat();
    
    /**
     * Tests for nil "format" element
     */
    boolean isNilFormat();
    
    /**
     * True if has "format" element
     */
    boolean isSetFormat();
    
    /**
     * Sets the "format" element
     */
    void setFormat(java.lang.String format);
    
    /**
     * Sets (as xml) the "format" element
     */
    void xsetFormat(org.apache.xmlbeans.XmlString format);
    
    /**
     * Nils the "format" element
     */
    void setNilFormat();
    
    /**
     * Unsets the "format" element
     */
    void unsetFormat();
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static com.bagnet.nettracer.tracing.db.xsd.NTDateFormat newInstance() {
          return (com.bagnet.nettracer.tracing.db.xsd.NTDateFormat) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static com.bagnet.nettracer.tracing.db.xsd.NTDateFormat newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (com.bagnet.nettracer.tracing.db.xsd.NTDateFormat) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static com.bagnet.nettracer.tracing.db.xsd.NTDateFormat parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.tracing.db.xsd.NTDateFormat) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static com.bagnet.nettracer.tracing.db.xsd.NTDateFormat parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.tracing.db.xsd.NTDateFormat) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static com.bagnet.nettracer.tracing.db.xsd.NTDateFormat parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.tracing.db.xsd.NTDateFormat) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static com.bagnet.nettracer.tracing.db.xsd.NTDateFormat parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.tracing.db.xsd.NTDateFormat) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static com.bagnet.nettracer.tracing.db.xsd.NTDateFormat parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.tracing.db.xsd.NTDateFormat) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static com.bagnet.nettracer.tracing.db.xsd.NTDateFormat parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.tracing.db.xsd.NTDateFormat) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static com.bagnet.nettracer.tracing.db.xsd.NTDateFormat parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.tracing.db.xsd.NTDateFormat) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static com.bagnet.nettracer.tracing.db.xsd.NTDateFormat parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.tracing.db.xsd.NTDateFormat) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static com.bagnet.nettracer.tracing.db.xsd.NTDateFormat parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.tracing.db.xsd.NTDateFormat) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static com.bagnet.nettracer.tracing.db.xsd.NTDateFormat parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.tracing.db.xsd.NTDateFormat) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static com.bagnet.nettracer.tracing.db.xsd.NTDateFormat parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.tracing.db.xsd.NTDateFormat) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static com.bagnet.nettracer.tracing.db.xsd.NTDateFormat parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.tracing.db.xsd.NTDateFormat) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static com.bagnet.nettracer.tracing.db.xsd.NTDateFormat parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.tracing.db.xsd.NTDateFormat) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static com.bagnet.nettracer.tracing.db.xsd.NTDateFormat parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.tracing.db.xsd.NTDateFormat) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.bagnet.nettracer.tracing.db.xsd.NTDateFormat parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.bagnet.nettracer.tracing.db.xsd.NTDateFormat) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.bagnet.nettracer.tracing.db.xsd.NTDateFormat parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.bagnet.nettracer.tracing.db.xsd.NTDateFormat) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
