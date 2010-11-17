package com.bagnet.nettracer.wt.svc;

import java.util.List;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.BDO;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles;
import com.bagnet.nettracer.tracing.db.WorldTracerFile.WTStatus;
import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles.ActionFileType;
import com.bagnet.nettracer.tracing.db.wt.ActionFileCount;
import com.bagnet.nettracer.tracing.db.wtq.WtqFwdGeneral;
import com.bagnet.nettracer.tracing.db.wtq.WtqFwdOhd;
import com.bagnet.nettracer.tracing.db.wtq.WtqQoh;
import com.bagnet.nettracer.tracing.db.wtq.WtqRequestOhd;
import com.bagnet.nettracer.tracing.db.wtq.WtqRequestPxf;
import com.bagnet.nettracer.tracing.db.wtq.WtqRequestQoh;
import com.bagnet.nettracer.wt.WorldTracerException;
import com.bagnet.nettracer.wt.connector.CaptchaException;
import com.bagnet.nettracer.wt.connector.WebServiceDto;
import com.bagnet.nettracer.wt.connector.WorldTracerConnector;

public interface WorldTracerService {

	public static enum WorldTracerField {
		CT("color.type"), FD("flight.date"), IT("initials"), RT("routing"), NM("name"), PT("pax.title"), PS(
				"pax.status"), FL("freqflier.id"), PA("perm.address"), EA("email.address"), CO("country"), PN(
				"home.phone"), TP("work.phone"), CP("mobile.phone"), FX("fax.number"), NP("num.pax"), BR("bag.itin"), TN(
				"bag.tagnum"), BI("bag.brand"), HC("handled.copy"), FS("fault.station"), TK("ticket.num"), PB(
				"pooled.ticket"), AG("agent"), AB("bag.address"), PR("pnr.locator"), CS("costs"), RL(
				"reason.loss"), RC("loss.comments"), SL("storage.location"), XT("expidite.tag"), FO("fwd.date"), FW(
				"fwd.stations"), FB("fwd.bag.count"), FT("fault.terminal"), SI("sup.info"), TX("teletype.address"), FI("further.info"),
				TA("temp.address"), CC("contents"), DD("deliver.date"), LD("local.delivery.inst"), DA("delivery.address"), TI("text.info"), 
				NF("new.flight"), NR("new.routing"), STATE("web.state"), ZIP("web.zip");
	
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
		IMPORT_OHD("wt.import_ohd"), CREATE_BDO("wt.create_bdo"), ERASE_AF("wt.erase_actionfile"), REQUEST_QOH("wt.request.qoh"), AF_COUNT("wt.af.count"), AF_SUMMARY("wt.af.summary"), AF_DETAIL("wt.af.detail"), SEND_PXF("wt.send.pxf"), CREATE_QOH("wt.qoh");
	
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

	String insertIncident(Incident incident, WebServiceDto dto) throws WorldTracerException, CaptchaException;

	String insertOhd(OHD ohd, WebServiceDto dto) throws WorldTracerException, CaptchaException;

	String closeIncident(Incident incident, WebServiceDto dto) throws WorldTracerException, CaptchaException;

	String closeOHD(OHD ohd, WebServiceDto dto) throws WorldTracerException, CaptchaException;
	
	String suspendIncident(Incident incident, WebServiceDto dto)throws WorldTracerException, CaptchaException;
	
	String suspendOhd(OHD ohd, WebServiceDto dto) throws WorldTracerException, CaptchaException;
	
	String reinstateIncident(Incident incident, WebServiceDto dto) throws WorldTracerException, CaptchaException;
	
	String reinstateOhd(OHD ohd, WebServiceDto dto) throws WorldTracerException, CaptchaException;

	String sendFwdMsg(WtqFwdGeneral fwd, Agent defaultWtAgent, WebServiceDto dto) throws WorldTracerException, CaptchaException;
	
	String forwardOhd(WtqFwdOhd fwd, WebServiceDto dto) throws WorldTracerException, CaptchaException;
	
	String requestOhd(WtqRequestOhd roh, WebServiceDto dto) throws WorldTracerException, CaptchaException;
	
	String requestQoh(WtqRequestQoh qoh, WebServiceDto dto) throws WorldTracerException, CaptchaException;

	void eraseActionFile(Worldtracer_Actionfiles waf, WebServiceDto dto) throws WorldTracerException, CaptchaException;
	
	String insertBdo(BDO bdo, WebServiceDto dto) throws WorldTracerException, CaptchaException;

	Incident getIncidentForAHL(String wt_id, WTStatus status, Agent user, WebServiceDto dto) throws WorldTracerException, CaptchaException;
	
	OHD getOhdforOhd(String wt_id, WTStatus status, Agent agent, WebServiceDto dto) throws WorldTracerException, CaptchaException;

	String amendAhl(Incident incident, WebServiceDto dto) throws WorldTracerException, CaptchaException;
	
	String amendOhd(OHD ohd, WebServiceDto dto) throws WorldTracerException, CaptchaException;
	
	public List<ActionFileCount> getActionFileCount(String companyCode,	String wtStation, Agent user, WebServiceDto dto) throws CaptchaException;
	
	List<Worldtracer_Actionfiles> getActionFileSummary(String companyCode, String wtStation, ActionFileType afType, String afSeq, int day, Agent user, WebServiceDto dto) throws WorldTracerException, CaptchaException;
	
	String getActionFileDetail(String companyCode, String wtStation, ActionFileType afType, int day, int itemNum, Agent user, WebServiceDto dto) throws WorldTracerException, CaptchaException;

	WorldTracerConnector getWtConnector();
	
	public String sendPxf(WtqRequestPxf pxf, WebServiceDto dto) throws WorldTracerException, CaptchaException;

	String insertQoh(WtqQoh wtqQoh, WebServiceDto dto) throws WorldTracerException, CaptchaException;
	
}
