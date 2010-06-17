/*
 * XML Type:  PassengerView
 * Namespace: http://onlineclaims.ws.nettracer.bagnet.com/xsd
 * Java type: com.bagnet.nettracer.ws.onlineclaims.xsd.PassengerView
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.onlineclaims.xsd.impl;
/**
 * An XML PassengerView(@http://onlineclaims.ws.nettracer.bagnet.com/xsd).
 *
 * This is a complex type.
 */
public class PassengerViewImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.onlineclaims.xsd.PassengerView
{
    
    public PassengerViewImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName AUTHENTICATIONSUCCESS$0 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "authenticationSuccess");
    private static final javax.xml.namespace.QName CLAIMID$2 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "claimId");
    private static final javax.xml.namespace.QName DATA$4 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd", "data");
    
    
    /**
     * Gets the "authenticationSuccess" element
     */
    public boolean getAuthenticationSuccess()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(AUTHENTICATIONSUCCESS$0, 0);
            if (target == null)
            {
                return false;
            }
            return target.getBooleanValue();
        }
    }
    
    /**
     * Gets (as xml) the "authenticationSuccess" element
     */
    public org.apache.xmlbeans.XmlBoolean xgetAuthenticationSuccess()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(AUTHENTICATIONSUCCESS$0, 0);
            return target;
        }
    }
    
    /**
     * True if has "authenticationSuccess" element
     */
    public boolean isSetAuthenticationSuccess()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(AUTHENTICATIONSUCCESS$0) != 0;
        }
    }
    
    /**
     * Sets the "authenticationSuccess" element
     */
    public void setAuthenticationSuccess(boolean authenticationSuccess)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(AUTHENTICATIONSUCCESS$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(AUTHENTICATIONSUCCESS$0);
            }
            target.setBooleanValue(authenticationSuccess);
        }
    }
    
    /**
     * Sets (as xml) the "authenticationSuccess" element
     */
    public void xsetAuthenticationSuccess(org.apache.xmlbeans.XmlBoolean authenticationSuccess)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(AUTHENTICATIONSUCCESS$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlBoolean)get_store().add_element_user(AUTHENTICATIONSUCCESS$0);
            }
            target.set(authenticationSuccess);
        }
    }
    
    /**
     * Unsets the "authenticationSuccess" element
     */
    public void unsetAuthenticationSuccess()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(AUTHENTICATIONSUCCESS$0, 0);
        }
    }
    
    /**
     * Gets the "claimId" element
     */
    public long getClaimId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CLAIMID$2, 0);
            if (target == null)
            {
                return 0L;
            }
            return target.getLongValue();
        }
    }
    
    /**
     * Gets (as xml) the "claimId" element
     */
    public org.apache.xmlbeans.XmlLong xgetClaimId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlLong target = null;
            target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(CLAIMID$2, 0);
            return target;
        }
    }
    
    /**
     * True if has "claimId" element
     */
    public boolean isSetClaimId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CLAIMID$2) != 0;
        }
    }
    
    /**
     * Sets the "claimId" element
     */
    public void setClaimId(long claimId)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CLAIMID$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CLAIMID$2);
            }
            target.setLongValue(claimId);
        }
    }
    
    /**
     * Sets (as xml) the "claimId" element
     */
    public void xsetClaimId(org.apache.xmlbeans.XmlLong claimId)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlLong target = null;
            target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(CLAIMID$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlLong)get_store().add_element_user(CLAIMID$2);
            }
            target.set(claimId);
        }
    }
    
    /**
     * Unsets the "claimId" element
     */
    public void unsetClaimId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CLAIMID$2, 0);
        }
    }
    
    /**
     * Gets the "data" element
     */
    public com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident getData()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident)get_store().find_element_user(DATA$4, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Tests for nil "data" element
     */
    public boolean isNilData()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident)get_store().find_element_user(DATA$4, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "data" element
     */
    public boolean isSetData()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DATA$4) != 0;
        }
    }
    
    /**
     * Sets the "data" element
     */
    public void setData(com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident data)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident)get_store().find_element_user(DATA$4, 0);
            if (target == null)
            {
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident)get_store().add_element_user(DATA$4);
            }
            target.set(data);
        }
    }
    
    /**
     * Appends and returns a new empty "data" element
     */
    public com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident addNewData()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident)get_store().add_element_user(DATA$4);
            return target;
        }
    }
    
    /**
     * Nils the "data" element
     */
    public void setNilData()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident)get_store().find_element_user(DATA$4, 0);
            if (target == null)
            {
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident)get_store().add_element_user(DATA$4);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "data" element
     */
    public void unsetData()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DATA$4, 0);
        }
    }
}
