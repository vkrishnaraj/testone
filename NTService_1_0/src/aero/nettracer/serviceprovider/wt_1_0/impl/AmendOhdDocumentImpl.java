/*
 * An XML document type.
 * Localname: amendOhd
 * Namespace: http://wt_1_0.serviceprovider.nettracer.aero
 * Java type: aero.nettracer.serviceprovider.wt_1_0.AmendOhdDocument
 *
 * Automatically generated - do not modify.
 */
package aero.nettracer.serviceprovider.wt_1_0.impl;
/**
 * A document containing one amendOhd(@http://wt_1_0.serviceprovider.nettracer.aero) element.
 *
 * This is a complex type.
 */
public class AmendOhdDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aero.nettracer.serviceprovider.wt_1_0.AmendOhdDocument
{
    
    public AmendOhdDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName AMENDOHD$0 = 
        new javax.xml.namespace.QName("http://wt_1_0.serviceprovider.nettracer.aero", "amendOhd");
    
    
    /**
     * Gets the "amendOhd" element
     */
    public aero.nettracer.serviceprovider.wt_1_0.AmendOhdDocument.AmendOhd getAmendOhd()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.wt_1_0.AmendOhdDocument.AmendOhd target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.AmendOhdDocument.AmendOhd)get_store().find_element_user(AMENDOHD$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "amendOhd" element
     */
    public void setAmendOhd(aero.nettracer.serviceprovider.wt_1_0.AmendOhdDocument.AmendOhd amendOhd)
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.wt_1_0.AmendOhdDocument.AmendOhd target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.AmendOhdDocument.AmendOhd)get_store().find_element_user(AMENDOHD$0, 0);
            if (target == null)
            {
                target = (aero.nettracer.serviceprovider.wt_1_0.AmendOhdDocument.AmendOhd)get_store().add_element_user(AMENDOHD$0);
            }
            target.set(amendOhd);
        }
    }
    
    /**
     * Appends and returns a new empty "amendOhd" element
     */
    public aero.nettracer.serviceprovider.wt_1_0.AmendOhdDocument.AmendOhd addNewAmendOhd()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.wt_1_0.AmendOhdDocument.AmendOhd target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.AmendOhdDocument.AmendOhd)get_store().add_element_user(AMENDOHD$0);
            return target;
        }
    }
    /**
     * An XML amendOhd(@http://wt_1_0.serviceprovider.nettracer.aero).
     *
     * This is a complex type.
     */
    public static class AmendOhdImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aero.nettracer.serviceprovider.wt_1_0.AmendOhdDocument.AmendOhd
    {
        
        public AmendOhdImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName HEADER$0 = 
            new javax.xml.namespace.QName("http://wt_1_0.serviceprovider.nettracer.aero", "header");
        private static final javax.xml.namespace.QName OHD$2 = 
            new javax.xml.namespace.QName("http://wt_1_0.serviceprovider.nettracer.aero", "ohd");
        
        
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
         * Gets the "ohd" element
         */
        public aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd getOhd()
        {
            synchronized (monitor())
            {
                check_orphaned();
                aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd target = null;
                target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd)get_store().find_element_user(OHD$2, 0);
                if (target == null)
                {
                    return null;
                }
                return target;
            }
        }
        
        /**
         * Tests for nil "ohd" element
         */
        public boolean isNilOhd()
        {
            synchronized (monitor())
            {
                check_orphaned();
                aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd target = null;
                target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd)get_store().find_element_user(OHD$2, 0);
                if (target == null) return false;
                return target.isNil();
            }
        }
        
        /**
         * True if has "ohd" element
         */
        public boolean isSetOhd()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(OHD$2) != 0;
            }
        }
        
        /**
         * Sets the "ohd" element
         */
        public void setOhd(aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd ohd)
        {
            synchronized (monitor())
            {
                check_orphaned();
                aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd target = null;
                target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd)get_store().find_element_user(OHD$2, 0);
                if (target == null)
                {
                    target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd)get_store().add_element_user(OHD$2);
                }
                target.set(ohd);
            }
        }
        
        /**
         * Appends and returns a new empty "ohd" element
         */
        public aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd addNewOhd()
        {
            synchronized (monitor())
            {
                check_orphaned();
                aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd target = null;
                target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd)get_store().add_element_user(OHD$2);
                return target;
            }
        }
        
        /**
         * Nils the "ohd" element
         */
        public void setNilOhd()
        {
            synchronized (monitor())
            {
                check_orphaned();
                aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd target = null;
                target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd)get_store().find_element_user(OHD$2, 0);
                if (target == null)
                {
                    target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd)get_store().add_element_user(OHD$2);
                }
                target.setNil();
            }
        }
        
        /**
         * Unsets the "ohd" element
         */
        public void unsetOhd()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(OHD$2, 0);
            }
        }
    }
}
