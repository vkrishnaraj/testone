package aero.nettracer.serviceprovider.wt_1_0.services.wtrweb.service;

import java.util.EnumMap;

import aero.nettracer.serviceprovider.wt_1_0.services.DefaultWorldTracerService.TxType;
import aero.nettracer.serviceprovider.wt_1_0.services.DefaultWorldTracerService.WorldTracerField;

public interface RuleMapper {

	public EnumMap<WorldTracerField,WorldTracerRule<String>> getRule(TxType txType);
	
}
