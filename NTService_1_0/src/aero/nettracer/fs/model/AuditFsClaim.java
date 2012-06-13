package aero.nettracer.fs.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

@Entity
@Proxy(lazy = false)
@Table(name = "audit_fs_claim")
public class AuditFsClaim {

	@Id
	@GeneratedValue
	private long id;
	private Date accessTime;
	private int agentId;
	private	long itemId;
	private int itemType;
	private int actionType;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public Date getAccessTime() {
		return accessTime;
	}
	
	public void setAccessTime(Date accessTime) {
		this.accessTime = accessTime;
	}
	
	public int getAgentId() {
		return agentId;
	}
	
	public void setAgentId(int agentId) {
		this.agentId = agentId;
	}
	
	public long getItemId() {
		return itemId;
	}
	
	public void setItemId(long itemId) {
		this.itemId = itemId;
	}
	
	public int getItemType() {
		return itemType;
	}
	
	public void setItemType(int itemType) {
		this.itemType = itemType;
	}
	
	public int getActionType() {
		return actionType;
	}
	
	public void setActionType(int actionType) {
		this.actionType = actionType;
	}
	
}
