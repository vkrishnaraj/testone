package com.bagnet.nettracer.tracing.db.audit;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Proxy;

import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.BagDrop;
import com.bagnet.nettracer.tracing.utils.DateUtils;


@Entity
@Proxy(lazy = false)
@Table(name="audit_bagdrop")
public class Audit_BagDrop {
	@Id
	@GeneratedValue
	private long id;
	@ManyToOne
	@JoinColumn(name = "bagdrop_id", nullable = false)
	private BagDrop bagdrop;
	private Date entryDate;
	private int entryMethod;
	@ManyToOne
	@JoinColumn(name = "modifyAgent_ID", nullable = false)
	private Agent modifyAgent;
	private Date bagDropTime;
	private Date schArrivalDate;
	private Date actArrivalDate;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public BagDrop getBagdrop() {
		return bagdrop;
	}
	public void setBagdrop(BagDrop bagdrop) {
		this.bagdrop = bagdrop;
	}
	public Date getEntryDate() {
		return entryDate;
	}
	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}
	public int getEntryMethod() {
		return entryMethod;
	}
	public void setEntryMethod(int entryMethod) {
		this.entryMethod = entryMethod;
	}
	public Agent getModifyAgent() {
		return modifyAgent;
	}
	public void setModifyAgent(Agent modifyAgent) {
		this.modifyAgent = modifyAgent;
	}
	public Date getBagDropTime() {
		return bagDropTime;
	}
	public void setBagDropTime(Date bagDropTime) {
		this.bagDropTime = bagDropTime;
	}
	public Date getSchArrivalDate() {
		return schArrivalDate;
	}
	public void setSchArrivalDate(Date schArrivalDate) {
		this.schArrivalDate = schArrivalDate;
	}
	public Date getActArrivalDate() {
		return actArrivalDate;
	}
	public void setActArrivalDate(Date actArrivalDate) {
		this.actArrivalDate = actArrivalDate;
	}
	
	/**The following are for display purposes**/
	@Transient
	private String _DATEFORMAT;
	@Transient
	private String _TIMEFORMAT;
	@Transient
	private java.util.TimeZone _TIMEZONE;
	@Transient
	private String dispEntryMethod;
	
	public String get_DATEFORMAT() {
		return _DATEFORMAT;
	}
	public void set_DATEFORMAT(String _DATEFORMAT) {
		this._DATEFORMAT = _DATEFORMAT;
	}
	public String get_TIMEFORMAT() {
		return _TIMEFORMAT;
	}
	public void set_TIMEFORMAT(String _TIMEFORMAT) {
		this._TIMEFORMAT = _TIMEFORMAT;
	}
	public java.util.TimeZone get_TIMEZONE() {
		return _TIMEZONE;
	}
	public void set_TIMEZONE(java.util.TimeZone _TIMEZONE) {
		this._TIMEZONE = _TIMEZONE;
	}
	public String getDispEntryMethod() {
		return dispEntryMethod!=null?dispEntryMethod:"";
	}
	public void setDispEntryMethod(String dispEntryMethod) {
		this.dispEntryMethod = dispEntryMethod;
	}
	
	public String getDispSchArrivalDate() {
		String s = DateUtils.formatDate(getSchArrivalDate(), _DATEFORMAT + " " + _TIMEFORMAT, null, _TIMEZONE);
		return s!=null?s:"";
	}
	
	public String getDispActArrivalDate() {
		String s = DateUtils.formatDate(getActArrivalDate(), _DATEFORMAT + " " + _TIMEFORMAT, null, _TIMEZONE);
		return s!=null?s:"";
	}
	
	public String getDispBagDropTime() {
		String s = DateUtils.formatDate(getBagDropTime(), _DATEFORMAT + " " + _TIMEFORMAT, null, _TIMEZONE);
		return s!=null?s:"";
	}
	
	public String getDispEntryDate(){
		String s = DateUtils.formatDate(getEntryDate(), _DATEFORMAT + " " + _TIMEFORMAT, null, _TIMEZONE);
		return s!=null?s:"";
	}

}
