/*
 * An XML document type.
 * Localname: getAdvancedIncidentsPVPhone
 * Namespace: http://passengerview.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPhoneDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.passengerview.impl;
/**
 * A document containing one getAdvancedIncidentsPVPhone(@http://passengerview.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public class GetAdvancedIncidentsPVPhoneDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPhoneDocument
{
    
    public GetAdvancedIncidentsPVPhoneDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName GETADVANCEDINCIDENTSPVPHONE$0 = 
        new javax.xml.namespace.QName("http://passengerview.ws.nettracer.bagnet.com", "getAdvancedIncidentsPVPhone");
    
    
    /**
     * Gets the "getAdvancedIncidentsPVPhone" element
     */
    public com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPhoneDocument.GetAdvancedIncidentsPVPhone getGetAdvancedIncidentsPVPhone()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPhoneDocument.GetAdvancedIncidentsPVPhone target = null;
            target = (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPhoneDocument.GetAdvancedIncidentsPVPhone)get_store().find_element_user(GETADVANCEDINCIDENTSPVPHONE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "getAdvancedIncidentsPVPhone" element
     */
    public void setGetAdvancedIncidentsPVPhone(com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPhoneDocument.GetAdvancedIncidentsPVPhone getAdvancedIncidentsPVPhone)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPhoneDocument.GetAdvancedIncidentsPVPhone target = null;
            target = (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPhoneDocument.GetAdvancedIncidentsPVPhone)get_store().find_element_user(GETADVANCEDINCIDENTSPVPHONE$0, 0);
            if (target == null)
            {
                target = (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPhoneDocument.GetAdvancedIncidentsPVPhone)get_store().add_element_user(GETADVANCEDINCIDENTSPVPHONE$0);
            }
            target.set(getAdvancedIncidentsPVPhone);
        }
    }
    
    /**
     * Appends and returns a new empty "getAdvancedIncidentsPVPhone" element
     */
    public com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPhoneDocument.GetAdvancedIncidentsPVPhone addNewGetAdvancedIncidentsPVPhone()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPhoneDocument.GetAdvancedIncidentsPVPhone target = null;
            target = (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPhoneDocument.GetAdvancedIncidentsPVPhone)get_store().add_element_user(GETADVANCEDINCIDENTSPVPHONE$0);
            return target;
        }
    }
    /**
     * An XML getAdvancedIncidentsPVPhone(@http://passengerview.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public static class GetAdvancedIncidentsPVPhoneImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPhoneDocument.GetAdvancedIncidentsPVPhone
    {
        
        public GetAdvancedIncidentsPVPhoneImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName PHONENUMBER$0 = 
            new javax.xml.namespace.QName("http://passengerview.ws.nettracer.bagnet.com", "phoneNumber");
        private static final javax.xml.namespace.QName CALLINGAGENT$2 = 
            new javax.xml.namespace.QName("http://passengerview.ws.nettracer.bagnet.com", "callingAgent");
        
        
        /**
         * Gets the "phoneNumber" element
         */
        public java.lang.String getPhoneNumber()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PHONENUMBER$0, 0);
                if (target == null)
                {
                    return null;
                }
                return target.getStringValue();
            }
        }
        
        /**
         * Gets (as xml) the "phoneNumber" element
         */
        public org.apache.xmlbeans.XmlString xgetPhoneNumber()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PHONENUMBER$0, 0);
                return target;
            }
        }
        
        /**
         * Tests for nil "phoneNumber" element
         */
        public boolean isNilPhoneNumber()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PHONENUMBER$0, 0);
                if (target == null) return false;
                return target.isNil();
            }
        }
        
        /**
         * True if has "phoneNumber" element
         */
        public boolean isSetPhoneNumber()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(PHONENUMBER$0) != 0;
            }
        }
        
        /**
         * Sets the "phoneNumber" element
         */
        public void setPhoneNumber(java.lang.String phoneNumber)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PHONENUMBER$0, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PHONENUMBER$0);
                }
                target.setStringValue(phoneNumber);
            }
        }
        
        /**
         * Sets (as xml) the "phoneNumber" element
         */
        public void xsetPhoneNumber(org.apache.xmlbeans.XmlString phoneNumber)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PHONENUMBER$0, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PHONENUMBER$0);
                }
                target.set(phoneNumber);
            }
        }
        
        /**
         * Nils the "phoneNumber" element
         */
        public void setNilPhoneNumber()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PHONENUMBER$0, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PHONENUMBER$0);
                }
                target.setNil();
            }
        }
        
        /**
         * Unsets the "phoneNumber" element
         */
        public void unsetPhoneNumber()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(PHONENUMBER$0, 0);
            }
        }
        
        /**
         * Gets the "callingAgent" element
         */
        public java.lang.String getCallingAgent()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CALLINGAGENT$2, 0);
                if (target == null)
                {
                    return null;
                }
                return target.getStringValue();
            }
        }
        
        /**
         * Gets (as xml) the "callingAgent" element
         */
        public org.apache.xmlbeans.XmlString xgetCallingAgent()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CALLINGAGENT$2, 0);
                return target;
            }
        }
        
        /**
         * Tests for nil "callingAgent" element
         */
        public boolean isNilCallingAgent()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CALLINGAGENT$2, 0);
                if (target == null) return false;
                return target.isNil();
            }
        }
        
        /**
         * True if has "callingAgent" element
         */
        public boolean isSetCallingAgent()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(CALLINGAGENT$2) != 0;
            }
        }
        
        /**
         * Sets the "callingAgent" element
         */
        public void setCallingAgent(java.lang.String callingAgent)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CALLINGAGENT$2, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CALLINGAGENT$2);
                }
                target.setStringValue(callingAgent);
            }
        }
        
        /**
         * Sets (as xml) the "callingAgent" element
         */
        public void xsetCallingAgent(org.apache.xmlbeans.XmlString callingAgent)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CALLINGAGENT$2, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CALLINGAGENT$2);
                }
                target.set(callingAgent);
            }
        }
        
        /**
         * Nils the "callingAgent" element
         */
        public void setNilCallingAgent()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CALLINGAGENT$2, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CALLINGAGENT$2);
                }
                target.setNil();
            }
        }
        
        /**
         * Unsets the "callingAgent" element
         */
        public void unsetCallingAgent()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(CALLINGAGENT$2, 0);
            }
        }
    }
}
