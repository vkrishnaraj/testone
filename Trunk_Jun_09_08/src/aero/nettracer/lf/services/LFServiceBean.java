package aero.nettracer.lf.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.ejb.Stateless;

import org.apache.struts.util.LabelValueBean;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.lf.LFCategory;
import com.bagnet.nettracer.tracing.db.lf.LFDelivery;
import com.bagnet.nettracer.tracing.db.lf.LFFound;
import com.bagnet.nettracer.tracing.db.lf.LFItem;
import com.bagnet.nettracer.tracing.db.lf.LFLost;
import com.bagnet.nettracer.tracing.db.lf.detection.LFMatchHistory;
import com.bagnet.nettracer.tracing.dto.LFSearchDTO;
import com.bagnet.nettracer.tracing.forms.lf.TraceResultsFilter;
import com.bagnet.nettracer.tracing.forms.lf.TraceResultsForm;
import com.bagnet.nettracer.tracing.utils.DateUtils;

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
	
	@Override
	public LFItem getItem(long id){
		Session sess = HibernateWrapper.getSession().openSession();
		LFItem f = null;
		try{
			f = (LFItem) sess.load(LFItem.class, id);

		}catch (Exception e){
			e.printStackTrace();
		}
		finally{
			sess.close();
		}
		return f;
	}
	
	public List<LFItem> getItemsByLostFoundId(long id, int type) {
		Session sess = HibernateWrapper.getSession().openSession();
		List<LFItem> items = null;
		try{
			String sql;
			if (type == TracingConstants.LF_TYPE_LOST) {
				sql = "from com.bagnet.nettracer.tracing.db.lf.LFItem i where i.lost.id = ";
			} else if (type == TracingConstants.LF_TYPE_FOUND) {
				sql = "from com.bagnet.nettracer.tracing.db.lf.LFItem i where i.found.id = ";
			} else {
				throw new Exception("Invalid type for item");
			}
			
			Query q = sess.createQuery(sql + id);
			items = (List<LFItem>) q.list();

		}catch (Exception e){
			e.printStackTrace();
		}
		finally{
			sess.close();
		}
		return items;
	}

	private static String getSearchLostFoundQuery(LFSearchDTO dto){
		String sql = null;
		if(dto.getType() == TracingConstants.LF_TYPE_LOST){
			sql = "from com.bagnet.nettracer.tracing.db.lf.LFLost o ";
		} else if (dto.getType() == TracingConstants.LF_TYPE_FOUND){
			sql = "from com.bagnet.nettracer.tracing.db.lf.LFFound o ";
		} else {
			return null;
		}
		
		if(dto.getPhoneNumber() != null && dto.getPhoneNumber().trim().length() > 0){
			sql += " left outer join o.client.phones p where p.phoneNumber = \'" + dto.getPhoneNumber() + "\'";
		} else {
			sql += " where 1=1";
		}
		
		if(dto.getId() > 0){
			sql += " and o.id = " + dto.getId();
		}
		if(dto.getLastName() != null && dto.getLastName().trim().length() > 0){
			sql += " and o.client.lastName = \'" + dto.getLastName().toUpperCase() + "\'";
		}
		if(dto.getFirstName() != null && dto.getFirstName().trim().length() > 0){
			sql += " and o.client.firstName = \'" + dto.getFirstName().toUpperCase() + "\'";
		}
		if(dto.getStation() != null && dto.getStation().getStation_ID() != -1){
			sql += " and o.location.station_ID = " + dto.getStation().getStation_ID();
		}
		if(dto.getStatus() != null && dto.getStatus().getStatus_ID() != -1){
			sql += " and o.status.status_ID = " + dto.getStatus().getStatus_ID();
		}
		if(dto.getDisposition() != null && dto.getDisposition().getStatus_ID() != -1){
			if(dto.getType() == TracingConstants.LF_TYPE_FOUND){
				sql += " and o.item.disposition.status_ID = " + dto.getDisposition().getStatus_ID();
			}
		}
		if(dto.getMvaNumber() != null && dto.getMvaNumber().trim().length() > 0){
			if(dto.getType() == TracingConstants.LF_TYPE_LOST){
				sql += " and o.reservation.mvaNumber = \'" + dto.getMvaNumber() + "\'";
			} else {
				sql += " and o.mvaNumber = \'" + dto.getMvaNumber() + "\'";
			}
		}
		if(dto.getStartDate() != null && dto.getStartDate().trim().length() > 0){
			String date = DateUtils.formatDate(dto.getStartDateAsDate(), TracingConstants.getDBDateFormat(HibernateWrapper.getConfig().getProperties()), null, null);
			if(dto.getType() == TracingConstants.LF_TYPE_LOST){
				sql += " and o.openDate >= \'" + date + "\'";
			} else {
				sql += " and o.foundDate >= \'" + date + "\'";
			}
		}
		if(dto.getEndDate() != null && dto.getEndDate().trim().length() > 0){
			String date = DateUtils.formatDate(dto.getEndDateAsDate(), TracingConstants.getDBDateFormat(HibernateWrapper.getConfig().getProperties()), null, null);
			if(dto.getType() == TracingConstants.LF_TYPE_LOST){
				sql += " and o.openDate <= \'" + date + "\'";
			} else {
				sql += " and o.foundDate <= \'" + date + "\'";
			}
		}
		if(dto.getAgreementNumber() != null && dto.getAgreementNumber().trim().length() > 0){
			if(dto.getType() == TracingConstants.LF_TYPE_LOST){
				sql += " and o.reservation.agreementNumber = \'" + dto.getAgreementNumber() + "\'";
			}
		}
		if(dto.getEmail() != null && dto.getEmail().trim().length() > 0){
			sql += " and o.client.email = \'" + dto.getEmail() + "\'";
		}
		return sql;
	}
	
	@Override
	public int searchLostCount(LFSearchDTO dto) {
		String sql = "select count(o.id) " + getSearchLostFoundQuery(dto);
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
			if(sess != null && sess.isOpen()){
				sess.close();
			}
		}
		return 0;
	}

	@Override
	public List<LFLost> searchLost(LFSearchDTO dto, int start, int offset) {
		String sql = getSearchLostFoundQuery(dto) + " order by o.openDate asc";
		Session sess = null;
		try{
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess.createQuery(sql);
			
			if (start > -1 && offset > -1 ) {
				q.setFirstResult(start);
				q.setMaxResults(offset);
			} else {
				throw new Exception("Invalided pagination bounds");
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

	@Override
	public long saveOrUpdateLostReport(LFLost lostReport) {
		Session sess = null;
		Transaction t = null;
		long reportId = -1;
		try{
			sess = HibernateWrapper.getSession().openSession();
			t = sess.beginTransaction();
			sess.saveOrUpdate(lostReport);
			t.commit();
			reportId = lostReport.getId();
		}catch (Exception e) {
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
		if(reportId > 0){
			return reportId;
		} else {
			return -1;
		}
	}
	
	@Override
	public long saveOrUpdateDelivery(LFDelivery delivery){
		Session sess = null;
		Transaction t = null;
		long reportId = -1;
		try{
			sess = HibernateWrapper.getSession().openSession();
			t = sess.beginTransaction();
			sess.saveOrUpdate(delivery);
			t.commit();
			reportId = delivery.getId();
		}catch (Exception e) {
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
		if(reportId > 0){
			return reportId;
		} else {
			return -1;
		}
	}
	
	@Override
	public LFDelivery getDelivery(long id) {
		Session sess = HibernateWrapper.getSession().openSession();
		LFDelivery d = null;
		try{
			d = (LFDelivery) sess.load(LFDelivery.class, id);

		}catch (Exception e){
			e.printStackTrace();
		}
		finally{
			sess.close();
		}
		return d;
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
		String sql = "select count(o.id) " + getSearchLostFoundQuery(dto);
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
			if(sess != null && sess.isOpen()){
				sess.close();
			}
		}
		return 0;
	}

	@Override
	public List<LFFound> searchFound(LFSearchDTO dto, int start, int offset) {
		String sql = getSearchLostFoundQuery(dto) + " order by o.foundDate asc";
		Session sess = null;
		try{
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess.createQuery(sql);
			
			if (start > -1 && offset > -1 ) {
				q.setFirstResult(start);
				q.setMaxResults(offset);
			} else {
				throw new Exception("Invalided pagination bounds");
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

	@Override
	public long saveOrUpdateFoundItem(LFFound foundItem) {
		Session sess = null;
		Transaction t = null;
		long reportId = -1;
		try{
			sess = HibernateWrapper.getSession().openSession();
			t = sess.beginTransaction();
			sess.saveOrUpdate(foundItem);
			t.commit();
			reportId = foundItem.getId();
		}catch (Exception e) {
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
		if(reportId > 0){
			return reportId;
		} else {
			return -1;
		}
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
		LFLost lost = getLostReport(id);
		if(lost != null){
			Status status = new Status();
			status.setStatus_ID(TracingConstants.LF_STATUS_CLOSED);
			lost.setStatus(status);
			if(saveOrUpdateLostReport(lost) > -1){
				return true;
			}
		}
		return false;
	}

	@Override
	public void sendStillSearching(long id) {
		// TODO Auto-generated method stub
		
	}

	private String getLostQuery(Station station){
		String sql = "from com.bagnet.nettracer.tracing.db.lf.LFLost l " +
				"where l.location.station_ID = " + station.getStation_ID()
				+ " and l.status.status_ID != " + TracingConstants.LF_STATUS_CLOSED;
		return sql;
	}
	
	private String getFoundQuery(Station station){
		String sql = "from com.bagnet.nettracer.tracing.db.lf.LFFound f " +
				"where f.location.station_ID = " + station.getStation_ID()
				+ " and f.status.status_ID != " + TracingConstants.LF_STATUS_CLOSED;
		return sql;
	}
	
	String getLostReportToCloseQuery(Station station){
		//TODO what status - universal maybe able to remove station
		int daysTillClose = PropertyBMO.getValueAsInt(PropertyBMO.LF_AUTO_CLOSE_DAYS);
		GregorianCalendar today = new GregorianCalendar();
		today.add(Calendar.DATE, -daysTillClose);
		String cutoff = DateUtils.formatDate(today.getTime(), TracingConstants.getDBDateTimeFormat(HibernateWrapper.getConfig().getProperties()), null, null);
		
		String sql = "from com.bagnet.nettracer.tracing.db.lf.LFLost l " +
		"where l.location.station_ID = " + station.getStation_ID() +
		" and l.openDate < \'" + cutoff + "\'"
		+ " and l.status.status_ID != " + TracingConstants.LF_STATUS_CLOSED;
		return sql;
	}
	
	private String getItemsToSalvageQuery(Station station){
		int daysTillSalvage = PropertyBMO.getValueAsInt(PropertyBMO.LF_AUTO_SALVAGE_DAYS);
		GregorianCalendar today = new GregorianCalendar();
		today.add(Calendar.DATE, -daysTillSalvage);
		String cutoff = DateUtils.formatDate(today.getTime(), TracingConstants.getDBDateTimeFormat(HibernateWrapper.getConfig().getProperties()), null, null);
		
		String sql = "from com.bagnet.nettracer.tracing.db.lf.LFItem i " +
		"where i.found.location.station_ID = " + station.getStation_ID() +
		" and i.found.foundDate < \'" + cutoff + "\'"
		+ " and i.found.status.status_ID != " + TracingConstants.LF_STATUS_CLOSED
		+ " and i.disposition.status_ID = " + TracingConstants.LF_DISPOSITION_OTHER;
		return sql;
	}
	
	private int getLostCount(String sql){
		if(sql == null){
			return 0;
		}
		String query = "select count(l.id) " + sql;
		Session sess = null;
		try{
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess.createQuery(query);
			List result = q.list();
			sess.close();
			return ((Long) result.get(0)).intValue();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(sess != null && sess.isOpen()){
				sess.close();
			}
		}
		return 0;
	}
	
	private List<LFLost> getLostPaginatedList(String sql, int start, int offset){
		if(sql == null){
			return null;
		}
		String query = sql + " order by l.openDate asc";
		Session sess = null;
		try{
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess.createQuery(query);
			
			if (start > -1 && offset > -1 ) {
				q.setFirstResult(start);
				q.setMaxResults(offset);
			} else {
				throw new Exception("Invalided pagination bounds");
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
	
	private int getFoundCount(String sql){
		if(sql == null){
			return 0;
		}
		String query = "select count(f.id) " + sql;
		Session sess = null;
		try{
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess.createQuery(query);
			List result = q.list();
			sess.close();
			return ((Long) result.get(0)).intValue();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(sess != null && sess.isOpen()){
				sess.close();
			}
		}
		return 0;
	}

	private int getItemCount(String sql){
		if(sql == null){
			return 0;
		}
		String query = "select count(i.id) " + sql;
		Session sess = null;
		try{
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess.createQuery(query);
			List result = q.list();
			sess.close();
			return ((Long) result.get(0)).intValue();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(sess != null && sess.isOpen()){
				sess.close();
			}
		}
		return 0;
	}
	
	private List<LFFound> getFoundPaginatedList(String sql, int start, int offset){
		if(sql == null){
			return null;
		}
		String query = sql + " order by f.foundDate asc";
		Session sess = null;
		try{
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess.createQuery(query);
			
			if (start > -1 && offset > -1 ) {
				q.setFirstResult(start);
				q.setMaxResults(offset);
			} else {
				throw new Exception("Invalided pagination bounds");
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
	
	private List<LFItem> getItemPaginatedList(String sql, int start, int offset){
		if(sql == null){
			return null;
		}
		
//		String query = sql + " order by i.foundDate asc";
		Session sess = null;
		try{
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess.createQuery(sql);
			
			if (start > -1 && offset > -1 ) {
				q.setFirstResult(start);
				q.setMaxResults(offset);
			} else {
				throw new Exception("Invalided pagination bounds");
			}
			
			List<LFItem> results = q.list();
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
	
	@Override
	public int getLostCount(Station station) {
		if(station == null){
			return 0;
		}
		return getLostCount(getLostQuery(station));
	}

	@Override
	public List<LFLost> getLostPaginatedList(Station station, int start, int offset) {
		if(station == null){
			return null;
		}
		return getLostPaginatedList(getLostQuery(station), start, offset);
	}
	
	@Override
	public int getFoundCount(Station station) {
		if(station == null){
			return 0;
		}
		return getFoundCount(getFoundQuery(station));
	}

	@Override
	public List<LFFound> getFoundPaginatedList(Station station, int start, int offset) {
		if(station == null){
			return null;
		}
		return getFoundPaginatedList(getFoundQuery(station), start, offset);
	}
	
	@Override
	public int getLostReportToCloseCount(Station station){
		if(station == null){
			return 0;
		}
		return getLostCount(getLostReportToCloseQuery(station));
	}
	
	@Override
	public List<LFLost> getLostReportToClosePaginatedList(Station station, int start, int offset) {
		if(station == null){
			return null;
		}
		return getLostPaginatedList(getLostReportToCloseQuery(station), start, offset);
	}

	@Override
	public int getItemsToSalvageCount(Station station) {
		if(station == null){
			return 0;
		}
		return getItemCount(getItemsToSalvageQuery(station));
	}

	@Override
	public List<LFItem> getItemsToSalvagePaginatedList(Station station, int start, int offset) {
		if(station == null){
			return null;
		}
		return getItemPaginatedList(getItemsToSalvageQuery(station), start, offset);
	}

	private String getTraceResultsQuery(Station station){
		//TODO location criteria
		String sql = "from com.bagnet.nettracer.tracing.db.lf.detection.LFMatchHistory mh " +
		"where mh.status.status_ID = " + TracingConstants.LF_TRACING_OPEN
		+ " and (mh.lost.location = " + station.getStation_ID() + " or " +
				"mh.found.location = " + station.getStation_ID() + ")";
		return sql;
	}
	
	@Override
	public int getTraceResultsCount(Station station) {
		if(station == null){
			return 0;
		}
		String query = "select count(mh.id) " + getTraceResultsQuery(station);
		Session sess = null;
		try{
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess.createQuery(query);
			List result = q.list();
			sess.close();
			return ((Long) result.get(0)).intValue();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(sess != null && sess.isOpen()){
				sess.close();
			}
		}
		return 0;
	}

	@Override
	public List<LFMatchHistory> getTraceResultsPaginated(Station station, int start, int offset) {
		if(station == null){
			return null;
		}
		String query = getTraceResultsQuery(station) + " order by mh.lost.openDate asc";
		Session sess = null;
		try{
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess.createQuery(query);
			
			if (start > -1 && offset > -1 ) {
				q.setFirstResult(start);
				q.setMaxResults(offset);
			} else {
				throw new Exception("Invalided pagination bounds");
			}
			
			List<LFMatchHistory> results = q.list();
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

	
	@Override
	public int getDeliveryPendingCount(Station station) {
		if(station == null){
			return 0;
		}
		String query = "select count (i.id) from com.bagnet.nettracer.tracing.db.lf.LFItem i " +
				"where i.found.location.station_ID = " + station.getStation_ID() +
				" and i.disposition.status_ID = " + TracingConstants.LF_DISPOSITION_TO_BE_DELIVERED +
				" and i.type = " + TracingConstants.LF_TYPE_FOUND;
		Session sess = null;
		try{
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess.createQuery(query);
			List result = q.list();
			sess.close();
			return ((Long) result.get(0)).intValue();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(sess != null && sess.isOpen()){
				sess.close();
			}
		}
		return 0;
	}

	@Override
	public List<LFItem> getDeliveryPendingPaginatedList(Station station, int start, int offset) {
		if(station == null){
			return null;
		}
		
		String query = "from com.bagnet.nettracer.tracing.db.lf.LFItem i " +
		"where i.found.location.station_ID = " + station.getStation_ID() +
		" and i.disposition.status_ID = " + TracingConstants.LF_DISPOSITION_TO_BE_DELIVERED 
		+ " order by i.found.foundDate asc";
		Session sess = null;
		try{
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess.createQuery(query);
			
			if (start > -1 && offset > -1 ) {
				q.setFirstResult(start);
				q.setMaxResults(offset);
			} else {
				throw new Exception("Invalided pagination bounds");
			}
			
			List<LFItem> results = q.list();
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
		String sql = "from com.bagnet.nettracer.tracing.db.lf.detection.LFMatchHistory m " +
		"where m.lost.id = " + id;

		Session sess = null;
		try{
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess.createQuery(sql);
			List result = q.list();
			sess.close();
			return  (List<LFMatchHistory>)result;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(sess != null && sess.isOpen()){
				sess.close();
			}
		}
		return null;
	}

	@Override
	public List<LFMatchHistory> getTraceResultsForFound(long id) {
		String sql = "from com.bagnet.nettracer.tracing.db.lf.detection.LFMatchHistory m " +
				"where m.found.id = " + id;
		
		Session sess = null;
		try{
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess.createQuery(sql);
			List result = q.list();
			sess.close();
			return  (List<LFMatchHistory>)result;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(sess != null && sess.isOpen()){
				sess.close();
			}
		}
		return null;
	}
	
	@Override
	public LFMatchHistory getTraceResult(long id){
		Session sess = HibernateWrapper.getSession().openSession();
		LFMatchHistory f = null;
		try{
			f = (LFMatchHistory) sess.load(LFMatchHistory.class, id);

		}catch (Exception e){
			e.printStackTrace();
		}
		finally{
			sess.close();
		}
		return f;
	}
	
	@Override
	public long saveOrUpdateTraceResult(LFMatchHistory match){
		Session sess = null;
		Transaction t = null;
		long id = -1;
		try{
			sess = HibernateWrapper.getSession().openSession();
			t = sess.beginTransaction();
			sess.saveOrUpdate(match);
			t.commit();
			id = match.getId();
		}catch (Exception e) {
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

	@Override
	public List<LFMatchHistory> traceFoundItem(long id) {
		try {
			return LFTracingUtil.traceFound(id, this);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<LFMatchHistory> traceLostItem(long id) {
		try {
			return LFTracingUtil.traceLost(id, this);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean confirmMatch(long id) {
		LFMatchHistory match = getTraceResult(id);
		if(match != null){
			Status status = new Status();
			status.setStatus_ID(TracingConstants.LF_TRACING_CONFIRMED);
			match.setStatus(status);
			
			if(match.getFound() != null && match.getFound().getItem() != null){
				Status deliveryStatus = new Status();
				deliveryStatus.setStatus_ID(TracingConstants.LF_DISPOSITION_TO_BE_DELIVERED);
				match.getFound().getItem().setDisposition(deliveryStatus);
			}
			
			if(saveOrUpdateTraceResult(match) > -1){
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean rejectMatch(long id) {
		LFMatchHistory match = getTraceResult(id);
		if(match != null){
			Status status = new Status();
			status.setStatus_ID(TracingConstants.LF_TRACING_REJECTED);
			match.setStatus(status);
			
			if(saveOrUpdateTraceResult(match) > -1){
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean undoMatch(long id) {
		LFMatchHistory match = getTraceResult(id);
		if(match != null){
			Status status = new Status();
			status.setStatus_ID(TracingConstants.LF_TRACING_OPEN);
			match.setStatus(status);
			
			if(match.getFound() != null && match.getFound().getItem() != null){
				Status deliveryStatus = new Status();
				deliveryStatus.setStatus_ID(TracingConstants.LF_DISPOSITION_OTHER);
				match.getFound().getItem().setDisposition(deliveryStatus);
			}
			
			if(saveOrUpdateTraceResult(match) > -1){
				return true;
			}
		}
		return false;
	}
	
	public long getFilteredTraceResultsCount(TraceResultsFilter filter) {
		String sql = "select count(distinct m) from com.bagnet.nettracer.tracing.db.lf.detection.LFMatchHistory m where 1 = 1";
		sql += getSqlFromTraceResultsForm(filter);
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
			if(sess != null && sess.isOpen()){
				sess.close();
			}
		}
		return 0;
	}
	
	public List<LFMatchHistory> getFilteredTraceResultsPaginatedList(TraceResultsFilter filter, int start, int offset) {
		String sql = "from com.bagnet.nettracer.tracing.db.lf.detection.LFMatchHistory m where 1 = 1";
		sql += getSqlFromTraceResultsForm(filter);
		List<LFMatchHistory> results = null;
		Session sess = null;
		try{
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess.createQuery(sql);
			
			if (start > -1 && offset > -1 ) {
				q.setFirstResult(start);
				q.setMaxResults(offset);
			} else {
				throw new Exception("Invalided pagination bounds");
			}
			
			results = q.list();
			sess.close();
			return results;
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(sess != null && sess.isOpen()){
				sess.close();
			}
		}
		return results;
	}
	
	private String getSqlFromTraceResultsForm(TraceResultsFilter filter) {
		String sql = "";
		if (filter.getLostId() > 0) {
			sql += " and m.lost.id = " + filter.getLostId();
		}

		if (filter.getFoundId() > 0) {
			sql += " and m.found.id = " + filter.getFoundId();
		}
		
		boolean filterByStatus = filter.getOpen() || filter.getClosed() || filter.getConfirmed() || filter.getRejected(); 
		if (filterByStatus) {
			sql += " and m.status.status_ID in (";
		
			if (filter.getOpen()) {
				sql += TracingConstants.LF_TRACING_OPEN + ",";
			}
			
			if (filter.getClosed()) {
				sql += TracingConstants.LF_TRACING_CLOSED + ",";
			}
	
			if (filter.getConfirmed()) {
				sql += TracingConstants.LF_TRACING_CONFIRMED + ",";
			}
			
			if (filter.getRejected()) {
				sql += TracingConstants.LF_TRACING_REJECTED + ",";
			}
			sql = sql.substring(0, sql.lastIndexOf(',')) + ")";
		}
		
		return sql;
	}
	
}
