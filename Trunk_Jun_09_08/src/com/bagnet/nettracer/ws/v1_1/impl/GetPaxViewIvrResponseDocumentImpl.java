/*
 * An XML document type.
 * Localname: getPaxViewIvrResponse
 * Namespace: http://v1_1.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.v1_1.GetPaxViewIvrResponseDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.v1_1.impl;
/**
 * A document containing one getPaxViewIvrResponse(@http://v1_1.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public class GetPaxViewIvrResponseDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.v1_1.GetPaxViewIvrResponseDocument
{
    
    public GetPaxViewIvrResponseDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName GETPAXVIEWIVRRESPONSE$0 = 
        new javax.xml.namespace.QName("http://v1_1.ws.nettracer.bagnet.com", "getPaxViewIvrResponse");
    
    
    /**
     * Gets the "getPaxViewIvrResponse" element
     */
    public com.bagnet.nettracer.ws.v1_1.GetPaxViewIvrResponseDocument.GetPaxViewIvrResponse getGetPaxViewIvrResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.v1_1.GetPaxViewIvrResponseDocument.GetPaxViewIvrResponse target = null;
            target = (com.bagnet.nettracer.ws.v1_1.GetPaxViewIvrResponseDocument.GetPaxViewIvrResponse)get_store().find_element_user(GETPAXVIEWIVRRESPONSE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "getPaxViewIvrResponse" element
     */
    public void setGetPaxViewIvrResponse(com.bagnet.nettracer.ws.v1_1.GetPaxViewIvrResponseDocument.GetPaxViewIvrResponse getPaxViewIvrResponse)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.v1_1.GetPaxViewIvrResponseDocument.GetPaxViewIvrResponse target = null;
            target = (com.bagnet.nettracer.ws.v1_1.GetPaxViewIvrResponseDocument.GetPaxViewIvrResponse)get_store().find_element_user(GETPAXVIEWIVRRESPONSE$0, 0);
            if (target == null)
            {
                target = (com.bagnet.nettracer.ws.v1_1.GetPaxViewIvrResponseDocument.GetPaxViewIvrResponse)get_store().add_element_user(GETPAXVIEWIVRRESPONSE$0);
            }
            target.set(getPaxViewIvrResponse);
        }
    }
    
    /**
     * Appends and returns a new empty "getPaxViewIvrResponse" element
     */
    public com.bagnet.nettracer.ws.v1_1.GetPaxViewIvrResponseDocument.GetPaxViewIvrResponse addNewGetPaxViewIvrResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.v1_1.GetPaxViewIvrResponseDocument.GetPaxViewIvrResponse target = null;
            target = (com.bagnet.nettracer.ws.v1_1.GetPaxViewIvrResponseDocument.GetPaxViewIvrResponse)get_store().add_element_user(GETPAXVIEWIVRRESPONSE$0);
            return target;
        }
    }
    /**
     * An XML getPaxViewIvrResponse(@http://v1_1.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public static class GetPaxViewIvrResponseImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.v1_1.GetPaxViewIvrResponseDocument.GetPaxViewIvrResponse
    {
        
        public GetPaxViewIvrResponseImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName RETURN$0 = 
            new javax.xml.namespace.QName("http://v1_1.ws.nettracer.bagnet.com", "return");
        
        
        /**
         * Gets the "return" element
         */
        public com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVIncident getReturn()
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVIncident target = null;
                target = (com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVIncident)get_store().find_element_user(RETURN$0, 0);
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
                com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVIncident target = null;
                target = (com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVIncident)get_store().find_element_user(RETURN$0, 0);
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
        public void setReturn(com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVIncident xreturn)
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVIncident target = null;
                target = (com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVIncident)get_store().find_element_user(RETURN$0, 0);
                if (target == null)
                {
                    target = (com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVIncident)get_store().add_element_user(RETURN$0);
                }
                target.set(xreturn);
            }
        }
        
        /**
         * Appends and returns a new empty "return" element
         */
        public com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVIncident addNewReturn()
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVIncident target = null;
                target = (com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVIncident)get_store().add_element_user(RETURN$0);
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
                com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVIncident target = null;
                target = (com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVIncident)get_store().find_element_user(RETURN$0, 0);
                if (target == null)
                {
                    target = (com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVIncident)get_store().add_element_user(RETURN$0);
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
