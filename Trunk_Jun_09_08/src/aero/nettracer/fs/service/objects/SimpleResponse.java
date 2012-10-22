package aero.nettracer.fs.service.objects;


public class ClaimResponse {

	private long fileId;
	private String directAccessUrl;
	private String searchSummary;
	private int warningLevel;
	private int warningColor;
	private boolean success;
	private String[] error;

	
	public long getFileId() {
		return fileId;
	}

	public void setFileId(long fileId) {
		this.fileId = fileId;
	}

	public String getDirectAccessUrl() {
		return directAccessUrl;
	}

	public void setDirectAccessUrl(String directAccessUrl) {
		this.directAccessUrl = directAccessUrl;
	}

	public String getSearchSummary() {
		return searchSummary;
	}

	public void setSearchSummary(String searchSummary) {
		this.searchSummary = searchSummary;
	}

	public int getWarningLevel() {
		return warningLevel;
	}

	public void setWarningLevel(int warningLevel) {
		this.warningLevel = warningLevel;
	}

	public int getWarningColor() {
		return warningColor;
	}

	public void setWarningColor(int warningColor) {
		this.warningColor = warningColor;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setError(String[] error) {
		this.error = error;
	}

	public String[] getError() {
		return error;
	}

}
