package com.bagnet.nettracer.tracing.service.impl;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.dao.DocumentDAO;
import com.bagnet.nettracer.tracing.dao.IncidentActivityDAO;
import com.bagnet.nettracer.tracing.dao.TemplateDAO;
import com.bagnet.nettracer.tracing.db.Address;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Passenger;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.communications.Activity;
import com.bagnet.nettracer.tracing.db.communications.IncidentActivity;
import com.bagnet.nettracer.tracing.db.communications.IncidentActivityRemark;
import com.bagnet.nettracer.tracing.db.documents.Document;
import com.bagnet.nettracer.tracing.db.documents.templates.Template;
import com.bagnet.nettracer.tracing.db.taskmanager.IncidentActivityTask;
import com.bagnet.nettracer.tracing.dto.IncidentActivityTaskDTO;
import com.bagnet.nettracer.tracing.dto.IncidentActivityTaskSearchDTO;
import com.bagnet.nettracer.tracing.dto.OptionDTO;
import com.bagnet.nettracer.tracing.service.IncidentActivityService;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.DomainUtils;
import com.bagnet.nettracer.tracing.utils.taskmanager.TaskManagerUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

public class IncidentActivityServiceImpl implements IncidentActivityService {

	private Logger logger = Logger.getLogger(IncidentActivityServiceImpl.class);
	
	private DocumentDAO documentDao;
	private IncidentActivityDAO incidentActivityDao;
	private TemplateDAO templateDao;
	
	private final Status STATUS_PENDING = new Status(TracingConstants.STATUS_CUSTOMER_COMM_PENDING);
	private final Status STATUS_DENIED = new Status(TracingConstants.STATUS_CUSTOMER_COMM_DENIED);
	private final Status FINANCE_STATUS_REJECTED = new Status(TracingConstants.FINANCE_STATUS_REJECTED);
	private final Status FINANCE_STATUS_FRAUD_REVIEW = new Status(TracingConstants.FINANCE_STATUS_FRAUD_REVIEW);
	private final Status FINANCE_STATUS_SUPERVISOR_REVIEW = new Status(TracingConstants.FINANCE_STATUS_SUPERVISOR_REVIEW);
	private final Status FINANCE_STATUS_AWAITING_DISBURSEMENT = new Status(TracingConstants.FINANCE_STATUS_AWAITING_DISBURSEMENT);
	private final Status STATUS_PENDING_PRINT = new Status(TracingConstants.STATUS_CUSTOMER_COMM_PENDING_PRINT);
	private final Status STATUS_PENDING_WP = new Status(TracingConstants.STATUS_CUSTOMER_COMM_PENDING_WP);
	private final Status STATUS_APPROVED = new Status(TracingConstants.STATUS_CUSTOMER_COMM_APPROVED);
	private final Status STATUS_FRAUD_APPROVED = new Status(TracingConstants.FINANCE_STATUS_FRAUD_APPROVED);
	private final Status STATUS_SUPERVISOR_APPROVED = new Status(TracingConstants.FINANCE_STATUS_SUPERVISOR_APPROVED);
	private final Status FINANCE_STATUS_APPROVED = new Status(TracingConstants.FINANCE_STATUS_FINANCE_APPROVED);
	private final Status FINANCE_STATUS_FINANCE_REJECTED = new Status(TracingConstants.FINANCE_STATUS_FINANCE_REJECTED);
	private final Status FINANCE_STATUS_FRAUD_REJECTED = new Status(TracingConstants.FINANCE_STATUS_FRAUD_REJECTED);
	private final Status FINANCE_STATUS_SUPERVISOR_REJECTED = new Status(TracingConstants.FINANCE_STATUS_SUPERVISOR_REJECTED);
	
	
	@Override
	public IncidentActivity load(long incidentActivityId) {
		return incidentActivityDao.load(incidentActivityId);
	}

	@Override
	public long save(IncidentActivity incidentActivity) {
		Incident incident = new IncidentBMO().findIncidentByID(incidentActivity.getIncident().getIncident_ID());
		if (incident == null) return 0;
		
		incidentActivity.setIncident(incident);
		
		String activityCode = incidentActivity.getActivity().getCode();
		if (TracingConstants.ACTIVITY_CUSTOMER_COMMUNICATION.equals(activityCode) || TracingConstants.CREATE_SETTLEMENT_ACTIVITY.equals(activityCode)) {
			Template template = templateDao.load(incidentActivity.getDocument().getTemplate().getId());
			if (template == null) return 0;
			
			Document document = incidentActivity.getDocument();
			document.setTemplate(template);
			if (documentDao.save(document) == 0) return 0;
			
			incidentActivity.setDocument(document);
		} else if (TracingConstants.ACTIVITY_ASSIGNED_TO.equals(activityCode)) {
			incidentActivity.setDescription(incidentActivity.getDescription() + ": " + incident.getAgent().getUsername());
		}
		return incidentActivityDao.save(incidentActivity);
	}

	@Override
	public boolean update(IncidentActivity incidentActivity) {
		Document document = documentDao.load(incidentActivity.getDocument().getId());
		if (document == null) return false;
		
		Document newDocument = incidentActivity.getDocument();
		document.setTitle(newDocument.getTitle());
		document.setContent(newDocument.getContent());
		if (!documentDao.update(document)) return false;
		
		return incidentActivityDao.update(incidentActivity);
	}

	@Override
	public boolean delete(long incidentActivityId) {
		List<IncidentActivityTask> tasks = incidentActivityDao.loadTasksForIncidentActivity(incidentActivityDao.load(incidentActivityId));
		return incidentActivityDao.deleteTasks(tasks) && incidentActivityDao.delete(incidentActivityId);
	}
	
	@Override
	public IncidentActivityTask loadTask(long incidentActivityTaskId) {
		return incidentActivityDao.loadTask(incidentActivityTaskId);
	}

	@Override
	public long saveTask(IncidentActivityTask incidentActivityTask) {
		return incidentActivityDao.saveTask(incidentActivityTask);
	}

	@Override
	public boolean updateTask(IncidentActivityTask incidentActivityTask) {
		return incidentActivityDao.updateTask(incidentActivityTask);
	}

	@Override
	public boolean deleteTask(long incidentActivityTaskId) {
		return incidentActivityDao.deleteTask(incidentActivityTaskId);
	}
	
	@Override
	public List<OptionDTO> getActivityOptions() {
		List<Activity> fromDb = incidentActivityDao.getActivities();
		List<OptionDTO> options = new ArrayList<OptionDTO>();
		if (!fromDb.isEmpty()) {
			for (Activity a: fromDb) {
				/** Removes Create Claim Settlement from Activity Selection so Users must go through Expenses to create one **/
				if(!a.getCode().equals(TracingConstants.CREATE_SETTLEMENT_ACTIVITY)){
					options.add(new OptionDTO(a.getCode(), a.getDescription()));
				}
			}
		}
		return options;
	}
	
	@Override
	public Activity getActivity(String code) {
		return incidentActivityDao.getActivity(code);
	}

	/** Search for a specific Activity by the Activity Code in an Incident **/
	@Override
	public IncidentActivity loadByActivityCode(String activityCode, String incident_ID) {
		return incidentActivityDao.loadByActivityCode(activityCode,incident_ID);
	}
	
	@Override
	public void writeOptionsList(List<OptionDTO> options, HttpServletResponse response) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			response.setContentType("text/x-json;charset=UTF-8");
			
			PrintWriter out = response.getWriter();
			out.println(mapper.writeValueAsString(options));
			out.flush();
		} catch (Exception e) {
			logger.error("Error caught trying to return the activity option list", e);
		}
	}
	
	@Override
	public boolean activityBelongsToIncident(long incidentActivityId, String incidentId) {
		IncidentActivity ia = load(incidentActivityId);
		if (ia == null || ia.getIncident() == null) return false;
		
		return incidentId.equals(ia.getIncident().getIncident_ID());
	}
	
	@Override
	public int getIncidentActivityAwaitingApprovalCount() {
		return incidentActivityDao.getIncidentActivityCount(STATUS_PENDING);
	}

	@Override
	public int getIncidentActivityRejectionCount(Agent agent) {
		return incidentActivityDao.getIncidentActivityCountByUser(agent, STATUS_DENIED);
	}
	
	@Override
	public int getIncidentActivityFraudReviewCount() {
		return incidentActivityDao.getIncidentActivityCount(FINANCE_STATUS_FRAUD_REVIEW);
	}
	
	@Override
	public int getIncidentActivitySupervisorReviewCount() {
		return incidentActivityDao.getIncidentActivityCount(FINANCE_STATUS_SUPERVISOR_REVIEW);
	}
	
	@Override
	public int getIncidentActivityAwaitingDisbursementCount() {
		return incidentActivityDao.getIncidentActivityCount(FINANCE_STATUS_AWAITING_DISBURSEMENT);
	}
	
	@Override
	public int getDisbursementRejectTaskCount(Agent agent) {
		return incidentActivityDao.getIncidentActivityCountByUser(agent, FINANCE_STATUS_REJECTED);
	}

	@Override
	public boolean hasIncidentActivityTask(IncidentActivity incidentActivity) {
		if (incidentActivity == null) return false;		
		return incidentActivityDao.hasTask(incidentActivity,STATUS_PENDING,FINANCE_STATUS_SUPERVISOR_REVIEW,FINANCE_STATUS_FRAUD_REVIEW,FINANCE_STATUS_AWAITING_DISBURSEMENT);
	}
	
	@Override
	public int getIncidentActivityTaskCount(IncidentActivityTaskSearchDTO dto) {
		return incidentActivityDao.getIncidentActivityTaskCount(dto);
	}
	
	@Override
	public boolean createTask(IncidentActivity incidentActivity, Status withStatus) {
		return createTask(incidentActivity, withStatus, AdminUtils.getAgentBasedOnUsername("ogadmin", "OW"));
	}
	
	@Override
	public boolean createTask(IncidentActivity incidentActivity, Status withStatus, Agent forAgent) {
		return createTask(incidentActivity, withStatus, forAgent, true);
	}
	
	@Override
	public boolean createTask(IncidentActivity incidentActivity, Status withStatus, Agent forAgent, boolean active) {
		if (incidentActivity == null || withStatus == null) return false;
		IncidentActivityTask iat = DomainUtils.createIncidentActivityTask(incidentActivity, withStatus, active);
		iat.setAssigned_agent(forAgent);
		boolean success = saveTask(iat) != 0;
		incidentActivity.getTasks().add(iat);
		return success;
	}
	
	@Override
	public boolean closeTask(long taskId) {
		IncidentActivityTask toClose = incidentActivityDao.loadTask(taskId);
		if (toClose == null) return true;
		toClose.setActive(false);
		toClose.setClosed_timestamp(DateUtils.convertToGMTDate(new Date()));
		toClose.getIncidentActivity().setApprovalAgent(toClose.getAssigned_agent());
		return incidentActivityDao.update(toClose.getIncidentActivity()) && incidentActivityDao.updateTask(toClose);
	}
	
	@Override
	public List<IncidentActivityTaskDTO> listRejectedIncidentActivityTasks(IncidentActivityTaskSearchDTO dto) {
		List<IncidentActivityTaskDTO> tasks = new ArrayList<IncidentActivityTaskDTO>();
		List<IncidentActivityTask> fromDb = incidentActivityDao.listIncidentActivityTasks(dto);
		for (IncidentActivityTask iat: fromDb) {
			IncidentActivityTaskDTO iatdto = createIncidentActivityTaskDTO(dto);
			iatdto.setId(iat.getTask_id());
			iatdto.setIncidentActivityId(iat.getIncidentActivity().getId());
			iatdto.setIncidentId(iat.getIncidentActivity().getIncident().getIncident_ID());
			iatdto.setDescription(iat.getIncidentActivity().getDescription());
			iatdto.setSpecialist(iat.getIncidentActivity().getAgent().getUsername());
			iatdto.setTaskDate(iat.getOpened_timestamp());
			iatdto.setStatus(iat.getStatus().getKey());
			if (iat.getIncidentActivity().getApprovalAgent() != null) {
				iatdto.setAgent(iat.getIncidentActivity().getApprovalAgent().getUsername());
			}
			if(iat.getIncidentActivity().getExpensePayout()!=null){
				iatdto.setExpenseId(iat.getIncidentActivity().getExpensePayout().getExpensepayout_ID());
			}
			tasks.add(iatdto);
		}
		return tasks;
	}
	
	@Override
	public List<IncidentActivityTaskDTO> listPendingIncidentActivityTasks(IncidentActivityTaskSearchDTO dto) {
		List<IncidentActivityTaskDTO> tasks = new ArrayList<IncidentActivityTaskDTO>();
		List<IncidentActivityTask> fromDb = incidentActivityDao.listIncidentActivityTasks(dto);
		for (IncidentActivityTask iat: fromDb) {
			IncidentActivityTaskDTO iatdto = createIncidentActivityTaskDTO(dto);
			iatdto.setId(iat.getIncidentActivity().getId());
			iatdto.setIncidentId(iat.getIncidentActivity().getIncident().getIncident_ID());
			iatdto.setTaskid(iat.getTask_id()); 
			iatdto.setDescription(iat.getIncidentActivity().getDescription());
			iatdto.setTaskDate(iat.getOpened_timestamp());
			iatdto.setSpecialist(iat.getIncidentActivity().getAgent().getUsername());
			iatdto.setStatus(String.valueOf(iat.getStatus().getStatus_ID()));
			
			if (iat.getIncidentActivity().getApprovalAgent() != null) {
				iatdto.setApprover(iat.getIncidentActivity().getApprovalAgent().getUsername()); 
			}

			/** Passenger Information **/
			if(iat.getIncidentActivity().getIncident().getPassenger_list().size()>0){
				Passenger p=iat.getIncidentActivity().getIncident().getPassenger_list().get(0);
				iatdto.setName(p.getFirstname()+" "+p.getLastname());
				if(p.getAddresses()!=null && p.getAddresses().size()>0){
					Address a=p.getAddress(0);
					iatdto.setAddress(a.getAddress1());
					iatdto.setAptnum(a.getAddress2());
					iatdto.setCity(a.getCity());
					iatdto.setState(a.getState());
					iatdto.setZip(a.getZip());
				}
			}
			
			/**
			 * TOBEUPDATED: This is controlled by what Reservation System the
			 * incident information is pulled from. This dynamic reservation
			 * system isn't implemented yet, so will need to update this section
			 * once that feature is implemented. Currently will default to User's company code
			 **/
			iatdto.setAirline(iat.getIncidentActivity().getIncident().getAgent().getCompanycode_ID()); 
			iatdto.setPnr(iat.getIncidentActivity().getIncident().getRecordlocator());
			
			if(iat.getIncidentActivity().getIncident().getItemtype_ID()==TracingConstants.LOST_DELAY){
				iatdto.setReason(TracingConstants.REASON_LOSS);
			} else if(iat.getIncidentActivity().getIncident().getItemtype_ID()==TracingConstants.MISSING_ARTICLES){
				iatdto.setReason(TracingConstants.REASON_MISSING);
			} else if(iat.getIncidentActivity().getIncident().getItemtype_ID()==TracingConstants.DAMAGED_BAG){
				iatdto.setReason(TracingConstants.REASON_DAMAGED);
			}
			
			/** Expense Information **/
			if(iat.getIncidentActivity().getExpensePayout()!=null){
				iatdto.setExpenseId(iat.getIncidentActivity().getExpensePayout().getExpensepayout_ID());
				iatdto.setExpensedraft(iat.getIncidentActivity().getExpensePayout().getDraft());
				iatdto.setExpensedraftdate(iat.getIncidentActivity().getExpensePayout().getDisdraftpaiddate());
				iatdto.setExpensemaildate(iat.getIncidentActivity().getExpensePayout().getDismaildate());
				iatdto.setExpensecheckamt(iat.getIncidentActivity().getExpensePayout().getCheckamt());
				iatdto.setExpensevoucheramt(iat.getIncidentActivity().getExpensePayout().getVoucheramt());
			}
			if(iat.getIncidentActivity().getLastPrinted()!=null){
				iatdto.setLastPrinted(DateUtils.formatDate(iat.getIncidentActivity().getLastPrinted(), dto.get_DATEFORMAT(), null,null));
			}

			tasks.add(iatdto);
		}
		return tasks;
	}
	
	@Override
	public IncidentActivityTask loadTaskForIncidentActivity(long incidentActivityId, Status withStatus) {
		return incidentActivityDao.loadTaskForIncidentActivity(load(incidentActivityId), withStatus);
	}
	
	@Override
	public IncidentActivityTask loadTaskForIncidentActivity(IncidentActivity incidentActivity, Status withStatus) {
		if (incidentActivity == null || withStatus == null) return null;
		return incidentActivityDao.loadTaskForIncidentActivity(incidentActivity, withStatus);
	}
	
	@Override
	public boolean createIncidentActivityRemark(String remark, IncidentActivity forActivity, Agent madeBy) {
		boolean success = false;
		IncidentActivityRemark iar = new IncidentActivityRemark();
		iar.setRemarkText(remark);
		iar.setIncidentActivity(forActivity);
		iar.setAgent(madeBy);
		iar.setCreateDate(DateUtils.convertToGMTDate(new Date()));
		try {
			success = incidentActivityDao.save(iar) != 0;
		} catch (Exception e) {
			logger.error("Failed to create remark.", e);
		}
		return success;
	}
	
	@Override
	public IncidentActivityTask getAssignedTask(Agent agent) {
		return incidentActivityDao.getAssignedTask(agent);
	}
	
	@Override
	public IncidentActivityTask getAssignedTask(Agent agent, Status s) {
		if (agent == null) return null;
		return incidentActivityDao.getAssignedTask(agent, s);
	}
	
	@Override
	public IncidentActivityTask getTask(Agent agent, Status status) {
		IncidentActivityTask task = incidentActivityDao.getTask(status);
		return startTask(task, agent);
	}
	
	@Override
	public IncidentActivityTask startTask(long incidentActivityId, Agent agent, Status s) {
		return startTask(loadTaskForIncidentActivity(incidentActivityId, s), agent);
	}
	
	private IncidentActivityTask startTask(IncidentActivityTask task, Agent agent) {
		if (task == null || agent == null) return null;
		
		task.setAssigned_agent(agent);
		if (task.getOpened_timestamp() == null) {
			task.setOpened_timestamp(DateUtils.convertToGMTDate(new Date()));
		}
		
		task = (IncidentActivityTask) TaskManagerUtil.lockTask(task);
		if (task != null) {
			updateTask(task);
			return task;
		}
		
		return null;
	}
	
	private IncidentActivityTaskDTO createIncidentActivityTaskDTO(IncidentActivityTaskSearchDTO dto) {
		IncidentActivityTaskDTO toReturn = new IncidentActivityTaskDTO();
		toReturn.set_DATEFORMAT(dto.get_DATEFORMAT());
		toReturn.set_TIMEFORMAT(dto.get_TIMEFORMAT());
		toReturn.set_TIMEZONE(dto.get_TIMEZONE());
		return toReturn;
	}
	
	@Override
	public List<IncidentActivity> loadActivities(List<Long> idlist){
		return incidentActivityDao.listIncidentActivities(idlist);
	}
	
	@Override
	public List<IncidentActivityTask> loadActivityTasks(List<Long> idlist){
		return incidentActivityDao.listIncidentActivityTasks(idlist);
	}

	@Override
	public boolean saveActivities(List<IncidentActivity> ialist){
		return incidentActivityDao.saveIncidentActivities(ialist);
	}
	
	@Override
	public boolean saveActivityTasks(List<IncidentActivityTask> iatlist){
		return incidentActivityDao.saveIncidentActivityTasks(iatlist);
	}
	
	public DocumentDAO getDocumentDao() {
		return documentDao;
	}

	public void setDocumentDao(DocumentDAO documentDao) {
		this.documentDao = documentDao;
	}

	public IncidentActivityDAO getIncidentActivityDao() {
		return incidentActivityDao;
	}

	public void setIncidentActivityDao(IncidentActivityDAO incidentActivityDao) {
		this.incidentActivityDao = incidentActivityDao;
	}

	public TemplateDAO getTemplateDao() {
		return templateDao;
	}

	public void setTemplateDao(TemplateDAO templateDao) {
		this.templateDao = templateDao;
	}
	
	public boolean handleTask(long incidentActivityTaskId, boolean approved) {
		closeTask(incidentActivityTaskId);
		IncidentActivityTask iat = loadTask(incidentActivityTaskId);
		if (iat != null) {
			iat.getIncidentActivity().setApprovalAgent(iat.getAssigned_agent());
			return approved ? handleApproveTask(iat) : handleRejectTask(iat);
		}
		logger.error("Failed to load task with id: " + incidentActivityTaskId);
		return false;
	}

	private boolean handleApproveTask(IncidentActivityTask iat) {

		IncidentActivity ia=iat.getIncidentActivity();
		ia.setApprovalAgent(iat.getAssigned_agent());
		int statusId=iat.getStatus().getStatus_ID();
		Status s=STATUS_APPROVED;
		if(statusId==TracingConstants.FINANCE_STATUS_FRAUD_REVIEW){
			s=STATUS_FRAUD_APPROVED;
		} else if (statusId==TracingConstants.FINANCE_STATUS_SUPERVISOR_REVIEW){
			s=STATUS_SUPERVISOR_APPROVED;
		} else if (statusId==TracingConstants.FINANCE_STATUS_AWAITING_DISBURSEMENT){
			s=FINANCE_STATUS_APPROVED;
		}
		
		if (!createTask(ia, s, iat.getAssigned_agent(), false)) {
			logger.error("Failed to create an approval task for IncidentActivity: " + iat.getTask_id());
		}

		if(statusId==TracingConstants.FINANCE_STATUS_FRAUD_REVIEW){
			return createTask(ia, FINANCE_STATUS_SUPERVISOR_REVIEW);
		} else if (statusId==TracingConstants.FINANCE_STATUS_SUPERVISOR_REVIEW){
			return createTask(ia, FINANCE_STATUS_AWAITING_DISBURSEMENT);
		}
		
		switch(ia.getCustCommId()) {
			case TracingConstants.CUST_COMM_POSTAL_MAIL:
				return createTask(ia, STATUS_PENDING_PRINT);
			case TracingConstants.CUST_COMM_WEB_PORTAL:
//				try {
//					OCFile file=documentService.publishDocument(ia);
//					if(file!=null){
//						OnlineClaimsDao dao=new OnlineClaimsDao();
//						if(!dao.saveFile(file)){
//							logger.error("Failed to email customer about communication for IncidentActivity with id: "+ ia.getId());
//						} else {
//							if(!EmailUtils.sendIncidentActivityEmail(ia, realpath)){
//								logger.error("Failed to email customer about communication for IncidentActivity with id: "+ ia.getId());
//							}
//						}
//					}
//				} catch (InsufficientInformationException e1) {
//					logger.error("Failed to publish document for IncidentActivity with id: "+ia.getId());
//					e1.printStackTrace();
//				}
//				
				return createTask(ia, STATUS_PENDING_WP);
			default:
				logger.error("Invalid value found for customer communication method id: " + ia.getCustCommId());
				return false;
		}
	}

	private boolean handleRejectTask(IncidentActivityTask iat) {
		IncidentActivity ia=iat.getIncidentActivity();
		ia.setApprovalAgent(iat.getAssigned_agent());
		int statusId=iat.getStatus().getStatus_ID();
		if(statusId==TracingConstants.FINANCE_STATUS_FRAUD_REVIEW){
			return createTask(ia, FINANCE_STATUS_FRAUD_REJECTED, ia.getAgent());
		} else if(statusId==TracingConstants.FINANCE_STATUS_SUPERVISOR_REVIEW){
			return createTask(ia, FINANCE_STATUS_SUPERVISOR_REJECTED, ia.getAgent());
		} else if(statusId==TracingConstants.FINANCE_STATUS_AWAITING_DISBURSEMENT){
			return createTask(ia, FINANCE_STATUS_FINANCE_REJECTED, ia.getAgent());
		}
		return createTask(ia, STATUS_DENIED, ia.getAgent());
	}
	
	public boolean handleRemark(long incidentActivityTaskId, String remark, Agent madeBy) {
		if (remark == null || remark.isEmpty()) return true;
		
		closeTask(incidentActivityTaskId);
		IncidentActivityTask iat = loadTask(incidentActivityTaskId);
		if (iat != null) {
			return createIncidentActivityRemark(remark, iat.getIncidentActivity(), madeBy);
		}
		return false;
	}
	

}
