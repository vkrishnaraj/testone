package com.bagnet.nettracer.tracing.db.salvage;

import static org.junit.Assert.*;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.constant.TracingConstants;


public class TestSalvageItem {
	
	private static final int QUANTITY = 10;
	private static final String DESCRIPTION = "Test Description";
	private static final String LOST_AND_FOUND_ID = "EWRWS00003531";
	
	private static Session session;
	private static Transaction transaction;	
	private static Salvage testSalvage;
	private static SalvageBox testSalvageBox;
	private static SalvageItem testSalvageItem;
	
	@BeforeClass
	public static void oneTimeSetUp() {
		session = HibernateWrapper.getSession().openSession();
		transaction = session.beginTransaction();

		testSalvage = new Salvage();
		session.save(testSalvage);

		testSalvageBox = new SalvageBox();
		testSalvageBox.setSalvage(testSalvage);
		testSalvageBox.setDisplayId(1);
		session.save(testSalvageBox);
		
		testSalvageItem = new SalvageItem();
		testSalvageItem.setLostAndFoundId(LOST_AND_FOUND_ID);
		testSalvageItem.setBox(testSalvageBox);
	}
	
	@AfterClass
	public static void oneTimeTearDown() {
		transaction.rollback();
		session.close();
	}
	
	@Test
	public void testSaveSalvageItem() {
		assertNotNull(session.save(testSalvageItem));
		assertFalse(testSalvageItem.getItemId() == 0);
	}
	
	@Test
	public void testLoadSalvageItem() {
		assertNotNull(getSalvageItemFromDb());
	}
	
	@Test
	public void testSalvageItemItemId() {
		assertEquals(getSalvageItemFromDb().getItemId(), testSalvageItem.getItemId());
	}
	
	@Test
	public void testSalvageItemType() {
		assertEquals(testSalvageItem.getType(), 0);
		testSalvageItem.setType(TracingConstants.SALVAGE_HIGH_VALUE);
		session.save(testSalvageItem);
		assertEquals(getSalvageItemFromDb().getType(), TracingConstants.SALVAGE_HIGH_VALUE);
	}
	
	@Test
	public void testSalvageItemDescription() {
		assertNull(testSalvageItem.getDescription());
		testSalvageItem.setDescription(DESCRIPTION);
		session.save(testSalvageItem);
		assertTrue(getSalvageItemFromDb().getDescription().equals(DESCRIPTION));
	}
	
	@Test
	public void testSalvageItemQuantity() {
		assertEquals(testSalvageItem.getQuantity(), 0);
		testSalvageItem.setQuantity(QUANTITY);
		session.save(testSalvageItem);
		assertEquals(getSalvageItemFromDb().getQuantity(), QUANTITY);
	}
	
	@Test
	public void testSalvageItemLostAndFoundId() {
		assertTrue(getSalvageItemFromDb().getLostAndFoundId().equals(LOST_AND_FOUND_ID));
	}
	
	@Test
	public void testSalvageItemSalvageBox() {
		assertTrue(getSalvageItemFromDb().getBox().equals(testSalvageBox));
	}
	
	private SalvageItem getSalvageItemFromDb() {
		return (SalvageItem) session.load(SalvageItem.class, testSalvageItem.getItemId());
	}	
	
}
