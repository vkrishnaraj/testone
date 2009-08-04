package aero.nettracer.serviceprovider.ws_1_0.res;

import aero.nettracer.serviceprovider.common.exceptions.UnexpectedException;
import aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader;
import aero.nettracer.serviceprovider.ws_1_0.response.xsd.EnplanementResponse;
import aero.nettracer.serviceprovider.ws_1_0.response.xsd.OsiResponse;
import aero.nettracer.serviceprovider.ws_1_0.response.xsd.RemarkResponse;
import aero.nettracer.serviceprovider.ws_1_0.response.xsd.ReservationResponse;

public interface ReservationInterface {

	public ReservationResponse getReservationData(RequestHeader header, String pnr, String bagTag) throws UnexpectedException;

	public OsiResponse getOsiContents(RequestHeader header, String pnr, String bagTag) throws UnexpectedException;

	public EnplanementResponse getEnplanements(RequestHeader header) throws UnexpectedException;

	public RemarkResponse writeRemark(RequestHeader header, String pnr, String remark) throws UnexpectedException;
}