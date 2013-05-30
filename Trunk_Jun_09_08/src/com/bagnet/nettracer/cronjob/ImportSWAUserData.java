package com.bagnet.nettracer.cronjob;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.htmlparser.Remark;

import aero.nettracer.fs.model.File;
import aero.nettracer.fs.model.FsAddress;
import aero.nettracer.fs.model.FsClaim;
import aero.nettracer.fs.model.FsIncident;
import aero.nettracer.fs.model.Person;
import aero.nettracer.fs.model.Bag;
import aero.nettracer.general.services.GeneralServiceBean;
import au.com.bytecode.opencsv.CSVReader;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.dao.ClaimDAO;
import com.bagnet.nettracer.tracing.dao.FileDAO;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.ExpensePayout;
import com.bagnet.nettracer.tracing.db.Claim;
import com.bagnet.nettracer.tracing.db.Item;
import com.bagnet.nettracer.tracing.db.CountryCode;
import com.bagnet.nettracer.tracing.db.State;
import com.bagnet.nettracer.tracing.db.audit.Audit_Agent;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.ClaimUtils;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.HibernateUtils;
import com.bagnet.nettracer.tracing.utils.audit.AuditAgentUtils;

public class ImportSWAUserData extends ImportClaimData {

	private static Pattern canada_zip = Pattern.compile("([0-9A-Z]{3}\\s{0,1}[0-9A-Z]{3})(\\s{2,}((\\w|\\s)*))?");
	
	final static int username = 0;
	final static int lastname = 1;
	final static int firstname = 2;
	final static int emailadd = 3;
	final static int adminaccess=4;
	
	static Agent ntuser = null;
	
	@Override
	protected Agent loadAgent() {
		if(ntuser == null){
			GeneralServiceBean bean = new GeneralServiceBean();
			ntuser = bean.getAgent("ntadmin", "WN");
		}
		return ntuser;
	}

	@Override
	protected void importThirdPartyClaims() {
		// TODO Auto-generated method stub
		Date start = new Date();
		Collection<Agent> users = parseData();
		Agent currentUser = null;
		int count = 0;
		int save = 0;
		for(Agent user:users){
			count++;
			if(!exists(user.getUsername()) && saveAgent(user)){
//				if(saveClaim(claim)){
					save++;
					currentUser = user;
//				queue.add(claim.getFile());
			}
		}
		Date end = new Date();
		System.out.println("FIN: " + currentUser.getUsername() + " " + save + "/" + count + " " + (end.getTime() - start.getTime()));
	}

	
	private static boolean exists(String id){
		String sql = "select agent_id from agent where companycode_id = 'WN' and username = :agentId";
		Session sess = HibernateWrapper.getSession().openSession();
		try{
			SQLQuery query = sess.createSQLQuery(sql);
			query.setParameter("agentId", id);
			List l = query.list();
//			if(l.size()>0)System.out.println("ID exists " + id);
			return l.size()>0;
		}catch(Exception e){
			e.printStackTrace();
		} finally {
			sess.close();
		}
		return false;
	}
	
	private boolean saveAgent(Agent user){
		
		boolean firstSave = user.getAgent_ID() == 0;
		HibernateUtils.saveNew(user);
		boolean userSaved = user.getAgent_ID()!=0;
		if (userSaved) {
			if (AdminUtils.getCompVariable(user.getCompanycode_ID()).getAudit_agent() == 1) {
				Audit_Agent audit_agent;
				try {
					audit_agent = AuditAgentUtils.getAuditAgent(user, ntuser);
					if (audit_agent != null) {
						HibernateUtils.saveNew(audit_agent);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
//			ClaimUtils.enterAuditClaimEntry(agent.getAgent_ID(), TracingConstants.FS_AUDIT_ITEM_TYPE_FILE, (claim.getFile()!= null?claim.getFile().getId():-1), TracingConstants.FS_ACTION_SAVE);
//			claim = ClaimDAO.loadClaim(claim.getId());
		} else {
			System.out.println(user.getUsername() + " has failed to save");
			return false;//TODO log error
		}
		
		
		return true;
	}
	
	private Collection<Agent> parseData(){
		String path = "C:\\Users\\Sean Fine\\Desktop\\Work files\\SWAFSUser.csv";
//		String path = "c:\\upload7\\test3.csv";
		List<String[]> list = parseCSV(filePath + "\\SWAFSUser2.csv");
		System.out.println("file length: " + list.size());
		System.out.println(list.iterator().next()[0]);
		
		
		GeneralServiceBean bean = new GeneralServiceBean();
		Agent agent = loadAgent();
		
		
		
//		HashMap<String,Claim> claims = new HashMap<String,Claim>();
//		HashMap<String,Claim> incidents = new HashMap<String,Claim>();
//		
		ArrayList<Agent> users = new ArrayList<Agent>();
		int i = 0;
		for(String[]s:list){
			String[] infolist=s[0].split(",");
			i++;
			Agent u=new Agent();
			u=populateAgent(u);
			
			u.setUsername(infolist[username]);
			u.setLastname(infolist[lastname]);
			u.setFirstname(infolist[firstname]);
			if(infolist[adminaccess]!=null && infolist[adminaccess].length()>0 && infolist[adminaccess].equals("YES") ){
				u.setUsergroup_id(2);
			} else {
				u.setUsergroup_id(3);
			}
			users.add(u);
		}
		return users;
	}
	
	private Agent populateAgent(Agent u) {
		u.setCompanycode_ID("WN");
		u.setActive(true);
		u.setDefaultlocale("en");
		u.setMname("");
		u.setCurrentlocale("en");
		u.setDefaultcurrency("USD");
		u.setDefaulttimezone("10");
		u.setCurrenttimezone("10");
		u.setDateformat(ntuser.getDateformat());
		u.setTimeformat(ntuser.getTimeformat());
		u.setWeb_enabled(true);
		u.setStation(ntuser.getStation());
		u.setTimeout(30);
		u.setPassword("39B819AE519B56A01F2684A08CA2D5473C70A9BF94E36FE51BDB9EF1E5162195"); //ntp@ssw0rd
		u.setReset_password(true);
		return u;
		
	}

		public static List<String[]> parseCSV(String file){
			CSVReader reader;
			try {
				reader = new CSVReader(new FileReader(file), '\t');
				
				return (List<String[]>) reader.readAll();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
		private static String clean(String s){
			return s.trim().replace("\"\'", "");
		}
		
	    public static boolean isIntegerParseInt(String str) {
	        try {
	            Integer.parseInt(str);
	            return true;
	        } catch (NumberFormatException nfe) {}
	        return false;
	    }
		
		public static void main(String[]args){

			ImportSWAUserData importer = new ImportSWAUserData();
			if (!importer.setVariablesFromArgs(args)) {
				System.err.println("Usage:\t" + ImportClaimDataUS.class.getSimpleName() + " [username] [password] [company] " +
						"[process(1=import, 2=third party, 3=submit to FS)] [trace active (0 or 1)] [thread count] [max return] " +
						"[queue size] [crm file path(OPTIONAL)]");
				System.err.println("Example:\t" + ImportClaimDataUS.class.getSimpleName() + " ntadmin Ladendead51! US 1 3 500 C:\\crm");
			} else {		
				importer.importClaims();
			}
			System.exit(0);
			
		}
}
