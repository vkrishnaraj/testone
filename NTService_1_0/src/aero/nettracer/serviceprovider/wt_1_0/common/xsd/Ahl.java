/*
 * XML Type:  Ahl
 * Namespace: http://common.wt_1_0.serviceprovider.nettracer.aero/xsd
 * Java type: aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl
 *
 * Automatically generated - do not modify.
 */
package aero.nettracer.serviceprovider.wt_1_0.common.xsd;


/**
 * An XML Ahl(@http://common.wt_1_0.serviceprovider.nettracer.aero/xsd).
 *
 * This is a complex type.
 */
public interface Ahl extends org.apache.xmlbeans.XmlObject {
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType) org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(Ahl.class.getClassLoader(),
            "schemaorg_apache_xmlbeans.system.sBCED675595897F865A233256B59F19BA")
                                                                                                                           .resolveHandle("ahla120type");

    /**
     * Gets the "ahlId" element
     */
    java.lang.String getAhlId();

    /**
     * Gets (as xml) the "ahlId" element
     */
    org.apache.xmlbeans.XmlString xgetAhlId();

    /**
     * Tests for nil "ahlId" element
     */
    boolean isNilAhlId();

    /**
     * True if has "ahlId" element
     */
    boolean isSetAhlId();

    /**
     * Sets the "ahlId" element
     */
    void setAhlId(java.lang.String ahlId);

    /**
     * Sets (as xml) the "ahlId" element
     */
    void xsetAhlId(org.apache.xmlbeans.XmlString ahlId);

    /**
     * Nils the "ahlId" element
     */
    void setNilAhlId();

    /**
     * Unsets the "ahlId" element
     */
    void unsetAhlId();

    /**
     * Gets array of all "bagItinerary" elements
     */
    aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary[] getBagItineraryArray();

    /**
     * Gets ith "bagItinerary" element
     */
    aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary getBagItineraryArray(
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
        aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary[] bagItineraryArray);

    /**
     * Sets ith "bagItinerary" element
     */
    void setBagItineraryArray(int i,
        aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary bagItinerary);

    /**
     * Nils the ith "bagItinerary" element
     */
    void setNilBagItineraryArray(int i);

    /**
     * Inserts and returns a new empty value (as xml) as the ith "bagItinerary" element
     */
    aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary insertNewBagItinerary(
        int i);

    /**
     * Appends and returns a new empty value (as xml) as the last "bagItinerary" element
     */
    aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary addNewBagItinerary();

    /**
     * Removes the ith "bagItinerary" element
     */
    void removeBagItinerary(int i);

    /**
     * Gets array of all "claimCheck" elements
     */
    aero.nettracer.serviceprovider.wt_1_0.common.xsd.ClaimCheck[] getClaimCheckArray();

    /**
     * Gets ith "claimCheck" element
     */
    aero.nettracer.serviceprovider.wt_1_0.common.xsd.ClaimCheck getClaimCheckArray(
        int i);

    /**
     * Tests for nil ith "claimCheck" element
     */
    boolean isNilClaimCheckArray(int i);

    /**
     * Returns number of "claimCheck" element
     */
    int sizeOfClaimCheckArray();

    /**
     * Sets array of all "claimCheck" element
     */
    void setClaimCheckArray(
        aero.nettracer.serviceprovider.wt_1_0.common.xsd.ClaimCheck[] claimCheckArray);

    /**
     * Sets ith "claimCheck" element
     */
    void setClaimCheckArray(int i,
        aero.nettracer.serviceprovider.wt_1_0.common.xsd.ClaimCheck claimCheck);

    /**
     * Nils the ith "claimCheck" element
     */
    void setNilClaimCheckArray(int i);

    /**
     * Inserts and returns a new empty value (as xml) as the ith "claimCheck" element
     */
    aero.nettracer.serviceprovider.wt_1_0.common.xsd.ClaimCheck insertNewClaimCheck(
        int i);

    /**
     * Appends and returns a new empty value (as xml) as the last "claimCheck" element
     */
    aero.nettracer.serviceprovider.wt_1_0.common.xsd.ClaimCheck addNewClaimCheck();

    /**
     * Removes the ith "claimCheck" element
     */
    void removeClaimCheck(int i);

    /**
     * Gets the "faultReason" element
     */
    int getFaultReason();

    /**
     * Gets (as xml) the "faultReason" element
     */
    org.apache.xmlbeans.XmlInt xgetFaultReason();

    /**
     * True if has "faultReason" element
     */
    boolean isSetFaultReason();

    /**
     * Sets the "faultReason" element
     */
    void setFaultReason(int faultReason);

    /**
     * Sets (as xml) the "faultReason" element
     */
    void xsetFaultReason(org.apache.xmlbeans.XmlInt faultReason);

    /**
     * Unsets the "faultReason" element
     */
    void unsetFaultReason();

    /**
     * Gets the "faultStation" element
     */
    java.lang.String getFaultStation();

    /**
     * Gets (as xml) the "faultStation" element
     */
    org.apache.xmlbeans.XmlString xgetFaultStation();

    /**
     * Tests for nil "faultStation" element
     */
    boolean isNilFaultStation();

    /**
     * True if has "faultStation" element
     */
    boolean isSetFaultStation();

    /**
     * Sets the "faultStation" element
     */
    void setFaultStation(java.lang.String faultStation);

    /**
     * Sets (as xml) the "faultStation" element
     */
    void xsetFaultStation(org.apache.xmlbeans.XmlString faultStation);

    /**
     * Nils the "faultStation" element
     */
    void setNilFaultStation();

    /**
     * Unsets the "faultStation" element
     */
    void unsetFaultStation();

    /**
     * Gets array of all "item" elements
     */
    aero.nettracer.serviceprovider.wt_1_0.common.xsd.Item[] getItemArray();

    /**
     * Gets ith "item" element
     */
    aero.nettracer.serviceprovider.wt_1_0.common.xsd.Item getItemArray(int i);

    /**
     * Tests for nil ith "item" element
     */
    boolean isNilItemArray(int i);

    /**
     * Returns number of "item" element
     */
    int sizeOfItemArray();

    /**
     * Sets array of all "item" element
     */
    void setItemArray(
        aero.nettracer.serviceprovider.wt_1_0.common.xsd.Item[] itemArray);

    /**
     * Sets ith "item" element
     */
    void setItemArray(int i,
        aero.nettracer.serviceprovider.wt_1_0.common.xsd.Item item);

    /**
     * Nils the ith "item" element
     */
    void setNilItemArray(int i);

    /**
     * Inserts and returns a new empty value (as xml) as the ith "item" element
     */
    aero.nettracer.serviceprovider.wt_1_0.common.xsd.Item insertNewItem(int i);

    /**
     * Appends and returns a new empty value (as xml) as the last "item" element
     */
    aero.nettracer.serviceprovider.wt_1_0.common.xsd.Item addNewItem();

    /**
     * Removes the ith "item" element
     */
    void removeItem(int i);

    /**
     * Gets the "numberPaxAffected" element
     */
    int getNumberPaxAffected();

    /**
     * Gets (as xml) the "numberPaxAffected" element
     */
    org.apache.xmlbeans.XmlInt xgetNumberPaxAffected();

    /**
     * True if has "numberPaxAffected" element
     */
    boolean isSetNumberPaxAffected();

    /**
     * Sets the "numberPaxAffected" element
     */
    void setNumberPaxAffected(int numberPaxAffected);

    /**
     * Sets (as xml) the "numberPaxAffected" element
     */
    void xsetNumberPaxAffected(org.apache.xmlbeans.XmlInt numberPaxAffected);

    /**
     * Unsets the "numberPaxAffected" element
     */
    void unsetNumberPaxAffected();

    /**
     * Gets array of all "pax" elements
     */
    aero.nettracer.serviceprovider.wt_1_0.common.xsd.Passenger[] getPaxArray();

    /**
     * Gets ith "pax" element
     */
    aero.nettracer.serviceprovider.wt_1_0.common.xsd.Passenger getPaxArray(
        int i);

    /**
     * Tests for nil ith "pax" element
     */
    boolean isNilPaxArray(int i);

    /**
     * Returns number of "pax" element
     */
    int sizeOfPaxArray();

    /**
     * Sets array of all "pax" element
     */
    void setPaxArray(
        aero.nettracer.serviceprovider.wt_1_0.common.xsd.Passenger[] paxArray);

    /**
     * Sets ith "pax" element
     */
    void setPaxArray(int i,
        aero.nettracer.serviceprovider.wt_1_0.common.xsd.Passenger pax);

    /**
     * Nils the ith "pax" element
     */
    void setNilPaxArray(int i);

    /**
     * Inserts and returns a new empty value (as xml) as the ith "pax" element
     */
    aero.nettracer.serviceprovider.wt_1_0.common.xsd.Passenger insertNewPax(
        int i);

    /**
     * Appends and returns a new empty value (as xml) as the last "pax" element
     */
    aero.nettracer.serviceprovider.wt_1_0.common.xsd.Passenger addNewPax();

    /**
     * Removes the ith "pax" element
     */
    void removePax(int i);

    /**
     * Gets array of all "paxItinerary" elements
     */
    aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary[] getPaxItineraryArray();

    /**
     * Gets ith "paxItinerary" element
     */
    aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary getPaxItineraryArray(
        int i);

    /**
     * Tests for nil ith "paxItinerary" element
     */
    boolean isNilPaxItineraryArray(int i);

    /**
     * Returns number of "paxItinerary" element
     */
    int sizeOfPaxItineraryArray();

    /**
     * Sets array of all "paxItinerary" element
     */
    void setPaxItineraryArray(
        aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary[] paxItineraryArray);

    /**
     * Sets ith "paxItinerary" element
     */
    void setPaxItineraryArray(int i,
        aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary paxItinerary);

    /**
     * Nils the ith "paxItinerary" element
     */
    void setNilPaxItineraryArray(int i);

    /**
     * Inserts and returns a new empty value (as xml) as the ith "paxItinerary" element
     */
    aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary insertNewPaxItinerary(
        int i);

    /**
     * Appends and returns a new empty value (as xml) as the last "paxItinerary" element
     */
    aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary addNewPaxItinerary();

    /**
     * Removes the ith "paxItinerary" element
     */
    void removePaxItinerary(int i);

    /**
     * Gets the "stationCode" element
     */
    java.lang.String getStationCode();

    /**
     * Gets (as xml) the "stationCode" element
     */
    org.apache.xmlbeans.XmlString xgetStationCode();

    /**
     * Tests for nil "stationCode" element
     */
    boolean isNilStationCode();

    /**
     * True if has "stationCode" element
     */
    boolean isSetStationCode();

    /**
     * Sets the "stationCode" element
     */
    void setStationCode(java.lang.String stationCode);

    /**
     * Sets (as xml) the "stationCode" element
     */
    void xsetStationCode(org.apache.xmlbeans.XmlString stationCode);

    /**
     * Nils the "stationCode" element
     */
    void setNilStationCode();

    /**
     * Unsets the "stationCode" element
     */
    void unsetStationCode();

    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    public static final class Factory {
        private Factory() {
        } // No instance of this class allowed

        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl newInstance() {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                      .newInstance(type,
                null);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl newInstance(
            org.apache.xmlbeans.XmlOptions options) {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                      .newInstance(type,
                options);
        }

        /** @param xmlAsString the string value to parse */
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl parse(
            java.lang.String xmlAsString)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                      .parse(xmlAsString,
                type, null);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl parse(
            java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                      .parse(xmlAsString,
                type, options);
        }

        /** @param file the file from which to load an xml document */
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl parse(
            java.io.File file)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                      .parse(file,
                type, null);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl parse(
            java.io.File file, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                      .parse(file,
                type, options);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl parse(
            java.net.URL u)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                      .parse(u,
                type, null);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl parse(
            java.net.URL u, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                      .parse(u,
                type, options);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl parse(
            java.io.InputStream is)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                      .parse(is,
                type, null);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl parse(
            java.io.InputStream is, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                      .parse(is,
                type, options);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl parse(
            java.io.Reader r)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                      .parse(r,
                type, null);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl parse(
            java.io.Reader r, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                      .parse(r,
                type, options);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl parse(
            javax.xml.stream.XMLStreamReader sr)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                      .parse(sr,
                type, null);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl parse(
            javax.xml.stream.XMLStreamReader sr,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                      .parse(sr,
                type, options);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl parse(
            org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                      .parse(node,
                type, null);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl parse(
            org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                      .parse(node,
                type, options);
        }

        /** @deprecated {@link XMLInputStream} */
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl parse(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                      .parse(xis,
                type, null);
        }

        /** @deprecated {@link XMLInputStream} */
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl parse(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
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
