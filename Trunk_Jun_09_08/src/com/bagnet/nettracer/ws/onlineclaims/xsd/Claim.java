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
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(Claim.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sECB0ECF159DBAD03265284E0F73AEDC5").resolveHandle("claimd979type");
    
    /**
     * Gets the "attemptToClaimAtArrival" element
     */
    int getAttemptToClaimAtArrival();
    
    /**
     * Gets (as xml) the "attemptToClaimAtArrival" element
     */
    org.apache.xmlbeans.XmlInt xgetAttemptToClaimAtArrival();
    
    /**
     * True if has "attemptToClaimAtArrival" element
     */
    boolean isSetAttemptToClaimAtArrival();
    
    /**
     * Sets the "attemptToClaimAtArrival" element
     */
    void setAttemptToClaimAtArrival(int attemptToClaimAtArrival);
    
    /**
     * Sets (as xml) the "attemptToClaimAtArrival" element
     */
    void xsetAttemptToClaimAtArrival(org.apache.xmlbeans.XmlInt attemptToClaimAtArrival);
    
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
    int getBagClearCustoms();
    
    /**
     * Gets (as xml) the "bagClearCustoms" element
     */
    org.apache.xmlbeans.XmlInt xgetBagClearCustoms();
    
    /**
     * True if has "bagClearCustoms" element
     */
    boolean isSetBagClearCustoms();
    
    /**
     * Sets the "bagClearCustoms" element
     */
    void setBagClearCustoms(int bagClearCustoms);
    
    /**
     * Sets (as xml) the "bagClearCustoms" element
     */
    void xsetBagClearCustoms(org.apache.xmlbeans.XmlInt bagClearCustoms);
    
    /**
     * Unsets the "bagClearCustoms" element
     */
    void unsetBagClearCustoms();
    
    /**
     * Gets the "bagReceivedDate" element
     */
    java.util.Calendar getBagReceivedDate();
    
    /**
     * Gets (as xml) the "bagReceivedDate" element
     */
    org.apache.xmlbeans.XmlDate xgetBagReceivedDate();
    
    /**
     * Tests for nil "bagReceivedDate" element
     */
    boolean isNilBagReceivedDate();
    
    /**
     * True if has "bagReceivedDate" element
     */
    boolean isSetBagReceivedDate();
    
    /**
     * Sets the "bagReceivedDate" element
     */
    void setBagReceivedDate(java.util.Calendar bagReceivedDate);
    
    /**
     * Sets (as xml) the "bagReceivedDate" element
     */
    void xsetBagReceivedDate(org.apache.xmlbeans.XmlDate bagReceivedDate);
    
    /**
     * Nils the "bagReceivedDate" element
     */
    void setNilBagReceivedDate();
    
    /**
     * Unsets the "bagReceivedDate" element
     */
    void unsetBagReceivedDate();
    
    /**
     * Gets the "bagWeight" element
     */
    java.lang.String getBagWeight();
    
    /**
     * Gets (as xml) the "bagWeight" element
     */
    org.apache.xmlbeans.XmlString xgetBagWeight();
    
    /**
     * Tests for nil "bagWeight" element
     */
    boolean isNilBagWeight();
    
    /**
     * True if has "bagWeight" element
     */
    boolean isSetBagWeight();
    
    /**
     * Sets the "bagWeight" element
     */
    void setBagWeight(java.lang.String bagWeight);
    
    /**
     * Sets (as xml) the "bagWeight" element
     */
    void xsetBagWeight(org.apache.xmlbeans.XmlString bagWeight);
    
    /**
     * Nils the "bagWeight" element
     */
    void setNilBagWeight();
    
    /**
     * Unsets the "bagWeight" element
     */
    void unsetBagWeight();
    
    /**
     * Gets the "baggageReroutedEnRoute" element
     */
    int getBaggageReroutedEnRoute();
    
    /**
     * Gets (as xml) the "baggageReroutedEnRoute" element
     */
    org.apache.xmlbeans.XmlInt xgetBaggageReroutedEnRoute();
    
    /**
     * True if has "baggageReroutedEnRoute" element
     */
    boolean isSetBaggageReroutedEnRoute();
    
    /**
     * Sets the "baggageReroutedEnRoute" element
     */
    void setBaggageReroutedEnRoute(int baggageReroutedEnRoute);
    
    /**
     * Sets (as xml) the "baggageReroutedEnRoute" element
     */
    void xsetBaggageReroutedEnRoute(org.apache.xmlbeans.XmlInt baggageReroutedEnRoute);
    
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
    int getChargedForExcessBaggage();
    
    /**
     * Gets (as xml) the "chargedForExcessBaggage" element
     */
    org.apache.xmlbeans.XmlInt xgetChargedForExcessBaggage();
    
    /**
     * True if has "chargedForExcessBaggage" element
     */
    boolean isSetChargedForExcessBaggage();
    
    /**
     * Sets the "chargedForExcessBaggage" element
     */
    void setChargedForExcessBaggage(int chargedForExcessBaggage);
    
    /**
     * Sets (as xml) the "chargedForExcessBaggage" element
     */
    void xsetChargedForExcessBaggage(org.apache.xmlbeans.XmlInt chargedForExcessBaggage);
    
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
     * Gets the "checkedLocationDescription" element
     */
    java.lang.String getCheckedLocationDescription();
    
    /**
     * Gets (as xml) the "checkedLocationDescription" element
     */
    org.apache.xmlbeans.XmlString xgetCheckedLocationDescription();
    
    /**
     * Tests for nil "checkedLocationDescription" element
     */
    boolean isNilCheckedLocationDescription();
    
    /**
     * True if has "checkedLocationDescription" element
     */
    boolean isSetCheckedLocationDescription();
    
    /**
     * Sets the "checkedLocationDescription" element
     */
    void setCheckedLocationDescription(java.lang.String checkedLocationDescription);
    
    /**
     * Sets (as xml) the "checkedLocationDescription" element
     */
    void xsetCheckedLocationDescription(org.apache.xmlbeans.XmlString checkedLocationDescription);
    
    /**
     * Nils the "checkedLocationDescription" element
     */
    void setNilCheckedLocationDescription();
    
    /**
     * Unsets the "checkedLocationDescription" element
     */
    void unsetCheckedLocationDescription();
    
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
     * Gets the "claimStatus" element
     */
    java.lang.String getClaimStatus();
    
    /**
     * Gets (as xml) the "claimStatus" element
     */
    org.apache.xmlbeans.XmlString xgetClaimStatus();
    
    /**
     * Tests for nil "claimStatus" element
     */
    boolean isNilClaimStatus();
    
    /**
     * True if has "claimStatus" element
     */
    boolean isSetClaimStatus();
    
    /**
     * Sets the "claimStatus" element
     */
    void setClaimStatus(java.lang.String claimStatus);
    
    /**
     * Sets (as xml) the "claimStatus" element
     */
    void xsetClaimStatus(org.apache.xmlbeans.XmlString claimStatus);
    
    /**
     * Nils the "claimStatus" element
     */
    void setNilClaimStatus();
    
    /**
     * Unsets the "claimStatus" element
     */
    void unsetClaimStatus();
    
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
     * Gets the "declaredCurrency" element
     */
    java.lang.String getDeclaredCurrency();
    
    /**
     * Gets (as xml) the "declaredCurrency" element
     */
    org.apache.xmlbeans.XmlString xgetDeclaredCurrency();
    
    /**
     * Tests for nil "declaredCurrency" element
     */
    boolean isNilDeclaredCurrency();
    
    /**
     * True if has "declaredCurrency" element
     */
    boolean isSetDeclaredCurrency();
    
    /**
     * Sets the "declaredCurrency" element
     */
    void setDeclaredCurrency(java.lang.String declaredCurrency);
    
    /**
     * Sets (as xml) the "declaredCurrency" element
     */
    void xsetDeclaredCurrency(org.apache.xmlbeans.XmlString declaredCurrency);
    
    /**
     * Nils the "declaredCurrency" element
     */
    void setNilDeclaredCurrency();
    
    /**
     * Unsets the "declaredCurrency" element
     */
    void unsetDeclaredCurrency();
    
    /**
     * Gets the "declaredExcessValue" element
     */
    int getDeclaredExcessValue();
    
    /**
     * Gets (as xml) the "declaredExcessValue" element
     */
    org.apache.xmlbeans.XmlInt xgetDeclaredExcessValue();
    
    /**
     * True if has "declaredExcessValue" element
     */
    boolean isSetDeclaredExcessValue();
    
    /**
     * Sets the "declaredExcessValue" element
     */
    void setDeclaredExcessValue(int declaredExcessValue);
    
    /**
     * Sets (as xml) the "declaredExcessValue" element
     */
    void xsetDeclaredExcessValue(org.apache.xmlbeans.XmlInt declaredExcessValue);
    
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
    int getDifferentClaimCheck();
    
    /**
     * Gets (as xml) the "differentClaimCheck" element
     */
    org.apache.xmlbeans.XmlInt xgetDifferentClaimCheck();
    
    /**
     * True if has "differentClaimCheck" element
     */
    boolean isSetDifferentClaimCheck();
    
    /**
     * Sets the "differentClaimCheck" element
     */
    void setDifferentClaimCheck(int differentClaimCheck);
    
    /**
     * Sets (as xml) the "differentClaimCheck" element
     */
    void xsetDifferentClaimCheck(org.apache.xmlbeans.XmlInt differentClaimCheck);
    
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
    int getFiledPreviousClaim();
    
    /**
     * Gets (as xml) the "filedPreviousClaim" element
     */
    org.apache.xmlbeans.XmlInt xgetFiledPreviousClaim();
    
    /**
     * True if has "filedPreviousClaim" element
     */
    boolean isSetFiledPreviousClaim();
    
    /**
     * Sets the "filedPreviousClaim" element
     */
    void setFiledPreviousClaim(int filedPreviousClaim);
    
    /**
     * Sets (as xml) the "filedPreviousClaim" element
     */
    void xsetFiledPreviousClaim(org.apache.xmlbeans.XmlInt filedPreviousClaim);
    
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
     * Gets the "foreignCurrencyEmail" element
     */
    java.lang.String getForeignCurrencyEmail();
    
    /**
     * Gets (as xml) the "foreignCurrencyEmail" element
     */
    org.apache.xmlbeans.XmlString xgetForeignCurrencyEmail();
    
    /**
     * Tests for nil "foreignCurrencyEmail" element
     */
    boolean isNilForeignCurrencyEmail();
    
    /**
     * True if has "foreignCurrencyEmail" element
     */
    boolean isSetForeignCurrencyEmail();
    
    /**
     * Sets the "foreignCurrencyEmail" element
     */
    void setForeignCurrencyEmail(java.lang.String foreignCurrencyEmail);
    
    /**
     * Sets (as xml) the "foreignCurrencyEmail" element
     */
    void xsetForeignCurrencyEmail(org.apache.xmlbeans.XmlString foreignCurrencyEmail);
    
    /**
     * Nils the "foreignCurrencyEmail" element
     */
    void setNilForeignCurrencyEmail();
    
    /**
     * Unsets the "foreignCurrencyEmail" element
     */
    void unsetForeignCurrencyEmail();
    
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
    int getHaveToRecheck();
    
    /**
     * Gets (as xml) the "haveToRecheck" element
     */
    org.apache.xmlbeans.XmlInt xgetHaveToRecheck();
    
    /**
     * True if has "haveToRecheck" element
     */
    boolean isSetHaveToRecheck();
    
    /**
     * Sets the "haveToRecheck" element
     */
    void setHaveToRecheck(int haveToRecheck);
    
    /**
     * Sets (as xml) the "haveToRecheck" element
     */
    void xsetHaveToRecheck(org.apache.xmlbeans.XmlInt haveToRecheck);
    
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
     * Gets the "lengthOfStay" element
     */
    java.lang.String getLengthOfStay();
    
    /**
     * Gets (as xml) the "lengthOfStay" element
     */
    org.apache.xmlbeans.XmlString xgetLengthOfStay();
    
    /**
     * Tests for nil "lengthOfStay" element
     */
    boolean isNilLengthOfStay();
    
    /**
     * True if has "lengthOfStay" element
     */
    boolean isSetLengthOfStay();
    
    /**
     * Sets the "lengthOfStay" element
     */
    void setLengthOfStay(java.lang.String lengthOfStay);
    
    /**
     * Sets (as xml) the "lengthOfStay" element
     */
    void xsetLengthOfStay(org.apache.xmlbeans.XmlString lengthOfStay);
    
    /**
     * Nils the "lengthOfStay" element
     */
    void setNilLengthOfStay();
    
    /**
     * Unsets the "lengthOfStay" element
     */
    void unsetLengthOfStay();
    
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
     * Gets array of all "messages" elements
     */
    com.bagnet.nettracer.ws.onlineclaims.xsd.Message[] getMessagesArray();
    
    /**
     * Gets ith "messages" element
     */
    com.bagnet.nettracer.ws.onlineclaims.xsd.Message getMessagesArray(int i);
    
    /**
     * Tests for nil ith "messages" element
     */
    boolean isNilMessagesArray(int i);
    
    /**
     * Returns number of "messages" element
     */
    int sizeOfMessagesArray();
    
    /**
     * Sets array of all "messages" element
     */
    void setMessagesArray(com.bagnet.nettracer.ws.onlineclaims.xsd.Message[] messagesArray);
    
    /**
     * Sets ith "messages" element
     */
    void setMessagesArray(int i, com.bagnet.nettracer.ws.onlineclaims.xsd.Message messages);
    
    /**
     * Nils the ith "messages" element
     */
    void setNilMessagesArray(int i);
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "messages" element
     */
    com.bagnet.nettracer.ws.onlineclaims.xsd.Message insertNewMessages(int i);
    
    /**
     * Appends and returns a new empty value (as xml) as the last "messages" element
     */
    com.bagnet.nettracer.ws.onlineclaims.xsd.Message addNewMessages();
    
    /**
     * Removes the ith "messages" element
     */
    void removeMessages(int i);
    
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
     * Gets array of all "passenger" elements
     */
    com.bagnet.nettracer.ws.onlineclaims.xsd.Passenger[] getPassengerArray();
    
    /**
     * Gets ith "passenger" element
     */
    com.bagnet.nettracer.ws.onlineclaims.xsd.Passenger getPassengerArray(int i);
    
    /**
     * Tests for nil ith "passenger" element
     */
    boolean isNilPassengerArray(int i);
    
    /**
     * Returns number of "passenger" element
     */
    int sizeOfPassengerArray();
    
    /**
     * Sets array of all "passenger" element
     */
    void setPassengerArray(com.bagnet.nettracer.ws.onlineclaims.xsd.Passenger[] passengerArray);
    
    /**
     * Sets ith "passenger" element
     */
    void setPassengerArray(int i, com.bagnet.nettracer.ws.onlineclaims.xsd.Passenger passenger);
    
    /**
     * Nils the ith "passenger" element
     */
    void setNilPassengerArray(int i);
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "passenger" element
     */
    com.bagnet.nettracer.ws.onlineclaims.xsd.Passenger insertNewPassenger(int i);
    
    /**
     * Appends and returns a new empty value (as xml) as the last "passenger" element
     */
    com.bagnet.nettracer.ws.onlineclaims.xsd.Passenger addNewPassenger();
    
    /**
     * Removes the ith "passenger" element
     */
    void removePassenger(int i);
    
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
     * Gets the "paxClaimAmount" element
     */
    java.lang.String getPaxClaimAmount();
    
    /**
     * Gets (as xml) the "paxClaimAmount" element
     */
    org.apache.xmlbeans.XmlString xgetPaxClaimAmount();
    
    /**
     * Tests for nil "paxClaimAmount" element
     */
    boolean isNilPaxClaimAmount();
    
    /**
     * True if has "paxClaimAmount" element
     */
    boolean isSetPaxClaimAmount();
    
    /**
     * Sets the "paxClaimAmount" element
     */
    void setPaxClaimAmount(java.lang.String paxClaimAmount);
    
    /**
     * Sets (as xml) the "paxClaimAmount" element
     */
    void xsetPaxClaimAmount(org.apache.xmlbeans.XmlString paxClaimAmount);
    
    /**
     * Nils the "paxClaimAmount" element
     */
    void setNilPaxClaimAmount();
    
    /**
     * Unsets the "paxClaimAmount" element
     */
    void unsetPaxClaimAmount();
    
    /**
     * Gets the "paxClaimAmountCurrency" element
     */
    java.lang.String getPaxClaimAmountCurrency();
    
    /**
     * Gets (as xml) the "paxClaimAmountCurrency" element
     */
    org.apache.xmlbeans.XmlString xgetPaxClaimAmountCurrency();
    
    /**
     * Tests for nil "paxClaimAmountCurrency" element
     */
    boolean isNilPaxClaimAmountCurrency();
    
    /**
     * True if has "paxClaimAmountCurrency" element
     */
    boolean isSetPaxClaimAmountCurrency();
    
    /**
     * Sets the "paxClaimAmountCurrency" element
     */
    void setPaxClaimAmountCurrency(java.lang.String paxClaimAmountCurrency);
    
    /**
     * Sets (as xml) the "paxClaimAmountCurrency" element
     */
    void xsetPaxClaimAmountCurrency(org.apache.xmlbeans.XmlString paxClaimAmountCurrency);
    
    /**
     * Nils the "paxClaimAmountCurrency" element
     */
    void setNilPaxClaimAmountCurrency();
    
    /**
     * Unsets the "paxClaimAmountCurrency" element
     */
    void unsetPaxClaimAmountCurrency();
    
    /**
     * Gets the "paxClaimDate" element
     */
    java.lang.String getPaxClaimDate();
    
    /**
     * Gets (as xml) the "paxClaimDate" element
     */
    org.apache.xmlbeans.XmlString xgetPaxClaimDate();
    
    /**
     * Tests for nil "paxClaimDate" element
     */
    boolean isNilPaxClaimDate();
    
    /**
     * True if has "paxClaimDate" element
     */
    boolean isSetPaxClaimDate();
    
    /**
     * Sets the "paxClaimDate" element
     */
    void setPaxClaimDate(java.lang.String paxClaimDate);
    
    /**
     * Sets (as xml) the "paxClaimDate" element
     */
    void xsetPaxClaimDate(org.apache.xmlbeans.XmlString paxClaimDate);
    
    /**
     * Nils the "paxClaimDate" element
     */
    void setNilPaxClaimDate();
    
    /**
     * Unsets the "paxClaimDate" element
     */
    void unsetPaxClaimDate();
    
    /**
     * Gets the "paxIpAddress" element
     */
    java.lang.String getPaxIpAddress();
    
    /**
     * Gets (as xml) the "paxIpAddress" element
     */
    org.apache.xmlbeans.XmlString xgetPaxIpAddress();
    
    /**
     * Tests for nil "paxIpAddress" element
     */
    boolean isNilPaxIpAddress();
    
    /**
     * True if has "paxIpAddress" element
     */
    boolean isSetPaxIpAddress();
    
    /**
     * Sets the "paxIpAddress" element
     */
    void setPaxIpAddress(java.lang.String paxIpAddress);
    
    /**
     * Sets (as xml) the "paxIpAddress" element
     */
    void xsetPaxIpAddress(org.apache.xmlbeans.XmlString paxIpAddress);
    
    /**
     * Nils the "paxIpAddress" element
     */
    void setNilPaxIpAddress();
    
    /**
     * Unsets the "paxIpAddress" element
     */
    void unsetPaxIpAddress();
    
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
     * Gets the "privateInsurance" element
     */
    int getPrivateInsurance();
    
    /**
     * Gets (as xml) the "privateInsurance" element
     */
    org.apache.xmlbeans.XmlInt xgetPrivateInsurance();
    
    /**
     * True if has "privateInsurance" element
     */
    boolean isSetPrivateInsurance();
    
    /**
     * Sets the "privateInsurance" element
     */
    void setPrivateInsurance(int privateInsurance);
    
    /**
     * Sets (as xml) the "privateInsurance" element
     */
    void xsetPrivateInsurance(org.apache.xmlbeans.XmlInt privateInsurance);
    
    /**
     * Unsets the "privateInsurance" element
     */
    void unsetPrivateInsurance();
    
    /**
     * Gets the "privateInsuranceAddr" element
     */
    java.lang.String getPrivateInsuranceAddr();
    
    /**
     * Gets (as xml) the "privateInsuranceAddr" element
     */
    org.apache.xmlbeans.XmlString xgetPrivateInsuranceAddr();
    
    /**
     * Tests for nil "privateInsuranceAddr" element
     */
    boolean isNilPrivateInsuranceAddr();
    
    /**
     * True if has "privateInsuranceAddr" element
     */
    boolean isSetPrivateInsuranceAddr();
    
    /**
     * Sets the "privateInsuranceAddr" element
     */
    void setPrivateInsuranceAddr(java.lang.String privateInsuranceAddr);
    
    /**
     * Sets (as xml) the "privateInsuranceAddr" element
     */
    void xsetPrivateInsuranceAddr(org.apache.xmlbeans.XmlString privateInsuranceAddr);
    
    /**
     * Nils the "privateInsuranceAddr" element
     */
    void setNilPrivateInsuranceAddr();
    
    /**
     * Unsets the "privateInsuranceAddr" element
     */
    void unsetPrivateInsuranceAddr();
    
    /**
     * Gets the "privateInsuranceName" element
     */
    java.lang.String getPrivateInsuranceName();
    
    /**
     * Gets (as xml) the "privateInsuranceName" element
     */
    org.apache.xmlbeans.XmlString xgetPrivateInsuranceName();
    
    /**
     * Tests for nil "privateInsuranceName" element
     */
    boolean isNilPrivateInsuranceName();
    
    /**
     * True if has "privateInsuranceName" element
     */
    boolean isSetPrivateInsuranceName();
    
    /**
     * Sets the "privateInsuranceName" element
     */
    void setPrivateInsuranceName(java.lang.String privateInsuranceName);
    
    /**
     * Sets (as xml) the "privateInsuranceName" element
     */
    void xsetPrivateInsuranceName(org.apache.xmlbeans.XmlString privateInsuranceName);
    
    /**
     * Nils the "privateInsuranceName" element
     */
    void setNilPrivateInsuranceName();
    
    /**
     * Unsets the "privateInsuranceName" element
     */
    void unsetPrivateInsuranceName();
    
    /**
     * Gets the "reasonForTravel" element
     */
    java.lang.String getReasonForTravel();
    
    /**
     * Gets (as xml) the "reasonForTravel" element
     */
    org.apache.xmlbeans.XmlString xgetReasonForTravel();
    
    /**
     * Tests for nil "reasonForTravel" element
     */
    boolean isNilReasonForTravel();
    
    /**
     * True if has "reasonForTravel" element
     */
    boolean isSetReasonForTravel();
    
    /**
     * Sets the "reasonForTravel" element
     */
    void setReasonForTravel(java.lang.String reasonForTravel);
    
    /**
     * Sets (as xml) the "reasonForTravel" element
     */
    void xsetReasonForTravel(org.apache.xmlbeans.XmlString reasonForTravel);
    
    /**
     * Nils the "reasonForTravel" element
     */
    void setNilReasonForTravel();
    
    /**
     * Unsets the "reasonForTravel" element
     */
    void unsetReasonForTravel();
    
    /**
     * Gets the "reportedAirline" element
     */
    java.lang.String getReportedAirline();
    
    /**
     * Gets (as xml) the "reportedAirline" element
     */
    org.apache.xmlbeans.XmlString xgetReportedAirline();
    
    /**
     * Tests for nil "reportedAirline" element
     */
    boolean isNilReportedAirline();
    
    /**
     * True if has "reportedAirline" element
     */
    boolean isSetReportedAirline();
    
    /**
     * Sets the "reportedAirline" element
     */
    void setReportedAirline(java.lang.String reportedAirline);
    
    /**
     * Sets (as xml) the "reportedAirline" element
     */
    void xsetReportedAirline(org.apache.xmlbeans.XmlString reportedAirline);
    
    /**
     * Nils the "reportedAirline" element
     */
    void setNilReportedAirline();
    
    /**
     * Unsets the "reportedAirline" element
     */
    void unsetReportedAirline();
    
    /**
     * Gets the "reportedCity" element
     */
    java.lang.String getReportedCity();
    
    /**
     * Gets (as xml) the "reportedCity" element
     */
    org.apache.xmlbeans.XmlString xgetReportedCity();
    
    /**
     * Tests for nil "reportedCity" element
     */
    boolean isNilReportedCity();
    
    /**
     * True if has "reportedCity" element
     */
    boolean isSetReportedCity();
    
    /**
     * Sets the "reportedCity" element
     */
    void setReportedCity(java.lang.String reportedCity);
    
    /**
     * Sets (as xml) the "reportedCity" element
     */
    void xsetReportedCity(org.apache.xmlbeans.XmlString reportedCity);
    
    /**
     * Nils the "reportedCity" element
     */
    void setNilReportedCity();
    
    /**
     * Unsets the "reportedCity" element
     */
    void unsetReportedCity();
    
    /**
     * Gets the "reportedFileNumber" element
     */
    java.lang.String getReportedFileNumber();
    
    /**
     * Gets (as xml) the "reportedFileNumber" element
     */
    org.apache.xmlbeans.XmlString xgetReportedFileNumber();
    
    /**
     * Tests for nil "reportedFileNumber" element
     */
    boolean isNilReportedFileNumber();
    
    /**
     * True if has "reportedFileNumber" element
     */
    boolean isSetReportedFileNumber();
    
    /**
     * Sets the "reportedFileNumber" element
     */
    void setReportedFileNumber(java.lang.String reportedFileNumber);
    
    /**
     * Sets (as xml) the "reportedFileNumber" element
     */
    void xsetReportedFileNumber(org.apache.xmlbeans.XmlString reportedFileNumber);
    
    /**
     * Nils the "reportedFileNumber" element
     */
    void setNilReportedFileNumber();
    
    /**
     * Unsets the "reportedFileNumber" element
     */
    void unsetReportedFileNumber();
    
    /**
     * Gets the "reportedToAnotherAirline" element
     */
    int getReportedToAnotherAirline();
    
    /**
     * Gets (as xml) the "reportedToAnotherAirline" element
     */
    org.apache.xmlbeans.XmlInt xgetReportedToAnotherAirline();
    
    /**
     * True if has "reportedToAnotherAirline" element
     */
    boolean isSetReportedToAnotherAirline();
    
    /**
     * Sets the "reportedToAnotherAirline" element
     */
    void setReportedToAnotherAirline(int reportedToAnotherAirline);
    
    /**
     * Sets (as xml) the "reportedToAnotherAirline" element
     */
    void xsetReportedToAnotherAirline(org.apache.xmlbeans.XmlInt reportedToAnotherAirline);
    
    /**
     * Unsets the "reportedToAnotherAirline" element
     */
    void unsetReportedToAnotherAirline();
    
    /**
     * Gets the "requestForeignCurrency" element
     */
    int getRequestForeignCurrency();
    
    /**
     * Gets (as xml) the "requestForeignCurrency" element
     */
    org.apache.xmlbeans.XmlInt xgetRequestForeignCurrency();
    
    /**
     * True if has "requestForeignCurrency" element
     */
    boolean isSetRequestForeignCurrency();
    
    /**
     * Sets the "requestForeignCurrency" element
     */
    void setRequestForeignCurrency(int requestForeignCurrency);
    
    /**
     * Sets (as xml) the "requestForeignCurrency" element
     */
    void xsetRequestForeignCurrency(org.apache.xmlbeans.XmlInt requestForeignCurrency);
    
    /**
     * Unsets the "requestForeignCurrency" element
     */
    void unsetRequestForeignCurrency();
    
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
     * Gets the "ticketNumber" element
     */
    java.lang.String getTicketNumber();
    
    /**
     * Gets (as xml) the "ticketNumber" element
     */
    org.apache.xmlbeans.XmlString xgetTicketNumber();
    
    /**
     * Tests for nil "ticketNumber" element
     */
    boolean isNilTicketNumber();
    
    /**
     * True if has "ticketNumber" element
     */
    boolean isSetTicketNumber();
    
    /**
     * Sets the "ticketNumber" element
     */
    void setTicketNumber(java.lang.String ticketNumber);
    
    /**
     * Sets (as xml) the "ticketNumber" element
     */
    void xsetTicketNumber(org.apache.xmlbeans.XmlString ticketNumber);
    
    /**
     * Nils the "ticketNumber" element
     */
    void setNilTicketNumber();
    
    /**
     * Unsets the "ticketNumber" element
     */
    void unsetTicketNumber();
    
    /**
     * Gets the "ticketWithAnotherAirline" element
     */
    int getTicketWithAnotherAirline();
    
    /**
     * Gets (as xml) the "ticketWithAnotherAirline" element
     */
    org.apache.xmlbeans.XmlInt xgetTicketWithAnotherAirline();
    
    /**
     * True if has "ticketWithAnotherAirline" element
     */
    boolean isSetTicketWithAnotherAirline();
    
    /**
     * Sets the "ticketWithAnotherAirline" element
     */
    void setTicketWithAnotherAirline(int ticketWithAnotherAirline);
    
    /**
     * Sets (as xml) the "ticketWithAnotherAirline" element
     */
    void xsetTicketWithAnotherAirline(org.apache.xmlbeans.XmlInt ticketWithAnotherAirline);
    
    /**
     * Unsets the "ticketWithAnotherAirline" element
     */
    void unsetTicketWithAnotherAirline();
    
    /**
     * Gets the "tsaInspected" element
     */
    int getTsaInspected();
    
    /**
     * Gets (as xml) the "tsaInspected" element
     */
    org.apache.xmlbeans.XmlInt xgetTsaInspected();
    
    /**
     * True if has "tsaInspected" element
     */
    boolean isSetTsaInspected();
    
    /**
     * Sets the "tsaInspected" element
     */
    void setTsaInspected(int tsaInspected);
    
    /**
     * Sets (as xml) the "tsaInspected" element
     */
    void xsetTsaInspected(org.apache.xmlbeans.XmlInt tsaInspected);
    
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
    int getTsaNotePresent();
    
    /**
     * Gets (as xml) the "tsaNotePresent" element
     */
    org.apache.xmlbeans.XmlInt xgetTsaNotePresent();
    
    /**
     * True if has "tsaNotePresent" element
     */
    boolean isSetTsaNotePresent();
    
    /**
     * Sets the "tsaNotePresent" element
     */
    void setTsaNotePresent(int tsaNotePresent);
    
    /**
     * Sets (as xml) the "tsaNotePresent" element
     */
    void xsetTsaNotePresent(org.apache.xmlbeans.XmlInt tsaNotePresent);
    
    /**
     * Unsets the "tsaNotePresent" element
     */
    void unsetTsaNotePresent();
    
    /**
     * Gets the "wasBagInspected" element
     */
    int getWasBagInspected();
    
    /**
     * Gets (as xml) the "wasBagInspected" element
     */
    org.apache.xmlbeans.XmlInt xgetWasBagInspected();
    
    /**
     * True if has "wasBagInspected" element
     */
    boolean isSetWasBagInspected();
    
    /**
     * Sets the "wasBagInspected" element
     */
    void setWasBagInspected(int wasBagInspected);
    
    /**
     * Sets (as xml) the "wasBagInspected" element
     */
    void xsetWasBagInspected(org.apache.xmlbeans.XmlInt wasBagInspected);
    
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
