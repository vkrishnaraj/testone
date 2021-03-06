package com.bagnet.nettracer.reporting;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.hibernate.Hibernate;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.SQLQuery;

import com.bagnet.nettracer.tracing.dto.StatReportDTO;

public class RonKitIssuanceReport extends AbstractNtJasperReport {
	
	private final int CREATE_DATE = 0;
	private final int STATION_CODE = 1;
	private final int INCIDENT_ID = 2;
	private final int NUM_ISSUED = 3;

	public RonKitIssuanceReport(ResourceBundle resources) {
		super(resources);
	}

	@Override
	protected String getMySqlSqlString(StatReportDTO srDto) {
		String sql = "select date(i.createdate) as 'create_date',s.stationcode,i.incident_ID,ron_kits.num_issued from incident i " +
					 "left outer join station s on i.stationcreated_ID = s.station_ID " +
					 "left outer join (select sum(p.numRonKitsIssued) as 'num_issued',p.incident_ID from passenger p " +
					 				  "where p.numRonKitsIssued > 0 " +
					 				  "group by p.incident_ID) ron_kits on i.incident_ID = ron_kits.incident_ID " +
					 "where num_issued > 0 " +	
					 "and date(i.createdate) between :startDate and :endDate " + getStationSql(srDto) + 
					 "order by s.stationcode,i.createdate;";
		return sql;
	}

	@Override
	protected void setQueryArgumentsAndScalars(SQLQuery query) {
		query.setDate("startDate", startDate);
		query.setDate("endDate", endDate);
		
		query.addScalar("create_date", StandardBasicTypes.STRING);
		query.addScalar("stationcode", StandardBasicTypes.STRING);
		query.addScalar("incident_ID", StandardBasicTypes.STRING);
		query.addScalar("num_issued", StandardBasicTypes.INTEGER);
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected List getRowsFromQuery(List raw) {
		List toReturn = new ArrayList();
		RonKitIssuanceReportRow currentRow;
		for (int i = 0; i < raw.size(); ++i) {
			currentRow = new RonKitIssuanceReportRow();
			Object[] data = (Object[]) raw.get(i);
			
			currentRow.setCreateDate((String) data[CREATE_DATE]);
			currentRow.setStationCode((String) data[STATION_CODE]);
			currentRow.setIncidentId((String) data[INCIDENT_ID]);
			currentRow.setNumIssued((Integer) data[NUM_ISSUED]);
			
			toReturn.add(currentRow);
		}
		return toReturn;
	}

	@Override
	protected String getSqlServerSqlString(StatReportDTO srDto) {
		String sql = "select cast(convert(varchar(10), i.createdate, 111) as datetime) as 'create_date',s.stationcode,i.incident_ID,ron_kits.num_issued from incident i " +
				 	 "left outer join station s on i.stationcreated_ID = s.station_ID " +
				 	 "left outer join (select sum(p.numRonKitsIssued) as 'num_issued',p.incident_ID from passenger p " +
				 				  	  "where p.numRonKitsIssued > 0 " +
				 				      "group by p.incident_ID) ron_kits on i.incident_ID = ron_kits.incident_ID " +
 				     "where num_issued > 0 " +	
 				     "and cast(convert(varchar(10), i.createdate, 111) as datetime) between :startDate and :endDate " + getStationSql(srDto) + 
 				     "order by s.stationcode,i.createdate;";
		return sql;
	}
	
	

}
