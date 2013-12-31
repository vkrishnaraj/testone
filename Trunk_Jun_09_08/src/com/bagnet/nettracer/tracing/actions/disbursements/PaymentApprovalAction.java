package com.bagnet.nettracer.tracing.actions.disbursements;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.bagnet.nettracer.tracing.actions.CheckedAction;
import com.bagnet.nettracer.tracing.bmo.ExpensePayoutBMO;
import com.bagnet.nettracer.tracing.bmo.ReportBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.dao.OnlineClaimsDao;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.ExpensePayout;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.communications.IncidentActivity;
import com.bagnet.nettracer.tracing.db.onlineclaims.OCFile;
import com.bagnet.nettracer.tracing.db.taskmanager.IncidentActivityTask;
import com.bagnet.nettracer.tracing.dto.IncidentActivityTaskDTO;
import com.bagnet.nettracer.tracing.dto.IncidentActivityTaskSearchDTO;
import com.bagnet.nettracer.tracing.exceptions.InsufficientInformationException;
import com.bagnet.nettracer.tracing.forms.disbursements.PaymentApprovalForm;
import com.bagnet.nettracer.tracing.service.DocumentService;
import com.bagnet.nettracer.tracing.service.IncidentActivityService;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.DomainUtils;
import com.bagnet.nettracer.tracing.utils.EmailUtils;
import com.bagnet.nettracer.tracing.utils.SpringUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;

public class PaymentApprovalAction extends CheckedAction {
	
	private IncidentActivityService incidentActivityService = (IncidentActivityService) SpringUtils.getBean(TracingConstants.INCIDENT_ACTIVITY_SERVICE_BEAN);
	private DocumentService documentService = (DocumentService) SpringUtils.getBean(TracingConstants.DOCUMENT_SERVICE_BEAN);
	private Logger logger = Logger.getLogger(PaymentApprovalAction.class);
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		TracerUtils.checkSession(session);
		ActionMessages errors = new ActionMessages();
		
		Agent user = (Agent) session.getAttribute("user");
		if (user == null || form == null) {
			response.sendRedirect("logoff.do");
			return null;
		}
		
		if (!UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_PAYMENT_APPROVAL, user))
			return (mapping.findForward(TracingConstants.NO_PERMISSION));
		
		PaymentApprovalForm paf = (PaymentApprovalForm) form;
		
		Status s;
		if(paf.getRejectId()!=null && !paf.getRejectId().isEmpty()  
				&& paf.getRejectRemark()!=null && !paf.getRejectRemark().isEmpty()){
			s=new Status();
			s.setStatus_ID(TracingConstants.FINANCE_STATUS_FINANCE_REJECTED);
			/** SF: Agent needs to start the task to approve/reject it**/
			IncidentActivityTask iat=incidentActivityService.startTask(Long.valueOf(paf.getRejectId()), user,new Status(TracingConstants.FINANCE_STATUS_AWAITING_DISBURSEMENT));
			if(iat!=null){
				/** SF: Sets the assigned user to whoever is updating the Tasks from the Payment Approval Page **/
				boolean success = incidentActivityService.handleTask(iat.getTask_id(),false);
				if (success) {
					success = incidentActivityService.handleRemark(iat.getTask_id(), paf.getRejectRemark(), user);
				} else {
					logger.error("Failed to reject task with ID: "+iat.getTask_id());
					ActionMessage error = new ActionMessage("");
					error = new ActionMessage("message.unable.to.save.finance.task");
					
					errors.add(ActionMessages.GLOBAL_MESSAGE, error);
					saveMessages(request, errors);
				} 
			} else {
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.unable.to.save.finance.task"));
				saveMessages(request, errors);
			}
		}
		
		if(request.getParameter("updateExpense")!=null && paf.getIatlist()!=null && paf.getIatlist().size()>0){
			boolean success=true;
			List<Long> iatidlist=new ArrayList<Long>();
			boolean sameDraftFormat=false;
			boolean sameMailFormat=false;
			for(IncidentActivityTaskDTO iatdto:paf.getIatlist()){
				sameDraftFormat=false;
				sameMailFormat=false;
				
				if(iatdto.getExpensedraftdate()!=null && !iatdto.getExpensedraftdate().isEmpty()){
					sameDraftFormat=DateUtils.convertToDate(iatdto.getExpensedraftdate(), user.getDateformat().getFormat(), null)!=null;
				}

				if(iatdto.getExpensemaildate()!=null && !iatdto.getExpensemaildate().isEmpty()){
					sameMailFormat=DateUtils.convertToDate(iatdto.getExpensemaildate(), user.getDateformat().getFormat(), null)!=null;
				}
				
				if(iatdto.getExpensedraft()!=null && !iatdto.getExpensedraft().isEmpty() && sameDraftFormat && sameMailFormat){
					iatidlist.add(iatdto.getTaskid());
				}
			}
			List<IncidentActivityTask> iatlist=null;
			if(iatidlist!=null && iatidlist.size()>0){
				iatlist=incidentActivityService.loadActivityTasks(iatidlist);
			}

			if(iatlist!=null){
				Map<Long, IncidentActivityTask> iatmap=new HashMap<Long,IncidentActivityTask>();
				for(IncidentActivityTask iat:iatlist){
					iatmap.put(iat.getTask_id(), iat);
					
				}
				OnlineClaimsDao dao = new OnlineClaimsDao();
				ExpensePayout ep=null;
				for(IncidentActivityTaskDTO iatdto:paf.getIatlist()){

					IncidentActivityTask iat=iatmap.get(iatdto.getTaskid());
					if(iat!=null){
						ep=null;
						/** SF: Agent needs to start task to approve it**/
						if(incidentActivityService.startTask(iat, user)!=null){
							success = incidentActivityService.handleTask(iat.getTask_id(),true);
							if (success) {
								ep=iat.getIncidentActivity().getExpensePayout();
								if(ep!=null){
									//Update Expense values and set status to paid
									ep.set_DATEFORMAT(user.getDateformat().getFormat());
									ep.setDraft(iatdto.getExpensedraft());
									if(iatdto.getExpensedraftdate()!=null && !iatdto.getExpensedraftdate().isEmpty()){
										ep.setDisdraftpaiddate(iatdto.getExpensedraftdate());
									}
									if(iatdto.getExpensemaildate()!=null && !iatdto.getExpensemaildate().isEmpty()){
										ep.setDismaildate(iatdto.getExpensemaildate());
									}
									ep.setStatus(new Status(TracingConstants.EXPENSEPAYOUT_STATUS_PAID));
		
									success=ExpensePayoutBMO.updateExpense(ep,user);
									if(!success){
										logger.error("Failed to updating expense with Task ID: "+iat.getTask_id());
									}
								}
								if(ep!=null && success){
									ServletContext sc = getServlet().getServletContext();
									String realpath = sc.getRealPath("/");
									try {
										IncidentActivity ia=iat.getIncidentActivity();
										if(ia.getCustCommId()==TracingConstants.CUST_COMM_WEB_PORTAL){
											OCFile file = documentService.publishDocument(ia);
											if (file != null) {
												if (!dao.saveFile(file)) {
													logger.error("Failed to email customer about communication for IncidentActivity with id: " + ia.getId());
												} else {
													if (!EmailUtils.sendIncidentActivityEmail(ia, realpath)) {
														logger.error("Failed to email customer about communication for IncidentActivity with id: " + ia.getId());
													}
												}
											}
										}
									} catch (InsufficientInformationException e1) {
										logger.error("Failed to publish document for IncidentActivity Task with id: " + iat.getTask_id());
										e1.printStackTrace();
									}
								}
								if(!success){
									logger.error("Failure handling remark with Incident Activity Task with ID: "+iat.getTask_id());
								}
							}
						}
					}

				}
				
				if(!success){
					ActionMessage error = new ActionMessage("");
					error = new ActionMessage("message.unable.to.save.finance.task");
					
					errors.add(ActionMessages.GLOBAL_MESSAGE, error);
					saveMessages(request, errors);
				}
			}
		}
		
		
		List<IncidentActivityTaskDTO> results = new ArrayList<IncidentActivityTaskDTO>();
		IncidentActivityTaskSearchDTO dto = DomainUtils.fromForm(paf);
		dto.setStatus(new Status(TracingConstants.FINANCE_STATUS_AWAITING_DISBURSEMENT));
		dto.setActive(true);
		dto.setFinancial(true);
		setUserInfoOnDto(user, dto);
		getSortCriteria(dto, request);
		
		int currpage = 0;
		int rowcount = incidentActivityService.getIncidentActivityTaskCount(dto);
		int rowsperpage = TracerUtils.manageRowsPerPage(request.getParameter("rowsperpage"), TracingConstants.ROWS_SEARCH_PAGES, session);

		if (rowcount > 0) {
			currpage = paf.getCurrpage() != null ? Integer.parseInt(paf.getCurrpage()) : 0;
			if (paf.getNextpage() != null && paf.getNextpage().equals("1")) {
				currpage++;
			}
	
			if (paf.getPrevpage() != null && paf.getPrevpage().equals("1")) {
				currpage--;
			}
	
			dto.setCurrentPage(currpage);
			dto.setRowsPerPage(rowsperpage);
	
			int totalpages = (int) Math.ceil((double) rowcount / (double) rowsperpage);		
			if (totalpages <= currpage) {
				currpage = 0;
				request.setAttribute("currpage", "0");
			}
			
			boolean end = currpage + 1 == totalpages && totalpages > 1;
			if (end)
				request.setAttribute("end", "1");
			if (totalpages > 1) {
				ArrayList<String> al = new ArrayList<String>();
				for (int j = 0; j < totalpages; j++) {
					al.add(Integer.toString(j));
				}
				request.setAttribute("pages", al);
			}
			results = incidentActivityService.listPendingIncidentActivityTasks(dto);
			paf.setIatlist(results);
		} else {

			paf.setIatlist(new ArrayList<IncidentActivityTaskDTO>());
		}
		
		if (request.getParameter("generateReport") != null && 
				request.getParameter("outputtype") != null) {
			dto.setRowsPerPage(0);
			results = incidentActivityService.listPendingIncidentActivityTasks(dto);
			ServletContext sc = getServlet().getServletContext();
			String reportpath = sc.getRealPath("/");
			String reportfile = null;
			ReportBMO rBMO = new ReportBMO(request);
			int outputtype=Integer.valueOf(request.getParameter("outputtype"));
			
			reportfile = rBMO.createPaymentApprovalReport(results,outputtype, user.getCurrentlocale(),reportpath,user);
			
			if (reportfile == null || reportfile.equals("")) {
				//no data to report
				if (rBMO.getErrormsg() != null && rBMO.getErrormsg().length() > 0) {
					ActionMessage error = new ActionMessage(rBMO.getErrormsg());
					errors.add(ActionMessages.GLOBAL_MESSAGE,error);
				} else {
					ActionMessage error = new ActionMessage("message.nodata");
					errors.add(ActionMessages.GLOBAL_MESSAGE, error);
				}
				
				saveMessages(request, errors);
			} else {
				request.setAttribute("reportfile", reportfile);
				if (request.getAttribute("outputtype") == null) {
					request.setAttribute("outputtype", request.getParameter("outputtype"));
				}
				List<Long> actIdList=new ArrayList<Long>();
				for(IncidentActivityTaskDTO idto:results){
					actIdList.add(idto.getId());
				}
				List<IncidentActivity> ialist=incidentActivityService.loadActivities(actIdList);
				for(IncidentActivity ia:ialist){
					ia.setLastPrinted(new Date());
				}
				boolean success=incidentActivityService.saveActivities(ialist);
				if(!success){
					System.out.println("Last Printed setting failed");
				}
			}
		}
		
		request.setAttribute("rowsperpage", Integer.toString(rowsperpage));
		request.setAttribute("rowcount", Integer.toString(rowcount));
		request.setAttribute("currpage", Integer.toString(currpage));	
		paf.set_DATEFORMAT(user.getDateformat().getFormat());
		
		return mapping.findForward(TracingConstants.PAYMENT_APPROVAL);
	}
	
	private void setUserInfoOnDto(Agent user, IncidentActivityTaskSearchDTO dto) {
		dto.set_DATEFORMAT(user.getDateformat().getFormat());
		dto.set_TIMEFORMAT(user.getTimeformat().getFormat());
		dto.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone()));
	}
	
	private void getSortCriteria(IncidentActivityTaskSearchDTO dto, HttpServletRequest request) {
		String sort = request.getParameter("sort");
		request.setAttribute("sort", sort);
		String dir = request.getParameter("dir");
		request.setAttribute("dir", dir);
		dto.setDir(dir!=null ? dir:TracingConstants.SORT_ASCENDING);
		dto.setSort(sort != null ? sort : "incidentId");
	}

}