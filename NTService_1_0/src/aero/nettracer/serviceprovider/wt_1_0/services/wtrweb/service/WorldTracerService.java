package aero.nettracer.serviceprovider.wt_1_0.services.wtrweb.service;


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
		IMPORT_OHD("wt.import_ohd"), CREATE_BDO("wt.create_bdo"), ERASE_AF("wt.erase_actionfile"), REQUEST_QOH("wt.request.qoh"), AF_COUNT("wt.af.count"), AF_SUMMARY("wt.af.summary"), AF_DETAIL("wt.af.detail"), SEND_PXF("wt.send.pxf");
	
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
}
