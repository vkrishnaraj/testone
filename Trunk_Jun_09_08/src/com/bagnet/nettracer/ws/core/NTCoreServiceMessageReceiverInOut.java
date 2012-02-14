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
                 if ("updateIncidentFaultCodes".equals(methodName)) {
                    com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesResponseDocument updateIncidentFaultCodesResponse3 =
                        null;
                    com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesDocument wrappedParam =
                        (com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesDocument) fromOM(msgContext.getEnvelope()
                                                                                                         .getBody()
                                                                                                         .getFirstElement(),
                            com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesDocument.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    updateIncidentFaultCodesResponse3 = skel.updateIncidentFaultCodes(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            updateIncidentFaultCodesResponse3, false);
                } else
                 if ("bulkBeornOHD".equals(methodName)) {
                    com.bagnet.nettracer.ws.core.BulkBeornOHDResponseDocument bulkBeornOHDResponse5 =
                        null;
                    com.bagnet.nettracer.ws.core.BulkBeornOHDDocument wrappedParam =
                        (com.bagnet.nettracer.ws.core.BulkBeornOHDDocument) fromOM(msgContext.getEnvelope()
                                                                                             .getBody()
                                                                                             .getFirstElement(),
                            com.bagnet.nettracer.ws.core.BulkBeornOHDDocument.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    bulkBeornOHDResponse5 = skel.bulkBeornOHD(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            bulkBeornOHDResponse5, false);
                } else
                 if ("insertQuickOHD".equals(methodName)) {
                    com.bagnet.nettracer.ws.core.InsertQuickOHDResponseDocument insertQuickOHDResponse7 =
                        null;
                    com.bagnet.nettracer.ws.core.InsertQuickOHDDocument wrappedParam =
                        (com.bagnet.nettracer.ws.core.InsertQuickOHDDocument) fromOM(msgContext.getEnvelope()
                                                                                               .getBody()
                                                                                               .getFirstElement(),
                            com.bagnet.nettracer.ws.core.InsertQuickOHDDocument.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    insertQuickOHDResponse7 = skel.insertQuickOHD(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            insertQuickOHDResponse7, false);
                } else
                 if ("beornOHD".equals(methodName)) {
                    com.bagnet.nettracer.ws.core.BeornOHDResponseDocument beornOHDResponse9 =
                        null;
                    com.bagnet.nettracer.ws.core.BeornOHDDocument wrappedParam = (com.bagnet.nettracer.ws.core.BeornOHDDocument) fromOM(msgContext.getEnvelope()
                                                                                                                                                  .getBody()
                                                                                                                                                  .getFirstElement(),
                            com.bagnet.nettracer.ws.core.BeornOHDDocument.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    beornOHDResponse9 = skel.beornOHD(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            beornOHDResponse9, false);
                } else
                 if ("getOHD".equals(methodName)) {
                    com.bagnet.nettracer.ws.core.GetOHDResponseDocument getOHDResponse11 =
                        null;
                    com.bagnet.nettracer.ws.core.GetOHDDocument wrappedParam = (com.bagnet.nettracer.ws.core.GetOHDDocument) fromOM(msgContext.getEnvelope()
                                                                                                                                              .getBody()
                                                                                                                                              .getFirstElement(),
                            com.bagnet.nettracer.ws.core.GetOHDDocument.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    getOHDResponse11 = skel.getOHD(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            getOHDResponse11, false);
                } else
                 if ("UpdateBdoDelivery".equals(methodName)) {
                    com.bagnet.nettracer.ws.core.UpdateBdoDeliveryResponseDocument updateBdoDeliveryResponse13 =
                        null;
                    com.bagnet.nettracer.ws.core.UpdateBdoDeliveryDocument wrappedParam =
                        (com.bagnet.nettracer.ws.core.UpdateBdoDeliveryDocument) fromOM(msgContext.getEnvelope()
                                                                                                  .getBody()
                                                                                                  .getFirstElement(),
                            com.bagnet.nettracer.ws.core.UpdateBdoDeliveryDocument.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    updateBdoDeliveryResponse13 = skel.UpdateBdoDelivery(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            updateBdoDeliveryResponse13, false);
                } else
                 if ("insertOHD".equals(methodName)) {
                    com.bagnet.nettracer.ws.core.InsertOHDResponseDocument insertOHDResponse15 =
                        null;
                    com.bagnet.nettracer.ws.core.InsertOHDDocument wrappedParam = (com.bagnet.nettracer.ws.core.InsertOHDDocument) fromOM(msgContext.getEnvelope()
                                                                                                                                                    .getBody()
                                                                                                                                                    .getFirstElement(),
                            com.bagnet.nettracer.ws.core.InsertOHDDocument.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    insertOHDResponse15 = skel.insertOHD(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            insertOHDResponse15, false);
                } else
                 if ("getIncident".equals(methodName)) {
                    com.bagnet.nettracer.ws.core.GetIncidentResponseDocument getIncidentResponse17 =
                        null;
                    com.bagnet.nettracer.ws.core.GetIncidentDocument wrappedParam =
                        (com.bagnet.nettracer.ws.core.GetIncidentDocument) fromOM(msgContext.getEnvelope()
                                                                                            .getBody()
                                                                                            .getFirstElement(),
                            com.bagnet.nettracer.ws.core.GetIncidentDocument.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    getIncidentResponse17 = skel.getIncident(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            getIncidentResponse17, false);
                } else
                 if ("logoff".equals(methodName)) {
                    com.bagnet.nettracer.ws.core.LogoffResponseDocument logoffResponse19 =
                        null;
                    com.bagnet.nettracer.ws.core.LogoffDocument wrappedParam = (com.bagnet.nettracer.ws.core.LogoffDocument) fromOM(msgContext.getEnvelope()
                                                                                                                                              .getBody()
                                                                                                                                              .getFirstElement(),
                            com.bagnet.nettracer.ws.core.LogoffDocument.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    logoffResponse19 = skel.logoff(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            logoffResponse19, false);
                } else
                 if ("insertIncident".equals(methodName)) {
                    com.bagnet.nettracer.ws.core.InsertIncidentResponseDocument insertIncidentResponse21 =
                        null;
                    com.bagnet.nettracer.ws.core.InsertIncidentDocument wrappedParam =
                        (com.bagnet.nettracer.ws.core.InsertIncidentDocument) fromOM(msgContext.getEnvelope()
                                                                                               .getBody()
                                                                                               .getFirstElement(),
                            com.bagnet.nettracer.ws.core.InsertIncidentDocument.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    insertIncidentResponse21 = skel.insertIncident(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            insertIncidentResponse21, false);
                } else
                 if ("queryForFaultCode".equals(methodName)) {
                    com.bagnet.nettracer.ws.core.QueryForFaultCodeResponseDocument queryForFaultCodeResponse23 =
                        null;
                    com.bagnet.nettracer.ws.core.QueryForFaultCodeDocument wrappedParam =
                        (com.bagnet.nettracer.ws.core.QueryForFaultCodeDocument) fromOM(msgContext.getEnvelope()
                                                                                                  .getBody()
                                                                                                  .getFirstElement(),
                            com.bagnet.nettracer.ws.core.QueryForFaultCodeDocument.class,
                            getEnvelopeNamespaces(msgContext.getEnvelope()));

                    queryForFaultCodeResponse23 = skel.queryForFaultCode(wrappedParam);

                    envelope = toEnvelope(getSOAPFactory(msgContext),
                            queryForFaultCodeResponse23, false);
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
        com.bagnet.nettracer.ws.core.InsertQuickOHDDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.om.OMElement toOM(
        com.bagnet.nettracer.ws.core.InsertQuickOHDResponseDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.om.OMElement toOM(
        com.bagnet.nettracer.ws.core.BulkBeornOHDDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.om.OMElement toOM(
        com.bagnet.nettracer.ws.core.BulkBeornOHDResponseDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.om.OMElement toOM(
        com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.om.OMElement toOM(
        com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesResponseDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

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
        com.bagnet.nettracer.ws.core.BeornOHDDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.om.OMElement toOM(
        com.bagnet.nettracer.ws.core.BeornOHDResponseDocument param,
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
        com.bagnet.nettracer.ws.core.UpdateBdoDeliveryDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.om.OMElement toOM(
        com.bagnet.nettracer.ws.core.UpdateBdoDeliveryResponseDocument param,
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
        com.bagnet.nettracer.ws.core.QueryForFaultCodeDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.om.impl.builder.StAXOMBuilder builder = new org.apache.axiom.om.impl.builder.StAXOMBuilder(org.apache.axiom.om.OMAbstractFactory.getOMFactory(),
                new org.apache.axis2.util.StreamWrapper(param.newXMLStreamReader()));
        org.apache.axiom.om.OMElement documentElement = builder.getDocumentElement();

        ((org.apache.axiom.om.impl.OMNodeEx) documentElement).setParent(null);

        return documentElement;
    }

    private org.apache.axiom.om.OMElement toOM(
        com.bagnet.nettracer.ws.core.QueryForFaultCodeResponseDocument param,
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
        com.bagnet.nettracer.ws.core.InsertQuickOHDResponseDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.soap.SOAPEnvelope envelope = factory.getDefaultEnvelope();

        if (param != null) {
            envelope.getBody().addChild(toOM(param, optimizeContent));
        }

        return envelope;
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        com.bagnet.nettracer.ws.core.BulkBeornOHDResponseDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.soap.SOAPEnvelope envelope = factory.getDefaultEnvelope();

        if (param != null) {
            envelope.getBody().addChild(toOM(param, optimizeContent));
        }

        return envelope;
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesResponseDocument param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        org.apache.axiom.soap.SOAPEnvelope envelope = factory.getDefaultEnvelope();

        if (param != null) {
            envelope.getBody().addChild(toOM(param, optimizeContent));
        }

        return envelope;
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
        com.bagnet.nettracer.ws.core.BeornOHDResponseDocument param,
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
        com.bagnet.nettracer.ws.core.UpdateBdoDeliveryResponseDocument param,
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
        com.bagnet.nettracer.ws.core.QueryForFaultCodeResponseDocument param,
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
            if (com.bagnet.nettracer.ws.core.InsertQuickOHDDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return com.bagnet.nettracer.ws.core.InsertQuickOHDDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return com.bagnet.nettracer.ws.core.InsertQuickOHDDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                }
            }

            if (com.bagnet.nettracer.ws.core.InsertQuickOHDResponseDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return com.bagnet.nettracer.ws.core.InsertQuickOHDResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return com.bagnet.nettracer.ws.core.InsertQuickOHDResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                }
            }

            if (com.bagnet.nettracer.ws.core.BulkBeornOHDDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return com.bagnet.nettracer.ws.core.BulkBeornOHDDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return com.bagnet.nettracer.ws.core.BulkBeornOHDDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                }
            }

            if (com.bagnet.nettracer.ws.core.BulkBeornOHDResponseDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return com.bagnet.nettracer.ws.core.BulkBeornOHDResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return com.bagnet.nettracer.ws.core.BulkBeornOHDResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                }
            }

            if (com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                }
            }

            if (com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesResponseDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return com.bagnet.nettracer.ws.core.UpdateIncidentFaultCodesResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                }
            }

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

            if (com.bagnet.nettracer.ws.core.BeornOHDDocument.class.equals(type)) {
                if (extraNamespaces != null) {
                    return com.bagnet.nettracer.ws.core.BeornOHDDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return com.bagnet.nettracer.ws.core.BeornOHDDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                }
            }

            if (com.bagnet.nettracer.ws.core.BeornOHDResponseDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return com.bagnet.nettracer.ws.core.BeornOHDResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return com.bagnet.nettracer.ws.core.BeornOHDResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
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

            if (com.bagnet.nettracer.ws.core.UpdateBdoDeliveryDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return com.bagnet.nettracer.ws.core.UpdateBdoDeliveryDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return com.bagnet.nettracer.ws.core.UpdateBdoDeliveryDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                }
            }

            if (com.bagnet.nettracer.ws.core.UpdateBdoDeliveryResponseDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return com.bagnet.nettracer.ws.core.UpdateBdoDeliveryResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return com.bagnet.nettracer.ws.core.UpdateBdoDeliveryResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
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

            if (com.bagnet.nettracer.ws.core.QueryForFaultCodeDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return com.bagnet.nettracer.ws.core.QueryForFaultCodeDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return com.bagnet.nettracer.ws.core.QueryForFaultCodeDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                }
            }

            if (com.bagnet.nettracer.ws.core.QueryForFaultCodeResponseDocument.class.equals(
                        type)) {
                if (extraNamespaces != null) {
                    return com.bagnet.nettracer.ws.core.QueryForFaultCodeResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching(),
                        new org.apache.xmlbeans.XmlOptions().setLoadAdditionalNamespaces(
                            extraNamespaces));
                } else {
                    return com.bagnet.nettracer.ws.core.QueryForFaultCodeResponseDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
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
