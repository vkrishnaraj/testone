package com.bagnet.clients;

import java.util.Date;
import java.util.EnumMap;

import com.bagnet.nettracer.datasources.ScannerDataSource;
import com.bagnet.nettracer.tracing.dto.ScannerDTO;
import com.bagnet.nettracer.tracing.utils.TracerProperties;
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

public class WorldTracerRuleMapImpl implements RuleMapper {
	private RuleMapper target=new com.bagnet.clients.defaul.wt.DefaultWorldTracerRuleMap();
	

    public WorldTracerRuleMapImpl() {
        String companyCode = TracerProperties.get("wt.company.code");
        String path = TracerProperties.get(companyCode, "wt.rules"); // Use new TP get method
        try {
        Class cls = Class.forName(path);
        RuleMapper res = (RuleMapper) cls.newInstance();
        target=res;
        }catch (Exception x) {
                
        }
    	
    }

	@Override
	public EnumMap<WorldTracerField, WorldTracerRule<String>> getRule(
			TxType txType) {
		// TODO Auto-generated method stub
		return target.getRule(txType);
	}
}
