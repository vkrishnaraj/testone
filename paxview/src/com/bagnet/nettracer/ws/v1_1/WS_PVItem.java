/**
 * WS_PVItem.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.bagnet.nettracer.ws.v1_1;

public class WS_PVItem  implements java.io.Serializable {
    private java.lang.String bagstatus;

    private java.lang.String claimchecknum;

    private java.lang.String color;

    private java.lang.String deliveryAddress;

    private java.lang.String type;

    public WS_PVItem() {
    }

    public WS_PVItem(
           java.lang.String bagstatus,
           java.lang.String claimchecknum,
           java.lang.String color,
           java.lang.String deliveryAddress,
           java.lang.String type) {
           this.bagstatus = bagstatus;
           this.claimchecknum = claimchecknum;
           this.color = color;
           this.deliveryAddress = deliveryAddress;
           this.type = type;
    }


    /**
     * Gets the bagstatus value for this WS_PVItem.
     * 
     * @return bagstatus
     */
    public java.lang.String getBagstatus() {
        return bagstatus;
    }


    /**
     * Sets the bagstatus value for this WS_PVItem.
     * 
     * @param bagstatus
     */
    public void setBagstatus(java.lang.String bagstatus) {
        this.bagstatus = bagstatus;
    }


    /**
     * Gets the claimchecknum value for this WS_PVItem.
     * 
     * @return claimchecknum
     */
    public java.lang.String getClaimchecknum() {
        return claimchecknum;
    }


    /**
     * Sets the claimchecknum value for this WS_PVItem.
     * 
     * @param claimchecknum
     */
    public void setClaimchecknum(java.lang.String claimchecknum) {
        this.claimchecknum = claimchecknum;
    }


    /**
     * Gets the color value for this WS_PVItem.
     * 
     * @return color
     */
    public java.lang.String getColor() {
        return color;
    }


    /**
     * Sets the color value for this WS_PVItem.
     * 
     * @param color
     */
    public void setColor(java.lang.String color) {
        this.color = color;
    }


    /**
     * Gets the deliveryAddress value for this WS_PVItem.
     * 
     * @return deliveryAddress
     */
    public java.lang.String getDeliveryAddress() {
        return deliveryAddress;
    }


    /**
     * Sets the deliveryAddress value for this WS_PVItem.
     * 
     * @param deliveryAddress
     */
    public void setDeliveryAddress(java.lang.String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }


    /**
     * Gets the type value for this WS_PVItem.
     * 
     * @return type
     */
    public java.lang.String getType() {
        return type;
    }


    /**
     * Sets the type value for this WS_PVItem.
     * 
     * @param type
     */
    public void setType(java.lang.String type) {
        this.type = type;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WS_PVItem)) return false;
        WS_PVItem other = (WS_PVItem) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.bagstatus==null && other.getBagstatus()==null) || 
             (this.bagstatus!=null &&
              this.bagstatus.equals(other.getBagstatus()))) &&
            ((this.claimchecknum==null && other.getClaimchecknum()==null) || 
             (this.claimchecknum!=null &&
              this.claimchecknum.equals(other.getClaimchecknum()))) &&
            ((this.color==null && other.getColor()==null) || 
             (this.color!=null &&
              this.color.equals(other.getColor()))) &&
            ((this.deliveryAddress==null && other.getDeliveryAddress()==null) || 
             (this.deliveryAddress!=null &&
              this.deliveryAddress.equals(other.getDeliveryAddress()))) &&
            ((this.type==null && other.getType()==null) || 
             (this.type!=null &&
              this.type.equals(other.getType())));
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
        if (getBagstatus() != null) {
            _hashCode += getBagstatus().hashCode();
        }
        if (getClaimchecknum() != null) {
            _hashCode += getClaimchecknum().hashCode();
        }
        if (getColor() != null) {
            _hashCode += getColor().hashCode();
        }
        if (getDeliveryAddress() != null) {
            _hashCode += getDeliveryAddress().hashCode();
        }
        if (getType() != null) {
            _hashCode += getType().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WS_PVItem.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://paxview.v1_1.ws.nettracer.bagnet.com/xsd", "WS_PVItem"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bagstatus");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paxview.v1_1.ws.nettracer.bagnet.com/xsd", "bagstatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("claimchecknum");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paxview.v1_1.ws.nettracer.bagnet.com/xsd", "claimchecknum"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("color");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paxview.v1_1.ws.nettracer.bagnet.com/xsd", "color"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deliveryAddress");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paxview.v1_1.ws.nettracer.bagnet.com/xsd", "deliveryAddress"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("type");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paxview.v1_1.ws.nettracer.bagnet.com/xsd", "type"));
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
