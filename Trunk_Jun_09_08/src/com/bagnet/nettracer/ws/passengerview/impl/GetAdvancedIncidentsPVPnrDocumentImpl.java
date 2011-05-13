/*
 * An XML document type.
 * Localname: getAdvancedIncidentsPVPnr
 * Namespace: http://passengerview.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.passengerview.impl;
/**
 * A document containing one getAdvancedIncidentsPVPnr(@http://passengerview.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public class GetAdvancedIncidentsPVPnrDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrDocument
{
    
    public GetAdvancedIncidentsPVPnrDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName GETADVANCEDINCIDENTSPVPNR$0 = 
        new javax.xml.namespace.QName("http://passengerview.ws.nettracer.bagnet.com", "getAdvancedIncidentsPVPnr");
    
    
    /**
     * Gets the "getAdvancedIncidentsPVPnr" element
     */
    public com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrDocument.GetAdvancedIncidentsPVPnr getGetAdvancedIncidentsPVPnr()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrDocument.GetAdvancedIncidentsPVPnr target = null;
            target = (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrDocument.GetAdvancedIncidentsPVPnr)get_store().find_element_user(GETADVANCEDINCIDENTSPVPNR$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "getAdvancedIncidentsPVPnr" element
     */
    public void setGetAdvancedIncidentsPVPnr(com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrDocument.GetAdvancedIncidentsPVPnr getAdvancedIncidentsPVPnr)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrDocument.GetAdvancedIncidentsPVPnr target = null;
            target = (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrDocument.GetAdvancedIncidentsPVPnr)get_store().find_element_user(GETADVANCEDINCIDENTSPVPNR$0, 0);
            if (target == null)
            {
                target = (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrDocument.GetAdvancedIncidentsPVPnr)get_store().add_element_user(GETADVANCEDINCIDENTSPVPNR$0);
            }
            target.set(getAdvancedIncidentsPVPnr);
        }
    }
    
    /**
     * Appends and returns a new empty "getAdvancedIncidentsPVPnr" element
     */
    public com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrDocument.GetAdvancedIncidentsPVPnr addNewGetAdvancedIncidentsPVPnr()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrDocument.GetAdvancedIncidentsPVPnr target = null;
            target = (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrDocument.GetAdvancedIncidentsPVPnr)get_store().add_element_user(GETADVANCEDINCIDENTSPVPNR$0);
            return target;
        }
    }
    /**
     * An XML getAdvancedIncidentsPVPnr(@http://passengerview.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public static class GetAdvancedIncidentsPVPnrImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrDocument.GetAdvancedIncidentsPVPnr
    {
        
        public GetAdvancedIncidentsPVPnrImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName RECORDLOCATOR$0 = 
            new javax.xml.namespace.QName("http://passengerview.ws.nettracer.bagnet.com", "recordLocator");
        private static final javax.xml.namespace.QName CALLINGAGENT$2 = 
            new javax.xml.namespace.QName("http://passengerview.ws.nettracer.bagnet.com", "callingAgent");
        
        
        /**
         * Gets the "recordLocator" element
         */
        public java.lang.String getRecordLocator()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(RECORDLOCATOR$0, 0);
                if (target == null)
                {
                    return null;
                }
                return target.getStringValue();
            }
        }
        
        /**
         * Gets (as xml) the "recordLocator" element
         */
        public org.apache.xmlbeans.XmlString xgetRecordLocator()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(RECORDLOCATOR$0, 0);
                return target;
            }
        }
        
        /**
         * Tests for nil "recordLocator" element
         */
        public boolean isNilRecordLocator()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(RECORDLOCATOR$0, 0);
                if (target == null) return false;
                return target.isNil();
            }
        }
        
        /**
         * True if has "recordLocator" element
         */
        public boolean isSetRecordLocator()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(RECORDLOCATOR$0) != 0;
            }
        }
        
        /**
         * Sets the "recordLocator" element
         */
        public void setRecordLocator(java.lang.String recordLocator)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(RECORDLOCATOR$0, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(RECORDLOCATOR$0);
                }
                target.setStringValue(recordLocator);
            }
        }
        
        /**
         * Sets (as xml) the "recordLocator" element
         */
        public void xsetRecordLocator(org.apache.xmlbeans.XmlString recordLocator)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(RECORDLOCATOR$0, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(RECORDLOCATOR$0);
                }
                target.set(recordLocator);
            }
        }
        
        /**
         * Nils the "recordLocator" element
         */
        public void setNilRecordLocator()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(RECORDLOCATOR$0, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(RECORDLOCATOR$0);
                }
                target.setNil();
            }
        }
        
        /**
         * Unsets the "recordLocator" element
         */
        public void unsetRecordLocator()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(RECORDLOCATOR$0, 0);
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
