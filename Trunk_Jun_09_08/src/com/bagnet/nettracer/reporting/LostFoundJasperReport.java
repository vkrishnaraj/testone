package com.bagnet.nettracer.reporting;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.classic.Session;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.dto.StatReportDTO;
import com.bagnet.nettracer.tracing.utils.DateUtils;

public class LostFoundJasperReport {
	
	private static final Logger logger = Logger.getLogger(LostFoundJasperReport.class);
	
	private final int ID = 0;
	private final int DATE = 1;
	private final int STATION = 2;
	private final int STATUS = 3;
	private final int DISPOSITION = 4;
	private final int TRACKING_NUMBER = 5;
	
	@SuppressWarnings("rawtypes")
	public List getReportData(StatReportDTO srDto, ResourceBundle resources) {
		ArrayList toReturn = null;		
		Session session = null;
		try {
			session = HibernateWrapper.getSession().openSession();
			SQLQuery query = session.createSQLQuery(getSqlFromDto(srDto));
			query.addScalar("id", Hibernate.LONG);
			query.addScalar("lfDate", Hibernate.TIMESTAMP);
			query.addScalar("stationcode", Hibernate.STRING);
			query.addScalar("desc", Hibernate.STRING);
			query.addScalar("desc1", Hibernate.STRING);
			query.addScalar("trackingNumber", Hibernate.STRING);
			List results = query.list();
			if (results.isEmpty()) {
				return toReturn;
			}
			
			toReturn = getRowsFromResultList(results, srDto.getDateFormat());
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
		
		return toReturn;
	}
	
	private String getSqlFromDto(StatReportDTO srDto) {
		String sql = "";
		String dateName = "";
		String tableName = "";
		String idName = "";
		if (srDto.getType() == TracingConstants.LF_TYPE_LOST) {
			dateName = "openDate";
			tableName = "lflost";
			idName = "lost_id";
		} else if (srDto.getType() == TracingConstants.LF_TYPE_FOUND) {
			dateName = "foundDate";
			tableName = "lffound";
			idName = "found_id";
		} else {
			return "";
		}
		sql = "select distinct lf.id,lf." + dateName + " as \'lfDate\',s.stationcode,st.description as \'desc\',st1.description as \'desc1\',i.trackingNumber from " + tableName + " lf " +
			  "join station s on lf.station_ID = s.station_ID join status st on lf.status_ID = st.status_ID " +
			  "join lfitem i on lf.id = i." + idName + " join status st1 on i.disposition_status_ID = st1.Status_ID where 1 = 1";

		String startDate = DateUtils.formatDate(srDto.getStarttime(), srDto.getDateFormat(), TracingConstants.DB_DATEFORMAT, null, null);
		String endDate = DateUtils.formatDate(srDto.getEndtime(), srDto.getDateFormat(), TracingConstants.DB_DATEFORMAT, null, null);
		
		sql += " and lf." + dateName + " between \'" + startDate + "\' and \'" + endDate + "\'";
		
		String stationId = srDto.getStation_ID()[0];
		if (stationId != null && !stationId.equals("0")) {
			sql += " and s.stationcode = \'" + stationId + "\'";
		}
		
		if (srDto.getStatus_ID() > 0) {
			sql += " and st.status_ID = " + srDto.getStatus_ID();
		}
		
		if (srDto.getDispositionId() > 0) {
			sql += " and i.disposition_status_ID = " + srDto.getDispositionId();
		}
		
		sql += " order by lf." + dateName + " desc";
		
		return sql;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private ArrayList getRowsFromResultList(List results, String dateFormat) {
		ArrayList toReturn = new ArrayList();
		LostFoundJasperReportRow lfRow;
		Object[] row;
		for (int i = 0; i < results.size(); ++i) {
			row = (Object[]) results.get(i);
			lfRow = new LostFoundJasperReportRow();
			
			lfRow.setId(((Long) row[ID]).longValue());
			lfRow.setDate(DateUtils.formatDate(((Timestamp) row[DATE]).toString(), TracingConstants.DB_DATEFORMAT, dateFormat, TracingConstants.DEFAULT_LOCALE, null));
			lfRow.setStation((String) row[STATION]);
			lfRow.setStatus((String) row[STATUS]);
			lfRow.setDisposition((String) row[DISPOSITION]);
			lfRow.setTrackingNumber((String) row[TRACKING_NUMBER]);
			
			toReturn.add(lfRow);
		}
		return toReturn;
	}

}
