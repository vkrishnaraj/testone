/*
 * XML Type:  Work_Shift
 * Namespace: http://db.tracing.nettracer.bagnet.com/xsd
 * Java type: com.bagnet.nettracer.tracing.db.xsd.WorkShift
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.tracing.db.xsd.impl;
/**
 * An XML Work_Shift(@http://db.tracing.nettracer.bagnet.com/xsd).
 *
 * This is a complex type.
 */
public class WorkShiftImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.tracing.db.xsd.WorkShift
{
    
    public WorkShiftImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName AGENTS$0 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "agents");
    private static final javax.xml.namespace.QName COMPANY$2 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "company");
    private static final javax.xml.namespace.QName LOCALE$4 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "locale");
    private static final javax.xml.namespace.QName SHIFTCODE$6 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "shift_code");
    private static final javax.xml.namespace.QName SHIFTDESCRIPTION$8 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "shift_description");
    private static final javax.xml.namespace.QName SHIFTID$10 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "shift_id");
    
    
    /**
     * Gets the "agents" element
     */
    public java.util.xsd.Set getAgents()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.xsd.Set target = null;
            target = (java.util.xsd.Set)get_store().find_element_user(AGENTS$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Tests for nil "agents" element
     */
    public boolean isNilAgents()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.xsd.Set target = null;
            target = (java.util.xsd.Set)get_store().find_element_user(AGENTS$0, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "agents" element
     */
    public boolean isSetAgents()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(AGENTS$0) != 0;
        }
    }
    
    /**
     * Sets the "agents" element
     */
    public void setAgents(java.util.xsd.Set agents)
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.xsd.Set target = null;
            target = (java.util.xsd.Set)get_store().find_element_user(AGENTS$0, 0);
            if (target == null)
            {
                target = (java.util.xsd.Set)get_store().add_element_user(AGENTS$0);
            }
            target.set(agents);
        }
    }
    
    /**
     * Appends and returns a new empty "agents" element
     */
    public java.util.xsd.Set addNewAgents()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.xsd.Set target = null;
            target = (java.util.xsd.Set)get_store().add_element_user(AGENTS$0);
            return target;
        }
    }
    
    /**
     * Nils the "agents" element
     */
    public void setNilAgents()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.xsd.Set target = null;
            target = (java.util.xsd.Set)get_store().find_element_user(AGENTS$0, 0);
            if (target == null)
            {
                target = (java.util.xsd.Set)get_store().add_element_user(AGENTS$0);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "agents" element
     */
    public void unsetAgents()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(AGENTS$0, 0);
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
            target = (com.bagnet.nettracer.tracing.db.xsd.Company)get_store().find_element_user(COMPANY$2, 0);
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
            target = (com.bagnet.nettracer.tracing.db.xsd.Company)get_store().find_element_user(COMPANY$2, 0);
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
            return get_store().count_elements(COMPANY$2) != 0;
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
            target = (com.bagnet.nettracer.tracing.db.xsd.Company)get_store().find_element_user(COMPANY$2, 0);
            if (target == null)
            {
                target = (com.bagnet.nettracer.tracing.db.xsd.Company)get_store().add_element_user(COMPANY$2);
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
            target = (com.bagnet.nettracer.tracing.db.xsd.Company)get_store().add_element_user(COMPANY$2);
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
            target = (com.bagnet.nettracer.tracing.db.xsd.Company)get_store().find_element_user(COMPANY$2, 0);
            if (target == null)
            {
                target = (com.bagnet.nettracer.tracing.db.xsd.Company)get_store().add_element_user(COMPANY$2);
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
            get_store().remove_element(COMPANY$2, 0);
        }
    }
    
    /**
     * Gets the "locale" element
     */
    public java.lang.String getLocale()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LOCALE$4, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "locale" element
     */
    public org.apache.xmlbeans.XmlString xgetLocale()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(LOCALE$4, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "locale" element
     */
    public boolean isNilLocale()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(LOCALE$4, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "locale" element
     */
    public boolean isSetLocale()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(LOCALE$4) != 0;
        }
    }
    
    /**
     * Sets the "locale" element
     */
    public void setLocale(java.lang.String locale)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LOCALE$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(LOCALE$4);
            }
            target.setStringValue(locale);
        }
    }
    
    /**
     * Sets (as xml) the "locale" element
     */
    public void xsetLocale(org.apache.xmlbeans.XmlString locale)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(LOCALE$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(LOCALE$4);
            }
            target.set(locale);
        }
    }
    
    /**
     * Nils the "locale" element
     */
    public void setNilLocale()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(LOCALE$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(LOCALE$4);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "locale" element
     */
    public void unsetLocale()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(LOCALE$4, 0);
        }
    }
    
    /**
     * Gets the "shift_code" element
     */
    public java.lang.String getShiftCode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SHIFTCODE$6, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "shift_code" element
     */
    public org.apache.xmlbeans.XmlString xgetShiftCode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SHIFTCODE$6, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "shift_code" element
     */
    public boolean isNilShiftCode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SHIFTCODE$6, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "shift_code" element
     */
    public boolean isSetShiftCode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(SHIFTCODE$6) != 0;
        }
    }
    
    /**
     * Sets the "shift_code" element
     */
    public void setShiftCode(java.lang.String shiftCode)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SHIFTCODE$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(SHIFTCODE$6);
            }
            target.setStringValue(shiftCode);
        }
    }
    
    /**
     * Sets (as xml) the "shift_code" element
     */
    public void xsetShiftCode(org.apache.xmlbeans.XmlString shiftCode)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SHIFTCODE$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(SHIFTCODE$6);
            }
            target.set(shiftCode);
        }
    }
    
    /**
     * Nils the "shift_code" element
     */
    public void setNilShiftCode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SHIFTCODE$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(SHIFTCODE$6);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "shift_code" element
     */
    public void unsetShiftCode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(SHIFTCODE$6, 0);
        }
    }
    
    /**
     * Gets the "shift_description" element
     */
    public java.lang.String getShiftDescription()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SHIFTDESCRIPTION$8, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "shift_description" element
     */
    public org.apache.xmlbeans.XmlString xgetShiftDescription()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SHIFTDESCRIPTION$8, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "shift_description" element
     */
    public boolean isNilShiftDescription()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SHIFTDESCRIPTION$8, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "shift_description" element
     */
    public boolean isSetShiftDescription()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(SHIFTDESCRIPTION$8) != 0;
        }
    }
    
    /**
     * Sets the "shift_description" element
     */
    public void setShiftDescription(java.lang.String shiftDescription)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SHIFTDESCRIPTION$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(SHIFTDESCRIPTION$8);
            }
            target.setStringValue(shiftDescription);
        }
    }
    
    /**
     * Sets (as xml) the "shift_description" element
     */
    public void xsetShiftDescription(org.apache.xmlbeans.XmlString shiftDescription)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SHIFTDESCRIPTION$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(SHIFTDESCRIPTION$8);
            }
            target.set(shiftDescription);
        }
    }
    
    /**
     * Nils the "shift_description" element
     */
    public void setNilShiftDescription()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SHIFTDESCRIPTION$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(SHIFTDESCRIPTION$8);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "shift_description" element
     */
    public void unsetShiftDescription()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(SHIFTDESCRIPTION$8, 0);
        }
    }
    
    /**
     * Gets the "shift_id" element
     */
    public int getShiftId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SHIFTID$10, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "shift_id" element
     */
    public org.apache.xmlbeans.XmlInt xgetShiftId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(SHIFTID$10, 0);
            return target;
        }
    }
    
    /**
     * True if has "shift_id" element
     */
    public boolean isSetShiftId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(SHIFTID$10) != 0;
        }
    }
    
    /**
     * Sets the "shift_id" element
     */
    public void setShiftId(int shiftId)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SHIFTID$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(SHIFTID$10);
            }
            target.setIntValue(shiftId);
        }
    }
    
    /**
     * Sets (as xml) the "shift_id" element
     */
    public void xsetShiftId(org.apache.xmlbeans.XmlInt shiftId)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(SHIFTID$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(SHIFTID$10);
            }
            target.set(shiftId);
        }
    }
    
    /**
     * Unsets the "shift_id" element
     */
    public void unsetShiftId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(SHIFTID$10, 0);
        }
    }
}
