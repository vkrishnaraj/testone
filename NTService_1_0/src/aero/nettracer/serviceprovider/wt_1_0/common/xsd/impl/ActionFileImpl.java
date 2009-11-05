/*
 * XML Type:  ActionFile
 * Namespace: http://common.wt_1_0.serviceprovider.nettracer.aero/xsd
 * Java type: aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFile
 *
 * Automatically generated - do not modify.
 */
package aero.nettracer.serviceprovider.wt_1_0.common.xsd.impl;
/**
 * An XML ActionFile(@http://common.wt_1_0.serviceprovider.nettracer.aero/xsd).
 *
 * This is a complex type.
 */
public class ActionFileImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFile
{
    
    public ActionFileImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName AHLID$0 = 
        new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd", "ahlId");
    private static final javax.xml.namespace.QName DETAILS$2 = 
        new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd", "details");
    private static final javax.xml.namespace.QName ITEMNUMBER$4 = 
        new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd", "itemNumber");
    private static final javax.xml.namespace.QName OHDID$6 = 
        new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd", "ohdId");
    private static final javax.xml.namespace.QName PERCENTMATCH$8 = 
        new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd", "percentMatch");
    private static final javax.xml.namespace.QName SUMMARY$10 = 
        new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd", "summary");
    
    
    /**
     * Gets the "ahlId" element
     */
    public java.lang.String getAhlId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(AHLID$0, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(AHLID$0, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(AHLID$0, 0);
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
            return get_store().count_elements(AHLID$0) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(AHLID$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(AHLID$0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(AHLID$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(AHLID$0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(AHLID$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(AHLID$0);
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
            get_store().remove_element(AHLID$0, 0);
        }
    }
    
    /**
     * Gets the "details" element
     */
    public java.lang.String getDetails()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DETAILS$2, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "details" element
     */
    public org.apache.xmlbeans.XmlString xgetDetails()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DETAILS$2, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "details" element
     */
    public boolean isNilDetails()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DETAILS$2, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "details" element
     */
    public boolean isSetDetails()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DETAILS$2) != 0;
        }
    }
    
    /**
     * Sets the "details" element
     */
    public void setDetails(java.lang.String details)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DETAILS$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DETAILS$2);
            }
            target.setStringValue(details);
        }
    }
    
    /**
     * Sets (as xml) the "details" element
     */
    public void xsetDetails(org.apache.xmlbeans.XmlString details)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DETAILS$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DETAILS$2);
            }
            target.set(details);
        }
    }
    
    /**
     * Nils the "details" element
     */
    public void setNilDetails()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DETAILS$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DETAILS$2);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "details" element
     */
    public void unsetDetails()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DETAILS$2, 0);
        }
    }
    
    /**
     * Gets the "itemNumber" element
     */
    public int getItemNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ITEMNUMBER$4, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "itemNumber" element
     */
    public org.apache.xmlbeans.XmlInt xgetItemNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(ITEMNUMBER$4, 0);
            return target;
        }
    }
    
    /**
     * True if has "itemNumber" element
     */
    public boolean isSetItemNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(ITEMNUMBER$4) != 0;
        }
    }
    
    /**
     * Sets the "itemNumber" element
     */
    public void setItemNumber(int itemNumber)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ITEMNUMBER$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ITEMNUMBER$4);
            }
            target.setIntValue(itemNumber);
        }
    }
    
    /**
     * Sets (as xml) the "itemNumber" element
     */
    public void xsetItemNumber(org.apache.xmlbeans.XmlInt itemNumber)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(ITEMNUMBER$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(ITEMNUMBER$4);
            }
            target.set(itemNumber);
        }
    }
    
    /**
     * Unsets the "itemNumber" element
     */
    public void unsetItemNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(ITEMNUMBER$4, 0);
        }
    }
    
    /**
     * Gets the "ohdId" element
     */
    public java.lang.String getOhdId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(OHDID$6, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "ohdId" element
     */
    public org.apache.xmlbeans.XmlString xgetOhdId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(OHDID$6, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "ohdId" element
     */
    public boolean isNilOhdId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(OHDID$6, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "ohdId" element
     */
    public boolean isSetOhdId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(OHDID$6) != 0;
        }
    }
    
    /**
     * Sets the "ohdId" element
     */
    public void setOhdId(java.lang.String ohdId)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(OHDID$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(OHDID$6);
            }
            target.setStringValue(ohdId);
        }
    }
    
    /**
     * Sets (as xml) the "ohdId" element
     */
    public void xsetOhdId(org.apache.xmlbeans.XmlString ohdId)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(OHDID$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(OHDID$6);
            }
            target.set(ohdId);
        }
    }
    
    /**
     * Nils the "ohdId" element
     */
    public void setNilOhdId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(OHDID$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(OHDID$6);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "ohdId" element
     */
    public void unsetOhdId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(OHDID$6, 0);
        }
    }
    
    /**
     * Gets the "percentMatch" element
     */
    public double getPercentMatch()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PERCENTMATCH$8, 0);
            if (target == null)
            {
                return 0.0;
            }
            return target.getDoubleValue();
        }
    }
    
    /**
     * Gets (as xml) the "percentMatch" element
     */
    public org.apache.xmlbeans.XmlDouble xgetPercentMatch()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(PERCENTMATCH$8, 0);
            return target;
        }
    }
    
    /**
     * True if has "percentMatch" element
     */
    public boolean isSetPercentMatch()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(PERCENTMATCH$8) != 0;
        }
    }
    
    /**
     * Sets the "percentMatch" element
     */
    public void setPercentMatch(double percentMatch)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PERCENTMATCH$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PERCENTMATCH$8);
            }
            target.setDoubleValue(percentMatch);
        }
    }
    
    /**
     * Sets (as xml) the "percentMatch" element
     */
    public void xsetPercentMatch(org.apache.xmlbeans.XmlDouble percentMatch)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(PERCENTMATCH$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(PERCENTMATCH$8);
            }
            target.set(percentMatch);
        }
    }
    
    /**
     * Unsets the "percentMatch" element
     */
    public void unsetPercentMatch()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(PERCENTMATCH$8, 0);
        }
    }
    
    /**
     * Gets the "summary" element
     */
    public java.lang.String getSummary()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SUMMARY$10, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "summary" element
     */
    public org.apache.xmlbeans.XmlString xgetSummary()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SUMMARY$10, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "summary" element
     */
    public boolean isNilSummary()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SUMMARY$10, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "summary" element
     */
    public boolean isSetSummary()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(SUMMARY$10) != 0;
        }
    }
    
    /**
     * Sets the "summary" element
     */
    public void setSummary(java.lang.String summary)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SUMMARY$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(SUMMARY$10);
            }
            target.setStringValue(summary);
        }
    }
    
    /**
     * Sets (as xml) the "summary" element
     */
    public void xsetSummary(org.apache.xmlbeans.XmlString summary)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SUMMARY$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(SUMMARY$10);
            }
            target.set(summary);
        }
    }
    
    /**
     * Nils the "summary" element
     */
    public void setNilSummary()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SUMMARY$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(SUMMARY$10);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "summary" element
     */
    public void unsetSummary()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(SUMMARY$10, 0);
        }
    }
}
