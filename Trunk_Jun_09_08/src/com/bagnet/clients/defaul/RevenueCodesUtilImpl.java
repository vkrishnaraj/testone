package com.bagnet.clients.defaul;


/**
 * 
 * @author Mike Sanders
 *
 */
public class RevenueCodesUtilImpl {

	private static RevenueCodesUtilImpl instance = null;
	
	private RevenueCodesUtilImpl() { }
	
	public static RevenueCodesUtilImpl getInstance() {
		if (instance == null) {
			instance = new RevenueCodesUtilImpl();
		}
		return instance;
	}
	
	public boolean isNonRevenueCode(String candidate) {
		return false;
	}
	
	public String getNonRevenueCodeFromPnr(String pnr, String searchKey) {
		return null;
	}

}
