package aero.nettracer.serviceprovider.wt_1_0.services.wtrweb.service;

import java.util.EnumMap;

import aero.nettracer.serviceprovider.wt_1_0.services.wtrweb.service.WorldTracerService.TxType;
import aero.nettracer.serviceprovider.wt_1_0.services.wtrweb.service.WorldTracerService.WorldTracerField;

public interface RuleMapper {

	public EnumMap<WorldTracerField,WorldTracerRule<String>> getRule(TxType txType);
	
}
