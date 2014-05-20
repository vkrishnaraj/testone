/*
 * Created on Jul 9, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.actions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.OHD_Log;
import com.bagnet.nettracer.tracing.db.Remark;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.WorldTracerFile.WTStatus;
import com.bagnet.nettracer.tracing.db.wtq.WtqAmendOhd;
import com.bagnet.nettracer.tracing.db.wtq.WtqCloseOhd;
import com.bagnet.nettracer.tracing.db.wtq.WtqCreateOhd;
import com.bagnet.nettracer.tracing.db.wtq.WtqOhdAction;
import com.bagnet.nettracer.tracing.forms.CloseOnHandForm;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.BagService;
import com.bagnet.nettracer.tracing.utils.OHDUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.wt.WorldTracerQueueUtils;

/**
 * Implementation of <strong>Action </strong> that is responsible for generating
 * a list of all the bags that needs to be closed and calls the batch close action.
 * 
 */
public class CloseOnHandAction extends CheckedAction {
	private static Logger logger = Logger.getLogger(CloseOnHandAction.class);
	private String closedOhd_ID = null;
	private String notClosedOhd_ID = null;
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		TracerUtils.checkSession(session);

		Agent user = (Agent) session.getAttribute("user");
		if (user == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}
		BagService bs = new BagService();
		CloseOnHandForm theform = (CloseOnHandForm) form;
		// get ohd_ids to be batch closed
		String ohd_ID = theform.getOhd_ID();
		if (request.getParameter("batch2") != null) {
			ohd_ID = request.getParameter("batch2_id");
		}		
		ArrayList<LabelValueBean> oList = new ArrayList<LabelValueBean>();
		if (ohd_ID != null && ohd_ID.length() > 0) {
			String[] ohdArray = ohd_ID.split(",");
			Map<String, OHD> ohdMap = new HashMap<String, OHD>();
			for (String o: ohdArray) {
				o = StringUtils.stripToNull(o);
				if (o == null) {
					continue;
				}
				
				OHD ohd = OHDUtils.getOHD(o);
				if(ohd != null) {
					ohdMap.put(o, ohd);
					oList.add(new LabelValueBean(o, ""));
				}
			}		
			theform.setOhd_ID(ohd_ID);
			theform.setOhdList(oList);
			request.setAttribute("ohdMap", ohdMap);
			session.setAttribute("closeOnHandForm", form);
			request.setAttribute("ohd_ID", ohd_ID);
			boolean batchResults = false;
			if (oList != null && oList.size() > 0 ){// && action != null && action.length() > 0){
				batchResults = batchCloseOnhands(bs, theform, request, user);
			}			
			return (mapping.findForward(TracingConstants.CLOSE_ON_HAND_SUCCESS));//this page shows successful and failed closing onhand number info
		}else{
			return (mapping.findForward(TracingConstants.ONHAND_LIST));
		}
	}
	
	/**
	 * This method is responsible for setting and controlling the workflow for batch close onhand bags:
	 * It loops through the onhand bag list, close one after another, and if any failed, it is remembered 
	 * but the flow continues to the next onhand bag. Finally, the successfully closed and failed to close 
	 * onhand bags IDs are displayed on the next flow UI.
	 * 
	 */
	private boolean batchCloseOnhands(BagService bs, CloseOnHandForm form, HttpServletRequest request, Agent user){
		boolean batchResults = false;
		StringBuffer sb = new StringBuffer();
		StringBuffer sbFailed = new StringBuffer();
		String onhandnum = null;
		List<LabelValueBean> ohds = form.getOhdList();			
		Status ohdStatus = new Status();
		ohdStatus.setStatus_ID(TracingConstants.OHD_STATUS_CLOSED);
		Status disposalStatus = new Status();
		disposalStatus.setStatus_ID(TracingConstants.OHD_DISPOSAL_STATUS_OTHER);
		
		Remark r = new Remark();
		r.setCreatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(TracerDateTime.getGMTDate()));
		r.setAgent(user);
		r.set_DATEFORMAT(user.getDateformat().getFormat());
		r.set_TIMEFORMAT(user.getTimeformat().getFormat());
		r.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone()));
		r.setRemarktext("The onhand is batch closed.");
		List<Remark> remarksList= form.getRemarklist();
		for (LabelValueBean ohdBean: ohds) {
			try{
				onhandnum = ohdBean.getLabel();
				OHD oDTO = bs.findOHDByID(onhandnum);;
				if(oDTO == null) {
					throw new Exception("Not found from db: " + onhandnum);
				}
				Set<Remark> remarks= oDTO.getRemarks();
				remarks.addAll(remarksList);
				remarks.add(r);
				oDTO.setStatus(ohdStatus);
				oDTO.setDisposal_status(disposalStatus);
				oDTO.setRemarks(remarks);
				bs.insertOnHandForBatchClose(oDTO, user);
				if(oDTO.getStatus().getStatus_ID() == TracingConstants.OHD_STATUS_CLOSED && oDTO.getWtFile() != null && !oDTO.getWtFile().getWt_status().equals(WTStatus.CLOSED)) {
					WtqOhdAction wtq = new WtqCloseOhd();
					wtq.setAgent(user);
					wtq.setCreatedate(TracerDateTime.getGMTDate());
					wtq.setOhd(oDTO);
					WorldTracerQueueUtils.createOrReplaceQueue(wtq);
				}
				if (sb.length() == 0){
					sb.append(onhandnum);
				}else{
					sb.append("," + onhandnum);
				}				
			}catch(Exception x){
				logger.error("Batch close onhand bag error: " + x.getMessage());
				if (sbFailed.length() == 0){
					sbFailed.append(onhandnum);
				}else{
					sbFailed.append("," + onhandnum);
				}
			}
			batchResults = true;
		}//end loop thru ohds
		if (sb.length() > 0){
			closedOhd_ID = sb.toString();
			request.setAttribute("closedOhd_ID", closedOhd_ID);
		}
		if (sbFailed.length() > 0){
			notClosedOhd_ID = sbFailed.toString();
			request.setAttribute("notClosedOhd_ID", notClosedOhd_ID);
		}
		return batchResults;
	}
}