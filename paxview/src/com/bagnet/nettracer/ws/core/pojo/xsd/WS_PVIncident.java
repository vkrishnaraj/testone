/**
 * WS_PVIncident.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.bagnet.nettracer.ws.core.pojo.xsd;

public class WS_PVIncident  implements java.io.Serializable {
    private java.lang.String companycode_id;

    private java.lang.String dispcreatetime;

    private java.lang.String email;

    private java.lang.String errorcode;

    private java.lang.String firstname;

    private java.lang.String homephone;

    private java.lang.String hotel;

    private java.lang.String incident_ID;

    private java.lang.String incident_status;

    private com.bagnet.nettracer.ws.core.pojo.xsd.WS_PVItem[] items;

    private java.lang.String lastname;

    private java.lang.String middlename;

    private java.lang.String mobile;

    private java.lang.String workphone;

    public WS_PVIncident() {
    }

    public WS_PVIncident(
           java.lang.String companycode_id,
           java.lang.String dispcreatetime,
           java.lang.String email,
           java.lang.String errorcode,
           java.lang.String firstname,
           java.lang.String homephone,
           java.lang.String hotel,
           java.lang.String incident_ID,
           java.lang.String incident_status,
           com.bagnet.nettracer.ws.core.pojo.xsd.WS_PVItem[] items,
           java.lang.String lastname,
           java.lang.String middlename,
           java.lang.String mobile,
           java.lang.String workphone) {
           this.companycode_id = companycode_id;
           this.dispcreatetime = dispcreatetime;
           this.email = email;
           this.errorcode = errorcode;
           this.firstname = firstname;
           this.homephone = homephone;
           this.hotel = hotel;
           this.incident_ID = incident_ID;
           this.incident_status = incident_status;
           this.items = items;
           this.lastname = lastname;
           this.middlename = middlename;
           this.mobile = mobile;
           this.workphone = workphone;
    }


    /**
     * Gets the companycode_id value for this WS_PVIncident.
     * 
     * @return companycode_id
     */
    public java.lang.String getCompanycode_id() {
        return companycode_id;
    }


    /**
     * Sets the companycode_id value for this WS_PVIncident.
     * 
     * @param companycode_id
     */
    public void setCompanycode_id(java.lang.String companycode_id) {
        this.companycode_id = companycode_id;
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
     * Gets the email value for this WS_PVIncident.
     * 
     * @return email
     */
    public java.lang.String getEmail() {
        return email;
    }


    /**
     * Sets the email value for this WS_PVIncident.
     * 
     * @param email
     */
    public void setEmail(java.lang.String email) {
        this.email = email;
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
     * Gets the firstname value for this WS_PVIncident.
     * 
     * @return firstname
     */
    public java.lang.String getFirstname() {
        return firstname;
    }


    /**
     * Sets the firstname value for this WS_PVIncident.
     * 
     * @param firstname
     */
    public void setFirstname(java.lang.String firstname) {
        this.firstname = firstname;
    }


    /**
     * Gets the homephone value for this WS_PVIncident.
     * 
     * @return homephone
     */
    public java.lang.String getHomephone() {
        return homephone;
    }


    /**
     * Sets the homephone value for this WS_PVIncident.
     * 
     * @param homephone
     */
    public void setHomephone(java.lang.String homephone) {
        this.homephone = homephone;
    }


    /**
     * Gets the hotel value for this WS_PVIncident.
     * 
     * @return hotel
     */
    public java.lang.String getHotel() {
        return hotel;
    }


    /**
     * Sets the hotel value for this WS_PVIncident.
     * 
     * @param hotel
     */
    public void setHotel(java.lang.String hotel) {
        this.hotel = hotel;
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
     * Gets the items value for this WS_PVIncident.
     * 
     * @return items
     */
    public com.bagnet.nettracer.ws.core.pojo.xsd.WS_PVItem[] getItems() {
        return items;
    }


    /**
     * Sets the items value for this WS_PVIncident.
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
     * Gets the lastname value for this WS_PVIncident.
     * 
     * @return lastname
     */
    public java.lang.String getLastname() {
        return lastname;
    }


    /**
     * Sets the lastname value for this WS_PVIncident.
     * 
     * @param lastname
     */
    public void setLastname(java.lang.String lastname) {
        this.lastname = lastname;
    }


    /**
     * Gets the middlename value for this WS_PVIncident.
     * 
     * @return middlename
     */
    public java.lang.String getMiddlename() {
        return middlename;
    }


    /**
     * Sets the middlename value for this WS_PVIncident.
     * 
     * @param middlename
     */
    public void setMiddlename(java.lang.String middlename) {
        this.middlename = middlename;
    }


    /**
     * Gets the mobile value for this WS_PVIncident.
     * 
     * @return mobile
     */
    public java.lang.String getMobile() {
        return mobile;
    }


    /**
     * Sets the mobile value for this WS_PVIncident.
     * 
     * @param mobile
     */
    public void setMobile(java.lang.String mobile) {
        this.mobile = mobile;
    }


    /**
     * Gets the workphone value for this WS_PVIncident.
     * 
     * @return workphone
     */
    public java.lang.String getWorkphone() {
        return workphone;
    }


    /**
     * Sets the workphone value for this WS_PVIncident.
     * 
     * @param workphone
     */
    public void setWorkphone(java.lang.String workphone) {
        this.workphone = workphone;
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
            ((this.companycode_id==null && other.getCompanycode_id()==null) || 
             (this.companycode_id!=null &&
              this.companycode_id.equals(other.getCompanycode_id()))) &&
            ((this.dispcreatetime==null && other.getDispcreatetime()==null) || 
             (this.dispcreatetime!=null &&
              this.dispcreatetime.equals(other.getDispcreatetime()))) &&
            ((this.email==null && other.getEmail()==null) || 
             (this.email!=null &&
              this.email.equals(other.getEmail()))) &&
            ((this.errorcode==null && other.getErrorcode()==null) || 
             (this.errorcode!=null &&
              this.errorcode.equals(other.getErrorcode()))) &&
            ((this.firstname==null && other.getFirstname()==null) || 
             (this.firstname!=null &&
              this.firstname.equals(other.getFirstname()))) &&
            ((this.homephone==null && other.getHomephone()==null) || 
             (this.homephone!=null &&
              this.homephone.equals(other.getHomephone()))) &&
            ((this.hotel==null && other.getHotel()==null) || 
             (this.hotel!=null &&
              this.hotel.equals(other.getHotel()))) &&
            ((this.incident_ID==null && other.getIncident_ID()==null) || 
             (this.incident_ID!=null &&
              this.incident_ID.equals(other.getIncident_ID()))) &&
            ((this.incident_status==null && other.getIncident_status()==null) || 
             (this.incident_status!=null &&
              this.incident_status.equals(other.getIncident_status()))) &&
            ((this.items==null && other.getItems()==null) || 
             (this.items!=null &&
              java.util.Arrays.equals(this.items, other.getItems()))) &&
            ((this.lastname==null && other.getLastname()==null) || 
             (this.lastname!=null &&
              this.lastname.equals(other.getLastname()))) &&
            ((this.middlename==null && other.getMiddlename()==null) || 
             (this.middlename!=null &&
              this.middlename.equals(other.getMiddlename()))) &&
            ((this.mobile==null && other.getMobile()==null) || 
             (this.mobile!=null &&
              this.mobile.equals(other.getMobile()))) &&
            ((this.workphone==null && other.getWorkphone()==null) || 
             (this.workphone!=null &&
              this.workphone.equals(other.getWorkphone())));
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
        if (getCompanycode_id() != null) {
            _hashCode += getCompanycode_id().hashCode();
        }
        if (getDispcreatetime() != null) {
            _hashCode += getDispcreatetime().hashCode();
        }
        if (getEmail() != null) {
            _hashCode += getEmail().hashCode();
        }
        if (getErrorcode() != null) {
            _hashCode += getErrorcode().hashCode();
        }
        if (getFirstname() != null) {
            _hashCode += getFirstname().hashCode();
        }
        if (getHomephone() != null) {
            _hashCode += getHomephone().hashCode();
        }
        if (getHotel() != null) {
            _hashCode += getHotel().hashCode();
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
        if (getLastname() != null) {
            _hashCode += getLastname().hashCode();
        }
        if (getMiddlename() != null) {
            _hashCode += getMiddlename().hashCode();
        }
        if (getMobile() != null) {
            _hashCode += getMobile().hashCode();
        }
        if (getWorkphone() != null) {
            _hashCode += getWorkphone().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WS_PVIncident.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "WS_PVIncident"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
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
        elemField.setFieldName("email");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "email"));
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
        elemField.setFieldName("firstname");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "firstname"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("homephone");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "homephone"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hotel");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "hotel"));
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
        elemField.setFieldName("lastname");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "lastname"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("middlename");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "middlename"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mobile");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "mobile"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("workphone");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd", "workphone"));
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
