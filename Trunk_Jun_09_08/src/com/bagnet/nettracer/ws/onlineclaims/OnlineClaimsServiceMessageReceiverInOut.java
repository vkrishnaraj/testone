/**
 * OnlineClaimsServiceMessageReceiverInOut.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.3  Built on : Aug 10, 2007 (04:45:47 LKT)
 */
package com.bagnet.nettracer.ws.onlineclaims;


/**
 *  OnlineClaimsServiceMessageReceiverInOut message receiver
 */
public class OnlineClaimsServiceMessageReceiverInOut extends org.apache.axis2.receivers.AbstractInOutSyncMessageReceiver {
    public void invokeBusinessLogic(
        org.apache.axis2.context.MessageContext msgContext,
        org.apache.axis2.context.MessageContext newMsgContext)
        throws org.apache.axis2.AxisFault {
        try {
            // get the implementation class for the Web Service
            Object obj = getTheImplementationObject(msgContext);

            OnlineClaimsServiceSkeleton skel = (OnlineClaimsServiceSkeleton) obj;

            //Out Envelop
            org.apache.axiom.soap.SOAPEnvelope envelope = null;

            //Find the axisOperation that has been set by the Dispatch phase.
            org.apache.axis2.description.AxisOperation op = msgContext.getOperationContext()
                                                                      .getAxisOperation();

            if (op == null) {
                throw new org.apache.axis2.AxisFault(
                    "Operation is not located, if this is doclit style the SOAP-ACTION should specified via the SOAP Action to use the RawXMLProvider");
            }

            java.lang.String methodName;

            if ((op.getName() != null) &&
                    ((methodName = org.apache.axis2.util.JavaUtils.xmlNameToJava(
                            op.getName().getLocalPart())) != null)) {
                if ("saveClaim".equals(methodName)) {
                    com.bagnet.nettracer.ws.onlineclaims.SaveClaimResponseDocument saveClaimResponse1 =
                        null;
                    com.bagnet.nettracer.ws.onlineclaims.SaveClaimDocument wrappedParam =
                        (com.bagnet.nettracer.ws.onlineclaims.SaveClaimDocument) fromOM(msgContext.getEnvelope()
                                                                                                  .getBody()
                                                                                                  .getFirstElement(),
                            com.bagnet.nettracer.ws.onlineclaims.SaveClaimDocument.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    saveClaimResponse1 = skel.saveClaim(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            saveClaimResponse1, false);
                } else
                 if ("authAdminUser".equals(methodName)) {
                    com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserResponseDocument authAdminUserResponse3 =
                        null;
                    com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserDocument wrappedParam =
                        (com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserDocument) fromOM(msgContext.getEnvelope()
                                                                                                      .getBody()
                                                                                                      .getFirstElement(),
                            com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserDocument.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    authAdminUserResponse3 = skel.authAdminUser(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            authAdminUserResponse3, false);
                } else
                 if ("uploadFile".equals(methodName)) {
                    com.bagnet.nettracer.ws.onlineclaims.UploadFileResponseDocument uploadFileResponse5 =
                        null;
                    com.bagnet.nettracer.ws.onlineclaims.UploadFileDocument wrappedParam =
                        (com.bagnet.nettracer.ws.onlineclaims.UploadFileDocument) fromOM(msgContext.getEnvelope()
                                                                                                   .getBody()
                                                                                                   .getFirstElement(),
                            com.bagnet.nettracer.ws.onlineclaims.UploadFileDocument.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    uploadFileResponse5 = skel.uploadFile(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            uploadFileResponse5, false);
                } else
                 if ("authPassenger".equals(methodName)) {
                    com.bagnet.nettracer.ws.onlineclaims.AuthPassengerResponseDocument authPassengerResponse7 =
                        null;
                    com.bagnet.nettracer.ws.onlineclaims.AuthPassengerDocument wrappedParam =
                        (com.bagnet.nettracer.ws.onlineclaims.AuthPassengerDocument) fromOM(msgContext.getEnvelope()
                                                                                                      .getBody()
                                                                                                      .getFirstElement(),
                            com.bagnet.nettracer.ws.onlineclaims.AuthPassengerDocument.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    authPassengerResponse7 = skel.authPassenger(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            authPassengerResponse7, false);
                } else
                 if ("loadClaim".equals(methodName)) {
                    com.bagnet.nettracer.ws.onlineclaims.LoadClaimResponseDocument loadClaimResponse9 =
                        null;
                    com.bagnet.nettracer.ws.onlineclaims.LoadClaimDocument wrappedParam =
                        (com.bagnet.nettracer.ws.onlineclaims.LoadClaimDocument) fromOM(msgContext.getEnvelope()
                                                                                                  .getBody()
                                                                                                  .getFirstElement(),
                            com.bagnet.nettracer.ws.onlineclaims.LoadClaimDocument.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    loadClaimResponse9 = skel.loadClaim(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            loadClaimResponse9, false);
                } else
                 if ("deleteFile".equals(methodName)) {
                    com.bagnet.nettracer.ws.onlineclaims.DeleteFileResponseDocument deleteFileResponse11 =
                        null;
                    com.bagnet.nettracer.ws.onlineclaims.DeleteFileDocument wrappedParam =
                        (com.bagnet.nettracer.ws.onlineclaims.DeleteFileDocument) fromOM(msgContext.getEnvelope()
                                                                                                   .getBody()
                                                                                                   .getFirstElement(),
                            com.bagnet.nettracer.ws.onlineclaims.DeleteFileDocument.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    deleteFileResponse11 = skel.deleteFile(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            deleteFileResponse11, false);
                } else {
                    throw new java.lang.RuntimeException("method not found");
                }

                newMsgContext.setEnvelope(envelope);
            }
        } catch (java.lang.Exception e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    //
    private org.apache.axiom.om.OMElement toOM(
        com.bagnet.nettracer.ws.onlineclaims.UploadFileDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.om.OMElement toOM(
        com.bagnet.nettracer.ws.onlineclaims.UploadFileResponseDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.om.OMElement toOM(
        com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.om.OMElement toOM(
        com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserResponseDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.om.OMElement toOM(
        com.bagnet.nettracer.ws.onlineclaims.SaveClaimDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.om.OMElement toOM(
        com.bagnet.nettracer.ws.onlineclaims.SaveClaimResponseDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.om.OMElement toOM(
        com.bagnet.nettracer.ws.onlineclaims.DeleteFileDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.om.OMElement toOM(
        com.bagnet.nettracer.ws.onlineclaims.DeleteFileResponseDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.om.OMElement toOM(
        com.bagnet.nettracer.ws.onlineclaims.LoadClaimDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.om.OMElement toOM(
        com.bagnet.nettracer.ws.onlineclaims.LoadClaimResponseDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.om.OMElement toOM(
        com.bagnet.nettracer.ws.onlineclaims.AuthPassengerDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.om.OMElement toOM(
        com.bagnet.nettracer.ws.onlineclaims.AuthPassengerResponseDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        com.bagnet.nettracer.ws.onlineclaims.UploadFileResponseDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.soap.SOAPEnvelope envelope = factory.getDefaultEnvelope();

        if (param != null) {
            envelope.getBody().addChild(toOM(param, optimizeContent));
        }

        return envelope;
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserResponseDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.soap.SOAPEnvelope envelope = factory.getDefaultEnvelope();

        if (param != null) {
            envelope.getBody().addChild(toOM(param, optimizeContent));
        }

        return envelope;
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        com.bagnet.nettracer.ws.onlineclaims.SaveClaimResponseDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.soap.SOAPEnvelope envelope = factory.getDefaultEnvelope();

        if (param != null) {
            envelope.getBody().addChild(toOM(param, optimizeContent));
        }

        return envelope;
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        com.bagnet.nettracer.ws.onlineclaims.DeleteFileResponseDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.soap.SOAPEnvelope envelope = factory.getDefaultEnvelope();

        if (param != null) {
            envelope.getBody().addChild(toOM(param, optimizeContent));
        }

        return envelope;
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        com.bagnet.nettracer.ws.onlineclaims.LoadClaimResponseDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.soap.SOAPEnvelope envelope = factory.getDefaultEnvelope();

        if (param != null) {
            envelope.getBody().addChild(toOM(param, optimizeContent));
        }

        return envelope;
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        com.bagnet.nettracer.ws.onlineclaims.AuthPassengerResponseDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.soap.SOAPEnvelope envelope = factory.getDefaultEnvelope();

        if (param != null) {
            envelope.getBody().addChild(toOM(param, optimizeContent));
        }

        return envelope;
    }

    /**
     *  get the default envelope
     */
    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory) {
        return factory.getDefaultEnvelope();
    }

    public org.apache.xmlbeans.XmlObject fromOM(
        org.apache.axiom.om.OMElement param, java.lang.Class type,
        java.util.Map extraNamespaces) throws org.apache.axis2.AxisFault {
        try {
            if (com.bagnet.nettracer.ws.onlineclaims.UploadFileDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return com.bagnet.nettracer.ws.onlineclaims.UploadFileDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return com.bagnet.nettracer.ws.onlineclaims.UploadFileDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                }
            }

            if (com.bagnet.nettracer.ws.onlineclaims.UploadFileResponseDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return com.bagnet.nettracer.ws.onlineclaims.UploadFileResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return com.bagnet.nettracer.ws.onlineclaims.UploadFileResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                }
            }

            if (com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                }
            }

            if (com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserResponseDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return com.bagnet.nettracer.ws.onlineclaims.AuthAdminUserResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                }
            }

            if (com.bagnet.nettracer.ws.onlineclaims.SaveClaimDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return com.bagnet.nettracer.ws.onlineclaims.SaveClaimDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return com.bagnet.nettracer.ws.onlineclaims.SaveClaimDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                }
            }

            if (com.bagnet.nettracer.ws.onlineclaims.SaveClaimResponseDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return com.bagnet.nettracer.ws.onlineclaims.SaveClaimResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return com.bagnet.nettracer.ws.onlineclaims.SaveClaimResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                }
            }

            if (com.bagnet.nettracer.ws.onlineclaims.DeleteFileDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return com.bagnet.nettracer.ws.onlineclaims.DeleteFileDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return com.bagnet.nettracer.ws.onlineclaims.DeleteFileDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                }
            }

            if (com.bagnet.nettracer.ws.onlineclaims.DeleteFileResponseDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return com.bagnet.nettracer.ws.onlineclaims.DeleteFileResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return com.bagnet.nettracer.ws.onlineclaims.DeleteFileResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                }
            }

            if (com.bagnet.nettracer.ws.onlineclaims.LoadClaimDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return com.bagnet.nettracer.ws.onlineclaims.LoadClaimDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return com.bagnet.nettracer.ws.onlineclaims.LoadClaimDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                }
            }

            if (com.bagnet.nettracer.ws.onlineclaims.LoadClaimResponseDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return com.bagnet.nettracer.ws.onlineclaims.LoadClaimResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return com.bagnet.nettracer.ws.onlineclaims.LoadClaimResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                }
            }

            if (com.bagnet.nettracer.ws.onlineclaims.AuthPassengerDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return com.bagnet.nettracer.ws.onlineclaims.AuthPassengerDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return com.bagnet.nettracer.ws.onlineclaims.AuthPassengerDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                }
            }

            if (com.bagnet.nettracer.ws.onlineclaims.AuthPassengerResponseDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return com.bagnet.nettracer.ws.onlineclaims.AuthPassengerResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return com.bagnet.nettracer.ws.onlineclaims.AuthPassengerResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                }
            }
        } catch (java.lang.Exception e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }

        return null;
    }

    /**
     *  A utility method that copies the namepaces from the SOAPEnvelope
     */
    private java.util.Map getEnvelopeNamespaces(
        org.apache.axiom.soap.SOAPEnvelope env) {
        java.util.Map returnMap = new java.util.HashMap();
        java.util.Iterator namespaceIterator = env.getAllDeclaredNamespaces();

        while (namespaceIterator.hasNext()) {
            org.apache.axiom.om.OMNamespace ns = (org.apache.axiom.om.OMNamespace) namespaceIterator.next();
            returnMap.put(ns.getPrefix(), ns.getNamespaceURI());
        }

        return returnMap;
    }

    private org.apache.axis2.AxisFault createAxisFault(java.lang.Exception e) {
        org.apache.axis2.AxisFault f;
        Throwable cause = e.getCause();

        if (cause != null) {
            f = new org.apache.axis2.AxisFault(e.getMessage(), cause);
        } else {
            f = new org.apache.axis2.AxisFault(e.getMessage());
        }

        return f;
    }
} //end of class
