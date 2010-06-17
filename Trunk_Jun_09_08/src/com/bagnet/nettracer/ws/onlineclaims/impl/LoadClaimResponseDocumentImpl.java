/*
 * An XML document type.
 * Localname: loadClaimResponse
 * Namespace: http://onlineclaims.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.onlineclaims.LoadClaimResponseDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.onlineclaims.impl;
/**
 * A document containing one loadClaimResponse(@http://onlineclaims.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public class LoadClaimResponseDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.onlineclaims.LoadClaimResponseDocument
{
    
    public LoadClaimResponseDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName LOADCLAIMRESPONSE$0 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com", "loadClaimResponse");
    
    
    /**
     * Gets the "loadClaimResponse" element
     */
    public com.bagnet.nettracer.ws.onlineclaims.LoadClaimResponseDocument.LoadClaimResponse getLoadClaimResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.LoadClaimResponseDocument.LoadClaimResponse target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.LoadClaimResponseDocument.LoadClaimResponse)get_store().find_element_user(LOADCLAIMRESPONSE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "loadClaimResponse" element
     */
    public void setLoadClaimResponse(com.bagnet.nettracer.ws.onlineclaims.LoadClaimResponseDocument.LoadClaimResponse loadClaimResponse)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.LoadClaimResponseDocument.LoadClaimResponse target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.LoadClaimResponseDocument.LoadClaimResponse)get_store().find_element_user(LOADCLAIMRESPONSE$0, 0);
            if (target == null)
            {
                target = (com.bagnet.nettracer.ws.onlineclaims.LoadClaimResponseDocument.LoadClaimResponse)get_store().add_element_user(LOADCLAIMRESPONSE$0);
            }
            target.set(loadClaimResponse);
        }
    }
    
    /**
     * Appends and returns a new empty "loadClaimResponse" element
     */
    public com.bagnet.nettracer.ws.onlineclaims.LoadClaimResponseDocument.LoadClaimResponse addNewLoadClaimResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.LoadClaimResponseDocument.LoadClaimResponse target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.LoadClaimResponseDocument.LoadClaimResponse)get_store().add_element_user(LOADCLAIMRESPONSE$0);
            return target;
        }
    }
    /**
     * An XML loadClaimResponse(@http://onlineclaims.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public static class LoadClaimResponseImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.onlineclaims.LoadClaimResponseDocument.LoadClaimResponse
    {
        
        public LoadClaimResponseImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName RETURN$0 = 
            new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com", "return");
        
        
        /**
         * Gets the "return" element
         */
        public com.bagnet.nettracer.ws.onlineclaims.xsd.Claim getReturn()
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.bagnet.nettracer.ws.onlineclaims.xsd.Claim target = null;
                target = (com.bagnet.nettracer.ws.onlineclaims.xsd.Claim)get_store().find_element_user(RETURN$0, 0);
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
                com.bagnet.nettracer.ws.onlineclaims.xsd.Claim target = null;
                target = (com.bagnet.nettracer.ws.onlineclaims.xsd.Claim)get_store().find_element_user(RETURN$0, 0);
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
        public void setReturn(com.bagnet.nettracer.ws.onlineclaims.xsd.Claim xreturn)
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.bagnet.nettracer.ws.onlineclaims.xsd.Claim target = null;
                target = (com.bagnet.nettracer.ws.onlineclaims.xsd.Claim)get_store().find_element_user(RETURN$0, 0);
                if (target == null)
                {
                    target = (com.bagnet.nettracer.ws.onlineclaims.xsd.Claim)get_store().add_element_user(RETURN$0);
                }
                target.set(xreturn);
            }
        }
        
        /**
         * Appends and returns a new empty "return" element
         */
        public com.bagnet.nettracer.ws.onlineclaims.xsd.Claim addNewReturn()
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.bagnet.nettracer.ws.onlineclaims.xsd.Claim target = null;
                target = (com.bagnet.nettracer.ws.onlineclaims.xsd.Claim)get_store().add_element_user(RETURN$0);
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
                com.bagnet.nettracer.ws.onlineclaims.xsd.Claim target = null;
                target = (com.bagnet.nettracer.ws.onlineclaims.xsd.Claim)get_store().find_element_user(RETURN$0, 0);
                if (target == null)
                {
                    target = (com.bagnet.nettracer.ws.onlineclaims.xsd.Claim)get_store().add_element_user(RETURN$0);
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
