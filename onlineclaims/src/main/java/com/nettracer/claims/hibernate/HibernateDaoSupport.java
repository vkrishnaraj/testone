package com.nettracer.claims.hibernate;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class HibernateDaoSupport extends org.springframework.orm.hibernate3.support.HibernateDaoSupport {
	@Autowired
	public void setAutoSessionFactory(SessionFactory autoSessionFactory) {
		setSessionFactory(autoSessionFactory);
	}
}
