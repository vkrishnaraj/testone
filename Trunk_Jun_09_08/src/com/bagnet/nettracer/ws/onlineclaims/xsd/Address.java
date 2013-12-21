/*
 * XML Type:  Address
 * Namespace: http://onlineclaims.ws.nettracer.bagnet.com/xsd
 * Java type: com.bagnet.nettracer.ws.onlineclaims.xsd.Address
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.onlineclaims.xsd;


/**
 * An XML Address(@http://onlineclaims.ws.nettracer.bagnet.com/xsd).
 *
 * This is a complex type.
 */
public interface Address extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(Address.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s47918F556B545B4B159263E4D62D20A1").resolveHandle("address9371type");
    
    /**
     * Gets the "address1" element
     */
    java.lang.String getAddress1();
    
    /**
     * Gets (as xml) the "address1" element
     */
    org.apache.xmlbeans.XmlString xgetAddress1();
    
    /**
     * Tests for nil "address1" element
     */
    boolean isNilAddress1();
    
    /**
     * True if has "address1" element
     */
    boolean isSetAddress1();
    
    /**
     * Sets the "address1" element
     */
    void setAddress1(java.lang.String address1);
    
    /**
     * Sets (as xml) the "address1" element
     */
    void xsetAddress1(org.apache.xmlbeans.XmlString address1);
    
    /**
     * Nils the "address1" element
     */
    void setNilAddress1();
    
    /**
     * Unsets the "address1" element
     */
    void unsetAddress1();
    
    /**
     * Gets the "address2" element
     */
    java.lang.String getAddress2();
    
    /**
     * Gets (as xml) the "address2" element
     */
    org.apache.xmlbeans.XmlString xgetAddress2();
    
    /**
     * Tests for nil "address2" element
     */
    boolean isNilAddress2();
    
    /**
     * True if has "address2" element
     */
    boolean isSetAddress2();
    
    /**
     * Sets the "address2" element
     */
    void setAddress2(java.lang.String address2);
    
    /**
     * Sets (as xml) the "address2" element
     */
    void xsetAddress2(org.apache.xmlbeans.XmlString address2);
    
    /**
     * Nils the "address2" element
     */
    void setNilAddress2();
    
    /**
     * Unsets the "address2" element
     */
    void unsetAddress2();
    
    /**
     * Gets the "city" element
     */
    java.lang.String getCity();
    
    /**
     * Gets (as xml) the "city" element
     */
    org.apache.xmlbeans.XmlString xgetCity();
    
    /**
     * Tests for nil "city" element
     */
    boolean isNilCity();
    
    /**
     * True if has "city" element
     */
    boolean isSetCity();
    
    /**
     * Sets the "city" element
     */
    void setCity(java.lang.String city);
    
    /**
     * Sets (as xml) the "city" element
     */
    void xsetCity(org.apache.xmlbeans.XmlString city);
    
    /**
     * Nils the "city" element
     */
    void setNilCity();
    
    /**
     * Unsets the "city" element
     */
    void unsetCity();
    
    /**
     * Gets the "country" element
     */
    java.lang.String getCountry();
    
    /**
     * Gets (as xml) the "country" element
     */
    org.apache.xmlbeans.XmlString xgetCountry();
    
    /**
     * Tests for nil "country" element
     */
    boolean isNilCountry();
    
    /**
     * True if has "country" element
     */
    boolean isSetCountry();
    
    /**
     * Sets the "country" element
     */
    void setCountry(java.lang.String country);
    
    /**
     * Sets (as xml) the "country" element
     */
    void xsetCountry(org.apache.xmlbeans.XmlString country);
    
    /**
     * Nils the "country" element
     */
    void setNilCountry();
    
    /**
     * Unsets the "country" element
     */
    void unsetCountry();
    
    /**
     * Gets the "postalCode" element
     */
    java.lang.String getPostalCode();
    
    /**
     * Gets (as xml) the "postalCode" element
     */
    org.apache.xmlbeans.XmlString xgetPostalCode();
    
    /**
     * Tests for nil "postalCode" element
     */
    boolean isNilPostalCode();
    
    /**
     * True if has "postalCode" element
     */
    boolean isSetPostalCode();
    
    /**
     * Sets the "postalCode" element
     */
    void setPostalCode(java.lang.String postalCode);
    
    /**
     * Sets (as xml) the "postalCode" element
     */
    void xsetPostalCode(org.apache.xmlbeans.XmlString postalCode);
    
    /**
     * Nils the "postalCode" element
     */
    void setNilPostalCode();
    
    /**
     * Unsets the "postalCode" element
     */
    void unsetPostalCode();
    
    /**
     * Gets the "stateProvince" element
     */
    java.lang.String getStateProvince();
    
    /**
     * Gets (as xml) the "stateProvince" element
     */
    org.apache.xmlbeans.XmlString xgetStateProvince();
    
    /**
     * Tests for nil "stateProvince" element
     */
    boolean isNilStateProvince();
    
    /**
     * True if has "stateProvince" element
     */
    boolean isSetStateProvince();
    
    /**
     * Sets the "stateProvince" element
     */
    void setStateProvince(java.lang.String stateProvince);
    
    /**
     * Sets (as xml) the "stateProvince" element
     */
    void xsetStateProvince(org.apache.xmlbeans.XmlString stateProvince);
    
    /**
     * Nils the "stateProvince" element
     */
    void setNilStateProvince();
    
    /**
     * Unsets the "stateProvince" element
     */
    void unsetStateProvince();
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Address newInstance() {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Address) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Address newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Address) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Address parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Address) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Address parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Address) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Address parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Address) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Address parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Address) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Address parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Address) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Address parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Address) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Address parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Address) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Address parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Address) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Address parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Address) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Address parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Address) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Address parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Address) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Address parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Address) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Address parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Address) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Address parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Address) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Address parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Address) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Address parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Address) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
