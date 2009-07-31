/*
 * XML Type:  Reservation
 * Namespace: http://common.ws_1_0.serviceprovider.nettracer.aero/xsd
 * Java type: aero.nettracer.serviceprovider.ws_1_0.common.xsd.Reservation
 *
 * Automatically generated - do not modify.
 */
package aero.nettracer.serviceprovider.ws_1_0.common.xsd.impl;

/**
 * An XML Reservation(@http://common.ws_1_0.serviceprovider.nettracer.aero/xsd).
 *
 * This is a complex type.
 */
public class ReservationImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
    implements aero.nettracer.serviceprovider.ws_1_0.common.xsd.Reservation {
    private static final javax.xml.namespace.QName BAGITINERARY$0 = new javax.xml.namespace.QName("http://common.ws_1_0.serviceprovider.nettracer.aero/xsd",
            "bagItinerary");
    private static final javax.xml.namespace.QName CHECKEDLOCATION$2 = new javax.xml.namespace.QName("http://common.ws_1_0.serviceprovider.nettracer.aero/xsd",
            "checkedLocation");
    private static final javax.xml.namespace.QName CLAIMCHECKS$4 = new javax.xml.namespace.QName("http://common.ws_1_0.serviceprovider.nettracer.aero/xsd",
            "claimChecks");
    private static final javax.xml.namespace.QName NUMBERCHECKED$6 = new javax.xml.namespace.QName("http://common.ws_1_0.serviceprovider.nettracer.aero/xsd",
            "numberChecked");
    private static final javax.xml.namespace.QName OSI$8 = new javax.xml.namespace.QName("http://common.ws_1_0.serviceprovider.nettracer.aero/xsd",
            "osi");
    private static final javax.xml.namespace.QName PASSENGERITINERARY$10 = new javax.xml.namespace.QName("http://common.ws_1_0.serviceprovider.nettracer.aero/xsd",
            "passengerItinerary");
    private static final javax.xml.namespace.QName PASSENGERS$12 = new javax.xml.namespace.QName("http://common.ws_1_0.serviceprovider.nettracer.aero/xsd",
            "passengers");
    private static final javax.xml.namespace.QName PAXAFFECTED$14 = new javax.xml.namespace.QName("http://common.ws_1_0.serviceprovider.nettracer.aero/xsd",
            "paxAffected");
    private static final javax.xml.namespace.QName PNR$16 = new javax.xml.namespace.QName("http://common.ws_1_0.serviceprovider.nettracer.aero/xsd",
            "pnr");

    public ReservationImpl(org.apache.xmlbeans.SchemaType sType) {
        super(sType);
    }

    /**
     * Gets array of all "bagItinerary" elements
     */
    public aero.nettracer.serviceprovider.ws_1_0.common.xsd.Itinerary[] getBagItineraryArray() {
        synchronized (monitor()) {
            check_orphaned();

            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(BAGITINERARY$0, targetList);

            aero.nettracer.serviceprovider.ws_1_0.common.xsd.Itinerary[] result = new aero.nettracer.serviceprovider.ws_1_0.common.xsd.Itinerary[targetList.size()];
            targetList.toArray(result);

            return result;
        }
    }

    /**
     * Gets ith "bagItinerary" element
     */
    public aero.nettracer.serviceprovider.ws_1_0.common.xsd.Itinerary getBagItineraryArray(
        int i) {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.ws_1_0.common.xsd.Itinerary target = null;
            target = (aero.nettracer.serviceprovider.ws_1_0.common.xsd.Itinerary) get_store()
                                                                                      .find_element_user(BAGITINERARY$0,
                    i);

            if (target == null) {
                throw new IndexOutOfBoundsException();
            }

            return target;
        }
    }

    /**
     * Tests for nil ith "bagItinerary" element
     */
    public boolean isNilBagItineraryArray(int i) {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.ws_1_0.common.xsd.Itinerary target = null;
            target = (aero.nettracer.serviceprovider.ws_1_0.common.xsd.Itinerary) get_store()
                                                                                      .find_element_user(BAGITINERARY$0,
                    i);

            if (target == null) {
                throw new IndexOutOfBoundsException();
            }

            return target.isNil();
        }
    }

    /**
     * Returns number of "bagItinerary" element
     */
    public int sizeOfBagItineraryArray() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(BAGITINERARY$0);
        }
    }

    /**
     * Sets array of all "bagItinerary" element
     */
    public void setBagItineraryArray(
        aero.nettracer.serviceprovider.ws_1_0.common.xsd.Itinerary[] bagItineraryArray) {
        synchronized (monitor()) {
            check_orphaned();
            arraySetterHelper(bagItineraryArray, BAGITINERARY$0);
        }
    }

    /**
     * Sets ith "bagItinerary" element
     */
    public void setBagItineraryArray(int i,
        aero.nettracer.serviceprovider.ws_1_0.common.xsd.Itinerary bagItinerary) {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.ws_1_0.common.xsd.Itinerary target = null;
            target = (aero.nettracer.serviceprovider.ws_1_0.common.xsd.Itinerary) get_store()
                                                                                      .find_element_user(BAGITINERARY$0,
                    i);

            if (target == null) {
                throw new IndexOutOfBoundsException();
            }

            target.set(bagItinerary);
        }
    }

    /**
     * Nils the ith "bagItinerary" element
     */
    public void setNilBagItineraryArray(int i) {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.ws_1_0.common.xsd.Itinerary target = null;
            target = (aero.nettracer.serviceprovider.ws_1_0.common.xsd.Itinerary) get_store()
                                                                                      .find_element_user(BAGITINERARY$0,
                    i);

            if (target == null) {
                throw new IndexOutOfBoundsException();
            }

            target.setNil();
        }
    }

    /**
     * Inserts and returns a new empty value (as xml) as the ith "bagItinerary" element
     */
    public aero.nettracer.serviceprovider.ws_1_0.common.xsd.Itinerary insertNewBagItinerary(
        int i) {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.ws_1_0.common.xsd.Itinerary target = null;
            target = (aero.nettracer.serviceprovider.ws_1_0.common.xsd.Itinerary) get_store()
                                                                                      .insert_element_user(BAGITINERARY$0,
                    i);

            return target;
        }
    }

    /**
     * Appends and returns a new empty value (as xml) as the last "bagItinerary" element
     */
    public aero.nettracer.serviceprovider.ws_1_0.common.xsd.Itinerary addNewBagItinerary() {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.ws_1_0.common.xsd.Itinerary target = null;
            target = (aero.nettracer.serviceprovider.ws_1_0.common.xsd.Itinerary) get_store()
                                                                                      .add_element_user(BAGITINERARY$0);

            return target;
        }
    }

    /**
     * Removes the ith "bagItinerary" element
     */
    public void removeBagItinerary(int i) {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(BAGITINERARY$0, i);
        }
    }

    /**
     * Gets the "checkedLocation" element
     */
    public int getCheckedLocation() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(CHECKEDLOCATION$2,
                    0);

            if (target == null) {
                return 0;
            }

            return target.getIntValue();
        }
    }

    /**
     * Gets (as xml) the "checkedLocation" element
     */
    public org.apache.xmlbeans.XmlInt xgetCheckedLocation() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt) get_store()
                                                      .find_element_user(CHECKEDLOCATION$2,
                    0);

            return target;
        }
    }

    /**
     * True if has "checkedLocation" element
     */
    public boolean isSetCheckedLocation() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(CHECKEDLOCATION$2) != 0;
        }
    }

    /**
     * Sets the "checkedLocation" element
     */
    public void setCheckedLocation(int checkedLocation) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(CHECKEDLOCATION$2,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(CHECKEDLOCATION$2);
            }

            target.setIntValue(checkedLocation);
        }
    }

    /**
     * Sets (as xml) the "checkedLocation" element
     */
    public void xsetCheckedLocation(org.apache.xmlbeans.XmlInt checkedLocation) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt) get_store()
                                                      .find_element_user(CHECKEDLOCATION$2,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlInt) get_store()
                                                          .add_element_user(CHECKEDLOCATION$2);
            }

            target.set(checkedLocation);
        }
    }

    /**
     * Unsets the "checkedLocation" element
     */
    public void unsetCheckedLocation() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(CHECKEDLOCATION$2, 0);
        }
    }

    /**
     * Gets array of all "claimChecks" elements
     */
    public aero.nettracer.serviceprovider.ws_1_0.common.xsd.ClaimCheck[] getClaimChecksArray() {
        synchronized (monitor()) {
            check_orphaned();

            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(CLAIMCHECKS$4, targetList);

            aero.nettracer.serviceprovider.ws_1_0.common.xsd.ClaimCheck[] result =
                new aero.nettracer.serviceprovider.ws_1_0.common.xsd.ClaimCheck[targetList.size()];
            targetList.toArray(result);

            return result;
        }
    }

    /**
     * Gets ith "claimChecks" element
     */
    public aero.nettracer.serviceprovider.ws_1_0.common.xsd.ClaimCheck getClaimChecksArray(
        int i) {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.ws_1_0.common.xsd.ClaimCheck target = null;
            target = (aero.nettracer.serviceprovider.ws_1_0.common.xsd.ClaimCheck) get_store()
                                                                                       .find_element_user(CLAIMCHECKS$4,
                    i);

            if (target == null) {
                throw new IndexOutOfBoundsException();
            }

            return target;
        }
    }

    /**
     * Tests for nil ith "claimChecks" element
     */
    public boolean isNilClaimChecksArray(int i) {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.ws_1_0.common.xsd.ClaimCheck target = null;
            target = (aero.nettracer.serviceprovider.ws_1_0.common.xsd.ClaimCheck) get_store()
                                                                                       .find_element_user(CLAIMCHECKS$4,
                    i);

            if (target == null) {
                throw new IndexOutOfBoundsException();
            }

            return target.isNil();
        }
    }

    /**
     * Returns number of "claimChecks" element
     */
    public int sizeOfClaimChecksArray() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(CLAIMCHECKS$4);
        }
    }

    /**
     * Sets array of all "claimChecks" element
     */
    public void setClaimChecksArray(
        aero.nettracer.serviceprovider.ws_1_0.common.xsd.ClaimCheck[] claimChecksArray) {
        synchronized (monitor()) {
            check_orphaned();
            arraySetterHelper(claimChecksArray, CLAIMCHECKS$4);
        }
    }

    /**
     * Sets ith "claimChecks" element
     */
    public void setClaimChecksArray(int i,
        aero.nettracer.serviceprovider.ws_1_0.common.xsd.ClaimCheck claimChecks) {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.ws_1_0.common.xsd.ClaimCheck target = null;
            target = (aero.nettracer.serviceprovider.ws_1_0.common.xsd.ClaimCheck) get_store()
                                                                                       .find_element_user(CLAIMCHECKS$4,
                    i);

            if (target == null) {
                throw new IndexOutOfBoundsException();
            }

            target.set(claimChecks);
        }
    }

    /**
     * Nils the ith "claimChecks" element
     */
    public void setNilClaimChecksArray(int i) {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.ws_1_0.common.xsd.ClaimCheck target = null;
            target = (aero.nettracer.serviceprovider.ws_1_0.common.xsd.ClaimCheck) get_store()
                                                                                       .find_element_user(CLAIMCHECKS$4,
                    i);

            if (target == null) {
                throw new IndexOutOfBoundsException();
            }

            target.setNil();
        }
    }

    /**
     * Inserts and returns a new empty value (as xml) as the ith "claimChecks" element
     */
    public aero.nettracer.serviceprovider.ws_1_0.common.xsd.ClaimCheck insertNewClaimChecks(
        int i) {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.ws_1_0.common.xsd.ClaimCheck target = null;
            target = (aero.nettracer.serviceprovider.ws_1_0.common.xsd.ClaimCheck) get_store()
                                                                                       .insert_element_user(CLAIMCHECKS$4,
                    i);

            return target;
        }
    }

    /**
     * Appends and returns a new empty value (as xml) as the last "claimChecks" element
     */
    public aero.nettracer.serviceprovider.ws_1_0.common.xsd.ClaimCheck addNewClaimChecks() {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.ws_1_0.common.xsd.ClaimCheck target = null;
            target = (aero.nettracer.serviceprovider.ws_1_0.common.xsd.ClaimCheck) get_store()
                                                                                       .add_element_user(CLAIMCHECKS$4);

            return target;
        }
    }

    /**
     * Removes the ith "claimChecks" element
     */
    public void removeClaimChecks(int i) {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(CLAIMCHECKS$4, i);
        }
    }

    /**
     * Gets the "numberChecked" element
     */
    public int getNumberChecked() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(NUMBERCHECKED$6,
                    0);

            if (target == null) {
                return 0;
            }

            return target.getIntValue();
        }
    }

    /**
     * Gets (as xml) the "numberChecked" element
     */
    public org.apache.xmlbeans.XmlInt xgetNumberChecked() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt) get_store()
                                                      .find_element_user(NUMBERCHECKED$6,
                    0);

            return target;
        }
    }

    /**
     * True if has "numberChecked" element
     */
    public boolean isSetNumberChecked() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(NUMBERCHECKED$6) != 0;
        }
    }

    /**
     * Sets the "numberChecked" element
     */
    public void setNumberChecked(int numberChecked) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(NUMBERCHECKED$6,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(NUMBERCHECKED$6);
            }

            target.setIntValue(numberChecked);
        }
    }

    /**
     * Sets (as xml) the "numberChecked" element
     */
    public void xsetNumberChecked(org.apache.xmlbeans.XmlInt numberChecked) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt) get_store()
                                                      .find_element_user(NUMBERCHECKED$6,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlInt) get_store()
                                                          .add_element_user(NUMBERCHECKED$6);
            }

            target.set(numberChecked);
        }
    }

    /**
     * Unsets the "numberChecked" element
     */
    public void unsetNumberChecked() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(NUMBERCHECKED$6, 0);
        }
    }

    /**
     * Gets the "osi" element
     */
    public java.lang.String getOsi() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(OSI$8,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "osi" element
     */
    public org.apache.xmlbeans.XmlString xgetOsi() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(OSI$8,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "osi" element
     */
    public boolean isNilOsi() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(OSI$8,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "osi" element
     */
    public boolean isSetOsi() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(OSI$8) != 0;
        }
    }

    /**
     * Sets the "osi" element
     */
    public void setOsi(java.lang.String osi) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(OSI$8,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(OSI$8);
            }

            target.setStringValue(osi);
        }
    }

    /**
     * Sets (as xml) the "osi" element
     */
    public void xsetOsi(org.apache.xmlbeans.XmlString osi) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(OSI$8,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(OSI$8);
            }

            target.set(osi);
        }
    }

    /**
     * Nils the "osi" element
     */
    public void setNilOsi() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(OSI$8,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(OSI$8);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "osi" element
     */
    public void unsetOsi() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(OSI$8, 0);
        }
    }

    /**
     * Gets array of all "passengerItinerary" elements
     */
    public aero.nettracer.serviceprovider.ws_1_0.common.xsd.Itinerary[] getPassengerItineraryArray() {
        synchronized (monitor()) {
            check_orphaned();

            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(PASSENGERITINERARY$10, targetList);

            aero.nettracer.serviceprovider.ws_1_0.common.xsd.Itinerary[] result = new aero.nettracer.serviceprovider.ws_1_0.common.xsd.Itinerary[targetList.size()];
            targetList.toArray(result);

            return result;
        }
    }

    /**
     * Gets ith "passengerItinerary" element
     */
    public aero.nettracer.serviceprovider.ws_1_0.common.xsd.Itinerary getPassengerItineraryArray(
        int i) {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.ws_1_0.common.xsd.Itinerary target = null;
            target = (aero.nettracer.serviceprovider.ws_1_0.common.xsd.Itinerary) get_store()
                                                                                      .find_element_user(PASSENGERITINERARY$10,
                    i);

            if (target == null) {
                throw new IndexOutOfBoundsException();
            }

            return target;
        }
    }

    /**
     * Tests for nil ith "passengerItinerary" element
     */
    public boolean isNilPassengerItineraryArray(int i) {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.ws_1_0.common.xsd.Itinerary target = null;
            target = (aero.nettracer.serviceprovider.ws_1_0.common.xsd.Itinerary) get_store()
                                                                                      .find_element_user(PASSENGERITINERARY$10,
                    i);

            if (target == null) {
                throw new IndexOutOfBoundsException();
            }

            return target.isNil();
        }
    }

    /**
     * Returns number of "passengerItinerary" element
     */
    public int sizeOfPassengerItineraryArray() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(PASSENGERITINERARY$10);
        }
    }

    /**
     * Sets array of all "passengerItinerary" element
     */
    public void setPassengerItineraryArray(
        aero.nettracer.serviceprovider.ws_1_0.common.xsd.Itinerary[] passengerItineraryArray) {
        synchronized (monitor()) {
            check_orphaned();
            arraySetterHelper(passengerItineraryArray, PASSENGERITINERARY$10);
        }
    }

    /**
     * Sets ith "passengerItinerary" element
     */
    public void setPassengerItineraryArray(int i,
        aero.nettracer.serviceprovider.ws_1_0.common.xsd.Itinerary passengerItinerary) {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.ws_1_0.common.xsd.Itinerary target = null;
            target = (aero.nettracer.serviceprovider.ws_1_0.common.xsd.Itinerary) get_store()
                                                                                      .find_element_user(PASSENGERITINERARY$10,
                    i);

            if (target == null) {
                throw new IndexOutOfBoundsException();
            }

            target.set(passengerItinerary);
        }
    }

    /**
     * Nils the ith "passengerItinerary" element
     */
    public void setNilPassengerItineraryArray(int i) {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.ws_1_0.common.xsd.Itinerary target = null;
            target = (aero.nettracer.serviceprovider.ws_1_0.common.xsd.Itinerary) get_store()
                                                                                      .find_element_user(PASSENGERITINERARY$10,
                    i);

            if (target == null) {
                throw new IndexOutOfBoundsException();
            }

            target.setNil();
        }
    }

    /**
     * Inserts and returns a new empty value (as xml) as the ith "passengerItinerary" element
     */
    public aero.nettracer.serviceprovider.ws_1_0.common.xsd.Itinerary insertNewPassengerItinerary(
        int i) {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.ws_1_0.common.xsd.Itinerary target = null;
            target = (aero.nettracer.serviceprovider.ws_1_0.common.xsd.Itinerary) get_store()
                                                                                      .insert_element_user(PASSENGERITINERARY$10,
                    i);

            return target;
        }
    }

    /**
     * Appends and returns a new empty value (as xml) as the last "passengerItinerary" element
     */
    public aero.nettracer.serviceprovider.ws_1_0.common.xsd.Itinerary addNewPassengerItinerary() {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.ws_1_0.common.xsd.Itinerary target = null;
            target = (aero.nettracer.serviceprovider.ws_1_0.common.xsd.Itinerary) get_store()
                                                                                      .add_element_user(PASSENGERITINERARY$10);

            return target;
        }
    }

    /**
     * Removes the ith "passengerItinerary" element
     */
    public void removePassengerItinerary(int i) {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(PASSENGERITINERARY$10, i);
        }
    }

    /**
     * Gets array of all "passengers" elements
     */
    public aero.nettracer.serviceprovider.ws_1_0.common.xsd.Passenger[] getPassengersArray() {
        synchronized (monitor()) {
            check_orphaned();

            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(PASSENGERS$12, targetList);

            aero.nettracer.serviceprovider.ws_1_0.common.xsd.Passenger[] result = new aero.nettracer.serviceprovider.ws_1_0.common.xsd.Passenger[targetList.size()];
            targetList.toArray(result);

            return result;
        }
    }

    /**
     * Gets ith "passengers" element
     */
    public aero.nettracer.serviceprovider.ws_1_0.common.xsd.Passenger getPassengersArray(
        int i) {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.ws_1_0.common.xsd.Passenger target = null;
            target = (aero.nettracer.serviceprovider.ws_1_0.common.xsd.Passenger) get_store()
                                                                                      .find_element_user(PASSENGERS$12,
                    i);

            if (target == null) {
                throw new IndexOutOfBoundsException();
            }

            return target;
        }
    }

    /**
     * Tests for nil ith "passengers" element
     */
    public boolean isNilPassengersArray(int i) {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.ws_1_0.common.xsd.Passenger target = null;
            target = (aero.nettracer.serviceprovider.ws_1_0.common.xsd.Passenger) get_store()
                                                                                      .find_element_user(PASSENGERS$12,
                    i);

            if (target == null) {
                throw new IndexOutOfBoundsException();
            }

            return target.isNil();
        }
    }

    /**
     * Returns number of "passengers" element
     */
    public int sizeOfPassengersArray() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(PASSENGERS$12);
        }
    }

    /**
     * Sets array of all "passengers" element
     */
    public void setPassengersArray(
        aero.nettracer.serviceprovider.ws_1_0.common.xsd.Passenger[] passengersArray) {
        synchronized (monitor()) {
            check_orphaned();
            arraySetterHelper(passengersArray, PASSENGERS$12);
        }
    }

    /**
     * Sets ith "passengers" element
     */
    public void setPassengersArray(int i,
        aero.nettracer.serviceprovider.ws_1_0.common.xsd.Passenger passengers) {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.ws_1_0.common.xsd.Passenger target = null;
            target = (aero.nettracer.serviceprovider.ws_1_0.common.xsd.Passenger) get_store()
                                                                                      .find_element_user(PASSENGERS$12,
                    i);

            if (target == null) {
                throw new IndexOutOfBoundsException();
            }

            target.set(passengers);
        }
    }

    /**
     * Nils the ith "passengers" element
     */
    public void setNilPassengersArray(int i) {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.ws_1_0.common.xsd.Passenger target = null;
            target = (aero.nettracer.serviceprovider.ws_1_0.common.xsd.Passenger) get_store()
                                                                                      .find_element_user(PASSENGERS$12,
                    i);

            if (target == null) {
                throw new IndexOutOfBoundsException();
            }

            target.setNil();
        }
    }

    /**
     * Inserts and returns a new empty value (as xml) as the ith "passengers" element
     */
    public aero.nettracer.serviceprovider.ws_1_0.common.xsd.Passenger insertNewPassengers(
        int i) {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.ws_1_0.common.xsd.Passenger target = null;
            target = (aero.nettracer.serviceprovider.ws_1_0.common.xsd.Passenger) get_store()
                                                                                      .insert_element_user(PASSENGERS$12,
                    i);

            return target;
        }
    }

    /**
     * Appends and returns a new empty value (as xml) as the last "passengers" element
     */
    public aero.nettracer.serviceprovider.ws_1_0.common.xsd.Passenger addNewPassengers() {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.ws_1_0.common.xsd.Passenger target = null;
            target = (aero.nettracer.serviceprovider.ws_1_0.common.xsd.Passenger) get_store()
                                                                                      .add_element_user(PASSENGERS$12);

            return target;
        }
    }

    /**
     * Removes the ith "passengers" element
     */
    public void removePassengers(int i) {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(PASSENGERS$12, i);
        }
    }

    /**
     * Gets the "paxAffected" element
     */
    public int getPaxAffected() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(PAXAFFECTED$14,
                    0);

            if (target == null) {
                return 0;
            }

            return target.getIntValue();
        }
    }

    /**
     * Gets (as xml) the "paxAffected" element
     */
    public org.apache.xmlbeans.XmlInt xgetPaxAffected() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt) get_store()
                                                      .find_element_user(PAXAFFECTED$14,
                    0);

            return target;
        }
    }

    /**
     * True if has "paxAffected" element
     */
    public boolean isSetPaxAffected() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(PAXAFFECTED$14) != 0;
        }
    }

    /**
     * Sets the "paxAffected" element
     */
    public void setPaxAffected(int paxAffected) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(PAXAFFECTED$14,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(PAXAFFECTED$14);
            }

            target.setIntValue(paxAffected);
        }
    }

    /**
     * Sets (as xml) the "paxAffected" element
     */
    public void xsetPaxAffected(org.apache.xmlbeans.XmlInt paxAffected) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt) get_store()
                                                      .find_element_user(PAXAFFECTED$14,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlInt) get_store()
                                                          .add_element_user(PAXAFFECTED$14);
            }

            target.set(paxAffected);
        }
    }

    /**
     * Unsets the "paxAffected" element
     */
    public void unsetPaxAffected() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(PAXAFFECTED$14, 0);
        }
    }

    /**
     * Gets the "pnr" element
     */
    public java.lang.String getPnr() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(PNR$16,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "pnr" element
     */
    public org.apache.xmlbeans.XmlString xgetPnr() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(PNR$16,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "pnr" element
     */
    public boolean isNilPnr() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(PNR$16,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "pnr" element
     */
    public boolean isSetPnr() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(PNR$16) != 0;
        }
    }

    /**
     * Sets the "pnr" element
     */
    public void setPnr(java.lang.String pnr) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(PNR$16,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(PNR$16);
            }

            target.setStringValue(pnr);
        }
    }

    /**
     * Sets (as xml) the "pnr" element
     */
    public void xsetPnr(org.apache.xmlbeans.XmlString pnr) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(PNR$16,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(PNR$16);
            }

            target.set(pnr);
        }
    }

    /**
     * Nils the "pnr" element
     */
    public void setNilPnr() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(PNR$16,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(PNR$16);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "pnr" element
     */
    public void unsetPnr() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(PNR$16, 0);
        }
    }
}
