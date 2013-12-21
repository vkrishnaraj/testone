/*
 * XML Type:  Message
 * Namespace: http://onlineclaims.ws.nettracer.bagnet.com/xsd
 * Java type: com.bagnet.nettracer.ws.onlineclaims.xsd.Message
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.onlineclaims.xsd.impl;
/**
 * An XML Message(@http://onlineclaims.ws.nettracer.bagnet.com/xsd).
 *
 * This is a complex type.
 */
public class MessageImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.onlineclaims.xsd.Message
{
    private static final long serialVersionUID = 1L;
    
    public MessageImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName DATECREATED$0 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "dateCreated");
    private static final javax.xml.namespace.QName DATEREVIEWED$2 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "dateReviewed");
    private static final javax.xml.namespace.QName ID$4 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "id");
    private static final javax.xml.namespace.QName MESSAGE$6 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "message");
    private static final javax.xml.namespace.QName PUBLISH$8 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "publish");
    private static final javax.xml.namespace.QName STATUSID$10 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "statusId");
    private static final javax.xml.namespace.QName USERNAME$12 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "username");
    
    
    /**
     * Gets the "dateCreated" element
     */
    public java.util.Calendar getDateCreated()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATECREATED$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "dateCreated" element
     */
    public org.apache.xmlbeans.XmlDateTime xgetDateCreated()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATECREATED$0, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "dateCreated" element
     */
    public boolean isNilDateCreated()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATECREATED$0, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "dateCreated" element
     */
    public boolean isSetDateCreated()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DATECREATED$0) != 0;
        }
    }
    
    /**
     * Sets the "dateCreated" element
     */
    public void setDateCreated(java.util.Calendar dateCreated)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATECREATED$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DATECREATED$0);
            }
            target.setCalendarValue(dateCreated);
        }
    }
    
    /**
     * Sets (as xml) the "dateCreated" element
     */
    public void xsetDateCreated(org.apache.xmlbeans.XmlDateTime dateCreated)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATECREATED$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(DATECREATED$0);
            }
            target.set(dateCreated);
        }
    }
    
    /**
     * Nils the "dateCreated" element
     */
    public void setNilDateCreated()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATECREATED$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(DATECREATED$0);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "dateCreated" element
     */
    public void unsetDateCreated()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DATECREATED$0, 0);
        }
    }
    
    /**
     * Gets the "dateReviewed" element
     */
    public java.util.Calendar getDateReviewed()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATEREVIEWED$2, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "dateReviewed" element
     */
    public org.apache.xmlbeans.XmlDateTime xgetDateReviewed()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATEREVIEWED$2, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "dateReviewed" element
     */
    public boolean isNilDateReviewed()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATEREVIEWED$2, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "dateReviewed" element
     */
    public boolean isSetDateReviewed()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DATEREVIEWED$2) != 0;
        }
    }
    
    /**
     * Sets the "dateReviewed" element
     */
    public void setDateReviewed(java.util.Calendar dateReviewed)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATEREVIEWED$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DATEREVIEWED$2);
            }
            target.setCalendarValue(dateReviewed);
        }
    }
    
    /**
     * Sets (as xml) the "dateReviewed" element
     */
    public void xsetDateReviewed(org.apache.xmlbeans.XmlDateTime dateReviewed)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATEREVIEWED$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(DATEREVIEWED$2);
            }
            target.set(dateReviewed);
        }
    }
    
    /**
     * Nils the "dateReviewed" element
     */
    public void setNilDateReviewed()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(DATEREVIEWED$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(DATEREVIEWED$2);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "dateReviewed" element
     */
    public void unsetDateReviewed()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DATEREVIEWED$2, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ID$4, 0);
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
            target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(ID$4, 0);
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
            return get_store().count_elements(ID$4) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ID$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ID$4);
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
            target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(ID$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlLong)get_store().add_element_user(ID$4);
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
            get_store().remove_element(ID$4, 0);
        }
    }
    
    /**
     * Gets the "message" element
     */
    public java.lang.String getMessage()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MESSAGE$6, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "message" element
     */
    public org.apache.xmlbeans.XmlString xgetMessage()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MESSAGE$6, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "message" element
     */
    public boolean isNilMessage()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MESSAGE$6, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "message" element
     */
    public boolean isSetMessage()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(MESSAGE$6) != 0;
        }
    }
    
    /**
     * Sets the "message" element
     */
    public void setMessage(java.lang.String message)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MESSAGE$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(MESSAGE$6);
            }
            target.setStringValue(message);
        }
    }
    
    /**
     * Sets (as xml) the "message" element
     */
    public void xsetMessage(org.apache.xmlbeans.XmlString message)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MESSAGE$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(MESSAGE$6);
            }
            target.set(message);
        }
    }
    
    /**
     * Nils the "message" element
     */
    public void setNilMessage()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MESSAGE$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(MESSAGE$6);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "message" element
     */
    public void unsetMessage()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(MESSAGE$6, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PUBLISH$8, 0);
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
            target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(PUBLISH$8, 0);
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
            return get_store().count_elements(PUBLISH$8) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PUBLISH$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PUBLISH$8);
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
            target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(PUBLISH$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlBoolean)get_store().add_element_user(PUBLISH$8);
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
            get_store().remove_element(PUBLISH$8, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STATUSID$10, 0);
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
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(STATUSID$10, 0);
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
            return get_store().count_elements(STATUSID$10) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STATUSID$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(STATUSID$10);
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
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(STATUSID$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(STATUSID$10);
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
            get_store().remove_element(STATUSID$10, 0);
        }
    }
    
    /**
     * Gets the "username" element
     */
    public java.lang.String getUsername()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(USERNAME$12, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "username" element
     */
    public org.apache.xmlbeans.XmlString xgetUsername()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(USERNAME$12, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "username" element
     */
    public boolean isNilUsername()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(USERNAME$12, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "username" element
     */
    public boolean isSetUsername()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(USERNAME$12) != 0;
        }
    }
    
    /**
     * Sets the "username" element
     */
    public void setUsername(java.lang.String username)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(USERNAME$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(USERNAME$12);
            }
            target.setStringValue(username);
        }
    }
    
    /**
     * Sets (as xml) the "username" element
     */
    public void xsetUsername(org.apache.xmlbeans.XmlString username)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(USERNAME$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(USERNAME$12);
            }
            target.set(username);
        }
    }
    
    /**
     * Nils the "username" element
     */
    public void setNilUsername()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(USERNAME$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(USERNAME$12);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "username" element
     */
    public void unsetUsername()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(USERNAME$12, 0);
        }
    }
}
