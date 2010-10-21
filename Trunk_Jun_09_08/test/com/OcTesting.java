package com;

import java.util.List;

import org.junit.Test;

import com.bagnet.nettracer.tracing.dao.OnlineClaimsDao;
import com.bagnet.nettracer.tracing.db.onlineclaims.OnlineClaim;

public class OcTesting {

	@Test
	public void testOc1() {
		OnlineClaimsDao d = new OnlineClaimsDao();
		int c = d.getReviewCount(2, 119);
		System.out.println(c);
	}

	@Test
	public void testOc2() {
		OnlineClaimsDao d = new OnlineClaimsDao();
//		List<OnlineClaim> r = d.getClaimsList("REVIEW", 15, 0, 0, 0);
//		System.out.println(r);
	}

	
}
