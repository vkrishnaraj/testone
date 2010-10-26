/*
 * An XML document type.
 * Localname: getIncident
 * Namespace: http://core.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.core.GetIncidentDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.core.impl;
/**
 * A document containing one getIncident(@http://core.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public class GetIncidentDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.core.GetIncidentDocument
{
    
    public GetIncidentDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName GETINCIDENT$0 = 
        new javax.xml.namespace.QName("http://core.ws.nettracer.bagnet.com", "getIncident");
    
    
    /**
     * Gets the "getIncident" element
     */
    public com.bagnet.nettracer.ws.core.GetIncidentDocument.GetIncident getGetIncident()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.GetIncidentDocument.GetIncident target = null;
            target = (com.bagnet.nettracer.ws.core.GetIncidentDocument.GetIncident)get_store().find_element_user(GETINCIDENT$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "getIncident" element
     */
    public void setGetIncident(com.bagnet.nettracer.ws.core.GetIncidentDocument.GetIncident getIncident)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.GetIncidentDocument.GetIncident target = null;
            target = (com.bagnet.nettracer.ws.core.GetIncidentDocument.GetIncident)get_store().find_element_user(GETINCIDENT$0, 0);
            if (target == null)
            {
                target = (com.bagnet.nettracer.ws.core.GetIncidentDocument.GetIncident)get_store().add_element_user(GETINCIDENT$0);
            }
            target.set(getIncident);
        }
    }
    
    /**
     * Appends and returns a new empty "getIncident" element
     */
    public com.bagnet.nettracer.ws.core.GetIncidentDocument.GetIncident addNewGetIncident()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.GetIncidentDocument.GetIncident target = null;
            target = (com.bagnet.nettracer.ws.core.GetIncidentDocument.GetIncident)get_store().add_element_user(GETINCIDENT$0);
            return target;
        }
    }
    /**
     * An XML getIncident(@http://core.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public static class GetIncidentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.core.GetIncidentDocument.GetIncident
    {
        
        public GetIncidentImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName SESSIONID$0 = 
            new javax.xml.namespace.QName("http://core.ws.nettracer.bagnet.com", "session_id");
        private static final javax.xml.namespace.QName INCIDENTID$2 = 
            new javax.xml.namespace.QName("http://core.ws.nettracer.bagnet.com", "incident_id");
        private static final javax.xml.namespace.QName INCTYPE$4 = 
            new javax.xml.namespace.QName("http://core.ws.nettracer.bagnet.com", "inc_type");
        
        
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
         * Gets the "incident_id" element
         */
        public java.lang.String getIncidentId()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(INCIDENTID$2, 0);
                if (target == null)
                {
                    return null;
                }
                return target.getStringValue();
            }
        }
        
        /**
         * Gets (as xml) the "incident_id" element
         */
        public org.apache.xmlbeans.XmlString xgetIncidentId()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(INCIDENTID$2, 0);
                return target;
            }
        }
        
        /**
         * Tests for nil "incident_id" element
         */
        public boolean isNilIncidentId()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(INCIDENTID$2, 0);
                if (target == null) return false;
                return target.isNil();
            }
        }
        
        /**
         * True if has "incident_id" element
         */
        public boolean isSetIncidentId()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(INCIDENTID$2) != 0;
            }
        }
        
        /**
         * Sets the "incident_id" element
         */
        public void setIncidentId(java.lang.String incidentId)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(INCIDENTID$2, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(INCIDENTID$2);
                }
                target.setStringValue(incidentId);
            }
        }
        
        /**
         * Sets (as xml) the "incident_id" element
         */
        public void xsetIncidentId(org.apache.xmlbeans.XmlString incidentId)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(INCIDENTID$2, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(INCIDENTID$2);
                }
                target.set(incidentId);
            }
        }
        
        /**
         * Nils the "incident_id" element
         */
        public void setNilIncidentId()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(INCIDENTID$2, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(INCIDENTID$2);
                }
                target.setNil();
            }
        }
        
        /**
         * Unsets the "incident_id" element
         */
        public void unsetIncidentId()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(INCIDENTID$2, 0);
            }
        }
        
        /**
         * Gets the "inc_type" element
         */
        public java.lang.String getIncType()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(INCTYPE$4, 0);
                if (target == null)
                {
                    return null;
                }
                return target.getStringValue();
            }
        }
        
        /**
         * Gets (as xml) the "inc_type" element
         */
        public org.apache.xmlbeans.XmlString xgetIncType()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(INCTYPE$4, 0);
                return target;
            }
        }
        
        /**
         * Tests for nil "inc_type" element
         */
        public boolean isNilIncType()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(INCTYPE$4, 0);
                if (target == null) return false;
                return target.isNil();
            }
        }
        
        /**
         * True if has "inc_type" element
         */
        public boolean isSetIncType()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(INCTYPE$4) != 0;
            }
        }
        
        /**
         * Sets the "inc_type" element
         */
        public void setIncType(java.lang.String incType)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(INCTYPE$4, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(INCTYPE$4);
                }
                target.setStringValue(incType);
            }
        }
        
        /**
         * Sets (as xml) the "inc_type" element
         */
        public void xsetIncType(org.apache.xmlbeans.XmlString incType)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(INCTYPE$4, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(INCTYPE$4);
                }
                target.set(incType);
            }
        }
        
        /**
         * Nils the "inc_type" element
         */
        public void setNilIncType()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(INCTYPE$4, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(INCTYPE$4);
                }
                target.setNil();
            }
        }
        
        /**
         * Unsets the "inc_type" element
         */
        public void unsetIncType()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(INCTYPE$4, 0);
            }
        }
    }
}
