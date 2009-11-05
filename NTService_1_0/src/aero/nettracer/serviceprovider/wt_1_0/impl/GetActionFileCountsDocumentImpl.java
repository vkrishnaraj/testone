/*
 * An XML document type.
 * Localname: getActionFileCounts
 * Namespace: http://wt_1_0.serviceprovider.nettracer.aero
 * Java type: aero.nettracer.serviceprovider.wt_1_0.GetActionFileCountsDocument
 *
 * Automatically generated - do not modify.
 */
package aero.nettracer.serviceprovider.wt_1_0.impl;
/**
 * A document containing one getActionFileCounts(@http://wt_1_0.serviceprovider.nettracer.aero) element.
 *
 * This is a complex type.
 */
public class GetActionFileCountsDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aero.nettracer.serviceprovider.wt_1_0.GetActionFileCountsDocument
{
    
    public GetActionFileCountsDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName GETACTIONFILECOUNTS$0 = 
        new javax.xml.namespace.QName("http://wt_1_0.serviceprovider.nettracer.aero", "getActionFileCounts");
    
    
    /**
     * Gets the "getActionFileCounts" element
     */
    public aero.nettracer.serviceprovider.wt_1_0.GetActionFileCountsDocument.GetActionFileCounts getGetActionFileCounts()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.wt_1_0.GetActionFileCountsDocument.GetActionFileCounts target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.GetActionFileCountsDocument.GetActionFileCounts)get_store().find_element_user(GETACTIONFILECOUNTS$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "getActionFileCounts" element
     */
    public void setGetActionFileCounts(aero.nettracer.serviceprovider.wt_1_0.GetActionFileCountsDocument.GetActionFileCounts getActionFileCounts)
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.wt_1_0.GetActionFileCountsDocument.GetActionFileCounts target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.GetActionFileCountsDocument.GetActionFileCounts)get_store().find_element_user(GETACTIONFILECOUNTS$0, 0);
            if (target == null)
            {
                target = (aero.nettracer.serviceprovider.wt_1_0.GetActionFileCountsDocument.GetActionFileCounts)get_store().add_element_user(GETACTIONFILECOUNTS$0);
            }
            target.set(getActionFileCounts);
        }
    }
    
    /**
     * Appends and returns a new empty "getActionFileCounts" element
     */
    public aero.nettracer.serviceprovider.wt_1_0.GetActionFileCountsDocument.GetActionFileCounts addNewGetActionFileCounts()
    {
        synchronized (monitor())
        {
            check_orphaned();
            aero.nettracer.serviceprovider.wt_1_0.GetActionFileCountsDocument.GetActionFileCounts target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.GetActionFileCountsDocument.GetActionFileCounts)get_store().add_element_user(GETACTIONFILECOUNTS$0);
            return target;
        }
    }
    /**
     * An XML getActionFileCounts(@http://wt_1_0.serviceprovider.nettracer.aero).
     *
     * This is a complex type.
     */
    public static class GetActionFileCountsImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements aero.nettracer.serviceprovider.wt_1_0.GetActionFileCountsDocument.GetActionFileCounts
    {
        
        public GetActionFileCountsImpl(org.apache.xmlbeans.SchemaType sType)
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
        public aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileRequestData getData()
        {
            synchronized (monitor())
            {
                check_orphaned();
                aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileRequestData target = null;
                target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileRequestData)get_store().find_element_user(DATA$2, 0);
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
                aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileRequestData target = null;
                target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileRequestData)get_store().find_element_user(DATA$2, 0);
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
        public void setData(aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileRequestData data)
        {
            synchronized (monitor())
            {
                check_orphaned();
                aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileRequestData target = null;
                target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileRequestData)get_store().find_element_user(DATA$2, 0);
                if (target == null)
                {
                    target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileRequestData)get_store().add_element_user(DATA$2);
                }
                target.set(data);
            }
        }
        
        /**
         * Appends and returns a new empty "data" element
         */
        public aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileRequestData addNewData()
        {
            synchronized (monitor())
            {
                check_orphaned();
                aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileRequestData target = null;
                target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileRequestData)get_store().add_element_user(DATA$2);
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
                aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileRequestData target = null;
                target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileRequestData)get_store().find_element_user(DATA$2, 0);
                if (target == null)
                {
                    target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileRequestData)get_store().add_element_user(DATA$2);
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
