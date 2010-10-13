package aero.nettracer.serviceprovider.wt_1_0.services.webservices;

import org.junit.Test;

import aero.nettracer.serviceprovider.wt_1_0.common.ActionFile;
import aero.nettracer.serviceprovider.wt_1_0.common.ActionFileCount;
import aero.nettracer.serviceprovider.wt_1_0.common.Content;
import java.util.HashMap;
import java.util.Map;


public class WorldTracerServiceImplTest {
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
	
	
}
