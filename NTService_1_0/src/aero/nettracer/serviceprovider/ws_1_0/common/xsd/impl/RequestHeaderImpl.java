/*
 * XML Type:  RequestHeader
 * Namespace: http://common.ws_1_0.serviceprovider.nettracer.aero/xsd
 * Java type: aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader
 *
 * Automatically generated - do not modify.
 */
package aero.nettracer.serviceprovider.ws_1_0.common.xsd.impl;
/**
 * An XML RequestHeader(@http://common.ws_1_0.serviceprovider.nettracer.aero/xsd).
 *
 * This is a complex type.
 */
public class RequestHeaderImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader
{
    
    public RequestHeaderImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName PARAMETERS$0 = 
        new javax.xml.namespace.QName("http://common.ws_1_0.serviceprovider.nettracer.aero/xsd", "parameters");
    private static final javax.xml.namespace.QName PASSWORD$2 = 
        new javax.xml.namespace.QName("http://common.ws_1_0.serviceprovider.nettracer.aero/xsd", "password");
    private static final javax.xml.namespace.QName USERNAME$4 = 
        new javax.xml.namespace.QName("http://common.ws_1_0.serviceprovider.nettracer.aero/xsd", "username");
    
    
    /**
     * Gets array of all "parameters" elements
     */
    public aero.nettracer.serviceprovider.ws_1_0.common.xsd.Parameter[] getParametersArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(PARAMETERS$0, targetList);
            aero.nettracer.serviceprovider.ws_1_0.common.xsd.Parameter[] result = new aero.nettracer.serviceprovider.ws_1_0.common.xsd.Parameter[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "parameters" element
     */
    public aero.nettracer.serviceprovider.ws_1_0.common.xsd.Parameter getParametersArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.ws_1_0.common.xsd.Parameter target = null;
            target = (aero.nettracer.serviceprovider.ws_1_0.common.xsd.Parameter)get_store().find_element_user(PARAMETERS$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Tests for nil ith "parameters" element
     */
    public boolean isNilParametersArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.ws_1_0.common.xsd.Parameter target = null;
            target = (aero.nettracer.serviceprovider.ws_1_0.common.xsd.Parameter)get_store().find_element_user(PARAMETERS$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target.isNil();
        }
    }
    
    /**
     * Returns number of "parameters" element
     */
    public int sizeOfParametersArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(PARAMETERS$0);
        }
    }
    
    /**
     * Sets array of all "parameters" element
     */
    public void setParametersArray(aero.nettracer.serviceprovider.ws_1_0.common.xsd.Parameter[] parametersArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(parametersArray, PARAMETERS$0);
        }
    }
    
    /**
     * Sets ith "parameters" element
     */
    public void setParametersArray(int i, aero.nettracer.serviceprovider.ws_1_0.common.xsd.Parameter parameters)
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.ws_1_0.common.xsd.Parameter target = null;
            target = (aero.nettracer.serviceprovider.ws_1_0.common.xsd.Parameter)get_store().find_element_user(PARAMETERS$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(parameters);
        }
    }
    
    /**
     * Nils the ith "parameters" element
     */
    public void setNilParametersArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.ws_1_0.common.xsd.Parameter target = null;
            target = (aero.nettracer.serviceprovider.ws_1_0.common.xsd.Parameter)get_store().find_element_user(PARAMETERS$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.setNil();
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "parameters" element
     */
    public aero.nettracer.serviceprovider.ws_1_0.common.xsd.Parameter insertNewParameters(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.ws_1_0.common.xsd.Parameter target = null;
            target = (aero.nettracer.serviceprovider.ws_1_0.common.xsd.Parameter)get_store().insert_element_user(PARAMETERS$0, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "parameters" element
     */
    public aero.nettracer.serviceprovider.ws_1_0.common.xsd.Parameter addNewParameters()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.ws_1_0.common.xsd.Parameter target = null;
            target = (aero.nettracer.serviceprovider.ws_1_0.common.xsd.Parameter)get_store().add_element_user(PARAMETERS$0);
            return target;
        }
    }
    
    /**
     * Removes the ith "parameters" element
     */
    public void removeParameters(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(PARAMETERS$0, i);
        }
    }
    
    /**
     * Gets the "password" element
     */
    public java.lang.String getPassword()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PASSWORD$2, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "password" element
     */
    public org.apache.xmlbeans.XmlString xgetPassword()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PASSWORD$2, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "password" element
     */
    public boolean isNilPassword()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PASSWORD$2, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "password" element
     */
    public boolean isSetPassword()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(PASSWORD$2) != 0;
        }
    }
    
    /**
     * Sets the "password" element
     */
    public void setPassword(java.lang.String password)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PASSWORD$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PASSWORD$2);
            }
            target.setStringValue(password);
        }
    }
    
    /**
     * Sets (as xml) the "password" element
     */
    public void xsetPassword(org.apache.xmlbeans.XmlString password)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PASSWORD$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PASSWORD$2);
            }
            target.set(password);
        }
    }
    
    /**
     * Nils the "password" element
     */
    public void setNilPassword()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PASSWORD$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PASSWORD$2);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "password" element
     */
    public void unsetPassword()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(PASSWORD$2, 0);
        }
    }
    
    /**
     * Gets the "username" element
     */
    public java.lang.String getUsername()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(USERNAME$4, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "username" element
     */
    public org.apache.xmlbeans.XmlString xgetUsername()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(USERNAME$4, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "username" element
     */
    public boolean isNilUsername()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(USERNAME$4, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "username" element
     */
    public boolean isSetUsername()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(USERNAME$4) != 0;
        }
    }
    
    /**
     * Sets the "username" element
     */
    public void setUsername(java.lang.String username)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(USERNAME$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(USERNAME$4);
            }
            target.setStringValue(username);
        }
    }
    
    /**
     * Sets (as xml) the "username" element
     */
    public void xsetUsername(org.apache.xmlbeans.XmlString username)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(USERNAME$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(USERNAME$4);
            }
            target.set(username);
        }
    }
    
    /**
     * Nils the "username" element
     */
    public void setNilUsername()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(USERNAME$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(USERNAME$4);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "username" element
     */
    public void unsetUsername()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(USERNAME$4, 0);
        }
    }
}
