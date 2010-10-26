/*
 * An XML document type.
 * Localname: UpdateBdoDelivery
 * Namespace: http://core.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.core.UpdateBdoDeliveryDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.core.impl;
/**
 * A document containing one UpdateBdoDelivery(@http://core.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public class UpdateBdoDeliveryDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.core.UpdateBdoDeliveryDocument
{
    
    public UpdateBdoDeliveryDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName UPDATEBDODELIVERY$0 = 
        new javax.xml.namespace.QName("http://core.ws.nettracer.bagnet.com", "UpdateBdoDelivery");
    
    
    /**
     * Gets the "UpdateBdoDelivery" element
     */
    public com.bagnet.nettracer.ws.core.UpdateBdoDeliveryDocument.UpdateBdoDelivery getUpdateBdoDelivery()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.UpdateBdoDeliveryDocument.UpdateBdoDelivery target = null;
            target = (com.bagnet.nettracer.ws.core.UpdateBdoDeliveryDocument.UpdateBdoDelivery)get_store().find_element_user(UPDATEBDODELIVERY$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "UpdateBdoDelivery" element
     */
    public void setUpdateBdoDelivery(com.bagnet.nettracer.ws.core.UpdateBdoDeliveryDocument.UpdateBdoDelivery updateBdoDelivery)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.UpdateBdoDeliveryDocument.UpdateBdoDelivery target = null;
            target = (com.bagnet.nettracer.ws.core.UpdateBdoDeliveryDocument.UpdateBdoDelivery)get_store().find_element_user(UPDATEBDODELIVERY$0, 0);
            if (target == null)
            {
                target = (com.bagnet.nettracer.ws.core.UpdateBdoDeliveryDocument.UpdateBdoDelivery)get_store().add_element_user(UPDATEBDODELIVERY$0);
            }
            target.set(updateBdoDelivery);
        }
    }
    
    /**
     * Appends and returns a new empty "UpdateBdoDelivery" element
     */
    public com.bagnet.nettracer.ws.core.UpdateBdoDeliveryDocument.UpdateBdoDelivery addNewUpdateBdoDelivery()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.UpdateBdoDeliveryDocument.UpdateBdoDelivery target = null;
            target = (com.bagnet.nettracer.ws.core.UpdateBdoDeliveryDocument.UpdateBdoDelivery)get_store().add_element_user(UPDATEBDODELIVERY$0);
            return target;
        }
    }
    /**
     * An XML UpdateBdoDelivery(@http://core.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public static class UpdateBdoDeliveryImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.core.UpdateBdoDeliveryDocument.UpdateBdoDelivery
    {
        
        public UpdateBdoDeliveryImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName USERNAME$0 = 
            new javax.xml.namespace.QName("http://core.ws.nettracer.bagnet.com", "username");
        private static final javax.xml.namespace.QName PASSWORD$2 = 
            new javax.xml.namespace.QName("http://core.ws.nettracer.bagnet.com", "password");
        private static final javax.xml.namespace.QName COMPANYCODE$4 = 
            new javax.xml.namespace.QName("http://core.ws.nettracer.bagnet.com", "companycode");
        private static final javax.xml.namespace.QName UPDATES$6 = 
            new javax.xml.namespace.QName("http://core.ws.nettracer.bagnet.com", "updates");
        
        
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
         * Gets the "companycode" element
         */
        public java.lang.String getCompanycode()
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
         * Gets (as xml) the "companycode" element
         */
        public org.apache.xmlbeans.XmlString xgetCompanycode()
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
         * Tests for nil "companycode" element
         */
        public boolean isNilCompanycode()
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
         * True if has "companycode" element
         */
        public boolean isSetCompanycode()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(COMPANYCODE$4) != 0;
            }
        }
        
        /**
         * Sets the "companycode" element
         */
        public void setCompanycode(java.lang.String companycode)
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
                target.setStringValue(companycode);
            }
        }
        
        /**
         * Sets (as xml) the "companycode" element
         */
        public void xsetCompanycode(org.apache.xmlbeans.XmlString companycode)
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
                target.set(companycode);
            }
        }
        
        /**
         * Nils the "companycode" element
         */
        public void setNilCompanycode()
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
         * Unsets the "companycode" element
         */
        public void unsetCompanycode()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(COMPANYCODE$4, 0);
            }
        }
        
        /**
         * Gets the "updates" element
         */
        public com.bagnet.nettracer.ws.core.pojo.xsd.BdoStatusUpdate getUpdates()
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.bagnet.nettracer.ws.core.pojo.xsd.BdoStatusUpdate target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.BdoStatusUpdate)get_store().find_element_user(UPDATES$6, 0);
                if (target == null)
                {
                    return null;
                }
                return target;
            }
        }
        
        /**
         * Tests for nil "updates" element
         */
        public boolean isNilUpdates()
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.bagnet.nettracer.ws.core.pojo.xsd.BdoStatusUpdate target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.BdoStatusUpdate)get_store().find_element_user(UPDATES$6, 0);
                if (target == null) return false;
                return target.isNil();
            }
        }
        
        /**
         * True if has "updates" element
         */
        public boolean isSetUpdates()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(UPDATES$6) != 0;
            }
        }
        
        /**
         * Sets the "updates" element
         */
        public void setUpdates(com.bagnet.nettracer.ws.core.pojo.xsd.BdoStatusUpdate updates)
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.bagnet.nettracer.ws.core.pojo.xsd.BdoStatusUpdate target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.BdoStatusUpdate)get_store().find_element_user(UPDATES$6, 0);
                if (target == null)
                {
                    target = (com.bagnet.nettracer.ws.core.pojo.xsd.BdoStatusUpdate)get_store().add_element_user(UPDATES$6);
                }
                target.set(updates);
            }
        }
        
        /**
         * Appends and returns a new empty "updates" element
         */
        public com.bagnet.nettracer.ws.core.pojo.xsd.BdoStatusUpdate addNewUpdates()
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.bagnet.nettracer.ws.core.pojo.xsd.BdoStatusUpdate target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.BdoStatusUpdate)get_store().add_element_user(UPDATES$6);
                return target;
            }
        }
        
        /**
         * Nils the "updates" element
         */
        public void setNilUpdates()
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.bagnet.nettracer.ws.core.pojo.xsd.BdoStatusUpdate target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.BdoStatusUpdate)get_store().find_element_user(UPDATES$6, 0);
                if (target == null)
                {
                    target = (com.bagnet.nettracer.ws.core.pojo.xsd.BdoStatusUpdate)get_store().add_element_user(UPDATES$6);
                }
                target.setNil();
            }
        }
        
        /**
         * Unsets the "updates" element
         */
        public void unsetUpdates()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(UPDATES$6, 0);
            }
        }
    }
}
