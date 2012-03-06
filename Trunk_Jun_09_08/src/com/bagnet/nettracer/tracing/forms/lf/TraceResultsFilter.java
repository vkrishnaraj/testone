package com.bagnet.nettracer.tracing.forms.lf;

import java.io.Serializable;

public class TraceResultsFilter implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5206810436388053586L;
	private long lostId;
	private long foundId;
	private boolean open;
	private boolean closed;
	private boolean confirmed;
	private boolean rejected;
	private String barcode;
	
	public long getLostId() {
		return lostId;
	}
	
	public void setLostId(long lostId) {
		this.lostId = lostId;
	}
	
	public long getFoundId() {
		return foundId;
	}
	
	public void setFoundId(long foundId) {
		this.foundId = foundId;
	}
	
	public boolean getOpen() {
		return open;
	}
	
	public void setOpen(boolean open) {
		this.open = open;
	}
	
	public boolean getClosed() {
		return closed;
	}
	
	public void setClosed(boolean closed) {
		this.closed = closed;
	}
	
	public boolean getConfirmed() {
		return confirmed;
	}
	
	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}
	
	public boolean getRejected() {
		return rejected;
	}
	
	public void setRejected(boolean rejected) {
		this.rejected = rejected;
	}
	
	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public boolean filterResults() {
		boolean filterResults = false;
		filterResults |= open;
		filterResults |= closed;
		filterResults |= confirmed;
		filterResults |= rejected;
		filterResults |= lostId > 0;
		filterResults |= foundId > 0;
		return filterResults;
	}
}
