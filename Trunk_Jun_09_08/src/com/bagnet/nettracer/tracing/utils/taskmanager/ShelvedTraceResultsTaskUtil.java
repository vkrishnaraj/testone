package com.bagnet.nettracer.tracing.utils.taskmanager;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.bmo.TaskManagerBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.lf.detection.LFMatchHistory;
import com.bagnet.nettracer.tracing.db.taskmanager.GeneralTask;
import com.bagnet.nettracer.tracing.db.taskmanager.ItemTraceResultsTask;

public class ShelvedTraceResultsTaskUtil {
	
	@SuppressWarnings("rawtypes")
	public static GeneralTask getNextTask(Agent a, int value) {
		ItemTraceResultsTask task = null;
		String sql = getTaskQuery(a, value);
		Session session = null;
		try {
			session = HibernateWrapper.getSession().openSession();
			Query q = session.createQuery(sql);
			q.setMaxResults(1);
			
			List result = q.list();
			if (result.size() > 0) {
				task = new ItemTraceResultsTask();
				task.setFoundItem(((LFMatchHistory) result.get(0)).getFound());
				if(TaskManagerUtil.lockTask(task) != null){
					return task;
				}
			}
			return null;
		} catch(Exception e){
			e.printStackTrace();
		}finally{
			if(session != null && session.isOpen()){
				session.close();
			}
		}
		return null;
	}
	
	private static String getTaskQuery(Agent a, int value) {
		String sql = "from com.bagnet.nettracer.tracing.db.lf.detection.LFMatchHistory m "
			   + "where m.status = " + TracingConstants.LF_TRACING_OPEN + " and m.found.status = " + TracingConstants.LF_STATUS_OPEN + " "
			   + "and m.found.location.station_ID = " + a.getStation().getStation_ID() + " "
			   + "and m.found.item.value = " + value + " "
			   + "and m.found.itemLocation = " + TracingConstants.LF_LOCATION_SHELF + " "
			   + "and 0 = (select count(l.id) from com.bagnet.nettracer.tracing.db.Lock l where l.lockKey = m.found.barcode) "
			   + "order by m.id asc group by m.found.id";
		return sql;
	}

}
