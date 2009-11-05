package aero.nettracer.serviceprovider.wt_1_0.services;

import aero.nettracer.serviceprovider.common.ServiceConstants;
import aero.nettracer.serviceprovider.ws_1_0.common.WebServiceError;
import aero.nettracer.serviceprovider.wt_1_0.common.ActionFileRequestData;
import aero.nettracer.serviceprovider.wt_1_0.common.Pxf;
import aero.nettracer.serviceprovider.wt_1_0.common.WorldTracerResponse;
import aero.nettracer.serviceprovider.wt_1_0.dto.WorldTracerActionDTO;
import aero.nettracer.serviceprovider.wt_1_0.dto.WorldTracerActionType;
import aero.nettracer.serviceprovider.wt_1_0.services.wtrweb.connection.WorldTracerHttpClient;
import aero.nettracer.serviceprovider.wt_1_0.services.wtrweb.service.NotLoggedIntoWorldTracerException;
import aero.nettracer.serviceprovider.wt_1_0.services.wtrweb.service.WorldTracerException;

public abstract class AbstractServiceManager implements ServiceManagerInterface {

	public WorldTracerResponse process(WorldTracerActionDTO dto) {
		WorldTracerResponse response = new WorldTracerResponse();
		WorldTracerActionType type = dto.getType();
		boolean retry = true;
		int retryCount = 0;
		while (retry && retryCount < 2) {
			retry = false;
			try {
				boolean process = preProcess(dto, response);
				if (process) {
					switch (type) {
					case GET_AHL:
						//TODO: Complete
						response = getAhl(dto, response);
						break;
					case CREATE_AHL:
						//TODO: Complete
						response = createAhl(dto, response);
						break;
					case AMEND_AHL:
						//TODO: Complete
						response = amendAhl(dto, response);
						break;
					case CLOSE_AHL:
						//TODO: Complete
						response = closeAhl(dto, response);
						break;
					case SUSPEND_AHL:
						//TODO: Complete
						response = suspendAhl(dto, response);
						break;
					case REINSTATE_AHL:
						//TODO: Complete
						response = reinstateAhl(dto, response);
						break;
					case GET_OHD:
						//TODO: Complete
						response = getOhd(dto, response);
						break;
					case CREATE_OHD:
						//TODO: Complete
						response = createOhd(dto, response);
						break;
					case AMEND_OHD:
						//TODO: Complete
						response = amendOhd(dto, response);
						break;
					case CLOSE_OHD:
						//TODO: Complete
						response = closeOhd(dto, response);
						break;
					case FORWARD_OHD:
						//TODO: Complete
						response = forwardOhd(dto, response);
						break;
					case REQUEST_OHD:
						//TODO: Complete
						response = requestOhd(dto, response);
						break;
					case REQUEST_QOHD:
						//TODO: Complete
						response = requestQuickOhd(dto, response);
						break;
					case CREATE_BDO:
						//TODO: Complete
						response = createBdo(dto, response);
						break;
					case ERASE_ACTION_FILE:
						ActionFileRequestData data3 = (ActionFileRequestData) dto
						.getPayload();
						response = eraseActionFile(dto, data3, response);
						break;
					case ACTION_FILE_COUNTS:
						ActionFileRequestData data = (ActionFileRequestData) dto
								.getPayload();
						response = getActionFileCounts(dto, data, response);
						break;
					case ACTION_FILE_SUMMARY:
						ActionFileRequestData data1 = (ActionFileRequestData) dto
						.getPayload();
						response = getActionFileSummary(dto, data1, response);
						break;
					case ACTION_FILE_DETAILS:
						ActionFileRequestData data2 = (ActionFileRequestData) dto
						.getPayload();
						response = getActionFileDetails(dto, data2, response);
						break;
					case SEND_FORWARD_MESSAGE:
						//TODO: Complete
						response = sendFwdMessage(dto, response);
						break;
					case PLACE_ACTION_FILE:
						Pxf pxf = (Pxf) dto
						.getPayload();
						response = placeActionFile(dto, pxf, response);
						break;
					}
				}
			} catch (WorldTracerException e) {
				e.printStackTrace();
				response.setError(new WebServiceError(
						ServiceConstants.UNEXPECTED_EXCEPTION));
			} catch (NotLoggedIntoWorldTracerException e) {
				e.printStackTrace();
				WorldTracerHttpClient client = (WorldTracerHttpClient) dto.getConnection();
				client.setValidConnection(false);
				retry = true;
				retryCount += 1;
				//TODO: IF BACKEND PROCESS NEED TO ONLY RETRY LIMIT # TIMES
			}
		}
		postProcess(dto, response);

		return response;

	}

	@Override
	public boolean preProcess(WorldTracerActionDTO dto,
			WorldTracerResponse response) {
		return true;
	}

	@Override
	public boolean postProcess(WorldTracerActionDTO dto,
			WorldTracerResponse response) {
		return true;
	}
}
