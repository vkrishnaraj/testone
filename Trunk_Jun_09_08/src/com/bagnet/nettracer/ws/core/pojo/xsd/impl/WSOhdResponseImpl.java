/*
 * XML Type:  WS_OhdResponse
 * Namespace: http://pojo.core.ws.nettracer.bagnet.com/xsd
 * Java type: com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.core.pojo.xsd.impl;

/**
 * An XML WS_OhdResponse(@http://pojo.core.ws.nettracer.bagnet.com/xsd).
 *
 * This is a complex type.
 */
public class WSOhdResponseImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
    implements com.bagnet.nettracer.ws.core.pojo.xsd.WSOhdResponse {
    private static final javax.xml.namespace.QName CLAIMCHECKNUMBER$0 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "claimCheckNumber");
    private static final javax.xml.namespace.QName ERRORRESPONSE$2 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "errorResponse");
    private static final javax.xml.namespace.QName OHDID$4 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "ohdId");

    public WSOhdResponseImpl(org.apache.xmlbeans.SchemaType sType) {
        super(sType);
    }

    /**
     * Gets the "claimCheckNumber" element
     */
    public java.lang.String getClaimCheckNumber() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(CLAIMCHECKNUMBER$0,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "claimCheckNumber" element
     */
    public org.apache.xmlbeans.XmlString xgetClaimCheckNumber() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(CLAIMCHECKNUMBER$0,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "claimCheckNumber" element
     */
    public boolean isNilClaimCheckNumber() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(CLAIMCHECKNUMBER$0,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "claimCheckNumber" element
     */
    public boolean isSetClaimCheckNumber() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(CLAIMCHECKNUMBER$0) != 0;
        }
    }

    /**
     * Sets the "claimCheckNumber" element
     */
    public void setClaimCheckNumber(java.lang.String claimCheckNumber) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(CLAIMCHECKNUMBER$0,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(CLAIMCHECKNUMBER$0);
            }

            target.setStringValue(claimCheckNumber);
        }
    }

    /**
     * Sets (as xml) the "claimCheckNumber" element
     */
    public void xsetClaimCheckNumber(
        org.apache.xmlbeans.XmlString claimCheckNumber) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(CLAIMCHECKNUMBER$0,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(CLAIMCHECKNUMBER$0);
            }

            target.set(claimCheckNumber);
        }
    }

    /**
     * Nils the "claimCheckNumber" element
     */
    public void setNilClaimCheckNumber() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(CLAIMCHECKNUMBER$0,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(CLAIMCHECKNUMBER$0);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "claimCheckNumber" element
     */
    public void unsetClaimCheckNumber() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(CLAIMCHECKNUMBER$0, 0);
        }
    }

    /**
     * Gets the "errorResponse" element
     */
    public java.lang.String getErrorResponse() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(ERRORRESPONSE$2,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "errorResponse" element
     */
    public org.apache.xmlbeans.XmlString xgetErrorResponse() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(ERRORRESPONSE$2,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "errorResponse" element
     */
    public boolean isNilErrorResponse() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(ERRORRESPONSE$2,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "errorResponse" element
     */
    public boolean isSetErrorResponse() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(ERRORRESPONSE$2) != 0;
        }
    }

    /**
     * Sets the "errorResponse" element
     */
    public void setErrorResponse(java.lang.String errorResponse) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(ERRORRESPONSE$2,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(ERRORRESPONSE$2);
            }

            target.setStringValue(errorResponse);
        }
    }

    /**
     * Sets (as xml) the "errorResponse" element
     */
    public void xsetErrorResponse(org.apache.xmlbeans.XmlString errorResponse) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(ERRORRESPONSE$2,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(ERRORRESPONSE$2);
            }

            target.set(errorResponse);
        }
    }

    /**
     * Nils the "errorResponse" element
     */
    public void setNilErrorResponse() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(ERRORRESPONSE$2,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(ERRORRESPONSE$2);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "errorResponse" element
     */
    public void unsetErrorResponse() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(ERRORRESPONSE$2, 0);
        }
    }

    /**
     * Gets the "ohdId" element
     */
    public java.lang.String getOhdId() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(OHDID$4,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "ohdId" element
     */
    public org.apache.xmlbeans.XmlString xgetOhdId() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(OHDID$4,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "ohdId" element
     */
    public boolean isNilOhdId() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(OHDID$4,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "ohdId" element
     */
    public boolean isSetOhdId() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(OHDID$4) != 0;
        }
    }

    /**
     * Sets the "ohdId" element
     */
    public void setOhdId(java.lang.String ohdId) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(OHDID$4,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(OHDID$4);
            }

            target.setStringValue(ohdId);
        }
    }

    /**
     * Sets (as xml) the "ohdId" element
     */
    public void xsetOhdId(org.apache.xmlbeans.XmlString ohdId) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(OHDID$4,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(OHDID$4);
            }

            target.set(ohdId);
        }
    }

    /**
     * Nils the "ohdId" element
     */
    public void setNilOhdId() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(OHDID$4,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(OHDID$4);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "ohdId" element
     */
    public void unsetOhdId() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(OHDID$4, 0);
        }
    }
}
