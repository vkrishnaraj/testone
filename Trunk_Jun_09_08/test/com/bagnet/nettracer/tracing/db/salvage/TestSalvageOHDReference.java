package com.bagnet.nettracer.tracing.db.salvage;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bagnet.nettracer.hibernate.HibernateWrapper;


public class TestSalvageOHDReference {

	private static final String OHD_ID = "YOWWS00102894";
	
	private static Session session;
	private static Transaction transaction;
	private static Salvage testSalvage;
	private static SalvageOHDReference testSalvageOHDReference;
	
	@BeforeClass
	public static void oneTimeSetUp() {
		session = HibernateWrapper.getSession().openSession();
		transaction = session.beginTransaction();
		
		testSalvage = new Salvage();
		session.save(testSalvage);
		
		testSalvageOHDReference = new SalvageOHDReference();
		testSalvageOHDReference.setSalvage(testSalvage);
		testSalvageOHDReference.setOhdId(OHD_ID);
	}
	
	@AfterClass
	public static void oneTimeTearDown() {
		transaction.rollback();
		session.close();
	}
	
	@Test
	public void testSaveSalvageOHDReference() {
		assertNotNull(session.save(testSalvageOHDReference));
		assertFalse(testSalvageOHDReference.getOhdRefId() == 0);
	}
	
	@Test
	public void testLoadSalvageOHDReference() {
		assertNotNull(getSalvageOHDReferenceFromDb());
	}
	
	@Test
	public void testSalvageOHDReferenceOhdRefId() {
		assertEquals(getSalvageOHDReferenceFromDb().getOhdRefId(), testSalvageOHDReference.getOhdRefId());
	}
	
	@Test
	public void testSalvageOHDReferenceOhdId() {
		assertTrue(getSalvageOHDReferenceFromDb().getOhdId().equals(testSalvageOHDReference.getOhdId()));
	}
	
	@Test
	public void testSalvageOHDReferenceSalvage() {
		assertTrue(getSalvageOHDReferenceFromDb().getSalvage().equals(testSalvage));
	}
	
	private SalvageOHDReference getSalvageOHDReferenceFromDb() {
		return (SalvageOHDReference) session.load(SalvageOHDReference.class, testSalvageOHDReference.getOhdRefId());
	}
	
}
