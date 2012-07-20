package aero.nettracer.lf.services;

import java.math.BigInteger;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.ejb.Stateless;
import javax.mail.internet.InternetAddress;

import org.apache.commons.lang.WordUtils;
import org.apache.struts.util.LabelValueBean;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import aero.nettracer.general.services.GeneralServiceBean;
import aero.nettracer.lf.services.exception.NonUniqueBarcodeException;
import aero.nettracer.lf.services.exception.UpdateException;
import aero.nettracer.security.AES;

import com.bagnet.nettracer.email.HtmlEmail;
import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.lf.LFBoxContainer;
import com.bagnet.nettracer.tracing.db.lf.LFBoxCount;
import com.bagnet.nettracer.tracing.db.lf.LFCategory;
import com.bagnet.nettracer.tracing.db.lf.LFDelivery;
import com.bagnet.nettracer.tracing.db.lf.LFFound;
import com.bagnet.nettracer.tracing.db.lf.LFItem;
import com.bagnet.nettracer.tracing.db.lf.LFLost;
import com.bagnet.nettracer.tracing.db.lf.LFPhone;
import com.bagnet.nettracer.tracing.db.lf.LFSalvage;
import com.bagnet.nettracer.tracing.db.lf.LFSalvageFound;
import com.bagnet.nettracer.tracing.db.lf.LFSegment;
import com.bagnet.nettracer.tracing.db.lf.detection.LFMatchDetail;
import com.bagnet.nettracer.tracing.db.lf.detection.LFMatchHistory;
import com.bagnet.nettracer.tracing.dto.LFSearchDTO;
import com.bagnet.nettracer.tracing.dto.SalvageDTO;
import com.bagnet.nettracer.tracing.forms.lf.HandleItemsForm;
import com.bagnet.nettracer.tracing.forms.lf.TraceResultsFilter;
import com.bagnet.nettracer.tracing.forms.lfc.SalvageSearchForm;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.EmailParser;
import com.bagnet.nettracer.tracing.utils.TracerProperties;
import com.bagnet.nettracer.tracing.utils.general.Logger;

@Stateless
public class LFServiceBean implements LFServiceRemote, LFServiceHome{
	
	private static int EMAIL_FIRST_NOTICE_DAY = 3;
	
	private static int EMAIL_SECOND_NOTICE_DAY = 7;
	
	private static String autoagent = "autoagent";
	
	private static Agent auto;
	
	private static ResourceBundle resources = ResourceBundle.getBundle("com.bagnet.nettracer.tracing.resources.ApplicationResources", new Locale("US"));
	
	public Agent getAutoAgent(){
		if (auto == null) {
			GeneralServiceBean bean = new GeneralServiceBean();
			auto = bean.getAgent(autoagent, TracerProperties.get("wt.company.code"));
		}
		return auto;
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
		
		if(TracingConstants.LF_LF_COMPANY_ID.equals(dto.getAgent().getCompanycode_ID()) && dto.getType() == TracingConstants.LF_TYPE_LOST && dto.getStationId() != -1)
		{
			sql += "left outer join o.segments seg";
		}
		
		if (category > 0 || haveBrand || haveDesc) {
			if(dto.getType() == TracingConstants.LF_TYPE_LOST){
				sql += " left outer join o.items i";
			} else {
				sql += " left outer join o.item i";
			}
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
				itemSql += " and i.brand like \'%" + dto.getBrand().trim() + "%\'";
			}
			
			if (haveDesc) {
				itemSql += " and i.description like \'%" + dto.getItemDescription().trim() + "%\'";
			}
			
			sql += itemSql;
		}
		
		if (phone != null) {
			sql += " and p.phoneNumber = \'" + phone + "\'";
		}
		
		if(dto.getType() == TracingConstants.LF_TYPE_FOUND && dto.getBarcode() != null && dto.getBarcode().trim().length() > 0){
			sql += " and o.barcode = \'" + dto.getBarcode() + "\'";
		} else if(dto.getId() > 0){
			sql += " and o.id = " + dto.getId();
		}

		if(dto.getLastName() != null && dto.getLastName().trim().length() > 0){
			sql += " and o.client.lastName = \'" + dto.getLastName().toUpperCase() + "\'";
		}
		if(dto.getFirstName() != null && dto.getFirstName().trim().length() > 0){
			sql += " and o.client.firstName = \'" + dto.getFirstName().toUpperCase() + "\'";
		}
		if(dto.getStationId() != -1){
			if(dto.getType() == TracingConstants.LF_TYPE_LOST){
				if(TracingConstants.LF_LF_COMPANY_ID.equals(dto.getAgent().getCompanycode_ID()))
				{
					sql += " and (seg.destination.station_ID = " + dto.getStationId() +" or seg.origin.station_ID = " + dto.getStationId()+")";
				} else {
					sql += " and o.lossInfo.destination.station_ID = " + dto.getStationId();
				}
			} else {
				sql += " and o.location.station_ID = " + dto.getStationId();
			}
		}
		if(dto.getStatusId() != -1){
			sql += " and o.status.status_ID = " + dto.getStatusId();
		}
		if(dto.getDispositionId() != -1){
			if(dto.getType() == TracingConstants.LF_TYPE_FOUND){
				sql += " and o.item.disposition.status_ID = " + dto.getDispositionId();
			}
		}
		if(dto.getMvaNumber() != null && dto.getMvaNumber().trim().length() > 0){
			if(dto.getType() == TracingConstants.LF_TYPE_LOST){
				sql += " and o.lossInfo.mvaNumber = \'" + dto.getMvaNumber() + "\'";
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
		if(dto.getStartRentDate() != null && dto.getStartRentDate().trim().length() > 0){
			String date = DateUtils.formatDate(dto.getStartRentDateAsDate(), TracingConstants.getDBDateFormat(HibernateWrapper.getConfig().getProperties()), null, null);
			if(dto.getType() == TracingConstants.LF_TYPE_LOST){
				sql += " and o.lossInfo.lossdate >= \'" + date + "\'";
			}/* else {
				sql += " and o.foundDate >= \'" + date + "\'";
			}*/
		}
		if(dto.getEndRentDate() != null && dto.getEndRentDate().trim().length() > 0){
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(dto.getEndRentDateAsDate());
			cal.add(Calendar.DATE, 1);
			String date = DateUtils.formatDate(cal.getTime(), TracingConstants.getDBDateFormat(HibernateWrapper.getConfig().getProperties()), null, null);
			if(dto.getType() == TracingConstants.LF_TYPE_LOST){
				sql += " and o.lossInfo.lossdate < \'" + date + "\'";
			}/* else {
				sql += " and o.foundDate < \'" + date + "\'";
			}*/
		}
		if(dto.getAgreementNumber() != null && dto.getAgreementNumber().trim().length() > 0){
			if(dto.getType() == TracingConstants.LF_TYPE_LOST){
				sql += " and o.lossInfo.agreementNumber = \'" + dto.getAgreementNumber().trim() + "\'";
			} else {
				sql += " and o.agreementNumber = \'" + dto.getAgreementNumber().trim() + "\'";
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
		
		if(dto.getAgentName() != null && dto.getAgentName().trim().length() > 0){
			try {
				String ag = dto.getAgentName();
				if(ag != null){
					sql += " and o.agent.username = \'" + ag + "\'";
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
	
	private void cleanUpSegments(LFLost toSave, boolean paxView) {
		if (toSave.getId() != 0 && paxView) {
			LFLost current = this.getLostReport(toSave.getId());
			if(current != null && current.getSegments() != null && current.getSegments().size() > 0
					&& toSave != null && toSave.getSegments() != null && toSave.getSegments().size() > 0) {
				Session sess = null;
				Transaction t = null;
				try{
					sess = HibernateWrapper.getSession().openSession();
					t = sess.beginTransaction();
					for (LFSegment cSeg : current.getSegments()) {
						boolean deleteThis = true;
						for (LFSegment sSeg : toSave.getSegments()) {
							if (sSeg.getId() == cSeg.getId()) {
								deleteThis = false;
							}
						}
						if (deleteThis) {
							sess.delete(cSeg);
						}
					}
					t.commit();
				}catch (Exception e) {
					e.printStackTrace();
					try {
						t.rollback();
					} catch (Exception ex) {
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
				} // END FINALLY
			} // END CHECK Segments
		} // END if (id != 0)
	}
	
	@Override
	public long saveOrUpdateLostReport(LFLost lostReport, Agent agent) throws UpdateException {
		return saveOrUpdateLostReport(lostReport, agent, false);
	}
	
	public long saveOrUpdateLostReport(LFLost lostReport, Agent agent, boolean paxView) throws UpdateException {
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
		
		cleanUpSegments(lostReport, paxView);
		
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
					Logger.logLF(""+reportId, "LOST " + LFLogUtil.EVENT_CREATE, 0);
				}
			} else if (isNewlyClosed && agent != null) {
				LFLogUtil.writeLog(agent.getUsername(), agent.getStation().getStationcode(), LFLogUtil.EVENT_CLOSE, (int) reportId, 0);
				Logger.logLF(""+reportId, "LOST " +  LFLogUtil.EVENT_CLOSE, 0);
			} else if (agent != null) {
				LFLogUtil.writeLog(agent.getUsername(), agent.getStation().getStationcode(), LFLogUtil.EVENT_MODIFY, (int) reportId, 0);
				Logger.logLF(""+reportId, "LOST " +  LFLogUtil.EVENT_MODIFY, 0);
			}
			return reportId;
		} else {
			throw new UpdateException();
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
	@Deprecated
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
	@Deprecated
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
	public LFFound getFoundItemByBarcode(String barcode) throws NonUniqueBarcodeException{
		if(barcode == null || barcode.trim().length() == 0){
			return null;
		}
		
		Session sess = HibernateWrapper.getSession().openSession();
		LFFound f = null;
		List<LFFound> list = null;
		try{
			Criteria crit = sess.createCriteria(LFFound.class).add(Restrictions.eq("barcode", barcode));
			list = crit.list();
		}catch (HibernateException e){
			e.printStackTrace();
		}
		finally{
			sess.close();
		}
		if(list != null && list.size() > 0){
			if(list.size() > 1){
				throw new NonUniqueBarcodeException(barcode);
			} else {
				f = list.get(0);
			}
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
	public long saveOrUpdateFoundItem(LFFound foundItem, Agent agent) throws UpdateException {
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
				Logger.logLF(""+reportId, "FOUND " + LFLogUtil.EVENT_CREATE, 0);
			} else if (isNewlyClosed && agent != null) {
				LFLogUtil.writeLog(agent.getUsername(), agent.getStation().getStationcode(), LFLogUtil.EVENT_CLOSE, 0, (int) reportId);
				Logger.logLF(""+reportId, "FOUND " + LFLogUtil.EVENT_CLOSE, 0);
			} else if (agent != null) {
				LFLogUtil.writeLog(agent.getUsername(), agent.getStation().getStationcode(), LFLogUtil.EVENT_MODIFY, 0, (int) reportId);
				Logger.logLF(""+reportId, "FOUND " + LFLogUtil.EVENT_MODIFY, 0);
			}
			return reportId;
		} else {
			if(foundItem != null && foundItem.getBarcode() != null){
				//did we fail on a barcode constraint?
				LFFound f = this.getFoundItemByBarcode(foundItem.getBarcode());
				if(f != null && f.getId() != foundItem.getId()){
					throw new NonUniqueBarcodeException(foundItem.getBarcode());
				}
			}
			throw new UpdateException();
		}
	}

	@Override
	public ArrayList<LabelValueBean> getColors() {
		ArrayList <LabelValueBean> colors = new ArrayList<LabelValueBean>();
		
		colors.add(new LabelValueBean("Does Not Apply", TracingConstants.LFC_COLOR_DOESNOTAPPLY));
		colors.add(new LabelValueBean("Black", "BK"));
		colors.add(new LabelValueBean("Blue", "BU"));
		colors.add(new LabelValueBean("Brown", "BN"));
		colors.add(new LabelValueBean("Gold", "GD"));
		colors.add(new LabelValueBean("Green", "GN"));
		colors.add(new LabelValueBean("Grey", "GY"));
		colors.add(new LabelValueBean("Multiple Colors", "MC"));
		colors.add(new LabelValueBean("Orange", "OR"));
		colors.add(new LabelValueBean("Pink", "PK"));
		colors.add(new LabelValueBean("Plaid", "PL"));
		colors.add(new LabelValueBean("Purple", "PU"));
		colors.add(new LabelValueBean("Red", "RD"));
		colors.add(new LabelValueBean("Silver", "SV"));
		colors.add(new LabelValueBean("White", "WT"));
		colors.add(new LabelValueBean("Yellow", "YW"));
		colors.add(new LabelValueBean("Clear", "CL"));
		
		
		return colors;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<LFCategory> getCategories(String companycode) {
		String sql = "from com.bagnet.nettracer.tracing.db.lf.LFCategory cat where cat.companycode = :companycode order by cat.description";
		Session sess = HibernateWrapper.getSession().openSession();
		Query q = sess.createQuery(sql);
		q.setParameter("companycode", companycode);
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
			try {
				if(saveOrUpdateLostReport(lost, agent) > -1){
					return true;
				}
			} catch (UpdateException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	private String getLostQuery(Station station){
		String sql = "from com.bagnet.nettracer.tracing.db.lf.LFLost l where ";
				if(!TracingConstants.LF_LF_COMPANY_ID.equals(station.getCompany().getCompanyCode_ID()))
				{
					sql +=" (l.lossInfo.destination.station_ID = " + station.getStation_ID()+
							" or l.lossInfo.destination.lz_ID = " + station.getStation_ID()+") and";
				}
				sql += " l.status.status_ID != " + TracingConstants.LF_STATUS_CLOSED;
				
		return sql;
	}
	
	private String getFoundQuery(Station station){
		String sql = "from com.bagnet.nettracer.tracing.db.lf.LFFound f " +
				"where (f.location.station_ID = " + station.getStation_ID()
				+ " or f.location.lz_ID = " + station.getStation_ID() + ")"
				+ " and f.status.status_ID != " + TracingConstants.LF_STATUS_CLOSED;
		return sql;
	}
	
	private String getItemsToSalvageQuery(Station station){
		int daysTillSalvage = PropertyBMO.getValueAsInt(PropertyBMO.LF_AUTO_SALVAGE_DAYS);
		GregorianCalendar today = new GregorianCalendar();
		today.add(Calendar.DATE, -daysTillSalvage);
		String cutoff = DateUtils.formatDate(today.getTime(), TracingConstants.getDBDateTimeFormat(HibernateWrapper.getConfig().getProperties()), null, null);
		
		String sql = "from com.bagnet.nettracer.tracing.db.lf.LFItem i " +
		"where (i.found.location.station_ID = " + station.getStation_ID() 
		+ " or i.found.location.lz_ID = " + station.getStation_ID() + ")"
		+ " and i.found.foundDate < \'" + cutoff + "\'"
		+ " and i.found.status.status_ID != " + TracingConstants.LF_STATUS_CLOSED
		+ " and i.disposition.status_ID = " + TracingConstants.LF_DISPOSITION_OTHER;
		return sql;
	}
	
	private String getLFCItemsToSalvageQuery(Station station, HandleItemsForm hiForm) {
		
		int lowValueSalvageDays = PropertyBMO.getValueAsInt("lf.low.value.salvage.days");
		int highValueSalvageDays = PropertyBMO.getValueAsInt("lf.high.value.salvage.days");
		
		String sql = "from com.bagnet.nettracer.tracing.db.lf.LFItem i " +
					 "where i.found.location.station_ID = " + station.getStation_ID() + " " +
					 "and i.type = " + TracingConstants.LF_TYPE_FOUND + " " +
					 "and (i.disposition.status_ID in (" + TracingConstants.LF_DISPOSITION_OTHER + "," + TracingConstants.LF_DISPOSITION_TO_BE_DELIVERED + ") or i.deliveryRejected = 1) ";
		
		if (hiForm == null) {
			
			sql += "and ((i.value = " + TracingConstants.LFC_ITEM_LOW_VALUE + " and dateDiff(now(),date(i.found.receivedDate)) >= " + lowValueSalvageDays + ") or " +
					 	"(i.value = " + TracingConstants.LFC_ITEM_HIGH_VALUE + " and dateDiff(now(),date(i.found.receivedDate)) >= " + highValueSalvageDays + "))";
		
		} else if (hiForm.getValue() == TracingConstants.LFC_ITEM_LOW_VALUE) {
		
			sql += "and i.value = " + TracingConstants.LFC_ITEM_LOW_VALUE + " and dateDiff(now(),date(i.found.receivedDate)) >= " + lowValueSalvageDays + " ";
		
		} else if (hiForm.getValue() == TracingConstants.LFC_ITEM_HIGH_VALUE) {
		
			sql += "and i.value = " + TracingConstants.LFC_ITEM_HIGH_VALUE + " and dateDiff(now(),date(i.found.receivedDate)) >= " + highValueSalvageDays + " ";
		
		}
		
		if (hiForm != null && hiForm.getStartDate() != null && !hiForm.getStartDate().isEmpty()) {
			String startDate = hiForm.getStartDate();
			Calendar start = new GregorianCalendar();
			start.setTime(DateUtils.convertToDate(startDate, hiForm.getDateFormat(), null));

			
			String endDate = hiForm.getEndDate();
			if (endDate == null || endDate.isEmpty()) {
				endDate = startDate;
			}
			
			Calendar end = new GregorianCalendar();
			end.setTime(DateUtils.convertToDate(endDate, hiForm.getDateFormat(), null));
			end.add(Calendar.DAY_OF_MONTH, 1);
			
			startDate = DateUtils.formatDate(start.getTime(), TracingConstants.DB_DATEFORMAT, null, null);
			endDate = DateUtils.formatDate(end.getTime(), TracingConstants.DB_DATEFORMAT, null, null);
			
			sql += "and i.found.receivedDate between \'" + startDate + "\' and \'" + endDate + "\' ";
		}
		
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
		} else if (station.getCompany().getCompanyCode_ID().equals(TracingConstants.LF_AB_COMPANY_ID)) {
			return getItemCount(getItemsToSalvageQuery(station));
		} else if (station.getCompany().getCompanyCode_ID().equals(TracingConstants.LF_LF_COMPANY_ID)) {
			return getItemCount(getLFCItemsToSalvageQuery(station, null));
		}
		return 0;
	}
	
	public int getLFItemsToSalvageCount(Station station, HandleItemsForm hiForm) {
		return getItemCount(getLFCItemsToSalvageQuery(station, hiForm));
	}

	@Override
	public List<LFItem> getItemsToSalvagePaginatedList(Station station, int start, int offset) {
		if(station == null){
			return null;
		}
		return getItemPaginatedList(getItemsToSalvageQuery(station), start, offset);
	}
	
	public List<LFItem> getLFItemsToSalvagePaginatedList(Station station, HandleItemsForm hiForm, int start, int offset) {
		if (station == null || hiForm == null) {
			return null;
		}
		return getItemPaginatedList(getLFCItemsToSalvageQuery(station, hiForm), start, offset);
	}

	private String getTraceResultsQuery(Station station){
		//TODO location criteria
		String sql = "from com.bagnet.nettracer.tracing.db.lf.detection.LFMatchHistory mh";
				
		sql += " where mh.status.status_ID = " + TracingConstants.LF_TRACING_OPEN;
		if(!TracingConstants.LF_LF_COMPANY_ID.equals(station.getCompany().getCompanyCode_ID()))
		{
			sql +=" and (mh.lost.lossInfo.destination.station_ID = " + station.getStation_ID() + " or "+
					" mh.lost.lossInfo.destination.lz_ID = " + station.getStation_ID() + " or "+
					" mh.found.location.station_ID = " + station.getStation_ID() + " or " +
					" mh.found.location.lz_ID = " + station.getStation_ID() + ")";
		} 
		return sql;
	}
	
	@Override
	//TODO review if this needs to be deprecated
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
	//TODO review if this needs to be deprecated
	public List<LFMatchHistory> getTraceResultsPaginated(Station station, int start, int offset) {
		if(station == null){
			return null;
		}
		String query = getTraceResultsQuery(station) + " order by score desc, id";
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
				"where (i.found.location.station_ID = " + station.getStation_ID() +
				" or i.found.location.lz_ID = " + station.getStation_ID() + ")" +
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
		"where (i.found.location.station_ID = " + station.getStation_ID() +
		" or i.found.location.lz_ID = " + station.getStation_ID() + ")" +
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
	@Deprecated
	public String getLostReport(Date startdate, Date enddate, Station station,
			int matchType, boolean shipped) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Deprecated
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
		return LFTracingUtil.saveLFMatchHistory(match);
	}

	@Override
	public long createManualMatch(LFLost lost, LFFound found){
		LFMatchHistory match = new LFMatchHistory();
		match.setFound(found);
		match.setLost(lost);
		if(found != null && lost != null && !isAlreadyMatched(match)) {
			// MJS: The Management Summary and Itemization Report depends on
			// manual matches having a score of -1. If this number is changed
			// for any reason, make sure to change the score in the query for
			// that report as well.
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
			q.setParameter("score", -1.0);
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
		ArrayList<LFMatchHistory> ret = new ArrayList<LFMatchHistory>();
		List<LFMatchHistory> toAdd = this.traceFoundItem(id, true);
		if(toAdd!=null){
			ret.addAll(toAdd);
		}
		if(PropertyBMO.isTrue(PropertyBMO.LF_TRACING_SECONDARY_TRACE)){
			toAdd = this.traceFoundItem(id, false);
			if(toAdd!=null){
				ret.addAll(toAdd);
			}
		}
		return ret;
	}
	
	@Override
	public List<LFMatchHistory> traceFoundItem(long id, boolean isPrimary) {
		try {
			return LFTracingUtil.traceFound(id, this, true, isPrimary);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<LFMatchHistory> traceLostItem(long id) {
		ArrayList<LFMatchHistory> ret = new ArrayList<LFMatchHistory>();
		List<LFMatchHistory> toAdd = this.traceLostItem(id, true);
		if(toAdd!=null){
			ret.addAll(toAdd);
		}
		if(PropertyBMO.isTrue(PropertyBMO.LF_TRACING_SECONDARY_TRACE)){
			toAdd = this.traceLostItem(id, false);
			if(toAdd!=null){
				ret.addAll(toAdd);
			}
		}
		return ret;
	}
	
	@Override
	public List<LFMatchHistory> traceLostItem(long id, boolean isPrimary){
		try {
			return LFTracingUtil.traceLost(id, this, true, isPrimary);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean confirmMatch(long id, Agent agent) {
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
					try {
						saveOrUpdateFoundItem(match.getFound(), agent);
					} catch (UpdateException e) {
						e.printStackTrace();
						return false;
					}
				}
				
				if(match.getLost() != null && match.getLost().getItem() != null){
					Status deliveryStatus = new Status();
					deliveryStatus.setStatus_ID(TracingConstants.LF_DISPOSITION_TO_BE_DELIVERED);
					match.getLost().getItem().setDisposition(deliveryStatus);
					match.getLost().getItem().setFound(match.getFound());
					try {
						saveOrUpdateLostReport(match.getLost(), agent);
					} catch (UpdateException e) {
						e.printStackTrace();
						return false;
					}
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
	public boolean rejectMatch(long id, Agent agent) {
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
	public boolean unrejectMatch(long id, Agent agent){
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
	public boolean undoMatch(long id, Agent agent) {
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
				try {
					saveOrUpdateFoundItem(match.getFound(), agent);
				} catch (UpdateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return false;
				}
			}
			
			if(match.getLost() != null && match.getLost().getItem() != null){
				Status deliveryStatus = new Status();
				deliveryStatus.setStatus_ID(TracingConstants.LF_DISPOSITION_OTHER);
				match.getLost().getItem().setDisposition(deliveryStatus);
				match.getLost().getItem().setFound(null);
				try {
					saveOrUpdateLostReport(match.getLost(), agent);
				} catch (UpdateException e) {
					e.printStackTrace();
					return false;
				}
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
		if(!TracingConstants.LF_LF_COMPANY_ID.equals(station.getCompany().getCompanyCode_ID()))
		{
			sql +=" and (m.lost.lossInfo.destination.station_ID = " + station.getStation_ID() + " or "+
					" m.lost.lossInfo.destination.lz_ID = " + station.getStation_ID() + " or "+
					" m.found.location.station_ID = " + station.getStation_ID() + " or " +
			 		" m.found.location.lz_ID = " + station.getStation_ID() + ")";
		}
		
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
		String sql = "from com.bagnet.nettracer.tracing.db.lf.detection.LFMatchHistory m ";
		
		sql+=" where 1 = 1 ";
		sql += getSqlFromTraceResultsForm(filter);
		if(!TracingConstants.LF_LF_COMPANY_ID.equals(station.getCompany().getCompanyCode_ID()))
		{
			sql +=" and (m.lost.lossInfo.destination.station_ID = " + station.getStation_ID() + " or "+
					" m.lost.lossInfo.destination.lz_ID = " + station.getStation_ID() + " or " +
					" m.found.location.station_ID = " + station.getStation_ID() + " or " +
			 		" m.found.location.lz_ID = " + station.getStation_ID() + ")";
		}
		
 		sql +=	" order by score desc, id";
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

		if (filter.getBarcode() != null && !filter.getBarcode().equals("")) {
			sql += " and m.found.barcode = '" + filter.getBarcode() + "'";
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
		String sql = "select l.id lostid from lflost l, station s, lflossinfo r " +
		" where l.status_ID = " + TracingConstants.LF_STATUS_OPEN +
		" and l.lossInfo_id = r.id " +
		" and r.destination_station_ID = s.Station_ID " +
		" and (datediff(curdate(),l.emailSentDate) >= s.priority or l.emailSentDate is null)" +
		" and l.id not in (select mh.lost_id from lfmatchhistory mh where mh.lost_id = l.id and mh.status_Status_ID = " +
		TracingConstants.LF_TRACING_CONFIRMED +
		" ) ";

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
	
	protected List<Long> getXDayList(int day, int notice) throws Exception{
		String sql = "select l.id lostid from lflost l " +
		" where l.status_ID != " + TracingConstants.LF_STATUS_CLOSED +
		" and (datediff(curdate(),l.openDate)) = :day " +
		" and l.id not in (select mh.lost_id from lfmatchhistory mh where mh.lost_id = l.id and mh.status_Status_ID = " +
		TracingConstants.LF_TRACING_CONFIRMED +
		" ) ";
		
		switch(notice){
		case 1: sql+=" and l.email1 = 0";break;
		case 2: sql+=" and l.email2 = 0";break;
		default:throw new Exception();
		}
		
		Session sess = null;
		SQLQuery pq = null;
		ArrayList<Long>lostIds = new ArrayList<Long>();
		try{
			sess = HibernateWrapper.getSession().openSession();
			pq = sess.createSQLQuery(sql.toString());
			pq.setParameter("day", day);
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
		
		return lostIds;
	}
	
	public void send1stNoticeEmails(){
		try {
			List<Long> lostIds = getXDayList(EMAIL_FIRST_NOTICE_DAY, 1);
			if(lostIds != null){
				for(Long id: lostIds){
					send1stNotice(id);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void send2ndNoticeEmails(){
		try {
			List<Long> lostIds = getXDayList(EMAIL_SECOND_NOTICE_DAY, 2);
			if(lostIds != null){
				for(Long id: lostIds){
					send2ndNotice(id);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	
	private HashMap<String,String> getEmailParams(LFLost lost){
		HashMap<String,String> h = new HashMap<String,String>();
		h.put("LOSTID", (new Long(lost.getId())).toString());
		
		if(lost.getLossInfo()!=null && lost.getLossInfo().getDestination()!= null){
			h.put("DAYS", (new Integer(lost.getLossInfo().getDestination().getPriority()).toString()));
		}
				
		if(lost.getClient() != null){
			h.put("FIRSTNAME", WordUtils.capitalize(lost.getClient().getFirstName().toLowerCase()));
			h.put("LASTNAME", WordUtils.capitalize(lost.getClient().getLastName().toLowerCase()));
		}
		h.put("MAXDAYS", PropertyBMO.getValue(PropertyBMO.LF_AUTO_CLOSE_DAYS));
		
		//by default we use the return address from the company variable
		h.put("RETURNADDRESS", getAutoAgent().getStation().getCompany().getVariable().getEmail_from());
		
		if("AB".equalsIgnoreCase(TracingConstants.LF_SUBCOMPANIES.get(lost.getCompanyId()))){
			h.put("COMPANY", (lost.getCompanyId().equals(TracingConstants.LF_BUDGET_COMPANY_ID) ? "Budget" : "Avis"));
			h.put("SUBJECTLINE", resources.getString("ab.email.subject"));
		} else if (TracingConstants.LF_SWA_COMPANY_ID.equalsIgnoreCase(lost.getCompanyId())){
			h.put("COMPANY", "Southwest");
			h.put("SUBJECTLINE", resources.getString("wn.email.subject"));
		} else {
			h.put("COMPANY", "Company");
			h.put("SUBJECTLINE", resources.getString("dm.email.subject"));
		}
		
		return h;
	}
	
	private HashMap<String,String> getEmailParams(){
		
		
		HashMap<String,String> h = new HashMap<String,String>();
		GregorianCalendar cal=new GregorianCalendar();
		long lastFour = System.currentTimeMillis() + (4 * 86400 * 7 * 1000);
		long lastWeek = System.currentTimeMillis() + (86400 * 7 * 1000);
		Date endDate=new Date();
		//DateUtils.addDays(endDate, -7);
		Date week=DateUtils.addDays(endDate, -7);
		Date fourWeek=DateUtils.addDays(endDate, -28);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		Date beginMonth=cal.getTime();
		cal.set(Calendar.DAY_OF_YEAR, 1);
		Date beginYear=cal.getTime();
		System.out.println(DateUtils.formatDate(fourWeek,TracingConstants.DB_DATEFORMAT, null, null));
		
		int weeklyTotal=0;
		int MTD=0;
		int YTD=0;
		//DateUtils.
		cal.setTime(week);
		int i=1;
		do{
			h.put("date"+i, DateUtils.formatDate(cal.getTime(),TracingConstants.DISPLAY_DATEFORMAT, null, null));
			cal.add(cal.DATE, 1);
			i++;
		}
		while(endDate.after(cal.getTime()));
		cal.setTime(fourWeek);
		i=1;
		do{
			h.put("week"+i,DateUtils.formatDate(cal.getTime(), TracingConstants.DISPLAY_DATEFORMAT, null, null));
			cal.add(cal.DATE, 7);
			i++;
		}
		while((DateUtils.addDays(endDate,1)).after(cal.getTime()));
		
		getLostReportsEntered(h, endDate,week);
		getBoxesEntered(h,endDate,week);	
		getHighValueEntered(h,endDate,week);
		getLowValueEntered(h,endDate,week);
		getHighValueReturned(h,endDate,week);
		getLowValueReturned(h,endDate,week);
		getMTDTotals(h,endDate,beginMonth);
		getYTDTotals(h,endDate,beginYear);
		getWeekItemCounts(h,endDate,fourWeek);		
		
		h.put("STARTDATE",DateUtils.formatDate(fourWeek, TracingConstants.DISPLAY_DATEFORMAT, null, null));
		h.put("ENDDATE", DateUtils.formatDate(endDate, TracingConstants.DISPLAY_DATEFORMAT, null, null));
		h.put("SUBJECTLINE", "Weekly Summary Report: {STARTDATE} - {ENDDATE}");
		
		return h;
	}
	
	public void getBoxesEntered(HashMap h,Date endDate, Date week)
	{
		int weeklyTotal=0;
		//Boxes Entered
		String sql = "select bc.dateCount, case sum(b.boxCount) when null then 0 else sum(b.boxCount) end from lfBoxCount b left join lfboxcontainer bc on bc.id =b.container_ID "+
				"where bc.dateCount < :enddate and bc.dateCount >= :lastweek group by date(bc.dateCount)";
		SQLQuery q = null;
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			q = sess.createSQLQuery(sql.toString());
			
			q.setParameter("enddate", DateUtils.formatDate(endDate, TracingConstants.DB_DATEFORMAT, null, null));
			q.setParameter("lastweek", DateUtils.formatDate(week, TracingConstants.DB_DATEFORMAT, null, null));
			
			List lis = q.list();
			Object[] o;
			if(lis.size()!=0){
			for (int i = 0; i < lis.size(); ++i) {
				o = (Object[]) lis.get(i);
				for(int j=1; j<8; j++){
					if(DateUtils.formatDate(o[0].toString(), TracingConstants.DB_DATETIMEFORMAT,TracingConstants.DISPLAY_DATEFORMAT, null, null).equals(h.get("date"+j))){
						if(o[1]!=null){
							h.put("BoxesReceived"+(j), o[1].toString());
							weeklyTotal+=Integer.valueOf(o[1].toString());
						} else {
		
							h.put("BoxesReceived"+(j), "0");
						}
					}
					else if(h.get("BoxesReceived"+(j))==(null))
					{
						h.put("BoxesReceived"+(j), "0");
					}
				}
			}
			}
			else
			{
				for(int j=1; j<8; j++){
					h.put("BoxesReceived"+(j), "0");
				}
			}
			h.put("BoxesReceivedWeek", String.valueOf(weeklyTotal));
		} catch (Exception e) {
			e.printStackTrace();
			return;
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
	
	public void getLostReportsEntered(HashMap h,Date endDate, Date week)
	{
	int weeklyTotal=0;
		//Lost Reports Entered
		String sql = "select opendate, case count(*) when null then 0 else count(*) end from lflost "+
				"where openDate < :enddate and OpenDate >= :lastweek group by date(openDate)";
		SQLQuery q = null;
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			q = sess.createSQLQuery(sql.toString());
			
			q.setParameter("enddate", DateUtils.formatDate(endDate, TracingConstants.DB_DATEFORMAT, null, null));
			q.setParameter("lastweek", DateUtils.formatDate(week, TracingConstants.DB_DATEFORMAT, null, null));
			
			List lis = q.list();
			Object[] o;
			if(lis.size()!=0){
			for (int i = 0; i < lis.size(); i++) {
				o = (Object[]) lis.get(i);
				for(int j=1; j<8; j++){
					if(DateUtils.formatDate((Date)o[0], TracingConstants.DISPLAY_DATEFORMAT, null, null).equals(h.get("date"+(j)))){
						if(o[1]!=null){
						h.put("LostEntered"+(j), o[1].toString());
						weeklyTotal+=Integer.valueOf(o[1].toString());
						}
						else {
							h.put("LostEntered"+(j), "0");
						}
					}
					else if(h.get("LostEntered"+(j))==(null))
					{
						h.put("LostEntered"+(j), "0");
					}
				}
			}
			}
			else
			{
				for(int j=1; j<8; j++){
					h.put("LostEntered"+(j), "0");
				}
			}
			h.put("LostWeekEntered", String.valueOf(weeklyTotal));
		} catch (Exception e) {
			e.printStackTrace();
			return;
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
	

	public void getHighValueEntered(HashMap h,Date endDate, Date week)
	{
		int weeklyTotal=0;
		//High Value Items entered
		String sql = "select f.receivedDate, case count(*) when null then 0 else count(*) end from lfitem i join lffound f on f.id=i.found_id "+
				"where i.value=1 and f.receivedDate < :enddate and f.receivedDate >= :lastweek group by date(f.receivedDate)";
		SQLQuery q = null;
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			q = sess.createSQLQuery(sql.toString());
			
			q.setParameter("enddate", DateUtils.formatDate(endDate, TracingConstants.DB_DATEFORMAT, null, null));
			q.setParameter("lastweek", DateUtils.formatDate(week, TracingConstants.DB_DATEFORMAT, null, null));
			
			List lis = q.list();
			Object[] o;
			if(lis.size()!=0){
			for (int i = 0; i < lis.size(); ++i) {
				o = (Object[]) lis.get(i);
				for(int j=1; j<8; j++){
					if(DateUtils.formatDate((Date)o[0], TracingConstants.DISPLAY_DATEFORMAT, null, null).equals(h.get("date"+j))){
						if(o[1]!=null){
							h.put("HighEntered"+(j), o[1].toString());
							weeklyTotal+=Integer.valueOf(o[1].toString());
						} else {
							h.put("HighEntered"+(j), "0");
						}
					}
					else if(h.get("HighEntered"+(j))==(null))
					{
						h.put("HighEntered"+(j), "0");
					}
				}
			}
			}
			else
			{
				for(int j=1; j<8; j++){
					h.put("HighEntered"+(j), "0");
				}
			}
			h.put("HighEnteredWeek", String.valueOf(weeklyTotal));
		} catch (Exception e) {
			e.printStackTrace();
			return;
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
	
	public void getLowValueEntered(HashMap h,Date endDate, Date week)
	{
		int weeklyTotal=0;
		//Est Low Value Items entered
		
		String sql = "select f.receivedDate, case count(*) when null then 0 else count(*) end from lfitem i join lffound f on f.id=i.found_id "+
				"where i.value=0 and f.receivedDate < :enddate and f.receivedDate >= :lastweek group by date(f.receivedDate)";
		SQLQuery q = null;
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			q = sess.createSQLQuery(sql.toString());
			
			q.setParameter("enddate", DateUtils.formatDate(endDate, TracingConstants.DB_DATEFORMAT, null, null));
			q.setParameter("lastweek", DateUtils.formatDate(week, TracingConstants.DB_DATEFORMAT, null, null));
			
			List lis = q.list();
			Object[] o;
			if(lis.size()!=0){
			for (int i = 0; i < lis.size(); ++i) {
				o = (Object[]) lis.get(i);
				for(int j=1; j<8; j++){
					if(DateUtils.formatDate((Date)o[0], TracingConstants.DISPLAY_DATEFORMAT, null, null).equals(h.get("date"+j))){
						if(o[1]!=null){
							h.put("LowEntered"+(j), o[1].toString());
							weeklyTotal+=Integer.valueOf(o[1].toString());
						} else {
							h.put("LowEntered"+(j), "0");
						}
					}
					else if(h.get("LowEntered"+(j))==(null))
					{
						h.put("LowEntered"+(j), "0");
					}
				}
			}

			}
			else
			{
				for(int j=1; j<8; j++){
					h.put("LowEntered"+(j), "0");
				}
			}
			h.put("LowEnteredWeek", String.valueOf(weeklyTotal));
		} catch (Exception e) {
			e.printStackTrace();
			return;
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
	
	public void getHighValueReturned(HashMap h, Date endDate, Date week)
	{
		int weeklyTotal=0;
		//High Value Items returned
		String sql = "select f.deliveredDate, case count(*) when null then 0 else count(*) end from lfitem i join lffound f on f.id=i.found_id "+
				"where i.value=1 and f.deliveredDate < :enddate and f.deliveredDate >= :lastweek and ((trackingNumber is not null and trackingNumber != '') or i.deliveryRejected = 1) group by date(f.deliveredDate)";
		SQLQuery q = null;
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			q = sess.createSQLQuery(sql.toString());
			
			q.setParameter("enddate", DateUtils.formatDate(endDate, TracingConstants.DB_DATEFORMAT, null, null));
			q.setParameter("lastweek", DateUtils.formatDate(week, TracingConstants.DB_DATEFORMAT, null, null));
			
			List lis = q.list();
			Object[] o;
			if(lis.size()!=0){
			for (int i = 0; i < lis.size(); ++i) {
				o = (Object[]) lis.get(i);
				for(int j=1; j<8; j++){
					if(DateUtils.formatDate((Date)o[0], TracingConstants.DISPLAY_DATEFORMAT, null, null).equals(h.get("date"+j))){
						if(o[1]!=null){
							h.put("HighReturned"+(j), o[1].toString());
							weeklyTotal+=Integer.valueOf(o[1].toString());
						} else {
							h.put("HighReturned"+(j), "0");					
						}
					}
					else if(h.get("HighReturned"+(j))==(null))
					{
						h.put("HighReturned"+(j), "0");
					}
				}
			}
			}
			else
			{
				for(int j=1; j<8; j++){
					h.put("HighReturned"+(j), "0");
				}
			}
			h.put("HighReturnedWeek", weeklyTotal);
		} catch (Exception e) {
			e.printStackTrace();
			return;
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
	
	public void getLowValueReturned(HashMap h, Date endDate, Date week)
	{
		int weeklyTotal=0;
		//Low Value Items returned
		String sql = "select f.deliveredDate, case count(*) when null then 0 else count(*) end from lfitem i join lffound f on f.id=i.found_id "+
				"where i.value=0 and f.deliveredDate < :enddate and f.deliveredDate >= :lastweek and ((trackingNumber is not null and trackingNumber != '') or i.deliveryRejected = 1) group by date(f.deliveredDate)";
		SQLQuery q = null;
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			q = sess.createSQLQuery(sql.toString());
			
			q.setParameter("enddate", DateUtils.formatDate(endDate, TracingConstants.DB_DATEFORMAT, null, null));
			q.setParameter("lastweek", DateUtils.formatDate(week, TracingConstants.DB_DATEFORMAT, null, null));
			
			List lis = q.list();
			Object[] o;
			if(lis.size()!=0){
			for (int i = 0; i < lis.size(); ++i) {
				o = (Object[]) lis.get(i);
				for(int j=1; j<8; j++){
					if(DateUtils.formatDate((Date)o[0], TracingConstants.DISPLAY_DATEFORMAT, null, null).equals(h.get("date"+j))){
						if(o[1]!=null){
							h.put("LowReturned"+(j), o[1].toString());
							weeklyTotal+=Integer.valueOf(o[1].toString());
						} else {
							h.put("LowReturned"+(j), "0");
						}
					}
					else if(h.get("LowReturned"+(j))==(null))
					{
						h.put("LowReturned"+(j), "0");
					}
				}
			}
			}
			else
			{
				for(int j=1; j<8; j++){
					h.put("LowReturned"+(j), "0");
				}
			}
			h.put("LowReturnedWeek", weeklyTotal);
		} catch (Exception e) {
			e.printStackTrace();
			return;
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
	
	public void getMTDTotals(HashMap h, Date endDate, Date week)
	{
		//Low Value Items returned
		String sql = "select f.deliveredDate, case count(*) when null then 0 else count(*) end from lfitem i join lffound f on f.id=i.found_id "+
				"where i.value=0 and f.deliveredDate < :enddate and f.deliveredDate >= :lastweek and ((trackingNumber is not null and trackingNumber != '') or i.deliveryRejected = 1)";
		SQLQuery q = null;
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			q = sess.createSQLQuery(sql.toString());
			
			q.setParameter("enddate", DateUtils.formatDate(endDate, TracingConstants.DB_DATEFORMAT, null, null));
			q.setParameter("lastweek", DateUtils.formatDate(week, TracingConstants.DB_DATEFORMAT, null, null));
			
			List lis = q.list();
			Object[] o;
			if(lis.size()!=0){
				o=(Object[]) lis.get(0);
				if(o[1]!=null){
					h.put("LowReturnedMTD", o[1].toString());
				}
				else {
					h.put("LowReturnedMTD", "0");
				}
			}
			else {
				h.put("LowReturnedMTD", "0");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		//High Value Items Returned Month to Date
		sql = "select f.deliveredDate, case count(*) when null then 0 else count(*) end from lfitem i join lffound f on f.id=i.found_id "+
				"where i.value=1 and f.deliveredDate < :enddate and f.deliveredDate >= :lastweek and ((trackingNumber is not null and trackingNumber != '') or i.deliveryRejected = 1)";
		q = null;
		sess = HibernateWrapper.getSession().openSession();
		try {
			q = sess.createSQLQuery(sql.toString());
			
			q.setParameter("enddate", DateUtils.formatDate(endDate, TracingConstants.DB_DATEFORMAT, null, null));
			q.setParameter("lastweek", DateUtils.formatDate(week, TracingConstants.DB_DATEFORMAT, null, null));
			
			List lis = q.list();
			Object[] o;
			if(lis.size()!=0){
				o=(Object[]) lis.get(0);
				if(o[1]!=null){
					h.put("HighReturnedMTD", o[1].toString());
				}
				else {
					h.put("HighReturnedMTD", "0");
				}
			}
			else {
				h.put("HighReturnedMTD", "0");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		//Low Value Items Entered Month to Date
		sql = "select f.receivedDate, case count(*) when null then 0 else count(*) end from lfitem i join lffound f on f.id=i.found_id "+
				"where i.value=0 and f.receivedDate < :enddate and f.receivedDate >= :lastweek";
		q = null;
		sess = HibernateWrapper.getSession().openSession();
		try {
			q = sess.createSQLQuery(sql.toString());
			
			q.setParameter("enddate", DateUtils.formatDate(endDate, TracingConstants.DB_DATEFORMAT, null, null));
			q.setParameter("lastweek", DateUtils.formatDate(week, TracingConstants.DB_DATEFORMAT, null, null));
			
			List lis = q.list();
			Object[] o;
			if(lis.size()!=0){
				o=(Object[]) lis.get(0);
				if(o[1]!=null){
					h.put("LowEnteredMTD", o[1].toString());
				}
				else {
					h.put("LowEnteredMTD", "0");
				}
			}
			else {
				h.put("LowEnteredMTD", "0");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		//High Value Items Entered Month to Date
		sql = "select f.receivedDate, case count(*) when null then 0 else count(*) end from lfitem i join lffound f on f.id=i.found_id "+
				"where i.value=1 and f.receivedDate < :enddate and f.receivedDate >= :lastweek";
		q = null;
		sess = HibernateWrapper.getSession().openSession();
		try {
			q = sess.createSQLQuery(sql.toString());
			
			q.setParameter("enddate", DateUtils.formatDate(endDate, TracingConstants.DB_DATEFORMAT, null, null));
			q.setParameter("lastweek", DateUtils.formatDate(week, TracingConstants.DB_DATEFORMAT, null, null));
			
			List lis = q.list();
			Object[] o;
			if(lis.size()!=0){
				o=(Object[]) lis.get(0);
				if(o[1]!=null){
					h.put("HighEnteredMTD", o[1].toString());
				}
				else {
					h.put("HighEnteredMTD", "0");
				}
			}
			else {
				h.put("HighEnteredMTD", "0");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		//Boxes Entered Month to Date
		sql = "select bc.dateCount, case sum(b.boxCount) when null then 0 else sum(b.boxCount) end from lfBoxCount b left join lfboxcontainer bc on bc.id =b.container_ID "+
				"where bc.dateCount < :enddate and bc.dateCount >= :lastweek";
		q = null;
		sess = HibernateWrapper.getSession().openSession();
		try {
			q = sess.createSQLQuery(sql.toString());
			
			q.setParameter("enddate", DateUtils.formatDate(endDate, TracingConstants.DB_DATEFORMAT, null, null));
			q.setParameter("lastweek", DateUtils.formatDate(week, TracingConstants.DB_DATEFORMAT, null, null));
			
			List lis = q.list();
			Object[] o;
			if(lis.size()!=0){
				o=(Object[]) lis.get(0);
				if(o[1]!=null){
					h.put("BoxesReceivedMTD", o[1].toString());
				}
				else {
					h.put("BoxesReceivedMTD", "0");
				}
			}
			else {
				h.put("BoxesReceivedMTD", "0");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		//Lost Reports Entered Month to Date
		sql = "select opendate, case count(*) when null then 0 else count(*) end from lflost "+
				"where openDate < :enddate and OpenDate >= :lastweek";
		q = null;
		sess = HibernateWrapper.getSession().openSession();
		try {
			q = sess.createSQLQuery(sql.toString());
			
			q.setParameter("enddate", DateUtils.formatDate(endDate, TracingConstants.DB_DATEFORMAT, null, null));
			q.setParameter("lastweek", DateUtils.formatDate(week, TracingConstants.DB_DATEFORMAT, null, null));
			
			List lis = q.list();
			Object[] o;
			if(lis.size()!=0){
				o=(Object[]) lis.get(0);
				if(o[1]!=null){
					h.put("LostMTDEntered", o[1].toString());
				}
				else {
					h.put("LostMTDEntered", "0");
				}
			}
			else {
				h.put("LostMTDEntered", "0");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
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
	
	public void getYTDTotals(HashMap h, Date endDate, Date week)
	{
		//Low Value Items returned
		String sql = "select f.deliveredDate, case count(*) when null then 0 else count(*) end from lfitem i join lffound f on f.id=i.found_id "+
				"where i.value=0 and f.deliveredDate < :enddate and f.deliveredDate >= :lastweek and ((trackingNumber is not null and trackingNumber != '') or i.deliveryRejected = 1)";
		SQLQuery q = null;
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			q = sess.createSQLQuery(sql.toString());
			
			q.setParameter("enddate", DateUtils.formatDate(endDate, TracingConstants.DB_DATEFORMAT, null, null));
			q.setParameter("lastweek", DateUtils.formatDate(week, TracingConstants.DB_DATEFORMAT, null, null));
			
			List lis = q.list();
			Object[] o;
			if(lis.size()!=0){
				o=(Object[]) lis.get(0);
				if(o[1]!=null){
					h.put("LowReturnedYTD", o[1].toString());
				}
				else {
					h.put("LowReturnedYTD", "0");
				}
			}
			else {
				h.put("LowReturnedYTD", "0");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		//High Value Items Returned year to Date
		sql = "select f.deliveredDate, case count(*) when null then 0 else count(*) end from lfitem i join lffound f on f.id=i.found_id "+
				"where i.value=1 and f.deliveredDate < :enddate and f.deliveredDate >= :lastweek and ((trackingNumber is not null and trackingNumber != '') or i.deliveryRejected = 1)";
		q = null;
		sess = HibernateWrapper.getSession().openSession();
		try {
			q = sess.createSQLQuery(sql.toString());
			
			q.setParameter("enddate", DateUtils.formatDate(endDate, TracingConstants.DB_DATEFORMAT, null, null));
			q.setParameter("lastweek", DateUtils.formatDate(week, TracingConstants.DB_DATEFORMAT, null, null));
			
			List lis = q.list();
			Object[] o;
			if(lis.size()!=0){
				o=(Object[]) lis.get(0);
				if(o[1]!=null){
					h.put("HighReturnedYTD", o[1].toString());
				}
				else {
					h.put("HighReturnedYTD", "0");
				}
			}
			else {
				h.put("HighReturnedYTD", "0");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		//Low Value Items Entered Year to Date
		sql = "select f.receivedDate, case count(*) when null then 0 else count(*) end from lfitem i join lffound f on f.id=i.found_id "+
				"where i.value=0 and f.receivedDate < :enddate and f.receivedDate >= :lastweek";
		q = null;
		sess = HibernateWrapper.getSession().openSession();
		try {
			q = sess.createSQLQuery(sql.toString());
			
			q.setParameter("enddate", DateUtils.formatDate(endDate, TracingConstants.DB_DATEFORMAT, null, null));
			q.setParameter("lastweek", DateUtils.formatDate(week, TracingConstants.DB_DATEFORMAT, null, null));
			
			List lis = q.list();
			Object[] o;
			if(lis.size()!=0){
				o=(Object[]) lis.get(0);
				if(o[1]!=null){
					h.put("LowEnteredYTD", o[1].toString());
				}
				else {
					h.put("LowEnteredYTD", "0");
				}
			}
			else {
				h.put("LowEnteredYTD", "0");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		//High Value Items Entered year to Date
		sql = "select f.receivedDate, case count(*) when null then 0 else count(*) end from lfitem i join lffound f on f.id=i.found_id "+
				"where i.value=1 and f.receivedDate < :enddate and f.receivedDate >= :lastweek";
		q = null;
		sess = HibernateWrapper.getSession().openSession();
		try {
			q = sess.createSQLQuery(sql.toString());
			
			q.setParameter("enddate", DateUtils.formatDate(endDate, TracingConstants.DB_DATEFORMAT, null, null));
			q.setParameter("lastweek", DateUtils.formatDate(week, TracingConstants.DB_DATEFORMAT, null, null));
			
			List lis = q.list();
			Object[] o;
			if(lis.size()!=0){
				o=(Object[]) lis.get(0);
				if(o[1]!=null){
					h.put("HighEnteredYTD", o[1].toString());
				}
				else {
					h.put("HighEnteredYTD", "0");
				}
			}
			else {
				h.put("HighEnteredYTD", "0");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		//Boxes Entered year to Date
		sql = "select bc.dateCount, case sum(b.boxCount) when null then 0 else sum(b.boxCount) end from lfBoxCount b left join lfboxcontainer bc on bc.id =b.container_ID "+
				"where bc.dateCount < :enddate and bc.dateCount >= :lastweek";
		q = null;
		sess = HibernateWrapper.getSession().openSession();
		try {
			q = sess.createSQLQuery(sql.toString());
			
			q.setParameter("enddate", DateUtils.formatDate(endDate, TracingConstants.DB_DATEFORMAT, null, null));
			q.setParameter("lastweek", DateUtils.formatDate(week, TracingConstants.DB_DATEFORMAT, null, null));
			
			List lis = q.list();
			Object[] o;
			if(lis.size()!=0){
				o=(Object[]) lis.get(0);
				if(o[1]!=null){
					h.put("BoxesReceivedYTD", o[1].toString());
				}
				else {
					h.put("BoxesReceivedYTD", "0");
				}
			}
			else {
				h.put("BoxesReceivedYTD", "0");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		//Lost Reports Entered Year to Date
		sql = "select opendate, case count(*) when null then 0 else count(*) end from lflost "+
				"where openDate < :enddate and OpenDate >= :lastweek";
		q = null;
		sess = HibernateWrapper.getSession().openSession();
		try {
			q = sess.createSQLQuery(sql.toString());
			
			q.setParameter("enddate", DateUtils.formatDate(endDate, TracingConstants.DB_DATEFORMAT, null, null));
			q.setParameter("lastweek", DateUtils.formatDate(week, TracingConstants.DB_DATEFORMAT, null, null));
			
			List lis = q.list();
			Object[] o;
			if(lis.size()!=0){
				o=(Object[]) lis.get(0);
				if(o[1]!=null){
					h.put("LostYTDEntered", o[1].toString());
				}
				else {
					h.put("LostYTDEntered", "0");
				}
			}
			else {
				h.put("LostYTDEntered", "0");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
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
	
	public void getWeekItemCounts(HashMap h, Date endDate, Date fourweek)
	{
		int lowenterTotal=0;
		int highenterTotal=0;
		int lowShipTotal=0;
		int highShipTotal=0;
		int lowWOCTotal=0;
		int highWOCTotal=0;
		int LowReceived=0;
        DecimalFormat twoPlaces = new DecimalFormat("0.00");
		//Item and Status Counts (Last 4 weeks)
		String sql="select bc.dateCount, Round(10.56 * .9 * case sum(b.boxCount) when null then 0 else sum(b.boxCount) end) from lfBoxCount b left join lfboxcontainer bc on bc.id =b.container_ID "
				+"where bc.dateCount < :enddate and bc.dateCount >= :fourweek";
		SQLQuery q = null;
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			q = sess.createSQLQuery(sql.toString());
			
			q.setParameter("enddate", DateUtils.formatDate(endDate, TracingConstants.DB_DATEFORMAT, null, null));
			q.setParameter("fourweek", DateUtils.formatDate(fourweek, TracingConstants.DB_DATEFORMAT, null, null));
			
			List lis = q.list();
			Object[] o;
			o = (Object[]) lis.get(0);
			if(lis.size()!=0){
				if(o[1]!=null){
					h.put("LowReceived", o[1].toString());
					LowReceived=Integer.valueOf(o[1].toString());
				} else {
					h.put("lowReceived", "0");
				}
			}
			else
			{
				for(int j=1; j<8; j++){
					h.put("lowReceived", "0");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		sess = HibernateWrapper.getSession().openSession();
		sql = "select q1.val, q1.wk1, q1.ct1, q2.ct2, q3.ct3, (q2.ct2+q3.ct3)/q1.ct1*100 as 'Return' "+ 
			"from (select case  value when 0 then 'Low Value' else 'High Value' END as val, makedate(year(f.receivedDate), 7*week(f.receivedDate)-6) as wk1, "+
			"case count(*) when null then 0 else count(*) end as ct1 from lfitem i join lffound f on i.found_id = f.id where type = 2 and f.receivedDate < :enddate and f.receivedDate >= :fourweek group by value, week(f.receivedDate)) as q1 left outer join "+
			"(select case  value when 0 then 'Low Value' else 'High Value' END as val, makedate(year(f.receivedDate), 7*week(f.receivedDate)-6) as wk2, "+
			"case count(*) when null then 0 else count(*) end as ct2 from lfitem i join lffound f on i.found_id = f.id where type = 2 and f.receivedDate < :enddate and f.receivedDate >= :fourweek and ((trackingNumber is not null and trackingNumber != '') or i.deliveryRejected = 1) group by value, week(f.receivedDate, 3)) q2 "+
			"on q2.val = q1.val and q1.wk1 = q2.wk2 left outer join "+
			"(select case  value when 0 then 'Low Value' else 'High Value' END as val, makedate(year(f.receivedDate), 7*week(f.receivedDate)-6) as wk3, "+
			"case count(*) when null then 0 else count(*) end as ct3  from lfitem i join lffound f on i.found_id = f.id where type = 2 and f.receivedDate < :enddate and f.receivedDate >= :fourweek and (trackingNumber is null or trackingNumber = '') and lost_id is not null group by value, week(f.receivedDate, 3)) q3 "+
			"on q3.val = q1.val and q3.wk3 = q1.wk1 group by val, q1.wk1 order by q1.wk1 asc, q1.val desc";
//		sql = "select case  value when 0 then 'Low Value' else 'High Value' END as val, makedate(year(f.receivedDate), 7*week(f.receivedDate)-6) as wk3, "+
//			"case count(*) when null then 0 else count(*) end as ct2  from lfitem i join lffound f on i.found_id = f.id where type = 2 and f.receivedDate < :enddate and f.receivedDate >= :fourweek and (trackingNumber is null or trackingNumber = '') and lost_id is not null group by value, week(f.receivedDate, 3)";
	    q = null;
		try {
			q = sess.createSQLQuery(sql.toString());
			
			q.setParameter("enddate", DateUtils.formatDate(endDate, TracingConstants.DB_DATEFORMAT, null, null));
			q.setParameter("fourweek", DateUtils.formatDate(fourweek, TracingConstants.DB_DATEFORMAT, null, null));
			
			List lis = q.list();
			Object[] o;
			for (int i = 1; i <6; ++i) {
				h.put("lowEntered"+(i),"0");
				h.put("lowShipped"+(i), "0");
				h.put("lowWOC"+(i), "0");
				h.put("highEntered"+(i), "0");
				h.put("highShipped"+(i), "0");
				h.put("highWOC"+(i), "0");
			}
			
			int rowNum=0;
			for (int i = 0; i < lis.size(); ++i) {
				o = (Object[]) lis.get(i);
				if(i%2==0 )
				{
					rowNum++;
				}
				if(o[0].toString().equals("Low Value")){
					if(o[2]!=null){
						h.put("lowEntered"+(rowNum), o[2].toString());
						lowenterTotal+=Integer.valueOf(o[2].toString());
					} else {
						h.put("lowEntered"+(rowNum), "0");
					}
					if(o[3]!=null){
						h.put("lowShipped"+(rowNum), o[3].toString());
						lowShipTotal+=Integer.valueOf(o[3].toString());
					} else {
						h.put("lowShipped"+(rowNum), "0");
					}
					if(o[4]!=null){
						h.put("lowWOC"+(rowNum), o[4].toString());
						lowWOCTotal+=Integer.valueOf(o[4].toString());
					} else {
						h.put("lowWOC"+(rowNum), "0");
					}
					
				}
				else if (o[0].toString().equals("High Value"))
				{
					if(o[2]!=null){
						h.put("highEntered"+(rowNum), o[2].toString());
						highenterTotal+=Integer.valueOf(o[2].toString());
					} else {
						h.put("highEntered"+(rowNum), "0");
					}
					if(o[3]!=null){
						h.put("highShipped"+(rowNum), o[3].toString());
						highShipTotal+=Integer.valueOf(o[3].toString());
					} else {
						h.put("highShipped"+(rowNum), "0");
					}

					if(o[4]!=null){
						h.put("highWOC"+(rowNum), o[4].toString());
						highWOCTotal+=Integer.valueOf(o[4].toString());
					} else {
						h.put("highWOC"+(rowNum), "0");
					}
					
				}
			}
			h.put("LowEnterTotal", String.valueOf(lowenterTotal));
			h.put("LowShipTotal", String.valueOf(lowShipTotal));
			h.put("highEnterTotal", String.valueOf(highenterTotal));
			h.put("highShipTotal", String.valueOf(highShipTotal));
			h.put("LowWocTotal", String.valueOf(lowWOCTotal));
			h.put("highWocTotal", String.valueOf(highWOCTotal));
			h.put("TotalWoc", String.valueOf(highWOCTotal+lowWOCTotal));
			
			h.put("TotalShipped", String.valueOf(highShipTotal+lowShipTotal));
			h.put("TotalReceived", String.valueOf(LowReceived+highenterTotal));


			h.put("HighReceivedPercent", twoPlaces.format((Double.valueOf(highenterTotal)/Double.valueOf(LowReceived+highenterTotal))*100)+"%");
			h.put("LowReceivedPercent", twoPlaces.format(Double.valueOf(LowReceived)/(Double.valueOf(LowReceived+highenterTotal))*100)+"%");
			h.put("TotalReturnedPercent", twoPlaces.format(Double.valueOf(highShipTotal+lowShipTotal+highWOCTotal+lowWOCTotal)/(Double.valueOf(LowReceived+highenterTotal))*100)+"%");
			
			h.put("LowTotal", String.valueOf(lowWOCTotal+lowShipTotal));
			h.put("HighTotal",String.valueOf( highShipTotal+highWOCTotal));
			h.put("TotalTotal", String.valueOf(highShipTotal+lowShipTotal+highWOCTotal+lowWOCTotal));

			h.put("LowReturnedPercent", twoPlaces.format((Double.valueOf(lowShipTotal+lowWOCTotal)/Double.valueOf(LowReceived))*100)+"%");
			h.put("HighReturnedPercent", twoPlaces.format((Double.valueOf(highShipTotal+highWOCTotal)/Double.valueOf(highenterTotal))*100)+"%");
			h.put("TotalReceivedPercent", twoPlaces.format((Double.valueOf(LowReceived)/(Double.valueOf(LowReceived+highenterTotal))*100)+(Double.valueOf(highenterTotal)/Double.valueOf(LowReceived+highenterTotal))*100)+"%");
			
			h.put("HighVariance", twoPlaces.format( ((((Double.valueOf(highShipTotal+highWOCTotal)/Double.valueOf(highenterTotal))*100)-9.97)/9.97)*100) +"%");
			h.put("LowVariance", twoPlaces.format( ((((Double.valueOf(lowShipTotal+lowWOCTotal)/Double.valueOf(LowReceived))*100)-1.19)/1.19)*100)+"%");
			h.put("TotalVariance", twoPlaces.format( (((Double.valueOf(highShipTotal+lowShipTotal+highWOCTotal+lowWOCTotal)/(Double.valueOf(LowReceived+highenterTotal))*100)-1.90)/1.90)*100)+"%");
		} catch (Exception e) {
			e.printStackTrace();
			return;
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
	
	public void sendLostCreatedEmail(long id){
		LFLost lost = getLostReport(id);
		HashMap<String,String> h = getEmailParams(lost);
		
		if (TracingConstants.LF_SWA_COMPANY_ID.equalsIgnoreCase(lost.getCompanyId())){
			//use no reply email
			h.put("RETURNADDRESS", PropertyBMO.getValue(PropertyBMO.LF_EMAIL_RETURNADDR_INIT));
		}
		
		if(sendEmail(lost, h, "create_report_email.html", h.get("SUBJECTLINE"))){
			lost.setEmailSentDate(new Date());
			try {
				saveOrUpdateLostReport(lost,getAutoAgent());
				Logger.logLF(""+id, "LOST CREATED EMAIL SENT", 0);
			} catch (UpdateException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void sendLFWeekly(){
//		Agent a=GeneralServiceBean.getAgent(id);
		Agent a = getAutoAgent();
		HashMap<String,String> h = getEmailParams();
		if(sendEmail(a, h, "weekly_email.html",  h.get("SUBJECTLINE"))){
			Logger.logLF(""+a.getAgent_ID(), "WEEKLY REPORT EMAIL SENT", 0);
		}
	}
	
	public void sendStillSearching(long id){
		LFLost lost = getLostReport(id);
		HashMap<String,String> h = getEmailParams(lost);
		if(sendEmail(lost, h, "update_report_email.html",  h.get("SUBJECTLINE"))){
			lost.setEmailSentDate(new Date());
			try {
				saveOrUpdateLostReport(lost,getAutoAgent());
				Logger.logLF(""+id, "STILL SEARCHING EMAIL SENT", 0);
			} catch (UpdateException e) {
				e.printStackTrace();
			}
		}
	}
	public void send1stNotice(long id){
		LFLost lost = getLostReport(id);
		HashMap<String,String> h = getEmailParams(lost);
		
		if (TracingConstants.LF_SWA_COMPANY_ID.equalsIgnoreCase(lost.getCompanyId())){
			//use no reply email
			h.put("RETURNADDRESS", PropertyBMO.getValue(PropertyBMO.LF_EMAIL_RETURNADDR_FIRST));
		}
		
		if(!lost.isEmail1() && sendEmail(lost, h, "update_1_report_email.html", h.get("SUBJECTLINE"))){
			lost.setEmailSentDate(new Date());
			lost.setEmail1(true);
			try {
				saveOrUpdateLostReport(lost,getAutoAgent());
				Logger.logLF(""+id, "1ST NOTICE EMAIL SENT", 0);
			} catch (UpdateException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void send2ndNotice(long id){
		LFLost lost = getLostReport(id);
		HashMap<String,String> h = getEmailParams(lost);
		
		if (TracingConstants.LF_SWA_COMPANY_ID.equalsIgnoreCase(lost.getCompanyId())){
			//use no reply email
			h.put("RETURNADDRESS", PropertyBMO.getValue(PropertyBMO.LF_EMAIL_RETURNADDR_SECOND));
		}
		
		String email = "update_2_report_email.html";
		if (TracingConstants.LF_SWA_COMPANY_ID.equalsIgnoreCase(lost.getCompanyId()) && dataplan(lost)){
			email = "update_2_ipad_report_email.html";
		}
		if(!lost.isEmail2() && sendEmail(lost, h, email, h.get("SUBJECTLINE"))){
			lost.setEmailSentDate(new Date());
			lost.setEmail2(true);
			try {
				saveOrUpdateLostReport(lost,getAutoAgent());
				Logger.logLF(""+id, "2ND NOTICE EMAIL SENT", 0);
			} catch (UpdateException e) {
				e.printStackTrace();
			}
		}
	}
	
//	public void sendFoundEmail(long id){
//		LFLost lost = getLostReport(id);
//		HashMap<String,String> h = getEmailParams(lost);
//		if(sendEmail(lost, h, "found_report_email.html", h.get("SUBJECTLINE"))){
//			lost.setEmailSentDate(new Date());
//			try {
//				saveOrUpdateLostReport(lost,getAutoAgent());
//				Logger.logLF(""+id, "FOUND EMAIL SENT", 0);
//			} catch (UpdateException e) {
//				e.printStackTrace();
//			}
//		}
//	}
	
	public boolean sendFoundEmail(long id){
		LFLost lost = getLostReport(id);
		HashMap<String,String> h = getEmailParams(lost);
		if(!lost.isFoundEmail()){
			if(sendEmail(lost, h, "found_report_email.html", h.get("SUBJECTLINE"))){
				lost.setEmailSentDate(new Date());
				lost.setFoundEmail(true);
				try {
					saveOrUpdateLostReport(lost,getAutoAgent());
					Logger.logLF(""+id, "FOUND EMAIL SENT", 0);
				} catch (UpdateException e) {
					e.printStackTrace();
				}
				return true;
			}
		}
		return false;
	}
	
	public void closeLostAndEmail(long id, Agent agent){
		LFLost lost = getLostReport(id);
		HashMap<String,String> h = getEmailParams(lost);

		if (TracingConstants.LF_SWA_COMPANY_ID.equalsIgnoreCase(lost.getCompanyId())){
			//use no reply email
			h.put("RETURNADDRESS", PropertyBMO.getValue(PropertyBMO.LF_EMAIL_RETURNADDR_CLOSE));
		}
		
		String email = "closed_report_email.html";
		if (TracingConstants.LF_SWA_COMPANY_ID.equalsIgnoreCase(lost.getCompanyId()) && dataplan(lost)){
			email = "closed_report_ipad_email.html";
		}
		
		if(sendEmail(lost, h, email, h.get("SUBJECTLINE"))){
			//regardless of the send status of the email, close the report
		}
		lost.setCloseDate(new Date());
		Status status = new Status();
		status.setStatus_ID(TracingConstants.LF_STATUS_CLOSED);
		lost.setStatus(status);
		try {
			saveOrUpdateLostReport(lost, agent);
			Logger.logLF(""+id, "CLOSED EMAIL SENT", 0);
		} catch (UpdateException e) {
			e.printStackTrace();
		}
	}
	
	private boolean dataplan(LFLost lost){
		if(lost != null && lost.getItem() != null){
			if(lost.getItem().getCategory() == 7 ||      //cellphone
			   lost.getItem().getSubCategory() == 34 ||  //laptop
			   lost.getItem().getSubCategory() == 48){   //PDA
				return true;
			}
		}
		return false;
	}
	
	public boolean sendEmail(LFLost lost, HashMap params, String htmlFileName, String subjectline){
		if(lost != null && lost.getClient() != null && lost.getClient().getDecryptedEmail() != null && !lost.getClient().getDecryptedEmail().isEmpty()){
			try {
				String root = TracerProperties.get("email.resources");
				String imgbanner = TracerProperties.get("lf.email.banner");
				String configpath = root + "/";
				String imagepath = root + "/";
				if("AB".equalsIgnoreCase(TracingConstants.LF_SUBCOMPANIES.get(lost.getCompanyId()))){
					configpath = root + "/";
					imagepath = root + "/";
				} else if (TracingConstants.LF_SWA_COMPANY_ID.equalsIgnoreCase(lost.getCompanyId())){
					configpath = root + "/WN/";
					imagepath = root + "/WN/";
				} else {
					configpath = root + "/DM/";
					imagepath = root + "/DM/";
				}
				boolean embedImage = true;
				
//				System.out.println("path: " + root);
				
				HtmlEmail he = new HtmlEmail();
				String currentLocale = lost.getAgent().getCurrentlocale();
				
				String from = (String) params.get("RETURNADDRESS");
				if(from == null || from.trim().length() == 0){
					from = getAutoAgent().getStation().getCompany().getVariable().getEmail_from();
				}
				
				String host = getAutoAgent().getStation().getCompany().getVariable().getEmail_host();
				int port = getAutoAgent().getStation().getCompany().getVariable().getEmail_port();
				
				he.setHostName(host);
				he.setSmtpPort(port);

				he.setFrom(from);
				ArrayList al = new ArrayList();
				al.add(new InternetAddress(lost.getClient().getDecryptedEmail()));
				he.setTo(al);
				
				if(subjectline != null){
					subjectline = subjectline.replace("{LOSTID}", "" + lost.getId());
				}
				he.setSubject(subjectline);

				// set embedded images
				if (embedImage) {
					try{
					String img1 = he.embed(new URL("file:" + imagepath + imgbanner),
							imgbanner);
					params.put("BANNER_IMAGE", img1);
					}catch(Exception e){
//						e.printStackTrace();
						System.out.println("Unable to find email banner: " + imagepath + imgbanner);
					}
				}
				
				String msg = EmailParser.parse(configpath + htmlFileName, params, currentLocale);
				if(msg == null){
					System.out.println("email is null, check email path: " + configpath + htmlFileName);
				}
				he.setHtmlMsg(msg);
				he.send();
				
			}catch (Exception e){
				e.printStackTrace();
				return false;
			}
			return true;
		}
		return false;
	}
	
	public boolean sendEmail(Agent a, HashMap params, String htmlFileName, String subjectline){
		//if(lost != null && lost.getClient() != null && lost.getClient().getDecryptedEmail() != null && !lost.getClient().getDecryptedEmail().isEmpty()){
			try {
				String root = TracerProperties.get("email.resources");
				String imgbanner = TracerProperties.get("lf.email.banner");
				String configpath = root + "/";
				String imagepath = root + "/";
				configpath = root + "/WN/";
				imagepath = root + "/WN/";
				boolean embedImage = true;
				
//				System.out.println("path: " + root);
				
				HtmlEmail he = new HtmlEmail();
				String currentLocale = a.getCurrentlocale();
				
				String from = (String) params.get("RETURNADDRESS");
				if(from == null || from.trim().length() == 0){
					from = getAutoAgent().getStation().getCompany().getVariable().getEmail_from();
				}
				
				String host = getAutoAgent().getStation().getCompany().getVariable().getEmail_host();
				int port = getAutoAgent().getStation().getCompany().getVariable().getEmail_port();
				
				he.setHostName(host);
				he.setSmtpPort(port);

				he.setFrom(from);
				ArrayList al = new ArrayList();
				al.add(new InternetAddress("lfcmetrics@nettracer.aero")); 
				he.setTo(al);
				
				String msg = EmailParser.parse(configpath + htmlFileName, params, currentLocale);
				if(subjectline != null){
					subjectline = subjectline.replace("{STARTDATE}", "" + params.get("STARTDATE"));
					subjectline = subjectline.replace("{ENDDATE}", "" + params.get("ENDDATE"));
				}
				he.setSubject(subjectline);

				if(msg == null){
					System.out.println("email is null, check email path: " + configpath + htmlFileName);
				}
				he.setHtmlMsg(msg);
				he.send();
				
			}catch (Exception e){
				e.printStackTrace();
				return false;
			}
			return true;
		//}
		//return false;
	}
	
	public void testEmail(){
		try{
			HtmlEmail he = new HtmlEmail();
			
			String from = "noreply@lostandfound.aero";
			String host = "10.8.185.132";
			int port = 8625;
			
			he.setHostName(host);
			he.setSmtpPort(port);

			he.setFrom(from);
			ArrayList al = new ArrayList();
			al.add(new InternetAddress("mloupas@nettracer.aero"));
			al.add(new InternetAddress("noreply@lostandfound.aero"));
			he.setTo(al);
			
			he.setSubject("Avis Budget Group - Test Email");
			
			he.setHtmlMsg("Test");
			he.send();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void traceAllFoundItems(){
		LFTracingUtil.traceAllFoundItems(true);
	}


	@Override
	public int getShelvedTraceResultsCount(Station station, int value) {
		if(station == null){
			return 0;
		}
		String query = "select count(x.match_id) from ("
					 + "select distinct mh.id as match_id from lfmatchhistory mh "
					 + "left outer join lffound lf on mh.found_id = lf.id "
					 + "join lfitem i on lf.id = i.found_id and i.type = " + TracingConstants.LF_TYPE_FOUND + " "
					 + "where mh.status_Status_ID = " + TracingConstants.LF_TRACING_OPEN + " " 
					 + "and lf.status_ID = " + TracingConstants.LF_STATUS_OPEN + " "
					 + "and lf.itemLocation = " + TracingConstants.LF_LOCATION_SHELF + " ";
		if (value > -1) {
			query += "and i.value = " + value + " ";
		}
		
		query += "group by lf.id order by mh.id asc) x ";
		
		Session sess = null;
		try{
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess.createSQLQuery(query);
			List result = q.list();
			sess.close();
			return ((BigInteger) result.get(0)).intValue();
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
	public List<LFFound> getShelvedTraceResultsPaginated(Station station, int value, int start, int offset) {
		if(station == null){
			return null;
		}
		
		String sql = "from com.bagnet.nettracer.tracing.db.lf.detection.LFMatchHistory m "
				   + "where m.status = " + TracingConstants.LF_TRACING_OPEN + " and m.found.status = " + TracingConstants.LF_STATUS_OPEN + " "
				   + "and m.found.item.value = " + value + " "
				   + "and m.found.itemLocation = " + TracingConstants.LF_LOCATION_SHELF + " "
				   + "and not exists (from com.bagnet.nettracer.tracing.db.Lock l where l.lockKey = m.found.barcode) "
				   + "group by m.found.id order by m.id asc";
		Session sess = null;
		try{
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess.createQuery(sql);
			
			if (start > -1 && offset > -1 ) {
				q.setFirstResult(start);
				q.setMaxResults(offset);
			} else {
				throw new Exception("Invalid pagination bounds");
			}
			
			List result = q.list();
			
			ArrayList<LFFound> toReturn = new ArrayList<LFFound>();
			for (int i = 0; i < result.size(); ++i) {
				toReturn.add(((LFMatchHistory) result.get(i)).getFound());
			}
			return toReturn;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(sess != null && sess.isOpen()){
				sess.close();
			}
		}
		return null;
	}
	
	public LFBoxContainer loadBoxContainer(Date date){
		if(date == null){
			return null;
		}
		
		Session sess = HibernateWrapper.getSession().openSession();
		LFBoxContainer s = null;
		List<LFBoxContainer> list = null;
		try{
			Criteria crit = sess.createCriteria(LFBoxContainer.class).add(Restrictions.eq("dateCount", date));
			list = crit.list();
		}catch (HibernateException e){
			e.printStackTrace();
		}
		finally{
			sess.close();
		}
		if(list != null && list.size() > 0){
			s = list.get(0);
		}
		return s;
	}
	
	public LFBoxCount loadBoxCount(Station sta, Date date){
		if(date == null || sta == null){
			return null;
		}
		
		Session sess = HibernateWrapper.getSession().openSession();
		LFBoxCount s = null;
		List<LFBoxCount> list = null;
		try{
			Criteria crit = sess.createCriteria(LFBoxCount.class).add(Restrictions.eq("source", sta))
					.createCriteria("container").add(Restrictions.eq("dateCount", date));
			//crit = sess.createCriteria(LFBoxCount.class).add(Restrictions.eq("source", sta));
			list = crit.list();
		}catch (HibernateException e){
			e.printStackTrace();
		}
		finally{
			sess.close();
		}
		if(list != null && list.size() > 0){
			s = list.get(0);
		}
		return s;
	}
	
	public LFSalvage loadSalvage(long id) {
		String sql = "select s.id, s.createdDate, s.closedDate, s.status_ID, s.agent_ID, s.station_id from LFSalvage s" +
				" where id=:sid ";
		SQLQuery q = null;
		
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			q = sess.createSQLQuery(sql.toString());
			q.setParameter("sid", id);
			q.addScalar("s.id",Hibernate.LONG);
			q.addScalar("s.createdDate",Hibernate.DATE);
			q.addScalar("s.closedDate",Hibernate.DATE);
			q.addScalar("s.status_id",Hibernate.INTEGER);
			q.addScalar("s.agent_ID",Hibernate.INTEGER);
			q.addScalar("s.station_id",Hibernate.INTEGER);
			List<Object[]> lis = q.list();
			LFSalvage salv=new LFSalvage();
			Object[] o=lis.get(0);
			salv.setId(Long.valueOf(o[0].toString()));
			salv.setCreatedDate((Date)o[1]);
			if(o[2]!=null){
				salv.setClosedDate((Date)o[2]);
			}
			salv.setStatus(new Status());
			salv.setStatusId(Integer.valueOf(o[3].toString()));
			int aid=Integer.valueOf(o[4].toString());
			Agent a=GeneralServiceBean.getAgent(aid);
			salv.setAgent(a);
			int stid=Integer.valueOf(o[5].toString());
			Station sta=StationBMO.getStation(stid);
			salv.setLocation(sta);
			return salv;
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
	
	public List<LFSalvageFound> loadSalvageFound(long id) {
		String sql = "select f.id, f.barcode, f.receivedDate, i.brand, i.model, i.serialNumber, i.color, i.description, i.longDescription, a.username, a.currentTimeZone, i.category, i.subcategory from LFFound f" +
				" left join Agent a on f.agent_ID=a.agent_ID left join LFItem i on f.item_id=i.id where salvage_id=:sid ";
		SQLQuery q = null;
		
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			q = sess.createSQLQuery(sql.toString());
			q.setParameter("sid", id);
			List lis = q.list();
			List<LFSalvageFound> results=new ArrayList();
			Object[] o;
			LFSalvageFound dto;
			for (int i = 0; i < lis.size(); ++i) {
				o = (Object[]) lis.get(i);
				dto=new LFSalvageFound();
				dto.setFoundID(o[0].toString());
				dto.setFoundBarcode(o[1].toString());
				dto.setReceivedDate((Date)o[2]);
				dto.setBrand(o[3].toString());
				dto.setModel(o[4].toString());
				dto.setSerialNumber(o[5].toString());
				dto.setColor(o[6].toString());
				dto.setDescription(o[7].toString());
				if(o[8]!=null){
					dto.setLongDescription(o[8].toString());
				}
				dto.setUsername(o[9].toString());
				dto.setCurrentTimeZone(o[10].toString());
				dto.setCategory(Long.valueOf(o[11].toString()));
				dto.setSubcategory(Long.valueOf(o[12].toString()));
				results.add(dto);
			}
			
			return results;
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
	
	public boolean saveSalvage(LFSalvage salvage) {
		boolean success = false;
		if (salvage == null) {
			return success;
		}
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = HibernateWrapper.getSession().openSession();
			transaction = session.beginTransaction();
			if(salvage.getId() > 0) {
				session.merge(salvage);
			} else {
				session.save(salvage);
			}
			transaction.commit();
			success = true;
		} catch (Exception e) {
			System.err.println(e);
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return success;
	}
	
	public boolean saveContainer(LFBoxContainer container) {
		boolean success = false;
		if (container == null) {
			return success;
		}
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = HibernateWrapper.getSession().openSession();
			transaction = session.beginTransaction();
			if(container.getId() > 0) {
				session.merge(container);
			} else {
				session.save(container);
			}
			transaction.commit();
			success = true;
		} catch (Exception e) {
			System.err.println(e);
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return success;
	}
	
	public boolean saveCount(LFBoxCount count) {
		boolean success = false;
		if (count == null) {
			return success;
		}
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = HibernateWrapper.getSession().openSession();
			transaction = session.beginTransaction();
			if(count.getId() > 0) {
				session.merge(count);
			} else {
				session.save(count);
			}
			transaction.commit();
			success = true;
		} catch (Exception e) {
			System.err.println(e);
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return success;
	}
	
	@Override
	public int getSalvageCount(Station station, SalvageSearchForm ssForm) {
		if(station == null){
			return 0;
		}
		
		String sql = "select count(s.id) " + getSalvageQueryFromForm(station, ssForm);
		
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
	@SuppressWarnings({ "rawtypes" })
	public List<LFSalvage> getSalvagesPaginated(Station station, SalvageSearchForm ssForm, int start, int offset) {
		if(station == null){
			return null;
		}
		
		String sql = getSalvageQueryFromForm(station, ssForm);
		Session sess = null;
		try{
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess.createQuery(sql);
			
			if (start > -1 && offset > -1 ) {
				q.setFirstResult(start);
				q.setMaxResults(offset);
			} else {
				throw new Exception("Invalid pagination bounds");
			}
			
			List result = q.list();
			return result;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(sess != null && sess.isOpen()){
				sess.close();
			}
		}
		return null;
	}
	
	private String getSalvageQueryFromForm(Station station, SalvageSearchForm ssForm) {
		String sql = "from com.bagnet.nettracer.tracing.db.lf.LFSalvage s " +
		"where s.location.station_ID = " + station.getStation_ID() + " ";
		
		if (ssForm.getS_createtime() != null && !ssForm.getS_createtime().isEmpty()) {
			Calendar calendarStart = new GregorianCalendar();
			calendarStart.setTime(DateUtils.convertToDate(ssForm.getS_createtime(), ssForm.getDateFormat(), null));
			
			String endDateString = ssForm.getE_createtime();
			if (endDateString == null || endDateString.isEmpty()) {
				endDateString = ssForm.getS_createtime();
			}
			
			Calendar calendarEnd = new GregorianCalendar();
			calendarEnd.setTime(DateUtils.convertToDate(endDateString, ssForm.getDateFormat(), null));
			calendarEnd.add(Calendar.DAY_OF_MONTH, 1);
			
			String startDate = DateUtils.formatDate(calendarStart.getTime(), TracingConstants.DB_DATEFORMAT, null, null);
			String endDate = DateUtils.formatDate(calendarEnd.getTime(), TracingConstants.DB_DATEFORMAT, null, null);
			
			sql += "and s.createdDate between \'" + startDate + "\' and \'" + endDate + "\' ";
		}
		
		if (ssForm.getSalvageId() != 0) {
			sql += "and s.id = " + ssForm.getSalvageId() + " ";
		}
		
		if (ssForm.getSalvageStatus() > 0) {
			sql += "and s.status.status_ID = " + ssForm.getSalvageStatus() + " ";
		} 
		
		sql += "order by s.createdDate asc";
		
		return sql;
	}
	
	public List<SalvageDTO> getSalvageSearch(Station station, SalvageSearchForm ssForm, int rowsperpage, int currpage) {
		String sql = "select s.id, s.createdDate, s.closedDate, a.username, a.currentTimeZone, s.status_ID, count(f.id) from LFSalvage s left join LFFound f on s.id = f.salvage_id " +
		" left join Agent a on s.agent_ID=a.agent_ID where s.station_ID = :station ";
		
		if (ssForm.getS_createtime() != null && !ssForm.getS_createtime().isEmpty()) {
			sql += "and s.createdDate between :startDate and :endDate ";
		}
		
		if (ssForm.getSalvageId() != 0) {
			sql += "and s.id = :sid ";
		}
		
		if (ssForm.getSalvageStatus() > 0) {
			sql += "and s.status_ID = :sstatus ";
		} 
		
		sql += " group by s.id order by s.createdDate asc";
		
		SQLQuery q = null;
		
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			q = sess.createSQLQuery(sql.toString());
			
			q.setParameter("station", station.getStation_ID());
			
			if (ssForm.getSalvageStatus() > 0) {
				q.setInteger("sstatus", ssForm.getSalvageStatus());
			}
			
			if (ssForm.getSalvageId() != 0) {
				q.setParameter("sid", ssForm.getSalvageId());
			}			
			
			if (ssForm.getS_createtime() != null && !ssForm.getS_createtime().isEmpty()) {
				
				Calendar calendarStart = new GregorianCalendar();
				calendarStart.setTime(DateUtils.convertToDate(ssForm.getS_createtime(), ssForm.getDateFormat(), null));
				
				String endDateString = ssForm.getE_createtime();
				if (endDateString == null || endDateString.isEmpty()) {
					endDateString = ssForm.getS_createtime();
				}
				
				Calendar calendarEnd = new GregorianCalendar();
				calendarEnd.setTime(DateUtils.convertToDate(endDateString, ssForm.getDateFormat(), null));
				calendarEnd.add(Calendar.DAY_OF_MONTH, 1);
				
				String startDate = DateUtils.formatDate(calendarStart.getTime(), TracingConstants.DB_DATEFORMAT, null, null);
				String endDate = DateUtils.formatDate(calendarEnd.getTime(), TracingConstants.DB_DATEFORMAT, null, null);
				
				q.setParameter("startDate", startDate);
				q.setParameter("endDate", endDate);
			}
			
			if (rowsperpage > 0) {
				int startnum = currpage * rowsperpage;
				q.setFirstResult(startnum);
				q.setMaxResults(rowsperpage);
			}
			List lis = q.list();
			List<SalvageDTO> results=new ArrayList();
			Object[] o;
			SalvageDTO dto;
			for (int i = 0; i < lis.size(); ++i) {
				o = (Object[]) lis.get(i);
				dto=new SalvageDTO();
				dto.setSalvageID(Long.valueOf(o[0].toString()));
				dto.setCreatedDate((Date)o[1]);
				dto.setClosedDate((Date)o[2]);
				dto.setAgent(o[3].toString());
				dto.setTimeZone(o[4].toString());
				dto.setStatus(Integer.valueOf(o[5].toString()));
				dto.setItemCount(Integer.valueOf(o[6].toString()));
				results.add(dto);
			}
			
			return results;
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
	}
}
