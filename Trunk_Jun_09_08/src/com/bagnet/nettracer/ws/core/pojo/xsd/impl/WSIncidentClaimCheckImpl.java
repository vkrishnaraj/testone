/*
 * XML Type:  WS_IncidentClaimCheck
 * Namespace: http://pojo.core.ws.nettracer.bagnet.com/xsd
 * Java type: com.bagnet.nettracer.ws.core.pojo.xsd.WSIncidentClaimCheck
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.core.pojo.xsd.impl;

/**
 * An XML WS_IncidentClaimCheck(@http://pojo.core.ws.nettracer.bagnet.com/xsd).
 *
 * This is a complex type.
 */
public class WSIncidentClaimCheckImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
    implements com.bagnet.nettracer.ws.core.pojo.xsd.WSIncidentClaimCheck {
    private static final javax.xml.namespace.QName CLAIMCHECKNUM$0 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "claimchecknum");
    private static final javax.xml.namespace.QName MATCHEDOHD$2 = new javax.xml.namespace.QName("http://pojo.core.ws.nettracer.bagnet.com/xsd",
            "matched_ohd");

    public WSIncidentClaimCheckImpl(org.apache.xmlbeans.SchemaType sType) {
        super(sType);
    }

    /**
     * Gets the "claimchecknum" element
     */
    public java.lang.String getClaimchecknum() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(CLAIMCHECKNUM$0,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "claimchecknum" element
     */
    public org.apache.xmlbeans.XmlString xgetClaimchecknum() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(CLAIMCHECKNUM$0,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "claimchecknum" element
     */
    public boolean isNilClaimchecknum() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(CLAIMCHECKNUM$0,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "claimchecknum" element
     */
    public boolean isSetClaimchecknum() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(CLAIMCHECKNUM$0) != 0;
        }
    }

    /**
     * Sets the "claimchecknum" element
     */
    public void setClaimchecknum(java.lang.String claimchecknum) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(CLAIMCHECKNUM$0,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(CLAIMCHECKNUM$0);
            }

            target.setStringValue(claimchecknum);
        }
    }

    /**
     * Sets (as xml) the "claimchecknum" element
     */
    public void xsetClaimchecknum(org.apache.xmlbeans.XmlString claimchecknum) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(CLAIMCHECKNUM$0,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(CLAIMCHECKNUM$0);
            }

            target.set(claimchecknum);
        }
    }

    /**
     * Nils the "claimchecknum" element
     */
    public void setNilClaimchecknum() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(CLAIMCHECKNUM$0,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(CLAIMCHECKNUM$0);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "claimchecknum" element
     */
    public void unsetClaimchecknum() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(CLAIMCHECKNUM$0, 0);
        }
    }

    /**
     * Gets the "matched_ohd" element
     */
    public java.lang.String getMatchedOhd() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(MATCHEDOHD$2,
                    0);

            if (target == null) {
                return null;
            }

            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "matched_ohd" element
     */
    public org.apache.xmlbeans.XmlString xgetMatchedOhd() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(MATCHEDOHD$2,
                    0);

            return target;
        }
    }

    /**
     * Tests for nil "matched_ohd" element
     */
    public boolean isNilMatchedOhd() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(MATCHEDOHD$2,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "matched_ohd" element
     */
    public boolean isSetMatchedOhd() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(MATCHEDOHD$2) != 0;
        }
    }

    /**
     * Sets the "matched_ohd" element
     */
    public void setMatchedOhd(java.lang.String matchedOhd) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                           .find_element_user(MATCHEDOHD$2,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue) get_store()
                                                               .add_element_user(MATCHEDOHD$2);
            }

            target.setStringValue(matchedOhd);
        }
    }

    /**
     * Sets (as xml) the "matched_ohd" element
     */
    public void xsetMatchedOhd(org.apache.xmlbeans.XmlString matchedOhd) {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(MATCHEDOHD$2,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(MATCHEDOHD$2);
            }

            target.set(matchedOhd);
        }
    }

    /**
     * Nils the "matched_ohd" element
     */
    public void setNilMatchedOhd() {
        synchronized (monitor()) {
            check_orphaned();

            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString) get_store()
                                                         .find_element_user(MATCHEDOHD$2,
                    0);

            if (target == null) {
                target = (org.apache.xmlbeans.XmlString) get_store()
                                                             .add_element_user(MATCHEDOHD$2);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "matched_ohd" element
     */
    public void unsetMatchedOhd() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(MATCHEDOHD$2, 0);
        }
    }
}
