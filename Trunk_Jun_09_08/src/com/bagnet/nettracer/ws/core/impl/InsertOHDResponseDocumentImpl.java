/*
 * An XML document type.
 * Localname: insertOHDResponse
 * Namespace: http://core.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.core.InsertOHDResponseDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.core.impl;
/**
 * A document containing one insertOHDResponse(@http://core.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public class InsertOHDResponseDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.core.InsertOHDResponseDocument
{
    
    public InsertOHDResponseDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName INSERTOHDRESPONSE$0 = 
        new javax.xml.namespace.QName("http://core.ws.nettracer.bagnet.com", "insertOHDResponse");
    
    
    /**
     * Gets the "insertOHDResponse" element
     */
    public com.bagnet.nettracer.ws.core.InsertOHDResponseDocument.InsertOHDResponse getInsertOHDResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.InsertOHDResponseDocument.InsertOHDResponse target = null;
            target = (com.bagnet.nettracer.ws.core.InsertOHDResponseDocument.InsertOHDResponse)get_store().find_element_user(INSERTOHDRESPONSE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "insertOHDResponse" element
     */
    public void setInsertOHDResponse(com.bagnet.nettracer.ws.core.InsertOHDResponseDocument.InsertOHDResponse insertOHDResponse)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.InsertOHDResponseDocument.InsertOHDResponse target = null;
            target = (com.bagnet.nettracer.ws.core.InsertOHDResponseDocument.InsertOHDResponse)get_store().find_element_user(INSERTOHDRESPONSE$0, 0);
            if (target == null)
            {
                target = (com.bagnet.nettracer.ws.core.InsertOHDResponseDocument.InsertOHDResponse)get_store().add_element_user(INSERTOHDRESPONSE$0);
            }
            target.set(insertOHDResponse);
        }
    }
    
    /**
     * Appends and returns a new empty "insertOHDResponse" element
     */
    public com.bagnet.nettracer.ws.core.InsertOHDResponseDocument.InsertOHDResponse addNewInsertOHDResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.InsertOHDResponseDocument.InsertOHDResponse target = null;
            target = (com.bagnet.nettracer.ws.core.InsertOHDResponseDocument.InsertOHDResponse)get_store().add_element_user(INSERTOHDRESPONSE$0);
            return target;
        }
    }
    /**
     * An XML insertOHDResponse(@http://core.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public static class InsertOHDResponseImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.core.InsertOHDResponseDocument.InsertOHDResponse
    {
        
        public InsertOHDResponseImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName RETURN$0 = 
            new javax.xml.namespace.QName("http://core.ws.nettracer.bagnet.com", "return");
        
        
        /**
         * Gets the "return" element
         */
        public com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse getReturn()
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse)get_store().find_element_user(RETURN$0, 0);
                if (target == null)
                {
                    return null;
                }
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
                com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse)get_store().find_element_user(RETURN$0, 0);
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
        public void setReturn(com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse xreturn)
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse)get_store().find_element_user(RETURN$0, 0);
                if (target == null)
                {
                    target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse)get_store().add_element_user(RETURN$0);
                }
                target.set(xreturn);
            }
        }
        
        /**
         * Appends and returns a new empty "return" element
         */
        public com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse addNewReturn()
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse)get_store().add_element_user(RETURN$0);
                return target;
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
                com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse)get_store().find_element_user(RETURN$0, 0);
                if (target == null)
                {
                    target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse)get_store().add_element_user(RETURN$0);
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
