/*
 * XML Type:  Itinerary
 * Namespace: http://common.wt_1_0.serviceprovider.nettracer.aero/xsd
 * Java type: aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary
 *
 * Automatically generated - do not modify.
 */
package aero.nettracer.serviceprovider.wt_1_0.common.xsd.impl;
/**
 * An XML Itinerary(@http://common.wt_1_0.serviceprovider.nettracer.aero/xsd).
 *
 * This is a complex type.
 */
public class ItineraryImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary
{
    
    public ItineraryImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName AIRLINE$0 = 
        new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd", "airline");
    private static final javax.xml.namespace.QName ARRIVALCITY$2 = 
        new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd", "arrivalCity");
    private static final javax.xml.namespace.QName DEPARTURECITY$4 = 
        new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd", "departureCity");
    private static final javax.xml.namespace.QName FLIGHTDATE$6 = 
        new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd", "flightDate");
    private static final javax.xml.namespace.QName FLIGHTNUM$8 = 
        new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd", "flightnum");
    
    
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
     * Gets the "departureCity" element
     */
    public java.lang.String getDepartureCity()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DEPARTURECITY$4, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DEPARTURECITY$4, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DEPARTURECITY$4, 0);
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
            return get_store().count_elements(DEPARTURECITY$4) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DEPARTURECITY$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DEPARTURECITY$4);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DEPARTURECITY$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DEPARTURECITY$4);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DEPARTURECITY$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DEPARTURECITY$4);
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
            get_store().remove_element(DEPARTURECITY$4, 0);
        }
    }
    
    /**
     * Gets the "flightDate" element
     */
    public java.util.Calendar getFlightDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FLIGHTDATE$6, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "flightDate" element
     */
    public org.apache.xmlbeans.XmlDateTime xgetFlightDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(FLIGHTDATE$6, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "flightDate" element
     */
    public boolean isNilFlightDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(FLIGHTDATE$6, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "flightDate" element
     */
    public boolean isSetFlightDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(FLIGHTDATE$6) != 0;
        }
    }
    
    /**
     * Sets the "flightDate" element
     */
    public void setFlightDate(java.util.Calendar flightDate)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FLIGHTDATE$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FLIGHTDATE$6);
            }
            target.setCalendarValue(flightDate);
        }
    }
    
    /**
     * Sets (as xml) the "flightDate" element
     */
    public void xsetFlightDate(org.apache.xmlbeans.XmlDateTime flightDate)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(FLIGHTDATE$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(FLIGHTDATE$6);
            }
            target.set(flightDate);
        }
    }
    
    /**
     * Nils the "flightDate" element
     */
    public void setNilFlightDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(FLIGHTDATE$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(FLIGHTDATE$6);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "flightDate" element
     */
    public void unsetFlightDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(FLIGHTDATE$6, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FLIGHTNUM$8, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FLIGHTNUM$8, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FLIGHTNUM$8, 0);
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
            return get_store().count_elements(FLIGHTNUM$8) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FLIGHTNUM$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FLIGHTNUM$8);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FLIGHTNUM$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(FLIGHTNUM$8);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FLIGHTNUM$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(FLIGHTNUM$8);
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
            get_store().remove_element(FLIGHTNUM$8, 0);
        }
    }
}
