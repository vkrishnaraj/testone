/*
 * XML Type:  Pxf
 * Namespace: http://common.wt_1_0.serviceprovider.nettracer.aero/xsd
 * Java type: aero.nettracer.serviceprovider.wt_1_0.common.xsd.Pxf
 *
 * Automatically generated - do not modify.
 */
package aero.nettracer.serviceprovider.wt_1_0.common.xsd.impl;
/**
 * An XML Pxf(@http://common.wt_1_0.serviceprovider.nettracer.aero/xsd).
 *
 * This is a complex type.
 */
public class PxfImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aero.nettracer.serviceprovider.wt_1_0.common.xsd.Pxf
{
    
    public PxfImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName CONTENT$0 = 
        new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd", "content");
    private static final javax.xml.namespace.QName DESTINATION$2 = 
        new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd", "destination");
    private static final javax.xml.namespace.QName PXFDETAILS$4 = 
        new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd", "pxfDetails");
    private static final javax.xml.namespace.QName TELETYPE$6 = 
        new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd", "teletype");
    
    
    /**
     * Gets the "content" element
     */
    public java.lang.String getContent()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CONTENT$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "content" element
     */
    public org.apache.xmlbeans.XmlString xgetContent()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CONTENT$0, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "content" element
     */
    public boolean isNilContent()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CONTENT$0, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "content" element
     */
    public boolean isSetContent()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CONTENT$0) != 0;
        }
    }
    
    /**
     * Sets the "content" element
     */
    public void setContent(java.lang.String content)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CONTENT$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CONTENT$0);
            }
            target.setStringValue(content);
        }
    }
    
    /**
     * Sets (as xml) the "content" element
     */
    public void xsetContent(org.apache.xmlbeans.XmlString content)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CONTENT$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CONTENT$0);
            }
            target.set(content);
        }
    }
    
    /**
     * Nils the "content" element
     */
    public void setNilContent()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CONTENT$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CONTENT$0);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "content" element
     */
    public void unsetContent()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CONTENT$0, 0);
        }
    }
    
    /**
     * Gets the "destination" element
     */
    public int getDestination()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DESTINATION$2, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "destination" element
     */
    public org.apache.xmlbeans.XmlInt xgetDestination()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(DESTINATION$2, 0);
            return target;
        }
    }
    
    /**
     * True if has "destination" element
     */
    public boolean isSetDestination()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DESTINATION$2) != 0;
        }
    }
    
    /**
     * Sets the "destination" element
     */
    public void setDestination(int destination)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DESTINATION$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DESTINATION$2);
            }
            target.setIntValue(destination);
        }
    }
    
    /**
     * Sets (as xml) the "destination" element
     */
    public void xsetDestination(org.apache.xmlbeans.XmlInt destination)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(DESTINATION$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(DESTINATION$2);
            }
            target.set(destination);
        }
    }
    
    /**
     * Unsets the "destination" element
     */
    public void unsetDestination()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DESTINATION$2, 0);
        }
    }
    
    /**
     * Gets array of all "pxfDetails" elements
     */
    public aero.nettracer.serviceprovider.wt_1_0.common.xsd.PxfAas[] getPxfDetailsArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(PXFDETAILS$4, targetList);
            aero.nettracer.serviceprovider.wt_1_0.common.xsd.PxfAas[] result = new aero.nettracer.serviceprovider.wt_1_0.common.xsd.PxfAas[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "pxfDetails" element
     */
    public aero.nettracer.serviceprovider.wt_1_0.common.xsd.PxfAas getPxfDetailsArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.wt_1_0.common.xsd.PxfAas target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.PxfAas)get_store().find_element_user(PXFDETAILS$4, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Tests for nil ith "pxfDetails" element
     */
    public boolean isNilPxfDetailsArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.wt_1_0.common.xsd.PxfAas target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.PxfAas)get_store().find_element_user(PXFDETAILS$4, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target.isNil();
        }
    }
    
    /**
     * Returns number of "pxfDetails" element
     */
    public int sizeOfPxfDetailsArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(PXFDETAILS$4);
        }
    }
    
    /**
     * Sets array of all "pxfDetails" element
     */
    public void setPxfDetailsArray(aero.nettracer.serviceprovider.wt_1_0.common.xsd.PxfAas[] pxfDetailsArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(pxfDetailsArray, PXFDETAILS$4);
        }
    }
    
    /**
     * Sets ith "pxfDetails" element
     */
    public void setPxfDetailsArray(int i, aero.nettracer.serviceprovider.wt_1_0.common.xsd.PxfAas pxfDetails)
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.wt_1_0.common.xsd.PxfAas target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.PxfAas)get_store().find_element_user(PXFDETAILS$4, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(pxfDetails);
        }
    }
    
    /**
     * Nils the ith "pxfDetails" element
     */
    public void setNilPxfDetailsArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.wt_1_0.common.xsd.PxfAas target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.PxfAas)get_store().find_element_user(PXFDETAILS$4, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.setNil();
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "pxfDetails" element
     */
    public aero.nettracer.serviceprovider.wt_1_0.common.xsd.PxfAas insertNewPxfDetails(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.wt_1_0.common.xsd.PxfAas target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.PxfAas)get_store().insert_element_user(PXFDETAILS$4, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "pxfDetails" element
     */
    public aero.nettracer.serviceprovider.wt_1_0.common.xsd.PxfAas addNewPxfDetails()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.wt_1_0.common.xsd.PxfAas target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.PxfAas)get_store().add_element_user(PXFDETAILS$4);
            return target;
        }
    }
    
    /**
     * Removes the ith "pxfDetails" element
     */
    public void removePxfDetails(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(PXFDETAILS$4, i);
        }
    }
    
    /**
     * Gets array of all "teletype" elements
     */
    public java.lang.String[] getTeletypeArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(TELETYPE$6, targetList);
            java.lang.String[] result = new java.lang.String[targetList.size()];
            for (int i = 0, len = targetList.size() ; i < len ; i++)
                result[i] = ((org.apache.xmlbeans.SimpleValue)targetList.get(i)).getStringValue();
            return result;
        }
    }
    
    /**
     * Gets ith "teletype" element
     */
    public java.lang.String getTeletypeArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TELETYPE$6, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) array of all "teletype" elements
     */
    public org.apache.xmlbeans.XmlString[] xgetTeletypeArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(TELETYPE$6, targetList);
            org.apache.xmlbeans.XmlString[] result = new org.apache.xmlbeans.XmlString[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets (as xml) ith "teletype" element
     */
    public org.apache.xmlbeans.XmlString xgetTeletypeArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TELETYPE$6, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return (org.apache.xmlbeans.XmlString)target;
        }
    }
    
    /**
     * Tests for nil ith "teletype" element
     */
    public boolean isNilTeletypeArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TELETYPE$6, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target.isNil();
        }
    }
    
    /**
     * Returns number of "teletype" element
     */
    public int sizeOfTeletypeArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(TELETYPE$6);
        }
    }
    
    /**
     * Sets array of all "teletype" element
     */
    public void setTeletypeArray(java.lang.String[] teletypeArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(teletypeArray, TELETYPE$6);
        }
    }
    
    /**
     * Sets ith "teletype" element
     */
    public void setTeletypeArray(int i, java.lang.String teletype)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TELETYPE$6, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.setStringValue(teletype);
        }
    }
    
    /**
     * Sets (as xml) array of all "teletype" element
     */
    public void xsetTeletypeArray(org.apache.xmlbeans.XmlString[]teletypeArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(teletypeArray, TELETYPE$6);
        }
    }
    
    /**
     * Sets (as xml) ith "teletype" element
     */
    public void xsetTeletypeArray(int i, org.apache.xmlbeans.XmlString teletype)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TELETYPE$6, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(teletype);
        }
    }
    
    /**
     * Nils the ith "teletype" element
     */
    public void setNilTeletypeArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TELETYPE$6, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.setNil();
        }
    }
    
    /**
     * Inserts the value as the ith "teletype" element
     */
    public void insertTeletype(int i, java.lang.String teletype)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = 
                (org.apache.xmlbeans.SimpleValue)get_store().insert_element_user(TELETYPE$6, i);
            target.setStringValue(teletype);
        }
    }
    
    /**
     * Appends the value as the last "teletype" element
     */
    public void addTeletype(java.lang.String teletype)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(TELETYPE$6);
            target.setStringValue(teletype);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "teletype" element
     */
    public org.apache.xmlbeans.XmlString insertNewTeletype(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().insert_element_user(TELETYPE$6, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "teletype" element
     */
    public org.apache.xmlbeans.XmlString addNewTeletype()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(TELETYPE$6);
            return target;
        }
    }
    
    /**
     * Removes the ith "teletype" element
     */
    public void removeTeletype(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(TELETYPE$6, i);
        }
    }
}
