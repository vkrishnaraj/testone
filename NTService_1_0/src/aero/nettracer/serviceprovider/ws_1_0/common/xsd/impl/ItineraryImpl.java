/*
 * XML Type:  Itinerary
 * Namespace: http://common.ws_1_0.serviceprovider.nettracer.aero/xsd
 * Java type: aero.nettracer.serviceprovider.ws_1_0.common.xsd.Itinerary
 *
 * Automatically generated - do not modify.
 */
package aero.nettracer.serviceprovider.ws_1_0.common.xsd.impl;

/**
 * An XML Itinerary(@http://common.ws_1_0.serviceprovider.nettracer.aero/xsd).
 *
 * This is a complex type.
 */
public class ItineraryImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
    implements aero.nettracer.serviceprovider.ws_1_0.common.xsd.Itinerary {
    private static final javax.xml.namespace.QName ACTARRIVETIME$0 = new javax.xml.namespace.QName("http://common.ws_1_0.serviceprovider.nettracer.aero/xsd",
            "actarrivetime");
    private static final javax.xml.namespace.QName ACTARRIVETIMEGMT$2 = new javax.xml.namespace.QName("http://common.ws_1_0.serviceprovider.nettracer.aero/xsd",
            "actarrivetimeGmt");
    private static final javax.xml.namespace.QName ACTDEPARTTIME$4 = new javax.xml.namespace.QName("http://common.ws_1_0.serviceprovider.nettracer.aero/xsd",
            "actdeparttime");
    private static final javax.xml.namespace.QName ACTDEPARTTIMEGMT$6 = new javax.xml.namespace.QName("http://common.ws_1_0.serviceprovider.nettracer.aero/xsd",
            "actdeparttimeGmt");
    private static final javax.xml.namespace.QName AIRLINE$8 = new javax.xml.namespace.QName("http://common.ws_1_0.serviceprovider.nettracer.aero/xsd",
            "airline");
    private static final javax.xml.namespace.QName ARRIVALCITY$10 = new javax.xml.namespace.QName("http://common.ws_1_0.serviceprovider.nettracer.aero/xsd",
            "arrivalCity");
    private static final javax.xml.namespace.QName ARRIVALUNKNOWN$12 = new javax.xml.namespace.QName("http://common.ws_1_0.serviceprovider.nettracer.aero/xsd",
            "arrivalUnknown");
    private static final javax.xml.namespace.QName DEPARTURECITY$14 = new javax.xml.namespace.QName("http://common.ws_1_0.serviceprovider.nettracer.aero/xsd",
            "departureCity");
    private static final javax.xml.namespace.QName FLIGHTNUM$16 = new javax.xml.namespace.QName("http://common.ws_1_0.serviceprovider.nettracer.aero/xsd",
            "flightnum");
    private static final javax.xml.namespace.QName SCHARRIVETIME$18 = new javax.xml.namespace.QName("http://common.ws_1_0.serviceprovider.nettracer.aero/xsd",
            "scharrivetime");
    private static final javax.xml.namespace.QName SCHARRIVETIMEGMT$20 = new javax.xml.namespace.QName("http://common.ws_1_0.serviceprovider.nettracer.aero/xsd",
            "scharrivetimeGmt");
    private static final javax.xml.namespace.QName SCHDEPARTTIME$22 = new javax.xml.namespace.QName("http://common.ws_1_0.serviceprovider.nettracer.aero/xsd",
            "schdeparttime");
    private static final javax.xml.namespace.QName SCHDEPARTTIMEGMT$24 = new javax.xml.namespace.QName("http://common.ws_1_0.serviceprovider.nettracer.aero/xsd",
            "schdeparttimeGmt");
    private static final javax.xml.namespace.QName TIMECHECKED$26 = new javax.xml.namespace.QName("http://common.ws_1_0.serviceprovider.nettracer.aero/xsd",
            "timeChecked");

    public ItineraryImpl(org.apache.xmlbeans.SchemaType sType) {
        super(sType);
    }

    /**
     * Gets the "actarrivetime" element
     */
    public java.util.Calendar getActarrivetime() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(ACTARRIVETIME$0,
                    0);

            if (target == null) {
                return null;
            }

            return target.getCalendarValue();
        }
    }

    /**
     * Gets (as xml) the "actarrivetime" element
     */
    public org.apache.xmlbeans.XmlDateTime xgetActarrivetime() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime) get_store()
                                                           .find_element_user(ACTARRIVETIME$0,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "actarrivetime" element
     */
    public boolean isNilActarrivetime() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime) get_store()
                                                           .find_element_user(ACTARRIVETIME$0,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "actarrivetime" element
     */
    public boolean isSetActarrivetime() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(ACTARRIVETIME$0) != 0;
        }
    }

    /**
     * Sets the "actarrivetime" element
     */
    public void setActarrivetime(java.util.Calendar actarrivetime) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(ACTARRIVETIME$0,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(ACTARRIVETIME$0);
            }

            target.setCalendarValue(actarrivetime);
        }
    }

    /**
     * Sets (as xml) the "actarrivetime" element
     */
    public void xsetActarrivetime(org.apache.xmlbeans.XmlDateTime actarrivetime) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime) get_store()
                                                           .find_element_user(ACTARRIVETIME$0,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlDateTime) get_store()
                                                               .add_element_user(ACTARRIVETIME$0);
            }

            target.set(actarrivetime);
        }
    }

    /**
     * Nils the "actarrivetime" element
     */
    public void setNilActarrivetime() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime) get_store()
                                                           .find_element_user(ACTARRIVETIME$0,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlDateTime) get_store()
                                                               .add_element_user(ACTARRIVETIME$0);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "actarrivetime" element
     */
    public void unsetActarrivetime() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(ACTARRIVETIME$0, 0);
        }
    }

    /**
     * Gets the "actarrivetimeGmt" element
     */
    public java.util.Calendar getActarrivetimeGmt() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(ACTARRIVETIMEGMT$2,
                    0);

            if (target == null) {
                return null;
            }

            return target.getCalendarValue();
        }
    }

    /**
     * Gets (as xml) the "actarrivetimeGmt" element
     */
    public org.apache.xmlbeans.XmlDateTime xgetActarrivetimeGmt() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime) get_store()
                                                           .find_element_user(ACTARRIVETIMEGMT$2,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "actarrivetimeGmt" element
     */
    public boolean isNilActarrivetimeGmt() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime) get_store()
                                                           .find_element_user(ACTARRIVETIMEGMT$2,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "actarrivetimeGmt" element
     */
    public boolean isSetActarrivetimeGmt() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(ACTARRIVETIMEGMT$2) != 0;
        }
    }

    /**
     * Sets the "actarrivetimeGmt" element
     */
    public void setActarrivetimeGmt(java.util.Calendar actarrivetimeGmt) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(ACTARRIVETIMEGMT$2,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(ACTARRIVETIMEGMT$2);
            }

            target.setCalendarValue(actarrivetimeGmt);
        }
    }

    /**
     * Sets (as xml) the "actarrivetimeGmt" element
     */
    public void xsetActarrivetimeGmt(
        org.apache.xmlbeans.XmlDateTime actarrivetimeGmt) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime) get_store()
                                                           .find_element_user(ACTARRIVETIMEGMT$2,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlDateTime) get_store()
                                                               .add_element_user(ACTARRIVETIMEGMT$2);
            }

            target.set(actarrivetimeGmt);
        }
    }

    /**
     * Nils the "actarrivetimeGmt" element
     */
    public void setNilActarrivetimeGmt() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime) get_store()
                                                           .find_element_user(ACTARRIVETIMEGMT$2,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlDateTime) get_store()
                                                               .add_element_user(ACTARRIVETIMEGMT$2);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "actarrivetimeGmt" element
     */
    public void unsetActarrivetimeGmt() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(ACTARRIVETIMEGMT$2, 0);
        }
    }

    /**
     * Gets the "actdeparttime" element
     */
    public java.util.Calendar getActdeparttime() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(ACTDEPARTTIME$4,
                    0);

            if (target == null) {
                return null;
            }

            return target.getCalendarValue();
        }
    }

    /**
     * Gets (as xml) the "actdeparttime" element
     */
    public org.apache.xmlbeans.XmlDateTime xgetActdeparttime() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime) get_store()
                                                           .find_element_user(ACTDEPARTTIME$4,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "actdeparttime" element
     */
    public boolean isNilActdeparttime() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime) get_store()
                                                           .find_element_user(ACTDEPARTTIME$4,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "actdeparttime" element
     */
    public boolean isSetActdeparttime() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(ACTDEPARTTIME$4) != 0;
        }
    }

    /**
     * Sets the "actdeparttime" element
     */
    public void setActdeparttime(java.util.Calendar actdeparttime) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(ACTDEPARTTIME$4,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(ACTDEPARTTIME$4);
            }

            target.setCalendarValue(actdeparttime);
        }
    }

    /**
     * Sets (as xml) the "actdeparttime" element
     */
    public void xsetActdeparttime(org.apache.xmlbeans.XmlDateTime actdeparttime) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime) get_store()
                                                           .find_element_user(ACTDEPARTTIME$4,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlDateTime) get_store()
                                                               .add_element_user(ACTDEPARTTIME$4);
            }

            target.set(actdeparttime);
        }
    }

    /**
     * Nils the "actdeparttime" element
     */
    public void setNilActdeparttime() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime) get_store()
                                                           .find_element_user(ACTDEPARTTIME$4,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlDateTime) get_store()
                                                               .add_element_user(ACTDEPARTTIME$4);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "actdeparttime" element
     */
    public void unsetActdeparttime() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(ACTDEPARTTIME$4, 0);
        }
    }

    /**
     * Gets the "actdeparttimeGmt" element
     */
    public java.util.Calendar getActdeparttimeGmt() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(ACTDEPARTTIMEGMT$6,
                    0);

            if (target == null) {
                return null;
            }

            return target.getCalendarValue();
        }
    }

    /**
     * Gets (as xml) the "actdeparttimeGmt" element
     */
    public org.apache.xmlbeans.XmlDateTime xgetActdeparttimeGmt() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime) get_store()
                                                           .find_element_user(ACTDEPARTTIMEGMT$6,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "actdeparttimeGmt" element
     */
    public boolean isNilActdeparttimeGmt() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime) get_store()
                                                           .find_element_user(ACTDEPARTTIMEGMT$6,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "actdeparttimeGmt" element
     */
    public boolean isSetActdeparttimeGmt() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(ACTDEPARTTIMEGMT$6) != 0;
        }
    }

    /**
     * Sets the "actdeparttimeGmt" element
     */
    public void setActdeparttimeGmt(java.util.Calendar actdeparttimeGmt) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(ACTDEPARTTIMEGMT$6,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(ACTDEPARTTIMEGMT$6);
            }

            target.setCalendarValue(actdeparttimeGmt);
        }
    }

    /**
     * Sets (as xml) the "actdeparttimeGmt" element
     */
    public void xsetActdeparttimeGmt(
        org.apache.xmlbeans.XmlDateTime actdeparttimeGmt) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime) get_store()
                                                           .find_element_user(ACTDEPARTTIMEGMT$6,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlDateTime) get_store()
                                                               .add_element_user(ACTDEPARTTIMEGMT$6);
            }

            target.set(actdeparttimeGmt);
        }
    }

    /**
     * Nils the "actdeparttimeGmt" element
     */
    public void setNilActdeparttimeGmt() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime) get_store()
                                                           .find_element_user(ACTDEPARTTIMEGMT$6,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlDateTime) get_store()
                                                               .add_element_user(ACTDEPARTTIMEGMT$6);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "actdeparttimeGmt" element
     */
    public void unsetActdeparttimeGmt() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(ACTDEPARTTIMEGMT$6, 0);
        }
    }

    /**
     * Gets the "airline" element
     */
    public java.lang.String getAirline() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(AIRLINE$8,
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
                                                         .find_element_user(AIRLINE$8,
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
                                                         .find_element_user(AIRLINE$8,
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

            return get_store().count_elements(AIRLINE$8) != 0;
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
                                                           .find_element_user(AIRLINE$8,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(AIRLINE$8);
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
                                                         .find_element_user(AIRLINE$8,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(AIRLINE$8);
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
                                                         .find_element_user(AIRLINE$8,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(AIRLINE$8);
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
            get_store().remove_element(AIRLINE$8, 0);
        }
    }

    /**
     * Gets the "arrivalCity" element
     */
    public java.lang.String getArrivalCity() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(ARRIVALCITY$10,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "arrivalCity" element
     */
    public org.apache.xmlbeans.XmlString xgetArrivalCity() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(ARRIVALCITY$10,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "arrivalCity" element
     */
    public boolean isNilArrivalCity() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(ARRIVALCITY$10,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "arrivalCity" element
     */
    public boolean isSetArrivalCity() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(ARRIVALCITY$10) != 0;
        }
    }

    /**
     * Sets the "arrivalCity" element
     */
    public void setArrivalCity(java.lang.String arrivalCity) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(ARRIVALCITY$10,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(ARRIVALCITY$10);
            }

            target.setStringValue(arrivalCity);
        }
    }

    /**
     * Sets (as xml) the "arrivalCity" element
     */
    public void xsetArrivalCity(org.apache.xmlbeans.XmlString arrivalCity) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(ARRIVALCITY$10,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(ARRIVALCITY$10);
            }

            target.set(arrivalCity);
        }
    }

    /**
     * Nils the "arrivalCity" element
     */
    public void setNilArrivalCity() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(ARRIVALCITY$10,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(ARRIVALCITY$10);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "arrivalCity" element
     */
    public void unsetArrivalCity() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(ARRIVALCITY$10, 0);
        }
    }

    /**
     * Gets the "arrivalUnknown" element
     */
    public boolean getArrivalUnknown() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(ARRIVALUNKNOWN$12,
                    0);

            if (target == null) {
                return false;
            }

            return target.getBooleanValue();
        }
    }

    /**
     * Gets (as xml) the "arrivalUnknown" element
     */
    public org.apache.xmlbeans.XmlBoolean xgetArrivalUnknown() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean) get_store()
                                                          .find_element_user(ARRIVALUNKNOWN$12,
                    0);

            return target;
        }
    }

    /**
     * True if has "arrivalUnknown" element
     */
    public boolean isSetArrivalUnknown() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(ARRIVALUNKNOWN$12) != 0;
        }
    }

    /**
     * Sets the "arrivalUnknown" element
     */
    public void setArrivalUnknown(boolean arrivalUnknown) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(ARRIVALUNKNOWN$12,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(ARRIVALUNKNOWN$12);
            }

            target.setBooleanValue(arrivalUnknown);
        }
    }

    /**
     * Sets (as xml) the "arrivalUnknown" element
     */
    public void xsetArrivalUnknown(
        org.apache.xmlbeans.XmlBoolean arrivalUnknown) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean) get_store()
                                                          .find_element_user(ARRIVALUNKNOWN$12,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlBoolean) get_store()
                                                              .add_element_user(ARRIVALUNKNOWN$12);
            }

            target.set(arrivalUnknown);
        }
    }

    /**
     * Unsets the "arrivalUnknown" element
     */
    public void unsetArrivalUnknown() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(ARRIVALUNKNOWN$12, 0);
        }
    }

    /**
     * Gets the "departureCity" element
     */
    public java.lang.String getDepartureCity() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(DEPARTURECITY$14,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "departureCity" element
     */
    public org.apache.xmlbeans.XmlString xgetDepartureCity() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(DEPARTURECITY$14,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "departureCity" element
     */
    public boolean isNilDepartureCity() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(DEPARTURECITY$14,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "departureCity" element
     */
    public boolean isSetDepartureCity() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(DEPARTURECITY$14) != 0;
        }
    }

    /**
     * Sets the "departureCity" element
     */
    public void setDepartureCity(java.lang.String departureCity) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(DEPARTURECITY$14,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(DEPARTURECITY$14);
            }

            target.setStringValue(departureCity);
        }
    }

    /**
     * Sets (as xml) the "departureCity" element
     */
    public void xsetDepartureCity(org.apache.xmlbeans.XmlString departureCity) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(DEPARTURECITY$14,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(DEPARTURECITY$14);
            }

            target.set(departureCity);
        }
    }

    /**
     * Nils the "departureCity" element
     */
    public void setNilDepartureCity() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(DEPARTURECITY$14,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(DEPARTURECITY$14);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "departureCity" element
     */
    public void unsetDepartureCity() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(DEPARTURECITY$14, 0);
        }
    }

    /**
     * Gets the "flightnum" element
     */
    public java.lang.String getFlightnum() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(FLIGHTNUM$16,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "flightnum" element
     */
    public org.apache.xmlbeans.XmlString xgetFlightnum() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(FLIGHTNUM$16,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "flightnum" element
     */
    public boolean isNilFlightnum() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(FLIGHTNUM$16,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "flightnum" element
     */
    public boolean isSetFlightnum() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(FLIGHTNUM$16) != 0;
        }
    }

    /**
     * Sets the "flightnum" element
     */
    public void setFlightnum(java.lang.String flightnum) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(FLIGHTNUM$16,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(FLIGHTNUM$16);
            }

            target.setStringValue(flightnum);
        }
    }

    /**
     * Sets (as xml) the "flightnum" element
     */
    public void xsetFlightnum(org.apache.xmlbeans.XmlString flightnum) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(FLIGHTNUM$16,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(FLIGHTNUM$16);
            }

            target.set(flightnum);
        }
    }

    /**
     * Nils the "flightnum" element
     */
    public void setNilFlightnum() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(FLIGHTNUM$16,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(FLIGHTNUM$16);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "flightnum" element
     */
    public void unsetFlightnum() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(FLIGHTNUM$16, 0);
        }
    }

    /**
     * Gets the "scharrivetime" element
     */
    public java.util.Calendar getScharrivetime() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(SCHARRIVETIME$18,
                    0);

            if (target == null) {
                return null;
            }

            return target.getCalendarValue();
        }
    }

    /**
     * Gets (as xml) the "scharrivetime" element
     */
    public org.apache.xmlbeans.XmlDateTime xgetScharrivetime() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime) get_store()
                                                           .find_element_user(SCHARRIVETIME$18,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "scharrivetime" element
     */
    public boolean isNilScharrivetime() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime) get_store()
                                                           .find_element_user(SCHARRIVETIME$18,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "scharrivetime" element
     */
    public boolean isSetScharrivetime() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(SCHARRIVETIME$18) != 0;
        }
    }

    /**
     * Sets the "scharrivetime" element
     */
    public void setScharrivetime(java.util.Calendar scharrivetime) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(SCHARRIVETIME$18,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(SCHARRIVETIME$18);
            }

            target.setCalendarValue(scharrivetime);
        }
    }

    /**
     * Sets (as xml) the "scharrivetime" element
     */
    public void xsetScharrivetime(org.apache.xmlbeans.XmlDateTime scharrivetime) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime) get_store()
                                                           .find_element_user(SCHARRIVETIME$18,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlDateTime) get_store()
                                                               .add_element_user(SCHARRIVETIME$18);
            }

            target.set(scharrivetime);
        }
    }

    /**
     * Nils the "scharrivetime" element
     */
    public void setNilScharrivetime() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime) get_store()
                                                           .find_element_user(SCHARRIVETIME$18,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlDateTime) get_store()
                                                               .add_element_user(SCHARRIVETIME$18);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "scharrivetime" element
     */
    public void unsetScharrivetime() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(SCHARRIVETIME$18, 0);
        }
    }

    /**
     * Gets the "scharrivetimeGmt" element
     */
    public java.util.Calendar getScharrivetimeGmt() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(SCHARRIVETIMEGMT$20,
                    0);

            if (target == null) {
                return null;
            }

            return target.getCalendarValue();
        }
    }

    /**
     * Gets (as xml) the "scharrivetimeGmt" element
     */
    public org.apache.xmlbeans.XmlDateTime xgetScharrivetimeGmt() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime) get_store()
                                                           .find_element_user(SCHARRIVETIMEGMT$20,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "scharrivetimeGmt" element
     */
    public boolean isNilScharrivetimeGmt() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime) get_store()
                                                           .find_element_user(SCHARRIVETIMEGMT$20,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "scharrivetimeGmt" element
     */
    public boolean isSetScharrivetimeGmt() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(SCHARRIVETIMEGMT$20) != 0;
        }
    }

    /**
     * Sets the "scharrivetimeGmt" element
     */
    public void setScharrivetimeGmt(java.util.Calendar scharrivetimeGmt) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(SCHARRIVETIMEGMT$20,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(SCHARRIVETIMEGMT$20);
            }

            target.setCalendarValue(scharrivetimeGmt);
        }
    }

    /**
     * Sets (as xml) the "scharrivetimeGmt" element
     */
    public void xsetScharrivetimeGmt(
        org.apache.xmlbeans.XmlDateTime scharrivetimeGmt) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime) get_store()
                                                           .find_element_user(SCHARRIVETIMEGMT$20,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlDateTime) get_store()
                                                               .add_element_user(SCHARRIVETIMEGMT$20);
            }

            target.set(scharrivetimeGmt);
        }
    }

    /**
     * Nils the "scharrivetimeGmt" element
     */
    public void setNilScharrivetimeGmt() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime) get_store()
                                                           .find_element_user(SCHARRIVETIMEGMT$20,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlDateTime) get_store()
                                                               .add_element_user(SCHARRIVETIMEGMT$20);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "scharrivetimeGmt" element
     */
    public void unsetScharrivetimeGmt() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(SCHARRIVETIMEGMT$20, 0);
        }
    }

    /**
     * Gets the "schdeparttime" element
     */
    public java.util.Calendar getSchdeparttime() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(SCHDEPARTTIME$22,
                    0);

            if (target == null) {
                return null;
            }

            return target.getCalendarValue();
        }
    }

    /**
     * Gets (as xml) the "schdeparttime" element
     */
    public org.apache.xmlbeans.XmlDateTime xgetSchdeparttime() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime) get_store()
                                                           .find_element_user(SCHDEPARTTIME$22,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "schdeparttime" element
     */
    public boolean isNilSchdeparttime() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime) get_store()
                                                           .find_element_user(SCHDEPARTTIME$22,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "schdeparttime" element
     */
    public boolean isSetSchdeparttime() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(SCHDEPARTTIME$22) != 0;
        }
    }

    /**
     * Sets the "schdeparttime" element
     */
    public void setSchdeparttime(java.util.Calendar schdeparttime) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(SCHDEPARTTIME$22,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(SCHDEPARTTIME$22);
            }

            target.setCalendarValue(schdeparttime);
        }
    }

    /**
     * Sets (as xml) the "schdeparttime" element
     */
    public void xsetSchdeparttime(org.apache.xmlbeans.XmlDateTime schdeparttime) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime) get_store()
                                                           .find_element_user(SCHDEPARTTIME$22,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlDateTime) get_store()
                                                               .add_element_user(SCHDEPARTTIME$22);
            }

            target.set(schdeparttime);
        }
    }

    /**
     * Nils the "schdeparttime" element
     */
    public void setNilSchdeparttime() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime) get_store()
                                                           .find_element_user(SCHDEPARTTIME$22,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlDateTime) get_store()
                                                               .add_element_user(SCHDEPARTTIME$22);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "schdeparttime" element
     */
    public void unsetSchdeparttime() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(SCHDEPARTTIME$22, 0);
        }
    }

    /**
     * Gets the "schdeparttimeGmt" element
     */
    public java.util.Calendar getSchdeparttimeGmt() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(SCHDEPARTTIMEGMT$24,
                    0);

            if (target == null) {
                return null;
            }

            return target.getCalendarValue();
        }
    }

    /**
     * Gets (as xml) the "schdeparttimeGmt" element
     */
    public org.apache.xmlbeans.XmlDateTime xgetSchdeparttimeGmt() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime) get_store()
                                                           .find_element_user(SCHDEPARTTIMEGMT$24,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "schdeparttimeGmt" element
     */
    public boolean isNilSchdeparttimeGmt() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime) get_store()
                                                           .find_element_user(SCHDEPARTTIMEGMT$24,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "schdeparttimeGmt" element
     */
    public boolean isSetSchdeparttimeGmt() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(SCHDEPARTTIMEGMT$24) != 0;
        }
    }

    /**
     * Sets the "schdeparttimeGmt" element
     */
    public void setSchdeparttimeGmt(java.util.Calendar schdeparttimeGmt) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(SCHDEPARTTIMEGMT$24,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(SCHDEPARTTIMEGMT$24);
            }

            target.setCalendarValue(schdeparttimeGmt);
        }
    }

    /**
     * Sets (as xml) the "schdeparttimeGmt" element
     */
    public void xsetSchdeparttimeGmt(
        org.apache.xmlbeans.XmlDateTime schdeparttimeGmt) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime) get_store()
                                                           .find_element_user(SCHDEPARTTIMEGMT$24,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlDateTime) get_store()
                                                               .add_element_user(SCHDEPARTTIMEGMT$24);
            }

            target.set(schdeparttimeGmt);
        }
    }

    /**
     * Nils the "schdeparttimeGmt" element
     */
    public void setNilSchdeparttimeGmt() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime) get_store()
                                                           .find_element_user(SCHDEPARTTIMEGMT$24,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlDateTime) get_store()
                                                               .add_element_user(SCHDEPARTTIMEGMT$24);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "schdeparttimeGmt" element
     */
    public void unsetSchdeparttimeGmt() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(SCHDEPARTTIMEGMT$24, 0);
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
                                                           .find_element_user(TIMECHECKED$26,
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
                                                           .find_element_user(TIMECHECKED$26,
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
                                                           .find_element_user(TIMECHECKED$26,
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

            return get_store().count_elements(TIMECHECKED$26) != 0;
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
                                                           .find_element_user(TIMECHECKED$26,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(TIMECHECKED$26);
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
                                                           .find_element_user(TIMECHECKED$26,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlDateTime) get_store()
                                                               .add_element_user(TIMECHECKED$26);
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
                                                           .find_element_user(TIMECHECKED$26,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlDateTime) get_store()
                                                               .add_element_user(TIMECHECKED$26);
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
            get_store().remove_element(TIMECHECKED$26, 0);
        }
    }
}
