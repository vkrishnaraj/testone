/*
 * XML Type:  ForwardOhd
 * Namespace: http://common.wt_1_0.serviceprovider.nettracer.aero/xsd
 * Java type: aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardOhd
 *
 * Automatically generated - do not modify.
 */
package aero.nettracer.serviceprovider.wt_1_0.common.xsd.impl;
/**
 * An XML ForwardOhd(@http://common.wt_1_0.serviceprovider.nettracer.aero/xsd).
 *
 * This is a complex type.
 */
public class ForwardOhdImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardOhd
{
    
    public ForwardOhdImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName OHD$0 = 
        new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd", "ohd");
    private static final javax.xml.namespace.QName OHDID$2 = 
        new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd", "ohdId");
    
    
    /**
     * Gets the "ohd" element
     */
    public aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd getOhd()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd)get_store().find_element_user(OHD$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Tests for nil "ohd" element
     */
    public boolean isNilOhd()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd)get_store().find_element_user(OHD$0, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "ohd" element
     */
    public boolean isSetOhd()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(OHD$0) != 0;
        }
    }
    
    /**
     * Sets the "ohd" element
     */
    public void setOhd(aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd ohd)
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd)get_store().find_element_user(OHD$0, 0);
            if (target == null)
            {
                target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd)get_store().add_element_user(OHD$0);
            }
            target.set(ohd);
        }
    }
    
    /**
     * Appends and returns a new empty "ohd" element
     */
    public aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd addNewOhd()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd)get_store().add_element_user(OHD$0);
            return target;
        }
    }
    
    /**
     * Nils the "ohd" element
     */
    public void setNilOhd()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd)get_store().find_element_user(OHD$0, 0);
            if (target == null)
            {
                target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd)get_store().add_element_user(OHD$0);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "ohd" element
     */
    public void unsetOhd()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(OHD$0, 0);
        }
    }
    
    /**
     * Gets the "ohdId" element
     */
    public java.lang.String getOhdId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(OHDID$2, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "ohdId" element
     */
    public org.apache.xmlbeans.XmlString xgetOhdId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(OHDID$2, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "ohdId" element
     */
    public boolean isNilOhdId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(OHDID$2, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "ohdId" element
     */
    public boolean isSetOhdId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(OHDID$2) != 0;
        }
    }
    
    /**
     * Sets the "ohdId" element
     */
    public void setOhdId(java.lang.String ohdId)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(OHDID$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(OHDID$2);
            }
            target.setStringValue(ohdId);
        }
    }
    
    /**
     * Sets (as xml) the "ohdId" element
     */
    public void xsetOhdId(org.apache.xmlbeans.XmlString ohdId)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(OHDID$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(OHDID$2);
            }
            target.set(ohdId);
        }
    }
    
    /**
     * Nils the "ohdId" element
     */
    public void setNilOhdId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(OHDID$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(OHDID$2);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "ohdId" element
     */
    public void unsetOhdId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(OHDID$2, 0);
        }
    }
}
