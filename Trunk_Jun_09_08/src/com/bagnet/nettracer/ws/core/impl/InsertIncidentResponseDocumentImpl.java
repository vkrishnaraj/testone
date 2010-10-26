/*
 * An XML document type.
 * Localname: insertIncidentResponse
 * Namespace: http://core.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.core.InsertIncidentResponseDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.core.impl;
/**
 * A document containing one insertIncidentResponse(@http://core.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public class InsertIncidentResponseDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.core.InsertIncidentResponseDocument
{
    
    public InsertIncidentResponseDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName INSERTINCIDENTRESPONSE$0 = 
        new javax.xml.namespace.QName("http://core.ws.nettracer.bagnet.com", "insertIncidentResponse");
    
    
    /**
     * Gets the "insertIncidentResponse" element
     */
    public com.bagnet.nettracer.ws.core.InsertIncidentResponseDocument.InsertIncidentResponse getInsertIncidentResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.InsertIncidentResponseDocument.InsertIncidentResponse target = null;
            target = (com.bagnet.nettracer.ws.core.InsertIncidentResponseDocument.InsertIncidentResponse)get_store().find_element_user(INSERTINCIDENTRESPONSE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "insertIncidentResponse" element
     */
    public void setInsertIncidentResponse(com.bagnet.nettracer.ws.core.InsertIncidentResponseDocument.InsertIncidentResponse insertIncidentResponse)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.InsertIncidentResponseDocument.InsertIncidentResponse target = null;
            target = (com.bagnet.nettracer.ws.core.InsertIncidentResponseDocument.InsertIncidentResponse)get_store().find_element_user(INSERTINCIDENTRESPONSE$0, 0);
            if (target == null)
            {
                target = (com.bagnet.nettracer.ws.core.InsertIncidentResponseDocument.InsertIncidentResponse)get_store().add_element_user(INSERTINCIDENTRESPONSE$0);
            }
            target.set(insertIncidentResponse);
        }
    }
    
    /**
     * Appends and returns a new empty "insertIncidentResponse" element
     */
    public com.bagnet.nettracer.ws.core.InsertIncidentResponseDocument.InsertIncidentResponse addNewInsertIncidentResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.InsertIncidentResponseDocument.InsertIncidentResponse target = null;
            target = (com.bagnet.nettracer.ws.core.InsertIncidentResponseDocument.InsertIncidentResponse)get_store().add_element_user(INSERTINCIDENTRESPONSE$0);
            return target;
        }
    }
    /**
     * An XML insertIncidentResponse(@http://core.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public static class InsertIncidentResponseImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.core.InsertIncidentResponseDocument.InsertIncidentResponse
    {
        
        public InsertIncidentResponseImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName RETURN$0 = 
            new javax.xml.namespace.QName("http://core.ws.nettracer.bagnet.com", "return");
        
        
        /**
         * Gets the "return" element
         */
        public java.lang.String getReturn()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(RETURN$0, 0);
                if (target == null)
                {
                    return null;
                }
                return target.getStringValue();
            }
        }
        
        /**
         * Gets (as xml) the "return" element
         */
        public org.apache.xmlbeans.XmlString xgetReturn()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(RETURN$0, 0);
                return target;
            }
        }
        
        /**
         * Tests for nil "return" element
         */
        public boolean isNilReturn()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(RETURN$0, 0);
                if (target == null) return false;
                return target.isNil();
            }
        }
        
        /**
         * True if has "return" element
         */
        public boolean isSetReturn()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(RETURN$0) != 0;
            }
        }
        
        /**
         * Sets the "return" element
         */
        public void setReturn(java.lang.String xreturn)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(RETURN$0, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(RETURN$0);
                }
                target.setStringValue(xreturn);
            }
        }
        
        /**
         * Sets (as xml) the "return" element
         */
        public void xsetReturn(org.apache.xmlbeans.XmlString xreturn)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(RETURN$0, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(RETURN$0);
                }
                target.set(xreturn);
            }
        }
        
        /**
         * Nils the "return" element
         */
        public void setNilReturn()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(RETURN$0, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(RETURN$0);
                }
                target.setNil();
            }
        }
        
        /**
         * Unsets the "return" element
         */
        public void unsetReturn()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(RETURN$0, 0);
            }
        }
    }
}
