package com.bagnet.nettracer.tracing.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.dao.IncidentActivityDAO;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.communications.Activity;
import com.bagnet.nettracer.tracing.db.communications.IncidentActivity;
import com.bagnet.nettracer.tracing.db.communications.IncidentActivityRemark;
import com.bagnet.nettracer.tracing.db.onlineclaims.OCFile;
import com.bagnet.nettracer.tracing.db.onlineclaims.OCMessage;
import com.bagnet.nettracer.tracing.db.taskmanager.IncidentActivityTask;
import com.bagnet.nettracer.tracing.dto.IncidentActivityTaskSearchDTO;
import com.bagnet.nettracer.tracing.utils.DateUtils;

public class IncidentActivityDAOImpl implements IncidentActivityDAO {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private static Map<String, String> map = new LinkedHashMap<String, String>();
	private static Map<Integer, String> financestatusmap = new LinkedHashMap<Integer, String>();
	
	static {		
		map.put("id", 			"iat.id");
		map.put("incidentId", 	"i.incident_ID");
		map.put("agent", 		"ia.approvalAgent");
		map.put("date",			"iat.generic_timestamp");
		map.put("expenseId",    "ia.expensePayout");
		map.put("status",    	"ia.status");
		map.put("taskId",    	"iat.task_id");
		map.put("name",  		"p.firstname");
		map.put("address", 		"a.address1");
		map.put("amount", 		"e.checkamt");
		map.put("pnr", 			"i.recordlocator");
		map.put("reason", 		"i.itemtype");
		map.put("specialist",	"ia.agent");
		map.put("approver",		"ia.approvalAgent");
		
		financestatusmap.put(TracingConstants.FINANCE_STATUS_AWAITING_DISBURSEMENT, "1");
		financestatusmap.put(TracingConstants.FINANCE_STATUS_FINANCE_APPROVED, "1");
		financestatusmap.put(TracingConstants.FINANCE_STATUS_FINANCE_REJECTED, "1");
		financestatusmap.put(TracingConstants.FINANCE_STATUS_FRAUD_REJECTED, "1");
		financestatusmap.put(TracingConstants.FINANCE_STATUS_FRAUD_REVIEW, "1");
		financestatusmap.put(TracingConstants.FINANCE_STATUS_REJECTED, "1");
		financestatusmap.put(TracingConstants.FINANCE_STATUS_SUPERVISOR_REVIEW, "1");
		financestatusmap.put(TracingConstants.FINANCE_STATUS_SUPERVISOR_REJECTED, "1");
		financestatusmap.put(TracingConstants.FINANCE_STATUS_SUPERVISOR_APPROVED, "1");
		financestatusmap.put(TracingConstants.FINANCE_STATUS_FRAUD_APPROVED, "1");
	}
	
	@Override
	public long save(IncidentActivityRemark remark) {
		long id = 0;
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateWrapper.getSession().openSession();
			transaction = session.beginTransaction();
			session.save(remark);
			transaction.commit();
			id = remark.getId();
		} catch (Exception e) {
			logger.error("Exception caught while attempting to save incident activity remark.", e);
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return id;
	}
	
	@Override
	public IncidentActivity load(long incidentActivityId) {
		IncidentActivity incidentActivity = null;
		Session session = null;
		try {
			session = HibernateWrapper.getSession().openSession();
			incidentActivity = (IncidentActivity) session.get(IncidentActivity.class, incidentActivityId);			
		} catch (Exception e) {
			logger.error("Exception caught while attempting to load incident activity with id: " + incidentActivityId, e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return incidentActivity;
	}

	@Override
	public long save(IncidentActivity incidentActivity) {
		long id = 0;
		if (incidentActivity == null) {
			return id;
		}
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = HibernateWrapper.getSession().openSession();
			transaction = session.beginTransaction();	
			
			Activity activity = getActivityByCode(incidentActivity.getActivity().getCode());
			if (activity == null) return 0;
			
			incidentActivity.setActivity(activity);
			session.save(incidentActivity);
			transaction.commit();
			id = incidentActivity.getId();
		} catch (Exception e) {
			logger.error("Failed to save incident activity: " + incidentActivity.getDocument().getTitle(), e);
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return id;
	}

	@Override
	public boolean update(IncidentActivity incidentActivity) {
		boolean success = false;
		if (incidentActivity == null) {
			return success;
		}
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = HibernateWrapper.getSession().openSession();
			transaction = session.beginTransaction();
			IncidentActivity oldActivity = load(incidentActivity.getId());
			if (oldActivity == null) {
				logger.error("Failed to load incident activity with id: " + incidentActivity.getId());
				success = false;
			} else {
				incidentActivity.setCreateDate(oldActivity.getCreateDate());
				Activity activity = getActivityByCode(incidentActivity.getActivity().getCode());
				incidentActivity.setActivity(activity);
				if (activity != null) {
					session.merge(incidentActivity);
					transaction.commit();
					success = true;
				}
			}
		} catch (Exception e) {
			logger.error("Failed to update incident activity with id: " + incidentActivity.getId(), e);
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return success;
	}

	@Override
	public boolean delete(long incidentActivityId) {
		boolean success = false;
		if (incidentActivityId <= 0) return success;
		
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateWrapper.getSession().openSession();
			transaction = session.beginTransaction();
			IncidentActivity incidentActivity = (IncidentActivity) session.createCriteria(IncidentActivity.class, "ia").add(Restrictions.eq("ia.id", incidentActivityId)).uniqueResult();
			/**
			 * Because IncidentActivities are CascadeType.All on Incidents, they
			 * have to be removed from the Incident before they can be deleted
			 **/
			Incident inc=incidentActivity.getIncident();
			inc.getActivities().remove(incidentActivity);
			/**
			 * Because IncidentActivities are CascadeType.All on OCMessages and OCFiles, they
			 * have to be removed from the OCMessage and OCFile before they can be deleted
			 **/
			List<OCMessage>  messageList=new ArrayList<OCMessage>(incidentActivity.getMessages());
			for(OCMessage message:messageList){
				incidentActivity.getMessages().remove(message);
				
				message.setIncAct(null);
				session.merge(message);
			}
			List<OCFile>  fileList=new ArrayList<OCFile>(incidentActivity.getFiles());
			for(OCFile file:fileList){
				incidentActivity.getFiles().remove(file);
				file.setIncAct(null);
				session.merge(file);
			}
			
			session.delete(incidentActivity);
			transaction.commit();
			success = true;
		} catch (Exception e) {
			logger.error("Failed to delete incident activity with id: " + incidentActivityId, e);
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return success;
	}
	
	@Override
	public IncidentActivityTask loadTask(long incidentActivityTaskId) {
		IncidentActivityTask incidentActivityTask = null;
		Session session = null;
		try {
			session = HibernateWrapper.getSession().openSession();
			incidentActivityTask = (IncidentActivityTask) session.get(IncidentActivityTask.class, incidentActivityTaskId);			
		} catch (Exception e) {
			logger.error("Exception caught while attempting to load template with id: " + incidentActivityTaskId, e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return incidentActivityTask;
	}

	@Override
	public long saveTask(IncidentActivityTask incidentActivityTask) {
		long id = 0;
		if (incidentActivityTask == null) {
			return id;
		}
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = HibernateWrapper.getSession().openSession();
			transaction = session.beginTransaction();	
			session.save(incidentActivityTask);
			transaction.commit();
			id = incidentActivityTask.getTask_id();
		} catch (Exception e) {
			logger.error("Failed to save incident activity task.", e);
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return id;
	}

	@Override
	public boolean updateTask(IncidentActivityTask incidentActivityTask) {
		boolean success = false;
		if (incidentActivityTask == null) {
			return success;
		}
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = HibernateWrapper.getSession().openSession();
			transaction = session.beginTransaction();
			incidentActivityTask.setGeneric_timestamp(DateUtils.convertToGMTDate(new Date()));
			session.merge(incidentActivityTask);
			transaction.commit();
			success = true;
		} catch (Exception e) {
			logger.error("Failed to update incident activity task with id: " + incidentActivityTask.getTask_id(), e);
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return success;
	}

	@Override
	public boolean publishTask(IncidentActivityTask incidentActivityTask) {
		boolean success = false;
		if (incidentActivityTask == null) {
			return success;
		}
		
		Session session = null;
		Transaction transaction = null;
		
		try {
			session = HibernateWrapper.getSession().openSession();
			IncidentActivityTask oldIncidentActivityTask = (IncidentActivityTask) session.get(IncidentActivityTask.class, incidentActivityTask.getTask_id());
			if (oldIncidentActivityTask == null) {
				logger.error("Failed to publish incidentActivityTask - id = {}", incidentActivityTask.getTask_id());
				return false;			
			}
			
			if (oldIncidentActivityTask.getStatus().getStatus_ID() != TracingConstants.STATUS_CUSTOMER_COMM_PENDING_PRINT) {
				logger.error("The task status '{}' for incidentActivityTask id '{}' cannot be published", oldIncidentActivityTask.getStatus().getStatus_ID(), incidentActivityTask.getTask_id());
				return false;			
			}
			
			transaction = session.beginTransaction();
			
			incidentActivityTask.getStatus().setStatus_ID(TracingConstants.STATUS_CUSTOMER_COMM_PUBLISHED);
			session.merge(incidentActivityTask);
			transaction.commit();
			
			success = true;
		} catch (Exception e) {
			logger.error("Failed to publish incident activity task with id: " + incidentActivityTask.getTask_id(), e);
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return success;
	}

	@Override
	public boolean deleteTask(long incidentActivityTaskId) {
		if (incidentActivityTaskId <= 0) return true;
		return deleteTask(loadTask(incidentActivityTaskId));
	}
	
	@Override
	public boolean deleteTask(IncidentActivityTask incidentActivityTask) {
		boolean success = false;
		if (incidentActivityTask == null) return success;
		
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateWrapper.getSession().openSession();
			transaction = session.beginTransaction();
			session.delete(incidentActivityTask);
			transaction.commit();
			success = true;
		} catch (Exception e) {
			logger.error("Failed to delete incident activity task with id: " + incidentActivityTask.getTask_id(), e);
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return success;
	}

	@Override
	public boolean deleteTasks(List<IncidentActivityTask> tasks) {
		if (tasks == null || tasks.isEmpty()) return true;
		
		boolean success = false;
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateWrapper.getSession().openSession();
			transaction = session.beginTransaction();
			for (IncidentActivityTask task: tasks) {
				session.delete(task);
			}
			transaction.commit();
			success = true;
		} catch (Exception e) {
			logger.error("Failed to delete incident activity tasks.", e);
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return success;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public IncidentActivityTask loadTaskForIncidentActivity(IncidentActivity incidentActivity, Status withStatus) {
		IncidentActivityTask toReturn = null;
		Session session = null;
		try {
			session = HibernateWrapper.getSession().openSession();
			Criteria criteria = session.createCriteria(IncidentActivityTask.class, "iat");
			Conjunction and = Restrictions.conjunction();
			and.add(Restrictions.eq("iat.incidentActivity", incidentActivity));
			and.add(Restrictions.eq("iat.status", withStatus));
			and.add(Restrictions.eq("iat.active", true));
			criteria.add(and);
			List<IncidentActivityTask> results = (List<IncidentActivityTask>) criteria.list();
			if (results != null && !results.isEmpty()) {
				toReturn = results.get(0);
			}
		} catch (Exception e) {
			logger.error("Failed to load an incident activity task for incident activity with id: " + incidentActivity.getId() + " having status: " + withStatus.getStatus_ID(), e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return toReturn;
	}

	@Override
	public boolean hasTask(IncidentActivity incidentActivity, Status... statuses) {
		if (incidentActivity == null || statuses == null || statuses.length == 0) return false;

		boolean hasTask = false;
		Session session = null;
		try {
			session = HibernateWrapper.getSession().openSession();
			Criteria criteria = session.createCriteria(IncidentActivityTask.class, "iat");
			criteria.add(Restrictions.eq("iat.incidentActivity", incidentActivity));
			criteria.add(Restrictions.in("iat.status", statuses));
			criteria.add(Restrictions.eq("iat.active", true));
			criteria.setProjection(Projections.countDistinct("iat.task_id"));
			Long taskCount = (Long) criteria.uniqueResult();
			if (taskCount != null && taskCount.longValue() != 0) {
				hasTask = true;
			}
		} catch (Exception e) {
			logger.error("Could not determine if incident activity with id: " + incidentActivity.getId() + " has an active task.", e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return hasTask;
	}
	
	@Override
	public boolean hasTask(long incidentActivityId, Status... statuses) {
		if (incidentActivityId <= 0) return false;
		return hasTask(load(incidentActivityId), statuses);
	}
	
	/* (non-Javadoc)
	 * @see com.bagnet.nettracer.tracing.dao.TemplateDAO#getActivities()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Activity> getActivities() {
		List<Activity> results = new ArrayList<Activity>();
		Session session = null;
		try {
			session = HibernateWrapper.getSession().openSession();
			Criteria criteria = session.createCriteria(Activity.class, "a");
			criteria.addOrder(Order.asc("a.description"));
			results = (List<Activity>) criteria.list();
		} catch (Exception e) {
			logger.error("Failed to load activities", e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return results;
	}
	
	@Override
	public Activity getActivity(String code) {
		Activity activity = null;
		Session session = null;
		try {
			session = HibernateWrapper.getSession().openSession();
			Criteria criteria = session.createCriteria(Activity.class, "a");
			criteria.add(Restrictions.eq("a.code", code));
			activity = (Activity) criteria.uniqueResult();
		} catch (Exception e) {
			logger.error("Exception caught while attempting to load template with code: " + code, e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return activity;
	}
	
	@Override
	public int getIncidentActivityCount(Status status) {
		return this.getIncidentActivityCountByUser(null, status);
	}

	@Override
	public int getIncidentActivityCountByUser(Agent agent, Status status) {
		Session session = null;
		int result = 0;
		try {
			session = HibernateWrapper.getSession().openSession();
			Criteria criteria = session.createCriteria(IncidentActivityTask.class, "iat");
			
			/** If Status is Payment Approval Status, then join the ExpensePayout**/
			if(financestatusmap.get(status.getStatus_ID())!=null){
				criteria.createAlias("iat.incidentActivity", "ia");
				criteria.createAlias("ia.expensePayout", "e");
			}
			
			/**
			 * If looking for Payment Approval Rejected tasks, must get all
			 * different types of Payment Approval Rejection Statuses
			 **/
			if(status.getStatus_ID()==TracingConstants.FINANCE_STATUS_REJECTED){
				criteria.add(Restrictions.disjunction()
						.add(Restrictions.eq("iat.status", new Status(TracingConstants.FINANCE_STATUS_FRAUD_REJECTED)))
						.add(Restrictions.eq("iat.status", new Status(TracingConstants.FINANCE_STATUS_SUPERVISOR_REJECTED)))
						.add(Restrictions.eq("iat.status", new Status(TracingConstants.FINANCE_STATUS_FINANCE_REJECTED))));
			} else {
				criteria.add(Restrictions.eq("iat.status", status));
			}
			criteria.add(Restrictions.eq("iat.active", true));
			
			if (agent != null) {
				criteria.add(Restrictions.eq("iat.assigned_agent", agent));
			}
			
			criteria.setProjection(Projections.countDistinct("iat.task_id"));
			Long count = (Long) criteria.uniqueResult();
			if (count != null) {
				result = count.intValue();
			}			
		} catch (Exception e) {
			logger.error("Exception caught while attempting to retrieve count of incident activity tasks with status: " + status.getStatus_ID(), e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return result;
	}

	@Override
	public int getIncidentActivityTaskCount(IncidentActivityTaskSearchDTO dto) {
		Session session = null;
		int count = 0;
		try {
			session = HibernateWrapper.getSession().openSession();
			Criteria criteria = getCriteriaFromDto(session, dto);
			criteria.setProjection(Projections.rowCount());
			Object result = criteria.uniqueResult();
			if (result != null) {
				count = ((Long) result).intValue();
			} else {
				logger.error("Received null result for count in IncidentActivityDAOImpl.getIncidentActivityTaskCount");
			}
		} catch (Exception e) {
			logger.error("An error occurred while attempting to retrieve the incident activity task count", e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return count;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<IncidentActivityTask> listIncidentActivityTasks(IncidentActivityTaskSearchDTO dto) {
		List<IncidentActivityTask> results = new ArrayList<IncidentActivityTask>();
		Session session = null;
		try {
			session = HibernateWrapper.getSession().openSession();
			Criteria criteria = getCriteriaFromDto(session, dto);
			applyOrder(dto, criteria);
			criteria.setProjection(Projections.projectionList()
					.add(Projections.groupProperty("iat.task_id").as("task_id"))
					.add(Projections.property("iat.opened_timestamp").as("opened_timestamp"))
					.add(Projections.property("iat.closed_timestamp").as("closed_timestamp"))
					.add(Projections.property("iat.assigned_agent").as("assigned_agent"))
					.add(Projections.property("iat.status").as("status"))
					.add(Projections.property("iat.generic_timestamp").as("generic_timestamp"))
					.add(Projections.property("iat.incidentActivity").as("incidentActivity"))
					.add(Projections.property("iat.active").as("active")));
			criteria.setResultTransformer(Transformers.aliasToBean(IncidentActivityTask.class));
			results = (List<IncidentActivityTask>) criteria.list();
		} catch (Exception e) {
			logger.error("An error occurred while attempting to get a list of incident activity tasks", e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return results;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<IncidentActivity> getIncidentActivitiesByTaskStatus(Status status, String sortBy) {
		List<IncidentActivity> incidentActivities = ListUtils.EMPTY_LIST;
		if (status == null || status.getStatus_ID() < 1) {
			return incidentActivities;
		}

		Session session = null;
		try {
			session = HibernateWrapper.getSession().openSession();
			Criteria criteria = session.createCriteria(IncidentActivity.class, "ia");			
			criteria.add(Restrictions.isNotNull("ia.approvalAgent"));		

			criteria.createAlias("tasks", "task");
			criteria.add(Restrictions.eq("task.status", status));
			criteria.add(Restrictions.eq("task.active", true));
			
			if (StringUtils.startsWithIgnoreCase(sortBy, TracingConstants.SortParam.INCIDENT_ACTIVITY_APPROVAL_AGENT_NAME.getParamString())
					|| StringUtils.startsWithIgnoreCase(sortBy, TracingConstants.SortParam.INCIDENT_ACTIVITY_APPROVAL_AGENT_USERNAME.getParamString())) {
				criteria.createAlias("approvalAgent", "aa");
				if (StringUtils.equalsIgnoreCase(sortBy, TracingConstants.SortParam.INCIDENT_ACTIVITY_APPROVAL_AGENT_NAME.getParamString())) {
					criteria.addOrder(Order.asc("aa.lastname"));
				} else if (StringUtils.equalsIgnoreCase(sortBy, TracingConstants.SortParam.INCIDENT_ACTIVITY_APPROVAL_AGENT_NAME_REV.getParamString())) {
					criteria.addOrder(Order.desc("aa.lastname"));
				} else if (StringUtils.equalsIgnoreCase(sortBy, TracingConstants.SortParam.INCIDENT_ACTIVITY_APPROVAL_AGENT_USERNAME.getParamString())) {
					criteria.addOrder(Order.asc("aa.username"));
				} else {
					criteria.addOrder(Order.desc("aa.username"));
				}
			} else if (StringUtils.startsWithIgnoreCase(sortBy, TracingConstants.SortParam.INCIDENT_ACTIVITY_DOCUMENT_ID.getParamString())
					|| StringUtils.startsWithIgnoreCase(sortBy, TracingConstants.SortParam.INCIDENT_ACTIVITY_DOCUMENT_TITLE.getParamString())) {
				criteria.createAlias("document", "d");
				if (StringUtils.equalsIgnoreCase(sortBy, TracingConstants.SortParam.INCIDENT_ACTIVITY_DOCUMENT_ID.getParamString())) {
					criteria.addOrder(Order.asc("d.id"));
				} else if (StringUtils.equalsIgnoreCase(sortBy, TracingConstants.SortParam.INCIDENT_ACTIVITY_DOCUMENT_ID_REV.getParamString())) {
					criteria.addOrder(Order.desc("d.id"));
				} else if (StringUtils.equalsIgnoreCase(sortBy, TracingConstants.SortParam.INCIDENT_ACTIVITY_DOCUMENT_TITLE.getParamString())) {
					criteria.addOrder(Order.asc("d.title"));
				} else {
					criteria.addOrder(Order.desc("d.title"));
				}
			} else if (StringUtils.equalsIgnoreCase(sortBy, TracingConstants.SortParam.INCIDENT_REV.getParamString())) {
				criteria.addOrder(Order.desc("ia.incident"));
			} else {
				criteria.addOrder(Order.asc("ia.incident"));
			}
			
			incidentActivities = criteria.list();
		} catch (Exception e) {
			logger.error("Failed loading incident activities by tasks status's Id = {}", status.getStatus_ID(), e);
		} finally {
			if (session != null) {
				session.close();
			}
		}


		logger.info("\r\n------------------------------------------------> incidentActivities={} with status Id={}", incidentActivities.size(), status.getStatus_ID());//??????
		return incidentActivities;
	}
	
	@Override
	public IncidentActivityTask getAssignedTask(Agent agent) {
		return getAssignedTask(agent, new Status(TracingConstants.STATUS_CUSTOMER_COMM_PENDING));
	}
	
	@Override
	public IncidentActivityTask getAssignedTask(Agent agent, Status s) {
		IncidentActivityTask toReturn = null;
		Session session = null;
		try {
			session = HibernateWrapper.getSession().openSession();
			Criteria criteria = session.createCriteria(IncidentActivityTask.class, "iat");
			criteria.add(Restrictions.eq("iat.assigned_agent", agent));
			criteria.add(Restrictions.eq("iat.active", true));
			if (s != null) {
				criteria.add(Restrictions.eq("iat.status", s));
			}
			toReturn = (IncidentActivityTask) criteria.uniqueResult();
		} catch (Exception e) {
			logger.error("An error occurred while attempting to get an assigned task for: " + agent.getUsername(), e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return toReturn;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public IncidentActivityTask getTask(Status status) {
		IncidentActivityTask task = null;
		Session session = null;
		try {
			session = HibernateWrapper.getSession().openSession();
			Criteria criteria = session.createCriteria(IncidentActivityTask.class, "iat");
			criteria.add(Restrictions.eq("iat.status", status));
			criteria.add(Restrictions.eq("iat.active", true));
			criteria.addOrder(Order.asc("iat.opened_timestamp"));
			List<IncidentActivityTask> results = (List<IncidentActivityTask>) criteria.list();
			if (results != null && !results.isEmpty()) {
				task = results.get(0);
			}
		} catch (Exception e) {
			logger.error("An error occurred while attempting to get an incident activity task.", e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return task;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<IncidentActivityTask> loadTasksForIncidentActivity(IncidentActivity incidentActivity) {
		List<IncidentActivityTask> tasks = new ArrayList<IncidentActivityTask>();
		if (incidentActivity == null) return tasks;
		Session session = null;
		try {
			session = HibernateWrapper.getSession().openSession();
			Criteria criteria = session.createCriteria(IncidentActivityTask.class, "iat");
			criteria.add(Restrictions.eq("iat.incidentActivity", incidentActivity));
			tasks = (List<IncidentActivityTask>) criteria.list();
		} catch (Exception e) {
			logger.error("An error occurred while attempting to get a list of incident activity tasks", e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return tasks;
	}
	
	
	/** @Author: Sean Fine
	 * Get List of Incident Activities based on a list of IDs
	 */
	@Override
	@SuppressWarnings("unchecked") public List<IncidentActivity> listIncidentActivities(List<Long> idlist){
		List<IncidentActivity> results = new ArrayList<IncidentActivity>();
		Session session = null;
		try {
			session = HibernateWrapper.getSession().openSession();
			Criteria criteria = session.createCriteria(IncidentActivity.class, "ia");
			criteria.createAlias("ia.incident", "i");
			criteria.add(Restrictions.in("ia.id", idlist));
			results = (List<IncidentActivity>) criteria.list();
		} catch (Exception e) {
			logger.error("An error occurred while attempting to get a list of incident activities", e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return results;
	}
	

	/** @Author: Sean Fine
	 * Get List of Incident Activity Tasks based on a list of IDs
	 */
	@Override
	@SuppressWarnings("unchecked") public List<IncidentActivityTask> listIncidentActivityTasks(List<Long> idlist){

		List<IncidentActivityTask> results = new ArrayList<IncidentActivityTask>();
		Session session = null;
		try {
			session = HibernateWrapper.getSession().openSession();
			Criteria criteria = session.createCriteria(IncidentActivityTask.class, "iat");
			criteria.createAlias("iat.incidentActivity", "ia");
			criteria.createAlias("ia.incident", "i");
			criteria.add(Restrictions.in("iat.id", idlist));
			results = (List<IncidentActivityTask>) criteria.list();
		} catch (Exception e) {
			logger.error("An error occurred while attempting to get a list of incident activities", e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return results;
	}
	
	private Activity getActivityByCode(String activityCode) {
		Activity activity = null;
		Session session = null;
		try {
			session = HibernateWrapper.getSession().openSession();
			Criteria criteria = session.createCriteria(Activity.class, "a");
			criteria.add(Restrictions.eq("a.code", activityCode));
			activity = (Activity) criteria.uniqueResult();
		} catch (Exception e) {
			logger.error("Failed to load Activity with code: " + activityCode, e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return activity;
	}

	@Override
	public IncidentActivity loadByActivityCode(String activityCode,String incident_ID) {
		IncidentActivity activity = null;
		Session session = null;
		try {
			session = HibernateWrapper.getSession().openSession();
			Criteria criteria = session.createCriteria(IncidentActivity.class, "ia");
			criteria.createAlias("ia.activity", "a");
			criteria.createAlias("ia.incident", "i");
			criteria.add(Restrictions.eq("a.code", activityCode));
			criteria.add(Restrictions.eq("i.incident_ID", incident_ID));
			activity = (IncidentActivity) criteria.uniqueResult();
		} catch (Exception e) {
			logger.error("Failed to load Incident Activity with code: " + activityCode+" and Incident ID: "+incident_ID, e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return activity;
	}
	
	private Criteria getCriteriaFromDto(Session session, IncidentActivityTaskSearchDTO dto) {
		Criteria criteria = session.createCriteria(IncidentActivityTask.class, "iat");
		criteria.createAlias("iat.incidentActivity", "ia");
		criteria.createAlias("ia.incident", "i");
		criteria.createAlias("ia.document", "d");
		
		if(dto.getSort()!=null){
			/** Check for sorting to properly join tables as need **/
			if(dto.getSort().equals("name") || dto.getSort().equals("address")){
				criteria.createAlias("i.passengers", "p");
			}
		
			if(dto.getSort().equals("address")){
				criteria.createAlias("p.addresses", "a");
			}
		}
		
		if (dto.getAgent() != null) {
			criteria.add(Restrictions.eq("iat.assigned_agent", dto.getAgent()));
		}
		
		if (dto.getStatus() != null) {

			/* If Status is Payment Approval Status, then join the ExpensePayout */
			if(financestatusmap.get(dto.getStatus().getStatus_ID())!=null){
				criteria.createAlias("ia.expensePayout", "e");
			}

			/*
			 * If looking for Payment Approval Rejected tasks, must get all
			 * different types of Payment Approval Rejection Statuses
			 */
			if(dto.getStatus().getStatus_ID()==TracingConstants.FINANCE_STATUS_REJECTED){
				criteria.add(Restrictions.disjunction()
						.add(Restrictions.eq("iat.status", new Status(TracingConstants.FINANCE_STATUS_FRAUD_REJECTED)))
						.add(Restrictions.eq("iat.status", new Status(TracingConstants.FINANCE_STATUS_SUPERVISOR_REJECTED)))
						.add(Restrictions.eq("iat.status", new Status(TracingConstants.FINANCE_STATUS_FINANCE_REJECTED))));
			} else {
				criteria.add(Restrictions.eq("iat.status", dto.getStatus()));
			}
		}
		
		if (dto.getRowsPerPage() > 0) {
			criteria.setFirstResult(dto.getCurrentPage() * dto.getRowsPerPage());
			criteria.setMaxResults(dto.getRowsPerPage());
		}
		
		criteria.add(Restrictions.eq("iat.active", dto.isActive()));
		return criteria;
	}
	
	private void applyOrder(IncidentActivityTaskSearchDTO dto, Criteria criteria) {
		String orderVar = IncidentActivityDAOImpl.map.get(dto.getSort());
		if (orderVar == null) {
			orderVar = "iat.task_id";
			logger.warn("Illegal sort value found for incident activity tasks: " + dto.getSort());
		}
		
		String dir = dto.getDir();
		if (!TracingConstants.SORT_ASCENDING.equals(dir) && !TracingConstants.SORT_DESCENDING.equals(dir)) {
			dir = TracingConstants.SORT_ASCENDING;
			logger.warn("Illegal sort direction value found for incident activity tasks: " + dto.getDir());
		}
		
		criteria.addOrder(TracingConstants.SORT_ASCENDING.equals(dir) ? Order.asc(orderVar) : Order.desc(orderVar));
	}

	/** @author SeanFine
	 * Save a list of Incident Activities
	 */
	@Override
	public boolean saveIncidentActivities(List<IncidentActivity> ialist) {
		Session session = null;
		Transaction transaction = null;
		boolean success=true;
		try {
			session = HibernateWrapper.getSession().openSession();
			transaction = session.beginTransaction();	
			for(IncidentActivity ia:ialist){
				session.merge(ia);
				session.flush();
				session.clear();
			}
			
			transaction.commit();
		} catch (Exception e) {
			logger.error("Failed to save incident activity tasks", e);
			if (transaction != null) {
				transaction.rollback();
			}
			success=false;
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return success;
	}

	/** @author SeanFine
	 * Save a list of Incident Activity Tasks
	 */
	@Override
	public boolean saveIncidentActivityTasks(List<IncidentActivityTask> iatlist) {
		Session session = null;
		Transaction transaction = null;
		boolean success=true;
		try {
			session = HibernateWrapper.getSession().openSession();
			transaction = session.beginTransaction();	
			for(IncidentActivityTask iat:iatlist){
				session.merge(iat);
				session.flush();
				session.clear();
			}
			
			transaction.commit();
		} catch (Exception e) {
			logger.error("Failed to save incident activity tasks", e);
			if (transaction != null) {
				transaction.rollback();
			}
			success=false;
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return success;
	}
	
	@Override
	public int getIncidentActivityTaskNotInWorkCount(IncidentActivityTaskSearchDTO dto) {
		int count = 0;
		Session session = null;
		try {
			session = HibernateWrapper.getSession().openSession();
			Criteria criteria = getCriteriaForNotInWorkTasks(session, dto);
			criteria.setProjection(Projections.countDistinct("iat.task_id"));
			Long result = (Long) criteria.uniqueResult();
			if (result != null) {
				count = result.intValue();
			}
		} catch (Exception e) {
			logger.error("An error occurred while attempting to get the incident activity task count.", e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return count;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<IncidentActivityTask> getIncidentActivitiesNotInWork(IncidentActivityTaskSearchDTO dto) {
		List<IncidentActivityTask> results = new ArrayList<IncidentActivityTask>();
		Session session = null;
		try {
			session = HibernateWrapper.getSession().openSession();
			Criteria criteria = getCriteriaForNotInWorkTasks(session, dto);
			results = (List<IncidentActivityTask>) criteria.list();
		} catch (Exception e) {
			logger.error("An error occurred while attempting to get the incident activity tasks.", e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return results;
	}
	
	private Criteria getCriteriaForNotInWorkTasks(Session session, IncidentActivityTaskSearchDTO dto) {
		Criteria criteria = session.createCriteria(IncidentActivityTask.class, "iat");
		criteria.createAlias("iat.incidentActivity", "ia");
		criteria.createAlias("ia.incident", "i");
		
		if (dto.getAgent() != null) {
			criteria.add(Restrictions.eq("i.agentassigned", dto.getAgent()));
		}
		
		if (dto.getPassengerFirstName() != null || dto.getPassengerLastName() != null) {
			criteria.createAlias("i.passengers", "p");
			if (dto.getPassengerFirstName() != null && !dto.getPassengerFirstName().isEmpty()) {
				criteria.add(Restrictions.ilike("p.firstname", dto.getPassengerFirstName()));
			}

			if (dto.getPassengerLastName() != null && !dto.getPassengerLastName().isEmpty()) {
				criteria.add(Restrictions.ilike("p.lastname", dto.getPassengerLastName()));
			}
		}
		
		Date start = dto.getStartCreateDate();
		Date end = dto.getEndCreateDate();
		if (start != null && end != null) {
			criteria.add(Restrictions.between("iat.opened_timestamp", start, end));
		} else if (start != null) {
			criteria.add(Restrictions.ge("iat.opened_timestamp", start));
		} else if (end != null) {
			criteria.add(Restrictions.lt("iat.opened_timestamp", end));
		}
		
		criteria.add(Restrictions.eq("iat.active", true));
		return criteria;
	}

}
