package com.bagnet.nettracer.reporting;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.hibernate.Hibernate;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.SQLQuery;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.dto.StatReportDTO;

public class ReplacementBagIssuanceReport extends AbstractNtJasperReport {
	
	private final int CREATE_DATE = 0;
	private final int STATION_CODE = 1;
	private final int INCIDENT_ID = 2;
	private final int NUM_BAGS_AFFECTED = 3;
	private final int NUM_BAGS_ISSUED = 4;
	private final int LEVEL_OF_DAMAGE = 5;

	public ReplacementBagIssuanceReport(ResourceBundle resources) {
		super(resources);
	}

	@Override
	protected String getMySqlSqlString(StatReportDTO srDto) {
		String sql = "select date(i.createdate) as 'create_date',s.stationcode,i.incident_ID,count(it.item_ID) as 'num_bags_affected', " +
					 "sum(it.replacementBagIssued) as 'num_bags_issued',it.lvlofdamage from incident i " +
					 "left outer join station s on i.stationcreated_ID = s.station_ID " +
					 "left outer join item it on i.incident_ID = it.incident_ID " +
					 "where it.replacementBagIssued > 0 " +
					 "and date(i.createdate) between :startDate and :endDate " + getStationSql(srDto) + 
					 "group by i.incident_ID,it.lvlofdamage;";
		return sql;
	}

	@Override
	protected void setQueryArgumentsAndScalars(SQLQuery query) {
		query.setDate("startDate", startDate);
		query.setDate("endDate", endDate);
		
		query.addScalar("create_date", StandardBasicTypes.STRING);
		query.addScalar("stationcode", StandardBasicTypes.STRING);
		query.addScalar("incident_ID", StandardBasicTypes.STRING);
		query.addScalar("num_bags_affected", StandardBasicTypes.INTEGER);		
		query.addScalar("num_bags_issued", StandardBasicTypes.INTEGER);		
		query.addScalar("lvlofdamage", StandardBasicTypes.INTEGER);		
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected List getRowsFromQuery(List raw) {
		List toReturn = new ArrayList();
		ReplacementBagIssuanceReportRow currentRow;
		for (int i = 0; i < raw.size(); ++i) {
			currentRow = new ReplacementBagIssuanceReportRow();
			Object[] data = (Object[]) raw.get(i);
			
			currentRow.setCreateDate((String) data[CREATE_DATE]);
			currentRow.setStationCode((String) data[STATION_CODE]);
			currentRow.setIncidentId((String) data[INCIDENT_ID]);
			currentRow.setNumBagsAffected((Integer) data[NUM_BAGS_AFFECTED]);
			currentRow.setNumBagsIssued((Integer) data[NUM_BAGS_ISSUED]);

			int lvlofdamage = (Integer) data[LEVEL_OF_DAMAGE];
			switch (lvlofdamage) {
			case TracingConstants.DAMAGE_MINOR:
				currentRow.setLevelOfDamage(resources.getString("select.minor"));
				break;
			case TracingConstants.DAMAGE_MAJOR:
				currentRow.setLevelOfDamage(resources.getString("select.major"));
				break;
			case TracingConstants.DAMAGE_COMPLETE:
				currentRow.setLevelOfDamage(resources.getString("select.complete"));
				break;
			}
			
			toReturn.add(currentRow);
		}
		return toReturn;
	}

	@Override
	protected String getSqlServerSqlString(StatReportDTO srDto) {
		String sql = "select cast(convert(varchar(10), i.createdate, 111) as datetime) as 'create_date',s.stationcode,i.incident_ID,count(it.item_ID) as 'num_bags_affected', " +
				 "sum(it.replacementBagIssued) as 'num_bags_issued',it.lvlofdamage from incident i " +
				 "left outer join station s on i.stationcreated_ID = s.station_ID " +
				 "left outer join item it on i.incident_ID = it.incident_ID " +
				 "where it.replacementBagIssued > 0 " +
				 "and cast(convert(varchar(10), i.createdate, 111) as datetime) between :startDate and :endDate " + getStationSql(srDto) + 
				 "group by i.incident_ID,it.lvlofdamage,i.createdate,s.stationcode;";
		return sql;
	}

}
