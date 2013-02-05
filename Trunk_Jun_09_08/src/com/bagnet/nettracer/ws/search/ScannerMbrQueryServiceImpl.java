package com.bagnet.nettracer.ws.search;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
//import org.hibernate.classic.Session;
import org.hibernate.Session;
import org.hibernate.type.StandardBasicTypes;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.StringUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.ws.search.QueryNetTracerDocument.QueryNetTracer;
import com.bagnet.nettracer.ws.search.QueryNetTracerResponseDocument.QueryNetTracerResponse;
import com.bagnet.nettracer.ws.search.pojo.xsd.Match;

public class ScannerMbrQueryServiceImpl extends ScannerMbrQueryServiceSkeleton {
	
	private static final Logger logger = Logger
			.getLogger(ScannerMbrQueryServiceImpl.class);
	
	private static final int INCIDENT_ID = 0;
	private static final int TYPE_ID = 1;
	private static final int TYPE = 2;
	private static final int CREATEDATE = 3;
	private static final int CLAIMCHECK_C = 4;
	private static final int CLAIMCHECK_ITM = 5;
	private static final int PNR = 6;
	private static final int LD_TYPE = 1;
	private static final int MISS_TYPE = 2;
	private static final int DAM_TYPE = 3;
    
   /**
    * Auto generated method signature
    * 
    * @param queryNetTracer
    */
   public com.bagnet.nettracer.ws.search.QueryNetTracerResponseDocument queryNetTracer(com.bagnet.nettracer.ws.search.QueryNetTracerDocument queryNetTracer) {
	   int daysBack = PropertyBMO.getValueAsInt(PropertyBMO.SCANQUERY_DAYS_BACK);
	   if (daysBack == 0) {
		   daysBack = 4;
	   }
	   return queryNetTracer(queryNetTracer, daysBack);
   }
   
   public com.bagnet.nettracer.ws.search.QueryNetTracerResponseDocument queryNetTracer(com.bagnet.nettracer.ws.search.QueryNetTracerDocument queryNetTracer, int subDays) {
		QueryNetTracer query = queryNetTracer.getQueryNetTracer();
		String[] tags = query.getTagNumberArray();
		String[] pnrs = query.getPnrArray();
		
		QueryNetTracerResponseDocument resDoc = QueryNetTracerResponseDocument.Factory.newInstance();
		QueryNetTracerResponse queryRes = resDoc.addNewQueryNetTracerResponse();
		
		Long startTime = new Date().getTime();
		
		querySystem(tags, pnrs, queryRes, subDays);
		
		Long timeDiff = new Date().getTime() - startTime;
		
		com.bagnet.nettracer.tracing.utils.general.Logger.logScanQuery("", "SCAN QUERY WS CALLED", timeDiff);
		
		logger.info("RESPONSE: " + resDoc.toString());
		
		return resDoc;
    }
    
    @SuppressWarnings("rawtypes")
	public void querySystem(String[] tags, String[] pnrs, QueryNetTracerResponse queryRes, int subDays) {
    	
    	if ((tags == null || tags.length == 0) && (pnrs == null || pnrs.length == 0)) {
    		return;
    	}
    	
    	Date now = new Date();
    	Date before = DateUtils.addDays(now, (-1 * subDays));

    	logger.info("TIME " + subDays + ": \n\n" + now + "\n" + before + "\n\nEND TIME\n");
    	
    	String addTime = "ADDTIME(i.createdate, i.createtime)";
    	String subTime = DateUtils.formatDate(before, TracingConstants.getDBDateFormat(HibernateWrapper.getConfig().getProperties()), null, null); 
    	
    	boolean sqlServer = "org.hibernate.dialect.SQLServerDialect".equals(HibernateWrapper.getConfig().getProperties().getProperty("hibernate.dialect"));
    	
    	if (sqlServer) {
    		addTime = "DATEADD(second, ((DATEPART(hour, i.createtime) * 3600) + (DATEPART(minute, i.createtime) * 60) + DATEPART(second, i.createtime)), i.createdate)";
    	}
    	
    	String where = " where :subTime <= i.createdate and (1=0 ";

		HashMap<String, ArrayList<String>> eightDig = new HashMap<String, ArrayList<String>>();
		HashMap<String, ArrayList<String>> nineDig = new HashMap<String, ArrayList<String>>();
		HashMap<String, ArrayList<String>> tenDig = new HashMap<String, ArrayList<String>>();
		
    	String pnrSelect = generatePNR(pnrs, where);
    	String tagSelect = generateTAG(tags, where, eightDig, nineDig, tenDig);
    	String unionSelect = "null";
    	if (pnrSelect != null && pnrSelect.length() > 0) {
    		unionSelect = pnrSelect;
    		if (tagSelect != null && tagSelect.length() > 0) {
    			unionSelect += " UNION " + tagSelect;
    		}
    	} else if (tagSelect != null && tagSelect.length() > 0) {
    		unionSelect = tagSelect;
    	}
    	
    	String query = "select i.Incident_ID as id, i.itemtype_ID as type_id, 'UNKNOWN' as type, " + addTime + " as createdatetime, "
	    		+ "c.claimchecknum as c_check, itm.claimchecknum as itm_check, i.recordlocator as pnr from incident i "
    			+ "left outer join incident_claimcheck c on i.Incident_ID = c.incident_ID "
    			+ "left outer join item itm on i.Incident_ID = itm.incident_ID "
    			+ "where i.Incident_ID in ( " + unionSelect + " ) " + "order by i.Incident_ID";
    	
    	logger.info("QUERY: \n\n" + query + "\n\nEND QUERY\n");
    	
    	List results = getQueryResults(query, subTime, pnrs, eightDig, nineDig, tenDig);
    	
    	if (results.isEmpty()) {
    		return;
    	}
    	
    	ArrayList<Match> matches = getMatchesFromResultList(results, pnrs, tags);
    	Match[] ret = null;
    	if (!matches.isEmpty()) {
	    	ret = new Match[matches.size()];
	    	for (int i = 0; i < matches.size(); i++) {
	    		ret[i] = matches.get(i);
	    	}
    	}
    	queryRes.setReturnArray(ret);    	
    }
    
    private String generatePNR(String[] pnrs, String where) {
    	String returnMe = null; 
    	if (pnrs != null && pnrs.length > 0) {
    		returnMe = "select i.Incident_ID from incident i " + where + " or (i.recordlocator in (:pnrList)) )";
    	}
    	return returnMe;
    }
    
    private String generateTAG(String[] tags, String where, HashMap<String, ArrayList<String>> eightDig, HashMap<String, 
    		ArrayList<String>> nineDig, HashMap<String, ArrayList<String>> tenDig) {
    	String returnMe = null;
    	if (tags != null && tags.length > 0) {
    		for (String tag: tags) {
    			if (tag != null && tag.length() == 8) {
    				String key = tag.substring(0, 2);
    				if (eightDig.containsKey(key)) {
    					eightDig.get(key).add(tag.substring(2));
    				} else {
    					ArrayList<String> value = new ArrayList<String>();
    					value.add(tag.substring(2));
    					eightDig.put(key, value);
    				}
    			} else if (tag != null && tag.length() == 9) {
    				String key = tag.substring(0, 3);
    				if (nineDig.containsKey(key)) {
    					nineDig.get(key).add(tag.substring(3));
    				} else {
    					ArrayList<String> value = new ArrayList<String>();
    					value.add(tag.substring(3));
    					nineDig.put(key, value);
    				}
    			} else if (tag != null && tag.length() == 10) {
    				String key = tag.substring(0, 4);
    				if (tenDig.containsKey(key)) {
    					tenDig.get(key).add(tag.substring(4));
    				} else {
    					ArrayList<String> value = new ArrayList<String>();
    					value.add(tag.substring(4));
    					tenDig.put(key, value);
    				}
    			}
    		}
    		if (!eightDig.isEmpty() || !nineDig.isEmpty() || !tenDig.isEmpty()) {
        		String icSelect = "select i.Incident_ID from incident i "
        				+ "left outer join incident_claimcheck c on i.Incident_ID = c.incident_ID " + where;
        		String itemSelect = "select i.Incident_ID from incident i "
        				+ "left outer join item itm on i.Incident_ID = itm.incident_ID " + where;
	    		for (String key: eightDig.keySet()) {
	    			String temp = " or (%TABLE%.claimchecknum_carriercode = :key8" + key + " and %TABLE%.claimchecknum_bagnumber in (:key8" + key + "List)) ";
	        		itemSelect += temp.replaceAll("%TABLE%", "itm");
	        		icSelect += temp.replaceAll("%TABLE%", "c");
	    		}
	    		for (String key: nineDig.keySet()) {
	    			String temp = " or (%TABLE%.claimchecknum_ticketingcode = :key9" + key + " and %TABLE%.claimchecknum_bagnumber in (:key9" + key + "List)) ";
	        		itemSelect += temp.replaceAll("%TABLE%", "itm");
	        		icSelect += temp.replaceAll("%TABLE%", "c");
	    		}
	    		for (String key: tenDig.keySet()) {
	    			String temp = " or ((%TABLE%.claimchecknum_leading = :key10" + key + "One or %TABLE%.claimchecknum_leading is null) "
	    					+ "and %TABLE%.claimchecknum_ticketingcode = :key10" + key + "Two and %TABLE%.claimchecknum_bagnumber in (:key10" + key + "List)) ";
	        		itemSelect += temp.replaceAll("%TABLE%", "itm");
	        		icSelect += temp.replaceAll("%TABLE%", "c");
	    		}
	    		returnMe = icSelect + " ) UNION " + itemSelect + " ) ";
    		}
    	}
    	return returnMe;
    }
    
	@SuppressWarnings("rawtypes")
	public List getQueryResults(String sql, String subTime, String[] pnrs, HashMap<String, ArrayList<String>> eightDig, HashMap<String, 
    		ArrayList<String>> nineDig, HashMap<String, ArrayList<String>> tenDig) {
		Session session = null;
		try {
			session = HibernateWrapper.getDirtySession().openSession();
			SQLQuery query = session.createSQLQuery(sql);
			
			//Set Parameters
			query.setString("subTime", subTime);
	    	if (pnrs != null && pnrs.length > 0) {
	    		query.setParameterList("pnrList", pnrs);
	    	}
    		if (!eightDig.isEmpty() || !nineDig.isEmpty() || !tenDig.isEmpty()) {
	    		for (String key: eightDig.keySet()) {
	    			query.setString("key8" + key, key);
	    			query.setParameterList("key8" + key + "List", eightDig.get(key));
	    		}
	    		for (String key: nineDig.keySet()) {
	    			query.setString("key9" + key, key);
	    			query.setParameterList("key9" + key + "List", nineDig.get(key));
	    		}
	    		for (String key: tenDig.keySet()) {
	    			query.setString("key10" + key + "One", key.substring(0, 1));
	    			query.setString("key10" + key + "Two", key.substring(1));
	    			query.setParameterList("key10" + key + "List", tenDig.get(key));
	    		}
    		}

    		//Set Returns
			query.addScalar("id", StandardBasicTypes.STRING);
			query.addScalar("type_id", StandardBasicTypes.INTEGER);
			query.addScalar("type", StandardBasicTypes.STRING);
			query.addScalar("createdatetime", StandardBasicTypes.TIMESTAMP);
			query.addScalar("c_check", StandardBasicTypes.STRING);
			query.addScalar("itm_check", StandardBasicTypes.STRING);
			query.addScalar("pnr", StandardBasicTypes.STRING);

			return query.list();
			
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return null;
	}
	
	@SuppressWarnings({ "rawtypes" })
	private ArrayList<Match> getMatchesFromResultList(List results, String[] pnrs, String[] tags) {
		HashMap<String, Match> matches = new HashMap<String, Match>();
		Match match;
		Object[] row;
		for (int i = 0; i < results.size(); ++i) {
			row = (Object[]) results.get(i);
			
			if (row != null) {
				String incident = (String) row[INCIDENT_ID];
				int type_id = (Integer) row[TYPE_ID];
				String type = (String) row[TYPE];
				Calendar create = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
				Date createDate = (Date) row[CREATEDATE];
				Calendar tzFix = Calendar.getInstance();
				tzFix.setTime(createDate);
				create.set(tzFix.get(Calendar.YEAR), tzFix.get(Calendar.MONTH), tzFix.get(Calendar.DAY_OF_MONTH), 
						tzFix.get(Calendar.HOUR_OF_DAY), tzFix.get(Calendar.MINUTE), tzFix.get(Calendar.SECOND));
				String c_check = (String) row[CLAIMCHECK_C];
				String itm_check = (String) row[CLAIMCHECK_ITM];
				String pnr = (String) row[PNR];
				String claimcheck = itm_check;
				if (LD_TYPE == type_id) {
					claimcheck = c_check;
					type = "Delayed";
				} else if (MISS_TYPE == type_id) {
					type = "Pilfered";
				} else if (DAM_TYPE == type_id) {
					type = "Damaged";
				}
				
				if (matches.containsKey(incident) && claimcheck != null && claimcheck.trim().length() > 0) {
					match = matches.get(incident);
					String[] oldTags = match.getAllTagsInIncidentArray();
					int oldSize = oldTags.length;
					String[] allTags = new String[oldSize + 1];
					boolean notAdded = true;
					for (int j = 0; j < oldSize; j++) {
						if (claimcheck.equals(oldTags[j])) {
							notAdded = false;
						}
						allTags[j] = oldTags[j];
					}
					if (notAdded) {
						allTags[oldSize] = claimcheck;
						match.setAllTagsInIncidentArray(allTags);
						String test = hasMatch(allTags[oldSize], tags, true);
						if (null != test) {
							String[] oldMatchTags = match.getMatchingTagNumberArray();
							int oldMatchSize = 0;
							if (oldMatchTags != null && oldMatchTags.length > 0) {
								oldMatchSize = oldMatchTags.length;
							}
							String[] matchTags = new String[oldMatchSize + 1];
							for (int j = 0; j < oldMatchSize; j++) {
								matchTags[j] = oldMatchTags[j];
							}
							matchTags[oldMatchSize] = test;
							match.setMatchingTagNumberArray(matchTags);
						}
					}
				} else {
					match = Match.Factory.newInstance();
					match.setIncident(incident);
					match.setType(type);
					match.setCreated(create);
					if (claimcheck != null && claimcheck.trim().length() > 0) {
						String[] allTags = new String[1];
						allTags[0] = claimcheck;
						match.setAllTagsInIncidentArray(allTags);
						String test = hasMatch(allTags[0], tags, true);
						if (null != test) {
							String[] matchTags = new String[1];
							matchTags[0] = test;
							match.setMatchingTagNumberArray(matchTags);
						}
					}
					if (pnr != null && pnr.trim().length() > 0) {
						match.setPnr(pnr);
						if (null != hasMatch(pnr, pnrs, false)) {
							match.setMatchingPnr(pnr);
						}
					}
					matches.put(incident, match);
				}
			}
		}
		ArrayList<Match> toReturn = new ArrayList<Match>(matches.values());
		return toReturn;
	}
	
	private String hasMatch(String test, String[] list, boolean tag) {
		if (test == null) {
			return null;
		}
		for (int i = 0; i < list.length; i++) {
			if (test.equalsIgnoreCase(list[i])) {
				return test;
			}
			if (tag && test.substring(test.length() - 6).equalsIgnoreCase(list[i].substring(list[i].length() - 6))) {
				return list[i];
			}
		}
		return null;
	}
}
