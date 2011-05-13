/*
 * XML Type:  WS_PVIncident
 * Namespace: http://pojo.core.ws.nettracer.bagnet.com/xsd
 * Java type: com.bagnet.nettracer.ws.core.pojo.xsd.WSPVIncident
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.core.pojo.xsd.impl;
/**
 * An XML WS_PVIncident(@http://pojo.core.ws.nettracer.bagnet.com/xsd).
 *
 * This is a complex type.
 */
public class WSPVIncidentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.core.pojo.xsd.WSPVIncident
{
    
    public WSPVIncidentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName COMPANYCODEID$0 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "companycode_id");
    private static final javax.xml.namespace.QName DISPCREATETIME$2 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "dispcreatetime");
    private static final javax.xml.namespace.QName EMAIL$4 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "email");
    private static final javax.xml.namespace.QName ERRORCODE$6 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "errorcode");
    private static final javax.xml.namespace.QName FIRSTNAME$8 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "firstname");
    private static final javax.xml.namespace.QName HOMEPHONE$10 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "homephone");
    private static final javax.xml.namespace.QName HOTEL$12 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "hotel");
    private static final javax.xml.namespace.QName INCIDENTID$14 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "incident_ID");
    private static final javax.xml.namespace.QName INCIDENTSTATUS$16 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "incident_status");
    private static final javax.xml.namespace.QName ITEMS$18 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "items");
    private static final javax.xml.namespace.QName LASTNAME$20 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "lastname");
    private static final javax.xml.namespace.QName MIDDLENAME$22 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "middlename");
    private static final javax.xml.namespace.QName MOBILE$24 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "mobile");
    private static final javax.xml.namespace.QName WORKPHONE$26 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "workphone");
    
    
    /**
     * Gets the "companycode_id" element
     */
    public java.lang.String getCompanycodeId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COMPANYCODEID$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "companycode_id" element
     */
    public org.apache.xmlbeans.XmlString xgetCompanycodeId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COMPANYCODEID$0, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "companycode_id" element
     */
    public boolean isNilCompanycodeId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COMPANYCODEID$0, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "companycode_id" element
     */
    public boolean isSetCompanycodeId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(COMPANYCODEID$0) != 0;
        }
    }
    
    /**
     * Sets the "companycode_id" element
     */
    public void setCompanycodeId(java.lang.String companycodeId)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COMPANYCODEID$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(COMPANYCODEID$0);
            }
            target.setStringValue(companycodeId);
        }
    }
    
    /**
     * Sets (as xml) the "companycode_id" element
     */
    public void xsetCompanycodeId(org.apache.xmlbeans.XmlString companycodeId)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COMPANYCODEID$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(COMPANYCODEID$0);
            }
            target.set(companycodeId);
        }
    }
    
    /**
     * Nils the "companycode_id" element
     */
    public void setNilCompanycodeId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COMPANYCODEID$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(COMPANYCODEID$0);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "companycode_id" element
     */
    public void unsetCompanycodeId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(COMPANYCODEID$0, 0);
        }
    }
    
    /**
     * Gets the "dispcreatetime" element
     */
    public java.lang.String getDispcreatetime()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DISPCREATETIME$2, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "dispcreatetime" element
     */
    public org.apache.xmlbeans.XmlString xgetDispcreatetime()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DISPCREATETIME$2, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "dispcreatetime" element
     */
    public boolean isNilDispcreatetime()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DISPCREATETIME$2, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "dispcreatetime" element
     */
    public boolean isSetDispcreatetime()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DISPCREATETIME$2) != 0;
        }
    }
    
    /**
     * Sets the "dispcreatetime" element
     */
    public void setDispcreatetime(java.lang.String dispcreatetime)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DISPCREATETIME$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DISPCREATETIME$2);
            }
            target.setStringValue(dispcreatetime);
        }
    }
    
    /**
     * Sets (as xml) the "dispcreatetime" element
     */
    public void xsetDispcreatetime(org.apache.xmlbeans.XmlString dispcreatetime)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DISPCREATETIME$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DISPCREATETIME$2);
            }
            target.set(dispcreatetime);
        }
    }
    
    /**
     * Nils the "dispcreatetime" element
     */
    public void setNilDispcreatetime()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DISPCREATETIME$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DISPCREATETIME$2);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "dispcreatetime" element
     */
    public void unsetDispcreatetime()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DISPCREATETIME$2, 0);
        }
    }
    
    /**
     * Gets the "email" element
     */
    public java.lang.String getEmail()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EMAIL$4, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "email" element
     */
    public org.apache.xmlbeans.XmlString xgetEmail()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(EMAIL$4, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "email" element
     */
    public boolean isNilEmail()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(EMAIL$4, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "email" element
     */
    public boolean isSetEmail()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(EMAIL$4) != 0;
        }
    }
    
    /**
     * Sets the "email" element
     */
    public void setEmail(java.lang.String email)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EMAIL$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(EMAIL$4);
            }
            target.setStringValue(email);
        }
    }
    
    /**
     * Sets (as xml) the "email" element
     */
    public void xsetEmail(org.apache.xmlbeans.XmlString email)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(EMAIL$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(EMAIL$4);
            }
            target.set(email);
        }
    }
    
    /**
     * Nils the "email" element
     */
    public void setNilEmail()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(EMAIL$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(EMAIL$4);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "email" element
     */
    public void unsetEmail()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(EMAIL$4, 0);
        }
    }
    
    /**
     * Gets the "errorcode" element
     */
    public java.lang.String getErrorcode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ERRORCODE$6, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "errorcode" element
     */
    public org.apache.xmlbeans.XmlString xgetErrorcode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ERRORCODE$6, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "errorcode" element
     */
    public boolean isNilErrorcode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ERRORCODE$6, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "errorcode" element
     */
    public boolean isSetErrorcode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(ERRORCODE$6) != 0;
        }
    }
    
    /**
     * Sets the "errorcode" element
     */
    public void setErrorcode(java.lang.String errorcode)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ERRORCODE$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ERRORCODE$6);
            }
            target.setStringValue(errorcode);
        }
    }
    
    /**
     * Sets (as xml) the "errorcode" element
     */
    public void xsetErrorcode(org.apache.xmlbeans.XmlString errorcode)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ERRORCODE$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(ERRORCODE$6);
            }
            target.set(errorcode);
        }
    }
    
    /**
     * Nils the "errorcode" element
     */
    public void setNilErrorcode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ERRORCODE$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(ERRORCODE$6);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "errorcode" element
     */
    public void unsetErrorcode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(ERRORCODE$6, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FIRSTNAME$8, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FIRSTNAME$8, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FIRSTNAME$8, 0);
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
            return get_store().count_elements(FIRSTNAME$8) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FIRSTNAME$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FIRSTNAME$8);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FIRSTNAME$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(FIRSTNAME$8);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FIRSTNAME$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(FIRSTNAME$8);
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
            get_store().remove_element(FIRSTNAME$8, 0);
        }
    }
    
    /**
     * Gets the "homephone" element
     */
    public java.lang.String getHomephone()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(HOMEPHONE$10, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "homephone" element
     */
    public org.apache.xmlbeans.XmlString xgetHomephone()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(HOMEPHONE$10, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "homephone" element
     */
    public boolean isNilHomephone()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(HOMEPHONE$10, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "homephone" element
     */
    public boolean isSetHomephone()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(HOMEPHONE$10) != 0;
        }
    }
    
    /**
     * Sets the "homephone" element
     */
    public void setHomephone(java.lang.String homephone)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(HOMEPHONE$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(HOMEPHONE$10);
            }
            target.setStringValue(homephone);
        }
    }
    
    /**
     * Sets (as xml) the "homephone" element
     */
    public void xsetHomephone(org.apache.xmlbeans.XmlString homephone)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(HOMEPHONE$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(HOMEPHONE$10);
            }
            target.set(homephone);
        }
    }
    
    /**
     * Nils the "homephone" element
     */
    public void setNilHomephone()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(HOMEPHONE$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(HOMEPHONE$10);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "homephone" element
     */
    public void unsetHomephone()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(HOMEPHONE$10, 0);
        }
    }
    
    /**
     * Gets the "hotel" element
     */
    public java.lang.String getHotel()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(HOTEL$12, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "hotel" element
     */
    public org.apache.xmlbeans.XmlString xgetHotel()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(HOTEL$12, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "hotel" element
     */
    public boolean isNilHotel()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(HOTEL$12, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "hotel" element
     */
    public boolean isSetHotel()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(HOTEL$12) != 0;
        }
    }
    
    /**
     * Sets the "hotel" element
     */
    public void setHotel(java.lang.String hotel)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(HOTEL$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(HOTEL$12);
            }
            target.setStringValue(hotel);
        }
    }
    
    /**
     * Sets (as xml) the "hotel" element
     */
    public void xsetHotel(org.apache.xmlbeans.XmlString hotel)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(HOTEL$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(HOTEL$12);
            }
            target.set(hotel);
        }
    }
    
    /**
     * Nils the "hotel" element
     */
    public void setNilHotel()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(HOTEL$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(HOTEL$12);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "hotel" element
     */
    public void unsetHotel()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(HOTEL$12, 0);
        }
    }
    
    /**
     * Gets the "incident_ID" element
     */
    public java.lang.String getIncidentID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(INCIDENTID$14, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "incident_ID" element
     */
    public org.apache.xmlbeans.XmlString xgetIncidentID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(INCIDENTID$14, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "incident_ID" element
     */
    public boolean isNilIncidentID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(INCIDENTID$14, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "incident_ID" element
     */
    public boolean isSetIncidentID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(INCIDENTID$14) != 0;
        }
    }
    
    /**
     * Sets the "incident_ID" element
     */
    public void setIncidentID(java.lang.String incidentID)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(INCIDENTID$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(INCIDENTID$14);
            }
            target.setStringValue(incidentID);
        }
    }
    
    /**
     * Sets (as xml) the "incident_ID" element
     */
    public void xsetIncidentID(org.apache.xmlbeans.XmlString incidentID)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(INCIDENTID$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(INCIDENTID$14);
            }
            target.set(incidentID);
        }
    }
    
    /**
     * Nils the "incident_ID" element
     */
    public void setNilIncidentID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(INCIDENTID$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(INCIDENTID$14);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "incident_ID" element
     */
    public void unsetIncidentID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(INCIDENTID$14, 0);
        }
    }
    
    /**
     * Gets the "incident_status" element
     */
    public java.lang.String getIncidentStatus()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(INCIDENTSTATUS$16, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "incident_status" element
     */
    public org.apache.xmlbeans.XmlString xgetIncidentStatus()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(INCIDENTSTATUS$16, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "incident_status" element
     */
    public boolean isNilIncidentStatus()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(INCIDENTSTATUS$16, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "incident_status" element
     */
    public boolean isSetIncidentStatus()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(INCIDENTSTATUS$16) != 0;
        }
    }
    
    /**
     * Sets the "incident_status" element
     */
    public void setIncidentStatus(java.lang.String incidentStatus)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(INCIDENTSTATUS$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(INCIDENTSTATUS$16);
            }
            target.setStringValue(incidentStatus);
        }
    }
    
    /**
     * Sets (as xml) the "incident_status" element
     */
    public void xsetIncidentStatus(org.apache.xmlbeans.XmlString incidentStatus)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(INCIDENTSTATUS$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(INCIDENTSTATUS$16);
            }
            target.set(incidentStatus);
        }
    }
    
    /**
     * Nils the "incident_status" element
     */
    public void setNilIncidentStatus()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(INCIDENTSTATUS$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(INCIDENTSTATUS$16);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "incident_status" element
     */
    public void unsetIncidentStatus()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(INCIDENTSTATUS$16, 0);
        }
    }
    
    /**
     * Gets array of all "items" elements
     */
    public com.bagnet.nettracer.ws.core.pojo.xsd.WSPVItem[] getItemsArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(ITEMS$18, targetList);
            com.bagnet.nettracer.ws.core.pojo.xsd.WSPVItem[] result = new com.bagnet.nettracer.ws.core.pojo.xsd.WSPVItem[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "items" element
     */
    public com.bagnet.nettracer.ws.core.pojo.xsd.WSPVItem getItemsArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.pojo.xsd.WSPVItem target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVItem)get_store().find_element_user(ITEMS$18, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Tests for nil ith "items" element
     */
    public boolean isNilItemsArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.pojo.xsd.WSPVItem target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVItem)get_store().find_element_user(ITEMS$18, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target.isNil();
        }
    }
    
    /**
     * Returns number of "items" element
     */
    public int sizeOfItemsArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(ITEMS$18);
        }
    }
    
    /**
     * Sets array of all "items" element
     */
    public void setItemsArray(com.bagnet.nettracer.ws.core.pojo.xsd.WSPVItem[] itemsArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(itemsArray, ITEMS$18);
        }
    }
    
    /**
     * Sets ith "items" element
     */
    public void setItemsArray(int i, com.bagnet.nettracer.ws.core.pojo.xsd.WSPVItem items)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.pojo.xsd.WSPVItem target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVItem)get_store().find_element_user(ITEMS$18, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(items);
        }
    }
    
    /**
     * Nils the ith "items" element
     */
    public void setNilItemsArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.pojo.xsd.WSPVItem target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVItem)get_store().find_element_user(ITEMS$18, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.setNil();
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "items" element
     */
    public com.bagnet.nettracer.ws.core.pojo.xsd.WSPVItem insertNewItems(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.pojo.xsd.WSPVItem target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVItem)get_store().insert_element_user(ITEMS$18, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "items" element
     */
    public com.bagnet.nettracer.ws.core.pojo.xsd.WSPVItem addNewItems()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.pojo.xsd.WSPVItem target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVItem)get_store().add_element_user(ITEMS$18);
            return target;
        }
    }
    
    /**
     * Removes the ith "items" element
     */
    public void removeItems(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(ITEMS$18, i);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LASTNAME$20, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(LASTNAME$20, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(LASTNAME$20, 0);
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
            return get_store().count_elements(LASTNAME$20) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LASTNAME$20, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(LASTNAME$20);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(LASTNAME$20, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(LASTNAME$20);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(LASTNAME$20, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(LASTNAME$20);
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
            get_store().remove_element(LASTNAME$20, 0);
        }
    }
    
    /**
     * Gets the "middlename" element
     */
    public java.lang.String getMiddlename()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MIDDLENAME$22, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "middlename" element
     */
    public org.apache.xmlbeans.XmlString xgetMiddlename()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MIDDLENAME$22, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "middlename" element
     */
    public boolean isNilMiddlename()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MIDDLENAME$22, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "middlename" element
     */
    public boolean isSetMiddlename()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(MIDDLENAME$22) != 0;
        }
    }
    
    /**
     * Sets the "middlename" element
     */
    public void setMiddlename(java.lang.String middlename)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MIDDLENAME$22, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(MIDDLENAME$22);
            }
            target.setStringValue(middlename);
        }
    }
    
    /**
     * Sets (as xml) the "middlename" element
     */
    public void xsetMiddlename(org.apache.xmlbeans.XmlString middlename)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MIDDLENAME$22, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(MIDDLENAME$22);
            }
            target.set(middlename);
        }
    }
    
    /**
     * Nils the "middlename" element
     */
    public void setNilMiddlename()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MIDDLENAME$22, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(MIDDLENAME$22);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "middlename" element
     */
    public void unsetMiddlename()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(MIDDLENAME$22, 0);
        }
    }
    
    /**
     * Gets the "mobile" element
     */
    public java.lang.String getMobile()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MOBILE$24, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "mobile" element
     */
    public org.apache.xmlbeans.XmlString xgetMobile()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MOBILE$24, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "mobile" element
     */
    public boolean isNilMobile()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MOBILE$24, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "mobile" element
     */
    public boolean isSetMobile()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(MOBILE$24) != 0;
        }
    }
    
    /**
     * Sets the "mobile" element
     */
    public void setMobile(java.lang.String mobile)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MOBILE$24, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(MOBILE$24);
            }
            target.setStringValue(mobile);
        }
    }
    
    /**
     * Sets (as xml) the "mobile" element
     */
    public void xsetMobile(org.apache.xmlbeans.XmlString mobile)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MOBILE$24, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(MOBILE$24);
            }
            target.set(mobile);
        }
    }
    
    /**
     * Nils the "mobile" element
     */
    public void setNilMobile()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MOBILE$24, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(MOBILE$24);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "mobile" element
     */
    public void unsetMobile()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(MOBILE$24, 0);
        }
    }
    
    /**
     * Gets the "workphone" element
     */
    public java.lang.String getWorkphone()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(WORKPHONE$26, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "workphone" element
     */
    public org.apache.xmlbeans.XmlString xgetWorkphone()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(WORKPHONE$26, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "workphone" element
     */
    public boolean isNilWorkphone()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(WORKPHONE$26, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "workphone" element
     */
    public boolean isSetWorkphone()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(WORKPHONE$26) != 0;
        }
    }
    
    /**
     * Sets the "workphone" element
     */
    public void setWorkphone(java.lang.String workphone)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(WORKPHONE$26, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(WORKPHONE$26);
            }
            target.setStringValue(workphone);
        }
    }
    
    /**
     * Sets (as xml) the "workphone" element
     */
    public void xsetWorkphone(org.apache.xmlbeans.XmlString workphone)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(WORKPHONE$26, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(WORKPHONE$26);
            }
            target.set(workphone);
        }
    }
    
    /**
     * Nils the "workphone" element
     */
    public void setNilWorkphone()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(WORKPHONE$26, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(WORKPHONE$26);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "workphone" element
     */
    public void unsetWorkphone()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(WORKPHONE$26, 0);
        }
    }
}
