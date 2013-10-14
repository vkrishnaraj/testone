package aero.nettracer.serviceprovider.ws_1_0.res.cebs;

import java.io.File;
import java.net.URL;

import org.apache.axis2.AxisFault;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;
import org.apache.log4j.Logger;

import aero.nettracer.serviceprovider.common.db.ParameterType;
import aero.nettracer.serviceprovider.common.db.User;
import aero.nettracer.utilities.ssl.AuthSSLProtocolSocketFactory;

import com.swacorp.services.btws.wsdl.v1.BTWSStub;

public class ConnectionUtil {
	
	private static Logger logger = Logger.getLogger(ConnectionUtil.class);
	
	/**
	 * This method creates a custom ProtocolSocketFactory using the truststore and keystore locations passed in. It will then
	 * set this custom protocol on the stubs service client for use by just this stub. This is needed for 2 way SSL.
	 */
	protected static void prepareTwoWaySSL(BTWSStub stub, String truststoreLoc, String keystoreLoc, String trustpass, String keypass) {
		try {
			// Preparing the stores
			URL truststoreUrl = new File(truststoreLoc).toURI().toURL();
			URL keystoreUrl = new File(keystoreLoc).toURI().toURL();
			// Creating the custom factory
			ProtocolSocketFactory socketFactory = new AuthSSLProtocolSocketFactory(keystoreUrl, keypass, truststoreUrl, trustpass);
			// Creating a custom https protocol with the factory
			Protocol twoWaySSLProtocolHandler = new Protocol("https", socketFactory, 443);
			// Setting the protocol on the stub
			stub._getServiceClient().getOptions().setProperty(HTTPConstants.CUSTOM_PROTOCOL_HANDLER, twoWaySSLProtocolHandler);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	/**
	 * Returns the service stub for cebs integration.  Null checks the existing stub for mocking/testing purposes
	 * 
	 * @param btwsStub
	 * @param user
	 * @throws AxisFault
	 */
	protected static BTWSStub getStub(BTWSStub btwsStub, User user) throws AxisFault {
		if (btwsStub == null) {
			// Grabbing all the user profile parameters needed for this call.
			String endpoint = user.getProfile().getParameters().get(ParameterType.RESERVATION_ENDPOINT);
			String truststore = user.getProfile().getParameters().get(ParameterType.TRUSTSTORE);
			String keystore = user.getProfile().getParameters().get(ParameterType.KEYSTORE);
			String trustpass = user.getProfile().getParameters().get(ParameterType.TRUSTPASS);
			String keypass = user.getProfile().getParameters().get(ParameterType.KEYPASS);
			
			// STUB & 2 WAY SSL
			btwsStub = new BTWSStub(endpoint);
			ConnectionUtil.prepareTwoWaySSL(btwsStub, truststore, keystore, trustpass, keypass);
		}
		return btwsStub;
	}
}
