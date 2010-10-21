package com.bagnet.clients.defaul.wt;

import java.util.EnumMap;

import com.bagnet.nettracer.wt.svc.BasicRule;
import com.bagnet.nettracer.wt.svc.ContentAmendRule;
import com.bagnet.nettracer.wt.svc.ContentRule;
import com.bagnet.nettracer.wt.svc.EmailRule;
import com.bagnet.nettracer.wt.svc.NumberedLinesRule;
import com.bagnet.nettracer.wt.svc.RuleMapper;
import com.bagnet.nettracer.wt.svc.SameLineRule;
import com.bagnet.nettracer.wt.svc.WorldTracerRule;
import com.bagnet.nettracer.wt.svc.WorldTracerRule.Format;
import com.bagnet.nettracer.wt.svc.WorldTracerService.TxType;
import com.bagnet.nettracer.wt.svc.WorldTracerService.WorldTracerField;

public class DefaultWorldTracerRuleMap implements RuleMapper {
	public final EnumMap<WorldTracerField, WorldTracerRule<String>> INC_FIELD_RULES;
	public final EnumMap<WorldTracerField, WorldTracerRule<String>> OHD_FIELD_RULES;

	public final EnumMap<WorldTracerField, WorldTracerRule<String>> CAH_FIELD_RULES;
	public final EnumMap<WorldTracerField, WorldTracerRule<String>> COH_FIELD_RULES;

	public final EnumMap<WorldTracerField, WorldTracerRule<String>> FWD_FIELD_RULES;

	public final EnumMap<WorldTracerField, WorldTracerRule<String>> ROH_FIELD_RULES;

	public final EnumMap<WorldTracerField, WorldTracerRule<String>> AMEND_OHD_FIELD_RULES;
	public final EnumMap<WorldTracerField, WorldTracerRule<String>> AMEND_AHL_FIELD_RULES;

	public final EnumMap<WorldTracerField, WorldTracerRule<String>> REQ_QOH_FIELD_RULES;
	public final EnumMap<WorldTracerField, WorldTracerRule<String>> BDO_FIELD_RULES;

	{
		BDO_FIELD_RULES = new EnumMap<WorldTracerField, WorldTracerRule<String>>(WorldTracerField.class);
		BDO_FIELD_RULES.put(WorldTracerField.NM, new SameLineRule(2, 16, 3, Format.ALPHA));
		BDO_FIELD_RULES.put(WorldTracerField.PA, new BasicRule(1, 57, 2, Format.FREE_FLOW));
		BDO_FIELD_RULES.put(WorldTracerField.PN, new BasicRule(1, 20, 2, Format.NUMERIC));
		
		INC_FIELD_RULES = new EnumMap<WorldTracerField, WorldTracerRule<String>>(WorldTracerField.class);
		INC_FIELD_RULES.put(WorldTracerField.CT, new BasicRule(7, 7, 10, Format.ALPHA_NUMERIC));
		INC_FIELD_RULES.put(WorldTracerField.FD, new SameLineRule(1, 14, 4, Format.ALPHA_NUMERIC));
		INC_FIELD_RULES.put(WorldTracerField.IT, new SameLineRule(1, 4, 3, Format.ALPHA));
		INC_FIELD_RULES.put(WorldTracerField.RT, new SameLineRule(3, 3, 5, Format.ALPHA_NUMERIC));
		INC_FIELD_RULES.put(WorldTracerField.NM, new SameLineRule(1, 16, 3, Format.ALPHA));
		INC_FIELD_RULES.put(WorldTracerField.PT, new BasicRule(1, 25, 1, Format.ALPHA));
		INC_FIELD_RULES.put(WorldTracerField.PS, new BasicRule(1, 25, 1, Format.FREE_FLOW));
		INC_FIELD_RULES.put(WorldTracerField.FL, new BasicRule(1, 25, 1, Format.ALPHA_NUMERIC));
		INC_FIELD_RULES.put(WorldTracerField.PA, new BasicRule(1, 57, 2, Format.FREE_FLOW));
		INC_FIELD_RULES.put(WorldTracerField.TA, new BasicRule(1, 57, 2, Format.FREE_FLOW));
		INC_FIELD_RULES.put(WorldTracerField.EA, new EmailRule(1, 44, 1, Format.FREE_FLOW));
		INC_FIELD_RULES.put(WorldTracerField.CO, new BasicRule(2, 5, 1, Format.ALPHA_NUMERIC));
		INC_FIELD_RULES.put(WorldTracerField.PN, new BasicRule(1, 20, 2, Format.NUMERIC));
		INC_FIELD_RULES.put(WorldTracerField.TP, new BasicRule(1, 20, 2, Format.NUMERIC));
		INC_FIELD_RULES.put(WorldTracerField.CP, new BasicRule(1, 20, 2, Format.NUMERIC));
		INC_FIELD_RULES.put(WorldTracerField.FX, new BasicRule(1, 20, 2, Format.NUMERIC));
		INC_FIELD_RULES.put(WorldTracerField.NP, new BasicRule(1, 25, 1, Format.FREE_FLOW));
		INC_FIELD_RULES.put(WorldTracerField.BR, new SameLineRule(1, 14, 4, Format.ALPHA_NUMERIC));
		INC_FIELD_RULES.put(WorldTracerField.TN, new BasicRule(8, 10, 10, Format.ALPHA_NUMERIC));
		INC_FIELD_RULES.put(WorldTracerField.BI, new BasicRule(2, 57, 10, Format.ALPHA_NUMERIC));
		INC_FIELD_RULES.put(WorldTracerField.HC, new BasicRule(1, 1, 1, Format.ALPHA));
		INC_FIELD_RULES.put(WorldTracerField.FS, new BasicRule(3, 3, 1, Format.FREE_FLOW));
		INC_FIELD_RULES.put(WorldTracerField.PB, new BasicRule(1, 15, 1, Format.ALPHA_NUMERIC));
		INC_FIELD_RULES.put(WorldTracerField.CC, new ContentRule(1, 57, 10, Format.CONTENT_FIELD));
		INC_FIELD_RULES.put(WorldTracerField.AG, new BasicRule(1, 12, 1, Format.FREE_FLOW));
		INC_FIELD_RULES.put(WorldTracerField.PR, new BasicRule(1, 12, 1, Format.FREE_FLOW));
		
		OHD_FIELD_RULES = new EnumMap<WorldTracerField, WorldTracerRule<String>>(WorldTracerField.class);
		OHD_FIELD_RULES.put(WorldTracerField.CT, new SameLineRule(7, 7, 1, Format.ALPHA_NUMERIC));
		OHD_FIELD_RULES.put(WorldTracerField.TN, new BasicRule(8, 10, 1, Format.ALPHA_NUMERIC));
		OHD_FIELD_RULES.put(WorldTracerField.PA, new BasicRule(1, 57, 2, Format.FREE_FLOW));
		OHD_FIELD_RULES.put(WorldTracerField.TA, new BasicRule(1, 57, 2, Format.FREE_FLOW));
		OHD_FIELD_RULES.put(WorldTracerField.AB, new BasicRule(1, 57, 2, Format.FREE_FLOW));
		OHD_FIELD_RULES.put(WorldTracerField.FD, new SameLineRule(1, 14, 4, Format.ALPHA_NUMERIC));
		OHD_FIELD_RULES.put(WorldTracerField.RT, new SameLineRule(3, 3, 5, Format.ALPHA_NUMERIC));
		OHD_FIELD_RULES.put(WorldTracerField.AG, new BasicRule(1, 12, 1, Format.FREE_FLOW));
		OHD_FIELD_RULES.put(WorldTracerField.CP, new BasicRule(1, 20, 2, Format.NUMERIC));
		OHD_FIELD_RULES.put(WorldTracerField.FL, new BasicRule(1, 25, 1, Format.ALPHA_NUMERIC));
		OHD_FIELD_RULES.put(WorldTracerField.PN, new BasicRule(1, 20, 2, Format.NUMERIC));
		OHD_FIELD_RULES.put(WorldTracerField.TP, new BasicRule(1, 20, 2, Format.NUMERIC));
		OHD_FIELD_RULES.put(WorldTracerField.FX, new BasicRule(1, 20, 2, Format.NUMERIC));
		OHD_FIELD_RULES.put(WorldTracerField.NM, new SameLineRule(1, 16, 3, Format.ALPHA));
		OHD_FIELD_RULES.put(WorldTracerField.EA, new EmailRule(1, 44, 1, Format.FREE_FLOW));
		OHD_FIELD_RULES.put(WorldTracerField.SL, new BasicRule(1, 32, 1, Format.ALPHA_NUMERIC));
		OHD_FIELD_RULES.put(WorldTracerField.BI, new BasicRule(2, 57, 1, Format.ALPHA_NUMERIC));
		OHD_FIELD_RULES.put(WorldTracerField.PT, new BasicRule(1, 25, 1, Format.ALPHA));
		OHD_FIELD_RULES.put(WorldTracerField.CC, new ContentRule(1, 57, 10, Format.CONTENT_FIELD));
		OHD_FIELD_RULES.put(WorldTracerField.PR, new BasicRule(1, 12, 1, Format.FREE_FLOW));
		
		CAH_FIELD_RULES = new EnumMap<WorldTracerField, WorldTracerRule<String>>(WorldTracerField.class);
		CAH_FIELD_RULES.put(WorldTracerField.CS, new BasicRule(6, 16, 5, Format.ALPHA_NUMERIC));
		CAH_FIELD_RULES.put(WorldTracerField.NM, new SameLineRule(2, 16, 3, Format.ALPHA));
		CAH_FIELD_RULES.put(WorldTracerField.RL, new BasicRule(2, 2, 1, Format.NUMERIC));
		CAH_FIELD_RULES.put(WorldTracerField.FS, new BasicRule(3, 3, 1, Format.FREE_FLOW));
		CAH_FIELD_RULES.put(WorldTracerField.RC, new BasicRule(1, 57, 1, Format.FREE_FLOW));
		CAH_FIELD_RULES.put(WorldTracerField.AG, new BasicRule(1, 12, 1, Format.FREE_FLOW));
		
		COH_FIELD_RULES = new EnumMap<WorldTracerField, WorldTracerRule<String>>(WorldTracerField.class);
		COH_FIELD_RULES.put(WorldTracerField.CS, new BasicRule(6, 16, 5, Format.ALPHA_NUMERIC));
		COH_FIELD_RULES.put(WorldTracerField.AG, new BasicRule(1, 12, 1, Format.FREE_FLOW));
		
		FWD_FIELD_RULES = new EnumMap<WorldTracerField, WorldTracerRule<String>>(WorldTracerField.class);
		FWD_FIELD_RULES.put(WorldTracerField.AG, new BasicRule(1, 12, 1, Format.FREE_FLOW));
		FWD_FIELD_RULES.put(WorldTracerField.TN, new BasicRule(8, 10, 7, Format.ALPHA_NUMERIC));
		FWD_FIELD_RULES.put(WorldTracerField.XT, new BasicRule(8, 10, 7, Format.ALPHA_NUMERIC));
		FWD_FIELD_RULES.put(WorldTracerField.FO, new SameLineRule(1, 14, 4, Format.ALPHA_NUMERIC));
		FWD_FIELD_RULES.put(WorldTracerField.FW, new SameLineRule(5, 5, 5, Format.ALPHA_NUMERIC));
		FWD_FIELD_RULES.put(WorldTracerField.FB, new BasicRule(1, 2, 1, Format.NUMERIC));
		FWD_FIELD_RULES.put(WorldTracerField.FD, new SameLineRule(1, 14, 4, Format.ALPHA_NUMERIC));
		FWD_FIELD_RULES.put(WorldTracerField.FS, new BasicRule(3, 3, 1, Format.ALPHA_NUMERIC));
		FWD_FIELD_RULES.put(WorldTracerField.FT, new BasicRule(2, 2, 1, Format.ALPHA_NUMERIC));
		FWD_FIELD_RULES.put(WorldTracerField.HC, new BasicRule(1, 1, 1, Format.ALPHA));
		FWD_FIELD_RULES.put(WorldTracerField.NM, new SameLineRule(2, 16, 10, Format.ALPHA));
		FWD_FIELD_RULES.put(WorldTracerField.RC, new BasicRule(1, 55, 1, Format.FREE_FLOW));
		FWD_FIELD_RULES.put(WorldTracerField.RL, new BasicRule(2, 2, 1, Format.NUMERIC));
		FWD_FIELD_RULES.put(WorldTracerField.SI, new BasicRule(1, 55, 3, Format.FREE_FLOW));
		FWD_FIELD_RULES.put(WorldTracerField.TX, new BasicRule(5, 9, 10, Format.ALPHA_NUMERIC));
		
		ROH_FIELD_RULES = new EnumMap<WorldTracerField, WorldTracerRule<String>>(WorldTracerField.class);
		ROH_FIELD_RULES.put(WorldTracerField.AG, new BasicRule(1, 12, 1, Format.FREE_FLOW));
		ROH_FIELD_RULES.put(WorldTracerField.NM, new BasicRule(2, 16, 1, Format.ALPHA_NUMERIC));
		ROH_FIELD_RULES.put(WorldTracerField.FI, new BasicRule(1, 57, 1, Format.FREE_FLOW));
		ROH_FIELD_RULES.put(WorldTracerField.TX, new BasicRule(5, 9, 10, Format.ALPHA_NUMERIC));
		ROH_FIELD_RULES.put(WorldTracerField.SI, new BasicRule(1, 55, 3, Format.FREE_FLOW));
		
		REQ_QOH_FIELD_RULES = new EnumMap<WorldTracerField, WorldTracerRule<String>>(WorldTracerField.class);
		REQ_QOH_FIELD_RULES.put(WorldTracerField.TN, new BasicRule(8, 10, 10, Format.ALPHA_NUMERIC, false, true));
		REQ_QOH_FIELD_RULES.put(WorldTracerField.AG, new BasicRule(1, 12, 1, Format.FREE_FLOW, false, true));
		REQ_QOH_FIELD_RULES.put(WorldTracerField.NM, new BasicRule(2, 16, 1, Format.ALPHA_NUMERIC, false, true));
		REQ_QOH_FIELD_RULES.put(WorldTracerField.FI, new BasicRule(1, 57, 1, Format.FREE_FLOW, false, true));
		REQ_QOH_FIELD_RULES.put(WorldTracerField.TX, new BasicRule(5, 9, 10, Format.ALPHA_NUMERIC, false, true));
		
		AMEND_AHL_FIELD_RULES = new EnumMap<WorldTracerField, WorldTracerRule<String>>(WorldTracerField.class);
		AMEND_AHL_FIELD_RULES.put(WorldTracerField.CT, new NumberedLinesRule(7, 7, 10, Format.ALPHA_NUMERIC, false));
		AMEND_AHL_FIELD_RULES.put(WorldTracerField.FD, new SameLineRule(1, 14, 4, Format.ALPHA_NUMERIC));
		AMEND_AHL_FIELD_RULES.put(WorldTracerField.IT, new NumberedLinesRule(1, 4, 3, Format.ALPHA, false));
		AMEND_AHL_FIELD_RULES.put(WorldTracerField.RT, new SameLineRule(3, 3, 5, Format.ALPHA_NUMERIC));
		AMEND_AHL_FIELD_RULES.put(WorldTracerField.NM, new NumberedLinesRule(2, 16, 3, Format.ALPHA, true));
		AMEND_AHL_FIELD_RULES.put(WorldTracerField.PT, new BasicRule(1, 25, 1, Format.ALPHA));
		AMEND_AHL_FIELD_RULES.put(WorldTracerField.PS, new BasicRule(1, 25, 1, Format.FREE_FLOW));
		AMEND_AHL_FIELD_RULES.put(WorldTracerField.FL, new BasicRule(1, 25, 1, Format.ALPHA_NUMERIC));
		AMEND_AHL_FIELD_RULES.put(WorldTracerField.PA, new NumberedLinesRule(1, 57, 2, Format.FREE_FLOW, false));
		AMEND_AHL_FIELD_RULES.put(WorldTracerField.TA, new NumberedLinesRule(1, 57, 2, Format.FREE_FLOW, true));
		AMEND_AHL_FIELD_RULES.put(WorldTracerField.EA, new EmailRule(1, 44, 1, Format.FREE_FLOW));
		AMEND_AHL_FIELD_RULES.put(WorldTracerField.CO, new BasicRule(2, 5, 1, Format.ALPHA_NUMERIC));
		AMEND_AHL_FIELD_RULES.put(WorldTracerField.PN, new NumberedLinesRule(1, 20, 2, Format.NUMERIC, false));
		AMEND_AHL_FIELD_RULES.put(WorldTracerField.TP, new NumberedLinesRule(1, 20, 2, Format.NUMERIC, false));
		AMEND_AHL_FIELD_RULES.put(WorldTracerField.CP, new NumberedLinesRule(1, 20, 2, Format.NUMERIC, false));
		AMEND_AHL_FIELD_RULES.put(WorldTracerField.FX, new NumberedLinesRule(1, 20, 2, Format.NUMERIC, false));
		AMEND_AHL_FIELD_RULES.put(WorldTracerField.NP, new BasicRule(1, 25, 1, Format.FREE_FLOW));
		AMEND_AHL_FIELD_RULES.put(WorldTracerField.BR, new SameLineRule(1, 14, 4, Format.ALPHA_NUMERIC));
		AMEND_AHL_FIELD_RULES.put(WorldTracerField.TN, new NumberedLinesRule(8, 10, 10, Format.ALPHA_NUMERIC));
		AMEND_AHL_FIELD_RULES.put(WorldTracerField.BI, new NumberedLinesRule(2, 57, 10, Format.ALPHA_NUMERIC, true));
		AMEND_AHL_FIELD_RULES.put(WorldTracerField.HC, new BasicRule(1, 1, 1, Format.ALPHA));
		AMEND_AHL_FIELD_RULES.put(WorldTracerField.FS, new BasicRule(3, 3, 1, Format.FREE_FLOW));
		AMEND_AHL_FIELD_RULES.put(WorldTracerField.PB, new BasicRule(1, 15, 1, Format.ALPHA_NUMERIC));
		AMEND_AHL_FIELD_RULES.put(WorldTracerField.CC, new ContentAmendRule(1, 57, 10, Format.CONTENT_FIELD, true));
		AMEND_AHL_FIELD_RULES.put(WorldTracerField.AG, new BasicRule(1, 12, 1, Format.FREE_FLOW));
		
		AMEND_OHD_FIELD_RULES = new EnumMap<WorldTracerField, WorldTracerRule<String>>(WorldTracerField.class);
		AMEND_OHD_FIELD_RULES.put(WorldTracerField.CT, new SameLineRule(7, 7, 1, Format.ALPHA_NUMERIC));
		AMEND_OHD_FIELD_RULES.put(WorldTracerField.TN, new BasicRule(8, 10, 1, Format.ALPHA_NUMERIC));
		AMEND_OHD_FIELD_RULES.put(WorldTracerField.PA, new NumberedLinesRule(1, 57, 2, Format.FREE_FLOW, true));
		AMEND_OHD_FIELD_RULES.put(WorldTracerField.FD, new SameLineRule(1, 14, 4, Format.ALPHA_NUMERIC));
		AMEND_OHD_FIELD_RULES.put(WorldTracerField.RT, new SameLineRule(3, 3, 1, Format.ALPHA_NUMERIC));
		AMEND_OHD_FIELD_RULES.put(WorldTracerField.AG, new BasicRule(1, 12, 1, Format.FREE_FLOW));
		AMEND_OHD_FIELD_RULES.put(WorldTracerField.CC, new ContentAmendRule(1, 57, 10, Format.CONTENT_FIELD, false));
		AMEND_OHD_FIELD_RULES.put(WorldTracerField.CP, new NumberedLinesRule(1, 20, 2, Format.NUMERIC, true));
		AMEND_OHD_FIELD_RULES.put(WorldTracerField.FL, new BasicRule(1, 25, 1, Format.ALPHA_NUMERIC));
		AMEND_OHD_FIELD_RULES.put(WorldTracerField.NM, new NumberedLinesRule(2, 16, 3, Format.ALPHA, true));
		AMEND_OHD_FIELD_RULES.put(WorldTracerField.EA, new EmailRule(1, 44, 1, Format.FREE_FLOW));
		AMEND_OHD_FIELD_RULES.put(WorldTracerField.SL, new BasicRule(1, 32, 1, Format.ALPHA_NUMERIC));
		AMEND_OHD_FIELD_RULES.put(WorldTracerField.BI, new BasicRule(2, 57, 1, Format.FREE_FLOW));
		AMEND_OHD_FIELD_RULES.put(WorldTracerField.PT, new BasicRule(1, 25, 1, Format.ALPHA));
		AMEND_OHD_FIELD_RULES.put(WorldTracerField.AB, new BasicRule(1, 57, 2, Format.FREE_FLOW));
		AMEND_OHD_FIELD_RULES.put(WorldTracerField.PN, new NumberedLinesRule(1, 20, 2, Format.NUMERIC, false));
	}
	
	public EnumMap<WorldTracerField,WorldTracerRule<String>> getRule(TxType tx){
		switch (tx) {
		case AMEND_AHL:
			return AMEND_AHL_FIELD_RULES;
		case AMEND_OHD:
			return AMEND_OHD_FIELD_RULES;
		case CREATE_AHL:
			return INC_FIELD_RULES;
		case CREATE_OHD:
			return OHD_FIELD_RULES;
		case CLOSE_AHL:
			return CAH_FIELD_RULES;
		case CLOSE_OHD:
			return COH_FIELD_RULES;
		case FWD_GENERAL:
			return FWD_FIELD_RULES;
		case REQUEST_OHD:
			return ROH_FIELD_RULES;
		case REQUEST_QOH:
			return REQ_QOH_FIELD_RULES;
		case CREATE_BDO:
			return BDO_FIELD_RULES;
		default:
			return new EnumMap<WorldTracerField, WorldTracerRule<String>>(WorldTracerField.class);
		}
	}
}
