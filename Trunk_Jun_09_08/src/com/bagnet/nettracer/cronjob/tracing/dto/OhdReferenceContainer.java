package com.bagnet.nettracer.cronjob.tracing.dto;

import java.util.ArrayList;
import java.util.Date;

public class OhdReferenceContainer {
	private Date referenceDate;
	private int index;
	private ArrayList<String> ohdList;
	
	public OhdReferenceContainer(Date date, int index, ArrayList<String> ohdList) {
		this.referenceDate = date;
		this.index = index;
		this.ohdList = ohdList;
	}
	
	/**
	 * @return the referenceDate
	 */
	public Date getReferenceDate() {
		return referenceDate;
	}
	/**
	 * @param referenceDate the referenceDate to set
	 */
	public void setReferenceDate(Date referenceDate) {
		this.referenceDate = referenceDate;
	}
	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}
	/**
	 * @param index the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
	}
	/**
	 * @return the ohdList
	 */
	public ArrayList<String> getOhdList() {
		return ohdList;
	}
	/**
	 * @param ohdList the ohdList to set
	 */
	public void setOhdList(ArrayList<String> ohdList) {
		this.ohdList = ohdList;
	}
}
