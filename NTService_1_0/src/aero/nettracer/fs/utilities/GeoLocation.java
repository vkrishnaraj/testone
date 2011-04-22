package aero.nettracer.fs.utilities;

public class GeoLocation {
	double latitude;
	double longitude;
	int type;

	public GeoLocation(double longitude, double latitude, int type) {
		this.longitude = longitude;
		this.latitude = latitude;
		this.type = type;
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
