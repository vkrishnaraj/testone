package com.bagnet.nettracer.tracing.db.templates;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

@Entity
@Table(name="document_template")
public class DocumentTemplate {
	
	@Id
	@GeneratedValue
	private long id;
	
	@Column(length = 256)
	private String name;
	
	@Column(length = 256)
	private String description;
	
	private boolean active;
	
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date createDate;
	
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date lastUpdated;
	
	@Type(type = "text")
	private String data;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL} )
	@JoinTable(name = "document_template_var_mapping",
			   joinColumns = {@JoinColumn(name = "documentTemplateId")},
			   inverseJoinColumns = {@JoinColumn(name = "documentTemplateVarId")})
	private Set<DocumentTemplateVar> variables;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Set<DocumentTemplateVar> getVariables() {
		return variables;
	}

	public void setVariables(Set<DocumentTemplateVar> variables) {
		this.variables = variables;
	}

}
