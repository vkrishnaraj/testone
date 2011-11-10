package aero.nettracer.lf.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.List;

import org.hibernate.Query;
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
import com.bagnet.nettracer.tracing.db.lf.LFLost;
import com.bagnet.nettracer.tracing.db.lf.LFPerson;
import com.bagnet.nettracer.tracing.db.lf.LFPhone;
import com.bagnet.nettracer.tracing.db.lf.LFReservation;
import com.bagnet.nettracer.tracing.db.lf.LFSubCategory;
import com.bagnet.nettracer.tracing.db.lf.detection.LFMatchDetail;
import com.bagnet.nettracer.tracing.db.lf.detection.LFMatchHistory;

public class LFTracingUtil {
	
	public static final int CUTOFF = 9;
	public static final double SCORE_VANTIVE = 10;
	public static final double SCORE_CATEGORY = 10;
	public static final double SCORE_CATEGORY_PARTICLE = 10;
	private static final double SCORE_COLOR = 5;
	private static final double SCORE_SERIAL_NUMBER = 20;
	private static final double SCORE_PHONE = 10;
	private static final double SCORE_NAME = 10;
	private static final double SCORE_DESCRIPTION = 10;
	private static final double SCORE_ADDRESS = 10;
	private static final double SCORE_BRAND = 10;
	private static final double SCORE_MVA = 15;
	private static final double SCORE_AGREEMENT_NUMBER = 15;
	private static final double SCORE_EMAIL = 10;
	
	
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
	
	public static List<LFLost> getPotentialLost(LFFound found){
		String sql = "select l from com.bagnet.nettracer.tracing.db.lf.LFLost l " +
				" left outer join l.items i " +
				" where l.status.status_ID = :status" +
				" and i.disposition.status_ID = :disposition";
		boolean hasLocation = false;
		if(found != null && found.getLocation() != null){
				sql += " and (l.reservation.pickupLocation.station_ID = :foundstation " +
				" or l.reservation.dropoffLocation.station_ID = :foundstation)" ;
				hasLocation = true;
		}
		Session sess = null;
		try{
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess.createQuery(sql);
			q.setParameter("status", TracingConstants.LF_STATUS_OPEN);
			q.setParameter("disposition", TracingConstants.LF_DISPOSITION_OTHER);
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
	
	public static List<LFFound> getPotentialFound(LFLost lost){
		String sql = "from com.bagnet.nettracer.tracing.db.lf.LFFound f " +
				" where f.status.status_ID = :status" +
				" and f.item.disposition.status_ID = :disposition" +
				" and f.foundDate > :founddate";
		
				boolean hasReservation = false;
				if(lost != null && lost.getReservation() != null 
						&& lost.getReservation().getDropoffLocation() != null 
						&& lost.getReservation().getPickupLocation() != null){
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
			q.setDate("founddate", cal.getTime()); //DATE
			if(hasReservation){
				q.setParameter("pickup", lost.getReservation().getPickupLocation().getStation_ID());
				q.setParameter("dropoff", lost.getReservation().getDropoffLocation().getStation_ID());
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
			
			if(lc.getPhones() != null && fc.getPhones() != null){
				for(LFPhone lphone:lc.getPhones()){
					for(LFPhone fphone:fc.getPhones()){
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

			if(lc.getVantiveNumber() != null && lc.getVantiveNumber().trim().length() > 0
					&& fc.getVantiveNumber() != null && fc.getVantiveNumber().trim().length() > 0){
				if(lc.getVantiveNumber().equalsIgnoreCase(fc.getVantiveNumber())){
					LFMatchDetail detail = new LFMatchDetail();
					detail.setDescription("Vantive Number Match");
					detail.setMatchHistory(match);
					detail.setScore(SCORE_VANTIVE);
					detail.setDecryptedFoundValue(fc.getVantiveNumber());
					detail.setDecryptedLostValue(lc.getVantiveNumber());
					match.getDetails().add(detail);
				}
			}
		}
		
		//process reservation
		if(match.getLost() != null && match.getLost().getReservation() != null){
			LFReservation lr = match.getLost().getReservation();
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
		LFFound found = bean.getFoundItem(id);
		List<LFLost> lostList = getPotentialLost(found);
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
				if(bean.saveOrUpdateTraceResult(match) > -1){
				matchList.add(match);
				} else {
					throw new Exception("failed to add trace result");
				}
			}
		}
		return matchList;
	}
}
