/*
 * An XML document type.
 * Localname: getIncidentPV
 * Namespace: http://passengerview.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.passengerview.GetIncidentPVDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.passengerview.impl;
/**
 * A document containing one getIncidentPV(@http://passengerview.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public class GetIncidentPVDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.passengerview.GetIncidentPVDocument
{
    
    public GetIncidentPVDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName GETINCIDENTPV$0 = 
        new javax.xml.namespace.QName("http://passengerview.ws.nettracer.bagnet.com", "getIncidentPV");
    
    
    /**
     * Gets the "getIncidentPV" element
     */
    public com.bagnet.nettracer.ws.passengerview.GetIncidentPVDocument.GetIncidentPV getGetIncidentPV()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.passengerview.GetIncidentPVDocument.GetIncidentPV target = null;
            target = (com.bagnet.nettracer.ws.passengerview.GetIncidentPVDocument.GetIncidentPV)get_store().find_element_user(GETINCIDENTPV$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "getIncidentPV" element
     */
    public void setGetIncidentPV(com.bagnet.nettracer.ws.passengerview.GetIncidentPVDocument.GetIncidentPV getIncidentPV)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.passengerview.GetIncidentPVDocument.GetIncidentPV target = null;
            target = (com.bagnet.nettracer.ws.passengerview.GetIncidentPVDocument.GetIncidentPV)get_store().find_element_user(GETINCIDENTPV$0, 0);
            if (target == null)
            {
                target = (com.bagnet.nettracer.ws.passengerview.GetIncidentPVDocument.GetIncidentPV)get_store().add_element_user(GETINCIDENTPV$0);
            }
            target.set(getIncidentPV);
        }
    }
    
    /**
     * Appends and returns a new empty "getIncidentPV" element
     */
    public com.bagnet.nettracer.ws.passengerview.GetIncidentPVDocument.GetIncidentPV addNewGetIncidentPV()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.passengerview.GetIncidentPVDocument.GetIncidentPV target = null;
            target = (com.bagnet.nettracer.ws.passengerview.GetIncidentPVDocument.GetIncidentPV)get_store().add_element_user(GETINCIDENTPV$0);
            return target;
        }
    }
    /**
     * An XML getIncidentPV(@http://passengerview.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public static class GetIncidentPVImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.passengerview.GetIncidentPVDocument.GetIncidentPV
    {
        
        public GetIncidentPVImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName INCIDENTID$0 = 
            new javax.xml.namespace.QName("http://passengerview.ws.nettracer.bagnet.com", "incident_id");
        private static final javax.xml.namespace.QName LASTNAME$2 = 
            new javax.xml.namespace.QName("http://passengerview.ws.nettracer.bagnet.com", "lastname");
        
        
        /**
         * Gets the "incident_id" element
         */
        public java.lang.String getIncidentId()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(INCIDENTID$0, 0);
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
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(INCIDENTID$0, 0);
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
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(INCIDENTID$0, 0);
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
                return get_store().count_elements(INCIDENTID$0) != 0;
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
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(INCIDENTID$0, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(INCIDENTID$0);
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
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(INCIDENTID$0, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(INCIDENTID$0);
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
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(INCIDENTID$0, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(INCIDENTID$0);
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
                get_store().remove_element(INCIDENTID$0, 0);
            }
        }
        
        /**
         * Gets the "lastname" element
         */
        public java.lang.String getLastname()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LASTNAME$2, 0);
                if (target == null)
                {
                    return null;
                }
                return target.getStringValue();
            }
        }
        
        /**
         * Gets (as xml) the "lastname" element
         */
        public org.apache.xmlbeans.XmlString xgetLastname()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(LASTNAME$2, 0);
                return target;
            }
        }
        
        /**
         * Tests for nil "lastname" element
         */
        public boolean isNilLastname()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(LASTNAME$2, 0);
                if (target == null) return false;
                return target.isNil();
            }
        }
        
        /**
         * True if has "lastname" element
         */
        public boolean isSetLastname()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(LASTNAME$2) != 0;
            }
        }
        
        /**
         * Sets the "lastname" element
         */
        public void setLastname(java.lang.String lastname)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LASTNAME$2, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(LASTNAME$2);
                }
                target.setStringValue(lastname);
            }
        }
        
        /**
         * Sets (as xml) the "lastname" element
         */
        public void xsetLastname(org.apache.xmlbeans.XmlString lastname)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(LASTNAME$2, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(LASTNAME$2);
                }
                target.set(lastname);
            }
        }
        
        /**
         * Nils the "lastname" element
         */
        public void setNilLastname()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(LASTNAME$2, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(LASTNAME$2);
                }
                target.setNil();
            }
        }
        
        /**
         * Unsets the "lastname" element
         */
        public void unsetLastname()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(LASTNAME$2, 0);
            }
        }
    }
}
