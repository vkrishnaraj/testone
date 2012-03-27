/*
 * An XML document type.
 * Localname: authIncidentResponse
 * Namespace: http://onlineclaims.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.onlineclaims.AuthIncidentResponseDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.onlineclaims.impl;
/**
 * A document containing one authIncidentResponse(@http://onlineclaims.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public class AuthIncidentResponseDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.onlineclaims.AuthIncidentResponseDocument
{
    private static final long serialVersionUID = 1L;
    
    public AuthIncidentResponseDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName AUTHINCIDENTRESPONSE$0 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com", "authIncidentResponse");
    
    
    /**
     * Gets the "authIncidentResponse" element
     */
    public com.bagnet.nettracer.ws.onlineclaims.AuthIncidentResponseDocument.AuthIncidentResponse getAuthIncidentResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.AuthIncidentResponseDocument.AuthIncidentResponse target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.AuthIncidentResponseDocument.AuthIncidentResponse)get_store().find_element_user(AUTHINCIDENTRESPONSE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "authIncidentResponse" element
     */
    public void setAuthIncidentResponse(com.bagnet.nettracer.ws.onlineclaims.AuthIncidentResponseDocument.AuthIncidentResponse authIncidentResponse)
    {
        generatedSetterHelperImpl(authIncidentResponse, AUTHINCIDENTRESPONSE$0, 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
    }
    
    /**
     * Appends and returns a new empty "authIncidentResponse" element
     */
    public com.bagnet.nettracer.ws.onlineclaims.AuthIncidentResponseDocument.AuthIncidentResponse addNewAuthIncidentResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.AuthIncidentResponseDocument.AuthIncidentResponse target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.AuthIncidentResponseDocument.AuthIncidentResponse)get_store().add_element_user(AUTHINCIDENTRESPONSE$0);
            return target;
        }
    }
    /**
     * An XML authIncidentResponse(@http://onlineclaims.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public static class AuthIncidentResponseImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.onlineclaims.AuthIncidentResponseDocument.AuthIncidentResponse
    {
        private static final long serialVersionUID = 1L;
        
        public AuthIncidentResponseImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName RETURN$0 = 
            new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com", "return");
        
        
        /**
         * Gets the "return" element
         */
        public com.bagnet.nettracer.ws.onlineclaims.xsd.Incident getReturn()
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.bagnet.nettracer.ws.onlineclaims.xsd.Incident target = null;
                target = (com.bagnet.nettracer.ws.onlineclaims.xsd.Incident)get_store().find_element_user(RETURN$0, 0);
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
                com.bagnet.nettracer.ws.onlineclaims.xsd.Incident target = null;
                target = (com.bagnet.nettracer.ws.onlineclaims.xsd.Incident)get_store().find_element_user(RETURN$0, 0);
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
        public void setReturn(com.bagnet.nettracer.ws.onlineclaims.xsd.Incident xreturn)
        {
            generatedSetterHelperImpl(xreturn, RETURN$0, 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
        }
        
        /**
         * Appends and returns a new empty "return" element
         */
        public com.bagnet.nettracer.ws.onlineclaims.xsd.Incident addNewReturn()
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.bagnet.nettracer.ws.onlineclaims.xsd.Incident target = null;
                target = (com.bagnet.nettracer.ws.onlineclaims.xsd.Incident)get_store().add_element_user(RETURN$0);
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
                com.bagnet.nettracer.ws.onlineclaims.xsd.Incident target = null;
                target = (com.bagnet.nettracer.ws.onlineclaims.xsd.Incident)get_store().find_element_user(RETURN$0, 0);
                if (target == null)
                {
                    target = (com.bagnet.nettracer.ws.onlineclaims.xsd.Incident)get_store().add_element_user(RETURN$0);
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
