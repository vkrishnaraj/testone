/*
 * XML Type:  File
 * Namespace: http://onlineclaims.ws.nettracer.bagnet.com/xsd
 * Java type: com.bagnet.nettracer.ws.onlineclaims.xsd.File
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.onlineclaims.xsd.impl;

/**
 * An XML File(@http://onlineclaims.ws.nettracer.bagnet.com/xsd).
 *
 * This is a complex type.
 */
public class FileImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
    implements com.bagnet.nettracer.ws.onlineclaims.xsd.File {
    private static final javax.xml.namespace.QName FILENAME$0 = new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd",
            "filename");
    private static final javax.xml.namespace.QName ID$2 = new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd",
            "id");

    public FileImpl(org.apache.xmlbeans.SchemaType sType) {
        super(sType);
    }

    /**
     * Gets the "filename" element
     */
    public java.lang.String getFilename() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(FILENAME$0,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "filename" element
     */
    public org.apache.xmlbeans.XmlString xgetFilename() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(FILENAME$0,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "filename" element
     */
    public boolean isNilFilename() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(FILENAME$0,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "filename" element
     */
    public boolean isSetFilename() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(FILENAME$0) != 0;
        }
    }

    /**
     * Sets the "filename" element
     */
    public void setFilename(java.lang.String filename) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(FILENAME$0,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(FILENAME$0);
            }

            target.setStringValue(filename);
        }
    }

    /**
     * Sets (as xml) the "filename" element
     */
    public void xsetFilename(org.apache.xmlbeans.XmlString filename) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(FILENAME$0,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(FILENAME$0);
            }

            target.set(filename);
        }
    }

    /**
     * Nils the "filename" element
     */
    public void setNilFilename() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(FILENAME$0,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(FILENAME$0);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "filename" element
     */
    public void unsetFilename() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(FILENAME$0, 0);
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
                                                           .find_element_user(ID$2,
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
                                                       .find_element_user(ID$2,
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

            return get_store().count_elements(ID$2) != 0;
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
                                                           .find_element_user(ID$2,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(ID$2);
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
                                                       .find_element_user(ID$2,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlLong) get_store()
                                                           .add_element_user(ID$2);
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
            get_store().remove_element(ID$2, 0);
        }
    }
}
