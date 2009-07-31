/*
 * XML Type:  ReservationResponse
 * Namespace: http://response.ws_1_0.serviceprovider.nettracer.aero/xsd
 * Java type: aero.nettracer.serviceprovider.ws_1_0.response.xsd.ReservationResponse
 *
 * Automatically generated - do not modify.
 */
package aero.nettracer.serviceprovider.ws_1_0.response.xsd.impl;

/**
 * An XML ReservationResponse(@http://response.ws_1_0.serviceprovider.nettracer.aero/xsd).
 *
 * This is a complex type.
 */
public class ReservationResponseImpl extends aero.nettracer.serviceprovider.ws_1_0.response.xsd.impl.GenericResponseImpl
    implements aero.nettracer.serviceprovider.ws_1_0.response.xsd.ReservationResponse {
    private static final javax.xml.namespace.QName RESERVATION$0 = new javax.xml.namespace.QName("http://response.ws_1_0.serviceprovider.nettracer.aero/xsd",
            "reservation");

    public ReservationResponseImpl(org.apache.xmlbeans.SchemaType sType) {
        super(sType);
    }

    /**
     * Gets the "reservation" element
     */
    public aero.nettracer.serviceprovider.ws_1_0.common.xsd.Reservation getReservation() {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.ws_1_0.common.xsd.Reservation target = null;
            target = (aero.nettracer.serviceprovider.ws_1_0.common.xsd.Reservation) get_store()
                                                                                        .find_element_user(RESERVATION$0,
                    0);

            if (target == null) {
                return null;
            }

            return target;
        }
    }

    /**
     * Tests for nil "reservation" element
     */
    public boolean isNilReservation() {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.ws_1_0.common.xsd.Reservation target = null;
            target = (aero.nettracer.serviceprovider.ws_1_0.common.xsd.Reservation) get_store()
                                                                                        .find_element_user(RESERVATION$0,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "reservation" element
     */
    public boolean isSetReservation() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(RESERVATION$0) != 0;
        }
    }

    /**
     * Sets the "reservation" element
     */
    public void setReservation(
        aero.nettracer.serviceprovider.ws_1_0.common.xsd.Reservation reservation) {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.ws_1_0.common.xsd.Reservation target = null;
            target = (aero.nettracer.serviceprovider.ws_1_0.common.xsd.Reservation) get_store()
                                                                                        .find_element_user(RESERVATION$0,
                    0);

            if (target == null) {
                target = (aero.nettracer.serviceprovider.ws_1_0.common.xsd.Reservation) get_store()
                                                                                            .add_element_user(RESERVATION$0);
            }

            target.set(reservation);
        }
    }

    /**
     * Appends and returns a new empty "reservation" element
     */
    public aero.nettracer.serviceprovider.ws_1_0.common.xsd.Reservation addNewReservation() {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.ws_1_0.common.xsd.Reservation target = null;
            target = (aero.nettracer.serviceprovider.ws_1_0.common.xsd.Reservation) get_store()
                                                                                        .add_element_user(RESERVATION$0);

            return target;
        }
    }

    /**
     * Nils the "reservation" element
     */
    public void setNilReservation() {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.ws_1_0.common.xsd.Reservation target = null;
            target = (aero.nettracer.serviceprovider.ws_1_0.common.xsd.Reservation) get_store()
                                                                                        .find_element_user(RESERVATION$0,
                    0);

            if (target == null) {
                target = (aero.nettracer.serviceprovider.ws_1_0.common.xsd.Reservation) get_store()
                                                                                            .add_element_user(RESERVATION$0);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "reservation" element
     */
    public void unsetReservation() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(RESERVATION$0, 0);
        }
    }
}
