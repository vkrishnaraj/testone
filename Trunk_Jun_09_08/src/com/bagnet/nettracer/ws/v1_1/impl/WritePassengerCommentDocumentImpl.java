/*
 * An XML document type.
 * Localname: writePassengerComment
 * Namespace: http://v1_1.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.v1_1.WritePassengerCommentDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.v1_1.impl;
/**
 * A document containing one writePassengerComment(@http://v1_1.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public class WritePassengerCommentDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.v1_1.WritePassengerCommentDocument
{
    
    public WritePassengerCommentDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName WRITEPASSENGERCOMMENT$0 = 
        new javax.xml.namespace.QName("http://v1_1.ws.nettracer.bagnet.com", "writePassengerComment");
    
    
    /**
     * Gets the "writePassengerComment" element
     */
    public com.bagnet.nettracer.ws.v1_1.WritePassengerCommentDocument.WritePassengerComment getWritePassengerComment()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.v1_1.WritePassengerCommentDocument.WritePassengerComment target = null;
            target = (com.bagnet.nettracer.ws.v1_1.WritePassengerCommentDocument.WritePassengerComment)get_store().find_element_user(WRITEPASSENGERCOMMENT$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "writePassengerComment" element
     */
    public void setWritePassengerComment(com.bagnet.nettracer.ws.v1_1.WritePassengerCommentDocument.WritePassengerComment writePassengerComment)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.v1_1.WritePassengerCommentDocument.WritePassengerComment target = null;
            target = (com.bagnet.nettracer.ws.v1_1.WritePassengerCommentDocument.WritePassengerComment)get_store().find_element_user(WRITEPASSENGERCOMMENT$0, 0);
            if (target == null)
            {
                target = (com.bagnet.nettracer.ws.v1_1.WritePassengerCommentDocument.WritePassengerComment)get_store().add_element_user(WRITEPASSENGERCOMMENT$0);
            }
            target.set(writePassengerComment);
        }
    }
    
    /**
     * Appends and returns a new empty "writePassengerComment" element
     */
    public com.bagnet.nettracer.ws.v1_1.WritePassengerCommentDocument.WritePassengerComment addNewWritePassengerComment()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.v1_1.WritePassengerCommentDocument.WritePassengerComment target = null;
            target = (com.bagnet.nettracer.ws.v1_1.WritePassengerCommentDocument.WritePassengerComment)get_store().add_element_user(WRITEPASSENGERCOMMENT$0);
            return target;
        }
    }
    /**
     * An XML writePassengerComment(@http://v1_1.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public static class WritePassengerCommentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.v1_1.WritePassengerCommentDocument.WritePassengerComment
    {
        
        public WritePassengerCommentImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName INCIDENTID$0 = 
            new javax.xml.namespace.QName("http://v1_1.ws.nettracer.bagnet.com", "incident_id");
        private static final javax.xml.namespace.QName COMMENT$2 = 
            new javax.xml.namespace.QName("http://v1_1.ws.nettracer.bagnet.com", "comment");
        private static final javax.xml.namespace.QName USERNAME$4 = 
            new javax.xml.namespace.QName("http://v1_1.ws.nettracer.bagnet.com", "username");
        private static final javax.xml.namespace.QName PASSWORD$6 = 
            new javax.xml.namespace.QName("http://v1_1.ws.nettracer.bagnet.com", "password");
        
        
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
         * Gets the "comment" element
         */
        public java.lang.String getComment()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COMMENT$2, 0);
                if (target == null)
                {
                    return null;
                }
                return target.getStringValue();
            }
        }
        
        /**
         * Gets (as xml) the "comment" element
         */
        public org.apache.xmlbeans.XmlString xgetComment()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COMMENT$2, 0);
                return target;
            }
        }
        
        /**
         * Tests for nil "comment" element
         */
        public boolean isNilComment()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COMMENT$2, 0);
                if (target == null) return false;
                return target.isNil();
            }
        }
        
        /**
         * True if has "comment" element
         */
        public boolean isSetComment()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(COMMENT$2) != 0;
            }
        }
        
        /**
         * Sets the "comment" element
         */
        public void setComment(java.lang.String comment)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COMMENT$2, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(COMMENT$2);
                }
                target.setStringValue(comment);
            }
        }
        
        /**
         * Sets (as xml) the "comment" element
         */
        public void xsetComment(org.apache.xmlbeans.XmlString comment)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COMMENT$2, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(COMMENT$2);
                }
                target.set(comment);
            }
        }
        
        /**
         * Nils the "comment" element
         */
        public void setNilComment()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COMMENT$2, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(COMMENT$2);
                }
                target.setNil();
            }
        }
        
        /**
         * Unsets the "comment" element
         */
        public void unsetComment()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(COMMENT$2, 0);
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
    }
}
