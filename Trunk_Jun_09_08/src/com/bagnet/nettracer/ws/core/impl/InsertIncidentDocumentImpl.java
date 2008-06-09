/*
 * An XML document type.
 * Localname: insertIncident
 * Namespace: http://core.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.core.InsertIncidentDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.core.impl;

/**
 * A document containing one insertIncident(@http://core.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public class InsertIncidentDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
    implements com.bagnet.nettracer.ws.core.InsertIncidentDocument {
    private static final javax.xml.namespace.QName INSERTINCIDENT$0 = new javax.xml.namespace.QName("http://core.ws.nettracer.bagnet.com",
            "insertIncident");

    public InsertIncidentDocumentImpl(org.apache.xmlbeans.SchemaType sType) {
        super(sType);
    }

    /**
     * Gets the "insertIncident" element
     */
    public com.bagnet.nettracer.ws.core.InsertIncidentDocument.InsertIncident getInsertIncident() {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.InsertIncidentDocument.InsertIncident target =
                null;
            target = (com.bagnet.nettracer.ws.core.InsertIncidentDocument.InsertIncident) get_store()
                                                                                              .find_element_user(INSERTINCIDENT$0,
                    0);

            if (target == null) {
                return null;
            }

            return target;
        }
    }

    /**
     * Sets the "insertIncident" element
     */
    public void setInsertIncident(
        com.bagnet.nettracer.ws.core.InsertIncidentDocument.InsertIncident insertIncident) {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.InsertIncidentDocument.InsertIncident target =
                null;
            target = (com.bagnet.nettracer.ws.core.InsertIncidentDocument.InsertIncident) get_store()
                                                                                              .find_element_user(INSERTINCIDENT$0,
                    0);

            if (target == null) {
                target = (com.bagnet.nettracer.ws.core.InsertIncidentDocument.InsertIncident) get_store()
                                                                                                  .add_element_user(INSERTINCIDENT$0);
            }

            target.set(insertIncident);
        }
    }

    /**
     * Appends and returns a new empty "insertIncident" element
     */
    public com.bagnet.nettracer.ws.core.InsertIncidentDocument.InsertIncident addNewInsertIncident() {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.InsertIncidentDocument.InsertIncident target =
                null;
            target = (com.bagnet.nettracer.ws.core.InsertIncidentDocument.InsertIncident) get_store()
                                                                                              .add_element_user(INSERTINCIDENT$0);

            return target;
        }
    }

    /**
     * An XML insertIncident(@http://core.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public static class InsertIncidentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
        implements com.bagnet.nettracer.ws.core.InsertIncidentDocument.InsertIncident {
        private static final javax.xml.namespace.QName SI$0 = new javax.xml.namespace.QName("http://core.ws.nettracer.bagnet.com",
                "si");

        public InsertIncidentImpl(org.apache.xmlbeans.SchemaType sType) {
            super(sType);
        }

        /**
         * Gets the "si" element
         */
        public com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident getSi() {
            synchronized (monitor()) {
                check_orphaned();

                com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident) get_store()
                                                                                .find_element_user(SI$0,
                        0);

                if (target == null) {
                    return null;
                }

                return target;
            }
        }

        /**
         * Tests for nil "si" element
         */
        public boolean isNilSi() {
            synchronized (monitor()) {
                check_orphaned();

                com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident) get_store()
                                                                                .find_element_user(SI$0,
                        0);

                if (target == null) {
                    return false;
                }

                return target.isNil();
            }
        }

        /**
         * True if has "si" element
         */
        public boolean isSetSi() {
            synchronized (monitor()) {
                check_orphaned();

                return get_store().count_elements(SI$0) != 0;
            }
        }

        /**
         * Sets the "si" element
         */
        public void setSi(com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident si) {
            synchronized (monitor()) {
                check_orphaned();

                com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident) get_store()
                                                                                .find_element_user(SI$0,
                        0);

                if (target == null) {
                    target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident) get_store()
                                                                                    .add_element_user(SI$0);
                }

                target.set(si);
            }
        }

        /**
         * Appends and returns a new empty "si" element
         */
        public com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident addNewSi() {
            synchronized (monitor()) {
                check_orphaned();

                com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident) get_store()
                                                                                .add_element_user(SI$0);

                return target;
            }
        }

        /**
         * Nils the "si" element
         */
        public void setNilSi() {
            synchronized (monitor()) {
                check_orphaned();

                com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident) get_store()
                                                                                .find_element_user(SI$0,
                        0);

                if (target == null) {
                    target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSIncident) get_store()
                                                                                    .add_element_user(SI$0);
                }

                target.setNil();
            }
        }

        /**
         * Unsets the "si" element
         */
        public void unsetSi() {
            synchronized (monitor()) {
                check_orphaned();
                get_store().remove_element(SI$0, 0);
            }
        }
    }
}
