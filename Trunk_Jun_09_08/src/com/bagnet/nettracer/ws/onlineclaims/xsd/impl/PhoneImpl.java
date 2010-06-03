/*
 * XML Type:  Phone
 * Namespace: http://onlineclaims.ws.nettracer.bagnet.com/xsd
 * Java type: com.bagnet.nettracer.ws.onlineclaims.xsd.Phone
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.onlineclaims.xsd.impl;

/**
 * An XML Phone(@http://onlineclaims.ws.nettracer.bagnet.com/xsd).
 *
 * This is a complex type.
 */
public class PhoneImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
    implements com.bagnet.nettracer.ws.onlineclaims.xsd.Phone {
    private static final javax.xml.namespace.QName PHONENUMBER$0 = new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd",
            "phoneNumber");
    private static final javax.xml.namespace.QName PHONETYPE$2 = new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd",
            "phoneType");

    public PhoneImpl(org.apache.xmlbeans.SchemaType sType) {
        super(sType);
    }

    /**
     * Gets the "phoneNumber" element
     */
    public java.lang.String getPhoneNumber() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(PHONENUMBER$0,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "phoneNumber" element
     */
    public org.apache.xmlbeans.XmlString xgetPhoneNumber() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(PHONENUMBER$0,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "phoneNumber" element
     */
    public boolean isNilPhoneNumber() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(PHONENUMBER$0,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "phoneNumber" element
     */
    public boolean isSetPhoneNumber() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(PHONENUMBER$0) != 0;
        }
    }

    /**
     * Sets the "phoneNumber" element
     */
    public void setPhoneNumber(java.lang.String phoneNumber) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(PHONENUMBER$0,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(PHONENUMBER$0);
            }

            target.setStringValue(phoneNumber);
        }
    }

    /**
     * Sets (as xml) the "phoneNumber" element
     */
    public void xsetPhoneNumber(org.apache.xmlbeans.XmlString phoneNumber) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(PHONENUMBER$0,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(PHONENUMBER$0);
            }

            target.set(phoneNumber);
        }
    }

    /**
     * Nils the "phoneNumber" element
     */
    public void setNilPhoneNumber() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(PHONENUMBER$0,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(PHONENUMBER$0);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "phoneNumber" element
     */
    public void unsetPhoneNumber() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(PHONENUMBER$0, 0);
        }
    }

    /**
     * Gets the "phoneType" element
     */
    public java.lang.String getPhoneType() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(PHONETYPE$2,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "phoneType" element
     */
    public org.apache.xmlbeans.XmlString xgetPhoneType() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(PHONETYPE$2,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "phoneType" element
     */
    public boolean isNilPhoneType() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(PHONETYPE$2,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "phoneType" element
     */
    public boolean isSetPhoneType() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(PHONETYPE$2) != 0;
        }
    }

    /**
     * Sets the "phoneType" element
     */
    public void setPhoneType(java.lang.String phoneType) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(PHONETYPE$2,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(PHONETYPE$2);
            }

            target.setStringValue(phoneType);
        }
    }

    /**
     * Sets (as xml) the "phoneType" element
     */
    public void xsetPhoneType(org.apache.xmlbeans.XmlString phoneType) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(PHONETYPE$2,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(PHONETYPE$2);
            }

            target.set(phoneType);
        }
    }

    /**
     * Nils the "phoneType" element
     */
    public void setNilPhoneType() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(PHONETYPE$2,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(PHONETYPE$2);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "phoneType" element
     */
    public void unsetPhoneType() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(PHONETYPE$2, 0);
        }
    }
}
