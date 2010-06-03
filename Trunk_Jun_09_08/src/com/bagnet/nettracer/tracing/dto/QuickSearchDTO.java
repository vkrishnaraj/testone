package com.bagnet.nettracer.tracing.dto;

import java.util.Date;
import java.util.List;

import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.OHD;

public class QuickSearchDTO {
	private String redirect = null;
	private boolean prepop;
	private int prepopType;

	private boolean displayIncList;
	private List<Incident> iList;
	private boolean iMore;

	private boolean displayOhdList;
	private List<OHD> oList;
	private boolean oMore;

	private boolean displayScanList;
	private List<ScannerDataDTO> sList;
	private boolean sMore;
	private String scanError;
	private Date startDate;
	private Date endDate;

	public String getRedirect() {
		return redirect;
	}

	public void setRedirect(String redirect) {
		this.redirect = redirect;
	}

	public List<Incident> getIList() {
		return iList;
	}

	public void setIList(List<Incident> list) {
		iList = list;
	}

	public List<OHD> getOList() {
		return oList;
	}

	public void setOList(List<OHD> list) {
		oList = list;
	}

	public List<ScannerDataDTO> getSList() {
		return sList;
	}

	public void setSList(List<ScannerDataDTO> list) {
		sList = list;
	}

	public boolean isIMore() {
		return iMore;
	}

	public void setIMore(boolean more) {
		iMore = more;
	}

	public boolean isOMore() {
		return oMore;
	}

	public void setOMore(boolean more) {
		oMore = more;
	}

	public boolean isSMore() {
		return sMore;
	}

	public void setSMore(boolean more) {
		sMore = more;
	}

	public boolean isDisplayIncList() {
		return displayIncList;
	}

	public void setDisplayIncList(boolean displayIncList) {
		this.displayIncList = displayIncList;
	}

	public boolean isDisplayOhdList() {
		return displayOhdList;
	}

	public void setDisplayOhdList(boolean displayOhdList) {
		this.displayOhdList = displayOhdList;
	}

	public boolean isDisplayScanList() {
		return displayScanList;
	}

	public void setDisplayScanList(boolean displayScanList) {
		this.displayScanList = displayScanList;
	}

	public boolean isPrepop() {
		return prepop;
	}

	public void setPrepop(boolean prepop) {
		this.prepop = prepop;
	}

	public int getPrepopType() {
		return prepopType;
	}

	public void setPrepopType(int prepopType) {
		this.prepopType = prepopType;
	}

	public String getScanError() {
		return scanError;
	}

	public void setScanError(String scanError) {
		this.scanError = scanError;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
