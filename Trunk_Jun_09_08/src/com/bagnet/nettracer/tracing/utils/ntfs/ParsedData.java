package com.bagnet.nettracer.tracing.utils.ntfs;

import java.util.ArrayList;

public class ParsedData {
	public String fop;
	public double ticketPrice;
	public String cardType;
	public String last4;
	public String expiration;
	public ArrayList<Name> names = new ArrayList<Name>();
//	public String add1;
	public String add2;
	public String city;
	public String state;
	public String zip;
	public ArrayList<String> phones = new ArrayList<String>();
	public ArrayList<String> emails = new ArrayList<String>();

	public void print() {
		System.out.println("** " + fop + " " + ticketPrice + " " + cardType + " " + last4 + " " + expiration + "  Total Fare: ");
//		System.out.println("  " + add1);
		System.out.println("  " + add2);
		System.out.println("  " + city + " " + state + " " + zip);
		for (Name name : names) {
			System.out.println("      " + name.firstName + " " + name.lastName + "     " + name.cc);
		}
		
		for (String e : emails) {
			System.out.println("      " + e);
		}
	}
}