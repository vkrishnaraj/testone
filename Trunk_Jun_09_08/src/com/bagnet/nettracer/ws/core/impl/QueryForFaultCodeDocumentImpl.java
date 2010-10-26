/*
 * An XML document type.
 * Localname: queryForFaultCode
 * Namespace: http://core.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.core.QueryForFaultCodeDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.core.impl;
/**
 * A document containing one queryForFaultCode(@http://core.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public class QueryForFaultCodeDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.core.QueryForFaultCodeDocument
{
    
    public QueryForFaultCodeDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName QUERYFORFAULTCODE$0 = 
        new javax.xml.namespace.QName("http://core.ws.nettracer.bagnet.com", "queryForFaultCode");
    
    
    /**
     * Gets the "queryForFaultCode" element
     */
    public com.bagnet.nettracer.ws.core.QueryForFaultCodeDocument.QueryForFaultCode getQueryForFaultCode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.QueryForFaultCodeDocument.QueryForFaultCode target = null;
            target = (com.bagnet.nettracer.ws.core.QueryForFaultCodeDocument.QueryForFaultCode)get_store().find_element_user(QUERYFORFAULTCODE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "queryForFaultCode" element
     */
    public void setQueryForFaultCode(com.bagnet.nettracer.ws.core.QueryForFaultCodeDocument.QueryForFaultCode queryForFaultCode)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.QueryForFaultCodeDocument.QueryForFaultCode target = null;
            target = (com.bagnet.nettracer.ws.core.QueryForFaultCodeDocument.QueryForFaultCode)get_store().find_element_user(QUERYFORFAULTCODE$0, 0);
            if (target == null)
            {
                target = (com.bagnet.nettracer.ws.core.QueryForFaultCodeDocument.QueryForFaultCode)get_store().add_element_user(QUERYFORFAULTCODE$0);
            }
            target.set(queryForFaultCode);
        }
    }
    
    /**
     * Appends and returns a new empty "queryForFaultCode" element
     */
    public com.bagnet.nettracer.ws.core.QueryForFaultCodeDocument.QueryForFaultCode addNewQueryForFaultCode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.QueryForFaultCodeDocument.QueryForFaultCode target = null;
            target = (com.bagnet.nettracer.ws.core.QueryForFaultCodeDocument.QueryForFaultCode)get_store().add_element_user(QUERYFORFAULTCODE$0);
            return target;
        }
    }
    /**
     * An XML queryForFaultCode(@http://core.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public static class QueryForFaultCodeImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.core.QueryForFaultCodeDocument.QueryForFaultCode
    {
        
        public QueryForFaultCodeImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName SESSIONID$0 = 
            new javax.xml.namespace.QName("http://core.ws.nettracer.bagnet.com", "session_id");
        private static final javax.xml.namespace.QName FAULTSTATION$2 = 
            new javax.xml.namespace.QName("http://core.ws.nettracer.bagnet.com", "faultStation");
        private static final javax.xml.namespace.QName COMPANYCODE$4 = 
            new javax.xml.namespace.QName("http://core.ws.nettracer.bagnet.com", "companyCode");
        private static final javax.xml.namespace.QName FAULTCODE$6 = 
            new javax.xml.namespace.QName("http://core.ws.nettracer.bagnet.com", "faultCode");
        private static final javax.xml.namespace.QName INCIDENTTYPE$8 = 
            new javax.xml.namespace.QName("http://core.ws.nettracer.bagnet.com", "IncidentType");
        
        
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
         * Gets the "faultStation" element
         */
        public java.lang.String getFaultStation()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FAULTSTATION$2, 0);
                if (target == null)
                {
                    return null;
                }
                return target.getStringValue();
            }
        }
        
        /**
         * Gets (as xml) the "faultStation" element
         */
        public org.apache.xmlbeans.XmlString xgetFaultStation()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FAULTSTATION$2, 0);
                return target;
            }
        }
        
        /**
         * Tests for nil "faultStation" element
         */
        public boolean isNilFaultStation()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FAULTSTATION$2, 0);
                if (target == null) return false;
                return target.isNil();
            }
        }
        
        /**
         * True if has "faultStation" element
         */
        public boolean isSetFaultStation()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(FAULTSTATION$2) != 0;
            }
        }
        
        /**
         * Sets the "faultStation" element
         */
        public void setFaultStation(java.lang.String faultStation)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FAULTSTATION$2, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FAULTSTATION$2);
                }
                target.setStringValue(faultStation);
            }
        }
        
        /**
         * Sets (as xml) the "faultStation" element
         */
        public void xsetFaultStation(org.apache.xmlbeans.XmlString faultStation)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FAULTSTATION$2, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(FAULTSTATION$2);
                }
                target.set(faultStation);
            }
        }
        
        /**
         * Nils the "faultStation" element
         */
        public void setNilFaultStation()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FAULTSTATION$2, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(FAULTSTATION$2);
                }
                target.setNil();
            }
        }
        
        /**
         * Unsets the "faultStation" element
         */
        public void unsetFaultStation()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(FAULTSTATION$2, 0);
            }
        }
        
        /**
         * Gets the "companyCode" element
         */
        public java.lang.String getCompanyCode()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COMPANYCODE$4, 0);
                if (target == null)
                {
                    return null;
                }
                return target.getStringValue();
            }
        }
        
        /**
         * Gets (as xml) the "companyCode" element
         */
        public org.apache.xmlbeans.XmlString xgetCompanyCode()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COMPANYCODE$4, 0);
                return target;
            }
        }
        
        /**
         * Tests for nil "companyCode" element
         */
        public boolean isNilCompanyCode()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COMPANYCODE$4, 0);
                if (target == null) return false;
                return target.isNil();
            }
        }
        
        /**
         * True if has "companyCode" element
         */
        public boolean isSetCompanyCode()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(COMPANYCODE$4) != 0;
            }
        }
        
        /**
         * Sets the "companyCode" element
         */
        public void setCompanyCode(java.lang.String companyCode)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COMPANYCODE$4, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(COMPANYCODE$4);
                }
                target.setStringValue(companyCode);
            }
        }
        
        /**
         * Sets (as xml) the "companyCode" element
         */
        public void xsetCompanyCode(org.apache.xmlbeans.XmlString companyCode)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COMPANYCODE$4, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(COMPANYCODE$4);
                }
                target.set(companyCode);
            }
        }
        
        /**
         * Nils the "companyCode" element
         */
        public void setNilCompanyCode()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COMPANYCODE$4, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(COMPANYCODE$4);
                }
                target.setNil();
            }
        }
        
        /**
         * Unsets the "companyCode" element
         */
        public void unsetCompanyCode()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(COMPANYCODE$4, 0);
            }
        }
        
        /**
         * Gets the "faultCode" element
         */
        public int getFaultCode()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FAULTCODE$6, 0);
                if (target == null)
                {
                    return 0;
                }
                return target.getIntValue();
            }
        }
        
        /**
         * Gets (as xml) the "faultCode" element
         */
        public org.apache.xmlbeans.XmlInt xgetFaultCode()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlInt target = null;
                target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(FAULTCODE$6, 0);
                return target;
            }
        }
        
        /**
         * True if has "faultCode" element
         */
        public boolean isSetFaultCode()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(FAULTCODE$6) != 0;
            }
        }
        
        /**
         * Sets the "faultCode" element
         */
        public void setFaultCode(int faultCode)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FAULTCODE$6, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FAULTCODE$6);
                }
                target.setIntValue(faultCode);
            }
        }
        
        /**
         * Sets (as xml) the "faultCode" element
         */
        public void xsetFaultCode(org.apache.xmlbeans.XmlInt faultCode)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlInt target = null;
                target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(FAULTCODE$6, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(FAULTCODE$6);
                }
                target.set(faultCode);
            }
        }
        
        /**
         * Unsets the "faultCode" element
         */
        public void unsetFaultCode()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(FAULTCODE$6, 0);
            }
        }
        
        /**
         * Gets the "IncidentType" element
         */
        public java.lang.String getIncidentType()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(INCIDENTTYPE$8, 0);
                if (target == null)
                {
                    return null;
                }
                return target.getStringValue();
            }
        }
        
        /**
         * Gets (as xml) the "IncidentType" element
         */
        public org.apache.xmlbeans.XmlString xgetIncidentType()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(INCIDENTTYPE$8, 0);
                return target;
            }
        }
        
        /**
         * Tests for nil "IncidentType" element
         */
        public boolean isNilIncidentType()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(INCIDENTTYPE$8, 0);
                if (target == null) return false;
                return target.isNil();
            }
        }
        
        /**
         * True if has "IncidentType" element
         */
        public boolean isSetIncidentType()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(INCIDENTTYPE$8) != 0;
            }
        }
        
        /**
         * Sets the "IncidentType" element
         */
        public void setIncidentType(java.lang.String incidentType)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(INCIDENTTYPE$8, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(INCIDENTTYPE$8);
                }
                target.setStringValue(incidentType);
            }
        }
        
        /**
         * Sets (as xml) the "IncidentType" element
         */
        public void xsetIncidentType(org.apache.xmlbeans.XmlString incidentType)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(INCIDENTTYPE$8, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(INCIDENTTYPE$8);
                }
                target.set(incidentType);
            }
        }
        
        /**
         * Nils the "IncidentType" element
         */
        public void setNilIncidentType()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(INCIDENTTYPE$8, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(INCIDENTTYPE$8);
                }
                target.setNil();
            }
        }
        
        /**
         * Unsets the "IncidentType" element
         */
        public void unsetIncidentType()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(INCIDENTTYPE$8, 0);
            }
        }
    }
}
