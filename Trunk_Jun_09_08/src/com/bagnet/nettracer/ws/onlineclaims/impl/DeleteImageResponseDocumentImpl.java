/*
 * An XML document type.
 * Localname: deleteImageResponse
 * Namespace: http://onlineclaims.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.onlineclaims.DeleteImageResponseDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.onlineclaims.impl;
/**
 * A document containing one deleteImageResponse(@http://onlineclaims.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public class DeleteImageResponseDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.onlineclaims.DeleteImageResponseDocument
{
    
    public DeleteImageResponseDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName DELETEIMAGERESPONSE$0 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com", "deleteImageResponse");
    
    
    /**
     * Gets the "deleteImageResponse" element
     */
    public com.bagnet.nettracer.ws.onlineclaims.DeleteImageResponseDocument.DeleteImageResponse getDeleteImageResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.DeleteImageResponseDocument.DeleteImageResponse target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.DeleteImageResponseDocument.DeleteImageResponse)get_store().find_element_user(DELETEIMAGERESPONSE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "deleteImageResponse" element
     */
    public void setDeleteImageResponse(com.bagnet.nettracer.ws.onlineclaims.DeleteImageResponseDocument.DeleteImageResponse deleteImageResponse)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.DeleteImageResponseDocument.DeleteImageResponse target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.DeleteImageResponseDocument.DeleteImageResponse)get_store().find_element_user(DELETEIMAGERESPONSE$0, 0);
            if (target == null)
            {
                target = (com.bagnet.nettracer.ws.onlineclaims.DeleteImageResponseDocument.DeleteImageResponse)get_store().add_element_user(DELETEIMAGERESPONSE$0);
            }
            target.set(deleteImageResponse);
        }
    }
    
    /**
     * Appends and returns a new empty "deleteImageResponse" element
     */
    public com.bagnet.nettracer.ws.onlineclaims.DeleteImageResponseDocument.DeleteImageResponse addNewDeleteImageResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.DeleteImageResponseDocument.DeleteImageResponse target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.DeleteImageResponseDocument.DeleteImageResponse)get_store().add_element_user(DELETEIMAGERESPONSE$0);
            return target;
        }
    }
    /**
     * An XML deleteImageResponse(@http://onlineclaims.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public static class DeleteImageResponseImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.onlineclaims.DeleteImageResponseDocument.DeleteImageResponse
    {
        
        public DeleteImageResponseImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName RETURN$0 = 
            new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com", "return");
        
        
        /**
         * Gets the "return" element
         */
        public boolean getReturn()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(RETURN$0, 0);
                if (target == null)
                {
                    return false;
                }
                return target.getBooleanValue();
            }
        }
        
        /**
         * Gets (as xml) the "return" element
         */
        public org.apache.xmlbeans.XmlBoolean xgetReturn()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlBoolean target = null;
                target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(RETURN$0, 0);
                return target;
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
        public void setReturn(boolean xreturn)
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
                target.setBooleanValue(xreturn);
            }
        }
        
        /**
         * Sets (as xml) the "return" element
         */
        public void xsetReturn(org.apache.xmlbeans.XmlBoolean xreturn)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlBoolean target = null;
                target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(RETURN$0, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlBoolean)get_store().add_element_user(RETURN$0);
                }
                target.set(xreturn);
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
