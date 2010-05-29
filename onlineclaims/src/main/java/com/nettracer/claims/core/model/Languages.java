/**
 * 
 */
package com.nettracer.claims.core.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


/**
 * @author Utpal
 * 
 */
@Entity
@Table(name="languages", uniqueConstraints={@UniqueConstraint(columnNames={"languageName"})})
public class Languages  implements Serializable{

	private static final long serialVersionUID = 8917820665547161835L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String languageName;
	
	
	private String description;
	
	
	private Boolean activeStatus;
	
	
	@OneToMany(cascade=CascadeType.ALL,mappedBy="languages",fetch=FetchType.LAZY)
	private List<Localetext> localetexts= new ArrayList<Localetext>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLanguageName() {
		return languageName;
	}

	public void setLanguageName(String languageName) {
		this.languageName = languageName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(Boolean activeStatus) {
		this.activeStatus = activeStatus;
	}

	public List<Localetext> getLocaletexts() {
		return localetexts;
	}

	public void setLocaletexts(List<Localetext> localetexts) {
		this.localetexts = localetexts;
	}

	
	
	
}
