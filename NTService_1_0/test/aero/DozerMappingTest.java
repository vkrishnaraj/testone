package aero;

import java.util.ArrayList;

import junit.framework.Assert;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.junit.Test;

import aero.nettracer.serviceprovider.wt_1_0.common.ActionFileCount;
import aero.nettracer.serviceprovider.wt_1_0.common.Ahl;
import aero.nettracer.serviceprovider.wt_1_0.common.xsd.WorldTracerResponse;

public class DozerMappingTest {
	
	private static Logger logger = Logger.getLogger(DozerMappingTest.class);
	@Test
	public void test() {
		logger.setLevel(Level.ALL);
		WorldTracerResponse destObject = null;
		try {
			aero.nettracer.serviceprovider.wt_1_0.common.WorldTracerResponse input = new aero.nettracer.serviceprovider.wt_1_0.common.WorldTracerResponse();
			input.setResponseData("Response data string");
			input.setResponseId("Response ID");
			input.setSuccess(true);
			ArrayList<ActionFileCount> list = new ArrayList<ActionFileCount>();
			ActionFileCount a = new ActionFileCount();
			a.setDay(1);
			a.setCount(2);
			a.setStation("XAX");
			list.add(a);
			ActionFileCount b = new ActionFileCount();
			b.setDay(3);
			b.setCount(4);
			list.add(b);
			
//			input.setCounts(list);
			Ahl ahl = new Ahl();
			input.setAhl(ahl);
			ahl.setAhlId("ASDF123123");

			ArrayList mappingFiles = new ArrayList();
			mappingFiles.add("dozerBeanMapping.xml");
			//Mapper mapper = new DozerBeanMapper(mappingFiles);
			Mapper mapper = new DozerBeanMapper();
			destObject = mapper.map(input,WorldTracerResponse.class);
//			mapper.map(input, destObject);
			
			
			System.out.println(destObject.toString());
			
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
//		aero.nettracer.serviceprovider.wt_1_0.common.xsd.ActionFileCount[] counts = destObject.getC;
//		System.out.println("Size: " + counts.length);
//		Assert.assertEquals(counts.length == 2, true);

	}
	
	

}
