package aero.nettracer.serviceprovider.wt_1_0.services.webservices;

import aero.nettracer.serviceprovider.wt_1_0.common.WorldTracerResponse;
import aero.nettracer.serviceprovider.wt_1_0.dto.WorldTracerActionDTO;
import aero.nettracer.serviceprovider.wt_1_0.services.AbstractServiceManager;
import aero.nettracer.serviceprovider.wt_1_0.services.ServiceManagerInterface;

public class WTWebServiceManager extends AbstractServiceManager implements
		ServiceManagerInterface {
	private static WTWebServiceManager instance = null;

	public static AbstractServiceManager getInstance() {
		synchronized (instance) {
			if (instance == null) {
				instance = new WTWebServiceManager();
			}
			return instance;
		}
	}

	@Override
	public WorldTracerResponse amendAhl(WorldTracerActionDTO dto,
			WorldTracerResponse response) {

		return response;
	}

	@Override
	public WorldTracerResponse amendOhd(WorldTracerActionDTO dto,
			WorldTracerResponse response) {

		return response;
	}

	@Override
	public WorldTracerResponse closeAhl(WorldTracerActionDTO dto,
			WorldTracerResponse response) {

		return response;
	}

	@Override
	public WorldTracerResponse closeOhd(WorldTracerActionDTO dto,
			WorldTracerResponse response) {

		return response;
	}

	@Override
	public WorldTracerResponse createAhl(WorldTracerActionDTO dto,
			WorldTracerResponse response) {

		return response;
	}

	@Override
	public WorldTracerResponse createBdo(WorldTracerActionDTO dto,
			WorldTracerResponse response) {

		return response;
	}

	@Override
	public WorldTracerResponse createOhd(WorldTracerActionDTO dto,
			WorldTracerResponse response) {

		return response;
	}

	@Override
	public WorldTracerResponse eraseActionFile(WorldTracerActionDTO dto,
			WorldTracerResponse response) {

		return response;
	}

	@Override
	public WorldTracerResponse forwardOhd(WorldTracerActionDTO dto,
			WorldTracerResponse response) {

		return response;
	}

	@Override
	public WorldTracerResponse requestOhd(WorldTracerActionDTO dto,
			WorldTracerResponse response) {

		return response;
	}

	@Override
	public WorldTracerResponse requestQuickOhd(WorldTracerActionDTO dto,
			WorldTracerResponse response) {

		return response;
	}

	@Override
	public WorldTracerResponse getActionFileCounts(WorldTracerActionDTO dto,
			WorldTracerResponse response) {

		return response;
	}

	@Override
	public WorldTracerResponse getActionFileDetails(WorldTracerActionDTO dto,
			WorldTracerResponse response) {

		return response;
	}

	@Override
	public WorldTracerResponse getActionFileSummary(WorldTracerActionDTO dto,
			WorldTracerResponse response) {

		return response;
	}

	@Override
	public WorldTracerResponse getAhl(WorldTracerActionDTO dto,
			WorldTracerResponse response) {

		return response;
	}

	@Override
	public WorldTracerResponse getOhd(WorldTracerActionDTO dto,
			WorldTracerResponse response) {

		return response;
	}

	@Override
	public WorldTracerResponse pleaseActionFile(WorldTracerActionDTO dto,
			WorldTracerResponse response) {

		return response;
	}

	@Override
	public WorldTracerResponse reinstateAhl(WorldTracerActionDTO dto,
			WorldTracerResponse response) {

		return response;
	}

	@Override
	public WorldTracerResponse sendFwdMessage(WorldTracerActionDTO dto,
			WorldTracerResponse response) {

		return response;
	}

	@Override
	public WorldTracerResponse suspendAhl(WorldTracerActionDTO dto,
			WorldTracerResponse response) {

		return response;
	}
}
