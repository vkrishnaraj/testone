package aero.nettracer.selfservice.fraud;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import aero.nettracer.fs.model.Bag;
import aero.nettracer.fs.model.File;
import aero.nettracer.fs.model.FsAddress;
import aero.nettracer.fs.model.FsClaim;
import aero.nettracer.fs.model.FsIncident;
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
import aero.nettracer.fs.model.messaging.Message;
import aero.nettracer.fs.utilities.GeoCode;
import aero.nettracer.fs.utilities.GeoLocation;
import aero.nettracer.fs.utilities.InternationalException;
import aero.nettracer.fs.utilities.tracing.Producer;
import aero.nettracer.fs.utilities.tracing.TraceWrapper;
import aero.nettracer.serviceprovider.common.db.PrivacyPermissions;
import aero.nettracer.serviceprovider.common.db.PrivacyPermissions.AccessLevelType;
import aero.nettracer.serviceprovider.common.hibernate.HibernateWrapper;

import com.bagnet.nettracer.tracing.utils.DateUtils;

@Stateless
public class ClaimBean implements ClaimRemote, ClaimHome {

	public static boolean debug = false;

	public String echoTest(String s) {
		return "echo: " + s;
	}

	public TraceResponse traceFile(long fileId, int maxDelay) {
		return Producer.matchFile(fileId, maxDelay, true);
	}

	public long insertFile(File file) {
		Transaction t = null;
		Session sess = null;
		try {
			File toSubmit = resetIdAndgeocode(file);

			sess = HibernateWrapper.getSession().openSession();
			t = sess.beginTransaction();
			if (toSubmit.getId() > 0) {
				if (debug)
					System.out.println("delete and save");
				File toDelete = (File) sess.load(File.class, toSubmit.getId());
				if (toDelete.getClaim() != null) {
					FsClaim claim = toDelete.getClaim();
					claim.setFile(null);
					if (claim.getIncident() != null) {
						FsIncident inc = toDelete.getClaim().getIncident();
						claim.getIncident().setFile(null);
					}
					sess.delete(claim);
					toDelete.setClaim(toSubmit.getClaim());
					if (toDelete.getClaim() != null) {
						toDelete.getClaim().setFile(toDelete);
					}
					toDelete.setIncident(toSubmit.getIncident());
					if (toDelete.getIncident() != null) {
						toDelete.getIncident().setFile(toDelete);
					}

				} else if (toDelete.getIncident() != null) {
					FsIncident inc = toDelete.getIncident();
					inc.setFile(null);
					sess.delete(inc);
					toDelete.setIncident(toSubmit.getIncident());
					if (toDelete.getIncident() != null) {
						toDelete.getIncident().setFile(toDelete);
					}
				}

				// toDelete.setClaim(null);
				// toDelete.setIncident(null);
				// sess.saveOrUpdate(toDelete);
				// t.commit();
				// sess.evict(toDelete);
				// sess.close();
				// sess = HibernateWrapper.getSession().openSession();
				// t = sess.beginTransaction();

				// File toAdd = (File)sess.load(File.class,toSubmit.getId());
				// toAdd.setClaim(toSubmit.getClaim());
				// if(toAdd.getClaim() != null){
				// toAdd.getClaim().setFile(toAdd);
				// }
				// toAdd.setIncident(toSubmit.getIncident());
				// if(toAdd.getIncident() != null){
				// toAdd.getIncident().setFile(toAdd);
				// }
				// sess.saveOrUpdate(toAdd);
				// t.commit();

				sess.saveOrUpdate(toDelete);
				t.commit();

			} else {
				if (debug)
					System.out.println("saving:" + toSubmit.getId());
				sess.saveOrUpdate(toSubmit);
				t.commit();
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

			if (file.getClaim() != null && file.getIncident() != null
					&& file.getClaim().getIncident().equals(file.getIncident()) == false) {
				throw new Exception("file incident does not match claim incident");
			}

			if (file.getClaim() != null) {
				file.setClaim(resetClaim(file.getClaim()));
			} else if (file.getIncident() != null) {
				file.setIncident(resetIncident(file.getIncident()));
			}

			if (debug)
				System.out.println(file.toString());
		}
		return file;
	}

	public static FsClaim resetClaim(FsClaim claim) {
		if (claim != null) {
			claim.setSwapId(claim.getId());
			claim.setId(0);

			claim.setIncident(resetIncident(claim.getIncident()));
			claim.setClaimants(resetPersonId(claim.getClaimants()));
			claim.setSegments(resetSegmentId(claim.getSegments()));
			claim.setBlacklist(resetBlackListId(claim.getBlacklist()));
		}
		return claim;
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
				phone.setId(0);
				phone.setPhoneNumber(aero.nettracer.fs.utilities.Util.removeNonNumeric(phone.getPhoneNumber()));
			}
		}
		return phones;
	}

	public static Set<FsAddress> resetAddressIdAndGeocode(Set<FsAddress> addresses) {
		if (addresses != null) {
			for (FsAddress address : addresses) {

				address.setId(0);
				// GEOCODING ALL ADDRESSES POSSIBLE ON SAVE
				// Note: Because this is not persisted to the client,
				// it must happen every time.
				try {
					GeoLocation loc = null;
					loc = GeoCode.locate(address.getAddress1().replaceAll("[\\.#\"><%!@$%^&*()_+-/]", " "),
							address.getCity(), address.getState(), address.getZip(), address.getProvince(),
							address.getCountry(), null);

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
		return addresses;
	}

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
		fsIncident.setClaim(claim);
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
	public Set<MatchHistory> getFileMatches(long fileId) {
		return Producer.getCensoredFileMatches(fileId);
	}

	@Override
	public void requestAccess(long fileId, long matchHistory, String agent, String requestingAirline, String message) {

		Session sess = HibernateWrapper.getSession().openSession();

		AccessRequest request = new AccessRequest();
		File file = (File) sess.load(File.class, fileId);
		MatchHistory mh = (MatchHistory) sess.load(MatchHistory.class, matchHistory);
		request.setFile(file);
		request.setMatchHistory(mh);
		request.setRequestedAgent(agent);
		request.setRequestedDate(DateUtils.convertToGMTDate(new Date()));
		request.setRequestedAirline(requestingAirline);
		if (message != null && message.trim().length() > 0) {
			Message m = new Message();
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
		Session sess = HibernateWrapper.getSession().openSession();
		String sql = "from aero.nettracer.fs.model.detection.AccessRequest ar where ar.status = :status and ar.file.incident.airline = :airline";

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
		RequestStatus status = RequestStatus.Approved;
		approveOrDenyRequest(requestId, message, agent, status);
	}

	@Override
	public File getFile(long fileId, String airline) {
		Session sess = HibernateWrapper.getSession().openSession();
		File file = null;

		try {
			File fullFile = (File) sess.load(File.class, fileId);
			if (fullFile != null) {
				String sql = "from aero.nettracer.fs.model.detection.AccessRequest ar where ar.file.id = :fileId and ar.requestedAirline = :requestingAirline";

				Query q = sess.createQuery(sql);
				q.setParameter("requestingAirline", airline);

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
		return file;
	}

	@Override
	public TraceResponse traceFile(File file, int maxDelay, boolean persistResults) {
		// TODO Auto-generated method stub
		return null;
	}

	private void approveOrDenyRequest(long requestId, String message, String agent, RequestStatus status) {
		Session sess = HibernateWrapper.getSession().openSession();
		AccessRequest request = (AccessRequest) sess.load(AccessRequest.class, requestId);
		request.setStatus(status);
		request.setResponseAgent(agent);
		request.setResponseDate(DateUtils.convertToGMTDate(new Date()));

		if (message != null && message.trim().length() > 0) {
			Message m = new Message();
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
	public int getOutstandingRequetsCount(String airlineId) {
		Session sess = HibernateWrapper.getSession().openSession();
		String sql = "select count(ar.id) from aero.nettracer.fs.model.detection.AccessRequest ar where ar.status = :status and ar.file.incident.airline = :airline";

		Query q = sess.createQuery(sql);

		q.setParameter("status", AccessRequest.RequestStatus.Created);
		q.setParameter("airline", airlineId);

		List list = q.list();
		return ((Long) list.get(0)).intValue();
	}

}
