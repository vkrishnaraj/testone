/*
 * XML Type:  WS_QueryResponse
 * Namespace: http://pojo.core.ws.nettracer.bagnet.com/xsd
 * Java type: com.bagnet.nettracer.ws.core.pojo.xsd.WSQueryResponse
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.core.pojo.xsd.impl;
/**
 * An XML WS_QueryResponse(@http://pojo.core.ws.nettracer.bagnet.com/xsd).
 *
 * This is a complex type.
 */
public class WSQueryResponseImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.core.pojo.xsd.WSQueryResponse
{
    
    public WSQueryResponseImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName ERRORRESPONSE$0 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "errorResponse");
    private static final javax.xml.namespace.QName RESULT$2 = 
        new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "result");
    
    
    /**
     * Gets the "errorResponse" element
     */
    public java.lang.String getErrorResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ERRORRESPONSE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "errorResponse" element
     */
    public org.apache.xmlbeans.XmlString xgetErrorResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ERRORRESPONSE$0, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "errorResponse" element
     */
    public boolean isNilErrorResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ERRORRESPONSE$0, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "errorResponse" element
     */
    public boolean isSetErrorResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(ERRORRESPONSE$0) != 0;
        }
    }
    
    /**
     * Sets the "errorResponse" element
     */
    public void setErrorResponse(java.lang.String errorResponse)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ERRORRESPONSE$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ERRORRESPONSE$0);
            }
            target.setStringValue(errorResponse);
        }
    }
    
    /**
     * Sets (as xml) the "errorResponse" element
     */
    public void xsetErrorResponse(org.apache.xmlbeans.XmlString errorResponse)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ERRORRESPONSE$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(ERRORRESPONSE$0);
            }
            target.set(errorResponse);
        }
    }
    
    /**
     * Nils the "errorResponse" element
     */
    public void setNilErrorResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ERRORRESPONSE$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(ERRORRESPONSE$0);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "errorResponse" element
     */
    public void unsetErrorResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(ERRORRESPONSE$0, 0);
        }
    }
    
    /**
     * Gets array of all "result" elements
     */
    public java.lang.String[] getResultArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(RESULT$2, targetList);
            java.lang.String[] result = new java.lang.String[targetList.size()];
            for (int i = 0, len = targetList.size() ; i < len ; i++)
                result[i] = ((org.apache.xmlbeans.SimpleValue)targetList.get(i)).getStringValue();
            return result;
        }
    }
    
    /**
     * Gets ith "result" element
     */
    public java.lang.String getResultArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(RESULT$2, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) array of all "result" elements
     */
    public org.apache.xmlbeans.XmlString[] xgetResultArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(RESULT$2, targetList);
            org.apache.xmlbeans.XmlString[] result = new org.apache.xmlbeans.XmlString[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets (as xml) ith "result" element
     */
    public org.apache.xmlbeans.XmlString xgetResultArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(RESULT$2, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return (org.apache.xmlbeans.XmlString)target;
        }
    }
    
    /**
     * Tests for nil ith "result" element
     */
    public boolean isNilResultArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(RESULT$2, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target.isNil();
        }
    }
    
    /**
     * Returns number of "result" element
     */
    public int sizeOfResultArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(RESULT$2);
        }
    }
    
    /**
     * Sets array of all "result" element
     */
    public void setResultArray(java.lang.String[] resultArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(resultArray, RESULT$2);
        }
    }
    
    /**
     * Sets ith "result" element
     */
    public void setResultArray(int i, java.lang.String result)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(RESULT$2, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.setStringValue(result);
        }
    }
    
    /**
     * Sets (as xml) array of all "result" element
     */
    public void xsetResultArray(org.apache.xmlbeans.XmlString[]resultArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(resultArray, RESULT$2);
        }
    }
    
    /**
     * Sets (as xml) ith "result" element
     */
    public void xsetResultArray(int i, org.apache.xmlbeans.XmlString result)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(RESULT$2, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(result);
        }
    }
    
    /**
     * Nils the ith "result" element
     */
    public void setNilResultArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(RESULT$2, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.setNil();
        }
    }
    
    /**
     * Inserts the value as the ith "result" element
     */
    public void insertResult(int i, java.lang.String result)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = 
                (org.apache.xmlbeans.SimpleValue)get_store().insert_element_user(RESULT$2, i);
            target.setStringValue(result);
        }
    }
    
    /**
     * Appends the value as the last "result" element
     */
    public void addResult(java.lang.String result)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(RESULT$2);
            target.setStringValue(result);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "result" element
     */
    public org.apache.xmlbeans.XmlString insertNewResult(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().insert_element_user(RESULT$2, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "result" element
     */
    public org.apache.xmlbeans.XmlString addNewResult()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(RESULT$2);
            return target;
        }
    }
    
    /**
     * Removes the ith "result" element
     */
    public void removeResult(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(RESULT$2, i);
        }
    }
}
