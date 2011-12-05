package aero.nettracer.selfservice.fraud;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


import javax.ejb.Stateless;


import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import aero.nettracer.fs.model.Bag;
import aero.nettracer.fs.model.File;
import aero.nettracer.fs.model.FsAddress;
import aero.nettracer.fs.model.FsClaim;
import aero.nettracer.fs.model.FsIncident;
import aero.nettracer.fs.model.FsMatchHistoryAudit;
import aero.nettracer.fs.model.FsReceipt;
import aero.nettracer.fs.model.Person;
import aero.nettracer.fs.model.Phone;
import aero.nettracer.fs.model.PnrData;
import aero.nettracer.fs.model.Reservation;
import aero.nettracer.fs.model.Segment;
import aero.nettracer.fs.model.detection.AccessRequest;
import aero.nettracer.fs.model.detection.AccessRequest.RequestStatus;
import aero.nettracer.fs.model.detection.Blacklist;
import aero.nettracer.fs.model.detection.MatchHistory;
import aero.nettracer.fs.model.detection.TraceResponse;
import aero.nettracer.fs.model.detection.Whitelist;
import aero.nettracer.fs.model.messaging.FsMessage;
import aero.nettracer.fs.utilities.AuditUtil;
import aero.nettracer.fs.utilities.DataRetentionThread;
import aero.nettracer.fs.utilities.GeoCode;
import aero.nettracer.fs.utilities.GeoLocation;
import aero.nettracer.fs.utilities.InternationalException;
import aero.nettracer.fs.utilities.tracing.Consumer;
import aero.nettracer.fs.utilities.WhiteListUtil;
import aero.nettracer.fs.utilities.tracing.Producer;
import aero.nettracer.fs.utilities.tracing.TraceWrapper;
import aero.nettracer.serviceprovider.common.db.PrivacyPermissions;
import aero.nettracer.serviceprovider.common.db.PrivacyPermissions.AccessLevelType;
import aero.nettracer.serviceprovider.common.hibernate.HibernateWrapper;

import com.bagnet.nettracer.tracing.utils.DateUtils;

@Stateless
//@WebService
public class ClaimBean implements ClaimRemote, ClaimHome {
	private static final Logger logger = Logger.getLogger(ClaimBean.class);
	private static Thread DATA_RETENTION_THREAD;
	

	public ClaimBean(){
		if(DATA_RETENTION_THREAD == null){
			DataRetentionThread drt = new DataRetentionThread();
			DATA_RETENTION_THREAD = new Thread(drt, "DataRetentionThread");
			DATA_RETENTION_THREAD.start();
		}
	}
	
	public String echoTest(String s) {
		return "echo: " + s;
	}

	public TraceResponse traceFile(long fileId, int maxDelay, boolean isPrimary, boolean returnResults) {
		return Producer.matchFile(fileId, maxDelay, true, isPrimary, returnResults);
	}

	public long insertFile(File file) {
		Transaction t = null;
		Session sess = null;
		if(file.getValidatingCompanycode() == null){
			return -1;
		}
		try {
			File toSubmit = resetIdAndgeocode(file);

			sess = HibernateWrapper.getSession().openSession();
			t = sess.beginTransaction();
			if (toSubmit.getId() > 0) {
				logger.debug("delete and save");
				File toDelete = (File) sess.load(File.class, toSubmit.getId());

				if (toDelete.getClaims() != null) {
					for(FsClaim claim: toDelete.getClaims()){
						sess.delete(claim);
						
					} 
					toDelete.getClaims().clear();
				}

				if (toDelete.getIncident() != null) {
					FsIncident inc = toDelete.getIncident();
					inc.setFile(null);
					sess.delete(inc);
				}

				toDelete.setIncident(null);

				
				toDelete.setIncident(toSubmit.getIncident());
				if(toDelete.getIncident() != null){
					toDelete.getIncident().setFile(toDelete);
				}
				
				if(toSubmit.getClaims() != null){
					for(FsClaim claim:toSubmit.getClaims()){
						claim.setFile(toDelete);
						toDelete.getClaims().add(claim);
					}
				}
				
				toDelete.setStatusId(toSubmit.getStatusId());
				
				sess.saveOrUpdate(toDelete);
				t.commit();
				AuditUtil.saveActionAudit(AuditUtil.ACTION_UPDATE_FILE, file.getId(), file.getValidatingCompanycode());

			} else {
				logger.debug("saving:" + toSubmit.getId());
				sess.saveOrUpdate(toSubmit);
				t.commit();
				AuditUtil.saveActionAudit(AuditUtil.ACTION_CREATE_FILE, file.getId(), file.getValidatingCompanycode());
			}
			return toSubmit.getId();
		} catch (Exception e) {
			// logger.error("Error Saving: ", e);
			e.printStackTrace();
			if (t != null) {
				try {
					t.rollback();
				} catch (Exception ex) {
					// logger.error("Error Saving: ", ex);
					ex.printStackTrace();
				}
			}
			return -1;
		} finally {

			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static File resetIdAndgeocode(File file) throws Exception {
		if (file != null) {
			long temp = file.getSwapId();
			file.setSwapId(file.getId());
			file.setId(temp);

			//we might be breaking this associatation
//			if (file.getClaim() != null && file.getIncident() != null
//					&& file.getClaim().getIncident().equals(file.getIncident()) == false) {
//				throw new Exception("file incident does not match claim incident");
//			}

			
			if (file.getClaims() != null) {
				for(FsClaim claim:file.getClaims()){
					claim = (resetClaim(claim));//TODO this might be illegal
				}	
			} 
			if (file.getIncident() != null) {
				file.setIncident(resetIncident(file.getIncident()));
			}

			logger.debug(file.toString());
		}
		return file;
	}

	public static FsClaim resetClaim(FsClaim claim) {
		if (claim != null) {
			claim.setSwapId(claim.getId());
			claim.setId(0);

			claim.setClaimants(resetPersonId(claim.getClaimants()));
			claim.setSegments(resetSegmentId(claim.getSegments()));
			claim.setBlacklist(resetBlackListId(claim.getBlacklist()));
			claim.setReceipts(resetReceiptId(claim.getReceipts()));
		}
		return claim;
	}

	private static Set<FsReceipt> resetReceiptId(Set<FsReceipt> receipts) {
		if(receipts != null){
			for(FsReceipt receipt:receipts){
				receipt.setId(0);
				HashSet<FsAddress> addresses = new HashSet<FsAddress>();
				addresses.add(receipt.getAddress());
				resetAddressIdAndGeocode(addresses);
				
				HashSet<Phone> phones = new HashSet<Phone>();
				phones.add(receipt.getPhone());
				resetPhoneId(phones);
			}
		}
		return receipts;
	}

	public static FsIncident resetIncident(FsIncident inc) {
		if (inc != null) {
			inc.setId(0);

			inc.setReservation(resetReservation(inc.getReservation()));
			inc.setSegments(resetSegmentId(inc.getSegments()));
			inc.setBags(resetBagId(inc.getBags()));
			inc.setPassengers(resetPersonId(inc.getPassengers()));
		}
		return inc;
	}

	public static Reservation resetReservation(Reservation res) {
		if (res != null) {
			res.setId(0);
			if (res.getPurchaser() != null) {
				res.getPurchaser().setId(0);
				res.getPurchaser().setAddresses(resetAddressIdAndGeocode(res.getPurchaser().getAddresses()));
				res.getPurchaser().setPhones(resetPhoneId(res.getPurchaser().getPhones()));
			}

			res.setSegments(resetSegmentId(res.getSegments()));
			res.setCcWhitelist(resetWhiteListId(res.getCcWhitelist()));
			res.setPnrData(resetPnrDataId(res.getPnrData()));
			res.setPassengers(resetPersonId(res.getPassengers()));
			res.setPhones(resetPhoneId(res.getPhones()));
		}
		return res;
	}

	public static Set<Segment> resetSegmentId(Set<Segment> segments) {
		if (segments != null) {

			for (Segment segment : segments) {
				segment.setId(0);
			}
		}
		return segments;
	}

	public static Set<Bag> resetBagId(Set<Bag> bags) {
		if (bags != null) {
			for (Bag bag : bags) {
				bag.setId(0);
			}
		}
		return bags;
	}

	public static Set<Person> resetPersonId(Set<Person> persons) {
		if (persons != null) {
			for (Person person : persons) {
				person.setId(0);
				person.setAddresses(resetAddressIdAndGeocode(person.getAddresses()));
				person.setPhones(resetPhoneId(person.getPhones()));
			}
		}
		return persons;
	}

	public static Whitelist resetWhiteListId(Whitelist wlist) {
		if (wlist != null) {
			wlist.setId(0);
		}
		return wlist;
	}

	public static Blacklist resetBlackListId(Blacklist blist) {
		if (blist != null) {
			blist.setId(0);
		}
		return blist;
	}

	public static PnrData resetPnrDataId(PnrData pnrData) {
		if (pnrData != null) {
			pnrData.setId(0);
		}
		return pnrData;
	}

	public static Set<Phone> resetPhoneId(Set<Phone> phones) {
		if (phones != null) {
			for (Phone phone : phones) {
				if(phone != null){
					phone.setId(0);
					if(phone.getReceipt() != null){
						phone.getReceipt().setId(0);
					}

					phone.setPhoneNumber(aero.nettracer.fs.utilities.Util.removeNonNumeric(phone.getPhoneNumber()));
					phone.setWhitelist(WhiteListUtil.isPhoneWhiteListed(phone.getPhoneNumber()));
				}
			}
		}
		return phones;
	}

	public static Set<FsAddress> resetAddressIdAndGeocode(Set<FsAddress> addresses) {
		if (addresses != null) {
			for (FsAddress address : addresses) {
				if(address != null && address.getReceipt() != null){
					address.getReceipt().setId(0);
				}
				if(address != null && (address.getId() != 0 || address.getGeocodeType() == 0)){
					address.setId(0);
					address.setWhitelist(WhiteListUtil.isAddressWhiteListed(address));

					// GEOCODING ALL ADDRESSES POSSIBLE ON SAVE
					// Note: Because this is not persisted to the client,
					// it must happen every time.
					try {
						GeoLocation loc = null;
						if(address.getAddress1() != null){
							loc = GeoCode.locate(address.getAddress1().replaceAll("[\\.#\"><%!@$%^&*()_+-/]", " "),
									address.getCity(), address.getState(), address.getZip(), address.getProvince(),
									address.getCountry(), null);
						}

						if (loc != null) {

							address.setGeocodeType(loc.getType());
							address.setLattitude(loc.getLatitude());
							address.setLongitude(loc.getLongitude());
						}
					} catch (InternationalException e) {
						// Ignore; not pertinent at this time.
					} catch (Exception e) {
						// Log error only
						e.printStackTrace();
					}
				}
			}
		}
		return addresses;
	}

	//TODO delete this
	public static FsClaim createFsClaim() {

		// create the claim
		FsClaim claim = new FsClaim();
		claim.setAirline("WS");

		// create the person
		Person person = new Person();

		// create the claim currency
		String currency = "USD";

		// create the address
		FsAddress address = new FsAddress();
		address.setPerson(person);
		LinkedHashSet<FsAddress> addresses = new LinkedHashSet<FsAddress>();
		addresses.add(address);

		// create the phones
		LinkedHashSet<Phone> phones = new LinkedHashSet<Phone>();

		// create the person
		person.setAddresses(addresses);
		person.setPhones(phones);
		person.setClaim(claim);
		LinkedHashSet<Person> claimants = new LinkedHashSet<Person>();
		claimants.add(person);

		// create the pnr data
		PnrData pnrData = new PnrData();

		// create the reservation
		Reservation reservation = new Reservation();
		reservation.setPassengers(new LinkedHashSet<Person>());
		reservation.setPhones(new LinkedHashSet<Phone>());
		reservation.setPnrData(pnrData);
		reservation.setSegments(new LinkedHashSet<Segment>());
		pnrData.setReservation(reservation);

		// set the fraud incident
		FsIncident fsIncident = new FsIncident();
		fsIncident.setReservation(reservation);
		reservation.setIncident(fsIncident);
		person.setIncident(fsIncident);
//		fsIncident.setClaim(claim);
		fsIncident.setPassengers(claimants);

		// create the claim
		claim.setAmountClaimedCurrency(currency);
		claim.setClaimants(claimants);
		claim.setIncident(fsIncident);

		return claim;

	}

	public int getIncidentCacheSize() {
		return TraceWrapper.getIncidentCacheSize();
	}

	public int getClaimCacheSize() {
		return TraceWrapper.getClaimCacheSize();
	}

	@Override
	public TraceResponse getFileMatches(long fileId) {
		TraceResponse tr = new TraceResponse();
		File file = TraceWrapper.loadFileFromCache(fileId);
		Set<MatchHistory> mh = Producer.getCensoredFileMatches(fileId);
		tr.setMatchHistory(mh);
		tr.setTraceComplete(true);
		Set<FsAddress> addresses = Consumer.getAddresses(file);
		Producer.analyzeFile(file, tr, addresses);
		return tr;
	}
	
	public static boolean hasRequest(long fileId, String requestingAirline){
		Session sess = HibernateWrapper.getSession().openSession();
		String sql = "from aero.nettracer.fs.model.detection.AccessRequest where file.id = :id and requestedAirline =:airline";
		Query q = sess.createQuery(sql);
		q.setParameter("id", fileId);
		q.setParameter("airline", requestingAirline);
		List results = q.list();
		if(results != null && results.size() > 0){
			return true;
		} else {
			return false;
		}
		
	}
	

	@Override
	public void requestAccess(long fileId, long matchHistory, String agent, String requestingAirline, String message) {
		//TODO do we need to check for dup request?
//		if(hasRequest(fileId, requestingAirline)){
//			return;
//		}
		AuditUtil.saveActionAudit(AuditUtil.ACTION_REQUEST_ACCESS, fileId, requestingAirline);
		
		Session sess = HibernateWrapper.getSession().openSession();

		AccessRequest request = new AccessRequest();
		File file = (File) sess.load(File.class, fileId);
		MatchHistory mh = (MatchHistory) sess.load(MatchHistory.class, matchHistory);
		
		boolean autosend = false;
		if(file.getValidatingCompanycode() != null){
			PrivacyPermissionsBean bean = new PrivacyPermissionsBean();
			PrivacyPermissions permissions = bean.getPrivacyPermissions(file.getValidatingCompanycode(), AccessLevelType.req);
			if (permissions != null) {
				if(permissions.isAutosend()){
					autosend = true;
				}
			}
		}
		
		request.setFile(file);
		request.setMatchHistory(mh);
		request.setRequestedAgent(agent);
		request.setRequestedDate(DateUtils.convertToGMTDate(new Date()));
		request.setRequestedAirline(requestingAirline);
		if(autosend){
			request.setStatus(RequestStatus.Approved);
		} else {
			request.setStatus(RequestStatus.Created);
		}
		if (message != null && message.trim().length() > 0) {
			FsMessage m = new FsMessage();
			m.setSenderName(agent);
			m.setMessage(message);
			m.setTimestamp(DateUtils.convertToGMTDate(new Date()));
			request.setMessage(m);
		}

		Transaction t = null;
		try {
			t = sess.beginTransaction();
			sess.save(request);
			t.commit();
		} catch (Exception e) {
			if (t != null)
				t.rollback();
		} finally {
			sess.close();
		}

	}

	@Override
	public List<AccessRequest> getOutstandingRequests(String airlineId, int begin, int perPage) {
		AuditUtil.saveActionAudit(AuditUtil.ACTION_GET_ACCESS_REQUESTS, -1, airlineId);
		Session sess = HibernateWrapper.getSession().openSession();
		String sql = "from aero.nettracer.fs.model.detection.AccessRequest ar where ar.status = :status and ar.file.validatingCompanycode = :airline";

		Query q = sess.createQuery(sql);

		q.setParameter("status", AccessRequest.RequestStatus.Created);
		q.setParameter("airline", airlineId);
		q.setMaxResults(perPage);
		q.setFirstResult(begin);

		return q.list();

	}

	@Override
	public void approveRequest(long requestId, String message, String agent) {
		RequestStatus status = RequestStatus.Approved;
		approveOrDenyRequest(requestId, message, agent, status);
	}

	@Override
	public void denyRequest(long requestId, String message, String agent) {
		RequestStatus status = RequestStatus.Denied;
		approveOrDenyRequest(requestId, message, agent, status);
	}

	@Override
	public File getFile(long fileId, String airline) {
		//TODO need to incorporate killswitch
		
		Session sess = HibernateWrapper.getSession().openSession();
		File fullFile = null;

		try {
			fullFile = (File) sess.load(File.class, fileId);
			if (fullFile != null) {
				AuditUtil.saveActionAudit(AuditUtil.ACTION_GET_FILE, fullFile.getId(), airline);
				String sql = "from aero.nettracer.fs.model.detection.AccessRequest ar where ar.file.id = :fileId and ar.requestedAirline = :requestingAirline";

				Query q = sess.createQuery(sql);
				q.setParameter("requestingAirline", airline);
				q.setParameter("fileId", fileId);

				List<AccessRequest> requests = q.list();
				RequestStatus highest = null;
				if (requests != null && requests.size() > 0) {
					highest = RequestStatus.Created;
					for (AccessRequest req : requests) {
						if (req.getStatus().equals(RequestStatus.Approved)) {
							highest = RequestStatus.Approved;
						}
					}
				}
				
				if (highest != null) {
					
					AccessLevelType access = AccessLevelType.def;
					if (highest == RequestStatus.Approved) {
						access = AccessLevelType.req;
					}
					PrivacyPermissions p1 = null;
					for(PrivacyPermissions p: PrivacyPermissionsBean.getPrivacyPermissions()){
						if(p.getKey().getCompanycode().equals(airline) && p.getKey().getLevel().equals(access)){
							p1 = p;
						}
					}
					Producer.censorFile(fullFile, p1);
					return fullFile;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sess.close();
		}
		return fullFile;
	}


	private void approveOrDenyRequest(long requestId, String message, String agent, RequestStatus status) {
		Session sess = HibernateWrapper.getSession().openSession();
		AccessRequest request = (AccessRequest) sess.load(AccessRequest.class, requestId);
		request.setStatus(status);
		request.setResponseAgent(agent);
		request.setResponseDate(DateUtils.convertToGMTDate(new Date()));

		if (message != null && message.trim().length() > 0) {
			FsMessage m = new FsMessage();
			m.setSenderName(agent);
			m.setMessage(message);
			m.setTimestamp(DateUtils.convertToGMTDate(new Date()));
			request.setMessage(m);
		}

		Transaction t = null;
		try {
			t = sess.beginTransaction();
			sess.save(request);
			t.commit();
		} catch (Exception e) {
			if (t != null)
				t.rollback();
		} finally {
			if(status.equals(RequestStatus.Approved)){
				AuditUtil.saveActionAudit(AuditUtil.ACTION_APPROVE_REQUEST, request.getFile().getId(), request.getFile().getValidatingCompanycode());
			} else {
				AuditUtil.saveActionAudit(AuditUtil.ACTION_DENY_REQUEST, request.getFile().getId(), request.getFile().getValidatingCompanycode());
			}
			sess.close();
		}
	}

	@Override
	public int getOutstandingRequetsCount(String airlineId) {
		Session sess = HibernateWrapper.getSession().openSession();
		String sql = "select count(ar.id) from aero.nettracer.fs.model.detection.AccessRequest ar where ar.status = :status and ar.file.incident.airline = :airline";

		Query q = sess.createQuery(sql);

		q.setParameter("status", AccessRequest.RequestStatus.Created);
		q.setParameter("airline", airlineId);

		List list = q.list();
		return ((Long) list.get(0)).intValue();
	}

	public boolean deleteMatch(Set<Long>matchIds){
		Session sess = HibernateWrapper.getSession().openSession();
		try{
			Transaction t = sess.getTransaction();
			t.begin();
			for(Long id:matchIds){
				MatchHistory mh = (MatchHistory)sess.load(MatchHistory.class, id);
				mh.setDeleted(true);
				sess.saveOrUpdate(mh);
				Set<FsMatchHistoryAudit> s = new HashSet<FsMatchHistoryAudit>();
				s.add(new FsMatchHistoryAudit(id, null));
				AuditUtil.saveActionAudit(AuditUtil.ACTION_DELETE_MATCH, mh.getFile1().getId(), mh.getFile1().getValidatingCompanycode(), s);
			}
			t.commit();
			sess.close();
			return true;
		} catch (Exception e){
			e.printStackTrace();
			sess.close();
			return false;
		}
	}
	
	@Override
	public boolean deleteMatch(long matchId) {
		Session sess = HibernateWrapper.getSession().openSession();
		try{

			MatchHistory mh = (MatchHistory)sess.load(MatchHistory.class, matchId);
			mh.setDeleted(true);
			sess.saveOrUpdate(mh);
			sess.close();
			Set<FsMatchHistoryAudit> s = new HashSet<FsMatchHistoryAudit>();
			s.add(new FsMatchHistoryAudit(matchId, null));
			AuditUtil.saveActionAudit(AuditUtil.ACTION_DELETE_MATCH, mh.getFile1().getId(), mh.getFile1().getValidatingCompanycode(), s);
			return true;
		} catch (Exception e){
			e.printStackTrace();
			sess.close();
			return false;
		}
	}
	
	
	public void deleteOldFiles(){
		String sql = "select distinct(key.companycode) from aero.nettracer.serviceprovider.common.db.PrivacyPermissions";
		Session sess = HibernateWrapper.getSession().openSession();
		try{
			Query q = sess.createQuery(sql);

			List<String> list = (List<String>)q.list();
			
			for(String companycode:list){
				System.out.println("companycode: " + companycode);
				deleteOldFiles(companycode);
			}
			
		}catch (Exception e){
			e.printStackTrace();
			sess.close();
		}
	}
	
	private Date getDeleteDate(String companycode) throws Exception{
		PrivacyPermissionsBean bean = new PrivacyPermissionsBean();
		PrivacyPermissions p = bean.getPrivacyPermissions(companycode, PrivacyPermissions.AccessLevelType.def);
		if(p != null && p.getRetention() > 0){
			GregorianCalendar cal = new GregorianCalendar();
			cal.add(Calendar.YEAR, -p.getRetention());
			System.out.println(cal.getTime());
			return cal.getTime();
		} else {
			throw new Exception("unable to determine retention policy for " + companycode);
		}
	}
	
	public void deleteOldFiles(String companycode){
		String sql = "select f.id from aero.nettracer.fs.model.File f left outer join f.incident i left outer join f.claims c" +
				" where f.validatingCompanycode = :companycode and (i.timestampOpen < :date or c.claimDate < :date) ";
		
		Session sess = HibernateWrapper.getSession().openSession();
		try{
			Query q = sess.createQuery(sql);

			q.setParameter("date", getDeleteDate(companycode));
			q.setParameter("companycode", companycode);

			List<Long> list = (List<Long>)q.list();
			
			for(Long id:list){
				System.out.println("deleting: " + id);
				if(!deleteFile(id)){
					throw new Exception("failed to delete file:" + id);
				}
			}
			AuditUtil.saveActionAudit(AuditUtil.ACTION_DATARETENTION, -1, companycode);
			
		}catch (Exception e){
			e.printStackTrace();
			sess.close();
		}
	}
	
	
	public boolean deleteFile(long id){
		Session sess = HibernateWrapper.getSession().openSession();
		try{
			File file = (File)sess.load(File.class, id);
			if(file != null){
				try{
					Transaction t = sess.getTransaction();
					t.begin();
					sess.delete(file);
					t.commit();
					sess.close();
					AuditUtil.saveActionAudit(AuditUtil.ACTION_DELETE_FILE, id, (file!=null?file.getValidatingCompanycode():null));
					return true;
				} catch (Exception e){
					e.printStackTrace();
					sess.close();
					return false;
				}
			} else {
				return true;
			}
		} catch (Exception e){
			e.printStackTrace();
			sess.close();
			return false;
		}
	}
	
	
}
