package com.bagnet.nettracer.tracing.db.salvage;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.util.Date;
import java.util.LinkedHashSet;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bagnet.nettracer.hibernate.HibernateWrapper;


public class TestSalvage {
	
	private static final String FIRST_NAME = "John";
	private static final String LAST_NAME = "Doe";
	
	private static Session session;
	private static Transaction transaction;
	private static Salvage testSalvage;
	private static LinkedHashSet<SalvageBox> testBoxes;
	private static LinkedHashSet<SalvageOHDReference> testOhdRefs;
	
	@BeforeClass
	public static void oneTimeSetUp() throws ParseException {
		session = HibernateWrapper.getSession().openSession();
		transaction = session.beginTransaction();
		
		testSalvage = new Salvage();
		
		testBoxes = new LinkedHashSet<SalvageBox>();
		testBoxes.add(new SalvageBox());
		
		testOhdRefs = new LinkedHashSet<SalvageOHDReference>();
		testOhdRefs.add(new SalvageOHDReference());
	}
	
	@AfterClass
	public static void oneTimeTearDown() {
		transaction.rollback();
		session.close();
	}
	
	@Test
	public void testSaveSalvage() {
		assertNotNull(session.save(testSalvage));
		assertFalse(testSalvage.getSalvageId() == 0);
	}
	
	@Test
	public void testLoadSalvageTestNotNull() {
		assertNotNull(getSalvageFromDb());
	}
	
	@Test
	public void testSalvageSalvageId() {
		assertEquals(getSalvageFromDb().getSalvageId(), testSalvage.getSalvageId());
	}
	
	@Test
	public void testSalvageCompanyCodeId() {
		assertNull(getSalvageFromDb().getCompanyCodeId());
		testSalvage.setCompanyCodeId("ws");
		session.save(testSalvage);
		assertTrue(getSalvageFromDb().getCompanyCodeId().equals("ws"));
	}
	

	@Test
	public void testSalvagePickedUpByFName() {
		assertNull(getSalvageFromDb().getPickedUpByFName());
		testSalvage.setPickedUpByFName(FIRST_NAME);
		session.save(testSalvage);
		assertTrue(getSalvageFromDb().getPickedUpByFName().equals(FIRST_NAME));
	}

	@Test
	public void testSalvagePickedUpByLName() {
		assertNull(getSalvageFromDb().getPickedUpByLName());
		testSalvage.setPickedUpByLName(LAST_NAME);
		session.save(testSalvage);
		assertTrue(getSalvageFromDb().getPickedUpByLName().equals(LAST_NAME));
	}
	
	@Test
	public void testSalvageSalvageDate() {
		assertNull(getSalvageFromDb().getSalvageDate());
		Date now = new Date();
		testSalvage.setSalvageDate(now);
		session.save(testSalvage);
		assertTrue(getSalvageFromDb().getSalvageDate().equals(now));
	}
	
	@Test
	public void testSalvageSalvageBoxes() {
		assertNull(getSalvageFromDb().getSalvageBoxes());
		testSalvage.setSalvageBoxes(testBoxes);
		session.save(testSalvage);
		assertTrue(getSalvageFromDb().getSalvageBoxes().equals(testBoxes));
	}

	@Test
	public void testSalvageOhdReferences() {
		assertNull(getSalvageFromDb().getOhdReferences());
		testSalvage.setOhdReferences(testOhdRefs);
		session.save(testSalvage);
		assertTrue(getSalvageFromDb().getOhdReferences().equals(testOhdRefs));
	}

	private Salvage getSalvageFromDb() {
		return (Salvage) session.get(Salvage.class, testSalvage.getSalvageId());
	}

}
