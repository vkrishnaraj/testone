package aero.nettracer.fs.model.transport.v2;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Proxy;

@Entity
@Proxy(lazy = false)
public class FsReceipt implements Serializable {

	private static final long serialVersionUID = -3328176366384823064L;

	@Id
	@GeneratedValue
	private long id;
	private String company;
	@Column(length = 2)
	private String ccType;
	@Column(length = 4)
	private String ccLastFour;
	@Column(length = 2)
	private int ccExpMonth;
	@Column(length = 2)
	private int ccExpYear;
	
	@ManyToOne(targetEntity = aero.nettracer.fs.model.FsClaim.class)
	@Fetch(FetchMode.SELECT)
	private FsClaim claim;
	
	@OneToOne(mappedBy = "receipt", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Cascade(value = org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@Fetch(FetchMode.SELECT)
	private FsAddress address;
	
	@OneToOne(mappedBy = "receipt", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Cascade(value = org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@Fetch(FetchMode.SELECT)
	private Phone phone;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}
	
	public String getCcType() {
		return ccType;
	}

	public void setCcType(String ccType) {
		this.ccType = ccType;
	}

	public String getCcLastFour() {
		return ccLastFour;
	}

	public void setCcLastFour(String ccLastFour) {
		this.ccLastFour = ccLastFour;
	}

	public int getCcExpMonth() {
		return ccExpMonth;
	}

	public void setCcExpMonth(int ccExpMonth) {
		this.ccExpMonth = ccExpMonth;
	}

	public int getCcExpYear() {
		return ccExpYear;
	}

	public void setCcExpYear(int ccExpYear) {
		this.ccExpYear = ccExpYear;
	}

	public FsAddress getAddress() {
		return address;
	}

	public void setAddress(FsAddress address) {
		this.address = address;
	}

	public Phone getPhone() {
		return phone;
	}

	public void setPhone(Phone phone) {
		this.phone = phone;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public FsClaim getClaim() {
		return claim;
	}

	public void setClaim(FsClaim claim) {
		this.claim = claim;
	}
	
}
