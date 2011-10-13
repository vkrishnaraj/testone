/*
 * XML Type:  WS_Item
 * Namespace: http://pojo.core.ws.nettracer.bagnet.com/xsd
 * Java type: com.bagnet.nettracer.ws.core.pojo.xsd.WSItem
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.core.pojo.xsd.impl;

/**
 * An XML WS_Item(@http://pojo.core.ws.nettracer.bagnet.com/xsd).
 *
 * This is a complex type.
 */
public class WSItemImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
    implements com.bagnet.nettracer.ws.core.pojo.xsd.WSItem {
    private static final javax.xml.namespace.QName ARRIVEDONAIRLINEID$0 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "arrivedonairline_ID");
    private static final javax.xml.namespace.QName ARRIVEDONDATE$2 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "arrivedondate");
    private static final javax.xml.namespace.QName ARRIVEDONFLIGHTNUM$4 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "arrivedonflightnum");
    private static final javax.xml.namespace.QName BAGNUMBER$6 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "bagnumber");
    private static final javax.xml.namespace.QName BAGSTATUS$8 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "bagstatus");
    private static final javax.xml.namespace.QName BAGTYPE$10 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "bagtype");
    private static final javax.xml.namespace.QName CLAIMCHECKNUM$12 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "claimchecknum");
    private static final javax.xml.namespace.QName COLOR$14 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "color");
    private static final javax.xml.namespace.QName COST$16 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "cost");
    private static final javax.xml.namespace.QName CURRENCYID$18 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "currency_ID");
    private static final javax.xml.namespace.QName DAMAGE$20 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "damage");
    private static final javax.xml.namespace.QName DRAFTS$22 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "drafts");
    private static final javax.xml.namespace.QName FNAMEONBAG$24 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "fnameonbag");
    private static final javax.xml.namespace.QName INVENTORIES$26 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "inventories");
    private static final javax.xml.namespace.QName ITEMTYPE$28 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "itemtype");
    private static final javax.xml.namespace.QName LNAMEONBAG$30 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "lnameonbag");
    private static final javax.xml.namespace.QName LVLOFDAMAGE$32 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "lvlofdamage");
    private static final javax.xml.namespace.QName MANUFACTURER$34 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "manufacturer");
    private static final javax.xml.namespace.QName MATCHEDOHD$36 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "matched_ohd");
    private static final javax.xml.namespace.QName MNAMEONBAG$38 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "mnameonbag");
    private static final javax.xml.namespace.QName RESOLUTIONDESC$40 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "resolutiondesc");
    private static final javax.xml.namespace.QName RESOLUTIONSTATUS$42 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "resolutionstatus");
    private static final javax.xml.namespace.QName XDESCELEMENT1$44 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "xdescelement_1");
    private static final javax.xml.namespace.QName XDESCELEMENT2$46 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "xdescelement_2");
    private static final javax.xml.namespace.QName XDESCELEMENT3$48 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "xdescelement_3");

    public WSItemImpl(org.apache.xmlbeans.SchemaType sType) {
        super(sType);
    }

    /**
     * Gets the "arrivedonairline_ID" element
     */
    public java.lang.String getArrivedonairlineID() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(ARRIVEDONAIRLINEID$0,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "arrivedonairline_ID" element
     */
    public org.apache.xmlbeans.XmlString xgetArrivedonairlineID() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(ARRIVEDONAIRLINEID$0,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "arrivedonairline_ID" element
     */
    public boolean isNilArrivedonairlineID() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(ARRIVEDONAIRLINEID$0,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "arrivedonairline_ID" element
     */
    public boolean isSetArrivedonairlineID() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(ARRIVEDONAIRLINEID$0) != 0;
        }
    }

    /**
     * Sets the "arrivedonairline_ID" element
     */
    public void setArrivedonairlineID(java.lang.String arrivedonairlineID) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(ARRIVEDONAIRLINEID$0,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(ARRIVEDONAIRLINEID$0);
            }

            target.setStringValue(arrivedonairlineID);
        }
    }

    /**
     * Sets (as xml) the "arrivedonairline_ID" element
     */
    public void xsetArrivedonairlineID(
        org.apache.xmlbeans.XmlString arrivedonairlineID) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(ARRIVEDONAIRLINEID$0,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(ARRIVEDONAIRLINEID$0);
            }

            target.set(arrivedonairlineID);
        }
    }

    /**
     * Nils the "arrivedonairline_ID" element
     */
    public void setNilArrivedonairlineID() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(ARRIVEDONAIRLINEID$0,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(ARRIVEDONAIRLINEID$0);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "arrivedonairline_ID" element
     */
    public void unsetArrivedonairlineID() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(ARRIVEDONAIRLINEID$0, 0);
        }
    }

    /**
     * Gets the "arrivedondate" element
     */
    public java.lang.String getArrivedondate() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(ARRIVEDONDATE$2,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "arrivedondate" element
     */
    public org.apache.xmlbeans.XmlString xgetArrivedondate() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(ARRIVEDONDATE$2,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "arrivedondate" element
     */
    public boolean isNilArrivedondate() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(ARRIVEDONDATE$2,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "arrivedondate" element
     */
    public boolean isSetArrivedondate() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(ARRIVEDONDATE$2) != 0;
        }
    }

    /**
     * Sets the "arrivedondate" element
     */
    public void setArrivedondate(java.lang.String arrivedondate) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(ARRIVEDONDATE$2,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(ARRIVEDONDATE$2);
            }

            target.setStringValue(arrivedondate);
        }
    }

    /**
     * Sets (as xml) the "arrivedondate" element
     */
    public void xsetArrivedondate(org.apache.xmlbeans.XmlString arrivedondate) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(ARRIVEDONDATE$2,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(ARRIVEDONDATE$2);
            }

            target.set(arrivedondate);
        }
    }

    /**
     * Nils the "arrivedondate" element
     */
    public void setNilArrivedondate() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(ARRIVEDONDATE$2,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(ARRIVEDONDATE$2);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "arrivedondate" element
     */
    public void unsetArrivedondate() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(ARRIVEDONDATE$2, 0);
        }
    }

    /**
     * Gets the "arrivedonflightnum" element
     */
    public java.lang.String getArrivedonflightnum() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(ARRIVEDONFLIGHTNUM$4,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "arrivedonflightnum" element
     */
    public org.apache.xmlbeans.XmlString xgetArrivedonflightnum() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(ARRIVEDONFLIGHTNUM$4,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "arrivedonflightnum" element
     */
    public boolean isNilArrivedonflightnum() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(ARRIVEDONFLIGHTNUM$4,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "arrivedonflightnum" element
     */
    public boolean isSetArrivedonflightnum() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(ARRIVEDONFLIGHTNUM$4) != 0;
        }
    }

    /**
     * Sets the "arrivedonflightnum" element
     */
    public void setArrivedonflightnum(java.lang.String arrivedonflightnum) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(ARRIVEDONFLIGHTNUM$4,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(ARRIVEDONFLIGHTNUM$4);
            }

            target.setStringValue(arrivedonflightnum);
        }
    }

    /**
     * Sets (as xml) the "arrivedonflightnum" element
     */
    public void xsetArrivedonflightnum(
        org.apache.xmlbeans.XmlString arrivedonflightnum) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(ARRIVEDONFLIGHTNUM$4,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(ARRIVEDONFLIGHTNUM$4);
            }

            target.set(arrivedonflightnum);
        }
    }

    /**
     * Nils the "arrivedonflightnum" element
     */
    public void setNilArrivedonflightnum() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(ARRIVEDONFLIGHTNUM$4,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(ARRIVEDONFLIGHTNUM$4);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "arrivedonflightnum" element
     */
    public void unsetArrivedonflightnum() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(ARRIVEDONFLIGHTNUM$4, 0);
        }
    }

    /**
     * Gets the "bagnumber" element
     */
    public int getBagnumber() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(BAGNUMBER$6,
                    0);

            if (target == null) {
                return 0;
            }

            return target.getIntValue();
        }
    }

    /**
     * Gets (as xml) the "bagnumber" element
     */
    public org.apache.xmlbeans.XmlInt xgetBagnumber() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt) get_store()
                                                      .find_element_user(BAGNUMBER$6,
                    0);

            return target;
        }
    }

    /**
     * True if has "bagnumber" element
     */
    public boolean isSetBagnumber() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(BAGNUMBER$6) != 0;
        }
    }

    /**
     * Sets the "bagnumber" element
     */
    public void setBagnumber(int bagnumber) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(BAGNUMBER$6,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(BAGNUMBER$6);
            }

            target.setIntValue(bagnumber);
        }
    }

    /**
     * Sets (as xml) the "bagnumber" element
     */
    public void xsetBagnumber(org.apache.xmlbeans.XmlInt bagnumber) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt) get_store()
                                                      .find_element_user(BAGNUMBER$6,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlInt) get_store()
                                                          .add_element_user(BAGNUMBER$6);
            }

            target.set(bagnumber);
        }
    }

    /**
     * Unsets the "bagnumber" element
     */
    public void unsetBagnumber() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(BAGNUMBER$6, 0);
        }
    }

    /**
     * Gets the "bagstatus" element
     */
    public java.lang.String getBagstatus() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(BAGSTATUS$8,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "bagstatus" element
     */
    public org.apache.xmlbeans.XmlString xgetBagstatus() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(BAGSTATUS$8,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "bagstatus" element
     */
    public boolean isNilBagstatus() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(BAGSTATUS$8,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "bagstatus" element
     */
    public boolean isSetBagstatus() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(BAGSTATUS$8) != 0;
        }
    }

    /**
     * Sets the "bagstatus" element
     */
    public void setBagstatus(java.lang.String bagstatus) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(BAGSTATUS$8,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(BAGSTATUS$8);
            }

            target.setStringValue(bagstatus);
        }
    }

    /**
     * Sets (as xml) the "bagstatus" element
     */
    public void xsetBagstatus(org.apache.xmlbeans.XmlString bagstatus) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(BAGSTATUS$8,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(BAGSTATUS$8);
            }

            target.set(bagstatus);
        }
    }

    /**
     * Nils the "bagstatus" element
     */
    public void setNilBagstatus() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(BAGSTATUS$8,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(BAGSTATUS$8);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "bagstatus" element
     */
    public void unsetBagstatus() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(BAGSTATUS$8, 0);
        }
    }

    /**
     * Gets the "bagtype" element
     */
    public java.lang.String getBagtype() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(BAGTYPE$10,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "bagtype" element
     */
    public org.apache.xmlbeans.XmlString xgetBagtype() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(BAGTYPE$10,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "bagtype" element
     */
    public boolean isNilBagtype() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(BAGTYPE$10,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "bagtype" element
     */
    public boolean isSetBagtype() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(BAGTYPE$10) != 0;
        }
    }

    /**
     * Sets the "bagtype" element
     */
    public void setBagtype(java.lang.String bagtype) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(BAGTYPE$10,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(BAGTYPE$10);
            }

            target.setStringValue(bagtype);
        }
    }

    /**
     * Sets (as xml) the "bagtype" element
     */
    public void xsetBagtype(org.apache.xmlbeans.XmlString bagtype) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(BAGTYPE$10,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(BAGTYPE$10);
            }

            target.set(bagtype);
        }
    }

    /**
     * Nils the "bagtype" element
     */
    public void setNilBagtype() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(BAGTYPE$10,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(BAGTYPE$10);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "bagtype" element
     */
    public void unsetBagtype() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(BAGTYPE$10, 0);
        }
    }

    /**
     * Gets the "claimchecknum" element
     */
    public java.lang.String getClaimchecknum() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(CLAIMCHECKNUM$12,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "claimchecknum" element
     */
    public org.apache.xmlbeans.XmlString xgetClaimchecknum() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(CLAIMCHECKNUM$12,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "claimchecknum" element
     */
    public boolean isNilClaimchecknum() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(CLAIMCHECKNUM$12,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "claimchecknum" element
     */
    public boolean isSetClaimchecknum() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(CLAIMCHECKNUM$12) != 0;
        }
    }

    /**
     * Sets the "claimchecknum" element
     */
    public void setClaimchecknum(java.lang.String claimchecknum) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(CLAIMCHECKNUM$12,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(CLAIMCHECKNUM$12);
            }

            target.setStringValue(claimchecknum);
        }
    }

    /**
     * Sets (as xml) the "claimchecknum" element
     */
    public void xsetClaimchecknum(org.apache.xmlbeans.XmlString claimchecknum) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(CLAIMCHECKNUM$12,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(CLAIMCHECKNUM$12);
            }

            target.set(claimchecknum);
        }
    }

    /**
     * Nils the "claimchecknum" element
     */
    public void setNilClaimchecknum() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(CLAIMCHECKNUM$12,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(CLAIMCHECKNUM$12);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "claimchecknum" element
     */
    public void unsetClaimchecknum() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(CLAIMCHECKNUM$12, 0);
        }
    }

    /**
     * Gets the "color" element
     */
    public java.lang.String getColor() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(COLOR$14,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "color" element
     */
    public org.apache.xmlbeans.XmlString xgetColor() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(COLOR$14,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "color" element
     */
    public boolean isNilColor() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(COLOR$14,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "color" element
     */
    public boolean isSetColor() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(COLOR$14) != 0;
        }
    }

    /**
     * Sets the "color" element
     */
    public void setColor(java.lang.String color) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(COLOR$14,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(COLOR$14);
            }

            target.setStringValue(color);
        }
    }

    /**
     * Sets (as xml) the "color" element
     */
    public void xsetColor(org.apache.xmlbeans.XmlString color) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(COLOR$14,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(COLOR$14);
            }

            target.set(color);
        }
    }

    /**
     * Nils the "color" element
     */
    public void setNilColor() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(COLOR$14,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(COLOR$14);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "color" element
     */
    public void unsetColor() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(COLOR$14, 0);
        }
    }

    /**
     * Gets the "cost" element
     */
    public double getCost() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(COST$16,
                    0);

            if (target == null) {
                return 0.0;
            }

            return target.getDoubleValue();
        }
    }

    /**
     * Gets (as xml) the "cost" element
     */
    public org.apache.xmlbeans.XmlDouble xgetCost() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble) get_store()
                                                         .find_element_user(COST$16,
                    0);

            return target;
        }
    }

    /**
     * True if has "cost" element
     */
    public boolean isSetCost() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(COST$16) != 0;
        }
    }

    /**
     * Sets the "cost" element
     */
    public void setCost(double cost) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(COST$16,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(COST$16);
            }

            target.setDoubleValue(cost);
        }
    }

    /**
     * Sets (as xml) the "cost" element
     */
    public void xsetCost(org.apache.xmlbeans.XmlDouble cost) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble) get_store()
                                                         .find_element_user(COST$16,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlDouble) get_store()
                                                             .add_element_user(COST$16);
            }

            target.set(cost);
        }
    }

    /**
     * Unsets the "cost" element
     */
    public void unsetCost() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(COST$16, 0);
        }
    }

    /**
     * Gets the "currency_ID" element
     */
    public java.lang.String getCurrencyID() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(CURRENCYID$18,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "currency_ID" element
     */
    public org.apache.xmlbeans.XmlString xgetCurrencyID() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(CURRENCYID$18,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "currency_ID" element
     */
    public boolean isNilCurrencyID() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(CURRENCYID$18,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "currency_ID" element
     */
    public boolean isSetCurrencyID() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(CURRENCYID$18) != 0;
        }
    }

    /**
     * Sets the "currency_ID" element
     */
    public void setCurrencyID(java.lang.String currencyID) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(CURRENCYID$18,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(CURRENCYID$18);
            }

            target.setStringValue(currencyID);
        }
    }

    /**
     * Sets (as xml) the "currency_ID" element
     */
    public void xsetCurrencyID(org.apache.xmlbeans.XmlString currencyID) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(CURRENCYID$18,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(CURRENCYID$18);
            }

            target.set(currencyID);
        }
    }

    /**
     * Nils the "currency_ID" element
     */
    public void setNilCurrencyID() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(CURRENCYID$18,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(CURRENCYID$18);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "currency_ID" element
     */
    public void unsetCurrencyID() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(CURRENCYID$18, 0);
        }
    }

    /**
     * Gets the "damage" element
     */
    public java.lang.String getDamage() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(DAMAGE$20,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "damage" element
     */
    public org.apache.xmlbeans.XmlString xgetDamage() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(DAMAGE$20,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "damage" element
     */
    public boolean isNilDamage() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(DAMAGE$20,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "damage" element
     */
    public boolean isSetDamage() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(DAMAGE$20) != 0;
        }
    }

    /**
     * Sets the "damage" element
     */
    public void setDamage(java.lang.String damage) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(DAMAGE$20,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(DAMAGE$20);
            }

            target.setStringValue(damage);
        }
    }

    /**
     * Sets (as xml) the "damage" element
     */
    public void xsetDamage(org.apache.xmlbeans.XmlString damage) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(DAMAGE$20,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(DAMAGE$20);
            }

            target.set(damage);
        }
    }

    /**
     * Nils the "damage" element
     */
    public void setNilDamage() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(DAMAGE$20,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(DAMAGE$20);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "damage" element
     */
    public void unsetDamage() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(DAMAGE$20, 0);
        }
    }

    /**
     * Gets the "drafts" element
     */
    public java.lang.String getDrafts() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(DRAFTS$22,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "drafts" element
     */
    public org.apache.xmlbeans.XmlString xgetDrafts() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(DRAFTS$22,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "drafts" element
     */
    public boolean isNilDrafts() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(DRAFTS$22,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "drafts" element
     */
    public boolean isSetDrafts() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(DRAFTS$22) != 0;
        }
    }

    /**
     * Sets the "drafts" element
     */
    public void setDrafts(java.lang.String drafts) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(DRAFTS$22,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(DRAFTS$22);
            }

            target.setStringValue(drafts);
        }
    }

    /**
     * Sets (as xml) the "drafts" element
     */
    public void xsetDrafts(org.apache.xmlbeans.XmlString drafts) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(DRAFTS$22,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(DRAFTS$22);
            }

            target.set(drafts);
        }
    }

    /**
     * Nils the "drafts" element
     */
    public void setNilDrafts() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(DRAFTS$22,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(DRAFTS$22);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "drafts" element
     */
    public void unsetDrafts() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(DRAFTS$22, 0);
        }
    }

    /**
     * Gets the "fnameonbag" element
     */
    public java.lang.String getFnameonbag() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(FNAMEONBAG$24,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "fnameonbag" element
     */
    public org.apache.xmlbeans.XmlString xgetFnameonbag() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(FNAMEONBAG$24,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "fnameonbag" element
     */
    public boolean isNilFnameonbag() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(FNAMEONBAG$24,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "fnameonbag" element
     */
    public boolean isSetFnameonbag() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(FNAMEONBAG$24) != 0;
        }
    }

    /**
     * Sets the "fnameonbag" element
     */
    public void setFnameonbag(java.lang.String fnameonbag) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(FNAMEONBAG$24,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(FNAMEONBAG$24);
            }

            target.setStringValue(fnameonbag);
        }
    }

    /**
     * Sets (as xml) the "fnameonbag" element
     */
    public void xsetFnameonbag(org.apache.xmlbeans.XmlString fnameonbag) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(FNAMEONBAG$24,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(FNAMEONBAG$24);
            }

            target.set(fnameonbag);
        }
    }

    /**
     * Nils the "fnameonbag" element
     */
    public void setNilFnameonbag() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(FNAMEONBAG$24,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(FNAMEONBAG$24);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "fnameonbag" element
     */
    public void unsetFnameonbag() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(FNAMEONBAG$24, 0);
        }
    }

    /**
     * Gets array of all "inventories" elements
     */
    public com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory[] getInventoriesArray() {
        synchronized (monitor()) {
            check_orphaned();

            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(INVENTORIES$26, targetList);

            com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory[] result = new com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory[targetList.size()];
            targetList.toArray(result);

            return result;
        }
    }

    /**
     * Gets ith "inventories" element
     */
    public com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory getInventoriesArray(
        int i) {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory) get_store()
                                                                             .find_element_user(INVENTORIES$26,
                    i);

            if (target == null) {
                throw new IndexOutOfBoundsException();
            }

            return target;
        }
    }

    /**
     * Tests for nil ith "inventories" element
     */
    public boolean isNilInventoriesArray(int i) {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory) get_store()
                                                                             .find_element_user(INVENTORIES$26,
                    i);

            if (target == null) {
                throw new IndexOutOfBoundsException();
            }

            return target.isNil();
        }
    }

    /**
     * Returns number of "inventories" element
     */
    public int sizeOfInventoriesArray() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(INVENTORIES$26);
        }
    }

    /**
     * Sets array of all "inventories" element
     */
    public void setInventoriesArray(
        com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory[] inventoriesArray) {
        synchronized (monitor()) {
            check_orphaned();
            arraySetterHelper(inventoriesArray, INVENTORIES$26);
        }
    }

    /**
     * Sets ith "inventories" element
     */
    public void setInventoriesArray(int i,
        com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory inventories) {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory) get_store()
                                                                             .find_element_user(INVENTORIES$26,
                    i);

            if (target == null) {
                throw new IndexOutOfBoundsException();
            }

            target.set(inventories);
        }
    }

    /**
     * Nils the ith "inventories" element
     */
    public void setNilInventoriesArray(int i) {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory) get_store()
                                                                             .find_element_user(INVENTORIES$26,
                    i);

            if (target == null) {
                throw new IndexOutOfBoundsException();
            }

            target.setNil();
        }
    }

    /**
     * Inserts and returns a new empty value (as xml) as the ith "inventories" element
     */
    public com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory insertNewInventories(
        int i) {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory) get_store()
                                                                             .insert_element_user(INVENTORIES$26,
                    i);

            return target;
        }
    }

    /**
     * Appends and returns a new empty value (as xml) as the last "inventories" element
     */
    public com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory addNewInventories() {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSInventory) get_store()
                                                                             .add_element_user(INVENTORIES$26);

            return target;
        }
    }

    /**
     * Removes the ith "inventories" element
     */
    public void removeInventories(int i) {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(INVENTORIES$26, i);
        }
    }

    /**
     * Gets the "itemtype" element
     */
    public java.lang.String getItemtype() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(ITEMTYPE$28,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "itemtype" element
     */
    public org.apache.xmlbeans.XmlString xgetItemtype() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(ITEMTYPE$28,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "itemtype" element
     */
    public boolean isNilItemtype() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(ITEMTYPE$28,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "itemtype" element
     */
    public boolean isSetItemtype() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(ITEMTYPE$28) != 0;
        }
    }

    /**
     * Sets the "itemtype" element
     */
    public void setItemtype(java.lang.String itemtype) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(ITEMTYPE$28,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(ITEMTYPE$28);
            }

            target.setStringValue(itemtype);
        }
    }

    /**
     * Sets (as xml) the "itemtype" element
     */
    public void xsetItemtype(org.apache.xmlbeans.XmlString itemtype) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(ITEMTYPE$28,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(ITEMTYPE$28);
            }

            target.set(itemtype);
        }
    }

    /**
     * Nils the "itemtype" element
     */
    public void setNilItemtype() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(ITEMTYPE$28,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(ITEMTYPE$28);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "itemtype" element
     */
    public void unsetItemtype() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(ITEMTYPE$28, 0);
        }
    }

    /**
     * Gets the "lnameonbag" element
     */
    public java.lang.String getLnameonbag() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(LNAMEONBAG$30,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "lnameonbag" element
     */
    public org.apache.xmlbeans.XmlString xgetLnameonbag() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(LNAMEONBAG$30,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "lnameonbag" element
     */
    public boolean isNilLnameonbag() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(LNAMEONBAG$30,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "lnameonbag" element
     */
    public boolean isSetLnameonbag() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(LNAMEONBAG$30) != 0;
        }
    }

    /**
     * Sets the "lnameonbag" element
     */
    public void setLnameonbag(java.lang.String lnameonbag) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(LNAMEONBAG$30,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(LNAMEONBAG$30);
            }

            target.setStringValue(lnameonbag);
        }
    }

    /**
     * Sets (as xml) the "lnameonbag" element
     */
    public void xsetLnameonbag(org.apache.xmlbeans.XmlString lnameonbag) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(LNAMEONBAG$30,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(LNAMEONBAG$30);
            }

            target.set(lnameonbag);
        }
    }

    /**
     * Nils the "lnameonbag" element
     */
    public void setNilLnameonbag() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(LNAMEONBAG$30,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(LNAMEONBAG$30);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "lnameonbag" element
     */
    public void unsetLnameonbag() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(LNAMEONBAG$30, 0);
        }
    }

    /**
     * Gets the "lvlofdamage" element
     */
    public int getLvlofdamage() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(LVLOFDAMAGE$32,
                    0);

            if (target == null) {
                return 0;
            }

            return target.getIntValue();
        }
    }

    /**
     * Gets (as xml) the "lvlofdamage" element
     */
    public org.apache.xmlbeans.XmlInt xgetLvlofdamage() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt) get_store()
                                                      .find_element_user(LVLOFDAMAGE$32,
                    0);

            return target;
        }
    }

    /**
     * True if has "lvlofdamage" element
     */
    public boolean isSetLvlofdamage() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(LVLOFDAMAGE$32) != 0;
        }
    }

    /**
     * Sets the "lvlofdamage" element
     */
    public void setLvlofdamage(int lvlofdamage) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(LVLOFDAMAGE$32,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(LVLOFDAMAGE$32);
            }

            target.setIntValue(lvlofdamage);
        }
    }

    /**
     * Sets (as xml) the "lvlofdamage" element
     */
    public void xsetLvlofdamage(org.apache.xmlbeans.XmlInt lvlofdamage) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt) get_store()
                                                      .find_element_user(LVLOFDAMAGE$32,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlInt) get_store()
                                                          .add_element_user(LVLOFDAMAGE$32);
            }

            target.set(lvlofdamage);
        }
    }

    /**
     * Unsets the "lvlofdamage" element
     */
    public void unsetLvlofdamage() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(LVLOFDAMAGE$32, 0);
        }
    }

    /**
     * Gets the "manufacturer" element
     */
    public java.lang.String getManufacturer() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(MANUFACTURER$34,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "manufacturer" element
     */
    public org.apache.xmlbeans.XmlString xgetManufacturer() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(MANUFACTURER$34,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "manufacturer" element
     */
    public boolean isNilManufacturer() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(MANUFACTURER$34,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "manufacturer" element
     */
    public boolean isSetManufacturer() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(MANUFACTURER$34) != 0;
        }
    }

    /**
     * Sets the "manufacturer" element
     */
    public void setManufacturer(java.lang.String manufacturer) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(MANUFACTURER$34,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(MANUFACTURER$34);
            }

            target.setStringValue(manufacturer);
        }
    }

    /**
     * Sets (as xml) the "manufacturer" element
     */
    public void xsetManufacturer(org.apache.xmlbeans.XmlString manufacturer) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(MANUFACTURER$34,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(MANUFACTURER$34);
            }

            target.set(manufacturer);
        }
    }

    /**
     * Nils the "manufacturer" element
     */
    public void setNilManufacturer() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(MANUFACTURER$34,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(MANUFACTURER$34);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "manufacturer" element
     */
    public void unsetManufacturer() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(MANUFACTURER$34, 0);
        }
    }

    /**
     * Gets the "matched_ohd" element
     */
    public java.lang.String getMatchedOhd() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(MATCHEDOHD$36,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "matched_ohd" element
     */
    public org.apache.xmlbeans.XmlString xgetMatchedOhd() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(MATCHEDOHD$36,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "matched_ohd" element
     */
    public boolean isNilMatchedOhd() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(MATCHEDOHD$36,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "matched_ohd" element
     */
    public boolean isSetMatchedOhd() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(MATCHEDOHD$36) != 0;
        }
    }

    /**
     * Sets the "matched_ohd" element
     */
    public void setMatchedOhd(java.lang.String matchedOhd) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(MATCHEDOHD$36,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(MATCHEDOHD$36);
            }

            target.setStringValue(matchedOhd);
        }
    }

    /**
     * Sets (as xml) the "matched_ohd" element
     */
    public void xsetMatchedOhd(org.apache.xmlbeans.XmlString matchedOhd) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(MATCHEDOHD$36,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(MATCHEDOHD$36);
            }

            target.set(matchedOhd);
        }
    }

    /**
     * Nils the "matched_ohd" element
     */
    public void setNilMatchedOhd() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(MATCHEDOHD$36,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(MATCHEDOHD$36);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "matched_ohd" element
     */
    public void unsetMatchedOhd() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(MATCHEDOHD$36, 0);
        }
    }

    /**
     * Gets the "mnameonbag" element
     */
    public java.lang.String getMnameonbag() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(MNAMEONBAG$38,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "mnameonbag" element
     */
    public org.apache.xmlbeans.XmlString xgetMnameonbag() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(MNAMEONBAG$38,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "mnameonbag" element
     */
    public boolean isNilMnameonbag() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(MNAMEONBAG$38,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "mnameonbag" element
     */
    public boolean isSetMnameonbag() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(MNAMEONBAG$38) != 0;
        }
    }

    /**
     * Sets the "mnameonbag" element
     */
    public void setMnameonbag(java.lang.String mnameonbag) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(MNAMEONBAG$38,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(MNAMEONBAG$38);
            }

            target.setStringValue(mnameonbag);
        }
    }

    /**
     * Sets (as xml) the "mnameonbag" element
     */
    public void xsetMnameonbag(org.apache.xmlbeans.XmlString mnameonbag) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(MNAMEONBAG$38,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(MNAMEONBAG$38);
            }

            target.set(mnameonbag);
        }
    }

    /**
     * Nils the "mnameonbag" element
     */
    public void setNilMnameonbag() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(MNAMEONBAG$38,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(MNAMEONBAG$38);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "mnameonbag" element
     */
    public void unsetMnameonbag() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(MNAMEONBAG$38, 0);
        }
    }

    /**
     * Gets the "resolutiondesc" element
     */
    public java.lang.String getResolutiondesc() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(RESOLUTIONDESC$40,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "resolutiondesc" element
     */
    public org.apache.xmlbeans.XmlString xgetResolutiondesc() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(RESOLUTIONDESC$40,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "resolutiondesc" element
     */
    public boolean isNilResolutiondesc() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(RESOLUTIONDESC$40,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "resolutiondesc" element
     */
    public boolean isSetResolutiondesc() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(RESOLUTIONDESC$40) != 0;
        }
    }

    /**
     * Sets the "resolutiondesc" element
     */
    public void setResolutiondesc(java.lang.String resolutiondesc) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(RESOLUTIONDESC$40,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(RESOLUTIONDESC$40);
            }

            target.setStringValue(resolutiondesc);
        }
    }

    /**
     * Sets (as xml) the "resolutiondesc" element
     */
    public void xsetResolutiondesc(org.apache.xmlbeans.XmlString resolutiondesc) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(RESOLUTIONDESC$40,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(RESOLUTIONDESC$40);
            }

            target.set(resolutiondesc);
        }
    }

    /**
     * Nils the "resolutiondesc" element
     */
    public void setNilResolutiondesc() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(RESOLUTIONDESC$40,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(RESOLUTIONDESC$40);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "resolutiondesc" element
     */
    public void unsetResolutiondesc() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(RESOLUTIONDESC$40, 0);
        }
    }

    /**
     * Gets the "resolutionstatus" element
     */
    public java.lang.String getResolutionstatus() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(RESOLUTIONSTATUS$42,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "resolutionstatus" element
     */
    public org.apache.xmlbeans.XmlString xgetResolutionstatus() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(RESOLUTIONSTATUS$42,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "resolutionstatus" element
     */
    public boolean isNilResolutionstatus() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(RESOLUTIONSTATUS$42,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "resolutionstatus" element
     */
    public boolean isSetResolutionstatus() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(RESOLUTIONSTATUS$42) != 0;
        }
    }

    /**
     * Sets the "resolutionstatus" element
     */
    public void setResolutionstatus(java.lang.String resolutionstatus) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(RESOLUTIONSTATUS$42,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(RESOLUTIONSTATUS$42);
            }

            target.setStringValue(resolutionstatus);
        }
    }

    /**
     * Sets (as xml) the "resolutionstatus" element
     */
    public void xsetResolutionstatus(
        org.apache.xmlbeans.XmlString resolutionstatus) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(RESOLUTIONSTATUS$42,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(RESOLUTIONSTATUS$42);
            }

            target.set(resolutionstatus);
        }
    }

    /**
     * Nils the "resolutionstatus" element
     */
    public void setNilResolutionstatus() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(RESOLUTIONSTATUS$42,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(RESOLUTIONSTATUS$42);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "resolutionstatus" element
     */
    public void unsetResolutionstatus() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(RESOLUTIONSTATUS$42, 0);
        }
    }

    /**
     * Gets the "xdescelement_1" element
     */
    public java.lang.String getXdescelement1() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(XDESCELEMENT1$44,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "xdescelement_1" element
     */
    public org.apache.xmlbeans.XmlString xgetXdescelement1() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(XDESCELEMENT1$44,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "xdescelement_1" element
     */
    public boolean isNilXdescelement1() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(XDESCELEMENT1$44,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "xdescelement_1" element
     */
    public boolean isSetXdescelement1() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(XDESCELEMENT1$44) != 0;
        }
    }

    /**
     * Sets the "xdescelement_1" element
     */
    public void setXdescelement1(java.lang.String xdescelement1) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(XDESCELEMENT1$44,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(XDESCELEMENT1$44);
            }

            target.setStringValue(xdescelement1);
        }
    }

    /**
     * Sets (as xml) the "xdescelement_1" element
     */
    public void xsetXdescelement1(org.apache.xmlbeans.XmlString xdescelement1) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(XDESCELEMENT1$44,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(XDESCELEMENT1$44);
            }

            target.set(xdescelement1);
        }
    }

    /**
     * Nils the "xdescelement_1" element
     */
    public void setNilXdescelement1() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(XDESCELEMENT1$44,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(XDESCELEMENT1$44);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "xdescelement_1" element
     */
    public void unsetXdescelement1() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(XDESCELEMENT1$44, 0);
        }
    }

    /**
     * Gets the "xdescelement_2" element
     */
    public java.lang.String getXdescelement2() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(XDESCELEMENT2$46,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "xdescelement_2" element
     */
    public org.apache.xmlbeans.XmlString xgetXdescelement2() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(XDESCELEMENT2$46,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "xdescelement_2" element
     */
    public boolean isNilXdescelement2() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(XDESCELEMENT2$46,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "xdescelement_2" element
     */
    public boolean isSetXdescelement2() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(XDESCELEMENT2$46) != 0;
        }
    }

    /**
     * Sets the "xdescelement_2" element
     */
    public void setXdescelement2(java.lang.String xdescelement2) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(XDESCELEMENT2$46,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(XDESCELEMENT2$46);
            }

            target.setStringValue(xdescelement2);
        }
    }

    /**
     * Sets (as xml) the "xdescelement_2" element
     */
    public void xsetXdescelement2(org.apache.xmlbeans.XmlString xdescelement2) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(XDESCELEMENT2$46,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(XDESCELEMENT2$46);
            }

            target.set(xdescelement2);
        }
    }

    /**
     * Nils the "xdescelement_2" element
     */
    public void setNilXdescelement2() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(XDESCELEMENT2$46,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(XDESCELEMENT2$46);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "xdescelement_2" element
     */
    public void unsetXdescelement2() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(XDESCELEMENT2$46, 0);
        }
    }

    /**
     * Gets the "xdescelement_3" element
     */
    public java.lang.String getXdescelement3() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(XDESCELEMENT3$48,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "xdescelement_3" element
     */
    public org.apache.xmlbeans.XmlString xgetXdescelement3() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(XDESCELEMENT3$48,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "xdescelement_3" element
     */
    public boolean isNilXdescelement3() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(XDESCELEMENT3$48,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "xdescelement_3" element
     */
    public boolean isSetXdescelement3() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(XDESCELEMENT3$48) != 0;
        }
    }

    /**
     * Sets the "xdescelement_3" element
     */
    public void setXdescelement3(java.lang.String xdescelement3) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(XDESCELEMENT3$48,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(XDESCELEMENT3$48);
            }

            target.setStringValue(xdescelement3);
        }
    }

    /**
     * Sets (as xml) the "xdescelement_3" element
     */
    public void xsetXdescelement3(org.apache.xmlbeans.XmlString xdescelement3) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(XDESCELEMENT3$48,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(XDESCELEMENT3$48);
            }

            target.set(xdescelement3);
        }
    }

    /**
     * Nils the "xdescelement_3" element
     */
    public void setNilXdescelement3() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(XDESCELEMENT3$48,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(XDESCELEMENT3$48);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "xdescelement_3" element
     */
    public void unsetXdescelement3() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(XDESCELEMENT3$48, 0);
        }
    }
}
