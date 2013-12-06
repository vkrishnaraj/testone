package aero.nettracer.serviceprovider.ws_1_0.res;

import aero.nettracer.serviceprovider.common.db.User;
import aero.nettracer.serviceprovider.ws_1_0.common.xsd.Voucher;
import aero.nettracer.serviceprovider.ws_1_0.response.xsd.VoucherResponse;

/**
 * Interface for VoucherService integration to submit and update voucher information
 * 
 * @author Loupas
 *
 */
public interface VoucherServiceInterface {
	/**
	 * @param user
	 * @param voucher
	 * @return
	 */
	public VoucherResponse submitVoucher(User user, Voucher voucher);
}
