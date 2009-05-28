package com.bagnet.nettracer.cronjob.tracing;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.db.Airport;
import com.bagnet.nettracer.tracing.db.Incident;
import com.mysql.jdbc.Connection;

public class DirtyReadTest {
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	
	@Test
	public void testDirtyRead() throws SQLException {
		Session sess = HibernateWrapper.getSession().openSession();
		
		java.sql.Connection foo = sess.connection();
		System.out.println("Isolation level: " + foo.getTransactionIsolation());
		Query q = sess.createQuery("from Airport where id = :id");
		q.setParameter("id", 1);
		System.out.println(q.getQueryString());
		List<Airport> result = q.list();
		System.out.println(result.size());
	}
}
