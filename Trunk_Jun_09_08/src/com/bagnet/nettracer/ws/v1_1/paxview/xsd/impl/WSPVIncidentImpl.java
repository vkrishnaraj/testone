/*
 * XML Type:  WS_PVIncident
 * Namespace: http://paxview.v1_1.ws.nettracer.bagnet.com/xsd
 * Java type: com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVIncident
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.v1_1.paxview.xsd.impl;
/**
 * An XML WS_PVIncident(@http://paxview.v1_1.ws.nettracer.bagnet.com/xsd).
 *
 * This is a complex type.
 */
public class WSPVIncidentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVIncident
{
    
    public WSPVIncidentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName COMMENTS$0 = 
        new javax.xml.namespace.QName("http://paxview.v1_1.ws.nettracer.bagnet.com/xsd", "comments");
    private static final javax.xml.namespace.QName DISPCREATETIME$2 = 
        new javax.xml.namespace.QName("http://paxview.v1_1.ws.nettracer.bagnet.com/xsd", "dispcreatetime");
    private static final javax.xml.namespace.QName ERRORCODE$4 = 
        new javax.xml.namespace.QName("http://paxview.v1_1.ws.nettracer.bagnet.com/xsd", "errorcode");
    private static final javax.xml.namespace.QName INCIDENTID$6 = 
        new javax.xml.namespace.QName("http://paxview.v1_1.ws.nettracer.bagnet.com/xsd", "incident_ID");
    private static final javax.xml.namespace.QName INCIDENTSTATUS$8 = 
        new javax.xml.namespace.QName("http://paxview.v1_1.ws.nettracer.bagnet.com/xsd", "incident_status");
    private static final javax.xml.namespace.QName ITEMTYPE$10 = 
        new javax.xml.namespace.QName("http://paxview.v1_1.ws.nettracer.bagnet.com/xsd", "itemType");
    private static final javax.xml.namespace.QName ITEMS$12 = 
        new javax.xml.namespace.QName("http://paxview.v1_1.ws.nettracer.bagnet.com/xsd", "items");
    private static final javax.xml.namespace.QName PASSENGERS$14 = 
        new javax.xml.namespace.QName("http://paxview.v1_1.ws.nettracer.bagnet.com/xsd", "passengers");
    private static final javax.xml.namespace.QName PAXCLAIMCHECKS$16 = 
        new javax.xml.namespace.QName("http://paxview.v1_1.ws.nettracer.bagnet.com/xsd", "paxClaimchecks");
    private static final javax.xml.namespace.QName PAXCOMMUNICATION$18 = 
        new javax.xml.namespace.QName("http://paxview.v1_1.ws.nettracer.bagnet.com/xsd", "paxCommunication");
    
    
    /**
     * Gets the "comments" element
     */
    public java.lang.String getComments()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COMMENTS$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "comments" element
     */
    public org.apache.xmlbeans.XmlString xgetComments()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COMMENTS$0, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "comments" element
     */
    public boolean isNilComments()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COMMENTS$0, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "comments" element
     */
    public boolean isSetComments()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(COMMENTS$0) != 0;
        }
    }
    
    /**
     * Sets the "comments" element
     */
    public void setComments(java.lang.String comments)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COMMENTS$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(COMMENTS$0);
            }
            target.setStringValue(comments);
        }
    }
    
    /**
     * Sets (as xml) the "comments" element
     */
    public void xsetComments(org.apache.xmlbeans.XmlString comments)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COMMENTS$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(COMMENTS$0);
            }
            target.set(comments);
        }
    }
    
    /**
     * Nils the "comments" element
     */
    public void setNilComments()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COMMENTS$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(COMMENTS$0);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "comments" element
     */
    public void unsetComments()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(COMMENTS$0, 0);
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
     * Gets the "errorcode" element
     */
    public java.lang.String getErrorcode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ERRORCODE$4, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ERRORCODE$4, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ERRORCODE$4, 0);
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
            return get_store().count_elements(ERRORCODE$4) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ERRORCODE$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ERRORCODE$4);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ERRORCODE$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(ERRORCODE$4);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ERRORCODE$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(ERRORCODE$4);
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
            get_store().remove_element(ERRORCODE$4, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(INCIDENTID$6, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(INCIDENTID$6, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(INCIDENTID$6, 0);
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
            return get_store().count_elements(INCIDENTID$6) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(INCIDENTID$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(INCIDENTID$6);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(INCIDENTID$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(INCIDENTID$6);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(INCIDENTID$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(INCIDENTID$6);
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
            get_store().remove_element(INCIDENTID$6, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(INCIDENTSTATUS$8, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(INCIDENTSTATUS$8, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(INCIDENTSTATUS$8, 0);
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
            return get_store().count_elements(INCIDENTSTATUS$8) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(INCIDENTSTATUS$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(INCIDENTSTATUS$8);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(INCIDENTSTATUS$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(INCIDENTSTATUS$8);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(INCIDENTSTATUS$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(INCIDENTSTATUS$8);
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
            get_store().remove_element(INCIDENTSTATUS$8, 0);
        }
    }
    
    /**
     * Gets the "itemType" element
     */
    public java.lang.String getItemType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ITEMTYPE$10, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "itemType" element
     */
    public org.apache.xmlbeans.XmlString xgetItemType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ITEMTYPE$10, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "itemType" element
     */
    public boolean isNilItemType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ITEMTYPE$10, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "itemType" element
     */
    public boolean isSetItemType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(ITEMTYPE$10) != 0;
        }
    }
    
    /**
     * Sets the "itemType" element
     */
    public void setItemType(java.lang.String itemType)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ITEMTYPE$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ITEMTYPE$10);
            }
            target.setStringValue(itemType);
        }
    }
    
    /**
     * Sets (as xml) the "itemType" element
     */
    public void xsetItemType(org.apache.xmlbeans.XmlString itemType)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ITEMTYPE$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(ITEMTYPE$10);
            }
            target.set(itemType);
        }
    }
    
    /**
     * Nils the "itemType" element
     */
    public void setNilItemType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ITEMTYPE$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(ITEMTYPE$10);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "itemType" element
     */
    public void unsetItemType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(ITEMTYPE$10, 0);
        }
    }
    
    /**
     * Gets array of all "items" elements
     */
    public com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVItem[] getItemsArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(ITEMS$12, targetList);
            com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVItem[] result = new com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVItem[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "items" element
     */
    public com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVItem getItemsArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVItem target = null;
            target = (com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVItem)get_store().find_element_user(ITEMS$12, i);
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
            com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVItem target = null;
            target = (com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVItem)get_store().find_element_user(ITEMS$12, i);
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
            return get_store().count_elements(ITEMS$12);
        }
    }
    
    /**
     * Sets array of all "items" element
     */
    public void setItemsArray(com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVItem[] itemsArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(itemsArray, ITEMS$12);
        }
    }
    
    /**
     * Sets ith "items" element
     */
    public void setItemsArray(int i, com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVItem items)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVItem target = null;
            target = (com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVItem)get_store().find_element_user(ITEMS$12, i);
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
            com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVItem target = null;
            target = (com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVItem)get_store().find_element_user(ITEMS$12, i);
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
    public com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVItem insertNewItems(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVItem target = null;
            target = (com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVItem)get_store().insert_element_user(ITEMS$12, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "items" element
     */
    public com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVItem addNewItems()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVItem target = null;
            target = (com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVItem)get_store().add_element_user(ITEMS$12);
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
            get_store().remove_element(ITEMS$12, i);
        }
    }
    
    /**
     * Gets array of all "passengers" elements
     */
    public com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVPassenger[] getPassengersArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(PASSENGERS$14, targetList);
            com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVPassenger[] result = new com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVPassenger[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "passengers" element
     */
    public com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVPassenger getPassengersArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVPassenger target = null;
            target = (com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVPassenger)get_store().find_element_user(PASSENGERS$14, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Tests for nil ith "passengers" element
     */
    public boolean isNilPassengersArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVPassenger target = null;
            target = (com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVPassenger)get_store().find_element_user(PASSENGERS$14, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target.isNil();
        }
    }
    
    /**
     * Returns number of "passengers" element
     */
    public int sizeOfPassengersArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(PASSENGERS$14);
        }
    }
    
    /**
     * Sets array of all "passengers" element
     */
    public void setPassengersArray(com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVPassenger[] passengersArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(passengersArray, PASSENGERS$14);
        }
    }
    
    /**
     * Sets ith "passengers" element
     */
    public void setPassengersArray(int i, com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVPassenger passengers)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVPassenger target = null;
            target = (com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVPassenger)get_store().find_element_user(PASSENGERS$14, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(passengers);
        }
    }
    
    /**
     * Nils the ith "passengers" element
     */
    public void setNilPassengersArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVPassenger target = null;
            target = (com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVPassenger)get_store().find_element_user(PASSENGERS$14, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.setNil();
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "passengers" element
     */
    public com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVPassenger insertNewPassengers(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVPassenger target = null;
            target = (com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVPassenger)get_store().insert_element_user(PASSENGERS$14, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "passengers" element
     */
    public com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVPassenger addNewPassengers()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVPassenger target = null;
            target = (com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVPassenger)get_store().add_element_user(PASSENGERS$14);
            return target;
        }
    }
    
    /**
     * Removes the ith "passengers" element
     */
    public void removePassengers(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(PASSENGERS$14, i);
        }
    }
    
    /**
     * Gets array of all "paxClaimchecks" elements
     */
    public com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVClaimChecks[] getPaxClaimchecksArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(PAXCLAIMCHECKS$16, targetList);
            com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVClaimChecks[] result = new com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVClaimChecks[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "paxClaimchecks" element
     */
    public com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVClaimChecks getPaxClaimchecksArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVClaimChecks target = null;
            target = (com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVClaimChecks)get_store().find_element_user(PAXCLAIMCHECKS$16, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Tests for nil ith "paxClaimchecks" element
     */
    public boolean isNilPaxClaimchecksArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVClaimChecks target = null;
            target = (com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVClaimChecks)get_store().find_element_user(PAXCLAIMCHECKS$16, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target.isNil();
        }
    }
    
    /**
     * Returns number of "paxClaimchecks" element
     */
    public int sizeOfPaxClaimchecksArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(PAXCLAIMCHECKS$16);
        }
    }
    
    /**
     * Sets array of all "paxClaimchecks" element
     */
    public void setPaxClaimchecksArray(com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVClaimChecks[] paxClaimchecksArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(paxClaimchecksArray, PAXCLAIMCHECKS$16);
        }
    }
    
    /**
     * Sets ith "paxClaimchecks" element
     */
    public void setPaxClaimchecksArray(int i, com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVClaimChecks paxClaimchecks)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVClaimChecks target = null;
            target = (com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVClaimChecks)get_store().find_element_user(PAXCLAIMCHECKS$16, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(paxClaimchecks);
        }
    }
    
    /**
     * Nils the ith "paxClaimchecks" element
     */
    public void setNilPaxClaimchecksArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVClaimChecks target = null;
            target = (com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVClaimChecks)get_store().find_element_user(PAXCLAIMCHECKS$16, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.setNil();
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "paxClaimchecks" element
     */
    public com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVClaimChecks insertNewPaxClaimchecks(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVClaimChecks target = null;
            target = (com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVClaimChecks)get_store().insert_element_user(PAXCLAIMCHECKS$16, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "paxClaimchecks" element
     */
    public com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVClaimChecks addNewPaxClaimchecks()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVClaimChecks target = null;
            target = (com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVClaimChecks)get_store().add_element_user(PAXCLAIMCHECKS$16);
            return target;
        }
    }
    
    /**
     * Removes the ith "paxClaimchecks" element
     */
    public void removePaxClaimchecks(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(PAXCLAIMCHECKS$16, i);
        }
    }
    
    /**
     * Gets array of all "paxCommunication" elements
     */
    public com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVPaxCommunication[] getPaxCommunicationArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(PAXCOMMUNICATION$18, targetList);
            com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVPaxCommunication[] result = new com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVPaxCommunication[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "paxCommunication" element
     */
    public com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVPaxCommunication getPaxCommunicationArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVPaxCommunication target = null;
            target = (com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVPaxCommunication)get_store().find_element_user(PAXCOMMUNICATION$18, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Tests for nil ith "paxCommunication" element
     */
    public boolean isNilPaxCommunicationArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVPaxCommunication target = null;
            target = (com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVPaxCommunication)get_store().find_element_user(PAXCOMMUNICATION$18, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target.isNil();
        }
    }
    
    /**
     * Returns number of "paxCommunication" element
     */
    public int sizeOfPaxCommunicationArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(PAXCOMMUNICATION$18);
        }
    }
    
    /**
     * Sets array of all "paxCommunication" element
     */
    public void setPaxCommunicationArray(com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVPaxCommunication[] paxCommunicationArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(paxCommunicationArray, PAXCOMMUNICATION$18);
        }
    }
    
    /**
     * Sets ith "paxCommunication" element
     */
    public void setPaxCommunicationArray(int i, com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVPaxCommunication paxCommunication)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVPaxCommunication target = null;
            target = (com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVPaxCommunication)get_store().find_element_user(PAXCOMMUNICATION$18, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(paxCommunication);
        }
    }
    
    /**
     * Nils the ith "paxCommunication" element
     */
    public void setNilPaxCommunicationArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVPaxCommunication target = null;
            target = (com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVPaxCommunication)get_store().find_element_user(PAXCOMMUNICATION$18, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.setNil();
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "paxCommunication" element
     */
    public com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVPaxCommunication insertNewPaxCommunication(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVPaxCommunication target = null;
            target = (com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVPaxCommunication)get_store().insert_element_user(PAXCOMMUNICATION$18, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "paxCommunication" element
     */
    public com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVPaxCommunication addNewPaxCommunication()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVPaxCommunication target = null;
            target = (com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVPaxCommunication)get_store().add_element_user(PAXCOMMUNICATION$18);
            return target;
        }
    }
    
    /**
     * Removes the ith "paxCommunication" element
     */
    public void removePaxCommunication(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(PAXCOMMUNICATION$18, i);
        }
    }
}
