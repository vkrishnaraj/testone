/*
 * XML Type:  UserGroup
 * Namespace: http://db.tracing.nettracer.bagnet.com/xsd
 * Java type: com.bagnet.nettracer.tracing.db.xsd.UserGroup
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.tracing.db.xsd.impl;
/**
 * An XML UserGroup(@http://db.tracing.nettracer.bagnet.com/xsd).
 *
 * This is a complex type.
 */
public class UserGroupImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.tracing.db.xsd.UserGroup
{
    
    public UserGroupImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName COMPANYCODEID$0 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "companycode_ID");
    private static final javax.xml.namespace.QName COMPONENTPOLICIES$2 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "componentPolicies");
    private static final javax.xml.namespace.QName DESCRIPTION$4 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "description");
    private static final javax.xml.namespace.QName DESCRIPTION2$6 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "description2");
    private static final javax.xml.namespace.QName PERMISSIONLINKMAP$8 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "permissionLinkMap");
    private static final javax.xml.namespace.QName PERMISSIONNAMEMAP$10 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "permissionNameMap");
    private static final javax.xml.namespace.QName USERGROUPID$12 = 
        new javax.xml.namespace.QName("http://db.tracing.nettracer.bagnet.com/xsd", "userGroup_ID");
    
    
    /**
     * Gets the "companycode_ID" element
     */
    public java.lang.String getCompanycodeID()
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
     * Gets (as xml) the "companycode_ID" element
     */
    public org.apache.xmlbeans.XmlString xgetCompanycodeID()
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
     * Tests for nil "companycode_ID" element
     */
    public boolean isNilCompanycodeID()
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
     * True if has "companycode_ID" element
     */
    public boolean isSetCompanycodeID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(COMPANYCODEID$0) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COMPANYCODEID$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(COMPANYCODEID$0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COMPANYCODEID$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(COMPANYCODEID$0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COMPANYCODEID$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(COMPANYCODEID$0);
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
            get_store().remove_element(COMPANYCODEID$0, 0);
        }
    }
    
    /**
     * Gets the "componentPolicies" element
     */
    public java.util.xsd.Set getComponentPolicies()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.xsd.Set target = null;
            target = (java.util.xsd.Set)get_store().find_element_user(COMPONENTPOLICIES$2, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Tests for nil "componentPolicies" element
     */
    public boolean isNilComponentPolicies()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.xsd.Set target = null;
            target = (java.util.xsd.Set)get_store().find_element_user(COMPONENTPOLICIES$2, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "componentPolicies" element
     */
    public boolean isSetComponentPolicies()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(COMPONENTPOLICIES$2) != 0;
        }
    }
    
    /**
     * Sets the "componentPolicies" element
     */
    public void setComponentPolicies(java.util.xsd.Set componentPolicies)
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.xsd.Set target = null;
            target = (java.util.xsd.Set)get_store().find_element_user(COMPONENTPOLICIES$2, 0);
            if (target == null)
            {
                target = (java.util.xsd.Set)get_store().add_element_user(COMPONENTPOLICIES$2);
            }
            target.set(componentPolicies);
        }
    }
    
    /**
     * Appends and returns a new empty "componentPolicies" element
     */
    public java.util.xsd.Set addNewComponentPolicies()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.xsd.Set target = null;
            target = (java.util.xsd.Set)get_store().add_element_user(COMPONENTPOLICIES$2);
            return target;
        }
    }
    
    /**
     * Nils the "componentPolicies" element
     */
    public void setNilComponentPolicies()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.xsd.Set target = null;
            target = (java.util.xsd.Set)get_store().find_element_user(COMPONENTPOLICIES$2, 0);
            if (target == null)
            {
                target = (java.util.xsd.Set)get_store().add_element_user(COMPONENTPOLICIES$2);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "componentPolicies" element
     */
    public void unsetComponentPolicies()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(COMPONENTPOLICIES$2, 0);
        }
    }
    
    /**
     * Gets the "description" element
     */
    public java.lang.String getDescription()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DESCRIPTION$4, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DESCRIPTION$4, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DESCRIPTION$4, 0);
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
            return get_store().count_elements(DESCRIPTION$4) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DESCRIPTION$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DESCRIPTION$4);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DESCRIPTION$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DESCRIPTION$4);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DESCRIPTION$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DESCRIPTION$4);
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
            get_store().remove_element(DESCRIPTION$4, 0);
        }
    }
    
    /**
     * Gets the "description2" element
     */
    public java.lang.String getDescription2()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DESCRIPTION2$6, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "description2" element
     */
    public org.apache.xmlbeans.XmlString xgetDescription2()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DESCRIPTION2$6, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "description2" element
     */
    public boolean isNilDescription2()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DESCRIPTION2$6, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "description2" element
     */
    public boolean isSetDescription2()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DESCRIPTION2$6) != 0;
        }
    }
    
    /**
     * Sets the "description2" element
     */
    public void setDescription2(java.lang.String description2)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DESCRIPTION2$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DESCRIPTION2$6);
            }
            target.setStringValue(description2);
        }
    }
    
    /**
     * Sets (as xml) the "description2" element
     */
    public void xsetDescription2(org.apache.xmlbeans.XmlString description2)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DESCRIPTION2$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DESCRIPTION2$6);
            }
            target.set(description2);
        }
    }
    
    /**
     * Nils the "description2" element
     */
    public void setNilDescription2()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DESCRIPTION2$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DESCRIPTION2$6);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "description2" element
     */
    public void unsetDescription2()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DESCRIPTION2$6, 0);
        }
    }
    
    /**
     * Gets the "permissionLinkMap" element
     */
    public org.apache.xmlbeans.XmlObject getPermissionLinkMap()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlObject target = null;
            target = (org.apache.xmlbeans.XmlObject)get_store().find_element_user(PERMISSIONLINKMAP$8, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Tests for nil "permissionLinkMap" element
     */
    public boolean isNilPermissionLinkMap()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlObject target = null;
            target = (org.apache.xmlbeans.XmlObject)get_store().find_element_user(PERMISSIONLINKMAP$8, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "permissionLinkMap" element
     */
    public boolean isSetPermissionLinkMap()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(PERMISSIONLINKMAP$8) != 0;
        }
    }
    
    /**
     * Sets the "permissionLinkMap" element
     */
    public void setPermissionLinkMap(org.apache.xmlbeans.XmlObject permissionLinkMap)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlObject target = null;
            target = (org.apache.xmlbeans.XmlObject)get_store().find_element_user(PERMISSIONLINKMAP$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlObject)get_store().add_element_user(PERMISSIONLINKMAP$8);
            }
            target.set(permissionLinkMap);
        }
    }
    
    /**
     * Appends and returns a new empty "permissionLinkMap" element
     */
    public org.apache.xmlbeans.XmlObject addNewPermissionLinkMap()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlObject target = null;
            target = (org.apache.xmlbeans.XmlObject)get_store().add_element_user(PERMISSIONLINKMAP$8);
            return target;
        }
    }
    
    /**
     * Nils the "permissionLinkMap" element
     */
    public void setNilPermissionLinkMap()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlObject target = null;
            target = (org.apache.xmlbeans.XmlObject)get_store().find_element_user(PERMISSIONLINKMAP$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlObject)get_store().add_element_user(PERMISSIONLINKMAP$8);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "permissionLinkMap" element
     */
    public void unsetPermissionLinkMap()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(PERMISSIONLINKMAP$8, 0);
        }
    }
    
    /**
     * Gets the "permissionNameMap" element
     */
    public org.apache.xmlbeans.XmlObject getPermissionNameMap()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlObject target = null;
            target = (org.apache.xmlbeans.XmlObject)get_store().find_element_user(PERMISSIONNAMEMAP$10, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Tests for nil "permissionNameMap" element
     */
    public boolean isNilPermissionNameMap()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlObject target = null;
            target = (org.apache.xmlbeans.XmlObject)get_store().find_element_user(PERMISSIONNAMEMAP$10, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "permissionNameMap" element
     */
    public boolean isSetPermissionNameMap()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(PERMISSIONNAMEMAP$10) != 0;
        }
    }
    
    /**
     * Sets the "permissionNameMap" element
     */
    public void setPermissionNameMap(org.apache.xmlbeans.XmlObject permissionNameMap)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlObject target = null;
            target = (org.apache.xmlbeans.XmlObject)get_store().find_element_user(PERMISSIONNAMEMAP$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlObject)get_store().add_element_user(PERMISSIONNAMEMAP$10);
            }
            target.set(permissionNameMap);
        }
    }
    
    /**
     * Appends and returns a new empty "permissionNameMap" element
     */
    public org.apache.xmlbeans.XmlObject addNewPermissionNameMap()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlObject target = null;
            target = (org.apache.xmlbeans.XmlObject)get_store().add_element_user(PERMISSIONNAMEMAP$10);
            return target;
        }
    }
    
    /**
     * Nils the "permissionNameMap" element
     */
    public void setNilPermissionNameMap()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlObject target = null;
            target = (org.apache.xmlbeans.XmlObject)get_store().find_element_user(PERMISSIONNAMEMAP$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlObject)get_store().add_element_user(PERMISSIONNAMEMAP$10);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "permissionNameMap" element
     */
    public void unsetPermissionNameMap()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(PERMISSIONNAMEMAP$10, 0);
        }
    }
    
    /**
     * Gets the "userGroup_ID" element
     */
    public int getUserGroupID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(USERGROUPID$12, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "userGroup_ID" element
     */
    public org.apache.xmlbeans.XmlInt xgetUserGroupID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(USERGROUPID$12, 0);
            return target;
        }
    }
    
    /**
     * True if has "userGroup_ID" element
     */
    public boolean isSetUserGroupID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(USERGROUPID$12) != 0;
        }
    }
    
    /**
     * Sets the "userGroup_ID" element
     */
    public void setUserGroupID(int userGroupID)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(USERGROUPID$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(USERGROUPID$12);
            }
            target.setIntValue(userGroupID);
        }
    }
    
    /**
     * Sets (as xml) the "userGroup_ID" element
     */
    public void xsetUserGroupID(org.apache.xmlbeans.XmlInt userGroupID)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(USERGROUPID$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(USERGROUPID$12);
            }
            target.set(userGroupID);
        }
    }
    
    /**
     * Unsets the "userGroup_ID" element
     */
    public void unsetUserGroupID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(USERGROUPID$12, 0);
        }
    }
}
