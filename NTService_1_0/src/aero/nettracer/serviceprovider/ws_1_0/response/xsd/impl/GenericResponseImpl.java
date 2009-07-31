/*
 * XML Type:  GenericResponse
 * Namespace: http://response.ws_1_0.serviceprovider.nettracer.aero/xsd
 * Java type: aero.nettracer.serviceprovider.ws_1_0.response.xsd.GenericResponse
 *
 * Automatically generated - do not modify.
 */
package aero.nettracer.serviceprovider.ws_1_0.response.xsd.impl;

/**
 * An XML GenericResponse(@http://response.ws_1_0.serviceprovider.nettracer.aero/xsd).
 *
 * This is a complex type.
 */
public class GenericResponseImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl
    implements aero.nettracer.serviceprovider.ws_1_0.response.xsd.GenericResponse {
    private static final javax.xml.namespace.QName ERROR$0 = new javax.xml.namespace.QName("http://response.ws_1_0.serviceprovider.nettracer.aero/xsd",
            "error");

    public GenericResponseImpl(org.apache.xmlbeans.SchemaType sType) {
        super(sType);
    }

    /**
     * Gets the "error" element
     */
    public aero.nettracer.serviceprovider.ws_1_0.common.xsd.WebServiceError getError() {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.ws_1_0.common.xsd.WebServiceError target =
                null;
            target = (aero.nettracer.serviceprovider.ws_1_0.common.xsd.WebServiceError) get_store()
                                                                                            .find_element_user(ERROR$0,
                    0);

            if (target == null) {
                return null;
            }

            return target;
        }
    }

    /**
     * Tests for nil "error" element
     */
    public boolean isNilError() {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.ws_1_0.common.xsd.WebServiceError target =
                null;
            target = (aero.nettracer.serviceprovider.ws_1_0.common.xsd.WebServiceError) get_store()
                                                                                            .find_element_user(ERROR$0,
                    0);

            if (target == null) {
                return false;
            }

            return target.isNil();
        }
    }

    /**
     * True if has "error" element
     */
    public boolean isSetError() {
        synchronized (monitor()) {
            check_orphaned();

            return get_store().count_elements(ERROR$0) != 0;
        }
    }

    /**
     * Sets the "error" element
     */
    public void setError(
        aero.nettracer.serviceprovider.ws_1_0.common.xsd.WebServiceError error) {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.ws_1_0.common.xsd.WebServiceError target =
                null;
            target = (aero.nettracer.serviceprovider.ws_1_0.common.xsd.WebServiceError) get_store()
                                                                                            .find_element_user(ERROR$0,
                    0);

            if (target == null) {
                target = (aero.nettracer.serviceprovider.ws_1_0.common.xsd.WebServiceError) get_store()
                                                                                                .add_element_user(ERROR$0);
            }

            target.set(error);
        }
    }

    /**
     * Appends and returns a new empty "error" element
     */
    public aero.nettracer.serviceprovider.ws_1_0.common.xsd.WebServiceError addNewError() {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.ws_1_0.common.xsd.WebServiceError target =
                null;
            target = (aero.nettracer.serviceprovider.ws_1_0.common.xsd.WebServiceError) get_store()
                                                                                            .add_element_user(ERROR$0);

            return target;
        }
    }

    /**
     * Nils the "error" element
     */
    public void setNilError() {
        synchronized (monitor()) {
            check_orphaned();

            aero.nettracer.serviceprovider.ws_1_0.common.xsd.WebServiceError target =
                null;
            target = (aero.nettracer.serviceprovider.ws_1_0.common.xsd.WebServiceError) get_store()
                                                                                            .find_element_user(ERROR$0,
                    0);

            if (target == null) {
                target = (aero.nettracer.serviceprovider.ws_1_0.common.xsd.WebServiceError) get_store()
                                                                                                .add_element_user(ERROR$0);
            }

            target.setNil();
        }
    }

    /**
     * Unsets the "error" element
     */
    public void unsetError() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(ERROR$0, 0);
        }
    }
}
