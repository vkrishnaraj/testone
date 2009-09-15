package aero.nettracer.serviceprovider.ws_1_0.res.sabre;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.axis2.engine.AxisConfiguration;
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
import aero.nettracer.serviceprovider.ws_1_0.common.xsd.ClaimCheck;
import aero.nettracer.serviceprovider.ws_1_0.common.xsd.Itinerary;
import aero.nettracer.serviceprovider.ws_1_0.common.xsd.Passenger;
import aero.nettracer.serviceprovider.ws_1_0.res.ReservationInterface;
import aero.nettracer.serviceprovider.ws_1_0.res.sabre.dto.SabreParseDTO;
import aero.nettracer.serviceprovider.ws_1_0.res.sabre.dto.SabreParsedBagItin;
import aero.nettracer.serviceprovider.ws_1_0.res.sabre.dto.SabreParsedBags;
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

	private static Logger logger = Logger.getLogger(Reservation.class);

	@Override
	public EnplanementResponse getEnplanements(User user) {
		return null;
	}

	@Override
	public OsiResponse getOsiContents(User user, String pnr, String bagTag) {
		return null;
	}

	@Override
	public ReservationResponse getReservationData(User user, String pnr,
			String bagTag) throws UnexpectedException {

		ReservationResponse response = ReservationResponse.Factory
				.newInstance();
		HashMap<String, Passenger> paxMap = new HashMap<String, Passenger>();

		SabrePool pool = SabrePoolManager.getInstance().getPool(user);
		SabreConnection connParams = null;

		try {
			connParams = (SabreConnection) pool.borrowObject();

			if (pnr == null && bagTag != null) {
				throw new RuntimeException(
						"This feature not capable of implementation.");
			}

			OTATravelItineraryRSDocument doc = loadPnr(connParams, pnr, false);
			TravelItinerary ti = doc.getOTATravelItineraryRS()
					.getTravelItinerary();
			Customer cust = ti.getCustomerInfos().getCustomerInfo()
					.getCustomer();

			aero.nettracer.serviceprovider.ws_1_0.common.xsd.Reservation res = response
					.addNewReservation();

			PersonName[] pns = cust.getPersonNameArray();
			CustLoyalty[] custLoyalty = cust.getCustLoyaltyArray();
			Email[] email = cust.getEmailArray();
			Telephone[] telephones = cust.getTelephoneArray();
			ItemType[] items = ti.getItineraryInfo().getReservationItems()
					.getItemArray();

			for (ItemType item : items) {
				Air[] airs = item.getAirArray();
				for (Air air : airs) {
					Itinerary itin = res.addNewPassengerItinerary();
					itin.setDepartureCity(air.getDepartureAirport()
							.getLocationCode());
					itin.setArrivalCity(air.getArrivalAirport()
							.getLocationCode());
					itin.setFlightnum(air.getFlightNumber());
					String depTime = air.getDepartureDateTime();
					String arrTime = air.getArrivalDateTime();

					SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyy-MM-dd'T'HH:mm:ss");
					Calendar schdeparttime = new GregorianCalendar();
					Calendar scharrivetime = new GregorianCalendar();

					schdeparttime.setTime(sdf.parse(depTime));
					scharrivetime.setTime(sdf.parse(arrTime));

					itin.setSchdeparttime(schdeparttime);
					itin.setScharrivetime(scharrivetime);
					if (air.getMarketingAirline() != null) {
						itin.setAirline(air.getMarketingAirline().getCode());
					} else if (air.getOperatingAirline() != null) {
						itin.setAirline(air.getOperatingAirline().getCode());
					}
					air.getArrivalDateTime();

				}
			}

			res.setPaxAffected(pns.length);
			Passenger firstPax = null;

			for (int i = 0; i < pns.length; ++i) {
				PersonName pn = pns[i];
				Passenger pax = res.addNewPassengers();
				String paxKey = pn.getTPAExtensions().getNameNumber()
						.getNumber();
				paxMap.put(paxKey, pax);
				if (firstPax == null) {
					firstPax = pax;
				}

				pax.setFirstname(pn.getGivenName());
				pax.setLastname(pn.getSurname());
				aero.nettracer.serviceprovider.ws_1_0.common.xsd.Address add = pax
						.addNewAddresses();

				if (i == 0) {
					if (cust.getAddress() != null) {
						Address padd = cust.getAddress();
						String[] lines = padd.getAddressLineArray();

						String x = StringUtils.join(lines, " ");
						if (x.length() > 0) {
							x = x.trim();

							if (x.length() > 49) {
								ArrayList<String> al = ServiceUtilities
										.splitOnWordBreak(x, 49);
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
			}
			
			ArrayList<String> numbersLeftToAdd = new ArrayList<String>();
			Pattern p = Pattern.compile("([0-9\\-]*)(-[HCBFA])");
			
			
			for (int i = 0; i < telephones.length; ++i) {
				Telephone tel = telephones[i];
				
				String phone = tel.getPhoneNumber();
				String phoneText = phone;
				Matcher m = p.matcher(phone);
				
				if (m.find()) {
					phoneText = m.group(1);
				} 
				
				if (phone.contains("-H")) {
					firstPax.getAddresses().setHomePhone(phoneText);
				} else if (phone.contains("-C")) {
					firstPax.getAddresses().setMobilePhone(phoneText);
				}else if (phone.contains("-B")) {
					firstPax.getAddresses().setWorkPhone(phoneText);
				}else if (phone.contains("-F")) {
					firstPax.getAddresses().setAltPhone(phoneText);
				}else if (phone.contains("-A")) {
					firstPax.getAddresses().setAltPhone(phoneText);
				} else {
					numbersLeftToAdd.add(phone);
				}
			}
			
			for (String phone : numbersLeftToAdd) {
				if (firstPax.getAddresses().getHomePhone() != null) {
					firstPax.getAddresses().setHomePhone(phone);
				} else if (firstPax.getAddresses().getMobilePhone() != null) {
					firstPax.getAddresses().setMobilePhone(phone);
				} else if (firstPax.getAddresses().getWorkPhone() != null) {
					firstPax.getAddresses().setWorkPhone(phone);
				} else if (firstPax.getAddresses().getAltPhone() != null) {
					firstPax.getAddresses().setAltPhone(phone);
				}
			}

			for (CustLoyalty custL : custLoyalty) {
				Passenger pax = paxMap.get(custL.getNameNumber());
				pax.setFfAirline(custL.getProgramID());
				pax.setFfNumber(custL.getMembershipID());
				pax.setFfStatus(custL.getShortText());
			}

			for (Email e : email) {
				Passenger pax = paxMap.get(e.getNameNumber());
				aero.nettracer.serviceprovider.ws_1_0.common.xsd.Address a = pax
						.getAddresses();
				a.setEmailAddress(e.getStringValue());
			}

			res.setPnr(doc.getOTATravelItineraryRS().getTravelItinerary()
					.getItineraryRef().getID());

			res.setNilOsi();

			
			String command = "*" + pnr;
			String commandString = genericCommand(connParams, command);
			SabreParseDTO dto = parseForBaggageInfo(commandString);
			
			int numberChecked = dto.getBags().size();
			
			
			for (SabreParsedBags bag: dto.getBags()) {
				ClaimCheck check = res.addNewClaimChecks();
				check.setAirline(bag.getCarrier());
				check.setTagNumber(bag.getTag());
				check.setTimeChecked(bag.getCheckedTime());
			}
			
			for (SabreParsedBagItin itin: dto.getItin()) {
				Itinerary i = res.addNewBagItinerary();
				i.setAirline(itin.getAirline());
				i.setArrivalCity(itin.getArrivalCity());
//				i.setCheckedTime(itin.getCheckedTime());
				i.setDepartureCity(itin.getDepartureCity());
				i.setFlightnum(itin.getFlight());
			}

			
			int checkedLocation = 0;
			if (numberChecked > 0) {
				res.setNumberChecked(numberChecked);
			}
			
			if (checkedLocation > 0) {
				res.setCheckedLocation(checkedLocation);
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error: ", e);
			throw new UnexpectedException();
		} finally {
			try {
				pool.returnObject(connParams);
			} catch (Exception e) {
				logger.error("Error: ", e);
				response.addNewError().setDescription(
						ServiceConstants.UNEXPECTED_EXCEPTION);
			}
		}
		return response;
	}

	@Override
	public RemarkResponse writeRemark(User user, String pnr, String remark)
			throws UnexpectedException {
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
				response.addNewError().setDescription(
						ServiceConstants.UNEXPECTED_EXCEPTION);
			}
		}
		return response;
	}

	public static SabreParseDTO parseForBaggageInfo(String string) {
			SabreParseDTO retVal = new SabreParseDTO();
			LinkedHashMap<String,SabreParsedBags> bagMap = new LinkedHashMap<String,SabreParsedBags>();
			LinkedHashMap<String,SabreParsedBagItin> itinMap = new LinkedHashMap<String,SabreParsedBagItin>();
			
			Pattern p1 = Pattern.compile("BAGGAGE INFORMATION((.*\\n)*)^([A-Z]{3}\\.)", Pattern.MULTILINE);
			Matcher m = p1.matcher(string);
			m.find();
			
			try {
				String[] sArray = m.group(1).split("\\n");
				String airline = null;
				String flight = null;
				
				for (String st: sArray) {
					SabreParsedBagItin itin = new SabreParsedBagItin();
					SabreParsedBags bag = new SabreParsedBags();
					
					Pattern rtPat = Pattern.compile("ROUTING-([A-Z0-9]{2})\\s*([0-9]{3,})\\s([A-Z]{3})");
					Pattern bagPat = Pattern.compile("\\s+([A-Z]{3})\\s+([A-Z0-9]{2})\\s+([0-9]{6})\\s-\\sBY\\s+([A-Z]{3})[A-Z0-9]{3,4}\\s([0-9]{4})/([0-9]{2})([A-Z]{3})([0-9]{2})");
					
					Matcher m1 = rtPat.matcher(st);
					Matcher m3 = bagPat.matcher(st);
					
					if (m1.find()) {
						System.out.println("Routing Line: " + m1.group());
						airline = m1.group(1);
						flight = m1.group(2);
		//				arrCity1 = m1.group(3);
						
		
					} else if (m3.find()) {
						System.out.println("Bag Line: " + m3.group());
		//				System.out.println(m3.group());
						String arrCity = m3.group(1);
						String bagAirline = m3.group(2);
						String bagTag = m3.group(2) + m3.group(3);
						String depCity = m3.group(4);
						String time = m3.group(5) + " " + m3.group(6) + " " + m3.group(7) + " " + m3.group(8);			
						GregorianCalendar checkedTime = new GregorianCalendar();
						
						try {
							SimpleDateFormat sdf = new SimpleDateFormat("HHmm dd MMM yy");
							checkedTime.setTime(sdf.parse(time));
							
						} catch (Exception e) {
							e.printStackTrace();
						}
						bag.setTag(bagTag);
						bag.setCheckedTime(checkedTime);
						bag.setCarrier(bagAirline);
						
						itin.setAirline(airline);
						itin.setArrivalCity(arrCity);
						itin.setCheckedTime(checkedTime);
						itin.setDepartureCity(depCity);
						itin.setFlight(flight);
						
						itinMap.put(itin.key(), itin);
						bagMap.put(bag.key(), bag);
					}
					
				}
			} catch (IllegalStateException e) {
				// Ignore
			}
			
			ArrayList<SabreParsedBags> bags = new ArrayList<SabreParsedBags>();
			ArrayList<SabreParsedBagItin> itins = new ArrayList<SabreParsedBagItin>();
			for (String key: bagMap.keySet()) {
				bags.add(bagMap.get(key));
			}
			
			for (String key: itinMap.keySet()) {
				itins.add(itinMap.get(key));
			}
			retVal.setBags(bags);
			retVal.setItin(itins);
			return retVal;
		}

	public static String genericCommand(SabreConnection connParams, String command)
			throws RemoteException {
		try {

			SabreCommandLLSServiceStub stub = new SabreCommandLLSServiceStub(
					null, connParams.getEndpoint());

			SecurityDocument securityDocument = getSecurityDocument(connParams);
			MessageHeaderDocument mhDoc = getMessageHeader(connParams,
					ACTION_GENERIC_RQ);
			SabreCommandLLSRQDocument rqDoc = SabreCommandLLSRQDocument.Factory
					.newInstance();
			rqDoc.addNewSabreCommandLLSRQ().addNewRequest().setHostCommand(
					command);

			logger.info(NATIVE_COMMAND + connParams.getLoggingString()
					+ " Command: " + command);

			SabreCommandLLSRSDocument responseDocument = stub
					.sabreCommandLLSRQ(rqDoc, mhDoc, securityDocument);

			logger.info(NATIVE_COMMAND + responseDocument.toString());
			return responseDocument.getSabreCommandLLSRS().getResponse();

		} catch (RemoteException e) {
			logger.error(NATIVE_COMMAND + "Error creating session: ", e);
			throw e;
		}
	}

	public static void addRemark(SabreConnection connParams, String remark)
			throws RemoteException {
		try {
			AddRemarkServiceStub stub = new AddRemarkServiceStub(null,
					connParams.getEndpoint());

			SecurityDocument securityDocument = getSecurityDocument(connParams);
			MessageHeaderDocument mhDoc = getMessageHeader(connParams,
					ACTION_ADD_REMARK_RQ);
			AddRemarkRQDocument rqDoc = AddRemarkRQDocument.Factory
					.newInstance();
			BasicRemark br = rqDoc.addNewAddRemarkRQ().addNewBasicRemark();
			br.setText(remark);

			logger.info(ADD_REMARK + connParams.getLoggingString()
					+ " Remark: " + remark);

			AddRemarkRSDocument responseDocument = stub.addRemarkRQ(rqDoc,
					mhDoc, securityDocument);

			logger.info(ADD_REMARK + responseDocument.toString());

		} catch (RemoteException e) {
			logger.error(ADD_REMARK + "Error creating session: ", e);
			throw e;
		}
	}

	public static void endTransaction(SabreConnection connParams)
			throws RemoteException {
		try {

			EndTransactionServiceStub stub = new EndTransactionServiceStub(
					null, connParams.getEndpoint());

			SecurityDocument securityDocument = getSecurityDocument(connParams);
			MessageHeaderDocument mhDoc = getMessageHeader(connParams,
					ACTION_END_RQ);
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

	public static void ignoreTransaction(SabreConnection connParams)
			throws RemoteException {

		try {

			IgnoreTransactionServiceStub stub = new IgnoreTransactionServiceStub(
					null, connParams.getEndpoint());

			SecurityDocument securityDocument = getSecurityDocument(connParams);
			MessageHeaderDocument mhDoc = getMessageHeader(connParams,
					ACTION_IGNORE_RQ);
			IgnoreTransactionRQDocument rqDoc = IgnoreTransactionRQDocument.Factory
					.newInstance();
			IgnoreTransactionRQ rqDoc1 = rqDoc.addNewIgnoreTransactionRQ();
			rqDoc1.setVersion("2003A.TsabreXML1.0.1");

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

	public static OTATravelItineraryRSDocument loadPnr(
			SabreConnection connParams, String pnr, boolean getData)
			throws RemoteException {
		try {

			OTA_TravelItineraryServiceStub stub = new OTA_TravelItineraryServiceStub(
					null, connParams.getEndpoint());

			SecurityDocument securityDocument = getSecurityDocument(connParams);
			MessageHeaderDocument mhDoc = getMessageHeader(connParams,
					ACTION_TRAVEL_ITINERARY_RQ);
			OTATravelItineraryReadRQDocument rqDoc = OTATravelItineraryReadRQDocument.Factory
					.newInstance();
			OTATravelItineraryReadRQ readRq = rqDoc
					.addNewOTATravelItineraryReadRQ();
			UniqueID id = readRq.addNewUniqueID();
			id.setID(pnr);

			OTATravelItineraryRSDocument responseDocument = null;

			if (!getData) {
				logger.info(LOAD_PNR_NO_DATA + connParams.getLoggingString());

				responseDocument = stub.oTA_TravelItineraryReadRQ(rqDoc, mhDoc,
						securityDocument);

				logger.info(LOAD_PNR_NO_DATA + responseDocument.toString());
			} else {

			}

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
			MessageHeaderDocument mhDoc = getMessageHeader(connParams,
					ACTION_SESSION_CLOSE_RQ);
			SessionCloseRQDocument rqDoc = SessionCloseRQDocument.Factory
					.newInstance();
			rqDoc.addNewSessionCloseRQ();

			logger.info(CLOSE_PREFIX + connParams.getLoggingString());
			SessionCloseRSDocument responseDocument = stub.sessionCloseRQ(
					rqDoc, mhDoc, securityDocument);

			logger.info(CLOSE_PREFIX + responseDocument.toString());

		} catch (RemoteException e) {
			logger.error(CLOSE_PREFIX + "Error closing session: ", e);
			throw e;
		}
	}

	public static void validateSession(SabreConnection connParams)
			throws RemoteException {
		try {

			SessionValidateRQServiceStub stub = new SessionValidateRQServiceStub(
					null, connParams.getEndpoint());

			SecurityDocument securityDocument = getSecurityDocument(connParams);
			MessageHeaderDocument mhDoc = getMessageHeader(connParams,
					ACTION_SESSION_VALIDATE_RQ);
			SessionValidateRQDocument rqDoc = SessionValidateRQDocument.Factory
					.newInstance();
			rqDoc.addNewSessionValidateRQ();

			logger.info(VALIDATE_SESSION + connParams.getLoggingString());
			SessionValidateRSDocument responseDocument = stub
					.sessionValidateRQ(rqDoc, mhDoc, securityDocument);

			logger.info(VALIDATE_SESSION + responseDocument.toString());

		} catch (RemoteException e) {
			if (!e.getMessage().contains(
					"The document is not a SessionValidateRS")) {
				logger.error("Unexpected Error Occurred", e);
				throw e;
			}

		}
	}

	public static void createSession(SabreConnection connParams)
			throws RemoteException {
		try {

			SessionCreateRQServiceStub2 stub = new SessionCreateRQServiceStub2(
					null, connParams.getEndpoint());
			AxisConfiguration ac = stub._getServiceClient()
					.getAxisConfiguration();
			ArrayList al = new ArrayList();
			al.add(new MustUnderstandHandler());
			ac.setInPhasesUptoAndIncludingPostDispatch(al);

			// stub._getServiceClient().engageModule("rampart");
			//
			// StAXOMBuilder builder;
			// try {
			// builder = new StAXOMBuilder("policy.xml");
			// Policy clientPolicy = PolicyEngine.getPolicy(builder
			// .getDocumentElement());
			// Options o = stub._getServiceClient().getOptions();
			// o.setProperty(RampartMessageData.KEY_RAMPART_POLICY,
			// clientPolicy);
			//
			// } catch (FileNotFoundException e) {
			//
			// e.printStackTrace();
			// } catch (XMLStreamException e) {
			// e.printStackTrace();
			// }

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
			MessageHeaderDocument mhDoc = getMessageHeader(connParams,
					ACTION_SESSION_CREATE_RQ);
			rqDoc.addNewSessionCreateRQ();

			logger.info(CREATE_PREFIX + connParams.getLoggingString());

			SessionCreateRSDocument responseDocument = stub.sessionCreateRQ(
					rqDoc, mhDoc, securityDocument);
			logger.debug(CREATE_PREFIX + responseDocument.toString());

			String status = responseDocument.getSessionCreateRS().getStatus();
			String binarySecurityToken = stub.getBinarySecurityToken();
			connParams.setBinarySecurityToken(binarySecurityToken);

			logger.debug(CREATE_PREFIX + responseDocument.toString());
			logger.info(CREATE_PREFIX + "Status Received: " + status);
			logger.info(CREATE_PREFIX + "Binary Security Token Received: "
					+ binarySecurityToken);

		} catch (RemoteException e) {
			logger.error(CREATE_PREFIX + "Error creating session: ", e);
			throw e;
		}
	}

	private static SecurityDocument getSecurityDocument(
			SabreConnection connParams) {
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
		MessageHeaderDocument mhDoc = MessageHeaderDocument.Factory
				.newInstance();
		MessageHeader mh = mhDoc.addNewMessageHeader();
		PartyId a = mh.addNewFrom().addNewPartyId();
		PartyId b = mh.addNewTo().addNewPartyId();
		a.setStringValue("1");
		b.setStringValue("1");
		mh.setConversationId(connParams.getConversation());
		mh.setAction(action);
		return mhDoc;
	}
}
