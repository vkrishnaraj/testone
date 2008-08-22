/**
 * 
 */
package com.bagnet.nettracer.tracing.dto;

/**
 * @author Byron
 *
 */
public class ScannerDataDTO {

	private String string1;
	private String string2;
	private String string3;
	private String string4;
	private String ohdId;
	
	public ScannerDataDTO() {};
	
	public ScannerDataDTO (String timeStamp, String station, String scanType, String otherInfo, String ohdId) {
		this.setString1(timeStamp);
		this.setString2(station);
		this.setString3(scanType);
		this.setString4(otherInfo);
		this.setOhdId(ohdId);
	}

	/**
	 * @return the string1
	 */
	public String getString1() {
		return string1;
	}

	/**
	 * @param string1 the string1 to set
	 */
	public void setString1(String string1) {
		this.string1 = string1;
	}

	/**
	 * @return the string2
	 */
	public String getString2() {
		return string2;
	}

	/**
	 * @param string2 the string2 to set
	 */
	public void setString2(String string2) {
		this.string2 = string2;
	}

	/**
	 * @return the string3
	 */
	public String getString3() {
		return string3;
	}

	/**
	 * @param string3 the string3 to set
	 */
	public void setString3(String string3) {
		this.string3 = string3;
	}

	/**
	 * @return the string4
	 */
	public String getString4() {
		return string4;
	}

	/**
	 * @param string4 the string4 to set
	 */
	public void setString4(String string4) {
		this.string4 = string4;
	}

	/**
	 * @return the ohdId
	 */
	public String getOhdId() {
		return ohdId;
	}

	/**
	 * @param ohdId the ohdId to set
	 */
	public void setOhdId(String ohdId) {
		this.ohdId = ohdId;
	}
	
		
}
