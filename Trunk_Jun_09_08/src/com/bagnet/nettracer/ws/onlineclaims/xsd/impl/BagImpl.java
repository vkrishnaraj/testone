/*
 * XML Type:  Bag
 * Namespace: http://onlineclaims.ws.nettracer.bagnet.com/xsd
 * Java type: com.bagnet.nettracer.ws.onlineclaims.xsd.Bag
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.onlineclaims.xsd.impl;

/**
 * An XML Bag(@http://onlineclaims.ws.nettracer.bagnet.com/xsd).
 *
 * This is a complex type.
 */
public class BagImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
    implements com.bagnet.nettracer.ws.onlineclaims.xsd.Bag {
    private static final javax.xml.namespace.QName BAGARRIVE$0 = new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd",
            "bagArrive");
    private static final javax.xml.namespace.QName BAGCOLOR$2 = new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd",
            "bagColor");
    private static final javax.xml.namespace.QName BAGTYPE$4 = new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd",
            "bagType");
    private static final javax.xml.namespace.QName BRAND$6 = new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd",
            "brand");
    private static final javax.xml.namespace.QName CONTENTS$8 = new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd",
            "contents");
    private static final javax.xml.namespace.QName EXTERNALMARKINGS$10 = new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd",
            "externalMarkings");
    private static final javax.xml.namespace.QName FEET$12 = new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd",
            "feet");
    private static final javax.xml.namespace.QName HARDSIDED$14 = new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd",
            "hardSided");
    private static final javax.xml.namespace.QName ID$16 = new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd",
            "id");
    private static final javax.xml.namespace.QName LOCKS$18 = new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd",
            "locks");
    private static final javax.xml.namespace.QName NAMEONBAG$20 = new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd",
            "nameOnBag");
    private static final javax.xml.namespace.QName NAMETAG$22 = new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd",
            "nameTag");
    private static final javax.xml.namespace.QName POCKETS$24 = new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd",
            "pockets");
    private static final javax.xml.namespace.QName PULLSTRAP$26 = new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd",
            "pullStrap");
    private static final javax.xml.namespace.QName PURCHASEDATE$28 = new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd",
            "purchaseDate");
    private static final javax.xml.namespace.QName RETRACTIBLEHANDLE$30 = new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd",
            "retractibleHandle");
    private static final javax.xml.namespace.QName RIBBONSORMARKINGS$32 = new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd",
            "ribbonsOrMarkings");
    private static final javax.xml.namespace.QName SOFTSIDED$34 = new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd",
            "softSided");
    private static final javax.xml.namespace.QName TAG$36 = new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd",
            "tag");
    private static final javax.xml.namespace.QName TRIM$38 = new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd",
            "trim");
    private static final javax.xml.namespace.QName WHEELS$40 = new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd",
            "wheels");
    private static final javax.xml.namespace.QName ZIPPERS$42 = new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd",
            "zippers");

    public BagImpl(org.apache.xmlbeans.SchemaType sType) {
        super(sType);
    }

    /**
     * Gets the "bagArrive" element
     */
    public boolean getBagArrive() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(BAGARRIVE$0,
                    0);

            if (target == null) {
                return false;
            }

            return target.getBooleanValue();
        }
    }

    /**
     * Gets (as xml) the "bagArrive" element
     */
    public org.apache.xmlbeans.XmlBoolean xgetBagArrive() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean) get_store()
                                                          .find_element_user(BAGARRIVE$0,
                    0);

            return target;
        }
    }

    /**
     * True if has "bagArrive" element
     */
    public boolean isSetBagArrive() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(BAGARRIVE$0) != 0;
        }
    }

    /**
     * Sets the "bagArrive" element
     */
    public void setBagArrive(boolean bagArrive) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(BAGARRIVE$0,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(BAGARRIVE$0);
            }

            target.setBooleanValue(bagArrive);
        }
    }

    /**
     * Sets (as xml) the "bagArrive" element
     */
    public void xsetBagArrive(org.apache.xmlbeans.XmlBoolean bagArrive) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean) get_store()
                                                          .find_element_user(BAGARRIVE$0,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlBoolean) get_store()
                                                              .add_element_user(BAGARRIVE$0);
            }

            target.set(bagArrive);
        }
    }

    /**
     * Unsets the "bagArrive" element
     */
    public void unsetBagArrive() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(BAGARRIVE$0, 0);
        }
    }

    /**
     * Gets the "bagColor" element
     */
    public java.lang.String getBagColor() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(BAGCOLOR$2,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "bagColor" element
     */
    public org.apache.xmlbeans.XmlString xgetBagColor() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(BAGCOLOR$2,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "bagColor" element
     */
    public boolean isNilBagColor() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(BAGCOLOR$2,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "bagColor" element
     */
    public boolean isSetBagColor() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(BAGCOLOR$2) != 0;
        }
    }

    /**
     * Sets the "bagColor" element
     */
    public void setBagColor(java.lang.String bagColor) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(BAGCOLOR$2,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(BAGCOLOR$2);
            }

            target.setStringValue(bagColor);
        }
    }

    /**
     * Sets (as xml) the "bagColor" element
     */
    public void xsetBagColor(org.apache.xmlbeans.XmlString bagColor) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(BAGCOLOR$2,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(BAGCOLOR$2);
            }

            target.set(bagColor);
        }
    }

    /**
     * Nils the "bagColor" element
     */
    public void setNilBagColor() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(BAGCOLOR$2,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(BAGCOLOR$2);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "bagColor" element
     */
    public void unsetBagColor() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(BAGCOLOR$2, 0);
        }
    }

    /**
     * Gets the "bagType" element
     */
    public java.lang.String getBagType() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(BAGTYPE$4,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "bagType" element
     */
    public org.apache.xmlbeans.XmlString xgetBagType() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(BAGTYPE$4,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "bagType" element
     */
    public boolean isNilBagType() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(BAGTYPE$4,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "bagType" element
     */
    public boolean isSetBagType() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(BAGTYPE$4) != 0;
        }
    }

    /**
     * Sets the "bagType" element
     */
    public void setBagType(java.lang.String bagType) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(BAGTYPE$4,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(BAGTYPE$4);
            }

            target.setStringValue(bagType);
        }
    }

    /**
     * Sets (as xml) the "bagType" element
     */
    public void xsetBagType(org.apache.xmlbeans.XmlString bagType) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(BAGTYPE$4,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(BAGTYPE$4);
            }

            target.set(bagType);
        }
    }

    /**
     * Nils the "bagType" element
     */
    public void setNilBagType() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(BAGTYPE$4,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(BAGTYPE$4);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "bagType" element
     */
    public void unsetBagType() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(BAGTYPE$4, 0);
        }
    }

    /**
     * Gets the "brand" element
     */
    public java.lang.String getBrand() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(BRAND$6,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "brand" element
     */
    public org.apache.xmlbeans.XmlString xgetBrand() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(BRAND$6,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "brand" element
     */
    public boolean isNilBrand() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(BRAND$6,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "brand" element
     */
    public boolean isSetBrand() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(BRAND$6) != 0;
        }
    }

    /**
     * Sets the "brand" element
     */
    public void setBrand(java.lang.String brand) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(BRAND$6,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(BRAND$6);
            }

            target.setStringValue(brand);
        }
    }

    /**
     * Sets (as xml) the "brand" element
     */
    public void xsetBrand(org.apache.xmlbeans.XmlString brand) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(BRAND$6,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(BRAND$6);
            }

            target.set(brand);
        }
    }

    /**
     * Nils the "brand" element
     */
    public void setNilBrand() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(BRAND$6,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(BRAND$6);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "brand" element
     */
    public void unsetBrand() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(BRAND$6, 0);
        }
    }

    /**
     * Gets array of all "contents" elements
     */
    public com.bagnet.nettracer.ws.onlineclaims.xsd.Contents[] getContentsArray() {
        synchronized (monitor()) {
            check_orphaned();

            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(CONTENTS$8, targetList);

            com.bagnet.nettracer.ws.onlineclaims.xsd.Contents[] result = new com.bagnet.nettracer.ws.onlineclaims.xsd.Contents[targetList.size()];
            targetList.toArray(result);

            return result;
        }
    }

    /**
     * Gets ith "contents" element
     */
    public com.bagnet.nettracer.ws.onlineclaims.xsd.Contents getContentsArray(
        int i) {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.onlineclaims.xsd.Contents target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.xsd.Contents) get_store()
                                                                             .find_element_user(CONTENTS$8,
                    i);

            if (target == null) {
                throw new IndexOutOfBoundsException();
            }

            return target;
        }
    }

    /**
     * Tests for nil ith "contents" element
     */
    public boolean isNilContentsArray(int i) {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.onlineclaims.xsd.Contents target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.xsd.Contents) get_store()
                                                                             .find_element_user(CONTENTS$8,
                    i);

            if (target == null) {
                throw new IndexOutOfBoundsException();
            }

            return target.isNil();
        }
    }

    /**
     * Returns number of "contents" element
     */
    public int sizeOfContentsArray() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(CONTENTS$8);
        }
    }

    /**
     * Sets array of all "contents" element
     */
    public void setContentsArray(
        com.bagnet.nettracer.ws.onlineclaims.xsd.Contents[] contentsArray) {
        synchronized (monitor()) {
            check_orphaned();
            arraySetterHelper(contentsArray, CONTENTS$8);
        }
    }

    /**
     * Sets ith "contents" element
     */
    public void setContentsArray(int i,
        com.bagnet.nettracer.ws.onlineclaims.xsd.Contents contents) {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.onlineclaims.xsd.Contents target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.xsd.Contents) get_store()
                                                                             .find_element_user(CONTENTS$8,
                    i);

            if (target == null) {
                throw new IndexOutOfBoundsException();
            }

            target.set(contents);
        }
    }

    /**
     * Nils the ith "contents" element
     */
    public void setNilContentsArray(int i) {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.onlineclaims.xsd.Contents target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.xsd.Contents) get_store()
                                                                             .find_element_user(CONTENTS$8,
                    i);

            if (target == null) {
                throw new IndexOutOfBoundsException();
            }

            target.setNil();
        }
    }

    /**
     * Inserts and returns a new empty value (as xml) as the ith "contents" element
     */
    public com.bagnet.nettracer.ws.onlineclaims.xsd.Contents insertNewContents(
        int i) {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.onlineclaims.xsd.Contents target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.xsd.Contents) get_store()
                                                                             .insert_element_user(CONTENTS$8,
                    i);

            return target;
        }
    }

    /**
     * Appends and returns a new empty value (as xml) as the last "contents" element
     */
    public com.bagnet.nettracer.ws.onlineclaims.xsd.Contents addNewContents() {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.onlineclaims.xsd.Contents target = null;
            target = (com.bagnet.nettracer.ws.onlineclaims.xsd.Contents) get_store()
                                                                             .add_element_user(CONTENTS$8);

            return target;
        }
    }

    /**
     * Removes the ith "contents" element
     */
    public void removeContents(int i) {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(CONTENTS$8, i);
        }
    }

    /**
     * Gets the "externalMarkings" element
     */
    public java.lang.String getExternalMarkings() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(EXTERNALMARKINGS$10,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "externalMarkings" element
     */
    public org.apache.xmlbeans.XmlString xgetExternalMarkings() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(EXTERNALMARKINGS$10,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "externalMarkings" element
     */
    public boolean isNilExternalMarkings() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(EXTERNALMARKINGS$10,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "externalMarkings" element
     */
    public boolean isSetExternalMarkings() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(EXTERNALMARKINGS$10) != 0;
        }
    }

    /**
     * Sets the "externalMarkings" element
     */
    public void setExternalMarkings(java.lang.String externalMarkings) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(EXTERNALMARKINGS$10,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(EXTERNALMARKINGS$10);
            }

            target.setStringValue(externalMarkings);
        }
    }

    /**
     * Sets (as xml) the "externalMarkings" element
     */
    public void xsetExternalMarkings(
        org.apache.xmlbeans.XmlString externalMarkings) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(EXTERNALMARKINGS$10,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(EXTERNALMARKINGS$10);
            }

            target.set(externalMarkings);
        }
    }

    /**
     * Nils the "externalMarkings" element
     */
    public void setNilExternalMarkings() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(EXTERNALMARKINGS$10,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(EXTERNALMARKINGS$10);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "externalMarkings" element
     */
    public void unsetExternalMarkings() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(EXTERNALMARKINGS$10, 0);
        }
    }

    /**
     * Gets the "feet" element
     */
    public boolean getFeet() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(FEET$12,
                    0);

            if (target == null) {
                return false;
            }

            return target.getBooleanValue();
        }
    }

    /**
     * Gets (as xml) the "feet" element
     */
    public org.apache.xmlbeans.XmlBoolean xgetFeet() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean) get_store()
                                                          .find_element_user(FEET$12,
                    0);

            return target;
        }
    }

    /**
     * True if has "feet" element
     */
    public boolean isSetFeet() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(FEET$12) != 0;
        }
    }

    /**
     * Sets the "feet" element
     */
    public void setFeet(boolean feet) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(FEET$12,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(FEET$12);
            }

            target.setBooleanValue(feet);
        }
    }

    /**
     * Sets (as xml) the "feet" element
     */
    public void xsetFeet(org.apache.xmlbeans.XmlBoolean feet) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean) get_store()
                                                          .find_element_user(FEET$12,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlBoolean) get_store()
                                                              .add_element_user(FEET$12);
            }

            target.set(feet);
        }
    }

    /**
     * Unsets the "feet" element
     */
    public void unsetFeet() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(FEET$12, 0);
        }
    }

    /**
     * Gets the "hardSided" element
     */
    public boolean getHardSided() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(HARDSIDED$14,
                    0);

            if (target == null) {
                return false;
            }

            return target.getBooleanValue();
        }
    }

    /**
     * Gets (as xml) the "hardSided" element
     */
    public org.apache.xmlbeans.XmlBoolean xgetHardSided() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean) get_store()
                                                          .find_element_user(HARDSIDED$14,
                    0);

            return target;
        }
    }

    /**
     * True if has "hardSided" element
     */
    public boolean isSetHardSided() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(HARDSIDED$14) != 0;
        }
    }

    /**
     * Sets the "hardSided" element
     */
    public void setHardSided(boolean hardSided) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(HARDSIDED$14,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(HARDSIDED$14);
            }

            target.setBooleanValue(hardSided);
        }
    }

    /**
     * Sets (as xml) the "hardSided" element
     */
    public void xsetHardSided(org.apache.xmlbeans.XmlBoolean hardSided) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean) get_store()
                                                          .find_element_user(HARDSIDED$14,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlBoolean) get_store()
                                                              .add_element_user(HARDSIDED$14);
            }

            target.set(hardSided);
        }
    }

    /**
     * Unsets the "hardSided" element
     */
    public void unsetHardSided() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(HARDSIDED$14, 0);
        }
    }

    /**
     * Gets the "id" element
     */
    public long getId() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(ID$16,
                    0);

            if (target == null) {
                return 0L;
            }

            return target.getLongValue();
        }
    }

    /**
     * Gets (as xml) the "id" element
     */
    public org.apache.xmlbeans.XmlLong xgetId() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlLong target = null;
            target = (org.apache.xmlbeans.XmlLong) get_store()
                                                       .find_element_user(ID$16,
                    0);

            return target;
        }
    }

    /**
     * True if has "id" element
     */
    public boolean isSetId() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(ID$16) != 0;
        }
    }

    /**
     * Sets the "id" element
     */
    public void setId(long id) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(ID$16,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(ID$16);
            }

            target.setLongValue(id);
        }
    }

    /**
     * Sets (as xml) the "id" element
     */
    public void xsetId(org.apache.xmlbeans.XmlLong id) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlLong target = null;
            target = (org.apache.xmlbeans.XmlLong) get_store()
                                                       .find_element_user(ID$16,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlLong) get_store()
                                                           .add_element_user(ID$16);
            }

            target.set(id);
        }
    }

    /**
     * Unsets the "id" element
     */
    public void unsetId() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(ID$16, 0);
        }
    }

    /**
     * Gets the "locks" element
     */
    public boolean getLocks() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(LOCKS$18,
                    0);

            if (target == null) {
                return false;
            }

            return target.getBooleanValue();
        }
    }

    /**
     * Gets (as xml) the "locks" element
     */
    public org.apache.xmlbeans.XmlBoolean xgetLocks() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean) get_store()
                                                          .find_element_user(LOCKS$18,
                    0);

            return target;
        }
    }

    /**
     * True if has "locks" element
     */
    public boolean isSetLocks() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(LOCKS$18) != 0;
        }
    }

    /**
     * Sets the "locks" element
     */
    public void setLocks(boolean locks) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(LOCKS$18,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(LOCKS$18);
            }

            target.setBooleanValue(locks);
        }
    }

    /**
     * Sets (as xml) the "locks" element
     */
    public void xsetLocks(org.apache.xmlbeans.XmlBoolean locks) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean) get_store()
                                                          .find_element_user(LOCKS$18,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlBoolean) get_store()
                                                              .add_element_user(LOCKS$18);
            }

            target.set(locks);
        }
    }

    /**
     * Unsets the "locks" element
     */
    public void unsetLocks() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(LOCKS$18, 0);
        }
    }

    /**
     * Gets the "nameOnBag" element
     */
    public java.lang.String getNameOnBag() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(NAMEONBAG$20,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "nameOnBag" element
     */
    public org.apache.xmlbeans.XmlString xgetNameOnBag() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(NAMEONBAG$20,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "nameOnBag" element
     */
    public boolean isNilNameOnBag() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(NAMEONBAG$20,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "nameOnBag" element
     */
    public boolean isSetNameOnBag() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(NAMEONBAG$20) != 0;
        }
    }

    /**
     * Sets the "nameOnBag" element
     */
    public void setNameOnBag(java.lang.String nameOnBag) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(NAMEONBAG$20,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(NAMEONBAG$20);
            }

            target.setStringValue(nameOnBag);
        }
    }

    /**
     * Sets (as xml) the "nameOnBag" element
     */
    public void xsetNameOnBag(org.apache.xmlbeans.XmlString nameOnBag) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(NAMEONBAG$20,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(NAMEONBAG$20);
            }

            target.set(nameOnBag);
        }
    }

    /**
     * Nils the "nameOnBag" element
     */
    public void setNilNameOnBag() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(NAMEONBAG$20,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(NAMEONBAG$20);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "nameOnBag" element
     */
    public void unsetNameOnBag() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(NAMEONBAG$20, 0);
        }
    }

    /**
     * Gets the "nameTag" element
     */
    public boolean getNameTag() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(NAMETAG$22,
                    0);

            if (target == null) {
                return false;
            }

            return target.getBooleanValue();
        }
    }

    /**
     * Gets (as xml) the "nameTag" element
     */
    public org.apache.xmlbeans.XmlBoolean xgetNameTag() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean) get_store()
                                                          .find_element_user(NAMETAG$22,
                    0);

            return target;
        }
    }

    /**
     * True if has "nameTag" element
     */
    public boolean isSetNameTag() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(NAMETAG$22) != 0;
        }
    }

    /**
     * Sets the "nameTag" element
     */
    public void setNameTag(boolean nameTag) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(NAMETAG$22,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(NAMETAG$22);
            }

            target.setBooleanValue(nameTag);
        }
    }

    /**
     * Sets (as xml) the "nameTag" element
     */
    public void xsetNameTag(org.apache.xmlbeans.XmlBoolean nameTag) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean) get_store()
                                                          .find_element_user(NAMETAG$22,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlBoolean) get_store()
                                                              .add_element_user(NAMETAG$22);
            }

            target.set(nameTag);
        }
    }

    /**
     * Unsets the "nameTag" element
     */
    public void unsetNameTag() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(NAMETAG$22, 0);
        }
    }

    /**
     * Gets the "pockets" element
     */
    public boolean getPockets() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(POCKETS$24,
                    0);

            if (target == null) {
                return false;
            }

            return target.getBooleanValue();
        }
    }

    /**
     * Gets (as xml) the "pockets" element
     */
    public org.apache.xmlbeans.XmlBoolean xgetPockets() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean) get_store()
                                                          .find_element_user(POCKETS$24,
                    0);

            return target;
        }
    }

    /**
     * True if has "pockets" element
     */
    public boolean isSetPockets() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(POCKETS$24) != 0;
        }
    }

    /**
     * Sets the "pockets" element
     */
    public void setPockets(boolean pockets) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(POCKETS$24,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(POCKETS$24);
            }

            target.setBooleanValue(pockets);
        }
    }

    /**
     * Sets (as xml) the "pockets" element
     */
    public void xsetPockets(org.apache.xmlbeans.XmlBoolean pockets) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean) get_store()
                                                          .find_element_user(POCKETS$24,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlBoolean) get_store()
                                                              .add_element_user(POCKETS$24);
            }

            target.set(pockets);
        }
    }

    /**
     * Unsets the "pockets" element
     */
    public void unsetPockets() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(POCKETS$24, 0);
        }
    }

    /**
     * Gets the "pullStrap" element
     */
    public boolean getPullStrap() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(PULLSTRAP$26,
                    0);

            if (target == null) {
                return false;
            }

            return target.getBooleanValue();
        }
    }

    /**
     * Gets (as xml) the "pullStrap" element
     */
    public org.apache.xmlbeans.XmlBoolean xgetPullStrap() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean) get_store()
                                                          .find_element_user(PULLSTRAP$26,
                    0);

            return target;
        }
    }

    /**
     * True if has "pullStrap" element
     */
    public boolean isSetPullStrap() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(PULLSTRAP$26) != 0;
        }
    }

    /**
     * Sets the "pullStrap" element
     */
    public void setPullStrap(boolean pullStrap) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(PULLSTRAP$26,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(PULLSTRAP$26);
            }

            target.setBooleanValue(pullStrap);
        }
    }

    /**
     * Sets (as xml) the "pullStrap" element
     */
    public void xsetPullStrap(org.apache.xmlbeans.XmlBoolean pullStrap) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean) get_store()
                                                          .find_element_user(PULLSTRAP$26,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlBoolean) get_store()
                                                              .add_element_user(PULLSTRAP$26);
            }

            target.set(pullStrap);
        }
    }

    /**
     * Unsets the "pullStrap" element
     */
    public void unsetPullStrap() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(PULLSTRAP$26, 0);
        }
    }

    /**
     * Gets the "purchaseDate" element
     */
    public java.util.Calendar getPurchaseDate() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(PURCHASEDATE$28,
                    0);

            if (target == null) {
                return null;
            }

            return target.getCalendarValue();
        }
    }

    /**
     * Gets (as xml) the "purchaseDate" element
     */
    public org.apache.xmlbeans.XmlDateTime xgetPurchaseDate() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime) get_store()
                                                           .find_element_user(PURCHASEDATE$28,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "purchaseDate" element
     */
    public boolean isNilPurchaseDate() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime) get_store()
                                                           .find_element_user(PURCHASEDATE$28,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "purchaseDate" element
     */
    public boolean isSetPurchaseDate() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(PURCHASEDATE$28) != 0;
        }
    }

    /**
     * Sets the "purchaseDate" element
     */
    public void setPurchaseDate(java.util.Calendar purchaseDate) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(PURCHASEDATE$28,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(PURCHASEDATE$28);
            }

            target.setCalendarValue(purchaseDate);
        }
    }

    /**
     * Sets (as xml) the "purchaseDate" element
     */
    public void xsetPurchaseDate(org.apache.xmlbeans.XmlDateTime purchaseDate) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime) get_store()
                                                           .find_element_user(PURCHASEDATE$28,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlDateTime) get_store()
                                                               .add_element_user(PURCHASEDATE$28);
            }

            target.set(purchaseDate);
        }
    }

    /**
     * Nils the "purchaseDate" element
     */
    public void setNilPurchaseDate() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlDateTime target = null;
            target = (org.apache.xmlbeans.XmlDateTime) get_store()
                                                           .find_element_user(PURCHASEDATE$28,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlDateTime) get_store()
                                                               .add_element_user(PURCHASEDATE$28);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "purchaseDate" element
     */
    public void unsetPurchaseDate() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(PURCHASEDATE$28, 0);
        }
    }

    /**
     * Gets the "retractibleHandle" element
     */
    public boolean getRetractibleHandle() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(RETRACTIBLEHANDLE$30,
                    0);

            if (target == null) {
                return false;
            }

            return target.getBooleanValue();
        }
    }

    /**
     * Gets (as xml) the "retractibleHandle" element
     */
    public org.apache.xmlbeans.XmlBoolean xgetRetractibleHandle() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean) get_store()
                                                          .find_element_user(RETRACTIBLEHANDLE$30,
                    0);

            return target;
        }
    }

    /**
     * True if has "retractibleHandle" element
     */
    public boolean isSetRetractibleHandle() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(RETRACTIBLEHANDLE$30) != 0;
        }
    }

    /**
     * Sets the "retractibleHandle" element
     */
    public void setRetractibleHandle(boolean retractibleHandle) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(RETRACTIBLEHANDLE$30,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(RETRACTIBLEHANDLE$30);
            }

            target.setBooleanValue(retractibleHandle);
        }
    }

    /**
     * Sets (as xml) the "retractibleHandle" element
     */
    public void xsetRetractibleHandle(
        org.apache.xmlbeans.XmlBoolean retractibleHandle) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean) get_store()
                                                          .find_element_user(RETRACTIBLEHANDLE$30,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlBoolean) get_store()
                                                              .add_element_user(RETRACTIBLEHANDLE$30);
            }

            target.set(retractibleHandle);
        }
    }

    /**
     * Unsets the "retractibleHandle" element
     */
    public void unsetRetractibleHandle() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(RETRACTIBLEHANDLE$30, 0);
        }
    }

    /**
     * Gets the "ribbonsOrMarkings" element
     */
    public boolean getRibbonsOrMarkings() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(RIBBONSORMARKINGS$32,
                    0);

            if (target == null) {
                return false;
            }

            return target.getBooleanValue();
        }
    }

    /**
     * Gets (as xml) the "ribbonsOrMarkings" element
     */
    public org.apache.xmlbeans.XmlBoolean xgetRibbonsOrMarkings() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean) get_store()
                                                          .find_element_user(RIBBONSORMARKINGS$32,
                    0);

            return target;
        }
    }

    /**
     * True if has "ribbonsOrMarkings" element
     */
    public boolean isSetRibbonsOrMarkings() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(RIBBONSORMARKINGS$32) != 0;
        }
    }

    /**
     * Sets the "ribbonsOrMarkings" element
     */
    public void setRibbonsOrMarkings(boolean ribbonsOrMarkings) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(RIBBONSORMARKINGS$32,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(RIBBONSORMARKINGS$32);
            }

            target.setBooleanValue(ribbonsOrMarkings);
        }
    }

    /**
     * Sets (as xml) the "ribbonsOrMarkings" element
     */
    public void xsetRibbonsOrMarkings(
        org.apache.xmlbeans.XmlBoolean ribbonsOrMarkings) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean) get_store()
                                                          .find_element_user(RIBBONSORMARKINGS$32,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlBoolean) get_store()
                                                              .add_element_user(RIBBONSORMARKINGS$32);
            }

            target.set(ribbonsOrMarkings);
        }
    }

    /**
     * Unsets the "ribbonsOrMarkings" element
     */
    public void unsetRibbonsOrMarkings() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(RIBBONSORMARKINGS$32, 0);
        }
    }

    /**
     * Gets the "softSided" element
     */
    public boolean getSoftSided() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(SOFTSIDED$34,
                    0);

            if (target == null) {
                return false;
            }

            return target.getBooleanValue();
        }
    }

    /**
     * Gets (as xml) the "softSided" element
     */
    public org.apache.xmlbeans.XmlBoolean xgetSoftSided() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean) get_store()
                                                          .find_element_user(SOFTSIDED$34,
                    0);

            return target;
        }
    }

    /**
     * True if has "softSided" element
     */
    public boolean isSetSoftSided() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(SOFTSIDED$34) != 0;
        }
    }

    /**
     * Sets the "softSided" element
     */
    public void setSoftSided(boolean softSided) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(SOFTSIDED$34,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(SOFTSIDED$34);
            }

            target.setBooleanValue(softSided);
        }
    }

    /**
     * Sets (as xml) the "softSided" element
     */
    public void xsetSoftSided(org.apache.xmlbeans.XmlBoolean softSided) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean) get_store()
                                                          .find_element_user(SOFTSIDED$34,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlBoolean) get_store()
                                                              .add_element_user(SOFTSIDED$34);
            }

            target.set(softSided);
        }
    }

    /**
     * Unsets the "softSided" element
     */
    public void unsetSoftSided() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(SOFTSIDED$34, 0);
        }
    }

    /**
     * Gets the "tag" element
     */
    public java.lang.String getTag() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(TAG$36,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "tag" element
     */
    public org.apache.xmlbeans.XmlString xgetTag() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(TAG$36,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "tag" element
     */
    public boolean isNilTag() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(TAG$36,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "tag" element
     */
    public boolean isSetTag() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(TAG$36) != 0;
        }
    }

    /**
     * Sets the "tag" element
     */
    public void setTag(java.lang.String tag) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(TAG$36,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(TAG$36);
            }

            target.setStringValue(tag);
        }
    }

    /**
     * Sets (as xml) the "tag" element
     */
    public void xsetTag(org.apache.xmlbeans.XmlString tag) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(TAG$36,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(TAG$36);
            }

            target.set(tag);
        }
    }

    /**
     * Nils the "tag" element
     */
    public void setNilTag() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(TAG$36,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(TAG$36);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "tag" element
     */
    public void unsetTag() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(TAG$36, 0);
        }
    }

    /**
     * Gets the "trim" element
     */
    public boolean getTrim() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(TRIM$38,
                    0);

            if (target == null) {
                return false;
            }

            return target.getBooleanValue();
        }
    }

    /**
     * Gets (as xml) the "trim" element
     */
    public org.apache.xmlbeans.XmlBoolean xgetTrim() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean) get_store()
                                                          .find_element_user(TRIM$38,
                    0);

            return target;
        }
    }

    /**
     * True if has "trim" element
     */
    public boolean isSetTrim() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(TRIM$38) != 0;
        }
    }

    /**
     * Sets the "trim" element
     */
    public void setTrim(boolean trim) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(TRIM$38,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(TRIM$38);
            }

            target.setBooleanValue(trim);
        }
    }

    /**
     * Sets (as xml) the "trim" element
     */
    public void xsetTrim(org.apache.xmlbeans.XmlBoolean trim) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean) get_store()
                                                          .find_element_user(TRIM$38,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlBoolean) get_store()
                                                              .add_element_user(TRIM$38);
            }

            target.set(trim);
        }
    }

    /**
     * Unsets the "trim" element
     */
    public void unsetTrim() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(TRIM$38, 0);
        }
    }

    /**
     * Gets the "wheels" element
     */
    public boolean getWheels() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(WHEELS$40,
                    0);

            if (target == null) {
                return false;
            }

            return target.getBooleanValue();
        }
    }

    /**
     * Gets (as xml) the "wheels" element
     */
    public org.apache.xmlbeans.XmlBoolean xgetWheels() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean) get_store()
                                                          .find_element_user(WHEELS$40,
                    0);

            return target;
        }
    }

    /**
     * True if has "wheels" element
     */
    public boolean isSetWheels() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(WHEELS$40) != 0;
        }
    }

    /**
     * Sets the "wheels" element
     */
    public void setWheels(boolean wheels) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(WHEELS$40,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(WHEELS$40);
            }

            target.setBooleanValue(wheels);
        }
    }

    /**
     * Sets (as xml) the "wheels" element
     */
    public void xsetWheels(org.apache.xmlbeans.XmlBoolean wheels) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean) get_store()
                                                          .find_element_user(WHEELS$40,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlBoolean) get_store()
                                                              .add_element_user(WHEELS$40);
            }

            target.set(wheels);
        }
    }

    /**
     * Unsets the "wheels" element
     */
    public void unsetWheels() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(WHEELS$40, 0);
        }
    }

    /**
     * Gets the "zippers" element
     */
    public boolean getZippers() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(ZIPPERS$42,
                    0);

            if (target == null) {
                return false;
            }

            return target.getBooleanValue();
        }
    }

    /**
     * Gets (as xml) the "zippers" element
     */
    public org.apache.xmlbeans.XmlBoolean xgetZippers() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean) get_store()
                                                          .find_element_user(ZIPPERS$42,
                    0);

            return target;
        }
    }

    /**
     * True if has "zippers" element
     */
    public boolean isSetZippers() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(ZIPPERS$42) != 0;
        }
    }

    /**
     * Sets the "zippers" element
     */
    public void setZippers(boolean zippers) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(ZIPPERS$42,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(ZIPPERS$42);
            }

            target.setBooleanValue(zippers);
        }
    }

    /**
     * Sets (as xml) the "zippers" element
     */
    public void xsetZippers(org.apache.xmlbeans.XmlBoolean zippers) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean) get_store()
                                                          .find_element_user(ZIPPERS$42,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlBoolean) get_store()
                                                              .add_element_user(ZIPPERS$42);
            }

            target.set(zippers);
        }
    }

    /**
     * Unsets the "zippers" element
     */
    public void unsetZippers() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(ZIPPERS$42, 0);
        }
    }
}
