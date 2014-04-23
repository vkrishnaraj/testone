package com.bagnet.nettracer.reporting.lf;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.SQLQuery;

import com.bagnet.nettracer.reporting.AbstractNtJasperReport;
import com.bagnet.nettracer.tracing.dto.StatReportDTO;

public class LFSummaryReport extends AbstractNtJasperReport {

	static {
		logger = Logger.getLogger(LFSummaryReport.class);
	}
	
	private final int DATE = 0;
	private final int NLRR = 1;
	private final int NFIE = 2;

	public LFSummaryReport(ResourceBundle resources) {
		super(resources);
	}

	@Override
	protected String getMySqlSqlString(StatReportDTO srDto) {
		String sql = "select DATE_FORMAT(greatest(ifnull(combo.lost_date, 0), ifnull(combo.found_date, 0)), '%Y-%m-%d') as 'date', ifnull(combo.lost_id_count, 0) as 'nlrr', ifnull(combo.found_id_count, 0) as 'nfie' from ( " +
					 "select lost.date as 'lost_date',found.date as 'found_date',lost.id_count as 'lost_id_count',found.id_count as 'found_id_count' from " + 
					 "(select date(lfl.openDate) as 'date',count(lfl.id) as 'id_count' from lflost lfl where lfl.openDate between :startDate and :endDate ";
					 if(!srDto.getSubcompCode().equals("0")){
						 sql+=" and lfl.companyId=\'"+srDto.getSubcompCode()+"\' ";
					 }
					 sql+="group by date(lfl.openDate)) lost " +
					 "left outer join " +
					 "(select date(lff.foundDate) as 'date',count(lff.id) as 'id_count' from lffound lff where lff.foundDate between :startDate and :endDate";
					 if(!srDto.getSubcompCode().equals("0")){
						 sql+=" and lff.companyId=\'"+srDto.getSubcompCode()+"\' ";
					 }
					 sql+=" group by date(lff.foundDate)) found " +
					 "on lost.date = found.date " +
					 "union " + 
					 "select lost.date as 'lost_date',found.date as 'found_date',lost.id_count as 'lost_id_count',found.id_count as 'found_id_count' from " + 
					 "(select date(lfl.openDate) as 'date',count(lfl.id) as 'id_count' from lflost lfl where lfl.openDate between :startDate and :endDate";
					 if(!srDto.getSubcompCode().equals("0")){
						 sql+=" and lfl.companyId=\'"+srDto.getSubcompCode()+"\' ";
					 }
					 sql+=" group by date(lfl.openDate)) lost " +
					 "right outer join " +
					 "(select date(lff.foundDate) as 'date',count(lff.id) as 'id_count' from lffound lff where lff.foundDate between :startDate and :endDate";
					 if(!srDto.getSubcompCode().equals("0")){
						 sql+=" and lff.companyId=\'"+srDto.getSubcompCode()+"\' ";
					 }
					 sql+=" group by date(lff.foundDate)) found " +
					 "on lost.date = found.date) as combo " +
					 "order by date;";
		
		return sql;
	}

	@Override
	protected void setQueryArgumentsAndScalars(SQLQuery query) {
		
		query.setDate("startDate", startDate);
		query.setDate("endDate", endDate);
		
		query.addScalar("date", StandardBasicTypes.STRING);
		query.addScalar("nlrr", StandardBasicTypes.INTEGER);
		query.addScalar("nfie", StandardBasicTypes.INTEGER);
			
	}
	
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected List getRowsFromQuery(List raw) {
		List toReturn = new ArrayList();
		LFSummaryReportRow currentRow;
		for (int i = 0; i < raw.size(); ++i) {
			currentRow = new LFSummaryReportRow();
			Object[] data = (Object[]) raw.get(i);
			
			currentRow.setDate((String) data[DATE]);
			currentRow.setNlrr((Integer) data[NLRR]);
			currentRow.setNfie((Integer) data[NFIE]);
			
			toReturn.add(currentRow);
		}
		return toReturn;
	}

	@Override
	protected String getSqlServerSqlString(StatReportDTO srDto) {
		return this.getMySqlSqlString(srDto);
	}
	
}
