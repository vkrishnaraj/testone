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
import org.hibernate.classic.Session;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.ws.search.QueryNetTracerDocument.QueryNetTracer;
import com.bagnet.nettracer.ws.search.QueryNetTracerResponseDocument.QueryNetTracerResponse;
import com.bagnet.nettracer.ws.search.pojo.xsd.Match;

public class ScannerMbrQueryServiceImpl extends ScannerMbrQueryServiceSkeleton {
	
	private static final Logger logger = Logger
			.getLogger(ScannerMbrQueryServiceImpl.class);
	
	private static final int INCIDENT_ID = 0;
	private static final int TYPE = 1;
	private static final int CREATEDATE = 2;
	private static final int CLAIMCHECK_C = 3;
	private static final int CLAIMCHECK_ITM = 4;
	private static final int PNR = 5;
	private static final String LD_TYPE = "Lost/Delayed";
    
   /**
    * Auto generated method signature
    * 
    * @param queryNetTracer
    */
   public com.bagnet.nettracer.ws.search.QueryNetTracerResponseDocument queryNetTracer(com.bagnet.nettracer.ws.search.QueryNetTracerDocument queryNetTracer) {
	   return queryNetTracer(queryNetTracer, 4);
   }
   
   public com.bagnet.nettracer.ws.search.QueryNetTracerResponseDocument queryNetTracer(com.bagnet.nettracer.ws.search.QueryNetTracerDocument queryNetTracer, int subDays) {
		QueryNetTracer query = queryNetTracer.getQueryNetTracer();
		String[] tags = query.getArgs0Array();
		String[] pnrs = query.getArgs1Array();
		
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
    	
    	String addTime = "ADDTIME(i.createdate, i.createtime)";
    	String subTime = "DATE_SUB(UTC_TIMESTAMP(), INTERVAL " + subDays + " DAY)";
    	
    	boolean sqlServer = "org.hibernate.dialect.SQLServerDialect".equals(HibernateWrapper.getConfig().getProperties().getProperty("hibernate.dialect"));
    	
    	if (sqlServer) {
    		addTime = "DATEADD(second, ((DATEPART(hour, i.createtime) * 3600) + (DATEPART(minute, i.createtime) * 60) + DATEPART(second, i.createtime)), i.createdate)";
    		subTime = "DATEADD(day, -" + subDays + ", GETUTCDATE())";
    	}
    	
    	String query = "select i.Incident_ID as id, it.description as type, " + addTime + " as createdatetime, "
	    		+ "c.claimchecknum as c_check, itm.claimchecknum as itm_check, i.recordlocator as pnr from incident i "
    			+ "left outer join incident_claimcheck c on i.Incident_ID = c.incident_ID "
    			+ "left outer join item itm on i.Incident_ID = itm.incident_ID "
    			+ "join itemtype it on i.itemtype_ID = it.ItemType_ID "
    			+ "where " + subTime + " <= " + addTime + " and (1=0 " + generatePNR(pnrs) + generateTAG(tags) + ") "
    			+ "order by i.Incident_ID";
    	
    	logger.info("QUERY: \n\n" + query + "\n\nEND QUERY\n");
    	
    	List results = getQueryResults(query);
    	
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
    
    private String generatePNR(String[] pnrs) {
    	String returnMe = "";
    	if (pnrs != null && pnrs.length > 0) {
    		returnMe = " or (i.recordlocator in (";
    		boolean notFirst = false;
    		for (String pnr: pnrs) {
    			if (notFirst) {
    				returnMe += ", ";
    			}
    			returnMe += "'" + pnr + "'";
    			notFirst = true;
    		}
    		returnMe += ")) ";
    	}
    	return returnMe;
    }
    
    private String generateTAG(String[] tags) {
    	String returnMe = "";
    	if (tags != null && tags.length > 0) {
    		HashMap<String, ArrayList<String>> eightDig = new HashMap<String, ArrayList<String>>();
    		HashMap<String, ArrayList<String>> nineDig = new HashMap<String, ArrayList<String>>();
    		HashMap<String, ArrayList<String>> tenDig = new HashMap<String, ArrayList<String>>();
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
    		for (String key: eightDig.keySet()) {
    			String temp = " or (%TABLE%.claimchecknum_carriercode = '" + key + "' and %TABLE%.claimchecknum_bagnumber in (";
        		boolean notFirst = false;
        		for (String tag: eightDig.get(key)) {
        			if (notFirst) {
        				temp += ", ";
        			}
        			temp += "'" + tag + "'";
        			notFirst = true;
        		}
        		temp += ")) ";
        		returnMe += temp.replaceAll("%TABLE%", "itm");
        		returnMe += temp.replaceAll("%TABLE%", "c");
    		}
    		for (String key: nineDig.keySet()) {
    			String temp = " or (%TABLE%.claimchecknum_ticketingcode = '" + key + "' and %TABLE%.claimchecknum_bagnumber in (";
        		boolean notFirst = false;
        		for (String tag: nineDig.get(key)) {
        			if (notFirst) {
        				temp += ", ";
        			}
        			temp += "'" + tag + "'";
        			notFirst = true;
        		}
        		temp += ")) ";
        		returnMe += temp.replaceAll("%TABLE%", "itm");
        		returnMe += temp.replaceAll("%TABLE%", "c");
    		}
    		for (String key: tenDig.keySet()) {
    			String temp = " or (%TABLE%.claimchecknum_leading = " + key.substring(0, 1) + " and %TABLE%.claimchecknum_ticketingcode = '" + key.substring(1) 
    					+ "' and %TABLE%.claimchecknum_bagnumber in (";
        		boolean notFirst = false;
        		for (String tag: tenDig.get(key)) {
        			if (notFirst) {
        				temp += ", ";
        			}
        			temp += "'" + tag + "'";
        			notFirst = true;
        		}
        		temp += ")) ";
        		returnMe += temp.replaceAll("%TABLE%", "itm");
        		returnMe += temp.replaceAll("%TABLE%", "c");
    		}
    	}
    	return returnMe;
    }
    
	@SuppressWarnings("rawtypes")
	public List getQueryResults(String sql) {
		Session session = null;
		try {
			session = HibernateWrapper.getDirtySession().openSession();
			SQLQuery query = session.createSQLQuery(sql);

			query.addScalar("id", Hibernate.STRING);
			query.addScalar("type", Hibernate.STRING);
			query.addScalar("createdatetime", Hibernate.TIMESTAMP);
			query.addScalar("c_check", Hibernate.STRING);
			query.addScalar("itm_check", Hibernate.STRING);
			query.addScalar("pnr", Hibernate.STRING);

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
				if (LD_TYPE.equals(type)) {
					claimcheck = c_check;
				}
				
				if (matches.containsKey(incident) && claimcheck != null && claimcheck.trim().length() > 0) {
					match = matches.get(incident);
					String[] oldTags = match.getAllTagsInIncidentArray();
					int oldSize = oldTags.length;
					String[] allTags = new String[oldSize + 1];
					for (int j = 0; j < oldSize; j++) {
						allTags[j] = oldTags[j];
					}
					allTags[oldSize] = claimcheck;
					match.setAllTagsInIncidentArray(allTags);
					if (hasMatch(allTags[oldSize], tags)) {
						String[] oldMatchTags = match.getMatchingTagNumberArray();
						int oldMatchSize = oldMatchTags.length;
						String[] matchTags = new String[oldMatchSize + 1];
						for (int j = 0; j < oldMatchSize; j++) {
							matchTags[j] = oldMatchTags[j];
						}
						matchTags[oldMatchSize] = allTags[oldSize];
						match.setMatchingTagNumberArray(matchTags);
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
						if (hasMatch(allTags[0], tags)) {
							String[] matchTags = new String[1];
							matchTags[0] = allTags[0];
							match.setMatchingTagNumberArray(matchTags);
						}
					}
					match.setPnr(pnr);
					if (hasMatch(pnr, pnrs)) {
						String[] matchPnrs = new String[1];
						matchPnrs[0] = pnr;
						match.setMatchingPnrArray(matchPnrs);
					}
					matches.put(incident, match);
				}
			}
		}
		ArrayList<Match> toReturn = new ArrayList<Match>(matches.values());
		return toReturn;
	}
	
	private boolean hasMatch(String test, String[] list) {
		if (test == null) {
			return false;
		}
		for (int i = 0; i < list.length; i++) {
			if (test.equalsIgnoreCase(list[i])) {
				return true;
			}
		}
		return false;
	}
}
