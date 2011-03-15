/*
 * XML Type:  IncidentItinerary
 * Namespace: http://onlineclaims.ws.nettracer.bagnet.com/xsd
 * Java type: com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentItinerary
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.onlineclaims.xsd.impl;
/**
 * An XML IncidentItinerary(@http://onlineclaims.ws.nettracer.bagnet.com/xsd).
 *
 * This is a complex type.
 */
public class IncidentItineraryImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentItinerary
{
    
    public IncidentItineraryImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName AIRLINE$0 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "airline");
    private static final javax.xml.namespace.QName ARRIVALCITY$2 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "arrivalCity");
    private static final javax.xml.namespace.QName ARRIVALDATE$4 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "arrivalDate");
    private static final javax.xml.namespace.QName DEPARTURECITY$6 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "departureCity");
    private static final javax.xml.namespace.QName DEPARTUREDATE$8 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "departureDate");
    private static final javax.xml.namespace.QName FLIGHTNUM$10 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "flightNum");
    private static final javax.xml.namespace.QName TYPE$12 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "type");
    
    
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
     * Gets the "arrivalCity" element
     */
    public java.lang.String getArrivalCity()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ARRIVALCITY$2, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "arrivalCity" element
     */
    public org.apache.xmlbeans.XmlString xgetArrivalCity()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ARRIVALCITY$2, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "arrivalCity" element
     */
    public boolean isNilArrivalCity()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ARRIVALCITY$2, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "arrivalCity" element
     */
    public boolean isSetArrivalCity()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(ARRIVALCITY$2) != 0;
        }
    }
    
    /**
     * Sets the "arrivalCity" element
     */
    public void setArrivalCity(java.lang.String arrivalCity)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ARRIVALCITY$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ARRIVALCITY$2);
            }
            target.setStringValue(arrivalCity);
        }
    }
    
    /**
     * Sets (as xml) the "arrivalCity" element
     */
    public void xsetArrivalCity(org.apache.xmlbeans.XmlString arrivalCity)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ARRIVALCITY$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(ARRIVALCITY$2);
            }
            target.set(arrivalCity);
        }
    }
    
    /**
     * Nils the "arrivalCity" element
     */
    public void setNilArrivalCity()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ARRIVALCITY$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(ARRIVALCITY$2);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "arrivalCity" element
     */
    public void unsetArrivalCity()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(ARRIVALCITY$2, 0);
        }
    }
    
    /**
     * Gets the "arrivalDate" element
     */
    public java.util.Calendar getArrivalDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ARRIVALDATE$4, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "arrivalDate" element
     */
    public org.apache.xmlbeans.XmlDate xgetArrivalDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDate target = null;
            target = (org.apache.xmlbeans.XmlDate)get_store().find_element_user(ARRIVALDATE$4, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "arrivalDate" element
     */
    public boolean isNilArrivalDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDate target = null;
            target = (org.apache.xmlbeans.XmlDate)get_store().find_element_user(ARRIVALDATE$4, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "arrivalDate" element
     */
    public boolean isSetArrivalDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(ARRIVALDATE$4) != 0;
        }
    }
    
    /**
     * Sets the "arrivalDate" element
     */
    public void setArrivalDate(java.util.Calendar arrivalDate)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ARRIVALDATE$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ARRIVALDATE$4);
            }
            target.setCalendarValue(arrivalDate);
        }
    }
    
    /**
     * Sets (as xml) the "arrivalDate" element
     */
    public void xsetArrivalDate(org.apache.xmlbeans.XmlDate arrivalDate)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDate target = null;
            target = (org.apache.xmlbeans.XmlDate)get_store().find_element_user(ARRIVALDATE$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDate)get_store().add_element_user(ARRIVALDATE$4);
            }
            target.set(arrivalDate);
        }
    }
    
    /**
     * Nils the "arrivalDate" element
     */
    public void setNilArrivalDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDate target = null;
            target = (org.apache.xmlbeans.XmlDate)get_store().find_element_user(ARRIVALDATE$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDate)get_store().add_element_user(ARRIVALDATE$4);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "arrivalDate" element
     */
    public void unsetArrivalDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(ARRIVALDATE$4, 0);
        }
    }
    
    /**
     * Gets the "departureCity" element
     */
    public java.lang.String getDepartureCity()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DEPARTURECITY$6, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "departureCity" element
     */
    public org.apache.xmlbeans.XmlString xgetDepartureCity()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DEPARTURECITY$6, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "departureCity" element
     */
    public boolean isNilDepartureCity()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DEPARTURECITY$6, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "departureCity" element
     */
    public boolean isSetDepartureCity()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DEPARTURECITY$6) != 0;
        }
    }
    
    /**
     * Sets the "departureCity" element
     */
    public void setDepartureCity(java.lang.String departureCity)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DEPARTURECITY$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DEPARTURECITY$6);
            }
            target.setStringValue(departureCity);
        }
    }
    
    /**
     * Sets (as xml) the "departureCity" element
     */
    public void xsetDepartureCity(org.apache.xmlbeans.XmlString departureCity)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DEPARTURECITY$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DEPARTURECITY$6);
            }
            target.set(departureCity);
        }
    }
    
    /**
     * Nils the "departureCity" element
     */
    public void setNilDepartureCity()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DEPARTURECITY$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DEPARTURECITY$6);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "departureCity" element
     */
    public void unsetDepartureCity()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DEPARTURECITY$6, 0);
        }
    }
    
    /**
     * Gets the "departureDate" element
     */
    public java.util.Calendar getDepartureDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DEPARTUREDATE$8, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "departureDate" element
     */
    public org.apache.xmlbeans.XmlDate xgetDepartureDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDate target = null;
            target = (org.apache.xmlbeans.XmlDate)get_store().find_element_user(DEPARTUREDATE$8, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "departureDate" element
     */
    public boolean isNilDepartureDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDate target = null;
            target = (org.apache.xmlbeans.XmlDate)get_store().find_element_user(DEPARTUREDATE$8, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "departureDate" element
     */
    public boolean isSetDepartureDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DEPARTUREDATE$8) != 0;
        }
    }
    
    /**
     * Sets the "departureDate" element
     */
    public void setDepartureDate(java.util.Calendar departureDate)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DEPARTUREDATE$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DEPARTUREDATE$8);
            }
            target.setCalendarValue(departureDate);
        }
    }
    
    /**
     * Sets (as xml) the "departureDate" element
     */
    public void xsetDepartureDate(org.apache.xmlbeans.XmlDate departureDate)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDate target = null;
            target = (org.apache.xmlbeans.XmlDate)get_store().find_element_user(DEPARTUREDATE$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDate)get_store().add_element_user(DEPARTUREDATE$8);
            }
            target.set(departureDate);
        }
    }
    
    /**
     * Nils the "departureDate" element
     */
    public void setNilDepartureDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDate target = null;
            target = (org.apache.xmlbeans.XmlDate)get_store().find_element_user(DEPARTUREDATE$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDate)get_store().add_element_user(DEPARTUREDATE$8);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "departureDate" element
     */
    public void unsetDepartureDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DEPARTUREDATE$8, 0);
        }
    }
    
    /**
     * Gets the "flightNum" element
     */
    public java.lang.String getFlightNum()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FLIGHTNUM$10, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "flightNum" element
     */
    public org.apache.xmlbeans.XmlString xgetFlightNum()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FLIGHTNUM$10, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "flightNum" element
     */
    public boolean isNilFlightNum()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FLIGHTNUM$10, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "flightNum" element
     */
    public boolean isSetFlightNum()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(FLIGHTNUM$10) != 0;
        }
    }
    
    /**
     * Sets the "flightNum" element
     */
    public void setFlightNum(java.lang.String flightNum)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FLIGHTNUM$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FLIGHTNUM$10);
            }
            target.setStringValue(flightNum);
        }
    }
    
    /**
     * Sets (as xml) the "flightNum" element
     */
    public void xsetFlightNum(org.apache.xmlbeans.XmlString flightNum)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FLIGHTNUM$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(FLIGHTNUM$10);
            }
            target.set(flightNum);
        }
    }
    
    /**
     * Nils the "flightNum" element
     */
    public void setNilFlightNum()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FLIGHTNUM$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(FLIGHTNUM$10);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "flightNum" element
     */
    public void unsetFlightNum()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(FLIGHTNUM$10, 0);
        }
    }
    
    /**
     * Gets the "type" element
     */
    public int getType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TYPE$12, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "type" element
     */
    public org.apache.xmlbeans.XmlInt xgetType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(TYPE$12, 0);
            return target;
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
    public void setType(int type)
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
            target.setIntValue(type);
        }
    }
    
    /**
     * Sets (as xml) the "type" element
     */
    public void xsetType(org.apache.xmlbeans.XmlInt type)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(TYPE$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(TYPE$12);
            }
            target.set(type);
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
