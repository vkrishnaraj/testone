/*
 * XML Type:  NTTimeFormat
 * Namespace: http://db.tracing.nettracer.bagnet.com/xsd
 * Java type: com.bagnet.nettracer.tracing.db.xsd.NTTimeFormat
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.tracing.db.xsd.impl;
/**
 * An XML NTTimeFormat(@http://db.tracing.nettracer.bagnet.com/xsd).
 *
 * This is a complex type.
 */
public class NTTimeFormatImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.tracing.db.xsd.NTTimeFormat
{
    
    public NTTimeFormatImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName FORMAT$0 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "format");
    private static final javax.xml.namespace.QName TIMEFORMATID$2 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "timeformat_ID");
    
    
    /**
     * Gets the "format" element
     */
    public java.lang.String getFormat()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FORMAT$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "format" element
     */
    public org.apache.xmlbeans.XmlString xgetFormat()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FORMAT$0, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "format" element
     */
    public boolean isNilFormat()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FORMAT$0, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "format" element
     */
    public boolean isSetFormat()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(FORMAT$0) != 0;
        }
    }
    
    /**
     * Sets the "format" element
     */
    public void setFormat(java.lang.String format)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FORMAT$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FORMAT$0);
            }
            target.setStringValue(format);
        }
    }
    
    /**
     * Sets (as xml) the "format" element
     */
    public void xsetFormat(org.apache.xmlbeans.XmlString format)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FORMAT$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(FORMAT$0);
            }
            target.set(format);
        }
    }
    
    /**
     * Nils the "format" element
     */
    public void setNilFormat()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FORMAT$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(FORMAT$0);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "format" element
     */
    public void unsetFormat()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(FORMAT$0, 0);
        }
    }
    
    /**
     * Gets the "timeformat_ID" element
     */
    public int getTimeformatID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TIMEFORMATID$2, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "timeformat_ID" element
     */
    public org.apache.xmlbeans.XmlInt xgetTimeformatID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(TIMEFORMATID$2, 0);
            return target;
        }
    }
    
    /**
     * True if has "timeformat_ID" element
     */
    public boolean isSetTimeformatID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(TIMEFORMATID$2) != 0;
        }
    }
    
    /**
     * Sets the "timeformat_ID" element
     */
    public void setTimeformatID(int timeformatID)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TIMEFORMATID$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(TIMEFORMATID$2);
            }
            target.setIntValue(timeformatID);
        }
    }
    
    /**
     * Sets (as xml) the "timeformat_ID" element
     */
    public void xsetTimeformatID(org.apache.xmlbeans.XmlInt timeformatID)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(TIMEFORMATID$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(TIMEFORMATID$2);
            }
            target.set(timeformatID);
        }
    }
    
    /**
     * Unsets the "timeformat_ID" element
     */
    public void unsetTimeformatID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(TIMEFORMATID$2, 0);
        }
    }
}
