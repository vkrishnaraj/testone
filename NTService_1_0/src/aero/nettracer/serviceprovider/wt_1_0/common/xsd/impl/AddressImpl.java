/*
 * XML Type:  Address
 * Namespace: http://common.wt_1_0.serviceprovider.nettracer.aero/xsd
 * Java type: aero.nettracer.serviceprovider.wt_1_0.common.xsd.Address
 *
 * Automatically generated - do not modify.
 */
package aero.nettracer.serviceprovider.wt_1_0.common.xsd.impl;
/**
 * An XML Address(@http://common.wt_1_0.serviceprovider.nettracer.aero/xsd).
 *
 * This is a complex type.
 */
public class AddressImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aero.nettracer.serviceprovider.wt_1_0.common.xsd.Address
{
    
    public AddressImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName ADDRESS1$0 = 
        new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd", "address1");
    private static final javax.xml.namespace.QName ADDRESS2$2 = 
        new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd", "address2");
    private static final javax.xml.namespace.QName ALTPHONE$4 = 
        new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd", "altPhone");
    private static final javax.xml.namespace.QName CITY$6 = 
        new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd", "city");
    private static final javax.xml.namespace.QName COUNTRY$8 = 
        new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd", "country");
    private static final javax.xml.namespace.QName EMAILADDRESS$10 = 
        new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd", "emailAddress");
    private static final javax.xml.namespace.QName HOMEPHONE$12 = 
        new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd", "homePhone");
    private static final javax.xml.namespace.QName MOBILEPHONE$14 = 
        new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd", "mobilePhone");
    private static final javax.xml.namespace.QName PAGERNUMBER$16 = 
        new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd", "pagerNumber");
    private static final javax.xml.namespace.QName PROVINCE$18 = 
        new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd", "province");
    private static final javax.xml.namespace.QName STATE$20 = 
        new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd", "state");
    private static final javax.xml.namespace.QName TEMPORARYADDRESS$22 = 
        new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd", "temporaryAddress");
    private static final javax.xml.namespace.QName WORKPHONE$24 = 
        new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd", "workPhone");
    private static final javax.xml.namespace.QName ZIP$26 = 
        new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd", "zip");
    
    
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
     * Gets the "altPhone" element
     */
    public java.lang.String getAltPhone()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ALTPHONE$4, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "altPhone" element
     */
    public org.apache.xmlbeans.XmlString xgetAltPhone()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ALTPHONE$4, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "altPhone" element
     */
    public boolean isNilAltPhone()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ALTPHONE$4, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "altPhone" element
     */
    public boolean isSetAltPhone()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(ALTPHONE$4) != 0;
        }
    }
    
    /**
     * Sets the "altPhone" element
     */
    public void setAltPhone(java.lang.String altPhone)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ALTPHONE$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ALTPHONE$4);
            }
            target.setStringValue(altPhone);
        }
    }
    
    /**
     * Sets (as xml) the "altPhone" element
     */
    public void xsetAltPhone(org.apache.xmlbeans.XmlString altPhone)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ALTPHONE$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(ALTPHONE$4);
            }
            target.set(altPhone);
        }
    }
    
    /**
     * Nils the "altPhone" element
     */
    public void setNilAltPhone()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ALTPHONE$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(ALTPHONE$4);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "altPhone" element
     */
    public void unsetAltPhone()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(ALTPHONE$4, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CITY$6, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CITY$6, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CITY$6, 0);
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
            return get_store().count_elements(CITY$6) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CITY$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CITY$6);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CITY$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CITY$6);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CITY$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CITY$6);
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
            get_store().remove_element(CITY$6, 0);
        }
    }
    
    /**
     * Gets the "country" element
     */
    public java.lang.String getCountry()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COUNTRY$8, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "country" element
     */
    public org.apache.xmlbeans.XmlString xgetCountry()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COUNTRY$8, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "country" element
     */
    public boolean isNilCountry()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COUNTRY$8, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "country" element
     */
    public boolean isSetCountry()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(COUNTRY$8) != 0;
        }
    }
    
    /**
     * Sets the "country" element
     */
    public void setCountry(java.lang.String country)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COUNTRY$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(COUNTRY$8);
            }
            target.setStringValue(country);
        }
    }
    
    /**
     * Sets (as xml) the "country" element
     */
    public void xsetCountry(org.apache.xmlbeans.XmlString country)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COUNTRY$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(COUNTRY$8);
            }
            target.set(country);
        }
    }
    
    /**
     * Nils the "country" element
     */
    public void setNilCountry()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COUNTRY$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(COUNTRY$8);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "country" element
     */
    public void unsetCountry()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(COUNTRY$8, 0);
        }
    }
    
    /**
     * Gets the "emailAddress" element
     */
    public java.lang.String getEmailAddress()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EMAILADDRESS$10, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "emailAddress" element
     */
    public org.apache.xmlbeans.XmlString xgetEmailAddress()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(EMAILADDRESS$10, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "emailAddress" element
     */
    public boolean isNilEmailAddress()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(EMAILADDRESS$10, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "emailAddress" element
     */
    public boolean isSetEmailAddress()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(EMAILADDRESS$10) != 0;
        }
    }
    
    /**
     * Sets the "emailAddress" element
     */
    public void setEmailAddress(java.lang.String emailAddress)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EMAILADDRESS$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(EMAILADDRESS$10);
            }
            target.setStringValue(emailAddress);
        }
    }
    
    /**
     * Sets (as xml) the "emailAddress" element
     */
    public void xsetEmailAddress(org.apache.xmlbeans.XmlString emailAddress)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(EMAILADDRESS$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(EMAILADDRESS$10);
            }
            target.set(emailAddress);
        }
    }
    
    /**
     * Nils the "emailAddress" element
     */
    public void setNilEmailAddress()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(EMAILADDRESS$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(EMAILADDRESS$10);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "emailAddress" element
     */
    public void unsetEmailAddress()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(EMAILADDRESS$10, 0);
        }
    }
    
    /**
     * Gets the "homePhone" element
     */
    public java.lang.String getHomePhone()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(HOMEPHONE$12, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "homePhone" element
     */
    public org.apache.xmlbeans.XmlString xgetHomePhone()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(HOMEPHONE$12, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "homePhone" element
     */
    public boolean isNilHomePhone()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(HOMEPHONE$12, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "homePhone" element
     */
    public boolean isSetHomePhone()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(HOMEPHONE$12) != 0;
        }
    }
    
    /**
     * Sets the "homePhone" element
     */
    public void setHomePhone(java.lang.String homePhone)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(HOMEPHONE$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(HOMEPHONE$12);
            }
            target.setStringValue(homePhone);
        }
    }
    
    /**
     * Sets (as xml) the "homePhone" element
     */
    public void xsetHomePhone(org.apache.xmlbeans.XmlString homePhone)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(HOMEPHONE$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(HOMEPHONE$12);
            }
            target.set(homePhone);
        }
    }
    
    /**
     * Nils the "homePhone" element
     */
    public void setNilHomePhone()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(HOMEPHONE$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(HOMEPHONE$12);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "homePhone" element
     */
    public void unsetHomePhone()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(HOMEPHONE$12, 0);
        }
    }
    
    /**
     * Gets the "mobilePhone" element
     */
    public java.lang.String getMobilePhone()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MOBILEPHONE$14, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "mobilePhone" element
     */
    public org.apache.xmlbeans.XmlString xgetMobilePhone()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MOBILEPHONE$14, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "mobilePhone" element
     */
    public boolean isNilMobilePhone()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MOBILEPHONE$14, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "mobilePhone" element
     */
    public boolean isSetMobilePhone()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(MOBILEPHONE$14) != 0;
        }
    }
    
    /**
     * Sets the "mobilePhone" element
     */
    public void setMobilePhone(java.lang.String mobilePhone)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MOBILEPHONE$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(MOBILEPHONE$14);
            }
            target.setStringValue(mobilePhone);
        }
    }
    
    /**
     * Sets (as xml) the "mobilePhone" element
     */
    public void xsetMobilePhone(org.apache.xmlbeans.XmlString mobilePhone)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MOBILEPHONE$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(MOBILEPHONE$14);
            }
            target.set(mobilePhone);
        }
    }
    
    /**
     * Nils the "mobilePhone" element
     */
    public void setNilMobilePhone()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MOBILEPHONE$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(MOBILEPHONE$14);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "mobilePhone" element
     */
    public void unsetMobilePhone()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(MOBILEPHONE$14, 0);
        }
    }
    
    /**
     * Gets the "pagerNumber" element
     */
    public java.lang.String getPagerNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PAGERNUMBER$16, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "pagerNumber" element
     */
    public org.apache.xmlbeans.XmlString xgetPagerNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PAGERNUMBER$16, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "pagerNumber" element
     */
    public boolean isNilPagerNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PAGERNUMBER$16, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "pagerNumber" element
     */
    public boolean isSetPagerNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(PAGERNUMBER$16) != 0;
        }
    }
    
    /**
     * Sets the "pagerNumber" element
     */
    public void setPagerNumber(java.lang.String pagerNumber)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PAGERNUMBER$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PAGERNUMBER$16);
            }
            target.setStringValue(pagerNumber);
        }
    }
    
    /**
     * Sets (as xml) the "pagerNumber" element
     */
    public void xsetPagerNumber(org.apache.xmlbeans.XmlString pagerNumber)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PAGERNUMBER$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PAGERNUMBER$16);
            }
            target.set(pagerNumber);
        }
    }
    
    /**
     * Nils the "pagerNumber" element
     */
    public void setNilPagerNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PAGERNUMBER$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PAGERNUMBER$16);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "pagerNumber" element
     */
    public void unsetPagerNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(PAGERNUMBER$16, 0);
        }
    }
    
    /**
     * Gets the "province" element
     */
    public java.lang.String getProvince()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PROVINCE$18, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "province" element
     */
    public org.apache.xmlbeans.XmlString xgetProvince()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PROVINCE$18, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "province" element
     */
    public boolean isNilProvince()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PROVINCE$18, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "province" element
     */
    public boolean isSetProvince()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(PROVINCE$18) != 0;
        }
    }
    
    /**
     * Sets the "province" element
     */
    public void setProvince(java.lang.String province)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PROVINCE$18, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PROVINCE$18);
            }
            target.setStringValue(province);
        }
    }
    
    /**
     * Sets (as xml) the "province" element
     */
    public void xsetProvince(org.apache.xmlbeans.XmlString province)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PROVINCE$18, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PROVINCE$18);
            }
            target.set(province);
        }
    }
    
    /**
     * Nils the "province" element
     */
    public void setNilProvince()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PROVINCE$18, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PROVINCE$18);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "province" element
     */
    public void unsetProvince()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(PROVINCE$18, 0);
        }
    }
    
    /**
     * Gets the "state" element
     */
    public java.lang.String getState()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STATE$20, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "state" element
     */
    public org.apache.xmlbeans.XmlString xgetState()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STATE$20, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "state" element
     */
    public boolean isNilState()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STATE$20, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "state" element
     */
    public boolean isSetState()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(STATE$20) != 0;
        }
    }
    
    /**
     * Sets the "state" element
     */
    public void setState(java.lang.String state)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STATE$20, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(STATE$20);
            }
            target.setStringValue(state);
        }
    }
    
    /**
     * Sets (as xml) the "state" element
     */
    public void xsetState(org.apache.xmlbeans.XmlString state)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STATE$20, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(STATE$20);
            }
            target.set(state);
        }
    }
    
    /**
     * Nils the "state" element
     */
    public void setNilState()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STATE$20, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(STATE$20);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "state" element
     */
    public void unsetState()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(STATE$20, 0);
        }
    }
    
    /**
     * Gets the "temporaryAddress" element
     */
    public boolean getTemporaryAddress()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TEMPORARYADDRESS$22, 0);
            if (target == null)
            {
                return false;
            }
            return target.getBooleanValue();
        }
    }
    
    /**
     * Gets (as xml) the "temporaryAddress" element
     */
    public org.apache.xmlbeans.XmlBoolean xgetTemporaryAddress()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(TEMPORARYADDRESS$22, 0);
            return target;
        }
    }
    
    /**
     * True if has "temporaryAddress" element
     */
    public boolean isSetTemporaryAddress()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(TEMPORARYADDRESS$22) != 0;
        }
    }
    
    /**
     * Sets the "temporaryAddress" element
     */
    public void setTemporaryAddress(boolean temporaryAddress)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TEMPORARYADDRESS$22, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(TEMPORARYADDRESS$22);
            }
            target.setBooleanValue(temporaryAddress);
        }
    }
    
    /**
     * Sets (as xml) the "temporaryAddress" element
     */
    public void xsetTemporaryAddress(org.apache.xmlbeans.XmlBoolean temporaryAddress)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(TEMPORARYADDRESS$22, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlBoolean)get_store().add_element_user(TEMPORARYADDRESS$22);
            }
            target.set(temporaryAddress);
        }
    }
    
    /**
     * Unsets the "temporaryAddress" element
     */
    public void unsetTemporaryAddress()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(TEMPORARYADDRESS$22, 0);
        }
    }
    
    /**
     * Gets the "workPhone" element
     */
    public java.lang.String getWorkPhone()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(WORKPHONE$24, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "workPhone" element
     */
    public org.apache.xmlbeans.XmlString xgetWorkPhone()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(WORKPHONE$24, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "workPhone" element
     */
    public boolean isNilWorkPhone()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(WORKPHONE$24, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "workPhone" element
     */
    public boolean isSetWorkPhone()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(WORKPHONE$24) != 0;
        }
    }
    
    /**
     * Sets the "workPhone" element
     */
    public void setWorkPhone(java.lang.String workPhone)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(WORKPHONE$24, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(WORKPHONE$24);
            }
            target.setStringValue(workPhone);
        }
    }
    
    /**
     * Sets (as xml) the "workPhone" element
     */
    public void xsetWorkPhone(org.apache.xmlbeans.XmlString workPhone)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(WORKPHONE$24, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(WORKPHONE$24);
            }
            target.set(workPhone);
        }
    }
    
    /**
     * Nils the "workPhone" element
     */
    public void setNilWorkPhone()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(WORKPHONE$24, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(WORKPHONE$24);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "workPhone" element
     */
    public void unsetWorkPhone()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(WORKPHONE$24, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ZIP$26, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ZIP$26, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ZIP$26, 0);
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
            return get_store().count_elements(ZIP$26) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ZIP$26, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ZIP$26);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ZIP$26, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(ZIP$26);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ZIP$26, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(ZIP$26);
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
            get_store().remove_element(ZIP$26, 0);
        }
    }
}
