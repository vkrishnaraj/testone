package aero.nettracer.lf.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.bagnet.nettracer.match.StringCompare;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.lf.LFAddress;
import com.bagnet.nettracer.tracing.db.lf.LFCategory;
import com.bagnet.nettracer.tracing.db.lf.LFFound;
import com.bagnet.nettracer.tracing.db.lf.LFItem;
import com.bagnet.nettracer.tracing.db.lf.LFLossInfo;
import com.bagnet.nettracer.tracing.db.lf.LFLost;
import com.bagnet.nettracer.tracing.db.lf.LFPerson;
import com.bagnet.nettracer.tracing.db.lf.LFPhone;
import com.bagnet.nettracer.tracing.db.lf.LFSubCategory;
import com.bagnet.nettracer.tracing.db.lf.detection.LFMatchDetail;
import com.bagnet.nettracer.tracing.db.lf.detection.LFMatchHistory;

public class LFTracingUtil {
	
	public static final int CUTOFF = 9;
	public static final double SCORE_VANTIVE = 10;
	public static final double SCORE_CATEGORY = 10;
	public static final double SCORE_CATEGORY_PARTICLE = 10;
	private static final double SCORE_COLOR = 5;
	private static final double SCORE_MODEL = 5;
	private static final double SCORE_CASE_COLOR = 5;
	private static final double SCORE_SERIAL_NUMBER = 20;
	private static final double SCORE_PHONE = 10;
	private static final double SCORE_NAME = 10;
	private static final double SCORE_DESCRIPTION = 10;
	private static final double SCORE_LONG_DESCRIPTION = 10;
	private static final double SCORE_ADDRESS = 10;
	private static final double SCORE_BRAND = 10;
	private static final double SCORE_MVA = 15;
	private static final double SCORE_AGREEMENT_NUMBER = 15;
	private static final double SCORE_EMAIL = 10;
	
	
	private static HashMap<Long, LFLost> lostMap;
	private static int LOSTMAP_MAX_SIZE = 6000;
	
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
	
	public static List<LFLost> getAvisPotentialLost(LFFound found){
		String sql = "select l from com.bagnet.nettracer.tracing.db.lf.LFLost l " +
		" left outer join l.items i " +
		" where l.status.status_ID = :status" +
		" and i.disposition.status_ID = :disposition" +
		" and (l.lossInfo.lossdate is null or l.lossInfo.lossdate <= :founddate) ";
		boolean hasLocation = false;
		if(found != null && found.getLocation() != null){
			sql += " and (l.lossInfo.origin.station_ID = :foundstation " +
			" or l.lossInfo.destination.station_ID = :foundstation)" ;
			hasLocation = true;
		}
		Session sess = null;
		try{
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess.createQuery(sql);
			q.setParameter("status", TracingConstants.LF_STATUS_OPEN);
			q.setParameter("disposition", TracingConstants.LF_DISPOSITION_OTHER);
			q.setDate("founddate", found.getFoundDate());
			if(hasLocation){
				q.setParameter("foundstation", found.getLocation().getStation_ID());
			}
			List<LFLost> results = (List<LFLost>)q.list();
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

	public static List<Long> getLFCPotentialLostId(LFFound found){
		String sql = "select l.id id from lflost l  " +
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
	
	public static List<LFLost> getLFCPotentialLost(LFFound found){
		return getLFCPotentialLost(found, null);
	}
	
	public static List<LFLost> getLFCPotentialLost(LFFound found, LFServiceBean bean){
		List<Long> list = getLFCPotentialLostId(found);
		if(bean == null){
			bean = new LFServiceBean();
		}
		ArrayList<LFLost> ret = new ArrayList<LFLost>();
		for(Long l:list){
			if(lostMap != null && lostMap.containsKey(l)){
				ret.add(lostMap.get(l));
			} else {
				LFLost toAdd = bean.getLostReport(l);
				if(lostMap != null && lostMap.size() < LOSTMAP_MAX_SIZE){
					lostMap.put(l,toAdd);
				}
				ret.add(toAdd);
			}
		}
		return ret;
	}
	
	public static List<LFLost> getPotentialLost(LFFound found){
		return getPotentialLost(found, null);
	}
	
	public static List<LFLost> getPotentialLost(LFFound found, LFServiceBean bean){
		if(TracingConstants.LF_AB_COMPANY_ID.equalsIgnoreCase(TracingConstants.LF_SUBCOMPANIES.get(found.getCompanyId()))){
			return getAvisPotentialLost(found);
		} else if (TracingConstants.LF_LF_COMPANY_ID.equalsIgnoreCase(TracingConstants.LF_SUBCOMPANIES.get(found.getCompanyId()))){
			return getLFCPotentialLost(found, bean);
		} else {
			return null;
		}
	}
	
	private static List<LFFound> getAvisPotentialFound(LFLost lost){
		String sql = "from com.bagnet.nettracer.tracing.db.lf.LFFound f " +
		" where f.status.status_ID = :status" +
		" and f.item.disposition.status_ID = :disposition" +
		" and f.foundDate > :founddate";

		boolean hasReservation = false;
		if(lost != null && lost.getLossInfo() != null 
				&& lost.getLossInfo().getDestination() != null 
				&& lost.getLossInfo().getOrigin() != null){
			sql += " and (f.location.station_ID = :pickup or f.location.station_ID = :dropoff)";
			hasReservation = true;
		}

		Session sess = null;
		try{
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess.createQuery(sql);
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
			List<LFFound> results = q.list();
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
	
	private static List<LFFound> getLFCPotentialFound(LFLost lost){
		String sql = "from com.bagnet.nettracer.tracing.db.lf.LFFound f " +
		" where f.status.status_ID = :status" +
		" and f.item.disposition.status_ID = :disposition" +
		" and f.foundDate > :founddate";

		boolean hasReservation = false;
		if(lost != null && lost.getLossInfo() != null 
				&& lost.getLossInfo().getDestination() != null 
				&& lost.getLossInfo().getOrigin() != null){
			sql += " and (f.location.station_ID = :dropoff)";
			hasReservation = true;
		}

		Session sess = null;
		try{
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess.createQuery(sql);
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
				q.setParameter("dropoff", lost.getLossInfo().getDestination().getStation_ID());
			}
			List<LFFound> results = q.list();
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
	
	public static List<LFFound> getPotentialFound(LFLost lost){
		if(TracingConstants.LF_AB_COMPANY_ID.equalsIgnoreCase(TracingConstants.LF_SUBCOMPANIES.get(lost.getCompanyId()))){
			return getAvisPotentialFound(lost);
		} else if (TracingConstants.LF_LF_COMPANY_ID.equalsIgnoreCase(TracingConstants.LF_SUBCOMPANIES.get(lost.getCompanyId()))){
			return getLFCPotentialFound(lost);
		} else {
			return null;
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
	
	public static double processMatch(LFMatchHistory match){
		
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
					detail.setScore(SCORE_NAME);
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
							detail.setScore(SCORE_PHONE);
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
					detail.setScore(SCORE_EMAIL);
					detail.setDecryptedFoundValue(fc.getDecryptedEmail());
					detail.setDecryptedLostValue(lc.getDecryptedEmail());
					match.getDetails().add(detail);
				}
			}
			
			if(lc.getAddress() != null && fc.getAddress() != null){
				if(lc.getAddress().getDecryptedAddress1() != null && lc.getAddress().getDecryptedAddress1().trim().length() > 0
						&& fc.getAddress().getDecryptedAddress1() != null && fc.getAddress().getDecryptedAddress1().trim().length() > 0
						&& lc.getAddress().getDecryptedCity() != null && lc.getAddress().getDecryptedCity().trim().length() > 0
						&& fc.getAddress().getDecryptedCity() != null && fc.getAddress().getDecryptedCity().trim().length() > 0){
					
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
						detail.setScore(SCORE_ADDRESS);
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
		if(match.getLost() != null && match.getLost().getLossInfo() != null){
			LFLossInfo lr = match.getLost().getLossInfo();
			if(lr.getMvaNumber() != null && lr.getMvaNumber().trim().length() > 0
					&& match.getFound() != null && match.getFound().getMvaNumber() != null 
					&& match.getFound().getMvaNumber().trim().length() > 0){
				if(lr.getMvaNumber().trim().equalsIgnoreCase(match.getFound().getMvaNumber().trim())){
					LFMatchDetail detail = new LFMatchDetail();
					detail.setDescription("MVA Number Match");
					detail.setMatchHistory(match);
					detail.setScore(SCORE_MVA);
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
					detail.setScore(SCORE_AGREEMENT_NUMBER);
					detail.setDecryptedFoundValue(match.getFound().getAgreementNumber());
					detail.setDecryptedLostValue(lr.getAgreementNumber());
					match.getDetails().add(detail);
				} else { // For RA number: If A and B both contain a RA number and they do not match. Do not return a result.
					return 0;
				}
			}
			
		}
		
		//process item
		if(match.getLost().getItems() != null && match.getFound().getItem() != null){
			LFItem fitem = match.getFound().getItem();
			for(LFItem litem:match.getLost().getItems()){
				if(litem.getBrand() != null && litem.getBrand().trim().length() > 0
						&& fitem.getBrand() != null && fitem.getBrand().trim().length() > 0){
					if(StringCompare.compareStrings(litem.getBrand(), fitem.getBrand()) > 80.0){
						LFMatchDetail detail = new LFMatchDetail();
						detail.setDescription("Brand Match");
						detail.setMatchHistory(match);
						detail.setScore(SCORE_BRAND);
						detail.setDecryptedFoundValue(fitem.getBrand());
						detail.setDecryptedLostValue(litem.getBrand());
						match.getDetails().add(detail);
					}
				}
				if(litem.getCategory() > 0 && fitem.getCategory() > 0
					&& litem.getCategory() == fitem.getCategory()){
					if(litem.getSubCategory() > 0 && fitem.getSubCategory() > 0
						&& litem.getSubCategory() == fitem.getSubCategory()){
						LFMatchDetail detail = new LFMatchDetail();
						detail.setDescription("Category Match");
						detail.setMatchHistory(match);
						detail.setScore(SCORE_CATEGORY_PARTICLE);
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
						detail.setScore(SCORE_CATEGORY);
						LFCategory cat = getCategory(litem.getCategory());
						if(cat != null){
							detail.setScore(cat.getScore());
							detail.setDecryptedFoundValue(cat.getDescription());
							detail.setDecryptedLostValue(cat.getDescription());
						}
						match.getDetails().add(detail);
					}
				}
				if(litem.getColor() != null && litem.getColor().trim().length() > 0
						&& fitem.getColor() != null && fitem.getColor().trim().length() > 0){
					if(litem.getColor().equalsIgnoreCase(fitem.getColor())){
						LFMatchDetail detail = new LFMatchDetail();
						detail.setDescription("Color Match");
						detail.setMatchHistory(match);
						detail.setScore(SCORE_COLOR);
						detail.setDecryptedFoundValue(fitem.getColor());
						detail.setDecryptedLostValue(litem.getColor());
						match.getDetails().add(detail);
					}
				}
				if(litem.getCaseColor() != null && litem.getCaseColor().trim().length() > 0
						&& fitem.getCaseColor() != null && fitem.getCaseColor().trim().length() > 0){
					if(litem.getCaseColor().equalsIgnoreCase(fitem.getCaseColor())){
						LFMatchDetail detail = new LFMatchDetail();
						detail.setDescription("Case Color Match");
						detail.setMatchHistory(match);
						detail.setScore(SCORE_CASE_COLOR);
						detail.setDecryptedFoundValue(fitem.getCaseColor());
						detail.setDecryptedLostValue(litem.getCaseColor());
						match.getDetails().add(detail);
					}
				}
				if(litem.getDescription() != null && litem.getDescription().trim().length() > 0
						&& fitem.getDescription() != null && fitem.getDescription().trim().length() > 0){
					if(StringCompare.compareStrings(litem.getDescription(), fitem.getDescription()) > 60){
						LFMatchDetail detail = new LFMatchDetail();
						detail.setDescription("Description Match");
						detail.setMatchHistory(match);
						detail.setScore(SCORE_DESCRIPTION);
						detail.setDecryptedFoundValue(fitem.getDescription());
						detail.setDecryptedLostValue(litem.getDescription());
						match.getDetails().add(detail);
					}
				}
				if(litem.getLongDescription() != null && litem.getLongDescription().trim().length() > 0
						&& fitem.getLongDescription() != null && fitem.getLongDescription().trim().length() > 0){
					if(StringCompare.compareStrings(litem.getLongDescription(), fitem.getLongDescription()) > 60){
						LFMatchDetail detail = new LFMatchDetail();
						detail.setDescription("Long Description Match");
						detail.setMatchHistory(match);
						detail.setScore(SCORE_LONG_DESCRIPTION);
						detail.setDecryptedFoundValue(fitem.getLongDescription());
						detail.setDecryptedLostValue(litem.getLongDescription());
						match.getDetails().add(detail);
					}
				}
				if(litem.getModel() != null && litem.getModel().trim().length() > 0
						&& fitem.getModel() != null && fitem.getModel().trim().length() > 0){
					if(StringCompare.compareStrings(litem.getModel(), fitem.getModel()) > 80){
						LFMatchDetail detail = new LFMatchDetail();
						detail.setDescription("Model Match");
						detail.setMatchHistory(match);
						detail.setScore(SCORE_MODEL);
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
						detail.setScore(SCORE_SERIAL_NUMBER);
						detail.setDecryptedFoundValue(fitem.getSerialNumber());
						detail.setDecryptedLostValue(litem.getSerialNumber());
						match.getDetails().add(detail);
					}
				}
			}
		}
		return match.getTotalScore();
	}
	
	public static List<LFMatchHistory> traceLost(long id) throws Exception{
		LFServiceBean bean = new LFServiceBean();
		return traceLost(id, bean);
	}
	
	public static List<LFMatchHistory> traceFound(long id) throws Exception{
		LFServiceBean bean = new LFServiceBean();
		return traceFound(id, bean);
	}
	
	public static List<LFMatchHistory> traceLost(long id, LFServiceBean bean) throws Exception{
		LFLost lost = bean.getLostReport(id);
		List<LFFound> foundList = getPotentialFound(lost);
		ArrayList<LFMatchHistory> matchList = new ArrayList<LFMatchHistory>();
		
		for(LFFound found:foundList){
			LFMatchHistory match = new LFMatchHistory();
			match.setLost(lost);
			match.setFound(found);
			Status status = new Status();
			status.setStatus_ID(TracingConstants.LF_TRACING_OPEN);
			match.setStatus(status);
			match.setDetails(new LinkedHashSet<LFMatchDetail>());
			double score = processMatch(match);
			match.setScore(score);
			if(score > CUTOFF){
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
		return matchList;
	}
	
	public static List<LFMatchHistory> traceFound(long id, LFServiceBean bean) throws Exception{
		System.out.println("tracing: " + id);
		LFFound found = bean.getFoundItem(id);
		List<LFLost> lostList = getPotentialLost(found, bean);
		System.out.println("tracing: " + id + " against " + lostList.size());
		ArrayList<LFMatchHistory> matchList = new ArrayList<LFMatchHistory>();
		
		for(LFLost lost:lostList){
			LFMatchHistory match = new LFMatchHistory();
			match.setLost(lost);
			match.setFound(found);
			Status status = new Status();
			status.setStatus_ID(TracingConstants.LF_TRACING_OPEN);
			match.setStatus(status);
			match.setDetails(new LinkedHashSet<LFMatchDetail>());
			double score = processMatch(match);
			match.setScore(score);
			if(score > CUTOFF){
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
		return matchList;
	}
	
	private static List<Long> getFoundItemsForTracing(){
		String sql = "select f.id id from lffound f, lfitem i " +	
		" where f.item_id = i.id and i.disposition_status_ID = :disposition " +
		" and f.status_ID = :status " +
		" and f.foundDate > :salvagedate";

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
		lostMap = null;
		if(useCache){
			lostMap = new HashMap<Long, LFLost>(10000);
		}
		List<Long> foundList = getFoundItemsForTracing();
		for(Long l:foundList){
			try {
				traceFound(l);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		lostMap = null;
	}
	
}
