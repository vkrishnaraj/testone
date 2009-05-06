/*
 * Created on Aug 30, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.utils;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Priority;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.Task;

/**
 * @author Ankur Gupta
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class TaskUtils {

	public static List getTasks(int station_id, int task_status_id, int rowsperpage, int currpage,
			String sort) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(Task.class);
			cri.createCriteria("station").add(Expression.eq("station_ID", new Integer(station_id)));
			if (task_status_id != Task.ALL_TASKS) cri.createCriteria("status").add(
					Expression.eq("status_ID", new Integer(task_status_id)));
			else cri.createCriteria("status").add(
					Expression.not(Expression.eq("status_ID", new Integer(task_status_id))));

			if (sort != null && sort.length() > 0) {
				if (sort.equalsIgnoreCase("due_date")) {
					cri.addOrder(Order.asc("due_date_time"));
				} else {
					if (sort.equalsIgnoreCase("reminder_date")) {
						cri.addOrder(Order.asc("reminder_date_time"));
					} else {
						if (sort.equalsIgnoreCase("file_ref_number")) {
							cri.addOrder(Order.asc("file_ref_number"));
						}
					}

				}
			} else {
				cri.addOrder(Order.asc("due_date_time"));
				cri.addOrder(Order.asc("reminder_date_time"));
			}

			if (rowsperpage > 0) {
				int startnum = currpage * rowsperpage;
				cri.setFirstResult(startnum);
				cri.setMaxResults(rowsperpage);
			}
			return cri.list();
		} catch (Exception e) {
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

	/**
	 * 
	 * @param file_ref
	 * @param file_type
	 * @return
	 */
	public static List getFileTasks(String file_ref, int file_type) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(Task.class)
					.add(Expression.eq("file_ref_number", file_ref)).add(
							Expression.eq("file_type", new Integer(file_type)));
			cri.addOrder(Order.asc("due_date_time"));
			cri.addOrder(Order.asc("reminder_date_time"));
			return cri.list();
		} catch (Exception e) {
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

	public static int getActiveTaskCount(int station_id) {
		return getActiveTaskCount(station_id, false);
	}
	
	/**
	 * Used for the task manager only, assumes -1
	 * 
	 * @param station_id
	 * @param task_status_id
	 * @return
	 */
	public static int getActiveTaskCount(int station_id, boolean dirtyRead) {
		Session sess = null;

		try {
			if(dirtyRead) {
				sess = HibernateWrapper.getDirtySession().openSession();
			}
			else {
				sess = HibernateWrapper.getSession().openSession();
			}
			
			String sql = "select count(task.task_id) from " + "com.bagnet.nettracer.tracing.db.Task task where 1=1 ";
			sql += " and task.station.station_ID = :station_ID";
			sql += " and task.status.status_ID != :deleted_status";
			sql += " and task.status.status_ID != :completed_status";
			Query q = sess.createQuery(sql);
			q.setInteger("station_ID", station_id);

			q.setInteger("deleted_status", TracingConstants.TASK_STATUS_DELETED);
			q.setInteger("completed_status", TracingConstants.TASK_STATUS_COMPLETED);

			List list = q.list();
			if(list.size() > 0) {
				return ((Long) list.get(0)).intValue();
			}
			else {
				return 0;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		finally {
			if(sess != null) {
				try {
					sess.close();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 
	 * @param station_id
	 * @param file_ref_number
	 * @param task_status_id
	 * @param s_date
	 * @param e_date
	 * @param rowsperpage
	 * @param currpage
	 * @return
	 */
	public static List getTasks(int station_id, String agent_id, String file_ref_number,
			int task_status_id, String s_date, String e_date, Agent user, int rowsperpage, int currpage,
			String sort, String file_type, boolean isCount) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();

			StringBuffer sql = new StringBuffer(512);

			if (isCount) {
				sql
						.append("select count(distinct task.task_id) from com.bagnet.nettracer.tracing.db.Task task where 1=1");
			} else {
				sql.append("select distinct task from com.bagnet.nettracer.tracing.db.Task task where 1=1");
			}

			sql.append(" and task.station.station_ID = :station_ID");

			if (agent_id != null && !agent_id.equals("")) {
				sql.append(" and task.assignedTo.agent_ID = :agent_ID");
			}

			if (task_status_id == Task.ALL_TASKS) {
				sql.append(" and task.status.status_ID <> :status_ID");
			} else if(task_status_id == Task.ACTIVE_TASKS) {
				sql.append(" and task.status.status_ID != :deleted_status ");
				sql.append(" and task.status.status_ID != :completed_status ");
			}
			else {
				if (task_status_id == TracingConstants.TASK_STATUS_NOT_COMPLETED) {
					task_status_id = TracingConstants.TASK_STATUS_COMPLETED;
					sql.append(" and task.status.status_ID <> :status_ID");
				} else {
					sql.append(" and task.status.status_ID = :status_ID");
				}
			}

			if (file_ref_number != null && file_ref_number.length() > 0) sql
					.append(" and task.file_ref_number = :file_ref_number");

			if (file_type != null && file_type.length() > 0 && !file_type.equals("-1")) {
				sql.append(" and task.file_type = :file_type");
			}

			Date sdate = null, edate = null;
			
			if (s_date != null && s_date.length() > 0) {
				sdate = DateUtils.convertToDate(s_date, user.getDateformat().getFormat(), null);
			}
			if (e_date != null && e_date.length() > 0) {
				edate = DateUtils.convertToDate(e_date, user.getDateformat().getFormat(), null);
			}
			
			if (sdate != null && !sdate.equals("")) {
				if (edate != null && !edate.equals("")) {
					if (sdate.equals(edate)) sql.append(" and task.due_date_time = :sdate ");
					else sql.append(" and task.due_date_time between :sdate and :edate ");
				} else {
					sql.append(" and task.due_date_time = :sdate ");
				}
			}

			if (!isCount) {
				sql.append(" order by ");
	
				if (sort != null && sort.length() > 0) {
					if (sort.equalsIgnoreCase("due_date")) {
						sql.append("task.due_date_time asc");
					} else {
						if (sort.equalsIgnoreCase("reminder_date")) {
							sql.append("reminder_date_time asc ");
						} else {
							if (sort.equalsIgnoreCase("file_ref_number")) {
								sql.append("file_ref_number");
							} else {
								if (sort.equalsIgnoreCase("status")) {
									sql.append("task.status.description");
								} else {
									if (sort.equalsIgnoreCase("priority")) {
										sql.append("task.priority.priority_ID desc");
									} else {
										if (sort.equalsIgnoreCase("assigned_to")) {
											sql.append("task.assignedTo.username");
										}
									}
								}
							}
						}
					}
				} else {
					sql.append("task.due_date_time asc");
				}
			}
			
			Query q = sess.createQuery(sql.toString());

			if (rowsperpage > 0) {
				int startnum = currpage * rowsperpage;
				q.setFirstResult(startnum);
				q.setMaxResults(rowsperpage);
			}

			if(task_status_id != Task.ACTIVE_TASKS) {
				q.setInteger("status_ID", task_status_id);
			}
			else {
				q.setInteger("completed_status", TracingConstants.TASK_STATUS_COMPLETED);
				q.setInteger("deleted_status", TracingConstants.TASK_STATUS_DELETED);
			}
			
			if (file_ref_number != null && file_ref_number.length() > 0) q.setString("file_ref_number",
					file_ref_number);
			if (file_type != null && file_type.length() > 0 && !file_type.equals("-1")) {
				q.setInteger("file_type", Integer.parseInt(file_type));
			}

			if (sdate != null && !sdate.equals("")) {
				if (edate != null && !edate.equals("")) {
					if (sdate.equals(edate)) q.setDate("sdate", sdate);
					else {
						q.setDate("sdate", sdate);
						q.setDate("edate", edate);
					}
				} else {
					q.setDate("sdate", sdate);
				}
			}
			if (agent_id != null && !agent_id.equals("")) {
				q.setInteger("agent_ID", Integer.parseInt(agent_id));
			}
			q.setInteger("station_ID", station_id);
			return q.list();
		} catch (Exception e) {
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

	/**
	 * 
	 * @param station_id
	 * @param file_ref_number
	 * @param task_status_id
	 * @param s_date
	 * @param e_date
	 * @param user
	 * @param file_type
	 * @return
	 */
	public static int getTaskCount(int station_id, String file_ref_number, int task_status_id,
			String s_date, String e_date, Agent user, String file_type) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			String sql = "select count(task.task_id) from "
					+ "com.bagnet.nettracer.tracing.db.Task task where 1=1 ";
			sql += " and task.station.station_ID = :station_ID";
			if (file_ref_number != null && file_ref_number.length() > 0) {
				sql += " and task.file_ref_number = :file_ref_number";
			}
			
			Date sdate = null, edate = null;
			if (s_date != null && s_date.length() > 0) {
				sdate = DateUtils.convertToDate(s_date, user.getDateformat().getFormat(), null);
			}
			if (e_date != null && e_date.length() > 0) {
				edate = DateUtils.convertToDate(e_date, user.getDateformat().getFormat(), null);
			}
			
			if (sdate != null && !sdate.equals("")) {
				if (edate != null && !edate.equals("")) {
					if (sdate.equals(edate)) sql += " and task.due_date_time = :sdate ";
					else sql += " and task.due_date_time between :sdate and :edate ";
				} else {
					sql += " and task.due_date_time = :sdate ";
				}
			}

			if (task_status_id != Task.ALL_TASKS) {
				if (task_status_id == TracingConstants.TASK_STATUS_NOT_COMPLETED) {
					task_status_id = TracingConstants.TASK_STATUS_COMPLETED;
					sql += " and task.status.status_ID <> :status_ID";
				} else {
					sql += " and task.status.status_ID = :status_ID";
				}
			} else {
				sql += " and task.status.status_ID <> :status_ID";
			}

			if (file_type != null && file_type.length() > 0 && !file_type.equals("-1")) {
				sql += " and task.file_type = :file_type";
			}

			Query q = sess.createQuery(sql);
			q.setInteger("station_ID", station_id);

			if (file_type != null && file_type.length() > 0 && !file_type.equals("-1")) {
				q.setInteger("file_type", (new Integer(file_type)).intValue());
			}

			if (file_ref_number != null && file_ref_number.length() > 0) {
				q.setString("file_ref_number", file_ref_number);
			}
			
			if (sdate != null && !sdate.equals("")) {
				if (edate != null && !edate.equals("")) {
					if (sdate.equals(edate)) q.setDate("sdate", sdate);
					else {
						q.setDate("sdate", sdate);
						q.setDate("edate", edate);
					}
				} else {
					q.setDate("sdate", sdate);
				}
			}
			
			q.setInteger("status_ID", task_status_id);
			List list = q.list();
			if (list.size() > 0) {
				return ((Long) list.get(0)).intValue();
			} else {
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
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

	/**
	 * Get the task id
	 * 
	 * @param task_id
	 *          the id of task
	 * @return return the task
	 */
	public static Task getTask(String task_id) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(Task.class).add(
					Expression.eq("task_id", new Integer(task_id)));
			return (Task) cri.list().get(0);

		} catch (Exception e) {
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