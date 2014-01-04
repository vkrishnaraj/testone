/*
 * XML Type:  File
 * Namespace: http://onlineclaims.ws.nettracer.bagnet.com/xsd
 * Java type: com.bagnet.nettracer.ws.onlineclaims.xsd.File
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.onlineclaims.xsd.impl;
/**
 * An XML File(@http://onlineclaims.ws.nettracer.bagnet.com/xsd).
 *
 * This is a complex type.
 */
public class FileImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.onlineclaims.xsd.File
{
    private static final long serialVersionUID = 1L;
    
    public FileImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName DATEUPLOADED$0 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "dateUploaded");
    private static final javax.xml.namespace.QName DATEVIEWED$2 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "dateViewed");
    private static final javax.xml.namespace.QName FILENAME$4 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "filename");
    private static final javax.xml.namespace.QName ID$6 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "id");
    private static final javax.xml.namespace.QName INTERIM$8 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "interim");
    private static final javax.xml.namespace.QName PATH$10 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "path");
    private static final javax.xml.namespace.QName PUBLISH$12 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "publish");
    private static final javax.xml.namespace.QName STATUSID$14 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "statusId");
    
    
    /**
     * Gets the "dateUploaded" element
     */
    public java.util.Calendar getDateUploaded()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATEUPLOADED$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "dateUploaded" element
     */
    public org.apache.xmlbeans.XmlDateTime xgetDateUploaded()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATEUPLOADED$0, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "dateUploaded" element
     */
    public boolean isNilDateUploaded()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATEUPLOADED$0, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "dateUploaded" element
     */
    public boolean isSetDateUploaded()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DATEUPLOADED$0) != 0;
        }
    }
    
    /**
     * Sets the "dateUploaded" element
     */
    public void setDateUploaded(java.util.Calendar dateUploaded)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATEUPLOADED$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DATEUPLOADED$0);
            }
            target.setCalendarValue(dateUploaded);
        }
    }
    
    /**
     * Sets (as xml) the "dateUploaded" element
     */
    public void xsetDateUploaded(org.apache.xmlbeans.XmlDateTime dateUploaded)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATEUPLOADED$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(DATEUPLOADED$0);
            }
            target.set(dateUploaded);
        }
    }
    
    /**
     * Nils the "dateUploaded" element
     */
    public void setNilDateUploaded()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATEUPLOADED$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(DATEUPLOADED$0);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "dateUploaded" element
     */
    public void unsetDateUploaded()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DATEUPLOADED$0, 0);
        }
    }
    
    /**
     * Gets the "dateViewed" element
     */
    public java.util.Calendar getDateViewed()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATEVIEWED$2, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "dateViewed" element
     */
    public org.apache.xmlbeans.XmlDateTime xgetDateViewed()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATEVIEWED$2, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "dateViewed" element
     */
    public boolean isNilDateViewed()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATEVIEWED$2, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "dateViewed" element
     */
    public boolean isSetDateViewed()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DATEVIEWED$2) != 0;
        }
    }
    
    /**
     * Sets the "dateViewed" element
     */
    public void setDateViewed(java.util.Calendar dateViewed)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATEVIEWED$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DATEVIEWED$2);
            }
            target.setCalendarValue(dateViewed);
        }
    }
    
    /**
     * Sets (as xml) the "dateViewed" element
     */
    public void xsetDateViewed(org.apache.xmlbeans.XmlDateTime dateViewed)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATEVIEWED$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(DATEVIEWED$2);
            }
            target.set(dateViewed);
        }
    }
    
    /**
     * Nils the "dateViewed" element
     */
    public void setNilDateViewed()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATEVIEWED$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(DATEVIEWED$2);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "dateViewed" element
     */
    public void unsetDateViewed()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DATEVIEWED$2, 0);
        }
    }
    
    /**
     * Gets the "filename" element
     */
    public java.lang.String getFilename()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FILENAME$4, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "filename" element
     */
    public org.apache.xmlbeans.XmlString xgetFilename()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FILENAME$4, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "filename" element
     */
    public boolean isNilFilename()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FILENAME$4, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "filename" element
     */
    public boolean isSetFilename()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(FILENAME$4) != 0;
        }
    }
    
    /**
     * Sets the "filename" element
     */
    public void setFilename(java.lang.String filename)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FILENAME$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FILENAME$4);
            }
            target.setStringValue(filename);
        }
    }
    
    /**
     * Sets (as xml) the "filename" element
     */
    public void xsetFilename(org.apache.xmlbeans.XmlString filename)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FILENAME$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(FILENAME$4);
            }
            target.set(filename);
        }
    }
    
    /**
     * Nils the "filename" element
     */
    public void setNilFilename()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FILENAME$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(FILENAME$4);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "filename" element
     */
    public void unsetFilename()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(FILENAME$4, 0);
        }
    }
    
    /**
     * Gets the "id" element
     */
    public long getId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ID$6, 0);
            if (target == null)
            {
                return 0L;
            }
            return target.getLongValue();
        }
    }
    
    /**
     * Gets (as xml) the "id" element
     */
    public org.apache.xmlbeans.XmlLong xgetId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlLong target = null;
            target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(ID$6, 0);
            return target;
        }
    }
    
    /**
     * True if has "id" element
     */
    public boolean isSetId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(ID$6) != 0;
        }
    }
    
    /**
     * Sets the "id" element
     */
    public void setId(long id)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ID$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ID$6);
            }
            target.setLongValue(id);
        }
    }
    
    /**
     * Sets (as xml) the "id" element
     */
    public void xsetId(org.apache.xmlbeans.XmlLong id)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlLong target = null;
            target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(ID$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlLong)get_store().add_element_user(ID$6);
            }
            target.set(id);
        }
    }
    
    /**
     * Unsets the "id" element
     */
    public void unsetId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(ID$6, 0);
        }
    }
    
    /**
     * Gets the "interim" element
     */
    public boolean getInterim()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(INTERIM$8, 0);
            if (target == null)
            {
                return false;
            }
            return target.getBooleanValue();
        }
    }
    
    /**
     * Gets (as xml) the "interim" element
     */
    public org.apache.xmlbeans.XmlBoolean xgetInterim()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(INTERIM$8, 0);
            return target;
        }
    }
    
    /**
     * True if has "interim" element
     */
    public boolean isSetInterim()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(INTERIM$8) != 0;
        }
    }
    
    /**
     * Sets the "interim" element
     */
    public void setInterim(boolean interim)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(INTERIM$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(INTERIM$8);
            }
            target.setBooleanValue(interim);
        }
    }
    
    /**
     * Sets (as xml) the "interim" element
     */
    public void xsetInterim(org.apache.xmlbeans.XmlBoolean interim)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(INTERIM$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlBoolean)get_store().add_element_user(INTERIM$8);
            }
            target.set(interim);
        }
    }
    
    /**
     * Unsets the "interim" element
     */
    public void unsetInterim()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(INTERIM$8, 0);
        }
    }
    
    /**
     * Gets the "path" element
     */
    public java.lang.String getPath()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PATH$10, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "path" element
     */
    public org.apache.xmlbeans.XmlString xgetPath()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PATH$10, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "path" element
     */
    public boolean isNilPath()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PATH$10, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "path" element
     */
    public boolean isSetPath()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(PATH$10) != 0;
        }
    }
    
    /**
     * Sets the "path" element
     */
    public void setPath(java.lang.String path)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PATH$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PATH$10);
            }
            target.setStringValue(path);
        }
    }
    
    /**
     * Sets (as xml) the "path" element
     */
    public void xsetPath(org.apache.xmlbeans.XmlString path)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PATH$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PATH$10);
            }
            target.set(path);
        }
    }
    
    /**
     * Nils the "path" element
     */
    public void setNilPath()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PATH$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PATH$10);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "path" element
     */
    public void unsetPath()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(PATH$10, 0);
        }
    }
    
    /**
     * Gets the "publish" element
     */
    public boolean getPublish()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PUBLISH$12, 0);
            if (target == null)
            {
                return false;
            }
            return target.getBooleanValue();
        }
    }
    
    /**
     * Gets (as xml) the "publish" element
     */
    public org.apache.xmlbeans.XmlBoolean xgetPublish()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(PUBLISH$12, 0);
            return target;
        }
    }
    
    /**
     * True if has "publish" element
     */
    public boolean isSetPublish()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(PUBLISH$12) != 0;
        }
    }
    
    /**
     * Sets the "publish" element
     */
    public void setPublish(boolean publish)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PUBLISH$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PUBLISH$12);
            }
            target.setBooleanValue(publish);
        }
    }
    
    /**
     * Sets (as xml) the "publish" element
     */
    public void xsetPublish(org.apache.xmlbeans.XmlBoolean publish)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(PUBLISH$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlBoolean)get_store().add_element_user(PUBLISH$12);
            }
            target.set(publish);
        }
    }
    
    /**
     * Unsets the "publish" element
     */
    public void unsetPublish()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(PUBLISH$12, 0);
        }
    }
    
    /**
     * Gets the "statusId" element
     */
    public int getStatusId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STATUSID$14, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "statusId" element
     */
    public org.apache.xmlbeans.XmlInt xgetStatusId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(STATUSID$14, 0);
            return target;
        }
    }
    
    /**
     * True if has "statusId" element
     */
    public boolean isSetStatusId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(STATUSID$14) != 0;
        }
    }
    
    /**
     * Sets the "statusId" element
     */
    public void setStatusId(int statusId)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STATUSID$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(STATUSID$14);
            }
            target.setIntValue(statusId);
        }
    }
    
    /**
     * Sets (as xml) the "statusId" element
     */
    public void xsetStatusId(org.apache.xmlbeans.XmlInt statusId)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(STATUSID$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(STATUSID$14);
            }
            target.set(statusId);
        }
    }
    
    /**
     * Unsets the "statusId" element
     */
    public void unsetStatusId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(STATUSID$14, 0);
        }
    }
}
