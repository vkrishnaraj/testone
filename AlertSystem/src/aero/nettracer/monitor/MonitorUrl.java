package aero.nettracer.monitor;

public class MonitorUrl {
	private String title;
	private String url;
	private String test;
	private String paramString;
	private boolean isProduction;
	private int tryAgain;
	public int getTryAgain() {
		return tryAgain;
	}

	public void setTryAgain(int tryAgain) {
		this.tryAgain = tryAgain;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	private int timeout;
	
	public boolean isProduction() {
		return isProduction;
	}

	public void setProduction(boolean isProduction) {
		this.isProduction = isProduction;
	}

	public MonitorUrl(String title, String url, String paramString, String test, boolean isProduction, int timeout, int tryAgain) {
		this.title = title;
		this.url = url;
		this.test = test;
		this.paramString = paramString;
		this.isProduction = isProduction;
		this.timeout = timeout;
		this.tryAgain = tryAgain;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}

	public String getParamString() {
		return paramString;
	}

	public void setParamString(String paramString) {
		this.paramString = paramString;
	}

}
