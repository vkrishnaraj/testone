/*
 * An XML document type.
 * Localname: uploadFileResponse
 * Namespace: http://onlineclaims.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.onlineclaims.UploadFileResponseDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.onlineclaims.impl;
/**
 * A document containing one uploadFileResponse(@http://onlineclaims.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public class UploadFileResponseDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.onlineclaims.UploadFileResponseDocument
{
    
    public UploadFileResponseDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName UPLOADFILERESPONSE$0 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com", "uploadFileResponse");
    
    
    /**
     * Gets the "uploadFileResponse" element
     */
    public com.bagnet.nettracer.ws.onlineclaims.UploadFileResponseDocument.UploadFileResponse getUploadFileResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.UploadFileResponseDocument.UploadFileResponse target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.UploadFileResponseDocument.UploadFileResponse)get_store().find_element_user(UPLOADFILERESPONSE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "uploadFileResponse" element
     */
    public void setUploadFileResponse(com.bagnet.nettracer.ws.onlineclaims.UploadFileResponseDocument.UploadFileResponse uploadFileResponse)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.UploadFileResponseDocument.UploadFileResponse target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.UploadFileResponseDocument.UploadFileResponse)get_store().find_element_user(UPLOADFILERESPONSE$0, 0);
            if (target == null)
            {
                target = (com.bagnet.nettracer.ws.onlineclaims.UploadFileResponseDocument.UploadFileResponse)get_store().add_element_user(UPLOADFILERESPONSE$0);
            }
            target.set(uploadFileResponse);
        }
    }
    
    /**
     * Appends and returns a new empty "uploadFileResponse" element
     */
    public com.bagnet.nettracer.ws.onlineclaims.UploadFileResponseDocument.UploadFileResponse addNewUploadFileResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.UploadFileResponseDocument.UploadFileResponse target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.UploadFileResponseDocument.UploadFileResponse)get_store().add_element_user(UPLOADFILERESPONSE$0);
            return target;
        }
    }
    /**
     * An XML uploadFileResponse(@http://onlineclaims.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public static class UploadFileResponseImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.onlineclaims.UploadFileResponseDocument.UploadFileResponse
    {
        
        public UploadFileResponseImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName RETURN$0 = 
            new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com", "return");
        
        
        /**
         * Gets the "return" element
         */
        public com.bagnet.nettracer.ws.onlineclaims.xsd.File getReturn()
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.bagnet.nettracer.ws.onlineclaims.xsd.File target = null;
                target = (com.bagnet.nettracer.ws.onlineclaims.xsd.File)get_store().find_element_user(RETURN$0, 0);
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
                com.bagnet.nettracer.ws.onlineclaims.xsd.File target = null;
                target = (com.bagnet.nettracer.ws.onlineclaims.xsd.File)get_store().find_element_user(RETURN$0, 0);
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
        public void setReturn(com.bagnet.nettracer.ws.onlineclaims.xsd.File xreturn)
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.bagnet.nettracer.ws.onlineclaims.xsd.File target = null;
                target = (com.bagnet.nettracer.ws.onlineclaims.xsd.File)get_store().find_element_user(RETURN$0, 0);
                if (target == null)
                {
                    target = (com.bagnet.nettracer.ws.onlineclaims.xsd.File)get_store().add_element_user(RETURN$0);
                }
                target.set(xreturn);
            }
        }
        
        /**
         * Appends and returns a new empty "return" element
         */
        public com.bagnet.nettracer.ws.onlineclaims.xsd.File addNewReturn()
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.bagnet.nettracer.ws.onlineclaims.xsd.File target = null;
                target = (com.bagnet.nettracer.ws.onlineclaims.xsd.File)get_store().add_element_user(RETURN$0);
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
                com.bagnet.nettracer.ws.onlineclaims.xsd.File target = null;
                target = (com.bagnet.nettracer.ws.onlineclaims.xsd.File)get_store().find_element_user(RETURN$0, 0);
                if (target == null)
                {
                    target = (com.bagnet.nettracer.ws.onlineclaims.xsd.File)get_store().add_element_user(RETURN$0);
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
