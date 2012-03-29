package com.bagnet.nettracer.reporting.lf;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;

import com.bagnet.nettracer.reporting.AbstractNtJasperReport;
import com.bagnet.nettracer.reporting.LFDisbursementsReportRow;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.dto.StatReportDTO;

public class LFDisbursementsReport extends AbstractNtJasperReport {

	static {
		logger = Logger.getLogger(LFDisbursementsReport.class);
	}

	private final int DATE = 0;
	private final int CHECK_AMOUNT = 1;
	private final int CHECK_NUMBER = 2;
	private final int LOST_ID = 3;
	private final int BARCODE = 4;
	private final int TRACKING_NUMBER = 5;
	
	public LFDisbursementsReport(ResourceBundle resources) {
		super(resources);
	}

	@Override
	protected String getSqlString(StatReportDTO srDto) {
		String sql = "select date(lff.deliveredDate) as 'deliveredDate',lff.checkAmount,lff.checkNumber,ifnull(lfl.id,0) as 'lost_id',lff.barcode,i.trackingNumber from lffound lff " +
					 "left outer join lfitem i on lff.id = i.found_id and i.type = " + TracingConstants.LF_TYPE_FOUND + " " +
					 "left outer join lflost lfl on i.lost_id = lfl.id " +
					 "where lff.deliveredDate between :startDate and :endDate " + 
					 "and lff.checkAmount > 0;";
		
		return sql;
	}

	@Override
	protected void setQueryArgumentsAndScalars(SQLQuery query) {
		query.setDate("startDate", startDate);
		query.setDate("endDate", endDate);
		
		query.addScalar("deliveredDate", Hibernate.STRING);
		query.addScalar("checkAmount", Hibernate.DOUBLE);
		query.addScalar("checkNumber", Hibernate.INTEGER);
		query.addScalar("lost_id", Hibernate.LONG);
		query.addScalar("barcode", Hibernate.LONG);
		query.addScalar("trackingNumber", Hibernate.STRING);		
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected List getRowsFromQuery(List raw) {
		List toReturn = new ArrayList();
		LFDisbursementsReportRow currentRow;
		for (int i = 0; i < raw.size(); ++i) {
			currentRow = new LFDisbursementsReportRow();
			Object[] data = (Object[]) raw.get(i);
			
			currentRow.setDate((String) data[DATE]);
			currentRow.setCheckAmount((Double) data[CHECK_AMOUNT]);
			currentRow.setCheckNumber((Integer) data[CHECK_NUMBER]);
			currentRow.setLostId((Long) data[LOST_ID]);
			currentRow.setBarcode((Long) data[BARCODE]);
			currentRow.setTrackingNumber((String) data[TRACKING_NUMBER]);
			
			toReturn.add(currentRow);
		}
		return toReturn;
	}

}
