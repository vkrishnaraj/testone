package aero.nettracer.serviceprovider.wt_1_0.services.wtrweb;

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
import aero.nettracer.serviceprovider.wt_1_0.services.AbstractServiceManager;
import aero.nettracer.serviceprovider.wt_1_0.services.NotLoggedIntoWorldTracerException;
import aero.nettracer.serviceprovider.wt_1_0.services.ServiceManagerInterface;
import aero.nettracer.serviceprovider.wt_1_0.services.WorldTracerException;
import aero.nettracer.serviceprovider.wt_1_0.services.wtrweb.connection.ConnectionPoolManager;
import aero.nettracer.serviceprovider.wt_1_0.services.wtrweb.connection.WorldTracerClientPool;
import aero.nettracer.serviceprovider.wt_1_0.services.wtrweb.connection.WorldTracerHttpClient;
import aero.nettracer.serviceprovider.wt_1_0.services.wtrweb.service.WorldTracerServiceImpl;

public class WtrWebServiceManager extends AbstractServiceManager implements
		ServiceManagerInterface {
	private static WtrWebServiceManager instance = null;

	public static AbstractServiceManager getInstance() {
		if (instance == null) {
			instance = new WtrWebServiceManager();
		}
		return instance;
	}

	@Override
	public boolean preProcess(WorldTracerActionDTO dto,
			WorldTracerResponse response) {
		WorldTracerClientPool pool = ConnectionPoolManager.getInstance().getPool(dto.getUser());
		WorldTracerHttpClient conn = null;
		
		if (dto.getConnection() == null) {
			Integer key = null;
			
			// Provide a Round-Robin approach to obtaining cron connections.
			String strLoggedInConnection = dto.getParameterValue(ServiceConstants.USE_AVAILABLE_CONNECTIONS_IF_POSSIBLE);
			if (strLoggedInConnection != null && strLoggedInConnection.length() > 0) {
				Integer tmpKey = pool.getNextLoggedInKeyForCron();
				if (tmpKey != null) {
					key = tmpKey;
				}
			}
			
			String tmp = dto.getParameterValue(ServiceConstants.WT_WTR_CONNECTION_KEY);
			if (key == null && tmp != null && tmp.length() > 0) {
				key = Integer.parseInt(tmp);
			} 

			try {
				conn = (WorldTracerHttpClient) pool.borrowObject(key);
				response.setConnectionRef(conn.getKey().toString());
				dto.setConnection(conn);
			} catch (Exception e) {
				e.printStackTrace();

			}
		}

		if (conn == null) {
			response.setError(new WebServiceError(
					ServiceConstants.UNEXPECTED_EXCEPTION));
			response.setSuccess(false);
			return false;
		} else {
			if (conn.isWaitingOnCaptcha() || !conn.isValidConnection()) {
				if (!login(dto)) {
					response.setCaptcha(conn.getByteCaptcha());
					response.setCaptchaTimestamp(Long.toString(conn
							.getCaptchaTimestamp().getTime().getTime()));
					response.setError(new WebServiceError(
							ServiceConstants.CAPTCHA_EXCEPTION));
					response.setSuccess(false);
					return false;
				}
			}
		}
		return true;
	}

	private synchronized boolean login(WorldTracerActionDTO dto) {
		WorldTracerHttpClient client = (WorldTracerHttpClient) dto
				.getConnection();
		boolean loginWithCaptcha = false;

		String captchaText = dto
				.getParameterValue(ServiceConstants.CAPTCHA_TEXT);
		String captchaTimestamp = dto
				.getParameterValue(ServiceConstants.CAPTCHA_TIMESTAMP);
		long time = 0;
		if (captchaTimestamp != null) {
			time = Long.parseLong(captchaTimestamp);
		}

		boolean cronUser = false;
		String cronUserStr = dto.getParameterValue(ServiceConstants.CRON_USER);
		if (cronUserStr != null && cronUserStr.length() > 0) {
			cronUser = true;
		}
		
		
		if (client.getAccount().isCaptchaRequired()
				&& cronUser) {
			// Automatically determine the captcha value
			loginWithCaptcha = true;
			try {
				client.getLogonPageAndCaptcha();
			} catch (WorldTracerException e) {
				e.printStackTrace();
			}

			captchaText = client.getAutoProcessCaptcha(client.getByteCaptcha());
			if (captchaText == null) {
				return false;
			}

		} else if (client.getAccount().isCaptchaRequired()) {
			if (client.isWaitingOnCaptcha()) {

				Long cts = client.getCaptchaTimestamp().getTime().getTime();

				if (cts - time != 0 || captchaText == null
						|| captchaText.length() == 0) {
					// Timestamps differ - there is a newer captcha available
					return false;
				}

				// Timestamps are the same, so attempt to log in using captcha
				// from user
				loginWithCaptcha = true;

			} else {
				// login page and load the captcha
				try {
					client.getLogonPageAndCaptcha();
				} catch (WorldTracerException e) {
					e.printStackTrace();
				}
				return false;
			}
		}

		return client.performLogon(loginWithCaptcha, captchaText);
	}
//
//	private synchronized void logout(WorldTracerHttpClient conn) {
//		// TODO Auto-generated method stub
//
//	}

	@Override
	public boolean postProcess(WorldTracerActionDTO dto,
			WorldTracerResponse response) {
		WorldTracerClientPool pool = ConnectionPoolManager.getInstance()
				.getPool(dto.getUser());
		WorldTracerHttpClient client = (WorldTracerHttpClient) dto
				.getConnection();
		Integer key = client.getKey();
		try {
			pool.returnObject(key, client);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	@Override
	public WorldTracerResponse amendAhl(WorldTracerActionDTO dto, Ahl data,
			WorldTracerResponse response) throws WorldTracerException, NotLoggedIntoWorldTracerException {
		WorldTracerServiceImpl impl = new WorldTracerServiceImpl(dto);
		impl.amendAhl(dto, data, response);
		return response;
	}

	@Override
	public WorldTracerResponse amendOhd(WorldTracerActionDTO dto, Ohd data,
			WorldTracerResponse response)  throws WorldTracerException, NotLoggedIntoWorldTracerException {
		WorldTracerServiceImpl impl = new WorldTracerServiceImpl(dto);
		impl.amendOhd(dto, data, response);
		return response;
	}

	@Override
	public WorldTracerResponse closeAhl(WorldTracerActionDTO dto, Ahl ahl,
			WorldTracerResponse response) {
		WorldTracerServiceImpl impl = new WorldTracerServiceImpl(dto);
		impl.closeAhl(dto, ahl, response);
		return response;
	}

	@Override
	public WorldTracerResponse closeOhd(WorldTracerActionDTO dto,  Ohd data,
			WorldTracerResponse response)  throws WorldTracerException, NotLoggedIntoWorldTracerException {
		WorldTracerServiceImpl impl = new WorldTracerServiceImpl(dto);
		impl.closeOhd(dto, data, response);
		return response;
	}

	@Override
	public WorldTracerResponse createAhl(WorldTracerActionDTO dto, Ahl data,
			WorldTracerResponse response)  throws WorldTracerException, NotLoggedIntoWorldTracerException {
		WorldTracerServiceImpl impl = new WorldTracerServiceImpl(dto);
		impl.createAhl(dto, data, response);
		return response;
	}

	@Override
	public WorldTracerResponse createBdo(WorldTracerActionDTO dto, Bdo bdo, 
			WorldTracerResponse response) throws WorldTracerException, NotLoggedIntoWorldTracerException {
		WorldTracerServiceImpl impl = new WorldTracerServiceImpl(dto);
		impl.insertBdo(dto, bdo, response);
		return response;
	}

	@Override
	public WorldTracerResponse createOhd(WorldTracerActionDTO dto,  Ohd data,
			WorldTracerResponse response)  throws WorldTracerException, NotLoggedIntoWorldTracerException {
		WorldTracerServiceImpl impl = new WorldTracerServiceImpl(dto);
		impl.createOhd(dto, data, response);
		return response;
	}

	@Override
	public WorldTracerResponse eraseActionFile(WorldTracerActionDTO dto,
			ActionFileRequestData data, WorldTracerResponse response) throws WorldTracerException, NotLoggedIntoWorldTracerException {
		WorldTracerServiceImpl impl = new WorldTracerServiceImpl(dto);
		impl.eraseActionFile(dto, data, response);
		return response;
	}

	@Override
	public WorldTracerResponse forwardOhd(WorldTracerActionDTO dto, ForwardOhd data,
			WorldTracerResponse response) throws WorldTracerException, NotLoggedIntoWorldTracerException {
		WorldTracerServiceImpl impl = new WorldTracerServiceImpl(dto);
		impl.forwardOhd(dto, data, response);
		return response;
	}

	@Override
	public WorldTracerResponse requestOhd(WorldTracerActionDTO dto, RequestOhd data,
			WorldTracerResponse response) throws WorldTracerException, NotLoggedIntoWorldTracerException {
		WorldTracerServiceImpl impl = new WorldTracerServiceImpl(dto);
		impl.requestOhd(dto, data, response);
		return response;
	}

	@Override
	public WorldTracerResponse requestQuickOhd(WorldTracerActionDTO dto, RequestOhd data,
			WorldTracerResponse response) throws WorldTracerException, NotLoggedIntoWorldTracerException {
		WorldTracerServiceImpl impl = new WorldTracerServiceImpl(dto);
		impl.requestQuickOhd(dto, data, response);
		return response;
	}

	@Override
	public WorldTracerResponse getActionFileCounts(WorldTracerActionDTO dto,
			ActionFileRequestData data, WorldTracerResponse response)
			throws WorldTracerException, NotLoggedIntoWorldTracerException {

		WorldTracerServiceImpl impl = new WorldTracerServiceImpl(dto);
		impl.getActionFileCounts(dto, data, response);
		return response;
	}

	@Override
	public WorldTracerResponse getActionFileDetails(WorldTracerActionDTO dto, ActionFileRequestData data, 
			WorldTracerResponse response) throws WorldTracerException, NotLoggedIntoWorldTracerException {
		WorldTracerServiceImpl impl = new WorldTracerServiceImpl(dto);
		impl.getActionFileDetails(dto, data, response);
		return response;
	}

	@Override
	public WorldTracerResponse getActionFileSummary(WorldTracerActionDTO dto,
			ActionFileRequestData data, 
			WorldTracerResponse response) throws WorldTracerException, NotLoggedIntoWorldTracerException {
		WorldTracerServiceImpl impl = new WorldTracerServiceImpl(dto);
		impl.getActionFileSummary(dto, data, response);
		return response;
	}

	@Override
	public WorldTracerResponse getAhl(WorldTracerActionDTO dto, Ahl data,
			WorldTracerResponse response)  throws WorldTracerException, NotLoggedIntoWorldTracerException {
		WorldTracerServiceImpl impl = new WorldTracerServiceImpl(dto);
		impl.getAhl(dto, data, response);
		return response;
	}

	@Override
	public WorldTracerResponse getOhd(WorldTracerActionDTO dto, Ohd data,
			WorldTracerResponse response)  throws WorldTracerException, NotLoggedIntoWorldTracerException {
		WorldTracerServiceImpl impl = new WorldTracerServiceImpl(dto);
		impl.getOhd(dto, data, response);
		return response;
	}

	@Override
	public WorldTracerResponse placeActionFile(WorldTracerActionDTO dto, Pxf pxf,
			WorldTracerResponse response) throws WorldTracerException, NotLoggedIntoWorldTracerException {
		WorldTracerServiceImpl impl = new WorldTracerServiceImpl(dto);
		impl.placeActionFile(dto, pxf, response);
		return response;
	}

	@Override
	public WorldTracerResponse reinstateAhl(WorldTracerActionDTO dto, Ahl ahl,
			WorldTracerResponse response) throws WorldTracerException, NotLoggedIntoWorldTracerException {
		WorldTracerServiceImpl impl = new WorldTracerServiceImpl(dto);
		impl.reinstateAhl(dto, ahl, response);
		return response;
	}

	@Override
	public WorldTracerResponse sendFwdMessage(WorldTracerActionDTO dto, ForwardMessage msg,
			WorldTracerResponse response) throws WorldTracerException, NotLoggedIntoWorldTracerException {
		WorldTracerServiceImpl impl = new WorldTracerServiceImpl(dto);
		impl.sendForwardMessage(dto, msg, response);
		return response;
	}

	@Override
	public WorldTracerResponse suspendAhl(WorldTracerActionDTO dto, Ahl ahl,
	    WorldTracerResponse response) throws WorldTracerException, NotLoggedIntoWorldTracerException {
		WorldTracerServiceImpl impl = new WorldTracerServiceImpl(dto);
		impl.suspendAhl(dto, ahl, response);
		return response;
	}

}
