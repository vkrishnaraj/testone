/*
 * An XML document type.
 * Localname: authIncident
 * Namespace: http://onlineclaims.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.onlineclaims.AuthIncidentDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.onlineclaims.impl;
/**
 * A document containing one authIncident(@http://onlineclaims.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public class AuthIncidentDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.onlineclaims.AuthIncidentDocument
{
    private static final long serialVersionUID = 1L;
    
    public AuthIncidentDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName AUTHINCIDENT$0 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com", "authIncident");
    
    
    /**
     * Gets the "authIncident" element
     */
    public com.bagnet.nettracer.ws.onlineclaims.AuthIncidentDocument.AuthIncident getAuthIncident()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.AuthIncidentDocument.AuthIncident target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.AuthIncidentDocument.AuthIncident)get_store().find_element_user(AUTHINCIDENT$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "authIncident" element
     */
    public void setAuthIncident(com.bagnet.nettracer.ws.onlineclaims.AuthIncidentDocument.AuthIncident authIncident)
    {
        generatedSetterHelperImpl(authIncident, AUTHINCIDENT$0, 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
    }
    
    /**
     * Appends and returns a new empty "authIncident" element
     */
    public com.bagnet.nettracer.ws.onlineclaims.AuthIncidentDocument.AuthIncident addNewAuthIncident()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.AuthIncidentDocument.AuthIncident target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.AuthIncidentDocument.AuthIncident)get_store().add_element_user(AUTHINCIDENT$0);
            return target;
        }
    }
    /**
     * An XML authIncident(@http://onlineclaims.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public static class AuthIncidentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.onlineclaims.AuthIncidentDocument.AuthIncident
    {
        private static final long serialVersionUID = 1L;
        
        public AuthIncidentImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName FIRSTNAME$0 = 
            new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com", "firstName");
        private static final javax.xml.namespace.QName LASTNAME$2 = 
            new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com", "lastName");
        private static final javax.xml.namespace.QName PNR$4 = 
            new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com", "pnr");
        private static final javax.xml.namespace.QName AUTH$6 = 
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
         * Gets the "auth" element
         */
        public com.bagnet.nettracer.ws.onlineclaims.xsd.NtAuth getAuth()
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.bagnet.nettracer.ws.onlineclaims.xsd.NtAuth target = null;
                target = (com.bagnet.nettracer.ws.onlineclaims.xsd.NtAuth)get_store().find_element_user(AUTH$6, 0);
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
                target = (com.bagnet.nettracer.ws.onlineclaims.xsd.NtAuth)get_store().find_element_user(AUTH$6, 0);
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
                return get_store().count_elements(AUTH$6) != 0;
            }
        }
        
        /**
         * Sets the "auth" element
         */
        public void setAuth(com.bagnet.nettracer.ws.onlineclaims.xsd.NtAuth auth)
        {
            generatedSetterHelperImpl(auth, AUTH$6, 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
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
                target = (com.bagnet.nettracer.ws.onlineclaims.xsd.NtAuth)get_store().add_element_user(AUTH$6);
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
                target = (com.bagnet.nettracer.ws.onlineclaims.xsd.NtAuth)get_store().find_element_user(AUTH$6, 0);
                if (target == null)
                {
                    target = (com.bagnet.nettracer.ws.onlineclaims.xsd.NtAuth)get_store().add_element_user(AUTH$6);
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
                get_store().remove_element(AUTH$6, 0);
            }
        }
    }
}
