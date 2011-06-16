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
	
	public static final int CUTOFF = 0;
	public static final double SCORE_VANTIVE = 10;
	public static final double SCORE_CATEGORY = 10;
	public static final double SCORE_CATEGORY_PARTICLE = 10;
	private static final double SCORE_COLOR = 5;
	private static final double SCORE_SERIAL_NUMBER = 20;
	private static final double SCORE_PHONE = 10;
	
	public List<LFLost> getPotentialLost(){
		String sql = "from com.bagnet.nettracer.tracing.db.lf.LFLost l " +
				" left outer join l.items i " +
				" where l.status.status_ID = :status" +
				" and i.disposition.status_ID = :disposition";
		Session sess = null;
		try{
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess.createQuery(sql);
			q.setParameter("status", TracingConstants.LF_STATUS_OPEN);
			q.setParameter("disposition", TracingConstants.LF_DISPOSITION_OTHER);
			
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
	
	public List<LFFound> getPotentialFound(){
		String sql = "from com.bagnet.nettracer.tracing.db.lf.LFFound f " +
				" where f.status.status_ID = :status" +
				" and f.item.disposition.status_ID = :disposition";
		Session sess = null;
		try{
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess.createQuery(sql);
			q.setParameter("status", TracingConstants.LF_STATUS_OPEN);
			q.setParameter("disposition", TracingConstants.LF_DISPOSITION_OTHER);
			
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
	
	public double processMatch(LFMatchHistory match){
		
		//process person
		if(match.getLost().getClient() != null && match.getFound().getClient() != null){
			LFPerson lc = match.getLost().getClient();
			LFPerson fc = match.getFound().getClient();
			
			if(lc.getLastName() != null && lc.getLastName().trim().length() > 0
					&& fc.getLastName() != null && fc.getLastName().trim().length() > 0
					&& lc.getFirstName() != null && lc.getFirstName().trim().length() > 0
					&& fc.getFirstName() != null && fc.getFirstName().trim().length() > 0){
				//TODO
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
				//TODO
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
					//TODO
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
					//TODO
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
			}
		}
		return match.getTotalScore();
	}
	
	public List<LFMatchHistory> traceLost(long id) throws Exception{
		LFServiceBean bean = new LFServiceBean();
		List<LFFound> foundList = getPotentialFound();
		LFLost lost = bean.getLostReport(id);
		ArrayList<LFMatchHistory> matchList = new ArrayList<LFMatchHistory>();
		
		for(LFFound found:foundList){
			LFMatchHistory match = new LFMatchHistory();
			match.setLost(lost);
			match.setFound(found);
			Status status = new Status();
			status.setStatus_ID(TracingConstants.LF_TRACING_NEW);
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
	
	public List<LFMatchHistory> traceFound(long id) throws Exception{
		LFServiceBean bean = new LFServiceBean();
		List<LFLost> lostList = getPotentialLost();
		LFFound found = bean.getFoundItem(id);
		ArrayList<LFMatchHistory> matchList = new ArrayList<LFMatchHistory>();
		
		for(LFLost lost:lostList){
			LFMatchHistory match = new LFMatchHistory();
			match.setLost(lost);
			match.setFound(found);
			Status status = new Status();
			status.setStatus_ID(TracingConstants.LF_TRACING_NEW);
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
