package com.bagnet.nettracer.wt.connector;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.axis2.AxisFault;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.log4j.Logger;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;

import aero.nettracer.serviceprovider.ws_1_0.common.Parameter;
import aero.nettracer.serviceprovider.ws_1_0.common.RequestHeader;
import aero.nettracer.serviceprovider.ws_1_0.common.xsd.WebServiceError;
import aero.nettracer.serviceprovider.wt_1_0.AmendAhlDocument;
import aero.nettracer.serviceprovider.wt_1_0.AmendAhlResponseDocument;
import aero.nettracer.serviceprovider.wt_1_0.AmendOhdDocument;
import aero.nettracer.serviceprovider.wt_1_0.AmendOhdResponseDocument;
import aero.nettracer.serviceprovider.wt_1_0.CloseAhlDocument;
import aero.nettracer.serviceprovider.wt_1_0.CloseAhlResponseDocument;
import aero.nettracer.serviceprovider.wt_1_0.CloseOhdDocument;
import aero.nettracer.serviceprovider.wt_1_0.CloseOhdResponseDocument;
import aero.nettracer.serviceprovider.wt_1_0.CreateAhlDocument;
import aero.nettracer.serviceprovider.wt_1_0.CreateAhlResponseDocument;
import aero.nettracer.serviceprovider.wt_1_0.CreateBdoDocument;
import aero.nettracer.serviceprovider.wt_1_0.CreateBdoResponseDocument;
import aero.nettracer.serviceprovider.wt_1_0.CreateOhdDocument;
import aero.nettracer.serviceprovider.wt_1_0.CreateOhdResponseDocument;
import aero.nettracer.serviceprovider.wt_1_0.EraseActionFileDocument;
import aero.nettracer.serviceprovider.wt_1_0.EraseActionFileResponseDocument;
import aero.nettracer.serviceprovider.wt_1_0.EstablishWtrConnectionDocument;
import aero.nettracer.serviceprovider.wt_1_0.EstablishWtrConnectionResponseDocument;
import aero.nettracer.serviceprovider.wt_1_0.ForwardOhdDocument;
import aero.nettracer.serviceprovider.wt_1_0.ForwardOhdResponseDocument;
import aero.nettracer.serviceprovider.wt_1_0.GetActionFileCountsDocument;
import aero.nettracer.serviceprovider.wt_1_0.GetActionFileCountsResponseDocument;
import aero.nettracer.serviceprovider.wt_1_0.GetActionFileSummaryDocument;
import aero.nettracer.serviceprovider.wt_1_0.GetActionFileSummaryResponseDocument;
import aero.nettracer.serviceprovider.wt_1_0.GetAhlDocument;
import aero.nettracer.serviceprovider.wt_1_0.GetAhlResponseDocument;
import aero.nettracer.serviceprovider.wt_1_0.GetOhdDocument;
import aero.nettracer.serviceprovider.wt_1_0.GetOhdResponseDocument;
import aero.nettracer.serviceprovider.wt_1_0.PlaceActionFileDocument;
import aero.nettracer.serviceprovider.wt_1_0.PlaceActionFileResponseDocument;
import aero.nettracer.serviceprovider.wt_1_0.ReinstateAhlDocument;
import aero.nettracer.serviceprovider.wt_1_0.ReinstateAhlResponseDocument;
import aero.nettracer.serviceprovider.wt_1_0.ReinstateOhdDocument;
import aero.nettracer.serviceprovider.wt_1_0.ReinstateOhdResponseDocument;
import aero.nettracer.serviceprovider.wt_1_0.RequestOhdDocument;
import aero.nettracer.serviceprovider.wt_1_0.RequestOhdResponseDocument;
import aero.nettracer.serviceprovider.wt_1_0.RequestQuickOhdDocument;
import aero.nettracer.serviceprovider.wt_1_0.RequestQuickOhdResponseDocument;
import aero.nettracer.serviceprovider.wt_1_0.SendForwardMessageDocument;
import aero.nettracer.serviceprovider.wt_1_0.SendForwardMessageResponseDocument;
import aero.nettracer.serviceprovider.wt_1_0.SendQohDocument;
import aero.nettracer.serviceprovider.wt_1_0.SendQohResponseDocument;
import aero.nettracer.serviceprovider.wt_1_0.SuspendAhlDocument;
import aero.nettracer.serviceprovider.wt_1_0.SuspendAhlResponseDocument;
import aero.nettracer.serviceprovider.wt_1_0.SuspendOhdDocument;
import aero.nettracer.serviceprovider.wt_1_0.SuspendOhdResponseDocument;
import aero.nettracer.serviceprovider.wt_1_0.WorldTracerServiceStub;
import aero.nettracer.serviceprovider.wt_1_0.AmendAhlDocument.AmendAhl;
import aero.nettracer.serviceprovider.wt_1_0.AmendOhdDocument.AmendOhd;
import aero.nettracer.serviceprovider.wt_1_0.CloseAhlDocument.CloseAhl;
import aero.nettracer.serviceprovider.wt_1_0.CloseOhdDocument.CloseOhd;
import aero.nettracer.serviceprovider.wt_1_0.CreateAhlDocument.CreateAhl;
import aero.nettracer.serviceprovider.wt_1_0.CreateBdoDocument.CreateBdo;
import aero.nettracer.serviceprovider.wt_1_0.CreateOhdDocument.CreateOhd;
import aero.nettracer.serviceprovider.wt_1_0.EraseActionFileDocument.EraseActionFile;
import aero.nettracer.serviceprovider.wt_1_0.EstablishWtrConnectionDocument.EstablishWtrConnection;
import aero.nettracer.serviceprovider.wt_1_0.GetActionFileCountsDocument.GetActionFileCounts;
import aero.nettracer.serviceprovider.wt_1_0.GetActionFileSummaryDocument.GetActionFileSummary;
import aero.nettracer.serviceprovider.wt_1_0.GetAhlDocument.GetAhl;
import aero.nettracer.serviceprovider.wt_1_0.GetOhdDocument.GetOhd;
import aero.nettracer.serviceprovider.wt_1_0.PlaceActionFileDocument.PlaceActionFile;
import aero.nettracer.serviceprovider.wt_1_0.ReinstateAhlDocument.ReinstateAhl;
import aero.nettracer.serviceprovider.wt_1_0.ReinstateOhdDocument.ReinstateOhd;
import aero.nettracer.serviceprovider.wt_1_0.RequestOhdDocument.RequestOhd;
import aero.nettracer.serviceprovider.wt_1_0.RequestQuickOhdDocument.RequestQuickOhd;
import aero.nettracer.serviceprovider.wt_1_0.SendForwardMessageDocument.SendForwardMessage;
import aero.nettracer.serviceprovider.wt_1_0.SendQohDocument.SendQoh;
import aero.nettracer.serviceprovider.wt_1_0.SuspendAhlDocument.SuspendAhl;
import aero.nettracer.serviceprovider.wt_1_0.SuspendOhdDocument.SuspendOhd;
import aero.nettracer.serviceprovider.wt_1_0.common.ActionFileRequestData;
import aero.nettracer.serviceprovider.wt_1_0.common.Address;
import aero.nettracer.serviceprovider.wt_1_0.common.Agent;
import aero.nettracer.serviceprovider.wt_1_0.common.Ahl;
import aero.nettracer.serviceprovider.wt_1_0.common.Bdo;
import aero.nettracer.serviceprovider.wt_1_0.common.ClaimCheck;
import aero.nettracer.serviceprovider.wt_1_0.common.Content;
import aero.nettracer.serviceprovider.wt_1_0.common.Expenses;
import aero.nettracer.serviceprovider.wt_1_0.common.ForwardMessage;
import aero.nettracer.serviceprovider.wt_1_0.common.ForwardOhd;
import aero.nettracer.serviceprovider.wt_1_0.common.Itinerary;
import aero.nettracer.serviceprovider.wt_1_0.common.Ohd;
import aero.nettracer.serviceprovider.wt_1_0.common.Passenger;
import aero.nettracer.serviceprovider.wt_1_0.common.Pxf;
import aero.nettracer.serviceprovider.wt_1_0.common.PxfDetails;
import aero.nettracer.serviceprovider.wt_1_0.common.Qoh;
import aero.nettracer.serviceprovider.wt_1_0.common.Tag;
import aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFile;
import aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileCount;
import aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse;


import com.bagnet.nettracer.tracing.bmo.CategoryBMO;
import com.bagnet.nettracer.tracing.bmo.LossCodeBMO;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.AirlineMembership;
import com.bagnet.nettracer.tracing.db.BDO;
import com.bagnet.nettracer.tracing.db.Company_specific_irregularity_code;
import com.bagnet.nettracer.tracing.db.DeliverCompany;
import com.bagnet.nettracer.tracing.db.ExpensePayout;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Incident_Claimcheck;
import com.bagnet.nettracer.tracing.db.Item;
import com.bagnet.nettracer.tracing.db.Item_Inventory;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.OHD_Inventory;
import com.bagnet.nettracer.tracing.db.OHD_Itinerary;
import com.bagnet.nettracer.tracing.db.OHD_Passenger;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.WT_PXF;
import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles;
import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles.ActionFileType;
import com.bagnet.nettracer.tracing.db.wtq.WtqFwdGeneral;
import com.bagnet.nettracer.tracing.db.wtq.WtqFwdOhd;
import com.bagnet.nettracer.tracing.db.wtq.WtqQoh;
import com.bagnet.nettracer.tracing.db.wtq.WtqRequestOhd;
import com.bagnet.nettracer.tracing.db.wtq.WtqRequestPxf;
import com.bagnet.nettracer.tracing.db.wtq.WtqRequestQoh;
import com.bagnet.nettracer.tracing.db.wtq.WtqSegment;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.SpringUtils;
import com.bagnet.nettracer.wt.WorldTracerException;
import com.bagnet.nettracer.wt.WorldTracerRecordNotFoundException;
import com.bagnet.nettracer.wt.svc.BasicRule;
import com.bagnet.nettracer.wt.svc.WorldTracerRule;
import com.bagnet.nettracer.wt.svc.WorldTracerService.WorldTracerField;
import com.bagnet.nettracer.wt.utils.ActionFileDto;

public class WorldTracerWebService implements WorldTracerConnector {

	private static final String CONNECTION_ERROR_UNABLE_TO_PERFORM_ACTION = "Connection error... Unable to perform action...";

	private static WorldTracerWebService instance = null;

	private static String endpoint = PropertyBMO.getValue(PropertyBMO.PROPERTY_NT_CENTRAL_ENDPOINT);
	private static String username = PropertyBMO.getValue(PropertyBMO.PROPERTY_NT_CENTRAL_USERNAME);
	private static String password = PropertyBMO.getValue(PropertyBMO.PROPERTY_NT_CENTRAL_PASSWORD);

	public static final String USER_NOT_AUTHORIZED = "USER NOT AUTHORIZED";
	public static final String CONFIGURATION_ERROR = "CONFIGURATION ERROR";
	public static final String UNEXPECTED_EXCEPTION = "UNEXPECTED EXCEPTION ENCOUNTERED";
	public static final String COMMAND_NOT_PROPERLY_FORMATTED = "COMMAND NOT PROPERLY FORMATTED";
	public static final String CAPTCHA_EXCEPTION = "MUST ENTER CAPTCHA";
	public static final String RECORD_NOT_FOUND_EXCEPTION = "RECORD NOT FOUND";

	public static final String CAPTCHA_TEXT = "CAPTCHA_TEXT";
	public static final String CAPTCHA_TIMESTAMP = "CAPTCHA_TIMESTAMP";
	public static final String USE_AVAILABLE_CONNECTIONS_IF_POSSIBLE = "USE_AVAILABLE_CONNECTIONS_IF_POSSIBLE";
	public static final String CRON_USER = "CRON_USER";
	public static final String REFERENCED_OBJECT_CLOSED = "REFERENCED OBJECT ALREADY CLOSED";
	
	public static final int MAX_COUNTRY_LENGTH = 64;
	public static final int MAX_STATEPROV_LENGTH = 64;
	
	private static final Logger logger = Logger.getLogger(WorldTracerWebService.class);

	private static Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();

	public static WorldTracerWebService getInstance() {
		if (instance == null) {
			instance = new WorldTracerWebService();
		}
		return instance;
	}
	

	private WorldTracerServiceStub getConfiguredServiceStub() throws AxisFault {
		WorldTracerServiceStub stub = new WorldTracerServiceStub(endpoint);
		stub._getServiceClient().getOptions().setProperty(HTTPConstants.SO_TIMEOUT, new Integer(5 * 60 * 1000));
		stub._getServiceClient().getOptions().setProperty(HTTPConstants.CONNECTION_TIMEOUT, new Integer(5 * 60 * 1000));
		return stub;
	}

	private void processResponseAndUpdateDto(WebServiceDto dto, WorldTracerResponse response) throws CaptchaException {
		dto.reset();
		if (response.getConnectionRef() != null) {
			dto.setConnectionReference(response.getConnectionRef());
		}

		if (response.getError() != null) {
			WebServiceError error = response.getError();

			if (error.getDescription().contains(CAPTCHA_EXCEPTION)) {
				dto.setCaptcha(response.getCaptcha());
				dto.setCaptchaTimestamp(response.getCaptchaTimestamp());
				throw new CaptchaException();
			}
		}
	}

	private RequestHeader generateHeader(WebServiceDto dto) {
		RequestHeader header = new RequestHeader();
		header.setUsername(username);
		header.setPassword(password);
		ArrayList<Parameter> params = new ArrayList<Parameter>();

		if (dto.getConnectionReference() != null) {
			Parameter param = new Parameter();
			param.setName("WT_WTR_CONNECTION_KEY");
			param.setValue(dto.getConnectionReference());
			params.add(param);
		}

		if (dto.getCaptchaText() != null) {
			Parameter param = new Parameter();
			param.setName(CAPTCHA_TEXT);
			param.setValue(dto.getCaptchaText());
			params.add(param);

			Parameter param2 = new Parameter();
			param2.setName(CAPTCHA_TIMESTAMP);
			param2.setValue(dto.getCaptchaTimestamp());
			params.add(param2);
		}

		if (dto.isUseAvailableConnectionsIfAvailable()) {
			Parameter param = new Parameter();
			param.setName(USE_AVAILABLE_CONNECTIONS_IF_POSSIBLE);
			param.setValue("1");
			params.add(param);
		}

		if (dto.isCronUser()) {
			Parameter param = new Parameter();
			param.setName(CRON_USER);
			param.setValue("1");
			params.add(param);
		}

		header.setParameters(params.toArray(new Parameter[params.size()]));
		return header;
	}

	
	public boolean establishWtrConnection(WebServiceDto dto) throws WorldTracerException, CaptchaException {
		
		try {
			WorldTracerServiceStub stub = getConfiguredServiceStub();

			RequestHeader header = generateHeader(dto);
			EstablishWtrConnectionDocument d = EstablishWtrConnectionDocument.Factory.newInstance();
			EstablishWtrConnection c = d.addNewEstablishWtrConnection();

			c.setHeader(mapper.map(header, aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader.class));
			// STEP 2: SEND REQUEST
			EstablishWtrConnectionResponseDocument r = stub.establishWtrConnection(d);
			WorldTracerResponse response = r.getEstablishWtrConnectionResponse().getReturn();

			// STEP 3: BASIC PROCESS OF RESPONSE
			processResponseAndUpdateDto(dto, response);
			
			if (response.getSuccess()) {
				return true;
			}

		} catch (AxisFault e) {
			e.printStackTrace();
			throw new WorldTracerException(CONNECTION_ERROR_UNABLE_TO_PERFORM_ACTION, e);
		} catch (RemoteException e) {
			e.printStackTrace();
			throw new WorldTracerException(CONNECTION_ERROR_UNABLE_TO_PERFORM_ACTION, e);
		}
		return false;
	}
	

	@Override
	public List<aero.nettracer.serviceprovider.wt_1_0.common.ActionFileCount> getActionFileCounts(String companyCode, String wtStation, WebServiceDto dto) throws WorldTracerException, CaptchaException {
		ArrayList<aero.nettracer.serviceprovider.wt_1_0.common.ActionFileCount> result = new ArrayList<aero.nettracer.serviceprovider.wt_1_0.common.ActionFileCount>();
		try {
			WorldTracerServiceStub stub = getConfiguredServiceStub();

			RequestHeader header = generateHeader(dto);

			// STEP 1: BEGINNING OF FUNCTION SPECIFIC LOGIC
			ActionFileRequestData data = new ActionFileRequestData();
			data.setAirline(companyCode);
			data.setStation(wtStation);

			GetActionFileCountsDocument d = GetActionFileCountsDocument.Factory.newInstance();
			GetActionFileCounts c = d.addNewGetActionFileCounts();

			c.setHeader(mapper.map(header, aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader.class));
			c.setData(mapper.map(data, aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileRequestData.class));

			// STEP 2: SEND REQUEST
			GetActionFileCountsResponseDocument r = stub.getActionFileCounts(d);
			WorldTracerResponse response = r.getGetActionFileCountsResponse().getReturn();
			
			// STEP 3: BASIC PROCESS OF RESPONSE
			processResponseAndUpdateDto(dto, response);

			// STEP 4: CASE-SEPECIFIC PROCESSING
			if (response.getCountsArray() != null) {
				ActionFileCount[] counts = response.getCountsArray();
				for (ActionFileCount count : counts) {
					aero.nettracer.serviceprovider.wt_1_0.common.ActionFileCount toAdd = new aero.nettracer.serviceprovider.wt_1_0.common.ActionFileCount();
					toAdd.setType(count.getType());
					toAdd.setStation(count.getStation());
					toAdd.setSeq(count.getSeq());
					toAdd.setDay(count.getDay());
					toAdd.setCount(count.getCount());
					result.add(toAdd);
				}
			}

		} catch (AxisFault e) {
			e.printStackTrace();
			throw new WorldTracerException(CONNECTION_ERROR_UNABLE_TO_PERFORM_ACTION, e);
		} catch (RemoteException e) {
			e.printStackTrace();
			throw new WorldTracerException(CONNECTION_ERROR_UNABLE_TO_PERFORM_ACTION, e);
		}

		return result;
	}

	@Override
	public String amendAhl(Incident incident, String wt_ahl_id, WebServiceDto dto) throws WorldTracerException, CaptchaException {
		String returnValue = null;
		try {
			WorldTracerServiceStub stub = getConfiguredServiceStub();

			RequestHeader header = generateHeader(dto);

			// STEP 1: BEGINNING OF FUNCTION SPECIFIC LOGIC
			Ahl ahl = new Ahl();
			
			mapIncidentToAhl(incident, ahl);

			AmendAhlDocument d = AmendAhlDocument.Factory.newInstance();
			AmendAhl c = d.addNewAmendAhl();

			c.setHeader(mapper.map(header, aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader.class));
			c.setAhl(mapper.map(ahl, aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl.class));

			// STEP 2: SEND REQUEST
			AmendAhlResponseDocument r = stub.amendAhl(d);
			WorldTracerResponse response = r.getAmendAhlResponse().getReturn();

			// STEP 3: BASIC PROCESS OF RESPONSE
			processResponseAndUpdateDto(dto, response);

			// STEP 4: CASE-SEPECIFIC PROCESSING
			
			if (response.getSuccess()) {
				returnValue = "1";
			} else {
				if (response != null && response.getError() != null && response.getError().getDescription() != null && response.getError().getDescription().length() > 0) {
					throw new WorldTracerException("Unable to update AHL: " + response.getError().getDescription());
				} else {
					throw new WorldTracerException("Unable to successfully update AHL");
				}
			}

		} catch (AxisFault e) {
			e.printStackTrace();
			throw new WorldTracerException("World Tracer Connection error... Unable to amend AHL: " + incident.getIncident_ID());
		} catch (RemoteException e) {
			e.printStackTrace();
			throw new WorldTracerException("World Tracer Connection error... Unable to amend AHL: " + incident.getIncident_ID());
		}

		return returnValue;
	}

	@Override
	public String amendOhd(OHD onhand, String wt_ohd_id, WebServiceDto dto) throws WorldTracerException, CaptchaException {
		String returnValue = null;
		try {
			WorldTracerServiceStub stub = getConfiguredServiceStub();

			RequestHeader header = generateHeader(dto);

			// STEP 1: BEGINNING OF FUNCTION SPECIFIC LOGIC
			Ohd ohd = new Ohd();
			
			mapOhdToWt(onhand, ohd);

			AmendOhdDocument d = AmendOhdDocument.Factory.newInstance();
			AmendOhd c = d.addNewAmendOhd();

			c.setHeader(mapper.map(header, aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader.class));
			c.setOhd(mapper.map(ohd, aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd.class));

			// STEP 2: SEND REQUEST
			AmendOhdResponseDocument r = stub.amendOhd(d);
			WorldTracerResponse response = r.getAmendOhdResponse().getReturn();

			// STEP 3: BASIC PROCESS OF RESPONSE
			processResponseAndUpdateDto(dto, response);

			// STEP 4: CASE-SEPECIFIC PROCESSING
			
			if (response.getSuccess()) {
				returnValue = ohd.getOhdId();
			} else {
				if (response != null && response.getError() != null && response.getError().getDescription() != null && response.getError().getDescription().length() > 0) {
					throw new WorldTracerException("Unable to update OHD: " + response.getError().getDescription());
				} else {
					throw new WorldTracerException("Unable to successfully amend ohd");
				}
				
			}

		} catch (AxisFault e) {
			e.printStackTrace();
			throw new WorldTracerException(CONNECTION_ERROR_UNABLE_TO_PERFORM_ACTION, e);
		} catch (RemoteException e) {
			e.printStackTrace();
			throw new WorldTracerException(CONNECTION_ERROR_UNABLE_TO_PERFORM_ACTION, e);
		}

		return returnValue;
	}

	@Override
	public String closeIncident(Incident incident, String wtId, String stationCode, WebServiceDto dto) throws WorldTracerException, CaptchaException {
		String returnValue = null;
		try {
			WorldTracerServiceStub stub = getConfiguredServiceStub();

			RequestHeader header = generateHeader(dto);

			// STEP 1: BEGINNING OF FUNCTION SPECIFIC LOGIC
			Ahl ahl = new Ahl();
			
			mapIncidentToAhl(incident, ahl);

			CloseAhlDocument d = CloseAhlDocument.Factory.newInstance();
			CloseAhl c = d.addNewCloseAhl();

			c.setHeader(mapper.map(header, aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader.class));
			c.setAhl(mapper.map(ahl, aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl.class));

			// STEP 2: SEND REQUEST
			CloseAhlResponseDocument r = stub.closeAhl(d);
			WorldTracerResponse response = r.getCloseAhlResponse().getReturn();

			// STEP 3: BASIC PROCESS OF RESPONSE
			processResponseAndUpdateDto(dto, response);

			// STEP 4: CASE-SEPECIFIC PROCESSING
			
			if (response.getSuccess()) {
				returnValue = ahl.getAhlId();
			} else {
				if (response != null && response.getError() != null && response.getError().getDescription() != null && response.getError().getDescription().length() > 0) {
					throw new WorldTracerException("Unable to close AHL: " + response.getError().getDescription());
				} else {
					throw new WorldTracerException("Unable to successfully close incident");
				}
			}

		} catch (AxisFault e) {
			e.printStackTrace();
			throw new WorldTracerException("World Tracer connection error... Unable to close AHL: " + incident.getIncident_ID());
		} catch (RemoteException e) {
			e.printStackTrace();
			throw new WorldTracerException("World Tracer connection error... Unable to close AHL: " + incident.getIncident_ID());
		}

		return returnValue;
	}

	@Override
	public String closeOhd(OHD onhand, String wt_id, String wt_stationcode, WebServiceDto dto) throws WorldTracerException, CaptchaException {
		String returnValue = null;
		try {
			WorldTracerServiceStub stub = getConfiguredServiceStub();

			RequestHeader header = generateHeader(dto);

			// STEP 1: BEGINNING OF FUNCTION SPECIFIC LOGIC
			Ohd ohd = new Ohd();
			
			mapOhdToWt(onhand, ohd);

			CloseOhdDocument d = CloseOhdDocument.Factory.newInstance();
			CloseOhd c = d.addNewCloseOhd();

			c.setHeader(mapper.map(header, aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader.class));
			c.setOhd(mapper.map(ohd, aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd.class));

			// STEP 2: SEND REQUEST
			CloseOhdResponseDocument r = stub.closeOhd(d);
			WorldTracerResponse response = r.getCloseOhdResponse().getReturn();

			// STEP 3: BASIC PROCESS OF RESPONSE
			processResponseAndUpdateDto(dto, response);

			// STEP 4: CASE-SEPECIFIC PROCESSING
			
			if (response.getSuccess()) {
				returnValue = ohd.getOhdId();
			} else {
				if (response != null && response.getError() != null && response.getError().getDescription() != null && response.getError().getDescription().length() > 0) {
					throw new WorldTracerException("Unable to close OHD: " + response.getError().getDescription());
				} else {
					throw new WorldTracerException("Unable to successfully close ohd");
				}
			}

		} catch (AxisFault e) {
			e.printStackTrace();
			throw new WorldTracerException(CONNECTION_ERROR_UNABLE_TO_PERFORM_ACTION, e);
		} catch (RemoteException e) {
			e.printStackTrace();
			throw new WorldTracerException(CONNECTION_ERROR_UNABLE_TO_PERFORM_ACTION, e);
		}

		return returnValue;
	}

	@Override
	public String createBdo(Map<WorldTracerField, List<String>> fieldMap, String ahl_id, String ohd_id, DeliverCompany delivercompany, Station station, WebServiceDto dto, BDO bdo1)
			throws WorldTracerException, CaptchaException {
		try {
			WorldTracerServiceStub stub = getConfiguredServiceStub();

			RequestHeader header = generateHeader(dto);

			// STEP 1: BEGINNING OF FUNCTION SPECIFIC LOGIC
			Bdo bdo = new Bdo();
			
//			bdo.setAddress1(address1);
//			bdo.setAddress2(address2);
			
			Agent a = new Agent();
			a.setAirline(station.getCompany().getCompanyCode_ID());
			a.setUsername(fieldMap.get(WorldTracerField.AG).get(0));
			bdo.setAgent(a);
			bdo.setAhlId(ahl_id);
			bdo.setAirlineCode(station.getCompany().getCompanyCode_ID());
			if (fieldMap.get(WorldTracerField.LD) != null) {
				bdo.setDeliveryComments(fieldMap.get(WorldTracerField.LD).get(0));
			}

//			bdo.setDeliveryCompany(deliveryCompany);
//			bdo.setOriginationStationCode(originationStationCode);
//			bdo.setPassengers(passengers);

			
			bdo.setDeliveryStationCode(station.getStationcode());
			
			ArrayList<aero.nettracer.serviceprovider.wt_1_0.common.Item> al = new ArrayList<aero.nettracer.serviceprovider.wt_1_0.common.Item>();
			
			

			// TODO: HERE
			for (Item item: (Set<Item>) bdo1.getItems()) {
				aero.nettracer.serviceprovider.wt_1_0.common.Item i = new aero.nettracer.serviceprovider.wt_1_0.common.Item();
				i.setBagNumber(item.getBagnumber() + 1);
				i.setColor(item.getColor());
				i.setType(item.getBagtype());
				al.add(i);	
			}
			GregorianCalendar acal = new GregorianCalendar();
			acal.setTime(bdo1.getDeliverydate());
			bdo.setDeliveryDate(acal);
//			i.setBagNumber(bagNumber);
			bdo.setItems(al.toArray(new aero.nettracer.serviceprovider.wt_1_0.common.Item[al.size()]));
			bdo.setStationCode(station.getStationcode());
			
			CreateBdoDocument d = CreateBdoDocument.Factory.newInstance();
			CreateBdo c = d.addNewCreateBdo();

			c.setHeader(mapper.map(header, aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader.class));
			c.setBdo(mapper.map(bdo, aero.nettracer.serviceprovider.wt_1_0.common.xsd.Bdo.class));

			// STEP 2: SEND REQUEST
			CreateBdoResponseDocument r = stub.createBdo(d);
			WorldTracerResponse response = r.getCreateBdoResponse().getReturn();

			// STEP 3: BASIC PROCESS OF RESPONSE
			processResponseAndUpdateDto(dto, response);

			if (!response.getSuccess()) {
					throw new WorldTracerException("Unable to create BDO for AHL: " + ahl_id);
			}
			
			// STEP 4: CASE-SEPECIFIC PROCESSING
		} catch (AxisFault e) {
			e.printStackTrace();
			throw new WorldTracerException(CONNECTION_ERROR_UNABLE_TO_PERFORM_ACTION, e);
		} catch (RemoteException e) {
			e.printStackTrace();
			throw new WorldTracerException(CONNECTION_ERROR_UNABLE_TO_PERFORM_ACTION, e);
		}
		
		return null;
	}

	@Override
	public void eraseActionFile(String station_id, String companyCode, ActionFileType area, String seq, int day, int itemNum, WebServiceDto dto) throws WorldTracerException, CaptchaException {

		try {
			WorldTracerServiceStub stub = getConfiguredServiceStub();

			RequestHeader header = generateHeader(dto);

			// STEP 1: BEGINNING OF FUNCTION SPECIFIC LOGIC
			ActionFileRequestData data = new ActionFileRequestData();
			data.setAirline(companyCode);
			data.setStation(station_id);
			data.setType(area.name());
			data.setNumber(itemNum);
			data.setDay(day);
			data.setSeq(seq);

			EraseActionFileDocument d = EraseActionFileDocument.Factory.newInstance();
			EraseActionFile c = d.addNewEraseActionFile();

			c.setHeader(mapper.map(header, aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader.class));
			c.setData(mapper.map(data, aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileRequestData.class));

			// STEP 2: SEND REQUEST
			EraseActionFileResponseDocument r = stub.eraseActionFile(d);
			WorldTracerResponse response = r.getEraseActionFileResponse().getReturn();

			// STEP 3: BASIC PROCESS OF RESPONSE
			processResponseAndUpdateDto(dto, response);

			// STEP 4: CASE-SEPECIFIC PROCESSING
		} catch (AxisFault e) {
			logger.error("Unable to erase action file", e);
			throw new WorldTracerException(CONNECTION_ERROR_UNABLE_TO_PERFORM_ACTION, e);
		} catch (RemoteException e) {
			logger.error("Unable to erase action file", e);
			throw new WorldTracerException(CONNECTION_ERROR_UNABLE_TO_PERFORM_ACTION, e);
		}
	}

	@Override
	public Ahl findAHL(String wt_id, WebServiceDto dto) throws WorldTracerException, CaptchaException {
		try {
			WorldTracerServiceStub stub = getConfiguredServiceStub();

			RequestHeader header = generateHeader(dto);
			
			Ahl ahl = new Ahl();
			ahl.setAhlId(wt_id);
			
			GetAhlDocument d = GetAhlDocument.Factory.newInstance();
			GetAhl c = d.addNewGetAhl();
			
			c.setHeader(mapper.map(header, aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader.class));
			c.setAhl(mapper.map(ahl, aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl.class));
			
			GetAhlResponseDocument r = stub.getAhl(d);
			WorldTracerResponse response = r.getGetAhlResponse().getReturn();
			
			if (response.getSuccess()) {
				processResponseAndUpdateDto(dto, response);
				return mapper.map(response.getAhl(), aero.nettracer.serviceprovider.wt_1_0.common.Ahl.class);

			} else {
				logger.info("Unable to forward to get AHL from WT: " + wt_id);
				if (response.getError() != null) {
					WebServiceError error = response.getError();
					if (error.getDescription().contains(CAPTCHA_EXCEPTION)) {
						dto.setCaptcha(response.getCaptcha());
						dto.setCaptchaTimestamp(response.getCaptchaTimestamp());
						throw new CaptchaException();
					} else if (error.getDescription().contains(RECORD_NOT_FOUND_EXCEPTION)) {
						throw new WorldTracerRecordNotFoundException();
					} else {
						throw new WorldTracerException(error.getDescription());
					}
				} else {
					throw new WorldTracerException("Unable to forward to get AHL from WT: " + wt_id);
				}
			}
			
		} catch (AxisFault e) {
			logger.error("Unable to erase action file", e);
			throw new WorldTracerException(CONNECTION_ERROR_UNABLE_TO_PERFORM_ACTION, e);
		} catch (RemoteException e) {
			logger.error("Unable to erase action file", e);
			throw new WorldTracerException(CONNECTION_ERROR_UNABLE_TO_PERFORM_ACTION, e);
		}
	}

	@Override
	public Ohd findOHD(String wt_id, WebServiceDto dto) throws WorldTracerException, CaptchaException {
		try {
			WorldTracerServiceStub stub = getConfiguredServiceStub();

			RequestHeader header = generateHeader(dto);
			
			Ohd ohd = new Ohd();
			ohd.setOhdId(wt_id);

			GetOhdDocument d = GetOhdDocument.Factory.newInstance();
			GetOhd c = d.addNewGetOhd();
			
			
			c.setHeader(mapper.map(header, aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader.class));
			c.setOhd(mapper.map(ohd, aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd.class));
			
			GetOhdResponseDocument r = stub.getOhd(d);
			WorldTracerResponse response = r.getGetOhdResponse().getReturn();
			System.out.print(response.toString());
			if (response.getSuccess()) {
				processResponseAndUpdateDto(dto, response);
				return mapper.map(response.getOhd(), aero.nettracer.serviceprovider.wt_1_0.common.Ohd.class);

			} else {
				logger.info("Unable to forward to get OHD from WT: " + wt_id);
				if (response.getError() != null) {
					WebServiceError error = response.getError();
					if (error.getDescription().contains(CAPTCHA_EXCEPTION)) {
						dto.setCaptcha(response.getCaptcha());
						dto.setCaptchaTimestamp(response.getCaptchaTimestamp());
						throw new CaptchaException();
					} else if (error.getDescription().contains(RECORD_NOT_FOUND_EXCEPTION)) {
						throw new WorldTracerRecordNotFoundException();
					} else {
						throw new WorldTracerException(error.getDescription());
					}
				} else {
					throw new WorldTracerException("Unable to forward to get OHD from WT: " + wt_id);
				}
			}

		} catch (AxisFault e) {
			logger.error("Unable to erase action file", e);
			throw new WorldTracerException(CONNECTION_ERROR_UNABLE_TO_PERFORM_ACTION, e);
		} catch (RemoteException e) {
			logger.error("Unable to erase action file", e);
			throw new WorldTracerException(CONNECTION_ERROR_UNABLE_TO_PERFORM_ACTION, e);
		}
	}

	
	@Override
	public String forwardOhd(WtqFwdOhd fwd, WebServiceDto dto) throws WorldTracerException, CaptchaException {
		String returnValue = null;
		try {
			WorldTracerServiceStub stub = getConfiguredServiceStub();

			RequestHeader header = generateHeader(dto);

			// STEP 1: BEGINNING OF FUNCTION SPECIFIC LOGIC
			ForwardOhd fw = new ForwardOhd();
			fw.setAhlId(fwd.getMatchingAhl());
			fw.setOhdId(fwd.getOhd().getWt_id());
			fw.setSupplementaryInfo(fwd.getSupInfo());
			fw.setExpediteNumber(fwd.getFwdExpediteNum());
			fw.setDestinationAirline(fwd.getFwdDestinationAirline());
			fw.setDestinationStation(fwd.getFwdDestinationStation());
			
			Agent a = new Agent();
			a.setAirline(fwd.getAgent().getCompanycode_ID());
			a.setUsername(fwd.getAgent().getUsername());
			fw.setAgent(a);
			
			if (fwd.getTeletypes() != null) {
				String[] teletype = fwd.getTeletypes().toArray(new String[fwd.getTeletypes().size()]);
				fw.setTeletype(teletype);
			}
			
			if (fwd.getFwdName() != null) {
				String[] names = fwd.getFwdName().toArray(new String[fwd.getFwdName().size()]);
				fw.setFwdName(names);
			}
			
			ArrayList<Itinerary> itinerary = new ArrayList<Itinerary>();
			
			if (fwd.getItinerary() != null) {
				for (WtqSegment itin : fwd.getItinerary()) {
					if (itin.getAirline() == null || itin.getAirline().trim().length() <= 0 || itin.getFlightnum() == null
							|| itin.getFlightnum().trim().length() <= 0 || itin.getLegfrom() == null
							|| itin.getLegfrom().trim().length() <= 0 || itin.getLegto() == null
							|| itin.getLegto().trim().length() <= 0 || itin.getDepartdate() == null) {
						continue;
					}
					Itinerary i = new Itinerary();
					i.setAirline(itin.getAirline());
					i.setArrivalCity(itin.getLegto());
					i.setDepartureCity(itin.getLegfrom());
					i.setFlightNumber(itin.getFlightnum());
					
					GregorianCalendar cal = new GregorianCalendar();
					cal.setTime(itin.getDepartdate());
					i.setFlightDate(cal);
					itinerary.add(i);
				}
			}
			
			
			fw.setItinerary(itinerary.toArray(new Itinerary[itinerary.size()]));
			
			try {
				SpringUtils.getClientEventHandler().doEventOnForward(fw);
			} catch (Exception e) {
				logger.error("Error performing client-specific BEORN Action...", e);
			}
			
			
			ForwardOhdDocument d = ForwardOhdDocument.Factory.newInstance();
			aero.nettracer.serviceprovider.wt_1_0.ForwardOhdDocument.ForwardOhd c = d.addNewForwardOhd();

			c.setHeader(mapper.map(header, aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader.class));
			c.setData(mapper.map(fw, aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardOhd.class));

			// STEP 2: SEND REQUEST
			ForwardOhdResponseDocument r = stub.forwardOhd(d);
			WorldTracerResponse response = r.getForwardOhdResponse().getReturn();

			// STEP 3: BASIC PROCESS OF RESPONSE
			processResponseAndUpdateDto(dto, response);

			// STEP 4: CASE-SEPECIFIC PROCESSING
			
			if (response.getSuccess()) {
				returnValue = fw.getOhdId();
			} else {
				logger.info("Unable to forward on-hand: " + fw.getOhdId());
				throw new WorldTracerException("Unable to forward on-hand: " + fw.getOhdId());
			}

		} catch (AxisFault e) {
			e.printStackTrace();
			throw new WorldTracerException(CONNECTION_ERROR_UNABLE_TO_PERFORM_ACTION, e);
		} catch (RemoteException e) {
			e.printStackTrace();
			throw new WorldTracerException(CONNECTION_ERROR_UNABLE_TO_PERFORM_ACTION, e);
		}

		return returnValue;
	}

	@Override
	@Deprecated
	public String getActionFileDetails(String companyCode, String stationCode, ActionFileType afType, int day, int itemNum, WebServiceDto dto) throws WorldTracerException,
			CaptchaException {
		// NO LONGER NEED WITH ISHARES APPROACH
		return null;
	}

	@Override
	public List<ActionFileDto> getActionFileSummary(String companyCode, String stationCode, ActionFileType afType, String seq, int day, WebServiceDto dto) throws WorldTracerException,
			CaptchaException,WorldTracerRecordNotFoundException {
		ArrayList<ActionFileDto> result = new ArrayList<ActionFileDto>();
		try {
			
			WorldTracerServiceStub stub = getConfiguredServiceStub();

			RequestHeader header = generateHeader(dto);

			// STEP 1: BEGINNING OF FUNCTION SPECIFIC LOGIC
			ActionFileRequestData data = new ActionFileRequestData();
			data.setAirline(companyCode);
			data.setStation(stationCode);
			data.setDay(day);
			data.setType(afType.name());
			data.setSeq(seq);
			
			
			GetActionFileSummaryDocument d = GetActionFileSummaryDocument.Factory.newInstance();
			GetActionFileSummary c = d.addNewGetActionFileSummary();
			c.setHeader(mapper.map(header, aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader.class));
			c.setData(mapper.map(data, aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileRequestData.class));

			// STEP 2: SEND REQUEST
			GetActionFileSummaryResponseDocument r = stub.getActionFileSummary(d);
			WorldTracerResponse response = r.getGetActionFileSummaryResponse().getReturn();

			// STEP 3: BASIC PROCESS OF RESPONSE
			processResponseAndUpdateDto(dto, response);

			// STEP 4: CASE-SEPECIFIC PROCESSING
			if (response.getError() != null 
					&& response.getError().getDescription() != null
					&& response.getError().getDescription().contains(RECORD_NOT_FOUND_EXCEPTION)) {
				throw new WorldTracerRecordNotFoundException();
			} else if (response.getActionFilesArray() != null) {
				ActionFile[] afs = response.getActionFilesArray();
				for (ActionFile af: afs) {
					if (af != null) {
						ActionFileDto afd = new ActionFileDto();
						afd.setAhlId(af.getAhlId());
						if (af.getSummary().length() > 512) {
							afd.setSummary(af.getSummary().substring(0, 512));
						} else {
							afd.setSummary(af.getSummary());
						}
						
						afd.setItemNumber(af.getItemNumber());
						afd.setOhdId(af.getOhdId());
						afd.setPercentMatch(af.getPercentMatch());
						afd.setDetails(af.getDetails());
						result.add(afd);
											
					}
				}
			}

		} catch (AxisFault e) {
			e.printStackTrace();
			throw new WorldTracerException(CONNECTION_ERROR_UNABLE_TO_PERFORM_ACTION, e);
		} catch (RemoteException e) {
			e.printStackTrace();
			throw new WorldTracerException(CONNECTION_ERROR_UNABLE_TO_PERFORM_ACTION, e);
		}

		return result;
	}

	@Override
	@Deprecated
	public List<Worldtracer_Actionfiles> getActionFiles(String companyCode, String stationCode, ActionFileType afType, int day, int count, WebServiceDto dto)
			throws WorldTracerException, CaptchaException {
		// NO LONGER NEED WITH TYPE-A INTERFACE
		return null;
	}

	@Override
	public String insertIncident(Incident incident, String companyCode, String stationCode, WebServiceDto dto) throws WorldTracerException,
			CaptchaException {

		String returnValue = null;
		try {
			WorldTracerServiceStub stub = getConfiguredServiceStub();

			RequestHeader header = generateHeader(dto);

			// STEP 1: BEGINNING OF FUNCTION SPECIFIC LOGIC
			Ahl ahl = new Ahl();
			
			mapIncidentToAhl(incident, ahl);

			CreateAhlDocument d = CreateAhlDocument.Factory.newInstance();
			CreateAhl c = d.addNewCreateAhl();

			c.setHeader(mapper.map(header, aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader.class));
			c.setAhl(mapper.map(ahl, aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl.class));

			// STEP 2: SEND REQUEST
			CreateAhlResponseDocument r = stub.createAhl(d);
			WorldTracerResponse response = r.getCreateAhlResponse().getReturn();

			// STEP 3: BASIC PROCESS OF RESPONSE
			processResponseAndUpdateDto(dto, response);

			// STEP 4: CASE-SEPECIFIC PROCESSING
			
			if (response.getSuccess() && response.getAhl().getAhlId() != null) {
				returnValue = response.getAhl().getAhlId();
			} else {
				
				if (response != null && response.getError() != null && response.getError().getDescription() != null && response.getError().getDescription().length() > 0) {
					throw new WorldTracerException("Unable create AHL: " + incident.getIncident_ID() + " - " + response.getError().getDescription());
				} else {
					throw new WorldTracerException("Unable to create incident: " + incident.getIncident_ID());
				}
			}

		} catch (AxisFault e) {
			e.printStackTrace();
			throw new WorldTracerException("Connection error... Unable to create incident: " + incident.getIncident_ID());
		} catch (RemoteException e) {
			e.printStackTrace();
			throw new WorldTracerException("Connection error... Unable to create incident: " + incident.getIncident_ID());
		}

		return returnValue;
	}


	@Override
	public String insertOhd(OHD onhand, String companyCode, String stationCode, WebServiceDto dto) throws WorldTracerException, CaptchaException {
		String returnValue = null;
		try {
			WorldTracerServiceStub stub = getConfiguredServiceStub();

			RequestHeader header = generateHeader(dto);

			// STEP 1: BEGINNING OF FUNCTION SPECIFIC LOGIC
			Ohd ohd = new Ohd();
			
			mapOhdToWt(onhand, ohd);

			CreateOhdDocument d = CreateOhdDocument.Factory.newInstance();
			CreateOhd c = d.addNewCreateOhd();

			c.setHeader(mapper.map(header, aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader.class));
			c.setOhd(mapper.map(ohd, aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd.class));

			// STEP 2: SEND REQUEST
			CreateOhdResponseDocument r = stub.createOhd(d);
			WorldTracerResponse response = r.getCreateOhdResponse().getReturn();

			// STEP 3: BASIC PROCESS OF RESPONSE
			processResponseAndUpdateDto(dto, response);

			// STEP 4: CASE-SEPECIFIC PROCESSING
			
			if (response.getSuccess()) {
				returnValue = response.getOhd().getOhdId();
			} else {
				if (response != null && response.getError() != null && response.getError().getDescription() != null && response.getError().getDescription().length() > 0) {
					throw new WorldTracerException("Unable create ohd: " + response.getError().getDescription());
				} else {
					throw new WorldTracerException("Unable to create ohd ");
				}
			}

		} catch (AxisFault e) {
			e.printStackTrace();
			throw new WorldTracerException(CONNECTION_ERROR_UNABLE_TO_PERFORM_ACTION, e);
		} catch (RemoteException e) {
			e.printStackTrace();
			throw new WorldTracerException(CONNECTION_ERROR_UNABLE_TO_PERFORM_ACTION, e);
		}

		return returnValue;
	}


	@Override
	public void reinstateAHL(String wt_id, String agent, WebServiceDto dto) throws WorldTracerException, CaptchaException {

		try {
			WorldTracerServiceStub stub = getConfiguredServiceStub();

			RequestHeader header = generateHeader(dto);

			// STEP 1: BEGINNING OF FUNCTION SPECIFIC LOGIC
			Ahl ahl = new Ahl();
			ahl.setAhlId(wt_id);
			
			Agent ag = new Agent();
			ag.setUsername(agent);
			ahl.setAgent(ag);

			ReinstateAhlDocument d = ReinstateAhlDocument.Factory.newInstance();
			ReinstateAhl c = d.addNewReinstateAhl();

			c.setHeader(mapper.map(header, aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader.class));
			c.setAhl(mapper.map(ahl, aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl.class));

			// STEP 2: SEND REQUEST
			ReinstateAhlResponseDocument r = stub.reinstateAhl(d);
			WorldTracerResponse response = r.getReinstateAhlResponse().getReturn();

			// STEP 3: BASIC PROCESS OF RESPONSE
			processResponseAndUpdateDto(dto, response);

			// STEP 4: CASE-SEPECIFIC PROCESSING
			
			if (!response.getSuccess()) {
				logger.info("Unable to reinstate AHL: " + ahl.getAhlId());
				
				if (response != null && response.getError() != null && response.getError().getDescription() != null && response.getError().getDescription().length() > 0) {
					throw new WorldTracerException("Unable reinstate AHL: " + ahl.getAhlId() + " " + response.getError().getDescription());
				}
				
				throw new WorldTracerException("Unable to reinstate AHL: " + ahl.getAhlId());
			}

		} catch (AxisFault e) {
			e.printStackTrace();
			throw new WorldTracerException(CONNECTION_ERROR_UNABLE_TO_PERFORM_ACTION, e);
		} catch (RemoteException e) {
			e.printStackTrace();
			throw new WorldTracerException(CONNECTION_ERROR_UNABLE_TO_PERFORM_ACTION, e);
		}
	}



	@Override
	public String requestOhd(String wt_ohd_id, String wt_ahl_id, Map<WorldTracerField, List<String>> fieldMap, WebServiceDto dto, WtqRequestOhd roh) throws WorldTracerException, CaptchaException {


		String returnValue = null;
		try {
			WorldTracerServiceStub stub = getConfiguredServiceStub();

			RequestHeader header = generateHeader(dto);

			// STEP 1: BEGINNING OF FUNCTION SPECIFIC LOGIC
			aero.nettracer.serviceprovider.wt_1_0.common.RequestOhd ro = new aero.nettracer.serviceprovider.wt_1_0.common.RequestOhd();
			
			Agent a = new Agent();
			a.setAirline(roh.getAgent().getStation().getCompany().getCompanyCode_ID());
			a.setUsername(roh.getAgent().getUsername());
			ro.setAgent(a);
			
			Ahl ahl = new Ahl();
			ahl.setAhlId(wt_ahl_id);
			ro.setAhl(ahl);
			ro.setFurtherInfo(roh.getFurtherInfo());
			ro.setOhdId(wt_ohd_id);
			
			
			if (roh.getTeletypes() != null) {
				String[] teletype = roh.getTeletypes().toArray(new String[roh.getTeletypes().size()]);
				ro.setTeletype(teletype);
			}

	
			

			RequestOhdDocument d = RequestOhdDocument.Factory.newInstance();
			RequestOhd c = d.addNewRequestOhd();

			c.setHeader(mapper.map(header, aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader.class));
			c.setData(mapper.map(ro, aero.nettracer.serviceprovider.wt_1_0.common.xsd.RequestOhd.class));

			// STEP 2: SEND REQUEST
			RequestOhdResponseDocument r = stub.requestOhd(d);
			WorldTracerResponse response = r.getRequestOhdResponse().getReturn();

			// STEP 3: BASIC PROCESS OF RESPONSE
			processResponseAndUpdateDto(dto, response);

			// STEP 4: CASE-SEPECIFIC PROCESSING
			
			if (response.getSuccess()) {
				returnValue = "SUCESS";
			} else {
				logger.info("Unable to send request ohd");
				throw new WorldTracerException("Unable to request ohd");
			}

		} catch (AxisFault e) {
			e.printStackTrace();
			throw new WorldTracerException(CONNECTION_ERROR_UNABLE_TO_PERFORM_ACTION, e);
		} catch (RemoteException e) {
			e.printStackTrace();
			throw new WorldTracerException(CONNECTION_ERROR_UNABLE_TO_PERFORM_ACTION, e);
		}

		return returnValue;
	}

	@Override
	public String requestQoh(String fromStation, String fromAirline, String wt_ahl_id, Map<WorldTracerField, List<String>> fieldMap, WebServiceDto dto, WtqRequestQoh wtq)
			throws WorldTracerException, CaptchaException {
		String returnValue = null;
		try {
			WorldTracerServiceStub stub = getConfiguredServiceStub();

			RequestHeader header = generateHeader(dto);

			// STEP 1: BEGINNING OF FUNCTION SPECIFIC LOGIC
			aero.nettracer.serviceprovider.wt_1_0.common.RequestOhd ro = new aero.nettracer.serviceprovider.wt_1_0.common.RequestOhd();
			
			Agent a = new Agent();
			a.setAirline(wtq.getAgent().getStation().getCompany().getCompanyCode_ID());
			a.setUsername(wtq.getAgent().getUsername());
			ro.setAgent(a);
			
			Ahl ahl = new Ahl();
			ahl.setAhlId(wt_ahl_id);
			ro.setAhl(ahl);
			
			ro.setFurtherInfo(wtq.getFurtherInfo());
			ro.setBagTagNumber(wtq.getBagTagNumber());
			ro.setFromAirline(fromAirline);
			ro.setFromStation(fromStation);
			
			
			if (wtq.getTeletypes() != null) {
				String[] teletype = wtq.getTeletypes().toArray(new String[wtq.getTeletypes().size()]);
				ro.setTeletype(teletype);
			}

			RequestQuickOhdDocument d = RequestQuickOhdDocument.Factory.newInstance();
			RequestQuickOhd c = d.addNewRequestQuickOhd();

			c.setHeader(mapper.map(header, aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader.class));
			c.setData(mapper.map(ro, aero.nettracer.serviceprovider.wt_1_0.common.xsd.RequestOhd.class));
			
			// STEP 2: SEND REQUEST
			RequestQuickOhdResponseDocument r = stub.requestQuickOhd(d);
			WorldTracerResponse response = r.getRequestQuickOhdResponse().getReturn();

			// STEP 3: BASIC PROCESS OF RESPONSE
			processResponseAndUpdateDto(dto, response);

			// STEP 4: CASE-SEPECIFIC PROCESSING
			
			if (response.getSuccess()) {
				returnValue = "SUCESS";
			} else {
				logger.info("Unable to request ohd");
				throw new WorldTracerException("Unable to request ohd");
			}

		} catch (AxisFault e) {
			e.printStackTrace();
			throw new WorldTracerException(CONNECTION_ERROR_UNABLE_TO_PERFORM_ACTION, e);
		} catch (RemoteException e) {
			e.printStackTrace();
			throw new WorldTracerException(CONNECTION_ERROR_UNABLE_TO_PERFORM_ACTION, e);
		}

		return returnValue;
	}

	@Override
	public String sendFwd(WtqFwdGeneral fwd, WebServiceDto dto) throws WorldTracerException, CaptchaException {

		
		String returnValue = null;
		try {
			WorldTracerServiceStub stub = getConfiguredServiceStub();

			RequestHeader header = generateHeader(dto);

			// STEP 1: BEGINNING OF FUNCTION SPECIFIC LOGIC
			ForwardMessage fw = new ForwardMessage();
			
			fw.setTextInfo(fwd.getSupInfo());
			fw.setSuplementaryInfo(fwd.getSupInfo());
			fw.setExpediteTag(fwd.getFwdExpediteNum());
			fw.setTagNum(fwd.getFwdTagNum());
			fw.setDestinationAirline(fwd.getFwdDestinationAirline());
			fw.setDestinationStation(fwd.getFwdDestinationStation());
			fw.setFromAirline(fwd.getAgent().getCompanycode_ID());
			fw.setCrossReferenceId(fwd.getMatchingAhl());
			if (fwd.getLossCode() != 0) {
				fw.setFaultReason(fwd.getLossCode());
			}
			
			fw.setFaultReasonDescription(fwd.getLossComments());
			// fw.setFaultStation(faultStation);
			
			String from_station = fwd.getFrom_station();
			if(from_station == null || from_station.trim().length() < 1) {
				from_station = fwd.getAgent().getStation().getWt_stationcode();
			}
			fw.setFromStation(from_station);
			
			Agent a = new Agent();
			a.setAirline(fwd.getAgent().getCompanycode_ID());
			a.setUsername(fwd.getAgent().getUsername());
			fw.setAgent(a);
			
			if (fwd.getTeletypes() != null) {
				String[] teletype = fwd.getTeletypes().toArray(new String[fwd.getTeletypes().size()]);
				fw.setTeletype(teletype);
			}
			
			if (fwd.getFwdName() != null) {
				String[] names = fwd.getFwdName().toArray(new String[fwd.getFwdName().size()]);
				fw.setName(names);
			}
			
			ArrayList<Itinerary> itinerary = new ArrayList<Itinerary>();
			
			if (fwd.getItinerary() != null) {
				for (WtqSegment itin : fwd.getItinerary()) {
					if (itin.getAirline() == null || itin.getAirline().trim().length() <= 0 || itin.getFlightnum() == null
							|| itin.getFlightnum().trim().length() <= 0 || itin.getLegfrom() == null
							|| itin.getLegfrom().trim().length() <= 0 || itin.getLegto() == null
							|| itin.getLegto().trim().length() <= 0 || itin.getDepartdate() == null) {
						continue;
					}
					Itinerary i = new Itinerary();
					i.setAirline(itin.getAirline());
					i.setArrivalCity(itin.getLegto());
					i.setDepartureCity(itin.getLegfrom());
					i.setFlightNumber(itin.getFlightnum());
					
					GregorianCalendar cal = new GregorianCalendar();
					cal.setTime(itin.getDepartdate());
					i.setFlightDate(cal);
					itinerary.add(i);
				}
			}
			
			
			
			
			fw.setItinerary(itinerary.toArray(new Itinerary[itinerary.size()]));
			

			

			SendForwardMessageDocument d = SendForwardMessageDocument.Factory.newInstance();
			SendForwardMessage c = d.addNewSendForwardMessage();

			c.setHeader(mapper.map(header, aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader.class));
			c.setForwardMessage(mapper.map(fw, aero.nettracer.serviceprovider.wt_1_0.common.xsd.ForwardMessage.class));

			try {
				SpringUtils.getClientEventHandler().doEventOnForward(fw);
			} catch (Exception e) {
				logger.error("Error performing client-specific BEORN Action...", e);
			}
			
			// STEP 2: SEND REQUEST
			SendForwardMessageResponseDocument r = stub.sendForwardMessage(d);
			WorldTracerResponse response = r.getSendForwardMessageResponse().getReturn();

			// STEP 3: BASIC PROCESS OF RESPONSE
			processResponseAndUpdateDto(dto, response);

			// STEP 4: CASE-SEPECIFIC PROCESSING
			
			if (response.getSuccess()) {
				returnValue = "SUCESS";
			} else {
				logger.info("Unable to send forward message: ");
				throw new WorldTracerException("Unable to send forward message: ");
			}

		} catch (AxisFault e) {
			e.printStackTrace();
			throw new WorldTracerException(CONNECTION_ERROR_UNABLE_TO_PERFORM_ACTION, e);
		} catch (RemoteException e) {
			e.printStackTrace();
			throw new WorldTracerException(CONNECTION_ERROR_UNABLE_TO_PERFORM_ACTION, e);
		}

		return returnValue;
	}

	@Override
	public String sendPxf(WtqRequestPxf pxf, WebServiceDto dto) throws WorldTracerException, CaptchaException {
		String retVal = "";
		try {
			WorldTracerServiceStub stub = getConfiguredServiceStub();

			RequestHeader header = generateHeader(dto);

			// STEP 1: BEGINNING OF FUNCTION SPECIFIC LOGIC
			Pxf data = new Pxf();
			
			WorldTracerRule RULE = new BasicRule(1, 2800, 3, WorldTracerRule.Format.FREE_FLOW);
			data.setContent(RULE.formatEntry(pxf.getFurtherInfo()));
			
			// data.setDestination(pxf.get); // No longer used
			data.setSendingStation(pxf.getAgent().getStation().getWt_stationcode());
			
			if (pxf.getTeletypes() != null) {
				String[] teletype = pxf.getTeletypes().toArray(new String[pxf.getTeletypes().size()]);
				data.setTeletype(teletype);
			}

			WT_PXF p = pxf.getPxf();
			
			ArrayList<PxfDetails> details = new ArrayList<PxfDetails>();
			PxfDetails a1 = new PxfDetails();
			a1.setAirline(p.getAirline_1());
			a1.setStation(p.getStation_1());
			a1.setArea(p.getArea_1());
			details.add(a1);
			
			
			PxfDetails a2 = new PxfDetails();
			if (p.getStation_2() != null && p.getStation_2().length() > 0) {
				a2.setAirline(p.getAirline_2());
				a2.setStation(p.getStation_2());
				a2.setArea(p.getArea_2());
				details.add(a2);
			}
			
			PxfDetails a3 = new PxfDetails();
			if (p.getStation_3() != null && p.getStation_3().length() > 0) {
				a3.setAirline(p.getAirline_3());
				a3.setStation(p.getStation_3());
				a3.setArea(p.getArea_3());
				details.add(a3);
			}
			
			PxfDetails a4 = new PxfDetails();
			if (p.getStation_4() != null && p.getStation_4().length() > 0) {
				a4.setAirline(p.getAirline_4());
				a4.setStation(p.getStation_4());
				a4.setArea(p.getArea_4());
				details.add(a4);
			}
			
			PxfDetails a5 = new PxfDetails();
			if (p.getStation_5() != null && p.getStation_5().length() > 0) {
				a5.setAirline(p.getAirline_5());
				a5.setStation(p.getStation_5());
				a5.setArea(p.getArea_5());
				details.add(a5);
			}
			
			
			data.setPxfDetails(details.toArray(new PxfDetails[details.size()]));
			
			PlaceActionFileDocument d = PlaceActionFileDocument.Factory.newInstance();
			PlaceActionFile c = d.addNewPlaceActionFile();

			c.setHeader(mapper.map(header, aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader.class));
			c.setPxf(mapper.map(data, aero.nettracer.serviceprovider.wt_1_0.common.xsd.Pxf.class));

			// STEP 2: SEND REQUEST
			PlaceActionFileResponseDocument r = stub.placeActionFile(d);
			WorldTracerResponse response = r.getPlaceActionFileResponse().getReturn();

			// STEP 3: BASIC PROCESS OF RESPONSE
			processResponseAndUpdateDto(dto, response);

			// STEP 4: CASE-SEPECIFIC PROCESSING
		} catch (AxisFault e) {
			e.printStackTrace();
			throw new WorldTracerException(CONNECTION_ERROR_UNABLE_TO_PERFORM_ACTION, e);
		} catch (RemoteException e) {
			e.printStackTrace();
			throw new WorldTracerException(CONNECTION_ERROR_UNABLE_TO_PERFORM_ACTION, e);
		}
		return retVal;
	}

	@Override
	public void suspendAHL(String wt_id, String agent, WebServiceDto dto) throws WorldTracerException, CaptchaException {

		try {
			WorldTracerServiceStub stub = getConfiguredServiceStub();

			RequestHeader header = generateHeader(dto);

			// STEP 1: BEGINNING OF FUNCTION SPECIFIC LOGIC
			Ahl ahl = new Ahl();
			ahl.setAhlId(wt_id);
			
			Agent ag = new Agent();
			ag.setUsername(agent);
			ahl.setAgent(ag);

			SuspendAhlDocument d = SuspendAhlDocument.Factory.newInstance();
			SuspendAhl c = d.addNewSuspendAhl();

			c.setHeader(mapper.map(header, aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader.class));
			c.setAhl(mapper.map(ahl, aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ahl.class));

			// STEP 2: SEND REQUEST
			SuspendAhlResponseDocument r = stub.suspendAhl(d);
			WorldTracerResponse response = r.getSuspendAhlResponse().getReturn();

			// STEP 3: BASIC PROCESS OF RESPONSE
			processResponseAndUpdateDto(dto, response);

			// STEP 4: CASE-SEPECIFIC PROCESSING
			
			if (!response.getSuccess()) {
				logger.info("Unable to suspend AHL: " + ahl.getAhlId());
				
				if (response != null && response.getError() != null && response.getError().getDescription() != null && response.getError().getDescription().length() > 0) {
					throw new WorldTracerException("Unable suspend AHL: " + ahl.getAhlId() + " " + response.getError().getDescription());
				}
				
				throw new WorldTracerException("Unable to suspend incident: " + ahl.getAhlId());
				
				
			}

		} catch (AxisFault e) {
			e.printStackTrace();
			throw new WorldTracerException(CONNECTION_ERROR_UNABLE_TO_PERFORM_ACTION, e);
		} catch (RemoteException e) {
			e.printStackTrace();
			throw new WorldTracerException(CONNECTION_ERROR_UNABLE_TO_PERFORM_ACTION, e);
		}

	}


	@Override
	@Deprecated
	public void initialize() {
		// No longer needed

	}

	@Override
	@Deprecated
	public void logout() {
		// No longer needed

	}

	public WebServiceDto getDto(HttpSession session) {
		WebServiceDto d = (WebServiceDto) session.getAttribute(TracingConstants.WEB_SERVICE_DTO);
		if (d == null) {
			d = new WebServiceDto();
			session.setAttribute(TracingConstants.WEB_SERVICE_DTO, d);
		}
		return d;
	}

	public static WebServiceDto getBasicDto(HttpSession session) {
		WebServiceDto d = (WebServiceDto) session.getAttribute(TracingConstants.WEB_SERVICE_DTO);
		if (d == null) {
			d = new WebServiceDto();
			session.setAttribute(TracingConstants.WEB_SERVICE_DTO, d);
		}
		return d;
	}
	
	private void mapIncidentToAhl(Incident i, Ahl a) {
		String wtCompanyCode = i.getStationcreated().getCompany().getCompanyCode_ID();
		
		Agent ag = getIncidentAgent(i);
		a.setAgent(ag);
		
		String c = checkString(i.getWt_id());
		if (c != null) {
			a.setAhlId(c);
		}
		if(i.getWtCompanyCode()!=null && i.getWtCompanyCode().length()>0){
			a.setAirlineCode(i.getWtCompanyCode());
		} else {
			a.setAirlineCode(i.getStationcreated().getCompany().getCompanyCode_ID());
		}
		
		if(i.getWtStationCode()!=null && i.getWtStationCode().length()==3){
			a.setStationCode(i.getWtStationCode().toUpperCase());
		} else {
			a.setStationCode(i.getStationcreated().getWt_stationcode());
		}
		
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(i.getCreatedate());

		a.setCreateDate(cal);
		a.setNumberBagsChecked(i.getNumbagchecked());
		a.setNumberPaxAffected(i.getNumpassengers());
		
		if (i.getLoss_code() != 0) {
			Company_specific_irregularity_code csic = LossCodeBMO.getLossCode(i.getLoss_code(), TracingConstants.LOST_DELAY, AdminUtils.getCompany(wtCompanyCode));
			a.setFaultReason(i.getLoss_code());
			a.setFaultStation(i.getFaultstation().getWt_stationcode());
			if (a.getFaultStation() == null || "ZZZ".equals(i.getFaultstation().getWt_stationcode())) {
				a.setFaultStation(i.getStationassigned().getWt_stationcode());
			}
			a.setFaultReasonDescription(csic.getDescription());
		} else {
			a.setFaultReason(79);
			a.setFaultReasonDescription("Created in error");
		}
		
		c = checkString(i.getRecordlocator());
		if (c != null) {
			a.setPnrLocator(c);
		}
		
		ArrayList<Passenger> paxList = new ArrayList<Passenger>();
		for (com.bagnet.nettracer.tracing.db.Passenger p: i.getPassenger_list()) {
			if (p != null) {
				Passenger pax = mapIncidentPassenger(p);
				paxList.add(pax);
			}
		}
		a.setPax(paxList.toArray(new Passenger[paxList.size()]));
		
		ArrayList<Expenses> ale = mapIncidentExpenses(i);
		a.setExpenses(ale.toArray(new Expenses[ale.size()]));
		
		ArrayList<Itinerary> aliPax = new ArrayList<Itinerary>();
		ArrayList<Itinerary> aliBag = new ArrayList<Itinerary>();
		
		for (com.bagnet.nettracer.tracing.db.Itinerary itin: i.getItinerary()) {
			if (itin != null) {
				Itinerary it = mapItinerary(itin);
				
				if (itin.getItinerarytype() == TracingConstants.PASSENGER_ROUTING) {
					aliPax.add(it);
				} else {
					aliBag.add(it);
				}
			}
		}
		
		if (PropertyBMO.isTrue(PropertyBMO.PROPERTY_BAG_ITIN_NOT_REQUIRED)
			&& aliBag.size() == 0){
			aliBag = aliPax;
		}

		
		a.setBagItinerary(aliBag.toArray(new Itinerary[aliBag.size()]));
		a.setPaxItinerary(aliPax.toArray(new Itinerary[aliPax.size()]));
		
		ArrayList<aero.nettracer.serviceprovider.wt_1_0.common.Item> items = new ArrayList<aero.nettracer.serviceprovider.wt_1_0.common.Item>();
		for (Item item: i.getItemlist()) {
			if (item != null) {
				mapIncidentItem(items, item);
			}
		}
		a.setItem(items.toArray(new aero.nettracer.serviceprovider.wt_1_0.common.Item[items.size()]));
		
		ArrayList<ClaimCheck> claimChecks = new ArrayList<ClaimCheck>();
		for (Incident_Claimcheck cc: i.getClaimcheck_list()) {
			/*
	    	 * Checking for UTB tag - We don't submit Untagged Bagtags to World Tracer
	    	 */
			if(cc.getClaimchecknum()!=null && cc.getClaimchecknum().length()>0 && !(cc.getClaimchecknum().length()>3 && cc.getClaimchecknum().substring(0, 3).toUpperCase().equals(TracingConstants.UTB_CHECK))){
				ClaimCheck cl = new ClaimCheck();
				cl.setTagNumber(cc.getClaimchecknum());
				claimChecks.add(cl);
			}
		}
		a.setClaimCheck(claimChecks.toArray(new ClaimCheck[claimChecks.size()]));
		if (i.getTracingComplete() != null) {
			Calendar traceComp = Calendar.getInstance();
			traceComp.setTime(i.getTracingComplete());
			a.setTracingFinalized(traceComp);
		}
		
		a.setFurtherInfo(i.getIncident_ID()+"");
	}


	private Passenger mapIncidentPassenger(com.bagnet.nettracer.tracing.db.Passenger p) {
		com.bagnet.nettracer.tracing.db.Address ad = p.getAddress(0);
		Passenger pax = new Passenger();
		Address add = new Address();
		pax.setAddress(add);
		
		pax.setFfAirline(p.getAirlinememcompany());
		pax.setFfNumber(p.getAirlinememnumber());
		pax.setFfStatus(p.getAirlinememstatus());
		pax.setFirstname(p.getFirstname());
		pax.setLastname(p.getLastname());
		pax.setMiddlename(p.getMiddlename());
		pax.setSalutation(p.getSalutation());
		pax.setLanguageFreeFlow(p.getLanguageFreeFlow());
		
		add.setAddress1(ad.getAddress1());
		add.setAddress2(ad.getAddress2());
		add.setAltPhone(ad.getAltphone());
		add.setCity(ad.getCity());
		if(PropertyBMO.isTrue(PropertyBMO.PROPERTY_WT_USE_COUNTRY_NAME)){
			String country = ad.getCountry();
			if(country.length() >  MAX_COUNTRY_LENGTH){
				country = country.substring(0, MAX_COUNTRY_LENGTH);
			}
			add.setCountryCode(country);
		} else {
			add.setCountryCode(ad.getCountrycode_ID());
		}
		add.setEmailAddress(ad.getEmail());
		add.setHomePhone(ad.getHomephone());
		add.setMobilePhone(ad.getMobile());
		add.setPagerNumber(ad.getPager());
		add.setProvince(ad.getProvince());
		if(PropertyBMO.isTrue(PropertyBMO.PROPERTY_WT_USE_STATE_NAME)){
			String state = ad.getState();
			if(state.length() >  MAX_STATEPROV_LENGTH){
				state = state.substring(0, MAX_STATEPROV_LENGTH);
			}
			add.setState(state);
		} else {
			add.setState(ad.getState_ID());
		}
		add.setTemporaryAddress(!ad.isPermanent());
		add.setWorkPhone(ad.getWorkphone());
		add.setZip(ad.getZip());
		return pax;
	}
	
	private Passenger mapOhdPassenger(com.bagnet.nettracer.tracing.db.OHD_Passenger p) {
		com.bagnet.nettracer.tracing.db.OHD_Address ad = p.getAddress(0);
		Passenger pax = new Passenger();
		Address add = new Address();
		pax.setAddress(add);
		
		pax.setFirstname(p.getFirstname());
		pax.setLastname(p.getLastname());
		pax.setMiddlename(p.getMiddlename());
		
		add.setAddress1(ad.getAddress1());
		add.setAddress2(ad.getAddress2());
		add.setAltPhone(ad.getAltphone());
		add.setCity(ad.getCity());
		if(PropertyBMO.isTrue(PropertyBMO.PROPERTY_WT_USE_COUNTRY_NAME)){
			String country = ad.getCountry();
			if(country.length() >  MAX_COUNTRY_LENGTH){
				country = country.substring(0, MAX_COUNTRY_LENGTH);
			}
			add.setCountryCode(country);
		} else {
			add.setCountryCode(ad.getCountrycode_ID());
		}
		add.setEmailAddress(ad.getEmail());
		add.setHomePhone(ad.getHomephone());
		add.setMobilePhone(ad.getMobile());
		add.setPagerNumber(ad.getPager());
		add.setProvince(ad.getProvince());
		if(PropertyBMO.isTrue(PropertyBMO.PROPERTY_WT_USE_STATE_NAME)){
			String state = ad.getState();
			if(state.length() >  MAX_STATEPROV_LENGTH){
				state = state.substring(0, MAX_STATEPROV_LENGTH);
			}
			add.setState(state);
		} else {
			add.setState(ad.getState_ID());
		}
		add.setWorkPhone(ad.getWorkphone());
		add.setZip(ad.getZip());
		return pax;
	}


	private void mapIncidentItem(ArrayList<aero.nettracer.serviceprovider.wt_1_0.common.Item> items, Item item) {
		aero.nettracer.serviceprovider.wt_1_0.common.Item it = new aero.nettracer.serviceprovider.wt_1_0.common.Item();
	
		it.setBagNumber(item.getBagnumber());
		it.setColor(item.getColor());
		
		ArrayList<Content> contents = new ArrayList<Content>();
		for (Item_Inventory inv: (List<Item_Inventory>)(item.getInventorylist())) {
			Content content = new Content();
			
			try {
				content.setCategory(CategoryBMO.getCategory(inv.getCategorytype_ID(), TracingConstants.DEFAULT_LOCALE).getWtCategory());
			} catch (NullPointerException e) {
				content.setCategory("UNKNOWN");
			}

			content.setDescription(inv.getDescription().trim().toUpperCase());
			contents.add(content);
		}
		
		it.setContent(contents.toArray(new Content[contents.size()]));
		it.setDesc1(item.getX1());
		it.setDesc2(item.getX2());
		it.setDesc3(item.getX3());
		it.setFirstNameOnBag(item.getFnameonbag());
		it.setLastNameOnBag(item.getLnameonbag());
		it.setManufacturer(item.getManufacturer());
		it.setType(item.getBagtype());
		it.setExternaldesc(item.getExternaldesc());
		items.add(it);
	}


	private Itinerary mapItinerary(com.bagnet.nettracer.tracing.db.Itinerary itin) {
		Itinerary it = new Itinerary();
		
		it.setAirline(itin.getAirline());
		it.setArrivalCity(itin.getLegto());
		it.setDepartureCity(itin.getLegfrom());
		
		GregorianCalendar cal2 = new GregorianCalendar();
		cal2.setTime(itin.getDepartdate());
		
		it.setFlightDate(cal2);
		it.setFlightNumber(itin.getFlightnum());
		it.setLegfrom_type(itin.getLegfrom_type());
		it.setLegto_type(itin.getLegto_type());
		return it;
	}

	private Itinerary mapOhdItinerary(com.bagnet.nettracer.tracing.db.OHD_Itinerary itin) {
		Itinerary it = new Itinerary();
		
		it.setAirline(itin.getAirline());
		it.setArrivalCity(itin.getLegto());
		it.setDepartureCity(itin.getLegfrom());
		
		GregorianCalendar cal2 = new GregorianCalendar();
		cal2.setTime(itin.getDepartdate());
		
		it.setFlightDate(cal2);
		it.setFlightNumber(itin.getFlightnum());
		it.setLegfrom_type(itin.getLegfrom_type());
		it.setLegto_type(itin.getLegto_type());
		return it;
	}

	private ArrayList<Expenses> mapIncidentExpenses(Incident i) {
		ArrayList<Expenses> ale = new ArrayList<Expenses>();
		String cs_fmt = "%02d %s/%s%1.2f";
		int claimCount = 0;
		if (i.getClaims() != null && !i.getClaims().isEmpty()) {
			String cost;

			if (i.getExpenses() != null) {
				for (ExpensePayout expense : i.getExpenses()) {
					if (expense.getApproval_date() != null && expense.getCurrency_ID() != null) {
						claimCount++;
						Expenses e = new Expenses();
						e.setPaycode(expense.getPaycode());
						e.setCheckamt((float)expense.getCheckamt());
						e.setCurrrency(expense.getCurrency_ID());
						ale.add(e);
					}
				}
			}
		}
		
		// see if we added a CS
		if (claimCount == 0) {
			Expenses e = new Expenses();
			e.setPaycode("X");
			e.setCheckamt(0);
			e.setCurrrency("USD");
			ale.add(e);
		}
		return ale;
	}
	
	private String checkString(String s) {
		if (s != null && s.length() > 0) {
			return s;
		}
		return null;
	}


	private Agent getIncidentAgent(Incident i) {
		Agent ag = new Agent();
		ag.setAirline(i.getAgent().getCompanycode_ID());
		ag.setUsername(i.getAgent().getUsername());
		return ag;
	}
	
	private Agent getOhdAgent(OHD i) {
		Agent ag = new Agent();
		ag.setAirline(i.getAgent().getCompanycode_ID());
		ag.setUsername(i.getAgent().getUsername());
		return ag;
	}
	

	private void mapOhdToWt(OHD onhand, Ohd ohd) {
		ohd.setAgent(getOhdAgent(onhand));
		
		ohd.setOhdId(onhand.getWt_id());
//		ohd.setFaultReason(faultReason);
//		ohd.setFaultStation(faultStation);

		
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(onhand.getFounddate());
		ohd.setCreateDate(cal);
		
		ohd.setPnrLocator(onhand.getRecord_locator());
		ohd.setAirlineCode(onhand.getAgent().getStation().getCompany().getCompanyCode_ID());
		/*
    	 * Checking for UTB tag - We don't submit Untagged Bagtags to World Tracer
    	 */
		if(onhand.getClaimnum()!=null && onhand.getClaimnum().length()>0 && !(onhand.getClaimnum().length()>3 && onhand.getClaimnum().substring(0, 3).toUpperCase().equals(TracingConstants.UTB_CHECK))){
			ClaimCheck claimCheck = new ClaimCheck();
			claimCheck.setTagNumber(onhand.getClaimnum());
			ohd.setClaimCheck(claimCheck);
		}
		ohd.setStationCode(onhand.getHoldingStation().getWt_stationcode());
		ohd.setStorageLocation(onhand.getStorage_location());
		
		ohd.setFurtherInfo(onhand.getOHD_ID());

		aero.nettracer.serviceprovider.wt_1_0.common.Item item = new aero.nettracer.serviceprovider.wt_1_0.common.Item();
		item.setColor(onhand.getColor());
		item.setDesc1(onhand.getX1());
		item.setDesc2(onhand.getX2());
		item.setDesc3(onhand.getX3());
		item.setFirstNameOnBag(onhand.getFirstname());
		item.setLastNameOnBag(onhand.getLastname());
		item.setManufacturer(onhand.getManufacturer());
		item.setExternaldesc(onhand.getExternaldesc());
		item.setType(onhand.getType());		
		ohd.setItem(item);
		
		ArrayList<Content> contents = new ArrayList<Content>();
		for (OHD_Inventory inv: (Set<OHD_Inventory>)(onhand.getItems())) {
			Content content = new Content();
			try {
				content.setCategory(CategoryBMO.getCategory(inv.getOHD_categorytype_ID(), TracingConstants.DEFAULT_LOCALE).getWtCategory());
			} catch (NullPointerException e) {
				content.setCategory("UNKNOWN");
			}
			content.setDescription(inv.getDescription().trim().toUpperCase());
			contents.add(content);
			
		}
		item.setContent(contents.toArray(new Content[contents.size()]));
		
		ArrayList<Passenger> paxList = new ArrayList<Passenger>();
		int count = 0;
		for (com.bagnet.nettracer.tracing.db.OHD_Passenger p: (Set<OHD_Passenger>) onhand.getPassengers()) {
			if (p != null) {
				Passenger pax = mapOhdPassenger(p);
				paxList.add(pax);
				if (count == 0 && onhand.getMembership() != null) {
					AirlineMembership mem = onhand.getMembership();
					String c = checkString(mem.getCompanycode_ID());
					pax.setFfAirline(c);
					c = checkString(mem.getMembershipnum());
					pax.setFfNumber(c);
					c = checkString(mem.getMembershipstatus());
					pax.setFfStatus(c);
				}
				++count;
			}
		}
		ohd.setPax(paxList.toArray(new Passenger[paxList.size()]));
		
		ArrayList<Itinerary> aliPax = new ArrayList<Itinerary>();
		ArrayList<Itinerary> aliBag = new ArrayList<Itinerary>();
		
		for (OHD_Itinerary itin: (Set<OHD_Itinerary>)(onhand.getItinerary())) {
			if (itin != null) {
				Itinerary it = mapOhdItinerary(itin);
				
				if (itin.getItinerarytype() == TracingConstants.PASSENGER_ROUTING) {
					aliPax.add(it);
				} else {
					aliBag.add(it);
				}
			}
		}
		ohd.setBagItinerary(aliBag.toArray(new Itinerary[aliBag.size()]));
		ohd.setPaxItinerary(aliPax.toArray(new Itinerary[aliPax.size()]));

	}
	
	@Override
	public void reinstateOHD(String wt_id, String agent, WebServiceDto dto) throws WorldTracerException, CaptchaException {
		try {
			WorldTracerServiceStub stub = getConfiguredServiceStub();

			RequestHeader header = generateHeader(dto);

			// STEP 1: BEGINNING OF FUNCTION SPECIFIC LOGIC
			Ohd ohd = new Ohd();
			ohd.setOhdId(wt_id);
			
			Agent ag = new Agent();
			ag.setUsername(agent);
			ohd.setAgent(ag);

			ReinstateOhdDocument d = ReinstateOhdDocument.Factory.newInstance();
			ReinstateOhd c = d.addNewReinstateOhd();

			c.setHeader(mapper.map(header, aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader.class));
			c.setOhd(mapper.map(ohd, aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd.class));

			// STEP 2: SEND REQUEST
			ReinstateOhdResponseDocument r = stub.reinstateOhd(d);
			WorldTracerResponse response = r.getReinstateOhdResponse().getReturn();

			// STEP 3: BASIC PROCESS OF RESPONSE
			processResponseAndUpdateDto(dto, response);

			// STEP 4: CASE-SEPECIFIC PROCESSING
			
			if (!response.getSuccess()) {
				logger.info("Unable to reinstate OHD: " + ohd.getOhdId());
				throw new WorldTracerException("Unable to reinstate OHD: " + ohd.getOhdId());
			}

		} catch (AxisFault e) {
			e.printStackTrace();
			throw new WorldTracerException(CONNECTION_ERROR_UNABLE_TO_PERFORM_ACTION, e);
		} catch (RemoteException e) {
			e.printStackTrace();
			throw new WorldTracerException(CONNECTION_ERROR_UNABLE_TO_PERFORM_ACTION, e);
		}

	}
	
	@Override
	public void suspendOHD(String wt_id, String agent, WebServiceDto dto) throws WorldTracerException, CaptchaException {
		try {
			WorldTracerServiceStub stub = getConfiguredServiceStub();

			RequestHeader header = generateHeader(dto);

			// STEP 1: BEGINNING OF FUNCTION SPECIFIC LOGIC
			Ohd ohd = new Ohd();
			ohd.setOhdId(wt_id);
			
			Agent ag = new Agent();
			ag.setUsername(agent);
			ohd.setAgent(ag);


			SuspendOhdDocument d = SuspendOhdDocument.Factory.newInstance();
			SuspendOhd c = d.addNewSuspendOhd();

			c.setHeader(mapper.map(header, aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader.class));
			c.setOhd(mapper.map(ohd, aero.nettracer.serviceprovider.wt_1_0.common.xsd.Ohd.class));

			// STEP 2: SEND REQUEST
			SuspendOhdResponseDocument r = stub.suspendOhd(d);
			WorldTracerResponse response = r.getSuspendOhdResponse().getReturn();

			// STEP 3: BASIC PROCESS OF RESPONSE
			processResponseAndUpdateDto(dto, response);

			// STEP 4: CASE-SEPECIFIC PROCESSING
			
			if (!response.getSuccess()) {
				logger.info("Unable to reinstate OHD: " + ohd.getOhdId());
				throw new WorldTracerException("Unable to reinstate OHD: " + ohd.getOhdId());
			}

		} catch (AxisFault e) {
			e.printStackTrace();
			throw new WorldTracerException(CONNECTION_ERROR_UNABLE_TO_PERFORM_ACTION, e);
		} catch (RemoteException e) {
			e.printStackTrace();
			throw new WorldTracerException(CONNECTION_ERROR_UNABLE_TO_PERFORM_ACTION, e);
		}
	}





	@Override
	public String sendQoh(WtqQoh wtqQoh, WebServiceDto dto) throws WorldTracerException, CaptchaException {
		try {
			WorldTracerServiceStub stub = getConfiguredServiceStub();

			RequestHeader header = generateHeader(dto);

			// STEP 1: BEGINNING OF FUNCTION SPECIFIC LOGIC
			Qoh qoh = new Qoh();
			Station holdingStation = null;
			Collection<OHD> x = wtqQoh.getOhdTags();
			ArrayList<Tag> tags = new ArrayList<Tag>();
			for (OHD ohd: x) {
				
				if (holdingStation == null) {
					holdingStation = ohd.getHoldingStation();
				}
				
				String claimNum = ohd.getClaimnum().trim();
				/*
		    	 * Checking for UTB tag - We don't submit Untagged Bagtags to World Tracer for Quick On Hands
		    	 */
				if(claimNum!=null && claimNum.length()>0 && !(claimNum.length()>3 && claimNum.substring(0, 3).toUpperCase().equals(TracingConstants.UTB_CHECK))){
					Tag tag = new Tag();
					tag.setTagSequence(claimNum);
					tags.add(tag);
				}
			}
			
			
			qoh.setStationCode(holdingStation.getStationcode());
			qoh.setTags(tags.toArray(new Tag[tags.size()]));


			SendQohDocument d = SendQohDocument.Factory.newInstance();
			SendQoh c = d.addNewSendQoh();
			

			c.setHeader(mapper.map(header, aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader.class));
			c.setOhd(mapper.map(qoh, aero.nettracer.serviceprovider.wt_1_0.common.xsd.Qoh.class));
			
			
			// STEP 2: SEND REQUEST
			
			SendQohResponseDocument r = stub.sendQoh(d);
			WorldTracerResponse response = r.getSendQohResponse().getReturn();
			logger.info(response);
			// STEP 3: BASIC PROCESS OF RESPONSE
			processResponseAndUpdateDto(dto, response);

			// STEP 4: CASE-SEPECIFIC PROCESSING
			
			if (!response.getSuccess()) {
				logger.info("Unable to process QOH Queue ID: " + wtqQoh.getWt_queue_id());
				throw new WorldTracerException("Unable to process QOH Queue ID: " + wtqQoh.getWt_queue_id());
			}

		} catch (AxisFault e) {
			e.printStackTrace();
			throw new WorldTracerException(CONNECTION_ERROR_UNABLE_TO_PERFORM_ACTION, e);
		} catch (RemoteException e) {
			e.printStackTrace();
			throw new WorldTracerException(CONNECTION_ERROR_UNABLE_TO_PERFORM_ACTION, e);
		}
		return null;
	}


}
