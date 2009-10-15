package aero.nettracer.serviceprovider.wt_1_0.dto;

import aero.nettracer.serviceprovider.common.db.ParameterType;

/*
 * Special Note:
 * 
 * All of these enum elements must also exist in the 
 * aero.nettracer.serviceprovider.common.db.ParameterType class
 * with WTSM_ prepended to it (to add clarity when looking at the DB)
 */
public enum WorldTracerActionType {

	GET_AHL, CREATE_AHL, AMEND_AHL, CLOSE_AHL, SUSPEND_AHL, REINSTATE_AHL, GET_OHD, CREATE_OHD, AMEND_OHD, CLOSE_OHD, FORWARD_OHD, REQUEST_OHD, REQUEST_QOHD, CREATE_BDO, ERASE_ACTION_FILE, ACTION_FILE_COUNTS, ACTION_FILE_SUMMARY, ACTION_FILE_DETAILS, SEND_FORWARD_MESSAGE, PLACE_ACTION_FILE;

	public ParameterType getParameterType() {
		return ParameterType.valueOf("WTSM_" + this.name());
	}

}
