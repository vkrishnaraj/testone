package aero.nettracer.monitor;

public class MonitorDB {
	private String DBName;
	private String ConnectionURL;
	private String test;
//	private String paramString;
	private boolean isProduction;
//	private int tryAgain;
//	public int getTryAgain() {
//		return tryAgain;
//	}
//
//	public void setTryAgain(int tryAgain) {
//		this.tryAgain = tryAgain;
//	}

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

	public MonitorDB(String title, String url) {
		this.DBName = title;
		this.ConnectionURL = url;
		this.timeout = timeout;
//		this.test = test;
//		this.paramString = paramString;
//		this.isProduction = isProduction;
//		this.tryAgain = tryAgain;
	}

	public String getDBName() {
		return DBName;
	}

	public void setDBName(String title) {
		this.DBName = title;
	}

	public String getConnectionURL() {
		return ConnectionURL;
	}

	public void setConnectionURL(String url) {
		this.ConnectionURL = url;
	}

	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}

//	public String getParamString() {
//		return paramString;
//	}
//
//	public void setParamString(String paramString) {
//		this.paramString = paramString;
//	}

}
