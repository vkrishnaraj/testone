package com.bagnet.nettracer;


import java.util.List;

import org.junit.Test;

import com.bagnet.nettracer.tracing.db.Lz;
import com.bagnet.nettracer.tracing.utils.LzUtils;

public class TmpTest {
	

	@Test
	public void test() {
		List<Lz> lzList = (List<Lz>) LzUtils.getIncidentLzStations("US");
	}
	
}
