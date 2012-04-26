package RealAutoProcessesNotTests;

public class AutoClose {

	String barcode;

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getTrackingNumber() {
		return trackingNumber;
	}

	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

	String trackingNumber;

	public AutoClose(String barcode, String trackingNumber) {
		this.barcode = barcode;
		this.trackingNumber = trackingNumber;
	}

}
