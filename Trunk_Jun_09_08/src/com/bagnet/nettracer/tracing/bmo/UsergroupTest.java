package com.bagnet.nettracer.tracing.bmo;

import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.UserGroup;
import com.bagnet.nettracer.tracing.utils.AdminUtils;

public class UsergroupTest {


	@Test
	public void testUsergroupNameLookup(){
		List<String> list=new ArrayList<String>();
		list.add("Ground Ops Weather_D");
		list.add("NetTracer_Station_Agent_S");
		list.add("Test_Group");
		int usergroupid=UsergroupBMO.getUsergroupMapId(list);
		UserGroup group=UsergroupBMO.getUsergroup(usergroupid);
		assertTrue(group.getDescription().equals("Station Agent"));
	}	
	
	@Test
	public void testAgentCreation(){

		List<String> list=new ArrayList<String>();
		list.add("Ground Ops Weather_D");
		list.add("NetTracer_Station_Agent_S");
		list.add("Test_Group");
		String millis=String.valueOf(System.currentTimeMillis());
		millis=millis.substring(millis.length()-10, millis.length()-1);
		Agent u=AdminUtils.createAgent("testUser"+millis, "testFirst", "testLast", list, "ATL", "WN");
		if(u!=null){
			assertTrue(u.getAgent_ID()>0);
		} else {
			assertTrue(false);
		}
	}	
}