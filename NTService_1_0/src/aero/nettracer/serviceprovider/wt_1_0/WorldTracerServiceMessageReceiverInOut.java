/**
 * WorldTracerServiceMessageReceiverInOut.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.3  Built on : Aug 10, 2007 (04:45:47 LKT)
 */
package aero.nettracer.serviceprovider.wt_1_0;


/**
 *  WorldTracerServiceMessageReceiverInOut message receiver
 */
public class WorldTracerServiceMessageReceiverInOut extends org.apache.axis2.receivers.AbstractInOutSyncMessageReceiver {
    public void invokeBusinessLogic(
        org.apache.axis2.context.MessageContext msgContext,
        org.apache.axis2.context.MessageContext newMsgContext)
        throws org.apache.axis2.AxisFault {
        try {
            // get the implementation class for the Web Service
            Object obj = getTheImplementationObject(msgContext);

            WorldTracerServiceSkeleton skel = (WorldTracerServiceSkeleton) obj;

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
                if ("closeOhd".equals(methodName)) {
                    aero.nettracer.serviceprovider.wt_1_0.CloseOhdResponseDocument closeOhdResponse1 =
                        null;
                    aero.nettracer.serviceprovider.wt_1_0.CloseOhdDocument wrappedParam =
                        (aero.nettracer.serviceprovider.wt_1_0.CloseOhdDocument) fromOM(msgContext.getEnvelope()
                                                                                                  .getBody()
                                                                                                  .getFirstElement(),
                            aero.nettracer.serviceprovider.wt_1_0.CloseOhdDocument.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    closeOhdResponse1 = skel.closeOhd(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            closeOhdResponse1, false);
                } else if ("forwardOhd".equals(methodName)) {
                    aero.nettracer.serviceprovider.wt_1_0.ForwardOhdResponseDocument forwardOhdResponse3 =
                        null;
                    aero.nettracer.serviceprovider.wt_1_0.ForwardOhdDocument wrappedParam =
                        (aero.nettracer.serviceprovider.wt_1_0.ForwardOhdDocument) fromOM(msgContext.getEnvelope()
                                                                                                    .getBody()
                                                                                                    .getFirstElement(),
                            aero.nettracer.serviceprovider.wt_1_0.ForwardOhdDocument.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    forwardOhdResponse3 = skel.forwardOhd(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            forwardOhdResponse3, false);
                } else if ("getAhl".equals(methodName)) {
                    aero.nettracer.serviceprovider.wt_1_0.GetAhlResponseDocument getAhlResponse5 =
                        null;
                    aero.nettracer.serviceprovider.wt_1_0.GetAhlDocument wrappedParam =
                        (aero.nettracer.serviceprovider.wt_1_0.GetAhlDocument) fromOM(msgContext.getEnvelope()
                                                                                                .getBody()
                                                                                                .getFirstElement(),
                            aero.nettracer.serviceprovider.wt_1_0.GetAhlDocument.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    getAhlResponse5 = skel.getAhl(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            getAhlResponse5, false);
                } else if ("createAhl".equals(methodName)) {
                    aero.nettracer.serviceprovider.wt_1_0.CreateAhlResponseDocument createAhlResponse7 =
                        null;
                    aero.nettracer.serviceprovider.wt_1_0.CreateAhlDocument wrappedParam =
                        (aero.nettracer.serviceprovider.wt_1_0.CreateAhlDocument) fromOM(msgContext.getEnvelope()
                                                                                                   .getBody()
                                                                                                   .getFirstElement(),
                            aero.nettracer.serviceprovider.wt_1_0.CreateAhlDocument.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    createAhlResponse7 = skel.createAhl(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            createAhlResponse7, false);
                } else if ("eraseActionFile".equals(methodName)) {
                    aero.nettracer.serviceprovider.wt_1_0.EraseActionFileResponseDocument eraseActionFileResponse9 =
                        null;
                    aero.nettracer.serviceprovider.wt_1_0.EraseActionFileDocument wrappedParam =
                        (aero.nettracer.serviceprovider.wt_1_0.EraseActionFileDocument) fromOM(msgContext.getEnvelope()
                                                                                                         .getBody()
                                                                                                         .getFirstElement(),
                            aero.nettracer.serviceprovider.wt_1_0.EraseActionFileDocument.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    eraseActionFileResponse9 = skel.eraseActionFile(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            eraseActionFileResponse9, false);
                } else if ("amendOhd".equals(methodName)) {
                    aero.nettracer.serviceprovider.wt_1_0.AmendOhdResponseDocument amendOhdResponse11 =
                        null;
                    aero.nettracer.serviceprovider.wt_1_0.AmendOhdDocument wrappedParam =
                        (aero.nettracer.serviceprovider.wt_1_0.AmendOhdDocument) fromOM(msgContext.getEnvelope()
                                                                                                  .getBody()
                                                                                                  .getFirstElement(),
                            aero.nettracer.serviceprovider.wt_1_0.AmendOhdDocument.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    amendOhdResponse11 = skel.amendOhd(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            amendOhdResponse11, false);
                } else if ("getActionFileSummary".equals(methodName)) {
                    aero.nettracer.serviceprovider.wt_1_0.GetActionFileSummaryResponseDocument getActionFileSummaryResponse13 =
                        null;
                    aero.nettracer.serviceprovider.wt_1_0.GetActionFileSummaryDocument wrappedParam =
                        (aero.nettracer.serviceprovider.wt_1_0.GetActionFileSummaryDocument) fromOM(msgContext.getEnvelope()
                                                                                                              .getBody()
                                                                                                              .getFirstElement(),
                            aero.nettracer.serviceprovider.wt_1_0.GetActionFileSummaryDocument.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    getActionFileSummaryResponse13 = skel.getActionFileSummary(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            getActionFileSummaryResponse13, false);
                } else if ("getOhd".equals(methodName)) {
                    aero.nettracer.serviceprovider.wt_1_0.GetOhdResponseDocument getOhdResponse15 =
                        null;
                    aero.nettracer.serviceprovider.wt_1_0.GetOhdDocument wrappedParam =
                        (aero.nettracer.serviceprovider.wt_1_0.GetOhdDocument) fromOM(msgContext.getEnvelope()
                                                                                                .getBody()
                                                                                                .getFirstElement(),
                            aero.nettracer.serviceprovider.wt_1_0.GetOhdDocument.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    getOhdResponse15 = skel.getOhd(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            getOhdResponse15, false);
                } else if ("getActionFileCounts".equals(methodName)) {
                    aero.nettracer.serviceprovider.wt_1_0.GetActionFileCountsResponseDocument getActionFileCountsResponse17 =
                        null;
                    aero.nettracer.serviceprovider.wt_1_0.GetActionFileCountsDocument wrappedParam =
                        (aero.nettracer.serviceprovider.wt_1_0.GetActionFileCountsDocument) fromOM(msgContext.getEnvelope()
                                                                                                             .getBody()
                                                                                                             .getFirstElement(),
                            aero.nettracer.serviceprovider.wt_1_0.GetActionFileCountsDocument.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    getActionFileCountsResponse17 = skel.getActionFileCounts(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            getActionFileCountsResponse17, false);
                } else if ("requestOhd".equals(methodName)) {
                    aero.nettracer.serviceprovider.wt_1_0.RequestOhdResponseDocument requestOhdResponse19 =
                        null;
                    aero.nettracer.serviceprovider.wt_1_0.RequestOhdDocument wrappedParam =
                        (aero.nettracer.serviceprovider.wt_1_0.RequestOhdDocument) fromOM(msgContext.getEnvelope()
                                                                                                    .getBody()
                                                                                                    .getFirstElement(),
                            aero.nettracer.serviceprovider.wt_1_0.RequestOhdDocument.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    requestOhdResponse19 = skel.requestOhd(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            requestOhdResponse19, false);
                } else if ("amendAhl".equals(methodName)) {
                    aero.nettracer.serviceprovider.wt_1_0.AmendAhlResponseDocument amendAhlResponse21 =
                        null;
                    aero.nettracer.serviceprovider.wt_1_0.AmendAhlDocument wrappedParam =
                        (aero.nettracer.serviceprovider.wt_1_0.AmendAhlDocument) fromOM(msgContext.getEnvelope()
                                                                                                  .getBody()
                                                                                                  .getFirstElement(),
                            aero.nettracer.serviceprovider.wt_1_0.AmendAhlDocument.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    amendAhlResponse21 = skel.amendAhl(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            amendAhlResponse21, false);
                } else if ("createBdo".equals(methodName)) {
                    aero.nettracer.serviceprovider.wt_1_0.CreateBdoResponseDocument createBdoResponse23 =
                        null;
                    aero.nettracer.serviceprovider.wt_1_0.CreateBdoDocument wrappedParam =
                        (aero.nettracer.serviceprovider.wt_1_0.CreateBdoDocument) fromOM(msgContext.getEnvelope()
                                                                                                   .getBody()
                                                                                                   .getFirstElement(),
                            aero.nettracer.serviceprovider.wt_1_0.CreateBdoDocument.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    createBdoResponse23 = skel.createBdo(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            createBdoResponse23, false);
                } else if ("requestQuickOhd".equals(methodName)) {
                    aero.nettracer.serviceprovider.wt_1_0.RequestQuickOhdResponseDocument requestQuickOhdResponse25 =
                        null;
                    aero.nettracer.serviceprovider.wt_1_0.RequestQuickOhdDocument wrappedParam =
                        (aero.nettracer.serviceprovider.wt_1_0.RequestQuickOhdDocument) fromOM(msgContext.getEnvelope()
                                                                                                         .getBody()
                                                                                                         .getFirstElement(),
                            aero.nettracer.serviceprovider.wt_1_0.RequestQuickOhdDocument.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    requestQuickOhdResponse25 = skel.requestQuickOhd(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            requestQuickOhdResponse25, false);
                } else if ("getActionFileDetails".equals(methodName)) {
                    aero.nettracer.serviceprovider.wt_1_0.GetActionFileDetailsResponseDocument getActionFileDetailsResponse27 =
                        null;
                    aero.nettracer.serviceprovider.wt_1_0.GetActionFileDetailsDocument wrappedParam =
                        (aero.nettracer.serviceprovider.wt_1_0.GetActionFileDetailsDocument) fromOM(msgContext.getEnvelope()
                                                                                                              .getBody()
                                                                                                              .getFirstElement(),
                            aero.nettracer.serviceprovider.wt_1_0.GetActionFileDetailsDocument.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    getActionFileDetailsResponse27 = skel.getActionFileDetails(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            getActionFileDetailsResponse27, false);
                } else if ("sendForwardMessage".equals(methodName)) {
                    aero.nettracer.serviceprovider.wt_1_0.SendForwardMessageResponseDocument sendForwardMessageResponse29 =
                        null;
                    aero.nettracer.serviceprovider.wt_1_0.SendForwardMessageDocument wrappedParam =
                        (aero.nettracer.serviceprovider.wt_1_0.SendForwardMessageDocument) fromOM(msgContext.getEnvelope()
                                                                                                            .getBody()
                                                                                                            .getFirstElement(),
                            aero.nettracer.serviceprovider.wt_1_0.SendForwardMessageDocument.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    sendForwardMessageResponse29 = skel.sendForwardMessage(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            sendForwardMessageResponse29, false);
                } else if ("placeActionFile".equals(methodName)) {
                    aero.nettracer.serviceprovider.wt_1_0.PlaceActionFileResponseDocument placeActionFileResponse31 =
                        null;
                    aero.nettracer.serviceprovider.wt_1_0.PlaceActionFileDocument wrappedParam =
                        (aero.nettracer.serviceprovider.wt_1_0.PlaceActionFileDocument) fromOM(msgContext.getEnvelope()
                                                                                                         .getBody()
                                                                                                         .getFirstElement(),
                            aero.nettracer.serviceprovider.wt_1_0.PlaceActionFileDocument.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    placeActionFileResponse31 = skel.placeActionFile(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            placeActionFileResponse31, false);
                } else if ("createOhd".equals(methodName)) {
                    aero.nettracer.serviceprovider.wt_1_0.CreateOhdResponseDocument createOhdResponse33 =
                        null;
                    aero.nettracer.serviceprovider.wt_1_0.CreateOhdDocument wrappedParam =
                        (aero.nettracer.serviceprovider.wt_1_0.CreateOhdDocument) fromOM(msgContext.getEnvelope()
                                                                                                   .getBody()
                                                                                                   .getFirstElement(),
                            aero.nettracer.serviceprovider.wt_1_0.CreateOhdDocument.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    createOhdResponse33 = skel.createOhd(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            createOhdResponse33, false);
                } else if ("reinstanteAhl".equals(methodName)) {
                    aero.nettracer.serviceprovider.wt_1_0.ReinstanteAhlResponseDocument reinstanteAhlResponse35 =
                        null;
                    aero.nettracer.serviceprovider.wt_1_0.ReinstanteAhlDocument wrappedParam =
                        (aero.nettracer.serviceprovider.wt_1_0.ReinstanteAhlDocument) fromOM(msgContext.getEnvelope()
                                                                                                       .getBody()
                                                                                                       .getFirstElement(),
                            aero.nettracer.serviceprovider.wt_1_0.ReinstanteAhlDocument.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    reinstanteAhlResponse35 = skel.reinstanteAhl(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            reinstanteAhlResponse35, false);
                } else if ("closeAhl".equals(methodName)) {
                    aero.nettracer.serviceprovider.wt_1_0.CloseAhlResponseDocument closeAhlResponse37 =
                        null;
                    aero.nettracer.serviceprovider.wt_1_0.CloseAhlDocument wrappedParam =
                        (aero.nettracer.serviceprovider.wt_1_0.CloseAhlDocument) fromOM(msgContext.getEnvelope()
                                                                                                  .getBody()
                                                                                                  .getFirstElement(),
                            aero.nettracer.serviceprovider.wt_1_0.CloseAhlDocument.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    closeAhlResponse37 = skel.closeAhl(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            closeAhlResponse37, false);
                } else if ("suspendAhl".equals(methodName)) {
                    aero.nettracer.serviceprovider.wt_1_0.SuspendAhlResponseDocument suspendAhlResponse39 =
                        null;
                    aero.nettracer.serviceprovider.wt_1_0.SuspendAhlDocument wrappedParam =
                        (aero.nettracer.serviceprovider.wt_1_0.SuspendAhlDocument) fromOM(msgContext.getEnvelope()
                                                                                                    .getBody()
                                                                                                    .getFirstElement(),
                            aero.nettracer.serviceprovider.wt_1_0.SuspendAhlDocument.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    suspendAhlResponse39 = skel.suspendAhl(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            suspendAhlResponse39, false);
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
        aero.nettracer.serviceprovider.wt_1_0.PlaceActionFileDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.om.OMElement toOM(
        aero.nettracer.serviceprovider.wt_1_0.PlaceActionFileResponseDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.om.OMElement toOM(
        aero.nettracer.serviceprovider.wt_1_0.SendForwardMessageDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.om.OMElement toOM(
        aero.nettracer.serviceprovider.wt_1_0.SendForwardMessageResponseDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.om.OMElement toOM(
        aero.nettracer.serviceprovider.wt_1_0.GetActionFileDetailsDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.om.OMElement toOM(
        aero.nettracer.serviceprovider.wt_1_0.GetActionFileDetailsResponseDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.om.OMElement toOM(
        aero.nettracer.serviceprovider.wt_1_0.CreateBdoDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.om.OMElement toOM(
        aero.nettracer.serviceprovider.wt_1_0.CreateBdoResponseDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.om.OMElement toOM(
        aero.nettracer.serviceprovider.wt_1_0.RequestQuickOhdDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.om.OMElement toOM(
        aero.nettracer.serviceprovider.wt_1_0.RequestQuickOhdResponseDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.om.OMElement toOM(
        aero.nettracer.serviceprovider.wt_1_0.CloseAhlDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.om.OMElement toOM(
        aero.nettracer.serviceprovider.wt_1_0.CloseAhlResponseDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.om.OMElement toOM(
        aero.nettracer.serviceprovider.wt_1_0.ReinstanteAhlDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.om.OMElement toOM(
        aero.nettracer.serviceprovider.wt_1_0.ReinstanteAhlResponseDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.om.OMElement toOM(
        aero.nettracer.serviceprovider.wt_1_0.SuspendAhlDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.om.OMElement toOM(
        aero.nettracer.serviceprovider.wt_1_0.SuspendAhlResponseDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.om.OMElement toOM(
        aero.nettracer.serviceprovider.wt_1_0.CreateOhdDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.om.OMElement toOM(
        aero.nettracer.serviceprovider.wt_1_0.CreateOhdResponseDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.om.OMElement toOM(
        aero.nettracer.serviceprovider.wt_1_0.AmendOhdDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.om.OMElement toOM(
        aero.nettracer.serviceprovider.wt_1_0.AmendOhdResponseDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.om.OMElement toOM(
        aero.nettracer.serviceprovider.wt_1_0.GetActionFileSummaryDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.om.OMElement toOM(
        aero.nettracer.serviceprovider.wt_1_0.GetActionFileSummaryResponseDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.om.OMElement toOM(
        aero.nettracer.serviceprovider.wt_1_0.CloseOhdDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.om.OMElement toOM(
        aero.nettracer.serviceprovider.wt_1_0.CloseOhdResponseDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.om.OMElement toOM(
        aero.nettracer.serviceprovider.wt_1_0.CreateAhlDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.om.OMElement toOM(
        aero.nettracer.serviceprovider.wt_1_0.CreateAhlResponseDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.om.OMElement toOM(
        aero.nettracer.serviceprovider.wt_1_0.EraseActionFileDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.om.OMElement toOM(
        aero.nettracer.serviceprovider.wt_1_0.EraseActionFileResponseDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.om.OMElement toOM(
        aero.nettracer.serviceprovider.wt_1_0.ForwardOhdDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.om.OMElement toOM(
        aero.nettracer.serviceprovider.wt_1_0.ForwardOhdResponseDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.om.OMElement toOM(
        aero.nettracer.serviceprovider.wt_1_0.GetAhlDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.om.OMElement toOM(
        aero.nettracer.serviceprovider.wt_1_0.GetAhlResponseDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.om.OMElement toOM(
        aero.nettracer.serviceprovider.wt_1_0.RequestOhdDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.om.OMElement toOM(
        aero.nettracer.serviceprovider.wt_1_0.RequestOhdResponseDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.om.OMElement toOM(
        aero.nettracer.serviceprovider.wt_1_0.AmendAhlDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.om.OMElement toOM(
        aero.nettracer.serviceprovider.wt_1_0.AmendAhlResponseDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.om.OMElement toOM(
        aero.nettracer.serviceprovider.wt_1_0.GetActionFileCountsDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.om.OMElement toOM(
        aero.nettracer.serviceprovider.wt_1_0.GetActionFileCountsResponseDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.om.OMElement toOM(
        aero.nettracer.serviceprovider.wt_1_0.GetOhdDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.om.OMElement toOM(
        aero.nettracer.serviceprovider.wt_1_0.GetOhdResponseDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        aero.nettracer.serviceprovider.wt_1_0.PlaceActionFileResponseDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.soap.SOAPEnvelope envelope = factory.getDefaultEnvelope();

        if (param != null) {
            envelope.getBody().addChild(toOM(param, optimizeContent));
        }

        return envelope;
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        aero.nettracer.serviceprovider.wt_1_0.SendForwardMessageResponseDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.soap.SOAPEnvelope envelope = factory.getDefaultEnvelope();

        if (param != null) {
            envelope.getBody().addChild(toOM(param, optimizeContent));
        }

        return envelope;
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        aero.nettracer.serviceprovider.wt_1_0.GetActionFileDetailsResponseDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.soap.SOAPEnvelope envelope = factory.getDefaultEnvelope();

        if (param != null) {
            envelope.getBody().addChild(toOM(param, optimizeContent));
        }

        return envelope;
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        aero.nettracer.serviceprovider.wt_1_0.CreateBdoResponseDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.soap.SOAPEnvelope envelope = factory.getDefaultEnvelope();

        if (param != null) {
            envelope.getBody().addChild(toOM(param, optimizeContent));
        }

        return envelope;
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        aero.nettracer.serviceprovider.wt_1_0.RequestQuickOhdResponseDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.soap.SOAPEnvelope envelope = factory.getDefaultEnvelope();

        if (param != null) {
            envelope.getBody().addChild(toOM(param, optimizeContent));
        }

        return envelope;
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        aero.nettracer.serviceprovider.wt_1_0.CloseAhlResponseDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.soap.SOAPEnvelope envelope = factory.getDefaultEnvelope();

        if (param != null) {
            envelope.getBody().addChild(toOM(param, optimizeContent));
        }

        return envelope;
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        aero.nettracer.serviceprovider.wt_1_0.ReinstanteAhlResponseDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.soap.SOAPEnvelope envelope = factory.getDefaultEnvelope();

        if (param != null) {
            envelope.getBody().addChild(toOM(param, optimizeContent));
        }

        return envelope;
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        aero.nettracer.serviceprovider.wt_1_0.SuspendAhlResponseDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.soap.SOAPEnvelope envelope = factory.getDefaultEnvelope();

        if (param != null) {
            envelope.getBody().addChild(toOM(param, optimizeContent));
        }

        return envelope;
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        aero.nettracer.serviceprovider.wt_1_0.CreateOhdResponseDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.soap.SOAPEnvelope envelope = factory.getDefaultEnvelope();

        if (param != null) {
            envelope.getBody().addChild(toOM(param, optimizeContent));
        }

        return envelope;
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        aero.nettracer.serviceprovider.wt_1_0.AmendOhdResponseDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.soap.SOAPEnvelope envelope = factory.getDefaultEnvelope();

        if (param != null) {
            envelope.getBody().addChild(toOM(param, optimizeContent));
        }

        return envelope;
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        aero.nettracer.serviceprovider.wt_1_0.GetActionFileSummaryResponseDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.soap.SOAPEnvelope envelope = factory.getDefaultEnvelope();

        if (param != null) {
            envelope.getBody().addChild(toOM(param, optimizeContent));
        }

        return envelope;
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        aero.nettracer.serviceprovider.wt_1_0.CloseOhdResponseDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.soap.SOAPEnvelope envelope = factory.getDefaultEnvelope();

        if (param != null) {
            envelope.getBody().addChild(toOM(param, optimizeContent));
        }

        return envelope;
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        aero.nettracer.serviceprovider.wt_1_0.CreateAhlResponseDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.soap.SOAPEnvelope envelope = factory.getDefaultEnvelope();

        if (param != null) {
            envelope.getBody().addChild(toOM(param, optimizeContent));
        }

        return envelope;
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        aero.nettracer.serviceprovider.wt_1_0.EraseActionFileResponseDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.soap.SOAPEnvelope envelope = factory.getDefaultEnvelope();

        if (param != null) {
            envelope.getBody().addChild(toOM(param, optimizeContent));
        }

        return envelope;
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        aero.nettracer.serviceprovider.wt_1_0.ForwardOhdResponseDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.soap.SOAPEnvelope envelope = factory.getDefaultEnvelope();

        if (param != null) {
            envelope.getBody().addChild(toOM(param, optimizeContent));
        }

        return envelope;
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        aero.nettracer.serviceprovider.wt_1_0.GetAhlResponseDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.soap.SOAPEnvelope envelope = factory.getDefaultEnvelope();

        if (param != null) {
            envelope.getBody().addChild(toOM(param, optimizeContent));
        }

        return envelope;
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        aero.nettracer.serviceprovider.wt_1_0.RequestOhdResponseDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.soap.SOAPEnvelope envelope = factory.getDefaultEnvelope();

        if (param != null) {
            envelope.getBody().addChild(toOM(param, optimizeContent));
        }

        return envelope;
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        aero.nettracer.serviceprovider.wt_1_0.AmendAhlResponseDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.soap.SOAPEnvelope envelope = factory.getDefaultEnvelope();

        if (param != null) {
            envelope.getBody().addChild(toOM(param, optimizeContent));
        }

        return envelope;
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        aero.nettracer.serviceprovider.wt_1_0.GetActionFileCountsResponseDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.soap.SOAPEnvelope envelope = factory.getDefaultEnvelope();

        if (param != null) {
            envelope.getBody().addChild(toOM(param, optimizeContent));
        }

        return envelope;
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        aero.nettracer.serviceprovider.wt_1_0.GetOhdResponseDocument param,
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
            if (aero.nettracer.serviceprovider.wt_1_0.PlaceActionFileDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return aero.nettracer.serviceprovider.wt_1_0.PlaceActionFileDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return aero.nettracer.serviceprovider.wt_1_0.PlaceActionFileDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                }
            }

            if (aero.nettracer.serviceprovider.wt_1_0.PlaceActionFileResponseDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return aero.nettracer.serviceprovider.wt_1_0.PlaceActionFileResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return aero.nettracer.serviceprovider.wt_1_0.PlaceActionFileResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                }
            }

            if (aero.nettracer.serviceprovider.wt_1_0.SendForwardMessageDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return aero.nettracer.serviceprovider.wt_1_0.SendForwardMessageDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return aero.nettracer.serviceprovider.wt_1_0.SendForwardMessageDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                }
            }

            if (aero.nettracer.serviceprovider.wt_1_0.SendForwardMessageResponseDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return aero.nettracer.serviceprovider.wt_1_0.SendForwardMessageResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return aero.nettracer.serviceprovider.wt_1_0.SendForwardMessageResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                }
            }

            if (aero.nettracer.serviceprovider.wt_1_0.GetActionFileDetailsDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return aero.nettracer.serviceprovider.wt_1_0.GetActionFileDetailsDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return aero.nettracer.serviceprovider.wt_1_0.GetActionFileDetailsDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                }
            }

            if (aero.nettracer.serviceprovider.wt_1_0.GetActionFileDetailsResponseDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return aero.nettracer.serviceprovider.wt_1_0.GetActionFileDetailsResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return aero.nettracer.serviceprovider.wt_1_0.GetActionFileDetailsResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                }
            }

            if (aero.nettracer.serviceprovider.wt_1_0.CreateBdoDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return aero.nettracer.serviceprovider.wt_1_0.CreateBdoDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return aero.nettracer.serviceprovider.wt_1_0.CreateBdoDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                }
            }

            if (aero.nettracer.serviceprovider.wt_1_0.CreateBdoResponseDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return aero.nettracer.serviceprovider.wt_1_0.CreateBdoResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return aero.nettracer.serviceprovider.wt_1_0.CreateBdoResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                }
            }

            if (aero.nettracer.serviceprovider.wt_1_0.RequestQuickOhdDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return aero.nettracer.serviceprovider.wt_1_0.RequestQuickOhdDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return aero.nettracer.serviceprovider.wt_1_0.RequestQuickOhdDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                }
            }

            if (aero.nettracer.serviceprovider.wt_1_0.RequestQuickOhdResponseDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return aero.nettracer.serviceprovider.wt_1_0.RequestQuickOhdResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return aero.nettracer.serviceprovider.wt_1_0.RequestQuickOhdResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                }
            }

            if (aero.nettracer.serviceprovider.wt_1_0.CloseAhlDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return aero.nettracer.serviceprovider.wt_1_0.CloseAhlDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return aero.nettracer.serviceprovider.wt_1_0.CloseAhlDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                }
            }

            if (aero.nettracer.serviceprovider.wt_1_0.CloseAhlResponseDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return aero.nettracer.serviceprovider.wt_1_0.CloseAhlResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return aero.nettracer.serviceprovider.wt_1_0.CloseAhlResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                }
            }

            if (aero.nettracer.serviceprovider.wt_1_0.ReinstanteAhlDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return aero.nettracer.serviceprovider.wt_1_0.ReinstanteAhlDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return aero.nettracer.serviceprovider.wt_1_0.ReinstanteAhlDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                }
            }

            if (aero.nettracer.serviceprovider.wt_1_0.ReinstanteAhlResponseDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return aero.nettracer.serviceprovider.wt_1_0.ReinstanteAhlResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return aero.nettracer.serviceprovider.wt_1_0.ReinstanteAhlResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                }
            }

            if (aero.nettracer.serviceprovider.wt_1_0.SuspendAhlDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return aero.nettracer.serviceprovider.wt_1_0.SuspendAhlDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return aero.nettracer.serviceprovider.wt_1_0.SuspendAhlDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                }
            }

            if (aero.nettracer.serviceprovider.wt_1_0.SuspendAhlResponseDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return aero.nettracer.serviceprovider.wt_1_0.SuspendAhlResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return aero.nettracer.serviceprovider.wt_1_0.SuspendAhlResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                }
            }

            if (aero.nettracer.serviceprovider.wt_1_0.CreateOhdDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return aero.nettracer.serviceprovider.wt_1_0.CreateOhdDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return aero.nettracer.serviceprovider.wt_1_0.CreateOhdDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                }
            }

            if (aero.nettracer.serviceprovider.wt_1_0.CreateOhdResponseDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return aero.nettracer.serviceprovider.wt_1_0.CreateOhdResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return aero.nettracer.serviceprovider.wt_1_0.CreateOhdResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                }
            }

            if (aero.nettracer.serviceprovider.wt_1_0.AmendOhdDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return aero.nettracer.serviceprovider.wt_1_0.AmendOhdDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return aero.nettracer.serviceprovider.wt_1_0.AmendOhdDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                }
            }

            if (aero.nettracer.serviceprovider.wt_1_0.AmendOhdResponseDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return aero.nettracer.serviceprovider.wt_1_0.AmendOhdResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return aero.nettracer.serviceprovider.wt_1_0.AmendOhdResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                }
            }

            if (aero.nettracer.serviceprovider.wt_1_0.GetActionFileSummaryDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return aero.nettracer.serviceprovider.wt_1_0.GetActionFileSummaryDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return aero.nettracer.serviceprovider.wt_1_0.GetActionFileSummaryDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                }
            }

            if (aero.nettracer.serviceprovider.wt_1_0.GetActionFileSummaryResponseDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return aero.nettracer.serviceprovider.wt_1_0.GetActionFileSummaryResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return aero.nettracer.serviceprovider.wt_1_0.GetActionFileSummaryResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                }
            }

            if (aero.nettracer.serviceprovider.wt_1_0.CloseOhdDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return aero.nettracer.serviceprovider.wt_1_0.CloseOhdDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return aero.nettracer.serviceprovider.wt_1_0.CloseOhdDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                }
            }

            if (aero.nettracer.serviceprovider.wt_1_0.CloseOhdResponseDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return aero.nettracer.serviceprovider.wt_1_0.CloseOhdResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return aero.nettracer.serviceprovider.wt_1_0.CloseOhdResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                }
            }

            if (aero.nettracer.serviceprovider.wt_1_0.CreateAhlDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return aero.nettracer.serviceprovider.wt_1_0.CreateAhlDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return aero.nettracer.serviceprovider.wt_1_0.CreateAhlDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                }
            }

            if (aero.nettracer.serviceprovider.wt_1_0.CreateAhlResponseDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return aero.nettracer.serviceprovider.wt_1_0.CreateAhlResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return aero.nettracer.serviceprovider.wt_1_0.CreateAhlResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                }
            }

            if (aero.nettracer.serviceprovider.wt_1_0.EraseActionFileDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return aero.nettracer.serviceprovider.wt_1_0.EraseActionFileDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return aero.nettracer.serviceprovider.wt_1_0.EraseActionFileDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                }
            }

            if (aero.nettracer.serviceprovider.wt_1_0.EraseActionFileResponseDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return aero.nettracer.serviceprovider.wt_1_0.EraseActionFileResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return aero.nettracer.serviceprovider.wt_1_0.EraseActionFileResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                }
            }

            if (aero.nettracer.serviceprovider.wt_1_0.ForwardOhdDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return aero.nettracer.serviceprovider.wt_1_0.ForwardOhdDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return aero.nettracer.serviceprovider.wt_1_0.ForwardOhdDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                }
            }

            if (aero.nettracer.serviceprovider.wt_1_0.ForwardOhdResponseDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return aero.nettracer.serviceprovider.wt_1_0.ForwardOhdResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return aero.nettracer.serviceprovider.wt_1_0.ForwardOhdResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                }
            }

            if (aero.nettracer.serviceprovider.wt_1_0.GetAhlDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return aero.nettracer.serviceprovider.wt_1_0.GetAhlDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return aero.nettracer.serviceprovider.wt_1_0.GetAhlDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                }
            }

            if (aero.nettracer.serviceprovider.wt_1_0.GetAhlResponseDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return aero.nettracer.serviceprovider.wt_1_0.GetAhlResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return aero.nettracer.serviceprovider.wt_1_0.GetAhlResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                }
            }

            if (aero.nettracer.serviceprovider.wt_1_0.RequestOhdDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return aero.nettracer.serviceprovider.wt_1_0.RequestOhdDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return aero.nettracer.serviceprovider.wt_1_0.RequestOhdDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                }
            }

            if (aero.nettracer.serviceprovider.wt_1_0.RequestOhdResponseDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return aero.nettracer.serviceprovider.wt_1_0.RequestOhdResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return aero.nettracer.serviceprovider.wt_1_0.RequestOhdResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                }
            }

            if (aero.nettracer.serviceprovider.wt_1_0.AmendAhlDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return aero.nettracer.serviceprovider.wt_1_0.AmendAhlDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return aero.nettracer.serviceprovider.wt_1_0.AmendAhlDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                }
            }

            if (aero.nettracer.serviceprovider.wt_1_0.AmendAhlResponseDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return aero.nettracer.serviceprovider.wt_1_0.AmendAhlResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return aero.nettracer.serviceprovider.wt_1_0.AmendAhlResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                }
            }

            if (aero.nettracer.serviceprovider.wt_1_0.GetActionFileCountsDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return aero.nettracer.serviceprovider.wt_1_0.GetActionFileCountsDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return aero.nettracer.serviceprovider.wt_1_0.GetActionFileCountsDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                }
            }

            if (aero.nettracer.serviceprovider.wt_1_0.GetActionFileCountsResponseDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return aero.nettracer.serviceprovider.wt_1_0.GetActionFileCountsResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return aero.nettracer.serviceprovider.wt_1_0.GetActionFileCountsResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                }
            }

            if (aero.nettracer.serviceprovider.wt_1_0.GetOhdDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return aero.nettracer.serviceprovider.wt_1_0.GetOhdDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return aero.nettracer.serviceprovider.wt_1_0.GetOhdDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                }
            }

            if (aero.nettracer.serviceprovider.wt_1_0.GetOhdResponseDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return aero.nettracer.serviceprovider.wt_1_0.GetOhdResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return aero.nettracer.serviceprovider.wt_1_0.GetOhdResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
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
