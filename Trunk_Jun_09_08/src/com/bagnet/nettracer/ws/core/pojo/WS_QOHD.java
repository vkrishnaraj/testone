package com.bagnet.nettracer.ws.core.pojo;

import java.util.Date;

public class WS_QOHD {

	private String foundAtStation;
	private Date foundDateTime;
	private String bagTagNumber;
	

	/**
	 * @return the foundAtStation
	 */
	public String getFoundAtStation() {
		return foundAtStation;
	}
	/**
	 * @param foundAtStation the foundAtStation to set
	 */
	public void setFoundAtStation(String foundAtStation) {
		this.foundAtStation = foundAtStation;
	}
	/**
	 * @return the foundDateTime
	 */
	public Date getFounddatetime() {
		return foundDateTime;
	}
	/**
	 * @param founddatetime the foundDateTime to set
	 */
	public void setFounddatetime(Date foundDateTime) {
		this.foundDateTime = foundDateTime;
	}

	/**
	 * @return the bagTagNumber
	 */
	public String getBagTagNumber() {
		return bagTagNumber;
	}
	/**
	 * @param bagtagnum the bagTagNumber to set
	 */
	public void setBagTagNumber(String bagTagNumber) {
		this.bagTagNumber = bagTagNumber;
	}
}
