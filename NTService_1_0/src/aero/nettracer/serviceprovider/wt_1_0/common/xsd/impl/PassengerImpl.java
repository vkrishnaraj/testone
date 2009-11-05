/*
 * XML Type:  Passenger
 * Namespace: http://common.wt_1_0.serviceprovider.nettracer.aero/xsd
 * Java type: aero.nettracer.serviceprovider.wt_1_0.common.xsd.Passenger
 *
 * Automatically generated - do not modify.
 */
package aero.nettracer.serviceprovider.wt_1_0.common.xsd.impl;
/**
 * An XML Passenger(@http://common.wt_1_0.serviceprovider.nettracer.aero/xsd).
 *
 * This is a complex type.
 */
public class PassengerImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aero.nettracer.serviceprovider.wt_1_0.common.xsd.Passenger
{
    
    public PassengerImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName ADDRESSES$0 = 
        new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd", "addresses");
    private static final javax.xml.namespace.QName FFAIRLINE$2 = 
        new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd", "ffAirline");
    private static final javax.xml.namespace.QName FFNUMBER$4 = 
        new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd", "ffNumber");
    private static final javax.xml.namespace.QName FFSTATUS$6 = 
        new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd", "ffStatus");
    private static final javax.xml.namespace.QName FIRSTNAME$8 = 
        new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd", "firstname");
    private static final javax.xml.namespace.QName INITIALS$10 = 
        new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd", "initials");
    private static final javax.xml.namespace.QName LASTNAME$12 = 
        new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd", "lastname");
    private static final javax.xml.namespace.QName MIDDLENAME$14 = 
        new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd", "middlename");
    
    
    /**
     * Gets the "addresses" element
     */
    public aero.nettracer.serviceprovider.wt_1_0.common.xsd.Address getAddresses()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Address target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Address)get_store().find_element_user(ADDRESSES$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Tests for nil "addresses" element
     */
    public boolean isNilAddresses()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Address target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Address)get_store().find_element_user(ADDRESSES$0, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "addresses" element
     */
    public boolean isSetAddresses()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(ADDRESSES$0) != 0;
        }
    }
    
    /**
     * Sets the "addresses" element
     */
    public void setAddresses(aero.nettracer.serviceprovider.wt_1_0.common.xsd.Address addresses)
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Address target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Address)get_store().find_element_user(ADDRESSES$0, 0);
            if (target == null)
            {
                target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Address)get_store().add_element_user(ADDRESSES$0);
            }
            target.set(addresses);
        }
    }
    
    /**
     * Appends and returns a new empty "addresses" element
     */
    public aero.nettracer.serviceprovider.wt_1_0.common.xsd.Address addNewAddresses()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Address target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Address)get_store().add_element_user(ADDRESSES$0);
            return target;
        }
    }
    
    /**
     * Nils the "addresses" element
     */
    public void setNilAddresses()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Address target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Address)get_store().find_element_user(ADDRESSES$0, 0);
            if (target == null)
            {
                target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Address)get_store().add_element_user(ADDRESSES$0);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "addresses" element
     */
    public void unsetAddresses()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(ADDRESSES$0, 0);
        }
    }
    
    /**
     * Gets the "ffAirline" element
     */
    public java.lang.String getFfAirline()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FFAIRLINE$2, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "ffAirline" element
     */
    public org.apache.xmlbeans.XmlString xgetFfAirline()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FFAIRLINE$2, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "ffAirline" element
     */
    public boolean isNilFfAirline()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FFAIRLINE$2, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "ffAirline" element
     */
    public boolean isSetFfAirline()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(FFAIRLINE$2) != 0;
        }
    }
    
    /**
     * Sets the "ffAirline" element
     */
    public void setFfAirline(java.lang.String ffAirline)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FFAIRLINE$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FFAIRLINE$2);
            }
            target.setStringValue(ffAirline);
        }
    }
    
    /**
     * Sets (as xml) the "ffAirline" element
     */
    public void xsetFfAirline(org.apache.xmlbeans.XmlString ffAirline)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FFAIRLINE$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(FFAIRLINE$2);
            }
            target.set(ffAirline);
        }
    }
    
    /**
     * Nils the "ffAirline" element
     */
    public void setNilFfAirline()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FFAIRLINE$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(FFAIRLINE$2);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "ffAirline" element
     */
    public void unsetFfAirline()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(FFAIRLINE$2, 0);
        }
    }
    
    /**
     * Gets the "ffNumber" element
     */
    public java.lang.String getFfNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FFNUMBER$4, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "ffNumber" element
     */
    public org.apache.xmlbeans.XmlString xgetFfNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FFNUMBER$4, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "ffNumber" element
     */
    public boolean isNilFfNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FFNUMBER$4, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "ffNumber" element
     */
    public boolean isSetFfNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(FFNUMBER$4) != 0;
        }
    }
    
    /**
     * Sets the "ffNumber" element
     */
    public void setFfNumber(java.lang.String ffNumber)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FFNUMBER$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FFNUMBER$4);
            }
            target.setStringValue(ffNumber);
        }
    }
    
    /**
     * Sets (as xml) the "ffNumber" element
     */
    public void xsetFfNumber(org.apache.xmlbeans.XmlString ffNumber)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FFNUMBER$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(FFNUMBER$4);
            }
            target.set(ffNumber);
        }
    }
    
    /**
     * Nils the "ffNumber" element
     */
    public void setNilFfNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FFNUMBER$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(FFNUMBER$4);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "ffNumber" element
     */
    public void unsetFfNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(FFNUMBER$4, 0);
        }
    }
    
    /**
     * Gets the "ffStatus" element
     */
    public java.lang.String getFfStatus()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FFSTATUS$6, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "ffStatus" element
     */
    public org.apache.xmlbeans.XmlString xgetFfStatus()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FFSTATUS$6, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "ffStatus" element
     */
    public boolean isNilFfStatus()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FFSTATUS$6, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "ffStatus" element
     */
    public boolean isSetFfStatus()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(FFSTATUS$6) != 0;
        }
    }
    
    /**
     * Sets the "ffStatus" element
     */
    public void setFfStatus(java.lang.String ffStatus)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FFSTATUS$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FFSTATUS$6);
            }
            target.setStringValue(ffStatus);
        }
    }
    
    /**
     * Sets (as xml) the "ffStatus" element
     */
    public void xsetFfStatus(org.apache.xmlbeans.XmlString ffStatus)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FFSTATUS$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(FFSTATUS$6);
            }
            target.set(ffStatus);
        }
    }
    
    /**
     * Nils the "ffStatus" element
     */
    public void setNilFfStatus()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FFSTATUS$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(FFSTATUS$6);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "ffStatus" element
     */
    public void unsetFfStatus()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(FFSTATUS$6, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FIRSTNAME$8, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FIRSTNAME$8, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FIRSTNAME$8, 0);
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
            return get_store().count_elements(FIRSTNAME$8) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FIRSTNAME$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FIRSTNAME$8);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FIRSTNAME$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(FIRSTNAME$8);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FIRSTNAME$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(FIRSTNAME$8);
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
            get_store().remove_element(FIRSTNAME$8, 0);
        }
    }
    
    /**
     * Gets the "initials" element
     */
    public java.lang.String getInitials()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(INITIALS$10, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "initials" element
     */
    public org.apache.xmlbeans.XmlString xgetInitials()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(INITIALS$10, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "initials" element
     */
    public boolean isNilInitials()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(INITIALS$10, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "initials" element
     */
    public boolean isSetInitials()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(INITIALS$10) != 0;
        }
    }
    
    /**
     * Sets the "initials" element
     */
    public void setInitials(java.lang.String initials)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(INITIALS$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(INITIALS$10);
            }
            target.setStringValue(initials);
        }
    }
    
    /**
     * Sets (as xml) the "initials" element
     */
    public void xsetInitials(org.apache.xmlbeans.XmlString initials)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(INITIALS$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(INITIALS$10);
            }
            target.set(initials);
        }
    }
    
    /**
     * Nils the "initials" element
     */
    public void setNilInitials()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(INITIALS$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(INITIALS$10);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "initials" element
     */
    public void unsetInitials()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(INITIALS$10, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LASTNAME$12, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(LASTNAME$12, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(LASTNAME$12, 0);
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
            return get_store().count_elements(LASTNAME$12) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LASTNAME$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(LASTNAME$12);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(LASTNAME$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(LASTNAME$12);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(LASTNAME$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(LASTNAME$12);
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
            get_store().remove_element(LASTNAME$12, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MIDDLENAME$14, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MIDDLENAME$14, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MIDDLENAME$14, 0);
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
            return get_store().count_elements(MIDDLENAME$14) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MIDDLENAME$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(MIDDLENAME$14);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MIDDLENAME$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(MIDDLENAME$14);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MIDDLENAME$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(MIDDLENAME$14);
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
            get_store().remove_element(MIDDLENAME$14, 0);
        }
    }
}
