/*
 * XML Type:  Incident
 * Namespace: http://onlineclaims.ws.nettracer.bagnet.com/xsd
 * Java type: com.bagnet.nettracer.ws.onlineclaims.xsd.Incident
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.onlineclaims.xsd.impl;
/**
 * An XML Incident(@http://onlineclaims.ws.nettracer.bagnet.com/xsd).
 *
 * This is a complex type.
 */
public class IncidentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.onlineclaims.xsd.Incident
{
    
    public IncidentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName AUTHID$0 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "authID");
    private static final javax.xml.namespace.QName AUTHSTATUS$2 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "authStatus");
    private static final javax.xml.namespace.QName BAG$4 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "bag");
    private static final javax.xml.namespace.QName CLAIMCHECK$6 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "claimCheck");
    private static final javax.xml.namespace.QName DELIVERWITHOUTSIGNATURE$8 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "deliverWithoutSignature");
    private static final javax.xml.namespace.QName DELIVERYADDRESS$10 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "deliveryAddress");
    private static final javax.xml.namespace.QName DELIVERYTYPE$12 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "deliveryType");
    private static final javax.xml.namespace.QName EMAIL$14 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "email");
    private static final javax.xml.namespace.QName FIRSTNAME$16 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "firstName");
    private static final javax.xml.namespace.QName ITINERARY$18 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "itinerary");
    private static final javax.xml.namespace.QName LASTNAME$20 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "lastName");
    private static final javax.xml.namespace.QName OSI$22 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "osi");
    private static final javax.xml.namespace.QName PHONE$24 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "phone");
    private static final javax.xml.namespace.QName PNR$26 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "pnr");
    
    
    /**
     * Gets the "authID" element
     */
    public int getAuthID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(AUTHID$0, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "authID" element
     */
    public org.apache.xmlbeans.XmlInt xgetAuthID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(AUTHID$0, 0);
            return target;
        }
    }
    
    /**
     * True if has "authID" element
     */
    public boolean isSetAuthID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(AUTHID$0) != 0;
        }
    }
    
    /**
     * Sets the "authID" element
     */
    public void setAuthID(int authID)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(AUTHID$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(AUTHID$0);
            }
            target.setIntValue(authID);
        }
    }
    
    /**
     * Sets (as xml) the "authID" element
     */
    public void xsetAuthID(org.apache.xmlbeans.XmlInt authID)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(AUTHID$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(AUTHID$0);
            }
            target.set(authID);
        }
    }
    
    /**
     * Unsets the "authID" element
     */
    public void unsetAuthID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(AUTHID$0, 0);
        }
    }
    
    /**
     * Gets the "authStatus" element
     */
    public int getAuthStatus()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(AUTHSTATUS$2, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "authStatus" element
     */
    public org.apache.xmlbeans.XmlInt xgetAuthStatus()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(AUTHSTATUS$2, 0);
            return target;
        }
    }
    
    /**
     * True if has "authStatus" element
     */
    public boolean isSetAuthStatus()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(AUTHSTATUS$2) != 0;
        }
    }
    
    /**
     * Sets the "authStatus" element
     */
    public void setAuthStatus(int authStatus)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(AUTHSTATUS$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(AUTHSTATUS$2);
            }
            target.setIntValue(authStatus);
        }
    }
    
    /**
     * Sets (as xml) the "authStatus" element
     */
    public void xsetAuthStatus(org.apache.xmlbeans.XmlInt authStatus)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(AUTHSTATUS$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(AUTHSTATUS$2);
            }
            target.set(authStatus);
        }
    }
    
    /**
     * Unsets the "authStatus" element
     */
    public void unsetAuthStatus()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(AUTHSTATUS$2, 0);
        }
    }
    
    /**
     * Gets array of all "bag" elements
     */
    public com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentBag[] getBagArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(BAG$4, targetList);
            com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentBag[] result = new com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentBag[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "bag" element
     */
    public com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentBag getBagArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentBag target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentBag)get_store().find_element_user(BAG$4, i);
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
            com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentBag target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentBag)get_store().find_element_user(BAG$4, i);
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
            return get_store().count_elements(BAG$4);
        }
    }
    
    /**
     * Sets array of all "bag" element
     */
    public void setBagArray(com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentBag[] bagArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(bagArray, BAG$4);
        }
    }
    
    /**
     * Sets ith "bag" element
     */
    public void setBagArray(int i, com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentBag bag)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentBag target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentBag)get_store().find_element_user(BAG$4, i);
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
            com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentBag target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentBag)get_store().find_element_user(BAG$4, i);
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
    public com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentBag insertNewBag(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentBag target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentBag)get_store().insert_element_user(BAG$4, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "bag" element
     */
    public com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentBag addNewBag()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentBag target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentBag)get_store().add_element_user(BAG$4);
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
            get_store().remove_element(BAG$4, i);
        }
    }
    
    /**
     * Gets array of all "claimCheck" elements
     */
    public java.lang.String[] getClaimCheckArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(CLAIMCHECK$6, targetList);
            java.lang.String[] result = new java.lang.String[targetList.size()];
            for (int i = 0, len = targetList.size() ; i < len ; i++)
                result[i] = ((org.apache.xmlbeans.SimpleValue)targetList.get(i)).getStringValue();
            return result;
        }
    }
    
    /**
     * Gets ith "claimCheck" element
     */
    public java.lang.String getClaimCheckArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CLAIMCHECK$6, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) array of all "claimCheck" elements
     */
    public org.apache.xmlbeans.XmlString[] xgetClaimCheckArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(CLAIMCHECK$6, targetList);
            org.apache.xmlbeans.XmlString[] result = new org.apache.xmlbeans.XmlString[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets (as xml) ith "claimCheck" element
     */
    public org.apache.xmlbeans.XmlString xgetClaimCheckArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CLAIMCHECK$6, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return (org.apache.xmlbeans.XmlString)target;
        }
    }
    
    /**
     * Tests for nil ith "claimCheck" element
     */
    public boolean isNilClaimCheckArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CLAIMCHECK$6, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target.isNil();
        }
    }
    
    /**
     * Returns number of "claimCheck" element
     */
    public int sizeOfClaimCheckArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CLAIMCHECK$6);
        }
    }
    
    /**
     * Sets array of all "claimCheck" element
     */
    public void setClaimCheckArray(java.lang.String[] claimCheckArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(claimCheckArray, CLAIMCHECK$6);
        }
    }
    
    /**
     * Sets ith "claimCheck" element
     */
    public void setClaimCheckArray(int i, java.lang.String claimCheck)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CLAIMCHECK$6, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.setStringValue(claimCheck);
        }
    }
    
    /**
     * Sets (as xml) array of all "claimCheck" element
     */
    public void xsetClaimCheckArray(org.apache.xmlbeans.XmlString[]claimCheckArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(claimCheckArray, CLAIMCHECK$6);
        }
    }
    
    /**
     * Sets (as xml) ith "claimCheck" element
     */
    public void xsetClaimCheckArray(int i, org.apache.xmlbeans.XmlString claimCheck)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CLAIMCHECK$6, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(claimCheck);
        }
    }
    
    /**
     * Nils the ith "claimCheck" element
     */
    public void setNilClaimCheckArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CLAIMCHECK$6, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.setNil();
        }
    }
    
    /**
     * Inserts the value as the ith "claimCheck" element
     */
    public void insertClaimCheck(int i, java.lang.String claimCheck)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = 
                (org.apache.xmlbeans.SimpleValue)get_store().insert_element_user(CLAIMCHECK$6, i);
            target.setStringValue(claimCheck);
        }
    }
    
    /**
     * Appends the value as the last "claimCheck" element
     */
    public void addClaimCheck(java.lang.String claimCheck)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CLAIMCHECK$6);
            target.setStringValue(claimCheck);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "claimCheck" element
     */
    public org.apache.xmlbeans.XmlString insertNewClaimCheck(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().insert_element_user(CLAIMCHECK$6, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "claimCheck" element
     */
    public org.apache.xmlbeans.XmlString addNewClaimCheck()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CLAIMCHECK$6);
            return target;
        }
    }
    
    /**
     * Removes the ith "claimCheck" element
     */
    public void removeClaimCheck(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CLAIMCHECK$6, i);
        }
    }
    
    /**
     * Gets the "deliverWithoutSignature" element
     */
    public boolean getDeliverWithoutSignature()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DELIVERWITHOUTSIGNATURE$8, 0);
            if (target == null)
            {
                return false;
            }
            return target.getBooleanValue();
        }
    }
    
    /**
     * Gets (as xml) the "deliverWithoutSignature" element
     */
    public org.apache.xmlbeans.XmlBoolean xgetDeliverWithoutSignature()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(DELIVERWITHOUTSIGNATURE$8, 0);
            return target;
        }
    }
    
    /**
     * True if has "deliverWithoutSignature" element
     */
    public boolean isSetDeliverWithoutSignature()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DELIVERWITHOUTSIGNATURE$8) != 0;
        }
    }
    
    /**
     * Sets the "deliverWithoutSignature" element
     */
    public void setDeliverWithoutSignature(boolean deliverWithoutSignature)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DELIVERWITHOUTSIGNATURE$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DELIVERWITHOUTSIGNATURE$8);
            }
            target.setBooleanValue(deliverWithoutSignature);
        }
    }
    
    /**
     * Sets (as xml) the "deliverWithoutSignature" element
     */
    public void xsetDeliverWithoutSignature(org.apache.xmlbeans.XmlBoolean deliverWithoutSignature)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(DELIVERWITHOUTSIGNATURE$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlBoolean)get_store().add_element_user(DELIVERWITHOUTSIGNATURE$8);
            }
            target.set(deliverWithoutSignature);
        }
    }
    
    /**
     * Unsets the "deliverWithoutSignature" element
     */
    public void unsetDeliverWithoutSignature()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DELIVERWITHOUTSIGNATURE$8, 0);
        }
    }
    
    /**
     * Gets the "deliveryAddress" element
     */
    public com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentAddress getDeliveryAddress()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentAddress target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentAddress)get_store().find_element_user(DELIVERYADDRESS$10, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Tests for nil "deliveryAddress" element
     */
    public boolean isNilDeliveryAddress()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentAddress target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentAddress)get_store().find_element_user(DELIVERYADDRESS$10, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "deliveryAddress" element
     */
    public boolean isSetDeliveryAddress()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DELIVERYADDRESS$10) != 0;
        }
    }
    
    /**
     * Sets the "deliveryAddress" element
     */
    public void setDeliveryAddress(com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentAddress deliveryAddress)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentAddress target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentAddress)get_store().find_element_user(DELIVERYADDRESS$10, 0);
            if (target == null)
            {
                target = (com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentAddress)get_store().add_element_user(DELIVERYADDRESS$10);
            }
            target.set(deliveryAddress);
        }
    }
    
    /**
     * Appends and returns a new empty "deliveryAddress" element
     */
    public com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentAddress addNewDeliveryAddress()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentAddress target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentAddress)get_store().add_element_user(DELIVERYADDRESS$10);
            return target;
        }
    }
    
    /**
     * Nils the "deliveryAddress" element
     */
    public void setNilDeliveryAddress()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentAddress target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentAddress)get_store().find_element_user(DELIVERYADDRESS$10, 0);
            if (target == null)
            {
                target = (com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentAddress)get_store().add_element_user(DELIVERYADDRESS$10);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "deliveryAddress" element
     */
    public void unsetDeliveryAddress()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DELIVERYADDRESS$10, 0);
        }
    }
    
    /**
     * Gets the "deliveryType" element
     */
    public int getDeliveryType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DELIVERYTYPE$12, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "deliveryType" element
     */
    public org.apache.xmlbeans.XmlInt xgetDeliveryType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(DELIVERYTYPE$12, 0);
            return target;
        }
    }
    
    /**
     * True if has "deliveryType" element
     */
    public boolean isSetDeliveryType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DELIVERYTYPE$12) != 0;
        }
    }
    
    /**
     * Sets the "deliveryType" element
     */
    public void setDeliveryType(int deliveryType)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DELIVERYTYPE$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DELIVERYTYPE$12);
            }
            target.setIntValue(deliveryType);
        }
    }
    
    /**
     * Sets (as xml) the "deliveryType" element
     */
    public void xsetDeliveryType(org.apache.xmlbeans.XmlInt deliveryType)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(DELIVERYTYPE$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(DELIVERYTYPE$12);
            }
            target.set(deliveryType);
        }
    }
    
    /**
     * Unsets the "deliveryType" element
     */
    public void unsetDeliveryType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DELIVERYTYPE$12, 0);
        }
    }
    
    /**
     * Gets the "email" element
     */
    public java.lang.String getEmail()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EMAIL$14, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "email" element
     */
    public org.apache.xmlbeans.XmlString xgetEmail()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(EMAIL$14, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "email" element
     */
    public boolean isNilEmail()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(EMAIL$14, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "email" element
     */
    public boolean isSetEmail()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(EMAIL$14) != 0;
        }
    }
    
    /**
     * Sets the "email" element
     */
    public void setEmail(java.lang.String email)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EMAIL$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(EMAIL$14);
            }
            target.setStringValue(email);
        }
    }
    
    /**
     * Sets (as xml) the "email" element
     */
    public void xsetEmail(org.apache.xmlbeans.XmlString email)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(EMAIL$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(EMAIL$14);
            }
            target.set(email);
        }
    }
    
    /**
     * Nils the "email" element
     */
    public void setNilEmail()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(EMAIL$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(EMAIL$14);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "email" element
     */
    public void unsetEmail()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(EMAIL$14, 0);
        }
    }
    
    /**
     * Gets the "firstName" element
     */
    public java.lang.String getFirstName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FIRSTNAME$16, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "firstName" element
     */
    public org.apache.xmlbeans.XmlString xgetFirstName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FIRSTNAME$16, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "firstName" element
     */
    public boolean isNilFirstName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FIRSTNAME$16, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "firstName" element
     */
    public boolean isSetFirstName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(FIRSTNAME$16) != 0;
        }
    }
    
    /**
     * Sets the "firstName" element
     */
    public void setFirstName(java.lang.String firstName)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FIRSTNAME$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FIRSTNAME$16);
            }
            target.setStringValue(firstName);
        }
    }
    
    /**
     * Sets (as xml) the "firstName" element
     */
    public void xsetFirstName(org.apache.xmlbeans.XmlString firstName)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FIRSTNAME$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(FIRSTNAME$16);
            }
            target.set(firstName);
        }
    }
    
    /**
     * Nils the "firstName" element
     */
    public void setNilFirstName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FIRSTNAME$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(FIRSTNAME$16);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "firstName" element
     */
    public void unsetFirstName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(FIRSTNAME$16, 0);
        }
    }
    
    /**
     * Gets array of all "itinerary" elements
     */
    public com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentItinerary[] getItineraryArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(ITINERARY$18, targetList);
            com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentItinerary[] result = new com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentItinerary[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "itinerary" element
     */
    public com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentItinerary getItineraryArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentItinerary target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentItinerary)get_store().find_element_user(ITINERARY$18, i);
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
            com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentItinerary target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentItinerary)get_store().find_element_user(ITINERARY$18, i);
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
            return get_store().count_elements(ITINERARY$18);
        }
    }
    
    /**
     * Sets array of all "itinerary" element
     */
    public void setItineraryArray(com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentItinerary[] itineraryArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(itineraryArray, ITINERARY$18);
        }
    }
    
    /**
     * Sets ith "itinerary" element
     */
    public void setItineraryArray(int i, com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentItinerary itinerary)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentItinerary target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentItinerary)get_store().find_element_user(ITINERARY$18, i);
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
            com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentItinerary target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentItinerary)get_store().find_element_user(ITINERARY$18, i);
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
    public com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentItinerary insertNewItinerary(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentItinerary target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentItinerary)get_store().insert_element_user(ITINERARY$18, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "itinerary" element
     */
    public com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentItinerary addNewItinerary()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentItinerary target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentItinerary)get_store().add_element_user(ITINERARY$18);
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
            get_store().remove_element(ITINERARY$18, i);
        }
    }
    
    /**
     * Gets the "lastName" element
     */
    public java.lang.String getLastName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LASTNAME$20, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "lastName" element
     */
    public org.apache.xmlbeans.XmlString xgetLastName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(LASTNAME$20, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "lastName" element
     */
    public boolean isNilLastName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(LASTNAME$20, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "lastName" element
     */
    public boolean isSetLastName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(LASTNAME$20) != 0;
        }
    }
    
    /**
     * Sets the "lastName" element
     */
    public void setLastName(java.lang.String lastName)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LASTNAME$20, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(LASTNAME$20);
            }
            target.setStringValue(lastName);
        }
    }
    
    /**
     * Sets (as xml) the "lastName" element
     */
    public void xsetLastName(org.apache.xmlbeans.XmlString lastName)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(LASTNAME$20, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(LASTNAME$20);
            }
            target.set(lastName);
        }
    }
    
    /**
     * Nils the "lastName" element
     */
    public void setNilLastName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(LASTNAME$20, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(LASTNAME$20);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "lastName" element
     */
    public void unsetLastName()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(LASTNAME$20, 0);
        }
    }
    
    /**
     * Gets the "osi" element
     */
    public java.lang.String getOsi()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(OSI$22, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "osi" element
     */
    public org.apache.xmlbeans.XmlString xgetOsi()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(OSI$22, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "osi" element
     */
    public boolean isNilOsi()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(OSI$22, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "osi" element
     */
    public boolean isSetOsi()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(OSI$22) != 0;
        }
    }
    
    /**
     * Sets the "osi" element
     */
    public void setOsi(java.lang.String osi)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(OSI$22, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(OSI$22);
            }
            target.setStringValue(osi);
        }
    }
    
    /**
     * Sets (as xml) the "osi" element
     */
    public void xsetOsi(org.apache.xmlbeans.XmlString osi)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(OSI$22, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(OSI$22);
            }
            target.set(osi);
        }
    }
    
    /**
     * Nils the "osi" element
     */
    public void setNilOsi()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(OSI$22, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(OSI$22);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "osi" element
     */
    public void unsetOsi()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(OSI$22, 0);
        }
    }
    
    /**
     * Gets array of all "phone" elements
     */
    public com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentPhone[] getPhoneArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(PHONE$24, targetList);
            com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentPhone[] result = new com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentPhone[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "phone" element
     */
    public com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentPhone getPhoneArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentPhone target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentPhone)get_store().find_element_user(PHONE$24, i);
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
            com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentPhone target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentPhone)get_store().find_element_user(PHONE$24, i);
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
            return get_store().count_elements(PHONE$24);
        }
    }
    
    /**
     * Sets array of all "phone" element
     */
    public void setPhoneArray(com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentPhone[] phoneArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(phoneArray, PHONE$24);
        }
    }
    
    /**
     * Sets ith "phone" element
     */
    public void setPhoneArray(int i, com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentPhone phone)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentPhone target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentPhone)get_store().find_element_user(PHONE$24, i);
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
            com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentPhone target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentPhone)get_store().find_element_user(PHONE$24, i);
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
    public com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentPhone insertNewPhone(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentPhone target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentPhone)get_store().insert_element_user(PHONE$24, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "phone" element
     */
    public com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentPhone addNewPhone()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentPhone target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentPhone)get_store().add_element_user(PHONE$24);
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
            get_store().remove_element(PHONE$24, i);
        }
    }
    
    /**
     * Gets the "pnr" element
     */
    public java.lang.String getPnr()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PNR$26, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "pnr" element
     */
    public org.apache.xmlbeans.XmlString xgetPnr()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PNR$26, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "pnr" element
     */
    public boolean isNilPnr()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PNR$26, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "pnr" element
     */
    public boolean isSetPnr()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(PNR$26) != 0;
        }
    }
    
    /**
     * Sets the "pnr" element
     */
    public void setPnr(java.lang.String pnr)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PNR$26, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PNR$26);
            }
            target.setStringValue(pnr);
        }
    }
    
    /**
     * Sets (as xml) the "pnr" element
     */
    public void xsetPnr(org.apache.xmlbeans.XmlString pnr)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PNR$26, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PNR$26);
            }
            target.set(pnr);
        }
    }
    
    /**
     * Nils the "pnr" element
     */
    public void setNilPnr()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PNR$26, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PNR$26);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "pnr" element
     */
    public void unsetPnr()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(PNR$26, 0);
        }
    }
}
