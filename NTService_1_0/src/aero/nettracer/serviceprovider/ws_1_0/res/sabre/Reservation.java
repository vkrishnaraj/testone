package aero.nettracer.serviceprovider.ws_1_0.res.sabre;

import aero.nettracer.serviceprovider.ws_1_0.common.RequestHeader;
import aero.nettracer.serviceprovider.ws_1_0.res.ReservationInterface;
import aero.nettracer.serviceprovider.ws_1_0.response.EnplanementResponse;
import aero.nettracer.serviceprovider.ws_1_0.response.OsiResponse;
import aero.nettracer.serviceprovider.ws_1_0.response.RemarkResponse;
import aero.nettracer.serviceprovider.ws_1_0.response.ReservationResponse;

public class Reservation implements ReservationInterface {

	@Override
	public EnplanementResponse getEnplanements(RequestHeader header) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OsiResponse getOsiContents(RequestHeader header, String pnr, String bagTag) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReservationResponse getReservationData(RequestHeader header, String pnr, String bagTag) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RemarkResponse writeRemark(RequestHeader header, String pnr) {
		// TODO Auto-generated method stub
		return null;
	}

}
