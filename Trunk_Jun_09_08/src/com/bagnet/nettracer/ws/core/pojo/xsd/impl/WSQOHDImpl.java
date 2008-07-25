/*
 * XML Type:  WS_QOHD
 * Namespace: http://pojo.core.ws.nettracer.bagnet.com/xsd
 * Java type: com.bagnet.nettracer.ws.core.pojo.xsd.WSQOHD
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.core.pojo.xsd.impl;

/**
 * An XML WS_QOHD(@http://pojo.core.ws.nettracer.bagnet.com/xsd).
 *
 * This is a complex type.
 */
public class WSQOHDImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
    implements com.bagnet.nettracer.ws.core.pojo.xsd.WSQOHD {
    private static final javax.xml.namespace.QName BAGTAGNUMBER$0 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "bagTagNumber");
    private static final javax.xml.namespace.QName FOUNDATSTATION$2 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "foundAtStation");
    private static final javax.xml.namespace.QName FOUNDDATETIME$4 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "founddatetime");

    public WSQOHDImpl(org.apache.xmlbeans.SchemaType sType) {
        super(sType);
    }

    /**
     * Gets the "bagTagNumber" element
     */
    public java.lang.String getBagTagNumber() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(BAGTAGNUMBER$0,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "bagTagNumber" element
     */
    public org.apache.xmlbeans.XmlString xgetBagTagNumber() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(BAGTAGNUMBER$0,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "bagTagNumber" element
     */
    public boolean isNilBagTagNumber() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(BAGTAGNUMBER$0,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "bagTagNumber" element
     */
    public boolean isSetBagTagNumber() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(BAGTAGNUMBER$0) != 0;
        }
    }

    /**
     * Sets the "bagTagNumber" element
     */
    public void setBagTagNumber(java.lang.String bagTagNumber) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(BAGTAGNUMBER$0,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(BAGTAGNUMBER$0);
            }

            target.setStringValue(bagTagNumber);
        }
    }

    /**
     * Sets (as xml) the "bagTagNumber" element
     */
    public void xsetBagTagNumber(org.apache.xmlbeans.XmlString bagTagNumber) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(BAGTAGNUMBER$0,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(BAGTAGNUMBER$0);
            }

            target.set(bagTagNumber);
        }
    }

    /**
     * Nils the "bagTagNumber" element
     */
    public void setNilBagTagNumber() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(BAGTAGNUMBER$0,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(BAGTAGNUMBER$0);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "bagTagNumber" element
     */
    public void unsetBagTagNumber() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(BAGTAGNUMBER$0, 0);
        }
    }

    /**
     * Gets the "foundAtStation" element
     */
    public java.lang.String getFoundAtStation() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(FOUNDATSTATION$2,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "foundAtStation" element
     */
    public org.apache.xmlbeans.XmlString xgetFoundAtStation() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(FOUNDATSTATION$2,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "foundAtStation" element
     */
    public boolean isNilFoundAtStation() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(FOUNDATSTATION$2,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "foundAtStation" element
     */
    public boolean isSetFoundAtStation() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(FOUNDATSTATION$2) != 0;
        }
    }

    /**
     * Sets the "foundAtStation" element
     */
    public void setFoundAtStation(java.lang.String foundAtStation) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(FOUNDATSTATION$2,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(FOUNDATSTATION$2);
            }

            target.setStringValue(foundAtStation);
        }
    }

    /**
     * Sets (as xml) the "foundAtStation" element
     */
    public void xsetFoundAtStation(org.apache.xmlbeans.XmlString foundAtStation) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(FOUNDATSTATION$2,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(FOUNDATSTATION$2);
            }

            target.set(foundAtStation);
        }
    }

    /**
     * Nils the "foundAtStation" element
     */
    public void setNilFoundAtStation() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(FOUNDATSTATION$2,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(FOUNDATSTATION$2);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "foundAtStation" element
     */
    public void unsetFoundAtStation() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(FOUNDATSTATION$2, 0);
        }
    }

    /**
     * Gets the "founddatetime" element
     */
    public java.util.Calendar getFounddatetime() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(FOUNDDATETIME$4,
                    0);

            if (target == null) {
                return null;
            }

            return target.getCalendarValue();
        }
    }

    /**
     * Gets (as xml) the "founddatetime" element
     */
    public org.apache.xmlbeans.XmlDateTime xgetFounddatetime() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime) get_store()
                                                           .find_element_user(FOUNDDATETIME$4,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "founddatetime" element
     */
    public boolean isNilFounddatetime() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime) get_store()
                                                           .find_element_user(FOUNDDATETIME$4,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "founddatetime" element
     */
    public boolean isSetFounddatetime() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(FOUNDDATETIME$4) != 0;
        }
    }

    /**
     * Sets the "founddatetime" element
     */
    public void setFounddatetime(java.util.Calendar founddatetime) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(FOUNDDATETIME$4,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(FOUNDDATETIME$4);
            }

            target.setCalendarValue(founddatetime);
        }
    }

    /**
     * Sets (as xml) the "founddatetime" element
     */
    public void xsetFounddatetime(org.apache.xmlbeans.XmlDateTime founddatetime) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime) get_store()
                                                           .find_element_user(FOUNDDATETIME$4,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlDateTime) get_store()
                                                               .add_element_user(FOUNDDATETIME$4);
            }

            target.set(founddatetime);
        }
    }

    /**
     * Nils the "founddatetime" element
     */
    public void setNilFounddatetime() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime) get_store()
                                                           .find_element_user(FOUNDDATETIME$4,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlDateTime) get_store()
                                                               .add_element_user(FOUNDDATETIME$4);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "founddatetime" element
     */
    public void unsetFounddatetime() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(FOUNDDATETIME$4, 0);
        }
    }
}
