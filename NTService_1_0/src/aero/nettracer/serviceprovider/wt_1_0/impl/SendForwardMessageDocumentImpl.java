/*
 * An XML document type.
 * Localname: sendForwardMessage
 * Namespace: http://wt_1_0.serviceprovider.nettracer.aero
 * Java type: aero.nettracer.serviceprovider.wt_1_0.SendForwardMessageDocument
 *
 * Automatically generated - do not modify.
 */
package aero.nettracer.serviceprovider.wt_1_0.impl;
/**
 * A document containing one sendForwardMessage(@http://wt_1_0.serviceprovider.nettracer.aero) element.
 *
 * This is a complex type.
 */
public class SendForwardMessageDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aero.nettracer.serviceprovider.wt_1_0.SendForwardMessageDocument
{
    
    public SendForwardMessageDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName SENDFORWARDMESSAGE$0 = 
        new javax.xml.namespace.QName("http://wt_1_0.serviceprovider.nettracer.aero", "sendForwardMessage");
    
    
    /**
     * Gets the "sendForwardMessage" element
     */
    public aero.nettracer.serviceprovider.wt_1_0.SendForwardMessageDocument.SendForwardMessage getSendForwardMessage()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.wt_1_0.SendForwardMessageDocument.SendForwardMessage target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.SendForwardMessageDocument.SendForwardMessage)get_store().find_element_user(SENDFORWARDMESSAGE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "sendForwardMessage" element
     */
    public void setSendForwardMessage(aero.nettracer.serviceprovider.wt_1_0.SendForwardMessageDocument.SendForwardMessage sendForwardMessage)
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.wt_1_0.SendForwardMessageDocument.SendForwardMessage target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.SendForwardMessageDocument.SendForwardMessage)get_store().find_element_user(SENDFORWARDMESSAGE$0, 0);
            if (target == null)
            {
                target = (aero.nettracer.serviceprovider.wt_1_0.SendForwardMessageDocument.SendForwardMessage)get_store().add_element_user(SENDFORWARDMESSAGE$0);
            }
            target.set(sendForwardMessage);
        }
    }
    
    /**
     * Appends and returns a new empty "sendForwardMessage" element
     */
    public aero.nettracer.serviceprovider.wt_1_0.SendForwardMessageDocument.SendForwardMessage addNewSendForwardMessage()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.wt_1_0.SendForwardMessageDocument.SendForwardMessage target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.SendForwardMessageDocument.SendForwardMessage)get_store().add_element_user(SENDFORWARDMESSAGE$0);
            return target;
        }
    }
    /**
     * An XML sendForwardMessage(@http://wt_1_0.serviceprovider.nettracer.aero).
     *
     * This is a complex type.
     */
    public static class SendForwardMessageImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aero.nettracer.serviceprovider.wt_1_0.SendForwardMessageDocument.SendForwardMessage
    {
        
        public SendForwardMessageImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName HEADER$0 = 
            new javax.xml.namespace.QName("http://wt_1_0.serviceprovider.nettracer.aero", "header");
        private static final javax.xml.namespace.QName FORWARDMESSAGE$2 = 
            new javax.xml.namespace.QName("http://wt_1_0.serviceprovider.nettracer.aero", "forwardMessage");
        
        
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
         * Gets the "forwardMessage" element
         */
        public aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardMessage getForwardMessage()
        {
            synchronized (monitor())
            {
                check_orphaned();
                aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardMessage target = null;
                target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardMessage)get_store().find_element_user(FORWARDMESSAGE$2, 0);
                if (target == null)
                {
                    return null;
                }
                return target;
            }
        }
        
        /**
         * Tests for nil "forwardMessage" element
         */
        public boolean isNilForwardMessage()
        {
            synchronized (monitor())
            {
                check_orphaned();
                aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardMessage target = null;
                target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardMessage)get_store().find_element_user(FORWARDMESSAGE$2, 0);
                if (target == null) return false;
                return target.isNil();
            }
        }
        
        /**
         * True if has "forwardMessage" element
         */
        public boolean isSetForwardMessage()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(FORWARDMESSAGE$2) != 0;
            }
        }
        
        /**
         * Sets the "forwardMessage" element
         */
        public void setForwardMessage(aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardMessage forwardMessage)
        {
            synchronized (monitor())
            {
                check_orphaned();
                aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardMessage target = null;
                target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardMessage)get_store().find_element_user(FORWARDMESSAGE$2, 0);
                if (target == null)
                {
                    target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardMessage)get_store().add_element_user(FORWARDMESSAGE$2);
                }
                target.set(forwardMessage);
            }
        }
        
        /**
         * Appends and returns a new empty "forwardMessage" element
         */
        public aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardMessage addNewForwardMessage()
        {
            synchronized (monitor())
            {
                check_orphaned();
                aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardMessage target = null;
                target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardMessage)get_store().add_element_user(FORWARDMESSAGE$2);
                return target;
            }
        }
        
        /**
         * Nils the "forwardMessage" element
         */
        public void setNilForwardMessage()
        {
            synchronized (monitor())
            {
                check_orphaned();
                aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardMessage target = null;
                target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardMessage)get_store().find_element_user(FORWARDMESSAGE$2, 0);
                if (target == null)
                {
                    target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardMessage)get_store().add_element_user(FORWARDMESSAGE$2);
                }
                target.setNil();
            }
        }
        
        /**
         * Unsets the "forwardMessage" element
         */
        public void unsetForwardMessage()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(FORWARDMESSAGE$2, 0);
            }
        }
    }
}
