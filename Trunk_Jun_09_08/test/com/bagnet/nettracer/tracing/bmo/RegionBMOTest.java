package com.bagnet.nettracer.tracing.bmo;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.bagnet.nettracer.tracing.db.Company;
import com.bagnet.nettracer.tracing.db.Region;
import com.bagnet.nettracer.tracing.db.Station;

public class RegionBMOTest {
//	@Test
	public void saveLoadTest(){
		Region r = new Region();
		Company c = new Company();
		c.setCompanyCode_ID("WS");
		r.setCompany(c);
		r.setDirector("Matt Loupas");
		r.setName("SouthWest");
		r.setTarget(1.85);
		long id = RegionBMO.saveRegion(r, null);
		assertTrue(id > -1);
		Region load = RegionBMO.getRegion(id);
		assertTrue("SouthWest".equals(load.getName()));
		load.setTarget(2.57);
		long id2 = RegionBMO.saveRegion(load, null);
		assertTrue(id2 == id);
		Region load2 = RegionBMO.getRegion(id2);
		assertTrue("2.57".equals(load2.getTarget()));
	}
	
//	@Test
	public void updateStations(){
		Station station = StationBMO.getStationByCode("YYC", "WS");
		Region r = new Region();
		r.setId(2);
		station.setRegion(r);
		List<Station>l=new ArrayList();
		l.add(station);
		RegionBMO.updateStationRegion(l, null);
	}
	
//	@Test
	public void resetStationRegions(){
		RegionBMO.resetStationRegion(2, null);
	}
	
//	@Test
	public void deleteStation(){
		assertTrue(RegionBMO.deleteRegion(1, null));
	}
	
//	@Test
	public void loadRegions(){
		List<Region> list = RegionBMO.getRegions("WS");
		for(Region r:list){
			System.out.println(r.getName());
		}
	}
}
