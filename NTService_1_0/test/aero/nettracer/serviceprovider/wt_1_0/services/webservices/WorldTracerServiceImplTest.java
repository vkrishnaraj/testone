package aero.nettracer.serviceprovider.wt_1_0.services.webservices;

import org.apache.xmlbeans.XmlError;
import org.apache.xmlbeans.XmlOptions;
import org.junit.Test;

import aero.nettracer.serviceprovider.common.db.ParameterType;
import aero.nettracer.serviceprovider.wt_1_0.common.ActionFile;
import aero.nettracer.serviceprovider.wt_1_0.common.ActionFileCount;
import aero.nettracer.serviceprovider.wt_1_0.common.Ahl;
import aero.nettracer.serviceprovider.wt_1_0.common.Content;
import aero.nettracer.serviceprovider.wt_1_0.dto.WorldTracerActionDTO;
import aero.nettracer.serviceprovider.wt_1_0.services.PreProcessor;
import aero.nettracer.serviceprovider.wt_1_0.services.WorldTracerException;
import aero.sita.www.bag.wtr._2009._01.RecordIdentifierType;
import aero.sita.www.bag.wtr._2009._01.RecordReferenceType;
import aero.sita.www.bag.wtr._2009._01.RecordType;
import aero.sita.www.bag.wtr._2009._01.WTRDelayedBagRecReadRSDocument;
import aero.sita.www.bag.wtr._2009._01.WTRReadRecordRQDocument;
import aero.sita.www.bag.wtr._2009._01.WTRReadRecordRQDocument.WTRReadRecordRQ;
import aero.sita.www.bag.wtr.delayedbagservice.DelayedBagServiceStub;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class WorldTracerServiceImplTest {
	private String getEnvironment(WorldTracerActionDTO dto) {
		String retVal = dto.getUser().getProfile().getParameters().get(ParameterType.CLIENT_ENVIRONMENT);
		if (retVal == null)
			return "";
		else
			return retVal;
	}
	
	
	@Test
	public void testFormate(){
		String s = "1234 Anywhere St.";
		try {
			String address = WorldTracerServiceImpl.BASIC_RULE.formatEntry(s.trim());
			System.out.println(address);
		} catch (WorldTracerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void contentList(){
		Content audio = new Content();
		audio.setCategory("audio");
		audio.setDescription("klispch");
		Content audio2 = new Content();
		audio2.setCategory("audio");
		audio2.setDescription("polk");
		Content audio3 = new Content();
		audio3.setCategory("audio");
		audio3.setDescription("");
		Content food2 = new Content();
		food2.setCategory("food");
		food2.setDescription("cookie");
		Content food = new Content();
		food.setCategory("food");
		food.setDescription("eggs bacon hashbrowns orange juice toast jam");
		
		Content [] ca = {audio, audio2, food2, food, audio3};
		Map <String,String>m = WorldTracerServiceImpl.combineContentFields(new Content[0]);
		for(String key:m.keySet()){
			System.out.println(key + "/" + m.get(key));
		}
	}
	
	@Test
	public void testValidation(){
		try{	
		DelayedBagServiceStub stub = new DelayedBagServiceStub("https://webservice.worldtracer.aero/DelayedBagService/0.1");

		WTRReadRecordRQDocument d = WTRReadRecordRQDocument.Factory.newInstance();
		WTRReadRecordRQ d1 = d.addNewWTRReadRecordRQ();

		// Set version & POS
		d1.setVersion(new BigDecimal(0.1).setScale(1, RoundingMode.HALF_UP));
		d1.addNewPOS().addNewSource().setAirlineVendorID("WS");

		RecordIdentifierType t1 = d1.addNewRecordID();
		t1.setRecordType(RecordType.DELAYED);

		RecordReferenceType t2 = t1.addNewRecordReference();

		t2.setAirlineCode("WS");
		t2.setReferenceNumber(101);
		t2.setStationCode("YYC");

		d1.setAgentID("1000");
		// Send Message
		WTRDelayedBagRecReadRSDocument wsresponse = null;
		org.apache.xmlbeans.XmlOptions options = new XmlOptions();
		ArrayList<XmlError> errors = new ArrayList();
		options.setErrorListener(errors);
		
		WorldTracerServiceImpl.validate(null);
		
//		boolean valid = d.validate(options);
//		if(!valid){
//			for(XmlError error:errors){
//				System.out.println("\n");
//				System.out.println("Message: " + error.getMessage() + "\n");
//				System.out.println("Location of invalid XML: " + 
//						error.getCursorLocation().xmlText() + "\n");
//
//			}
//		}

		} catch (Exception e){
			e.printStackTrace();
		}
		System.out.println("fin");
	}
	
}
