/*
 * XML Type:  WS_Incident
 * Namespace: http://pojo.core.ws.nettracer.bagnet.com/xsd
 * Java type: com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.core.pojo.xsd;


/**
 * An XML WS_Incident(@http://pojo.core.ws.nettracer.bagnet.com/xsd).
 *
 * This is a complex type.
 */
public interface WSIncident extends org.apache.xmlbeans.XmlObject {
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType) org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(WSIncident.class.getClassLoader(),
            "schemaorg_apache_xmlbeans.system.sB1D181FCD070B8C28352F412287BB191")
                                                                                                                           .resolveHandle("wsincidenta7d5type");

    /**
     * Gets the "agent" element
     */
    java.lang.String getAgent();

    /**
     * Gets (as xml) the "agent" element
     */
    org.apache.xmlbeans.XmlString xgetAgent();

    /**
     * Tests for nil "agent" element
     */
    boolean isNilAgent();

    /**
     * True if has "agent" element
     */
    boolean isSetAgent();

    /**
     * Sets the "agent" element
     */
    void setAgent(java.lang.String agent);

    /**
     * Sets (as xml) the "agent" element
     */
    void xsetAgent(org.apache.xmlbeans.XmlString agent);

    /**
     * Nils the "agent" element
     */
    void setNilAgent();

    /**
     * Unsets the "agent" element
     */
    void unsetAgent();

    /**
     * Gets the "agentassigned" element
     */
    java.lang.String getAgentassigned();

    /**
     * Gets (as xml) the "agentassigned" element
     */
    org.apache.xmlbeans.XmlString xgetAgentassigned();

    /**
     * Tests for nil "agentassigned" element
     */
    boolean isNilAgentassigned();

    /**
     * True if has "agentassigned" element
     */
    boolean isSetAgentassigned();

    /**
     * Sets the "agentassigned" element
     */
    void setAgentassigned(java.lang.String agentassigned);

    /**
     * Sets (as xml) the "agentassigned" element
     */
    void xsetAgentassigned(org.apache.xmlbeans.XmlString agentassigned);

    /**
     * Nils the "agentassigned" element
     */
    void setNilAgentassigned();

    /**
     * Unsets the "agentassigned" element
     */
    void unsetAgentassigned();

    /**
     * Gets array of all "articles" elements
     */
    com.bagnet.nettracer.ws.core.pojo.xsd.WSArticle[] getArticlesArray();

    /**
     * Gets ith "articles" element
     */
    com.bagnet.nettracer.ws.core.pojo.xsd.WSArticle getArticlesArray(int i);

    /**
     * Tests for nil ith "articles" element
     */
    boolean isNilArticlesArray(int i);

    /**
     * Returns number of "articles" element
     */
    int sizeOfArticlesArray();

    /**
     * Sets array of all "articles" element
     */
    void setArticlesArray(
        com.bagnet.nettracer.ws.core.pojo.xsd.WSArticle[] articlesArray);

    /**
     * Sets ith "articles" element
     */
    void setArticlesArray(int i,
        com.bagnet.nettracer.ws.core.pojo.xsd.WSArticle articles);

    /**
     * Nils the ith "articles" element
     */
    void setNilArticlesArray(int i);

    /**
     * Inserts and returns a new empty value (as xml) as the ith "articles" element
     */
    com.bagnet.nettracer.ws.core.pojo.xsd.WSArticle insertNewArticles(int i);

    /**
     * Appends and returns a new empty value (as xml) as the last "articles" element
     */
    com.bagnet.nettracer.ws.core.pojo.xsd.WSArticle addNewArticles();

    /**
     * Removes the ith "articles" element
     */
    void removeArticles(int i);

    /**
     * Gets the "checkedlocation" element
     */
    java.lang.String getCheckedlocation();

    /**
     * Gets (as xml) the "checkedlocation" element
     */
    org.apache.xmlbeans.XmlString xgetCheckedlocation();

    /**
     * Tests for nil "checkedlocation" element
     */
    boolean isNilCheckedlocation();

    /**
     * True if has "checkedlocation" element
     */
    boolean isSetCheckedlocation();

    /**
     * Sets the "checkedlocation" element
     */
    void setCheckedlocation(java.lang.String checkedlocation);

    /**
     * Sets (as xml) the "checkedlocation" element
     */
    void xsetCheckedlocation(org.apache.xmlbeans.XmlString checkedlocation);

    /**
     * Nils the "checkedlocation" element
     */
    void setNilCheckedlocation();

    /**
     * Unsets the "checkedlocation" element
     */
    void unsetCheckedlocation();

    /**
     * Gets array of all "claimchecks" elements
     */
    com.bagnet.nettracer.ws.core.pojo.xsd.WSIncidentClaimCheck[] getClaimchecksArray();

    /**
     * Gets ith "claimchecks" element
     */
    com.bagnet.nettracer.ws.core.pojo.xsd.WSIncidentClaimCheck getClaimchecksArray(
        int i);

    /**
     * Tests for nil ith "claimchecks" element
     */
    boolean isNilClaimchecksArray(int i);

    /**
     * Returns number of "claimchecks" element
     */
    int sizeOfClaimchecksArray();

    /**
     * Sets array of all "claimchecks" element
     */
    void setClaimchecksArray(
        com.bagnet.nettracer.ws.core.pojo.xsd.WSIncidentClaimCheck[] claimchecksArray);

    /**
     * Sets ith "claimchecks" element
     */
    void setClaimchecksArray(int i,
        com.bagnet.nettracer.ws.core.pojo.xsd.WSIncidentClaimCheck claimchecks);

    /**
     * Nils the ith "claimchecks" element
     */
    void setNilClaimchecksArray(int i);

    /**
     * Inserts and returns a new empty value (as xml) as the ith "claimchecks" element
     */
    com.bagnet.nettracer.ws.core.pojo.xsd.WSIncidentClaimCheck insertNewClaimchecks(
        int i);

    /**
     * Appends and returns a new empty value (as xml) as the last "claimchecks" element
     */
    com.bagnet.nettracer.ws.core.pojo.xsd.WSIncidentClaimCheck addNewClaimchecks();

    /**
     * Removes the ith "claimchecks" element
     */
    void removeClaimchecks(int i);

    /**
     * Gets the "closedate" element
     */
    java.util.Calendar getClosedate();

    /**
     * Gets (as xml) the "closedate" element
     */
    org.apache.xmlbeans.XmlDateTime xgetClosedate();

    /**
     * Tests for nil "closedate" element
     */
    boolean isNilClosedate();

    /**
     * True if has "closedate" element
     */
    boolean isSetClosedate();

    /**
     * Sets the "closedate" element
     */
    void setClosedate(java.util.Calendar closedate);

    /**
     * Sets (as xml) the "closedate" element
     */
    void xsetClosedate(org.apache.xmlbeans.XmlDateTime closedate);

    /**
     * Nils the "closedate" element
     */
    void setNilClosedate();

    /**
     * Unsets the "closedate" element
     */
    void unsetClosedate();

    /**
     * Gets the "companycode_ID" element
     */
    java.lang.String getCompanycodeID();

    /**
     * Gets (as xml) the "companycode_ID" element
     */
    org.apache.xmlbeans.XmlString xgetCompanycodeID();

    /**
     * Tests for nil "companycode_ID" element
     */
    boolean isNilCompanycodeID();

    /**
     * True if has "companycode_ID" element
     */
    boolean isSetCompanycodeID();

    /**
     * Sets the "companycode_ID" element
     */
    void setCompanycodeID(java.lang.String companycodeID);

    /**
     * Sets (as xml) the "companycode_ID" element
     */
    void xsetCompanycodeID(org.apache.xmlbeans.XmlString companycodeID);

    /**
     * Nils the "companycode_ID" element
     */
    void setNilCompanycodeID();

    /**
     * Unsets the "companycode_ID" element
     */
    void unsetCompanycodeID();

    /**
     * Gets the "courtesyreport" element
     */
    int getCourtesyreport();

    /**
     * Gets (as xml) the "courtesyreport" element
     */
    org.apache.xmlbeans.XmlInt xgetCourtesyreport();

    /**
     * True if has "courtesyreport" element
     */
    boolean isSetCourtesyreport();

    /**
     * Sets the "courtesyreport" element
     */
    void setCourtesyreport(int courtesyreport);

    /**
     * Sets (as xml) the "courtesyreport" element
     */
    void xsetCourtesyreport(org.apache.xmlbeans.XmlInt courtesyreport);

    /**
     * Unsets the "courtesyreport" element
     */
    void unsetCourtesyreport();

    /**
     * Gets the "createDate" element
     */
    java.util.Calendar getCreateDate();

    /**
     * Gets (as xml) the "createDate" element
     */
    org.apache.xmlbeans.XmlDateTime xgetCreateDate();

    /**
     * Tests for nil "createDate" element
     */
    boolean isNilCreateDate();

    /**
     * True if has "createDate" element
     */
    boolean isSetCreateDate();

    /**
     * Sets the "createDate" element
     */
    void setCreateDate(java.util.Calendar createDate);

    /**
     * Sets (as xml) the "createDate" element
     */
    void xsetCreateDate(org.apache.xmlbeans.XmlDateTime createDate);

    /**
     * Nils the "createDate" element
     */
    void setNilCreateDate();

    /**
     * Unsets the "createDate" element
     */
    void unsetCreateDate();

    /**
     * Gets the "customcleared" element
     */
    int getCustomcleared();

    /**
     * Gets (as xml) the "customcleared" element
     */
    org.apache.xmlbeans.XmlInt xgetCustomcleared();

    /**
     * True if has "customcleared" element
     */
    boolean isSetCustomcleared();

    /**
     * Sets the "customcleared" element
     */
    void setCustomcleared(int customcleared);

    /**
     * Sets (as xml) the "customcleared" element
     */
    void xsetCustomcleared(org.apache.xmlbeans.XmlInt customcleared);

    /**
     * Unsets the "customcleared" element
     */
    void unsetCustomcleared();

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
     * Gets the "faultairline" element
     */
    java.lang.String getFaultairline();

    /**
     * Gets (as xml) the "faultairline" element
     */
    org.apache.xmlbeans.XmlString xgetFaultairline();

    /**
     * Tests for nil "faultairline" element
     */
    boolean isNilFaultairline();

    /**
     * True if has "faultairline" element
     */
    boolean isSetFaultairline();

    /**
     * Sets the "faultairline" element
     */
    void setFaultairline(java.lang.String faultairline);

    /**
     * Sets (as xml) the "faultairline" element
     */
    void xsetFaultairline(org.apache.xmlbeans.XmlString faultairline);

    /**
     * Nils the "faultairline" element
     */
    void setNilFaultairline();

    /**
     * Unsets the "faultairline" element
     */
    void unsetFaultairline();

    /**
     * Gets the "faultstation" element
     */
    java.lang.String getFaultstation();

    /**
     * Gets (as xml) the "faultstation" element
     */
    org.apache.xmlbeans.XmlString xgetFaultstation();

    /**
     * Tests for nil "faultstation" element
     */
    boolean isNilFaultstation();

    /**
     * True if has "faultstation" element
     */
    boolean isSetFaultstation();

    /**
     * Sets the "faultstation" element
     */
    void setFaultstation(java.lang.String faultstation);

    /**
     * Sets (as xml) the "faultstation" element
     */
    void xsetFaultstation(org.apache.xmlbeans.XmlString faultstation);

    /**
     * Nils the "faultstation" element
     */
    void setNilFaultstation();

    /**
     * Unsets the "faultstation" element
     */
    void unsetFaultstation();

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
     * Gets array of all "items" elements
     */
    com.bagnet.nettracer.ws.core.pojo.xsd.WSItem[] getItemsArray();

    /**
     * Gets ith "items" element
     */
    com.bagnet.nettracer.ws.core.pojo.xsd.WSItem getItemsArray(int i);

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
    void setItemsArray(
        com.bagnet.nettracer.ws.core.pojo.xsd.WSItem[] itemsArray);

    /**
     * Sets ith "items" element
     */
    void setItemsArray(int i, com.bagnet.nettracer.ws.core.pojo.xsd.WSItem items);

    /**
     * Nils the ith "items" element
     */
    void setNilItemsArray(int i);

    /**
     * Inserts and returns a new empty value (as xml) as the ith "items" element
     */
    com.bagnet.nettracer.ws.core.pojo.xsd.WSItem insertNewItems(int i);

    /**
     * Appends and returns a new empty value (as xml) as the last "items" element
     */
    com.bagnet.nettracer.ws.core.pojo.xsd.WSItem addNewItems();

    /**
     * Removes the ith "items" element
     */
    void removeItems(int i);

    /**
     * Gets the "itemtype" element
     */
    java.lang.String getItemtype();

    /**
     * Gets (as xml) the "itemtype" element
     */
    org.apache.xmlbeans.XmlString xgetItemtype();

    /**
     * Tests for nil "itemtype" element
     */
    boolean isNilItemtype();

    /**
     * True if has "itemtype" element
     */
    boolean isSetItemtype();

    /**
     * Sets the "itemtype" element
     */
    void setItemtype(java.lang.String itemtype);

    /**
     * Sets (as xml) the "itemtype" element
     */
    void xsetItemtype(org.apache.xmlbeans.XmlString itemtype);

    /**
     * Nils the "itemtype" element
     */
    void setNilItemtype();

    /**
     * Unsets the "itemtype" element
     */
    void unsetItemtype();

    /**
     * Gets array of all "itineraries" elements
     */
    com.bagnet.nettracer.ws.core.pojo.xsd.WSItinerary[] getItinerariesArray();

    /**
     * Gets ith "itineraries" element
     */
    com.bagnet.nettracer.ws.core.pojo.xsd.WSItinerary getItinerariesArray(int i);

    /**
     * Tests for nil ith "itineraries" element
     */
    boolean isNilItinerariesArray(int i);

    /**
     * Returns number of "itineraries" element
     */
    int sizeOfItinerariesArray();

    /**
     * Sets array of all "itineraries" element
     */
    void setItinerariesArray(
        com.bagnet.nettracer.ws.core.pojo.xsd.WSItinerary[] itinerariesArray);

    /**
     * Sets ith "itineraries" element
     */
    void setItinerariesArray(int i,
        com.bagnet.nettracer.ws.core.pojo.xsd.WSItinerary itineraries);

    /**
     * Nils the ith "itineraries" element
     */
    void setNilItinerariesArray(int i);

    /**
     * Inserts and returns a new empty value (as xml) as the ith "itineraries" element
     */
    com.bagnet.nettracer.ws.core.pojo.xsd.WSItinerary insertNewItineraries(
        int i);

    /**
     * Appends and returns a new empty value (as xml) as the last "itineraries" element
     */
    com.bagnet.nettracer.ws.core.pojo.xsd.WSItinerary addNewItineraries();

    /**
     * Removes the ith "itineraries" element
     */
    void removeItineraries(int i);

    /**
     * Gets the "loss_code" element
     */
    int getLossCode();

    /**
     * Gets (as xml) the "loss_code" element
     */
    org.apache.xmlbeans.XmlInt xgetLossCode();

    /**
     * True if has "loss_code" element
     */
    boolean isSetLossCode();

    /**
     * Sets the "loss_code" element
     */
    void setLossCode(int lossCode);

    /**
     * Sets (as xml) the "loss_code" element
     */
    void xsetLossCode(org.apache.xmlbeans.XmlInt lossCode);

    /**
     * Unsets the "loss_code" element
     */
    void unsetLossCode();

    /**
     * Gets the "nonrevenue" element
     */
    int getNonrevenue();

    /**
     * Gets (as xml) the "nonrevenue" element
     */
    org.apache.xmlbeans.XmlInt xgetNonrevenue();

    /**
     * True if has "nonrevenue" element
     */
    boolean isSetNonrevenue();

    /**
     * Sets the "nonrevenue" element
     */
    void setNonrevenue(int nonrevenue);

    /**
     * Sets (as xml) the "nonrevenue" element
     */
    void xsetNonrevenue(org.apache.xmlbeans.XmlInt nonrevenue);

    /**
     * Unsets the "nonrevenue" element
     */
    void unsetNonrevenue();

    /**
     * Gets the "numbagchecked" element
     */
    int getNumbagchecked();

    /**
     * Gets (as xml) the "numbagchecked" element
     */
    org.apache.xmlbeans.XmlInt xgetNumbagchecked();

    /**
     * True if has "numbagchecked" element
     */
    boolean isSetNumbagchecked();

    /**
     * Sets the "numbagchecked" element
     */
    void setNumbagchecked(int numbagchecked);

    /**
     * Sets (as xml) the "numbagchecked" element
     */
    void xsetNumbagchecked(org.apache.xmlbeans.XmlInt numbagchecked);

    /**
     * Unsets the "numbagchecked" element
     */
    void unsetNumbagchecked();

    /**
     * Gets the "numbagreceived" element
     */
    int getNumbagreceived();

    /**
     * Gets (as xml) the "numbagreceived" element
     */
    org.apache.xmlbeans.XmlInt xgetNumbagreceived();

    /**
     * True if has "numbagreceived" element
     */
    boolean isSetNumbagreceived();

    /**
     * Sets the "numbagreceived" element
     */
    void setNumbagreceived(int numbagreceived);

    /**
     * Sets (as xml) the "numbagreceived" element
     */
    void xsetNumbagreceived(org.apache.xmlbeans.XmlInt numbagreceived);

    /**
     * Unsets the "numbagreceived" element
     */
    void unsetNumbagreceived();

    /**
     * Gets the "numpassengers" element
     */
    int getNumpassengers();

    /**
     * Gets (as xml) the "numpassengers" element
     */
    org.apache.xmlbeans.XmlInt xgetNumpassengers();

    /**
     * True if has "numpassengers" element
     */
    boolean isSetNumpassengers();

    /**
     * Sets the "numpassengers" element
     */
    void setNumpassengers(int numpassengers);

    /**
     * Sets (as xml) the "numpassengers" element
     */
    void xsetNumpassengers(org.apache.xmlbeans.XmlInt numpassengers);

    /**
     * Unsets the "numpassengers" element
     */
    void unsetNumpassengers();

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
     * Gets array of all "passengers" elements
     */
    com.bagnet.nettracer.ws.core.pojo.xsd.WSPassenger[] getPassengersArray();

    /**
     * Gets ith "passengers" element
     */
    com.bagnet.nettracer.ws.core.pojo.xsd.WSPassenger getPassengersArray(int i);

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
    void setPassengersArray(
        com.bagnet.nettracer.ws.core.pojo.xsd.WSPassenger[] passengersArray);

    /**
     * Sets ith "passengers" element
     */
    void setPassengersArray(int i,
        com.bagnet.nettracer.ws.core.pojo.xsd.WSPassenger passengers);

    /**
     * Nils the ith "passengers" element
     */
    void setNilPassengersArray(int i);

    /**
     * Inserts and returns a new empty value (as xml) as the ith "passengers" element
     */
    com.bagnet.nettracer.ws.core.pojo.xsd.WSPassenger insertNewPassengers(int i);

    /**
     * Appends and returns a new empty value (as xml) as the last "passengers" element
     */
    com.bagnet.nettracer.ws.core.pojo.xsd.WSPassenger addNewPassengers();

    /**
     * Removes the ith "passengers" element
     */
    void removePassengers(int i);

    /**
     * Gets the "recordlocator" element
     */
    java.lang.String getRecordlocator();

    /**
     * Gets (as xml) the "recordlocator" element
     */
    org.apache.xmlbeans.XmlString xgetRecordlocator();

    /**
     * Tests for nil "recordlocator" element
     */
    boolean isNilRecordlocator();

    /**
     * True if has "recordlocator" element
     */
    boolean isSetRecordlocator();

    /**
     * Sets the "recordlocator" element
     */
    void setRecordlocator(java.lang.String recordlocator);

    /**
     * Sets (as xml) the "recordlocator" element
     */
    void xsetRecordlocator(org.apache.xmlbeans.XmlString recordlocator);

    /**
     * Nils the "recordlocator" element
     */
    void setNilRecordlocator();

    /**
     * Unsets the "recordlocator" element
     */
    void unsetRecordlocator();

    /**
     * Gets the "reportmethod" element
     */
    java.lang.String getReportmethod();

    /**
     * Gets (as xml) the "reportmethod" element
     */
    org.apache.xmlbeans.XmlString xgetReportmethod();

    /**
     * Tests for nil "reportmethod" element
     */
    boolean isNilReportmethod();

    /**
     * True if has "reportmethod" element
     */
    boolean isSetReportmethod();

    /**
     * Sets the "reportmethod" element
     */
    void setReportmethod(java.lang.String reportmethod);

    /**
     * Sets (as xml) the "reportmethod" element
     */
    void xsetReportmethod(org.apache.xmlbeans.XmlString reportmethod);

    /**
     * Nils the "reportmethod" element
     */
    void setNilReportmethod();

    /**
     * Unsets the "reportmethod" element
     */
    void unsetReportmethod();

    /**
     * Gets the "stationassigned" element
     */
    java.lang.String getStationassigned();

    /**
     * Gets (as xml) the "stationassigned" element
     */
    org.apache.xmlbeans.XmlString xgetStationassigned();

    /**
     * Tests for nil "stationassigned" element
     */
    boolean isNilStationassigned();

    /**
     * True if has "stationassigned" element
     */
    boolean isSetStationassigned();

    /**
     * Sets the "stationassigned" element
     */
    void setStationassigned(java.lang.String stationassigned);

    /**
     * Sets (as xml) the "stationassigned" element
     */
    void xsetStationassigned(org.apache.xmlbeans.XmlString stationassigned);

    /**
     * Nils the "stationassigned" element
     */
    void setNilStationassigned();

    /**
     * Unsets the "stationassigned" element
     */
    void unsetStationassigned();

    /**
     * Gets the "stationcreated" element
     */
    java.lang.String getStationcreated();

    /**
     * Gets (as xml) the "stationcreated" element
     */
    org.apache.xmlbeans.XmlString xgetStationcreated();

    /**
     * Tests for nil "stationcreated" element
     */
    boolean isNilStationcreated();

    /**
     * True if has "stationcreated" element
     */
    boolean isSetStationcreated();

    /**
     * Sets the "stationcreated" element
     */
    void setStationcreated(java.lang.String stationcreated);

    /**
     * Sets (as xml) the "stationcreated" element
     */
    void xsetStationcreated(org.apache.xmlbeans.XmlString stationcreated);

    /**
     * Nils the "stationcreated" element
     */
    void setNilStationcreated();

    /**
     * Unsets the "stationcreated" element
     */
    void unsetStationcreated();

    /**
     * Gets the "status" element
     */
    java.lang.String getStatus();

    /**
     * Gets (as xml) the "status" element
     */
    org.apache.xmlbeans.XmlString xgetStatus();

    /**
     * Tests for nil "status" element
     */
    boolean isNilStatus();

    /**
     * True if has "status" element
     */
    boolean isSetStatus();

    /**
     * Sets the "status" element
     */
    void setStatus(java.lang.String status);

    /**
     * Sets (as xml) the "status" element
     */
    void xsetStatus(org.apache.xmlbeans.XmlString status);

    /**
     * Nils the "status" element
     */
    void setNilStatus();

    /**
     * Unsets the "status" element
     */
    void unsetStatus();

    /**
     * Gets the "ticketnumber" element
     */
    java.lang.String getTicketnumber();

    /**
     * Gets (as xml) the "ticketnumber" element
     */
    org.apache.xmlbeans.XmlString xgetTicketnumber();

    /**
     * Tests for nil "ticketnumber" element
     */
    boolean isNilTicketnumber();

    /**
     * True if has "ticketnumber" element
     */
    boolean isSetTicketnumber();

    /**
     * Sets the "ticketnumber" element
     */
    void setTicketnumber(java.lang.String ticketnumber);

    /**
     * Sets (as xml) the "ticketnumber" element
     */
    void xsetTicketnumber(org.apache.xmlbeans.XmlString ticketnumber);

    /**
     * Nils the "ticketnumber" element
     */
    void setNilTicketnumber();

    /**
     * Unsets the "ticketnumber" element
     */
    void unsetTicketnumber();

    /**
     * Gets the "tsachecked" element
     */
    int getTsachecked();

    /**
     * Gets (as xml) the "tsachecked" element
     */
    org.apache.xmlbeans.XmlInt xgetTsachecked();

    /**
     * True if has "tsachecked" element
     */
    boolean isSetTsachecked();

    /**
     * Sets the "tsachecked" element
     */
    void setTsachecked(int tsachecked);

    /**
     * Sets (as xml) the "tsachecked" element
     */
    void xsetTsachecked(org.apache.xmlbeans.XmlInt tsachecked);

    /**
     * Unsets the "tsachecked" element
     */
    void unsetTsachecked();

    /**
     * Gets the "voluntaryseparation" element
     */
    int getVoluntaryseparation();

    /**
     * Gets (as xml) the "voluntaryseparation" element
     */
    org.apache.xmlbeans.XmlInt xgetVoluntaryseparation();

    /**
     * True if has "voluntaryseparation" element
     */
    boolean isSetVoluntaryseparation();

    /**
     * Sets the "voluntaryseparation" element
     */
    void setVoluntaryseparation(int voluntaryseparation);

    /**
     * Sets (as xml) the "voluntaryseparation" element
     */
    void xsetVoluntaryseparation(org.apache.xmlbeans.XmlInt voluntaryseparation);

    /**
     * Unsets the "voluntaryseparation" element
     */
    void unsetVoluntaryseparation();

    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    public static final class Factory {
        private Factory() {
        } // No instance of this class allowed

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident newInstance() {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                  .newInstance(type,
                null);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident newInstance(
            org.apache.xmlbeans.XmlOptions options) {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                  .newInstance(type,
                options);
        }

        /** @param xmlAsString the string value to parse */
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident parse(
            java.lang.String xmlAsString)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                  .parse(xmlAsString,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident parse(
            java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                  .parse(xmlAsString,
                type, options);
        }

        /** @param file the file from which to load an xml document */
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident parse(
            java.io.File file)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                  .parse(file,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident parse(
            java.io.File file, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                  .parse(file,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident parse(
            java.net.URL u)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                  .parse(u,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident parse(
            java.net.URL u, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                  .parse(u,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident parse(
            java.io.InputStream is)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                  .parse(is,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident parse(
            java.io.InputStream is, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                  .parse(is,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident parse(
            java.io.Reader r)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                  .parse(r,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident parse(
            java.io.Reader r, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                  .parse(r,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident parse(
            javax.xml.stream.XMLStreamReader sr)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                  .parse(sr,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident parse(
            javax.xml.stream.XMLStreamReader sr,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                  .parse(sr,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident parse(
            org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                  .parse(node,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident parse(
            org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                  .parse(node,
                type, options);
        }

        /** @deprecated {@link XMLInputStream} */
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident parse(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                  .parse(xis,
                type, null);
        }

        /** @deprecated {@link XMLInputStream} */
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident parse(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                  .parse(xis,
                type, options);
        }

        /** @deprecated {@link XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                               .newValidatingXMLInputStream(xis,
                type, null);
        }

        /** @deprecated {@link XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                               .newValidatingXMLInputStream(xis,
                type, options);
        }
    }
}
