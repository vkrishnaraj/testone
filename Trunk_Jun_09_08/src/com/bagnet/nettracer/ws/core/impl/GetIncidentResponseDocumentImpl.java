/*
 * An XML document type.
 * Localname: getIncidentResponse
 * Namespace: http://core.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.core.GetIncidentResponseDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.core.impl;
/**
 * A document containing one getIncidentResponse(@http://core.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public class GetIncidentResponseDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.core.GetIncidentResponseDocument
{
    
    public GetIncidentResponseDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName GETINCIDENTRESPONSE$0 = 
        new javax.xml.namespace.QName("http://core.ws.nettracer.bagnet.com", "getIncidentResponse");
    
    
    /**
     * Gets the "getIncidentResponse" element
     */
    public com.bagnet.nettracer.ws.core.GetIncidentResponseDocument.GetIncidentResponse getGetIncidentResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.GetIncidentResponseDocument.GetIncidentResponse target = null;
            target = (com.bagnet.nettracer.ws.core.GetIncidentResponseDocument.GetIncidentResponse)get_store().find_element_user(GETINCIDENTRESPONSE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "getIncidentResponse" element
     */
    public void setGetIncidentResponse(com.bagnet.nettracer.ws.core.GetIncidentResponseDocument.GetIncidentResponse getIncidentResponse)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.GetIncidentResponseDocument.GetIncidentResponse target = null;
            target = (com.bagnet.nettracer.ws.core.GetIncidentResponseDocument.GetIncidentResponse)get_store().find_element_user(GETINCIDENTRESPONSE$0, 0);
            if (target == null)
            {
                target = (com.bagnet.nettracer.ws.core.GetIncidentResponseDocument.GetIncidentResponse)get_store().add_element_user(GETINCIDENTRESPONSE$0);
            }
            target.set(getIncidentResponse);
        }
    }
    
    /**
     * Appends and returns a new empty "getIncidentResponse" element
     */
    public com.bagnet.nettracer.ws.core.GetIncidentResponseDocument.GetIncidentResponse addNewGetIncidentResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.GetIncidentResponseDocument.GetIncidentResponse target = null;
            target = (com.bagnet.nettracer.ws.core.GetIncidentResponseDocument.GetIncidentResponse)get_store().add_element_user(GETINCIDENTRESPONSE$0);
            return target;
        }
    }
    /**
     * An XML getIncidentResponse(@http://core.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public static class GetIncidentResponseImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.core.GetIncidentResponseDocument.GetIncidentResponse
    {
        
        public GetIncidentResponseImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName RETURN$0 = 
            new javax.xml.namespace.QName("http://core.ws.nettracer.bagnet.com", "return");
        
        
        /**
         * Gets the "return" element
         */
        public com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident getReturn()
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident)get_store().find_element_user(RETURN$0, 0);
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
                com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident)get_store().find_element_user(RETURN$0, 0);
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
        public void setReturn(com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident xreturn)
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident)get_store().find_element_user(RETURN$0, 0);
                if (target == null)
                {
                    target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident)get_store().add_element_user(RETURN$0);
                }
                target.set(xreturn);
            }
        }
        
        /**
         * Appends and returns a new empty "return" element
         */
        public com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident addNewReturn()
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident)get_store().add_element_user(RETURN$0);
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
                com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident)get_store().find_element_user(RETURN$0, 0);
                if (target == null)
                {
                    target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident)get_store().add_element_user(RETURN$0);
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
