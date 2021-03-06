package com.bagnet.nettracer.reporting.lf;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.hibernate.Hibernate;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.SQLQuery;

import com.bagnet.nettracer.reporting.AbstractNtJasperReport;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.dto.StatReportDTO;

public class LFLostItemizationReport extends AbstractNtJasperReport {
	
	private final int ID = 0;
	private final int STATION_CODE = 1;
	private final int OPEN_DATE = 2;
	private final int CATEGORY = 3;
	private final int SUB_CATEGORY = 4;
	private final int BRAND = 5;
	private final int MODEL = 6;
	private final int SERIAL_NUMBER = 7;
	private final int COLOR = 8;
	private final int CASE_COLOR = 9;
	private final int DESCRIPTION = 10;
	
	public LFLostItemizationReport(ResourceBundle resources) {
		super(resources);
	}

	@Override
	protected String getMySqlSqlString(StatReportDTO srDto) {			
		String sql= "select l.id,s.stationcode,date(l.openDate) as 'date',ifnull(c.description,'') as 'category', " +
		"ifnull(sc.description,'') as 'sub_category',ifnull(i.brand,'') as 'brand',ifnull(i.model,'') as 'model', " +
		"ifnull(i.serialNumber,'') as 'serial_number',ifnull(i.color,'') as 'color', ifnull(i.caseColor,'') as 'case_color', " +
		"ifnull(i.description,'') as 'description' from lflost l " +
				
		"left outer join lfitem i on l.id = i.lost_id and i.type = " + TracingConstants.LF_TYPE_LOST + " ";
		sql+= "left outer join (select lost_id, max(id)id from lfsegment group by lost_id) fs on l.id = fs.lost_id " +
		"left join lfsegment seg on fs.id = seg.id " +
		"join station s on seg.destination_station_ID = s.station_ID ";

		sql+= "join lfcategory c on i.category = c.id " +
		"left outer join lfsubcategory sc on i.subCategory = sc.id " +
		"where date(l.openDate) between :startDate and :endDate " + getStationSql(srDto); 
		if(!srDto.getSubcompCode().equals("0")){
			 sql+=" and l.companyId=\'"+srDto.getSubcompCode()+"\' ";
		 }
		sql+=  "order by s.stationcode,date;";
		return sql;
	}

	@Override
	protected void setQueryArgumentsAndScalars(SQLQuery query) {
		query.setDate("startDate", startDate);
		query.setDate("endDate", endDate);
		
		query.addScalar("id", StandardBasicTypes.LONG);
		query.addScalar("stationcode", StandardBasicTypes.STRING);
		query.addScalar("date", StandardBasicTypes.STRING);
		query.addScalar("category", StandardBasicTypes.STRING);
		query.addScalar("sub_category", StandardBasicTypes.STRING);
		query.addScalar("brand", StandardBasicTypes.STRING);
		query.addScalar("model", StandardBasicTypes.STRING);
		query.addScalar("serial_number", StandardBasicTypes.STRING);
		query.addScalar("color", StandardBasicTypes.STRING);
		query.addScalar("case_color", StandardBasicTypes.STRING);
		query.addScalar("description", StandardBasicTypes.STRING);
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected List getRowsFromQuery(List raw) {
		List toReturn = new ArrayList();
		LFLostItemizationReportRow currentRow;
		for (int i = 0; i < raw.size(); ++i) {
			currentRow = new LFLostItemizationReportRow();
			Object[] data = (Object[]) raw.get(i);
			
			currentRow.setId((Long) data[ID]);
			currentRow.setStation((String) data[STATION_CODE]);
			currentRow.setOpenDate((String) data[OPEN_DATE]);
			currentRow.setCategory((String) data[CATEGORY]);
			currentRow.setSubCategory((String) data[SUB_CATEGORY]);
			currentRow.setBrand((String) data[BRAND]);
			currentRow.setModel((String) data[MODEL]);
			currentRow.setSerialNumber((String) data[SERIAL_NUMBER]);		
			currentRow.setColor((String) data[COLOR]);
			currentRow.setCaseColor((String) data[CASE_COLOR]);
			currentRow.setDescription((String) data[DESCRIPTION]);
			
			toReturn.add(currentRow);
		}
		return toReturn;
	}

	@Override
	protected String getSqlServerSqlString(StatReportDTO srDto) {
		return this.getMySqlSqlString(srDto);
	}
	
}
