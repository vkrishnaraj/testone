package aero.nettracer.fs.model.detection;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Proxy;

import com.bagnet.nettracer.tracing.utils.DateUtils;

import aero.nettracer.fs.model.File;
import aero.nettracer.fs.model.messaging.FsMessage;

@Entity
@Proxy(lazy = false)
public class AccessRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	public static enum RequestStatus {
		Created, Approved, Denied
	}

	@Id
	@GeneratedValue
	private long id;

	@OneToOne(fetch = FetchType.EAGER)
	private File file;

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	private MatchHistory matchHistory;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private RequestStatus status;

	@OneToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	private FsMessage message;

	@Temporal(TemporalType.TIMESTAMP)
	private Date requestedDate;

	private String requestedAgent;
	//private String contact;
	
	private String contactName;
	private String contactEmail;
	private String contactPhone;
	
	private String requestedAirline;

	public String getRequestedAirline() {
		return requestedAirline;
	}

	public void setRequestedAirline(String requestedAirline) {
		this.requestedAirline = requestedAirline;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public MatchHistory getMatchHistory() {
		return matchHistory;
	}

	public void setMatchHistory(MatchHistory matchHistory) {
		this.matchHistory = matchHistory;
	}

	public RequestStatus getStatus() {
		return status;
	}

	public void setStatus(RequestStatus status) {
		this.status = status;
	}

	public FsMessage getMessage() {
		return message;
	}

	public void setMessage(FsMessage message) {
		this.message = message;
	}

	public Date getRequestedDate() {
		return requestedDate;
	}

	public void setRequestedDate(Date requestedDate) {
		this.requestedDate = requestedDate;
	}

	public String getRequestedAgent() {
		return requestedAgent;
	}

	public void setRequestedAgent(String requestedAgent) {
		this.requestedAgent = requestedAgent;
	}
	
//	public String getContact() {
//		return contact;
//	}
//
//	public void setContact(String contact) {
//		this.contact = contact;
//	}

	public Date getResponseDate() {
		return responseDate;
	}

	public void setResponseDate(Date responseDate) {
		this.responseDate = responseDate;
	}

	public String getResponseAgent() {
		return responseAgent;
	}

	public void setResponseAgent(String responseAgent) {
		this.responseAgent = responseAgent;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private Date responseDate;
	private String responseAgent;
	
	@Transient
	public String getDisRequestedDate(String dateFormat) {
		return DateUtils.formatDate(requestedDate, dateFormat, "", null);
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactName() {
		return contactName;
	}

}
