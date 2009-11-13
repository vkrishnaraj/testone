package aero.nettracer.serviceprovider.wt_1_0.services.ishares.service;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import aero.nettracer.serviceprovider.wt_1_0.common.ActionFileCount;
import aero.nettracer.serviceprovider.wt_1_0.common.ActionFileRequestData;
import aero.nettracer.serviceprovider.wt_1_0.common.Ahl;
import aero.nettracer.serviceprovider.wt_1_0.common.Bdo;
import aero.nettracer.serviceprovider.wt_1_0.common.ForwardMessage;
import aero.nettracer.serviceprovider.wt_1_0.common.ForwardOhd;
import aero.nettracer.serviceprovider.wt_1_0.common.Ohd;
import aero.nettracer.serviceprovider.wt_1_0.common.Pxf;
import aero.nettracer.serviceprovider.wt_1_0.common.RequestOhd;
import aero.nettracer.serviceprovider.wt_1_0.common.WorldTracerResponse;
import aero.nettracer.serviceprovider.wt_1_0.dto.WorldTracerActionDTO;
import aero.nettracer.serviceprovider.wt_1_0.services.wtrweb.service.WorldTracerService;


public class WorldTracerServiceImpl implements WorldTracerService {

	private WorldTracerActionDTO dto = null;
	private ISharesConnection connection = null;
	private String connectionType = "WM";
	private boolean unitTest = false;
	static Pattern commandResponsePattern = Pattern.compile("<PRE>((.*\\n)*.*)</PRE>", Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);

	public WorldTracerServiceImpl(WorldTracerActionDTO dto) {
		// TODO: Byron to define connection type
		this.dto = dto;
	}
	
	public WorldTracerServiceImpl(WorldTracerActionDTO dto, boolean unitTest) {
		// TODO: Byron to define connection type
		this.dto = dto;
		this.unitTest = unitTest;
	}
	
	
	
	static String sendCommand(String command) throws CommandNotProperlyFormedException {
		
		
		// TODO Byron to fill in with communication means
		// Test for presence of any remaining {} characters and throw exception
		testCommand(command);
		// TODO: SEND
		String response = "";
		
		
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
			if (replaceValue != null) output = output.replace("{" + key + "}", replaceValue);
		}
		return output;
	}
	

	private static void testCommand(String command) throws CommandNotProperlyFormedException {
		if (command.contains("{") || command.contains("}")) throw new CommandNotProperlyFormedException();
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
	 */
	public void getActionFileCounts(WorldTracerActionDTO dto,
			ActionFileRequestData data, WorldTracerResponse response) throws CommandNotProperlyFormedException {
		
		// Prepare variables
		boolean success = false;
		
		// If applicable, perform any pre-processing required to obtain a
		// hashmap of field names
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("COMMAND_TYPE", connectionType);
		map.put("STATION_CODE", data.getStation());
		map.put("AIRLINE_CODE", data.getAirline());
		
		// Generate Command from Hash (Example means provided)
		String command = "{COMMAND_TYPE} CXF {STATION_CODE}{AIRLINE_CODE}";
		command = inputValuesIntoCommand(map, command);
		
		// Send Command
		String responseTxt = null;
		if (unitTest) {
			responseTxt = MockISharesResponse.mockGetActionFileCountsCommand(command);
		} else {
			responseTxt = sendCommand(command);
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
			WorldTracerResponse response) throws CommandNotProperlyFormedException {
		// TODO Auto-generated method stub
		
	}


	public void eraseActionFile(WorldTracerActionDTO dto,
			ActionFileRequestData data, WorldTracerResponse response) throws CommandNotProperlyFormedException {
		// TODO Auto-generated method stub
		
	}



	public void requestOhd(WorldTracerActionDTO dto, RequestOhd data,
			WorldTracerResponse response) throws CommandNotProperlyFormedException {
		// TODO Auto-generated method stub
		
	}



	public void requestQuickOhd(WorldTracerActionDTO dto, RequestOhd data,
			WorldTracerResponse response) throws CommandNotProperlyFormedException {
		// TODO Auto-generated method stub
		
	}


	public void getActionFileDetails(WorldTracerActionDTO dto,
			ActionFileRequestData data, WorldTracerResponse response) throws CommandNotProperlyFormedException {
		// TODO SPECIAL CASE: MAY NOT NEED TO BE IMPLEMENTED
		
		
	}



	public void getActionFileSummary(WorldTracerActionDTO dto,
			ActionFileRequestData data, WorldTracerResponse response) throws CommandNotProperlyFormedException {
		// TODO Auto-generated method stub
		
	}



	public void placeActionFile(WorldTracerActionDTO dto, Pxf pxf,
			WorldTracerResponse response) throws CommandNotProperlyFormedException {
		// TODO Auto-generated method stub
		
	}


}



