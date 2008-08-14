package com.bagnet.nettracer.wt;

public class CountActionFile {
	private String type;
	private int[] counts = new int[7];
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public int[] getCounts() {
		return counts;
	}
	
	public int getDayCount(int day) {
		if(day > 0 && day <= 7) {
		return counts[day - 1];
		}
		else 
			return 0;
	}
	
	public void setDayCount(int day, int amount) {
		if(day > 0 && day <= 7) {
			counts[day - 1] = amount;
		}
	}
}
