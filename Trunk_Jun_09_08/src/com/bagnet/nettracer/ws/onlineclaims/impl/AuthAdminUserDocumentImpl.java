/*
 * An XML document type.
 * Localname: authAdminUser
 * Namespace: http://onlineclaims.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.onlineclaims.impl;
/**
 * A document containing one authAdminUser(@http://onlineclaims.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public class AuthAdminUserDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserDocument
{
    private static final long serialVersionUID = 1L;
    
    public AuthAdminUserDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName AUTHADMINUSER$0 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com", "authAdminUser");
    
    
    /**
     * Gets the "authAdminUser" element
     */
    public com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserDocument.AuthAdminUser getAuthAdminUser()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserDocument.AuthAdminUser target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserDocument.AuthAdminUser)get_store().find_element_user(AUTHADMINUSER$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "authAdminUser" element
     */
    public void setAuthAdminUser(com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserDocument.AuthAdminUser authAdminUser)
    {
        generatedSetterHelperImpl(authAdminUser, AUTHADMINUSER$0, 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
    }
    
    /**
     * Appends and returns a new empty "authAdminUser" element
     */
    public com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserDocument.AuthAdminUser addNewAuthAdminUser()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserDocument.AuthAdminUser target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserDocument.AuthAdminUser)get_store().add_element_user(AUTHADMINUSER$0);
            return target;
        }
    }
    /**
     * An XML authAdminUser(@http://onlineclaims.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public static class AuthAdminUserImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserDocument.AuthAdminUser
    {
        private static final long serialVersionUID = 1L;
        
        public AuthAdminUserImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName USERNAME$0 = 
            new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com", "username");
        private static final javax.xml.namespace.QName PASSWORD$2 = 
            new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com", "password");
        private static final javax.xml.namespace.QName AUTH$4 = 
            new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com", "auth");
        
        
        /**
         * Gets the "username" element
         */
        public java.lang.String getUsername()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(USERNAME$0, 0);
                if (target == null)
                {
                    return null;
                }
                return target.getStringValue();
            }
        }
        
        /**
         * Gets (as xml) the "username" element
         */
        public org.apache.xmlbeans.XmlString xgetUsername()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(USERNAME$0, 0);
                return target;
            }
        }
        
        /**
         * Tests for nil "username" element
         */
        public boolean isNilUsername()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(USERNAME$0, 0);
                if (target == null) return false;
                return target.isNil();
            }
        }
        
        /**
         * True if has "username" element
         */
        public boolean isSetUsername()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(USERNAME$0) != 0;
            }
        }
        
        /**
         * Sets the "username" element
         */
        public void setUsername(java.lang.String username)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(USERNAME$0, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(USERNAME$0);
                }
                target.setStringValue(username);
            }
        }
        
        /**
         * Sets (as xml) the "username" element
         */
        public void xsetUsername(org.apache.xmlbeans.XmlString username)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(USERNAME$0, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(USERNAME$0);
                }
                target.set(username);
            }
        }
        
        /**
         * Nils the "username" element
         */
        public void setNilUsername()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(USERNAME$0, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(USERNAME$0);
                }
                target.setNil();
            }
        }
        
        /**
         * Unsets the "username" element
         */
        public void unsetUsername()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(USERNAME$0, 0);
            }
        }
        
        /**
         * Gets the "password" element
         */
        public java.lang.String getPassword()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PASSWORD$2, 0);
                if (target == null)
                {
                    return null;
                }
                return target.getStringValue();
            }
        }
        
        /**
         * Gets (as xml) the "password" element
         */
        public org.apache.xmlbeans.XmlString xgetPassword()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PASSWORD$2, 0);
                return target;
            }
        }
        
        /**
         * Tests for nil "password" element
         */
        public boolean isNilPassword()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PASSWORD$2, 0);
                if (target == null) return false;
                return target.isNil();
            }
        }
        
        /**
         * True if has "password" element
         */
        public boolean isSetPassword()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(PASSWORD$2) != 0;
            }
        }
        
        /**
         * Sets the "password" element
         */
        public void setPassword(java.lang.String password)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PASSWORD$2, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PASSWORD$2);
                }
                target.setStringValue(password);
            }
        }
        
        /**
         * Sets (as xml) the "password" element
         */
        public void xsetPassword(org.apache.xmlbeans.XmlString password)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PASSWORD$2, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PASSWORD$2);
                }
                target.set(password);
            }
        }
        
        /**
         * Nils the "password" element
         */
        public void setNilPassword()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PASSWORD$2, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PASSWORD$2);
                }
                target.setNil();
            }
        }
        
        /**
         * Unsets the "password" element
         */
        public void unsetPassword()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(PASSWORD$2, 0);
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
                target = (com.bagnet.nettracer.ws.onlineclaims.xsd.NtAuth)get_store().find_element_user(AUTH$4, 0);
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
                target = (com.bagnet.nettracer.ws.onlineclaims.xsd.NtAuth)get_store().find_element_user(AUTH$4, 0);
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
                return get_store().count_elements(AUTH$4) != 0;
            }
        }
        
        /**
         * Sets the "auth" element
         */
        public void setAuth(com.bagnet.nettracer.ws.onlineclaims.xsd.NtAuth auth)
        {
            generatedSetterHelperImpl(auth, AUTH$4, 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
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
                target = (com.bagnet.nettracer.ws.onlineclaims.xsd.NtAuth)get_store().add_element_user(AUTH$4);
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
                target = (com.bagnet.nettracer.ws.onlineclaims.xsd.NtAuth)get_store().find_element_user(AUTH$4, 0);
                if (target == null)
                {
                    target = (com.bagnet.nettracer.ws.onlineclaims.xsd.NtAuth)get_store().add_element_user(AUTH$4);
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
                get_store().remove_element(AUTH$4, 0);
            }
        }
    }
}
