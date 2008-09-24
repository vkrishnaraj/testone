package com.bagnet.nettracer.wt.svc;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.bagnet.nettracer.aop.WorldTracerTx;
import com.bagnet.nettracer.exceptions.BagtagException;
import com.bagnet.nettracer.tracing.bmo.CategoryBMO;
import com.bagnet.nettracer.tracing.bmo.LossCodeBMO;
import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.bmo.XDescElementsBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Address;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.BDO;
import com.bagnet.nettracer.tracing.db.Claim;
import com.bagnet.nettracer.tracing.db.Company_specific_irregularity_code;
import com.bagnet.nettracer.tracing.db.ExpensePayout;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Incident_Claimcheck;
import com.bagnet.nettracer.tracing.db.Item;
import com.bagnet.nettracer.tracing.db.Item_Inventory;
import com.bagnet.nettracer.tracing.db.Itinerary;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.OHD_Address;
import com.bagnet.nettracer.tracing.db.OHD_CategoryType;
import com.bagnet.nettracer.tracing.db.OHD_Inventory;
import com.bagnet.nettracer.tracing.db.OHD_Itinerary;
import com.bagnet.nettracer.tracing.db.OHD_Passenger;
import com.bagnet.nettracer.tracing.db.Passenger;
import com.bagnet.nettracer.tracing.db.WT_FWD_Log;
import com.bagnet.nettracer.tracing.db.WT_FWD_Log_Itinerary;
import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles;
import com.bagnet.nettracer.tracing.db.WorldTracerFile.WTStatus;
import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles.ActionFileType;
import com.bagnet.nettracer.tracing.db.wtq.WorldTracerTransaction;
import com.bagnet.nettracer.tracing.db.wtq.WtqFwd;
import com.bagnet.nettracer.tracing.db.wtq.WtqFwdGeneral;
import com.bagnet.nettracer.tracing.db.wtq.WtqFwdOhd;
import com.bagnet.nettracer.tracing.db.wtq.WtqRequestOhd;
import com.bagnet.nettracer.tracing.db.wtq.WtqSegment;
import com.bagnet.nettracer.tracing.db.wtq.WorldTracerTransaction.Result;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.StringUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.lookup.LookupAirlineCodes;
import com.bagnet.nettracer.wt.WTIncident;
import com.bagnet.nettracer.wt.WTOHD;
import com.bagnet.nettracer.wt.WorldTracerException;
import com.bagnet.nettracer.wt.WorldTracerUtils;
import com.bagnet.nettracer.wt.bmo.WtTransactionBmo;
import com.bagnet.nettracer.wt.connector.BetaWtConnector;
import com.bagnet.nettracer.wt.connector.WorldTracerConnector;
import com.bagnet.nettracer.wt.svc.WorldTracerRule.Format;
import com.bagnet.nettracer.wt.svc.WorldTracerService.WorldTracerField;

public class DefaultWorldTracerService implements WorldTracerService {

	private static final DateFormat ITIN_DATE_FORMAT = new SimpleDateFormat("ddMMM");

	private static final Logger logger = Logger.getLogger(DefaultWorldTracerService.class);

	public static final EnumMap<WorldTracerField, WorldTracerRule<String>> AMEND_AHL_FIELD_RULES;

	private WorldTracerConnector wtConnector;

	private String wtCompanyCode;

	private WtTransactionBmo txBmo;
	
	private static final List<String> wt_mats = Arrays.asList("D", "L", "M", "R", "T");
	private static final List<String> wt_descs = Arrays.asList("D", "L", "M", "R", "T", "B", "K", "C", "H", "S", "W", "X" );

	public static final String FIELD_SEP = ".";
	public static final String ENTRY_SEP = "/";
	public static final String CONTINUATION = "-";

	public static enum RepeatType {
		NONE, SAME_LINE, MANY_LINES, MULTIPLE
	}

	public static final EnumMap<WorldTracerField, WorldTracerRule<String>> INC_FIELD_RULES;
	public static final EnumMap<WorldTracerField, WorldTracerRule<String>> OHD_FIELD_RULES;

	public static final EnumMap<WorldTracerField, WorldTracerRule<String>> CAH_FIELD_RULES;
	public static final EnumMap<WorldTracerField, WorldTracerRule<String>> COH_FIELD_RULES;

	public static final EnumMap<WorldTracerField, WorldTracerRule<String>> FWD_FIELD_RULES;

	public static final EnumMap<WorldTracerField, WorldTracerRule<String>> ROH_FIELD_RULES;
	
	public static final EnumMap<WorldTracerField, WorldTracerRule<String>> AMEND_OHD_FIELD_RULES;

	static {
		INC_FIELD_RULES = new EnumMap<WorldTracerField, WorldTracerRule<String>>(WorldTracerField.class);
		INC_FIELD_RULES.put(WorldTracerField.CT, new SameLineRule(7, 7, 10, Format.ALPHA_NUMERIC));
		// new Object[] { 10, RepeatType.MULTIPLE });
		INC_FIELD_RULES.put(WorldTracerField.FD, new SameLineRule(1, 14, 4, Format.ALPHA_NUMERIC));
		// , new Object[] { 4, RepeatType.SAME_LINE });
		INC_FIELD_RULES.put(WorldTracerField.IT, new SameLineRule(1, 4, 3, Format.ALPHA));
		// new Object[] { 3, RepeatType.SAME_LINE });
		INC_FIELD_RULES.put(WorldTracerField.RT, new SameLineRule(3, 3, 5, Format.ALPHA_NUMERIC));
		// new Object[] { 15, RepeatType.SAME_LINE });
		INC_FIELD_RULES.put(WorldTracerField.NM, new SameLineRule(2, 16, 3, Format.ALPHA));
		// new Object[] { 3, RepeatType.SAME_LINE });
		INC_FIELD_RULES.put(WorldTracerField.PT, new BasicRule(1, 25, 1, Format.FREE_FLOW));
		// new Object[] { 1, RepeatType.NONE });
		INC_FIELD_RULES.put(WorldTracerField.PS, new BasicRule(1, 25, 1, Format.FREE_FLOW));
		// new Object[] { 1, RepeatType.NONE });
		INC_FIELD_RULES.put(WorldTracerField.FL, new BasicRule(1, 25, 1, Format.ALPHA_NUMERIC));
		// new Object[] { 1, RepeatType.NONE });
		INC_FIELD_RULES.put(WorldTracerField.PA, new BasicRule(1, 57, 2, Format.FREE_FLOW));
		INC_FIELD_RULES.put(WorldTracerField.TA, new BasicRule(1, 57, 2, Format.FREE_FLOW));
		// new Object[] { 1, RepeatType.MULTIPLE });
		INC_FIELD_RULES.put(WorldTracerField.EA, new EmailRule(1, 44, 1, Format.FREE_FLOW));
		// new Object[] { 1, RepeatType.NONE });
		INC_FIELD_RULES.put(WorldTracerField.CO, new BasicRule(2, 5, 1, Format.ALPHA_NUMERIC));
		// new Object[] { 1, RepeatType.NONE });
		INC_FIELD_RULES.put(WorldTracerField.PN, new BasicRule(1, 20, 2, Format.NUMERIC));
		// new Object[] { 2, RepeatType.MULTIPLE });
		INC_FIELD_RULES.put(WorldTracerField.TP, new BasicRule(1, 20, 2, Format.NUMERIC));
		// new Object[] { 2, RepeatType.MULTIPLE });
		INC_FIELD_RULES.put(WorldTracerField.CP, new BasicRule(1, 20, 2, Format.NUMERIC));
		// new Object[] { 2, RepeatType.MULTIPLE });
		INC_FIELD_RULES.put(WorldTracerField.FX, new BasicRule(1, 20, 2, Format.NUMERIC));
		// new Object[] { 2, RepeatType.MULTIPLE });
		INC_FIELD_RULES.put(WorldTracerField.NP, new BasicRule(1, 25, 1, Format.FREE_FLOW));
		// new Object[] { 1, RepeatType.NONE });
		INC_FIELD_RULES.put(WorldTracerField.BR, new SameLineRule(1, 14, 4, Format.ALPHA_NUMERIC));
		// new Object[] { 4, RepeatType.SAME_LINE });
		INC_FIELD_RULES.put(WorldTracerField.TN, new BasicRule(8, 10, 10, Format.ALPHA_NUMERIC));
		// new Object[] { 10, RepeatType.MULTIPLE });
		INC_FIELD_RULES.put(WorldTracerField.BI, new BasicRule(2, 57, 10, Format.FREE_FLOW));
		// new Object[] { 10, RepeatType.MULTIPLE });
		INC_FIELD_RULES.put(WorldTracerField.HC, new BasicRule(1, 1, 1, Format.ALPHA));
		// new Object[] { 1, RepeatType.NONE });
		INC_FIELD_RULES.put(WorldTracerField.FS, new BasicRule(3, 3, 1, Format.FREE_FLOW));
		// new Object[] { 1, RepeatType.NONE });
		INC_FIELD_RULES.put(WorldTracerField.TK, new BasicRule(1, 57, 1, Format.FREE_FLOW));
		// new Object[] { 1, RepeatType.NONE });
		INC_FIELD_RULES.put(WorldTracerField.PB, new BasicRule(1, 15, 1, Format.ALPHA_NUMERIC));
		// new Object[] { 1, RepeatType.NONE });
		INC_FIELD_RULES.put(WorldTracerField.CC, new ContentRule(1, 57, 10, Format.FREE_FLOW));
		// new Object[] { 10, RepeatType.MULTIPLE });
		INC_FIELD_RULES.put(WorldTracerField.AG, new BasicRule(1, 12, 1, Format.FREE_FLOW));
		// new Object[] { 1, RepeatType.NONE });

		OHD_FIELD_RULES = new EnumMap<WorldTracerField, WorldTracerRule<String>>(WorldTracerField.class);
		OHD_FIELD_RULES.put(WorldTracerField.CT, new SameLineRule(7, 7, 1, Format.ALPHA_NUMERIC));
		// new Object[] { 1, RepeatType.NONE });
		OHD_FIELD_RULES.put(WorldTracerField.TN, new BasicRule(8, 10, 1, Format.ALPHA_NUMERIC));
		// new Object[] { 1, RepeatType.NONE });
		OHD_FIELD_RULES.put(WorldTracerField.PA, new BasicRule(1, 57, 2, Format.FREE_FLOW));
		OHD_FIELD_RULES.put(WorldTracerField.TA, new BasicRule(1, 57, 2, Format.FREE_FLOW));
		// new Object[] { 1, RepeatType.MULTIPLE });
		OHD_FIELD_RULES.put(WorldTracerField.FD, new SameLineRule(1, 14, 4, Format.ALPHA_NUMERIC));
		// new Object[] { 4, RepeatType.SAME_LINE });
		OHD_FIELD_RULES.put(WorldTracerField.RT, new SameLineRule(3, 3, 5, Format.ALPHA_NUMERIC));
		// new Object[] { 5, RepeatType.SAME_LINE });
		OHD_FIELD_RULES.put(WorldTracerField.AG, new BasicRule(1, 12, 1, Format.FREE_FLOW));
		// new Object[] { 1, RepeatType.NONE });
		// new Object[] { 1, RepeatType.NONE });
		OHD_FIELD_RULES.put(WorldTracerField.CP, new BasicRule(1, 20, 2, Format.NUMERIC));
		// new Object[] { 2, RepeatType.MULTIPLE });
		OHD_FIELD_RULES.put(WorldTracerField.FL, new BasicRule(1, 25, 1, Format.ALPHA_NUMERIC));
		OHD_FIELD_RULES.put(WorldTracerField.PN, new BasicRule(1, 20, 2, Format.NUMERIC));
		OHD_FIELD_RULES.put(WorldTracerField.TP, new BasicRule(1, 20, 2, Format.NUMERIC));
		OHD_FIELD_RULES.put(WorldTracerField.FX, new BasicRule(1, 20, 2, Format.NUMERIC));
		// new Object[] { 1, RepeatType.NONE });
		OHD_FIELD_RULES.put(WorldTracerField.NM, new SameLineRule(2, 16, 3, Format.ALPHA));
		// new Object[] { 3, RepeatType.SAME_LINE });
		OHD_FIELD_RULES.put(WorldTracerField.EA, new EmailRule(1, 44, 1, Format.FREE_FLOW));
		// new Object[] { 1, RepeatType.NONE });
		OHD_FIELD_RULES.put(WorldTracerField.SL, new BasicRule(1, 32, 1, Format.FREE_FLOW));
		// new Object[] { 1, RepeatType.NONE });
		OHD_FIELD_RULES.put(WorldTracerField.BI, new BasicRule(2, 57, 1, Format.FREE_FLOW));
		OHD_FIELD_RULES.put(WorldTracerField.PT, new BasicRule(1, 25, 1, Format.FREE_FLOW));
		OHD_FIELD_RULES.put(WorldTracerField.CC, new ContentRule(1, 57, 10, Format.FREE_FLOW));

		CAH_FIELD_RULES = new EnumMap<WorldTracerField, WorldTracerRule<String>>(WorldTracerField.class);
		CAH_FIELD_RULES.put(WorldTracerField.CS, new BasicRule(6, 16, 5, Format.ALPHA_NUMERIC));
		// new Object[] { 5, RepeatType.MULTIPLE });
		CAH_FIELD_RULES.put(WorldTracerField.NM, new SameLineRule(2, 16, 3, Format.ALPHA));
		// new Object[] { 3, RepeatType.SAME_LINE });
		CAH_FIELD_RULES.put(WorldTracerField.RL, new BasicRule(2, 2, 1, Format.NUMERIC));
		// new Object[] { 1, RepeatType.NONE });
		CAH_FIELD_RULES.put(WorldTracerField.FS, new BasicRule(3, 3, 1, Format.FREE_FLOW));
		// new Object[] { 1, RepeatType.NONE });
		CAH_FIELD_RULES.put(WorldTracerField.RC, new BasicRule(1, 57, 1, Format.FREE_FLOW));
		// new Object[] { 1, RepeatType.NONE });
		CAH_FIELD_RULES.put(WorldTracerField.AG, new BasicRule(1, 12, 1, Format.FREE_FLOW));
		// new Object[] { 1, RepeatType.NONE });

		COH_FIELD_RULES = new EnumMap<WorldTracerField, WorldTracerRule<String>>(WorldTracerField.class);
		COH_FIELD_RULES.put(WorldTracerField.CS, new BasicRule(6, 16, 5, Format.ALPHA_NUMERIC));
		// new Object[] { 5, RepeatType.MULTIPLE });
		COH_FIELD_RULES.put(WorldTracerField.AG, new BasicRule(1, 12, 1, Format.FREE_FLOW));
		// new Object[] { 1, RepeatType.NONE });

		FWD_FIELD_RULES = new EnumMap<WorldTracerField, WorldTracerRule<String>>(WorldTracerField.class);
		FWD_FIELD_RULES.put(WorldTracerField.AG, new BasicRule(1, 12, 1, Format.FREE_FLOW));
		// new Object[] { 1, RepeatType.NONE });
		FWD_FIELD_RULES.put(WorldTracerField.TN, new BasicRule(8, 10, 7, Format.ALPHA_NUMERIC));
		// new Object[] { 18, RepeatType.SAME_LINE });
		FWD_FIELD_RULES.put(WorldTracerField.XT, new BasicRule(8, 10, 7, Format.ALPHA_NUMERIC));
		// new Object[] { 18, RepeatType.SAME_LINE });
		FWD_FIELD_RULES.put(WorldTracerField.FO, new SameLineRule(1, 14, 4, Format.ALPHA_NUMERIC));
		// new Object[] { 4, RepeatType.SAME_LINE });
		FWD_FIELD_RULES.put(WorldTracerField.FW, new SameLineRule(5, 5, 5, Format.ALPHA_NUMERIC));
		// new Object[] { 5, RepeatType.SAME_LINE });
		FWD_FIELD_RULES.put(WorldTracerField.FB, new BasicRule(1, 2, 1, Format.NUMERIC));
		// new Object[] { 1, RepeatType.NONE });
		FWD_FIELD_RULES.put(WorldTracerField.FD, new SameLineRule(1, 14, 4, Format.ALPHA_NUMERIC));
		// new Object[] { 4, RepeatType.SAME_LINE });
		FWD_FIELD_RULES.put(WorldTracerField.FS, new BasicRule(3, 3, 1, Format.ALPHA_NUMERIC));
		// new Object[] { 1, RepeatType.NONE });
		FWD_FIELD_RULES.put(WorldTracerField.FT, new BasicRule(2, 2, 1, Format.ALPHA_NUMERIC));
		// new Object[] { 1, RepeatType.NONE });
		FWD_FIELD_RULES.put(WorldTracerField.HC, new BasicRule(1, 1, 1, Format.ALPHA));
		// new Object[] { 1, RepeatType.NONE });
		FWD_FIELD_RULES.put(WorldTracerField.NM, new SameLineRule(2, 16, 10, Format.ALPHA));
		// new Object[] { 10, RepeatType.SAME_LINE });
		FWD_FIELD_RULES.put(WorldTracerField.RC, new BasicRule(1, 55, 1, Format.FREE_FLOW));
		// new Object[] { 1, RepeatType.NONE });
		FWD_FIELD_RULES.put(WorldTracerField.RL, new BasicRule(2, 2, 1, Format.NUMERIC));
		// new Object[] { 1, RepeatType.NONE });
		FWD_FIELD_RULES.put(WorldTracerField.SI, new BasicRule(1, 55, 3, Format.FREE_FLOW));
		// new Object[] { 1, RepeatType.MANY_LINES });
		FWD_FIELD_RULES.put(WorldTracerField.TX, new BasicRule(5, 9, 10, Format.ALPHA_NUMERIC));
		// new Object[] { 10, RepeatType.SAME_LINE });
		
		ROH_FIELD_RULES = new EnumMap<WorldTracerField, WorldTracerRule<String>>(WorldTracerField.class);
		ROH_FIELD_RULES.put(WorldTracerField.AG, new BasicRule(1, 12, 1, Format.FREE_FLOW));
		ROH_FIELD_RULES.put(WorldTracerField.NM, new BasicRule(2, 16, 1, Format.ALPHA_NUMERIC));
		ROH_FIELD_RULES.put(WorldTracerField.FI, new BasicRule(1, 57, 1, Format.FREE_FLOW));
		ROH_FIELD_RULES.put(WorldTracerField.TX, new BasicRule(5, 9, 10, Format.ALPHA_NUMERIC));
		
		AMEND_AHL_FIELD_RULES = new EnumMap<WorldTracerField, WorldTracerRule<String>>(WorldTracerField.class);
		AMEND_AHL_FIELD_RULES.put(WorldTracerField.CT, new NumberedLinesRule(7, 7, 10, Format.ALPHA_NUMERIC, true));
		// new Object[] { 10, RepeatType.MULTIPLE });
		AMEND_AHL_FIELD_RULES.put(WorldTracerField.FD, new SameLineRule(1, 14, 4, Format.ALPHA_NUMERIC));
		// , new Object[] { 4, RepeatType.SAME_LINE });
		AMEND_AHL_FIELD_RULES.put(WorldTracerField.IT, new NumberedLinesRule(1, 4, 3, Format.ALPHA, false));
		// new Object[] { 3, RepeatType.SAME_LINE });
		AMEND_AHL_FIELD_RULES.put(WorldTracerField.RT, new SameLineRule(3, 3, 5, Format.ALPHA_NUMERIC));
		// new Object[] { 15, RepeatType.SAME_LINE });
		AMEND_AHL_FIELD_RULES.put(WorldTracerField.NM, new NumberedLinesRule(2, 16, 3, Format.ALPHA, true));
		// new Object[] { 3, RepeatType.SAME_LINE });
		AMEND_AHL_FIELD_RULES.put(WorldTracerField.PT, new BasicRule(1, 25, 1, Format.FREE_FLOW));
		// new Object[] { 1, RepeatType.NONE });
		AMEND_AHL_FIELD_RULES.put(WorldTracerField.PS, new BasicRule(1, 25, 1, Format.FREE_FLOW));
		// new Object[] { 1, RepeatType.NONE });
		AMEND_AHL_FIELD_RULES.put(WorldTracerField.FL, new BasicRule(1, 25, 1, Format.ALPHA_NUMERIC));
		// new Object[] { 1, RepeatType.NONE });
		AMEND_AHL_FIELD_RULES.put(WorldTracerField.PA, new NumberedLinesRule(1, 57, 2, Format.FREE_FLOW, true));
		AMEND_AHL_FIELD_RULES.put(WorldTracerField.TA, new NumberedLinesRule(1, 57, 2, Format.FREE_FLOW, true));
		// new Object[] { 1, RepeatType.MULTIPLE });
		AMEND_AHL_FIELD_RULES.put(WorldTracerField.EA, new EmailRule(1, 44, 1, Format.FREE_FLOW));
		// new Object[] { 1, RepeatType.NONE });
		AMEND_AHL_FIELD_RULES.put(WorldTracerField.CO, new BasicRule(2, 5, 1, Format.ALPHA_NUMERIC));
		// new Object[] { 1, RepeatType.NONE });
		AMEND_AHL_FIELD_RULES.put(WorldTracerField.PN, new NumberedLinesRule(1, 20, 2, Format.NUMERIC, true));
		// new Object[] { 2, RepeatType.MULTIPLE });
		AMEND_AHL_FIELD_RULES.put(WorldTracerField.TP, new NumberedLinesRule(1, 20, 2, Format.NUMERIC, true));
		// new Object[] { 2, RepeatType.MULTIPLE });
		AMEND_AHL_FIELD_RULES.put(WorldTracerField.CP, new NumberedLinesRule(1, 20, 2, Format.NUMERIC, true));
		// new Object[] { 2, RepeatType.MULTIPLE });
		AMEND_AHL_FIELD_RULES.put(WorldTracerField.FX, new NumberedLinesRule(1, 20, 2, Format.NUMERIC, true));
		// new Object[] { 2, RepeatType.MULTIPLE });
		AMEND_AHL_FIELD_RULES.put(WorldTracerField.NP, new BasicRule(1, 25, 1, Format.FREE_FLOW));
		// new Object[] { 1, RepeatType.NONE });
		AMEND_AHL_FIELD_RULES.put(WorldTracerField.BR, new SameLineRule(1, 14, 4, Format.ALPHA_NUMERIC));
		// new Object[] { 4, RepeatType.SAME_LINE });
		AMEND_AHL_FIELD_RULES.put(WorldTracerField.TN, new NumberedLinesRule(8, 10, 10, Format.ALPHA_NUMERIC));
		// new Object[] { 10, RepeatType.MULTIPLE });
		AMEND_AHL_FIELD_RULES.put(WorldTracerField.BI, new NumberedLinesRule(2, 57, 10, Format.FREE_FLOW, true));
		// new Object[] { 10, RepeatType.MULTIPLE });
		AMEND_AHL_FIELD_RULES.put(WorldTracerField.HC, new BasicRule(1, 1, 1, Format.ALPHA));
		// new Object[] { 1, RepeatType.NONE });
		AMEND_AHL_FIELD_RULES.put(WorldTracerField.FS, new BasicRule(3, 3, 1, Format.FREE_FLOW));
		// new Object[] { 1, RepeatType.NONE });
		AMEND_AHL_FIELD_RULES.put(WorldTracerField.TK, new BasicRule(1, 57, 1, Format.FREE_FLOW));
		// new Object[] { 1, RepeatType.NONE });
		AMEND_AHL_FIELD_RULES.put(WorldTracerField.PB, new BasicRule(1, 15, 1, Format.ALPHA_NUMERIC));
		// new Object[] { 1, RepeatType.NONE });
		AMEND_AHL_FIELD_RULES.put(WorldTracerField.CC, new ContentAmendRule(1, 57, 10, Format.FREE_FLOW, true));
		// new Object[] { 10, RepeatType.MULTIPLE });
		AMEND_AHL_FIELD_RULES.put(WorldTracerField.AG, new BasicRule(1, 12, 1, Format.FREE_FLOW));
		
		AMEND_OHD_FIELD_RULES = new EnumMap<WorldTracerField, WorldTracerRule<String>>(WorldTracerField.class);
		AMEND_OHD_FIELD_RULES.put(WorldTracerField.CT, new SameLineRule(7, 7, 1, Format.ALPHA_NUMERIC));
		// new Object[] { 1, RepeatType.NONE });
		AMEND_OHD_FIELD_RULES.put(WorldTracerField.TN, new BasicRule(8, 10, 1, Format.ALPHA_NUMERIC));
		// new Object[] { 1, RepeatType.NONE });
		AMEND_OHD_FIELD_RULES.put(WorldTracerField.PA, new NumberedLinesRule(1, 57, 2, Format.FREE_FLOW, true));
		// new Object[] { 1, RepeatType.MULTIPLE });
		AMEND_OHD_FIELD_RULES.put(WorldTracerField.FD, new SameLineRule(1, 14, 4, Format.ALPHA_NUMERIC));
		// new Object[] { 4, RepeatType.SAME_LINE });
		AMEND_OHD_FIELD_RULES.put(WorldTracerField.RT, new SameLineRule(3, 3, 1, Format.ALPHA_NUMERIC));
		// new Object[] { 5, RepeatType.SAME_LINE });
		AMEND_OHD_FIELD_RULES.put(WorldTracerField.AG, new BasicRule(1, 12, 1, Format.FREE_FLOW));
		// new Object[] { 1, RepeatType.NONE });
		AMEND_OHD_FIELD_RULES.put(WorldTracerField.CC, new ContentAmendRule(1, 57, 10, Format.FREE_FLOW, false));
		// new Object[] { 1, RepeatType.NONE });
		AMEND_OHD_FIELD_RULES.put(WorldTracerField.CP, new NumberedLinesRule(1, 20, 2, Format.NUMERIC, true));
		// new Object[] { 2, RepeatType.MULTIPLE });
		AMEND_OHD_FIELD_RULES.put(WorldTracerField.FL, new BasicRule(1, 25, 1, Format.ALPHA_NUMERIC));
		// new Object[] { 1, RepeatType.NONE });
		AMEND_OHD_FIELD_RULES.put(WorldTracerField.NM, new NumberedLinesRule(2, 16, 3, Format.ALPHA, true));
		// new Object[] { 3, RepeatType.SAME_LINE });
		AMEND_OHD_FIELD_RULES.put(WorldTracerField.EA, new EmailRule(1, 44, 1, Format.FREE_FLOW));
		// new Object[] { 1, RepeatType.NONE });
		AMEND_OHD_FIELD_RULES.put(WorldTracerField.SL, new BasicRule(1, 32, 1, Format.FREE_FLOW));
		// new Object[] { 1, RepeatType.NONE });
		AMEND_OHD_FIELD_RULES.put(WorldTracerField.BI, new BasicRule(2, 57, 1, Format.FREE_FLOW));
		
	}

	@WorldTracerTx(type = TxType.CREATE_AHL)
	public String insertIncident(Incident incident) throws WorldTracerException {
		String wt_id = null;

		try {
			Map<WorldTracerField, List<String>> fieldMap = createFieldMap(incident);

			if (fieldMap == null) {
				throw new WorldTracerException("Unable to generate incident mapping");
			}

			wt_id = wtConnector.insertIncident(fieldMap,
					incident.getStationassigned().getCompany().getCompanyCode_ID(), incident.getStationassigned()
							.getWt_stationcode());

		} catch (Exception e) {
			if (e instanceof WorldTracerException) {
				throw (WorldTracerException) e;
			} else {
				throw new WorldTracerException(e.getMessage(), e);
			}
		}
		return wt_id;
	}

	@WorldTracerTx(type = TxType.CLOSE_AHL)
	public String closeIncident(Incident incident) throws WorldTracerException {

		Map<WorldTracerField, List<String>> fieldMap = createCloseFieldMap(incident);

		String wt_id = wtConnector.closeIncident(fieldMap, incident.getWtFile().getWt_id(), incident
				.getStationassigned().getWt_stationcode());

		return wt_id;

	}

	@WorldTracerTx(type = TxType.CREATE_OHD)
	public String insertOhd(OHD ohd) throws WorldTracerException {

		Map<WorldTracerField, List<String>> fieldMap = createFieldMap(ohd);

		if (fieldMap == null) {
			throw new WorldTracerException("Unable to generate ohd mapping");
		}

		String wt_id = null;

		wt_id = wtConnector.insertOhd(fieldMap, ohd.getFoundAtStation().getCompany().getCompanyCode_ID(), ohd
				.getHoldingStation().getWt_stationcode());

		return wt_id;
	}

	@WorldTracerTx(type = TxType.CLOSE_OHD)
	public String closeOHD(OHD ohd) throws WorldTracerException {
		Map<WorldTracerField, List<String>> fieldMap = createCloseFieldMap(ohd);

		if (fieldMap == null) {
			throw new WorldTracerException("Unable to generate close ohd mapping");
		}

		String wt_id = null;

		wt_id = wtConnector.closeOhd(fieldMap, ohd.getWt_id(), ohd.getHoldingStation().getWt_stationcode());

		return wt_id;
	}

	@WorldTracerTx(type = TxType.REINSTATE_AHL)
	public String reinstateIncident(Incident incident) throws WorldTracerException {
		wtConnector.reinstateAHL(incident.getWt_id(), getAgentEntry(incident.getAgent()));
		return incident.getWt_id();
	}

	@WorldTracerTx(type = TxType.REINSTATE_OHD)
	public String reinstateOhd(OHD ohd) throws WorldTracerException {
		wtConnector.reinstateOHD(ohd.getWt_id(), getAgentEntry(ohd.getAgent()));
		return ohd.getWt_id();
	}

	@WorldTracerTx(type = TxType.SUSPEND_AHL)
	public String suspendIncident(Incident incident) throws WorldTracerException {
		wtConnector.suspendAHL(incident.getWt_id(), getAgentEntry(incident.getAgent()));
		return incident.getWt_id();
	}

	@WorldTracerTx(type = TxType.SUSPEND_OHD)
	public String suspendOhd(OHD ohd) throws WorldTracerException {
		wtConnector.suspendOHD(ohd.getWt_id(), getAgentEntry(ohd.getAgent()));
		return ohd.getWt_id();
	}

	@WorldTracerTx(type = TxType.CREATE_BDO)
	public String insertBdo(BDO bdo) throws WorldTracerException {
		// TODO Auto-generated method stub
		return null;
	}

	@WorldTracerTx(type = TxType.ERASE_AF)
	public void eraseActionFile(Worldtracer_Actionfiles waf) throws WorldTracerException {
		Worldtracer_Actionfiles temp = wtConnector.getActionFile(waf.getAirline(), waf.getStation(), waf
				.getAction_file_type(), waf.getDay(), waf.getItem_number());

		if (temp != null && waf.getAction_file_text() != null
				&& waf.getAction_file_text().equals(temp.getAction_file_text())) {
			wtConnector.eraseActionFile(waf.getStation(), waf.getAirline(), waf.getAction_file_type(), waf.getDay(),
					waf.getItem_number());
		}
	}

	public List<Worldtracer_Actionfiles> getActionFiles(String airline, String station, ActionFileType actionFileType,
			int day) throws WorldTracerException {
		return wtConnector.getActionFiles(airline, station, actionFileType, day);
	}

	@WorldTracerTx(type = TxType.IMPORT_AHL)
	public Incident getIncidentForAHL(String wt_id, WTStatus status) throws WorldTracerException {
		String result = wtConnector.findAHL(wt_id);
		Incident foundinc = WTIncident.parseWTIncident(result, status);
		return foundinc;
	}

	@WorldTracerTx(type = TxType.IMPORT_OHD)
	public OHD getOhdforOhd(String wt_id, WTStatus status) throws WorldTracerException {
		String result = wtConnector.findOHD(wt_id);
		// for now show all as active
		OHD foundohd = WTOHD.parseWTOHD(result, WTStatus.ACTIVE);
		return foundohd;
	}

	@WorldTracerTx(type = TxType.FWD_OHD)
	public String forwardOhd(WtqFwdOhd fwd) throws WorldTracerException {
		Map<WorldTracerField, List<String>> fieldMap = createFwdFieldMap(fwd);

		if (fieldMap == null) {
			throw new WorldTracerException("Unable to generate fwdOHD mapping");
		}

		String result = null;

		result = wtConnector.forwardOhd(fieldMap, fwd.getOhd().getWt_id(), fwd.getMatchingAhl());

		return result;
	}

	@WorldTracerTx(type = TxType.FWD_GENERAL)
	public String sendFwdMsg(WtqFwdGeneral fwd) throws WorldTracerException {
		Map<WorldTracerField, List<String>> fieldMap = createFwdFieldMap(fwd);

		if (fieldMap == null) {
			throw new WorldTracerException("Unable to generate fwdOHD mapping");
		}
		
		addIncidentFieldEntry(WorldTracerField.RL, Integer.toString(fwd.getLossCode()), fieldMap);
		
		addIncidentFieldEntry(WorldTracerField.RC, fwd.getLossComments(), fieldMap);
				
		String result  = wtConnector.sendFwd(fieldMap, fwd.getAgent().getStation().getStationcode(), fwd.getAgent().getCompanycode_ID());
		return result;
	}

	@WorldTracerTx(type = TxType.AMEND_AHL)
	public String amendAhl(Incident incident) throws WorldTracerException {
		String wt_id = null;

		try {
			Map<WorldTracerField, List<String>> fieldMap = createFieldMap(incident);

			if (fieldMap == null) {
				throw new WorldTracerException("Unable to generate incident mapping");
			}

			wt_id = wtConnector.amendAhl(fieldMap, incident.getWt_id());

		} catch (Exception e) {
			if (e instanceof WorldTracerException) {
				throw (WorldTracerException) e;
			} else {
				throw new WorldTracerException(e.getMessage(), e);
			}
		}
		return wt_id;
	}

	@WorldTracerTx(type = TxType.AMEND_OHD)
	public String amendOhd(OHD ohd) throws WorldTracerException {
		Map<WorldTracerField, List<String>> fieldMap = createFieldMap(ohd);

		if (fieldMap == null) {
			throw new WorldTracerException("Unable to generate ohd mapping");
		}

		String result = null;

		result = wtConnector.amendOhd(fieldMap, ohd.getWt_id());

		return result;
	}

	@WorldTracerTx(type = TxType.REQUEST_OHD)
	public String requestOhd(WtqRequestOhd roh) throws WorldTracerException {
		Map<WorldTracerField, List<String>> fieldMap = createFieldMap(roh);
		String result = wtConnector.requestOhd(roh.getWt_ohd(), roh.getIncident().getWt_id(), fieldMap);
		return result;
	}

	private Map<WorldTracerField, List<String>> createFieldMap(WtqRequestOhd roh) {
		if (roh == null) {
			return null;
		}

		Map<WorldTracerField, List<String>> result = new EnumMap<WorldTracerField, List<String>>(WorldTracerField.class);
		if(roh.getIncident() != null && roh.getIncident().getPassenger_list() != null && roh.getIncident().getPassenger_list().size() > 0) {
			addIncidentFieldEntry(WorldTracerField.NM, ((Passenger)roh.getIncident().getPassenger_list().get(0)).getLastname(), result);
		}
		if(roh.getTeletypes() != null) {
			for(String tt : roh.getTeletypes()) {
				addIncidentFieldEntry(WorldTracerField.TX, tt, result);
			}
		}
		
		addIncidentFieldEntry(WorldTracerField.AG, getAgentEntry(roh.getAgent()), result);
		
		return result;

	}

	private Map<WorldTracerField, List<String>> createCloseFieldMap(OHD ohd) {
		Map<WorldTracerField, List<String>> result = new EnumMap<WorldTracerField, List<String>>(WorldTracerField.class);
		addIncidentFieldEntry(WorldTracerField.AG, getAgentEntry(ohd.getAgent()), result);
		return result;
	}

	private static String getAgentEntry(Agent ag) {
		return (ag.getUsername().length() > 8 ? ag.getUsername().substring(0, 8) : ag.getUsername()) + "/"
				+ ag.getCompanycode_ID();
	}

	private Map<WorldTracerField, List<String>> createFwdFieldMap(WtqFwd fwd) throws WorldTracerException {
		if (fwd == null) {
			return null;
		}

		Map<WorldTracerField, List<String>> result = new EnumMap<WorldTracerField, List<String>>(WorldTracerField.class);
		addIncidentFieldEntry(WorldTracerField.AG, getAgentEntry(fwd.getAgent()), result);

		addIncidentFieldEntry(WorldTracerField.XT, fwd.getFwdExpediteNum(), result);

		if (fwd.getFwdName() != null) {
			for (String name : fwd.getFwdName()) {
				addIncidentFieldEntry(WorldTracerField.NM, name.trim(), result);
			}
		}
		
		if (fwd.getTeletypes() != null) {
			for (String tt : fwd.getTeletypes()) {
				addIncidentFieldEntry(WorldTracerField.TX, tt.trim(), result);
			}
		}

		addIncidentFieldEntry(WorldTracerField.SI, fwd.getSupInfo(), result);

		String fw = null;
		if (fwd.getItinerary() != null) {
			for (WtqSegment itin : fwd.getItinerary()) {
				if (itin.getAirline() == null || itin.getAirline().trim().length() <= 0 || itin.getFlightnum() == null
						|| itin.getFlightnum().trim().length() <= 0 || itin.getLegfrom() == null
						|| itin.getLegfrom().trim().length() <= 0 || itin.getLegto() == null
						|| itin.getLegto().trim().length() <= 0 || itin.getDepartdate() == null) {
					continue;
				}
				String fd = itin.getAirline() + itin.getFlightnum() + "/" + ITIN_DATE_FORMAT.format(itin.getDepartdate());
				addIncidentFieldEntry(WorldTracerField.FO, fd, result);
				fw = itin.getLegto() + itin.getAirline();
				addIncidentFieldEntry(WorldTracerField.FW, fw, result);
			}
		}
		List<String> foo = result.get(WorldTracerField.FW);
		if(foo != null && foo.size() > 0) {
			foo.set(foo.size() - 1, fwd.getFwdDestinationStation() + fwd.getFwdDestinationAirline());
		}
		else {
			throw new WorldTracerException("invalid forward itinerary");
		}

		return result;
	}

	private Map<WorldTracerField, List<String>> createFieldMap(OHD ohd) throws WorldTracerException {
		if (ohd == null) {
			return null;
		}

		Map<WorldTracerField, List<String>> result = new EnumMap<WorldTracerField, List<String>>(WorldTracerField.class);

		if (ohd.getColor() == null || ohd.getColor().trim().length() <= 0 || ohd.getType() == null
				|| ohd.getType().trim().length() != 2) {
			throw new WorldTracerException("OHD missing color / type");
		}

		String colorType = "";
		if("TD".equals(ohd.getColor().trim())) {
			colorType = "BN" + ohd.getType().trim();
		}
		else {
			colorType = ohd.getColor().trim() + ohd.getType().trim();
		}
		
		String desc1 = mapXDesc(XDescElementsBMO.getXdescelementcode(ohd.getXdescelement_ID_1()));
		String desc2 = mapXDesc(XDescElementsBMO.getXdescelementcode(ohd.getXdescelement_ID_2()));
		String desc3 = mapXDesc(XDescElementsBMO.getXdescelementcode(ohd.getXdescelement_ID_3()));
		
		colorType += getDescString(desc1, desc2, desc3);

		addIncidentFieldEntry(WorldTracerField.CT, colorType, result);

		if (ohd.getClaimnum() != null && ohd.getClaimnum().trim().length() > 0) {
			addClaimCheckNum(ohd.getClaimnum(), result, ohd.getAgent().getCompanycode_ID());
		}

		addIncidentFieldEntry(WorldTracerField.BI, ohd.getManufacturer(), result);

		addIncidentFieldEntry(WorldTracerField.PR, ohd.getRecord_locator(), result);

		addIncidentFieldEntry(WorldTracerField.SL, ohd.getStorage_location(), result);

		if (ohd.getMembership() != null) {
			addIncidentFieldEntry(WorldTracerField.FL, ohd.getMembership().getMembershipnum(), result);
		}

		if (ohd.getItinerary() == null || ohd.getItinerary().size() == 0) {
			throw new WorldTracerException("OHD missing itinerary");
		}
		for (OHD_Itinerary itin : (Iterable<OHD_Itinerary>) ohd.getItinerary()) {
			getOhdItineraryInfo(itin, result);
		}
		
		addIncidentFieldEntry(WorldTracerField.NM, ohd.getLastname(), result);
		
		addIncidentFieldEntry(WorldTracerField.PT, ohd.getFirstname(), result);

		for (OHD_Passenger p : (Iterable<OHD_Passenger>) ohd.getPassengers()) {
			getOhdPaxInfo(p, result);
		}

		if (ohd.getItems() != null) {
			Map<String, List<String>> temp = new HashMap<String, List<String>>();
			for (OHD_Inventory inv : (Iterable<OHD_Inventory>) ohd.getItems()) {
				// TODO update for ContentRule
				OHD_CategoryType cat = CategoryBMO.getCategory(inv.getOHD_categorytype_ID());
				if(cat == null) {
					continue;
				}
				String category = cat.getWtCategory();
				String contents = inv.getDescription().trim().toUpperCase();
				if (category == null || contents == null || category.trim().length() == 0
						|| contents.trim().length() == 0)
					continue;
				if (temp.get(category) == null) {
					temp.put(category, new ArrayList<String>());
				}
				temp.get(category).add(contents);
			}
			if (temp.size() > 0) {
				//pass 0 for bagnum so no numbers on field
				String entry = ContentRule.buildEntry(temp, 0);
				if (entry != null) {
					addIncidentFieldEntry(WorldTracerField.CC, entry, result);
				}
			}
		}

		addIncidentFieldEntry(WorldTracerField.AG, DefaultWorldTracerService.getAgentEntry(ohd.getAgent()), result);
		return result;

	}

	private String getDescString(String... descs) {
		// TODO Auto-generated method stub
		//need to remove duplicates 
		Set<String> foo = new HashSet<String>(Arrays.asList(descs));
		String result = "";
		boolean hasMat = false;
		for(String desc : foo) {
			if(wt_mats.contains(desc)) {
				if(!hasMat) {
					result += desc;
					hasMat = true;
				}
			}
			else {
				result += desc;
			}
		}
		if(result.length() > 3) {
			return result.substring(0, 3);
		}
		else if (result.length() == 3) {
			return result;
		}
		else if (result.length() == 2) {
			return result + "X";
		}
		else if (result.length() == 1) {
			return result + "XX";
		}
		else {
			return "XXX";
		}
	}

	private String mapXDesc(String code) {
		if (code == null || !wt_descs.contains(code)) {
			return "X";
		}
		return code;
	}

	private void getOhdPaxInfo(OHD_Passenger p, Map<WorldTracerField, List<String>> result) {
		OHD_Address address = p.getAddress(0);
		if (p.getLastname() == null || p.getLastname().trim().length() <= 0) {
			if(!result.containsKey(WorldTracerField.TA) && address != null) {
				addWtOhdAddress(result, address, WorldTracerField.TA);
			}
		}
		else {			
			// add the name
			addIncidentFieldEntry(WorldTracerField.NM, p.getLastname().trim(), result);
	
			// add the initials
			String initials = null;
			if (p.getFirstname() != null && p.getFirstname().trim().length() > 0) {
				initials = p.getFirstname().trim().substring(0, 1) + p.getLastname().trim().substring(0, 1);
			} else {
				initials = p.getLastname().trim().substring(0, 1);
			}
			addIncidentFieldEntry(WorldTracerField.IT, initials, result);
	
			// add the passenger title (salutation)
			addIncidentFieldEntry(WorldTracerField.PT, p.getFirstname(), result);
	
			// add permanent address
			if (address != null && !result.containsKey(WorldTracerField.PA)) {
				addWtOhdAddress(result,address, WorldTracerField.PA);
			}
		}
		if(address != null) {

			// add email
			addIncidentFieldEntry(WorldTracerField.EA, wtEscape(address.getEmail()), result);

			// add home phone
			addIncidentFieldEntry(WorldTracerField.PN, wtPhone(address.getHomephone()), result);

			// add work phone
			addIncidentFieldEntry(WorldTracerField.TP, wtPhone(address.getWorkphone()), result);

			// add cell phone
			addIncidentFieldEntry(WorldTracerField.CP, wtPhone(address.getMobile()), result);

			// add fax num
			addIncidentFieldEntry(WorldTracerField.FX, wtPhone(address.getAltphone()), result);

			// add country
			addIncidentFieldEntry(WorldTracerField.CO, address.getCountrycode_ID(), result);
		}

	}

	private void addWtOhdAddress(Map<WorldTracerField, List<String>> result, OHD_Address address, WorldTracerField addressField) {
		ArrayList<String> addr1Pieces = new ArrayList<String>();
		ArrayList<String> addr2Pieces = new ArrayList<String>();
		if (address.getAddress1() != null)
			addr1Pieces.add(address.getAddress1().trim());
		if (address.getAddress2() != null)
			addr1Pieces.add(address.getAddress2().trim());
		if (address.getCity() != null)
			addr2Pieces.add(address.getCity().trim());
		if (address.getState() != null)
			addr2Pieces.add(address.getState().trim());
		else if (address.getProvince() != null)
			addr2Pieces.add(address.getProvince().trim());
		if (address.getZip() != null)
			addr2Pieces.add(address.getZip().trim());
		String value = StringUtils.join(addr1Pieces, " ").replaceAll("\\s+", " ");
		addIncidentFieldEntry(addressField, wtClear(value), result);
		value = StringUtils.join(addr2Pieces, " ").replaceAll("\\s+", " ");
		addIncidentFieldEntry(addressField, wtClear(value), result);
	}
	
	private void addWtIncAddress(Map<WorldTracerField, List<String>> result, Address address, WorldTracerField addressField) {
		ArrayList<String> addr1Pieces = new ArrayList<String>();
		ArrayList<String> addr2Pieces = new ArrayList<String>();
		if (address.getAddress1() != null)
			addr1Pieces.add(address.getAddress1().trim());
		if (address.getAddress2() != null)
			addr1Pieces.add(address.getAddress2().trim());
		if (address.getCity() != null)
			addr2Pieces.add(address.getCity().trim());
		if (address.getState() != null)
			addr2Pieces.add(address.getState().trim());
		else if (address.getProvince() != null)
			addr2Pieces.add(address.getProvince().trim());
		if (address.getZip() != null)
			addr2Pieces.add(address.getZip().trim());
		String value = StringUtils.join(addr1Pieces, " ").replaceAll("\\s+", " ");
		addIncidentFieldEntry(addressField, wtClear(value), result);
		value = StringUtils.join(addr2Pieces, " ").replaceAll("\\s+", " ");
		addIncidentFieldEntry(addressField, wtClear(value), result);
	}

	protected Map<WorldTracerField, List<String>> createFieldMap(Incident ntIncident) throws WorldTracerException {

		if (ntIncident == null) {
			return null;
		}
		Map<WorldTracerField, List<String>> result = new EnumMap<WorldTracerField, List<String>>(WorldTracerField.class);

		if (ntIncident.getPassenger_list() != null) {
			for (Passenger p : (List<Passenger>) ntIncident.getPassenger_list()) {
				getPassengerInfo(p, result);
			}
		}

		// num passengers
		addIncidentFieldEntry(WorldTracerField.NP, Integer.toString(ntIncident.getNumpassengers()), result);

		if (ntIncident.getItinerary_list() != null) {
			for (Itinerary i : (List<Itinerary>) ntIncident.getItinerary_list()) {
				getItineraryInfo(i, result);
			}
		}

		if (ntIncident.getClaimcheck_list() != null) {
			for (Incident_Claimcheck ic : (List<Incident_Claimcheck>) ntIncident.getClaimcheck_list()) {
				if (ic.getClaimchecknum() != null && ic.getClaimchecknum().trim().length() > 0) {
					addClaimCheckNum(ic.getClaimchecknum(), result, ntIncident.getStationassigned().getCompany()
							.getCompanyCode_ID());
				}
			}
		}
		// contents category can only have one entry in WT, but you can use up
		// to two lines for each
		// you can have 12 total categories (see ContentRule class)
		if (ntIncident.getItemlist() != null) {
			int bagCount = 1;
			for (Item i : (List<Item>) ntIncident.getItemlist()) {
				getItemInfo(i, result, ntIncident.getStationassigned().getCompany().getCompanyCode_ID());
				if (i.getInventorylist() != null) {

					Map<String, List<String>> temp = new HashMap<String, List<String>>();
					for (Item_Inventory inv : (List<Item_Inventory>) i.getInventorylist()) {
						String category = CategoryBMO.getCategory(inv.getCategorytype_ID()).getWtCategory();
						String contents = inv.getDescription().trim().toUpperCase();
						if (category == null || contents == null || category.trim().length() == 0
								|| contents.trim().length() == 0)
							continue;
						if (temp.get(category) == null) {
							temp.put(category, new ArrayList<String>());
						}
						temp.get(category).add(contents);
					}
					if (temp.size() > 0) {
						String entry = ContentRule.buildEntry(temp, bagCount);
						if (entry != null) {
							addIncidentFieldEntry(WorldTracerField.CC, entry, result);
							bagCount += 1;
						}
					}
				}
			}
		}

		if (ntIncident.getTicketnumber() != null && ntIncident.getTicketnumber().trim().length() > 0) {
			addIncidentFieldEntry(WorldTracerField.TK, ntIncident.getTicketnumber().trim(), result);
			addIncidentFieldEntry(WorldTracerField.PB, ntIncident.getTicketnumber().trim(), result);

		} else {
			addIncidentFieldEntry(WorldTracerField.PB, "0000", result);
		}
		addIncidentFieldEntry(WorldTracerField.AG, DefaultWorldTracerService.getAgentEntry(ntIncident.getAgent()),
				result);
		return result;
	}

	private Map<WorldTracerField, List<String>> createCloseFieldMap(Incident incident) {
		Map<WorldTracerField, List<String>> result = new EnumMap<WorldTracerField, List<String>>(WorldTracerField.class);
		String cs_fmt = "%02d %s/%s%1.2f";
		int claimCount = 0;
		if (incident.getClaims() != null) {
			String cost;

			for (Claim claim : (Iterable<Claim>) incident.getClaims()) {
				if (claim.getExpenses() != null) {
					for (ExpensePayout expense : (Iterable<ExpensePayout>) claim.getExpenses()) {
						if (expense.getApproval_date() != null && expense.getCurrency_ID() != null) {
							claimCount++;
							if ("ADV".equals(expense.getPaycode())) {
								cost = String.format(cs_fmt, claimCount, "A", expense.getCurrency_ID(), expense
										.getCheckamt());
							} else if ("DEL".equals(expense.getPaycode())) {
								cost = String.format(cs_fmt, claimCount, "D", expense.getCurrency_ID(), expense
										.getCheckamt());
							} else if ("FIN".equals(expense.getPaycode())) {
								cost = String.format(cs_fmt, claimCount, "F", expense.getCurrency_ID(), expense
										.getCheckamt());
							} else if ("INS".equals(expense.getPaycode())) {
								cost = String.format(cs_fmt, claimCount, "I", expense.getCurrency_ID(), expense
										.getCheckamt());
							} else {
								cost = String.format(cs_fmt, claimCount, "X", expense.getCurrency_ID(), expense
										.getCheckamt());
							}
							addIncidentFieldEntry(WorldTracerField.CS, cost, result);
						}
					}
				}
			}
		}
		// see if we added a CS
		if (claimCount == 0) {
			addIncidentFieldEntry(WorldTracerField.CS, "01 X/USD0.00", result);
		}

		if (incident.getLoss_code() != 0) {
			addIncidentFieldEntry(WorldTracerField.RL, Integer.toString(incident.getLoss_code()), result);
			Company_specific_irregularity_code csic = LossCodeBMO.getLossCode(incident.getLoss_code(),
					TracingConstants.LOST_DELAY, TracingConstants.DEFAULT_LOCALE, AdminUtils.getCompany(wtCompanyCode));
			addIncidentFieldEntry(WorldTracerField.RC, csic.getDescription(), result);
		} else {
			addIncidentFieldEntry(WorldTracerField.RL, "79", result);
			addIncidentFieldEntry(WorldTracerField.RC, "Created in error", result);
		}
		if (incident.getFaultstation() != null) {
			addIncidentFieldEntry(WorldTracerField.FS, StationBMO.getStationByCode(incident.getFaultstationcode())
					.getWt_stationcode(), result);
		} else {
			addIncidentFieldEntry(WorldTracerField.FS, incident.getStationcreated().getWt_stationcode(), result);
		}
		addIncidentFieldEntry(WorldTracerField.AG, DefaultWorldTracerService.getAgentEntry(incident.getAgent()), result);
		return result;
	}

	protected void getItemInfo(Item item, Map<WorldTracerField, List<String>> result, String companyCode) {

		if (item.getColor() == null || item.getColor().trim().length() <= 0 || item.getBagtype() == null
				|| item.getBagtype().trim().length() != 2) {
			return;
		}
		String colorType = "";
		if("TD".equals(item.getColor().trim())) {
			colorType = "BN" + item.getBagtype().trim();
		}
		else {
			colorType = item.getColor().trim() + item.getBagtype().trim();
		}
		
		String desc1 = mapXDesc(XDescElementsBMO.getXdescelementcode(item.getXdescelement_ID_1()));
		String desc2 = mapXDesc(XDescElementsBMO.getXdescelementcode(item.getXdescelement_ID_2()));
		String desc3 = mapXDesc(XDescElementsBMO.getXdescelementcode(item.getXdescelement_ID_3()));
		
		colorType += getDescString(desc1, desc2, desc3);
		
		addIncidentFieldEntry(WorldTracerField.CT, colorType, result);

		addIncidentFieldEntry(WorldTracerField.BI, item.getManufacturer(), result);
	}

	private void addClaimCheckNum(String claimCheck, Map<WorldTracerField, List<String>> result, String companyCode) {
		String bagTagString = null;
		try {
			bagTagString = LookupAirlineCodes.getTwoCharacterBagTag(claimCheck.trim());
		} catch (BagtagException e) {
			// couldn't figure out the tag.
			Pattern wt_patt = Pattern.compile("([a-zA-Z0-9]{2})(\\d{1,6})");
			Matcher m = wt_patt.matcher(claimCheck.trim());
			if (m.find() && LookupAirlineCodes.getThreeDigitTicketingCode(m.group(1)) != null) {
				bagTagString = String.format("%s%06d", m.group(1), Integer.parseInt(m.group(2)));
			} else {
				Pattern base_patt = Pattern.compile("(\\d{1,6})(\\D|$)");
				m = base_patt.matcher(claimCheck.trim());
				if (m.find()) {
					bagTagString = companyCode + m.group(1);
				}
			}
		}
		addIncidentFieldEntry(WorldTracerField.TN, bagTagString, result);
	}

	/**
	 * 
	 * @param itin
	 * @param result
	 */
	protected void getItineraryInfo(Itinerary itin, Map<WorldTracerField, List<String>> result) {

		// make sure it's a valid itinerary
		if (itin.getAirline() == null || itin.getAirline().trim().length() <= 0 || itin.getFlightnum() == null
				|| itin.getFlightnum().trim().length() <= 0 || itin.getLegfrom() == null
				|| itin.getLegfrom().trim().length() <= 0 || itin.getLegto() == null
				|| itin.getLegto().trim().length() <= 0 || itin.getDepartdate() == null) {
			return;
		}

		String fd = itin.getAirline() + itin.getFlightnum() + "/" + ITIN_DATE_FORMAT.format(itin.getDepartdate());
		if (itin.getItinerarytype() == TracingConstants.BAGGAGE_ROUTING) {
			addIncidentFieldEntry(WorldTracerField.BR, fd, result);
		} else if (itin.getItinerarytype() == TracingConstants.PASSENGER_ROUTING) {
			addIncidentFieldEntry(WorldTracerField.FD, fd, result);
			List<String> routing = result.get(WorldTracerField.RT);
			if (routing == null || !routing.get(routing.size() - 1).equalsIgnoreCase(itin.getLegfrom().trim())) {
				addIncidentFieldEntry(WorldTracerField.RT, itin.getLegfrom().trim(), result);
			}
			addIncidentFieldEntry(WorldTracerField.RT, itin.getLegto().trim(), result);
		}

	}

	protected void getOhdItineraryInfo(OHD_Itinerary itin, Map<WorldTracerField, List<String>> result) {
		// make sure it's a valid itinerary
		if (itin.getAirline() == null || itin.getAirline().trim().length() <= 0 || itin.getFlightnum() == null
				|| itin.getFlightnum().trim().length() <= 0 || itin.getLegfrom() == null
				|| itin.getLegfrom().trim().length() <= 0 || itin.getLegto() == null
				|| itin.getLegto().trim().length() <= 0 || itin.getDepartdate() == null) {
			return;
		}

		String fd = itin.getAirline() + itin.getFlightnum() + "/" + ITIN_DATE_FORMAT.format(itin.getDepartdate());

		addIncidentFieldEntry(WorldTracerField.FD, fd, result);
		List<String> routing = result.get(WorldTracerField.RT);
		if (routing == null || !routing.get(routing.size() - 1).equalsIgnoreCase(itin.getLegfrom().trim())) {
			addIncidentFieldEntry(WorldTracerField.RT, itin.getLegfrom().trim(), result);
		}
		addIncidentFieldEntry(WorldTracerField.RT, itin.getLegto().trim(), result);
	}

	protected void getPassengerInfo(Passenger p, Map<WorldTracerField, List<String>> result)
			throws WorldTracerException {
		Address address = p.getAddress(0);
		if(address != null && address.getIs_permanent() == 1) {
			result.remove(WorldTracerField.PA);
			addWtIncAddress(result, address, WorldTracerField.PA);
		}
		if (p.getLastname() == null || p.getLastname().trim().length() <= 0) {
			if(!result.containsKey(WorldTracerField.TA) && address != null && address.getIs_permanent() != 1) {
				addWtIncAddress(result, address, WorldTracerField.TA);
			}
		}
		else {			
			// add the name
			addIncidentFieldEntry(WorldTracerField.NM, p.getLastname().trim(), result);
	
			// add the initials
			String initials = null;
			if (p.getFirstname() != null && p.getFirstname().trim().length() > 0) {
				initials = p.getFirstname().trim().substring(0, 1) + p.getLastname().trim().substring(0, 1);
			} else {
				initials = p.getLastname().trim().substring(0, 1);
			}
			addIncidentFieldEntry(WorldTracerField.IT, initials, result);
	
			// add the passenger title (salutation)
			String title = StringUtils.join(" ", p.getSalutationdesc() != null ? p.getSalutationdesc() : "", p.getFirstname() != null ? p.getFirstname() : "");
			addIncidentFieldEntry(WorldTracerField.PT, title, result);
	
			// add the frequent flier class status
			addIncidentFieldEntry(WorldTracerField.PS, p.getAirlinememstatus(), result);
	
			// add the frequent flier num
			addIncidentFieldEntry(WorldTracerField.FL, p.getAirlinememnumber(), result);
	
			// add permanent address
			if (address != null && !result.containsKey(WorldTracerField.PA)) {
				addWtIncAddress(result,address, WorldTracerField.PA);
			}
		}
		if(address != null) {

			// add email
			addIncidentFieldEntry(WorldTracerField.EA, wtEscape(address.getEmail()), result);

			// add home phone
			addIncidentFieldEntry(WorldTracerField.PN, wtPhone(address.getHomephone()), result);

			// add work phone
			addIncidentFieldEntry(WorldTracerField.TP, wtPhone(address.getWorkphone()), result);

			// add cell phone
			addIncidentFieldEntry(WorldTracerField.CP, wtPhone(address.getMobile()), result);

			// add fax num
			addIncidentFieldEntry(WorldTracerField.FX, wtPhone(address.getAltphone()), result);

			// add country
			addIncidentFieldEntry(WorldTracerField.CO, address.getCountrycode_ID(), result);
		}
	}

	protected void addIncidentFieldEntry(WorldTracerField key, String value, Map<WorldTracerField, List<String>> result) {
		if (value == null || value.trim().length() <= 0) {
			return;
		}

		List<String> entryList = result.get(key);
		if (entryList == null) {
			entryList = new ArrayList<String>();
			entryList.add(value);
			result.put(key, entryList);
		} else {
			entryList.add(value);
		}
	}

	public void setWtConnector(WorldTracerConnector wtConnector) {
		this.wtConnector = wtConnector;
	}

	private String wtPhone(String rawText) {
		return rawText != null ? rawText.replaceAll("\\D", "") : null;
	}

	private String wtClear(String rawText) {
		return rawText != null ? rawText.replaceAll("[" + FIELD_SEP + ENTRY_SEP + "]", " ") : null;
	}

	private String wtEscape(String rawText) {
		return rawText != null ? rawText.replace("@", "/A/").replace(".", "/D/").replace("_", "/U/").replace("~", "/T/").replace("+",
				"/P/") : null;
	}

	public void setWtCompanyCode(String wtCompanyCode) {
		this.wtCompanyCode = wtCompanyCode;
	}

}
