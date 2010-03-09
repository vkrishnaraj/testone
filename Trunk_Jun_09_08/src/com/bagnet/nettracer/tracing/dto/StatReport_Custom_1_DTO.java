/*
 * Created on Sep 1, 2004
 *
 * Administrator
 */
package com.bagnet.nettracer.tracing.dto;

import com.bagnet.nettracer.tracing.utils.TracerUtils;


/**
 * @author Administrator
 *
 * create date - Sep 1, 2004
 */
/**
 * top flight temporary class
 * 
 * @author Administrator
 * 
 * create date - Sep 1, 2004
 */
public class StatReport_Custom_1_DTO {
	private String companycode;
	private String stationcode;
	private String station_region;
	private String station_region_mgr;
	private int loss10;
	private int loss12;
	private int loss15;
	private int loss18;
	private int loss23;
	private int loss24;
	private int loss25;
	private int loss26;
	private int loss30;
	private int loss32;
	private int loss33;
	private int loss35;
	private int loss42;
	private int loss51;
	private int loss54;
	private int loss56;
	private int loss63;
	private int loss64;
	private int loss73;
	private int loss74;
	private int loss76;
	private int loss78;
	private int loss80;
	private int loss81;
	private int loss82;
	private int loss90;
	private long total;
	private double boarded;
	private double ratio;
	private double goal;
	private double goal_times_boarded;
	
	/**
	 * @return Returns the station_region.
	 */
	public String getStation_region() {
		return station_region;
	}
	/**
	 * @param station_region The station_region to set.
	 */
	public void setStation_region(String station_region) {
		this.station_region = station_region;
	}
	/**
	 * @return Returns the station_region_mgr.
	 */
	public String getStation_region_mgr() {
		return station_region_mgr;
	}
	/**
	 * @param station_region_mgr The station_region_mgr to set.
	 */
	public void setStation_region_mgr(String station_region_mgr) {
		this.station_region_mgr = station_region_mgr;
	}
	
	/**
	 * @return Returns the goal.
	 */
	public double getGoal() {
		return goal;
	}
	/**
	 * @param goal The goal to set.
	 */
	public void setGoal(double goal) {
		this.goal = goal;
	}

	/**
	 * @return Returns the loss10.
	 */
	public int getLoss10() {
		return loss10;
	}
	/**
	 * @param loss10 The loss10 to set.
	 */
	public void setLoss10(int loss10) {
		this.loss10 = loss10;
	}
	/**
	 * @return Returns the loss12.
	 */
	public int getLoss12() {
		return loss12;
	}
	/**
	 * @param loss12 The loss12 to set.
	 */
	public void setLoss12(int loss12) {
		this.loss12 = loss12;
	}
	/**
	 * @return Returns the loss15.
	 */
	public int getLoss15() {
		return loss15;
	}
	/**
	 * @param loss15 The loss15 to set.
	 */
	public void setLoss15(int loss15) {
		this.loss15 = loss15;
	}
	/**
	 * @return Returns the loss18.
	 */
	public int getLoss18() {
		return loss18;
	}
	/**
	 * @param loss18 The loss18 to set.
	 */
	public void setLoss18(int loss18) {
		this.loss18 = loss18;
	}
	/**
	 * @return Returns the loss23.
	 */
	public int getLoss23() {
		return loss23;
	}
	/**
	 * @param loss23 The loss23 to set.
	 */
	public void setLoss23(int loss23) {
		this.loss23 = loss23;
	}
	/**
	 * @return Returns the loss24.
	 */
	public int getLoss24() {
		return loss24;
	}
	/**
	 * @param loss24 The loss24 to set.
	 */
	public void setLoss24(int loss24) {
		this.loss24 = loss24;
	}
	/**
	 * @return Returns the loss25.
	 */
	public int getLoss25() {
		return loss25;
	}
	/**
	 * @param loss25 The loss25 to set.
	 */
	public void setLoss25(int loss25) {
		this.loss25 = loss25;
	}
	/**
	 * @return Returns the loss26.
	 */
	public int getLoss26() {
		return loss26;
	}
	/**
	 * @param loss26 The loss26 to set.
	 */
	public void setLoss26(int loss26) {
		this.loss26 = loss26;
	}
	/**
	 * @return Returns the loss30.
	 */
	public int getLoss30() {
		return loss30;
	}
	/**
	 * @param loss30 The loss30 to set.
	 */
	public void setLoss30(int loss30) {
		this.loss30 = loss30;
	}
	/**
	 * @return Returns the loss32.
	 */
	public int getLoss32() {
		return loss32;
	}
	/**
	 * @param loss32 The loss32 to set.
	 */
	public void setLoss32(int loss32) {
		this.loss32 = loss32;
	}
	/**
	 * @return Returns the loss33.
	 */
	public int getLoss33() {
		return loss33;
	}
	/**
	 * @param loss33 The loss33 to set.
	 */
	public void setLoss33(int loss33) {
		this.loss33 = loss33;
	}
	/**
	 * @return Returns the loss35.
	 */
	public int getLoss35() {
		return loss35;
	}
	/**
	 * @param loss35 The loss35 to set.
	 */
	public void setLoss35(int loss35) {
		this.loss35 = loss35;
	}
	/**
	 * @return Returns the loss42.
	 */
	public int getLoss42() {
		return loss42;
	}
	/**
	 * @param loss42 The loss42 to set.
	 */
	public void setLoss42(int loss42) {
		this.loss42 = loss42;
	}
	/**
	 * @return Returns the loss51.
	 */
	public int getLoss51() {
		return loss51;
	}
	/**
	 * @param loss51 The loss51 to set.
	 */
	public void setLoss51(int loss51) {
		this.loss51 = loss51;
	}
	/**
	 * @return Returns the loss54.
	 */
	public int getLoss54() {
		return loss54;
	}
	/**
	 * @param loss54 The loss54 to set.
	 */
	public void setLoss54(int loss54) {
		this.loss54 = loss54;
	}
	/**
	 * @return Returns the loss56.
	 */
	public int getLoss56() {
		return loss56;
	}
	/**
	 * @param loss56 The loss56 to set.
	 */
	public void setLoss56(int loss56) {
		this.loss56 = loss56;
	}
	/**
	 * @return Returns the loss63.
	 */
	public int getLoss63() {
		return loss63;
	}
	/**
	 * @param loss63 The loss63 to set.
	 */
	public void setLoss63(int loss63) {
		this.loss63 = loss63;
	}
	/**
	 * @return Returns the loss64.
	 */
	public int getLoss64() {
		return loss64;
	}
	/**
	 * @param loss64 The loss64 to set.
	 */
	public void setLoss64(int loss64) {
		this.loss64 = loss64;
	}
	/**
	 * @return Returns the loss73.
	 */
	public int getLoss73() {
		return loss73;
	}
	/**
	 * @param loss73 The loss73 to set.
	 */
	public void setLoss73(int loss73) {
		this.loss73 = loss73;
	}
	/**
	 * @return Returns the loss74.
	 */
	public int getLoss74() {
		return loss74;
	}
	/**
	 * @param loss74 The loss74 to set.
	 */
	public void setLoss74(int loss74) {
		this.loss74 = loss74;
	}
	/**
	 * @return Returns the loss76.
	 */
	public int getLoss76() {
		return loss76;
	}
	/**
	 * @param loss76 The loss76 to set.
	 */
	public void setLoss76(int loss76) {
		this.loss76 = loss76;
	}
	/**
	 * @return Returns the loss78.
	 */
	public int getLoss78() {
		return loss78;
	}
	/**
	 * @param loss78 The loss78 to set.
	 */
	public void setLoss78(int loss78) {
		this.loss78 = loss78;
	}
	/**
	 * @return Returns the loss80.
	 */
	public int getLoss80() {
		return loss80;
	}
	/**
	 * @param loss80 The loss80 to set.
	 */
	public void setLoss80(int loss80) {
		this.loss80 = loss80;
	}
	/**
	 * @return Returns the loss81.
	 */
	public int getLoss81() {
		return loss81;
	}
	/**
	 * @param loss81 The loss81 to set.
	 */
	public void setLoss81(int loss81) {
		this.loss81 = loss81;
	}
	/**
	 * @return Returns the loss82.
	 */
	public int getLoss82() {
		return loss82;
	}
	/**
	 * @param loss82 The loss82 to set.
	 */
	public void setLoss82(int loss82) {
		this.loss82 = loss82;
	}
	/**
	 * @return Returns the loss90.
	 */
	public int getLoss90() {
		return loss90;
	}
	/**
	 * @param loss90 The loss90 to set.
	 */
	public void setLoss90(int loss90) {
		this.loss90 = loss90;
	}
	/**
	 * @return Returns the ratio.
	 * (total claim - passenger convenience) / boarded (1000)
	 */
	public double getRatio() {
		if (boarded > 0) {
			return Double.parseDouble(TracerUtils.format((getTotal() - getLoss81()) / boarded,"%"));
		} else {
			return 0;
		}
	}
	/**
	 * @param ratio The ratio to set.
	 */
	public void setRatio(double ratio) {
		this.ratio = ratio;
	}
	/**
	 * @return Returns the stationcode.
	 */
	public String getStationcode() {
		return stationcode;
	}
	/**
	 * @param stationcode The stationcode to set.
	 */
	public void setStationcode(String stationcode) {
		this.stationcode = stationcode;
	}
	/**
	 * @return Returns the total.
	 */
	public long getTotal() {
		return loss10 + loss12 + loss15 + loss18 + loss23 + loss24 + loss25
				+ loss26 + loss30 + loss32 + loss33 + loss35 + loss42 + loss51
				+ loss54 + loss56 + loss63 + loss64 + loss73 + loss74 + loss76
				+ loss78 + loss80 + loss81 + loss82 + loss90;
	}
	/**
	 * @param total The total to set.
	 */
	public void setTotal(long total) {
		this.total = total;
	}

	
	/**
	 * @return Returns the goal_times_boarded.
	 */
	public double getGoal_times_boarded() {
		return getGoal() * getBoarded();
	}
	/**
	 * @param goal_times_boarded The goal_times_boarded to set.
	 */
	public void setGoal_times_boarded(double goal_times_boarded) {
		this.goal_times_boarded = goal_times_boarded;
	}
	/**
	 * @return Returns the boarded.
	 */
	public double getBoarded() {
		return boarded;
	}
	/**
	 * @param boarded The boarded to set.
	 */
	public void setBoarded(double boarded) {
		this.boarded = boarded;
	}
	public String getCompanycode() {
		return companycode;
	}
	public void setCompanycode(String companycode) {
		this.companycode = companycode;
	}
	
	
}