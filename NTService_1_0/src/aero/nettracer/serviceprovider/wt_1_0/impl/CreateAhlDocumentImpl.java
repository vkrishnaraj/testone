/*
 * An XML document type.
 * Localname: createAhl
 * Namespace: http://wt_1_0.serviceprovider.nettracer.aero
 * Java type: aero.nettracer.serviceprovider.wt_1_0.CreateAhlDocument
 *
 * Automatically generated - do not modify.
 */
package aero.nettracer.serviceprovider.wt_1_0.impl;
/**
 * A document containing one createAhl(@http://wt_1_0.serviceprovider.nettracer.aero) element.
 *
 * This is a complex type.
 */
public class CreateAhlDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aero.nettracer.serviceprovider.wt_1_0.CreateAhlDocument
{
    
    public CreateAhlDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName CREATEAHL$0 = 
        new javax.xml.namespace.QName("http://wt_1_0.serviceprovider.nettracer.aero", "createAhl");
    
    
    /**
     * Gets the "createAhl" element
     */
    public aero.nettracer.serviceprovider.wt_1_0.CreateAhlDocument.CreateAhl getCreateAhl()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.wt_1_0.CreateAhlDocument.CreateAhl target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.CreateAhlDocument.CreateAhl)get_store().find_element_user(CREATEAHL$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "createAhl" element
     */
    public void setCreateAhl(aero.nettracer.serviceprovider.wt_1_0.CreateAhlDocument.CreateAhl createAhl)
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.wt_1_0.CreateAhlDocument.CreateAhl target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.CreateAhlDocument.CreateAhl)get_store().find_element_user(CREATEAHL$0, 0);
            if (target == null)
            {
                target = (aero.nettracer.serviceprovider.wt_1_0.CreateAhlDocument.CreateAhl)get_store().add_element_user(CREATEAHL$0);
            }
            target.set(createAhl);
        }
    }
    
    /**
     * Appends and returns a new empty "createAhl" element
     */
    public aero.nettracer.serviceprovider.wt_1_0.CreateAhlDocument.CreateAhl addNewCreateAhl()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.wt_1_0.CreateAhlDocument.CreateAhl target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.CreateAhlDocument.CreateAhl)get_store().add_element_user(CREATEAHL$0);
            return target;
        }
    }
    /**
     * An XML createAhl(@http://wt_1_0.serviceprovider.nettracer.aero).
     *
     * This is a complex type.
     */
    public static class CreateAhlImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aero.nettracer.serviceprovider.wt_1_0.CreateAhlDocument.CreateAhl
    {
        
        public CreateAhlImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName HEADER$0 = 
            new javax.xml.namespace.QName("http://wt_1_0.serviceprovider.nettracer.aero", "header");
        private static final javax.xml.namespace.QName AHL$2 = 
            new javax.xml.namespace.QName("http://wt_1_0.serviceprovider.nettracer.aero", "ahl");
        
        
        /**
         * Gets the "header" element
         */
        public aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader getHeader()
        {
            synchronized (monitor())
            {
                check_orphaned();
                aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader target = null;
                target = (aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader)get_store().find_element_user(HEADER$0, 0);
                if (target == null)
                {
                    return null;
                }
                return target;
            }
        }
        
        /**
         * Tests for nil "header" element
         */
        public boolean isNilHeader()
        {
            synchronized (monitor())
            {
                check_orphaned();
                aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader target = null;
                target = (aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader)get_store().find_element_user(HEADER$0, 0);
                if (target == null) return false;
                return target.isNil();
            }
        }
        
        /**
         * True if has "header" element
         */
        public boolean isSetHeader()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(HEADER$0) != 0;
            }
        }
        
        /**
         * Sets the "header" element
         */
        public void setHeader(aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader header)
        {
            synchronized (monitor())
            {
                check_orphaned();
                aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader target = null;
                target = (aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader)get_store().find_element_user(HEADER$0, 0);
                if (target == null)
                {
                    target = (aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader)get_store().add_element_user(HEADER$0);
                }
                target.set(header);
            }
        }
        
        /**
         * Appends and returns a new empty "header" element
         */
        public aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader addNewHeader()
        {
            synchronized (monitor())
            {
                check_orphaned();
                aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader target = null;
                target = (aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader)get_store().add_element_user(HEADER$0);
                return target;
            }
        }
        
        /**
         * Nils the "header" element
         */
        public void setNilHeader()
        {
            synchronized (monitor())
            {
                check_orphaned();
                aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader target = null;
                target = (aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader)get_store().find_element_user(HEADER$0, 0);
                if (target == null)
                {
                    target = (aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader)get_store().add_element_user(HEADER$0);
                }
                target.setNil();
            }
        }
        
        /**
         * Unsets the "header" element
         */
        public void unsetHeader()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(HEADER$0, 0);
            }
        }
        
        /**
         * Gets the "ahl" element
         */
        public aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl getAhl()
        {
            synchronized (monitor())
            {
                check_orphaned();
                aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl target = null;
                target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl)get_store().find_element_user(AHL$2, 0);
                if (target == null)
                {
                    return null;
                }
                return target;
            }
        }
        
        /**
         * Tests for nil "ahl" element
         */
        public boolean isNilAhl()
        {
            synchronized (monitor())
            {
                check_orphaned();
                aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl target = null;
                target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl)get_store().find_element_user(AHL$2, 0);
                if (target == null) return false;
                return target.isNil();
            }
        }
        
        /**
         * True if has "ahl" element
         */
        public boolean isSetAhl()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(AHL$2) != 0;
            }
        }
        
        /**
         * Sets the "ahl" element
         */
        public void setAhl(aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl ahl)
        {
            synchronized (monitor())
            {
                check_orphaned();
                aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl target = null;
                target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl)get_store().find_element_user(AHL$2, 0);
                if (target == null)
                {
                    target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl)get_store().add_element_user(AHL$2);
                }
                target.set(ahl);
            }
        }
        
        /**
         * Appends and returns a new empty "ahl" element
         */
        public aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl addNewAhl()
        {
            synchronized (monitor())
            {
                check_orphaned();
                aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl target = null;
                target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl)get_store().add_element_user(AHL$2);
                return target;
            }
        }
        
        /**
         * Nils the "ahl" element
         */
        public void setNilAhl()
        {
            synchronized (monitor())
            {
                check_orphaned();
                aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl target = null;
                target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl)get_store().find_element_user(AHL$2, 0);
                if (target == null)
                {
                    target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl)get_store().add_element_user(AHL$2);
                }
                target.setNil();
            }
        }
        
        /**
         * Unsets the "ahl" element
         */
        public void unsetAhl()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(AHL$2, 0);
            }
        }
    }
}
