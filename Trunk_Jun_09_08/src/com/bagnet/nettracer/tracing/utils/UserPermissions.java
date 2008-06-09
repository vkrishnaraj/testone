/*
 * Created on Jul 23, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.utils;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.GroupComponentPolicy;
import com.bagnet.nettracer.tracing.db.SystemComponent;

/**
 * @author Ankur Gupta
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class UserPermissions {
	public static LinkedHashMap renderApplicationLinks(Agent agent) {
		LinkedHashMap permissionMap = new LinkedHashMap();

		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			/*
			 * Criteria cri =
			 * sess.createCriteria(GroupComponentPolicy.class).add(Expression.eq("group_id",
			 * new Integer(agent.getGroup().getUserGroup_ID())));
			 * 
			 * List list = cri.list();
			 */
			StringBuffer sql = new StringBuffer(1024);
			sql
					.append("select distinct policy,policy.component.sort_order from com.bagnet.nettracer.tracing.db.GroupComponentPolicy policy ");
			sql.append(" where 1=1 ");

			sql.append(" and policy.component.component_ID = policy.component.parent.component_ID");
			sql.append(" and policy.usergroup.userGroup_ID = :groupId ");
			sql.append(" order by policy.component.sort_order ");

			Query q = sess.createQuery(sql.toString());
			q.setInteger("groupId", agent.getGroup().getUserGroup_ID());

			List list = q.list();
			Object[] o = null;

			for (Iterator i = list.iterator(); i.hasNext();) {
				//For each parent do something.
				o = (Object[]) i.next();

				GroupComponentPolicy plicy = (GroupComponentPolicy) o[0];

				if (plicy.getComponent().getDisplay() != 0) {
					LinkedHashMap childMap = new LinkedHashMap();
					permissionMap.put(plicy.getComponent().getComponent_Name(), childMap);
					List childList = getChildLinks(plicy.getComponent().getComponent_ID(), agent.getGroup()
							.getUserGroup_ID());
					for (Iterator j = childList.iterator(); j.hasNext();) {
						/*
						GroupComponentPolicy plicy2 = (GroupComponentPolicy) j.next();
						if (plicy2.getComponent().getDisplay() != 0) {
							childMap.put(plicy2.getComponent().getComponent_Name(), plicy2.getComponent()
									.getComponent_action_link());
						}
						*/
						SystemComponent sc = (SystemComponent) j.next();
						if (sc.getDisplay() != 0) {
							childMap.put(sc.getComponent_Name(), sc.getComponent_action_link());
						}
					}
				}
			}
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
		return permissionMap;
	}

	public static List getChildLinks(int parent_id, int groupId) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();

			StringBuffer sql = new StringBuffer(1024);
			sql
					.append("select distinct policy.component from com.bagnet.nettracer.tracing.db.GroupComponentPolicy policy ");
			sql.append(" where 1=1 ");

			sql.append(" and policy.component.component_ID != policy.component.parent.component_ID");
			sql.append(" and policy.component.parent.component_ID = :parent_ID ");
			sql.append(" and policy.usergroup.userGroup_ID = :groupId ");

			//--Change made by Ankur for Hibernate..

			sql.append(" order by policy.component.sort_order asc ");

			Query q = sess.createQuery(sql.toString());
			q.setInteger("parent_ID", parent_id);
			q.setInteger("groupId", groupId);
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
	 * Checks whether a particular agent has access to a certain component.
	 * 
	 * @param componentName
	 * @param a
	 * @return
	 */
	public static boolean hasPermission(String componentName, Agent a) {
		boolean ret = false;

		Set l = a.getGroup().getComponentPolicies();

		if (l != null) {
			for (Iterator i = l.iterator(); i.hasNext();) {
				GroupComponentPolicy policy = (GroupComponentPolicy) i.next();

				if (policy.getComponent().getComponent_Name().equalsIgnoreCase(componentName)) {
					ret = true;
					break;
				}
			}
		}

		return ret;
	}

	/**
	 * Checks whether a particular agent has access to a certain action link.
	 * 
	 * @param componentName
	 * @param a
	 * @return
	 */
	public static boolean hasLinkPermission(String actionLink, Agent a) {
		boolean ret = false;

		Set l = a.getGroup().getComponentPolicies();

		if (l != null) {
			for (Iterator i = l.iterator(); i.hasNext();) {
				GroupComponentPolicy policy = (GroupComponentPolicy) i.next();

				if (policy.getComponent().getComponent_action_link() != null
						&& policy.getComponent().getComponent_action_link().indexOf(actionLink) != -1) {
					ret = true;
					break;
				}
			}
		}

		return ret;
	}

}