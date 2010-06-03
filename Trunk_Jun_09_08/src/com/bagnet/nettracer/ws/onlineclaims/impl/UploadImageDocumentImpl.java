/*
 * An XML document type.
 * Localname: uploadImage
 * Namespace: http://onlineclaims.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.onlineclaims.UploadImageDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.onlineclaims.impl;
/**
 * A document containing one uploadImage(@http://onlineclaims.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public class UploadImageDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.onlineclaims.UploadImageDocument
{
    
    public UploadImageDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName UPLOADIMAGE$0 = 
        new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com", "uploadImage");
    
    
    /**
     * Gets the "uploadImage" element
     */
    public com.bagnet.nettracer.ws.onlineclaims.UploadImageDocument.UploadImage getUploadImage()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.UploadImageDocument.UploadImage target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.UploadImageDocument.UploadImage)get_store().find_element_user(UPLOADIMAGE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "uploadImage" element
     */
    public void setUploadImage(com.bagnet.nettracer.ws.onlineclaims.UploadImageDocument.UploadImage uploadImage)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.UploadImageDocument.UploadImage target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.UploadImageDocument.UploadImage)get_store().find_element_user(UPLOADIMAGE$0, 0);
            if (target == null)
            {
                target = (com.bagnet.nettracer.ws.onlineclaims.UploadImageDocument.UploadImage)get_store().add_element_user(UPLOADIMAGE$0);
            }
            target.set(uploadImage);
        }
    }
    
    /**
     * Appends and returns a new empty "uploadImage" element
     */
    public com.bagnet.nettracer.ws.onlineclaims.UploadImageDocument.UploadImage addNewUploadImage()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.onlineclaims.UploadImageDocument.UploadImage target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.UploadImageDocument.UploadImage)get_store().add_element_user(UPLOADIMAGE$0);
            return target;
        }
    }
    /**
     * An XML uploadImage(@http://onlineclaims.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public static class UploadImageImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.onlineclaims.UploadImageDocument.UploadImage
    {
        
        public UploadImageImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName IMAGE$0 = 
            new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com", "image");
        
        
        /**
         * Gets the "image" element
         */
        public byte[] getImage()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMAGE$0, 0);
                if (target == null)
                {
                    return null;
                }
                return target.getByteArrayValue();
            }
        }
        
        /**
         * Gets (as xml) the "image" element
         */
        public org.apache.xmlbeans.XmlBase64Binary xgetImage()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlBase64Binary target = null;
                target = (org.apache.xmlbeans.XmlBase64Binary)get_store().find_element_user(IMAGE$0, 0);
                return target;
            }
        }
        
        /**
         * Tests for nil "image" element
         */
        public boolean isNilImage()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlBase64Binary target = null;
                target = (org.apache.xmlbeans.XmlBase64Binary)get_store().find_element_user(IMAGE$0, 0);
                if (target == null) return false;
                return target.isNil();
            }
        }
        
        /**
         * True if has "image" element
         */
        public boolean isSetImage()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(IMAGE$0) != 0;
            }
        }
        
        /**
         * Sets the "image" element
         */
        public void setImage(byte[] image)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(IMAGE$0, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(IMAGE$0);
                }
                target.setByteArrayValue(image);
            }
        }
        
        /**
         * Sets (as xml) the "image" element
         */
        public void xsetImage(org.apache.xmlbeans.XmlBase64Binary image)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlBase64Binary target = null;
                target = (org.apache.xmlbeans.XmlBase64Binary)get_store().find_element_user(IMAGE$0, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlBase64Binary)get_store().add_element_user(IMAGE$0);
                }
                target.set(image);
            }
        }
        
        /**
         * Nils the "image" element
         */
        public void setNilImage()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlBase64Binary target = null;
                target = (org.apache.xmlbeans.XmlBase64Binary)get_store().find_element_user(IMAGE$0, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlBase64Binary)get_store().add_element_user(IMAGE$0);
                }
                target.setNil();
            }
        }
        
        /**
         * Unsets the "image" element
         */
        public void unsetImage()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(IMAGE$0, 0);
            }
        }
    }
}
