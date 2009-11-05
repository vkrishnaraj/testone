/*
 * XML Type:  ActionFileRequestData
 * Namespace: http://common.wt_1_0.serviceprovider.nettracer.aero/xsd
 * Java type: aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileRequestData
 *
 * Automatically generated - do not modify.
 */
package aero.nettracer.serviceprovider.wt_1_0.common.xsd;


/**
 * An XML ActionFileRequestData(@http://common.wt_1_0.serviceprovider.nettracer.aero/xsd).
 *
 * This is a complex type.
 */
public interface ActionFileRequestData extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(ActionFileRequestData.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s062C397454C935FCA9C3F988DC4FD439").resolveHandle("actionfilerequestdata7ce2type");
    
    /**
     * Gets the "airline" element
     */
    java.lang.String getAirline();
    
    /**
     * Gets (as xml) the "airline" element
     */
    org.apache.xmlbeans.XmlString xgetAirline();
    
    /**
     * Tests for nil "airline" element
     */
    boolean isNilAirline();
    
    /**
     * True if has "airline" element
     */
    boolean isSetAirline();
    
    /**
     * Sets the "airline" element
     */
    void setAirline(java.lang.String airline);
    
    /**
     * Sets (as xml) the "airline" element
     */
    void xsetAirline(org.apache.xmlbeans.XmlString airline);
    
    /**
     * Nils the "airline" element
     */
    void setNilAirline();
    
    /**
     * Unsets the "airline" element
     */
    void unsetAirline();
    
    /**
     * Gets the "day" element
     */
    int getDay();
    
    /**
     * Gets (as xml) the "day" element
     */
    org.apache.xmlbeans.XmlInt xgetDay();
    
    /**
     * True if has "day" element
     */
    boolean isSetDay();
    
    /**
     * Sets the "day" element
     */
    void setDay(int day);
    
    /**
     * Sets (as xml) the "day" element
     */
    void xsetDay(org.apache.xmlbeans.XmlInt day);
    
    /**
     * Unsets the "day" element
     */
    void unsetDay();
    
    /**
     * Gets the "number" element
     */
    int getNumber();
    
    /**
     * Gets (as xml) the "number" element
     */
    org.apache.xmlbeans.XmlInt xgetNumber();
    
    /**
     * True if has "number" element
     */
    boolean isSetNumber();
    
    /**
     * Sets the "number" element
     */
    void setNumber(int number);
    
    /**
     * Sets (as xml) the "number" element
     */
    void xsetNumber(org.apache.xmlbeans.XmlInt number);
    
    /**
     * Unsets the "number" element
     */
    void unsetNumber();
    
    /**
     * Gets the "station" element
     */
    java.lang.String getStation();
    
    /**
     * Gets (as xml) the "station" element
     */
    org.apache.xmlbeans.XmlString xgetStation();
    
    /**
     * Tests for nil "station" element
     */
    boolean isNilStation();
    
    /**
     * True if has "station" element
     */
    boolean isSetStation();
    
    /**
     * Sets the "station" element
     */
    void setStation(java.lang.String station);
    
    /**
     * Sets (as xml) the "station" element
     */
    void xsetStation(org.apache.xmlbeans.XmlString station);
    
    /**
     * Nils the "station" element
     */
    void setNilStation();
    
    /**
     * Unsets the "station" element
     */
    void unsetStation();
    
    /**
     * Gets the "type" element
     */
    java.lang.String getType();
    
    /**
     * Gets (as xml) the "type" element
     */
    org.apache.xmlbeans.XmlString xgetType();
    
    /**
     * Tests for nil "type" element
     */
    boolean isNilType();
    
    /**
     * True if has "type" element
     */
    boolean isSetType();
    
    /**
     * Sets the "type" element
     */
    void setType(java.lang.String type);
    
    /**
     * Sets (as xml) the "type" element
     */
    void xsetType(org.apache.xmlbeans.XmlString type);
    
    /**
     * Nils the "type" element
     */
    void setNilType();
    
    /**
     * Unsets the "type" element
     */
    void unsetType();
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileRequestData newInstance() {
          return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileRequestData) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileRequestData newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileRequestData) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileRequestData parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileRequestData) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileRequestData parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileRequestData) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileRequestData parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileRequestData) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileRequestData parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileRequestData) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileRequestData parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileRequestData) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileRequestData parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileRequestData) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileRequestData parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileRequestData) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileRequestData parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileRequestData) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileRequestData parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileRequestData) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileRequestData parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileRequestData) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileRequestData parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileRequestData) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileRequestData parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileRequestData) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileRequestData parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileRequestData) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileRequestData parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileRequestData) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileRequestData parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileRequestData) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileRequestData parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileRequestData) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
