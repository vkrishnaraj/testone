/*
 * XML Type:  WS_PVIncident2
 * Namespace: http://pojo.core.ws.nettracer.bagnet.com/xsd
 * Java type: com.bagnet.nettracer.ws.core.pojo.xsd.WSPVIncident2
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.core.pojo.xsd.impl;

/**
 * An XML WS_PVIncident2(@http://pojo.core.ws.nettracer.bagnet.com/xsd).
 *
 * This is a complex type.
 */
public class WSPVIncident2Impl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
    implements com.bagnet.nettracer.ws.core.pojo.xsd.WSPVIncident2 {
    private static final javax.xml.namespace.QName COMPANYCODEID$0 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "companycode_id");
    private static final javax.xml.namespace.QName DISPCREATETIME$2 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "dispcreatetime");
    private static final javax.xml.namespace.QName ERRORCODE$4 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "errorcode");
    private static final javax.xml.namespace.QName INCIDENTID$6 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "incident_ID");
    private static final javax.xml.namespace.QName INCIDENTSTATUS$8 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "incident_status");
    private static final javax.xml.namespace.QName ITEMS$10 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "items");
    private static final javax.xml.namespace.QName PASSENGERS$12 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "passengers");

    public WSPVIncident2Impl(org.apache.xmlbeans.SchemaType sType) {
        super(sType);
    }

    /**
     * Gets the "companycode_id" element
     */
    public java.lang.String getCompanycodeId() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(COMPANYCODEID$0,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "companycode_id" element
     */
    public org.apache.xmlbeans.XmlString xgetCompanycodeId() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(COMPANYCODEID$0,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "companycode_id" element
     */
    public boolean isNilCompanycodeId() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(COMPANYCODEID$0,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "companycode_id" element
     */
    public boolean isSetCompanycodeId() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(COMPANYCODEID$0) != 0;
        }
    }

    /**
     * Sets the "companycode_id" element
     */
    public void setCompanycodeId(java.lang.String companycodeId) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(COMPANYCODEID$0,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(COMPANYCODEID$0);
            }

            target.setStringValue(companycodeId);
        }
    }

    /**
     * Sets (as xml) the "companycode_id" element
     */
    public void xsetCompanycodeId(org.apache.xmlbeans.XmlString companycodeId) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(COMPANYCODEID$0,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(COMPANYCODEID$0);
            }

            target.set(companycodeId);
        }
    }

    /**
     * Nils the "companycode_id" element
     */
    public void setNilCompanycodeId() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(COMPANYCODEID$0,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(COMPANYCODEID$0);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "companycode_id" element
     */
    public void unsetCompanycodeId() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(COMPANYCODEID$0, 0);
        }
    }

    /**
     * Gets the "dispcreatetime" element
     */
    public java.lang.String getDispcreatetime() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(DISPCREATETIME$2,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "dispcreatetime" element
     */
    public org.apache.xmlbeans.XmlString xgetDispcreatetime() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(DISPCREATETIME$2,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "dispcreatetime" element
     */
    public boolean isNilDispcreatetime() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(DISPCREATETIME$2,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "dispcreatetime" element
     */
    public boolean isSetDispcreatetime() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(DISPCREATETIME$2) != 0;
        }
    }

    /**
     * Sets the "dispcreatetime" element
     */
    public void setDispcreatetime(java.lang.String dispcreatetime) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(DISPCREATETIME$2,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(DISPCREATETIME$2);
            }

            target.setStringValue(dispcreatetime);
        }
    }

    /**
     * Sets (as xml) the "dispcreatetime" element
     */
    public void xsetDispcreatetime(org.apache.xmlbeans.XmlString dispcreatetime) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(DISPCREATETIME$2,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(DISPCREATETIME$2);
            }

            target.set(dispcreatetime);
        }
    }

    /**
     * Nils the "dispcreatetime" element
     */
    public void setNilDispcreatetime() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(DISPCREATETIME$2,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(DISPCREATETIME$2);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "dispcreatetime" element
     */
    public void unsetDispcreatetime() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(DISPCREATETIME$2, 0);
        }
    }

    /**
     * Gets the "errorcode" element
     */
    public java.lang.String getErrorcode() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(ERRORCODE$4,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "errorcode" element
     */
    public org.apache.xmlbeans.XmlString xgetErrorcode() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(ERRORCODE$4,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "errorcode" element
     */
    public boolean isNilErrorcode() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(ERRORCODE$4,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "errorcode" element
     */
    public boolean isSetErrorcode() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(ERRORCODE$4) != 0;
        }
    }

    /**
     * Sets the "errorcode" element
     */
    public void setErrorcode(java.lang.String errorcode) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(ERRORCODE$4,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(ERRORCODE$4);
            }

            target.setStringValue(errorcode);
        }
    }

    /**
     * Sets (as xml) the "errorcode" element
     */
    public void xsetErrorcode(org.apache.xmlbeans.XmlString errorcode) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(ERRORCODE$4,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(ERRORCODE$4);
            }

            target.set(errorcode);
        }
    }

    /**
     * Nils the "errorcode" element
     */
    public void setNilErrorcode() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(ERRORCODE$4,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(ERRORCODE$4);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "errorcode" element
     */
    public void unsetErrorcode() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(ERRORCODE$4, 0);
        }
    }

    /**
     * Gets the "incident_ID" element
     */
    public java.lang.String getIncidentID() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(INCIDENTID$6,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "incident_ID" element
     */
    public org.apache.xmlbeans.XmlString xgetIncidentID() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(INCIDENTID$6,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "incident_ID" element
     */
    public boolean isNilIncidentID() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(INCIDENTID$6,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "incident_ID" element
     */
    public boolean isSetIncidentID() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(INCIDENTID$6) != 0;
        }
    }

    /**
     * Sets the "incident_ID" element
     */
    public void setIncidentID(java.lang.String incidentID) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(INCIDENTID$6,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(INCIDENTID$6);
            }

            target.setStringValue(incidentID);
        }
    }

    /**
     * Sets (as xml) the "incident_ID" element
     */
    public void xsetIncidentID(org.apache.xmlbeans.XmlString incidentID) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(INCIDENTID$6,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(INCIDENTID$6);
            }

            target.set(incidentID);
        }
    }

    /**
     * Nils the "incident_ID" element
     */
    public void setNilIncidentID() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(INCIDENTID$6,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(INCIDENTID$6);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "incident_ID" element
     */
    public void unsetIncidentID() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(INCIDENTID$6, 0);
        }
    }

    /**
     * Gets the "incident_status" element
     */
    public java.lang.String getIncidentStatus() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(INCIDENTSTATUS$8,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "incident_status" element
     */
    public org.apache.xmlbeans.XmlString xgetIncidentStatus() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(INCIDENTSTATUS$8,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "incident_status" element
     */
    public boolean isNilIncidentStatus() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(INCIDENTSTATUS$8,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "incident_status" element
     */
    public boolean isSetIncidentStatus() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(INCIDENTSTATUS$8) != 0;
        }
    }

    /**
     * Sets the "incident_status" element
     */
    public void setIncidentStatus(java.lang.String incidentStatus) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(INCIDENTSTATUS$8,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(INCIDENTSTATUS$8);
            }

            target.setStringValue(incidentStatus);
        }
    }

    /**
     * Sets (as xml) the "incident_status" element
     */
    public void xsetIncidentStatus(org.apache.xmlbeans.XmlString incidentStatus) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(INCIDENTSTATUS$8,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(INCIDENTSTATUS$8);
            }

            target.set(incidentStatus);
        }
    }

    /**
     * Nils the "incident_status" element
     */
    public void setNilIncidentStatus() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(INCIDENTSTATUS$8,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(INCIDENTSTATUS$8);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "incident_status" element
     */
    public void unsetIncidentStatus() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(INCIDENTSTATUS$8, 0);
        }
    }

    /**
     * Gets array of all "items" elements
     */
    public com.bagnet.nettracer.ws.core.pojo.xsd.WSPVItem[] getItemsArray() {
        synchronized (monitor()) {
            check_orphaned();

            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(ITEMS$10, targetList);

            com.bagnet.nettracer.ws.core.pojo.xsd.WSPVItem[] result = new com.bagnet.nettracer.ws.core.pojo.xsd.WSPVItem[targetList.size()];
            targetList.toArray(result);

            return result;
        }
    }

    /**
     * Gets ith "items" element
     */
    public com.bagnet.nettracer.ws.core.pojo.xsd.WSPVItem getItemsArray(int i) {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.pojo.xsd.WSPVItem target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVItem) get_store()
                                                                          .find_element_user(ITEMS$10,
                    i);

            if (target == null) {
                throw new IndexOutOfBoundsException();
            }

            return target;
        }
    }

    /**
     * Tests for nil ith "items" element
     */
    public boolean isNilItemsArray(int i) {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.pojo.xsd.WSPVItem target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVItem) get_store()
                                                                          .find_element_user(ITEMS$10,
                    i);

            if (target == null) {
                throw new IndexOutOfBoundsException();
            }

            return target.isNil();
        }
    }

    /**
     * Returns number of "items" element
     */
    public int sizeOfItemsArray() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(ITEMS$10);
        }
    }

    /**
     * Sets array of all "items" element
     */
    public void setItemsArray(
        com.bagnet.nettracer.ws.core.pojo.xsd.WSPVItem[] itemsArray) {
        synchronized (monitor()) {
            check_orphaned();
            arraySetterHelper(itemsArray, ITEMS$10);
        }
    }

    /**
     * Sets ith "items" element
     */
    public void setItemsArray(int i,
        com.bagnet.nettracer.ws.core.pojo.xsd.WSPVItem items) {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.pojo.xsd.WSPVItem target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVItem) get_store()
                                                                          .find_element_user(ITEMS$10,
                    i);

            if (target == null) {
                throw new IndexOutOfBoundsException();
            }

            target.set(items);
        }
    }

    /**
     * Nils the ith "items" element
     */
    public void setNilItemsArray(int i) {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.pojo.xsd.WSPVItem target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVItem) get_store()
                                                                          .find_element_user(ITEMS$10,
                    i);

            if (target == null) {
                throw new IndexOutOfBoundsException();
            }

            target.setNil();
        }
    }

    /**
     * Inserts and returns a new empty value (as xml) as the ith "items" element
     */
    public com.bagnet.nettracer.ws.core.pojo.xsd.WSPVItem insertNewItems(int i) {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.pojo.xsd.WSPVItem target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVItem) get_store()
                                                                          .insert_element_user(ITEMS$10,
                    i);

            return target;
        }
    }

    /**
     * Appends and returns a new empty value (as xml) as the last "items" element
     */
    public com.bagnet.nettracer.ws.core.pojo.xsd.WSPVItem addNewItems() {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.pojo.xsd.WSPVItem target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVItem) get_store()
                                                                          .add_element_user(ITEMS$10);

            return target;
        }
    }

    /**
     * Removes the ith "items" element
     */
    public void removeItems(int i) {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(ITEMS$10, i);
        }
    }

    /**
     * Gets array of all "passengers" elements
     */
    public com.bagnet.nettracer.ws.core.pojo.xsd.WSPVPassenger[] getPassengersArray() {
        synchronized (monitor()) {
            check_orphaned();

            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(PASSENGERS$12, targetList);

            com.bagnet.nettracer.ws.core.pojo.xsd.WSPVPassenger[] result = new com.bagnet.nettracer.ws.core.pojo.xsd.WSPVPassenger[targetList.size()];
            targetList.toArray(result);

            return result;
        }
    }

    /**
     * Gets ith "passengers" element
     */
    public com.bagnet.nettracer.ws.core.pojo.xsd.WSPVPassenger getPassengersArray(
        int i) {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.pojo.xsd.WSPVPassenger target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVPassenger) get_store()
                                                                               .find_element_user(PASSENGERS$12,
                    i);

            if (target == null) {
                throw new IndexOutOfBoundsException();
            }

            return target;
        }
    }

    /**
     * Tests for nil ith "passengers" element
     */
    public boolean isNilPassengersArray(int i) {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.pojo.xsd.WSPVPassenger target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVPassenger) get_store()
                                                                               .find_element_user(PASSENGERS$12,
                    i);

            if (target == null) {
                throw new IndexOutOfBoundsException();
            }

            return target.isNil();
        }
    }

    /**
     * Returns number of "passengers" element
     */
    public int sizeOfPassengersArray() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(PASSENGERS$12);
        }
    }

    /**
     * Sets array of all "passengers" element
     */
    public void setPassengersArray(
        com.bagnet.nettracer.ws.core.pojo.xsd.WSPVPassenger[] passengersArray) {
        synchronized (monitor()) {
            check_orphaned();
            arraySetterHelper(passengersArray, PASSENGERS$12);
        }
    }

    /**
     * Sets ith "passengers" element
     */
    public void setPassengersArray(int i,
        com.bagnet.nettracer.ws.core.pojo.xsd.WSPVPassenger passengers) {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.pojo.xsd.WSPVPassenger target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVPassenger) get_store()
                                                                               .find_element_user(PASSENGERS$12,
                    i);

            if (target == null) {
                throw new IndexOutOfBoundsException();
            }

            target.set(passengers);
        }
    }

    /**
     * Nils the ith "passengers" element
     */
    public void setNilPassengersArray(int i) {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.pojo.xsd.WSPVPassenger target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVPassenger) get_store()
                                                                               .find_element_user(PASSENGERS$12,
                    i);

            if (target == null) {
                throw new IndexOutOfBoundsException();
            }

            target.setNil();
        }
    }

    /**
     * Inserts and returns a new empty value (as xml) as the ith "passengers" element
     */
    public com.bagnet.nettracer.ws.core.pojo.xsd.WSPVPassenger insertNewPassengers(
        int i) {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.pojo.xsd.WSPVPassenger target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVPassenger) get_store()
                                                                               .insert_element_user(PASSENGERS$12,
                    i);

            return target;
        }
    }

    /**
     * Appends and returns a new empty value (as xml) as the last "passengers" element
     */
    public com.bagnet.nettracer.ws.core.pojo.xsd.WSPVPassenger addNewPassengers() {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.pojo.xsd.WSPVPassenger target = null;
            target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSPVPassenger) get_store()
                                                                               .add_element_user(PASSENGERS$12);

            return target;
        }
    }

    /**
     * Removes the ith "passengers" element
     */
    public void removePassengers(int i) {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(PASSENGERS$12, i);
        }
    }
}
