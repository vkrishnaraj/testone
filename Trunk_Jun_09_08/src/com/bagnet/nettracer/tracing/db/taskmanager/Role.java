package com.bagnet.nettracer.tracing.db.taskmanager;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name="role")
public class Role {
	long role_id;

	public long getRole_id() {
		return role_id;
	}

	public void setRole_id(long roleId) {
		role_id = roleId;
	}
}
