/*
 * An XML document type.
 * Localname: deleteFile
 * Namespace: http://onlineclaims.ws.nettracer.bagnet.com
 * Java type: com.bagnet.nettracer.ws.onlineclaims.DeleteFileDocument
 *
 * Automatically generated - do not modify.
 */
package com.bagnet.nettracer.ws.onlineclaims;


/**
 * A document containing one deleteFile(@http://onlineclaims.ws.nettracer.bagnet.com) element.
 *
 * This is a complex type.
 */
public interface DeleteFileDocument extends org.apache.xmlbeans.XmlObject {
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType) org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(DeleteFileDocument.class.getClassLoader(),
            "schemaorg_apache_xmlbeans.system.sA32190FCCD380409BBAAD434D27BBA59")
                                                                                                                           .resolveHandle("deletefilef2ecdoctype");

    /**
     * Gets the "deleteFile" element
     */
    com.bagnet.nettracer.ws.onlineclaims.DeleteFileDocument.DeleteFile getDeleteFile();

    /**
     * Sets the "deleteFile" element
     */
    void setDeleteFile(
        com.bagnet.nettracer.ws.onlineclaims.DeleteFileDocument.DeleteFile deleteFile);

    /**
     * Appends and returns a new empty "deleteFile" element
     */
    com.bagnet.nettracer.ws.onlineclaims.DeleteFileDocument.DeleteFile addNewDeleteFile();

    /**
     * An XML deleteFile(@http://onlineclaims.ws.nettracer.bagnet.com).
     *
     * This is a complex type.
     */
    public interface DeleteFile extends org.apache.xmlbeans.XmlObject {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType) org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(DeleteFile.class.getClassLoader(),
                "schemaorg_apache_xmlbeans.system.sA32190FCCD380409BBAAD434D27BBA59")
                                                                                                                               .resolveHandle("deletefile17e9elemtype");

        /**
         * Gets the "claimId" element
         */
        long getClaimId();

        /**
         * Gets (as xml) the "claimId" element
         */
        org.apache.xmlbeans.XmlLong xgetClaimId();

        /**
         * True if has "claimId" element
         */
        boolean isSetClaimId();

        /**
         * Sets the "claimId" element
         */
        void setClaimId(long claimId);

        /**
         * Sets (as xml) the "claimId" element
         */
        void xsetClaimId(org.apache.xmlbeans.XmlLong claimId);

        /**
         * Unsets the "claimId" element
         */
        void unsetClaimId();

        /**
         * Gets the "fileId" element
         */
        long getFileId();

        /**
         * Gets (as xml) the "fileId" element
         */
        org.apache.xmlbeans.XmlLong xgetFileId();

        /**
         * True if has "fileId" element
         */
        boolean isSetFileId();

        /**
         * Sets the "fileId" element
         */
        void setFileId(long fileId);

        /**
         * Sets (as xml) the "fileId" element
         */
        void xsetFileId(org.apache.xmlbeans.XmlLong fileId);

        /**
         * Unsets the "fileId" element
         */
        void unsetFileId();

        /**
         * Gets the "auth" element
         */
        com.bagnet.nettracer.ws.onlineclaims.xsd.NtAuth getAuth();

        /**
         * Tests for nil "auth" element
         */
        boolean isNilAuth();

        /**
         * True if has "auth" element
         */
        boolean isSetAuth();

        /**
         * Sets the "auth" element
         */
        void setAuth(com.bagnet.nettracer.ws.onlineclaims.xsd.NtAuth auth);

        /**
         * Appends and returns a new empty "auth" element
         */
        com.bagnet.nettracer.ws.onlineclaims.xsd.NtAuth addNewAuth();

        /**
         * Nils the "auth" element
         */
        void setNilAuth();

        /**
         * Unsets the "auth" element
         */
        void unsetAuth();

        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        public static final class Factory {
            private Factory() {
            } // No instance of this class allowed

            public static com.bagnet.nettracer.ws.onlineclaims.DeleteFileDocument.DeleteFile newInstance() {
                return (com.bagnet.nettracer.ws.onlineclaims.DeleteFileDocument.DeleteFile) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                        .newInstance(type,
                    null);
            }

            public static com.bagnet.nettracer.ws.onlineclaims.DeleteFileDocument.DeleteFile newInstance(
                org.apache.xmlbeans.XmlOptions options) {
                return (com.bagnet.nettracer.ws.onlineclaims.DeleteFileDocument.DeleteFile) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                                        .newInstance(type,
                    options);
            }
        }
    }

    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    public static final class Factory {
        private Factory() {
        } // No instance of this class allowed

        public static com.bagnet.nettracer.ws.onlineclaims.DeleteFileDocument newInstance() {
            return (com.bagnet.nettracer.ws.onlineclaims.DeleteFileDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                         .newInstance(type,
                null);
        }

        public static com.bagnet.nettracer.ws.onlineclaims.DeleteFileDocument newInstance(
            org.apache.xmlbeans.XmlOptions options) {
            return (com.bagnet.nettracer.ws.onlineclaims.DeleteFileDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                         .newInstance(type,
                options);
        }

        /** @param xmlAsString the string value to parse */
        public static com.bagnet.nettracer.ws.onlineclaims.DeleteFileDocument parse(
            java.lang.String xmlAsString)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.onlineclaims.DeleteFileDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                         .parse(xmlAsString,
                type, null);
        }

        public static com.bagnet.nettracer.ws.onlineclaims.DeleteFileDocument parse(
            java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.onlineclaims.DeleteFileDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                         .parse(xmlAsString,
                type, options);
        }

        /** @param file the file from which to load an xml document */
        public static com.bagnet.nettracer.ws.onlineclaims.DeleteFileDocument parse(
            java.io.File file)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.onlineclaims.DeleteFileDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                         .parse(file,
                type, null);
        }

        public static com.bagnet.nettracer.ws.onlineclaims.DeleteFileDocument parse(
            java.io.File file, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.onlineclaims.DeleteFileDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                         .parse(file,
                type, options);
        }

        public static com.bagnet.nettracer.ws.onlineclaims.DeleteFileDocument parse(
            java.net.URL u)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.onlineclaims.DeleteFileDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                         .parse(u,
                type, null);
        }

        public static com.bagnet.nettracer.ws.onlineclaims.DeleteFileDocument parse(
            java.net.URL u, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.onlineclaims.DeleteFileDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                         .parse(u,
                type, options);
        }

        public static com.bagnet.nettracer.ws.onlineclaims.DeleteFileDocument parse(
            java.io.InputStream is)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.onlineclaims.DeleteFileDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                         .parse(is,
                type, null);
        }

        public static com.bagnet.nettracer.ws.onlineclaims.DeleteFileDocument parse(
            java.io.InputStream is, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.onlineclaims.DeleteFileDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                         .parse(is,
                type, options);
        }

        public static com.bagnet.nettracer.ws.onlineclaims.DeleteFileDocument parse(
            java.io.Reader r)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.onlineclaims.DeleteFileDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                         .parse(r,
                type, null);
        }

        public static com.bagnet.nettracer.ws.onlineclaims.DeleteFileDocument parse(
            java.io.Reader r, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException, java.io.IOException {
            return (com.bagnet.nettracer.ws.onlineclaims.DeleteFileDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                         .parse(r,
                type, options);
        }

        public static com.bagnet.nettracer.ws.onlineclaims.DeleteFileDocument parse(
            javax.xml.stream.XMLStreamReader sr)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.onlineclaims.DeleteFileDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                         .parse(sr,
                type, null);
        }

        public static com.bagnet.nettracer.ws.onlineclaims.DeleteFileDocument parse(
            javax.xml.stream.XMLStreamReader sr,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.onlineclaims.DeleteFileDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                         .parse(sr,
                type, options);
        }

        public static com.bagnet.nettracer.ws.onlineclaims.DeleteFileDocument parse(
            org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.onlineclaims.DeleteFileDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                         .parse(node,
                type, null);
        }

        public static com.bagnet.nettracer.ws.onlineclaims.DeleteFileDocument parse(
            org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException {
            return (com.bagnet.nettracer.ws.onlineclaims.DeleteFileDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                         .parse(node,
                type, options);
        }

        /** @deprecated {@link XMLInputStream} */
        public static com.bagnet.nettracer.ws.onlineclaims.DeleteFileDocument parse(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (com.bagnet.nettracer.ws.onlineclaims.DeleteFileDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                         .parse(xis,
                type, null);
        }

        /** @deprecated {@link XMLInputStream} */
        public static com.bagnet.nettracer.ws.onlineclaims.DeleteFileDocument parse(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return (com.bagnet.nettracer.ws.onlineclaims.DeleteFileDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                                                                                         .parse(xis,
                type, options);
        }

        /** @deprecated {@link XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                               .newValidatingXMLInputStream(xis,
                type, null);
        }

        /** @deprecated {@link XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(
            org.apache.xmlbeans.xml.stream.XMLInputStream xis,
            org.apache.xmlbeans.XmlOptions options)
            throws org.apache.xmlbeans.XmlException,
                org.apache.xmlbeans.xml.stream.XMLStreamException {
            return org.apache.xmlbeans.XmlBeans.getContextTypeLoader()
                                               .newValidatingXMLInputStream(xis,
                type, options);
        }
    }
}
