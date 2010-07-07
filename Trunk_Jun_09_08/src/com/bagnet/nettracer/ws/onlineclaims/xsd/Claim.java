/*
 * XML Type:  Claim
 * Namespace: http://onlineclaims.ws.nettracer.bagnet.com/xsd
 * Java type: com.bagnet.nettracer.ws.onlineclaims.xsd.Claim
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.onlineclaims.xsd;


/**
 * An XML Claim(@http://onlineclaims.ws.nettracer.bagnet.com/xsd).
 *
 * This is a complex type.
 */
public interface Claim extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(Claim.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s16F40E8718DC66FAA429E7A8E1C40F42").resolveHandle("claimd979type");
    
    /**
     * Gets the "accept" element
     */
    java.lang.String getAccept();
    
    /**
     * Gets (as xml) the "accept" element
     */
    org.apache.xmlbeans.XmlString xgetAccept();
    
    /**
     * Tests for nil "accept" element
     */
    boolean isNilAccept();
    
    /**
     * True if has "accept" element
     */
    boolean isSetAccept();
    
    /**
     * Sets the "accept" element
     */
    void setAccept(java.lang.String accept);
    
    /**
     * Sets (as xml) the "accept" element
     */
    void xsetAccept(org.apache.xmlbeans.XmlString accept);
    
    /**
     * Nils the "accept" element
     */
    void setNilAccept();
    
    /**
     * Unsets the "accept" element
     */
    void unsetAccept();
    
    /**
     * Gets the "attemptToClaimAtArrival" element
     */
    boolean getAttemptToClaimAtArrival();
    
    /**
     * Gets (as xml) the "attemptToClaimAtArrival" element
     */
    org.apache.xmlbeans.XmlBoolean xgetAttemptToClaimAtArrival();
    
    /**
     * True if has "attemptToClaimAtArrival" element
     */
    boolean isSetAttemptToClaimAtArrival();
    
    /**
     * Sets the "attemptToClaimAtArrival" element
     */
    void setAttemptToClaimAtArrival(boolean attemptToClaimAtArrival);
    
    /**
     * Sets (as xml) the "attemptToClaimAtArrival" element
     */
    void xsetAttemptToClaimAtArrival(org.apache.xmlbeans.XmlBoolean attemptToClaimAtArrival);
    
    /**
     * Unsets the "attemptToClaimAtArrival" element
     */
    void unsetAttemptToClaimAtArrival();
    
    /**
     * Gets array of all "bag" elements
     */
    com.bagnet.nettracer.ws.onlineclaims.xsd.Bag[] getBagArray();
    
    /**
     * Gets ith "bag" element
     */
    com.bagnet.nettracer.ws.onlineclaims.xsd.Bag getBagArray(int i);
    
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
    void setBagArray(com.bagnet.nettracer.ws.onlineclaims.xsd.Bag[] bagArray);
    
    /**
     * Sets ith "bag" element
     */
    void setBagArray(int i, com.bagnet.nettracer.ws.onlineclaims.xsd.Bag bag);
    
    /**
     * Nils the ith "bag" element
     */
    void setNilBagArray(int i);
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "bag" element
     */
    com.bagnet.nettracer.ws.onlineclaims.xsd.Bag insertNewBag(int i);
    
    /**
     * Appends and returns a new empty value (as xml) as the last "bag" element
     */
    com.bagnet.nettracer.ws.onlineclaims.xsd.Bag addNewBag();
    
    /**
     * Removes the ith "bag" element
     */
    void removeBag(int i);
    
    /**
     * Gets the "bagClearCustoms" element
     */
    boolean getBagClearCustoms();
    
    /**
     * Gets (as xml) the "bagClearCustoms" element
     */
    org.apache.xmlbeans.XmlBoolean xgetBagClearCustoms();
    
    /**
     * True if has "bagClearCustoms" element
     */
    boolean isSetBagClearCustoms();
    
    /**
     * Sets the "bagClearCustoms" element
     */
    void setBagClearCustoms(boolean bagClearCustoms);
    
    /**
     * Sets (as xml) the "bagClearCustoms" element
     */
    void xsetBagClearCustoms(org.apache.xmlbeans.XmlBoolean bagClearCustoms);
    
    /**
     * Unsets the "bagClearCustoms" element
     */
    void unsetBagClearCustoms();
    
    /**
     * Gets the "baggageReroutedEnRoute" element
     */
    boolean getBaggageReroutedEnRoute();
    
    /**
     * Gets (as xml) the "baggageReroutedEnRoute" element
     */
    org.apache.xmlbeans.XmlBoolean xgetBaggageReroutedEnRoute();
    
    /**
     * True if has "baggageReroutedEnRoute" element
     */
    boolean isSetBaggageReroutedEnRoute();
    
    /**
     * Sets the "baggageReroutedEnRoute" element
     */
    void setBaggageReroutedEnRoute(boolean baggageReroutedEnRoute);
    
    /**
     * Sets (as xml) the "baggageReroutedEnRoute" element
     */
    void xsetBaggageReroutedEnRoute(org.apache.xmlbeans.XmlBoolean baggageReroutedEnRoute);
    
    /**
     * Unsets the "baggageReroutedEnRoute" element
     */
    void unsetBaggageReroutedEnRoute();
    
    /**
     * Gets the "bagsDelayed" element
     */
    int getBagsDelayed();
    
    /**
     * Gets (as xml) the "bagsDelayed" element
     */
    org.apache.xmlbeans.XmlInt xgetBagsDelayed();
    
    /**
     * True if has "bagsDelayed" element
     */
    boolean isSetBagsDelayed();
    
    /**
     * Sets the "bagsDelayed" element
     */
    void setBagsDelayed(int bagsDelayed);
    
    /**
     * Sets (as xml) the "bagsDelayed" element
     */
    void xsetBagsDelayed(org.apache.xmlbeans.XmlInt bagsDelayed);
    
    /**
     * Unsets the "bagsDelayed" element
     */
    void unsetBagsDelayed();
    
    /**
     * Gets the "bagsStillMissing" element
     */
    int getBagsStillMissing();
    
    /**
     * Gets (as xml) the "bagsStillMissing" element
     */
    org.apache.xmlbeans.XmlInt xgetBagsStillMissing();
    
    /**
     * True if has "bagsStillMissing" element
     */
    boolean isSetBagsStillMissing();
    
    /**
     * Sets the "bagsStillMissing" element
     */
    void setBagsStillMissing(int bagsStillMissing);
    
    /**
     * Sets (as xml) the "bagsStillMissing" element
     */
    void xsetBagsStillMissing(org.apache.xmlbeans.XmlInt bagsStillMissing);
    
    /**
     * Unsets the "bagsStillMissing" element
     */
    void unsetBagsStillMissing();
    
    /**
     * Gets the "bagsTravelWith" element
     */
    int getBagsTravelWith();
    
    /**
     * Gets (as xml) the "bagsTravelWith" element
     */
    org.apache.xmlbeans.XmlInt xgetBagsTravelWith();
    
    /**
     * True if has "bagsTravelWith" element
     */
    boolean isSetBagsTravelWith();
    
    /**
     * Sets the "bagsTravelWith" element
     */
    void setBagsTravelWith(int bagsTravelWith);
    
    /**
     * Sets (as xml) the "bagsTravelWith" element
     */
    void xsetBagsTravelWith(org.apache.xmlbeans.XmlInt bagsTravelWith);
    
    /**
     * Unsets the "bagsTravelWith" element
     */
    void unsetBagsTravelWith();
    
    /**
     * Gets the "businessName" element
     */
    java.lang.String getBusinessName();
    
    /**
     * Gets (as xml) the "businessName" element
     */
    org.apache.xmlbeans.XmlString xgetBusinessName();
    
    /**
     * Tests for nil "businessName" element
     */
    boolean isNilBusinessName();
    
    /**
     * True if has "businessName" element
     */
    boolean isSetBusinessName();
    
    /**
     * Sets the "businessName" element
     */
    void setBusinessName(java.lang.String businessName);
    
    /**
     * Sets (as xml) the "businessName" element
     */
    void xsetBusinessName(org.apache.xmlbeans.XmlString businessName);
    
    /**
     * Nils the "businessName" element
     */
    void setNilBusinessName();
    
    /**
     * Unsets the "businessName" element
     */
    void unsetBusinessName();
    
    /**
     * Gets the "chargedForExcessBaggage" element
     */
    boolean getChargedForExcessBaggage();
    
    /**
     * Gets (as xml) the "chargedForExcessBaggage" element
     */
    org.apache.xmlbeans.XmlBoolean xgetChargedForExcessBaggage();
    
    /**
     * True if has "chargedForExcessBaggage" element
     */
    boolean isSetChargedForExcessBaggage();
    
    /**
     * Sets the "chargedForExcessBaggage" element
     */
    void setChargedForExcessBaggage(boolean chargedForExcessBaggage);
    
    /**
     * Sets (as xml) the "chargedForExcessBaggage" element
     */
    void xsetChargedForExcessBaggage(org.apache.xmlbeans.XmlBoolean chargedForExcessBaggage);
    
    /**
     * Unsets the "chargedForExcessBaggage" element
     */
    void unsetChargedForExcessBaggage();
    
    /**
     * Gets the "checkedLocation" element
     */
    java.lang.String getCheckedLocation();
    
    /**
     * Gets (as xml) the "checkedLocation" element
     */
    org.apache.xmlbeans.XmlString xgetCheckedLocation();
    
    /**
     * Tests for nil "checkedLocation" element
     */
    boolean isNilCheckedLocation();
    
    /**
     * True if has "checkedLocation" element
     */
    boolean isSetCheckedLocation();
    
    /**
     * Sets the "checkedLocation" element
     */
    void setCheckedLocation(java.lang.String checkedLocation);
    
    /**
     * Sets (as xml) the "checkedLocation" element
     */
    void xsetCheckedLocation(org.apache.xmlbeans.XmlString checkedLocation);
    
    /**
     * Nils the "checkedLocation" element
     */
    void setNilCheckedLocation();
    
    /**
     * Unsets the "checkedLocation" element
     */
    void unsetCheckedLocation();
    
    /**
     * Gets the "claimId" element
     */
    long getClaimId();
    
    /**
     * Gets (as xml) the "claimId" element
     */
    org.apache.xmlbeans.XmlLong xgetClaimId();
    
    /**
     * True if has "claimId" element
     */
    boolean isSetClaimId();
    
    /**
     * Sets the "claimId" element
     */
    void setClaimId(long claimId);
    
    /**
     * Sets (as xml) the "claimId" element
     */
    void xsetClaimId(org.apache.xmlbeans.XmlLong claimId);
    
    /**
     * Unsets the "claimId" element
     */
    void unsetClaimId();
    
    /**
     * Gets the "claimType" element
     */
    int getClaimType();
    
    /**
     * Gets (as xml) the "claimType" element
     */
    org.apache.xmlbeans.XmlInt xgetClaimType();
    
    /**
     * True if has "claimType" element
     */
    boolean isSetClaimType();
    
    /**
     * Sets the "claimType" element
     */
    void setClaimType(int claimType);
    
    /**
     * Sets (as xml) the "claimType" element
     */
    void xsetClaimType(org.apache.xmlbeans.XmlInt claimType);
    
    /**
     * Unsets the "claimType" element
     */
    void unsetClaimType();
    
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
     * Gets the "declaredExcessValue" element
     */
    boolean getDeclaredExcessValue();
    
    /**
     * Gets (as xml) the "declaredExcessValue" element
     */
    org.apache.xmlbeans.XmlBoolean xgetDeclaredExcessValue();
    
    /**
     * True if has "declaredExcessValue" element
     */
    boolean isSetDeclaredExcessValue();
    
    /**
     * Sets the "declaredExcessValue" element
     */
    void setDeclaredExcessValue(boolean declaredExcessValue);
    
    /**
     * Sets (as xml) the "declaredExcessValue" element
     */
    void xsetDeclaredExcessValue(org.apache.xmlbeans.XmlBoolean declaredExcessValue);
    
    /**
     * Unsets the "declaredExcessValue" element
     */
    void unsetDeclaredExcessValue();
    
    /**
     * Gets the "declaredValue" element
     */
    java.lang.String getDeclaredValue();
    
    /**
     * Gets (as xml) the "declaredValue" element
     */
    org.apache.xmlbeans.XmlString xgetDeclaredValue();
    
    /**
     * Tests for nil "declaredValue" element
     */
    boolean isNilDeclaredValue();
    
    /**
     * True if has "declaredValue" element
     */
    boolean isSetDeclaredValue();
    
    /**
     * Sets the "declaredValue" element
     */
    void setDeclaredValue(java.lang.String declaredValue);
    
    /**
     * Sets (as xml) the "declaredValue" element
     */
    void xsetDeclaredValue(org.apache.xmlbeans.XmlString declaredValue);
    
    /**
     * Nils the "declaredValue" element
     */
    void setNilDeclaredValue();
    
    /**
     * Unsets the "declaredValue" element
     */
    void unsetDeclaredValue();
    
    /**
     * Gets the "differentClaimCheck" element
     */
    boolean getDifferentClaimCheck();
    
    /**
     * Gets (as xml) the "differentClaimCheck" element
     */
    org.apache.xmlbeans.XmlBoolean xgetDifferentClaimCheck();
    
    /**
     * True if has "differentClaimCheck" element
     */
    boolean isSetDifferentClaimCheck();
    
    /**
     * Sets the "differentClaimCheck" element
     */
    void setDifferentClaimCheck(boolean differentClaimCheck);
    
    /**
     * Sets (as xml) the "differentClaimCheck" element
     */
    void xsetDifferentClaimCheck(org.apache.xmlbeans.XmlBoolean differentClaimCheck);
    
    /**
     * Unsets the "differentClaimCheck" element
     */
    void unsetDifferentClaimCheck();
    
    /**
     * Gets the "emailAddress" element
     */
    java.lang.String getEmailAddress();
    
    /**
     * Gets (as xml) the "emailAddress" element
     */
    org.apache.xmlbeans.XmlString xgetEmailAddress();
    
    /**
     * Tests for nil "emailAddress" element
     */
    boolean isNilEmailAddress();
    
    /**
     * True if has "emailAddress" element
     */
    boolean isSetEmailAddress();
    
    /**
     * Sets the "emailAddress" element
     */
    void setEmailAddress(java.lang.String emailAddress);
    
    /**
     * Sets (as xml) the "emailAddress" element
     */
    void xsetEmailAddress(org.apache.xmlbeans.XmlString emailAddress);
    
    /**
     * Nils the "emailAddress" element
     */
    void setNilEmailAddress();
    
    /**
     * Unsets the "emailAddress" element
     */
    void unsetEmailAddress();
    
    /**
     * Gets array of all "file" elements
     */
    com.bagnet.nettracer.ws.onlineclaims.xsd.File[] getFileArray();
    
    /**
     * Gets ith "file" element
     */
    com.bagnet.nettracer.ws.onlineclaims.xsd.File getFileArray(int i);
    
    /**
     * Tests for nil ith "file" element
     */
    boolean isNilFileArray(int i);
    
    /**
     * Returns number of "file" element
     */
    int sizeOfFileArray();
    
    /**
     * Sets array of all "file" element
     */
    void setFileArray(com.bagnet.nettracer.ws.onlineclaims.xsd.File[] fileArray);
    
    /**
     * Sets ith "file" element
     */
    void setFileArray(int i, com.bagnet.nettracer.ws.onlineclaims.xsd.File file);
    
    /**
     * Nils the ith "file" element
     */
    void setNilFileArray(int i);
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "file" element
     */
    com.bagnet.nettracer.ws.onlineclaims.xsd.File insertNewFile(int i);
    
    /**
     * Appends and returns a new empty value (as xml) as the last "file" element
     */
    com.bagnet.nettracer.ws.onlineclaims.xsd.File addNewFile();
    
    /**
     * Removes the ith "file" element
     */
    void removeFile(int i);
    
    /**
     * Gets the "filedPreviousAirline" element
     */
    java.lang.String getFiledPreviousAirline();
    
    /**
     * Gets (as xml) the "filedPreviousAirline" element
     */
    org.apache.xmlbeans.XmlString xgetFiledPreviousAirline();
    
    /**
     * Tests for nil "filedPreviousAirline" element
     */
    boolean isNilFiledPreviousAirline();
    
    /**
     * True if has "filedPreviousAirline" element
     */
    boolean isSetFiledPreviousAirline();
    
    /**
     * Sets the "filedPreviousAirline" element
     */
    void setFiledPreviousAirline(java.lang.String filedPreviousAirline);
    
    /**
     * Sets (as xml) the "filedPreviousAirline" element
     */
    void xsetFiledPreviousAirline(org.apache.xmlbeans.XmlString filedPreviousAirline);
    
    /**
     * Nils the "filedPreviousAirline" element
     */
    void setNilFiledPreviousAirline();
    
    /**
     * Unsets the "filedPreviousAirline" element
     */
    void unsetFiledPreviousAirline();
    
    /**
     * Gets the "filedPreviousClaim" element
     */
    boolean getFiledPreviousClaim();
    
    /**
     * Gets (as xml) the "filedPreviousClaim" element
     */
    org.apache.xmlbeans.XmlBoolean xgetFiledPreviousClaim();
    
    /**
     * True if has "filedPreviousClaim" element
     */
    boolean isSetFiledPreviousClaim();
    
    /**
     * Sets the "filedPreviousClaim" element
     */
    void setFiledPreviousClaim(boolean filedPreviousClaim);
    
    /**
     * Sets (as xml) the "filedPreviousClaim" element
     */
    void xsetFiledPreviousClaim(org.apache.xmlbeans.XmlBoolean filedPreviousClaim);
    
    /**
     * Unsets the "filedPreviousClaim" element
     */
    void unsetFiledPreviousClaim();
    
    /**
     * Gets the "filedPreviousClaimant" element
     */
    java.lang.String getFiledPreviousClaimant();
    
    /**
     * Gets (as xml) the "filedPreviousClaimant" element
     */
    org.apache.xmlbeans.XmlString xgetFiledPreviousClaimant();
    
    /**
     * Tests for nil "filedPreviousClaimant" element
     */
    boolean isNilFiledPreviousClaimant();
    
    /**
     * True if has "filedPreviousClaimant" element
     */
    boolean isSetFiledPreviousClaimant();
    
    /**
     * Sets the "filedPreviousClaimant" element
     */
    void setFiledPreviousClaimant(java.lang.String filedPreviousClaimant);
    
    /**
     * Sets (as xml) the "filedPreviousClaimant" element
     */
    void xsetFiledPreviousClaimant(org.apache.xmlbeans.XmlString filedPreviousClaimant);
    
    /**
     * Nils the "filedPreviousClaimant" element
     */
    void setNilFiledPreviousClaimant();
    
    /**
     * Unsets the "filedPreviousClaimant" element
     */
    void unsetFiledPreviousClaimant();
    
    /**
     * Gets the "filedPrevoiusDate" element
     */
    java.util.Calendar getFiledPrevoiusDate();
    
    /**
     * Gets (as xml) the "filedPrevoiusDate" element
     */
    org.apache.xmlbeans.XmlDate xgetFiledPrevoiusDate();
    
    /**
     * Tests for nil "filedPrevoiusDate" element
     */
    boolean isNilFiledPrevoiusDate();
    
    /**
     * True if has "filedPrevoiusDate" element
     */
    boolean isSetFiledPrevoiusDate();
    
    /**
     * Sets the "filedPrevoiusDate" element
     */
    void setFiledPrevoiusDate(java.util.Calendar filedPrevoiusDate);
    
    /**
     * Sets (as xml) the "filedPrevoiusDate" element
     */
    void xsetFiledPrevoiusDate(org.apache.xmlbeans.XmlDate filedPrevoiusDate);
    
    /**
     * Nils the "filedPrevoiusDate" element
     */
    void setNilFiledPrevoiusDate();
    
    /**
     * Unsets the "filedPrevoiusDate" element
     */
    void unsetFiledPrevoiusDate();
    
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
     * Gets the "frequentFlierNumber" element
     */
    java.lang.String getFrequentFlierNumber();
    
    /**
     * Gets (as xml) the "frequentFlierNumber" element
     */
    org.apache.xmlbeans.XmlString xgetFrequentFlierNumber();
    
    /**
     * Tests for nil "frequentFlierNumber" element
     */
    boolean isNilFrequentFlierNumber();
    
    /**
     * True if has "frequentFlierNumber" element
     */
    boolean isSetFrequentFlierNumber();
    
    /**
     * Sets the "frequentFlierNumber" element
     */
    void setFrequentFlierNumber(java.lang.String frequentFlierNumber);
    
    /**
     * Sets (as xml) the "frequentFlierNumber" element
     */
    void xsetFrequentFlierNumber(org.apache.xmlbeans.XmlString frequentFlierNumber);
    
    /**
     * Nils the "frequentFlierNumber" element
     */
    void setNilFrequentFlierNumber();
    
    /**
     * Unsets the "frequentFlierNumber" element
     */
    void unsetFrequentFlierNumber();
    
    /**
     * Gets the "haveToRecheck" element
     */
    boolean getHaveToRecheck();
    
    /**
     * Gets (as xml) the "haveToRecheck" element
     */
    org.apache.xmlbeans.XmlBoolean xgetHaveToRecheck();
    
    /**
     * True if has "haveToRecheck" element
     */
    boolean isSetHaveToRecheck();
    
    /**
     * Sets the "haveToRecheck" element
     */
    void setHaveToRecheck(boolean haveToRecheck);
    
    /**
     * Sets (as xml) the "haveToRecheck" element
     */
    void xsetHaveToRecheck(org.apache.xmlbeans.XmlBoolean haveToRecheck);
    
    /**
     * Unsets the "haveToRecheck" element
     */
    void unsetHaveToRecheck();
    
    /**
     * Gets array of all "itinerary" elements
     */
    com.bagnet.nettracer.ws.onlineclaims.xsd.Itinerary[] getItineraryArray();
    
    /**
     * Gets ith "itinerary" element
     */
    com.bagnet.nettracer.ws.onlineclaims.xsd.Itinerary getItineraryArray(int i);
    
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
    void setItineraryArray(com.bagnet.nettracer.ws.onlineclaims.xsd.Itinerary[] itineraryArray);
    
    /**
     * Sets ith "itinerary" element
     */
    void setItineraryArray(int i, com.bagnet.nettracer.ws.onlineclaims.xsd.Itinerary itinerary);
    
    /**
     * Nils the ith "itinerary" element
     */
    void setNilItineraryArray(int i);
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "itinerary" element
     */
    com.bagnet.nettracer.ws.onlineclaims.xsd.Itinerary insertNewItinerary(int i);
    
    /**
     * Appends and returns a new empty value (as xml) as the last "itinerary" element
     */
    com.bagnet.nettracer.ws.onlineclaims.xsd.Itinerary addNewItinerary();
    
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
     * Gets the "lastSawBaggage" element
     */
    java.lang.String getLastSawBaggage();
    
    /**
     * Gets (as xml) the "lastSawBaggage" element
     */
    org.apache.xmlbeans.XmlString xgetLastSawBaggage();
    
    /**
     * Tests for nil "lastSawBaggage" element
     */
    boolean isNilLastSawBaggage();
    
    /**
     * True if has "lastSawBaggage" element
     */
    boolean isSetLastSawBaggage();
    
    /**
     * Sets the "lastSawBaggage" element
     */
    void setLastSawBaggage(java.lang.String lastSawBaggage);
    
    /**
     * Sets (as xml) the "lastSawBaggage" element
     */
    void xsetLastSawBaggage(org.apache.xmlbeans.XmlString lastSawBaggage);
    
    /**
     * Nils the "lastSawBaggage" element
     */
    void setNilLastSawBaggage();
    
    /**
     * Unsets the "lastSawBaggage" element
     */
    void unsetLastSawBaggage();
    
    /**
     * Gets the "mailingAddress" element
     */
    com.bagnet.nettracer.ws.onlineclaims.xsd.Address getMailingAddress();
    
    /**
     * Tests for nil "mailingAddress" element
     */
    boolean isNilMailingAddress();
    
    /**
     * True if has "mailingAddress" element
     */
    boolean isSetMailingAddress();
    
    /**
     * Sets the "mailingAddress" element
     */
    void setMailingAddress(com.bagnet.nettracer.ws.onlineclaims.xsd.Address mailingAddress);
    
    /**
     * Appends and returns a new empty "mailingAddress" element
     */
    com.bagnet.nettracer.ws.onlineclaims.xsd.Address addNewMailingAddress();
    
    /**
     * Nils the "mailingAddress" element
     */
    void setNilMailingAddress();
    
    /**
     * Unsets the "mailingAddress" element
     */
    void unsetMailingAddress();
    
    /**
     * Gets the "middleInitial" element
     */
    java.lang.String getMiddleInitial();
    
    /**
     * Gets (as xml) the "middleInitial" element
     */
    org.apache.xmlbeans.XmlString xgetMiddleInitial();
    
    /**
     * Tests for nil "middleInitial" element
     */
    boolean isNilMiddleInitial();
    
    /**
     * True if has "middleInitial" element
     */
    boolean isSetMiddleInitial();
    
    /**
     * Sets the "middleInitial" element
     */
    void setMiddleInitial(java.lang.String middleInitial);
    
    /**
     * Sets (as xml) the "middleInitial" element
     */
    void xsetMiddleInitial(org.apache.xmlbeans.XmlString middleInitial);
    
    /**
     * Nils the "middleInitial" element
     */
    void setNilMiddleInitial();
    
    /**
     * Unsets the "middleInitial" element
     */
    void unsetMiddleInitial();
    
    /**
     * Gets the "occupation" element
     */
    java.lang.String getOccupation();
    
    /**
     * Gets (as xml) the "occupation" element
     */
    org.apache.xmlbeans.XmlString xgetOccupation();
    
    /**
     * Tests for nil "occupation" element
     */
    boolean isNilOccupation();
    
    /**
     * True if has "occupation" element
     */
    boolean isSetOccupation();
    
    /**
     * Sets the "occupation" element
     */
    void setOccupation(java.lang.String occupation);
    
    /**
     * Sets (as xml) the "occupation" element
     */
    void xsetOccupation(org.apache.xmlbeans.XmlString occupation);
    
    /**
     * Nils the "occupation" element
     */
    void setNilOccupation();
    
    /**
     * Unsets the "occupation" element
     */
    void unsetOccupation();
    
    /**
     * Gets the "passengersTravelingWithYou" element
     */
    int getPassengersTravelingWithYou();
    
    /**
     * Gets (as xml) the "passengersTravelingWithYou" element
     */
    org.apache.xmlbeans.XmlInt xgetPassengersTravelingWithYou();
    
    /**
     * True if has "passengersTravelingWithYou" element
     */
    boolean isSetPassengersTravelingWithYou();
    
    /**
     * Sets the "passengersTravelingWithYou" element
     */
    void setPassengersTravelingWithYou(int passengersTravelingWithYou);
    
    /**
     * Sets (as xml) the "passengersTravelingWithYou" element
     */
    void xsetPassengersTravelingWithYou(org.apache.xmlbeans.XmlInt passengersTravelingWithYou);
    
    /**
     * Unsets the "passengersTravelingWithYou" element
     */
    void unsetPassengersTravelingWithYou();
    
    /**
     * Gets the "permanentAddress" element
     */
    com.bagnet.nettracer.ws.onlineclaims.xsd.Address getPermanentAddress();
    
    /**
     * Tests for nil "permanentAddress" element
     */
    boolean isNilPermanentAddress();
    
    /**
     * True if has "permanentAddress" element
     */
    boolean isSetPermanentAddress();
    
    /**
     * Sets the "permanentAddress" element
     */
    void setPermanentAddress(com.bagnet.nettracer.ws.onlineclaims.xsd.Address permanentAddress);
    
    /**
     * Appends and returns a new empty "permanentAddress" element
     */
    com.bagnet.nettracer.ws.onlineclaims.xsd.Address addNewPermanentAddress();
    
    /**
     * Nils the "permanentAddress" element
     */
    void setNilPermanentAddress();
    
    /**
     * Unsets the "permanentAddress" element
     */
    void unsetPermanentAddress();
    
    /**
     * Gets array of all "phone" elements
     */
    com.bagnet.nettracer.ws.onlineclaims.xsd.Phone[] getPhoneArray();
    
    /**
     * Gets ith "phone" element
     */
    com.bagnet.nettracer.ws.onlineclaims.xsd.Phone getPhoneArray(int i);
    
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
    void setPhoneArray(com.bagnet.nettracer.ws.onlineclaims.xsd.Phone[] phoneArray);
    
    /**
     * Sets ith "phone" element
     */
    void setPhoneArray(int i, com.bagnet.nettracer.ws.onlineclaims.xsd.Phone phone);
    
    /**
     * Nils the ith "phone" element
     */
    void setNilPhoneArray(int i);
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "phone" element
     */
    com.bagnet.nettracer.ws.onlineclaims.xsd.Phone insertNewPhone(int i);
    
    /**
     * Appends and returns a new empty value (as xml) as the last "phone" element
     */
    com.bagnet.nettracer.ws.onlineclaims.xsd.Phone addNewPhone();
    
    /**
     * Removes the ith "phone" element
     */
    void removePhone(int i);
    
    /**
     * Gets the "reportedToAnotherAirline" element
     */
    boolean getReportedToAnotherAirline();
    
    /**
     * Gets (as xml) the "reportedToAnotherAirline" element
     */
    org.apache.xmlbeans.XmlBoolean xgetReportedToAnotherAirline();
    
    /**
     * True if has "reportedToAnotherAirline" element
     */
    boolean isSetReportedToAnotherAirline();
    
    /**
     * Sets the "reportedToAnotherAirline" element
     */
    void setReportedToAnotherAirline(boolean reportedToAnotherAirline);
    
    /**
     * Sets (as xml) the "reportedToAnotherAirline" element
     */
    void xsetReportedToAnotherAirline(org.apache.xmlbeans.XmlBoolean reportedToAnotherAirline);
    
    /**
     * Unsets the "reportedToAnotherAirline" element
     */
    void unsetReportedToAnotherAirline();
    
    /**
     * Gets the "reroutedAirlineCity" element
     */
    java.lang.String getReroutedAirlineCity();
    
    /**
     * Gets (as xml) the "reroutedAirlineCity" element
     */
    org.apache.xmlbeans.XmlString xgetReroutedAirlineCity();
    
    /**
     * Tests for nil "reroutedAirlineCity" element
     */
    boolean isNilReroutedAirlineCity();
    
    /**
     * True if has "reroutedAirlineCity" element
     */
    boolean isSetReroutedAirlineCity();
    
    /**
     * Sets the "reroutedAirlineCity" element
     */
    void setReroutedAirlineCity(java.lang.String reroutedAirlineCity);
    
    /**
     * Sets (as xml) the "reroutedAirlineCity" element
     */
    void xsetReroutedAirlineCity(org.apache.xmlbeans.XmlString reroutedAirlineCity);
    
    /**
     * Nils the "reroutedAirlineCity" element
     */
    void setNilReroutedAirlineCity();
    
    /**
     * Unsets the "reroutedAirlineCity" element
     */
    void unsetReroutedAirlineCity();
    
    /**
     * Gets the "reroutedReason" element
     */
    java.lang.String getReroutedReason();
    
    /**
     * Gets (as xml) the "reroutedReason" element
     */
    org.apache.xmlbeans.XmlString xgetReroutedReason();
    
    /**
     * Tests for nil "reroutedReason" element
     */
    boolean isNilReroutedReason();
    
    /**
     * True if has "reroutedReason" element
     */
    boolean isSetReroutedReason();
    
    /**
     * Sets the "reroutedReason" element
     */
    void setReroutedReason(java.lang.String reroutedReason);
    
    /**
     * Sets (as xml) the "reroutedReason" element
     */
    void xsetReroutedReason(org.apache.xmlbeans.XmlString reroutedReason);
    
    /**
     * Nils the "reroutedReason" element
     */
    void setNilReroutedReason();
    
    /**
     * Unsets the "reroutedReason" element
     */
    void unsetReroutedReason();
    
    /**
     * Gets the "socialSecurity" element
     */
    java.lang.String getSocialSecurity();
    
    /**
     * Gets (as xml) the "socialSecurity" element
     */
    org.apache.xmlbeans.XmlString xgetSocialSecurity();
    
    /**
     * Tests for nil "socialSecurity" element
     */
    boolean isNilSocialSecurity();
    
    /**
     * True if has "socialSecurity" element
     */
    boolean isSetSocialSecurity();
    
    /**
     * Sets the "socialSecurity" element
     */
    void setSocialSecurity(java.lang.String socialSecurity);
    
    /**
     * Sets (as xml) the "socialSecurity" element
     */
    void xsetSocialSecurity(org.apache.xmlbeans.XmlString socialSecurity);
    
    /**
     * Nils the "socialSecurity" element
     */
    void setNilSocialSecurity();
    
    /**
     * Unsets the "socialSecurity" element
     */
    void unsetSocialSecurity();
    
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
     * Gets the "ticketWithAnotherAirline" element
     */
    boolean getTicketWithAnotherAirline();
    
    /**
     * Gets (as xml) the "ticketWithAnotherAirline" element
     */
    org.apache.xmlbeans.XmlBoolean xgetTicketWithAnotherAirline();
    
    /**
     * True if has "ticketWithAnotherAirline" element
     */
    boolean isSetTicketWithAnotherAirline();
    
    /**
     * Sets the "ticketWithAnotherAirline" element
     */
    void setTicketWithAnotherAirline(boolean ticketWithAnotherAirline);
    
    /**
     * Sets (as xml) the "ticketWithAnotherAirline" element
     */
    void xsetTicketWithAnotherAirline(org.apache.xmlbeans.XmlBoolean ticketWithAnotherAirline);
    
    /**
     * Unsets the "ticketWithAnotherAirline" element
     */
    void unsetTicketWithAnotherAirline();
    
    /**
     * Gets the "tsaInspected" element
     */
    boolean getTsaInspected();
    
    /**
     * Gets (as xml) the "tsaInspected" element
     */
    org.apache.xmlbeans.XmlBoolean xgetTsaInspected();
    
    /**
     * True if has "tsaInspected" element
     */
    boolean isSetTsaInspected();
    
    /**
     * Sets the "tsaInspected" element
     */
    void setTsaInspected(boolean tsaInspected);
    
    /**
     * Sets (as xml) the "tsaInspected" element
     */
    void xsetTsaInspected(org.apache.xmlbeans.XmlBoolean tsaInspected);
    
    /**
     * Unsets the "tsaInspected" element
     */
    void unsetTsaInspected();
    
    /**
     * Gets the "tsaInspectionLocation" element
     */
    java.lang.String getTsaInspectionLocation();
    
    /**
     * Gets (as xml) the "tsaInspectionLocation" element
     */
    org.apache.xmlbeans.XmlString xgetTsaInspectionLocation();
    
    /**
     * Tests for nil "tsaInspectionLocation" element
     */
    boolean isNilTsaInspectionLocation();
    
    /**
     * True if has "tsaInspectionLocation" element
     */
    boolean isSetTsaInspectionLocation();
    
    /**
     * Sets the "tsaInspectionLocation" element
     */
    void setTsaInspectionLocation(java.lang.String tsaInspectionLocation);
    
    /**
     * Sets (as xml) the "tsaInspectionLocation" element
     */
    void xsetTsaInspectionLocation(org.apache.xmlbeans.XmlString tsaInspectionLocation);
    
    /**
     * Nils the "tsaInspectionLocation" element
     */
    void setNilTsaInspectionLocation();
    
    /**
     * Unsets the "tsaInspectionLocation" element
     */
    void unsetTsaInspectionLocation();
    
    /**
     * Gets the "tsaNotePresent" element
     */
    boolean getTsaNotePresent();
    
    /**
     * Gets (as xml) the "tsaNotePresent" element
     */
    org.apache.xmlbeans.XmlBoolean xgetTsaNotePresent();
    
    /**
     * True if has "tsaNotePresent" element
     */
    boolean isSetTsaNotePresent();
    
    /**
     * Sets the "tsaNotePresent" element
     */
    void setTsaNotePresent(boolean tsaNotePresent);
    
    /**
     * Sets (as xml) the "tsaNotePresent" element
     */
    void xsetTsaNotePresent(org.apache.xmlbeans.XmlBoolean tsaNotePresent);
    
    /**
     * Unsets the "tsaNotePresent" element
     */
    void unsetTsaNotePresent();
    
    /**
     * Gets the "wasBagInspected" element
     */
    boolean getWasBagInspected();
    
    /**
     * Gets (as xml) the "wasBagInspected" element
     */
    org.apache.xmlbeans.XmlBoolean xgetWasBagInspected();
    
    /**
     * True if has "wasBagInspected" element
     */
    boolean isSetWasBagInspected();
    
    /**
     * Sets the "wasBagInspected" element
     */
    void setWasBagInspected(boolean wasBagInspected);
    
    /**
     * Sets (as xml) the "wasBagInspected" element
     */
    void xsetWasBagInspected(org.apache.xmlbeans.XmlBoolean wasBagInspected);
    
    /**
     * Unsets the "wasBagInspected" element
     */
    void unsetWasBagInspected();
    
    /**
     * Gets the "whereDidYouFileReport" element
     */
    java.lang.String getWhereDidYouFileReport();
    
    /**
     * Gets (as xml) the "whereDidYouFileReport" element
     */
    org.apache.xmlbeans.XmlString xgetWhereDidYouFileReport();
    
    /**
     * Tests for nil "whereDidYouFileReport" element
     */
    boolean isNilWhereDidYouFileReport();
    
    /**
     * True if has "whereDidYouFileReport" element
     */
    boolean isSetWhereDidYouFileReport();
    
    /**
     * Sets the "whereDidYouFileReport" element
     */
    void setWhereDidYouFileReport(java.lang.String whereDidYouFileReport);
    
    /**
     * Sets (as xml) the "whereDidYouFileReport" element
     */
    void xsetWhereDidYouFileReport(org.apache.xmlbeans.XmlString whereDidYouFileReport);
    
    /**
     * Nils the "whereDidYouFileReport" element
     */
    void setNilWhereDidYouFileReport();
    
    /**
     * Unsets the "whereDidYouFileReport" element
     */
    void unsetWhereDidYouFileReport();
    
    /**
     * Gets the "whereWasBaggageChecked" element
     */
    java.lang.String getWhereWasBaggageChecked();
    
    /**
     * Gets (as xml) the "whereWasBaggageChecked" element
     */
    org.apache.xmlbeans.XmlString xgetWhereWasBaggageChecked();
    
    /**
     * Tests for nil "whereWasBaggageChecked" element
     */
    boolean isNilWhereWasBaggageChecked();
    
    /**
     * True if has "whereWasBaggageChecked" element
     */
    boolean isSetWhereWasBaggageChecked();
    
    /**
     * Sets the "whereWasBaggageChecked" element
     */
    void setWhereWasBaggageChecked(java.lang.String whereWasBaggageChecked);
    
    /**
     * Sets (as xml) the "whereWasBaggageChecked" element
     */
    void xsetWhereWasBaggageChecked(org.apache.xmlbeans.XmlString whereWasBaggageChecked);
    
    /**
     * Nils the "whereWasBaggageChecked" element
     */
    void setNilWhereWasBaggageChecked();
    
    /**
     * Unsets the "whereWasBaggageChecked" element
     */
    void unsetWhereWasBaggageChecked();
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Claim newInstance() {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Claim) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Claim newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Claim) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Claim parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Claim) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Claim parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Claim) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Claim parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Claim) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Claim parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Claim) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Claim parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Claim) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Claim parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Claim) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Claim parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Claim) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Claim parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Claim) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Claim parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Claim) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Claim parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Claim) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Claim parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Claim) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Claim parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Claim) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Claim parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Claim) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Claim parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Claim) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Claim parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Claim) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static com.bagnet.nettracer.ws.onlineclaims.xsd.Claim parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.bagnet.nettracer.ws.onlineclaims.xsd.Claim) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
