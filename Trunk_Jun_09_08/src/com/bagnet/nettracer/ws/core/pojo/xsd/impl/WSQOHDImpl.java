/*
 * XML Type:  WS_QOHD
 * Namespace: http://pojo.core.ws.nettracer.bagnet.com/xsd
 * Java type: com.bagnet.nettracer.ws.core.pojo.xsd.WSQOHD
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.core.pojo.xsd.impl;
/**
 * An XML WS_QOHD(@http://pojo.core.ws.nettracer.bagnet.com/xsd).
 *
 * This is a complex type.
 */
public class WSQOHDImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.core.pojo.xsd.WSQOHD
{
    
    public WSQOHDImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName BAGTAGNUMBER$0 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "bagTagNumber");
    private static final javax.xml.namespace.QName COMMENT$2 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "comment");
    private static final javax.xml.namespace.QName FOUNDATSTATION$4 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "foundAtStation");
    private static final javax.xml.namespace.QName FOUNDDATETIME$6 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "founddatetime");
    
    
    /**
     * Gets the "bagTagNumber" element
     */
    public java.lang.String getBagTagNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(BAGTAGNUMBER$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "bagTagNumber" element
     */
    public org.apache.xmlbeans.XmlString xgetBagTagNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(BAGTAGNUMBER$0, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "bagTagNumber" element
     */
    public boolean isNilBagTagNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(BAGTAGNUMBER$0, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "bagTagNumber" element
     */
    public boolean isSetBagTagNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(BAGTAGNUMBER$0) != 0;
        }
    }
    
    /**
     * Sets the "bagTagNumber" element
     */
    public void setBagTagNumber(java.lang.String bagTagNumber)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(BAGTAGNUMBER$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(BAGTAGNUMBER$0);
            }
            target.setStringValue(bagTagNumber);
        }
    }
    
    /**
     * Sets (as xml) the "bagTagNumber" element
     */
    public void xsetBagTagNumber(org.apache.xmlbeans.XmlString bagTagNumber)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(BAGTAGNUMBER$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(BAGTAGNUMBER$0);
            }
            target.set(bagTagNumber);
        }
    }
    
    /**
     * Nils the "bagTagNumber" element
     */
    public void setNilBagTagNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(BAGTAGNUMBER$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(BAGTAGNUMBER$0);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "bagTagNumber" element
     */
    public void unsetBagTagNumber()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(BAGTAGNUMBER$0, 0);
        }
    }
    
    /**
     * Gets the "comment" element
     */
    public java.lang.String getComment()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COMMENT$2, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "comment" element
     */
    public org.apache.xmlbeans.XmlString xgetComment()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COMMENT$2, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "comment" element
     */
    public boolean isNilComment()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COMMENT$2, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "comment" element
     */
    public boolean isSetComment()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(COMMENT$2) != 0;
        }
    }
    
    /**
     * Sets the "comment" element
     */
    public void setComment(java.lang.String comment)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COMMENT$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(COMMENT$2);
            }
            target.setStringValue(comment);
        }
    }
    
    /**
     * Sets (as xml) the "comment" element
     */
    public void xsetComment(org.apache.xmlbeans.XmlString comment)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COMMENT$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(COMMENT$2);
            }
            target.set(comment);
        }
    }
    
    /**
     * Nils the "comment" element
     */
    public void setNilComment()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COMMENT$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(COMMENT$2);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "comment" element
     */
    public void unsetComment()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(COMMENT$2, 0);
        }
    }
    
    /**
     * Gets the "foundAtStation" element
     */
    public java.lang.String getFoundAtStation()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FOUNDATSTATION$4, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "foundAtStation" element
     */
    public org.apache.xmlbeans.XmlString xgetFoundAtStation()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FOUNDATSTATION$4, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "foundAtStation" element
     */
    public boolean isNilFoundAtStation()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FOUNDATSTATION$4, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "foundAtStation" element
     */
    public boolean isSetFoundAtStation()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(FOUNDATSTATION$4) != 0;
        }
    }
    
    /**
     * Sets the "foundAtStation" element
     */
    public void setFoundAtStation(java.lang.String foundAtStation)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FOUNDATSTATION$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FOUNDATSTATION$4);
            }
            target.setStringValue(foundAtStation);
        }
    }
    
    /**
     * Sets (as xml) the "foundAtStation" element
     */
    public void xsetFoundAtStation(org.apache.xmlbeans.XmlString foundAtStation)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FOUNDATSTATION$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(FOUNDATSTATION$4);
            }
            target.set(foundAtStation);
        }
    }
    
    /**
     * Nils the "foundAtStation" element
     */
    public void setNilFoundAtStation()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FOUNDATSTATION$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(FOUNDATSTATION$4);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "foundAtStation" element
     */
    public void unsetFoundAtStation()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(FOUNDATSTATION$4, 0);
        }
    }
    
    /**
     * Gets the "founddatetime" element
     */
    public java.util.Calendar getFounddatetime()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FOUNDDATETIME$6, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "founddatetime" element
     */
    public org.apache.xmlbeans.XmlDate xgetFounddatetime()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDate target = null;
            target = (org.apache.xmlbeans.XmlDate)get_store().find_element_user(FOUNDDATETIME$6, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "founddatetime" element
     */
    public boolean isNilFounddatetime()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDate target = null;
            target = (org.apache.xmlbeans.XmlDate)get_store().find_element_user(FOUNDDATETIME$6, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "founddatetime" element
     */
    public boolean isSetFounddatetime()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(FOUNDDATETIME$6) != 0;
        }
    }
    
    /**
     * Sets the "founddatetime" element
     */
    public void setFounddatetime(java.util.Calendar founddatetime)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FOUNDDATETIME$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FOUNDDATETIME$6);
            }
            target.setCalendarValue(founddatetime);
        }
    }
    
    /**
     * Sets (as xml) the "founddatetime" element
     */
    public void xsetFounddatetime(org.apache.xmlbeans.XmlDate founddatetime)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDate target = null;
            target = (org.apache.xmlbeans.XmlDate)get_store().find_element_user(FOUNDDATETIME$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDate)get_store().add_element_user(FOUNDDATETIME$6);
            }
            target.set(founddatetime);
        }
    }
    
    /**
     * Nils the "founddatetime" element
     */
    public void setNilFounddatetime()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDate target = null;
            target = (org.apache.xmlbeans.XmlDate)get_store().find_element_user(FOUNDDATETIME$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDate)get_store().add_element_user(FOUNDDATETIME$6);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "founddatetime" element
     */
    public void unsetFounddatetime()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(FOUNDDATETIME$6, 0);
        }
    }
}
