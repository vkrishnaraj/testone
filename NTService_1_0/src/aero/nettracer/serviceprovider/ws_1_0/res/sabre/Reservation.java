package aero.nettracer.serviceprovider.ws_1_0.res.sabre;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.axis2.engine.AxisConfiguration;
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
import com.sabre.webservices.sabrexml._2003._07.AddRemarkRQDocument.AddRemarkRQ;
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
import com.sabre.webservices.websvc.OTA_TravelItineraryServiceStub2;
import com.sabre.webservices.websvc.SabreCommandLLSServiceStub;
import com.sabre.webservices.websvc.SessionCloseRQServiceStub;
import com.sabre.webservices.websvc.SessionCreateRQServiceStub2;
import com.sabre.webservices.websvc.SessionValidateRQServiceStub;

public class Reservation implements ReservationInterface {

	/*
	 * Sabre Versions:
	 * AddRemarkRQ 				2003A.TsabreXML1.3.1
	 * EndTransactionRQ			2003A.TsabreXML1.4.1
	 * OTATravelItineraryReadRQ	2003A.TsabreXML1.14.1
	 * IgnoreTransactionRQ		2003A.TsabreXML1.0.1
	 * SabreCommandRQ			2003A.TsabreXML1.6.1
	 * 
	 * webservices.sabre.com - Developer Resource Center
	 * Username: bsmith@nettracer.aero
	 * Password: flash99
	 */
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
		String exceptionText = null;
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
			
			if (doc.getOTATravelItineraryRS().getErrors() != null && doc.getOTATravelItineraryRS().getErrors().getError() != null && doc.getOTATravelItineraryRS().getErrors().getError().getErrorMessage() != null) {
				exceptionText = doc.getOTATravelItineraryRS().getErrors().getError().getErrorMessage();
				throw new Exception();
			}
			
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
			
			if (ti.getItineraryInfo() != null && ti.getItineraryInfo().getReservationItems() != null) {
				ItemType[] items = ti.getItineraryInfo().getReservationItems().getItemArray();

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

						// Example:
						// <AddressLine>N/FRED PARKER</AddressLine>
						// <AddressLine>A/3045 JOHN F KENNDY</AddressLine>
						// <AddressLine>C/NEW YORK, NY</AddressLine>
						// <AddressLine>Z/10018</AddressLine>
						
						for (String line: lines) {
							if (line.startsWith("A/")) {
								add.setAddress1(line.substring(2));
							} else if (line.startsWith("C/")) {
								String[] arr = line.split(",");
								String city = arr[0].substring(2);
								add.setCity(city.trim());
								
								String state = "";
								try {
									state = arr[1];
								} catch (Exception e) {
									// Ignore exception.
									// State was unable to be parsed because there was no comma between city and state
									// Sample is below
									// <AddressLine>N/MIKE JOHNSON</AddressLine>
									// <AddressLine>A/123 MAIN
									// STREET</AddressLine>
									// <AddressLine>C/JERSEY CITY,
									// NJ</AddressLine>
									// <AddressLine>Z/07306</AddressLine>
								}
								add.setState(state.trim());
							} else if (line.startsWith("Z/")) {
								add.setZip(line.substring(2));
							}
						}
					}
				}
			}

			ArrayList<String> numbersLeftToAdd = new ArrayList<String>();
			Pattern p = Pattern.compile("([0-9\\-]*)(-[A-Z])");

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
				} else if (phone.contains("-B")) {
					firstPax.getAddresses().setWorkPhone(phoneText);
				} else if (phone.contains("-F")) {
					firstPax.getAddresses().setAltPhone(phoneText);
				} else if (phone.contains("-A")) {
					firstPax.getAddresses().setAltPhone(phoneText);
				} else {
					numbersLeftToAdd.add(phoneText);
				}
			}

			for (String phone : numbersLeftToAdd) {

				
				if (firstPax.getAddresses().getHomePhone() == null) {
					firstPax.getAddresses().setHomePhone(phone);
				} else if (firstPax.getAddresses().getMobilePhone() == null) {
					firstPax.getAddresses().setMobilePhone(phone);
				} else if (firstPax.getAddresses().getWorkPhone() == null) {
					firstPax.getAddresses().setWorkPhone(phone);
				} else if (firstPax.getAddresses().getAltPhone() == null) {
					firstPax.getAddresses().setAltPhone(phone);
				}
			}

			for (CustLoyalty custL : custLoyalty) {
				Passenger pax = paxMap.get(custL.getNameNumber());
				if (pax == null) {
					pax = firstPax;
				}
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

			for (SabreParsedBags bag : dto.getBags()) {
				ClaimCheck check = res.addNewClaimChecks();
				check.setAirline(bag.getCarrier());
				check.setTagNumber(bag.getTag());
				check.setTimeChecked(bag.getCheckedTime());
			}

			for (SabreParsedBagItin itin : dto.getItin()) {
				Itinerary i = res.addNewBagItinerary();
				i.setAirline(itin.getAirline());
				i.setArrivalCity(itin.getArrivalCity());
				i.setTimeChecked(itin.getCheckedTime());
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
			logger.error("Error: ", e);
			if (exceptionText != null) {
				if (exceptionText.contains("PNR LOCATOR NOT VALID")) {
					response.addNewError().setDescription(
							ServiceConstants.PNR_NOT_VALID);	
				} else {
					response.addNewError().setDescription(
							ServiceConstants.UNEXPECTED_EXCEPTION);
				}
				
			} else { 
				throw new UnexpectedException();
			}
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
		LinkedHashMap<String, SabreParsedBags> bagMap = new LinkedHashMap<String, SabreParsedBags>();
		LinkedHashMap<String, SabreParsedBagItin> itinMap = new LinkedHashMap<String, SabreParsedBagItin>();

		Pattern p1 = Pattern.compile(
				"BAGGAGE INFORMATION((.*\\n)*)^([A-Z]{3}\\.)",
				Pattern.MULTILINE);
		Matcher m = p1.matcher(string);
		m.find();

		try {
			String[] sArray = m.group(1).split("\\n");
			String airline = null;
			String flight = null;
			String arrCity1 = null;
			ArrayList<SabreParsedBagItin> currentItinList = new ArrayList<SabreParsedBagItin>();

			for (String st : sArray) {

				SabreParsedBags bag = new SabreParsedBags();

				// Pattern rtPat =
				// Pattern.compile("ROUTING-([A-Z0-9]{2})\\s*([0-9]{3,})\\s([A-Z]{3})");
				Pattern rtPat = Pattern
						.compile("ROUTING-[([A-Z0-9]{2})\\s*([0-9]{1,})\\s([A-Z]{3})/*]+");
				Pattern subRtPat = Pattern
						.compile("([A-Z0-9]{2})\\s*([0-9]{1,})\\s([A-Z]{3})/*\\s*+");
				Pattern bagPat = Pattern
						.compile("\\s+([A-Z]{3})\\s+([A-Z0-9]{2})\\s+([0-9]{6})\\s-\\sBY\\s+([A-Z]{3})[A-Z0-9]{3,4}\\s([0-9]{4})/([0-9]{2})([A-Z]{3})([0-9]{2})");

				// System.out.println("Line: " + st);

				Matcher m1 = rtPat.matcher(st);
				Matcher m3 = bagPat.matcher(st);

				if (m1.find()) {

					Matcher sub = subRtPat.matcher(m1.group());

					// System.out.println("Routing Line: " + m1.group());
					int itinNum = 0;
					while (sub.find()) {

						SabreParsedBagItin itin = new SabreParsedBagItin();
						// System.out.println("Sub Routing: " + sub.group());
						airline = sub.group(1);
						flight = sub.group(2);
						arrCity1 = sub.group(3);

						itin.setAirline(airline);
						itin.setArrivalCity(arrCity1);
						itin.setFlight(flight);

						if (itinNum > 0) {
							itin.setDepartureCity(currentItinList.get(
									itinNum - 1).getArrivalCity());
						}
						currentItinList.add(itin);
						++itinNum;

					}

				} else if (m3.find()) {
					// System.out.println("Bag Line: " + m3.group());
					// System.out.println(m3.group());
					String arrCity = m3.group(1);
					String bagAirline = m3.group(2);
					String bagTag = m3.group(2) + m3.group(3);
					String depCity = m3.group(4);
					String time = m3.group(5) + " " + m3.group(6) + " "
							+ m3.group(7) + " " + m3.group(8);
					GregorianCalendar checkedTime = new GregorianCalendar();

					try {
						SimpleDateFormat sdf = new SimpleDateFormat(
								"HHmm dd MMM yy");
						checkedTime.setTime(sdf.parse(time));

					} catch (Exception e) {
						logger.error("Error: ", e);
					}
					bag.setTag(bagTag);
					bag.setCheckedTime(checkedTime);
					bag.setCarrier(bagAirline);

					for (int i = 0; i < currentItinList.size(); ++i) {
						SabreParsedBagItin itin = currentItinList.get(i);

						if (itin.getDepartureCity() == null) {
							itin.setDepartureCity(depCity);
						}

						if (itin.getCheckedTime() == null
								|| itin.getCheckedTime().compareTo(checkedTime) < 0) {
							itin.setCheckedTime(checkedTime);
						}
						itinMap.put(itin.key(), itin);
					}

					bagMap.put(bag.key(), bag);
				}

			}
		} catch (IllegalStateException e) {
			// Ignore
		}

		ArrayList<SabreParsedBags> bags = new ArrayList<SabreParsedBags>();
		ArrayList<SabreParsedBagItin> itins = new ArrayList<SabreParsedBagItin>();
		for (String key : bagMap.keySet()) {
			bags.add(bagMap.get(key));
		}

		for (String key : itinMap.keySet()) {
			itins.add(itinMap.get(key));
		}
		retVal.setBags(bags);
		retVal.setItin(itins);
		return retVal;
	}

	public static String genericCommand(SabreConnection connParams,
			String command) throws RemoteException {
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
			rqDoc.getSabreCommandLLSRQ().setVersion("2003A.TsabreXML1.6.1");
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
			AddRemarkRQ rq = rqDoc.addNewAddRemarkRQ();
			rq.setVersion("2003A.TsabreXML1.3.1");
			BasicRemark br = rq.addNewBasicRemark();
			String remarkText = remark;
			remarkText = remarkText.replaceAll("[(),]", "");
			remarkText = remarkText.replaceAll("[:]", "-");
			
			ArrayList<String> list = ServiceUtilities.splitOnWordBreak(remarkText, 58);
			for (String str: list) {
				br.setText(str);
				
				logger.info(ADD_REMARK + connParams.getLoggingString()
						+ " Remark: " + remark);

				AddRemarkRSDocument responseDocument = stub.addRemarkRQ(rqDoc,
						mhDoc, securityDocument);

				logger.info(ADD_REMARK + responseDocument.toString());
			}
			
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
			rqDoc1.setVersion("2003A.TsabreXML1.4.1");
			rqDoc1.addNewUpdatedBy().addNewTPAExtensions().addNewAccess().addNewAccessPerson().setGivenName("NETTRACER");
			rqDoc1.addNewEndTransaction().setInd(true);

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
			logger
					.error(IGNORE_TRANSACTION + "Error ignoring transaction: ",
							e);
		}

	}

	public static OTATravelItineraryRSDocument loadPnr(
			SabreConnection connParams, String pnr, boolean getData)
			throws RemoteException {
		try {

			OTA_TravelItineraryServiceStub2 stub = new OTA_TravelItineraryServiceStub2(
					null, connParams.getEndpoint());

			SecurityDocument securityDocument = getSecurityDocument(connParams);
			MessageHeaderDocument mhDoc = getMessageHeader(connParams,
					ACTION_TRAVEL_ITINERARY_RQ, "Travel");
			
			
			OTATravelItineraryReadRQDocument rqDoc = OTATravelItineraryReadRQDocument.Factory
					.newInstance();
			OTATravelItineraryReadRQ readRq = rqDoc.addNewOTATravelItineraryReadRQ();
			UniqueID id = readRq.addNewUniqueID();
			id.setID(pnr);
			readRq.setVersion("2003A.TsabreXML1.14.1");

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
			
			List al = ac.getInFlowPhases();
			List al2 = ac.getInFaultFlowPhases();
			if (al.size() > 0 && !(al.get(0) instanceof MustUnderstandHandler)) {
				logger.info("Created MustUnderstandHandler");
				MustUnderstandHandler mu = new MustUnderstandHandler();
				al.add(0, mu);
				al2.add(0, mu);
				ac.setInPhasesUptoAndIncludingPostDispatch(al);
				ac.setInFaultPhases(al2);
			}

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
		return getMessageHeader(connParams, action, null);
	}
	
	private static MessageHeaderDocument getMessageHeader(
			SabreConnection connParams, String action, String service) {
		MessageHeaderDocument mhDoc = MessageHeaderDocument.Factory
				.newInstance();
		MessageHeader mh = mhDoc.addNewMessageHeader();
		PartyId a = mh.addNewFrom().addNewPartyId();
		PartyId b = mh.addNewTo().addNewPartyId();
		a.setStringValue("1");
		b.setStringValue("1");
		mh.setConversationId(connParams.getConversation());
		if (service != null) { 
			mh.addNewService().setStringValue(service);
		} else {
			mh.addNewService().setStringValue("");
		}
		mh.setAction(action);
		return mhDoc;
	}
}
