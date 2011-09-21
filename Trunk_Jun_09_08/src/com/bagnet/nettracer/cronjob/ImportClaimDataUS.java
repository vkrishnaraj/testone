package com.bagnet.nettracer.cronjob;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.struts.action.ActionMessages;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import aero.nettracer.fs.model.File;
import aero.nettracer.fs.model.FsClaim;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.utils.ClaimUtils;
import com.bagnet.nettracer.tracing.utils.SecurityUtils;
import com.bagnet.nettracer.tracing.utils.TracerProperties;

public class ImportClaimDataUS extends ImportClaimData {
	
//	private static Logger logger = Logger.getLogger(ImportClaimDataUS.class);
	
	private final int CRM_KEY = 0;
	private final int INCIDENT_ID = 1;
	
	private final int CLAIM_AMOUNT_CLAIMED = 1;
	private final int CLAIM_AMOUNT_COMPENSATED = 2;
	private final int CLAIM_INCIDENT_TYPE = 3;
	private final int CLAIM_INCIDENT_ID = 4;
	private final int CLAIM_DATE_OF_OCCURRENCE = 5;
	private final int CLAIM_STATION = 6;
	private final int CLAIM_ITEM_TYPE = 9;
	private final int CLAIM_CRM_ID = 10;
	private final int CLAIM_LAST_NAME = 11;
	private final int CLAIM_FIRST_NAME = 12;
	private final int CLAIM_MIDDLE_NAME = 13;
	private final int CLAIM_ADDRESS1 = 14;
	private final int CLAIM_ADDRESS2 = 15;
	private final int CLAIM_CITY = 17;
	private final int CLAIM_STATE = 18;
	private final int CLAIM_ZIP = 19;
	private final int CLAIM_COUNTRY = 20;
	private final int CLAIM_EMAIL = 23;
	private final int CLAIM_BUS_PHONE = 27;
	private final int CLAIM_HOME_PHONE = 28;
	private final int CLAIM_MOBILE_PHONE = 29;
	private final int CLAIM_FAX_PHONE = 30;
	private final int CLAIM_PAGER_PHONE = 31;
	
	private LinkedHashMap<String, String> crmIncidentIds;
	private LinkedHashMap<String, File> importedClaims;
	
	public ImportClaimDataUS() {
		ntUser = false;
	}

	@Override
	protected Agent loadAgent() {
		return SecurityUtils.authUser("ntadmin", "Ladendead51!", "US",
				0, new ActionMessages());
	}

	@Override
	protected void importThirdPartyClaims() {
		System.out.print("Loading CRM table data...");
		loadCrmIncidentIds();
		System.out.println("done!\t" + crmIncidentIds.size() + " rows loaded.");
		
		importCrmClaimsFromFile("..\\..\\clients\\us\\crm\\crm_claims.csv");
		
	}
	
	@SuppressWarnings("rawtypes")
	private void loadCrmIncidentIds() {
		crmIncidentIds = new LinkedHashMap<String, String>();
		Session session = null;
		try {
			session = HibernateWrapper.getSession().openSession();
			String sql = "select crm_key,incident_id from crm where crm_key is not null;";
			SQLQuery query = session.createSQLQuery(sql);
			List rows = query.list();
			
			Object[] row;
			String crmKey;
			String incidentId;
			for (int i = 0; i < rows.size(); ++i) {
				row = (Object[]) rows.get(i);
				crmKey = (String) row[CRM_KEY];
				incidentId = (String) row[INCIDENT_ID];
				crmIncidentIds.put(crmKey, incidentId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	private void importCrmClaimsFromFile(String fileName) {
		try {
			InputStream in = ImportClaimDataUS.class.getResourceAsStream(fileName);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			
			String line = br.readLine();
			while ((line = br.readLine()) != null) {
				createClaimFromLine(line);
			}
			
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	private void createClaimFromLine(String line) {
		FsClaim claim;
		String[] columns = line.split(",");
		
		String incidentId = columns[CLAIM_INCIDENT_ID];
		incidentId = normalizeIncidentId(incidentId);
		System.out.println("Normalized incident ID:\t" + incidentId);
	}
	
	private String normalizeIncidentId(String incidentId) {
		if (incidentId == null) {
			return null;
		}
		
		String toReturn = incidentId.toUpperCase();
		toReturn = toReturn.replaceAll(" ", "");
		int i = 0;
		for (; i < toReturn.length(); ++i) {
			if (Character.isDigit(toReturn.charAt(i))) {
				break;
			}
		}
		
		if (i == toReturn.length()) {
			return null;
		}
		
		String left = toReturn.substring(0, i);
		String right = toReturn.substring(i);
		
		int padNum = 13 - (left.length() + right.length());
		if (padNum > 0) {
			for (int j = 0; j < padNum; ++j) {
				left += "0";
			}
		}
		
		toReturn = left + right;		
		return toReturn;
	}
	
	public static void main(String[] args) {
		new ImportClaimDataUS().importClaims();
	}

}
