package com.bagnet.nettracer.reporting.lf;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.dto.StatReportDTO;

public class LFDailyStatusReport extends LFReport {

	static {
		logger = Logger.getLogger(LFDailyStatusReport.class);
	}
	
	private final int RECEIVED_DATE = 0;
	private final int STATION_CODE = 1;
	private final int HVIR_COUNT = 2;
	private final int LVIR_COUNT = 3;
	private final int HVIRWR_COUNT = 4;
	private final int LVIRWR_COUNT = 5;
	private final int HVIRWOR_COUNT = 6;
	private final int LVIRWOR_COUNT = 7;

	public LFDailyStatusReport(ResourceBundle resources) {
		super(resources);
	}

	@Override
	protected String getSqlString(StatReportDTO srDto) {
		
		String sql = "select ifnull(date(lf.receivedDate),'') as 'received_date',s.stationcode,ifnull(hvir.count, 0) as 'hvir_count', " +
					 "ifnull(lvir.count, 0) as 'lvir_count',ifnull(hvirwr.count, 0) as 'hvirwr_count', " +
					 "ifnull(lvirwr.count, 0) as 'lvirwr_count',ifnull(hvirwor.count, 0) as 'hvirwor_count', " +
					 "ifnull(lvirwor.count, 0) as 'lvirwor_count' from station s " +
					 "left outer join lffound lf on s.station_id = lf.station_id " +
					 "left outer join (select s.station_id,count(i.id) as 'count' from station s " +
                        			  "left outer join lffound lf on s.station_id = lf.station_id " +
                        			  "left outer join lfitem i on lf.id = i.found_id and i.type = " + TracingConstants.LF_TYPE_FOUND + " " + 
                        			  "where lf.receivedDate between :startDate and :endDate and i.value = " + TracingConstants.LFC_ITEM_HIGH_VALUE + " " +
                        			  "group by date(lf.receivedDate) order by date(lf.receivedDate),s.station_id) hvir on s.station_id = hvir.station_id " +
					 "left outer join (select s.station_id,count(i.id) as 'count' from station s " +
					 				  "left outer join lffound lf on s.station_id = lf.station_id " +
					 				  "left outer join lfitem i on lf.id = i.found_id and i.type = " + TracingConstants.LF_TYPE_FOUND + " " +
					 				  "where lf.receivedDate between :startDate and :endDate and i.value = " + TracingConstants.LFC_ITEM_LOW_VALUE + " " +
					 				  "group by date(lf.receivedDate) order by date(lf.receivedDate),s.station_id) lvir on s.station_id = lvir.station_id " + 
					 "left outer join (select s.station_id,count(i.id) as 'count' from station s " +
					                  "left outer join lffound lf on s.station_id = lf.station_id " +
					                  "left outer join lfitem i on lf.id = i.found_id and i.type = " + TracingConstants.LF_TYPE_FOUND + " " +
				                      "where lf.receivedDate between :startDate and :endDate and i.value = " + TracingConstants.LFC_ITEM_HIGH_VALUE + " " +
				                      "and lf.status_id = " + TracingConstants.LF_STATUS_CLOSED + " " + 
				                      "and (i.disposition_status_id in (" + TracingConstants.LF_DISPOSITION_DELIVERED + "," + TracingConstants.LF_DISPOSITION_PICKED_UP + ") or i.deliveryRejected = 1) " +
				                      "and i.lost_id is not null " + 
				                      "group by date(lf.receivedDate) order by date(lf.receivedDate),s.station_id) hvirwr on s.station_id = hvirwr.station_id " +
				     "left outer join (select s.station_id,count(i.id) as 'count' from station s " +
					                  "left outer join lffound lf on s.station_id = lf.station_id " +
					                  "left outer join lfitem i on lf.id = i.found_id and i.type = " + TracingConstants.LF_TYPE_FOUND + " " +
					                  "where lf.receivedDate between :startDate and :endDate and i.value = " + TracingConstants.LFC_ITEM_LOW_VALUE + " " +
					                  "and lf.status_id = " + TracingConstants.LF_STATUS_CLOSED + " " +
					                  "and (i.disposition_status_id in (" + TracingConstants.LF_DISPOSITION_DELIVERED + "," + TracingConstants.LF_DISPOSITION_PICKED_UP + ") or i.deliveryRejected = 1) " +
					                  "and i.lost_id is not null " +
					                  "group by date(lf.receivedDate) order by date(lf.receivedDate),s.station_id) lvirwr on s.station_id = lvirwr.station_id " +
					 "left outer join (select s.station_id,count(i.id) as 'count' from station s " +
					                  "left outer join lffound lf on s.station_id = lf.station_id " +
					                  "left outer join lfitem i on lf.id = i.found_id and i.type = " + TracingConstants.LF_TYPE_FOUND + " " +
					                  "where lf.receivedDate between :startDate and :endDate and i.value = " + TracingConstants.LFC_ITEM_HIGH_VALUE + " " +
					                  "and lf.status_id = " + TracingConstants.LF_STATUS_CLOSED + " " +
					                  "and (i.disposition_status_id in (" + TracingConstants.LF_DISPOSITION_DELIVERED + "," + TracingConstants.LF_DISPOSITION_PICKED_UP + ") or i.deliveryRejected = 1) " +
					                  "and i.lost_id is null " +
					                  "group by date(lf.receivedDate) order by date(lf.receivedDate),s.station_id) hvirwor on s.station_id = hvirwor.station_id " +
					 "left outer join (select s.station_id,count(i.id) as 'count' from station s " +
					                  "left outer join lffound lf on s.station_id = lf.station_id " +
					                  "left outer join lfitem i on lf.id = i.found_id and i.type = " + TracingConstants.LF_TYPE_FOUND + " " +
					                  "where lf.receivedDate between :startDate and :endDate and i.value = " + TracingConstants.LFC_ITEM_LOW_VALUE + " " +
					                  "and lf.status_id = " + TracingConstants.LF_STATUS_CLOSED + " " + 
					                  "and (i.disposition_status_id in (" + TracingConstants.LF_DISPOSITION_DELIVERED + "," + TracingConstants.LF_DISPOSITION_PICKED_UP + ") or i.deliveryRejected = 1) " +
					                  "and i.lost_id is null " +
					                  "group by date(lf.receivedDate) order by date(lf.receivedDate),s.station_id) lvirwor on s.station_id = lvirwor.station_id " +
					 getStationSql(srDto) + 
					 "group by date(lf.receivedDate),s.stationcode " +
					 "order by s.stationcode,date(lf.receivedDate);";
		
		return sql;
	}

	protected void setQueryArgumentsAndScalars(SQLQuery query) {
		
		query.setDate("startDate", startDate);
		query.setDate("endDate", endDate);
		
		query.addScalar("received_date", Hibernate.STRING);
		query.addScalar("stationcode", Hibernate.STRING);
		query.addScalar("hvir_count", Hibernate.INTEGER);
		query.addScalar("lvir_count", Hibernate.INTEGER);
		query.addScalar("hvirwr_count", Hibernate.INTEGER);
		query.addScalar("lvirwr_count", Hibernate.INTEGER);
		query.addScalar("hvirwor_count", Hibernate.INTEGER);
		query.addScalar("lvirwor_count", Hibernate.INTEGER);
		
	}
	
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected List getRowsFromQuery(List raw) {
		List toReturn = new ArrayList();
		LFDailyStatusReportRow currentRow;
		for (int i = 0; i < raw.size(); ++i) {
			currentRow = new LFDailyStatusReportRow();
			Object[] data = (Object[]) raw.get(i);
			
			currentRow.setReceivedDate((String) data[RECEIVED_DATE]);
			currentRow.setStationCode((String) data[STATION_CODE]);
			currentRow.setHvir((Integer) data[HVIR_COUNT]);
			currentRow.setLvir((Integer) data[LVIR_COUNT]);
			currentRow.setHvirwr((Integer) data[HVIRWR_COUNT]);
			currentRow.setLvirwr((Integer) data[LVIRWR_COUNT]);
			currentRow.setHvirwor((Integer) data[HVIRWOR_COUNT]);
			currentRow.setLvirwor((Integer) data[LVIRWOR_COUNT]);
			
			toReturn.add(currentRow);
		}
		return toReturn;
	}
	
}
