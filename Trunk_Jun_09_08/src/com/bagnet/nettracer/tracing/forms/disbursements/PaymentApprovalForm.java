package com.bagnet.nettracer.tracing.forms.disbursements;

import java.util.ArrayList;
import java.util.List;

import com.bagnet.nettracer.tracing.dto.IncidentActivityTaskDTO;
import com.bagnet.nettracer.tracing.forms.templates.CommandForm;

public class PaymentApprovalForm extends CommandForm {

	private static final long serialVersionUID = -5261247795706286000L;

	private String currpage;
	private String nextpage;
	private String prevpage;
	private String pagination;
	private String _DATEFORMAT;
	
	private String s_createtime = "";
	private String e_createtime = "";

	private boolean active;
	
	private String rejectRemark;
	private String rejectId;
	
	List<IncidentActivityTaskDTO> iatlist  = new ArrayList<IncidentActivityTaskDTO>();;
	
	public List<IncidentActivityTaskDTO> getIatlist(){
		return iatlist;
	}
	
	public void setIatlist(List<IncidentActivityTaskDTO> iatlist){
		this.iatlist=iatlist;
	}
	

	public IncidentActivityTaskDTO getIat(int index) {
		if (index < 0) index = 0;

		while (this.iatlist.size() <= index) {

			this.iatlist.add(new IncidentActivityTaskDTO());
		}
		return (IncidentActivityTaskDTO) this.iatlist.get(index);
	}

	public IncidentActivityTaskDTO getIatlist(int index) {
		while (this.iatlist.size() <= index) {
			this.iatlist.add(new IncidentActivityTaskDTO());
		}
		return iatlist.get(index);
	}
	
	public String getCurrpage() {
		return currpage;
	}
	public void setCurrpage(String currpage) {
		this.currpage = currpage;
	}
	
	public String getNextpage() {
		return nextpage;
	}
	public void setNextpage(String nextpage) {
		this.nextpage = nextpage;
	}
	
	public String getPrevpage() {
		return prevpage;
	}
	public void setPrevpage(String prevpage) {
		this.prevpage = prevpage;
	}
	
	public String getPagination() {
		return pagination;
	}
	public void setPagination(String pagination) {
		this.pagination = pagination;
	}
	
	public String get_DATEFORMAT() {
		return _DATEFORMAT;
	}
	public void set_DATEFORMAT(String _DATEFORMAT) {
		this._DATEFORMAT = _DATEFORMAT;
	}
	
	public String getS_createtime() {
		return s_createtime;
	}
	public void setS_createtime(String s_createtime) {
		this.s_createtime = s_createtime;
	}
	
	public String getE_createtime() {
		return e_createtime;
	}
	public void setE_createtime(String e_createtime) {
		this.e_createtime = e_createtime;
	}

	public String getRejectRemark() {
		return rejectRemark;
	}

	public void setRejectRemark(String rejectRemark) {
		this.rejectRemark = rejectRemark;
	}

	public String getRejectId() {
		return rejectId;
	}

	public void setRejectId(String rejectId) {
		this.rejectId = rejectId;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
