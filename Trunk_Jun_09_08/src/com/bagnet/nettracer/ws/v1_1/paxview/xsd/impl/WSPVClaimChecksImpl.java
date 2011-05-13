/*
 * XML Type:  WS_PVClaimChecks
 * Namespace: http://paxview.v1_1.ws.nettracer.bagnet.com/xsd
 * Java type: com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVClaimChecks
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.v1_1.paxview.xsd.impl;
/**
 * An XML WS_PVClaimChecks(@http://paxview.v1_1.ws.nettracer.bagnet.com/xsd).
 *
 * This is a complex type.
 */
public class WSPVClaimChecksImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVClaimChecks
{
    
    public WSPVClaimChecksImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName CLAIMCHECK$0 = 
        new javax.xml.namespace.QName("http://paxview.v1_1.ws.nettracer.bagnet.com/xsd", "claimcheck");
    
    
    /**
     * Gets the "claimcheck" element
     */
    public java.lang.String getClaimcheck()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CLAIMCHECK$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "claimcheck" element
     */
    public org.apache.xmlbeans.XmlString xgetClaimcheck()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CLAIMCHECK$0, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "claimcheck" element
     */
    public boolean isNilClaimcheck()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CLAIMCHECK$0, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "claimcheck" element
     */
    public boolean isSetClaimcheck()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CLAIMCHECK$0) != 0;
        }
    }
    
    /**
     * Sets the "claimcheck" element
     */
    public void setClaimcheck(java.lang.String claimcheck)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CLAIMCHECK$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CLAIMCHECK$0);
            }
            target.setStringValue(claimcheck);
        }
    }
    
    /**
     * Sets (as xml) the "claimcheck" element
     */
    public void xsetClaimcheck(org.apache.xmlbeans.XmlString claimcheck)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CLAIMCHECK$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CLAIMCHECK$0);
            }
            target.set(claimcheck);
        }
    }
    
    /**
     * Nils the "claimcheck" element
     */
    public void setNilClaimcheck()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CLAIMCHECK$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CLAIMCHECK$0);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "claimcheck" element
     */
    public void unsetClaimcheck()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CLAIMCHECK$0, 0);
        }
    }
}
