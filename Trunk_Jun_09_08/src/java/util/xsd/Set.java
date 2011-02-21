/*
 * XML Type:  Set
 * Namespace: http://util.java/xsd
 * Java type: java.util.xsd.Set
 *
 * Automatically generated - do not modify.
 */
package java.util.xsd;


/**
 * An XML Set(@http://util.java/xsd).
 *
 * This is a complex type.
 */
public interface Set extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(Set.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sECD53D12A3B16E559E57ADA881AD63EA").resolveHandle("setf79dtype");
    
    /**
     * Gets the "empty" element
     */
    boolean getEmpty();
    
    /**
     * Gets (as xml) the "empty" element
     */
    org.apache.xmlbeans.XmlBoolean xgetEmpty();
    
    /**
     * True if has "empty" element
     */
    boolean isSetEmpty();
    
    /**
     * Sets the "empty" element
     */
    void setEmpty(boolean empty);
    
    /**
     * Sets (as xml) the "empty" element
     */
    void xsetEmpty(org.apache.xmlbeans.XmlBoolean empty);
    
    /**
     * Unsets the "empty" element
     */
    void unsetEmpty();
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static java.util.xsd.Set newInstance() {
          return (java.util.xsd.Set) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static java.util.xsd.Set newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (java.util.xsd.Set) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static java.util.xsd.Set parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (java.util.xsd.Set) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static java.util.xsd.Set parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (java.util.xsd.Set) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static java.util.xsd.Set parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (java.util.xsd.Set) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static java.util.xsd.Set parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (java.util.xsd.Set) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static java.util.xsd.Set parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (java.util.xsd.Set) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static java.util.xsd.Set parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (java.util.xsd.Set) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static java.util.xsd.Set parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (java.util.xsd.Set) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static java.util.xsd.Set parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (java.util.xsd.Set) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static java.util.xsd.Set parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (java.util.xsd.Set) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static java.util.xsd.Set parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (java.util.xsd.Set) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static java.util.xsd.Set parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (java.util.xsd.Set) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static java.util.xsd.Set parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (java.util.xsd.Set) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static java.util.xsd.Set parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (java.util.xsd.Set) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static java.util.xsd.Set parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (java.util.xsd.Set) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static java.util.xsd.Set parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (java.util.xsd.Set) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static java.util.xsd.Set parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (java.util.xsd.Set) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
