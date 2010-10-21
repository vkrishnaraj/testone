package com.bagnet.nettracer.wt.connector;


import org.junit.Test;
import com.bagnet.nettracer.wt.svc.WorldTracerService;
import com.bagnet.nettracer.wt.connector.WebServiceDto;
import com.bagnet.nettracer.wt.WorldTracerException;

import aero.nettracer.serviceprovider.wt_1_0.common.Ahl;


public class WorldTracerWebServiceTest {
	@Test
	public void test(){


		WorldTracerWebService wtws = WorldTracerWebService.getInstance();
		try{
			WebServiceDto dto = new WebServiceDto();
			dto.setCronUser(false);
			dto.setUseAvailableConnectionsIfAvailable(true);
//			Ahl a = wtws.findAHL("YYCWS10002", dto);
			System.out.print(wtws.findAHL("YYCWS10002", dto));
		}
		catch (WorldTracerException e){
			System.out.print(e.getCause().getMessage());
		}
		catch (Exception e){
			System.out.print(e.getStackTrace());
		}
		
	}
	
	private Ahl createAhl(){
		Ahl ahl = new Ahl();
			ahl.setAhlId("YYCWS10002");
			ahl.setPax(null);
			ahl.setPaxItinerary(null);
			ahl.setItem(null);
		return ahl;
	}
	
	@Test
	public void mockReturn(){
		
	}

}
