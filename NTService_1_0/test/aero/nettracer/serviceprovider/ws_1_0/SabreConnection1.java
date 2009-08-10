package aero.nettracer.serviceprovider.ws_1_0;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.opentravel.www.ota._2002._11.SessionCreateRQDocument;
import org.opentravel.www.ota._2002._11.SessionCreateRSDocument;
import org.opentravel.www.ota._2002._11.SessionCreateRQDocument.SessionCreateRQ.POS.Source;
import org.xmlsoap.schemas.ws._2002._12.secext.SecurityDocument;
import org.xmlsoap.schemas.ws._2002._12.secext.SecurityDocument.Security.UsernameToken;

import aero.nettracer.serviceprovider.ws_1_0.res.sabre.Reservation;

import com.sabre.webservices.websvc.SessionCreateRQServiceStub;
import com.sabre.webservices.websvc.SessionCreateRQServiceStub2;

public class SabreConnection1 {
	private static final String CREATE_PREFIX = "CREATE SESSION: ";
	private static final String ENDPOINT = "http://DEV01:8088/mockSessionCreateSoapBinding";
	private static final String USERNAME = "Username";
	private static final String PASSWORD = "Password";
	private static final String ORGANIZATION = "Organization";
	private static final String PCC = "PCC";
	private static final String DOMAIN = "DOMAIN";
	
	@Test
	public void test() throws RemoteException {
		Logger logger = Logger.getLogger(Reservation.class);
		
		try {

			SessionCreateRQServiceStub2 stub = new SessionCreateRQServiceStub2(
					null, ENDPOINT);

			SecurityDocument securityDocument = SecurityDocument.Factory
					.newInstance();
			UsernameToken token = securityDocument.addNewSecurity()
					.addNewUsernameToken();
			token.setUsername(USERNAME);
			token.setOrganization(ORGANIZATION);
			token.setPassword(PASSWORD);
			token.setDomain(DOMAIN);

			SessionCreateRQDocument rqDoc = SessionCreateRQDocument.Factory
					.newInstance();
			Source source = rqDoc.addNewSessionCreateRQ().addNewPOS()
					.addNewSource();
			source.setPseudoCityCode(PCC);

			
			
			SessionCreateRSDocument responseDocument = stub.sessionCreateRQ(rqDoc, null, securityDocument);
			String binaryToken = stub.getBinarySecurityToken();
			System.out.println(binaryToken);			
			// TODO: HANDLE RESPONSE
			
			logger.info(responseDocument.toString());

		} catch (AxisFault e) {
			logger.error(CREATE_PREFIX + "Error creating session: ", e);
			throw e;
		} catch (RemoteException e) {
			logger.error(CREATE_PREFIX + "Error creating session: ", e);
			throw e;
		}
	}
}
