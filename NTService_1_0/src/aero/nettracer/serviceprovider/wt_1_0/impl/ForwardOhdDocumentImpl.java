/*
 * An XML document type.
 * Localname: forwardOhd
 * Namespace: http://wt_1_0.serviceprovider.nettracer.aero
 * Java type: aero.nettracer.serviceprovider.wt_1_0.ForwardOhdDocument
 *
 * Automatically generated - do not modify.
 */
package aero.nettracer.serviceprovider.wt_1_0.impl;
/**
 * A document containing one forwardOhd(@http://wt_1_0.serviceprovider.nettracer.aero) element.
 *
 * This is a complex type.
 */
public class ForwardOhdDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aero.nettracer.serviceprovider.wt_1_0.ForwardOhdDocument
{
    
    public ForwardOhdDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName FORWARDOHD$0 = 
        new javax.xml.namespace.QName("http://wt_1_0.serviceprovider.nettracer.aero", "forwardOhd");
    
    
    /**
     * Gets the "forwardOhd" element
     */
    public aero.nettracer.serviceprovider.wt_1_0.ForwardOhdDocument.ForwardOhd getForwardOhd()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.wt_1_0.ForwardOhdDocument.ForwardOhd target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.ForwardOhdDocument.ForwardOhd)get_store().find_element_user(FORWARDOHD$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "forwardOhd" element
     */
    public void setForwardOhd(aero.nettracer.serviceprovider.wt_1_0.ForwardOhdDocument.ForwardOhd forwardOhd)
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.wt_1_0.ForwardOhdDocument.ForwardOhd target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.ForwardOhdDocument.ForwardOhd)get_store().find_element_user(FORWARDOHD$0, 0);
            if (target == null)
            {
                target = (aero.nettracer.serviceprovider.wt_1_0.ForwardOhdDocument.ForwardOhd)get_store().add_element_user(FORWARDOHD$0);
            }
            target.set(forwardOhd);
        }
    }
    
    /**
     * Appends and returns a new empty "forwardOhd" element
     */
    public aero.nettracer.serviceprovider.wt_1_0.ForwardOhdDocument.ForwardOhd addNewForwardOhd()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.wt_1_0.ForwardOhdDocument.ForwardOhd target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.ForwardOhdDocument.ForwardOhd)get_store().add_element_user(FORWARDOHD$0);
            return target;
        }
    }
    /**
     * An XML forwardOhd(@http://wt_1_0.serviceprovider.nettracer.aero).
     *
     * This is a complex type.
     */
    public static class ForwardOhdImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aero.nettracer.serviceprovider.wt_1_0.ForwardOhdDocument.ForwardOhd
    {
        
        public ForwardOhdImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName HEADER$0 = 
            new javax.xml.namespace.QName("http://wt_1_0.serviceprovider.nettracer.aero", "header");
        private static final javax.xml.namespace.QName DATA$2 = 
            new javax.xml.namespace.QName("http://wt_1_0.serviceprovider.nettracer.aero", "data");
        
        
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
         * Gets the "data" element
         */
        public aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardOhd getData()
        {
            synchronized (monitor())
            {
                check_orphaned();
                aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardOhd target = null;
                target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardOhd)get_store().find_element_user(DATA$2, 0);
                if (target == null)
                {
                    return null;
                }
                return target;
            }
        }
        
        /**
         * Tests for nil "data" element
         */
        public boolean isNilData()
        {
            synchronized (monitor())
            {
                check_orphaned();
                aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardOhd target = null;
                target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardOhd)get_store().find_element_user(DATA$2, 0);
                if (target == null) return false;
                return target.isNil();
            }
        }
        
        /**
         * True if has "data" element
         */
        public boolean isSetData()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(DATA$2) != 0;
            }
        }
        
        /**
         * Sets the "data" element
         */
        public void setData(aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardOhd data)
        {
            synchronized (monitor())
            {
                check_orphaned();
                aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardOhd target = null;
                target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardOhd)get_store().find_element_user(DATA$2, 0);
                if (target == null)
                {
                    target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardOhd)get_store().add_element_user(DATA$2);
                }
                target.set(data);
            }
        }
        
        /**
         * Appends and returns a new empty "data" element
         */
        public aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardOhd addNewData()
        {
            synchronized (monitor())
            {
                check_orphaned();
                aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardOhd target = null;
                target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardOhd)get_store().add_element_user(DATA$2);
                return target;
            }
        }
        
        /**
         * Nils the "data" element
         */
        public void setNilData()
        {
            synchronized (monitor())
            {
                check_orphaned();
                aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardOhd target = null;
                target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardOhd)get_store().find_element_user(DATA$2, 0);
                if (target == null)
                {
                    target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardOhd)get_store().add_element_user(DATA$2);
                }
                target.setNil();
            }
        }
        
        /**
         * Unsets the "data" element
         */
        public void unsetData()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(DATA$2, 0);
            }
        }
    }
}
