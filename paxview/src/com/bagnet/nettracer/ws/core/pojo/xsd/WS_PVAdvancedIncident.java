/**
 * WS_PVAdvancedIncident.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.bagnet.nettracer.ws.core.pojo.xsd;

public class WS_PVAdvancedIncident  implements java.io.Serializable {
    private java.lang.String comments;

    private java.lang.String companycode_id;

    private java.lang.String dispcreatetime;

    private java.lang.String errorcode;

    private java.lang.String incident_ID;

    private java.lang.String incident_status;

    private com.bagnet.nettracer.ws.core.pojo.xsd.WS_PVItem[] items;

    private com.bagnet.nettracer.ws.core.pojo.xsd.WS_PVPassenger[] passengers;

    public WS_PVAdvancedIncident() {
    }

    public WS_PVAdvancedIncident(
           java.lang.String comments,
           java.lang.String companycode_id,
           java.lang.String dispcreatetime,
           java.lang.String errorcode,
           java.lang.String incident_ID,
           java.lang.String incident_status,
           com.bagnet.nettracer.ws.core.pojo.xsd.WS_PVItem[] items,
           com.bagnet.nettracer.ws.core.pojo.xsd.WS_PVPassenger[] passengers) {
           this.comments = comments;
           this.companycode_id = companycode_id;
           this.dispcreatetime = dispcreatetime;
           this.errorcode = errorcode;
           this.incident_ID = incident_ID;
           this.incident_status = incident_status;
           this.items = items;
           this.passengers = passengers;
    }


    /**
     * Gets the comments value for this WS_PVAdvancedIncident.
     * 
     * @return comments
     */
    public java.lang.String getComments() {
        return comments;
    }


    /**
     * Sets the comments value for this WS_PVAdvancedIncident.
     * 
     * @param comments
     */
    public void setComments(java.lang.String comments) {
        this.comments = comments;
    }


    /**
     * Gets the companycode_id value for this WS_PVAdvancedIncident.
     * 
     * @return companycode_id
     */
    public java.lang.String getCompanycode_id() {
        return companycode_id;
    }


    /**
     * Sets the companycode_id value for this WS_PVAdvancedIncident.
     * 
     * @param companycode_id
     */
    public void setCompanycode_id(java.lang.String companycode_id) {
        this.companycode_id = companycode_id;
    }


    /**
     * Gets the dispcreatetime value for this WS_PVAdvancedIncident.
     * 
     * @return dispcreatetime
     */
    public java.lang.String getDispcreatetime() {
        return dispcreatetime;
    }


    /**
     * Sets the dispcreatetime value for this WS_PVAdvancedIncident.
     * 
     * @param dispcreatetime
     */
    public void setDispcreatetime(java.lang.String dispcreatetime) {
        this.dispcreatetime = dispcreatetime;
    }


    /**
     * Gets the errorcode value for this WS_PVAdvancedIncident.
     * 
     * @return errorcode
     */
    public java.lang.String getErrorcode() {
        return errorcode;
    }


    /**
     * Sets the errorcode value for this WS_PVAdvancedIncident.
     * 
     * @param errorcode
     */
    public void setErrorcode(java.lang.String errorcode) {
        this.errorcode = errorcode;
    }


    /**
     * Gets the incident_ID value for this WS_PVAdvancedIncident.
     * 
     * @return incident_ID
     */
    public java.lang.String getIncident_ID() {
        return incident_ID;
    }


    /**
     * Sets the incident_ID value for this WS_PVAdvancedIncident.
     * 
     * @param incident_ID
     */
    public void setIncident_ID(java.lang.String incident_ID) {
        this.incident_ID = incident_ID;
    }


    /**
     * Gets the incident_status value for this WS_PVAdvancedIncident.
     * 
     * @return incident_status
     */
    public java.lang.String getIncident_status() {
        return incident_status;
    }


    /**
     * Sets the incident_status value for this WS_PVAdvancedIncident.
     * 
     * @param incident_status
     */
    public void setIncident_status(java.lang.String incident_status) {
        this.incident_status = incident_status;
    }


    /**
     * Gets the items value for this WS_PVAdvancedIncident.
     * 
     * @return items
     */
    public com.bagnet.nettracer.ws.core.pojo.xsd.WS_PVItem[] getItems() {
        return items;
    }


    /**
     * Sets the items value for this WS_PVAdvancedIncident.
     * 
     * @param items
     */
    public void setItems(com.bagnet.nettracer.ws.core.pojo.xsd.WS_PVItem[] items) {
        this.items = items;
    }

    public com.bagnet.nettracer.ws.core.pojo.xsd.WS_PVItem getItems(int i) {
        return this.items[i];
    }

    public void setItems(int i, com.bagnet.nettracer.ws.core.pojo.xsd.WS_PVItem _value) {
        this.items[i] = _value;
    }


    /**
     * Gets the passengers value for this WS_PVAdvancedIncident.
     * 
     * @return passengers
     */
    public com.bagnet.nettracer.ws.core.pojo.xsd.WS_PVPassenger[] getPassengers() {
        return passengers;
    }


    /**
     * Sets the passengers value for this WS_PVAdvancedIncident.
     * 
     * @param passengers
     */
    public void setPassengers(com.bagnet.nettracer.ws.core.pojo.xsd.WS_PVPassenger[] passengers) {
        this.passengers = passengers;
    }

    public com.bagnet.nettracer.ws.core.pojo.xsd.WS_PVPassenger getPassengers(int i) {
        return this.passengers[i];
    }

    public void setPassengers(int i, com.bagnet.nettracer.ws.core.pojo.xsd.WS_PVPassenger _value) {
        this.passengers[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WS_PVAdvancedIncident)) return false;
        WS_PVAdvancedIncident other = (WS_PVAdvancedIncident) obj;
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
            ((this.companycode_id==null && other.getCompanycode_id()==null) || 
             (this.companycode_id!=null &&
              this.companycode_id.equals(other.getCompanycode_id()))) &&
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
            ((this.items==null && other.getItems()==null) || 
             (this.items!=null &&
              java.util.Arrays.equals(this.items, other.getItems()))) &&
            ((this.passengers==null && other.getPassengers()==null) || 
             (this.passengers!=null &&
              java.util.Arrays.equals(this.passengers, other.getPassengers())));
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
        if (getCompanycode_id() != null) {
            _hashCode += getCompanycode_id().hashCode();
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
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WS_PVAdvancedIncident.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "WS_PVAdvancedIncident"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("comments");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "comments"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("companycode_id");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "companycode_id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dispcreatetime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "dispcreatetime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("errorcode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "errorcode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("incident_ID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "incident_ID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("incident_status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "incident_status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("items");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "items"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "WS_PVItem"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("passengers");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "passengers"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "WS_PVPassenger"));
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
