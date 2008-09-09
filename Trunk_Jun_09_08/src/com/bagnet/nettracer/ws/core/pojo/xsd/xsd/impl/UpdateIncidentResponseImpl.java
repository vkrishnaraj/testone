/*
 * XML Type:  UpdateIncidentResponse
 * Namespace: http://xsd.pojo.core.ws.nettracer.bagnet.com/xsd
 * Java type: com.bagnet.nettracer.ws.core.pojo.xsd.xsd.UpdateIncidentResponse
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.core.pojo.xsd.xsd.impl;

/**
 * An XML UpdateIncidentResponse(@http://xsd.pojo.core.ws.nettracer.bagnet.com/xsd).
 *
 * This is a complex type.
 */
public class UpdateIncidentResponseImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
    implements com.bagnet.nettracer.ws.core.pojo.xsd.xsd.UpdateIncidentResponse {
    private static final javax.xml.namespace.QName ERRORRESPONSE$0 = new javax.xml.namespace.QName("http://xsd.pojo.core.ws.nettracer.bagnet.com/xsd",
            "errorResponse");
    private static final javax.xml.namespace.QName SUCCESS$2 = new javax.xml.namespace.QName("http://xsd.pojo.core.ws.nettracer.bagnet.com/xsd",
            "success");

    public UpdateIncidentResponseImpl(org.apache.xmlbeans.SchemaType sType) {
        super(sType);
    }

    /**
     * Gets the "errorResponse" element
     */
    public java.lang.String getErrorResponse() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(ERRORRESPONSE$0,
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
                                                         .find_element_user(ERRORRESPONSE$0,
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
                                                         .find_element_user(ERRORRESPONSE$0,
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

            return get_store().count_elements(ERRORRESPONSE$0) != 0;
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
                                                           .find_element_user(ERRORRESPONSE$0,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(ERRORRESPONSE$0);
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
                                                         .find_element_user(ERRORRESPONSE$0,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(ERRORRESPONSE$0);
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
                                                         .find_element_user(ERRORRESPONSE$0,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(ERRORRESPONSE$0);
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
            get_store().remove_element(ERRORRESPONSE$0, 0);
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
                                                           .find_element_user(SUCCESS$2,
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
                                                          .find_element_user(SUCCESS$2,
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

            return get_store().count_elements(SUCCESS$2) != 0;
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
                                                           .find_element_user(SUCCESS$2,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(SUCCESS$2);
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
                                                          .find_element_user(SUCCESS$2,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlBoolean) get_store()
                                                              .add_element_user(SUCCESS$2);
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
            get_store().remove_element(SUCCESS$2, 0);
        }
    }
}
