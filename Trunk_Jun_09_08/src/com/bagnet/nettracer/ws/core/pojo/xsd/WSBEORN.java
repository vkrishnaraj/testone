/*
 * XML Type:  WS_BEORN
 * Namespace: http://pojo.core.ws.nettracer.bagnet.com/xsd
 * Java type: com.bagnet.nettracer.ws.core.pojo.xsd.WSBEORN
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.core.pojo.xsd;


/**
 * An XML WS_BEORN(@http://pojo.core.ws.nettracer.bagnet.com/xsd).
 *
 * This is a complex type.
 */
public interface WSBEORN extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(WSBEORN.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s45E1E5C329E9FFAAC1A63D087AEED0C2").resolveHandle("wsbeorn2a13type");
    
    /**
     * Gets array of all "baggageItinerary" elements
     */
    com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary[] getBaggageItineraryArray();
    
    /**
     * Gets ith "baggageItinerary" element
     */
    com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary getBaggageItineraryArray(int i);
    
    /**
     * Tests for nil ith "baggageItinerary" element
     */
    boolean isNilBaggageItineraryArray(int i);
    
    /**
     * Returns number of "baggageItinerary" element
     */
    int sizeOfBaggageItineraryArray();
    
    /**
     * Sets array of all "baggageItinerary" element
     */
    void setBaggageItineraryArray(com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary[] baggageItineraryArray);
    
    /**
     * Sets ith "baggageItinerary" element
     */
    void setBaggageItineraryArray(int i, com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary baggageItinerary);
    
    /**
     * Nils the ith "baggageItinerary" element
     */
    void setNilBaggageItineraryArray(int i);
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "baggageItinerary" element
     */
    com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary insertNewBaggageItinerary(int i);
    
    /**
     * Appends and returns a new empty value (as xml) as the last "baggageItinerary" element
     */
    com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary addNewBaggageItinerary();
    
    /**
     * Removes the ith "baggageItinerary" element
     */
    void removeBaggageItinerary(int i);
    
    /**
     * Gets the "claimCheckNumber" element
     */
    java.lang.String getClaimCheckNumber();
    
    /**
     * Gets (as xml) the "claimCheckNumber" element
     */
    org.apache.xmlbeans.XmlString xgetClaimCheckNumber();
    
    /**
     * Tests for nil "claimCheckNumber" element
     */
    boolean isNilClaimCheckNumber();
    
    /**
     * True if has "claimCheckNumber" element
     */
    boolean isSetClaimCheckNumber();
    
    /**
     * Sets the "claimCheckNumber" element
     */
    void setClaimCheckNumber(java.lang.String claimCheckNumber);
    
    /**
     * Sets (as xml) the "claimCheckNumber" element
     */
    void xsetClaimCheckNumber(org.apache.xmlbeans.XmlString claimCheckNumber);
    
    /**
     * Nils the "claimCheckNumber" element
     */
    void setNilClaimCheckNumber();
    
    /**
     * Unsets the "claimCheckNumber" element
     */
    void unsetClaimCheckNumber();
    
    /**
     * Gets the "destinationStation" element
     */
    java.lang.String getDestinationStation();
    
    /**
     * Gets (as xml) the "destinationStation" element
     */
    org.apache.xmlbeans.XmlString xgetDestinationStation();
    
    /**
     * Tests for nil "destinationStation" element
     */
    boolean isNilDestinationStation();
    
    /**
     * True if has "destinationStation" element
     */
    boolean isSetDestinationStation();
    
    /**
     * Sets the "destinationStation" element
     */
    void setDestinationStation(java.lang.String destinationStation);
    
    /**
     * Sets (as xml) the "destinationStation" element
     */
    void xsetDestinationStation(org.apache.xmlbeans.XmlString destinationStation);
    
    /**
     * Nils the "destinationStation" element
     */
    void setNilDestinationStation();
    
    /**
     * Unsets the "destinationStation" element
     */
    void unsetDestinationStation();
    
    /**
     * Gets the "expediteNumber" element
     */
    java.lang.String getExpediteNumber();
    
    /**
     * Gets (as xml) the "expediteNumber" element
     */
    org.apache.xmlbeans.XmlString xgetExpediteNumber();
    
    /**
     * Tests for nil "expediteNumber" element
     */
    boolean isNilExpediteNumber();
    
    /**
     * True if has "expediteNumber" element
     */
    boolean isSetExpediteNumber();
    
    /**
     * Sets the "expediteNumber" element
     */
    void setExpediteNumber(java.lang.String expediteNumber);
    
    /**
     * Sets (as xml) the "expediteNumber" element
     */
    void xsetExpediteNumber(org.apache.xmlbeans.XmlString expediteNumber);
    
    /**
     * Nils the "expediteNumber" element
     */
    void setNilExpediteNumber();
    
    /**
     * Unsets the "expediteNumber" element
     */
    void unsetExpediteNumber();
    
    /**
     * Gets the "faultStation" element
     */
    java.lang.String getFaultStation();
    
    /**
     * Gets (as xml) the "faultStation" element
     */
    org.apache.xmlbeans.XmlString xgetFaultStation();
    
    /**
     * Tests for nil "faultStation" element
     */
    boolean isNilFaultStation();
    
    /**
     * True if has "faultStation" element
     */
    boolean isSetFaultStation();
    
    /**
     * Sets the "faultStation" element
     */
    void setFaultStation(java.lang.String faultStation);
    
    /**
     * Sets (as xml) the "faultStation" element
     */
    void xsetFaultStation(org.apache.xmlbeans.XmlString faultStation);
    
    /**
     * Nils the "faultStation" element
     */
    void setNilFaultStation();
    
    /**
     * Unsets the "faultStation" element
     */
    void unsetFaultStation();
    
    /**
     * Gets array of all "forwardItinerary" elements
     */
    com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary[] getForwardItineraryArray();
    
    /**
     * Gets ith "forwardItinerary" element
     */
    com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary getForwardItineraryArray(int i);
    
    /**
     * Tests for nil ith "forwardItinerary" element
     */
    boolean isNilForwardItineraryArray(int i);
    
    /**
     * Returns number of "forwardItinerary" element
     */
    int sizeOfForwardItineraryArray();
    
    /**
     * Sets array of all "forwardItinerary" element
     */
    void setForwardItineraryArray(com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary[] forwardItineraryArray);
    
    /**
     * Sets ith "forwardItinerary" element
     */
    void setForwardItineraryArray(int i, com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary forwardItinerary);
    
    /**
     * Nils the ith "forwardItinerary" element
     */
    void setNilForwardItineraryArray(int i);
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "forwardItinerary" element
     */
    com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary insertNewForwardItinerary(int i);
    
    /**
     * Appends and returns a new empty value (as xml) as the last "forwardItinerary" element
     */
    com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary addNewForwardItinerary();
    
    /**
     * Removes the ith "forwardItinerary" element
     */
    void removeForwardItinerary(int i);
    
    /**
     * Gets the "foundAtStation" element
     */
    java.lang.String getFoundAtStation();
    
    /**
     * Gets (as xml) the "foundAtStation" element
     */
    org.apache.xmlbeans.XmlString xgetFoundAtStation();
    
    /**
     * Tests for nil "foundAtStation" element
     */
    boolean isNilFoundAtStation();
    
    /**
     * True if has "foundAtStation" element
     */
    boolean isSetFoundAtStation();
    
    /**
     * Sets the "foundAtStation" element
     */
    void setFoundAtStation(java.lang.String foundAtStation);
    
    /**
     * Sets (as xml) the "foundAtStation" element
     */
    void xsetFoundAtStation(org.apache.xmlbeans.XmlString foundAtStation);
    
    /**
     * Nils the "foundAtStation" element
     */
    void setNilFoundAtStation();
    
    /**
     * Unsets the "foundAtStation" element
     */
    void unsetFoundAtStation();
    
    /**
     * Gets the "founddatetime" element
     */
    java.util.Calendar getFounddatetime();
    
    /**
     * Gets (as xml) the "founddatetime" element
     */
    org.apache.xmlbeans.XmlDate xgetFounddatetime();
    
    /**
     * Tests for nil "founddatetime" element
     */
    boolean isNilFounddatetime();
    
    /**
     * True if has "founddatetime" element
     */
    boolean isSetFounddatetime();
    
    /**
     * Sets the "founddatetime" element
     */
    void setFounddatetime(java.util.Calendar founddatetime);
    
    /**
     * Sets (as xml) the "founddatetime" element
     */
    void xsetFounddatetime(org.apache.xmlbeans.XmlDate founddatetime);
    
    /**
     * Nils the "founddatetime" element
     */
    void setNilFounddatetime();
    
    /**
     * Unsets the "founddatetime" element
     */
    void unsetFounddatetime();
    
    /**
     * Gets the "lossCode" element
     */
    int getLossCode();
    
    /**
     * Gets (as xml) the "lossCode" element
     */
    org.apache.xmlbeans.XmlInt xgetLossCode();
    
    /**
     * Tests for nil "lossCode" element
     */
    boolean isNilLossCode();
    
    /**
     * True if has "lossCode" element
     */
    boolean isSetLossCode();
    
    /**
     * Sets the "lossCode" element
     */
    void setLossCode(int lossCode);
    
    /**
     * Sets (as xml) the "lossCode" element
     */
    void xsetLossCode(org.apache.xmlbeans.XmlInt lossCode);
    
    /**
     * Nils the "lossCode" element
     */
    void setNilLossCode();
    
    /**
     * Unsets the "lossCode" element
     */
    void unsetLossCode();
    
    /**
     * Gets the "message" element
     */
    java.lang.String getMessage();
    
    /**
     * Gets (as xml) the "message" element
     */
    org.apache.xmlbeans.XmlString xgetMessage();
    
    /**
     * Tests for nil "message" element
     */
    boolean isNilMessage();
    
    /**
     * True if has "message" element
     */
    boolean isSetMessage();
    
    /**
     * Sets the "message" element
     */
    void setMessage(java.lang.String message);
    
    /**
     * Sets (as xml) the "message" element
     */
    void xsetMessage(org.apache.xmlbeans.XmlString message);
    
    /**
     * Nils the "message" element
     */
    void setNilMessage();
    
    /**
     * Unsets the "message" element
     */
    void unsetMessage();
    
    /**
     * Gets the "specialInstructions" element
     */
    java.lang.String getSpecialInstructions();
    
    /**
     * Gets (as xml) the "specialInstructions" element
     */
    org.apache.xmlbeans.XmlString xgetSpecialInstructions();
    
    /**
     * Tests for nil "specialInstructions" element
     */
    boolean isNilSpecialInstructions();
    
    /**
     * True if has "specialInstructions" element
     */
    boolean isSetSpecialInstructions();
    
    /**
     * Sets the "specialInstructions" element
     */
    void setSpecialInstructions(java.lang.String specialInstructions);
    
    /**
     * Sets (as xml) the "specialInstructions" element
     */
    void xsetSpecialInstructions(org.apache.xmlbeans.XmlString specialInstructions);
    
    /**
     * Nils the "specialInstructions" element
     */
    void setNilSpecialInstructions();
    
    /**
     * Unsets the "specialInstructions" element
     */
    void unsetSpecialInstructions();
    
    /**
     * Gets the "tagOff" element
     */
    boolean getTagOff();
    
    /**
     * Gets (as xml) the "tagOff" element
     */
    org.apache.xmlbeans.XmlBoolean xgetTagOff();
    
    /**
     * True if has "tagOff" element
     */
    boolean isSetTagOff();
    
    /**
     * Sets the "tagOff" element
     */
    void setTagOff(boolean tagOff);
    
    /**
     * Sets (as xml) the "tagOff" element
     */
    void xsetTagOff(org.apache.xmlbeans.XmlBoolean tagOff);
    
    /**
     * Unsets the "tagOff" element
     */
    void unsetTagOff();
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSBEORN newInstance() {
          return (com.bagnet.nettracer.ws.core.pojo.xsd.WSBEORN) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSBEORN newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (com.bagnet.nettracer.ws.core.pojo.xsd.WSBEORN) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSBEORN parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.core.pojo.xsd.WSBEORN) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSBEORN parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.core.pojo.xsd.WSBEORN) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSBEORN parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.core.pojo.xsd.WSBEORN) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSBEORN parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.core.pojo.xsd.WSBEORN) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSBEORN parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.core.pojo.xsd.WSBEORN) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSBEORN parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.core.pojo.xsd.WSBEORN) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSBEORN parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.core.pojo.xsd.WSBEORN) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSBEORN parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.core.pojo.xsd.WSBEORN) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSBEORN parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.core.pojo.xsd.WSBEORN) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSBEORN parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.core.pojo.xsd.WSBEORN) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSBEORN parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.core.pojo.xsd.WSBEORN) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSBEORN parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.core.pojo.xsd.WSBEORN) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSBEORN parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.core.pojo.xsd.WSBEORN) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSBEORN parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.core.pojo.xsd.WSBEORN) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSBEORN parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.bagnet.nettracer.ws.core.pojo.xsd.WSBEORN) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSBEORN parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.bagnet.nettracer.ws.core.pojo.xsd.WSBEORN) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
