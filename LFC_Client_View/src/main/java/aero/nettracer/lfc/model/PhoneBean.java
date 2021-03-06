package aero.nettracer.lfc.model;

import java.io.Serializable;

import com.bagnet.nettracer.tracing.constant.TracingConstants;

public class PhoneBean implements Serializable{

	private static final long serialVersionUID = -6086762016588491652L;
	
	private String number;
	private String country;
	private String area;
	private String exchange;
	private String line;
	private String extension;
	private int type;
	
	//Putting Constants here so that ui has access.
	private int work = TracingConstants.LF_PHONE_TYPE_WORK;
	private int home = TracingConstants.LF_PHONE_TYPE_HOME;
	private int mobile = TracingConstants.LF_PHONE_TYPE_MOBILE;
	private int other = TracingConstants.LF_PHONE_TYPE_OTHER;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	public int getWork() {
		return work;
	}

	public void setWork(int work) {
		this.work = work;
	}

	public int getHome() {
		return home;
	}

	public void setHome(int home) {
		this.home = home;
	}

	public int getMobile() {
		return mobile;
	}

	public void setMobile(int mobile) {
		this.mobile = mobile;
	}

	public int getOther() {
		return other;
	}

	public void setOther(int other) {
		this.other = other;
	}

	public String getTypeText() {
		switch(type) {
		case TracingConstants.LF_PHONE_TYPE_WORK:
			return "Work: ";
		case TracingConstants.LF_PHONE_TYPE_HOME:
			return "Home: ";
		case TracingConstants.LF_PHONE_TYPE_MOBILE:
			return "Mobile: ";
		case TracingConstants.LF_PHONE_TYPE_OTHER:
			return "Other: ";
		default:
			return "";	
		}
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getExchange() {
		return exchange;
	}

	public void setExchange(String exchange) {
		this.exchange = exchange;
	}

	public String getLine() {
		if(line!=null && !line.isEmpty())
			return line;
		else if (number!=null && !number.isEmpty())
			return number;
		return "";
	}

	public void setLine(String line) {
		this.line = line;
	}

}
