/*
 * An XML document type.
 * Localname: insertIncident
 * Namespace: http://core.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.core.InsertIncidentDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.core.impl;
/**
 * A document containing one insertIncident(@http://core.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public class InsertIncidentDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.core.InsertIncidentDocument
{
    
    public InsertIncidentDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName INSERTINCIDENT$0 = 
        new javax.xml.namespace.QName("http://core.ws.nettracer.bagnet.com", "insertIncident");
    
    
    /**
     * Gets the "insertIncident" element
     */
    public com.bagnet.nettracer.ws.core.InsertIncidentDocument.InsertIncident getInsertIncident()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.InsertIncidentDocument.InsertIncident target = null;
            target = (com.bagnet.nettracer.ws.core.InsertIncidentDocument.InsertIncident)get_store().find_element_user(INSERTINCIDENT$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "insertIncident" element
     */
    public void setInsertIncident(com.bagnet.nettracer.ws.core.InsertIncidentDocument.InsertIncident insertIncident)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.InsertIncidentDocument.InsertIncident target = null;
            target = (com.bagnet.nettracer.ws.core.InsertIncidentDocument.InsertIncident)get_store().find_element_user(INSERTINCIDENT$0, 0);
            if (target == null)
            {
                target = (com.bagnet.nettracer.ws.core.InsertIncidentDocument.InsertIncident)get_store().add_element_user(INSERTINCIDENT$0);
            }
            target.set(insertIncident);
        }
    }
    
    /**
     * Appends and returns a new empty "insertIncident" element
     */
    public com.bagnet.nettracer.ws.core.InsertIncidentDocument.InsertIncident addNewInsertIncident()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.InsertIncidentDocument.InsertIncident target = null;
            target = (com.bagnet.nettracer.ws.core.InsertIncidentDocument.InsertIncident)get_store().add_element_user(INSERTINCIDENT$0);
            return target;
        }
    }
    /**
     * An XML insertIncident(@http://core.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public static class InsertIncidentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.core.InsertIncidentDocument.InsertIncident
    {
        
        public InsertIncidentImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName SESSIONID$0 = 
            new javax.xml.namespace.QName("http://core.ws.nettracer.bagnet.com", "session_id");
        private static final javax.xml.namespace.QName SI$2 = 
            new javax.xml.namespace.QName("http://core.ws.nettracer.bagnet.com", "si");
        
        
        /**
         * Gets the "session_id" element
         */
        public java.lang.String getSessionId()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SESSIONID$0, 0);
                if (target == null)
                {
                    return null;
                }
                return target.getStringValue();
            }
        }
        
        /**
         * Gets (as xml) the "session_id" element
         */
        public org.apache.xmlbeans.XmlString xgetSessionId()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SESSIONID$0, 0);
                return target;
            }
        }
        
        /**
         * Tests for nil "session_id" element
         */
        public boolean isNilSessionId()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SESSIONID$0, 0);
                if (target == null) return false;
                return target.isNil();
            }
        }
        
        /**
         * True if has "session_id" element
         */
        public boolean isSetSessionId()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(SESSIONID$0) != 0;
            }
        }
        
        /**
         * Sets the "session_id" element
         */
        public void setSessionId(java.lang.String sessionId)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SESSIONID$0, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(SESSIONID$0);
                }
                target.setStringValue(sessionId);
            }
        }
        
        /**
         * Sets (as xml) the "session_id" element
         */
        public void xsetSessionId(org.apache.xmlbeans.XmlString sessionId)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SESSIONID$0, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(SESSIONID$0);
                }
                target.set(sessionId);
            }
        }
        
        /**
         * Nils the "session_id" element
         */
        public void setNilSessionId()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SESSIONID$0, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(SESSIONID$0);
                }
                target.setNil();
            }
        }
        
        /**
         * Unsets the "session_id" element
         */
        public void unsetSessionId()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(SESSIONID$0, 0);
            }
        }
        
        /**
         * Gets the "si" element
         */
        public com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident getSi()
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident)get_store().find_element_user(SI$2, 0);
                if (target == null)
                {
                    return null;
                }
                return target;
            }
        }
        
        /**
         * Tests for nil "si" element
         */
        public boolean isNilSi()
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident)get_store().find_element_user(SI$2, 0);
                if (target == null) return false;
                return target.isNil();
            }
        }
        
        /**
         * True if has "si" element
         */
        public boolean isSetSi()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(SI$2) != 0;
            }
        }
        
        /**
         * Sets the "si" element
         */
        public void setSi(com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident si)
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident)get_store().find_element_user(SI$2, 0);
                if (target == null)
                {
                    target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident)get_store().add_element_user(SI$2);
                }
                target.set(si);
            }
        }
        
        /**
         * Appends and returns a new empty "si" element
         */
        public com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident addNewSi()
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident)get_store().add_element_user(SI$2);
                return target;
            }
        }
        
        /**
         * Nils the "si" element
         */
        public void setNilSi()
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident)get_store().find_element_user(SI$2, 0);
                if (target == null)
                {
                    target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident)get_store().add_element_user(SI$2);
                }
                target.setNil();
            }
        }
        
        /**
         * Unsets the "si" element
         */
        public void unsetSi()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(SI$2, 0);
            }
        }
    }
}
