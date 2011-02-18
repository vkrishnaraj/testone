/*
 * An XML document type.
 * Localname: saveNewIncident
 * Namespace: http://onlineclaims.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.onlineclaims.SaveNewIncidentDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.onlineclaims.impl;
/**
 * A document containing one saveNewIncident(@http://onlineclaims.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public class SaveNewIncidentDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.onlineclaims.SaveNewIncidentDocument
{
    
    public SaveNewIncidentDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName SAVENEWINCIDENT$0 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com", "saveNewIncident");
    
    
    /**
     * Gets the "saveNewIncident" element
     */
    public com.bagnet.nettracer.ws.onlineclaims.SaveNewIncidentDocument.SaveNewIncident getSaveNewIncident()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.SaveNewIncidentDocument.SaveNewIncident target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.SaveNewIncidentDocument.SaveNewIncident)get_store().find_element_user(SAVENEWINCIDENT$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "saveNewIncident" element
     */
    public void setSaveNewIncident(com.bagnet.nettracer.ws.onlineclaims.SaveNewIncidentDocument.SaveNewIncident saveNewIncident)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.SaveNewIncidentDocument.SaveNewIncident target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.SaveNewIncidentDocument.SaveNewIncident)get_store().find_element_user(SAVENEWINCIDENT$0, 0);
            if (target == null)
            {
                target = (com.bagnet.nettracer.ws.onlineclaims.SaveNewIncidentDocument.SaveNewIncident)get_store().add_element_user(SAVENEWINCIDENT$0);
            }
            target.set(saveNewIncident);
        }
    }
    
    /**
     * Appends and returns a new empty "saveNewIncident" element
     */
    public com.bagnet.nettracer.ws.onlineclaims.SaveNewIncidentDocument.SaveNewIncident addNewSaveNewIncident()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.SaveNewIncidentDocument.SaveNewIncident target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.SaveNewIncidentDocument.SaveNewIncident)get_store().add_element_user(SAVENEWINCIDENT$0);
            return target;
        }
    }
    /**
     * An XML saveNewIncident(@http://onlineclaims.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public static class SaveNewIncidentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.onlineclaims.SaveNewIncidentDocument.SaveNewIncident
    {
        
        public SaveNewIncidentImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName FIRSTNAME$0 = 
            new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com", "firstName");
        private static final javax.xml.namespace.QName LASTNAME$2 = 
            new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com", "lastName");
        private static final javax.xml.namespace.QName PNR$4 = 
            new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com", "pnr");
        private static final javax.xml.namespace.QName INCIDENT$6 = 
            new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com", "incident");
        private static final javax.xml.namespace.QName AUTH$8 = 
            new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com", "auth");
        
        
        /**
         * Gets the "firstName" element
         */
        public java.lang.String getFirstName()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FIRSTNAME$0, 0);
                if (target == null)
                {
                    return null;
                }
                return target.getStringValue();
            }
        }
        
        /**
         * Gets (as xml) the "firstName" element
         */
        public org.apache.xmlbeans.XmlString xgetFirstName()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FIRSTNAME$0, 0);
                return target;
            }
        }
        
        /**
         * Tests for nil "firstName" element
         */
        public boolean isNilFirstName()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FIRSTNAME$0, 0);
                if (target == null) return false;
                return target.isNil();
            }
        }
        
        /**
         * True if has "firstName" element
         */
        public boolean isSetFirstName()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(FIRSTNAME$0) != 0;
            }
        }
        
        /**
         * Sets the "firstName" element
         */
        public void setFirstName(java.lang.String firstName)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FIRSTNAME$0, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FIRSTNAME$0);
                }
                target.setStringValue(firstName);
            }
        }
        
        /**
         * Sets (as xml) the "firstName" element
         */
        public void xsetFirstName(org.apache.xmlbeans.XmlString firstName)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FIRSTNAME$0, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(FIRSTNAME$0);
                }
                target.set(firstName);
            }
        }
        
        /**
         * Nils the "firstName" element
         */
        public void setNilFirstName()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FIRSTNAME$0, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(FIRSTNAME$0);
                }
                target.setNil();
            }
        }
        
        /**
         * Unsets the "firstName" element
         */
        public void unsetFirstName()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(FIRSTNAME$0, 0);
            }
        }
        
        /**
         * Gets the "lastName" element
         */
        public java.lang.String getLastName()
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
         * Gets (as xml) the "lastName" element
         */
        public org.apache.xmlbeans.XmlString xgetLastName()
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
         * Tests for nil "lastName" element
         */
        public boolean isNilLastName()
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
         * True if has "lastName" element
         */
        public boolean isSetLastName()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(LASTNAME$2) != 0;
            }
        }
        
        /**
         * Sets the "lastName" element
         */
        public void setLastName(java.lang.String lastName)
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
                target.setStringValue(lastName);
            }
        }
        
        /**
         * Sets (as xml) the "lastName" element
         */
        public void xsetLastName(org.apache.xmlbeans.XmlString lastName)
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
                target.set(lastName);
            }
        }
        
        /**
         * Nils the "lastName" element
         */
        public void setNilLastName()
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
         * Unsets the "lastName" element
         */
        public void unsetLastName()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(LASTNAME$2, 0);
            }
        }
        
        /**
         * Gets the "pnr" element
         */
        public java.lang.String getPnr()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PNR$4, 0);
                if (target == null)
                {
                    return null;
                }
                return target.getStringValue();
            }
        }
        
        /**
         * Gets (as xml) the "pnr" element
         */
        public org.apache.xmlbeans.XmlString xgetPnr()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PNR$4, 0);
                return target;
            }
        }
        
        /**
         * Tests for nil "pnr" element
         */
        public boolean isNilPnr()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PNR$4, 0);
                if (target == null) return false;
                return target.isNil();
            }
        }
        
        /**
         * True if has "pnr" element
         */
        public boolean isSetPnr()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(PNR$4) != 0;
            }
        }
        
        /**
         * Sets the "pnr" element
         */
        public void setPnr(java.lang.String pnr)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PNR$4, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PNR$4);
                }
                target.setStringValue(pnr);
            }
        }
        
        /**
         * Sets (as xml) the "pnr" element
         */
        public void xsetPnr(org.apache.xmlbeans.XmlString pnr)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PNR$4, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PNR$4);
                }
                target.set(pnr);
            }
        }
        
        /**
         * Nils the "pnr" element
         */
        public void setNilPnr()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PNR$4, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PNR$4);
                }
                target.setNil();
            }
        }
        
        /**
         * Unsets the "pnr" element
         */
        public void unsetPnr()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(PNR$4, 0);
            }
        }
        
        /**
         * Gets the "incident" element
         */
        public com.bagnet.nettracer.ws.onlineclaims.xsd.Incident getIncident()
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.bagnet.nettracer.ws.onlineclaims.xsd.Incident target = null;
                target = (com.bagnet.nettracer.ws.onlineclaims.xsd.Incident)get_store().find_element_user(INCIDENT$6, 0);
                if (target == null)
                {
                    return null;
                }
                return target;
            }
        }
        
        /**
         * Tests for nil "incident" element
         */
        public boolean isNilIncident()
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.bagnet.nettracer.ws.onlineclaims.xsd.Incident target = null;
                target = (com.bagnet.nettracer.ws.onlineclaims.xsd.Incident)get_store().find_element_user(INCIDENT$6, 0);
                if (target == null) return false;
                return target.isNil();
            }
        }
        
        /**
         * True if has "incident" element
         */
        public boolean isSetIncident()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(INCIDENT$6) != 0;
            }
        }
        
        /**
         * Sets the "incident" element
         */
        public void setIncident(com.bagnet.nettracer.ws.onlineclaims.xsd.Incident incident)
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.bagnet.nettracer.ws.onlineclaims.xsd.Incident target = null;
                target = (com.bagnet.nettracer.ws.onlineclaims.xsd.Incident)get_store().find_element_user(INCIDENT$6, 0);
                if (target == null)
                {
                    target = (com.bagnet.nettracer.ws.onlineclaims.xsd.Incident)get_store().add_element_user(INCIDENT$6);
                }
                target.set(incident);
            }
        }
        
        /**
         * Appends and returns a new empty "incident" element
         */
        public com.bagnet.nettracer.ws.onlineclaims.xsd.Incident addNewIncident()
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.bagnet.nettracer.ws.onlineclaims.xsd.Incident target = null;
                target = (com.bagnet.nettracer.ws.onlineclaims.xsd.Incident)get_store().add_element_user(INCIDENT$6);
                return target;
            }
        }
        
        /**
         * Nils the "incident" element
         */
        public void setNilIncident()
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.bagnet.nettracer.ws.onlineclaims.xsd.Incident target = null;
                target = (com.bagnet.nettracer.ws.onlineclaims.xsd.Incident)get_store().find_element_user(INCIDENT$6, 0);
                if (target == null)
                {
                    target = (com.bagnet.nettracer.ws.onlineclaims.xsd.Incident)get_store().add_element_user(INCIDENT$6);
                }
                target.setNil();
            }
        }
        
        /**
         * Unsets the "incident" element
         */
        public void unsetIncident()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(INCIDENT$6, 0);
            }
        }
        
        /**
         * Gets the "auth" element
         */
        public com.bagnet.nettracer.ws.onlineclaims.xsd.NtAuth getAuth()
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.bagnet.nettracer.ws.onlineclaims.xsd.NtAuth target = null;
                target = (com.bagnet.nettracer.ws.onlineclaims.xsd.NtAuth)get_store().find_element_user(AUTH$8, 0);
                if (target == null)
                {
                    return null;
                }
                return target;
            }
        }
        
        /**
         * Tests for nil "auth" element
         */
        public boolean isNilAuth()
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.bagnet.nettracer.ws.onlineclaims.xsd.NtAuth target = null;
                target = (com.bagnet.nettracer.ws.onlineclaims.xsd.NtAuth)get_store().find_element_user(AUTH$8, 0);
                if (target == null) return false;
                return target.isNil();
            }
        }
        
        /**
         * True if has "auth" element
         */
        public boolean isSetAuth()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(AUTH$8) != 0;
            }
        }
        
        /**
         * Sets the "auth" element
         */
        public void setAuth(com.bagnet.nettracer.ws.onlineclaims.xsd.NtAuth auth)
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.bagnet.nettracer.ws.onlineclaims.xsd.NtAuth target = null;
                target = (com.bagnet.nettracer.ws.onlineclaims.xsd.NtAuth)get_store().find_element_user(AUTH$8, 0);
                if (target == null)
                {
                    target = (com.bagnet.nettracer.ws.onlineclaims.xsd.NtAuth)get_store().add_element_user(AUTH$8);
                }
                target.set(auth);
            }
        }
        
        /**
         * Appends and returns a new empty "auth" element
         */
        public com.bagnet.nettracer.ws.onlineclaims.xsd.NtAuth addNewAuth()
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.bagnet.nettracer.ws.onlineclaims.xsd.NtAuth target = null;
                target = (com.bagnet.nettracer.ws.onlineclaims.xsd.NtAuth)get_store().add_element_user(AUTH$8);
                return target;
            }
        }
        
        /**
         * Nils the "auth" element
         */
        public void setNilAuth()
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.bagnet.nettracer.ws.onlineclaims.xsd.NtAuth target = null;
                target = (com.bagnet.nettracer.ws.onlineclaims.xsd.NtAuth)get_store().find_element_user(AUTH$8, 0);
                if (target == null)
                {
                    target = (com.bagnet.nettracer.ws.onlineclaims.xsd.NtAuth)get_store().add_element_user(AUTH$8);
                }
                target.setNil();
            }
        }
        
        /**
         * Unsets the "auth" element
         */
        public void unsetAuth()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(AUTH$8, 0);
            }
        }
    }
}
