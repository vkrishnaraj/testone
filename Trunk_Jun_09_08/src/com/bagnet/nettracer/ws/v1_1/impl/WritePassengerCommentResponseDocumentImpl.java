/*
 * An XML document type.
 * Localname: writePassengerCommentResponse
 * Namespace: http://v1_1.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.v1_1.WritePassengerCommentResponseDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.v1_1.impl;
/**
 * A document containing one writePassengerCommentResponse(@http://v1_1.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public class WritePassengerCommentResponseDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.v1_1.WritePassengerCommentResponseDocument
{
    
    public WritePassengerCommentResponseDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName WRITEPASSENGERCOMMENTRESPONSE$0 = 
        new javax.xml.namespace.QName("http://v1_1.ws.nettracer.bagnet.com", "writePassengerCommentResponse");
    
    
    /**
     * Gets the "writePassengerCommentResponse" element
     */
    public com.bagnet.nettracer.ws.v1_1.WritePassengerCommentResponseDocument.WritePassengerCommentResponse getWritePassengerCommentResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.v1_1.WritePassengerCommentResponseDocument.WritePassengerCommentResponse target = null;
            target = (com.bagnet.nettracer.ws.v1_1.WritePassengerCommentResponseDocument.WritePassengerCommentResponse)get_store().find_element_user(WRITEPASSENGERCOMMENTRESPONSE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "writePassengerCommentResponse" element
     */
    public void setWritePassengerCommentResponse(com.bagnet.nettracer.ws.v1_1.WritePassengerCommentResponseDocument.WritePassengerCommentResponse writePassengerCommentResponse)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.v1_1.WritePassengerCommentResponseDocument.WritePassengerCommentResponse target = null;
            target = (com.bagnet.nettracer.ws.v1_1.WritePassengerCommentResponseDocument.WritePassengerCommentResponse)get_store().find_element_user(WRITEPASSENGERCOMMENTRESPONSE$0, 0);
            if (target == null)
            {
                target = (com.bagnet.nettracer.ws.v1_1.WritePassengerCommentResponseDocument.WritePassengerCommentResponse)get_store().add_element_user(WRITEPASSENGERCOMMENTRESPONSE$0);
            }
            target.set(writePassengerCommentResponse);
        }
    }
    
    /**
     * Appends and returns a new empty "writePassengerCommentResponse" element
     */
    public com.bagnet.nettracer.ws.v1_1.WritePassengerCommentResponseDocument.WritePassengerCommentResponse addNewWritePassengerCommentResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.v1_1.WritePassengerCommentResponseDocument.WritePassengerCommentResponse target = null;
            target = (com.bagnet.nettracer.ws.v1_1.WritePassengerCommentResponseDocument.WritePassengerCommentResponse)get_store().add_element_user(WRITEPASSENGERCOMMENTRESPONSE$0);
            return target;
        }
    }
    /**
     * An XML writePassengerCommentResponse(@http://v1_1.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public static class WritePassengerCommentResponseImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.v1_1.WritePassengerCommentResponseDocument.WritePassengerCommentResponse
    {
        
        public WritePassengerCommentResponseImpl(org.apache.xmlbeans.SchemaType sType)
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
