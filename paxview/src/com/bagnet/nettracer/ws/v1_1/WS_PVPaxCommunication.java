/**
 * WS_PVPaxCommunication.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.bagnet.nettracer.ws.v1_1;

public class WS_PVPaxCommunication  implements java.io.Serializable {
    private java.lang.String acknowledged_agent;

    private java.lang.String acknowledged_airline;

    private java.lang.String acknowledged_timestamp;

    private java.lang.String agent;

    private java.lang.String comment;

    private java.lang.String create_timestamp;

    private java.lang.String status;

    public WS_PVPaxCommunication() {
    }

    public WS_PVPaxCommunication(
           java.lang.String acknowledged_agent,
           java.lang.String acknowledged_airline,
           java.lang.String acknowledged_timestamp,
           java.lang.String agent,
           java.lang.String comment,
           java.lang.String create_timestamp,
           java.lang.String status) {
           this.acknowledged_agent = acknowledged_agent;
           this.acknowledged_airline = acknowledged_airline;
           this.acknowledged_timestamp = acknowledged_timestamp;
           this.agent = agent;
           this.comment = comment;
           this.create_timestamp = create_timestamp;
           this.status = status;
    }


    /**
     * Gets the acknowledged_agent value for this WS_PVPaxCommunication.
     * 
     * @return acknowledged_agent
     */
    public java.lang.String getAcknowledged_agent() {
        return acknowledged_agent;
    }


    /**
     * Sets the acknowledged_agent value for this WS_PVPaxCommunication.
     * 
     * @param acknowledged_agent
     */
    public void setAcknowledged_agent(java.lang.String acknowledged_agent) {
        this.acknowledged_agent = acknowledged_agent;
    }


    /**
     * Gets the acknowledged_airline value for this WS_PVPaxCommunication.
     * 
     * @return acknowledged_airline
     */
    public java.lang.String getAcknowledged_airline() {
        return acknowledged_airline;
    }


    /**
     * Sets the acknowledged_airline value for this WS_PVPaxCommunication.
     * 
     * @param acknowledged_airline
     */
    public void setAcknowledged_airline(java.lang.String acknowledged_airline) {
        this.acknowledged_airline = acknowledged_airline;
    }


    /**
     * Gets the acknowledged_timestamp value for this WS_PVPaxCommunication.
     * 
     * @return acknowledged_timestamp
     */
    public java.lang.String getAcknowledged_timestamp() {
        return acknowledged_timestamp;
    }


    /**
     * Sets the acknowledged_timestamp value for this WS_PVPaxCommunication.
     * 
     * @param acknowledged_timestamp
     */
    public void setAcknowledged_timestamp(java.lang.String acknowledged_timestamp) {
        this.acknowledged_timestamp = acknowledged_timestamp;
    }


    /**
     * Gets the agent value for this WS_PVPaxCommunication.
     * 
     * @return agent
     */
    public java.lang.String getAgent() {
        return agent;
    }


    /**
     * Sets the agent value for this WS_PVPaxCommunication.
     * 
     * @param agent
     */
    public void setAgent(java.lang.String agent) {
        this.agent = agent;
    }


    /**
     * Gets the comment value for this WS_PVPaxCommunication.
     * 
     * @return comment
     */
    public java.lang.String getComment() {
        return comment;
    }


    /**
     * Sets the comment value for this WS_PVPaxCommunication.
     * 
     * @param comment
     */
    public void setComment(java.lang.String comment) {
        this.comment = comment;
    }


    /**
     * Gets the create_timestamp value for this WS_PVPaxCommunication.
     * 
     * @return create_timestamp
     */
    public java.lang.String getCreate_timestamp() {
        return create_timestamp;
    }


    /**
     * Sets the create_timestamp value for this WS_PVPaxCommunication.
     * 
     * @param create_timestamp
     */
    public void setCreate_timestamp(java.lang.String create_timestamp) {
        this.create_timestamp = create_timestamp;
    }


    /**
     * Gets the status value for this WS_PVPaxCommunication.
     * 
     * @return status
     */
    public java.lang.String getStatus() {
        return status;
    }


    /**
     * Sets the status value for this WS_PVPaxCommunication.
     * 
     * @param status
     */
    public void setStatus(java.lang.String status) {
        this.status = status;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WS_PVPaxCommunication)) return false;
        WS_PVPaxCommunication other = (WS_PVPaxCommunication) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.acknowledged_agent==null && other.getAcknowledged_agent()==null) || 
             (this.acknowledged_agent!=null &&
              this.acknowledged_agent.equals(other.getAcknowledged_agent()))) &&
            ((this.acknowledged_airline==null && other.getAcknowledged_airline()==null) || 
             (this.acknowledged_airline!=null &&
              this.acknowledged_airline.equals(other.getAcknowledged_airline()))) &&
            ((this.acknowledged_timestamp==null && other.getAcknowledged_timestamp()==null) || 
             (this.acknowledged_timestamp!=null &&
              this.acknowledged_timestamp.equals(other.getAcknowledged_timestamp()))) &&
            ((this.agent==null && other.getAgent()==null) || 
             (this.agent!=null &&
              this.agent.equals(other.getAgent()))) &&
            ((this.comment==null && other.getComment()==null) || 
             (this.comment!=null &&
              this.comment.equals(other.getComment()))) &&
            ((this.create_timestamp==null && other.getCreate_timestamp()==null) || 
             (this.create_timestamp!=null &&
              this.create_timestamp.equals(other.getCreate_timestamp()))) &&
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getAcknowledged_agent() != null) {
            _hashCode += getAcknowledged_agent().hashCode();
        }
        if (getAcknowledged_airline() != null) {
            _hashCode += getAcknowledged_airline().hashCode();
        }
        if (getAcknowledged_timestamp() != null) {
            _hashCode += getAcknowledged_timestamp().hashCode();
        }
        if (getAgent() != null) {
            _hashCode += getAgent().hashCode();
        }
        if (getComment() != null) {
            _hashCode += getComment().hashCode();
        }
        if (getCreate_timestamp() != null) {
            _hashCode += getCreate_timestamp().hashCode();
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WS_PVPaxCommunication.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://paxview.v1_1.ws.nettracer.bagnet.com/xsd", "WS_PVPaxCommunication"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acknowledged_agent");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paxview.v1_1.ws.nettracer.bagnet.com/xsd", "acknowledged_agent"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acknowledged_airline");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paxview.v1_1.ws.nettracer.bagnet.com/xsd", "acknowledged_airline"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acknowledged_timestamp");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paxview.v1_1.ws.nettracer.bagnet.com/xsd", "acknowledged_timestamp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("agent");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paxview.v1_1.ws.nettracer.bagnet.com/xsd", "agent"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("comment");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paxview.v1_1.ws.nettracer.bagnet.com/xsd", "comment"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("create_timestamp");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paxview.v1_1.ws.nettracer.bagnet.com/xsd", "create_timestamp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paxview.v1_1.ws.nettracer.bagnet.com/xsd", "status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
