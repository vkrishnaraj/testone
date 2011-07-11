package fs;

import java.util.LinkedHashMap;
import java.util.List;

import org.apache.struts.action.ActionMessages;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.junit.Test;

import aero.nettracer.fs.model.File;
import aero.nettracer.fs.model.FsClaim;
import aero.nettracer.serviceprovider.common.hibernate.HibernateWrapper;

import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.dao.FileDAO;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.utils.ClaimUtils;
import com.bagnet.nettracer.tracing.utils.SecurityUtils;

public class ImportClaimData {

	private final int CLAIM_ID = 0;
	private final int INCIDENT_ID = 1;
	private final int CLAIMAMOUNT = 2;
	private final int CURRENCY_ID = 3;
	private final int STATUS_ID = 4;
	private final int CLAIMPRORATE_ID = 5;
	private final int TOTAL = 6;
	private final int SSN = 7;
	private final int DRIVERSLICENSE = 8;
	private final int DLSTATE = 9;
	private final int COMMONNUM = 10;
	private final int COUNTRYOFISSUE = 11;

	private LinkedHashMap<String, String> failedIncidents;

	@Test
	public void importClaims() {
		failedIncidents = new LinkedHashMap<String, String>();

		Agent agent = SecurityUtils.authUser("ntadmin", "Ladendead51!", "B6",
				0, new ActionMessages());
		if (agent == null) {
			return;
		}

		IncidentBMO ibmo = new IncidentBMO();

		Session session = null;
		System.out.println("Begin processing...");
		try {
			session = HibernateWrapper.getSession().openSession();

			System.out.println("Fetching claims...\n");
			String sql = "select * from claim;";
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
				file = new File();
				file.setClaim(claim);
				claim.setFile(file);

				file.setIncident(claim.getIncident());
				claim.getIncident().setFile(file);

				// save the file - should also save the new claim
				if (!FileDAO.saveFile(file)) {
					System.err.println("\tError saving file: " + file.getId());
					break;
				}

				if (ibmo.saveAndAuditIncident(incident, agent, session) <= 0) {
					System.err.println("\tError saving incident: "
							+ incident.getIncident_ID());
					break;
				} else {
					System.out
							.print("\tSuccessfully processed incident: "
									+ (String) row[INCIDENT_ID] + "\n");
				}

				if (claimsProcessed == 500) {
					session.close();
					session = HibernateWrapper.getSession().openSession();
					claimsProcessed = 0;
				}

			}

			long end = System.currentTimeMillis();
			System.out.println("Processed " + i + " claims in " + (end - start)
					/ 1000 + " seconds.\n");
			printErrorSummary();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}

	}

	private void printErrorSummary() {
		int size = failedIncidents.keySet().size();
		String[] keys = failedIncidents.keySet().toArray(new String[size]);
		System.err.println("The following incidents could not be processed:");
		for (int i = 0; i < size; ++i) {
			System.err.println((i+1) + ":\t" + keys[i] + " : " + failedIncidents.get(keys[i]));
		}
	}
	
//	@Test
//	public void testPrintErrorSummary() {
//		failedIncidents = new LinkedHashMap<String, String>();
//		failedIncidents.put("FLLWS00011122", "Failed to load");
//		failedIncidents.put("FLLWS00011123", "Failed to create claim.");
//		failedIncidents.put("FLLWS00011124", "Failed to create claim.");
//		printErrorSummary();
//	}

}
