package com.bagnet.nettracer.tracing.db.lf;

public interface LFObject {
	public long getId();
	public String getStatusDescription();
	public String getDisStation();
	public String getDisplayDate(String dateFormat);
	public String getClientName();
	public String getBarcode();
}
