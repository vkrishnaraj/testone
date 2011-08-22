package aero.nettracer.lf.services;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.bagnet.nettracer.match.StringCompare;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
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
	
	public static final int CUTOFF = 5;
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
				" and f.item.disposition.status_ID = :disposition";
		
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
					detail.setFoundValue(fc.getFirstName() + " " + fc.getLastName());
					detail.setLostValue(lc.getFirstName() + " " + lc.getLastName());
					match.getDetails().add(detail);
				}
			}
			
			if(lc.getPhones() != null && fc.getPhones() != null){
				for(LFPhone lphone:lc.getPhones()){
					for(LFPhone fphone:fc.getPhones()){
						if(lphone.getNormalizeNumber() != null && lphone.getNormalizeNumber().trim().length() > 0
								&& fphone.getNormalizeNumber() != null && fphone.getNormalizeNumber().trim().length() > 0){
							if(lphone.getNormalizeNumber().equalsIgnoreCase(fphone.getNormalizeNumber())){
								LFMatchDetail detail = new LFMatchDetail();
								detail.setDescription("Phone Number Match");
								detail.setMatchHistory(match);
								detail.setScore(SCORE_PHONE);
								detail.setFoundValue(fphone.getPhoneNumber());
								detail.setLostValue(lphone.getPhoneNumber());
								match.getDetails().add(detail);
							}
						}
					}
				}
			}
			
			if(lc.getAddress() != null && fc.getAddress() != null){
				if(lc.getAddress().getAddress1() != null && lc.getAddress().getAddress1().trim().length() > 0
						&& fc.getAddress().getAddress1() != null && fc.getAddress().getAddress1().trim().length() > 0
						&& lc.getAddress().getCity() != null && lc.getAddress().getCity().trim().length() > 0
						&& fc.getAddress().getCity() != null && fc.getAddress().getCity().trim().length() > 0){
					
					LFAddress la = lc.getAddress();
					LFAddress fa = fc.getAddress();
					
					String laddress = la.getAddress1()!=null?la.getAddress1():"" 
							+ la.getAddress2()!=null?la.getAddress2():"" 
							+ la.getCity()!=null?la.getCity():"" 
							+ la.getState()!=null?la.getState():""
							+ la.getProvince()!=null?la.getProvince():""
							+ la.getZip()!=null?la.getZip():"";
					String faddress = fa.getAddress1()!=null?fa.getAddress1():"" 
							+ fa.getAddress2()!=null?fa.getAddress2():"" 
							+ fa.getCity()!=null?fa.getCity():"" 
							+ fa.getState()!=null?fa.getState():""
							+ fa.getProvince()!=null?fa.getProvince():""
							+ fa.getZip()!=null?fa.getZip():"";
					

					if(StringCompare.compareStrings(laddress, faddress) > 60.0){
						LFMatchDetail detail = new LFMatchDetail();
						detail.setDescription("Address Match");
						detail.setMatchHistory(match);
						detail.setScore(SCORE_ADDRESS);
						String lostStateProvince = lc.getAddress().getState()!=null?lc.getAddress().getState():lc.getAddress().getProvince()!=null?lc.getAddress().getProvince():"";
						String foundStateProvince = fc.getAddress().getState()!=null?fc.getAddress().getState():fc.getAddress().getProvince()!=null?fc.getAddress().getProvince():"";
						detail.setFoundValue(fc.getAddress().getAddress1() + " " + fc.getAddress().getCity() + " " + foundStateProvince);
						detail.setLostValue(lc.getAddress().getAddress1() + " " + lc.getAddress().getCity() + " " + lostStateProvince);
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
					detail.setFoundValue(fc.getVantiveNumber());
					detail.setLostValue(lc.getVantiveNumber());
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
				if(lr.getMvaNumber().equalsIgnoreCase(match.getFound().getMvaNumber())){
					LFMatchDetail detail = new LFMatchDetail();
					detail.setDescription("MVA Number Match");
					detail.setMatchHistory(match);
					detail.setScore(SCORE_MVA);
					detail.setFoundValue(match.getFound().getMvaNumber());
					detail.setLostValue(lr.getMvaNumber());
					match.getDetails().add(detail);
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
						detail.setFoundValue(fitem.getBrand());
						detail.setLostValue(litem.getBrand());
						match.getDetails().add(detail);
					}
				}
				if(litem.getCategory() > 0 && fitem.getCategory() > 0){
					if(litem.getSubCategory() > 0 && fitem.getSubCategory() > 0){
						if(litem.getSubCategory() == fitem.getSubCategory()
								&& litem.getCategory() == fitem.getCategory()){
							LFMatchDetail detail = new LFMatchDetail();
							detail.setDescription("Category Match");
							detail.setMatchHistory(match);
							detail.setScore(SCORE_CATEGORY);
							LFSubCategory sub = getSubCategory(litem.getSubCategory());
							if(sub != null){
								detail.setFoundValue(sub.getParent().getDescription() + "/" + sub.getDescription());
								detail.setLostValue(sub.getParent().getDescription() + "/" + sub.getDescription());
							}
							match.getDetails().add(detail);
						}
					} else {
						if(litem.getCategory() == fitem.getCategory()){
							LFMatchDetail detail = new LFMatchDetail();
							detail.setDescription("Particle Category Match");
							detail.setMatchHistory(match);
							detail.setScore(SCORE_CATEGORY_PARTICLE);
							LFCategory cat = getCategory(litem.getCategory());
							if(cat != null){
								detail.setFoundValue(cat.getDescription());
								detail.setLostValue(cat.getDescription());
							}
							match.getDetails().add(detail);
						}
					}
				}
				if(litem.getColor() != null && litem.getColor().trim().length() > 0
						&& fitem.getColor() != null && fitem.getColor().trim().length() > 0){
					if(litem.getColor().equalsIgnoreCase(fitem.getColor())){
						LFMatchDetail detail = new LFMatchDetail();
						detail.setDescription("Color Match");
						detail.setMatchHistory(match);
						detail.setScore(SCORE_COLOR);
						detail.setFoundValue(fitem.getColor());
						detail.setLostValue(litem.getColor());
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
						detail.setFoundValue(fitem.getDescription());
						detail.setLostValue(litem.getDescription());
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
						detail.setFoundValue(fitem.getSerialNumber());
						detail.setLostValue(litem.getSerialNumber());
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
