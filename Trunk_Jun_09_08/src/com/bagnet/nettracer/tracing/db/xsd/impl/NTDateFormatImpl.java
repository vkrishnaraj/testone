/*
 * XML Type:  NTDateFormat
 * Namespace: http://db.tracing.nettracer.bagnet.com/xsd
 * Java type: com.bagnet.nettracer.tracing.db.xsd.NTDateFormat
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.tracing.db.xsd.impl;
/**
 * An XML NTDateFormat(@http://db.tracing.nettracer.bagnet.com/xsd).
 *
 * This is a complex type.
 */
public class NTDateFormatImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.tracing.db.xsd.NTDateFormat
{
    
    public NTDateFormatImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName DATEFORMATID$0 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "dateformat_ID");
    private static final javax.xml.namespace.QName FORMAT$2 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "format");
    
    
    /**
     * Gets the "dateformat_ID" element
     */
    public int getDateformatID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATEFORMATID$0, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "dateformat_ID" element
     */
    public org.apache.xmlbeans.XmlInt xgetDateformatID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(DATEFORMATID$0, 0);
            return target;
        }
    }
    
    /**
     * True if has "dateformat_ID" element
     */
    public boolean isSetDateformatID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DATEFORMATID$0) != 0;
        }
    }
    
    /**
     * Sets the "dateformat_ID" element
     */
    public void setDateformatID(int dateformatID)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATEFORMATID$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DATEFORMATID$0);
            }
            target.setIntValue(dateformatID);
        }
    }
    
    /**
     * Sets (as xml) the "dateformat_ID" element
     */
    public void xsetDateformatID(org.apache.xmlbeans.XmlInt dateformatID)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(DATEFORMATID$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(DATEFORMATID$0);
            }
            target.set(dateformatID);
        }
    }
    
    /**
     * Unsets the "dateformat_ID" element
     */
    public void unsetDateformatID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DATEFORMATID$0, 0);
        }
    }
    
    /**
     * Gets the "format" element
     */
    public java.lang.String getFormat()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FORMAT$2, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FORMAT$2, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FORMAT$2, 0);
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
            return get_store().count_elements(FORMAT$2) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FORMAT$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FORMAT$2);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FORMAT$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(FORMAT$2);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FORMAT$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(FORMAT$2);
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
            get_store().remove_element(FORMAT$2, 0);
        }
    }
}
