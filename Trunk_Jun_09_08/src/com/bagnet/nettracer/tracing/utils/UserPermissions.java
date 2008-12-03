/*
 * Created on Jul 23, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.GroupComponentPolicy;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.SystemComponent;

/**
 * @author Ankur Gupta
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
					.append("select distinct policy,policy.component.sort_order, " +
							"policy.component.sort_group from com.bagnet.nettracer.tracing.db.GroupComponentPolicy policy ");
			sql.append(" where 1=1 ");

			sql.append(" and policy.component.component_ID = policy.component.parent.component_ID");
			sql.append(" and policy.usergroup.userGroup_ID = :groupId ");
			sql.append(" order by policy.component.sort_group, policy.component.sort_order ");

			Query q = sess.createQuery(sql.toString());
			q.setInteger("groupId", agent.getGroup().getUserGroup_ID());

			List list = q.list();
			Object[] o = null;

			for (Iterator i = list.iterator(); i.hasNext();) {
				//For each parent do something.
				o = (Object[]) i.next();

				GroupComponentPolicy plicy = (GroupComponentPolicy) o[0];
				if (agent.getStation().getCompany().getVariable().getWt_enabled() == 1){
					
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
				else if (plicy.getComponent().getDisplay() != 0 && plicy.getComponent().getComponent_Name().indexOf("WorldTracer") == -1) {
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


	public static ArrayList<GroupComponentPolicy> getTaskManagerComponents(Agent agent) {
		
		List list = null;
		
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();

			
			
			StringBuffer sql = new StringBuffer(1024);
			sql
					.append("select distinct policy,policy.component.sort_order, policy.component.sort_group from com.bagnet.nettracer.tracing.db.GroupComponentPolicy policy ");
			sql.append(" where 1=1 ");

			sql.append(" and ((policy.component.parent.component_ID = 15");
			sql.append(" and policy.component.component_ID != 15) or policy.component.component_ID = 18)");
			sql.append(" and policy.usergroup.userGroup_ID = :groupId ");
			sql.append(" order by policy.component.sort_group, policy.component.sort_order ");

			Query q = sess.createQuery(sql.toString());
			q.setInteger("groupId", agent.getGroup().getUserGroup_ID());

			list = q.list();

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
		
		ArrayList retList = new ArrayList();
		for (int i=0; i<list.size(); ++i)
			retList.add(((Object[])(list.get(i)))[0]);
			
		return retList;
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
	
	public static boolean hasLimitedSavePermission(Agent a, String incident_ID) {
		if (incident_ID == null) {
			return true;
		}
		
		Incident inc = IncidentBMO.getIncidentByID(incident_ID, null);
		if (inc != null) {
			int type = inc.getItemtype().getItemType_ID();
			return hasLimitedSavePermissionByType(a, type);
		} else {
			return false;
		}
	}
	
	public static boolean hasLimitedSavePermissionByType(Agent a, int type) {

		String componentNameAll = null;
		boolean limitedPermission = false;
		
		
		switch (type) {
			case TracingConstants.LOST_DELAY:
				componentNameAll = TracingConstants.SYSTEM_COMPONENT_NAME_LOST_DELAY_UPDATE_ALL;
				limitedPermission = (PropertyBMO.getValue(PropertyBMO.PROPERTY_LIMIT_LD_STATIONS).equals("1"));
				break;
			case TracingConstants.DAMAGED_BAG:
				componentNameAll = TracingConstants.SYSTEM_COMPONENT_NAME_DAMAGED_BAG_UPDATE_ALL;
				limitedPermission = (PropertyBMO.getValue(PropertyBMO.PROPERTY_LIMIT_DAMAGED_STATIONS).equals("1"));
				break;
			case TracingConstants.MISSING_ARTICLES:
				componentNameAll = TracingConstants.SYSTEM_COMPONENT_NAME_MISSING_ARTICLES_UPDATE_ALL;
				limitedPermission = (PropertyBMO.getValue(PropertyBMO.PROPERTY_LIMIT_MISSING_STATIONS).equals("1"));
				break;
		}
		
		// If the user has full permission, allow it to be edited.
		if (hasPermission(componentNameAll, a)) {
			return false;
		} 

		return limitedPermission;
	}
	
	public static List<Station> getLimitedSaveStations(Agent a, int type) {
		return getLimitedSaveStations(a, null, type);
	}
	
	public static List<Station> getLimitedSaveStations(Agent a, String incident_ID) {
		return getLimitedSaveStations(a, incident_ID, 0);
	}

	public static List<Station> getLimitedSaveStations(Agent a, String incident_ID, int type) {
		ArrayList<Station> stationList = new ArrayList<Station>();
		ArrayList<String> stationCodeList = new ArrayList<String>();
		
		stationList.add(a.getStation());
		stationCodeList.add(a.getStation().getStationcode());
		
		String stationString = null;
		
		if (incident_ID == null && type == 0) {
			return stationList;
		} else if (incident_ID != null) {
			Incident inc = IncidentBMO.getIncidentByID(incident_ID, null);
			if(inc != null) {
			type = inc.getItemtype().getItemType_ID();
			}
		}
		
		switch (type) {
			case TracingConstants.LOST_DELAY:
				stationString = PropertyBMO.getValue(PropertyBMO.PROPERTY_LIMIT_LD_ADDSTATIONS);
				break;
			case TracingConstants.DAMAGED_BAG:
				stationString = PropertyBMO.getValue(PropertyBMO.PROPERTY_LIMIT_DAMAGED_ADDSTATIONS);
				break;
			case TracingConstants.MISSING_ARTICLES:
				stationString = PropertyBMO.getValue(PropertyBMO.PROPERTY_LIMIT_MISSING_ADDSTATIONS);
				break;
		}
		
		for (String stationCode: stationString.split(",")) {
			Station station = StationBMO.getStationByCode(stationCode, a.getCompanycode_ID());
			if (station != null && !stationCodeList.contains(station.getStationcode())) {
				stationList.add(station);
				stationCodeList.add(station.getStationcode());
			}
		}
		
		return stationList;
	}
	
	public static boolean hasIncidentSavePermission(Agent a, String incident_ID) {
		IncidentBMO ibmo = new IncidentBMO();
		return hasIncidentSavePermission(a, ibmo.findIncidentByID(incident_ID));
	}
	
	public static boolean hasIncidentSavePermission(Agent a, Incident inc) {

		if (inc == null) {
			return true;
		}
		
		if(!inc.getStationassigned().getCompany().getCompanyCode_ID().equals(a.getCompanycode_ID())) {
			return false;
		}
		
		int type = inc.getItemtype().getItemType_ID();
		String componentNameCreated = null;
		String componentNameAll = null;
		
		
		switch (type) {
			case TracingConstants.LOST_DELAY:
				componentNameCreated = TracingConstants.SYSTEM_COMPONENT_NAME_LOST_DELAY_UPDATE_CREATED;
				componentNameAll = TracingConstants.SYSTEM_COMPONENT_NAME_LOST_DELAY_UPDATE_ALL;
				break;
			case TracingConstants.DAMAGED_BAG:
				componentNameCreated = TracingConstants.SYSTEM_COMPONENT_NAME_DAMAGED_BAG_UPDATE_CREATED;
				componentNameAll = TracingConstants.SYSTEM_COMPONENT_NAME_DAMAGED_BAG_UPDATE_ALL;
				break;
			case TracingConstants.MISSING_ARTICLES:
				componentNameCreated = TracingConstants.SYSTEM_COMPONENT_NAME_MISSING_ARTICLES_UPDATE_CREATED;
				componentNameAll = TracingConstants.SYSTEM_COMPONENT_NAME_MISSING_ARTICLES_UPDATE_ALL;
				break;
		}
		
		// If the user has full permission, allow it to be edited.
		if (hasPermission(componentNameAll, a)) {
			return true;
		}
		
		// If the station was the created station and the user has that permission, it can be edited.
		if (inc.getStationcreated().getStation_ID() == a.getStation().getStation_ID() && 
				hasPermission(componentNameCreated, a)) {
			return true;
		}
		
		// If the agent and incident station are the same, user has permission to edit it.
		if (inc.getStationassigned().getStation_ID() == a.getStation().getStation_ID()) {
			return true;
		}

		return false;
	}
	

}