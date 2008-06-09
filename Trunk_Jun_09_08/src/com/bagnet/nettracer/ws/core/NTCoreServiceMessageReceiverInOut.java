/**
 * NTCoreServiceMessageReceiverInOut.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.3  Built on : Aug 10, 2007 (04:45:47 LKT)
 */
package com.bagnet.nettracer.ws.core;


/**
 *  NTCoreServiceMessageReceiverInOut message receiver
 */
public class NTCoreServiceMessageReceiverInOut extends org.apache.axis2.receivers.AbstractInOutSyncMessageReceiver {
    public void invokeBusinessLogic(
        org.apache.axis2.context.MessageContext msgContext,
        org.apache.axis2.context.MessageContext newMsgContext)
        throws org.apache.axis2.AxisFault {
        try {
            // get the implementation class for the Web Service
            Object obj = getTheImplementationObject(msgContext);

            NTCoreServiceSkeleton skel = (NTCoreServiceSkeleton) obj;

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
                if ("authenticate".equals(methodName)) {
                    com.bagnet.nettracer.ws.core.AuthenticateResponseDocument authenticateResponse1 =
                        null;
                    com.bagnet.nettracer.ws.core.AuthenticateDocument wrappedParam =
                        (com.bagnet.nettracer.ws.core.AuthenticateDocument) fromOM(msgContext.getEnvelope()
                                                                                             .getBody()
                                                                                             .getFirstElement(),
                            com.bagnet.nettracer.ws.core.AuthenticateDocument.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    authenticateResponse1 = skel.authenticate(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            authenticateResponse1, false);
                } else
                 if ("getOHDsForWT".equals(methodName)) {
                    com.bagnet.nettracer.ws.core.GetOHDsForWTResponseDocument getOHDsForWTResponse3 =
                        null;
                    com.bagnet.nettracer.ws.core.GetOHDsForWTDocument wrappedParam =
                        (com.bagnet.nettracer.ws.core.GetOHDsForWTDocument) fromOM(msgContext.getEnvelope()
                                                                                             .getBody()
                                                                                             .getFirstElement(),
                            com.bagnet.nettracer.ws.core.GetOHDsForWTDocument.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    getOHDsForWTResponse3 = skel.getOHDsForWT(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            getOHDsForWTResponse3, false);
                } else
                 if ("getOHD".equals(methodName)) {
                    com.bagnet.nettracer.ws.core.GetOHDResponseDocument getOHDResponse5 =
                        null;
                    com.bagnet.nettracer.ws.core.GetOHDDocument wrappedParam = (com.bagnet.nettracer.ws.core.GetOHDDocument) fromOM(msgContext.getEnvelope()
                                                                                                                                              .getBody()
                                                                                                                                              .getFirstElement(),
                            com.bagnet.nettracer.ws.core.GetOHDDocument.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    getOHDResponse5 = skel.getOHD(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            getOHDResponse5, false);
                } else
                 if ("insertOHD".equals(methodName)) {
                    com.bagnet.nettracer.ws.core.InsertOHDResponseDocument insertOHDResponse7 =
                        null;
                    com.bagnet.nettracer.ws.core.InsertOHDDocument wrappedParam = (com.bagnet.nettracer.ws.core.InsertOHDDocument) fromOM(msgContext.getEnvelope()
                                                                                                                                                    .getBody()
                                                                                                                                                    .getFirstElement(),
                            com.bagnet.nettracer.ws.core.InsertOHDDocument.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    insertOHDResponse7 = skel.insertOHD(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            insertOHDResponse7, false);
                } else
                 if ("getIncident".equals(methodName)) {
                    com.bagnet.nettracer.ws.core.GetIncidentResponseDocument getIncidentResponse9 =
                        null;
                    com.bagnet.nettracer.ws.core.GetIncidentDocument wrappedParam =
                        (com.bagnet.nettracer.ws.core.GetIncidentDocument) fromOM(msgContext.getEnvelope()
                                                                                            .getBody()
                                                                                            .getFirstElement(),
                            com.bagnet.nettracer.ws.core.GetIncidentDocument.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    getIncidentResponse9 = skel.getIncident(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            getIncidentResponse9, false);
                } else
                 if ("logoff".equals(methodName)) {
                    com.bagnet.nettracer.ws.core.LogoffResponseDocument logoffResponse11 =
                        null;
                    com.bagnet.nettracer.ws.core.LogoffDocument wrappedParam = (com.bagnet.nettracer.ws.core.LogoffDocument) fromOM(msgContext.getEnvelope()
                                                                                                                                              .getBody()
                                                                                                                                              .getFirstElement(),
                            com.bagnet.nettracer.ws.core.LogoffDocument.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    logoffResponse11 = skel.logoff(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            logoffResponse11, false);
                } else
                 if ("insertIncident".equals(methodName)) {
                    com.bagnet.nettracer.ws.core.InsertIncidentResponseDocument insertIncidentResponse13 =
                        null;
                    com.bagnet.nettracer.ws.core.InsertIncidentDocument wrappedParam =
                        (com.bagnet.nettracer.ws.core.InsertIncidentDocument) fromOM(msgContext.getEnvelope()
                                                                                               .getBody()
                                                                                               .getFirstElement(),
                            com.bagnet.nettracer.ws.core.InsertIncidentDocument.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    insertIncidentResponse13 = skel.insertIncident(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            insertIncidentResponse13, false);
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
        com.bagnet.nettracer.ws.core.AuthenticateDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.om.OMElement toOM(
        com.bagnet.nettracer.ws.core.AuthenticateResponseDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.om.OMElement toOM(
        com.bagnet.nettracer.ws.core.GetOHDDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.om.OMElement toOM(
        com.bagnet.nettracer.ws.core.GetOHDResponseDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.om.OMElement toOM(
        com.bagnet.nettracer.ws.core.GetOHDsForWTDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.om.OMElement toOM(
        com.bagnet.nettracer.ws.core.GetOHDsForWTResponseDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.om.OMElement toOM(
        com.bagnet.nettracer.ws.core.InsertOHDDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.om.OMElement toOM(
        com.bagnet.nettracer.ws.core.InsertOHDResponseDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.om.OMElement toOM(
        com.bagnet.nettracer.ws.core.GetIncidentDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.om.OMElement toOM(
        com.bagnet.nettracer.ws.core.GetIncidentResponseDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.om.OMElement toOM(
        com.bagnet.nettracer.ws.core.LogoffDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.om.OMElement toOM(
        com.bagnet.nettracer.ws.core.LogoffResponseDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.om.OMElement toOM(
        com.bagnet.nettracer.ws.core.InsertIncidentDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.om.OMElement toOM(
        com.bagnet.nettracer.ws.core.InsertIncidentResponseDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        com.bagnet.nettracer.ws.core.AuthenticateResponseDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.soap.SOAPEnvelope envelope = factory.getDefaultEnvelope();

        if (param != null) {
            envelope.getBody().addChild(toOM(param, optimizeContent));
        }

        return envelope;
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        com.bagnet.nettracer.ws.core.GetOHDResponseDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.soap.SOAPEnvelope envelope = factory.getDefaultEnvelope();

        if (param != null) {
            envelope.getBody().addChild(toOM(param, optimizeContent));
        }

        return envelope;
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        com.bagnet.nettracer.ws.core.GetOHDsForWTResponseDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.soap.SOAPEnvelope envelope = factory.getDefaultEnvelope();

        if (param != null) {
            envelope.getBody().addChild(toOM(param, optimizeContent));
        }

        return envelope;
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        com.bagnet.nettracer.ws.core.InsertOHDResponseDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.soap.SOAPEnvelope envelope = factory.getDefaultEnvelope();

        if (param != null) {
            envelope.getBody().addChild(toOM(param, optimizeContent));
        }

        return envelope;
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        com.bagnet.nettracer.ws.core.GetIncidentResponseDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.soap.SOAPEnvelope envelope = factory.getDefaultEnvelope();

        if (param != null) {
            envelope.getBody().addChild(toOM(param, optimizeContent));
        }

        return envelope;
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        com.bagnet.nettracer.ws.core.LogoffResponseDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.soap.SOAPEnvelope envelope = factory.getDefaultEnvelope();

        if (param != null) {
            envelope.getBody().addChild(toOM(param, optimizeContent));
        }

        return envelope;
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        com.bagnet.nettracer.ws.core.InsertIncidentResponseDocument param,
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
            if (com.bagnet.nettracer.ws.core.AuthenticateDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return com.bagnet.nettracer.ws.core.AuthenticateDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return com.bagnet.nettracer.ws.core.AuthenticateDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                }
            }

            if (com.bagnet.nettracer.ws.core.AuthenticateResponseDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return com.bagnet.nettracer.ws.core.AuthenticateResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return com.bagnet.nettracer.ws.core.AuthenticateResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                }
            }

            if (com.bagnet.nettracer.ws.core.GetOHDDocument.class.equals(type)) {
                if (extraNamespaces != null) {
                    return com.bagnet.nettracer.ws.core.GetOHDDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return com.bagnet.nettracer.ws.core.GetOHDDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                }
            }

            if (com.bagnet.nettracer.ws.core.GetOHDResponseDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return com.bagnet.nettracer.ws.core.GetOHDResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return com.bagnet.nettracer.ws.core.GetOHDResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                }
            }

            if (com.bagnet.nettracer.ws.core.GetOHDsForWTDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return com.bagnet.nettracer.ws.core.GetOHDsForWTDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return com.bagnet.nettracer.ws.core.GetOHDsForWTDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                }
            }

            if (com.bagnet.nettracer.ws.core.GetOHDsForWTResponseDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return com.bagnet.nettracer.ws.core.GetOHDsForWTResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return com.bagnet.nettracer.ws.core.GetOHDsForWTResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                }
            }

            if (com.bagnet.nettracer.ws.core.InsertOHDDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return com.bagnet.nettracer.ws.core.InsertOHDDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return com.bagnet.nettracer.ws.core.InsertOHDDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                }
            }

            if (com.bagnet.nettracer.ws.core.InsertOHDResponseDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return com.bagnet.nettracer.ws.core.InsertOHDResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return com.bagnet.nettracer.ws.core.InsertOHDResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                }
            }

            if (com.bagnet.nettracer.ws.core.GetIncidentDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return com.bagnet.nettracer.ws.core.GetIncidentDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return com.bagnet.nettracer.ws.core.GetIncidentDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                }
            }

            if (com.bagnet.nettracer.ws.core.GetIncidentResponseDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return com.bagnet.nettracer.ws.core.GetIncidentResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return com.bagnet.nettracer.ws.core.GetIncidentResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                }
            }

            if (com.bagnet.nettracer.ws.core.LogoffDocument.class.equals(type)) {
                if (extraNamespaces != null) {
                    return com.bagnet.nettracer.ws.core.LogoffDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return com.bagnet.nettracer.ws.core.LogoffDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                }
            }

            if (com.bagnet.nettracer.ws.core.LogoffResponseDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return com.bagnet.nettracer.ws.core.LogoffResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return com.bagnet.nettracer.ws.core.LogoffResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                }
            }

            if (com.bagnet.nettracer.ws.core.InsertIncidentDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return com.bagnet.nettracer.ws.core.InsertIncidentDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return com.bagnet.nettracer.ws.core.InsertIncidentDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                }
            }

            if (com.bagnet.nettracer.ws.core.InsertIncidentResponseDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return com.bagnet.nettracer.ws.core.InsertIncidentResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return com.bagnet.nettracer.ws.core.InsertIncidentResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
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
