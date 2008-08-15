package com.bagnet.nettracer.ws.core.pojo;

import java.util.Date;

public class WS_QOHD {

	private String foundAtStation;
	private Date founddatetime;
	private String bagTagNumber;
	private String comment;
	

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
		return founddatetime;
	}
	/**
	 * @param founddatetime the foundDateTime to set
	 */
	public void setFounddatetime(Date foundDateTime) {
		this.founddatetime = foundDateTime;
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
	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}
	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
}
