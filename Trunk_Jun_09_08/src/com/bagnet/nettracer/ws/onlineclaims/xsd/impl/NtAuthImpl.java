/*
 * XML Type:  NtAuth
 * Namespace: http://onlineclaims.ws.nettracer.bagnet.com/xsd
 * Java type: com.bagnet.nettracer.ws.onlineclaims.xsd.NtAuth
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.onlineclaims.xsd.impl;

/**
 * An XML NtAuth(@http://onlineclaims.ws.nettracer.bagnet.com/xsd).
 *
 * This is a complex type.
 */
public class NtAuthImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
    implements com.bagnet.nettracer.ws.onlineclaims.xsd.NtAuth {
    private static final javax.xml.namespace.QName PASSWORD$0 = new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd",
            "password");
    private static final javax.xml.namespace.QName USERNAME$2 = new javax.xml.namespace.QName("http://onlineclaims.ws.nettracer.bagnet.com/xsd",
            "username");

    public NtAuthImpl(org.apache.xmlbeans.SchemaType sType) {
        super(sType);
    }

    /**
     * Gets the "password" element
     */
    public java.lang.String getPassword() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(PASSWORD$0,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "password" element
     */
    public org.apache.xmlbeans.XmlString xgetPassword() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(PASSWORD$0,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "password" element
     */
    public boolean isNilPassword() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(PASSWORD$0,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "password" element
     */
    public boolean isSetPassword() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(PASSWORD$0) != 0;
        }
    }

    /**
     * Sets the "password" element
     */
    public void setPassword(java.lang.String password) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(PASSWORD$0,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(PASSWORD$0);
            }

            target.setStringValue(password);
        }
    }

    /**
     * Sets (as xml) the "password" element
     */
    public void xsetPassword(org.apache.xmlbeans.XmlString password) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(PASSWORD$0,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(PASSWORD$0);
            }

            target.set(password);
        }
    }

    /**
     * Nils the "password" element
     */
    public void setNilPassword() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(PASSWORD$0,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(PASSWORD$0);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "password" element
     */
    public void unsetPassword() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(PASSWORD$0, 0);
        }
    }

    /**
     * Gets the "username" element
     */
    public java.lang.String getUsername() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(USERNAME$2,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "username" element
     */
    public org.apache.xmlbeans.XmlString xgetUsername() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(USERNAME$2,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "username" element
     */
    public boolean isNilUsername() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(USERNAME$2,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "username" element
     */
    public boolean isSetUsername() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(USERNAME$2) != 0;
        }
    }

    /**
     * Sets the "username" element
     */
    public void setUsername(java.lang.String username) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(USERNAME$2,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(USERNAME$2);
            }

            target.setStringValue(username);
        }
    }

    /**
     * Sets (as xml) the "username" element
     */
    public void xsetUsername(org.apache.xmlbeans.XmlString username) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(USERNAME$2,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(USERNAME$2);
            }

            target.set(username);
        }
    }

    /**
     * Nils the "username" element
     */
    public void setNilUsername() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(USERNAME$2,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(USERNAME$2);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "username" element
     */
    public void unsetUsername() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(USERNAME$2, 0);
        }
    }
}
