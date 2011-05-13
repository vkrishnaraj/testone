/*
 * An XML document type.
 * Localname: getAdvancedIncidentsPVFrequentFlyer
 * Namespace: http://passengerview.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVFrequentFlyerDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.passengerview.impl;
/**
 * A document containing one getAdvancedIncidentsPVFrequentFlyer(@http://passengerview.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public class GetAdvancedIncidentsPVFrequentFlyerDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVFrequentFlyerDocument
{
    
    public GetAdvancedIncidentsPVFrequentFlyerDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName GETADVANCEDINCIDENTSPVFREQUENTFLYER$0 = 
        new javax.xml.namespace.QName("http://passengerview.ws.nettracer.bagnet.com", "getAdvancedIncidentsPVFrequentFlyer");
    
    
    /**
     * Gets the "getAdvancedIncidentsPVFrequentFlyer" element
     */
    public com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVFrequentFlyerDocument.GetAdvancedIncidentsPVFrequentFlyer getGetAdvancedIncidentsPVFrequentFlyer()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVFrequentFlyerDocument.GetAdvancedIncidentsPVFrequentFlyer target = null;
            target = (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVFrequentFlyerDocument.GetAdvancedIncidentsPVFrequentFlyer)get_store().find_element_user(GETADVANCEDINCIDENTSPVFREQUENTFLYER$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "getAdvancedIncidentsPVFrequentFlyer" element
     */
    public void setGetAdvancedIncidentsPVFrequentFlyer(com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVFrequentFlyerDocument.GetAdvancedIncidentsPVFrequentFlyer getAdvancedIncidentsPVFrequentFlyer)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVFrequentFlyerDocument.GetAdvancedIncidentsPVFrequentFlyer target = null;
            target = (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVFrequentFlyerDocument.GetAdvancedIncidentsPVFrequentFlyer)get_store().find_element_user(GETADVANCEDINCIDENTSPVFREQUENTFLYER$0, 0);
            if (target == null)
            {
                target = (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVFrequentFlyerDocument.GetAdvancedIncidentsPVFrequentFlyer)get_store().add_element_user(GETADVANCEDINCIDENTSPVFREQUENTFLYER$0);
            }
            target.set(getAdvancedIncidentsPVFrequentFlyer);
        }
    }
    
    /**
     * Appends and returns a new empty "getAdvancedIncidentsPVFrequentFlyer" element
     */
    public com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVFrequentFlyerDocument.GetAdvancedIncidentsPVFrequentFlyer addNewGetAdvancedIncidentsPVFrequentFlyer()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVFrequentFlyerDocument.GetAdvancedIncidentsPVFrequentFlyer target = null;
            target = (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVFrequentFlyerDocument.GetAdvancedIncidentsPVFrequentFlyer)get_store().add_element_user(GETADVANCEDINCIDENTSPVFREQUENTFLYER$0);
            return target;
        }
    }
    /**
     * An XML getAdvancedIncidentsPVFrequentFlyer(@http://passengerview.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public static class GetAdvancedIncidentsPVFrequentFlyerImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVFrequentFlyerDocument.GetAdvancedIncidentsPVFrequentFlyer
    {
        
        public GetAdvancedIncidentsPVFrequentFlyerImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName FREQUENTFLYER$0 = 
            new javax.xml.namespace.QName("http://passengerview.ws.nettracer.bagnet.com", "frequentFlyer");
        private static final javax.xml.namespace.QName CALLINGAGENT$2 = 
            new javax.xml.namespace.QName("http://passengerview.ws.nettracer.bagnet.com", "callingAgent");
        
        
        /**
         * Gets the "frequentFlyer" element
         */
        public java.lang.String getFrequentFlyer()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FREQUENTFLYER$0, 0);
                if (target == null)
                {
                    return null;
                }
                return target.getStringValue();
            }
        }
        
        /**
         * Gets (as xml) the "frequentFlyer" element
         */
        public org.apache.xmlbeans.XmlString xgetFrequentFlyer()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FREQUENTFLYER$0, 0);
                return target;
            }
        }
        
        /**
         * Tests for nil "frequentFlyer" element
         */
        public boolean isNilFrequentFlyer()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FREQUENTFLYER$0, 0);
                if (target == null) return false;
                return target.isNil();
            }
        }
        
        /**
         * True if has "frequentFlyer" element
         */
        public boolean isSetFrequentFlyer()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(FREQUENTFLYER$0) != 0;
            }
        }
        
        /**
         * Sets the "frequentFlyer" element
         */
        public void setFrequentFlyer(java.lang.String frequentFlyer)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FREQUENTFLYER$0, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FREQUENTFLYER$0);
                }
                target.setStringValue(frequentFlyer);
            }
        }
        
        /**
         * Sets (as xml) the "frequentFlyer" element
         */
        public void xsetFrequentFlyer(org.apache.xmlbeans.XmlString frequentFlyer)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FREQUENTFLYER$0, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(FREQUENTFLYER$0);
                }
                target.set(frequentFlyer);
            }
        }
        
        /**
         * Nils the "frequentFlyer" element
         */
        public void setNilFrequentFlyer()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FREQUENTFLYER$0, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(FREQUENTFLYER$0);
                }
                target.setNil();
            }
        }
        
        /**
         * Unsets the "frequentFlyer" element
         */
        public void unsetFrequentFlyer()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(FREQUENTFLYER$0, 0);
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
