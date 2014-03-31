package aero.nettracer.fs.utilities.tracing;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.type.StandardBasicTypes;

import aero.nettracer.fs.model.Bag;
import aero.nettracer.fs.model.File;
import aero.nettracer.fs.model.FsAddress;
import aero.nettracer.fs.model.FsAttachment;
import aero.nettracer.fs.model.FsClaim;
import aero.nettracer.fs.model.FsIPAddress;
import aero.nettracer.fs.model.FsMatchHistoryAudit;
import aero.nettracer.fs.model.GreyListAddress;
import aero.nettracer.fs.model.Person;
import aero.nettracer.fs.model.Phone;
import aero.nettracer.fs.model.FsReceipt;
import aero.nettracer.fs.model.Reservation;
import aero.nettracer.fs.model.Segment;
import aero.nettracer.fs.model.detection.AccessRequest;
import aero.nettracer.fs.model.detection.AccessRequest.RequestStatus;
import aero.nettracer.fs.model.detection.MatchDetail;
import aero.nettracer.fs.model.detection.MatchDetail.MatchType;
import aero.nettracer.fs.model.detection.MatchHistory;
import aero.nettracer.fs.model.detection.MetaWarning;
import aero.nettracer.fs.model.detection.TraceResponse;
import aero.nettracer.fs.utilities.AuditUtil;
import aero.nettracer.fs.utilities.GeoCode;
import aero.nettracer.fs.utilities.Util;
import aero.nettracer.selfservice.fraud.PrivacyPermissionsBean;
import aero.nettracer.serviceprovider.common.db.PrivacyPermissions;
import aero.nettracer.serviceprovider.common.db.PrivacyPermissions.AccessLevelType;
import aero.nettracer.serviceprovider.common.hibernate.HibernateWrapper;

import com.bagnet.nettracer.tracing.utils.DateUtils;

public class Producer {

	private static final Logger logger = Logger.getLogger(Producer.class);
	private static final String ACCESS_NOT_GRANTED = "*** Access not Granted ***";
	private static final int MAX_WAIT = 40;
	public static final double MILE_SEARCH_RADIUS = 10;
	private static final long WAIT_TIME = 250;
	public static final double MILE_SEARCH_ZIP = 10;
	public static final double MILE_SEARCH_CITY = 10;
	public static final String invalidChars = "[\'%\"]";
	
	public static String format(String s){
		return s.trim().toUpperCase().replaceAll(invalidChars, "");
	}
	
	public static TraceResponse matchFile(long fileId, int maxDelay, boolean persistData, boolean isPrimary, boolean returnResults){
		File file = TraceWrapper.getCacheManager().loadFile(fileId);
		if(file != null){
			return matchFile(file, maxDelay, persistData, isPrimary, returnResults);
		} else {
			return null;
		}
	}

	
	protected static boolean queueFile(Long id, ConsumerQueueElement element, File file, Date createDate, boolean isPrimary){
		if(element.getTraceIds().put(id, id) == null){
			MatchHistory match = new MatchHistory();
			match.setDetails(new LinkedHashSet<MatchDetail>());
			match.setFile1(file);
			match.setFile2(new File(id));
			match.setCreatedate(createDate);
			match.setPrimarymatch(isPrimary);
			try{
				element.getQueue().add(match);
				return true;
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public static TraceResponse matchFile(File file, int maxDelay, boolean persistData, boolean isPrimary, boolean returnResults) {
		
		Date producerStarttime = new Date();
		Date createDate = DateUtils.convertToGMTDate(new Date());
		ConsumerQueueElement element = startProducerThreads(file, isPrimary, createDate);
		element.setProducerTotalStart(producerStarttime);

		// so we are to keep old match histories

		if (file.getId() != 0) {
			String ms = "select file2_id, overallScore from matchhistory where file1_id = " + file.getId();
			SQLQuery pq = null;
			Session sess = HibernateWrapper.getSession().openSession();
			try{
			pq = sess.createSQLQuery(ms.toString());
			pq.addScalar("file2_id", StandardBasicTypes.LONG);
			pq.addScalar("overallScore", StandardBasicTypes.DOUBLE);
			List<Object[]> listMatchingFiles = pq.list();
			
			HashMap<Long, List<Double>> matchingMap = new HashMap<Long, List<Double>>();
			List<Double> scoreList;
			for (Object[] strs : listMatchingFiles) {
				Long f1 = (Long) strs[0];
				Double f2 = (Double) strs[1];
				if(matchingMap.get(f1)!=null){
					scoreList=matchingMap.get(f1);
					scoreList.add(f2);
					matchingMap.put(f1, scoreList);
				} else {
					scoreList=new ArrayList<Double>();
					scoreList.add(f2);
					matchingMap.put(f1, scoreList);
				}
			}
			file.setMatchingFiles(matchingMap);
			} catch (Exception e){
				e.printStackTrace();
			} finally {
				sess.close();
			}
		}

		
		String sql = "select f1.id as f1_id, " // IS there a claim associated ot this person 
			+ "f2.id as f2_id, " // # Does the icident have claim
			+ "f3.id as f3_id, " // Is there a incident associated to this person
			+ "f4.id as f4_id, "
			+ "f5.id as f5_id, "
			+ "null as f6_id, "
			+ "null as f7_id, "
			+ "\'person\' as type "
			+ "from person p "
			+ "left outer join fsclaim c1 on p.claim_id = c1.id "
			+ "left outer join FsFile f1 on f1.id = c1.file_id "
			+ "left outer join fsincident i2 on p.incident_id = i2.id "
			+ "left outer join fsclaim c2 on i2.claim_id = c2.id "
			+ "left outer join FsFile f2 on f2.id = c2.file_id "
			+ "left outer join FsFile f3 on f3.id = i2.file_id "
			+ "left outer join reservation r3 on p.reservation_id = r3.id "
			+ "left outer join fsincident i3 on r3.incident_id = i3.id "
			+ "left outer join fsclaim c3 on i3.claim_id = c3.id "
			+ "left outer join FsFile f4 on f4.id = c3.file_id "
			+ "left outer join FsFile f5 on f5.id = i3.file_id "
            + "where 1=0 ";

		Set<Person> persons = Consumer.getPersons(file, true);
		file.setPersonCache(persons, true);
		if(persons != null) for (Person person : persons) {
			
			if(person.getFirstNameSoundex() != null && person.getLastNameSoundex() != null
					&& person.getFirstNameSoundex().length() > 0  && person.getLastNameSoundex().length() > 0){
			sql += " or (lastNameSoundex = \'"+ person.getLastNameSoundex() + "\'"
				+ " and firstNameSoundex = \'" + person.getFirstNameSoundex() + "\')";
			}
			
			if(person.getFirstNameDmp() != null && person.getLastNameDmp() != null
					&& person.getFirstNameDmp().length() > 0 && person.getLastNameDmp().length() > 0){
			sql += " or (lastNameDmp = \'"+ person.getLastNameDmp() + "\'"
			+ " and firstNameDmp = \'" + person.getFirstNameDmp() + "\')";
			}
			
			if(person.getPassportNumber() != null && person.getPassportNumber().trim().length() > 0){
				sql += " or passportNumber = \'" + format(person.getPassportNumber()) + "\' ";
			}
			
			if(person.getSocialSecurity() != null && person.getSocialSecurity().trim().length() > 0){
				sql += " or socialSecurity = \'" + format(person.getSocialSecurity()) + "\' ";
			}
			
			if(person.getEmailAddress() != null && person.getEmailAddress().trim().length() > 0){
				sql += " or emailAddress = \'" + format(person.getEmailAddress()) + "\' ";
			}
			
			if(person.getFfNumber() != null && person.getFfNumber().trim().length() > 0){
				sql += " or ffNumber = \'" + format(person.getFfNumber()) + "\' ";
			}
			
			if(person.getDriversLicenseNumber() != null && person.getDriversLicenseNumber().trim().length() > 0){
				sql += " or driversLicenseNumber = \'" + format(person.getDriversLicenseNumber()) + "\' ";
			}
		}
		
		Set<Phone> phones = Consumer.getPhones(file);
		file.setPhoneCache(phones);
		Set<String> phoneNumbers = new HashSet<String>();
		//we might have duplicate Phone objects with the same phone number, we want unique phone numbers
		for(Phone phone:phones){
			if(phone.getPhoneNumber() != null && phone.getPhoneNumber().trim().length()>0){
				phoneNumbers.add(phone.getPhoneNumber());
			}
		}
		if(phoneNumbers.size() > 0){

			sql += " union ";
			sql += "select f1.id as f1_id, " +
					"f2.id as f2_id, " +
					"f3.id as f3_id, " +
					"f4.id as f4_id, " +
					"f5.id as f5_id, " +
					"f6.id as f6_id, " +
					"f7.id as f7_id, " +
					"'phone' as type ";
			sql += "from phone ph "
				+  " left outer join person p on ph.person_id = p.id "
				+ " left outer join fsclaim c1 on p.claim_id = c1.id "
				+ " left outer join FsFile f1 on f1.id = c1.file_id "
				+ "  left outer join fsincident i2 on p.incident_id = i2.id "
				+ " left outer join fsclaim c2 on i2.claim_id = c2.id "
				+ " left outer join FsFile f2 on f2.id = c2.file_id "
				+ " left outer join FsFile f3 on f3.id = i2.file_id "
				+ "   left outer join reservation r3 on p.reservation_id = r3.id "
				+ "     left outer join fsincident i3 on r3.incident_id = i3.id "
				+ "     left outer join fsclaim c3 on i3.claim_id = c3.id "
				+ " left outer join FsFile f4 on f4.id = c3.file_id "
				+ " left outer join FsFile f5 on f5.id = i3.file_id "
				+ "  left outer join reservation r4 on ph.reservation_id =	r4.id "
				+ "     left outer join fsincident i4 on r4.incident_id = i4.id "
				+ "    left outer join fsclaim c4 on i4.claim_id = c4.id "
				+ " left outer join FsFile f6 on f6.id = c4.file_id "
				+ " left outer join FsFile f7 on f7.id = i4.file_id "
				+ " where 1=0 ";

//			for(Phone phone:phones){
//				if(phone.getPhoneNumber() != null && phone.getPhoneNumber().trim().length() > 0){
//					sql += " or ph.phoneNumber = \'" + phone.getPhoneNumber() + "\' ";
//				}
//			}
			for(String phoneNumber:phoneNumbers){
				// TODO this may need to change for partial phone matches
				sql += " or ph.phoneNumber = \'" + format(phoneNumber) + "\' ";
			}
		}
		
		if(file.getIncident() != null && file.getIncident().getReservation() != null
				&& file.getIncident().getReservation().getCcNumLastFour() != null
				&& file.getIncident().getReservation().getCcNumLastFour().trim().length() > 0){
			sql += " union select null as f1_id, " +
					"f2.id as f2_id, " +
					"f3.id as f3_id, " +
					"null as f4_id, " +
					"null as f5_id, " +
					"null as f6_id, " +
					"null as f7_id, " +
					"'cc' as type " +
					"from reservation res " +
					"left outer join fsincident i2 on res.incident_id = i2.id " +
					"left outer join fsclaim c2 on i2.claim_id = c2.id " +
					"left outer join FsFile f2 on f2.id = c2.file_id " +
					"left outer join FsFile f3 on f3.id = i2.file_id " +
					"where ccNumLastFour = \'" + format(file.getIncident().getReservation().getCcNumLastFour()) +"\' ";
		}

		Set<FsClaim> claims = file.getClaims();
		Set<FsReceipt> receipts= new HashSet<FsReceipt>();
		Set<String> ccNumbers = new HashSet<String>();
		Set<FsIPAddress> ipaddresses = new HashSet<FsIPAddress>();
		Set<String> ipaddressnums = new HashSet<String>();
		//we might have duplicate Phone objects with the same phone number, we want unique phone numbers
		for(FsClaim claim:claims){
			receipts.addAll(claim.getReceipts());
			ipaddresses.addAll(claim.getIpAddresses());
		}
		for(FsReceipt receipt:receipts)
		{
			ccNumbers.add(receipt.getCcLastFour());
		}
		
		for(FsIPAddress ipaddress:ipaddresses){
			ipaddressnums.add(ipaddress.getIpAddress());
		}
		
		if((ccNumbers!=null && ccNumbers.size()>0)){
			sql += " union select null as f1_id, " +
					"f2.id as f2_id, " +
					"f3.id as f3_id, " +
					"null as f4_id, " +
					"null as f5_id, " +
					"null as f6_id, " +
					"null as f7_id, " +
					"'cc' as type " +
					"from fsReceipt rec " +
					"left outer join fsclaim c2 on rec.claim_id = c2.id " +
					"left outer join fsincident i2 on i2.claim_id = c2.id " +
					"left outer join FsFile f2 on f2.id = c2.file_id " +
					"left outer join FsFile f3 on f3.id = i2.file_id " +
					"where ";
					if(ccNumbers.size()>0)
					{
						for(String ccnumber:ccNumbers)
						{
							sql+="rec.ccLastFour = \'"+format(ccnumber)+"\' OR ";
						}
					}
					sql=sql.substring(0, sql.length()-3);			
		}
		
		if(ipaddresses!=null && ipaddresses.size()>0){
			sql += " union select null as f1_id, " +
					"f2.id as f2_id, " +
					"f3.id as f3_id, " +
					"null as f4_id, " +
					"null as f5_id, " +
					"null as f6_id, " +
					"null as f7_id, " +
					"'cc' as type " +
					"from fsipaddress ip " +
					"left outer join fsclaim c2 on ip.claim_id = c2.id " +
					"left outer join fsincident i2 on i2.claim_id = c2.id " +
					"left outer join FsFile f2 on f2.id = c2.file_id " +
					"left outer join FsFile f3 on f3.id = i2.file_id " +
					"where ";
					if(ipaddressnums.size()>0)
					{
						for(String ipaddress:ipaddressnums)
						{
							sql+="ip.ipaddress = \'"+format(ipaddress)+"\' OR ";
						}
					}
					sql=sql.substring(0, sql.length()-3);
		}
		
		logger.debug("Producer query: " + sql);
	
		
		SQLQuery pq = null;
		Session sess = HibernateWrapper.getSession().openSession();
		pq = sess.createSQLQuery(sql.toString());
		pq.addScalar("f1_id", StandardBasicTypes.LONG);
		pq.addScalar("f2_id", StandardBasicTypes.LONG);
		pq.addScalar("f3_id", StandardBasicTypes.LONG);
		pq.addScalar("f4_id", StandardBasicTypes.LONG);
		pq.addScalar("f5_id", StandardBasicTypes.LONG);
		pq.addScalar("f6_id", StandardBasicTypes.LONG);
		pq.addScalar("f7_id", StandardBasicTypes.LONG);
		pq.addScalar("type", StandardBasicTypes.STRING);
		List<Object[]> result = pq.list();

		sess.close();
		
		int count = -1;//remove match against self
		
		for (Object[] strs : result) {
			Long f1 = (Long) strs[0];
			Long f2 = (Long) strs[1];
			Long f3 = (Long) strs[2];
			Long f4 = (Long) strs[3];
			Long f5 = (Long) strs[4];
			Long f6 = (Long) strs[5];
			Long f7 = (Long) strs[6];
			String type = (String) strs[7];
			
			if(f1 != null){
				queueFile(f1, element, file, createDate, isPrimary);
			} else if (f2 != null){
				queueFile(f2, element, file, createDate, isPrimary);
			} else if (f3 != null){
				queueFile(f3, element, file, createDate, isPrimary);
			} else if (f4 != null){
				queueFile(f4, element, file, createDate, isPrimary);
			} else if (f5 != null){
				queueFile(f5, element, file, createDate, isPrimary);
			} else if (f6 != null){
				queueFile(f6, element, file, createDate, isPrimary);
			} else if (f7 != null){
				queueFile(f7, element, file, createDate, isPrimary);
			}
			count++;
		}		
		
		Date producerEndtime = new Date();
		element.setProducerStart(producerStarttime);
		element.setProducerEnd(producerEndtime);
		element.setProducerCount(count);
		element.setProducerFinished(true);
		//System.out.println("Consumer BEGIN: " + (new Date()));
		
		traceProgress.put(file.getId(), new TraceProgress((new Date()).getTime(),element));
		
		if(returnResults){
			//		System.out.println("  Potential results: " + fileQueue.size());
			//		System.out.println("  MaxDelay: " + maxDelay);
			try {
				int i = 0;
				for(i= 0; !element.isTraceFinished() && (i < (maxDelay * 1000)/WAIT_TIME || maxDelay == -1); i++){
					Thread.sleep(WAIT_TIME);
				}
				Thread.sleep(WAIT_TIME);//TODO isTraceFininshed marks when the last element is being worked, not when it is finished work, wait one additional cycle for now. may implement a notify later
				Date endtime = new Date();
				if (maxDelay == -1) {
					logger.debug("  Complete Trace Elapsed Time: "  + (endtime.getTime() - producerEndtime.getTime()));
					file.setPersonCache(null, true);
					file.setPersonCache(null, false);
					file.setAddressCache(null);
					file.setPhoneCache(null);
				} else if (i >= MAX_WAIT) {
					logger.debug("***WARNING: Maximum Search Time Exceeded: " + (WAIT_TIME * MAX_WAIT) + "ms");
				}

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//		System.out.println("Consumer END: " + (new Date()));

			
			Set<FsMatchHistoryAudit> matchAuditSet = new HashSet<FsMatchHistoryAudit>();
			Set<MatchHistory> mh = getCensoredFileMatches(file.getId(), matchAuditSet);
			//		System.out.println("Returning RESULTS: " + mh.size());
			TraceResponse tr = new TraceResponse();

			tr.setMatchHistory(mh);
			
			//TODO review me
			Set<FsAddress> addresses = Consumer.getAddresses(file);
			file.setAddressCache(addresses);
			
			analyzeFile(file, tr, addresses);
			
			
			Date end = new Date();
			AuditUtil.saveActionAudit(
					AuditUtil.createAuditAction(AuditUtil.ACTION_GET_AUTO_TRACE_RESULTS, file.getId(), file.getValidatingCompanycode(),matchAuditSet)
					.addMetric(AuditUtil.ACTION_GET_AUTO_TRACE_RESULTS, end.getTime() - producerEndtime.getTime(), 0, "seconds until reload: " + tr.getSecondsUntilReload())
					.addMetric(AuditUtil.METRIC_TOTAL_USER_WAIT,end.getTime() - producerEndtime.getTime(), 0, null));
			
			return tr; 
		} else {
			return null;
		}
	}
	

	public static void analyzeFile(File file, TraceResponse tr, Set<FsAddress> addresses) {
		Producer.cleanTraceProgress();
		TraceProgress tp = traceProgress.get(file.getId());
		if(tp != null && tp.stillRunning()){
			tr.setTraceComplete(false);
			tr.setSecondsUntilReload(tp.getSecondsUntilComplete());
		} else {
			tr.setTraceComplete(true);
		}
		
		LinkedHashSet<MetaWarning> metaWarnings = new LinkedHashSet<MetaWarning>();
		tr.setMetaWarning(metaWarnings);
		
		int threatLevel = 0;

		
		if (tr.getMatchHistory().size() > 0) {
			MetaWarning warn = new MetaWarning();
			metaWarnings.add(warn);
			warn.setDescription("Total Match Results: " + tr.getMatchHistory().size());
			warn.setThreatLevel(TraceResponse.THREAT_LEVEL_UNKNOWN);
			warn.setPercentageMatch(0);
		}


		int response = analyzeForKnownOrSuspectedFraud(tr, file);
		if (threatLevel == 1) {
			if (threatLevel < response) {
				threatLevel += response;
			}
		} else {
			if (threatLevel < response) {
				threatLevel = response;
			}
		}
		
		response = analyzeForGreyListMatch(tr, addresses, metaWarnings);
		if (threatLevel < response) {
			threatLevel += response;
		}
		
		tr.setThreatLevel(Math.min(threatLevel, 3));
		
	}

	private static int analyzeForKnownOrSuspectedFraud(TraceResponse tr, File file) {
		int returnValue = 0; 
		int knownFraudCount = 0 ;
		int suspectedFraudCount = 0;
		int count = 0;
		Set<MatchHistory> mhs = tr.getMatchHistory();
		for (MatchHistory mh: mhs) {
			File file2 = null;
			if (mh.getFile1().getId() == file.getId()) {
				// Match against second file.
				file2 = mh.getFile2();
			} else {
				// Compare against first file.
				file2 = mh.getFile1();				
			}
			
			
			int responseValue = 0;
			if (file2.getStatusId() == 37) { // Suspected Fraud
				responseValue = 2;
				++suspectedFraudCount;
			} else if (file2.getStatusId() == 38) { // Known Fraud
				responseValue = 3;
				++knownFraudCount;
			}

			// END TOOD
			if (responseValue > 0) {
				++count;
			}
			if (responseValue > returnValue) {
				returnValue = responseValue;
			}
		}
		if (knownFraudCount > 0) {
			MetaWarning warn = new MetaWarning();
			tr.getMetaWarning().add(warn);
			warn.setDescription("Total Known Fraud Results: " + knownFraudCount);
			warn.setThreatLevel(TraceResponse.THREAT_LEVEL_RED);
			warn.setPercentageMatch(0);
		}
		if (suspectedFraudCount > 0){
			MetaWarning warn = new MetaWarning();
			tr.getMetaWarning().add(warn);
			warn.setDescription("Total Suspected Fraud Results: " + suspectedFraudCount);
			warn.setThreatLevel(TraceResponse.THREAT_LEVEL_YELLOW);
			warn.setPercentageMatch(0);
		}

		return returnValue;
		
	}


	private static int analyzeForGreyListMatch(TraceResponse tr,
			Set<FsAddress> addresses, HashSet<MetaWarning> metaWarnings) {
		int threatLevel = 0;
		HashMap<String, GreyListAddress> greyListMap = Util.getGreyListAddressMap();
		HashMap<String, String> keyCheck=new HashMap<String,String>();
		for (FsAddress a: addresses) {
			String key = Util.normalizeGeoNumber(a.getLattitude()) + "/" + Util.normalizeGeoNumber(a.getLongitude());
			if (greyListMap.containsKey(key) && a.getGeocodeType() == 1 && !keyCheck.containsKey(key)) {
				GreyListAddress gla = greyListMap.get(key);
				MetaWarning warn = new MetaWarning();
				metaWarnings.add(warn);
				warn.setDescription("The following address may belong to a mailbox provider: <ul>" 
						+ "<li>" + gla.getDescription() + " at: " + gla.getAddress() + " " + gla.getCity() + " " + gla.getState() + "  " + gla.getZip()
						+ "</li>"
						+ "<li>Address matching: "
						+ a.getAddress1()
						+ ", "
						+ a.getCity()
						+ " "
						+ a.getState()
						+ "  "
						+ a.getZip()
						+ "</li></ul>");
				warn.setThreatLevel(TraceResponse.THREAT_LEVEL_YELLOW);
				warn.setPercentageMatch(0);
				threatLevel = TraceResponse.THREAT_LEVEL_YELLOW;
			}
			keyCheck.put(key, "");
		}
		return threatLevel;
	}
	
	public static List<String> getKillSwitchCompanies(){
		String sql = "select companycode_id from killswitch";
		SQLQuery pq = null;
		Session sess = HibernateWrapper.getSession().openSession();
		pq = sess.createSQLQuery(sql.toString());
		pq.addScalar("companycode_id", StandardBasicTypes.STRING);
		List<String> result = pq.list();
		sess.close();
		return result;
	}
	
	private static Set<MatchHistory> getMatchHistoryResult(long fileId){
//		String personSql = "from aero.nettracer.fs.model.detection.MatchHistory m where 1=1 " +
//				"and (m.file1.id = :id or m.file2.id = :id) order by m.primarymatch desc, m.createdate desc, m.overallScore desc";

		//matching on file1 only because Mike is whining
		String personSql = "from aero.nettracer.fs.model.detection.MatchHistory m where 1=1 " +
		"and (m.file1.id = :id) and m.deleted = 0 order by m.primarymatch desc, m.file2.statusId desc, m.createdate desc, m.overallScore desc, m.id desc";
		
		Query q = null;
		Session sess = HibernateWrapper.getSession().openSession();
		q = sess.createQuery(personSql.toString());
		q.setParameter("id", fileId);
		List <MatchHistory>result = q.list();
		logger.debug(personSql);
		logger.debug("MatchResult for fileId: " + fileId);
		sess.close();
		LinkedHashSet<MatchHistory> ret = new LinkedHashSet<MatchHistory>();
		List<String> klist = getKillSwitchCompanies();
		for(MatchHistory match:result){
			boolean add = true;
			if(klist!=null){
				for(String k:klist){
					if(match.getFile1() != null){
						if(match.getFile1() != null && match.getFile1().getValidatingCompanycode().equalsIgnoreCase(k))add=false;
						if(match.getFile1().getClaims() != null){
							for(FsClaim claim:match.getFile1().getClaims()){
								if(claim.getAirline() != null && claim.getAirline().equalsIgnoreCase(k))add=false;
							}
						}
						if(match.getFile1().getIncident() != null && match.getFile1().getIncident().getAirline() != null && match.getFile1().getIncident().getAirline().equalsIgnoreCase(k))add=false;
					}
					if(match.getFile2() != null){
						if(match.getFile2() != null && match.getFile2().getValidatingCompanycode().equalsIgnoreCase(k))add=false;
						if(match.getFile2().getClaims() != null){
							for(FsClaim claim:match.getFile2().getClaims()){
								if(claim.getAirline() != null && claim.getAirline().equalsIgnoreCase(k))add=false;
							}
						}
						if(match.getFile2().getIncident() != null && match.getFile2().getIncident().getAirline() != null && match.getFile2().getIncident().getAirline().equalsIgnoreCase(k))add=false;
					}
				}
			} 
			if(add){
				ret.add(match);
				logger.debug("Match: " + match.getId() + "  " + match.getOverallScore());
			}else{
				logger.debug("Match censored due to kill swith: " + match.getId());
			}
		}
		return ret;
	}
	
	public static Set<MatchHistory> getCensoredFileMatches(long fileId, Set<FsMatchHistoryAudit> matchAuditSet) {
		File f = TraceWrapper.getCacheManager().loadFile(fileId);
		String company = f.getValidatingCompanycode();
		Set<MatchHistory> histories = Producer.getMatchHistoryResult(fileId);
		List<PrivacyPermissions> p = PrivacyPermissionsBean.getPrivacyPermissions();
		for(MatchHistory history:histories){
			//TODO change level based on access request
			RequestStatus rs;
			long requestedId;
			if(history.getFile1().getValidatingCompanycode().equals(company)){
				requestedId = history.getFile2().getId();
				rs = getRequestStatus(requestedId, company);
				history.getFile2().setRequestStatus(rs);
			} else{
				requestedId = history.getFile1().getId();
				rs = getRequestStatus(requestedId, company);
				history.getFile1().setRequestStatus(rs);
			}
			
			if(rs != null && rs.equals(RequestStatus.Approved)){
				censor(history, AccessLevelType.req, company, p);
				matchAuditSet.add(new FsMatchHistoryAudit(history.getId(), "REQ"));
			} else {
				censor(history, AccessLevelType.def, company, p);
				matchAuditSet.add(new FsMatchHistoryAudit(history.getId(), "DEF"));
			}
		}
		return histories;
	}
	
	public static RequestStatus getRequestStatus(long fileId, String requestedCompany){
		String sql = "select status from accessrequest where file_id = :id and requestedAirline = :airline order by requestedDate desc";
		SQLQuery q = null;
		Session sess = HibernateWrapper.getSession().openSession();
		q = sess.createSQLQuery(sql);
		
		q.setParameter("id", fileId);
		q.setParameter("airline", requestedCompany);

		q.addScalar("status", StandardBasicTypes.STRING);
		q.setMaxResults(1);
		
		String level = (String)q.uniqueResult();
		sess.close();
		if(level != null){
			return RequestStatus.valueOf(level);
		}

		return null;
	}
	
	public static boolean isApproved(long fileId, String requestedCompany){
		String sql = "select status from accessrequest where file_id = :id and requestedAirline = :airline order by requestedDate desc";
		SQLQuery q = null;
		Session sess = HibernateWrapper.getSession().openSession();
		q = sess.createSQLQuery(sql);
		
		q.setParameter("id", fileId);
		q.setParameter("airline", requestedCompany);

		q.addScalar("status", StandardBasicTypes.STRING);
		q.setMaxResults(1);
		
		String level = (String)q.uniqueResult();
		sess.close();
		if(level != null){
			return level.equals(AccessRequest.RequestStatus.Approved.toString());
		}

		return false;
	}
	
	public static void censor(MatchHistory match, AccessLevelType level, String userCompany, List<PrivacyPermissions> plist){
		String s = ACCESS_NOT_GRANTED;
		PrivacyPermissions p1 = new PrivacyPermissions();
		PrivacyPermissions p2 = new PrivacyPermissions();
		String company1 = (match.getFile1()!=null?match.getFile1().getValidatingCompanycode():null);
		String company2 = (match.getFile2()!=null?match.getFile2().getValidatingCompanycode():null);
		
		for(PrivacyPermissions pl: plist){
			if(pl.getKey().getCompanycode().equals(company1) && pl.getKey().getLevel().equals(level)){
				p1 = pl;
			}
			if(pl.getKey().getCompanycode().equals(company2) && pl.getKey().getLevel().equals(level)){
				p2 = pl;
			}
		}
		
		if(company1 == null || company1.trim().length() == 0 || !company1.equals(userCompany)){
			censorFile(match.getFile1(), p1);
		} else {
			p1.setAllEnabled(true);
		}
		if(company2 == null || company2.trim().length() == 0 || !company2.equals(userCompany)){
			censorFile(match.getFile2(), p2);
		} else {
			p2.setAllEnabled(true);
		}
		
		for(MatchDetail d:match.getDetails()){
			if(d.getMatchtype() == null){
				System.out.println("ERROR:" + d.getDescription() + ":" + d.getContent1() + ":" + d.getContent2());
			}
			if(d.getMatchtype().equals(MatchType.name)){
				if(!p1.isAllEnabled() && !p1.isName() && !p1.isShowallclaiminfo())d.setContent1(s);
				if(!p2.isAllEnabled() && !p2.isName() && !p2.isShowallclaiminfo())d.setContent2(s);
			} else if (d.getMatchtype().equals(MatchType.address)){
				if(!p1.isAllEnabled() && !p1.isAddress() && !p1.isShowallclaiminfo())d.setContent1(s);
				if(!p2.isAllEnabled() && !p2.isAddress() && !p2.isShowallclaiminfo())d.setContent2(s);
			} else if (d.getMatchtype().equals(MatchType.phone)){
				if(!p1.isAllEnabled() && !p1.isPhonenumber() && !p1.isShowallclaiminfo())d.setContent1(s);
				if(!p2.isAllEnabled() && !p2.isPhonenumber() && !p2.isShowallclaiminfo())d.setContent2(s);
			} else if (d.getMatchtype().equals(MatchType.email)){
				if(!p1.isAllEnabled() && !p1.isEmail() && !p1.isShowallclaiminfo())d.setContent1(s);
				if(!p2.isAllEnabled() && !p2.isEmail() && !p2.isShowallclaiminfo())d.setContent2(s);
			} else if (d.getMatchtype().equals(MatchType.cc)){
				if(!p1.isAllEnabled() && !p1.isCc() && !p1.isShowallclaiminfo())d.setContent1(s);
				if(!p2.isAllEnabled() && !p2.isCc() && !p2.isShowallclaiminfo())d.setContent2(s);
			} else if (d.getMatchtype().equals(MatchType.drivers)){
				if(!p1.isAllEnabled() && !p1.isDrivers() && !p1.isShowallclaiminfo())d.setContent1(s);
				if(!p2.isAllEnabled() && !p2.isDrivers() && !p2.isShowallclaiminfo())d.setContent2(s);
			} else if (d.getMatchtype().equals(MatchType.ffn)){
				if(!p1.isAllEnabled() && !p1.isFfn() && !p1.isShowallclaiminfo())d.setContent1(s);
				if(!p2.isAllEnabled() && !p2.isFfn() && !p2.isShowallclaiminfo())d.setContent2(s);
			} else if (d.getMatchtype().equals(MatchType.ssn)){
				if(!p1.isAllEnabled() && !p1.isSsn() && !p1.isShowallclaiminfo())d.setContent1(s);
				if(!p2.isAllEnabled() && !p2.isSsn() && !p2.isShowallclaiminfo())d.setContent2(s);
			} else if (d.getMatchtype().equals(MatchType.passport)){
				if(!p1.isAllEnabled() && !p1.isPassport() && !p1.isShowallclaiminfo())d.setContent1(s);
				if(!p2.isAllEnabled() && !p2.isPassport() && !p2.isShowallclaiminfo())d.setContent2(s);
			} else if (d.getMatchtype().equals(MatchType.pnrloc)){
				if(!p1.isAllEnabled() && !p1.isPnrloc() && !p1.isShowallclaiminfo())d.setContent1(s);
				if(!p2.isAllEnabled() && !p2.isPnrloc() && !p2.isShowallclaiminfo())d.setContent2(s);
			} else if (d.getMatchtype().equals(MatchType.pnrdata)){
				if(!p1.isAllEnabled() && !p1.isPnrdata() && !p1.isShowallclaiminfo())d.setContent1(s);
				if(!p2.isAllEnabled() && !p2.isPnrdata() && !p2.isShowallclaiminfo())d.setContent2(s);
			} else if (d.getMatchtype().equals(MatchType.traveldate)){
				if(!p1.isAllEnabled() && !p1.isTraveldate() && !p1.isShowallclaiminfo())d.setContent1(s);
				if(!p2.isAllEnabled() && !p2.isTraveldate() && !p2.isShowallclaiminfo())d.setContent2(s);
			} else if (d.getMatchtype().equals(MatchType.ticketamount)){
				if(!p1.isAllEnabled() && !p1.isTicketamount() && !p1.isShowallclaiminfo())d.setContent1(s);
				if(!p2.isAllEnabled() && !p2.isTicketamount() && !p2.isShowallclaiminfo())d.setContent2(s);
			} else if (d.getMatchtype().equals(MatchType.dob)){
				if(!p1.isAllEnabled() && !p1.isDob() && !p1.isShowallclaiminfo())d.setContent1(s);
				if(!p2.isAllEnabled() && !p2.isDob() && !p2.isShowallclaiminfo())d.setContent2(s);
			} else if (d.getMatchtype().equals(MatchType.itin)){
				if(!p1.isAllEnabled() && !p1.isItin() && !p1.isShowallclaiminfo())d.setContent1(s);
				if(!p2.isAllEnabled() && !p2.isItin() && !p2.isShowallclaiminfo())d.setContent2(s);
			} else if (d.getMatchtype().equals(MatchType.claimRemarks)){
				if(!p1.isAllEnabled() && !p1.isClaimRemarks() && !p1.isShowallclaiminfo())d.setContent1(s);
				if(!p2.isAllEnabled() && !p2.isClaimRemarks() && !p2.isShowallclaiminfo())d.setContent2(s);
			} else if (d.getMatchtype().equals(MatchType.ipAddress)){
				if(!p1.isAllEnabled() && !p1.isIpAddresses() && !p1.isShowallclaiminfo())d.setContent1(s);
				if(!p2.isAllEnabled() && !p2.isIpAddresses() && !p2.isShowallclaiminfo())d.setContent2(s);
			}
			
			String c1=p1.getKey()!= null ? p1.getKey().getCompanycode():"NA";
			String c2=p2.getKey()!= null ? p2.getKey().getCompanycode():"NA";
			logger.debug(c1+c2+":"+d.getContent1()+":"+d.getContent2());
			
		}
	}
	
//	public void censorFile(File f, AccessLevelType level){
//		
//	}
	
	public static void censorFile(File f, PrivacyPermissions p){
		String s = ACCESS_NOT_GRANTED;
		Set<Person> persons = Consumer.getPersons(f);
		for(Person person:persons){
			if(!p.isName()){
				person.setFirstName(s);
				person.setMiddleName(s);
				person.setLastName(s);
				person.setFirstNameSoundex(s);
				person.setFirstNameDmp(s);
				person.setLastNameSoundex(s);
				person.setLastNameDmp(s);
			}
			if(!p.isEmail()){
				person.setEmailAddress(s);
			}
			if(!p.isDrivers()){
				person.setDriversLicenseState(s);
				person.setDriversLicenseProvince(s);
				person.setDriversLicenseCountry(s);
				person.setDriversLicenseNumber(s);
			}
			if(!p.isFfn()){
				person.setFfAirline(s);
				person.setFfNumber(s);
			}
			if(!p.isPassport()){
				person.setPassportIssuer(s);
				person.setPassportNumber(s);
			}
			if(!p.isSsn()){
				person.setSocialSecurity(s);
			}
			if(!p.isDob()){
				person.setDateOfBirth(null);
			}
		}
		
		if (!p.isIpAddresses()) {
			for (FsClaim claim: f.getClaims()) {
				if (claim.getIpAddresses() != null && !claim.getIpAddresses().isEmpty()) {
					for (FsIPAddress ip: claim.getIpAddresses()) {
						ip.setIpAddress(s);
						ip.setAssociation(s);
					}
				}
			}
		}
		
		Set<FsAddress> addresses = Consumer.getAddresses(f);
		for(FsAddress address:addresses){
			if(!p.isAddress()){
				address.setAddress1(s);
				address.setAddress2(s);
				address.setCity(s);
				address.setCountry(s);
				address.setLattitude(-1);
				address.setLongitude(-1);
				address.setState(s);
				address.setZip(s);
				address.setProvince(s);
			}
		}
		
		Set<Phone> phones = Consumer.getPhones(f);
		for(Phone phone:phones){
			if(!p.isPhonenumber()){
				phone.setPhoneNumber(s);
				phone.setAssociation(s);
			}
		}

		if(f.getClaims() != null){
			for(FsClaim claim: f.getClaims()){
				if(!p.isAmountclaimed()){
					claim.setAmountClaimed(-1);
				}
				if(!p.isAmountpaid()){
					claim.setAmountPaid(-1);
				}
				if(!p.isDenied()){
					//				f.getClaim().setDenied(null);
				}
				if(!p.isTraveldate()){
					claim.setTravelDate(null);
				}
				
				if(!p.isClaimRemarks()){
					claim.setClaimRemark(s);
				}
				for(Person per:claim.getClaimants()){
					if(!p.isName()){
						per.setFirstName(s);
						per.setMiddleName(s);
						per.setLastName(s);
						per.setFirstNameSoundex(s);
						per.setFirstNameDmp(s);
						per.setLastNameSoundex(s);
						per.setLastNameDmp(s);
					}
					if(!p.isEmail()){ 
						per.setEmailAddress(s);
					}
					if(!p.isDrivers()){
						per.setDriversLicenseState(s);
						per.setDriversLicenseProvince(s);
						per.setDriversLicenseCountry(s);
						per.setDriversLicenseNumber(s);
					}
					if(!p.isFfn()){
						per.setFfAirline(s);
						per.setFfNumber(s);
					}
					if(!p.isPassport()){
						per.setPassportIssuer(s);
						per.setPassportNumber(s);
					}
					if(!p.isSsn()){
						per.setSocialSecurity(s);
					}
					if(!p.isDob()){
						per.setDateOfBirth(null);
					}
					
					if(!p.isAddress()){
						per.getAddress().setAddress1(s);
						per.getAddress().setAddress2(s);
						per.getAddress().setCity(s);
						per.getAddress().setCountry(s);
						per.getAddress().setLattitude(-1);
						per.getAddress().setLongitude(-1);
						per.getAddress().setState(s);
						per.getAddress().setZip(s);
						per.getAddress().setProvince(s);
					}
				}
				
			}
		}
		
		
		//incident
		if(f.getIncident() != null){
			if(!p.isIncdate()){
				f.getIncident().setNumberDaysOpen(-1);
			}
			if(!p.isBdo()){
				f.getIncident().setNumberOfBdos(-1);
			}
			if(!p.isIncremarks()){
				f.getIncident().setRemarks(s);
			}
			if(!p.isBag() && f.getIncident().getBags() != null){
				for(Bag bag:f.getIncident().getBags()){
					bag.setBagColor(s);
					bag.setBagType(s);
					bag.setDescription(s);
					bag.setManufacturer(s);
				}
			}
		}
		
		if(f.getIncident() != null && f.getIncident().getReservation() != null){
			Reservation reservation = f.getIncident().getReservation();
			if(!p.isCc()){
				reservation.setCcExpMonth(-1);
				reservation.setCcExpYear(-1);
				reservation.setCcNumLastFour(s);
				reservation.setCcType(s);
			}
			if(!p.isPnrloc()){
				reservation.setRecordLocator(s);
			}
			if(!p.isTicketamount()){
				reservation.setTicketAmount(-1);
			}
			if(!p.isTraveldate()){
				reservation.setTravelDate(null);
			}
			if(!p.isItin()){
				reservation.setTripLength(-1);
			}
			
			if(reservation.getPnrData() != null){
				if(!p.isPnrdata()){
					reservation.getPnrData().setPnrData(s);
				}
				if(!p.isPnrloc()){
					reservation.getPnrData().setRecordLocator(s);
				}
			}
			
			if(reservation.getSegments() != null){
				for(Segment segment:reservation.getSegments()){
					if(!p.isItin()){
						segment.setArrival(s);
						segment.setDeparture(s);
						segment.setFlight(s);
						segment.setDate(null);
					}
				}
			}
		}
	}
		
	
	public static void censorFile(File f, AccessLevelType level, String userCompany, List<PrivacyPermissions> plist){
		String s = ACCESS_NOT_GRANTED;
		PrivacyPermissions p1 = new PrivacyPermissions();
		String company1 = f.getValidatingCompanycode();
		
		for(PrivacyPermissions p: plist){
			if(p.getKey().getCompanycode().equals(company1) && p.getKey().getLevel().equals(level)){
				p1 = p;
			}
		}
		
			if((company1 == null || company1.trim().length() == 0 || !company1.equals(userCompany))  && !p1.isShowallclaiminfo()){
				censorFile(f, p1);
			} else {
				p1.setAllEnabled(true);
			}
		
		
		/**/

	}
	
	public static boolean censorAttachments(List<FsAttachment> list, String valComp, AccessLevelType level, String userCompany, List<PrivacyPermissions> plist){
		String s = ACCESS_NOT_GRANTED;
		PrivacyPermissions p1 = new PrivacyPermissions();
		String company1 = valComp;
		
		for(PrivacyPermissions p: plist){
			if(p.getKey().getCompanycode().equals(company1) && p.getKey().getLevel().equals(level)){
				p1 = p;
			}
		}
		
		if((company1 == null || company1.trim().length() == 0 || !company1.equals(userCompany))  && !p1.isShowallclaiminfo()){
			if(!p1.isAttachment()){
				for (FsAttachment a:list){
					a.setId(-1);
				}
				return false;
			}
		} else {
			p1.setAllEnabled(true);
		}
		return true;
		
		
		/**/

	}

	private static ConcurrentHashMap<Long,TraceProgress> traceProgress = new ConcurrentHashMap<Long,TraceProgress>();
	
	public static void cleanTraceProgress(){
		long now = (new Date()).getTime();
		for(TraceProgress tp:traceProgress.values()){
			if(!tp.stillRunning() || now - tp.getStartTime() > 1000 * 60 * 5){
				traceProgress.remove(tp.getFileId());
			}
		}
	}
	
	
	private static ConsumerQueueElement startProducerThreads(File file, boolean isPrimary, Date createDate){
		ConsumerQueueElement element = ConsumerQueueElement.getInstance(file);
		TraceWrapper.startProducerGeoThread(element, file, isPrimary, createDate);
		return element;
	}
	
}
