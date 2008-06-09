/*
 * An XML document type.
 * Localname: insertOHD
 * Namespace: http://core.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.core.InsertOHDDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.core.impl;

/**
 * A document containing one insertOHD(@http://core.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public class InsertOHDDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
    implements com.bagnet.nettracer.ws.core.InsertOHDDocument {
    private static final javax.xml.namespace.QName INSERTOHD$0 = new javax.xml.namespace.QName("http://core.ws.nettracer.bagnet.com",
            "insertOHD");

    public InsertOHDDocumentImpl(org.apache.xmlbeans.SchemaType sType) {
        super(sType);
    }

    /**
     * Gets the "insertOHD" element
     */
    public com.bagnet.nettracer.ws.core.InsertOHDDocument.InsertOHD getInsertOHD() {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.InsertOHDDocument.InsertOHD target = null;
            target = (com.bagnet.nettracer.ws.core.InsertOHDDocument.InsertOHD) get_store()
                                                                                    .find_element_user(INSERTOHD$0,
                    0);

            if (target == null) {
                return null;
            }

            return target;
        }
    }

    /**
     * Sets the "insertOHD" element
     */
    public void setInsertOHD(
        com.bagnet.nettracer.ws.core.InsertOHDDocument.InsertOHD insertOHD) {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.InsertOHDDocument.InsertOHD target = null;
            target = (com.bagnet.nettracer.ws.core.InsertOHDDocument.InsertOHD) get_store()
                                                                                    .find_element_user(INSERTOHD$0,
                    0);

            if (target == null) {
                target = (com.bagnet.nettracer.ws.core.InsertOHDDocument.InsertOHD) get_store()
                                                                                        .add_element_user(INSERTOHD$0);
            }

            target.set(insertOHD);
        }
    }

    /**
     * Appends and returns a new empty "insertOHD" element
     */
    public com.bagnet.nettracer.ws.core.InsertOHDDocument.InsertOHD addNewInsertOHD() {
        synchronized (monitor()) {
            check_orphaned();

            com.bagnet.nettracer.ws.core.InsertOHDDocument.InsertOHD target = null;
            target = (com.bagnet.nettracer.ws.core.InsertOHDDocument.InsertOHD) get_store()
                                                                                    .add_element_user(INSERTOHD$0);

            return target;
        }
    }

    /**
     * An XML insertOHD(@http://core.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public static class InsertOHDImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
        implements com.bagnet.nettracer.ws.core.InsertOHDDocument.InsertOHD {
        private static final javax.xml.namespace.QName SO$0 = new javax.xml.namespace.QName("http://core.ws.nettracer.bagnet.com",
                "so");

        public InsertOHDImpl(org.apache.xmlbeans.SchemaType sType) {
            super(sType);
        }

        /**
         * Gets the "so" element
         */
        public com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD getSo() {
            synchronized (monitor()) {
                check_orphaned();

                com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD) get_store()
                                                                           .find_element_user(SO$0,
                        0);

                if (target == null) {
                    return null;
                }

                return target;
            }
        }

        /**
         * Tests for nil "so" element
         */
        public boolean isNilSo() {
            synchronized (monitor()) {
                check_orphaned();

                com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD) get_store()
                                                                           .find_element_user(SO$0,
                        0);

                if (target == null) {
                    return false;
                }

                return target.isNil();
            }
        }

        /**
         * True if has "so" element
         */
        public boolean isSetSo() {
            synchronized (monitor()) {
                check_orphaned();

                return get_store().count_elements(SO$0) != 0;
            }
        }

        /**
         * Sets the "so" element
         */
        public void setSo(com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD so) {
            synchronized (monitor()) {
                check_orphaned();

                com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD) get_store()
                                                                           .find_element_user(SO$0,
                        0);

                if (target == null) {
                    target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD) get_store()
                                                                               .add_element_user(SO$0);
                }

                target.set(so);
            }
        }

        /**
         * Appends and returns a new empty "so" element
         */
        public com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD addNewSo() {
            synchronized (monitor()) {
                check_orphaned();

                com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD) get_store()
                                                                           .add_element_user(SO$0);

                return target;
            }
        }

        /**
         * Nils the "so" element
         */
        public void setNilSo() {
            synchronized (monitor()) {
                check_orphaned();

                com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD target = null;
                target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD) get_store()
                                                                           .find_element_user(SO$0,
                        0);

                if (target == null) {
                    target = (com.bagnet.nettracer.ws.core.pojo.xsd.WSOHD) get_store()
                                                                               .add_element_user(SO$0);
                }

                target.setNil();
            }
        }

        /**
         * Unsets the "so" element
         */
        public void unsetSo() {
            synchronized (monitor()) {
                check_orphaned();
                get_store().remove_element(SO$0, 0);
            }
        }
    }
}
