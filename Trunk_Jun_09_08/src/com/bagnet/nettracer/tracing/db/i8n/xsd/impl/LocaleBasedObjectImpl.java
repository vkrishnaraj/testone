/*
 * XML Type:  LocaleBasedObject
 * Namespace: http://i8n.db.tracing.nettracer.bagnet.com/xsd
 * Java type: com.bagnet.nettracer.tracing.db.i8n.xsd.LocaleBasedObject
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.tracing.db.i8n.xsd.impl;
/**
 * An XML LocaleBasedObject(@http://i8n.db.tracing.nettracer.bagnet.com/xsd).
 *
 * This is a complex type.
 */
public class LocaleBasedObjectImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.tracing.db.i8n.xsd.LocaleBasedObject
{
    
    public LocaleBasedObjectImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName DESCRIPTION$0 = 
        new javax.xml.namespace.QName("http://i8n.db.tracing.nettracer.bagnet.com/xsd", "description");
    private static final javax.xml.namespace.QName KEY$2 = 
        new javax.xml.namespace.QName("http://i8n.db.tracing.nettracer.bagnet.com/xsd", "key");
    private static final javax.xml.namespace.QName LOCALE$4 = 
        new javax.xml.namespace.QName("http://i8n.db.tracing.nettracer.bagnet.com/xsd", "locale");
    
    
    /**
     * Gets the "description" element
     */
    public java.lang.String getDescription()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DESCRIPTION$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "description" element
     */
    public org.apache.xmlbeans.XmlString xgetDescription()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DESCRIPTION$0, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "description" element
     */
    public boolean isNilDescription()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DESCRIPTION$0, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "description" element
     */
    public boolean isSetDescription()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DESCRIPTION$0) != 0;
        }
    }
    
    /**
     * Sets the "description" element
     */
    public void setDescription(java.lang.String description)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DESCRIPTION$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DESCRIPTION$0);
            }
            target.setStringValue(description);
        }
    }
    
    /**
     * Sets (as xml) the "description" element
     */
    public void xsetDescription(org.apache.xmlbeans.XmlString description)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DESCRIPTION$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DESCRIPTION$0);
            }
            target.set(description);
        }
    }
    
    /**
     * Nils the "description" element
     */
    public void setNilDescription()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DESCRIPTION$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DESCRIPTION$0);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "description" element
     */
    public void unsetDescription()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DESCRIPTION$0, 0);
        }
    }
    
    /**
     * Gets the "key" element
     */
    public java.lang.String getKey()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(KEY$2, 0);
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
    public org.apache.xmlbeans.XmlString xgetKey()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(KEY$2, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "key" element
     */
    public boolean isNilKey()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(KEY$2, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "key" element
     */
    public boolean isSetKey()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(KEY$2) != 0;
        }
    }
    
    /**
     * Sets the "key" element
     */
    public void setKey(java.lang.String key)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(KEY$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(KEY$2);
            }
            target.setStringValue(key);
        }
    }
    
    /**
     * Sets (as xml) the "key" element
     */
    public void xsetKey(org.apache.xmlbeans.XmlString key)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(KEY$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(KEY$2);
            }
            target.set(key);
        }
    }
    
    /**
     * Nils the "key" element
     */
    public void setNilKey()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(KEY$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(KEY$2);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "key" element
     */
    public void unsetKey()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(KEY$2, 0);
        }
    }
    
    /**
     * Gets the "locale" element
     */
    public com.bagnet.nettracer.tracing.db.xsd.Agent getLocale()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.tracing.db.xsd.Agent target = null;
            target = (com.bagnet.nettracer.tracing.db.xsd.Agent)get_store().find_element_user(LOCALE$4, 0);
            if (target == null)
            {
                return null;
            }
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
            com.bagnet.nettracer.tracing.db.xsd.Agent target = null;
            target = (com.bagnet.nettracer.tracing.db.xsd.Agent)get_store().find_element_user(LOCALE$4, 0);
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
    public void setLocale(com.bagnet.nettracer.tracing.db.xsd.Agent locale)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.tracing.db.xsd.Agent target = null;
            target = (com.bagnet.nettracer.tracing.db.xsd.Agent)get_store().find_element_user(LOCALE$4, 0);
            if (target == null)
            {
                target = (com.bagnet.nettracer.tracing.db.xsd.Agent)get_store().add_element_user(LOCALE$4);
            }
            target.set(locale);
        }
    }
    
    /**
     * Appends and returns a new empty "locale" element
     */
    public com.bagnet.nettracer.tracing.db.xsd.Agent addNewLocale()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.tracing.db.xsd.Agent target = null;
            target = (com.bagnet.nettracer.tracing.db.xsd.Agent)get_store().add_element_user(LOCALE$4);
            return target;
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
            com.bagnet.nettracer.tracing.db.xsd.Agent target = null;
            target = (com.bagnet.nettracer.tracing.db.xsd.Agent)get_store().find_element_user(LOCALE$4, 0);
            if (target == null)
            {
                target = (com.bagnet.nettracer.tracing.db.xsd.Agent)get_store().add_element_user(LOCALE$4);
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
}
