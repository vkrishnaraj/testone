/*
 * XML Type:  WS_PVPaxCommunication
 * Namespace: http://paxview.v1_1.ws.nettracer.bagnet.com/xsd
 * Java type: com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVPaxCommunication
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.v1_1.paxview.xsd.impl;
/**
 * An XML WS_PVPaxCommunication(@http://paxview.v1_1.ws.nettracer.bagnet.com/xsd).
 *
 * This is a complex type.
 */
public class WSPVPaxCommunicationImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.bagnet.nettracer.ws.v1_1.paxview.xsd.WSPVPaxCommunication
{
    
    public WSPVPaxCommunicationImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName ACKNOWLEDGEDAGENT$0 = 
        new javax.xml.namespace.QName("http://paxview.v1_1.ws.nettracer.bagnet.com/xsd", "acknowledged_agent");
    private static final javax.xml.namespace.QName ACKNOWLEDGEDAIRLINE$2 = 
        new javax.xml.namespace.QName("http://paxview.v1_1.ws.nettracer.bagnet.com/xsd", "acknowledged_airline");
    private static final javax.xml.namespace.QName ACKNOWLEDGEDTIMESTAMP$4 = 
        new javax.xml.namespace.QName("http://paxview.v1_1.ws.nettracer.bagnet.com/xsd", "acknowledged_timestamp");
    private static final javax.xml.namespace.QName AGENT$6 = 
        new javax.xml.namespace.QName("http://paxview.v1_1.ws.nettracer.bagnet.com/xsd", "agent");
    private static final javax.xml.namespace.QName COMMENT$8 = 
        new javax.xml.namespace.QName("http://paxview.v1_1.ws.nettracer.bagnet.com/xsd", "comment");
    private static final javax.xml.namespace.QName CREATETIMESTAMP$10 = 
        new javax.xml.namespace.QName("http://paxview.v1_1.ws.nettracer.bagnet.com/xsd", "create_timestamp");
    private static final javax.xml.namespace.QName STATUS$12 = 
        new javax.xml.namespace.QName("http://paxview.v1_1.ws.nettracer.bagnet.com/xsd", "status");
    
    
    /**
     * Gets the "acknowledged_agent" element
     */
    public java.lang.String getAcknowledgedAgent()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ACKNOWLEDGEDAGENT$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "acknowledged_agent" element
     */
    public org.apache.xmlbeans.XmlString xgetAcknowledgedAgent()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ACKNOWLEDGEDAGENT$0, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "acknowledged_agent" element
     */
    public boolean isNilAcknowledgedAgent()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ACKNOWLEDGEDAGENT$0, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "acknowledged_agent" element
     */
    public boolean isSetAcknowledgedAgent()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(ACKNOWLEDGEDAGENT$0) != 0;
        }
    }
    
    /**
     * Sets the "acknowledged_agent" element
     */
    public void setAcknowledgedAgent(java.lang.String acknowledgedAgent)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ACKNOWLEDGEDAGENT$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ACKNOWLEDGEDAGENT$0);
            }
            target.setStringValue(acknowledgedAgent);
        }
    }
    
    /**
     * Sets (as xml) the "acknowledged_agent" element
     */
    public void xsetAcknowledgedAgent(org.apache.xmlbeans.XmlString acknowledgedAgent)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ACKNOWLEDGEDAGENT$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(ACKNOWLEDGEDAGENT$0);
            }
            target.set(acknowledgedAgent);
        }
    }
    
    /**
     * Nils the "acknowledged_agent" element
     */
    public void setNilAcknowledgedAgent()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ACKNOWLEDGEDAGENT$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(ACKNOWLEDGEDAGENT$0);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "acknowledged_agent" element
     */
    public void unsetAcknowledgedAgent()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(ACKNOWLEDGEDAGENT$0, 0);
        }
    }
    
    /**
     * Gets the "acknowledged_airline" element
     */
    public java.lang.String getAcknowledgedAirline()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ACKNOWLEDGEDAIRLINE$2, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "acknowledged_airline" element
     */
    public org.apache.xmlbeans.XmlString xgetAcknowledgedAirline()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ACKNOWLEDGEDAIRLINE$2, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "acknowledged_airline" element
     */
    public boolean isNilAcknowledgedAirline()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ACKNOWLEDGEDAIRLINE$2, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "acknowledged_airline" element
     */
    public boolean isSetAcknowledgedAirline()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(ACKNOWLEDGEDAIRLINE$2) != 0;
        }
    }
    
    /**
     * Sets the "acknowledged_airline" element
     */
    public void setAcknowledgedAirline(java.lang.String acknowledgedAirline)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ACKNOWLEDGEDAIRLINE$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ACKNOWLEDGEDAIRLINE$2);
            }
            target.setStringValue(acknowledgedAirline);
        }
    }
    
    /**
     * Sets (as xml) the "acknowledged_airline" element
     */
    public void xsetAcknowledgedAirline(org.apache.xmlbeans.XmlString acknowledgedAirline)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ACKNOWLEDGEDAIRLINE$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(ACKNOWLEDGEDAIRLINE$2);
            }
            target.set(acknowledgedAirline);
        }
    }
    
    /**
     * Nils the "acknowledged_airline" element
     */
    public void setNilAcknowledgedAirline()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ACKNOWLEDGEDAIRLINE$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(ACKNOWLEDGEDAIRLINE$2);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "acknowledged_airline" element
     */
    public void unsetAcknowledgedAirline()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(ACKNOWLEDGEDAIRLINE$2, 0);
        }
    }
    
    /**
     * Gets the "acknowledged_timestamp" element
     */
    public java.lang.String getAcknowledgedTimestamp()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ACKNOWLEDGEDTIMESTAMP$4, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "acknowledged_timestamp" element
     */
    public org.apache.xmlbeans.XmlString xgetAcknowledgedTimestamp()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ACKNOWLEDGEDTIMESTAMP$4, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "acknowledged_timestamp" element
     */
    public boolean isNilAcknowledgedTimestamp()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ACKNOWLEDGEDTIMESTAMP$4, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "acknowledged_timestamp" element
     */
    public boolean isSetAcknowledgedTimestamp()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(ACKNOWLEDGEDTIMESTAMP$4) != 0;
        }
    }
    
    /**
     * Sets the "acknowledged_timestamp" element
     */
    public void setAcknowledgedTimestamp(java.lang.String acknowledgedTimestamp)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ACKNOWLEDGEDTIMESTAMP$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ACKNOWLEDGEDTIMESTAMP$4);
            }
            target.setStringValue(acknowledgedTimestamp);
        }
    }
    
    /**
     * Sets (as xml) the "acknowledged_timestamp" element
     */
    public void xsetAcknowledgedTimestamp(org.apache.xmlbeans.XmlString acknowledgedTimestamp)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ACKNOWLEDGEDTIMESTAMP$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(ACKNOWLEDGEDTIMESTAMP$4);
            }
            target.set(acknowledgedTimestamp);
        }
    }
    
    /**
     * Nils the "acknowledged_timestamp" element
     */
    public void setNilAcknowledgedTimestamp()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ACKNOWLEDGEDTIMESTAMP$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(ACKNOWLEDGEDTIMESTAMP$4);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "acknowledged_timestamp" element
     */
    public void unsetAcknowledgedTimestamp()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(ACKNOWLEDGEDTIMESTAMP$4, 0);
        }
    }
    
    /**
     * Gets the "agent" element
     */
    public java.lang.String getAgent()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(AGENT$6, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "agent" element
     */
    public org.apache.xmlbeans.XmlString xgetAgent()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(AGENT$6, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "agent" element
     */
    public boolean isNilAgent()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(AGENT$6, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "agent" element
     */
    public boolean isSetAgent()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(AGENT$6) != 0;
        }
    }
    
    /**
     * Sets the "agent" element
     */
    public void setAgent(java.lang.String agent)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(AGENT$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(AGENT$6);
            }
            target.setStringValue(agent);
        }
    }
    
    /**
     * Sets (as xml) the "agent" element
     */
    public void xsetAgent(org.apache.xmlbeans.XmlString agent)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(AGENT$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(AGENT$6);
            }
            target.set(agent);
        }
    }
    
    /**
     * Nils the "agent" element
     */
    public void setNilAgent()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(AGENT$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(AGENT$6);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "agent" element
     */
    public void unsetAgent()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(AGENT$6, 0);
        }
    }
    
    /**
     * Gets the "comment" element
     */
    public java.lang.String getComment()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COMMENT$8, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "comment" element
     */
    public org.apache.xmlbeans.XmlString xgetComment()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COMMENT$8, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "comment" element
     */
    public boolean isNilComment()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COMMENT$8, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "comment" element
     */
    public boolean isSetComment()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(COMMENT$8) != 0;
        }
    }
    
    /**
     * Sets the "comment" element
     */
    public void setComment(java.lang.String comment)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(COMMENT$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(COMMENT$8);
            }
            target.setStringValue(comment);
        }
    }
    
    /**
     * Sets (as xml) the "comment" element
     */
    public void xsetComment(org.apache.xmlbeans.XmlString comment)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COMMENT$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(COMMENT$8);
            }
            target.set(comment);
        }
    }
    
    /**
     * Nils the "comment" element
     */
    public void setNilComment()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(COMMENT$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(COMMENT$8);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "comment" element
     */
    public void unsetComment()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(COMMENT$8, 0);
        }
    }
    
    /**
     * Gets the "create_timestamp" element
     */
    public java.lang.String getCreateTimestamp()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CREATETIMESTAMP$10, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "create_timestamp" element
     */
    public org.apache.xmlbeans.XmlString xgetCreateTimestamp()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CREATETIMESTAMP$10, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "create_timestamp" element
     */
    public boolean isNilCreateTimestamp()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CREATETIMESTAMP$10, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "create_timestamp" element
     */
    public boolean isSetCreateTimestamp()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CREATETIMESTAMP$10) != 0;
        }
    }
    
    /**
     * Sets the "create_timestamp" element
     */
    public void setCreateTimestamp(java.lang.String createTimestamp)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CREATETIMESTAMP$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CREATETIMESTAMP$10);
            }
            target.setStringValue(createTimestamp);
        }
    }
    
    /**
     * Sets (as xml) the "create_timestamp" element
     */
    public void xsetCreateTimestamp(org.apache.xmlbeans.XmlString createTimestamp)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CREATETIMESTAMP$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CREATETIMESTAMP$10);
            }
            target.set(createTimestamp);
        }
    }
    
    /**
     * Nils the "create_timestamp" element
     */
    public void setNilCreateTimestamp()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CREATETIMESTAMP$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CREATETIMESTAMP$10);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "create_timestamp" element
     */
    public void unsetCreateTimestamp()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CREATETIMESTAMP$10, 0);
        }
    }
    
    /**
     * Gets the "status" element
     */
    public java.lang.String getStatus()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STATUS$12, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "status" element
     */
    public org.apache.xmlbeans.XmlString xgetStatus()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STATUS$12, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "status" element
     */
    public boolean isNilStatus()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STATUS$12, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "status" element
     */
    public boolean isSetStatus()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(STATUS$12) != 0;
        }
    }
    
    /**
     * Sets the "status" element
     */
    public void setStatus(java.lang.String status)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STATUS$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(STATUS$12);
            }
            target.setStringValue(status);
        }
    }
    
    /**
     * Sets (as xml) the "status" element
     */
    public void xsetStatus(org.apache.xmlbeans.XmlString status)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STATUS$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(STATUS$12);
            }
            target.set(status);
        }
    }
    
    /**
     * Nils the "status" element
     */
    public void setNilStatus()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STATUS$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(STATUS$12);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "status" element
     */
    public void unsetStatus()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(STATUS$12, 0);
        }
    }
}
