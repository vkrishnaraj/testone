/*
 * XML Type:  WS_OHD
 * Namespace: http://pojo.core.ws.nettracer.bagnet.com/xsd
 * Java type: com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.core.pojo.xsd.impl;
/**
 * An XML WS_OHD(@http://pojo.core.ws.nettracer.bagnet.com/xsd).
 *
 * This is a complex type.
 */
public class WSOHDImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD
{
    
    public WSOHDImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName OHDID$0 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "OHD_ID");
    private static final javax.xml.namespace.QName AGENT$2 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "agent");
    private static final javax.xml.namespace.QName BAGARRIVEDATE$4 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "bagarrivedate");
    private static final javax.xml.namespace.QName BAGTAGNUM$6 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "bagtagnum");
    private static final javax.xml.namespace.QName CLOSEDATE$8 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "close_date");
    private static final javax.xml.namespace.QName COLOR$10 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "color");
    private static final javax.xml.namespace.QName COMPANYCODEID$12 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "companycode_id");
    private static final javax.xml.namespace.QName DISPOSALSTATUS$14 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "disposal_status");
    private static final javax.xml.namespace.QName ERRORCODE$16 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "errorcode");
    private static final javax.xml.namespace.QName FAULTSTATION$18 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "faultStation");
    private static final javax.xml.namespace.QName FIRSTNAME$20 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "firstname");
    private static final javax.xml.namespace.QName FOUNDATSTATION$22 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "foundAtStation");
    private static final javax.xml.namespace.QName FOUNDDATETIME$24 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "founddatetime");
    private static final javax.xml.namespace.QName HOLDINGSTATION$26 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "holdingStation");
    private static final javax.xml.namespace.QName INVENTORIES$28 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "inventories");
    private static final javax.xml.namespace.QName ITINERARIES$30 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "itineraries");
    private static final javax.xml.namespace.QName LASTNAME$32 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "lastname");
    private static final javax.xml.namespace.QName LOSSCODE$34 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "lossCode");
    private static final javax.xml.namespace.QName MANUFACTURER$36 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "manufacturer");
    private static final javax.xml.namespace.QName MEMBERSHIP$38 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "membership");
    private static final javax.xml.namespace.QName MIDDLENAME$40 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "middlename");
    private static final javax.xml.namespace.QName PASSENGERS$42 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "passengers");
    private static final javax.xml.namespace.QName RECORDLOCATOR$44 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "record_locator");
    private static final javax.xml.namespace.QName STATUS$46 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "status");
    private static final javax.xml.namespace.QName STORAGELOCATION$48 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "storage_location");
    private static final javax.xml.namespace.QName TYPE$50 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "type");
    private static final javax.xml.namespace.QName XDESCELEMENT1$52 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "xdescelement1");
    private static final javax.xml.namespace.QName XDESCELEMENT2$54 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "xdescelement2");
    private static final javax.xml.namespace.QName XDESCELEMENT3$56 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "xdescelement3");
    
    
    /**
     * Gets the "OHD_ID" element
     */
    public java.lang.String getOHDID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(OHDID$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "OHD_ID" element
     */
    public org.apache.xmlbeans.XmlString xgetOHDID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(OHDID$0, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "OHD_ID" element
     */
    public boolean isNilOHDID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(OHDID$0, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "OHD_ID" element
     */
    public boolean isSetOHDID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(OHDID$0) != 0;
        }
    }
    
    /**
     * Sets the "OHD_ID" element
     */
    public void setOHDID(java.lang.String ohdid)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(OHDID$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(OHDID$0);
            }
            target.setStringValue(ohdid);
        }
    }
    
    /**
     * Sets (as xml) the "OHD_ID" element
     */
    public void xsetOHDID(org.apache.xmlbeans.XmlString ohdid)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(OHDID$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(OHDID$0);
            }
            target.set(ohdid);
        }
    }
    
    /**
     * Nils the "OHD_ID" element
     */
    public void setNilOHDID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(OHDID$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(OHDID$0);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "OHD_ID" element
     */
    public void unsetOHDID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(OHDID$0, 0);
        }
    }
    
    /**
     * Gets the "agent" element
     */
    public java.lang.String getAgent()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(AGENT$2, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "agent" element
     */
    public org.apache.xmlbeans.XmlString xgetAgent()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(AGENT$2, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "agent" element
     */
    public boolean isNilAgent()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(AGENT$2, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "agent" element
     */
    public boolean isSetAgent()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(AGENT$2) != 0;
        }
    }
    
    /**
     * Sets the "agent" element
     */
    public void setAgent(java.lang.String agent)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(AGENT$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(AGENT$2);
            }
            target.setStringValue(agent);
        }
    }
    
    /**
     * Sets (as xml) the "agent" element
     */
    public void xsetAgent(org.apache.xmlbeans.XmlString agent)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(AGENT$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(AGENT$2);
            }
            target.set(agent);
        }
    }
    
    /**
     * Nils the "agent" element
     */
    public void setNilAgent()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(AGENT$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(AGENT$2);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "agent" element
     */
    public void unsetAgent()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(AGENT$2, 0);
        }
    }
    
    /**
     * Gets the "bagarrivedate" element
     */
    public java.lang.String getBagarrivedate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(BAGARRIVEDATE$4, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "bagarrivedate" element
     */
    public org.apache.xmlbeans.XmlString xgetBagarrivedate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(BAGARRIVEDATE$4, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "bagarrivedate" element
     */
    public boolean isNilBagarrivedate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(BAGARRIVEDATE$4, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "bagarrivedate" element
     */
    public boolean isSetBagarrivedate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(BAGARRIVEDATE$4) != 0;
        }
    }
    
    /**
     * Sets the "bagarrivedate" element
     */
    public void setBagarrivedate(java.lang.String bagarrivedate)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(BAGARRIVEDATE$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(BAGARRIVEDATE$4);
            }
            target.setStringValue(bagarrivedate);
        }
    }
    
    /**
     * Sets (as xml) the "bagarrivedate" element
     */
    public void xsetBagarrivedate(org.apache.xmlbeans.XmlString bagarrivedate)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(BAGARRIVEDATE$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(BAGARRIVEDATE$4);
            }
            target.set(bagarrivedate);
        }
    }
    
    /**
     * Nils the "bagarrivedate" element
     */
    public void setNilBagarrivedate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(BAGARRIVEDATE$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(BAGARRIVEDATE$4);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "bagarrivedate" element
     */
    public void unsetBagarrivedate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(BAGARRIVEDATE$4, 0);
        }
    }
    
    /**
     * Gets the "bagtagnum" element
     */
    public java.lang.String getBagtagnum()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(BAGTAGNUM$6, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "bagtagnum" element
     */
    public org.apache.xmlbeans.XmlString xgetBagtagnum()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(BAGTAGNUM$6, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "bagtagnum" element
     */
    public boolean isNilBagtagnum()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(BAGTAGNUM$6, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "bagtagnum" element
     */
    public boolean isSetBagtagnum()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(BAGTAGNUM$6) != 0;
        }
    }
    
    /**
     * Sets the "bagtagnum" element
     */
    public void setBagtagnum(java.lang.String bagtagnum)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(BAGTAGNUM$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(BAGTAGNUM$6);
            }
            target.setStringValue(bagtagnum);
        }
    }
    
    /**
     * Sets (as xml) the "bagtagnum" element
     */
    public void xsetBagtagnum(org.apache.xmlbeans.XmlString bagtagnum)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(BAGTAGNUM$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(BAGTAGNUM$6);
            }
            target.set(bagtagnum);
        }
    }
    
    /**
     * Nils the "bagtagnum" element
     */
    public void setNilBagtagnum()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(BAGTAGNUM$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(BAGTAGNUM$6);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "bagtagnum" element
     */
    public void unsetBagtagnum()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(BAGTAGNUM$6, 0);
        }
    }
    
    /**
     * Gets the "close_date" element
     */
    public java.lang.String getCloseDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CLOSEDATE$8, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "close_date" element
     */
    public org.apache.xmlbeans.XmlString xgetCloseDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CLOSEDATE$8, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "close_date" element
     */
    public boolean isNilCloseDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CLOSEDATE$8, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "close_date" element
     */
    public boolean isSetCloseDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CLOSEDATE$8) != 0;
        }
    }
    
    /**
     * Sets the "close_date" element
     */
    public void setCloseDate(java.lang.String closeDate)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CLOSEDATE$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CLOSEDATE$8);
            }
            target.setStringValue(closeDate);
        }
    }
    
    /**
     * Sets (as xml) the "close_date" element
     */
    public void xsetCloseDate(org.apache.xmlbeans.XmlString closeDate)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CLOSEDATE$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CLOSEDATE$8);
            }
            target.set(closeDate);
        }
    }
    
    /**
     * Nils the "close_date" element
     */
    public void setNilCloseDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CLOSEDATE$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CLOSEDATE$8);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "close_date" element
     */
    public void unsetCloseDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CLOSEDATE$8, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COLOR$10, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COLOR$10, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COLOR$10, 0);
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
            return get_store().count_elements(COLOR$10) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COLOR$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(COLOR$10);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COLOR$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(COLOR$10);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COLOR$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(COLOR$10);
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
            get_store().remove_element(COLOR$10, 0);
        }
    }
    
    /**
     * Gets the "companycode_id" element
     */
    public java.lang.String getCompanycodeId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COMPANYCODEID$12, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "companycode_id" element
     */
    public org.apache.xmlbeans.XmlString xgetCompanycodeId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COMPANYCODEID$12, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "companycode_id" element
     */
    public boolean isNilCompanycodeId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COMPANYCODEID$12, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "companycode_id" element
     */
    public boolean isSetCompanycodeId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(COMPANYCODEID$12) != 0;
        }
    }
    
    /**
     * Sets the "companycode_id" element
     */
    public void setCompanycodeId(java.lang.String companycodeId)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COMPANYCODEID$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(COMPANYCODEID$12);
            }
            target.setStringValue(companycodeId);
        }
    }
    
    /**
     * Sets (as xml) the "companycode_id" element
     */
    public void xsetCompanycodeId(org.apache.xmlbeans.XmlString companycodeId)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COMPANYCODEID$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(COMPANYCODEID$12);
            }
            target.set(companycodeId);
        }
    }
    
    /**
     * Nils the "companycode_id" element
     */
    public void setNilCompanycodeId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COMPANYCODEID$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(COMPANYCODEID$12);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "companycode_id" element
     */
    public void unsetCompanycodeId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(COMPANYCODEID$12, 0);
        }
    }
    
    /**
     * Gets the "disposal_status" element
     */
    public java.lang.String getDisposalStatus()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DISPOSALSTATUS$14, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "disposal_status" element
     */
    public org.apache.xmlbeans.XmlString xgetDisposalStatus()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DISPOSALSTATUS$14, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "disposal_status" element
     */
    public boolean isNilDisposalStatus()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DISPOSALSTATUS$14, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "disposal_status" element
     */
    public boolean isSetDisposalStatus()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DISPOSALSTATUS$14) != 0;
        }
    }
    
    /**
     * Sets the "disposal_status" element
     */
    public void setDisposalStatus(java.lang.String disposalStatus)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DISPOSALSTATUS$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DISPOSALSTATUS$14);
            }
            target.setStringValue(disposalStatus);
        }
    }
    
    /**
     * Sets (as xml) the "disposal_status" element
     */
    public void xsetDisposalStatus(org.apache.xmlbeans.XmlString disposalStatus)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DISPOSALSTATUS$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DISPOSALSTATUS$14);
            }
            target.set(disposalStatus);
        }
    }
    
    /**
     * Nils the "disposal_status" element
     */
    public void setNilDisposalStatus()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DISPOSALSTATUS$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DISPOSALSTATUS$14);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "disposal_status" element
     */
    public void unsetDisposalStatus()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DISPOSALSTATUS$14, 0);
        }
    }
    
    /**
     * Gets the "errorcode" element
     */
    public java.lang.String getErrorcode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ERRORCODE$16, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "errorcode" element
     */
    public org.apache.xmlbeans.XmlString xgetErrorcode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ERRORCODE$16, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "errorcode" element
     */
    public boolean isNilErrorcode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ERRORCODE$16, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "errorcode" element
     */
    public boolean isSetErrorcode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(ERRORCODE$16) != 0;
        }
    }
    
    /**
     * Sets the "errorcode" element
     */
    public void setErrorcode(java.lang.String errorcode)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ERRORCODE$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ERRORCODE$16);
            }
            target.setStringValue(errorcode);
        }
    }
    
    /**
     * Sets (as xml) the "errorcode" element
     */
    public void xsetErrorcode(org.apache.xmlbeans.XmlString errorcode)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ERRORCODE$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(ERRORCODE$16);
            }
            target.set(errorcode);
        }
    }
    
    /**
     * Nils the "errorcode" element
     */
    public void setNilErrorcode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ERRORCODE$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(ERRORCODE$16);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "errorcode" element
     */
    public void unsetErrorcode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(ERRORCODE$16, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FAULTSTATION$18, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FAULTSTATION$18, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FAULTSTATION$18, 0);
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
            return get_store().count_elements(FAULTSTATION$18) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FAULTSTATION$18, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FAULTSTATION$18);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FAULTSTATION$18, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(FAULTSTATION$18);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FAULTSTATION$18, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(FAULTSTATION$18);
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
            get_store().remove_element(FAULTSTATION$18, 0);
        }
    }
    
    /**
     * Gets the "firstname" element
     */
    public java.lang.String getFirstname()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FIRSTNAME$20, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "firstname" element
     */
    public org.apache.xmlbeans.XmlString xgetFirstname()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FIRSTNAME$20, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "firstname" element
     */
    public boolean isNilFirstname()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FIRSTNAME$20, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "firstname" element
     */
    public boolean isSetFirstname()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(FIRSTNAME$20) != 0;
        }
    }
    
    /**
     * Sets the "firstname" element
     */
    public void setFirstname(java.lang.String firstname)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FIRSTNAME$20, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FIRSTNAME$20);
            }
            target.setStringValue(firstname);
        }
    }
    
    /**
     * Sets (as xml) the "firstname" element
     */
    public void xsetFirstname(org.apache.xmlbeans.XmlString firstname)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FIRSTNAME$20, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(FIRSTNAME$20);
            }
            target.set(firstname);
        }
    }
    
    /**
     * Nils the "firstname" element
     */
    public void setNilFirstname()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FIRSTNAME$20, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(FIRSTNAME$20);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "firstname" element
     */
    public void unsetFirstname()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(FIRSTNAME$20, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FOUNDATSTATION$22, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FOUNDATSTATION$22, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FOUNDATSTATION$22, 0);
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
            return get_store().count_elements(FOUNDATSTATION$22) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FOUNDATSTATION$22, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FOUNDATSTATION$22);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FOUNDATSTATION$22, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(FOUNDATSTATION$22);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FOUNDATSTATION$22, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(FOUNDATSTATION$22);
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
            get_store().remove_element(FOUNDATSTATION$22, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FOUNDDATETIME$24, 0);
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
            target = (org.apache.xmlbeans.XmlDate)get_store().find_element_user(FOUNDDATETIME$24, 0);
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
            target = (org.apache.xmlbeans.XmlDate)get_store().find_element_user(FOUNDDATETIME$24, 0);
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
            return get_store().count_elements(FOUNDDATETIME$24) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FOUNDDATETIME$24, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FOUNDDATETIME$24);
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
            target = (org.apache.xmlbeans.XmlDate)get_store().find_element_user(FOUNDDATETIME$24, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDate)get_store().add_element_user(FOUNDDATETIME$24);
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
            target = (org.apache.xmlbeans.XmlDate)get_store().find_element_user(FOUNDDATETIME$24, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDate)get_store().add_element_user(FOUNDDATETIME$24);
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
            get_store().remove_element(FOUNDDATETIME$24, 0);
        }
    }
    
    /**
     * Gets the "holdingStation" element
     */
    public java.lang.String getHoldingStation()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(HOLDINGSTATION$26, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "holdingStation" element
     */
    public org.apache.xmlbeans.XmlString xgetHoldingStation()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(HOLDINGSTATION$26, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "holdingStation" element
     */
    public boolean isNilHoldingStation()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(HOLDINGSTATION$26, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "holdingStation" element
     */
    public boolean isSetHoldingStation()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(HOLDINGSTATION$26) != 0;
        }
    }
    
    /**
     * Sets the "holdingStation" element
     */
    public void setHoldingStation(java.lang.String holdingStation)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(HOLDINGSTATION$26, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(HOLDINGSTATION$26);
            }
            target.setStringValue(holdingStation);
        }
    }
    
    /**
     * Sets (as xml) the "holdingStation" element
     */
    public void xsetHoldingStation(org.apache.xmlbeans.XmlString holdingStation)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(HOLDINGSTATION$26, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(HOLDINGSTATION$26);
            }
            target.set(holdingStation);
        }
    }
    
    /**
     * Nils the "holdingStation" element
     */
    public void setNilHoldingStation()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(HOLDINGSTATION$26, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(HOLDINGSTATION$26);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "holdingStation" element
     */
    public void unsetHoldingStation()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(HOLDINGSTATION$26, 0);
        }
    }
    
    /**
     * Gets array of all "inventories" elements
     */
    public com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory[] getInventoriesArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(INVENTORIES$28, targetList);
            com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory[] result = new com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "inventories" element
     */
    public com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory getInventoriesArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory)get_store().find_element_user(INVENTORIES$28, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Tests for nil ith "inventories" element
     */
    public boolean isNilInventoriesArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory)get_store().find_element_user(INVENTORIES$28, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target.isNil();
        }
    }
    
    /**
     * Returns number of "inventories" element
     */
    public int sizeOfInventoriesArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(INVENTORIES$28);
        }
    }
    
    /**
     * Sets array of all "inventories" element
     */
    public void setInventoriesArray(com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory[] inventoriesArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(inventoriesArray, INVENTORIES$28);
        }
    }
    
    /**
     * Sets ith "inventories" element
     */
    public void setInventoriesArray(int i, com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory inventories)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory)get_store().find_element_user(INVENTORIES$28, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(inventories);
        }
    }
    
    /**
     * Nils the ith "inventories" element
     */
    public void setNilInventoriesArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory)get_store().find_element_user(INVENTORIES$28, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.setNil();
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "inventories" element
     */
    public com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory insertNewInventories(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory)get_store().insert_element_user(INVENTORIES$28, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "inventories" element
     */
    public com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory addNewInventories()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory)get_store().add_element_user(INVENTORIES$28);
            return target;
        }
    }
    
    /**
     * Removes the ith "inventories" element
     */
    public void removeInventories(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(INVENTORIES$28, i);
        }
    }
    
    /**
     * Gets array of all "itineraries" elements
     */
    public com.bagnet.nettracer.ws.core.pojo.xsd.WSItinerary[] getItinerariesArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(ITINERARIES$30, targetList);
            com.bagnet.nettracer.ws.core.pojo.xsd.WSItinerary[] result = new com.bagnet.nettracer.ws.core.pojo.xsd.WSItinerary[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "itineraries" element
     */
    public com.bagnet.nettracer.ws.core.pojo.xsd.WSItinerary getItinerariesArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.pojo.xsd.WSItinerary target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSItinerary)get_store().find_element_user(ITINERARIES$30, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Tests for nil ith "itineraries" element
     */
    public boolean isNilItinerariesArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.pojo.xsd.WSItinerary target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSItinerary)get_store().find_element_user(ITINERARIES$30, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target.isNil();
        }
    }
    
    /**
     * Returns number of "itineraries" element
     */
    public int sizeOfItinerariesArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(ITINERARIES$30);
        }
    }
    
    /**
     * Sets array of all "itineraries" element
     */
    public void setItinerariesArray(com.bagnet.nettracer.ws.core.pojo.xsd.WSItinerary[] itinerariesArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(itinerariesArray, ITINERARIES$30);
        }
    }
    
    /**
     * Sets ith "itineraries" element
     */
    public void setItinerariesArray(int i, com.bagnet.nettracer.ws.core.pojo.xsd.WSItinerary itineraries)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.pojo.xsd.WSItinerary target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSItinerary)get_store().find_element_user(ITINERARIES$30, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(itineraries);
        }
    }
    
    /**
     * Nils the ith "itineraries" element
     */
    public void setNilItinerariesArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.pojo.xsd.WSItinerary target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSItinerary)get_store().find_element_user(ITINERARIES$30, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.setNil();
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "itineraries" element
     */
    public com.bagnet.nettracer.ws.core.pojo.xsd.WSItinerary insertNewItineraries(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.pojo.xsd.WSItinerary target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSItinerary)get_store().insert_element_user(ITINERARIES$30, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "itineraries" element
     */
    public com.bagnet.nettracer.ws.core.pojo.xsd.WSItinerary addNewItineraries()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.pojo.xsd.WSItinerary target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSItinerary)get_store().add_element_user(ITINERARIES$30);
            return target;
        }
    }
    
    /**
     * Removes the ith "itineraries" element
     */
    public void removeItineraries(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(ITINERARIES$30, i);
        }
    }
    
    /**
     * Gets the "lastname" element
     */
    public java.lang.String getLastname()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LASTNAME$32, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "lastname" element
     */
    public org.apache.xmlbeans.XmlString xgetLastname()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(LASTNAME$32, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "lastname" element
     */
    public boolean isNilLastname()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(LASTNAME$32, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "lastname" element
     */
    public boolean isSetLastname()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(LASTNAME$32) != 0;
        }
    }
    
    /**
     * Sets the "lastname" element
     */
    public void setLastname(java.lang.String lastname)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LASTNAME$32, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(LASTNAME$32);
            }
            target.setStringValue(lastname);
        }
    }
    
    /**
     * Sets (as xml) the "lastname" element
     */
    public void xsetLastname(org.apache.xmlbeans.XmlString lastname)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(LASTNAME$32, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(LASTNAME$32);
            }
            target.set(lastname);
        }
    }
    
    /**
     * Nils the "lastname" element
     */
    public void setNilLastname()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(LASTNAME$32, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(LASTNAME$32);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "lastname" element
     */
    public void unsetLastname()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(LASTNAME$32, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LOSSCODE$34, 0);
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
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(LOSSCODE$34, 0);
            return target;
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
            return get_store().count_elements(LOSSCODE$34) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LOSSCODE$34, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(LOSSCODE$34);
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
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(LOSSCODE$34, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(LOSSCODE$34);
            }
            target.set(lossCode);
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
            get_store().remove_element(LOSSCODE$34, 0);
        }
    }
    
    /**
     * Gets the "manufacturer" element
     */
    public java.lang.String getManufacturer()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MANUFACTURER$36, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "manufacturer" element
     */
    public org.apache.xmlbeans.XmlString xgetManufacturer()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MANUFACTURER$36, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "manufacturer" element
     */
    public boolean isNilManufacturer()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MANUFACTURER$36, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "manufacturer" element
     */
    public boolean isSetManufacturer()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(MANUFACTURER$36) != 0;
        }
    }
    
    /**
     * Sets the "manufacturer" element
     */
    public void setManufacturer(java.lang.String manufacturer)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MANUFACTURER$36, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(MANUFACTURER$36);
            }
            target.setStringValue(manufacturer);
        }
    }
    
    /**
     * Sets (as xml) the "manufacturer" element
     */
    public void xsetManufacturer(org.apache.xmlbeans.XmlString manufacturer)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MANUFACTURER$36, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(MANUFACTURER$36);
            }
            target.set(manufacturer);
        }
    }
    
    /**
     * Nils the "manufacturer" element
     */
    public void setNilManufacturer()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MANUFACTURER$36, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(MANUFACTURER$36);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "manufacturer" element
     */
    public void unsetManufacturer()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(MANUFACTURER$36, 0);
        }
    }
    
    /**
     * Gets the "membership" element
     */
    public java.lang.String getMembership()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MEMBERSHIP$38, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "membership" element
     */
    public org.apache.xmlbeans.XmlString xgetMembership()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MEMBERSHIP$38, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "membership" element
     */
    public boolean isNilMembership()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MEMBERSHIP$38, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "membership" element
     */
    public boolean isSetMembership()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(MEMBERSHIP$38) != 0;
        }
    }
    
    /**
     * Sets the "membership" element
     */
    public void setMembership(java.lang.String membership)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MEMBERSHIP$38, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(MEMBERSHIP$38);
            }
            target.setStringValue(membership);
        }
    }
    
    /**
     * Sets (as xml) the "membership" element
     */
    public void xsetMembership(org.apache.xmlbeans.XmlString membership)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MEMBERSHIP$38, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(MEMBERSHIP$38);
            }
            target.set(membership);
        }
    }
    
    /**
     * Nils the "membership" element
     */
    public void setNilMembership()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MEMBERSHIP$38, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(MEMBERSHIP$38);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "membership" element
     */
    public void unsetMembership()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(MEMBERSHIP$38, 0);
        }
    }
    
    /**
     * Gets the "middlename" element
     */
    public java.lang.String getMiddlename()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MIDDLENAME$40, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "middlename" element
     */
    public org.apache.xmlbeans.XmlString xgetMiddlename()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MIDDLENAME$40, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "middlename" element
     */
    public boolean isNilMiddlename()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MIDDLENAME$40, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "middlename" element
     */
    public boolean isSetMiddlename()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(MIDDLENAME$40) != 0;
        }
    }
    
    /**
     * Sets the "middlename" element
     */
    public void setMiddlename(java.lang.String middlename)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MIDDLENAME$40, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(MIDDLENAME$40);
            }
            target.setStringValue(middlename);
        }
    }
    
    /**
     * Sets (as xml) the "middlename" element
     */
    public void xsetMiddlename(org.apache.xmlbeans.XmlString middlename)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MIDDLENAME$40, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(MIDDLENAME$40);
            }
            target.set(middlename);
        }
    }
    
    /**
     * Nils the "middlename" element
     */
    public void setNilMiddlename()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MIDDLENAME$40, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(MIDDLENAME$40);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "middlename" element
     */
    public void unsetMiddlename()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(MIDDLENAME$40, 0);
        }
    }
    
    /**
     * Gets array of all "passengers" elements
     */
    public com.bagnet.nettracer.ws.core.pojo.xsd.WSPassenger[] getPassengersArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(PASSENGERS$42, targetList);
            com.bagnet.nettracer.ws.core.pojo.xsd.WSPassenger[] result = new com.bagnet.nettracer.ws.core.pojo.xsd.WSPassenger[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "passengers" element
     */
    public com.bagnet.nettracer.ws.core.pojo.xsd.WSPassenger getPassengersArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.pojo.xsd.WSPassenger target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPassenger)get_store().find_element_user(PASSENGERS$42, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Tests for nil ith "passengers" element
     */
    public boolean isNilPassengersArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.pojo.xsd.WSPassenger target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPassenger)get_store().find_element_user(PASSENGERS$42, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target.isNil();
        }
    }
    
    /**
     * Returns number of "passengers" element
     */
    public int sizeOfPassengersArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(PASSENGERS$42);
        }
    }
    
    /**
     * Sets array of all "passengers" element
     */
    public void setPassengersArray(com.bagnet.nettracer.ws.core.pojo.xsd.WSPassenger[] passengersArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(passengersArray, PASSENGERS$42);
        }
    }
    
    /**
     * Sets ith "passengers" element
     */
    public void setPassengersArray(int i, com.bagnet.nettracer.ws.core.pojo.xsd.WSPassenger passengers)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.pojo.xsd.WSPassenger target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPassenger)get_store().find_element_user(PASSENGERS$42, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(passengers);
        }
    }
    
    /**
     * Nils the ith "passengers" element
     */
    public void setNilPassengersArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.pojo.xsd.WSPassenger target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPassenger)get_store().find_element_user(PASSENGERS$42, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.setNil();
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "passengers" element
     */
    public com.bagnet.nettracer.ws.core.pojo.xsd.WSPassenger insertNewPassengers(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.pojo.xsd.WSPassenger target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPassenger)get_store().insert_element_user(PASSENGERS$42, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "passengers" element
     */
    public com.bagnet.nettracer.ws.core.pojo.xsd.WSPassenger addNewPassengers()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.pojo.xsd.WSPassenger target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPassenger)get_store().add_element_user(PASSENGERS$42);
            return target;
        }
    }
    
    /**
     * Removes the ith "passengers" element
     */
    public void removePassengers(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(PASSENGERS$42, i);
        }
    }
    
    /**
     * Gets the "record_locator" element
     */
    public java.lang.String getRecordLocator()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(RECORDLOCATOR$44, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "record_locator" element
     */
    public org.apache.xmlbeans.XmlString xgetRecordLocator()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(RECORDLOCATOR$44, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "record_locator" element
     */
    public boolean isNilRecordLocator()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(RECORDLOCATOR$44, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "record_locator" element
     */
    public boolean isSetRecordLocator()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(RECORDLOCATOR$44) != 0;
        }
    }
    
    /**
     * Sets the "record_locator" element
     */
    public void setRecordLocator(java.lang.String recordLocator)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(RECORDLOCATOR$44, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(RECORDLOCATOR$44);
            }
            target.setStringValue(recordLocator);
        }
    }
    
    /**
     * Sets (as xml) the "record_locator" element
     */
    public void xsetRecordLocator(org.apache.xmlbeans.XmlString recordLocator)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(RECORDLOCATOR$44, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(RECORDLOCATOR$44);
            }
            target.set(recordLocator);
        }
    }
    
    /**
     * Nils the "record_locator" element
     */
    public void setNilRecordLocator()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(RECORDLOCATOR$44, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(RECORDLOCATOR$44);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "record_locator" element
     */
    public void unsetRecordLocator()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(RECORDLOCATOR$44, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STATUS$46, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STATUS$46, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STATUS$46, 0);
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
            return get_store().count_elements(STATUS$46) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STATUS$46, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(STATUS$46);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STATUS$46, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(STATUS$46);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STATUS$46, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(STATUS$46);
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
            get_store().remove_element(STATUS$46, 0);
        }
    }
    
    /**
     * Gets the "storage_location" element
     */
    public java.lang.String getStorageLocation()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STORAGELOCATION$48, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "storage_location" element
     */
    public org.apache.xmlbeans.XmlString xgetStorageLocation()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STORAGELOCATION$48, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "storage_location" element
     */
    public boolean isNilStorageLocation()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STORAGELOCATION$48, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "storage_location" element
     */
    public boolean isSetStorageLocation()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(STORAGELOCATION$48) != 0;
        }
    }
    
    /**
     * Sets the "storage_location" element
     */
    public void setStorageLocation(java.lang.String storageLocation)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STORAGELOCATION$48, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(STORAGELOCATION$48);
            }
            target.setStringValue(storageLocation);
        }
    }
    
    /**
     * Sets (as xml) the "storage_location" element
     */
    public void xsetStorageLocation(org.apache.xmlbeans.XmlString storageLocation)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STORAGELOCATION$48, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(STORAGELOCATION$48);
            }
            target.set(storageLocation);
        }
    }
    
    /**
     * Nils the "storage_location" element
     */
    public void setNilStorageLocation()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STORAGELOCATION$48, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(STORAGELOCATION$48);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "storage_location" element
     */
    public void unsetStorageLocation()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(STORAGELOCATION$48, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TYPE$50, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TYPE$50, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TYPE$50, 0);
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
            return get_store().count_elements(TYPE$50) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TYPE$50, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(TYPE$50);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TYPE$50, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(TYPE$50);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TYPE$50, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(TYPE$50);
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
            get_store().remove_element(TYPE$50, 0);
        }
    }
    
    /**
     * Gets the "xdescelement1" element
     */
    public java.lang.String getXdescelement1()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(XDESCELEMENT1$52, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "xdescelement1" element
     */
    public org.apache.xmlbeans.XmlString xgetXdescelement1()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(XDESCELEMENT1$52, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "xdescelement1" element
     */
    public boolean isNilXdescelement1()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(XDESCELEMENT1$52, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "xdescelement1" element
     */
    public boolean isSetXdescelement1()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(XDESCELEMENT1$52) != 0;
        }
    }
    
    /**
     * Sets the "xdescelement1" element
     */
    public void setXdescelement1(java.lang.String xdescelement1)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(XDESCELEMENT1$52, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(XDESCELEMENT1$52);
            }
            target.setStringValue(xdescelement1);
        }
    }
    
    /**
     * Sets (as xml) the "xdescelement1" element
     */
    public void xsetXdescelement1(org.apache.xmlbeans.XmlString xdescelement1)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(XDESCELEMENT1$52, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(XDESCELEMENT1$52);
            }
            target.set(xdescelement1);
        }
    }
    
    /**
     * Nils the "xdescelement1" element
     */
    public void setNilXdescelement1()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(XDESCELEMENT1$52, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(XDESCELEMENT1$52);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "xdescelement1" element
     */
    public void unsetXdescelement1()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(XDESCELEMENT1$52, 0);
        }
    }
    
    /**
     * Gets the "xdescelement2" element
     */
    public java.lang.String getXdescelement2()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(XDESCELEMENT2$54, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "xdescelement2" element
     */
    public org.apache.xmlbeans.XmlString xgetXdescelement2()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(XDESCELEMENT2$54, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "xdescelement2" element
     */
    public boolean isNilXdescelement2()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(XDESCELEMENT2$54, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "xdescelement2" element
     */
    public boolean isSetXdescelement2()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(XDESCELEMENT2$54) != 0;
        }
    }
    
    /**
     * Sets the "xdescelement2" element
     */
    public void setXdescelement2(java.lang.String xdescelement2)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(XDESCELEMENT2$54, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(XDESCELEMENT2$54);
            }
            target.setStringValue(xdescelement2);
        }
    }
    
    /**
     * Sets (as xml) the "xdescelement2" element
     */
    public void xsetXdescelement2(org.apache.xmlbeans.XmlString xdescelement2)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(XDESCELEMENT2$54, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(XDESCELEMENT2$54);
            }
            target.set(xdescelement2);
        }
    }
    
    /**
     * Nils the "xdescelement2" element
     */
    public void setNilXdescelement2()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(XDESCELEMENT2$54, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(XDESCELEMENT2$54);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "xdescelement2" element
     */
    public void unsetXdescelement2()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(XDESCELEMENT2$54, 0);
        }
    }
    
    /**
     * Gets the "xdescelement3" element
     */
    public java.lang.String getXdescelement3()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(XDESCELEMENT3$56, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "xdescelement3" element
     */
    public org.apache.xmlbeans.XmlString xgetXdescelement3()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(XDESCELEMENT3$56, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "xdescelement3" element
     */
    public boolean isNilXdescelement3()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(XDESCELEMENT3$56, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "xdescelement3" element
     */
    public boolean isSetXdescelement3()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(XDESCELEMENT3$56) != 0;
        }
    }
    
    /**
     * Sets the "xdescelement3" element
     */
    public void setXdescelement3(java.lang.String xdescelement3)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(XDESCELEMENT3$56, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(XDESCELEMENT3$56);
            }
            target.setStringValue(xdescelement3);
        }
    }
    
    /**
     * Sets (as xml) the "xdescelement3" element
     */
    public void xsetXdescelement3(org.apache.xmlbeans.XmlString xdescelement3)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(XDESCELEMENT3$56, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(XDESCELEMENT3$56);
            }
            target.set(xdescelement3);
        }
    }
    
    /**
     * Nils the "xdescelement3" element
     */
    public void setNilXdescelement3()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(XDESCELEMENT3$56, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(XDESCELEMENT3$56);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "xdescelement3" element
     */
    public void unsetXdescelement3()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(XDESCELEMENT3$56, 0);
        }
    }
}
