/*
 * XML Type:  Status
 * Namespace: http://db.tracing.nettracer.bagnet.com/xsd
 * Java type: com.bagnet.nettracer.tracing.db.xsd.Status
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.tracing.db.xsd.impl;
/**
 * An XML Status(@http://db.tracing.nettracer.bagnet.com/xsd).
 *
 * This is a complex type.
 */
public class StatusImpl extends com.bagnet.nettracer.tracing.db.i8n.xsd.impl.LocaleBasedObjectImpl implements com.bagnet.nettracer.tracing.db.xsd.Status
{
    
    public StatusImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName KEY2$0 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "key");
    private static final javax.xml.namespace.QName STATUSID$2 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "status_ID");
    private static final javax.xml.namespace.QName TABLEID$4 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "table_ID");
    
    
    /**
     * Gets the "key" element
     */
    public java.lang.String getKey2()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(KEY2$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "key" element
     */
    public org.apache.xmlbeans.XmlString xgetKey2()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(KEY2$0, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "key" element
     */
    public boolean isNilKey2()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(KEY2$0, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "key" element
     */
    public boolean isSetKey2()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(KEY2$0) != 0;
        }
    }
    
    /**
     * Sets the "key" element
     */
    public void setKey2(java.lang.String key2)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(KEY2$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(KEY2$0);
            }
            target.setStringValue(key2);
        }
    }
    
    /**
     * Sets (as xml) the "key" element
     */
    public void xsetKey2(org.apache.xmlbeans.XmlString key2)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(KEY2$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(KEY2$0);
            }
            target.set(key2);
        }
    }
    
    /**
     * Nils the "key" element
     */
    public void setNilKey2()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(KEY2$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(KEY2$0);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "key" element
     */
    public void unsetKey2()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(KEY2$0, 0);
        }
    }
    
    /**
     * Gets the "status_ID" element
     */
    public int getStatusID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STATUSID$2, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "status_ID" element
     */
    public org.apache.xmlbeans.XmlInt xgetStatusID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(STATUSID$2, 0);
            return target;
        }
    }
    
    /**
     * True if has "status_ID" element
     */
    public boolean isSetStatusID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(STATUSID$2) != 0;
        }
    }
    
    /**
     * Sets the "status_ID" element
     */
    public void setStatusID(int statusID)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STATUSID$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(STATUSID$2);
            }
            target.setIntValue(statusID);
        }
    }
    
    /**
     * Sets (as xml) the "status_ID" element
     */
    public void xsetStatusID(org.apache.xmlbeans.XmlInt statusID)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(STATUSID$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(STATUSID$2);
            }
            target.set(statusID);
        }
    }
    
    /**
     * Unsets the "status_ID" element
     */
    public void unsetStatusID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(STATUSID$2, 0);
        }
    }
    
    /**
     * Gets the "table_ID" element
     */
    public int getTableID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TABLEID$4, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "table_ID" element
     */
    public org.apache.xmlbeans.XmlInt xgetTableID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(TABLEID$4, 0);
            return target;
        }
    }
    
    /**
     * True if has "table_ID" element
     */
    public boolean isSetTableID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(TABLEID$4) != 0;
        }
    }
    
    /**
     * Sets the "table_ID" element
     */
    public void setTableID(int tableID)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TABLEID$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(TABLEID$4);
            }
            target.setIntValue(tableID);
        }
    }
    
    /**
     * Sets (as xml) the "table_ID" element
     */
    public void xsetTableID(org.apache.xmlbeans.XmlInt tableID)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(TABLEID$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(TABLEID$4);
            }
            target.set(tableID);
        }
    }
    
    /**
     * Unsets the "table_ID" element
     */
    public void unsetTableID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(TABLEID$4, 0);
        }
    }
}
