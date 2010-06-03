/*
 * An XML document type.
 * Localname: retrieveImageResponse
 * Namespace: http://onlineclaims.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.onlineclaims.RetrieveImageResponseDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.onlineclaims.impl;
/**
 * A document containing one retrieveImageResponse(@http://onlineclaims.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public class RetrieveImageResponseDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.onlineclaims.RetrieveImageResponseDocument
{
    
    public RetrieveImageResponseDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName RETRIEVEIMAGERESPONSE$0 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com", "retrieveImageResponse");
    
    
    /**
     * Gets the "retrieveImageResponse" element
     */
    public com.bagnet.nettracer.ws.onlineclaims.RetrieveImageResponseDocument.RetrieveImageResponse getRetrieveImageResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.RetrieveImageResponseDocument.RetrieveImageResponse target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.RetrieveImageResponseDocument.RetrieveImageResponse)get_store().find_element_user(RETRIEVEIMAGERESPONSE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "retrieveImageResponse" element
     */
    public void setRetrieveImageResponse(com.bagnet.nettracer.ws.onlineclaims.RetrieveImageResponseDocument.RetrieveImageResponse retrieveImageResponse)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.RetrieveImageResponseDocument.RetrieveImageResponse target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.RetrieveImageResponseDocument.RetrieveImageResponse)get_store().find_element_user(RETRIEVEIMAGERESPONSE$0, 0);
            if (target == null)
            {
                target = (com.bagnet.nettracer.ws.onlineclaims.RetrieveImageResponseDocument.RetrieveImageResponse)get_store().add_element_user(RETRIEVEIMAGERESPONSE$0);
            }
            target.set(retrieveImageResponse);
        }
    }
    
    /**
     * Appends and returns a new empty "retrieveImageResponse" element
     */
    public com.bagnet.nettracer.ws.onlineclaims.RetrieveImageResponseDocument.RetrieveImageResponse addNewRetrieveImageResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.RetrieveImageResponseDocument.RetrieveImageResponse target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.RetrieveImageResponseDocument.RetrieveImageResponse)get_store().add_element_user(RETRIEVEIMAGERESPONSE$0);
            return target;
        }
    }
    /**
     * An XML retrieveImageResponse(@http://onlineclaims.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public static class RetrieveImageResponseImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.onlineclaims.RetrieveImageResponseDocument.RetrieveImageResponse
    {
        
        public RetrieveImageResponseImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName RETURN$0 = 
            new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com", "return");
        
        
        /**
         * Gets the "return" element
         */
        public byte[] getReturn()
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
                return target.getByteArrayValue();
            }
        }
        
        /**
         * Gets (as xml) the "return" element
         */
        public org.apache.xmlbeans.XmlBase64Binary xgetReturn()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlBase64Binary target = null;
                target = (org.apache.xmlbeans.XmlBase64Binary)get_store().find_element_user(RETURN$0, 0);
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
                org.apache.xmlbeans.XmlBase64Binary target = null;
                target = (org.apache.xmlbeans.XmlBase64Binary)get_store().find_element_user(RETURN$0, 0);
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
        public void setReturn(byte[] xreturn)
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
                target.setByteArrayValue(xreturn);
            }
        }
        
        /**
         * Sets (as xml) the "return" element
         */
        public void xsetReturn(org.apache.xmlbeans.XmlBase64Binary xreturn)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlBase64Binary target = null;
                target = (org.apache.xmlbeans.XmlBase64Binary)get_store().find_element_user(RETURN$0, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlBase64Binary)get_store().add_element_user(RETURN$0);
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
                org.apache.xmlbeans.XmlBase64Binary target = null;
                target = (org.apache.xmlbeans.XmlBase64Binary)get_store().find_element_user(RETURN$0, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlBase64Binary)get_store().add_element_user(RETURN$0);
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
