/*
 * XML Type:  WorldTracerResponse
 * Namespace: http://common.wt_1_0.serviceprovider.nettracer.aero/xsd
 * Java type: aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse
 *
 * Automatically generated - do not modify.
 */
package aero.nettracer.serviceprovider.wt_1_0.common.xsd;


/**
 * An XML WorldTracerResponse(@http://common.wt_1_0.serviceprovider.nettracer.aero/xsd).
 *
 * This is a complex type.
 */
public interface WorldTracerResponse extends aero.nettracer.serviceprovider.ws_1_0.response.xsd.GenericResponse
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(WorldTracerResponse.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s062C397454C935FCA9C3F988DC4FD439").resolveHandle("worldtracerresponse805btype");
    
    /**
     * Gets array of all "actionFiles" elements
     */
    aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFile[] getActionFilesArray();
    
    /**
     * Gets ith "actionFiles" element
     */
    aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFile getActionFilesArray(int i);
    
    /**
     * Tests for nil ith "actionFiles" element
     */
    boolean isNilActionFilesArray(int i);
    
    /**
     * Returns number of "actionFiles" element
     */
    int sizeOfActionFilesArray();
    
    /**
     * Sets array of all "actionFiles" element
     */
    void setActionFilesArray(aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFile[] actionFilesArray);
    
    /**
     * Sets ith "actionFiles" element
     */
    void setActionFilesArray(int i, aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFile actionFiles);
    
    /**
     * Nils the ith "actionFiles" element
     */
    void setNilActionFilesArray(int i);
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "actionFiles" element
     */
    aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFile insertNewActionFiles(int i);
    
    /**
     * Appends and returns a new empty value (as xml) as the last "actionFiles" element
     */
    aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFile addNewActionFiles();
    
    /**
     * Removes the ith "actionFiles" element
     */
    void removeActionFiles(int i);
    
    /**
     * Gets the "ahl" element
     */
    aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl getAhl();
    
    /**
     * Tests for nil "ahl" element
     */
    boolean isNilAhl();
    
    /**
     * True if has "ahl" element
     */
    boolean isSetAhl();
    
    /**
     * Sets the "ahl" element
     */
    void setAhl(aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl ahl);
    
    /**
     * Appends and returns a new empty "ahl" element
     */
    aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl addNewAhl();
    
    /**
     * Nils the "ahl" element
     */
    void setNilAhl();
    
    /**
     * Unsets the "ahl" element
     */
    void unsetAhl();
    
    /**
     * Gets the "captcha" element
     */
    byte[] getCaptcha();
    
    /**
     * Gets (as xml) the "captcha" element
     */
    org.apache.xmlbeans.XmlBase64Binary xgetCaptcha();
    
    /**
     * Tests for nil "captcha" element
     */
    boolean isNilCaptcha();
    
    /**
     * True if has "captcha" element
     */
    boolean isSetCaptcha();
    
    /**
     * Sets the "captcha" element
     */
    void setCaptcha(byte[] captcha);
    
    /**
     * Sets (as xml) the "captcha" element
     */
    void xsetCaptcha(org.apache.xmlbeans.XmlBase64Binary captcha);
    
    /**
     * Nils the "captcha" element
     */
    void setNilCaptcha();
    
    /**
     * Unsets the "captcha" element
     */
    void unsetCaptcha();
    
    /**
     * Gets the "captchaTimestamp" element
     */
    java.lang.String getCaptchaTimestamp();
    
    /**
     * Gets (as xml) the "captchaTimestamp" element
     */
    org.apache.xmlbeans.XmlString xgetCaptchaTimestamp();
    
    /**
     * Tests for nil "captchaTimestamp" element
     */
    boolean isNilCaptchaTimestamp();
    
    /**
     * True if has "captchaTimestamp" element
     */
    boolean isSetCaptchaTimestamp();
    
    /**
     * Sets the "captchaTimestamp" element
     */
    void setCaptchaTimestamp(java.lang.String captchaTimestamp);
    
    /**
     * Sets (as xml) the "captchaTimestamp" element
     */
    void xsetCaptchaTimestamp(org.apache.xmlbeans.XmlString captchaTimestamp);
    
    /**
     * Nils the "captchaTimestamp" element
     */
    void setNilCaptchaTimestamp();
    
    /**
     * Unsets the "captchaTimestamp" element
     */
    void unsetCaptchaTimestamp();
    
    /**
     * Gets the "connectionRef" element
     */
    java.lang.String getConnectionRef();
    
    /**
     * Gets (as xml) the "connectionRef" element
     */
    org.apache.xmlbeans.XmlString xgetConnectionRef();
    
    /**
     * Tests for nil "connectionRef" element
     */
    boolean isNilConnectionRef();
    
    /**
     * True if has "connectionRef" element
     */
    boolean isSetConnectionRef();
    
    /**
     * Sets the "connectionRef" element
     */
    void setConnectionRef(java.lang.String connectionRef);
    
    /**
     * Sets (as xml) the "connectionRef" element
     */
    void xsetConnectionRef(org.apache.xmlbeans.XmlString connectionRef);
    
    /**
     * Nils the "connectionRef" element
     */
    void setNilConnectionRef();
    
    /**
     * Unsets the "connectionRef" element
     */
    void unsetConnectionRef();
    
    /**
     * Gets array of all "counts" elements
     */
    aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileCount[] getCountsArray();
    
    /**
     * Gets ith "counts" element
     */
    aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileCount getCountsArray(int i);
    
    /**
     * Tests for nil ith "counts" element
     */
    boolean isNilCountsArray(int i);
    
    /**
     * Returns number of "counts" element
     */
    int sizeOfCountsArray();
    
    /**
     * Sets array of all "counts" element
     */
    void setCountsArray(aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileCount[] countsArray);
    
    /**
     * Sets ith "counts" element
     */
    void setCountsArray(int i, aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileCount counts);
    
    /**
     * Nils the ith "counts" element
     */
    void setNilCountsArray(int i);
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "counts" element
     */
    aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileCount insertNewCounts(int i);
    
    /**
     * Appends and returns a new empty value (as xml) as the last "counts" element
     */
    aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileCount addNewCounts();
    
    /**
     * Removes the ith "counts" element
     */
    void removeCounts(int i);
    
    /**
     * Gets the "ohd" element
     */
    aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd getOhd();
    
    /**
     * Tests for nil "ohd" element
     */
    boolean isNilOhd();
    
    /**
     * True if has "ohd" element
     */
    boolean isSetOhd();
    
    /**
     * Sets the "ohd" element
     */
    void setOhd(aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd ohd);
    
    /**
     * Appends and returns a new empty "ohd" element
     */
    aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd addNewOhd();
    
    /**
     * Nils the "ohd" element
     */
    void setNilOhd();
    
    /**
     * Unsets the "ohd" element
     */
    void unsetOhd();
    
    /**
     * Gets the "responseData" element
     */
    java.lang.String getResponseData();
    
    /**
     * Gets (as xml) the "responseData" element
     */
    org.apache.xmlbeans.XmlString xgetResponseData();
    
    /**
     * Tests for nil "responseData" element
     */
    boolean isNilResponseData();
    
    /**
     * True if has "responseData" element
     */
    boolean isSetResponseData();
    
    /**
     * Sets the "responseData" element
     */
    void setResponseData(java.lang.String responseData);
    
    /**
     * Sets (as xml) the "responseData" element
     */
    void xsetResponseData(org.apache.xmlbeans.XmlString responseData);
    
    /**
     * Nils the "responseData" element
     */
    void setNilResponseData();
    
    /**
     * Unsets the "responseData" element
     */
    void unsetResponseData();
    
    /**
     * Gets the "responseId" element
     */
    java.lang.String getResponseId();
    
    /**
     * Gets (as xml) the "responseId" element
     */
    org.apache.xmlbeans.XmlString xgetResponseId();
    
    /**
     * Tests for nil "responseId" element
     */
    boolean isNilResponseId();
    
    /**
     * True if has "responseId" element
     */
    boolean isSetResponseId();
    
    /**
     * Sets the "responseId" element
     */
    void setResponseId(java.lang.String responseId);
    
    /**
     * Sets (as xml) the "responseId" element
     */
    void xsetResponseId(org.apache.xmlbeans.XmlString responseId);
    
    /**
     * Nils the "responseId" element
     */
    void setNilResponseId();
    
    /**
     * Unsets the "responseId" element
     */
    void unsetResponseId();
    
    /**
     * Gets the "success" element
     */
    boolean getSuccess();
    
    /**
     * Gets (as xml) the "success" element
     */
    org.apache.xmlbeans.XmlBoolean xgetSuccess();
    
    /**
     * True if has "success" element
     */
    boolean isSetSuccess();
    
    /**
     * Sets the "success" element
     */
    void setSuccess(boolean success);
    
    /**
     * Sets (as xml) the "success" element
     */
    void xsetSuccess(org.apache.xmlbeans.XmlBoolean success);
    
    /**
     * Unsets the "success" element
     */
    void unsetSuccess();
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse newInstance() {
          return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
