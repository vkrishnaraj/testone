package aero.nettracer.serviceprovider.wt_1_0.services;

import aero.nettracer.serviceprovider.wt_1_0.common.WorldTracerResponse;
import aero.nettracer.serviceprovider.wt_1_0.dto.WorldTracerActionDTO;

public interface ServiceManagerInterface {
	public abstract WorldTracerResponse getAhl(WorldTracerActionDTO dto, WorldTracerResponse response);
	public abstract WorldTracerResponse createAhl(WorldTracerActionDTO dto, WorldTracerResponse response);
	public abstract WorldTracerResponse amendAhl(WorldTracerActionDTO dto, WorldTracerResponse response);
	public abstract WorldTracerResponse closeAhl(WorldTracerActionDTO dto, WorldTracerResponse response);
	public abstract WorldTracerResponse suspendAhl(WorldTracerActionDTO dto, WorldTracerResponse response);
	public abstract WorldTracerResponse reinstateAhl(WorldTracerActionDTO dto, WorldTracerResponse response);
	public abstract WorldTracerResponse getOhd(WorldTracerActionDTO dto, WorldTracerResponse response);
	public abstract WorldTracerResponse createOhd(WorldTracerActionDTO dto, WorldTracerResponse response);
	public abstract WorldTracerResponse amendOhd(WorldTracerActionDTO dto, WorldTracerResponse response);
	public abstract WorldTracerResponse closeOhd(WorldTracerActionDTO dto, WorldTracerResponse response);
	public abstract WorldTracerResponse forwardOhd(WorldTracerActionDTO dto, WorldTracerResponse response);
	public abstract WorldTracerResponse requestOhd(WorldTracerActionDTO dto, WorldTracerResponse response);
	public abstract WorldTracerResponse requestQuickOhd(WorldTracerActionDTO dto, WorldTracerResponse response);
	public abstract WorldTracerResponse createBdo(WorldTracerActionDTO dto, WorldTracerResponse response);
	public abstract WorldTracerResponse eraseActionFile(WorldTracerActionDTO dto, WorldTracerResponse response);
	public abstract WorldTracerResponse getActionFileCounts(WorldTracerActionDTO dto, WorldTracerResponse response);
	public abstract WorldTracerResponse getActionFileSummary(WorldTracerActionDTO dto, WorldTracerResponse response);
	public abstract WorldTracerResponse getActionFileDetails(WorldTracerActionDTO dto, WorldTracerResponse response);
	public abstract WorldTracerResponse sendFwdMessage(WorldTracerActionDTO dto, WorldTracerResponse response);
	public abstract WorldTracerResponse pleaseActionFile(WorldTracerActionDTO dto, WorldTracerResponse response);

}

