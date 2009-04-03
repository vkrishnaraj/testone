package com.bagnet.nettracer.tracing.db.wt;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;

import javax.persistence.Embeddable;

@Embeddable
public class ActionFileCount implements Serializable, Iterable<Integer>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3545886590094938766L;
	
	int dayOne;
	boolean dayOneLoaded;
	int dayTwo;
	boolean dayTwoLoaded;
	int dayThree;
	boolean dayThreeLoaded;
	int dayFour;
	boolean dayFourLoaded;
	int dayFive;
	boolean dayFiveLoaded;
	int daySix;
	boolean daySixLoaded;
	int daySeven;
	boolean daySevenLoaded;
	
	public int getDayOne() {
		return dayOne;
	}
	public void setDayOne(int dayOne) {
		this.dayOne = dayOne;
	}
	public int getDayTwo() {
		return dayTwo;
	}
	public void setDayTwo(int dayTwo) {
		this.dayTwo = dayTwo;
	}
	public int getDayThree() {
		return dayThree;
	}
	public void setDayThree(int dayThree) {
		this.dayThree = dayThree;
	}
	public int getDayFour() {
		return dayFour;
	}
	public void setDayFour(int dayFour) {
		this.dayFour = dayFour;
	}
	public int getDayFive() {
		return dayFive;
	}
	public void setDayFive(int dayFive) {
		this.dayFive = dayFive;
	}
	public int getDaySix() {
		return daySix;
	}
	public void setDaySix(int daySix) {
		this.daySix = daySix;
	}
	public int getDaySeven() {
		return daySeven;
	}
	public void setDaySeven(int daySeven) {
		this.daySeven = daySeven;
	}
	
	
	
	public boolean isDayOneLoaded() {
		return dayOneLoaded;
	}
	public void setDayOneLoaded(boolean dayOneLoaded) {
		this.dayOneLoaded = dayOneLoaded;
	}
	public boolean isDayTwoLoaded() {
		return dayTwoLoaded;
	}
	public void setDayTwoLoaded(boolean dayTwoLoaded) {
		this.dayTwoLoaded = dayTwoLoaded;
	}
	public boolean isDayThreeLoaded() {
		return dayThreeLoaded;
	}
	public void setDayThreeLoaded(boolean dayThreeLoaded) {
		this.dayThreeLoaded = dayThreeLoaded;
	}
	public boolean isDayFourLoaded() {
		return dayFourLoaded;
	}
	public void setDayFourLoaded(boolean dayFourLoaded) {
		this.dayFourLoaded = dayFourLoaded;
	}
	public boolean isDayFiveLoaded() {
		return dayFiveLoaded;
	}
	public void setDayFiveLoaded(boolean dayFiveLoaded) {
		this.dayFiveLoaded = dayFiveLoaded;
	}
	public boolean isDaySixLoaded() {
		return daySixLoaded;
	}
	public void setDaySixLoaded(boolean daySixLoaded) {
		this.daySixLoaded = daySixLoaded;
	}
	public boolean isDaySevenLoaded() {
		return daySevenLoaded;
	}
	public void setDaySevenLoaded(boolean daySevenLoaded) {
		this.daySevenLoaded = daySevenLoaded;
	}
	public Iterator<Integer> iterator() {
		return Arrays.asList(dayOne, dayTwo, dayThree, dayFour, dayFive, daySix, daySeven).iterator();
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

}
