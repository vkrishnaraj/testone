/*
 * An XML document type.
 * Localname: getAdvancedIncidentsPVPnrResponse
 * Namespace: http://passengerview.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrResponseDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.passengerview.impl;
/**
 * A document containing one getAdvancedIncidentsPVPnrResponse(@http://passengerview.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public class GetAdvancedIncidentsPVPnrResponseDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrResponseDocument
{
    
    public GetAdvancedIncidentsPVPnrResponseDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName GETADVANCEDINCIDENTSPVPNRRESPONSE$0 = 
        new javax.xml.namespace.QName("http://passengerview.ws.nettracer.bagnet.com", "getAdvancedIncidentsPVPnrResponse");
    
    
    /**
     * Gets the "getAdvancedIncidentsPVPnrResponse" element
     */
    public com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrResponseDocument.GetAdvancedIncidentsPVPnrResponse getGetAdvancedIncidentsPVPnrResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrResponseDocument.GetAdvancedIncidentsPVPnrResponse target = null;
            target = (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrResponseDocument.GetAdvancedIncidentsPVPnrResponse)get_store().find_element_user(GETADVANCEDINCIDENTSPVPNRRESPONSE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "getAdvancedIncidentsPVPnrResponse" element
     */
    public void setGetAdvancedIncidentsPVPnrResponse(com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrResponseDocument.GetAdvancedIncidentsPVPnrResponse getAdvancedIncidentsPVPnrResponse)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrResponseDocument.GetAdvancedIncidentsPVPnrResponse target = null;
            target = (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrResponseDocument.GetAdvancedIncidentsPVPnrResponse)get_store().find_element_user(GETADVANCEDINCIDENTSPVPNRRESPONSE$0, 0);
            if (target == null)
            {
                target = (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrResponseDocument.GetAdvancedIncidentsPVPnrResponse)get_store().add_element_user(GETADVANCEDINCIDENTSPVPNRRESPONSE$0);
            }
            target.set(getAdvancedIncidentsPVPnrResponse);
        }
    }
    
    /**
     * Appends and returns a new empty "getAdvancedIncidentsPVPnrResponse" element
     */
    public com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrResponseDocument.GetAdvancedIncidentsPVPnrResponse addNewGetAdvancedIncidentsPVPnrResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrResponseDocument.GetAdvancedIncidentsPVPnrResponse target = null;
            target = (com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrResponseDocument.GetAdvancedIncidentsPVPnrResponse)get_store().add_element_user(GETADVANCEDINCIDENTSPVPNRRESPONSE$0);
            return target;
        }
    }
    /**
     * An XML getAdvancedIncidentsPVPnrResponse(@http://passengerview.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public static class GetAdvancedIncidentsPVPnrResponseImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.passengerview.GetAdvancedIncidentsPVPnrResponseDocument.GetAdvancedIncidentsPVPnrResponse
    {
        
        public GetAdvancedIncidentsPVPnrResponseImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName RETURN$0 = 
            new javax.xml.namespace.QName("http://passengerview.ws.nettracer.bagnet.com", "return");
        
        
        /**
         * Gets array of all "return" elements
         */
        public com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident[] getReturnArray()
        {
            synchronized (monitor())
            {
                check_orphaned();
                java.util.List targetList = new java.util.ArrayList();
                get_store().find_all_element_users(RETURN$0, targetList);
                com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident[] result = new com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident[targetList.size()];
                targetList.toArray(result);
                return result;
            }
        }
        
        /**
         * Gets ith "return" element
         */
        public com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident getReturnArray(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident)get_store().find_element_user(RETURN$0, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                return target;
            }
        }
        
        /**
         * Tests for nil ith "return" element
         */
        public boolean isNilReturnArray(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident)get_store().find_element_user(RETURN$0, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                return target.isNil();
            }
        }
        
        /**
         * Returns number of "return" element
         */
        public int sizeOfReturnArray()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(RETURN$0);
            }
        }
        
        /**
         * Sets array of all "return" element
         */
        public void setReturnArray(com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident[] xreturnArray)
        {
            synchronized (monitor())
            {
                check_orphaned();
                arraySetterHelper(xreturnArray, RETURN$0);
            }
        }
        
        /**
         * Sets ith "return" element
         */
        public void setReturnArray(int i, com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident xreturn)
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident)get_store().find_element_user(RETURN$0, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                target.set(xreturn);
            }
        }
        
        /**
         * Nils the ith "return" element
         */
        public void setNilReturnArray(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident)get_store().find_element_user(RETURN$0, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                target.setNil();
            }
        }
        
        /**
         * Inserts and returns a new empty value (as xml) as the ith "return" element
         */
        public com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident insertNewReturn(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident)get_store().insert_element_user(RETURN$0, i);
                return target;
            }
        }
        
        /**
         * Appends and returns a new empty value (as xml) as the last "return" element
         */
        public com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident addNewReturn()
        {
            synchronized (monitor())
            {
                check_orphaned();
                com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident)get_store().add_element_user(RETURN$0);
                return target;
            }
        }
        
        /**
         * Removes the ith "return" element
         */
        public void removeReturn(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(RETURN$0, i);
            }
        }
    }
}
