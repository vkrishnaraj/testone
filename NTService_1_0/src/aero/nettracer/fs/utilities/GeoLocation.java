package aero.nettracer.fs.utilities;

public class GeoLocation {
	double latitude;
	double longitude;

	public GeoLocation(double longitude, double latitude) {
		this.longitude = longitude;
		this.latitude = latitude;
	}
	
	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

}
