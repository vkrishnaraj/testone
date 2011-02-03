package com.bagnet.clients;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.bagnet.clients.us.RevenueCodesUtilImpl;

public class TestRevenueCodesUtil {

	private String[] codes = {"SA1P", "SA2P", "SA3P", "SA4P", "SA5P", "SA6P", "SA6O", "SA7P", "SA8P", "SA9P", "PS2B", "PC2B", "PS3B", "PS3Y"};
	private String pnrHeader = "PNR Content\nCV3H4Q  -ETKT-\n";
//	private String pnrValidCode = " 1.SA3P900212/1MC-THRASHER/ERINE  2.SA3P900212/1MC'THRASHER/KAYLAM\n 3.SA3P900212/1MC'THRASHER/IANM\n 3 US4006Y 02JAN SU CVGPHL MM3   722A  900A              \nP CVG/OUT    900A L01:38\n 4 US3199Y 02JAN SU PHLDCA MM3   210P  310P\nP PWN/OUT   1208P L00:41\nP PHL/OUT    303P L00:53\n";
	private String pnrValidCode = " 1.SA3P900212/1MC%THRASHER/  2.SA3P900212/1MC$THRASHER/\n 3.SA3P900212/1MC'THRASHER/\n 3 US4006Y 02JAN SU CVGPHL MM3   722A  900A              \nP CVG/OUT    900A L01:38\n 4 US3199Y 02JAN SU PHLDCA MM3   210P  310P\nP PWN/OUT   1208P L00:41\nP PHL/OUT    303P L00:53\n";
//	private String pnrInvalidCode = " 1.SXXP900212/1THRASHER/ERINE  2.SA00900212/1THRASHER/KAYLAM\n";
	private String pnrPhone = "FONE-\n1.DCA288-6700/EH540\n1.DCA*4THRASHER(AT)VERIZON.NET/N000\n3.DCA288-6700/H540\n4.DCA288-6700/H540\nTKT-T/FQR/MS041876US02100/24143852085/0001//.00";
	private String name = "MC%THRASHER";
		
	@Test
	public void testGetInstance() {
		assertNotNull(RevenueCodesUtilImpl.getInstance());
	}
	
	@Test
	public void testIsNonRevenueCodeNull() {
		assertFalse(RevenueCodesUtilImpl.getInstance().isNonRevenueCode(null));
	}
	
	@Test
	public void testIsNonRevenueCodeValid() {
		for (String candidate: codes) {
			assertTrue(RevenueCodesUtilImpl.getInstance().isNonRevenueCode(candidate));
		}
	}
	
	@Test
	public void testIsNonRevenueCodeInvalid() {
		assertFalse(RevenueCodesUtilImpl.getInstance().isNonRevenueCode("SXXP"));
	}
	
	@Test
	public void testGetRevenueCodeFromPnrNullPnr() {
		assertNull(RevenueCodesUtilImpl.getInstance().getNonRevenueCodeFromPnr(null, "xxx"));
	}

	@Test
	public void testGetRevenueCodeFromPnrNullSearchKey() {
		assertNull(RevenueCodesUtilImpl.getInstance().getNonRevenueCodeFromPnr("xxx", null));
	}
	
	@Test
	public void testGetRevenueCodeFromPnrValid() {
		assertNotNull(RevenueCodesUtilImpl.getInstance().getNonRevenueCodeFromPnr(pnrValidCode, name));
	}
	
//	@Test
//	public void testContainsNonRevCode() {
//		String code = RevenueCodesUtilImpl.getInstance().getNonRevenueCodeFromPnr(pnrHeader + pnrValidCode, name);
//		assertEquals(true, RevenueCodesUtilImpl.getInstance().isNonRevenueCode(code));
//	}
//	
//	@Test
//	public void testContainsNoNonRevCode() {
//		String code = RevenueCodesUtilImpl.getInstance().getNonRevenueCodeFromPnr(pnrHeader + pnrInvalidCode, name);
//		assertEquals(false, RevenueCodesUtilImpl.getInstance().isNonRevenueCode(code));
//	}
//	
	@Test
	public void testContainsPhoneAfterRevCode() {
//		String code = RevenueCodesUtilImpl.getInstance().getNonRevenueCodeFromPnr(pnrHeader + pnrValidCode + pnrPhone, name);
		assertTrue(RevenueCodesUtilImpl.getInstance().getNonRevenueCodeFromPnr(pnrHeader + pnrValidCode + pnrPhone, name).equals("SA3P"));
	}
	
	@Test
	public void testContainsPhoneBeforeRevCode() {
//		String code = RevenueCodesUtilImpl.getInstance().getNonRevenueCodeFromPnr(pnrHeader + pnrPhone + pnrValidCode, name);
		assertTrue(RevenueCodesUtilImpl.getInstance().getNonRevenueCodeFromPnr(pnrHeader + pnrPhone + pnrValidCode, name).equals("SA3P"));
	}
	
	public void printPnr() {
		System.out.println(pnrHeader + pnrValidCode + pnrPhone);
	}
	
}
