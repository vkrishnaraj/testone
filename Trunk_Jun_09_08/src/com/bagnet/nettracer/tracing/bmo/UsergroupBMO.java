package com.bagnet.nettracer.tracing.bmo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.db.UserGroup;
import com.bagnet.nettracer.tracing.db.UsergroupNameMap;

public class UsergroupBMO {

	private static Logger logger = Logger.getLogger(UsergroupBMO.class);
	
	/**
	 * 
	 */
	public static UserGroup getUsergroup(int usergroup_ID) throws HibernateException {
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			return (UserGroup) sess.load(UserGroup.class, usergroup_ID);
			
		} catch (Exception e) {
			logger.error("unable to retrieve station from database: " + e);
			e.printStackTrace();
			return null;
		} finally {
			sess.close();
		}
	}
	

	public static UserGroup getUsergroupByName(String usergroupname) throws HibernateException {
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			Criteria crit = sess.createCriteria(UserGroup.class).add(
					Restrictions.eq("description", usergroupname).ignoreCase());
			@SuppressWarnings("unchecked")
			List<UserGroup> grouplist = crit.list();
			UserGroup group=null;
			if(grouplist!=null && grouplist.size()>0){
				group=grouplist.get(0);
			}
			return group;
		} catch (Exception e) {
			logger.error("unable to retrieve usergroup from database: " + e);
			e.printStackTrace();
			return null;
		} finally {
			sess.close();
		}
	}
	
	public static int getUsergroupMapId(List<String> groupNames) {
		Map<String,Integer> nameMap=getUsergroupMapNames();
		if(nameMap!=null){
			for(String l:groupNames){
				if(nameMap.get(l)!=null){
					return nameMap.get(l);
				}
			}
		}
		return 0;
	}
	
	@SuppressWarnings("unchecked")
	private static Map<String,Integer> getUsergroupMapNames(){
		Map<String,Integer> nameMap=new HashMap<String,Integer>();

		List<UsergroupNameMap> retrieval = null;
		String hql="from UsergroupNameMap";
		
		Session sess = HibernateWrapper.getSession().openSession();
		Query q=sess.createQuery(hql);
		retrieval = q.list();
		if(retrieval!=null){
			for(UsergroupNameMap name:retrieval){
				nameMap.put(name.getLdapName(), name.getNtGroupId());
			}
			return nameMap;
		}
		return null;
	}
}
