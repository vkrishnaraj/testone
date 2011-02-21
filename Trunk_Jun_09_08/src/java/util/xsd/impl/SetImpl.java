/*
 * XML Type:  Set
 * Namespace: http://util.java/xsd
 * Java type: java.util.xsd.Set
 *
 * Automatically generated - do not modify.
 */
package java.util.xsd.impl;
/**
 * An XML Set(@http://util.java/xsd).
 *
 * This is a complex type.
 */
public class SetImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements java.util.xsd.Set
{
    
    public SetImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName EMPTY$0 = 
        new javax.xml.namespace.QName("http://util.java/xsd", "empty");
    
    
    /**
     * Gets the "empty" element
     */
    public boolean getEmpty()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EMPTY$0, 0);
            if (target == null)
            {
                return false;
            }
            return target.getBooleanValue();
        }
    }
    
    /**
     * Gets (as xml) the "empty" element
     */
    public org.apache.xmlbeans.XmlBoolean xgetEmpty()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(EMPTY$0, 0);
            return target;
        }
    }
    
    /**
     * True if has "empty" element
     */
    public boolean isSetEmpty()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(EMPTY$0) != 0;
        }
    }
    
    /**
     * Sets the "empty" element
     */
    public void setEmpty(boolean empty)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(EMPTY$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(EMPTY$0);
            }
            target.setBooleanValue(empty);
        }
    }
    
    /**
     * Sets (as xml) the "empty" element
     */
    public void xsetEmpty(org.apache.xmlbeans.XmlBoolean empty)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(EMPTY$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlBoolean)get_store().add_element_user(EMPTY$0);
            }
            target.set(empty);
        }
    }
    
    /**
     * Unsets the "empty" element
     */
    public void unsetEmpty()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(EMPTY$0, 0);
        }
    }
}
