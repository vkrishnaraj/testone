/*
 * XML Type:  BdoStatusUpdate
 * Namespace: http://pojo.core.ws.nettracer.bagnet.com/xsd
 * Java type: com.bagnet.nettracer.ws.core.pojo.xsd.BdoStatusUpdate
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.core.pojo.xsd.impl;

/**
 * An XML BdoStatusUpdate(@http://pojo.core.ws.nettracer.bagnet.com/xsd).
 *
 * This is a complex type.
 */
public class BdoStatusUpdateImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
    implements com.bagnet.nettracer.ws.core.pojo.xsd.BdoStatusUpdate {
    private static final javax.xml.namespace.QName BDO$0 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "bdo");
    private static final javax.xml.namespace.QName REMARKS$2 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "remarks");
    private static final javax.xml.namespace.QName STATUS$4 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "status");
    private static final javax.xml.namespace.QName STATUSDATETIME$6 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "statusDateTime");

    public BdoStatusUpdateImpl(org.apache.xmlbeans.SchemaType sType) {
        super(sType);
    }

    /**
     * Gets the "bdo" element
     */
    public int getBdo() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(BDO$0,
                    0);

            if (target == null) {
                return 0;
            }

            return target.getIntValue();
        }
    }

    /**
     * Gets (as xml) the "bdo" element
     */
    public org.apache.xmlbeans.XmlInt xgetBdo() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt) get_store()
                                                      .find_element_user(BDO$0,
                    0);

            return target;
        }
    }

    /**
     * True if has "bdo" element
     */
    public boolean isSetBdo() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(BDO$0) != 0;
        }
    }

    /**
     * Sets the "bdo" element
     */
    public void setBdo(int bdo) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(BDO$0,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(BDO$0);
            }

            target.setIntValue(bdo);
        }
    }

    /**
     * Sets (as xml) the "bdo" element
     */
    public void xsetBdo(org.apache.xmlbeans.XmlInt bdo) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt) get_store()
                                                      .find_element_user(BDO$0,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlInt) get_store()
                                                          .add_element_user(BDO$0);
            }

            target.set(bdo);
        }
    }

    /**
     * Unsets the "bdo" element
     */
    public void unsetBdo() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(BDO$0, 0);
        }
    }

    /**
     * Gets the "remarks" element
     */
    public java.lang.String getRemarks() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(REMARKS$2,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "remarks" element
     */
    public org.apache.xmlbeans.XmlString xgetRemarks() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(REMARKS$2,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "remarks" element
     */
    public boolean isNilRemarks() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(REMARKS$2,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "remarks" element
     */
    public boolean isSetRemarks() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(REMARKS$2) != 0;
        }
    }

    /**
     * Sets the "remarks" element
     */
    public void setRemarks(java.lang.String remarks) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(REMARKS$2,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(REMARKS$2);
            }

            target.setStringValue(remarks);
        }
    }

    /**
     * Sets (as xml) the "remarks" element
     */
    public void xsetRemarks(org.apache.xmlbeans.XmlString remarks) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(REMARKS$2,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(REMARKS$2);
            }

            target.set(remarks);
        }
    }

    /**
     * Nils the "remarks" element
     */
    public void setNilRemarks() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(REMARKS$2,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(REMARKS$2);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "remarks" element
     */
    public void unsetRemarks() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(REMARKS$2, 0);
        }
    }

    /**
     * Gets the "status" element
     */
    public java.lang.String getStatus() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(STATUS$4,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "status" element
     */
    public org.apache.xmlbeans.XmlString xgetStatus() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(STATUS$4,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "status" element
     */
    public boolean isNilStatus() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(STATUS$4,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "status" element
     */
    public boolean isSetStatus() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(STATUS$4) != 0;
        }
    }

    /**
     * Sets the "status" element
     */
    public void setStatus(java.lang.String status) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(STATUS$4,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(STATUS$4);
            }

            target.setStringValue(status);
        }
    }

    /**
     * Sets (as xml) the "status" element
     */
    public void xsetStatus(org.apache.xmlbeans.XmlString status) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(STATUS$4,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(STATUS$4);
            }

            target.set(status);
        }
    }

    /**
     * Nils the "status" element
     */
    public void setNilStatus() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(STATUS$4,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(STATUS$4);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "status" element
     */
    public void unsetStatus() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(STATUS$4, 0);
        }
    }

    /**
     * Gets the "statusDateTime" element
     */
    public java.util.Calendar getStatusDateTime() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(STATUSDATETIME$6,
                    0);

            if (target == null) {
                return null;
            }

            return target.getCalendarValue();
        }
    }

    /**
     * Gets (as xml) the "statusDateTime" element
     */
    public org.apache.xmlbeans.XmlDateTime xgetStatusDateTime() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime) get_store()
                                                           .find_element_user(STATUSDATETIME$6,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "statusDateTime" element
     */
    public boolean isNilStatusDateTime() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime) get_store()
                                                           .find_element_user(STATUSDATETIME$6,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "statusDateTime" element
     */
    public boolean isSetStatusDateTime() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(STATUSDATETIME$6) != 0;
        }
    }

    /**
     * Sets the "statusDateTime" element
     */
    public void setStatusDateTime(java.util.Calendar statusDateTime) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(STATUSDATETIME$6,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(STATUSDATETIME$6);
            }

            target.setCalendarValue(statusDateTime);
        }
    }

    /**
     * Sets (as xml) the "statusDateTime" element
     */
    public void xsetStatusDateTime(
        org.apache.xmlbeans.XmlDateTime statusDateTime) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime) get_store()
                                                           .find_element_user(STATUSDATETIME$6,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlDateTime) get_store()
                                                               .add_element_user(STATUSDATETIME$6);
            }

            target.set(statusDateTime);
        }
    }

    /**
     * Nils the "statusDateTime" element
     */
    public void setNilStatusDateTime() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime) get_store()
                                                           .find_element_user(STATUSDATETIME$6,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlDateTime) get_store()
                                                               .add_element_user(STATUSDATETIME$6);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "statusDateTime" element
     */
    public void unsetStatusDateTime() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(STATUSDATETIME$6, 0);
        }
    }
}
