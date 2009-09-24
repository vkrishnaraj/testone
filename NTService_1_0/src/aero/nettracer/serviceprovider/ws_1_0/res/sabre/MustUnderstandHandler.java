package aero.nettracer.serviceprovider.ws_1_0.res.sabre;

import java.util.Iterator;

import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axiom.soap.SOAPHeaderBlock;
import org.apache.axis2.AxisFault;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.description.AxisOperation;
import org.apache.axis2.description.AxisService;
import org.apache.axis2.engine.AbstractDispatcher;
import org.apache.axis2.jaxws.dispatchers.MustUnderstandUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Do JAXWS MustUnderstand header processing per the JAXWS 2.0 specification.
 * This checks for a specific compliance situation where a non-existant
 * operation with mustUnderstood headers that are not understood must throw a
 * mustUnderstandFault rather than an invalid EPR exception.
 * 
 * Note that this handler should be inserted in the inbound dispather chains so
 * that the Dispatcher checkPostConditions does not throw the invalid EPR fault
 * if the operation is null.
 */
public class MustUnderstandHandler extends AbstractDispatcher {
	private static final Log log = LogFactory
			.getLog(MustUnderstandHandler.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.axis2.engine.AbstractDispatcher#findOperation(org.apache.axis2
	 * .description.AxisService, org.apache.axis2.context.MessageContext)
	 */
	@Override
	public AxisOperation findOperation(AxisService service,
			MessageContext messageContext) throws AxisFault {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.axis2.engine.AbstractDispatcher#findService(org.apache.axis2
	 * .context.MessageContext)
	 */
	@Override
	public AxisService findService(MessageContext messageContext)
			throws AxisFault {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.axis2.engine.AbstractDispatcher#initDispatcher()
	 */
	@Override
	public void initDispatcher() {
	}

	public InvocationResponse invoke(MessageContext msgctx) throws AxisFault {
		AxisService axisService = msgctx.getAxisService();
		AxisOperation axisOperation = msgctx.getAxisOperation();


		// Byron: 9/14/2009
		// Code modified to acknowledge the mustUnderstand attributes
		checkMustUnderstand(msgctx);

		return InvocationResponse.CONTINUE;
	}

	private boolean checkMustUnderstand(MessageContext msgContext)
			throws AxisFault {
		boolean checksPass = true;

		SOAPEnvelope envelope = msgContext.getEnvelope();

		MustUnderstandUtils.markUnderstoodHeaderParameters(msgContext);

		if (envelope.getHeader() != null) {
			Iterator headerBlocks = envelope.getHeader().getHeadersToProcess(
					null);
			while (headerBlocks.hasNext()) {
				SOAPHeaderBlock headerBlock = (SOAPHeaderBlock) headerBlocks
						.next();
				if (headerBlock.isProcessed()
						|| !headerBlock.getMustUnderstand()) {
					continue;
				}
				headerBlock.setProcessed();
			}
		}
		return checksPass;
	}

}
