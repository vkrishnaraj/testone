package com.bagnet.nettracer.reporting.lf;


public class LFDailyStatusReportRow {
	
	private String receivedDate;
	private String stationCode;
	private int hvir;
	private int lvir;
	private int hvirwr;
	private int lvirwr;
	private int hvirwor;
	private int lvirwor;
	private int boxCount;
	
	public String getReceivedDate() {
		return receivedDate;
	}
	
	public void setReceivedDate(String receivedDate) {
		this.receivedDate = receivedDate;
	}
	
	public String getStationCode() {
		return stationCode;
	}
	
	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}
	
	public int getHvir() {
		return hvir;
	}
	
	public void setHvir(int hvir) {
		this.hvir = hvir;
	}
	
	public int getLvir() {
		return lvir;
	}
	
	public void setLvir(int lvir) {
		this.lvir = lvir;
	}
	
	public int getHvirwr() {
		return hvirwr;
	}
	
	public void setHvirwr(int hvirwr) {
		this.hvirwr = hvirwr;
	}
	
	public int getLvirwr() {
		return lvirwr;
	}
	
	public void setLvirwr(int lvirwr) {
		this.lvirwr = lvirwr;
	}
	
	public int getHvirwor() {
		return hvirwor;
	}
	
	public void setHvirwor(int hvirwor) {
		this.hvirwor = hvirwor;
	}
	
	public int getLvirwor() {
		return lvirwor;
	}
	
	public void setLvirwor(int lvirwor) {
		this.lvirwor = lvirwor;
	}

	public int getBoxCount() {
		return boxCount;
	}
	
	public void setBoxCount(int boxCount) {
		this.boxCount = boxCount;
	}

}
