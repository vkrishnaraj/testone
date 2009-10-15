/*
 * XML Type:  Content
 * Namespace: http://common.wt_1_0.serviceprovider.nettracer.aero/xsd
 * Java type: aero.nettracer.serviceprovider.wt_1_0.common.xsd.Content
 *
 * Automatically generated - do not modify.
 */
package aero.nettracer.serviceprovider.wt_1_0.common.xsd.impl;

/**
 * An XML Content(@http://common.wt_1_0.serviceprovider.nettracer.aero/xsd).
 *
 * This is a complex type.
 */
public class ContentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
    implements aero.nettracer.serviceprovider.wt_1_0.common.xsd.Content {
    private static final javax.xml.namespace.QName CATEGORY$0 = new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd",
            "category");
    private static final javax.xml.namespace.QName DESCRIPTION$2 = new javax.xml.namespace.QName("http://common.wt_1_0.serviceprovider.nettracer.aero/xsd",
            "description");

    public ContentImpl(org.apache.xmlbeans.SchemaType sType) {
        super(sType);
    }

    /**
     * Gets the "category" element
     */
    public java.lang.String getCategory() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(CATEGORY$0,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "category" element
     */
    public org.apache.xmlbeans.XmlString xgetCategory() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(CATEGORY$0,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "category" element
     */
    public boolean isNilCategory() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(CATEGORY$0,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "category" element
     */
    public boolean isSetCategory() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(CATEGORY$0) != 0;
        }
    }

    /**
     * Sets the "category" element
     */
    public void setCategory(java.lang.String category) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(CATEGORY$0,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(CATEGORY$0);
            }

            target.setStringValue(category);
        }
    }

    /**
     * Sets (as xml) the "category" element
     */
    public void xsetCategory(org.apache.xmlbeans.XmlString category) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(CATEGORY$0,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(CATEGORY$0);
            }

            target.set(category);
        }
    }

    /**
     * Nils the "category" element
     */
    public void setNilCategory() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(CATEGORY$0,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(CATEGORY$0);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "category" element
     */
    public void unsetCategory() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(CATEGORY$0, 0);
        }
    }

    /**
     * Gets the "description" element
     */
    public java.lang.String getDescription() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(DESCRIPTION$2,
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
                                                         .find_element_user(DESCRIPTION$2,
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
                                                         .find_element_user(DESCRIPTION$2,
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

            return get_store().count_elements(DESCRIPTION$2) != 0;
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
                                                           .find_element_user(DESCRIPTION$2,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(DESCRIPTION$2);
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
                                                         .find_element_user(DESCRIPTION$2,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(DESCRIPTION$2);
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
                                                         .find_element_user(DESCRIPTION$2,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(DESCRIPTION$2);
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
            get_store().remove_element(DESCRIPTION$2, 0);
        }
    }
}
