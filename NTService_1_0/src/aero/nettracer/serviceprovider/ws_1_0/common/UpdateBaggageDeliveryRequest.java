package aero.nettracer.serviceprovider.ws_1_0.common;


public class UpdateBaggageDeliveryRequest {
	private BaggageDeliveryInfo[] info;
	private String airline; 
	private int bdoNumber;
	private String deliveryCompany;
	private String lastname;
	
	public BaggageDeliveryInfo[] getInfo() {
		return info;
	}
	public void setInfo(BaggageDeliveryInfo[] info) {
		this.info = info;
	}
	public String getAirline() {
		return airline;
	}
	public void setAirline(String airline) {
		this.airline = airline;
	}
	public int getBdoNumber() {
		return bdoNumber;
	}
	public void setBdoNumber(int bdoNumber) {
		this.bdoNumber = bdoNumber;
	}
	public String getDeliveryCompany() {
		return deliveryCompany;
	}
	public void setDeliveryCompany(String deliveryCompany) {
		this.deliveryCompany = deliveryCompany;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	
	
}
