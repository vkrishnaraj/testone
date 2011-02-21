package com.bagnet.nettracer.tracing.utils;

import com.bagnet.nettracer.tracing.db.Station;

public class PrecoderResult {
	Station faultStation;
	int lossCode;
	String remark;
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Station getFaultStation() {
		return faultStation;
	}
	public void setFaultStation(Station faultStation) {
		this.faultStation = faultStation;
	}
	public int getLossCode() {
		return lossCode;
	}
	public void setLossCode(int lossCode) {
		this.lossCode = lossCode;
	}
}
