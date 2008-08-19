package com.bagnet.nettracer.tracing.utils;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.struts.validator.DynaValidatorForm;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
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
import com.bagnet.nettracer.tracing.db.audit.Audit_GroupComponentPolicy;
import com.bagnet.nettracer.tracing.db.audit.Audit_UserGroup;
import com.bagnet.nettracer.tracing.dto.ComponentDTO;
import com.bagnet.nettracer.tracing.forms.MaintainCompanyForm;
import com.bagnet.nettracer.tracing.forms.MaintainDeliveryCompanyForm;
import com.bagnet.nettracer.tracing.forms.UserActivityForm;

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
			String sql = "select count(logger.ID) from " + "com.bagnet.nettracer.tracing.db.Agent_Logger logger where 1=1 ";
			sql += " and logger.companycode_ID = :companycode";

			if (form.getS_time() != null && (!form.getS_time().equals(""))) {
				if (form.getE_time() == null || form.getE_time().equals("")) {
					sql += " and logger.log_in_time >= :s_date";
				} else {
					sql += " and logger.log_in_time >= :s_date and logger.log_in_time <= :e_date";
				}
			}
			
			Agent findagent = null;
			if (form.getAgent() != null && form.getAgent().length() > 0) {
				// get agent_id from username
				findagent = AdminUtils.getAgentBasedOnUsername(form.getAgent(),user.getCompanycode_ID());
				sql += " and logger.agent_ID = :agent_ID";
				if (findagent == null) return 0;
			}

			//only logged on users
			if (form.getActivity_status() == null || form.getActivity_status().length() < 1 || !form.getActivity_status().equals("-1"))
				sql += " and logger.log_off_time is null";

			Query q = sess.createQuery(sql);
			q.setString("companycode", companycode);

			if (form.getAgent() != null && form.getAgent().length() > 0 && findagent != null) {
				q.setInteger("agent_ID", findagent.getAgent_ID());
			}

			if (form.getS_time() != null && (!form.getS_time().equals(""))) {
				if (form.getE_time() == null || form.getE_time().equals("")) {
					q.setDate("s_date", DateUtils.convertToDate(form.getS_time(), user.getDateformat().getFormat(), user.getCurrentlocale()));
				} else {
					q.setDate("s_date", DateUtils.convertToDate(form.getS_time(), user.getDateformat().getFormat(), user.getCurrentlocale()));
					q.setDate("e_date", DateUtils.convertToDate(form.getE_time(), user.getDateformat().getFormat(), user.getCurrentlocale()));
				}
			}

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

	public static List getLoggedAgents(Agent user, UserActivityForm form, String companycode, int rowsperpage, int currpage) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			String sql = "select logger from " + "com.bagnet.nettracer.tracing.db.Agent_Logger logger where 1=1 ";
			sql += " and logger.companycode_ID = :companycode";

			if (form.getS_time() != null && (!form.getS_time().equals(""))) {
				if (form.getE_time() == null || form.getE_time().equals("")) {
					sql += " and logger.log_in_time >= :s_date";
				} else {
					sql += " and logger.log_in_time >= :s_date and logger.log_in_time <= :e_date";
				}
			}

			Agent findagent = null;
			if (form.getAgent() != null && form.getAgent().length() > 0) {
				// get agent_id from username
				findagent = AdminUtils.getAgentBasedOnUsername(form.getAgent(),user.getCompanycode_ID());
				sql += " and logger.agent_ID = :agent_ID";
				if (findagent == null) return null;
			}

	
			//only logged on users
			if (form.getActivity_status() == null || form.getActivity_status().length() < 1 || !form.getActivity_status().equals("-1"))
				sql += " and logger.log_off_time is null";

			sql += " order by logger.log_in_time desc";

			Query q = sess.createQuery(sql);

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
					q.setDate("s_date", DateUtils.convertToDate(form.getS_time(), user.getDateformat().getFormat(), user.getCurrentlocale()));
				} else {
					q.setDate("s_date", DateUtils.convertToDate(form.getS_time(), user.getDateformat().getFormat(), user.getCurrentlocale()));
					q.setDate("e_date", DateUtils.convertToDate(form.getE_time(), user.getDateformat().getFormat(), user.getCurrentlocale()));
				}
			}

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
			Criteria cri = sess.createCriteria(Company.class).add(Expression.eq("companyCode_ID", companyCode));
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

	public static String getReportDescription(int type_id, String locale) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(ItemType.class);
			cri.add(Expression.eq("itemType_ID", new Integer(type_id)));
			cri.add(Expression.eq("locale", locale));
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
	public static List getTimeZones() {
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
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(TimeZone.class).add(Expression.eq("id", new Integer(id)));
			return (TimeZone) cri.list().get(0);
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
			cri.add(Expression.eq("userGroup_ID", new Integer(group_id)));
			cri.add(Expression.eq("companycode_ID", TracingConstants.OWENS_GROUP));
			List x = cri.list();
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
	public static boolean checkIfPermissionExists(int group_id, int component_id) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(GroupComponentPolicy.class);
			cri.createCriteria("usergroup").add(Expression.eq("userGroup_ID", new Integer(group_id)));
			cri.createCriteria("component").add(Expression.eq("component_ID", new Integer(component_id)));
			List x = cri.list();
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
			Criteria cri = sess.createCriteria(NTDateFormat.class).add(Expression.eq("dateformat_ID", new Integer(dateFormatID)));
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
			Criteria cri = sess.createCriteria(NTTimeFormat.class).add(Expression.eq("timeformat_ID", new Integer(timeFormatID)));
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
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(UserGroup.class).add(Expression.eq("userGroup_ID", new Integer(groupId)));
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
			cri.add(Expression.eq("companycode_ID", companyCode)).add(Expression.eq("description", "Guest"));
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
	public static List getStations(String stationcode, String companycode, String locale, int rowsperpage, int currpage) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(Station.class).add(Expression.eq("stationcode", stationcode)).add(Expression.eq("locale", locale));
			cri.createCriteria("company").add(Expression.eq("companyCode_ID", companycode));
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
			Criteria cri = sess.createCriteria(Work_Shift.class).add(Expression.eq("shift_id", new Integer(shiftId)));
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
			Criteria cri = sess.createCriteria(Airport.class).add(Expression.eq("id", new Integer(id)));
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
			Criteria cri = sess.createCriteria(Airport.class).add(Expression.eq("companyCode_ID", companyCode));
			cri.add(Expression.like("airport_code", airportCode));
			cri.add(Expression.like("locale", locale));
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

	/**
	 * Get the loss_code based on the passed in id
	 * 
	 * @param loss_code
	 * @return the irregularity code null if exception or not found
	 */
	public static Company_specific_irregularity_code getCode(String code_id) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(Company_specific_irregularity_code.class).add(Expression.eq("code_id", new Integer(code_id)));
			return (Company_specific_irregularity_code) cri.list().get(0);
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
	 * Get the loss_code based on the passed in id
	 * 
	 * @param loss_code
	 * @return the irregularity code null if exception or not found
	 */
	public static Company_specific_irregularity_code getLossCode(int loss_code, int report_type, String locale, Company company) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(Company_specific_irregularity_code.class).add(Expression.eq("loss_code", new Integer(loss_code))).add(
					Expression.eq("report_type", new Integer(report_type))).add(Expression.eq("locale", locale)).add(Expression.eq("company", company));
			List list = cri.list();
			if (list != null && list.size() > 0){
				return (Company_specific_irregularity_code) cri.list().get(0);
			}
			return null;
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
			Criteria cri = sess.createCriteria(Company_Specific_Variable.class).add(Expression.eq("companyCode_ID", companycode));
			return (Company_Specific_Variable) cri.list().get(0);
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
	 * Get the list of all the companies in the system
	 * 
	 * @param rowsperpage
	 * @param currpage
	 * @return list of companies or null if exception
	 */
	public static List getCompanies(MaintainCompanyForm dForm, int rowsperpage, int currpage) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(Company.class);

			String ccode = null;
			if (dForm != null) {
				ccode = (String) dForm.getCompanySearchName();
			}
			if (ccode != null && ccode.length() > 0) {
				cri.add(Expression.like("companyCode_ID", ccode));
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

	/**
	 * Get the list of all the companies in the system
	 * 
	 * @param rowsperpage
	 * @param currpage
	 * @return list of companies or null if exception
	 */
	public static List getCompaniesByName(int rowsperpage, int currpage) {
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
	public static List getShifts(DynaValidatorForm form, String companyCode, int rowsperpage, int currpage) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(Work_Shift.class);
			cri.createCriteria("company").add(Expression.eq("companyCode_ID", companyCode));
			String shift = null;
			if (form != null) {
				shift = (String) form.get("shiftSearchName");
				if (shift != null && shift.length() > 0) {
					cri.add(Expression.like("shift_code", shift));
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
	 * Retrieve all the irregularity codes associated with a given company
	 * 
	 * @param companyCode
	 * @param rowsperpage
	 * @param currpage
	 * @return list of codes null in case of exception
	 */
	public static List getCodes(String companyCode, String report_type, DynaValidatorForm dForm, int rowsperpage, int currpage) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(Company_specific_irregularity_code.class);
			cri.createCriteria("company").add(Expression.eq("companyCode_ID", companyCode));

			if (report_type.length() > 0) {
				cri.add(Expression.eq("report_type", new Integer(report_type)));
			}

			String code = null;
			int intCode = -1;

			if (dForm != null) {
				code = (String) dForm.get("codeSearchName");

				if (code != null && code.length() > 0) {
					try {
						intCode = Integer.parseInt(code);
					} catch (Exception e) {

					}
				}
			}

			if (intCode != -1) {
				cri.add(Expression.eq("loss_code", new Integer(intCode)));
			}

			cri.addOrder(Order.asc("loss_code"));
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
	 * Retrieve all the iata loss codes
	 * 
	 * @param companyCode
	 * @param rowsperpage
	 * @param currpage
	 * @return list of codes null in case of exception
	 */
	public static List getIATACodes(int rowsperpage, int currpage) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(IATA_irregularity_code.class);
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
	 * Get the company specific irregularity codes based on locale
	 * 
	 * @param companyCode
	 * @param locale
	 * @return list of codes null in case of exception
	 */
	public static List getLocaleCompanyCodes(String companyCode, int report_type, String locale) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(Company_specific_irregularity_code.class);
			cri.createCriteria("company").add(Expression.eq("companyCode_ID", companyCode));
			if (report_type > 0) {
				cri.add(Expression.eq("report_type", new Integer(report_type)));
			}
			cri.add(Expression.eq("locale", locale));
			cri.addOrder(Order.asc("loss_code"));
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
	public static List getDistinctLocaleCompanyCodes(String companyCode, int report_type, String locale) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			String query = "select distinct co.loss_code,co.description from Company_specific_irregularity_code co "
					+ " where co.company.companyCode_ID = :companycode_ID and co.locale = :locale";
			if (report_type > 0)
				query += " and report_type = :report_type";
			query += " order by loss_code";
			Query q = sess.createQuery(query);
			q.setString("locale", locale);
			q.setString("companycode_ID", companyCode);
			if (report_type > 0)
				q.setInteger("report_type", report_type);

			List l = q.list();
			List l2 = new ArrayList();
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
		boolean ret = false;
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(IATA_irregularity_code.class).add(Expression.eq("locale", locale)).add(
					Expression.eq("loss_code", new Integer(loss_code)));
			cri.addOrder(Order.asc("loss_code"));
			List results = cri.list();

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
	public static List getShifts(String companyCode, String locale, int rowsperpage, int currpage) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(Work_Shift.class).add(Expression.eq("locale", locale));
			;
			cri.addOrder(Order.asc("shift_description"));
			cri.createCriteria("company").add(Expression.eq("companyCode_ID", companyCode));
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
	public static List getAirports(String companyCode, DynaValidatorForm dForm, int rowsperpage, int currpage) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(Airport.class).add(Expression.eq("companyCode_ID", companyCode));

			String acode = null;
			if (dForm != null)
				acode = (String) dForm.get("airportSearchName");

			if (acode != null && acode.length() > 0) {
				cri.add(Expression.like("airport_code", acode));
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
	public static List getStations(DynaValidatorForm form, String companyCode, int rowsperpage, int currpage) {
		return getCustomStations(form, companyCode, rowsperpage, currpage, TracingConstants.ActiveStatus.ACTIVE);
	}
	
	/**
	 * Get a list of all the stations within a company
	 * 
	 * @param companyCode
	 * @param rowsperpage
	 * @param currpage
	 * @return list of stations
	 */
	public static List getCustomStations(DynaValidatorForm form, String companyCode, int rowsperpage, int currpage, TracingConstants.ActiveStatus activeStatus) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(Station.class);
			cri.createCriteria("company").add(Expression.eq("companyCode_ID", companyCode));

			if (activeStatus.equals(TracingConstants.ActiveStatus.ACTIVE)) {
				cri.add(Expression.eq("active", true));
			} else if (activeStatus.equals(TracingConstants.ActiveStatus.INACTIVE)) {
				cri.add(Expression.eq("active", false));
			}

			String code = null;

			if (form != null) {
				code = (String) form.get("stationSearchName");
			}

			if (code != null && code.length() > 0) {
				cri.add(Expression.like("stationcode", code));
			}
			cri.addOrder(Order.asc("stationcode"));
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
	 * Get a list of all the delivery companies within a company
	 * 
	 * @param companyCode
	 * @param rowsperpage
	 * @param currpage
	 * @return list of stations
	 */
	public static List getDeliveryCompanies(MaintainDeliveryCompanyForm form, Company company, int rowsperpage, int currpage) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(DeliverCompany.class).add(Expression.eq("active", true));
			cri.add(Expression.eq("company", company));

			String companyName = null;

			if (form != null) {
				companyName = (String) form.getCompanySearchName();
			}

			if (companyName != null && companyName.length() > 0) {
				cri.add(Expression.like("name", companyName));
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
	 * Retrieve an delivery company based on its id
	 * 
	 * @param deliverCompany_ID
	 * @return delivery company; null if not found or exception
	 */
	public static DeliverCompany getDeliveryCompany(String deliverCompany_ID) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(DeliverCompany.class).add(Expression.eq("delivercompany_ID", new Integer(deliverCompany_ID)));
			return (DeliverCompany) cri.list().get(0);
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
			Criteria cri = sess.createCriteria(Agent.class).add(Expression.eq("agent_ID", new Integer(agentID)));
			return (Agent) cri.list().get(0);
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
			Criteria cri = sess.createCriteria(Agent.class).add(Expression.eq("username", username));
			cri.createCriteria("station").createCriteria("company").add(Expression.eq("companyCode_ID", companyCode));
			List ret = cri.list();
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
			Criteria cri = sess.createCriteria(Agent.class).add(Expression.eq("username", username));
			cri.createCriteria("station").createCriteria("company").add(Expression.eq("companyCode_ID", companyCode));
			List ret = cri.list();
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
	public static List getAgents(String companyCode, String sort, DynaValidatorForm dForm, int rowsperpage, int currpage) {
		return getCustomAgents(null, companyCode, sort, dForm, rowsperpage, currpage, TracingConstants.ActiveStatus.ACTIVE);
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
	public static List getAgentsByStation(String station_Id, String sort, DynaValidatorForm dForm, int rowsperpage, int currpage) {
		return getCustomAgents(station_Id, null, sort, dForm, rowsperpage, currpage, TracingConstants.ActiveStatus.ACTIVE);
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
	public static List getCustomAgents(String stationId, String companyCode, String sort, DynaValidatorForm dForm, int rowsperpage, int currpage, TracingConstants.ActiveStatus activeStatus) {
		Session sess = null;

		try {
			sess = HibernateWrapper.getSession().openSession();
			
			String username = null;
			String fname = null;
			String lname = null;
			String agentType = null;
			
			StringBuffer sql = new StringBuffer(512);
			sql.append("select distinct agent from com.bagnet.nettracer.tracing.db.Agent agent where 1=1 ");
			if (stationId != null && stationId.length() > 0)
				sql.append(" and agent.station.station_ID = :station_ID ");

			if (companyCode != null && companyCode.length() > 0)
				sql.append(" and agent.station.company.companyCode_ID = :companyCode_ID ");

			if (activeStatus.equals(TracingConstants.ActiveStatus.ACTIVE))
				sql.append(" and agent.active = 1 ");

			if (activeStatus.equals(TracingConstants.ActiveStatus.INACTIVE)) 
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
				if (sort.equalsIgnoreCase("lastname")) {
					sql.append(" order by agent.lastname asc ");
				} else {
					if (sort.equalsIgnoreCase("active")) {
						sql.append(" order by agent.active desc ");
					} else {
						if (sort.equalsIgnoreCase("username")) {
							sql.append(" order by agent.username asc ");
						} else {
							if (sort.equalsIgnoreCase("group")) {
								sql.append(" order by agent.group.description asc ");
							} else {
								if (sort.equalsIgnoreCase("station")) {
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
	 * Retrieves agents belonging to the group
	 * 
	 * @param groupId
	 * @param sort
	 * @param rowsperpage
	 * @param currpage
	 * @return
	 */
	public static List getAgentsByGroup(String groupId, String sort, int rowsperpage, int currpage) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			StringBuffer sql = new StringBuffer(512);
			sql.append("select distinct agent from com.bagnet.nettracer.tracing.db.Agent agent where 1=1 ");
			sql.append(" and agent.group.userGroup_ID = :userGroup_ID ");

			if (sort != null && sort.length() > 0) {
				if (sort.equalsIgnoreCase("lastname")) {
					sql.append(" order by agent.lastname asc ");
				} else {
					if (sort.equalsIgnoreCase("active")) {
						sql.append(" order by agent.active desc ");
					} else {
						if (sort.equalsIgnoreCase("username")) {
							sql.append(" order by agent.username asc ");
						} else {
							if (sort.equalsIgnoreCase("group")) {
								sql.append(" order by agent.group.description asc ");
							} else {
								if (sort.equalsIgnoreCase("station")) {
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
	public static List getGroups(DynaValidatorForm form, String companyCode, int rowsperpage, int currpage) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(UserGroup.class);
			cri.add(Expression.eq("companycode_ID", companyCode));
			String groupName = null;
			if (form != null) {
				groupName = (String) form.get("groupSearchName");
			}
			if (groupName != null && groupName.length() > 0) {
				cri.add(Expression.like("description", groupName));
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
	
	public static List getGroups(String companyCode) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(UserGroup.class);
			cri.add(Expression.eq("companycode_ID", companyCode));
			List list = cri.list();
			
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
	public static TreeMap getComponentTreeMap(String groupID, Agent user) {
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
				if (user.getStation().getCompany().getCompanyCode_ID().equals(TracingConstants.OWENS_GROUP)
						|| AdminUtils.checkIfPermissionExists(user.getGroup().getUserGroup_ID(), component.getComponent_ID())) {

					//do for parent component.
					boolean isChecked = true;
					//check if this component belongs to the current group.
					Criteria cri = sess.createCriteria(GroupComponentPolicy.class);
					cri.createCriteria("usergroup").add(Expression.eq("userGroup_ID", new Integer(groupID)));
					cri.createCriteria("component").add(Expression.eq("component_ID", new Integer(component.getComponent_ID())));
					List someList = cri.list();
					if (someList == null || someList.size() < 1)
						isChecked = false;
					int checked = isChecked ? 1 : 0;
					TreeMap parentMap = new TreeMap();
					permissionMap.put("" + component.getComponent_Name() + "#" + component.getComponent_ID() + "#" + checked, parentMap);

					//Go through the child components.
					List childList = getChildComponents(component.getComponent_ID());
					for (Iterator j = childList.iterator(); j.hasNext();) {
						SystemComponent component2 = (SystemComponent) j.next();
						if (user.getStation().getCompany().getCompanyCode_ID().equals(TracingConstants.OWENS_GROUP)
								|| AdminUtils.checkIfPermissionExists(user.getGroup().getUserGroup_ID(), component2.getComponent_ID())) {

							//do for parent component.
							boolean isChecked2 = true;
							//check if this component belongs to the current group.
							Criteria cri2 = sess.createCriteria(GroupComponentPolicy.class);
							cri2.createCriteria("usergroup").add(Expression.eq("userGroup_ID", new Integer(groupID)));
							cri2.createCriteria("component").add(Expression.eq("component_ID", new Integer(component2.getComponent_ID())));
							List someList2 = cri2.list();
							if (someList2 == null || someList2.size() < 1)
								isChecked2 = false;
							int checked2 = isChecked2 ? 1 : 0;

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
	public static void saveComponents(String groupID, int audit_group_id, HttpServletRequest request, Agent user) {
		Session sess = null;
		Transaction t = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			t = sess.beginTransaction();

			UserGroup u = getGroup(groupID);
			for (Iterator i = u.getComponentPolicies().iterator(); i.hasNext();) {
				GroupComponentPolicy gcp = (GroupComponentPolicy) i.next();
				sess.delete(gcp);
			}
			u.setComponentPolicies(new HashSet());

			//delete all the group relationships for this group Id.
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

					if (audit_group_id > 0) {
						if (user.getStation().getCompany().getVariable().getAudit_group() == 1) {
							Audit_GroupComponentPolicy plicy2 = new Audit_GroupComponentPolicy();
							BeanUtils.copyProperties(plicy2, policy);
							Audit_UserGroup au = new Audit_UserGroup();
							au.setUserGroup_ID(audit_group_id);
							plicy2.setAudit_usergroup(au);
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
}