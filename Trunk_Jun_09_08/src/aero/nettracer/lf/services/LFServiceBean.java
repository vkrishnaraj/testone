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
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.BeanUtils;

import aero.nettracer.general.services.GeneralServiceBean;
import aero.nettracer.lf.services.exception.NonUniqueBarcodeException;
import aero.nettracer.lf.services.exception.UpdateException;
import aero.nettracer.lfc.model.ShippingBean;
import aero.nettracer.security.AES;

import com.bagnet.nettracer.email.HtmlEmail;
import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.dao.lf.SubCompanyDAO;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.lf.LFAddress;
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
import com.bagnet.nettracer.tracing.db.lf.LFShipping;
import com.bagnet.nettracer.tracing.db.lf.LFSubCategory;
import com.bagnet.nettracer.tracing.db.lf.LFTransaction;
import com.bagnet.nettracer.tracing.db.lf.Subcompany;
import com.bagnet.nettracer.tracing.db.lf.SubcompanyStation;
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
	
	private static final String CALCULATE_RETURNED_ITEMS_QUERY = 
			"SELECT dt, sum(val) FROM (SELECT date(f.deliveredDate) AS dt, CASE count(*) WHEN NULL THEN 0 ELSE count(*) END AS val " +
			"FROM lfitem i JOIN lffound f ON f.id = i.found_id LEFT OUTER JOIN lfmatchhistory h ON h.found_id = f.id AND h.status_Status_ID = 608 " +
			"LEFT OUTER JOIN lflost l ON l.id = h.lost_id LEFT OUTER JOIN lfitem i2 ON i2.lost_id = l.id AND i2.type = 1 WHERE i.value = :value " +
			"AND f.deliveredDate < :enddate AND f.deliveredDate >= :lastweek AND ( ( i.trackingNumber IS NOT NULL AND i.trackingNumber != '') " +
			"OR i.deliveryRejected = 1 OR ( i2.trackingNumber IS NOT NULL AND i2.trackingNumber != '') OR i2.deliveryRejected = 1) " +
			"GROUP BY date(f.deliveredDate) UNION SELECT date(l.closeDate) AS dt, CASE count(*) WHEN NULL THEN 0 ELSE count(*) END AS val " +
			"FROM lfitem i JOIN lffound f ON f.id = i.found_id LEFT OUTER JOIN lfmatchhistory h ON h.found_id = f.id AND h.status_Status_ID = 608 " +
			"LEFT OUTER JOIN lflost l ON l.id = h.lost_id LEFT OUTER JOIN lfitem i2 ON i2.lost_id = l.id AND i2.type = 1 WHERE i.value = :value " +
			"AND l.closeDate < :enddate AND l.closeDate >= :lastweek AND ( ( i.trackingNumber IS NULL OR i.trackingNumber = '') AND " +
			"i.deliveryRejected != 1 AND (( i2.trackingNumber IS NOT NULL AND i2.trackingNumber != '') OR i2.deliveryRejected = 1)) " +
			"GROUP BY date(l.closeDate)) tb1"; 
	private static final String CALCULATE_RETURNED_ITEMS_GROUP_BY = " GROUP BY dt ";
	private static String autoagent = "autoagent";
	
	private static Agent auto;
	private static ResourceBundle resources = null;
	private static int HIGH_VALUE = 1;
	private static int LOW_VALUE = 0;
	
	// Try/catch block inserted only to allow email to be sent from jUnit test.  
	static {
			try {
				resources = ResourceBundle.getBundle("com.bagnet.nettracer.tracing.resources.ApplicationResources", new Locale("US"));
			} catch (Exception e) {
			}	
		}

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
	
	@SuppressWarnings("unchecked")
	@Override
	public LFShipping getShipment(long id) {
		Session sess = HibernateWrapper.getSession().openSession();
		List<LFShipping> f = null;
		LFShipping shipment=null;
		try{
			String sql;
			sql="from com.bagnet.nettracer.tracing.db.lf.LFShipping s where s.lost.id= ";
			Query q = sess.createQuery(sql + id);
			f = (List<LFShipping>)  q.list();
			if(f!=null){
				shipment=f.get(0);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		finally{
			sess.close();
		}
		return shipment;
		
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
		boolean haveSerial = dto.getSerialNumber() != null && !dto.getSerialNumber().isEmpty();
		boolean haveTracking = dto.getTrackingNumber() != null && !dto.getTrackingNumber().isEmpty();
		int value = dto.getValue();
		
		if(TracingConstants.LF_LF_COMPANY_ID.equals(dto.getAgent().getCompanycode_ID()) && dto.getType() == TracingConstants.LF_TYPE_LOST && dto.getStationId() != -1)
		{
			sql += "left outer join o.segments seg";
		}
		
		if (category > 0 || subCategory > 0 || haveBrand || haveDesc || haveSerial || haveTracking || value > -1) {
			if(dto.getType() == TracingConstants.LF_TYPE_LOST){
				sql += " left outer join o.items i";
			} else {
				sql += " left outer join o.item i";
			}
		}
		

		String phone = null;

		String intnum = null;

		String toEncrypt="";
		if(dto.getIntNumber() != null && dto.getIntNumber().trim().length() > 0){
			try{
				intnum = AES.encrypt(LFPhone.normalizePhone(dto.getIntNumber()));
				toEncrypt+=dto.getIntNumber();
			} catch (Exception e){
				e.printStackTrace();
			}
		} 
		String areanum = null;
		if(dto.getAreaNumber() != null && dto.getAreaNumber().trim().length() > 0){
			try{
				areanum = AES.encrypt(LFPhone.normalizePhone(dto.getAreaNumber()));
				toEncrypt+=dto.getAreaNumber();
			} catch (Exception e){
				e.printStackTrace();
			}
		} 
		String exchangenum = null;
		if(dto.getExchangeNumber() != null && dto.getExchangeNumber().trim().length() > 0){
			try{
				exchangenum = AES.encrypt(LFPhone.normalizePhone(dto.getExchangeNumber()));
				toEncrypt+=dto.getExchangeNumber();
			} catch (Exception e){
				e.printStackTrace();
			}
		} 
		String linenum = null;
		if(dto.getLineNumber() != null && dto.getLineNumber().trim().length() > 0){
			try{
				linenum = AES.encrypt(LFPhone.normalizePhone(dto.getLineNumber()));
				toEncrypt+=dto.getLineNumber();
			} catch (Exception e){
				e.printStackTrace();
			}
		} 
		String extension = null;
		if(dto.getExtension() != null && dto.getExtension().trim().length() > 0){
			try{
				extension = dto.getExtension();
			} catch (Exception e){
				e.printStackTrace();
			}
		} 
		try{
			if(toEncrypt!=null && toEncrypt.length()>0)
				phone = AES.encrypt(LFPhone.normalizePhone(toEncrypt));
		} catch (Exception e){
			e.printStackTrace();
		}
		
		if(phone!=null || intnum != null || areanum!=null || exchangenum!=null || linenum!=null || extension!=null){
			sql += " left outer join o.client.phones p";
		}
		sql += " where 1=1";

		if (category > 0 || subCategory > 0 || haveBrand || haveDesc || haveSerial || haveTracking || value > -1) {
			String itemSql = " and i.type = " + dto.getType();
			if (category > 0) {
				itemSql += " and i.category = " + category;
			}
			
			if (subCategory > 0 ) {
				itemSql += " and i.subCategory = " + subCategory;
			}
			
			if (haveBrand) {
				itemSql += " and i.brand like \'%" + dto.getBrand().trim().replaceAll("'", "''") + "%\'";
			}
			
			if (haveDesc) {
				itemSql += " and i.description like \'%" + dto.getItemDescription().trim().replaceAll("'", "''") + "%\'";
			}
			if(haveSerial){
				String sn = dto.getSerialNumber().trim().replaceAll("'", "''");
				if(sn != null){
					if(sn.contains("%")){
						itemSql += " and i.serialNumber like \'" + sn + "\'";
					} else {
						itemSql += " and i.serialNumber = \'" + sn + "\'";
					}
				}
			}
			if (haveTracking) {
				itemSql += " and i.trackingNumber = \'" + dto.getTrackingNumber().trim().replaceAll("'", "''") + "\'";
			}
			if (value > -1) {
				itemSql += " and i.value = " + value;
			}
			
			sql += itemSql;
		}
		
		if(intnum!=null){
			sql += " and p.countryNumber = \'" + intnum + "\'";
		}
		if(areanum!=null){
			sql += " and p.areaNumber = \'" + areanum + "\'";
		}
		if(exchangenum!=null){
			sql += " and p.exchangeNumber = \'" + exchangenum + "\'";
		}
		if(linenum!=null){
			sql += " and p.lineNumber = \'" + linenum + "\'";
		}
		if(extension!=null){
			sql += " and p.extension = \'" + extension + "\'";
		}

		if (phone != null) {
			sql += " or (p.phoneNumber = \'" + phone + "\')";
		}
		if(dto.getType() == TracingConstants.LF_TYPE_FOUND && dto.getBarcode() != null && dto.getBarcode().trim().length() > 0){
			sql += " and o.barcode = \'" + dto.getBarcode().replaceAll("'", "''") + "\'";
		} else if(dto.getId() > 0){
			sql += " and o.id = " + dto.getId();
		}

		if(dto.getLastName() != null && dto.getLastName().trim().length() > 0){
			String lName = dto.getLastName().trim().toUpperCase().replaceAll("'", "''");
			String comparator = "=";
			if (lName.contains("%")) {
				comparator = "like";
			}
			sql += " and o.client.lastName " + comparator + " \'" + lName + "\'";
		}
		if(dto.getFirstName() != null && dto.getFirstName().trim().length() > 0){
			String fName = dto.getFirstName().trim().toUpperCase().replaceAll("'", "''");
			String comparator = "=";
			if (fName.contains("%")) {
				comparator = "like";
			}
			sql += " and o.client.firstName " + comparator + " \'" + fName + "\'";
		}
		if(dto.getCompanyId() !=null && dto.getCompanyId().length()>0){
			sql+=" and o.companyId = \'"+dto.getCompanyId().replaceAll("'", "''")+"\'";
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
				sql += " and o.lossInfo.mvaNumber = \'" + dto.getMvaNumber().replaceAll("'", "''") + "\'";
			} else {
				sql += " and o.mvaNumber = \'" + dto.getMvaNumber().replaceAll("'", "''") + "\'";
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
			}
		}
		if(dto.getEndRentDate() != null && dto.getEndRentDate().trim().length() > 0){
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(dto.getEndRentDateAsDate());
			cal.add(Calendar.DATE, 1);
			String date = DateUtils.formatDate(cal.getTime(), TracingConstants.getDBDateFormat(HibernateWrapper.getConfig().getProperties()), null, null);
			if(dto.getType() == TracingConstants.LF_TYPE_LOST){
				sql += " and o.lossInfo.lossdate < \'" + date + "\'";
			}
		}
		if(dto.getAgreementNumber() != null && dto.getAgreementNumber().trim().length() > 0){
			if(dto.getType() == TracingConstants.LF_TYPE_LOST){
				sql += " and o.lossInfo.agreementNumber = \'" + dto.getAgreementNumber().trim().replaceAll("'", "''") + "\'";
			} else {
				sql += " and o.agreementNumber = \'" + dto.getAgreementNumber().trim().replaceAll("'", "''") + "\'";
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
				String ag = dto.getAgentName().replaceAll("'", "''");
				if(ag != null){
					sql += " and o.agent.username like \'" + ag + "\'";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(dto.getAgent().getSubcompany()!=null){
			try {
				String scc = dto.getAgent().getSubcompany().getSubcompanyCode();
				if(scc != null){
					sql += " and o.companyId = \'" + scc + "\'";
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
	

	public void saveOrUpdateShipping(LFShipping shipment) throws UpdateException {
		Session sess = null;
		Transaction t = null;
		
		try{
			sess = HibernateWrapper.getSession().openSession();
			t = sess.beginTransaction();
			sess.saveOrUpdate(shipment);
			t.commit();
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
	}
	
	public void saveTransaction(LFTransaction tran) throws UpdateException {
		Session sess = null;
		Transaction t = null;
		
		try{
			sess = HibernateWrapper.getSession().openSession();
			t = sess.beginTransaction();
			sess.saveOrUpdate(tran);
			t.commit();
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
	
	@SuppressWarnings("unchecked")
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
			// Replaced the below else statement with an else if that will allow a full unpaginated search
			// when you provide the value -99 for both the start and the offset. This is needed to run a
		    // report and was done this way to maintain the functionality in other parts of the application
			// that expect this exception to be thrown. CG 9/22/13
			} else if (start != -99 && offset != -99){
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
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Subcompany> getSubcompanies(String companycode) {
		String sql = "from com.bagnet.nettracer.tracing.db.lf.Subcompany sub where sub.company.companyCode_ID = :companycode order by sub.name";
		Session sess = HibernateWrapper.getSession().openSession();
		Query q = sess.createQuery(sql);
		q.setParameter("companycode", companycode);
		List<Subcompany> subCompList = null;
		
		try{
			subCompList = (List<Subcompany>)q.list();
		}catch (Exception e){
			e.printStackTrace();
		}
		finally{
			sess.close();
		}
		return subCompList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SubcompanyStation> getSubcompanyStations(String companycode) {
		String sql = "from com.bagnet.nettracer.tracing.db.lf.SubcompanyStation sub where sub.subcompany.company.companyCode_ID = :companycode order by sub.station.stationcode";
		Session sess = HibernateWrapper.getSession().openSession();
		Query q = sess.createQuery(sql);
		q.setParameter("companycode", companycode);
		List<SubcompanyStation> subCompList = null;
		
		try{
			subCompList = (List<SubcompanyStation>)q.list();
		}catch (Exception e){
			e.printStackTrace();
		}
		finally{
			sess.close();
		}
		return subCompList;
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

	private String getToBeShippedQuery(Agent agent){
		String sql = "from com.bagnet.nettracer.tracing.db.lf.detection.LFMatchHistory mh where 1=1 ";
//				if(!TracingConstants.LF_LF_COMPANY_ID.equals(agent.getStation().getCompany().getCompanyCode_ID()))
//				{
//					sql +=" and (l.lossInfo.destination.station_ID = " + agent.getStation().getStation_ID()+
//							" or l.lossInfo.destination.lz_ID = " + agent.getStation().getStation_ID()+") ";
//				}
				sql += " and mh.status.status_ID = "+TracingConstants.LF_TRACING_CONFIRMED+
						" and mh.lost.shipment.transaction!=null and mh.found!=null";
				if(agent.getSubcompany()!=null){
					sql += " and mh.lost.companyId = '"+agent.getSubcompany().getSubcompanyCode()+"'";
				}
				
		return sql;
	}
	
	private String getLostQuery(Agent agent){
		String sql = "from com.bagnet.nettracer.tracing.db.lf.LFLost l where 1=1 ";
				if(!TracingConstants.LF_LF_COMPANY_ID.equals(agent.getStation().getCompany().getCompanyCode_ID()))
				{
					sql +=" and (l.lossInfo.destination.station_ID = " + agent.getStation().getStation_ID()+
							" or l.lossInfo.destination.lz_ID = " + agent.getStation().getStation_ID()+") ";
				}
				sql += " and l.status.status_ID != " + TracingConstants.LF_STATUS_CLOSED;
				if(agent.getSubcompany()!=null){
					sql += " and l.companyId = '"+agent.getSubcompany().getSubcompanyCode()+"'";
				}
				
		return sql;
	}
	
	private String getLostQuery(Station station, Subcompany subcomp){
		String sql = "from com.bagnet.nettracer.tracing.db.lf.LFLost l where ";
				if(!TracingConstants.LF_LF_COMPANY_ID.equals(station.getCompany().getCompanyCode_ID()))
				{
					sql +=" (l.lossInfo.destination.station_ID = " + station.getStation_ID()+
							" or l.lossInfo.destination.lz_ID = " + station.getStation_ID()+") and";
				}
				sql += " l.status.status_ID != " + TracingConstants.LF_STATUS_CLOSED;
				if(subcomp!=null){
					sql+=" and l.companyId = '"+subcomp.getSubcompanyCode()+"'";
				}
				
		return sql;
	}

	private String getFoundQuery(Agent agent){
		String sql = "from com.bagnet.nettracer.tracing.db.lf.LFFound f " +
				"where (f.location.station_ID = " + agent.getStation().getStation_ID()
				+ " or f.location.lz_ID = " + agent.getStation().getStation_ID() + ")"
				+ " and f.status.status_ID != " + TracingConstants.LF_STATUS_CLOSED;

		if(agent.getSubcompany()!=null){
			sql += " and f.companyId = '"+agent.getSubcompany().getSubcompanyCode()+"'";
		}
		return sql;
	}
	
	private String getFoundQuery(Station station,Subcompany subcomp){
		String sql = "from com.bagnet.nettracer.tracing.db.lf.LFFound f " +
				"where (f.location.station_ID = " + station.getStation_ID()
				+ " or f.location.lz_ID = " + station.getStation_ID() + ")"
				+ " and f.status.status_ID != " + TracingConstants.LF_STATUS_CLOSED;
		if(subcomp!=null){
			sql+=" and l.companyId = '"+subcomp.getSubcompanyCode()+"'";
		}
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
	
	private String getLFCItemsToSalvageQuery(Station station, HandleItemsForm hiForm, Subcompany subcomp) {
		
		StringBuilder sql=new StringBuilder();
		sql.append("from com.bagnet.nettracer.tracing.db.lf.LFItem i, com.bagnet.nettracer.tracing.db.lf.Subcompany sc where i.found.location.station_ID = " + station.getStation_ID() + " " +
					 " and i.found.companyId=sc.subcompanyCode " +
					 " and i.type = " + TracingConstants.LF_TYPE_FOUND + " " +
					 " and (i.disposition.status_ID in (" + TracingConstants.LF_DISPOSITION_OTHER + "," + TracingConstants.LF_DISPOSITION_TO_BE_DELIVERED + ") or i.deliveryRejected = 1) ");
		
		if (hiForm == null) {
			
			sql.append(" and ((i.value = " + TracingConstants.LFC_ITEM_LOW_VALUE + " and dateDiff(now(),date(i.found.receivedDate)) >= sc.salvage_Low) or " +
					 	" (i.value = " + TracingConstants.LFC_ITEM_HIGH_VALUE + " and dateDiff(now(),date(i.found.receivedDate)) >= sc.salvage_High))");
		
		} else if (hiForm.getValue() == TracingConstants.LFC_ITEM_LOW_VALUE) {
		
			sql.append(" and i.value = " + TracingConstants.LFC_ITEM_LOW_VALUE + " and dateDiff(now(),date(i.found.receivedDate)) >= sc.salvage_Low ");
		
		} else if (hiForm.getValue() == TracingConstants.LFC_ITEM_HIGH_VALUE) {
		
			sql.append(" and i.value = " + TracingConstants.LFC_ITEM_HIGH_VALUE + " and dateDiff(now(),date(i.found.receivedDate)) >= sc.salvage_High ");
		
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
			
			sql.append(" and i.found.receivedDate between \'" + startDate + "\' and \'" + endDate + "\' ");
		}
		if(subcomp!=null){
			sql.append("  and i.found.companyId=\'"+subcomp.getSubcompanyCode()+"\'");
		}
		
		return sql.toString();
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

	private int getToBeShippedCount(String sql){
		if(sql == null){
			return 0;
		}
		String query = "select count(mh.id) " + sql;
		Session sess = null;
		try{
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess.createQuery(query);
			@SuppressWarnings("unchecked")
			List<Long> result = q.list();
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
			List<?> result = q.list();
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
			List<?> result = q.list();
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
			
			@SuppressWarnings("unchecked")
			List<LFFound> results = q.list();
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
		
		String query= "select distinct i "+sql; 
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
			List<LFItem> results = q.list();
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
	public int getToBeShippedCount(Agent agent) {
		if(agent == null){
			return 0;
		}
		return getToBeShippedCount(getToBeShippedQuery(agent));
	}

	@Override
	public List<LFMatchHistory> getToBeShippedList(Agent agent, int start, int offset) {
		if(agent == null){
			return null;
		}
		return getToBeShippedList(getToBeShippedQuery(agent), start, offset);
	}
	
	private List<LFMatchHistory> getToBeShippedList(String sql, int start, int offset) {
		if(sql == null){
			return null;
		}
		String query = sql + " order by mh.lost.shipment.transaction.transactionDate asc";
		Session sess = null;
		try{
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess.createQuery(query);
			
			@SuppressWarnings("unchecked")
			List<LFMatchHistory> results = q.list();
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
	public int getLostCount(Agent agent) {
		if(agent == null){
			return 0;
		}
		return getLostCount(getLostQuery(agent));
	}
	
	@Override
	public int getLostCount(Station station,Subcompany subcomp) {
		if(station == null){
			return 0;
		}
		return getLostCount(getLostQuery(station,subcomp));
	}
	
	public int getLostCount(Station station) {
		if(station == null){
			return 0;
		}
		return getLostCount(getLostQuery(station,null));
	}

	@Override
	public List<LFLost> getLostPaginatedList(Station station, int start, int offset, Subcompany subcomp) {
		if(station == null){
			return null;
		}
		return getLostPaginatedList(getLostQuery(station,subcomp), start, offset);
	}
	
	public List<LFLost> getLostPaginatedList(Station station, int start, int offset) {
		if(station == null){
			return null;
		}
		return getLostPaginatedList(getLostQuery(station,null), start, offset);
	}


	@Override
	public int getFoundCount(Agent agent) {
		if(agent == null){
			return 0;
		}
		return getFoundCount(getFoundQuery(agent));
	}
	
	@Override
	public int getFoundCount(Station station,Subcompany subcomp) {
		if(station == null){
			return 0;
		}
		return getFoundCount(getFoundQuery(station,subcomp));
	}

	public int getFoundCount(Station station) {
		if(station == null){
			return 0;
		}
		return getFoundCount(getFoundQuery(station,null));
	}

	@Override
	public List<LFFound> getFoundPaginatedList(Station station, int start, int offset, Subcompany subcomp) {
		if(station == null){
			return null;
		}
		return getFoundPaginatedList(getFoundQuery(station,subcomp), start, offset);
	}
	
	public List<LFFound> getFoundPaginatedList(Station station, int start, int offset) {
		if(station == null){
			return null;
		}
		return getFoundPaginatedList(getFoundQuery(station,null), start, offset);
	}

	@Override
	public int getItemsToSalvageCount(Station station, Subcompany subcomp) {
		if(station == null){
			return 0;
		} else if (station.getCompany().getCompanyCode_ID().equals(TracingConstants.LF_AB_COMPANY_ID)) {
			return getItemCount(getItemsToSalvageQuery(station));
		} else if (station.getCompany().getCompanyCode_ID().equals(TracingConstants.LF_LF_COMPANY_ID)) {
			return getItemCount(getLFCItemsToSalvageQuery(station, null,subcomp));
		}
		return 0;
	}

	public int getLFItemsToSalvageCount(Station station, HandleItemsForm hiForm, Subcompany subcomp) {
		return getItemCount(getLFCItemsToSalvageQuery(station, hiForm,subcomp));
	}

	@Override
	public List<LFItem> getItemsToSalvagePaginatedList(Station station, int start, int offset, Subcompany subcomp) {
		if(station == null){
			return null;
		}
		return getItemPaginatedList(getItemsToSalvageQuery(station), start, offset);
	}

	public List<LFItem> getItemsToSalvagePaginatedList(Station station, int start, int offset) {
		if(station == null){
			return null;
		}
		return getItemPaginatedList(getItemsToSalvageQuery(station), start, offset);
	}
	
	public List<LFItem> getLFItemsToSalvagePaginatedList(Station station, HandleItemsForm hiForm, int start, int offset, Subcompany subcomp) {
		if (station == null || hiForm == null) {
			return null;
		}
		return getItemPaginatedList(getLFCItemsToSalvageQuery(station, hiForm, subcomp), start, offset);
	}

	private String getTraceResultsQuery(Station station, Subcompany subcomp){
		
		String sql = "from com.bagnet.nettracer.tracing.db.lf.detection.LFMatchHistory mh";
				
		sql += " where mh.status.status_ID = " + TracingConstants.LF_TRACING_OPEN;
		if(!TracingConstants.LF_LF_COMPANY_ID.equals(station.getCompany().getCompanyCode_ID()))
		{
			sql +=" and (mh.lost.lossInfo.destination.station_ID = " + station.getStation_ID() + " or "+
					" mh.lost.lossInfo.destination.lz_ID = " + station.getStation_ID() + " or "+
					" mh.found.location.station_ID = " + station.getStation_ID() + " or " +
					" mh.found.location.lz_ID = " + station.getStation_ID() + ")";
		} 
		if(subcomp!=null){
			sql +=" and (mh.found.companyId='"+subcomp.getSubcompanyCode()+"' or mh.lost.companyId='"+subcomp.getSubcompanyCode()+"')";
		}
		return sql;
	}
	

	public int getTraceResultsCount(Station station) {
		return getTraceResultsCount(station,null);
	}
	
	@Override
	//TODO review if this needs to be deprecated
	public int getTraceResultsCount(Station station, Subcompany subcomp) {
		if(station == null){
			return 0;
		}
		String query = "select count(mh.id) " + getTraceResultsQuery(station, subcomp);
		Session sess = null;
		try{
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess.createQuery(query);
			List<?> result = q.list();
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

	public List<LFMatchHistory> getTraceResultsPaginated(Station station, int start, int offset){
		return getTraceResultsPaginated(station, start, offset, null);
	}
	
	@Override
	//TODO review if this needs to be deprecated
	public List<LFMatchHistory> getTraceResultsPaginated(Station station, int start, int offset, Subcompany subcomp) {
		if(station == null){
			return null;
		}
		String query = getTraceResultsQuery(station, subcomp) + " order by score desc, id";
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
			List<LFMatchHistory> results = q.list();
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

	public int getDeliveryPendingCount(Station station){
		return getDeliveryPendingCount(station, null);
	}
	
	@Override
	public int getDeliveryPendingCount(Station station, Subcompany subcomp) {
		if(station == null){
			return 0;
		}
		String query = "select count (i.id) from com.bagnet.nettracer.tracing.db.lf.LFItem i " +
				"where (i.found.location.station_ID = " + station.getStation_ID() +
				" or i.found.location.lz_ID = " + station.getStation_ID() + ")" +
				" and i.disposition.status_ID = " + TracingConstants.LF_DISPOSITION_TO_BE_DELIVERED +
				" and i.type = " + TracingConstants.LF_TYPE_FOUND;
		if(subcomp!=null){
			query += " and i.found.companyId='"+subcomp.getSubcompanyCode()+"'";
		}
		Session sess = null;
		try{
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess.createQuery(query);
			List<?> result = q.list();
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

	public List<LFItem> getDeliveryPendingPaginatedList(Station station, int start, int offset){
		return getDeliveryPendingPaginatedList(station, start, offset,null); 
	}
	
	
	@Override
	public List<LFItem> getDeliveryPendingPaginatedList(Station station, int start, int offset, Subcompany subcomp) {
		if(station == null){
			return null;
		}
		
		String query = "from com.bagnet.nettracer.tracing.db.lf.LFItem i " +
		"where (i.found.location.station_ID = " + station.getStation_ID() +
		" or i.found.location.lz_ID = " + station.getStation_ID() + ")" +
		" and i.disposition.status_ID = " + TracingConstants.LF_DISPOSITION_TO_BE_DELIVERED + 
		" and i.type = " + TracingConstants.LF_TYPE_FOUND; 
		if(subcomp!=null){
			query += " and i.found.companyId='"+subcomp.getSubcompanyCode()+"'";
		}
		query += " order by i.found.foundDate asc";
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
			List<LFItem> results = q.list();
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

	@SuppressWarnings("unchecked")
	@Override
	public List<LFMatchHistory> getTraceResultsForLost(long id) {
		String sql = "from com.bagnet.nettracer.tracing.db.lf.detection.LFMatchHistory m " +
		"where m.lost.id = " + id;

		Session sess = null;
		try{
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess.createQuery(sql);
			List<?> result = q.list();
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

	@SuppressWarnings("unchecked")
	@Override
	public List<LFMatchHistory> getTraceResultsForFound(long id) {
		String sql = "from com.bagnet.nettracer.tracing.db.lf.detection.LFMatchHistory m " +
				"where m.found.id = " + id;
		
		Session sess = null;
		try{
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess.createQuery(sql);
			List<?> result = q.list();
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
	public List<LFMatchHistory> traceFoundItemPrimary(long id) {
		ArrayList<LFMatchHistory> ret = new ArrayList<LFMatchHistory>();
		List<LFMatchHistory> toAdd = this.traceFoundItem(id, true);
		if(toAdd!=null){
			ret.addAll(toAdd);
		}
		return ret;
	}
	
	@Override
	public List<LFMatchHistory> traceFoundItemSecondary(long id) {
		ArrayList<LFMatchHistory> ret = new ArrayList<LFMatchHistory>();
		List<LFMatchHistory> toAdd = this.traceFoundItem(id, false);
		if(toAdd!=null){
			ret.addAll(toAdd);
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
	public List<LFMatchHistory> traceLostItemPrimary(long id) {
		ArrayList<LFMatchHistory> ret = new ArrayList<LFMatchHistory>();
		List<LFMatchHistory> toAdd = this.traceLostItem(id, true);
		if(toAdd!=null){
			ret.addAll(toAdd);
		}
		return ret;
	}
	
	@Override
	public List<LFMatchHistory> traceLostItemSecondary(long id) {
		ArrayList<LFMatchHistory> ret = new ArrayList<LFMatchHistory>();
		List<LFMatchHistory> toAdd = this.traceLostItem(id, false);
		if(toAdd!=null){
			ret.addAll(toAdd);
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
				match.setMatchTimeStamp(new Date());
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
	
	public long getFilteredTraceResultsCount(Station station, TraceResultsFilter filter, Subcompany subcomp) {
		String sql = "select count(distinct m) from com.bagnet.nettracer.tracing.db.lf.detection.LFMatchHistory m where 1 = 1";
		sql += getSqlFromTraceResultsForm(filter);
		if(!TracingConstants.LF_LF_COMPANY_ID.equals(station.getCompany().getCompanyCode_ID()))
		{
			sql +=" and (m.lost.lossInfo.destination.station_ID = " + station.getStation_ID() + " or "+
					" m.lost.lossInfo.destination.lz_ID = " + station.getStation_ID() + " or "+
					" m.found.location.station_ID = " + station.getStation_ID() + " or " +
			 		" m.found.location.lz_ID = " + station.getStation_ID() + ")";
		}
		if(subcomp!=null){
			sql+=" and (m.lost.companyId='"+subcomp.getSubcompanyCode()+"' or m.found.companyId='"+subcomp.getSubcompanyCode()+"')";
		}
		
		
		Session sess = null;
		try{
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess.createQuery(sql);
			List<?> result = q.list();
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
	
	@SuppressWarnings("unchecked")
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
	
	@SuppressWarnings("unchecked")
	public List<LFMatchHistory> getFilteredTraceResultsPaginatedList(Station station, TraceResultsFilter filter, int start, int offset, Subcompany subcomp) {
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
		if(subcomp!=null){
			sql+=" and (m.lost.companyId='"+subcomp.getSubcompanyCode()+"' or m.found.companyId='"+subcomp.getSubcompanyCode()+"')";
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
			pq.addScalar("lostid", StandardBasicTypes.LONG);
			@SuppressWarnings("unchecked")
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
			pq.addScalar("lostid", StandardBasicTypes.LONG);
			@SuppressWarnings("unchecked")
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
	
	protected List<Long> getXDayList(int notice) throws Exception{
		String sql = "select l.id lostid from lflost l left outer join subcompany sc on sc.subcompanycode=l.companyId " +
		" where l.status_ID != " + TracingConstants.LF_STATUS_CLOSED +
		" and (datediff(curdate(),l.openDate)) >= ";
		switch(notice){
			case 1: sql += " sc.email_notice_1 and sc.email_notice_1 > 0 "; break;
			case 2: sql += " sc.email_notice_2 and sc.email_notice_2 > 0 "; break;
			case 3: sql += " sc.email_notice_3 and sc.email_notice_3 > 0 "; break;
			case 4: sql += " sc.email_notice_4 and sc.email_notice_4 > 0 "; break;
			case 5: sql += " sc.email_notice_5 and sc.email_notice_5 > 0 "; break;
		}
		sql += " and l.id not in (select mh.lost_id from lfmatchhistory mh where mh.lost_id = l.id and mh.status_Status_ID = " +
		TracingConstants.LF_TRACING_CONFIRMED +
		" ) ";
		
		switch(notice){
		case 1: sql+=" and l.email1 = 0";break;
		case 2: sql+=" and l.email2 = 0";break;
		case 3: sql+=" and l.email3 = 0";break;
		case 4: sql+=" and l.email4 = 0";break;
		case 5: sql+=" and l.email5 = 0";break;
		default:throw new Exception();
		}
		
		Session sess = null;
		SQLQuery pq = null;
		ArrayList<Long>lostIds = new ArrayList<Long>();
		try{
			sess = HibernateWrapper.getSession().openSession();
			pq = sess.createSQLQuery(sql.toString());
			pq.addScalar("lostid", StandardBasicTypes.LONG);
			@SuppressWarnings("unchecked")
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
	
	public void sendNoticeEmails(int notice){
		try {
			List<Long> lostIds = getXDayList(notice);
			if(lostIds != null){
				for(Long id: lostIds){
					sendNotice(id,notice);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendNoticeEmails(){
		sendNoticeEmails(1);
		sendNoticeEmails(2);
		sendNoticeEmails(3);
		sendNoticeEmails(4);
		sendNoticeEmails(5);
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
		Subcompany subcomp=SubCompanyDAO.loadSubcompany(lost.getCompanyId(), lost.getAgent().getCompanycode_ID());
		if("AB".equalsIgnoreCase(subcomp.getCompany().getCompanyCode_ID())){
			h.put("COMPANY", (lost.getCompanyId().equals(TracingConstants.LF_BUDGET_COMPANY_ID) ? "Budget" : "Avis"));
			h.put("SUBJECTLINE", resources.getString("ab.email.subject"));
		} else if ("LF".equalsIgnoreCase(subcomp.getCompany().getCompanyCode_ID())){
			h.put("COMPANY", subcomp.getName());
			h.put("SUBJECTLINE", subcomp.getEmail_Subject());
		} else {
			h.put("COMPANY", "Company");
			h.put("SUBJECTLINE", resources.getString("dm.email.subject"));
		}
		
		//h.put("HASH", ""+lost.getClient().getDecryptedEmail().hashCode());
		
		return h;
	}

	private HashMap<String, String> getEmailParams(LFLost lost,
			ShippingBean bean) {
		HashMap<String,String> h = new HashMap<String,String>();
		h.put("LOSTID", (new Long(lost.getId())).toString());
		
		if(lost.getLossInfo()!=null && lost.getLossInfo().getDestination()!= null){
			h.put("DAYS", (new Integer(lost.getLossInfo().getDestination().getPriority()).toString()));
		}
				
		if(lost.getClient() != null){
			h.put("FIRSTNAME", WordUtils.capitalize(lost.getClient().getFirstName().toLowerCase()));
			h.put("LASTNAME", WordUtils.capitalize(lost.getClient().getLastName().toLowerCase()));
		}
		h.put("TRANNUM", bean.getTransactionNum());
		h.put("CARDNUM", bean.getCredit4num());
		h.put("CARDTYPE", bean.getCardType());
		h.put("DATEPAID", bean.getDatePaid());
		h.put("SHIPTYPE", bean.getShippingOption());
		h.put("TOTAL", bean.getTotalPayment());
		h.put("EMAIL", bean.getLost().getContact().getEmailAddress());
		h.put("ADDRESS1", bean.getShippingAddress().getAddress1());
		h.put("ADDRESS2", bean.getShippingAddress().getAddress2());
		if(bean.getShippingAddress().getCountry().equals("US")){
			h.put("CITYSTATEPROVINCE", bean.getShippingAddress().getCity()+", "+bean.getShippingAddress().getState());
		} else if (bean.getShippingAddress().getCountry().equals("CA")){
			h.put("CITYSTATEPROVINCE", bean.getShippingAddress().getCity()+", "+bean.getShippingAddress().getProvince());
		} else {
			h.put("CITYSTATEPROVINCE", bean.getShippingAddress().getCity()+",");
		}
		h.put("ZIP", bean.getShippingAddress().getPostal());
		h.put("COUNTRY", bean.getShippingAddress().getCountry());
		
		h.put("ADDRESS2", bean.getShippingAddress().getAddress2());
		h.put("MAXDAYS", PropertyBMO.getValue(PropertyBMO.LF_AUTO_CLOSE_DAYS));
		//by default we use the return address from the company variable
		h.put("RETURNADDRESS", getAutoAgent().getStation().getCompany().getVariable().getEmail_from());
		Subcompany subcomp=SubCompanyDAO.loadSubcompany(lost.getCompanyId(), lost.getAgent().getCompanycode_ID());
		if("AB".equalsIgnoreCase(subcomp.getCompany().getCompanyCode_ID())){
			h.put("COMPANY", (lost.getCompanyId().equals(TracingConstants.LF_BUDGET_COMPANY_ID) ? "Budget" : "Avis"));
			h.put("SUBJECTLINE", resources.getString("ab.email.subject"));
		} else if ("LF".equalsIgnoreCase(subcomp.getCompany().getCompanyCode_ID())){
			h.put("COMPANY", subcomp.getName());
			h.put("SUBJECTLINE", subcomp.getEmail_Subject());
		} else {
			h.put("COMPANY", "Company");
			h.put("SUBJECTLINE", resources.getString("dm.email.subject"));
		}
		
		h.put("HASH", ""+lost.getClient().getDecryptedEmail().hashCode());
		
		return h;
	}
	
	private HashMap<String,String> getEmailParams(){
		
		
		HashMap<String,String> h = new HashMap<String,String>();
		GregorianCalendar cal=new GregorianCalendar();
		Date endDate=new Date();
		//DateUtils.addDays(endDate, -7);
		Date week=DateUtils.addDays(endDate, -7);
		Date fourWeek=DateUtils.addDays(endDate, -28);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		Date beginMonth=cal.getTime();
		cal.set(Calendar.DAY_OF_YEAR, 1);
		Date beginYear=cal.getTime();
		System.out.println(DateUtils.formatDate(fourWeek,TracingConstants.DB_DATEFORMAT, null, null));
		
		//DateUtils.
		cal.setTime(week);
		int i=1;
		do{
			h.put("date"+i, DateUtils.formatDate(cal.getTime(),TracingConstants.DISPLAY_DATEFORMAT, null, null));
			cal.add(GregorianCalendar.DATE, 1);
			i++;
		}
		while(endDate.after(cal.getTime()));
		cal.setTime(fourWeek);
		i=1;
		do{
			h.put("week"+i,DateUtils.formatDate(cal.getTime(), TracingConstants.DISPLAY_DATEFORMAT, null, null));
			cal.add(GregorianCalendar.DATE, 7);
			i++;
		}
		while((DateUtils.addDays(endDate,1)).after(cal.getTime()));
		
		getLostReportsEntered(h, endDate,week);
		getBoxesEntered(h,endDate,week);	
		getHighValueEntered(h,endDate,week);
		getLowValueEntered(h,endDate,week);
		getReturnedForValueType(HIGH_VALUE,"HighReturned", h,endDate,week);
		getReturnedForValueType(LOW_VALUE,"LowReturned", h,endDate,week);
		getMTDTotals(h,endDate,beginMonth);
		getYTDTotals(h,endDate,beginYear);
		getWeekItemCounts(h,endDate,fourWeek);		
		
		h.put("STARTDATE",DateUtils.formatDate(fourWeek, TracingConstants.DISPLAY_DATEFORMAT, null, null));
		h.put("ENDDATE", DateUtils.formatDate(endDate, TracingConstants.DISPLAY_DATEFORMAT, null, null));
		h.put("SUBJECTLINE", "Weekly Summary Report: {STARTDATE} - {ENDDATE}");
		
		return h;
	}
	
	public void getBoxesEntered(HashMap<String, String> h,Date endDate, Date week)
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
			
			List<?> lis = q.list();
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
	
	public void getLostReportsEntered(HashMap<String, String> h,Date endDate, Date week)
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
			
			List<?> lis = q.list();
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
	

	public void getHighValueEntered(HashMap<String, String> h,Date endDate, Date week)
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
			
			List<?> lis = q.list();
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
	
	public void getLowValueEntered(HashMap<String, String> h,Date endDate, Date week)
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
			
			List<?> lis = q.list();
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

	public void getReturnedForValueType(int type, String fieldNameLabel, HashMap<String, String> h, Date endDate, Date startDate)
	{
		int weeklyTotal=0;
		
		String sql = CALCULATE_RETURNED_ITEMS_QUERY + CALCULATE_RETURNED_ITEMS_GROUP_BY;
 		SQLQuery q = null;
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			q = sess.createSQLQuery(sql.toString());
			
			q.setParameter("enddate", DateUtils.formatDate(endDate, TracingConstants.DB_DATEFORMAT, null, null));
			q.setParameter("lastweek", DateUtils.formatDate(startDate, TracingConstants.DB_DATEFORMAT, null, null));
			q.setInteger("value",type);
			
			List<?> lis = q.list();
			Object[] o;
			if(lis.size()!=0){
			for (int i = 0; i < lis.size(); ++i) {
				o = (Object[]) lis.get(i);
				for(int j=1; j<8; j++){
					if(DateUtils.formatDate((Date)o[0], TracingConstants.DISPLAY_DATEFORMAT, null, null).equals(h.get("date"+j))){
						if(o[1]!=null){
							h.put(fieldNameLabel+(j), o[1].toString());
							weeklyTotal+=Integer.valueOf(o[1].toString());
						} else {
							h.put(fieldNameLabel+(j), "0");					
						}
					}
					else if(h.get(fieldNameLabel+(j))==(null))
					{
						h.put(fieldNameLabel+(j), "0");
					}
				}
			}
			}
			else
			{
				for(int j=1; j<8; j++){
					h.put(fieldNameLabel+(j), "0");
				}
			}
			h.put(fieldNameLabel+"Week", String.valueOf(weeklyTotal));
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
	
	public void getMTDTotals(HashMap<String, String> h, Date endDate, Date week)
	{
		//Low Value Items returned MTD
		String sql = CALCULATE_RETURNED_ITEMS_QUERY;
		SQLQuery q = null;
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			q = sess.createSQLQuery(sql);
			
			q.setParameter("enddate", DateUtils.formatDate(endDate, TracingConstants.DB_DATEFORMAT, null, null));
			q.setParameter("lastweek", DateUtils.formatDate(week, TracingConstants.DB_DATEFORMAT, null, null));
			q.setInteger("value", LOW_VALUE);
			
			List<?> lis = q.list();
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
		sql = CALCULATE_RETURNED_ITEMS_QUERY;
		q = null;
		sess = HibernateWrapper.getSession().openSession();
		try {
			q = sess.createSQLQuery(sql);
			
			q.setParameter("enddate", DateUtils.formatDate(endDate, TracingConstants.DB_DATEFORMAT, null, null));
			q.setParameter("lastweek", DateUtils.formatDate(week, TracingConstants.DB_DATEFORMAT, null, null));
			q.setInteger("value", HIGH_VALUE);
			
			List<?> lis = q.list();
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
			
			List<?> lis = q.list();
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
			
			List<?> lis = q.list();
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
			
			List<?> lis = q.list();
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
			
			List<?> lis = q.list();
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
	
	public void getYTDTotals(HashMap<String, String> h, Date endDate, Date week)
	{
		//Low Value Items returned
		String sql = CALCULATE_RETURNED_ITEMS_QUERY;
		SQLQuery q = null;
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			q = sess.createSQLQuery(sql);
			
			q.setParameter("enddate", DateUtils.formatDate(endDate, TracingConstants.DB_DATEFORMAT, null, null));
			q.setParameter("lastweek", DateUtils.formatDate(week, TracingConstants.DB_DATEFORMAT, null, null));
			q.setInteger("value", LOW_VALUE);
			List<?> lis = q.list();
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
		sql = CALCULATE_RETURNED_ITEMS_QUERY;
		q = null;
		sess = HibernateWrapper.getSession().openSession();
		try {
			q = sess.createSQLQuery(sql.toString());
			
			q.setParameter("enddate", DateUtils.formatDate(endDate, TracingConstants.DB_DATEFORMAT, null, null));
			q.setParameter("lastweek", DateUtils.formatDate(week, TracingConstants.DB_DATEFORMAT, null, null));
			q.setInteger("value", HIGH_VALUE);
			
			List<?> lis = q.list();
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
			
			List<?> lis = q.list();
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
			
			List<?> lis = q.list();
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
			
			List<?> lis = q.list();
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
			
			List<?> lis = q.list();
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
	
	public void getWeekItemCounts(HashMap<String, String> h, Date endDate, Date fourweek)
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
			
			List<?> lis = q.list();
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
		// The query below can be copied and inserted directly into Toad and formatted.  It is a fully functional SQL Query.
		sql = "select q1.val, q1.wk1 'Week', q1.ct1 'Entered', q2.ct2 'Returned', q3.ct3 'Waiting on Customer', " +
				"(q2.ct2+q3.ct3)/q1.ct1*100 as 'Return Rate' from (select case value when 0 then 'Low Value' else 'High Value' END as val, " +
				"DATE_ADD(f.receivedDate, INTERVAL(1-DAYOFWEEK(f.receivedDate)) DAY) as wk1, " +
				"case count(*) when null then 0 else count(*) end as ct1 from lfitem i join lffound f on i.found_id = f.id where type = 2 and " +
				"f.receivedDate < :enddate and f.receivedDate >= :fourweek group by value, wk1) as q1 left outer join " +
				"( SELECT CASE i.value WHEN 0 THEN 'Low Value' ELSE 'High Value' END AS val, DATE_ADD(f.receivedDate, " +
				"INTERVAL (1 - DAYOFWEEK(f.receivedDate)) DAY) AS wk2, CASE count(*) WHEN NULL THEN 0 ELSE count(*) END AS ct2 FROM lfitem i JOIN " +
				"lffound f ON i.found_id = f.id LEFT OUTER JOIN lfmatchhistory h on h.found_id = f.id and h.status_Status_ID = 608 LEFT OUTER JOIN " +
				"lflost l on l.id = h.lost_id LEFT OUTER JOIN lfitem i2 on i2.lost_id = l.id and i2.type = 1 WHERE i.type = 2 AND f.receivedDate < :enddate " +
				"AND f.receivedDate >= :fourweek AND ( (i.trackingNumber IS NOT NULL AND i.trackingNumber != '') OR i.deliveryRejected = 1 OR " +
				"(i2.trackingNumber IS NOT NULL AND i2.trackingNumber != '') OR i2.deliveryRejected = 1) GROUP BY i.value, wk2 ) q2 on q2.val = q1.val and " +
				"q1.wk1 = q2.wk2 left outer join ( SELECT CASE i.value WHEN 0 THEN 'Low Value' ELSE 'High Value' END AS val, DATE_ADD(f.receivedDate, " +
				"INTERVAL (1 - DAYOFWEEK(f.receivedDate)) DAY) AS wk3, CASE count(*) WHEN NULL THEN 0 ELSE count(*) END AS ct3 FROM lfitem i JOIN lffound f ON " +
				"i.found_id = f.id LEFT OUTER JOIN lfmatchhistory h on h.found_id = f.id and h.status_Status_ID = 608 LEFT OUTER JOIN lflost l on l.id = h.lost_id " +
				"LEFT OUTER JOIN lfitem i2 on i2.lost_id = l.id and i2.type = 1 WHERE i.type = 2 AND f.receivedDate < :enddate AND f.receivedDate >= :fourweek " +
				"AND ( ((i.trackingNumber IS NULL OR i.trackingNumber = '') AND i.lost_id IS NOT NULL) OR (l.foundemail = 1 AND i2.found_id = i.found_id AND " +
				"(i2.trackingNumber IS NULL OR i2.trackingNumber = '') AND (i.trackingNumber IS NULL OR i.trackingNumber = '')) ) " +
				"GROUP BY i.value, wk3 ) q3 on q3.val = q1.val and q3.wk3 = q1.wk1 group by val, q1.wk1 order by q1.wk1 asc, q1.val desc";
	    q = null;
		try {
			q = sess.createSQLQuery(sql.toString());
			
			q.setParameter("enddate", DateUtils.formatDate(endDate, TracingConstants.DB_DATEFORMAT, null, null));
			q.setParameter("fourweek", DateUtils.formatDate(fourweek, TracingConstants.DB_DATEFORMAT, null, null));
			
			List<?> lis = q.list();
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
	
	public void sendShippedEmail(ShippingBean bean){
		LFLost lost = getLostReport(Long.valueOf(bean.getLost().getReportId()));
		HashMap<String,String> h = getEmailParams(lost, bean);
		
		if (TracingConstants.LF_SWA_COMPANY_ID.equalsIgnoreCase(lost.getCompanyId())){
			//use no reply email
			h.put("RETURNADDRESS", PropertyBMO.getValue(PropertyBMO.LF_EMAIL_RETURNADDR_INIT));
		}
		
		if(sendEmail(lost, h, "shipped_email.html", h.get("SUBJECTLINE"))){
			lost.setEmailSentDate(new Date());
			try {
				saveOrUpdateLostReport(lost,getAutoAgent());
				Logger.logLF(""+bean.getLost().getReportId(), "SHIPPED EMAIL SENT", 0);
			} catch (UpdateException e) {
				e.printStackTrace();
			}
		}
	}

	public void sendLFWeekly(){
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
	
	public void sendNotice(long id, int notice){
		LFLost lost = getLostReport(id);
		HashMap<String,String> h = getEmailParams(lost);
		
		String email = null;
		String logMessage = null;

		switch (notice){
		case 1:
			/*mloupas - at some point during initial development we had different retaddrs depending on the email.
			 *however, looking at the current prod db, all retaddrs are set to noreply@lostandfound.aero
			 *need to confirm with project owner if we can reduce to single retaddr
			 *
			 *also, if in the future if different subcompanies need different return addrs, the addrs need to be part of
			 *the subcompany table as opposed to the properties table
			 */
			h.put("RETURNADDRESS", PropertyBMO.getValue(PropertyBMO.LF_EMAIL_RETURNADDR_FIRST));
			email = "update_1_report_email.html";
			logMessage = "1ST NOTICE EMAIL SENT";
			break;
		case 2:
			h.put("RETURNADDRESS", PropertyBMO.getValue(PropertyBMO.LF_EMAIL_RETURNADDR_SECOND));
			email = "update_2_report_email.html";
			if (dataplan(lost) && SubCompanyDAO.loadSubcompany(lost.getCompanyId()).isSendDataplanEmails()){//Probably should normalize dataplan emails in a future ticket
				email = "update_2_ipad_report_email.html";
			}
			logMessage = "2ND NOTICE EMAIL SENT";
			break;
		case 3:
			h.put("RETURNADDRESS", PropertyBMO.getValue(PropertyBMO.LF_EMAIL_RETURNADDR_THIRD));
			email = "update_3_report_email.html";
			logMessage = "3RD NOTICE EMAIL SENT";
			break;
		case 4:
			h.put("RETURNADDRESS", PropertyBMO.getValue(PropertyBMO.LF_EMAIL_RETURNADDR_FORTH));
			email = "update_4_report_email.html";
			logMessage = "4TH NOTICE EMAIL SENT";
			break;
		case 5:
			h.put("RETURNADDRESS", PropertyBMO.getValue(PropertyBMO.LF_EMAIL_RETURNADDR_FIFTH));
			email = "update_5_report_email.html";
			logMessage = "5TH NOTICE EMAIL SENT";
			break;
		}


		if(!lost.isEmail(notice) && sendEmail(lost, h, email, h.get("SUBJECTLINE"))){
			lost.setEmailSentDate(new Date());
			lost.setEmail(notice,true);
			try {
				saveOrUpdateLostReport(lost,getAutoAgent());
				Logger.logLF(""+id, logMessage, 0);
			} catch (UpdateException e) {
				e.printStackTrace();
			}
		}
	}
	
	public LFAddress createShippingAddress(LFLost lost){
		LFAddress shipping=new LFAddress();
		BeanUtils.copyProperties(lost.getClient().getAddress(), shipping);
		shipping.setId(0);
		return shipping;
	}
	
	public LFShipping createAndSaveShipping(LFLost lost){
		LFShipping shipment=new LFShipping();
		shipment.setLost(lost);
		shipment.setClient(lost.getClient());
		shipment.setShippingAddress(createShippingAddress(lost));
		Session sess = null;
		Transaction t = null;
		try{
			sess = HibernateWrapper.getSession().openSession();
			t = sess.beginTransaction();
			sess.saveOrUpdate(shipment);
			t.commit();
			return shipment;
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
		return null;
	}
	
	public boolean sendFoundEmail(long id){
		LFLost lost = getLostReport(id);
		HashMap<String,String> h = getEmailParams(lost);
		if(!lost.isFoundEmail()){
			if(PropertyBMO.isTrue(PropertyBMO.LF_EMAIL_ONLINE_BILLING)){
				if(sendEmail(lost, h, "found_report_online.html", h.get("SUBJECTLINE"))){
					try {
						lost.setShipment(createAndSaveShipping(lost));
						lost.setEmailSentDate(new Date());
						lost.setFoundEmail(true);
						saveOrUpdateLostReport(lost,getAutoAgent());
						Logger.logLF(""+id, "FOUND EMAIL SENT", 0);
					} catch (UpdateException e) {
						e.printStackTrace();
					}
					return true;
				}
			} else {
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
		}
		return false;
	}
	
	public void closeLostAndEmail(long id, Agent agent){
		LFLost lost = getLostReport(id);
		HashMap<String,String> h = getEmailParams(lost);

		Subcompany subcomp=SubCompanyDAO.loadSubcompany(lost.getCompanyId(), lost.getAgent().getCompanycode_ID());
		boolean isAvis = (TracingConstants.LF_AB_COMPANY_ID.equalsIgnoreCase(subcomp.getCompany().getCompanyCode_ID()));
		
		if (!isAvis){
			//use no reply email
			h.put("RETURNADDRESS", PropertyBMO.getValue(PropertyBMO.LF_EMAIL_RETURNADDR_CLOSE));
		}
		
		String email = "closed_report_email.html";
		if (dataplan(lost) && SubCompanyDAO.loadSubcompany(lost.getCompanyId()).isSendDataplanEmails()){
			email = "closed_report_ipad_email.html";
		}
		
		boolean isNotMatched = !(lost.getItem() != null && lost.getItem().getFound() != null && lost.getItem().getLost() != null);
		
		if (!isAvis && isNotMatched) {
			if(sendEmail(lost, h, email, h.get("SUBJECTLINE"))){
			//regardless of the send status of the email, close the report
			}
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
			LFCategory cat = LFUtils.loadCategory(lost.getItem().getCategory());
			LFSubCategory subcat = LFUtils.loadSubCategory(lost.getItem().getSubCategory());
			if((cat != null && cat.isDataplan()) || (subcat != null && subcat.isDataplan())) {
				return true;
			}
		}
		return false;
	}
	
	public boolean sendEmail(LFLost lost, HashMap<String, String> params, String htmlFileName, String subjectline){
		if(lost != null && lost.getClient() != null && lost.getClient().getDecryptedEmail() != null && !lost.getClient().getDecryptedEmail().isEmpty()){
			try {
				String root = TracerProperties.get(lost.getAgent().getCompanycode_ID(),"email.resources");
				String imgbanner = TracerProperties.get(lost.getAgent().getCompanycode_ID(),"lf.email.banner");
				String configpath = root + "/";
				String imagepath = root + "/";
				Subcompany subcomp=SubCompanyDAO.loadSubcompany(lost.getCompanyId(), lost.getAgent().getCompanycode_ID());
				
				if(subcomp!=null && subcomp.getEmail_Path()!=null && subcomp.getEmail_Path().length()>0){
					configpath = root + subcomp.getEmail_Path();
					imagepath = root + subcomp.getEmail_Path();
				} else if("AB".equalsIgnoreCase(subcomp.getCompany().getCompanyCode_ID())){
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
				ArrayList<InternetAddress> al = new ArrayList<InternetAddress>();
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
	
	public boolean sendEmail(Agent a, HashMap<String, String> params, String htmlFileName, String subjectline){
			try {
				String root = TracerProperties.get(a.getCompanycode_ID(),"email.resources");
				String configpath = root + "/";
				configpath = root + "/WN/";
				
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
				ArrayList<InternetAddress> al = new ArrayList<InternetAddress>();
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
			List<?> result = q.list();
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
	public List<LFFound> getShelvedTraceResultsPaginated(Station station, int value, int start, int offset, Subcompany subcomp) {
		if(station == null){
			return null;
		}
		
		String sql = "from com.bagnet.nettracer.tracing.db.lf.detection.LFMatchHistory m "
				   + "where m.status = " + TracingConstants.LF_TRACING_OPEN + " and m.found.status = " + TracingConstants.LF_STATUS_OPEN + " "
				   + "and m.found.item.value = " + value + " "
				   + "and m.found.itemLocation = " + TracingConstants.LF_LOCATION_SHELF + " "
				   + "and not exists (from com.bagnet.nettracer.tracing.db.Lock l where l.lockKey = m.found.barcode) "
				   + "group by m.found.id order by m.id asc";
		if(subcomp!=null){
			sql += " and m.found.companyId='"+subcomp.getSubcompanyCode()+"'";
		}
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
			
			List<?> result = q.list();
			
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
	
	@SuppressWarnings("unchecked")
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
	
	@SuppressWarnings("unchecked")
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
			q.addScalar("s.id",StandardBasicTypes.LONG);
			q.addScalar("s.createdDate",StandardBasicTypes.DATE);
			q.addScalar("s.closedDate",StandardBasicTypes.DATE);
			q.addScalar("s.status_id",StandardBasicTypes.INTEGER);
			q.addScalar("s.agent_ID",StandardBasicTypes.INTEGER);
			q.addScalar("s.station_id",StandardBasicTypes.INTEGER);
			@SuppressWarnings("unchecked")
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
		String sql = "select f.id, f.barcode, f.receivedDate, i.brand, i.model, i.serialNumber, i.color, i.description, i.longDescription, a.username, a.currentTimeZone, i.category, i.subcategory, f.salvageBoxId from LFFound f" +
				" left join Agent a on f.agent_ID=a.agent_ID left join LFItem i on f.item_id=i.id where f.salvage_id=:sid ";
		SQLQuery q = null;
		
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			q = sess.createSQLQuery(sql.toString());
			q.setParameter("sid", id);
			List<?> lis = q.list();
			List<LFSalvageFound> results=new ArrayList<LFSalvageFound>();
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
				if (o[13] != null) {
					dto.setSalvageBoxId(o[13].toString());
				}
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
			List<?> result = q.list();
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
	@SuppressWarnings({ "unchecked" })
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
			
			List<LFSalvage> result = q.list();
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
			List<?> lis = q.list();
			List<SalvageDTO> results=new ArrayList<SalvageDTO>();
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
}
