package com;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.junit.Test;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.dto.SearchIncident_DTO;
import com.bagnet.nettracer.tracing.utils.TracerUtils;

public class DbTest {

	private static Logger logger = Logger.getLogger(DbTest.class);
	@Test
	public void testIt() {

		Session sess = HibernateWrapper.getSession().openSession();
		
		Incident i = (Incident) sess.load(Incident.class, "XLFUS00007269");
		sess.close();
	}
	
	@Test
	public void test2() {
		Agent a = TracerUtils.getAgent("ntadmin", "US");
		logger.error("1...");
		Agent b = TracerUtils.getAgent("ntadmin", "US");
		logger.error("2...");
		IncidentBMO iBMO = new IncidentBMO();
		SearchIncident_DTO siDTO = new SearchIncident_DTO();
		int[] statuses = new int[3];
		statuses[0] = TracingConstants.MBR_STATUS_OPEN;
		statuses[1] = TracingConstants.MBR_STATUS_PENDING;
		statuses[2] = TracingConstants.MBR_STATUS_TEMP;
		siDTO.setStatus_IDs(statuses);
		siDTO.setStationassigned_ID(7);
		siDTO.setItemType_ID(2);
		
		iBMO.findIncident(siDTO, a, 1, 0, false, false);
	}
}
