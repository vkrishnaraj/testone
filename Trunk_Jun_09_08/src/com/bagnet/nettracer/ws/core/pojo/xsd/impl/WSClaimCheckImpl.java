/*
 * XML Type:  WS_ClaimCheck
 * Namespace: http://pojo.core.ws.nettracer.bagnet.com/xsd
 * Java type: com.bagnet.nettracer.ws.core.pojo.xsd.WSClaimCheck
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.core.pojo.xsd.impl;
/**
 * An XML WS_ClaimCheck(@http://pojo.core.ws.nettracer.bagnet.com/xsd).
 *
 * This is a complex type.
 */
public class WSClaimCheckImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.core.pojo.xsd.WSClaimCheck
{
    
    public WSClaimCheckImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName SCANS$0 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "scans");
    private static final javax.xml.namespace.QName TAG$2 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "tag");
    
    
    /**
     * Gets array of all "scans" elements
     */
    public com.bagnet.nettracer.ws.core.pojo.xsd.WSScanPoints[] getScansArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(SCANS$0, targetList);
            com.bagnet.nettracer.ws.core.pojo.xsd.WSScanPoints[] result = new com.bagnet.nettracer.ws.core.pojo.xsd.WSScanPoints[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "scans" element
     */
    public com.bagnet.nettracer.ws.core.pojo.xsd.WSScanPoints getScansArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.pojo.xsd.WSScanPoints target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSScanPoints)get_store().find_element_user(SCANS$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Tests for nil ith "scans" element
     */
    public boolean isNilScansArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.pojo.xsd.WSScanPoints target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSScanPoints)get_store().find_element_user(SCANS$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target.isNil();
        }
    }
    
    /**
     * Returns number of "scans" element
     */
    public int sizeOfScansArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(SCANS$0);
        }
    }
    
    /**
     * Sets array of all "scans" element
     */
    public void setScansArray(com.bagnet.nettracer.ws.core.pojo.xsd.WSScanPoints[] scansArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(scansArray, SCANS$0);
        }
    }
    
    /**
     * Sets ith "scans" element
     */
    public void setScansArray(int i, com.bagnet.nettracer.ws.core.pojo.xsd.WSScanPoints scans)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.pojo.xsd.WSScanPoints target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSScanPoints)get_store().find_element_user(SCANS$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(scans);
        }
    }
    
    /**
     * Nils the ith "scans" element
     */
    public void setNilScansArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.pojo.xsd.WSScanPoints target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSScanPoints)get_store().find_element_user(SCANS$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.setNil();
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "scans" element
     */
    public com.bagnet.nettracer.ws.core.pojo.xsd.WSScanPoints insertNewScans(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.pojo.xsd.WSScanPoints target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSScanPoints)get_store().insert_element_user(SCANS$0, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "scans" element
     */
    public com.bagnet.nettracer.ws.core.pojo.xsd.WSScanPoints addNewScans()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.pojo.xsd.WSScanPoints target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSScanPoints)get_store().add_element_user(SCANS$0);
            return target;
        }
    }
    
    /**
     * Removes the ith "scans" element
     */
    public void removeScans(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(SCANS$0, i);
        }
    }
    
    /**
     * Gets the "tag" element
     */
    public java.lang.String getTag()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TAG$2, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "tag" element
     */
    public org.apache.xmlbeans.XmlString xgetTag()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TAG$2, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "tag" element
     */
    public boolean isNilTag()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TAG$2, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "tag" element
     */
    public boolean isSetTag()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(TAG$2) != 0;
        }
    }
    
    /**
     * Sets the "tag" element
     */
    public void setTag(java.lang.String tag)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TAG$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(TAG$2);
            }
            target.setStringValue(tag);
        }
    }
    
    /**
     * Sets (as xml) the "tag" element
     */
    public void xsetTag(org.apache.xmlbeans.XmlString tag)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TAG$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(TAG$2);
            }
            target.set(tag);
        }
    }
    
    /**
     * Nils the "tag" element
     */
    public void setNilTag()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TAG$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(TAG$2);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "tag" element
     */
    public void unsetTag()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(TAG$2, 0);
        }
    }
}
