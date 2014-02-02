/*
 * XML Type:  WS_PVAdvancedIncident
 * Namespace: http://pojo.core.ws.nettracer.bagnet.com/xsd
 * Java type: com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.core.pojo.xsd.impl;
/**
 * An XML WS_PVAdvancedIncident(@http://pojo.core.ws.nettracer.bagnet.com/xsd).
 *
 * This is a complex type.
 */
public class WSPVAdvancedIncidentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident
{
    private static final long serialVersionUID = 1L;
    
    public WSPVAdvancedIncidentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName CANCREATECLAIM$0 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "canCreateClaim");
    private static final javax.xml.namespace.QName CLAIMCHECKS$2 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "claimChecks");
    private static final javax.xml.namespace.QName CLOSEDATE$4 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "closedate");
    private static final javax.xml.namespace.QName COMMENTS$6 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "comments");
    private static final javax.xml.namespace.QName COMPANYCODEID$8 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "companycode_id");
    private static final javax.xml.namespace.QName CREATEDATE$10 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "createdate");
    private static final javax.xml.namespace.QName DISPCREATETIME$12 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "dispcreatetime");
    private static final javax.xml.namespace.QName ERRORCODE$14 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "errorcode");
    private static final javax.xml.namespace.QName INCIDENTID$16 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "incident_ID");
    private static final javax.xml.namespace.QName INCIDENTSTATUS$18 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "incident_status");
    private static final javax.xml.namespace.QName ITEMTYPE$20 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "itemType");
    private static final javax.xml.namespace.QName ITEMS$22 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "items");
    private static final javax.xml.namespace.QName PASSENGERS$24 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "passengers");
    
    
    /**
     * Gets the "canCreateClaim" element
     */
    public boolean getCanCreateClaim()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CANCREATECLAIM$0, 0);
            if (target == null)
            {
                return false;
            }
            return target.getBooleanValue();
        }
    }
    
    /**
     * Gets (as xml) the "canCreateClaim" element
     */
    public org.apache.xmlbeans.XmlBoolean xgetCanCreateClaim()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(CANCREATECLAIM$0, 0);
            return target;
        }
    }
    
    /**
     * True if has "canCreateClaim" element
     */
    public boolean isSetCanCreateClaim()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CANCREATECLAIM$0) != 0;
        }
    }
    
    /**
     * Sets the "canCreateClaim" element
     */
    public void setCanCreateClaim(boolean canCreateClaim)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CANCREATECLAIM$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CANCREATECLAIM$0);
            }
            target.setBooleanValue(canCreateClaim);
        }
    }
    
    /**
     * Sets (as xml) the "canCreateClaim" element
     */
    public void xsetCanCreateClaim(org.apache.xmlbeans.XmlBoolean canCreateClaim)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(CANCREATECLAIM$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlBoolean)get_store().add_element_user(CANCREATECLAIM$0);
            }
            target.set(canCreateClaim);
        }
    }
    
    /**
     * Unsets the "canCreateClaim" element
     */
    public void unsetCanCreateClaim()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CANCREATECLAIM$0, 0);
        }
    }
    
    /**
     * Gets array of all "claimChecks" elements
     */
    public com.bagnet.nettracer.ws.core.pojo.xsd.WSClaimCheck[] getClaimChecksArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(CLAIMCHECKS$2, targetList);
            com.bagnet.nettracer.ws.core.pojo.xsd.WSClaimCheck[] result = new com.bagnet.nettracer.ws.core.pojo.xsd.WSClaimCheck[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "claimChecks" element
     */
    public com.bagnet.nettracer.ws.core.pojo.xsd.WSClaimCheck getClaimChecksArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.pojo.xsd.WSClaimCheck target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSClaimCheck)get_store().find_element_user(CLAIMCHECKS$2, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Tests for nil ith "claimChecks" element
     */
    public boolean isNilClaimChecksArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.pojo.xsd.WSClaimCheck target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSClaimCheck)get_store().find_element_user(CLAIMCHECKS$2, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target.isNil();
        }
    }
    
    /**
     * Returns number of "claimChecks" element
     */
    public int sizeOfClaimChecksArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CLAIMCHECKS$2);
        }
    }
    
    /**
     * Sets array of all "claimChecks" element  WARNING: This method is not atomicaly synchronized.
     */
    public void setClaimChecksArray(com.bagnet.nettracer.ws.core.pojo.xsd.WSClaimCheck[] claimChecksArray)
    {
        check_orphaned();
        arraySetterHelper(claimChecksArray, CLAIMCHECKS$2);
    }
    
    /**
     * Sets ith "claimChecks" element
     */
    public void setClaimChecksArray(int i, com.bagnet.nettracer.ws.core.pojo.xsd.WSClaimCheck claimChecks)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.pojo.xsd.WSClaimCheck target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSClaimCheck)get_store().find_element_user(CLAIMCHECKS$2, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(claimChecks);
        }
    }
    
    /**
     * Nils the ith "claimChecks" element
     */
    public void setNilClaimChecksArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.pojo.xsd.WSClaimCheck target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSClaimCheck)get_store().find_element_user(CLAIMCHECKS$2, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.setNil();
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "claimChecks" element
     */
    public com.bagnet.nettracer.ws.core.pojo.xsd.WSClaimCheck insertNewClaimChecks(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.pojo.xsd.WSClaimCheck target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSClaimCheck)get_store().insert_element_user(CLAIMCHECKS$2, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "claimChecks" element
     */
    public com.bagnet.nettracer.ws.core.pojo.xsd.WSClaimCheck addNewClaimChecks()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.pojo.xsd.WSClaimCheck target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSClaimCheck)get_store().add_element_user(CLAIMCHECKS$2);
            return target;
        }
    }
    
    /**
     * Removes the ith "claimChecks" element
     */
    public void removeClaimChecks(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CLAIMCHECKS$2, i);
        }
    }
    
    /**
     * Gets the "closedate" element
     */
    public java.util.Calendar getClosedate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CLOSEDATE$4, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "closedate" element
     */
    public org.apache.xmlbeans.XmlDateTime xgetClosedate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(CLOSEDATE$4, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "closedate" element
     */
    public boolean isNilClosedate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(CLOSEDATE$4, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "closedate" element
     */
    public boolean isSetClosedate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CLOSEDATE$4) != 0;
        }
    }
    
    /**
     * Sets the "closedate" element
     */
    public void setClosedate(java.util.Calendar closedate)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CLOSEDATE$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CLOSEDATE$4);
            }
            target.setCalendarValue(closedate);
        }
    }
    
    /**
     * Sets (as xml) the "closedate" element
     */
    public void xsetClosedate(org.apache.xmlbeans.XmlDateTime closedate)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(CLOSEDATE$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(CLOSEDATE$4);
            }
            target.set(closedate);
        }
    }
    
    /**
     * Nils the "closedate" element
     */
    public void setNilClosedate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(CLOSEDATE$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(CLOSEDATE$4);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "closedate" element
     */
    public void unsetClosedate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CLOSEDATE$4, 0);
        }
    }
    
    /**
     * Gets the "comments" element
     */
    public java.lang.String getComments()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COMMENTS$6, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COMMENTS$6, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COMMENTS$6, 0);
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
            return get_store().count_elements(COMMENTS$6) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COMMENTS$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(COMMENTS$6);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COMMENTS$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(COMMENTS$6);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COMMENTS$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(COMMENTS$6);
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
            get_store().remove_element(COMMENTS$6, 0);
        }
    }
    
    /**
     * Gets the "companycode_id" element
     */
    public java.lang.String getCompanycodeId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COMPANYCODEID$8, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COMPANYCODEID$8, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COMPANYCODEID$8, 0);
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
            return get_store().count_elements(COMPANYCODEID$8) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COMPANYCODEID$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(COMPANYCODEID$8);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COMPANYCODEID$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(COMPANYCODEID$8);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COMPANYCODEID$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(COMPANYCODEID$8);
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
            get_store().remove_element(COMPANYCODEID$8, 0);
        }
    }
    
    /**
     * Gets the "createdate" element
     */
    public java.util.Calendar getCreatedate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CREATEDATE$10, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "createdate" element
     */
    public org.apache.xmlbeans.XmlDateTime xgetCreatedate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(CREATEDATE$10, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "createdate" element
     */
    public boolean isNilCreatedate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(CREATEDATE$10, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "createdate" element
     */
    public boolean isSetCreatedate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CREATEDATE$10) != 0;
        }
    }
    
    /**
     * Sets the "createdate" element
     */
    public void setCreatedate(java.util.Calendar createdate)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CREATEDATE$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CREATEDATE$10);
            }
            target.setCalendarValue(createdate);
        }
    }
    
    /**
     * Sets (as xml) the "createdate" element
     */
    public void xsetCreatedate(org.apache.xmlbeans.XmlDateTime createdate)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(CREATEDATE$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(CREATEDATE$10);
            }
            target.set(createdate);
        }
    }
    
    /**
     * Nils the "createdate" element
     */
    public void setNilCreatedate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime)get_store().find_element_user(CREATEDATE$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDateTime)get_store().add_element_user(CREATEDATE$10);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "createdate" element
     */
    public void unsetCreatedate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CREATEDATE$10, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DISPCREATETIME$12, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DISPCREATETIME$12, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DISPCREATETIME$12, 0);
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
            return get_store().count_elements(DISPCREATETIME$12) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DISPCREATETIME$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DISPCREATETIME$12);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DISPCREATETIME$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DISPCREATETIME$12);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DISPCREATETIME$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DISPCREATETIME$12);
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
            get_store().remove_element(DISPCREATETIME$12, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ERRORCODE$14, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ERRORCODE$14, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ERRORCODE$14, 0);
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
            return get_store().count_elements(ERRORCODE$14) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ERRORCODE$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ERRORCODE$14);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ERRORCODE$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(ERRORCODE$14);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ERRORCODE$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(ERRORCODE$14);
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
            get_store().remove_element(ERRORCODE$14, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(INCIDENTID$16, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(INCIDENTID$16, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(INCIDENTID$16, 0);
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
            return get_store().count_elements(INCIDENTID$16) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(INCIDENTID$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(INCIDENTID$16);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(INCIDENTID$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(INCIDENTID$16);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(INCIDENTID$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(INCIDENTID$16);
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
            get_store().remove_element(INCIDENTID$16, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(INCIDENTSTATUS$18, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(INCIDENTSTATUS$18, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(INCIDENTSTATUS$18, 0);
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
            return get_store().count_elements(INCIDENTSTATUS$18) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(INCIDENTSTATUS$18, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(INCIDENTSTATUS$18);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(INCIDENTSTATUS$18, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(INCIDENTSTATUS$18);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(INCIDENTSTATUS$18, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(INCIDENTSTATUS$18);
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
            get_store().remove_element(INCIDENTSTATUS$18, 0);
        }
    }
    
    /**
     * Gets the "itemType" element
     */
    public int getItemType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ITEMTYPE$20, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "itemType" element
     */
    public org.apache.xmlbeans.XmlInt xgetItemType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(ITEMTYPE$20, 0);
            return target;
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
            return get_store().count_elements(ITEMTYPE$20) != 0;
        }
    }
    
    /**
     * Sets the "itemType" element
     */
    public void setItemType(int itemType)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ITEMTYPE$20, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ITEMTYPE$20);
            }
            target.setIntValue(itemType);
        }
    }
    
    /**
     * Sets (as xml) the "itemType" element
     */
    public void xsetItemType(org.apache.xmlbeans.XmlInt itemType)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(ITEMTYPE$20, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(ITEMTYPE$20);
            }
            target.set(itemType);
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
            get_store().remove_element(ITEMTYPE$20, 0);
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
            get_store().find_all_element_users(ITEMS$22, targetList);
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
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVItem)get_store().find_element_user(ITEMS$22, i);
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
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVItem)get_store().find_element_user(ITEMS$22, i);
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
            return get_store().count_elements(ITEMS$22);
        }
    }
    
    /**
     * Sets array of all "items" element  WARNING: This method is not atomicaly synchronized.
     */
    public void setItemsArray(com.bagnet.nettracer.ws.core.pojo.xsd.WSPVItem[] itemsArray)
    {
        check_orphaned();
        arraySetterHelper(itemsArray, ITEMS$22);
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
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVItem)get_store().find_element_user(ITEMS$22, i);
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
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVItem)get_store().find_element_user(ITEMS$22, i);
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
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVItem)get_store().insert_element_user(ITEMS$22, i);
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
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVItem)get_store().add_element_user(ITEMS$22);
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
            get_store().remove_element(ITEMS$22, i);
        }
    }
    
    /**
     * Gets array of all "passengers" elements
     */
    public com.bagnet.nettracer.ws.core.pojo.xsd.WSPVPassenger[] getPassengersArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(PASSENGERS$24, targetList);
            com.bagnet.nettracer.ws.core.pojo.xsd.WSPVPassenger[] result = new com.bagnet.nettracer.ws.core.pojo.xsd.WSPVPassenger[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "passengers" element
     */
    public com.bagnet.nettracer.ws.core.pojo.xsd.WSPVPassenger getPassengersArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.pojo.xsd.WSPVPassenger target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVPassenger)get_store().find_element_user(PASSENGERS$24, i);
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
            com.bagnet.nettracer.ws.core.pojo.xsd.WSPVPassenger target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVPassenger)get_store().find_element_user(PASSENGERS$24, i);
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
            return get_store().count_elements(PASSENGERS$24);
        }
    }
    
    /**
     * Sets array of all "passengers" element  WARNING: This method is not atomicaly synchronized.
     */
    public void setPassengersArray(com.bagnet.nettracer.ws.core.pojo.xsd.WSPVPassenger[] passengersArray)
    {
        check_orphaned();
        arraySetterHelper(passengersArray, PASSENGERS$24);
    }
    
    /**
     * Sets ith "passengers" element
     */
    public void setPassengersArray(int i, com.bagnet.nettracer.ws.core.pojo.xsd.WSPVPassenger passengers)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.pojo.xsd.WSPVPassenger target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVPassenger)get_store().find_element_user(PASSENGERS$24, i);
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
            com.bagnet.nettracer.ws.core.pojo.xsd.WSPVPassenger target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVPassenger)get_store().find_element_user(PASSENGERS$24, i);
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
    public com.bagnet.nettracer.ws.core.pojo.xsd.WSPVPassenger insertNewPassengers(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.pojo.xsd.WSPVPassenger target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVPassenger)get_store().insert_element_user(PASSENGERS$24, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "passengers" element
     */
    public com.bagnet.nettracer.ws.core.pojo.xsd.WSPVPassenger addNewPassengers()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.pojo.xsd.WSPVPassenger target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVPassenger)get_store().add_element_user(PASSENGERS$24);
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
            get_store().remove_element(PASSENGERS$24, i);
        }
    }
}
