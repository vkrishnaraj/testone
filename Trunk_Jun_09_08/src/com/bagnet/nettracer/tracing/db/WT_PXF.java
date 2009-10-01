package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

import com.bagnet.nettracer.tracing.db.wtq.WorldTracerQueue;

@Entity
@Table(name = "wtq_pxf")
@Proxy(lazy = false)
public class WT_PXF implements Serializable {
	public WT_PXF() {
		// 
	}
	
	@Id 
	@GeneratedValue
	private long id;
	
	//for PXF options setting
	// 3=stations; 2=region; 1=all
	private int destination = 3;
	
	private WorldTracerQueue wtq;
	
	@OneToOne
	@JoinColumn(name = "wt_queue_id")
	@org.hibernate.annotations.Cascade({ org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.PERSIST })
	public WorldTracerQueue getWtq() {
		return wtq;
	}

	public void setWtq(WorldTracerQueue wtq) {
		this.wtq = wtq;
	}

	@Id
	@GeneratedValue
	@Column(name = "ID")
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getDestination() {
		return destination;
	}

	public void setDestination(int destination) {
		this.destination = destination;
	}

	public String getAirline_1() {
		return airline_1;
	}

	public void setAirline_1(String airline_1) {
		this.airline_1 = airline_1;
	}

	public String getAirline_2() {
		return airline_2;
	}

	public void setAirline_2(String airline_2) {
		this.airline_2 = airline_2;
	}

	public String getAirline_3() {
		return airline_3;
	}

	public void setAirline_3(String airline_3) {
		this.airline_3 = airline_3;
	}

	public String getAirline_4() {
		return airline_4;
	}

	public void setAirline_4(String airline_4) {
		this.airline_4 = airline_4;
	}

	public String getAirline_5() {
		return airline_5;
	}

	public void setAirline_5(String airline_5) {
		this.airline_5 = airline_5;
	}

	public String getArea_1() {
		return area_1;
	}

	public void setArea_1(String area_1) {
		this.area_1 = area_1;
	}

	public String getArea_2() {
		return area_2;
	}

	public void setArea_2(String area_2) {
		this.area_2 = area_2;
	}

	public String getArea_3() {
		return area_3;
	}

	public void setArea_3(String area_3) {
		this.area_3 = area_3;
	}

	public String getArea_4() {
		return area_4;
	}

	public void setArea_4(String area_4) {
		this.area_4 = area_4;
	}

	public String getArea_5() {
		return area_5;
	}

	public void setArea_5(String area_5) {
		this.area_5 = area_5;
	}
	@Column(length=3, name="station_1")
	public String getStation_1() {
		return station_1;
	}

	public void setStation_1(String value) {
		this.station_1 = value;
	}
	
	@Column(length=3, name="station_2")
	public String getStation_2() {
		return station_2;
	}

	public void setStation_2(String value) {
		this.station_2 = value;
	}
	
	@Column(length=3, name="station_3")
	public String getStation_3() {
		return station_3;
	}

	public void setStation_3(String value) {
		this.station_3 = value;
	}
	
	@Column(length=3, name="station_4")
	public String getStation_4() {
		return station_4;
	}

	public void setStation_4(String value) {
		this.station_4 = value;
	}
	
	@Column(length=3, name="station_5")
	public String getStation_5() {
		return station_5;
	}

	public void setStation_5(String value) {
		this.station_5 = value;
	}
	
	private String airline_1;
	private String airline_2;
	private String airline_3;
	private String airline_4;
	private String airline_5;

	private String station_1;
	private String station_2;
	private String station_3;
	private String station_4;
	private String station_5;

	private String area_1;
	private String area_2;
	private String area_3;
	private String area_4;
	private String area_5;

}
