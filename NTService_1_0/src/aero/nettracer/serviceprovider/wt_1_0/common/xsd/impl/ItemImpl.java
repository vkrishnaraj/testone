/*
 * XML Type:  Item
 * Namespace: http://common.wt_1_0.serviceprovider.nettracer.aero/xsd
 * Java type: aero.nettracer.serviceprovider.wt_1_0.common.xsd.Item
 *
 * Automatically generated - do not modify.
 */
package aero.nettracer.serviceprovider.wt_1_0.common.xsd.impl;
/**
 * An XML Item(@http://common.wt_1_0.serviceprovider.nettracer.aero/xsd).
 *
 * This is a complex type.
 */
public class ItemImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aero.nettracer.serviceprovider.wt_1_0.common.xsd.Item
{
    
    public ItemImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName BRAND$0 = 
        new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd", "brand");
    private static final javax.xml.namespace.QName COLOR$2 = 
        new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd", "color");
    private static final javax.xml.namespace.QName CONTENT$4 = 
        new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd", "content");
    private static final javax.xml.namespace.QName DESC1$6 = 
        new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd", "desc1");
    private static final javax.xml.namespace.QName DESC2$8 = 
        new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd", "desc2");
    private static final javax.xml.namespace.QName DESC3$10 = 
        new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd", "desc3");
    private static final javax.xml.namespace.QName FIRSTNAMEONBAG$12 = 
        new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd", "firstNameOnBag");
    private static final javax.xml.namespace.QName LASTNAMEONBAG$14 = 
        new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd", "lastNameOnBag");
    private static final javax.xml.namespace.QName TYPE$16 = 
        new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd", "type");
    
    
    /**
     * Gets the "brand" element
     */
    public java.lang.String getBrand()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(BRAND$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "brand" element
     */
    public org.apache.xmlbeans.XmlString xgetBrand()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(BRAND$0, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "brand" element
     */
    public boolean isNilBrand()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(BRAND$0, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "brand" element
     */
    public boolean isSetBrand()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(BRAND$0) != 0;
        }
    }
    
    /**
     * Sets the "brand" element
     */
    public void setBrand(java.lang.String brand)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(BRAND$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(BRAND$0);
            }
            target.setStringValue(brand);
        }
    }
    
    /**
     * Sets (as xml) the "brand" element
     */
    public void xsetBrand(org.apache.xmlbeans.XmlString brand)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(BRAND$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(BRAND$0);
            }
            target.set(brand);
        }
    }
    
    /**
     * Nils the "brand" element
     */
    public void setNilBrand()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(BRAND$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(BRAND$0);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "brand" element
     */
    public void unsetBrand()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(BRAND$0, 0);
        }
    }
    
    /**
     * Gets the "color" element
     */
    public java.lang.String getColor()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COLOR$2, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "color" element
     */
    public org.apache.xmlbeans.XmlString xgetColor()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COLOR$2, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "color" element
     */
    public boolean isNilColor()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COLOR$2, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "color" element
     */
    public boolean isSetColor()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(COLOR$2) != 0;
        }
    }
    
    /**
     * Sets the "color" element
     */
    public void setColor(java.lang.String color)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COLOR$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(COLOR$2);
            }
            target.setStringValue(color);
        }
    }
    
    /**
     * Sets (as xml) the "color" element
     */
    public void xsetColor(org.apache.xmlbeans.XmlString color)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COLOR$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(COLOR$2);
            }
            target.set(color);
        }
    }
    
    /**
     * Nils the "color" element
     */
    public void setNilColor()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COLOR$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(COLOR$2);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "color" element
     */
    public void unsetColor()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(COLOR$2, 0);
        }
    }
    
    /**
     * Gets array of all "content" elements
     */
    public aero.nettracer.serviceprovider.wt_1_0.common.xsd.Content[] getContentArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(CONTENT$4, targetList);
            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Content[] result = new aero.nettracer.serviceprovider.wt_1_0.common.xsd.Content[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "content" element
     */
    public aero.nettracer.serviceprovider.wt_1_0.common.xsd.Content getContentArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Content target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Content)get_store().find_element_user(CONTENT$4, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Tests for nil ith "content" element
     */
    public boolean isNilContentArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Content target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Content)get_store().find_element_user(CONTENT$4, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target.isNil();
        }
    }
    
    /**
     * Returns number of "content" element
     */
    public int sizeOfContentArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CONTENT$4);
        }
    }
    
    /**
     * Sets array of all "content" element
     */
    public void setContentArray(aero.nettracer.serviceprovider.wt_1_0.common.xsd.Content[] contentArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(contentArray, CONTENT$4);
        }
    }
    
    /**
     * Sets ith "content" element
     */
    public void setContentArray(int i, aero.nettracer.serviceprovider.wt_1_0.common.xsd.Content content)
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Content target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Content)get_store().find_element_user(CONTENT$4, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(content);
        }
    }
    
    /**
     * Nils the ith "content" element
     */
    public void setNilContentArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Content target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Content)get_store().find_element_user(CONTENT$4, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.setNil();
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "content" element
     */
    public aero.nettracer.serviceprovider.wt_1_0.common.xsd.Content insertNewContent(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Content target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Content)get_store().insert_element_user(CONTENT$4, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "content" element
     */
    public aero.nettracer.serviceprovider.wt_1_0.common.xsd.Content addNewContent()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Content target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Content)get_store().add_element_user(CONTENT$4);
            return target;
        }
    }
    
    /**
     * Removes the ith "content" element
     */
    public void removeContent(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CONTENT$4, i);
        }
    }
    
    /**
     * Gets the "desc1" element
     */
    public java.lang.String getDesc1()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DESC1$6, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "desc1" element
     */
    public org.apache.xmlbeans.XmlString xgetDesc1()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DESC1$6, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "desc1" element
     */
    public boolean isNilDesc1()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DESC1$6, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "desc1" element
     */
    public boolean isSetDesc1()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DESC1$6) != 0;
        }
    }
    
    /**
     * Sets the "desc1" element
     */
    public void setDesc1(java.lang.String desc1)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DESC1$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DESC1$6);
            }
            target.setStringValue(desc1);
        }
    }
    
    /**
     * Sets (as xml) the "desc1" element
     */
    public void xsetDesc1(org.apache.xmlbeans.XmlString desc1)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DESC1$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DESC1$6);
            }
            target.set(desc1);
        }
    }
    
    /**
     * Nils the "desc1" element
     */
    public void setNilDesc1()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DESC1$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DESC1$6);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "desc1" element
     */
    public void unsetDesc1()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DESC1$6, 0);
        }
    }
    
    /**
     * Gets the "desc2" element
     */
    public java.lang.String getDesc2()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DESC2$8, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "desc2" element
     */
    public org.apache.xmlbeans.XmlString xgetDesc2()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DESC2$8, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "desc2" element
     */
    public boolean isNilDesc2()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DESC2$8, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "desc2" element
     */
    public boolean isSetDesc2()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DESC2$8) != 0;
        }
    }
    
    /**
     * Sets the "desc2" element
     */
    public void setDesc2(java.lang.String desc2)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DESC2$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DESC2$8);
            }
            target.setStringValue(desc2);
        }
    }
    
    /**
     * Sets (as xml) the "desc2" element
     */
    public void xsetDesc2(org.apache.xmlbeans.XmlString desc2)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DESC2$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DESC2$8);
            }
            target.set(desc2);
        }
    }
    
    /**
     * Nils the "desc2" element
     */
    public void setNilDesc2()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DESC2$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DESC2$8);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "desc2" element
     */
    public void unsetDesc2()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DESC2$8, 0);
        }
    }
    
    /**
     * Gets the "desc3" element
     */
    public java.lang.String getDesc3()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DESC3$10, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "desc3" element
     */
    public org.apache.xmlbeans.XmlString xgetDesc3()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DESC3$10, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "desc3" element
     */
    public boolean isNilDesc3()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DESC3$10, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "desc3" element
     */
    public boolean isSetDesc3()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DESC3$10) != 0;
        }
    }
    
    /**
     * Sets the "desc3" element
     */
    public void setDesc3(java.lang.String desc3)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DESC3$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DESC3$10);
            }
            target.setStringValue(desc3);
        }
    }
    
    /**
     * Sets (as xml) the "desc3" element
     */
    public void xsetDesc3(org.apache.xmlbeans.XmlString desc3)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DESC3$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DESC3$10);
            }
            target.set(desc3);
        }
    }
    
    /**
     * Nils the "desc3" element
     */
    public void setNilDesc3()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DESC3$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DESC3$10);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "desc3" element
     */
    public void unsetDesc3()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DESC3$10, 0);
        }
    }
    
    /**
     * Gets the "firstNameOnBag" element
     */
    public java.lang.String getFirstNameOnBag()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FIRSTNAMEONBAG$12, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "firstNameOnBag" element
     */
    public org.apache.xmlbeans.XmlString xgetFirstNameOnBag()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FIRSTNAMEONBAG$12, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "firstNameOnBag" element
     */
    public boolean isNilFirstNameOnBag()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FIRSTNAMEONBAG$12, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "firstNameOnBag" element
     */
    public boolean isSetFirstNameOnBag()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(FIRSTNAMEONBAG$12) != 0;
        }
    }
    
    /**
     * Sets the "firstNameOnBag" element
     */
    public void setFirstNameOnBag(java.lang.String firstNameOnBag)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FIRSTNAMEONBAG$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FIRSTNAMEONBAG$12);
            }
            target.setStringValue(firstNameOnBag);
        }
    }
    
    /**
     * Sets (as xml) the "firstNameOnBag" element
     */
    public void xsetFirstNameOnBag(org.apache.xmlbeans.XmlString firstNameOnBag)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FIRSTNAMEONBAG$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(FIRSTNAMEONBAG$12);
            }
            target.set(firstNameOnBag);
        }
    }
    
    /**
     * Nils the "firstNameOnBag" element
     */
    public void setNilFirstNameOnBag()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FIRSTNAMEONBAG$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(FIRSTNAMEONBAG$12);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "firstNameOnBag" element
     */
    public void unsetFirstNameOnBag()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(FIRSTNAMEONBAG$12, 0);
        }
    }
    
    /**
     * Gets the "lastNameOnBag" element
     */
    public java.lang.String getLastNameOnBag()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LASTNAMEONBAG$14, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "lastNameOnBag" element
     */
    public org.apache.xmlbeans.XmlString xgetLastNameOnBag()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(LASTNAMEONBAG$14, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "lastNameOnBag" element
     */
    public boolean isNilLastNameOnBag()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(LASTNAMEONBAG$14, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "lastNameOnBag" element
     */
    public boolean isSetLastNameOnBag()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(LASTNAMEONBAG$14) != 0;
        }
    }
    
    /**
     * Sets the "lastNameOnBag" element
     */
    public void setLastNameOnBag(java.lang.String lastNameOnBag)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LASTNAMEONBAG$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(LASTNAMEONBAG$14);
            }
            target.setStringValue(lastNameOnBag);
        }
    }
    
    /**
     * Sets (as xml) the "lastNameOnBag" element
     */
    public void xsetLastNameOnBag(org.apache.xmlbeans.XmlString lastNameOnBag)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(LASTNAMEONBAG$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(LASTNAMEONBAG$14);
            }
            target.set(lastNameOnBag);
        }
    }
    
    /**
     * Nils the "lastNameOnBag" element
     */
    public void setNilLastNameOnBag()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(LASTNAMEONBAG$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(LASTNAMEONBAG$14);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "lastNameOnBag" element
     */
    public void unsetLastNameOnBag()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(LASTNAMEONBAG$14, 0);
        }
    }
    
    /**
     * Gets the "type" element
     */
    public java.lang.String getType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TYPE$16, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "type" element
     */
    public org.apache.xmlbeans.XmlString xgetType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TYPE$16, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "type" element
     */
    public boolean isNilType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TYPE$16, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "type" element
     */
    public boolean isSetType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(TYPE$16) != 0;
        }
    }
    
    /**
     * Sets the "type" element
     */
    public void setType(java.lang.String type)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TYPE$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(TYPE$16);
            }
            target.setStringValue(type);
        }
    }
    
    /**
     * Sets (as xml) the "type" element
     */
    public void xsetType(org.apache.xmlbeans.XmlString type)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TYPE$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(TYPE$16);
            }
            target.set(type);
        }
    }
    
    /**
     * Nils the "type" element
     */
    public void setNilType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TYPE$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(TYPE$16);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "type" element
     */
    public void unsetType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(TYPE$16, 0);
        }
    }
}
