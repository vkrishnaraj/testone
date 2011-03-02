package com.bagnet.nettracer.tracing.utils.taskmanager;

import com.bagnet.nettracer.tracing.db.Station;

public class MorningDutiesDJReport {
	Station station;
	int dayTwoCount = 0;
	int dayThreeCount = 0;
	int dayFourCount = 0;

	int yDayTwoCount;
	int yDayThreeCount;
	int yDayFourCount;

	
	public int getTotalCalls(){
		return dayTwoCount + dayThreeCount + dayFourCount;
	}
	
	public int getTotalYesterdayCalls(){
		return yDayTwoCount+yDayThreeCount+yDayFourCount;
	}
	
	public int getTotalYesterdayCompleteCalls(){
		return yDayTwoComplete+yDayThreeComplete+yDayFourComplete;
	}
	
	public int getYesterdaysTwoPercent(){
		if(yDayTwoCount == 0){
			return 0;
		} else {
			return yDayTwoComplete / yDayTwoCount * 100;
		}
	}
	
	public int getYesterdaysThreePercent(){
		if(yDayThreeCount == 0){
			return 0;
		} else {
			return yDayThreeComplete / yDayThreeCount * 100;
		}
	}
	
	public int getYesterdaysFourPercent(){
		if(yDayFourCount == 0){
			return 0;
		} else {
			return yDayFourComplete / yDayFourCount * 100;
		}
	}
	
	public int getYesterdaysTotalPercent(){
		if(getTotalYesterdayCalls() == 0){
			return 0;
		} else {
			return getTotalYesterdayCompleteCalls() / getTotalYesterdayCalls() * 100;
		}
	}
	
	public Station getStation() {
		return station;
	}
	public void setStation(Station station) {
		this.station = station;
	}
	public int getDayTwoCount() {
		return dayTwoCount;
	}
	public void setDayTwoCount(int dayTwoCount) {
		this.dayTwoCount = dayTwoCount;
	}
	public int getDayThreeCount() {
		return dayThreeCount;
	}
	public void setDayThreeCount(int dayThreeCount) {
		this.dayThreeCount = dayThreeCount;
	}
	public int getDayFourCount() {
		return dayFourCount;
	}
	public void setDayFourCount(int dayFourCount) {
		this.dayFourCount = dayFourCount;
	}
	public int getyDayTwoCount() {
		return yDayTwoCount;
	}
	public void setyDayTwoCount(int yDayTwoCount) {
		this.yDayTwoCount = yDayTwoCount;
	}
	public int getyDayThreeCount() {
		return yDayThreeCount;
	}
	public void setyDayThreeCount(int yDayThreeCount) {
		this.yDayThreeCount = yDayThreeCount;
	}
	public int getyDayFourCount() {
		return yDayFourCount;
	}
	public void setyDayFourCount(int yDayFourCount) {
		this.yDayFourCount = yDayFourCount;
	}
	public int getyDayTwoComplete() {
		return yDayTwoComplete;
	}
	public void setyDayTwoComplete(int yDayTwoComplete) {
		this.yDayTwoComplete = yDayTwoComplete;
	}
	public int getyDayThreeComplete() {
		return yDayThreeComplete;
	}
	public void setyDayThreeComplete(int yDayThreeComplete) {
		this.yDayThreeComplete = yDayThreeComplete;
	}
	public int getyDayFourComplete() {
		return yDayFourComplete;
	}
	public void setyDayFourComplete(int yDayFourComplete) {
		this.yDayFourComplete = yDayFourComplete;
	}
	int yDayTwoComplete;
	int yDayThreeComplete;
	int yDayFourComplete;
}
