package com.bagnet.nettracer.tracing.db.salvage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.LinkedHashSet;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.constant.TracingConstants;


public class TestSalvageBox {
	
	private static final String DESCRIPTION = "Test Description";
	
	private static Session session;
	private static Transaction transaction;
	private static Salvage testSalvage;
	private static SalvageBox testSalvageBox;
	private static LinkedHashSet<SalvageItem> testItems;
	
	@BeforeClass
	public static void oneTimeSetUp() {
		session = HibernateWrapper.getSession().openSession();
		transaction = session.beginTransaction();
		
		testSalvage = new Salvage();
		session.save(testSalvage);
		
		testSalvageBox = new SalvageBox();
		testSalvageBox.setSalvage(testSalvage);
		testSalvageBox.setDisplayId(1);
		
		testItems = new LinkedHashSet<SalvageItem>();
		testItems.add(new SalvageItem());
	}
	
	@AfterClass
	public static void oneTimeTearDown() {
		transaction.rollback();
		session.close();
	}
	
	@Test
	public void testSaveSalvageBox() {
		assertNotNull(session.save(testSalvageBox));
		assertFalse(testSalvageBox.getBoxId() == 0);
	}
	
	@Test
	public void testLoadSalvageBox() {
		assertNotNull(getSalvageBoxFromDb());
	}
	
	@Test
	public void testSalvageBoxBoxId() {
		assertEquals(getSalvageBoxFromDb().getBoxId(), testSalvageBox.getBoxId());
	}
	
	@Test
	public void testSalvageBoxDisplayId() {
		assertEquals(getSalvageBoxFromDb().getDisplayId(), testSalvageBox.getDisplayId());
	}
	
	@Test
	public void testSalvageBoxType() {
		assertEquals(testSalvageBox.getType(), 0);
		testSalvageBox.setType(TracingConstants.SALVAGE_LOW_VALUE);
		session.save(testSalvageBox);
		assertEquals(getSalvageBoxFromDb().getType(), TracingConstants.SALVAGE_LOW_VALUE);
	}
	
	@Test
	public void testSalvageBoxDescription() {
		assertNull(testSalvageBox.getDescription());
		testSalvageBox.setDescription(DESCRIPTION);
		session.save(testSalvageBox);
		assertTrue(getSalvageBoxFromDb().getDescription().equals(DESCRIPTION));
	}
	
	@Test
	public void testSalvageBoxSalvage() {
		assertTrue(getSalvageBoxFromDb().getSalvage().equals(testSalvage));
	}
	
	@Test
	public void testSalvageBoxSalvageItems() {
		assertNull(testSalvageBox.getSalvageItems());
		testSalvageBox.setSalvageItems(testItems);
		session.save(testSalvageBox);
		assertTrue(getSalvageBoxFromDb().getSalvageItems().equals(testItems));
	}
	
	private SalvageBox getSalvageBoxFromDb() {
		return (SalvageBox) session.load(SalvageBox.class, testSalvageBox.getBoxId());
	}

}
