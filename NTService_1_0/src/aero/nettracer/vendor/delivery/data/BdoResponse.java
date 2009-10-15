package aero.nettracer.vendor.delivery.data;

public class BdoResponse {
	private String vendorReference;
	private String errorMessage;

	public String getVendorReference() {
		return vendorReference;
	}

	public void setVendorReference(String vendorReference) {
		this.vendorReference = vendorReference;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
