/*
 * XML Type:  WS_BEORN
 * Namespace: http://pojo.core.ws.nettracer.bagnet.com/xsd
 * Java type: com.bagnet.nettracer.ws.core.pojo.xsd.WSBEORN
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.core.pojo.xsd.impl;
/**
 * An XML WS_BEORN(@http://pojo.core.ws.nettracer.bagnet.com/xsd).
 *
 * This is a complex type.
 */
public class WSBEORNImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.core.pojo.xsd.WSBEORN
{
    
    public WSBEORNImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName BAGGAGEITINERARY$0 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "baggageItinerary");
    private static final javax.xml.namespace.QName CLAIMCHECKNUMBER$2 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "claimCheckNumber");
    private static final javax.xml.namespace.QName DESTINATIONSTATION$4 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "destinationStation");
    private static final javax.xml.namespace.QName EXPEDITENUMBER$6 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "expediteNumber");
    private static final javax.xml.namespace.QName FAULTSTATION$8 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "faultStation");
    private static final javax.xml.namespace.QName FORWARDITINERARY$10 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "forwardItinerary");
    private static final javax.xml.namespace.QName FOUNDATSTATION$12 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "foundAtStation");
    private static final javax.xml.namespace.QName FOUNDDATETIME$14 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "founddatetime");
    private static final javax.xml.namespace.QName LOSSCODE$16 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "lossCode");
    private static final javax.xml.namespace.QName MESSAGE$18 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "message");
    private static final javax.xml.namespace.QName SPECIALINSTRUCTIONS$20 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "specialInstructions");
    private static final javax.xml.namespace.QName TAGOFF$22 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "tagOff");
    
    
    /**
     * Gets array of all "baggageItinerary" elements
     */
    public com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary[] getBaggageItineraryArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(BAGGAGEITINERARY$0, targetList);
            com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary[] result = new com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "baggageItinerary" element
     */
    public com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary getBaggageItineraryArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary)get_store().find_element_user(BAGGAGEITINERARY$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Tests for nil ith "baggageItinerary" element
     */
    public boolean isNilBaggageItineraryArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary)get_store().find_element_user(BAGGAGEITINERARY$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target.isNil();
        }
    }
    
    /**
     * Returns number of "baggageItinerary" element
     */
    public int sizeOfBaggageItineraryArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(BAGGAGEITINERARY$0);
        }
    }
    
    /**
     * Sets array of all "baggageItinerary" element
     */
    public void setBaggageItineraryArray(com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary[] baggageItineraryArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(baggageItineraryArray, BAGGAGEITINERARY$0);
        }
    }
    
    /**
     * Sets ith "baggageItinerary" element
     */
    public void setBaggageItineraryArray(int i, com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary baggageItinerary)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary)get_store().find_element_user(BAGGAGEITINERARY$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(baggageItinerary);
        }
    }
    
    /**
     * Nils the ith "baggageItinerary" element
     */
    public void setNilBaggageItineraryArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary)get_store().find_element_user(BAGGAGEITINERARY$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.setNil();
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "baggageItinerary" element
     */
    public com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary insertNewBaggageItinerary(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary)get_store().insert_element_user(BAGGAGEITINERARY$0, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "baggageItinerary" element
     */
    public com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary addNewBaggageItinerary()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary)get_store().add_element_user(BAGGAGEITINERARY$0);
            return target;
        }
    }
    
    /**
     * Removes the ith "baggageItinerary" element
     */
    public void removeBaggageItinerary(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(BAGGAGEITINERARY$0, i);
        }
    }
    
    /**
     * Gets the "claimCheckNumber" element
     */
    public java.lang.String getClaimCheckNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CLAIMCHECKNUMBER$2, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "claimCheckNumber" element
     */
    public org.apache.xmlbeans.XmlString xgetClaimCheckNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CLAIMCHECKNUMBER$2, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "claimCheckNumber" element
     */
    public boolean isNilClaimCheckNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CLAIMCHECKNUMBER$2, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "claimCheckNumber" element
     */
    public boolean isSetClaimCheckNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CLAIMCHECKNUMBER$2) != 0;
        }
    }
    
    /**
     * Sets the "claimCheckNumber" element
     */
    public void setClaimCheckNumber(java.lang.String claimCheckNumber)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CLAIMCHECKNUMBER$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CLAIMCHECKNUMBER$2);
            }
            target.setStringValue(claimCheckNumber);
        }
    }
    
    /**
     * Sets (as xml) the "claimCheckNumber" element
     */
    public void xsetClaimCheckNumber(org.apache.xmlbeans.XmlString claimCheckNumber)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CLAIMCHECKNUMBER$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CLAIMCHECKNUMBER$2);
            }
            target.set(claimCheckNumber);
        }
    }
    
    /**
     * Nils the "claimCheckNumber" element
     */
    public void setNilClaimCheckNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CLAIMCHECKNUMBER$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CLAIMCHECKNUMBER$2);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "claimCheckNumber" element
     */
    public void unsetClaimCheckNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CLAIMCHECKNUMBER$2, 0);
        }
    }
    
    /**
     * Gets the "destinationStation" element
     */
    public java.lang.String getDestinationStation()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DESTINATIONSTATION$4, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "destinationStation" element
     */
    public org.apache.xmlbeans.XmlString xgetDestinationStation()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DESTINATIONSTATION$4, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "destinationStation" element
     */
    public boolean isNilDestinationStation()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DESTINATIONSTATION$4, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "destinationStation" element
     */
    public boolean isSetDestinationStation()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DESTINATIONSTATION$4) != 0;
        }
    }
    
    /**
     * Sets the "destinationStation" element
     */
    public void setDestinationStation(java.lang.String destinationStation)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DESTINATIONSTATION$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DESTINATIONSTATION$4);
            }
            target.setStringValue(destinationStation);
        }
    }
    
    /**
     * Sets (as xml) the "destinationStation" element
     */
    public void xsetDestinationStation(org.apache.xmlbeans.XmlString destinationStation)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DESTINATIONSTATION$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DESTINATIONSTATION$4);
            }
            target.set(destinationStation);
        }
    }
    
    /**
     * Nils the "destinationStation" element
     */
    public void setNilDestinationStation()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DESTINATIONSTATION$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DESTINATIONSTATION$4);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "destinationStation" element
     */
    public void unsetDestinationStation()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DESTINATIONSTATION$4, 0);
        }
    }
    
    /**
     * Gets the "expediteNumber" element
     */
    public java.lang.String getExpediteNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EXPEDITENUMBER$6, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "expediteNumber" element
     */
    public org.apache.xmlbeans.XmlString xgetExpediteNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(EXPEDITENUMBER$6, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "expediteNumber" element
     */
    public boolean isNilExpediteNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(EXPEDITENUMBER$6, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "expediteNumber" element
     */
    public boolean isSetExpediteNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(EXPEDITENUMBER$6) != 0;
        }
    }
    
    /**
     * Sets the "expediteNumber" element
     */
    public void setExpediteNumber(java.lang.String expediteNumber)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EXPEDITENUMBER$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(EXPEDITENUMBER$6);
            }
            target.setStringValue(expediteNumber);
        }
    }
    
    /**
     * Sets (as xml) the "expediteNumber" element
     */
    public void xsetExpediteNumber(org.apache.xmlbeans.XmlString expediteNumber)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(EXPEDITENUMBER$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(EXPEDITENUMBER$6);
            }
            target.set(expediteNumber);
        }
    }
    
    /**
     * Nils the "expediteNumber" element
     */
    public void setNilExpediteNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(EXPEDITENUMBER$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(EXPEDITENUMBER$6);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "expediteNumber" element
     */
    public void unsetExpediteNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(EXPEDITENUMBER$6, 0);
        }
    }
    
    /**
     * Gets the "faultStation" element
     */
    public java.lang.String getFaultStation()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FAULTSTATION$8, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "faultStation" element
     */
    public org.apache.xmlbeans.XmlString xgetFaultStation()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FAULTSTATION$8, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "faultStation" element
     */
    public boolean isNilFaultStation()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FAULTSTATION$8, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "faultStation" element
     */
    public boolean isSetFaultStation()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(FAULTSTATION$8) != 0;
        }
    }
    
    /**
     * Sets the "faultStation" element
     */
    public void setFaultStation(java.lang.String faultStation)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FAULTSTATION$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FAULTSTATION$8);
            }
            target.setStringValue(faultStation);
        }
    }
    
    /**
     * Sets (as xml) the "faultStation" element
     */
    public void xsetFaultStation(org.apache.xmlbeans.XmlString faultStation)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FAULTSTATION$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(FAULTSTATION$8);
            }
            target.set(faultStation);
        }
    }
    
    /**
     * Nils the "faultStation" element
     */
    public void setNilFaultStation()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FAULTSTATION$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(FAULTSTATION$8);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "faultStation" element
     */
    public void unsetFaultStation()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(FAULTSTATION$8, 0);
        }
    }
    
    /**
     * Gets array of all "forwardItinerary" elements
     */
    public com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary[] getForwardItineraryArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(FORWARDITINERARY$10, targetList);
            com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary[] result = new com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "forwardItinerary" element
     */
    public com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary getForwardItineraryArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary)get_store().find_element_user(FORWARDITINERARY$10, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Tests for nil ith "forwardItinerary" element
     */
    public boolean isNilForwardItineraryArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary)get_store().find_element_user(FORWARDITINERARY$10, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target.isNil();
        }
    }
    
    /**
     * Returns number of "forwardItinerary" element
     */
    public int sizeOfForwardItineraryArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(FORWARDITINERARY$10);
        }
    }
    
    /**
     * Sets array of all "forwardItinerary" element
     */
    public void setForwardItineraryArray(com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary[] forwardItineraryArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(forwardItineraryArray, FORWARDITINERARY$10);
        }
    }
    
    /**
     * Sets ith "forwardItinerary" element
     */
    public void setForwardItineraryArray(int i, com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary forwardItinerary)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary)get_store().find_element_user(FORWARDITINERARY$10, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(forwardItinerary);
        }
    }
    
    /**
     * Nils the ith "forwardItinerary" element
     */
    public void setNilForwardItineraryArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary)get_store().find_element_user(FORWARDITINERARY$10, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.setNil();
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "forwardItinerary" element
     */
    public com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary insertNewForwardItinerary(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary)get_store().insert_element_user(FORWARDITINERARY$10, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "forwardItinerary" element
     */
    public com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary addNewForwardItinerary()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary)get_store().add_element_user(FORWARDITINERARY$10);
            return target;
        }
    }
    
    /**
     * Removes the ith "forwardItinerary" element
     */
    public void removeForwardItinerary(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(FORWARDITINERARY$10, i);
        }
    }
    
    /**
     * Gets the "foundAtStation" element
     */
    public java.lang.String getFoundAtStation()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FOUNDATSTATION$12, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "foundAtStation" element
     */
    public org.apache.xmlbeans.XmlString xgetFoundAtStation()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FOUNDATSTATION$12, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "foundAtStation" element
     */
    public boolean isNilFoundAtStation()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FOUNDATSTATION$12, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "foundAtStation" element
     */
    public boolean isSetFoundAtStation()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(FOUNDATSTATION$12) != 0;
        }
    }
    
    /**
     * Sets the "foundAtStation" element
     */
    public void setFoundAtStation(java.lang.String foundAtStation)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FOUNDATSTATION$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FOUNDATSTATION$12);
            }
            target.setStringValue(foundAtStation);
        }
    }
    
    /**
     * Sets (as xml) the "foundAtStation" element
     */
    public void xsetFoundAtStation(org.apache.xmlbeans.XmlString foundAtStation)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FOUNDATSTATION$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(FOUNDATSTATION$12);
            }
            target.set(foundAtStation);
        }
    }
    
    /**
     * Nils the "foundAtStation" element
     */
    public void setNilFoundAtStation()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FOUNDATSTATION$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(FOUNDATSTATION$12);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "foundAtStation" element
     */
    public void unsetFoundAtStation()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(FOUNDATSTATION$12, 0);
        }
    }
    
    /**
     * Gets the "founddatetime" element
     */
    public java.util.Calendar getFounddatetime()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FOUNDDATETIME$14, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "founddatetime" element
     */
    public org.apache.xmlbeans.XmlDate xgetFounddatetime()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDate target = null;
            target = (org.apache.xmlbeans.XmlDate)get_store().find_element_user(FOUNDDATETIME$14, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "founddatetime" element
     */
    public boolean isNilFounddatetime()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDate target = null;
            target = (org.apache.xmlbeans.XmlDate)get_store().find_element_user(FOUNDDATETIME$14, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "founddatetime" element
     */
    public boolean isSetFounddatetime()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(FOUNDDATETIME$14) != 0;
        }
    }
    
    /**
     * Sets the "founddatetime" element
     */
    public void setFounddatetime(java.util.Calendar founddatetime)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FOUNDDATETIME$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FOUNDDATETIME$14);
            }
            target.setCalendarValue(founddatetime);
        }
    }
    
    /**
     * Sets (as xml) the "founddatetime" element
     */
    public void xsetFounddatetime(org.apache.xmlbeans.XmlDate founddatetime)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDate target = null;
            target = (org.apache.xmlbeans.XmlDate)get_store().find_element_user(FOUNDDATETIME$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDate)get_store().add_element_user(FOUNDDATETIME$14);
            }
            target.set(founddatetime);
        }
    }
    
    /**
     * Nils the "founddatetime" element
     */
    public void setNilFounddatetime()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDate target = null;
            target = (org.apache.xmlbeans.XmlDate)get_store().find_element_user(FOUNDDATETIME$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDate)get_store().add_element_user(FOUNDDATETIME$14);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "founddatetime" element
     */
    public void unsetFounddatetime()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(FOUNDDATETIME$14, 0);
        }
    }
    
    /**
     * Gets the "lossCode" element
     */
    public int getLossCode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LOSSCODE$16, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "lossCode" element
     */
    public org.apache.xmlbeans.XmlInt xgetLossCode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(LOSSCODE$16, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "lossCode" element
     */
    public boolean isNilLossCode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(LOSSCODE$16, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "lossCode" element
     */
    public boolean isSetLossCode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(LOSSCODE$16) != 0;
        }
    }
    
    /**
     * Sets the "lossCode" element
     */
    public void setLossCode(int lossCode)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LOSSCODE$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(LOSSCODE$16);
            }
            target.setIntValue(lossCode);
        }
    }
    
    /**
     * Sets (as xml) the "lossCode" element
     */
    public void xsetLossCode(org.apache.xmlbeans.XmlInt lossCode)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(LOSSCODE$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(LOSSCODE$16);
            }
            target.set(lossCode);
        }
    }
    
    /**
     * Nils the "lossCode" element
     */
    public void setNilLossCode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(LOSSCODE$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(LOSSCODE$16);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "lossCode" element
     */
    public void unsetLossCode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(LOSSCODE$16, 0);
        }
    }
    
    /**
     * Gets the "message" element
     */
    public java.lang.String getMessage()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MESSAGE$18, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "message" element
     */
    public org.apache.xmlbeans.XmlString xgetMessage()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MESSAGE$18, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "message" element
     */
    public boolean isNilMessage()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MESSAGE$18, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "message" element
     */
    public boolean isSetMessage()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(MESSAGE$18) != 0;
        }
    }
    
    /**
     * Sets the "message" element
     */
    public void setMessage(java.lang.String message)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MESSAGE$18, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(MESSAGE$18);
            }
            target.setStringValue(message);
        }
    }
    
    /**
     * Sets (as xml) the "message" element
     */
    public void xsetMessage(org.apache.xmlbeans.XmlString message)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MESSAGE$18, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(MESSAGE$18);
            }
            target.set(message);
        }
    }
    
    /**
     * Nils the "message" element
     */
    public void setNilMessage()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MESSAGE$18, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(MESSAGE$18);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "message" element
     */
    public void unsetMessage()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(MESSAGE$18, 0);
        }
    }
    
    /**
     * Gets the "specialInstructions" element
     */
    public java.lang.String getSpecialInstructions()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SPECIALINSTRUCTIONS$20, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "specialInstructions" element
     */
    public org.apache.xmlbeans.XmlString xgetSpecialInstructions()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SPECIALINSTRUCTIONS$20, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "specialInstructions" element
     */
    public boolean isNilSpecialInstructions()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SPECIALINSTRUCTIONS$20, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "specialInstructions" element
     */
    public boolean isSetSpecialInstructions()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(SPECIALINSTRUCTIONS$20) != 0;
        }
    }
    
    /**
     * Sets the "specialInstructions" element
     */
    public void setSpecialInstructions(java.lang.String specialInstructions)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SPECIALINSTRUCTIONS$20, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(SPECIALINSTRUCTIONS$20);
            }
            target.setStringValue(specialInstructions);
        }
    }
    
    /**
     * Sets (as xml) the "specialInstructions" element
     */
    public void xsetSpecialInstructions(org.apache.xmlbeans.XmlString specialInstructions)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SPECIALINSTRUCTIONS$20, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(SPECIALINSTRUCTIONS$20);
            }
            target.set(specialInstructions);
        }
    }
    
    /**
     * Nils the "specialInstructions" element
     */
    public void setNilSpecialInstructions()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SPECIALINSTRUCTIONS$20, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(SPECIALINSTRUCTIONS$20);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "specialInstructions" element
     */
    public void unsetSpecialInstructions()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(SPECIALINSTRUCTIONS$20, 0);
        }
    }
    
    /**
     * Gets the "tagOff" element
     */
    public boolean getTagOff()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TAGOFF$22, 0);
            if (target == null)
            {
                return false;
            }
            return target.getBooleanValue();
        }
    }
    
    /**
     * Gets (as xml) the "tagOff" element
     */
    public org.apache.xmlbeans.XmlBoolean xgetTagOff()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(TAGOFF$22, 0);
            return target;
        }
    }
    
    /**
     * True if has "tagOff" element
     */
    public boolean isSetTagOff()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(TAGOFF$22) != 0;
        }
    }
    
    /**
     * Sets the "tagOff" element
     */
    public void setTagOff(boolean tagOff)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TAGOFF$22, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(TAGOFF$22);
            }
            target.setBooleanValue(tagOff);
        }
    }
    
    /**
     * Sets (as xml) the "tagOff" element
     */
    public void xsetTagOff(org.apache.xmlbeans.XmlBoolean tagOff)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(TAGOFF$22, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlBoolean)get_store().add_element_user(TAGOFF$22);
            }
            target.set(tagOff);
        }
    }
    
    /**
     * Unsets the "tagOff" element
     */
    public void unsetTagOff()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(TAGOFF$22, 0);
        }
    }
}
