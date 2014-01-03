package com.bagnet.nettracer.tracing.db;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

@Entity
@Table(name = "UsergroupNameMap")
@Proxy(lazy = false)
public class UsergroupNameMap {

	private long id;
	private String ldapName;
	private String ntName;
	private int ntGroupId;

	@Id @GeneratedValue
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLdapName() {
		return ldapName;
	}

	public void setLdapName(String ldapName) {
		this.ldapName = ldapName;
	}

	public String getNtName() {
		return ntName;
	}

	public void setNtName(String ntName) {
		this.ntName = ntName;
	}

	public int getNtGroupId() {
		return ntGroupId;
	}

	public void setNtGroupId(int ntGroupId) {
		this.ntGroupId = ntGroupId;
	}


}
