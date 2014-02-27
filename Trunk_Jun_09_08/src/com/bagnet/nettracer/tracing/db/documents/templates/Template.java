package com.bagnet.nettracer.tracing.db.documents.templates;

import java.util.Date;
import java.util.Set;

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

import com.bagnet.nettracer.tracing.enums.TemplateType;

@Entity
@Table(name="template")
public class Template {
	
	@Id
	@GeneratedValue
	private long id;
	
	@Column(length = 256)
	private String name;
	
	@Column(length = 256)
	private String description;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "template_type_mapping",
			   joinColumns = {@JoinColumn(name = "templateId")},
			   inverseJoinColumns = {@JoinColumn(name = "templateTypeId")})
	private Set<TemplateTypeMapping> types;
	
	private boolean active;
	
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date createDate;
	
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date lastUpdated;
	
	@Type(type = "text")
	private String data;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "template_var_mapping",
			   joinColumns = {@JoinColumn(name = "templateId")},
			   inverseJoinColumns = {@JoinColumn(name = "templateVarId")})
	private Set<TemplateVar> variables;
	
	private int typeAvailableFor;
	
	@Column(length = 5)
	private String marginTop;

	@Column(length = 5)
	private String marginBottom;

	@Column(length = 5)
	private String marginLeft;

	@Column(length = 5)
	private String marginRight;

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

	public Set<TemplateTypeMapping> getTypes() {
		return types;
	}

	public void setTypes(Set<TemplateTypeMapping> types) {
		this.types = types;
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

	public Set<TemplateVar> getVariables() {
		return variables;
	}

	public void setVariables(Set<TemplateVar> variables) {
		this.variables = variables;
	}
	
	public int getTypeAvailableFor() {
		return typeAvailableFor;
	}

	public void setTypeAvailableFor(int typeAvailableFor) {
		this.typeAvailableFor = typeAvailableFor;
	}

	public String getMarginTop() {
		return marginTop;
	}

	public void setMarginTop(String marginTop) {
		this.marginTop = marginTop;
	}

	public String getMarginBottom() {
		return marginBottom;
	}

	public void setMarginBottom(String marginBottom) {
		this.marginBottom = marginBottom;
	}

	public String getMarginLeft() {
		return marginLeft;
	}

	public void setMarginLeft(String marginLeft) {
		this.marginLeft = marginLeft;
	}

	public String getMarginRight() {
		return marginRight;
	}

	public void setMarginRight(String marginRight) {
		this.marginRight = marginRight;
	}

	public boolean isValid() {
		for (TemplateTypeMapping mapping: types) {
			if (TemplateType.INVALID == TemplateType.fromOrdinal(mapping.getOrdinal())) {
				return false;
			}
		}
		return true;
	}

}
