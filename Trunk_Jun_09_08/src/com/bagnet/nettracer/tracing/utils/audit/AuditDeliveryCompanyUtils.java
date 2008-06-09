/*
 * Created on Jul 6, 2005
 *
 * Administrator
 */
package com.bagnet.nettracer.tracing.utils.audit;

import org.apache.log4j.Logger;

import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.DeliverCo_Station;
import com.bagnet.nettracer.tracing.db.DeliverCompany;
import com.bagnet.nettracer.tracing.db.Deliver_ServiceLevel;
import com.bagnet.nettracer.tracing.db.audit.Audit_DeliverCo_Station;
import com.bagnet.nettracer.tracing.db.audit.Audit_DeliverCompany;
import com.bagnet.nettracer.tracing.db.audit.Audit_Deliver_ServiceLevel;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;

/**
 * @author Byron Smith
 *
 */
public class AuditDeliveryCompanyUtils {

	private static Logger logger = Logger.getLogger(AuditDeliveryCompanyUtils.class);


	public static Audit_DeliverCompany getAuditDeliverCompany(DeliverCompany dc, Agent mod_agent) throws Exception {
		Audit_DeliverCompany audit_dc = new Audit_DeliverCompany();
		audit_dc.setDelivercompany_ID(dc.getDelivercompany_ID());
		audit_dc.setAddress(dc.getAddress());
		audit_dc.setPhone(dc.getPhone());
		audit_dc.setCompany(dc.getCompany());
		audit_dc.setName(dc.getName());
		audit_dc.setTime_modified(TracerDateTime.getGMTDate());
		audit_dc.setModifying_agent(mod_agent);
		return audit_dc;
	}
	
	public static Audit_DeliverCo_Station getAuditDeliverCoStation(DeliverCo_Station dcs, Agent mod_agent) throws Exception {
		Audit_DeliverCo_Station audit_dcs = new Audit_DeliverCo_Station();
		audit_dcs.setDeliverco_station_ID(dcs.getDeliverco_station_ID());
		audit_dcs.setDelivercompany(dcs.getDelivercompany());
		audit_dcs.setStation_ID(dcs.getStation_ID());
		audit_dcs.setTime_modified(TracerDateTime.getGMTDate());
		audit_dcs.setModifying_agent(mod_agent);
		return audit_dcs;
	}
	
	public static Audit_Deliver_ServiceLevel getAuditDeliver_ServiceLevel(Deliver_ServiceLevel sl, Agent mod_agent) throws Exception {
		Audit_Deliver_ServiceLevel audit_sl = new Audit_Deliver_ServiceLevel();
		audit_sl.setServicelevel_ID(sl.getServicelevel_ID());
		audit_sl.setDelivercompany(sl.getDelivercompany());
		audit_sl.setDescription(sl.getDescription());
		audit_sl.setTime_modified(TracerDateTime.getGMTDate());
		audit_sl.setModifying_agent(mod_agent);
		return audit_sl;
	}

}