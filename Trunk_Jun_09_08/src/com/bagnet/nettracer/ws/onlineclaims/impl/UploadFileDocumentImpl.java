/*
 * An XML document type.
 * Localname: uploadFile
 * Namespace: http://onlineclaims.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.onlineclaims.UploadFileDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.onlineclaims.impl;
/**
 * A document containing one uploadFile(@http://onlineclaims.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public class UploadFileDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.onlineclaims.UploadFileDocument
{
    
    public UploadFileDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName UPLOADFILE$0 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com", "uploadFile");
    
    
    /**
     * Gets the "uploadFile" element
     */
    public com.bagnet.nettracer.ws.onlineclaims.UploadFileDocument.UploadFile getUploadFile()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.UploadFileDocument.UploadFile target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.UploadFileDocument.UploadFile)get_store().find_element_user(UPLOADFILE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "uploadFile" element
     */
    public void setUploadFile(com.bagnet.nettracer.ws.onlineclaims.UploadFileDocument.UploadFile uploadFile)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.UploadFileDocument.UploadFile target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.UploadFileDocument.UploadFile)get_store().find_element_user(UPLOADFILE$0, 0);
            if (target == null)
            {
                target = (com.bagnet.nettracer.ws.onlineclaims.UploadFileDocument.UploadFile)get_store().add_element_user(UPLOADFILE$0);
            }
            target.set(uploadFile);
        }
    }
    
    /**
     * Appends and returns a new empty "uploadFile" element
     */
    public com.bagnet.nettracer.ws.onlineclaims.UploadFileDocument.UploadFile addNewUploadFile()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.UploadFileDocument.UploadFile target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.UploadFileDocument.UploadFile)get_store().add_element_user(UPLOADFILE$0);
            return target;
        }
    }
    /**
     * An XML uploadFile(@http://onlineclaims.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public static class UploadFileImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.onlineclaims.UploadFileDocument.UploadFile
    {
        
        public UploadFileImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName CLAIMID$0 = 
            new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com", "claimId");
        private static final javax.xml.namespace.QName FILENAME$2 = 
            new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com", "filename");
        private static final javax.xml.namespace.QName FILE$4 = 
            new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com", "file");
        private static final javax.xml.namespace.QName AUTH$6 = 
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
         * Gets the "filename" element
         */
        public java.lang.String getFilename()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FILENAME$2, 0);
                if (target == null)
                {
                    return null;
                }
                return target.getStringValue();
            }
        }
        
        /**
         * Gets (as xml) the "filename" element
         */
        public org.apache.xmlbeans.XmlString xgetFilename()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FILENAME$2, 0);
                return target;
            }
        }
        
        /**
         * Tests for nil "filename" element
         */
        public boolean isNilFilename()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FILENAME$2, 0);
                if (target == null) return false;
                return target.isNil();
            }
        }
        
        /**
         * True if has "filename" element
         */
        public boolean isSetFilename()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(FILENAME$2) != 0;
            }
        }
        
        /**
         * Sets the "filename" element
         */
        public void setFilename(java.lang.String filename)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FILENAME$2, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FILENAME$2);
                }
                target.setStringValue(filename);
            }
        }
        
        /**
         * Sets (as xml) the "filename" element
         */
        public void xsetFilename(org.apache.xmlbeans.XmlString filename)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FILENAME$2, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(FILENAME$2);
                }
                target.set(filename);
            }
        }
        
        /**
         * Nils the "filename" element
         */
        public void setNilFilename()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FILENAME$2, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(FILENAME$2);
                }
                target.setNil();
            }
        }
        
        /**
         * Unsets the "filename" element
         */
        public void unsetFilename()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(FILENAME$2, 0);
            }
        }
        
        /**
         * Gets the "file" element
         */
        public byte[] getFile()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FILE$4, 0);
                if (target == null)
                {
                    return null;
                }
                return target.getByteArrayValue();
            }
        }
        
        /**
         * Gets (as xml) the "file" element
         */
        public org.apache.xmlbeans.XmlBase64Binary xgetFile()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlBase64Binary target = null;
                target = (org.apache.xmlbeans.XmlBase64Binary)get_store().find_element_user(FILE$4, 0);
                return target;
            }
        }
        
        /**
         * Tests for nil "file" element
         */
        public boolean isNilFile()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlBase64Binary target = null;
                target = (org.apache.xmlbeans.XmlBase64Binary)get_store().find_element_user(FILE$4, 0);
                if (target == null) return false;
                return target.isNil();
            }
        }
        
        /**
         * True if has "file" element
         */
        public boolean isSetFile()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(FILE$4) != 0;
            }
        }
        
        /**
         * Sets the "file" element
         */
        public void setFile(byte[] file)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FILE$4, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FILE$4);
                }
                target.setByteArrayValue(file);
            }
        }
        
        /**
         * Sets (as xml) the "file" element
         */
        public void xsetFile(org.apache.xmlbeans.XmlBase64Binary file)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlBase64Binary target = null;
                target = (org.apache.xmlbeans.XmlBase64Binary)get_store().find_element_user(FILE$4, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlBase64Binary)get_store().add_element_user(FILE$4);
                }
                target.set(file);
            }
        }
        
        /**
         * Nils the "file" element
         */
        public void setNilFile()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlBase64Binary target = null;
                target = (org.apache.xmlbeans.XmlBase64Binary)get_store().find_element_user(FILE$4, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlBase64Binary)get_store().add_element_user(FILE$4);
                }
                target.setNil();
            }
        }
        
        /**
         * Unsets the "file" element
         */
        public void unsetFile()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(FILE$4, 0);
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
            synchronized (monitor())
            {
                check_orphaned();
                com.bagnet.nettracer.ws.onlineclaims.xsd.NtAuth target = null;
                target = (com.bagnet.nettracer.ws.onlineclaims.xsd.NtAuth)get_store().find_element_user(AUTH$6, 0);
                if (target == null)
                {
                    target = (com.bagnet.nettracer.ws.onlineclaims.xsd.NtAuth)get_store().add_element_user(AUTH$6);
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
