package aero.nettracer.lf.services;

import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.ejb.Stateless;
import javax.mail.internet.InternetAddress;

import org.apache.struts.util.LabelValueBean;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import aero.nettracer.general.services.GeneralServiceBean;
import aero.nettracer.security.AES;

import com.bagnet.nettracer.email.HtmlEmail;
import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.lf.LFCategory;
import com.bagnet.nettracer.tracing.db.lf.LFDelivery;
import com.bagnet.nettracer.tracing.db.lf.LFFound;
import com.bagnet.nettracer.tracing.db.lf.LFItem;
import com.bagnet.nettracer.tracing.db.lf.LFLost;
import com.bagnet.nettracer.tracing.db.lf.LFPhone;
import com.bagnet.nettracer.tracing.db.lf.LFSubCategory;
import com.bagnet.nettracer.tracing.db.lf.detection.LFMatchDetail;
import com.bagnet.nettracer.tracing.db.lf.detection.LFMatchHistory;
import com.bagnet.nettracer.tracing.dto.LFSearchDTO;
import com.bagnet.nettracer.tracing.forms.lf.TraceResultsFilter;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.EmailParser;
import com.bagnet.nettracer.tracing.utils.TracerProperties;

@Stateless
public class LFServiceBean implements LFServiceRemote, LFServiceHome{
	
	private static String autoagent = "autoagent";
	
	public Agent getAutoAgent(){
		GeneralServiceBean bean = new GeneralServiceBean();
		return bean.getAgent(autoagent, TracerProperties.get("wt.company.code"));
	}
	
	
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
	
	@SuppressWarnings("unchecked")
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
			sql = " from com.bagnet.nettracer.tracing.db.lf.LFLost o ";
		} else if (dto.getType() == TracingConstants.LF_TYPE_FOUND){
			sql = " from com.bagnet.nettracer.tracing.db.lf.LFFound o ";
		} else {
			return null;
		}
		
		long category = dto.getCategory();
		long subCategory = dto.getSubCategory();
		boolean haveBrand = dto.getBrand() != null && !dto.getBrand().isEmpty();
		boolean haveDesc = dto.getItemDescription() != null && !dto.getItemDescription().isEmpty();
		
		if (category > 0 || haveBrand || haveDesc) {
			sql += " left outer join o.items i";
		}
		

		String phone = null;
		if(dto.getPhoneNumber() != null && dto.getPhoneNumber().trim().length() > 0){
			try{
				phone = AES.encrypt(LFPhone.normalizePhone(dto.getPhoneNumber()));
				if(phone != null){
					sql += " left outer join o.client.phones p";
				}
			} catch (Exception e){
				e.printStackTrace();
			}
		} 
		
		sql += " where 1=1";

		if (category > 0 || haveBrand || haveDesc) {
			String itemSql = " and i.type = " + dto.getType();
			if (category > 0) {
				itemSql += " and i.category = " + category;
				if (subCategory > 0 ) {
					itemSql += " and i.subCategory = " + subCategory;
				}
			}
			
			if (haveBrand) {
				itemSql += " and i.brand like \'" + dto.getBrand() + "\'";
			}
			
			if (haveDesc) {
				itemSql += " and i.description like \'" + dto.getItemDescription() + "\'";
			}
			
			sql += itemSql;
		}
		
		if (phone != null) {
			sql += " and p.phoneNumber = \'" + phone + "\'";
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
			if(dto.getType() == TracingConstants.LF_TYPE_LOST){
				sql += " and o.reservation.dropoffLocation.station_ID = " + dto.getStation().getStation_ID();
			} else {
				sql += " and o.location.station_ID = " + dto.getStation().getStation_ID();
			}
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
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(dto.getEndDateAsDate());
			cal.add(Calendar.DATE, 1);
			String date = DateUtils.formatDate(cal.getTime(), TracingConstants.getDBDateFormat(HibernateWrapper.getConfig().getProperties()), null, null);
			if(dto.getType() == TracingConstants.LF_TYPE_LOST){
				sql += " and o.openDate < \'" + date + "\'";
			} else {
				sql += " and o.foundDate < \'" + date + "\'";
			}
		}
		if(dto.getAgreementNumber() != null && dto.getAgreementNumber().trim().length() > 0){
			if(dto.getType() == TracingConstants.LF_TYPE_LOST){
				sql += " and o.reservation.agreementNumber = \'" + dto.getAgreementNumber() + "\'";
			}
		}
		if(dto.getEmail() != null && dto.getEmail().trim().length() > 0){
			try {
				String s = AES.encrypt(dto.getEmail().toLowerCase());
				if(s != null){
					sql += " and o.client.email = \'" + s + "\'";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
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
			@SuppressWarnings("unchecked")
			List<Long> result = q.list();
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
		String sql = "select o " + getSearchLostFoundQuery(dto) + " order by o.openDate asc";
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
			
			@SuppressWarnings("unchecked")
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

	private boolean isNewlyClosed(LFLost toSave) {
		if(toSave == null){
			return false;
		}
		if(toSave.getStatus().getStatus_ID() != TracingConstants.LF_STATUS_CLOSED){
			return false;
		}
		if(toSave.getId() == 0){
			return true;//we have a new Lost to save and it is closed
		}
		
		LFLost current = this.getLostReport(toSave.getId());
		if(current == null){
			//assume newly closed
			return true;
		}
		
		if(current.getStatus().getStatus_ID() != TracingConstants.LF_STATUS_CLOSED){
			//this is a newly closed file
			return true;
		} else {
			return false;
		}
	}

	private boolean isNewlyClosed(LFFound toSave) {
		if(toSave == null){
			return false;
		}
		if(toSave.getStatus().getStatus_ID() != TracingConstants.LF_STATUS_CLOSED){
			return false;
		}
		if(toSave.getId() == 0){
			return true;//we have a new Found to save and it is closed
		}
		
		LFFound current = this.getFoundItem(toSave.getId());
		if(current == null){
			//assume newly closed
			return true;
		}
		
		if(current.getStatus().getStatus_ID() != TracingConstants.LF_STATUS_CLOSED){
			//this is a newly closed file
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public long saveOrUpdateLostReport(LFLost lostReport, Agent agent) {
		Session sess = null;
		Transaction t = null;
		long reportId = -1;
		boolean isNew = (lostReport!=null&&lostReport.getId()==0)?true:false;
		
		boolean isNewlyClosed = this.isNewlyClosed(lostReport);
		
		if(isNewlyClosed){
			lostReport.setCloseDate(new Date());
			lostReport.setCloseAgent(agent);
		}
		if(lostReport.getStatus().getStatus_ID() != TracingConstants.LF_STATUS_CLOSED){
			lostReport.setCloseDate(null);
			lostReport.setCloseAgent(null);
		}
		
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
		if(lostReport.getStatus().getStatus_ID() == TracingConstants.LF_STATUS_CLOSED){
			//close any open trace results
			closeOpenTraceResults(lostReport.getId(), TracingConstants.LF_TYPE_LOST);
		}
		if(reportId > 0){
			if(isNew){
				sendLostCreatedEmail(reportId);
				if (agent != null) {
					LFLogUtil.writeLog(agent.getUsername(), agent.getStation().getStationcode(), LFLogUtil.EVENT_CREATE, (int) reportId, 0);
				}
			} else if (isNewlyClosed && agent != null) {
				LFLogUtil.writeLog(agent.getUsername(), agent.getStation().getStationcode(), LFLogUtil.EVENT_CLOSE, (int) reportId, 0);
			} else if (agent != null) {
				LFLogUtil.writeLog(agent.getUsername(), agent.getStation().getStationcode(), LFLogUtil.EVENT_MODIFY, (int) reportId, 0);
			}
			return reportId;
		} else {
			return -1;
		}
	}
	
	public void closeOpenTraceResults(long id, int type){
		String sql = "update lfmatchhistory set status_Status_ID = " + TracingConstants.LF_TRACING_CLOSED + " where " +
				" status_Status_ID = " + TracingConstants.LF_TRACING_OPEN;
		if(type == TracingConstants.LF_TYPE_LOST){
			sql += " and lost_id = " + id; 
		} else {
			sql += " and found_id = " + id;
		}
		Session sess = null;
		Transaction t = null;
		try{
			sess = HibernateWrapper.getSession().openSession();
			SQLQuery q = sess.createSQLQuery(sql);
			t = sess.beginTransaction();
			q.executeUpdate();
			t.commit();
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
			@SuppressWarnings("unchecked")
			List <Long >result = q.list();
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
		String sql = "select o " + getSearchLostFoundQuery(dto) + " order by o.foundDate asc";
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
			
			@SuppressWarnings("unchecked")
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
	public long saveOrUpdateFoundItem(LFFound foundItem, Agent agent) {
		Session sess = null;
		Transaction t = null;
		long reportId = -1;
		boolean isNew = (foundItem!=null&&foundItem.getId()==0)?true:false;
		
		boolean isNewlyClosed = this.isNewlyClosed(foundItem);
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
		if(foundItem.getStatus().getStatus_ID() == TracingConstants.LF_STATUS_CLOSED){
			closeOpenTraceResults(foundItem.getId(), TracingConstants.LF_TYPE_FOUND);
		}
		if(reportId > 0){
			if(isNew && agent != null){
				LFLogUtil.writeLog(agent.getUsername(), agent.getStation().getStationcode(), LFLogUtil.EVENT_CREATE, 0, (int) reportId);
			} else if (isNewlyClosed && agent != null) {
				LFLogUtil.writeLog(agent.getUsername(), agent.getStation().getStationcode(), LFLogUtil.EVENT_CLOSE, 0, (int) reportId);
			} else if (agent != null) {
				LFLogUtil.writeLog(agent.getUsername(), agent.getStation().getStationcode(), LFLogUtil.EVENT_MODIFY, 0, (int) reportId);
			}
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
		//colors.add(new LabelValueBean("Beige", "BE"));
		colors.add(new LabelValueBean("Red", "RD"));
		colors.add(new LabelValueBean("Yellow", "YW"));
		colors.add(new LabelValueBean("Brown", "BN"));
		colors.add(new LabelValueBean("Green", "GN"));
		colors.add(new LabelValueBean("Purple", "PU"));

		colors.add(new LabelValueBean("Pink", "PK"));
		colors.add(new LabelValueBean("Gold", "GD"));
		colors.add(new LabelValueBean("Silver", "SV"));
		colors.add(new LabelValueBean("Plaid", "PL"));
		
		colors.add(new LabelValueBean("Multiple Colors", "MC"));
		return colors;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LFCategory> getCategories() {
		String sql = "from com.bagnet.nettracer.tracing.db.lf.LFCategory cat order by cat.description";
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
	public boolean closeLostReport(long id, Agent agent) {
		LFLost lost = getLostReport(id);
		if(lost != null){
			Status status = new Status();
			status.setStatus_ID(TracingConstants.LF_STATUS_CLOSED);
			lost.setStatus(status);
			if(saveOrUpdateLostReport(lost, agent) > -1){
				return true;
			}
		}
		return false;
	}

	private String getLostQuery(Station station){
		String sql = "from com.bagnet.nettracer.tracing.db.lf.LFLost l " +
				"where l.reservation.dropoffLocation.station_ID = " + station.getStation_ID()
				+ " and l.status.status_ID != " + TracingConstants.LF_STATUS_CLOSED;
		return sql;
	}
	
	private String getFoundQuery(Station station){
		String sql = "from com.bagnet.nettracer.tracing.db.lf.LFFound f " +
				"where f.location.station_ID = " + station.getStation_ID()
				+ " and f.status.status_ID != " + TracingConstants.LF_STATUS_CLOSED;
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
			@SuppressWarnings("unchecked")
			List<Long> result = q.list();
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
			
			@SuppressWarnings("unchecked")
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
		+ " and (mh.lost.reservation.dropoffLocation = " + station.getStation_ID() + " or " +
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
		" and i.disposition.status_ID = " + TracingConstants.LF_DISPOSITION_TO_BE_DELIVERED + 
		" and i.type = " + TracingConstants.LF_TYPE_FOUND + 
		" order by i.found.foundDate asc";
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
	
	public boolean updateTraceResults(List<LFMatchHistory> matchlist){
		Session sess = null;
		Transaction t = null;
		boolean success = true;
		try{
			sess = HibernateWrapper.getSession().openSession();
			t = sess.beginTransaction();
			for(LFMatchHistory match:matchlist){
				sess.saveOrUpdate(match);
			}
			t.commit();
		}catch (Exception e) {
			success = false;
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
		return success;
	}
	
	
	@Override
	public long saveOrUpdateTraceResult(LFMatchHistory match) throws org.hibernate.exception.ConstraintViolationException{
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

	@Override
	public long createManualMatch(LFLost lost, LFFound found){
		LFMatchHistory match = new LFMatchHistory();
		match.setFound(found);
		match.setLost(lost);
		if(found != null && lost != null && !isAlreadyMatched(match)) {
			match.setScore(-1);
			Status status = new Status();
			status.setStatus_ID(TracingConstants.LF_TRACING_OPEN);
			match.setStatus(status);
			LFMatchDetail detail = new LFMatchDetail();
			detail.setDescription("Manually Matched");
			detail.setScore(0.0);
			detail.setMatchHistory(match);
			HashSet<LFMatchDetail> s = new HashSet<LFMatchDetail>();
			s.add(detail);
			match.setDetails(s);
			long matchId = -1;
			try{
				matchId = saveOrUpdateTraceResult(match);
			} catch (org.hibernate.exception.ConstraintViolationException e){
				try{
					return findExistingManualMatch(lost.getId(), found.getId());
				} catch (org.hibernate.NonUniqueResultException ne){
					return -1;
				}
			}
			return matchId;
		}
		return -1;
	}
	
	private long findExistingManualMatch(long lostId, long foundId){
		String sql ="select m.id from com.bagnet.nettracer.tracing.db.lf.detection.LFMatchHistory m " +
		" where m.lost.id = :lostId and m.found.id = :foundId and m.score = :score";
		Session sess = null;
		try{
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess.createQuery(sql);
			q.setParameter("lostId", lostId);
			q.setParameter("foundId", foundId);
			q.setParameter("score", 0.0);
			Long result = (Long) q.uniqueResult();
			sess.close();
			return  result;
		} catch (org.hibernate.NonUniqueResultException e){
			throw e;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(sess != null && sess.isOpen()){
				sess.close();
			}
		}
		return -1;
	}
	
	@Override
	public long findConfirmedMatch(long lostId, long foundId) throws org.hibernate.NonUniqueResultException{
		String sql ="select m.id from com.bagnet.nettracer.tracing.db.lf.detection.LFMatchHistory m " +
				" where m.lost.id = :lostId and m.found.id = :foundId and m.status.status_ID = :status";
		Session sess = null;
		try{
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess.createQuery(sql);
			q.setParameter("lostId", lostId);
			q.setParameter("foundId", foundId);
			q.setParameter("status", TracingConstants.LF_TRACING_CONFIRMED);
			Long result = (Long) q.uniqueResult();
			sess.close();
			return  result;
		} catch (org.hibernate.NonUniqueResultException e){
			throw e;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(sess != null && sess.isOpen()){
				sess.close();
			}
		}
		return -1;
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
		if (id != -1) {
			LFMatchHistory match = getTraceResult(id);
			if(match != null && !isAlreadyMatched(match)){
				Status status = new Status();
				status.setStatus_ID(TracingConstants.LF_TRACING_CONFIRMED);
				match.setStatus(status);
				
				if(match.getFound() != null && match.getFound().getItem() != null){
					Status deliveryStatus = new Status();
					deliveryStatus.setStatus_ID(TracingConstants.LF_DISPOSITION_TO_BE_DELIVERED);
					match.getFound().getItem().setDisposition(deliveryStatus);
					match.getFound().getItem().setLost(match.getLost());
				}
				
				if(match.getLost() != null && match.getLost().getItem() != null){
					Status deliveryStatus = new Status();
					deliveryStatus.setStatus_ID(TracingConstants.LF_DISPOSITION_TO_BE_DELIVERED);
					match.getLost().getItem().setDisposition(deliveryStatus);
					match.getLost().getItem().setFound(match.getFound());
				}
				
				saveOrUpdateTraceResult(match);
				
				List<LFMatchHistory> matchList = getAssociatedTraceResults(match);
				ArrayList<LFMatchHistory> saveList = new ArrayList<LFMatchHistory>();
				saveOrUpdateTraceResult(match);
				if(matchList != null){
					for(LFMatchHistory m:matchList){
						if(m.getId() != match.getId() && m.getStatus().getStatus_ID() == TracingConstants.LF_TRACING_OPEN){
							Status closed = new Status();
							closed.setStatus_ID(TracingConstants.LF_TRACING_CLOSED);
							m.setStatus(closed);
							saveList.add(m);
						}
					}
	//				saveList.add(match);
					return updateTraceResults(saveList);
				}
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
	public boolean unrejectMatch(long id){
		LFMatchHistory match = getTraceResult(id);
		if(match != null){
			Status status = new Status();
			if(isAlreadyMatched(match)){
				status.setStatus_ID(TracingConstants.LF_TRACING_CLOSED);
			} else {
				status.setStatus_ID(TracingConstants.LF_TRACING_OPEN);
			}
			match.setStatus(status);
			
			if(saveOrUpdateTraceResult(match) > -1){
				return true;
			}
		}
		return false;
	}
	
	private boolean isAlreadyMatched(LFMatchHistory match){
		if(match.getLost().getItem() != null && match.getLost().getItem().getFound() != null && match.getLost().getItem().getLost() != null){
			return true;
		}
		if(match.getFound().getItem() != null && match.getFound().getItem().getLost() != null && match.getFound().getItem().getFound() != null){
			return true;
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
				match.getFound().getItem().setLost(null);
			}
			
			if(match.getLost() != null && match.getLost().getItem() != null){
				Status deliveryStatus = new Status();
				deliveryStatus.setStatus_ID(TracingConstants.LF_DISPOSITION_OTHER);
				match.getLost().getItem().setDisposition(deliveryStatus);
				match.getLost().getItem().setFound(null);
			}
			saveOrUpdateTraceResult(match);
			List<LFMatchHistory> matchList = getAssociatedTraceResults(match);
			ArrayList<LFMatchHistory> saveList = new ArrayList<LFMatchHistory>();
			if(matchList != null){
				Status open = new Status();
				open.setStatus_ID(TracingConstants.LF_TRACING_OPEN);
				for(LFMatchHistory m:matchList){
					if(m.getId() != match.getId() && m.getStatus().getStatus_ID() == TracingConstants.LF_TRACING_CLOSED){
						m.setStatus(open);
						saveList.add(m);
					}
				}
//				saveList.add(match);
				return updateTraceResults(saveList);
			}
		}
		return false;
	}
	
	public long getFilteredTraceResultsCount(Station station, TraceResultsFilter filter) {
		String sql = "select count(distinct m) from com.bagnet.nettracer.tracing.db.lf.detection.LFMatchHistory m where 1 = 1";
		sql += getSqlFromTraceResultsForm(filter);
		 sql += " and (m.lost.reservation.dropoffLocation = " + station.getStation_ID() + " or " +
			"m.found.location = " + station.getStation_ID() + ")";
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
	
	public List<LFMatchHistory> getAssociatedTraceResults(LFMatchHistory match){
		if(match == null){
			return null;
		}
		String sql = "from com.bagnet.nettracer.tracing.db.lf.detection.LFMatchHistory m " +
				" where (m.lost.id = :lostid or m.found.id = :foundid)";
		List<LFMatchHistory> results = null;
		Session sess = null;
		try{
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess.createQuery(sql);
			q.setParameter("lostid", match.getLost().getId());
			q.setParameter("foundid", match.getFound().getId());
			results = q.list();
			sess.close();
			return results;
		}catch(Exception e){
			e.printStackTrace();
			results = null;
		}finally{
			if(sess != null && sess.isOpen()){
				sess.close();
			}
		}
		return results;
		
	}
	
	public List<LFMatchHistory> getFilteredTraceResultsPaginatedList(Station station, TraceResultsFilter filter, int start, int offset) {
		String sql = "from com.bagnet.nettracer.tracing.db.lf.detection.LFMatchHistory m where 1 = 1";
		sql += getSqlFromTraceResultsForm(filter);
		sql +=  " and (m.lost.reservation.dropoffLocation = " + station.getStation_ID() + " or " +
			"m.found.location = " + station.getStation_ID() + ")";
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
	
	
	@Override
	public void getStillSearchingList() {
		String sql = "select l.id lostid from lflost l, station s, lfreservation r " +
		" where l.status_ID = " + TracingConstants.LF_STATUS_OPEN +
		" and l.reservation_id = r.id " +
		" and r.dropofflocation_station_ID = s.Station_ID " +
		" and (datediff(curdate(),l.emailSentDate) >= s.priority or l.emailSentDate is null)";

		Session sess = null;
		SQLQuery pq = null;
		ArrayList<Long>lostIds = new ArrayList<Long>();
		try{
			sess = HibernateWrapper.getSession().openSession();
			pq = sess.createSQLQuery(sql.toString());
			pq.addScalar("lostid", Hibernate.LONG);
			List<Long> listMatchingFiles = pq.list();
			for (Long strs : listMatchingFiles) {
				lostIds.add((Long) strs);
			}
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(sess != null){
				sess.close();
			}
		}
		for(Long id:lostIds){
			System.out.println(id);
			sendStillSearching(id);
		}
	}
	
	@Override
	public void sendStillSearching(long id) {
		LFLost lost = getLostReport(id);
		if(lost != null && lost.getClient() != null && lost.getClient().getDecryptedEmail() != null && !lost.getClient().getDecryptedEmail().isEmpty()){
			try {

				String root = TracerProperties.get("email.resources");
				String configpath = root + "/";
				String imagepath = root + "/";
				boolean embedImage = true;
				
				HtmlEmail he = new HtmlEmail();
				String currentLocale = lost.getAgent().getCurrentlocale();
				
				String from = lost.getReservation().getDropoffLocation().getCompany().getVariable().getEmail_from();
				String host = lost.getReservation().getDropoffLocation().getCompany().getVariable().getEmail_host();
				int port = lost.getReservation().getDropoffLocation().getCompany().getVariable().getEmail_port();
				
				he.setHostName(host);
				he.setSmtpPort(port);

				he.setFrom(from);
				ArrayList al = new ArrayList();
				al.add(new InternetAddress(lost.getClient().getDecryptedEmail()));
				he.setTo(al);
				
				he.setSubject("Avis Budget Group - Notification");
				
				HashMap h = new HashMap();
				String htmlFileName = "update_report_email.html";
				
				
				h.put("LOSTID", (new Long(id)).toString());
				h.put("COMPANY", (lost.getCompanyId().equals(TracingConstants.LF_BUDGET_COMPANY_ID) ? "Budget" : "Avis"));

				// set embedded images
				if (embedImage) {
					String img1 = he.embed(new URL("file:" + imagepath + "avis_email_banner.jpg"),
					"avis_email_banner.jpg");
					h.put("BANNER_IMAGE", img1);
				}
					

				String msg = EmailParser.parse(configpath + htmlFileName, h, currentLocale);
				he.setHtmlMsg(msg);
				he.send();
				
				lost.setEmailSentDate(new Date());
				saveOrUpdateLostReport(lost, getAutoAgent());
				
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public void closeLostAndEmail(long id, Agent agent){
		LFLost lost = getLostReport(id);
		if(lost!=null){
			if(lost.getClient() != null && lost.getClient().getDecryptedEmail() != null && !lost.getClient().getDecryptedEmail().isEmpty()){
				try {
					String root = TracerProperties.get("email.resources");
					String configpath = root + "/";
					String imagepath = root + "/";
					boolean embedImage = true;

					HtmlEmail he = new HtmlEmail();
					String currentLocale = lost.getAgent().getCurrentlocale();

					String from = lost.getReservation().getDropoffLocation().getCompany().getVariable().getEmail_from();
					String host = lost.getReservation().getDropoffLocation().getCompany().getVariable().getEmail_host();
					int port = lost.getReservation().getDropoffLocation().getCompany().getVariable().getEmail_port();

					he.setHostName(host);
					he.setSmtpPort(port);

					he.setFrom(from);
					ArrayList al = new ArrayList();
					al.add(new InternetAddress(lost.getClient().getDecryptedEmail()));
					he.setTo(al);

					he.setSubject("Avis Budget Group - Notification");

					HashMap h = new HashMap();
					String htmlFileName = "closed_report_email.html";

					h.put("LOSTID", (new Long(id)).toString());
					h.put("MAXDAYS", PropertyBMO.getValue(PropertyBMO.LF_AUTO_CLOSE_DAYS));

					if (embedImage) {
						String img1 = he.embed(new URL("file:" + imagepath + "avis_email_banner.jpg"),
						"avis_email_banner.jpg");
						h.put("BANNER_IMAGE", img1);
					}

					String msg = EmailParser.parse(configpath + htmlFileName, h, currentLocale);
					if(msg == null){
						System.out.println("email is null");
					}
					he.setHtmlMsg(msg);
					he.send();
					lost.setEmailSentDate(new Date());
				}catch (Exception e){
					e.printStackTrace();
				}
			}
			lost.setCloseDate(new Date());
			Status status = new Status();
			status.setStatus_ID(TracingConstants.LF_STATUS_CLOSED);
			lost.setStatus(status);
			saveOrUpdateLostReport(lost, agent);
		}
	}
	
	public void autoClose(){
		int daysTillClose = PropertyBMO.getValueAsInt(PropertyBMO.LF_AUTO_CLOSE_DAYS);
		GregorianCalendar today = new GregorianCalendar();
		today.add(Calendar.DATE, -daysTillClose);
		String cutoff = DateUtils.formatDate(today.getTime(), TracingConstants.getDBDateTimeFormat(HibernateWrapper.getConfig().getProperties()), null, null);
		
		String sql = "select l.id lostid from lflost l " +
		" where l.status_ID != " + TracingConstants.LF_STATUS_CLOSED +
		" and l.openDate < \'" + cutoff + "\'";
		
		Session sess = null;
		SQLQuery pq = null;
		ArrayList<Long>lostIds = new ArrayList<Long>();
		try{
			sess = HibernateWrapper.getSession().openSession();
			pq = sess.createSQLQuery(sql.toString());
			pq.addScalar("lostid", Hibernate.LONG);
			List<Long> listMatchingFiles = pq.list();
			for (Long strs : listMatchingFiles) {
				lostIds.add((Long) strs);
			}
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(sess != null){
				sess.close();
			}
		}
		Agent agent = getAutoAgent();
		for(Long id:lostIds){
			System.out.println(id + " " + agent!=null?agent.getUsername():"null");
			closeLostAndEmail(id, agent);
		}
	}
	
	public void sendLostCreatedEmail(long id){
		LFLost lost = getLostReport(id);
		if(lost != null && lost.getClient() != null && lost.getClient().getDecryptedEmail() != null && !lost.getClient().getDecryptedEmail().isEmpty()){
			try {
				String root = TracerProperties.get("email.resources");
				String configpath = root + "/";
				String imagepath = root + "/";
				boolean embedImage = true;
				
				System.out.println("path: " + root);
				
				HtmlEmail he = new HtmlEmail();
				String currentLocale = lost.getAgent().getCurrentlocale();
				
				String from = lost.getReservation().getDropoffLocation().getCompany().getVariable().getEmail_from();
				String host = lost.getReservation().getDropoffLocation().getCompany().getVariable().getEmail_host();
				int port = lost.getReservation().getDropoffLocation().getCompany().getVariable().getEmail_port();
				
				he.setHostName(host);
				he.setSmtpPort(port);

				he.setFrom(from);
				ArrayList al = new ArrayList();
				al.add(new InternetAddress(lost.getClient().getDecryptedEmail()));
				he.setTo(al);
				
				he.setSubject("Avis Budget Group - Notification");
				
				HashMap h = new HashMap();
				String htmlFileName = "create_report_email.html";
				
				
				h.put("LOSTID", (new Long(id)).toString());
				h.put("DAYS", (new Integer(lost.getReservation().getDropoffLocation().getPriority()).toString()));//TODO station driven
				h.put("COMPANY", (lost.getCompanyId().equals(TracingConstants.LF_BUDGET_COMPANY_ID) ? "Budget" : "Avis"));

				
				// set embedded images
				if (embedImage) {
					try{
					String img1 = he.embed(new URL("file:" + imagepath + "avis_email_banner.jpg"),
							"avis_email_banner.jpg");
					h.put("BANNER_IMAGE", img1);
					}catch(Exception e){
						e.printStackTrace();
					}
				}
					
				
				String msg = EmailParser.parse(configpath + htmlFileName, h, currentLocale);
				if(msg == null){
					System.out.println("email is null");
				}
				he.setHtmlMsg(msg);
				he.send();
				
				lost.setEmailSentDate(new Date());
				saveOrUpdateLostReport(lost,getAutoAgent());
				
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public void testEmail(){
		try{
			HtmlEmail he = new HtmlEmail();
			
			String from = "test@nettracer.aero";
			String host = "74.86.4.179";
			int port = 8625;
			
			he.setHostName(host);
			he.setSmtpPort(port);

			he.setFrom(from);
			ArrayList al = new ArrayList();
			al.add(new InternetAddress("mloupas@nettracer.aero"));
			he.setTo(al);
			
			he.setSubject("Avis Budget Group - Test Email");
			
			he.setHtmlMsg("Test");
			he.send();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<LFSubCategory> getSubCategories(long categoryId) {
		Session sess = null;
		if (categoryId == 0)
			return new ArrayList<LFSubCategory>();
		try {
			sess = HibernateWrapper.getSession().openSession();
			String sql = " from com.bagnet.nettracer.tracing.db.lf.LFSubCategory c where c.parent.id = " + categoryId;
			Query query = sess.createQuery(sql);
			return query.list();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
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
	
	public static void main(String [] args){
		LFServiceBean bean = new LFServiceBean();
		bean.testEmail();
		bean.getStillSearchingList();
	}
	
}
