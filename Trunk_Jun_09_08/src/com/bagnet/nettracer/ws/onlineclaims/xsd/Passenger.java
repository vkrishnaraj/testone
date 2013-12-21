/*
 * XML Type:  Passenger
 * Namespace: http://onlineclaims.ws.nettracer.bagnet.com/xsd
 * Java type: com.bagnet.nettracer.ws.onlineclaims.xsd.Passenger
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.onlineclaims.xsd;


/**
 * An XML Passenger(@http://onlineclaims.ws.nettracer.bagnet.com/xsd).
 *
 * This is a complex type.
 */
public interface Passenger extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(Passenger.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s47918F556B545B4B159263E4D62D20A1").resolveHandle("passengerae57type");
    
    /**
     * Gets the "accept" element
     */
    java.lang.String getAccept();
    
    /**
     * Gets (as xml) the "accept" element
     */
    org.apache.xmlbeans.XmlString xgetAccept();
    
    /**
     * Tests for nil "accept" element
     */
    boolean isNilAccept();
    
    /**
     * True if has "accept" element
     */
    boolean isSetAccept();
    
    /**
     * Sets the "accept" element
     */
    void setAccept(java.lang.String accept);
    
    /**
     * Sets (as xml) the "accept" element
     */
    void xsetAccept(org.apache.xmlbeans.XmlString accept);
    
    /**
     * Nils the "accept" element
     */
    void setNilAccept();
    
    /**
     * Unsets the "accept" element
     */
    void unsetAccept();
    
    /**
     * Gets the "firstName" element
     */
    java.lang.String getFirstName();
    
    /**
     * Gets (as xml) the "firstName" element
     */
    org.apache.xmlbeans.XmlString xgetFirstName();
    
    /**
     * Tests for nil "firstName" element
     */
    boolean isNilFirstName();
    
    /**
     * True if has "firstName" element
     */
    boolean isSetFirstName();
    
    /**
     * Sets the "firstName" element
     */
    void setFirstName(java.lang.String firstName);
    
    /**
     * Sets (as xml) the "firstName" element
     */
    void xsetFirstName(org.apache.xmlbeans.XmlString firstName);
    
    /**
     * Nils the "firstName" element
     */
    void setNilFirstName();
    
    /**
     * Unsets the "firstName" element
     */
    void unsetFirstName();
    
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
     * Gets the "lastName" element
     */
    java.lang.String getLastName();
    
    /**
     * Gets (as xml) the "lastName" element
     */
    org.apache.xmlbeans.XmlString xgetLastName();
    
    /**
     * Tests for nil "lastName" element
     */
    boolean isNilLastName();
    
    /**
     * True if has "lastName" element
     */
    boolean isSetLastName();
    
    /**
     * Sets the "lastName" element
     */
    void setLastName(java.lang.String lastName);
    
    /**
     * Sets (as xml) the "lastName" element
     */
    void xsetLastName(org.apache.xmlbeans.XmlString lastName);
    
    /**
     * Nils the "lastName" element
     */
    void setNilLastName();
    
    /**
     * Unsets the "lastName" element
     */
    void unsetLastName();
    
    /**
     * Gets the "middleInitial" element
     */
    java.lang.String getMiddleInitial();
    
    /**
     * Gets (as xml) the "middleInitial" element
     */
    org.apache.xmlbeans.XmlString xgetMiddleInitial();
    
    /**
     * Tests for nil "middleInitial" element
     */
    boolean isNilMiddleInitial();
    
    /**
     * True if has "middleInitial" element
     */
    boolean isSetMiddleInitial();
    
    /**
     * Sets the "middleInitial" element
     */
    void setMiddleInitial(java.lang.String middleInitial);
    
    /**
     * Sets (as xml) the "middleInitial" element
     */
    void xsetMiddleInitial(org.apache.xmlbeans.XmlString middleInitial);
    
    /**
     * Nils the "middleInitial" element
     */
    void setNilMiddleInitial();
    
    /**
     * Unsets the "middleInitial" element
     */
    void unsetMiddleInitial();
    
    /**
     * Gets the "salutation" element
     */
    java.lang.String getSalutation();
    
    /**
     * Gets (as xml) the "salutation" element
     */
    org.apache.xmlbeans.XmlString xgetSalutation();
    
    /**
     * Tests for nil "salutation" element
     */
    boolean isNilSalutation();
    
    /**
     * True if has "salutation" element
     */
    boolean isSetSalutation();
    
    /**
     * Sets the "salutation" element
     */
    void setSalutation(java.lang.String salutation);
    
    /**
     * Sets (as xml) the "salutation" element
     */
    void xsetSalutation(org.apache.xmlbeans.XmlString salutation);
    
    /**
     * Nils the "salutation" element
     */
    void setNilSalutation();
    
    /**
     * Unsets the "salutation" element
     */
    void unsetSalutation();
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Passenger newInstance() {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Passenger) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Passenger newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Passenger) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Passenger parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Passenger) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Passenger parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Passenger) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Passenger parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Passenger) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Passenger parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Passenger) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Passenger parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Passenger) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Passenger parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Passenger) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Passenger parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Passenger) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Passenger parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Passenger) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Passenger parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Passenger) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Passenger parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Passenger) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Passenger parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Passenger) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Passenger parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Passenger) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Passenger parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Passenger) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Passenger parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Passenger) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Passenger parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Passenger) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Passenger parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Passenger) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
