package aero.nettracer.lf.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;

import javax.ejb.Stateless;

import org.apache.struts.util.LabelValueBean;
import org.hibernate.Query;
import org.hibernate.Session;


import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.lf.LFCategory;
import com.bagnet.nettracer.tracing.db.lf.LFDelivery;
import com.bagnet.nettracer.tracing.db.lf.LFFound;
import com.bagnet.nettracer.tracing.db.lf.LFLost;
import com.bagnet.nettracer.tracing.db.lf.detection.LFMatchHistory;
import com.bagnet.nettracer.tracing.dto.LFSearchDTO;

@Stateless
public class LFServiceBean implements LFServiceRemote, LFServiceHome{
	
	@Override
	public String echo(String s) {
		return "echo: " + s;
	}
	
	@Override
	public LFLost getLostReport(long id) {
		Session sess = HibernateWrapper.getSession().openSession();
		LFLost f = null;
		try{
			f = (LFLost) sess.load(LFLost.class, id);

		}catch (Exception e){
			e.printStackTrace();
		}
		finally{
			sess.close();
		}
		return f;
	}

	@Override
	public LFLost getLostReport(long id, String lastname) {
		LFLost lost = getLostReport(id);
		if(lastname != null && lost != null && lost.getClient() != null 
				&& lost.getClient().getLastName() != null
				&& lost.getClient().getLastName().trim().toUpperCase().equals(lastname.trim().toUpperCase())){
			return lost;
		}
		return null;
	}

	private static String getSearchFoundQuery(LFSearchDTO dto){
		String sql = "from com.bagnet.nettracer.tracing.db.lf.LFFound f where 1=1";
		if(dto.getAgent() != null){
			//TODO
		}
		if(dto.getStation() != null){
			//TODO
		}
		if(dto.getStatus() != null){
			//TODO
		}
		return sql;
	}
	
	private static String getSearchLostQuery(LFSearchDTO dto){
		String sql = "from com.bagnet.nettracer.tracing.db.lf.LFLost l where 1=1";
		
		if(dto.getId() > 0){
			sql += " and l.id = " + dto.getId();
		}
		if(dto.getLastName() != null){
			sql += " and l.client.lastName = \'" + dto.getLastName().toUpperCase() + "\'";
		}
		if(dto.getFirstName() != null){
			sql += " and l.client.firstName = \'" + dto.getFirstName().toUpperCase() + "\'";
		}
		if(dto.getOpenDate() != null){
			//TODO
		}
		if(dto.getCloseDate() != null){
			//TODO
		}
		if(dto.getAgent() != null){
			//TODO
		}
		if(dto.getStation() != null){
			//TODO
		}
		if(dto.getStatus() != null){
			//TODO
		}
		return sql;
	}
	
	@Override
	public int searchLostCount(LFSearchDTO dto) {
		String sql = "select count(l.id) " + getSearchLostQuery(dto);
		Session sess = null;
		try{
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess.createQuery(sql);
			List result = q.list();
			sess.close();
			return ((Long) result.get(0)).intValue();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(sess != null){
				sess.close();
			}
		}
		return 0;
	}

	@Override
	public List<LFLost> searchLost(LFSearchDTO dto, int start, int end) {
		String sql = getSearchLostQuery(dto) + " order by l.createDate asc";
		Session sess = null;
		try{
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess.createQuery(sql);
			
			if (start > -1 && end > start ) {
				q.setFirstResult(start);
				q.setMaxResults(end - start);
			}
			
			List<LFLost> results = q.list();
			sess.close();
			return results;
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(sess != null){
				sess.close();
			}
		}
		return null;
	}

	@Override
	public long saveOrUpdateLostReport(LFLost lostReport) {
		Session sess = null;
		try{
			sess = HibernateWrapper.getSession().openSession();
			sess.saveOrUpdate(lostReport);
			sess.close();
			return lostReport.getId();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(sess != null){
				sess.close();
			}
		}
		return -1;
	}

	@Override
	public LFFound getFoundItem(long id) {
		Session sess = HibernateWrapper.getSession().openSession();
		LFFound f = null;
		try{
			f = (LFFound) sess.load(LFFound.class, id);

		}catch (Exception e){
			e.printStackTrace();
		}
		finally{
			sess.close();
		}
		return f;
	}

	@Override
	public int searchFoundCount(LFSearchDTO dto) {
		String sql = "select count(f.id) " + getSearchFoundQuery(dto);
		Session sess = null;
		try{
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess.createQuery(sql);
			List result = q.list();
			sess.close();
			return ((Long) result.get(0)).intValue();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(sess != null){
				sess.close();
			}
		}
		return 0;
	}

	@Override
	public List<LFFound> searchFound(LFSearchDTO dto, int start, int end) {
		String sql = getSearchFoundQuery(dto) + " order by f.createDate asc";
		Session sess = null;
		try{
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess.createQuery(sql);
			
			if (start > -1 && end > start ) {
				q.setFirstResult(start);
				q.setMaxResults(end - start);
			}
			
			List<LFFound> results = q.list();
			sess.close();
			return results;
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(sess != null){
				sess.close();
			}
		}
		return null;
	}

	@Override
	public long saveOrUpdateFoundItem(LFFound foundItem) {
		Session sess = null;
		try{
			sess = HibernateWrapper.getSession().openSession();
			sess.saveOrUpdate(foundItem);
			sess.close();
			return foundItem.getId();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(sess != null){
				sess.close();
			}
		}
		return -1;
	}

	@Override
	public ArrayList<LabelValueBean> getColors() {
		ArrayList <LabelValueBean> colors = new ArrayList<LabelValueBean>();
		colors.add(new LabelValueBean("White", "WT"));
		colors.add(new LabelValueBean("Black", "BK"));
		colors.add(new LabelValueBean("Grey", "GY"));
		colors.add(new LabelValueBean("Blue", "BU"));
		colors.add(new LabelValueBean("Beige", "BE"));
		colors.add(new LabelValueBean("Red", "RD"));
		colors.add(new LabelValueBean("Yellow", "YW"));
		colors.add(new LabelValueBean("Brown", "BN"));
		colors.add(new LabelValueBean("Green", "GN"));
		colors.add(new LabelValueBean("Purple", "PU"));
		colors.add(new LabelValueBean("Multiple Colors", "MC"));
		return colors;
	}

	@Override
	public List<LFCategory> getCategories() {
		String sql = "from com.bagnet.nettracer.tracing.db.lf.LFCategory";
		Session sess = HibernateWrapper.getSession().openSession();
		Query q = sess.createQuery(sql);
		List<LFCategory> categoryList = null;
		
		try{
			categoryList = (List<LFCategory>)q.list();
		}catch (Exception e){
			e.printStackTrace();
		}
		finally{
			sess.close();
		}
		return categoryList;
	}

	@Override
	public boolean closeLostReport(long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void sendStillSearching(long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getLostCount(Station station) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<LFLost> getLostPaginatedList(Station station) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getFoundCount(Station station) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<LFFound> getFoundPaginatedList(Station station) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LFLost> getLostReportToClose(Station station) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getItemsToSalvageCount(Station station) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<LFFound> getItemsToSalvagePaginatedList(Station station) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTraceResultsCount(Station station) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<LFMatchHistory> getTraceResultsPaginated(Station station) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getDeliveryPendingCount(Station station) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<LFDelivery> getDeliveryPendingPaginatedList(Station station) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getLostReport(Date startdate, Date enddate, Station station,
			int matchType, boolean shipped) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getFoundReport(Date startdate, Date enddate, Station station,
			int matchType, boolean shipped) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LFMatchHistory> getTraceResultsForLost(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LFMatchHistory> getTraceResultsForFound(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void traceFoundItem(long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void traceLostItem(long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean confirmMatch(long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean rejectMatch(long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean undoMatch(long id) {
		// TODO Auto-generated method stub
		return false;
	}




}
