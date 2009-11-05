/*
 * An XML document type.
 * Localname: eraseActionFileResponse
 * Namespace: http://wt_1_0.serviceprovider.nettracer.aero
 * Java type: aero.nettracer.serviceprovider.wt_1_0.EraseActionFileResponseDocument
 *
 * Automatically generated - do not modify.
 */
package aero.nettracer.serviceprovider.wt_1_0.impl;
/**
 * A document containing one eraseActionFileResponse(@http://wt_1_0.serviceprovider.nettracer.aero) element.
 *
 * This is a complex type.
 */
public class EraseActionFileResponseDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aero.nettracer.serviceprovider.wt_1_0.EraseActionFileResponseDocument
{
    
    public EraseActionFileResponseDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName ERASEACTIONFILERESPONSE$0 = 
        new javax.xml.namespace.QName("http://wt_1_0.serviceprovider.nettracer.aero", "eraseActionFileResponse");
    
    
    /**
     * Gets the "eraseActionFileResponse" element
     */
    public aero.nettracer.serviceprovider.wt_1_0.EraseActionFileResponseDocument.EraseActionFileResponse getEraseActionFileResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.wt_1_0.EraseActionFileResponseDocument.EraseActionFileResponse target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.EraseActionFileResponseDocument.EraseActionFileResponse)get_store().find_element_user(ERASEACTIONFILERESPONSE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "eraseActionFileResponse" element
     */
    public void setEraseActionFileResponse(aero.nettracer.serviceprovider.wt_1_0.EraseActionFileResponseDocument.EraseActionFileResponse eraseActionFileResponse)
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.wt_1_0.EraseActionFileResponseDocument.EraseActionFileResponse target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.EraseActionFileResponseDocument.EraseActionFileResponse)get_store().find_element_user(ERASEACTIONFILERESPONSE$0, 0);
            if (target == null)
            {
                target = (aero.nettracer.serviceprovider.wt_1_0.EraseActionFileResponseDocument.EraseActionFileResponse)get_store().add_element_user(ERASEACTIONFILERESPONSE$0);
            }
            target.set(eraseActionFileResponse);
        }
    }
    
    /**
     * Appends and returns a new empty "eraseActionFileResponse" element
     */
    public aero.nettracer.serviceprovider.wt_1_0.EraseActionFileResponseDocument.EraseActionFileResponse addNewEraseActionFileResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.wt_1_0.EraseActionFileResponseDocument.EraseActionFileResponse target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.EraseActionFileResponseDocument.EraseActionFileResponse)get_store().add_element_user(ERASEACTIONFILERESPONSE$0);
            return target;
        }
    }
    /**
     * An XML eraseActionFileResponse(@http://wt_1_0.serviceprovider.nettracer.aero).
     *
     * This is a complex type.
     */
    public static class EraseActionFileResponseImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aero.nettracer.serviceprovider.wt_1_0.EraseActionFileResponseDocument.EraseActionFileResponse
    {
        
        public EraseActionFileResponseImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName RETURN$0 = 
            new javax.xml.namespace.QName("http://wt_1_0.serviceprovider.nettracer.aero", "return");
        
        
        /**
         * Gets the "return" element
         */
        public aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse getReturn()
        {
            synchronized (monitor())
            {
                check_orphaned();
                aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse target = null;
                target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse)get_store().find_element_user(RETURN$0, 0);
                if (target == null)
                {
                    return null;
                }
                return target;
            }
        }
        
        /**
         * Tests for nil "return" element
         */
        public boolean isNilReturn()
        {
            synchronized (monitor())
            {
                check_orphaned();
                aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse target = null;
                target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse)get_store().find_element_user(RETURN$0, 0);
                if (target == null) return false;
                return target.isNil();
            }
        }
        
        /**
         * True if has "return" element
         */
        public boolean isSetReturn()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(RETURN$0) != 0;
            }
        }
        
        /**
         * Sets the "return" element
         */
        public void setReturn(aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse xreturn)
        {
            synchronized (monitor())
            {
                check_orphaned();
                aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse target = null;
                target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse)get_store().find_element_user(RETURN$0, 0);
                if (target == null)
                {
                    target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse)get_store().add_element_user(RETURN$0);
                }
                target.set(xreturn);
            }
        }
        
        /**
         * Appends and returns a new empty "return" element
         */
        public aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse addNewReturn()
        {
            synchronized (monitor())
            {
                check_orphaned();
                aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse target = null;
                target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse)get_store().add_element_user(RETURN$0);
                return target;
            }
        }
        
        /**
         * Nils the "return" element
         */
        public void setNilReturn()
        {
            synchronized (monitor())
            {
                check_orphaned();
                aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse target = null;
                target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse)get_store().find_element_user(RETURN$0, 0);
                if (target == null)
                {
                    target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse)get_store().add_element_user(RETURN$0);
                }
                target.setNil();
            }
        }
        
        /**
         * Unsets the "return" element
         */
        public void unsetReturn()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(RETURN$0, 0);
            }
        }
    }
}
