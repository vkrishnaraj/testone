package com.bagnet.nettracer.reporting;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.salvage.Salvage;
import com.bagnet.nettracer.tracing.db.salvage.SalvageBox;
import com.bagnet.nettracer.tracing.db.salvage.SalvageItem;
import com.bagnet.nettracer.tracing.db.salvage.SalvageOHDReference;

public class SalvageReport {

	private ResourceBundle resources;
	private int numOhds;
	private int numItems;
	private int numBoxes;
	
	public SalvageReport(ResourceBundle resources) {
		this.resources = resources;
		numOhds = 0;
		numItems = 0;
		numBoxes = 0;
	}

	public List getSalvageReportItems(Salvage salvage) {
		List reportRows = new ArrayList();
		reportRows.addAll(getOhdsFromSalvage(salvage));
		SalvageReportRow row = new SalvageReportRow();
		
		for (SalvageBox box: salvage.getSalvageBoxes()) {
			reportRows.add(new SalvageReportRow());
			row = new SalvageReportRow();
			if (box.getDisplayId() == TracingConstants.SALVAGE_DEFAULT) {
				reportRows.add(getColumnHeader(TracingConstants.SALVAGE_HEADER_DEFAULT));
			} else {
				reportRows.add(getColumnHeader(TracingConstants.SALVAGE_HEADER_BOX));
				row.setCol1(String.valueOf(box.getDisplayId()));
				row.setCol2(box.getDescription());
				row.setCol3(getValueFromType(box.getType()));
				row.setCol4("");
				reportRows.add(row);
				numBoxes++;
			}
			reportRows.addAll(getItemsFromBox(box));
		}
		reportRows.addAll(0, getReportSummary());
		SalvageReportRow remarkRow = getColumnHeader(TracingConstants.SALVAGE_HEADER_REMARK);
		if (salvage.getRemark() != null && salvage.getRemark().getRemarktext() != null && !salvage.getRemark().getRemarktext().isEmpty()) {
			remarkRow.setCol2(remarkRow.getCol2() + " " + salvage.getRemark().getRemarktext());
			reportRows.add(0, new SalvageReportRow());
			reportRows.add(0, remarkRow);
		}
		
		return reportRows;
	}
	
	private List getOhdsFromSalvage(Salvage salvage) {
		List ohds = new ArrayList();
		ohds.add(getColumnHeader(TracingConstants.SALVAGE_HEADER_OHD));
		for (SalvageOHDReference ohd: salvage.getOhdReferences()) {
			SalvageReportRow row = new SalvageReportRow();
			row.setCol2(ohd.getOhdId());
			ohds.add(row);
			numOhds++;
		}
		return ohds;
	}
	
	private SalvageReportRow getColumnHeader(int type) {
		SalvageReportRow row = new SalvageReportRow();
		switch(type) {
		case TracingConstants.SALVAGE_HEADER_DEFAULT:
			row.setCol2(resources.getString("header.salvage_miscellaneous_items"));
			row.setCol3(resources.getString("report.salvage.column.value"));
			row.setCol4(resources.getString("report.salvage.column.quantity"));
			break;
		case TracingConstants.SALVAGE_HEADER_BOX:
			row.setCol1(resources.getString("report.salvage.column.box"));
			row.setCol2(resources.getString("report.salvage.column.description"));
			row.setCol3(resources.getString("report.salvage.column.value"));
			row.setCol4(resources.getString("report.salvage.column.quantity"));
			break;
		case TracingConstants.SALVAGE_HEADER_OHD:
			row.setCol2(resources.getString("report.salvage.column.ohd"));
			break;
		case TracingConstants.SALVAGE_HEADER_SUMMARY:
			row.setCol2(resources.getString("report.salvage.column.summary"));
			row.setCol4(resources.getString("report.salvage.column.quantity"));
			break;
		case TracingConstants.SALVAGE_HEADER_REMARK:
			row.setCol2(resources.getString("report.salvage.remark"));
			break;
		default:
			break;
		}
		return row;
	}
	
	private List getItemsFromBox(SalvageBox box) {
		List items = new ArrayList();
		
		for (SalvageItem item: box.getSalvageItems()) {
			SalvageReportRow row = new SalvageReportRow();
			row.setCol1("");
			row.setCol2(item.getDescription());
			if (box.getType() == TracingConstants.SALVAGE_DEFAULT) {
				row.setCol3(getValueFromType(item.getType()));
				numItems++;
			}
			row.setCol4(String.valueOf(item.getQuantity()));
			items.add(row);
		}
		
		return items;
	}
	
	private String getValueFromType(int type) {
		String toReturn = "";
		
		if (type == TracingConstants.SALVAGE_LOW_VALUE) {
			toReturn = resources.getString("salvage.low_value");
		} else if (type == TracingConstants.SALVAGE_HIGH_VALUE) {
			toReturn = resources.getString("salvage.high_value");
		}
		
		return toReturn;
	}
	
	private List getReportSummary() {
		List summary = new ArrayList();
		SalvageReportRow row = getColumnHeader(TracingConstants.SALVAGE_HEADER_SUMMARY);
		summary.add(row);
		
		row = new SalvageReportRow();
		row.setCol2(resources.getString("report.salvage.column.ohd"));
		row.setCol4(String.valueOf(numOhds));
		summary.add(row);

		row = new SalvageReportRow();
		row.setCol2(resources.getString("header.salvage_miscellaneous_items"));
		row.setCol4(String.valueOf(numItems));
		summary.add(row);
		
		row = new SalvageReportRow();
		row.setCol2(resources.getString("report.salvage.column.boxes"));
		row.setCol4(String.valueOf(numBoxes));
		summary.add(row);
		
		summary.add(new SalvageReportRow());
		
		return summary;
	}
	
}

