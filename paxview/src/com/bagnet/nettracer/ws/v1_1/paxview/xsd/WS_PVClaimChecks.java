/**
 * WS_PVClaimChecks.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.bagnet.nettracer.ws.v1_1.paxview.xsd;

public class WS_PVClaimChecks  implements java.io.Serializable {
    private java.lang.String claimcheck;

    public WS_PVClaimChecks() {
    }

    public WS_PVClaimChecks(
           java.lang.String claimcheck) {
           this.claimcheck = claimcheck;
    }


    /**
     * Gets the claimcheck value for this WS_PVClaimChecks.
     * 
     * @return claimcheck
     */
    public java.lang.String getClaimcheck() {
        return claimcheck;
    }


    /**
     * Sets the claimcheck value for this WS_PVClaimChecks.
     * 
     * @param claimcheck
     */
    public void setClaimcheck(java.lang.String claimcheck) {
        this.claimcheck = claimcheck;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WS_PVClaimChecks)) return false;
        WS_PVClaimChecks other = (WS_PVClaimChecks) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.claimcheck==null && other.getClaimcheck()==null) || 
             (this.claimcheck!=null &&
              this.claimcheck.equals(other.getClaimcheck())));
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
        if (getClaimcheck() != null) {
            _hashCode += getClaimcheck().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WS_PVClaimChecks.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://paxview.v1_1.ws.nettracer.bagnet.com/xsd", "WS_PVClaimChecks"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("claimcheck");
        elemField.setXmlName(new javax.xml.namespace.QName("http://paxview.v1_1.ws.nettracer.bagnet.com/xsd", "claimcheck"));
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
