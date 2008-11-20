package com.bagnet.nettracer.tracing.db.datafeed;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.bagnet.nettracer.tracing.db.Company;

@NamedQueries({
	@NamedQuery(
	name = "alreadyProcessedByType",
	query = "from DataFeedLog where fileDate >= :fileDate and dataType = :feedType and companyCode = :companycode_ID"
	)
	})
@Entity
@Table(name="z_ws_data_feed_log")
public class DataFeedLog implements Serializable {
	
	public static final String ALREADY_PROCESSED_BY_TYPE = "alreadyProcessedByType";
	
	public static final String FAIL = "FAIL";
	public static final String SUCCESS = "SUCCESS";
	
	public enum DataType {
		FLIGHT_INFO,
	}
	
	@Id @GeneratedValue
	private long id;
	
	@Column(name="data_type", length=20)
	@Enumerated(EnumType.STRING)
	private DataType dataType;
	
	@Column(name="import_date")
	private Date importDate;

	@Column(name="file_name", length=40)
	private String fileName;
	
	@Column(name="num_rows")
	private long numRows;
	
	@Column(name="flight_date")
	private Date fileDate;
	
	@Column(name="companycode_id")
	private String companyCode;
	
	@Column(name="result", length=10)
	private String result;

	public long getId() {
		return id;
	}

	public DataType getDataType() {
		return dataType;
	}

	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}

	public Date getImportDate() {
		return importDate;
	}

	public void setImportDate(Date importDate) {
		this.importDate = importDate;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public long getNumRows() {
		return numRows;
	}

	public void setNumRows(long numRows) {
		this.numRows = numRows;
	}

	public Date getFileDate() {
		return fileDate;
	}

	public void setFileDate(Date fileDate) {
		this.fileDate = fileDate;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String company) {
		this.companyCode = company;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	

	
}
