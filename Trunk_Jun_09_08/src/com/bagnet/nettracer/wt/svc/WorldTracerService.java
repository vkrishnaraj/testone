package com.bagnet.nettracer.wt.svc;

import java.util.List;

import com.bagnet.nettracer.tracing.db.BDO;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.WT_FWD_Log;
import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles;
import com.bagnet.nettracer.tracing.db.WorldTracerFile.WTStatus;
import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles.ActionFileType;
import com.bagnet.nettracer.tracing.db.wtq.WtqFwdGeneral;
import com.bagnet.nettracer.tracing.db.wtq.WtqFwdOhd;
import com.bagnet.nettracer.tracing.db.wtq.WtqRequestOhd;
import com.bagnet.nettracer.tracing.db.wtq.WtqRequestQoh;
import com.bagnet.nettracer.wt.WorldTracerException;

public interface WorldTracerService {

	public static enum WorldTracerField {
		CT("color.type"), FD("flight.date"), IT("initials"), RT("routing"), NM("name"), PT("pax.title"), PS(
				"pax.status"), FL("freqflier.id"), PA("perm.address"), EA("email.address"), CO("country"), PN(
				"home.phone"), TP("work.phone"), CP("mobile.phone"), FX("fax.number"), NP("num.pax"), BR("bag.itin"), TN(
				"bag.tagnum"), BI("bag.brand"), HC("handled.copy"), FS("fault.station"), TK("ticket.num"), PB(
				"pooled.ticket"), AG("agent"), AB("bag.address"), PR("pnr.locator"), CS("costs"), RL(
				"reason.loss"), RC("loss.comments"), SL("storage.location"), XT("expidite.tag"), FO("fwd.date"), FW(
				"fwd.stations"), FB("fwd.bag.count"), FT("fault.terminal"), SI("sup.info"), TX("teletype.address"), FI("further.info"),
				TA("temp.address"), CC("contents"), DD("deliver.date"), LD("local.delivery.inst"), DA("delivery.address");
	
		private String description_id;
	
		private WorldTracerField(String desc) {
			this.description_id = desc;
		}
	
		public String description() {
			return description_id;
		}
	}
	
	public static enum TxType { CREATE_AHL("wt.create_ahl"), CLOSE_AHL("wt.close_ahl"),
		SUSPEND_AHL("wt.suspend_ahl"), REINSTATE_AHL("wt.reinstate_ahl"), CREATE_OHD("wt.create_ohd"), CLOSE_OHD("wt.close_ohd"),
		SUSPEND_OHD("wt.suspend_ohd"), REINSTATE_OHD("wt.reinstate_ohd"), FWD_GENERAL("wt.fwd_gen"), FWD_OHD("wt.fwd_ohd"),
		REQUEST_OHD("wt.request_ohd"), AMEND_AHL("wt.amend_ahl"), AMEND_OHD("wt.amend_ohd"), IMPORT_AHL("wt.import_ahl"),
		IMPORT_OHD("wt.import_ohd"), CREATE_BDO("wt.create_bdo"), ERASE_AF("wt.erase_actionfile"), REQUEST_QOH("wt.request.qoh");
	
		private String messageKey;
		
		private TxType(String messageKey) {
			this.messageKey = messageKey;
		}	
		public String getMessageKey() {
			return messageKey;
		}
		
		public String getValue() {
			return this.name();
		}
	}

	String MAX_ACTIONFILES = "max.actionfiles";

	String insertIncident(Incident incident) throws WorldTracerException;

	String insertOhd(OHD ohd) throws WorldTracerException;

	String closeIncident(Incident incident) throws WorldTracerException;

	String closeOHD(OHD ohd) throws WorldTracerException;
	
	String suspendIncident(Incident incident)throws WorldTracerException;
	
	String suspendOhd(OHD ohd) throws WorldTracerException;
	
	String reinstateIncident(Incident incident) throws WorldTracerException;
	
	String reinstateOhd(OHD ohd) throws WorldTracerException;

	String sendFwdMsg(WtqFwdGeneral fwd) throws WorldTracerException;
	
	String forwardOhd(WtqFwdOhd fwd) throws WorldTracerException;
	
	String requestOhd(WtqRequestOhd roh) throws WorldTracerException;
	
	String requestQoh(WtqRequestQoh qoh) throws WorldTracerException;

	void eraseActionFile(Worldtracer_Actionfiles waf) throws WorldTracerException;

	List<Worldtracer_Actionfiles> getActionFiles(String airline, String station, ActionFileType actionFileType, int day)
			throws WorldTracerException;
	
	String insertBdo(BDO bdo) throws WorldTracerException;

	Incident getIncidentForAHL(String wt_id, WTStatus status) throws WorldTracerException;
	
	OHD getOhdforOhd(String wt_id, WTStatus status) throws WorldTracerException;

	String amendAhl(Incident incident) throws WorldTracerException;
	
	String amendOhd(OHD ohd) throws WorldTracerException;

}
