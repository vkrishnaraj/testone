package aero.nettracer.serviceprovider.wt_1_0;

import aero.nettracer.serviceprovider.ws_1_0.common.RequestHeader;
import aero.nettracer.serviceprovider.wt_1_0.common.ActionFileRequestData;
import aero.nettracer.serviceprovider.wt_1_0.common.Ahl;
import aero.nettracer.serviceprovider.wt_1_0.common.Bdo;
import aero.nettracer.serviceprovider.wt_1_0.common.ForwardMessage;
import aero.nettracer.serviceprovider.wt_1_0.common.ForwardOhd;
import aero.nettracer.serviceprovider.wt_1_0.common.Ohd;
import aero.nettracer.serviceprovider.wt_1_0.common.Pxf;
import aero.nettracer.serviceprovider.wt_1_0.common.RequestOhd;
import aero.nettracer.serviceprovider.wt_1_0.common.WorldTracerResponse;


public class WorldTracerService {
	public WorldTracerResponse establishWtrConnection(RequestHeader header) { return null; }
	public WorldTracerResponse amendAhl(RequestHeader header, Ahl ahl) {return null;}
	public WorldTracerResponse amendOhd(RequestHeader header, Ohd ohd) {return null;}
	public WorldTracerResponse closeAhl(RequestHeader header, Ahl ahl) {return null;}
	public WorldTracerResponse closeOhd(RequestHeader header, Ohd ohd) {return null;}
	public WorldTracerResponse createAhl(RequestHeader header, Ahl ahl) {return null;}
	public WorldTracerResponse createBdo(RequestHeader header, Bdo bdo) {return null;}
	
	public WorldTracerResponse createOhd(RequestHeader header, Ohd ohd) {return null;}
	public WorldTracerResponse eraseActionFile(RequestHeader header, ActionFileRequestData data) {return null;}
	public WorldTracerResponse forwardOhd(RequestHeader header, ForwardOhd data) {return null;}
	public WorldTracerResponse getActionFileCounts(RequestHeader header, ActionFileRequestData data) {return null;}
	public WorldTracerResponse getActionFileDetails(RequestHeader header, ActionFileRequestData data) {return null;}
	public WorldTracerResponse getActionFileSummary(RequestHeader header, ActionFileRequestData data) {return null;}
	public WorldTracerResponse getAhl(RequestHeader header, Ahl ahl) {return null;}
	
	public WorldTracerResponse getOhd(RequestHeader header, Ohd ohd) {return null;}
	
	public WorldTracerResponse placeActionFile(RequestHeader header, Pxf pxf) {return null;}
	public WorldTracerResponse reinstanteAhl(RequestHeader header, Ahl ahl) {return null;}
	public WorldTracerResponse requestOhd(RequestHeader header, RequestOhd data) {return null;}
	public WorldTracerResponse requestQuickOhd(RequestHeader header, RequestOhd data) {return null;}
	
	public WorldTracerResponse sendForwardMessage(RequestHeader header, ForwardMessage forwardMessage) {return null;}
	public WorldTracerResponse suspendAhl(RequestHeader header, Ahl ahl) {return null;}
	
}
