package aero.nettracer.serviceprovider.ws_1_0.res.sabre;

import aero.nettracer.serviceprovider.common.db.SabreConnection;
import aero.nettracer.serviceprovider.common.exceptions.UnexpectedException;
import aero.nettracer.serviceprovider.ws_1_0.common.xsd.RequestHeader;
import aero.nettracer.serviceprovider.ws_1_0.res.ReservationInterface;
import aero.nettracer.serviceprovider.ws_1_0.res.sabre.pool.SabrePool;
import aero.nettracer.serviceprovider.ws_1_0.response.xsd.EnplanementResponse;
import aero.nettracer.serviceprovider.ws_1_0.response.xsd.OsiResponse;
import aero.nettracer.serviceprovider.ws_1_0.response.xsd.RemarkResponse;
import aero.nettracer.serviceprovider.ws_1_0.response.xsd.ReservationResponse;


public class Reservation implements ReservationInterface {

	@Override
	public EnplanementResponse getEnplanements(RequestHeader header) {
		return null;
	}

	@Override
	public OsiResponse getOsiContents(RequestHeader header, String pnr, String bagTag) {
		return null;
	}

	@Override
	public ReservationResponse getReservationData(RequestHeader header, String pnr, String bagTag)  throws UnexpectedException {
		
		return null;
	}

	@Override
	public RemarkResponse writeRemark(RequestHeader header, String pnr, String comment) throws UnexpectedException{
		// TODO: Set up and configure pool.
		SabrePool pool = new SabrePool();
		SabreConnection connParams = null;
		try {
	    connParams = (SabreConnection) pool.borrowObject();
	    
	    // TODO: LOG ON (move to borrow?)
	    
	    // TODO: LOAD PNR
	    
	    // TODO: WRITE REMARK
	    
	    // TODO: END RECORD
	    
	    // TODO: IGNORE IF EXCEPTION IN END RECORD
	    
	    // TODO: LOG OFF (move to return?)

    } catch (Exception e) {
	    e.printStackTrace();
	    throw new UnexpectedException();
    } finally {
    	try {
	      pool.returnObject(connParams);
      } catch (Exception e) {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
      }
    }
		return null;
	}
}
