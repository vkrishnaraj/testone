package com.bagnet.nettracer.cronjob;

import org.apache.struts.action.ActionMessages;

import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.utils.SecurityUtils;

public class ImportClaimDataDef extends ImportClaimData {
	
	public ImportClaimDataDef() {
		ntUser = PropertyBMO.isTrue("nt.user");
	}

	@Override
	protected Agent loadAgent() {
		return SecurityUtils.authUser(username, password, company,
				0, new ActionMessages());
	}

	@Override
	protected void importThirdPartyClaims() {
	}
	
	public static void main(String[] args) {
		ImportClaimDataDef importer = new ImportClaimDataDef();
		if (!importer.setVariablesFromArgs(args)) {
			System.err.println("Usage:\t" + ImportClaimDataDef.class.getSimpleName() + " [username] [password] [company] " +
					"[process(1=import, 2=third party, 3=submit to FS)] [trace active (0 or 1)] [thread count] [max return] " +
					"[queue size] [crm file path(OPTIONAL)]");
			System.err.println("Example:\t" + ImportClaimDataDef.class.getSimpleName() + " ntadmin Ladendead51! US 1 3 500 C:\\crm");
		} else {		
			importer.importClaims();
		}
		System.exit(0);
	}

}
