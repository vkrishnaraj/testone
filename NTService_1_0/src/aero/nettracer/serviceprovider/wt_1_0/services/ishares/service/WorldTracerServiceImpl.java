package aero.nettracer.serviceprovider.wt_1_0.services.ishares.service;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpException;

import aero.nettracer.serviceprovider.wt_1_0.common.ActionFile;
import aero.nettracer.serviceprovider.wt_1_0.common.ActionFileCount;
import aero.nettracer.serviceprovider.wt_1_0.common.ActionFileRequestData;
import aero.nettracer.serviceprovider.wt_1_0.common.Ahl;
import aero.nettracer.serviceprovider.wt_1_0.common.Bdo;
import aero.nettracer.serviceprovider.wt_1_0.common.ForwardMessage;
import aero.nettracer.serviceprovider.wt_1_0.common.ForwardOhd;
import aero.nettracer.serviceprovider.wt_1_0.common.Ohd;
import aero.nettracer.serviceprovider.wt_1_0.common.Passenger;
import aero.nettracer.serviceprovider.wt_1_0.common.Pxf;
import aero.nettracer.serviceprovider.wt_1_0.common.PxfDetails;
import aero.nettracer.serviceprovider.wt_1_0.common.RequestOhd;
import aero.nettracer.serviceprovider.wt_1_0.common.WorldTracerResponse;
import aero.nettracer.serviceprovider.wt_1_0.dto.WorldTracerActionDTO;
import aero.nettracer.serviceprovider.wt_1_0.services.PreProcessor;
import aero.nettracer.serviceprovider.wt_1_0.services.WorldTracerException;
import aero.nettracer.serviceprovider.wt_1_0.services.DefaultWorldTracerService.WorldTracerField;
import aero.nettracer.serviceprovider.wt_1_0.services.ishares.connection.ISharesHttpClient;
import aero.nettracer.serviceprovider.wt_1_0.services.wtrweb.service.WorldTracerService;


public class WorldTracerServiceImpl implements WorldTracerService {

	private WorldTracerActionDTO dto = null;
	private ISharesHttpClient connection = null;
	private String connectionType = "WM";
	private boolean unitTest = false;
	private int unitTestNumber = 0;
	public static final int UNIT_TEST_SUCCESS = 0;
	public static final int UNIT_TEST_FAILURE = 1;
	static Pattern commandResponsePattern = Pattern.compile("<PRE>((.*\\n)*.*)</PRE>", Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);

	public WorldTracerServiceImpl(WorldTracerActionDTO dto) {
		this.dto = dto;
		connection = (ISharesHttpClient) dto.getConnection();
	}
	
	public WorldTracerServiceImpl(WorldTracerActionDTO dto, boolean unitTest, int unitTestNumber) {
		this.dto = dto;
		this.unitTest = unitTest;
		this.unitTestNumber = unitTestNumber;
	}
	
	public WorldTracerServiceImpl(WorldTracerActionDTO dto, boolean unitTest) {
		this.dto = dto;
		this.unitTest = unitTest;
	}
	
	private String sendCommand(String methodName, String command) throws CommandNotProperlyFormedException, HttpException, IOException {
		// Test for presence of any remaining {} characters and throw exception
		command = testCommand(command);
		String response = connection.sendCommand(methodName, command);
		
		Matcher m = commandResponsePattern.matcher(response);
		if (m.find()) {
			return m.group(1);
		}
		
		return null;
	}
	
	private String inputValuesIntoCommand(HashMap<String, String> map,
			String command) {
		String output = command;
		for (String key: map.keySet()) {
			String replaceValue = map.get(key);
			try {
				if (replaceValue != null) output = output.replaceAll("\\{" + key + "\\}", replaceValue);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return output;
	}
	

	private static String testCommand(String command) throws CommandNotProperlyFormedException {
		if (command.contains("{") || command.contains("}")) throw new CommandNotProperlyFormedException();
		command = command.replaceAll("\\[.*\\]", "");
		return command;
	}
	
	public void reinstateAhl(WorldTracerActionDTO dto, Ahl ahl,
			WorldTracerResponse response) throws CommandNotProperlyFormedException {
		// DO NOT IMPLMENT
	}

	public void sendForwardMessage(WorldTracerActionDTO dto,
			ForwardMessage msg, WorldTracerResponse response) throws CommandNotProperlyFormedException {
		// DO NOT IMPLMENT
	}

	public void suspendAhl(WorldTracerActionDTO dto, Ahl ahl,
			WorldTracerResponse response) throws CommandNotProperlyFormedException {
		// DO NOT IMPLMENT
	}

	public void amendOhd(WorldTracerActionDTO dto, Ohd data,
			WorldTracerResponse response) throws CommandNotProperlyFormedException {
		// DO NOT IMPLEMENT
	}


	public void amendAhl(WorldTracerActionDTO dto, Ahl data,
			WorldTracerResponse response) throws CommandNotProperlyFormedException {
		// DO NOT IMPLEMENT
	}

	public void closeAhl(WorldTracerActionDTO dto, Ahl ahl,
			WorldTracerResponse response) throws CommandNotProperlyFormedException {
		// DO NOT IMPLEMENT
	}

	public void closeOhd(WorldTracerActionDTO dto, Ohd data,
			WorldTracerResponse response) throws CommandNotProperlyFormedException {
		// DO NOT IMPLEMENT
	}

	public void createAhl(WorldTracerActionDTO dto, Ahl data,
			WorldTracerResponse response) throws CommandNotProperlyFormedException {
		// DO NOT IMPLEMENT
	}
	
	public void getAhl(WorldTracerActionDTO dto, Ahl data,
			WorldTracerResponse response) throws CommandNotProperlyFormedException {
		// DO NOT IMPLEMENT
	}

	public void getOhd(WorldTracerActionDTO dto, Ohd data,
			WorldTracerResponse response) throws CommandNotProperlyFormedException {
		// DO NOT IMPLEMENT
	}
	
	public void forwardOhd(WorldTracerActionDTO dto, ForwardOhd data,
			WorldTracerResponse response) throws CommandNotProperlyFormedException {
		// DO NOT IMPLEMENT
	}
	
	public void createOhd(WorldTracerActionDTO dto, Ohd data,
			WorldTracerResponse response) throws CommandNotProperlyFormedException {
		// DO NOT IMPLEMENT
	}


	
	/**
	 * Example Method to Process Commands
	 * @param dto
	 * @param data
	 * @param response
	 * @throws CommandNotProperlyFormedException 
	 * @throws IOException 
	 * @throws HttpException 
	 */
	public void getActionFileCounts(WorldTracerActionDTO dto,
			ActionFileRequestData data, WorldTracerResponse response) throws CommandNotProperlyFormedException, HttpException, IOException {
		
		// Prepare variables
		String specificCommandType = "CXF";
		boolean success = false;
		
		// If applicable, perform any pre-processing required to obtain a
		// hashmap of field names
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("COMMAND", specificCommandType);
		map.put("CONNECTION_TYPE", connectionType);
		map.put("STATION_CODE", data.getStation());
		map.put("AIRLINE_CODE", data.getAirline());
		
		// Generate Command from Hash (Example means provided)
		String command = "{CONNECTION_TYPE} {COMMAND} {STATION_CODE}{AIRLINE_CODE}";
		command = inputValuesIntoCommand(map, command);
		
		// Send Command
		String responseTxt = null;
		if (unitTest) {
			responseTxt = MockISharesResponse.mockGetActionFileCountsCommand(command);
		} else {
			responseTxt = sendCommand(specificCommandType, command);
		}
		
		// Process Response and insert any necessary data into the response
		// object & set success variable if successful
		ActionFileCount[] counts = ISharesResponseParser.processActionfileResponse(responseTxt);
		response.setCounts(counts);
		success = true;
		
		// On success, set success to true (defaults to false)
		if (success) {
			response.setSuccess(true);
		}
	}


	public void insertBdo(WorldTracerActionDTO dto, Bdo bdo,
			WorldTracerResponse response) throws CommandNotProperlyFormedException, HttpException, IOException {
		// TODO Auto-generated method stub
		boolean success = false;
		String specificCommandType = "BDO";
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("COMMAND_TYPE", connectionType);
		map.put("FILE_TYPE", "AHL");
		map.put("WT_AHL_ID", bdo.getAhlId());  
		map.put("DELIVERY_SERVICE","DS");
		map.put("STATION_CODE", bdo.getStationCode());
		map.put("AIRLINE_CODE", bdo.getStationCode());
		map.put("DELIVERY_SERVICE_ID", "01");  //Assume	that we will always use the first delivery company in the predefined WT list
		map.put("COLOR_TYPE", "01");
		
		String command = "{COMMAND_TYPE} BDO {FILE_TYPE} {WT_AHL_ID} \n {DELIVERY SERVICE} {STATION_CODE}{AIRLINE_CODE}{DELIVERY_SERVICE_ID}/{COLOR_TYPE}";
		
		command = inputValuesIntoCommand(map, command);
		
		// Send Command - The first BDO call will return a mask that will need to be resubmitted with the DD(delivery date) and .AG (Agent Code)
		String responseTxt = null;
		if (unitTest) {
			responseTxt = MockISharesResponse.mockRequestBDOCommand_1(command);
		} else {
			responseTxt = sendCommand(specificCommandType, command);
		}
		//The mask should now be in responseTxt - need to add DD and .AG
		if (responseTxt.contains("DD")& responseTxt.contains(".AG")){
			responseTxt = responseTxt.replaceAll("DD", "DD"+PreProcessor.ITIN_DATE_FORMAT.format(bdo.getDeliveryDate()));
			responseTxt = responseTxt.replaceAll(".AG", ".AG"+PreProcessor.getAgentEntry(bdo.getAgent()));
			if (unitTest) {
				responseTxt = MockISharesResponse.mockRequestBDOCommand_2(command);
			} else {
				responseTxt = sendCommand(specificCommandType, command);
			}
			//sample success message "WM BDO AHL XAXUS10485                   /-OK-/19NOV09 1516GMT"
			if (responseTxt.contains("-OK-")) {
				response.setSuccess(true);
			} 
		} else {
			response.setSuccess(success);
		}
		
		
	}


	public void eraseActionFile(WorldTracerActionDTO dto,
			ActionFileRequestData data, WorldTracerResponse response) throws CommandNotProperlyFormedException, HttpException, IOException {
		// TODO Auto-generated method stub
		// Prepare variables
		boolean success = false;
		
		// If applicable, perform any pre-processing required to obtain a
		// hashmap of field names
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("COMMAND_TYPE", connectionType);
		map.put("STATION_CODE", data.getStation());
		map.put("AIRLINE_CODE", data.getAirline());
		map.put("AREA_CODE", data.getType());
		map.put("DAY", data.getDay()+"");
		map.put("NUMBER", data.getNumber()+"");
		
		// Generate Command from Hash (Example means provided)
		String command = "{COMMAND_TYPE} EXF {STATION_CODE}{AIRLINE_CODE} {AREA_CODE} D{DAY}/{NUMBER}";

		command = inputValuesIntoCommand(map, command);
		
		// Send Command
		String responseTxt = null;
		if (unitTest) {
			switch (unitTestNumber) {
				case UNIT_TEST_SUCCESS: 
					responseTxt = MockISharesResponse.mockExfSuccessCommand(command);
					break;
				case UNIT_TEST_FAILURE:
					responseTxt = MockISharesResponse.mockExfFailureCommand(command);
					break;
			}
		} else {
			responseTxt = sendCommand("EXF", command);
		}
		
		// Process Response and insert any necessary data into the response
		// object & set success variable if successful
		// update here for erasing Action File
		
		// On success, set success to true (defaults to false)
		if (responseTxt.contains("-OK-")) {
			response.setSuccess(true);
		}

		
	}

	/**
	 * Method to Process Request On-Hand Commands
	 * @param dto
	 * @param data
	 * @param response
	 * @throws CommandNotProperlyFormedException 
	 * @throws WorldTracerException 
	 * @throws IOException 
	 * @throws HttpException 
	 */
	public void requestOhd(WorldTracerActionDTO dto, RequestOhd data,
			WorldTracerResponse response) throws CommandNotProperlyFormedException, WorldTracerException, HttpException, IOException {
		// TODO Auto-generated method stub
		// Prepare variables
		Map<WorldTracerField, List<String>> fieldMap = PreProcessor.requestOhd(dto, data, response);
		//TODO: CHECK THIS EnumMap<WorldTracerField, WorldTracerRule<String>> RULES = wtRuleMap.getRule(TxType.REQUEST_QOH);
		
		boolean success = false;
		
		// If applicable, perform any pre-processing required to obtain a
		// hashmap of field names
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("COMMAND_TYPE", connectionType);
		map.put("AHL_FILE_REFERENCE", data.getAhl().getAhlId());
		map.put("OHD_FILE_REFERENCE_NUMBER", data.getOhdId());
		map.put("FI_FREE_FORM_TEXT", ((List<String>)fieldMap.get(WorldTracerField.FI)).get(0));
		map.put("AGENT_ID", ((List<String>)fieldMap.get(WorldTracerField.AG)).get(0));
		
		// teletypes - optional field
		String[] myTeletypes = data.getTeletype();
		if(myTeletypes != null) {
			String myTX_LINE = "TX ";
			List<String> txs = Arrays.asList(myTeletypes);
			for(String tx: txs) {
				myTX_LINE += tx + "/";
			}
			// replace the last / with \n 
			myTX_LINE = myTX_LINE.substring(0, myTX_LINE.length()-1);
			myTX_LINE += "\n";
			map.put("[TX_LINE]", myTX_LINE);
		}
		
		// name - optional field
		Passenger myPax = data.getAhl().getPax()[0];
		if(myPax != null) {
			String myNAME_LINE = "NM " + myPax.getLastname();
			map.put("[NAME_LINE]", myNAME_LINE);
		}
		
		// Generate Command from Hash (Example means provided)
		String command = "{COMMAND_TYPE} ROH {AHL_FILE_REFERENCE}\n" +
					     "OHD {OHD_FILE_REFERENCE_NUMBER}\n" +
					   	 "FI {FI_FREE_FORM_TEXT}\n" +
					   	 "AG {AGENT_ID}\n" +
					   	 "SI {FI_FREE_FORM_TEXT}\n" +
					   	 "[TX_LINE]\n" +
					   	 "[NAME_LINE]";
		command = inputValuesIntoCommand(map, command);
		
		// Send Command
		String responseTxt = null;
		if (unitTest) {
			responseTxt = MockISharesResponse.mockRohCommand(command);
		} else {
			responseTxt = sendCommand("ROH", command);
		}
		
		// Process Response and insert any necessary data into the response
		// object & set success variable if successful
		// parse the result from iShare 
		Ohd myOhd = null;
		
		success = true;

		// On success, set success to true (defaults to false)
		if (responseTxt.contains("WM SUS AHL")) {
			response.setSuccess(true);
		}
		
	}



	public void requestQuickOhd(WorldTracerActionDTO dto, RequestOhd data,
			WorldTracerResponse response) throws CommandNotProperlyFormedException, WorldTracerException, HttpException, IOException {
		// TODO Auto-generated method stub
		Map<WorldTracerField, List<String>> fieldMap = PreProcessor.requestQuickOhd(dto, data, response);
		//TODO: CHECK THIS EnumMap<WorldTracerField, WorldTracerRule<String>> RULES = wtRuleMap.getRule(TxType.REQUEST_QOH);
		
		boolean success = false;
		
		// If applicable, perform any pre-processing required to obtain a
		// hashmap of field names
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("COMMAND_TYPE", connectionType);
		map.put("AHL_FILE_REFERENCE", data.getAhl().getAhlId());
		map.put("FROM_STATION", data.getFromStation());
		map.put("FROM_AIRLINE", data.getFromAirline());
		map.put("TAG_NUMBER", ((List<String>)fieldMap.get(WorldTracerField.TN)).get(0));
		map.put("FI_FREE_FORM_TEXT", ((List<String>)fieldMap.get(WorldTracerField.FI)).get(0));
		map.put("AGENT_ID", ((List<String>)fieldMap.get(WorldTracerField.AG)).get(0));
		
		// teletypes - optional field
		String[] myTeletypes = data.getTeletype();
		if(myTeletypes != null) {
			String myTX_LINE = "TX ";
			List<String> txs = Arrays.asList(myTeletypes);
			for(String tx: txs) {
				myTX_LINE += tx + "/";
			}
			// replace the last / with \n 
			myTX_LINE = myTX_LINE.substring(0, myTX_LINE.length()-1);
			myTX_LINE += "\n";
			map.put("[TX_LINE]", myTX_LINE);
		}
		
		// name - optional field
		Passenger myPax = data.getAhl().getPax()[0];
		if(myPax != null) {
			String myNAME_LINE = "NM " + myPax.getLastname();
			map.put("[NAME_LINE]", myNAME_LINE);
		}
		
		// Generate Command from Hash (Example means provided)
		String command = "{COMMAND_TYPE} ROH {AHL_FILE_REFERENCE}\n" +
					     "QOH {FROM_STATION}{FROM_AIRLINE} {TAG_NUMBER}\n" +
					   	 "FI {FI_FREE_FORM_TEXT}\n" +
					   	 "AG {AGENT_ID}\n" +
					   	 "SI {FI_FREE_FORM_TEXT}\n" +
					   	 "[TX_LINE]\n" +
					   	 "[NAME_LINE]";
		command = inputValuesIntoCommand(map, command);
		
		// Send Command
		String responseTxt = null;
		if (unitTest) {
			responseTxt = MockISharesResponse.mockRequestQohCommand(command);
		} else {
			responseTxt = sendCommand("Quick ROH", command);
		}
		
		// Process Response and insert any necessary data into the response
		// object & set success variable if successful
		// parse the result from iShare 


		// On success, set success to true (defaults to false)
		if (responseTxt.contains("WM SUS AHL")) {
			response.setSuccess(true);
		}
	}


	public void getActionFileDetails(WorldTracerActionDTO dto,
			ActionFileRequestData data, WorldTracerResponse response) throws CommandNotProperlyFormedException, WorldTracerException {
		throw new WorldTracerException("METHOD NOT IMPLEMENTED INTENTIONALLY");

	}



	public void getActionFileSummary(WorldTracerActionDTO dto,
			ActionFileRequestData data, WorldTracerResponse response) throws CommandNotProperlyFormedException, HttpException, IOException {
		//TODO here
		// Prepare variables
		
		// If applicable, perform any pre-processing required to obtain a
		// hashmap of field names
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("COMMAND_TYPE", connectionType);
		map.put("STATION", data.getStation());
		map.put("AIRLINE", data.getAirline());
		map.put("AREA", data.getType());
		map.put("DAY", data.getDay()+"");
//		map.put("NUMBER", data.getNumber()+"");
		
		// Generate Command from Hash (Example means provided)
		String command = "{COMMAND_TYPE} DXF {STATION}{AIRLINE} {AREA} D{DAY}";

		command = inputValuesIntoCommand(map, command);
		
		// Send Command
		String responseTxt = null;
		if (unitTest) {
			responseTxt = MockISharesResponse.mockActionFileSummaryNextCommand(command);
		} else {
			responseTxt = sendCommand("DXF", command);
		}
		
		List<ActionFile> myActionFileList = new ArrayList<ActionFile>();
		
		 while (responseTxt.contains("WMPN") || responseTxt.contains("ACTION FILE DISPLAY COMPLETE")){
			// multiple pages with next page flag or last page with last page flag
			// populate XF[]
			ActionFile myActionFile = ISharesResponseParser.processActionFileDetail(responseTxt);
			myActionFileList.add(myActionFile);
			
			if(responseTxt.contains("WMPN")){
				try{
					if (unitTest) {
						responseTxt = MockISharesResponse.mockActionFileSummaryNextCommand(command);
					} else {
						responseTxt = sendCommand("WMPN", "WMPN");
					}
					
					//get the result
				} catch(HttpException httpEx) {
					httpEx.printStackTrace();
				} catch(IOException ioEx) {
					ioEx.printStackTrace();
				}
			}
			
			// On success, set success to true (defaults to false)
			if (responseTxt.contains("ACTION FILE DISPLAY COMPLETE")) {  //only single page or on last page
				response.setSuccess(true);
				response.setActionFiles(myActionFileList.toArray(new ActionFile[myActionFileList.size()]));
				return;
			}
		}
	}

	public void placeActionFile(WorldTracerActionDTO dto, Pxf pxf,
			WorldTracerResponse response) throws CommandNotProperlyFormedException, HttpException, IOException {
		// TODO Auto-generated method stub
		boolean success = false;
		
		// If applicable, perform any pre-processing required to obtain a
		// hashmap of field names
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("COMMAND_TYPE", connectionType);
		
		// compose PXF targets here
		String myPXF_TARGET_LINE = "";
		PxfDetails[] myPxfDetails = pxf.getPxfDetails();
		if(myPxfDetails != null) {
			List<PxfDetails> targets = Arrays.asList(myPxfDetails);
			for(PxfDetails target: targets) {
				myPXF_TARGET_LINE += target.getStation() + target.getAirline() + target.getArea() + "/";
			}
		}
		// remove trailing /
		myPXF_TARGET_LINE = myPXF_TARGET_LINE.substring(0, myPXF_TARGET_LINE.length()-1);
		map.put("PXF_TARGET_LINE", myPXF_TARGET_LINE);
		map.put("SENDING_STATION", pxf.getSendingStation());
		map.put("MESSAGE_CONTENT", pxf.getContent());
		
		// Generate Command from Hash (Example means provided)
		String command = "{COMMAND_TYPE} PXF {PXF_TARGET_LINE}\n" +
						 ".{SENDING_STATION}\n" +
						 "{MESSAGE_CONTENT}";

		command = inputValuesIntoCommand(map, command);
		
		// Send Command
		String responseTxt = null;
		if (unitTest) {
			responseTxt = MockISharesResponse.mockPxfCommand(command);
		} else {
			int i = 1/0;
			responseTxt = sendCommand("PXF", command);
		}
		
		// Process Response and insert any necessary data into the response
		// object & set success variable if successful
		// update here for erasing Action File

		// On success, set success to true (defaults to false)
		if (responseTxt.contains("-OK-")) {
			response.setSuccess(true);
		}
	}
}



