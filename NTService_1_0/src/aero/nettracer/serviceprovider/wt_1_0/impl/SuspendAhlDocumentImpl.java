/*
 * An XML document type.
 * Localname: suspendAhl
 * Namespace: http://wt_1_0.serviceprovider.nettracer.aero
 * Java type: aero.nettracer.serviceprovider.wt_1_0.SuspendAhlDocument
 *
 * Automatically generated - do not modify.
 */
package aero.nettracer.serviceprovider.wt_1_0.impl;
/**
 * A document containing one suspendAhl(@http://wt_1_0.serviceprovider.nettracer.aero) element.
 *
 * This is a complex type.
 */
public class SuspendAhlDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aero.nettracer.serviceprovider.wt_1_0.SuspendAhlDocument
{
    
    public SuspendAhlDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName SUSPENDAHL$0 = 
        new javax.xml.namespace.QName("http://wt_1_0.serviceprovider.nettracer.aero", "suspendAhl");
    
    
    /**
     * Gets the "suspendAhl" element
     */
    public aero.nettracer.serviceprovider.wt_1_0.SuspendAhlDocument.SuspendAhl getSuspendAhl()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.wt_1_0.SuspendAhlDocument.SuspendAhl target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.SuspendAhlDocument.SuspendAhl)get_store().find_element_user(SUSPENDAHL$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "suspendAhl" element
     */
    public void setSuspendAhl(aero.nettracer.serviceprovider.wt_1_0.SuspendAhlDocument.SuspendAhl suspendAhl)
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.wt_1_0.SuspendAhlDocument.SuspendAhl target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.SuspendAhlDocument.SuspendAhl)get_store().find_element_user(SUSPENDAHL$0, 0);
            if (target == null)
            {
                target = (aero.nettracer.serviceprovider.wt_1_0.SuspendAhlDocument.SuspendAhl)get_store().add_element_user(SUSPENDAHL$0);
            }
            target.set(suspendAhl);
        }
    }
    
    /**
     * Appends and returns a new empty "suspendAhl" element
     */
    public aero.nettracer.serviceprovider.wt_1_0.SuspendAhlDocument.SuspendAhl addNewSuspendAhl()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.wt_1_0.SuspendAhlDocument.SuspendAhl target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.SuspendAhlDocument.SuspendAhl)get_store().add_element_user(SUSPENDAHL$0);
            return target;
        }
    }
    /**
     * An XML suspendAhl(@http://wt_1_0.serviceprovider.nettracer.aero).
     *
     * This is a complex type.
     */
    public static class SuspendAhlImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aero.nettracer.serviceprovider.wt_1_0.SuspendAhlDocument.SuspendAhl
    {
        
        public SuspendAhlImpl(org.apache.xmlbeans.SchemaType sType)
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
