/*
 * An XML document type.
 * Localname: deleteFile
 * Namespace: http://onlineclaims.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.onlineclaims.DeleteFileDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.onlineclaims.impl;
/**
 * A document containing one deleteFile(@http://onlineclaims.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public class DeleteFileDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.onlineclaims.DeleteFileDocument
{
    
    public DeleteFileDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName DELETEFILE$0 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com", "deleteFile");
    
    
    /**
     * Gets the "deleteFile" element
     */
    public com.bagnet.nettracer.ws.onlineclaims.DeleteFileDocument.DeleteFile getDeleteFile()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.DeleteFileDocument.DeleteFile target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.DeleteFileDocument.DeleteFile)get_store().find_element_user(DELETEFILE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "deleteFile" element
     */
    public void setDeleteFile(com.bagnet.nettracer.ws.onlineclaims.DeleteFileDocument.DeleteFile deleteFile)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.DeleteFileDocument.DeleteFile target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.DeleteFileDocument.DeleteFile)get_store().find_element_user(DELETEFILE$0, 0);
            if (target == null)
            {
                target = (com.bagnet.nettracer.ws.onlineclaims.DeleteFileDocument.DeleteFile)get_store().add_element_user(DELETEFILE$0);
            }
            target.set(deleteFile);
        }
    }
    
    /**
     * Appends and returns a new empty "deleteFile" element
     */
    public com.bagnet.nettracer.ws.onlineclaims.DeleteFileDocument.DeleteFile addNewDeleteFile()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.DeleteFileDocument.DeleteFile target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.DeleteFileDocument.DeleteFile)get_store().add_element_user(DELETEFILE$0);
            return target;
        }
    }
    /**
     * An XML deleteFile(@http://onlineclaims.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public static class DeleteFileImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.onlineclaims.DeleteFileDocument.DeleteFile
    {
        
        public DeleteFileImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName CLAIMID$0 = 
            new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com", "claimId");
        private static final javax.xml.namespace.QName FILEID$2 = 
            new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com", "fileId");
        private static final javax.xml.namespace.QName AUTH$4 = 
            new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com", "auth");
        
        
        /**
         * Gets the "claimId" element
         */
        public long getClaimId()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CLAIMID$0, 0);
                if (target == null)
                {
                    return 0L;
                }
                return target.getLongValue();
            }
        }
        
        /**
         * Gets (as xml) the "claimId" element
         */
        public org.apache.xmlbeans.XmlLong xgetClaimId()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlLong target = null;
                target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(CLAIMID$0, 0);
                return target;
            }
        }
        
        /**
         * True if has "claimId" element
         */
        public boolean isSetClaimId()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(CLAIMID$0) != 0;
            }
        }
        
        /**
         * Sets the "claimId" element
         */
        public void setClaimId(long claimId)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CLAIMID$0, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CLAIMID$0);
                }
                target.setLongValue(claimId);
            }
        }
        
        /**
         * Sets (as xml) the "claimId" element
         */
        public void xsetClaimId(org.apache.xmlbeans.XmlLong claimId)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlLong target = null;
                target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(CLAIMID$0, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlLong)get_store().add_element_user(CLAIMID$0);
                }
                target.set(claimId);
            }
        }
        
        /**
         * Unsets the "claimId" element
         */
        public void unsetClaimId()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(CLAIMID$0, 0);
            }
        }
        
        /**
         * Gets the "fileId" element
         */
        public long getFileId()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FILEID$2, 0);
                if (target == null)
                {
                    return 0L;
                }
                return target.getLongValue();
            }
        }
        
        /**
         * Gets (as xml) the "fileId" element
         */
        public org.apache.xmlbeans.XmlLong xgetFileId()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlLong target = null;
                target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(FILEID$2, 0);
                return target;
            }
        }
        
        /**
         * True if has "fileId" element
         */
        public boolean isSetFileId()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(FILEID$2) != 0;
            }
        }
        
        /**
         * Sets the "fileId" element
         */
        public void setFileId(long fileId)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FILEID$2, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FILEID$2);
                }
                target.setLongValue(fileId);
            }
        }
        
        /**
         * Sets (as xml) the "fileId" element
         */
        public void xsetFileId(org.apache.xmlbeans.XmlLong fileId)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlLong target = null;
                target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(FILEID$2, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlLong)get_store().add_element_user(FILEID$2);
                }
                target.set(fileId);
            }
        }
        
        /**
         * Unsets the "fileId" element
         */
        public void unsetFileId()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(FILEID$2, 0);
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
            synchronized (monitor())
            {
                check_orphaned();
                com.bagnet.nettracer.ws.onlineclaims.xsd.NtAuth target = null;
                target = (com.bagnet.nettracer.ws.onlineclaims.xsd.NtAuth)get_store().find_element_user(AUTH$4, 0);
                if (target == null)
                {
                    target = (com.bagnet.nettracer.ws.onlineclaims.xsd.NtAuth)get_store().add_element_user(AUTH$4);
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
