package com.bagnet.nettracer.tracing.forms.lf;

public class TraceResultsFilter {

	private int lostId;
	private int foundId;
	private boolean open;
	private boolean closed;
	private boolean confirmed;
	private boolean rejected;
	
	public int getLostId() {
		return lostId;
	}
	
	public void setLostId(int lostId) {
		this.lostId = lostId;
	}
	
	public int getFoundId() {
		return foundId;
	}
	
	public void setFoundId(int foundId) {
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
