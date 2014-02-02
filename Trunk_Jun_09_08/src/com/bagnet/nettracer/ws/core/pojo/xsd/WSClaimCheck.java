/*
 * XML Type:  WS_ClaimCheck
 * Namespace: http://pojo.core.ws.nettracer.bagnet.com/xsd
 * Java type: com.bagnet.nettracer.ws.core.pojo.xsd.WSClaimCheck
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.core.pojo.xsd;


/**
 * An XML WS_ClaimCheck(@http://pojo.core.ws.nettracer.bagnet.com/xsd).
 *
 * This is a complex type.
 */
public interface WSClaimCheck extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(WSClaimCheck.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sECB0ECF159DBAD03265284E0F73AEDC5").resolveHandle("wsclaimcheckd33btype");
    
    /**
     * Gets array of all "scans" elements
     */
    com.bagnet.nettracer.ws.core.pojo.xsd.WSScanPoints[] getScansArray();
    
    /**
     * Gets ith "scans" element
     */
    com.bagnet.nettracer.ws.core.pojo.xsd.WSScanPoints getScansArray(int i);
    
    /**
     * Tests for nil ith "scans" element
     */
    boolean isNilScansArray(int i);
    
    /**
     * Returns number of "scans" element
     */
    int sizeOfScansArray();
    
    /**
     * Sets array of all "scans" element
     */
    void setScansArray(com.bagnet.nettracer.ws.core.pojo.xsd.WSScanPoints[] scansArray);
    
    /**
     * Sets ith "scans" element
     */
    void setScansArray(int i, com.bagnet.nettracer.ws.core.pojo.xsd.WSScanPoints scans);
    
    /**
     * Nils the ith "scans" element
     */
    void setNilScansArray(int i);
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "scans" element
     */
    com.bagnet.nettracer.ws.core.pojo.xsd.WSScanPoints insertNewScans(int i);
    
    /**
     * Appends and returns a new empty value (as xml) as the last "scans" element
     */
    com.bagnet.nettracer.ws.core.pojo.xsd.WSScanPoints addNewScans();
    
    /**
     * Removes the ith "scans" element
     */
    void removeScans(int i);
    
    /**
     * Gets the "tag" element
     */
    java.lang.String getTag();
    
    /**
     * Gets (as xml) the "tag" element
     */
    org.apache.xmlbeans.XmlString xgetTag();
    
    /**
     * Tests for nil "tag" element
     */
    boolean isNilTag();
    
    /**
     * True if has "tag" element
     */
    boolean isSetTag();
    
    /**
     * Sets the "tag" element
     */
    void setTag(java.lang.String tag);
    
    /**
     * Sets (as xml) the "tag" element
     */
    void xsetTag(org.apache.xmlbeans.XmlString tag);
    
    /**
     * Nils the "tag" element
     */
    void setNilTag();
    
    /**
     * Unsets the "tag" element
     */
    void unsetTag();
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSClaimCheck newInstance() {
          return (com.bagnet.nettracer.ws.core.pojo.xsd.WSClaimCheck) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSClaimCheck newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (com.bagnet.nettracer.ws.core.pojo.xsd.WSClaimCheck) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSClaimCheck parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.core.pojo.xsd.WSClaimCheck) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSClaimCheck parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.core.pojo.xsd.WSClaimCheck) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSClaimCheck parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.core.pojo.xsd.WSClaimCheck) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSClaimCheck parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.core.pojo.xsd.WSClaimCheck) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSClaimCheck parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.core.pojo.xsd.WSClaimCheck) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSClaimCheck parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.core.pojo.xsd.WSClaimCheck) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSClaimCheck parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.core.pojo.xsd.WSClaimCheck) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSClaimCheck parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.core.pojo.xsd.WSClaimCheck) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSClaimCheck parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.core.pojo.xsd.WSClaimCheck) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSClaimCheck parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.core.pojo.xsd.WSClaimCheck) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSClaimCheck parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.core.pojo.xsd.WSClaimCheck) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSClaimCheck parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.core.pojo.xsd.WSClaimCheck) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSClaimCheck parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.core.pojo.xsd.WSClaimCheck) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSClaimCheck parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.core.pojo.xsd.WSClaimCheck) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSClaimCheck parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.bagnet.nettracer.ws.core.pojo.xsd.WSClaimCheck) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSClaimCheck parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.bagnet.nettracer.ws.core.pojo.xsd.WSClaimCheck) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
