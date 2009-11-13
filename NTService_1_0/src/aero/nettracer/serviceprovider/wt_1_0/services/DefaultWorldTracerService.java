package aero.nettracer.serviceprovider.wt_1_0.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Configurable;

import aero.nettracer.serviceprovider.wt_1_0.services.wtrweb.service.BasicRule;
import aero.nettracer.serviceprovider.wt_1_0.services.wtrweb.service.WorldTracerRule;
import aero.nettracer.serviceprovider.wt_1_0.services.wtrweb.service.WorldTracerService;
import aero.nettracer.serviceprovider.wt_1_0.services.wtrweb.service.WorldTracerRule.Format;

@Configurable("wtService")
public class DefaultWorldTracerService implements WorldTracerService {

	public static final DateFormat ITIN_DATE_FORMAT = new SimpleDateFormat("ddMMM", Locale.US);



	public static final Pattern FLIGHTNUM_FORMAT = Pattern.compile("[1-9]\\d{0,3}[a-zA-Z]?");

//	private WorldTracerConnector wtConnector;

	private String wtCompanyCode;

	private static final List<String> wt_mats = Arrays.asList("D", "L", "M", "R", "T");
	private static final List<String> wt_descs = Arrays.asList("D", "L", "M", "R", "T", "B", "K", "C", "H", "S", "W",
			"X");

	public static final String FIELD_SEP = ".";
	public static final String ENTRY_SEP = "/";
	public static final String CONTINUATION = "-";

	private static final List<String> VALID_BAG_TYPES = Arrays.asList(new String[] { "01", "02", "03", "05", "06",
			"07", "08", "09", "10", "12", "20", "22", "23", "25", "26", "27", "28", "29", "50", "51", "52", "53", "54",
			"55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "71", "72", "73",
			"74", "75", "81", "82", "83", "85", "89", "90", "92", "93", "94", "95", "96", "97", "98", "99" });

	public static enum RepeatType {
		NONE, SAME_LINE, MANY_LINES, MULTIPLE
	}
	public static final WorldTracerRule<String> BASIC_RULE = new BasicRule(1, 57, 1, Format.FREE_FLOW);
	private static final String DEFAULT_BAG_TYPE = "99";
	private static final String UNKNOWN_AIRLINE = "YY";

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
