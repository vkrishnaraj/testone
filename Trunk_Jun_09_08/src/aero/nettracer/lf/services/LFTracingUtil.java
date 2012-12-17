package aero.nettracer.lf.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.bagnet.nettracer.match.StringCompare;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.dao.lf.SubCompanyDAO;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.lf.LFAddress;
import com.bagnet.nettracer.tracing.db.lf.LFCategory;
import com.bagnet.nettracer.tracing.db.lf.LFFound;
import com.bagnet.nettracer.tracing.db.lf.LFItem;
import com.bagnet.nettracer.tracing.db.lf.LFLossInfo;
import com.bagnet.nettracer.tracing.db.lf.LFLost;
import com.bagnet.nettracer.tracing.db.lf.LFObject;
import com.bagnet.nettracer.tracing.db.lf.LFPerson;
import com.bagnet.nettracer.tracing.db.lf.LFPhone;
import com.bagnet.nettracer.tracing.db.lf.LFSegment;
import com.bagnet.nettracer.tracing.db.lf.LFSubCategory;
import com.bagnet.nettracer.tracing.db.lf.Subcompany;
import com.bagnet.nettracer.tracing.db.lf.detection.LFMatchDetail;
import com.bagnet.nettracer.tracing.db.lf.detection.LFMatchHistory;
import com.bagnet.nettracer.tracing.utils.TracerProperties;
import com.bagnet.nettracer.tracing.utils.general.AlertEmail;
import com.bagnet.nettracer.tracing.utils.general.Logger;
import com.bagnet.nettracer.tracing.utils.lf.TraceHandler;

public class LFTracingUtil {
	
	private static ConcurrentHashMap<Long, LFLost> lostCache;
	private static ConcurrentHashMap<Long, LFFound> foundCache;
	
	private static long lastCacheClean = 0;
	private static long lastCacheAlert = 0;
	
	private static final int DEFAULT_CACHE_SIZE = 20000;
	private static final long DEFAULT_CACHE_EXPIRE = 180 * 60000;//3h
	private static final int DEFAULT_CACHE_EXPRIE_RAND = 120;//2h
	private static final long DEFAULT_CACHE_CLEAN_INTERVAL = 30 * 60000;//30min
	private static final long CACHE_ALERT_INTERVAL = 240 * 60000;//4h
	private static final double CACHE_ALERT_PERCENT = 0.9;

	
	private static boolean hasExpired(LFObject o){
		long now = new Date().getTime();
		long expire = PropertyBMO.getValueAsInt(PropertyBMO.LF_TRACING_CACHE_EXPIRE) * 60000;
		return now - o.getLastLoaded() > (expire!=0?expire:DEFAULT_CACHE_EXPIRE);
	}
	
	private static void cacheFullSendAlert(){
		long now = new Date().getTime();
		if(now - lastCacheAlert > CACHE_ALERT_INTERVAL){
			int fsize = foundCache.size();
			int lsize = lostCache.size();
			boolean clean = cleanCache();
			int fcleanSize = foundCache.size();
			int lcleanSize = lostCache.size();
			lastCacheAlert = now;
			AlertEmail.sendAlertEmail("LFC Tracing Cache",
					"Cache for LFC Tracing has exceeded limit<br/>" +
					"Lost cache preclean:   " + lsize + "<br/>" +
					"Lost cache post clean: " + lcleanSize + "<br/>" + 
					"Found cache preclean:   " + fsize + "<br/>" +
					"Found cache post clean: " + fcleanSize + "<br/>" +
					"Instance Label " + TracerProperties.getInstanceLabel() + 
					"Alert interval " + CACHE_ALERT_INTERVAL + "<br/>" +
					"Cache Cleaned: " + clean);
		}
	}
	
	protected static long getExpireOffset(){
		int offset = PropertyBMO.getValueAsInt(PropertyBMO.LF_TRACING_CACHE_EXPIRE_RAND);
		offset = offset!=0?offset:DEFAULT_CACHE_EXPRIE_RAND;
		return (long)Math.floor(offset*Math.random()*60000);
	}
	
	public static LFFound loadFound(long foundId, LFServiceBean bean, boolean forceReload){
		if(PropertyBMO.isTrue(PropertyBMO.LF_TRACING_USE_CACHE)){
			int cacheSize = PropertyBMO.getValueAsInt(PropertyBMO.LF_TRACING_CACHE_SIZE);
			cacheSize = cacheSize!=0?cacheSize:DEFAULT_CACHE_SIZE;
			if(foundCache == null){
				foundCache = new ConcurrentHashMap<Long, LFFound>(cacheSize);
			}
			Long id = new Long(foundId);
			if(!forceReload && foundCache.containsKey(id) && !hasExpired((LFObject)foundCache.get(id)) ){
				return (LFFound)foundCache.get(id);
			} else {
				LFFound found = bean.getFoundItem(foundId);
				found.setLastLoaded(new Date().getTime() + getExpireOffset());
				if(foundCache.size() < cacheSize){
					foundCache.put(id, found);
				}
				if (foundCache.size() > (cacheSize * CACHE_ALERT_PERCENT)){
					cacheFullSendAlert();
				}
				return found;
			}
		} else {
			return bean.getFoundItem(foundId);
		}
	}
	
	public static LFLost loadLost(long lostId, LFServiceBean bean, boolean forceReload){
		if(PropertyBMO.isTrue(PropertyBMO.LF_TRACING_USE_CACHE)){
			int cacheSize = PropertyBMO.getValueAsInt(PropertyBMO.LF_TRACING_CACHE_SIZE);
			cacheSize = cacheSize!=0?cacheSize:DEFAULT_CACHE_SIZE;
			if(lostCache == null){
				lostCache = new ConcurrentHashMap<Long, LFLost>(cacheSize);
			}
			Long id = new Long(lostId);
			if(!forceReload && lostCache.containsKey(id) && !hasExpired((LFObject)lostCache.get(id)) ){
				return (LFLost)lostCache.get(id);
			} else {
				LFLost lost = bean.getLostReport(lostId);
				lost.setLastLoaded(new Date().getTime() + getExpireOffset());
				if(lostCache.size() < cacheSize){
					lostCache.put(id, lost);
				} 
				if (lostCache.size() > (cacheSize * CACHE_ALERT_PERCENT)){
					cacheFullSendAlert();
				}
				return lost;
			}
		} else {
			return bean.getLostReport(lostId);
		}
	}
	
	
	public static synchronized boolean cleanCache(){
		long interval = PropertyBMO.getValueAsInt(PropertyBMO.LF_TRACING_CACHE_CLEANUP_INTERVAL) * 60000;
		interval = interval!=0?interval:DEFAULT_CACHE_CLEAN_INTERVAL;
		long now = new Date().getTime();
		if(now - lastCacheClean > interval){
			lastCacheClean = now;
			if(lostCache != null){
				for(LFLost lost:lostCache.values()){
					if(hasExpired(lost)){
						lostCache.remove(new Long(lost.getId()));
					}
				}
			}
			if(foundCache != null){
				for(LFFound found:foundCache.values()){
					if(hasExpired(found)){
						foundCache.remove(new Long(found.getId()));
					}
				}
			}
			return true;
		}
		return false;
	}
	
	private static String replaceNull(String string) {
		  if (string == null) return "";
		  return string.trim();
	  }
	
	private static LFSubCategory getSubCategory(long id){
		Session sess = HibernateWrapper.getSession().openSession();
		LFSubCategory f = null;
		try{
			f = (LFSubCategory) sess.load(LFSubCategory.class, id);

		}catch (Exception e){
			e.printStackTrace();
		}
		finally{
			sess.close();
		}
		return f;
	}
	
	private static LFCategory getCategory(long id){
		Session sess = HibernateWrapper.getSession().openSession();
		LFCategory f = null;
		try{
			f = (LFCategory) sess.load(LFCategory.class, id);

		}catch (Exception e){
			e.printStackTrace();
		}
		finally{
			sess.close();
		}
		return f;
	}
	
	public static List<Long> getAvisPotentialLostId(LFFound found){
		String sql = "select l.id from LFLost l " +
		" left outer join lfitem i on l.id = i.lost_id " +
		" left outer join lflossinfo li on l.lossInfo_id = li.id " +
		" where l.status_ID = :status" +
		" and i.disposition_status_ID = :disposition" +
		" and (li.lossdate is null or li.lossdate <= :founddate) ";
		boolean hasLocation = false;
		if(found != null && found.getLocation() != null){
			sql += " and (li.origin_station_ID = :foundstation " +
			" or li.destination_station_ID = :foundstation)" ;
			hasLocation = true;
		}
		Session sess = null;
		try{
			sess = HibernateWrapper.getSession().openSession();
			SQLQuery q = sess.createSQLQuery(sql);
			q.setParameter("status", TracingConstants.LF_STATUS_OPEN);
			q.setParameter("disposition", TracingConstants.LF_DISPOSITION_OTHER);
			q.setDate("founddate", found.getFoundDate());
			if(hasLocation){
				q.setParameter("foundstation", found.getLocation().getStation_ID());
			}
			q.addScalar("id", Hibernate.LONG);
			List<Long> results = (List<Long>)q.list();
			sess.close();
			return results;

		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(sess != null && sess.isOpen()){
				sess.close();
			}
		}
		return null;
	}
	
	public static List<Long> getLFCPotentialLostId(LFFound found, boolean isPrimary){
		String sql = "select distinct(l.id) id from lflost l  " +
		" left outer join lfitem i on l.id = i.lost_id " +
		" left outer join lflossinfo li on l.lossInfo_id = li.id " +
		" left outer join lfsegment s on l.id = s.lost_id " +
		" where l.status_ID = :status" +
		" and i.disposition_status_ID = :disposition" +
		" and (li.lossdate is null or li.lossdate <= :founddate) ";
		boolean hasLocation = false;
		//loupas - removing limiting potentail list by reservation location
		//update leaving this in for now....until we change our mind again
		if(found != null && found.getLocation() != null){
			if(isPrimary){
				sql += " and (s.destination_station_ID = :foundstation or s.origin_station_ID = :foundstation2)" ;
			} else {
				sql += " and (s.destination_station_ID != :foundstation and s.origin_station_ID != :foundstation2)" ;
			}
			hasLocation = true;
		}
		Session sess = null;
		try{
			sess = HibernateWrapper.getSession().openSession();
			SQLQuery q = sess.createSQLQuery(sql);
			q.setParameter("status", TracingConstants.LF_STATUS_OPEN);
			q.setParameter("disposition", TracingConstants.LF_DISPOSITION_OTHER);
			q.setDate("founddate", found.getFoundDate());
			if(hasLocation){
				q.setParameter("foundstation", found.getLocation().getStation_ID());
				q.setParameter("foundstation2", found.getLocation().getStation_ID());
			}
			q.addScalar("id", Hibernate.LONG);
			List<Long> results = (List<Long>)q.list();
			sess.close();
			return results;

		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(sess != null && sess.isOpen()){
				sess.close();
			}
		}
		return null;
	}
	
	public static List<Long> getPotentialLostId(LFFound found, LFServiceBean bean, boolean isPrimary){
		Subcompany subcomp=SubCompanyDAO.loadSubcompany(found.getCompanyId());
		if(TracingConstants.LF_AB_COMPANY_ID.equalsIgnoreCase(subcomp.getCompany().getCompanyCode_ID())){
			return getAvisPotentialLostId(found);
		} else if (TracingConstants.LF_LF_COMPANY_ID.equalsIgnoreCase(subcomp.getCompany().getCompanyCode_ID())){
			return getLFCPotentialLostId(found, isPrimary);
		} else {
			return getLFCPotentialLostId(found, true);//for demo
		}
	}
	
	private static List<Long> getAvisPotentialFoundId(LFLost lost){
		String sql = "select f.id from LFFound f " +
		" left outer join lfitem i on f.id = i.found_id " +
		" where f.status_ID = :status" +
		" and i.disposition_status_ID = :disposition" +
		" and f.foundDate > :founddate";

		boolean hasReservation = false;
		if(lost != null && lost.getLossInfo() != null 
				&& lost.getLossInfo().getDestination() != null 
				&& lost.getLossInfo().getOrigin() != null){
			sql += " and (f.station_ID = :pickup or f.station_ID = :dropoff)";
			hasReservation = true;
		}

		Session sess = null;
		try{
			sess = HibernateWrapper.getSession().openSession();
			SQLQuery q = sess.createSQLQuery(sql);
			q.setParameter("status", TracingConstants.LF_STATUS_OPEN);
			q.setParameter("disposition", TracingConstants.LF_DISPOSITION_OTHER);
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -1 * PropertyBMO.getValueAsInt(PropertyBMO.LF_AUTO_SALVAGE_DAYS));
			if(hasReservation && lost.getLossInfo().getLossdate() != null && lost.getLossInfo().getLossdate().after(cal.getTime())){
				q.setDate("founddate", lost.getLossInfo().getLossdate());
			} else {
				q.setDate("founddate", cal.getTime()); //DATE
			}
			if(hasReservation){
				q.setParameter("pickup", lost.getLossInfo().getOrigin().getStation_ID());
				q.setParameter("dropoff", lost.getLossInfo().getDestination().getStation_ID());
			}
			q.addScalar("id", Hibernate.LONG);
			List<Long> results = (List<Long>)q.list();
			sess.close();
			return results;

		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(sess != null && sess.isOpen()){
				sess.close();
			}
		}
		return null;
	}
	
	private static List<Long> getLFCPotentialFoundId(LFLost lost, boolean isPrimary){
		String sql = "select f.id from LFFound f " +
		" left outer join lfitem i on f.id = i.found_id " +
		" where f.status_ID = :status" +
		" and i.disposition_status_ID = :disposition" +
		" and f.receivedDate > :founddate";

		boolean hasReservation = false;
		//loupas - removing limiting potentail list by reservation location
		//update leaving this in for now....until we change our mind again
		if(lost != null && lost.getSegments() != null 
				&& lost.getSegments().size() > 0){
			String stations = lost.getSegmentSQL();
			if(isPrimary){
				sql += " and (f.station_ID in " + stations + ")";
			} else {
				sql += " and (f.station_ID not in " + stations + ")";
			}
		}

		Session sess = null;
		try{			
			sess = HibernateWrapper.getSession().openSession();
			SQLQuery q = sess.createSQLQuery(sql);
			q.setParameter("status", TracingConstants.LF_STATUS_OPEN);
			q.setParameter("disposition", TracingConstants.LF_DISPOSITION_OTHER);
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -1 * PropertyBMO.getValueAsInt(PropertyBMO.LF_AUTO_SALVAGE_DAYS));
			if(hasReservation && lost.getLossInfo().getLossdate() != null && lost.getLossInfo().getLossdate().after(cal.getTime())){
				q.setDate("founddate", lost.getLossInfo().getLossdate());
			} else {
				q.setDate("founddate", cal.getTime()); //DATE
			}
			q.addScalar("id", Hibernate.LONG);
			List<Long> results = (List<Long>)q.list();
			sess.close();
			return results;

		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(sess != null && sess.isOpen()){
				sess.close();
			}
		}
		return null;
	}
	
	public static List<Long> getPotentialFoundId(LFLost lost, boolean isPrimary){
		Subcompany subcomp=SubCompanyDAO.loadSubcompany(lost.getCompanyId());
		if(TracingConstants.LF_AB_COMPANY_ID.equalsIgnoreCase(subcomp.getCompany().getCompanyCode_ID())){
			return getAvisPotentialFoundId(lost);
		} else if (TracingConstants.LF_LF_COMPANY_ID.equalsIgnoreCase(subcomp.getCompany().getCompanyCode_ID())){
			return getLFCPotentialFoundId(lost, isPrimary);
		} else {
			return getLFCPotentialFoundId(lost, true);//for demo
		}
	}
	
	private static List<LFPhone> getPhones(LFLost lost){
		ArrayList<LFPhone> ret = new ArrayList<LFPhone>();
		if(lost != null){
			ret.addAll(getPhones(lost.getClient()));
			ret.addAll(getPhones(lost.getItem()));
		}
		return ret;
	}
	
	private static List<LFPhone> getPhones(LFFound found){
		ArrayList<LFPhone> ret = new ArrayList<LFPhone>();
		if(found != null){
			ret.addAll(getPhones(found.getClient()));
			ret.addAll(getPhones(found.getItem()));
		}
		return ret;
	}
	
	private static List<LFPhone> getPhones(LFPerson person){
		ArrayList<LFPhone> ret = new ArrayList<LFPhone>();
		if(person != null && person.getPhones() != null){
			ret.addAll(person.getPhones());
		}
		return ret;
	}
	
	private static List<LFPhone> getPhones(LFItem item){
		ArrayList<LFPhone> ret = new ArrayList<LFPhone>();
		if(item != null && item.getPhone() != null){
			ret.add(item.getPhone());
		}
		return ret;
	}
	
	/**
	 * 
	 * returns true if both non-empty strings are different
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static boolean isDifferentIgnoreEmpty(String a, String b){
		if(a != null && a.trim().length() > 0 && b != null && b.trim().length() > 0
				&& !a.equalsIgnoreCase(b)){
			return true;
		}
		return false;
	}
	
	public static boolean isSameStateCountry(LFAddress a, LFAddress b){
		if(a == null || b == null){
			return true;//we are missing address information, default true
		} else {
			if(isDifferentIgnoreEmpty(a.getCountry(), b.getCountry())){
				return false;//countries are different
			} else {
				if(a.getCountry() != null && a.getCountry().equalsIgnoreCase("US")){
					return !isDifferentIgnoreEmpty(a.getDecryptedState(), b.getDecryptedState());
				} else {
					return true;//country is not US, we are not comparing province at this time, returning true
				}
			}
		}
	}
	
	public static double processMatch(LFMatchHistory match, boolean isPrimary){
		Subcompany subcomp=SubCompanyDAO.loadSubcompany(match.getFound().getCompanyId());
		boolean isLFC = TracingConstants.LF_LF_COMPANY_ID.equalsIgnoreCase(subcomp.getCompany().getCompanyCode_ID());
		
		//process person
		if(match.getLost().getClient() != null && match.getFound().getClient() != null){
			LFPerson lc = match.getLost().getClient();
			LFPerson fc = match.getFound().getClient();
			if(lc.getLastName() != null && lc.getLastName().trim().length() > 0
					&& fc.getLastName() != null && fc.getLastName().trim().length() > 0
					&& lc.getFirstName() != null && lc.getFirstName().trim().length() > 0
					&& fc.getFirstName() != null && fc.getFirstName().trim().length() > 0){
				if(StringCompare.compareStrings(lc.getFirstName()+lc.getLastName(), fc.getFirstName()+fc.getLastName()) > 80.0){
					LFMatchDetail detail = new LFMatchDetail();
					detail.setDescription("Name Match");
					detail.setMatchHistory(match);
					detail.setScore(PropertyBMO.getValueAsInt(PropertyBMO.LF_TRACING_WEIGHT_NAME));
					detail.setDecryptedFoundValue(fc.getFirstName() + " " + fc.getLastName());
					detail.setDecryptedLostValue(lc.getFirstName() + " " + lc.getLastName());
					match.getDetails().add(detail);
				}
			}
			
			for(LFPhone lphone:getPhones(match.getLost())){
				for(LFPhone fphone:getPhones(match.getFound())){
					if(lphone.getDecryptedPhoneNumber() != null && lphone.getDecryptedPhoneNumber().trim().length() > 0
							&& fphone.getDecryptedPhoneNumber() != null && fphone.getDecryptedPhoneNumber().trim().length() > 0){
						if(lphone.getDecryptedPhoneNumber().equalsIgnoreCase(fphone.getDecryptedPhoneNumber())){
							LFMatchDetail detail = new LFMatchDetail();
							detail.setDescription("Phone Number Match");
							detail.setMatchHistory(match);
							detail.setScore(PropertyBMO.getValueAsInt(PropertyBMO.LF_TRACING_WEIGHT_PHONE));
							detail.setDecryptedFoundValue(fphone.getDecryptedPhoneNumber());
							detail.setDecryptedLostValue(lphone.getDecryptedPhoneNumber());
							match.getDetails().add(detail);
						}
					}
				}
			}
			
			//process email
			if(lc.getDecryptedEmail() != null && lc.getDecryptedEmail().trim().length() > 0 && 
					fc.getDecryptedEmail() != null && fc.getDecryptedEmail().trim().length() > 0) {
				if (lc.getDecryptedEmail().equalsIgnoreCase(fc.getDecryptedEmail())) {
					LFMatchDetail detail = new LFMatchDetail();
					detail.setDescription("Email Match");
					detail.setMatchHistory(match);
					detail.setScore(PropertyBMO.getValueAsInt(PropertyBMO.LF_TRACING_WEIGHT_EMAIL));
					detail.setDecryptedFoundValue(fc.getDecryptedEmail());
					detail.setDecryptedLostValue(lc.getDecryptedEmail());
					match.getDetails().add(detail);
				}
			}

			if(lc.getAddress() != null && fc.getAddress() != null){
				if(fc.getAddress().getDecryptedAddress1() != null && fc.getAddress().getDecryptedAddress1().trim().length() > 0
						&& lc.getAddress().getDecryptedAddress1() != null && lc.getAddress().getDecryptedAddress1().trim().length() > 0
						&& lc.getAddress().getDecryptedCity() != null && lc.getAddress().getDecryptedCity().trim().length() > 0
						&& fc.getAddress().getDecryptedCity() != null && fc.getAddress().getDecryptedCity().trim().length() > 0
						&& isSameStateCountry(lc.getAddress(), fc.getAddress())){

					LFAddress la = lc.getAddress();
					LFAddress fa = fc.getAddress();
					
					String laddress = la.getDecryptedAddress1()!=null?la.getDecryptedAddress1():"" 
							+ la.getDecryptedAddress2()!=null?la.getDecryptedAddress2():"" 
							+ la.getDecryptedCity()!=null?la.getDecryptedCity():"" 
							+ la.getDecryptedState()!=null?la.getDecryptedState():""
							+ la.getDecryptedProvince()!=null?la.getDecryptedProvince():""
							+ la.getDecryptedZip()!=null?la.getDecryptedZip():"";
					String faddress = fa.getDecryptedAddress1()!=null?fa.getDecryptedAddress1():"" 
							+ fa.getDecryptedAddress2()!=null?fa.getDecryptedAddress2():"" 
							+ fa.getDecryptedCity()!=null?fa.getDecryptedCity():"" 
							+ fa.getDecryptedState()!=null?fa.getDecryptedState():""
							+ fa.getDecryptedProvince()!=null?fa.getDecryptedProvince():""
							+ fa.getDecryptedZip()!=null?fa.getDecryptedZip():"";
					

					if(StringCompare.compareStrings(laddress, faddress) > 60.0){
						LFMatchDetail detail = new LFMatchDetail();
						detail.setDescription("Address Match");
						detail.setMatchHistory(match);
						detail.setScore(PropertyBMO.getValueAsInt(PropertyBMO.LF_TRACING_WEIGHT_ADDRESS));
						String lostStateProvince = lc.getAddress().getDecryptedState()!=null?lc.getAddress().getDecryptedState():lc.getAddress().getDecryptedProvince()!=null?lc.getAddress().getDecryptedProvince():"";
						String foundStateProvince = fc.getAddress().getDecryptedState()!=null?fc.getAddress().getDecryptedState():fc.getAddress().getDecryptedProvince()!=null?fc.getAddress().getDecryptedProvince():"";
						detail.setDecryptedFoundValue(fc.getAddress().getDecryptedAddress1() + " " + fc.getAddress().getDecryptedCity() + " " + foundStateProvince);
						detail.setDecryptedLostValue(lc.getAddress().getDecryptedAddress1() + " " + lc.getAddress().getDecryptedCity() + " " + lostStateProvince);
						match.getDetails().add(detail);
					}
				} 
			}

		}
		
		//process reservation
		if(isPrimary && match.getLost() != null && match.getLost().getLossInfo() != null){
			LFLossInfo lr = match.getLost().getLossInfo();
			if(lr.getMvaNumber() != null && lr.getMvaNumber().trim().length() > 0
					&& match.getFound() != null && match.getFound().getMvaNumber() != null 
					&& match.getFound().getMvaNumber().trim().length() > 0){
				if(lr.getMvaNumber().trim().equalsIgnoreCase(match.getFound().getMvaNumber().trim())){
					LFMatchDetail detail = new LFMatchDetail();
					detail.setDescription("MVA Number Match");
					detail.setMatchHistory(match);
					detail.setScore(PropertyBMO.getValueAsInt(PropertyBMO.LF_TRACING_WEIGHT_MVA));
					detail.setDecryptedFoundValue(match.getFound().getMvaNumber());
					detail.setDecryptedLostValue(lr.getMvaNumber());
					match.getDetails().add(detail);
				} else { // For MVA number: If A and B both contain an MVA number and they do not match. Do not return a result.
					return 0;
				}
			}

			if(lr.getAgreementNumber() != null && lr.getAgreementNumber().trim().length() > 0
					&& match.getFound() != null && match.getFound().getAgreementNumber() != null 
					&& match.getFound().getAgreementNumber().trim().length() > 0){
				if(lr.getAgreementNumber().trim().equalsIgnoreCase(match.getFound().getAgreementNumber().trim())){
					LFMatchDetail detail = new LFMatchDetail();
					detail.setDescription("Rental Agreement Number Match");
					detail.setMatchHistory(match);
					detail.setScore(PropertyBMO.getValueAsInt(PropertyBMO.LF_TRACING_WEIGHT_AGREEMENT));
					detail.setDecryptedFoundValue(match.getFound().getAgreementNumber());
					detail.setDecryptedLostValue(lr.getAgreementNumber());
					match.getDetails().add(detail);
				} else { // For RA number: If A and B both contain a RA number and they do not match. Do not return a result.
					return 0;
				}
			}

			if(match.getFound() != null && match.getFound().getFlightNumber() != null 
					&& match.getFound().getFlightNumber().trim().length() > 0
					&& match.getLost().getSegments() != null && match.getLost().getSegments().size() > 0) {
				for (LFSegment seg : match.getLost().getSegments()) {
					if(seg.getFlightNumber() != null && seg.getFlightNumber().trim().length() > 0
							&& seg.getFlightNumber().trim().equalsIgnoreCase(match.getFound().getFlightNumber().trim())){
						LFMatchDetail detail = new LFMatchDetail();
						detail.setDescription("Flight Number Match");
						detail.setMatchHistory(match);
						detail.setScore(PropertyBMO.getValueAsInt(PropertyBMO.LF_TRACING_WEIGHT_FLIGHT_NUMBER));
						detail.setDecryptedFoundValue(match.getFound().getFlightNumber());
						detail.setDecryptedLostValue(seg.getFlightNumber());
						match.getDetails().add(detail);
					}
				}
			}
			
		}
		
		//process item
		if(match.getLost().getItems() != null && match.getFound().getItem() != null){
			LFItem fitem = match.getFound().getItem();
			for(LFItem litem:match.getLost().getItems()){
				if(isPrimary && litem.getBrand() != null && litem.getBrand().trim().length() > 0
						&& fitem.getBrand() != null && fitem.getBrand().trim().length() > 0){
					if(StringCompare.compareStrings(litem.getBrand(), fitem.getBrand()) > 80.0){
						LFMatchDetail detail = new LFMatchDetail();
						detail.setDescription("Brand Match");
						detail.setMatchHistory(match);
						detail.setScore(PropertyBMO.getValueAsInt(PropertyBMO.LF_TRACING_BRAND));
						detail.setDecryptedFoundValue(fitem.getBrand());
						detail.setDecryptedLostValue(litem.getBrand());
						match.getDetails().add(detail);
					}
				}
				if(isPrimary && litem.getCategory() > 0 && fitem.getCategory() > 0
					&& litem.getCategory() == fitem.getCategory()){
					if(litem.getSubCategory() > 0 && fitem.getSubCategory() > 0
						&& litem.getSubCategory() == fitem.getSubCategory()){
						LFMatchDetail detail = new LFMatchDetail();
						detail.setDescription("Category Match");
						detail.setMatchHistory(match);
						detail.setScore(PropertyBMO.getValueAsInt(PropertyBMO.LF_TRACING_WEIGHT_SUBCATEGORY));
						LFSubCategory sub = getSubCategory(litem.getSubCategory());
						if(sub != null){
							detail.setScore(sub.getScore());
							detail.setDecryptedFoundValue(sub.getParent().getDescription() + "/" + sub.getDescription());
							detail.setDecryptedLostValue(sub.getParent().getDescription() + "/" + sub.getDescription());
						}
						match.getDetails().add(detail);
					} else {
						LFMatchDetail detail = new LFMatchDetail();
						detail.setDescription("Partial Category Match");
						detail.setMatchHistory(match);
						detail.setScore(PropertyBMO.getValueAsInt(PropertyBMO.LF_TRACING_WEIGHT_CATEGORY));
						LFCategory cat = getCategory(litem.getCategory());
						if(cat != null){
							detail.setScore(cat.getScore());
							detail.setDecryptedFoundValue(cat.getDescription());
							detail.setDecryptedLostValue(cat.getDescription());
						}
						match.getDetails().add(detail);
					}
				}
				if(isPrimary && litem.getColor() != null && litem.getColor().trim().length() > 0
						&& fitem.getColor() != null && fitem.getColor().trim().length() > 0){
					if(litem.getColor().equalsIgnoreCase(fitem.getColor()) &&
							!litem.getColor().equalsIgnoreCase(TracingConstants.LFC_COLOR_DOESNOTAPPLY)){
						LFMatchDetail detail = new LFMatchDetail();
						detail.setDescription("Color Match");
						detail.setMatchHistory(match);
						detail.setScore(PropertyBMO.getValueAsInt(PropertyBMO.LF_TRACING_WEIGHT_COLOR));
						detail.setDecryptedFoundValue(fitem.getColor());
						detail.setDecryptedLostValue(litem.getColor());
						match.getDetails().add(detail);
					}
				}
				if(isPrimary && litem.getCaseColor() != null && litem.getCaseColor().trim().length() > 0
						&& fitem.getCaseColor() != null && fitem.getCaseColor().trim().length() > 0){
					if(litem.getCaseColor().equalsIgnoreCase(fitem.getCaseColor()) &&
							!litem.getCaseColor().equalsIgnoreCase(TracingConstants.LFC_COLOR_DOESNOTAPPLY)){
						LFMatchDetail detail = new LFMatchDetail();
						detail.setDescription("Case Color Match");
						detail.setMatchHistory(match);
						detail.setScore(PropertyBMO.getValueAsInt(PropertyBMO.LF_TRACING_WEIGHT_CASE_COLOR));
						detail.setDecryptedFoundValue(fitem.getCaseColor());
						detail.setDecryptedLostValue(litem.getCaseColor());
						match.getDetails().add(detail);
					}
				}
				if(isPrimary && litem.getDescription() != null && litem.getDescription().trim().length() > 0
						&& fitem.getDescription() != null && fitem.getDescription().trim().length() > 0){
					if(StringCompare.compareStrings(litem.getDescription(), fitem.getDescription()) > 60){
						LFMatchDetail detail = new LFMatchDetail();
						detail.setDescription("Description Match");
						detail.setMatchHistory(match);
						detail.setScore(PropertyBMO.getValueAsInt(PropertyBMO.LF_TRACING_WEIGHT_DESCRIPTION));
						detail.setDecryptedFoundValue(fitem.getDescription());
						detail.setDecryptedLostValue(litem.getDescription());
						match.getDetails().add(detail);
					}
				}
				if(isPrimary && litem.getLongDescription() != null && litem.getLongDescription().trim().length() > 0
						&& fitem.getLongDescription() != null && fitem.getLongDescription().trim().length() > 0){
					if(StringCompare.compareStrings(litem.getLongDescription(), fitem.getLongDescription()) > 60){
						LFMatchDetail detail = new LFMatchDetail();
						detail.setDescription("Long Description Match");
						detail.setMatchHistory(match);
						detail.setScore(PropertyBMO.getValueAsInt(PropertyBMO.LF_TRACING_WEIGHT_LONG_DESCRIPTION));
						detail.setDecryptedFoundValue(fitem.getLongDescription());
						detail.setDecryptedLostValue(litem.getLongDescription());
						match.getDetails().add(detail);
					}
				}
				if(isPrimary && litem.getModel() != null && litem.getModel().trim().length() > 0
						&& fitem.getModel() != null && fitem.getModel().trim().length() > 0){
					if(StringCompare.compareStrings(litem.getModel(), fitem.getModel()) > 80){
						LFMatchDetail detail = new LFMatchDetail();
						detail.setDescription("Model Match");
						detail.setMatchHistory(match);
						detail.setScore(PropertyBMO.getValueAsInt(PropertyBMO.LF_TRACING_WEIGHT_MODEL));
						detail.setDecryptedFoundValue(fitem.getModel());
						detail.setDecryptedLostValue(litem.getModel());
						match.getDetails().add(detail);
					}
				}
				if(litem.getSerialNumber() != null && litem.getSerialNumber().trim().length() > 0
						&& fitem.getSerialNumber() != null && fitem.getSerialNumber().trim().length() > 0){
					if(litem.getSerialNumber().equalsIgnoreCase(fitem.getSerialNumber())){
						LFMatchDetail detail = new LFMatchDetail();
						detail.setDescription("Serial Number Match");
						detail.setMatchHistory(match);
						detail.setScore(PropertyBMO.getValueAsInt(PropertyBMO.LF_TRACING_WEIGHT_SERIAL));
						detail.setDecryptedFoundValue(fitem.getSerialNumber());
						detail.setDecryptedLostValue(litem.getSerialNumber());
						match.getDetails().add(detail);
					}
				}
			}
		}
		return match.getTotalScore();
	}
	
	public static List<LFMatchHistory> traceLost(long id, LFServiceBean bean, boolean log, boolean isPrimary) throws Exception{
		if(PropertyBMO.isTrue(PropertyBMO.LF_TRACING_USE_CACHE)){
			cleanCache();
		}
		Date start = new Date();
		LFLost lost = loadLost(id, bean, true);
		List<Long> foundList = getPotentialFoundId(lost,isPrimary);
//		System.out.println("potential found " + isPrimary + " " + foundList.size());
		ArrayList<LFMatchHistory> matchList = new ArrayList<LFMatchHistory>();
		
		for(Long foundId:foundList){
			LFMatchHistory match = new LFMatchHistory();
			match.setLost(lost);
			match.setFound(loadFound(foundId, bean, false));
			Status status = new Status();
			status.setStatus_ID(TracingConstants.LF_TRACING_OPEN);
			match.setStatus(status);
			match.setDetails(new LinkedHashSet<LFMatchDetail>());
			double score = processMatch(match,isPrimary);
			match.setScore(score);
			if(score > PropertyBMO.getValueAsInt(PropertyBMO.LF_TRACE_CUTOFF)){
				try{
					long matchid = bean.saveOrUpdateTraceResult(match);
					if(matchid > -1){
						matchList.add(match);
					} else {
						throw new Exception("failed to add trace result");
					}
				} catch (org.hibernate.exception.ConstraintViolationException e){
					//already traced this result, ignore
				}
			}
		}
		Date end = new Date();
		if(log){
			if(isPrimary){
				Logger.logLF("" + id, "TRACE LOST", end.getTime()-start.getTime());
			} else {
				Logger.logLF("" + id, "TRACE LOST SEC", end.getTime()-start.getTime());
			}
		}
		return matchList;
	}
	
	public static boolean hasMatch(LFMatchHistory match){
		String sql = "select m.id from lfmatchhistory m where m.found_id = :found and m.lost_id = :lost and m.score = :score";
		Session sess = null;
		try{
			sess = HibernateWrapper.getSession().openSession();
			SQLQuery q = sess.createSQLQuery(sql);
			q.setParameter("found", match.getFound().getId());
			q.setParameter("lost", match.getLost().getId());
			q.setParameter("score", match.getScore());
			q.addScalar("id", Hibernate.LONG);
			List list = q.list();
			if(list.size() > 0){
				return true;
			} else {
				return false;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
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
	
	public static long saveLFMatchHistory(LFMatchHistory match) throws org.hibernate.exception.ConstraintViolationException{
		//loupas - encryption is cpu intensive, first check to see if we need to save then encrypt
		if(match.getId() == 0){//only need this check first time saving
			if(!hasMatch(match)){
				if(match.getDetails() != null){
					for(LFMatchDetail detail:match.getDetails()){
						detail.encrypt();
					}
				}
			} else {
				throw new org.hibernate.exception.ConstraintViolationException("", null, null);
			}
		}
		Session sess = null;
		Transaction t = null;
		long id = -1;
		try{
			sess = HibernateWrapper.getSession().openSession();
			t = sess.beginTransaction();
			sess.saveOrUpdate(match);
			t.commit();
			id = match.getId();
		} catch (org.hibernate.exception.ConstraintViolationException e){
			try {
				t.rollback();
			} catch (Exception ex) {
				// Fails
				ex.printStackTrace();
			}
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				t.rollback();
			} catch (Exception ex) {
				// Fails
				ex.printStackTrace();
			}
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		if(id > 0){
			return id;
		} else {
			return -1;
		}
	}
	
	public static List<LFMatchHistory> traceFound(long id, LFServiceBean bean, boolean log, boolean isPrimary) throws Exception{
		if(PropertyBMO.isTrue(PropertyBMO.LF_TRACING_USE_CACHE)){
			cleanCache();
		}
		Date start = new Date();
		LFFound found = loadFound(id, bean, true);
		List<Long> lostList = getPotentialLostId(found, bean, isPrimary);
		ArrayList<LFMatchHistory> matchList = new ArrayList<LFMatchHistory>();
//		System.out.println("potential lost " + isPrimary + " " + lostList.size());
		
		
		for(Long lostId:lostList){
			LFMatchHistory match = new LFMatchHistory();
			match.setLost(loadLost(lostId,bean, false));
			match.setFound(found);
			Status status = new Status();
			status.setStatus_ID(TracingConstants.LF_TRACING_OPEN);
			match.setStatus(status);
			match.setDetails(new LinkedHashSet<LFMatchDetail>());
			double score = processMatch(match,isPrimary);
			match.setScore(score);
			if(score > PropertyBMO.getValueAsInt(PropertyBMO.LF_TRACE_CUTOFF)){
				try{
					if(bean.saveOrUpdateTraceResult(match) > -1){
						matchList.add(match);
					} else {
						throw new Exception("failed to add trace result");
					}
				} catch (org.hibernate.exception.ConstraintViolationException e){
					//already traced this result, ignore
				}
			}
		}
		Date end = new Date();
		if(log){
			if(isPrimary){
				Logger.logLF("" + id, "TRACE FOUND", end.getTime()-start.getTime());
			} else {
				Logger.logLF("" + id, "TRACE FOUND SEC", end.getTime()-start.getTime());
			}
		}
		return matchList;
	}
	
	private static List<Long> getFoundItemsForTracing(){
		String sql = "select f.id id from lffound f, lfitem i " +	
		" where f.item_id = i.id and i.disposition_status_ID = :disposition " +
		" and f.status_ID = :status " +
		" and f.receivedDate > :salvagedate";

		Session sess = null;
		try{
			sess = HibernateWrapper.getSession().openSession();
			SQLQuery q = sess.createSQLQuery(sql);
			q.setParameter("status", TracingConstants.LF_STATUS_OPEN);
			q.setParameter("disposition", TracingConstants.LF_DISPOSITION_OTHER);
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -1 * PropertyBMO.getValueAsInt(PropertyBMO.LF_AUTO_SALVAGE_DAYS));
			q.setDate("salvagedate", cal.getTime()); //DATE
			q.addScalar("id", Hibernate.LONG);
			
			List<Long> results = q.list();
			sess.close();
			return results;

		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(sess != null && sess.isOpen()){
				sess.close();
			}
		}
		return null;
	}
	
	public static void traceAllFoundItems(boolean useCache){
		Date start = new Date();
		List<Long> foundList = getFoundItemsForTracing();
		for(Long l:foundList){
			try {
				LFFound f = new LFFound();
				f.setId(l);
				TraceHandler.trace(f);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		while(TraceHandler.getQueueSize() > 0){
			try {
				System.out.println("Traceall queue size: " + TraceHandler.getQueueSize());
				Thread.sleep(60000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			Thread.sleep(60000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date end = new Date();
		Logger.logLF(null, "TRACE ALL FOUND: " + foundList.size(), end.getTime() - start.getTime());
	}
	
}
