/*
 * An XML document type.
 * Localname: authAdminUserResponse
 * Namespace: http://onlineclaims.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserResponseDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.onlineclaims.impl;
/**
 * A document containing one authAdminUserResponse(@http://onlineclaims.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public class AuthAdminUserResponseDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserResponseDocument
{
    private static final long serialVersionUID = 1L;
    
    public AuthAdminUserResponseDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName AUTHADMINUSERRESPONSE$0 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com", "authAdminUserResponse");
    
    
    /**
     * Gets the "authAdminUserResponse" element
     */
    public com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserResponseDocument.AuthAdminUserResponse getAuthAdminUserResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserResponseDocument.AuthAdminUserResponse target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserResponseDocument.AuthAdminUserResponse)get_store().find_element_user(AUTHADMINUSERRESPONSE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "authAdminUserResponse" element
     */
    public void setAuthAdminUserResponse(com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserResponseDocument.AuthAdminUserResponse authAdminUserResponse)
    {
        generatedSetterHelperImpl(authAdminUserResponse, AUTHADMINUSERRESPONSE$0, 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
    }
    
    /**
     * Appends and returns a new empty "authAdminUserResponse" element
     */
    public com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserResponseDocument.AuthAdminUserResponse addNewAuthAdminUserResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserResponseDocument.AuthAdminUserResponse target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserResponseDocument.AuthAdminUserResponse)get_store().add_element_user(AUTHADMINUSERRESPONSE$0);
            return target;
        }
    }
    /**
     * An XML authAdminUserResponse(@http://onlineclaims.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public static class AuthAdminUserResponseImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserResponseDocument.AuthAdminUserResponse
    {
        private static final long serialVersionUID = 1L;
        
        public AuthAdminUserResponseImpl(org.apache.xmlbeans.SchemaType sType)
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
