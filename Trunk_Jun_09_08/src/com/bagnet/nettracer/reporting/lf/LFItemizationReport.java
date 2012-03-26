package com.bagnet.nettracer.reporting.lf;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.dto.StatReportDTO;

public class LFItemizationReport extends LFReport {

	static {
		logger = Logger.getLogger(LFItemizationReport.class);
	}
	
	private final int BARCODE = 0;
	private final int DATE_RECORDED = 1;
	private final int DATE_RECEIVED = 2;
	private final int VALUE = 3;
	private final int RECEIVED_FROM = 4;
	private final int CATEGORY = 5;
	private final int SUB_CATEGORY = 6;
	private final int STATUS = 7;
	private final int DISPOSITION = 8;
	private final int TITLE = 9;
	private final int RETURN_TIME = 10;
	private final int TRACKING_NUMBER = 11;
	private final int RETURNED_WITH_LOST_REPORT = 12;

	public LFItemizationReport(ResourceBundle resources) {
		super(resources);
	}

	@Override
	protected String getSqlString(StatReportDTO srDto) {
		String sql = "select s.stationcode,lf.barcode,date(lf.foundDate) as 'date_recorded',date(lf.receivedDate) as 'date_received', " +
					 "i.value,ifnull(c.description,'') as 'category',ifnull(sc.description,'') as 'subcategory',lf.status_id as 'status', " +
					 "i.disposition_status_id as 'disposition',i.description as 'title',ifnull(datediff(date(lf.deliveredDate), date(lf.receivedDate)),-1) as 'return_time', " + 
					 "ifnull(i.trackingNumber,'') as 'tracking_number', " +
					 "(i.lost_id is null) as 'returned_with_lost_report' from station s " +
					 "left outer join lffound lf on s.station_id = lf.station_id " +
					 "left outer join lfitem i on lf.id = i.found_id and i.type = " + TracingConstants.LF_TYPE_FOUND + " " +
					 "left outer join lfcategory c on i.category = c.id " +
					 "left outer join lfsubcategory sc on i.subCategory = sc.id " +
					 "where lf.receivedDate between :startDate and :endDate " + getStationSql(srDto) + " and lf.receivedDate between :startDate and :endDate " +
					 "order by s.stationcode,lf.receivedDate;";
		return sql;
	}

	@Override
	protected void setQueryArgumentsAndScalars(SQLQuery query) {
		
		query.setDate("startDate", startDate);
		query.setDate("endDate", endDate);
		
		query.addScalar("barcode", Hibernate.STRING);
		query.addScalar("date_recorded", Hibernate.STRING);
		query.addScalar("date_received", Hibernate.STRING);
		query.addScalar("value", Hibernate.INTEGER);
		query.addScalar("stationcode", Hibernate.STRING);
		query.addScalar("category", Hibernate.STRING);
		query.addScalar("subcategory", Hibernate.STRING);
		query.addScalar("status", Hibernate.INTEGER);
		query.addScalar("disposition", Hibernate.INTEGER);
		query.addScalar("title", Hibernate.STRING);
		query.addScalar("return_time", Hibernate.INTEGER);
		query.addScalar("tracking_number", Hibernate.STRING);
		query.addScalar("returned_with_lost_report", Hibernate.INTEGER);

	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected List getRowsFromQuery(List raw) {
		List toReturn = new ArrayList();
		LFItemizationReportRow currentRow;
		for (int i = 0; i < raw.size(); ++i) {
			currentRow = new LFItemizationReportRow();
			Object[] data = (Object[]) raw.get(i);
			
			currentRow.setBarcode((String) data[BARCODE]);
			currentRow.setDateRecorded((String) data[DATE_RECORDED]);
			currentRow.setDateReceived((String) data[DATE_RECEIVED]);
			
			String value = "";
			if (((Integer) data[VALUE]) == 1) {
				value = resources.getString("lf.itemization.value.high");
			} else {
				value = resources.getString("lf.itemization.value.low");
			}
			currentRow.setValue(value);
			
			currentRow.setStationcode((String) data[RECEIVED_FROM]);
			currentRow.setCategory((String) data[CATEGORY]);
			currentRow.setSubCategory((String) data[SUB_CATEGORY]);
			currentRow.setStatus(resources.getString("STATUS_KEY_" + data[STATUS]));
			currentRow.setDisposition(resources.getString("STATUS_KEY_" + data[DISPOSITION]));
			currentRow.setTitle((String) data[TITLE]);		
			currentRow.setTrackingNumber((String) data[TRACKING_NUMBER]);
			currentRow.setReturnTime((Integer) data[RETURN_TIME]);
			
			if (((Integer) data[RETURNED_WITH_LOST_REPORT]) == 1) {
				currentRow.setReturnedWithLostReport(resources.getString("lf.itemization.returned.with.lost.report.yes"));
			} else {
				currentRow.setReturnedWithLostReport(resources.getString("lf.itemization.returned.with.lost.report.no"));
			}
			
			toReturn.add(currentRow);
		}
		return toReturn;
	}

}
