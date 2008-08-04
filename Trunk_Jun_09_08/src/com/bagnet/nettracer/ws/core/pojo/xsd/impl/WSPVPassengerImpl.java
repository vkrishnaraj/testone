/*
 * XML Type:  WS_PVPassenger
 * Namespace: http://pojo.core.ws.nettracer.bagnet.com/xsd
 * Java type: com.bagnet.nettracer.ws.core.pojo.xsd.WSPVPassenger
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.core.pojo.xsd.impl;

/**
 * An XML WS_PVPassenger(@http://pojo.core.ws.nettracer.bagnet.com/xsd).
 *
 * This is a complex type.
 */
public class WSPVPassengerImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
    implements com.bagnet.nettracer.ws.core.pojo.xsd.WSPVPassenger {
    private static final javax.xml.namespace.QName EMAIL$0 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "email");
    private static final javax.xml.namespace.QName FIRSTNAME$2 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "firstname");
    private static final javax.xml.namespace.QName HOMEPHONE$4 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "homephone");
    private static final javax.xml.namespace.QName HOTEL$6 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "hotel");
    private static final javax.xml.namespace.QName LASTNAME$8 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "lastname");
    private static final javax.xml.namespace.QName MIDDLENAME$10 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "middlename");
    private static final javax.xml.namespace.QName MOBILE$12 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "mobile");
    private static final javax.xml.namespace.QName WORKPHONE$14 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "workphone");

    public WSPVPassengerImpl(org.apache.xmlbeans.SchemaType sType) {
        super(sType);
    }

    /**
     * Gets the "email" element
     */
    public java.lang.String getEmail() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(EMAIL$0,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "email" element
     */
    public org.apache.xmlbeans.XmlString xgetEmail() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(EMAIL$0,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "email" element
     */
    public boolean isNilEmail() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(EMAIL$0,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "email" element
     */
    public boolean isSetEmail() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(EMAIL$0) != 0;
        }
    }

    /**
     * Sets the "email" element
     */
    public void setEmail(java.lang.String email) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(EMAIL$0,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(EMAIL$0);
            }

            target.setStringValue(email);
        }
    }

    /**
     * Sets (as xml) the "email" element
     */
    public void xsetEmail(org.apache.xmlbeans.XmlString email) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(EMAIL$0,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(EMAIL$0);
            }

            target.set(email);
        }
    }

    /**
     * Nils the "email" element
     */
    public void setNilEmail() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(EMAIL$0,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(EMAIL$0);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "email" element
     */
    public void unsetEmail() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(EMAIL$0, 0);
        }
    }

    /**
     * Gets the "firstname" element
     */
    public java.lang.String getFirstname() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(FIRSTNAME$2,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "firstname" element
     */
    public org.apache.xmlbeans.XmlString xgetFirstname() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(FIRSTNAME$2,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "firstname" element
     */
    public boolean isNilFirstname() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(FIRSTNAME$2,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "firstname" element
     */
    public boolean isSetFirstname() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(FIRSTNAME$2) != 0;
        }
    }

    /**
     * Sets the "firstname" element
     */
    public void setFirstname(java.lang.String firstname) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(FIRSTNAME$2,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(FIRSTNAME$2);
            }

            target.setStringValue(firstname);
        }
    }

    /**
     * Sets (as xml) the "firstname" element
     */
    public void xsetFirstname(org.apache.xmlbeans.XmlString firstname) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(FIRSTNAME$2,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(FIRSTNAME$2);
            }

            target.set(firstname);
        }
    }

    /**
     * Nils the "firstname" element
     */
    public void setNilFirstname() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(FIRSTNAME$2,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(FIRSTNAME$2);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "firstname" element
     */
    public void unsetFirstname() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(FIRSTNAME$2, 0);
        }
    }

    /**
     * Gets the "homephone" element
     */
    public java.lang.String getHomephone() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(HOMEPHONE$4,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "homephone" element
     */
    public org.apache.xmlbeans.XmlString xgetHomephone() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(HOMEPHONE$4,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "homephone" element
     */
    public boolean isNilHomephone() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(HOMEPHONE$4,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "homephone" element
     */
    public boolean isSetHomephone() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(HOMEPHONE$4) != 0;
        }
    }

    /**
     * Sets the "homephone" element
     */
    public void setHomephone(java.lang.String homephone) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(HOMEPHONE$4,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(HOMEPHONE$4);
            }

            target.setStringValue(homephone);
        }
    }

    /**
     * Sets (as xml) the "homephone" element
     */
    public void xsetHomephone(org.apache.xmlbeans.XmlString homephone) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(HOMEPHONE$4,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(HOMEPHONE$4);
            }

            target.set(homephone);
        }
    }

    /**
     * Nils the "homephone" element
     */
    public void setNilHomephone() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(HOMEPHONE$4,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(HOMEPHONE$4);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "homephone" element
     */
    public void unsetHomephone() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(HOMEPHONE$4, 0);
        }
    }

    /**
     * Gets the "hotel" element
     */
    public java.lang.String getHotel() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(HOTEL$6,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "hotel" element
     */
    public org.apache.xmlbeans.XmlString xgetHotel() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(HOTEL$6,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "hotel" element
     */
    public boolean isNilHotel() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(HOTEL$6,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "hotel" element
     */
    public boolean isSetHotel() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(HOTEL$6) != 0;
        }
    }

    /**
     * Sets the "hotel" element
     */
    public void setHotel(java.lang.String hotel) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(HOTEL$6,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(HOTEL$6);
            }

            target.setStringValue(hotel);
        }
    }

    /**
     * Sets (as xml) the "hotel" element
     */
    public void xsetHotel(org.apache.xmlbeans.XmlString hotel) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(HOTEL$6,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(HOTEL$6);
            }

            target.set(hotel);
        }
    }

    /**
     * Nils the "hotel" element
     */
    public void setNilHotel() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(HOTEL$6,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(HOTEL$6);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "hotel" element
     */
    public void unsetHotel() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(HOTEL$6, 0);
        }
    }

    /**
     * Gets the "lastname" element
     */
    public java.lang.String getLastname() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(LASTNAME$8,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "lastname" element
     */
    public org.apache.xmlbeans.XmlString xgetLastname() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(LASTNAME$8,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "lastname" element
     */
    public boolean isNilLastname() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(LASTNAME$8,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "lastname" element
     */
    public boolean isSetLastname() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(LASTNAME$8) != 0;
        }
    }

    /**
     * Sets the "lastname" element
     */
    public void setLastname(java.lang.String lastname) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(LASTNAME$8,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(LASTNAME$8);
            }

            target.setStringValue(lastname);
        }
    }

    /**
     * Sets (as xml) the "lastname" element
     */
    public void xsetLastname(org.apache.xmlbeans.XmlString lastname) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(LASTNAME$8,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(LASTNAME$8);
            }

            target.set(lastname);
        }
    }

    /**
     * Nils the "lastname" element
     */
    public void setNilLastname() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(LASTNAME$8,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(LASTNAME$8);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "lastname" element
     */
    public void unsetLastname() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(LASTNAME$8, 0);
        }
    }

    /**
     * Gets the "middlename" element
     */
    public java.lang.String getMiddlename() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(MIDDLENAME$10,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "middlename" element
     */
    public org.apache.xmlbeans.XmlString xgetMiddlename() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(MIDDLENAME$10,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "middlename" element
     */
    public boolean isNilMiddlename() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(MIDDLENAME$10,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "middlename" element
     */
    public boolean isSetMiddlename() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(MIDDLENAME$10) != 0;
        }
    }

    /**
     * Sets the "middlename" element
     */
    public void setMiddlename(java.lang.String middlename) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(MIDDLENAME$10,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(MIDDLENAME$10);
            }

            target.setStringValue(middlename);
        }
    }

    /**
     * Sets (as xml) the "middlename" element
     */
    public void xsetMiddlename(org.apache.xmlbeans.XmlString middlename) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(MIDDLENAME$10,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(MIDDLENAME$10);
            }

            target.set(middlename);
        }
    }

    /**
     * Nils the "middlename" element
     */
    public void setNilMiddlename() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(MIDDLENAME$10,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(MIDDLENAME$10);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "middlename" element
     */
    public void unsetMiddlename() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(MIDDLENAME$10, 0);
        }
    }

    /**
     * Gets the "mobile" element
     */
    public java.lang.String getMobile() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(MOBILE$12,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "mobile" element
     */
    public org.apache.xmlbeans.XmlString xgetMobile() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(MOBILE$12,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "mobile" element
     */
    public boolean isNilMobile() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(MOBILE$12,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "mobile" element
     */
    public boolean isSetMobile() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(MOBILE$12) != 0;
        }
    }

    /**
     * Sets the "mobile" element
     */
    public void setMobile(java.lang.String mobile) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(MOBILE$12,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(MOBILE$12);
            }

            target.setStringValue(mobile);
        }
    }

    /**
     * Sets (as xml) the "mobile" element
     */
    public void xsetMobile(org.apache.xmlbeans.XmlString mobile) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(MOBILE$12,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(MOBILE$12);
            }

            target.set(mobile);
        }
    }

    /**
     * Nils the "mobile" element
     */
    public void setNilMobile() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(MOBILE$12,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(MOBILE$12);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "mobile" element
     */
    public void unsetMobile() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(MOBILE$12, 0);
        }
    }

    /**
     * Gets the "workphone" element
     */
    public java.lang.String getWorkphone() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(WORKPHONE$14,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "workphone" element
     */
    public org.apache.xmlbeans.XmlString xgetWorkphone() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(WORKPHONE$14,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "workphone" element
     */
    public boolean isNilWorkphone() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(WORKPHONE$14,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "workphone" element
     */
    public boolean isSetWorkphone() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(WORKPHONE$14) != 0;
        }
    }

    /**
     * Sets the "workphone" element
     */
    public void setWorkphone(java.lang.String workphone) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(WORKPHONE$14,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(WORKPHONE$14);
            }

            target.setStringValue(workphone);
        }
    }

    /**
     * Sets (as xml) the "workphone" element
     */
    public void xsetWorkphone(org.apache.xmlbeans.XmlString workphone) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(WORKPHONE$14,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(WORKPHONE$14);
            }

            target.set(workphone);
        }
    }

    /**
     * Nils the "workphone" element
     */
    public void setNilWorkphone() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(WORKPHONE$14,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(WORKPHONE$14);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "workphone" element
     */
    public void unsetWorkphone() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(WORKPHONE$14, 0);
        }
    }
}
