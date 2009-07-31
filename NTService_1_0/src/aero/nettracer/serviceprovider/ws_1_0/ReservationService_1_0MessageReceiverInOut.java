/**
 * ReservationService_1_0MessageReceiverInOut.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.3  Built on : Aug 10, 2007 (04:45:47 LKT)
 */
package aero.nettracer.serviceprovider.ws_1_0;


/**
 *  ReservationService_1_0MessageReceiverInOut message receiver
 */
public class ReservationService_1_0MessageReceiverInOut extends org.apache.axis2.receivers.AbstractInOutSyncMessageReceiver {
    public void invokeBusinessLogic(
        org.apache.axis2.context.MessageContext msgContext,
        org.apache.axis2.context.MessageContext newMsgContext)
        throws org.apache.axis2.AxisFault {
        try {
            // get the implementation class for the Web Service
            Object obj = getTheImplementationObject(msgContext);

            ReservationService_1_0Skeleton skel = (ReservationService_1_0Skeleton) obj;

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
                if ("getEnplanements".equals(methodName)) {
                    aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsResponseDocument getEnplanementsResponse1 =
                        null;
                    aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsDocument wrappedParam =
                        (aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsDocument) fromOM(msgContext.getEnvelope()
                                                                                                         .getBody()
                                                                                                         .getFirstElement(),
                            aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsDocument.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    getEnplanementsResponse1 = skel.getEnplanements(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            getEnplanementsResponse1, false);
                } else
                 if ("writeRemark".equals(methodName)) {
                    aero.nettracer.serviceprovider.ws_1_0.WriteRemarkResponseDocument writeRemarkResponse3 =
                        null;
                    aero.nettracer.serviceprovider.ws_1_0.WriteRemarkDocument wrappedParam =
                        (aero.nettracer.serviceprovider.ws_1_0.WriteRemarkDocument) fromOM(msgContext.getEnvelope()
                                                                                                     .getBody()
                                                                                                     .getFirstElement(),
                            aero.nettracer.serviceprovider.ws_1_0.WriteRemarkDocument.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    writeRemarkResponse3 = skel.writeRemark(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            writeRemarkResponse3, false);
                } else
                 if ("getReservationData".equals(methodName)) {
                    aero.nettracer.serviceprovider.ws_1_0.GetReservationDataResponseDocument getReservationDataResponse5 =
                        null;
                    aero.nettracer.serviceprovider.ws_1_0.GetReservationDataDocument wrappedParam =
                        (aero.nettracer.serviceprovider.ws_1_0.GetReservationDataDocument) fromOM(msgContext.getEnvelope()
                                                                                                            .getBody()
                                                                                                            .getFirstElement(),
                            aero.nettracer.serviceprovider.ws_1_0.GetReservationDataDocument.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    getReservationDataResponse5 = skel.getReservationData(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            getReservationDataResponse5, false);
                } else
                 if ("getOsiContents".equals(methodName)) {
                    aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsResponseDocument getOsiContentsResponse7 =
                        null;
                    aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsDocument wrappedParam =
                        (aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsDocument) fromOM(msgContext.getEnvelope()
                                                                                                        .getBody()
                                                                                                        .getFirstElement(),
                            aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsDocument.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    getOsiContentsResponse7 = skel.getOsiContents(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            getOsiContentsResponse7, false);
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
        aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.om.OMElement toOM(
        aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsResponseDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.om.OMElement toOM(
        aero.nettracer.serviceprovider.ws_1_0.GetReservationDataDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.om.OMElement toOM(
        aero.nettracer.serviceprovider.ws_1_0.GetReservationDataResponseDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.om.OMElement toOM(
        aero.nettracer.serviceprovider.ws_1_0.WriteRemarkDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.om.OMElement toOM(
        aero.nettracer.serviceprovider.ws_1_0.WriteRemarkResponseDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.om.OMElement toOM(
        aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.om.OMElement toOM(
        aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsResponseDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsResponseDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.soap.SOAPEnvelope envelope = factory.getDefaultEnvelope();

        if (param != null) {
            envelope.getBody().addChild(toOM(param, optimizeContent));
        }

        return envelope;
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        aero.nettracer.serviceprovider.ws_1_0.GetReservationDataResponseDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.soap.SOAPEnvelope envelope = factory.getDefaultEnvelope();

        if (param != null) {
            envelope.getBody().addChild(toOM(param, optimizeContent));
        }

        return envelope;
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        aero.nettracer.serviceprovider.ws_1_0.WriteRemarkResponseDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.soap.SOAPEnvelope envelope = factory.getDefaultEnvelope();

        if (param != null) {
            envelope.getBody().addChild(toOM(param, optimizeContent));
        }

        return envelope;
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsResponseDocument param,
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
            if (aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                }
            }

            if (aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsResponseDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return aero.nettracer.serviceprovider.ws_1_0.GetOsiContentsResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                }
            }

            if (aero.nettracer.serviceprovider.ws_1_0.GetReservationDataDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return aero.nettracer.serviceprovider.ws_1_0.GetReservationDataDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return aero.nettracer.serviceprovider.ws_1_0.GetReservationDataDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                }
            }

            if (aero.nettracer.serviceprovider.ws_1_0.GetReservationDataResponseDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return aero.nettracer.serviceprovider.ws_1_0.GetReservationDataResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return aero.nettracer.serviceprovider.ws_1_0.GetReservationDataResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                }
            }

            if (aero.nettracer.serviceprovider.ws_1_0.WriteRemarkDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return aero.nettracer.serviceprovider.ws_1_0.WriteRemarkDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return aero.nettracer.serviceprovider.ws_1_0.WriteRemarkDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                }
            }

            if (aero.nettracer.serviceprovider.ws_1_0.WriteRemarkResponseDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return aero.nettracer.serviceprovider.ws_1_0.WriteRemarkResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return aero.nettracer.serviceprovider.ws_1_0.WriteRemarkResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                }
            }

            if (aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                }
            }

            if (aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsResponseDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return aero.nettracer.serviceprovider.ws_1_0.GetEnplanementsResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
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
