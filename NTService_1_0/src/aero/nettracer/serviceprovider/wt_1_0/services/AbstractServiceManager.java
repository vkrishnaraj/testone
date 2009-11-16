package aero.nettracer.serviceprovider.wt_1_0.services;

import aero.nettracer.serviceprovider.common.ServiceConstants;
import aero.nettracer.serviceprovider.ws_1_0.common.WebServiceError;
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
import aero.nettracer.serviceprovider.wt_1_0.dto.WorldTracerActionType;
import aero.nettracer.serviceprovider.wt_1_0.services.ishares.service.CommandNotProperlyFormedException;
import aero.nettracer.serviceprovider.wt_1_0.services.wtrweb.connection.WorldTracerHttpClient;

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
						Ahl ahl5 = (Ahl) dto.getPayload();
						response = getAhl(dto, ahl5, response);
						break;
					case CREATE_AHL:
						Ahl ahl4 = (Ahl) dto.getPayload();
						response = createAhl(dto, ahl4, response);
						break;
					case AMEND_AHL:
						Ahl ahl3 = (Ahl) dto.getPayload();
						response = amendAhl(dto, ahl3, response);
						break;
					case CLOSE_AHL:
						Ahl ahl6 = (Ahl) dto.getPayload();
						response = closeAhl(dto, ahl6, response);
						break;
					case SUSPEND_AHL:
						Ahl ahl2 = (Ahl) dto.getPayload();
						response = suspendAhl(dto, ahl2, response);
						break;
					case REINSTATE_AHL:
						Ahl ahl = (Ahl) dto.getPayload();
						response = reinstateAhl(dto, ahl, response);
						break;
					case GET_OHD:
						//TODO: Complete
						Ohd ohd1 = (Ohd) dto.getPayload();
						response = getOhd(dto, ohd1, response);
						break;
					case CREATE_OHD:
						Ohd ohd2 = (Ohd) dto.getPayload();
						response = createOhd(dto, ohd2, response);
						break;
					case AMEND_OHD:
						Ohd ohd3 = (Ohd) dto.getPayload();
						response = amendOhd(dto, ohd3, response);
						break;
					case CLOSE_OHD:
						Ohd ohd4 = (Ohd) dto.getPayload();
						response = closeOhd(dto, ohd4, response);
						break;
					case FORWARD_OHD:
						ForwardOhd fw1 = (ForwardOhd) dto.getPayload();
						response = forwardOhd(dto, fw1, response);
						break;
					case REQUEST_OHD:
						RequestOhd rq2 = (RequestOhd) dto.getPayload();
						response = requestOhd(dto, rq2, response);
						break;
					case REQUEST_QOHD:
						RequestOhd rq1 = (RequestOhd) dto.getPayload();
						response = requestQuickOhd(dto, rq1, response);
						break;
					case CREATE_BDO:
						Bdo bdo = (Bdo) dto.getPayload();
						response = createBdo(dto, bdo, response);
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
						ForwardMessage msg = (ForwardMessage) dto.getPayload();
						response = sendFwdMessage(dto, msg, response);
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
				response.setSuccess(false);
			} catch (NotLoggedIntoWorldTracerException e) {
				e.printStackTrace();
				WorldTracerHttpClient client = (WorldTracerHttpClient) dto.getConnection();
				client.setValidConnection(false);
				retry = true;
				retryCount += 1;
				//TODO: IF BACKEND PROCESS NEED TO ONLY RETRY LIMIT # TIMES
			} catch (CommandNotProperlyFormedException e) {
				 response.setSuccess(false);
				 WebServiceError error = new WebServiceError(ServiceConstants.COMMAND_NOT_PROPERLY_FORMATTED);
			} catch (WorldTracerAlreadyClosedException e) {
	      response.setSuccess(false);
	      WebServiceError error = new WebServiceError(ServiceConstants.REFERENCED_OBJECT_CLOSED);
	      e.printStackTrace();
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
