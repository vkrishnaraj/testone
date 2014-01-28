package com.bagnet.nettracer.tracing.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.struts.validator.DynaValidatorForm;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.bmo.UsergroupBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.constant.TracingConstants.SortParam;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Agent_Logger;
import com.bagnet.nettracer.tracing.db.Airport;
import com.bagnet.nettracer.tracing.db.Company;
import com.bagnet.nettracer.tracing.db.Company_Specific_Variable;
import com.bagnet.nettracer.tracing.db.Company_specific_irregularity_code;
import com.bagnet.nettracer.tracing.db.DeliverCompany;
import com.bagnet.nettracer.tracing.db.GroupComponentPolicy;
import com.bagnet.nettracer.tracing.db.IATA_irregularity_code;
import com.bagnet.nettracer.tracing.db.ItemType;
import com.bagnet.nettracer.tracing.db.NTDateFormat;
import com.bagnet.nettracer.tracing.db.NTTimeFormat;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.SystemComponent;
import com.bagnet.nettracer.tracing.db.TimeZone;
import com.bagnet.nettracer.tracing.db.UserGroup;
import com.bagnet.nettracer.tracing.db.Work_Shift;
import com.bagnet.nettracer.tracing.db.Link;
import com.bagnet.nettracer.tracing.db.audit.Audit_Agent;
import com.bagnet.nettracer.tracing.db.audit.Audit_GroupComponentPolicy;
import com.bagnet.nettracer.tracing.db.audit.Audit_UserGroup;
import com.bagnet.nettracer.tracing.db.lf.Subcompany;
import com.bagnet.nettracer.tracing.db.lf.SubcompanyStation;
import com.bagnet.nettracer.tracing.dto.ComponentDTO;
import com.bagnet.nettracer.tracing.forms.MaintainCompanyForm;
import com.bagnet.nettracer.tracing.forms.MaintainDeliveryCompanyForm;
import com.bagnet.nettracer.tracing.forms.SubCompanyForm;
import com.bagnet.nettracer.tracing.forms.UserActivityForm;
import com.bagnet.nettracer.tracing.performance.Cache;
import com.bagnet.nettracer.tracing.utils.audit.AuditAgentUtils;

/**
 * @author Ankur Gupta
 * 
 * This class provides helper routines to obtain admin related information from
 * the database.
 *  
 */
public class AdminUtils {

	private static Logger logger = Logger.getLogger(AdminUtils.class);

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
	public static int getAgentLogCount(Agent user) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			String sql = "select count(logger.ID) from " + "com.bagnet.nettracer.tracing.db.Agent_Logger logger where 1=1 ";
			sql += " and logger.agent_ID = :agent_ID";

			Query q = sess.createQuery(sql);
			q.setInteger("agent_ID", user.getAgent_ID());
			@SuppressWarnings("rawtypes")
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
	public static int getLoggedAgentCount(Agent user, UserActivityForm form, String companycode) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();

			StringBuilder sql = new StringBuilder();
			sql.append("select count(logger.ID) from com.bagnet.nettracer.tracing.db.Agent_Logger logger where 1=1 "+
					" and logger.companycode_ID = :companycode");
			java.util.TimeZone tz = java.util.TimeZone.getTimeZone(getTimeZoneById(user.getDefaulttimezone()).getTimezone());
			Date sdate = null, edate = null;
			
			@SuppressWarnings("rawtypes")
			ArrayList dateal = null;
			if ((dateal = IncidentUtils.calculateDateDiff(form.getS_time(),form.getE_time(), tz, user)) == null) {
				return -1;
			}
			sdate = (Date) dateal.get(0);
			edate = (Date) dateal.get(3);
			if (form.getS_time() != null && (!form.getS_time().equals(""))) {
				if (form.getE_time() == null || form.getE_time().equals("")) {
					sql.append(" and logger.log_in_time >= :s_date");
				} else {
					sql.append(" and logger.log_in_time >= :s_date and logger.log_in_time <= :e_date");
				}
			}
			
			Agent findagent = null;
			if (form.getAgent() != null && form.getAgent().length() > 0) {
				// get agent_id from username
				findagent = AdminUtils.getAgentBasedOnUsername(form.getAgent(),user.getCompanycode_ID());
				sql.append(" and logger.agent_ID = :agent_ID");
				if (findagent == null) return 0;
			}

			//only logged on users
			if (form.getActivity_status() == null || form.getActivity_status().length() < 1 || !form.getActivity_status().equals("-1"))
				sql.append(" and logger.log_off_time is null");

			Query q = sess.createQuery(sql.toString());
			q.setString("companycode", companycode);

			if (form.getAgent() != null && form.getAgent().length() > 0 && findagent != null) {
				q.setInteger("agent_ID", findagent.getAgent_ID());
			}

			if (form.getS_time() != null && (!form.getS_time().equals(""))) {
				if (form.getE_time() == null || form.getE_time().equals("")) {
					q.setDate("s_date", sdate);
				} else {
					q.setDate("s_date", sdate);
					q.setDate("e_date", edate);
				}
			}

			@SuppressWarnings("rawtypes")
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
	
	@SuppressWarnings({ "rawtypes" })
	public static List<Agent_Logger> getLoggedAgents(Agent user, UserActivityForm form, String companycode, int rowsperpage, int currpage) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			
			StringBuilder sql = new StringBuilder();
			sql.append("select logger  from com.bagnet.nettracer.tracing.db.Agent_Logger logger where 1=1 " +
					" and logger.companycode_ID = :companycode");

			java.util.TimeZone tz = java.util.TimeZone.getTimeZone(getTimeZoneById(user.getDefaulttimezone()).getTimezone());
			Date sdate = null, edate = null; 
			
			ArrayList dateal = null;
			if ((dateal = IncidentUtils.calculateDateDiff(form.getS_time(),form.getE_time(), tz, user)) == null) {
				return null;
			}
			sdate = (Date) dateal.get(0);
			edate = (Date) dateal.get(3);
			if (form.getS_time() != null && (!form.getS_time().equals(""))) {
				if (form.getE_time() == null || form.getE_time().equals("")) {
					sql.append(" and logger.log_in_time >= :s_date");
				} else {
					sql.append(" and logger.log_in_time >= :s_date and logger.log_in_time <= :e_date");
				}
			}

			Agent findagent = null;
			if (form.getAgent() != null && form.getAgent().length() > 0) {
				// get agent_id from username
				findagent = AdminUtils.getAgentBasedOnUsername(form.getAgent(),user.getCompanycode_ID());
				sql.append(" and logger.agent_ID = :agent_ID");
				if (findagent == null) return null;
			}

	
			//only logged on users
			if (form.getActivity_status() == null || form.getActivity_status().length() < 1 || !form.getActivity_status().equals("-1"))
				sql.append(" and logger.log_off_time is null");

			sql.append(" order by logger.log_in_time desc");

			Query q = sess.createQuery(sql.toString());

			if (rowsperpage > 0) {
				int startnum = currpage * rowsperpage;
				q.setFirstResult(startnum);
				q.setMaxResults(rowsperpage);
			}

			q.setString("companycode", companycode);

			if (form.getAgent() != null && form.getAgent().length() > 0 && findagent != null) {
				q.setInteger("agent_ID", findagent.getAgent_ID());
			}
			
			if (form.getS_time() != null && (!form.getS_time().equals(""))) {
				if (form.getE_time() == null || form.getE_time().equals("")) {
					q.setDate("s_date", sdate);
				} else {
					q.setDate("s_date", sdate);
					q.setDate("e_date", edate);
				}
			}
			@SuppressWarnings("unchecked")
			List<Agent_Logger> loglist=q.list();
			return loglist;

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
	 * Obtain company record based on passed in company code
	 * 
	 * @param companyCode
	 *          the code related to the company
	 * @return company or null if exception or nothing found.
	 */
	public static Company getCompany(String companyCode) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(Company.class).add(Restrictions.eq("companyCode_ID", companyCode));
			return (Company) cri.list().get(0);
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.fatal(e.getMessage());
				}
			}
		}
	}

	public static String getReportDescription(int type_id) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(ItemType.class);
			cri.add(Restrictions.eq("itemType_ID", new Integer(type_id)));
			return ((ItemType) cri.list().get(0)).getDescription();
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			return "";
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.fatal(e.getMessage());
				}
			}
		}
	}

	/**
	 * Get all the timezones available in the system
	 * 
	 * @return list of timezones or null if exception or nothing found.
	 */
	@SuppressWarnings("unchecked")
	public static List<TimeZone> getTimeZones() {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(TimeZone.class);
			cri.addOrder(Order.asc("id"));
			return cri.list();
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.fatal(e.getMessage());
				}
			}
		}
	}

	/**
	 * Get the timezone based on id
	 * 
	 * @param id
	 *          the timezone id
	 * @return TimeZone or null if exception or nothing found.
	 */
	public static TimeZone getTimeZoneById(String id) {
		if (Cache.TIMEZONES.contains(id)) {
			return (TimeZone) Cache.TIMEZONES.retrieve(id);
		}
		
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(TimeZone.class).add(Restrictions.eq("id", new Integer(id)));
			TimeZone retVal = (TimeZone) cri.list().get(0);
			Cache.TIMEZONES.insert(id, retVal);
			return retVal;
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.fatal(e.getMessage());
				}
			}
		}
	}

	/**
	 * Helper module to determine if the current group has access to Maintain
	 * Company module.
	 * 
	 * @param group_id
	 *          the group id to check for
	 * @return true if ogadmin rights and false otherwise
	 */
	public static boolean checkIfSuperUser(String group_id) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(UserGroup.class);
			cri.add(Restrictions.eq("userGroup_ID", new Integer(group_id)));
			cri.add(Restrictions.eq("companycode_ID", TracingConstants.OWENS_GROUP));
			@SuppressWarnings("unchecked")
			List<UserGroup> x = cri.list();
			if (x != null && x.size() > 0)
				return true;
			else
				return false;
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			return false;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.fatal(e.getMessage());
				}
			}
		}
	}

	/**
	 * Determine if a certain group has access to a specific permission
	 * 
	 * @param group_id
	 *          the group's id
	 * @param component_id
	 *          the component's id
	 * @return true if the permission exists in the particular group
	 */
	@Deprecated
	public static boolean checkIfPermissionExists(int group_id, int component_id) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(GroupComponentPolicy.class);
			cri.createCriteria("usergroup").add(Restrictions.eq("userGroup_ID", new Integer(group_id)));
			cri.createCriteria("component").add(Restrictions.eq("component_ID", new Integer(component_id)));
			@SuppressWarnings("unchecked")
			List<GroupComponentPolicy> x = cri.list();
			if (x != null && x.size() > 0)
				return true;
			else
				return false;
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			return false;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.fatal(e.getMessage());
				}
			}
		}
	}

	/**
	 * Get the date format based on passed in id
	 * 
	 * @param dateFormatID
	 *          format id
	 * @return date format, null if not found or exception
	 */
	public static NTDateFormat getDateFormat(String dateFormatID) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(NTDateFormat.class).add(Restrictions.eq("dateformat_ID", new Integer(dateFormatID)));
			return (NTDateFormat) cri.list().get(0);
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.fatal(e.getMessage());
				}
			}
		}
	}

	/**
	 * Get the time format based on passed in id
	 * 
	 * @param timeFormatID
	 *          format id
	 * @return time format, null if not found or exception
	 */
	public static NTTimeFormat getTimeFormat(String timeFormatID) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(NTTimeFormat.class).add(Restrictions.eq("timeformat_ID", new Integer(timeFormatID)));
			return (NTTimeFormat) cri.list().get(0);
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.fatal(e.getMessage());
				}
			}
		}
	}

	/**
	 * Get the group based on group's id
	 * 
	 * @param groupId
	 *          the id of the group
	 * @return group based on id
	 */
	public static UserGroup getGroup(String groupId) {
		return UsergroupBMO.getUsergroup(Integer.parseInt(groupId));
	}

	/**
	 * Retrieves the "Guest" group related to the companyCode
	 * 
	 * @param companyCode
	 * @return the group; null if not found or exception
	 */
	public static UserGroup getGuestGroup(String companyCode) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(UserGroup.class);
			cri.add(Restrictions.eq("companycode_ID", companyCode)).add(Restrictions.eq("description", "Guest"));
			return (UserGroup) cri.list().get(0);
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.fatal(e.getMessage());
				}
			}
		}
	}


	/**
	 * Get a list of stations based on passed in criteria
	 * 
	 * @param stationcode
	 *          the code of station
	 * @param companycode
	 *          the company
	 * @param locale
	 *          the language
	 * @param rowsperpage
	 * @param currpage
	 * @return the list of all station based on criteria; null on exception
	 */
	@SuppressWarnings("unchecked")
	public static List<Station> getStations(String companycode, int rowsperpage, int currpage) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(Station.class);
			cri.createCriteria("company").add(Restrictions.eq("companyCode_ID", companycode));
			if (rowsperpage > 0) {
				int startnum = currpage * rowsperpage;
				cri.setFirstResult(startnum);
				cri.setMaxResults(rowsperpage);
			}
			return cri.list();
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.fatal(e.getMessage());
				}
			}
		}
	}
	
	/**
	 * Get the shift based on its id
	 * 
	 * @param shiftId
	 *          the id of the shift
	 * @return the work shift
	 */
	public static Work_Shift getShift(String shiftId) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(Work_Shift.class).add(Restrictions.eq("shift_id", new Integer(shiftId)));
			return (Work_Shift) cri.list().get(0);
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.fatal(e.getMessage());
				}
			}
		}
	}

	/**
	 * Get the airport based on its id
	 * 
	 * @param id
	 *          the id of the airport
	 * @return the work shift
	 */
	public static Airport getAirport(String id) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(Airport.class).add(Restrictions.eq("id", new Integer(id)));
			return (Airport) cri.list().get(0);
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.fatal(e.getMessage());
				}
			}
		}
	}
	

	
	/**
	 * Get the airport based on its id
	 * 
	 * @param companyCode The company code.
	 * @param airportCode The airport code.
	 * @return the work shift
	 */
	public static Airport getAirport(String companyCode, String airportCode, String locale) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(Airport.class).add(Restrictions.eq("companyCode_ID", companyCode));
			cri.add(Restrictions.like("airport_code", airportCode));
			cri.add(Restrictions.like("locale", locale));
			if (cri.list().size() > 0) {
				return (Airport) cri.list().get(0);
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.fatal(e.getMessage());
				}
			}
		}
	}

	public static Company_Specific_Variable getCompVariable(String companycode) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			return (Company_Specific_Variable) sess.load(Company_Specific_Variable.class, companycode);
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal(e.getMessage());
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.fatal(e.getMessage());
				}
			}
		}
	}

	/**
	 * Get the list of all the companies in the system
	 * 
	 * @param rowsperpage
	 * @param currpage
	 * @return list of companies or null if exception
	 */
	@SuppressWarnings("unchecked")
	public static List<Company> getCompanies(MaintainCompanyForm dForm, int rowsperpage, int currpage) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(Company.class);

			String ccode = null;
			if (dForm != null) {
				ccode = (String) dForm.getCompanySearchName();
			}
			if (ccode != null && ccode.length() > 0) {
				cri.add(Restrictions.like("companyCode_ID", ccode));
			}
			cri.addOrder(Order.asc("companyCode_ID"));
			if (rowsperpage > 0) {
				int startnum = currpage * rowsperpage;
				cri.setFirstResult(startnum);
				cri.setMaxResults(rowsperpage);
			} 
			return cri.list();
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.fatal(e.getMessage());
				}
			}
		}
	}
	
	public static int getCompaniesCount(MaintainCompanyForm dForm){
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(Company.class);

			String ccode = null;
			if (dForm != null) {
				ccode = (String) dForm.getCompanySearchName();
			}
			if (ccode != null && ccode.length() > 0) {
				cri.add(Restrictions.like("companyCode_ID", ccode));
			}
			cri.setProjection(Projections.rowCount());
			return ((Long)cri.list().get(0)).intValue();
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			return 0;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.fatal(e.getMessage());
				}
			}
		}
	}

	/**
	 * Get the list of all the companies in the system
	 * 
	 * @param rowsperpage
	 * @param currpage
	 * @return list of companies or null if exception
	 */
	@SuppressWarnings("unchecked")
	public static List<Company> getCompaniesByName(int rowsperpage, int currpage) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(Company.class);
			cri.addOrder(Order.asc("companydesc"));
			if (rowsperpage > 0) {
				int startnum = currpage * rowsperpage;
				cri.setFirstResult(startnum);
				cri.setMaxResults(rowsperpage);
			}
			return cri.list();
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.fatal(e.getMessage());
				}
			}
		}
	}

	/**
	 * Get all the shifts associated with a given company
	 * 
	 * @param companyCode
	 * @param rowsperpage
	 *          pagination rows
	 * @param currpage
	 *          page to start from
	 * @return number of shifts in the system
	 */
	@SuppressWarnings("unchecked")
	public static List<Work_Shift> getShifts(DynaValidatorForm form, String companyCode, int rowsperpage, int currpage) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(Work_Shift.class);
			cri.createCriteria("company").add(Restrictions.eq("companyCode_ID", companyCode));
			String shift = null;
			if (form != null) {
				shift = (String) form.get("shiftSearchName");
				if (shift != null && shift.length() > 0) {
					cri.add(Restrictions.like("shift_code", shift));
				}
			}
			cri.addOrder(Order.asc("shift_description"));
			if (rowsperpage > 0) {
				int startnum = currpage * rowsperpage;
				cri.setFirstResult(startnum);
				cri.setMaxResults(rowsperpage);
			}
			return cri.list();
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.fatal(e.getMessage());
				}
			}
		}
	}
	
	/**
	 * Get all the links associated with a given company
	 * 
	 * @param companyCode
	 * @return number of links in the system
	 */
	@SuppressWarnings("unchecked")
	public static List<Link> getLinks(String companyCode) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(Link.class);
			cri.createCriteria("company").add(Restrictions.eq("companyCode_ID", companyCode));
			return cri.list();
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.fatal(e.getMessage());
				}
			}
		}
	}

	/**
	 * Get the company specific irregularity codes based on locale
	 * 
	 * @param companyCode
	 * @param locale
	 * @return list of codes null in case of exception
	 */
	@SuppressWarnings("rawtypes")
	public static List getDistinctLocaleCompanyCodes(String companyCode, int report_type) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			String query = "select distinct co.loss_code,co.description from Company_specific_irregularity_code co "
					+ " where co.company.companyCode_ID = :companycode_ID ";
			if (report_type > 0)
				query += " and report_type = :report_type";
			query += " order by loss_code";
			Query q = sess.createQuery(query);
			
			q.setString("companycode_ID", companyCode);
			if (report_type > 0)
				q.setInteger("report_type", report_type);

			List l = q.list();
			List<Company_specific_irregularity_code> l2 = new ArrayList<Company_specific_irregularity_code>();
			if (l != null) {
				Company_specific_irregularity_code co = null;
				Object o[] = null;
				for (int i = 0; i < l.size(); i++) {
					o = (Object[]) l.get(i);
					co = new Company_specific_irregularity_code();
					co.setLoss_code(((Integer) o[0]).intValue());
					co.setDescription((String) o[1]);
					l2.add(co);
				}
			}

			return l2;
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.fatal(e.getMessage());
				}
			}
		}
	}

	/**
	 * Is the given code an IATA defined code or not?
	 * 
	 * @param locale
	 * @param loss_code
	 * @return true if it is and false otherwise
	 */
	public static boolean isIATAcode(String locale, int loss_code) {
		@SuppressWarnings("unused")
		boolean ret = false;
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(IATA_irregularity_code.class).add(Restrictions.eq("locale", locale)).add(
					Restrictions.eq("loss_code", new Integer(loss_code)));
			cri.addOrder(Order.asc("loss_code"));
			@SuppressWarnings("unchecked")
			List<IATA_irregularity_code> results = cri.list();

			if (results != null && results.size() > 0)
				return true;
			else
				return false;
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			return false;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.fatal(e.getMessage());
				}
			}
		}
	}

	/**
	 * Get all the shifts associated with a given company
	 * 
	 * @param companyCode
	 * @param locale
	 * @param rowsperpage
	 * @param currpage
	 * @return shifts within a company
	 */
	@SuppressWarnings("unchecked")
	public static List<Work_Shift> getShifts(String companyCode, String locale, int rowsperpage, int currpage) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(Work_Shift.class).add(Restrictions.eq("locale", locale));
			;
			cri.addOrder(Order.asc("shift_description"));
			cri.createCriteria("company").add(Restrictions.eq("companyCode_ID", companyCode));
			if (rowsperpage > 0) {
				int startnum = currpage * rowsperpage;
				cri.setFirstResult(startnum);
				cri.setMaxResults(rowsperpage);
			}
			return cri.list();
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.fatal(e.getMessage());
				}
			}
		}
	}

	/**
	 * Get all the airports associated with a given company
	 * 
	 * @param companyCode
	 * @param locale
	 * @param rowsperpage
	 * @param currpage
	 * @return shifts within a company
	 */
	@SuppressWarnings("unchecked")
	public static List<Airport> getAirports(String companyCode, DynaValidatorForm dForm, int rowsperpage, int currpage) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(Airport.class).add(Restrictions.eq("companyCode_ID", companyCode));

			String acode = null;
			if (dForm != null)
				acode = (String) dForm.get("airportSearchName");

			if (acode != null && acode.length() > 0) {
				cri.add(Restrictions.like("airport_code", acode));
			}
			cri.addOrder(Order.asc("airport_code"));

			if (rowsperpage > 0) {
				int startnum = currpage * rowsperpage;
				cri.setFirstResult(startnum);
				cri.setMaxResults(rowsperpage);
			}
			return cri.list();
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.fatal(e.getMessage());
				}
			}
		}
	}

	/**
	 * Get a list of all the stations within a company
	 * 
	 * @param companyCode
	 * @param rowsperpage
	 * @param currpage
	 * @return list of stations
	 */
	public static List<Station> getStations(DynaValidatorForm form, String companyCode, int rowsperpage, int currpage) {
		return getCustomStations(form, companyCode, rowsperpage, currpage, TracingConstants.AgentActiveStatus.ACTIVE);
	}
	
	/**
	 * Get a list of all the stations within a company
	 * 
	 * @param companyCode
	 * @param rowsperpage
	 * @param currpage
	 * @return list of stations
	 */
	public static List<Subcompany> getSubcompanies(SubCompanyForm form, String companyCode, int rowsperpage, int currpage) {
		return getCustomSubCompanies(form, companyCode, rowsperpage, currpage);
	}
	
	
	/**
	 * Get a list of all the stations within a company
	 * 
	 * @param companyCode
	 * @param rowsperpage
	 * @param currpage
	 * @return list of stations
	 */
	@SuppressWarnings("unchecked")
	public static List<Station> getCustomStations(DynaValidatorForm form, String companyCode, int rowsperpage, int currpage, TracingConstants.AgentActiveStatus activeStatus) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(Station.class);
			cri.createCriteria("company").add(Restrictions.eq("companyCode_ID", companyCode));

			if (activeStatus.equals(TracingConstants.AgentActiveStatus.ACTIVE)) {
				cri.add(Restrictions.eq("active", true));
			} else if (activeStatus.equals(TracingConstants.AgentActiveStatus.INACTIVE)) {
				cri.add(Restrictions.eq("active", false));
			}

			String code = null;

			if (form != null) {
				code = (String) form.get("stationSearchName");
			}

			if (code != null && code.length() > 0) {
				cri.add(Restrictions.like("stationcode", code));
			}
			cri.addOrder(Order.asc("stationcode"));
			if (rowsperpage > 0) {
				int startnum = currpage * rowsperpage;
				cri.setFirstResult(startnum);
				cri.setMaxResults(rowsperpage);
			}
			return (List<Station>)cri.list();
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.fatal(e.getMessage());
				}
			}
		}
	}
	
	/**
	 * Get a list of all the stations within a subcompany
	 * 
	 * @param subcompanyCode
	 * @param rowsperpage
	 * @param currpage
	 * @return list of stations
	 */
	@SuppressWarnings("unchecked")
	public static List<Station> getCustomStations(SubCompanyForm form, int rowsperpage, int currpage, TracingConstants.AgentActiveStatus activeStatus) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(Station.class);
			cri.createCriteria("subcompany").add(Restrictions.eq("subcompany_id", form.getId()));
			
			if (rowsperpage > 0) {
				int startnum = currpage * rowsperpage;
				cri.setFirstResult(startnum);
				cri.setMaxResults(rowsperpage);
			}
			return cri.list();
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.fatal(e.getMessage());
				}
			}
		}
	}
	
	/**
	 * Get a list of all the subcompanies within a company
	 * 
	 * @param companyCode
	 * @param rowsperpage
	 * @param currpage
	 * @return list of stations
	 */
	@SuppressWarnings("unchecked")
	public static List<Subcompany> getCustomSubCompanies(SubCompanyForm form, String companyCode, int rowsperpage, int currpage) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();

			String sql = "from Subcompany s where s.company.companyCode_ID = :companycode" +
					" order by subcompanyCode asc";
			
			Query q = sess.createQuery(sql);
			q.setParameter("companycode", companyCode);

		
			if (rowsperpage > 0) {
				int startnum = currpage * rowsperpage;
				q.setFirstResult(startnum);
				q.setMaxResults(rowsperpage);
			}
			return q.list();
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.fatal(e.getMessage());
				}
			}
		}
	}
	
	public static SubcompanyStation getSubcompanyStation(long subcompId, long stationId){
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(SubcompanyStation.class);
			cri.createCriteria("subcompany").add(Restrictions.eq("id", subcompId));
			cri.createCriteria("station").add(Restrictions.eq("station_ID", Integer.valueOf(String.valueOf(stationId))));
			return (SubcompanyStation) cri.uniqueResult();
			
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.fatal(e.getMessage());
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public static List<SubcompanyStation> getSubcompanyStationsBySubcompany(long subcompId){
		Session sess = null;
		try {
			Subcompany sub = new Subcompany();
			sub.setId(subcompId);
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(SubcompanyStation.class);
			cri.add(Restrictions.eq("subcompany", sub));
			return cri.list();
			
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.fatal(e.getMessage());
				}
			}
		}
	}

	/**
	 * Get a list of all the delivery companies within a company
	 * 
	 * @param companyCode
	 * @param rowsperpage
	 * @param currpage
	 * @return list of stations
	 */
	@SuppressWarnings("unchecked")
	public static List<DeliverCompany> getDeliveryCompanies(MaintainDeliveryCompanyForm form, Company company, int rowsperpage, int currpage) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(DeliverCompany.class).add(Restrictions.eq("active", true));
			cri.add(Restrictions.eq("company", company));

			String companyName = null;

			if (form != null) {
				companyName = (String) form.getCompanySearchName();
			}

			if (companyName != null && companyName.length() > 0) {
				cri.add(Restrictions.like("name", companyName));
			}
			
			cri.addOrder(Order.asc("name"));
			if (rowsperpage > 0) {
				int startnum = currpage * rowsperpage;
				cri.setFirstResult(startnum);
				cri.setMaxResults(rowsperpage);
			}
			return cri.list();
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.fatal(e.getMessage());
				}
			}
		}
	}

	
	/**
	 * Retrieve an agent based on its id
	 * 
	 * @param agentID
	 * @return agent; null if not found or exception
	 */
	public static Agent getAgent(String agentID) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			return (Agent) sess.load(Agent.class, Integer.parseInt(agentID));
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.fatal(e.getMessage());
				}
			}
		}
	}
	
	/**
	 * Retrieve an agent based on its id
	 * 
	 * @param agentID
	 * @return agent; null if not found or exception
	 */
	public static Station getStation(String stationID) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			return (Station) sess.load(Station.class, Integer.parseInt(stationID));
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.fatal(e.getMessage());
				}
			}
		}
	}

	/**
	 * Retrieve agent based on company and username
	 * 
	 * @param username
	 * @param companyCode
	 * @return agent if found, null otherwise
	 */
	public static Agent getAgentBasedOnUsername(String username, String companyCode) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess
			.createQuery("select agent from com.bagnet.nettracer.tracing.db.Agent agent"
					+ " where agent.username = :user and agent.station.company.companyCode_ID = :code");
			q.setString("user", username);
			q.setString("code", companyCode.toUpperCase());
			@SuppressWarnings("unchecked")
			List<Agent> ret = q.list();
			if (ret == null || ret.size() == 0)
				return null;
			else
				return ret.get(0);
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.fatal(e.getMessage());
				}
			}
		}
	}

	/**
	 * Find agent based on username password and company code
	 * 
	 * @param username
	 * @param password
	 * @param companyCode
	 * @return agent if found and null otherwise
	 */
	public static Agent getAgentBasedOnUsernamePassword(String username, String password, String companyCode) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(Agent.class).add(Restrictions.eq("username", username));
			cri.createCriteria("station").createCriteria("company").add(Restrictions.eq("companyCode_ID", companyCode));
			@SuppressWarnings("unchecked")
			List<Agent> ret = cri.list();
			if (ret == null || ret.size() == 0)
				return null;
			else
				return (Agent) ret.get(0);
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.fatal(e.getMessage());
				}
			}
		}
	}

	
	
	/**
	 * Retrieves agents belonging to a company
	 * 
	 * @param companyCode
	 * @param sort
	 * @param rowsperpage
	 * @param currpage
	 * @return
	 */
	public static List<Agent> getAgents(String companyCode, String sort, DynaValidatorForm dForm, int rowsperpage, int currpage) {
		return getCustomAgents(null, companyCode, sort, dForm, rowsperpage, currpage, TracingConstants.AgentActiveStatus.ACTIVE);
	}

	/**
	 * Retrieves agents belonging to the station
	 * 
	 * @param station_Id
	 * @param sort
	 * @param rowsperpage
	 * @param currpage
	 * @return
	 */
	public static List<Agent> getAgentsByStation(String station_Id, String sort, DynaValidatorForm dForm, int rowsperpage, int currpage) {
		return getCustomAgents(station_Id, null, sort, dForm, rowsperpage, currpage, TracingConstants.AgentActiveStatus.ACTIVE);
	}
	
	/**
	 * Retrieves stations belonging to the subcompany
	 * 
	 * @param station_Id
	 * @param sort
	 * @param rowsperpage
	 * @param currpage
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Station> getStationsBySubcompany(String subcompId, String companyCode, int rowsperpage, int currpage) {
		//return getCustomStations(subcomp_id, null, sort, form, rowsperpage, currpage, TracingConstants.AgentActiveStatus.ACTIVE);
		Session sess = null;

		try {
			sess = HibernateWrapper.getSession().openSession();
			
			StringBuffer sql = new StringBuffer(512);
			sql.append("select distinct station from com.bagnet.nettracer.tracing.db.Station station, com.bagnet.nettracer.tracing.db.lf.SubcompanyStation scs where station.station_ID= scs.station.station_ID ");
			if (subcompId != null && subcompId.length() > 0)
				sql.append(" and scs.subcompany.id = :subcomp_ID ");

			if (companyCode != null && companyCode.length() > 0)
				sql.append(" and station.company.companyCode_ID = :companyCode_ID ");

				sql.append(" order by station.stationcode asc ");
			
			Query q = sess.createQuery(sql.toString());
			if (rowsperpage > 0) {
				int startnum = currpage * rowsperpage;
				q.setFirstResult(startnum);
				q.setMaxResults(rowsperpage);
			}
			
			if (subcompId != null && subcompId.length() > 0)
				q.setInteger("subcomp_ID", Integer.parseInt(subcompId));
			if (companyCode != null && companyCode.length() > 0)
				q.setString("companyCode_ID", companyCode);

			return q.list();
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.fatal(e.getMessage());
				}
			}
		}
	}


	/**
	 * Retrieves agents belonging to a company, or station and accounts for
	 * agent's active status
	 * 
	 * @param companyCode
	 * @param sort
	 * @param rowsperpage
	 * @param currpage
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Agent> getCustomAgents(String stationId, String companyCode, String sort, DynaValidatorForm dForm, int rowsperpage, int currpage, TracingConstants.AgentActiveStatus activeStatus) {
		Session sess = null;

		try {
			sess = HibernateWrapper.getSession().openSession();
			
			String username = null;
			String fname = null;
			String lname = null;
			String agentType = null;
			
			StringBuffer sql = new StringBuffer(512);
			sql.append("select distinct agent from com.bagnet.nettracer.tracing.db.Agent agent, com.bagnet.nettracer.tracing.db.UserGroup ug where agent.usergroup_id = ug.userGroup_ID ");
			if (stationId != null && stationId.length() > 0)
				sql.append(" and agent.station.station_ID = :station_ID ");

			if (companyCode != null && companyCode.length() > 0)
				sql.append(" and agent.station.company.companyCode_ID = :companyCode_ID ");

			if (activeStatus.equals(TracingConstants.AgentActiveStatus.ACTIVE))
				sql.append(" and agent.active = 1 ");

			if (activeStatus.equals(TracingConstants.AgentActiveStatus.INACTIVE)) 
				sql.append(" and agent.active = 0 ");

			if (dForm != null) {
				username = (String) dForm.get("agentSearchName");
				if (username != null && username.length() > 0) {
					sql.append(" and agent.username like :username ");
				}
				
				fname = (String) dForm.get("agentSearchFName");
				if (fname != null && fname.length() > 0) {
					sql.append(" and agent.firstname like :fname ");
				}
	
				lname = (String) dForm.get("agentSearchLName");
				if (lname != null && lname.length() > 0) {
					sql.append(" and agent.lastname like :lname ");
				}
				
				agentType = (String) dForm.get("agentType");			
				
				if (agentType != null && agentType.length() > 0) {
					if (agentType.equals(TracingConstants.MAINTAIN_AGENTS_TYPE_WEBSRVICE))
						sql.append(" and agent.ws_enabled = 1 ");
					else if (agentType.equals(TracingConstants.MAINTAIN_AGENTS_TYPE_AGENTS))
						sql.append(" and agent.web_enabled = 1 ");
				} else {
					sql.append(" and agent.web_enabled = 1 ");
				}

			}
			
			
			if (sort != null && sort.length() > 0) {
				if (sort.equalsIgnoreCase(SortParam.LASTNAME.getParamString())) {
					sql.append(" order by agent.lastname asc ");
				} else {
					if (sort.equalsIgnoreCase(SortParam.ACTIVE.getParamString())) {
						sql.append(" order by agent.active desc ");
					} else {
						if (sort.equalsIgnoreCase(SortParam.USERNAME.getParamString())) {
							sql.append(" order by agent.username asc ");
						} else {
							if (sort.equalsIgnoreCase(SortParam.GROUP.getParamString())) {
								sql.append(" order by ug.description asc ");
							} else {
								if (sort.equalsIgnoreCase(SortParam.STATION.getParamString())) {
									sql.append(" order by agent.station.stationcode asc ");
								}
							}
						}
					}
				}
			} else {
				sql.append(" order by agent.username asc ");
			}
			
			Query q = sess.createQuery(sql.toString());
			if (rowsperpage > 0) {
				int startnum = currpage * rowsperpage;
				q.setFirstResult(startnum);
				q.setMaxResults(rowsperpage);
			}
			
			if (stationId != null && stationId.length() > 0)
				q.setInteger("station_ID", Integer.parseInt(stationId));
			if (companyCode != null && companyCode.length() > 0)
				q.setString("companyCode_ID", companyCode);

			if (username != null && username.length() > 0) {
				q.setString("username", username);
			}
			
			if (fname != null && fname.length() > 0) {
				q.setString("fname", fname);
			}
			if (lname != null && lname.length() > 0) {
				q.setString("lname", lname);
			}

			return (List<Agent>)q.list();
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.fatal(e.getMessage());
				}
			}
		}
	}
	
	
	/**
	 * Retrieves agents belonging to the group
	 * 
	 * @param groupId
	 * @param sort
	 * @param rowsperpage
	 * @param currpage
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Agent> getAgentsByGroup(String groupId, String sort, int rowsperpage, int currpage) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			StringBuffer sql = new StringBuffer(512);
			sql.append("select distinct agent from com.bagnet.nettracer.tracing.db.Agent agent where 1=1 ");
			sql.append(" and agent.usergroup_id = :userGroup_ID ");

			if (sort != null && sort.length() > 0) {
				if (sort.equalsIgnoreCase(SortParam.LASTNAME.getParamString())) {
					sql.append(" order by agent.lastname asc ");
				} else {
					if (sort.equalsIgnoreCase(SortParam.ACTIVE.getParamString())) {
						sql.append(" order by agent.active desc ");
					} else {
						if (sort.equalsIgnoreCase(SortParam.USERNAME.getParamString())) {
							sql.append(" order by agent.username asc ");
						} else {
							if (sort.equalsIgnoreCase(SortParam.STATION.getParamString())) {
								sql.append(" order by agent.station.stationcode asc ");
							}
						}
					}
				}
			} else {
				sql.append(" order by agent.username asc ");
			}
			Query q = sess.createQuery(sql.toString());
			if (rowsperpage > 0) {
				int startnum = currpage * rowsperpage;
				q.setFirstResult(startnum);
				q.setMaxResults(rowsperpage);
			}

			q.setInteger("userGroup_ID", Integer.parseInt(groupId));
			return q.list();
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.fatal(e.getMessage());
				}
			}
		}
	}

	/**
	 * Retrieves groups for a given company
	 * 
	 * @param companyCode
	 * @param rowsperpage
	 * @param currpage
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<UserGroup> getGroups(DynaValidatorForm form, String companyCode, int rowsperpage, int currpage) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(UserGroup.class);
			cri.add(Restrictions.eq("companycode_ID", companyCode));
			String groupName = null;
			if (form != null) {
				groupName = (String) form.get("groupSearchName");
			}
			if (groupName != null && groupName.length() > 0) {
				cri.add(Restrictions.like("description", groupName));
			}
			cri.addOrder(Order.asc("description"));
			if (rowsperpage > 0) {
				int startnum = currpage * rowsperpage;
				cri.setFirstResult(startnum);
				cri.setMaxResults(rowsperpage);
			}
			return cri.list();
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.fatal(e.getMessage());
				}
			}
		}
	}
	
	public static List<UserGroup> getGroups(String companyCode) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(UserGroup.class);
			cri.add(Restrictions.eq("companycode_ID", companyCode));
			@SuppressWarnings("unchecked")
			List<UserGroup> list = cri.list();
			
			if (list.size() == 0) return null;
			
			return list;
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.fatal(e.getMessage());
				}
			}
		}
	}

	/**
	 * Retrieves the permissions within a group
	 * 
	 * @param groupID
	 * @param loggedOnUserGroupID
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static TreeMap getComponentTreeMap(String groupID, Agent user) {
		
		
		boolean isOwensGrp = user.getStation().getCompany().getCompanyCode_ID().equals(TracingConstants.OWENS_GROUP);
		UserGroup currentUsersGroup = user.getGroup();
		HashMap<Integer, Integer> currentUserPermissionMap = new HashMap<Integer, Integer>();
		Set<GroupComponentPolicy> l = (Set<GroupComponentPolicy>)currentUsersGroup.getComponentPolicies();

		if (l != null) {
			for (GroupComponentPolicy p: l) {
				currentUserPermissionMap.put(p.getComponent().getComponent_ID(), 1);
			}
		}
		
		UserGroup queryingGroup = UsergroupBMO.getUsergroup(Integer.parseInt(groupID));
		HashMap<Integer, Integer> queryUserPermissionMap = new HashMap<Integer, Integer>();
		l = (Set<GroupComponentPolicy>)queryingGroup.getComponentPolicies();

		if (l != null) {
			for (GroupComponentPolicy p: l) {
				queryUserPermissionMap.put(p.getComponent().getComponent_ID(), 1);
			}
		}
		
		
		TreeMap permissionMap = new TreeMap();
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			
			//Obtain all parent components
			StringBuffer sql = new StringBuffer(1024);
			sql.append("select distinct component from com.bagnet.nettracer.tracing.db.SystemComponent component ");
			sql.append(" where 1=1 ");
			sql.append(" and component.component_ID = component.parent.component_ID");
			sql.append(" order by component.sort_order ");

			Query q = sess.createQuery(sql.toString());
			List componentList = q.list();
			for (Iterator i = componentList.iterator(); i.hasNext();) {
				SystemComponent component = (SystemComponent) i.next();
				//If the component exists in the parent group id..
				if (isOwensGrp|| currentUserPermissionMap.containsKey(component.getComponent_ID())) {

					//do for parent component.
					boolean isChecked = queryUserPermissionMap.containsKey(component.getComponent_ID());
					int checked = isChecked ? 1 : 0;
					TreeMap parentMap = new TreeMap();
					permissionMap.put("" + component.getComponent_Name() + "#" + component.getComponent_ID() + "#" + checked, parentMap);

					//Go through the child components.
					List childList = getChildComponents(component.getComponent_ID());
					for (Iterator j = childList.iterator(); j.hasNext();) {
						SystemComponent component2 = (SystemComponent) j.next();
						if (isOwensGrp || currentUserPermissionMap.containsKey(component2.getComponent_ID())) {

							//do for parent component.
							boolean isChecked2 = queryUserPermissionMap.containsKey(component2.getComponent_ID());

							ComponentDTO cDTO = new ComponentDTO();
							cDTO.populate(component2, isChecked2);
							parentMap.put(component2.getComponent_Name(), cDTO);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.fatal(e.getMessage());
				}
			}
		}
		
		
		return permissionMap;
		
	}

	@SuppressWarnings("rawtypes")
	public static List getChildComponents(int parent_id) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();

			StringBuffer sql = new StringBuffer(1024);

			sql.append("select distinct component from com.bagnet.nettracer.tracing.db.SystemComponent component ");
			sql.append(" where 1=1 ");
			sql.append(" and component.component_ID != component.parent.component_ID ");
			sql.append(" and component.parent.component_ID = :parent_ID ");
			sql.append(" order by component.sort_order asc ");

			Query q = sess.createQuery(sql.toString());
			q.setInteger("parent_ID", parent_id);

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
	 * Saves the new set of permissions within a group.
	 * 
	 * @param groupID
	 * @param request
	 * @throws Exception
	 */
	public static void saveComponents(String groupID, Audit_UserGroup audit_group, HttpServletRequest request, Agent user) {
		Session sess = null;
		Transaction t = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			t = sess.beginTransaction();

			UserGroup u = getGroup(groupID);
			for (Iterator<GroupComponentPolicy> i = u.getComponentPolicies().iterator(); i.hasNext();) {
				GroupComponentPolicy gcp = (GroupComponentPolicy) i.next();
				sess.delete(gcp);
			}
			u.setComponentPolicies(new HashSet<GroupComponentPolicy>());

			//delete all the group relationships for this group Id.
			@SuppressWarnings("rawtypes")
			Enumeration e = request.getParameterNames();
			while (e.hasMoreElements()) {
				String parameter = (String) e.nextElement();
				int i = -1;
				try {
					i = Integer.parseInt(parameter);
				} catch (Exception ex) {
				}
				if (i != -1) {
					GroupComponentPolicy policy = new GroupComponentPolicy();
					policy.setUsergroup(u);
					SystemComponent component = new SystemComponent();
					component.setComponent_ID(i);
					policy.setComponent(component);

					sess.save(policy);

					if (audit_group.getAudit_id() > 0) {
						if (user.getStation().getCompany().getVariable().getAudit_group() == 1) {
							Audit_GroupComponentPolicy plicy2 = new Audit_GroupComponentPolicy();
							BeanUtils.copyProperties(plicy2, policy);
							plicy2.setAudit_usergroup(audit_group);
							HibernateUtils.saveNew(plicy2);
						}
					}
				}
			}
			t.commit();

		} catch (Exception e) {
			e.printStackTrace();
			if (t != null) {
				try {
					t.rollback();
				} catch (Exception f) {
					logger.fatal(f.getMessage());
				}
			}
			logger.fatal(e.getMessage());
		} finally {
			try {
				if (sess != null)
					sess.close();
			} catch (Exception e) {
				logger.fatal(e.getMessage());
			}
		}
	}
	
	
	public static int getAssignedAgent(Agent user) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			String sql = "select count(incident.incident_ID) from " + "com.bagnet.nettracer.tracing.db.Incident incident where 1=1 ";
			sql += " and incident.agentassigned.agent_ID = :agent_ID";

			Query q = sess.createQuery(sql);
			q.setInteger("agent_ID", user.getAgent_ID());
			@SuppressWarnings("rawtypes")
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
	 * Creates a new Agent provided that an agent for the given username does not already exists.
	 * 
	 * Bases defaults (language, currency, dateformat) from the ntadmin account of the given company 
	 * (may need to refactor in future if defaults are provided through assertion)
	 * 
	 * @param username
	 * @param fname
	 * @param lname
	 * @param groupNames
	 * @param stationCode
	 * @param compCode
	 * @return
	 */
	public static Agent createAgent(String username, String fname, String lname, List<String> groupNames,
			String stationCode, String compCode) {

		/** If an agent with the user name already exists, do not create a new agent **/
		Agent agent = getAgentBasedOnUsername(username, compCode);
		if(agent!=null){
			logger.error("Agent with Username: "+username+" already exists - method should not run");
			return null;
		}
		
		/** load ntadmin account for given company and populate defaults **/
		Agent ntuser = getAgentBasedOnUsername("ntadmin", compCode);
		if(ntuser == null){
			//could not load defaults
			return null;
		}
		
		Agent u=new Agent();

		u.setUsername(username);
		u.setLastname(lname);
		u.setFirstname(fname);
		
		/**
		 * SF: Get a group based off the name provided in the Assertion Call. If no
		 * Group is available based on the name, default to Claims group
		 **/
		int groupId=0;
		if(groupNames!=null && groupNames.size()>0){
			groupId=UsergroupBMO.getUsergroupMapId(groupNames);
		}
		if(groupId>0){
			u.setUsergroup_id(groupId);
		} else {
			/** If no proper group is provide, do not create a user for the agent */
			return null;
		}
		
		/** SF: Get a station based off the station code provided in Assertion call. 
		 * If no station is available, default the ntadmin Station
		 */
		Station station=StationBMO.getStationByCode(stationCode,compCode);
		if(station!=null){
			u.setStation(station);
		} else {
			u.setStation(ntuser.getStation());
		}
		
		u.setCompanycode_ID(compCode);
		
		u.setActive(true);
		u.setWeb_enabled(true);
		u.setWs_enabled(true);
		
		/** Default other values to blank or ntadmin values **/
		u.setDefaultlocale(ntuser.getDefaultlocale());
		u.setMname("");
		u.setCurrentlocale(ntuser.getCurrentlocale());
		u.setDefaultcurrency(ntuser.getDefaultcurrency());
		
		String defaulttimezone=PropertyBMO.getValue(PropertyBMO.DEFAULT_AUTOPROVISION_TIMEZONE);
		if(defaulttimezone==null){
			defaulttimezone=ntuser.getDefaulttimezone();
		}
		u.setDefaulttimezone(defaulttimezone);
		
		String currenttimezone=PropertyBMO.getValue(PropertyBMO.CURRENT_AUTOPROVISION_TIMEZONE);
		if(currenttimezone==null){
			currenttimezone=ntuser.getCurrenttimezone();
		}		
		u.setCurrenttimezone(currenttimezone);
		
		u.setDateformat(ntuser.getDateformat());
		u.setTimeformat(ntuser.getTimeformat());
		u.setTimeout(ntuser.getTimeout());
		
		String pass=StringUtils.sha1_256("ntp@ssw0rd"); //Default Password
		u.setPassword(pass);
		u.setReset_password(false);
		
		HibernateUtils.save(u);
		if(u.getAgent_ID()>0){
			//check if audit is enabled for this company....
			if (AdminUtils.getCompVariable(u.getCompanycode_ID()).getAudit_agent() == 1) {
				Audit_Agent audit_agent;
				try {
					/** Since this an auto-provision agent, the mod agent is the agent that is being created **/
					audit_agent = AuditAgentUtils.getAuditAgent(u, u);
					if (audit_agent != null) {
						HibernateUtils.saveNew(audit_agent);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return u;
		} else {
			return null;
		}
	}
}