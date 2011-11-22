package com.bagnet.nettracer.tracing.forms;

import java.util.List;

import org.apache.struts.validator.ValidatorForm;

import com.bagnet.nettracer.tracing.db.Region;
import com.bagnet.nettracer.tracing.db.Station;

public class RegionForm extends ValidatorForm{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8919522002118322990L;
	public List<Region> getRegions() {
		return regions;
	}
	public void setRegions(List<Region> regions) {
		this.regions = regions;
	}
	public Region getEditRegion() {
		return editRegion;
	}
	public void setEditRegion(Region editRegion) {
		this.editRegion = editRegion;
	}
	List<Region> regions;

	Region editRegion;
	long key;
	public long getKey() {
		return key;
	}
	public void setKey(long key) {
		this.key = key;
	}
	
	List<Station>stations;
	public List<Station> getStations() {
		return stations;
	}
	public void setStations(List<Station> stations) {
		this.stations = stations;
	}
	
	public Station getStation(int i){
		for(Station station:stations){
			if(station.getStation_ID() == i){
				return station;
			}
		}
		return null;
	}
}
