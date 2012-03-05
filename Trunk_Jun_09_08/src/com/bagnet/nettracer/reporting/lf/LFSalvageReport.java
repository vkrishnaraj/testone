package com.bagnet.nettracer.reporting.lf;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.dto.StatReportDTO;

public class LFSalvageReport extends LFReport {
	
	private final int BARCODE = 0;
	private final int DATE_RECEIVED = 1;
	private final int VALUE = 2;
	private final int CATEGORY = 3;
	private final int SUB_CATEGORY = 4;
	private final int TITLE = 5;

	public LFSalvageReport(ResourceBundle resources) {
		super(resources);
	}
	
	@Override
	protected String getSqlString(StatReportDTO srDto) {
		String sql = "select lf.barcode,date(lf.receivedDate) as 'date_received',i.value,ifnull(c.description,'') as 'category', " +
					 "ifnull(sc.description,'') as 'subcategory',i.description as 'title' from lfsalvage s " +
					 "left outer join lffound lf on s.id = lf.salvage_id " +
					 "left outer join lfitem i on lf.id = i.found_id and i.type = " + TracingConstants.LF_TYPE_FOUND + " " +
					 "left outer join lfcategory c on i.category = c.id " +
					 "left outer join lfsubcategory sc on i.subCategory = sc.id " +
					 "where s.id = " + srDto.getSalvageId() + " " +
					 "order by lf.id;";
		return sql;
	}

	@Override
	protected void setQueryArgumentsAndScalars(SQLQuery query) {

		query.addScalar("barcode", Hibernate.LONG);
		query.addScalar("date_received", Hibernate.STRING);
		query.addScalar("value", Hibernate.INTEGER);
		query.addScalar("category", Hibernate.STRING);
		query.addScalar("subcategory", Hibernate.STRING);
		query.addScalar("title", Hibernate.STRING);
		
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected List getRowsFromQuery(List raw) {
		List toReturn = new ArrayList();
		LFSalvageReportRow currentRow;
		for (int i = 0; i < raw.size(); ++i) {
			currentRow = new LFSalvageReportRow();
			Object[] data = (Object[]) raw.get(i);
			if (data[0] == null) {
				break;
			}
			
			currentRow.setBarcode((Long) data[BARCODE]);
			currentRow.setDateReceived((String) data[DATE_RECEIVED]);
			
			String value = "";
			if (((Integer) data[VALUE]) == 1) {
				value = resources.getString("lf.itemization.value.high");
			} else {
				value = resources.getString("lf.itemization.value.low");
			}
			currentRow.setValue(value);
			
			currentRow.setCategory((String) data[CATEGORY]);
			currentRow.setSubCategory((String) data[SUB_CATEGORY]);
			currentRow.setTitle((String) data[TITLE]);		
			
			toReturn.add(currentRow);
		}
		return toReturn;
	}

}
