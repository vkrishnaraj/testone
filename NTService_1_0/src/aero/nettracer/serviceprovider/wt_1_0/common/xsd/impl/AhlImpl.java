/*
 * XML Type:  Ahl
 * Namespace: http://common.wt_1_0.serviceprovider.nettracer.aero/xsd
 * Java type: aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl
 *
 * Automatically generated - do not modify.
 */
package aero.nettracer.serviceprovider.wt_1_0.common.xsd.impl;

/**
 * An XML Ahl(@http://common.wt_1_0.serviceprovider.nettracer.aero/xsd).
 *
 * This is a complex type.
 */
public class AhlImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
    implements aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl {
    private static final javax.xml.namespace.QName AHLID$0 = new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd",
            "ahlId");
    private static final javax.xml.namespace.QName BAGITINERARY$2 = new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd",
            "bagItinerary");
    private static final javax.xml.namespace.QName CLAIMCHECK$4 = new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd",
            "claimCheck");
    private static final javax.xml.namespace.QName FAULTREASON$6 = new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd",
            "faultReason");
    private static final javax.xml.namespace.QName FAULTSTATION$8 = new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd",
            "faultStation");
    private static final javax.xml.namespace.QName ITEM$10 = new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd",
            "item");
    private static final javax.xml.namespace.QName NUMBERPAXAFFECTED$12 = new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd",
            "numberPaxAffected");
    private static final javax.xml.namespace.QName PAX$14 = new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd",
            "pax");
    private static final javax.xml.namespace.QName PAXITINERARY$16 = new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd",
            "paxItinerary");
    private static final javax.xml.namespace.QName STATIONCODE$18 = new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd",
            "stationCode");

    public AhlImpl(org.apache.xmlbeans.SchemaType sType) {
        super(sType);
    }

    /**
     * Gets the "ahlId" element
     */
    public java.lang.String getAhlId() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(AHLID$0,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "ahlId" element
     */
    public org.apache.xmlbeans.XmlString xgetAhlId() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(AHLID$0,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "ahlId" element
     */
    public boolean isNilAhlId() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(AHLID$0,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "ahlId" element
     */
    public boolean isSetAhlId() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(AHLID$0) != 0;
        }
    }

    /**
     * Sets the "ahlId" element
     */
    public void setAhlId(java.lang.String ahlId) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(AHLID$0,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(AHLID$0);
            }

            target.setStringValue(ahlId);
        }
    }

    /**
     * Sets (as xml) the "ahlId" element
     */
    public void xsetAhlId(org.apache.xmlbeans.XmlString ahlId) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(AHLID$0,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(AHLID$0);
            }

            target.set(ahlId);
        }
    }

    /**
     * Nils the "ahlId" element
     */
    public void setNilAhlId() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(AHLID$0,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(AHLID$0);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "ahlId" element
     */
    public void unsetAhlId() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(AHLID$0, 0);
        }
    }

    /**
     * Gets array of all "bagItinerary" elements
     */
    public aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary[] getBagItineraryArray() {
        synchronized (monitor()) {
            check_orphaned();

            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(BAGITINERARY$2, targetList);

            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary[] result = new aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary[targetList.size()];
            targetList.toArray(result);

            return result;
        }
    }

    /**
     * Gets ith "bagItinerary" element
     */
    public aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary getBagItineraryArray(
        int i) {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary) get_store()
                                                                                      .find_element_user(BAGITINERARY$2,
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

            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary) get_store()
                                                                                      .find_element_user(BAGITINERARY$2,
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

            return get_store().count_elements(BAGITINERARY$2);
        }
    }

    /**
     * Sets array of all "bagItinerary" element
     */
    public void setBagItineraryArray(
        aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary[] bagItineraryArray) {
        synchronized (monitor()) {
            check_orphaned();
            arraySetterHelper(bagItineraryArray, BAGITINERARY$2);
        }
    }

    /**
     * Sets ith "bagItinerary" element
     */
    public void setBagItineraryArray(int i,
        aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary bagItinerary) {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary) get_store()
                                                                                      .find_element_user(BAGITINERARY$2,
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

            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary) get_store()
                                                                                      .find_element_user(BAGITINERARY$2,
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
    public aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary insertNewBagItinerary(
        int i) {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary) get_store()
                                                                                      .insert_element_user(BAGITINERARY$2,
                    i);

            return target;
        }
    }

    /**
     * Appends and returns a new empty value (as xml) as the last "bagItinerary" element
     */
    public aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary addNewBagItinerary() {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary) get_store()
                                                                                      .add_element_user(BAGITINERARY$2);

            return target;
        }
    }

    /**
     * Removes the ith "bagItinerary" element
     */
    public void removeBagItinerary(int i) {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(BAGITINERARY$2, i);
        }
    }

    /**
     * Gets array of all "claimCheck" elements
     */
    public aero.nettracer.serviceprovider.wt_1_0.common.xsd.ClaimCheck[] getClaimCheckArray() {
        synchronized (monitor()) {
            check_orphaned();

            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(CLAIMCHECK$4, targetList);

            aero.nettracer.serviceprovider.wt_1_0.common.xsd.ClaimCheck[] result =
                new aero.nettracer.serviceprovider.wt_1_0.common.xsd.ClaimCheck[targetList.size()];
            targetList.toArray(result);

            return result;
        }
    }

    /**
     * Gets ith "claimCheck" element
     */
    public aero.nettracer.serviceprovider.wt_1_0.common.xsd.ClaimCheck getClaimCheckArray(
        int i) {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.wt_1_0.common.xsd.ClaimCheck target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ClaimCheck) get_store()
                                                                                       .find_element_user(CLAIMCHECK$4,
                    i);

            if (target == null) {
                throw new IndexOutOfBoundsException();
            }

            return target;
        }
    }

    /**
     * Tests for nil ith "claimCheck" element
     */
    public boolean isNilClaimCheckArray(int i) {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.wt_1_0.common.xsd.ClaimCheck target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ClaimCheck) get_store()
                                                                                       .find_element_user(CLAIMCHECK$4,
                    i);

            if (target == null) {
                throw new IndexOutOfBoundsException();
            }

            return target.isNil();
        }
    }

    /**
     * Returns number of "claimCheck" element
     */
    public int sizeOfClaimCheckArray() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(CLAIMCHECK$4);
        }
    }

    /**
     * Sets array of all "claimCheck" element
     */
    public void setClaimCheckArray(
        aero.nettracer.serviceprovider.wt_1_0.common.xsd.ClaimCheck[] claimCheckArray) {
        synchronized (monitor()) {
            check_orphaned();
            arraySetterHelper(claimCheckArray, CLAIMCHECK$4);
        }
    }

    /**
     * Sets ith "claimCheck" element
     */
    public void setClaimCheckArray(int i,
        aero.nettracer.serviceprovider.wt_1_0.common.xsd.ClaimCheck claimCheck) {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.wt_1_0.common.xsd.ClaimCheck target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ClaimCheck) get_store()
                                                                                       .find_element_user(CLAIMCHECK$4,
                    i);

            if (target == null) {
                throw new IndexOutOfBoundsException();
            }

            target.set(claimCheck);
        }
    }

    /**
     * Nils the ith "claimCheck" element
     */
    public void setNilClaimCheckArray(int i) {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.wt_1_0.common.xsd.ClaimCheck target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ClaimCheck) get_store()
                                                                                       .find_element_user(CLAIMCHECK$4,
                    i);

            if (target == null) {
                throw new IndexOutOfBoundsException();
            }

            target.setNil();
        }
    }

    /**
     * Inserts and returns a new empty value (as xml) as the ith "claimCheck" element
     */
    public aero.nettracer.serviceprovider.wt_1_0.common.xsd.ClaimCheck insertNewClaimCheck(
        int i) {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.wt_1_0.common.xsd.ClaimCheck target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ClaimCheck) get_store()
                                                                                       .insert_element_user(CLAIMCHECK$4,
                    i);

            return target;
        }
    }

    /**
     * Appends and returns a new empty value (as xml) as the last "claimCheck" element
     */
    public aero.nettracer.serviceprovider.wt_1_0.common.xsd.ClaimCheck addNewClaimCheck() {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.wt_1_0.common.xsd.ClaimCheck target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.ClaimCheck) get_store()
                                                                                       .add_element_user(CLAIMCHECK$4);

            return target;
        }
    }

    /**
     * Removes the ith "claimCheck" element
     */
    public void removeClaimCheck(int i) {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(CLAIMCHECK$4, i);
        }
    }

    /**
     * Gets the "faultReason" element
     */
    public int getFaultReason() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(FAULTREASON$6,
                    0);

            if (target == null) {
                return 0;
            }

            return target.getIntValue();
        }
    }

    /**
     * Gets (as xml) the "faultReason" element
     */
    public org.apache.xmlbeans.XmlInt xgetFaultReason() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt) get_store()
                                                      .find_element_user(FAULTREASON$6,
                    0);

            return target;
        }
    }

    /**
     * True if has "faultReason" element
     */
    public boolean isSetFaultReason() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(FAULTREASON$6) != 0;
        }
    }

    /**
     * Sets the "faultReason" element
     */
    public void setFaultReason(int faultReason) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(FAULTREASON$6,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(FAULTREASON$6);
            }

            target.setIntValue(faultReason);
        }
    }

    /**
     * Sets (as xml) the "faultReason" element
     */
    public void xsetFaultReason(org.apache.xmlbeans.XmlInt faultReason) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt) get_store()
                                                      .find_element_user(FAULTREASON$6,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlInt) get_store()
                                                          .add_element_user(FAULTREASON$6);
            }

            target.set(faultReason);
        }
    }

    /**
     * Unsets the "faultReason" element
     */
    public void unsetFaultReason() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(FAULTREASON$6, 0);
        }
    }

    /**
     * Gets the "faultStation" element
     */
    public java.lang.String getFaultStation() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(FAULTSTATION$8,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "faultStation" element
     */
    public org.apache.xmlbeans.XmlString xgetFaultStation() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(FAULTSTATION$8,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "faultStation" element
     */
    public boolean isNilFaultStation() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(FAULTSTATION$8,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "faultStation" element
     */
    public boolean isSetFaultStation() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(FAULTSTATION$8) != 0;
        }
    }

    /**
     * Sets the "faultStation" element
     */
    public void setFaultStation(java.lang.String faultStation) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(FAULTSTATION$8,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(FAULTSTATION$8);
            }

            target.setStringValue(faultStation);
        }
    }

    /**
     * Sets (as xml) the "faultStation" element
     */
    public void xsetFaultStation(org.apache.xmlbeans.XmlString faultStation) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(FAULTSTATION$8,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(FAULTSTATION$8);
            }

            target.set(faultStation);
        }
    }

    /**
     * Nils the "faultStation" element
     */
    public void setNilFaultStation() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(FAULTSTATION$8,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(FAULTSTATION$8);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "faultStation" element
     */
    public void unsetFaultStation() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(FAULTSTATION$8, 0);
        }
    }

    /**
     * Gets array of all "item" elements
     */
    public aero.nettracer.serviceprovider.wt_1_0.common.xsd.Item[] getItemArray() {
        synchronized (monitor()) {
            check_orphaned();

            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(ITEM$10, targetList);

            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Item[] result = new aero.nettracer.serviceprovider.wt_1_0.common.xsd.Item[targetList.size()];
            targetList.toArray(result);

            return result;
        }
    }

    /**
     * Gets ith "item" element
     */
    public aero.nettracer.serviceprovider.wt_1_0.common.xsd.Item getItemArray(
        int i) {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Item target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Item) get_store()
                                                                                 .find_element_user(ITEM$10,
                    i);

            if (target == null) {
                throw new IndexOutOfBoundsException();
            }

            return target;
        }
    }

    /**
     * Tests for nil ith "item" element
     */
    public boolean isNilItemArray(int i) {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Item target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Item) get_store()
                                                                                 .find_element_user(ITEM$10,
                    i);

            if (target == null) {
                throw new IndexOutOfBoundsException();
            }

            return target.isNil();
        }
    }

    /**
     * Returns number of "item" element
     */
    public int sizeOfItemArray() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(ITEM$10);
        }
    }

    /**
     * Sets array of all "item" element
     */
    public void setItemArray(
        aero.nettracer.serviceprovider.wt_1_0.common.xsd.Item[] itemArray) {
        synchronized (monitor()) {
            check_orphaned();
            arraySetterHelper(itemArray, ITEM$10);
        }
    }

    /**
     * Sets ith "item" element
     */
    public void setItemArray(int i,
        aero.nettracer.serviceprovider.wt_1_0.common.xsd.Item item) {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Item target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Item) get_store()
                                                                                 .find_element_user(ITEM$10,
                    i);

            if (target == null) {
                throw new IndexOutOfBoundsException();
            }

            target.set(item);
        }
    }

    /**
     * Nils the ith "item" element
     */
    public void setNilItemArray(int i) {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Item target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Item) get_store()
                                                                                 .find_element_user(ITEM$10,
                    i);

            if (target == null) {
                throw new IndexOutOfBoundsException();
            }

            target.setNil();
        }
    }

    /**
     * Inserts and returns a new empty value (as xml) as the ith "item" element
     */
    public aero.nettracer.serviceprovider.wt_1_0.common.xsd.Item insertNewItem(
        int i) {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Item target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Item) get_store()
                                                                                 .insert_element_user(ITEM$10,
                    i);

            return target;
        }
    }

    /**
     * Appends and returns a new empty value (as xml) as the last "item" element
     */
    public aero.nettracer.serviceprovider.wt_1_0.common.xsd.Item addNewItem() {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Item target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Item) get_store()
                                                                                 .add_element_user(ITEM$10);

            return target;
        }
    }

    /**
     * Removes the ith "item" element
     */
    public void removeItem(int i) {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(ITEM$10, i);
        }
    }

    /**
     * Gets the "numberPaxAffected" element
     */
    public int getNumberPaxAffected() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(NUMBERPAXAFFECTED$12,
                    0);

            if (target == null) {
                return 0;
            }

            return target.getIntValue();
        }
    }

    /**
     * Gets (as xml) the "numberPaxAffected" element
     */
    public org.apache.xmlbeans.XmlInt xgetNumberPaxAffected() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt) get_store()
                                                      .find_element_user(NUMBERPAXAFFECTED$12,
                    0);

            return target;
        }
    }

    /**
     * True if has "numberPaxAffected" element
     */
    public boolean isSetNumberPaxAffected() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(NUMBERPAXAFFECTED$12) != 0;
        }
    }

    /**
     * Sets the "numberPaxAffected" element
     */
    public void setNumberPaxAffected(int numberPaxAffected) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(NUMBERPAXAFFECTED$12,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(NUMBERPAXAFFECTED$12);
            }

            target.setIntValue(numberPaxAffected);
        }
    }

    /**
     * Sets (as xml) the "numberPaxAffected" element
     */
    public void xsetNumberPaxAffected(
        org.apache.xmlbeans.XmlInt numberPaxAffected) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt) get_store()
                                                      .find_element_user(NUMBERPAXAFFECTED$12,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlInt) get_store()
                                                          .add_element_user(NUMBERPAXAFFECTED$12);
            }

            target.set(numberPaxAffected);
        }
    }

    /**
     * Unsets the "numberPaxAffected" element
     */
    public void unsetNumberPaxAffected() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(NUMBERPAXAFFECTED$12, 0);
        }
    }

    /**
     * Gets array of all "pax" elements
     */
    public aero.nettracer.serviceprovider.wt_1_0.common.xsd.Passenger[] getPaxArray() {
        synchronized (monitor()) {
            check_orphaned();

            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(PAX$14, targetList);

            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Passenger[] result = new aero.nettracer.serviceprovider.wt_1_0.common.xsd.Passenger[targetList.size()];
            targetList.toArray(result);

            return result;
        }
    }

    /**
     * Gets ith "pax" element
     */
    public aero.nettracer.serviceprovider.wt_1_0.common.xsd.Passenger getPaxArray(
        int i) {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Passenger target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Passenger) get_store()
                                                                                      .find_element_user(PAX$14,
                    i);

            if (target == null) {
                throw new IndexOutOfBoundsException();
            }

            return target;
        }
    }

    /**
     * Tests for nil ith "pax" element
     */
    public boolean isNilPaxArray(int i) {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Passenger target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Passenger) get_store()
                                                                                      .find_element_user(PAX$14,
                    i);

            if (target == null) {
                throw new IndexOutOfBoundsException();
            }

            return target.isNil();
        }
    }

    /**
     * Returns number of "pax" element
     */
    public int sizeOfPaxArray() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(PAX$14);
        }
    }

    /**
     * Sets array of all "pax" element
     */
    public void setPaxArray(
        aero.nettracer.serviceprovider.wt_1_0.common.xsd.Passenger[] paxArray) {
        synchronized (monitor()) {
            check_orphaned();
            arraySetterHelper(paxArray, PAX$14);
        }
    }

    /**
     * Sets ith "pax" element
     */
    public void setPaxArray(int i,
        aero.nettracer.serviceprovider.wt_1_0.common.xsd.Passenger pax) {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Passenger target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Passenger) get_store()
                                                                                      .find_element_user(PAX$14,
                    i);

            if (target == null) {
                throw new IndexOutOfBoundsException();
            }

            target.set(pax);
        }
    }

    /**
     * Nils the ith "pax" element
     */
    public void setNilPaxArray(int i) {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Passenger target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Passenger) get_store()
                                                                                      .find_element_user(PAX$14,
                    i);

            if (target == null) {
                throw new IndexOutOfBoundsException();
            }

            target.setNil();
        }
    }

    /**
     * Inserts and returns a new empty value (as xml) as the ith "pax" element
     */
    public aero.nettracer.serviceprovider.wt_1_0.common.xsd.Passenger insertNewPax(
        int i) {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Passenger target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Passenger) get_store()
                                                                                      .insert_element_user(PAX$14,
                    i);

            return target;
        }
    }

    /**
     * Appends and returns a new empty value (as xml) as the last "pax" element
     */
    public aero.nettracer.serviceprovider.wt_1_0.common.xsd.Passenger addNewPax() {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Passenger target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Passenger) get_store()
                                                                                      .add_element_user(PAX$14);

            return target;
        }
    }

    /**
     * Removes the ith "pax" element
     */
    public void removePax(int i) {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(PAX$14, i);
        }
    }

    /**
     * Gets array of all "paxItinerary" elements
     */
    public aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary[] getPaxItineraryArray() {
        synchronized (monitor()) {
            check_orphaned();

            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(PAXITINERARY$16, targetList);

            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary[] result = new aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary[targetList.size()];
            targetList.toArray(result);

            return result;
        }
    }

    /**
     * Gets ith "paxItinerary" element
     */
    public aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary getPaxItineraryArray(
        int i) {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary) get_store()
                                                                                      .find_element_user(PAXITINERARY$16,
                    i);

            if (target == null) {
                throw new IndexOutOfBoundsException();
            }

            return target;
        }
    }

    /**
     * Tests for nil ith "paxItinerary" element
     */
    public boolean isNilPaxItineraryArray(int i) {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary) get_store()
                                                                                      .find_element_user(PAXITINERARY$16,
                    i);

            if (target == null) {
                throw new IndexOutOfBoundsException();
            }

            return target.isNil();
        }
    }

    /**
     * Returns number of "paxItinerary" element
     */
    public int sizeOfPaxItineraryArray() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(PAXITINERARY$16);
        }
    }

    /**
     * Sets array of all "paxItinerary" element
     */
    public void setPaxItineraryArray(
        aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary[] paxItineraryArray) {
        synchronized (monitor()) {
            check_orphaned();
            arraySetterHelper(paxItineraryArray, PAXITINERARY$16);
        }
    }

    /**
     * Sets ith "paxItinerary" element
     */
    public void setPaxItineraryArray(int i,
        aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary paxItinerary) {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary) get_store()
                                                                                      .find_element_user(PAXITINERARY$16,
                    i);

            if (target == null) {
                throw new IndexOutOfBoundsException();
            }

            target.set(paxItinerary);
        }
    }

    /**
     * Nils the ith "paxItinerary" element
     */
    public void setNilPaxItineraryArray(int i) {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary) get_store()
                                                                                      .find_element_user(PAXITINERARY$16,
                    i);

            if (target == null) {
                throw new IndexOutOfBoundsException();
            }

            target.setNil();
        }
    }

    /**
     * Inserts and returns a new empty value (as xml) as the ith "paxItinerary" element
     */
    public aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary insertNewPaxItinerary(
        int i) {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary) get_store()
                                                                                      .insert_element_user(PAXITINERARY$16,
                    i);

            return target;
        }
    }

    /**
     * Appends and returns a new empty value (as xml) as the last "paxItinerary" element
     */
    public aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary addNewPaxItinerary() {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary) get_store()
                                                                                      .add_element_user(PAXITINERARY$16);

            return target;
        }
    }

    /**
     * Removes the ith "paxItinerary" element
     */
    public void removePaxItinerary(int i) {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(PAXITINERARY$16, i);
        }
    }

    /**
     * Gets the "stationCode" element
     */
    public java.lang.String getStationCode() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(STATIONCODE$18,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "stationCode" element
     */
    public org.apache.xmlbeans.XmlString xgetStationCode() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(STATIONCODE$18,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "stationCode" element
     */
    public boolean isNilStationCode() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(STATIONCODE$18,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "stationCode" element
     */
    public boolean isSetStationCode() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(STATIONCODE$18) != 0;
        }
    }

    /**
     * Sets the "stationCode" element
     */
    public void setStationCode(java.lang.String stationCode) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(STATIONCODE$18,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(STATIONCODE$18);
            }

            target.setStringValue(stationCode);
        }
    }

    /**
     * Sets (as xml) the "stationCode" element
     */
    public void xsetStationCode(org.apache.xmlbeans.XmlString stationCode) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(STATIONCODE$18,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(STATIONCODE$18);
            }

            target.set(stationCode);
        }
    }

    /**
     * Nils the "stationCode" element
     */
    public void setNilStationCode() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(STATIONCODE$18,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(STATIONCODE$18);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "stationCode" element
     */
    public void unsetStationCode() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(STATIONCODE$18, 0);
        }
    }
}
