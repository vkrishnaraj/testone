/*
 * XML Type:  ClaimCheck
 * Namespace: http://common.wt_1_0.serviceprovider.nettracer.aero/xsd
 * Java type: aero.nettracer.serviceprovider.wt_1_0.common.xsd.ClaimCheck
 *
 * Automatically generated - do not modify.
 */
package aero.nettracer.serviceprovider.wt_1_0.common.xsd;


/**
 * An XML ClaimCheck(@http://common.wt_1_0.serviceprovider.nettracer.aero/xsd).
 *
 * This is a complex type.
 */
public interface ClaimCheck extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(ClaimCheck.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s062C397454C935FCA9C3F988DC4FD439").resolveHandle("claimcheck3999type");
    
    /**
     * Gets the "airlineCode" element
     */
    java.lang.String getAirlineCode();
    
    /**
     * Gets (as xml) the "airlineCode" element
     */
    org.apache.xmlbeans.XmlString xgetAirlineCode();
    
    /**
     * Tests for nil "airlineCode" element
     */
    boolean isNilAirlineCode();
    
    /**
     * True if has "airlineCode" element
     */
    boolean isSetAirlineCode();
    
    /**
     * Sets the "airlineCode" element
     */
    void setAirlineCode(java.lang.String airlineCode);
    
    /**
     * Sets (as xml) the "airlineCode" element
     */
    void xsetAirlineCode(org.apache.xmlbeans.XmlString airlineCode);
    
    /**
     * Nils the "airlineCode" element
     */
    void setNilAirlineCode();
    
    /**
     * Unsets the "airlineCode" element
     */
    void unsetAirlineCode();
    
    /**
     * Gets the "tagNumber" element
     */
    java.lang.String getTagNumber();
    
    /**
     * Gets (as xml) the "tagNumber" element
     */
    org.apache.xmlbeans.XmlString xgetTagNumber();
    
    /**
     * Tests for nil "tagNumber" element
     */
    boolean isNilTagNumber();
    
    /**
     * True if has "tagNumber" element
     */
    boolean isSetTagNumber();
    
    /**
     * Sets the "tagNumber" element
     */
    void setTagNumber(java.lang.String tagNumber);
    
    /**
     * Sets (as xml) the "tagNumber" element
     */
    void xsetTagNumber(org.apache.xmlbeans.XmlString tagNumber);
    
    /**
     * Nils the "tagNumber" element
     */
    void setNilTagNumber();
    
    /**
     * Unsets the "tagNumber" element
     */
    void unsetTagNumber();
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.ClaimCheck newInstance() {
          return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ClaimCheck) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.ClaimCheck newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ClaimCheck) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.ClaimCheck parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ClaimCheck) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.ClaimCheck parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ClaimCheck) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.ClaimCheck parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ClaimCheck) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.ClaimCheck parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ClaimCheck) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.ClaimCheck parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ClaimCheck) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.ClaimCheck parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ClaimCheck) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.ClaimCheck parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ClaimCheck) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.ClaimCheck parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ClaimCheck) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.ClaimCheck parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ClaimCheck) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.ClaimCheck parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ClaimCheck) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.ClaimCheck parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ClaimCheck) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.ClaimCheck parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ClaimCheck) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.ClaimCheck parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ClaimCheck) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.ClaimCheck parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ClaimCheck) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.ClaimCheck parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ClaimCheck) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.ClaimCheck parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ClaimCheck) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
