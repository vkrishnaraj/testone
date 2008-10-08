/**
 * WS_PVItem.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.bagnet.nettracer.ws.core.pojo.xsd;

public class WS_PVItem  implements java.io.Serializable {
    private java.lang.String address1;

    private java.lang.String address2;

    private java.lang.String bagstatus;

    private java.lang.String city;

    private java.lang.String claimchecknum;

    private java.lang.String state_ID;

    private java.lang.String zip;

    public WS_PVItem() {
    }

    public WS_PVItem(
           java.lang.String address1,
           java.lang.String address2,
           java.lang.String bagstatus,
           java.lang.String city,
           java.lang.String claimchecknum,
           java.lang.String state_ID,
           java.lang.String zip) {
           this.address1 = address1;
           this.address2 = address2;
           this.bagstatus = bagstatus;
           this.city = city;
           this.claimchecknum = claimchecknum;
           this.state_ID = state_ID;
           this.zip = zip;
    }


    /**
     * Gets the address1 value for this WS_PVItem.
     * 
     * @return address1
     */
    public java.lang.String getAddress1() {
        return address1;
    }


    /**
     * Sets the address1 value for this WS_PVItem.
     * 
     * @param address1
     */
    public void setAddress1(java.lang.String address1) {
        this.address1 = address1;
    }


    /**
     * Gets the address2 value for this WS_PVItem.
     * 
     * @return address2
     */
    public java.lang.String getAddress2() {
        return address2;
    }


    /**
     * Sets the address2 value for this WS_PVItem.
     * 
     * @param address2
     */
    public void setAddress2(java.lang.String address2) {
        this.address2 = address2;
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
     * Gets the city value for this WS_PVItem.
     * 
     * @return city
     */
    public java.lang.String getCity() {
        return city;
    }


    /**
     * Sets the city value for this WS_PVItem.
     * 
     * @param city
     */
    public void setCity(java.lang.String city) {
        this.city = city;
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
     * Gets the state_ID value for this WS_PVItem.
     * 
     * @return state_ID
     */
    public java.lang.String getState_ID() {
        return state_ID;
    }


    /**
     * Sets the state_ID value for this WS_PVItem.
     * 
     * @param state_ID
     */
    public void setState_ID(java.lang.String state_ID) {
        this.state_ID = state_ID;
    }


    /**
     * Gets the zip value for this WS_PVItem.
     * 
     * @return zip
     */
    public java.lang.String getZip() {
        return zip;
    }


    /**
     * Sets the zip value for this WS_PVItem.
     * 
     * @param zip
     */
    public void setZip(java.lang.String zip) {
        this.zip = zip;
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
            ((this.address1==null && other.getAddress1()==null) || 
             (this.address1!=null &&
              this.address1.equals(other.getAddress1()))) &&
            ((this.address2==null && other.getAddress2()==null) || 
             (this.address2!=null &&
              this.address2.equals(other.getAddress2()))) &&
            ((this.bagstatus==null && other.getBagstatus()==null) || 
             (this.bagstatus!=null &&
              this.bagstatus.equals(other.getBagstatus()))) &&
            ((this.city==null && other.getCity()==null) || 
             (this.city!=null &&
              this.city.equals(other.getCity()))) &&
            ((this.claimchecknum==null && other.getClaimchecknum()==null) || 
             (this.claimchecknum!=null &&
              this.claimchecknum.equals(other.getClaimchecknum()))) &&
            ((this.state_ID==null && other.getState_ID()==null) || 
             (this.state_ID!=null &&
              this.state_ID.equals(other.getState_ID()))) &&
            ((this.zip==null && other.getZip()==null) || 
             (this.zip!=null &&
              this.zip.equals(other.getZip())));
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
        if (getAddress1() != null) {
            _hashCode += getAddress1().hashCode();
        }
        if (getAddress2() != null) {
            _hashCode += getAddress2().hashCode();
        }
        if (getBagstatus() != null) {
            _hashCode += getBagstatus().hashCode();
        }
        if (getCity() != null) {
            _hashCode += getCity().hashCode();
        }
        if (getClaimchecknum() != null) {
            _hashCode += getClaimchecknum().hashCode();
        }
        if (getState_ID() != null) {
            _hashCode += getState_ID().hashCode();
        }
        if (getZip() != null) {
            _hashCode += getZip().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WS_PVItem.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "WS_PVItem"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("address1");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "address1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("address2");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "address2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bagstatus");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "bagstatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("city");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "city"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("claimchecknum");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "claimchecknum"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("state_ID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "state_ID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("zip");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "zip"));
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
