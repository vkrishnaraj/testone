/*
 * XML Type:  WorldTracerResponse
 * Namespace: http://common.wt_1_0.serviceprovider.nettracer.aero/xsd
 * Java type: aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse
 *
 * Automatically generated - do not modify.
 */
package aero.nettracer.serviceprovider.wt_1_0.common.xsd.impl;

/**
 * An XML WorldTracerResponse(@http://common.wt_1_0.serviceprovider.nettracer.aero/xsd).
 *
 * This is a complex type.
 */
public class WorldTracerResponseImpl extends aero.nettracer.serviceprovider.ws_1_0.response.xsd.impl.GenericResponseImpl
    implements aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse {
    private static final javax.xml.namespace.QName ACTIONFILES$0 = new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd",
            "actionFiles");
    private static final javax.xml.namespace.QName AHL$2 = new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd",
            "ahl");
    private static final javax.xml.namespace.QName COUNTS$4 = new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd",
            "counts");
    private static final javax.xml.namespace.QName OHD$6 = new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd",
            "ohd");
    private static final javax.xml.namespace.QName RESPONSEDATA$8 = new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd",
            "responseData");
    private static final javax.xml.namespace.QName RESPONSEID$10 = new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd",
            "responseId");
    private static final javax.xml.namespace.QName SUCCESS$12 = new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd",
            "success");

    public WorldTracerResponseImpl(org.apache.xmlbeans.SchemaType sType) {
        super(sType);
    }

    /**
     * Gets the "actionFiles" element
     */
    public org.apache.xmlbeans.XmlObject getActionFiles() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlObject target = null;
            target = (org.apache.xmlbeans.XmlObject) get_store()
                                                         .find_element_user(ACTIONFILES$0,
                    0);

            if (target == null) {
                return null;
            }

            return target;
        }
    }

    /**
     * Tests for nil "actionFiles" element
     */
    public boolean isNilActionFiles() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlObject target = null;
            target = (org.apache.xmlbeans.XmlObject) get_store()
                                                         .find_element_user(ACTIONFILES$0,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "actionFiles" element
     */
    public boolean isSetActionFiles() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(ACTIONFILES$0) != 0;
        }
    }

    /**
     * Sets the "actionFiles" element
     */
    public void setActionFiles(org.apache.xmlbeans.XmlObject actionFiles) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlObject target = null;
            target = (org.apache.xmlbeans.XmlObject) get_store()
                                                         .find_element_user(ACTIONFILES$0,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlObject) get_store()
                                                             .add_element_user(ACTIONFILES$0);
            }

            target.set(actionFiles);
        }
    }

    /**
     * Appends and returns a new empty "actionFiles" element
     */
    public org.apache.xmlbeans.XmlObject addNewActionFiles() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlObject target = null;
            target = (org.apache.xmlbeans.XmlObject) get_store()
                                                         .add_element_user(ACTIONFILES$0);

            return target;
        }
    }

    /**
     * Nils the "actionFiles" element
     */
    public void setNilActionFiles() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlObject target = null;
            target = (org.apache.xmlbeans.XmlObject) get_store()
                                                         .find_element_user(ACTIONFILES$0,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlObject) get_store()
                                                             .add_element_user(ACTIONFILES$0);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "actionFiles" element
     */
    public void unsetActionFiles() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(ACTIONFILES$0, 0);
        }
    }

    /**
     * Gets the "ahl" element
     */
    public aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl getAhl() {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl) get_store()
                                                                                .find_element_user(AHL$2,
                    0);

            if (target == null) {
                return null;
            }

            return target;
        }
    }

    /**
     * Tests for nil "ahl" element
     */
    public boolean isNilAhl() {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl) get_store()
                                                                                .find_element_user(AHL$2,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "ahl" element
     */
    public boolean isSetAhl() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(AHL$2) != 0;
        }
    }

    /**
     * Sets the "ahl" element
     */
    public void setAhl(aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl ahl) {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl) get_store()
                                                                                .find_element_user(AHL$2,
                    0);

            if (target == null) {
                target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl) get_store()
                                                                                    .add_element_user(AHL$2);
            }

            target.set(ahl);
        }
    }

    /**
     * Appends and returns a new empty "ahl" element
     */
    public aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl addNewAhl() {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl) get_store()
                                                                                .add_element_user(AHL$2);

            return target;
        }
    }

    /**
     * Nils the "ahl" element
     */
    public void setNilAhl() {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl) get_store()
                                                                                .find_element_user(AHL$2,
                    0);

            if (target == null) {
                target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl) get_store()
                                                                                    .add_element_user(AHL$2);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "ahl" element
     */
    public void unsetAhl() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(AHL$2, 0);
        }
    }

    /**
     * Gets the "counts" element
     */
    public org.apache.xmlbeans.XmlObject getCounts() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlObject target = null;
            target = (org.apache.xmlbeans.XmlObject) get_store()
                                                         .find_element_user(COUNTS$4,
                    0);

            if (target == null) {
                return null;
            }

            return target;
        }
    }

    /**
     * Tests for nil "counts" element
     */
    public boolean isNilCounts() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlObject target = null;
            target = (org.apache.xmlbeans.XmlObject) get_store()
                                                         .find_element_user(COUNTS$4,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "counts" element
     */
    public boolean isSetCounts() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(COUNTS$4) != 0;
        }
    }

    /**
     * Sets the "counts" element
     */
    public void setCounts(org.apache.xmlbeans.XmlObject counts) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlObject target = null;
            target = (org.apache.xmlbeans.XmlObject) get_store()
                                                         .find_element_user(COUNTS$4,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlObject) get_store()
                                                             .add_element_user(COUNTS$4);
            }

            target.set(counts);
        }
    }

    /**
     * Appends and returns a new empty "counts" element
     */
    public org.apache.xmlbeans.XmlObject addNewCounts() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlObject target = null;
            target = (org.apache.xmlbeans.XmlObject) get_store()
                                                         .add_element_user(COUNTS$4);

            return target;
        }
    }

    /**
     * Nils the "counts" element
     */
    public void setNilCounts() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlObject target = null;
            target = (org.apache.xmlbeans.XmlObject) get_store()
                                                         .find_element_user(COUNTS$4,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlObject) get_store()
                                                             .add_element_user(COUNTS$4);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "counts" element
     */
    public void unsetCounts() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(COUNTS$4, 0);
        }
    }

    /**
     * Gets the "ohd" element
     */
    public aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd getOhd() {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd) get_store()
                                                                                .find_element_user(OHD$6,
                    0);

            if (target == null) {
                return null;
            }

            return target;
        }
    }

    /**
     * Tests for nil "ohd" element
     */
    public boolean isNilOhd() {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd) get_store()
                                                                                .find_element_user(OHD$6,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "ohd" element
     */
    public boolean isSetOhd() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(OHD$6) != 0;
        }
    }

    /**
     * Sets the "ohd" element
     */
    public void setOhd(aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd ohd) {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd) get_store()
                                                                                .find_element_user(OHD$6,
                    0);

            if (target == null) {
                target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd) get_store()
                                                                                    .add_element_user(OHD$6);
            }

            target.set(ohd);
        }
    }

    /**
     * Appends and returns a new empty "ohd" element
     */
    public aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd addNewOhd() {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd) get_store()
                                                                                .add_element_user(OHD$6);

            return target;
        }
    }

    /**
     * Nils the "ohd" element
     */
    public void setNilOhd() {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd target = null;
            target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd) get_store()
                                                                                .find_element_user(OHD$6,
                    0);

            if (target == null) {
                target = (aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd) get_store()
                                                                                    .add_element_user(OHD$6);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "ohd" element
     */
    public void unsetOhd() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(OHD$6, 0);
        }
    }

    /**
     * Gets the "responseData" element
     */
    public java.lang.String getResponseData() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(RESPONSEDATA$8,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "responseData" element
     */
    public org.apache.xmlbeans.XmlString xgetResponseData() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(RESPONSEDATA$8,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "responseData" element
     */
    public boolean isNilResponseData() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(RESPONSEDATA$8,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "responseData" element
     */
    public boolean isSetResponseData() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(RESPONSEDATA$8) != 0;
        }
    }

    /**
     * Sets the "responseData" element
     */
    public void setResponseData(java.lang.String responseData) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(RESPONSEDATA$8,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(RESPONSEDATA$8);
            }

            target.setStringValue(responseData);
        }
    }

    /**
     * Sets (as xml) the "responseData" element
     */
    public void xsetResponseData(org.apache.xmlbeans.XmlString responseData) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(RESPONSEDATA$8,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(RESPONSEDATA$8);
            }

            target.set(responseData);
        }
    }

    /**
     * Nils the "responseData" element
     */
    public void setNilResponseData() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(RESPONSEDATA$8,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(RESPONSEDATA$8);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "responseData" element
     */
    public void unsetResponseData() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(RESPONSEDATA$8, 0);
        }
    }

    /**
     * Gets the "responseId" element
     */
    public java.lang.String getResponseId() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(RESPONSEID$10,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "responseId" element
     */
    public org.apache.xmlbeans.XmlString xgetResponseId() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(RESPONSEID$10,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "responseId" element
     */
    public boolean isNilResponseId() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(RESPONSEID$10,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "responseId" element
     */
    public boolean isSetResponseId() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(RESPONSEID$10) != 0;
        }
    }

    /**
     * Sets the "responseId" element
     */
    public void setResponseId(java.lang.String responseId) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(RESPONSEID$10,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(RESPONSEID$10);
            }

            target.setStringValue(responseId);
        }
    }

    /**
     * Sets (as xml) the "responseId" element
     */
    public void xsetResponseId(org.apache.xmlbeans.XmlString responseId) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(RESPONSEID$10,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(RESPONSEID$10);
            }

            target.set(responseId);
        }
    }

    /**
     * Nils the "responseId" element
     */
    public void setNilResponseId() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(RESPONSEID$10,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(RESPONSEID$10);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "responseId" element
     */
    public void unsetResponseId() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(RESPONSEID$10, 0);
        }
    }

    /**
     * Gets the "success" element
     */
    public boolean getSuccess() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(SUCCESS$12,
                    0);

            if (target == null) {
                return false;
            }

            return target.getBooleanValue();
        }
    }

    /**
     * Gets (as xml) the "success" element
     */
    public org.apache.xmlbeans.XmlBoolean xgetSuccess() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean) get_store()
                                                          .find_element_user(SUCCESS$12,
                    0);

            return target;
        }
    }

    /**
     * True if has "success" element
     */
    public boolean isSetSuccess() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(SUCCESS$12) != 0;
        }
    }

    /**
     * Sets the "success" element
     */
    public void setSuccess(boolean success) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(SUCCESS$12,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(SUCCESS$12);
            }

            target.setBooleanValue(success);
        }
    }

    /**
     * Sets (as xml) the "success" element
     */
    public void xsetSuccess(org.apache.xmlbeans.XmlBoolean success) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean) get_store()
                                                          .find_element_user(SUCCESS$12,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlBoolean) get_store()
                                                              .add_element_user(SUCCESS$12);
            }

            target.set(success);
        }
    }

    /**
     * Unsets the "success" element
     */
    public void unsetSuccess() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(SUCCESS$12, 0);
        }
    }
}
