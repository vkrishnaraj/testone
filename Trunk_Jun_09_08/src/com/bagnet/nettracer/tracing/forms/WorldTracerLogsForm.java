package com.bagnet.nettracer.tracing.forms;

import java.util.Date;

import org.apache.struts.validator.ValidatorForm;

import com.bagnet.nettracer.tracing.db.wtq.WorldTracerTransaction.Result;
import com.bagnet.nettracer.wt.svc.WorldTracerService.TxType;

public class WorldTracerLogsForm extends ValidatorForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5513984670868693920L;
	
	private String startDate = null;
	private String endDate = null;
	
	private String error = null;
	
	private String result = null;
	
	private String txType = null;
	
	private String incident_id = null;
	
	private String ohd_id = null;
	
	public WorldTracerLogsForm() {
		super();
	}

	
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getTxType() {
		return txType;
	}

	public void setTxType(String txType) {
		this.txType = txType;
	}

	public String getIncident_id() {
		return incident_id;
	}

	public void setIncident_id(String incident_id) {
		this.incident_id = incident_id;
	}

	public String getOhd_id() {
		return ohd_id;
	}

	public void setOhd_id(String ohd_id) {
		this.ohd_id = ohd_id;
	}
}
