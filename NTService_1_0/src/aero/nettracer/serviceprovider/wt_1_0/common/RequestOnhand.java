package aero.nettracer.serviceprovider.wt_1_0.common;

public class RequestOnhand {
	private String ahlStationCode;
	private String ahlAirlineCode;
	private String ahlId;
	private String ohdStationCode;
	private String ohdAirlineCode;
	private String ohdId;
	private String supplementaryInfo;
	private String storageLocation;
	private ClaimCheck[] claimCheck;
	private String[] names;

	public String getAhlStationCode() {
		return ahlStationCode;
	}

	public void setAhlStationCode(String ahlStationCode) {
		this.ahlStationCode = ahlStationCode;
	}

	public String getAhlAirlineCode() {
		return ahlAirlineCode;
	}

	public void setAhlAirlineCode(String ahlAirlineCode) {
		this.ahlAirlineCode = ahlAirlineCode;
	}

	public String getAhlId() {
		return ahlId;
	}

	public void setAhlId(String ahlId) {
		this.ahlId = ahlId;
	}

	public String getOhdStationCode() {
		return ohdStationCode;
	}

	public void setOhdStationCode(String ohdStationCode) {
		this.ohdStationCode = ohdStationCode;
	}

	public String getOhdAirlineCode() {
		return ohdAirlineCode;
	}

	public void setOhdAirlineCode(String ohdAirlineCode) {
		this.ohdAirlineCode = ohdAirlineCode;
	}

	public String getOhdId() {
		return ohdId;
	}

	public void setOhdId(String ohdId) {
		this.ohdId = ohdId;
	}

	public String getSupplementaryInfo() {
		return supplementaryInfo;
	}

	public void setSupplementaryInfo(String supplementaryInfo) {
		this.supplementaryInfo = supplementaryInfo;
	}

	public String getStorageLocation() {
		return storageLocation;
	}

	public void setStorageLocation(String storageLocation) {
		this.storageLocation = storageLocation;
	}

	public String[] getNames() {
		return names;
	}

	public void setNames(String[] names) {
		this.names = names;
	}

	public ClaimCheck[] getClaimCheck() {
		return claimCheck;
	}

	public void setClaimCheck(ClaimCheck[] claimCheck) {
		this.claimCheck = claimCheck;
	}

}
