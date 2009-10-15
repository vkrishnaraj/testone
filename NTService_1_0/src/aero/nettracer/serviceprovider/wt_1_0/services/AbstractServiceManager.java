package aero.nettracer.serviceprovider.wt_1_0.services;

import aero.nettracer.serviceprovider.wt_1_0.common.WorldTracerResponse;
import aero.nettracer.serviceprovider.wt_1_0.dto.WorldTracerActionDTO;
import aero.nettracer.serviceprovider.wt_1_0.dto.WorldTracerActionType;


public abstract class AbstractServiceManager implements ServiceManagerInterface{

	public WorldTracerResponse process(WorldTracerActionDTO dto) {
		WorldTracerResponse response = new WorldTracerResponse();
		WorldTracerActionType type = dto.getType();
		
		switch (type) {
		case GET_AHL:
			response = getAhl(dto, response);
			break;
		case CREATE_AHL:
			response = createAhl(dto, response);
			break;
		case AMEND_AHL:
			response = amendAhl(dto, response);
			break;
		case CLOSE_AHL:
			response = closeAhl(dto, response);
			break;
		case SUSPEND_AHL:
			response = suspendAhl(dto, response);
			break;
		case REINSTATE_AHL:
			response = reinstateAhl(dto, response);
			break;
		case GET_OHD:
			response = getOhd(dto, response);
			break;
		case CREATE_OHD:
			response = createOhd(dto, response);
			break;
		case AMEND_OHD:
			response = amendOhd(dto, response);
			break;
		case CLOSE_OHD:
			response = closeOhd(dto, response);
			break;
		case FORWARD_OHD:
			response = forwardOhd(dto, response);
			break;
		case REQUEST_OHD:
			response = requestOhd(dto, response);
			break;
		case REQUEST_QOHD:
			response = requestQuickOhd(dto, response);
			break;
		case CREATE_BDO:
			response = createBdo(dto, response);
			break;
		case ERASE_ACTION_FILE:
			response = eraseActionFile(dto, response);
			break;
		case ACTION_FILE_COUNTS:
			response = getActionFileCounts(dto, response);
			break;
		case ACTION_FILE_SUMMARY:
			response = getActionFileSummary(dto, response);
			break;
		case ACTION_FILE_DETAILS:
			response = getActionFileDetails(dto, response);
			break;
		case SEND_FORWARD_MESSAGE:
			response = sendFwdMessage(dto, response);
			break;
		case PLACE_ACTION_FILE:
			response = pleaseActionFile(dto, response);
			break;
		}
		return response;
		
	}
	
}

