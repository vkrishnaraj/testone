/*
 * XML Type:  WS_ForwardItinerary
 * Namespace: http://pojo.core.ws.nettracer.bagnet.com/xsd
 * Java type: com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.core.pojo.xsd.impl;
/**
 * An XML WS_ForwardItinerary(@http://pojo.core.ws.nettracer.bagnet.com/xsd).
 *
 * This is a complex type.
 */
public class WSForwardItineraryImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary
{
    
    public WSForwardItineraryImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName AIRLINE$0 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "airline");
    private static final javax.xml.namespace.QName ARRIVEDATE$2 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "arrivedate");
    private static final javax.xml.namespace.QName DEPARTDATE$4 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "departdate");
    private static final javax.xml.namespace.QName FLIGHTNUM$6 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "flightnum");
    private static final javax.xml.namespace.QName LEGFROM$8 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "legfrom");
    private static final javax.xml.namespace.QName LEGTO$10 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "legto");
    
    
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
     * Gets the "arrivedate" element
     */
    public java.util.Calendar getArrivedate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ARRIVEDATE$2, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "arrivedate" element
     */
    public org.apache.xmlbeans.XmlDate xgetArrivedate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDate target = null;
            target = (org.apache.xmlbeans.XmlDate)get_store().find_element_user(ARRIVEDATE$2, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "arrivedate" element
     */
    public boolean isNilArrivedate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDate target = null;
            target = (org.apache.xmlbeans.XmlDate)get_store().find_element_user(ARRIVEDATE$2, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "arrivedate" element
     */
    public boolean isSetArrivedate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(ARRIVEDATE$2) != 0;
        }
    }
    
    /**
     * Sets the "arrivedate" element
     */
    public void setArrivedate(java.util.Calendar arrivedate)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ARRIVEDATE$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ARRIVEDATE$2);
            }
            target.setCalendarValue(arrivedate);
        }
    }
    
    /**
     * Sets (as xml) the "arrivedate" element
     */
    public void xsetArrivedate(org.apache.xmlbeans.XmlDate arrivedate)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDate target = null;
            target = (org.apache.xmlbeans.XmlDate)get_store().find_element_user(ARRIVEDATE$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDate)get_store().add_element_user(ARRIVEDATE$2);
            }
            target.set(arrivedate);
        }
    }
    
    /**
     * Nils the "arrivedate" element
     */
    public void setNilArrivedate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDate target = null;
            target = (org.apache.xmlbeans.XmlDate)get_store().find_element_user(ARRIVEDATE$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDate)get_store().add_element_user(ARRIVEDATE$2);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "arrivedate" element
     */
    public void unsetArrivedate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(ARRIVEDATE$2, 0);
        }
    }
    
    /**
     * Gets the "departdate" element
     */
    public java.util.Calendar getDepartdate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DEPARTDATE$4, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "departdate" element
     */
    public org.apache.xmlbeans.XmlDate xgetDepartdate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDate target = null;
            target = (org.apache.xmlbeans.XmlDate)get_store().find_element_user(DEPARTDATE$4, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "departdate" element
     */
    public boolean isNilDepartdate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDate target = null;
            target = (org.apache.xmlbeans.XmlDate)get_store().find_element_user(DEPARTDATE$4, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "departdate" element
     */
    public boolean isSetDepartdate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DEPARTDATE$4) != 0;
        }
    }
    
    /**
     * Sets the "departdate" element
     */
    public void setDepartdate(java.util.Calendar departdate)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DEPARTDATE$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DEPARTDATE$4);
            }
            target.setCalendarValue(departdate);
        }
    }
    
    /**
     * Sets (as xml) the "departdate" element
     */
    public void xsetDepartdate(org.apache.xmlbeans.XmlDate departdate)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDate target = null;
            target = (org.apache.xmlbeans.XmlDate)get_store().find_element_user(DEPARTDATE$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDate)get_store().add_element_user(DEPARTDATE$4);
            }
            target.set(departdate);
        }
    }
    
    /**
     * Nils the "departdate" element
     */
    public void setNilDepartdate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDate target = null;
            target = (org.apache.xmlbeans.XmlDate)get_store().find_element_user(DEPARTDATE$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDate)get_store().add_element_user(DEPARTDATE$4);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "departdate" element
     */
    public void unsetDepartdate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DEPARTDATE$4, 0);
        }
    }
    
    /**
     * Gets the "flightnum" element
     */
    public java.lang.String getFlightnum()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FLIGHTNUM$6, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "flightnum" element
     */
    public org.apache.xmlbeans.XmlString xgetFlightnum()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FLIGHTNUM$6, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "flightnum" element
     */
    public boolean isNilFlightnum()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FLIGHTNUM$6, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "flightnum" element
     */
    public boolean isSetFlightnum()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(FLIGHTNUM$6) != 0;
        }
    }
    
    /**
     * Sets the "flightnum" element
     */
    public void setFlightnum(java.lang.String flightnum)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FLIGHTNUM$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FLIGHTNUM$6);
            }
            target.setStringValue(flightnum);
        }
    }
    
    /**
     * Sets (as xml) the "flightnum" element
     */
    public void xsetFlightnum(org.apache.xmlbeans.XmlString flightnum)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FLIGHTNUM$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(FLIGHTNUM$6);
            }
            target.set(flightnum);
        }
    }
    
    /**
     * Nils the "flightnum" element
     */
    public void setNilFlightnum()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FLIGHTNUM$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(FLIGHTNUM$6);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "flightnum" element
     */
    public void unsetFlightnum()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(FLIGHTNUM$6, 0);
        }
    }
    
    /**
     * Gets the "legfrom" element
     */
    public java.lang.String getLegfrom()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LEGFROM$8, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "legfrom" element
     */
    public org.apache.xmlbeans.XmlString xgetLegfrom()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(LEGFROM$8, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "legfrom" element
     */
    public boolean isNilLegfrom()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(LEGFROM$8, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "legfrom" element
     */
    public boolean isSetLegfrom()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(LEGFROM$8) != 0;
        }
    }
    
    /**
     * Sets the "legfrom" element
     */
    public void setLegfrom(java.lang.String legfrom)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LEGFROM$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(LEGFROM$8);
            }
            target.setStringValue(legfrom);
        }
    }
    
    /**
     * Sets (as xml) the "legfrom" element
     */
    public void xsetLegfrom(org.apache.xmlbeans.XmlString legfrom)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(LEGFROM$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(LEGFROM$8);
            }
            target.set(legfrom);
        }
    }
    
    /**
     * Nils the "legfrom" element
     */
    public void setNilLegfrom()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(LEGFROM$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(LEGFROM$8);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "legfrom" element
     */
    public void unsetLegfrom()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(LEGFROM$8, 0);
        }
    }
    
    /**
     * Gets the "legto" element
     */
    public java.lang.String getLegto()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LEGTO$10, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "legto" element
     */
    public org.apache.xmlbeans.XmlString xgetLegto()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(LEGTO$10, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "legto" element
     */
    public boolean isNilLegto()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(LEGTO$10, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "legto" element
     */
    public boolean isSetLegto()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(LEGTO$10) != 0;
        }
    }
    
    /**
     * Sets the "legto" element
     */
    public void setLegto(java.lang.String legto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LEGTO$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(LEGTO$10);
            }
            target.setStringValue(legto);
        }
    }
    
    /**
     * Sets (as xml) the "legto" element
     */
    public void xsetLegto(org.apache.xmlbeans.XmlString legto)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(LEGTO$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(LEGTO$10);
            }
            target.set(legto);
        }
    }
    
    /**
     * Nils the "legto" element
     */
    public void setNilLegto()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(LEGTO$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(LEGTO$10);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "legto" element
     */
    public void unsetLegto()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(LEGTO$10, 0);
        }
    }
}
