package aero.nettracer.serviceprovider.wt_1_0.services;

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
import aero.nettracer.serviceprovider.wt_1_0.services.wtrweb.service.NotLoggedIntoWorldTracerException;
import aero.nettracer.serviceprovider.wt_1_0.services.wtrweb.service.WorldTracerException;

public interface ServiceManagerInterface {
	public abstract boolean preProcess(WorldTracerActionDTO dto, WorldTracerResponse response) throws WorldTracerException, NotLoggedIntoWorldTracerException;
	public abstract boolean postProcess(WorldTracerActionDTO dto, WorldTracerResponse response) throws WorldTracerException, NotLoggedIntoWorldTracerException;
	public abstract WorldTracerResponse getAhl(WorldTracerActionDTO dto, Ahl ahl, WorldTracerResponse response) throws WorldTracerException, NotLoggedIntoWorldTracerException;
	public abstract WorldTracerResponse createAhl(WorldTracerActionDTO dto, Ahl ahl, WorldTracerResponse response) throws WorldTracerException, NotLoggedIntoWorldTracerException;
	public abstract WorldTracerResponse amendAhl(WorldTracerActionDTO dto, Ahl ahl, WorldTracerResponse response) throws WorldTracerException, NotLoggedIntoWorldTracerException;
	public abstract WorldTracerResponse closeAhl(WorldTracerActionDTO dto, Ahl ahl3, WorldTracerResponse response) throws WorldTracerException, NotLoggedIntoWorldTracerException;
	public abstract WorldTracerResponse suspendAhl(WorldTracerActionDTO dto, Ahl ahl, WorldTracerResponse response) throws WorldTracerException, NotLoggedIntoWorldTracerException;
	public abstract WorldTracerResponse reinstateAhl(WorldTracerActionDTO dto, Ahl ahl, WorldTracerResponse response) throws WorldTracerException, NotLoggedIntoWorldTracerException;
	public abstract WorldTracerResponse getOhd(WorldTracerActionDTO dto, Ohd ohd, WorldTracerResponse response) throws WorldTracerException, NotLoggedIntoWorldTracerException;
	public abstract WorldTracerResponse createOhd(WorldTracerActionDTO dto, Ohd ohd, WorldTracerResponse response) throws WorldTracerException, NotLoggedIntoWorldTracerException;
	public abstract WorldTracerResponse amendOhd(WorldTracerActionDTO dto, Ohd ohd, WorldTracerResponse response) throws WorldTracerException, NotLoggedIntoWorldTracerException;
	public abstract WorldTracerResponse closeOhd(WorldTracerActionDTO dto, Ohd ohd, WorldTracerResponse response) throws WorldTracerException, NotLoggedIntoWorldTracerException;
	public abstract WorldTracerResponse forwardOhd(WorldTracerActionDTO dto, ForwardOhd data, WorldTracerResponse response) throws WorldTracerException, NotLoggedIntoWorldTracerException;
	public abstract WorldTracerResponse requestOhd(WorldTracerActionDTO dto, RequestOhd data, WorldTracerResponse response) throws WorldTracerException, NotLoggedIntoWorldTracerException;
	public abstract WorldTracerResponse requestQuickOhd(WorldTracerActionDTO dto, RequestOhd data, WorldTracerResponse response) throws WorldTracerException, NotLoggedIntoWorldTracerException;
	public abstract WorldTracerResponse createBdo(WorldTracerActionDTO dto, Bdo bdo, WorldTracerResponse response) throws WorldTracerException, NotLoggedIntoWorldTracerException;
	public abstract WorldTracerResponse eraseActionFile(WorldTracerActionDTO dto, ActionFileRequestData data, WorldTracerResponse response) throws WorldTracerException, NotLoggedIntoWorldTracerException;
	public abstract WorldTracerResponse getActionFileCounts(WorldTracerActionDTO dto, ActionFileRequestData data, WorldTracerResponse response) throws WorldTracerException, NotLoggedIntoWorldTracerException;
	public abstract WorldTracerResponse getActionFileSummary(WorldTracerActionDTO dto, ActionFileRequestData data, WorldTracerResponse response) throws WorldTracerException, NotLoggedIntoWorldTracerException;
	public abstract WorldTracerResponse getActionFileDetails(WorldTracerActionDTO dto, ActionFileRequestData data, WorldTracerResponse response) throws WorldTracerException, NotLoggedIntoWorldTracerException;
	public abstract WorldTracerResponse sendFwdMessage(WorldTracerActionDTO dto, ForwardMessage msg, WorldTracerResponse response) throws WorldTracerException, NotLoggedIntoWorldTracerException;
	public abstract WorldTracerResponse placeActionFile(WorldTracerActionDTO dto, Pxf data, WorldTracerResponse response) throws WorldTracerException, NotLoggedIntoWorldTracerException;

}


