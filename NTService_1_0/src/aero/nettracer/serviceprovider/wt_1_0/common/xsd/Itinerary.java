/*
 * XML Type:  Itinerary
 * Namespace: http://common.wt_1_0.serviceprovider.nettracer.aero/xsd
 * Java type: aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary
 *
 * Automatically generated - do not modify.
 */
package aero.nettracer.serviceprovider.wt_1_0.common.xsd;


/**
 * An XML Itinerary(@http://common.wt_1_0.serviceprovider.nettracer.aero/xsd).
 *
 * This is a complex type.
 */
public interface Itinerary extends org.apache.xmlbeans.XmlObject {
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType) org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(Itinerary.class.getClassLoader(),
            "schemaorg_apache_xmlbeans.system.sBCED675595897F865A233256B59F19BA")
                                                                                                                           .resolveHandle("itinerary91c6type");

    /**
     * Gets the "airline" element
     */
    java.lang.String getAirline();

    /**
     * Gets (as xml) the "airline" element
     */
    org.apache.xmlbeans.XmlString xgetAirline();

    /**
     * Tests for nil "airline" element
     */
    boolean isNilAirline();

    /**
     * True if has "airline" element
     */
    boolean isSetAirline();

    /**
     * Sets the "airline" element
     */
    void setAirline(java.lang.String airline);

    /**
     * Sets (as xml) the "airline" element
     */
    void xsetAirline(org.apache.xmlbeans.XmlString airline);

    /**
     * Nils the "airline" element
     */
    void setNilAirline();

    /**
     * Unsets the "airline" element
     */
    void unsetAirline();

    /**
     * Gets the "arrivalCity" element
     */
    java.lang.String getArrivalCity();

    /**
     * Gets (as xml) the "arrivalCity" element
     */
    org.apache.xmlbeans.XmlString xgetArrivalCity();

    /**
     * Tests for nil "arrivalCity" element
     */
    boolean isNilArrivalCity();

    /**
     * True if has "arrivalCity" element
     */
    boolean isSetArrivalCity();

    /**
     * Sets the "arrivalCity" element
     */
    void setArrivalCity(java.lang.String arrivalCity);

    /**
     * Sets (as xml) the "arrivalCity" element
     */
    void xsetArrivalCity(org.apache.xmlbeans.XmlString arrivalCity);

    /**
     * Nils the "arrivalCity" element
     */
    void setNilArrivalCity();

    /**
     * Unsets the "arrivalCity" element
     */
    void unsetArrivalCity();

    /**
     * Gets the "departureCity" element
     */
    java.lang.String getDepartureCity();

    /**
     * Gets (as xml) the "departureCity" element
     */
    org.apache.xmlbeans.XmlString xgetDepartureCity();

    /**
     * Tests for nil "departureCity" element
     */
    boolean isNilDepartureCity();

    /**
     * True if has "departureCity" element
     */
    boolean isSetDepartureCity();

    /**
     * Sets the "departureCity" element
     */
    void setDepartureCity(java.lang.String departureCity);

    /**
     * Sets (as xml) the "departureCity" element
     */
    void xsetDepartureCity(org.apache.xmlbeans.XmlString departureCity);

    /**
     * Nils the "departureCity" element
     */
    void setNilDepartureCity();

    /**
     * Unsets the "departureCity" element
     */
    void unsetDepartureCity();

    /**
     * Gets the "flightDate" element
     */
    java.util.Calendar getFlightDate();

    /**
     * Gets (as xml) the "flightDate" element
     */
    org.apache.xmlbeans.XmlDateTime xgetFlightDate();

    /**
     * Tests for nil "flightDate" element
     */
    boolean isNilFlightDate();

    /**
     * True if has "flightDate" element
     */
    boolean isSetFlightDate();

    /**
     * Sets the "flightDate" element
     */
    void setFlightDate(java.util.Calendar flightDate);

    /**
     * Sets (as xml) the "flightDate" element
     */
    void xsetFlightDate(org.apache.xmlbeans.XmlDateTime flightDate);

    /**
     * Nils the "flightDate" element
     */
    void setNilFlightDate();

    /**
     * Unsets the "flightDate" element
     */
    void unsetFlightDate();

    /**
     * Gets the "flightnum" element
     */
    java.lang.String getFlightnum();

    /**
     * Gets (as xml) the "flightnum" element
     */
    org.apache.xmlbeans.XmlString xgetFlightnum();

    /**
     * Tests for nil "flightnum" element
     */
    boolean isNilFlightnum();

    /**
     * True if has "flightnum" element
     */
    boolean isSetFlightnum();

    /**
     * Sets the "flightnum" element
     */
    void setFlightnum(java.lang.String flightnum);

    /**
     * Sets (as xml) the "flightnum" element
     */
    void xsetFlightnum(org.apache.xmlbeans.XmlString flightnum);

    /**
     * Nils the "flightnum" element
     */
    void setNilFlightnum();

    /**
     * Unsets the "flightnum" element
     */
    void unsetFlightnum();

    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    public static final class Factory {
        private Factory() {
        } // No instance of this class allowed

        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary newInstance() {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                            .newInstance(type,
                null);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary newInstance(
            org.apache.xmlbeans.XmlOptions options) {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                            .newInstance(type,
                options);
        }

        /** @param xmlAsString the string value to parse */
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary parse(
            java.lang.String xmlAsString)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                            .parse(xmlAsString,
                type, null);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary parse(
            java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                            .parse(xmlAsString,
                type, options);
        }

        /** @param file the file from which to load an xml document */
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary parse(
            java.io.File file)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                            .parse(file,
                type, null);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary parse(
            java.io.File file, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                            .parse(file,
                type, options);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary parse(
            java.net.URL u)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                            .parse(u,
                type, null);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary parse(
            java.net.URL u, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                            .parse(u,
                type, options);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary parse(
            java.io.InputStream is)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                            .parse(is,
                type, null);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary parse(
            java.io.InputStream is, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                            .parse(is,
                type, options);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary parse(
            java.io.Reader r)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                            .parse(r,
                type, null);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary parse(
            java.io.Reader r, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                            .parse(r,
                type, options);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary parse(
            javax.xml.stream.XMLStreamReader sr)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                            .parse(sr,
                type, null);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary parse(
            javax.xml.stream.XMLStreamReader sr,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                            .parse(sr,
                type, options);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary parse(
            org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                            .parse(node,
                type, null);
        }

        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary parse(
            org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                            .parse(node,
                type, options);
        }

        /** @deprecated {@link XMLInputStream} */
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary parse(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                            .parse(xis,
                type, null);
        }

        /** @deprecated {@link XMLInputStream} */
        public static aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary parse(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Itinerary) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
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
