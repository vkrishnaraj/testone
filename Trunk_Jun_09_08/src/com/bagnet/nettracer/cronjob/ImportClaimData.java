package com.bagnet.nettracer.cronjob;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

import javax.naming.Context;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import aero.nettracer.fs.model.File;
import aero.nettracer.fs.model.FsClaim;
import aero.nettracer.fs.model.FsReceipt;
import aero.nettracer.fs.model.Person;
import aero.nettracer.fs.model.Segment;
import aero.nettracer.selfservice.fraud.ClaimRemote;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.dao.FileDAO;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.utils.ClaimUtils;
import com.bagnet.nettracer.tracing.utils.ntfs.ConnectionUtil;

public abstract class ImportClaimData {

	protected final int SLEEP_DELAY = 5000;

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

	protected boolean ntUser;
	protected Agent agent;
	protected DecimalFormat df = new DecimalFormat("#0.00");
	protected String filePath;
	protected int queueSize;
	protected int submissionThreadCount;
	protected String username;
	protected String password;
	protected String company;
	protected int runWhich;
	protected boolean submitToFraud = false;
	protected int maxReturn;
	protected boolean traceActive = false;
	
	protected PrintWriter outputFile;
	protected ArrayBlockingQueue<aero.nettracer.fs.model.File> queue;

	private static Logger logger = Logger.getLogger(ImportClaimData.class);
	private static LinkedHashMap<String, String> failedIncidents;
	private Thread[] threads;
	
	public void importClaims() {
		
		if (runWhich == 13 || runWhich == 23 || runWhich == 3) {
			submitToFraud = true;
		}
		
		if (!init()) {
			logger.error("Failed to init.");
			return;
		}
		
		// TODO UNCOMMENT THIS SECTION!!!
		// import existing nettracer claims
		if (ntUser && (runWhich == 1 || runWhich == 13)) {
			 importNtClaims();
			 sleepForABit();
		}
		
		if (runWhich == 2 || runWhich == 23) {
			importThirdPartyClaims();
		
			sleepForABit();
		}
		
		// submit any files to fraud services that have not been submitted yet
		if (runWhich == 3) {
			submitFilesToFraudServices();		
			sleepForABit();
		}
		
		closeOutputFile();
		
	}

	@SuppressWarnings("rawtypes")
	private void importNtClaims() {
		
		failedIncidents = new LinkedHashMap<String, String>();

		IncidentBMO ibmo = new IncidentBMO();

		Session session = null;
		outputFile.println("Processing NetTracer claims...");
		try {
			session = HibernateWrapper.getSession().openSession();

			outputFile.println("Fetching claims...\n");
			String sql = "select * from claim where incident_ID not in (select ntIncidentId from fsclaim where ntIncidentId is not null);";
			SQLQuery query = session.createSQLQuery(sql);
			List claims = query.list();
			session.close();

			int i = 0;
			Object[] row;
			Incident incident;
			FsClaim claim;
			File file;
			double start = System.currentTimeMillis();
			for (; i < claims.size(); ++i) {
				row = (Object[]) claims.get(i);
				outputFile.println(i + ":\tProcessing claim "
						+ (Integer) row[CLAIM_ID] + " for incident: "
						+ (String) row[INCIDENT_ID]);

				double startClaim = System.currentTimeMillis();
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
					logger.error(e);
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
					logger.error("\tError saving file: " + file.getId());
					break;
				}
				
				// add the file to the queue to be submitted to fraud services
				if (submitToFraud) {
					queue.put(file);
				}

				if (ibmo.saveAndAuditIncident(incident, agent, null) <= 0) {
					logger.error("\tError saving incident: "
							+ incident.getIncident_ID());
					break;
				} else {
					double endClaim = System.currentTimeMillis();
					double durationClaim = (endClaim - startClaim) / 1000;
					outputFile.println("\tSuccessfully processed incident: "
							+ (String) row[INCIDENT_ID] + " in " + df.format(durationClaim) + " seconds.\n");
				}

			}

			double end = System.currentTimeMillis();
			double duration = (end - start) / 1000;
			outputFile.println("Processed " + i + " claims in " + df.format(duration) + " seconds.\n");
			printErrorSummary();

		} catch (Exception e) {
			logger.error(e);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}

	}
	
	private void closeOutputFile() {
		if (outputFile != null) {
			outputFile.close();
		}
	}

	public boolean setVariablesFromArgs(String[] args) {
		boolean success = true;
		if (args.length != 8 && args.length != 9) {
			success = false;
		} else {
			try {
				this.username = args[0];
				this.password = args[1];
				this.company = args[2];
				this.runWhich = Integer.valueOf(args[3]);
				this.traceActive = Integer.valueOf(args[4]) == 1;
				this.submissionThreadCount = Integer.valueOf(args[5]);
				this.maxReturn = Integer.valueOf(args[6]);
				this.queueSize = Integer.valueOf(args[7]);
				if (args.length == 9) {
					this.filePath = args[8];
				}
			} catch (NumberFormatException nfe) {
				logger.error(nfe);
				success = false;
			}
		}
		return success;
	}
	
	private boolean init() {
		boolean success = true;
		if (submitToFraud && !contactCentralServices()) {
			logger.error("Failed to connect to central services.");
			return false;
		}
		
		if (!initOutputFile()) {
			logger.error("Failed to open the output file.");
			return false;
		}
		
		if (!initAgent()) {
			logger.error("Failed to load the agent from the database.");
			return false;
		}
		if (submitToFraud) {
			initSubmissionThreads();
		}
		return success;
	}
	
	private boolean contactCentralServices() {
		boolean success = true;
		String test = "test";
		Context ctx = null;
		ClaimRemote remote = null;
		try {
			ctx = ConnectionUtil.getInitialContext();
			remote = (ClaimRemote) ConnectionUtil.getRemoteEjb(ctx, PropertyBMO.getValue(PropertyBMO.CENTRAL_FRAUD_SERVICE_NAME));
			if (remote == null) {
				success = false;
			} else {
				String response = remote.echoTest(test);
				if (response == null) {
					success = false;
				}
			}
		} catch (Exception e) {
			logger.error(e, e);
			success = false;
		}
		
		return success;
	}
	
	private boolean initOutputFile() {
		boolean success = true;
		try {
			outputFile = new PrintWriter(new BufferedWriter(new FileWriter(filePath + "/process_output.txt")), true);
		} catch (IOException ioe) {
			logger.error(ioe.getMessage(), ioe);
			success = false;
		}
		return success;
	}
	
	private boolean initAgent() {
		boolean success = true;
		agent = loadAgent();
		if (agent == null) {
			success = false;
		}
		return success;
	}
	
	private void initSubmissionThreads() {
		queue = new ArrayBlockingQueue<aero.nettracer.fs.model.File>(queueSize);
		threads = new Thread[submissionThreadCount];
		for (int i = 0; i < submissionThreadCount; ++i) {
			Context ctx = null;
			ClaimRemote remote = null;
			try {
				ctx = ConnectionUtil.getInitialContext();
				remote = (ClaimRemote) ConnectionUtil.getRemoteEjb(ctx, PropertyBMO.getValue(PropertyBMO.CENTRAL_FRAUD_SERVICE_NAME));
			} catch (Exception e) {
				logger.error(e, e);
			}
			if (remote != null) {
				threads[i] = new Thread(new SubmissionThread(queue, remote));
				threads[i].start(); 
				outputFile.println("Created SubmissionThread: " + (i + 1));
			} else {
				outputFile.println("Failed Remote for SubmissionThread: " + (i + 1));
			}
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// do nothing
			}
		}
	}
	
	private void submitToFraudAndSave(aero.nettracer.fs.model.File file, ClaimRemote remote) {
		long originalFileId;		
		long remoteFileId = 0;
		if (remote == null || file == null) {
			return;
		} else {
			originalFileId = file.getId();
			LinkedHashSet<FsClaim> fsClaims = new LinkedHashSet<FsClaim>();
			
			for (FsClaim current: file.getClaims()) {
				FsClaim newClaim = new FsClaim();
				try {
					BeanUtils.copyProperties(newClaim, current);
				} catch (IllegalAccessException e) {
					logger.error(e, e);
					continue;
				} catch (InvocationTargetException e) {
					logger.error(e, e);
					continue;
				} 
				
				LinkedHashSet<Segment> segs = new LinkedHashSet<Segment>();
				newClaim.setSegments(segs);
				
				LinkedHashSet<Person> pers = new LinkedHashSet<Person>();
				newClaim.setClaimants(pers);
				
				for (Person p: current.getClaimants()) {
					p.setClaim(newClaim);
					pers.add(p);
				}
				
				for (Segment s: current.getSegments()) {
					s.setClaim(newClaim);
					segs.add(s);
				}
				
				LinkedHashSet<FsReceipt> receipts = new LinkedHashSet<FsReceipt>();
				newClaim.setReceipts(receipts);
				for (FsReceipt r: current.getReceipts()) {
					r.setClaim(newClaim);
					receipts.add(r);
				}
				
				file.setStatusId(current.getStatusId());
				newClaim.setFile(file);
				file.setIncident(newClaim.getIncident());
				newClaim.getIncident().setFile(file);
				fsClaims.add(newClaim);
			}
			
			file.setClaims(fsClaims);
			logger.info("Submitting file: " + originalFileId + " to central services...");
			double start = System.currentTimeMillis();
			remoteFileId = remote.insertFile(file);
			if (remoteFileId > 0) {
				file = FileDAO.loadFile(originalFileId);
				file.setSwapId(remoteFileId);
				FileDAO.saveFile(file, false);
				if (traceActive) {
					remote.traceFile(remoteFileId, 6, true, false);
				}
				logger.info("File: " + file.getId() + " saved to central services with remote id: " + remoteFileId);
			} else {
				logger.info("Failed to save file: " + file.getId() + " to fraud services.");
			}
			double end = System.currentTimeMillis();
			double duration = (end - start) / 1000;
			logger.info("Submitted file: " + originalFileId + " to central services in: " + df.format(duration) + " seconds.");			
		}
	}
	
	@SuppressWarnings("unchecked")
	protected void submitFilesToFraudServices() {
		LinkedHashSet<Long> map = new LinkedHashSet<Long>();		
		Session session = null;
		boolean haveFiles = false;
		int noneAdded = 0;
		try {
			while (true) {
				session = HibernateWrapper.getSession().openSession();
	
				String sql = "from aero.nettracer.fs.model.File as file where file.swapId = 0";
				Query query = session.createQuery(sql);
				query.setMaxResults(maxReturn);
				List<aero.nettracer.fs.model.File> files = query.list();
				session.close();
				
				if (files.size() == 0) {
					outputFile.println("The query returned no files. Exiting loop...");
					break;
				} else {
					outputFile.println("Query returned: " + files.size() + " files.");
				}

				haveFiles = false;
				for (aero.nettracer.fs.model.File f: files) {
					if (map.add(f.getId())) {
						queue.put(f);
						haveFiles = true;
					}
				}

				if (!haveFiles) {
					outputFile.println("No files were added to the process.");
					if (noneAdded == 5) {
						outputFile.println("No more files to process. Exiting loop...");
						break;
					}
					noneAdded++;
				} else {
					noneAdded = 0;
				}
				
				while (queue.size() > 5) {
					try {
						outputFile.println("Waiting for threads to work the queue...");
						Thread.sleep(5000);
					} catch (InterruptedException e) { }
				}
				
			}
		} catch (Exception e) {
			logger.error(e, e);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}
	
	protected abstract Agent loadAgent();

	protected abstract void importThirdPartyClaims();
	
	private void sleepForABit() {
		while (queue.size() > 0) {
			outputFile.println("Still submitting files. Sleeping for " + SLEEP_DELAY / 1000 + " more seconds...");
			try {
				Thread.sleep(SLEEP_DELAY);
			} catch (InterruptedException e) {
				// do nothing
			}
		}

		try {
			outputFile.println("Sleeping for " + (SLEEP_DELAY * 2) / 1000 + " seconds to allow the submission threads to finish.");
			Thread.sleep(SLEEP_DELAY * 2);
		} catch (InterruptedException e) {
			// do nothing
		}
	}
	
	private void printErrorSummary() {
		int size = failedIncidents.keySet().size();
		String[] keys = failedIncidents.keySet().toArray(new String[size]);
		logger.error("The following incidents could not be processed:");
		for (int i = 0; i < size; ++i) {
			logger.error((i + 1) + ":\t" + keys[i] + " : "
					+ failedIncidents.get(keys[i]));
		}
	}
	
	public class SubmissionThread implements Runnable {

		private ArrayBlockingQueue<aero.nettracer.fs.model.File> queue;
		ClaimRemote remote;
		
		public SubmissionThread(ArrayBlockingQueue<aero.nettracer.fs.model.File> queue, ClaimRemote remote) {
			this.queue = queue;
			this.remote = remote;
		}
		
		@Override
		public void run() {
			while(true){
				try{
					File file = queue.take();
					try {
						remote.echoTest("TEST");
					} catch(Exception ex) {
						Context ctx = null;
						try {
							ctx = ConnectionUtil.getInitialContext();
							remote = (ClaimRemote) ConnectionUtil.getRemoteEjb(ctx, PropertyBMO.getValue(PropertyBMO.CENTRAL_FRAUD_SERVICE_NAME));
						} catch (Exception e) {
							logger.error(e, e);
						}
					}
					submitToFraudAndSave(file, remote);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			
		}
		
	}

}
