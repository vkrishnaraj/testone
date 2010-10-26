/*
 * An XML document type.
 * Localname: updateIncidentFaultCodesResponse
 * Namespace: http://core.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesResponseDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.core.impl;
/**
 * A document containing one updateIncidentFaultCodesResponse(@http://core.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public class UpdateIncidentFaultCodesResponseDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesResponseDocument
{
    
    public UpdateIncidentFaultCodesResponseDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName UPDATEINCIDENTFAULTCODESRESPONSE$0 = 
        new javax.xml.namespace.QName("http://core.ws.nettracer.bagnet.com", "updateIncidentFaultCodesResponse");
    
    
    /**
     * Gets the "updateIncidentFaultCodesResponse" element
     */
    public com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesResponseDocument.UpdateIncidentFaultCodesResponse getUpdateIncidentFaultCodesResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesResponseDocument.UpdateIncidentFaultCodesResponse target = null;
            target = (com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesResponseDocument.UpdateIncidentFaultCodesResponse)get_store().find_element_user(UPDATEINCIDENTFAULTCODESRESPONSE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "updateIncidentFaultCodesResponse" element
     */
    public void setUpdateIncidentFaultCodesResponse(com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesResponseDocument.UpdateIncidentFaultCodesResponse updateIncidentFaultCodesResponse)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesResponseDocument.UpdateIncidentFaultCodesResponse target = null;
            target = (com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesResponseDocument.UpdateIncidentFaultCodesResponse)get_store().find_element_user(UPDATEINCIDENTFAULTCODESRESPONSE$0, 0);
            if (target == null)
            {
                target = (com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesResponseDocument.UpdateIncidentFaultCodesResponse)get_store().add_element_user(UPDATEINCIDENTFAULTCODESRESPONSE$0);
            }
            target.set(updateIncidentFaultCodesResponse);
        }
    }
    
    /**
     * Appends and returns a new empty "updateIncidentFaultCodesResponse" element
     */
    public com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesResponseDocument.UpdateIncidentFaultCodesResponse addNewUpdateIncidentFaultCodesResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesResponseDocument.UpdateIncidentFaultCodesResponse target = null;
            target = (com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesResponseDocument.UpdateIncidentFaultCodesResponse)get_store().add_element_user(UPDATEINCIDENTFAULTCODESRESPONSE$0);
            return target;
        }
    }
    /**
     * An XML updateIncidentFaultCodesResponse(@http://core.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public static class UpdateIncidentFaultCodesResponseImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesResponseDocument.UpdateIncidentFaultCodesResponse
    {
        
        public UpdateIncidentFaultCodesResponseImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName RETURN$0 = 
            new javax.xml.namespace.QName("http://core.ws.nettracer.bagnet.com", "return");
        
        
        /**
         * Gets the "return" element
         */
        public com.bagnet.nettracer.ws.core.pojo.xsd.xsd.UpdateIncidentResponse getReturn()
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.bagnet.nettracer.ws.core.pojo.xsd.xsd.UpdateIncidentResponse target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.xsd.UpdateIncidentResponse)get_store().find_element_user(RETURN$0, 0);
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
                com.bagnet.nettracer.ws.core.pojo.xsd.xsd.UpdateIncidentResponse target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.xsd.UpdateIncidentResponse)get_store().find_element_user(RETURN$0, 0);
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
        public void setReturn(com.bagnet.nettracer.ws.core.pojo.xsd.xsd.UpdateIncidentResponse xreturn)
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.bagnet.nettracer.ws.core.pojo.xsd.xsd.UpdateIncidentResponse target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.xsd.UpdateIncidentResponse)get_store().find_element_user(RETURN$0, 0);
                if (target == null)
                {
                    target = (com.bagnet.nettracer.ws.core.pojo.xsd.xsd.UpdateIncidentResponse)get_store().add_element_user(RETURN$0);
                }
                target.set(xreturn);
            }
        }
        
        /**
         * Appends and returns a new empty "return" element
         */
        public com.bagnet.nettracer.ws.core.pojo.xsd.xsd.UpdateIncidentResponse addNewReturn()
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.bagnet.nettracer.ws.core.pojo.xsd.xsd.UpdateIncidentResponse target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.xsd.UpdateIncidentResponse)get_store().add_element_user(RETURN$0);
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
                com.bagnet.nettracer.ws.core.pojo.xsd.xsd.UpdateIncidentResponse target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.xsd.UpdateIncidentResponse)get_store().find_element_user(RETURN$0, 0);
                if (target == null)
                {
                    target = (com.bagnet.nettracer.ws.core.pojo.xsd.xsd.UpdateIncidentResponse)get_store().add_element_user(RETURN$0);
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
