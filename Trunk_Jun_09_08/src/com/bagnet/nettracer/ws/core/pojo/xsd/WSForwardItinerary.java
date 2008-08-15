/*
 * XML Type:  WS_ForwardItinerary
 * Namespace: http://pojo.core.ws.nettracer.bagnet.com/xsd
 * Java type: com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.core.pojo.xsd;


/**
 * An XML WS_ForwardItinerary(@http://pojo.core.ws.nettracer.bagnet.com/xsd).
 *
 * This is a complex type.
 */
public interface WSForwardItinerary extends org.apache.xmlbeans.XmlObject {
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType) org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(WSForwardItinerary.class.getClassLoader(),
            "schemaorg_apache_xmlbeans.system.s16B510B5563ED0857509A4DB5C2996DF")
                                                                                                                           .resolveHandle("wsforwarditinerary7761type");

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
     * Gets the "arrivedate" element
     */
    java.util.Calendar getArrivedate();

    /**
     * Gets (as xml) the "arrivedate" element
     */
    org.apache.xmlbeans.XmlDateTime xgetArrivedate();

    /**
     * Tests for nil "arrivedate" element
     */
    boolean isNilArrivedate();

    /**
     * True if has "arrivedate" element
     */
    boolean isSetArrivedate();

    /**
     * Sets the "arrivedate" element
     */
    void setArrivedate(java.util.Calendar arrivedate);

    /**
     * Sets (as xml) the "arrivedate" element
     */
    void xsetArrivedate(org.apache.xmlbeans.XmlDateTime arrivedate);

    /**
     * Nils the "arrivedate" element
     */
    void setNilArrivedate();

    /**
     * Unsets the "arrivedate" element
     */
    void unsetArrivedate();

    /**
     * Gets the "departdate" element
     */
    java.util.Calendar getDepartdate();

    /**
     * Gets (as xml) the "departdate" element
     */
    org.apache.xmlbeans.XmlDateTime xgetDepartdate();

    /**
     * Tests for nil "departdate" element
     */
    boolean isNilDepartdate();

    /**
     * True if has "departdate" element
     */
    boolean isSetDepartdate();

    /**
     * Sets the "departdate" element
     */
    void setDepartdate(java.util.Calendar departdate);

    /**
     * Sets (as xml) the "departdate" element
     */
    void xsetDepartdate(org.apache.xmlbeans.XmlDateTime departdate);

    /**
     * Nils the "departdate" element
     */
    void setNilDepartdate();

    /**
     * Unsets the "departdate" element
     */
    void unsetDepartdate();

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
     * Gets the "legfrom" element
     */
    java.lang.String getLegfrom();

    /**
     * Gets (as xml) the "legfrom" element
     */
    org.apache.xmlbeans.XmlString xgetLegfrom();

    /**
     * Tests for nil "legfrom" element
     */
    boolean isNilLegfrom();

    /**
     * True if has "legfrom" element
     */
    boolean isSetLegfrom();

    /**
     * Sets the "legfrom" element
     */
    void setLegfrom(java.lang.String legfrom);

    /**
     * Sets (as xml) the "legfrom" element
     */
    void xsetLegfrom(org.apache.xmlbeans.XmlString legfrom);

    /**
     * Nils the "legfrom" element
     */
    void setNilLegfrom();

    /**
     * Unsets the "legfrom" element
     */
    void unsetLegfrom();

    /**
     * Gets the "legto" element
     */
    java.lang.String getLegto();

    /**
     * Gets (as xml) the "legto" element
     */
    org.apache.xmlbeans.XmlString xgetLegto();

    /**
     * Tests for nil "legto" element
     */
    boolean isNilLegto();

    /**
     * True if has "legto" element
     */
    boolean isSetLegto();

    /**
     * Sets the "legto" element
     */
    void setLegto(java.lang.String legto);

    /**
     * Sets (as xml) the "legto" element
     */
    void xsetLegto(org.apache.xmlbeans.XmlString legto);

    /**
     * Nils the "legto" element
     */
    void setNilLegto();

    /**
     * Unsets the "legto" element
     */
    void unsetLegto();

    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    public static final class Factory {
        private Factory() {
        } // No instance of this class allowed

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary newInstance() {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                          .newInstance(type,
                null);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary newInstance(
            org.apache.xmlbeans.XmlOptions options) {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                          .newInstance(type,
                options);
        }

        /** @param xmlAsString the string value to parse */
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary parse(
            java.lang.String xmlAsString)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                          .parse(xmlAsString,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary parse(
            java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                          .parse(xmlAsString,
                type, options);
        }

        /** @param file the file from which to load an xml document */
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary parse(
            java.io.File file)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                          .parse(file,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary parse(
            java.io.File file, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                          .parse(file,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary parse(
            java.net.URL u)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                          .parse(u,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary parse(
            java.net.URL u, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                          .parse(u,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary parse(
            java.io.InputStream is)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                          .parse(is,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary parse(
            java.io.InputStream is, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                          .parse(is,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary parse(
            java.io.Reader r)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                          .parse(r,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary parse(
            java.io.Reader r, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                          .parse(r,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary parse(
            javax.xml.stream.XMLStreamReader sr)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                          .parse(sr,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary parse(
            javax.xml.stream.XMLStreamReader sr,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                          .parse(sr,
                type, options);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary parse(
            org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                          .parse(node,
                type, null);
        }

        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary parse(
            org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                          .parse(node,
                type, options);
        }

        /** @deprecated {@link XMLInputStream} */
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary parse(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                          .parse(xis,
                type, null);
        }

        /** @deprecated {@link XMLInputStream} */
        public static com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary parse(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (com.bagnet.nettracer.ws.core.pojo.xsd.WSForwardItinerary) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
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
