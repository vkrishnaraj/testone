/*
 * An XML document type.
 * Localname: getActivePhoneListResponse
 * Namespace: http://passengerview.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.passengerview.GetActivePhoneListResponseDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.passengerview.impl;
/**
 * A document containing one getActivePhoneListResponse(@http://passengerview.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public class GetActivePhoneListResponseDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.passengerview.GetActivePhoneListResponseDocument
{
    
    public GetActivePhoneListResponseDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName GETACTIVEPHONELISTRESPONSE$0 = 
        new javax.xml.namespace.QName("http://passengerview.ws.nettracer.bagnet.com", "getActivePhoneListResponse");
    
    
    /**
     * Gets the "getActivePhoneListResponse" element
     */
    public com.bagnet.nettracer.ws.passengerview.GetActivePhoneListResponseDocument.GetActivePhoneListResponse getGetActivePhoneListResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.passengerview.GetActivePhoneListResponseDocument.GetActivePhoneListResponse target = null;
            target = (com.bagnet.nettracer.ws.passengerview.GetActivePhoneListResponseDocument.GetActivePhoneListResponse)get_store().find_element_user(GETACTIVEPHONELISTRESPONSE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "getActivePhoneListResponse" element
     */
    public void setGetActivePhoneListResponse(com.bagnet.nettracer.ws.passengerview.GetActivePhoneListResponseDocument.GetActivePhoneListResponse getActivePhoneListResponse)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.passengerview.GetActivePhoneListResponseDocument.GetActivePhoneListResponse target = null;
            target = (com.bagnet.nettracer.ws.passengerview.GetActivePhoneListResponseDocument.GetActivePhoneListResponse)get_store().find_element_user(GETACTIVEPHONELISTRESPONSE$0, 0);
            if (target == null)
            {
                target = (com.bagnet.nettracer.ws.passengerview.GetActivePhoneListResponseDocument.GetActivePhoneListResponse)get_store().add_element_user(GETACTIVEPHONELISTRESPONSE$0);
            }
            target.set(getActivePhoneListResponse);
        }
    }
    
    /**
     * Appends and returns a new empty "getActivePhoneListResponse" element
     */
    public com.bagnet.nettracer.ws.passengerview.GetActivePhoneListResponseDocument.GetActivePhoneListResponse addNewGetActivePhoneListResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.passengerview.GetActivePhoneListResponseDocument.GetActivePhoneListResponse target = null;
            target = (com.bagnet.nettracer.ws.passengerview.GetActivePhoneListResponseDocument.GetActivePhoneListResponse)get_store().add_element_user(GETACTIVEPHONELISTRESPONSE$0);
            return target;
        }
    }
    /**
     * An XML getActivePhoneListResponse(@http://passengerview.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public static class GetActivePhoneListResponseImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.passengerview.GetActivePhoneListResponseDocument.GetActivePhoneListResponse
    {
        
        public GetActivePhoneListResponseImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName RETURN$0 = 
            new javax.xml.namespace.QName("http://passengerview.ws.nettracer.bagnet.com", "return");
        
        
        /**
         * Gets array of all "return" elements
         */
        public java.lang.String[] getReturnArray()
        {
            synchronized (monitor())
            {
                check_orphaned();
                java.util.List targetList = new java.util.ArrayList();
                get_store().find_all_element_users(RETURN$0, targetList);
                java.lang.String[] result = new java.lang.String[targetList.size()];
                for (int i = 0, len = targetList.size() ; i < len ; i++)
                    result[i] = ((org.apache.xmlbeans.SimpleValue)targetList.get(i)).getStringValue();
                return result;
            }
        }
        
        /**
         * Gets ith "return" element
         */
        public java.lang.String getReturnArray(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(RETURN$0, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                return target.getStringValue();
            }
        }
        
        /**
         * Gets (as xml) array of all "return" elements
         */
        public org.apache.xmlbeans.XmlString[] xgetReturnArray()
        {
            synchronized (monitor())
            {
                check_orphaned();
                java.util.List targetList = new java.util.ArrayList();
                get_store().find_all_element_users(RETURN$0, targetList);
                org.apache.xmlbeans.XmlString[] result = new org.apache.xmlbeans.XmlString[targetList.size()];
                targetList.toArray(result);
                return result;
            }
        }
        
        /**
         * Gets (as xml) ith "return" element
         */
        public org.apache.xmlbeans.XmlString xgetReturnArray(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(RETURN$0, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                return (org.apache.xmlbeans.XmlString)target;
            }
        }
        
        /**
         * Tests for nil ith "return" element
         */
        public boolean isNilReturnArray(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(RETURN$0, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                return target.isNil();
            }
        }
        
        /**
         * Returns number of "return" element
         */
        public int sizeOfReturnArray()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(RETURN$0);
            }
        }
        
        /**
         * Sets array of all "return" element
         */
        public void setReturnArray(java.lang.String[] xreturnArray)
        {
            synchronized (monitor())
            {
                check_orphaned();
                arraySetterHelper(xreturnArray, RETURN$0);
            }
        }
        
        /**
         * Sets ith "return" element
         */
        public void setReturnArray(int i, java.lang.String xreturn)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(RETURN$0, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                target.setStringValue(xreturn);
            }
        }
        
        /**
         * Sets (as xml) array of all "return" element
         */
        public void xsetReturnArray(org.apache.xmlbeans.XmlString[]xreturnArray)
        {
            synchronized (monitor())
            {
                check_orphaned();
                arraySetterHelper(xreturnArray, RETURN$0);
            }
        }
        
        /**
         * Sets (as xml) ith "return" element
         */
        public void xsetReturnArray(int i, org.apache.xmlbeans.XmlString xreturn)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(RETURN$0, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                target.set(xreturn);
            }
        }
        
        /**
         * Nils the ith "return" element
         */
        public void setNilReturnArray(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(RETURN$0, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                target.setNil();
            }
        }
        
        /**
         * Inserts the value as the ith "return" element
         */
        public void insertReturn(int i, java.lang.String xreturn)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = 
                    (org.apache.xmlbeans.SimpleValue)get_store().insert_element_user(RETURN$0, i);
                target.setStringValue(xreturn);
            }
        }
        
        /**
         * Appends the value as the last "return" element
         */
        public void addReturn(java.lang.String xreturn)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(RETURN$0);
                target.setStringValue(xreturn);
            }
        }
        
        /**
         * Inserts and returns a new empty value (as xml) as the ith "return" element
         */
        public org.apache.xmlbeans.XmlString insertNewReturn(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().insert_element_user(RETURN$0, i);
                return target;
            }
        }
        
        /**
         * Appends and returns a new empty value (as xml) as the last "return" element
         */
        public org.apache.xmlbeans.XmlString addNewReturn()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(RETURN$0);
                return target;
            }
        }
        
        /**
         * Removes the ith "return" element
         */
        public void removeReturn(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(RETURN$0, i);
            }
        }
    }
}
