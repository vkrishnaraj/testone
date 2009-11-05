/*
 * XML Type:  PxfAas
 * Namespace: http://common.wt_1_0.serviceprovider.nettracer.aero/xsd
 * Java type: aero.nettracer.serviceprovider.wt_1_0.common.xsd.PxfAas
 *
 * Automatically generated - do not modify.
 */
package aero.nettracer.serviceprovider.wt_1_0.common.xsd.impl;
/**
 * An XML PxfAas(@http://common.wt_1_0.serviceprovider.nettracer.aero/xsd).
 *
 * This is a complex type.
 */
public class PxfAasImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aero.nettracer.serviceprovider.wt_1_0.common.xsd.PxfAas
{
    
    public PxfAasImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName AIRLINE$0 = 
        new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd", "airline");
    private static final javax.xml.namespace.QName AREA$2 = 
        new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd", "area");
    private static final javax.xml.namespace.QName STATION$4 = 
        new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd", "station");
    
    
    /**
     * Gets the "airline" element
     */
    public java.lang.String getAirline()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(AIRLINE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "airline" element
     */
    public org.apache.xmlbeans.XmlString xgetAirline()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(AIRLINE$0, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "airline" element
     */
    public boolean isNilAirline()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(AIRLINE$0, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "airline" element
     */
    public boolean isSetAirline()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(AIRLINE$0) != 0;
        }
    }
    
    /**
     * Sets the "airline" element
     */
    public void setAirline(java.lang.String airline)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(AIRLINE$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(AIRLINE$0);
            }
            target.setStringValue(airline);
        }
    }
    
    /**
     * Sets (as xml) the "airline" element
     */
    public void xsetAirline(org.apache.xmlbeans.XmlString airline)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(AIRLINE$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(AIRLINE$0);
            }
            target.set(airline);
        }
    }
    
    /**
     * Nils the "airline" element
     */
    public void setNilAirline()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(AIRLINE$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(AIRLINE$0);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "airline" element
     */
    public void unsetAirline()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(AIRLINE$0, 0);
        }
    }
    
    /**
     * Gets the "area" element
     */
    public java.lang.String getArea()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(AREA$2, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "area" element
     */
    public org.apache.xmlbeans.XmlString xgetArea()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(AREA$2, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "area" element
     */
    public boolean isNilArea()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(AREA$2, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "area" element
     */
    public boolean isSetArea()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(AREA$2) != 0;
        }
    }
    
    /**
     * Sets the "area" element
     */
    public void setArea(java.lang.String area)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(AREA$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(AREA$2);
            }
            target.setStringValue(area);
        }
    }
    
    /**
     * Sets (as xml) the "area" element
     */
    public void xsetArea(org.apache.xmlbeans.XmlString area)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(AREA$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(AREA$2);
            }
            target.set(area);
        }
    }
    
    /**
     * Nils the "area" element
     */
    public void setNilArea()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(AREA$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(AREA$2);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "area" element
     */
    public void unsetArea()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(AREA$2, 0);
        }
    }
    
    /**
     * Gets the "station" element
     */
    public java.lang.String getStation()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STATION$4, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "station" element
     */
    public org.apache.xmlbeans.XmlString xgetStation()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STATION$4, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "station" element
     */
    public boolean isNilStation()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STATION$4, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "station" element
     */
    public boolean isSetStation()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(STATION$4) != 0;
        }
    }
    
    /**
     * Sets the "station" element
     */
    public void setStation(java.lang.String station)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STATION$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(STATION$4);
            }
            target.setStringValue(station);
        }
    }
    
    /**
     * Sets (as xml) the "station" element
     */
    public void xsetStation(org.apache.xmlbeans.XmlString station)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STATION$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(STATION$4);
            }
            target.set(station);
        }
    }
    
    /**
     * Nils the "station" element
     */
    public void setNilStation()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STATION$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(STATION$4);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "station" element
     */
    public void unsetStation()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(STATION$4, 0);
        }
    }
}
