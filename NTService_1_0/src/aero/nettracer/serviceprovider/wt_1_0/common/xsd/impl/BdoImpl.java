/*
 * XML Type:  Bdo
 * Namespace: http://common.wt_1_0.serviceprovider.nettracer.aero/xsd
 * Java type: aero.nettracer.serviceprovider.wt_1_0.common.xsd.Bdo
 *
 * Automatically generated - do not modify.
 */
package aero.nettracer.serviceprovider.wt_1_0.common.xsd.impl;
/**
 * An XML Bdo(@http://common.wt_1_0.serviceprovider.nettracer.aero/xsd).
 *
 * This is a complex type.
 */
public class BdoImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aero.nettracer.serviceprovider.wt_1_0.common.xsd.Bdo
{
    
    public BdoImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName ADDRESS1$0 = 
        new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd", "address1");
    private static final javax.xml.namespace.QName ADDRESS2$2 = 
        new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd", "address2");
    private static final javax.xml.namespace.QName AHLID$4 = 
        new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd", "ahlId");
    private static final javax.xml.namespace.QName AIRLINECODE$6 = 
        new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd", "airlineCode");
    private static final javax.xml.namespace.QName DELIVERYCOMPANY$8 = 
        new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd", "deliveryCompany");
    private static final javax.xml.namespace.QName DELIVERYDATE$10 = 
        new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd", "deliveryDate");
    private static final javax.xml.namespace.QName DELIVERYSTATIONCODE$12 = 
        new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd", "deliveryStationCode");
    private static final javax.xml.namespace.QName ITEM$14 = 
        new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd", "item");
    private static final javax.xml.namespace.QName ORIGINATIONSTATIONCODE$16 = 
        new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd", "originationStationCode");
    private static final javax.xml.namespace.QName PASSENGER$18 = 
        new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd", "passenger");
    private static final javax.xml.namespace.QName STATIONCODE$20 = 
        new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd", "stationCode");
    
    
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
     * Gets the "ahlId" element
     */
    public java.lang.String getAhlId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(AHLID$4, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "ahlId" element
     */
    public org.apache.xmlbeans.XmlString xgetAhlId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(AHLID$4, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "ahlId" element
     */
    public boolean isNilAhlId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(AHLID$4, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "ahlId" element
     */
    public boolean isSetAhlId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(AHLID$4) != 0;
        }
    }
    
    /**
     * Sets the "ahlId" element
     */
    public void setAhlId(java.lang.String ahlId)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(AHLID$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(AHLID$4);
            }
            target.setStringValue(ahlId);
        }
    }
    
    /**
     * Sets (as xml) the "ahlId" element
     */
    public void xsetAhlId(org.apache.xmlbeans.XmlString ahlId)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(AHLID$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(AHLID$4);
            }
            target.set(ahlId);
        }
    }
    
    /**
     * Nils the "ahlId" element
     */
    public void setNilAhlId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(AHLID$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(AHLID$4);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "ahlId" element
     */
    public void unsetAhlId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(AHLID$4, 0);
        }
    }
    
    /**
     * Gets the "airlineCode" element
     */
    public java.lang.String getAirlineCode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(AIRLINECODE$6, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "airlineCode" element
     */
    public org.apache.xmlbeans.XmlString xgetAirlineCode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(AIRLINECODE$6, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "airlineCode" element
     */
    public boolean isNilAirlineCode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(AIRLINECODE$6, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "airlineCode" element
     */
    public boolean isSetAirlineCode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(AIRLINECODE$6) != 0;
        }
    }
    
    /**
     * Sets the "airlineCode" element
     */
    public void setAirlineCode(java.lang.String airlineCode)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(AIRLINECODE$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(AIRLINECODE$6);
            }
            target.setStringValue(airlineCode);
        }
    }
    
    /**
     * Sets (as xml) the "airlineCode" element
     */
    public void xsetAirlineCode(org.apache.xmlbeans.XmlString airlineCode)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(AIRLINECODE$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(AIRLINECODE$6);
            }
            target.set(airlineCode);
        }
    }
    
    /**
     * Nils the "airlineCode" element
     */
    public void setNilAirlineCode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(AIRLINECODE$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(AIRLINECODE$6);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "airlineCode" element
     */
    public void unsetAirlineCode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(AIRLINECODE$6, 0);
        }
    }
    
    /**
     * Gets the "deliveryCompany" element
     */
    public java.lang.String getDeliveryCompany()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DELIVERYCOMPANY$8, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "deliveryCompany" element
     */
    public org.apache.xmlbeans.XmlString xgetDeliveryCompany()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DELIVERYCOMPANY$8, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "deliveryCompany" element
     */
    public boolean isNilDeliveryCompany()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DELIVERYCOMPANY$8, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "deliveryCompany" element
     */
    public boolean isSetDeliveryCompany()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DELIVERYCOMPANY$8) != 0;
        }
    }
    
    /**
     * Sets the "deliveryCompany" element
     */
    public void setDeliveryCompany(java.lang.String deliveryCompany)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DELIVERYCOMPANY$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DELIVERYCOMPANY$8);
            }
            target.setStringValue(deliveryCompany);
        }
    }
    
    /**
     * Sets (as xml) the "deliveryCompany" element
     */
    public void xsetDeliveryCompany(org.apache.xmlbeans.XmlString deliveryCompany)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DELIVERYCOMPANY$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DELIVERYCOMPANY$8);
            }
            target.set(deliveryCompany);
        }
    }
    
    /**
     * Nils the "deliveryCompany" element
     */
    public void setNilDeliveryCompany()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DELIVERYCOMPANY$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DELIVERYCOMPANY$8);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "deliveryCompany" element
     */
    public void unsetDeliveryCompany()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DELIVERYCOMPANY$8, 0);
        }
    }
    
    /**
     * Gets the "deliveryDate" element
     */
    public java.util.Calendar getDeliveryDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DELIVERYDATE$10, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "deliveryDate" element
     */
    public org.apache.xmlbeans.XmlDateTime xgetDeliveryDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DELIVERYDATE$10, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "deliveryDate" element
     */
    public boolean isNilDeliveryDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DELIVERYDATE$10, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "deliveryDate" element
     */
    public boolean isSetDeliveryDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DELIVERYDATE$10) != 0;
        }
    }
    
    /**
     * Sets the "deliveryDate" element
     */
    public void setDeliveryDate(java.util.Calendar deliveryDate)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DELIVERYDATE$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DELIVERYDATE$10);
            }
            target.setCalendarValue(deliveryDate);
        }
    }
    
    /**
     * Sets (as xml) the "deliveryDate" element
     */
    public void xsetDeliveryDate(org.apache.xmlbeans.XmlDateTime deliveryDate)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DELIVERYDATE$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(DELIVERYDATE$10);
            }
            target.set(deliveryDate);
        }
    }
    
    /**
     * Nils the "deliveryDate" element
     */
    public void setNilDeliveryDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DELIVERYDATE$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(DELIVERYDATE$10);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "deliveryDate" element
     */
    public void unsetDeliveryDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DELIVERYDATE$10, 0);
        }
    }
    
    /**
     * Gets the "deliveryStationCode" element
     */
    public java.lang.String getDeliveryStationCode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DELIVERYSTATIONCODE$12, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "deliveryStationCode" element
     */
    public org.apache.xmlbeans.XmlString xgetDeliveryStationCode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DELIVERYSTATIONCODE$12, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "deliveryStationCode" element
     */
    public boolean isNilDeliveryStationCode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DELIVERYSTATIONCODE$12, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "deliveryStationCode" element
     */
    public boolean isSetDeliveryStationCode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DELIVERYSTATIONCODE$12) != 0;
        }
    }
    
    /**
     * Sets the "deliveryStationCode" element
     */
    public void setDeliveryStationCode(java.lang.String deliveryStationCode)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DELIVERYSTATIONCODE$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DELIVERYSTATIONCODE$12);
            }
            target.setStringValue(deliveryStationCode);
        }
    }
    
    /**
     * Sets (as xml) the "deliveryStationCode" element
     */
    public void xsetDeliveryStationCode(org.apache.xmlbeans.XmlString deliveryStationCode)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DELIVERYSTATIONCODE$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DELIVERYSTATIONCODE$12);
            }
            target.set(deliveryStationCode);
        }
    }
    
    /**
     * Nils the "deliveryStationCode" element
     */
    public void setNilDeliveryStationCode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DELIVERYSTATIONCODE$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DELIVERYSTATIONCODE$12);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "deliveryStationCode" element
     */
    public void unsetDeliveryStationCode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DELIVERYSTATIONCODE$12, 0);
        }
    }
    
    /**
     * Gets array of all "item" elements
     */
    public aero.nettracer.serviceprovider.wt_1_0.common.xsd.Item[] getItemArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(ITEM$14, targetList);
            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Item[] result = new aero.nettracer.serviceprovider.wt_1_0.common.xsd.Item[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "item" element
     */
    public aero.nettracer.serviceprovider.wt_1_0.common.xsd.Item getItemArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Item target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Item)get_store().find_element_user(ITEM$14, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Tests for nil ith "item" element
     */
    public boolean isNilItemArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Item target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Item)get_store().find_element_user(ITEM$14, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target.isNil();
        }
    }
    
    /**
     * Returns number of "item" element
     */
    public int sizeOfItemArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(ITEM$14);
        }
    }
    
    /**
     * Sets array of all "item" element
     */
    public void setItemArray(aero.nettracer.serviceprovider.wt_1_0.common.xsd.Item[] itemArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(itemArray, ITEM$14);
        }
    }
    
    /**
     * Sets ith "item" element
     */
    public void setItemArray(int i, aero.nettracer.serviceprovider.wt_1_0.common.xsd.Item item)
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Item target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Item)get_store().find_element_user(ITEM$14, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(item);
        }
    }
    
    /**
     * Nils the ith "item" element
     */
    public void setNilItemArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Item target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Item)get_store().find_element_user(ITEM$14, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.setNil();
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "item" element
     */
    public aero.nettracer.serviceprovider.wt_1_0.common.xsd.Item insertNewItem(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Item target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Item)get_store().insert_element_user(ITEM$14, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "item" element
     */
    public aero.nettracer.serviceprovider.wt_1_0.common.xsd.Item addNewItem()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Item target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Item)get_store().add_element_user(ITEM$14);
            return target;
        }
    }
    
    /**
     * Removes the ith "item" element
     */
    public void removeItem(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(ITEM$14, i);
        }
    }
    
    /**
     * Gets the "originationStationCode" element
     */
    public java.lang.String getOriginationStationCode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ORIGINATIONSTATIONCODE$16, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "originationStationCode" element
     */
    public org.apache.xmlbeans.XmlString xgetOriginationStationCode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ORIGINATIONSTATIONCODE$16, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "originationStationCode" element
     */
    public boolean isNilOriginationStationCode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ORIGINATIONSTATIONCODE$16, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "originationStationCode" element
     */
    public boolean isSetOriginationStationCode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(ORIGINATIONSTATIONCODE$16) != 0;
        }
    }
    
    /**
     * Sets the "originationStationCode" element
     */
    public void setOriginationStationCode(java.lang.String originationStationCode)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ORIGINATIONSTATIONCODE$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ORIGINATIONSTATIONCODE$16);
            }
            target.setStringValue(originationStationCode);
        }
    }
    
    /**
     * Sets (as xml) the "originationStationCode" element
     */
    public void xsetOriginationStationCode(org.apache.xmlbeans.XmlString originationStationCode)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ORIGINATIONSTATIONCODE$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(ORIGINATIONSTATIONCODE$16);
            }
            target.set(originationStationCode);
        }
    }
    
    /**
     * Nils the "originationStationCode" element
     */
    public void setNilOriginationStationCode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ORIGINATIONSTATIONCODE$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(ORIGINATIONSTATIONCODE$16);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "originationStationCode" element
     */
    public void unsetOriginationStationCode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(ORIGINATIONSTATIONCODE$16, 0);
        }
    }
    
    /**
     * Gets array of all "passenger" elements
     */
    public aero.nettracer.serviceprovider.wt_1_0.common.xsd.Passenger[] getPassengerArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(PASSENGER$18, targetList);
            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Passenger[] result = new aero.nettracer.serviceprovider.wt_1_0.common.xsd.Passenger[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "passenger" element
     */
    public aero.nettracer.serviceprovider.wt_1_0.common.xsd.Passenger getPassengerArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Passenger target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Passenger)get_store().find_element_user(PASSENGER$18, i);
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
            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Passenger target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Passenger)get_store().find_element_user(PASSENGER$18, i);
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
            return get_store().count_elements(PASSENGER$18);
        }
    }
    
    /**
     * Sets array of all "passenger" element
     */
    public void setPassengerArray(aero.nettracer.serviceprovider.wt_1_0.common.xsd.Passenger[] passengerArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(passengerArray, PASSENGER$18);
        }
    }
    
    /**
     * Sets ith "passenger" element
     */
    public void setPassengerArray(int i, aero.nettracer.serviceprovider.wt_1_0.common.xsd.Passenger passenger)
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Passenger target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Passenger)get_store().find_element_user(PASSENGER$18, i);
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
            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Passenger target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Passenger)get_store().find_element_user(PASSENGER$18, i);
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
    public aero.nettracer.serviceprovider.wt_1_0.common.xsd.Passenger insertNewPassenger(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Passenger target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Passenger)get_store().insert_element_user(PASSENGER$18, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "passenger" element
     */
    public aero.nettracer.serviceprovider.wt_1_0.common.xsd.Passenger addNewPassenger()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Passenger target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Passenger)get_store().add_element_user(PASSENGER$18);
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
            get_store().remove_element(PASSENGER$18, i);
        }
    }
    
    /**
     * Gets the "stationCode" element
     */
    public java.lang.String getStationCode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STATIONCODE$20, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "stationCode" element
     */
    public org.apache.xmlbeans.XmlString xgetStationCode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STATIONCODE$20, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "stationCode" element
     */
    public boolean isNilStationCode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STATIONCODE$20, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "stationCode" element
     */
    public boolean isSetStationCode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(STATIONCODE$20) != 0;
        }
    }
    
    /**
     * Sets the "stationCode" element
     */
    public void setStationCode(java.lang.String stationCode)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STATIONCODE$20, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(STATIONCODE$20);
            }
            target.setStringValue(stationCode);
        }
    }
    
    /**
     * Sets (as xml) the "stationCode" element
     */
    public void xsetStationCode(org.apache.xmlbeans.XmlString stationCode)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STATIONCODE$20, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(STATIONCODE$20);
            }
            target.set(stationCode);
        }
    }
    
    /**
     * Nils the "stationCode" element
     */
    public void setNilStationCode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STATIONCODE$20, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(STATIONCODE$20);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "stationCode" element
     */
    public void unsetStationCode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(STATIONCODE$20, 0);
        }
    }
}
