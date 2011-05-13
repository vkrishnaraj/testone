/*
 * XML Type:  WS_PVItem
 * Namespace: http://pojo.core.ws.nettracer.bagnet.com/xsd
 * Java type: com.bagnet.nettracer.ws.core.pojo.xsd.WSPVItem
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.core.pojo.xsd.impl;
/**
 * An XML WS_PVItem(@http://pojo.core.ws.nettracer.bagnet.com/xsd).
 *
 * This is a complex type.
 */
public class WSPVItemImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.core.pojo.xsd.WSPVItem
{
    
    public WSPVItemImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName ADDRESS1$0 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "address1");
    private static final javax.xml.namespace.QName ADDRESS2$2 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "address2");
    private static final javax.xml.namespace.QName BAGSTATUS$4 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "bagstatus");
    private static final javax.xml.namespace.QName CITY$6 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "city");
    private static final javax.xml.namespace.QName CLAIMCHECKNUM$8 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "claimchecknum");
    private static final javax.xml.namespace.QName DELIVERYSTATUS$10 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "deliveryStatus");
    private static final javax.xml.namespace.QName LASTDELIVERYUPDATE$12 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "lastDeliveryUpdate");
    private static final javax.xml.namespace.QName STATEID$14 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "state_ID");
    private static final javax.xml.namespace.QName ZIP$16 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "zip");
    
    
    /**
     * Gets the "address1" element
     */
    public java.lang.String getAddress1()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ADDRESS1$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "address1" element
     */
    public org.apache.xmlbeans.XmlString xgetAddress1()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ADDRESS1$0, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "address1" element
     */
    public boolean isNilAddress1()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ADDRESS1$0, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "address1" element
     */
    public boolean isSetAddress1()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(ADDRESS1$0) != 0;
        }
    }
    
    /**
     * Sets the "address1" element
     */
    public void setAddress1(java.lang.String address1)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ADDRESS1$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ADDRESS1$0);
            }
            target.setStringValue(address1);
        }
    }
    
    /**
     * Sets (as xml) the "address1" element
     */
    public void xsetAddress1(org.apache.xmlbeans.XmlString address1)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ADDRESS1$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(ADDRESS1$0);
            }
            target.set(address1);
        }
    }
    
    /**
     * Nils the "address1" element
     */
    public void setNilAddress1()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ADDRESS1$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(ADDRESS1$0);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "address1" element
     */
    public void unsetAddress1()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(ADDRESS1$0, 0);
        }
    }
    
    /**
     * Gets the "address2" element
     */
    public java.lang.String getAddress2()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ADDRESS2$2, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "address2" element
     */
    public org.apache.xmlbeans.XmlString xgetAddress2()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ADDRESS2$2, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "address2" element
     */
    public boolean isNilAddress2()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ADDRESS2$2, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "address2" element
     */
    public boolean isSetAddress2()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(ADDRESS2$2) != 0;
        }
    }
    
    /**
     * Sets the "address2" element
     */
    public void setAddress2(java.lang.String address2)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ADDRESS2$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ADDRESS2$2);
            }
            target.setStringValue(address2);
        }
    }
    
    /**
     * Sets (as xml) the "address2" element
     */
    public void xsetAddress2(org.apache.xmlbeans.XmlString address2)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ADDRESS2$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(ADDRESS2$2);
            }
            target.set(address2);
        }
    }
    
    /**
     * Nils the "address2" element
     */
    public void setNilAddress2()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ADDRESS2$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(ADDRESS2$2);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "address2" element
     */
    public void unsetAddress2()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(ADDRESS2$2, 0);
        }
    }
    
    /**
     * Gets the "bagstatus" element
     */
    public java.lang.String getBagstatus()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(BAGSTATUS$4, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "bagstatus" element
     */
    public org.apache.xmlbeans.XmlString xgetBagstatus()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(BAGSTATUS$4, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "bagstatus" element
     */
    public boolean isNilBagstatus()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(BAGSTATUS$4, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "bagstatus" element
     */
    public boolean isSetBagstatus()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(BAGSTATUS$4) != 0;
        }
    }
    
    /**
     * Sets the "bagstatus" element
     */
    public void setBagstatus(java.lang.String bagstatus)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(BAGSTATUS$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(BAGSTATUS$4);
            }
            target.setStringValue(bagstatus);
        }
    }
    
    /**
     * Sets (as xml) the "bagstatus" element
     */
    public void xsetBagstatus(org.apache.xmlbeans.XmlString bagstatus)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(BAGSTATUS$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(BAGSTATUS$4);
            }
            target.set(bagstatus);
        }
    }
    
    /**
     * Nils the "bagstatus" element
     */
    public void setNilBagstatus()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(BAGSTATUS$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(BAGSTATUS$4);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "bagstatus" element
     */
    public void unsetBagstatus()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(BAGSTATUS$4, 0);
        }
    }
    
    /**
     * Gets the "city" element
     */
    public java.lang.String getCity()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CITY$6, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "city" element
     */
    public org.apache.xmlbeans.XmlString xgetCity()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CITY$6, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "city" element
     */
    public boolean isNilCity()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CITY$6, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "city" element
     */
    public boolean isSetCity()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CITY$6) != 0;
        }
    }
    
    /**
     * Sets the "city" element
     */
    public void setCity(java.lang.String city)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CITY$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CITY$6);
            }
            target.setStringValue(city);
        }
    }
    
    /**
     * Sets (as xml) the "city" element
     */
    public void xsetCity(org.apache.xmlbeans.XmlString city)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CITY$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CITY$6);
            }
            target.set(city);
        }
    }
    
    /**
     * Nils the "city" element
     */
    public void setNilCity()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CITY$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CITY$6);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "city" element
     */
    public void unsetCity()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CITY$6, 0);
        }
    }
    
    /**
     * Gets the "claimchecknum" element
     */
    public java.lang.String getClaimchecknum()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CLAIMCHECKNUM$8, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "claimchecknum" element
     */
    public org.apache.xmlbeans.XmlString xgetClaimchecknum()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CLAIMCHECKNUM$8, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "claimchecknum" element
     */
    public boolean isNilClaimchecknum()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CLAIMCHECKNUM$8, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "claimchecknum" element
     */
    public boolean isSetClaimchecknum()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CLAIMCHECKNUM$8) != 0;
        }
    }
    
    /**
     * Sets the "claimchecknum" element
     */
    public void setClaimchecknum(java.lang.String claimchecknum)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CLAIMCHECKNUM$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CLAIMCHECKNUM$8);
            }
            target.setStringValue(claimchecknum);
        }
    }
    
    /**
     * Sets (as xml) the "claimchecknum" element
     */
    public void xsetClaimchecknum(org.apache.xmlbeans.XmlString claimchecknum)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CLAIMCHECKNUM$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CLAIMCHECKNUM$8);
            }
            target.set(claimchecknum);
        }
    }
    
    /**
     * Nils the "claimchecknum" element
     */
    public void setNilClaimchecknum()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CLAIMCHECKNUM$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CLAIMCHECKNUM$8);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "claimchecknum" element
     */
    public void unsetClaimchecknum()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CLAIMCHECKNUM$8, 0);
        }
    }
    
    /**
     * Gets the "deliveryStatus" element
     */
    public java.lang.String getDeliveryStatus()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DELIVERYSTATUS$10, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "deliveryStatus" element
     */
    public org.apache.xmlbeans.XmlString xgetDeliveryStatus()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DELIVERYSTATUS$10, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "deliveryStatus" element
     */
    public boolean isNilDeliveryStatus()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DELIVERYSTATUS$10, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "deliveryStatus" element
     */
    public boolean isSetDeliveryStatus()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DELIVERYSTATUS$10) != 0;
        }
    }
    
    /**
     * Sets the "deliveryStatus" element
     */
    public void setDeliveryStatus(java.lang.String deliveryStatus)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DELIVERYSTATUS$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DELIVERYSTATUS$10);
            }
            target.setStringValue(deliveryStatus);
        }
    }
    
    /**
     * Sets (as xml) the "deliveryStatus" element
     */
    public void xsetDeliveryStatus(org.apache.xmlbeans.XmlString deliveryStatus)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DELIVERYSTATUS$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DELIVERYSTATUS$10);
            }
            target.set(deliveryStatus);
        }
    }
    
    /**
     * Nils the "deliveryStatus" element
     */
    public void setNilDeliveryStatus()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DELIVERYSTATUS$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DELIVERYSTATUS$10);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "deliveryStatus" element
     */
    public void unsetDeliveryStatus()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DELIVERYSTATUS$10, 0);
        }
    }
    
    /**
     * Gets the "lastDeliveryUpdate" element
     */
    public java.util.Calendar getLastDeliveryUpdate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LASTDELIVERYUPDATE$12, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "lastDeliveryUpdate" element
     */
    public org.apache.xmlbeans.XmlDateTime xgetLastDeliveryUpdate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(LASTDELIVERYUPDATE$12, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "lastDeliveryUpdate" element
     */
    public boolean isNilLastDeliveryUpdate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(LASTDELIVERYUPDATE$12, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "lastDeliveryUpdate" element
     */
    public boolean isSetLastDeliveryUpdate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(LASTDELIVERYUPDATE$12) != 0;
        }
    }
    
    /**
     * Sets the "lastDeliveryUpdate" element
     */
    public void setLastDeliveryUpdate(java.util.Calendar lastDeliveryUpdate)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LASTDELIVERYUPDATE$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(LASTDELIVERYUPDATE$12);
            }
            target.setCalendarValue(lastDeliveryUpdate);
        }
    }
    
    /**
     * Sets (as xml) the "lastDeliveryUpdate" element
     */
    public void xsetLastDeliveryUpdate(org.apache.xmlbeans.XmlDateTime lastDeliveryUpdate)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(LASTDELIVERYUPDATE$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(LASTDELIVERYUPDATE$12);
            }
            target.set(lastDeliveryUpdate);
        }
    }
    
    /**
     * Nils the "lastDeliveryUpdate" element
     */
    public void setNilLastDeliveryUpdate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(LASTDELIVERYUPDATE$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(LASTDELIVERYUPDATE$12);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "lastDeliveryUpdate" element
     */
    public void unsetLastDeliveryUpdate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(LASTDELIVERYUPDATE$12, 0);
        }
    }
    
    /**
     * Gets the "state_ID" element
     */
    public java.lang.String getStateID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STATEID$14, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "state_ID" element
     */
    public org.apache.xmlbeans.XmlString xgetStateID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STATEID$14, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "state_ID" element
     */
    public boolean isNilStateID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STATEID$14, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "state_ID" element
     */
    public boolean isSetStateID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(STATEID$14) != 0;
        }
    }
    
    /**
     * Sets the "state_ID" element
     */
    public void setStateID(java.lang.String stateID)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STATEID$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(STATEID$14);
            }
            target.setStringValue(stateID);
        }
    }
    
    /**
     * Sets (as xml) the "state_ID" element
     */
    public void xsetStateID(org.apache.xmlbeans.XmlString stateID)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STATEID$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(STATEID$14);
            }
            target.set(stateID);
        }
    }
    
    /**
     * Nils the "state_ID" element
     */
    public void setNilStateID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STATEID$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(STATEID$14);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "state_ID" element
     */
    public void unsetStateID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(STATEID$14, 0);
        }
    }
    
    /**
     * Gets the "zip" element
     */
    public java.lang.String getZip()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ZIP$16, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "zip" element
     */
    public org.apache.xmlbeans.XmlString xgetZip()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ZIP$16, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "zip" element
     */
    public boolean isNilZip()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ZIP$16, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "zip" element
     */
    public boolean isSetZip()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(ZIP$16) != 0;
        }
    }
    
    /**
     * Sets the "zip" element
     */
    public void setZip(java.lang.String zip)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ZIP$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ZIP$16);
            }
            target.setStringValue(zip);
        }
    }
    
    /**
     * Sets (as xml) the "zip" element
     */
    public void xsetZip(org.apache.xmlbeans.XmlString zip)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ZIP$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(ZIP$16);
            }
            target.set(zip);
        }
    }
    
    /**
     * Nils the "zip" element
     */
    public void setNilZip()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ZIP$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(ZIP$16);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "zip" element
     */
    public void unsetZip()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(ZIP$16, 0);
        }
    }
}
