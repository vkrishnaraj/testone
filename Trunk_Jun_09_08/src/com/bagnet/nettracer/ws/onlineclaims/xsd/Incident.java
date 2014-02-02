/*
 * XML Type:  Incident
 * Namespace: http://onlineclaims.ws.nettracer.bagnet.com/xsd
 * Java type: com.bagnet.nettracer.ws.onlineclaims.xsd.Incident
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.onlineclaims.xsd;


/**
 * An XML Incident(@http://onlineclaims.ws.nettracer.bagnet.com/xsd).
 *
 * This is a complex type.
 */
public interface Incident extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(Incident.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sECB0ECF159DBAD03265284E0F73AEDC5").resolveHandle("incident1c81type");
    
    /**
     * Gets the "authID" element
     */
    int getAuthID();
    
    /**
     * Gets (as xml) the "authID" element
     */
    org.apache.xmlbeans.XmlInt xgetAuthID();
    
    /**
     * True if has "authID" element
     */
    boolean isSetAuthID();
    
    /**
     * Sets the "authID" element
     */
    void setAuthID(int authID);
    
    /**
     * Sets (as xml) the "authID" element
     */
    void xsetAuthID(org.apache.xmlbeans.XmlInt authID);
    
    /**
     * Unsets the "authID" element
     */
    void unsetAuthID();
    
    /**
     * Gets the "authStatus" element
     */
    int getAuthStatus();
    
    /**
     * Gets (as xml) the "authStatus" element
     */
    org.apache.xmlbeans.XmlInt xgetAuthStatus();
    
    /**
     * True if has "authStatus" element
     */
    boolean isSetAuthStatus();
    
    /**
     * Sets the "authStatus" element
     */
    void setAuthStatus(int authStatus);
    
    /**
     * Sets (as xml) the "authStatus" element
     */
    void xsetAuthStatus(org.apache.xmlbeans.XmlInt authStatus);
    
    /**
     * Unsets the "authStatus" element
     */
    void unsetAuthStatus();
    
    /**
     * Gets array of all "bag" elements
     */
    com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentBag[] getBagArray();
    
    /**
     * Gets ith "bag" element
     */
    com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentBag getBagArray(int i);
    
    /**
     * Tests for nil ith "bag" element
     */
    boolean isNilBagArray(int i);
    
    /**
     * Returns number of "bag" element
     */
    int sizeOfBagArray();
    
    /**
     * Sets array of all "bag" element
     */
    void setBagArray(com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentBag[] bagArray);
    
    /**
     * Sets ith "bag" element
     */
    void setBagArray(int i, com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentBag bag);
    
    /**
     * Nils the ith "bag" element
     */
    void setNilBagArray(int i);
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "bag" element
     */
    com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentBag insertNewBag(int i);
    
    /**
     * Appends and returns a new empty value (as xml) as the last "bag" element
     */
    com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentBag addNewBag();
    
    /**
     * Removes the ith "bag" element
     */
    void removeBag(int i);
    
    /**
     * Gets array of all "claimCheck" elements
     */
    java.lang.String[] getClaimCheckArray();
    
    /**
     * Gets ith "claimCheck" element
     */
    java.lang.String getClaimCheckArray(int i);
    
    /**
     * Gets (as xml) array of all "claimCheck" elements
     */
    org.apache.xmlbeans.XmlString[] xgetClaimCheckArray();
    
    /**
     * Gets (as xml) ith "claimCheck" element
     */
    org.apache.xmlbeans.XmlString xgetClaimCheckArray(int i);
    
    /**
     * Tests for nil ith "claimCheck" element
     */
    boolean isNilClaimCheckArray(int i);
    
    /**
     * Returns number of "claimCheck" element
     */
    int sizeOfClaimCheckArray();
    
    /**
     * Sets array of all "claimCheck" element
     */
    void setClaimCheckArray(java.lang.String[] claimCheckArray);
    
    /**
     * Sets ith "claimCheck" element
     */
    void setClaimCheckArray(int i, java.lang.String claimCheck);
    
    /**
     * Sets (as xml) array of all "claimCheck" element
     */
    void xsetClaimCheckArray(org.apache.xmlbeans.XmlString[] claimCheckArray);
    
    /**
     * Sets (as xml) ith "claimCheck" element
     */
    void xsetClaimCheckArray(int i, org.apache.xmlbeans.XmlString claimCheck);
    
    /**
     * Nils the ith "claimCheck" element
     */
    void setNilClaimCheckArray(int i);
    
    /**
     * Inserts the value as the ith "claimCheck" element
     */
    void insertClaimCheck(int i, java.lang.String claimCheck);
    
    /**
     * Appends the value as the last "claimCheck" element
     */
    void addClaimCheck(java.lang.String claimCheck);
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "claimCheck" element
     */
    org.apache.xmlbeans.XmlString insertNewClaimCheck(int i);
    
    /**
     * Appends and returns a new empty value (as xml) as the last "claimCheck" element
     */
    org.apache.xmlbeans.XmlString addNewClaimCheck();
    
    /**
     * Removes the ith "claimCheck" element
     */
    void removeClaimCheck(int i);
    
    /**
     * Gets the "deliverWithoutSignature" element
     */
    boolean getDeliverWithoutSignature();
    
    /**
     * Gets (as xml) the "deliverWithoutSignature" element
     */
    org.apache.xmlbeans.XmlBoolean xgetDeliverWithoutSignature();
    
    /**
     * True if has "deliverWithoutSignature" element
     */
    boolean isSetDeliverWithoutSignature();
    
    /**
     * Sets the "deliverWithoutSignature" element
     */
    void setDeliverWithoutSignature(boolean deliverWithoutSignature);
    
    /**
     * Sets (as xml) the "deliverWithoutSignature" element
     */
    void xsetDeliverWithoutSignature(org.apache.xmlbeans.XmlBoolean deliverWithoutSignature);
    
    /**
     * Unsets the "deliverWithoutSignature" element
     */
    void unsetDeliverWithoutSignature();
    
    /**
     * Gets the "deliveryAddress" element
     */
    com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentAddress getDeliveryAddress();
    
    /**
     * Tests for nil "deliveryAddress" element
     */
    boolean isNilDeliveryAddress();
    
    /**
     * True if has "deliveryAddress" element
     */
    boolean isSetDeliveryAddress();
    
    /**
     * Sets the "deliveryAddress" element
     */
    void setDeliveryAddress(com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentAddress deliveryAddress);
    
    /**
     * Appends and returns a new empty "deliveryAddress" element
     */
    com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentAddress addNewDeliveryAddress();
    
    /**
     * Nils the "deliveryAddress" element
     */
    void setNilDeliveryAddress();
    
    /**
     * Unsets the "deliveryAddress" element
     */
    void unsetDeliveryAddress();
    
    /**
     * Gets the "deliveryType" element
     */
    int getDeliveryType();
    
    /**
     * Gets (as xml) the "deliveryType" element
     */
    org.apache.xmlbeans.XmlInt xgetDeliveryType();
    
    /**
     * True if has "deliveryType" element
     */
    boolean isSetDeliveryType();
    
    /**
     * Sets the "deliveryType" element
     */
    void setDeliveryType(int deliveryType);
    
    /**
     * Sets (as xml) the "deliveryType" element
     */
    void xsetDeliveryType(org.apache.xmlbeans.XmlInt deliveryType);
    
    /**
     * Unsets the "deliveryType" element
     */
    void unsetDeliveryType();
    
    /**
     * Gets the "email" element
     */
    java.lang.String getEmail();
    
    /**
     * Gets (as xml) the "email" element
     */
    org.apache.xmlbeans.XmlString xgetEmail();
    
    /**
     * Tests for nil "email" element
     */
    boolean isNilEmail();
    
    /**
     * True if has "email" element
     */
    boolean isSetEmail();
    
    /**
     * Sets the "email" element
     */
    void setEmail(java.lang.String email);
    
    /**
     * Sets (as xml) the "email" element
     */
    void xsetEmail(org.apache.xmlbeans.XmlString email);
    
    /**
     * Nils the "email" element
     */
    void setNilEmail();
    
    /**
     * Unsets the "email" element
     */
    void unsetEmail();
    
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
     * Gets array of all "itinerary" elements
     */
    com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentItinerary[] getItineraryArray();
    
    /**
     * Gets ith "itinerary" element
     */
    com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentItinerary getItineraryArray(int i);
    
    /**
     * Tests for nil ith "itinerary" element
     */
    boolean isNilItineraryArray(int i);
    
    /**
     * Returns number of "itinerary" element
     */
    int sizeOfItineraryArray();
    
    /**
     * Sets array of all "itinerary" element
     */
    void setItineraryArray(com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentItinerary[] itineraryArray);
    
    /**
     * Sets ith "itinerary" element
     */
    void setItineraryArray(int i, com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentItinerary itinerary);
    
    /**
     * Nils the ith "itinerary" element
     */
    void setNilItineraryArray(int i);
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "itinerary" element
     */
    com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentItinerary insertNewItinerary(int i);
    
    /**
     * Appends and returns a new empty value (as xml) as the last "itinerary" element
     */
    com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentItinerary addNewItinerary();
    
    /**
     * Removes the ith "itinerary" element
     */
    void removeItinerary(int i);
    
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
     * Gets the "membershipNumber" element
     */
    java.lang.String getMembershipNumber();
    
    /**
     * Gets (as xml) the "membershipNumber" element
     */
    org.apache.xmlbeans.XmlString xgetMembershipNumber();
    
    /**
     * Tests for nil "membershipNumber" element
     */
    boolean isNilMembershipNumber();
    
    /**
     * True if has "membershipNumber" element
     */
    boolean isSetMembershipNumber();
    
    /**
     * Sets the "membershipNumber" element
     */
    void setMembershipNumber(java.lang.String membershipNumber);
    
    /**
     * Sets (as xml) the "membershipNumber" element
     */
    void xsetMembershipNumber(org.apache.xmlbeans.XmlString membershipNumber);
    
    /**
     * Nils the "membershipNumber" element
     */
    void setNilMembershipNumber();
    
    /**
     * Unsets the "membershipNumber" element
     */
    void unsetMembershipNumber();
    
    /**
     * Gets the "membershipStatus" element
     */
    java.lang.String getMembershipStatus();
    
    /**
     * Gets (as xml) the "membershipStatus" element
     */
    org.apache.xmlbeans.XmlString xgetMembershipStatus();
    
    /**
     * Tests for nil "membershipStatus" element
     */
    boolean isNilMembershipStatus();
    
    /**
     * True if has "membershipStatus" element
     */
    boolean isSetMembershipStatus();
    
    /**
     * Sets the "membershipStatus" element
     */
    void setMembershipStatus(java.lang.String membershipStatus);
    
    /**
     * Sets (as xml) the "membershipStatus" element
     */
    void xsetMembershipStatus(org.apache.xmlbeans.XmlString membershipStatus);
    
    /**
     * Nils the "membershipStatus" element
     */
    void setNilMembershipStatus();
    
    /**
     * Unsets the "membershipStatus" element
     */
    void unsetMembershipStatus();
    
    /**
     * Gets the "numPassengers" element
     */
    int getNumPassengers();
    
    /**
     * Gets (as xml) the "numPassengers" element
     */
    org.apache.xmlbeans.XmlInt xgetNumPassengers();
    
    /**
     * True if has "numPassengers" element
     */
    boolean isSetNumPassengers();
    
    /**
     * Sets the "numPassengers" element
     */
    void setNumPassengers(int numPassengers);
    
    /**
     * Sets (as xml) the "numPassengers" element
     */
    void xsetNumPassengers(org.apache.xmlbeans.XmlInt numPassengers);
    
    /**
     * Unsets the "numPassengers" element
     */
    void unsetNumPassengers();
    
    /**
     * Gets the "osi" element
     */
    java.lang.String getOsi();
    
    /**
     * Gets (as xml) the "osi" element
     */
    org.apache.xmlbeans.XmlString xgetOsi();
    
    /**
     * Tests for nil "osi" element
     */
    boolean isNilOsi();
    
    /**
     * True if has "osi" element
     */
    boolean isSetOsi();
    
    /**
     * Sets the "osi" element
     */
    void setOsi(java.lang.String osi);
    
    /**
     * Sets (as xml) the "osi" element
     */
    void xsetOsi(org.apache.xmlbeans.XmlString osi);
    
    /**
     * Nils the "osi" element
     */
    void setNilOsi();
    
    /**
     * Unsets the "osi" element
     */
    void unsetOsi();
    
    /**
     * Gets array of all "phone" elements
     */
    com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentPhone[] getPhoneArray();
    
    /**
     * Gets ith "phone" element
     */
    com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentPhone getPhoneArray(int i);
    
    /**
     * Tests for nil ith "phone" element
     */
    boolean isNilPhoneArray(int i);
    
    /**
     * Returns number of "phone" element
     */
    int sizeOfPhoneArray();
    
    /**
     * Sets array of all "phone" element
     */
    void setPhoneArray(com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentPhone[] phoneArray);
    
    /**
     * Sets ith "phone" element
     */
    void setPhoneArray(int i, com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentPhone phone);
    
    /**
     * Nils the ith "phone" element
     */
    void setNilPhoneArray(int i);
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "phone" element
     */
    com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentPhone insertNewPhone(int i);
    
    /**
     * Appends and returns a new empty value (as xml) as the last "phone" element
     */
    com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentPhone addNewPhone();
    
    /**
     * Removes the ith "phone" element
     */
    void removePhone(int i);
    
    /**
     * Gets the "pnr" element
     */
    java.lang.String getPnr();
    
    /**
     * Gets (as xml) the "pnr" element
     */
    org.apache.xmlbeans.XmlString xgetPnr();
    
    /**
     * Tests for nil "pnr" element
     */
    boolean isNilPnr();
    
    /**
     * True if has "pnr" element
     */
    boolean isSetPnr();
    
    /**
     * Sets the "pnr" element
     */
    void setPnr(java.lang.String pnr);
    
    /**
     * Sets (as xml) the "pnr" element
     */
    void xsetPnr(org.apache.xmlbeans.XmlString pnr);
    
    /**
     * Nils the "pnr" element
     */
    void setNilPnr();
    
    /**
     * Unsets the "pnr" element
     */
    void unsetPnr();
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Incident newInstance() {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Incident) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Incident newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Incident) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Incident parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Incident) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Incident parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Incident) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Incident parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Incident) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Incident parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Incident) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Incident parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Incident) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Incident parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Incident) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Incident parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Incident) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Incident parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Incident) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Incident parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Incident) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Incident parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Incident) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Incident parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Incident) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Incident parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Incident) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Incident parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Incident) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Incident parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Incident) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Incident parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Incident) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Incident parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Incident) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
