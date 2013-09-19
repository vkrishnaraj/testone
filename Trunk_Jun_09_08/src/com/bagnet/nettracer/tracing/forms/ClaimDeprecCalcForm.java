package com.bagnet.nettracer.tracing.forms;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.TimeZone;

import org.apache.struts.action.ActionForm;

import aero.nettracer.fs.model.FsAddress;
import aero.nettracer.fs.model.FsIPAddress;
import aero.nettracer.fs.model.FsReceipt;
import aero.nettracer.fs.model.Person;
import aero.nettracer.fs.model.Phone;
import aero.nettracer.fs.model.Segment;

import com.bagnet.nettracer.tracing.db.Claim;
import com.bagnet.nettracer.tracing.db.Claim_Depreciation;
import com.bagnet.nettracer.tracing.db.Depreciation_Category;
import com.bagnet.nettracer.tracing.db.Depreciation_Item;
import com.bagnet.nettracer.tracing.utils.CreditCardType;
import com.bagnet.nettracer.tracing.utils.DateUtils;

/** * @author Sean Fine
 * 
 * This class represents the claim depreciation calculator form that is used for calculating depreciation items and payouts
 */
public final class ClaimDeprecCalcForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private long claim_id;
	private int addNum;
	private Claim_Depreciation claimDeprec;

	private String _DATEFORMAT; // current login agent's date format
	
	public long getClaim_id() {
		return claim_id;
	}

	public void setClaim_id(long claim_id) {
		this.claim_id = claim_id;
	}

	public Depreciation_Item getDeprecItem(int index) {
		if (index < 0) index = 0;
		Depreciation_Item dc = null;
		while (this.claimDeprec.getItemlist().size() <= index) {
			dc = new Depreciation_Item();
			dc.setClaimDepreciation(this.claimDeprec);
			this.claimDeprec.getItemlist().add(dc);
		}
		return (Depreciation_Item) this.claimDeprec.getItemlist().get(index);
	}

	public Depreciation_Item getTheDeprecItem(int index) {
		if (index < 0) index = 0;
		if (this.claimDeprec.getItemlist().size() <= index) {

			this.claimDeprec.getItemlist().add(new Depreciation_Item());
		}
		return (Depreciation_Item) this.claimDeprec.getItemlist().get(index);
	}

	public void setTheDeprecItem(int index,Depreciation_Item dc) {
		claimDeprec.getItemlist().add(index,dc);
	}

	public int getAddNum() {
		return addNum;
	}

	public void setAddNum(int addNum) {
		this.addNum = addNum;
	}

	public Claim_Depreciation getClaimDeprec() {
		return claimDeprec;
	}

	public void setClaimDeprec(Claim_Depreciation claimDeprec) {
		this.claimDeprec = claimDeprec;
	}



	@Override
	public void reset(org.apache.struts.action.ActionMapping mapping, javax.servlet.http.HttpServletRequest request) {
		super.reset(mapping, request);
		
		if (claimDeprec != null && claimDeprec.getItemlist()!=null) {
			for (Depreciation_Item dc : claimDeprec.getItemlist()) {
				dc.setNotCoveredCoc(false);
			}
		}
		
	}
	
}