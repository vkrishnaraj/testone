package aero.nettracer.lf.services;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.lf.LFFound;
import com.bagnet.nettracer.tracing.db.lf.LFItem;
import com.bagnet.nettracer.tracing.db.lf.LFLost;
import com.bagnet.nettracer.tracing.db.lf.LFPerson;
import com.bagnet.nettracer.tracing.db.lf.LFPhone;
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
	private static final double SCORE_CAT_SUBCAT = 10;
	private static final double SCORE_CAT = 10;
	
	public static List<LFLost> getPotentialLost(LFFound found){
		String sql = "from com.bagnet.nettracer.tracing.db.lf.LFLost l " +
				" left outer join l.items i " +
				" where l.status.status_ID = :status" +
				" and i.disposition.status_ID = :disposition";
		boolean hasLocation = false;
		if(found != null && found.getLocation() != null){
				sql += " and (l.reservation.pickupstation.station_ID = :foundstation " +
				" or l.reservation.dropoff.station_ID = :foundstation)" ;
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
			List<LFLost> results = q.list();
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
					sql += " and (f.location = :pickup or f.location = :dropoff)";
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
				if(lc.getLastName().equalsIgnoreCase(fc.getLastName()) && lc.getFirstName().equalsIgnoreCase(fc.getFirstName())){
					LFMatchDetail detail = new LFMatchDetail();
					detail.setDescription("Name Number Match");
					detail.setMatchHistory(match);
					detail.setScore(SCORE_NAME);
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
					if(lc.getAddress().getAddress1().equalsIgnoreCase(fc.getAddress().getAddress1())
							&& lc.getAddress().getCity().equalsIgnoreCase(fc.getAddress().getCity())){
						if(lc.getAddress().getState() != null && lc.getAddress().getState().trim().length() > 0
								&& fc.getAddress().getState() != null && fc.getAddress().getState().trim().length() > 0){
							if(lc.getAddress().getState().equalsIgnoreCase(fc.getAddress().getState())){
								LFMatchDetail detail = new LFMatchDetail();
								detail.setDescription("Address Match");
								detail.setMatchHistory(match);
								detail.setScore(SCORE_PHONE);
								match.getDetails().add(detail);
							}
						} else if (lc.getAddress().getProvince() != null && lc.getAddress().getProvince().trim().length() > 0
								&& fc.getAddress().getProvince() != null && fc.getAddress().getProvince().trim().length() > 0){
							if(lc.getAddress().getProvince().equalsIgnoreCase(fc.getAddress().getProvince())){
								LFMatchDetail detail = new LFMatchDetail();
								detail.setDescription("Address Match");
								detail.setMatchHistory(match);
								detail.setScore(SCORE_PHONE);
								match.getDetails().add(detail);
							}
						} else {
							//TODO no state or province?
						}
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
					if(litem.getBrand().equalsIgnoreCase(fitem.getBrand()));
				}
				if(litem.getCategory() > 0 && fitem.getCategory() > 0){
					if(litem.getSubCategory() > 0 && fitem.getSubCategory() > 0){
						if(litem.getSubCategory() == fitem.getSubCategory()
								&& litem.getCategory() == fitem.getCategory()){
							LFMatchDetail detail = new LFMatchDetail();
							detail.setDescription("Category Match");
							detail.setMatchHistory(match);
							detail.setScore(SCORE_CATEGORY);
							match.getDetails().add(detail);
						}
					} else {
						if(litem.getCategory() == fitem.getCategory()){
							LFMatchDetail detail = new LFMatchDetail();
							detail.setDescription("Particle Category Match");
							detail.setMatchHistory(match);
							detail.setScore(SCORE_CATEGORY_PARTICLE);
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
						match.getDetails().add(detail);
					}
				}
				if(litem.getDescription() != null && litem.getDescription().trim().length() > 0
						&& fitem.getDescription() != null && fitem.getDescription().trim().length() > 0){
					if(litem.getDescription().equalsIgnoreCase(fitem.getDescription())){
						LFMatchDetail detail = new LFMatchDetail();
						detail.setDescription("Description Match");
						detail.setMatchHistory(match);
						detail.setScore(SCORE_DESCRIPTION);
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
						match.getDetails().add(detail);
					}
				}
				if(litem.getCategory() > 0 && fitem.getCategory() > 0){
					if(litem.getSubCategory() > 0 && fitem.getSubCategory() > 0){
						if(litem.getCategory() == fitem.getCategory() &&
								litem.getSubCategory() == fitem.getSubCategory()){
							LFMatchDetail detail = new LFMatchDetail();
							detail.setDescription("Category/Sub Category Number Match");
							detail.setMatchHistory(match);
							detail.setScore(SCORE_CAT_SUBCAT);
							match.getDetails().add(detail);
						}
					} else {
						if(litem.getCategory() == fitem.getCategory()){
							LFMatchDetail detail = new LFMatchDetail();
							detail.setDescription("Category Number Match");
							detail.setMatchHistory(match);
							detail.setScore(SCORE_CAT);
							match.getDetails().add(detail);
						}
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
