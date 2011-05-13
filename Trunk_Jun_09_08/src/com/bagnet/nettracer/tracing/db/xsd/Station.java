/*
 * XML Type:  Station
 * Namespace: http://db.tracing.nettracer.bagnet.com/xsd
 * Java type: com.bagnet.nettracer.tracing.db.xsd.Station
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.tracing.db.xsd;


/**
 * An XML Station(@http://db.tracing.nettracer.bagnet.com/xsd).
 *
 * This is a complex type.
 */
public interface Station extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(Station.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s44D040BCFE05AB6C78726EB309E96766").resolveHandle("station5e45type");
    
    /**
     * Gets the "active" element
     */
    boolean getActive();
    
    /**
     * Gets (as xml) the "active" element
     */
    org.apache.xmlbeans.XmlBoolean xgetActive();
    
    /**
     * True if has "active" element
     */
    boolean isSetActive();
    
    /**
     * Sets the "active" element
     */
    void setActive(boolean active);
    
    /**
     * Sets (as xml) the "active" element
     */
    void xsetActive(org.apache.xmlbeans.XmlBoolean active);
    
    /**
     * Unsets the "active" element
     */
    void unsetActive();
    
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
     * Gets the "airport_location" element
     */
    java.lang.String getAirportLocation();
    
    /**
     * Gets (as xml) the "airport_location" element
     */
    org.apache.xmlbeans.XmlString xgetAirportLocation();
    
    /**
     * Tests for nil "airport_location" element
     */
    boolean isNilAirportLocation();
    
    /**
     * True if has "airport_location" element
     */
    boolean isSetAirportLocation();
    
    /**
     * Sets the "airport_location" element
     */
    void setAirportLocation(java.lang.String airportLocation);
    
    /**
     * Sets (as xml) the "airport_location" element
     */
    void xsetAirportLocation(org.apache.xmlbeans.XmlString airportLocation);
    
    /**
     * Nils the "airport_location" element
     */
    void setNilAirportLocation();
    
    /**
     * Unsets the "airport_location" element
     */
    void unsetAirportLocation();
    
    /**
     * Gets the "associated_airport" element
     */
    java.lang.String getAssociatedAirport();
    
    /**
     * Gets (as xml) the "associated_airport" element
     */
    org.apache.xmlbeans.XmlString xgetAssociatedAirport();
    
    /**
     * Tests for nil "associated_airport" element
     */
    boolean isNilAssociatedAirport();
    
    /**
     * True if has "associated_airport" element
     */
    boolean isSetAssociatedAirport();
    
    /**
     * Sets the "associated_airport" element
     */
    void setAssociatedAirport(java.lang.String associatedAirport);
    
    /**
     * Sets (as xml) the "associated_airport" element
     */
    void xsetAssociatedAirport(org.apache.xmlbeans.XmlString associatedAirport);
    
    /**
     * Nils the "associated_airport" element
     */
    void setNilAssociatedAirport();
    
    /**
     * Unsets the "associated_airport" element
     */
    void unsetAssociatedAirport();
    
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
     * Gets the "countrycode_ID" element
     */
    java.lang.String getCountrycodeID();
    
    /**
     * Gets (as xml) the "countrycode_ID" element
     */
    org.apache.xmlbeans.XmlString xgetCountrycodeID();
    
    /**
     * Tests for nil "countrycode_ID" element
     */
    boolean isNilCountrycodeID();
    
    /**
     * True if has "countrycode_ID" element
     */
    boolean isSetCountrycodeID();
    
    /**
     * Sets the "countrycode_ID" element
     */
    void setCountrycodeID(java.lang.String countrycodeID);
    
    /**
     * Sets (as xml) the "countrycode_ID" element
     */
    void xsetCountrycodeID(org.apache.xmlbeans.XmlString countrycodeID);
    
    /**
     * Nils the "countrycode_ID" element
     */
    void setNilCountrycodeID();
    
    /**
     * Unsets the "countrycode_ID" element
     */
    void unsetCountrycodeID();
    
    /**
     * Gets the "emailLanguage" element
     */
    java.lang.String getEmailLanguage();
    
    /**
     * Gets (as xml) the "emailLanguage" element
     */
    org.apache.xmlbeans.XmlString xgetEmailLanguage();
    
    /**
     * Tests for nil "emailLanguage" element
     */
    boolean isNilEmailLanguage();
    
    /**
     * True if has "emailLanguage" element
     */
    boolean isSetEmailLanguage();
    
    /**
     * Sets the "emailLanguage" element
     */
    void setEmailLanguage(java.lang.String emailLanguage);
    
    /**
     * Sets (as xml) the "emailLanguage" element
     */
    void xsetEmailLanguage(org.apache.xmlbeans.XmlString emailLanguage);
    
    /**
     * Nils the "emailLanguage" element
     */
    void setNilEmailLanguage();
    
    /**
     * Unsets the "emailLanguage" element
     */
    void unsetEmailLanguage();
    
    /**
     * Gets the "goal" element
     */
    double getGoal();
    
    /**
     * Gets (as xml) the "goal" element
     */
    org.apache.xmlbeans.XmlDouble xgetGoal();
    
    /**
     * True if has "goal" element
     */
    boolean isSetGoal();
    
    /**
     * Sets the "goal" element
     */
    void setGoal(double goal);
    
    /**
     * Sets (as xml) the "goal" element
     */
    void xsetGoal(org.apache.xmlbeans.XmlDouble goal);
    
    /**
     * Unsets the "goal" element
     */
    void unsetGoal();
    
    /**
     * Gets the "lz_ID" element
     */
    int getLzID();
    
    /**
     * Gets (as xml) the "lz_ID" element
     */
    org.apache.xmlbeans.XmlInt xgetLzID();
    
    /**
     * True if has "lz_ID" element
     */
    boolean isSetLzID();
    
    /**
     * Sets the "lz_ID" element
     */
    void setLzID(int lzID);
    
    /**
     * Sets (as xml) the "lz_ID" element
     */
    void xsetLzID(org.apache.xmlbeans.XmlInt lzID);
    
    /**
     * Unsets the "lz_ID" element
     */
    void unsetLzID();
    
    /**
     * Gets the "operation_hours" element
     */
    java.lang.String getOperationHours();
    
    /**
     * Gets (as xml) the "operation_hours" element
     */
    org.apache.xmlbeans.XmlString xgetOperationHours();
    
    /**
     * Tests for nil "operation_hours" element
     */
    boolean isNilOperationHours();
    
    /**
     * True if has "operation_hours" element
     */
    boolean isSetOperationHours();
    
    /**
     * Sets the "operation_hours" element
     */
    void setOperationHours(java.lang.String operationHours);
    
    /**
     * Sets (as xml) the "operation_hours" element
     */
    void xsetOperationHours(org.apache.xmlbeans.XmlString operationHours);
    
    /**
     * Nils the "operation_hours" element
     */
    void setNilOperationHours();
    
    /**
     * Unsets the "operation_hours" element
     */
    void unsetOperationHours();
    
    /**
     * Gets the "phone" element
     */
    java.lang.String getPhone();
    
    /**
     * Gets (as xml) the "phone" element
     */
    org.apache.xmlbeans.XmlString xgetPhone();
    
    /**
     * Tests for nil "phone" element
     */
    boolean isNilPhone();
    
    /**
     * True if has "phone" element
     */
    boolean isSetPhone();
    
    /**
     * Sets the "phone" element
     */
    void setPhone(java.lang.String phone);
    
    /**
     * Sets (as xml) the "phone" element
     */
    void xsetPhone(org.apache.xmlbeans.XmlString phone);
    
    /**
     * Nils the "phone" element
     */
    void setNilPhone();
    
    /**
     * Unsets the "phone" element
     */
    void unsetPhone();
    
    /**
     * Gets the "priority" element
     */
    int getPriority();
    
    /**
     * Gets (as xml) the "priority" element
     */
    org.apache.xmlbeans.XmlInt xgetPriority();
    
    /**
     * True if has "priority" element
     */
    boolean isSetPriority();
    
    /**
     * Sets the "priority" element
     */
    void setPriority(int priority);
    
    /**
     * Sets (as xml) the "priority" element
     */
    void xsetPriority(org.apache.xmlbeans.XmlInt priority);
    
    /**
     * Unsets the "priority" element
     */
    void unsetPriority();
    
    /**
     * Gets the "state_ID" element
     */
    java.lang.String getStateID();
    
    /**
     * Gets (as xml) the "state_ID" element
     */
    org.apache.xmlbeans.XmlString xgetStateID();
    
    /**
     * Tests for nil "state_ID" element
     */
    boolean isNilStateID();
    
    /**
     * True if has "state_ID" element
     */
    boolean isSetStateID();
    
    /**
     * Sets the "state_ID" element
     */
    void setStateID(java.lang.String stateID);
    
    /**
     * Sets (as xml) the "state_ID" element
     */
    void xsetStateID(org.apache.xmlbeans.XmlString stateID);
    
    /**
     * Nils the "state_ID" element
     */
    void setNilStateID();
    
    /**
     * Unsets the "state_ID" element
     */
    void unsetStateID();
    
    /**
     * Gets the "station_ID" element
     */
    int getStationID();
    
    /**
     * Gets (as xml) the "station_ID" element
     */
    org.apache.xmlbeans.XmlInt xgetStationID();
    
    /**
     * True if has "station_ID" element
     */
    boolean isSetStationID();
    
    /**
     * Sets the "station_ID" element
     */
    void setStationID(int stationID);
    
    /**
     * Sets (as xml) the "station_ID" element
     */
    void xsetStationID(org.apache.xmlbeans.XmlInt stationID);
    
    /**
     * Unsets the "station_ID" element
     */
    void unsetStationID();
    
    /**
     * Gets the "station_region" element
     */
    java.lang.String getStationRegion();
    
    /**
     * Gets (as xml) the "station_region" element
     */
    org.apache.xmlbeans.XmlString xgetStationRegion();
    
    /**
     * Tests for nil "station_region" element
     */
    boolean isNilStationRegion();
    
    /**
     * True if has "station_region" element
     */
    boolean isSetStationRegion();
    
    /**
     * Sets the "station_region" element
     */
    void setStationRegion(java.lang.String stationRegion);
    
    /**
     * Sets (as xml) the "station_region" element
     */
    void xsetStationRegion(org.apache.xmlbeans.XmlString stationRegion);
    
    /**
     * Nils the "station_region" element
     */
    void setNilStationRegion();
    
    /**
     * Unsets the "station_region" element
     */
    void unsetStationRegion();
    
    /**
     * Gets the "station_region_mgr" element
     */
    java.lang.String getStationRegionMgr();
    
    /**
     * Gets (as xml) the "station_region_mgr" element
     */
    org.apache.xmlbeans.XmlString xgetStationRegionMgr();
    
    /**
     * Tests for nil "station_region_mgr" element
     */
    boolean isNilStationRegionMgr();
    
    /**
     * True if has "station_region_mgr" element
     */
    boolean isSetStationRegionMgr();
    
    /**
     * Sets the "station_region_mgr" element
     */
    void setStationRegionMgr(java.lang.String stationRegionMgr);
    
    /**
     * Sets (as xml) the "station_region_mgr" element
     */
    void xsetStationRegionMgr(org.apache.xmlbeans.XmlString stationRegionMgr);
    
    /**
     * Nils the "station_region_mgr" element
     */
    void setNilStationRegionMgr();
    
    /**
     * Unsets the "station_region_mgr" element
     */
    void unsetStationRegionMgr();
    
    /**
     * Gets the "stationcode" element
     */
    java.lang.String getStationcode();
    
    /**
     * Gets (as xml) the "stationcode" element
     */
    org.apache.xmlbeans.XmlString xgetStationcode();
    
    /**
     * Tests for nil "stationcode" element
     */
    boolean isNilStationcode();
    
    /**
     * True if has "stationcode" element
     */
    boolean isSetStationcode();
    
    /**
     * Sets the "stationcode" element
     */
    void setStationcode(java.lang.String stationcode);
    
    /**
     * Sets (as xml) the "stationcode" element
     */
    void xsetStationcode(org.apache.xmlbeans.XmlString stationcode);
    
    /**
     * Nils the "stationcode" element
     */
    void setNilStationcode();
    
    /**
     * Unsets the "stationcode" element
     */
    void unsetStationcode();
    
    /**
     * Gets the "stationdesc" element
     */
    java.lang.String getStationdesc();
    
    /**
     * Gets (as xml) the "stationdesc" element
     */
    org.apache.xmlbeans.XmlString xgetStationdesc();
    
    /**
     * Tests for nil "stationdesc" element
     */
    boolean isNilStationdesc();
    
    /**
     * True if has "stationdesc" element
     */
    boolean isSetStationdesc();
    
    /**
     * Sets the "stationdesc" element
     */
    void setStationdesc(java.lang.String stationdesc);
    
    /**
     * Sets (as xml) the "stationdesc" element
     */
    void xsetStationdesc(org.apache.xmlbeans.XmlString stationdesc);
    
    /**
     * Nils the "stationdesc" element
     */
    void setNilStationdesc();
    
    /**
     * Unsets the "stationdesc" element
     */
    void unsetStationdesc();
    
    /**
     * Gets the "thisOhdLz" element
     */
    boolean getThisOhdLz();
    
    /**
     * Gets (as xml) the "thisOhdLz" element
     */
    org.apache.xmlbeans.XmlBoolean xgetThisOhdLz();
    
    /**
     * True if has "thisOhdLz" element
     */
    boolean isSetThisOhdLz();
    
    /**
     * Sets the "thisOhdLz" element
     */
    void setThisOhdLz(boolean thisOhdLz);
    
    /**
     * Sets (as xml) the "thisOhdLz" element
     */
    void xsetThisOhdLz(org.apache.xmlbeans.XmlBoolean thisOhdLz);
    
    /**
     * Unsets the "thisOhdLz" element
     */
    void unsetThisOhdLz();
    
    /**
     * Gets the "wt_stationcode" element
     */
    java.lang.String getWtStationcode();
    
    /**
     * Gets (as xml) the "wt_stationcode" element
     */
    org.apache.xmlbeans.XmlString xgetWtStationcode();
    
    /**
     * Tests for nil "wt_stationcode" element
     */
    boolean isNilWtStationcode();
    
    /**
     * True if has "wt_stationcode" element
     */
    boolean isSetWtStationcode();
    
    /**
     * Sets the "wt_stationcode" element
     */
    void setWtStationcode(java.lang.String wtStationcode);
    
    /**
     * Sets (as xml) the "wt_stationcode" element
     */
    void xsetWtStationcode(org.apache.xmlbeans.XmlString wtStationcode);
    
    /**
     * Nils the "wt_stationcode" element
     */
    void setNilWtStationcode();
    
    /**
     * Unsets the "wt_stationcode" element
     */
    void unsetWtStationcode();
    
    /**
     * Gets the "zip" element
     */
    java.lang.String getZip();
    
    /**
     * Gets (as xml) the "zip" element
     */
    org.apache.xmlbeans.XmlString xgetZip();
    
    /**
     * Tests for nil "zip" element
     */
    boolean isNilZip();
    
    /**
     * True if has "zip" element
     */
    boolean isSetZip();
    
    /**
     * Sets the "zip" element
     */
    void setZip(java.lang.String zip);
    
    /**
     * Sets (as xml) the "zip" element
     */
    void xsetZip(org.apache.xmlbeans.XmlString zip);
    
    /**
     * Nils the "zip" element
     */
    void setNilZip();
    
    /**
     * Unsets the "zip" element
     */
    void unsetZip();
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static com.bagnet.nettracer.tracing.db.xsd.Station newInstance() {
          return (com.bagnet.nettracer.tracing.db.xsd.Station) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static com.bagnet.nettracer.tracing.db.xsd.Station newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (com.bagnet.nettracer.tracing.db.xsd.Station) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static com.bagnet.nettracer.tracing.db.xsd.Station parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.tracing.db.xsd.Station) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static com.bagnet.nettracer.tracing.db.xsd.Station parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.tracing.db.xsd.Station) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static com.bagnet.nettracer.tracing.db.xsd.Station parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.tracing.db.xsd.Station) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static com.bagnet.nettracer.tracing.db.xsd.Station parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.tracing.db.xsd.Station) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static com.bagnet.nettracer.tracing.db.xsd.Station parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.tracing.db.xsd.Station) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static com.bagnet.nettracer.tracing.db.xsd.Station parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.tracing.db.xsd.Station) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static com.bagnet.nettracer.tracing.db.xsd.Station parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.tracing.db.xsd.Station) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static com.bagnet.nettracer.tracing.db.xsd.Station parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.tracing.db.xsd.Station) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static com.bagnet.nettracer.tracing.db.xsd.Station parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.tracing.db.xsd.Station) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static com.bagnet.nettracer.tracing.db.xsd.Station parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.tracing.db.xsd.Station) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static com.bagnet.nettracer.tracing.db.xsd.Station parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.tracing.db.xsd.Station) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static com.bagnet.nettracer.tracing.db.xsd.Station parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.tracing.db.xsd.Station) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static com.bagnet.nettracer.tracing.db.xsd.Station parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.tracing.db.xsd.Station) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static com.bagnet.nettracer.tracing.db.xsd.Station parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.tracing.db.xsd.Station) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.bagnet.nettracer.tracing.db.xsd.Station parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.bagnet.nettracer.tracing.db.xsd.Station) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.bagnet.nettracer.tracing.db.xsd.Station parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.bagnet.nettracer.tracing.db.xsd.Station) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
