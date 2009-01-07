package com.bagnet.nettracer.cronjob.tracing;

public class IndexReference {
	private int beginningIndex;

	public int getBeginningIndex() {
		return beginningIndex;
	}

	public void setBeginningIndex(int beginningIndex) {
		this.beginningIndex = beginningIndex;
	}
	
	public IndexReference(int i) {
		beginningIndex = i;
	}
}
