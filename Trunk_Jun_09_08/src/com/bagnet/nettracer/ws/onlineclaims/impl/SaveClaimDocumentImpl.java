/*
 * An XML document type.
 * Localname: saveClaim
 * Namespace: http://onlineclaims.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.onlineclaims.SaveClaimDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.onlineclaims.impl;
/**
 * A document containing one saveClaim(@http://onlineclaims.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public class SaveClaimDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.onlineclaims.SaveClaimDocument
{
    private static final long serialVersionUID = 1L;
    
    public SaveClaimDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName SAVECLAIM$0 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com", "saveClaim");
    
    
    /**
     * Gets the "saveClaim" element
     */
    public com.bagnet.nettracer.ws.onlineclaims.SaveClaimDocument.SaveClaim getSaveClaim()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.SaveClaimDocument.SaveClaim target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.SaveClaimDocument.SaveClaim)get_store().find_element_user(SAVECLAIM$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "saveClaim" element
     */
    public void setSaveClaim(com.bagnet.nettracer.ws.onlineclaims.SaveClaimDocument.SaveClaim saveClaim)
    {
        generatedSetterHelperImpl(saveClaim, SAVECLAIM$0, 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
    }
    
    /**
     * Appends and returns a new empty "saveClaim" element
     */
    public com.bagnet.nettracer.ws.onlineclaims.SaveClaimDocument.SaveClaim addNewSaveClaim()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.SaveClaimDocument.SaveClaim target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.SaveClaimDocument.SaveClaim)get_store().add_element_user(SAVECLAIM$0);
            return target;
        }
    }
    /**
     * An XML saveClaim(@http://onlineclaims.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public static class SaveClaimImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.onlineclaims.SaveClaimDocument.SaveClaim
    {
        private static final long serialVersionUID = 1L;
        
        public SaveClaimImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName NAME$0 = 
            new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com", "name");
        private static final javax.xml.namespace.QName INCIDENTID$2 = 
            new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com", "incidentId");
        private static final javax.xml.namespace.QName CLAIM$4 = 
            new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com", "claim");
        private static final javax.xml.namespace.QName AUTH$6 = 
            new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com", "auth");
        
        
        /**
         * Gets the "name" element
         */
        public java.lang.String getName()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NAME$0, 0);
                if (target == null)
                {
                    return null;
                }
                return target.getStringValue();
            }
        }
        
        /**
         * Gets (as xml) the "name" element
         */
        public org.apache.xmlbeans.XmlString xgetName()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(NAME$0, 0);
                return target;
            }
        }
        
        /**
         * Tests for nil "name" element
         */
        public boolean isNilName()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(NAME$0, 0);
                if (target == null) return false;
                return target.isNil();
            }
        }
        
        /**
         * True if has "name" element
         */
        public boolean isSetName()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(NAME$0) != 0;
            }
        }
        
        /**
         * Sets the "name" element
         */
        public void setName(java.lang.String name)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NAME$0, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(NAME$0);
                }
                target.setStringValue(name);
            }
        }
        
        /**
         * Sets (as xml) the "name" element
         */
        public void xsetName(org.apache.xmlbeans.XmlString name)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(NAME$0, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(NAME$0);
                }
                target.set(name);
            }
        }
        
        /**
         * Nils the "name" element
         */
        public void setNilName()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(NAME$0, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(NAME$0);
                }
                target.setNil();
            }
        }
        
        /**
         * Unsets the "name" element
         */
        public void unsetName()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(NAME$0, 0);
            }
        }
        
        /**
         * Gets the "incidentId" element
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
         * Gets (as xml) the "incidentId" element
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
         * Tests for nil "incidentId" element
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
         * True if has "incidentId" element
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
         * Sets the "incidentId" element
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
         * Sets (as xml) the "incidentId" element
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
         * Nils the "incidentId" element
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
         * Unsets the "incidentId" element
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
         * Gets the "claim" element
         */
        public com.bagnet.nettracer.ws.onlineclaims.xsd.Claim getClaim()
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.bagnet.nettracer.ws.onlineclaims.xsd.Claim target = null;
                target = (com.bagnet.nettracer.ws.onlineclaims.xsd.Claim)get_store().find_element_user(CLAIM$4, 0);
                if (target == null)
                {
                    return null;
                }
                return target;
            }
        }
        
        /**
         * Tests for nil "claim" element
         */
        public boolean isNilClaim()
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.bagnet.nettracer.ws.onlineclaims.xsd.Claim target = null;
                target = (com.bagnet.nettracer.ws.onlineclaims.xsd.Claim)get_store().find_element_user(CLAIM$4, 0);
                if (target == null) return false;
                return target.isNil();
            }
        }
        
        /**
         * True if has "claim" element
         */
        public boolean isSetClaim()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(CLAIM$4) != 0;
            }
        }
        
        /**
         * Sets the "claim" element
         */
        public void setClaim(com.bagnet.nettracer.ws.onlineclaims.xsd.Claim claim)
        {
            generatedSetterHelperImpl(claim, CLAIM$4, 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
        }
        
        /**
         * Appends and returns a new empty "claim" element
         */
        public com.bagnet.nettracer.ws.onlineclaims.xsd.Claim addNewClaim()
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.bagnet.nettracer.ws.onlineclaims.xsd.Claim target = null;
                target = (com.bagnet.nettracer.ws.onlineclaims.xsd.Claim)get_store().add_element_user(CLAIM$4);
                return target;
            }
        }
        
        /**
         * Nils the "claim" element
         */
        public void setNilClaim()
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.bagnet.nettracer.ws.onlineclaims.xsd.Claim target = null;
                target = (com.bagnet.nettracer.ws.onlineclaims.xsd.Claim)get_store().find_element_user(CLAIM$4, 0);
                if (target == null)
                {
                    target = (com.bagnet.nettracer.ws.onlineclaims.xsd.Claim)get_store().add_element_user(CLAIM$4);
                }
                target.setNil();
            }
        }
        
        /**
         * Unsets the "claim" element
         */
        public void unsetClaim()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(CLAIM$4, 0);
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
