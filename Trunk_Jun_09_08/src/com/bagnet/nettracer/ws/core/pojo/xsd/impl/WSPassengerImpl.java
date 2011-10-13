/*
 * XML Type:  WS_Passenger
 * Namespace: http://pojo.core.ws.nettracer.bagnet.com/xsd
 * Java type: com.bagnet.nettracer.ws.core.pojo.xsd.WSPassenger
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.core.pojo.xsd.impl;

/**
 * An XML WS_Passenger(@http://pojo.core.ws.nettracer.bagnet.com/xsd).
 *
 * This is a complex type.
 */
public class WSPassengerImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
    implements com.bagnet.nettracer.ws.core.pojo.xsd.WSPassenger {
    private static final javax.xml.namespace.QName ADDRESS1$0 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "address1");
    private static final javax.xml.namespace.QName ADDRESS2$2 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "address2");
    private static final javax.xml.namespace.QName ALTPHONE$4 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "altphone");
    private static final javax.xml.namespace.QName CITY$6 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "city");
    private static final javax.xml.namespace.QName COMMONNUM$8 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "commonnum");
    private static final javax.xml.namespace.QName COUNTRYCODEID$10 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "countrycode_ID");
    private static final javax.xml.namespace.QName COUNTRYOFISSUE$12 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "countryofissue");
    private static final javax.xml.namespace.QName DLSTATE$14 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "dlstate");
    private static final javax.xml.namespace.QName DRIVERSLICENSE$16 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "driverslicense");
    private static final javax.xml.namespace.QName EMAIL$18 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "email");
    private static final javax.xml.namespace.QName FIRSTNAME$20 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "firstname");
    private static final javax.xml.namespace.QName HOMEPHONE$22 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "homephone");
    private static final javax.xml.namespace.QName HOTEL$24 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "hotel");
    private static final javax.xml.namespace.QName ISPERMANENT$26 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "is_permanent");
    private static final javax.xml.namespace.QName ISPRIMARY$28 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "isprimary");
    private static final javax.xml.namespace.QName JOBTITLE$30 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "jobtitle");
    private static final javax.xml.namespace.QName LASTNAME$32 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "lastname");
    private static final javax.xml.namespace.QName MEMBERSHIPAIRLINE$34 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "membershipairline");
    private static final javax.xml.namespace.QName MEMBERSHIPNUM$36 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "membershipnum");
    private static final javax.xml.namespace.QName MEMBERSHIPSTATUS$38 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "membershipstatus");
    private static final javax.xml.namespace.QName MIDDLENAME$40 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "middlename");
    private static final javax.xml.namespace.QName MOBILE$42 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "mobile");
    private static final javax.xml.namespace.QName PAGER$44 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "pager");
    private static final javax.xml.namespace.QName PASSENGERID$46 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "passenger_ID");
    private static final javax.xml.namespace.QName PROVINCE$48 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "province");
    private static final javax.xml.namespace.QName SALUTATION$50 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "salutation");
    private static final javax.xml.namespace.QName STATEID$52 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "state_ID");
    private static final javax.xml.namespace.QName VALIDBDATE$54 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "valid_bdate");
    private static final javax.xml.namespace.QName VALIDEDATE$56 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "valid_edate");
    private static final javax.xml.namespace.QName WORKPHONE$58 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "workphone");
    private static final javax.xml.namespace.QName ZIP$60 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "zip");

    public WSPassengerImpl(org.apache.xmlbeans.SchemaType sType) {
        super(sType);
    }

    /**
     * Gets the "address1" element
     */
    public java.lang.String getAddress1() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(ADDRESS1$0,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "address1" element
     */
    public org.apache.xmlbeans.XmlString xgetAddress1() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(ADDRESS1$0,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "address1" element
     */
    public boolean isNilAddress1() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(ADDRESS1$0,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "address1" element
     */
    public boolean isSetAddress1() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(ADDRESS1$0) != 0;
        }
    }

    /**
     * Sets the "address1" element
     */
    public void setAddress1(java.lang.String address1) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(ADDRESS1$0,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(ADDRESS1$0);
            }

            target.setStringValue(address1);
        }
    }

    /**
     * Sets (as xml) the "address1" element
     */
    public void xsetAddress1(org.apache.xmlbeans.XmlString address1) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(ADDRESS1$0,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(ADDRESS1$0);
            }

            target.set(address1);
        }
    }

    /**
     * Nils the "address1" element
     */
    public void setNilAddress1() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(ADDRESS1$0,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(ADDRESS1$0);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "address1" element
     */
    public void unsetAddress1() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(ADDRESS1$0, 0);
        }
    }

    /**
     * Gets the "address2" element
     */
    public java.lang.String getAddress2() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(ADDRESS2$2,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "address2" element
     */
    public org.apache.xmlbeans.XmlString xgetAddress2() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(ADDRESS2$2,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "address2" element
     */
    public boolean isNilAddress2() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(ADDRESS2$2,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "address2" element
     */
    public boolean isSetAddress2() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(ADDRESS2$2) != 0;
        }
    }

    /**
     * Sets the "address2" element
     */
    public void setAddress2(java.lang.String address2) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(ADDRESS2$2,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(ADDRESS2$2);
            }

            target.setStringValue(address2);
        }
    }

    /**
     * Sets (as xml) the "address2" element
     */
    public void xsetAddress2(org.apache.xmlbeans.XmlString address2) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(ADDRESS2$2,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(ADDRESS2$2);
            }

            target.set(address2);
        }
    }

    /**
     * Nils the "address2" element
     */
    public void setNilAddress2() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(ADDRESS2$2,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(ADDRESS2$2);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "address2" element
     */
    public void unsetAddress2() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(ADDRESS2$2, 0);
        }
    }

    /**
     * Gets the "altphone" element
     */
    public java.lang.String getAltphone() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(ALTPHONE$4,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "altphone" element
     */
    public org.apache.xmlbeans.XmlString xgetAltphone() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(ALTPHONE$4,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "altphone" element
     */
    public boolean isNilAltphone() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(ALTPHONE$4,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "altphone" element
     */
    public boolean isSetAltphone() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(ALTPHONE$4) != 0;
        }
    }

    /**
     * Sets the "altphone" element
     */
    public void setAltphone(java.lang.String altphone) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(ALTPHONE$4,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(ALTPHONE$4);
            }

            target.setStringValue(altphone);
        }
    }

    /**
     * Sets (as xml) the "altphone" element
     */
    public void xsetAltphone(org.apache.xmlbeans.XmlString altphone) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(ALTPHONE$4,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(ALTPHONE$4);
            }

            target.set(altphone);
        }
    }

    /**
     * Nils the "altphone" element
     */
    public void setNilAltphone() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(ALTPHONE$4,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(ALTPHONE$4);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "altphone" element
     */
    public void unsetAltphone() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(ALTPHONE$4, 0);
        }
    }

    /**
     * Gets the "city" element
     */
    public java.lang.String getCity() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(CITY$6,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "city" element
     */
    public org.apache.xmlbeans.XmlString xgetCity() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(CITY$6,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "city" element
     */
    public boolean isNilCity() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(CITY$6,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "city" element
     */
    public boolean isSetCity() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(CITY$6) != 0;
        }
    }

    /**
     * Sets the "city" element
     */
    public void setCity(java.lang.String city) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(CITY$6,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(CITY$6);
            }

            target.setStringValue(city);
        }
    }

    /**
     * Sets (as xml) the "city" element
     */
    public void xsetCity(org.apache.xmlbeans.XmlString city) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(CITY$6,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(CITY$6);
            }

            target.set(city);
        }
    }

    /**
     * Nils the "city" element
     */
    public void setNilCity() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(CITY$6,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(CITY$6);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "city" element
     */
    public void unsetCity() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(CITY$6, 0);
        }
    }

    /**
     * Gets the "commonnum" element
     */
    public java.lang.String getCommonnum() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(COMMONNUM$8,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "commonnum" element
     */
    public org.apache.xmlbeans.XmlString xgetCommonnum() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(COMMONNUM$8,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "commonnum" element
     */
    public boolean isNilCommonnum() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(COMMONNUM$8,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "commonnum" element
     */
    public boolean isSetCommonnum() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(COMMONNUM$8) != 0;
        }
    }

    /**
     * Sets the "commonnum" element
     */
    public void setCommonnum(java.lang.String commonnum) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(COMMONNUM$8,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(COMMONNUM$8);
            }

            target.setStringValue(commonnum);
        }
    }

    /**
     * Sets (as xml) the "commonnum" element
     */
    public void xsetCommonnum(org.apache.xmlbeans.XmlString commonnum) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(COMMONNUM$8,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(COMMONNUM$8);
            }

            target.set(commonnum);
        }
    }

    /**
     * Nils the "commonnum" element
     */
    public void setNilCommonnum() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(COMMONNUM$8,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(COMMONNUM$8);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "commonnum" element
     */
    public void unsetCommonnum() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(COMMONNUM$8, 0);
        }
    }

    /**
     * Gets the "countrycode_ID" element
     */
    public java.lang.String getCountrycodeID() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(COUNTRYCODEID$10,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "countrycode_ID" element
     */
    public org.apache.xmlbeans.XmlString xgetCountrycodeID() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(COUNTRYCODEID$10,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "countrycode_ID" element
     */
    public boolean isNilCountrycodeID() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(COUNTRYCODEID$10,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "countrycode_ID" element
     */
    public boolean isSetCountrycodeID() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(COUNTRYCODEID$10) != 0;
        }
    }

    /**
     * Sets the "countrycode_ID" element
     */
    public void setCountrycodeID(java.lang.String countrycodeID) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(COUNTRYCODEID$10,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(COUNTRYCODEID$10);
            }

            target.setStringValue(countrycodeID);
        }
    }

    /**
     * Sets (as xml) the "countrycode_ID" element
     */
    public void xsetCountrycodeID(org.apache.xmlbeans.XmlString countrycodeID) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(COUNTRYCODEID$10,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(COUNTRYCODEID$10);
            }

            target.set(countrycodeID);
        }
    }

    /**
     * Nils the "countrycode_ID" element
     */
    public void setNilCountrycodeID() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(COUNTRYCODEID$10,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(COUNTRYCODEID$10);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "countrycode_ID" element
     */
    public void unsetCountrycodeID() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(COUNTRYCODEID$10, 0);
        }
    }

    /**
     * Gets the "countryofissue" element
     */
    public java.lang.String getCountryofissue() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(COUNTRYOFISSUE$12,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "countryofissue" element
     */
    public org.apache.xmlbeans.XmlString xgetCountryofissue() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(COUNTRYOFISSUE$12,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "countryofissue" element
     */
    public boolean isNilCountryofissue() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(COUNTRYOFISSUE$12,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "countryofissue" element
     */
    public boolean isSetCountryofissue() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(COUNTRYOFISSUE$12) != 0;
        }
    }

    /**
     * Sets the "countryofissue" element
     */
    public void setCountryofissue(java.lang.String countryofissue) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(COUNTRYOFISSUE$12,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(COUNTRYOFISSUE$12);
            }

            target.setStringValue(countryofissue);
        }
    }

    /**
     * Sets (as xml) the "countryofissue" element
     */
    public void xsetCountryofissue(org.apache.xmlbeans.XmlString countryofissue) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(COUNTRYOFISSUE$12,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(COUNTRYOFISSUE$12);
            }

            target.set(countryofissue);
        }
    }

    /**
     * Nils the "countryofissue" element
     */
    public void setNilCountryofissue() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(COUNTRYOFISSUE$12,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(COUNTRYOFISSUE$12);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "countryofissue" element
     */
    public void unsetCountryofissue() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(COUNTRYOFISSUE$12, 0);
        }
    }

    /**
     * Gets the "dlstate" element
     */
    public java.lang.String getDlstate() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(DLSTATE$14,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "dlstate" element
     */
    public org.apache.xmlbeans.XmlString xgetDlstate() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(DLSTATE$14,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "dlstate" element
     */
    public boolean isNilDlstate() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(DLSTATE$14,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "dlstate" element
     */
    public boolean isSetDlstate() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(DLSTATE$14) != 0;
        }
    }

    /**
     * Sets the "dlstate" element
     */
    public void setDlstate(java.lang.String dlstate) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(DLSTATE$14,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(DLSTATE$14);
            }

            target.setStringValue(dlstate);
        }
    }

    /**
     * Sets (as xml) the "dlstate" element
     */
    public void xsetDlstate(org.apache.xmlbeans.XmlString dlstate) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(DLSTATE$14,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(DLSTATE$14);
            }

            target.set(dlstate);
        }
    }

    /**
     * Nils the "dlstate" element
     */
    public void setNilDlstate() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(DLSTATE$14,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(DLSTATE$14);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "dlstate" element
     */
    public void unsetDlstate() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(DLSTATE$14, 0);
        }
    }

    /**
     * Gets the "driverslicense" element
     */
    public java.lang.String getDriverslicense() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(DRIVERSLICENSE$16,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "driverslicense" element
     */
    public org.apache.xmlbeans.XmlString xgetDriverslicense() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(DRIVERSLICENSE$16,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "driverslicense" element
     */
    public boolean isNilDriverslicense() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(DRIVERSLICENSE$16,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "driverslicense" element
     */
    public boolean isSetDriverslicense() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(DRIVERSLICENSE$16) != 0;
        }
    }

    /**
     * Sets the "driverslicense" element
     */
    public void setDriverslicense(java.lang.String driverslicense) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(DRIVERSLICENSE$16,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(DRIVERSLICENSE$16);
            }

            target.setStringValue(driverslicense);
        }
    }

    /**
     * Sets (as xml) the "driverslicense" element
     */
    public void xsetDriverslicense(org.apache.xmlbeans.XmlString driverslicense) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(DRIVERSLICENSE$16,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(DRIVERSLICENSE$16);
            }

            target.set(driverslicense);
        }
    }

    /**
     * Nils the "driverslicense" element
     */
    public void setNilDriverslicense() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(DRIVERSLICENSE$16,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(DRIVERSLICENSE$16);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "driverslicense" element
     */
    public void unsetDriverslicense() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(DRIVERSLICENSE$16, 0);
        }
    }

    /**
     * Gets the "email" element
     */
    public java.lang.String getEmail() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(EMAIL$18,
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
                                                         .find_element_user(EMAIL$18,
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
                                                         .find_element_user(EMAIL$18,
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

            return get_store().count_elements(EMAIL$18) != 0;
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
                                                           .find_element_user(EMAIL$18,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(EMAIL$18);
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
                                                         .find_element_user(EMAIL$18,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(EMAIL$18);
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
                                                         .find_element_user(EMAIL$18,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(EMAIL$18);
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
            get_store().remove_element(EMAIL$18, 0);
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
                                                           .find_element_user(FIRSTNAME$20,
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
                                                         .find_element_user(FIRSTNAME$20,
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
                                                         .find_element_user(FIRSTNAME$20,
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

            return get_store().count_elements(FIRSTNAME$20) != 0;
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
                                                           .find_element_user(FIRSTNAME$20,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(FIRSTNAME$20);
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
                                                         .find_element_user(FIRSTNAME$20,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(FIRSTNAME$20);
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
                                                         .find_element_user(FIRSTNAME$20,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(FIRSTNAME$20);
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
            get_store().remove_element(FIRSTNAME$20, 0);
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
                                                           .find_element_user(HOMEPHONE$22,
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
                                                         .find_element_user(HOMEPHONE$22,
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
                                                         .find_element_user(HOMEPHONE$22,
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

            return get_store().count_elements(HOMEPHONE$22) != 0;
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
                                                           .find_element_user(HOMEPHONE$22,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(HOMEPHONE$22);
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
                                                         .find_element_user(HOMEPHONE$22,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(HOMEPHONE$22);
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
                                                         .find_element_user(HOMEPHONE$22,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(HOMEPHONE$22);
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
            get_store().remove_element(HOMEPHONE$22, 0);
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
                                                           .find_element_user(HOTEL$24,
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
                                                         .find_element_user(HOTEL$24,
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
                                                         .find_element_user(HOTEL$24,
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

            return get_store().count_elements(HOTEL$24) != 0;
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
                                                           .find_element_user(HOTEL$24,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(HOTEL$24);
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
                                                         .find_element_user(HOTEL$24,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(HOTEL$24);
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
                                                         .find_element_user(HOTEL$24,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(HOTEL$24);
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
            get_store().remove_element(HOTEL$24, 0);
        }
    }

    /**
     * Gets the "is_permanent" element
     */
    public int getIsPermanent() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(ISPERMANENT$26,
                    0);

            if (target == null) {
                return 0;
            }

            return target.getIntValue();
        }
    }

    /**
     * Gets (as xml) the "is_permanent" element
     */
    public org.apache.xmlbeans.XmlInt xgetIsPermanent() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt) get_store()
                                                      .find_element_user(ISPERMANENT$26,
                    0);

            return target;
        }
    }

    /**
     * True if has "is_permanent" element
     */
    public boolean isSetIsPermanent() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(ISPERMANENT$26) != 0;
        }
    }

    /**
     * Sets the "is_permanent" element
     */
    public void setIsPermanent(int isPermanent) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(ISPERMANENT$26,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(ISPERMANENT$26);
            }

            target.setIntValue(isPermanent);
        }
    }

    /**
     * Sets (as xml) the "is_permanent" element
     */
    public void xsetIsPermanent(org.apache.xmlbeans.XmlInt isPermanent) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt) get_store()
                                                      .find_element_user(ISPERMANENT$26,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlInt) get_store()
                                                          .add_element_user(ISPERMANENT$26);
            }

            target.set(isPermanent);
        }
    }

    /**
     * Unsets the "is_permanent" element
     */
    public void unsetIsPermanent() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(ISPERMANENT$26, 0);
        }
    }

    /**
     * Gets the "isprimary" element
     */
    public int getIsprimary() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(ISPRIMARY$28,
                    0);

            if (target == null) {
                return 0;
            }

            return target.getIntValue();
        }
    }

    /**
     * Gets (as xml) the "isprimary" element
     */
    public org.apache.xmlbeans.XmlInt xgetIsprimary() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt) get_store()
                                                      .find_element_user(ISPRIMARY$28,
                    0);

            return target;
        }
    }

    /**
     * True if has "isprimary" element
     */
    public boolean isSetIsprimary() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(ISPRIMARY$28) != 0;
        }
    }

    /**
     * Sets the "isprimary" element
     */
    public void setIsprimary(int isprimary) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(ISPRIMARY$28,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(ISPRIMARY$28);
            }

            target.setIntValue(isprimary);
        }
    }

    /**
     * Sets (as xml) the "isprimary" element
     */
    public void xsetIsprimary(org.apache.xmlbeans.XmlInt isprimary) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt) get_store()
                                                      .find_element_user(ISPRIMARY$28,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlInt) get_store()
                                                          .add_element_user(ISPRIMARY$28);
            }

            target.set(isprimary);
        }
    }

    /**
     * Unsets the "isprimary" element
     */
    public void unsetIsprimary() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(ISPRIMARY$28, 0);
        }
    }

    /**
     * Gets the "jobtitle" element
     */
    public java.lang.String getJobtitle() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(JOBTITLE$30,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "jobtitle" element
     */
    public org.apache.xmlbeans.XmlString xgetJobtitle() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(JOBTITLE$30,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "jobtitle" element
     */
    public boolean isNilJobtitle() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(JOBTITLE$30,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "jobtitle" element
     */
    public boolean isSetJobtitle() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(JOBTITLE$30) != 0;
        }
    }

    /**
     * Sets the "jobtitle" element
     */
    public void setJobtitle(java.lang.String jobtitle) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(JOBTITLE$30,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(JOBTITLE$30);
            }

            target.setStringValue(jobtitle);
        }
    }

    /**
     * Sets (as xml) the "jobtitle" element
     */
    public void xsetJobtitle(org.apache.xmlbeans.XmlString jobtitle) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(JOBTITLE$30,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(JOBTITLE$30);
            }

            target.set(jobtitle);
        }
    }

    /**
     * Nils the "jobtitle" element
     */
    public void setNilJobtitle() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(JOBTITLE$30,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(JOBTITLE$30);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "jobtitle" element
     */
    public void unsetJobtitle() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(JOBTITLE$30, 0);
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
                                                           .find_element_user(LASTNAME$32,
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
                                                         .find_element_user(LASTNAME$32,
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
                                                         .find_element_user(LASTNAME$32,
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

            return get_store().count_elements(LASTNAME$32) != 0;
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
                                                           .find_element_user(LASTNAME$32,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(LASTNAME$32);
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
                                                         .find_element_user(LASTNAME$32,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(LASTNAME$32);
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
                                                         .find_element_user(LASTNAME$32,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(LASTNAME$32);
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
            get_store().remove_element(LASTNAME$32, 0);
        }
    }

    /**
     * Gets the "membershipairline" element
     */
    public java.lang.String getMembershipairline() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(MEMBERSHIPAIRLINE$34,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "membershipairline" element
     */
    public org.apache.xmlbeans.XmlString xgetMembershipairline() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(MEMBERSHIPAIRLINE$34,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "membershipairline" element
     */
    public boolean isNilMembershipairline() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(MEMBERSHIPAIRLINE$34,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "membershipairline" element
     */
    public boolean isSetMembershipairline() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(MEMBERSHIPAIRLINE$34) != 0;
        }
    }

    /**
     * Sets the "membershipairline" element
     */
    public void setMembershipairline(java.lang.String membershipairline) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(MEMBERSHIPAIRLINE$34,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(MEMBERSHIPAIRLINE$34);
            }

            target.setStringValue(membershipairline);
        }
    }

    /**
     * Sets (as xml) the "membershipairline" element
     */
    public void xsetMembershipairline(
        org.apache.xmlbeans.XmlString membershipairline) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(MEMBERSHIPAIRLINE$34,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(MEMBERSHIPAIRLINE$34);
            }

            target.set(membershipairline);
        }
    }

    /**
     * Nils the "membershipairline" element
     */
    public void setNilMembershipairline() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(MEMBERSHIPAIRLINE$34,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(MEMBERSHIPAIRLINE$34);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "membershipairline" element
     */
    public void unsetMembershipairline() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(MEMBERSHIPAIRLINE$34, 0);
        }
    }

    /**
     * Gets the "membershipnum" element
     */
    public java.lang.String getMembershipnum() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(MEMBERSHIPNUM$36,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "membershipnum" element
     */
    public org.apache.xmlbeans.XmlString xgetMembershipnum() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(MEMBERSHIPNUM$36,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "membershipnum" element
     */
    public boolean isNilMembershipnum() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(MEMBERSHIPNUM$36,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "membershipnum" element
     */
    public boolean isSetMembershipnum() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(MEMBERSHIPNUM$36) != 0;
        }
    }

    /**
     * Sets the "membershipnum" element
     */
    public void setMembershipnum(java.lang.String membershipnum) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(MEMBERSHIPNUM$36,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(MEMBERSHIPNUM$36);
            }

            target.setStringValue(membershipnum);
        }
    }

    /**
     * Sets (as xml) the "membershipnum" element
     */
    public void xsetMembershipnum(org.apache.xmlbeans.XmlString membershipnum) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(MEMBERSHIPNUM$36,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(MEMBERSHIPNUM$36);
            }

            target.set(membershipnum);
        }
    }

    /**
     * Nils the "membershipnum" element
     */
    public void setNilMembershipnum() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(MEMBERSHIPNUM$36,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(MEMBERSHIPNUM$36);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "membershipnum" element
     */
    public void unsetMembershipnum() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(MEMBERSHIPNUM$36, 0);
        }
    }

    /**
     * Gets the "membershipstatus" element
     */
    public java.lang.String getMembershipstatus() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(MEMBERSHIPSTATUS$38,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "membershipstatus" element
     */
    public org.apache.xmlbeans.XmlString xgetMembershipstatus() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(MEMBERSHIPSTATUS$38,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "membershipstatus" element
     */
    public boolean isNilMembershipstatus() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(MEMBERSHIPSTATUS$38,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "membershipstatus" element
     */
    public boolean isSetMembershipstatus() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(MEMBERSHIPSTATUS$38) != 0;
        }
    }

    /**
     * Sets the "membershipstatus" element
     */
    public void setMembershipstatus(java.lang.String membershipstatus) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(MEMBERSHIPSTATUS$38,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(MEMBERSHIPSTATUS$38);
            }

            target.setStringValue(membershipstatus);
        }
    }

    /**
     * Sets (as xml) the "membershipstatus" element
     */
    public void xsetMembershipstatus(
        org.apache.xmlbeans.XmlString membershipstatus) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(MEMBERSHIPSTATUS$38,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(MEMBERSHIPSTATUS$38);
            }

            target.set(membershipstatus);
        }
    }

    /**
     * Nils the "membershipstatus" element
     */
    public void setNilMembershipstatus() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(MEMBERSHIPSTATUS$38,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(MEMBERSHIPSTATUS$38);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "membershipstatus" element
     */
    public void unsetMembershipstatus() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(MEMBERSHIPSTATUS$38, 0);
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
                                                           .find_element_user(MIDDLENAME$40,
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
                                                         .find_element_user(MIDDLENAME$40,
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
                                                         .find_element_user(MIDDLENAME$40,
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

            return get_store().count_elements(MIDDLENAME$40) != 0;
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
                                                           .find_element_user(MIDDLENAME$40,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(MIDDLENAME$40);
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
                                                         .find_element_user(MIDDLENAME$40,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(MIDDLENAME$40);
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
                                                         .find_element_user(MIDDLENAME$40,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(MIDDLENAME$40);
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
            get_store().remove_element(MIDDLENAME$40, 0);
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
                                                           .find_element_user(MOBILE$42,
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
                                                         .find_element_user(MOBILE$42,
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
                                                         .find_element_user(MOBILE$42,
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

            return get_store().count_elements(MOBILE$42) != 0;
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
                                                           .find_element_user(MOBILE$42,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(MOBILE$42);
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
                                                         .find_element_user(MOBILE$42,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(MOBILE$42);
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
                                                         .find_element_user(MOBILE$42,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(MOBILE$42);
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
            get_store().remove_element(MOBILE$42, 0);
        }
    }

    /**
     * Gets the "pager" element
     */
    public java.lang.String getPager() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(PAGER$44,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "pager" element
     */
    public org.apache.xmlbeans.XmlString xgetPager() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(PAGER$44,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "pager" element
     */
    public boolean isNilPager() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(PAGER$44,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "pager" element
     */
    public boolean isSetPager() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(PAGER$44) != 0;
        }
    }

    /**
     * Sets the "pager" element
     */
    public void setPager(java.lang.String pager) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(PAGER$44,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(PAGER$44);
            }

            target.setStringValue(pager);
        }
    }

    /**
     * Sets (as xml) the "pager" element
     */
    public void xsetPager(org.apache.xmlbeans.XmlString pager) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(PAGER$44,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(PAGER$44);
            }

            target.set(pager);
        }
    }

    /**
     * Nils the "pager" element
     */
    public void setNilPager() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(PAGER$44,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(PAGER$44);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "pager" element
     */
    public void unsetPager() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(PAGER$44, 0);
        }
    }

    /**
     * Gets the "passenger_ID" element
     */
    public int getPassengerID() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(PASSENGERID$46,
                    0);

            if (target == null) {
                return 0;
            }

            return target.getIntValue();
        }
    }

    /**
     * Gets (as xml) the "passenger_ID" element
     */
    public org.apache.xmlbeans.XmlInt xgetPassengerID() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt) get_store()
                                                      .find_element_user(PASSENGERID$46,
                    0);

            return target;
        }
    }

    /**
     * True if has "passenger_ID" element
     */
    public boolean isSetPassengerID() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(PASSENGERID$46) != 0;
        }
    }

    /**
     * Sets the "passenger_ID" element
     */
    public void setPassengerID(int passengerID) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(PASSENGERID$46,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(PASSENGERID$46);
            }

            target.setIntValue(passengerID);
        }
    }

    /**
     * Sets (as xml) the "passenger_ID" element
     */
    public void xsetPassengerID(org.apache.xmlbeans.XmlInt passengerID) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt) get_store()
                                                      .find_element_user(PASSENGERID$46,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlInt) get_store()
                                                          .add_element_user(PASSENGERID$46);
            }

            target.set(passengerID);
        }
    }

    /**
     * Unsets the "passenger_ID" element
     */
    public void unsetPassengerID() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(PASSENGERID$46, 0);
        }
    }

    /**
     * Gets the "province" element
     */
    public java.lang.String getProvince() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(PROVINCE$48,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "province" element
     */
    public org.apache.xmlbeans.XmlString xgetProvince() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(PROVINCE$48,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "province" element
     */
    public boolean isNilProvince() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(PROVINCE$48,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "province" element
     */
    public boolean isSetProvince() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(PROVINCE$48) != 0;
        }
    }

    /**
     * Sets the "province" element
     */
    public void setProvince(java.lang.String province) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(PROVINCE$48,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(PROVINCE$48);
            }

            target.setStringValue(province);
        }
    }

    /**
     * Sets (as xml) the "province" element
     */
    public void xsetProvince(org.apache.xmlbeans.XmlString province) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(PROVINCE$48,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(PROVINCE$48);
            }

            target.set(province);
        }
    }

    /**
     * Nils the "province" element
     */
    public void setNilProvince() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(PROVINCE$48,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(PROVINCE$48);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "province" element
     */
    public void unsetProvince() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(PROVINCE$48, 0);
        }
    }

    /**
     * Gets the "salutation" element
     */
    public java.lang.String getSalutation() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(SALUTATION$50,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "salutation" element
     */
    public org.apache.xmlbeans.XmlString xgetSalutation() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(SALUTATION$50,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "salutation" element
     */
    public boolean isNilSalutation() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(SALUTATION$50,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "salutation" element
     */
    public boolean isSetSalutation() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(SALUTATION$50) != 0;
        }
    }

    /**
     * Sets the "salutation" element
     */
    public void setSalutation(java.lang.String salutation) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(SALUTATION$50,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(SALUTATION$50);
            }

            target.setStringValue(salutation);
        }
    }

    /**
     * Sets (as xml) the "salutation" element
     */
    public void xsetSalutation(org.apache.xmlbeans.XmlString salutation) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(SALUTATION$50,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(SALUTATION$50);
            }

            target.set(salutation);
        }
    }

    /**
     * Nils the "salutation" element
     */
    public void setNilSalutation() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(SALUTATION$50,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(SALUTATION$50);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "salutation" element
     */
    public void unsetSalutation() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(SALUTATION$50, 0);
        }
    }

    /**
     * Gets the "state_ID" element
     */
    public java.lang.String getStateID() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(STATEID$52,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "state_ID" element
     */
    public org.apache.xmlbeans.XmlString xgetStateID() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(STATEID$52,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "state_ID" element
     */
    public boolean isNilStateID() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(STATEID$52,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "state_ID" element
     */
    public boolean isSetStateID() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(STATEID$52) != 0;
        }
    }

    /**
     * Sets the "state_ID" element
     */
    public void setStateID(java.lang.String stateID) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(STATEID$52,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(STATEID$52);
            }

            target.setStringValue(stateID);
        }
    }

    /**
     * Sets (as xml) the "state_ID" element
     */
    public void xsetStateID(org.apache.xmlbeans.XmlString stateID) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(STATEID$52,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(STATEID$52);
            }

            target.set(stateID);
        }
    }

    /**
     * Nils the "state_ID" element
     */
    public void setNilStateID() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(STATEID$52,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(STATEID$52);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "state_ID" element
     */
    public void unsetStateID() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(STATEID$52, 0);
        }
    }

    /**
     * Gets the "valid_bdate" element
     */
    public java.lang.String getValidBdate() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(VALIDBDATE$54,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "valid_bdate" element
     */
    public org.apache.xmlbeans.XmlString xgetValidBdate() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(VALIDBDATE$54,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "valid_bdate" element
     */
    public boolean isNilValidBdate() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(VALIDBDATE$54,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "valid_bdate" element
     */
    public boolean isSetValidBdate() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(VALIDBDATE$54) != 0;
        }
    }

    /**
     * Sets the "valid_bdate" element
     */
    public void setValidBdate(java.lang.String validBdate) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(VALIDBDATE$54,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(VALIDBDATE$54);
            }

            target.setStringValue(validBdate);
        }
    }

    /**
     * Sets (as xml) the "valid_bdate" element
     */
    public void xsetValidBdate(org.apache.xmlbeans.XmlString validBdate) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(VALIDBDATE$54,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(VALIDBDATE$54);
            }

            target.set(validBdate);
        }
    }

    /**
     * Nils the "valid_bdate" element
     */
    public void setNilValidBdate() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(VALIDBDATE$54,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(VALIDBDATE$54);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "valid_bdate" element
     */
    public void unsetValidBdate() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(VALIDBDATE$54, 0);
        }
    }

    /**
     * Gets the "valid_edate" element
     */
    public java.lang.String getValidEdate() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(VALIDEDATE$56,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "valid_edate" element
     */
    public org.apache.xmlbeans.XmlString xgetValidEdate() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(VALIDEDATE$56,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "valid_edate" element
     */
    public boolean isNilValidEdate() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(VALIDEDATE$56,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "valid_edate" element
     */
    public boolean isSetValidEdate() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(VALIDEDATE$56) != 0;
        }
    }

    /**
     * Sets the "valid_edate" element
     */
    public void setValidEdate(java.lang.String validEdate) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(VALIDEDATE$56,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(VALIDEDATE$56);
            }

            target.setStringValue(validEdate);
        }
    }

    /**
     * Sets (as xml) the "valid_edate" element
     */
    public void xsetValidEdate(org.apache.xmlbeans.XmlString validEdate) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(VALIDEDATE$56,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(VALIDEDATE$56);
            }

            target.set(validEdate);
        }
    }

    /**
     * Nils the "valid_edate" element
     */
    public void setNilValidEdate() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(VALIDEDATE$56,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(VALIDEDATE$56);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "valid_edate" element
     */
    public void unsetValidEdate() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(VALIDEDATE$56, 0);
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
                                                           .find_element_user(WORKPHONE$58,
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
                                                         .find_element_user(WORKPHONE$58,
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
                                                         .find_element_user(WORKPHONE$58,
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

            return get_store().count_elements(WORKPHONE$58) != 0;
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
                                                           .find_element_user(WORKPHONE$58,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(WORKPHONE$58);
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
                                                         .find_element_user(WORKPHONE$58,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(WORKPHONE$58);
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
                                                         .find_element_user(WORKPHONE$58,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(WORKPHONE$58);
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
            get_store().remove_element(WORKPHONE$58, 0);
        }
    }

    /**
     * Gets the "zip" element
     */
    public java.lang.String getZip() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(ZIP$60,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "zip" element
     */
    public org.apache.xmlbeans.XmlString xgetZip() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(ZIP$60,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "zip" element
     */
    public boolean isNilZip() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(ZIP$60,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "zip" element
     */
    public boolean isSetZip() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(ZIP$60) != 0;
        }
    }

    /**
     * Sets the "zip" element
     */
    public void setZip(java.lang.String zip) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(ZIP$60,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(ZIP$60);
            }

            target.setStringValue(zip);
        }
    }

    /**
     * Sets (as xml) the "zip" element
     */
    public void xsetZip(org.apache.xmlbeans.XmlString zip) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(ZIP$60,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(ZIP$60);
            }

            target.set(zip);
        }
    }

    /**
     * Nils the "zip" element
     */
    public void setNilZip() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(ZIP$60,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(ZIP$60);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "zip" element
     */
    public void unsetZip() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(ZIP$60, 0);
        }
    }
}
