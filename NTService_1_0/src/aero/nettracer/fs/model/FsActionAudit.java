package aero.nettracer.fs.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Proxy;


@Entity
@Proxy(lazy = false)
public class FsActionAudit {
	@Id
	@GeneratedValue
	long id;
	
	Date actiondate;
	String action;
	long file_id;
	String companycode_id;
	
	@OneToMany(mappedBy = "action", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@org.hibernate.annotations.OrderBy(clause = "id")
	@Fetch(FetchMode.SELECT)
	Set<FsMatchHistoryAudit> matchhistory;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getActiondate() {
		return actiondate;
	}
	public void setActiondate(Date actiondate) {
		this.actiondate = actiondate;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public long getFile_id() {
		return file_id;
	}
	public void setFile_id(long file_id) {
		this.file_id = file_id;
	}
	
	public Set<FsMatchHistoryAudit> getMatchdetails() {
		return matchhistory;
	}
	public void setMatchdetails(Set<FsMatchHistoryAudit> matchhistory) {
		this.matchhistory = matchhistory;
	}
	
	public String getCompanycode_id() {
		return companycode_id;
	}
	public void setCompanycode_id(String companycode_id) {
		this.companycode_id = companycode_id;
	}
}
