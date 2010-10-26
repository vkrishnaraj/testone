/*
 * An XML document type.
 * Localname: insertOHD
 * Namespace: http://core.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.core.InsertOHDDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.core.impl;
/**
 * A document containing one insertOHD(@http://core.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public class InsertOHDDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.core.InsertOHDDocument
{
    
    public InsertOHDDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName INSERTOHD$0 = 
        new javax.xml.namespace.QName("http://core.ws.nettracer.bagnet.com", "insertOHD");
    
    
    /**
     * Gets the "insertOHD" element
     */
    public com.bagnet.nettracer.ws.core.InsertOHDDocument.InsertOHD getInsertOHD()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.InsertOHDDocument.InsertOHD target = null;
            target = (com.bagnet.nettracer.ws.core.InsertOHDDocument.InsertOHD)get_store().find_element_user(INSERTOHD$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "insertOHD" element
     */
    public void setInsertOHD(com.bagnet.nettracer.ws.core.InsertOHDDocument.InsertOHD insertOHD)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.InsertOHDDocument.InsertOHD target = null;
            target = (com.bagnet.nettracer.ws.core.InsertOHDDocument.InsertOHD)get_store().find_element_user(INSERTOHD$0, 0);
            if (target == null)
            {
                target = (com.bagnet.nettracer.ws.core.InsertOHDDocument.InsertOHD)get_store().add_element_user(INSERTOHD$0);
            }
            target.set(insertOHD);
        }
    }
    
    /**
     * Appends and returns a new empty "insertOHD" element
     */
    public com.bagnet.nettracer.ws.core.InsertOHDDocument.InsertOHD addNewInsertOHD()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.core.InsertOHDDocument.InsertOHD target = null;
            target = (com.bagnet.nettracer.ws.core.InsertOHDDocument.InsertOHD)get_store().add_element_user(INSERTOHD$0);
            return target;
        }
    }
    /**
     * An XML insertOHD(@http://core.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public static class InsertOHDImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.core.InsertOHDDocument.InsertOHD
    {
        
        public InsertOHDImpl(org.apache.xmlbeans.SchemaType sType)
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
        public com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD getSi()
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD)get_store().find_element_user(SI$2, 0);
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
                com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD)get_store().find_element_user(SI$2, 0);
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
        public void setSi(com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD si)
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD)get_store().find_element_user(SI$2, 0);
                if (target == null)
                {
                    target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD)get_store().add_element_user(SI$2);
                }
                target.set(si);
            }
        }
        
        /**
         * Appends and returns a new empty "si" element
         */
        public com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD addNewSi()
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD)get_store().add_element_user(SI$2);
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
                com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD)get_store().find_element_user(SI$2, 0);
                if (target == null)
                {
                    target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD)get_store().add_element_user(SI$2);
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
