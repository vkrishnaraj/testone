/*
 * XML Type:  Claim
 * Namespace: http://onlineclaims.ws.nettracer.bagnet.com/xsd
 * Java type: com.bagnet.nettracer.ws.onlineclaims.xsd.Claim
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.onlineclaims.xsd.impl;
/**
 * An XML Claim(@http://onlineclaims.ws.nettracer.bagnet.com/xsd).
 *
 * This is a complex type.
 */
public class ClaimImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.onlineclaims.xsd.Claim
{
    private static final long serialVersionUID = 1L;
    
    public ClaimImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName ATTEMPTTOCLAIMATARRIVAL$0 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "attemptToClaimAtArrival");
    private static final javax.xml.namespace.QName BAG$2 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "bag");
    private static final javax.xml.namespace.QName BAGCLEARCUSTOMS$4 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "bagClearCustoms");
    private static final javax.xml.namespace.QName BAGRECEIVEDDATE$6 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "bagReceivedDate");
    private static final javax.xml.namespace.QName BAGWEIGHT$8 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "bagWeight");
    private static final javax.xml.namespace.QName BAGGAGEREROUTEDENROUTE$10 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "baggageReroutedEnRoute");
    private static final javax.xml.namespace.QName BAGSDELAYED$12 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "bagsDelayed");
    private static final javax.xml.namespace.QName BAGSSTILLMISSING$14 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "bagsStillMissing");
    private static final javax.xml.namespace.QName BAGSTRAVELWITH$16 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "bagsTravelWith");
    private static final javax.xml.namespace.QName BUSINESSNAME$18 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "businessName");
    private static final javax.xml.namespace.QName CHARGEDFOREXCESSBAGGAGE$20 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "chargedForExcessBaggage");
    private static final javax.xml.namespace.QName CHECKEDLOCATION$22 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "checkedLocation");
    private static final javax.xml.namespace.QName CHECKEDLOCATIONDESCRIPTION$24 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "checkedLocationDescription");
    private static final javax.xml.namespace.QName CLAIMID$26 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "claimId");
    private static final javax.xml.namespace.QName CLAIMSTATUS$28 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "claimStatus");
    private static final javax.xml.namespace.QName CLAIMTYPE$30 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "claimType");
    private static final javax.xml.namespace.QName COMMENTS$32 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "comments");
    private static final javax.xml.namespace.QName DECLAREDCURRENCY$34 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "declaredCurrency");
    private static final javax.xml.namespace.QName DECLAREDEXCESSVALUE$36 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "declaredExcessValue");
    private static final javax.xml.namespace.QName DECLAREDVALUE$38 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "declaredValue");
    private static final javax.xml.namespace.QName DIFFERENTCLAIMCHECK$40 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "differentClaimCheck");
    private static final javax.xml.namespace.QName EMAILADDRESS$42 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "emailAddress");
    private static final javax.xml.namespace.QName FILE$44 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "file");
    private static final javax.xml.namespace.QName FILEDPREVIOUSAIRLINE$46 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "filedPreviousAirline");
    private static final javax.xml.namespace.QName FILEDPREVIOUSCLAIM$48 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "filedPreviousClaim");
    private static final javax.xml.namespace.QName FILEDPREVIOUSCLAIMANT$50 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "filedPreviousClaimant");
    private static final javax.xml.namespace.QName FILEDPREVOIUSDATE$52 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "filedPrevoiusDate");
    private static final javax.xml.namespace.QName FOREIGNCURRENCYEMAIL$54 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "foreignCurrencyEmail");
    private static final javax.xml.namespace.QName FREQUENTFLIERNUMBER$56 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "frequentFlierNumber");
    private static final javax.xml.namespace.QName HAVETORECHECK$58 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "haveToRecheck");
    private static final javax.xml.namespace.QName ITINERARY$60 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "itinerary");
    private static final javax.xml.namespace.QName LASTSAWBAGGAGE$62 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "lastSawBaggage");
    private static final javax.xml.namespace.QName LENGTHOFSTAY$64 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "lengthOfStay");
    private static final javax.xml.namespace.QName MAILINGADDRESS$66 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "mailingAddress");
    private static final javax.xml.namespace.QName MESSAGES$68 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "messages");
    private static final javax.xml.namespace.QName OCCUPATION$70 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "occupation");
    private static final javax.xml.namespace.QName PASSENGER$72 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "passenger");
    private static final javax.xml.namespace.QName PASSENGERSTRAVELINGWITHYOU$74 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "passengersTravelingWithYou");
    private static final javax.xml.namespace.QName PAXCLAIMAMOUNT$76 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "paxClaimAmount");
    private static final javax.xml.namespace.QName PAXCLAIMAMOUNTCURRENCY$78 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "paxClaimAmountCurrency");
    private static final javax.xml.namespace.QName PAXCLAIMDATE$80 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "paxClaimDate");
    private static final javax.xml.namespace.QName PAXIPADDRESS$82 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "paxIpAddress");
    private static final javax.xml.namespace.QName PERMANENTADDRESS$84 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "permanentAddress");
    private static final javax.xml.namespace.QName PHONE$86 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "phone");
    private static final javax.xml.namespace.QName PRIVATEINSURANCE$88 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "privateInsurance");
    private static final javax.xml.namespace.QName PRIVATEINSURANCEADDR$90 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "privateInsuranceAddr");
    private static final javax.xml.namespace.QName PRIVATEINSURANCENAME$92 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "privateInsuranceName");
    private static final javax.xml.namespace.QName REASONFORTRAVEL$94 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "reasonForTravel");
    private static final javax.xml.namespace.QName REPORTEDAIRLINE$96 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "reportedAirline");
    private static final javax.xml.namespace.QName REPORTEDCITY$98 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "reportedCity");
    private static final javax.xml.namespace.QName REPORTEDFILENUMBER$100 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "reportedFileNumber");
    private static final javax.xml.namespace.QName REPORTEDTOANOTHERAIRLINE$102 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "reportedToAnotherAirline");
    private static final javax.xml.namespace.QName REQUESTFOREIGNCURRENCY$104 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "requestForeignCurrency");
    private static final javax.xml.namespace.QName REROUTEDAIRLINECITY$106 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "reroutedAirlineCity");
    private static final javax.xml.namespace.QName REROUTEDREASON$108 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "reroutedReason");
    private static final javax.xml.namespace.QName SOCIALSECURITY$110 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "socialSecurity");
    private static final javax.xml.namespace.QName STATUS$112 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "status");
    private static final javax.xml.namespace.QName TICKETNUMBER$114 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "ticketNumber");
    private static final javax.xml.namespace.QName TICKETWITHANOTHERAIRLINE$116 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "ticketWithAnotherAirline");
    private static final javax.xml.namespace.QName TSAINSPECTED$118 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "tsaInspected");
    private static final javax.xml.namespace.QName TSAINSPECTIONLOCATION$120 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "tsaInspectionLocation");
    private static final javax.xml.namespace.QName TSANOTEPRESENT$122 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "tsaNotePresent");
    private static final javax.xml.namespace.QName WASBAGINSPECTED$124 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "wasBagInspected");
    private static final javax.xml.namespace.QName WHEREDIDYOUFILEREPORT$126 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "whereDidYouFileReport");
    private static final javax.xml.namespace.QName WHEREWASBAGGAGECHECKED$128 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "whereWasBaggageChecked");
    
    
    /**
     * Gets the "attemptToClaimAtArrival" element
     */
    public int getAttemptToClaimAtArrival()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ATTEMPTTOCLAIMATARRIVAL$0, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "attemptToClaimAtArrival" element
     */
    public org.apache.xmlbeans.XmlInt xgetAttemptToClaimAtArrival()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(ATTEMPTTOCLAIMATARRIVAL$0, 0);
            return target;
        }
    }
    
    /**
     * True if has "attemptToClaimAtArrival" element
     */
    public boolean isSetAttemptToClaimAtArrival()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(ATTEMPTTOCLAIMATARRIVAL$0) != 0;
        }
    }
    
    /**
     * Sets the "attemptToClaimAtArrival" element
     */
    public void setAttemptToClaimAtArrival(int attemptToClaimAtArrival)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ATTEMPTTOCLAIMATARRIVAL$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ATTEMPTTOCLAIMATARRIVAL$0);
            }
            target.setIntValue(attemptToClaimAtArrival);
        }
    }
    
    /**
     * Sets (as xml) the "attemptToClaimAtArrival" element
     */
    public void xsetAttemptToClaimAtArrival(org.apache.xmlbeans.XmlInt attemptToClaimAtArrival)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(ATTEMPTTOCLAIMATARRIVAL$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(ATTEMPTTOCLAIMATARRIVAL$0);
            }
            target.set(attemptToClaimAtArrival);
        }
    }
    
    /**
     * Unsets the "attemptToClaimAtArrival" element
     */
    public void unsetAttemptToClaimAtArrival()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(ATTEMPTTOCLAIMATARRIVAL$0, 0);
        }
    }
    
    /**
     * Gets array of all "bag" elements
     */
    public com.bagnet.nettracer.ws.onlineclaims.xsd.Bag[] getBagArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(BAG$2, targetList);
            com.bagnet.nettracer.ws.onlineclaims.xsd.Bag[] result = new com.bagnet.nettracer.ws.onlineclaims.xsd.Bag[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "bag" element
     */
    public com.bagnet.nettracer.ws.onlineclaims.xsd.Bag getBagArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.xsd.Bag target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.xsd.Bag)get_store().find_element_user(BAG$2, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Tests for nil ith "bag" element
     */
    public boolean isNilBagArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.xsd.Bag target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.xsd.Bag)get_store().find_element_user(BAG$2, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target.isNil();
        }
    }
    
    /**
     * Returns number of "bag" element
     */
    public int sizeOfBagArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(BAG$2);
        }
    }
    
    /**
     * Sets array of all "bag" element  WARNING: This method is not atomicaly synchronized.
     */
    public void setBagArray(com.bagnet.nettracer.ws.onlineclaims.xsd.Bag[] bagArray)
    {
        check_orphaned();
        arraySetterHelper(bagArray, BAG$2);
    }
    
    /**
     * Sets ith "bag" element
     */
    public void setBagArray(int i, com.bagnet.nettracer.ws.onlineclaims.xsd.Bag bag)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.xsd.Bag target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.xsd.Bag)get_store().find_element_user(BAG$2, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(bag);
        }
    }
    
    /**
     * Nils the ith "bag" element
     */
    public void setNilBagArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.xsd.Bag target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.xsd.Bag)get_store().find_element_user(BAG$2, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.setNil();
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "bag" element
     */
    public com.bagnet.nettracer.ws.onlineclaims.xsd.Bag insertNewBag(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.xsd.Bag target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.xsd.Bag)get_store().insert_element_user(BAG$2, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "bag" element
     */
    public com.bagnet.nettracer.ws.onlineclaims.xsd.Bag addNewBag()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.xsd.Bag target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.xsd.Bag)get_store().add_element_user(BAG$2);
            return target;
        }
    }
    
    /**
     * Removes the ith "bag" element
     */
    public void removeBag(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(BAG$2, i);
        }
    }
    
    /**
     * Gets the "bagClearCustoms" element
     */
    public int getBagClearCustoms()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(BAGCLEARCUSTOMS$4, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "bagClearCustoms" element
     */
    public org.apache.xmlbeans.XmlInt xgetBagClearCustoms()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(BAGCLEARCUSTOMS$4, 0);
            return target;
        }
    }
    
    /**
     * True if has "bagClearCustoms" element
     */
    public boolean isSetBagClearCustoms()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(BAGCLEARCUSTOMS$4) != 0;
        }
    }
    
    /**
     * Sets the "bagClearCustoms" element
     */
    public void setBagClearCustoms(int bagClearCustoms)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(BAGCLEARCUSTOMS$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(BAGCLEARCUSTOMS$4);
            }
            target.setIntValue(bagClearCustoms);
        }
    }
    
    /**
     * Sets (as xml) the "bagClearCustoms" element
     */
    public void xsetBagClearCustoms(org.apache.xmlbeans.XmlInt bagClearCustoms)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(BAGCLEARCUSTOMS$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(BAGCLEARCUSTOMS$4);
            }
            target.set(bagClearCustoms);
        }
    }
    
    /**
     * Unsets the "bagClearCustoms" element
     */
    public void unsetBagClearCustoms()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(BAGCLEARCUSTOMS$4, 0);
        }
    }
    
    /**
     * Gets the "bagReceivedDate" element
     */
    public java.util.Calendar getBagReceivedDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(BAGRECEIVEDDATE$6, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "bagReceivedDate" element
     */
    public org.apache.xmlbeans.XmlDate xgetBagReceivedDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDate target = null;
            target = (org.apache.xmlbeans.XmlDate)get_store().find_element_user(BAGRECEIVEDDATE$6, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "bagReceivedDate" element
     */
    public boolean isNilBagReceivedDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDate target = null;
            target = (org.apache.xmlbeans.XmlDate)get_store().find_element_user(BAGRECEIVEDDATE$6, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "bagReceivedDate" element
     */
    public boolean isSetBagReceivedDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(BAGRECEIVEDDATE$6) != 0;
        }
    }
    
    /**
     * Sets the "bagReceivedDate" element
     */
    public void setBagReceivedDate(java.util.Calendar bagReceivedDate)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(BAGRECEIVEDDATE$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(BAGRECEIVEDDATE$6);
            }
            target.setCalendarValue(bagReceivedDate);
        }
    }
    
    /**
     * Sets (as xml) the "bagReceivedDate" element
     */
    public void xsetBagReceivedDate(org.apache.xmlbeans.XmlDate bagReceivedDate)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDate target = null;
            target = (org.apache.xmlbeans.XmlDate)get_store().find_element_user(BAGRECEIVEDDATE$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDate)get_store().add_element_user(BAGRECEIVEDDATE$6);
            }
            target.set(bagReceivedDate);
        }
    }
    
    /**
     * Nils the "bagReceivedDate" element
     */
    public void setNilBagReceivedDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDate target = null;
            target = (org.apache.xmlbeans.XmlDate)get_store().find_element_user(BAGRECEIVEDDATE$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDate)get_store().add_element_user(BAGRECEIVEDDATE$6);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "bagReceivedDate" element
     */
    public void unsetBagReceivedDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(BAGRECEIVEDDATE$6, 0);
        }
    }
    
    /**
     * Gets the "bagWeight" element
     */
    public java.lang.String getBagWeight()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(BAGWEIGHT$8, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "bagWeight" element
     */
    public org.apache.xmlbeans.XmlString xgetBagWeight()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(BAGWEIGHT$8, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "bagWeight" element
     */
    public boolean isNilBagWeight()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(BAGWEIGHT$8, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "bagWeight" element
     */
    public boolean isSetBagWeight()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(BAGWEIGHT$8) != 0;
        }
    }
    
    /**
     * Sets the "bagWeight" element
     */
    public void setBagWeight(java.lang.String bagWeight)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(BAGWEIGHT$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(BAGWEIGHT$8);
            }
            target.setStringValue(bagWeight);
        }
    }
    
    /**
     * Sets (as xml) the "bagWeight" element
     */
    public void xsetBagWeight(org.apache.xmlbeans.XmlString bagWeight)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(BAGWEIGHT$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(BAGWEIGHT$8);
            }
            target.set(bagWeight);
        }
    }
    
    /**
     * Nils the "bagWeight" element
     */
    public void setNilBagWeight()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(BAGWEIGHT$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(BAGWEIGHT$8);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "bagWeight" element
     */
    public void unsetBagWeight()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(BAGWEIGHT$8, 0);
        }
    }
    
    /**
     * Gets the "baggageReroutedEnRoute" element
     */
    public int getBaggageReroutedEnRoute()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(BAGGAGEREROUTEDENROUTE$10, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "baggageReroutedEnRoute" element
     */
    public org.apache.xmlbeans.XmlInt xgetBaggageReroutedEnRoute()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(BAGGAGEREROUTEDENROUTE$10, 0);
            return target;
        }
    }
    
    /**
     * True if has "baggageReroutedEnRoute" element
     */
    public boolean isSetBaggageReroutedEnRoute()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(BAGGAGEREROUTEDENROUTE$10) != 0;
        }
    }
    
    /**
     * Sets the "baggageReroutedEnRoute" element
     */
    public void setBaggageReroutedEnRoute(int baggageReroutedEnRoute)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(BAGGAGEREROUTEDENROUTE$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(BAGGAGEREROUTEDENROUTE$10);
            }
            target.setIntValue(baggageReroutedEnRoute);
        }
    }
    
    /**
     * Sets (as xml) the "baggageReroutedEnRoute" element
     */
    public void xsetBaggageReroutedEnRoute(org.apache.xmlbeans.XmlInt baggageReroutedEnRoute)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(BAGGAGEREROUTEDENROUTE$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(BAGGAGEREROUTEDENROUTE$10);
            }
            target.set(baggageReroutedEnRoute);
        }
    }
    
    /**
     * Unsets the "baggageReroutedEnRoute" element
     */
    public void unsetBaggageReroutedEnRoute()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(BAGGAGEREROUTEDENROUTE$10, 0);
        }
    }
    
    /**
     * Gets the "bagsDelayed" element
     */
    public int getBagsDelayed()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(BAGSDELAYED$12, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "bagsDelayed" element
     */
    public org.apache.xmlbeans.XmlInt xgetBagsDelayed()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(BAGSDELAYED$12, 0);
            return target;
        }
    }
    
    /**
     * True if has "bagsDelayed" element
     */
    public boolean isSetBagsDelayed()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(BAGSDELAYED$12) != 0;
        }
    }
    
    /**
     * Sets the "bagsDelayed" element
     */
    public void setBagsDelayed(int bagsDelayed)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(BAGSDELAYED$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(BAGSDELAYED$12);
            }
            target.setIntValue(bagsDelayed);
        }
    }
    
    /**
     * Sets (as xml) the "bagsDelayed" element
     */
    public void xsetBagsDelayed(org.apache.xmlbeans.XmlInt bagsDelayed)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(BAGSDELAYED$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(BAGSDELAYED$12);
            }
            target.set(bagsDelayed);
        }
    }
    
    /**
     * Unsets the "bagsDelayed" element
     */
    public void unsetBagsDelayed()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(BAGSDELAYED$12, 0);
        }
    }
    
    /**
     * Gets the "bagsStillMissing" element
     */
    public int getBagsStillMissing()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(BAGSSTILLMISSING$14, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "bagsStillMissing" element
     */
    public org.apache.xmlbeans.XmlInt xgetBagsStillMissing()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(BAGSSTILLMISSING$14, 0);
            return target;
        }
    }
    
    /**
     * True if has "bagsStillMissing" element
     */
    public boolean isSetBagsStillMissing()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(BAGSSTILLMISSING$14) != 0;
        }
    }
    
    /**
     * Sets the "bagsStillMissing" element
     */
    public void setBagsStillMissing(int bagsStillMissing)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(BAGSSTILLMISSING$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(BAGSSTILLMISSING$14);
            }
            target.setIntValue(bagsStillMissing);
        }
    }
    
    /**
     * Sets (as xml) the "bagsStillMissing" element
     */
    public void xsetBagsStillMissing(org.apache.xmlbeans.XmlInt bagsStillMissing)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(BAGSSTILLMISSING$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(BAGSSTILLMISSING$14);
            }
            target.set(bagsStillMissing);
        }
    }
    
    /**
     * Unsets the "bagsStillMissing" element
     */
    public void unsetBagsStillMissing()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(BAGSSTILLMISSING$14, 0);
        }
    }
    
    /**
     * Gets the "bagsTravelWith" element
     */
    public int getBagsTravelWith()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(BAGSTRAVELWITH$16, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "bagsTravelWith" element
     */
    public org.apache.xmlbeans.XmlInt xgetBagsTravelWith()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(BAGSTRAVELWITH$16, 0);
            return target;
        }
    }
    
    /**
     * True if has "bagsTravelWith" element
     */
    public boolean isSetBagsTravelWith()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(BAGSTRAVELWITH$16) != 0;
        }
    }
    
    /**
     * Sets the "bagsTravelWith" element
     */
    public void setBagsTravelWith(int bagsTravelWith)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(BAGSTRAVELWITH$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(BAGSTRAVELWITH$16);
            }
            target.setIntValue(bagsTravelWith);
        }
    }
    
    /**
     * Sets (as xml) the "bagsTravelWith" element
     */
    public void xsetBagsTravelWith(org.apache.xmlbeans.XmlInt bagsTravelWith)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(BAGSTRAVELWITH$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(BAGSTRAVELWITH$16);
            }
            target.set(bagsTravelWith);
        }
    }
    
    /**
     * Unsets the "bagsTravelWith" element
     */
    public void unsetBagsTravelWith()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(BAGSTRAVELWITH$16, 0);
        }
    }
    
    /**
     * Gets the "businessName" element
     */
    public java.lang.String getBusinessName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(BUSINESSNAME$18, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "businessName" element
     */
    public org.apache.xmlbeans.XmlString xgetBusinessName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(BUSINESSNAME$18, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "businessName" element
     */
    public boolean isNilBusinessName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(BUSINESSNAME$18, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "businessName" element
     */
    public boolean isSetBusinessName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(BUSINESSNAME$18) != 0;
        }
    }
    
    /**
     * Sets the "businessName" element
     */
    public void setBusinessName(java.lang.String businessName)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(BUSINESSNAME$18, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(BUSINESSNAME$18);
            }
            target.setStringValue(businessName);
        }
    }
    
    /**
     * Sets (as xml) the "businessName" element
     */
    public void xsetBusinessName(org.apache.xmlbeans.XmlString businessName)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(BUSINESSNAME$18, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(BUSINESSNAME$18);
            }
            target.set(businessName);
        }
    }
    
    /**
     * Nils the "businessName" element
     */
    public void setNilBusinessName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(BUSINESSNAME$18, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(BUSINESSNAME$18);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "businessName" element
     */
    public void unsetBusinessName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(BUSINESSNAME$18, 0);
        }
    }
    
    /**
     * Gets the "chargedForExcessBaggage" element
     */
    public int getChargedForExcessBaggage()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CHARGEDFOREXCESSBAGGAGE$20, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "chargedForExcessBaggage" element
     */
    public org.apache.xmlbeans.XmlInt xgetChargedForExcessBaggage()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(CHARGEDFOREXCESSBAGGAGE$20, 0);
            return target;
        }
    }
    
    /**
     * True if has "chargedForExcessBaggage" element
     */
    public boolean isSetChargedForExcessBaggage()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CHARGEDFOREXCESSBAGGAGE$20) != 0;
        }
    }
    
    /**
     * Sets the "chargedForExcessBaggage" element
     */
    public void setChargedForExcessBaggage(int chargedForExcessBaggage)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CHARGEDFOREXCESSBAGGAGE$20, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CHARGEDFOREXCESSBAGGAGE$20);
            }
            target.setIntValue(chargedForExcessBaggage);
        }
    }
    
    /**
     * Sets (as xml) the "chargedForExcessBaggage" element
     */
    public void xsetChargedForExcessBaggage(org.apache.xmlbeans.XmlInt chargedForExcessBaggage)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(CHARGEDFOREXCESSBAGGAGE$20, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(CHARGEDFOREXCESSBAGGAGE$20);
            }
            target.set(chargedForExcessBaggage);
        }
    }
    
    /**
     * Unsets the "chargedForExcessBaggage" element
     */
    public void unsetChargedForExcessBaggage()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CHARGEDFOREXCESSBAGGAGE$20, 0);
        }
    }
    
    /**
     * Gets the "checkedLocation" element
     */
    public java.lang.String getCheckedLocation()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CHECKEDLOCATION$22, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "checkedLocation" element
     */
    public org.apache.xmlbeans.XmlString xgetCheckedLocation()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CHECKEDLOCATION$22, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "checkedLocation" element
     */
    public boolean isNilCheckedLocation()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CHECKEDLOCATION$22, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "checkedLocation" element
     */
    public boolean isSetCheckedLocation()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CHECKEDLOCATION$22) != 0;
        }
    }
    
    /**
     * Sets the "checkedLocation" element
     */
    public void setCheckedLocation(java.lang.String checkedLocation)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CHECKEDLOCATION$22, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CHECKEDLOCATION$22);
            }
            target.setStringValue(checkedLocation);
        }
    }
    
    /**
     * Sets (as xml) the "checkedLocation" element
     */
    public void xsetCheckedLocation(org.apache.xmlbeans.XmlString checkedLocation)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CHECKEDLOCATION$22, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CHECKEDLOCATION$22);
            }
            target.set(checkedLocation);
        }
    }
    
    /**
     * Nils the "checkedLocation" element
     */
    public void setNilCheckedLocation()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CHECKEDLOCATION$22, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CHECKEDLOCATION$22);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "checkedLocation" element
     */
    public void unsetCheckedLocation()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CHECKEDLOCATION$22, 0);
        }
    }
    
    /**
     * Gets the "checkedLocationDescription" element
     */
    public java.lang.String getCheckedLocationDescription()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CHECKEDLOCATIONDESCRIPTION$24, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "checkedLocationDescription" element
     */
    public org.apache.xmlbeans.XmlString xgetCheckedLocationDescription()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CHECKEDLOCATIONDESCRIPTION$24, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "checkedLocationDescription" element
     */
    public boolean isNilCheckedLocationDescription()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CHECKEDLOCATIONDESCRIPTION$24, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "checkedLocationDescription" element
     */
    public boolean isSetCheckedLocationDescription()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CHECKEDLOCATIONDESCRIPTION$24) != 0;
        }
    }
    
    /**
     * Sets the "checkedLocationDescription" element
     */
    public void setCheckedLocationDescription(java.lang.String checkedLocationDescription)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CHECKEDLOCATIONDESCRIPTION$24, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CHECKEDLOCATIONDESCRIPTION$24);
            }
            target.setStringValue(checkedLocationDescription);
        }
    }
    
    /**
     * Sets (as xml) the "checkedLocationDescription" element
     */
    public void xsetCheckedLocationDescription(org.apache.xmlbeans.XmlString checkedLocationDescription)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CHECKEDLOCATIONDESCRIPTION$24, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CHECKEDLOCATIONDESCRIPTION$24);
            }
            target.set(checkedLocationDescription);
        }
    }
    
    /**
     * Nils the "checkedLocationDescription" element
     */
    public void setNilCheckedLocationDescription()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CHECKEDLOCATIONDESCRIPTION$24, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CHECKEDLOCATIONDESCRIPTION$24);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "checkedLocationDescription" element
     */
    public void unsetCheckedLocationDescription()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CHECKEDLOCATIONDESCRIPTION$24, 0);
        }
    }
    
    /**
     * Gets the "claimId" element
     */
    public long getClaimId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CLAIMID$26, 0);
            if (target == null)
            {
                return 0L;
            }
            return target.getLongValue();
        }
    }
    
    /**
     * Gets (as xml) the "claimId" element
     */
    public org.apache.xmlbeans.XmlLong xgetClaimId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlLong target = null;
            target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(CLAIMID$26, 0);
            return target;
        }
    }
    
    /**
     * True if has "claimId" element
     */
    public boolean isSetClaimId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CLAIMID$26) != 0;
        }
    }
    
    /**
     * Sets the "claimId" element
     */
    public void setClaimId(long claimId)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CLAIMID$26, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CLAIMID$26);
            }
            target.setLongValue(claimId);
        }
    }
    
    /**
     * Sets (as xml) the "claimId" element
     */
    public void xsetClaimId(org.apache.xmlbeans.XmlLong claimId)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlLong target = null;
            target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(CLAIMID$26, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlLong)get_store().add_element_user(CLAIMID$26);
            }
            target.set(claimId);
        }
    }
    
    /**
     * Unsets the "claimId" element
     */
    public void unsetClaimId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CLAIMID$26, 0);
        }
    }
    
    /**
     * Gets the "claimStatus" element
     */
    public java.lang.String getClaimStatus()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CLAIMSTATUS$28, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "claimStatus" element
     */
    public org.apache.xmlbeans.XmlString xgetClaimStatus()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CLAIMSTATUS$28, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "claimStatus" element
     */
    public boolean isNilClaimStatus()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CLAIMSTATUS$28, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "claimStatus" element
     */
    public boolean isSetClaimStatus()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CLAIMSTATUS$28) != 0;
        }
    }
    
    /**
     * Sets the "claimStatus" element
     */
    public void setClaimStatus(java.lang.String claimStatus)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CLAIMSTATUS$28, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CLAIMSTATUS$28);
            }
            target.setStringValue(claimStatus);
        }
    }
    
    /**
     * Sets (as xml) the "claimStatus" element
     */
    public void xsetClaimStatus(org.apache.xmlbeans.XmlString claimStatus)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CLAIMSTATUS$28, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CLAIMSTATUS$28);
            }
            target.set(claimStatus);
        }
    }
    
    /**
     * Nils the "claimStatus" element
     */
    public void setNilClaimStatus()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CLAIMSTATUS$28, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CLAIMSTATUS$28);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "claimStatus" element
     */
    public void unsetClaimStatus()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CLAIMSTATUS$28, 0);
        }
    }
    
    /**
     * Gets the "claimType" element
     */
    public int getClaimType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CLAIMTYPE$30, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "claimType" element
     */
    public org.apache.xmlbeans.XmlInt xgetClaimType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(CLAIMTYPE$30, 0);
            return target;
        }
    }
    
    /**
     * True if has "claimType" element
     */
    public boolean isSetClaimType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CLAIMTYPE$30) != 0;
        }
    }
    
    /**
     * Sets the "claimType" element
     */
    public void setClaimType(int claimType)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CLAIMTYPE$30, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CLAIMTYPE$30);
            }
            target.setIntValue(claimType);
        }
    }
    
    /**
     * Sets (as xml) the "claimType" element
     */
    public void xsetClaimType(org.apache.xmlbeans.XmlInt claimType)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(CLAIMTYPE$30, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(CLAIMTYPE$30);
            }
            target.set(claimType);
        }
    }
    
    /**
     * Unsets the "claimType" element
     */
    public void unsetClaimType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CLAIMTYPE$30, 0);
        }
    }
    
    /**
     * Gets the "comments" element
     */
    public java.lang.String getComments()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COMMENTS$32, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "comments" element
     */
    public org.apache.xmlbeans.XmlString xgetComments()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COMMENTS$32, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "comments" element
     */
    public boolean isNilComments()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COMMENTS$32, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "comments" element
     */
    public boolean isSetComments()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(COMMENTS$32) != 0;
        }
    }
    
    /**
     * Sets the "comments" element
     */
    public void setComments(java.lang.String comments)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COMMENTS$32, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(COMMENTS$32);
            }
            target.setStringValue(comments);
        }
    }
    
    /**
     * Sets (as xml) the "comments" element
     */
    public void xsetComments(org.apache.xmlbeans.XmlString comments)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COMMENTS$32, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(COMMENTS$32);
            }
            target.set(comments);
        }
    }
    
    /**
     * Nils the "comments" element
     */
    public void setNilComments()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COMMENTS$32, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(COMMENTS$32);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "comments" element
     */
    public void unsetComments()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(COMMENTS$32, 0);
        }
    }
    
    /**
     * Gets the "declaredCurrency" element
     */
    public java.lang.String getDeclaredCurrency()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DECLAREDCURRENCY$34, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "declaredCurrency" element
     */
    public org.apache.xmlbeans.XmlString xgetDeclaredCurrency()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DECLAREDCURRENCY$34, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "declaredCurrency" element
     */
    public boolean isNilDeclaredCurrency()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DECLAREDCURRENCY$34, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "declaredCurrency" element
     */
    public boolean isSetDeclaredCurrency()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DECLAREDCURRENCY$34) != 0;
        }
    }
    
    /**
     * Sets the "declaredCurrency" element
     */
    public void setDeclaredCurrency(java.lang.String declaredCurrency)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DECLAREDCURRENCY$34, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DECLAREDCURRENCY$34);
            }
            target.setStringValue(declaredCurrency);
        }
    }
    
    /**
     * Sets (as xml) the "declaredCurrency" element
     */
    public void xsetDeclaredCurrency(org.apache.xmlbeans.XmlString declaredCurrency)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DECLAREDCURRENCY$34, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DECLAREDCURRENCY$34);
            }
            target.set(declaredCurrency);
        }
    }
    
    /**
     * Nils the "declaredCurrency" element
     */
    public void setNilDeclaredCurrency()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DECLAREDCURRENCY$34, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DECLAREDCURRENCY$34);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "declaredCurrency" element
     */
    public void unsetDeclaredCurrency()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DECLAREDCURRENCY$34, 0);
        }
    }
    
    /**
     * Gets the "declaredExcessValue" element
     */
    public int getDeclaredExcessValue()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DECLAREDEXCESSVALUE$36, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "declaredExcessValue" element
     */
    public org.apache.xmlbeans.XmlInt xgetDeclaredExcessValue()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(DECLAREDEXCESSVALUE$36, 0);
            return target;
        }
    }
    
    /**
     * True if has "declaredExcessValue" element
     */
    public boolean isSetDeclaredExcessValue()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DECLAREDEXCESSVALUE$36) != 0;
        }
    }
    
    /**
     * Sets the "declaredExcessValue" element
     */
    public void setDeclaredExcessValue(int declaredExcessValue)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DECLAREDEXCESSVALUE$36, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DECLAREDEXCESSVALUE$36);
            }
            target.setIntValue(declaredExcessValue);
        }
    }
    
    /**
     * Sets (as xml) the "declaredExcessValue" element
     */
    public void xsetDeclaredExcessValue(org.apache.xmlbeans.XmlInt declaredExcessValue)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(DECLAREDEXCESSVALUE$36, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(DECLAREDEXCESSVALUE$36);
            }
            target.set(declaredExcessValue);
        }
    }
    
    /**
     * Unsets the "declaredExcessValue" element
     */
    public void unsetDeclaredExcessValue()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DECLAREDEXCESSVALUE$36, 0);
        }
    }
    
    /**
     * Gets the "declaredValue" element
     */
    public java.lang.String getDeclaredValue()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DECLAREDVALUE$38, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "declaredValue" element
     */
    public org.apache.xmlbeans.XmlString xgetDeclaredValue()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DECLAREDVALUE$38, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "declaredValue" element
     */
    public boolean isNilDeclaredValue()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DECLAREDVALUE$38, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "declaredValue" element
     */
    public boolean isSetDeclaredValue()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DECLAREDVALUE$38) != 0;
        }
    }
    
    /**
     * Sets the "declaredValue" element
     */
    public void setDeclaredValue(java.lang.String declaredValue)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DECLAREDVALUE$38, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DECLAREDVALUE$38);
            }
            target.setStringValue(declaredValue);
        }
    }
    
    /**
     * Sets (as xml) the "declaredValue" element
     */
    public void xsetDeclaredValue(org.apache.xmlbeans.XmlString declaredValue)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DECLAREDVALUE$38, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DECLAREDVALUE$38);
            }
            target.set(declaredValue);
        }
    }
    
    /**
     * Nils the "declaredValue" element
     */
    public void setNilDeclaredValue()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DECLAREDVALUE$38, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DECLAREDVALUE$38);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "declaredValue" element
     */
    public void unsetDeclaredValue()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DECLAREDVALUE$38, 0);
        }
    }
    
    /**
     * Gets the "differentClaimCheck" element
     */
    public int getDifferentClaimCheck()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DIFFERENTCLAIMCHECK$40, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "differentClaimCheck" element
     */
    public org.apache.xmlbeans.XmlInt xgetDifferentClaimCheck()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(DIFFERENTCLAIMCHECK$40, 0);
            return target;
        }
    }
    
    /**
     * True if has "differentClaimCheck" element
     */
    public boolean isSetDifferentClaimCheck()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DIFFERENTCLAIMCHECK$40) != 0;
        }
    }
    
    /**
     * Sets the "differentClaimCheck" element
     */
    public void setDifferentClaimCheck(int differentClaimCheck)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DIFFERENTCLAIMCHECK$40, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DIFFERENTCLAIMCHECK$40);
            }
            target.setIntValue(differentClaimCheck);
        }
    }
    
    /**
     * Sets (as xml) the "differentClaimCheck" element
     */
    public void xsetDifferentClaimCheck(org.apache.xmlbeans.XmlInt differentClaimCheck)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(DIFFERENTCLAIMCHECK$40, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(DIFFERENTCLAIMCHECK$40);
            }
            target.set(differentClaimCheck);
        }
    }
    
    /**
     * Unsets the "differentClaimCheck" element
     */
    public void unsetDifferentClaimCheck()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DIFFERENTCLAIMCHECK$40, 0);
        }
    }
    
    /**
     * Gets the "emailAddress" element
     */
    public java.lang.String getEmailAddress()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EMAILADDRESS$42, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "emailAddress" element
     */
    public org.apache.xmlbeans.XmlString xgetEmailAddress()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(EMAILADDRESS$42, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "emailAddress" element
     */
    public boolean isNilEmailAddress()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(EMAILADDRESS$42, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "emailAddress" element
     */
    public boolean isSetEmailAddress()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(EMAILADDRESS$42) != 0;
        }
    }
    
    /**
     * Sets the "emailAddress" element
     */
    public void setEmailAddress(java.lang.String emailAddress)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EMAILADDRESS$42, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(EMAILADDRESS$42);
            }
            target.setStringValue(emailAddress);
        }
    }
    
    /**
     * Sets (as xml) the "emailAddress" element
     */
    public void xsetEmailAddress(org.apache.xmlbeans.XmlString emailAddress)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(EMAILADDRESS$42, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(EMAILADDRESS$42);
            }
            target.set(emailAddress);
        }
    }
    
    /**
     * Nils the "emailAddress" element
     */
    public void setNilEmailAddress()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(EMAILADDRESS$42, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(EMAILADDRESS$42);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "emailAddress" element
     */
    public void unsetEmailAddress()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(EMAILADDRESS$42, 0);
        }
    }
    
    /**
     * Gets array of all "file" elements
     */
    public com.bagnet.nettracer.ws.onlineclaims.xsd.File[] getFileArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(FILE$44, targetList);
            com.bagnet.nettracer.ws.onlineclaims.xsd.File[] result = new com.bagnet.nettracer.ws.onlineclaims.xsd.File[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "file" element
     */
    public com.bagnet.nettracer.ws.onlineclaims.xsd.File getFileArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.xsd.File target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.xsd.File)get_store().find_element_user(FILE$44, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Tests for nil ith "file" element
     */
    public boolean isNilFileArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.xsd.File target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.xsd.File)get_store().find_element_user(FILE$44, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target.isNil();
        }
    }
    
    /**
     * Returns number of "file" element
     */
    public int sizeOfFileArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(FILE$44);
        }
    }
    
    /**
     * Sets array of all "file" element  WARNING: This method is not atomicaly synchronized.
     */
    public void setFileArray(com.bagnet.nettracer.ws.onlineclaims.xsd.File[] fileArray)
    {
        check_orphaned();
        arraySetterHelper(fileArray, FILE$44);
    }
    
    /**
     * Sets ith "file" element
     */
    public void setFileArray(int i, com.bagnet.nettracer.ws.onlineclaims.xsd.File file)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.xsd.File target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.xsd.File)get_store().find_element_user(FILE$44, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(file);
        }
    }
    
    /**
     * Nils the ith "file" element
     */
    public void setNilFileArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.xsd.File target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.xsd.File)get_store().find_element_user(FILE$44, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.setNil();
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "file" element
     */
    public com.bagnet.nettracer.ws.onlineclaims.xsd.File insertNewFile(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.xsd.File target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.xsd.File)get_store().insert_element_user(FILE$44, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "file" element
     */
    public com.bagnet.nettracer.ws.onlineclaims.xsd.File addNewFile()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.xsd.File target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.xsd.File)get_store().add_element_user(FILE$44);
            return target;
        }
    }
    
    /**
     * Removes the ith "file" element
     */
    public void removeFile(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(FILE$44, i);
        }
    }
    
    /**
     * Gets the "filedPreviousAirline" element
     */
    public java.lang.String getFiledPreviousAirline()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FILEDPREVIOUSAIRLINE$46, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "filedPreviousAirline" element
     */
    public org.apache.xmlbeans.XmlString xgetFiledPreviousAirline()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FILEDPREVIOUSAIRLINE$46, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "filedPreviousAirline" element
     */
    public boolean isNilFiledPreviousAirline()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FILEDPREVIOUSAIRLINE$46, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "filedPreviousAirline" element
     */
    public boolean isSetFiledPreviousAirline()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(FILEDPREVIOUSAIRLINE$46) != 0;
        }
    }
    
    /**
     * Sets the "filedPreviousAirline" element
     */
    public void setFiledPreviousAirline(java.lang.String filedPreviousAirline)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FILEDPREVIOUSAIRLINE$46, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FILEDPREVIOUSAIRLINE$46);
            }
            target.setStringValue(filedPreviousAirline);
        }
    }
    
    /**
     * Sets (as xml) the "filedPreviousAirline" element
     */
    public void xsetFiledPreviousAirline(org.apache.xmlbeans.XmlString filedPreviousAirline)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FILEDPREVIOUSAIRLINE$46, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(FILEDPREVIOUSAIRLINE$46);
            }
            target.set(filedPreviousAirline);
        }
    }
    
    /**
     * Nils the "filedPreviousAirline" element
     */
    public void setNilFiledPreviousAirline()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FILEDPREVIOUSAIRLINE$46, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(FILEDPREVIOUSAIRLINE$46);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "filedPreviousAirline" element
     */
    public void unsetFiledPreviousAirline()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(FILEDPREVIOUSAIRLINE$46, 0);
        }
    }
    
    /**
     * Gets the "filedPreviousClaim" element
     */
    public int getFiledPreviousClaim()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FILEDPREVIOUSCLAIM$48, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "filedPreviousClaim" element
     */
    public org.apache.xmlbeans.XmlInt xgetFiledPreviousClaim()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(FILEDPREVIOUSCLAIM$48, 0);
            return target;
        }
    }
    
    /**
     * True if has "filedPreviousClaim" element
     */
    public boolean isSetFiledPreviousClaim()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(FILEDPREVIOUSCLAIM$48) != 0;
        }
    }
    
    /**
     * Sets the "filedPreviousClaim" element
     */
    public void setFiledPreviousClaim(int filedPreviousClaim)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FILEDPREVIOUSCLAIM$48, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FILEDPREVIOUSCLAIM$48);
            }
            target.setIntValue(filedPreviousClaim);
        }
    }
    
    /**
     * Sets (as xml) the "filedPreviousClaim" element
     */
    public void xsetFiledPreviousClaim(org.apache.xmlbeans.XmlInt filedPreviousClaim)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(FILEDPREVIOUSCLAIM$48, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(FILEDPREVIOUSCLAIM$48);
            }
            target.set(filedPreviousClaim);
        }
    }
    
    /**
     * Unsets the "filedPreviousClaim" element
     */
    public void unsetFiledPreviousClaim()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(FILEDPREVIOUSCLAIM$48, 0);
        }
    }
    
    /**
     * Gets the "filedPreviousClaimant" element
     */
    public java.lang.String getFiledPreviousClaimant()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FILEDPREVIOUSCLAIMANT$50, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "filedPreviousClaimant" element
     */
    public org.apache.xmlbeans.XmlString xgetFiledPreviousClaimant()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FILEDPREVIOUSCLAIMANT$50, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "filedPreviousClaimant" element
     */
    public boolean isNilFiledPreviousClaimant()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FILEDPREVIOUSCLAIMANT$50, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "filedPreviousClaimant" element
     */
    public boolean isSetFiledPreviousClaimant()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(FILEDPREVIOUSCLAIMANT$50) != 0;
        }
    }
    
    /**
     * Sets the "filedPreviousClaimant" element
     */
    public void setFiledPreviousClaimant(java.lang.String filedPreviousClaimant)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FILEDPREVIOUSCLAIMANT$50, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FILEDPREVIOUSCLAIMANT$50);
            }
            target.setStringValue(filedPreviousClaimant);
        }
    }
    
    /**
     * Sets (as xml) the "filedPreviousClaimant" element
     */
    public void xsetFiledPreviousClaimant(org.apache.xmlbeans.XmlString filedPreviousClaimant)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FILEDPREVIOUSCLAIMANT$50, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(FILEDPREVIOUSCLAIMANT$50);
            }
            target.set(filedPreviousClaimant);
        }
    }
    
    /**
     * Nils the "filedPreviousClaimant" element
     */
    public void setNilFiledPreviousClaimant()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FILEDPREVIOUSCLAIMANT$50, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(FILEDPREVIOUSCLAIMANT$50);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "filedPreviousClaimant" element
     */
    public void unsetFiledPreviousClaimant()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(FILEDPREVIOUSCLAIMANT$50, 0);
        }
    }
    
    /**
     * Gets the "filedPrevoiusDate" element
     */
    public java.util.Calendar getFiledPrevoiusDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FILEDPREVOIUSDATE$52, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "filedPrevoiusDate" element
     */
    public org.apache.xmlbeans.XmlDate xgetFiledPrevoiusDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDate target = null;
            target = (org.apache.xmlbeans.XmlDate)get_store().find_element_user(FILEDPREVOIUSDATE$52, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "filedPrevoiusDate" element
     */
    public boolean isNilFiledPrevoiusDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDate target = null;
            target = (org.apache.xmlbeans.XmlDate)get_store().find_element_user(FILEDPREVOIUSDATE$52, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "filedPrevoiusDate" element
     */
    public boolean isSetFiledPrevoiusDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(FILEDPREVOIUSDATE$52) != 0;
        }
    }
    
    /**
     * Sets the "filedPrevoiusDate" element
     */
    public void setFiledPrevoiusDate(java.util.Calendar filedPrevoiusDate)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FILEDPREVOIUSDATE$52, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FILEDPREVOIUSDATE$52);
            }
            target.setCalendarValue(filedPrevoiusDate);
        }
    }
    
    /**
     * Sets (as xml) the "filedPrevoiusDate" element
     */
    public void xsetFiledPrevoiusDate(org.apache.xmlbeans.XmlDate filedPrevoiusDate)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDate target = null;
            target = (org.apache.xmlbeans.XmlDate)get_store().find_element_user(FILEDPREVOIUSDATE$52, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDate)get_store().add_element_user(FILEDPREVOIUSDATE$52);
            }
            target.set(filedPrevoiusDate);
        }
    }
    
    /**
     * Nils the "filedPrevoiusDate" element
     */
    public void setNilFiledPrevoiusDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDate target = null;
            target = (org.apache.xmlbeans.XmlDate)get_store().find_element_user(FILEDPREVOIUSDATE$52, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDate)get_store().add_element_user(FILEDPREVOIUSDATE$52);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "filedPrevoiusDate" element
     */
    public void unsetFiledPrevoiusDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(FILEDPREVOIUSDATE$52, 0);
        }
    }
    
    /**
     * Gets the "foreignCurrencyEmail" element
     */
    public java.lang.String getForeignCurrencyEmail()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FOREIGNCURRENCYEMAIL$54, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "foreignCurrencyEmail" element
     */
    public org.apache.xmlbeans.XmlString xgetForeignCurrencyEmail()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FOREIGNCURRENCYEMAIL$54, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "foreignCurrencyEmail" element
     */
    public boolean isNilForeignCurrencyEmail()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FOREIGNCURRENCYEMAIL$54, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "foreignCurrencyEmail" element
     */
    public boolean isSetForeignCurrencyEmail()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(FOREIGNCURRENCYEMAIL$54) != 0;
        }
    }
    
    /**
     * Sets the "foreignCurrencyEmail" element
     */
    public void setForeignCurrencyEmail(java.lang.String foreignCurrencyEmail)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FOREIGNCURRENCYEMAIL$54, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FOREIGNCURRENCYEMAIL$54);
            }
            target.setStringValue(foreignCurrencyEmail);
        }
    }
    
    /**
     * Sets (as xml) the "foreignCurrencyEmail" element
     */
    public void xsetForeignCurrencyEmail(org.apache.xmlbeans.XmlString foreignCurrencyEmail)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FOREIGNCURRENCYEMAIL$54, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(FOREIGNCURRENCYEMAIL$54);
            }
            target.set(foreignCurrencyEmail);
        }
    }
    
    /**
     * Nils the "foreignCurrencyEmail" element
     */
    public void setNilForeignCurrencyEmail()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FOREIGNCURRENCYEMAIL$54, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(FOREIGNCURRENCYEMAIL$54);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "foreignCurrencyEmail" element
     */
    public void unsetForeignCurrencyEmail()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(FOREIGNCURRENCYEMAIL$54, 0);
        }
    }
    
    /**
     * Gets the "frequentFlierNumber" element
     */
    public java.lang.String getFrequentFlierNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FREQUENTFLIERNUMBER$56, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "frequentFlierNumber" element
     */
    public org.apache.xmlbeans.XmlString xgetFrequentFlierNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FREQUENTFLIERNUMBER$56, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "frequentFlierNumber" element
     */
    public boolean isNilFrequentFlierNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FREQUENTFLIERNUMBER$56, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "frequentFlierNumber" element
     */
    public boolean isSetFrequentFlierNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(FREQUENTFLIERNUMBER$56) != 0;
        }
    }
    
    /**
     * Sets the "frequentFlierNumber" element
     */
    public void setFrequentFlierNumber(java.lang.String frequentFlierNumber)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FREQUENTFLIERNUMBER$56, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FREQUENTFLIERNUMBER$56);
            }
            target.setStringValue(frequentFlierNumber);
        }
    }
    
    /**
     * Sets (as xml) the "frequentFlierNumber" element
     */
    public void xsetFrequentFlierNumber(org.apache.xmlbeans.XmlString frequentFlierNumber)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FREQUENTFLIERNUMBER$56, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(FREQUENTFLIERNUMBER$56);
            }
            target.set(frequentFlierNumber);
        }
    }
    
    /**
     * Nils the "frequentFlierNumber" element
     */
    public void setNilFrequentFlierNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FREQUENTFLIERNUMBER$56, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(FREQUENTFLIERNUMBER$56);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "frequentFlierNumber" element
     */
    public void unsetFrequentFlierNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(FREQUENTFLIERNUMBER$56, 0);
        }
    }
    
    /**
     * Gets the "haveToRecheck" element
     */
    public int getHaveToRecheck()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(HAVETORECHECK$58, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "haveToRecheck" element
     */
    public org.apache.xmlbeans.XmlInt xgetHaveToRecheck()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(HAVETORECHECK$58, 0);
            return target;
        }
    }
    
    /**
     * True if has "haveToRecheck" element
     */
    public boolean isSetHaveToRecheck()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(HAVETORECHECK$58) != 0;
        }
    }
    
    /**
     * Sets the "haveToRecheck" element
     */
    public void setHaveToRecheck(int haveToRecheck)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(HAVETORECHECK$58, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(HAVETORECHECK$58);
            }
            target.setIntValue(haveToRecheck);
        }
    }
    
    /**
     * Sets (as xml) the "haveToRecheck" element
     */
    public void xsetHaveToRecheck(org.apache.xmlbeans.XmlInt haveToRecheck)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(HAVETORECHECK$58, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(HAVETORECHECK$58);
            }
            target.set(haveToRecheck);
        }
    }
    
    /**
     * Unsets the "haveToRecheck" element
     */
    public void unsetHaveToRecheck()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(HAVETORECHECK$58, 0);
        }
    }
    
    /**
     * Gets array of all "itinerary" elements
     */
    public com.bagnet.nettracer.ws.onlineclaims.xsd.Itinerary[] getItineraryArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(ITINERARY$60, targetList);
            com.bagnet.nettracer.ws.onlineclaims.xsd.Itinerary[] result = new com.bagnet.nettracer.ws.onlineclaims.xsd.Itinerary[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "itinerary" element
     */
    public com.bagnet.nettracer.ws.onlineclaims.xsd.Itinerary getItineraryArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.xsd.Itinerary target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.xsd.Itinerary)get_store().find_element_user(ITINERARY$60, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Tests for nil ith "itinerary" element
     */
    public boolean isNilItineraryArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.xsd.Itinerary target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.xsd.Itinerary)get_store().find_element_user(ITINERARY$60, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target.isNil();
        }
    }
    
    /**
     * Returns number of "itinerary" element
     */
    public int sizeOfItineraryArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(ITINERARY$60);
        }
    }
    
    /**
     * Sets array of all "itinerary" element  WARNING: This method is not atomicaly synchronized.
     */
    public void setItineraryArray(com.bagnet.nettracer.ws.onlineclaims.xsd.Itinerary[] itineraryArray)
    {
        check_orphaned();
        arraySetterHelper(itineraryArray, ITINERARY$60);
    }
    
    /**
     * Sets ith "itinerary" element
     */
    public void setItineraryArray(int i, com.bagnet.nettracer.ws.onlineclaims.xsd.Itinerary itinerary)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.xsd.Itinerary target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.xsd.Itinerary)get_store().find_element_user(ITINERARY$60, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(itinerary);
        }
    }
    
    /**
     * Nils the ith "itinerary" element
     */
    public void setNilItineraryArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.xsd.Itinerary target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.xsd.Itinerary)get_store().find_element_user(ITINERARY$60, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.setNil();
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "itinerary" element
     */
    public com.bagnet.nettracer.ws.onlineclaims.xsd.Itinerary insertNewItinerary(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.xsd.Itinerary target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.xsd.Itinerary)get_store().insert_element_user(ITINERARY$60, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "itinerary" element
     */
    public com.bagnet.nettracer.ws.onlineclaims.xsd.Itinerary addNewItinerary()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.xsd.Itinerary target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.xsd.Itinerary)get_store().add_element_user(ITINERARY$60);
            return target;
        }
    }
    
    /**
     * Removes the ith "itinerary" element
     */
    public void removeItinerary(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(ITINERARY$60, i);
        }
    }
    
    /**
     * Gets the "lastSawBaggage" element
     */
    public java.lang.String getLastSawBaggage()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LASTSAWBAGGAGE$62, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "lastSawBaggage" element
     */
    public org.apache.xmlbeans.XmlString xgetLastSawBaggage()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(LASTSAWBAGGAGE$62, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "lastSawBaggage" element
     */
    public boolean isNilLastSawBaggage()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(LASTSAWBAGGAGE$62, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "lastSawBaggage" element
     */
    public boolean isSetLastSawBaggage()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(LASTSAWBAGGAGE$62) != 0;
        }
    }
    
    /**
     * Sets the "lastSawBaggage" element
     */
    public void setLastSawBaggage(java.lang.String lastSawBaggage)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LASTSAWBAGGAGE$62, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(LASTSAWBAGGAGE$62);
            }
            target.setStringValue(lastSawBaggage);
        }
    }
    
    /**
     * Sets (as xml) the "lastSawBaggage" element
     */
    public void xsetLastSawBaggage(org.apache.xmlbeans.XmlString lastSawBaggage)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(LASTSAWBAGGAGE$62, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(LASTSAWBAGGAGE$62);
            }
            target.set(lastSawBaggage);
        }
    }
    
    /**
     * Nils the "lastSawBaggage" element
     */
    public void setNilLastSawBaggage()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(LASTSAWBAGGAGE$62, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(LASTSAWBAGGAGE$62);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "lastSawBaggage" element
     */
    public void unsetLastSawBaggage()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(LASTSAWBAGGAGE$62, 0);
        }
    }
    
    /**
     * Gets the "lengthOfStay" element
     */
    public java.lang.String getLengthOfStay()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LENGTHOFSTAY$64, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "lengthOfStay" element
     */
    public org.apache.xmlbeans.XmlString xgetLengthOfStay()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(LENGTHOFSTAY$64, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "lengthOfStay" element
     */
    public boolean isNilLengthOfStay()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(LENGTHOFSTAY$64, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "lengthOfStay" element
     */
    public boolean isSetLengthOfStay()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(LENGTHOFSTAY$64) != 0;
        }
    }
    
    /**
     * Sets the "lengthOfStay" element
     */
    public void setLengthOfStay(java.lang.String lengthOfStay)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LENGTHOFSTAY$64, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(LENGTHOFSTAY$64);
            }
            target.setStringValue(lengthOfStay);
        }
    }
    
    /**
     * Sets (as xml) the "lengthOfStay" element
     */
    public void xsetLengthOfStay(org.apache.xmlbeans.XmlString lengthOfStay)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(LENGTHOFSTAY$64, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(LENGTHOFSTAY$64);
            }
            target.set(lengthOfStay);
        }
    }
    
    /**
     * Nils the "lengthOfStay" element
     */
    public void setNilLengthOfStay()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(LENGTHOFSTAY$64, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(LENGTHOFSTAY$64);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "lengthOfStay" element
     */
    public void unsetLengthOfStay()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(LENGTHOFSTAY$64, 0);
        }
    }
    
    /**
     * Gets the "mailingAddress" element
     */
    public com.bagnet.nettracer.ws.onlineclaims.xsd.Address getMailingAddress()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.xsd.Address target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.xsd.Address)get_store().find_element_user(MAILINGADDRESS$66, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Tests for nil "mailingAddress" element
     */
    public boolean isNilMailingAddress()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.xsd.Address target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.xsd.Address)get_store().find_element_user(MAILINGADDRESS$66, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "mailingAddress" element
     */
    public boolean isSetMailingAddress()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(MAILINGADDRESS$66) != 0;
        }
    }
    
    /**
     * Sets the "mailingAddress" element
     */
    public void setMailingAddress(com.bagnet.nettracer.ws.onlineclaims.xsd.Address mailingAddress)
    {
        generatedSetterHelperImpl(mailingAddress, MAILINGADDRESS$66, 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
    }
    
    /**
     * Appends and returns a new empty "mailingAddress" element
     */
    public com.bagnet.nettracer.ws.onlineclaims.xsd.Address addNewMailingAddress()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.xsd.Address target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.xsd.Address)get_store().add_element_user(MAILINGADDRESS$66);
            return target;
        }
    }
    
    /**
     * Nils the "mailingAddress" element
     */
    public void setNilMailingAddress()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.xsd.Address target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.xsd.Address)get_store().find_element_user(MAILINGADDRESS$66, 0);
            if (target == null)
            {
                target = (com.bagnet.nettracer.ws.onlineclaims.xsd.Address)get_store().add_element_user(MAILINGADDRESS$66);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "mailingAddress" element
     */
    public void unsetMailingAddress()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(MAILINGADDRESS$66, 0);
        }
    }
    
    /**
     * Gets array of all "messages" elements
     */
    public com.bagnet.nettracer.ws.onlineclaims.xsd.Message[] getMessagesArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(MESSAGES$68, targetList);
            com.bagnet.nettracer.ws.onlineclaims.xsd.Message[] result = new com.bagnet.nettracer.ws.onlineclaims.xsd.Message[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "messages" element
     */
    public com.bagnet.nettracer.ws.onlineclaims.xsd.Message getMessagesArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.xsd.Message target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.xsd.Message)get_store().find_element_user(MESSAGES$68, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Tests for nil ith "messages" element
     */
    public boolean isNilMessagesArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.xsd.Message target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.xsd.Message)get_store().find_element_user(MESSAGES$68, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target.isNil();
        }
    }
    
    /**
     * Returns number of "messages" element
     */
    public int sizeOfMessagesArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(MESSAGES$68);
        }
    }
    
    /**
     * Sets array of all "messages" element  WARNING: This method is not atomicaly synchronized.
     */
    public void setMessagesArray(com.bagnet.nettracer.ws.onlineclaims.xsd.Message[] messagesArray)
    {
        check_orphaned();
        arraySetterHelper(messagesArray, MESSAGES$68);
    }
    
    /**
     * Sets ith "messages" element
     */
    public void setMessagesArray(int i, com.bagnet.nettracer.ws.onlineclaims.xsd.Message messages)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.xsd.Message target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.xsd.Message)get_store().find_element_user(MESSAGES$68, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(messages);
        }
    }
    
    /**
     * Nils the ith "messages" element
     */
    public void setNilMessagesArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.xsd.Message target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.xsd.Message)get_store().find_element_user(MESSAGES$68, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.setNil();
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "messages" element
     */
    public com.bagnet.nettracer.ws.onlineclaims.xsd.Message insertNewMessages(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.xsd.Message target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.xsd.Message)get_store().insert_element_user(MESSAGES$68, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "messages" element
     */
    public com.bagnet.nettracer.ws.onlineclaims.xsd.Message addNewMessages()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.xsd.Message target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.xsd.Message)get_store().add_element_user(MESSAGES$68);
            return target;
        }
    }
    
    /**
     * Removes the ith "messages" element
     */
    public void removeMessages(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(MESSAGES$68, i);
        }
    }
    
    /**
     * Gets the "occupation" element
     */
    public java.lang.String getOccupation()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(OCCUPATION$70, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "occupation" element
     */
    public org.apache.xmlbeans.XmlString xgetOccupation()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(OCCUPATION$70, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "occupation" element
     */
    public boolean isNilOccupation()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(OCCUPATION$70, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "occupation" element
     */
    public boolean isSetOccupation()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(OCCUPATION$70) != 0;
        }
    }
    
    /**
     * Sets the "occupation" element
     */
    public void setOccupation(java.lang.String occupation)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(OCCUPATION$70, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(OCCUPATION$70);
            }
            target.setStringValue(occupation);
        }
    }
    
    /**
     * Sets (as xml) the "occupation" element
     */
    public void xsetOccupation(org.apache.xmlbeans.XmlString occupation)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(OCCUPATION$70, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(OCCUPATION$70);
            }
            target.set(occupation);
        }
    }
    
    /**
     * Nils the "occupation" element
     */
    public void setNilOccupation()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(OCCUPATION$70, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(OCCUPATION$70);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "occupation" element
     */
    public void unsetOccupation()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(OCCUPATION$70, 0);
        }
    }
    
    /**
     * Gets array of all "passenger" elements
     */
    public com.bagnet.nettracer.ws.onlineclaims.xsd.Passenger[] getPassengerArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(PASSENGER$72, targetList);
            com.bagnet.nettracer.ws.onlineclaims.xsd.Passenger[] result = new com.bagnet.nettracer.ws.onlineclaims.xsd.Passenger[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "passenger" element
     */
    public com.bagnet.nettracer.ws.onlineclaims.xsd.Passenger getPassengerArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.xsd.Passenger target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.xsd.Passenger)get_store().find_element_user(PASSENGER$72, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Tests for nil ith "passenger" element
     */
    public boolean isNilPassengerArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.xsd.Passenger target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.xsd.Passenger)get_store().find_element_user(PASSENGER$72, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target.isNil();
        }
    }
    
    /**
     * Returns number of "passenger" element
     */
    public int sizeOfPassengerArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(PASSENGER$72);
        }
    }
    
    /**
     * Sets array of all "passenger" element  WARNING: This method is not atomicaly synchronized.
     */
    public void setPassengerArray(com.bagnet.nettracer.ws.onlineclaims.xsd.Passenger[] passengerArray)
    {
        check_orphaned();
        arraySetterHelper(passengerArray, PASSENGER$72);
    }
    
    /**
     * Sets ith "passenger" element
     */
    public void setPassengerArray(int i, com.bagnet.nettracer.ws.onlineclaims.xsd.Passenger passenger)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.xsd.Passenger target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.xsd.Passenger)get_store().find_element_user(PASSENGER$72, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(passenger);
        }
    }
    
    /**
     * Nils the ith "passenger" element
     */
    public void setNilPassengerArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.xsd.Passenger target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.xsd.Passenger)get_store().find_element_user(PASSENGER$72, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.setNil();
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "passenger" element
     */
    public com.bagnet.nettracer.ws.onlineclaims.xsd.Passenger insertNewPassenger(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.xsd.Passenger target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.xsd.Passenger)get_store().insert_element_user(PASSENGER$72, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "passenger" element
     */
    public com.bagnet.nettracer.ws.onlineclaims.xsd.Passenger addNewPassenger()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.xsd.Passenger target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.xsd.Passenger)get_store().add_element_user(PASSENGER$72);
            return target;
        }
    }
    
    /**
     * Removes the ith "passenger" element
     */
    public void removePassenger(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(PASSENGER$72, i);
        }
    }
    
    /**
     * Gets the "passengersTravelingWithYou" element
     */
    public int getPassengersTravelingWithYou()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PASSENGERSTRAVELINGWITHYOU$74, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "passengersTravelingWithYou" element
     */
    public org.apache.xmlbeans.XmlInt xgetPassengersTravelingWithYou()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(PASSENGERSTRAVELINGWITHYOU$74, 0);
            return target;
        }
    }
    
    /**
     * True if has "passengersTravelingWithYou" element
     */
    public boolean isSetPassengersTravelingWithYou()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(PASSENGERSTRAVELINGWITHYOU$74) != 0;
        }
    }
    
    /**
     * Sets the "passengersTravelingWithYou" element
     */
    public void setPassengersTravelingWithYou(int passengersTravelingWithYou)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PASSENGERSTRAVELINGWITHYOU$74, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PASSENGERSTRAVELINGWITHYOU$74);
            }
            target.setIntValue(passengersTravelingWithYou);
        }
    }
    
    /**
     * Sets (as xml) the "passengersTravelingWithYou" element
     */
    public void xsetPassengersTravelingWithYou(org.apache.xmlbeans.XmlInt passengersTravelingWithYou)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(PASSENGERSTRAVELINGWITHYOU$74, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(PASSENGERSTRAVELINGWITHYOU$74);
            }
            target.set(passengersTravelingWithYou);
        }
    }
    
    /**
     * Unsets the "passengersTravelingWithYou" element
     */
    public void unsetPassengersTravelingWithYou()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(PASSENGERSTRAVELINGWITHYOU$74, 0);
        }
    }
    
    /**
     * Gets the "paxClaimAmount" element
     */
    public java.lang.String getPaxClaimAmount()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PAXCLAIMAMOUNT$76, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "paxClaimAmount" element
     */
    public org.apache.xmlbeans.XmlString xgetPaxClaimAmount()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PAXCLAIMAMOUNT$76, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "paxClaimAmount" element
     */
    public boolean isNilPaxClaimAmount()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PAXCLAIMAMOUNT$76, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "paxClaimAmount" element
     */
    public boolean isSetPaxClaimAmount()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(PAXCLAIMAMOUNT$76) != 0;
        }
    }
    
    /**
     * Sets the "paxClaimAmount" element
     */
    public void setPaxClaimAmount(java.lang.String paxClaimAmount)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PAXCLAIMAMOUNT$76, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PAXCLAIMAMOUNT$76);
            }
            target.setStringValue(paxClaimAmount);
        }
    }
    
    /**
     * Sets (as xml) the "paxClaimAmount" element
     */
    public void xsetPaxClaimAmount(org.apache.xmlbeans.XmlString paxClaimAmount)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PAXCLAIMAMOUNT$76, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PAXCLAIMAMOUNT$76);
            }
            target.set(paxClaimAmount);
        }
    }
    
    /**
     * Nils the "paxClaimAmount" element
     */
    public void setNilPaxClaimAmount()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PAXCLAIMAMOUNT$76, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PAXCLAIMAMOUNT$76);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "paxClaimAmount" element
     */
    public void unsetPaxClaimAmount()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(PAXCLAIMAMOUNT$76, 0);
        }
    }
    
    /**
     * Gets the "paxClaimAmountCurrency" element
     */
    public java.lang.String getPaxClaimAmountCurrency()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PAXCLAIMAMOUNTCURRENCY$78, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "paxClaimAmountCurrency" element
     */
    public org.apache.xmlbeans.XmlString xgetPaxClaimAmountCurrency()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PAXCLAIMAMOUNTCURRENCY$78, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "paxClaimAmountCurrency" element
     */
    public boolean isNilPaxClaimAmountCurrency()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PAXCLAIMAMOUNTCURRENCY$78, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "paxClaimAmountCurrency" element
     */
    public boolean isSetPaxClaimAmountCurrency()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(PAXCLAIMAMOUNTCURRENCY$78) != 0;
        }
    }
    
    /**
     * Sets the "paxClaimAmountCurrency" element
     */
    public void setPaxClaimAmountCurrency(java.lang.String paxClaimAmountCurrency)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PAXCLAIMAMOUNTCURRENCY$78, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PAXCLAIMAMOUNTCURRENCY$78);
            }
            target.setStringValue(paxClaimAmountCurrency);
        }
    }
    
    /**
     * Sets (as xml) the "paxClaimAmountCurrency" element
     */
    public void xsetPaxClaimAmountCurrency(org.apache.xmlbeans.XmlString paxClaimAmountCurrency)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PAXCLAIMAMOUNTCURRENCY$78, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PAXCLAIMAMOUNTCURRENCY$78);
            }
            target.set(paxClaimAmountCurrency);
        }
    }
    
    /**
     * Nils the "paxClaimAmountCurrency" element
     */
    public void setNilPaxClaimAmountCurrency()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PAXCLAIMAMOUNTCURRENCY$78, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PAXCLAIMAMOUNTCURRENCY$78);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "paxClaimAmountCurrency" element
     */
    public void unsetPaxClaimAmountCurrency()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(PAXCLAIMAMOUNTCURRENCY$78, 0);
        }
    }
    
    /**
     * Gets the "paxClaimDate" element
     */
    public java.lang.String getPaxClaimDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PAXCLAIMDATE$80, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "paxClaimDate" element
     */
    public org.apache.xmlbeans.XmlString xgetPaxClaimDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PAXCLAIMDATE$80, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "paxClaimDate" element
     */
    public boolean isNilPaxClaimDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PAXCLAIMDATE$80, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "paxClaimDate" element
     */
    public boolean isSetPaxClaimDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(PAXCLAIMDATE$80) != 0;
        }
    }
    
    /**
     * Sets the "paxClaimDate" element
     */
    public void setPaxClaimDate(java.lang.String paxClaimDate)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PAXCLAIMDATE$80, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PAXCLAIMDATE$80);
            }
            target.setStringValue(paxClaimDate);
        }
    }
    
    /**
     * Sets (as xml) the "paxClaimDate" element
     */
    public void xsetPaxClaimDate(org.apache.xmlbeans.XmlString paxClaimDate)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PAXCLAIMDATE$80, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PAXCLAIMDATE$80);
            }
            target.set(paxClaimDate);
        }
    }
    
    /**
     * Nils the "paxClaimDate" element
     */
    public void setNilPaxClaimDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PAXCLAIMDATE$80, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PAXCLAIMDATE$80);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "paxClaimDate" element
     */
    public void unsetPaxClaimDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(PAXCLAIMDATE$80, 0);
        }
    }
    
    /**
     * Gets the "paxIpAddress" element
     */
    public java.lang.String getPaxIpAddress()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PAXIPADDRESS$82, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "paxIpAddress" element
     */
    public org.apache.xmlbeans.XmlString xgetPaxIpAddress()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PAXIPADDRESS$82, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "paxIpAddress" element
     */
    public boolean isNilPaxIpAddress()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PAXIPADDRESS$82, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "paxIpAddress" element
     */
    public boolean isSetPaxIpAddress()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(PAXIPADDRESS$82) != 0;
        }
    }
    
    /**
     * Sets the "paxIpAddress" element
     */
    public void setPaxIpAddress(java.lang.String paxIpAddress)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PAXIPADDRESS$82, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PAXIPADDRESS$82);
            }
            target.setStringValue(paxIpAddress);
        }
    }
    
    /**
     * Sets (as xml) the "paxIpAddress" element
     */
    public void xsetPaxIpAddress(org.apache.xmlbeans.XmlString paxIpAddress)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PAXIPADDRESS$82, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PAXIPADDRESS$82);
            }
            target.set(paxIpAddress);
        }
    }
    
    /**
     * Nils the "paxIpAddress" element
     */
    public void setNilPaxIpAddress()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PAXIPADDRESS$82, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PAXIPADDRESS$82);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "paxIpAddress" element
     */
    public void unsetPaxIpAddress()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(PAXIPADDRESS$82, 0);
        }
    }
    
    /**
     * Gets the "permanentAddress" element
     */
    public com.bagnet.nettracer.ws.onlineclaims.xsd.Address getPermanentAddress()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.xsd.Address target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.xsd.Address)get_store().find_element_user(PERMANENTADDRESS$84, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Tests for nil "permanentAddress" element
     */
    public boolean isNilPermanentAddress()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.xsd.Address target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.xsd.Address)get_store().find_element_user(PERMANENTADDRESS$84, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "permanentAddress" element
     */
    public boolean isSetPermanentAddress()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(PERMANENTADDRESS$84) != 0;
        }
    }
    
    /**
     * Sets the "permanentAddress" element
     */
    public void setPermanentAddress(com.bagnet.nettracer.ws.onlineclaims.xsd.Address permanentAddress)
    {
        generatedSetterHelperImpl(permanentAddress, PERMANENTADDRESS$84, 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
    }
    
    /**
     * Appends and returns a new empty "permanentAddress" element
     */
    public com.bagnet.nettracer.ws.onlineclaims.xsd.Address addNewPermanentAddress()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.xsd.Address target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.xsd.Address)get_store().add_element_user(PERMANENTADDRESS$84);
            return target;
        }
    }
    
    /**
     * Nils the "permanentAddress" element
     */
    public void setNilPermanentAddress()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.xsd.Address target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.xsd.Address)get_store().find_element_user(PERMANENTADDRESS$84, 0);
            if (target == null)
            {
                target = (com.bagnet.nettracer.ws.onlineclaims.xsd.Address)get_store().add_element_user(PERMANENTADDRESS$84);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "permanentAddress" element
     */
    public void unsetPermanentAddress()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(PERMANENTADDRESS$84, 0);
        }
    }
    
    /**
     * Gets array of all "phone" elements
     */
    public com.bagnet.nettracer.ws.onlineclaims.xsd.Phone[] getPhoneArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(PHONE$86, targetList);
            com.bagnet.nettracer.ws.onlineclaims.xsd.Phone[] result = new com.bagnet.nettracer.ws.onlineclaims.xsd.Phone[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "phone" element
     */
    public com.bagnet.nettracer.ws.onlineclaims.xsd.Phone getPhoneArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.xsd.Phone target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.xsd.Phone)get_store().find_element_user(PHONE$86, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Tests for nil ith "phone" element
     */
    public boolean isNilPhoneArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.xsd.Phone target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.xsd.Phone)get_store().find_element_user(PHONE$86, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target.isNil();
        }
    }
    
    /**
     * Returns number of "phone" element
     */
    public int sizeOfPhoneArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(PHONE$86);
        }
    }
    
    /**
     * Sets array of all "phone" element  WARNING: This method is not atomicaly synchronized.
     */
    public void setPhoneArray(com.bagnet.nettracer.ws.onlineclaims.xsd.Phone[] phoneArray)
    {
        check_orphaned();
        arraySetterHelper(phoneArray, PHONE$86);
    }
    
    /**
     * Sets ith "phone" element
     */
    public void setPhoneArray(int i, com.bagnet.nettracer.ws.onlineclaims.xsd.Phone phone)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.xsd.Phone target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.xsd.Phone)get_store().find_element_user(PHONE$86, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(phone);
        }
    }
    
    /**
     * Nils the ith "phone" element
     */
    public void setNilPhoneArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.xsd.Phone target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.xsd.Phone)get_store().find_element_user(PHONE$86, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.setNil();
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "phone" element
     */
    public com.bagnet.nettracer.ws.onlineclaims.xsd.Phone insertNewPhone(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.xsd.Phone target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.xsd.Phone)get_store().insert_element_user(PHONE$86, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "phone" element
     */
    public com.bagnet.nettracer.ws.onlineclaims.xsd.Phone addNewPhone()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.xsd.Phone target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.xsd.Phone)get_store().add_element_user(PHONE$86);
            return target;
        }
    }
    
    /**
     * Removes the ith "phone" element
     */
    public void removePhone(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(PHONE$86, i);
        }
    }
    
    /**
     * Gets the "privateInsurance" element
     */
    public int getPrivateInsurance()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PRIVATEINSURANCE$88, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "privateInsurance" element
     */
    public org.apache.xmlbeans.XmlInt xgetPrivateInsurance()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(PRIVATEINSURANCE$88, 0);
            return target;
        }
    }
    
    /**
     * True if has "privateInsurance" element
     */
    public boolean isSetPrivateInsurance()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(PRIVATEINSURANCE$88) != 0;
        }
    }
    
    /**
     * Sets the "privateInsurance" element
     */
    public void setPrivateInsurance(int privateInsurance)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PRIVATEINSURANCE$88, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PRIVATEINSURANCE$88);
            }
            target.setIntValue(privateInsurance);
        }
    }
    
    /**
     * Sets (as xml) the "privateInsurance" element
     */
    public void xsetPrivateInsurance(org.apache.xmlbeans.XmlInt privateInsurance)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(PRIVATEINSURANCE$88, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(PRIVATEINSURANCE$88);
            }
            target.set(privateInsurance);
        }
    }
    
    /**
     * Unsets the "privateInsurance" element
     */
    public void unsetPrivateInsurance()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(PRIVATEINSURANCE$88, 0);
        }
    }
    
    /**
     * Gets the "privateInsuranceAddr" element
     */
    public java.lang.String getPrivateInsuranceAddr()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PRIVATEINSURANCEADDR$90, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "privateInsuranceAddr" element
     */
    public org.apache.xmlbeans.XmlString xgetPrivateInsuranceAddr()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PRIVATEINSURANCEADDR$90, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "privateInsuranceAddr" element
     */
    public boolean isNilPrivateInsuranceAddr()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PRIVATEINSURANCEADDR$90, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "privateInsuranceAddr" element
     */
    public boolean isSetPrivateInsuranceAddr()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(PRIVATEINSURANCEADDR$90) != 0;
        }
    }
    
    /**
     * Sets the "privateInsuranceAddr" element
     */
    public void setPrivateInsuranceAddr(java.lang.String privateInsuranceAddr)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PRIVATEINSURANCEADDR$90, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PRIVATEINSURANCEADDR$90);
            }
            target.setStringValue(privateInsuranceAddr);
        }
    }
    
    /**
     * Sets (as xml) the "privateInsuranceAddr" element
     */
    public void xsetPrivateInsuranceAddr(org.apache.xmlbeans.XmlString privateInsuranceAddr)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PRIVATEINSURANCEADDR$90, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PRIVATEINSURANCEADDR$90);
            }
            target.set(privateInsuranceAddr);
        }
    }
    
    /**
     * Nils the "privateInsuranceAddr" element
     */
    public void setNilPrivateInsuranceAddr()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PRIVATEINSURANCEADDR$90, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PRIVATEINSURANCEADDR$90);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "privateInsuranceAddr" element
     */
    public void unsetPrivateInsuranceAddr()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(PRIVATEINSURANCEADDR$90, 0);
        }
    }
    
    /**
     * Gets the "privateInsuranceName" element
     */
    public java.lang.String getPrivateInsuranceName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PRIVATEINSURANCENAME$92, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "privateInsuranceName" element
     */
    public org.apache.xmlbeans.XmlString xgetPrivateInsuranceName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PRIVATEINSURANCENAME$92, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "privateInsuranceName" element
     */
    public boolean isNilPrivateInsuranceName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PRIVATEINSURANCENAME$92, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "privateInsuranceName" element
     */
    public boolean isSetPrivateInsuranceName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(PRIVATEINSURANCENAME$92) != 0;
        }
    }
    
    /**
     * Sets the "privateInsuranceName" element
     */
    public void setPrivateInsuranceName(java.lang.String privateInsuranceName)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PRIVATEINSURANCENAME$92, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PRIVATEINSURANCENAME$92);
            }
            target.setStringValue(privateInsuranceName);
        }
    }
    
    /**
     * Sets (as xml) the "privateInsuranceName" element
     */
    public void xsetPrivateInsuranceName(org.apache.xmlbeans.XmlString privateInsuranceName)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PRIVATEINSURANCENAME$92, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PRIVATEINSURANCENAME$92);
            }
            target.set(privateInsuranceName);
        }
    }
    
    /**
     * Nils the "privateInsuranceName" element
     */
    public void setNilPrivateInsuranceName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PRIVATEINSURANCENAME$92, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PRIVATEINSURANCENAME$92);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "privateInsuranceName" element
     */
    public void unsetPrivateInsuranceName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(PRIVATEINSURANCENAME$92, 0);
        }
    }
    
    /**
     * Gets the "reasonForTravel" element
     */
    public java.lang.String getReasonForTravel()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(REASONFORTRAVEL$94, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "reasonForTravel" element
     */
    public org.apache.xmlbeans.XmlString xgetReasonForTravel()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(REASONFORTRAVEL$94, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "reasonForTravel" element
     */
    public boolean isNilReasonForTravel()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(REASONFORTRAVEL$94, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "reasonForTravel" element
     */
    public boolean isSetReasonForTravel()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(REASONFORTRAVEL$94) != 0;
        }
    }
    
    /**
     * Sets the "reasonForTravel" element
     */
    public void setReasonForTravel(java.lang.String reasonForTravel)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(REASONFORTRAVEL$94, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(REASONFORTRAVEL$94);
            }
            target.setStringValue(reasonForTravel);
        }
    }
    
    /**
     * Sets (as xml) the "reasonForTravel" element
     */
    public void xsetReasonForTravel(org.apache.xmlbeans.XmlString reasonForTravel)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(REASONFORTRAVEL$94, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(REASONFORTRAVEL$94);
            }
            target.set(reasonForTravel);
        }
    }
    
    /**
     * Nils the "reasonForTravel" element
     */
    public void setNilReasonForTravel()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(REASONFORTRAVEL$94, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(REASONFORTRAVEL$94);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "reasonForTravel" element
     */
    public void unsetReasonForTravel()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(REASONFORTRAVEL$94, 0);
        }
    }
    
    /**
     * Gets the "reportedAirline" element
     */
    public java.lang.String getReportedAirline()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(REPORTEDAIRLINE$96, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "reportedAirline" element
     */
    public org.apache.xmlbeans.XmlString xgetReportedAirline()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(REPORTEDAIRLINE$96, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "reportedAirline" element
     */
    public boolean isNilReportedAirline()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(REPORTEDAIRLINE$96, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "reportedAirline" element
     */
    public boolean isSetReportedAirline()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(REPORTEDAIRLINE$96) != 0;
        }
    }
    
    /**
     * Sets the "reportedAirline" element
     */
    public void setReportedAirline(java.lang.String reportedAirline)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(REPORTEDAIRLINE$96, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(REPORTEDAIRLINE$96);
            }
            target.setStringValue(reportedAirline);
        }
    }
    
    /**
     * Sets (as xml) the "reportedAirline" element
     */
    public void xsetReportedAirline(org.apache.xmlbeans.XmlString reportedAirline)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(REPORTEDAIRLINE$96, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(REPORTEDAIRLINE$96);
            }
            target.set(reportedAirline);
        }
    }
    
    /**
     * Nils the "reportedAirline" element
     */
    public void setNilReportedAirline()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(REPORTEDAIRLINE$96, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(REPORTEDAIRLINE$96);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "reportedAirline" element
     */
    public void unsetReportedAirline()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(REPORTEDAIRLINE$96, 0);
        }
    }
    
    /**
     * Gets the "reportedCity" element
     */
    public java.lang.String getReportedCity()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(REPORTEDCITY$98, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "reportedCity" element
     */
    public org.apache.xmlbeans.XmlString xgetReportedCity()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(REPORTEDCITY$98, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "reportedCity" element
     */
    public boolean isNilReportedCity()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(REPORTEDCITY$98, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "reportedCity" element
     */
    public boolean isSetReportedCity()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(REPORTEDCITY$98) != 0;
        }
    }
    
    /**
     * Sets the "reportedCity" element
     */
    public void setReportedCity(java.lang.String reportedCity)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(REPORTEDCITY$98, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(REPORTEDCITY$98);
            }
            target.setStringValue(reportedCity);
        }
    }
    
    /**
     * Sets (as xml) the "reportedCity" element
     */
    public void xsetReportedCity(org.apache.xmlbeans.XmlString reportedCity)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(REPORTEDCITY$98, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(REPORTEDCITY$98);
            }
            target.set(reportedCity);
        }
    }
    
    /**
     * Nils the "reportedCity" element
     */
    public void setNilReportedCity()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(REPORTEDCITY$98, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(REPORTEDCITY$98);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "reportedCity" element
     */
    public void unsetReportedCity()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(REPORTEDCITY$98, 0);
        }
    }
    
    /**
     * Gets the "reportedFileNumber" element
     */
    public java.lang.String getReportedFileNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(REPORTEDFILENUMBER$100, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "reportedFileNumber" element
     */
    public org.apache.xmlbeans.XmlString xgetReportedFileNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(REPORTEDFILENUMBER$100, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "reportedFileNumber" element
     */
    public boolean isNilReportedFileNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(REPORTEDFILENUMBER$100, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "reportedFileNumber" element
     */
    public boolean isSetReportedFileNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(REPORTEDFILENUMBER$100) != 0;
        }
    }
    
    /**
     * Sets the "reportedFileNumber" element
     */
    public void setReportedFileNumber(java.lang.String reportedFileNumber)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(REPORTEDFILENUMBER$100, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(REPORTEDFILENUMBER$100);
            }
            target.setStringValue(reportedFileNumber);
        }
    }
    
    /**
     * Sets (as xml) the "reportedFileNumber" element
     */
    public void xsetReportedFileNumber(org.apache.xmlbeans.XmlString reportedFileNumber)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(REPORTEDFILENUMBER$100, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(REPORTEDFILENUMBER$100);
            }
            target.set(reportedFileNumber);
        }
    }
    
    /**
     * Nils the "reportedFileNumber" element
     */
    public void setNilReportedFileNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(REPORTEDFILENUMBER$100, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(REPORTEDFILENUMBER$100);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "reportedFileNumber" element
     */
    public void unsetReportedFileNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(REPORTEDFILENUMBER$100, 0);
        }
    }
    
    /**
     * Gets the "reportedToAnotherAirline" element
     */
    public int getReportedToAnotherAirline()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(REPORTEDTOANOTHERAIRLINE$102, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "reportedToAnotherAirline" element
     */
    public org.apache.xmlbeans.XmlInt xgetReportedToAnotherAirline()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(REPORTEDTOANOTHERAIRLINE$102, 0);
            return target;
        }
    }
    
    /**
     * True if has "reportedToAnotherAirline" element
     */
    public boolean isSetReportedToAnotherAirline()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(REPORTEDTOANOTHERAIRLINE$102) != 0;
        }
    }
    
    /**
     * Sets the "reportedToAnotherAirline" element
     */
    public void setReportedToAnotherAirline(int reportedToAnotherAirline)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(REPORTEDTOANOTHERAIRLINE$102, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(REPORTEDTOANOTHERAIRLINE$102);
            }
            target.setIntValue(reportedToAnotherAirline);
        }
    }
    
    /**
     * Sets (as xml) the "reportedToAnotherAirline" element
     */
    public void xsetReportedToAnotherAirline(org.apache.xmlbeans.XmlInt reportedToAnotherAirline)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(REPORTEDTOANOTHERAIRLINE$102, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(REPORTEDTOANOTHERAIRLINE$102);
            }
            target.set(reportedToAnotherAirline);
        }
    }
    
    /**
     * Unsets the "reportedToAnotherAirline" element
     */
    public void unsetReportedToAnotherAirline()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(REPORTEDTOANOTHERAIRLINE$102, 0);
        }
    }
    
    /**
     * Gets the "requestForeignCurrency" element
     */
    public int getRequestForeignCurrency()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(REQUESTFOREIGNCURRENCY$104, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "requestForeignCurrency" element
     */
    public org.apache.xmlbeans.XmlInt xgetRequestForeignCurrency()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(REQUESTFOREIGNCURRENCY$104, 0);
            return target;
        }
    }
    
    /**
     * True if has "requestForeignCurrency" element
     */
    public boolean isSetRequestForeignCurrency()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(REQUESTFOREIGNCURRENCY$104) != 0;
        }
    }
    
    /**
     * Sets the "requestForeignCurrency" element
     */
    public void setRequestForeignCurrency(int requestForeignCurrency)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(REQUESTFOREIGNCURRENCY$104, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(REQUESTFOREIGNCURRENCY$104);
            }
            target.setIntValue(requestForeignCurrency);
        }
    }
    
    /**
     * Sets (as xml) the "requestForeignCurrency" element
     */
    public void xsetRequestForeignCurrency(org.apache.xmlbeans.XmlInt requestForeignCurrency)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(REQUESTFOREIGNCURRENCY$104, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(REQUESTFOREIGNCURRENCY$104);
            }
            target.set(requestForeignCurrency);
        }
    }
    
    /**
     * Unsets the "requestForeignCurrency" element
     */
    public void unsetRequestForeignCurrency()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(REQUESTFOREIGNCURRENCY$104, 0);
        }
    }
    
    /**
     * Gets the "reroutedAirlineCity" element
     */
    public java.lang.String getReroutedAirlineCity()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(REROUTEDAIRLINECITY$106, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "reroutedAirlineCity" element
     */
    public org.apache.xmlbeans.XmlString xgetReroutedAirlineCity()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(REROUTEDAIRLINECITY$106, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "reroutedAirlineCity" element
     */
    public boolean isNilReroutedAirlineCity()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(REROUTEDAIRLINECITY$106, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "reroutedAirlineCity" element
     */
    public boolean isSetReroutedAirlineCity()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(REROUTEDAIRLINECITY$106) != 0;
        }
    }
    
    /**
     * Sets the "reroutedAirlineCity" element
     */
    public void setReroutedAirlineCity(java.lang.String reroutedAirlineCity)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(REROUTEDAIRLINECITY$106, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(REROUTEDAIRLINECITY$106);
            }
            target.setStringValue(reroutedAirlineCity);
        }
    }
    
    /**
     * Sets (as xml) the "reroutedAirlineCity" element
     */
    public void xsetReroutedAirlineCity(org.apache.xmlbeans.XmlString reroutedAirlineCity)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(REROUTEDAIRLINECITY$106, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(REROUTEDAIRLINECITY$106);
            }
            target.set(reroutedAirlineCity);
        }
    }
    
    /**
     * Nils the "reroutedAirlineCity" element
     */
    public void setNilReroutedAirlineCity()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(REROUTEDAIRLINECITY$106, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(REROUTEDAIRLINECITY$106);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "reroutedAirlineCity" element
     */
    public void unsetReroutedAirlineCity()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(REROUTEDAIRLINECITY$106, 0);
        }
    }
    
    /**
     * Gets the "reroutedReason" element
     */
    public java.lang.String getReroutedReason()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(REROUTEDREASON$108, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "reroutedReason" element
     */
    public org.apache.xmlbeans.XmlString xgetReroutedReason()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(REROUTEDREASON$108, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "reroutedReason" element
     */
    public boolean isNilReroutedReason()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(REROUTEDREASON$108, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "reroutedReason" element
     */
    public boolean isSetReroutedReason()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(REROUTEDREASON$108) != 0;
        }
    }
    
    /**
     * Sets the "reroutedReason" element
     */
    public void setReroutedReason(java.lang.String reroutedReason)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(REROUTEDREASON$108, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(REROUTEDREASON$108);
            }
            target.setStringValue(reroutedReason);
        }
    }
    
    /**
     * Sets (as xml) the "reroutedReason" element
     */
    public void xsetReroutedReason(org.apache.xmlbeans.XmlString reroutedReason)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(REROUTEDREASON$108, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(REROUTEDREASON$108);
            }
            target.set(reroutedReason);
        }
    }
    
    /**
     * Nils the "reroutedReason" element
     */
    public void setNilReroutedReason()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(REROUTEDREASON$108, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(REROUTEDREASON$108);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "reroutedReason" element
     */
    public void unsetReroutedReason()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(REROUTEDREASON$108, 0);
        }
    }
    
    /**
     * Gets the "socialSecurity" element
     */
    public java.lang.String getSocialSecurity()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SOCIALSECURITY$110, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "socialSecurity" element
     */
    public org.apache.xmlbeans.XmlString xgetSocialSecurity()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SOCIALSECURITY$110, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "socialSecurity" element
     */
    public boolean isNilSocialSecurity()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SOCIALSECURITY$110, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "socialSecurity" element
     */
    public boolean isSetSocialSecurity()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(SOCIALSECURITY$110) != 0;
        }
    }
    
    /**
     * Sets the "socialSecurity" element
     */
    public void setSocialSecurity(java.lang.String socialSecurity)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SOCIALSECURITY$110, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(SOCIALSECURITY$110);
            }
            target.setStringValue(socialSecurity);
        }
    }
    
    /**
     * Sets (as xml) the "socialSecurity" element
     */
    public void xsetSocialSecurity(org.apache.xmlbeans.XmlString socialSecurity)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SOCIALSECURITY$110, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(SOCIALSECURITY$110);
            }
            target.set(socialSecurity);
        }
    }
    
    /**
     * Nils the "socialSecurity" element
     */
    public void setNilSocialSecurity()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SOCIALSECURITY$110, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(SOCIALSECURITY$110);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "socialSecurity" element
     */
    public void unsetSocialSecurity()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(SOCIALSECURITY$110, 0);
        }
    }
    
    /**
     * Gets the "status" element
     */
    public java.lang.String getStatus()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STATUS$112, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "status" element
     */
    public org.apache.xmlbeans.XmlString xgetStatus()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STATUS$112, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "status" element
     */
    public boolean isNilStatus()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STATUS$112, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "status" element
     */
    public boolean isSetStatus()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(STATUS$112) != 0;
        }
    }
    
    /**
     * Sets the "status" element
     */
    public void setStatus(java.lang.String status)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STATUS$112, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(STATUS$112);
            }
            target.setStringValue(status);
        }
    }
    
    /**
     * Sets (as xml) the "status" element
     */
    public void xsetStatus(org.apache.xmlbeans.XmlString status)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STATUS$112, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(STATUS$112);
            }
            target.set(status);
        }
    }
    
    /**
     * Nils the "status" element
     */
    public void setNilStatus()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STATUS$112, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(STATUS$112);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "status" element
     */
    public void unsetStatus()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(STATUS$112, 0);
        }
    }
    
    /**
     * Gets the "ticketNumber" element
     */
    public java.lang.String getTicketNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TICKETNUMBER$114, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "ticketNumber" element
     */
    public org.apache.xmlbeans.XmlString xgetTicketNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TICKETNUMBER$114, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "ticketNumber" element
     */
    public boolean isNilTicketNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TICKETNUMBER$114, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "ticketNumber" element
     */
    public boolean isSetTicketNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(TICKETNUMBER$114) != 0;
        }
    }
    
    /**
     * Sets the "ticketNumber" element
     */
    public void setTicketNumber(java.lang.String ticketNumber)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TICKETNUMBER$114, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(TICKETNUMBER$114);
            }
            target.setStringValue(ticketNumber);
        }
    }
    
    /**
     * Sets (as xml) the "ticketNumber" element
     */
    public void xsetTicketNumber(org.apache.xmlbeans.XmlString ticketNumber)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TICKETNUMBER$114, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(TICKETNUMBER$114);
            }
            target.set(ticketNumber);
        }
    }
    
    /**
     * Nils the "ticketNumber" element
     */
    public void setNilTicketNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TICKETNUMBER$114, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(TICKETNUMBER$114);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "ticketNumber" element
     */
    public void unsetTicketNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(TICKETNUMBER$114, 0);
        }
    }
    
    /**
     * Gets the "ticketWithAnotherAirline" element
     */
    public int getTicketWithAnotherAirline()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TICKETWITHANOTHERAIRLINE$116, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "ticketWithAnotherAirline" element
     */
    public org.apache.xmlbeans.XmlInt xgetTicketWithAnotherAirline()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(TICKETWITHANOTHERAIRLINE$116, 0);
            return target;
        }
    }
    
    /**
     * True if has "ticketWithAnotherAirline" element
     */
    public boolean isSetTicketWithAnotherAirline()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(TICKETWITHANOTHERAIRLINE$116) != 0;
        }
    }
    
    /**
     * Sets the "ticketWithAnotherAirline" element
     */
    public void setTicketWithAnotherAirline(int ticketWithAnotherAirline)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TICKETWITHANOTHERAIRLINE$116, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(TICKETWITHANOTHERAIRLINE$116);
            }
            target.setIntValue(ticketWithAnotherAirline);
        }
    }
    
    /**
     * Sets (as xml) the "ticketWithAnotherAirline" element
     */
    public void xsetTicketWithAnotherAirline(org.apache.xmlbeans.XmlInt ticketWithAnotherAirline)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(TICKETWITHANOTHERAIRLINE$116, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(TICKETWITHANOTHERAIRLINE$116);
            }
            target.set(ticketWithAnotherAirline);
        }
    }
    
    /**
     * Unsets the "ticketWithAnotherAirline" element
     */
    public void unsetTicketWithAnotherAirline()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(TICKETWITHANOTHERAIRLINE$116, 0);
        }
    }
    
    /**
     * Gets the "tsaInspected" element
     */
    public int getTsaInspected()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TSAINSPECTED$118, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "tsaInspected" element
     */
    public org.apache.xmlbeans.XmlInt xgetTsaInspected()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(TSAINSPECTED$118, 0);
            return target;
        }
    }
    
    /**
     * True if has "tsaInspected" element
     */
    public boolean isSetTsaInspected()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(TSAINSPECTED$118) != 0;
        }
    }
    
    /**
     * Sets the "tsaInspected" element
     */
    public void setTsaInspected(int tsaInspected)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TSAINSPECTED$118, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(TSAINSPECTED$118);
            }
            target.setIntValue(tsaInspected);
        }
    }
    
    /**
     * Sets (as xml) the "tsaInspected" element
     */
    public void xsetTsaInspected(org.apache.xmlbeans.XmlInt tsaInspected)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(TSAINSPECTED$118, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(TSAINSPECTED$118);
            }
            target.set(tsaInspected);
        }
    }
    
    /**
     * Unsets the "tsaInspected" element
     */
    public void unsetTsaInspected()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(TSAINSPECTED$118, 0);
        }
    }
    
    /**
     * Gets the "tsaInspectionLocation" element
     */
    public java.lang.String getTsaInspectionLocation()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TSAINSPECTIONLOCATION$120, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "tsaInspectionLocation" element
     */
    public org.apache.xmlbeans.XmlString xgetTsaInspectionLocation()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TSAINSPECTIONLOCATION$120, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "tsaInspectionLocation" element
     */
    public boolean isNilTsaInspectionLocation()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TSAINSPECTIONLOCATION$120, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "tsaInspectionLocation" element
     */
    public boolean isSetTsaInspectionLocation()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(TSAINSPECTIONLOCATION$120) != 0;
        }
    }
    
    /**
     * Sets the "tsaInspectionLocation" element
     */
    public void setTsaInspectionLocation(java.lang.String tsaInspectionLocation)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TSAINSPECTIONLOCATION$120, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(TSAINSPECTIONLOCATION$120);
            }
            target.setStringValue(tsaInspectionLocation);
        }
    }
    
    /**
     * Sets (as xml) the "tsaInspectionLocation" element
     */
    public void xsetTsaInspectionLocation(org.apache.xmlbeans.XmlString tsaInspectionLocation)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TSAINSPECTIONLOCATION$120, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(TSAINSPECTIONLOCATION$120);
            }
            target.set(tsaInspectionLocation);
        }
    }
    
    /**
     * Nils the "tsaInspectionLocation" element
     */
    public void setNilTsaInspectionLocation()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TSAINSPECTIONLOCATION$120, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(TSAINSPECTIONLOCATION$120);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "tsaInspectionLocation" element
     */
    public void unsetTsaInspectionLocation()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(TSAINSPECTIONLOCATION$120, 0);
        }
    }
    
    /**
     * Gets the "tsaNotePresent" element
     */
    public int getTsaNotePresent()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TSANOTEPRESENT$122, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "tsaNotePresent" element
     */
    public org.apache.xmlbeans.XmlInt xgetTsaNotePresent()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(TSANOTEPRESENT$122, 0);
            return target;
        }
    }
    
    /**
     * True if has "tsaNotePresent" element
     */
    public boolean isSetTsaNotePresent()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(TSANOTEPRESENT$122) != 0;
        }
    }
    
    /**
     * Sets the "tsaNotePresent" element
     */
    public void setTsaNotePresent(int tsaNotePresent)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TSANOTEPRESENT$122, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(TSANOTEPRESENT$122);
            }
            target.setIntValue(tsaNotePresent);
        }
    }
    
    /**
     * Sets (as xml) the "tsaNotePresent" element
     */
    public void xsetTsaNotePresent(org.apache.xmlbeans.XmlInt tsaNotePresent)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(TSANOTEPRESENT$122, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(TSANOTEPRESENT$122);
            }
            target.set(tsaNotePresent);
        }
    }
    
    /**
     * Unsets the "tsaNotePresent" element
     */
    public void unsetTsaNotePresent()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(TSANOTEPRESENT$122, 0);
        }
    }
    
    /**
     * Gets the "wasBagInspected" element
     */
    public int getWasBagInspected()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(WASBAGINSPECTED$124, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "wasBagInspected" element
     */
    public org.apache.xmlbeans.XmlInt xgetWasBagInspected()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(WASBAGINSPECTED$124, 0);
            return target;
        }
    }
    
    /**
     * True if has "wasBagInspected" element
     */
    public boolean isSetWasBagInspected()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(WASBAGINSPECTED$124) != 0;
        }
    }
    
    /**
     * Sets the "wasBagInspected" element
     */
    public void setWasBagInspected(int wasBagInspected)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(WASBAGINSPECTED$124, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(WASBAGINSPECTED$124);
            }
            target.setIntValue(wasBagInspected);
        }
    }
    
    /**
     * Sets (as xml) the "wasBagInspected" element
     */
    public void xsetWasBagInspected(org.apache.xmlbeans.XmlInt wasBagInspected)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(WASBAGINSPECTED$124, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(WASBAGINSPECTED$124);
            }
            target.set(wasBagInspected);
        }
    }
    
    /**
     * Unsets the "wasBagInspected" element
     */
    public void unsetWasBagInspected()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(WASBAGINSPECTED$124, 0);
        }
    }
    
    /**
     * Gets the "whereDidYouFileReport" element
     */
    public java.lang.String getWhereDidYouFileReport()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(WHEREDIDYOUFILEREPORT$126, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "whereDidYouFileReport" element
     */
    public org.apache.xmlbeans.XmlString xgetWhereDidYouFileReport()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(WHEREDIDYOUFILEREPORT$126, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "whereDidYouFileReport" element
     */
    public boolean isNilWhereDidYouFileReport()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(WHEREDIDYOUFILEREPORT$126, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "whereDidYouFileReport" element
     */
    public boolean isSetWhereDidYouFileReport()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(WHEREDIDYOUFILEREPORT$126) != 0;
        }
    }
    
    /**
     * Sets the "whereDidYouFileReport" element
     */
    public void setWhereDidYouFileReport(java.lang.String whereDidYouFileReport)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(WHEREDIDYOUFILEREPORT$126, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(WHEREDIDYOUFILEREPORT$126);
            }
            target.setStringValue(whereDidYouFileReport);
        }
    }
    
    /**
     * Sets (as xml) the "whereDidYouFileReport" element
     */
    public void xsetWhereDidYouFileReport(org.apache.xmlbeans.XmlString whereDidYouFileReport)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(WHEREDIDYOUFILEREPORT$126, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(WHEREDIDYOUFILEREPORT$126);
            }
            target.set(whereDidYouFileReport);
        }
    }
    
    /**
     * Nils the "whereDidYouFileReport" element
     */
    public void setNilWhereDidYouFileReport()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(WHEREDIDYOUFILEREPORT$126, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(WHEREDIDYOUFILEREPORT$126);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "whereDidYouFileReport" element
     */
    public void unsetWhereDidYouFileReport()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(WHEREDIDYOUFILEREPORT$126, 0);
        }
    }
    
    /**
     * Gets the "whereWasBaggageChecked" element
     */
    public java.lang.String getWhereWasBaggageChecked()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(WHEREWASBAGGAGECHECKED$128, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "whereWasBaggageChecked" element
     */
    public org.apache.xmlbeans.XmlString xgetWhereWasBaggageChecked()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(WHEREWASBAGGAGECHECKED$128, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "whereWasBaggageChecked" element
     */
    public boolean isNilWhereWasBaggageChecked()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(WHEREWASBAGGAGECHECKED$128, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "whereWasBaggageChecked" element
     */
    public boolean isSetWhereWasBaggageChecked()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(WHEREWASBAGGAGECHECKED$128) != 0;
        }
    }
    
    /**
     * Sets the "whereWasBaggageChecked" element
     */
    public void setWhereWasBaggageChecked(java.lang.String whereWasBaggageChecked)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(WHEREWASBAGGAGECHECKED$128, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(WHEREWASBAGGAGECHECKED$128);
            }
            target.setStringValue(whereWasBaggageChecked);
        }
    }
    
    /**
     * Sets (as xml) the "whereWasBaggageChecked" element
     */
    public void xsetWhereWasBaggageChecked(org.apache.xmlbeans.XmlString whereWasBaggageChecked)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(WHEREWASBAGGAGECHECKED$128, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(WHEREWASBAGGAGECHECKED$128);
            }
            target.set(whereWasBaggageChecked);
        }
    }
    
    /**
     * Nils the "whereWasBaggageChecked" element
     */
    public void setNilWhereWasBaggageChecked()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(WHEREWASBAGGAGECHECKED$128, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(WHEREWASBAGGAGECHECKED$128);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "whereWasBaggageChecked" element
     */
    public void unsetWhereWasBaggageChecked()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(WHEREWASBAGGAGECHECKED$128, 0);
        }
    }
}
