/*
 * XML Type:  EnplanementResponse
 * Namespace: http://response.ws_1_0.serviceprovider.nettracer.aero/xsd
 * Java type: aero.nettracer.serviceprovider.ws_1_0.response.xsd.EnplanementResponse
 *
 * Automatically generated - do not modify.
 */
package aero.nettracer.serviceprovider.ws_1_0.response.xsd.impl;

/**
 * An XML EnplanementResponse(@http://response.ws_1_0.serviceprovider.nettracer.aero/xsd).
 *
 * This is a complex type.
 */
public class EnplanementResponseImpl extends aero.nettracer.serviceprovider.ws_1_0.response.xsd.impl.GenericResponseImpl
    implements aero.nettracer.serviceprovider.ws_1_0.response.xsd.EnplanementResponse {
    private static final javax.xml.namespace.QName ENPLANEMENTS$0 = new javax.xml.namespace.QName("http://response.ws_1_0.serviceprovider.nettracer.aero/xsd",
            "enplanements");

    public EnplanementResponseImpl(org.apache.xmlbeans.SchemaType sType) {
        super(sType);
    }

    /**
     * Gets the "enplanements" element
     */
    public int getEnplanements() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(ENPLANEMENTS$0,
                    0);

            if (target == null) {
                return 0;
            }

            return target.getIntValue();
        }
    }

    /**
     * Gets (as xml) the "enplanements" element
     */
    public org.apache.xmlbeans.XmlInt xgetEnplanements() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt) get_store()
                                                      .find_element_user(ENPLANEMENTS$0,
                    0);

            return target;
        }
    }

    /**
     * True if has "enplanements" element
     */
    public boolean isSetEnplanements() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(ENPLANEMENTS$0) != 0;
        }
    }

    /**
     * Sets the "enplanements" element
     */
    public void setEnplanements(int enplanements) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(ENPLANEMENTS$0,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(ENPLANEMENTS$0);
            }

            target.setIntValue(enplanements);
        }
    }

    /**
     * Sets (as xml) the "enplanements" element
     */
    public void xsetEnplanements(org.apache.xmlbeans.XmlInt enplanements) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt) get_store()
                                                      .find_element_user(ENPLANEMENTS$0,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlInt) get_store()
                                                          .add_element_user(ENPLANEMENTS$0);
            }

            target.set(enplanements);
        }
    }

    /**
     * Unsets the "enplanements" element
     */
    public void unsetEnplanements() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(ENPLANEMENTS$0, 0);
        }
    }
}
