/*
 * An XML document type.
 * Localname: getIncidentPVResponse
 * Namespace: http://passengerview.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.passengerview.GetIncidentPVResponseDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.passengerview.impl;
/**
 * A document containing one getIncidentPVResponse(@http://passengerview.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public class GetIncidentPVResponseDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.passengerview.GetIncidentPVResponseDocument
{
    
    public GetIncidentPVResponseDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName GETINCIDENTPVRESPONSE$0 = 
        new javax.xml.namespace.QName("http://passengerview.ws.nettracer.bagnet.com", "getIncidentPVResponse");
    
    
    /**
     * Gets the "getIncidentPVResponse" element
     */
    public com.bagnet.nettracer.ws.passengerview.GetIncidentPVResponseDocument.GetIncidentPVResponse getGetIncidentPVResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.passengerview.GetIncidentPVResponseDocument.GetIncidentPVResponse target = null;
            target = (com.bagnet.nettracer.ws.passengerview.GetIncidentPVResponseDocument.GetIncidentPVResponse)get_store().find_element_user(GETINCIDENTPVRESPONSE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "getIncidentPVResponse" element
     */
    public void setGetIncidentPVResponse(com.bagnet.nettracer.ws.passengerview.GetIncidentPVResponseDocument.GetIncidentPVResponse getIncidentPVResponse)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.passengerview.GetIncidentPVResponseDocument.GetIncidentPVResponse target = null;
            target = (com.bagnet.nettracer.ws.passengerview.GetIncidentPVResponseDocument.GetIncidentPVResponse)get_store().find_element_user(GETINCIDENTPVRESPONSE$0, 0);
            if (target == null)
            {
                target = (com.bagnet.nettracer.ws.passengerview.GetIncidentPVResponseDocument.GetIncidentPVResponse)get_store().add_element_user(GETINCIDENTPVRESPONSE$0);
            }
            target.set(getIncidentPVResponse);
        }
    }
    
    /**
     * Appends and returns a new empty "getIncidentPVResponse" element
     */
    public com.bagnet.nettracer.ws.passengerview.GetIncidentPVResponseDocument.GetIncidentPVResponse addNewGetIncidentPVResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.passengerview.GetIncidentPVResponseDocument.GetIncidentPVResponse target = null;
            target = (com.bagnet.nettracer.ws.passengerview.GetIncidentPVResponseDocument.GetIncidentPVResponse)get_store().add_element_user(GETINCIDENTPVRESPONSE$0);
            return target;
        }
    }
    /**
     * An XML getIncidentPVResponse(@http://passengerview.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public static class GetIncidentPVResponseImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.passengerview.GetIncidentPVResponseDocument.GetIncidentPVResponse
    {
        
        public GetIncidentPVResponseImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName RETURN$0 = 
            new javax.xml.namespace.QName("http://passengerview.ws.nettracer.bagnet.com", "return");
        
        
        /**
         * Gets the "return" element
         */
        public com.bagnet.nettracer.ws.core.pojo.xsd.WSPVIncident getReturn()
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.bagnet.nettracer.ws.core.pojo.xsd.WSPVIncident target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVIncident)get_store().find_element_user(RETURN$0, 0);
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
                com.bagnet.nettracer.ws.core.pojo.xsd.WSPVIncident target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVIncident)get_store().find_element_user(RETURN$0, 0);
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
        public void setReturn(com.bagnet.nettracer.ws.core.pojo.xsd.WSPVIncident xreturn)
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.bagnet.nettracer.ws.core.pojo.xsd.WSPVIncident target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVIncident)get_store().find_element_user(RETURN$0, 0);
                if (target == null)
                {
                    target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVIncident)get_store().add_element_user(RETURN$0);
                }
                target.set(xreturn);
            }
        }
        
        /**
         * Appends and returns a new empty "return" element
         */
        public com.bagnet.nettracer.ws.core.pojo.xsd.WSPVIncident addNewReturn()
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.bagnet.nettracer.ws.core.pojo.xsd.WSPVIncident target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVIncident)get_store().add_element_user(RETURN$0);
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
                com.bagnet.nettracer.ws.core.pojo.xsd.WSPVIncident target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVIncident)get_store().find_element_user(RETURN$0, 0);
                if (target == null)
                {
                    target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVIncident)get_store().add_element_user(RETURN$0);
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
