package com.bagnet.nettracer.tracing.db;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.apache.struts.util.MessageResources;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.IndexColumn;

//for auto checklist feature

@Entity
@Table(name = "CHECKLIST_TASK")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)

public class ChecklistTask {
	private long id;
	//private long version_id;
	private long order_id;
	private String description;  // to store resource key

	private ChecklistVersion checklistVersion;   // an task belongs to a task_version
	private List<ChecklistTaskOption> checklistTaskOptions;  //a task has options

	@Id
	@GeneratedValue
	@Column(name = "Task_ID")
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

/*	@Column(nullable = false)
	public long getVersion_id() {
		return version_id;
	}

	public void setVersion_id(long version_id) {
		this.version_id = version_id;
	}*/
	
	public long getOrder_id() {
		return order_id;
	}

	public void setOrder_id(long order_id) {
		this.order_id = order_id;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@ManyToOne
	@JoinColumn(name = "Version_ID")
	public ChecklistVersion getChecklistVersion() {
		return checklistVersion;
	}

	public void setChecklistVersion(ChecklistVersion checklistVersion) {
		this.checklistVersion = checklistVersion;
	}
	
	@OneToMany(mappedBy="checklistTask", 
            targetEntity=com.bagnet.nettracer.tracing.db.ChecklistTaskOption.class, 
             fetch=FetchType.EAGER,
              cascade=CascadeType.ALL)
    @IndexColumn(name="order_id")
    @Fetch(FetchMode.SELECT)
	public List<ChecklistTaskOption> getChecklistTaskOptions() {
		return checklistTaskOptions;
	}

	public void setChecklistTaskOptions(List<ChecklistTaskOption> checklistTaskOptions) {
		this.checklistTaskOptions = checklistTaskOptions;
	}
	
	private IncidentChecklist snapshotData;  // to get the state of the task
	
	@Transient
	public IncidentChecklist getSnapshotData() {
		return snapshotData;
	}
	public void setSnapshotData(IncidentChecklist snapshotData) {
		this.snapshotData = snapshotData;
	}
	
	private String dispName;  // to replace description with this key for internationalization
	
	@Transient
	public String getDispName() {
		return setDispNameByKey(description);
	}
	private String setDispNameByKey( String value ){  
		String result = null;
		MessageResources messages = MessageResources.getMessageResources("com.bagnet.nettracer.tracing.resources.ApplicationResources");  
		result = "" + messages.getMessage( value ); 
		
		return result;
	} 

}
