package com.bagnet.nettracer.reporting;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.classic.Session;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.dto.StatReportDTO;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;

public abstract class AbstractNtJasperReport {
	
	protected static Logger logger;
	
	protected ResourceBundle resources;
	
	protected Date startDate;
	protected Date endDate;
	
	protected Agent user;
	
	protected AbstractNtJasperReport(ResourceBundle resources) {
		this.resources = resources;
	}
	
	protected AbstractNtJasperReport(ResourceBundle resources, Agent agent) {
		this.resources = resources;
		this.user = agent;
	}
	
	@SuppressWarnings("rawtypes")
	public List getData(StatReportDTO srDto) {
		getDatesFromDto(srDto);
		String sql = TracerUtils.isSqlServerClient(srDto.getCompanyCode()) ? getSqlServerSqlString(srDto) : getMySqlSqlString(srDto);
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
//		end.add(Calendar.DAY_OF_MONTH, 1);
		
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
		
		return stationSql;
	}
	
	protected abstract String getMySqlSqlString(StatReportDTO srDto);
	protected abstract String getSqlServerSqlString(StatReportDTO srDto);

	protected abstract void setQueryArgumentsAndScalars(SQLQuery query);

	@SuppressWarnings("rawtypes")
	protected abstract List getRowsFromQuery(List raw);

}
