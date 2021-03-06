package com.bagnet.nettracer.tracing.forms;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.validator.ValidatorForm;

import com.bagnet.nettracer.tracing.db.Depreciation_Category;

/**
 * @author Sean Fine
 * 
 * This class represents depreciation calculation administration form
 */

public final class DeprecCalcAdminForm extends ValidatorForm {
	
	private double lessTwentyDeprec;
	private double twentyOnefiftyDeprec;
	private double onefiftyDeprec;
	private int noDates;
	private int compMembers;
	
	private List<Depreciation_Category> categories=new ArrayList<Depreciation_Category>();

	public List<Depreciation_Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Depreciation_Category> categories) {
		this.categories = categories;
	}
	
	public Depreciation_Category getCategory(int index) {
		if (index < 0) index = 0;
		Depreciation_Category dc = null;
		if (categories != null) {
			while (this.categories.size() <= index) {
				dc = new Depreciation_Category();
				dc.setMaxDeprec(100);
				this.categories.add(dc);
			}
			return (Depreciation_Category) this.categories.get(index);
		}
		return null;
	}

	public void setTheCategory(int index,Depreciation_Category dc) {
		categories.add(index,dc);
	}
	
	@Override
	public void reset(org.apache.struts.action.ActionMapping mapping, javax.servlet.http.HttpServletRequest request) {
		super.reset(mapping, request);
		
		if (categories != null) {
			for (Depreciation_Category dc : categories) {
				dc.setNotCoveredCoc(false);
			}
		}
		
	}

	public double getLessTwentyDeprec() {
		return lessTwentyDeprec;
	}

	public void setLessTwentyDeprec(double lessTwentyDeprec) {
		this.lessTwentyDeprec = lessTwentyDeprec;
	}

	public double getTwentyOnefiftyDeprec() {
		return twentyOnefiftyDeprec;
	}

	public void setTwentyOnefiftyDeprec(double twentyOnefiftyDeprec) {
		this.twentyOnefiftyDeprec = twentyOnefiftyDeprec;
	}

	public double getOnefiftyDeprec() {
		return onefiftyDeprec;
	}

	public void setOnefiftyDeprec(double onefiftyDeprec) {
		this.onefiftyDeprec = onefiftyDeprec;
	}

	public int getNoDates() {
		return noDates;
	}

	public void setNoDates(int noDates) {
		this.noDates = noDates;
	}

	public int getCompMembers() {
		return compMembers;
	}

	public void setCompMembers(int compMembers) {
		this.compMembers = compMembers;
	}

}