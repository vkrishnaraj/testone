package aero.nettracer.fs.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Proxy;

import com.bagnet.nettracer.tracing.utils.TracerUtils;

import aero.nettracer.fs.model.detection.AddressWhiteList;

@Entity
@Proxy(lazy = false)
public class FsAddress implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private long id;

	@ManyToOne(targetEntity = aero.nettracer.fs.model.Person.class)
	@Fetch(FetchMode.SELECT)
	private Person person;

	@ManyToOne(targetEntity = aero.nettracer.fs.model.Reservation.class)
	@Fetch(FetchMode.SELECT)
	private Reservation reservation;

	@OneToOne(targetEntity = aero.nettracer.fs.model.FsReceipt.class)
	@Fetch(FetchMode.SELECT)
	private FsReceipt receipt;

	private String address1;
	private String address2;
	private String city;
	private String state;
	private String province;
	private String zip;
	private String country;
	private double lattitude;
	private double longitude;
	private int geocodeType;

	@ManyToOne(targetEntity = aero.nettracer.fs.model.detection.AddressWhiteList.class)
	@Fetch(FetchMode.SELECT)
	private AddressWhiteList whitelist;

	private String normAddress;

	public int getGeocodeType() {
		return geocodeType;
	}

	public void setGeocodeType(int geocodeType) {
		this.geocodeType = geocodeType;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Reservation getReservation() {
		return reservation;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCountryName() {
		if (country != null && country.length() > 0) {
			return TracerUtils.getCountry(country).getCountry();
		}
		return "";
	}

	public double getLattitude() {
		return lattitude;
	}

	public void setLattitude(double lattitude) {
		this.lattitude = lattitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public FsReceipt getReceipt() {
		return receipt;
	}

	public void setReceipt(FsReceipt receipt) {
		this.receipt = receipt;
	}

	public void setNormAddress(String normAddress) {
		this.normAddress = normAddress;
	}

	public String getNormAddress() {
		return normAddress;
	}

	public void setWhitelist(AddressWhiteList whitelist) {
		this.whitelist = whitelist;
	}

	public AddressWhiteList getWhitelist() {
		return whitelist;
	}

	public boolean isEmpty() {
		boolean empty = true;
		if ((address1 != null && !address1.isEmpty())
				|| (address2 != null && !address2.isEmpty())
				|| (city != null && !city.isEmpty())
				|| (state != null && !state.isEmpty())
				|| (province != null && !province.isEmpty())
				|| (zip != null && !zip.isEmpty())) {
			empty = false;
		}

		return empty;
	}

}
