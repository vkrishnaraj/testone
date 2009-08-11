package aero.nettracer.serviceprovider.ws_1_0.res;

import aero.nettracer.serviceprovider.common.db.User;
import aero.nettracer.serviceprovider.common.exceptions.UnexpectedException;
import aero.nettracer.serviceprovider.ws_1_0.response.xsd.EnplanementResponse;
import aero.nettracer.serviceprovider.ws_1_0.response.xsd.OsiResponse;
import aero.nettracer.serviceprovider.ws_1_0.response.xsd.RemarkResponse;
import aero.nettracer.serviceprovider.ws_1_0.response.xsd.ReservationResponse;

public interface ReservationInterface {

	public ReservationResponse getReservationData(User user, String pnr, String bagTag) throws UnexpectedException;

	public OsiResponse getOsiContents(User user, String pnr, String bagTag) throws UnexpectedException;

	public EnplanementResponse getEnplanements(User user) throws UnexpectedException;

	public RemarkResponse writeRemark(User user, String pnr, String remark) throws UnexpectedException;
}