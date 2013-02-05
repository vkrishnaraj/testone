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

public class LFSalvageReport extends AbstractNtJasperReport {
	
	private final int BARCODE = 0;
	private final int DATE_RECEIVED = 1;
	private final int VALUE = 2;
	private final int CATEGORY = 3;
	private final int SUB_CATEGORY = 4;
	private final int TITLE = 5;
	private final int BRAND = 6;
	private final int MODEL = 7;
	private final int SALVAGEBOX = 8;

	public LFSalvageReport(ResourceBundle resources) {
		super(resources);
	}
	
	@Override
	protected String getMySqlSqlString(StatReportDTO srDto) {
		String sql = "select lf.barcode,date(lf.receivedDate) as 'date_received',i.value,ifnull(c.description,'') as 'category', " +
					 "ifnull(sc.description,'') as 'subcategory',i.description as 'title', " +
					 "i.brand as 'brand', i.model as 'model', lf.salvageBoxId from lfsalvage s " +
					 "left outer join lffound lf on s.id = lf.salvage_id " +
					 "left outer join lfitem i on lf.id = i.found_id and i.type = " + TracingConstants.LF_TYPE_FOUND + " " +
					 "left outer join lfcategory c on i.category = c.id " +
					 "left outer join lfsubcategory sc on i.subCategory = sc.id " +
					 "where s.id = " + srDto.getSalvageId() + " ";
					 if(!srDto.getSubcompCode().equals("0")){
						 sql+=" and lf.companyId=\'"+srDto.getSubcompCode()+"\' ";
					 }
					sql+="order by lf.id;";
		return sql;
	}

	@Override
	protected void setQueryArgumentsAndScalars(SQLQuery query) {

		query.addScalar("barcode", StandardBasicTypes.LONG);
		query.addScalar("date_received", StandardBasicTypes.STRING);
		query.addScalar("value", StandardBasicTypes.INTEGER);
		query.addScalar("category", StandardBasicTypes.STRING);
		query.addScalar("subcategory", StandardBasicTypes.STRING);
		query.addScalar("title", StandardBasicTypes.STRING);
		query.addScalar("brand", StandardBasicTypes.STRING);
		query.addScalar("model", StandardBasicTypes.STRING);
		query.addScalar("salvageBoxId", StandardBasicTypes.STRING);
		
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
			
			currentRow.setSalvageBoxId((String) data[SALVAGEBOX]);	
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
			currentRow.setBrand((String) data[BRAND]);		
			currentRow.setModel((String) data[MODEL]);		
			
			toReturn.add(currentRow);
		}
		return toReturn;
	}

	@Override
	protected String getSqlServerSqlString(StatReportDTO srDto) {
		return this.getMySqlSqlString(srDto);
	}

}
