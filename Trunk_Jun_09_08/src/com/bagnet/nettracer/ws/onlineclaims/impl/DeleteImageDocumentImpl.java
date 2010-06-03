/*
 * An XML document type.
 * Localname: deleteImage
 * Namespace: http://onlineclaims.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.onlineclaims.DeleteImageDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.onlineclaims.impl;
/**
 * A document containing one deleteImage(@http://onlineclaims.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public class DeleteImageDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.onlineclaims.DeleteImageDocument
{
    
    public DeleteImageDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName DELETEIMAGE$0 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com", "deleteImage");
    
    
    /**
     * Gets the "deleteImage" element
     */
    public com.bagnet.nettracer.ws.onlineclaims.DeleteImageDocument.DeleteImage getDeleteImage()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.DeleteImageDocument.DeleteImage target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.DeleteImageDocument.DeleteImage)get_store().find_element_user(DELETEIMAGE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "deleteImage" element
     */
    public void setDeleteImage(com.bagnet.nettracer.ws.onlineclaims.DeleteImageDocument.DeleteImage deleteImage)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.DeleteImageDocument.DeleteImage target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.DeleteImageDocument.DeleteImage)get_store().find_element_user(DELETEIMAGE$0, 0);
            if (target == null)
            {
                target = (com.bagnet.nettracer.ws.onlineclaims.DeleteImageDocument.DeleteImage)get_store().add_element_user(DELETEIMAGE$0);
            }
            target.set(deleteImage);
        }
    }
    
    /**
     * Appends and returns a new empty "deleteImage" element
     */
    public com.bagnet.nettracer.ws.onlineclaims.DeleteImageDocument.DeleteImage addNewDeleteImage()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.DeleteImageDocument.DeleteImage target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.DeleteImageDocument.DeleteImage)get_store().add_element_user(DELETEIMAGE$0);
            return target;
        }
    }
    /**
     * An XML deleteImage(@http://onlineclaims.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public static class DeleteImageImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.onlineclaims.DeleteImageDocument.DeleteImage
    {
        
        public DeleteImageImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName CLAIMID$0 = 
            new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com", "claimId");
        private static final javax.xml.namespace.QName IMAGEID$2 = 
            new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com", "imageId");
        
        
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
         * Gets the "imageId" element
         */
        public long getImageId()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMAGEID$2, 0);
                if (target == null)
                {
                    return 0L;
                }
                return target.getLongValue();
            }
        }
        
        /**
         * Gets (as xml) the "imageId" element
         */
        public org.apache.xmlbeans.XmlLong xgetImageId()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlLong target = null;
                target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(IMAGEID$2, 0);
                return target;
            }
        }
        
        /**
         * True if has "imageId" element
         */
        public boolean isSetImageId()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(IMAGEID$2) != 0;
            }
        }
        
        /**
         * Sets the "imageId" element
         */
        public void setImageId(long imageId)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMAGEID$2, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IMAGEID$2);
                }
                target.setLongValue(imageId);
            }
        }
        
        /**
         * Sets (as xml) the "imageId" element
         */
        public void xsetImageId(org.apache.xmlbeans.XmlLong imageId)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlLong target = null;
                target = (org.apache.xmlbeans.XmlLong)get_store().find_element_user(IMAGEID$2, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlLong)get_store().add_element_user(IMAGEID$2);
                }
                target.set(imageId);
            }
        }
        
        /**
         * Unsets the "imageId" element
         */
        public void unsetImageId()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(IMAGEID$2, 0);
            }
        }
    }
}
