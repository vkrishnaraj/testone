/*
 * XML Type:  WS_PVItem
 * Namespace: http://paxview.v1_1.ws.nettracer.bagnet.com/xsd
 * Java type: com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVItem
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.v1_1.paxview.xsd.impl;
/**
 * An XML WS_PVItem(@http://paxview.v1_1.ws.nettracer.bagnet.com/xsd).
 *
 * This is a complex type.
 */
public class WSPVItemImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVItem
{
    
    public WSPVItemImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName BAGSTATUS$0 = 
        new javax.xml.namespace.QName("http://paxview.v1_1.ws.nettracer.bagnet.com/xsd", "bagstatus");
    private static final javax.xml.namespace.QName CLAIMCHECKNUM$2 = 
        new javax.xml.namespace.QName("http://paxview.v1_1.ws.nettracer.bagnet.com/xsd", "claimchecknum");
    private static final javax.xml.namespace.QName COLOR$4 = 
        new javax.xml.namespace.QName("http://paxview.v1_1.ws.nettracer.bagnet.com/xsd", "color");
    private static final javax.xml.namespace.QName DELIVERYADDRESS$6 = 
        new javax.xml.namespace.QName("http://paxview.v1_1.ws.nettracer.bagnet.com/xsd", "deliveryAddress");
    private static final javax.xml.namespace.QName DELIVERYSTATUS$8 = 
        new javax.xml.namespace.QName("http://paxview.v1_1.ws.nettracer.bagnet.com/xsd", "deliveryStatus");
    private static final javax.xml.namespace.QName LASTDELIVERYUPDATE$10 = 
        new javax.xml.namespace.QName("http://paxview.v1_1.ws.nettracer.bagnet.com/xsd", "lastDeliveryUpdate");
    private static final javax.xml.namespace.QName TYPE$12 = 
        new javax.xml.namespace.QName("http://paxview.v1_1.ws.nettracer.bagnet.com/xsd", "type");
    
    
    /**
     * Gets the "bagstatus" element
     */
    public java.lang.String getBagstatus()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(BAGSTATUS$0, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(BAGSTATUS$0, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(BAGSTATUS$0, 0);
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
            return get_store().count_elements(BAGSTATUS$0) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(BAGSTATUS$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(BAGSTATUS$0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(BAGSTATUS$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(BAGSTATUS$0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(BAGSTATUS$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(BAGSTATUS$0);
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
            get_store().remove_element(BAGSTATUS$0, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CLAIMCHECKNUM$2, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CLAIMCHECKNUM$2, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CLAIMCHECKNUM$2, 0);
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
            return get_store().count_elements(CLAIMCHECKNUM$2) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CLAIMCHECKNUM$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CLAIMCHECKNUM$2);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CLAIMCHECKNUM$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CLAIMCHECKNUM$2);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CLAIMCHECKNUM$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CLAIMCHECKNUM$2);
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
            get_store().remove_element(CLAIMCHECKNUM$2, 0);
        }
    }
    
    /**
     * Gets the "color" element
     */
    public java.lang.String getColor()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COLOR$4, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "color" element
     */
    public org.apache.xmlbeans.XmlString xgetColor()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COLOR$4, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "color" element
     */
    public boolean isNilColor()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COLOR$4, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "color" element
     */
    public boolean isSetColor()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(COLOR$4) != 0;
        }
    }
    
    /**
     * Sets the "color" element
     */
    public void setColor(java.lang.String color)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COLOR$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(COLOR$4);
            }
            target.setStringValue(color);
        }
    }
    
    /**
     * Sets (as xml) the "color" element
     */
    public void xsetColor(org.apache.xmlbeans.XmlString color)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COLOR$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(COLOR$4);
            }
            target.set(color);
        }
    }
    
    /**
     * Nils the "color" element
     */
    public void setNilColor()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COLOR$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(COLOR$4);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "color" element
     */
    public void unsetColor()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(COLOR$4, 0);
        }
    }
    
    /**
     * Gets the "deliveryAddress" element
     */
    public java.lang.String getDeliveryAddress()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DELIVERYADDRESS$6, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "deliveryAddress" element
     */
    public org.apache.xmlbeans.XmlString xgetDeliveryAddress()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DELIVERYADDRESS$6, 0);
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
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DELIVERYADDRESS$6, 0);
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
            return get_store().count_elements(DELIVERYADDRESS$6) != 0;
        }
    }
    
    /**
     * Sets the "deliveryAddress" element
     */
    public void setDeliveryAddress(java.lang.String deliveryAddress)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DELIVERYADDRESS$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DELIVERYADDRESS$6);
            }
            target.setStringValue(deliveryAddress);
        }
    }
    
    /**
     * Sets (as xml) the "deliveryAddress" element
     */
    public void xsetDeliveryAddress(org.apache.xmlbeans.XmlString deliveryAddress)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DELIVERYADDRESS$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DELIVERYADDRESS$6);
            }
            target.set(deliveryAddress);
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
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DELIVERYADDRESS$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DELIVERYADDRESS$6);
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
            get_store().remove_element(DELIVERYADDRESS$6, 0);
        }
    }
    
    /**
     * Gets the "deliveryStatus" element
     */
    public com.bagnet.nettracer.tracing.db.xsd.Status getDeliveryStatus()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.tracing.db.xsd.Status target = null;
            target = (com.bagnet.nettracer.tracing.db.xsd.Status)get_store().find_element_user(DELIVERYSTATUS$8, 0);
            if (target == null)
            {
                return null;
            }
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
            com.bagnet.nettracer.tracing.db.xsd.Status target = null;
            target = (com.bagnet.nettracer.tracing.db.xsd.Status)get_store().find_element_user(DELIVERYSTATUS$8, 0);
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
            return get_store().count_elements(DELIVERYSTATUS$8) != 0;
        }
    }
    
    /**
     * Sets the "deliveryStatus" element
     */
    public void setDeliveryStatus(com.bagnet.nettracer.tracing.db.xsd.Status deliveryStatus)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.tracing.db.xsd.Status target = null;
            target = (com.bagnet.nettracer.tracing.db.xsd.Status)get_store().find_element_user(DELIVERYSTATUS$8, 0);
            if (target == null)
            {
                target = (com.bagnet.nettracer.tracing.db.xsd.Status)get_store().add_element_user(DELIVERYSTATUS$8);
            }
            target.set(deliveryStatus);
        }
    }
    
    /**
     * Appends and returns a new empty "deliveryStatus" element
     */
    public com.bagnet.nettracer.tracing.db.xsd.Status addNewDeliveryStatus()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.tracing.db.xsd.Status target = null;
            target = (com.bagnet.nettracer.tracing.db.xsd.Status)get_store().add_element_user(DELIVERYSTATUS$8);
            return target;
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
            com.bagnet.nettracer.tracing.db.xsd.Status target = null;
            target = (com.bagnet.nettracer.tracing.db.xsd.Status)get_store().find_element_user(DELIVERYSTATUS$8, 0);
            if (target == null)
            {
                target = (com.bagnet.nettracer.tracing.db.xsd.Status)get_store().add_element_user(DELIVERYSTATUS$8);
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
            get_store().remove_element(DELIVERYSTATUS$8, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LASTDELIVERYUPDATE$10, 0);
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
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(LASTDELIVERYUPDATE$10, 0);
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
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(LASTDELIVERYUPDATE$10, 0);
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
            return get_store().count_elements(LASTDELIVERYUPDATE$10) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LASTDELIVERYUPDATE$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(LASTDELIVERYUPDATE$10);
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
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(LASTDELIVERYUPDATE$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(LASTDELIVERYUPDATE$10);
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
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(LASTDELIVERYUPDATE$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(LASTDELIVERYUPDATE$10);
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
            get_store().remove_element(LASTDELIVERYUPDATE$10, 0);
        }
    }
    
    /**
     * Gets the "type" element
     */
    public java.lang.String getType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TYPE$12, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "type" element
     */
    public org.apache.xmlbeans.XmlString xgetType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TYPE$12, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "type" element
     */
    public boolean isNilType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TYPE$12, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "type" element
     */
    public boolean isSetType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(TYPE$12) != 0;
        }
    }
    
    /**
     * Sets the "type" element
     */
    public void setType(java.lang.String type)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TYPE$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(TYPE$12);
            }
            target.setStringValue(type);
        }
    }
    
    /**
     * Sets (as xml) the "type" element
     */
    public void xsetType(org.apache.xmlbeans.XmlString type)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TYPE$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(TYPE$12);
            }
            target.set(type);
        }
    }
    
    /**
     * Nils the "type" element
     */
    public void setNilType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TYPE$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(TYPE$12);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "type" element
     */
    public void unsetType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(TYPE$12, 0);
        }
    }
}
