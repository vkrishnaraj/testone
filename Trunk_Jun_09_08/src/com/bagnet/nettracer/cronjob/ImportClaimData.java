package com.bagnet.nettracer.cronjob;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import aero.nettracer.fs.model.File;
import aero.nettracer.fs.model.FsClaim;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.dao.FileDAO;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.utils.ClaimUtils;

public abstract class ImportClaimData {

	protected boolean ntUser;
	protected Agent agent;
	protected DecimalFormat df = new DecimalFormat("0.00");

	private final int CLAIM_ID = 0;
	private final int INCIDENT_ID = 1;
	private final int CLAIMAMOUNT = 2;
	private final int CURRENCY_ID = 3;
	private final int STATUS_ID = 4;
	private final int CLAIMPRORATE_ID = 5;
	// private final int TOTAL = 6;
	private final int SSN = 7;
	private final int DRIVERSLICENSE = 8;
	private final int DLSTATE = 9;
	private final int COMMONNUM = 10;
	private final int COUNTRYOFISSUE = 11;

	private static Logger logger = Logger.getLogger(ImportClaimData.class);
	private static LinkedHashMap<String, String> failedIncidents;

	public void importClaims() {
		agent = loadAgent();
		if (agent == null) {
			return;
		}

		// import existing nettracer claims
		if (ntUser) {
			 importNtClaims();
		}
		
		importThirdPartyClaims();
	}

	@SuppressWarnings("rawtypes")
	private void importNtClaims() {
		
		failedIncidents = new LinkedHashMap<String, String>();

		IncidentBMO ibmo = new IncidentBMO();

		Session session = null;
		System.out.println("Processing NetTracer claims...");
		try {
			session = HibernateWrapper.getSession().openSession();

			System.out.println("Fetching claims...\n");
			String sql = "select * from claim where incident_ID not in (select ntIncidentId from fsclaim where ntIncidentId is not null);";
			SQLQuery query = session.createSQLQuery(sql);
			List claims = query.list();

			int i = 0;
			int claimsProcessed = 0;
			Object[] row;
			Incident incident;
			FsClaim claim;
			File file;
			long start = System.currentTimeMillis();
			for (; i < claims.size(); ++i) {
				claimsProcessed++;
				row = (Object[]) claims.get(i);
				System.out.println(i + ":\tProcessing claim "
						+ (Integer) row[CLAIM_ID] + " for incident: "
						+ (String) row[INCIDENT_ID]);

				long startClaim = Calendar.getInstance().getTimeInMillis();
				// load the incident
				incident = ibmo.findIncidentByID((String) row[INCIDENT_ID]);
				if (incident == null) {
					failedIncidents.put((String) row[INCIDENT_ID],
							"Failed to load.");
					continue;
				}

				// create the claim from the incident
				try {
					claim = ClaimUtils.createClaim(agent, incident);
				} catch (Exception e) {
					failedIncidents.put((String) row[INCIDENT_ID],
							"Failed to create claim due to: " + e.getMessage());
					continue;
				}

				// set the claim date
				claim.setClaimDate(incident.getCreatedate());

				// set the claim amount
				claim.setAmountClaimed((Double) row[CLAIMAMOUNT]);

				// set the claim currency
				claim.setAmountClaimedCurrency((String) row[CURRENCY_ID]);

				// set the claim prorate id
				if (row[CLAIMPRORATE_ID] != null) {
					claim.setClaimProrateId((Integer) row[CLAIMPRORATE_ID]);
				}

				// set the claim status
				claim.setStatusId((Integer) row[STATUS_ID]);

				// set the claimant ssn
				claim.getClaimant().setSocialSecurity((String) row[SSN]);

				// set the claimant drivers license
				claim.getClaimant().setDriversLicenseNumber(
						(String) row[DRIVERSLICENSE]);

				// set the claimant drivers license state
				claim.getClaimant().setDriversLicenseState(
						(String) row[DLSTATE]);

				// set the claimant passport number
				claim.getClaimant().setPassportNumber((String) row[COMMONNUM]);

				// set the claimant passport country of issue
				claim.getClaimant().setPassportIssuer(
						(String) row[COUNTRYOFISSUE]);

				// create the file
				file = FileDAO.loadFile(incident.getIncident_ID());
				if (file == null) {
					file = new File();
					file.setClaims(new LinkedHashSet<FsClaim>());
				}
				file.getClaims().add(claim);
				claim.setFile(file);

				file.setIncident(claim.getIncident());
				claim.getIncident().setFile(file);

				file.setValidatingCompanycode(agent.getCompanycode_ID());

				// save the file - should also save the new claim
				if (!FileDAO.saveFile(file, true)) {
					System.err.println("\tError saving file: " + file.getId());
					break;
				}

				if (ibmo.saveAndAuditIncident(incident, agent, null) <= 0) {
					System.err.println("\tError saving incident: "
							+ incident.getIncident_ID());
					break;
				} else {
					long endClaim = Calendar.getInstance().getTimeInMillis();
					double durationClaim = (endClaim - startClaim) / 1000;
					System.out.println("\tSuccessfully processed incident: "
							+ (String) row[INCIDENT_ID] + " in " + df.format(durationClaim) + " seconds.\n");
				}

				if (claimsProcessed == 500) {
					session.close();
					session = HibernateWrapper.getSession().openSession();
					claimsProcessed = 0;
				}

			}

			long end = System.currentTimeMillis();
			double duration = (end - start) / 1000;
			System.out.println("Processed " + i + " claims in " + df.format(duration) + " seconds.\n");
			printErrorSummary();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}

	}

	protected abstract Agent loadAgent();

	protected abstract void importThirdPartyClaims();

	private void printErrorSummary() {
		int size = failedIncidents.keySet().size();
		String[] keys = failedIncidents.keySet().toArray(new String[size]);
		System.err.println("The following incidents could not be processed:");
		for (int i = 0; i < size; ++i) {
			logger.error((i + 1) + ":\t" + keys[i] + " : "
					+ failedIncidents.get(keys[i]));
		}
	}

}
