package com.bagnet.nettracer.reporting;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.classic.Session;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.dto.StatReportDTO;
import com.bagnet.nettracer.tracing.utils.DateUtils;

public class CustomWestJetReports {

	private static final Logger logger = Logger
			.getLogger(CustomWestJetReports.class);

	@SuppressWarnings("rawtypes")
	public List getLossReportData(StatReportDTO srDTO, ResourceBundle resources) {
		ArrayList toReturn = null;
		Session session = null;

		String startDate = DateUtils.formatDate(srDTO.getStarttime(),
				srDTO.getDateFormat(), TracingConstants.DB_DATEFORMAT, null,
				null);
		String endDate = DateUtils.formatDate(srDTO.getEndtime(),
				srDTO.getDateFormat(), TracingConstants.DB_DATEFORMAT, null,
				null);

		String stationLimit1 = "";
		String stationLimit2 = "";
		
		String stationCode = srDTO.getStationCode();
		if (stationCode !=null) {
			stationCode = stationCode.trim();
			if  (stationCode.length() > 0) {
				stationCode = stationCode.substring(0,Math.min(3, stationCode.length())).toUpperCase();
			}
		}
			
		
		if (stationCode != null && !stationCode.equals("")) {
			stationLimit1 = " and legfrom = \'" + stationCode + "\'";
			stationLimit2 = " and legto = \'" + stationCode + "\'";
		}

		String sql = "select groupcol column1, count(distinct sumcol) column2 from"
				+ " (select distinct i.incident_id sumcol, legfrom groupcol"
				+ "   from itinerary it join incident i on it.incident_id = i.incident_id"
				+ "     join expensepayout ep on ep.incident_id = i.incident_id"
				+ "   where " + "   i.createdate >= \'"
				+ startDate
				+ "\' and i.createdate <= \'"
				+ endDate
				+ "\'"
				+ "   and itinerarytype = 0 "
				+ "   and legfrom is not null"
				+ "   and legfrom != ''"
				+ "   and i.itemtype_id = 1"
				+ "   and expensetype_id = 5"
				+ stationLimit1
				+ "   "
				+ "   "
				+ "   union "
				+ " select distinct i.incident_id sumcol, legto groupcol"
				+ "   from itinerary it join incident i on it.incident_id = i.incident_id"
				+ "     join expensepayout ep on ep.incident_id = i.incident_id"
				+ "   where "
				+ "   i.createdate >= \'"
				+ startDate
				+ "\' and i.createdate <= \'"
				+ endDate
				+ "\'"
				+ "   and itinerarytype = 0 "
				+ "   and legto is not null"
				+ "   and legto != ''"
				+ "   and i.itemtype_id = 1"
				+ stationLimit2
				+ "   and expensetype_id = 5"
				+ "   "
				+ " ) as tmptable"
				+ " group by groupcol"
				+ " order by groupcol asc";

		String sql2 = " select distinct iid column1 from ("
				+ " select i.incident_id iid"
				+ "   from itinerary it join incident i on it.incident_id = i.incident_id"
				+ "     join expensepayout ep on ep.incident_id = i.incident_id"
				+ "   where " + "   i.createdate >= \'"
				+ startDate
				+ "\' and i.createdate <= \'"
				+ endDate
				+ "\'"
				+ "   and itinerarytype = 0 "
				+ "   and legfrom is not null"
				+ "   and legfrom != ''"
				+ stationLimit1
				+ "   and i.itemtype_id = 1"
				+ "   and expensetype_id = 5"
				+ "   union "
				+ " select i.incident_id iid"
				+ "   from itinerary it join incident i on it.incident_id = i.incident_id"
				+ "     join expensepayout ep on ep.incident_id = i.incident_id"
				+ "   where "
				+ "   i.createdate >= \'"
				+ startDate
				+ "\' and i.createdate <= \'"
				+ endDate
				+ "\'"
				+ "   and itinerarytype = 0 "
				+ "   and legto is not null"
				+ "   and legto != ''"
				+ stationLimit2
				+ "   and i.itemtype_id = 1"
				+ "   and expensetype_id = 5"
				+ "   ) as grouped" + " order by iid";

		
		try {
			session = HibernateWrapper.getSession().openSession();
			SQLQuery query = session.createSQLQuery(sql);
			query.addScalar("column1", Hibernate.STRING);
			query.addScalar("column2", Hibernate.STRING);
			List results = query.list();
			
			if (results.isEmpty()) {
				return toReturn;
			}
			
			List results2 = null;
			if (stationCode != null && !stationCode.equals("")) {
				session = HibernateWrapper.getSession().openSession();
				SQLQuery query2 = session.createSQLQuery(sql2);
				query2.addScalar("column1", Hibernate.STRING);
				results2 = query2.list();

			}
			
			toReturn = getRowsFromResultList(results, results2, srDTO.getDateFormat());
			
			
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return toReturn;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private ArrayList getRowsFromResultList(List results, List results2, String dateFormat) {
		ArrayList toReturn = new ArrayList();
		SimpleReportRow reportRow;
		Object[] row;
		for (int i = 0; i < results.size(); ++i) {
			row = (Object[]) results.get(i);
			reportRow = new SimpleReportRow();

			reportRow.setColumn1((String) row[0]);
			reportRow.setColumn2((String) row[1]);
			if (row.length > 2) {
				reportRow.setColumn3((String) row[2]);
				if (row.length > 3) {
					reportRow.setColumn4((String) row[3]);
					if (row.length > 4) {
						reportRow.setColumn5((String) row[4]);
						if (row.length > 5) {
							reportRow.setColumn6((String) row[5]);
							if (row.length > 6) {
								reportRow.setColumn7((String) row[6]);
								if (row.length > 7) {
									reportRow.setColumn8((String) row[7]);
									if (row.length > 8) {
										reportRow.setColumn9((String) row[8]);
										if (row.length > 9) {
											reportRow.setColumn10((String) row[9]);
										}
									}
								}
							}
						}
					}
				}
			}

			toReturn.add(reportRow);
		}
		
		if (results2 != null) {
			reportRow = new SimpleReportRow();
			reportRow.setColumn1("");
			reportRow.setColumn2("");
			toReturn.add(reportRow);
			
			reportRow = new SimpleReportRow();
			reportRow.setColumn1("List of Files:");
			toReturn.add(reportRow);
			
			for (int i = 0; i < results2.size(); ++i) {
				reportRow = new SimpleReportRow();

				reportRow.setColumn1((String)results2.get(i));
				reportRow.setColumn2("");
				toReturn.add(reportRow);
			}
		}
		return toReturn;
	}

	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private ArrayList getRowsFromResultList2(List results, List results2, String dateFormat) {
		ArrayList toReturn = new ArrayList();
		SimpleReportRow2 reportRow;
		Object[] row;
		for (int i = 0; i < results.size(); ++i) {
			row = (Object[]) results.get(i);
			reportRow = new SimpleReportRow2();

			reportRow.setColumn1((String) row[0]);
			reportRow.setColumn2((Integer) row[1]);
			reportRow.setColumn3((Integer) row[2]);
			reportRow.setColumn4((Integer) row[3]);
			reportRow.setColumn5((Integer) row[4]);
			reportRow.setColumn6((Integer) row[5]);
			reportRow.setColumn7((Integer) row[6]);
			reportRow.setColumn8((Integer) row[7]);
			reportRow.setColumn9((Integer) row[8]);
			reportRow.setColumn10((Integer) row[9]);

			toReturn.add(reportRow);
		}
		
		return toReturn;
	}
	
	/**
	 * Method is used by multiple reports - do not edit without reviewing.
	 */
	public int getLossReportTotalPirs(StatReportDTO srDTO) {
		String startDate = DateUtils.formatDate(srDTO.getStarttime(),
				srDTO.getDateFormat(), TracingConstants.DB_DATEFORMAT, null,
				null);
		String endDate = DateUtils.formatDate(srDTO.getEndtime(),
				srDTO.getDateFormat(), TracingConstants.DB_DATEFORMAT, null,
				null);

		String sql = "select count(i.incident_id) total from incident i where"
				+ " i.itemtype_id = 1" + " and i.createdate >= \'" + startDate
				+ "\' and i.createdate <= \'" + endDate + "\'";

		Session session = null;
		try {
			session = HibernateWrapper.getSession().openSession();
			SQLQuery query = session.createSQLQuery(sql);
			query.addScalar("total", Hibernate.LONG);
			List results = query.list();
			return ((Long) results.get(0)).intValue();

		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return 0;
	}

	public int getLossReportLosses(StatReportDTO srDTO) {
		String startDate = DateUtils.formatDate(srDTO.getStarttime(),
				srDTO.getDateFormat(), TracingConstants.DB_DATEFORMAT, null,
				null);
		String endDate = DateUtils.formatDate(srDTO.getEndtime(),
				srDTO.getDateFormat(), TracingConstants.DB_DATEFORMAT, null,
				null);

		String sql = "select count(distinct i.incident_id) total from expensepayout ep"
				+ " join incident i on ep.incident_id = i.incident_id where"
				+ " i.itemtype_id = 1"
				+ " and i.createdate >= \'"
				+ startDate
				+ "\' and i.createdate <= \'"
				+ endDate
				+ "\'"
				+ " and expensetype_id = 5";

		Session session = null;
		try {
			session = HibernateWrapper.getSession().openSession();
			SQLQuery query = session.createSQLQuery(sql);
			query.addScalar("total", Hibernate.LONG);
			List results = query.list();
			return ((Long) results.get(0)).intValue();

		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return 0;
	}
	
	public int getTotalPilferage(StatReportDTO srDTO) {
		String startDate = DateUtils.formatDate(srDTO.getStarttime(),
				srDTO.getDateFormat(), TracingConstants.DB_DATEFORMAT, null,
				null);
		String endDate = DateUtils.formatDate(srDTO.getEndtime(),
				srDTO.getDateFormat(), TracingConstants.DB_DATEFORMAT, null,
				null);

		String sql = "select count(i.incident_id) total from incident i where"
				+ " i.itemtype_id = 2" + " and i.createdate >= \'" + startDate
				+ "\' and i.createdate <= \'" + endDate + "\'";

		Session session = null;
		try {
			session = HibernateWrapper.getSession().openSession();
			SQLQuery query = session.createSQLQuery(sql);
			query.addScalar("total", Hibernate.LONG);
			List results = query.list();
			return ((Long) results.get(0)).intValue();

		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return 0;
	}

	
	// TODO: HERE
	
	@SuppressWarnings("rawtypes")
	public List getPilferageReportData(StatReportDTO srDTO, ResourceBundle resources) {
		ArrayList toReturn = null;
		Session session = null;

		String startDate = DateUtils.formatDate(srDTO.getStarttime(),
				srDTO.getDateFormat(), TracingConstants.DB_DATEFORMAT, null,
				null);
		String endDate = DateUtils.formatDate(srDTO.getEndtime(),
				srDTO.getDateFormat(), TracingConstants.DB_DATEFORMAT, null,
				null);

		String stationLimit1 = "";
		String stationLimit2 = "";
		
		String stationCode = srDTO.getStationCode();
		if (stationCode !=null) {
			stationCode = stationCode.trim();
			if  (stationCode.length() > 0) {
				stationCode = stationCode.substring(0,Math.min(3, stationCode.length())).toUpperCase();
			}
		}
			
		
		if (stationCode != null && !stationCode.equals("")) {
			stationLimit1 = " and legfrom = \'" + stationCode + "\'";
			stationLimit2 = " and legto = \'" + stationCode + "\'";
		}

		String sql = " select groupcol column1, count(distinct sumcol) column2 from"
			+ " (select  distinct i.incident_id sumcol, legfrom groupcol"
			+ "   from itinerary it join incident i on it.incident_id = i.incident_id"
			+ "   where " + "   i.createdate >= \'"
			+ startDate
			+ "\' and i.createdate <= \'"
			+ endDate
			+ "\'"
			+ "   and itinerarytype = 0 "
			+ "   and legfrom is not null"
			+ "   and legfrom != ''"
			+ stationLimit1
			+ "   and i.itemtype_id = 2"
			+ "   "
			+ "   "
			+ "   union "
			+ " select distinct i.incident_id sumcol, legto groupcol"
			+ "   from itinerary it join incident i on it.incident_id = i.incident_id"
			+ "   where " + "   i.createdate >= \'"
			+ startDate
			+ "\' and i.createdate <= \'"
			+ endDate
			+ "\'"
			+ "   and itinerarytype = 0 "
			+ "   and legto is not null"
			+ "   and legto != ''"
			+ stationLimit2
			+ "   and i.itemtype_id = 2"
			+ "   "
			+ " ) as tmptable"
			+ " group by groupcol"
			+ " order by groupcol asc ";

		String sql2 = " select distinct iid column1 from ("
			+ " select i.incident_id iid"
			+ "   from itinerary it join incident i on it.incident_id = i.incident_id"
			+ "   where " + "   i.createdate >= \'"
			+ startDate
			+ "\' and i.createdate <= \'"
			+ endDate
			+ "\'"
			+ "   and itinerarytype = 0 "
			+ "   and legfrom is not null"
			+ "   and legfrom != ''"
			+ stationLimit1
			+ "   and i.itemtype_id = 2"
			+ "   union "
			+ " select i.incident_id iid"
			+ "   from itinerary it join incident i on it.incident_id = i.incident_id"
			+ "   where " + "   i.createdate >= \'"
			+ startDate
			+ "\' and i.createdate <= \'"
			+ endDate
			+ "\'"
			+ "   and itinerarytype = 0 "
			+ "   and legto is not null"
			+ "   and legto != ''"
			+ stationLimit2
			+ "   and i.itemtype_id = 2"
			+ "   ) as grouped"
			+ " order by iid"; 
			
			
			

		
		try {
			session = HibernateWrapper.getSession().openSession();
			SQLQuery query = session.createSQLQuery(sql);
			query.addScalar("column1", Hibernate.STRING);
			query.addScalar("column2", Hibernate.STRING);
			List results = query.list();
			
			if (results.isEmpty()) {
				return toReturn;
			}
			
			List results2 = null;
			if (stationCode != null && !stationCode.equals("")) {
				session = HibernateWrapper.getSession().openSession();
				SQLQuery query2 = session.createSQLQuery(sql2);
				query2.addScalar("column1", Hibernate.STRING);
				results2 = query2.list();

			}
			
			toReturn = getRowsFromResultList(results, results2, srDTO.getDateFormat());
			
			
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return toReturn;
	}

	
	@SuppressWarnings("rawtypes")
	public List getDamageReportData(StatReportDTO srDTO, ResourceBundle resources) {
		
		ArrayList toReturn = null;
		Session session = null;

		String startDate = DateUtils.formatDate(srDTO.getStarttime(),
				srDTO.getDateFormat(), TracingConstants.DB_DATEFORMAT, null,
				null);
		String endDate = DateUtils.formatDate(srDTO.getEndtime(),
				srDTO.getDateFormat(), TracingConstants.DB_DATEFORMAT, null,
				null);

		String stationLimit1 = "";
		
		String stationCode = srDTO.getStationCode();
		
		if (stationCode.equals("0")) {
			stationCode = null;
		}
		
		if (stationCode !=null) {
			stationCode = stationCode.trim();
			if  (stationCode.length() > 0) {
				stationCode = stationCode.substring(0,Math.min(3, stationCode.length())).toUpperCase();
			}
		}
			
		
		if (stationCode != null && !stationCode.equals("")) {
			stationLimit1 = " and s.stationcode = \'" + stationCode + "\' ";
		}

		String sql = " select s.stationcode column1, count(distinct i.incident_id) column2, damagerepair column3, damagereplace column4"
			+ "   from incident i join station s on s.station_id = i.stationcreated_ID"
			+ "      left outer join "
			+ "       ("
			+ "         select s.stationcode as st, count(distinct i.incident_id) as damagereplace"
			+ "           from incident i join station s on s.station_id = i.stationcreated_ID"
			+ "             left outer join expensepayout ep on ep.incident_id = i.incident_id"
			+ "   where " + "   i.createdate >= \'"
			+ startDate
			+ "\' and i.createdate <= \'"
			+ endDate
			+ "\'"
			+ "             and expensetype_id = 1"
			+ "           group by s.stationcode"
			+ "         "
			+ "       )"
			+ "     as inner1 on inner1.st = s.stationcode"
			+ "     left outer join "
			+ "       ("
			+ "         select s.stationcode as st, count(distinct i.incident_id) as damagerepair"
			+ "           from incident i join station s on s.station_id = i.stationcreated_ID"
			+ "             left outer join expensepayout ep on ep.incident_id = i.incident_id"
			+ "   where " + "   i.createdate >= \'"
			+ startDate
			+ "\' and i.createdate <= \'"
			+ endDate
			+ "\'"
			+ "             and i.itemtype_id = 3"
			+ "             and expensetype_id = 9"
			+ "           group by s.stationcode"
			+ "         "
			+ "       )"
			+ "     as inner2 on inner2.st = s.stationcode"
			+ "   where " + "   i.createdate >= \'"
			+ startDate
			+ "\' and i.createdate <= \'"
			+ endDate
			+ stationLimit1
			+ "\'"
			+ "   and i.itemtype_id = 3"
			+ "   group by s.stationcode";
			
			
			

		
		try {
			session = HibernateWrapper.getSession().openSession();
			SQLQuery query = session.createSQLQuery(sql);
			query.addScalar("column1", Hibernate.STRING);
			query.addScalar("column2", Hibernate.STRING);
			query.addScalar("column3", Hibernate.STRING);
			query.addScalar("column4", Hibernate.STRING);
			List results = query.list();
			
			if (results.isEmpty()) {
				return toReturn;
			}
			
			
			toReturn = getRowsFromResultList(results, null, srDTO.getDateFormat());
			
			
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return toReturn;
	}
	
	public int getTotalOALPirs(StatReportDTO srDTO) {
		String startDate = DateUtils.formatDate(srDTO.getStarttime(),
				srDTO.getDateFormat(), TracingConstants.DB_DATEFORMAT, null,
				null);
		String endDate = DateUtils.formatDate(srDTO.getEndtime(),
				srDTO.getDateFormat(), TracingConstants.DB_DATEFORMAT, null,
				null);
		
		String sql = " select count(distinct i.incident_id) total"
				+ "   from incident i join incident_claimcheck ic on i.incident_id = ic.incident_id"
				+ "   where "
				+ " i.itemtype_id = 1"
				+ " and i.createdate >= \'"
				+ startDate
				+ "\' and i.createdate <= \'"
				+ endDate
				+ "\'"
				+ "   and claimchecknum not like '_838%' and claimchecknum not like 'WS%'"
				+ "  ;" + " ";

		Session session = null;
		try {
			session = HibernateWrapper.getSession().openSession();
			SQLQuery query = session.createSQLQuery(sql);
			query.addScalar("total", Hibernate.LONG);
			List results = query.list();
			return ((Long) results.get(0)).intValue();

		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return 0;
	}

	@SuppressWarnings("rawtypes")
	public List getOtherAirlineReportData(StatReportDTO srDTO, ResourceBundle resources) {
		
		ArrayList toReturn = null;
		Session session = null;

		String startDate = DateUtils.formatDate(srDTO.getStarttime(),
				srDTO.getDateFormat(), TracingConstants.DB_DATEFORMAT, null,
				null);
		String endDate = DateUtils.formatDate(srDTO.getEndtime(),
				srDTO.getDateFormat(), TracingConstants.DB_DATEFORMAT, null,
				null);

		String airlineLimit = "";
		
		String airlineCode = srDTO.getStationCode();
		
		if (airlineCode.equals("0")) {
			airlineCode = null;
		}
		
		if (airlineCode !=null) {
			airlineCode = airlineCode.trim();
			if  (airlineCode.length() > 0) {
				airlineCode = airlineCode.substring(0,Math.min(2, airlineCode.length())).toUpperCase();
			}
		}
			
		
		if (airlineCode != null && !airlineCode.equals("")) {
			airlineLimit = " where a1.airlines = '" + airlineCode + "'";
		}


		String sql = " select a1.airlines column1, count(a1.airlines) column2 from (select distinct i.incident_id,"
			+ "   IF (la.Airline_2_Character_Code is not null, la.Airline_2_Character_Code, substring(claimchecknum,1,2)) airlines"
			+ "   from incident i "
			+ "     join incident_claimcheck ic on i.incident_id = ic.incident_id"
			+ "     left outer join lookup_airline_codes la on substring(claimchecknum, 2,3) = la.Airline_3_Digit_Ticketing_Code"
			+ "   where "
			+ "   i.createdate >= \'"
			+ startDate
			+ "\' and i.createdate <= \'"
			+ endDate
			+ "\'"
			+ "   and i.itemtype_id = 1"
			+ "   and claimchecknum not like '_838%' and claimchecknum not like 'WS%') as a1"
			+ airlineLimit
			+ "   group by a1.airlines"
			+ "   order by column1 asc";

		String sql2 = " select a1.incident column1 from (select distinct i.incident_id incident,"
			+ "   IF (la.Airline_2_Character_Code is not null, la.Airline_2_Character_Code, substring(claimchecknum,1,2)) airlines"
			+ "   from incident i "
			+ "     join incident_claimcheck ic on i.incident_id = ic.incident_id"
			+ "     left outer join lookup_airline_codes la on substring(claimchecknum, 2,3) = la.Airline_3_Digit_Ticketing_Code"
			+ "   where "
			+ "   i.createdate >= \'"
			+ startDate
			+ "\' and i.createdate <= \'"
			+ endDate
			+ "\'"
			+ "   and i.itemtype_id = 1"
			+ "   and claimchecknum not like '_838%' and claimchecknum not like 'WS%'"
			+ "   ) as a1"
			+ "   where"
			+ "   a1.airlines like '" + airlineCode + "'"
			+ "   order by column1 asc";
			

		
		try {
			session = HibernateWrapper.getSession().openSession();
			SQLQuery query = session.createSQLQuery(sql);
			query.addScalar("column1", Hibernate.STRING);
			query.addScalar("column2", Hibernate.STRING);
			List results = query.list();
			
			if (results.isEmpty()) {
				return toReturn;
			}
			
			List results2 = null;
			if (airlineCode != null && !airlineCode.equals("")) {
				session = HibernateWrapper.getSession().openSession();
				SQLQuery query2 = session.createSQLQuery(sql2);
				query2.addScalar("column1", Hibernate.STRING);
				results2 = query2.list();

			}
			
			toReturn = getRowsFromResultList(results, results2, srDTO.getDateFormat());
			
			
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return toReturn;
	}

	@SuppressWarnings("rawtypes")
	public List getDelayedReportData(StatReportDTO srDTO, ResourceBundle resources) {
		
		ArrayList toReturn = null;
		Session session = null;

		String startDate = DateUtils.formatDate(srDTO.getStarttime(),
				srDTO.getDateFormat(), TracingConstants.DB_DATEFORMAT, null,
				null);
		String endDate = DateUtils.formatDate(srDTO.getEndtime(),
				srDTO.getDateFormat(), TracingConstants.DB_DATEFORMAT, null,
				null);

		String stationLimit1 = "";
		
		String stationCode = srDTO.getStationCode();
		
		if (stationCode.equals("0")) {
			stationCode = null;
		}
		
		if (stationCode !=null) {
			stationCode = stationCode.trim();
			if  (stationCode.length() > 0) {
				stationCode = stationCode.substring(0,Math.min(3, stationCode.length())).toUpperCase();
			}
		}
			
		
		if (stationCode != null && !stationCode.equals("")) {
			stationLimit1 = " and s.stationcode = \'" + stationCode + "\' ";
		}
		
		String dateLimit = " i.createdate >= \'" + startDate
				+ "\' and i.createdate <= \'" + endDate + "\' ";

		String sql = " select s.stationcode column1, count(distinct i.incident_id) column2, closedPirs column3, hours24 column4, hours48 column5, days5 column6, days21 column7, daysOver21 column8," +
				" case when pirsBdos is null then 0 else pirsBdos end column9, " +
				" closedPirs-case when pirsBdos is null then 0 else pirsBdos end column10 "
			+ "   from incident i join station s on s.station_id = i.stationcreated_ID"
			+ "      left outer join "
			+ "       ("
			+ "         select s.stationcode as st, count(distinct i.incident_id) as closedPirs"
			+ "           from incident i join station s on s.station_id = i.stationcreated_ID"
			+ "           where "
			+ dateLimit
			+ "             and i.itemtype_id = 1"
			+ "             and i.status_id = 13"
			+ "           group by s.stationcode"
			+ "       ) as inner1 on inner1.st = s.stationcode"
			+ "     left outer join "
			+ "       ("
			+ "         select s.stationcode as st, count(distinct i.incident_id) as hours24"
			+ "           from incident i join station s on s.station_id = i.stationcreated_ID"
			+ "           where "
			+ dateLimit
			+ "             and i.close_date < DATE_ADD(ADDTIME(i.createdate, i.createtime), INTERVAL 24 HOUR)"
			+ "             and i.itemtype_id = 1"
			+ "             and i.status_id = 13"
			+ "           group by s.stationcode"
			+ "       )"
			+ "     as inner2 on inner2.st = s.stationcode"
			+ "     left outer join "
			+ "       ("
			+ "         select s.stationcode as st, count(distinct i.incident_id) as hours48"
			+ "           from incident i join station s on s.station_id = i.stationcreated_ID"
			+ "           where "
			+ dateLimit
			+ "             and i.close_date >= DATE_ADD(ADDTIME(i.createdate, i.createtime), INTERVAL 24 HOUR)"
			+ "             and i.close_date < DATE_ADD(ADDTIME(i.createdate, i.createtime), INTERVAL 48 HOUR)"
			+ "             and i.itemtype_id = 1"
			+ "             and i.status_id = 13"
			+ "           group by s.stationcode"
			+ "       )"
			+ "     as inner3 on inner3.st = s.stationcode"
			+ "         left outer join "
			+ "       ("
			+ "         select s.stationcode as st, count(distinct i.incident_id) as days5"
			+ "           from incident i join station s on s.station_id = i.stationcreated_ID"
			+ "           where "
			+ dateLimit
			+ "             and i.close_date >= DATE_ADD(ADDTIME(i.createdate, i.createtime), INTERVAL 48 HOUR)"
			+ "             and i.close_date < DATE_ADD(ADDTIME(i.createdate, i.createtime), INTERVAL 120 HOUR)"
			+ "             and i.itemtype_id = 1"
			+ "             and i.status_id = 13"
			+ "           group by s.stationcode"
			+ "       )"
			+ "     as inner4 on inner4.st = s.stationcode"
			+ "             left outer join "
			+ "       ("
			+ "         select s.stationcode as st, count(distinct i.incident_id) as days21"
			+ "           from incident i join station s on s.station_id = i.stationcreated_ID"
			+ "           where "
			+ dateLimit
			+ "             and i.close_date >= DATE_ADD(ADDTIME(i.createdate, i.createtime), INTERVAL 120 HOUR)"
			+ "             and i.close_date < DATE_ADD(ADDTIME(i.createdate, i.createtime), INTERVAL 504 HOUR)"
			+ "             and i.itemtype_id = 1"
			+ "             and i.status_id = 13"
			+ "           group by s.stationcode"
			+ "       )"
			+ "     as inner5 on inner5.st = s.stationcode"
			+ "             left outer join "
			+ "       ("
			+ "         select s.stationcode as st, count(distinct i.incident_id) as daysOver21"
			+ "           from incident i join station s on s.station_id = i.stationcreated_ID"
			+ "           where "
			+ dateLimit
			+ "             and i.close_date >= DATE_ADD(ADDTIME(i.createdate, i.createtime), INTERVAL 504 HOUR)"
			+ "             and i.itemtype_id = 1"
			+ "             and i.status_id = 13"
			+ "           group by s.stationcode"
			+ "       )"
			+ "     as inner6 on inner6.st = s.stationcode"
			+ "             left outer join "
			+ "       ("
			+ "         select s.stationcode as st, count(distinct i.incident_id) as pirsBdos"
			+ "           from incident i join station s on s.station_id = i.stationcreated_ID"
			+ "             join bdo b on b.incident_id = i.incident_id"
			+ "           where "
			+ dateLimit
			+ "             and i.itemtype_id = 1"
			+ "           group by s.stationcode"
			+ "       )"
			+ "     as inner7 on inner7.st = s.stationcode"
			+ "   where "
			+ dateLimit
			+ "   and i.itemtype_id = 1"
			+ stationLimit1
			+ "   group by s.stationcode";
			
			
			

		
		try {
			session = HibernateWrapper.getSession().openSession();
			SQLQuery query = session.createSQLQuery(sql);
			query.addScalar("column1", Hibernate.STRING);
			query.addScalar("column2", Hibernate.INTEGER);
			query.addScalar("column3", Hibernate.INTEGER);
			query.addScalar("column4", Hibernate.INTEGER);
			query.addScalar("column5", Hibernate.INTEGER);
			query.addScalar("column6", Hibernate.INTEGER);
			query.addScalar("column7", Hibernate.INTEGER);
			query.addScalar("column8", Hibernate.INTEGER);
			query.addScalar("column9", Hibernate.INTEGER);
			query.addScalar("column10", Hibernate.INTEGER);
			List results = query.list();
			
			if (results.isEmpty()) {
				return toReturn;
			}
			
			
			toReturn = getRowsFromResultList2(results, null, srDTO.getDateFormat());
			
			
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return toReturn;
	}
	
}
