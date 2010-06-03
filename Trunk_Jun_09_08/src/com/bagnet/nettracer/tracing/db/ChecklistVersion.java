package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.IndexColumn;

import com.bagnet.nettracer.tracing.db.i8n.LocaleBasedObject;

//for auto checklist feature

@Entity
@Table(name = "CHECKLIST_VERSION")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class ChecklistVersion extends LocaleBasedObject implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private long version_id;
	private boolean active;
	private List<ChecklistTask> checklistTasks;

	@Id
	@GeneratedValue
	public long getVersion_id() {
		return version_id;
	}


	public void setVersion_id(long version_id) {
		this.version_id = version_id;
	}

	@Column(nullable = false)
	public boolean isActive() {
		return active;
	}


	public void setActive(boolean active) {
		this.active = active;
	}

	@OneToMany(mappedBy="checklistVersion", 
            targetEntity=com.bagnet.nettracer.tracing.db.ChecklistTask.class, 
             fetch=FetchType.EAGER,
              cascade=CascadeType.ALL)
    @IndexColumn(name="order_id")
    @Fetch(FetchMode.SELECT)
	public List<ChecklistTask> getChecklistTasks() {
		return checklistTasks;
	}

	public void setChecklistTasks(List<ChecklistTask> checklistTasks) {
		this.checklistTasks = checklistTasks;
	}
	
	@Override
	@Transient
	public String getKey() {
		return "" + serialVersionUID;
	}
}
