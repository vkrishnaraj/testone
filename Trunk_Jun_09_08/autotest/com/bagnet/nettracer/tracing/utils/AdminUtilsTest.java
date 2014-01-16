package com.bagnet.nettracer.tracing.utils;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.audit.Audit_Agent;
import com.bagnet.nettracer.tracing.utils.audit.AuditAgentUtils;

public class AdminUtilsTest {
	

	/**
	 * This method is to test that the AdminUtils.createAgent method creates an audit entry for the newly created agent
	 * and that by default, web services are enabled (as per SWA requirements).
	 * 
	 * This method should be run manually rather that as part of automated testing since it involves creating agents.
	 */
	public void createAgentTest(){
		String username = "testuser3";
		String fname = "test";
		String lname = "user1";
		String groupName = "NetTracer_Station_Agent_S";
		String stationCode = "ATL";
		String compCode = "WN";
		ArrayList<String>groupNames = new ArrayList<String>();
		groupNames.add(groupName);
		
		AdminUtils.createAgent(username, fname, lname, groupNames, stationCode, compCode);
		Agent agent = AdminUtils.getAgentBasedOnUsername(username, "WN");
		assertTrue(agent.isWs_enabled());
		
		@SuppressWarnings("unchecked")
		List<Audit_Agent> audits = AuditAgentUtils.getAudits("" + agent.getAgent_ID(), 15, 0, false);
		assertTrue(audits.size() == 1);
	}
}
