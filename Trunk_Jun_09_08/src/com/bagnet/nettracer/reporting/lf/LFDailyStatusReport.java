package com.bagnet.nettracer.reporting.lf;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.SQLQuery;

import com.bagnet.nettracer.reporting.AbstractNtJasperReport;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.dto.StatReportDTO;

public class LFDailyStatusReport extends AbstractNtJasperReport {

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
	private final int BOX_COUNT = 8;

	public LFDailyStatusReport(ResourceBundle resources) {
		super(resources);
	}

	@Override
	protected String getMySqlSqlString(StatReportDTO srDto) {
		
		String sql = "select ifnull(zz.received_date,'') as 'received_date',s.stationcode,ifnull(hvir.count, 0) as 'hvir_count', " +
					 "ifnull(lvir.count, 0) as 'lvir_count',ifnull(hvirwr.count, 0) as 'hvirwr_count', " +
					 "ifnull(lvirwr.count, 0) as 'lvirwr_count',ifnull(hvirwor.count, 0) as 'hvirwor_count', " +
					 "ifnull(lvirwor.count, 0) as 'lvirwor_count', ifnull(boxc.boxcount, 0) as 'box_count' from " +
					 /*NT-2393: When one query gets an extra column in a union, the unioned query must have the same number of columns. For this case, returning null for LFFound portion of the union*/
					 "(select xx.dateCount as 'received_date', x.station_ID as 'station', x.id as 'countId' from lfboxcount x "+
					 "left outer join lfboxcontainer xx on x.container_ID = xx.id where xx.dateCount between :startDate and :endDate UNION "+
					 "select tt.receivedDate 'received_date', tt.station_ID 'station', null from lffound tt where tt.receivedDate between :startDate and :endDate ) zz " +
					 "left outer join station s on zz.station = s.Station_ID " +
					 "left outer join lffound lf on zz.received_date = lf.receivedDate "+
					 /*NT-2393: Removed the group by date as it was skewing the accurate boxcounts and changed what the query groups on*/
					 "left outer join (select bcc.dateCount as 'received_date', bc.boxCount as 'boxcount', bc.station_ID as 'station', bc.id as 'countId' from lfboxcount bc "+
					 				  "left outer join lfboxcontainer bcc on bc.container_ID = bcc.id where bcc.dateCount between :startDate and :endDate order by bcc.dateCount, bc.Station_ID) boxc on zz.countId= boxc.countId " +
					 "left outer join (select lf.receivedDate as 'received_date',s.station_id,count(i.id) as 'count' from station s " +
                        			  "left outer join lffound lf on s.station_id = lf.station_id " +
                        			  "left outer join lfitem i on lf.id = i.found_id and i.type = " + TracingConstants.LF_TYPE_FOUND + " " + 
                        			  "where lf.receivedDate between :startDate and :endDate and i.value = " + TracingConstants.LFC_ITEM_HIGH_VALUE + " " +
                        			  "group by lf.receivedDate,s.station_id order by lf.receivedDate,s.station_id) hvir on lf.receivedDate = hvir.received_date and s.station_id = hvir.station_id " +
					 "left outer join (select lf.receivedDate as 'received_date',s.station_id,count(i.id) as 'count' from station s " +
					 				  "left outer join lffound lf on s.station_id = lf.station_id " +
					 				  "left outer join lfitem i on lf.id = i.found_id and i.type = " + TracingConstants.LF_TYPE_FOUND + " " +
					 				  "where lf.receivedDate between :startDate and :endDate and i.value = " + TracingConstants.LFC_ITEM_LOW_VALUE + " " +
					 				  "group by lf.receivedDate,s.station_id order by lf.receivedDate,s.station_id) lvir on lf.receivedDate = lvir.received_date and s.station_id = lvir.station_id " + 
					 "left outer join (select lf.receivedDate as 'received_date',s.station_id,count(i.id) as 'count' from station s " +
					                  "left outer join lffound lf on s.station_id = lf.station_id " +
					                  "left outer join lfitem i on lf.id = i.found_id and i.type = " + TracingConstants.LF_TYPE_FOUND + " " +
				                      "where lf.receivedDate between :startDate and :endDate and i.value = " + TracingConstants.LFC_ITEM_HIGH_VALUE + " " +
				                      "and lf.status_id = " + TracingConstants.LF_STATUS_CLOSED + " " + 
				                      "and (i.disposition_status_id in (" + TracingConstants.LF_DISPOSITION_DELIVERED + "," + TracingConstants.LF_DISPOSITION_PICKED_UP + ") or i.deliveryRejected = 1) " +
				                      "and i.lost_id is not null " + 
				                      "group by lf.receivedDate,s.station_id order by lf.receivedDate,s.station_id) hvirwr on lf.receivedDate = hvirwr.received_date and s.station_id = hvirwr.station_id " +
				     "left outer join (select lf.receivedDate as 'received_date',s.station_id,count(i.id) as 'count' from station s " +
					                  "left outer join lffound lf on s.station_id = lf.station_id " +
					                  "left outer join lfitem i on lf.id = i.found_id and i.type = " + TracingConstants.LF_TYPE_FOUND + " " +
					                  "where lf.receivedDate between :startDate and :endDate and i.value = " + TracingConstants.LFC_ITEM_LOW_VALUE + " " +
					                  "and lf.status_id = " + TracingConstants.LF_STATUS_CLOSED + " " +
					                  "and (i.disposition_status_id in (" + TracingConstants.LF_DISPOSITION_DELIVERED + "," + TracingConstants.LF_DISPOSITION_PICKED_UP + ") or i.deliveryRejected = 1) " +
					                  "and i.lost_id is not null " +
					                  "group by lf.receivedDate,s.station_id order by lf.receivedDate,s.station_id) lvirwr on lf.receivedDate = lvirwr.received_date and s.station_id = lvirwr.station_id " +
					 "left outer join (select lf.receivedDate as 'received_date',s.station_id,count(i.id) as 'count' from station s " +
					                  "left outer join lffound lf on s.station_id = lf.station_id " +
					                  "left outer join lfitem i on lf.id = i.found_id and i.type = " + TracingConstants.LF_TYPE_FOUND + " " +
					                  "where lf.receivedDate between :startDate and :endDate and i.value = " + TracingConstants.LFC_ITEM_HIGH_VALUE + " " +
					                  "and lf.status_id = " + TracingConstants.LF_STATUS_CLOSED + " " +
					                  "and (i.disposition_status_id in (" + TracingConstants.LF_DISPOSITION_DELIVERED + "," + TracingConstants.LF_DISPOSITION_PICKED_UP + ") or i.deliveryRejected = 1) " +
					                  "and i.lost_id is null " +
					                  "group by lf.receivedDate,s.station_id order by lf.receivedDate,s.station_id) hvirwor on lf.receivedDate = hvir.received_date and s.station_id = hvirwor.station_id " +
					 "left outer join (select lf.receivedDate as 'received_date',s.station_id,count(i.id) as 'count' from station s " +
					                  "left outer join lffound lf on s.station_id = lf.station_id " +
					                  "left outer join lfitem i on lf.id = i.found_id and i.type = " + TracingConstants.LF_TYPE_FOUND + " " +
					                  "where lf.receivedDate between :startDate and :endDate and i.value = " + TracingConstants.LFC_ITEM_LOW_VALUE + " " +
					                  "and lf.status_id = " + TracingConstants.LF_STATUS_CLOSED + " " + 
					                  "and (i.disposition_status_id in (" + TracingConstants.LF_DISPOSITION_DELIVERED + "," + TracingConstants.LF_DISPOSITION_PICKED_UP + ") or i.deliveryRejected = 1) " +
					                  "and i.lost_id is null " +
					                  "group by lf.receivedDate,s.station_id order by lf.receivedDate,s.station_id) lvirwor on lf.receivedDate = lvirwor.received_date and s.station_id = lvirwor.station_id " +
					 "where 1 = 1 " + getStationSql(srDto) + "and s.companycode_ID = '" + TracingConstants.LF_LF_COMPANY_ID + "' and zz.received_date between :startDate and :endDate ";
					 if(!srDto.getSubcompCode().equals("0")){
						 sql+=" and lf.companyId=\'"+srDto.getSubcompCode()+"\' ";
					 }
					 sql+=" group by zz.received_date,s.stationcode " +
					 "order by s.stationcode,zz.received_date;";
		
		return sql;
	}

	protected void setQueryArgumentsAndScalars(SQLQuery query) {
		
		query.setDate("startDate", startDate);
		query.setDate("endDate", endDate);
		
		query.addScalar("received_date", StandardBasicTypes.STRING);
		query.addScalar("stationcode", StandardBasicTypes.STRING);
		query.addScalar("hvir_count", StandardBasicTypes.INTEGER);
		query.addScalar("lvir_count", StandardBasicTypes.INTEGER);
		query.addScalar("hvirwr_count", StandardBasicTypes.INTEGER);
		query.addScalar("lvirwr_count", StandardBasicTypes.INTEGER);
		query.addScalar("hvirwor_count", StandardBasicTypes.INTEGER);
		query.addScalar("lvirwor_count", StandardBasicTypes.INTEGER);
		query.addScalar("box_count", StandardBasicTypes.INTEGER);
		
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
			currentRow.setBoxCount((Integer) data[BOX_COUNT]);
			
			toReturn.add(currentRow);
		}
		return toReturn;
	}

	@Override
	protected String getSqlServerSqlString(StatReportDTO srDto) {
		return this.getMySqlSqlString(srDto);
	}
	
}
