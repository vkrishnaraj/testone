/*
 * An XML document type.
 * Localname: saveNewIncidentResponse
 * Namespace: http://onlineclaims.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.onlineclaims.SaveNewIncidentResponseDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.onlineclaims.impl;
/**
 * A document containing one saveNewIncidentResponse(@http://onlineclaims.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public class SaveNewIncidentResponseDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.onlineclaims.SaveNewIncidentResponseDocument
{
    private static final long serialVersionUID = 1L;
    
    public SaveNewIncidentResponseDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName SAVENEWINCIDENTRESPONSE$0 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com", "saveNewIncidentResponse");
    
    
    /**
     * Gets the "saveNewIncidentResponse" element
     */
    public com.bagnet.nettracer.ws.onlineclaims.SaveNewIncidentResponseDocument.SaveNewIncidentResponse getSaveNewIncidentResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.SaveNewIncidentResponseDocument.SaveNewIncidentResponse target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.SaveNewIncidentResponseDocument.SaveNewIncidentResponse)get_store().find_element_user(SAVENEWINCIDENTRESPONSE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "saveNewIncidentResponse" element
     */
    public void setSaveNewIncidentResponse(com.bagnet.nettracer.ws.onlineclaims.SaveNewIncidentResponseDocument.SaveNewIncidentResponse saveNewIncidentResponse)
    {
        generatedSetterHelperImpl(saveNewIncidentResponse, SAVENEWINCIDENTRESPONSE$0, 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
    }
    
    /**
     * Appends and returns a new empty "saveNewIncidentResponse" element
     */
    public com.bagnet.nettracer.ws.onlineclaims.SaveNewIncidentResponseDocument.SaveNewIncidentResponse addNewSaveNewIncidentResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.SaveNewIncidentResponseDocument.SaveNewIncidentResponse target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.SaveNewIncidentResponseDocument.SaveNewIncidentResponse)get_store().add_element_user(SAVENEWINCIDENTRESPONSE$0);
            return target;
        }
    }
    /**
     * An XML saveNewIncidentResponse(@http://onlineclaims.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public static class SaveNewIncidentResponseImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.onlineclaims.SaveNewIncidentResponseDocument.SaveNewIncidentResponse
    {
        private static final long serialVersionUID = 1L;
        
        public SaveNewIncidentResponseImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName RETURN$0 = 
            new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com", "return");
        
        
        /**
         * Gets the "return" element
         */
        public java.lang.String getReturn()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(RETURN$0, 0);
                if (target == null)
                {
                    return null;
                }
                return target.getStringValue();
            }
        }
        
        /**
         * Gets (as xml) the "return" element
         */
        public org.apache.xmlbeans.XmlString xgetReturn()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(RETURN$0, 0);
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
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(RETURN$0, 0);
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
        public void setReturn(java.lang.String xreturn)
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
                target.setStringValue(xreturn);
            }
        }
        
        /**
         * Sets (as xml) the "return" element
         */
        public void xsetReturn(org.apache.xmlbeans.XmlString xreturn)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(RETURN$0, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(RETURN$0);
                }
                target.set(xreturn);
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
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(RETURN$0, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(RETURN$0);
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
