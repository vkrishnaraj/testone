/*
 * An XML document type.
 * Localname: UpdateBdoDeliveryResponse
 * Namespace: http://core.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.core.UpdateBdoDeliveryResponseDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.core.impl;
/**
 * A document containing one UpdateBdoDeliveryResponse(@http://core.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public class UpdateBdoDeliveryResponseDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.core.UpdateBdoDeliveryResponseDocument
{
    
    public UpdateBdoDeliveryResponseDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName UPDATEBDODELIVERYRESPONSE$0 = 
        new javax.xml.namespace.QName("http://core.ws.nettracer.bagnet.com", "UpdateBdoDeliveryResponse");
    
    
    /**
     * Gets the "UpdateBdoDeliveryResponse" element
     */
    public com.bagnet.nettracer.ws.core.UpdateBdoDeliveryResponseDocument.UpdateBdoDeliveryResponse getUpdateBdoDeliveryResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.UpdateBdoDeliveryResponseDocument.UpdateBdoDeliveryResponse target = null;
            target = (com.bagnet.nettracer.ws.core.UpdateBdoDeliveryResponseDocument.UpdateBdoDeliveryResponse)get_store().find_element_user(UPDATEBDODELIVERYRESPONSE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "UpdateBdoDeliveryResponse" element
     */
    public void setUpdateBdoDeliveryResponse(com.bagnet.nettracer.ws.core.UpdateBdoDeliveryResponseDocument.UpdateBdoDeliveryResponse updateBdoDeliveryResponse)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.UpdateBdoDeliveryResponseDocument.UpdateBdoDeliveryResponse target = null;
            target = (com.bagnet.nettracer.ws.core.UpdateBdoDeliveryResponseDocument.UpdateBdoDeliveryResponse)get_store().find_element_user(UPDATEBDODELIVERYRESPONSE$0, 0);
            if (target == null)
            {
                target = (com.bagnet.nettracer.ws.core.UpdateBdoDeliveryResponseDocument.UpdateBdoDeliveryResponse)get_store().add_element_user(UPDATEBDODELIVERYRESPONSE$0);
            }
            target.set(updateBdoDeliveryResponse);
        }
    }
    
    /**
     * Appends and returns a new empty "UpdateBdoDeliveryResponse" element
     */
    public com.bagnet.nettracer.ws.core.UpdateBdoDeliveryResponseDocument.UpdateBdoDeliveryResponse addNewUpdateBdoDeliveryResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.UpdateBdoDeliveryResponseDocument.UpdateBdoDeliveryResponse target = null;
            target = (com.bagnet.nettracer.ws.core.UpdateBdoDeliveryResponseDocument.UpdateBdoDeliveryResponse)get_store().add_element_user(UPDATEBDODELIVERYRESPONSE$0);
            return target;
        }
    }
    /**
     * An XML UpdateBdoDeliveryResponse(@http://core.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public static class UpdateBdoDeliveryResponseImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.core.UpdateBdoDeliveryResponseDocument.UpdateBdoDeliveryResponse
    {
        
        public UpdateBdoDeliveryResponseImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName RETURN$0 = 
            new javax.xml.namespace.QName("http://core.ws.nettracer.bagnet.com", "return");
        
        
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
