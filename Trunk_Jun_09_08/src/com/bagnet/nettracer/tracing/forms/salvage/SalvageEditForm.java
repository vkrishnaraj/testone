package com.bagnet.nettracer.tracing.forms.salvage;

import java.util.ArrayList;
import java.util.TimeZone;

import org.apache.struts.validator.ValidatorForm;

import com.bagnet.nettracer.tracing.db.salvage.Salvage;
import com.bagnet.nettracer.tracing.db.salvage.SalvageBox;
import com.bagnet.nettracer.tracing.db.salvage.SalvageOHDReference;
import com.bagnet.nettracer.tracing.utils.DateUtils;

@SuppressWarnings("serial")
public class SalvageEditForm extends ValidatorForm {
	
	private Salvage salvage;
	private String dateFormat;
	private String timeFormat;
	private TimeZone timeZone;
	private String _DATEFORMAT;

	public Salvage getSalvage() {
		return salvage;
	}

	public void setSalvage(Salvage salvage) {
		this.salvage = salvage;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public String getTimeFormat() {
		return timeFormat;
	}

	public void setTimeFormat(String timeFormat) {
		this.timeFormat = timeFormat;
	}

	public TimeZone getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(TimeZone timeZone) {
		this.timeZone = timeZone;
	}

	public String getDisSalvageDate() {
		return DateUtils.formatDate(salvage.getSalvageDate(), dateFormat, "", timeZone);
	}

	public void setDisSalvageDate(String s) {
		salvage.setSalvageDate(DateUtils.convertToDate(s, dateFormat, null));
	}

	public SalvageBox getSalvageBox(int i) {
		ArrayList<SalvageBox> boxList;
		if (salvage.getSalvageBoxes() != null && i < salvage.getSalvageBoxes().size()) {
			boxList = new ArrayList<SalvageBox>(salvage.getSalvageBoxes());
			return boxList.get(i);
		}
		return null;
		
	}

	public SalvageOHDReference getOhdReference(int i) {
		ArrayList<SalvageOHDReference> refList;
		if (salvage.getOhdReferences() != null && i < salvage.getOhdReferences().size()) {
			refList = new ArrayList<SalvageOHDReference>(salvage.getOhdReferences());
			return refList.get(i);
		}
		return null;
	}

}
