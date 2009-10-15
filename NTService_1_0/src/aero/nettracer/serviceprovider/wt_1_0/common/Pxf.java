package aero.nettracer.serviceprovider.wt_1_0.common;

public class Pxf {
	private int destination;
	private PxfAas[] pxfDetails;
	private String[] teletype;
	private String content;

	public int getDestination() {
		return destination;
	}

	public void setDestination(int destination) {
		this.destination = destination;
	}

	public PxfAas[] getPxfDetails() {
		return pxfDetails;
	}

	public void setPxfDetails(PxfAas[] pxfDetails) {
		this.pxfDetails = pxfDetails;
	}

	public String[] getTeletype() {
		return teletype;
	}

	public void setTeletype(String[] teletype) {
		this.teletype = teletype;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
