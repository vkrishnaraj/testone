package aero.nettracer.serviceprovider.ws_1_0.res;

import aero.nettracer.serviceprovider.ws_1_0.common.RequestHeader;
import aero.nettracer.serviceprovider.ws_1_0.response.EnplanementResponse;
import aero.nettracer.serviceprovider.ws_1_0.response.OsiResponse;
import aero.nettracer.serviceprovider.ws_1_0.response.RemarkResponse;
import aero.nettracer.serviceprovider.ws_1_0.response.ReservationResponse;

public interface ReservationInterface {

	public ReservationResponse getReservationData(RequestHeader header, String pnr, String bagTag);

	public OsiResponse getOsiContents(RequestHeader header, String pnr, String bagTag);

	public EnplanementResponse getEnplanements(RequestHeader header);

	public RemarkResponse writeRemark(RequestHeader header, String pnr);
}
