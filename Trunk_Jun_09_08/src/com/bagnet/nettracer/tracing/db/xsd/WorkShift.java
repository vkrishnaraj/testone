/*
 * XML Type:  Work_Shift
 * Namespace: http://db.tracing.nettracer.bagnet.com/xsd
 * Java type: com.bagnet.nettracer.tracing.db.xsd.WorkShift
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.tracing.db.xsd;


/**
 * An XML Work_Shift(@http://db.tracing.nettracer.bagnet.com/xsd).
 *
 * This is a complex type.
 */
public interface WorkShift extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(WorkShift.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s3120780053B2C9C6ED89C37135AF9582").resolveHandle("workshift3493type");
    
    /**
     * Gets the "agents" element
     */
    java.util.xsd.Set getAgents();
    
    /**
     * Tests for nil "agents" element
     */
    boolean isNilAgents();
    
    /**
     * True if has "agents" element
     */
    boolean isSetAgents();
    
    /**
     * Sets the "agents" element
     */
    void setAgents(java.util.xsd.Set agents);
    
    /**
     * Appends and returns a new empty "agents" element
     */
    java.util.xsd.Set addNewAgents();
    
    /**
     * Nils the "agents" element
     */
    void setNilAgents();
    
    /**
     * Unsets the "agents" element
     */
    void unsetAgents();
    
    /**
     * Gets the "company" element
     */
    com.bagnet.nettracer.tracing.db.xsd.Company getCompany();
    
    /**
     * Tests for nil "company" element
     */
    boolean isNilCompany();
    
    /**
     * True if has "company" element
     */
    boolean isSetCompany();
    
    /**
     * Sets the "company" element
     */
    void setCompany(com.bagnet.nettracer.tracing.db.xsd.Company company);
    
    /**
     * Appends and returns a new empty "company" element
     */
    com.bagnet.nettracer.tracing.db.xsd.Company addNewCompany();
    
    /**
     * Nils the "company" element
     */
    void setNilCompany();
    
    /**
     * Unsets the "company" element
     */
    void unsetCompany();
    
    /**
     * Gets the "locale" element
     */
    java.lang.String getLocale();
    
    /**
     * Gets (as xml) the "locale" element
     */
    org.apache.xmlbeans.XmlString xgetLocale();
    
    /**
     * Tests for nil "locale" element
     */
    boolean isNilLocale();
    
    /**
     * True if has "locale" element
     */
    boolean isSetLocale();
    
    /**
     * Sets the "locale" element
     */
    void setLocale(java.lang.String locale);
    
    /**
     * Sets (as xml) the "locale" element
     */
    void xsetLocale(org.apache.xmlbeans.XmlString locale);
    
    /**
     * Nils the "locale" element
     */
    void setNilLocale();
    
    /**
     * Unsets the "locale" element
     */
    void unsetLocale();
    
    /**
     * Gets the "shift_code" element
     */
    java.lang.String getShiftCode();
    
    /**
     * Gets (as xml) the "shift_code" element
     */
    org.apache.xmlbeans.XmlString xgetShiftCode();
    
    /**
     * Tests for nil "shift_code" element
     */
    boolean isNilShiftCode();
    
    /**
     * True if has "shift_code" element
     */
    boolean isSetShiftCode();
    
    /**
     * Sets the "shift_code" element
     */
    void setShiftCode(java.lang.String shiftCode);
    
    /**
     * Sets (as xml) the "shift_code" element
     */
    void xsetShiftCode(org.apache.xmlbeans.XmlString shiftCode);
    
    /**
     * Nils the "shift_code" element
     */
    void setNilShiftCode();
    
    /**
     * Unsets the "shift_code" element
     */
    void unsetShiftCode();
    
    /**
     * Gets the "shift_description" element
     */
    java.lang.String getShiftDescription();
    
    /**
     * Gets (as xml) the "shift_description" element
     */
    org.apache.xmlbeans.XmlString xgetShiftDescription();
    
    /**
     * Tests for nil "shift_description" element
     */
    boolean isNilShiftDescription();
    
    /**
     * True if has "shift_description" element
     */
    boolean isSetShiftDescription();
    
    /**
     * Sets the "shift_description" element
     */
    void setShiftDescription(java.lang.String shiftDescription);
    
    /**
     * Sets (as xml) the "shift_description" element
     */
    void xsetShiftDescription(org.apache.xmlbeans.XmlString shiftDescription);
    
    /**
     * Nils the "shift_description" element
     */
    void setNilShiftDescription();
    
    /**
     * Unsets the "shift_description" element
     */
    void unsetShiftDescription();
    
    /**
     * Gets the "shift_id" element
     */
    int getShiftId();
    
    /**
     * Gets (as xml) the "shift_id" element
     */
    org.apache.xmlbeans.XmlInt xgetShiftId();
    
    /**
     * True if has "shift_id" element
     */
    boolean isSetShiftId();
    
    /**
     * Sets the "shift_id" element
     */
    void setShiftId(int shiftId);
    
    /**
     * Sets (as xml) the "shift_id" element
     */
    void xsetShiftId(org.apache.xmlbeans.XmlInt shiftId);
    
    /**
     * Unsets the "shift_id" element
     */
    void unsetShiftId();
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static com.bagnet.nettracer.tracing.db.xsd.WorkShift newInstance() {
          return (com.bagnet.nettracer.tracing.db.xsd.WorkShift) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static com.bagnet.nettracer.tracing.db.xsd.WorkShift newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (com.bagnet.nettracer.tracing.db.xsd.WorkShift) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static com.bagnet.nettracer.tracing.db.xsd.WorkShift parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.tracing.db.xsd.WorkShift) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static com.bagnet.nettracer.tracing.db.xsd.WorkShift parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.tracing.db.xsd.WorkShift) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static com.bagnet.nettracer.tracing.db.xsd.WorkShift parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.tracing.db.xsd.WorkShift) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static com.bagnet.nettracer.tracing.db.xsd.WorkShift parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.tracing.db.xsd.WorkShift) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static com.bagnet.nettracer.tracing.db.xsd.WorkShift parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.tracing.db.xsd.WorkShift) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static com.bagnet.nettracer.tracing.db.xsd.WorkShift parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.tracing.db.xsd.WorkShift) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static com.bagnet.nettracer.tracing.db.xsd.WorkShift parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.tracing.db.xsd.WorkShift) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static com.bagnet.nettracer.tracing.db.xsd.WorkShift parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.tracing.db.xsd.WorkShift) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static com.bagnet.nettracer.tracing.db.xsd.WorkShift parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.tracing.db.xsd.WorkShift) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static com.bagnet.nettracer.tracing.db.xsd.WorkShift parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.tracing.db.xsd.WorkShift) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static com.bagnet.nettracer.tracing.db.xsd.WorkShift parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.tracing.db.xsd.WorkShift) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static com.bagnet.nettracer.tracing.db.xsd.WorkShift parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.tracing.db.xsd.WorkShift) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static com.bagnet.nettracer.tracing.db.xsd.WorkShift parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.tracing.db.xsd.WorkShift) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static com.bagnet.nettracer.tracing.db.xsd.WorkShift parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.tracing.db.xsd.WorkShift) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.bagnet.nettracer.tracing.db.xsd.WorkShift parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.bagnet.nettracer.tracing.db.xsd.WorkShift) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.bagnet.nettracer.tracing.db.xsd.WorkShift parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.bagnet.nettracer.tracing.db.xsd.WorkShift) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
