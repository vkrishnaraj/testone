package aero.nettracer.serviceprovider.ws_1_0.res.sabre;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.ebxml.www.namespaces.messageheader.MessageHeaderDocument;
import org.ebxml.www.namespaces.messageheader.MessageHeaderDocument.MessageHeader;
import org.ebxml.www.namespaces.messageheader.PartyIdDocument.PartyId;
import org.opentravel.www.ota._2002._11.SessionCloseRQDocument;
import org.opentravel.www.ota._2002._11.SessionCloseRSDocument;
import org.opentravel.www.ota._2002._11.SessionCreateRQDocument;
import org.opentravel.www.ota._2002._11.SessionCreateRSDocument;
import org.opentravel.www.ota._2002._11.SessionValidateRQDocument;
import org.opentravel.www.ota._2002._11.SessionValidateRSDocument;
import org.xmlsoap.schemas.ws._2002._12.secext.SecurityDocument;
import org.xmlsoap.schemas.ws._2002._12.secext.SecurityDocument.Security;
import org.xmlsoap.schemas.ws._2002._12.secext.SecurityDocument.Security.UsernameToken;

import aero.nettracer.serviceprovider.common.ServiceConstants;
import aero.nettracer.serviceprovider.common.db.SabreConnection;
import aero.nettracer.serviceprovider.common.db.User;
import aero.nettracer.serviceprovider.common.exceptions.UnexpectedException;
import aero.nettracer.serviceprovider.common.utils.ServiceUtilities;
import aero.nettracer.serviceprovider.ws_1_0.common.xsd.Itinerary;
import aero.nettracer.serviceprovider.ws_1_0.common.xsd.Passenger;
import aero.nettracer.serviceprovider.ws_1_0.res.ReservationInterface;
import aero.nettracer.serviceprovider.ws_1_0.res.sabre.pool.SabrePool;
import aero.nettracer.serviceprovider.ws_1_0.res.sabre.pool.SabrePoolManager;
import aero.nettracer.serviceprovider.ws_1_0.response.xsd.EnplanementResponse;
import aero.nettracer.serviceprovider.ws_1_0.response.xsd.OsiResponse;
import aero.nettracer.serviceprovider.ws_1_0.response.xsd.RemarkResponse;
import aero.nettracer.serviceprovider.ws_1_0.response.xsd.ReservationResponse;

import com.sabre.webservices.sabrexml._2003._07.AddRemarkRQDocument;
import com.sabre.webservices.sabrexml._2003._07.AddRemarkRSDocument;
import com.sabre.webservices.sabrexml._2003._07.EndTransactionRQDocument;
import com.sabre.webservices.sabrexml._2003._07.EndTransactionRSDocument;
import com.sabre.webservices.sabrexml._2003._07.IgnoreTransactionRQDocument;
import com.sabre.webservices.sabrexml._2003._07.IgnoreTransactionRSDocument;
import com.sabre.webservices.sabrexml._2003._07.ItemType;
import com.sabre.webservices.sabrexml._2003._07.OTATravelItineraryRSDocument;
import com.sabre.webservices.sabrexml._2003._07.OTATravelItineraryReadRQDocument;
import com.sabre.webservices.sabrexml._2003._07.SabreCommandLLSRQDocument;
import com.sabre.webservices.sabrexml._2003._07.SabreCommandLLSRSDocument;
import com.sabre.webservices.sabrexml._2003._07.AddRemarkRQDocument.AddRemarkRQ.BasicRemark;
import com.sabre.webservices.sabrexml._2003._07.CustomerInfoType.Customer;
import com.sabre.webservices.sabrexml._2003._07.CustomerInfoType.Customer.Address;
import com.sabre.webservices.sabrexml._2003._07.CustomerInfoType.Customer.CustLoyalty;
import com.sabre.webservices.sabrexml._2003._07.CustomerInfoType.Customer.Email;
import com.sabre.webservices.sabrexml._2003._07.CustomerInfoType.Customer.PersonName;
import com.sabre.webservices.sabrexml._2003._07.CustomerInfoType.Customer.Telephone;
import com.sabre.webservices.sabrexml._2003._07.EndTransactionRQDocument.EndTransactionRQ;
import com.sabre.webservices.sabrexml._2003._07.IgnoreTransactionRQDocument.IgnoreTransactionRQ;
import com.sabre.webservices.sabrexml._2003._07.ItemType.Air;
import com.sabre.webservices.sabrexml._2003._07.OTATravelItineraryRSDocument.OTATravelItineraryRS.TravelItinerary;
import com.sabre.webservices.sabrexml._2003._07.OTATravelItineraryReadRQDocument.OTATravelItineraryReadRQ;
import com.sabre.webservices.sabrexml._2003._07.OTATravelItineraryReadRQDocument.OTATravelItineraryReadRQ.UniqueID;
import com.sabre.webservices.websvc.AddRemarkServiceStub;
import com.sabre.webservices.websvc.EndTransactionServiceStub;
import com.sabre.webservices.websvc.IgnoreTransactionServiceStub;
import com.sabre.webservices.websvc.OTA_TravelItineraryServiceStub;
import com.sabre.webservices.websvc.SabreCommandLLSServiceStub;
import com.sabre.webservices.websvc.SessionCloseRQServiceStub;
import com.sabre.webservices.websvc.SessionCreateRQServiceStub2;
import com.sabre.webservices.websvc.SessionValidateRQServiceStub;

public class Reservation implements ReservationInterface {

	private static final String CREATE_PREFIX = "CREATE SESSION: ";
	private static final String CLOSE_PREFIX = "CLOSE SESSION: ";
	private static final String LOAD_PNR_NO_DATA = "DISPLAY_PNR NO DATA: ";
	private static final String IGNORE_TRANSACTION = "IGN: ";
	private static final String END_TRANSACTION = "END TRANSACTION: ";
	private static final String ADD_REMARK = "ADD REMARK: ";
	private static final String VALIDATE_SESSION = "VALIDATE SESSION: ";
	private static final String NATIVE_COMMAND = "SABRE_NATIVE_COMMAND: ";
	private static final String ACTION_SESSION_CREATE_RQ = "SessionCreateRQ";
	private static final String ACTION_SESSION_CLOSE_RQ = "SessionCloseRQ";
	private static final String ACTION_GENERIC_RQ = "SabreCommandLLSRQ";
	private static final String ACTION_ADD_REMARK_RQ = "AddRemarkLLSRQ";
	private static final String ACTION_END_RQ = "EndTransactionLLSRQ";
	private static final String ACTION_IGNORE_RQ = "IgnoreTransactionLLSRQ";
	private static final String ACTION_TRAVEL_ITINERARY_RQ = "OTA_TravelItineraryReadLLSRQ";
	private static final String ACTION_SESSION_VALIDATE_RQ = "SessionValidateRQ";
	
	private static PartyId basicPartyId = PartyId.Factory.newInstance();
	private static Logger logger = Logger.getLogger(Reservation.class);
	
	static {
		basicPartyId.setStringValue("1");
	}

	@Override
	public EnplanementResponse getEnplanements(User user) {
		return null;
	}

	@Override
	public OsiResponse getOsiContents(User user, String pnr,
			String bagTag) {
		return null;
	}

	@Override
	public ReservationResponse getReservationData(User user,
			String pnr, String bagTag) throws UnexpectedException {

		ReservationResponse response = ReservationResponse.Factory.newInstance();
		HashMap<String, Passenger> paxMap = new HashMap<String, Passenger>();

		SabrePool pool = SabrePoolManager.getInstance().getPool(user);
		SabreConnection connParams = null;

		try {
			connParams = (SabreConnection) pool.borrowObject();
			
			if (pnr == null && bagTag != null) {
				throw new RuntimeException("This feature not capable of implementation.");
			}

			OTATravelItineraryRSDocument doc = loadPnr(connParams, pnr, true);
			TravelItinerary ti = doc.getOTATravelItineraryRS().getTravelItinerary(); 
			Customer cust = ti.getCustomerInfos().getCustomerInfo().getCustomer();
			
			aero.nettracer.serviceprovider.ws_1_0.common.xsd.Reservation res = response.addNewReservation();
			
			
			PersonName[] pns = cust.getPersonNameArray();
			CustLoyalty[] custLoyalty = cust.getCustLoyaltyArray();			
			Email[] email = cust.getEmailArray();
			Telephone[] telephones = cust.getTelephoneArray();
			ItemType[] items = ti.getItineraryInfo().getReservationItems().getItemArray();
			
			for (ItemType item: items) {
				Air[] airs = item.getAirArray();
				for (Air air: airs) {
					Itinerary itin = res.addNewPassengerItinerary();
					itin.setDepartureCity(air.getDepartureAirport().getLocationCode());
					itin.setArrivalCity(air.getArrivalAirport().getLocationCode());
					itin.setFlightnum(air.getFlightNumber());
					String depTime = air.getDepartureDateTime();
					String arrTime = air.getArrivalDateTime();
					
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
					Calendar schdeparttime = new GregorianCalendar();
					Calendar scharrivetime = new GregorianCalendar();
					
					schdeparttime.setTime(sdf.parse(depTime));
					scharrivetime.setTime(sdf.parse(arrTime));
					
					itin.setSchdeparttime(schdeparttime);
					itin.setScharrivetime(scharrivetime);
					itin.setAirline(air.getMarketingAirline().getCode());
					air.getArrivalDateTime();
					
				}
			}

			res.setPaxAffected(pns.length);
			Passenger firstPax = null;
			
			for (int i=0; i<pns.length; ++i) {
				PersonName pn = pns[i];
				Passenger pax = res.addNewPassengers();
				String paxKey = pn.getTPAExtensions().getNameNumber().getNumber();
				paxMap.put(paxKey, pax);
				if (firstPax == null) {
					firstPax = pax;
				}

				pax.setFirstname(pn.getGivenName());
				pax.setLastname(pn.getSurname());
				aero.nettracer.serviceprovider.ws_1_0.common.xsd.Address add = pax.addNewAddresses();
				
				if (i == 0) {
					Address padd = cust.getAddress();
					String[] lines = padd.getAddressLineArray();
					
					String x = StringUtils.join(lines, " ");
					if (x.length() > 0) {
						x = x.trim();
						
						if (x.length() > 49) {
							ArrayList<String> al = ServiceUtilities.splitOnWordBreak(x, 49);
							add.setAddress1(al.get(0));
							if (al.size() > 1) {
								add.setAddress2(al.get(1));
							}
						} else {
							add.setAddress1(x);
						}
					}

				}
			}
			
			for (int i=0; i< telephones.length; ++i) {
				Telephone tel = telephones[i];
				switch (i) {
					case 0: firstPax.getAddresses().setHomePhone(tel.getPhoneNumber());
							break;
					case 1: firstPax.getAddresses().setMobilePhone(tel.getPhoneNumber());
							break;
					case 2: firstPax.getAddresses().setAltPhone(tel.getPhoneNumber());
							break;
					case 3: firstPax.getAddresses().setWorkPhone(tel.getPhoneNumber());
							break;
				}
			}

			
			for (CustLoyalty custL: custLoyalty) {
				Passenger pax = paxMap.get(custL.getNameNumber());
				pax.setFfAirline(custL.getProgramID());
				pax.setFfNumber(custL.getMembershipID());
				pax.setFfStatus(custL.getShortText());
			}
			
			for (Email e: email) {
				Passenger pax = paxMap.get(e.getNameNumber());
				aero.nettracer.serviceprovider.ws_1_0.common.xsd.Address a = pax.getAddresses();
				a.setEmailAddress(e.getStringValue());
			}
			
			
			res.setPnr(doc.getOTATravelItineraryRS().getTravelItinerary().getItineraryRef().getID());
			
			res.setNilOsi();
			
			// TODO: Need to get claim checks & associated book dates.
			//String command = "";
			//genericCommand(connParams, command);

			int numberChecked = 0;
			int checkedLocation = 0;
			res.setNumberChecked(numberChecked);
			res.setCheckedLocation(checkedLocation);

		} catch (Exception e) {
			e.printStackTrace();
			throw new UnexpectedException();
		} finally {
			try {
				pool.returnObject(connParams);
			} catch (Exception e) {
				logger.error("Error: ", e);
				response.addNewError().setDescription(ServiceConstants.UNEXPECTED_EXCEPTION);
			}
		}
		return response;
	}

	@Override
	public RemarkResponse writeRemark(User user, String pnr,
			String remark) throws UnexpectedException {
		RemarkResponse response = RemarkResponse.Factory.newInstance();

		SabrePool pool = SabrePoolManager.getInstance().getPool(user);
		SabreConnection connParams = null;

		try {
			connParams = (SabreConnection) pool.borrowObject();

			loadPnr(connParams, pnr, false);
			addRemark(connParams, remark);
			endTransaction(connParams);


		} catch (Exception e) {
			e.printStackTrace();
			throw new UnexpectedException();
		} finally {
			try {
				pool.returnObject(connParams);
			} catch (Exception e) {
				logger.error("Error: ", e);
				response.addNewError().setDescription(ServiceConstants.UNEXPECTED_EXCEPTION);
			}
		}
		return response;
	}

	private void genericCommand(
			SabreConnection connParams, String command) throws RemoteException {
		try {
			
			SabreCommandLLSServiceStub stub = new SabreCommandLLSServiceStub(null,
					connParams.getEndpoint());

			SecurityDocument securityDocument = getSecurityDocument(connParams);
			MessageHeaderDocument mhDoc = getMessageHeader(connParams, ACTION_GENERIC_RQ);
			SabreCommandLLSRQDocument rqDoc = SabreCommandLLSRQDocument.Factory
					.newInstance();
			rqDoc.addNewSabreCommandLLSRQ().addNewRequest().setHostCommand(command);

			logger.info(NATIVE_COMMAND + connParams.getLoggingString() + " Command: " + command);

			SabreCommandLLSRSDocument responseDocument =stub.sabreCommandLLSRQ(rqDoc,
					mhDoc, securityDocument);

			logger.info(NATIVE_COMMAND + responseDocument.toString());

		} catch (RemoteException e) {
			logger.error(NATIVE_COMMAND + "Error creating session: ", e);
			throw e;
		}

	}

	private void addRemark(SabreConnection connParams,
			String remark) throws RemoteException {
		try {
			AddRemarkServiceStub stub = new AddRemarkServiceStub(null,
					connParams.getEndpoint());

			SecurityDocument securityDocument = getSecurityDocument(connParams);
			MessageHeaderDocument mhDoc = getMessageHeader(connParams, ACTION_ADD_REMARK_RQ);
			AddRemarkRQDocument rqDoc = AddRemarkRQDocument.Factory
					.newInstance();
			BasicRemark br = rqDoc.addNewAddRemarkRQ().addNewBasicRemark();
			br.setText(remark);

			logger.info(ADD_REMARK + connParams.getLoggingString() + " Remark: " + remark);

			AddRemarkRSDocument responseDocument = stub.addRemarkRQ(rqDoc,
					mhDoc, securityDocument);

			logger.info(ADD_REMARK + responseDocument.toString());

		} catch (RemoteException e) {
			logger.error(ADD_REMARK + "Error creating session: ", e);
			throw e;
		}
	}

	private void endTransaction(SabreConnection connParams)
			throws RemoteException {
		try {

			EndTransactionServiceStub stub = new EndTransactionServiceStub(
					null, connParams.getEndpoint());

			SecurityDocument securityDocument = getSecurityDocument(connParams);
			MessageHeaderDocument mhDoc = getMessageHeader(connParams, ACTION_END_RQ);
			EndTransactionRQDocument rqDoc = EndTransactionRQDocument.Factory
					.newInstance();
			EndTransactionRQ rqDoc1 = rqDoc.addNewEndTransactionRQ();

			logger.info(END_TRANSACTION + connParams.getLoggingString());

			EndTransactionRSDocument responseDocument = stub.endTransactionRQ(
					rqDoc, mhDoc, securityDocument);

			logger.info(END_TRANSACTION + responseDocument.toString());

		} catch (RemoteException e) {
			logger.error(END_TRANSACTION + "Error creating session: ", e);
			throw e;
		}
	}

	public static void ignoreTransaction(
			SabreConnection connParams) throws RemoteException {

		try {

			IgnoreTransactionServiceStub stub = new IgnoreTransactionServiceStub(
					null, connParams.getEndpoint());

			SecurityDocument securityDocument = getSecurityDocument(connParams);
			MessageHeaderDocument mhDoc = getMessageHeader(connParams, ACTION_IGNORE_RQ);
			IgnoreTransactionRQDocument rqDoc = IgnoreTransactionRQDocument.Factory
					.newInstance();
			IgnoreTransactionRQ rqDoc1 = rqDoc.addNewIgnoreTransactionRQ();


			rqDoc1.addNewIgnoreTransaction().setInd(true);

			logger.info(IGNORE_TRANSACTION + connParams.getLoggingString());

			IgnoreTransactionRSDocument responseDocument = stub
					.ignoreTransactionRQ(rqDoc, mhDoc, securityDocument);

			logger.info(IGNORE_TRANSACTION + responseDocument.toString());

		} catch (RemoteException e) {
			logger.error(IGNORE_TRANSACTION + "Error creating session: ", e);
			throw e;
		}

	}

	private OTATravelItineraryRSDocument loadPnr(
			SabreConnection connParams, String pnr, boolean getData) throws RemoteException {
		try {

			OTA_TravelItineraryServiceStub stub = new OTA_TravelItineraryServiceStub(
					null, connParams.getEndpoint());

			SecurityDocument securityDocument = getSecurityDocument(connParams);
			MessageHeaderDocument mhDoc = getMessageHeader(connParams, ACTION_TRAVEL_ITINERARY_RQ);
			OTATravelItineraryReadRQDocument rqDoc = OTATravelItineraryReadRQDocument.Factory
					.newInstance();
			OTATravelItineraryReadRQ readRq = rqDoc
					.addNewOTATravelItineraryReadRQ();
			UniqueID id = readRq.addNewUniqueID();
			id.setID(pnr);


			logger.info(LOAD_PNR_NO_DATA + connParams.getLoggingString());

			OTATravelItineraryRSDocument responseDocument = stub
					.oTA_TravelItineraryReadRQ(rqDoc, mhDoc, securityDocument);

			logger.info(LOAD_PNR_NO_DATA + responseDocument.toString());
			
			return responseDocument;

		} catch (RemoteException e) {
			logger.error(LOAD_PNR_NO_DATA + "Error creating session: ", e);
			throw e;
		}
	}

	public static void closeSession(SabreConnection connParams)
			throws RemoteException {
		try {

			SessionCloseRQServiceStub stub = new SessionCloseRQServiceStub(
					null, connParams.getEndpoint());

			SecurityDocument securityDocument = getSecurityDocument(connParams);
			MessageHeaderDocument mhDoc = getMessageHeader(connParams, ACTION_SESSION_CLOSE_RQ);
			SessionCloseRQDocument rqDoc = SessionCloseRQDocument.Factory
					.newInstance();

			logger.info(CLOSE_PREFIX + connParams.getLoggingString());
			SessionCloseRSDocument responseDocument = stub.sessionCloseRQ(
					rqDoc, mhDoc, securityDocument);

			logger.info(CLOSE_PREFIX + responseDocument.toString());

		} catch (RemoteException e) {
			logger.error(CLOSE_PREFIX + "Error closing session: ", e);
			throw e;
		}
	}

	public static void validateSession(
			SabreConnection connParams) throws RemoteException {
		try {

			SessionValidateRQServiceStub stub = new SessionValidateRQServiceStub(
					null, connParams.getEndpoint());

			SecurityDocument securityDocument = getSecurityDocument(connParams);
			MessageHeaderDocument mhDoc = getMessageHeader(connParams, ACTION_SESSION_VALIDATE_RQ);
			SessionValidateRQDocument rqDoc = SessionValidateRQDocument.Factory
					.newInstance();

			logger.info(VALIDATE_SESSION + connParams.getLoggingString());
			SessionValidateRSDocument responseDocument = stub
					.sessionValidateRQ(rqDoc, mhDoc, securityDocument);
			

			logger.info(VALIDATE_SESSION + responseDocument.toString());

		} catch (RemoteException e) {
			logger.error(VALIDATE_SESSION + "Error closing session: ", e);
			throw e;
		}
	}

	public static void createSession(SabreConnection connParams)
			throws RemoteException {
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
			MessageHeaderDocument mhDoc = getMessageHeader(connParams, ACTION_SESSION_CREATE_RQ);
			rqDoc.addNewSessionCreateRQ();

			logger.info(CREATE_PREFIX + connParams.getLoggingString());

			SessionCreateRSDocument responseDocument = stub.sessionCreateRQ(
					rqDoc, mhDoc, securityDocument);
			logger.debug(CREATE_PREFIX + responseDocument.toString());
			
			
			String status = responseDocument.getSessionCreateRS().getStatus();
			String binarySecurityToken = stub.getBinarySecurityToken();
			connParams.setBinarySecurityToken(binarySecurityToken);

			logger.debug(CREATE_PREFIX + responseDocument.toString());
			logger.info(CREATE_PREFIX + "Status Received: "
					+ status);
			logger.info(CREATE_PREFIX + "Binary Security Token Received: "
					+ binarySecurityToken);

		} catch (RemoteException e) {
			logger.error(CREATE_PREFIX + "Error creating session: ", e);
			throw e;
		}
	}

	private static SecurityDocument getSecurityDocument(SabreConnection connParams) {
		SecurityDocument securityDocument = SecurityDocument.Factory
				.newInstance();

		Security s = securityDocument.addNewSecurity();
		s.setBinarySecurityToken(connParams.getBinarySecurityToken());
		UsernameToken token = s.addNewUsernameToken();
		token.setUsername(connParams.getUsername());
		token.setOrganization(connParams.getOrganization());
		token.setPassword(connParams.getPassword());
		token.setDomain(connParams.getDomain());
		return securityDocument;
	}
	
	private static MessageHeaderDocument getMessageHeader(
			SabreConnection connParams, String action) {
		MessageHeaderDocument mhDoc = MessageHeaderDocument.Factory.newInstance();
		MessageHeader mh = mhDoc.addNewMessageHeader();
		mh.addNewFrom().setPartyIdArray(0, basicPartyId);
		mh.addNewTo().setPartyIdArray(0, basicPartyId);			
		mh.setConversationId(connParams.getConversation());
		mh.setAction(action);
		return mhDoc;
	}
}
