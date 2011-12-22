package com.bagnet.nettracer.tracing.db.lf;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.utils.DateUtils;

@Entity
@Table(name="lflossinfo")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class LFLossInfo {
	@Id
	@GeneratedValue
	private long id;
	
	private Date lossdate;

	@ManyToOne
	@JoinColumn(name = "origin_station_ID", nullable = true)
	private Station origin;
	
	@ManyToOne
	@JoinColumn(name = "destination_station_ID", nullable = true)
	private Station destination;
	
	private String agreementNumber;
	
	private String mvaNumber;

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}
	
	public Date getLossdate() {
		return lossdate;
	}

	public void setLossdate(Date lossdate) {
		this.lossdate = lossdate;
	}


	public void setDisLossdate(String date, String format){
		setLossdate(DateUtils.convertToDate(date, format, null));
	}
	
	public String getDisLossdate(String format){
		return DateUtils.formatDate(getLossdate(), format, "", null);
	}
	
	public Station getOrigin() {
		return origin;
	}

	public void setOrigin(Station origin) {
		this.origin = origin;
	}

	public Station getDestination() {
		return destination;
	}

	public void setDestination(Station destination) {
		this.destination = destination;
	}
	
	public int getOriginId() {
		return getOrigin().getStation_ID();
	}
	
	public void setOriginId(int originId) {
		getOrigin().setStation_ID(originId);
	}
	
	public int getDestinationId() {
		return getDestination().getStation_ID();
	}
	
	public void setDestinationId(int destinationId) {
		getDestination().setStation_ID(destinationId);
	}
	
	public String getAgreementNumber() {
		return agreementNumber;
	}

	public void setAgreementNumber(String agreementNumber) {
		this.agreementNumber = agreementNumber;
	}

	public String getMvaNumber() {
		return mvaNumber;
	}

	public void setMvaNumber(String mvaNumber) {
		this.mvaNumber = mvaNumber;
	}
	
}
