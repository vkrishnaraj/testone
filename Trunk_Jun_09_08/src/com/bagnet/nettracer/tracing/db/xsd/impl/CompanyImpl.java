/*
 * XML Type:  Company
 * Namespace: http://db.tracing.nettracer.bagnet.com/xsd
 * Java type: com.bagnet.nettracer.tracing.db.xsd.Company
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.tracing.db.xsd.impl;
/**
 * An XML Company(@http://db.tracing.nettracer.bagnet.com/xsd).
 *
 * This is a complex type.
 */
public class CompanyImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.tracing.db.xsd.Company
{
    
    public CompanyImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName ADDRESS1$0 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "address1");
    private static final javax.xml.namespace.QName ADDRESS2$2 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "address2");
    private static final javax.xml.namespace.QName CITY$4 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "city");
    private static final javax.xml.namespace.QName COMPANYCODEID$6 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "companyCode_ID");
    private static final javax.xml.namespace.QName COMPANYDESC$8 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "companydesc");
    private static final javax.xml.namespace.QName COUNTRYCODEID$10 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "countrycode_ID");
    private static final javax.xml.namespace.QName EMAILADDRESS$12 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "email_address");
    private static final javax.xml.namespace.QName IRREGULARITYCODES$14 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "irregularity_codes");
    private static final javax.xml.namespace.QName PHONE$16 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "phone");
    private static final javax.xml.namespace.QName STATEID$18 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "state_ID");
    private static final javax.xml.namespace.QName VARIABLE$20 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "variable");
    private static final javax.xml.namespace.QName ZIP$22 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "zip");
    
    
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
     * Gets the "city" element
     */
    public java.lang.String getCity()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CITY$4, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CITY$4, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CITY$4, 0);
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
            return get_store().count_elements(CITY$4) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CITY$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CITY$4);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CITY$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CITY$4);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CITY$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CITY$4);
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
            get_store().remove_element(CITY$4, 0);
        }
    }
    
    /**
     * Gets the "companyCode_ID" element
     */
    public java.lang.String getCompanyCodeID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COMPANYCODEID$6, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "companyCode_ID" element
     */
    public org.apache.xmlbeans.XmlString xgetCompanyCodeID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COMPANYCODEID$6, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "companyCode_ID" element
     */
    public boolean isNilCompanyCodeID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COMPANYCODEID$6, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "companyCode_ID" element
     */
    public boolean isSetCompanyCodeID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(COMPANYCODEID$6) != 0;
        }
    }
    
    /**
     * Sets the "companyCode_ID" element
     */
    public void setCompanyCodeID(java.lang.String companyCodeID)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COMPANYCODEID$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(COMPANYCODEID$6);
            }
            target.setStringValue(companyCodeID);
        }
    }
    
    /**
     * Sets (as xml) the "companyCode_ID" element
     */
    public void xsetCompanyCodeID(org.apache.xmlbeans.XmlString companyCodeID)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COMPANYCODEID$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(COMPANYCODEID$6);
            }
            target.set(companyCodeID);
        }
    }
    
    /**
     * Nils the "companyCode_ID" element
     */
    public void setNilCompanyCodeID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COMPANYCODEID$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(COMPANYCODEID$6);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "companyCode_ID" element
     */
    public void unsetCompanyCodeID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(COMPANYCODEID$6, 0);
        }
    }
    
    /**
     * Gets the "companydesc" element
     */
    public java.lang.String getCompanydesc()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COMPANYDESC$8, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "companydesc" element
     */
    public org.apache.xmlbeans.XmlString xgetCompanydesc()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COMPANYDESC$8, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "companydesc" element
     */
    public boolean isNilCompanydesc()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COMPANYDESC$8, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "companydesc" element
     */
    public boolean isSetCompanydesc()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(COMPANYDESC$8) != 0;
        }
    }
    
    /**
     * Sets the "companydesc" element
     */
    public void setCompanydesc(java.lang.String companydesc)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COMPANYDESC$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(COMPANYDESC$8);
            }
            target.setStringValue(companydesc);
        }
    }
    
    /**
     * Sets (as xml) the "companydesc" element
     */
    public void xsetCompanydesc(org.apache.xmlbeans.XmlString companydesc)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COMPANYDESC$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(COMPANYDESC$8);
            }
            target.set(companydesc);
        }
    }
    
    /**
     * Nils the "companydesc" element
     */
    public void setNilCompanydesc()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COMPANYDESC$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(COMPANYDESC$8);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "companydesc" element
     */
    public void unsetCompanydesc()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(COMPANYDESC$8, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COUNTRYCODEID$10, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COUNTRYCODEID$10, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COUNTRYCODEID$10, 0);
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
            return get_store().count_elements(COUNTRYCODEID$10) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COUNTRYCODEID$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(COUNTRYCODEID$10);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COUNTRYCODEID$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(COUNTRYCODEID$10);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COUNTRYCODEID$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(COUNTRYCODEID$10);
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
            get_store().remove_element(COUNTRYCODEID$10, 0);
        }
    }
    
    /**
     * Gets the "email_address" element
     */
    public java.lang.String getEmailAddress()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EMAILADDRESS$12, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "email_address" element
     */
    public org.apache.xmlbeans.XmlString xgetEmailAddress()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(EMAILADDRESS$12, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "email_address" element
     */
    public boolean isNilEmailAddress()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(EMAILADDRESS$12, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "email_address" element
     */
    public boolean isSetEmailAddress()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(EMAILADDRESS$12) != 0;
        }
    }
    
    /**
     * Sets the "email_address" element
     */
    public void setEmailAddress(java.lang.String emailAddress)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EMAILADDRESS$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(EMAILADDRESS$12);
            }
            target.setStringValue(emailAddress);
        }
    }
    
    /**
     * Sets (as xml) the "email_address" element
     */
    public void xsetEmailAddress(org.apache.xmlbeans.XmlString emailAddress)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(EMAILADDRESS$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(EMAILADDRESS$12);
            }
            target.set(emailAddress);
        }
    }
    
    /**
     * Nils the "email_address" element
     */
    public void setNilEmailAddress()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(EMAILADDRESS$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(EMAILADDRESS$12);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "email_address" element
     */
    public void unsetEmailAddress()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(EMAILADDRESS$12, 0);
        }
    }
    
    /**
     * Gets the "irregularity_codes" element
     */
    public org.apache.xmlbeans.XmlObject getIrregularityCodes()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlObject target = null;
            target = (org.apache.xmlbeans.XmlObject)get_store().find_element_user(IRREGULARITYCODES$14, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Tests for nil "irregularity_codes" element
     */
    public boolean isNilIrregularityCodes()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlObject target = null;
            target = (org.apache.xmlbeans.XmlObject)get_store().find_element_user(IRREGULARITYCODES$14, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "irregularity_codes" element
     */
    public boolean isSetIrregularityCodes()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(IRREGULARITYCODES$14) != 0;
        }
    }
    
    /**
     * Sets the "irregularity_codes" element
     */
    public void setIrregularityCodes(org.apache.xmlbeans.XmlObject irregularityCodes)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlObject target = null;
            target = (org.apache.xmlbeans.XmlObject)get_store().find_element_user(IRREGULARITYCODES$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlObject)get_store().add_element_user(IRREGULARITYCODES$14);
            }
            target.set(irregularityCodes);
        }
    }
    
    /**
     * Appends and returns a new empty "irregularity_codes" element
     */
    public org.apache.xmlbeans.XmlObject addNewIrregularityCodes()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlObject target = null;
            target = (org.apache.xmlbeans.XmlObject)get_store().add_element_user(IRREGULARITYCODES$14);
            return target;
        }
    }
    
    /**
     * Nils the "irregularity_codes" element
     */
    public void setNilIrregularityCodes()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlObject target = null;
            target = (org.apache.xmlbeans.XmlObject)get_store().find_element_user(IRREGULARITYCODES$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlObject)get_store().add_element_user(IRREGULARITYCODES$14);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "irregularity_codes" element
     */
    public void unsetIrregularityCodes()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(IRREGULARITYCODES$14, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PHONE$16, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PHONE$16, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PHONE$16, 0);
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
            return get_store().count_elements(PHONE$16) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PHONE$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PHONE$16);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PHONE$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PHONE$16);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PHONE$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PHONE$16);
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
            get_store().remove_element(PHONE$16, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STATEID$18, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STATEID$18, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STATEID$18, 0);
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
            return get_store().count_elements(STATEID$18) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STATEID$18, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(STATEID$18);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STATEID$18, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(STATEID$18);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STATEID$18, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(STATEID$18);
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
            get_store().remove_element(STATEID$18, 0);
        }
    }
    
    /**
     * Gets the "variable" element
     */
    public com.bagnet.nettracer.tracing.db.xsd.CompanySpecificVariable getVariable()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.tracing.db.xsd.CompanySpecificVariable target = null;
            target = (com.bagnet.nettracer.tracing.db.xsd.CompanySpecificVariable)get_store().find_element_user(VARIABLE$20, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Tests for nil "variable" element
     */
    public boolean isNilVariable()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.tracing.db.xsd.CompanySpecificVariable target = null;
            target = (com.bagnet.nettracer.tracing.db.xsd.CompanySpecificVariable)get_store().find_element_user(VARIABLE$20, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "variable" element
     */
    public boolean isSetVariable()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(VARIABLE$20) != 0;
        }
    }
    
    /**
     * Sets the "variable" element
     */
    public void setVariable(com.bagnet.nettracer.tracing.db.xsd.CompanySpecificVariable variable)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.tracing.db.xsd.CompanySpecificVariable target = null;
            target = (com.bagnet.nettracer.tracing.db.xsd.CompanySpecificVariable)get_store().find_element_user(VARIABLE$20, 0);
            if (target == null)
            {
                target = (com.bagnet.nettracer.tracing.db.xsd.CompanySpecificVariable)get_store().add_element_user(VARIABLE$20);
            }
            target.set(variable);
        }
    }
    
    /**
     * Appends and returns a new empty "variable" element
     */
    public com.bagnet.nettracer.tracing.db.xsd.CompanySpecificVariable addNewVariable()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.tracing.db.xsd.CompanySpecificVariable target = null;
            target = (com.bagnet.nettracer.tracing.db.xsd.CompanySpecificVariable)get_store().add_element_user(VARIABLE$20);
            return target;
        }
    }
    
    /**
     * Nils the "variable" element
     */
    public void setNilVariable()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.tracing.db.xsd.CompanySpecificVariable target = null;
            target = (com.bagnet.nettracer.tracing.db.xsd.CompanySpecificVariable)get_store().find_element_user(VARIABLE$20, 0);
            if (target == null)
            {
                target = (com.bagnet.nettracer.tracing.db.xsd.CompanySpecificVariable)get_store().add_element_user(VARIABLE$20);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "variable" element
     */
    public void unsetVariable()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(VARIABLE$20, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ZIP$22, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ZIP$22, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ZIP$22, 0);
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
            return get_store().count_elements(ZIP$22) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ZIP$22, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ZIP$22);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ZIP$22, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(ZIP$22);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ZIP$22, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(ZIP$22);
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
            get_store().remove_element(ZIP$22, 0);
        }
    }
}
