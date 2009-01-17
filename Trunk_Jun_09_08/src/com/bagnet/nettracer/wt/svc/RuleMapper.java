package com.bagnet.nettracer.wt.svc;

import java.util.EnumMap;

import com.bagnet.nettracer.wt.svc.WorldTracerService.TxType;
import com.bagnet.nettracer.wt.svc.WorldTracerService.WorldTracerField;

public interface RuleMapper {

	public EnumMap<WorldTracerField,WorldTracerRule<String>> getRule(TxType txType);
	
}
