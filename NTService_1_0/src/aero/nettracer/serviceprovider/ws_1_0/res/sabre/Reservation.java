package aero.nettracer.serviceprovider.ws_1_0.res.sabre;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;
import org.apache.log4j.Logger;
import org.opentravel.www.ota._2002._11.SessionCloseRQDocument;
import org.opentravel.www.ota._2002._11.SessionCloseRSDocument;
import org.opentravel.www.ota._2002._11.SessionCreateRQDocument;
import org.opentravel.www.ota._2002._11.SessionCreateRSDocument;
import org.opentravel.www.ota._2002._11.SessionCreateRQDocument.SessionCreateRQ.POS.Source;
import org.xmlsoap.schemas.ws._2002._12.secext.SecurityDocument;
import org.xmlsoap.schemas.ws._2002._12.secext.SecurityDocument.Security;
import org.xmlsoap.schemas.ws._2002._12.secext.SecurityDocument.Security.UsernameToken;

import aero.nettracer.serviceprovider.common.db.SabreConnection;
import aero.nettracer.serviceprovider.common.exceptions.UnexpectedException;
import aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader;
import aero.nettracer.serviceprovider.ws_1_0.res.ReservationInterface;
import aero.nettracer.serviceprovider.ws_1_0.res.sabre.pool.SabrePool;
import aero.nettracer.serviceprovider.ws_1_0.response.xsd.EnplanementResponse;
import aero.nettracer.serviceprovider.ws_1_0.response.xsd.OsiResponse;
import aero.nettracer.serviceprovider.ws_1_0.response.xsd.RemarkResponse;
import aero.nettracer.serviceprovider.ws_1_0.response.xsd.ReservationResponse;

import com.sabre.webservices.websvc.SessionCloseRQServiceStub;
import com.sabre.webservices.websvc.SessionCreateRQServiceStub2;

public class Reservation implements ReservationInterface {

	private static final String CREATE_PREFIX = "CREATE SESSION: ";
	private static final String CLOSE_PREFIX = "CLOSE SESSION: ";
	private static final String VALIDATE_PREFIX = "VALIDATE SESSION: ";
	private static final String WRI_PREFIX = "ADD REM: ";
	private static final String OSI__PREFIX = "GET OSI: ";
	private static final String RES_PREFIX = "GET RES: ";
	private static final String ENP_PREFIX = "GET ENP: ";

	Logger logger = Logger.getLogger(Reservation.class);

	@Override
	public EnplanementResponse getEnplanements(RequestHeader header) {
		return null;
	}

	@Override
	public OsiResponse getOsiContents(RequestHeader header, String pnr,
			String bagTag) {
		return null;
	}

	@Override
	public ReservationResponse getReservationData(RequestHeader header,
			String pnr, String bagTag) throws UnexpectedException {

		return null;
	}

	@Override
	public RemarkResponse writeRemark(RequestHeader header, String pnr,
			String comment) throws UnexpectedException {
		// TODO: Set up and configure pool.
		SabrePool pool = new SabrePool();
		SabreConnection connParams = null;
		try {
			connParams = (SabreConnection) pool.borrowObject();

			// TODO: LOG ON (move to borrow?)
			createSession(header, connParams);

			// TODO: LOAD PNR

			// TODO: WRITE REMARK

			// TODO: END RECORD

			// TODO: IGNORE IF EXCEPTION IN END RECORD

			// TODO: LOG OFF (move to return?)
			closeSession(header, connParams);

		} catch (Exception e) {
			e.printStackTrace();
			throw new UnexpectedException();
		} finally {
			try {
				pool.returnObject(connParams);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	private void closeSession(RequestHeader header, SabreConnection connParams)
			throws AxisFault, RemoteException {
		try {

			SessionCloseRQServiceStub stub = new SessionCloseRQServiceStub(
					null, connParams.getEndpoint());

			SecurityDocument securityDocument = SecurityDocument.Factory
					.newInstance();
			
			Security s = securityDocument.addNewSecurity();
			s.setBinarySecurityToken(connParams.getBinarySecurityToken());
			
			
			UsernameToken token = s
					.addNewUsernameToken();
			token.setUsername(connParams.getUsername());
			token.setOrganization(connParams.getOrganization());
			token.setPassword(connParams.getPassword());
			token.setDomain(connParams.getDomain());
			
			

			SessionCloseRQDocument rqDoc = SessionCloseRQDocument.Factory
					.newInstance();
			org.opentravel.www.ota._2002._11.SessionCloseRQDocument.SessionCloseRQ.POS.Source source = rqDoc.addNewSessionCloseRQ().addNewPOS()
					.addNewSource();

			source.setPseudoCityCode(connParams.getPseudoCityCode());

			logger.info(CLOSE_PREFIX + connParams.getLoggingString());
			SessionCloseRSDocument responseDocument = stub.sessionCloseRQ(rqDoc, null, securityDocument);
				//stub.sessionCreateRQ(rqDoc, null, securityDocument);
			logger.info(responseDocument.toString());

		} catch (AxisFault e) {
			logger.error(CLOSE_PREFIX + "Error creating session: ", e);
			throw e;
		} catch (RemoteException e) {
			logger.error(CLOSE_PREFIX + "Error creating session: ", e);
			throw e;
		}
	}

	private void createSession(RequestHeader header, SabreConnection connParams)
			throws AxisFault, RemoteException {
		try {
			SessionCreateRQServiceStub2 stub = new SessionCreateRQServiceStub2(
					null, connParams.getEndpoint());

			SecurityDocument securityDocument = SecurityDocument.Factory
					.newInstance();
			UsernameToken token = securityDocument.addNewSecurity()
					.addNewUsernameToken();
			token.setUsername(connParams.getUsername());
			token.setOrganization(connParams.getOrganization());
			token.setPassword(connParams.getPassword());
			token.setDomain(connParams.getDomain());

			SessionCreateRQDocument rqDoc = SessionCreateRQDocument.Factory
					.newInstance();
			Source source = rqDoc.addNewSessionCreateRQ().addNewPOS()
					.addNewSource();
			source.setPseudoCityCode(connParams.getPseudoCityCode());

			logger.info(CREATE_PREFIX + connParams.getLoggingString());
			
			SessionCreateRSDocument responseDocument = stub.sessionCreateRQ(rqDoc, null, securityDocument);
			String binarySecurityToken = stub.getBinarySecurityToken();
			connParams.setBinarySecurityToken(binarySecurityToken);
			
			logger.info(responseDocument.toString());
			logger.info("Binary Security Token Received: " + binarySecurityToken);

		} catch (AxisFault e) {
			logger.error(CREATE_PREFIX + "Error creating session: ", e);
			throw e;
		} catch (RemoteException e) {
			logger.error(CREATE_PREFIX + "Error creating session: ", e);
			throw e;
		}
	}
}
