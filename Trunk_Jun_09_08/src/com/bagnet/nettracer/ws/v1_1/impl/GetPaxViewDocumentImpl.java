/*
 * An XML document type.
 * Localname: getPaxView
 * Namespace: http://v1_1.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.v1_1.GetPaxViewDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.v1_1.impl;
/**
 * A document containing one getPaxView(@http://v1_1.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public class GetPaxViewDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.v1_1.GetPaxViewDocument
{
    
    public GetPaxViewDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName GETPAXVIEW$0 = 
        new javax.xml.namespace.QName("http://v1_1.ws.nettracer.bagnet.com", "getPaxView");
    
    
    /**
     * Gets the "getPaxView" element
     */
    public com.bagnet.nettracer.ws.v1_1.GetPaxViewDocument.GetPaxView getGetPaxView()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.v1_1.GetPaxViewDocument.GetPaxView target = null;
            target = (com.bagnet.nettracer.ws.v1_1.GetPaxViewDocument.GetPaxView)get_store().find_element_user(GETPAXVIEW$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "getPaxView" element
     */
    public void setGetPaxView(com.bagnet.nettracer.ws.v1_1.GetPaxViewDocument.GetPaxView getPaxView)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.v1_1.GetPaxViewDocument.GetPaxView target = null;
            target = (com.bagnet.nettracer.ws.v1_1.GetPaxViewDocument.GetPaxView)get_store().find_element_user(GETPAXVIEW$0, 0);
            if (target == null)
            {
                target = (com.bagnet.nettracer.ws.v1_1.GetPaxViewDocument.GetPaxView)get_store().add_element_user(GETPAXVIEW$0);
            }
            target.set(getPaxView);
        }
    }
    
    /**
     * Appends and returns a new empty "getPaxView" element
     */
    public com.bagnet.nettracer.ws.v1_1.GetPaxViewDocument.GetPaxView addNewGetPaxView()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.v1_1.GetPaxViewDocument.GetPaxView target = null;
            target = (com.bagnet.nettracer.ws.v1_1.GetPaxViewDocument.GetPaxView)get_store().add_element_user(GETPAXVIEW$0);
            return target;
        }
    }
    /**
     * An XML getPaxView(@http://v1_1.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public static class GetPaxViewImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.v1_1.GetPaxViewDocument.GetPaxView
    {
        
        public GetPaxViewImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName INCIDENTID$0 = 
            new javax.xml.namespace.QName("http://v1_1.ws.nettracer.bagnet.com", "incident_id");
        private static final javax.xml.namespace.QName LASTNAME$2 = 
            new javax.xml.namespace.QName("http://v1_1.ws.nettracer.bagnet.com", "lastname");
        private static final javax.xml.namespace.QName USERNAME$4 = 
            new javax.xml.namespace.QName("http://v1_1.ws.nettracer.bagnet.com", "username");
        private static final javax.xml.namespace.QName PASSWORD$6 = 
            new javax.xml.namespace.QName("http://v1_1.ws.nettracer.bagnet.com", "password");
        private static final javax.xml.namespace.QName MSGSREADBYUSER$8 = 
            new javax.xml.namespace.QName("http://v1_1.ws.nettracer.bagnet.com", "msgsReadByUser");
        
        
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
        
        /**
         * Gets the "username" element
         */
        public java.lang.String getUsername()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(USERNAME$4, 0);
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
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(USERNAME$4, 0);
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
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(USERNAME$4, 0);
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
                return get_store().count_elements(USERNAME$4) != 0;
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
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(USERNAME$4, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(USERNAME$4);
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
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(USERNAME$4, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(USERNAME$4);
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
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(USERNAME$4, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(USERNAME$4);
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
                get_store().remove_element(USERNAME$4, 0);
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
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PASSWORD$6, 0);
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
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PASSWORD$6, 0);
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
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PASSWORD$6, 0);
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
                return get_store().count_elements(PASSWORD$6) != 0;
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
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PASSWORD$6, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PASSWORD$6);
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
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PASSWORD$6, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PASSWORD$6);
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
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PASSWORD$6, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PASSWORD$6);
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
                get_store().remove_element(PASSWORD$6, 0);
            }
        }
        
        /**
         * Gets the "msgsReadByUser" element
         */
        public boolean getMsgsReadByUser()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MSGSREADBYUSER$8, 0);
                if (target == null)
                {
                    return false;
                }
                return target.getBooleanValue();
            }
        }
        
        /**
         * Gets (as xml) the "msgsReadByUser" element
         */
        public org.apache.xmlbeans.XmlBoolean xgetMsgsReadByUser()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlBoolean target = null;
                target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(MSGSREADBYUSER$8, 0);
                return target;
            }
        }
        
        /**
         * True if has "msgsReadByUser" element
         */
        public boolean isSetMsgsReadByUser()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(MSGSREADBYUSER$8) != 0;
            }
        }
        
        /**
         * Sets the "msgsReadByUser" element
         */
        public void setMsgsReadByUser(boolean msgsReadByUser)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MSGSREADBYUSER$8, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(MSGSREADBYUSER$8);
                }
                target.setBooleanValue(msgsReadByUser);
            }
        }
        
        /**
         * Sets (as xml) the "msgsReadByUser" element
         */
        public void xsetMsgsReadByUser(org.apache.xmlbeans.XmlBoolean msgsReadByUser)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlBoolean target = null;
                target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(MSGSREADBYUSER$8, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlBoolean)get_store().add_element_user(MSGSREADBYUSER$8);
                }
                target.set(msgsReadByUser);
            }
        }
        
        /**
         * Unsets the "msgsReadByUser" element
         */
        public void unsetMsgsReadByUser()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(MSGSREADBYUSER$8, 0);
            }
        }
    }
}
