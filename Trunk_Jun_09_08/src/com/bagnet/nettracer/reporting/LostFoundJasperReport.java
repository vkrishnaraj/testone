package com.bagnet.nettracer.reporting;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.classic.Session;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.dto.StatReportDTO;
import com.bagnet.nettracer.tracing.utils.DateUtils;

public class LostFoundJasperReport {

	private static final Logger logger = Logger
			.getLogger(LostFoundJasperReport.class);

	private final int ID = 0;
	private final int DATE = 1;
	private final int STATION = 2;
	private final int STATUS = 3;
	private final int DISPOSITION = 4;
	private final int TRACKING_NUMBER = 5;
	private final int DROPOFF = 6;
	
	private final int IR_STATION = 0;
	private final int IR_COMPANY = 1;
	private final int IR_STATUS = 2;
	private final int IR_DISPOSITION = 3;
	private final int IR_ITEM_COUNT = 4;
	
	private final int IR_MATCHED_COUNT = 2;
	private final int IR_TO_BE_SALVAGED_COUNT = 2;
	
	private final int MS_STATION = 0;
	private final int MS_LOST_COUNT = 1;
	private final int MS_FOUND_COUNT = 2;
	private final int MS_MATCHED_COUNT = 3;
	private final int MS_SALVAGED_COUNT = 4;
	private final int MS_NOT_MATCHED_COUNT = 5;
	
	private final int ITEMIZ_ID = 0;
	private final int ITEMIZ_STATION = 1;
	private final int ITEMIZ_DATE = 2;
	private final int ITEMIZ_STATUS = 3;
	private final int ITEMIZ_DISPOSITION = 4;
	private final int ITEMIZ_CATEGORY = 5;
	private final int ITEMIZ_SUB_CATEGORY = 6;
	private final int ITEMIZ_BRAND = 7;
	private final int ITEMIZ_DESCRIPTION = 8;
	
	
	
	@SuppressWarnings("rawtypes")
	public List getReportData(StatReportDTO srDto) {
		ArrayList toReturn = null;
		Session session = null;
		try {
			session = HibernateWrapper.getSession().openSession();
			boolean isLost = srDto.getType() == TracingConstants.LF_TYPE_LOST;
			SQLQuery query = session.createSQLQuery(getSqlFromDto(srDto));
			query.addScalar("id", Hibernate.LONG);
			query.addScalar("lfDate", Hibernate.TIMESTAMP);
			query.addScalar("stationcode", Hibernate.STRING);
			query.addScalar("desc", Hibernate.STRING);
			query.addScalar("desc1", Hibernate.STRING);
			query.addScalar("trackingNumber", Hibernate.STRING);
			if (isLost) {
				query.addScalar("dropoff", Hibernate.STRING);
			}
			List results = query.list();
			if (results.isEmpty()) {
				return toReturn;
			}

			toReturn = getRowsFromResultList(results, srDto.getDateFormat(),
					isLost);
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
		boolean isLost = true;
		if (srDto.getType() == TracingConstants.LF_TYPE_LOST) {
			dateName = "openDate";
			tableName = "lflost";
			idName = "lost_id";
		} else if (srDto.getType() == TracingConstants.LF_TYPE_FOUND) {
			dateName = "foundDate";
			tableName = "lffound";
			idName = "found_id";
			isLost = false;
		} else {
			return "";
		}
		sql = "select distinct lf.id,lf."
				+ dateName
				+ " as \'lfDate\',s.stationcode,st.description as \'desc\',st1.description as \'desc1\',i.trackingNumber"
				+ (isLost ? ",ds.stationcode as \'dropoff\'" : "")
				+ " from "
				+ tableName
				+ " lf "
				+ "join station s on lf.station_ID = s.station_ID join status st on lf.status_ID = st.status_ID "
				+ (isLost ? "join lfreservation r on lf.reservation_id = r.id join station ds on r.dropofflocation_station_ID = ds.station_ID "
						: "")
				+ "join lfitem i on lf.id = i."
				+ idName
				+ " join status st1 on i.disposition_status_ID = st1.Status_ID where 1 = 1";

		Calendar calendarStart = new GregorianCalendar();
		calendarStart.setTime(DateUtils.convertToDate(srDto.getStarttime(),
				srDto.getDateFormat(), null));
		calendarStart.add(Calendar.DAY_OF_MONTH, -1);

		Calendar calendarEnd = new GregorianCalendar();
		calendarEnd.setTime(DateUtils.convertToDate(srDto.getEndtime(),
				srDto.getDateFormat(), null));
		calendarEnd.add(Calendar.DAY_OF_MONTH, 1);

		String startDate = DateUtils.formatDate(calendarStart.getTime(),
				TracingConstants.DB_DATEFORMAT, null, null);
		String endDate = DateUtils.formatDate(calendarEnd.getTime(),
				TracingConstants.DB_DATEFORMAT, null, null);

		sql += " and lf." + dateName + " between \'" + startDate + "\' and \'"
				+ endDate + "\'";

		String stationId = srDto.getStation_ID()[0];
		if (stationId != null && !stationId.equals("0")) {
			sql += " and s.stationcode = \'" + stationId + "\'";
		}

		if (isLost) {
			String dropOffStation = srDto.getStationCode();
			if (dropOffStation != null && !dropOffStation.equals("0")) {
				sql += " and ds.stationcode = \'" + dropOffStation + "\'";
			}
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
	private ArrayList getRowsFromResultList(List results, String dateFormat,
			boolean isLost) {
		ArrayList toReturn = new ArrayList();
		LostFoundJasperReportRow lfRow;
		Object[] row;
		for (int i = 0; i < results.size(); ++i) {
			row = (Object[]) results.get(i);
			lfRow = new LostFoundJasperReportRow();

			lfRow.setId(((Long) row[ID]).longValue());
			lfRow.setDate(DateUtils.formatDate(
					((Timestamp) row[DATE]).toString(),
					TracingConstants.DB_DATEFORMAT, dateFormat,
					TracingConstants.DEFAULT_LOCALE, null));
			lfRow.setStation((String) row[STATION]);
			lfRow.setStatus((String) row[STATUS]);
			lfRow.setDisposition((String) row[DISPOSITION]);
			lfRow.setTrackingNumber((String) row[TRACKING_NUMBER]);
			if (isLost) {
				lfRow.setDropoff((String) row[DROPOFF]);
			}

			toReturn.add(lfRow);
		}
		return toReturn;
	}

	@SuppressWarnings("rawtypes")
	public List getItemRecoveryReportData(StatReportDTO srDto) {
		ArrayList toReturn = null;
		Session session = null;
		boolean isLost = srDto.getType() == TracingConstants.LF_TYPE_LOST;
		try {
			session = HibernateWrapper.getSession().openSession();

			SQLQuery query = session.createSQLQuery(getItemRecoverySqlFromDto(srDto));
			query.addScalar("stationcode", Hibernate.STRING);
			query.addScalar("companyId", Hibernate.STRING);
			query.addScalar("status_id", Hibernate.LONG);
			query.addScalar("disposition_status_id", Hibernate.LONG);
			query.addScalar("item_count", Hibernate.INTEGER);
			
			List results = query.list();
			if (results.isEmpty()) {
				return toReturn;
			}
			
			query = session.createSQLQuery(getMatchedByNtSql(srDto));
			query.addScalar("stationcode", Hibernate.STRING);
			query.addScalar("companyId", Hibernate.STRING);
			query.addScalar("matchedCount", Hibernate.INTEGER);
		
			List results1 = query.list();
			if (results1.isEmpty()) {
				return toReturn;
			}
			
			List toBeSalvaged = null;
			if (!isLost) {
				query = session.createSQLQuery(getItemsToBeSalvagedSql(srDto));
				query.addScalar("stationcode", Hibernate.STRING);
				query.addScalar("companyId", Hibernate.STRING);
				query.addScalar("toBeSalvagedCount", Hibernate.INTEGER);
				
				toBeSalvaged = query.list();
			}

			toReturn = getItemRecoveryRowsFromResultList(isLost, results, results1, toBeSalvaged);
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return toReturn;
	}

	private String getItemRecoverySqlFromDto(StatReportDTO srDto) {
		int itemType;
		String typeId;
		boolean isLost = srDto.getType() == TracingConstants.LF_TYPE_LOST;

		String sql = "select s.stationcode,lf.companyId,lf.status_id,i.disposition_status_id,count(lf.id) as 'item_count' from station s ";
		
		if (isLost) {
			itemType = TracingConstants.LF_TYPE_LOST;
			typeId = "lost_id";
			sql += "left outer join lfreservation r on r.dropofflocation_station_id = s.station_id " +
				   "left outer join lflost lf on lf.reservation_id = r.id ";
		} else {
			itemType = TracingConstants.LF_TYPE_FOUND;
			typeId = "found_id";
			sql += "left outer join lffound lf on lf.station_id = s.station_id ";
		}
		
		sql += "left outer join lfitem i on lf.id = i." + typeId + " and i.type = " + itemType + " ";
		sql += "where s.associated_airport in ('ABG','AVS','BGT') ";
						
		sql += getDateSql(srDto);
		
		String stationId = srDto.getStation_ID()[0];
		if (stationId != null && !stationId.equals("0")) {
			sql += "and s.stationcode = \'" + stationId + "\' ";
		}
		
		sql += "group by s.stationcode,lf.companyId,lf.status_id,i.disposition_status_id " +
				"order by s.stationcode,lf.companyId,lf.status_id,i.disposition_status_id;";
		
		return sql;
	}
	
	private String getMatchedByNtSql(StatReportDTO srDto) {
		boolean isLost = srDto.getType() == TracingConstants.LF_TYPE_LOST;
		String idName = "";
		if (isLost) {
			idName = "lost_id";
		} else {
			idName = "found_id";
		}
		
		String sql = "select s.stationcode,lf.companyId, count(distinct mh1.id) as \'matchedCount\' from station s ";
		if (isLost) {
			sql += "left outer join lfreservation r on r.dropofflocation_station_id = s.station_id " +
				   "left outer join lflost lf on lf.reservation_id = r.id ";
		} else {
			sql += "left outer join lffound lf on lf.station_id = s.station_id ";
		}
		
		sql += "left outer join lfmatchhistory mh1 on (lf.id = mh1." + idName + ") " + 
			   "join lfmatchhistory mh2 on (mh1.lost_id = mh2.lost_id and mh1.found_id = mh2.found_id) " +
			   "where mh1.status_status_id = 608 and mh2.score > 0 " +
			   "and s.associated_airport in ('ABG','AVS','BGT') ";
		
		sql += getDateSql(srDto);
		
		return sql;
	}
	
	private String getItemsToBeSalvagedSql(StatReportDTO srDto) {
		String sql = "select s.stationcode,lf.companyId,count(lf.id) as \'toBeSalvagedCount\' from station s " +
					 "left outer join lffound lf on s.station_id = lf.station_id " +
					 "left outer join lfitem i on lf.id = i.found_id and i.type = " + TracingConstants.LF_TYPE_FOUND + " " +
					 "where s.associated_airport in ('ABG','AVS','BGT') " +
					 "and lf.status_id = 600 and i.disposition_status_id = " + TracingConstants.LF_DISPOSITION_SALVAGED + " " +
					 "and datediff(now(), lf.foundDate) > 30 ";
		
		sql += getDateSql(srDto);
		
		String stationId = srDto.getStation_ID()[0];
		if (stationId != null && !stationId.equals("0")) {
			sql += "and s.stationcode = \'" + stationId + "\' ";
		}
		
		sql += getDateSql(srDto);
		sql += "group by s.stationcode,lf.companyId;";
		
		return sql;
	}
	
	private String getDateSql(StatReportDTO srDto) {
		String sql = "";
		
		Calendar calendarStart = new GregorianCalendar();
		calendarStart.setTime(DateUtils.convertToDate(srDto.getStarttime(), srDto.getDateFormat(), null));
		calendarStart.add(Calendar.DAY_OF_MONTH, -1);
		
		String endDateString = srDto.getEndtime();
		if (endDateString == null || endDateString.isEmpty()) {
			endDateString = srDto.getStarttime();
		}
		
		Calendar calendarEnd = new GregorianCalendar();
		calendarEnd.setTime(DateUtils.convertToDate(endDateString, srDto.getDateFormat(), null));
		calendarEnd.add(Calendar.DAY_OF_MONTH, 1);
		
		String startDate = DateUtils.formatDate(calendarStart.getTime(), TracingConstants.DB_DATEFORMAT, null, null);
		String endDate = DateUtils.formatDate(calendarEnd.getTime(), TracingConstants.DB_DATEFORMAT, null, null);

		if (srDto.getType() == TracingConstants.LF_TYPE_LOST) {
			sql += "and ((lf.openDate between \'" + startDate + "\' and \'" + endDate + "\') or (lf.closeDate between \'" + startDate + "\' and \'" + endDate + "\')) ";
		} else {
			sql += "and lf.foundDate between \'" + startDate + "\' and \'" + endDate + "\' ";
		}
		
		return sql;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private ArrayList getItemRecoveryRowsFromResultList(boolean isLost, List results, List results1, List toBeSalvaged) {
		LinkedHashMap<String, LostFoundItemRecoveryRow> processedRows = new LinkedHashMap<String, LostFoundItemRecoveryRow>();
		LostFoundItemRecoveryRow lfRow;
		Object[] row;
		
		for (int i = 0; i < results.size(); ++i) {
			row = (Object[]) results.get(i);
			String key = (String) row[IR_STATION] + "/" + (String) row[IR_COMPANY];
			if (processedRows.containsKey(key)) {
				lfRow = processedRows.get(key);
			} else {		
				lfRow = new LostFoundItemRecoveryRow();
				lfRow.setStation((String) row[IR_STATION]);
				
				String companyId = (String) row[IR_COMPANY];
				String companyName;
				if (companyId.equals("AVS")) {
					companyName = "Avis";
				} else if (companyId.equals("BGT")) {
					companyName = "Budget";
				} else {
					companyName = "Avis Budget Group";
				}
				lfRow.setCompany(companyName);
				
				processedRows.put(key, lfRow);
			}
			
			long status = (Long) row[IR_STATUS];
			long disposition = (Long) row[IR_DISPOSITION];
			int itemCount = (Integer) row[IR_ITEM_COUNT];
			
			if (status == TracingConstants.LF_STATUS_OPEN) {
				lfRow.addOpenItems(itemCount);
				lfRow.addItemsReported(itemCount);
			} else {
				lfRow.addClosed(itemCount);
			}
			lfRow = addDispositionCount(lfRow, status, disposition, itemCount);
			
		}

		for (int i = 0; i < results1.size(); ++i) {
			row = (Object[]) results1.get(i);
			String key = (String) row[IR_STATION] + "/" + (String) row[IR_COMPANY];
			if (!processedRows.containsKey(key)) {
				break;
			}
			
			lfRow = processedRows.get(key);
			if (lfRow == null) {
				return null;
			}
			lfRow.setClosedMatchedByNt((Integer) row[IR_MATCHED_COUNT]);
		}
		
		if (isLost) {
			for (LostFoundItemRecoveryRow r: processedRows.values()) {
				r.calculateAndSetReturnRate();
			}
		} else {
			for (int i = 0; i < toBeSalvaged.size(); ++i) {
				row = (Object[]) toBeSalvaged.get(i);
				String key = (String) row[IR_STATION] + "/" + (String) row[IR_COMPANY];
				if (!processedRows.containsKey(key)) {
					continue;
				}
				
				lfRow = processedRows.get(key);
				if (lfRow == null) {
					return null;
				}
				lfRow.setToBeSalvaged((Integer) row[IR_TO_BE_SALVAGED_COUNT]);
			}
			
			for (LostFoundItemRecoveryRow r: processedRows.values()) {
				r.calculateAndSetSalvagedRate();
			}
		}
		
		return new ArrayList(processedRows.values());
	}
	
	private LostFoundItemRecoveryRow addDispositionCount(LostFoundItemRecoveryRow lfRow, long status, long disposition, int itemCount) {
		
		if (status == TracingConstants.LF_STATUS_OPEN) {
			if (disposition == TracingConstants.LF_DISPOSITION_OTHER) {
				lfRow.addOpenNotMatched(itemCount);
			} else if (disposition == TracingConstants.LF_DISPOSITION_TO_BE_DELIVERED) {
				lfRow.addMatchedPendingAction(itemCount);
			} else {
				lfRow.addClosedOther(itemCount);
			}
		} else {
			if (disposition == TracingConstants.LF_DISPOSITION_DELIVERED) {
				lfRow.addDelivered(itemCount);
			} else if (disposition == TracingConstants.LF_DISPOSITION_PICKED_UP) {
				lfRow.addPickedUpByCustomer(itemCount);
			} else if (disposition == TracingConstants.LF_DISPOSITION_SALVAGED) {
				lfRow.addSalvaged(itemCount);
			} else {
				lfRow.addClosedOther(itemCount);
			}
		}

		return lfRow;
	}
	
	@SuppressWarnings("rawtypes")
	public List getManagementSummaryData(StatReportDTO srDto) {
		ArrayList toReturn = null;
		Session session = null;
		try {
			session = HibernateWrapper.getSession().openSession();

			SQLQuery query = session.createSQLQuery(getManagementSummarySqlFromDto(srDto));
			query.addScalar("stationcode", Hibernate.STRING);
			query.addScalar("lost_count", Hibernate.INTEGER);
			query.addScalar("found_count", Hibernate.INTEGER);
			query.addScalar("matched_count", Hibernate.INTEGER);
			query.addScalar("salvaged_count", Hibernate.INTEGER);
			query.addScalar("not_matched_count", Hibernate.INTEGER);
			
			List results = query.list();
			if (results.isEmpty()) {
				return toReturn;
			}
			
			toReturn = getManagementSummaryRowsFromResults(results);
		} catch (Exception e) {
			logger.error(e, e);
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return toReturn;
	}
	
	private String getManagementSummarySqlFromDto(StatReportDTO srDto) {
		
		Calendar calendarStart = new GregorianCalendar();
		calendarStart.setTime(DateUtils.convertToDate(srDto.getStarttime(), srDto.getDateFormat(), null));
		calendarStart.add(Calendar.DAY_OF_MONTH, -1);
		
		String endDateString = srDto.getEndtime();
		if (endDateString == null || endDateString.isEmpty()) {
			endDateString = srDto.getStarttime();
		}
		
		Calendar calendarEnd = new GregorianCalendar();
		calendarEnd.setTime(DateUtils.convertToDate(endDateString, srDto.getDateFormat(), null));
		calendarEnd.add(Calendar.DAY_OF_MONTH, 1);
		
		String startDate = DateUtils.formatDate(calendarStart.getTime(), TracingConstants.DB_DATEFORMAT, null, null);
		String endDate = DateUtils.formatDate(calendarEnd.getTime(), TracingConstants.DB_DATEFORMAT, null, null);

		String stationSql = "";
		String stationId = srDto.getStation_ID()[0];
		if (stationId != null && !stationId.equals("0")) {
			stationSql += "and s.stationcode = \'" + stationId + "\' ";
		}
		
		
		String sql = "select s.stationcode,ifnull(lost.count, 0) as 'lost_count',ifnull(found.count, 0) as 'found_count',ifnull(matched.count, 0) as 'matched_count',ifnull(salvaged.count, 0) as 'salvaged_count',ifnull(notmatched.count, 0) as 'not_matched_count' from station s " +
					 "left outer join (select s.station_id,count(i1.id) as 'count' from station s " +
					 				  "left outer join lfreservation r on s.station_id = r.dropoffLocation_station_id " +
					 				  "left outer join lflost lf on r.id = lf.reservation_id " +
					 				  "left outer join lfitem i1 on lf.id = i1.lost_id and i1.type = " + TracingConstants.LF_TYPE_LOST + " " +
					 				  "where 1 = 1 and lf.openDate between \'" + startDate + "\' and \'" + endDate + "\' " + stationSql +
					 				  "group by s.station_id) lost on s.station_id = lost.station_id " +
					 "left outer join (select s.station_id,count(i1.id) as 'count' from station s " +
					 				  "left outer join lffound lf on s.station_id = lf.station_id " +
					 				  "left outer join lfitem i1 on lf.id = i1.found_id and i1.type = " + TracingConstants.LF_TYPE_FOUND + " " +
					 				  "where 1 = 1 and lf.foundDate between \'" + startDate + "\' and \'" + endDate + "\' " + stationSql +
					 				  "group by s.station_id) found on s.station_id = found.station_id " +
					 "left outer join (select s.station_id,count(distinct mh1.id) as 'count' from station s " +
					 				  "left outer join lffound lf on lf.station_id = s.station_id " +
					 				  "left outer join lfmatchhistory mh1 on (lf.id = mh1.found_id) join lfmatchhistory mh2 on (mh1.lost_id = mh2.lost_id and mh1.found_id = mh2.found_id) " +
					 				  "left outer join lfitem i on lf.id = i.found_id and i.type = " + TracingConstants.LF_TYPE_FOUND + " " +
					 				  "where mh1.status_status_id = 608 and mh2.score > 0 " +
					 				  "and i.disposition_status_id in (603,604) " + 
					 				  "and lf.foundDate between \'" + startDate + "\' and \'" + endDate + "\' " + stationSql +
					 				  "group by s.station_id) matched on s.station_id = matched.station_id " +
					 "left outer join (select s.station_id,count(i.id) as 'count' from station s " +
					 				  "left outer join lffound lf on s.station_id = lf.station_id " +
					 				  "left outer join lfitem i on lf.id = i.found_id and i.type = " + TracingConstants.LF_TYPE_FOUND + " " +
					 				  "where lf.status_id = 601 and i.disposition_status_id = 605 " + 
					 				  "and lf.foundDate between \'" + startDate + "\' and \'" + endDate + "\' " + stationSql +
					 				  "group by s.station_id) salvaged on s.station_id = salvaged.station_id " +
		             "left outer join (select s.station_id,count(i.id) as 'count' from station s " +
		             	 			  "left outer join lffound lf on s.station_id = lf.station_id " +
		             	 			  "left outer join lfitem i on lf.id = i.found_id and i.type = " + TracingConstants.LF_TYPE_FOUND + " " +
		             	 			  "where 1 = 1 and i.disposition_status_id in (603,604) " + 
		             	 			  "and lf.foundDate between \'" + startDate + "\' and \'" + endDate + "\' " + stationSql +
		             	 			  "and not exists (select 'x' from lfmatchhistory m where m.found_id = i.found_id) " +
		             	 			  "group by s.station_id) notmatched on s.station_id = notmatched.station_id " +
		             "where s.associated_airport in ('ABG','AVS','BGT');";
		
		return sql;
	}
	
	@SuppressWarnings("rawtypes")
	private ArrayList<LFManagementSummaryRow> getManagementSummaryRowsFromResults(List results) {
		ArrayList<LFManagementSummaryRow> toReturn = new ArrayList<LFManagementSummaryRow>();
		Object[] row;
		LFManagementSummaryRow lfmsr;
		LFManagementSummaryRow total = new LFManagementSummaryRow();
		total.setStation("Total");
		for (int i = 0; i < results.size(); ++i) {
			row = (Object[]) results.get(i);
			lfmsr = new LFManagementSummaryRow();
			lfmsr.setStation((String) row[MS_STATION]);
			
			lfmsr.setReportedLost((Integer) row[MS_LOST_COUNT]);
			total.addReportedLost((Integer) row[MS_LOST_COUNT]);
			
			lfmsr.setFoundItems((Integer) row[MS_FOUND_COUNT]);
			total.addFoundItems((Integer) row[MS_FOUND_COUNT]);
			
			lfmsr.setMatchedAndReturned((Integer) row[MS_MATCHED_COUNT]);
			total.addMatchedAndReturned((Integer) row[MS_MATCHED_COUNT]);
			
			lfmsr.setSalvaged((Integer) row[MS_SALVAGED_COUNT]);
			total.addSalvaged((Integer) row[MS_SALVAGED_COUNT]);
			
			lfmsr.setNotMatchedReturned((Integer) row[MS_NOT_MATCHED_COUNT]);
			total.addNotMatchedReturned((Integer) row[MS_NOT_MATCHED_COUNT]);
			
			toReturn.add(lfmsr);
		}
		toReturn.add(total);
		return toReturn;
	}
	
	@SuppressWarnings("rawtypes")
	public List getLfItemizationData(StatReportDTO srDto) {
		ArrayList toReturn = null;
		Session session = null;
		try {
			session = HibernateWrapper.getSession().openSession();

			SQLQuery query = session.createSQLQuery(getItemizationSqlFromDto(srDto));
			query.addScalar("id", Hibernate.LONG);
			query.addScalar("stationcode", Hibernate.STRING);
			query.addScalar("date_reported", Hibernate.STRING);
			query.addScalar("status_id", Hibernate.LONG);
			query.addScalar("disposition_status_id", Hibernate.LONG);
			query.addScalar("category", Hibernate.STRING);
			query.addScalar("subcategory", Hibernate.STRING);
			query.addScalar("brand", Hibernate.STRING);
			query.addScalar("description", Hibernate.STRING);
			
			List results = query.list();
			if (results.isEmpty()) {
				return toReturn;
			}
			
			toReturn = getItemizationRowsFromResults(results);
		} catch (Exception e) {
			logger.error(e, e);
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return toReturn;
	}
	
	private String getItemizationSqlFromDto(StatReportDTO srDto) {
		boolean isLost = srDto.getType() == TracingConstants.LF_TYPE_LOST; 
		String sql = "";
		String idName = "";
		String dateName = "";
		String joinSql = "";
		int type;
		if (isLost) {
			idName = "lost_id";
			dateName = "openDate";
			joinSql = "left outer join lfreservation r on s.station_id = r.dropoffLocation_station_id " +
					  "left outer join lflost lf on r.id = lf.reservation_id ";
			type = TracingConstants.LF_TYPE_LOST;
		} else {
			idName = "found_id";
			dateName = "foundDate";
			joinSql = "left outer join lffound lf on s.station_id = lf.station_id ";
			type = TracingConstants.LF_TYPE_FOUND;
		}
		
		Calendar calendarStart = new GregorianCalendar();
		calendarStart.setTime(DateUtils.convertToDate(srDto.getStarttime(), srDto.getDateFormat(), null));
		calendarStart.add(Calendar.DAY_OF_MONTH, -1);
		
		String endDateString = srDto.getEndtime();
		if (endDateString == null || endDateString.isEmpty()) {
			endDateString = srDto.getStarttime();
		}
		
		Calendar calendarEnd = new GregorianCalendar();
		calendarEnd.setTime(DateUtils.convertToDate(endDateString, srDto.getDateFormat(), null));
		calendarEnd.add(Calendar.DAY_OF_MONTH, 1);
		
		String startDate = DateUtils.formatDate(calendarStart.getTime(), TracingConstants.DB_DATEFORMAT, null, null);
		String endDate = DateUtils.formatDate(calendarEnd.getTime(), TracingConstants.DB_DATEFORMAT, null, null);
		
		String stationSql = "";
		String stationId = srDto.getStation_ID()[0];
		if (stationId != null && !stationId.equals("0")) {
			stationSql += "and s.stationcode = \'" + stationId + "\' ";
		}
		
		sql += "select lf.id,s.stationcode,lf." + dateName + " as 'date_reported',lf.status_id,i.disposition_status_id,c.description as 'category',ifnull(sc.description, '') as 'subcategory',i.brand,i.description " +
			   "from station s " + joinSql +
			   "left outer join lfitem i on lf.id = i." + idName + " and i.type = " + type + " " +
			   "left outer join lfcategory c on i.category = c.id " +
			   "left outer join lfsubcategory sc on i.subcategory = sc.id " +
			   "where s.associated_airport in ('ABG','AVS','BGT') " +
			   "and c.id in (5,6,7,10,14,18,19,24) " +
			   "and lf." + dateName + " between \'" + startDate + "\' and \'" + endDate + "\' " + stationSql +
			   "order by s.stationcode,lf." + dateName + ";";
		
		return sql;
	}
	
	@SuppressWarnings("rawtypes")
	private ArrayList<LFItemizationRow> getItemizationRowsFromResults(List results) {
		ArrayList<LFItemizationRow> toReturn = new ArrayList<LFItemizationRow>();
		Object[] row;
		LFItemizationRow lfir;
		for (int i = 0; i < results.size(); ++i) {
			row = (Object[]) results.get(i);
			lfir = new LFItemizationRow();
			lfir.setId((Long) row[ITEMIZ_ID]);
			lfir.setStation((String) row[ITEMIZ_STATION]);
			lfir.setDate((String) row[ITEMIZ_DATE]);
			lfir.setStatus((Long) row[ITEMIZ_STATUS]);
			lfir.setDisposition((Long) row[ITEMIZ_DISPOSITION]);
			lfir.setCategory((String) row[ITEMIZ_CATEGORY]);
			lfir.setSubCategory((String) row[ITEMIZ_SUB_CATEGORY]);
			lfir.setBrand((String) row[ITEMIZ_BRAND]);
			lfir.setDescription((String) row[ITEMIZ_DESCRIPTION]);
			
			toReturn.add(lfir);
		}
		return toReturn;
	}
	
}
