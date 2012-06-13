package aero.nettracer.fs.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Proxy;

@Entity
@Proxy(lazy = false)
public class PnrData implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private long id;
	@OneToOne(targetEntity = aero.nettracer.fs.model.Reservation.class)
	@Fetch(FetchMode.SELECT)
	private Reservation reservation;
	private String recordLocator;
	private String airline;
	@Column(columnDefinition = "TEXT")
	private String pnrData;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Reservation getReservation() {
		return reservation;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

	public String getRecordLocator() {
		return recordLocator;
	}

	public void setRecordLocator(String recordLocator) {
		this.recordLocator = recordLocator;
	}

	public String getAirline() {
		return airline;
	}

	public void setAirline(String airline) {
		this.airline = airline;
	}

	public String getPnrData() {
		return pnrData;
	}

	public void setPnrData(String pnrData) {
		this.pnrData = pnrData;
	}
}
