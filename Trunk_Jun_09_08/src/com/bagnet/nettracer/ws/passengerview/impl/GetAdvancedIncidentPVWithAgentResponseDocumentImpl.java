/*
 * An XML document type.
 * Localname: getAdvancedIncidentPVWithAgentResponse
 * Namespace: http://passengerview.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentPVWithAgentResponseDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.passengerview.impl;
/**
 * A document containing one getAdvancedIncidentPVWithAgentResponse(@http://passengerview.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public class GetAdvancedIncidentPVWithAgentResponseDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentPVWithAgentResponseDocument
{
    
    public GetAdvancedIncidentPVWithAgentResponseDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName GETADVANCEDINCIDENTPVWITHAGENTRESPONSE$0 = 
        new javax.xml.namespace.QName("http://passengerview.ws.nettracer.bagnet.com", "getAdvancedIncidentPVWithAgentResponse");
    
    
    /**
     * Gets the "getAdvancedIncidentPVWithAgentResponse" element
     */
    public com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentPVWithAgentResponseDocument.GetAdvancedIncidentPVWithAgentResponse getGetAdvancedIncidentPVWithAgentResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentPVWithAgentResponseDocument.GetAdvancedIncidentPVWithAgentResponse target = null;
            target = (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentPVWithAgentResponseDocument.GetAdvancedIncidentPVWithAgentResponse)get_store().find_element_user(GETADVANCEDINCIDENTPVWITHAGENTRESPONSE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "getAdvancedIncidentPVWithAgentResponse" element
     */
    public void setGetAdvancedIncidentPVWithAgentResponse(com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentPVWithAgentResponseDocument.GetAdvancedIncidentPVWithAgentResponse getAdvancedIncidentPVWithAgentResponse)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentPVWithAgentResponseDocument.GetAdvancedIncidentPVWithAgentResponse target = null;
            target = (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentPVWithAgentResponseDocument.GetAdvancedIncidentPVWithAgentResponse)get_store().find_element_user(GETADVANCEDINCIDENTPVWITHAGENTRESPONSE$0, 0);
            if (target == null)
            {
                target = (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentPVWithAgentResponseDocument.GetAdvancedIncidentPVWithAgentResponse)get_store().add_element_user(GETADVANCEDINCIDENTPVWITHAGENTRESPONSE$0);
            }
            target.set(getAdvancedIncidentPVWithAgentResponse);
        }
    }
    
    /**
     * Appends and returns a new empty "getAdvancedIncidentPVWithAgentResponse" element
     */
    public com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentPVWithAgentResponseDocument.GetAdvancedIncidentPVWithAgentResponse addNewGetAdvancedIncidentPVWithAgentResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentPVWithAgentResponseDocument.GetAdvancedIncidentPVWithAgentResponse target = null;
            target = (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentPVWithAgentResponseDocument.GetAdvancedIncidentPVWithAgentResponse)get_store().add_element_user(GETADVANCEDINCIDENTPVWITHAGENTRESPONSE$0);
            return target;
        }
    }
    /**
     * An XML getAdvancedIncidentPVWithAgentResponse(@http://passengerview.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public static class GetAdvancedIncidentPVWithAgentResponseImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentPVWithAgentResponseDocument.GetAdvancedIncidentPVWithAgentResponse
    {
        
        public GetAdvancedIncidentPVWithAgentResponseImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName RETURN$0 = 
            new javax.xml.namespace.QName("http://passengerview.ws.nettracer.bagnet.com", "return");
        
        
        /**
         * Gets the "return" element
         */
        public com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident getReturn()
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident)get_store().find_element_user(RETURN$0, 0);
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
                com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident)get_store().find_element_user(RETURN$0, 0);
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
        public void setReturn(com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident xreturn)
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident)get_store().find_element_user(RETURN$0, 0);
                if (target == null)
                {
                    target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident)get_store().add_element_user(RETURN$0);
                }
                target.set(xreturn);
            }
        }
        
        /**
         * Appends and returns a new empty "return" element
         */
        public com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident addNewReturn()
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident)get_store().add_element_user(RETURN$0);
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
                com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident)get_store().find_element_user(RETURN$0, 0);
                if (target == null)
                {
                    target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident)get_store().add_element_user(RETURN$0);
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
