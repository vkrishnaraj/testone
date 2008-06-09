/*
 * XML Type:  WS_Itinerary
 * Namespace: http://pojo.core.ws.nettracer.bagnet.com/xsd
 * Java type: com.bagnet.nettracer.ws.core.pojo.xsd.WSItinerary
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.core.pojo.xsd.impl;

/**
 * An XML WS_Itinerary(@http://pojo.core.ws.nettracer.bagnet.com/xsd).
 *
 * This is a complex type.
 */
public class WSItineraryImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
    implements com.bagnet.nettracer.ws.core.pojo.xsd.WSItinerary {
    private static final javax.xml.namespace.QName ACTARRIVETIME$0 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "actarrivetime");
    private static final javax.xml.namespace.QName ACTDEPARTTIME$2 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "actdeparttime");
    private static final javax.xml.namespace.QName AIRLINE$4 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "airline");
    private static final javax.xml.namespace.QName ARRIVEDATE$6 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "arrivedate");
    private static final javax.xml.namespace.QName DEPARTDATE$8 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "departdate");
    private static final javax.xml.namespace.QName FLIGHTNUM$10 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "flightnum");
    private static final javax.xml.namespace.QName ITINERARYID$12 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "itinerary_ID");
    private static final javax.xml.namespace.QName ITINERARYTYPE$14 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "itinerarytype");
    private static final javax.xml.namespace.QName LEGFROM$16 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "legfrom");
    private static final javax.xml.namespace.QName LEGFROMTYPE$18 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "legfrom_type");
    private static final javax.xml.namespace.QName LEGTO$20 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "legto");
    private static final javax.xml.namespace.QName LEGTOTYPE$22 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "legto_type");
    private static final javax.xml.namespace.QName SCHARRIVETIME$24 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "scharrivetime");
    private static final javax.xml.namespace.QName SCHDEPARTTIME$26 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "schdeparttime");

    public WSItineraryImpl(org.apache.xmlbeans.SchemaType sType) {
        super(sType);
    }

    /**
     * Gets the "actarrivetime" element
     */
    public java.lang.String getActarrivetime() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(ACTARRIVETIME$0,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "actarrivetime" element
     */
    public org.apache.xmlbeans.XmlString xgetActarrivetime() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
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

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
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
    public void setActarrivetime(java.lang.String actarrivetime) {
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

            target.setStringValue(actarrivetime);
        }
    }

    /**
     * Sets (as xml) the "actarrivetime" element
     */
    public void xsetActarrivetime(org.apache.xmlbeans.XmlString actarrivetime) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(ACTARRIVETIME$0,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
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

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(ACTARRIVETIME$0,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
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
     * Gets the "actdeparttime" element
     */
    public java.lang.String getActdeparttime() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(ACTDEPARTTIME$2,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "actdeparttime" element
     */
    public org.apache.xmlbeans.XmlString xgetActdeparttime() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(ACTDEPARTTIME$2,
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

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(ACTDEPARTTIME$2,
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

            return get_store().count_elements(ACTDEPARTTIME$2) != 0;
        }
    }

    /**
     * Sets the "actdeparttime" element
     */
    public void setActdeparttime(java.lang.String actdeparttime) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(ACTDEPARTTIME$2,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(ACTDEPARTTIME$2);
            }

            target.setStringValue(actdeparttime);
        }
    }

    /**
     * Sets (as xml) the "actdeparttime" element
     */
    public void xsetActdeparttime(org.apache.xmlbeans.XmlString actdeparttime) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(ACTDEPARTTIME$2,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(ACTDEPARTTIME$2);
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

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(ACTDEPARTTIME$2,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(ACTDEPARTTIME$2);
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
            get_store().remove_element(ACTDEPARTTIME$2, 0);
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
                                                           .find_element_user(AIRLINE$4,
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
                                                         .find_element_user(AIRLINE$4,
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
                                                         .find_element_user(AIRLINE$4,
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

            return get_store().count_elements(AIRLINE$4) != 0;
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
                                                           .find_element_user(AIRLINE$4,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(AIRLINE$4);
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
                                                         .find_element_user(AIRLINE$4,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(AIRLINE$4);
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
                                                         .find_element_user(AIRLINE$4,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(AIRLINE$4);
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
            get_store().remove_element(AIRLINE$4, 0);
        }
    }

    /**
     * Gets the "arrivedate" element
     */
    public java.lang.String getArrivedate() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(ARRIVEDATE$6,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "arrivedate" element
     */
    public org.apache.xmlbeans.XmlString xgetArrivedate() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(ARRIVEDATE$6,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "arrivedate" element
     */
    public boolean isNilArrivedate() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(ARRIVEDATE$6,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "arrivedate" element
     */
    public boolean isSetArrivedate() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(ARRIVEDATE$6) != 0;
        }
    }

    /**
     * Sets the "arrivedate" element
     */
    public void setArrivedate(java.lang.String arrivedate) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(ARRIVEDATE$6,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(ARRIVEDATE$6);
            }

            target.setStringValue(arrivedate);
        }
    }

    /**
     * Sets (as xml) the "arrivedate" element
     */
    public void xsetArrivedate(org.apache.xmlbeans.XmlString arrivedate) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(ARRIVEDATE$6,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(ARRIVEDATE$6);
            }

            target.set(arrivedate);
        }
    }

    /**
     * Nils the "arrivedate" element
     */
    public void setNilArrivedate() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(ARRIVEDATE$6,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(ARRIVEDATE$6);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "arrivedate" element
     */
    public void unsetArrivedate() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(ARRIVEDATE$6, 0);
        }
    }

    /**
     * Gets the "departdate" element
     */
    public java.lang.String getDepartdate() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(DEPARTDATE$8,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "departdate" element
     */
    public org.apache.xmlbeans.XmlString xgetDepartdate() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(DEPARTDATE$8,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "departdate" element
     */
    public boolean isNilDepartdate() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(DEPARTDATE$8,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "departdate" element
     */
    public boolean isSetDepartdate() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(DEPARTDATE$8) != 0;
        }
    }

    /**
     * Sets the "departdate" element
     */
    public void setDepartdate(java.lang.String departdate) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(DEPARTDATE$8,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(DEPARTDATE$8);
            }

            target.setStringValue(departdate);
        }
    }

    /**
     * Sets (as xml) the "departdate" element
     */
    public void xsetDepartdate(org.apache.xmlbeans.XmlString departdate) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(DEPARTDATE$8,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(DEPARTDATE$8);
            }

            target.set(departdate);
        }
    }

    /**
     * Nils the "departdate" element
     */
    public void setNilDepartdate() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(DEPARTDATE$8,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(DEPARTDATE$8);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "departdate" element
     */
    public void unsetDepartdate() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(DEPARTDATE$8, 0);
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
                                                           .find_element_user(FLIGHTNUM$10,
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
                                                         .find_element_user(FLIGHTNUM$10,
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
                                                         .find_element_user(FLIGHTNUM$10,
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

            return get_store().count_elements(FLIGHTNUM$10) != 0;
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
                                                           .find_element_user(FLIGHTNUM$10,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(FLIGHTNUM$10);
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
                                                         .find_element_user(FLIGHTNUM$10,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(FLIGHTNUM$10);
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
                                                         .find_element_user(FLIGHTNUM$10,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(FLIGHTNUM$10);
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
            get_store().remove_element(FLIGHTNUM$10, 0);
        }
    }

    /**
     * Gets the "itinerary_ID" element
     */
    public int getItineraryID() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(ITINERARYID$12,
                    0);

            if (target == null) {
                return 0;
            }

            return target.getIntValue();
        }
    }

    /**
     * Gets (as xml) the "itinerary_ID" element
     */
    public org.apache.xmlbeans.XmlInt xgetItineraryID() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt) get_store()
                                                      .find_element_user(ITINERARYID$12,
                    0);

            return target;
        }
    }

    /**
     * True if has "itinerary_ID" element
     */
    public boolean isSetItineraryID() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(ITINERARYID$12) != 0;
        }
    }

    /**
     * Sets the "itinerary_ID" element
     */
    public void setItineraryID(int itineraryID) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(ITINERARYID$12,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(ITINERARYID$12);
            }

            target.setIntValue(itineraryID);
        }
    }

    /**
     * Sets (as xml) the "itinerary_ID" element
     */
    public void xsetItineraryID(org.apache.xmlbeans.XmlInt itineraryID) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt) get_store()
                                                      .find_element_user(ITINERARYID$12,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlInt) get_store()
                                                          .add_element_user(ITINERARYID$12);
            }

            target.set(itineraryID);
        }
    }

    /**
     * Unsets the "itinerary_ID" element
     */
    public void unsetItineraryID() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(ITINERARYID$12, 0);
        }
    }

    /**
     * Gets the "itinerarytype" element
     */
    public int getItinerarytype() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(ITINERARYTYPE$14,
                    0);

            if (target == null) {
                return 0;
            }

            return target.getIntValue();
        }
    }

    /**
     * Gets (as xml) the "itinerarytype" element
     */
    public org.apache.xmlbeans.XmlInt xgetItinerarytype() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt) get_store()
                                                      .find_element_user(ITINERARYTYPE$14,
                    0);

            return target;
        }
    }

    /**
     * True if has "itinerarytype" element
     */
    public boolean isSetItinerarytype() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(ITINERARYTYPE$14) != 0;
        }
    }

    /**
     * Sets the "itinerarytype" element
     */
    public void setItinerarytype(int itinerarytype) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(ITINERARYTYPE$14,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(ITINERARYTYPE$14);
            }

            target.setIntValue(itinerarytype);
        }
    }

    /**
     * Sets (as xml) the "itinerarytype" element
     */
    public void xsetItinerarytype(org.apache.xmlbeans.XmlInt itinerarytype) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt) get_store()
                                                      .find_element_user(ITINERARYTYPE$14,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlInt) get_store()
                                                          .add_element_user(ITINERARYTYPE$14);
            }

            target.set(itinerarytype);
        }
    }

    /**
     * Unsets the "itinerarytype" element
     */
    public void unsetItinerarytype() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(ITINERARYTYPE$14, 0);
        }
    }

    /**
     * Gets the "legfrom" element
     */
    public java.lang.String getLegfrom() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(LEGFROM$16,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "legfrom" element
     */
    public org.apache.xmlbeans.XmlString xgetLegfrom() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(LEGFROM$16,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "legfrom" element
     */
    public boolean isNilLegfrom() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(LEGFROM$16,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "legfrom" element
     */
    public boolean isSetLegfrom() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(LEGFROM$16) != 0;
        }
    }

    /**
     * Sets the "legfrom" element
     */
    public void setLegfrom(java.lang.String legfrom) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(LEGFROM$16,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(LEGFROM$16);
            }

            target.setStringValue(legfrom);
        }
    }

    /**
     * Sets (as xml) the "legfrom" element
     */
    public void xsetLegfrom(org.apache.xmlbeans.XmlString legfrom) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(LEGFROM$16,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(LEGFROM$16);
            }

            target.set(legfrom);
        }
    }

    /**
     * Nils the "legfrom" element
     */
    public void setNilLegfrom() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(LEGFROM$16,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(LEGFROM$16);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "legfrom" element
     */
    public void unsetLegfrom() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(LEGFROM$16, 0);
        }
    }

    /**
     * Gets the "legfrom_type" element
     */
    public int getLegfromType() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(LEGFROMTYPE$18,
                    0);

            if (target == null) {
                return 0;
            }

            return target.getIntValue();
        }
    }

    /**
     * Gets (as xml) the "legfrom_type" element
     */
    public org.apache.xmlbeans.XmlInt xgetLegfromType() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt) get_store()
                                                      .find_element_user(LEGFROMTYPE$18,
                    0);

            return target;
        }
    }

    /**
     * True if has "legfrom_type" element
     */
    public boolean isSetLegfromType() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(LEGFROMTYPE$18) != 0;
        }
    }

    /**
     * Sets the "legfrom_type" element
     */
    public void setLegfromType(int legfromType) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(LEGFROMTYPE$18,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(LEGFROMTYPE$18);
            }

            target.setIntValue(legfromType);
        }
    }

    /**
     * Sets (as xml) the "legfrom_type" element
     */
    public void xsetLegfromType(org.apache.xmlbeans.XmlInt legfromType) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt) get_store()
                                                      .find_element_user(LEGFROMTYPE$18,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlInt) get_store()
                                                          .add_element_user(LEGFROMTYPE$18);
            }

            target.set(legfromType);
        }
    }

    /**
     * Unsets the "legfrom_type" element
     */
    public void unsetLegfromType() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(LEGFROMTYPE$18, 0);
        }
    }

    /**
     * Gets the "legto" element
     */
    public java.lang.String getLegto() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(LEGTO$20,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "legto" element
     */
    public org.apache.xmlbeans.XmlString xgetLegto() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(LEGTO$20,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "legto" element
     */
    public boolean isNilLegto() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(LEGTO$20,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "legto" element
     */
    public boolean isSetLegto() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(LEGTO$20) != 0;
        }
    }

    /**
     * Sets the "legto" element
     */
    public void setLegto(java.lang.String legto) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(LEGTO$20,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(LEGTO$20);
            }

            target.setStringValue(legto);
        }
    }

    /**
     * Sets (as xml) the "legto" element
     */
    public void xsetLegto(org.apache.xmlbeans.XmlString legto) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(LEGTO$20,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(LEGTO$20);
            }

            target.set(legto);
        }
    }

    /**
     * Nils the "legto" element
     */
    public void setNilLegto() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(LEGTO$20,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(LEGTO$20);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "legto" element
     */
    public void unsetLegto() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(LEGTO$20, 0);
        }
    }

    /**
     * Gets the "legto_type" element
     */
    public int getLegtoType() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(LEGTOTYPE$22,
                    0);

            if (target == null) {
                return 0;
            }

            return target.getIntValue();
        }
    }

    /**
     * Gets (as xml) the "legto_type" element
     */
    public org.apache.xmlbeans.XmlInt xgetLegtoType() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt) get_store()
                                                      .find_element_user(LEGTOTYPE$22,
                    0);

            return target;
        }
    }

    /**
     * True if has "legto_type" element
     */
    public boolean isSetLegtoType() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(LEGTOTYPE$22) != 0;
        }
    }

    /**
     * Sets the "legto_type" element
     */
    public void setLegtoType(int legtoType) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(LEGTOTYPE$22,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(LEGTOTYPE$22);
            }

            target.setIntValue(legtoType);
        }
    }

    /**
     * Sets (as xml) the "legto_type" element
     */
    public void xsetLegtoType(org.apache.xmlbeans.XmlInt legtoType) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt) get_store()
                                                      .find_element_user(LEGTOTYPE$22,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlInt) get_store()
                                                          .add_element_user(LEGTOTYPE$22);
            }

            target.set(legtoType);
        }
    }

    /**
     * Unsets the "legto_type" element
     */
    public void unsetLegtoType() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(LEGTOTYPE$22, 0);
        }
    }

    /**
     * Gets the "scharrivetime" element
     */
    public java.lang.String getScharrivetime() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(SCHARRIVETIME$24,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "scharrivetime" element
     */
    public org.apache.xmlbeans.XmlString xgetScharrivetime() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(SCHARRIVETIME$24,
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

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(SCHARRIVETIME$24,
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

            return get_store().count_elements(SCHARRIVETIME$24) != 0;
        }
    }

    /**
     * Sets the "scharrivetime" element
     */
    public void setScharrivetime(java.lang.String scharrivetime) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(SCHARRIVETIME$24,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(SCHARRIVETIME$24);
            }

            target.setStringValue(scharrivetime);
        }
    }

    /**
     * Sets (as xml) the "scharrivetime" element
     */
    public void xsetScharrivetime(org.apache.xmlbeans.XmlString scharrivetime) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(SCHARRIVETIME$24,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(SCHARRIVETIME$24);
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

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(SCHARRIVETIME$24,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(SCHARRIVETIME$24);
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
            get_store().remove_element(SCHARRIVETIME$24, 0);
        }
    }

    /**
     * Gets the "schdeparttime" element
     */
    public java.lang.String getSchdeparttime() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(SCHDEPARTTIME$26,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "schdeparttime" element
     */
    public org.apache.xmlbeans.XmlString xgetSchdeparttime() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(SCHDEPARTTIME$26,
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

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(SCHDEPARTTIME$26,
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

            return get_store().count_elements(SCHDEPARTTIME$26) != 0;
        }
    }

    /**
     * Sets the "schdeparttime" element
     */
    public void setSchdeparttime(java.lang.String schdeparttime) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(SCHDEPARTTIME$26,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(SCHDEPARTTIME$26);
            }

            target.setStringValue(schdeparttime);
        }
    }

    /**
     * Sets (as xml) the "schdeparttime" element
     */
    public void xsetSchdeparttime(org.apache.xmlbeans.XmlString schdeparttime) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(SCHDEPARTTIME$26,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(SCHDEPARTTIME$26);
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

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(SCHDEPARTTIME$26,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(SCHDEPARTTIME$26);
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
            get_store().remove_element(SCHDEPARTTIME$26, 0);
        }
    }
}
