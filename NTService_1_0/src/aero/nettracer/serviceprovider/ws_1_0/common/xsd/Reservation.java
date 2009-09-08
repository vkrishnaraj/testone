/*
 * XML Type:  Reservation
 * Namespace: http://common.ws_1_0.serviceprovider.nettracer.aero/xsd
 * Java type: aero.nettracer.serviceprovider.ws_1_0.common.xsd.Reservation
 *
 * Automatically generated - do not modify.
 */
package aero.nettracer.serviceprovider.ws_1_0.common.xsd;


/**
 * An XML Reservation(@http://common.ws_1_0.serviceprovider.nettracer.aero/xsd).
 *
 * This is a complex type.
 */
public interface Reservation extends org.apache.xmlbeans.XmlObject {
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType) org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(Reservation.class.getClassLoader(),
            "schemaorg_apache_xmlbeans.system.s72AC0240CDCB7D5C0FD050B1FA2B4C94")
                                                                                                                           .resolveHandle("reservation92e8type");

    /**
     * Gets array of all "bagItinerary" elements
     */
    aero.nettracer.serviceprovider.ws_1_0.common.xsd.Itinerary[] getBagItineraryArray();

    /**
     * Gets ith "bagItinerary" element
     */
    aero.nettracer.serviceprovider.ws_1_0.common.xsd.Itinerary getBagItineraryArray(
        int i);

    /**
     * Tests for nil ith "bagItinerary" element
     */
    boolean isNilBagItineraryArray(int i);

    /**
     * Returns number of "bagItinerary" element
     */
    int sizeOfBagItineraryArray();

    /**
     * Sets array of all "bagItinerary" element
     */
    void setBagItineraryArray(
        aero.nettracer.serviceprovider.ws_1_0.common.xsd.Itinerary[] bagItineraryArray);

    /**
     * Sets ith "bagItinerary" element
     */
    void setBagItineraryArray(int i,
        aero.nettracer.serviceprovider.ws_1_0.common.xsd.Itinerary bagItinerary);

    /**
     * Nils the ith "bagItinerary" element
     */
    void setNilBagItineraryArray(int i);

    /**
     * Inserts and returns a new empty value (as xml) as the ith "bagItinerary" element
     */
    aero.nettracer.serviceprovider.ws_1_0.common.xsd.Itinerary insertNewBagItinerary(
        int i);

    /**
     * Appends and returns a new empty value (as xml) as the last "bagItinerary" element
     */
    aero.nettracer.serviceprovider.ws_1_0.common.xsd.Itinerary addNewBagItinerary();

    /**
     * Removes the ith "bagItinerary" element
     */
    void removeBagItinerary(int i);

    /**
     * Gets the "checkedLocation" element
     */
    int getCheckedLocation();

    /**
     * Gets (as xml) the "checkedLocation" element
     */
    org.apache.xmlbeans.XmlInt xgetCheckedLocation();

    /**
     * True if has "checkedLocation" element
     */
    boolean isSetCheckedLocation();

    /**
     * Sets the "checkedLocation" element
     */
    void setCheckedLocation(int checkedLocation);

    /**
     * Sets (as xml) the "checkedLocation" element
     */
    void xsetCheckedLocation(org.apache.xmlbeans.XmlInt checkedLocation);

    /**
     * Unsets the "checkedLocation" element
     */
    void unsetCheckedLocation();

    /**
     * Gets array of all "claimChecks" elements
     */
    aero.nettracer.serviceprovider.ws_1_0.common.xsd.ClaimCheck[] getClaimChecksArray();

    /**
     * Gets ith "claimChecks" element
     */
    aero.nettracer.serviceprovider.ws_1_0.common.xsd.ClaimCheck getClaimChecksArray(
        int i);

    /**
     * Tests for nil ith "claimChecks" element
     */
    boolean isNilClaimChecksArray(int i);

    /**
     * Returns number of "claimChecks" element
     */
    int sizeOfClaimChecksArray();

    /**
     * Sets array of all "claimChecks" element
     */
    void setClaimChecksArray(
        aero.nettracer.serviceprovider.ws_1_0.common.xsd.ClaimCheck[] claimChecksArray);

    /**
     * Sets ith "claimChecks" element
     */
    void setClaimChecksArray(int i,
        aero.nettracer.serviceprovider.ws_1_0.common.xsd.ClaimCheck claimChecks);

    /**
     * Nils the ith "claimChecks" element
     */
    void setNilClaimChecksArray(int i);

    /**
     * Inserts and returns a new empty value (as xml) as the ith "claimChecks" element
     */
    aero.nettracer.serviceprovider.ws_1_0.common.xsd.ClaimCheck insertNewClaimChecks(
        int i);

    /**
     * Appends and returns a new empty value (as xml) as the last "claimChecks" element
     */
    aero.nettracer.serviceprovider.ws_1_0.common.xsd.ClaimCheck addNewClaimChecks();

    /**
     * Removes the ith "claimChecks" element
     */
    void removeClaimChecks(int i);

    /**
     * Gets the "numberChecked" element
     */
    int getNumberChecked();

    /**
     * Gets (as xml) the "numberChecked" element
     */
    org.apache.xmlbeans.XmlInt xgetNumberChecked();

    /**
     * True if has "numberChecked" element
     */
    boolean isSetNumberChecked();

    /**
     * Sets the "numberChecked" element
     */
    void setNumberChecked(int numberChecked);

    /**
     * Sets (as xml) the "numberChecked" element
     */
    void xsetNumberChecked(org.apache.xmlbeans.XmlInt numberChecked);

    /**
     * Unsets the "numberChecked" element
     */
    void unsetNumberChecked();

    /**
     * Gets the "osi" element
     */
    java.lang.String getOsi();

    /**
     * Gets (as xml) the "osi" element
     */
    org.apache.xmlbeans.XmlString xgetOsi();

    /**
     * Tests for nil "osi" element
     */
    boolean isNilOsi();

    /**
     * True if has "osi" element
     */
    boolean isSetOsi();

    /**
     * Sets the "osi" element
     */
    void setOsi(java.lang.String osi);

    /**
     * Sets (as xml) the "osi" element
     */
    void xsetOsi(org.apache.xmlbeans.XmlString osi);

    /**
     * Nils the "osi" element
     */
    void setNilOsi();

    /**
     * Unsets the "osi" element
     */
    void unsetOsi();

    /**
     * Gets array of all "passengerItinerary" elements
     */
    aero.nettracer.serviceprovider.ws_1_0.common.xsd.Itinerary[] getPassengerItineraryArray();

    /**
     * Gets ith "passengerItinerary" element
     */
    aero.nettracer.serviceprovider.ws_1_0.common.xsd.Itinerary getPassengerItineraryArray(
        int i);

    /**
     * Tests for nil ith "passengerItinerary" element
     */
    boolean isNilPassengerItineraryArray(int i);

    /**
     * Returns number of "passengerItinerary" element
     */
    int sizeOfPassengerItineraryArray();

    /**
     * Sets array of all "passengerItinerary" element
     */
    void setPassengerItineraryArray(
        aero.nettracer.serviceprovider.ws_1_0.common.xsd.Itinerary[] passengerItineraryArray);

    /**
     * Sets ith "passengerItinerary" element
     */
    void setPassengerItineraryArray(int i,
        aero.nettracer.serviceprovider.ws_1_0.common.xsd.Itinerary passengerItinerary);

    /**
     * Nils the ith "passengerItinerary" element
     */
    void setNilPassengerItineraryArray(int i);

    /**
     * Inserts and returns a new empty value (as xml) as the ith "passengerItinerary" element
     */
    aero.nettracer.serviceprovider.ws_1_0.common.xsd.Itinerary insertNewPassengerItinerary(
        int i);

    /**
     * Appends and returns a new empty value (as xml) as the last "passengerItinerary" element
     */
    aero.nettracer.serviceprovider.ws_1_0.common.xsd.Itinerary addNewPassengerItinerary();

    /**
     * Removes the ith "passengerItinerary" element
     */
    void removePassengerItinerary(int i);

    /**
     * Gets array of all "passengers" elements
     */
    aero.nettracer.serviceprovider.ws_1_0.common.xsd.Passenger[] getPassengersArray();

    /**
     * Gets ith "passengers" element
     */
    aero.nettracer.serviceprovider.ws_1_0.common.xsd.Passenger getPassengersArray(
        int i);

    /**
     * Tests for nil ith "passengers" element
     */
    boolean isNilPassengersArray(int i);

    /**
     * Returns number of "passengers" element
     */
    int sizeOfPassengersArray();

    /**
     * Sets array of all "passengers" element
     */
    void setPassengersArray(
        aero.nettracer.serviceprovider.ws_1_0.common.xsd.Passenger[] passengersArray);

    /**
     * Sets ith "passengers" element
     */
    void setPassengersArray(int i,
        aero.nettracer.serviceprovider.ws_1_0.common.xsd.Passenger passengers);

    /**
     * Nils the ith "passengers" element
     */
    void setNilPassengersArray(int i);

    /**
     * Inserts and returns a new empty value (as xml) as the ith "passengers" element
     */
    aero.nettracer.serviceprovider.ws_1_0.common.xsd.Passenger insertNewPassengers(
        int i);

    /**
     * Appends and returns a new empty value (as xml) as the last "passengers" element
     */
    aero.nettracer.serviceprovider.ws_1_0.common.xsd.Passenger addNewPassengers();

    /**
     * Removes the ith "passengers" element
     */
    void removePassengers(int i);

    /**
     * Gets the "paxAffected" element
     */
    int getPaxAffected();

    /**
     * Gets (as xml) the "paxAffected" element
     */
    org.apache.xmlbeans.XmlInt xgetPaxAffected();

    /**
     * True if has "paxAffected" element
     */
    boolean isSetPaxAffected();

    /**
     * Sets the "paxAffected" element
     */
    void setPaxAffected(int paxAffected);

    /**
     * Sets (as xml) the "paxAffected" element
     */
    void xsetPaxAffected(org.apache.xmlbeans.XmlInt paxAffected);

    /**
     * Unsets the "paxAffected" element
     */
    void unsetPaxAffected();

    /**
     * Gets the "pnr" element
     */
    java.lang.String getPnr();

    /**
     * Gets (as xml) the "pnr" element
     */
    org.apache.xmlbeans.XmlString xgetPnr();

    /**
     * Tests for nil "pnr" element
     */
    boolean isNilPnr();

    /**
     * True if has "pnr" element
     */
    boolean isSetPnr();

    /**
     * Sets the "pnr" element
     */
    void setPnr(java.lang.String pnr);

    /**
     * Sets (as xml) the "pnr" element
     */
    void xsetPnr(org.apache.xmlbeans.XmlString pnr);

    /**
     * Nils the "pnr" element
     */
    void setNilPnr();

    /**
     * Unsets the "pnr" element
     */
    void unsetPnr();

    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    public static final class Factory {
        private Factory() {
        } // No instance of this class allowed

        public static aero.nettracer.serviceprovider.ws_1_0.common.xsd.Reservation newInstance() {
            return (aero.nettracer.serviceprovider.ws_1_0.common.xsd.Reservation) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                              .newInstance(type,
                null);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.common.xsd.Reservation newInstance(
            org.apache.xmlbeans.XmlOptions options) {
            return (aero.nettracer.serviceprovider.ws_1_0.common.xsd.Reservation) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                              .newInstance(type,
                options);
        }

        /** @param xmlAsString the string value to parse */
        public static aero.nettracer.serviceprovider.ws_1_0.common.xsd.Reservation parse(
            java.lang.String xmlAsString)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.ws_1_0.common.xsd.Reservation) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                              .parse(xmlAsString,
                type, null);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.common.xsd.Reservation parse(
            java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.ws_1_0.common.xsd.Reservation) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                              .parse(xmlAsString,
                type, options);
        }

        /** @param file the file from which to load an xml document */
        public static aero.nettracer.serviceprovider.ws_1_0.common.xsd.Reservation parse(
            java.io.File file)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.ws_1_0.common.xsd.Reservation) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                              .parse(file,
                type, null);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.common.xsd.Reservation parse(
            java.io.File file, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.ws_1_0.common.xsd.Reservation) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                              .parse(file,
                type, options);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.common.xsd.Reservation parse(
            java.net.URL u)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.ws_1_0.common.xsd.Reservation) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                              .parse(u,
                type, null);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.common.xsd.Reservation parse(
            java.net.URL u, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.ws_1_0.common.xsd.Reservation) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                              .parse(u,
                type, options);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.common.xsd.Reservation parse(
            java.io.InputStream is)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.ws_1_0.common.xsd.Reservation) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                              .parse(is,
                type, null);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.common.xsd.Reservation parse(
            java.io.InputStream is, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.ws_1_0.common.xsd.Reservation) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                              .parse(is,
                type, options);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.common.xsd.Reservation parse(
            java.io.Reader r)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.ws_1_0.common.xsd.Reservation) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                              .parse(r,
                type, null);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.common.xsd.Reservation parse(
            java.io.Reader r, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.ws_1_0.common.xsd.Reservation) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                              .parse(r,
                type, options);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.common.xsd.Reservation parse(
            javax.xml.stream.XMLStreamReader sr)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.ws_1_0.common.xsd.Reservation) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                              .parse(sr,
                type, null);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.common.xsd.Reservation parse(
            javax.xml.stream.XMLStreamReader sr,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.ws_1_0.common.xsd.Reservation) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                              .parse(sr,
                type, options);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.common.xsd.Reservation parse(
            org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.ws_1_0.common.xsd.Reservation) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                              .parse(node,
                type, null);
        }

        public static aero.nettracer.serviceprovider.ws_1_0.common.xsd.Reservation parse(
            org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.ws_1_0.common.xsd.Reservation) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                              .parse(node,
                type, options);
        }

        /** @deprecated {@link XMLInputStream} */
        public static aero.nettracer.serviceprovider.ws_1_0.common.xsd.Reservation parse(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (aero.nettracer.serviceprovider.ws_1_0.common.xsd.Reservation) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                              .parse(xis,
                type, null);
        }

        /** @deprecated {@link XMLInputStream} */
        public static aero.nettracer.serviceprovider.ws_1_0.common.xsd.Reservation parse(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (aero.nettracer.serviceprovider.ws_1_0.common.xsd.Reservation) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                              .parse(xis,
                type, options);
        }

        /** @deprecated {@link XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                               .newValidatingXMLInputStream(xis,
                type, null);
        }

        /** @deprecated {@link XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                               .newValidatingXMLInputStream(xis,
                type, options);
        }
    }
}
