/**
 * WS_PVIncident.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.bagnet.nettracer.ws.v1_1.paxview.xsd;

public class WS_PVIncident  implements java.io.Serializable {
    private java.lang.String comments;

    private java.lang.String dispcreatetime;

    private java.lang.String errorcode;

    private java.lang.String incident_ID;

    private java.lang.String incident_status;

    private java.lang.String itemType;

    private com.bagnet.nettracer.ws.v1_1.paxview.xsd.WS_PVItem[] items;

    private com.bagnet.nettracer.ws.v1_1.paxview.xsd.WS_PVPassenger[] passengers;

    private com.bagnet.nettracer.ws.v1_1.paxview.xsd.WS_PVClaimChecks[] paxClaimchecks;

    private com.bagnet.nettracer.ws.v1_1.paxview.xsd.WS_PVPaxCommunication[] paxCommunication;

    public WS_PVIncident() {
    }

    public WS_PVIncident(
           java.lang.String comments,
           java.lang.String dispcreatetime,
           java.lang.String errorcode,
           java.lang.String incident_ID,
           java.lang.String incident_status,
           java.lang.String itemType,
           com.bagnet.nettracer.ws.v1_1.paxview.xsd.WS_PVItem[] items,
           com.bagnet.nettracer.ws.v1_1.paxview.xsd.WS_PVPassenger[] passengers,
           com.bagnet.nettracer.ws.v1_1.paxview.xsd.WS_PVClaimChecks[] paxClaimchecks,
           com.bagnet.nettracer.ws.v1_1.paxview.xsd.WS_PVPaxCommunication[] paxCommunication) {
           this.comments = comments;
           this.dispcreatetime = dispcreatetime;
           this.errorcode = errorcode;
           this.incident_ID = incident_ID;
           this.incident_status = incident_status;
           this.itemType = itemType;
           this.items = items;
           this.passengers = passengers;
           this.paxClaimchecks = paxClaimchecks;
           this.paxCommunication = paxCommunication;
    }


    /**
     * Gets the comments value for this WS_PVIncident.
     * 
     * @return comments
     */
    public java.lang.String getComments() {
        return comments;
    }


    /**
     * Sets the comments value for this WS_PVIncident.
     * 
     * @param comments
     */
    public void setComments(java.lang.String comments) {
        this.comments = comments;
    }


    /**
     * Gets the dispcreatetime value for this WS_PVIncident.
     * 
     * @return dispcreatetime
     */
    public java.lang.String getDispcreatetime() {
        return dispcreatetime;
    }


    /**
     * Sets the dispcreatetime value for this WS_PVIncident.
     * 
     * @param dispcreatetime
     */
    public void setDispcreatetime(java.lang.String dispcreatetime) {
        this.dispcreatetime = dispcreatetime;
    }


    /**
     * Gets the errorcode value for this WS_PVIncident.
     * 
     * @return errorcode
     */
    public java.lang.String getErrorcode() {
        return errorcode;
    }


    /**
     * Sets the errorcode value for this WS_PVIncident.
     * 
     * @param errorcode
     */
    public void setErrorcode(java.lang.String errorcode) {
        this.errorcode = errorcode;
    }


    /**
     * Gets the incident_ID value for this WS_PVIncident.
     * 
     * @return incident_ID
     */
    public java.lang.String getIncident_ID() {
        return incident_ID;
    }


    /**
     * Sets the incident_ID value for this WS_PVIncident.
     * 
     * @param incident_ID
     */
    public void setIncident_ID(java.lang.String incident_ID) {
        this.incident_ID = incident_ID;
    }


    /**
     * Gets the incident_status value for this WS_PVIncident.
     * 
     * @return incident_status
     */
    public java.lang.String getIncident_status() {
        return incident_status;
    }


    /**
     * Sets the incident_status value for this WS_PVIncident.
     * 
     * @param incident_status
     */
    public void setIncident_status(java.lang.String incident_status) {
        this.incident_status = incident_status;
    }


    /**
     * Gets the itemType value for this WS_PVIncident.
     * 
     * @return itemType
     */
    public java.lang.String getItemType() {
        return itemType;
    }


    /**
     * Sets the itemType value for this WS_PVIncident.
     * 
     * @param itemType
     */
    public void setItemType(java.lang.String itemType) {
        this.itemType = itemType;
    }


    /**
     * Gets the items value for this WS_PVIncident.
     * 
     * @return items
     */
    public com.bagnet.nettracer.ws.v1_1.paxview.xsd.WS_PVItem[] getItems() {
        return items;
    }


    /**
     * Sets the items value for this WS_PVIncident.
     * 
     * @param items
     */
    public void setItems(com.bagnet.nettracer.ws.v1_1.paxview.xsd.WS_PVItem[] items) {
        this.items = items;
    }

    public com.bagnet.nettracer.ws.v1_1.paxview.xsd.WS_PVItem getItems(int i) {
        return this.items[i];
    }

    public void setItems(int i, com.bagnet.nettracer.ws.v1_1.paxview.xsd.WS_PVItem _value) {
        this.items[i] = _value;
    }


    /**
     * Gets the passengers value for this WS_PVIncident.
     * 
     * @return passengers
     */
    public com.bagnet.nettracer.ws.v1_1.paxview.xsd.WS_PVPassenger[] getPassengers() {
        return passengers;
    }


    /**
     * Sets the passengers value for this WS_PVIncident.
     * 
     * @param passengers
     */
    public void setPassengers(com.bagnet.nettracer.ws.v1_1.paxview.xsd.WS_PVPassenger[] passengers) {
        this.passengers = passengers;
    }

    public com.bagnet.nettracer.ws.v1_1.paxview.xsd.WS_PVPassenger getPassengers(int i) {
        return this.passengers[i];
    }

    public void setPassengers(int i, com.bagnet.nettracer.ws.v1_1.paxview.xsd.WS_PVPassenger _value) {
        this.passengers[i] = _value;
    }


    /**
     * Gets the paxClaimchecks value for this WS_PVIncident.
     * 
     * @return paxClaimchecks
     */
    public com.bagnet.nettracer.ws.v1_1.paxview.xsd.WS_PVClaimChecks[] getPaxClaimchecks() {
        return paxClaimchecks;
    }


    /**
     * Sets the paxClaimchecks value for this WS_PVIncident.
     * 
     * @param paxClaimchecks
     */
    public void setPaxClaimchecks(com.bagnet.nettracer.ws.v1_1.paxview.xsd.WS_PVClaimChecks[] paxClaimchecks) {
        this.paxClaimchecks = paxClaimchecks;
    }

    public com.bagnet.nettracer.ws.v1_1.paxview.xsd.WS_PVClaimChecks getPaxClaimchecks(int i) {
        return this.paxClaimchecks[i];
    }

    public void setPaxClaimchecks(int i, com.bagnet.nettracer.ws.v1_1.paxview.xsd.WS_PVClaimChecks _value) {
        this.paxClaimchecks[i] = _value;
    }


    /**
     * Gets the paxCommunication value for this WS_PVIncident.
     * 
     * @return paxCommunication
     */
    public com.bagnet.nettracer.ws.v1_1.paxview.xsd.WS_PVPaxCommunication[] getPaxCommunication() {
        return paxCommunication;
    }


    /**
     * Sets the paxCommunication value for this WS_PVIncident.
     * 
     * @param paxCommunication
     */
    public void setPaxCommunication(com.bagnet.nettracer.ws.v1_1.paxview.xsd.WS_PVPaxCommunication[] paxCommunication) {
        this.paxCommunication = paxCommunication;
    }

    public com.bagnet.nettracer.ws.v1_1.paxview.xsd.WS_PVPaxCommunication getPaxCommunication(int i) {
        return this.paxCommunication[i];
    }

    public void setPaxCommunication(int i, com.bagnet.nettracer.ws.v1_1.paxview.xsd.WS_PVPaxCommunication _value) {
        this.paxCommunication[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WS_PVIncident)) return false;
        WS_PVIncident other = (WS_PVIncident) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.comments==null && other.getComments()==null) || 
             (this.comments!=null &&
              this.comments.equals(other.getComments()))) &&
            ((this.dispcreatetime==null && other.getDispcreatetime()==null) || 
             (this.dispcreatetime!=null &&
              this.dispcreatetime.equals(other.getDispcreatetime()))) &&
            ((this.errorcode==null && other.getErrorcode()==null) || 
             (this.errorcode!=null &&
              this.errorcode.equals(other.getErrorcode()))) &&
            ((this.incident_ID==null && other.getIncident_ID()==null) || 
             (this.incident_ID!=null &&
              this.incident_ID.equals(other.getIncident_ID()))) &&
            ((this.incident_status==null && other.getIncident_status()==null) || 
             (this.incident_status!=null &&
              this.incident_status.equals(other.getIncident_status()))) &&
            ((this.itemType==null && other.getItemType()==null) || 
             (this.itemType!=null &&
              this.itemType.equals(other.getItemType()))) &&
            ((this.items==null && other.getItems()==null) || 
             (this.items!=null &&
              java.util.Arrays.equals(this.items, other.getItems()))) &&
            ((this.passengers==null && other.getPassengers()==null) || 
             (this.passengers!=null &&
              java.util.Arrays.equals(this.passengers, other.getPassengers()))) &&
            ((this.paxClaimchecks==null && other.getPaxClaimchecks()==null) || 
             (this.paxClaimchecks!=null &&
              java.util.Arrays.equals(this.paxClaimchecks, other.getPaxClaimchecks()))) &&
            ((this.paxCommunication==null && other.getPaxCommunication()==null) || 
             (this.paxCommunication!=null &&
              java.util.Arrays.equals(this.paxCommunication, other.getPaxCommunication())));
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
        if (getComments() != null) {
            _hashCode += getComments().hashCode();
        }
        if (getDispcreatetime() != null) {
            _hashCode += getDispcreatetime().hashCode();
        }
        if (getErrorcode() != null) {
            _hashCode += getErrorcode().hashCode();
        }
        if (getIncident_ID() != null) {
            _hashCode += getIncident_ID().hashCode();
        }
        if (getIncident_status() != null) {
            _hashCode += getIncident_status().hashCode();
        }
        if (getItemType() != null) {
            _hashCode += getItemType().hashCode();
        }
        if (getItems() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getItems());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getItems(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getPassengers() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPassengers());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPassengers(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getPaxClaimchecks() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPaxClaimchecks());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPaxClaimchecks(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getPaxCommunication() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPaxCommunication());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPaxCommunication(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WS_PVIncident.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://paxview.v1_1.ws.nettracer.bagnet.com/xsd", "WS_PVIncident"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("comments");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paxview.v1_1.ws.nettracer.bagnet.com/xsd", "comments"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dispcreatetime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paxview.v1_1.ws.nettracer.bagnet.com/xsd", "dispcreatetime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("errorcode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paxview.v1_1.ws.nettracer.bagnet.com/xsd", "errorcode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("incident_ID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paxview.v1_1.ws.nettracer.bagnet.com/xsd", "incident_ID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("incident_status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paxview.v1_1.ws.nettracer.bagnet.com/xsd", "incident_status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("itemType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paxview.v1_1.ws.nettracer.bagnet.com/xsd", "itemType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("items");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paxview.v1_1.ws.nettracer.bagnet.com/xsd", "items"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://paxview.v1_1.ws.nettracer.bagnet.com/xsd", "WS_PVItem"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("passengers");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paxview.v1_1.ws.nettracer.bagnet.com/xsd", "passengers"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://paxview.v1_1.ws.nettracer.bagnet.com/xsd", "WS_PVPassenger"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("paxClaimchecks");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paxview.v1_1.ws.nettracer.bagnet.com/xsd", "paxClaimchecks"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://paxview.v1_1.ws.nettracer.bagnet.com/xsd", "WS_PVClaimChecks"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("paxCommunication");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paxview.v1_1.ws.nettracer.bagnet.com/xsd", "paxCommunication"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://paxview.v1_1.ws.nettracer.bagnet.com/xsd", "WS_PVPaxCommunication"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
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
