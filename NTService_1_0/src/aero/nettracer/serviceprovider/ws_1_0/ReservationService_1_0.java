package aero.nettracer.serviceprovider.ws_1_0;

import aero.nettracer.serviceprovider.ws_1_0.common.RequestHeader;
import aero.nettracer.serviceprovider.ws_1_0.response.EnplanementResponse;
import aero.nettracer.serviceprovider.ws_1_0.response.OsiResponse;
import aero.nettracer.serviceprovider.ws_1_0.response.RemarkResponse;
import aero.nettracer.serviceprovider.ws_1_0.response.ReservationResponse;

public class ReservationService_1_0 {

	public ReservationResponse getReservationData(RequestHeader header, String pnr, String bagTag) {
		return null;
	}

	public OsiResponse getOsiContents(RequestHeader header, String pnr, String bagTag) {
		return null;
	}

	public EnplanementResponse getEnplanements(RequestHeader header) {
		return null;
	}

	public RemarkResponse writeRemark(RequestHeader header, String pnr) {
		return null;
	}

}
