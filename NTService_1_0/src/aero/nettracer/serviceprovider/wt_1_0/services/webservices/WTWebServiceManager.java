package aero.nettracer.serviceprovider.wt_1_0.services.webservices;

import aero.nettracer.serviceprovider.wt_1_0.common.ActionFileRequestData;
import aero.nettracer.serviceprovider.wt_1_0.common.Ahl;
import aero.nettracer.serviceprovider.wt_1_0.common.Bdo;
import aero.nettracer.serviceprovider.wt_1_0.common.ForwardMessage;
import aero.nettracer.serviceprovider.wt_1_0.common.ForwardOhd;
import aero.nettracer.serviceprovider.wt_1_0.common.Ohd;
import aero.nettracer.serviceprovider.wt_1_0.common.Pxf;
import aero.nettracer.serviceprovider.wt_1_0.common.Qoh;
import aero.nettracer.serviceprovider.wt_1_0.common.RequestOhd;
import aero.nettracer.serviceprovider.wt_1_0.common.WorldTracerResponse;
import aero.nettracer.serviceprovider.wt_1_0.dto.WorldTracerActionDTO;
import aero.nettracer.serviceprovider.wt_1_0.services.AbstractServiceManager;
import aero.nettracer.serviceprovider.wt_1_0.services.NotLoggedIntoWorldTracerException;
import aero.nettracer.serviceprovider.wt_1_0.services.ServiceManagerInterface;
import aero.nettracer.serviceprovider.wt_1_0.services.WorldTracerAlreadyClosedException;
import aero.nettracer.serviceprovider.wt_1_0.services.WorldTracerException;
import aero.nettracer.serviceprovider.wt_1_0.services.WorldTracerTimeoutException;
import aero.nettracer.serviceprovider.wt_1_0.services.ishares.service.CommandNotProperlyFormedException;


public class WTWebServiceManager extends AbstractServiceManager implements
		ServiceManagerInterface {

	
	private static WTWebServiceManager instance = null;
	

	public static AbstractServiceManager getInstance() {
		if (instance == null) {
			instance = new WTWebServiceManager();
		}
		return instance;
	}


	
	@Override
	public WorldTracerResponse amendAhl(WorldTracerActionDTO dto, Ahl data,
			WorldTracerResponse response) throws WorldTracerException, NotLoggedIntoWorldTracerException, WorldTracerAlreadyClosedException {
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
			WorldTracerResponse response) throws WorldTracerException {
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

	@Override
	public WorldTracerResponse reinstateOhd(WorldTracerActionDTO dto, Ohd ohd, WorldTracerResponse response) throws WorldTracerException, NotLoggedIntoWorldTracerException,
			CommandNotProperlyFormedException {
		// TODO Auto-generated method stub
		WorldTracerServiceImpl impl = new WorldTracerServiceImpl(dto);
		impl.reinstateOhd(dto, ohd, response);
		return response;
	}

	@Override
	public WorldTracerResponse suspendOhd(WorldTracerActionDTO dto, Ohd ohd, WorldTracerResponse response) throws WorldTracerException, NotLoggedIntoWorldTracerException,
			CommandNotProperlyFormedException {
		// TODO Auto-generated method stub
		WorldTracerServiceImpl impl = new WorldTracerServiceImpl(dto);
		impl.suspendOhd(dto, ohd, response);
		return response;
	}



	@Override
	public WorldTracerResponse sendQoh(WorldTracerActionDTO dto, Qoh qoh, WorldTracerResponse response) throws WorldTracerException, NotLoggedIntoWorldTracerException,
			WorldTracerTimeoutException, CommandNotProperlyFormedException {
		WorldTracerServiceImpl impl = new WorldTracerServiceImpl(dto);
		impl.sendQoh(dto, qoh, response);
		return response;
	}

}
