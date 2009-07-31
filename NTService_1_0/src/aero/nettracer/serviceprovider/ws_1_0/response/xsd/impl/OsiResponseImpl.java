/*
 * XML Type:  OsiResponse
 * Namespace: http://response.ws_1_0.serviceprovider.nettracer.aero/xsd
 * Java type: aero.nettracer.serviceprovider.ws_1_0.response.xsd.OsiResponse
 *
 * Automatically generated - do not modify.
 */
package aero.nettracer.serviceprovider.ws_1_0.response.xsd.impl;

/**
 * An XML OsiResponse(@http://response.ws_1_0.serviceprovider.nettracer.aero/xsd).
 *
 * This is a complex type.
 */
public class OsiResponseImpl extends aero.nettracer.serviceprovider.ws_1_0.response.xsd.impl.GenericResponseImpl
    implements aero.nettracer.serviceprovider.ws_1_0.response.xsd.OsiResponse {
    private static final javax.xml.namespace.QName OSI$0 = new javax.xml.namespace.QName("http://response.ws_1_0.serviceprovider.nettracer.aero/xsd",
            "osi");

    public OsiResponseImpl(org.apache.xmlbeans.SchemaType sType) {
        super(sType);
    }

    /**
     * Gets the "osi" element
     */
    public java.lang.String getOsi() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(OSI$0,
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
                                                         .find_element_user(OSI$0,
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
                                                         .find_element_user(OSI$0,
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

            return get_store().count_elements(OSI$0) != 0;
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
                                                           .find_element_user(OSI$0,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(OSI$0);
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
                                                         .find_element_user(OSI$0,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(OSI$0);
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
                                                         .find_element_user(OSI$0,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(OSI$0);
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
            get_store().remove_element(OSI$0, 0);
        }
    }
}
