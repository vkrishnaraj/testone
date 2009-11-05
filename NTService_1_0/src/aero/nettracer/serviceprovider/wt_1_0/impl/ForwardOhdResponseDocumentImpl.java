/*
 * An XML document type.
 * Localname: forwardOhdResponse
 * Namespace: http://wt_1_0.serviceprovider.nettracer.aero
 * Java type: aero.nettracer.serviceprovider.wt_1_0.ForwardOhdResponseDocument
 *
 * Automatically generated - do not modify.
 */
package aero.nettracer.serviceprovider.wt_1_0.impl;
/**
 * A document containing one forwardOhdResponse(@http://wt_1_0.serviceprovider.nettracer.aero) element.
 *
 * This is a complex type.
 */
public class ForwardOhdResponseDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aero.nettracer.serviceprovider.wt_1_0.ForwardOhdResponseDocument
{
    
    public ForwardOhdResponseDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName FORWARDOHDRESPONSE$0 = 
        new javax.xml.namespace.QName("http://wt_1_0.serviceprovider.nettracer.aero", "forwardOhdResponse");
    
    
    /**
     * Gets the "forwardOhdResponse" element
     */
    public aero.nettracer.serviceprovider.wt_1_0.ForwardOhdResponseDocument.ForwardOhdResponse getForwardOhdResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.wt_1_0.ForwardOhdResponseDocument.ForwardOhdResponse target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.ForwardOhdResponseDocument.ForwardOhdResponse)get_store().find_element_user(FORWARDOHDRESPONSE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "forwardOhdResponse" element
     */
    public void setForwardOhdResponse(aero.nettracer.serviceprovider.wt_1_0.ForwardOhdResponseDocument.ForwardOhdResponse forwardOhdResponse)
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.wt_1_0.ForwardOhdResponseDocument.ForwardOhdResponse target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.ForwardOhdResponseDocument.ForwardOhdResponse)get_store().find_element_user(FORWARDOHDRESPONSE$0, 0);
            if (target == null)
            {
                target = (aero.nettracer.serviceprovider.wt_1_0.ForwardOhdResponseDocument.ForwardOhdResponse)get_store().add_element_user(FORWARDOHDRESPONSE$0);
            }
            target.set(forwardOhdResponse);
        }
    }
    
    /**
     * Appends and returns a new empty "forwardOhdResponse" element
     */
    public aero.nettracer.serviceprovider.wt_1_0.ForwardOhdResponseDocument.ForwardOhdResponse addNewForwardOhdResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.wt_1_0.ForwardOhdResponseDocument.ForwardOhdResponse target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.ForwardOhdResponseDocument.ForwardOhdResponse)get_store().add_element_user(FORWARDOHDRESPONSE$0);
            return target;
        }
    }
    /**
     * An XML forwardOhdResponse(@http://wt_1_0.serviceprovider.nettracer.aero).
     *
     * This is a complex type.
     */
    public static class ForwardOhdResponseImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aero.nettracer.serviceprovider.wt_1_0.ForwardOhdResponseDocument.ForwardOhdResponse
    {
        
        public ForwardOhdResponseImpl(org.apache.xmlbeans.SchemaType sType)
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
