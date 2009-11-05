/*
 * XML Type:  PxfAas
 * Namespace: http://common.wt_1_0.serviceprovider.nettracer.aero/xsd
 * Java type: aero.nettracer.serviceprovider.wt_1_0.common.xsd.PxfAas
 *
 * Automatically generated - do not modify.
 */
package aero.nettracer.serviceprovider.wt_1_0.common.xsd;


/**
 * An XML PxfAas(@http://common.wt_1_0.serviceprovider.nettracer.aero/xsd).
 *
 * This is a complex type.
 */
public interface PxfAas extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(PxfAas.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s062C397454C935FCA9C3F988DC4FD439").resolveHandle("pxfaas0d02type");
    
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
     * Gets the "area" element
     */
    java.lang.String getArea();
    
    /**
     * Gets (as xml) the "area" element
     */
    org.apache.xmlbeans.XmlString xgetArea();
    
    /**
     * Tests for nil "area" element
     */
    boolean isNilArea();
    
    /**
     * True if has "area" element
     */
    boolean isSetArea();
    
    /**
     * Sets the "area" element
     */
    void setArea(java.lang.String area);
    
    /**
     * Sets (as xml) the "area" element
     */
    void xsetArea(org.apache.xmlbeans.XmlString area);
    
    /**
     * Nils the "area" element
     */
    void setNilArea();
    
    /**
     * Unsets the "area" element
     */
    void unsetArea();
    
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
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.PxfAas newInstance() {
          return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.PxfAas) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.PxfAas newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.PxfAas) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.PxfAas parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.PxfAas) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.PxfAas parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.PxfAas) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.PxfAas parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.PxfAas) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.PxfAas parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.PxfAas) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.PxfAas parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.PxfAas) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.PxfAas parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.PxfAas) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.PxfAas parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.PxfAas) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.PxfAas parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.PxfAas) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.PxfAas parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.PxfAas) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.PxfAas parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.PxfAas) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.PxfAas parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.PxfAas) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.PxfAas parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.PxfAas) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.PxfAas parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.PxfAas) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.PxfAas parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.PxfAas) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.PxfAas parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.PxfAas) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.PxfAas parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.PxfAas) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
