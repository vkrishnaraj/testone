package com.bagnet.nettracer.tracing.forms;

import org.apache.struts.action.ActionForm;

import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Claim_Depreciation;
import com.bagnet.nettracer.tracing.db.Depreciation_Item;

/** * @author Sean Fine
 * 
 * This class represents the claim depreciation calculator form that is used for calculating depreciation items and payouts
 */
public final class ClaimDeprecCalcForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private long claim_id;
	private int addNum;
	private Claim_Depreciation claimDeprec;
	private Agent agent;
	
	public long getClaim_id() {
		return claim_id;
	}

	public void setClaim_id(long claim_id) {
		this.claim_id = claim_id;
	}

	public Depreciation_Item getDeprecItem(int index) {
		if (index < 0) index = 0;
		Depreciation_Item dc = null;
		if (claimDeprec != null && claimDeprec.getItemlist()!=null) {
			while (this.claimDeprec.getItemlist().size() <= index) {
				dc = new Depreciation_Item();
				dc.setClaimDepreciation(this.claimDeprec);
				this.claimDeprec.getItemlist().add(dc);
			}
			return (Depreciation_Item) this.claimDeprec.getItemlist().get(index);
		}
		return null;
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

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}
	
}