/*
 * XML Type:  Station
 * Namespace: http://db.tracing.nettracer.bagnet.com/xsd
 * Java type: com.bagnet.nettracer.tracing.db.xsd.Station
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.tracing.db.xsd.impl;
/**
 * An XML Station(@http://db.tracing.nettracer.bagnet.com/xsd).
 *
 * This is a complex type.
 */
public class StationImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.tracing.db.xsd.Station
{
    
    public StationImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName ACTIVE$0 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "active");
    private static final javax.xml.namespace.QName ADDRESS1$2 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "address1");
    private static final javax.xml.namespace.QName ADDRESS2$4 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "address2");
    private static final javax.xml.namespace.QName AIRPORTLOCATION$6 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "airport_location");
    private static final javax.xml.namespace.QName ASSOCIATEDAIRPORT$8 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "associated_airport");
    private static final javax.xml.namespace.QName CITY$10 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "city");
    private static final javax.xml.namespace.QName COMPANY$12 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "company");
    private static final javax.xml.namespace.QName COUNTRYCODEID$14 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "countrycode_ID");
    private static final javax.xml.namespace.QName EMAILLANGUAGE$16 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "emailLanguage");
    private static final javax.xml.namespace.QName GOAL$18 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "goal");
    private static final javax.xml.namespace.QName LZID$20 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "lz_ID");
    private static final javax.xml.namespace.QName OPERATIONHOURS$22 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "operation_hours");
    private static final javax.xml.namespace.QName PHONE$24 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "phone");
    private static final javax.xml.namespace.QName PRIORITY$26 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "priority");
    private static final javax.xml.namespace.QName STATEID$28 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "state_ID");
    private static final javax.xml.namespace.QName STATIONID$30 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "station_ID");
    private static final javax.xml.namespace.QName STATIONREGION$32 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "station_region");
    private static final javax.xml.namespace.QName STATIONREGIONMGR$34 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "station_region_mgr");
    private static final javax.xml.namespace.QName STATIONCODE$36 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "stationcode");
    private static final javax.xml.namespace.QName STATIONDESC$38 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "stationdesc");
    private static final javax.xml.namespace.QName THISOHDLZ$40 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "thisOhdLz");
    private static final javax.xml.namespace.QName WTSTATIONCODE$42 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "wt_stationcode");
    private static final javax.xml.namespace.QName ZIP$44 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "zip");
    
    
    /**
     * Gets the "active" element
     */
    public boolean getActive()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ACTIVE$0, 0);
            if (target == null)
            {
                return false;
            }
            return target.getBooleanValue();
        }
    }
    
    /**
     * Gets (as xml) the "active" element
     */
    public org.apache.xmlbeans.XmlBoolean xgetActive()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(ACTIVE$0, 0);
            return target;
        }
    }
    
    /**
     * True if has "active" element
     */
    public boolean isSetActive()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(ACTIVE$0) != 0;
        }
    }
    
    /**
     * Sets the "active" element
     */
    public void setActive(boolean active)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ACTIVE$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ACTIVE$0);
            }
            target.setBooleanValue(active);
        }
    }
    
    /**
     * Sets (as xml) the "active" element
     */
    public void xsetActive(org.apache.xmlbeans.XmlBoolean active)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(ACTIVE$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlBoolean)get_store().add_element_user(ACTIVE$0);
            }
            target.set(active);
        }
    }
    
    /**
     * Unsets the "active" element
     */
    public void unsetActive()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(ACTIVE$0, 0);
        }
    }
    
    /**
     * Gets the "address1" element
     */
    public java.lang.String getAddress1()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ADDRESS1$2, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ADDRESS1$2, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ADDRESS1$2, 0);
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
            return get_store().count_elements(ADDRESS1$2) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ADDRESS1$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ADDRESS1$2);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ADDRESS1$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(ADDRESS1$2);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ADDRESS1$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(ADDRESS1$2);
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
            get_store().remove_element(ADDRESS1$2, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ADDRESS2$4, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ADDRESS2$4, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ADDRESS2$4, 0);
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
            return get_store().count_elements(ADDRESS2$4) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ADDRESS2$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ADDRESS2$4);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ADDRESS2$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(ADDRESS2$4);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ADDRESS2$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(ADDRESS2$4);
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
            get_store().remove_element(ADDRESS2$4, 0);
        }
    }
    
    /**
     * Gets the "airport_location" element
     */
    public java.lang.String getAirportLocation()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(AIRPORTLOCATION$6, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "airport_location" element
     */
    public org.apache.xmlbeans.XmlString xgetAirportLocation()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(AIRPORTLOCATION$6, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "airport_location" element
     */
    public boolean isNilAirportLocation()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(AIRPORTLOCATION$6, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "airport_location" element
     */
    public boolean isSetAirportLocation()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(AIRPORTLOCATION$6) != 0;
        }
    }
    
    /**
     * Sets the "airport_location" element
     */
    public void setAirportLocation(java.lang.String airportLocation)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(AIRPORTLOCATION$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(AIRPORTLOCATION$6);
            }
            target.setStringValue(airportLocation);
        }
    }
    
    /**
     * Sets (as xml) the "airport_location" element
     */
    public void xsetAirportLocation(org.apache.xmlbeans.XmlString airportLocation)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(AIRPORTLOCATION$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(AIRPORTLOCATION$6);
            }
            target.set(airportLocation);
        }
    }
    
    /**
     * Nils the "airport_location" element
     */
    public void setNilAirportLocation()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(AIRPORTLOCATION$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(AIRPORTLOCATION$6);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "airport_location" element
     */
    public void unsetAirportLocation()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(AIRPORTLOCATION$6, 0);
        }
    }
    
    /**
     * Gets the "associated_airport" element
     */
    public java.lang.String getAssociatedAirport()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ASSOCIATEDAIRPORT$8, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "associated_airport" element
     */
    public org.apache.xmlbeans.XmlString xgetAssociatedAirport()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ASSOCIATEDAIRPORT$8, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "associated_airport" element
     */
    public boolean isNilAssociatedAirport()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ASSOCIATEDAIRPORT$8, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "associated_airport" element
     */
    public boolean isSetAssociatedAirport()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(ASSOCIATEDAIRPORT$8) != 0;
        }
    }
    
    /**
     * Sets the "associated_airport" element
     */
    public void setAssociatedAirport(java.lang.String associatedAirport)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ASSOCIATEDAIRPORT$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ASSOCIATEDAIRPORT$8);
            }
            target.setStringValue(associatedAirport);
        }
    }
    
    /**
     * Sets (as xml) the "associated_airport" element
     */
    public void xsetAssociatedAirport(org.apache.xmlbeans.XmlString associatedAirport)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ASSOCIATEDAIRPORT$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(ASSOCIATEDAIRPORT$8);
            }
            target.set(associatedAirport);
        }
    }
    
    /**
     * Nils the "associated_airport" element
     */
    public void setNilAssociatedAirport()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ASSOCIATEDAIRPORT$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(ASSOCIATEDAIRPORT$8);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "associated_airport" element
     */
    public void unsetAssociatedAirport()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(ASSOCIATEDAIRPORT$8, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CITY$10, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CITY$10, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CITY$10, 0);
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
            return get_store().count_elements(CITY$10) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CITY$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CITY$10);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CITY$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CITY$10);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CITY$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CITY$10);
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
            get_store().remove_element(CITY$10, 0);
        }
    }
    
    /**
     * Gets the "company" element
     */
    public com.bagnet.nettracer.tracing.db.xsd.Company getCompany()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.tracing.db.xsd.Company target = null;
            target = (com.bagnet.nettracer.tracing.db.xsd.Company)get_store().find_element_user(COMPANY$12, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Tests for nil "company" element
     */
    public boolean isNilCompany()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.tracing.db.xsd.Company target = null;
            target = (com.bagnet.nettracer.tracing.db.xsd.Company)get_store().find_element_user(COMPANY$12, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "company" element
     */
    public boolean isSetCompany()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(COMPANY$12) != 0;
        }
    }
    
    /**
     * Sets the "company" element
     */
    public void setCompany(com.bagnet.nettracer.tracing.db.xsd.Company company)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.tracing.db.xsd.Company target = null;
            target = (com.bagnet.nettracer.tracing.db.xsd.Company)get_store().find_element_user(COMPANY$12, 0);
            if (target == null)
            {
                target = (com.bagnet.nettracer.tracing.db.xsd.Company)get_store().add_element_user(COMPANY$12);
            }
            target.set(company);
        }
    }
    
    /**
     * Appends and returns a new empty "company" element
     */
    public com.bagnet.nettracer.tracing.db.xsd.Company addNewCompany()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.tracing.db.xsd.Company target = null;
            target = (com.bagnet.nettracer.tracing.db.xsd.Company)get_store().add_element_user(COMPANY$12);
            return target;
        }
    }
    
    /**
     * Nils the "company" element
     */
    public void setNilCompany()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.tracing.db.xsd.Company target = null;
            target = (com.bagnet.nettracer.tracing.db.xsd.Company)get_store().find_element_user(COMPANY$12, 0);
            if (target == null)
            {
                target = (com.bagnet.nettracer.tracing.db.xsd.Company)get_store().add_element_user(COMPANY$12);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "company" element
     */
    public void unsetCompany()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(COMPANY$12, 0);
        }
    }
    
    /**
     * Gets the "countrycode_ID" element
     */
    public java.lang.String getCountrycodeID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COUNTRYCODEID$14, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "countrycode_ID" element
     */
    public org.apache.xmlbeans.XmlString xgetCountrycodeID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COUNTRYCODEID$14, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "countrycode_ID" element
     */
    public boolean isNilCountrycodeID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COUNTRYCODEID$14, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "countrycode_ID" element
     */
    public boolean isSetCountrycodeID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(COUNTRYCODEID$14) != 0;
        }
    }
    
    /**
     * Sets the "countrycode_ID" element
     */
    public void setCountrycodeID(java.lang.String countrycodeID)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COUNTRYCODEID$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(COUNTRYCODEID$14);
            }
            target.setStringValue(countrycodeID);
        }
    }
    
    /**
     * Sets (as xml) the "countrycode_ID" element
     */
    public void xsetCountrycodeID(org.apache.xmlbeans.XmlString countrycodeID)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COUNTRYCODEID$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(COUNTRYCODEID$14);
            }
            target.set(countrycodeID);
        }
    }
    
    /**
     * Nils the "countrycode_ID" element
     */
    public void setNilCountrycodeID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COUNTRYCODEID$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(COUNTRYCODEID$14);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "countrycode_ID" element
     */
    public void unsetCountrycodeID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(COUNTRYCODEID$14, 0);
        }
    }
    
    /**
     * Gets the "emailLanguage" element
     */
    public java.lang.String getEmailLanguage()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EMAILLANGUAGE$16, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "emailLanguage" element
     */
    public org.apache.xmlbeans.XmlString xgetEmailLanguage()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(EMAILLANGUAGE$16, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "emailLanguage" element
     */
    public boolean isNilEmailLanguage()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(EMAILLANGUAGE$16, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "emailLanguage" element
     */
    public boolean isSetEmailLanguage()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(EMAILLANGUAGE$16) != 0;
        }
    }
    
    /**
     * Sets the "emailLanguage" element
     */
    public void setEmailLanguage(java.lang.String emailLanguage)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EMAILLANGUAGE$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(EMAILLANGUAGE$16);
            }
            target.setStringValue(emailLanguage);
        }
    }
    
    /**
     * Sets (as xml) the "emailLanguage" element
     */
    public void xsetEmailLanguage(org.apache.xmlbeans.XmlString emailLanguage)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(EMAILLANGUAGE$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(EMAILLANGUAGE$16);
            }
            target.set(emailLanguage);
        }
    }
    
    /**
     * Nils the "emailLanguage" element
     */
    public void setNilEmailLanguage()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(EMAILLANGUAGE$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(EMAILLANGUAGE$16);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "emailLanguage" element
     */
    public void unsetEmailLanguage()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(EMAILLANGUAGE$16, 0);
        }
    }
    
    /**
     * Gets the "goal" element
     */
    public double getGoal()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(GOAL$18, 0);
            if (target == null)
            {
                return 0.0;
            }
            return target.getDoubleValue();
        }
    }
    
    /**
     * Gets (as xml) the "goal" element
     */
    public org.apache.xmlbeans.XmlDouble xgetGoal()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(GOAL$18, 0);
            return target;
        }
    }
    
    /**
     * True if has "goal" element
     */
    public boolean isSetGoal()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(GOAL$18) != 0;
        }
    }
    
    /**
     * Sets the "goal" element
     */
    public void setGoal(double goal)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(GOAL$18, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(GOAL$18);
            }
            target.setDoubleValue(goal);
        }
    }
    
    /**
     * Sets (as xml) the "goal" element
     */
    public void xsetGoal(org.apache.xmlbeans.XmlDouble goal)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(GOAL$18, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(GOAL$18);
            }
            target.set(goal);
        }
    }
    
    /**
     * Unsets the "goal" element
     */
    public void unsetGoal()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(GOAL$18, 0);
        }
    }
    
    /**
     * Gets the "lz_ID" element
     */
    public int getLzID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LZID$20, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "lz_ID" element
     */
    public org.apache.xmlbeans.XmlInt xgetLzID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(LZID$20, 0);
            return target;
        }
    }
    
    /**
     * True if has "lz_ID" element
     */
    public boolean isSetLzID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(LZID$20) != 0;
        }
    }
    
    /**
     * Sets the "lz_ID" element
     */
    public void setLzID(int lzID)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LZID$20, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(LZID$20);
            }
            target.setIntValue(lzID);
        }
    }
    
    /**
     * Sets (as xml) the "lz_ID" element
     */
    public void xsetLzID(org.apache.xmlbeans.XmlInt lzID)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(LZID$20, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(LZID$20);
            }
            target.set(lzID);
        }
    }
    
    /**
     * Unsets the "lz_ID" element
     */
    public void unsetLzID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(LZID$20, 0);
        }
    }
    
    /**
     * Gets the "operation_hours" element
     */
    public java.lang.String getOperationHours()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(OPERATIONHOURS$22, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "operation_hours" element
     */
    public org.apache.xmlbeans.XmlString xgetOperationHours()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(OPERATIONHOURS$22, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "operation_hours" element
     */
    public boolean isNilOperationHours()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(OPERATIONHOURS$22, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "operation_hours" element
     */
    public boolean isSetOperationHours()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(OPERATIONHOURS$22) != 0;
        }
    }
    
    /**
     * Sets the "operation_hours" element
     */
    public void setOperationHours(java.lang.String operationHours)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(OPERATIONHOURS$22, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(OPERATIONHOURS$22);
            }
            target.setStringValue(operationHours);
        }
    }
    
    /**
     * Sets (as xml) the "operation_hours" element
     */
    public void xsetOperationHours(org.apache.xmlbeans.XmlString operationHours)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(OPERATIONHOURS$22, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(OPERATIONHOURS$22);
            }
            target.set(operationHours);
        }
    }
    
    /**
     * Nils the "operation_hours" element
     */
    public void setNilOperationHours()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(OPERATIONHOURS$22, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(OPERATIONHOURS$22);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "operation_hours" element
     */
    public void unsetOperationHours()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(OPERATIONHOURS$22, 0);
        }
    }
    
    /**
     * Gets the "phone" element
     */
    public java.lang.String getPhone()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PHONE$24, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "phone" element
     */
    public org.apache.xmlbeans.XmlString xgetPhone()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PHONE$24, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "phone" element
     */
    public boolean isNilPhone()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PHONE$24, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "phone" element
     */
    public boolean isSetPhone()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(PHONE$24) != 0;
        }
    }
    
    /**
     * Sets the "phone" element
     */
    public void setPhone(java.lang.String phone)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PHONE$24, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PHONE$24);
            }
            target.setStringValue(phone);
        }
    }
    
    /**
     * Sets (as xml) the "phone" element
     */
    public void xsetPhone(org.apache.xmlbeans.XmlString phone)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PHONE$24, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PHONE$24);
            }
            target.set(phone);
        }
    }
    
    /**
     * Nils the "phone" element
     */
    public void setNilPhone()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PHONE$24, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PHONE$24);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "phone" element
     */
    public void unsetPhone()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(PHONE$24, 0);
        }
    }
    
    /**
     * Gets the "priority" element
     */
    public int getPriority()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PRIORITY$26, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "priority" element
     */
    public org.apache.xmlbeans.XmlInt xgetPriority()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(PRIORITY$26, 0);
            return target;
        }
    }
    
    /**
     * True if has "priority" element
     */
    public boolean isSetPriority()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(PRIORITY$26) != 0;
        }
    }
    
    /**
     * Sets the "priority" element
     */
    public void setPriority(int priority)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PRIORITY$26, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PRIORITY$26);
            }
            target.setIntValue(priority);
        }
    }
    
    /**
     * Sets (as xml) the "priority" element
     */
    public void xsetPriority(org.apache.xmlbeans.XmlInt priority)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(PRIORITY$26, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(PRIORITY$26);
            }
            target.set(priority);
        }
    }
    
    /**
     * Unsets the "priority" element
     */
    public void unsetPriority()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(PRIORITY$26, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STATEID$28, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STATEID$28, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STATEID$28, 0);
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
            return get_store().count_elements(STATEID$28) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STATEID$28, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(STATEID$28);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STATEID$28, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(STATEID$28);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STATEID$28, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(STATEID$28);
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
            get_store().remove_element(STATEID$28, 0);
        }
    }
    
    /**
     * Gets the "station_ID" element
     */
    public int getStationID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STATIONID$30, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "station_ID" element
     */
    public org.apache.xmlbeans.XmlInt xgetStationID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(STATIONID$30, 0);
            return target;
        }
    }
    
    /**
     * True if has "station_ID" element
     */
    public boolean isSetStationID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(STATIONID$30) != 0;
        }
    }
    
    /**
     * Sets the "station_ID" element
     */
    public void setStationID(int stationID)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STATIONID$30, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(STATIONID$30);
            }
            target.setIntValue(stationID);
        }
    }
    
    /**
     * Sets (as xml) the "station_ID" element
     */
    public void xsetStationID(org.apache.xmlbeans.XmlInt stationID)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(STATIONID$30, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(STATIONID$30);
            }
            target.set(stationID);
        }
    }
    
    /**
     * Unsets the "station_ID" element
     */
    public void unsetStationID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(STATIONID$30, 0);
        }
    }
    
    /**
     * Gets the "station_region" element
     */
    public java.lang.String getStationRegion()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STATIONREGION$32, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "station_region" element
     */
    public org.apache.xmlbeans.XmlString xgetStationRegion()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STATIONREGION$32, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "station_region" element
     */
    public boolean isNilStationRegion()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STATIONREGION$32, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "station_region" element
     */
    public boolean isSetStationRegion()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(STATIONREGION$32) != 0;
        }
    }
    
    /**
     * Sets the "station_region" element
     */
    public void setStationRegion(java.lang.String stationRegion)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STATIONREGION$32, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(STATIONREGION$32);
            }
            target.setStringValue(stationRegion);
        }
    }
    
    /**
     * Sets (as xml) the "station_region" element
     */
    public void xsetStationRegion(org.apache.xmlbeans.XmlString stationRegion)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STATIONREGION$32, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(STATIONREGION$32);
            }
            target.set(stationRegion);
        }
    }
    
    /**
     * Nils the "station_region" element
     */
    public void setNilStationRegion()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STATIONREGION$32, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(STATIONREGION$32);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "station_region" element
     */
    public void unsetStationRegion()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(STATIONREGION$32, 0);
        }
    }
    
    /**
     * Gets the "station_region_mgr" element
     */
    public java.lang.String getStationRegionMgr()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STATIONREGIONMGR$34, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "station_region_mgr" element
     */
    public org.apache.xmlbeans.XmlString xgetStationRegionMgr()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STATIONREGIONMGR$34, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "station_region_mgr" element
     */
    public boolean isNilStationRegionMgr()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STATIONREGIONMGR$34, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "station_region_mgr" element
     */
    public boolean isSetStationRegionMgr()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(STATIONREGIONMGR$34) != 0;
        }
    }
    
    /**
     * Sets the "station_region_mgr" element
     */
    public void setStationRegionMgr(java.lang.String stationRegionMgr)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STATIONREGIONMGR$34, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(STATIONREGIONMGR$34);
            }
            target.setStringValue(stationRegionMgr);
        }
    }
    
    /**
     * Sets (as xml) the "station_region_mgr" element
     */
    public void xsetStationRegionMgr(org.apache.xmlbeans.XmlString stationRegionMgr)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STATIONREGIONMGR$34, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(STATIONREGIONMGR$34);
            }
            target.set(stationRegionMgr);
        }
    }
    
    /**
     * Nils the "station_region_mgr" element
     */
    public void setNilStationRegionMgr()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STATIONREGIONMGR$34, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(STATIONREGIONMGR$34);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "station_region_mgr" element
     */
    public void unsetStationRegionMgr()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(STATIONREGIONMGR$34, 0);
        }
    }
    
    /**
     * Gets the "stationcode" element
     */
    public java.lang.String getStationcode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STATIONCODE$36, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "stationcode" element
     */
    public org.apache.xmlbeans.XmlString xgetStationcode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STATIONCODE$36, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "stationcode" element
     */
    public boolean isNilStationcode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STATIONCODE$36, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "stationcode" element
     */
    public boolean isSetStationcode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(STATIONCODE$36) != 0;
        }
    }
    
    /**
     * Sets the "stationcode" element
     */
    public void setStationcode(java.lang.String stationcode)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STATIONCODE$36, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(STATIONCODE$36);
            }
            target.setStringValue(stationcode);
        }
    }
    
    /**
     * Sets (as xml) the "stationcode" element
     */
    public void xsetStationcode(org.apache.xmlbeans.XmlString stationcode)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STATIONCODE$36, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(STATIONCODE$36);
            }
            target.set(stationcode);
        }
    }
    
    /**
     * Nils the "stationcode" element
     */
    public void setNilStationcode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STATIONCODE$36, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(STATIONCODE$36);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "stationcode" element
     */
    public void unsetStationcode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(STATIONCODE$36, 0);
        }
    }
    
    /**
     * Gets the "stationdesc" element
     */
    public java.lang.String getStationdesc()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STATIONDESC$38, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "stationdesc" element
     */
    public org.apache.xmlbeans.XmlString xgetStationdesc()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STATIONDESC$38, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "stationdesc" element
     */
    public boolean isNilStationdesc()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STATIONDESC$38, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "stationdesc" element
     */
    public boolean isSetStationdesc()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(STATIONDESC$38) != 0;
        }
    }
    
    /**
     * Sets the "stationdesc" element
     */
    public void setStationdesc(java.lang.String stationdesc)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STATIONDESC$38, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(STATIONDESC$38);
            }
            target.setStringValue(stationdesc);
        }
    }
    
    /**
     * Sets (as xml) the "stationdesc" element
     */
    public void xsetStationdesc(org.apache.xmlbeans.XmlString stationdesc)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STATIONDESC$38, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(STATIONDESC$38);
            }
            target.set(stationdesc);
        }
    }
    
    /**
     * Nils the "stationdesc" element
     */
    public void setNilStationdesc()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STATIONDESC$38, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(STATIONDESC$38);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "stationdesc" element
     */
    public void unsetStationdesc()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(STATIONDESC$38, 0);
        }
    }
    
    /**
     * Gets the "thisOhdLz" element
     */
    public boolean getThisOhdLz()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(THISOHDLZ$40, 0);
            if (target == null)
            {
                return false;
            }
            return target.getBooleanValue();
        }
    }
    
    /**
     * Gets (as xml) the "thisOhdLz" element
     */
    public org.apache.xmlbeans.XmlBoolean xgetThisOhdLz()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(THISOHDLZ$40, 0);
            return target;
        }
    }
    
    /**
     * True if has "thisOhdLz" element
     */
    public boolean isSetThisOhdLz()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(THISOHDLZ$40) != 0;
        }
    }
    
    /**
     * Sets the "thisOhdLz" element
     */
    public void setThisOhdLz(boolean thisOhdLz)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(THISOHDLZ$40, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(THISOHDLZ$40);
            }
            target.setBooleanValue(thisOhdLz);
        }
    }
    
    /**
     * Sets (as xml) the "thisOhdLz" element
     */
    public void xsetThisOhdLz(org.apache.xmlbeans.XmlBoolean thisOhdLz)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(THISOHDLZ$40, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlBoolean)get_store().add_element_user(THISOHDLZ$40);
            }
            target.set(thisOhdLz);
        }
    }
    
    /**
     * Unsets the "thisOhdLz" element
     */
    public void unsetThisOhdLz()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(THISOHDLZ$40, 0);
        }
    }
    
    /**
     * Gets the "wt_stationcode" element
     */
    public java.lang.String getWtStationcode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(WTSTATIONCODE$42, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "wt_stationcode" element
     */
    public org.apache.xmlbeans.XmlString xgetWtStationcode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(WTSTATIONCODE$42, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "wt_stationcode" element
     */
    public boolean isNilWtStationcode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(WTSTATIONCODE$42, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "wt_stationcode" element
     */
    public boolean isSetWtStationcode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(WTSTATIONCODE$42) != 0;
        }
    }
    
    /**
     * Sets the "wt_stationcode" element
     */
    public void setWtStationcode(java.lang.String wtStationcode)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(WTSTATIONCODE$42, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(WTSTATIONCODE$42);
            }
            target.setStringValue(wtStationcode);
        }
    }
    
    /**
     * Sets (as xml) the "wt_stationcode" element
     */
    public void xsetWtStationcode(org.apache.xmlbeans.XmlString wtStationcode)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(WTSTATIONCODE$42, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(WTSTATIONCODE$42);
            }
            target.set(wtStationcode);
        }
    }
    
    /**
     * Nils the "wt_stationcode" element
     */
    public void setNilWtStationcode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(WTSTATIONCODE$42, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(WTSTATIONCODE$42);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "wt_stationcode" element
     */
    public void unsetWtStationcode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(WTSTATIONCODE$42, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ZIP$44, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ZIP$44, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ZIP$44, 0);
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
            return get_store().count_elements(ZIP$44) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ZIP$44, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ZIP$44);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ZIP$44, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(ZIP$44);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ZIP$44, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(ZIP$44);
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
            get_store().remove_element(ZIP$44, 0);
        }
    }
}
