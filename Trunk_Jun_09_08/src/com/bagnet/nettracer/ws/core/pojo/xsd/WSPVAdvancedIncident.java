/*
 * XML Type:  WS_PVAdvancedIncident
 * Namespace: http://pojo.core.ws.nettracer.bagnet.com/xsd
 * Java type: com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.core.pojo.xsd;


/**
 * An XML WS_PVAdvancedIncident(@http://pojo.core.ws.nettracer.bagnet.com/xsd).
 *
 * This is a complex type.
 */
public interface WSPVAdvancedIncident extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(WSPVAdvancedIncident.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sB19FE03AAADC8EF07F1707671AB638D5").resolveHandle("wspvadvancedincident350dtype");
    
    /**
     * Gets the "canCreateClaim" element
     */
    boolean getCanCreateClaim();
    
    /**
     * Gets (as xml) the "canCreateClaim" element
     */
    org.apache.xmlbeans.XmlBoolean xgetCanCreateClaim();
    
    /**
     * True if has "canCreateClaim" element
     */
    boolean isSetCanCreateClaim();
    
    /**
     * Sets the "canCreateClaim" element
     */
    void setCanCreateClaim(boolean canCreateClaim);
    
    /**
     * Sets (as xml) the "canCreateClaim" element
     */
    void xsetCanCreateClaim(org.apache.xmlbeans.XmlBoolean canCreateClaim);
    
    /**
     * Unsets the "canCreateClaim" element
     */
    void unsetCanCreateClaim();
    
    /**
     * Gets array of all "claimChecks" elements
     */
    com.bagnet.nettracer.ws.core.pojo.xsd.WSClaimCheck[] getClaimChecksArray();
    
    /**
     * Gets ith "claimChecks" element
     */
    com.bagnet.nettracer.ws.core.pojo.xsd.WSClaimCheck getClaimChecksArray(int i);
    
    /**
     * Tests for nil ith "claimChecks" element
     */
    boolean isNilClaimChecksArray(int i);
    
    /**
     * Returns number of "claimChecks" element
     */
    int sizeOfClaimChecksArray();
    
    /**
     * Sets array of all "claimChecks" element
     */
    void setClaimChecksArray(com.bagnet.nettracer.ws.core.pojo.xsd.WSClaimCheck[] claimChecksArray);
    
    /**
     * Sets ith "claimChecks" element
     */
    void setClaimChecksArray(int i, com.bagnet.nettracer.ws.core.pojo.xsd.WSClaimCheck claimChecks);
    
    /**
     * Nils the ith "claimChecks" element
     */
    void setNilClaimChecksArray(int i);
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "claimChecks" element
     */
    com.bagnet.nettracer.ws.core.pojo.xsd.WSClaimCheck insertNewClaimChecks(int i);
    
    /**
     * Appends and returns a new empty value (as xml) as the last "claimChecks" element
     */
    com.bagnet.nettracer.ws.core.pojo.xsd.WSClaimCheck addNewClaimChecks();
    
    /**
     * Removes the ith "claimChecks" element
     */
    void removeClaimChecks(int i);
    
    /**
     * Gets the "comments" element
     */
    java.lang.String getComments();
    
    /**
     * Gets (as xml) the "comments" element
     */
    org.apache.xmlbeans.XmlString xgetComments();
    
    /**
     * Tests for nil "comments" element
     */
    boolean isNilComments();
    
    /**
     * True if has "comments" element
     */
    boolean isSetComments();
    
    /**
     * Sets the "comments" element
     */
    void setComments(java.lang.String comments);
    
    /**
     * Sets (as xml) the "comments" element
     */
    void xsetComments(org.apache.xmlbeans.XmlString comments);
    
    /**
     * Nils the "comments" element
     */
    void setNilComments();
    
    /**
     * Unsets the "comments" element
     */
    void unsetComments();
    
    /**
     * Gets the "companycode_id" element
     */
    java.lang.String getCompanycodeId();
    
    /**
     * Gets (as xml) the "companycode_id" element
     */
    org.apache.xmlbeans.XmlString xgetCompanycodeId();
    
    /**
     * Tests for nil "companycode_id" element
     */
    boolean isNilCompanycodeId();
    
    /**
     * True if has "companycode_id" element
     */
    boolean isSetCompanycodeId();
    
    /**
     * Sets the "companycode_id" element
     */
    void setCompanycodeId(java.lang.String companycodeId);
    
    /**
     * Sets (as xml) the "companycode_id" element
     */
    void xsetCompanycodeId(org.apache.xmlbeans.XmlString companycodeId);
    
    /**
     * Nils the "companycode_id" element
     */
    void setNilCompanycodeId();
    
    /**
     * Unsets the "companycode_id" element
     */
    void unsetCompanycodeId();
    
    /**
     * Gets the "createdate" element
     */
    java.util.Calendar getCreatedate();
    
    /**
     * Gets (as xml) the "createdate" element
     */
    org.apache.xmlbeans.XmlDateTime xgetCreatedate();
    
    /**
     * Tests for nil "createdate" element
     */
    boolean isNilCreatedate();
    
    /**
     * True if has "createdate" element
     */
    boolean isSetCreatedate();
    
    /**
     * Sets the "createdate" element
     */
    void setCreatedate(java.util.Calendar createdate);
    
    /**
     * Sets (as xml) the "createdate" element
     */
    void xsetCreatedate(org.apache.xmlbeans.XmlDateTime createdate);
    
    /**
     * Nils the "createdate" element
     */
    void setNilCreatedate();
    
    /**
     * Unsets the "createdate" element
     */
    void unsetCreatedate();
    
    /**
     * Gets the "dispcreatetime" element
     */
    java.lang.String getDispcreatetime();
    
    /**
     * Gets (as xml) the "dispcreatetime" element
     */
    org.apache.xmlbeans.XmlString xgetDispcreatetime();
    
    /**
     * Tests for nil "dispcreatetime" element
     */
    boolean isNilDispcreatetime();
    
    /**
     * True if has "dispcreatetime" element
     */
    boolean isSetDispcreatetime();
    
    /**
     * Sets the "dispcreatetime" element
     */
    void setDispcreatetime(java.lang.String dispcreatetime);
    
    /**
     * Sets (as xml) the "dispcreatetime" element
     */
    void xsetDispcreatetime(org.apache.xmlbeans.XmlString dispcreatetime);
    
    /**
     * Nils the "dispcreatetime" element
     */
    void setNilDispcreatetime();
    
    /**
     * Unsets the "dispcreatetime" element
     */
    void unsetDispcreatetime();
    
    /**
     * Gets the "errorcode" element
     */
    java.lang.String getErrorcode();
    
    /**
     * Gets (as xml) the "errorcode" element
     */
    org.apache.xmlbeans.XmlString xgetErrorcode();
    
    /**
     * Tests for nil "errorcode" element
     */
    boolean isNilErrorcode();
    
    /**
     * True if has "errorcode" element
     */
    boolean isSetErrorcode();
    
    /**
     * Sets the "errorcode" element
     */
    void setErrorcode(java.lang.String errorcode);
    
    /**
     * Sets (as xml) the "errorcode" element
     */
    void xsetErrorcode(org.apache.xmlbeans.XmlString errorcode);
    
    /**
     * Nils the "errorcode" element
     */
    void setNilErrorcode();
    
    /**
     * Unsets the "errorcode" element
     */
    void unsetErrorcode();
    
    /**
     * Gets the "incident_ID" element
     */
    java.lang.String getIncidentID();
    
    /**
     * Gets (as xml) the "incident_ID" element
     */
    org.apache.xmlbeans.XmlString xgetIncidentID();
    
    /**
     * Tests for nil "incident_ID" element
     */
    boolean isNilIncidentID();
    
    /**
     * True if has "incident_ID" element
     */
    boolean isSetIncidentID();
    
    /**
     * Sets the "incident_ID" element
     */
    void setIncidentID(java.lang.String incidentID);
    
    /**
     * Sets (as xml) the "incident_ID" element
     */
    void xsetIncidentID(org.apache.xmlbeans.XmlString incidentID);
    
    /**
     * Nils the "incident_ID" element
     */
    void setNilIncidentID();
    
    /**
     * Unsets the "incident_ID" element
     */
    void unsetIncidentID();
    
    /**
     * Gets the "incident_status" element
     */
    java.lang.String getIncidentStatus();
    
    /**
     * Gets (as xml) the "incident_status" element
     */
    org.apache.xmlbeans.XmlString xgetIncidentStatus();
    
    /**
     * Tests for nil "incident_status" element
     */
    boolean isNilIncidentStatus();
    
    /**
     * True if has "incident_status" element
     */
    boolean isSetIncidentStatus();
    
    /**
     * Sets the "incident_status" element
     */
    void setIncidentStatus(java.lang.String incidentStatus);
    
    /**
     * Sets (as xml) the "incident_status" element
     */
    void xsetIncidentStatus(org.apache.xmlbeans.XmlString incidentStatus);
    
    /**
     * Nils the "incident_status" element
     */
    void setNilIncidentStatus();
    
    /**
     * Unsets the "incident_status" element
     */
    void unsetIncidentStatus();
    
    /**
     * Gets the "itemType" element
     */
    int getItemType();
    
    /**
     * Gets (as xml) the "itemType" element
     */
    org.apache.xmlbeans.XmlInt xgetItemType();
    
    /**
     * True if has "itemType" element
     */
    boolean isSetItemType();
    
    /**
     * Sets the "itemType" element
     */
    void setItemType(int itemType);
    
    /**
     * Sets (as xml) the "itemType" element
     */
    void xsetItemType(org.apache.xmlbeans.XmlInt itemType);
    
    /**
     * Unsets the "itemType" element
     */
    void unsetItemType();
    
    /**
     * Gets array of all "items" elements
     */
    com.bagnet.nettracer.ws.core.pojo.xsd.WSPVItem[] getItemsArray();
    
    /**
     * Gets ith "items" element
     */
    com.bagnet.nettracer.ws.core.pojo.xsd.WSPVItem getItemsArray(int i);
    
    /**
     * Tests for nil ith "items" element
     */
    boolean isNilItemsArray(int i);
    
    /**
     * Returns number of "items" element
     */
    int sizeOfItemsArray();
    
    /**
     * Sets array of all "items" element
     */
    void setItemsArray(com.bagnet.nettracer.ws.core.pojo.xsd.WSPVItem[] itemsArray);
    
    /**
     * Sets ith "items" element
     */
    void setItemsArray(int i, com.bagnet.nettracer.ws.core.pojo.xsd.WSPVItem items);
    
    /**
     * Nils the ith "items" element
     */
    void setNilItemsArray(int i);
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "items" element
     */
    com.bagnet.nettracer.ws.core.pojo.xsd.WSPVItem insertNewItems(int i);
    
    /**
     * Appends and returns a new empty value (as xml) as the last "items" element
     */
    com.bagnet.nettracer.ws.core.pojo.xsd.WSPVItem addNewItems();
    
    /**
     * Removes the ith "items" element
     */
    void removeItems(int i);
    
    /**
     * Gets array of all "passengers" elements
     */
    com.bagnet.nettracer.ws.core.pojo.xsd.WSPVPassenger[] getPassengersArray();
    
    /**
     * Gets ith "passengers" element
     */
    com.bagnet.nettracer.ws.core.pojo.xsd.WSPVPassenger getPassengersArray(int i);
    
    /**
     * Tests for nil ith "passengers" element
     */
    boolean isNilPassengersArray(int i);
    
    /**
     * Returns number of "passengers" element
     */
    int sizeOfPassengersArray();
    
    /**
     * Sets array of all "passengers" element
     */
    void setPassengersArray(com.bagnet.nettracer.ws.core.pojo.xsd.WSPVPassenger[] passengersArray);
    
    /**
     * Sets ith "passengers" element
     */
    void setPassengersArray(int i, com.bagnet.nettracer.ws.core.pojo.xsd.WSPVPassenger passengers);
    
    /**
     * Nils the ith "passengers" element
     */
    void setNilPassengersArray(int i);
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "passengers" element
     */
    com.bagnet.nettracer.ws.core.pojo.xsd.WSPVPassenger insertNewPassengers(int i);
    
    /**
     * Appends and returns a new empty value (as xml) as the last "passengers" element
     */
    com.bagnet.nettracer.ws.core.pojo.xsd.WSPVPassenger addNewPassengers();
    
    /**
     * Removes the ith "passengers" element
     */
    void removePassengers(int i);
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident newInstance() {
          return (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
