package com.bagnet.nettracer.tracing.db.wtq;



import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.wt.svc.WorldTracerService.TxType;

@Entity
@Table(name="wt_transaction")
@Proxy(lazy=false)
public class WorldTracerTransaction {
	
	public static final String UNKNOWN_ERROR = "Unknown Error";
	
	public enum Result {FAILURE("wt_txfailure"), SUCCESS("wt_txsuccess");
		private String messageKey;
		
		private Result(String messageKey) {
			this.messageKey = messageKey;
		}
		
		public String getMessageKey() {
			return messageKey;
		}
		
		public String getValue() {
			return this.name();
		}
	}

	private long id;
	
	private Date createDate;
	
	private long duration = -1;
	
	private Result result;
	
	private String error;	
	
	private TxType txType;

	private Incident incident;
	
	private OHD ohd;
	
	private String txInputData;

	private String txOutputData;
	
	private long start = -1;
	private long finish = -1;

	public WorldTracerTransaction() {
		super();
		this.createDate = TracerDateTime.getGMTDate();
	}
	
	public WorldTracerTransaction(TxType txType) {
		this();
		this.txType = txType;
	}

	@Id @GeneratedValue
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Basic
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Basic
	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	@Enumerated(EnumType.STRING)
	@Column(length=20)
	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	@Column(length=100)
	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	@ManyToOne(targetEntity = Incident.class)
	@JoinColumn(name = "incident_id")
	public Incident getIncident() {
		return incident;
	}

	public void setIncident(Incident incidentId) {
		this.incident = incidentId;
	}
	
	@ManyToOne(targetEntity = OHD.class)
	@JoinColumn(name = "ohd_id")
	public OHD getOhd() {
		return ohd;
	}

	public void setOhd(OHD ohd) {
		this.ohd = ohd;
	}


	@Column(length=1020)
	public String getTxInputData() {
		return this.txInputData;
	}
	
	public void setTxInputData(String txData) {
		this.txInputData = txData;
	}
	
	@Column(length=1020)
	public String getTxOutputData() {
		return txOutputData;
	}

	public void setTxOutputData(String txResult) {
		this.txOutputData = txResult;
	}
	

	@Enumerated(EnumType.STRING)
	@Column(nullable=false, length = 40)
	public TxType getTxType() {
		return txType;
	}

	public void setTxType(TxType txType) {
		this.txType = txType;
	}

	public void startTransaction() {
		this.start = System.currentTimeMillis();
	}
	
	public void endTransaction() {
		this.finish = System.currentTimeMillis();
		this.duration = finish - start;
	}

	public void successTransaction(String data) {
		this.result = Result.SUCCESS;
		this.setTxOutputData(data);
	}
	public void failTransaction(Throwable e) {
		String error = null;
		this.result = Result.FAILURE;
		if(e.getMessage() != null && e.getMessage().trim().length() > 0) {
			error = e.getClass().getName() + ": " + e.getMessage();
		}
		else {
			error = e.getClass().getName() + ": " + WorldTracerTransaction.UNKNOWN_ERROR;
		}
		if(e.getCause() != null) {
			error += "\nCause: " + e.getCause().getClass().getName() + " " + e.getCause().getMessage();
		}
		this.setError(error);
	}

}
