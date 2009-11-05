/*
 * XML Type:  ClaimCheck
 * Namespace: http://common.wt_1_0.serviceprovider.nettracer.aero/xsd
 * Java type: aero.nettracer.serviceprovider.wt_1_0.common.xsd.ClaimCheck
 *
 * Automatically generated - do not modify.
 */
package aero.nettracer.serviceprovider.wt_1_0.common.xsd.impl;
/**
 * An XML ClaimCheck(@http://common.wt_1_0.serviceprovider.nettracer.aero/xsd).
 *
 * This is a complex type.
 */
public class ClaimCheckImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aero.nettracer.serviceprovider.wt_1_0.common.xsd.ClaimCheck
{
    
    public ClaimCheckImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName AIRLINECODE$0 = 
        new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd", "airlineCode");
    private static final javax.xml.namespace.QName TAGNUMBER$2 = 
        new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd", "tagNumber");
    
    
    /**
     * Gets the "airlineCode" element
     */
    public java.lang.String getAirlineCode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(AIRLINECODE$0, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(AIRLINECODE$0, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(AIRLINECODE$0, 0);
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
            return get_store().count_elements(AIRLINECODE$0) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(AIRLINECODE$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(AIRLINECODE$0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(AIRLINECODE$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(AIRLINECODE$0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(AIRLINECODE$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(AIRLINECODE$0);
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
            get_store().remove_element(AIRLINECODE$0, 0);
        }
    }
    
    /**
     * Gets the "tagNumber" element
     */
    public java.lang.String getTagNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TAGNUMBER$2, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "tagNumber" element
     */
    public org.apache.xmlbeans.XmlString xgetTagNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TAGNUMBER$2, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "tagNumber" element
     */
    public boolean isNilTagNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TAGNUMBER$2, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "tagNumber" element
     */
    public boolean isSetTagNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(TAGNUMBER$2) != 0;
        }
    }
    
    /**
     * Sets the "tagNumber" element
     */
    public void setTagNumber(java.lang.String tagNumber)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TAGNUMBER$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(TAGNUMBER$2);
            }
            target.setStringValue(tagNumber);
        }
    }
    
    /**
     * Sets (as xml) the "tagNumber" element
     */
    public void xsetTagNumber(org.apache.xmlbeans.XmlString tagNumber)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TAGNUMBER$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(TAGNUMBER$2);
            }
            target.set(tagNumber);
        }
    }
    
    /**
     * Nils the "tagNumber" element
     */
    public void setNilTagNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TAGNUMBER$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(TAGNUMBER$2);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "tagNumber" element
     */
    public void unsetTagNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(TAGNUMBER$2, 0);
        }
    }
}
