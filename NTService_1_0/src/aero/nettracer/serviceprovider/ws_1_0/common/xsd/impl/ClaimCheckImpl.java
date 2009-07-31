/*
 * XML Type:  ClaimCheck
 * Namespace: http://common.ws_1_0.serviceprovider.nettracer.aero/xsd
 * Java type: aero.nettracer.serviceprovider.ws_1_0.common.xsd.ClaimCheck
 *
 * Automatically generated - do not modify.
 */
package aero.nettracer.serviceprovider.ws_1_0.common.xsd.impl;

/**
 * An XML ClaimCheck(@http://common.ws_1_0.serviceprovider.nettracer.aero/xsd).
 *
 * This is a complex type.
 */
public class ClaimCheckImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
    implements aero.nettracer.serviceprovider.ws_1_0.common.xsd.ClaimCheck {
    private static final javax.xml.namespace.QName AIRLINE$0 = new javax.xml.namespace.QName("http://common.ws_1_0.serviceprovider.nettracer.aero/xsd",
            "airline");
    private static final javax.xml.namespace.QName TAGNUMBER$2 = new javax.xml.namespace.QName("http://common.ws_1_0.serviceprovider.nettracer.aero/xsd",
            "tagNumber");
    private static final javax.xml.namespace.QName TIMECHECKED$4 = new javax.xml.namespace.QName("http://common.ws_1_0.serviceprovider.nettracer.aero/xsd",
            "timeChecked");

    public ClaimCheckImpl(org.apache.xmlbeans.SchemaType sType) {
        super(sType);
    }

    /**
     * Gets the "airline" element
     */
    public java.lang.String getAirline() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(AIRLINE$0,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "airline" element
     */
    public org.apache.xmlbeans.XmlString xgetAirline() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(AIRLINE$0,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "airline" element
     */
    public boolean isNilAirline() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(AIRLINE$0,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "airline" element
     */
    public boolean isSetAirline() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(AIRLINE$0) != 0;
        }
    }

    /**
     * Sets the "airline" element
     */
    public void setAirline(java.lang.String airline) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(AIRLINE$0,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(AIRLINE$0);
            }

            target.setStringValue(airline);
        }
    }

    /**
     * Sets (as xml) the "airline" element
     */
    public void xsetAirline(org.apache.xmlbeans.XmlString airline) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(AIRLINE$0,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(AIRLINE$0);
            }

            target.set(airline);
        }
    }

    /**
     * Nils the "airline" element
     */
    public void setNilAirline() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(AIRLINE$0,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(AIRLINE$0);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "airline" element
     */
    public void unsetAirline() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(AIRLINE$0, 0);
        }
    }

    /**
     * Gets the "tagNumber" element
     */
    public java.lang.String getTagNumber() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(TAGNUMBER$2,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "tagNumber" element
     */
    public org.apache.xmlbeans.XmlString xgetTagNumber() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(TAGNUMBER$2,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "tagNumber" element
     */
    public boolean isNilTagNumber() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(TAGNUMBER$2,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "tagNumber" element
     */
    public boolean isSetTagNumber() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(TAGNUMBER$2) != 0;
        }
    }

    /**
     * Sets the "tagNumber" element
     */
    public void setTagNumber(java.lang.String tagNumber) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(TAGNUMBER$2,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(TAGNUMBER$2);
            }

            target.setStringValue(tagNumber);
        }
    }

    /**
     * Sets (as xml) the "tagNumber" element
     */
    public void xsetTagNumber(org.apache.xmlbeans.XmlString tagNumber) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(TAGNUMBER$2,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(TAGNUMBER$2);
            }

            target.set(tagNumber);
        }
    }

    /**
     * Nils the "tagNumber" element
     */
    public void setNilTagNumber() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(TAGNUMBER$2,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(TAGNUMBER$2);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "tagNumber" element
     */
    public void unsetTagNumber() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(TAGNUMBER$2, 0);
        }
    }

    /**
     * Gets the "timeChecked" element
     */
    public java.util.Calendar getTimeChecked() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(TIMECHECKED$4,
                    0);

            if (target == null) {
                return null;
            }

            return target.getCalendarValue();
        }
    }

    /**
     * Gets (as xml) the "timeChecked" element
     */
    public org.apache.xmlbeans.XmlDateTime xgetTimeChecked() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime) get_store()
                                                           .find_element_user(TIMECHECKED$4,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "timeChecked" element
     */
    public boolean isNilTimeChecked() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime) get_store()
                                                           .find_element_user(TIMECHECKED$4,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "timeChecked" element
     */
    public boolean isSetTimeChecked() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(TIMECHECKED$4) != 0;
        }
    }

    /**
     * Sets the "timeChecked" element
     */
    public void setTimeChecked(java.util.Calendar timeChecked) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(TIMECHECKED$4,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(TIMECHECKED$4);
            }

            target.setCalendarValue(timeChecked);
        }
    }

    /**
     * Sets (as xml) the "timeChecked" element
     */
    public void xsetTimeChecked(org.apache.xmlbeans.XmlDateTime timeChecked) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime) get_store()
                                                           .find_element_user(TIMECHECKED$4,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlDateTime) get_store()
                                                               .add_element_user(TIMECHECKED$4);
            }

            target.set(timeChecked);
        }
    }

    /**
     * Nils the "timeChecked" element
     */
    public void setNilTimeChecked() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime) get_store()
                                                           .find_element_user(TIMECHECKED$4,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlDateTime) get_store()
                                                               .add_element_user(TIMECHECKED$4);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "timeChecked" element
     */
    public void unsetTimeChecked() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(TIMECHECKED$4, 0);
        }
    }
}
