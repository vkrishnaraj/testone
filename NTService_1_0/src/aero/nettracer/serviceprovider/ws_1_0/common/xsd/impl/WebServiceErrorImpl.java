/*
 * XML Type:  WebServiceError
 * Namespace: http://common.ws_1_0.serviceprovider.nettracer.aero/xsd
 * Java type: aero.nettracer.serviceprovider.ws_1_0.common.xsd.WebServiceError
 *
 * Automatically generated - do not modify.
 */
package aero.nettracer.serviceprovider.ws_1_0.common.xsd.impl;

/**
 * An XML WebServiceError(@http://common.ws_1_0.serviceprovider.nettracer.aero/xsd).
 *
 * This is a complex type.
 */
public class WebServiceErrorImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
    implements aero.nettracer.serviceprovider.ws_1_0.common.xsd.WebServiceError {
    private static final javax.xml.namespace.QName DESCRIPTION$0 = new javax.xml.namespace.QName("http://common.ws_1_0.serviceprovider.nettracer.aero/xsd",
            "description");
    private static final javax.xml.namespace.QName CONFIGURATIONERROR$2 = new javax.xml.namespace.QName("http://common.ws_1_0.serviceprovider.nettracer.aero/xsd",
            "CONFIGURATION_ERROR");
    private static final javax.xml.namespace.QName UNEXPECTEDEXCEPTION$4 = new javax.xml.namespace.QName("http://common.ws_1_0.serviceprovider.nettracer.aero/xsd",
            "UNEXPECTED_EXCEPTION");
    private static final javax.xml.namespace.QName USERNOTAUTHORIZED$6 = new javax.xml.namespace.QName("http://common.ws_1_0.serviceprovider.nettracer.aero/xsd",
            "USER_NOT_AUTHORIZED");

    public WebServiceErrorImpl(org.apache.xmlbeans.SchemaType sType) {
        super(sType);
    }

    /**
     * Gets the "description" element
     */
    public java.lang.String getDescription() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(DESCRIPTION$0,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "description" element
     */
    public org.apache.xmlbeans.XmlString xgetDescription() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(DESCRIPTION$0,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "description" element
     */
    public boolean isNilDescription() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(DESCRIPTION$0,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "description" element
     */
    public boolean isSetDescription() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(DESCRIPTION$0) != 0;
        }
    }

    /**
     * Sets the "description" element
     */
    public void setDescription(java.lang.String description) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(DESCRIPTION$0,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(DESCRIPTION$0);
            }

            target.setStringValue(description);
        }
    }

    /**
     * Sets (as xml) the "description" element
     */
    public void xsetDescription(org.apache.xmlbeans.XmlString description) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(DESCRIPTION$0,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(DESCRIPTION$0);
            }

            target.set(description);
        }
    }

    /**
     * Nils the "description" element
     */
    public void setNilDescription() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(DESCRIPTION$0,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(DESCRIPTION$0);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "description" element
     */
    public void unsetDescription() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(DESCRIPTION$0, 0);
        }
    }

    /**
     * Gets the "CONFIGURATION_ERROR" element
     */
    public java.lang.String getCONFIGURATIONERROR() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(CONFIGURATIONERROR$2,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "CONFIGURATION_ERROR" element
     */
    public org.apache.xmlbeans.XmlString xgetCONFIGURATIONERROR() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(CONFIGURATIONERROR$2,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "CONFIGURATION_ERROR" element
     */
    public boolean isNilCONFIGURATIONERROR() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(CONFIGURATIONERROR$2,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "CONFIGURATION_ERROR" element
     */
    public boolean isSetCONFIGURATIONERROR() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(CONFIGURATIONERROR$2) != 0;
        }
    }

    /**
     * Sets the "CONFIGURATION_ERROR" element
     */
    public void setCONFIGURATIONERROR(java.lang.String configurationerror) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(CONFIGURATIONERROR$2,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(CONFIGURATIONERROR$2);
            }

            target.setStringValue(configurationerror);
        }
    }

    /**
     * Sets (as xml) the "CONFIGURATION_ERROR" element
     */
    public void xsetCONFIGURATIONERROR(
        org.apache.xmlbeans.XmlString configurationerror) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(CONFIGURATIONERROR$2,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(CONFIGURATIONERROR$2);
            }

            target.set(configurationerror);
        }
    }

    /**
     * Nils the "CONFIGURATION_ERROR" element
     */
    public void setNilCONFIGURATIONERROR() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(CONFIGURATIONERROR$2,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(CONFIGURATIONERROR$2);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "CONFIGURATION_ERROR" element
     */
    public void unsetCONFIGURATIONERROR() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(CONFIGURATIONERROR$2, 0);
        }
    }

    /**
     * Gets the "UNEXPECTED_EXCEPTION" element
     */
    public java.lang.String getUNEXPECTEDEXCEPTION() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(UNEXPECTEDEXCEPTION$4,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "UNEXPECTED_EXCEPTION" element
     */
    public org.apache.xmlbeans.XmlString xgetUNEXPECTEDEXCEPTION() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(UNEXPECTEDEXCEPTION$4,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "UNEXPECTED_EXCEPTION" element
     */
    public boolean isNilUNEXPECTEDEXCEPTION() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(UNEXPECTEDEXCEPTION$4,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "UNEXPECTED_EXCEPTION" element
     */
    public boolean isSetUNEXPECTEDEXCEPTION() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(UNEXPECTEDEXCEPTION$4) != 0;
        }
    }

    /**
     * Sets the "UNEXPECTED_EXCEPTION" element
     */
    public void setUNEXPECTEDEXCEPTION(java.lang.String unexpectedexception) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(UNEXPECTEDEXCEPTION$4,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(UNEXPECTEDEXCEPTION$4);
            }

            target.setStringValue(unexpectedexception);
        }
    }

    /**
     * Sets (as xml) the "UNEXPECTED_EXCEPTION" element
     */
    public void xsetUNEXPECTEDEXCEPTION(
        org.apache.xmlbeans.XmlString unexpectedexception) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(UNEXPECTEDEXCEPTION$4,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(UNEXPECTEDEXCEPTION$4);
            }

            target.set(unexpectedexception);
        }
    }

    /**
     * Nils the "UNEXPECTED_EXCEPTION" element
     */
    public void setNilUNEXPECTEDEXCEPTION() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(UNEXPECTEDEXCEPTION$4,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(UNEXPECTEDEXCEPTION$4);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "UNEXPECTED_EXCEPTION" element
     */
    public void unsetUNEXPECTEDEXCEPTION() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(UNEXPECTEDEXCEPTION$4, 0);
        }
    }

    /**
     * Gets the "USER_NOT_AUTHORIZED" element
     */
    public java.lang.String getUSERNOTAUTHORIZED() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(USERNOTAUTHORIZED$6,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "USER_NOT_AUTHORIZED" element
     */
    public org.apache.xmlbeans.XmlString xgetUSERNOTAUTHORIZED() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(USERNOTAUTHORIZED$6,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "USER_NOT_AUTHORIZED" element
     */
    public boolean isNilUSERNOTAUTHORIZED() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(USERNOTAUTHORIZED$6,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "USER_NOT_AUTHORIZED" element
     */
    public boolean isSetUSERNOTAUTHORIZED() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(USERNOTAUTHORIZED$6) != 0;
        }
    }

    /**
     * Sets the "USER_NOT_AUTHORIZED" element
     */
    public void setUSERNOTAUTHORIZED(java.lang.String usernotauthorized) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(USERNOTAUTHORIZED$6,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(USERNOTAUTHORIZED$6);
            }

            target.setStringValue(usernotauthorized);
        }
    }

    /**
     * Sets (as xml) the "USER_NOT_AUTHORIZED" element
     */
    public void xsetUSERNOTAUTHORIZED(
        org.apache.xmlbeans.XmlString usernotauthorized) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(USERNOTAUTHORIZED$6,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(USERNOTAUTHORIZED$6);
            }

            target.set(usernotauthorized);
        }
    }

    /**
     * Nils the "USER_NOT_AUTHORIZED" element
     */
    public void setNilUSERNOTAUTHORIZED() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(USERNOTAUTHORIZED$6,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(USERNOTAUTHORIZED$6);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "USER_NOT_AUTHORIZED" element
     */
    public void unsetUSERNOTAUTHORIZED() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(USERNOTAUTHORIZED$6, 0);
        }
    }
}
