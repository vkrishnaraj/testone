package com.bagnet.clients;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;

import com.bagnet.clients.us.ScannerDataSourceImpl;

public class USScannerTest {
	@Test
	public void testScanners() {
		ScannerDataSourceImpl s = new ScannerDataSourceImpl();
		
		ArrayList<Long> duration = new ArrayList();
		
		for (int i=0; i<10; ++i) {
			Date start = new Date();
			Date startDate = new Date();
			startDate.setDate(25);
			Date endDate = new Date();
			endDate.setDate(31);
			String bagTagNumber = "0037331065";
			try {
				s.getScannerData(startDate, endDate, bagTagNumber);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Date end = new Date();
			Long elapsed = (start.getTime() - end.getTime());
			System.out.println("Start: " + start + "  End: " + end + "  Elapsed Seconds: " + elapsed/1000);
			duration.add(elapsed);
		}
		
		double average = 0;
		int count = 0;
		for (long i: duration) {
			average += i;
			count += 1;
		}
		System.out.println("Average: " + average/count);
		
	}
}
