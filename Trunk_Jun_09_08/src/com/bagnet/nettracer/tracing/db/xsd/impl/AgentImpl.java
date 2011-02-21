/*
 * XML Type:  Agent
 * Namespace: http://db.tracing.nettracer.bagnet.com/xsd
 * Java type: com.bagnet.nettracer.tracing.db.xsd.Agent
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.tracing.db.xsd.impl;
/**
 * An XML Agent(@http://db.tracing.nettracer.bagnet.com/xsd).
 *
 * This is a complex type.
 */
public class AgentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.tracing.db.xsd.Agent
{
    
    public AgentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName ACCOUNTLOCKED$0 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "account_locked");
    private static final javax.xml.namespace.QName ACTIVE$2 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "active");
    private static final javax.xml.namespace.QName AGENTID$4 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "agent_ID");
    private static final javax.xml.namespace.QName COMPANYCODEID$6 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "companycode_ID");
    private static final javax.xml.namespace.QName CURRENTLOCALE$8 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "currentlocale");
    private static final javax.xml.namespace.QName CURRENTTIMEZONE$10 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "currenttimezone");
    private static final javax.xml.namespace.QName DATEFORMAT$12 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "dateformat");
    private static final javax.xml.namespace.QName DEFAULTCURRENCY$14 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "defaultcurrency");
    private static final javax.xml.namespace.QName DEFAULTLOCALE$16 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "defaultlocale");
    private static final javax.xml.namespace.QName DEFAULTTIMEZONE$18 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "defaulttimezone");
    private static final javax.xml.namespace.QName FAILEDLOGINS$20 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "failed_logins");
    private static final javax.xml.namespace.QName FIRSTNAME$22 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "firstname");
    private static final javax.xml.namespace.QName GROUP$24 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "group");
    private static final javax.xml.namespace.QName INITIAL$26 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "initial");
    private static final javax.xml.namespace.QName ISONLINE$28 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "is_online");
    private static final javax.xml.namespace.QName ISWTUSER$30 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "is_wt_user");
    private static final javax.xml.namespace.QName LASTLOGGEDON$32 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "last_logged_on");
    private static final javax.xml.namespace.QName LASTPASSRESETDATE$34 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "last_pass_reset_date");
    private static final javax.xml.namespace.QName LASTNAME$36 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "lastname");
    private static final javax.xml.namespace.QName MAXWSSESSIONS$38 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "max_ws_sessions");
    private static final javax.xml.namespace.QName MNAME$40 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "mname");
    private static final javax.xml.namespace.QName PASSWORD$42 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "password");
    private static final javax.xml.namespace.QName RESETPASSWORD$44 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "reset_password");
    private static final javax.xml.namespace.QName SHIFT$46 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "shift");
    private static final javax.xml.namespace.QName STATION$48 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "station");
    private static final javax.xml.namespace.QName TIMEFORMAT$50 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "timeformat");
    private static final javax.xml.namespace.QName TIMEOUT$52 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "timeout");
    private static final javax.xml.namespace.QName USERGROUPID$54 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "usergroup_id");
    private static final javax.xml.namespace.QName USERNAME$56 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "username");
    private static final javax.xml.namespace.QName WEBENABLED$58 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "web_enabled");
    private static final javax.xml.namespace.QName WSENABLED$60 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "ws_enabled");
    
    
    /**
     * Gets the "account_locked" element
     */
    public boolean getAccountLocked()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ACCOUNTLOCKED$0, 0);
            if (target == null)
            {
                return false;
            }
            return target.getBooleanValue();
        }
    }
    
    /**
     * Gets (as xml) the "account_locked" element
     */
    public org.apache.xmlbeans.XmlBoolean xgetAccountLocked()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(ACCOUNTLOCKED$0, 0);
            return target;
        }
    }
    
    /**
     * True if has "account_locked" element
     */
    public boolean isSetAccountLocked()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(ACCOUNTLOCKED$0) != 0;
        }
    }
    
    /**
     * Sets the "account_locked" element
     */
    public void setAccountLocked(boolean accountLocked)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ACCOUNTLOCKED$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ACCOUNTLOCKED$0);
            }
            target.setBooleanValue(accountLocked);
        }
    }
    
    /**
     * Sets (as xml) the "account_locked" element
     */
    public void xsetAccountLocked(org.apache.xmlbeans.XmlBoolean accountLocked)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(ACCOUNTLOCKED$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlBoolean)get_store().add_element_user(ACCOUNTLOCKED$0);
            }
            target.set(accountLocked);
        }
    }
    
    /**
     * Unsets the "account_locked" element
     */
    public void unsetAccountLocked()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(ACCOUNTLOCKED$0, 0);
        }
    }
    
    /**
     * Gets the "active" element
     */
    public boolean getActive()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ACTIVE$2, 0);
            if (target == null)
            {
                return false;
            }
            return target.getBooleanValue();
        }
    }
    
    /**
     * Gets (as xml) the "active" element
     */
    public org.apache.xmlbeans.XmlBoolean xgetActive()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(ACTIVE$2, 0);
            return target;
        }
    }
    
    /**
     * True if has "active" element
     */
    public boolean isSetActive()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(ACTIVE$2) != 0;
        }
    }
    
    /**
     * Sets the "active" element
     */
    public void setActive(boolean active)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ACTIVE$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ACTIVE$2);
            }
            target.setBooleanValue(active);
        }
    }
    
    /**
     * Sets (as xml) the "active" element
     */
    public void xsetActive(org.apache.xmlbeans.XmlBoolean active)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(ACTIVE$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlBoolean)get_store().add_element_user(ACTIVE$2);
            }
            target.set(active);
        }
    }
    
    /**
     * Unsets the "active" element
     */
    public void unsetActive()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(ACTIVE$2, 0);
        }
    }
    
    /**
     * Gets the "agent_ID" element
     */
    public int getAgentID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(AGENTID$4, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "agent_ID" element
     */
    public org.apache.xmlbeans.XmlInt xgetAgentID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(AGENTID$4, 0);
            return target;
        }
    }
    
    /**
     * True if has "agent_ID" element
     */
    public boolean isSetAgentID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(AGENTID$4) != 0;
        }
    }
    
    /**
     * Sets the "agent_ID" element
     */
    public void setAgentID(int agentID)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(AGENTID$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(AGENTID$4);
            }
            target.setIntValue(agentID);
        }
    }
    
    /**
     * Sets (as xml) the "agent_ID" element
     */
    public void xsetAgentID(org.apache.xmlbeans.XmlInt agentID)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(AGENTID$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(AGENTID$4);
            }
            target.set(agentID);
        }
    }
    
    /**
     * Unsets the "agent_ID" element
     */
    public void unsetAgentID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(AGENTID$4, 0);
        }
    }
    
    /**
     * Gets the "companycode_ID" element
     */
    public java.lang.String getCompanycodeID()
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
     * Gets (as xml) the "companycode_ID" element
     */
    public org.apache.xmlbeans.XmlString xgetCompanycodeID()
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
     * Tests for nil "companycode_ID" element
     */
    public boolean isNilCompanycodeID()
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
     * True if has "companycode_ID" element
     */
    public boolean isSetCompanycodeID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(COMPANYCODEID$6) != 0;
        }
    }
    
    /**
     * Sets the "companycode_ID" element
     */
    public void setCompanycodeID(java.lang.String companycodeID)
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
            target.setStringValue(companycodeID);
        }
    }
    
    /**
     * Sets (as xml) the "companycode_ID" element
     */
    public void xsetCompanycodeID(org.apache.xmlbeans.XmlString companycodeID)
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
            target.set(companycodeID);
        }
    }
    
    /**
     * Nils the "companycode_ID" element
     */
    public void setNilCompanycodeID()
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
     * Unsets the "companycode_ID" element
     */
    public void unsetCompanycodeID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(COMPANYCODEID$6, 0);
        }
    }
    
    /**
     * Gets the "currentlocale" element
     */
    public java.lang.String getCurrentlocale()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CURRENTLOCALE$8, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "currentlocale" element
     */
    public org.apache.xmlbeans.XmlString xgetCurrentlocale()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CURRENTLOCALE$8, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "currentlocale" element
     */
    public boolean isNilCurrentlocale()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CURRENTLOCALE$8, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "currentlocale" element
     */
    public boolean isSetCurrentlocale()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CURRENTLOCALE$8) != 0;
        }
    }
    
    /**
     * Sets the "currentlocale" element
     */
    public void setCurrentlocale(java.lang.String currentlocale)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CURRENTLOCALE$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CURRENTLOCALE$8);
            }
            target.setStringValue(currentlocale);
        }
    }
    
    /**
     * Sets (as xml) the "currentlocale" element
     */
    public void xsetCurrentlocale(org.apache.xmlbeans.XmlString currentlocale)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CURRENTLOCALE$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CURRENTLOCALE$8);
            }
            target.set(currentlocale);
        }
    }
    
    /**
     * Nils the "currentlocale" element
     */
    public void setNilCurrentlocale()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CURRENTLOCALE$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CURRENTLOCALE$8);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "currentlocale" element
     */
    public void unsetCurrentlocale()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CURRENTLOCALE$8, 0);
        }
    }
    
    /**
     * Gets the "currenttimezone" element
     */
    public java.lang.String getCurrenttimezone()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CURRENTTIMEZONE$10, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "currenttimezone" element
     */
    public org.apache.xmlbeans.XmlString xgetCurrenttimezone()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CURRENTTIMEZONE$10, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "currenttimezone" element
     */
    public boolean isNilCurrenttimezone()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CURRENTTIMEZONE$10, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "currenttimezone" element
     */
    public boolean isSetCurrenttimezone()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CURRENTTIMEZONE$10) != 0;
        }
    }
    
    /**
     * Sets the "currenttimezone" element
     */
    public void setCurrenttimezone(java.lang.String currenttimezone)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CURRENTTIMEZONE$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CURRENTTIMEZONE$10);
            }
            target.setStringValue(currenttimezone);
        }
    }
    
    /**
     * Sets (as xml) the "currenttimezone" element
     */
    public void xsetCurrenttimezone(org.apache.xmlbeans.XmlString currenttimezone)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CURRENTTIMEZONE$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CURRENTTIMEZONE$10);
            }
            target.set(currenttimezone);
        }
    }
    
    /**
     * Nils the "currenttimezone" element
     */
    public void setNilCurrenttimezone()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CURRENTTIMEZONE$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CURRENTTIMEZONE$10);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "currenttimezone" element
     */
    public void unsetCurrenttimezone()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CURRENTTIMEZONE$10, 0);
        }
    }
    
    /**
     * Gets the "dateformat" element
     */
    public com.bagnet.nettracer.tracing.db.xsd.NTDateFormat getDateformat()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.tracing.db.xsd.NTDateFormat target = null;
            target = (com.bagnet.nettracer.tracing.db.xsd.NTDateFormat)get_store().find_element_user(DATEFORMAT$12, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Tests for nil "dateformat" element
     */
    public boolean isNilDateformat()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.tracing.db.xsd.NTDateFormat target = null;
            target = (com.bagnet.nettracer.tracing.db.xsd.NTDateFormat)get_store().find_element_user(DATEFORMAT$12, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "dateformat" element
     */
    public boolean isSetDateformat()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DATEFORMAT$12) != 0;
        }
    }
    
    /**
     * Sets the "dateformat" element
     */
    public void setDateformat(com.bagnet.nettracer.tracing.db.xsd.NTDateFormat dateformat)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.tracing.db.xsd.NTDateFormat target = null;
            target = (com.bagnet.nettracer.tracing.db.xsd.NTDateFormat)get_store().find_element_user(DATEFORMAT$12, 0);
            if (target == null)
            {
                target = (com.bagnet.nettracer.tracing.db.xsd.NTDateFormat)get_store().add_element_user(DATEFORMAT$12);
            }
            target.set(dateformat);
        }
    }
    
    /**
     * Appends and returns a new empty "dateformat" element
     */
    public com.bagnet.nettracer.tracing.db.xsd.NTDateFormat addNewDateformat()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.tracing.db.xsd.NTDateFormat target = null;
            target = (com.bagnet.nettracer.tracing.db.xsd.NTDateFormat)get_store().add_element_user(DATEFORMAT$12);
            return target;
        }
    }
    
    /**
     * Nils the "dateformat" element
     */
    public void setNilDateformat()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.tracing.db.xsd.NTDateFormat target = null;
            target = (com.bagnet.nettracer.tracing.db.xsd.NTDateFormat)get_store().find_element_user(DATEFORMAT$12, 0);
            if (target == null)
            {
                target = (com.bagnet.nettracer.tracing.db.xsd.NTDateFormat)get_store().add_element_user(DATEFORMAT$12);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "dateformat" element
     */
    public void unsetDateformat()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DATEFORMAT$12, 0);
        }
    }
    
    /**
     * Gets the "defaultcurrency" element
     */
    public java.lang.String getDefaultcurrency()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DEFAULTCURRENCY$14, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "defaultcurrency" element
     */
    public org.apache.xmlbeans.XmlString xgetDefaultcurrency()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DEFAULTCURRENCY$14, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "defaultcurrency" element
     */
    public boolean isNilDefaultcurrency()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DEFAULTCURRENCY$14, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "defaultcurrency" element
     */
    public boolean isSetDefaultcurrency()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DEFAULTCURRENCY$14) != 0;
        }
    }
    
    /**
     * Sets the "defaultcurrency" element
     */
    public void setDefaultcurrency(java.lang.String defaultcurrency)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DEFAULTCURRENCY$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DEFAULTCURRENCY$14);
            }
            target.setStringValue(defaultcurrency);
        }
    }
    
    /**
     * Sets (as xml) the "defaultcurrency" element
     */
    public void xsetDefaultcurrency(org.apache.xmlbeans.XmlString defaultcurrency)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DEFAULTCURRENCY$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DEFAULTCURRENCY$14);
            }
            target.set(defaultcurrency);
        }
    }
    
    /**
     * Nils the "defaultcurrency" element
     */
    public void setNilDefaultcurrency()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DEFAULTCURRENCY$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DEFAULTCURRENCY$14);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "defaultcurrency" element
     */
    public void unsetDefaultcurrency()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DEFAULTCURRENCY$14, 0);
        }
    }
    
    /**
     * Gets the "defaultlocale" element
     */
    public java.lang.String getDefaultlocale()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DEFAULTLOCALE$16, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "defaultlocale" element
     */
    public org.apache.xmlbeans.XmlString xgetDefaultlocale()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DEFAULTLOCALE$16, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "defaultlocale" element
     */
    public boolean isNilDefaultlocale()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DEFAULTLOCALE$16, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "defaultlocale" element
     */
    public boolean isSetDefaultlocale()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DEFAULTLOCALE$16) != 0;
        }
    }
    
    /**
     * Sets the "defaultlocale" element
     */
    public void setDefaultlocale(java.lang.String defaultlocale)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DEFAULTLOCALE$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DEFAULTLOCALE$16);
            }
            target.setStringValue(defaultlocale);
        }
    }
    
    /**
     * Sets (as xml) the "defaultlocale" element
     */
    public void xsetDefaultlocale(org.apache.xmlbeans.XmlString defaultlocale)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DEFAULTLOCALE$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DEFAULTLOCALE$16);
            }
            target.set(defaultlocale);
        }
    }
    
    /**
     * Nils the "defaultlocale" element
     */
    public void setNilDefaultlocale()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DEFAULTLOCALE$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DEFAULTLOCALE$16);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "defaultlocale" element
     */
    public void unsetDefaultlocale()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DEFAULTLOCALE$16, 0);
        }
    }
    
    /**
     * Gets the "defaulttimezone" element
     */
    public java.lang.String getDefaulttimezone()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DEFAULTTIMEZONE$18, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "defaulttimezone" element
     */
    public org.apache.xmlbeans.XmlString xgetDefaulttimezone()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DEFAULTTIMEZONE$18, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "defaulttimezone" element
     */
    public boolean isNilDefaulttimezone()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DEFAULTTIMEZONE$18, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "defaulttimezone" element
     */
    public boolean isSetDefaulttimezone()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DEFAULTTIMEZONE$18) != 0;
        }
    }
    
    /**
     * Sets the "defaulttimezone" element
     */
    public void setDefaulttimezone(java.lang.String defaulttimezone)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DEFAULTTIMEZONE$18, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DEFAULTTIMEZONE$18);
            }
            target.setStringValue(defaulttimezone);
        }
    }
    
    /**
     * Sets (as xml) the "defaulttimezone" element
     */
    public void xsetDefaulttimezone(org.apache.xmlbeans.XmlString defaulttimezone)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DEFAULTTIMEZONE$18, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DEFAULTTIMEZONE$18);
            }
            target.set(defaulttimezone);
        }
    }
    
    /**
     * Nils the "defaulttimezone" element
     */
    public void setNilDefaulttimezone()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DEFAULTTIMEZONE$18, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DEFAULTTIMEZONE$18);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "defaulttimezone" element
     */
    public void unsetDefaulttimezone()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DEFAULTTIMEZONE$18, 0);
        }
    }
    
    /**
     * Gets the "failed_logins" element
     */
    public int getFailedLogins()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FAILEDLOGINS$20, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "failed_logins" element
     */
    public org.apache.xmlbeans.XmlInt xgetFailedLogins()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(FAILEDLOGINS$20, 0);
            return target;
        }
    }
    
    /**
     * True if has "failed_logins" element
     */
    public boolean isSetFailedLogins()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(FAILEDLOGINS$20) != 0;
        }
    }
    
    /**
     * Sets the "failed_logins" element
     */
    public void setFailedLogins(int failedLogins)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FAILEDLOGINS$20, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FAILEDLOGINS$20);
            }
            target.setIntValue(failedLogins);
        }
    }
    
    /**
     * Sets (as xml) the "failed_logins" element
     */
    public void xsetFailedLogins(org.apache.xmlbeans.XmlInt failedLogins)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(FAILEDLOGINS$20, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(FAILEDLOGINS$20);
            }
            target.set(failedLogins);
        }
    }
    
    /**
     * Unsets the "failed_logins" element
     */
    public void unsetFailedLogins()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(FAILEDLOGINS$20, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FIRSTNAME$22, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FIRSTNAME$22, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FIRSTNAME$22, 0);
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
            return get_store().count_elements(FIRSTNAME$22) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FIRSTNAME$22, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FIRSTNAME$22);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FIRSTNAME$22, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(FIRSTNAME$22);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FIRSTNAME$22, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(FIRSTNAME$22);
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
            get_store().remove_element(FIRSTNAME$22, 0);
        }
    }
    
    /**
     * Gets the "group" element
     */
    public com.bagnet.nettracer.tracing.db.xsd.UserGroup getGroup()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.tracing.db.xsd.UserGroup target = null;
            target = (com.bagnet.nettracer.tracing.db.xsd.UserGroup)get_store().find_element_user(GROUP$24, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Tests for nil "group" element
     */
    public boolean isNilGroup()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.tracing.db.xsd.UserGroup target = null;
            target = (com.bagnet.nettracer.tracing.db.xsd.UserGroup)get_store().find_element_user(GROUP$24, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "group" element
     */
    public boolean isSetGroup()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(GROUP$24) != 0;
        }
    }
    
    /**
     * Sets the "group" element
     */
    public void setGroup(com.bagnet.nettracer.tracing.db.xsd.UserGroup group)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.tracing.db.xsd.UserGroup target = null;
            target = (com.bagnet.nettracer.tracing.db.xsd.UserGroup)get_store().find_element_user(GROUP$24, 0);
            if (target == null)
            {
                target = (com.bagnet.nettracer.tracing.db.xsd.UserGroup)get_store().add_element_user(GROUP$24);
            }
            target.set(group);
        }
    }
    
    /**
     * Appends and returns a new empty "group" element
     */
    public com.bagnet.nettracer.tracing.db.xsd.UserGroup addNewGroup()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.tracing.db.xsd.UserGroup target = null;
            target = (com.bagnet.nettracer.tracing.db.xsd.UserGroup)get_store().add_element_user(GROUP$24);
            return target;
        }
    }
    
    /**
     * Nils the "group" element
     */
    public void setNilGroup()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.tracing.db.xsd.UserGroup target = null;
            target = (com.bagnet.nettracer.tracing.db.xsd.UserGroup)get_store().find_element_user(GROUP$24, 0);
            if (target == null)
            {
                target = (com.bagnet.nettracer.tracing.db.xsd.UserGroup)get_store().add_element_user(GROUP$24);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "group" element
     */
    public void unsetGroup()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(GROUP$24, 0);
        }
    }
    
    /**
     * Gets the "initial" element
     */
    public java.lang.String getInitial()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(INITIAL$26, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "initial" element
     */
    public org.apache.xmlbeans.XmlString xgetInitial()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(INITIAL$26, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "initial" element
     */
    public boolean isNilInitial()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(INITIAL$26, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "initial" element
     */
    public boolean isSetInitial()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(INITIAL$26) != 0;
        }
    }
    
    /**
     * Sets the "initial" element
     */
    public void setInitial(java.lang.String initial)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(INITIAL$26, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(INITIAL$26);
            }
            target.setStringValue(initial);
        }
    }
    
    /**
     * Sets (as xml) the "initial" element
     */
    public void xsetInitial(org.apache.xmlbeans.XmlString initial)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(INITIAL$26, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(INITIAL$26);
            }
            target.set(initial);
        }
    }
    
    /**
     * Nils the "initial" element
     */
    public void setNilInitial()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(INITIAL$26, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(INITIAL$26);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "initial" element
     */
    public void unsetInitial()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(INITIAL$26, 0);
        }
    }
    
    /**
     * Gets the "is_online" element
     */
    public int getIsOnline()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ISONLINE$28, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "is_online" element
     */
    public org.apache.xmlbeans.XmlInt xgetIsOnline()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(ISONLINE$28, 0);
            return target;
        }
    }
    
    /**
     * True if has "is_online" element
     */
    public boolean isSetIsOnline()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(ISONLINE$28) != 0;
        }
    }
    
    /**
     * Sets the "is_online" element
     */
    public void setIsOnline(int isOnline)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ISONLINE$28, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ISONLINE$28);
            }
            target.setIntValue(isOnline);
        }
    }
    
    /**
     * Sets (as xml) the "is_online" element
     */
    public void xsetIsOnline(org.apache.xmlbeans.XmlInt isOnline)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(ISONLINE$28, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(ISONLINE$28);
            }
            target.set(isOnline);
        }
    }
    
    /**
     * Unsets the "is_online" element
     */
    public void unsetIsOnline()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(ISONLINE$28, 0);
        }
    }
    
    /**
     * Gets the "is_wt_user" element
     */
    public int getIsWtUser()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ISWTUSER$30, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "is_wt_user" element
     */
    public org.apache.xmlbeans.XmlInt xgetIsWtUser()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(ISWTUSER$30, 0);
            return target;
        }
    }
    
    /**
     * True if has "is_wt_user" element
     */
    public boolean isSetIsWtUser()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(ISWTUSER$30) != 0;
        }
    }
    
    /**
     * Sets the "is_wt_user" element
     */
    public void setIsWtUser(int isWtUser)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ISWTUSER$30, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ISWTUSER$30);
            }
            target.setIntValue(isWtUser);
        }
    }
    
    /**
     * Sets (as xml) the "is_wt_user" element
     */
    public void xsetIsWtUser(org.apache.xmlbeans.XmlInt isWtUser)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(ISWTUSER$30, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(ISWTUSER$30);
            }
            target.set(isWtUser);
        }
    }
    
    /**
     * Unsets the "is_wt_user" element
     */
    public void unsetIsWtUser()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(ISWTUSER$30, 0);
        }
    }
    
    /**
     * Gets the "last_logged_on" element
     */
    public java.util.Calendar getLastLoggedOn()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LASTLOGGEDON$32, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "last_logged_on" element
     */
    public org.apache.xmlbeans.XmlDate xgetLastLoggedOn()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDate target = null;
            target = (org.apache.xmlbeans.XmlDate)get_store().find_element_user(LASTLOGGEDON$32, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "last_logged_on" element
     */
    public boolean isNilLastLoggedOn()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDate target = null;
            target = (org.apache.xmlbeans.XmlDate)get_store().find_element_user(LASTLOGGEDON$32, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "last_logged_on" element
     */
    public boolean isSetLastLoggedOn()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(LASTLOGGEDON$32) != 0;
        }
    }
    
    /**
     * Sets the "last_logged_on" element
     */
    public void setLastLoggedOn(java.util.Calendar lastLoggedOn)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LASTLOGGEDON$32, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(LASTLOGGEDON$32);
            }
            target.setCalendarValue(lastLoggedOn);
        }
    }
    
    /**
     * Sets (as xml) the "last_logged_on" element
     */
    public void xsetLastLoggedOn(org.apache.xmlbeans.XmlDate lastLoggedOn)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDate target = null;
            target = (org.apache.xmlbeans.XmlDate)get_store().find_element_user(LASTLOGGEDON$32, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDate)get_store().add_element_user(LASTLOGGEDON$32);
            }
            target.set(lastLoggedOn);
        }
    }
    
    /**
     * Nils the "last_logged_on" element
     */
    public void setNilLastLoggedOn()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDate target = null;
            target = (org.apache.xmlbeans.XmlDate)get_store().find_element_user(LASTLOGGEDON$32, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDate)get_store().add_element_user(LASTLOGGEDON$32);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "last_logged_on" element
     */
    public void unsetLastLoggedOn()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(LASTLOGGEDON$32, 0);
        }
    }
    
    /**
     * Gets the "last_pass_reset_date" element
     */
    public java.util.Calendar getLastPassResetDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LASTPASSRESETDATE$34, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "last_pass_reset_date" element
     */
    public org.apache.xmlbeans.XmlDate xgetLastPassResetDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDate target = null;
            target = (org.apache.xmlbeans.XmlDate)get_store().find_element_user(LASTPASSRESETDATE$34, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "last_pass_reset_date" element
     */
    public boolean isNilLastPassResetDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDate target = null;
            target = (org.apache.xmlbeans.XmlDate)get_store().find_element_user(LASTPASSRESETDATE$34, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "last_pass_reset_date" element
     */
    public boolean isSetLastPassResetDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(LASTPASSRESETDATE$34) != 0;
        }
    }
    
    /**
     * Sets the "last_pass_reset_date" element
     */
    public void setLastPassResetDate(java.util.Calendar lastPassResetDate)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LASTPASSRESETDATE$34, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(LASTPASSRESETDATE$34);
            }
            target.setCalendarValue(lastPassResetDate);
        }
    }
    
    /**
     * Sets (as xml) the "last_pass_reset_date" element
     */
    public void xsetLastPassResetDate(org.apache.xmlbeans.XmlDate lastPassResetDate)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDate target = null;
            target = (org.apache.xmlbeans.XmlDate)get_store().find_element_user(LASTPASSRESETDATE$34, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDate)get_store().add_element_user(LASTPASSRESETDATE$34);
            }
            target.set(lastPassResetDate);
        }
    }
    
    /**
     * Nils the "last_pass_reset_date" element
     */
    public void setNilLastPassResetDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDate target = null;
            target = (org.apache.xmlbeans.XmlDate)get_store().find_element_user(LASTPASSRESETDATE$34, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDate)get_store().add_element_user(LASTPASSRESETDATE$34);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "last_pass_reset_date" element
     */
    public void unsetLastPassResetDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(LASTPASSRESETDATE$34, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LASTNAME$36, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(LASTNAME$36, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(LASTNAME$36, 0);
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
            return get_store().count_elements(LASTNAME$36) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LASTNAME$36, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(LASTNAME$36);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(LASTNAME$36, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(LASTNAME$36);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(LASTNAME$36, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(LASTNAME$36);
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
            get_store().remove_element(LASTNAME$36, 0);
        }
    }
    
    /**
     * Gets the "max_ws_sessions" element
     */
    public int getMaxWsSessions()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MAXWSSESSIONS$38, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "max_ws_sessions" element
     */
    public org.apache.xmlbeans.XmlInt xgetMaxWsSessions()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(MAXWSSESSIONS$38, 0);
            return target;
        }
    }
    
    /**
     * True if has "max_ws_sessions" element
     */
    public boolean isSetMaxWsSessions()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(MAXWSSESSIONS$38) != 0;
        }
    }
    
    /**
     * Sets the "max_ws_sessions" element
     */
    public void setMaxWsSessions(int maxWsSessions)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MAXWSSESSIONS$38, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(MAXWSSESSIONS$38);
            }
            target.setIntValue(maxWsSessions);
        }
    }
    
    /**
     * Sets (as xml) the "max_ws_sessions" element
     */
    public void xsetMaxWsSessions(org.apache.xmlbeans.XmlInt maxWsSessions)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(MAXWSSESSIONS$38, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(MAXWSSESSIONS$38);
            }
            target.set(maxWsSessions);
        }
    }
    
    /**
     * Unsets the "max_ws_sessions" element
     */
    public void unsetMaxWsSessions()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(MAXWSSESSIONS$38, 0);
        }
    }
    
    /**
     * Gets the "mname" element
     */
    public java.lang.String getMname()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MNAME$40, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "mname" element
     */
    public org.apache.xmlbeans.XmlString xgetMname()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MNAME$40, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "mname" element
     */
    public boolean isNilMname()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MNAME$40, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "mname" element
     */
    public boolean isSetMname()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(MNAME$40) != 0;
        }
    }
    
    /**
     * Sets the "mname" element
     */
    public void setMname(java.lang.String mname)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MNAME$40, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(MNAME$40);
            }
            target.setStringValue(mname);
        }
    }
    
    /**
     * Sets (as xml) the "mname" element
     */
    public void xsetMname(org.apache.xmlbeans.XmlString mname)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MNAME$40, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(MNAME$40);
            }
            target.set(mname);
        }
    }
    
    /**
     * Nils the "mname" element
     */
    public void setNilMname()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MNAME$40, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(MNAME$40);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "mname" element
     */
    public void unsetMname()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(MNAME$40, 0);
        }
    }
    
    /**
     * Gets the "password" element
     */
    public java.lang.String getPassword()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PASSWORD$42, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "password" element
     */
    public org.apache.xmlbeans.XmlString xgetPassword()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PASSWORD$42, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "password" element
     */
    public boolean isNilPassword()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PASSWORD$42, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "password" element
     */
    public boolean isSetPassword()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(PASSWORD$42) != 0;
        }
    }
    
    /**
     * Sets the "password" element
     */
    public void setPassword(java.lang.String password)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PASSWORD$42, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PASSWORD$42);
            }
            target.setStringValue(password);
        }
    }
    
    /**
     * Sets (as xml) the "password" element
     */
    public void xsetPassword(org.apache.xmlbeans.XmlString password)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PASSWORD$42, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PASSWORD$42);
            }
            target.set(password);
        }
    }
    
    /**
     * Nils the "password" element
     */
    public void setNilPassword()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PASSWORD$42, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PASSWORD$42);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "password" element
     */
    public void unsetPassword()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(PASSWORD$42, 0);
        }
    }
    
    /**
     * Gets the "reset_password" element
     */
    public boolean getResetPassword()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(RESETPASSWORD$44, 0);
            if (target == null)
            {
                return false;
            }
            return target.getBooleanValue();
        }
    }
    
    /**
     * Gets (as xml) the "reset_password" element
     */
    public org.apache.xmlbeans.XmlBoolean xgetResetPassword()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(RESETPASSWORD$44, 0);
            return target;
        }
    }
    
    /**
     * True if has "reset_password" element
     */
    public boolean isSetResetPassword()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(RESETPASSWORD$44) != 0;
        }
    }
    
    /**
     * Sets the "reset_password" element
     */
    public void setResetPassword(boolean resetPassword)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(RESETPASSWORD$44, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(RESETPASSWORD$44);
            }
            target.setBooleanValue(resetPassword);
        }
    }
    
    /**
     * Sets (as xml) the "reset_password" element
     */
    public void xsetResetPassword(org.apache.xmlbeans.XmlBoolean resetPassword)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(RESETPASSWORD$44, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlBoolean)get_store().add_element_user(RESETPASSWORD$44);
            }
            target.set(resetPassword);
        }
    }
    
    /**
     * Unsets the "reset_password" element
     */
    public void unsetResetPassword()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(RESETPASSWORD$44, 0);
        }
    }
    
    /**
     * Gets the "shift" element
     */
    public com.bagnet.nettracer.tracing.db.xsd.WorkShift getShift()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.tracing.db.xsd.WorkShift target = null;
            target = (com.bagnet.nettracer.tracing.db.xsd.WorkShift)get_store().find_element_user(SHIFT$46, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Tests for nil "shift" element
     */
    public boolean isNilShift()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.tracing.db.xsd.WorkShift target = null;
            target = (com.bagnet.nettracer.tracing.db.xsd.WorkShift)get_store().find_element_user(SHIFT$46, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "shift" element
     */
    public boolean isSetShift()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(SHIFT$46) != 0;
        }
    }
    
    /**
     * Sets the "shift" element
     */
    public void setShift(com.bagnet.nettracer.tracing.db.xsd.WorkShift shift)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.tracing.db.xsd.WorkShift target = null;
            target = (com.bagnet.nettracer.tracing.db.xsd.WorkShift)get_store().find_element_user(SHIFT$46, 0);
            if (target == null)
            {
                target = (com.bagnet.nettracer.tracing.db.xsd.WorkShift)get_store().add_element_user(SHIFT$46);
            }
            target.set(shift);
        }
    }
    
    /**
     * Appends and returns a new empty "shift" element
     */
    public com.bagnet.nettracer.tracing.db.xsd.WorkShift addNewShift()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.tracing.db.xsd.WorkShift target = null;
            target = (com.bagnet.nettracer.tracing.db.xsd.WorkShift)get_store().add_element_user(SHIFT$46);
            return target;
        }
    }
    
    /**
     * Nils the "shift" element
     */
    public void setNilShift()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.tracing.db.xsd.WorkShift target = null;
            target = (com.bagnet.nettracer.tracing.db.xsd.WorkShift)get_store().find_element_user(SHIFT$46, 0);
            if (target == null)
            {
                target = (com.bagnet.nettracer.tracing.db.xsd.WorkShift)get_store().add_element_user(SHIFT$46);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "shift" element
     */
    public void unsetShift()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(SHIFT$46, 0);
        }
    }
    
    /**
     * Gets the "station" element
     */
    public com.bagnet.nettracer.tracing.db.xsd.Station getStation()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.tracing.db.xsd.Station target = null;
            target = (com.bagnet.nettracer.tracing.db.xsd.Station)get_store().find_element_user(STATION$48, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Tests for nil "station" element
     */
    public boolean isNilStation()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.tracing.db.xsd.Station target = null;
            target = (com.bagnet.nettracer.tracing.db.xsd.Station)get_store().find_element_user(STATION$48, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "station" element
     */
    public boolean isSetStation()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(STATION$48) != 0;
        }
    }
    
    /**
     * Sets the "station" element
     */
    public void setStation(com.bagnet.nettracer.tracing.db.xsd.Station station)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.tracing.db.xsd.Station target = null;
            target = (com.bagnet.nettracer.tracing.db.xsd.Station)get_store().find_element_user(STATION$48, 0);
            if (target == null)
            {
                target = (com.bagnet.nettracer.tracing.db.xsd.Station)get_store().add_element_user(STATION$48);
            }
            target.set(station);
        }
    }
    
    /**
     * Appends and returns a new empty "station" element
     */
    public com.bagnet.nettracer.tracing.db.xsd.Station addNewStation()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.tracing.db.xsd.Station target = null;
            target = (com.bagnet.nettracer.tracing.db.xsd.Station)get_store().add_element_user(STATION$48);
            return target;
        }
    }
    
    /**
     * Nils the "station" element
     */
    public void setNilStation()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.tracing.db.xsd.Station target = null;
            target = (com.bagnet.nettracer.tracing.db.xsd.Station)get_store().find_element_user(STATION$48, 0);
            if (target == null)
            {
                target = (com.bagnet.nettracer.tracing.db.xsd.Station)get_store().add_element_user(STATION$48);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "station" element
     */
    public void unsetStation()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(STATION$48, 0);
        }
    }
    
    /**
     * Gets the "timeformat" element
     */
    public com.bagnet.nettracer.tracing.db.xsd.NTTimeFormat getTimeformat()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.tracing.db.xsd.NTTimeFormat target = null;
            target = (com.bagnet.nettracer.tracing.db.xsd.NTTimeFormat)get_store().find_element_user(TIMEFORMAT$50, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Tests for nil "timeformat" element
     */
    public boolean isNilTimeformat()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.tracing.db.xsd.NTTimeFormat target = null;
            target = (com.bagnet.nettracer.tracing.db.xsd.NTTimeFormat)get_store().find_element_user(TIMEFORMAT$50, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "timeformat" element
     */
    public boolean isSetTimeformat()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(TIMEFORMAT$50) != 0;
        }
    }
    
    /**
     * Sets the "timeformat" element
     */
    public void setTimeformat(com.bagnet.nettracer.tracing.db.xsd.NTTimeFormat timeformat)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.tracing.db.xsd.NTTimeFormat target = null;
            target = (com.bagnet.nettracer.tracing.db.xsd.NTTimeFormat)get_store().find_element_user(TIMEFORMAT$50, 0);
            if (target == null)
            {
                target = (com.bagnet.nettracer.tracing.db.xsd.NTTimeFormat)get_store().add_element_user(TIMEFORMAT$50);
            }
            target.set(timeformat);
        }
    }
    
    /**
     * Appends and returns a new empty "timeformat" element
     */
    public com.bagnet.nettracer.tracing.db.xsd.NTTimeFormat addNewTimeformat()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.tracing.db.xsd.NTTimeFormat target = null;
            target = (com.bagnet.nettracer.tracing.db.xsd.NTTimeFormat)get_store().add_element_user(TIMEFORMAT$50);
            return target;
        }
    }
    
    /**
     * Nils the "timeformat" element
     */
    public void setNilTimeformat()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.tracing.db.xsd.NTTimeFormat target = null;
            target = (com.bagnet.nettracer.tracing.db.xsd.NTTimeFormat)get_store().find_element_user(TIMEFORMAT$50, 0);
            if (target == null)
            {
                target = (com.bagnet.nettracer.tracing.db.xsd.NTTimeFormat)get_store().add_element_user(TIMEFORMAT$50);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "timeformat" element
     */
    public void unsetTimeformat()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(TIMEFORMAT$50, 0);
        }
    }
    
    /**
     * Gets the "timeout" element
     */
    public int getTimeout()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TIMEOUT$52, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "timeout" element
     */
    public org.apache.xmlbeans.XmlInt xgetTimeout()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(TIMEOUT$52, 0);
            return target;
        }
    }
    
    /**
     * True if has "timeout" element
     */
    public boolean isSetTimeout()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(TIMEOUT$52) != 0;
        }
    }
    
    /**
     * Sets the "timeout" element
     */
    public void setTimeout(int timeout)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TIMEOUT$52, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(TIMEOUT$52);
            }
            target.setIntValue(timeout);
        }
    }
    
    /**
     * Sets (as xml) the "timeout" element
     */
    public void xsetTimeout(org.apache.xmlbeans.XmlInt timeout)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(TIMEOUT$52, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(TIMEOUT$52);
            }
            target.set(timeout);
        }
    }
    
    /**
     * Unsets the "timeout" element
     */
    public void unsetTimeout()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(TIMEOUT$52, 0);
        }
    }
    
    /**
     * Gets the "usergroup_id" element
     */
    public int getUsergroupId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(USERGROUPID$54, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "usergroup_id" element
     */
    public org.apache.xmlbeans.XmlInt xgetUsergroupId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(USERGROUPID$54, 0);
            return target;
        }
    }
    
    /**
     * True if has "usergroup_id" element
     */
    public boolean isSetUsergroupId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(USERGROUPID$54) != 0;
        }
    }
    
    /**
     * Sets the "usergroup_id" element
     */
    public void setUsergroupId(int usergroupId)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(USERGROUPID$54, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(USERGROUPID$54);
            }
            target.setIntValue(usergroupId);
        }
    }
    
    /**
     * Sets (as xml) the "usergroup_id" element
     */
    public void xsetUsergroupId(org.apache.xmlbeans.XmlInt usergroupId)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(USERGROUPID$54, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(USERGROUPID$54);
            }
            target.set(usergroupId);
        }
    }
    
    /**
     * Unsets the "usergroup_id" element
     */
    public void unsetUsergroupId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(USERGROUPID$54, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(USERNAME$56, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(USERNAME$56, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(USERNAME$56, 0);
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
            return get_store().count_elements(USERNAME$56) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(USERNAME$56, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(USERNAME$56);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(USERNAME$56, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(USERNAME$56);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(USERNAME$56, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(USERNAME$56);
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
            get_store().remove_element(USERNAME$56, 0);
        }
    }
    
    /**
     * Gets the "web_enabled" element
     */
    public boolean getWebEnabled()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(WEBENABLED$58, 0);
            if (target == null)
            {
                return false;
            }
            return target.getBooleanValue();
        }
    }
    
    /**
     * Gets (as xml) the "web_enabled" element
     */
    public org.apache.xmlbeans.XmlBoolean xgetWebEnabled()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(WEBENABLED$58, 0);
            return target;
        }
    }
    
    /**
     * True if has "web_enabled" element
     */
    public boolean isSetWebEnabled()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(WEBENABLED$58) != 0;
        }
    }
    
    /**
     * Sets the "web_enabled" element
     */
    public void setWebEnabled(boolean webEnabled)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(WEBENABLED$58, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(WEBENABLED$58);
            }
            target.setBooleanValue(webEnabled);
        }
    }
    
    /**
     * Sets (as xml) the "web_enabled" element
     */
    public void xsetWebEnabled(org.apache.xmlbeans.XmlBoolean webEnabled)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(WEBENABLED$58, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlBoolean)get_store().add_element_user(WEBENABLED$58);
            }
            target.set(webEnabled);
        }
    }
    
    /**
     * Unsets the "web_enabled" element
     */
    public void unsetWebEnabled()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(WEBENABLED$58, 0);
        }
    }
    
    /**
     * Gets the "ws_enabled" element
     */
    public boolean getWsEnabled()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(WSENABLED$60, 0);
            if (target == null)
            {
                return false;
            }
            return target.getBooleanValue();
        }
    }
    
    /**
     * Gets (as xml) the "ws_enabled" element
     */
    public org.apache.xmlbeans.XmlBoolean xgetWsEnabled()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(WSENABLED$60, 0);
            return target;
        }
    }
    
    /**
     * True if has "ws_enabled" element
     */
    public boolean isSetWsEnabled()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(WSENABLED$60) != 0;
        }
    }
    
    /**
     * Sets the "ws_enabled" element
     */
    public void setWsEnabled(boolean wsEnabled)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(WSENABLED$60, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(WSENABLED$60);
            }
            target.setBooleanValue(wsEnabled);
        }
    }
    
    /**
     * Sets (as xml) the "ws_enabled" element
     */
    public void xsetWsEnabled(org.apache.xmlbeans.XmlBoolean wsEnabled)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(WSENABLED$60, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlBoolean)get_store().add_element_user(WSENABLED$60);
            }
            target.set(wsEnabled);
        }
    }
    
    /**
     * Unsets the "ws_enabled" element
     */
    public void unsetWsEnabled()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(WSENABLED$60, 0);
        }
    }
}
