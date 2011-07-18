package aero.nettracer.fs.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Proxy;


@Entity
@Proxy(lazy = false)
public class FsMatchHistoryAudit {
	
	public FsMatchHistoryAudit(){
		super();
	}
	public FsMatchHistoryAudit(long matchhistory_id, String accessLevel){
		this();
		this.matchhistory_id = matchhistory_id;
		this.accessLevel = accessLevel;
	}
	
	@Id
	@GeneratedValue
	long id;
	
	
	long matchhistory_id;
	String accessLevel;
	
	@ManyToOne(targetEntity = aero.nettracer.fs.model.FsActionAudit.class)
	private FsActionAudit action;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getMatchhistory_id() {
		return matchhistory_id;
	}
	public void setMatchhistory_id(long matchhistory_id) {
		this.matchhistory_id = matchhistory_id;
	}
	public String getAccessLevel() {
		return accessLevel;
	}
	public void setAccessLevel(String accessLevel) {
		this.accessLevel = accessLevel;
	}
	public void setAction(FsActionAudit action) {
		this.action = action;
	}
	public FsActionAudit getAction() {
		return action;
	}
}
