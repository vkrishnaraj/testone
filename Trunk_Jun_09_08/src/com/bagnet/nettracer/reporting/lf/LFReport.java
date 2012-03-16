package com.bagnet.nettracer.reporting.lf;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.classic.Session;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.dto.StatReportDTO;
import com.bagnet.nettracer.tracing.utils.DateUtils;

public abstract class LFReport {
	
	protected static Logger logger;
	
	protected ResourceBundle resources;
	
	protected Date startDate;
	protected Date endDate;
	
	protected LFReport(ResourceBundle resources) {
		this.resources = resources;
	}
	
	@SuppressWarnings("rawtypes")
	public List getData(StatReportDTO srDto) {
		getDatesFromDto(srDto);
		String sql = getSqlString(srDto);
		List raw = getDataFromDb(sql);
		if (raw == null || raw.isEmpty()) {
			return null;
		}
		List rows = getRowsFromQuery(raw);
		return rows;
	}
	
	protected void getDatesFromDto(StatReportDTO srDto) {
		Calendar start = new GregorianCalendar();
		start.setTime(DateUtils.convertToDate(srDto.getStarttime(), srDto.getDateFormat(), null));
		
		String endDateString = srDto.getEndtime();
		if (endDateString == null || endDateString.isEmpty()) {
			endDateString = srDto.getStarttime();
		}
		
		Calendar end = new GregorianCalendar();
		end.setTime(DateUtils.convertToDate(endDateString, srDto.getDateFormat(), null));
		end.add(Calendar.DAY_OF_MONTH, 1);
		
		startDate = start.getTime();
		endDate = end.getTime();
		
	}
	
	@SuppressWarnings("rawtypes")
	protected List getDataFromDb(String sql) {
		
		List toReturn = null;
		Session session = null;

		try { 
			session = HibernateWrapper.getSession().openSession();
			SQLQuery query = session.createSQLQuery(sql);
			
			setQueryArgumentsAndScalars(query);
			
			List results = query.list();
			if (!results.isEmpty()) {
				toReturn = results;
			}
		
		} catch (Exception e) {
			logger.error(e, e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		
		return toReturn;
	}
	
	protected String getStationSql(StatReportDTO srDto) {
		String stationSql = "";
		String stationId = srDto.getStation_ID()[0];
		if (stationId != null && !stationId.equals("0")) {
			stationSql += "and s.stationcode = '" + stationId + "' ";
		}
		stationSql += "and s.companycode_ID = '" + TracingConstants.LF_LF_COMPANY_ID + "' ";
		return stationSql;
	}
	
	protected abstract String getSqlString(StatReportDTO srDto);

	protected abstract void setQueryArgumentsAndScalars(SQLQuery query);

	@SuppressWarnings("rawtypes")
	protected abstract List getRowsFromQuery(List raw);

}
