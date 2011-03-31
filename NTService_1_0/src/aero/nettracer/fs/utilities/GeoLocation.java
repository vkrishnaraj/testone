package aero.nettracer.fs.utilities;

public class GeoLocation {
	String latitude;
	String longitude;

	public GeoLocation(String longitude, String latitude) {
		this.longitude = longitude;
		this.latitude = latitude;
	}
	
	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

}
