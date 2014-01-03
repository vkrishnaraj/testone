package com.bagnet.nettracer.tracing.bmo;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.taskmanager.InboundQueueTask;
import com.bagnet.nettracer.tracing.dto.InboundTasksDTO;

public class InboundTasksBMO {
	private static Logger logger = Logger.getLogger(InboundTasksBMO.class);
	
	/**
	 * Maps the sort parameters from DisplayTag to the appropriate database fields.
	 * Flight numbers are stored as a String, must cast to int in order to be sorted numerically
	 */
	private static Map<String, String> map = new LinkedHashMap<String, String>();
	
	static {
		map.put("opened_timestamp",	"t.opened_timestamp");
		map.put("incident_id",		"t.incident.incident_ID");
		map.put("type",				"t.description");
	}
	
	public long getTasksCount(InboundTasksDTO dto){
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			HashMap<String, Object>params = new HashMap<String, Object>();
			String sql = "select count(t.id) " + getTasksQuery(dto, params);
			Query query = sess.createQuery(sql);
			query.setProperties(params);
			return (Long)query.uniqueResult();
		} catch (Exception e){
			e.printStackTrace();
			return 0;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public List<InboundQueueTask> getTasks(InboundTasksDTO dto){
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			HashMap<String, Object>params = new HashMap<String, Object>();
			String sql = getTasksQuery(dto, params);
			Query query = sess.createQuery(sql);
			query.setProperties(params);
			
			if(dto.getStartIndex() > -1 && dto.getMaxResults() > 0){
				query.setFirstResult(dto.getStartIndex());
				query.setMaxResults(dto.getMaxResults());
			}
			
			@SuppressWarnings("unchecked")
			List<InboundQueueTask> results = query.list(); 
			return results;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private String getTasksQuery(InboundTasksDTO dto, Map<String,Object>params){
		String sql = "from InboundQueueTask t where 1=1 ";
		if(dto != null){
			if(dto.getId() > 0){
				sql += "and t.task_id = :id ";
				params.put("id", dto.getId());
				return sql;//unique id, no need to add any additional search criteria
			}
			
			if(dto.isSearchUnassignedTasks()){
				sql += "and t.incident.agentassigned = null ";
			} else if(dto.getAssigned_agent() != null){
				sql += "and t.incident.agentassigned.agent_ID = :agent_ID ";
				params.put("agent_ID", dto.getAssigned_agent().getAgent_ID());
			}
			
			if(dto.getStatus() != null){
				sql += "and t.status.status_ID = :status_ID ";
				params.put("status_ID", dto.getStatus().getStatus_ID());
			}
			if(dto.getSort() != null){
				sql += "order by " + (InboundTasksBMO.map.get(dto.getSort())!=null?InboundTasksBMO.map.get(dto.getSort()):"t.opened_timestamp");
				sql += dto.getDir()==null||TracingConstants.SORT_DESCENDING.equals(dto.getDir()) ? " desc ":" asc " ;
			}
		}
		return sql;
	}
	
	public long saveTask(InboundQueueTask task, Agent agent){
		Session sess = null;
		Transaction t = null;
		
		try {
			sess = HibernateWrapper.getSession().openSession();
			t = sess.beginTransaction();
			sess.saveOrUpdate(task);
			//TODO taskActivity
			t.commit();
			return task.getTask_id();
		} catch (Exception e){
			e.printStackTrace();
			if(t != null){
				t.rollback();
			}
			return -1;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Agent> getInboundAgentList(){
		String sql = "from com.bagnet.nettracer.tracing.db.Agent where active = :active and inboundQueue = :inboundQueue";
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Query query = sess.createQuery(sql);
			query.setParameter("active", true);
			query.setParameter("inboundQueue", true);
			return query.list();
		} catch (Exception e){
			e.printStackTrace();
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
